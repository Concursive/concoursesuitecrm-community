/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.icelet;

import org.aspcfs.modules.base.*;
import org.aspcfs.modules.website.utils.PortletUtils;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * FolderPortlet class to render folder records into portlet
 *
 * @author rajendra
 * @author nagaraja
 * @version $Id: Exp $
 * @created april 6th, 2007
 */
public class FolderPortlet extends GenericPortlet {

  public final static String SELECT_FOLDER = "7031914";
  public final static String NUMBER_OF_RECORDS = "7031915";
  public final static String FOLDER_ACCESS_VIEW = "7031916";
  public final static String FOLDER_ACCESS_ADD = "7031917";
  public final static String FOLDER_ACCESS_EDIT = "7031918";
  public final static String FOLDER_ACCESS_DELETE = "7031919";
  public final static String FIELDS_TO_DISPLAY = "7031920";
  public final static String AGGREGATE_TOTAL = "7031921";
  public final static String AGGREGATE_AVERAGE = "7031922";
  private final static String VIEW_PAGE1 = "/portlets/folder/fields_list.jsp";
  private final static String VIEW_PAGE2 = "/portlets/folder/fields_add.jsp";
  private final static String VIEW_PAGE3 = "/portlets/folder/fields_modify.jsp";
  private final static String VIEW_PAGE4 = "/portlets/folder/fields.jsp";

  /**
   * doView method of FolderPortlet
   *
   * @param request  RenderRequest
   * @param response RenderResponse
   */
  public void doView(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    String viewType = null;
    PortletRequestDispatcher requestDispatcher = null;
    if (request.getParameter("viewType") != null) {
      viewType = request.getParameter("viewType");
    }
    if ("add".equals(viewType)) {
      addFields(request, response);
    } else if ("edit".equals(viewType)) {
      modifyFields(request, response);
    } else if ("details".equals(viewType)) {
      buildFolderDetails(request, response);
    } else if ("delete".equals(viewType)) {
      deleteFields(request, response);
      buildFolderList(request, response);
      requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE1);
      requestDispatcher.include(request, response);
    } else {
      buildFolderList(request, response);
      requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE1);
      requestDispatcher.include(request, response);
    }
  }

  /**
   * process action method of FolderPortlet
   *
   * @param request  ActionRequest
   * @param response ActionResponse
   */
  public void processAction(ActionRequest request, ActionResponse response)
      throws PortletException, IOException {
    String actionType = request.getParameter("actionType");
    if ("insert".equals(actionType)) {
      insertFields(request, response);
    } else if ("update".equals(actionType)) {
      updateFields(request, response);
    }
  }

  /**
   * Deletes selected record
   *
   * @param request  RenderRequest
   * @param response RenderResponse
   */
  private void deleteFields(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    Connection db = null;
    CustomFieldCategory thisCategory = null;
    try {
      db = PortletUtils.getConnection(request);
      thisCategory = new CustomFieldCategory(db, Integer.parseInt(request.getPreferences().getValue(SELECT_FOLDER, "1")));
      String recordId = request.getParameter("recordId");
      CustomFieldRecord thisRecord = new CustomFieldRecord(db, Integer.parseInt(recordId));
      thisRecord.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      thisRecord.setLinkItemId(PortletUtils.getUser(request).getContact().getId());
      thisRecord.setCategoryId(thisCategory.getId());
      thisRecord.setId(Integer.parseInt(recordId));
      if (!thisCategory.getReadOnly()) {
        thisRecord.delete(db);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * build list of folder records from portlet preferences
   *
   * @param request  RenderRequest
   * @param response RenderResponse
   */
  private void buildFolderList(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    CustomFieldCategory thisCategory = null;
    Connection db;
    ArrayList displayInList = new ArrayList();
    int categoryId = 0;
    CustomFieldCategory category = null;
    CustomField thisField = null;
    CustomFieldGroup thisGroup = null;
    CustomFieldRecordList recordList = null;
    CustomFieldCategoryList thisList = null;
    ArrayList recordCategories = new ArrayList();
    int noOfRecords = 0;


    try {
      db = PortletUtils.getConnection(request);
      categoryId = Integer.parseInt(request.getPreferences().getValue(SELECT_FOLDER, "1"));
      thisCategory = new CustomFieldCategory(db, categoryId);
      thisCategory.setCanAdd(Boolean.parseBoolean(request.getPreferences().getValue(FOLDER_ACCESS_ADD, "false")));
      thisCategory.setCanView(Boolean.parseBoolean(request.getPreferences().getValue(FOLDER_ACCESS_VIEW, "false")));
      thisCategory.setCanEdit(Boolean.parseBoolean(request.getPreferences().getValue(FOLDER_ACCESS_EDIT, "false")));
      thisCategory.setCanDelete(Boolean.parseBoolean(request.getPreferences().getValue(FOLDER_ACCESS_DELETE, "false")));
      thisCategory.setDoAverage(Boolean.parseBoolean(request.getPreferences().getValue(AGGREGATE_AVERAGE, "false")));
      thisCategory.setDoTotal(Boolean.parseBoolean(request.getPreferences().getValue(AGGREGATE_TOTAL, "false")));
      String noOffRecods = request.getPreferences().getValue(NUMBER_OF_RECORDS, "false");
      String fieldList[] = request.getPreferences().getValue(FIELDS_TO_DISPLAY, "").split("\n");
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      for (int i = 0; i < fieldList.length; i++) {
        Iterator groups = thisCategory.iterator();
        while (groups.hasNext()) {
          thisGroup = (CustomFieldGroup) groups.next();
          Iterator fields = thisGroup.iterator();
          while (fields.hasNext()) {
            thisField = (CustomField) fields.next();
            if (((thisField.getName()).trim()).equalsIgnoreCase((fieldList[i]).trim())) {
              displayInList.add(thisField.getId());
            }
          }
        }
      }
      thisCategory.setDisplayInList(displayInList);
      thisCategory.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      db = PortletUtils.getConnection(request);
      thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      category = thisList.getCategory(categoryId);
      category.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      category.setIncludeEnabled(Constants.TRUE);
      category.setIncludeScheduled(Constants.TRUE);
      category.setBuildResources(true);
      recordList = new CustomFieldRecordList();
      recordList.setLinkItemId(PortletUtils.getUser(request).getContact().getId());
      recordList.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      recordList.setCategoryId(category.getId());

      recordList.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      recordList.setCategoryId(category.getId());

      recordList.buildList(db);
      recordList.buildRecordColumns(db, thisCategory);

      Iterator records = recordList.iterator();
      noOfRecords = recordList.size();
      CustomFieldRecord thisRecord;
      while (records.hasNext()) {
        thisRecord = (CustomFieldRecord) records.next();
        CustomFieldCategory recordCategory = new CustomFieldCategory(db, category.getId());
        recordCategory.setRecordId(thisRecord.getId());
        recordCategory.setBuildResources(true);
        recordCategory.buildResources(db);
        recordCategories.add(recordCategory);

      }

      thisCategory.doAggregateFunctions(db, noOfRecords);

    } catch (Exception e) {
      e.printStackTrace();
    }

    int temp;
    String randomNum = Integer.toString((temp = new Random().nextInt()) > 0 ? temp / 10000 : (-temp / 10000));
    request.setAttribute("randomNum", randomNum);
    request.setAttribute("Category", thisCategory);
    request.setAttribute("noOfRecords", request.getPreferences().getValue("NUMBER_OF_RECORDS", "0"));
    request.setAttribute("recordList", recordList);
    request.setAttribute("recordCategories", recordCategories);
    request.setAttribute("systemStatus", PortletUtils.getSystemStatus(request));
  }

  /**
   * renders the add record page into portlet
   *
   * @param request  RenderRequest
   * @param response RenderResponse
   */
  private void addFields(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    CustomFieldCategory thisCategory;
    Connection db;
    PortletRequestDispatcher requestDispatcher;
    try {
      db = PortletUtils.getConnection(request);
      int categoryId = Integer.parseInt(request.getPreferences().getValue(SELECT_FOLDER, "1"));
      thisCategory = new CustomFieldCategory(db, categoryId);
      thisCategory.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      thisCategory.setCanAdd(Boolean.parseBoolean(request.getPreferences().getValue(FOLDER_ACCESS_ADD, "false")));
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      request.setAttribute("Category", thisCategory);
      request.setAttribute("systemStatus", PortletUtils.getSystemStatus(request));
    } catch (Exception e) {
      e.printStackTrace();
    }
    requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE2);
    requestDispatcher.include(request, response);
  }

  /**
   * new record insertion into databse
   *
   * @param request  ActionRequest
   * @param response ActionResponse
   */

  private void insertFields(ActionRequest request, ActionResponse response)
      throws PortletException, IOException {
    int resultCode;
    Connection db = PortletUtils.getConnection(request);
    try {
      int categoryId = Integer.parseInt(request.getPreferences().getValue(SELECT_FOLDER, "1"));
      CustomFieldCategory newCategory = new CustomFieldCategory(db, categoryId);
      newCategory.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      newCategory.setIncludeEnabled(Constants.TRUE);
      newCategory.setIncludeScheduled(Constants.TRUE);
      newCategory.setLinkItemId(PortletUtils.getUser(request).getContact().getId());
      newCategory.setBuildResources(true);
      newCategory.buildResources(db);
      newCategory.setParameters((HttpServletRequest) request);
      newCategory.setEnteredBy(PortletUtils.getUser(request).getActualUserId());
      newCategory.setModifiedBy(PortletUtils.getUser(request).getActualUserId());
      if (!newCategory.getReadOnly()) {
        newCategory.setCanNotContinue(true);
        resultCode = newCategory.insert(db);
        Iterator groups = (Iterator) newCategory.iterator();
        while (groups.hasNext()) {
          CustomFieldGroup group = (CustomFieldGroup) groups.next();
          Iterator fields = (Iterator) group.iterator();
          while (fields.hasNext()) {
            CustomField field = (CustomField) fields.next();
            field.setValidateData(true);
            field.setRecordId(newCategory.getRecordId());
          }
        }
        newCategory.setCanNotContinue(false);
        if (resultCode != -1) {
          resultCode = newCategory.insertGroup(db, newCategory.getRecordId());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * renders modify record page
   *
   * @param request  RenderRequest
   * @param response RenderResponse
   */
  private void modifyFields(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    CustomFieldCategory thisCategory;
    Connection db;
    PortletRequestDispatcher requestDispatcher;
    int categoryId = Integer.parseInt(request.getPreferences().getValue(SELECT_FOLDER, "1"));
    String recordId = request.getParameter("recordId");
    db = PortletUtils.getConnection(request);
    try {
      thisCategory = new CustomFieldCategory(db, categoryId);
      thisCategory.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      thisCategory.setLinkItemId(PortletUtils.getUser(request).getOrgId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      request.setAttribute("Category", thisCategory);
      request.setAttribute("systemStatus", PortletUtils.getSystemStatus(request));
      requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE3);
      requestDispatcher.include(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * update record
   *
   * @param request  ActionRequest
   * @param response ActionResponse
   */
  private void updateFields(ActionRequest request, ActionResponse response)
      throws PortletException, IOException {
    CustomFieldCategory thisCategory;
    Connection db;
    int resultCode;
    try {
      db = PortletUtils.getConnection(request);
      int categoryId = Integer.parseInt(request.getPreferences().getValue(SELECT_FOLDER, "1"));
      thisCategory = new CustomFieldCategory(db, categoryId);
      thisCategory.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setLinkItemId(PortletUtils.getUser(request).getContact().getId());
      String recordId = request.getParameter("recordId");
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setParameters((HttpServletRequest) request);
      thisCategory.setEnteredBy(PortletUtils.getUser(request).getActualUserId());
      thisCategory.setModifiedBy(PortletUtils.getUser(request).getActualUserId());
      if (!thisCategory.getReadOnly()) {
        thisCategory.setCanNotContinue(true);
        //isValid = this.validateObject(context, db, thisCategory);
        //if (isValid) {
        resultCode = thisCategory.update(db);
        Iterator groups = (Iterator) thisCategory.iterator();
        while (groups.hasNext()) {
          CustomFieldGroup group = (CustomFieldGroup) groups.next();
          Iterator fields = (Iterator) group.iterator();
          while (fields.hasNext()) {
            CustomField field = (CustomField) fields.next();
            field.setValidateData(true);
            field.setRecordId(thisCategory.getRecordId());
            //isValid = this.validateObject(context, db, field) && isValid;
            //}
          }
        }
        thisCategory.setCanNotContinue(false);
        if (resultCode != -1) {
          resultCode = thisCategory.insertGroup(db, thisCategory.getRecordId());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * build record details
   *
   * @param request  RenderRequest
   * @param response RenderResponse
   */
  private void buildFolderDetails(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    PortletRequestDispatcher requestDispatcher;
    CustomFieldCategory thisCategory;
    CustomFieldRecord thisRecord;
    Connection db;
    int categoryId = Integer.parseInt(request.getPreferences().getValue(SELECT_FOLDER, "1"));
    String recordId = request.getParameter("recordId");
    db = PortletUtils.getConnection(request);
    try {
      thisCategory = new CustomFieldCategory(db, categoryId);
      thisCategory.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      thisCategory.setLinkItemId(PortletUtils.getUser(request).getOrgId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setCanEdit(Boolean.parseBoolean(request.getPreferences().getValue(FOLDER_ACCESS_EDIT, "false")));
      thisCategory.setCanView(Boolean.parseBoolean(request.getPreferences().getValue(FOLDER_ACCESS_VIEW, "false")));
      request.setAttribute("Category", thisCategory);
      if (thisCategory.getRecordId() > -1) {
        thisRecord = new CustomFieldRecord(db, thisCategory.getRecordId());
        request.setAttribute("Record", thisRecord);
      }
      requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE4);
      requestDispatcher.include(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}