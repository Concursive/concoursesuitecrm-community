package org.aspcfs.modules.pipeline.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.io.*;
import java.text.DateFormat;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.pipeline.beans.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.mycfs.base.CFSNote;

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
    int MINIMIZED_ITEMS_PER_PAGE = 5;
    if (!hasPermission(context, "pipeline-opportunities-calls-view")) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    //Parameters
    String headerId = context.getRequest().getParameter("headerId");

    addModuleBean(context, "View Opportunities", "Opportunity Activities");

    PagedListInfo leadsCallListInfo = this.getPagedListInfo(context, "LeadsCallListInfo");
    leadsCallListInfo.setLink("LeadsCalls.do?command=View&headerId=" + headerId + HTTPUtils.addLinkParams(context.getRequest(), "viewSource"));

    //reset the paged lists
    if ("true".equals(context.getRequest().getParameter("resetList"))) {
      context.getSession().removeAttribute("LeadsCallsListInfo");
      context.getSession().removeAttribute("LeadsCompletedCallsListInfo");
    }

    //Determine the sections to view
    String sectionId = null;
    if (context.getRequest().getParameter("pagedListSectionId") != null) {
      sectionId = context.getRequest().getParameter("pagedListSectionId");
    }

    Connection db = null;
    //Pending Call List
    CallList callList = new CallList();

    //set header id
    callList.setOppHeaderId(Integer.parseInt(headerId));

    //if request from calendar use different pagedlistinfo
    String pendingPagedListId = "LeadsCallsListInfo";

    if (sectionId == null || pendingPagedListId.equals(sectionId)) {
      PagedListInfo callListInfo = this.getPagedListInfo(context, pendingPagedListId, "c.alertdate", null);
      callListInfo.setLink("LeadsCalls.do?command=View&headerId=" + headerId +
          HTTPUtils.addLinkParams(context.getRequest(), "viewSource"));
      if (sectionId == null) {
        if (!callListInfo.getExpandedSelection()) {
          if (callListInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
            callListInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
          }
        } else {
          if (callListInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
            callListInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
          }
        }
      } else if (sectionId.equals(callListInfo.getId())) {
        callListInfo.setExpandedSelection(true);
      }
      callList.setPagedListInfo(callListInfo);
      //set the account
      callList.setOnlyPending(true);
    }

    //Completed Call List
    CallList completedCallList = new CallList();

    //set header id
    completedCallList.setOppHeaderId(Integer.parseInt(headerId));

    //if request from calendar use different pagedlistinfo
    String completedPagedListId = "LeadsCompletedCallsListInfo";

    if (sectionId == null || completedPagedListId.equals(sectionId)) {
      PagedListInfo completedCallListInfo = this.getPagedListInfo(context, completedPagedListId, "c.entered", "desc");
      completedCallListInfo.setLink("LeadsCalls.do?command=View&headerId=" + headerId +
          HTTPUtils.addLinkParams(context.getRequest(), "viewSource"));
      if (sectionId == null) {
        if (!completedCallListInfo.getExpandedSelection()) {
          if (completedCallListInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
            completedCallListInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
          }
        } else {
          if (completedCallListInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
            completedCallListInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
          }
        }
      } else if (sectionId.equals(completedCallListInfo.getId())) {
        completedCallListInfo.setExpandedSelection(true);
      }

      completedCallList.setPagedListInfo(completedCallListInfo);
    }
    try {
      db = this.getConnection(context);

      if (sectionId == null || pendingPagedListId.equals(sectionId)) {
        callList.buildList(db);
      }
      if (sectionId == null || completedPagedListId.equals(sectionId)) {
        completedCallList.buildList(db);
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      //Need the call types for display purposes
      LookupList callTypeList = systemStatus.getLookupList(db, "lookup_call_types");
      context.getRequest().setAttribute("CallTypeList", callTypeList);

      //Need the result types for display purposes
      CallResultList resultList = new CallResultList();
      resultList.buildList(db);
      context.getRequest().setAttribute("callResultList", resultList);

      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", oppHeader);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CallList", callList);
    context.getRequest().setAttribute("CompletedCallList", completedCallList);
    return this.getReturn(context, "View");
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

    addModuleBean(context, "View Opportunities", "Opportunity Activities");

    Connection db = null;
    try {
      db = this.getConnection(context);

      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", oppHeader);

      if (oppHeader.getAccountLink() > -1) {
        ContactList contactList = new ContactList();
        contactList.setOwner(userId);
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(oppHeader.getAccountLink());
        contactList.buildList(db);
        context.getRequest().setAttribute("ContactList", contactList);
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      //Type Lookup
      LookupList callTypeList = systemStatus.getLookupList(db, "lookup_call_types");
      callTypeList.addItem(0, "--None--");
      context.getRequest().setAttribute("CallTypeList", callTypeList);

      //Result Lookup
      CallResultList resultList = new CallResultList();
      resultList.buildList(db);
      context.getRequest().setAttribute("callResultList", resultList);

      //Priority Lookup
      LookupList priorityList = systemStatus.getLookupList(db, "lookup_call_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);

      //Reminder Type Lookup
      LookupList reminderList = systemStatus.getLookupList(db, "lookup_call_reminder");
      reminderList.addItem(0, "--None--");
      context.getRequest().setAttribute("ReminderTypeList", reminderList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //if a different module reuses this action then do a explicit return
    if (context.getRequest().getParameter("actionSource") != null) {
      return this.getReturn(context, "AddCall");
    }
    return this.getReturn(context, "Add");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    String permission = "pipeline-opportunities-calls-add";
    Exception errorMessage = null;
    boolean recordInserted = false;
    int resultCount = -1;
    Contact thisContact = null;
    Call parentCall = null;
    Call previousCall = null;

    //Process the parameters
    String parentId = context.getRequest().getParameter("parentId");
    String action = context.getRequest().getParameter("action");

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    String headerId = context.getRequest().getParameter("headerId");

    addModuleBean(context, "View Opportunities", "Opportunity Activities");

    //Save the current call
    Call thisCall = (Call) context.getFormBean();
    thisCall.setModifiedBy(getUserId(context));

    if (thisCall.getId() > 0) {
      permission = "pipeline-opportunities-calls-edit";
    }
    if (!(hasPermission(context, permission))) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      if (thisCall.getId() > 0) {
        previousCall = new Call(db, thisCall.getId());
        if (!hasViewpointAuthority(db, context, "pipeline", previousCall.getEnteredBy(), userId)) {
          return "PermissionError";
        }
      }
      if (parentId != null && Integer.parseInt(parentId) > -1) {
        parentCall = new Call(db, Integer.parseInt(parentId));
      }
      //update or insert the call
      if (thisCall.getId() > 0) {
        if (thisCall.getStatusId() == Call.COMPLETE) {
          if (previousCall.getAlertDate() == null && thisCall.getAlertDate() != null) {
            thisCall.setStatusId(Call.COMPLETE_FOLLOWUP_PENDING);
          }
        }
        resultCount = thisCall.update(db, context);
      } else {
        //set the status
        if (thisCall.getId() == -1) {
          if ("cancel".equals(action)) {
            thisCall.setStatusId(Call.CANCELED);
          } else {
            if (thisCall.getAlertDate() != null) {
              thisCall.setStatusId(Call.COMPLETE_FOLLOWUP_PENDING);
            } else {
              thisCall.setStatusId(Call.COMPLETE);
            }
          }
        }
        thisCall.setEnteredBy(getUserId(context));
      recordInserted = thisCall.insert(db, context);
      }

      if (!recordInserted && resultCount == -1) {
        processErrors(context, thisCall.getErrors());
        if (thisCall.getId() > 0) {
          addModifyFormElements(db, context, thisCall);
        }
      } else {
        if (parentCall != null) {
          parentCall.setStatusId(Call.COMPLETE);
          parentCall.update(db, context);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if (recordInserted) {
      if (context.getRequest().getParameter("actionSource") != null) {
        return this.getReturn(context, "InsertCall");
      }
      return this.getReturn(context, "Insert");
    } else if (resultCount == 1) {
      if ("list".equals(context.getRequest().getParameter("return"))) {
      return (executeCommandView(context));
      } else {
        return ("UpdateOK");
      }
    }
    if (thisCall.getId() > 0) {
      return this.getReturn(context, "Modify");
    }
    return (executeCommandAdd(context));
  }


  /**
   *  Show the details of the selected call record for an opportunity/lead
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

      if (thisCall.getAlertDate() != null) {
        SystemStatus systemStatus = this.getSystemStatus(context);
        //Need the call types for display purposes
        LookupList reminderList = systemStatus.getLookupList(db, "lookup_call_reminder");
        context.getRequest().setAttribute("ReminderTypeList", reminderList);
      }

      //Result Lookup
      //Need the result types for display purposes
      CallResult thisResult = new CallResult(db, thisCall.getResultId());
      context.getRequest().setAttribute("CallResult", thisResult);

      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", oppHeader);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CallDetails", thisCall);
    addModuleBean(context, "View Opportunities", "Opportunity Activities");
    return this.getReturn(context, "Details");
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
      
      if (oppHeader.getAccountLink() > -1) {
        ContactList contactList = new ContactList();
        contactList.setOwner(userId);
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(oppHeader.getAccountLink());
        contactList.buildList(db);
        context.getRequest().setAttribute("ContactList", contactList);
      }
      
      addModifyFormElements(db, context, thisCall);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Opportunities", "Opportunity Activities");
    context.getRequest().setAttribute("CallDetails", thisCall);
    if (context.getRequest().getParameter("popup") != null) {
      return ("ModifyPopupOK");
    }
    return ("ModifyOK");
  }


  /**
   *  Forward an Opportunity Call
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandForwardCall(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-calls-view"))) {
      return ("PermissionError");
    }

    String msgId = context.getRequest().getParameter("id");
    String headerId = context.getRequest().getParameter("headerId");
    CFSNote newNote = null;
    addModuleBean(context, "View Opportunities", "Opportunity Activities");

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);

      Call thisCall = new Call(db, msgId);
      if (!hasViewpointAuthority(db, context, "pipeline", thisCall.getEnteredBy(), userId)) {
        return "PermissionError";
      }
      newNote = new CFSNote();
      newNote.setBody(
          "Contact Name: " + StringUtils.toString(thisCall.getContactName()) + "\n" +
          "Type: " + StringUtils.toString(thisCall.getCallType()) + "\n" +
          "Length: " + StringUtils.toString(thisCall.getLengthText()) + "\n" +
          "Subject: " + StringUtils.toString(thisCall.getSubject()) + "\n" +
          "Notes: " + StringUtils.toString(thisCall.getNotes()) + "\n" +
          "Entered: " + getUser(context, thisCall.getEnteredBy()).getContact().getNameFirstLast() + " - " + DateUtils.getServerToUserDateTimeString(this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, thisCall.getEntered()) + "\n" +
          "Modified: " + getUser(context, thisCall.getModifiedBy()).getContact().getNameFirstLast() + " - " + DateUtils.getServerToUserDateTimeString(this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, thisCall.getModified()));

      OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(headerId));
      context.getRequest().setAttribute("opportunityHeader", oppHeader);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("Note", newNote);
    return ("ForwardCallOK");
  }


  /**
   *  Send an Opportunity Call
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSendCall(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-calls-view"))) {
      return ("PermissionError");
    }

    String msgId = context.getRequest().getParameter("id");
    String headerId = context.getRequest().getParameter("headerId");
    CFSNote newNote = null;
    addModuleBean(context, "View Opportunities", "Opportunity Activities");

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);

      Call thisCall = new Call(db, msgId);
      if (!hasViewpointAuthority(db, context, "pipeline", thisCall.getEnteredBy(), userId)) {
        return "PermissionError";
      }

      //add the call
      context.getRequest().setAttribute("CallDetails", thisCall);

      OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(headerId));
      context.getRequest().setAttribute("opportunityHeader", oppHeader);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("Note", newNote);
    return ("SendCallOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandComplete(ActionContext context) {

    if (!hasPermission(context, "pipeline-opportunities-calls-edit")) {
      return ("PermissionError");
    }

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    
    addModuleBean(context, "View Opportunities", "Complete Activity");
    //Process parameters
    String headerId = context.getRequest().getParameter("headerId");
    int callId = Integer.parseInt(context.getRequest().getParameter("id"));
    Connection db = null;
    Call thisCall = null;
    try {
      db = this.getConnection(context);
      //Load the previous Call to get details for completed activity
      thisCall = new Call(db, callId);
      context.getRequest().setAttribute("PreviousCallDetails", thisCall);

      OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(headerId));
      context.getRequest().setAttribute("opportunityHeader", oppHeader);
      
      if (oppHeader.getAccountLink() > -1) {
        ContactList contactList = new ContactList();
        contactList.setOwner(userId);
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(oppHeader.getAccountLink());
        contactList.buildList(db);
        context.getRequest().setAttribute("ContactList", contactList);
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      //Type Lookup
      LookupList callTypeList = systemStatus.getLookupList(db, "lookup_call_types");
      callTypeList.addItem(0, "--None--");
      context.getRequest().setAttribute("CallTypeList", callTypeList);

      //Result Lookup
      CallResultList resultList = new CallResultList();
      resultList.buildList(db);
      context.getRequest().setAttribute("callResultList", resultList);

      //Priority Lookup
      LookupList priorityList = systemStatus.getLookupList(db, "lookup_call_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);

      //Reminder Type Lookup
      LookupList reminderList = systemStatus.getLookupList(db, "lookup_call_reminder");
      context.getRequest().setAttribute("ReminderTypeList", reminderList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Add");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCancel(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-calls-delete")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Opportunities", "Opportunity Activities");
    //Process parameters
    String headerId = context.getRequest().getParameter("headerId");
    int callId = Integer.parseInt(context.getRequest().getParameter("id"));
    Connection db = null;
    Call thisCall = null;
    
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    
    try {
      db = this.getConnection(context);
      //Load the previous Call to get details for completed activity
      thisCall = new Call(db, callId);
      context.getRequest().setAttribute("PreviousCallDetails", thisCall);
      //Create an empty next call based on the previous call
      Call nextCall = new Call();
      nextCall.setCallTypeId(thisCall.getAlertCallTypeId());
      nextCall.setSubject(thisCall.getAlertText());
      nextCall.setNotes(thisCall.getFollowupNotes());
      context.getRequest().setAttribute("CallDetails", nextCall);

      //load contact details
      OpportunityHeader oppHeader = new OpportunityHeader(db, Integer.parseInt(headerId));
      context.getRequest().setAttribute("opportunityHeader", oppHeader);

      SystemStatus systemStatus = this.getSystemStatus(context);
      //Type Lookup
      LookupList callTypeList = systemStatus.getLookupList(db, "lookup_call_types");
      callTypeList.addItem(0, "--None--");
      context.getRequest().setAttribute("CallTypeList", callTypeList);
      //Result Lookup
      CallResultList resultList = new CallResultList();
      resultList.buildList(db);
      context.getRequest().setAttribute("callResultList", resultList);
      //Priority Lookup
      LookupList priorityList = systemStatus.getLookupList(db, "lookup_call_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
      
      //contact list
      if (oppHeader.getAccountLink() > -1) {
        ContactList contactList = new ContactList();
        contactList.setOwner(userId);
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(oppHeader.getAccountLink());
        contactList.buildList(db);
        context.getRequest().setAttribute("ContactList", contactList);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Add");
  }


  /**
   *  Adds a feature to the ModifyFormElements attribute of the LeadsCalls object
   *
   *@param  db                The feature to be added to the ModifyFormElements attribute
   *@param  context           The feature to be added to the ModifyFormElements attribute
   *@param  thisCall          The feature to be added to the ModifyFormElements attribute
   *@exception  SQLException  Description of the Exception
   */
  private void addModifyFormElements(Connection db, ActionContext context, Call thisCall) throws SQLException {
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Type Lookup
    LookupList callTypeList = systemStatus.getLookupList(db, "lookup_call_types");
    callTypeList.addItem(0, "--None--");
    context.getRequest().setAttribute("CallTypeList", callTypeList);
    if ("pending".equals(context.getRequest().getParameter("view")) || (thisCall.getStatusId() == Call.COMPLETE && thisCall.getAlertDate() == null)) {
      //Reminder Type Lookup
      LookupList reminderList = systemStatus.getLookupList(db, "lookup_call_reminder");
      context.getRequest().setAttribute("ReminderTypeList", reminderList);

      //Priority Lookup
      LookupList priorityList = systemStatus.getLookupList(db, "lookup_call_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);

      //Result
      CallResult thisResult = new CallResult(db, thisCall.getResultId());
      context.getRequest().setAttribute("CallResult", thisResult);

      //include the callResultList if it is a completed activity with no followup
      if (!"pending".equals(context.getRequest().getParameter("view"))) {
        CallResultList resultList = new CallResultList();
        resultList.buildList(db);
        context.getRequest().setAttribute("callResultList", resultList);
      }
    } else {
      //Result Lookup
      CallResultList resultList = new CallResultList();
      resultList.buildList(db);
      context.getRequest().setAttribute("callResultList", resultList);
    }
  }
}

