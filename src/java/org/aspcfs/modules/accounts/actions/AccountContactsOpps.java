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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityComponentList;
import org.aspcfs.modules.pipeline.base.OpportunityComponentLog;
import org.aspcfs.modules.pipeline.base.OpportunityComponentLogList;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.pipeline.beans.OpportunityBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *  Represents Opportunities for Account Contacts
 *
 * @author akhi_m
 * @version $Id$
 * @created April 23, 2003
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
      AccessTypeList accessTypeList = null;
      accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);

      oppList.setPagedListInfo(oppPagedListInfo);
      oppList.setAllowMultipleComponents(allowMultiple(context));
      oppList.setContactId(contactId);
      oppList.setBuildTotalValues(true);
      if (oppPagedListInfo.getListView() == null ||
          "".equals(oppPagedListInfo.getListView())) {
        oppPagedListInfo.setListView("all");
      }
      if ("my".equals(oppPagedListInfo.getListView())) {
        oppList.setControlledHierarchyOnly(Constants.UNDEFINED);
        oppList.setOwner(this.getUserId(context));
        oppList.setQueryOpenOnly(true);
      } else if ("closed".equals(oppPagedListInfo.getListView())) {
        oppList.setControlledHierarchy(Constants.FALSE, this.getUserRange(context));
        oppList.setAccessType(accessTypeList.getCode(AccessType.PUBLIC));
        oppList.setQueryClosedOnly(true);
      } else {
        oppList.setControlledHierarchy(Constants.FALSE, this.getUserRange(context));
        oppList.setAccessType(accessTypeList.getCode(AccessType.PUBLIC));
        oppList.setQueryOpenOnly(true);
      }
      if (thisContact.isTrashed()) {
        oppList.setIncludeOnlyTrashed(true);
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
   *  Prepare the form
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    String id = context.getRequest().getParameter("headerId");
    if (id == null || "".equals(id)) {
      id = (String) context.getRequest().getAttribute("headerId");
    }
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = thisUser.getUserRecord();
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(
        shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setExcludeExpiredIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);
    SystemStatus systemStatus = this.getSystemStatus(context);
    if (id != null && !"-1".equals(id)) {
      if (!hasPermission(
          context, "accounts-accounts-contacts-opportunities-add")) {
        return ("PermissionError");
      }
    }
    try {
      db = this.getConnection(context);

      //check if a header needs to be built.
      if (id != null && !"-1".equals(id)) {
        //Build the container item
        OpportunityHeader oppHeader = new OpportunityHeader(
            db, Integer.parseInt(id));
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
    if (!hasPermission(
        context, "accounts-accounts-contacts-opportunities-add")) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    boolean isValid = false;
    OpportunityBean newOpp = (OpportunityBean) context.getRequest().getAttribute(
        "OppDetails");
    String contactId = context.getRequest().getParameter("contactId");
    SystemStatus systemStatus = this.getSystemStatus(context);

    //set types
    if (!systemStatus.hasField("opportunity.componentTypes")) {
      newOpp.getComponent().setTypeList(
          context.getRequest().getParameterValues("selectedList"));
    }
    newOpp.getComponent().setEnteredBy(getUserId(context));
    newOpp.getComponent().setModifiedBy(getUserId(context));
    newOpp.getHeader().setEnteredBy(getUserId(context));
    newOpp.getHeader().setModifiedBy(getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);
      LookupList environmentSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_environment");
      context.getRequest().setAttribute(
          "environmentSelect", environmentSelect);
      LookupList competitorsSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_competitors");
      context.getRequest().setAttribute(
          "competitorsSelect", competitorsSelect);
      LookupList compellingEventSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_event_compelling");
      context.getRequest().setAttribute(
          "compellingEventSelect", compellingEventSelect);
      LookupList budgetSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_budget");
      context.getRequest().setAttribute("budgetSelect", budgetSelect);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      isValid = this.validateObject(context, db, newOpp);
      isValid = this.validateObject(context, db, newOpp.getHeader()) && isValid;
      isValid = this.validateObject(context, db, newOpp.getComponent()) && isValid;
      if (isValid) {
        recordInserted = newOpp.insert(db, context);
      }
      if (recordInserted) {
        OpportunityHeader header = newOpp.getHeader();
        OpportunityComponent component = newOpp.getComponent();
        component.setContactId(header.getContactLink());
        component.setOrgId(header.getAccountLink());
        this.processInsertHook(context, component);
      } else {
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute(
            "TypeList", newOpp.getComponent().getTypeList());
      }
      context.getRequest().setAttribute("ComponentDetails", newOpp.getComponent());

      //add header details
      context.getRequest().setAttribute("OpportunityHeader", newOpp.getHeader());
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
      context.getRequest().setAttribute(
          "headerId", String.valueOf(newOpp.getHeader().getId()));
      context.getRequest().setAttribute("OpportunityDetails", newOpp);
      if ("true".equals(context.getRequest().getParameter("addQuote"))) {
        context.getRequest().setAttribute("contactId", contactId);
        return "AddQuoteOK";
      }
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
    if (!hasPermission(
        context, "accounts-accounts-contacts-opportunities-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    boolean isValid = false;
    String headerId = context.getRequest().getParameter("headerId");

    try {
      db = this.getConnection(context);
      OpportunityHeader oldHeader = new OpportunityHeader(
          db, Integer.parseInt(headerId));
      OpportunityHeader oppHeader = new OpportunityHeader(
          db, Integer.parseInt(headerId));
      if (!hasAuthority(context, oppHeader.getManager())) {
        return "PermissionError";
      }
      oppHeader.setModifiedBy(getUserId(context));
      oppHeader.setDescription(context.getRequest().getParameter("description"));
      isValid = this.validateObject(context, db, oppHeader);
      if (isValid) {
        resultCount = oppHeader.update(db);
      }
      if (resultCount == 1) {
        this.processUpdateHook(context, oldHeader, oppHeader);
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

    OpportunityBean bean = new OpportunityBean();
    OpportunityHeader header = null;
    OpportunityComponent oldComponent = null;
    OpportunityComponent newComponent = (OpportunityComponent) context.getFormBean();
    SystemStatus systemStatus = this.getSystemStatus(context);
    if (!systemStatus.hasField("opportunity.componentTypes")) {
      newComponent.setTypeList(
          context.getRequest().getParameterValues("selectedList"));
    }
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
      LookupList environmentSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_environment");
      context.getRequest().setAttribute(
          "environmentSelect", environmentSelect);
      LookupList competitorsSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_competitors");
      context.getRequest().setAttribute(
          "competitorsSelect", competitorsSelect);
      LookupList compellingEventSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_event_compelling");
      context.getRequest().setAttribute(
          "compellingEventSelect", compellingEventSelect);
      LookupList budgetSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_budget");
      context.getRequest().setAttribute("budgetSelect", budgetSelect);
      header = new OpportunityHeader(db, newComponent.getHeaderId());
      newComponent.setContactId(header.getContactLink());
      newComponent.setOrgId(header.getAccountLink());
      bean.setHeader(header);
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      Contact thisContact = addFormElements(context, db);
      if (newComponent.getId() > 0) {
        newComponent.setModifiedBy(getUserId(context));
        if (!hasAuthority(db, context, thisContact)) {
          return "PermissionError";
        }
        if (!(hasAuthority(context, newComponent.getOwner()) ||
            hasAuthority(context, header.getManager()))) {
          return "PermissionError";
        }
        bean.setComponent(newComponent);
        isValid = this.validateObject(context, db, bean);
        isValid = this.validateObject(context, db, newComponent) && isValid;
        if (isValid) {
          oldComponent = new OpportunityComponent(db, newComponent.getId());
          resultCount = newComponent.update(db, context);
        }
        if (resultCount == 1) {
          this.processUpdateHook(context, oldComponent, newComponent);
        }
      } else {
        if (!hasAuthority(db, context, thisContact)) {
          return ("PermissionError");
        }
        if (!hasAuthority(context, header.getManager())) {
          return ("PermissionError");
        }
        bean.setComponent(newComponent);
        isValid = this.validateObject(context, db, bean);
        isValid = this.validateObject(context, db, newComponent) && isValid;
        if (isValid) {
          recordInserted = newComponent.insert(db, context);
        }
      }
      if (recordInserted) {
        this.processInsertHook(context, newComponent);
        addRecentItem(context, newComponent);
      } else if (resultCount == 1) {
        newComponent.queryRecord(db, newComponent.getId());
      } else {
        //rebuild the form
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute(
            "TypeList", newComponent.getTypeList());
        if ("modify".equals(action) && resultCount == -1) {
          UserBean thisUser = (UserBean) context.getSession().getAttribute(
              "User");
          User thisRec = thisUser.getUserRecord();
          UserList shortChildList = thisRec.getShortChildList();
          UserList userList = thisRec.getFullChildList(
              shortChildList, new UserList());
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
    if (!hasPermission(
        context, "accounts-accounts-contacts-opportunities-view")) {
      return ("PermissionError");
    }
    int headerId = -1;
    addModuleBean(context, "View Accounts", "Opportunities");
    if (context.getRequest().getParameter("headerId") != null) {
      headerId = Integer.parseInt(
          context.getRequest().getParameter("headerId"));
    } else {
      headerId = Integer.parseInt(
          (String) context.getRequest().getAttribute("headerId"));
    }
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    OpportunityHeader thisHeader = null;
    OpportunityComponentList componentList = null;
    PagedListInfo oppPagedListInfo = (PagedListInfo) context.getSession().getAttribute("AccountContactOppsPagedListInfo");
    PagedListInfo componentListInfo = this.getPagedListInfo(
        context, "AccountContactComponentListInfo");
    componentListInfo.setLink(
        "AccountContactsOpps.do?command=DetailsOpp&headerId=" + headerId + "&contactId=" + contactId);

    try {
      db = this.getConnection(context);
      Contact thisContact = addFormElements(context, db);
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);

      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("OpportunityHeader", thisHeader);
      componentList = new OpportunityComponentList();
      componentList.setPagedListInfo(componentListInfo);
      if ("my".equals(oppPagedListInfo.getListView())) {
        componentList.setControlledHierarchyOnly(Constants.UNDEFINED);
        componentList.setOwner(this.getUserId(context));
        componentList.setQueryOpenOnly(true);
      } else {
        componentList.setControlledHierarchy(Constants.FALSE, this.getUserRange(context));
      }
      componentList.setAccessType(thisHeader.getAccessType());
      if (!excludeHierarchy(context)) {
        if (!(hasAuthority(context, thisHeader.getManager()) || OpportunityHeader.isComponentOwner(db, headerId, this.getUserId(context)))) {
          return ("PermissionError");
        }
      }
      componentList.setHeaderId(thisHeader.getId());
      if (thisHeader.isTrashed()) {
        componentList.setIncludeOnlyTrashed(true);
      }
      componentList.buildList(db);
      context.getRequest().setAttribute("ComponentList", componentList);
      thisHeader.setAccountLink(thisContact.getOrgId());
      if (!allowMultiple(context) && (componentList.size() > 0)) {
        //Load the opportunity header for display
        OpportunityComponent thisComponent = (OpportunityComponent) componentList.get(0);
        context.getRequest().setAttribute("OppComponentDetails", thisComponent);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addRecentItem(context, thisHeader);
    if (!allowMultiple(context) && (componentList.size() > 0)) {
      return this.getReturn(context, "DetailsComponent");
    }
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
    int headerId = Integer.parseInt(
        context.getRequest().getParameter("headerId"));
    Connection db = null;
    OpportunityHeader thisHeader = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = addFormElements(context, db);
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      if (!hasAuthority(db, context, thisContact)) {
        return "PermissionError";
      }
      if (!hasAuthority(context, thisHeader.getManager())) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("OpportunityHeader", thisHeader);
      addRecentItem(context, thisHeader);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
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
    OpportunityHeader header = null;
    Contact thisContact = null;
    Connection db = null;
    addModuleBean(context, "View Accounts", "Opportunities");
    //Parameters
    String componentId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList environmentSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_environment");
      context.getRequest().setAttribute(
          "environmentSelect", environmentSelect);
      LookupList competitorsSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_competitors");
      context.getRequest().setAttribute(
          "competitorsSelect", competitorsSelect);
      LookupList compellingEventSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_event_compelling");
      context.getRequest().setAttribute(
          "compellingEventSelect", compellingEventSelect);
      LookupList budgetSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_budget");
      context.getRequest().setAttribute("budgetSelect", budgetSelect);
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
      //Add account and contact
      thisContact = addFormElements(context, db);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      //Load the component
      thisComponent = new OpportunityComponent(
          db, Integer.parseInt(componentId));
      header = new OpportunityHeader(db, thisComponent.getHeaderId());
      if (!excludeHierarchy(context)) {
        if (!hasAuthority(context, thisComponent.getOwner()) &&
            accessTypeList.getCode(AccessType.PUBLIC) != header.getAccessType()) {
          return "PermissionError";
        }
      }
      thisComponent.checkEnabledOwnerAccount(db);
      context.getRequest().setAttribute("OppComponentDetails", thisComponent);
      //Load the opportunity header for display
      context.getRequest().setAttribute("OpportunityHeader", header);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (this.allowMultiple(context)) {
      addRecentItem(context, thisComponent);
    } else {
      addRecentItem(context, header);
    }
    return getReturn(context, "DetailsComponent");
  }


  /**
   *  Confirm Deletion of an Opportunity
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(
        context, "accounts-accounts-contacts-opportunities-delete")) {
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
      OpportunityHeader thisOpp = new OpportunityHeader(db, headerId);
      if (!hasAuthority(context, thisOpp.getManager())) {
        return ("PermissionError");
      }
      DependencyList dependencies = thisOpp.processDependencies(db, allowMultiple(context));
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      htmlDialog.addButton(
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='AccountContactsOpps.do?command=TrashOpp&contactId=" + contactId + "&id=" + headerId + RequestUtils.addLinkParams(
          context.getRequest(), "popup|popupType|actionId") + "'");
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
    if (!hasPermission(
        context, "accounts-accounts-contacts-opportunities-delete")) {
      return ("PermissionError");
    }

    OpportunityComponent thisComponent = null;
    OpportunityHeader header = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    try {
      db = this.getConnection(context);
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
      Contact thisContact = addFormElements(context, db);
      thisComponent = new OpportunityComponent(db, id);
      header = new OpportunityHeader(db, thisComponent.getHeaderId());
      if (!(hasAuthority(context, thisComponent.getOwner()) ||
          hasAuthority(context, header.getManager()))) {
        return "PermissionError";
      }
      SystemStatus systemStatus = this.getSystemStatus(context);
      htmlDialog.setTitle(
          systemStatus.getLabel("confirmdelete.contact.opps.delete"));
      htmlDialog.setShowAndConfirm(false);
      htmlDialog.setDeleteUrl(
          "javascript:window.location.href='AccountContactsOppComponents.do?command=DeleteComponent&contactId=" + contactId + "&id=" + id + RequestUtils.addLinkParams(
          context.getRequest(), "popup|popupType|actionId") + "'");
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
   */
  public String executeCommandDeleteOpp(ActionContext context) {
    if (!hasPermission(
        context, "accounts-accounts-contacts-opportunities-delete")) {
      return ("PermissionError");
    }

    boolean recordDeleted = false;
    String contactId = context.getRequest().getParameter("contactId");
    OpportunityHeader newOpp = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = addFormElements(context, db);
      newOpp = new OpportunityHeader(
          db, context.getRequest().getParameter("id"));
      if (!hasAuthority(context, newOpp.getManager())) {
        return ("PermissionError");
      }
      recordDeleted = newOpp.delete(db, context, getDbNamePath(context));
      if (recordDeleted) {
        invalidateUserData(context, this.getUserId(context));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    boolean inLinePopup = "inline".equals(
        context.getRequest().getParameter("popupType"));
    if (recordDeleted) {
      context.getRequest().setAttribute("contactId", contactId);
      context.getRequest().setAttribute(
          "refreshUrl", "AccountContactsOpps.do?command=ViewOpps&contactId=" + contactId + RequestUtils.addLinkParams(
          context.getRequest(), "popupType|actionId" + (inLinePopup ? "|popup" : "")));
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
    if (!hasPermission(
        context, "accounts-accounts-contacts-opportunities-delete")) {
      return ("PermissionError");
    }

    boolean recordDeleted = false;
    OpportunityComponent component = null;
    OpportunityHeader header = null;
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
      Contact thisContact = addFormElements(context, db);
      component = new OpportunityComponent(
          db, context.getRequest().getParameter("id"));
      header = new OpportunityHeader(db, component.getHeaderId());
      if (!(hasAuthority(context, component.getOwner()) ||
          hasAuthority(context, header.getManager()))) {
        return "PermissionError";
      }
      recordDeleted = component.delete(db, context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    boolean inLinePopup = "inline".equals(
        context.getRequest().getParameter("popupType"));
    if (recordDeleted) {
      context.getRequest().setAttribute(
          "refreshUrl", "AccountContactsOpps.do?command=DetailsOpp&headerId=" + component.getHeaderId() + "&contactId=" + contactId + RequestUtils.addLinkParams(
          context.getRequest(), "popupType|actionId" + (inLinePopup ? "|popup" : "")));
      deleteRecentItem(context, component);
      return ("ComponentDeleteOK");
    } else {
      processErrors(context, component.getErrors());
      return (executeCommandViewOpps(context));
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandTrashOpp(ActionContext context) {
    if (!hasPermission(
        context, "accounts-accounts-contacts-opportunities-delete")) {
      return ("PermissionError");
    }

    boolean recordUpdated = false;
    String contactId = context.getRequest().getParameter("contactId");
    OpportunityHeader newOpp = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = addFormElements(context, db);
      newOpp = new OpportunityHeader(
          db, context.getRequest().getParameter("id"));
      recordUpdated = newOpp.updateStatus(db, context, true, this.getUserId(context));
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!hasAuthority(context, newOpp.getManager())) {
        return ("PermissionError");
      }
      newOpp.invalidateUserData(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    boolean inLinePopup = "inline".equals(
        context.getRequest().getParameter("popupType"));
    if (recordUpdated) {
      context.getRequest().setAttribute("contactId", contactId);
      context.getRequest().setAttribute(
          "refreshUrl", "AccountContactsOpps.do?command=ViewOpps&contactId=" + contactId + RequestUtils.addLinkParams(
          context.getRequest(), "popupType|actionId" + (inLinePopup ? "|popup" : "")));
      deleteRecentItem(context, newOpp);
      return "OppDeleteOK";
    } else {
      processErrors(context, newOpp.getErrors());
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
    if (!hasPermission(
        context, "accounts-accounts-contacts-opportunities-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    OpportunityComponent component = null;
    OpportunityHeader header = null;
    addModuleBean(context, "View Accounts", "Opportunities");

    String componentId = context.getRequest().getParameter("id");

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = thisUser.getUserRecord();
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(
        shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setExcludeExpiredIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);

    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList environmentSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_environment");
      context.getRequest().setAttribute(
          "environmentSelect", environmentSelect);
      LookupList competitorsSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_competitors");
      context.getRequest().setAttribute(
          "competitorsSelect", competitorsSelect);
      LookupList compellingEventSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_event_compelling");
      context.getRequest().setAttribute(
          "compellingEventSelect", compellingEventSelect);
      LookupList budgetSelect = systemStatus.getLookupList(
          db, "lookup_opportunity_budget");
      context.getRequest().setAttribute("budgetSelect", budgetSelect);
      //get the access type list
      AccessTypeList accessTypeList = systemStatus.getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
      component = new OpportunityComponent(db, componentId);
      context.getRequest().setAttribute("headerId", String.valueOf(component.getHeaderId()));
      header = new OpportunityHeader(db, component.getHeaderId());
      Contact thisContact = addFormElements(context, db);
      if (!hasAuthority(context, component.getOwner()) &&
          !hasAuthority(context, header.getManager())) {
        return ("PermissionError");
      }
      header.setAccountLink(thisContact.getOrgId());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ComponentDetails", component);
    if (this.allowMultiple(context)) {
      addRecentItem(context, component);
    } else {
      addRecentItem(context, header);
    }
    return executeCommandPrepare(context);
  }


  /**
   *  Adds a feature to the FormElements attribute of the AccountContactsOpps
   *  object
   *
   *@param  context        The feature to be added to the FormElements attribute
   *@param  db             The feature to be added to the FormElements attribute
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public Contact addFormElements(ActionContext context, Connection db) throws SQLException {
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = (Contact) context.getRequest().getAttribute(
        "ContactDetails");
    Organization thisOrganization = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    LookupList environmentSelect = systemStatus.getLookupList(
        db, "lookup_opportunity_environment");
    context.getRequest().setAttribute("environmentSelect", environmentSelect);
    LookupList competitorsSelect = systemStatus.getLookupList(
        db, "lookup_opportunity_competitors");
    context.getRequest().setAttribute("competitorsSelect", competitorsSelect);
    LookupList compellingEventSelect = systemStatus.getLookupList(
        db, "lookup_opportunity_event_compelling");
    context.getRequest().setAttribute(
        "compellingEventSelect", compellingEventSelect);
    LookupList budgetSelect = systemStatus.getLookupList(
        db, "lookup_opportunity_budget");
    context.getRequest().setAttribute("budgetSelect", budgetSelect);
    LookupList siteList = new LookupList(db, "lookup_site_id");
    siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("SiteIdList", siteList);
    //get the access type list
    AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
    context.getRequest().setAttribute("accessTypeList", accessTypeList);

    if (thisContact == null) {
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    }
    if (thisContact.getOrgId() > -1) {
      thisOrganization = new Organization(db, thisContact.getOrgId());
    }
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    context.getRequest().setAttribute("orgId", String.valueOf(thisOrganization.getId()));
    return thisContact;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddFolderRecord(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("systemStatus", systemStatus);
    try {
      String headerId = context.getRequest().getParameter("headerId");
      db = this.getConnection(context);

      //load the opportunity
      OpportunityHeader thisHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("OpportunityHeader", thisHeader);

      thisContact = new Contact(db, thisHeader.getContactLink());

      if (!(hasPermission(context, "accounts-accounts-contacts-opps-folders-add")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(context, thisHeader.getEnteredBy())) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_PIPELINE);
      thisCategory.setLinkItemId(thisHeader.getId());
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);

      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Opportunities", "Add Folder Record");
    return this.getReturn(context, "AddFolderRecord");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandFolderList(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-opps-folders-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    OpportunityHeader thisHeader = null;
    try {
      db = this.getConnection(context);

      String contactId = context.getRequest().getParameter("contactId");
      thisContact = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", thisContact);

      String headerId = context.getRequest().getParameter("headerId");
      thisHeader = new OpportunityHeader(db, Integer.parseInt(headerId));
      context.getRequest().setAttribute("OpportunityHeader", thisHeader);

      //Show a list of the different folders available in Accounts
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_PIPELINE);
      thisList.setLinkItemId(thisHeader.getId());
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.setBuildTotalNumOfRecords(true);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Contacts", "Custom Fields Details");

    return getReturn(context, "FolderList");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    String recordId = null;
    boolean showRecords = true;
    String selectedCatId = null;
    Organization thisOrganization = null;

    try {
      String headerId = context.getRequest().getParameter("headerId");
      db = this.getConnection(context);

      //load the opportunity
      OpportunityHeader thisHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("OpportunityHeader", thisHeader);

      thisContact = new Contact(db, thisHeader.getContactLink());

      if (!(hasPermission(context, "accounts-accounts-contacts-opps-folders-view")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-view")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(context, thisHeader.getEnteredBy())) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      //Show a list of the different folders available in Contacts
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_PIPELINE);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      //See which one is currently selected or use the default
      selectedCatId = (String) context.getRequest().getParameter("catId");
      if (selectedCatId == null) {
        selectedCatId = (String) context.getRequest().getAttribute("catId");
      }
      if (selectedCatId == null) {
        selectedCatId = String.valueOf(thisList.getDefaultCategoryId());
      }
      context.getRequest().setAttribute("catId", selectedCatId);

      if (Integer.parseInt(selectedCatId) > 0) {
        //See if a specific record has been chosen from the list
        recordId = context.getRequest().getParameter("recId");
        String recordDeleted = (String) context.getRequest().getAttribute("recordDeleted");
        if (recordDeleted != null) {
          recordId = null;
        }

        //Now build the specified or default category
        CustomFieldCategory thisCategory = thisList.getCategory(Integer.parseInt(selectedCatId));
        if (recordId == null && thisCategory.getAllowMultipleRecords()) {
          //The user didn't request a specific record, so show a list
          //of records matching this category that the user can choose from
          PagedListInfo folderListInfo = this.getPagedListInfo(context, "ContactFolderInfo");
          folderListInfo.setLink("AccountContactsOpps.do?command=Fields&headerId=" + headerId + "&catId=" + selectedCatId);

          CustomFieldRecordList recordList = new CustomFieldRecordList();
          recordList.setLinkModuleId(Constants.FOLDERS_PIPELINE);
          recordList.setLinkItemId(thisHeader.getId());
          recordList.setCategoryId(thisCategory.getId());
          recordList.buildList(db);
          recordList.buildRecordColumns(db, thisCategory);
          context.getRequest().setAttribute("Records", recordList);
        } else {
          //The user requested a specific record, or this category only
          //allows a single record.
          thisCategory.setLinkModuleId(Constants.FOLDERS_PIPELINE);
          thisCategory.setLinkItemId(thisHeader.getId());
          if (recordId != null) {
            thisCategory.setRecordId(Integer.parseInt(recordId));
          } else {
            thisCategory.buildRecordId(db);
            recordId = String.valueOf(thisCategory.getRecordId());
          }
          thisCategory.setIncludeEnabled(Constants.TRUE);
          thisCategory.setIncludeScheduled(Constants.TRUE);
          thisCategory.setBuildResources(true);
          thisCategory.buildResources(db);
          showRecords = false;

          if (thisCategory.getRecordId() > -1) {
            CustomFieldRecord thisRecord = new CustomFieldRecord(db, thisCategory.getRecordId());
            context.getRequest().setAttribute("Record", thisRecord);
          }
        }
        context.getRequest().setAttribute("Category", thisCategory);
      }

      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Opportunities", "Custom Fields Details");
    if (Integer.parseInt(selectedCatId) <= 0) {
      return this.getReturn(context, "FieldsEmpty");
    } else if (recordId == null && showRecords) {
      return this.getReturn(context, "FieldRecordList");
    } else {
      return this.getReturn(context, "Fields");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    String selectedCatId = (String) context.getRequest().getParameter("catId");
    String recordId = (String) context.getRequest().getParameter("recId");
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("systemStatus", systemStatus);

    try {
      String headerId = context.getRequest().getParameter("headerId");
      db = this.getConnection(context);

      //load the opportunity
      OpportunityHeader thisHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("OpportunityHeader", thisHeader);

      thisContact = new Contact(db, thisHeader.getContactLink());
      if (!(hasPermission(context, "accounts-accounts-contacts-opps-folders-edit")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(context, thisHeader.getEnteredBy())) {
        return "PermissionError";
      }

      context.getRequest().setAttribute("ContactDetails", thisContact);

      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_PIPELINE);
      thisCategory.setLinkItemId(thisHeader.getId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);

      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Opportunities", "Modify Custom Fields");
    if (recordId.equals("-1")) {
      return this.getReturn(context, "AddFolderRecord");
    } else {
      return this.getReturn(context, "ModifyFields");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    int resultCount = 0;
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("systemStatus", systemStatus);

    try {
      String headerId = context.getRequest().getParameter("headerId");
      db = this.getConnection(context);

      //load the opportunity
      OpportunityHeader thisHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("OpportunityHeader", thisHeader);

      thisContact = new Contact(db, thisHeader.getContactLink());
      if (!(hasPermission(context, "accounts-accounts-contacts-opps-folders-edit")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(context, thisHeader.getEnteredBy())) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_PIPELINE);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      String recordId = (String) context.getRequest().getParameter("recId");

      context.getRequest().setAttribute("catId", selectedCatId);
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_PIPELINE);
      thisCategory.setLinkItemId(thisHeader.getId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setParameters(context);
      thisCategory.setModifiedBy(this.getUserId(context));
      if (!thisCategory.getReadOnly()) {
        resultCount = thisCategory.update(db);
      }
      if (resultCount == -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Contacts-> ModifyField validation error");
        }
      } else {
        thisCategory.buildResources(db);
        CustomFieldRecord thisRecord = new CustomFieldRecord(db, thisCategory.getRecordId());
        context.getRequest().setAttribute("Record", thisRecord);
      }
      context.getRequest().setAttribute("Category", thisCategory);

      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1) {
      return this.getReturn(context, "ModifyFields");
    } else if (resultCount == 1) {
      return this.getReturn(context, "UpdateFields");
    } else {
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
  public String executeCommandInsertFields(ActionContext context) {
    Connection db = null;
    int resultCode = -1;
    Contact thisContact = null;
    Organization thisOrganization = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("systemStatus", systemStatus);

    try {
      String headerId = context.getRequest().getParameter("headerId");
      db = this.getConnection(context);

      //load the opportunity
      OpportunityHeader thisHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("OpportunityHeader", thisHeader);

      thisContact = new Contact(db, thisHeader.getContactLink());
      if (!(hasPermission(context, "accounts-accounts-contacts-opps-folders-add")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_PIPELINE);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      context.getRequest().setAttribute("catId", selectedCatId);
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_PIPELINE);
      thisCategory.setLinkItemId(thisHeader.getId());
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setParameters(context);
      thisCategory.setEnteredBy(this.getUserId(context));
      thisCategory.setModifiedBy(this.getUserId(context));
      if (!thisCategory.getReadOnly()) {
        resultCode = thisCategory.insert(db);
      }
      if (resultCode == -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Contacts-> InsertField validation error");
        }
      }
      context.getRequest().setAttribute("Category", thisCategory);

      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCode == -1) {
      return this.getReturn(context, "AddFolderRecord");
    } else {
      return (this.executeCommandFields(context));
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteFields(ActionContext context) {
    Connection db = null;
    boolean recordDeleted = false;
    Organization thisOrganization = null;
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
      String selectedCatId = context.getRequest().getParameter("catId");
      String recordId = context.getRequest().getParameter("recId");
      String headerId = context.getRequest().getParameter("headerId");

      //load the opportunity
      OpportunityHeader thisHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("OpportunityHeader", thisHeader);

      thisContact = new Contact(db, thisHeader.getContactLink());
      if (!(hasPermission(context, "accounts-accounts-contacts-opps-folders-delete")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }

      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));

      CustomFieldRecord thisRecord = new CustomFieldRecord(db, Integer.parseInt(recordId));
      thisRecord.setLinkModuleId(Constants.FOLDERS_PIPELINE);
      thisRecord.setLinkItemId(Integer.parseInt(headerId));
      thisRecord.setCategoryId(Integer.parseInt(selectedCatId));
      if (!thisCategory.getReadOnly()) {
        recordDeleted = thisRecord.delete(db);
      }
      context.getRequest().setAttribute("recordDeleted", "true");

      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DeleteFieldsOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandComponentHistory(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    int headerId = -1;
    int componentId = -1;
    addModuleBean(context, "View Contacts", "View Opportunity History List");
    if (context.getRequest().getParameter("id") != null) {
      componentId = Integer.parseInt(context.getRequest().getParameter("id"));
    } else {
      componentId = Integer.parseInt(
          (String) context.getRequest().getAttribute("id"));
    }

    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    Organization thisOrganization = null;
    Contact thisContact = null;
    OpportunityComponent thisComponent = null;
    OpportunityHeader thisHeader = null;
    OpportunityComponentLogList componentLogList = null;
    SystemStatus systemStatus = this.getSystemStatus(context);

    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("contactComponentHistoryListInfo");
    }
    PagedListInfo componentHistoryListInfo = this.getPagedListInfo(
        context, "accountContactsComponentHistoryListInfo");
    componentHistoryListInfo.setLink(
        "AccountContactsOppComponents.do?command=ComponentHistory&contactId=" + contactId +
        "&id=" + componentId + RequestUtils.addLinkParams(
        context.getRequest(), "viewSource"));

    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("contactDetails", thisContact);
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);

      //Generate the component
      thisComponent = new OpportunityComponent();
      thisComponent.queryRecord(db, componentId);
      context.getRequest().setAttribute("opportunityComponent", thisComponent);
      //Generate the opportunity header
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, thisComponent.getHeaderId());
      context.getRequest().setAttribute("opportunityHeader", thisHeader);

      //Generate the list of components
      componentLogList = new OpportunityComponentLogList();
      componentLogList.setPagedListInfo(componentHistoryListInfo);
      componentLogList.setHeaderId(thisHeader.getId());
      componentLogList.setComponentId(componentId);
      componentLogList.buildList(db);
      context.getRequest().setAttribute("componentHistoryList", componentLogList);

      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("orgDetails", thisOrganization);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ComponentHistoryOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandComponentHistoryDetails(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-opportunities-view")) {
      return ("PermissionError");
    }
    //Configure the action
    addModuleBean(context, "View Contacts", "View Opportunity History Details");

    String historyId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");

    Contact thisContact = null;
    Organization thisOrganization = null;
    OpportunityComponentLog thisComponentLog = null;
    OpportunityComponent component = null;
    OpportunityHeader header = null;
    //Begin processing
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("contactDetails", thisContact);

      thisComponentLog = new OpportunityComponentLog(db, Integer.parseInt(historyId));
      context.getRequest().setAttribute("componentLogDetails", thisComponentLog);

      component = new OpportunityComponent(db, thisComponentLog.getComponentId());
      context.getRequest().setAttribute("opportunityComponent", component);

      header = new OpportunityHeader(db, component.getHeaderId());
      context.getRequest().setAttribute("opportunityHeader", header);
      
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("orgDetails", thisOrganization);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    return ("ComponentHistoryDetailsOK");
  }


  /**
   *  Determines of multiple components are allowed for this site
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  private boolean allowMultiple(ActionContext context) {
    //get the preference for single/multiple components
    String multiple = this.getSystemPref(context, OpportunityComponent.MULTPLE_CONFIG_NAME, "multiple");
    return OpportunityComponent.allowMultiple(multiple);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  private boolean excludeHierarchy(ActionContext context) {
    //get the preference for single/multiple components
    String exclude = this.getSystemPref(context, OpportunityComponent.MULTPLE_CONFIG_NAME, "excludeHierarchy");
    return DatabaseUtils.parseBoolean(exclude);
  }
}

