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
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import java.text.*;

/**
 *  Actions for managing the Category Editors.
 *
 *@author     akhi_m
 *@created    May 23, 2003
 *@version    $id: exp$
 */
public final class AdminCategories extends CFSModule {

  /**
   *  Action to show a list of objects that support category editors for the
   *  specified module
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandShow(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "View");
    String moduleId = context.getRequest().getParameter("moduleId");
    Connection db = null;
    try {
      db = getConnection(context);
      // Build the module details
      PermissionCategory permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
      // Build a list of categories that can be edited in this module
      CategoryEditorList editorList = new CategoryEditorList();
      editorList.setModuleId(moduleId);
      editorList.buildList(db);
      context.getRequest().setAttribute("editorList", editorList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "ShowOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "View");
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      context.getRequest().setAttribute("categoryEditor", thisEditor);
      //build the module details
      PermissionCategory permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
      //create lists for selected categories
      HashMap tmpList = null;
      if (context.getSession().getAttribute("selectedCategories" + constantId) != null || 
          "true".equals(context.getRequest().getParameter("reset"))) {
        tmpList = (HashMap) context.getSession().getAttribute("selectedCategories" + constantId);
        HashMap categories = thisEditor.getCategoryList();
        for (int k = 1; k < thisEditor.getMaxLevels(); k++) {
          TicketCategoryDraftList subList = null;
          if (tmpList.get(new Integer(k - 1)) != null) {
            TicketCategoryDraft tmpCat = (TicketCategoryDraft) categories.get((Integer) (tmpList.get(new Integer(k - 1))));
            subList = tmpCat.getShortChildList();
          } else {
            subList = new TicketCategoryDraftList();
          }
          subList.setHtmlJsEvent("onChange=\"javascript:loadCategories('" + k + "');\"");
          context.getRequest().setAttribute("SubList" + k, subList);
        }
      } else {
        tmpList = new HashMap();
        context.getSession().setAttribute("selectedCategories" + constantId, tmpList);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "View");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Modify Categories");
    String categoryId = context.getRequest().getParameter("categoryId");
    TicketCategoryDraftList catList = new TicketCategoryDraftList();
    Connection db = null;
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      if (!"-1".equals(categoryId)) {
        TicketCategoryDraft thisCategory = (TicketCategoryDraft) thisEditor.getCategory(Integer.parseInt(categoryId));
        catList = thisCategory.getShortChildList();
      } else {
        catList = thisEditor.getTopCategoryList();
      }
      context.getRequest().setAttribute("categoryList", catList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Modify");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandCategoryJSList(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    String categoryId = context.getRequest().getParameter("categoryId");
    String level = context.getRequest().getParameter("level");
    Connection db = null;
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //get the category editor from system status (for max size)
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      context.getRequest().setAttribute("categoryEditor", thisEditor);
      //load the list
      TicketCategoryDraftList catList = null;
      if (Integer.parseInt(categoryId) > 0) {
        TicketCategoryDraft thisCategory = thisEditor.getCategory(Integer.parseInt(categoryId));
        catList = thisCategory.getShortChildList();
        context.getRequest().setAttribute("ParentCategory", thisCategory);
      } else {
        catList = thisEditor.getTopCategoryList();
      }
      context.getRequest().setAttribute("categoryList", catList);
      //update the selected categories
      context.getSession().removeAttribute("selectedCategories");
      if (Integer.parseInt(categoryId) != -1) {
        HashMap tmpList = thisEditor.getHierarchyAsList(Integer.parseInt(categoryId));
        context.getSession().setAttribute("selectedCategories", tmpList);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "CategoryJSList");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Save Categories");
    String categories = context.getRequest().getParameter("categories");
    String parentCatId = context.getRequest().getParameter("parentCode");
    
    Connection db = null;
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      // get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      // save the item
      thisEditor.updateCategory(db, categories, Integer.parseInt(parentCatId));
      TicketCategoryDraftList catList = null;
      if (!"-1".equals(parentCatId)) {
        TicketCategoryDraft thisCategory = thisEditor.getCategory(Integer.parseInt(parentCatId));
        catList = thisCategory.getShortChildList();
      } else {
        catList = thisEditor.getTopCategoryList();
      }
      context.getRequest().setAttribute("categoryList", catList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Save");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmSave(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    HtmlDialog htmlDialog = new HtmlDialog();
    Connection db = null;

    String categories = context.getRequest().getParameter("categories");
    String parentCatId = context.getRequest().getParameter("parentCode");
    String level = context.getRequest().getParameter("level");
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //escape the ' character in the categories parameter
      categories = StringUtils.replacePattern(categories, "'", "\\\\'");
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      DependencyList dependencies = thisEditor.processDependencies(categories);
      htmlDialog.addMessage(dependencies.getHtmlString());
      if (dependencies.size() == 0) {
        htmlDialog.setTitle("Confirm");
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setHeader("Are you sure ?");
        htmlDialog.setDeleteUrl("javascript:window.location.href='AdminCategories.do?command=Save&categories=' + escape('" + categories + "') + '&parentCode=" + parentCatId + "&level=" + level + "'");
      } else {
        if (dependencies.canDelete()) {
          htmlDialog.setTitle("Centric CRM: Confirm Delete");
          htmlDialog.setHeader("The following categories have dependencies within Centric CRM:");
          htmlDialog.addButton("Delete All", "javascript:window.location.href='AdminCategories.do?command=Save&categories=' + escape('" + categories + "') + '&parentCode=" + parentCatId + "&level=" + level + "'");
          htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
        } else {
          htmlDialog.setTitle("Centric CRM: Alert");
          htmlDialog.setHeader("The list cannot be updated since the following categories have dependencies within Centric CRM:");
          htmlDialog.addButton("OK", "javascript:parent.window.close()");
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return "ConfirmSaveOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveDraft(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Save Draft");
    Connection db = null;
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      //thisEditor.saveDraft(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "SaveDraft");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandReset(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Reset List");
    Connection db = null;
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      thisEditor.reset(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().removeAttribute("selectedCategories");
    return this.getReturn(context, "Reset");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandActivate(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Activate Categories");
    Connection db = null;
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      thisEditor.activate(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Activate");
  }


  /**
   *  Generates the top level of a list of categories for the specified module
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewActive(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Active Categories");
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      // build the module details
      PermissionCategory permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
      //get the category editor from system status (to determine level count)
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      context.getRequest().setAttribute("categoryEditor", thisEditor);
      // all category editors will put their elements in a common HtmlSelect
      HtmlSelect htmlSelect = new HtmlSelect();
      // build the list of categories needed
      CategoryList categoryList = new CategoryList(thisEditor.getTableName());
      categoryList.setCatLevel(0);
      categoryList.setParentCode(0);
      categoryList.buildList(db);
      categoryList.setIncludeDisabled(true);
      htmlSelect = categoryList.getHtmlSelect(-1);
      if (htmlSelect.size() == 0) {
        htmlSelect.addItem(0, "---------None---------");
      }
      context.getRequest().setAttribute("categoryList", htmlSelect);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "ViewActive");
  }


  /**
   *  Returns a list of categories to dynamically populate an HTML select
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandActiveCatJSList(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    String parentCode = context.getRequest().getParameter("categoryId");
    Connection db = null;
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      HtmlSelect htmlSelect = new HtmlSelect();
      db = getConnection(context);
      //get the category editor from system status (to determine level count)
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      context.getRequest().setAttribute("categoryEditor", thisEditor);
      // all category editors will put their elements in a common HtmlSelect
      CategoryList catList = new CategoryList(thisEditor.getTableName());
      catList.setParentCode(Integer.parseInt(parentCode));
      catList.buildList(db);
      htmlSelect = catList.getHtmlSelect(-1);
      context.getRequest().setAttribute("categoryList", htmlSelect);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "ActiveCategoryJSList");
  }
}

