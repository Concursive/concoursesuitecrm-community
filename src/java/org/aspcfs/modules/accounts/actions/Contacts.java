package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Vector;
import java.sql.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.utils.web.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    August 29, 2001
 *@version    $Id$
 */
public final class Contacts extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-add"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "Add Contact to Account");
    Exception errorMessage = null;
    Connection db = null;

    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);
      buildFormElements(context, db);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (SQLException e) {
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandClone(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-add"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "Clone Account Contact");
    Exception errorMessage = null;
    Connection db = null;

    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrganization = null;

    String contactId = context.getRequest().getParameter("id");
    Contact cloneContact = null;

    try {
      db = this.getConnection(context);
      buildFormElements(context, db);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      cloneContact = new Contact(db, contactId);
      cloneContact.resetBaseInfo();
      context.getRequest().setAttribute("ContactDetails", cloneContact);
    } catch (SQLException e) {
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsert(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-add"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "Add Contact to Account");
    Exception errorMessage = null;
    boolean recordInserted = false;

    Contact newContact = (Contact) context.getRequest().getAttribute("ContactDetails");
    newContact.setRequestItems(context.getRequest());
    newContact.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newContact.setEnteredBy(getUserId(context));
    newContact.setModifiedBy(getUserId(context));
    newContact.setOwner(getUserId(context));

    Organization thisOrganization = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      recordInserted = newContact.insert(db);
      if (recordInserted) {
        newContact = new Contact(db, "" + newContact.getId());
        context.getRequest().setAttribute("ContactDetails", newContact);
        thisOrganization = new Organization(db, newContact.getOrgId());
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
      } else {
        processErrors(context, newContact.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("orgId", "" + newContact.getOrgId());
      if (recordInserted) {
        if (context.getRequest().getParameter("saveAndClone") != null) {
          if (context.getRequest().getParameter("saveAndClone").equals("true")) {
            newContact.resetBaseInfo();
            context.getRequest().setAttribute("ContactDetails", newContact);
            return (executeCommandAdd(context));
          }
        }
        return ("DetailsOK");
      } else {
        return (executeCommandAdd(context));
      }
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
  public String executeCommandConfirmDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Contact thisContact = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;
    String orgId = null;

    if (!(hasPermission(context, "accounts-accounts-contacts-delete"))) {
      return ("PermissionError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = context.getRequest().getParameter("id");
    }

    if (context.getRequest().getParameter("orgId") != null) {
      orgId = context.getRequest().getParameter("orgId");
    }

    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, id);
      thisContact.checkUserAccount(db);
      DependencyList dependencies = thisContact.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());

      if (!thisContact.hasAccount()) {

        if (thisContact.getPrimaryContact()) {
          htmlDialog.setHeader("This contact cannot be deleted because it is also an individual account.");
          htmlDialog.addButton("OK", "javascript:parent.window.close()");
        } else {
          htmlDialog.setTitle("CFS: Confirm Delete");
          htmlDialog.setHeader("The contact you are requesting to delete has the following dependencies within CFS:");
          htmlDialog.addButton("Delete All", "javascript:window.location.href='Contacts.do?command=Delete&orgId=" + orgId + "&id=" + id + "'");
          htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
        }
      } else {
        htmlDialog.setHeader("This contact cannot be deleted because it is associated with a User account.");
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-view"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "View Contact Details");
    Exception errorMessage = null;

    String contactId = context.getRequest().getParameter("id");

    Connection db = null;
    Contact newContact = null;
    Organization thisOrganization = null;

    try {
      db = this.getConnection(context);
      newContact = new Contact(db, contactId);

      LookupList phoneTypeList = new LookupList(db, "lookup_contactphone_types");
      context.getRequest().setAttribute("ContactPhoneTypeList", phoneTypeList);

      LookupList emailTypeList = new LookupList(db, "lookup_contactemail_types");
      context.getRequest().setAttribute("ContactEmailTypeList", emailTypeList);

      LookupList addressTypeList = new LookupList(db, "lookup_contactaddress_types");
      context.getRequest().setAttribute("ContactAddressTypeList", addressTypeList);

      thisOrganization = new Organization(db, newContact.getOrgId());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {

      if (!hasAuthority(context, newContact.getOwner())) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("ContactDetails", newContact);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
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
    if (!(hasPermission(context, "accounts-accounts-contacts-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;

    Contact thisContact = null;
    Organization thisOrganization = null;
    String orgId = null;

    if (context.getRequest().getParameter("orgId") != null) {
      orgId = context.getRequest().getParameter("orgId");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, context.getRequest().getParameter("id"));
      recordDeleted = thisContact.delete(db);
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Delete Contact");
    if (errorMessage == null) {
      context.getRequest().setAttribute("orgId", orgId);
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", "Contacts.do?command=View&orgId=" + orgId);
        deleteRecentItem(context, thisContact);
        return ("DeleteOK");
      } else {
        processErrors(context, thisContact.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Modify Contact
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-edit"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "Modify Contact");
    Exception errorMessage = null;

    String orgid = context.getRequest().getParameter("orgId");
    String passedId = context.getRequest().getParameter("id");

    Connection db = null;
    Contact newContact = null;
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);
      newContact = new Contact(db, "" + passedId);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));
      buildFormElements(context, db);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {

      if (!hasAuthority(context, newContact.getOwner())) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("ContactDetails", newContact);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      return ("ModifyOK");
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
    if (!(hasPermission(context, "accounts-accounts-contacts-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    Contact newContact = (Contact) context.getFormBean();
    newContact.setRequestItems(context.getRequest());

    if (context.getRequest().getParameter("primaryContact") != null) {
      if (context.getRequest().getParameter("primaryContact").equalsIgnoreCase("true")) {
        newContact.setPrimaryContact(true);
      }
    }

    Organization thisOrganization = null;
    String orgid = context.getRequest().getParameter("orgId");

    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      newContact.setEnteredBy(getUserId(context));
      newContact.setTypeList(context.getRequest().getParameterValues("selectedList"));
      newContact.setModifiedBy(getUserId(context));
      resultCount = newContact.update(db);
      if (resultCount == -1) {
        processErrors(context, newContact.getErrors());
        buildFormElements(context, db);
        thisOrganization = new Organization(db, Integer.parseInt(orgid));
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
        return ("ModifyOK");
      } else if (resultCount == 1) {
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


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */

  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-view"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "View Contact Details");
    Exception errorMessage = null;

    String orgid = context.getRequest().getParameter("orgId");
    if (orgid == null) {
      orgid = (String) context.getRequest().getAttribute("orgId");
    }

    PagedListInfo companyDirectoryInfo = this.getPagedListInfo(context, "ContactListInfo");
    companyDirectoryInfo.setLink("Contacts.do?command=View&orgId=" + orgid);

    Connection db = null;
    ContactList contactList = new ContactList();
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);
      contactList.setPagedListInfo(companyDirectoryInfo);
      contactList.setOrgId(Integer.parseInt(orgid));
      //only MY contacts
      //contactList.setOwner(getUserId(context));
      contactList.buildList(db);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("ContactList", contactList);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      return ("ListOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected void buildFormElements(ActionContext context, Connection db) throws SQLException {
    ContactTypeList ctl = new ContactTypeList();
    ctl.setIncludeDefinedByUser(this.getUserId(context));
    ctl.setCategory(ContactType.ACCOUNT);
    ctl.buildList(db);
    ctl.addItem(0, "--None--");
    context.getRequest().setAttribute("ContactTypeList", ctl);

    LookupList phoneTypeList = new LookupList(db, "lookup_contactphone_types");
    context.getRequest().setAttribute("ContactPhoneTypeList", phoneTypeList);

    LookupList emailTypeList = new LookupList(db, "lookup_contactemail_types");
    context.getRequest().setAttribute("ContactEmailTypeList", emailTypeList);

    LookupList addressTypeList = new LookupList(db, "lookup_contactaddress_types");
    context.getRequest().setAttribute("ContactAddressTypeList", addressTypeList);
  }

}

