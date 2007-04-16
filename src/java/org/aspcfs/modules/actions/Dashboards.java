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

import java.sql.Connection;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.beans.ModuleBean;
import org.aspcfs.modules.website.base.Page;
import org.aspcfs.modules.website.base.PageList;
import org.aspcfs.modules.website.base.PageRowList;
import org.aspcfs.modules.website.framework.IceletManager;
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
			PageList dashboards = new PageList();
			dashboards.setDashboard(true);
			dashboards.setEnabled(1);
			dashboards.setLinkModuleId(Integer.parseInt(moduleId));
			User user = this.getUser(context, UserUtils.getUserId(context.getRequest()));
			dashboards.setRoleId(user.getRoleId());
			dashboards.buildList(db);
			
			if (dashboards.size() == 0) {
				return "NoItems";
			}
			
			Page page = (Page) dashboards.get(0);
			context.getRequest().setAttribute("dashboardList", dashboards);
			context.getRequest().setAttribute("selectedDashboard", page.getName());
			context.getRequest().setAttribute("Page", page);
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
   * @return
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
			Page page = null;
			if (dashboardId == null) {
				PageList dashboards = (PageList) context.getRequest().getAttribute("dashboardList");
				if (dashboards == null) {
					dashboards.setDashboard(true);
					dashboards.setLinkModuleId(Integer.parseInt(moduleId));
					dashboards.setEnabled(1);
					User user = this.getUser(context, UserUtils.getUserId(context.getRequest()));
					dashboards.setRoleId(user.getRoleId());
					dashboards.buildList(db);
				}
				page = dashboards.size() != 0 ? (Page) dashboards.get(0) : null;
			} else {
				page = new Page(db, Integer.parseInt(dashboardId));
			}
			page.buildPageVersionToView(db);
			page.getPageVersionToView().buildPageRowList(db);
			PageRowList pageRowList = page.getPageVersionToView().getPageRowList();
			ArrayList rowColumnList = new ArrayList();
			pageRowList.buildRowsColumns(rowColumnList, 0);
			IceletManager manager = IceletManager.getManager(context);
			manager.prepare(context, page, db);
			context.getRequest().setAttribute("Page", page);
			context.getRequest().setAttribute("rowsColumns", rowColumnList);
			context.getRequest().setAttribute("selectedDashboard", page.getName());
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
