//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfs.component;

import com.darkhorseventures.cfs.troubletickets.component.LoadTicketDetails;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import com.netdecisions.scenarios.util.CCPHTTPProcessInitiator;
import java.io.*;
import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;
import java.sql.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.webutils.LookupElement;

public class SendTicketToBPM extends ObjectHookComponent implements ComponentInterface {
  
  public String getDescription() {
    return "Send ticket information to NetDecisions BPM.";
  }
  
  public boolean execute(ComponentContext context) {
    boolean result = false;
    Ticket thisTicket = (Ticket)context.getThisObject();
    Organization organization = (Organization)context.getAttribute(LoadTicketDetails.ORGANIZATION);
    Contact contact = (Contact)context.getAttribute(LoadTicketDetails.CONTACT);
    
    LookupElement categoryLookup = (LookupElement)context.getAttribute(LoadTicketDetails.CATEGORY_LOOKUP);
    LookupElement subCategory1Lookup = (LookupElement)context.getAttribute(LoadTicketDetails.SUBCATEGORY1_LOOKUP);
    LookupElement subCategory2Lookup = (LookupElement)context.getAttribute(LoadTicketDetails.SUBCATEGORY2_LOOKUP);
    LookupElement subCategory3Lookup = (LookupElement)context.getAttribute(LoadTicketDetails.SUBCATEGORY3_LOOKUP);
    LookupElement severityLookup = (LookupElement)context.getAttribute(LoadTicketDetails.SEVERITY_LOOKUP);
    
    try {
      //Build an XML document needed for BPM
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = dbf.newDocumentBuilder();
      Document document = builder.newDocument();
      Element app = document.createElement("app");
      document.appendChild(app);

      Element ticketXML = document.createElement("ticket");
      app.appendChild(ticketXML);

      Element id = document.createElement("id");
      id.appendChild(document.createTextNode(String.valueOf(thisTicket.getId())));
      ticketXML.appendChild(id);

      if (organization != null) {
        Element thisElement = document.createElement("org");
        thisElement.appendChild(document.createTextNode(organization.getName()));
        ticketXML.appendChild(thisElement);
      }

      if (contact != null) {
        Element thisElement = document.createElement("contact");
        thisElement.appendChild(document.createTextNode(contact.getNameFull()));
        ticketXML.appendChild(thisElement);
      }

      if (1 == 1 && thisTicket.getProblem() != null) {
        Element thisElement = document.createElement("message");
        thisElement.appendChild(document.createTextNode(thisTicket.getProblem()));
        ticketXML.appendChild(thisElement);
      }

      if (1 == 1 && thisTicket.getComment() != null) {
        Element thisElement = document.createElement("comment");
        thisElement.appendChild(document.createTextNode(thisTicket.getComment()));
        ticketXML.appendChild(thisElement);
      }

      if (1 == 1 && thisTicket.getSolution() != null) {
        Element thisElement = document.createElement("solution");
        thisElement.appendChild(document.createTextNode(thisTicket.getSolution()));
        ticketXML.appendChild(thisElement);
      }

      //priorityCode
      //levelCode
      //department
      //sourceCode

      if (categoryLookup != null) {
        Element thisElement = document.createElement("cat");
        thisElement.appendChild(document.createTextNode(categoryLookup.getDescription()));
        ticketXML.appendChild(thisElement);
      }

      if (subCategory1Lookup != null) {
        Element thisElement = document.createElement("subCat1");
        thisElement.appendChild(document.createTextNode(subCategory1Lookup.getDescription()));
        ticketXML.appendChild(thisElement);
      }

      if (subCategory2Lookup != null) {
        Element thisElement = document.createElement("subCat2");
        thisElement.appendChild(document.createTextNode(subCategory2Lookup.getDescription()));
        ticketXML.appendChild(thisElement);
      }

      if (subCategory3Lookup != null) {
        Element thisElement = document.createElement("subCat3");
        thisElement.appendChild(document.createTextNode(subCategory3Lookup.getDescription()));
        ticketXML.appendChild(thisElement);
      }

      if (severityLookup != null) {
        Element thisElement = document.createElement("severity");
        thisElement.appendChild(document.createTextNode(severityLookup.getDescription()));
        ticketXML.appendChild(thisElement);
      }

      if (1 == 1) {
        Element thisElement = document.createElement("closed");
        String closedText = (thisTicket.getClosed() != null ? "true" : "false");
        thisElement.appendChild(document.createTextNode(closedText));
        ticketXML.appendChild(thisElement);
      }

      if (1 == 1) {
        Element thisElement = document.createElement("entered");
        thisElement.appendChild(document.createTextNode(String.valueOf(thisTicket.getEntered())));
        ticketXML.appendChild(thisElement);
      }

      if (System.getProperty("DEBUG") != null) {
        System.out.println("TicketHook-> XML: " + XMLUtils.toString(app));
      }
      
      CCPHTTPProcessInitiator ccp = new CCPHTTPProcessInitiator();
      ccp.setLocation("http://ds21.darkhorseventures.com:8080/Horse/servlet/ProcessInitiateServlet");
      ccp.initiateProcess(ticketXML);
      return true;
    } catch (Exception e) {
    }
    return result;
  }
}
