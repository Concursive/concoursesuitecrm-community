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
package org.aspcfs.modules.pipeline.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.mycfs.base.CFSNote;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.aspcfs.utils.web.ViewpointInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashMap;

/**
 * Description of the Class
 *
 * @author matt
 * @version $Id: LeadsCalls.java,v 1.9.10.1 2003/02/25 22:36:51 mrajkowski Exp
 *          $
 * @created March 14, 2002
 */
public final class LeadsCalls extends CFSModule {

  /**
   * Provides a list of calls with a form to add new calls for a selected
   * lead/opportunity
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandView(ActionContext context) {
    int MINIMIZED_ITEMS_PER_PAGE = 5;
    if (!hasPermission(context, "pipeline-opportunities-calls-view")) {
      return ("PermissionError");
    }
    //Parameters
    String headerId = context.getRequest().getParameter("headerId");

    addModuleBean(context, "View Opportunities", "Opportunity Activities");

    PagedListInfo leadsCallListInfo = this.getPagedListInfo(
        context, "LeadsCallListInfo");
    leadsCallListInfo.setLink(
        "LeadsCalls.do?command=View&headerId=" + headerId + RequestUtils.addLinkParams(
            context.getRequest(), "viewSource"));

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
      PagedListInfo callListInfo = this.getPagedListInfo(
          context, pendingPagedListId, "c.alertdate", null);
      callListInfo.setLink(
          "LeadsCalls.do?command=View&headerId=" + headerId +
          RequestUtils.addLinkParams(context.getRequest(), "viewSource"));
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
      PagedListInfo completedCallListInfo = this.getPagedListInfo(
          context, completedPagedListId, "c.entered", "desc");
      completedCallListInfo.setLink(
          "LeadsCalls.do?command=View&headerId=" + headerId +
          RequestUtils.addLinkParams(context.getRequest(), "viewSource"));
      if (sectionId == null) {
        if (!completedCallListInfo.getExpandedSelection()) {
          if (completedCallListInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
            completedCallListInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
          }
        } else {
          if (completedCallListInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
            completedCallListInfo.setItemsPerPage(
                PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
          }
        }
      } else if (sectionId.equals(completedCallListInfo.getId())) {
        completedCallListInfo.setExpandedSelection(true);
      }

      completedCallList.setPagedListInfo(completedCallListInfo);
    }
    try {
      db = this.getConnection(context);

      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", oppHeader);

      if (oppHeader.isTrashed()) {
        callList.setIncludeOnlyTrashed(true);
      }
      if (sectionId == null || pendingPagedListId.equals(sectionId)) {
        callList.buildList(db);
      }
      if (oppHeader.isTrashed()) {
        completedCallList.setIncludeOnlyTrashed(true);
      }
      if (sectionId == null || completedPagedListId.equals(sectionId)) {
        completedCallList.buildList(db);
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      //Need the call types for display purposes
      LookupList callTypeList = systemStatus.getLookupList(
          db, "lookup_call_types");
      context.getRequest().setAttribute("CallTypeList", callTypeList);

      //Need the result types for display purposes
      CallResultList resultList = new CallResultList();
      resultList.buildList(db);
      context.getRequest().setAttribute("callResultList", resultList);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CallList", callList);
    context.getRequest().setAttribute("CompletedCallList", completedCallList);
    return getReturn(context, "View");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-calls-add")) {
      return ("PermissionError");
    }

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    String headerId = context.getRequest().getParameter("headerId");

    addModuleBean(context, "View Opportunities", "Opportunity Activities");

    Connection db = null;
    try {
      db = this.getConnection(context);

      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", oppHeader);

      if (oppHeader.getAccountLink() > -1) {
        Organization oppOrg = new Organization(db, oppHeader.getAccountLink());
        ContactList contactList = new ContactList();
        if (oppOrg.getOwner() != userId && userId != this.getUserId(context)) {
          contactList.setOwner(userId);
        } else if (oppOrg.getOwner() == this.getUserId(context)) {
          contactList.setOwner(this.getUserId(context));
        }
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(oppHeader.getAccountLink());
        contactList.buildList(db);
        context.getRequest().setAttribute("ContactList", contactList);
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      //Type Lookup
      LookupList callTypeList = systemStatus.getLookupList(
          db, "lookup_call_types");
      callTypeList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CallTypeList", callTypeList);

      //Result Lookup
      CallResultList resultList = new CallResultList();
      resultList.buildList(db);
      context.getRequest().setAttribute("callResultList", resultList);

      //Priority Lookup
      LookupList priorityList = systemStatus.getLookupList(
          db, "lookup_call_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);

      //Reminder Type Lookup
      LookupList reminderList = systemStatus.getLookupList(
          db, "lookup_call_reminder");
      reminderList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("ReminderTypeList", reminderList);
      context.getRequest().setAttribute("systemStatus", systemStatus);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //if a different module reuses this action then do a explicit return
    if (context.getRequest().getParameter("actionSource") != null) {
      return getReturn(context, "AddCall");
    }
    return getReturn(context, "Add");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    String permission = "pipeline-opportunities-calls-add";
    boolean recordInserted = false;
    boolean isValid = false;
    int resultCount = -1;
    Call parentCall = null;
    Call previousCall = null;
    int tmpStatusId = -1;
    addModuleBean(context, "View Opportunities", "Opportunity Activities");
    //Process the parameters
    String parentId = context.getRequest().getParameter("parentId");
    String action = context.getRequest().getParameter("action");
    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    //Save the current call
    Call previousParentCall = null;
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
      String headerId = context.getRequest().getParameter("headerId");
      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", oppHeader);

      if (thisCall.getId() > 0) {
        previousCall = new Call(db, thisCall.getId());
        if (!hasViewpointAuthority(
            db, context, "pipeline", previousCall.getEnteredBy(), userId)) {
          return "PermissionError";
        }
      }
      if (parentId != null && Integer.parseInt(parentId) > -1) {
        parentCall = new Call(db, Integer.parseInt(parentId));
      }
      //Store current status id to reset the object upon server error or warning
      tmpStatusId = thisCall.getStatusId();
      if ((tmpStatusId == Call.COMPLETE_FOLLOWUP_PENDING) && (!"pending".equals(
          context.getRequest().getParameter("view")))) {
        thisCall.setCheckAlertDate(false);
      }
      //update or insert the call
      if (thisCall.getId() > 0) {
        if (thisCall.getStatusId() == Call.COMPLETE) {
          if (previousCall.getAlertDate() == null && thisCall.getAlertDate() != null) {
            thisCall.setStatusId(Call.COMPLETE_FOLLOWUP_PENDING);
          }
        }
        isValid = this.validateObject(context, db, thisCall);
        if (isValid) {
          resultCount = thisCall.update(db, context);
        }
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
        if (parentCall != null && parentCall.getOppHeaderId() != -1) {
          thisCall.setOppHeaderId(parentCall.getOppHeaderId());
        }
        isValid = this.validateObject(context, db, thisCall);
        if (isValid) {
          recordInserted = thisCall.insert(db, context);
        }
      }
      if (!recordInserted && resultCount == -1) {
        thisCall.setStatusId(tmpStatusId);
        if (thisCall.getId() > 0) {
          Call tempCall = new Call(db, thisCall.getId());
          if (thisCall.getAlertText() != null && !"".equals(
              thisCall.getAlertText())) {
            thisCall.setHasFollowup(true);
          }
          thisCall.setCallType(tempCall.getCallType());
          addModifyFormElements(db, context, thisCall);
          if (resultCount == 0) {
            HashMap errors = new HashMap();
            SystemStatus systemStatus = this.getSystemStatus(context);
            errors.put(
                "actionError", systemStatus.getLabel(
                    "object.validation.recordUpdatedByAnotherUser"));
            processErrors(context, errors);
            context.getRequest().setAttribute("CallDetails", tempCall);
          }
        }
      } else {
        if (parentCall != null) {
          previousParentCall = new Call(db, parentCall.getId());
          parentCall.setStatusId(Call.COMPLETE);
          parentCall.update(db, context);
          this.processUpdateHook(context, previousParentCall, parentCall);
        }
        thisCall = new Call(db, thisCall.getId());
        if (recordInserted) {
          this.processInsertHook(context, thisCall);
        } else if (resultCount > 0) {
          this.processUpdateHook(context, previousCall, thisCall);
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
        return getReturn(context, "InsertCall");
      }
      return getReturn(context, "Insert");
    } else if (resultCount == 1) {
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return (executeCommandView(context));
      } else {
        return ("UpdateOK");
      }
    }
    if (parentId != null && Integer.parseInt(parentId) > -1) {
      if (action != null && "cancel".equals(action)) {
        return executeCommandCancel(context);
      } else {
        return executeCommandComplete(context);
      }
    }
    if (thisCall.getId() > 0) {
      return getReturn(context, "Modify");
    }
    return (executeCommandAdd(context));
  }


  /**
   * Show the details of the selected call record for an opportunity/lead
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-calls-view")) {
      return ("PermissionError");
    }

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    String callId = context.getRequest().getParameter("id");
    String headerId = context.getRequest().getParameter("headerId");
    Connection db = null;
    Call thisCall = null;
    try {
      db = this.getConnection(context);
      thisCall = new Call(db, callId);
      if (!hasViewpointAuthority(
          db, context, "pipeline", thisCall.getEnteredBy(), userId)) {
        return "PermissionError";
      }

      if (thisCall.getAlertDate() != null) {
        SystemStatus systemStatus = this.getSystemStatus(context);
        //Need the call types for display purposes
        LookupList reminderList = systemStatus.getLookupList(
            db, "lookup_call_reminder");
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
    return getReturn(context, "Details");
  }


  /**
   * Delete a call record from the database
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-calls-delete"))) {
      return ("PermissionError");
    }

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    SystemStatus systemStatus = this.getSystemStatus(context);
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String headerId = context.getRequest().getParameter("headerId");
    Call thisCall = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisCall = new Call(db, context.getRequest().getParameter("id"));
      if (!hasViewpointAuthority(
          db, context, "pipeline", thisCall.getEnteredBy(), userId)) {
        return "PermissionError";
      }
      recordDeleted = thisCall.delete(db);
      if (!recordDeleted) {
        thisCall.getErrors().put(
            "actionError", systemStatus.getLabel(
                "obejct.validation.actionError.callDeletion"));
        processErrors(context, thisCall.getErrors());
      }
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
   * Show the modify form from which a call can be updated
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */

  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-calls-edit")) {
      return ("PermissionError");
    }

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));
    String headerId = context.getRequest().getParameter("headerId");
    int callId = Integer.parseInt(context.getRequest().getParameter("id"));
    Connection db = null;
    Call thisCall = null;
    try {
      db = this.getConnection(context);
      thisCall = (Call) context.getFormBean();
      if (thisCall.getId() == -1) {
        thisCall = new Call(db, callId);
      }
      OpportunityHeader oppHeader = new OpportunityHeader(db, headerId);
      context.getRequest().setAttribute("opportunityHeader", oppHeader);

      if (!hasViewpointAuthority(
          db, context, "pipeline", thisCall.getEnteredBy(), userId)) {
        return "PermissionError";
      }

      if (oppHeader.getAccountLink() > -1) {
        Organization oppOrg = new Organization(db, oppHeader.getAccountLink());
        ContactList contactList = new ContactList();
        if (oppOrg.getOwner() != userId && userId != this.getUserId(context)) {
          contactList.setOwner(userId);
        } else if (oppOrg.getOwner() == this.getUserId(context)) {
          contactList.setOwner(this.getUserId(context));
        } else {
          return ("PermissionError");
        }
        contactList.setOwner(userId);
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(oppHeader.getAccountLink());
        contactList.setDefaultContactId(thisCall.getContactId());
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
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    if (context.getRequest().getParameter("popup") != null) {
      return ("ModifyPopupOK");
    }
    return ("ModifyOK");
  }


  /**
   * Forward an Opportunity Call
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandForwardCall(ActionContext context) {
    if (!(hasPermission(context, "pipeline-opportunities-calls-view"))) {
      return ("PermissionError");
    }

    String msgId = context.getRequest().getParameter("id");
    String headerId = context.getRequest().getParameter("headerId");
    CFSNote newNote = null;
    addModuleBean(context, "View Opportunities", "Opportunity Activities");
    context.getRequest().setAttribute(
        "forwardType", String.valueOf(Constants.TASKS));

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      Call thisCall = new Call(db, msgId);
      if (!hasViewpointAuthority(
          db, context, "pipeline", thisCall.getEnteredBy(), userId)) {
        return "PermissionError";
      }
      newNote = new CFSNote();
      String contactName = "Contact Name: ";
      String type = "Type: ";
      String length = "Length: ";
      String subject = "Subject: ";
      String notes = "Notes: ";
      String entered = "Entered: ";
      String modified = "Modified: ";
      contactName = systemStatus.getLabel("mail.label.contactName");
      type = systemStatus.getLabel("mail.label.type");
      length = systemStatus.getLabel("mail.label.length");
      subject = systemStatus.getLabel("mail.label.subject.colon");
      notes = systemStatus.getLabel("mail.label.notes");
      entered = systemStatus.getLabel("mail.label.entered");
      modified = systemStatus.getLabel("mail.label.modified");

      newNote.setBody(
          contactName + StringUtils.toString(thisCall.getContactName()) + "\n" +
          type + StringUtils.toString(thisCall.getCallType()) + "\n" +
          length + StringUtils.toString(thisCall.getLengthText()) + "\n" +
          subject + StringUtils.toString(thisCall.getSubject()) + "\n" +
          notes + StringUtils.toString(thisCall.getNotes()) + "\n" +
          entered + getUser(context, thisCall.getEnteredBy()).getContact().getNameFirstLast() + " - " + DateUtils.getServerToUserDateTimeString(
              this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, thisCall.getEntered()) + "\n" +
          modified + getUser(context, thisCall.getModifiedBy()).getContact().getNameFirstLast() + " - " + DateUtils.getServerToUserDateTimeString(
              this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, thisCall.getModified()));

      OpportunityHeader oppHeader = new OpportunityHeader(
          db, Integer.parseInt(headerId));
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
   * Send an Opportunity Call
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);

      Call thisCall = new Call(db, msgId);
      if (!hasViewpointAuthority(
          db, context, "pipeline", thisCall.getEnteredBy(), userId)) {
        return "PermissionError";
      }

      //add the call
      context.getRequest().setAttribute("CallDetails", thisCall);

      OpportunityHeader oppHeader = new OpportunityHeader(
          db, Integer.parseInt(headerId));
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandComplete(ActionContext context) {

    if (!hasPermission(context, "pipeline-opportunities-calls-edit")) {
      return ("PermissionError");
    }

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
    int userId = viewpointInfo.getVpUserId(this.getUserId(context));

    addModuleBean(context, "View Opportunities", "Complete Activity");
    //Process parameters
    String headerId = context.getRequest().getParameter("headerId");
    int callId = -1;
    if (context.getRequest().getParameter("parentId") != null && !"".equals(
        (String) context.getRequest().getParameter("parentId"))) {
      callId = Integer.parseInt(context.getRequest().getParameter("parentId"));
    } else {
      callId = Integer.parseInt(context.getRequest().getParameter("id"));
    }
    Connection db = null;
    Call thisCall = null;
    try {
      db = this.getConnection(context);
      //Load the previous Call to get details for completed activity
      thisCall = new Call(db, callId);
      context.getRequest().setAttribute("PreviousCallDetails", thisCall);

      OpportunityHeader oppHeader = new OpportunityHeader(
          db, Integer.parseInt(headerId));
      context.getRequest().setAttribute("opportunityHeader", oppHeader);

      if (oppHeader.getAccountLink() > -1) {
        ContactList contactList = new ContactList();
        contactList.setOwner(userId);
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(oppHeader.getAccountLink());
        contactList.setIncludeEnabled(Constants.UNDEFINED);
        contactList.buildList(db);
        context.getRequest().setAttribute("ContactList", contactList);
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      //Type Lookup
      LookupList callTypeList = systemStatus.getLookupList(
          db, "lookup_call_types");
      callTypeList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CallTypeList", callTypeList);

      //Result Lookup
      CallResultList resultList = new CallResultList();
      resultList.buildList(db);
      context.getRequest().setAttribute("callResultList", resultList);

      //Priority Lookup
      LookupList priorityList = systemStatus.getLookupList(
          db, "lookup_call_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);

      //Reminder Type Lookup
      LookupList reminderList = systemStatus.getLookupList(
          db, "lookup_call_reminder");
      context.getRequest().setAttribute("ReminderTypeList", reminderList);
      context.getRequest().setAttribute("systemStatus", systemStatus);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Add");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCancel(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-calls-delete")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Opportunities", "Opportunity Activities");
    //Process parameters
    String headerId = context.getRequest().getParameter("headerId");
    int callId = -1;
    if (context.getRequest().getParameter("parentId") != null && !"".equals(
        (String) context.getRequest().getParameter("parentId"))) {
      callId = Integer.parseInt(context.getRequest().getParameter("parentId"));
    } else {
      callId = Integer.parseInt(context.getRequest().getParameter("id"));
    }
    Connection db = null;
    Call thisCall = null;

    //Get Viewpoints if any
    ViewpointInfo viewpointInfo = this.getViewpointInfo(
        context, "PipelineViewpointInfo");
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
      OpportunityHeader oppHeader = new OpportunityHeader(
          db, Integer.parseInt(headerId));
      context.getRequest().setAttribute("opportunityHeader", oppHeader);

      SystemStatus systemStatus = this.getSystemStatus(context);
      //Type Lookup
      LookupList callTypeList = systemStatus.getLookupList(
          db, "lookup_call_types");
      callTypeList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CallTypeList", callTypeList);
      //Result Lookup
      CallResultList resultList = new CallResultList();
      resultList.buildList(db);
      context.getRequest().setAttribute("callResultList", resultList);
      //Priority Lookup
      LookupList priorityList = systemStatus.getLookupList(
          db, "lookup_call_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);

      //contact list
      if (oppHeader.getAccountLink() > -1) {
        ContactList contactList = new ContactList();
        contactList.setOwner(userId);
        contactList.setBuildDetails(false);
        contactList.setBuildTypes(false);
        contactList.setOrgId(oppHeader.getAccountLink());
        contactList.setIncludeEnabled(Constants.UNDEFINED);
        contactList.buildList(db);
        context.getRequest().setAttribute("ContactList", contactList);
        context.getRequest().setAttribute("systemStatus", systemStatus);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Add");
  }


  /**
   * Adds a feature to the ModifyFormElements attribute of the LeadsCalls
   * object
   *
   * @param db       The feature to be added to the ModifyFormElements
   *                 attribute
   * @param context  The feature to be added to the ModifyFormElements
   *                 attribute
   * @param thisCall The feature to be added to the ModifyFormElements
   *                 attribute
   * @throws SQLException Description of the Exception
   */
  private void addModifyFormElements(Connection db, ActionContext context, Call thisCall) throws SQLException {
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Type Lookup
    LookupList callTypeList = systemStatus.getLookupList(
        db, "lookup_call_types");
    callTypeList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("CallTypeList", callTypeList);
    
    //Reminder Type Lookup
    LookupList reminderList = systemStatus.getLookupList(
        db, "lookup_call_reminder");
    context.getRequest().setAttribute("ReminderTypeList", reminderList);

    //Priority Lookup
    LookupList priorityList = systemStatus.getLookupList(
        db, "lookup_call_priority");
    context.getRequest().setAttribute("PriorityList", priorityList);

    if ("pending".equals(context.getRequest().getParameter("view")) || (thisCall.getStatusId() == Call.COMPLETE && (thisCall.getAlertDate() == null || context.getRequest().getAttribute(
        "alertDateWarning") != null))) {
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

