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
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.lang.reflect.*;


public final class ProcessPacket extends CFSModule {

  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    ArrayList statusMessages = new ArrayList();
    Connection db = null;

    try {
      XMLUtils xml = new XMLUtils(context.getRequest());

      if (System.getProperty("DEBUG") != null)
        System.out.println("ProcessPacket-> Parsing data");

      AuthenticationItem auth = new AuthenticationItem();
      xml.populateObject(auth, xml.getFirstChild("authentication"));
      db = auth.getConnection(context); 
      if (db != null) {
        Vector transactionList = new Vector();
        xml.getAllChildren(xml.getDocumentElement(), "transaction", transactionList);
        Iterator trans = transactionList.iterator();
        while (trans.hasNext()) {
          Element thisElement = (Element)trans.next();
          Transaction thisTransaction = new Transaction();
          thisTransaction.addMapping("account", "com.darkhorseventures.cfsbase.Organization");
          thisTransaction.addMapping("organization", "com.darkhorseventures.cfsbase.Organization");
          thisTransaction.addMapping("contact", "com.darkhorseventures.cfsbase.Contact");
          thisTransaction.addMapping("ticket", "com.darkhorseventures.cfsbase.Ticket");
          thisTransaction.addMapping("folder", "com.darkhorseventures.cfsbase.CustomFieldCategory");
          thisTransaction.build(thisElement);
          int statusCode = thisTransaction.execute(db);
          StatusMessage thisStatus = new StatusMessage();
          thisStatus.setStatusCode(statusCode);
          thisStatus.setId(thisTransaction.getId());
          thisStatus.setMessage(thisTransaction.getErrorMessage());
          statusMessages.add(thisStatus);
        }

        if (statusMessages.size() == 0 && transactionList.size() == 0) {
          StatusMessage thisStatus = new StatusMessage();
          thisStatus.setStatusCode(1);
          thisStatus.setMessage("No transactions found");
          statusMessages.add(thisStatus);
        }
      } else {
        StatusMessage thisStatus = new StatusMessage();
        thisStatus.setStatusCode(1);
        thisStatus.setMessage("Not authorized");
        statusMessages.add(thisStatus);
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
      StatusMessage thisStatus = new StatusMessage();
      thisStatus.setStatusCode(1);
      thisStatus.setMessage("Error: " + e.getMessage());
      statusMessages.add(thisStatus);
    } finally {
      if (db != null) this.freeConnection(context, db);
    }

    context.getRequest().setAttribute("statusMessages", statusMessages);
    return ("PacketOK");
  }
}

