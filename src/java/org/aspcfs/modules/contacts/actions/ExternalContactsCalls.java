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

public final class ExternalContactsCalls extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandView(ActionContext context) {
    Exception errorMessage = null;

    String contactId = context.getRequest().getParameter("contactId");

    addModuleBean(context, "External Contacts", "Calls");

    PagedListInfo callListInfo = this.getPagedListInfo(context, "CallListInfo");
    callListInfo.setLink("/ExternalContactCalls.do?command=View&contactId=" + contactId);
    callListInfo.setItemsPerPage(0);

    Connection db = null;
    CallList callList = new CallList();
    Contact thisContact = null;

    try {
      db = this.getConnection(context);
      callList.setPagedListInfo(callListInfo);
      callList.setContactId(contactId);
      callList.buildList(db);
      
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      
      LookupList callTypeList = new LookupList(db, "lookup_call_types");
      callTypeList.addItem(0, "--None--");
      context.getRequest().setAttribute("CallTypeList", callTypeList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("CallList", callList);
      return ("ViewOK");
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
  
  public String executeCommandForward(ActionContext context) {
    Exception errorMessage = null;
    boolean recordInserted = false;
    
    CFSNote thisNote = new CFSNote();
    thisNote.setEnteredBy(getUserId(context));
    thisNote.setModifiedBy(getUserId(context));
    thisNote.setBody(context.getRequest().getParameter("msgBody"));
    thisNote.setSubject(context.getRequest().getParameter("fwdsubject"));
    thisNote.setReplyId(getUserId(context));
    thisNote.setSentTo(Integer.parseInt(context.getRequest().getParameter("sentTo")));
    
    Connection db = null;
    try {
      db = this.getConnection(context);
      recordInserted = thisNote.insert(db);
      
      if (!recordInserted) {
        processErrors(context, thisNote.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
	    if (!recordInserted) {
		    return (executeCommandForwardForm(context));
   	    } else {
      		    return (executeCommandDetails(context));
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
    Exception errorMessage = null;
    addModuleBean(context, "External Contacts", "Calls");
    
    String callId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    Call thisCall = null;
    Contact thisContact = null;

    try {
      db = this.getConnection(context);
      thisCall = new Call(db, callId);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("CallDetails", thisCall);
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
    Exception errorMessage = null;
    boolean recordDeleted = false;

    String contactId = context.getRequest().getParameter("contactId");     
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
        context.getRequest().setAttribute("contactId", contactId);
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandAdd(ActionContext context) {
    Exception errorMessage = null;

    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;
    
    Connection db = null;
    Statement st = null;
    ResultSet rs = null;

    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      
      LookupList callTypeList = new LookupList(db, "lookup_call_types");
      callTypeList.addItem(0, "--None--");
      context.getRequest().setAttribute("CallTypeList", callTypeList);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "External Contacts", "Calls");
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }



  public String executeCommandModify(ActionContext context) {
    Exception errorMessage = null;
    addModuleBean(context, "External Contacts", "Calls");
    
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    int callId = -1;
    String passedId = context.getRequest().getParameter("id");
    callId = Integer.parseInt(passedId);

    Connection db = null;
    Call thisCall = null;

    try {
      db = this.getConnection(context);
      thisCall = new Call(db, "" + callId);
      
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      
      LookupList callTypeList = new LookupList(db, "lookup_call_types");
      callTypeList.addItem(0, "--None--");
      context.getRequest().setAttribute("CallTypeList", callTypeList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("CallDetails", thisCall);
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandForwardForm(ActionContext context) {
    Exception errorMessage = null;
    addModuleBean(context, "External Contacts", "Calls");
    
    Contact thisContact = null;
    String contactId = context.getRequest().getParameter("contactId");
    
    Call thisCall = null;
    String callId = context.getRequest().getParameter("id");

    Connection db = null;
    UserList list = new UserList();
    list.setEnabled(UserList.TRUE);
    list.setBuildContact(false);
    list.setBuildHierarchy(false);
    list.setBuildPermissions(false);
    
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      thisCall = new Call(db, callId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      list.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("UserList", list);
      context.getRequest().setAttribute("CallDetails", thisCall);
      return ("ForwardFormOK");
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
    Exception errorMessage = null;

    Call thisCall = (Call)context.getFormBean();
    
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      thisCall.setModifiedBy(getUserId(context));
      resultCount = thisCall.update(db, context);
      if (resultCount == -1) {
        thisContact = new Contact(db, contactId);
        context.getRequest().setAttribute("ContactDetails", thisContact);
        
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

