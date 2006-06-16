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

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.hooks.CustomHook;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessLogList;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.UserEmail;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.FileUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.BatchInfo;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Methods for managing users
 *
 * @author     mrajkowski
 * @created    September 17, 2001
 * @version    $Id: Users.java 15115 2006-05-31 16:47:51 +0000 (Wed, 31 May
 *      2006) matt $
 */
public final class Users extends CFSModule {
  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandListUsers(context);
  }


  /**
   *  Lists the users in the system that have a corresponding contact record
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.6
   */
  public String executeCommandListUsers(ActionContext context) {
    if (!hasPermission(context, "admin-users-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    UserList list = new UserList();
    RoleList roleList = new RoleList();
    //Configure the pagedList
    PagedListInfo listInfo = getPagedListInfo(context, "UserListInfo");
    listInfo.setLink("Users.do?command=ListUsers");
    //Configure batch feature
    BatchInfo batchInfo = new BatchInfo("batchUsers");
    batchInfo.setAction("Users.do?command=ProcessBatch");

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
      list.setRoleType(Constants.ROLETYPE_REGULAR);
      //fetch only regular users
      list.setRoleId(listInfo.getFilterKey("listFilter1"));
      list.setSiteId(this.getUserSiteId(context));
      list.buildList(db);

      //build a list of roles
      roleList.setBuildUsers(false);
      roleList.setRoleType(Constants.ROLETYPE_REGULAR);
      roleList.setEnabledState(Constants.TRUE);
      roleList.setEmptyHtmlSelectRecord(this.getSystemStatus(context).getLabel("accounts.any"));
      roleList.setJsEvent("onChange=\"javascript:document.userForm.submit();\"");
      roleList.buildList(db);
      context.getRequest().setAttribute(
          "roleList", roleList);

      batchInfo.setSize(list.size());
      context.getRequest().setAttribute(
          "userListBatchInfo", batchInfo);

      context.getRequest().setAttribute(
          "systemStatus", this.getSystemStatus(context));
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandViewLog(ActionContext context) {

    if (!(hasPermission(context, "admin-users-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo listInfo = getPagedListInfo(context, "AccessLogInfo");
    listInfo.setLink(
        "Users.do?command=ViewLog&id=" + context.getRequest().getParameter(
        "id"));

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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.6
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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.6
   */
  public String executeCommandInsertUserForm(ActionContext context) {
    if (!hasPermission(context, "admin-users-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    addModuleBean(context, "Add User", "Add New User");
    try {
      db = this.getConnection(context);
      String value = CustomHook.populateUser(db, getPref(context, "FILELIBRARY"));
      if (value != null) {
        return value;
      }
      // Set some defaults if first time showing form
      User user = (User) context.getFormBean();
      if (user.getUsername() == null) {
        user.setHasHttpApiAccess(true);
        user.setHasWebdavAccess(true);
      }
      //Prepare the role drop-down
      RoleList roleList = new RoleList();
      roleList.setBuildUsers(false);
      roleList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("admin.users.pleaseSelect"));
      roleList.setRoleType(Constants.ROLETYPE_REGULAR);
      roleList.setEnabledState(Constants.TRUE);
      roleList.buildList(db);
      context.getRequest().setAttribute("RoleList", roleList);
      //Prepare the user drop-down
      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      userList.setBuildContact(false);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);
      userList.setBuildContactDetails(false);
      userList.setRoleType(Constants.ROLETYPE_REGULAR);
      //fetch users who have access to all sites.
      //A user assigned to a site may report to
      //another user who has access to the same site or to one
      //who has accesss to all sites
      userList.setSiteId(this.getUserSiteId(context));
      userList.setIncludeUsersWithAccessToAllSites(true);
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);
      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);
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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.7
   */
  public String executeCommandAddUser(ActionContext context) {
    if (!hasPermission(context, "admin-users-add")) {
      return ("PermissionError");
    }
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    Connection db = null;
    String generatePassword = context.getRequest().getParameter("generatePassword");
    boolean recordInserted = false;
    boolean hasEmailAddress = false;
    User insertedUser = null;
    try {
      synchronized (this) {
        db = this.getConnection(context);
        String value = CustomHook.populateUser(db, getPref(context, "FILELIBRARY"));
        if (value != null) {
          return value;
        }
        //Process the user request form
        User thisUser = (User) context.getFormBean();
        if (context.getRequest().getParameter("typeId") != null) {
          ((Contact) thisUser.getContact()).addType(
              context.getRequest().getParameter("typeId"));
        }
        SystemStatus systemStatus = this.getSystemStatus(context);
        HashMap errors = new HashMap();
        if (thisUser.getManagerId() != -1) {
          User manager = this.getUser(context, thisUser.getManagerId());
          if (!manager.getEnabled()) {
            errors.put(
                "actionError", systemStatus.getLabel(
                "object.validation.genericActionError"));
            errors.put(
                "managerIdError", systemStatus.getLabel(
                "admin.disabledManager.text"));
            errors.putAll(thisUser.getErrors());
            if (thisUser.getContactId() != -1) {
              thisUser.setContact(new Contact(db, thisUser.getContactId()));
            }
            context.getRequest().setAttribute("UserRecord", thisUser);
            processErrors(context, errors);
            if (generatePassword != null && "true".equals(generatePassword)) {
              context.getRequest().setAttribute("generatePassword", generatePassword);
            }
            return (executeCommandInsertUserForm(context));
          } else if (manager.getSiteId() != -1) {
            /*
             *  Check compatibility between the site id of the new user
             *  record and the manager record
             */
            if ((manager.getSiteId() != thisUser.getSiteId()) ||
                (thisUser.getSiteId() == -1)) {
              errors.put(
                  "actionError", systemStatus.getLabel(
                  "admin.notTheSameSiteAsManager.text"));
              errors.putAll(thisUser.getErrors());
              if (thisUser.getContactId() != -1) {
                thisUser.setContact(new Contact(db, thisUser.getContactId()));
              }
              context.getRequest().setAttribute("UserRecord", thisUser);
              processErrors(context, errors);
              if (generatePassword != null && "true".equals(generatePassword)) {
                context.getRequest().setAttribute("generatePassword", generatePassword);
              }
              return (executeCommandInsertUserForm(context));
            }
          }
        }
        Contact contactForUser = new Contact(db, thisUser.getContactId());
        /*
         *  Check if the new user's contact has an email address
         */
        //TODO Add code to check for the contact's email adderss.
        //Add error if the generatePassword is true and contact does not have email address.
        if (contactForUser.getPrimaryEmailAddress() != null && !"".equals(contactForUser.getPrimaryEmailAddress())) {
          hasEmailAddress = true;
        }
        if (generatePassword != null && "true".equals(generatePassword)) {
          if (!hasEmailAddress) {
            errors.put("password1Error", systemStatus.getLabel("admin.generatePasswordError.text"));
            errors.putAll(thisUser.getErrors());
            processErrors(context, errors);
            return (executeCommandInsertUserForm(context));
          } else {
            //has password been generated?
            String password = null;
            password = StringUtils.randomString(6, 8) + String.valueOf(StringUtils.rand(1, 9999));
            thisUser.setPassword1(password);
            thisUser.setPassword2(password);
          }
        }
        /*
         *  Check compatibility between the site id of the contact record
         *  and the user record
         */
        if (contactForUser.getSiteId() != thisUser.getSiteId()) {
          errors.put(
              "actionError", systemStatus.getLabel(
              "admin.notTheSameSiteAsContact.text"));
          errors.putAll(thisUser.getErrors());
          processErrors(context, errors);
          if (thisUser.getContactId() != -1) {
            thisUser.setContact(contactForUser);
          }
          context.getRequest().setAttribute("UserRecord", thisUser);
          if (generatePassword != null && "true".equals(generatePassword)) {
            context.getRequest().setAttribute("generatePassword", generatePassword);
          }
          return (executeCommandInsertUserForm(context));
        }

        thisUser.setEnteredBy(getUserId(context));
        thisUser.setModifiedBy(getUserId(context));
        thisUser.setTimeZone(prefs.get("SYSTEM.TIMEZONE"));
        thisUser.setCurrency(prefs.get("SYSTEM.CURRENCY"));
        thisUser.setLanguage(systemStatus.getLanguage());
        recordInserted = thisUser.insert(db, context);
        if (recordInserted) {
          insertedUser = new User();
          insertedUser.setBuildContact(true);
          insertedUser.setBuildContactDetails(true);
          insertedUser.buildRecord(db, thisUser.getId());
          addRecentItem(context, insertedUser);
          context.getRequest().setAttribute("UserRecord", insertedUser);
          updateSystemHierarchyCheck(db, context);
          String templateFile = getDbNamePath(context) + "templates_" + getUserLanguage(context) + ".xml";
          if (!FileUtils.fileExists(templateFile)) {
            templateFile = getDbNamePath(context) + "templates_en_US.xml";
          }
          User modifiedByUser = new User();
          modifiedByUser.setBuildContact(true);
          modifiedByUser.setBuildContactDetails(true);
          modifiedByUser.buildRecord(db, this.getUserId(context));
          sendEmail(context, insertedUser, modifiedByUser, templateFile, thisUser.getPassword1());
        } else {
          if (generatePassword != null && "true".equals(generatePassword)) {
            context.getRequest().setAttribute("generatePassword", generatePassword);
          }
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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.12
   * @deprecated
   */
  public String executeCommandDeleteUser(ActionContext context) {
    if (!hasPermission(context, "admin-users-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    User thisUser = null;
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      thisUser = new User(db, context.getRequest().getParameter("id"));
      recordDeleted = thisUser.disable(db);
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
      thisUser.getErrors().put(
          "actionError", systemStatus.getLabel(
          "object.validation.actionError.userDeletion"));
      processErrors(context, thisUser.getErrors());
      return (executeCommandListUsers(context));
    }
  }


  /**
   *  Action to disable a user that is currently enabled.
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDisableUser(ActionContext context) {
    if (!hasPermission(context, "admin-users-delete")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
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
      recordDisabled = thisUser.disable(db);
      if (recordDisabled) {
        thisTicket = new Ticket();
        HashMap map = new HashMap();
        map.put("${thisUser.username}", thisUser.getUsername());
        map.put("${thisRec.username}", thisRec.getUsername());
        Template template = new Template(
            systemStatus.getLabel("ticket.problem.userDisabled"));
        template.setParseElements(map);
        thisTicket.setProblem(template.getParsedText());
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
          thisTicket.setDepartmentCode(
              managerUser.getContact().getDepartment());
        } else {
          thisTicket.setDepartmentCode(thisUser.getContact().getDepartment());
        }
        thisTicket.insert(db);
      } else {
        thisUser.getErrors().put(
            "actionError", systemStatus.getLabel(
            "object.validation.actionError.userNotDisabled"));
        processErrors(context, thisUser.getErrors());
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandEnableUser(ActionContext context) {
    if (!hasPermission(context, "admin-users-edit")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    boolean recordEnabled = false;
    User thisUser = null;
    Connection db = null;
    try {
      synchronized (this) {
        db = this.getConnection(context);
        String value = CustomHook.populateUser(db, getPref(context, "FILELIBRARY"));
        if (value != null) {
          return value;
        }
        //Activate the user
        thisUser = new User(db, context.getRequest().getParameter("id"));
        //Reactivate the contact if it was previously de-activated
        Contact thisContact = new Contact(db, thisUser.getContactId());
        if (!thisContact.getEnabled() || thisContact.isTrashed()) {
          return "PermissionError";
        } else {
          recordEnabled = thisUser.enable(db);
        }
        if (recordEnabled) {
          updateSystemHierarchyCheck(db, context);
        } else {
          thisUser.getErrors().put(
              "actionError", systemStatus.getLabel(
              "object.validation.actionError.userNotEnabled"));
          processErrors(context, thisUser.getErrors());
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
      thisUser.buildRecord(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.12
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
      userList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      userList.setBuildContact(false);
      userList.setBuildContactDetails(false);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);
      userList.setRoleType(Constants.ROLETYPE_REGULAR);

      //fetch users from the specified site id and users who have
      //access to all sites. A user assigned to as site may report to
      //another user who has access to the same site or to one
      //who has accesss to all sites
      userList.setSiteId(newUser.getSiteId());
      userList.setIncludeUsersWithAccessToAllSites(true);
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);
      //Prepare the role list
      RoleList roleList = new RoleList();
      roleList.setBuildUsers(false);
      roleList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      roleList.setEnabledState(Constants.TRUE);
      roleList.setRoleType(Constants.ROLETYPE_REGULAR);
      roleList.buildList(db);
      context.getRequest().setAttribute("RoleList", roleList);

      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);

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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.12
   */
  public String executeCommandUpdateUser(ActionContext context) {
    if (!hasPermission(context, "admin-users-edit")) {
      return ("PermissionError");
    }
    User newUser = (User) context.getFormBean();
    User manager = null;
    Connection db = null;
    int resultCount = -1;
    boolean isValid = true;
    boolean siteChangeAllowed = false;
    try {
      db = this.getConnection(context);
      newUser.setModifiedBy(getUserId(context));

      SystemStatus systemStatus = this.getSystemStatus(context);
      if (newUser.getManagerId() != -1) {
        manager = this.getUser(context, newUser.getManagerId());
        if (!manager.getEnabled()) {
          newUser.getErrors().put(
              "actionError", systemStatus.getLabel(
              "object.validation.genericActionError"));
          newUser.getErrors().put(
              "managerIdError", systemStatus.getLabel(
              "admin.disabledManager.text"));
          isValid = false;
        } else {
          if (manager.getSiteId() != -1) {
            // check to ensure that the manager is 'null site' or
            // belongs to the same site as the new user
            if ((manager.getSiteId() != newUser.getSiteId()) ||
                (newUser.getSiteId() == -1)) {
              newUser.getErrors().put(
                  "actionError", systemStatus.getLabel(
                  "admin.notTheSameSiteAsManager.text"));
              if (newUser.getContactId() != -1) {
                newUser.setContact(new Contact(db, newUser.getContactId()));
              }
              isValid = false;
            }
          }
        }
      }

      User thisUser = this.getUser(context, this.getUserId(context));
      User oldUser = this.getUser(context, newUser.getId());

      // Has the site information been changed
      if ((newUser.getSiteId() != oldUser.getSiteId()) && isValid) {
        // The currently logged in user should be from "null site" to be
        // able to modify the site information of another user.
        if (thisUser.getSiteId() == -1) {
          //By pass dependency additional dependency checks if the site is
          //being changed to null site.
          Contact contactForUser = new Contact(db, newUser.getContactId());
          if (newUser.getSiteId() != -1) {
            // Is user is changing his own site from 'null site' to a specific site
            if (thisUser.getId() == oldUser.getId()) {
              //find out if other null users exist
              UserList nullSiteUserList = new UserList();
              nullSiteUserList.setIncludeUsersWithAccessToAllSites(true);
              nullSiteUserList.buildList(db);
              //Change is diassallowed if other null site users do not exist
              //as this change has the potential to lock out an administrator
              //from a lot of site related data.
              if (nullSiteUserList.size() == 0) {
                isValid = false;
                newUser.getErrors().put(
                    "siteIdError", systemStatus.getLabel(
                    "object.validation.noOtherUserWithAccessToAllSitesExist.text"));
              }
            }
            // Find whether the user has associations (account ownership,
            // contact ownership, tickets, etc)
            if (!contactForUser.getEmployee() || contactForUser.getOrgId() > 0) {
              isValid = false;
              newUser.getErrors().put("siteIdError", systemStatus.getLabel("object.validation.siteChangeNotAllowed"));
            }
            isValid = this.validateObject(context, db, newUser) && isValid;
            if (isValid) {
              siteChangeAllowed = true;
            }
          } else {
            isValid = true;
            siteChangeAllowed = true;
            if (!contactForUser.getEmployee()) {
              isValid = false;
              newUser.getErrors().put("siteIdError", systemStatus.getLabel("object.validation.siteChangeNotAllowed"));
              isValid = this.validateObject(context, db, newUser) && isValid;
            }
          }
        } else {
          //throw error -- this is not allowed
          isValid = false;
          newUser.getErrors().put(
              "actionError", systemStatus.getLabel(
              "object.validation.siteChangeNotAllowed"));
        }
      }

      // checking whether all users reporting to the 'new user'
      // belong to the same site as the new user

      // The following lines of code have been commented as this check is
      // performed in the object validator. Also, once site change is allowed
      // the user can change to the new site and to start with, the user
      // would not have any other users reporting to him in the new site
      /*
       *  if ((newUser.getSiteId() != -1) && isValid){
       *  UserList reportingUserList = new UserList();
       *  reportingUserList.setManagerId(newUser.getId());
       *  reportingUserList.buildList(db);
       *  Iterator userItr = reportingUserList.iterator();
       *  while (userItr.hasNext()) {
       *  User subUser = (User) userItr.next();
       *  if ((subUser.getSiteId() == -1) ||
       *  (subUser.getSiteId() != newUser.getSiteId())) {
       *  newUser.getErrors().put(
       *  "actionError", systemStatus.getLabel(
       *  "object.validation.genericActionError"));
       *  newUser.getErrors().put(
       *  "siteIdError", systemStatus.getLabel(
       *  "admin.subUserNotTheSameSite.text"));
       *  isValid = false;
       *  break;
       *  }
       *  }
       *  }
       */
      if (isValid) {
        resultCount = newUser.update(db, context);
      }
      if (resultCount == -1) {
        processErrors(context, newUser.getErrors());
        //Prepare the form again
        newUser.setBuildContact(true);
        newUser.buildResources(db);
        context.getRequest().setAttribute("UserRecord", newUser);
        //Prepare the user list
        UserList userList = new UserList();
        userList.setEmptyHtmlSelectRecord(
            this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
        userList.setBuildContact(false);
        userList.setBuildContactDetails(false);
        userList.setExcludeDisabledIfUnselected(true);
        userList.setExcludeExpiredIfUnselected(true);
        userList.setRoleType(Constants.ROLETYPE_REGULAR);
        //fetch users from the specified site id and users who have
        //access to all sites. A user assigned to as site may report to
        //another user who has access to the same site or to one
        //who has accesss to all sites
        userList.setSiteId(newUser.getSiteId());
        userList.setIncludeUsersWithAccessToAllSites(true);
        userList.buildList(db);
        context.getRequest().setAttribute("UserList", userList);
        //Prepare the role list
        RoleList roleList = new RoleList();
        roleList.setBuildUsers(false);
        roleList.setEmptyHtmlSelectRecord(
            this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
        roleList.setEnabledState(Constants.TRUE);
        roleList.setRoleType(Constants.ROLETYPE_REGULAR);
        roleList.buildList(db);
        context.getRequest().setAttribute("RoleList", roleList);

        LookupList siteid = new LookupList(db, "lookup_site_id");
        siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
        context.getRequest().setAttribute("SiteIdList", siteid);
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("generatePass") != null) {
          String password = newUser.generateRandomPassword(db, context);
          newUser.setBuildContact(true);
          newUser.setBuildContactDetails(true);
          newUser.buildRecord(db, newUser.getId());
          String templateFile = getDbNamePath(context) + "templates_" + getUserLanguage(context) + ".xml";
          if (!FileUtils.fileExists(templateFile)) {
            templateFile = getDbNamePath(context) + "templates_en_US.xml";
          }
          User modifiedByUser = new User();
          modifiedByUser.setBuildContact(true);
          modifiedByUser.setBuildContactDetails(true);
          modifiedByUser.buildRecord(db, this.getUserId(context));
          sendEmail(context, newUser, modifiedByUser, templateFile, password);
        }
        // Change site information of the contact record of site information
        // of the user was changed.
        if (siteChangeAllowed) {
          Contact linkContact = new Contact(db, newUser.getContactId());
          linkContact.setSiteId(newUser.getSiteId());
          linkContact.update(db);
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
      if (isValid) {
        processErrors(context, newUser.getErrors());
      }
      return ("UserModifyOK");
    } else if (resultCount == 1) {
      context.getRequest().setAttribute(
          "id", context.getRequest().getParameter("id"));
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandListUsers(context));
      } else {
        return ("UserUpdateOK");
      }
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   *  fetches the user list based on site id
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.12
   */
  public String executeCommandReportsToJSList(ActionContext context) {

    String siteIdString = context.getRequest().getParameter("siteId");
    int siteId = -1;
    if ((siteIdString != null) || !"".equals(siteIdString)) {
      siteId = Integer.parseInt(siteIdString);
    }
    if (!isSiteAccessPermitted(context, String.valueOf(siteId))) {
      return ("PermissionError");
    }
    /*
     *  User user = this.getUser(context, this.getUserId(context));
     *  if (user.getSiteId() != -1 && user.getSiteId() != siteId) {
     *  return ("PermissionError");
     *  }
     */
    Connection db = null;
    try {
      db = this.getConnection(context);

      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      userList.setBuildContact(false);
      userList.setBuildContactDetails(false);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);
      userList.setRoleType(Constants.ROLETYPE_REGULAR);

      //fetch users from the specified site id and users who have
      //access to all sites. A user assigned to as site may report to
      //another user who has access to the same site or to one
      //who has accesss to all sites
      userList.setSiteId(siteId);
      userList.setIncludeUsersWithAccessToAllSites(true);
      userList.buildList(db);
      HtmlSelect userListSelect = userList.getHtmlSelectObj("userId", getUserId(context));
      context.getRequest().setAttribute("UserListSelect", userListSelect);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("SiteJSUserListOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandProcessBatch(ActionContext context) {
    if (!hasPermission(context, "admin-users-edit")) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      String batchName = context.getRequest().getParameter("batchId");
      String batchSize = context.getRequest().getParameter("batchSize");
      String action = context.getRequest().getParameter("action");
      String status = context.getRequest().getParameter("status");

      ArrayList selection = new ArrayList();

      for (int rowCount = 1; rowCount <= Integer.parseInt(batchSize); ++rowCount) {
        if (context.getRequest().getParameter(batchName + rowCount) != null) {
          String id = context.getRequest().
              getParameter(batchName + rowCount);

          if (!selection.contains(String.valueOf(id))) {
            selection.add(String.valueOf(id));
          }
          //Additional hidden parameters can be passed using the batch input handler
          //Process them based on requirement.
        }
      }

      for (int i = 0; i < selection.size(); i++) {
        int id = Integer.parseInt(
            (String) selection.get(i));
        User thisUser = new User(db, id);
        if (action != null && status != null) {
          if ("webdav".equals(action.toLowerCase())) {
            thisUser.updateWebdavAccess(
                db, "enable".equals(status.toLowerCase()));
          } else if ("httpapi".equals(action.toLowerCase())) {
            thisUser.updateHttpApiAccess(
                db, "enable".equals(status.toLowerCase()));
          } else if ("userlogin".equals(action.toLowerCase())) {
            //TODO: Implement
          }
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandListUsers(context));
  }


  /**
   *  Description of the Method
   *
   * @param  context         Description of the Parameter
   * @param  thisUser        Description of the Parameter
   * @param  modifiedByUser  Description of the Parameter
   * @param  template        Description of the Parameter
   * @param  password        Description of the Parameter
   * @return                 Description of the Return Value
   * @exception  Exception   Description of the Exception
   */
  public boolean sendEmail(ActionContext context, User thisUser, User modifiedByUser, String template, String password) throws Exception {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
    SystemStatus systemStatus = this.getSystemStatus(context);
    UserEmail userEmail = new UserEmail(context, thisUser, modifiedByUser.getContact().getNameLastFirst(), password, systemStatus.getUrl(), template);
    // Prepare the email
    SMTPMessage mail = new SMTPMessage();
    mail.setHost(prefs.get("MAILSERVER"));
    mail.setFrom(prefs.get("EMAILADDRESS"));
    mail.addReplyTo(prefs.get("EMAILADDRESS"));
    mail.setType("text/html");
    mail.setSubject(userEmail.getSubject());
    mail.setBody(userEmail.getBody());
    if (thisUser.getContact().getPrimaryEmailAddress() != null && !"".equals(thisUser.getContact().getPrimaryEmailAddress())) {
      mail.addTo(thisUser.getContact().getPrimaryEmailAddress());
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ADDING: " + thisUser.getContact().getPrimaryEmailAddress());
      }
    }
    int result = mail.send();
    if (result == 2) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Users-> Send error: " + mail.getErrorMsg() + "\n");
      }
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Users-> Sending message...");
      }
    }
    return true;
  }
}

