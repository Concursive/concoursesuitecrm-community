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
import java.sql.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.pipeline.beans.OpportunityBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.controller.SystemStatus;

/**
 *  Represents Opportunities for Account Contacts
 *
 *@author     akhi_m
 *@created    April 23, 2003
 *@version    $Id$
 */
public final class AccountContactsOpps extends CFSModule {

  /**
   *  View Opportunities
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewOpps(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-view")) {
      return ("PermissionError");
    }

    String contactId = context.getRequest().getParameter("contactId");
    addModuleBean(context, "View Accounts", "View Opportunities");

    PagedListInfo oppPagedListInfo = this.getPagedListInfo(context, "AccountContactOppsPagedListInfo");
    oppPagedListInfo.setLink("AccountContactsOpps.do?command=ViewOpps&contactId=" + contactId + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));

    Connection db = null;
    OpportunityHeaderList oppList = new OpportunityHeaderList();
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
      //add account and contact to the request
      thisContact = addFormElements(context, db);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }

      oppList.setPagedListInfo(oppPagedListInfo);
      oppList.setBuildTotalValues(true);
      oppList.setContactId(contactId);
      if ("all".equals(oppPagedListInfo.getListView())) {
        oppList.setOwnerIdRange(this.getUserRange(context));
        oppList.setQueryOpenOnly(true);
      } else if ("closed".equals(oppPagedListInfo.getListView())) {
        oppList.setOwnerIdRange(this.getUserRange(context));
        oppList.setQueryClosedOnly(true);
      } else {
        oppList.setOwner(this.getUserId(context));
        oppList.setQueryOpenOnly(true);
      }
      oppList.buildList(db);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    context.getRequest().setAttribute("OpportunityHeaderList", oppList);
    return getReturn(context, "ListOpps");
  }


  /**
   * Prepare the form
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    String id = context.getRequest().getParameter("headerId");
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = thisUser.getUserRecord();
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setExcludeExpiredIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);
    if (id != null && !"-1".equals(id)) {
      if (!hasPermission(context, "accounts-accounts-contacts-opportunities-add")) {
        return ("PermissionError");
      }
    }
    try {
      db = this.getConnection(context);

      //check if a header needs to be built.
      if (id != null && !"-1".equals(id)) {
        //Build the container item
        OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(id));
        context.getRequest().setAttribute("opportunityHeader", oppHeader);
      }

      //add account and contact to the request
      addFormElements(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "PrepareOppOK";
  }



  /**
   *  Save the Opportunity
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-add")) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    boolean isValid = false;
    OpportunityBean newOpp = (OpportunityBean) context.getRequest().getAttribute("OppDetails");
    String contactId = context.getRequest().getParameter("contactId");

    //set types
    newOpp.getComponent().setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOpp.getComponent().setEnteredBy(getUserId(context));
    newOpp.getComponent().setModifiedBy(getUserId(context));
    newOpp.getHeader().setEnteredBy(getUserId(context));
    newOpp.getHeader().setModifiedBy(getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);
      isValid = this.validateObject(context, db, newOpp.getHeader());
      isValid = this.validateObject(context, db, newOpp.getComponent()) && isValid;
      if (isValid) {
        recordInserted = newOpp.insert(db, context);
      }
      if (!recordInserted) {
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute("TypeList", newOpp.getComponent().getTypeList());
      }

      //add account and contact to the request
      addFormElements(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if (recordInserted) {
      addRecentItem(context, newOpp.getHeader());
      context.getRequest().setAttribute("headerId", String.valueOf(newOpp.getHeader().getId()));
      return (executeCommandDetailsOpp(context));
    }
    return (executeCommandPrepare(context));
  }


  /**
   *  Update the Opportunity
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateOpp(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    boolean isValid = false;
    String headerId = context.getRequest().getParameter("headerId");

    try {
      db = this.getConnection(context);
      OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(headerId));
      if (!hasAuthority(context, oppHeader.getEnteredBy())) {
        return "PermissionError";
      }
      oppHeader.setModifiedBy(getUserId(context));
      oppHeader.setDescription(context.getRequest().getParameter("description"));
      isValid = this.validateObject(context, db, oppHeader);
      if (isValid) {
        resultCount = oppHeader.update(db);
      }

      //add account and contact to the request
      addFormElements(context, db);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if (resultCount == 1) {
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return (executeCommandViewOpps(context));
      } else {
        return (executeCommandDetailsOpp(context));
      }
    } else {
      if (resultCount == -1 || !isValid) {
        return executeCommandModifyOpp(context);
      }
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveComponent(ActionContext context) {
    boolean recordInserted = false;
    int resultCount = 0;
    boolean isValid = false;
    String permission = "accounts-accounts-contacts-opportunities-add";

    OpportunityComponent newComponent = (OpportunityComponent) context.getFormBean();
    newComponent.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newComponent.setEnteredBy(getUserId(context));
    newComponent.setModifiedBy(getUserId(context));

    String action = (newComponent.getId() > 0 ? "modify" : "insert");
    String contactId = context.getRequest().getParameter("contactId");

    if ("modify".equals(action)) {
      permission = "accounts-accounts-contacts-opportunities-edit";
    }
    if (!hasPermission(context, permission)) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);

      Contact thisContact = addFormElements(context, db);
      if (newComponent.getId() > 0) {
        newComponent.setModifiedBy(getUserId(context));
        if (!(hasAuthority(db, context, thisContact) || hasAuthority(context, newComponent.getOwner()))) {
          return "PermissionError";
        }
        isValid = this.validateObject(context, db, newComponent);
        if (isValid) {
          resultCount = newComponent.update(db, context);
        }
      } else {
        if (!hasAuthority(db, context, thisContact)) {
          return ("PermissionError");
        }
        isValid = this.validateObject(context, db, newComponent);
        if (isValid) {
          recordInserted = newComponent.insert(db, context);
        }
      }
      if (recordInserted) {
        addRecentItem(context, newComponent);
      } else if (resultCount == 1) {
        newComponent.queryRecord(db, newComponent.getId());
      } else {
        //rebuild the form
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute("TypeList", newComponent.getTypeList());
        if ("modify".equals(action) && resultCount == -1) {
          UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
          User thisRec = thisUser.getUserRecord();
          UserList shortChildList = thisRec.getShortChildList();
          UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
          userList.setMyId(getUserId(context));
          userList.setMyValue(thisUser.getContact().getNameLastFirst());
          userList.setIncludeMe(true);
          userList.setExcludeDisabledIfUnselected(true);
          userList.setExcludeExpiredIfUnselected(true);
          context.getRequest().setAttribute("UserList", userList);
        }
      }
      context.getRequest().setAttribute("ComponentDetails", newComponent);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if ("insert".equals(action)) {
      if (recordInserted) {
        return (executeCommandDetailsOpp(context));
      } else {
        return executeCommandPrepare(context);
      }
    } else {
      if (resultCount == 1) {
        if ("list".equals(context.getRequest().getParameter("return"))) {
          return (executeCommandDetailsOpp(context));
        } else {
          return executeCommandDetailsComponent(context);
        }
      } else {
        if (resultCount == -1 || !isValid) {
          return executeCommandPrepare(context);
        }
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    }
  }


  /**
   *  Opportunity Details
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetailsOpp(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-view")) {
      return ("PermissionError");
    }
    int headerId = -1;
    addModuleBean(context, "View Accounts", "Opportunities");
    if (context.getRequest().getParameter("headerId") != null) {
      headerId = Integer.parseInt(context.getRequest().getParameter("headerId"));
    } else {
      headerId = Integer.parseInt((String) context.getRequest().getAttribute("headerId"));
    }
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    OpportunityHeader thisHeader = null;
    OpportunityComponentList componentList = null;

    PagedListInfo componentListInfo = this.getPagedListInfo(context, "AccountContactComponentListInfo");
    componentListInfo.setLink("AccountContactsOpps.do?command=DetailsOpp&headerId=" + headerId + "&contactId=" + contactId);

    try {
      db = this.getConnection(context);
      Contact thisContact = addFormElements(context, db);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }

      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("OpportunityHeader", thisHeader);

      componentList = new OpportunityComponentList();
      componentList.setPagedListInfo(componentListInfo);
      componentList.setOwnerIdRange(this.getUserRange(context));
      componentList.setHeaderId(thisHeader.getId());
      componentList.buildList(db);
      context.getRequest().setAttribute("ComponentList", componentList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    addRecentItem(context, thisHeader);
    return getReturn(context, "DetailsOpp");
  }


  /**
   *  Modify Opportunity
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyOpp(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Opportunities");
    int headerId = Integer.parseInt(context.getRequest().getParameter("headerId"));
    Connection db = null;
    OpportunityHeader thisHeader = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = addFormElements(context, db);
      if (!hasAuthority(db, context, thisContact)) {
        return "PermissionError";
      }
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("OpportunityHeader", thisHeader);
    addRecentItem(context, thisHeader);
    return getReturn(context, "ModifyOpp");
  }


  /**
   *  Component Details
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetailsComponent(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-view")) {
      return ("PermissionError");
    }
    OpportunityComponent thisComponent = null;
    Contact thisContact = null;
    Connection db = null;
    addModuleBean(context, "View Accounts", "Opportunities");
    //Parameters
    String componentId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    try {
      db = this.getConnection(context);
      //Add account and contact
      thisContact = addFormElements(context, db);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      //Load the component
      thisComponent = new OpportunityComponent(db, Integer.parseInt(componentId));
      if (!(hasAuthority(db, context, thisContact) || hasAuthority(context, thisComponent.getOwner()))) {
        return "PermissionError";
      }
      thisComponent.checkEnabledOwnerAccount(db);
      context.getRequest().setAttribute("OppComponentDetails", thisComponent);
      //Load the opportunity header for display
      OpportunityHeader oppHeader = new OpportunityHeader(db, thisComponent.getHeaderId());
      context.getRequest().setAttribute("OpportunityHeader", oppHeader);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!(hasAuthority(context, thisContact.getOwner()) || hasAuthority(context, thisComponent.getOwner()))) {
      return "PermissionError";
    }
    addRecentItem(context, thisComponent);
    return getReturn(context, "DetailsComponent");
  }


  /**
   *  Confirm Deletion of an Opportunity
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-delete")) {
      return ("PermissionError");
    }

    HtmlDialog htmlDialog = new HtmlDialog();
    String headerId = context.getRequest().getParameter("headerId");
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      Contact thisContact = addFormElements(context, db);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      OpportunityHeader thisOpp = new OpportunityHeader(db, headerId);
      DependencyList dependencies = thisOpp.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution")+"\n"+dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      htmlDialog.addButton(systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='AccountContactsOpps.do?command=DeleteOpp&contactId=" + contactId + "&id=" + headerId + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
      htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Confirm Deletion of a Opportunity Component
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmComponentDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-delete")) {
      return ("PermissionError");
    }

    OpportunityComponent thisComponent = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = addFormElements(context, db);
      thisComponent = new OpportunityComponent(db, id);
      if (!(hasAuthority(db, context, thisContact) || hasAuthority(context, thisComponent.getOwner()))) {
        return "PermissionError";
      }
      SystemStatus systemStatus = this.getSystemStatus(context);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.contact.opps.delete"));
      htmlDialog.setShowAndConfirm(false);
      htmlDialog.setDeleteUrl("javascript:window.location.href='AccountContactsOppComponents.do?command=DeleteComponent&contactId=" + contactId + "&id=" + id + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
      htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Delete an Opportunity
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDeleteOpp(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-delete")) {
      return ("PermissionError");
    }

    boolean recordDeleted = false;
    String contactId = context.getRequest().getParameter("contactId");
    OpportunityHeader newOpp = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = addFormElements(context, db);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      newOpp = new OpportunityHeader(db, context.getRequest().getParameter("id"));
      recordDeleted = newOpp.delete(db, context, this.getPath(context, "opportunities"));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    boolean inLinePopup = "inline".equals(context.getRequest().getParameter("popupType"));
    if (recordDeleted) {
      context.getRequest().setAttribute("contactId", contactId);
      context.getRequest().setAttribute("refreshUrl", "AccountContactsOpps.do?command=ViewOpps&contactId=" + contactId + RequestUtils.addLinkParams(context.getRequest(), "popupType|actionId" + (inLinePopup ? "|popup" : "")));
      deleteRecentItem(context, newOpp);
      return "OppDeleteOK";
    } else {
      processErrors(context, newOpp.getErrors());
      return (executeCommandViewOpps(context));
    }
  }


  /**
   *  Delete a Opportunity Component
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteComponent(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-delete")) {
      return ("PermissionError");
    }

    boolean recordDeleted = false;
    OpportunityComponent component = null;
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = addFormElements(context, db);
      component = new OpportunityComponent(db, context.getRequest().getParameter("id"));
      if (!(hasAuthority(db, context, thisContact) || hasAuthority(context, component.getOwner()))) {
        return "PermissionError";
      }
      recordDeleted = component.delete(db, context, this.getPath(context, "opportunities"));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    boolean inLinePopup = "inline".equals(context.getRequest().getParameter("popupType"));
    if (recordDeleted) {
      context.getRequest().setAttribute("refreshUrl", "AccountContactsOpps.do?command=DetailsOpp&headerId=" + component.getHeaderId() + "&contactId=" + contactId + RequestUtils.addLinkParams(context.getRequest(), "popupType|actionId" + (inLinePopup ? "|popup" : "")));
      deleteRecentItem(context, component);
      return ("ComponentDeleteOK");
    } else {
      processErrors(context, component.getErrors());
      return (executeCommandViewOpps(context));
    }
  }


  /**
   *  Modify a Opportunity Component
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyComponent(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    OpportunityComponent component = null;
    addModuleBean(context, "View Accounts", "Opportunities");

    String componentId = context.getRequest().getParameter("id");

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = thisUser.getUserRecord();
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setExcludeExpiredIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);

    try {
      db = this.getConnection(context);
      component = new OpportunityComponent(db, componentId);
      Contact thisContact = addFormElements(context, db);
      if (!(hasAuthority(db, context, thisContact) || !hasAuthority(context, component.getOwner()))) {
        return ("PermissionError");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ComponentDetails", component);
    addRecentItem(context, component);
    return executeCommandPrepare(context);
  }


  /**
   *  Adds a feature to the FormElements attribute of the AccountContactsOpps object
   *
   *@param  context           The feature to be added to the FormElements attribute
   *@param  db                The feature to be added to the FormElements attribute
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public Contact addFormElements(ActionContext context, Connection db) throws SQLException {
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = (Contact) context.getRequest().getAttribute("ContactDetails");
    Organization thisOrganization = null;

    if (thisContact == null) {
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    }
    if (thisContact.getOrgId() > -1) {
      thisOrganization = new Organization(db, thisContact.getOrgId());
    }
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return thisContact;
  }
}

