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
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.Viewpoint;
import org.aspcfs.modules.admin.base.ViewpointList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.HashMap;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id$
 * @created February 24, 2003
 */
public final class Viewpoints extends CFSModule {

  /**
   * Default action, calls list Viewpoints
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandListViewpoints(context);
  }


  /**
   * Action to list Viewpoints
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandListViewpoints(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo viewpointInfo = this.getPagedListInfo(
        context, "ViewpointListInfo");
    String userId = context.getRequest().getParameter("userId");
    viewpointInfo.setLink(
        "Viewpoints.do?command=ListViewpoints&userId=" + userId);

    Connection db = null;
    ViewpointList viewpointList = new ViewpointList();
    try {
      db = this.getConnection(context);
      viewpointList.setPagedListInfo(viewpointInfo);
      viewpointList.setUserId(Integer.parseInt(userId));
      viewpointList.setBuildResources(true);
      viewpointList.buildList(db);

      //build User Info for submenu
      User thisUser = new User();
      thisUser.setBuildContact(true);
      thisUser.buildRecord(db, Integer.parseInt(userId));
      context.getRequest().setAttribute("UserRecord", thisUser);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Users", "Viewpoint List");
    if (errorMessage == null) {
      context.getRequest().setAttribute("ViewpointList", viewpointList);
      return ("ListViewpointsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Action to view Viewpoint details
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandViewpointDetails(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String viewpointId = context.getRequest().getParameter("id");
    String action = context.getRequest().getParameter("action");
    String userId = context.getRequest().getParameter("userId");

    Connection db = null;

    try {
      db = this.getConnection(context);

      PermissionList permissionList = new PermissionList();
      permissionList.setViewpointsOnly(true);
      permissionList.buildList(db);
      context.getRequest().setAttribute("PermissionList", permissionList);

      Viewpoint thisViewpoint = new Viewpoint(
          db, Integer.parseInt(viewpointId));
      context.getRequest().setAttribute("Viewpoint", thisViewpoint);

      //build User Info for submenu
      User thisUser = new User();
      thisUser.setBuildContact(true);
      thisUser.buildRecord(db, Integer.parseInt(userId));
      context.getRequest().setAttribute("UserRecord", thisUser);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (action != null && action.equals("modify")) {
        addModuleBean(context, "Users", "Modify Viewpoint Details");
        return ("ViewpointDetailsModifyOK");
      } else {
        addModuleBean(context, "Users", "View Viewpoint Details");
        return ("ViewpointDetailsOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Action to process updating a Viewpoint based on html form
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateViewpoint(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    boolean isValid = false;
    String userId = context.getRequest().getParameter("userId");

    Viewpoint thisViewpoint = (Viewpoint) context.getFormBean();
    thisViewpoint.setRequestItems(context.getRequest());
    thisViewpoint.setModifiedBy(getUserId(context));

    try {
      db = this.getConnection(context);
      isValid = this.validateObject(context, db, thisViewpoint);
      if (isValid) {
        resultCount = thisViewpoint.update(db);
      }
      if (resultCount == 1) {
        this.invalidateViewpoints(context);
      }

      //build User Info for submenu
      User thisUser = new User();
      thisUser.setBuildContact(true);
      thisUser.buildRecord(db, Integer.parseInt(userId));
      context.getRequest().setAttribute("UserRecord", thisUser);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Users", "Update Viewpoint");
    if (resultCount == -1 || !isValid) {
      processErrors(context, thisViewpoint.getErrors());
      return executeCommandViewpointDetails(context);
    } else if (resultCount == 1) {
      return ("ViewpointUpdateOK");
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
    Viewpoint thisViewpoint = null;
    HtmlDialog htmlDialog = new HtmlDialog();

    int id = -1;
    String userId = context.getRequest().getParameter("userId");
    if (!(hasPermission(context, "admin-roles-delete"))) {
      return ("DefaultError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = Integer.parseInt(context.getRequest().getParameter("id"));
    }
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisViewpoint = new Viewpoint(db, id);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.setShowAndConfirm(false);
      htmlDialog.setDeleteUrl(
          "javascript:window.location.href='Viewpoints.do?command=DeleteViewpoint&id=" + id + "&userId=" + userId + "'");
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
   * Action to generate a Viewpoint insert form
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsertViewpointForm(ActionContext context) {
    if (!(hasPermission(context, "admin-roles-add"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Users", "Add a Viewpoint");
    Connection db = null;
    String userId = context.getRequest().getParameter("userId");

    try {
      db = this.getConnection(context);
      PermissionList permissionList = new PermissionList();
      permissionList.setViewpointsOnly(true);
      permissionList.buildList(db);
      context.getRequest().setAttribute("PermissionList", permissionList);

      //build User Info for submenu
      User thisUser = new User();
      thisUser.setBuildContact(true);
      thisUser.buildRecord(db, Integer.parseInt(userId));
      context.getRequest().setAttribute("UserRecord", thisUser);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ViewpointInsertFormOK");
  }


  /**
   * Action to insert a Viewpoint, based on html form entered by user
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsertViewpoint(ActionContext context) {
    if (!(hasPermission(context, "admin-roles-add"))) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    boolean isValid = false;
    String userId = context.getRequest().getParameter("userId");
    Viewpoint thisViewpoint = (Viewpoint) context.getFormBean();
    thisViewpoint.setRequestItems(context.getRequest());
    thisViewpoint.setEnteredBy(getUserId(context));
    thisViewpoint.setModifiedBy(getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);
      isValid = this.validateObject(context, db, thisViewpoint);
      ViewpointList viewpoints = new ViewpointList();
      viewpoints.setUserId(thisViewpoint.getUserId());
      viewpoints.buildList(db);
      
      HashMap errors = new HashMap();
      SystemStatus systemStatus = this.getSystemStatus(context);
      if (isValid) {
        //check that he site ids of the view points are compliant
        User tmpUser = this.getUser(context, thisViewpoint.getUserId());
        User tmpViewPointUser = this.getUser(context, thisViewpoint.getVpUserId());
        if (tmpUser.getSiteId() != -1){
          if (tmpUser.getSiteId() != tmpViewPointUser.getSiteId()){
            errors.put(
                "actionError", systemStatus.getLabel(
                    "object.validation.theSelectedUserHasIncompatibleSite"));
            isValid = false;
          }
        }
      
        //Check for duplicates
        if (viewpoints.checkForDuplicates(thisViewpoint.getVpUserId()) != 0) {
          errors.put(
              "actionError", systemStatus.getLabel(
                  "object.validation.duplicateViewpointUserCanNotBeInserted"));
          isValid = false;
        }
        if (isValid) {
          recordInserted = thisViewpoint.insert(db);
        } else {
          processErrors(context, errors);
        }
      }
      if (recordInserted) {
        PermissionList permissionList = new PermissionList(db);
        context.getRequest().setAttribute("PermissionList", permissionList);

        thisViewpoint = new Viewpoint(db, thisViewpoint.getId());
        context.getRequest().setAttribute("Viewpoint", thisViewpoint);
        
        //mark the viewpoints as invalid
        this.invalidateViewpoints(context);
        
        //build User Info for submenu
        User thisUser = new User();
        thisUser.setBuildContact(true);
        thisUser.buildRecord(db, Integer.parseInt(userId));
        context.getRequest().setAttribute("UserRecord", thisUser);
      } else {
        context.getRequest().setAttribute(
            "vpUserId", "" + thisViewpoint.getVpUserId());
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Users", "Insert a Viewpoint");
    if (recordInserted) {
      return ("ViewpointAddOK");
    } else {
      return (executeCommandInsertViewpointForm(context));
    }
  }


  /**
   * Action to delete a specific Viewpoint
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteViewpoint(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    Viewpoint thisViewpoint = null;
    String viewpointId = context.getRequest().getParameter("id");
    String userId = context.getRequest().getParameter("userId");
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisViewpoint = new Viewpoint(db, Integer.parseInt(viewpointId));
      recordDeleted = thisViewpoint.delete(db);
      if (recordDeleted) {
        //mark the viewpoints as invalid
        this.invalidateViewpoints(context);
      } else {
        thisViewpoint.getErrors().put(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.viewPointDeletion"));
        processErrors(context, thisViewpoint.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Users", "Delete a Viewpoint");
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute(
            "refreshUrl", "Viewpoints.do?command=ListViewpoints&userId=" + userId);
        return ("DeleteOK");
      } else {
        processErrors(context, thisViewpoint.getErrors());
        context.getRequest().setAttribute(
            "refreshUrl", "Viewpoints.do?command=ListViewpoints&userId=" + userId);
        return ("DeleteOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

