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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "View");
    Exception errorMessage = null;
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");

    try {
      db = getConnection(context);
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db);
      context.getRequest().setAttribute("CategoryEditor", thisEditor);

      //build the module details
      PermissionCategory permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);

      //create lists for selected categories
      HashMap tmpList = (HashMap) context.getSession().getAttribute("selectedCategories");
      if (tmpList == null || "true".equals(context.getRequest().getParameter("reset"))) {
        tmpList = new HashMap();
        context.getSession().setAttribute("selectedCategories", tmpList);
      }
      HashMap categories = thisEditor.getCategoryList();
      for (int k = 1; k < 4; k++) {
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
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return this.getReturn(context, "View");
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
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Modify Categories");
    String categoryId = context.getRequest().getParameter("categoryId");
    TicketCategoryDraftList catList = new TicketCategoryDraftList();
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);

      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db);
      if (!"-1".equals(categoryId)) {
        TicketCategoryDraft thisCategory = (TicketCategoryDraft) thisEditor.getCategory(Integer.parseInt(categoryId));
        catList = thisCategory.getShortChildList();
      } else {
        catList = thisEditor.getTopCategoryList();
      }
      context.getRequest().setAttribute("CategoryList", catList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return this.getReturn(context, "Modify");
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
  public String executeCommandCategoryJSList(ActionContext context) {
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    
    String categoryId = context.getRequest().getParameter("categoryId");
    String level = context.getRequest().getParameter("level");
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);

      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db);
      TicketCategoryDraftList catList = null;
      if (Integer.parseInt(categoryId) > 0) {
        TicketCategoryDraft thisCategory = thisEditor.getCategory(Integer.parseInt(categoryId));
        catList = thisCategory.getShortChildList();
        context.getRequest().setAttribute("ParentCategory", thisCategory);
      } else {
        catList = thisEditor.getTopCategoryList();
      }
      context.getRequest().setAttribute("CategoryList", catList);

      //update the selected categories
      context.getSession().removeAttribute("selectedCategories");
      if (Integer.parseInt(categoryId) != -1) {
        HashMap tmpList = thisEditor.getHierarchyAsList(Integer.parseInt(categoryId));
        context.getSession().setAttribute("selectedCategories", tmpList);
      }

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return this.getReturn(context, "CategoryJSList");
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
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Save Categories");
    String categories = context.getRequest().getParameter("categories");
    String parentCatId = context.getRequest().getParameter("parentCode");

    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);

      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db);
      thisEditor.updateCategory(db, categories, Integer.parseInt(parentCatId));
      TicketCategoryDraftList catList = null;
      if (!"-1".equals(parentCatId)) {
        TicketCategoryDraft thisCategory = thisEditor.getCategory(Integer.parseInt(parentCatId));
        catList = thisCategory.getShortChildList();
      } else {
        catList = thisEditor.getTopCategoryList();
      }
      context.getRequest().setAttribute("CategoryList", catList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return this.getReturn(context, "Save");
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
  public String executeCommandConfirmSave(ActionContext context) {
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    
    HtmlDialog htmlDialog = new HtmlDialog();
    Exception errorMessage = null;
    Connection db = null;

    String categories = context.getRequest().getParameter("categories");
    String parentCatId = context.getRequest().getParameter("parentCode");
    String level = context.getRequest().getParameter("level");
    try {
      db = getConnection(context);
      
      //escape the ' character in the categories parameter
      categories = StringUtils.replacePattern(categories, "'", "\\\\'");
      
      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db);
      DependencyList dependencies = thisEditor.processDependencies(categories);
      htmlDialog.addMessage(dependencies.getHtmlString());
      if (dependencies.size() == 0) {
        htmlDialog.setTitle("Confirm");
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setHeader("Are you sure ?");
        htmlDialog.setDeleteUrl("javascript:window.location.href='AdminCategories.do?command=Save&categories=' + escape('" + categories + "') + '&parentCode=" + parentCatId + "&level=" + level + "'");
      } else {
        if (dependencies.canDelete()) {
          htmlDialog.setTitle("CFS: Confirm Delete");
          htmlDialog.setHeader("The following categories have dependencies within CFS:");
          htmlDialog.addButton("Delete All", "javascript:window.location.href='AdminCategories.do?command=Save&categories=' + escape('" + categories + "') + '&parentCode=" + parentCatId + "&level=" + level + "'");
          htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
        } else {
          htmlDialog.setTitle("CFS: Alert");
          htmlDialog.setHeader("The list cannot be updated since the following categories have dependencies within CFS:");
          htmlDialog.addButton("OK", "javascript:parent.window.close()");
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return "ConfirmSaveOK";
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
  public String executeCommandSaveDraft(ActionContext context) {
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Save Draft");
    Connection db = null;
    try {
      db = getConnection(context);

      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db);
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
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Reset List");
    Connection db = null;
    try {
      db = getConnection(context);

      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db);
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
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Activate Categories");
    Connection db = null;
    try {
      db = getConnection(context);

      //get the category editor from system status
      SystemStatus systemStatus = this.getSystemStatus(context);
      CategoryEditor thisEditor = systemStatus.getCategoryEditor(db);
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewActive(ActionContext context) {
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Active Categories");
    Exception errorMessage = null;
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");

    try {
      db = getConnection(context);

      TicketCategoryList categoryList = new TicketCategoryList();
      categoryList.setCatLevel(0);
      categoryList.setParentCode(0);
      categoryList.setHtmlJsEvent("onChange=\"javascript:loadCategory();\"");
      categoryList.buildList(db);
      context.getRequest().setAttribute("TopCategoryList", categoryList);

      //build the module details
      PermissionCategory permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return this.getReturn(context, "ViewActive");
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
  public String executeCommandActiveCatJSList(ActionContext context) {
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }

    String parentCode = context.getRequest().getParameter("categoryId");
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);

      TicketCategoryList catList = new TicketCategoryList();
      catList.setParentCode(Integer.parseInt(parentCode));
      catList.buildList(db);
      context.getRequest().setAttribute("CategoryList", catList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return this.getReturn(context, "ActiveCategoryJSList");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

