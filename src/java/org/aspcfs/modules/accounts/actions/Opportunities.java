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
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityComponentList;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.beans.OpportunityBean;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author chris
 * @version $Id: Opportunities.java,v 1.17 2003/01/07 16:29:07 mrajkowski Exp
 *          $
 * @created October 17, 2001
 */
public final class Opportunities extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandHome(ActionContext context) {
    addModuleBean(context, "Opportunities", "Opportunities Home");
    return ("HomeOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String orgId = context.getRequest().getParameter("orgId");
    addModuleBean(context, "View Accounts", "View Opportunity Details");

    PagedListInfo oppPagedInfo = this.getPagedListInfo(
        context, "OpportunityPagedInfo");
    oppPagedInfo.setLink("Opportunities.do?command=View&orgId=" + orgId);

    Connection db = null;
    OpportunityHeaderList oppList = new OpportunityHeaderList();
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);

      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      oppList.setPagedListInfo(oppPagedInfo);
      oppList.setBuildTotalValues(true);
      oppList.setOrgId(orgId);
      if (thisOrganization.isTrashed()) {
        oppList.setIncludeOnlyTrashed(true);
      }
      if ("all".equals(oppPagedInfo.getListView())) {
        oppList.setOwnerIdRange(this.getUserRange(context));
        oppList.setQueryOpenOnly(true);
      } else if ("closed".equals(oppPagedInfo.getListView())) {
        oppList.setOwnerIdRange(this.getUserRange(context));
        oppList.setQueryClosedOnly(true);
      } else {
        oppList.setOwner(this.getUserId(context));
        oppList.setQueryOpenOnly(true);
      }

      oppList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OpportunityList", oppList);
      return ("ListOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveComponent(ActionContext context) {
    boolean recordInserted = false;
    boolean isValid = false;
    int resultCount = 0;
    Organization thisOrg = null;
    String orgId = null;
    String permission = "accounts-accounts-opportunities-add";
    Connection db = null;
    OpportunityComponent newComponent = (OpportunityComponent) context.getFormBean();
    OpportunityHeader header = null;
    String action = (newComponent.getId() == -1 ? "insert" : "modify");
    SystemStatus systemStatus = this.getSystemStatus(context);

    if (context.getRequest().getParameter("orgId") != null) {
      orgId = context.getRequest().getParameter("orgId");
    }

    if ("modify".equals(action)) {
      permission = "accounts-accounts-opportunities-edit";
    }
    if (!hasPermission(context, permission)) {
      return ("PermissionError");
    }
    //set types
    newComponent.setTypeList(
        context.getRequest().getParameterValues("selectedList"));
    newComponent.setEnteredBy(getUserId(context));
    newComponent.setModifiedBy(getUserId(context));

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
      if ("insert".equals(action)) {
        isValid = this.validateObject(context, db, newComponent);
        if (isValid) {
          recordInserted = newComponent.insert(db, context);
        }
        if (recordInserted) {
          this.processInsertHook(context, newComponent);
          addRecentItem(context, newComponent);
        }
      } else {
        OpportunityComponent oldComponent = new OpportunityComponent(
            db, newComponent.getId());
        if (!hasAuthority(context, oldComponent.getOwner())) {
          return ("PermissionError");
        }
        newComponent.setModifiedBy(getUserId(context));
        isValid = this.validateObject(context, db, newComponent);
        if (isValid) {
          resultCount = newComponent.update(db, context);
        }
        if (resultCount == 1) {
          newComponent.queryRecord(db, newComponent.getId());
          this.processUpdateHook(context, oldComponent, newComponent);
        }
      }

      if (("insert".equals(action) && !recordInserted) || ("modify".equals(
          action) && resultCount == -1)) {
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute(
            "TypeList", newComponent.getTypeList());
      }
      thisOrg = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrg);
      context.getRequest().setAttribute("ComponentDetails", newComponent);

      //build the header
      if (resultCount == 1 || recordInserted) {
        OpportunityHeader oppHeader = new OpportunityHeader(
            db, newComponent.getHeaderId());
        context.getRequest().setAttribute("OpportunityHeader", oppHeader);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if ("insert".equals(action)) {
      if (recordInserted) {
        return (executeCommandDetails(context));
      } else {
        return (executeCommandPrepare(context));
      }
    } else {
      if (resultCount == 1) {
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
            "return").equals("list")) {
          return (executeCommandDetails(context));
        } else {
          return ("DetailsComponentOK");
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-add")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Add Opportunity");
    return executeCommandPrepare(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandPrepare(ActionContext context) {
    //Parameters
    String orgId = context.getRequest().getParameter("orgId");
    String headerId = context.getRequest().getParameter("headerId");
    String permission = "accounts-accounts-opportunities-add";
    Organization thisOrganization = null;
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    //check permissions
    if (headerId != null && !"-1".equals(headerId)) {
      permission = "accounts-accounts-opportunities-edit";
    }
    if (!hasPermission(context, permission)) {
      return ("PermissionError");
    }

    if (context.getRequest().getParameter("UserList") == null) {
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
    }

    try {
      db = this.getConnection(context);
      if (orgId == null || "".equals(orgId)) {
        String contactId = context.getRequest().getParameter("contactId");
        Contact contact = new Contact(db, contactId);
        orgId = String.valueOf(contact.getOrgId());
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

      if (context.getRequest().getParameter("OrgDetails") == null) {
        thisOrganization = new Organization(db, Integer.parseInt(orgId));
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
      }
      //Load the header
      if (headerId != null && !"-1".equals(headerId)) {
        OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
        context.getRequest().setAttribute("opportunityHeader", oppHeader);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("PrepareOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandSave(ActionContext context) {
    boolean recordInserted = false;
    boolean isValid = false;
    int resultCount = 0;
    String headerId = context.getRequest().getParameter("headerId");
    String permission = "accounts-accounts-opportunities-add";
    OpportunityBean newOpp = (OpportunityBean) context.getRequest().getAttribute(
        "OppDetails");
    Organization thisOrganization = null;
    String orgId = context.getRequest().getParameter("orgId");
    String action = (newOpp.getHeader().getId() == -1 ? "insert" : "modify");
    SystemStatus systemStatus = this.getSystemStatus(context);

    if ("modify".equals(action)) {
      permission = "accounts-accounts-opportunities-edit";
    }
    if (!hasPermission(context, permission)) {
      return ("PermissionError");
    }

    //set types
    if ("insert".equals(action)) {
      newOpp.getComponent().setTypeList(
          context.getRequest().getParameterValues("selectedList"));
      newOpp.getComponent().setEnteredBy(getUserId(context));
      newOpp.getComponent().setModifiedBy(getUserId(context));
      newOpp.getHeader().setEnteredBy(getUserId(context));
      newOpp.getHeader().setModifiedBy(getUserId(context));
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
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      if ("insert".equals(action)) {
        isValid = this.validateObject(context, db, newOpp.getHeader());
        isValid = this.validateObject(context, db, newOpp.getComponent()) && isValid;
        if (isValid) {
          recordInserted = newOpp.insert(db, context);
        }
        if (recordInserted) {
          newOpp.getComponent().setContactId(
              newOpp.getHeader().getContactLink());
          newOpp.getComponent().setOrgId(newOpp.getHeader().getAccountLink());
          this.processInsertHook(context, newOpp.getComponent());
        } else {
          LookupList typeSelect = new LookupList(
              db, "lookup_opportunity_types");
          context.getRequest().setAttribute("TypeSelect", typeSelect);
          context.getRequest().setAttribute(
              "TypeList", newOpp.getComponent().getTypeList());
        }
      } else {
        OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
        OpportunityHeader oldHeader = new OpportunityHeader(db, headerId);
        if (!hasAuthority(context, oppHeader.getEnteredBy())) {
          return ("PermissionError");
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
      }
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if ("insert".equals(action)) {
      if (recordInserted) {
        addRecentItem(context, newOpp.getHeader());
        context.getRequest().setAttribute(
            "headerId", String.valueOf(newOpp.getHeader().getId()));
        return (executeCommandDetails(context));
      } else {
        return (executeCommandPrepare(context));
      }
    } else {
      if (resultCount == 1) {
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
            "return").equals("list")) {
          return (executeCommandView(context));
        } else {
          return (executeCommandDetails(context));
        }
      } else {
        if (resultCount == -1 || !isValid) {
          return executeCommandModify(context);
        }
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetailsComponent(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-view")) {
      return ("PermissionError");
    }
    addModuleBean(
        context, "View Accounts", "View Opportunity Component Details");
    OpportunityComponent thisComponent = null;
    Connection db = null;
    //Parameters
    String componentId = context.getRequest().getParameter("id");
    String orgId = context.getRequest().getParameter("orgId");
    String contactId = null;
    try {
      db = this.getConnection(context);
      if (orgId == null || "".equals(orgId)) {
        contactId = context.getRequest().getParameter("contactId");
        Contact contact = new Contact(db, contactId);
        orgId = String.valueOf(contact.getOrgId());
      }
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
      //Load the organization
      Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrg);
      //Load the component
      thisComponent = new OpportunityComponent(
          db, Integer.parseInt(componentId));
      thisComponent.checkEnabledOwnerAccount(db);
      //Load the header
      OpportunityHeader oppHeader = new OpportunityHeader(
          db, thisComponent.getHeaderId());
      context.getRequest().setAttribute("OpportunityHeader", oppHeader);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!hasAuthority(context, thisComponent.getOwner())) {
      return ("PermissionError");
    }
    context.getRequest().setAttribute("OppComponentDetails", thisComponent);
    addRecentItem(context, thisComponent);
    return ("DetailsComponentOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    int headerId = -1;
    addModuleBean(context, "View Accounts", "View Opportunity Details");
    if (context.getRequest().getParameter("headerId") != null) {
      headerId = Integer.parseInt(
          context.getRequest().getParameter("headerId"));
    } else {
      headerId = Integer.parseInt(
          (String) context.getRequest().getAttribute("headerId"));
    }
    String orgId = context.getRequest().getParameter("orgId");
    Connection db = null;
    Organization thisOrganization = null;
    OpportunityHeader thisHeader = null;
    OpportunityComponentList componentList = null;
    SystemStatus systemStatus = this.getSystemStatus(context);

    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("AccountsComponentListInfo");
    }
    PagedListInfo componentListInfo = this.getPagedListInfo(
        context, "AccountsComponentListInfo");
    componentListInfo.setLink(
        "Opportunities.do?command=Details&headerId=" + headerId + "&orgId=" + orgId);
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
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      if (!(hasAuthority(context, thisHeader.getEnteredBy()) || OpportunityHeader.isComponentOwner(
          db, headerId, this.getUserId(context)))) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("opportunityHeader", thisHeader);

      componentList = new OpportunityComponentList();
      componentList.setPagedListInfo(componentListInfo);
      componentList.setOwnerIdRange(this.getUserRange(context));
      componentList.setHeaderId(thisHeader.getId());
      if (thisHeader.isTrashed()) {
        componentList.setIncludeOnlyTrashed(true);
      }
      componentList.buildList(db);
      context.getRequest().setAttribute("ComponentList", componentList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addRecentItem(context, thisHeader);
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String orgId = context.getRequest().getParameter("orgId");
    OpportunityHeader newOpp = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      newOpp = new OpportunityHeader(
          db, context.getRequest().getParameter("id"));
      if (!hasAuthority(context, newOpp.getEnteredBy())) {
        return "PermissionError";
      }
      recordDeleted = newOpp.delete(db, context, getDbNamePath(context));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("orgId", orgId);
        context.getRequest().setAttribute(
            "refreshUrl", "Opportunities.do?command=View&orgId=" + orgId);
        deleteRecentItem(context, newOpp);
        return ("DeleteOK");
      } else {
        processErrors(context, newOpp.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmComponentDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    OpportunityComponent thisComponent = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = context.getRequest().getParameter("id");
    String orgId = context.getRequest().getParameter("orgId");

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisComponent = new OpportunityComponent(db, id);
      if (!hasAuthority(context, thisComponent.getOwner())) {
        return "PermissionError";
      }
      SystemStatus systemStatus = this.getSystemStatus(context);
      htmlDialog.setTitle(
          systemStatus.getLabel("confirmdelete.account.opps.delete"));
      htmlDialog.setShowAndConfirm(false);
      htmlDialog.setDeleteUrl(
          "javascript:window.location.href='OpportunitiesComponents.do?command=DeleteComponent&orgId=" + orgId + "&id=" + id + "'");
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteComponent(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    OpportunityComponent component = null;
    String orgId = context.getRequest().getParameter("orgId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      component = new OpportunityComponent(
          db, context.getRequest().getParameter("id"));
      if (!hasAuthority(context, component.getOwner())) {
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
        context.getRequest().setAttribute(
            "refreshUrl", "Opportunities.do?command=Details&headerId=" + component.getHeaderId() + "&orgId=" + orgId);
        deleteRecentItem(context, component);
        return ("ComponentDeleteOK");
      } else {
        processErrors(context, component.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Modify Contact
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Modify an Opportunity");
    int headerId = Integer.parseInt(
        context.getRequest().getParameter("headerId"));
    String orgId = context.getRequest().getParameter("orgId");
    Connection db = null;
    OpportunityHeader thisHeader = null;
    SystemStatus systemStatus = null;
    ContactList contacts = null;
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      systemStatus = this.getSystemStatus(context);
      contacts = new ContactList();
      contacts.setOrgId(thisHeader.getAccountLink());
      contacts.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
      contacts.addIgnoreTypeId(Contact.LEAD_TYPE);
      contacts.setEmployeesOnly(Constants.FALSE);
      contacts.setEmptyHtmlSelectRecord(
          systemStatus.getLabel("accounts.accounts_add.NoneSelected"));
      contacts.setDefaultContactId(thisHeader.getContactLink());
      contacts.buildList(db);
      context.getRequest().setAttribute("contacts", contacts);
      QuoteList quotes = new QuoteList();
      quotes.setHeaderId(thisHeader.getId());
      quotes.buildList(db);
      context.getRequest().setAttribute("hasQuotes", "" + (quotes.size() > 0));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("opportunityHeader", thisHeader);
    if (!hasAuthority(context, thisHeader.getEnteredBy())) {
      return "PermissionError";
    }
    addRecentItem(context, thisHeader);
    return "PrepareModifyOppOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-delete")) {
      return ("PermissionError");
    }
    HtmlDialog htmlDialog = new HtmlDialog();
    String headerId = context.getRequest().getParameter("headerId");
    String orgId = context.getRequest().getParameter("orgId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      OpportunityHeader thisOpp = new OpportunityHeader(db, headerId);
      if (!hasAuthority(context, thisOpp.getEnteredBy())) {
        return "PermissionError";
      }
      DependencyList dependencies = thisOpp.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      htmlDialog.addButton(
          systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='Opportunities.do?command=Delete&orgId=" + orgId + "&id=" + headerId + "'");
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyComponent(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    OpportunityComponent component = null;
    addModuleBean(context, "View Accounts", "Modify a Component");
    //Parameters
    String componentId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      //Load the component
      component = new OpportunityComponent(db, componentId);
      context.getRequest().setAttribute("ComponentDetails", component);
      //Load the header
      OpportunityHeader oppHeader = new OpportunityHeader(
          db, component.getHeaderId());
      context.getRequest().setAttribute("opportunityHeader", oppHeader);
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
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!hasAuthority(context, component.getOwner())) {
      return ("PermissionError");
    }
    addRecentItem(context, component);
    return executeCommandPrepare(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    boolean isValid = false;
    String headerId = context.getRequest().getParameter("headerId");
    String type = context.getRequest().getParameter("type");

    try {
      db = this.getConnection(context);
      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      OpportunityHeader oldHeader = new OpportunityHeader(db, headerId);
      if (!hasAuthority(context, oppHeader.getEnteredBy())) {
        return ("PermissionError");
      }
      oppHeader.setModifiedBy(getUserId(context));
      oppHeader.setDescription(
          context.getRequest().getParameter("description"));
      if (type.equals("contact")) {
        oppHeader.setContactLink(
            context.getRequest().getParameter("contactLink"));
        oppHeader.setAccountLink(-1);
      } else if (type.equals("org")) {
        oppHeader.setAccountLink(
            context.getRequest().getParameter("accountLink"));
        oppHeader.setContactLink(-1);
      }
      isValid = this.validateObject(context, db, oppHeader);
      if (isValid) {
        resultCount = oppHeader.update(db);
      }
      if (resultCount == 1) {
        this.processUpdateHook(context, oldHeader, oppHeader);
      }
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
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandView(context));
      } else {
        return (executeCommandDetails(context));
      }
    } else {
      if (resultCount == -1 || !isValid) {
        return executeCommandModify(context);
      }
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }
}

