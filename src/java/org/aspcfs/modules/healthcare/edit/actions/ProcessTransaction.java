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
package org.aspcfs.modules.healthcare.edit.actions;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SecurityHook;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.healthcare.edit.base.TransactionRecord;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.service.base.TransactionStatus;
import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.Connection;

/**
 * Processes records that are posted from clients, using XML
 *
 * @author chris
 * @version $Id: ProcessTransaction.java,v 1.13 2004/03/29 21:30:08 mrajkowski
 *          Exp $
 * @created February 11, 2003
 */
public final class ProcessTransaction extends CFSModule {

  /**
   * A single XML transaction is processed
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    XMLUtils xml = null;
    ConnectionElement ce = null;
    SystemStatus thisSystem = null;
    try {
      //This is a custom DTD that won't be used by any other processes, so
      //populate the object here
      xml = new XMLUtils(context.getRequest());
      TransactionRecord thisRecord = new TransactionRecord();
      XMLUtils.populateObject(thisRecord, xml.getDocumentElement());
      //get a database connection using the vhost context info
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);

      // The connection element is needed to retrieve the systemSystem object later
      ce = auth.getConnectionElement(context);
      thisSystem = this.getSystemStatus(context, ce);
      if (thisSystem == null) {
        //Since typical login was bypassed, make sure the system status is in memory
        thisSystem = SecurityHook.retrieveSystemStatus(
            context.getServletContext(), db, ce);
      }

      //Insert the record
      isValid = this.validateObject(context, db, thisRecord);
      if (isValid) {
        recordInserted = thisRecord.insert(db);
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
      status.appendChild(
          document.createTextNode(String.valueOf(thisMessage.getStatusCode())));
      response.appendChild(status);
      //Append the errorText
      Element errorText = document.createElement("errorText");
      if (thisMessage.getStatusCode() > 0) {
        errorText.appendChild(
            document.createTextNode(thisMessage.getMessage()));
      }
      response.appendChild(errorText);
      context.getRequest().setAttribute(
          "statusXML", XMLUtils.toString(document));
    } catch (Exception pce) {
      pce.printStackTrace(System.out);
    }
    // This project is phasing out so all requests are being forwarded
    try {
      if (xml != null && ce != null && thisSystem != null) {
        System.out.println("ProcessTransaction-> CE: " + ce.getUrl());
        System.out.println(
            "ProcessTransaction-> Trying to forward to: " + getValue(
                thisSystem, "FORWARD.URL"));
        System.out.println(
            "ProcessTransaction-> The message: " + xml.toString());
        HTTPUtils.sendPacket(
            getValue(thisSystem, "FORWARD.URL"), xml.toString());
        System.out.println(
            "ProcessTransaction-> Forwarded to: " + getValue(
                thisSystem, "FORWARD.URL"));
      } else {
        System.out.println("ProcessTransaction-> DID NOT FORWARD");
      }
    } catch (Exception e) {
      System.out.println("ProcessTransaction-> ERROR: " + e.getMessage());
    }
    return ("PacketOK");
  }


  /**
   * Gets the value attribute of the ProcessTransaction object
   *
   * @param thisSystem Description of the Parameter
   * @param param      Description of the Parameter
   * @return The value value
   */
  private String getValue(SystemStatus thisSystem, String param) {
    return thisSystem.getValue(
        "org.aspcfs.modules.healthcare.edit.actions.ProcessTransaction", param);
  }
}

