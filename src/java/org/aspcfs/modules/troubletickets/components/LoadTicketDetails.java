//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfs.troubletickets.component;

import com.darkhorseventures.controller.*;
import java.sql.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.webutils.LookupElement;

public class LoadTicketDetails extends ObjectHookComponent implements ComponentInterface {

  public static final String ORGANIZATION = "ticketOrganization";
  public static final String CONTACT = "ticketContact";
  public static final String CATEGORY_LOOKUP = "ticketCategoryLookup";
  public static final String SUBCATEGORY1_LOOKUP = "ticketSubCategory1Lookup";
  public static final String SUBCATEGORY2_LOOKUP = "ticketSubCategory2Lookup";
  public static final String SUBCATEGORY3_LOOKUP = "ticketSubCategory3Lookup";
  public static final String SEVERITY_LOOKUP = "ticketSeverityLookup";
  public static final String PRIORITY_LOOKUP = "ticketPriorityLookup";
  public static final String ENTERED_BY_CONTACT = "ticketModifiedByContact";
  public static final String MODIFIED_BY_CONTACT = "ticketModifiedByContact";
  
  public String getDescription() {
    return "Expands ticket information for use in other components for the current ticket";
  }
  
  public boolean execute(ComponentContext context) {
    boolean result = false;
    Ticket thisTicket = (Ticket)context.getThisObject();
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (thisTicket.getOrgId() > 0) {
        Organization organization = new Organization(db, thisTicket.getOrgId());
        context.setAttribute(ORGANIZATION, organization);
      }
      if (thisTicket.getContactId() > 0) {
        Contact contact = new Contact(db, thisTicket.getContactId());
        context.setAttribute(CONTACT, contact);
      }
      if (thisTicket.getCatCode() > 0) {
        TicketCategory categoryLookup = new TicketCategory(db, thisTicket.getCatCode());
        context.setAttribute(CATEGORY_LOOKUP, categoryLookup);
      }
      if (thisTicket.getSubCat1() > 0) {
        TicketCategory subCategory1Lookup = new TicketCategory(db, thisTicket.getSubCat1());
        context.setAttribute(SUBCATEGORY1_LOOKUP, subCategory1Lookup);
      }
      if (thisTicket.getSubCat2() > 0) {
        TicketCategory subCategory2Lookup = new TicketCategory(db, thisTicket.getSubCat2());
        context.setAttribute(SUBCATEGORY2_LOOKUP, subCategory2Lookup);
      }
      if (thisTicket.getSubCat3() > 0) {
        TicketCategory subCategory3Lookup = new TicketCategory(db, thisTicket.getSubCat3());
        context.setAttribute(SUBCATEGORY3_LOOKUP, subCategory3Lookup);
      }
      if (thisTicket.getSeverityCode() > 0) {
        LookupElement severityLookup = new LookupElement(db, thisTicket.getSeverityCode(), "ticket_severity");
        context.setAttribute(SEVERITY_LOOKUP, severityLookup);
      }
      if (thisTicket.getPriorityCode() > 0) {
        LookupElement priorityLookup = new LookupElement(db, thisTicket.getPriorityCode(), "ticket_priority");
        context.setAttribute(PRIORITY_LOOKUP, priorityLookup);
      }
      if (thisTicket.getModifiedBy() > 0) {
        User user = new User(db, thisTicket.getModifiedBy());
        Contact contact = new Contact(db, user.getContactId());
        context.setAttribute(MODIFIED_BY_CONTACT, contact);
      }
      if (thisTicket.getEnteredBy() > 0) {
        User user = new User(db, thisTicket.getEnteredBy());
        Contact contact = new Contact(db, user.getContactId());
        context.setAttribute(ENTERED_BY_CONTACT, contact);
      }
      result = true;
    } catch (Exception e) {
      
    } finally {
      this.freeConnection(context, db);
    }
    return result;
  }
}
