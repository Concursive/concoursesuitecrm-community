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
package org.aspcfs.modules.admin.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactTypeList;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.controller.ApplicationPrefs;

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
    if (!hasPermission(context, "admin-users-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    UserList list = new UserList();
    //Configure the pagedList
    PagedListInfo listInfo = getPagedListInfo(context, "UserListInfo");
    listInfo.setLink("Users.do?command=ListUsers");
    try {
      db = getConnection(context);
      if ("disabled".equals(listInfo.getListView())) {
        list.setEnabled(UserList.FALSE);
        list.setIncludeAliases(Constants.UNDEFINED);
      } else if ("aliases".equals(listInfo.getListView())) {
        list.setEnabled(UserList.TRUE);
        list.setIncludeAliases(Constants.TRUE);
      } else {
        list.setEnabled(UserList.TRUE);
        list.setIncludeAliases(Constants.UNDEFINED);
      }
      list.setPagedListInfo(listInfo);
      list.setBuildContact(false);
      list.setBuildContactDetails(false);
      list.setBuildHierarchy(false);
      list.setRoleType(Constants.ROLETYPE_REGULAR); //fetch only regular users
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
    listInfo.setLink("Users.do?command=ViewLog&id=" + context.getRequest().getParameter("id"));

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
    if (!hasPermission(context, "admin-users-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    addModuleBean(context, "Add User", "Add New User");
    try {
      //Load the license and see how many users can be added
      java.security.Key key = org.aspcfs.utils.PrivateString.loadKey(getPref(context, "FILELIBRARY") + "init" + fs + "zlib.jar");
      org.aspcfs.utils.XMLUtils xml = new org.aspcfs.utils.XMLUtils(org.aspcfs.utils.PrivateString.decrypt(key, org.aspcfs.utils.StringUtils.loadText(getPref(context, "FILELIBRARY") + "init" + fs + "input.txt")));
      String lpd = org.aspcfs.utils.XMLUtils.getNodeText(xml.getFirstChild("text2"));
      db = this.getConnection(context);
      //Check the license
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) AS user_count " +
          "FROM access a, role r " +
          "WHERE a.user_id > 0 " +
          "AND a.role_id > 0 " +
          "AND a.role_id = r.role_id " +
          "AND r.role_type = ? " +
          "AND a.enabled = ? ");
      pst.setInt(1, Constants.ROLETYPE_REGULAR);
      pst.setBoolean(2, true);
      ResultSet rs = pst.executeQuery();
      int current = 0;
      if (rs.next()) {
        current = rs.getInt("user_count");
      }
      if (!"-1".equals(lpd.substring(7)) && current >= Integer.parseInt(lpd.substring(7))) {
        return ("LicenseError");
      }
      rs.close();
      pst.close();
      //Prepare the role drop-down
      RoleList roleList = new RoleList();
      roleList.setBuildUsers(false);
      roleList.setEmptyHtmlSelectRecord("-- Please Select --");
      roleList.setRoleType(Constants.ROLETYPE_REGULAR);
      roleList.setEnabledState(Constants.TRUE);
      roleList.buildList(db);
      context.getRequest().setAttribute("RoleList", roleList);
      //Prepare the user drop-down
      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord("-- None --");
      userList.setBuildContact(false);
      userList.setBuildContactDetails(false);
      userList.setRoleType(Constants.ROLETYPE_REGULAR);
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("UserInsertFormOK");
  }


  /**
   *  Action that adds a user to the database based on submitted html form
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.7
   */
  public String executeCommandAddUser(ActionContext context) {
    if (!hasPermission(context, "admin-users-add")) {
      return ("PermissionError");
    }
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
    Connection db = null;
    boolean recordInserted = false;
    User insertedUser = null;
    try {
      synchronized(this) {
        //Load the license and see how many users can be added
        java.security.Key key = org.aspcfs.utils.PrivateString.loadKey(getPref(context, "FILELIBRARY") + "init" + fs + "zlib.jar");
        org.aspcfs.utils.XMLUtils xml = new org.aspcfs.utils.XMLUtils(org.aspcfs.utils.PrivateString.decrypt(key, org.aspcfs.utils.StringUtils.loadText(getPref(context, "FILELIBRARY") + "init" + fs + "input.txt")));
        String lpd = org.aspcfs.utils.XMLUtils.getNodeText(xml.getFirstChild("text2"));
        db = this.getConnection(context);
        //Check the license
        PreparedStatement pst = db.prepareStatement(
            "SELECT count(*) AS user_count " +
            "FROM access a, role r " +
            "WHERE a.user_id > 0 " +
            "AND a.role_id > 0 " +
            "AND a.role_id = r.role_id " +
            "AND r.role_type = ? " +
            "AND a.enabled = ? ");
        pst.setInt(1, Constants.ROLETYPE_REGULAR);
        pst.setBoolean(2, true);
        ResultSet rs = pst.executeQuery();
        int current = 0;
        if (rs.next()) {
          current = rs.getInt("user_count");
        }
        if (!"-1".equals(lpd.substring(7)) && current >= Integer.parseInt(lpd.substring(7))) {
          return ("LicenseError");
        }
        rs.close();
        pst.close();
        //Process the user request form
        User thisUser = (User) context.getFormBean();
        if (context.getRequest().getParameter("typeId") != null) {
          ((Contact) thisUser.getContact()).addType(context.getRequest().getParameter("typeId"));
        }
        thisUser.setEnteredBy(getUserId(context));
        thisUser.setModifiedBy(getUserId(context));
        thisUser.setTimeZone(prefs.get("SYSTEM.TIMEZONE"));
        thisUser.setCurrency(prefs.get("SYSTEM.CURRENCY"));
        thisUser.setLanguage(prefs.get("SYSTEM.LANGUAGE"));
        
        recordInserted = thisUser.insert(db, context);
        if (recordInserted) {
          insertedUser = new User();
          insertedUser.setBuildContact(true);
          insertedUser.buildRecord(db, thisUser.getId());
          addRecentItem(context, insertedUser);
          context.getRequest().setAttribute("UserRecord", insertedUser);
          updateSystemHierarchyCheck(db, context);
        } else {
          if (thisUser.getContactId() != -1) {
            thisUser.setContact(new Contact(db, thisUser.getContactId()));
          }
          context.getRequest().setAttribute("UserRecord", thisUser);
          processErrors(context, thisUser.getErrors());
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    if (recordInserted) {
      return ("UserDetailsOK");
    } else {
      return (executeCommandInsertUserForm(context));
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
    if (!hasPermission(context, "admin-users-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    User thisUser = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisUser = new User(db, context.getRequest().getParameter("id"));
      recordDeleted = thisUser.delete(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Users", "Delete User");
    if (recordDeleted) {
      return ("UserDeleteOK");
    } else {
      processErrors(context, thisUser.getErrors());
      return (executeCommandListUsers(context));
    }
  }


  /**
   *  Action to disable a user that is currently enabled.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDisableUser(ActionContext context) {
    if (!hasPermission(context, "admin-users-delete")) {
      return ("PermissionError");
    }
    boolean recordDisabled = false;
    User thisUser = null;
    Ticket thisTicket = null;
    User thisRec = ((UserBean) context.getSession().getAttribute("User")).getUserRecord();
    User managerUser = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisUser = new User(db, context.getRequest().getParameter("id"));
      if (context.getRequest().getParameter("disablecontact") != null) {
        Contact thisContact = new Contact(db, thisUser.getContactId());
        thisContact.disable(db);
      }
      recordDisabled = thisUser.delete(db);
      if (recordDisabled) {
        thisTicket = new Ticket();
        thisTicket.setProblem(
            "Dark Horse CRM user " + thisUser.getUsername() + " has been disabled by " + thisRec.getUsername() +
            ".  Since you are the direct manager of " + thisUser.getUsername() + ", you have been notified.  It is essential that " +
            "any data still directly associated with this disabled user gets re-assigned as soon as possible.");
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
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Users", "Delete User");
    if (recordDisabled) {
      return ("UserDeleteOK");
    } else {
      processErrors(context, thisUser.getErrors());
      return (executeCommandListUsers(context));
    }
  }


  /**
   *  Action to enable a user that is currently disabled.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandEnableUser(ActionContext context) {
    if (!hasPermission(context, "admin-users-edit")) {
      return ("PermissionError");
    }
    boolean recordEnabled = false;
    User thisUser = null;
    User managerUser = null;
    Connection db = null;
    try {
      synchronized(this) {
        //Load the license and see how many users can be added
        java.security.Key key = org.aspcfs.utils.PrivateString.loadKey(getPref(context, "FILELIBRARY") + "init" + fs + "zlib.jar");
        org.aspcfs.utils.XMLUtils xml = new org.aspcfs.utils.XMLUtils(org.aspcfs.utils.PrivateString.decrypt(key, org.aspcfs.utils.StringUtils.loadText(getPref(context, "FILELIBRARY") + "init" + fs + "input.txt")));
        String lpd = org.aspcfs.utils.XMLUtils.getNodeText(xml.getFirstChild("text2"));
        db = this.getConnection(context);
        //Check the license
        PreparedStatement pst = db.prepareStatement(
            "SELECT count(*) AS user_count " +
            "FROM access a, role r " +
            "WHERE a.user_id > 0 " +
            "AND a.role_id > 0 " +
            "AND a.role_id = r.role_id " +
            "AND r.role_type = ? " +
            "AND a.enabled = ? ");
        pst.setInt(1, Constants.ROLETYPE_REGULAR);
        pst.setBoolean(2, true);
        ResultSet rs = pst.executeQuery();
        int current = 0;
        if (rs.next()) {
          current = rs.getInt("user_count");
        }
        if (!"-1".equals(lpd.substring(7)) && current >= Integer.parseInt(lpd.substring(7))) {
          return ("LicenseError");
        }
        rs.close();
        pst.close();
        //Activate the user
        thisUser = new User(db, context.getRequest().getParameter("id"));
        //Reactivate the contact if it was previously de-activated
        Contact thisContact = new Contact(db, thisUser.getContactId());
        if (!thisContact.getEnabled()) {
          thisContact.enable(db);
        }
        recordEnabled = thisUser.enable(db);
        if (recordEnabled) {
          updateSystemHierarchyCheck(db, context);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Users", "Enable User");
    if (!recordEnabled) {
      processErrors(context, thisUser.getErrors());
    }
    return (executeCommandListUsers(context));
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDisableUserConfirm(ActionContext context) {
    if (!hasPermission(context, "admin-users-delete")) {
      return ("PermissionError");
    }
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
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Users", "Disable User");
    context.getRequest().setAttribute("User", thisUser);
    context.getRequest().setAttribute("ManagerUser", managerUser);
    return ("UserDisableConfirmOK");
  }


  /**
   *  Action to generate the form for modifying a user
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.12
   */
  public String executeCommandModifyUser(ActionContext context) {
    if (!hasPermission(context, "admin-users-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Users", "Modify User");
    Connection db = null;
    User newUser = null;
    //Process the request items
    String userId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      //Prepare the user
      newUser = new User();
      newUser.setBuildContact(true);
      newUser.setBuildContactDetails(true);
      newUser.buildRecord(db, Integer.parseInt(userId));
      //Prepare the user list
      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord("-- None --");
      userList.setBuildContact(false);
      userList.setBuildContactDetails(false);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setRoleType(Constants.ROLETYPE_REGULAR);
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);
      //Prepare the role list
      RoleList roleList = new RoleList();
      roleList.setBuildUsers(false);
      roleList.setEmptyHtmlSelectRecord("-- None --");
      roleList.setEnabledState(Constants.TRUE);
      roleList.setRoleType(Constants.ROLETYPE_REGULAR);
      roleList.buildList(db);
      context.getRequest().setAttribute("RoleList", roleList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("UserRecord", newUser);
    return ("UserModifyOK");
  }


  /**
   *  Action that updates the user record based on the submitted form
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.12
   */
  public String executeCommandUpdateUser(ActionContext context) {
    if (!hasPermission(context, "admin-users-edit")) {
      return ("PermissionError");
    }
    User newUser = (User) context.getFormBean();
    Connection db = null;
    int resultCount = 0;
    try {
      db = this.getConnection(context);
      newUser.setModifiedBy(getUserId(context));
      resultCount = newUser.update(db, context);
      if (resultCount == -1) {
        //Prepare the form again
        //Prepare the user list
        UserList userList = new UserList();
        userList.setEmptyHtmlSelectRecord("-- None --");
        userList.setBuildContact(false);
        userList.setBuildContactDetails(false);
        userList.setRoleType(Constants.ROLETYPE_REGULAR);
        userList.buildList(db);
        context.getRequest().setAttribute("UserList", userList);
        //Prepare the role list
        RoleList roleList = new RoleList();
        roleList.setBuildUsers(false);
        roleList.setEmptyHtmlSelectRecord("-- None --");
        roleList.setEnabledState(Constants.TRUE);
        roleList.setRoleType(Constants.ROLETYPE_REGULAR);
      roleList.buildList(db);
        context.getRequest().setAttribute("RoleList", roleList);
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("generatePass") != null) {
          resultCount = newUser.generateRandomPassword(db, context);
        }
        updateSystemHierarchyCheck(db, context);
        //NOTE: I believe this is no longer required since permissions are
        //cached and are no longer part of the user object
        //updateSystemPermissionCheck(db, context);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
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
  }
}

