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
import org.aspcfs.modules.admin.base.CustomListViewEditor;
import org.aspcfs.modules.admin.base.CustomListViewEditorList;
import org.aspcfs.modules.admin.base.CustomListViewList;
import org.aspcfs.modules.admin.base.CustomListView;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.HashMap;
import java.sql.SQLException;
import java.util.StringTokenizer;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    November 9, 2005
 */
public final class AdminCustomListViews extends CFSModule {
  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    Connection db = null;
    try {
      db = getConnection(context);
      // Build the module details
      PermissionCategory permCat = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permCat);
      // Build a list of custom list views that can be edited in this module
      CustomListViewEditorList editorList = new CustomListViewEditorList();
      editorList.setModuleId(moduleId);
      editorList.buildList(db);
      context.getRequest().setAttribute("editorList", editorList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "ListOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandListViews(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Custom List Views");
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      db = getConnection(context);
      //build the module details
      PermissionCategory permCat = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permCat);

      //build the custom list view editor
      String constantId = context.getRequest().getParameter("constantId");
      CustomListViewEditor editor = new CustomListViewEditor(db, Integer.parseInt(constantId));
      context.getRequest().setAttribute("listViewEditor", editor);

      //build a list of custom list views for this editor
      CustomListViewList customList = new CustomListViewList();
      customList.setEditorId(editor.getId());
      customList.buildList(db);
      context.getRequest().setAttribute("customListViews", customList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ListViewsOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-customlistviews-add")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Custom List Views");
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      db = getConnection(context);
      //build the module details
      PermissionCategory permCat = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permCat);

      //build master list of fields available for this view
      SystemStatus thisSystem = this.getSystemStatus(context);
      String constantId = context.getRequest().getParameter("constantId");
      CustomListViewEditor editor =
          thisSystem.getCustomListViewEditor(context, db, Integer.parseInt(constantId));
      context.getRequest().setAttribute("customListViewEditor", editor);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AddOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-customlistviews-add")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Custom List Views");
    Connection db = null;
    CustomListView newCustomView = null; 
    boolean isValid = true;
    boolean recordInserted = false;
    CustomListView customView = (CustomListView) context.getFormBean();
    
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      db = getConnection(context);
      //build the module details
      PermissionCategory permCat = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("permissionCategory", permCat);
      
      isValid = this.validateObject(context, db, customView);
      if (isValid) {
        recordInserted = customView.insert(db);
      }
      
      if (recordInserted) {
        newCustomView = new CustomListView(db, customView.getId());
        context.getRequest().setAttribute("customListView", newCustomView);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted && isValid) {
      return ("InsertOK");
    }
    return (executeCommandAdd(context));
  }
}

