package org.aspcfs.modules.pipeline.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.io.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.pipeline.beans.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.contacts.base.*;

/**
 *  Description of the Class
 *
 *@author     matt
 *@created    March 14, 2002
 *@version    $Id: LeadsCalls.java,v 1.9.10.1 2003/02/25 22:36:51 mrajkowski Exp
 *      $
 */
public final class LeadsCalls extends CFSModule {

  /**
   *  Provides a list of calls with a form to add new calls for a selected
   *  lead/opportunity
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-calls-view")) {
      return ("PermissionError");
    }
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Exception errorMessage = null;
    String headerId = context.getRequest().getParameter("headerId");

    addModuleBean(context, "View Opportunities", "Opportunity Calls");

    PagedListInfo leadsCallListInfo = this.getPagedListInfo(context, "LeadsCallListInfo");
    leadsCallListInfo.setLink("LeadsCalls.do?command=View&headerId=" + headerId);

    Connection db = null;
    CallList callList = new CallList();
    try {
      db = this.getConnection(context);
      callList.setPagedListInfo(leadsCallListInfo);
      callList.setOppHeaderId(Integer.parseInt(headerId));
      callList.buildList(db);

      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", oppHeader);
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-calls-add")) {
      return ("PermissionError");
    }

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Exception errorMessage = null;
    String headerId = context.getRequest().getParameter("headerId");

    addModuleBean(context, "View Opportunities", "Opportunity Calls");

    Connection db = null;
    try {
      db = this.getConnection(context);

      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", oppHeader);

      if(oppHeader.getAccountLink() > -1){
        ContactList contactList = new ContactList();
        contactList.setOwner(userId);
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(oppHeader.getAccountLink());
        contactList.buildList(db);
        context.getRequest().setAttribute("ContactList", contactList);
      }

      LookupList callTypeList = new LookupList(db, "lookup_call_types");
      callTypeList.addItem(0, "--None--");
      context.getRequest().setAttribute("CallTypeList", callTypeList);
    } catch (Exception e) {
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


  /**
   *  Insert a new call record for the selected opportunity/lead
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsert(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-calls-add")) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    Call thisCall = (Call) context.getRequest().getAttribute("CallDetails");
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
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandView(context));
  }


  /**
   *  Show the details of teh selected call record for an opportunity/lead
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-calls-view")) {
      return ("PermissionError");
    }

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Exception errorMessage = null;
    String callId = context.getRequest().getParameter("id");
    String headerId = context.getRequest().getParameter("headerId");
    Connection db = null;
    Call thisCall = null;
    try {
      db = this.getConnection(context);
      thisCall = new Call(db, callId);
      if (!hasViewpointAuthority(db, context, "pipeline", thisCall.getEnteredBy(), userId)) {
        return "PermissionError";
      }
      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", oppHeader);
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
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-calls-delete"))) {
      return ("PermissionError");
    }

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Exception errorMessage = null;
    boolean recordDeleted = false;
    String headerId = context.getRequest().getParameter("headerId");
    Call thisCall = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisCall = new Call(db, context.getRequest().getParameter("id"));
      if (!hasViewpointAuthority(db, context, "pipeline", thisCall.getEnteredBy(), userId)) {
        return "PermissionError";
      }
      recordDeleted = thisCall.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("headerId", headerId);
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
    if (!hasPermission(context, "pipeline-opportunities-calls-edit")) {
      return ("PermissionError");
    }

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Exception errorMessage = null;
    String headerId = context.getRequest().getParameter("headerId");
    int callId = Integer.parseInt(context.getRequest().getParameter("id"));
    Connection db = null;
    Call thisCall = null;
    try {
      db = this.getConnection(context);
      thisCall = new Call(db, callId);
      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", oppHeader);

      if (!hasViewpointAuthority(db, context, "pipeline", thisCall.getEnteredBy(), userId)) {
        return "PermissionError";
      }

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
      if (context.getRequest().getParameter("popup") != null) {
        return ("ModifyPopupOK");
      }
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
    if (!hasPermission(context, "pipeline-opportunities-calls-edit")) {
      return ("PermissionError");
    }
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Call thisCall = (Call) context.getFormBean();
    String headerId = context.getRequest().getParameter("headerId");
    Connection db = null;
    int resultCount = 0;
    try {
      db = this.getConnection(context);
      thisCall.setModifiedBy(getUserId(context));
      Call oldCall = new Call(db, context.getRequest().getParameter("id"));
      if (!hasViewpointAuthority(db, context, "pipeline", oldCall.getEnteredBy(), userId)) {
        return "PermissionError";
      }
      resultCount = thisCall.update(db, context);
      if (resultCount == -1) {
        OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
        context.getRequest().setAttribute("opportunityHeader", oppHeader);

        LookupList callTypeList = new LookupList(db, "lookup_call_types");
        callTypeList.addItem(0, "--None--");
        context.getRequest().setAttribute("CallTypeList", callTypeList);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1) {
      processErrors(context, thisCall.getErrors());
      context.getRequest().setAttribute("CallDetails", thisCall);
      return "ModifyOK";
    } else if (resultCount == 1) {
      if ("list".equals(context.getRequest().getParameter("return"))) {
        context.getRequest().removeAttribute("CallDetails");
        return (executeCommandView(context));
      } else if ("true".equals(context.getRequest().getParameter("popup"))) {
        return "PopupCloseOK";
      } else {
        return "UpdateOK";
      }
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return "UserError";
    }
  }
}

