/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.controller.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.lang.reflect.*;

/**
 *  An HTTP connector for incoming XML packet requests. Requests must include
 *  credentials and transactions to perform. There can be multiple transactions
 *  per request.<p>
 *
 *  After processing is complete, a response is sent with a request status.
 *
 *@author     matt
 *@created    April 24, 2002
 *@version    $Id$
 */
public final class ProcessPacket extends CFSModule {

  /**
   *  XML API for HTTP requests.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    LinkedList statusMessages = new LinkedList();
    Connection db = null;
    Connection dbLookup = null;

    try {
      //Put the request into an XML document
      XMLUtils xml = new XMLUtils(context.getRequest());
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProcessPacket-> Parsing data");
      }
      //There should be an authentication node in the packet
      AuthenticationItem auth = new AuthenticationItem();
      xml.populateObject(auth, xml.getFirstChild("authentication"));
      db = auth.getConnection(context);
      if (db != null) {
        if (auth.getSystemId() == -1) {
          //For Vport
          auth.setSystemId(1);
        }
        
        //Prepare the SyncClientManager
        SyncClientManager clientManager = new SyncClientManager();
        clientManager.addClient(db, auth.getClientId());
        
        //Prepare the objectMap: The allowable objects that can be processed for the
        //given systemId
        HashMap objectMap = this.getObjectMap(context, db, auth.getSystemId());
        
        //Prepare the objectHooks: Inserts and updates can trigger code if specified
        ObjectHookList hooks = new ObjectHookList();
        hooks.buildList(db);
        
        //2nd connection when transactions need to do additional processing
        dbLookup = auth.getConnection(context);
        dbLookup.setAutoCommit(false);
        
        //Process the transactions
        LinkedList transactionList = new LinkedList();
        xml.getAllChildren(xml.getDocumentElement(), "transaction", transactionList);
        Iterator trans = transactionList.iterator();
        while (trans.hasNext()) {
          //Configure the transaction
          Element thisElement = (Element) trans.next();
          Transaction thisTransaction = new Transaction();
          thisTransaction.setAuth(auth);
          thisTransaction.setMapping(objectMap);
          thisTransaction.setClientManager(clientManager);
          thisTransaction.setObjectHookList(hooks);
          SyncTable metaMapping = new SyncTable();
          metaMapping.setName("meta");
          metaMapping.setMappedClassName("com.darkhorseventures.utils.TransactionMeta");
          thisTransaction.addMapping("meta", metaMapping);
          thisTransaction.build(thisElement);
          //Execute the transaction
          int statusCode = thisTransaction.execute(db, dbLookup);
          //Build a status from the transaction response
          TransactionStatus thisStatus = new TransactionStatus();
          thisStatus.setStatusCode(statusCode);
          thisStatus.setId(thisTransaction.getId());
          thisStatus.setMessage(thisTransaction.getErrorMessage());
          thisStatus.setRecordList(thisTransaction.getRecordList());
          statusMessages.add(thisStatus);
        }
        dbLookup.commit();
        dbLookup.setAutoCommit(true);
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
      errorMessage = e;
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
        System.out.println("ProcessPacket-> Processing StatusMessages for output: " + statusMessages.size());
      }
      Iterator messages = statusMessages.iterator();
      while (messages.hasNext()) {
        TransactionStatus thisMessage = (TransactionStatus) messages.next();
        Element response = document.createElement("response");
        if (thisMessage.getId() > -1) {
          response.setAttribute("id", "" + thisMessage.getId());
        }
        app.appendChild(response);
        Element status = document.createElement("status");
        status.appendChild(document.createTextNode(String.valueOf(thisMessage.getStatusCode())));
        response.appendChild(status);

        Element errorText = document.createElement("errorText");
        errorText.appendChild(document.createTextNode(thisMessage.getMessage()));
        response.appendChild(errorText);

        if (!thisMessage.hasError() && thisMessage.hasRecordList()) {
          Element recordSet = document.createElement("recordSet");
          recordSet.setAttribute("name", thisMessage.getRecordList().getName());
          recordSet.setAttribute("count", String.valueOf(thisMessage.getRecordList().size()));
          if (thisMessage.getRecordList().getTotalRecords() > -1) {
            recordSet.setAttribute("total", String.valueOf(thisMessage.getRecordList().getTotalRecords()));
          }
          response.appendChild(recordSet);

          returnedRecordCount += thisMessage.getRecordList().size();
          Iterator recordList = thisMessage.getRecordList().iterator();
          while (recordList.hasNext()) {
            Element record = document.createElement("record");
            recordSet.appendChild(record);
            Record thisRecord = (Record) recordList.next();
            if (thisRecord.hasAction()) {
              record.setAttribute("action", thisRecord.getAction());
            }
            Iterator fields = thisRecord.keySet().iterator();
            while (fields.hasNext()) {
              String fieldName = (String) fields.next();
              String fieldValue = (String) thisRecord.get(fieldName);
              if (fieldValue == null) {
                fieldValue = "";
              }
              Element field = document.createElement(fieldName);
              field.appendChild(document.createTextNode(fieldValue));
              record.appendChild(field);
            }
          }

        }
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProcessPacket-> Total Records: " + returnedRecordCount); 
      }

      context.getRequest().setAttribute("statusXML", XMLUtils.toString(document));
    } catch (Exception pce) {
      pce.printStackTrace(System.out);
    }
    return ("PacketOK");
  }


  /**
   *  Clears the sync map that may have been cached in memory. The sync map only
   *  needs to be cleared when the sync table has been modified.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandReloadSyncMap(ActionContext context) {
    context.getServletContext().removeAttribute("SyncObjectMap");
    return ("PacketOK");
  }


  /**
   *  Gets the objectMap attribute of the ProcessPacket object
   *
   *@param  context   Description of Parameter
   *@param  db        Description of Parameter
   *@param  systemId  Description of Parameter
   *@return           The objectMap value
   */
  private HashMap getObjectMap(ActionContext context, Connection db, int systemId) {
    SyncTableList systemObjectMap = (SyncTableList) context.getServletContext().getAttribute("SyncObjectMap" + systemId);
    if (systemObjectMap == null) {
      synchronized (this) {
        systemObjectMap = (SyncTableList) context.getServletContext().getAttribute("SyncObjectMap" + systemId);
        if (systemObjectMap == null) {
          systemObjectMap = new SyncTableList();
          systemObjectMap.setBuildTextFields(false);
          try {
            systemObjectMap.buildList(db);
          } catch (SQLException e) {
            e.printStackTrace(System.out);
          }
          context.getServletContext().setAttribute("SyncObjectMap" + systemId, systemObjectMap);
        }
      }
    }
    HashMap thisObjectMap = systemObjectMap.getObjectMapping(systemId);
    return thisObjectMap;
  }
}

