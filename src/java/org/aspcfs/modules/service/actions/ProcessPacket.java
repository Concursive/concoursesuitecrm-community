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
    int statusCode = 1;
    String errorText = null;

    try {
      XMLUtils xml = new XMLUtils(context.getRequest());

      if (System.getProperty("DEBUG") != null)
        System.out.println("ProcessPacket-> Parsing data");

      AuthenticationItem auth = new AuthenticationItem();
      xml.populateObject(auth, xml.getFirstChild("authentication"));

      //if (auth.isValid(context)) {
      if (auth != null) {
        String siteId = auth.getId();
        String code = auth.getCode();
        System.out.println(siteId + "/" + code);

        Vector transactionList = new Vector();
        xml.getAllChildren(xml.getDocumentElement(), "transaction", transactionList);
        Iterator trans = transactionList.iterator();
        while (trans.hasNext()) {
          Transaction thisTransaction = new Transaction();
          thisTransaction.addMapping("account", "com.darkhorseventures.cfsbase.Account");
          thisTransaction.addMapping("contact", "com.darkhorseventures.cfsbase.Contact");
          thisTransaction.addMapping("ticket", "com.darkhorseventures.cfsbase.Ticket");
          thisTransaction.build((Element)trans.next());
        }

        if (errorText == null && transactionList.size() > 0) {
          statusCode = 0;
        }
        if (errorText == null && transactionList.size() == 0) {
          errorText = "No transactions found";
        }

      } else {
        errorText = "Authentication element not found";
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
      errorText = "Error: " + e.getMessage();
    }

    context.getRequest().setAttribute("statusCode", "" + statusCode);
    context.getRequest().setAttribute("errorText", errorText);
    return ("PacketOK");
  }
}

