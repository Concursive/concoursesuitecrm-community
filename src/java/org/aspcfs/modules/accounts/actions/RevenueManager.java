package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import java.sql.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;


public final class RevenueManager extends CFSModule {

  public String executeCommandAdd(ActionContext context) {
    addModuleBean(context, "View Accounts", "Add Revenue to Account");
    
    Exception errorMessage = null;
    Connection db = null;
    
    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);
      buildFormElements(context, db);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandView(ActionContext context) {
    addModuleBean(context, "View Accounts", "View Revenue List");
    
    Exception errorMessage = null;

    String orgid = context.getRequest().getParameter("orgId");

    PagedListInfo revenueInfo = this.getPagedListInfo(context, "RevenueListInfo");
    revenueInfo.setLink("/RevenueManager.do?command=View&orgId=" + orgid);

    Connection db = null;
    RevenueList revenueList = new RevenueList();
    Organization thisOrganization = null;
    
    try {
      db = this.getConnection(context);
      revenueList.setPagedListInfo(revenueInfo);
      revenueList.setOrgId(Integer.parseInt(orgid));
      revenueList.buildList(db);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("RevenueList", revenueList);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      return ("ViewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }
  
  public String executeCommandDelete(ActionContext context) {
    Exception errorMessage = null;
    boolean recordDeleted = false;
    
    Revenue thisRevenue = null;
    Organization thisOrganization = null;
    
    String orgId = context.getRequest().getParameter("orgId");
    Connection db = null;
    
    try {
      db = this.getConnection(context);
      thisRevenue = new Revenue(db, context.getRequest().getParameter("id"));
      recordDeleted = thisRevenue.delete(db);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    
    addModuleBean(context, "View Accounts", "Delete Revenue");
    
    if (errorMessage == null) {
      //context.getRequest().setAttribute("orgId", orgId);
      if (recordDeleted) {
        return ("DeleteOK");
      } else {
        processErrors(context, thisRevenue.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
   public String executeCommandInsert(ActionContext context) {
	
    addModuleBean(context, "View Accounts", "Insert Revenue");
    Exception errorMessage = null;
    boolean recordInserted = false;
    Connection db = null;
    
    Revenue thisRevenue = null;
    Revenue newRevenue = null;
    Organization thisOrganization = null;
    
    thisRevenue = (Revenue)context.getFormBean();
    thisRevenue.setEnteredBy(getUserId(context));
    thisRevenue.setOwner(getUserId(context));
    
    try {
      db = this.getConnection(context);
      recordInserted = thisRevenue.insert(db);
      if (recordInserted) {
        newRevenue = new Revenue(db, "" + thisRevenue.getId());
        context.getRequest().setAttribute("Revenue", newRevenue);
        thisOrganization = new Organization(db, newRevenue.getOrgId());
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
      } else {
        processErrors(context, thisRevenue.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("DetailsOK");
	//return("AddOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandModify(ActionContext context) {
    addModuleBean(context, "View Accounts", "Modify Revenue");
    Exception errorMessage = null;

    String orgid = context.getRequest().getParameter("orgId");
    String passedId = context.getRequest().getParameter("id");
    
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //this is how we get the multiple-level heirarchy...recursive function.

    User thisRec = thisUser.getUserRecord();

    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getNameLast() + ", " + thisUser.getNameFirst());
    userList.setIncludeMe(true);
    context.getRequest().setAttribute("UserList", userList);
    
    //end

    Connection db = null;
    Revenue thisRevenue = null;
    Organization thisOrganization = null;
    
    try {
      db = this.getConnection(context);
      thisRevenue = new Revenue(db, "" + passedId);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));
      buildFormElements(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("Revenue", thisRevenue);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandDetails(ActionContext context) {
    addModuleBean(context, "View Accounts", "View Revenue Details");
    Exception errorMessage = null;

    String revenueId = context.getRequest().getParameter("id");
    
    Connection db = null;
    Revenue newRevenue = null;
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);
      newRevenue = new Revenue(db, revenueId);
      thisOrganization = new Organization(db, newRevenue.getOrgId());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("Revenue", newRevenue);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandUpdate(ActionContext context) {
    Exception errorMessage = null;

    Revenue newRevenue = (Revenue)context.getFormBean();
    
    Organization thisOrganization = null;
    String orgid = context.getRequest().getParameter("orgId");
    
    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      newRevenue.setModifiedBy(getUserId(context));
      resultCount = newRevenue.update(db);
      if (resultCount == -1) {
        processErrors(context, newRevenue.getErrors());
        buildFormElements(context, db);
        thisOrganization = new Organization(db, Integer.parseInt(orgid));
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
        //return ("ModifyOK");
	return (executeCommandModify(context));
      } else if (resultCount == 1) {
        return ("UpdateOK");
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  protected void buildFormElements(ActionContext context, Connection db) throws SQLException {
    RevenueTypeList rtl = new RevenueTypeList(db);
    rtl.addItem(0, "--None--");
    context.getRequest().setAttribute("RevenueTypeList", rtl);

    LookupList monthList = new LookupList(db, "lookup_months");
    context.getRequest().setAttribute("MonthList", monthList);

/**
    LookupList emailTypeList = new LookupList(db, "lookup_contactemail_types");
    context.getRequest().setAttribute("ContactEmailTypeList", emailTypeList);

    LookupList addressTypeList = new LookupList(db, "lookup_contactaddress_types");
    context.getRequest().setAttribute("ContactAddressTypeList", addressTypeList);
*/
  }
 
}

