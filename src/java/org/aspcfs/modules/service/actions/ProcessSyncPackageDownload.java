/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.database.ConnectionElement;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.webutils.FileDownload;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.modules.service.sync.base.SyncPackage;
import org.aspcfs.utils.LoginUtils;
import org.aspcfs.utils.XMLUtils;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    November 16, 2006
 */
public class ProcessSyncPackageDownload extends CFSModule {
  /**
   *  Start the process when triggered by an offline client
   *
   * @param  context     Description of the Parameter
   * @return                   Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    Exception errorMessage = null;
    Connection db = null;
    String encoding = null;
    
    try {
      //Put the request into an XML document
      HttpServletRequest request = context.getRequest();
      StringBuffer data = new StringBuffer();
      BufferedReader br = request.getReader();
      String line = null;
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProcessSyncPackageDownload-> Reading XML from request");
      }
      while ((line = br.readLine()) != null) {
        data.append(line.trim() + System.getProperty("line.separator"));
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("  XML: " + data.toString());
      }
      XMLUtils xml = new XMLUtils(data.toString());
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProcessSyncPackageDownload-> Parsing data");
      }
      
      //There should be an authentication node in the packet
      AuthenticationItem auth = new AuthenticationItem();
      XMLUtils.populateObject(auth, xml.getFirstChild("authentication"));
      encoding = auth.getEncoding();
      
      //Initialize the auth by getting the connection element
      ConnectionElement ce = auth.getConnectionElement(context);
      context.getSession().setAttribute("ConnectionElement", ce);
      
      //TODO: Get rid of using the session for transactions, currently
      //it's being used for the ConnectionElement only.
      //Keep this session low (5 minutes)
      context.getSession().setMaxInactiveInterval(300);
      
      db = this.getConnection(context);
      
      SyncClient client = new SyncClient(db, auth.getClientId());
      
      LoginUtils loginUtils = new LoginUtils(db, auth.getUsername(), auth.getCode());
      loginUtils.setApplicationPrefs(applicationPrefs);
      
      if (loginUtils.isUserValid(context, db) && loginUtils.hasHttpApiAccess(db) 
                && client.getEnabled()) {
        //Offline client has been successfully authenticated. Determine the file that is being requested
        //for download
        Timestamp lastAnchor = auth.getLastAnchor();
        Timestamp nextAnchor = auth.getNextAnchor();
        if (client.hasSyncPackage(db)) {
          SyncPackage syncPackage = SyncPackage.getClientSyncPakage(db, client.getId());
          if ((lastAnchor == null && syncPackage.getLastAnchor() == null) || (lastAnchor.equals(syncPackage.getLastAnchor()) &&
                nextAnchor.equals(syncPackage.getNextAnchor()))) {
            //Stream the file
            FileItem itemToDownload = new FileItem(db, client.getPackageFileId());
            String filePath = this.getPath(context, "sync-packages") + getDatePath(
              itemToDownload.getEntered()) + itemToDownload.getFilename();
            
            FileDownload fileDownload = new FileDownload();
            fileDownload.setFullPath(filePath);
            fileDownload.setDisplayName(itemToDownload.getClientFilename());
            if (fileDownload.fileExists()) {
              fileDownload.sendFile(context);
              itemToDownload.updateCounter(db);
            } else {
              System.err.println(
                  "ProcessSyncPackageDownload-> Trying to send a file that does not exist");
            }
            
            syncPackage.setStatusId(SyncPackage.DOWNLOADED);
            syncPackage.update(db);
          }
        } else {
          //send appropriate error message
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      //send appropriate error message
    }
    
    return "-none-";
  }
}

