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
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.base.*;
import org.aspcfs.utils.web.*;

/**
 *  Description of the Class
 *
 * @author     chris
 * @created    August 29, 2001
 * @version    $Id$
 */
public final class Contacts extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;

    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrganization = null;
    Contact thisContact = (Contact) context.getFormBean();
    if (thisContact.getId() == -1) {
      if (!(hasPermission(context, "accounts-accounts-contacts-add"))) {
        return ("PermissionError");
      }
      addModuleBean(context, "View Accounts", "Add Contact to Account");
    }

    try {
      db = this.getConnection(context);
      //prepare org
      if (context.getRequest().getAttribute("OrgDetails") == null) {
        thisOrganization = new Organization(db, Integer.parseInt(orgId));
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
      }
      //prepare contact type list
      ContactTypeList ctl = new ContactTypeList();
      ctl.setIncludeDefinedByUser(this.getUserId(context));
      ctl.setCategory(ContactType.ACCOUNT);
      ctl.buildList(db);
      ctl.addItem(0, "--None--");
      context.getRequest().setAttribute("ContactTypeList", ctl);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("PrepareOK");
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
  public String executeCommandClone(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-add"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "Clone Account Contact");
    Exception errorMessage = null;
    Connection db = null;

    String contactId = context.getRequest().getParameter("id");
    Contact cloneContact = null;

    try {
      db = this.getConnection(context);
      cloneContact = new Contact(db, contactId);
      cloneContact.resetBaseInfo();
      context.getRequest().setAttribute("ContactDetails", cloneContact);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return executeCommandPrepare(context);
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
  public String executeCommandSave(ActionContext context) {
    Exception errorMessage = null;
    boolean recordInserted = false;
    int resultCount = 0;
    String permission = "accounts-accounts-contacts-add";
    Contact thisContact = (Contact) context.getFormBean();
    if (thisContact.getId() >  0) {
      permission = "accounts-accounts-contacts-edit";
    }
    if (!hasPermission(context, permission)) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Save Contact to Account");
    Organization thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      boolean newContact = (thisContact.getId() == -1);
      thisContact.setRequestItems(context.getRequest());
      thisContact.setTypeList(context.getRequest().getParameterValues("selectedList"));
      thisContact.setModifiedBy(getUserId(context));
      thisContact.setEnteredBy(getUserId(context));
      
      //make sure all can see the contact
      AccessTypeList accessTypes = this.getSystemStatus(context).getAccessTypeList(db, AccessType.ACCOUNT_CONTACTS);
      thisContact.setAccessType(accessTypes.getDefaultItem());
      
      if ("true".equals(context.getRequest().getParameter("primaryContact"))) {
        thisContact.setPrimaryContact(true);
      }
      if (newContact) {
        thisContact.setOwner(getUserId(context));
        recordInserted = thisContact.insert(db);
      } else {
        resultCount = thisContact.update(db);
      }

      if (recordInserted || resultCount == 1) {
        thisContact = new Contact(db, "" + thisContact.getId());
        context.getRequest().setAttribute("ContactDetails", thisContact);
        if (resultCount == 1) {
          thisContact.checkUserAccount(db);
          this.updateUserContact(db, context, thisContact.getUserId());
        }
        thisOrganization = new Organization(db, thisContact.getOrgId());
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
      }
      if (!recordInserted && resultCount < 1) {
        processErrors(context, thisContact.getErrors());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("orgId", "" + thisContact.getOrgId());
      if (recordInserted) {
        if ("true".equals(context.getRequest().getParameter("saveAndClone"))) {
          thisContact.resetBaseInfo();
          context.getRequest().setAttribute("ContactDetails", thisContact);
          return (executeCommandPrepare(context));
        }
        if (context.getRequest().getParameter("popup") != null) {
          return ("CloseAddPopup");
        }
        return ("DetailsOK");
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
          return (executeCommandView(context));
        } else {
          return ("UpdateOK");
        }
      } else {
        context.getRequest().setAttribute("TypeList", thisContact.getTypeList());
        return (executeCommandPrepare(context));
      }
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
        } else if (dependencies.canDelete()) {
          htmlDialog.setTitle("CFS: Confirm Delete");
          htmlDialog.setHeader("The contact you are requesting to delete has the following dependencies within CFS:");
          htmlDialog.addButton("Delete All", "javascript:window.location.href='Contacts.do?command=Delete&orgId=" + orgId + "&id=" + id + "'");
          htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
        } else {
          htmlDialog.setHeader("This contact cannot be deleted because it has the following dependencies within CFS:");
          htmlDialog.addButton("OK", "javascript:parent.window.close()");
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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-view")) {
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
      thisOrganization = new Organization(db, newContact.getOrgId());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-delete")) {
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
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Modify Contact");
    Exception errorMessage = null;
    String passedId = context.getRequest().getParameter("id");
    Connection db = null;
    Contact newContact = null;
    try {
      db = this.getConnection(context);
      newContact = (Contact) context.getFormBean();
      newContact.queryRecord(db, Integer.parseInt(passedId));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return executeCommandPrepare(context);
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
      contactList.setBuildDetails(true);
      contactList.setBuildTypes(false);
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
}

