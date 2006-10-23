/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.service.actions;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SecurityHook;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.controller.objectHookManager.ObjectHookManager;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.service.base.*;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.utils.LoginUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.BufferedReader;

/**
 * An HTTP connector for incoming XML packet requests. Requests must include
 * credentials and transactions to perform. There can be multiple transactions
 * per request.<p>
 * <p/>
 * After processing is complete, a response is sent with a request status.
 *
 * @author matt rajkowski
 * @version $Id: ProcessPacket.java,v 1.36 2003/01/13 22:01:24 mrajkowski Exp
 *          $
 * @created April 24, 2002
 */
public final class ProcessPacket extends CFSModule {

  /**
   * XML API for HTTP requests.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    TransactionStatusList statusMessages = new TransactionStatusList();
    Connection db = null;
    Connection dbLookup = null;
    String encoding = "UTF-8";
    String commitLevel = "packet";

    try {
      //Indicates if the commit happens at the packet level or the individual
      //transaction level
      String commit = context.getRequest().getHeader("commit-level");
      if (commit != null && "transaction".equals(commit)) {
        commitLevel = "transaction";
      }

      //Put the request into an XML document
      HttpServletRequest request = context.getRequest();
      StringBuffer data = new StringBuffer();
      BufferedReader br = request.getReader();
      String line = null;
      if (System.getProperty("DEBUG") != null) {
        System.out.println("XMLUtils->Reading XML from request");
      }
      while ((line = br.readLine()) != null) {
        data.append(line.trim() + System.getProperty("line.separator"));
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("  XML: " + data.toString());
      }
      XMLUtils xml = new XMLUtils(data.toString());
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProcessPacket-> Parsing data");
      }
      //There should be an authentication node in the packet
      AuthenticationItem auth = new AuthenticationItem();
      XMLUtils.populateObject(auth, xml.getFirstChild("authentication"));
      encoding = auth.getEncoding();

      //Initialize the auth by getting the connection element
      ConnectionElement ce = auth.getConnectionElement(context);

      //TODO: Get rid of using the session for transactions, currently
      //it's being used for the ConnectionElement only.
      //Keep this session low (5 minutes)
      context.getSession().setMaxInactiveInterval(300);

      //Since this module bypasses the user login module, set this "user's"
      //connection info to simulate the login; currently required for getConnection
      context.getSession().setAttribute("ConnectionElement", ce);

      //Authentication Information can contain user login information
      UserBean thisUser = null;

      db = this.getConnection(context);

      boolean proceed = false;

      //Authenticate based on type of authentication header
      if (auth.getType() == AuthenticationItem.CENTRIC_USER) {
        thisUser = isUserValid(context, db, auth, applicationPrefs);
        proceed = (thisUser != null);
      } else if (auth.getType() == AuthenticationItem.SYNC_CLIENT) {
        proceed = isClientValid(context, db, auth);
      }

      if (proceed) {
        //Environment variables for this packet request
        PacketContext packetContext = new PacketContext();
        packetContext.setActionContext(context);
        packetContext.setAuthenticationItem(auth);
        packetContext.setUserBean(thisUser);

        //Prepare the SyncClientManager
        SyncClientManager clientManager = new SyncClientManager();
        clientManager.addClient(db, auth.getClientId());
        packetContext.setClientManager(clientManager);

        //Prepare the objectMap: The allowable objects that can be processed for the
        //given systemId
        HashMap objectMap = this.getObjectMap(context, db, auth);
        packetContext.setObjectMap(objectMap);

        ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute(
            "ConnectionPool");
        packetContext.setConnectionPool(sqlDriver);
        packetContext.setConnectionElement(ce);

        //Initialize the systemStatus for this request to reuse objects, if not already initialized
        Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
        SystemStatus systemStatus = SecurityHook.retrieveSystemStatus(
            context.getServletContext(), db, ce, thisSite.getLanguage());
        packetContext.setSystemStatus(systemStatus);

        //Prepare the objectHooks that are cached
        ObjectHookManager hookManager = systemStatus.getHookManager();
        packetContext.setObjectHookManager(hookManager);

        //2nd connection when transactions need to do additional processing
        dbLookup = this.getConnection(context);

        //Process the transactions
        LinkedList transactionList = new LinkedList();
        XMLUtils.getAllChildren(
            xml.getDocumentElement(), "transaction", transactionList);
        Iterator trans = transactionList.iterator();
        try {
          if ("packet".equals(commitLevel)) {
            dbLookup.setAutoCommit(false);
          }
          while (trans.hasNext()) {
            //Configure the transaction
            Element thisElement = (Element) trans.next();
            Transaction thisTransaction = new Transaction();
            thisTransaction.setPacketContext(packetContext);

            SyncTable metaMapping = new SyncTable();
            metaMapping.setName("meta");
            metaMapping.setMappedClassName(
                "org.aspcfs.modules.service.base.TransactionMeta");
            thisTransaction.addMapping("meta", metaMapping);
            thisTransaction.build(thisElement);

            //Check to see if required permissions are satisfied
            //to execute the transaction
            if (auth.getType() == AuthenticationItem.CENTRIC_USER) {
              //TODO: implement this
              //proceed = thisUser.hasAccess(thisTransaction);
              proceed = true;
            } else if (auth.getType() == AuthenticationItem.SYNC_CLIENT) {
              //TODO: determine access definitions
              proceed = true;
            }

            if (proceed) {
              int statusCode = thisTransaction.execute(db, dbLookup);
              //Build a status from the transaction response
              TransactionStatus thisStatus = new TransactionStatus();
              thisStatus.setStatusCode(statusCode);
              thisStatus.setId(thisTransaction.getId());
              thisStatus.setMessage(thisTransaction.getErrorMessage());
              thisStatus.setRecordList(thisTransaction.getRecordList());
              statusMessages.add(thisStatus);
            }
          }
          if ("packet".equals(commitLevel)) {
            dbLookup.commit();
          }
        } catch (SQLException e) {
          if ("packet".equals(commitLevel)) {
            dbLookup.rollback();
          }
          throw new SQLException(e.getMessage());
        } finally {
          if ("packet".equals(commitLevel)) {
            dbLookup.setAutoCommit(true);
          }
        }
        //Each transaction provides a status that needs to be returned to the client
        if (statusMessages.size() == 0 && transactionList.size() == 0) {
          TransactionStatus thisStatus = new TransactionStatus();
          thisStatus.setStatusCode(1);
          thisStatus.setMessage("No transactions found");
          statusMessages.add(thisStatus);
        }
      } else {
        //The packet failed authentication
        TransactionStatus thisStatus = new TransactionStatus();
        thisStatus.setStatusCode(1);
        thisStatus.setMessage("Not authorized");
        statusMessages.add(thisStatus);
      }
    } catch (Exception e) {
      //The transaction usually catches errors, but not always
      e.printStackTrace();
      TransactionStatus thisStatus = new TransactionStatus();
      thisStatus.setStatusCode(1);
      thisStatus.setMessage("Error: " + e.getMessage());
      statusMessages.add(thisStatus);
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
      if (dbLookup != null) {
        this.freeConnection(context, dbLookup);
      }
    }
    //Construct the XML response
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = dbf.newDocumentBuilder();
      Document document = builder.newDocument();
      Element app = document.createElement("aspcfs");
      document.appendChild(app);
      //Convert the result messages to XML
      int returnedRecordCount = 0;
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ProcessPacket-> Processing StatusMessages for output: " + statusMessages.size());
      }
      //Process the status messages for output
      returnedRecordCount = statusMessages.appendResponse(document, app);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ProcessPacket-> Total Records: " + returnedRecordCount);
      }
      context.getRequest().setAttribute(
          "statusXML", XMLUtils.toString(document, encoding));
    } catch (Exception pce) {
      pce.printStackTrace(System.out);
    }
    return ("PacketOK");
  }


  /**
   * Gets the userValid attribute of the ProcessPacket object
   *
   * @param context          Description of the Parameter
   * @param db               Description of the Parameter
   * @param auth             Description of the Parameter
   * @param applicationPrefs Description of the Parameter
   * @return The userValid value
   */
  private UserBean isUserValid(ActionContext context, Connection db,
                              AuthenticationItem auth, ApplicationPrefs applicationPrefs) throws Exception {
    //Perform User Validation similar to Login
    LoginUtils loginUtils = new LoginUtils(db, auth.getUsername(), auth.getCode());
    loginUtils.setApplicationPrefs(applicationPrefs);
    if (loginUtils.isUserValid(context, db)) {
      if (loginUtils.hasHttpApiAccess(db)) {
        return loginUtils.getUserBean();
      }
    }
    return null;
  }


  /**
   * Gets the clientValid attribute of the ProcessPacket object
   *
   * @param context Description of the Parameter
   * @param db      Description of the Parameter
   * @param auth    Description of the Parameter
   * @return The clientValid value
   */
  private boolean isClientValid(ActionContext context, Connection db, AuthenticationItem auth) throws Exception {
    // Perform a lookup in the sync client table to set the authcode
    SyncClient client = new SyncClient(db, auth.getClientId());
    if (!client.getEnabled()) {
      return false;
    }
    // Test aspmode first
    if ((client.getCode() == null || "".equals(client.getCode())) && "true".equals(getPref(context, "WEBSERVER.ASPMODE")))
    {
      return auth.isAuthenticated(context);
    }
    // Test client
    auth.setAuthCode(client.getCode());
    return (auth.isAuthenticated(context));
  }


  /**
   * Clears the sync map that may have been cached in memory. The sync map only
   * needs to be cleared when the sync table has been modified.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandReloadSyncMap(ActionContext context) {
    context.getServletContext().removeAttribute("SyncObjectMap");
    return ("PacketOK");
  }


  /**
   * Gets the objectMap attribute of the ProcessPacket object
   *
   * @param context Description of Parameter
   * @param db      Description of Parameter
   * @param auth    Description of the Parameter
   * @return The objectMap value
   */
  private HashMap getObjectMap(ActionContext context, Connection db, AuthenticationItem auth) {
    SyncTableList systemObjectMap = (SyncTableList) context.getServletContext().getAttribute(
        "SyncObjectMap" + auth.getId());
    if (systemObjectMap == null) {
      synchronized (this) {
        systemObjectMap = (SyncTableList) context.getServletContext().getAttribute(
            "SyncObjectMap" + auth.getId());
        if (systemObjectMap == null) {
          systemObjectMap = new SyncTableList();
          systemObjectMap.setBuildTextFields(false);
          try {
            systemObjectMap.buildList(db);
          } catch (SQLException e) {
            e.printStackTrace(System.out);
          }
          context.getServletContext().setAttribute(
              "SyncObjectMap" + auth.getId(), systemObjectMap);
        }
      }
    }
    return (systemObjectMap.getObjectMapping(auth.getSystemId()));
  }
}

