/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.actionlist.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.actionlist.base.ActionContactsList;
import org.aspcfs.modules.actionlist.base.ActionList;
import org.aspcfs.modules.actionlist.base.ActionLists;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.communications.base.SearchCriteriaList;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     Mathur
 * @created    April 18, 2003
 * @version    $id:exp$
 */
public final class MyActionLists extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-view"))) {
      return ("PermissionError");
    }
    return executeCommandList(context);
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "My Action Lists", "Action Lists");
    String linkModuleId = context.getRequest().getParameter("linkModuleId");
    String userId = (String) context.getRequest().getParameter("viewUserId");
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (userId != null && !"".equals(userId)) {
      if (!this.hasAuthority(context, Integer.parseInt(userId))) {
        return ("PermissionError");
      }
      if (!userId.equals("" + this.getUserId(context))) {
        context.getSession().setAttribute("viewUserId", userId);
        viewUserId = userId;
      } else {
        context.getSession().removeAttribute("viewUserId");
        viewUserId = "" + this.getUserId(context);
      }
    } else if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = "" + this.getUserId(context);
    }
    PagedListInfo actionListInfo = this.getPagedListInfo(
        context, "ActionListInfo");
    actionListInfo.setLink(
        "MyActionLists.do?command=List&linkModuleId=" + linkModuleId + "&viewUserId=" + viewUserId);
    Connection db = null;
    try {
      db = getConnection(context);
      ActionLists thisList = new ActionLists();
      thisList.setPagedListInfo(actionListInfo);
      thisList.setLinkModuleId(Integer.parseInt(linkModuleId));
      thisList.setBuildDetails(true);
      thisList.setOwner(Integer.parseInt(viewUserId));
      if (!"all".equals(actionListInfo.getListView())) {
        if ("complete".equals(actionListInfo.getListView())) {
          thisList.setCompleteOnly(true);
        } else {
          thisList.setInProgressOnly(true);
        }
      }
      thisList.buildList(db);
      context.getRequest().setAttribute("ActionLists", thisList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "ListOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "My Action Lists", "");
    String actionId = context.getRequest().getParameter("id");
    Connection db = null;
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = "" + this.getUserId(context);
    }

    ActionList thisList = (ActionList) context.getFormBean();
    try {
      db = getConnection(context);
      thisList.queryRecord(db, Integer.parseInt(actionId));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "DetailsOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-add"))) {
      return ("PermissionError");
    }
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = "" + this.getUserId(context);
    }
    context.getRequest().setAttribute("changeSite","true");
    addModuleBean(context, "My Action Lists", "Add an Action List");
    return "PrepareAddOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "My Action Lists", "");
    String actionId = context.getRequest().getParameter("id");
    Connection db = null;
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = "" + this.getUserId(context);
    }

    ActionList thisList = (ActionList) context.getFormBean();
    try {
      db = getConnection(context);
      thisList.queryRecord(db, Integer.parseInt(actionId));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "PrepareModifyOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-add"))) {
      return ("PermissionError");
    }
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = "" + this.getUserId(context);
    }
    boolean recordInserted = false;
    int resultCount = 0;
    boolean isValid = false;
    HashMap errors = new HashMap();
    addModuleBean(context, "My Action Lists", "");

    ActionList thisList = (ActionList) context.getFormBean();
    thisList.setModifiedBy(this.getUserId(context));
    String action = (thisList.getId() > 0 ? "modify" : "insert");
    String criteria = context.getRequest().getParameter("searchCriteriaText");

    Connection db = null;
    try {
      db = getConnection(context);
      if ("insert".equals(action)) {
        if (criteria != null && !"".equals(criteria)) {
          thisList.setEnteredBy(this.getUserId(context));
          isValid = this.validateObject(context, db, thisList);
          if (isValid) {
            recordInserted = thisList.insert(db);
          }
          //if list was new and saved correctly insert contacts (TODO: after implementing commit across requests //MyActionContacts can be used for this
          if (recordInserted) {
            SearchCriteriaList thisSCL = null;
            //The criteria that makes up the contact list query
            thisSCL = new SearchCriteriaList(criteria);
            thisSCL.setGroupName("Action List");
            thisSCL.setEnteredBy(getUserId(context));
            thisSCL.setModifiedBy(getUserId(context));
            thisSCL.setOwner(getUserId(context));
            thisSCL.buildRelatedResources(db);

            //Build the contactList
            ContactList contacts = new ContactList();
            contacts.setScl(
                thisSCL, this.getUserId(context), this.getUserRange(context));
            contacts.setBuildDetails(true);
            contacts.setBuildTypes(false);
            AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.GENERAL_CONTACTS);
            contacts.setGeneralContactAccessTypes(accessTypeList);
            contacts.buildList(db);

            //save action contacts
            ActionContactsList contactsList = new ActionContactsList();
            contactsList.setActionId(thisList.getId());
            contactsList.setEnteredBy(this.getUserId(context));
            contactsList.insert(db, contacts);
          }
        } else {
          SystemStatus systemStatus = this.getSystemStatus(context);
          errors.put(
              "criteriaError", systemStatus.getLabel(
              "object.validation.criteriaNotDefined"));
          processErrors(context, errors);
        }
      } else {
        isValid = this.validateObject(context, db, thisList);
        if (isValid) {
          resultCount = thisList.update(db);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted) {
      return "InsertOK";
    } else if (resultCount == 1) {
      return (executeCommandList(context));
    }
    return (executeCommandAdd(context));
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-delete"))) {
      return ("PermissionError");
    }
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = "" + this.getUserId(context);
    }
    Connection db = null;
    addModuleBean(context, "My Action Lists", "");

    String actionId = context.getRequest().getParameter("id");
    String linkModuleId = context.getRequest().getParameter("linkModuleId");
    HtmlDialog htmlDialog = new HtmlDialog();
    ActionList thisList = (ActionList) context.getFormBean();

    try {
      db = getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisList.queryRecord(db, Integer.parseInt(actionId));
      DependencyList dependencies = thisList.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));

      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl(
            "javascript:window.location.href='MyActionLists.do?command=Delete&id=" + actionId + "&linkModuleId=" + linkModuleId + "'");
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='MyActionLists.do?command=Delete&id=" + actionId + "&linkModuleId=" + linkModuleId + "'");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return "ConfirmDeleteOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-delete"))) {
      return ("PermissionError");
    }
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = "" + this.getUserId(context);
    }
    Exception errorMessage = null;
    addModuleBean(context, "My Action Lists", "");
    String actionId = context.getRequest().getParameter("id");
    String linkModuleId = context.getRequest().getParameter("linkModuleId");
    Connection db = null;

    ActionList thisList = (ActionList) context.getFormBean();
    try {
      db = getConnection(context);
      thisList.queryRecord(db, Integer.parseInt(actionId));
      thisList.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute(
          "refreshUrl", "MyActionLists.do?command=List&linkModuleId=" + linkModuleId);
      return "DeleteOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandGetSiteForUser(ActionContext context) {
    String userId = (String) context.getRequest().getParameter("userId");
    String item = context.getRequest().getParameter("item");
    try {
      User user = this.getUser(context, Integer.parseInt(userId));
      context.getRequest().setAttribute("siteId", String.valueOf(user.getSiteId()));
      context.getRequest().setAttribute("item", item);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    }
    return "GetSiteForUserOK";
  }
}

