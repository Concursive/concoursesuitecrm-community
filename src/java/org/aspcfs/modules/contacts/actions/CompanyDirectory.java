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
package org.aspcfs.modules.contacts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.Iterator;

/**
 * The Company Directory module.
 *
 * @author mrajkowski
 * @version $Id: CompanyDirectory.java,v 1.9 2001/07/20 19:02:11 mrajkowski
 *          Exp $
 * @created July 9, 2001
 */
public final class CompanyDirectory extends CFSModule {

  /**
   * Includes an HTML page for output
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.0
   */
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    return ("IncludeOK");
  }


  /**
   * This method retrieves a list of employees from the database and populates
   * a Vector of the employees retrieved.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.0
   */
  public String executeCommandListEmployees(ActionContext context) {

    if (!(hasPermission(context, "contacts-internal_contacts-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo companyDirectoryInfo = this.getPagedListInfo(
        context, "CompanyDirectoryInfo");
    companyDirectoryInfo.setLink("CompanyDirectory.do?command=ListEmployees");

    Connection db = null;
    ContactList employeeList = new ContactList();
    try {
      db = this.getConnection(context);
      employeeList.setPagedListInfo(companyDirectoryInfo);
      employeeList.setEmployeesOnly(Constants.TRUE);
      employeeList.setLeadsOnly(Constants.FALSE);
      employeeList.setCheckEnabledUserAccess(true);
      employeeList.setBuildDetails(true);
      employeeList.setPersonalId(ContactList.IGNORE_PERSONAL);
      employeeList.setBuildTypes(false);
      employeeList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Internal Contacts", "Internal List");
    if (errorMessage == null) {
      context.getRequest().setAttribute("EmployeeList", employeeList);
      return ("ListEmployeesOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * This method retrieves the details of a single employee. The resulting
   * Employee object is added to the request if successful.<p>
   * <p/>
   * <p/>
   * <p/>
   * This method handles output for both viewing and modifying a contact.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.0
   */
  public String executeCommandEmployeeDetails(ActionContext context) {
    if (!hasPermission(context, "contacts-internal_contacts-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisEmployee = null;
    String employeeId = context.getRequest().getParameter("empid");
    try {
      db = this.getConnection(context);
      thisEmployee = new Contact(db, employeeId);
      //enabled user?
      thisEmployee.checkEnabledUserAccount(db);
      context.getRequest().setAttribute("ContactDetails", thisEmployee);
      addRecentItem(context, thisEmployee);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //If user is going to the detail screen
    addModuleBean(context, "Internal Contacts", "View Employee Details");
    return getReturn(context, "EmployeeDetails");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyEmployee(ActionContext context) {
    if (!hasPermission(context, "contacts-internal_contacts-view")) {
      return ("PermissionError");
    }
    String employeeId = context.getRequest().getParameter("empid");
    Connection db = null;
    Contact thisEmployee = null;
    try {
      db = this.getConnection(context);
      thisEmployee = (Contact) context.getFormBean();
      thisEmployee.queryRecord(db, Integer.parseInt(employeeId));
      //enabled user?
      thisEmployee.checkEnabledUserAccount(db);
      addRecentItem(context, thisEmployee);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Internal Contacts", "Modify Employee Details");
    return executeCommandPrepare(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    int resultCount = 0;
    boolean recordInserted = false;
    boolean isValid = false;
    String permission = "contacts-internal_contacts-add";

    Contact thisEmployee = (Contact) context.getFormBean();
    thisEmployee.setEmployee(true);
    thisEmployee.setRequestItems(context);
    thisEmployee.setEnteredBy(getUserId(context));
    thisEmployee.setModifiedBy(getUserId(context));

    //insert
    if (thisEmployee.getId() > 0) {
      permission = "accounts-accounts-contacts-edit";
    }
    if (!hasPermission(context, permission)) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      //set the access type as public
      AccessTypeList accessTypes = this.getSystemStatus(context).getAccessTypeList(
          db, AccessType.EMPLOYEES);
      thisEmployee.setAccessType(accessTypes.getDefaultItem());
      if (thisEmployee.getId() == -1) {
        // trying to insert a contact
        thisEmployee.setOrgId(0);
        addModuleBean(context, "Internal Contacts", "Internal Insert");
        isValid = validateObject(context, db, thisEmployee);
        if (isValid) {
          recordInserted = thisEmployee.insert(db);
        }
      } else {
        // trying to update a contact
        addModuleBean(context, "Internal Contacts", "Update Employee");
        isValid = validateObject(context, db, thisEmployee);
        if (isValid) {
          resultCount = thisEmployee.update(db);
        }
      }
      if (recordInserted) {
        thisEmployee = new Contact(db, thisEmployee.getId());
        context.getRequest().setAttribute("ContactDetails", thisEmployee);
        addRecentItem(context, thisEmployee);
      } else if (resultCount == 1) {
        //If the user is in the cache, update the contact record
        thisEmployee.checkUserAccount(db);
        this.updateUserContact(db, context, thisEmployee.getUserId());
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // decide what happend with the processing
    if (recordInserted) {
      if ("true".equals(
          (String) context.getRequest().getParameter("saveAndNew"))) {
        context.getRequest().removeAttribute("ContactDetails");
        return (executeCommandPrepare(context));
      }
      context.getRequest().setAttribute("ContactDetails", thisEmployee);
      if ("true".equals(context.getRequest().getParameter("popup"))) {
        if ("troubletickets".equals(
            context.getRequest().getParameter("source"))) {
          return ("TroubleTicketsCloseInsertPopup");
        }
        return ("CloseInsertContactPopup");
      }
      return ("EmployeeDetailsOK");
    } else if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandListEmployees(context));
      } else {
        return ("EmployeeDetailsUpdateOK");
      }
    } else {
      if (thisEmployee.getId() == -1) {
        // tried to insert
        if (!"true".equals(context.getRequest().getParameter("popup")) || "troubletickets".equals(
            context.getRequest().getParameter("source"))) {
          return (executeCommandPrepare(context));
        } else {
          return "ReloadAddContactPopup";
        }
      } else {
        // tried to update
        if (resultCount == -1 || !isValid) {
          return executeCommandPrepare(context);
        }
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Contact thisContact = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;

    if (!(hasPermission(context, "contacts-internal_contacts-delete"))) {
      return ("PermissionError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = context.getRequest().getParameter("id");
    }

    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisContact = new Contact(db, id);
      thisContact.checkUserAccount(db);
      DependencyList dependencies = thisContact.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());

      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      if (!thisContact.hasAccount()) {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      } else {
        htmlDialog.setHeader(
            systemStatus.getLabel("confirmdelete.employeeUserAccountHeader"));
      }
      htmlDialog.addButton(
          systemStatus.getLabel("button.delete"), "javascript:window.location.href='CompanyDirectory.do?command=TrashEmployee&empid=" + id + "&popup=true'");
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");

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
   * Preparation for displaying the insert employee contact form
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.10
   */
  public String executeCommandPrepare(ActionContext context) {
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Contact thisContact = (Contact) context.getFormBean();
    if (thisContact.getId() == -1) {
      if (!(hasPermission(context, "contacts-internal_contacts-add"))) {
        return ("PermissionError");
      }
      addModuleBean(context, "Internal Contacts", "Add a new Employee");
    }
    try {
      db = this.getConnection(context);
      //prepare the Department List if employee is being added.
      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(
          0, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("DepartmentList", departmentList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (context.getRequest().getParameter("actionSource") != null) {
      return getReturn(context, "EmployeePrepare");
    }
    return ("PrepareOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.10
   */
  public String executeCommandDeleteEmployee(ActionContext context) {

    if (!(hasPermission(context, "contacts-internal_contacts-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    Contact thisContact = null;
    SystemStatus systemStatus = this.getSystemStatus(context);

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(
          db, context.getRequest().getParameter("empid"));

      recordDeleted = thisContact.delete(db, this.getDbNamePath(context));
      processErrors(context, thisContact.getErrors());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Internal Contacts", "Internal Delete");
    if (errorMessage == null) {
      if (recordDeleted) {
        deleteRecentItem(context, thisContact);
        if ("true".equals(context.getRequest().getParameter("popup"))) {
          context.getRequest().setAttribute(
              "refreshUrl", "CompanyDirectory.do?command=ListEmployees");
          return ("EmployeeDeletePopupOK");
        }
        return "EmployeeDeleteOK";
      } else {
        processErrors(context, thisContact.getErrors());
        return (executeCommandListEmployees(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTrashEmployee(ActionContext context) {

    if (!(hasPermission(context, "contacts-internal_contacts-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;
    Contact thisContact = null;
    SystemStatus systemStatus = this.getSystemStatus(context);

    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(
          db, context.getRequest().getParameter("empid"));

      recordDeleted = thisContact.updateStatus(
          db, context, true, this.getUserId(context));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Internal Contacts", "Internal Delete");
    if (errorMessage == null) {
      if (recordDeleted) {
        deleteRecentItem(context, thisContact);
        if ("true".equals(context.getRequest().getParameter("popup"))) {
          context.getRequest().setAttribute(
              "refreshUrl", "CompanyDirectory.do?command=ListEmployees");
          return ("EmployeeDeletePopupOK");
        }
        return "EmployeeDeleteOK";
      } else {
        processErrors(context, thisContact.getErrors());
        return (executeCommandListEmployees(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandFolderList(ActionContext context) {
    if (!(hasPermission(context, "contacts-internal_contacts-folders-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    try {
      String empId = context.getRequest().getParameter("empid");
      db = this.getConnection(context);
      thisContact = new Contact(db, Integer.parseInt(empId));
      context.getRequest().setAttribute("ContactDetails", thisContact);
      //Show a list of the different folders available in Accounts
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_EMPLOYEES);
      thisList.setLinkItemId(thisContact.getId());
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.setBuildTotalNumOfRecords(true);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Employees", "Custom Fields Details");
    return getReturn(context, "FolderList");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandFields(ActionContext context) {
    if (!(hasPermission(context, "contacts-internal_contacts-folders-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    String catId = null;
    String recordId = null;
    boolean showRecords = true;
    try {
      String empId = context.getRequest().getParameter("empid");
      db = this.getConnection(context);
      thisContact = new Contact(db, Integer.parseInt(empId));
      context.getRequest().setAttribute("ContactDetails", thisContact);
      //Show a list of the different folders available in Accounts
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_EMPLOYEES);
      thisList.setLinkItemId(thisContact.getId());
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.setBuildTotalNumOfRecords(true);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);
      //Check to see if a category has been selected
      catId = (String) context.getRequest().getParameter("catId");
      if (Integer.parseInt(catId) > 0) {
        //See if a specific record has been chosen from the list
        recordId = context.getRequest().getParameter("recId");
        String recordDeleted = (String) context.getRequest().getAttribute(
            "recordDeleted");
        if (recordDeleted != null) {
          recordId = null;
        }
        //Build the selected category
        CustomFieldCategory thisCategory = thisList.getCategory(
            Integer.parseInt(catId));
        if (recordId == null && thisCategory.getAllowMultipleRecords()) {
          //The user didn't request a specific record, so show a list
          //of records matching this category that the user can choose from
          CustomFieldRecordList recordList = new CustomFieldRecordList();
          recordList.setLinkModuleId(Constants.FOLDERS_EMPLOYEES);
          recordList.setLinkItemId(thisContact.getId());
          recordList.setCategoryId(thisCategory.getId());
          recordList.buildList(db);
          recordList.buildRecordColumns(db, thisCategory);
          context.getRequest().setAttribute("Records", recordList);
        } else {
          //The user requested a specific record, or this category only
          //allows a single record.
          thisCategory.setLinkModuleId(Constants.FOLDERS_EMPLOYEES);
          thisCategory.setLinkItemId(thisContact.getId());
          if (recordId != null) {
            thisCategory.setRecordId(Integer.parseInt(recordId));
          } else {
            thisCategory.buildRecordId(db);
            recordId = String.valueOf(thisCategory.getRecordId());
          }
          thisCategory.setIncludeEnabled(Constants.TRUE);
          thisCategory.setIncludeScheduled(Constants.TRUE);
          thisCategory.setBuildResources(true);
          thisCategory.buildResources(db);
          showRecords = false;

          if (thisCategory.getRecordId() > -1) {
            CustomFieldRecord thisRecord = new CustomFieldRecord(
                db, thisCategory.getRecordId());
            context.getRequest().setAttribute("Record", thisRecord);
          }
        }
        context.getRequest().setAttribute("Category", thisCategory);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Employees", "Custom Fields Details");
    if (catId != null && Integer.parseInt(catId) <= 0) {
      return getReturn(context, "FieldsEmpty");
    } else if (recordId == null && showRecords) {
      return getReturn(context, "FieldRecordList");
    } else {
      return getReturn(context, "Fields");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddFolderRecord(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    try {
      String contactId = context.getRequest().getParameter("empid");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
      String selectedCatId = (String) context.getRequest().getParameter(
          "catId");
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_EMPLOYEES);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Employees", "Add Folder Record");
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    return getReturn(context, "AddFolderRecord");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsertFields(ActionContext context) {
    Connection db = null;
    int resultCode = -1;
    Contact thisContact = null;
    boolean isValid = false;
    try {
      String empId = context.getRequest().getParameter("empid");
      db = this.getConnection(context);
      thisContact = new Contact(db, empId);
      if (!(hasPermission(context, "contacts-internal_contacts-folders-add"))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.CONTACTS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String) context.getRequest().getParameter(
          "catId");
      context.getRequest().setAttribute("catId", selectedCatId);
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_EMPLOYEES);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setParameters(context);
      thisCategory.setEnteredBy(this.getUserId(context));
      thisCategory.setModifiedBy(this.getUserId(context));
      if (!thisCategory.getReadOnly()) {
        thisCategory.setCanNotContinue(true);
        resultCode = thisCategory.insert(db);
        Iterator groups = (Iterator) thisCategory.iterator();
        isValid = true;
        while (groups.hasNext()) {
          CustomFieldGroup group = (CustomFieldGroup) groups.next();
          Iterator fields = (Iterator) group.iterator();
          while (fields.hasNext()) {
            CustomField field = (CustomField) fields.next();
            field.setValidateData(true);
            field.setRecordId(thisCategory.getRecordId());
            isValid = this.validateObject(context, db, field) && isValid;
          }
        }
        thisCategory.setCanNotContinue(false);
        if (isValid && resultCode != -1) {
          resultCode = thisCategory.insertGroup(
              db, thisCategory.getRecordId());
        }
      }
      context.getRequest().setAttribute("Category", thisCategory);
      if (resultCode == -1 || !isValid) {
        if (thisCategory.getRecordId() != -1) {
          CustomFieldRecord record = new CustomFieldRecord(
              db, thisCategory.getRecordId());
          record.delete(db);
        }
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Employees-> InsertField validation error");
        }
        return getReturn(context, "AddFolderRecord");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandFields(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;

    String selectedCatId = (String) context.getRequest().getParameter("catId");
    String recordId = (String) context.getRequest().getParameter("recId");

    try {
      String contactId = context.getRequest().getParameter("empid");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "contacts-internal_contacts-folders-edit"))) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("ContactDetails", thisContact);

      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_EMPLOYEES);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);

    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "Modify Custom Fields");
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    if (recordId.equals("-1")) {
      return getReturn(context, "AddFolderRecord");
    } else {
      return getReturn(context, "ModifyFields");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    int resultCount = 0;
    boolean isValid = false;
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    try {
      String contactId = context.getRequest().getParameter("empid");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "contacts-internal_contacts-folders-edit"))) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("ContactDetails", thisContact);

      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_EMPLOYEES);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String) context.getRequest().getParameter(
          "catId");
      String recordId = (String) context.getRequest().getParameter("recId");

      context.getRequest().setAttribute("catId", selectedCatId);
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_EMPLOYEES);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setParameters(context);
      thisCategory.setModifiedBy(this.getUserId(context));
      if (!thisCategory.getReadOnly()) {
        thisCategory.setCanNotContinue(true);
        isValid = this.validateObject(context, db, thisCategory);
        if (isValid) {
          Iterator groups = (Iterator) thisCategory.iterator();
          while (groups.hasNext()) {
            CustomFieldGroup group = (CustomFieldGroup) groups.next();
            Iterator fields = (Iterator) group.iterator();
            while (fields.hasNext()) {
              CustomField field = (CustomField) fields.next();
              field.setValidateData(true);
              field.setRecordId(thisCategory.getRecordId());
              isValid = this.validateObject(context, db, field) && isValid;
            }
          }
        }
        if (isValid && resultCount != -1) {
          thisCategory.setCanNotContinue(true);
          resultCount = thisCategory.update(db);
          thisCategory.setCanNotContinue(false);
          resultCount = thisCategory.insertGroup(
              db, thisCategory.getRecordId());
        }
      }
      context.getRequest().setAttribute("Category", thisCategory);
      if (resultCount == -1 || !isValid) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Employees-> ModifyField validation error");
        }
        return getReturn(context, "ModifyFields");
      } else {
        thisCategory.buildResources(db);
        CustomFieldRecord thisRecord = new CustomFieldRecord(
            db, thisCategory.getRecordId());
        context.getRequest().setAttribute("Record", thisRecord);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      return getReturn(context, "UpdateFields");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteFields(ActionContext context) {
    Connection db = null;
    boolean recordDeleted = false;
    try {
      db = this.getConnection(context);
      String selectedCatId = context.getRequest().getParameter("catId");
      String recordId = context.getRequest().getParameter("recId");
      String contactId = context.getRequest().getParameter("empid");
      Contact thisContact = new Contact(db, Integer.parseInt(contactId));
      if (!(hasPermission(
          context, "contacts-internal_contacts-folders-delete"))) {
        return ("PermissionError");
      }
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));

      CustomFieldRecord thisRecord = new CustomFieldRecord(
          db, Integer.parseInt(recordId));
      thisRecord.setLinkModuleId(Constants.FOLDERS_EMPLOYEES);
      thisRecord.setLinkItemId(Integer.parseInt(contactId));
      thisRecord.setCategoryId(Integer.parseInt(selectedCatId));
      if (!thisCategory.getReadOnly()) {
        recordDeleted = thisRecord.delete(db);
      }
      context.getRequest().setAttribute("recordDeleted", "true");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DeleteFieldsOK");
  }
}

