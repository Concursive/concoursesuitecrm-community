/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package org.aspcfs.modules.healthcare.edit.actions;

import org.aspcfs.modules.actions.CFSModule;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.database.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.*;
import org.aspcfs.controller.*;
import org.aspcfs.modules.service.base.*;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.healthcare.edit.base.TransactionRecord;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 *  Processes records that are posted from clients, using XML
 *
 *@author     chris
 *@created    February 11, 2003
 *@version    $Id$
 */
public final class ProcessTransaction extends CFSModule {

  /**
   *  A single XML transaction is processed
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    try {
      //This is a custom DTD that won't be used by any other processes, so
      //populate the object here
      XMLUtils xml = new XMLUtils(context.getRequest());
      TransactionRecord thisRecord = new TransactionRecord();
      XMLUtils.populateObject(thisRecord, xml.getDocumentElement());
      //get a database connection using the vhost context info
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);
      //Insert the record
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProcessTransaction-> " + thisRecord.getTransactionDate());
        System.out.println("ProcessTransaction-> " + thisRecord.getTransactionTime());
        System.out.println("ProcessTransaction-> " + thisRecord.getPerformed());
      }
      recordInserted = thisRecord.insert(db);
      //The object might have specified some validation errors
      if (thisRecord.hasErrors()) {
        
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }
    //Prepare the transaction result/response
    TransactionStatus thisMessage = new TransactionStatus();
    if (errorMessage == null) {
      thisMessage.setStatusCode(0);
    } else {
      thisMessage.setStatusCode(1);
      thisMessage.setMessage(errorMessage.getMessage());
    }
    //Construct the XML response
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = dbf.newDocumentBuilder();
      Document document = builder.newDocument();
      Element app = document.createElement("aspcfs");
      document.appendChild(app);
      //Append the response
      Element response = document.createElement("response");
      app.appendChild(response);
      //Append the status code (0=OK, 1=Error)
      Element status = document.createElement("status");
      status.appendChild(document.createTextNode(String.valueOf(thisMessage.getStatusCode())));
      response.appendChild(status);
      //Append the errorText
      Element errorText = document.createElement("errorText");
      if (thisMessage.getStatusCode() > 0) {
        errorText.appendChild(document.createTextNode(thisMessage.getMessage()));
      }
      response.appendChild(errorText);
      context.getRequest().setAttribute("statusXML", XMLUtils.toString(document));
    } catch (Exception pce) {
      pce.printStackTrace(System.out);
    }
    return ("PacketOK");
  }
}

