//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.components;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import com.netdecisions.scenarios.util.CCPHTTPProcessInitiator;
import java.io.*;
import java.sql.*;
import org.aspcfs.controller.*;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.controller.objectHookManager.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.troubletickets.base.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowksi
 *@created    January 14, 2003
 *@version    $Id$
 */
public class SendTicketToBPM extends ObjectHookComponent implements ComponentInterface {

  public final static String BPM_HOST_URL = "bpm.host.url";


  /**
   *  Gets the description attribute of the SendTicketToBPM object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Send ticket information to NetDecisions BPM";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    Ticket thisTicket = (Ticket) context.getThisObject();

    try {
      Organization organization = (Organization) context.getAttribute(LoadTicketDetails.ORGANIZATION);
      Contact contact = (Contact) context.getAttribute(LoadTicketDetails.CONTACT);
      TicketCategory categoryLookup = (TicketCategory) context.getAttribute(LoadTicketDetails.CATEGORY_LOOKUP);
      TicketCategory subCategory1Lookup = (TicketCategory) context.getAttribute(LoadTicketDetails.SUBCATEGORY1_LOOKUP);
      TicketCategory subCategory2Lookup = (TicketCategory) context.getAttribute(LoadTicketDetails.SUBCATEGORY2_LOOKUP);
      TicketCategory subCategory3Lookup = (TicketCategory) context.getAttribute(LoadTicketDetails.SUBCATEGORY3_LOOKUP);
      LookupElement severityLookup = (LookupElement) context.getAttribute(LoadTicketDetails.SEVERITY_LOOKUP);

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

      CCPHTTPProcessInitiator ccp = new CCPHTTPProcessInitiator();
      ccp.setLocation(context.getParameter(BPM_HOST_URL));
      ccp.initiateProcess(ticketXML);
      return true;
    } catch (Exception e) {
    }
    return result;
  }
}

