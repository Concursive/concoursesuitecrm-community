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

import java.sql.Connection;
import java.sql.SQLException;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionList;
import org.aspcfs.modules.admin.base.PopulatePortalRolePermissionList;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

/**
 * @author Achyutha
 * @version $Id:  $
 * @created June 16, 2006
 */
public class PortalRoleEditor extends CFSModule {
	
  /**
   * Default action, calls list roles
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandList(context);
  }


  /**
   * Action to list roles
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "admin-portalroleeditor-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    //Setup the pagedList
    PagedListInfo roleInfo = this.getPagedListInfo(context, "portalRoleListInfo");
    roleInfo.setLink("PortalRoleEditor.do?command=List");
    RoleList roleList = new RoleList();
    try {
      db = this.getConnection(context);
      //Prepare the list of roles
      roleList.setPagedListInfo(roleInfo);
      roleList.setBuildUsers(false);
      roleList.setBuildUserCount(true);
      roleList.setRoleType(Constants.ROLETYPE_CUSTOMER);
      roleList.setEnabledState(Constants.UNDEFINED);
      roleList.buildList(db);
    } catch (Exception e) {
    	e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Roles", "Role List");
      context.getRequest().setAttribute("roleList", roleList);
      return ("ListOK");
  }

  /**
   * Action to view role details
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {

    if (!(hasPermission(context, "admin-portalroleeditor-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    String roleId = context.getRequest().getParameter("id");
    Connection db = null;

    try {
      db = this.getConnection(context);
      PopulatePortalRolePermissionList portalRoleList = new PopulatePortalRolePermissionList();
      PermissionList permissionList = portalRoleList.populatePortalPermissions(db);
      context.getRequest().setAttribute("permissionList", permissionList);

      Role thisRole = new Role(db, Integer.parseInt(roleId));
      context.getRequest().setAttribute("role", thisRole);

    } catch (Exception e) {
    	e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
      addModuleBean(context, "Roles", "Modify Role Details");
      return ("ModifyOK");
  }


  /**
   * Action to process updating a role based on html form
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "admin-portalroleeditor-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    boolean isValid = false;
    Role thisRole = (Role) context.getFormBean();
    thisRole.setRequestItems(context.getRequest());
    thisRole.setModifiedBy(getUserId(context));
    thisRole.setRoleType(Constants.ROLETYPE_CUSTOMER);
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
      return executeCommandModify(context);
    } else if (resultCount == 1) {
      return ("UpdateOK");
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
    if (!(hasPermission(context, "admin-portalroleeditor-delete"))) {
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
            "javascript:window.location.href='PortalRoleEditor.do?command=Delete&id=" + id + "'");
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
  public String executeCommandAdd(ActionContext context) {

    if (!(hasPermission(context, "admin-portalroleeditor-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "Roles", "Insert a Role");
    Connection db = null;
    try {
      db = this.getConnection(context);
      PopulatePortalRolePermissionList portalRoleList = new PopulatePortalRolePermissionList();
      PermissionList thisPermissionList = portalRoleList.populatePortalPermissions(db);
      context.getRequest().setAttribute("permissionList", thisPermissionList);

    } catch (Exception e) {
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
   * Action to insert a role, based on html form entered by user
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "admin-portalroleeditor-add"))) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    boolean isValid = false;

    Role thisRole = (Role) context.getFormBean();
    thisRole.setRequestItems(context.getRequest());
    thisRole.setEnteredBy(getUserId(context));
    thisRole.setModifiedBy(getUserId(context));
    thisRole.setRoleType(Constants.ROLETYPE_CUSTOMER);

    Connection db = null;
    try {
      db = this.getConnection(context);
      isValid = this.validateObject(context, db, thisRole);
      if (isValid) {
        recordInserted = thisRole.insert(db);
      }
      if (recordInserted) {
        PermissionList permissionList = new PermissionList(db);
        context.getRequest().setAttribute("permissionList", permissionList);
        thisRole = new Role(db, thisRole.getId());
        context.getRequest().setAttribute("role", thisRole);
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
      return ("SaveOK");
    }
    return (executeCommandAdd(context));
  }


  /**
   * Action to delete a specific role
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
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
          "refreshUrl", "PortalRoleEditor.do?command=List");
      return ("DeleteOK");
    } else {
      processErrors(context, thisRole.getErrors());
      context.getRequest().setAttribute(
          "refreshUrl", "PortalRoleEditor.do?command=List");
      return ("DeleteOK");
    }
  }
  /**
   * Action that enables sync client.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */

  public String executeCommandActivate(ActionContext context) {
    if (!(hasPermission(context, "admin-portalroleeditor-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Role thisRole = null;
    try {
      db = getConnection(context);
      User user = this.getUser(context, this.getUserId(context));      
      String roleId = context.getRequest().getParameter("id");      
      int id = -1;
      if (roleId != null) {
        id = Integer.parseInt(roleId);
        thisRole = new Role(db, id);
        thisRole.setEnabled(true);
        thisRole.setModifiedBy(user.getId());
        thisRole.update(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // forward to JSP
    addModuleBean(context, "Roles", "Activate a Role");
    return ("ActivateOK");
  }

  /**
   * Action that enables sync client.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */

  public String executeCommandDeactivate(ActionContext context) {
    if (!(hasPermission(context, "admin-portalroleeditor-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Role thisRole = null;
    try {
      db = getConnection(context);
      User user = this.getUser(context, this.getUserId(context));      
      String roleId = context.getRequest().getParameter("id");      
      int id = -1;
      if (roleId != null) {
        id = Integer.parseInt(roleId);
        thisRole = new Role(db, id);
        thisRole.setEnabled(false);
        thisRole.setModifiedBy(user.getId());
        thisRole.update(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // forward to JSP
    addModuleBean(context, "Roles", "Deactivate a Role");
    return ("DeactivateOK");
  }


  public String executeCommandTrash(ActionContext context) {
  	return ("");
  }
  
	public void buildPortalPermissions(ActionContext context,Connection db) throws SQLException {
		
	}

}
