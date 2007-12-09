/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.modules.actions;

import java.sql.Connection;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.beans.ModuleBean;
import org.aspcfs.utils.UserUtils;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 * Description of the Dashboards
 * 
 * @author Artem.Zakolodkin
 * @created Feb 14, 2007
 */
public class Dashboards extends CFSModule {
	/**
   * Logger.
   */
	private static final Logger LOGGER = Logger.getLogger(Dashboards.class.getName());
	
	/*
   * (non-Javadoc)
   * 
   * @see org.aspcfs.modules.actions.CFSModule#executeCommandDefault(com.darkhorseventures.framework.actions.ActionContext)
   */
	public String executeCommandDefault(ActionContext context) {
		String moduleId = (String) context.getRequest().getAttribute("moduleId");
		String action = (String) context.getRequest().getAttribute("action");
		if (action != null) {
			context.getRequest().setAttribute("action", action);
			ModuleBean thisModule = new ModuleBean();
			thisModule.setMenuKey(action.substring(0, action.lastIndexOf('.')));
			context.getRequest().setAttribute("ModuleBean", thisModule);
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			User user = this.getUser(context, UserUtils.getUserId(context.getRequest()));
			
		} catch (Exception e) {
			LOGGER.error(e, e);
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return executeCommandViewDashboard(context);
	}

	/**
   * @param context
   * @return String
   */
	public String executeCommandViewDashboard(ActionContext context) {
		String dashboardId = (String) context.getRequest().getAttribute("dashboardId");
		if (dashboardId == null) {
			dashboardId = (String) context.getRequest().getParameter("dashboardId");
		}
		String moduleId = (String) context.getRequest().getAttribute("moduleId");
		if (moduleId == null) {
			moduleId = (String) context.getRequest().getParameter("moduleId");
		}
		if (moduleId == null) {
			return "NoItems";
		}
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			ArrayList rowColumnList = new ArrayList();
			context.getRequest().setAttribute("rowsColumns", rowColumnList);
			context.getRequest().setAttribute("moduleId", moduleId);
			context.getRequest().setAttribute("viewContent", "true");
		} catch (Exception e) {
			LOGGER.error(e, e);
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		String menuName = (String) context.getRequest().getParameter("menu");
		if (menuName != null) {
			context.getRequest().setAttribute("menuName", menuName);
		}
		String action = (String) context.getRequest().getParameter("action");
		if (action != null) {
			context.getRequest().setAttribute("action", action);
			ModuleBean thisModule = new ModuleBean();
			thisModule.setMenuKey(action.substring(0, action.lastIndexOf('.')));
			context.getRequest().setAttribute("ModuleBean", thisModule);
		}
		return "ViewOK";
	}
}
