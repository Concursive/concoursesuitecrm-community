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

public final class LeadsCalls extends CFSModule {

  /**
   *  Provides a list of calls with a form to add new calls for a selected lead/opportunity
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandView(ActionContext context) {
    Exception errorMessage = null;
    String oppId = context.getRequest().getParameter("oppId");

    addModuleBean(context, "View Opportunities", "Opportunity Calls");

    PagedListInfo leadsCallListInfo = this.getPagedListInfo(context, "LeadsCallListInfo");
    //leadsCallListInfo.setLink("/LeadsCalls.do?command=View&id=" + oppId);
    leadsCallListInfo.setItemsPerPage(0);

    Connection db = null;
    CallList callList = new CallList();
    Opportunity thisOpp = null;

    try {
      db = this.getConnection(context);
      
      callList.setPagedListInfo(leadsCallListInfo);
      callList.setOppId(Integer.parseInt(context.getRequest().getParameter("oppId")));
      callList.buildList(db);
      
      thisOpp = new Opportunity(db, oppId);
      context.getRequest().setAttribute("OpportunityDetails", thisOpp);
      
      	ContactList contactList = new ContactList();
	contactList.setPersonalId(getUserId(context));
	//contactList.setTypeId(Integer.parseInt(typeId));
	contactList.setBuildDetails(false);
	contactList.setOrgId(thisOpp.getAccountLink());
	contactList.buildList(db);
	context.getRequest().setAttribute("ContactList", contactList);
      
      LookupList callTypeList = new LookupList(db, "lookup_call_types");
      callTypeList.addItem(0, "--None--");
      context.getRequest().setAttribute("CallTypeList", callTypeList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("LeadsCallList", callList);
      return ("ViewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Insert a new call record for the selected opportunity/lead
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsert(ActionContext context) {
    Exception errorMessage = null;
    boolean recordInserted = false;
    
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    Call thisCall = (Call)context.getRequest().getAttribute("CallDetails");
    thisCall.setEnteredBy(getUserId(context));
    thisCall.setModifiedBy(getUserId(context));
    
    Connection db = null;
    
    try {
      db = this.getConnection(context);
      recordInserted = thisCall.insert(db, context);
      if (recordInserted) {
        context.getRequest().removeAttribute("CallDetails");
      } else {
        processErrors(context, thisCall.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return (executeCommandView(context));
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Show the details of teh selected call record for an opportunity/lead
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDetails(ActionContext context) {
    Exception errorMessage = null;
    
    String callId = context.getRequest().getParameter("id");
    String oppId = context.getRequest().getParameter("oppId");

    Connection db = null;
    Call thisCall = null;
    Opportunity thisOpp = null;

    try {
      db = this.getConnection(context);
      thisCall = new Call(db, callId);
      thisOpp = new Opportunity(db, oppId);
      context.getRequest().setAttribute("OpportunityDetails", thisOpp);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("CallDetails", thisCall);
      addModuleBean(context, "View Opportunities", "Opportunity Calls");
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Delete a call record from the database
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDelete(ActionContext context) {
    Exception errorMessage = null;
    boolean recordDeleted = false;

    String oppId = context.getRequest().getParameter("oppId");     
    Call thisCall = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisCall = new Call(db, context.getRequest().getParameter("id"));
      recordDeleted = thisCall.delete(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("oppId", oppId);
        return (executeCommandView(context));
      } else {
        processErrors(context, thisCall.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  /**
   *  Show the modify form from which a call can be updated
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */

  public String executeCommandModify(ActionContext context) {
    Exception errorMessage = null;
    
    String oppId = context.getRequest().getParameter("oppId");
    Opportunity thisOpp = null;

    int callId = -1;
    String passedId = context.getRequest().getParameter("id");
    callId = Integer.parseInt(passedId);

    Connection db = null;
    Call thisCall = null;

    try {
      db = this.getConnection(context);
      thisCall = new Call(db, "" + callId);
      
      thisOpp = new Opportunity(db, oppId);
      context.getRequest().setAttribute("OpportunityDetails", thisOpp);
      
      LookupList callTypeList = new LookupList(db, "lookup_call_types");
      callTypeList.addItem(0, "--None--");
      context.getRequest().setAttribute("CallTypeList", callTypeList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "View Opportunities", "Opportunity Calls");
      context.getRequest().setAttribute("CallDetails", thisCall);
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Update a selected call record with new information
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandUpdate(ActionContext context) {
    Exception errorMessage = null;

    Call thisCall = (Call)context.getFormBean();
    
    String oppId = context.getRequest().getParameter("oppId");
    Opportunity thisOpp = null;

    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      thisCall.setModifiedBy(getUserId(context));
      resultCount = thisCall.update(db, context);
      if (resultCount == -1) {
        thisOpp = new Opportunity(db, oppId);
        context.getRequest().setAttribute("OpportunityDetails", thisOpp);
        
        LookupList callTypeList = new LookupList(db, "lookup_call_types");
        callTypeList.addItem(0, "--None--");
        context.getRequest().setAttribute("CallTypeList", callTypeList);
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        processErrors(context, thisCall.getErrors());
        context.getRequest().setAttribute("CallDetails", thisCall);
        return ("ModifyOK");
      } else if (resultCount == 1) {
        return ("UpdateOK");
      } else {
        context.getRequest().setAttribute("Error",
            "<b>This record could not be updated because someone else updated it first.</b><p>" +
            "You can hit the back button to review the changes that could not be committed, " +
            "but you must reload the record and make the changes again.");
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

