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
import java.util.Vector;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.base.DependencyList;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    February 24, 2003
 *@version    $Id$
 */
public final class Viewpoints extends CFSModule {

  /**
   *  Default action, calls list Viewpoints
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandListViewpoints(context);
  }


  /**
   *  Action to list Viewpoints
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListViewpoints(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo viewpointInfo = this.getPagedListInfo(context, "ViewpointListInfo");
    String userId = context.getRequest().getParameter("userId");
    viewpointInfo.setLink("Viewpoints.do?command=ListViewpoints&userId=" + userId);

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
   *  Action to view Viewpoint details
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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

      Viewpoint thisViewpoint = new Viewpoint(db, Integer.parseInt(viewpointId));
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
   *  Action to process updating a Viewpoint based on html form
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandUpdateViewpoint(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    String userId = context.getRequest().getParameter("userId");

    Viewpoint thisViewpoint = (Viewpoint) context.getFormBean();
    thisViewpoint.setRequestItems(context.getRequest());
    thisViewpoint.setModifiedBy(getUserId(context));

    try {
      db = this.getConnection(context);
      resultCount = thisViewpoint.update(db);
      if (resultCount == 1) {
        this.invalidateViewpoints(context);
      }

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

    addModuleBean(context, "Users", "Update Viewpoint");
    if (errorMessage == null) {
      if (resultCount == -1) {
        processErrors(context, thisViewpoint.getErrors());
        return executeCommandViewpointDetails(context);
      } else if (resultCount == 1) {
        return ("ViewpointUpdateOK");
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
      thisViewpoint = new Viewpoint(db, id);
      htmlDialog.setTitle("Confirm");
      htmlDialog.setShowAndConfirm(false);
      htmlDialog.setDeleteUrl("javascript:window.location.href='Viewpoints.do?command=DeleteViewpoint&id=" + id + "&userId=" + userId + "'");
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
   *  Action to generate a Viewpoint insert form
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsertViewpointForm(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
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
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("ViewpointInsertFormOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action to insert a Viewpoint, based on html form entered by user
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsertViewpoint(ActionContext context) {

    if (!(hasPermission(context, "admin-roles-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;

    String userId = context.getRequest().getParameter("userId");
    Viewpoint thisViewpoint = (Viewpoint) context.getFormBean();
    thisViewpoint.setRequestItems(context.getRequest());
    thisViewpoint.setEnteredBy(getUserId(context));
    thisViewpoint.setModifiedBy(getUserId(context));

    Connection db = null;
    try {
      db = this.getConnection(context);
      recordInserted = thisViewpoint.insert(db);
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
        processErrors(context, thisViewpoint.getErrors());

      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Users", "Insert a Viewpoint");
    if (errorMessage == null) {
      if (recordInserted) {
        return ("ViewpointAddOK");
      } else {
        return (executeCommandInsertViewpointForm(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action to delete a specific Viewpoint
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisViewpoint = new Viewpoint(db, Integer.parseInt(viewpointId));
      recordDeleted = thisViewpoint.delete(db);
      if(recordDeleted){
        //mark the viewpoints as invalid
        this.invalidateViewpoints(context);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Users", "Delete a Viewpoint");
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("refreshUrl", "Viewpoints.do?command=ListViewpoints&userId=" + userId);
        return ("DeleteOK");
      } else {
        processErrors(context, thisViewpoint.getErrors());
        context.getRequest().setAttribute("refreshUrl", "Viewpoints.do?command=ListViewpoints&userId=" + userId);
        return ("DeleteOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

