package org.aspcfs.modules.admin.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    January 4, 2002
 *@version    $Id$
 */
public final class AdminFields extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-view"))) {
      return ("PermissionError");
    }
    return executeCommandListFolders(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListFolders(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      this.addCategoryList(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "Configuration", "Configuration");
      return ("ListFoldersOK");
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
  public String executeCommandAddFolder(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    PermissionCategory permCat = null;
    String moduleId = context.getRequest().getParameter("modId");
    int constantId = -1;
    try {
      db = this.getConnection(context);
      constantId = this.queryConstantId(db, Integer.parseInt(moduleId));
      permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "Configuration", "Configuration");
      context.getRequest().setAttribute("ModId", moduleId);
      context.getRequest().setAttribute("ConstantId", String.valueOf(constantId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
      return ("AddFolderOK");
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
  public String executeCommandInsertFolder(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean result = true;
    try {
      db = this.getConnection(context);
      CustomFieldCategory thisCategory = (CustomFieldCategory) context.getFormBean();
      result = thisCategory.insertCategory(db);
      if (!result) {
        this.processErrors(context, thisCategory.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (result) {
        return ("InsertFolderOK");
      } else {
        return ("InsertFolderERROR");
      }
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
  public String executeCommandModifyFolder(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      this.addCategoryList(context, db);
      this.addCategory(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "Configuration", "Configuration");
      return ("ModifyFolderOK");
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
  public String executeCommandUpdateFolder(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean result = false;
    try {
      db = this.getConnection(context);
      CustomFieldCategory thisCategory = (CustomFieldCategory) context.getFormBean();
      result = thisCategory.updateCategory(db);
      this.processErrors(context, thisCategory.getErrors());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Configuration", "Configuration");
    if (errorMessage == null) {
      if (result) {
        return ("UpdateFolderOK");
      } else {
        return ("UpdateFolderERROR");
      }
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
  public String executeCommandToggleFolder(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean result = true;

    String moduleId = context.getRequest().getParameter("modId");
    String categoryId = context.getRequest().getParameter("catId");
    try {
      db = this.getConnection(context);
      int constantId = this.queryConstantId(db, Integer.parseInt(moduleId));
      CustomFieldCategory thisCategory = new CustomFieldCategory(db, Integer.parseInt(categoryId));
      if (constantId == thisCategory.getModuleId()) {
        thisCategory.toggleEnabled(db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("ToggleOK");
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
  public String executeCommandDeleteFolder(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-delete"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean result = true;

    try {
      db = this.getConnection(context);
      CustomFieldCategory thisCategory = (CustomFieldCategory) context.getFormBean();
      result = thisCategory.deleteCategory(db);
      if (!result) {
        this.processErrors(context, thisCategory.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
      context.getRequest().setAttribute("actionError", "Category could not be deleted due to referential integrity.");
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "Configuration", "Configuration");
      if (result) {
        return ("DeleteFolderOK");
      } else {
        return ("DeleteFolderERROR");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  //Groups

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListGroups(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;

    try {
      db = this.getConnection(context);
      this.addCategoryList(context, db);
      this.addCategory(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "Configuration", "Configuration");
      return ("ListGroupsOK");
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
  public String executeCommandAddGroup(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      this.addCategoryList(context, db);
      this.addCategory(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("AddGroupOK");
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
  public String executeCommandInsertGroup(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean result = false;
    try {
      db = this.getConnection(context);
      CustomFieldGroup thisGroup = (CustomFieldGroup) context.getFormBean();
      result = thisGroup.insertGroup(db);
      if (!result) {
        this.processErrors(context, thisGroup.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (result) {
        return ("InsertGroupOK");
      } else {
        return ("InsertGroupERROR");
      }
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
  public String executeCommandModifyGroup(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      this.addCategoryList(context, db);
      this.addCategory(context, db);
      this.addGroup(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "Configuration", "Configuration");
      return ("ModifyGroupOK");
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
  public String executeCommandUpdateGroup(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean result = false;
    try {
      db = this.getConnection(context);
      CustomFieldGroup thisGroup = (CustomFieldGroup) context.getFormBean();
      result = thisGroup.updateGroup(db);
      this.processErrors(context, thisGroup.getErrors());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "Configuration", "Configuration");
      if (result) {
        return ("UpdateGroupOK");
      } else {
        return ("UpdateGroupERROR");
      }
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
  public String executeCommandDeleteGroup(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-delete"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean result = true;
    try {
      db = this.getConnection(context);
      CustomFieldGroup thisGroup = (CustomFieldGroup) context.getFormBean();
      result = thisGroup.deleteGroup(db);
      if (!result) {
        this.processErrors(context, thisGroup.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (result) {
        return ("DeleteGroupOK");
      } else {
        return ("DeleteGroupERROR");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  //Fields

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddField(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      this.addCategoryList(context, db);
      this.addCategory(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("AddFieldOK");
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
  public String executeCommandInsertField(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean result = false;

    try {
      db = this.getConnection(context);
      CustomField thisField = (CustomField) context.getFormBean();
      result = thisField.insertField(db);
      if (!result) {
        this.processErrors(context, thisField.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (result) {
        return ("InsertFieldOK");
      } else {
        return ("InsertFieldERROR");
      }
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
  public String executeCommandDeleteField(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-delete"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean result = true;

    try {
      db = this.getConnection(context);
      CustomField thisField = (CustomField) context.getFormBean();
      result = thisField.deleteField(db);
      if (!result) {
        this.processErrors(context, thisField.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (result) {
        return ("DeleteFieldOK");
      } else {
        return ("DeleteFieldERROR");
      }
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
  public String executeCommandModifyField(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      this.addCategoryList(context, db);
      this.addCategory(context, db);
      this.addGroup(context, db);
      this.addField(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Configuration", "Configuration");
    if (errorMessage == null) {
      return ("ModifyFieldOK");
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
  public String executeCommandUpdateField(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean result = false;

    try {
      db = this.getConnection(context);
      CustomField thisField = (CustomField) context.getFormBean();
      thisField.buildElementData(db);

      if (thisField.getLookupListRequired()) {
        String[] params = context.getRequest().getParameterValues("selectedList");
        String[] names = new String[params.length];
        String tblName = "";
        int j = 0;

        StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("selectNames"), "^");
        while (st.hasMoreTokens()) {
          names[j] = (String) st.nextToken();
          if (System.getProperty("DEBUG") != null) {
            System.out.println(names[j]);
          }
          j++;
        }

        LookupList compareList = (LookupList) thisField.getElementData();
        LookupList newList = new LookupList(params, names);
        if (System.getProperty("DEBUG") != null) {
          newList.printVals();
        }

        Iterator i = compareList.iterator();
        while (i.hasNext()) {
          LookupElement thisElement = (LookupElement) i.next();

          //still there, stay enabled, don't re-insert it
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Here: " + thisElement.getCode() + " " + newList.getSelectedValue(thisElement.getCode()));
          }

          //not there, disable it, leave it
          if (newList.getSelectedValue(thisElement.getCode()).equals("") ||
              newList.getSelectedValue(thisElement.getCode()) == null) {
            thisElement.disableElement(db, "custom_field_lookup");
          }
        }

        Iterator k = newList.iterator();
        while (k.hasNext()) {
          LookupElement thisElement = (LookupElement) k.next();
          if (thisElement.getCode() == 0) {
            thisElement.insertElement(db, "custom_field_lookup", thisField.getId());
          } else {
            thisElement.setNewOrder(db, "custom_field_lookup");
          }
        }
      }

      result = thisField.updateField(db);
      this.processErrors(context, thisField.getErrors());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "Configuration", "Configuration");
      if (result) {
        return ("UpdateFieldOK");
      } else {
        return ("UpdateFieldERROR");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Adds a feature to the CategoryList attribute of the AdminFields object
   *
   *@param  context           The feature to be added to the CategoryList
   *      attribute
   *@param  db                The feature to be added to the CategoryList
   *      attribute
   *@exception  SQLException  Description of the Exception
   */
  private void addCategoryList(ActionContext context, Connection db) throws SQLException {
    int moduleId = -1;
    PermissionCategory permCat = null;
    CustomFieldCategoryList categoryList = new CustomFieldCategoryList();
    if (context.getRequest().getParameter("modId") != null) {
      moduleId = Integer.parseInt(context.getRequest().getParameter("modId"));
      int constantId = this.queryConstantId(db, moduleId);
      //to get the module name on the jsp
      permCat = new PermissionCategory(db, moduleId);
      categoryList.setLinkModuleId(constantId);
      categoryList.setBuildResources(false);
      categoryList.buildList(db);
      context.getRequest().setAttribute("CategoryList", categoryList);
      context.getRequest().setAttribute("ModId", context.getRequest().getParameter("modId"));
      context.getRequest().setAttribute("ConstantId", String.valueOf(constantId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
    }
  }


  /**
   *  Adds a feature to the Category attribute of the AdminFields object
   *
   *@param  context           The feature to be added to the Category attribute
   *@param  db                The feature to be added to the Category attribute
   *@exception  SQLException  Description of the Exception
   */
  private void addCategory(ActionContext context, Connection db) throws SQLException {
    int moduleId = -1;
    if ((context.getRequest().getParameter("auto-populate") == null) ||
        (context.getFormBean() == null) ||
        (!(context.getFormBean() instanceof CustomFieldCategory))) {

      String categoryId = context.getRequest().getParameter("catId");
      if (categoryId == null) {
        categoryId = (String) context.getRequest().getAttribute("catId");
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AdminFields-> Adding Category: " + categoryId);
      }

      if (context.getRequest().getParameter("modId") != null) {
        moduleId = Integer.parseInt(context.getRequest().getParameter("modId"));
      }

      if (categoryId != null) {
        context.getRequest().setAttribute("catId", categoryId);
        int constantId = this.queryConstantId(db, moduleId);
        LookupList moduleList = (LookupList) context.getRequest().getAttribute("ModuleList");
        CustomFieldCategoryList categoryList = (CustomFieldCategoryList) context.getRequest().getAttribute("CategoryList");
        CustomFieldCategory thisCategory = categoryList.getCategory(Integer.parseInt(categoryId));
        thisCategory.setLinkModuleId(constantId);
        thisCategory.setBuildResources(true);
        thisCategory.buildResources(db);
        context.getRequest().setAttribute("Category", thisCategory);
      }
    }
  }


  /**
   *  Adds a feature to the Group attribute of the AdminFields object
   *
   *@param  context           The feature to be added to the Group attribute
   *@param  db                The feature to be added to the Group attribute
   *@exception  SQLException  Description of the Exception
   */
  private void addGroup(ActionContext context, Connection db) throws SQLException {
    if ((context.getRequest().getParameter("auto-populate") == null) ||
        (context.getFormBean() == null) ||
        (!(context.getFormBean() instanceof CustomFieldGroup))) {

      String groupId = context.getRequest().getParameter("grpId");
      if (groupId == null) {
        groupId = (String) context.getRequest().getAttribute("grpId");
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AdminFields-> Adding Group: " + groupId);
      }
      if (groupId != null) {
        context.getRequest().setAttribute("grpId", groupId);
        LookupList moduleList = (LookupList) context.getRequest().getAttribute("ModuleList");
        CustomFieldCategoryList categoryList = (CustomFieldCategoryList) context.getRequest().getAttribute("CategoryList");
        CustomFieldCategory thisCategory = (CustomFieldCategory) context.getRequest().getAttribute("Category");
        CustomFieldGroup thisGroup = thisCategory.getGroup(Integer.parseInt(groupId));
        context.getRequest().setAttribute("Group", thisGroup);
      }
    }
  }


  /**
   *  Adds a feature to the Field attribute of the AdminFields object
   *
   *@param  context           The feature to be added to the Field attribute
   *@param  db                The feature to be added to the Field attribute
   *@exception  SQLException  Description of the Exception
   */
  private void addField(ActionContext context, Connection db) throws SQLException {
    if ((context.getRequest().getParameter("auto-populate") == null) ||
        (context.getFormBean() == null) ||
        (!(context.getFormBean() instanceof CustomField))) {

      String fieldId = context.getRequest().getParameter("id");
      if (fieldId == null) {
        fieldId = (String) context.getRequest().getAttribute("id");
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AdminFields-> Adding Field: " + fieldId);
      }
      if (fieldId != null) {
        context.getRequest().setAttribute("id", fieldId);
        LookupList moduleList = (LookupList) context.getRequest().getAttribute("ModuleList");
        CustomFieldCategoryList categoryList = (CustomFieldCategoryList) context.getRequest().getAttribute("CategoryList");
        CustomFieldCategory thisCategory = (CustomFieldCategory) context.getRequest().getAttribute("Category");
        CustomFieldGroup thisGroup = (CustomFieldGroup) context.getRequest().getAttribute("Group");
        if (thisGroup == null) {
          System.out.println("AdminFields-> CustomFieldGroup should not be null");
        }
        CustomField thisField = thisGroup.getField(Integer.parseInt(fieldId));
        context.getRequest().setAttribute("CustomField", thisField);
      }
    } else if ((context.getRequest().getParameter("auto-populate") != null) &&
        (context.getFormBean() != null) &&
        (context.getFormBean() instanceof CustomField)) {
      CustomField thisField = (CustomField) context.getFormBean();
      thisField.buildElementData(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandMoveField(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      this.addCategoryList(context, db);
      this.addCategory(context, db);
      String change = context.getRequest().getParameter("chg");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AdminFields->MoveField: " + change);
      }
      if (change != null && change.indexOf("|") > -1) {
        StringTokenizer st = new StringTokenizer(change, "|");
        int groupChange = Integer.parseInt((String) st.nextToken());
        int fieldChange = Integer.parseInt((String) st.nextToken());
        String direction = (String) st.nextToken();

        CustomFieldCategory thisCategory = (CustomFieldCategory) context.getRequest().getAttribute("Category");
        Iterator groups = thisCategory.iterator();
        int groupCount = 0;
        int previousGroupId = -1;
        int previousGroupSize = -1;
        CustomFieldGroup previousGroup = null;
        boolean hasNewGroup = false;
        int previousFieldId = -1;
        while (groups.hasNext()) {
          CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
          ++groupCount;
          Iterator fields = thisGroup.iterator();
          int level = 0;
          int fieldCount = 0;
          boolean forceUpdate = false;
          if (hasNewGroup) {
            ++level;
            ++fieldCount;
            CustomField thisField = new CustomField();
            thisField.setId(previousFieldId);
            thisField.setLevel(level);
            thisField.setGroupId(thisGroup.getId());
            thisField.updateLevel(db);
            hasNewGroup = false;
            forceUpdate = true;
          }

          while (fields.hasNext()) {
            CustomField thisField = (CustomField) fields.next();
            ++fieldCount;

            if (groupCount > 1 && fieldCount == 1 && groupChange == groupCount && fieldChange == 1 && "U".equals(direction)) {
              thisField.setLevel(previousGroup.size() + 1);
              thisField.setGroupId(previousGroup.getId());
            } else if (groups.hasNext() && !fields.hasNext() && groupChange == groupCount && fieldChange == fieldCount && "D".equals(direction)) {
              hasNewGroup = true;
              previousFieldId = thisField.getId();
            } else if (groupCount == groupChange && "U".equals(direction)) {
              if (fieldCount < fieldChange) {
                ++level;
              }
              if ((fieldCount + 1) == fieldChange) {
                ++level;
              }
              if (fieldCount == fieldChange) {
                --level;
              }
              if (fieldCount > fieldChange) {
                ++level;
              }
              thisField.setLevel((new Integer(level)).intValue());
              if (fieldCount == fieldChange) {
                ++level;
              }
            } else if (groupCount == groupChange && "D".equals(direction)) {
              if (fieldCount < fieldChange) {
                ++level;
              }
              if (fieldCount == fieldChange) {
                level = level + 2;
              }
              if ((fieldCount - 1) == fieldChange) {
                --level;
              }
              if ((fieldCount - 1) > fieldChange) {
                ++level;
              }
              thisField.setLevel((new Integer(level)).intValue());
              if ((fieldCount - 1) == fieldChange) {
                ++level;
              }
            } else if (forceUpdate) {
              ++level;
              thisField.setLevel((new Integer(level)).intValue());
            }

            if (!hasNewGroup) {
              thisField.updateLevel(db);
            }
            System.out.println("Grp: " + thisField.getGroupId() + " Fld: " + thisField.getLevel());
          }
          previousGroup = thisGroup;
          previousGroupId = thisGroup.getId();
          previousGroupSize = thisGroup.size();
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "Configuration", "Configuration");
      return ("MoveFieldOK");
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
  public String executeCommandMoveGroup(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-folders-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      this.addCategoryList(context, db);
      this.addCategory(context, db);

      String change = context.getRequest().getParameter("chg");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AdminFields->MoveGroup: " + change);
      }

      if (change != null && change.indexOf("|") > -1) {
        StringTokenizer st = new StringTokenizer(change, "|");
        int groupChange = Integer.parseInt((String) st.nextToken());
        String direction = (String) st.nextToken();

        CustomFieldCategory thisCategory = (CustomFieldCategory) context.getRequest().getAttribute("Category");
        Iterator groups = thisCategory.iterator();
        int groupCount = 0;
        int level = 0;
        while (groups.hasNext()) {
          CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
          ++groupCount;

          if ("U".equals(direction)) {
            if (groupCount < groupChange) {
              ++level;
            }
            if ((groupCount + 1) == groupChange) {
              ++level;
            }
            if (groupCount == groupChange) {
              --level;
            }
            if (groupCount > groupChange) {
              ++level;
            }
            thisGroup.setLevel((new Integer(level)).intValue());
            if (groupCount == groupChange) {
              ++level;
            }
          } else if ("D".equals(direction)) {
            if (groupCount < groupChange) {
              ++level;
            }
            if (groupCount == groupChange) {
              level = level + 2;
            }
            if ((groupCount - 1) == groupChange) {
              --level;
            }
            if ((groupCount - 1) > groupChange) {
              ++level;
            }
            thisGroup.setLevel((new Integer(level)).intValue());
            if ((groupCount - 1) == groupChange) {
              ++level;
            }
          }

          thisGroup.updateLevel(db);
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      addModuleBean(context, "Configuration", "Configuration");
      return ("MoveGroupOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  moduleId          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private static int queryConstantId(Connection db, int moduleId) throws SQLException {
    int constantId = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT category_id " +
        "FROM module_field_categorylink " +
        "WHERE module_id = ? ");
    pst.setInt(1, moduleId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      constantId = rs.getInt("category_id");
    }
    rs.close();
    pst.close();
    return constantId;
  }
}

