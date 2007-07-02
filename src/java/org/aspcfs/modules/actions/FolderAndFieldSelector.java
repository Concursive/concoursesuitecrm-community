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
package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.base.*;
import java.sql.Connection;
import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 * This class is used for getting Folder and it's field information
 *
 * @author dharmas
 * @version $Id: FolderAndFieldSelector.java 4.1 2007-03-29 11:31:35 +0530 (Thu, 29 Mar 2007) dharmas Exp $
 * @created Mar 29, 2007
 */
public final class FolderAndFieldSelector extends CFSModule {		
	
	/**
	 * Will retrieve the fields for the specified folder
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandFieldSelect(ActionContext context) {		
		Connection db = null;
		String folderId = context.getRequest().getParameter("folderId");
		
		ArrayList ignoredList = new ArrayList();
		ArrayList selectedList = new ArrayList();
		CustomFieldList finalCategories = null;
		String listType = context.getRequest().getParameter("listType");
		boolean listDone = false;
		
		String prevSelection = context.getRequest().getParameter(
		"previousSelection");
		if (prevSelection != null) {
			StringTokenizer st = new StringTokenizer(prevSelection, "|");
			while (st.hasMoreTokens()) {
				selectedList.add(String.valueOf(st.nextToken()));
			}
		}
		
		String ignoreIds = context.getRequest().getParameter("ignoreIds");
		if (ignoreIds != null) {
			StringTokenizer st = new StringTokenizer(ignoreIds, "|");
			while (st.hasMoreElements()) {
				ignoredList.add(String.valueOf(st.nextToken()));
			}
		}
		try {
			db = this.getConnection(context);
			
			int rowCount = 1;
			
			if ("list".equals(listType)) {
				while (context.getRequest().getParameter(
						"hiddenFolderFieldId" + rowCount) != null) {
					int fieldId = Integer.parseInt(
							context.getRequest().getParameter("hiddenFolderFieldId" + rowCount));
					if (context.getRequest().getParameter("field" + rowCount) != null) {
						if (!selectedList.contains(String.valueOf(fieldId))) {
							selectedList.add(String.valueOf(fieldId));
						}
					} else {
						selectedList.remove(String.valueOf(fieldId));
					}
					rowCount++;
				}
			}
			
			if ("true".equals(
					(String) context.getRequest().getParameter("finalsubmit"))) {
				//Handle single selection case
				if ("single".equals(listType)) {
					rowCount = Integer.parseInt(
							context.getRequest().getParameter("rowcount"));
					int fieldId = Integer.parseInt(
							context.getRequest().getParameter("hiddenFolderFieldId" + rowCount));
					selectedList.clear();
					selectedList.add(String.valueOf(fieldId));
				}
				listDone = true;
				if (finalCategories == null) {
					finalCategories = new CustomFieldList();
				}
				for (int i = 0; i < selectedList.size(); i++) {
					int fieldId = Integer.parseInt((String) selectedList.get(i));
					finalCategories.add(new CustomField(db, fieldId));
				}
			}
			CustomFieldList fieldList = new CustomFieldList();
			if (folderId != null && !"".equals(folderId.trim()) && Integer.parseInt(
					folderId) != -1) {
				fieldList.setCategoryId(folderId);
			}
			fieldList.buildList(db);
			context.getRequest().setAttribute("fieldNameList", fieldList);
		} catch (Exception e) {
				e.printStackTrace();
				context.getRequest().setAttribute("Error", e);
				return ("SystemError");
		} finally {
				this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("SelectedCategories", selectedList);
		context.getRequest().setAttribute("IgnoredCategories", ignoredList);
		if (listDone) {
			context.getRequest().setAttribute("FinalCategories", finalCategories);
		}
		return ("FieldSelectPopupOK");
	}
	
	/**
	 * Will retrieve the fields for the specified folder and the type in which they need to be rendered for the graph.
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandFieldListAndType(ActionContext context) {		
		Connection db = null;
		String folderId = context.getRequest().getParameter("folderId");
		
		try {
			db = this.getConnection(context);
			CustomFieldList fieldList = new CustomFieldList();
			fieldList.setCategoryId(folderId);
			fieldList.buildList(db);
			GraphTypeList graphList = new GraphTypeList();
			graphList.setTableName("lookup_graph_type");
			graphList.setEnabledState(1);
			graphList.buildList(db);
			context.getRequest().setAttribute("fieldNameList", fieldList);
			context.getRequest().setAttribute("graphTypeList", graphList);
		} catch (Exception e) {
				e.printStackTrace();
				context.getRequest().setAttribute("Error", e);
				return ("SystemError");
		} finally {
				this.freeConnection(context, db);
		}
		return ("FieldAndTypeListPopupOK");
	}
}
