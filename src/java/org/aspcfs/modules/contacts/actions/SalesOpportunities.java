/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.modules.contacts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
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

public final class SalesOpportunities extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandViewOpps(ActionContext context) {

    Exception errorMessage = null;
    String contactId = context.getRequest().getParameter("contactId");
    addModuleBean(context, "External Contacts", "Opportunities");
    PagedListInfo oppPagedListInfo = this.getPagedListInfo(
        context, "SalesOpportunitiesPagedListInfo");
    oppPagedListInfo.setLink(
        "SalesOpportunities.do?command=ViewOpps&contactId=" + contactId + RequestUtils.addLinkParams(
        context.getRequest(), "popup|popupType|actionId|from|listForm"));
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    Connection db = null;
    OpportunityHeaderList oppList = new OpportunityHeaderList();
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);

      if (!hasPermission(
          context, "sales-leads-opportunities-view") || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-opportunities-view")))) {
        return ("PermissionError");
      }

      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      oppList.setPagedListInfo(oppPagedListInfo);
      oppList.setAllowMultipleComponents(allowMultiple(context));
      oppList.setContactId(contactId);
      oppList.setBuildTotalValues(true);
      if ("my".equals(oppPagedListInfo.getListView())) {
        oppList.setControlledHierarchyOnly(Constants.UNDEFINED);
        oppList.setOwner(this.getUserId(context));
        oppList.setQueryOpenOnly(true);
      } else if ("closed".equals(oppPagedListInfo.getListView())) {
        if (!excludeHierarchy(context)) {
          oppList.setControlledHierarchy(Constants.FALSE, this.getUserRange(context));
          oppList.setAccessType(accessTypeList.getCode(AccessType.PUBLIC));
        } else {
          oppList.setControlledHierarchyOnly(Constants.UNDEFINED);
        }
        oppList.setQueryClosedOnly(true);
      } else {
        if (!excludeHierarchy(context)) {
          oppList.setControlledHierarchy(Constants.FALSE, this.getUserRange(context));
          oppList.setAccessType(accessTypeList.getCode(AccessType.PUBLIC));
        } else {
          oppList.setControlledHierarchyOnly(Constants.UNDEFINED);
        }
        oppList.setQueryOpenOnly(true);
      }
      if (thisContact.isTrashed()) {
        oppList.setIncludeOnlyTrashed(true);
      }
      oppList.buildList(db);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OpportunityHeaderList", oppList);
      return this.getReturn(context, "ListOpps");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandPrepare(ActionContext context) {
    Exception errorMessage = null;
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;
    Connection db = null;
    String id = context.getRequest().getParameter("headerId");
    if (id == null || "".equals(id)) {
      id = (String) context.getRequest().getAttribute("headerId");
    }
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
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
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
      if (context.getRequest().getAttribute("ContactDetails") == null) {
        thisContact = new Contact(db, contactId);
        if (!hasAuthority(db, context, thisContact)) {
          return ("PermissionError");
        }
        context.getRequest().setAttribute("ContactDetails", thisContact);
      } else {
        thisContact = (Contact) context.getRequest().getAttribute(
            "ContactDetails");
      }

      if (id != null && !"-1".equals(id)) {
        if (!hasPermission(
            context, "sales-leads-opportunities-add") || (thisContact.getOrgId() > 0 && !(hasPermission(
            context, "accounts-accounts-contacts-opportunities-add")))) {
          return ("PermissionError");
        }
      }

      //check if a header needs to be built.
      if (id != null && !"-1".equals(id)) {
        //Build the container item
        OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(id));
        context.getRequest().setAttribute("opportunityHeader", oppHeader);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "External Contacts", "Opportunities");
      return "PrepareOppOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandSave(ActionContext context) {
    boolean recordInserted = false;
    boolean isValid = false;
    OpportunityBean newOpp = (OpportunityBean) context.getRequest().getAttribute(
        "OppDetails");
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }

    SystemStatus systemStatus = this.getSystemStatus(context);
    if (!systemStatus.hasField("opportunity.componentTypes")) {
      //set types
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
      thisContact = new Contact(db, contactId);
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);

      if (!hasPermission(
          context, "sales-leads-opportunities-add") || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-opportunities-add")))) {
        return ("PermissionError");
      }

      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
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
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted) {
      addRecentItem(context, newOpp.getHeader());
      if (context.getRequest().getParameter("popup") != null ) {
        return ("CloseAddPopup");
      }
      context.getRequest().setAttribute(
          "headerId", String.valueOf(newOpp.getHeader().getId()));
      return (executeCommandDetailsOpp(context));
    }
    return (executeCommandPrepare(context));
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateOpp(ActionContext context) {
    Connection db = null;
    int resultCount = 0;
    boolean isValid = false;
    String headerId = context.getRequest().getParameter("headerId");
    String contactId = context.getRequest().getParameter("contactId");
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }

    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasPermission(
          context, "sales-leads-opportunities-edit") || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }

      OpportunityHeader oldHeader = new OpportunityHeader(
          db, Integer.parseInt(headerId));
      OpportunityHeader oppHeader = new OpportunityHeader(
          db, Integer.parseInt(headerId));
      if (!hasAuthority(context, oppHeader.getManager())) {
        return "PermissionError";
      }
      oppHeader.setModifiedBy(getUserId(context));
      oppHeader.setDescription(
          context.getRequest().getParameter("description"));
      isValid = this.validateObject(context, db, oppHeader);
      if (isValid) {
        resultCount = oppHeader.update(db);
      }
      if (resultCount == 1) {
        this.processUpdateHook(context, oldHeader, oppHeader);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandViewOpps(context));
      } else {
        return (executeCommandDetailsOpp(context));
      }
    } else if (resultCount == -1 || !isValid) {
      return executeCommandModifyOpp(context);
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
  public String executeCommandSaveComponent(ActionContext context) {
    boolean recordInserted = false;
    boolean isValid = false;
    int resultCount = 0;
    Contact thisContact = null;
    String permission = "sales-leads-opportunities-add";
    SystemStatus systemStatus = this.getSystemStatus(context);
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }

    OpportunityHeader header = null;
    OpportunityBean bean = new OpportunityBean();
    OpportunityComponent newComponent = (OpportunityComponent) context.getFormBean();
    OpportunityComponent oldComponent = null;
    if (!systemStatus.hasField("opportunity.componentTypes")) {
      newComponent.setTypeList(
          context.getRequest().getParameterValues("selectedList"));
    }
    newComponent.setEnteredBy(getUserId(context));
    newComponent.setModifiedBy(getUserId(context));

    String action = (newComponent.getId() > 0 ? "modify" : "insert");
    String contactId = context.getRequest().getParameter("contactId");

    if ("modify".equals(action)) {
      permission = "sales-leads-opportunities-edit";
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      header = new OpportunityHeader(db, newComponent.getHeaderId());
      newComponent.setContactId(header.getContactLink());
      newComponent.setOrgId(header.getAccountLink());
      bean.setHeader(header);
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);

      //check permissions
      if (!hasPermission(context, permission) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }

      if (newComponent.getId() > 0) {
        newComponent.setModifiedBy(getUserId(context));
        if (!hasAuthority(db, context, thisContact)) {
          return "PermissionError";
        }
        if (!hasAuthority(context, header.getManager()) &&
            !hasAuthority(context, newComponent.getOwner())) {
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
          return "PermissionError";
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
      context.getRequest().setAttribute("ComponentDetails", newComponent);
      context.getRequest().setAttribute("ContactDetails", thisContact);
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDetailsOpp(ActionContext context) {
    Exception errorMessage = null;
    int headerId = -1;
    addModuleBean(context, "External Contacts", "Opportunities");
    if (context.getRequest().getParameter("headerId") != null) {
      headerId = Integer.parseInt(
          context.getRequest().getParameter("headerId"));
    } else {
      headerId = Integer.parseInt(
          (String) context.getRequest().getAttribute("headerId"));
    }
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    Contact thisContact = null;
    OpportunityHeader thisHeader = null;
    OpportunityComponentList componentList = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }

    PagedListInfo oppPagedListInfo = this.getPagedListInfo(context, "SalesOpportunitiesPagedListInfo", false);
    PagedListInfo componentListInfo = this.getPagedListInfo(
        context, "ComponentListInfo");
    componentListInfo.setLink(
        "SalesOpportunities.do?command=DetailsOpp&headerId=" + headerId + "&contactId=" + contactId + RequestUtils.addLinkParams(
        context.getRequest(), "popup|popupType|actionId|from|listForm"));
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!thisContact.getEnabled() || thisContact.isTrashed()) {
        return "ContactDisabledError";
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      if (!hasPermission(
          context, "sales-leads-opportunities-view") || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-opportunities-view")))) {
        return ("PermissionError");
      }

      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }

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

      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", thisHeader);

      componentList = new OpportunityComponentList();
      componentList.setPagedListInfo(componentListInfo);
      componentList.setAccessType(thisHeader.getAccessType());
      if (oppPagedListInfo != null && oppPagedListInfo.getListView() != null) {
        if ("my".equals(oppPagedListInfo.getListView())) {
          componentList.setControlledHierarchyOnly(Constants.UNDEFINED);
          componentList.setOwner(this.getUserId(context));
          componentList.setQueryOpenOnly(true);
        } else {
          if (!excludeHierarchy(context)) {
            componentList.setControlledHierarchy(Constants.FALSE, this.getUserRange(context));
          } else {
            componentList.setControlledHierarchyOnly(Constants.UNDEFINED);
          }
        }
      } else {
        componentList.setControlledHierarchy(Constants.FALSE, this.getUserRange(context));
      }
      componentList.setHeaderId(thisHeader.getId());
      if (thisHeader.isTrashed()) {
        componentList.setIncludeOnlyTrashed(true);
      }
      componentList.buildList(db);
      context.getRequest().setAttribute("componentList", componentList);
      if (!allowMultiple(context) && (componentList.size() > 0)) {
        //Load the opportunity header for display
        OpportunityComponent thisComponent = (OpportunityComponent) componentList.get(0);
        context.getRequest().setAttribute("oppComponentDetails", thisComponent);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addRecentItem(context, thisHeader);
    if (!allowMultiple(context) && (componentList.size() > 0)) {
      return getReturn(context, "DetailsComponent");
    }
    return getReturn(context, "DetailsOpp");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyOpp(ActionContext context) {
    Exception errorMessage = null;
    addModuleBean(context, "External Contacts", "Opportunities");
    int headerId = Integer.parseInt(
        context.getRequest().getParameter("headerId"));
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    OpportunityHeader thisHeader = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Contact thisContact = null;
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }

    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);

      if (!hasPermission(
          context, "sales-leads-opportunities-edit") || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return "PermissionError";
      }
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      if (!hasAuthority(context, thisHeader.getManager())) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("opportunityHeader", thisHeader);
      addRecentItem(context, thisHeader);
      return this.getReturn(context, "ModifyOpp");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetailsComponent(ActionContext context) {
    OpportunityComponent thisComponent = null;
    Contact thisContact = null;
    Connection db = null;
    addModuleBean(context, "External Contacts", "Opportunities");
    //Parameters
    String componentId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }

    try {
      db = this.getConnection(context);
      //Load the contact
      thisContact = new Contact(db, contactId);

      if (!hasPermission(
          context, "sales-leads-opportunities-view") || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-opportunities-view")))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);
      //Load the component
      thisComponent = new OpportunityComponent(db, Integer.parseInt(componentId));
      //Load the opportunity header for display
      OpportunityHeader oppHeader = new OpportunityHeader(db, thisComponent.getHeaderId());
      context.getRequest().setAttribute("opportunityHeader", oppHeader);

      if (!hasAuthority(db, context, thisContact)) {
        return "PermissionError";
      }
      if (!hasAuthority(context, oppHeader.getManager()) &&
          !hasAuthority(context, thisComponent.getOwner()) &&
          accessTypeList.getCode(AccessType.PUBLIC) != oppHeader.getAccessType()) {
        return "PermissionError";
      }
      thisComponent.checkEnabledOwnerAccount(db);
      context.getRequest().setAttribute("oppComponentDetails", thisComponent);
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
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addRecentItem(context, thisComponent);
    return getReturn(context, "DetailsComponent");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    HtmlDialog htmlDialog = new HtmlDialog();
    String headerId = context.getRequest().getParameter("headerId");
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasPermission(
          context, "sales-leads-opportunities-delete") || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-opportunities-delete")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      OpportunityHeader thisOpp = new OpportunityHeader(db, headerId);
      if (!hasAuthority(context, thisOpp.getManager())) {
        return "PermissionError";
      }
      DependencyList dependencies = thisOpp.processDependencies(db, allowMultiple(context));
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      htmlDialog.addButton(
          systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='SalesOpportunities.do?command=DeleteOpp&contactId=" + contactId + "&id=" + headerId + RequestUtils.addLinkParams(
          context.getRequest(), "popup|popupType|actionId|sourcePopup") + "'");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmComponentDelete(ActionContext context) {
    Exception errorMessage = null;
    OpportunityComponent thisComponent = null;
    OpportunityHeader header = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);

      if (!hasPermission(
          context, "sales-leads-opportunities-delete") || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }

      thisComponent = new OpportunityComponent(db, id);
      header = new OpportunityHeader(db, thisComponent.getHeaderId());
      if (!hasAuthority(db, context, thisContact)) {
        return "PermissionError";
      }
      if (!hasAuthority(context, header.getManager()) &&
          !hasAuthority(context, thisComponent.getOwner())) {
        return "PermissionError";
      }
      SystemStatus systemStatus = this.getSystemStatus(context);
      htmlDialog.setTitle(
          systemStatus.getLabel("confirmdelete.contact.opps.delete"));
      htmlDialog.setShowAndConfirm(false);
      htmlDialog.setDeleteUrl(
          "javascript:window.location.href='SalesOpportunitiesComponents.do?command=DeleteComponent&contactId=" + contactId + "&id=" + id + RequestUtils.addLinkParams(
          context.getRequest(), "popup|popupType|actionId|sourcePopup") + "'");
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDeleteOpp(ActionContext context) {
    if (!hasPermission(
        context, "sales-leads-opportunities-delete")) {
      return ("PermissionError");
    }
    String popup = (String) context.getRequest().getParameter("sourcePopup");
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String contactId = context.getRequest().getParameter("contactId");
    OpportunityHeader newOpp = null;
    Connection db = null;
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);

      if (!hasPermission(
          context, "sales-leads-opportunities-delete") || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-opportunities-delete")))) {
        return ("PermissionError");
      }

      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }

      newOpp = new OpportunityHeader(db, context.getRequest().getParameter("id"));
      if (!hasAuthority(context, newOpp.getManager())) {
        return ("PermissionError");
      }
      recordDeleted = newOpp.delete(db, context, this.getPath(context));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("contactId", contactId);
        deleteRecentItem(context, newOpp);
        if (popup != null && "true".equals(popup)) {
          context.getRequest().setAttribute("id", "" + newOpp.getId());
          return "OppDeletePopupOK";
        }
        context.getRequest().setAttribute(
            "refreshUrl", "SalesOpportunities.do?command=ViewOpps&contactId=" + contactId + RequestUtils.addLinkParams(
            context.getRequest(), "popupType|actionId"));
        return "OppDeleteOK";
      } else {
        processErrors(context, newOpp.getErrors());
        return (executeCommandViewOpps(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteComponent(ActionContext context) {
    Exception errorMessage = null;
    String popup = (String) context.getRequest().getParameter("sourcePopup");
    boolean recordDeleted = false;
    OpportunityComponent component = null;
    OpportunityHeader header = null;
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);

      if (!hasPermission(
          context, "sales-leads-opportunities-delete") || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-opportunities-delete")))) {
        return ("PermissionError");
      }
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);

      component = new OpportunityComponent(db, context.getRequest().getParameter("id"));
      header = new OpportunityHeader(db, component.getHeaderId());
      if (!hasAuthority(db, context, thisContact)) {
        return "PermissionError";
      }
      if (!(hasAuthority(context, header.getManager()) ||
          hasAuthority(context, component.getOwner()))) {
        return "PermissionError";
      }
      recordDeleted = component.delete(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        deleteRecentItem(context, component);
        if (popup != null && "true".equals(popup)) {
          context.getRequest().setAttribute("id", "" + component.getHeaderId());
          return "OppDeletePopupOK";
        }
        context.getRequest().setAttribute(
            "refreshUrl", "SalesOpportunities.do?command=DetailsOpp&headerId=" + component.getHeaderId() + "&contactId=" + contactId + RequestUtils.addLinkParams(
            context.getRequest(), "popupType|actionId"));
        return ("ComponentDeleteOK");
      } else {
        processErrors(context, component.getErrors());
        return (executeCommandViewOpps(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyComponent(ActionContext context) {
    Exception errorMessage = null;
    Contact thisContact = null;
    Connection db = null;
    OpportunityComponent component = null;
    OpportunityHeader header = null;
    addModuleBean(context, "External Contacts", "Opportunities");
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }

    String contactId = context.getRequest().getParameter("contactId");
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
    SystemStatus systemStatus = this.getSystemStatus(context);

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
      component = new OpportunityComponent(db, componentId);
      header = new OpportunityHeader(db, component.getHeaderId());
      context.getRequest().setAttribute(
          "headerId", String.valueOf(component.getHeaderId()));
      thisContact = new Contact(db, contactId);
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.OPPORTUNITIES);
      context.getRequest().setAttribute("accessTypeList", accessTypeList);

      if (!hasPermission(
          context, "sales-leads-opportunities-edit") || (thisContact.getOrgId() > 0 &&
          !(hasPermission(context, "accounts-accounts-contacts-opportunities-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!hasAuthority(context, header.getManager()) &&
          !hasAuthority(context, component.getOwner())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("ComponentDetails", component);
      addRecentItem(context, component);
      return executeCommandPrepare(context);
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandComponentHistory(ActionContext context) {
    if (!hasPermission(context, "sales-leads-opportunities-view")) {
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
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
   String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    Contact thisContact = null;
    OpportunityComponent thisComponent = null;
    OpportunityHeader thisHeader = null;
    OpportunityComponentLogList componentLogList = null;
    SystemStatus systemStatus = this.getSystemStatus(context);

    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("contactComponentHistoryListInfo");
    }
    PagedListInfo oppPagedInfo = this.getPagedListInfo(
        context, "opportunityPagedInfo");
    PagedListInfo componentHistoryListInfo = this.getPagedListInfo(
        context, "contactComponentHistoryListInfo");
    componentHistoryListInfo.setLink(
        "SalesOpportunitiesComponents.do?command=ComponentHistory&contactId=" + contactId +
        "&id=" + componentId + RequestUtils.addLinkParams(
        context.getRequest(), "viewSource|popup|from|listForm"));

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

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "ComponentHistory");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandComponentHistoryDetails(ActionContext context) {
    if (!hasPermission(context, "sales-leads-opportunities-view")) {
      return ("PermissionError");
    }
    //Configure the action
    addModuleBean(context, "View Contacts", "View Opportunity History Details");
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    String historyId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    
    Contact thisContact = null;
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
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    
    return this.getReturn(context, "ComponentHistoryDetails");
  }


  /**
   *  Description of the Method
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

