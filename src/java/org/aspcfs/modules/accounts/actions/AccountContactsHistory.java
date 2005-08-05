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
package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.contacts.base.ContactHistoryList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created May 27, 2005
 */
public final class AccountContactsHistory extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandView(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-history-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ContactsHistory", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Contact newContact = null;
    String contactId = context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    PagedListInfo accountContactHistoryListInfo = this.getPagedListInfo(
        context, "accountContactHistoryListInfo");
    accountContactHistoryListInfo.setLink(
        "AccountContactsHistory.do?command=View&contactId=" + contactId);

    ContactHistoryList historyList = new ContactHistoryList();
    historyList.setPagedListInfo(accountContactHistoryListInfo);
    accountContactHistoryListInfo.setSearchCriteria(historyList, context);

    Connection db = null;
    try {
      db = this.getConnection(context);
      newContact = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", newContact);
      historyList.setContactId(newContact.getId());
      if (accountContactHistoryListInfo.getSavedCriteria().size() == 0) {
        setDefaultFilters(context, historyList);
//        historyList.setDefaultFilters(true);
      }

      //build the history for the organization
      historyList.buildList(db);
      context.getRequest().setAttribute("historyList", historyList);

      Organization orgDetails = new Organization(db, newContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", orgDetails);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ListOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddNote(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-history-add")) {
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
      if (!hasPermission(context, "accounts-accounts-contacts-history-edit")) {
        return ("PermissionError");
      }
    } else {
      if (!hasPermission(context, "accounts-accounts-contacts-history-add")) {
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
    if (!hasPermission(context, "accounts-accounts-contacts-history-edit")) {
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

  public String executeCommandDeleteNote(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-history-delete")) {
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
    boolean result = false;
    try {
      db = this.getConnection(context);
      newContact = new Contact(db, Integer.parseInt(contactId));
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
    return executeCommandView(context);
  }

  private void setDefaultFilters(ActionContext context, ContactHistoryList list) {
    if (hasPermission(context, "accounts-accounts-contacts-view")) {
      list.setNotes(true);
    }
    if (hasPermission(context, "accounts-accounts-contacts-calls-edit") || hasPermission(
        context, "accounts-accounts-contacts-calls-view")) {
      list.setActivities(true);
    }
    if (hasPermission(context, "myhomepage-inbox-view") || hasPermission(
        context, "accounts-accounts-contacts-messages-view")) {
      list.setEmail(true);
    }
    if (hasPermission(context, "accounts-quotes-view") || hasPermission(
        context, "accounts-quotes-edit")) {
      list.setQuotes(true);
    }
    if (hasPermission(
        context, "accounts-accounts-contacts-opportunities-view") || hasPermission(
            context, "accounts-accounts-contacts-opportunities-edit")) {
      list.setOpportunities(true);
    }
    if (hasPermission(context, "accounts-service-contracts-view") || hasPermission(
        context, "accounts-service-contracts-edit")) {
      list.setServiceContracts(true);
    }
    if (hasPermission(context, "accounts-accounts-tickets-edit") || hasPermission(
        context, "accounts-accounts-tickets-view")) {
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

