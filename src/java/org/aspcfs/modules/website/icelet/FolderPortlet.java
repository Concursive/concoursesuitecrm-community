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
import org.aspcfs.utils.FolderUtils;
import org.aspcfs.utils.web.PagedListInfo;
import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.HashMap;

/**
 * FolderPortlet class to render folder records into portlet
 *
 * @author rajendra
 * @author nagaraja
 * @version $Id: FolderPortlet.java 4.1 2007-04-06 11:31:46 +0530 (Fri, 06 Apr 2007) rajendrad and nagarajay Exp $
 * @created April 06, 2007
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
	private final static String VIEW_PAGE5 = "/portlets/folder/fields_delete_error.jsp";
	
	/**
	 * doView method of FolderPortlet
	 *
	 * @param request  RenderRequest
	 * @param response RenderResponse
	 */
	public void doView(RenderRequest request, RenderResponse response)
	throws PortletException, IOException {
		String viewType = null;
		int renderPortlet = Constants.ERROR;
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
			buildFolderList(request, response, renderPortlet);
			requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE1);
			requestDispatcher.include(request, response);
		} else {
			renderPortlet = buildFolderList(request, response, renderPortlet);
			if(renderPortlet == Constants.SUCCESS) {
				requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE1);
			} else if(renderPortlet == Constants.ERROR) {
				requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE5);
			}
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
	private int buildFolderList(RenderRequest request, RenderResponse response, int renderPortlet)
	throws PortletException, IOException {
		CustomFieldCategory thisCategory = null;
		Connection db;
		ArrayList displayInList = new ArrayList();
		int categoryId = 0;
		CustomField thisField = null;
		CustomFieldGroup thisGroup = null;
		CustomFieldRecordList recordList = null;
		ArrayList recordCategories = new ArrayList();
		int noOfRecords = 0;
		PagedListInfo recordListInfo = null;
		
		try {
			String field = null;
			boolean valid = true;
			db = PortletUtils.getConnection(request);
			categoryId = Integer.parseInt(request.getPreferences().getValue(SELECT_FOLDER, "1"));
			thisCategory = new CustomFieldCategory(db, categoryId);
			thisCategory.setCanAdd(Boolean.parseBoolean(request.getPreferences().getValue(FOLDER_ACCESS_ADD, "false")));
			thisCategory.setCanView(Boolean.parseBoolean(request.getPreferences().getValue(FOLDER_ACCESS_VIEW, "false")));
			thisCategory.setCanEdit(Boolean.parseBoolean(request.getPreferences().getValue(FOLDER_ACCESS_EDIT, "false")));
			thisCategory.setCanDelete(Boolean.parseBoolean(request.getPreferences().getValue(FOLDER_ACCESS_DELETE, "false")));
			thisCategory.setDoAverage(Boolean.parseBoolean(request.getPreferences().getValue(AGGREGATE_AVERAGE, "false")));
			thisCategory.setDoTotal(Boolean.parseBoolean(request.getPreferences().getValue(AGGREGATE_TOTAL, "false")));
			noOfRecords = Integer.parseInt(request.getPreferences().getValue(NUMBER_OF_RECORDS, "false"));
			String fieldList = request.getPreferences().getValue(FIELDS_TO_DISPLAY, "false");
			thisCategory.setBuildResources(true);
			thisCategory.buildResources(db);
			
			if(fieldList != null && !fieldList.equals("")){
				String commSeperatValues[] = fieldList.split(",");
				for(int i = 0;i < commSeperatValues.length;i++){					
					field = commSeperatValues[i].substring(0,commSeperatValues[i].indexOf(":"));					
					if(FolderUtils.fieldExists(field, thisCategory)) {
						displayInList.add(new Integer(commSeperatValues[i].substring(0,commSeperatValues[i].indexOf(":"))));
					} else {
						valid = false;
					}
				}
			}
			if(valid) {
				thisCategory.setDisplayInList(displayInList);
				thisCategory.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
				db = PortletUtils.getConnection(request);
				recordList = new CustomFieldRecordList();
				recordList.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
				recordList.setCategoryId(thisCategory.getId());
				recordList.buildList(db);
				recordList.buildRecordColumns(db, thisCategory);
				int count = 0;
				int offset = 0;
				CustomFieldRecord thisRecord;
				CustomFieldCategory recordCategory;
				recordListInfo = PortletUtils.getPagedListInfo(request, response, "recordListInfo");
				recordListInfo.setMode(PagedListInfo.LIST_VIEW);
				recordListInfo.setMaxRecords(recordList.size());
				recordListInfo.setItemsPerPage(noOfRecords);
				recordListInfo.setRenderParameters(new HashMap());
				recordList.setPagedListInfo(recordListInfo);
				offset = request.getParameter("offset") != null ? Integer.parseInt(request.getParameter("offset")) :
					request.getPortletSession().getAttribute("offset") != null ? (Integer) request.getPortletSession().getAttribute("offset") : 0;
					Iterator records = recordList.listIterator(offset);
					while (records.hasNext() && count < noOfRecords) {
						thisRecord = (CustomFieldRecord) records.next();
						recordCategory = new CustomFieldCategory(db, thisCategory.getId());
						recordCategory.setRecordId(thisRecord.getId());
						recordCategory.setBuildResources(true);
						recordCategory.buildResources(db);
						recordCategories.add(recordCategory);
						count++;
					}
					thisCategory.doAggregateFunctions(db, noOfRecords, offset);
					ArrayList list = thisCategory.getDisplayInList();
					HashMap totalMap = new HashMap();
					HashMap avgMap = new HashMap();
					Iterator iterator = list.iterator();
					int fieldId = 0, key = 1;      
					while(iterator.hasNext()) {      	
						fieldId = (Integer)iterator.next();
						totalMap.put(key, thisCategory.getFieldTotal(db, fieldId));
						avgMap.put(key, thisCategory.getFieldAverage(db, fieldId));
						key++;
					}
					request.setAttribute("totalMap", totalMap);
					request.setAttribute("avgMap", avgMap);
					request.getPortletSession().setAttribute("offset", offset);
					
					int temp = 0;
					String randomNum = Integer.toString((temp = new Random().nextInt()) > 0 ? temp / 10000 : (-temp / 10000));
					request.setAttribute("randomNum", randomNum);
					request.setAttribute("Category", thisCategory);
					request.setAttribute("noOfRecords", request.getPreferences().getValue("NUMBER_OF_RECORDS", "0"));
					request.setAttribute("records", recordList);
					request.setAttribute("recordListInfo", recordListInfo);
					request.setAttribute("recordCategories", recordCategories);
					request.setAttribute("systemStatus", PortletUtils.getSystemStatus(request));
					if(request.getPortletSession().getAttribute("isRecordId") != null) {
						request.setAttribute("isRecordId",request.getPortletSession().getAttribute("isRecordId"));
						request.getPortletSession().removeAttribute("isRecordId");
					}		
					renderPortlet = Constants.SUCCESS;					
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return renderPortlet;
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
			request.setAttribute("Category", request.getPortletSession().getAttribute("Category") != null ? request.getPortletSession().getAttribute("Category") : thisCategory);
			request.setAttribute("systemStatus", PortletUtils.getSystemStatus(request));
			request.getPortletSession().removeAttribute("Category");
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
			newCategory.setBuildResources(true);
			newCategory.buildResources(db);
			newCategory.setParameters((HttpServletRequest) request);
			newCategory.setEnteredBy(PortletUtils.getUser(request).getActualUserId());
			newCategory.setModifiedBy(PortletUtils.getUser(request).getActualUserId());
			if (!newCategory.getReadOnly()) {
				newCategory.setCanNotContinue(true);
				resultCode = newCategory.insert(db);
				Iterator groups = newCategory.iterator();
				while (groups.hasNext()) {
					CustomFieldGroup group = (CustomFieldGroup) groups.next();
					Iterator fields = group.iterator();
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
				if (resultCode == -1) {
					response.setRenderParameter("viewType", "add");
					request.getPortletSession().setAttribute("Category", newCategory);
					CustomFieldRecord thisRecord = new CustomFieldRecord();
					thisRecord.setLinkModuleId(newCategory.getModuleId());
					thisRecord.setCategoryId(newCategory.getId());
					thisRecord.setId(newCategory.getRecordId());
					thisRecord.delete(db);
					
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
		CustomFieldRecordList recordList = null;
		CustomFieldRecord thisRecord = null;
		Connection db;
		PortletRequestDispatcher requestDispatcher;
		int categoryId = Integer.parseInt(request.getPreferences().getValue(SELECT_FOLDER, "1"));
		String recordId = request.getParameter("recordId");
		db = PortletUtils.getConnection(request);
		try {
			thisCategory = new CustomFieldCategory(db, categoryId);
			thisCategory.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
			thisCategory.setRecordId(Integer.parseInt(recordId));
			thisCategory.setIncludeEnabled(Constants.TRUE);
			thisCategory.setIncludeScheduled(Constants.TRUE);
			thisCategory.setBuildResources(true);
			thisCategory.buildResources(db);
			
			recordList = new CustomFieldRecordList();
			recordList.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
			recordList.setCategoryId(thisCategory.getId());
			recordList.buildList(db);
			recordList.buildRecordColumns(db, thisCategory);
			Iterator itrRecordLst = recordList.iterator();
			while(itrRecordLst.hasNext()) {
				thisRecord = (CustomFieldRecord) itrRecordLst.next();
				if(thisRecord.getId() == Integer.parseInt(recordId))
					break;
			}
			request.setAttribute("Category", request.getPortletSession().getAttribute("Category") != null ? (CustomFieldCategory) request.getPortletSession().getAttribute("Category") : thisCategory);
			request.setAttribute("systemStatus", PortletUtils.getSystemStatus(request));
			request.setAttribute("beforeModifyTimeStamp", thisRecord.getModified().toString());
			request.getPortletSession().removeAttribute("Category");
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
		CustomFieldRecordList recordList = null;
		CustomFieldRecord thisRecord = null;
		String isRecordId = "false";
		Connection db;
		int resultCode;
		try {
			db = PortletUtils.getConnection(request);
			int categoryId = Integer.parseInt(request.getPreferences().getValue(SELECT_FOLDER, "1"));
			thisCategory = new CustomFieldCategory(db, categoryId);
			String recordId = request.getParameter("recordId");
			String beforeModifyTimeStamp = request.getParameter("beforeModifyTimeStamp");
			
			recordList = new CustomFieldRecordList();
			recordList.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
			recordList.setCategoryId(thisCategory.getId());
			recordList.buildList(db);
			recordList.buildRecordColumns(db, thisCategory);
			Iterator itrRecordLst = recordList.iterator();
			while(itrRecordLst.hasNext()) {
				thisRecord = (CustomFieldRecord) itrRecordLst.next();
				if(thisRecord.getId() == Integer.parseInt(recordId))
					break;
			}
			request.getPortletSession().setAttribute("isRecordId",isRecordId);
			
			if(thisRecord.getModified().toString().equals(beforeModifyTimeStamp)) {
				thisCategory.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
				thisCategory.setIncludeEnabled(Constants.TRUE);
				thisCategory.setIncludeScheduled(Constants.TRUE);
				thisCategory.setRecordId(Integer.parseInt(recordId));
				thisCategory.setBuildResources(true);
				thisCategory.buildResources(db);
				thisCategory.setParameters((HttpServletRequest) request);
				thisCategory.setEnteredBy(PortletUtils.getUser(request).getActualUserId());
				thisCategory.setModifiedBy(PortletUtils.getUser(request).getActualUserId());
				if (!thisCategory.getReadOnly()) {
					thisCategory.setCanNotContinue(true);
					Iterator groups = thisCategory.iterator();
					ArrayList valid = new ArrayList();
					while (groups.hasNext()) {
						CustomFieldGroup group = (CustomFieldGroup) groups.next();
						Iterator fields = group.iterator();
						while (fields.hasNext()) {
							CustomField field = (CustomField) fields.next();
							valid.add(Boolean.toString(field.isValid()));
						}
					}
					if (!valid.contains("false")) {
						resultCode = thisCategory.update(db);
						groups = thisCategory.iterator();
						while (groups.hasNext()) {
							CustomFieldGroup group = (CustomFieldGroup) groups.next();
							Iterator fields = group.iterator();
							while (fields.hasNext()) {
								CustomField field = (CustomField) fields.next();
								field.setValidateData(true);
								field.setRecordId(thisCategory.getRecordId());
							}
						}
						thisCategory.setCanNotContinue(false);
						if (resultCode != -1) {
							resultCode = thisCategory.insertGroup(db, thisCategory.getRecordId());
						}
					} else {
						request.getPortletSession().setAttribute("Category", thisCategory);
						response.setRenderParameter("recordId", Integer.toString(thisCategory.getRecordId()));
						response.setRenderParameter("viewType", "edit");
					}
				}
			} else {
				isRecordId = recordId;
				request.getPortletSession().setAttribute("isRecordId",isRecordId);
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