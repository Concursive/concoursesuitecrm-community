/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionList;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 * Action class for managing Roles
 *
 * @author mrajkowski
 * @version $Id$
 * @created September 19, 2001
 */
public final class Roles extends CFSModule {

  /**
   * Default action, calls list roles
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandListRoles(context);
  }


  /**
   * Action to list roles
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandListRoles(ActionContext context) {
    if (!hasPermission(context, "admin-roles-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    //Setup the pagedList
    PagedListInfo roleInfo = this.getPagedListInfo(context, "RoleListInfo");
    roleInfo.setLink("Roles.do?command=ListRoles");
    RoleList roleList = new RoleList();
    try {
      db = this.getConnection(context);
      //Prepare the list of roles
      roleList.setPagedListInfo(roleInfo);
      roleList.setBuildUsers(false);
      roleList.setBuildUserCount(true);
      roleList.setRoleType(Constants.ROLETYPE_REGULAR);
      roleList.setEnabledState(Constants.TRUE);
      roleList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Roles", "Role List");
    if (errorMessage == null) {
      context.getRequest().setAttribute("RoleList", roleList);
      return ("ListRolesOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Action to view role details
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRoleDetails(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String roleId = context.getRequest().getParameter("id");
    String action = context.getRequest().getParameter("action");

    Connection db = null;

    try {
      db = this.getConnection(context);

      PermissionList permissionList = new PermissionList(db);
      context.getRequest().setAttribute("PermissionList", permissionList);

      Role thisRole = new Role(db, Integer.parseInt(roleId));
      context.getRequest().setAttribute("Role", thisRole);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (action != null && action.equals("modify")) {
        addModuleBean(context, "Roles", "Modify Role Details");
        return ("RoleDetailsModifyOK");
      } else {
        addModuleBean(context, "Roles", "View Role Details");
        return ("RoleDetailsOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Action to process updating a role based on html form
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateRole(ActionContext context) {
    if (!(hasPermission(context, "admin-roles-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    boolean isValid = false;
    Role thisRole = (Role) context.getFormBean();
    thisRole.setRequestItems(context.getRequest());
    thisRole.setModifiedBy(getUserId(context));
    thisRole.setRoleType(0);
    try {
      db = this.getConnection(context);
      isValid = this.validateObject(context, db, thisRole);
      if (isValid) {
        resultCount = thisRole.update(db);
      }
      if (resultCount == 1) {
        updateSystemPermissionCheck(db, context);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Roles", "Update Role");
    if (resultCount == -1 || !isValid) {
      return executeCommandRoleDetails(context);
    } else if (resultCount == 1) {
      return ("RoleUpdateOK");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Action to generate delete dialog box for user
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Role thisRole = null;
    HtmlDialog htmlDialog = new HtmlDialog();

    int id = -1;
    if (!(hasPermission(context, "admin-roles-delete"))) {
      return ("DefaultError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = Integer.parseInt(context.getRequest().getParameter("id"));
    }
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisRole = new Role(db, id);
      DependencyList dependencies = thisRole.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl(
            "javascript:window.location.href='Roles.do?command=DeleteRole&id=" + id + "'");
      } else {
        htmlDialog.setHeader(
            systemStatus.getLabel("confirmdelete.roleUserHeader"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
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
   * Action to generate a role insert form
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsertRoleForm(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "Roles", "Insert a Role");
    Connection db = null;
    try {
      db = this.getConnection(context);
      PermissionList thisPermissionList = new PermissionList(db);
      context.getRequest().setAttribute("PermissionList", thisPermissionList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("RoleInsertFormOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Action to insert a role, based on html form entered by user
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsertRole(ActionContext context) {
    if (!(hasPermission(context, "admin-roles-add"))) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    boolean isValid = false;

    Role thisRole = (Role) context.getFormBean();
    thisRole.setRequestItems(context.getRequest());
    thisRole.setEnteredBy(getUserId(context));
    thisRole.setModifiedBy(getUserId(context));
    thisRole.setRoleType(0);

    Connection db = null;
    try {
      db = this.getConnection(context);
      isValid = this.validateObject(context, db, thisRole);
      if (isValid) {
        recordInserted = thisRole.insert(db);
      }
      if (recordInserted) {
        PermissionList permissionList = new PermissionList(db);
        context.getRequest().setAttribute("PermissionList", permissionList);
        thisRole = new Role(db, thisRole.getId());
        context.getRequest().setAttribute("Role", thisRole);
        updateSystemPermissionCheck(db, context);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Roles", "Insert a Role");
    if (recordInserted) {
      return ("RoleAddOK");
    }
    return (executeCommandInsertRoleForm(context));
  }


  /**
   * Action to delete a specific role
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteRole(ActionContext context) {
    if (!(hasPermission(context, "admin-roles-delete"))) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    Role thisRole = null;
    String roleId = context.getRequest().getParameter("id");

    Connection db = null;
    try {
      if (Integer.parseInt(roleId) == 1) {
        throw new Exception("Administrator Role cannot be deleted.");
      }
      db = this.getConnection(context);
      thisRole = new Role(db, Integer.parseInt(roleId));
      boolean usersPresent = thisRole.buildUserCount(db, true);
      SystemStatus systemStatus = this.getSystemStatus(context);
      if (usersPresent) {
        thisRole.getErrors().put(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.activeUsersAssignedToRole"));
      } else {
        recordDeleted = thisRole.delete(db);
        if (!recordDeleted) {
          thisRole.getErrors().put(
              "actionError", systemStatus.getLabel(
                  "object.validation.actionError.roleDeletion"));
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Roles", "Delete a Role");
    if (recordDeleted) {
      context.getRequest().setAttribute(
          "refreshUrl", "Roles.do?command=ListRoles");
      return ("DeleteOK");
    } else {
      processErrors(context, thisRole.getErrors());
      context.getRequest().setAttribute(
          "refreshUrl", "Roles.do?command=ListRoles");
      return ("DeleteOK");
    }
  }
}

