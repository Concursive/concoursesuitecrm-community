package org.aspcfs.modules.admin.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Vector;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.base.DependencyList;

/**
 *  Action class for managing Roles
 *
 *@author     mrajkowski
 *@created    September 19, 2001
 *@version    $Id$
 */
public final class Roles extends CFSModule {

  /**
   *  Default action, calls list roles
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandListRoles(context);
  }


  /**
   *  Action to list roles
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListRoles(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo roleInfo = this.getPagedListInfo(context, "RoleListInfo");
    roleInfo.setLink("Roles.do?command=ListRoles");

    Connection db = null;
    RoleList roleList = new RoleList();
    try {
      db = this.getConnection(context);
      roleList.setPagedListInfo(roleInfo);
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
   *  Action to view role details
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
   *  Action to process updating a role based on html form
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateRole(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

    Role thisRole = (Role) context.getFormBean();
    thisRole.setRequestItems(context.getRequest());
    thisRole.setModifiedBy(getUserId(context));

    try {
      db = this.getConnection(context);
      resultCount = thisRole.update(db);
      if (resultCount == 1) {
        updateSystemPermissionCheck(db, context);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Roles", "Update Role");
    if (errorMessage == null) {
      if (resultCount == -1) {
        processErrors(context, thisRole.getErrors());
        return executeCommandRoleDetails(context);
      } else if (resultCount == 1) {
        return ("RoleUpdateOK");
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
   *  Action to generate delete dialog box for user
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      thisRole = new Role(db, id);
      htmlDialog.setTitle("Confirm");
      DependencyList dependencies = thisRole.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='Roles.do?command=DeleteRole&id=" + id + "'");
      } else {
        htmlDialog.setHeader("This role cannot be deleted because at least one user is assigned to it.");
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
   *  Action to generate a role insert form
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
   *  Action to insert a role, based on html form entered by user
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsertRole(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;

    Role thisRole = (Role) context.getFormBean();
    thisRole.setRequestItems(context.getRequest());
    thisRole.setEnteredBy(getUserId(context));
    thisRole.setModifiedBy(getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);
      recordInserted = thisRole.insert(db);
      if (recordInserted) {
        PermissionList permissionList = new PermissionList(db);
        context.getRequest().setAttribute("PermissionList", permissionList);

        thisRole = new Role(db, thisRole.getId());
        context.getRequest().setAttribute("Role", thisRole);

      } else {
        processErrors(context, thisRole.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Roles", "Insert a Role");
    if (errorMessage == null) {
      if (recordInserted) {
        return ("RoleAddOK");
      } else {
        return (executeCommandInsertRoleForm(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action to delete a specific role
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteRole(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
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
      recordDeleted = thisRole.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Roles", "Delete a Role");
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", "Roles.do?command=ListRoles");
        return ("DeleteOK");
      } else {
        processErrors(context, thisRole.getErrors());
        context.getRequest().setAttribute("refreshUrl", "Roles.do?command=ListRoles");
        return ("DeleteOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

