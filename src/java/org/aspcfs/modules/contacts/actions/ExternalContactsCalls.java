package org.aspcfs.modules.contacts.actions;

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
import org.aspcfs.modules.actions.CFSModule;

/**
 *  Description of the Class
 *
 * @author     akhi_m
 * @created    September 9, 2002
 * @version    $Id: ExternalContactsCalls.java,v 1.12 2002/12/23 18:27:00 chris
 *      Exp $
 */
public final class ExternalContactsCalls extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-calls-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String contactId = context.getRequest().getParameter("contactId");
    addModuleBean(context, "External Contacts", "Calls");
    PagedListInfo callListInfo = this.getPagedListInfo(context, "CallListInfo");
    callListInfo.setLink("ExternalContactsCalls.do?command=View&contactId=" + contactId + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
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
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (!hasAuthority(context, thisContact.getOwner())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("CallList", callList);
      return this.getReturn(context, "View");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public String executeCommandInsert(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-calls-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;

    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;
    Call thisCall = (Call) context.getFormBean();
    thisCall.setEnteredBy(getUserId(context));
    thisCall.setModifiedBy(getUserId(context));
    
    Connection db = null;
    try {
      db = this.getConnection(context);
      recordInserted = thisCall.insert(db, context);
      if (!recordInserted) {
        processErrors(context, thisCall.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (context.getRequest().getParameter("actionSource") != null) {
        return this.getReturn(context, "InsertCall");
      }
      return this.getReturn(context, "Insert");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public String executeCommandDetails(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-calls-view"))) {
      return ("PermissionError");
    }

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
      if (!hasAuthority(context, thisContact.getOwner())) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("CallDetails", thisCall);
      return this.getReturn(context, "Details");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-calls-delete"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    Call thisCall = null;
    String id = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasAuthority(context, thisContact.getOwner())) {
        return "PermissionError";
      }
      thisCall = new Call(db, Integer.parseInt(id));
      DependencyList dependencies = thisCall.processDependencies(db);
      htmlDialog.setTitle("CFS: Confirm Delete");
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='ExternalContactsCalls.do?command=Delete&contactId=" + contactId + "&id=" + id + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
      }else{
        htmlDialog.addMessage(dependencies.getHtmlString());
        htmlDialog.setHeader("This object has the following dependencies within CFS:");
        htmlDialog.addButton("Delete All", "javascript:window.location.href='ExternalContactsCalls.do?command=Delete&contactId=" + contactId + "&id=" + id + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
        htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
      }
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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public String executeCommandDelete(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-calls-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;

    String contactId = context.getRequest().getParameter("contactId");
    Call thisCall = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      if (!hasAuthority(context, thisContact.getOwner())) {
        return ("PermissionError");
      }
      thisCall = new Call(db, context.getRequest().getParameter("id"));

      recordDeleted = thisCall.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("contactId", contactId);
        context.getRequest().setAttribute("refreshUrl", "ExternalContactsCalls.do?command=View&contactId=" + contactId + HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
        return "DeleteOK";
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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public String executeCommandAdd(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-calls-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String contactId = context.getRequest().getParameter("contactId");
    PagedListInfo callListInfo = this.getPagedListInfo(context, "CallListInfo");
    callListInfo.setLink("ExternalContactCalls.do?command=View&contactId=" + contactId);

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
      if (!hasAuthority(context, thisContact.getOwner())) {
        return ("PermissionError");
      }
      addModuleBean(context, "External Contacts", "Calls");
      //if a different module reuses this action then do a explicit return
      if (context.getRequest().getParameter("actionSource") != null) {
        return this.getReturn(context, "AddCall");
      }
      return this.getReturn(context, "Add");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }



  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-calls-edit")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "External Contacts", "Calls");

    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    int callId = Integer.parseInt(context.getRequest().getParameter("id"));

    Connection db = null;
    Call thisCall = null;

    try {
      db = this.getConnection(context);
      thisCall = new Call(db, callId);

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
      if (!hasAuthority(context, thisContact.getOwner())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("CallDetails", thisCall);
      return this.getReturn(context, "Modify");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public String executeCommandUpdate(ActionContext context) {

    if (!(hasPermission(context, "contacts-external_contacts-calls-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    Call thisCall = (Call) context.getFormBean();

    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      thisCall.setModifiedBy(getUserId(context));
      if (!hasAuthority(context, thisContact.getOwner())) {
        return ("PermissionError");
      }
      resultCount = thisCall.update(db, context);
      if (resultCount == -1) {
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
      boolean popup = "true".equals(context.getRequest().getParameter("popup"));
      boolean inlinePopup = "inline".equals(context.getRequest().getParameter("popupType"));
      if (popup && !inlinePopup) {
        return ("PopupCloseOK");
      } else if (resultCount == -1) {
        processErrors(context, thisCall.getErrors());
        context.getRequest().setAttribute("CallDetails", thisCall);
        return ("ModifyOK");
      } else if (resultCount == 1) {
        context.getRequest().removeAttribute("CallDetails");
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

