package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.ActionContext;
import java.sql.*;
import java.util.Vector;
import java.io.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.mycfs.base.CFSNote;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;

/**
 *  Represents Calls for an Account Contact
 *
 *@author     Mathur
 *@created    April 23, 2003
 *@version    $id:exp$
 */
public final class AccountContactsCalls extends CFSModule {

  /**
   *  View the Calls for an Account Contact
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-calls-view")) {
      return ("PermissionError");
    }

    String contactId = context.getRequest().getParameter("contactId");
    PagedListInfo callListInfo = this.getPagedListInfo(context, "AccountContactCallsListInfo");
    callListInfo.setLink("AccountContactsCalls.do?command=View&contactId=" + contactId + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
    addModuleBean(context, "View Accounts", "View Calls");

    Connection db = null;
    CallList callList = new CallList();
    try {
      db = this.getConnection(context);
      callList.setContactId(contactId);
      callList.setPagedListInfo(callListInfo);
      callList.buildList(db);

      //add account and contact to the request
      addFormElements(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CallList", callList);
    return this.getReturn(context, "View");
  }


  /**
   *  View Details of a Call
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-contacts-calls-view"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "Calls");

    String callId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");

    Connection db = null;
    Call thisCall = null;

    try {
      db = this.getConnection(context);

      //add account and contact to the request
      Contact thisContact = addFormElements(context, db);

      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }

      //build the call
      thisCall = new Call(db, callId);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    context.getRequest().setAttribute("CallDetails", thisCall);
    return this.getReturn(context, "Details");
  }


  /**
   *  Add a Call for an Account Contact
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-contacts-calls-add"))) {
      return ("PermissionError");
    }

    String contactId = context.getRequest().getParameter("contactId");
    addModuleBean(context, "View Accounts", "Add a Call");

    Connection db = null;
    try {
      db = this.getConnection(context);

      //add account and contact to the request
      addFormElements(context, db);

      LookupList callTypeList = new LookupList(db, "lookup_call_types");
      callTypeList.addItem(0, "--None--");
      context.getRequest().setAttribute("CallTypeList", callTypeList);
    } catch (SQLException e) {
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
   *  Save a Call
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {

    String permission = "contacts-external_contacts-add";

    boolean recordInserted = false;
    int resultCount = 0;
    String contactId = context.getRequest().getParameter("contactId");
    Call thisCall = (Call) context.getFormBean();
    thisCall.setModifiedBy(getUserId(context));
    addModuleBean(context, "View Accounts", "Save a Call");

    if (thisCall.getId() > 0) {
      permission = "accounts-accounts-contacts-calls-view";
    }

    if (!(hasPermission(context, permission))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);

      //update or insert the call
      if (thisCall.getId() > 0) {
        resultCount = thisCall.update(db, context);
      } else {
        thisCall.setEnteredBy(getUserId(context));
        recordInserted = thisCall.insert(db, context);
      }

      //add account and contact to the request
      addFormElements(context, db);

      if (!recordInserted && resultCount == 0) {
        processErrors(context, thisCall.getErrors());
        LookupList callTypeList = new LookupList(db, "lookup_call_types");
        callTypeList.addItem(0, "--None--");
        context.getRequest().setAttribute("CallTypeList", callTypeList);
      }
    } catch (SQLException e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if (recordInserted) {
      return (executeCommandView(context));
    } else if (resultCount == 1) {
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return (executeCommandView(context));
      }
      return (executeCommandDetails(context));
    }
    if (thisCall.getId() > 0) {
      return (executeCommandModify(context));
    }
    return (executeCommandAdd(context));
  }


  /**
   *  Modify a Call
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-calls-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Modify a Call");

    String contactId = context.getRequest().getParameter("contactId");

    int callId = Integer.parseInt(context.getRequest().getParameter("id"));

    Connection db = null;
    Call thisCall = null;

    try {
      db = this.getConnection(context);
      thisCall = new Call(db, callId);

      //add account and contact to the request
      Contact thisContact = addFormElements(context, db);

      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }

      LookupList callTypeList = new LookupList(db, "lookup_call_types");
      callTypeList.addItem(0, "--None--");
      context.getRequest().setAttribute("CallTypeList", callTypeList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CallDetails", thisCall);
    return this.getReturn(context, "Modify");
  }


  /**
   *  Confirm Deletion of the Call
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      Contact thisContact = new Contact(db, contactId);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      thisCall = new Call(db, Integer.parseInt(id));
      DependencyList dependencies = thisCall.processDependencies(db);
      htmlDialog.setTitle("CFS: Confirm Delete");
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='AccountContactsCalls.do?command=Delete&contactId=" + contactId + "&id=" + id + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
      } else {
        htmlDialog.addMessage(dependencies.getHtmlString());
        htmlDialog.setHeader("This object has the following dependencies within CFS:");
        htmlDialog.addButton("Delete All", "javascript:window.location.href='AccountContactsCalls.do?command=Delete&contactId=" + contactId + "&id=" + id + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
        htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Delete a Call
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDelete(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-contacts-calls-delete"))) {
      return ("PermissionError");
    }

    boolean recordDeleted = false;
    String contactId = context.getRequest().getParameter("contactId");
    Call thisCall = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      thisCall = new Call(db, context.getRequest().getParameter("id"));

      recordDeleted = thisCall.delete(db);

      //add account and contact to the request
      addFormElements(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    boolean inLinePopup = "inline".equals(context.getRequest().getParameter("popupType"));
    if (recordDeleted) {
      context.getRequest().setAttribute("contactId", contactId);
      context.getRequest().setAttribute("refreshUrl", "AccountContactsCalls.do?command=View&contactId=" + contactId + HTTPUtils.addLinkParams(context.getRequest(), "popupType|actionId" + (inLinePopup ? "|popup" : "")));
      return this.getReturn(context, "Delete");
    } else {
      processErrors(context, thisCall.getErrors());
      return (executeCommandView(context));
    }
  }


  /**
   *  Forward a Call
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandForwardCall(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-calls-view"))) {
      return ("PermissionError");
    }

    String msgId = context.getRequest().getParameter("id");
    CFSNote newNote = null;
    addModuleBean(context, "View Accounts", "Forward Message");

    Connection db = null;
    try {
      db = this.getConnection(context);

      newNote = new CFSNote();
      Call thisCall = new Call(db, msgId);
      newNote.setBody(
          "Contact Name: " + StringUtils.toString(thisCall.getContactName()) + "\n" +
          "Type: " + StringUtils.toString(thisCall.getCallType()) + "\n" +
          "Length: " + StringUtils.toString(thisCall.getLengthText()) + "\n" +
          "Subject: " + StringUtils.toString(thisCall.getSubject()) + "\n" +
          "Notes: " + StringUtils.toString(thisCall.getNotes()) + "\n" +
          "Entered: " + StringUtils.toString(thisCall.getEnteredName()) + " - " + thisCall.getEnteredString() + "\n" +
          "Modified: " + StringUtils.toString(thisCall.getModifiedName()) + " - " + thisCall.getModifiedString());

      //load contact and account
      addFormElements(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("Note", newNote);
    return ("ForwardMessageOK");
  }


  /**
   *  Send the Call(Uses the MyCFS SendMessage method to complete the Action)
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSendCall(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-calls-view"))) {
      return ("PermissionError");
    }

    String callId = context.getRequest().getParameter("id");
    addModuleBean(context, "View Accounts", "Send Message");
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

    return this.getReturn(context, "SendCall");
  }


  /**
   *  Adds the Account to the request
   *
   *@param  context           The feature to be added to the Organization attribute
   *@param  db                The feature to be added to the Organization attribute
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public Contact addFormElements(ActionContext context, Connection db) throws SQLException {
    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = (Contact) context.getRequest().getAttribute("ContactDetails");
    Organization thisOrganization = null;

    if (thisContact == null) {
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    }
    if (thisContact.getOrgId() > -1) {
      thisOrganization = new Organization(db, thisContact.getOrgId());
    }
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return thisContact;
  }
}

