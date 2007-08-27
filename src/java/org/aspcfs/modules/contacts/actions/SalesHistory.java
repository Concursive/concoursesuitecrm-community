/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.contacts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.contacts.base.ContactHistoryList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author yury.andreev
 * @version $Id: SalesHistory.java,v 1.1.2.2 2005/06/01 18:09:24
 *          yury.andreev Exp $
 * @created June 27, 2007
 */
public final class SalesHistory extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandList(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "sales-leads-history-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ContactsHistory", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Contact newContact = null;
    String contactId = context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    PagedListInfo contactHistoryListInfo = this.getPagedListInfo(
        context, "contactHistoryListInfo");
    contactHistoryListInfo.setLink(
        "SalesHistory.do?command=List&contactId=" + contactId + RequestUtils.addLinkParams(
            context.getRequest(), "popup|popupType|actionId|from|listForm"));

    ContactHistoryList historyList = new ContactHistoryList();
    historyList.setPagedListInfo(contactHistoryListInfo);
    contactHistoryListInfo.setSearchCriteria(historyList, context);
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    Connection db = null;
    try {

      db = this.getConnection(context);
      newContact = new Contact(db, Integer.parseInt(contactId));
      if (!isRecordAccessPermitted(context, newContact)){
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", newContact);
      historyList.setContactId(newContact.getId());
      if (contactHistoryListInfo.getSavedCriteria().size() == 0) {
        setDefaultFilters(context, historyList);
      }
      //build the history for the organization
      historyList.buildList(db);
      context.getRequest().setAttribute("historyList", historyList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "List");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddNote(ActionContext context) {
    if (!hasPermission(context, "sales-leads-history-add")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ContactsHistory", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Contact newContact = null;
    ContactHistory history = null;
    String contactId = context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      newContact = new Contact(db, Integer.parseInt(contactId));
      if (!isRecordAccessPermitted(context, newContact)){
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", newContact);
      history = new ContactHistory();
      context.getRequest().setAttribute("contactHistory", history);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AddNoteOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveNote(ActionContext context) {
    addModuleBean(context, "ContactsHistory", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Contact newContact = null;
    String contactId = context.getRequest().getParameter("contactId");
    ContactHistory contactHistory = (ContactHistory) context.getFormBean();
    if (contactHistory.getId() != -1) {
      if (!hasPermission(context, "contacts-external_contacts-history-edit")) {
        return ("PermissionError");
      }
    } else {
      if (!hasPermission(context, "contacts-external_contacts-history-add")) {
        return ("PermissionError");
      }
    }
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    boolean isValid = false;
    boolean recordInserted = false;
    int resultCount = -1;
    Connection db = null;
    try {
      db = this.getConnection(context);
      newContact = new Contact(db, Integer.parseInt(contactId));
      if (!isRecordAccessPermitted(context, newContact)){
        return ("PermissionError");
      }
      contactHistory.setEnteredBy(this.getUserId(context));
      contactHistory.setModifiedBy(this.getUserId(context));
      isValid = this.validateObject(context, db, contactHistory);
      if (isValid) {
        if (contactHistory.getId() != -1) {
          resultCount = contactHistory.update(db);
        } else {
          recordInserted = contactHistory.insert(db);
        }
      }
      if (!recordInserted && resultCount == -1) {
        context.getRequest().setAttribute("ContactDetails", newContact);
        context.getRequest().setAttribute("contactHistory", contactHistory);
        return "AddNoteOK";
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "SaveNoteOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyNote(ActionContext context) {
    if (!hasPermission(context, "sales-leads-history-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ContactsHistory", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Contact newContact = null;
    ContactHistory contactHistory = null;
    String contactId = context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    String id = context.getRequest().getParameter("id");
    if (id == null) {
      id = (String) context.getRequest().getAttribute("id");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      newContact = new Contact(db, Integer.parseInt(contactId));
      if (!isRecordAccessPermitted(context, newContact)){
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", newContact);
      contactHistory = new ContactHistory(db, Integer.parseInt(id));
      context.getRequest().setAttribute("contactHistory", contactHistory);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AddNoteOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteNote(ActionContext context) {
    if (!hasPermission(context, "sales-leads-history-delete")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ContactsHistory", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Contact newContact = null;
    ContactHistory contactHistory = null;
    String contactId = context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    String id = context.getRequest().getParameter("id");
    if (id == null) {
      id = (String) context.getRequest().getAttribute("id");
    }
    boolean result = false;
    Connection db = null;
    try {
      db = this.getConnection(context);
      newContact = new Contact(db, Integer.parseInt(contactId));
      if (!isRecordAccessPermitted(context, newContact)){
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", newContact);
      contactHistory = new ContactHistory(db, Integer.parseInt(id));
      result = contactHistory.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandList(context);
  }


  /**
   * Sets the defaultFilters attribute of the ExternalContactsHistory object
   *
   * @param context The new defaultFilters value
   * @param list    The new defaultFilters value
   */
  private void setDefaultFilters(ActionContext context, ContactHistoryList list) {
    if (hasPermission(context, "sales-leads-history-view")) {
      list.setNotes(true);
    }
    if (hasPermission(context, "sales-leads-calls-view") || hasPermission(
        context, "sales-leads-calls-edit")) {
      list.setActivities(true);
    }
    if (hasPermission(context, "sales-leads-messages-view") || hasPermission(
        context, "myhomepage-inbox-view")) {
      list.setEmail(true);
    }
    if (hasPermission(context, "quotes-quotes-view") || hasPermission(
        context, "quotes-quotes-edit")) {
      list.setQuotes(true);
    }
    if (hasPermission(
        context, "sales-leads-opportunities-view") || hasPermission(
            context, "sales-leads-opportunities-edit")) {
      list.setOpportunities(true);
    }
    if (hasPermission(context, "accounts-service-contracts-view") || hasPermission(
        context, "accounts-service-contracts-edit")) {
      list.setServiceContracts(true);
    }
    if (hasPermission(context, "tickets-tickets-view") || hasPermission(
        context, "tickets-tickets-edit")) {
      list.setTickets(true);
    }
    if (hasPermission(context, "myhomepage-tasks-view") || hasPermission(
        context, "myhomepage-tasks-edit")) {
      list.setTasks(true);
    }
    if (hasPermission(context, "accounts-assets-edit") || hasPermission(
        context, "accounts-assets-view")) {
      list.setAssets(true);
    }
  }
}

