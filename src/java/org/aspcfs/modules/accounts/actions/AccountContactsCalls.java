package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.ActionContext;
import java.sql.*;
import java.util.Vector;
import java.io.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.actions.CFSModule;

/**
 *  Description of the Class
 *
 * @author     akhi_m
 * @created    April 23, 2003
 */
public final class AccountContactsCalls extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String contactId = context.getRequest().getParameter("contactId");
    Connection db = null;
    CallList callList = new CallList();
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-contacts-edit"))) {
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
      return (executeCommandView(context));
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
  public String executeCommandAdd(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-contacts-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String contactId = context.getRequest().getParameter("contactId");
    Contact thisContact = null;

    Connection db = null;
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
}

