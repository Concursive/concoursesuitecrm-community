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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.CategoryEditorList;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.CategoryList;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.troubletickets.base.TicketCategoryDraft;
import org.aspcfs.modules.troubletickets.base.TicketCategoryDraftAssignment;
import org.aspcfs.modules.troubletickets.base.TicketCategoryDraftAssignmentList;
import org.aspcfs.modules.troubletickets.base.TicketCategoryDraftList;
import org.aspcfs.modules.troubletickets.base.TicketCategoryDraftPlanMapList;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.HashMap;
import java.sql.SQLException;
import java.util.StringTokenizer;

/**
 *  Actions for managing the Category Editors.
 *
 * @author     akhi_m
 * @created    May 23, 2003
 * @version    $id: exp$
 */
public final class AdminCategories extends CFSModule {

  /**
   *  Action to show a list of objects that support category editors for the
   *  specified module
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
      PermissionCategory permCat = new PermissionCategory(
          db, Integer.parseInt(moduleId));
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
    return getReturn(context, "Show");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "View");
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*      if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
        return ("PermissionError");
      }
*/
      context.getRequest().setAttribute("siteId", siteId);
      int constantId = Integer.parseInt(
          context.getRequest().getParameter("constantId"));
      User user = this.getUser(context, this.getUserId(context));
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
      departmentList.setJsEvent("onChange=\"javascript:updateDepartment();\" id=\"departmentId\"");
      context.getRequest().setAttribute("departmentSelect", departmentList);
      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(
          db, constantId);
      if (siteId == null || "".equals(siteId)) {
        siteId = String.valueOf(user.getSiteId());
      }
      thisEditor.setSiteId(siteId);
      thisEditor.build(db);
      context.getRequest().setAttribute("categoryEditor", thisEditor);
      //build the module details
      PermissionCategory permCat = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
      //create lists for selected categories
      HashMap tmpList = null;
      if (context.getSession().getAttribute("selectedCategories" + constantId) != null ||
          "true".equals(context.getRequest().getParameter("reset"))) {
        tmpList = (HashMap) context.getSession().getAttribute(
            "selectedCategories" + constantId);
        HashMap categories = thisEditor.getCategoryList();
        for (int k = 1; k < thisEditor.getMaxLevels(); k++) {
          TicketCategoryDraftList subList = null;
          if (tmpList.get(new Integer(k - 1)) != null) {
            TicketCategoryDraft tmpCat = (TicketCategoryDraft) categories.get(
                (Integer) (tmpList.get(new Integer(k - 1))));
            subList = tmpCat.getShortChildList();
          } else {
            subList = new TicketCategoryDraftList();
          }
          subList.setNoneLabel(systemStatus.getLabel("calendar.none.4dashes"));
          subList.setHtmlJsEvent(
              "onChange=\"javascript:loadCategories('" + k + "');\"");
          context.getRequest().setAttribute("SubList" + k, subList);
        }
      } else {
        tmpList = new HashMap();
        context.getSession().setAttribute(
            "selectedCategories" + constantId, tmpList);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "View");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Modify Categories");
    String categoryId = context.getRequest().getParameter("categoryId");
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    context.getRequest().setAttribute("siteId", siteId);
    TicketCategoryDraftList catList = new TicketCategoryDraftList();
    Connection db = null;
    try {
      int constantId = Integer.parseInt(
          context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(
          db, constantId);
      thisEditor.setSiteId(siteId);
      thisEditor.build(db);
      if (!"-1".equals(categoryId)) {
        TicketCategoryDraft thisCategory = (TicketCategoryDraft) thisEditor.getCategory(
            Integer.parseInt(categoryId));
        catList = thisCategory.getShortChildList();
      } else {
        catList = thisEditor.getTopCategoryList();
      }
      catList.setNoneLabel(systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("categoryList", catList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Modify");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandCategoryJSList(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    String moduleId = context.getRequest().getParameter("moduleId");
    String categoryId = context.getRequest().getParameter("categoryId");
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    context.getRequest().setAttribute("siteId", siteId);
    Connection db = null;
    try {
      int constantId = Integer.parseInt(
          context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      PermissionCategory permCat = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
      //get the category editor from system status (for max size)
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(
          db, constantId);
      thisEditor.setSiteId(siteId);
      thisEditor.build(db);
      context.getRequest().setAttribute("categoryEditor", thisEditor);
      //load the list
      String hierarchialUsers = this.getUserRange(context, this.getUserId(context));
      context.getRequest().setAttribute("hierarchialUsers", hierarchialUsers);
      TicketCategoryDraftList catList = null;
      TicketCategoryDraftAssignment assignment = new TicketCategoryDraftAssignment();
      if (Integer.parseInt(categoryId) > 0) {
        TicketCategoryDraft thisCategory = thisEditor.getCategory(
            Integer.parseInt(categoryId));
        assignment = new TicketCategoryDraftAssignment(db, thisCategory.getId(), (String) null);
        if (assignment.getId() == -1) {
          assignment.setCategoryId(thisCategory.getId());
        }
        catList = thisCategory.getShortChildList();
        context.getRequest().setAttribute("ParentCategory", thisCategory);
        assignment.buildPlanMapList(db);
      } else {
        catList = thisEditor.getTopCategoryList();
      }
      context.getRequest().setAttribute("draftAssignment", assignment);
      context.getRequest().setAttribute("categoryList", catList);
      //update the selected categories
      context.getSession().removeAttribute("selectedCategories");
      if (Integer.parseInt(categoryId) != -1) {
        HashMap tmpList = thisEditor.getHierarchyAsList(
            Integer.parseInt(categoryId));
        context.getSession().setAttribute("selectedCategories", tmpList);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "CategoryJSList");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Save Categories");
    String categories = context.getRequest().getParameter("categories");
    String parentCatId = context.getRequest().getParameter("parentCode");
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    context.getRequest().setAttribute("siteId", siteId);

    Connection db = null;
    try {
      int constantId = Integer.parseInt(
          context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      // get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(
          db, constantId);
      thisEditor.setSiteId(siteId);
      thisEditor.build(db);
      // save the item
      thisEditor.setSystemStatus(this.getSystemStatus(context));
      thisEditor.updateCategory(db, categories, Integer.parseInt(parentCatId));
      TicketCategoryDraftList catList = null;
      if (!"-1".equals(parentCatId)) {
        TicketCategoryDraft thisCategory = thisEditor.getCategory(
            Integer.parseInt(parentCatId));
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
    return getReturn(context, "Save");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    context.getRequest().setAttribute("siteId", siteId);
    try {
      int constantId = Integer.parseInt(
          context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //escape the ' character in the categories parameter
      categories = StringUtils.replacePattern(categories, "'", "\\\\'");
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(
          db, constantId);
      thisEditor.setSiteId(siteId);
      thisEditor.build(db);
      DependencyList dependencies = thisEditor.processDependencies(categories);
      //htmlDialog.addMessage("Please review carefully...\n" + dependencies.getHtmlString());
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.size() == 0) {
        htmlDialog.setTitle(systemStatus.getLabel("admin.confirm.title"));
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setHeader(systemStatus.getLabel("admin.confirm.header"));
        htmlDialog.setDeleteUrl(
            "javascript:window.location.href='AdminCategories.do?command=Save&categories=' + escape('" + categories + "') + '&parentCode=" + parentCatId + "&level=" + level + "'");
      } else {
        if (dependencies.canDelete()) {
          htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
          htmlDialog.setHeader(
              systemStatus.getLabel("admin.confirm.categories.header"));
          htmlDialog.addButton(
              systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='AdminCategories.do?command=Save&categories=' + escape('" + categories + "') + '&parentCode=" + parentCatId + "&level=" + level + "'");
          htmlDialog.addButton(
              systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
        } else {
          htmlDialog.setTitle(systemStatus.getLabel("admin.confirm.alert"));
          htmlDialog.setHeader(
              systemStatus.getLabel("admin.confirm.alert.message"));
          htmlDialog.addButton(
              systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return getReturn(context, "ConfirmSave");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSaveDraft(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Save Draft");
    Connection db = null;
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    context.getRequest().setAttribute("siteId", siteId);
    try {
      int constantId = Integer.parseInt(
          context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      systemStatus.getCategoryEditor(db, constantId);
      //thisEditor.saveDraft(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "SaveDraft");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandReset(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    context.getRequest().setAttribute("siteId", siteId);
    addModuleBean(context, "Configuration", "Reset List");
    User user = this.getUser(context, this.getUserId(context));
    Connection db = null;
    try {
      int constantId = Integer.parseInt(
          context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(
          db, constantId);
      if (siteId == null || "".equals(siteId.trim())) {
        siteId = String.valueOf(user.getSiteId());
      }
      //If the non-site user resets the categories, only non-site categories are reset.
      //Site categories are untouched unless the user resets a site specific categories
      thisEditor.setSiteId(siteId);
      thisEditor.build(db);
      thisEditor.reset(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().removeAttribute("selectedCategories");
    return getReturn(context, "Reset");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandActivate(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    context.getRequest().setAttribute("siteId", siteId);
    addModuleBean(context, "Configuration", "Activate Categories");
    User user = this.getUser(context, this.getUserId(context));
    Connection db = null;
    try {
      int constantId = Integer.parseInt(
          context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(
          db, constantId);
      if (siteId == null || "".equals(siteId.trim())) {
        siteId = String.valueOf(user.getSiteId());
      }
      thisEditor.setSiteId(siteId);
      thisEditor.build(db);
      thisEditor.activate(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Activate");
  }


  /**
   *  Generates the top level of a list of categories for the specified module
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandViewActive(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    context.getRequest().setAttribute("siteId", siteId);
    addModuleBean(context, "Configuration", "Active Categories");
    Connection db = null;
    User user = this.getUser(context, this.getUserId(context));
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      int constantId = Integer.parseInt(
          context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      // build the module details
      PermissionCategory permCat = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
      //get the category editor from system status (to determine level count)
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(
          db, constantId);
      if (siteId == null || "".equals(siteId.trim())) {
        siteId = String.valueOf(user.getSiteId());
      }
      thisEditor.setSiteId(siteId);
      thisEditor.build(db);
      context.getRequest().setAttribute("categoryEditor", thisEditor);
      // all category editors will put their elements in a common HtmlSelect
      HtmlSelect htmlSelect = new HtmlSelect();
      // build the list of categories needed
      CategoryList categoryList = new CategoryList(thisEditor.getTableName());
      categoryList.setCatLevel(0);
      categoryList.setParentCode(0);
      categoryList.setSiteId(siteId);
      categoryList.setExclusiveToSite(true);
      categoryList.buildList(db);
      categoryList.setIncludeDisabled(true);
      htmlSelect = categoryList.getHtmlSelect(-1);
      if (htmlSelect.size() == 0) {
        htmlSelect.addItem(0, systemStatus.getLabel("calendar.none.4dashes","---------None---------"));
      }
      context.getRequest().setAttribute("categoryList", htmlSelect);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ViewActive");
  }


  /**
   *  Returns a list of categories to dynamically populate an HTML select
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandActiveCatJSList(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    String parentCode = context.getRequest().getParameter("categoryId");
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    context.getRequest().setAttribute("siteId", siteId);
    Connection db = null;
    try {
      int constantId = Integer.parseInt(
          context.getRequest().getParameter("constantId"));
      HtmlSelect htmlSelect = new HtmlSelect();
      db = getConnection(context);
      //get the category editor from system status (to determine level count)
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(
          db, constantId);
      if (siteId == null || "".equals(siteId.trim())) {
        siteId = String.valueOf(this.getUser(context, this.getUserId(context)).getSiteId());
      }
      thisEditor.setSiteId(siteId);
      thisEditor.build(db);
      context.getRequest().setAttribute("categoryEditor", thisEditor);
      // all category editors will put their elements in a common HtmlSelect
      CategoryList catList = new CategoryList(thisEditor.getTableName());
      catList.setParentCode(Integer.parseInt(parentCode));
      catList.setSiteId(siteId);
      catList.setExclusiveToSite(true);
      catList.buildList(db);
      htmlSelect = catList.getHtmlSelect(-1);
      context.getRequest().setAttribute("categoryList", htmlSelect);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ActiveCategoryJSList");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddMapping(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-view")) {
      return ("PermissionError");
    }
    CategoryEditor thisEditor = null;
    TicketCategoryDraftPlanMapList planList = new TicketCategoryDraftPlanMapList();
    Connection db = null;
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    context.getRequest().setAttribute("siteId", siteId);
    String categoryId = context.getRequest().getParameter("categoryId");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      context.getRequest().setAttribute("constantId", String.valueOf(constantId));
      db = getConnection(context);
      if (categoryId != null && !"".equals(categoryId) && !"-1".equals(categoryId)) {
        context.getRequest().setAttribute("categoryId", categoryId);
      }
      //get the category editor from system status
      thisEditor = systemStatus.getCategoryEditor(db, PermissionCategory.MULTIPLE_CATEGORY_ACTIONPLAN);
      context.getRequest().setAttribute("categoryEditor", thisEditor);
      //create lists for selected categories
      planList.setCategoryId(categoryId);
      planList.setBuildPlan(true);
      planList.buildList(db);
      context.getRequest().setAttribute("categoryPlanMapList", planList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "AddMapping");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPopupSelector(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-view")) {
      return ("PermissionError");
    }
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    String selectedSiteId = context.getRequest().getParameter("selectedSiteId");
    if (selectedSiteId == null || "".equals(selectedSiteId.trim())) {
      selectedSiteId = siteId;
    }
    CategoryEditor thisCatEditor = null;
    CategoryEditor thisEditor = null;
    User user = this.getUser(context, this.getUserId(context));
    SystemStatus systemStatus = this.getSystemStatus(context);
    String categoryId = context.getRequest().getParameter("categoryId");
    context.getRequest().setAttribute("categoryId", categoryId);
    String constantId = context.getRequest().getParameter("constantId");
    context.getRequest().setAttribute("constantId", constantId);
    TicketCategoryDraftPlanMapList planList = new TicketCategoryDraftPlanMapList();
    Connection db = null;
    ActionPlanList selectList = new ActionPlanList();
    if (siteId == null || "".equals(siteId.trim())) {
      siteId = String.valueOf(user.getSiteId());
    }
    boolean listDone = false;
    PagedListInfo actionPlanSelectorInfo = this.getPagedListInfo(context, "actionPlanSelectorInfo");
    actionPlanSelectorInfo.setLink("AdminCategories.do?command=PopupSelector&categoryId=" + categoryId + "&constantId=" + constantId+"&selectedSiteId="+(selectedSiteId != null?selectedSiteId:""));
    HashMap selectedList = new HashMap();
    try {
      HashMap finalElementList = (HashMap) context.getSession().getAttribute("finalElements");
      if (context.getRequest().getParameter("previousSelection") != null) {
        int j = 0;
        StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("previousSelection"), "|");
        while (st.hasMoreTokens()) {
          String str = st.nextToken();
          selectedList.put(new Integer(str), str);
          j++;
        }
      } else {
        //get selected list from the session
        selectedList = (HashMap) context.getSession().getAttribute("selectedElements");
      }
      //Flush the selectedList if its a new selection
      if (context.getRequest().getParameter("flushtemplist") != null) {
        if (((String) context.getRequest().getParameter("flushtemplist")).equalsIgnoreCase("true")) {
          if (context.getSession().getAttribute("finalElements") != null && context.getRequest().getParameter("previousSelection") == null) {
            selectedList = (HashMap) ((HashMap) context.getSession().getAttribute("finalElements")).clone();
          }
        }
      }
      int rowCount = 1;
      while (context.getRequest().getParameter("hiddenelementid" + rowCount) != null) {
        int elementId = 0;
        String elementValue = "";
        elementId = Integer.parseInt(
            context.getRequest().getParameter("hiddenelementid" + rowCount));
        if (context.getRequest().getParameter("checkelement" + rowCount) != null) {
          if (selectedList.get(new Integer(elementId)) == null) {
            selectedList.put(new Integer(elementId), String.valueOf(elementId));
          } else {
            selectedList.remove(new Integer(elementId));
            selectedList.put(new Integer(elementId), String.valueOf(elementId));
          }
        } else {
          selectedList.remove(new Integer(elementId));
        }
        rowCount++;
      }
      context.getSession().setAttribute("selectedElements", selectedList);
      db = this.getConnection(context);
      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);
      if (categoryId != null && !"".equals(categoryId.trim())) {
        //get the category editor from system status
        thisCatEditor = systemStatus.getCategoryEditor(db, Integer.parseInt(constantId));
        context.getRequest().setAttribute("thisCatEditor", thisCatEditor);
        TicketCategoryDraft thisTicketCategory = (TicketCategoryDraft) thisCatEditor.getCategory(Integer.parseInt(categoryId));
        context.getRequest().setAttribute("ticketCategoryDraft", thisTicketCategory);
      }
      if (context.getRequest().getParameter("finalsubmit") != null) {
        if (((String) context.getRequest().getParameter("finalsubmit")).equalsIgnoreCase("true")) {
          context.getSession().removeAttribute("selectedElements");
          context.getSession().removeAttribute("finalElements");
          return saveActionPlans(context, db, selectedList, categoryId, constantId);
        }
      }
      selectList.setPagedListInfo(actionPlanSelectorInfo);
      actionPlanSelectorInfo.setSearchCriteria(selectList, context);
      selectList.setLinkObjectId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
      if (selectedSiteId != null && !"".equals(selectedSiteId)) {
        selectList.setSiteId(selectedSiteId);
      } else if (actionPlanSelectorInfo.getSearchOptionValue("searchcodeSiteId") != null) {
        selectedSiteId = actionPlanSelectorInfo.getSearchOptionValue("searchcodeSiteId");
      }
      selectList.setExclusiveToSite(true);
      context.getRequest().setAttribute("selectedSiteId", selectedSiteId);

      if (actionPlanSelectorInfo.getSearchOptionValue("searchcodeCatCode") == null || "".equals(actionPlanSelectorInfo.getSearchOptionValue("searchcodeCatCode")) || "0".equals(actionPlanSelectorInfo.getSearchOptionValue("searchcodeCatCode"))) {
        selectList.setCatCode(0);
        selectList.setSubCat1(0);
        selectList.setSubCat2(0);
        selectList.setSubCat3(0);
      }
      String refresh = context.getParameter("refresh");
      ActionPlanCategoryList categoryList = new ActionPlanCategoryList();
      categoryList.setCatLevel(0);
      categoryList.setParentCode(0);
      categoryList.setSiteId(selectedSiteId);
      categoryList.setExclusiveToSite(true);
      categoryList.setHtmlJsEvent("onChange=\"javascript:updateSubList1();\"");
      categoryList.buildList(db);
      categoryList.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("CategoryList", categoryList);

      ActionPlanCategoryList subList1 = new ActionPlanCategoryList();
      subList1.setCatLevel(1);
      subList1.setParentCode(selectList.getCatCode());
      subList1.setSiteId(selectedSiteId);
      subList1.setExclusiveToSite(true);
      subList1.setHtmlJsEvent("onChange=\"javascript:updateSubList2();\"");
      subList1.buildList(db);
      subList1.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("SubList1", subList1);
      if (subList1.size() <= 1) {
        selectList.setSubCat1(0);
      }

      ActionPlanCategoryList subList2 = new ActionPlanCategoryList();
      subList2.setCatLevel(2);
      subList2.setSiteId(selectedSiteId);
      subList2.setExclusiveToSite(true);
      subList2.setParentCode(selectList.getSubCat1());
      subList2.getCatListSelect().setDefaultKey(selectList.getSubCat2());
      subList2.setHtmlJsEvent("onChange=\"javascript:updateSubList3();\"");
      subList2.buildList(db);
      subList2.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("SubList2", subList2);
      if (subList2.size() <= 1) {
        selectList.setSubCat2(0);
      }

      ActionPlanCategoryList subList3 = new ActionPlanCategoryList();
      subList3.setCatLevel(3);
      subList3.setParentCode(selectList.getSubCat2());
      subList3.setSiteId(selectedSiteId);
      subList3.setExclusiveToSite(true);
      subList3.getCatListSelect().setDefaultKey(selectList.getSubCat3());
      subList3.setHtmlJsEvent("onChange=\"javascript:updateSubList4();\"");
      subList3.buildList(db);
      subList3.getCatListSelect().addItem(0, systemStatus.getLabel("accounts.assets.category.undetermined", "Undetermined"));
      context.getRequest().setAttribute("SubList3", subList3);
      selectList.buildList(db);

      planList.setCategoryId(categoryId);
      planList.setBuildPlan(true);
      planList.buildList(db);
      context.getRequest().setAttribute("mappedActionPlanList", planList);
      context.getRequest().setAttribute("siteId", siteId);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      context.getRequest().setAttribute("baseList", selectList);
      this.freeConnection(context, db);
    }
    return getReturn(context, "AddPlanMap");
  }


  /**
   *  Description of the Method
   *
   * @param  context           Description of the Parameter
   * @param  db                Description of the Parameter
   * @param  finalSelections   Description of the Parameter
   * @param  categoryId        Description of the Parameter
   * @param  constantId        Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public String saveActionPlans(ActionContext context, Connection db, HashMap finalSelections, String categoryId, String constantId) throws SQLException {
    try {
      TicketCategoryDraftPlanMapList mapList = new TicketCategoryDraftPlanMapList();
      mapList.setCategoryId(categoryId);
      mapList.buildList(db);
      mapList.parsePlans(db, finalSelections);
      
      TicketCategoryDraftPlanMapList planMapList = new TicketCategoryDraftPlanMapList();
      planMapList.setBuildPlan(true);
      planMapList.setCategoryId(categoryId);
      planMapList.buildList(db);
      context.getRequest().setAttribute("planMapList", planMapList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    }
    return "SavePlanMapPopupOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSaveMapping(ActionContext context) {
    if (!hasPermission(context, "admin-actionplans-view")) {
      return ("PermissionError");
    }
    String categoryId = context.getRequest().getParameter("selectedCategoryId");
    String planId = context.getRequest().getParameter("planId");
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    context.getRequest().setAttribute("siteId", siteId);
    TicketCategoryDraftList catList = new TicketCategoryDraftList();
    Connection db = null;
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      //get the category editor from system status (to determine level count)
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      thisEditor.setSiteId(siteId);
      thisEditor.build(db);
      context.getRequest().setAttribute("categoryEditor", thisEditor);
      //TODO
      // Save the action plan against the ticket category
      if (!"-1".equals(categoryId)) {
        TicketCategoryDraft thisCategory = (TicketCategoryDraft) thisEditor.getCategory(Integer.parseInt(categoryId));
        thisCategory.insertPlan(db, Integer.parseInt(planId), thisEditor.getTableName());
        context.getRequest().setAttribute("category", thisCategory);
      } else {
        return "PermissionError";
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "SavePlanMap");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandUpdateAssignment(ActionContext context) {
    String moduleId = context.getRequest().getParameter("moduleId");
    String categoryId = context.getRequest().getParameter("categoryId");
    String userGroupId = context.getRequest().getParameter("userGroupId");
    String assignedTo = context.getRequest().getParameter("assignedTo");
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    boolean isValid = false;
    boolean recordInserted = false;
    int recordCount = -1;
    context.getRequest().setAttribute("siteId", siteId);
    String departmentId = context.getRequest().getParameter("departmentId");
    TicketCategoryDraft thisCategory = null;
    Connection db = null;
    try {
      int constantId = Integer.parseInt(context.getRequest().getParameter("constantId"));
      db = getConnection(context);
      PermissionCategory permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
      //get the category editor from system status (to determine level count)
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db, constantId);
      thisEditor.setSiteId(siteId);
      thisEditor.build(db);
      // Save the action plan against the ticket category
      if (categoryId != null && !"-1".equals(categoryId)) {
        thisCategory = (TicketCategoryDraft) thisEditor.getCategory(Integer.parseInt(categoryId));
        // load and insert the assignment
        TicketCategoryDraftAssignment assignment = new TicketCategoryDraftAssignment(db, thisCategory.getId(), (String) null);
        if (userGroupId != null && !"".equals(userGroupId.trim())) {
          assignment.setUserGroupId(userGroupId);
        }
        if (assignedTo != null && !"".equals(assignedTo.trim())) {
          assignment.setAssignedTo(assignedTo);
        }
        if (departmentId != null && !"".equals(departmentId.trim())) {
          assignment.setDepartmentId(departmentId);
        }
        if (assignment.getId() > -1) {
          isValid = this.validateObject(context, db, assignment);
          if (isValid) {
            recordCount = assignment.update(db);
          }
        } else {
          assignment.setCategoryId(thisCategory.getId());
          isValid = this.validateObject(context, db, assignment);
          if (isValid) {
            recordInserted = assignment.insert(db);
          }
        }
      }
      if (!isValid || !(recordCount == 1 || recordInserted)) {
        return getReturn(context, "UpdateAssignmentError");
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "UpdateAssignment");
  }
}

