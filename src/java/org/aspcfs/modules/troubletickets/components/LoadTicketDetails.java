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
package org.aspcfs.modules.troubletickets.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.troubletickets.base.TicketCategory;
import org.aspcfs.utils.web.LookupElement;

import java.sql.Connection;
import java.text.DateFormat;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id: LoadTicketDetails.java,v 1.6 2003/05/22 19:30:14 mrajkowski
 *          Exp $
 * @created January 14, 2003
 */
public class LoadTicketDetails extends ObjectHookComponent implements ComponentInterface {

  public final static String ORGANIZATION = "ticketOrganization";
  public final static String CONTACT = "ticketContact";
  public final static String CATEGORY_LOOKUP = "ticketCategoryLookup";
  public final static String SUBCATEGORY1_LOOKUP = "ticketSubCategory1Lookup";
  public final static String SUBCATEGORY2_LOOKUP = "ticketSubCategory2Lookup";
  public final static String SUBCATEGORY3_LOOKUP = "ticketSubCategory3Lookup";
  public final static String SEVERITY_LOOKUP = "ticketSeverityLookup";
  public final static String PRIORITY_LOOKUP = "ticketPriorityLookup";
  public final static String ENTERED_BY_CONTACT = "ticketEnteredByContact";
  public final static String MODIFIED_BY_CONTACT = "ticketModifiedByContact";
  public final static String ASSIGNED_TO_CONTACT = "ticketAssignedToContact";
  public final static String GROUP_USERS = "ticketGroupUsers";
  public final static String TICKET_CATEGORIES = "ticketCategories";
  public final static String ENTERED_DATE = "process.ticketEnteredDate";
  public final static String PROJECT_ID = "projectTicket.id";


  /**
   * Gets the description attribute of the LoadTicketDetails object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Load all ticket information for use in other steps";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    Ticket thisTicket = (Ticket) context.getThisObject();
    Ticket previousTicket = (Ticket) context.getPreviousObject();
    Connection db = null;
    try {
      db = getConnection(context);
      context.setAttribute(ENTERED_DATE, thisTicket.getEnteredString(DateFormat.LONG, DateFormat.SHORT));
      if (thisTicket.getOrgId() > -1) {
        Organization organization = new Organization(
            db, thisTicket.getOrgId());
        context.setAttribute(ORGANIZATION, organization);
      } else {
        context.setAttribute(ORGANIZATION, new Organization());
      }
      int projectId = thisTicket.getProjectIdByTicket(db);
      if (projectId > -1) {
        context.setAttribute(PROJECT_ID, String.valueOf(projectId));
      }
      if (thisTicket.getAssignedTo() > 0) {
        User user = new User();
        user.setBuildContact(true);
        user.buildRecord(db, thisTicket.getAssignedTo());
        context.setAttribute(ASSIGNED_TO_CONTACT, user.getContact());
      } else {
        context.setAttribute(ASSIGNED_TO_CONTACT, new Contact());
      }
      if (thisTicket.getContactId() > 0) {
        Contact contact = new Contact(db, thisTicket.getContactId());
        context.setAttribute(CONTACT, contact);
      } else {
        context.setAttribute(CONTACT, new Contact());
      }
      if (thisTicket.getCatCode() > 0) {
        TicketCategory categoryLookup = new TicketCategory(
            db, thisTicket.getCatCode());
        context.setAttribute(CATEGORY_LOOKUP, categoryLookup);
      } else {
        context.setAttribute(CATEGORY_LOOKUP, new TicketCategory());
      }
      if (thisTicket.getSubCat1() > 0) {
        TicketCategory subCategory1Lookup = new TicketCategory(
            db, thisTicket.getSubCat1());
        context.setAttribute(SUBCATEGORY1_LOOKUP, subCategory1Lookup);
      } else {
        TicketCategory temp = new TicketCategory();
        context.setAttribute(SUBCATEGORY1_LOOKUP, temp);
      }
      if (thisTicket.getSubCat2() > 0) {
        TicketCategory subCategory2Lookup = new TicketCategory(
            db, thisTicket.getSubCat2());
        context.setAttribute(SUBCATEGORY2_LOOKUP, subCategory2Lookup);
      } else {
        TicketCategory temp = new TicketCategory();
        context.setAttribute(SUBCATEGORY2_LOOKUP, temp);
      }
      if (thisTicket.getSubCat3() > 0) {
        TicketCategory subCategory3Lookup = new TicketCategory(
            db, thisTicket.getSubCat3());
        context.setAttribute(SUBCATEGORY3_LOOKUP, subCategory3Lookup);
      } else {
        TicketCategory temp = new TicketCategory();
        context.setAttribute(SUBCATEGORY3_LOOKUP, temp);
      }
      if (thisTicket.getSeverityCode() > 0) {
        LookupElement severityLookup = new LookupElement(
            db, thisTicket.getSeverityCode(), "ticket_severity");
        context.setAttribute(SEVERITY_LOOKUP, severityLookup);
      } else {
        context.setAttribute(SEVERITY_LOOKUP, new LookupElement());
      }
      if (thisTicket.getPriorityCode() > 0) {
        LookupElement priorityLookup = new LookupElement(
            db, thisTicket.getPriorityCode(), "ticket_priority");
        context.setAttribute(PRIORITY_LOOKUP, priorityLookup);
      } else {
        LookupElement priorityLookup = new LookupElement();
        context.setAttribute(PRIORITY_LOOKUP, priorityLookup);
      }
      if (thisTicket.getModifiedBy() > 0) {
        User user = new User(db, thisTicket.getModifiedBy());
        Contact contact = new Contact(db, user.getContactId());
        context.setAttribute(MODIFIED_BY_CONTACT, contact);
      } else {
        context.setAttribute(MODIFIED_BY_CONTACT, new Contact());
      }
      if (thisTicket.getEnteredBy() > 0) {
        User user = null;
        if (previousTicket != null) {
          user = new User(db, previousTicket.getEnteredBy());
        } else {
          user = new User(db, thisTicket.getEnteredBy());
        }
        Contact contact = new Contact(db, user.getContactId());
        context.setAttribute(ENTERED_BY_CONTACT, contact);
      } else {
        context.setAttribute(ENTERED_BY_CONTACT, new Contact());
      }
      result = true;
    } catch (Exception e) {

    } finally {
      freeConnection(context, db);
    }
    return result;
  }
}

