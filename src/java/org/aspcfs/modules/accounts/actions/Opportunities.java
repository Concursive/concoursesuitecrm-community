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

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    October 17, 2001
 *@version    $Id$
 */
public final class Opportunities extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
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
   *@since
   */

  public String executeCommandView(ActionContext context) {
	  
	if (!(hasPermission(context, "accounts-accounts-opportunities-view"))) {
	    return ("PermissionError");
    	}
	

    Exception errorMessage = null;

    String orgId = context.getRequest().getParameter("orgId");

    addModuleBean(context, "View Accounts", "View Opportunity Details");

    PagedListInfo oppPagedInfo = this.getPagedListInfo(context, "OpportunityPagedInfo");
    oppPagedInfo.setLink("/Opportunities.do?command=View&orgId=" + orgId);

    Connection db = null;
    OpportunityList oppList = new OpportunityList();
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);
      oppList.setPagedListInfo(oppPagedInfo);
      oppList.setOrgId(orgId);
      oppList.setOwnerIdRange(this.getUserRange(context));
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */

  public String executeCommandAdd(ActionContext context) {
	  
	if (!(hasPermission(context, "accounts-accounts-opportunities-add"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    HtmlSelect busTypeSelect = new HtmlSelect();
    busTypeSelect.setSelectName("type");
    busTypeSelect.addItem("N", "New");
    busTypeSelect.addItem("E", "Existing");
    busTypeSelect.build();

    HtmlSelect unitSelect = new HtmlSelect();
    unitSelect.setSelectName("units");
    unitSelect.addItem("M", "Months");
    //unitSelect.addItem("D", "Days");
    //unitSelect.addItem("W", "Weeks");
    //unitSelect.addItem("Y", "Years");
    unitSelect.build();

    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrganization = null;

    Connection db = null;
    Statement st = null;
    ResultSet rs = null;

    try {
      db = this.getConnection(context);

      LookupList stageSelect = new LookupList(db, "lookup_stage");
      context.getRequest().setAttribute("StageList", stageSelect);

      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("BusTypeList", busTypeSelect);
      context.getRequest().setAttribute("UnitTypeList", unitSelect);
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsert(ActionContext context) {
	  
    if (!(hasPermission(context, "accounts-accounts-opportunities-add"))) {
	    return ("PermissionError");
    }
	
    Exception errorMessage = null;
    boolean recordInserted = false;

    Opportunity newOpp = (Opportunity) context.getRequest().getAttribute("OppDetails");
    //set types
    newOpp.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOpp.setEnteredBy(getUserId(context));
    newOpp.setOwner(getUserId(context));
    newOpp.setModifiedBy(getUserId(context));

    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrganization = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      recordInserted = newOpp.insert(db, context);
      if (recordInserted) {
        newOpp = new Opportunity(db, "" + newOpp.getId());
        context.getRequest().setAttribute("OppDetails", newOpp);
        addRecentItem(context, newOpp);
        thisOrganization = new Organization(db, Integer.parseInt(orgId));
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
      } else {
        processErrors(context, newOpp.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Insert an Opportunity");
    if (errorMessage == null) {
      if (recordInserted) {
        return ("DetailsOK");
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDetails(ActionContext context) {
	  
	if (!(hasPermission(context, "accounts-accounts-opportunities-view"))) {
	    return ("PermissionError");
    	}
	
    addModuleBean(context, "View Accounts", "View Opportunity Details");
    Exception errorMessage = null;

    String orgId = context.getRequest().getParameter("orgId");
    String oppId = context.getRequest().getParameter("id");

    Connection db = null;
    Opportunity newOpp = null;
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);
      newOpp = new Opportunity(db, oppId);
      
      //check whether or not the owner is an active User
      newOpp.checkEnabledOwnerAccount(db);
      
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OppDetails", newOpp);
      addRecentItem(context, newOpp);
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
   *@since
   */
  public String executeCommandDelete(ActionContext context) {
	  
	if (!(hasPermission(context, "accounts-accounts-opportunities-delete"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    boolean recordDeleted = false;
    Opportunity newOpp = null;
    Organization thisOrganization = null;

    String orgId = context.getRequest().getParameter("orgId");

    Connection db = null;
    try {
      db = this.getConnection(context);
      newOpp = new Opportunity(db, context.getRequest().getParameter("id"));
      recordDeleted = newOpp.delete(db, context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Delete an opportunity");
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl","Opportunities.do?command=View&orgId="+thisOrganization.getId());
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
   *  Modify Contact
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandModify(ActionContext context) {
	  
	if (!(hasPermission(context, "accounts-accounts-opportunities-edit"))) {
	    return ("PermissionError");
    	}
	
    addModuleBean(context, "View Accounts", "Modify an Opportunity");
    Exception errorMessage = null;

    HtmlSelect busTypeSelect = new HtmlSelect();
    busTypeSelect.setSelectName("type");
    busTypeSelect.addItem("N", "New");
    busTypeSelect.addItem("E", "Existing");

    HtmlSelect unitSelect = new HtmlSelect();
    unitSelect.setSelectName("units");
    unitSelect.addItem("M", "Months");
    //unitSelect.addItem("D", "Days");
    //unitSelect.addItem("W", "Weeks");
    //unitSelect.addItem("Y", "Years");

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //this is how we get the multiple-level heirarchy...recursive function.

    User thisRec = thisUser.getUserRecord();

    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getNameLast() + ", " + thisUser.getNameFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    context.getRequest().setAttribute("UserList", userList);

    int tempId = -1;
    String passedId = context.getRequest().getParameter("id");
    tempId = Integer.parseInt(passedId);

    String orgId = context.getRequest().getParameter("orgId");

    Connection db = null;
    Statement st = null;
    ResultSet rs = null;
    Opportunity newOpp = null;
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);

      newOpp = new Opportunity(db, "" + tempId);

      busTypeSelect.setDefaultKey(newOpp.getType());
      unitSelect.setDefaultKey(newOpp.getUnits());

      LookupList stageSelect = new LookupList(db, "lookup_stage");
      context.getRequest().setAttribute("StageList", stageSelect);

      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OppDetails", newOpp);
      addRecentItem(context, newOpp);
      context.getRequest().setAttribute("BusTypeList", busTypeSelect);
      context.getRequest().setAttribute("UnitTypeList", unitSelect);
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandConfirmDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Opportunity thisOpp = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;
    String orgId = null;

      if (!(hasPermission(context, "accounts-accounts-opportunities-delete"))) {
              return ("PermissionError");
      }

    if (context.getRequest().getParameter("id") != null) {
      id = context.getRequest().getParameter("id");
    }
    if (context.getRequest().getParameter("orgId") != null) {
      orgId = context.getRequest().getParameter("orgId");
    } 
    
    try {
      db = this.getConnection(context);
      thisOpp = new Opportunity(db, id);
      htmlDialog.setRelationships(thisOpp.processDependencies(db));
      
        htmlDialog.setTitle("CFS: Confirm Delete");
        htmlDialog.setHeader("The object you are requesting to delete has the following dependencies within CFS:");
        htmlDialog.addButton("Delete All", "javascript:window.location.href='Opportunities.do?command=Delete&orgId=" + orgId + "&id=" + id + "'");
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandUpdate(ActionContext context) {
	  
	if (!(hasPermission(context, "accounts-accounts-opportunities-edit"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    Opportunity newOpp = (Opportunity) context.getFormBean();
    newOpp.setTypeList(context.getRequest().getParameterValues("selectedList"));

    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrganization = null;

    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      newOpp.setModifiedBy(getUserId(context));
      resultCount = newOpp.update(db, context);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        processErrors(context, newOpp.getErrors());
        return executeCommandModify(context);
      } else if (resultCount == 1) {
              if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
		      return (executeCommandView(context));
	      } else {
		      return ("UpdateOK");
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

