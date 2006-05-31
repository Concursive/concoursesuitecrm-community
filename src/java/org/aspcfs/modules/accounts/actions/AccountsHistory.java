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
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created May 27, 2005
 */
public final class AccountsHistory extends CFSModule {

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
    if (!hasPermission(context, "accounts-accounts-history-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "AccountsHistory", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Organization newOrg = null;
    String orgid = context.getRequest().getParameter("orgId");
    if (orgid == null) {
      orgid = (String) context.getRequest().getAttribute("orgId");
    }
    PagedListInfo historyListInfo = this.getPagedListInfo(
        context, "orgHistoryListInfo");
    historyListInfo.setLink("AccountsHistory.do?command=View&orgId=" + orgid);

    OrganizationHistoryList historyList = new OrganizationHistoryList();
    historyList.setPagedListInfo(historyListInfo);
    historyListInfo.setSearchCriteria(historyList, context);

    ContactList contacts = new ContactList();

    Connection db = null;
    try {
      db = this.getConnection(context);
      newOrg = new Organization(db, Integer.parseInt(orgid));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("OrgDetails", newOrg);
      //Build the list of contacts
      contacts.setOrgId(orgid);
      contacts.setShowTrashedAndNormal(true);
      contacts.setIncludeEnabled(Constants.UNDEFINED);
      //contacts.setShowTrashedAndNormal(true);
      if (historyListInfo.getSavedCriteria().size() == 0) {
        setDefaultFilters(context, historyList);
      }
      contacts.buildList(db);
      context.getRequest().setAttribute(
          "contacts", contacts.getHashMapOfContacts());
      //Build the history for the organization
      historyList.setOrgId(newOrg.getOrgId());
      historyList.buildList(db);
      context.getRequest().setAttribute("historyList", historyList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ListOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddNote(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-history-add")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Account History", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Organization newOrg = null;
    OrganizationHistory history = null;
    String orgId = context.getRequest().getParameter("orgId");
    if (orgId == null) {
      orgId = (String) context.getRequest().getAttribute("orgId");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      newOrg = new Organization(db, Integer.parseInt(orgId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("OrgDetails", newOrg);
      history = new OrganizationHistory();
      context.getRequest().setAttribute("history", history);
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
    String source = (String) context.getRequest().getParameter("source");
    Organization newOrg = null;
    String orgId = context.getRequest().getParameter("orgId");
    if (orgId == null) {
      orgId = (String) context.getRequest().getAttribute("orgId");
    }
    OrganizationHistory history = (OrganizationHistory) context.getFormBean();
    if (history.getId() != -1) {
      if (!hasPermission(context, "accounts-accounts-history-edit")) {
        return ("PermissionError");
      }
    } else {
      if (!hasPermission(context, "accounts-accounts-history-add")) {
        return ("PermissionError");
      }
    }
    boolean isValid = false;
    boolean recordInserted = false;
    int resultCount = -1;
    Connection db = null;
    try {
      db = this.getConnection(context);
      newOrg = new Organization(db, Integer.parseInt(orgId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
        return ("PermissionError");
      }
      history.setEnteredBy(this.getUserId(context));
      history.setModifiedBy(this.getUserId(context));
      isValid = this.validateObject(context, db, history);
      if (isValid) {
        if (history.getId() != -1) {
          resultCount = history.update(db);
        } else {
          recordInserted = history.insert(db);
        }
      }
      if (!recordInserted && resultCount == -1) {
        context.getRequest().setAttribute("OrgDetails", newOrg);
        context.getRequest().setAttribute("history", history);
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
    if (!hasPermission(context, "accounts-accounts-history-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Organization History", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Organization newOrg = null;
    OrganizationHistory history = null;
    String orgId = context.getRequest().getParameter("orgId");
    if (orgId == null) {
      orgId = (String) context.getRequest().getAttribute("orgId");
    }
    String id = context.getRequest().getParameter("id");
    if (id == null) {
      id = (String) context.getRequest().getAttribute("id");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      newOrg = new Organization(db, Integer.parseInt(orgId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("OrgDetails", newOrg);
      history = new OrganizationHistory(db, Integer.parseInt(id));
      context.getRequest().setAttribute("history", history);
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
    if (!hasPermission(context, "accounts-accounts-history-delete")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Organization History", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Organization newOrg = null;
    OrganizationHistory history = null;
    String orgId = context.getRequest().getParameter("orgId");
    if (orgId == null) {
      orgId = (String) context.getRequest().getAttribute("orgId");
    }
    String id = context.getRequest().getParameter("id");
    if (id == null) {
      id = (String) context.getRequest().getAttribute("id");
    }
    boolean result = false;
    Connection db = null;
    try {
      db = this.getConnection(context);
      newOrg = new Organization(db, Integer.parseInt(orgId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("OrgDetails", newOrg);
      history = new OrganizationHistory(db, Integer.parseInt(id));
      result = history.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandView(context);
  }

  private void setDefaultFilters(ActionContext context, OrganizationHistoryList list) {
    if (hasPermission(context, "accounts-accounts-view")) {
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
    if (hasPermission(context, "accounts-accounts-documents-view") || hasPermission(
        context, "accounts-accounts-documents-edit")) {
      list.setDocuments(true);
    }
    if (hasPermission(context, "accounts-quotes-view") || hasPermission(
        context, "accounts-quotes-edit")) {
      list.setQuotes(true);
    }
    if (hasPermission(context, "accounts-accounts-opportunities-view") || hasPermission(
        context, "accounts-accounts-opportunities-edit")) {
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
    if (hasPermission(context, "accounts-accounts-relationships-view")) {
      list.setRelationships(true);
    }
    if (hasPermission(context, "accounts-assets-edit") || hasPermission(
        context, "accounts-assets-view")) {
      list.setAssets(true);
    }
  }
}

