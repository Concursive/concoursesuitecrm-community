package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import java.io.*;
import java.sql.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    October 15, 2001
 *@version    $Id: ExternalContactsOpps.java,v 1.3 2002/02/05 19:44:43 chris Exp
 *      $
 */
public final class ExternalContactsOpps extends CFSModule {

  private final static int EMPLOYEE_TYPE = 0;


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandViewOpps(ActionContext context) {
	  
  	if (!(hasPermission(context, "contacts-external_contacts-opportunities-view"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    String contactId = context.getRequest().getParameter("contactId");

    addModuleBean(context, "External Contacts", "Opportunities");

    PagedListInfo oppPagedListInfo = this.getPagedListInfo(context, "OpportunityPagedListInfo");
    oppPagedListInfo.setLink("/ExternalContactsOpps.do?command=ViewOpps&contactId=" + contactId);

    Connection db = null;
    OpportunityList oppList = new OpportunityList();
    Contact thisContact = null;

    try {
      db = this.getConnection(context);
      oppList.setPagedListInfo(oppPagedListInfo);
      oppList.setContactId(contactId);
      oppList.buildList(db);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OpportunityList", oppList);
      return ("ListOppsOK");
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
  public String executeCommandAddOpp(ActionContext context) {
	  
	if (!(hasPermission(context, "contacts-external_contacts-opportunities-add"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    HtmlSelect busTypeSelect = new HtmlSelect();
    busTypeSelect.setSelectName("type");
    busTypeSelect.addItem("N", "New");
    busTypeSelect.addItem("E", "Existing");
    busTypeSelect.build();

    HtmlSelect unitSelect = new HtmlSelect();
    unitSelect.setSelectName("units");
    unitSelect.addItem("M", "Months");
    unitSelect.build();

    Connection db = null;
    Statement st = null;
    ResultSet rs = null;

    try {
      db = this.getConnection(context);

      LookupList stageSelect = new LookupList(db, "lookup_stage");
      context.getRequest().setAttribute("StageList", stageSelect);

      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("BusTypeList", busTypeSelect);
      context.getRequest().setAttribute("UnitTypeList", unitSelect);
      addModuleBean(context, "External Contacts", "Opportunities");
      return ("AddOppOK");
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
  public String executeCommandInsertOpp(ActionContext context) {
	  
	if (!(hasPermission(context, "contacts-external_contacts-opportunities-add"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    boolean recordInserted = false;

    Opportunity newOpp = (Opportunity) context.getRequest().getAttribute("OppDetails");
    //set types
    newOpp.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOpp.setOwner(getUserId(context));
    newOpp.setEnteredBy(getUserId(context));
    newOpp.setModifiedBy(getUserId(context));

    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      recordInserted = newOpp.insert(db, context);
      if (recordInserted) {
        newOpp = new Opportunity(db, "" + newOpp.getId());
        context.getRequest().setAttribute("OppDetails", newOpp);
        addRecentItem(context, newOpp);
      } else {
        processErrors(context, newOpp.getErrors());
      }
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("DetailsOppOK");
      } else {
        return (executeCommandAddOpp(context));
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
  public String executeCommandDetailsOpp(ActionContext context) {
	  
	if (!(hasPermission(context, "contacts-external_contacts-opportunities-view"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    addModuleBean(context, "External Contacts", "Opportunities");

    String oppId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    Opportunity newOpp = null;
    Contact thisContact = null;

    try {
      db = this.getConnection(context);
      newOpp = new Opportunity(db, oppId);
      
      //check whether or not the owner is an active User
      newOpp.checkEnabledOwnerAccount(db);
      
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("OppDetails", newOpp);
      addRecentItem(context, newOpp);
      return ("DetailsOppOK");
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
    String contactId = null;

    if (!(hasPermission(context, "contacts-external_contacts-opportunities-delete"))) {
            return ("PermissionError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = context.getRequest().getParameter("id");
    }
    if (context.getRequest().getParameter("contactId") != null) {
      contactId = context.getRequest().getParameter("contactId");
    } 
    
    try {
      db = this.getConnection(context);
      thisOpp = new Opportunity(db, id);
      htmlDialog.setRelationships(thisOpp.processDependencies(db));
      htmlDialog.setTitle("CFS: Confirm Delete");
      htmlDialog.setHeader("The opportunity you are requesting to delete has the following dependencies within CFS:");
      htmlDialog.addButton("Delete All", "javascript:window.location.href='ExternalContactsOpps.do?command=DeleteOpp&contactId=" + contactId + "&id=" + id + "'");
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
  public String executeCommandDeleteOpp(ActionContext context) {
	  
	if (!(hasPermission(context, "contacts-external_contacts-opportunities-delete"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    boolean recordDeleted = false;
    Opportunity newOpp = null;
    String contactId = null;

    if (context.getRequest().getParameter("contactId") != null) {
      contactId = context.getRequest().getParameter("contactId");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      newOpp = new Opportunity(db, context.getRequest().getParameter("id"));
      recordDeleted = newOpp.delete(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("contactId", contactId);
        context.getRequest().setAttribute("refreshUrl","ExternalContactsOpps.do?command=ViewOpps&contactId="+contactId);
        deleteRecentItem(context, newOpp);
        return ("OppDeleteOK");
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
   *  Modify Contact
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandModifyOpp(ActionContext context) {
	  
	if (!(hasPermission(context, "contacts-external_contacts-opportunities-edit"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    addModuleBean(context, "External Contacts", "Opportunities");

    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

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

    int tempId = -1;
    String passedId = context.getRequest().getParameter("id");
    tempId = Integer.parseInt(passedId);

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

    Connection db = null;
    Statement st = null;
    ResultSet rs = null;
    Opportunity newOpp = null;

    try {
      db = this.getConnection(context);
      StringBuffer sql = new StringBuffer();

      newOpp = new Opportunity(db, "" + tempId);

      LookupList stageSelect = new LookupList(db, "lookup_stage");
      context.getRequest().setAttribute("StageList", stageSelect);

      busTypeSelect.setDefaultKey(newOpp.getType());
      unitSelect.setDefaultKey(newOpp.getUnits());

      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
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
      return ("OppModifyOK");
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
  public String executeCommandUpdateOpp(ActionContext context) {
	  
	if (!(hasPermission(context, "contacts-external_contacts-opportunities-edit"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    Opportunity newOpp = (Opportunity) context.getFormBean();
    newOpp.setTypeList(context.getRequest().getParameterValues("selectedList"));
    Opportunity oldOpp = null;

    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      newOpp.setModifiedBy(getUserId(context));
      resultCount = newOpp.update(db, context);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        processErrors(context, newOpp.getErrors());
        return executeCommandModifyOpp(context);
      } else if (resultCount == 1) {
              if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
		      return (executeCommandViewOpps(context));
	      } else {
		      return ("OppUpdateOK");
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

