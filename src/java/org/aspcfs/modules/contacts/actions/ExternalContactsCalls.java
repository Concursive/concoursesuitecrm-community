package org.aspcfs.modules.contacts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.ActionContext;
import java.sql.*;
import java.text.DateFormat;
import java.util.Vector;
import java.io.*;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.mycfs.base.CFSNote;
import org.aspcfs.modules.actions.CFSModule;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    September 9, 2002
 *@version    $Id: ExternalContactsCalls.java,v 1.12 2002/12/23 18:27:00 chris
 *      Exp $
 */
public final class ExternalContactsCalls extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandView(ActionContext context) {
    int MINIMIZED_ITEMS_PER_PAGE = 5;
    if (!hasPermission(context, "contacts-external_contacts-calls-view")) {
      return ("PermissionError");
    }

    //Parameters
    String contactId = context.getRequest().getParameter("contactId");
    String source = context.getRequest().getParameter("source");

    //reset the paged lists
    if ("true".equals(context.getRequest().getParameter("resetList"))) {
      if ("calendar".equals(source)) {
        context.getSession().removeAttribute("CalCallsListInfo");
        context.getSession().removeAttribute("CalCompletedCallsListInfo");
      }else{
        context.getSession().removeAttribute("CallsListInfo");
        context.getSession().removeAttribute("CompletedCallsListInfo");
      }
    }

    //Determine the sections to view
    String sectionId = null;
    if (context.getRequest().getParameter("pagedListSectionId") != null) {
      sectionId = context.getRequest().getParameter("pagedListSectionId");
    }
    addModuleBean(context, "External Contacts", "View Activities");
    Connection db = null;
    //Pending Call List
    CallList callList = new CallList();

    //if request from calendar use different pagedlistinfo
    String pendingPagedListId = "CallsListInfo";
    if ("calendar".equals(source)) {
      pendingPagedListId = "Cal" + pendingPagedListId;
    }

    if (sectionId == null || pendingPagedListId.equals(sectionId)) {
      PagedListInfo callListInfo = this.getPagedListInfo(context, pendingPagedListId, "c.alertdate", null);
      callListInfo.setLink("ExternalContactsCalls.do?command=View&contactId=" + contactId +
          HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
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
    String completedPagedListId = "CompletedCallsListInfo";
    if ("calendar".equals(source)) {
      completedPagedListId = "Cal" + completedPagedListId;
    }

    if (sectionId == null || completedPagedListId.equals(sectionId)) {
      PagedListInfo completedCallListInfo = this.getPagedListInfo(context, completedPagedListId, "c.entered", "desc");
      if ("calendar".equals(source)) {
        completedCallListInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        completedCallListInfo.setLink("CalendarCalls.do?command=View&contactId=" + contactId +
            HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId|source"));
        completedCallListInfo.setExpandedSelection(true);
      } else {
      completedCallListInfo.setLink("ExternalContactsCalls.do?command=View&contactId=" + contactId +
          HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
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
      }

      completedCallList.setPagedListInfo(completedCallListInfo);
      completedCallList.setContactId(contactId);
    }
    try {
      db = this.getConnection(context);

      //load contact details
      Contact thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);

      //check role permissions
      if (!hasPermission(context, "contacts-external_contacts-calls-view") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-calls-view")))) {
        return ("PermissionError");
      }

      //check record level permissions
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }

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
  public String executeCommandSave(ActionContext context) {
    String permission = "contacts-external_contacts-calls-add";
    boolean recordInserted = false;
    int resultCount = -1;
    Contact thisContact = null;
    Call parentCall = null;
    Call previousCall = null;
    addModuleBean(context, "External Contacts", "Save an Activity");
    //Process the parameters

    String contactId = context.getRequest().getParameter("contactId");
    String parentId = context.getRequest().getParameter("parentId");
    String action = context.getRequest().getParameter("action");
    //Save the current call
    Call thisCall = (Call) context.getFormBean();
    thisCall.setModifiedBy(getUserId(context));

    if (thisCall.getId() > 0) {
      permission = "contacts-external_contacts-calls-edit";
    }
    if (!(hasPermission(context, permission))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (parentId != null && Integer.parseInt(parentId) > -1) {
        parentCall = new Call(db, Integer.parseInt(parentId));
      }
      //update or insert the call
      if (thisCall.getId() > 0) {
        if (thisCall.getStatusId() == Call.COMPLETE) {
          previousCall = new Call(db, thisCall.getId());
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

      thisContact = new Contact(db, contactId);
      if (!hasPermission(context, "contacts-external_contacts-calls-add") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-calls-add")))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

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

    boolean popup = "true".equals(context.getRequest().getParameter("popup"));
    boolean inlinePopup = "inline".equals(context.getRequest().getParameter("popupType"));
    if (recordInserted) {
      if (context.getRequest().getParameter("actionSource") != null) {
        return this.getReturn(context, "InsertCall");
      }
      return this.getReturn(context, "Insert");
    } else if (resultCount == 1) {
      if (popup && !inlinePopup) {
        return ("PopupCloseOK");
      }
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDetails(ActionContext context) {
    addModuleBean(context, "External Contacts", "Calls");

    String callId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    Call thisCall = null;
    Contact thisContact = null;

    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!hasPermission(context, "contacts-external_contacts-calls-view") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-calls-view")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      thisCall = new Call(db, callId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      
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
      
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CallDetails", thisCall);
    return this.getReturn(context, "Details");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    HtmlDialog htmlDialog = new HtmlDialog();
    Call thisCall = null;
    String id = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasPermission(context, "contacts-external_contacts-calls-delete") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-calls-delete")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      thisCall = new Call(db, Integer.parseInt(id));
      DependencyList dependencies = thisCall.processDependencies(db);
      htmlDialog.setTitle("CFS: Confirm Delete");
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='ExternalContactsCalls.do?command=Delete&contactId=" + contactId + "&id=" + id + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
      } else {
        htmlDialog.addMessage(dependencies.getHtmlString());
        htmlDialog.setHeader("This object has the following dependencies within Dark Horse CRM:");
        htmlDialog.addButton("Delete All", "javascript:window.location.href='ExternalContactsCalls.do?command=Delete&contactId=" + contactId + "&id=" + id + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
        htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDelete(ActionContext context) {
    boolean recordDeleted = false;
    String contactId = context.getRequest().getParameter("contactId");
    Call thisCall = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasPermission(context, "contacts-external_contacts-calls-delete") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-calls-delete")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      thisCall = new Call(db, context.getRequest().getParameter("id"));

      recordDeleted = thisCall.delete(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    boolean inLinePopup = "inline".equals(context.getRequest().getParameter("popupType"));
    if (recordDeleted) {
      context.getRequest().setAttribute("contactId", contactId);
      context.getRequest().setAttribute("refreshUrl", "ExternalContactsCalls.do?command=View&contactId=" + contactId + HTTPUtils.addLinkParams(context.getRequest(), "popupType|actionId" + (inLinePopup ? "|popup" : "")));
      return this.getReturn(context, "Delete");
    } else {
      processErrors(context, thisCall.getErrors());
      return (executeCommandView(context));
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-calls-add"))) {
      return ("PermissionError");
    }
    String contactId = context.getRequest().getParameter("contactId");
    addModuleBean(context, "External Contacts", "Add an Activity");
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasPermission(context, "contacts-external_contacts-calls-add") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-calls-add")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

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
  public String executeCommandModify(ActionContext context) {
    addModuleBean(context, "External Contacts", "Activities");

    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    int callId = Integer.parseInt(context.getRequest().getParameter("id"));

    Connection db = null;
    Call thisCall = null;

    try {
      db = this.getConnection(context);
      thisCall = new Call(db, callId);

      thisContact = new Contact(db, contactId);
      if (!hasPermission(context, "contacts-external_contacts-calls-edit") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-calls-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      addModifyFormElements(db, context, thisCall);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CallDetails", thisCall);
    return this.getReturn(context, "Modify");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandComplete(ActionContext context) {
    /*
     *  if (!hasPermission(context, "contacts-external_contacts-completed-calls-edit")) {
     *  return ("PermissionError");
     *  }
     */
    addModuleBean(context, "External Contacts", "Complete Activity");
    //Process parameters
    String contactId = context.getRequest().getParameter("contactId");
    int callId = Integer.parseInt(context.getRequest().getParameter("id"));
    Connection db = null;
    Call thisCall = null;
    try {
      db = this.getConnection(context);
      //Load the previous Call to get details for completed activity
      thisCall = new Call(db, callId);
      context.getRequest().setAttribute("PreviousCallDetails", thisCall);

      Contact thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);

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
   *  Forward a Call
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandForwardCall(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-calls-view"))) {
      return ("PermissionError");
    }

    String msgId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    CFSNote newNote = null;
    Contact thisContact = null;
    addModuleBean(context, "External Contacts", "Forward Message");

    Connection db = null;
    try {
      db = this.getConnection(context);

      thisContact = new Contact(db, contactId);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      newNote = new CFSNote();
      Call thisCall = new Call(db, msgId);
      newNote.setBody(
          "Contact Name: " + StringUtils.toString(thisCall.getContactName()) + "\n" +
          "Type: " + StringUtils.toString(thisCall.getCallType()) + "\n" +
          "Length: " + StringUtils.toString(thisCall.getLengthText()) + "\n" +
          "Subject: " + StringUtils.toString(thisCall.getSubject()) + "\n" +
          "Notes: " + StringUtils.toString(thisCall.getNotes()) + "\n" +
          "Entered: " + getUser(context, thisCall.getEnteredBy()).getContact().getNameFirstLast() + " - " + DateUtils.getServerToUserDateTimeString(this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, thisCall.getEntered()) + "\n" +
          "Modified: " + getUser(context, thisCall.getModifiedBy()).getContact().getNameFirstLast() + " - " + DateUtils.getServerToUserDateTimeString(this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, thisCall.getModified()));

      context.getRequest().setAttribute("ContactDetails", thisContact);
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCancel(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-calls-delete")) {
      return ("PermissionError");
    }
    addModuleBean(context, "External Contacts", "Cancel Activity");
    //Process parameters
    String contactId = context.getRequest().getParameter("contactId");
    int callId = Integer.parseInt(context.getRequest().getParameter("id"));
    Connection db = null;
    Call thisCall = null;
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
      Contact thisContact = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", thisContact);

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
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Add");
  }


  /**
   *  Send the Call(Uses the MyCFS SendMessage method to complete the Action)
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSendCall(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-calls-view"))) {
      return ("PermissionError");
    }

    String callId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;
    addModuleBean(context, "External Contacts", "Send Message");
    Connection db = null;
    try {
      db = this.getConnection(context);

      thisContact = new Contact(db, contactId);
      if (!hasPermission(context, "contacts-external_contacts-calls-edit") || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-calls-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }

      //load the call
      Call thisCall = new Call(db, callId);
      context.getRequest().setAttribute("CallDetails", thisCall);

      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "SendCall");
  }


  /**
   *  Suggest activity based on result of a previous activity
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
    return this.getReturn(context, "SuggestCall");
  }


  /**
   *  Adds a feature to the ModifyFormElements attribute of the ExternalContactsCalls object
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

