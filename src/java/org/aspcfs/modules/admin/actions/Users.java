package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

/**
 *  Methods for managing users
 *
 *@author     mrajkowski
 *@created    September 17, 2001
 *@version    $Id$
 */
public final class Users extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandListUsers(context);
  }


  /**
   *  Lists the users in the system that have a corresponding contact record
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.6
   */
  public String executeCommandListUsers(ActionContext context) {

    if (!(hasPermission(context, "admin-users-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo listInfo = getPagedListInfo(context, "UserListInfo");
    listInfo.setLink("/Users.do?command=ListUsers");

    Connection db = null;
    UserList list = new UserList();

    try {
      db = getConnection(context);

      if ("disabled".equals(listInfo.getListView())) {
        list.setEnabled(UserList.FALSE);
      } else if ("aliases".equals(listInfo.getListView())) {
        list.setIncludeAliases(true);
        list.setEnabled(UserList.TRUE);
      } else {
        list.setEnabled(UserList.TRUE);
      }
      list.setPagedListInfo(listInfo);
      list.setBuildContact(false);
      list.setBuildHierarchy(false);
      list.buildList(db);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Users", "User List");
    if (errorMessage == null) {
      context.getRequest().setAttribute("UserList", list);
      return ("ListUsersOK");
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
  public String executeCommandViewLog(ActionContext context) {

    if (!(hasPermission(context, "admin-users-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo listInfo = getPagedListInfo(context, "AccessLogInfo");
    listInfo.setLink("/Users.do?command=ViewLog&id=" + context.getRequest().getParameter("id"));

    Connection db = null;
    AccessLogList list = new AccessLogList();
    int userId = -1;
    User newUser = null;

    if (context.getRequest().getParameter("id") != null) {
      userId = Integer.parseInt(context.getRequest().getParameter("id"));
    }

    try {
      db = getConnection(context);
      newUser = new User();
      newUser.setBuildContact(true);
      newUser.buildRecord(db, userId);

      list.setUserId(userId);
      list.setPagedListInfo(listInfo);
      list.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Users", "View User Details");
    if (errorMessage == null) {
      context.getRequest().setAttribute("UserRecord", newUser);
      context.getRequest().setAttribute("AccessLog", list);
      return ("ViewLogOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action that loads a user for display
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.6
   */
  public String executeCommandUserDetails(ActionContext context) {

    if (!(hasPermission(context, "admin-users-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String id = context.getRequest().getParameter("id");
    String action = context.getRequest().getParameter("action");

    Connection db = null;
    User thisUser = new User();

    try {
      db = this.getConnection(context);
      thisUser.setBuildContact(true);
      thisUser.buildRecord(db, Integer.parseInt(id));
      context.getRequest().setAttribute("UserRecord", thisUser);
      addRecentItem(context, thisUser);

      if (action != null && action.equals("modify")) {
        //RoleList roleList = new RoleList(db);
        //context.getRequest().setAttribute("RoleList", roleList);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (action != null && action.equals("modify")) {
        addModuleBean(context, "Users", "Modify User Details");
        return ("UserDetailsModifyOK");
      } else {
        addModuleBean(context, "Users", "View User Details");
        return ("UserDetailsOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }


  /**
   *  Action that generates the form data for inserting a new user
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.6
   */
  public String executeCommandInsertUserForm(ActionContext context) {

    if (!(hasPermission(context, "admin-users-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "Add User", "Add New User");

    Connection db = null;
    try {
      String typeId = context.getRequest().getParameter("typeId");
      if (typeId == null || typeId.equals("")) {
        typeId = "" + Contact.EMPLOYEE_TYPE;
      }
      String contactId = context.getRequest().getParameter("contactId");

      db = this.getConnection(context);
      ContactTypeList contactTypeList = new ContactTypeList();
      contactTypeList.setShowEmployees(true);
      contactTypeList.buildList(db);
      contactTypeList.setDefaultKey(typeId);
      contactTypeList.setJsEvent("onChange=\"javascript:updateContactList();\"");
      context.getRequest().setAttribute("ContactTypeList", contactTypeList);

      ContactList contactList = new ContactList();
      contactList.setEmptyHtmlSelectRecord("-- Please Select --");
      contactList.setIncludeNonUsersOnly(true);
      contactList.setPersonalId(getUserId(context));
      contactList.setTypeId(Integer.parseInt(typeId));
      contactList.buildList(db);
      context.getRequest().setAttribute("ContactList", contactList);

      RoleList roleList = new RoleList();
      roleList.setEmptyHtmlSelectRecord("-- Please Select --");
      roleList.buildList(db);
      context.getRequest().setAttribute("RoleList", roleList);

      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord("-- None --");
      userList.setBuildContact(true);
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("UserInsertFormOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action that generates a filtered contact list for use in a combobox
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandContactJSList(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      String typeId = context.getRequest().getParameter("typeId");
      if (typeId == null || typeId.equals("")) {
        typeId = "" + Contact.EMPLOYEE_TYPE;
      }
      db = this.getConnection(context);

      ContactList contactList = new ContactList();
      contactList.setPersonalId(getUserId(context));
      contactList.setTypeId(Integer.parseInt(typeId));
      contactList.setIncludeNonUsersOnly(true);
      contactList.buildList(db);
      context.getRequest().setAttribute("ContactList", contactList);

    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    return ("ContactJSListOK");
  }


  /**
   *  Action that adds a user to the database based on submitted html form
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.7
   */
  public String executeCommandAddUser(ActionContext context) {

    if (!(hasPermission(context, "admin-users-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    User insertedUser = null;

    try {
      db = getConnection(context);
      User thisUser = (User) context.getRequest().getAttribute("UserRecord");

      if (context.getRequest().getParameter("typeId") != null) {
        ((Contact) thisUser.getContact()).setTypeId(context.getRequest().getParameter("typeId"));
      }

      thisUser.setEnteredBy(getUserId(context));
      thisUser.setModifiedBy(getUserId(context));

      recordInserted = thisUser.insert(db, context);
      if (recordInserted) {
        insertedUser = new User();
        insertedUser.setBuildContact(true);
        //thisUser = new User(db, thisUser.getId());
        insertedUser.buildRecord(db, thisUser.getId());
        context.getRequest().setAttribute("UserRecord", insertedUser);
        addRecentItem(context, insertedUser);
        context.getRequest().setAttribute("UserRecord", insertedUser);
        updateSystemHierarchyCheck(db, context);
      } else {
        processErrors(context, thisUser.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("UserDetailsOK");
      } else {
        return (executeCommandInsertUserForm(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action that deletes a user from the database. No longer used because
   *  referential integrity is kept.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.12
   *@deprecated
   */
  public String executeCommandDeleteUser(ActionContext context) {

    if (!(hasPermission(context, "admin-users-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    User thisUser = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisUser = new User(db, context.getRequest().getParameter("id"));
      recordDeleted = thisUser.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Users", "Delete User");
    if (errorMessage == null) {
      if (recordDeleted) {
        return ("UserDeleteOK");
      } else {
        processErrors(context, thisUser.getErrors());
        return (executeCommandListUsers(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action to disable a user that is currently enabled.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDisableUser(ActionContext context) {

    if (!(hasPermission(context, "admin-users-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDisabled = false;
    User thisUser = null;
    Ticket thisTicket = null;
    User thisRec = ((UserBean) context.getSession().getAttribute("User")).getUserRecord();
    User managerUser = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisUser = new User(db, context.getRequest().getParameter("id"));

      recordDisabled = thisUser.delete(db);

      if (recordDisabled) {
        thisTicket = new Ticket();
        thisTicket.setProblem("CFS User " + thisUser.getUsername() + " has been disabled by " + thisRec.getUsername() +
            ".  Since you are the direct manager of " + thisUser.getUsername() + ", you have been notified.  It is essential that " +
            "any data still directly associated with this disabled User gets re-assigned as soon as possible.");
        thisTicket.setEnteredBy(thisRec.getId());
        thisTicket.setModifiedBy(thisRec.getId());
        thisTicket.setOrgId(0);
        thisTicket.setContactId(thisUser.getContactId());
        thisTicket.setSeverityCode(3);
        thisTicket.setPriorityCode(3);

        if (thisUser.getManagerId() > -1) {
          managerUser = new User();
          managerUser.setBuildContact(true);
          managerUser.buildRecord(db, thisUser.getManagerId());
          updateSystemHierarchyCheck(db, context);
          thisTicket.setAssignedTo(thisUser.getManagerId());
          thisTicket.setDepartmentCode(managerUser.getContact().getDepartment());
        } else {
          thisTicket.setDepartmentCode(thisUser.getContact().getDepartment());
        }

        thisTicket.insert(db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Users", "Delete User");
    if (errorMessage == null) {
      if (recordDisabled) {
        return ("UserDeleteOK");
      } else {
        processErrors(context, thisUser.getErrors());
        return (executeCommandListUsers(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action to enable a user that is currently disabled.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandEnableUser(ActionContext context) {

    if (!(hasPermission(context, "admin-users-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordEnabled = false;
    User thisUser = null;
    User managerUser = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisUser = new User(db, context.getRequest().getParameter("id"));
      recordEnabled = thisUser.enable(db);

      if (recordEnabled) {
        updateSystemHierarchyCheck(db, context);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Users", "Enable User");
    if (errorMessage == null) {
      if (!recordEnabled) {
        processErrors(context, thisUser.getErrors());
      }
      return (executeCommandListUsers(context));
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
  public String executeCommandDisableUserConfirm(ActionContext context) {

    if (!(hasPermission(context, "admin-users-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    User thisUser = null;
    User managerUser = null;

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisUser = new User();
      thisUser.setBuildContact(true);
      thisUser.buildRecord(db, Integer.parseInt(context.getRequest().getParameter("id")));

      if (thisUser.getManagerId() > -1) {
        managerUser = new User();
        managerUser.setBuildContact(true);
        managerUser.buildRecord(db, thisUser.getManagerId());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Users", "Disable User");
    if (errorMessage == null) {
      context.getRequest().setAttribute("User", thisUser);
      context.getRequest().setAttribute("ManagerUser", managerUser);
      return ("UserDisableConfirmOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }


  /**
   *  Action to generate the form for modifying a user
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.12
   */
  public String executeCommandModifyUser(ActionContext context) {

    if (!(hasPermission(context, "admin-users-edit"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Users", "Modify User");
    Exception errorMessage = null;

    String userId = context.getRequest().getParameter("id");

    Connection db = null;
    User newUser = null;

    try {
      db = this.getConnection(context);
      newUser = new User();
      newUser.setBuildContact(true);
      newUser.buildRecord(db, Integer.parseInt(userId));

      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord("-- None --");
      userList.setBuildContact(false);
      userList.setExcludeDisabledIfUnselected(true);
      userList.buildList(db);

      context.getRequest().setAttribute("UserList", userList);

      RoleList roleList = new RoleList();
      roleList.setEmptyHtmlSelectRecord("-- None --");
      roleList.buildList(db);
      context.getRequest().setAttribute("RoleList", roleList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("UserRecord", newUser);
      return ("UserModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action that updates the user record based on the submitted form
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.12
   */
  public String executeCommandUpdateUser(ActionContext context) {

    if (!(hasPermission(context, "admin-users-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    User newUser = (User) context.getFormBean();

    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      newUser.setModifiedBy(getUserId(context));
      resultCount = newUser.update(db, context);

      if (resultCount == -1) {
        UserList userList = new UserList();
        userList.setEmptyHtmlSelectRecord("-- None --");
        userList.setBuildContact(true);
        userList.buildList(db);
        context.getRequest().setAttribute("UserList", userList);

        RoleList roleList = new RoleList();
        roleList.setEmptyHtmlSelectRecord("-- None --");
        roleList.buildList(db);
        context.getRequest().setAttribute("RoleList", roleList);
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("generatePass") != null) {
          resultCount = newUser.generateRandomPassword(db, context);
        }
        updateSystemHierarchyCheck(db, context);
        //NOTE: I believe this is no longer required since permissions are
        //cached an are no longer part of the user object
        //updateSystemPermissionCheck(db, context);
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        processErrors(context, newUser.getErrors());
        return ("UserModifyOK");
      } else if (resultCount == 1) {
        context.getRequest().setAttribute("id", context.getRequest().getParameter("id"));

        if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
          return (executeCommandListUsers(context));
        } else {
          return ("UserUpdateOK");
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

