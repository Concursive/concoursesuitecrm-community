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
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.mycfs.base.CFSNote;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashMap;

/**
 * Represents Calls for an Account Contact
 *
 * @author Mathur
 * @version $Id$
 * @created April 23, 2003
 */
public final class AccountContactsCalls extends CFSModule {

  /**
   * View the Calls for an Account Contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    int MINIMIZED_ITEMS_PER_PAGE = 5;
    if (!hasPermission(context, "accounts-accounts-contacts-calls-view")) {
      return ("PermissionError");
    }

    //Parameters
    String contactId = context.getRequest().getParameter("contactId");
    String source = context.getRequest().getParameter("source");

    //reset the paged lists
    if ("true".equals(context.getRequest().getParameter("resetList"))) {
      if ("calendar".equals(source)) {
        context.getSession().removeAttribute("CalAccountContactCallsListInfo");
        context.getSession().removeAttribute(
            "CalAccountContactCompletedCallsListInfo");
      } else {
        context.getSession().removeAttribute("AccountContactCallsListInfo");
        context.getSession().removeAttribute(
            "AccountContactCompletedCallsListInfo");
      }
    }
    //Determine the sections to view
    String sectionId = null;
    if (context.getRequest().getParameter("pagedListSectionId") != null) {
      sectionId = context.getRequest().getParameter("pagedListSectionId");
    }
    addModuleBean(context, "View Accounts", "View Activities");
    Connection db = null;
    //Pending Call List
    CallList callList = new CallList();

    //if request from calendar use different pagedlistinfo
    String pendingPagedListId = "AccountContactCallsListInfo";
    if ("calendar".equals(source)) {
      pendingPagedListId = "Cal" + pendingPagedListId;
    }

    if (sectionId == null || pendingPagedListId.equals(sectionId)) {
      PagedListInfo callListInfo = this.getPagedListInfo(
          context, pendingPagedListId, "c.alertdate", null);
      callListInfo.setLink(
          "AccountContactsCalls.do?command=View&contactId=" + contactId +
              RequestUtils.addLinkParams(
                  context.getRequest(), "popup|popupType|actionId"));
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
      callList.setContactId(contactId);
    }

    //Completed Call List
    CallList completedCallList = new CallList();

    //if request from calendar use different pagedlistinfo
    String completedPagedListId = "AccountContactCompletedCallsListInfo";
    if ("calendar".equals(source)) {
      completedPagedListId = "Cal" + completedPagedListId;
    }

    if (sectionId == null || completedPagedListId.equals(sectionId)) {
      PagedListInfo completedCallListInfo = this.getPagedListInfo(
          context, completedPagedListId, "c.entered", "desc");
      if ("calendar".equals(source)) {
        completedCallListInfo.setItemsPerPage(
            PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        completedCallListInfo.setLink(
            "CalendarCalls.do?command=View&contactId=" + contactId +
                RequestUtils.addLinkParams(
                    context.getRequest(), "popup|popupType|actionId|source"));
        completedCallListInfo.setExpandedSelection(true);
      } else {
        completedCallListInfo.setLink(
            "AccountContactsCalls.do?command=View&contactId=" + contactId +
                RequestUtils.addLinkParams(
                    context.getRequest(), "popup|popupType|actionId"));
        if (sectionId == null) {
          if (!completedCallListInfo.getExpandedSelection()) {
            if (completedCallListInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE)
            {
              completedCallListInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
            }
          } else {
            if (completedCallListInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE)
            {
              completedCallListInfo.setItemsPerPage(
                  PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
            }
          }
        } else if (sectionId.equals(completedCallListInfo.getId())) {
          completedCallListInfo.setExpandedSelection(true);
        }
      }
      completedCallList.setPagedListInfo(completedCallListInfo);
      completedCallList.setContactId(contactId);
      completedCallList.setOnlyCompletedOrCanceled(true);
    }
    try {
      db = this.getConnection(context);
      if (contactId != null && !"-1".equals(contactId)) {
        Contact tmpContact = new Contact(db, contactId);
        if (tmpContact.isTrashed()) {
          callList.setIncludeOnlyTrashed(true);
          completedCallList.setIncludeOnlyTrashed(true);
        }
      }
      if (sectionId == null || pendingPagedListId.equals(sectionId)) {
        callList.buildList(db);
      }
      if (sectionId == null || completedPagedListId.equals(sectionId)) {
        completedCallList.buildList(db);
      }
      //add account and contact to the request
      addFormElements(context, db);

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
   * View Details of a Call
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-calls-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Activities");
    //Process parameters
    String callId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    //Prepare the view
    Connection db = null;
    Call thisCall = null;
    try {
      db = this.getConnection(context);
      //add account and contact to the request
      Contact thisContact = addFormElements(context, db);

      //build the call
      thisCall = new Call(db, callId);
      thisCall.buildParticipants(db);

      SystemStatus systemStatus = this.getSystemStatus(context);
      //Need the call types for display purposes
      LookupList reminderList = systemStatus.getLookupList(
          db, "lookup_call_reminder");
      context.getRequest().setAttribute("ReminderTypeList", reminderList);

      //Result Lookup
      //Need the result types for display purposes
      if(thisCall.getResultId()>-1){
      CallResult thisResult = new CallResult(db, thisCall.getResultId());
      context.getRequest().setAttribute("CallResult", thisResult);}
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    context.getRequest().setAttribute("CallDetails", thisCall);
    return getReturn(context, "Details");
  }


  /**
   * Add a Call for an Account Contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandLog(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-calls-add"))) {
      return ("PermissionError");
    }
    String contactId = context.getRequest().getParameter("contactId");
    addModuleBean(context, "View Accounts", "Log an Activity");
    Connection db = null;
    try {
      db = this.getConnection(context);
      //add account and contact to the request
      addFormElements(context, db);

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
      context.getRequest().setAttribute("Log", "Log");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //if a different module reuses this action then do a explicit return
    if (context.getRequest().getParameter("actionSource") != null) {
      return getReturn(context, "LogCall");
    }
    return getReturn(context, "Log");
  }

  /**
   * Add a Call for an Account Contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSchedule(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-calls-add"))) {
      return ("PermissionError");
    }
    String contactId = context.getRequest().getParameter("contactId");
    addModuleBean(context, "View Accounts", "Log an Activity");
    Connection db = null;
    try {
      db = this.getConnection(context);
      //add account and contact to the request
      addFormElements(context, db);

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
      context.getRequest().setAttribute("Log", "Log");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("action", "schedule");
    //if a different module reuses this action then do a explicit return
    if (context.getRequest().getParameter("actionSource") != null) {
      return getReturn(context, "LogCall");
    }
    return getReturn(context, "Log");
  }


  /**
   * Save an Activity
   * <p/>
   * TODO: Too many business rules here, need to move the parentCall into
   * the object.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    String permission = "accounts-accounts-contacts-calls-add";
    boolean recordInserted = false;
    boolean isValid = false;
    int resultCount = -1;
    Call parentCall = null;
    Call previousCall = null;
    int tmpStatusId = -1;
    addModuleBean(context, "View Accounts", "Save an Activity");
    //Process the parameters
    String contactId = context.getRequest().getParameter("contactId");
    String parentId = context.getRequest().getParameter("parentId");
    String action = context.getRequest().getParameter("action");
    String participantIds = context.getRequest().getParameter("participantIdValues");
    String followupParticipantIds = context.getRequest().getParameter("followupParticipantIdValues");    //Save the current call
    Call thisCall = (Call) context.getFormBean();
    Call previousParentCall = null;
    CallParticipantList participants = null;
    CallParticipantList followupParticipants = null;

    thisCall.setModifiedBy(getUserId(context));
    thisCall.setContactId(contactId);
    if (thisCall.getId() > 0) {
      permission = "accounts-accounts-contacts-calls-edit";
    }
    if (!(hasPermission(context, permission))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (participantIds!=null && !"".equals(participantIds)) {
      	participants = CallParticipantList.buildList(participantIds, getUserId(context), db, CallParticipant.LOGGED_CALL);
      }
      if (followupParticipantIds!=null && !"".equals(followupParticipantIds)) {
      	followupParticipants = CallParticipantList.buildList(followupParticipantIds, getUserId(context), db, CallParticipant.SCHEDULED_CALL);
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
        previousCall = new Call(db, thisCall.getId());
        previousCall.buildParticipants(db);
        if (thisCall.getStatusId() == Call.COMPLETE) {
          if (previousCall.getAlertDate() == null && thisCall.getAlertDate() != null)
          {
            thisCall.setStatusId(Call.COMPLETE_FOLLOWUP_PENDING);
          }
        }
        isValid = this.validateObject(context, db, thisCall);
        if (isValid) {
          resultCount = thisCall.update(db);
          if (resultCount > 0) {
          	thisCall.buildParticipants(db);
            if (participants != null) {
            	thisCall.getParticipants().delete(db);
            	thisCall.setParticipants(participants);
            	thisCall.getParticipants().save(db);
            }
            if (followupParticipants != null) {
            	thisCall.getFollowupParticipants().delete(db);
            	thisCall.setFollowupParticipants(followupParticipants);
            	thisCall.getFollowupParticipants().save(db);
            }
          }
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
        thisCall.setAction(context.getRequest().getParameter("action"));
        isValid = this.validateObject(context, db, thisCall);
        if (isValid) {
          recordInserted = thisCall.insert(db, context);
          if (recordInserted) {
            thisCall.buildParticipants(db);
            if (participants != null) {
              thisCall.getParticipants().delete(db);
              thisCall.setParticipants(participants);
              thisCall.getParticipants().save(db);
            }
            if (followupParticipants != null) {
              thisCall.getFollowupParticipants().delete(db);
              thisCall.setFollowupParticipants(followupParticipants);
              thisCall.getFollowupParticipants().save(db);
            }
          }
        }
      }
      if ("schedule".equals(context.getRequest().getParameter("action"))) {
        context.getRequest().setAttribute("actionSource", "accountItem");
        context.getRequest().setAttribute("action", "schedule");
      }
      //add account and contact to the request
      addFormElements(context, db);

      ContactList cl = new ContactList();
      cl.setOrgId(thisCall.getOrgId());
      cl.setBuildDetails(false);
      cl.setBuildTypes(false);
      cl.buildList(db);
      context.getRequest().setAttribute("contactList",cl);

      if (!recordInserted && resultCount == -1) {
        thisCall.setStatusId(tmpStatusId);
        if (thisCall.getAlertText() != null && !"".equals(
            thisCall.getAlertText())) {
          thisCall.setHasFollowup(true);
        }
        if (thisCall.getId() > 0) {
          Call tempCall = new Call(db, thisCall.getId());
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
        // The new call has been recorded, if there is a parent, then mark it
        // complete.
        if (parentCall != null) {
          previousParentCall = new Call(db, parentCall.getId());
          parentCall.setStatusId(Call.COMPLETE);
          parentCall.update(db);
          this.processUpdateHook(context, previousParentCall, parentCall);
        }
        thisCall = new Call(db, thisCall.getId());
        if (recordInserted) {
          this.processInsertHook(context, thisCall);
        } else if (resultCount == 1) {
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
        return "UpdateListOK";
      }
      return getReturn(context, "Update");
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
    return (executeCommandLog(context));
  }


  /**
   * Modify a Call
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-calls-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Modify an Activity");

    String contactId = context.getRequest().getParameter("contactId");

    int callId = Integer.parseInt(context.getRequest().getParameter("id"));

    Connection db = null;
    Call thisCall = null;

    try {
      db = this.getConnection(context);
      thisCall = new Call(db, callId);
      if (thisCall.getOwner() != this.getUserId(context)) {
      	return ("PermissionError");
      }

      thisCall.buildParticipants(db);

      //add account and contact to the request
      Contact thisContact = addFormElements(context, db);
      addModifyFormElements(db, context, thisCall);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CallDetails", thisCall);
    context.getRequest().setAttribute("action", context.getRequest().getParameter("action"));
    return getReturn(context, "Modify");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandComplete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-calls-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Complete Activity");
    //Process parameters
    String contactId = context.getRequest().getParameter("contactId");
    int callId = -1;
    if (context.getRequest().getParameter("parentId") != null && !"".equals(
        (String) context.getRequest().getParameter("parentId"))) {
      callId = Integer.parseInt(context.getRequest().getParameter("parentId"));
    } else {
      callId = Integer.parseInt(context.getRequest().getParameter("id"));
    }
    Connection db = null;
    Call thisCall = null;
    Call completedCalls = (Call) context.getRequest().getAttribute("CallDetails");
    try {
      db = this.getConnection(context);
      //Load the previous Call to get details for completed activity
      thisCall = new Call(db, callId);
      thisCall.buildParticipants(db);
      context.getRequest().setAttribute("PreviousCallDetails", thisCall);

      if ((thisCall.getSubject()!=null) && (!"".equals(thisCall.getSubject()))) {
      	completedCalls.setCallTypeId(thisCall.getCallTypeId());
	      completedCalls.setCallLengthDuration(thisCall.getCallLengthDuration());
 		    completedCalls.setCallStartDate(thisCall.getCallStartDate());
 	      completedCalls.setCallEndDate(thisCall.getCallEndDate());
 	      completedCalls.setContact(thisCall.getContact());
 	      completedCalls.setCallLocation(thisCall.getCallLocation());
      	completedCalls.setSubject(thisCall.getSubject());
      	completedCalls.setNotes(thisCall.getNotes());
      	completedCalls.setParticipants(thisCall.getParticipants());
      	context.getRequest().setAttribute("CallDetails", completedCalls);
      }else {
      	completedCalls.setCallTypeId(thisCall.getAlertCallTypeId());
	      completedCalls.setCallLengthDuration(thisCall.getFollowupLength());
 		    completedCalls.setCallStartDate(thisCall.getAlertDate());
 	      completedCalls.setCallEndDate(thisCall.getFollowupEndDate());
 	      completedCalls.setContact(thisCall.getFollowupContact());
 	      completedCalls.setCallLocation(thisCall.getFollowupLocation());
 	      completedCalls.setSubject(thisCall.getAlertText());
      	completedCalls.setNotes(thisCall.getFollowupNotes());
      	completedCalls.setParticipants(thisCall.getFollowupParticipants());
      	context.getRequest().setAttribute("CallDetails", completedCalls);
      	context.getRequest().setAttribute("Completed", "completed");
      }
      //add account and contact to the request
      Contact thisContact = addFormElements(context, db);

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
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("action", context.getRequest().getParameter("action"));
    return getReturn(context, "Log");
  }


  /**
   * Modify a Call
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCancel(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-calls-delete")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Cancel Activity");
    //Process parameters
    String contactId = context.getRequest().getParameter("contactId");
    int callId = -1;
    if (context.getRequest().getParameter("parentId") != null && !"".equals(
        (String) context.getRequest().getParameter("parentId"))) {
      callId = Integer.parseInt(context.getRequest().getParameter("parentId"));
    } else {
      callId = Integer.parseInt(context.getRequest().getParameter("id"));
    }
    Connection db = null;
    Call thisCall = null;
    Call cancelCalls = (Call) context.getRequest().getAttribute("CallDetails");
    try {
      db = this.getConnection(context);
      //Load the previous Call to get details for completed activity
      thisCall = new Call(db, callId);
      thisCall.buildParticipants(db);
      context.getRequest().setAttribute("PreviousCallDetails", thisCall);
      //Create an empty next call based on the previous call
      if ((thisCall.getSubject()!=null) && (!"".equals(thisCall.getSubject()))) {
      	cancelCalls.setCallTypeId(thisCall.getCallTypeId());
	      cancelCalls.setCallLengthDuration(thisCall.getCallLengthDuration());
 		    cancelCalls.setCallStartDate(thisCall.getCallStartDate());
 	      cancelCalls.setCallEndDate(thisCall.getCallEndDate());
 	      cancelCalls.setContact(thisCall.getContact());
 	      cancelCalls.setCallLocation(thisCall.getCallLocation());
      	cancelCalls.setSubject(thisCall.getSubject());
      	cancelCalls.setNotes(thisCall.getNotes());
      	cancelCalls.setParticipants(thisCall.getParticipants());
      	context.getRequest().setAttribute("CallDetails", cancelCalls);
      }else {
      	cancelCalls.setCallTypeId(thisCall.getAlertCallTypeId());
	      cancelCalls.setCallLengthDuration(thisCall.getFollowupLength());
 		    cancelCalls.setCallStartDate(thisCall.getAlertDate());
 	      cancelCalls.setCallEndDate(thisCall.getFollowupEndDate());
 	      cancelCalls.setContact(thisCall.getFollowupContact());
 	      cancelCalls.setCallLocation(thisCall.getFollowupLocation());
 	      cancelCalls.setSubject(thisCall.getAlertText());
      	cancelCalls.setNotes(thisCall.getFollowupNotes());
      	cancelCalls.setParticipants(thisCall.getFollowupParticipants());
      	context.getRequest().setAttribute("CallDetails", cancelCalls);
      }
      context.getRequest().setAttribute("Completed", "cancel");
      //add account and contact to the request
      Contact thisContact = addFormElements(context, db);
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
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("action", context.getRequest().getParameter("action"));
    return getReturn(context, "Log");
  }


  /**
   * Confirm Deletion of the Call
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-calls-delete"))) {
      return ("PermissionError");
    }
    HtmlDialog htmlDialog = new HtmlDialog();
    Call thisCall = null;
    String id = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      Contact thisContact = new Contact(db, contactId);
      thisCall = new Call(db, Integer.parseInt(id));
      DependencyList dependencies = thisCall.processDependencies(db);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl(
            "javascript:window.location.href='AccountContactsCalls.do?command=Delete&contactId=" + contactId + "&id=" + id + RequestUtils.addLinkParams(
                context.getRequest(), "popup|popupType|actionId") + "'");
      } else {
        dependencies.setSystemStatus(systemStatus);
        htmlDialog.addMessage(
            systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='AccountContactsCalls.do?command=Delete&contactId=" + contactId + "&id=" + id + RequestUtils.addLinkParams(
            context.getRequest(), "popup|popupType|actionId") + "'");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("action", context.getRequest().getParameter("action"));
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   * Delete a Call
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-contacts-calls-delete"))) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    String contactId = context.getRequest().getParameter("contactId");
    Call thisCall = null;
    SystemStatus systemStatus = this.getSystemStatus(context);

    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      thisCall = new Call(db, context.getRequest().getParameter("id"));

      recordDeleted = thisCall.delete(db);
      if (!recordDeleted) {
        thisCall.getErrors().put(
            "actionError", systemStatus.getLabel(
            "obejct.validation.actionError.callDeletion"));
        processErrors(context, thisCall.getErrors());
      }

      //add account and contact to the request
      addFormElements(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    boolean inLinePopup = "inline".equals(
        context.getRequest().getParameter("popupType"));
    if (recordDeleted) {
      context.getRequest().setAttribute("contactId", contactId);
      context.getRequest().setAttribute(
          "refreshUrl", "AccountContactsCalls.do?command=View&contactId=" + contactId + RequestUtils.addLinkParams(
          context.getRequest(), "popupType|actionId" + (inLinePopup ? "|popup" : "")));
      return getReturn(context, "Delete");
    } else {
      processErrors(context, thisCall.getErrors());
      return (executeCommandView(context));
    }
  }


  /**
   * Forward a Call
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandForwardCall(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-calls-view"))) {
      return ("PermissionError");
    }
    int noteType = -1;
    String msgId = (String) context.getRequest().getParameter("id");
    CFSNote newNote = null;
    addModuleBean(context, "View Accounts", "Forward Activity");
    Connection db = null;
    Call thisCall = null;
    try {
      noteType = Integer.parseInt(
          context.getRequest().getParameter("forwardType"));
      context.getRequest().setAttribute("forwardType", "" + Constants.TASKS);
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      newNote = new CFSNote();
      thisCall = new Call(db, msgId);
      String contactName = systemStatus.getLabel("mail.label.contactName");
      String type = systemStatus.getLabel("mail.label.type");
      String length = systemStatus.getLabel("mail.label.length");
      String subject = systemStatus.getLabel("mail.label.subject.colon");
      String notes = systemStatus.getLabel("mail.label.notes");
      String entered = systemStatus.getLabel("mail.label.entered");
      String modified = systemStatus.getLabel("mail.label.modified");
      newNote.setBody(
          contactName + StringUtils.toString(thisCall.getContactName()) + "\n" +
              type + StringUtils.toString(thisCall.getCallType()) + "\n" +
              length + StringUtils.toString(thisCall.getLengthText()) + "\n" +
              subject + StringUtils.toString(thisCall.getSubject()) +
              (((!StringUtils.toString(thisCall.getSubject()).equals(StringUtils.toString(thisCall.getAlertText()))) && (!"".equals(StringUtils.toString(thisCall.getAlertText())))) ? "\\" + StringUtils.toString(thisCall.getAlertText()) : "") + "\n" +
              notes + StringUtils.toString(thisCall.getNotes()) + "\n" +
              entered + StringUtils.toString(thisCall.getEnteredName()) + " - " + DateUtils.getServerToUserDateTimeString(
              this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, thisCall.getEntered()) + "\n" +
              modified + StringUtils.toString(thisCall.getModifiedName()) + " - " + DateUtils.getServerToUserDateTimeString(
              this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, thisCall.getModified()));
      //load contact and account
      addFormElements(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("action", context.getRequest().getParameter("action"));
    context.getRequest().setAttribute("Note", newNote);
    return getReturn(context, "ForwardMessage");
  }


  /**
   * Send the Call(Uses the MyCFS SendMessage method to complete the Action)
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSendCall(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-calls-view"))) {
      return ("PermissionError");
    }
    String callId = context.getRequest().getParameter("id");
    addModuleBean(context, "View Accounts", "Send Activity");
    Connection db = null;
    try {
      db = this.getConnection(context);
      //load the call
      Call thisCall = new Call(db, callId);
      context.getRequest().setAttribute("CallDetails", thisCall);
      //load contact and account
      addFormElements(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("action", context.getRequest().getParameter("action"));
    return getReturn(context, "SendCall");
  }


  /**
   * Adds the Account to the request
   *
   * @param context The feature to be added to the Organization
   *                attribute
   * @param db      The feature to be added to the Organization
   *                attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public Contact addFormElements(ActionContext context, Connection db) throws SQLException {
    String contactId = context.getRequest().getParameter("contactId");
    String orgId = context.getRequest().getParameter("orgId");
    Contact thisContact = (Contact) context.getRequest().getAttribute("ContactDetails");
    Organization thisOrganization = null;
    if (thisContact == null && contactId!=null && !"-1".equals(contactId) && !"0".equals(contactId)) {
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);

      if (thisContact.getOrgId() > -1) {
        thisOrganization = new Organization(db, thisContact.getOrgId());
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
      }
    } else if (orgId != null && !"-1".equals(orgId)) {
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    }
    return thisContact;
  }


  /**
   * Adds a feature to the ModifyFormElements attribute of the
   * AccountContactsCalls object
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
      if (thisCall.getResultId() != -1) {
        CallResult thisResult = new CallResult(db, thisCall.getResultId());
        context.getRequest().setAttribute("CallResult", thisResult);
      }
      //include the callResultList if it is a completed activity with no followup
      if (!"pending".equals(context.getRequest().getParameter("view"))) {
        CallResultList resultList = new CallResultList();
        resultList.buildList(db);
        context.getRequest().setAttribute("callResultList", resultList);
      }
      if (thisCall.getFollowupContactId() != -1) {
      	Contact followupContact = new Contact(db, thisCall.getFollowupContactId());
      	context.getRequest().setAttribute("followupContactDetails", followupContact);
      }
    } else {
      //Result Lookup
      CallResultList resultList = new CallResultList();
      resultList.buildList(db);
      context.getRequest().setAttribute("callResultList", resultList);
    }
  }


  /**
   * Suggest activity based on result of a previous activity
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSuggestCall(ActionContext context) {
    String resultId = context.getRequest().getParameter("resultId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      CallResult thisResult = new CallResult(db, Integer.parseInt(resultId));
      context.getRequest().setAttribute("CallResult", thisResult);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("action", context.getRequest().getParameter("action"));
    return getReturn(context, "SuggestCall");
  }
}

