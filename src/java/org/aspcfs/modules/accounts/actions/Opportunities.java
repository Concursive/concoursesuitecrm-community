package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.pipeline.beans.OpportunityBean;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    October 17, 2001
 *@version    $Id: Opportunities.java,v 1.17 2003/01/07 16:29:07 mrajkowski Exp
 *      $
 */
public final class Opportunities extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandHome(ActionContext context) {
    addModuleBean(context, "Opportunities", "Opportunities Home");
    return ("HomeOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String orgId = context.getRequest().getParameter("orgId");
    addModuleBean(context, "View Accounts", "View Opportunity Details");

    PagedListInfo oppPagedInfo = this.getPagedListInfo(context, "OpportunityPagedInfo");
    oppPagedInfo.setLink("Opportunities.do?command=View&orgId=" + orgId);

    Connection db = null;
    OpportunityHeaderList oppList = new OpportunityHeaderList();
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);
      oppList.setPagedListInfo(oppPagedInfo);
      oppList.setBuildTotalValues(true);
      oppList.setOrgId(orgId);
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
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsertOppComponent(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-opportunities-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;
    Organization thisOrg = null;
    String orgId = null;
    Connection db = null;

    OpportunityComponent newComponent = (OpportunityComponent) context.getFormBean();

    if (context.getRequest().getParameter("orgId") != null) {
      orgId = context.getRequest().getParameter("orgId");
    }

    //set types
    newComponent.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newComponent.setEnteredBy(getUserId(context));
    newComponent.setModifiedBy(getUserId(context));

    try {
      db = this.getConnection(context);

      recordInserted = newComponent.insert(db, context);

      if (recordInserted) {
        context.getRequest().setAttribute("OppComponentDetails", newComponent);
        addRecentItem(context, newComponent);
      } else {
        processErrors(context, newComponent.getErrors());
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute("TypeList", newComponent.getTypeList());
      }
      thisOrg = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrg);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return (executeCommandDetails(context));
      } else {
        return (executeCommandAddOppComponent(context));
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
  public String executeCommandAddOppComponent(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-add")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String orgId = context.getRequest().getParameter("orgId");
    String headerId = context.getRequest().getParameter("headerId");
    Connection db = null;

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = thisUser.getUserRecord();
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);

    try {
      db = this.getConnection(context);
      buildFormElements(db, context);
      //get the header info
      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("OpportunityHeader", oppHeader);
      //build the org
      Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrg);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "View Accounts", "Add Opportunity Component");
      return ("AddOppComponentOK");
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
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-add")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrganization = null;
    Connection db = null;

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = thisUser.getUserRecord();
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);
    try {
      db = this.getConnection(context);
      buildFormElements(db, context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "View Accounts", "Add Opportunity");
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db       Description of the Parameter
   *@param  context  Description of the Parameter
   */
  public void buildFormElements(Connection db, ActionContext context) {
    Exception errorMessage = null;

    HtmlSelect busTypeSelect = new HtmlSelect();
    busTypeSelect.setSelectName("type");
    busTypeSelect.addItem("N", "New");
    busTypeSelect.addItem("E", "Existing");
    busTypeSelect.build();

    HtmlSelect unitSelect = new HtmlSelect();
    unitSelect.setSelectName("units");
    unitSelect.addItem("M", "Months");
    unitSelect.build();

    context.getRequest().setAttribute("BusTypeList", busTypeSelect);
    context.getRequest().setAttribute("UnitTypeList", unitSelect);

    try {
      LookupList stageSelect = new LookupList(db, "lookup_stage");
      context.getRequest().setAttribute("StageList", stageSelect);
    } catch (Exception e) {
      errorMessage = e;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-add")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordInserted = false;
    OpportunityBean newOpp = (OpportunityBean) context.getRequest().getAttribute("OppDetails");
    Organization thisOrganization = null;
    String orgId = context.getRequest().getParameter("orgId");

    //set types
    newOpp.getComponent().setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOpp.getComponent().setEnteredBy(getUserId(context));
    newOpp.getComponent().setModifiedBy(getUserId(context));
    newOpp.getHeader().setEnteredBy(getUserId(context));
    newOpp.getHeader().setModifiedBy(getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      recordInserted = newOpp.insert(db, context);
      if (!recordInserted) {
        processErrors(context, newOpp.getHeader().getErrors());
        processErrors(context, newOpp.getComponent().getErrors());
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute("TypeList", newOpp.getComponent().getTypeList());
      }
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    //addModuleBean(context, "View Accounts", "Insert an Opportunity");
    if (errorMessage == null) {
      if (recordInserted) {
        addRecentItem(context, newOpp.getHeader());
        context.getRequest().setAttribute("headerId", String.valueOf(newOpp.getHeader().getId()));
        return (executeCommandDetails(context));
      } else {
        return (executeCommandAdd(context));
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
  public String executeCommandDetailsComponent(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "View Accounts", "View Opportunity Component Details");
    String componentId = context.getRequest().getParameter("id");
    String orgId = context.getRequest().getParameter("orgId");
    OpportunityComponent thisComponent = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
      thisComponent = new OpportunityComponent(db, Integer.parseInt(componentId));
      thisComponent.checkEnabledOwnerAccount(db);
      context.getRequest().setAttribute("OrgDetails", thisOrg);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (!hasAuthority(context, thisComponent.getOwner())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("OppComponentDetails", thisComponent);
      addRecentItem(context, thisComponent);
      return ("DetailsComponentOK");
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
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    int headerId = -1;
    addModuleBean(context, "View Accounts", "View Opportunity Details");
    if (context.getRequest().getParameter("headerId") != null) {
      headerId = Integer.parseInt(context.getRequest().getParameter("headerId"));
    } else {
      headerId = Integer.parseInt((String) context.getRequest().getAttribute("headerId"));
    }
    String orgId = context.getRequest().getParameter("orgId");
    Connection db = null;
    Organization thisOrganization = null;
    OpportunityHeader thisHeader = null;
    OpportunityComponentList componentList = null;

    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("AccountsComponentListInfo");
    }
    PagedListInfo componentListInfo = this.getPagedListInfo(context, "AccountsComponentListInfo");
    componentListInfo.setLink("Opportunities.do?command=Details&headerId=" + headerId + "&orgId=" + orgId);
    try {
      db = this.getConnection(context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      if (!(hasAuthority(context, thisHeader.getEnteredBy()) || thisHeader.isComponentOwner(db, headerId, this.getUserId(context)))) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("HeaderDetails", thisHeader);

      componentList = new OpportunityComponentList();
      componentList.setPagedListInfo(componentListInfo);
      componentList.setOwnerIdRange(this.getUserRange(context));
      componentList.setHeaderId(thisHeader.getId());
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
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
      newOpp = new OpportunityHeader(db, context.getRequest().getParameter("id"));
      if (!hasAuthority(context, newOpp.getEnteredBy())) {
        return "PermissionError";
      }
      recordDeleted = newOpp.delete(db, context, this.getPath(context, "opportunities"));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("orgId", orgId);
        context.getRequest().setAttribute("refreshUrl", "Opportunities.do?command=View&orgId=" + orgId);
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      htmlDialog.setTitle("CFS: Account Management Opportunities");
      htmlDialog.setShowAndConfirm(false);
      htmlDialog.setDeleteUrl("javascript:window.location.href='OpportunitiesComponents.do?command=DeleteComponent&orgId=" + orgId + "&id=" + id + "'");
      htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      component = new OpportunityComponent(db, context.getRequest().getParameter("id"));
      if (!hasAuthority(context, component.getOwner())) {
        return "PermissionError";
      }
      recordDeleted = component.delete(db, context, this.getPath(context, "opportunities"));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", "Opportunities.do?command=Details&headerId=" + component.getHeaderId() + "&orgId=" + orgId);
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
   *  Modify Contact
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-edit")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "View Accounts", "Modify an Opportunity");
    int headerId = Integer.parseInt(context.getRequest().getParameter("headerId"));
    String orgId = context.getRequest().getParameter("orgId");
    Connection db = null;
    OpportunityHeader thisHeader = null;
    Organization thisOrg = null;
    try {
      db = this.getConnection(context);
      thisHeader = new OpportunityHeader();
      thisHeader.setBuildComponentCount(true);
      thisHeader.queryRecord(db, headerId);
      thisOrg = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrg);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (!hasAuthority(context, thisHeader.getEnteredBy())) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("HeaderDetails", thisHeader);
      addRecentItem(context, thisHeader);
      return ("ModifyOK");
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
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String headerId = context.getRequest().getParameter("headerId");
    String orgId = context.getRequest().getParameter("orgId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      OpportunityHeader thisOpp = new OpportunityHeader(db, headerId);
      if (!hasAuthority(context, thisOpp.getEnteredBy())) {
        return "PermissionError";
      }
      DependencyList dependencies = thisOpp.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());
      htmlDialog.setTitle("CFS: Confirm Delete");
      htmlDialog.setHeader("This object has the following dependencies within CFS:");
      htmlDialog.addButton("Delete All", "javascript:window.location.href='Opportunities.do?command=Delete&orgId=" + orgId + "&id=" + headerId + "'");
      htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyComponent(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-edit")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Organization thisOrg = null;
    Connection db = null;
    OpportunityComponent component = null;
    addModuleBean(context, "View Accounts", "Modify a Component");

    String orgId = context.getRequest().getParameter("orgId");
    String componentId = context.getRequest().getParameter("id");

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = thisUser.getUserRecord();
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);

    try {
      db = this.getConnection(context);
      buildFormElements(db, context);
      component = new OpportunityComponent(db, componentId);
      thisOrg = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrg);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (!hasAuthority(context, component.getOwner())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("OppComponentDetails", component);
      addRecentItem(context, component);
      return ("ComponentModifyOK");
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
  public String executeCommandUpdateComponent(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-opportunities-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    Organization thisOrg = null;
    OpportunityHeader header = null;

    OpportunityComponent component = (OpportunityComponent) context.getFormBean();
    component.setTypeList(context.getRequest().getParameterValues("selectedList"));

    String orgId = context.getRequest().getParameter("orgId");

    try {
      db = this.getConnection(context);
      OpportunityComponent oldComponent = new OpportunityComponent(db, component.getId());
      if (!hasAuthority(context, oldComponent.getOwner())) {
        return ("PermissionError");
      }
      component.setModifiedBy(getUserId(context));
      resultCount = component.update(db, context);
      if (resultCount == 1) {
        component.queryRecord(db, component.getId());
        context.getRequest().setAttribute("OppComponentDetails", component);
      }
      if (resultCount == -1) {
        UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
        User thisRec = thisUser.getUserRecord();
        UserList shortChildList = thisRec.getShortChildList();
        UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
        userList.setMyId(getUserId(context));
        userList.setMyValue(thisUser.getContact().getNameLastFirst());
        userList.setIncludeMe(true);
        userList.setExcludeDisabledIfUnselected(true);
        context.getRequest().setAttribute("UserList", userList);
        LookupList typeSelect = new LookupList(db, "lookup_opportunity_types");
        context.getRequest().setAttribute("TypeSelect", typeSelect);
        context.getRequest().setAttribute("TypeList", component.getTypeList());
        buildFormElements(db, context);
      }
      thisOrg = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrg);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        processErrors(context, component.getErrors());
        return "ComponentModifyOK";
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
          return (executeCommandDetails(context));
        } else {
          return ("DetailsComponentOK");
        }
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
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
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-opportunities-edit")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    String headerId = context.getRequest().getParameter("headerId");

    try {
      db = this.getConnection(context);
      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      if (!hasAuthority(context, oppHeader.getEnteredBy())) {
        return ("PermissionError");
      }
      oppHeader.setModifiedBy(getUserId(context));
      oppHeader.setDescription(context.getRequest().getParameter("description"));
      resultCount = oppHeader.update(db);
      if (resultCount == -1) {
        processErrors(context, oppHeader.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return executeCommandModify(context);
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
          return (executeCommandView(context));
        } else {
          return (executeCommandDetails(context));
        }
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

}

