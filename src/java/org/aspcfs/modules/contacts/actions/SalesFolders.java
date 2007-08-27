/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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

import java.sql.Connection;
import java.util.Iterator;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomField;
import org.aspcfs.modules.base.CustomFieldCategory;
import org.aspcfs.modules.base.CustomFieldCategoryList;
import org.aspcfs.modules.base.CustomFieldGroup;
import org.aspcfs.modules.base.CustomFieldRecord;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import com.darkhorseventures.framework.actions.ActionContext;

/**
 *  Description of the Class
 *
 * @author     yury.andreev
 * @created    June 27, 2007
 * @version    $Id: SalesFolders.java,v 1.1.2.2 2005/06/01 18:09:24 yury.andreev
 *      Exp $
 */
public final class SalesFolders extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandFolderList(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-folders-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    try {
      String empId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, Integer.parseInt(empId));
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);
      //Show a list of the different folders available in Accounts
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_LEADS);
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
    addModuleBean(context, "External Contacts", "Custom Fields Details");

    return getReturn(context, "FolderList");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    String recordId = null;
    boolean showRecords = true;
    String selectedCatId = null;

    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);


      if (!(hasPermission(context, "sales-leads-folders-view")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-view")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);


      //Show a list of the different folders available in Contacts
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_LEADS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      //See which one is currently selected or use the default
      selectedCatId = (String) context.getRequest().getParameter("catId");
      if (selectedCatId == null) {
        selectedCatId = (String) context.getRequest().getAttribute("catId");
      }
      if (selectedCatId == null) {
        selectedCatId = String.valueOf(thisList.getDefaultCategoryId());
      }
      context.getRequest().setAttribute("catId", selectedCatId);

      if (Integer.parseInt(selectedCatId) > 0) {
        //See if a specific record has been chosen from the list
        recordId = context.getRequest().getParameter("recId");
        String recordDeleted = (String) context.getRequest().getAttribute(
            "recordDeleted");
        if (recordDeleted != null) {
          recordId = null;
        }


        //Now build the specified or default category
        CustomFieldCategory thisCategory = thisList.getCategory(
            Integer.parseInt(selectedCatId));
        if (recordId == null && thisCategory.getAllowMultipleRecords()) {
          //The user didn't request a specific record, so show a list
          //of records matching this category that the user can choose from
          PagedListInfo folderListInfo = this.getPagedListInfo(
              context, "ContactFolderInfo");
          folderListInfo.setLink(
              "SalesFolders.do?command=Fields&contactId=" + contactId + "&catId=" + selectedCatId + RequestUtils.addLinkParams(
              context.getRequest(), "popup|popupType|actionId"));

          CustomFieldRecordList recordList = new CustomFieldRecordList();
          recordList.setLinkModuleId(Constants.FOLDERS_LEADS);
          recordList.setLinkItemId(thisContact.getId());
          recordList.setCategoryId(thisCategory.getId());
          recordList.buildList(db);
          recordList.buildRecordColumns(db, thisCategory);
          context.getRequest().setAttribute("Records", recordList);

        } else {
          //The user requested a specific record, or this category only
          //allows a single record.
          thisCategory.setLinkModuleId(Constants.FOLDERS_LEADS);
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
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Leads", "Custom Fields Details");
    if (Integer.parseInt(selectedCatId) <= 0) {
      return getReturn(context, "FieldsEmpty");
    } else if (recordId == null && showRecords) {
      return getReturn(context, "FieldRecordList");
    } else {
      return getReturn(context, "Fields");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDeleteFields(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      String selectedCatId = context.getRequest().getParameter("catId");
      String recordId = context.getRequest().getParameter("recId");
      String contactId = context.getRequest().getParameter("contactId");


      Contact thisContact = new Contact(db, Integer.parseInt(contactId));
      if (!(hasPermission(
          context, "sales-leads-folders-delete")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));

      CustomFieldRecord thisRecord = new CustomFieldRecord(
          db, Integer.parseInt(recordId));
      thisRecord.setLinkModuleId(Constants.FOLDERS_LEADS);
      thisRecord.setLinkItemId(Integer.parseInt(contactId));
      thisRecord.setCategoryId(Integer.parseInt(selectedCatId));
      if (!thisCategory.getReadOnly()) {
        thisRecord.delete(db);
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


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandModifyFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;

    String selectedCatId = (String) context.getRequest().getParameter("catId");
    String recordId = (String) context.getRequest().getParameter("recId");

    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "sales-leads-folders-edit")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("ContactDetails", thisContact);

      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_LEADS);
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
   *  UpdateFields: Performs the actual update of the selected Custom Field
   *  Record based on user-submitted information from the modify form.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandUpdateFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    int resultCount = 0;
    boolean isValid = false;
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "sales-leads-folders-edit")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);


      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_LEADS);
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
      thisCategory.setLinkModuleId(Constants.FOLDERS_LEADS);
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
          System.out.println("Contacts-> ModifyField validation error");
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
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandInsertFields(ActionContext context) {
    Connection db = null;
    int resultCode = -1;
    Contact thisContact = null;
    boolean isValid = false;
    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "sales-leads-folders-add")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);


      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_LEADS);
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
      thisCategory.setLinkModuleId(Constants.FOLDERS_LEADS);
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
        SystemStatus systemStatus = this.getSystemStatus(context);
        context.getRequest().setAttribute("systemStatus", systemStatus);
        if (thisCategory.getRecordId() != -1) {
          CustomFieldRecord record = new CustomFieldRecord(
              db, thisCategory.getRecordId());
          record.delete(db);
        }
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Contacts-> InsertField validation error");
        }
        return getReturn(context, "AddFolderRecord");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (this.executeCommandFields(context));
  }



  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddFolderRecord(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);


      if (!(hasPermission(context, "sales-leads-folders-add"))) {
        return ("PermissionError");

      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      String selectedCatId = (String) context.getRequest().getParameter(
          "catId");
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.FOLDERS_LEADS);
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
    addModuleBean(context, "Leads", "Add Folder Record");
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    return getReturn(context, "AddFolderRecord");
  }
}
