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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.ContainerMenu;
import org.aspcfs.modules.base.ContainerMenuList;
import org.aspcfs.modules.beans.ModuleBean;
import org.aspcfs.modules.website.base.Page;
import org.aspcfs.modules.website.base.PageRowList;
import org.aspcfs.modules.website.framework.IceletManager;
import org.aspcfs.taglib.ContainerMenuClass;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 * Description of the CustomTabs
 *
 * @author Artem.Zakolodkin
 * @created Feb 23, 2007
 */
public class CustomTabs extends CFSModule {
	
	/**
   * Logger.
   */
	private static final Logger LOGGER = Logger.getLogger(CustomTabs.class.getName());
	
	/* (non-Javadoc)
	 * @see org.aspcfs.modules.actions.CFSModule#executeCommandDefault(com.darkhorseventures.framework.actions.ActionContext)
	 */
	public String executeCommandDefault(ActionContext context) {
		return executeCommandViewCustomTab(context);
	}
	
	public String executeCommandViewCustomTab(ActionContext context) {
    HashMap stack = (HashMap) context.getSession().getAttribute("stack");
    String action = context.getRequest().getParameter("action");
		if (action != null) {
			ModuleBean thisModule = new ModuleBean();
			thisModule.setMenuKey(action);
			context.getRequest().setAttribute("ModuleBean", thisModule);
		}
		String customtabId = (String) context.getRequest().getAttribute("customtabId");
		if (customtabId == null) {
			customtabId = (String) context.getRequest().getParameter("customtabId");
		}
		String moduleId = (String) context.getRequest().getAttribute("moduleId");
		if (moduleId == null) {
			moduleId = (String) context.getRequest().getParameter("moduleId");
		}
		
		if (moduleId == null || stack == null) {
			return "NoItems";
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			Page page = new Page(db, Integer.parseInt(customtabId));
			page.buildPageVersionToView(db);
			page.getPageVersionToView().buildPageRowList(db);
			PageRowList pageRowList = page.getPageVersionToView().getPageRowList();
			ArrayList rowColumnList = new ArrayList();
			pageRowList.buildRowsColumns(rowColumnList, 0);
			IceletManager manager = IceletManager.getManager(context);
			manager.prepare(context, page, db);
			
			ContainerMenuList container = new ContainerMenuList();
			container.setContainerId(page.getLinkContainerId());
			container.buildList(db);
			String containerName = ((ContainerMenu)container.get(0)).getCname();
			context.getRequest().setAttribute("Page", page);
			context.getRequest().setAttribute("rowsColumns", rowColumnList);
			context.getRequest().setAttribute("selectedCustomTab", page.getName());
			context.getRequest().setAttribute("moduleId", moduleId);
			context.getRequest().setAttribute("containerName", containerName);
			ContainerMenuClass dhvcontainer = (ContainerMenuClass) stack.get(containerName);
			dhvcontainer.setSelected(page.getName());
			ContainerMenuClass tmpcont = dhvcontainer;
			Stack dhvcontainers = new Stack();
			HashMap tmp = new HashMap();
			while (tmpcont != null) {
				dhvcontainers.push(tmpcont);
				tmp.put(tmpcont.getName(), tmpcont);
				tmpcont = tmpcont.getParent();
			}
			stack.clear();
			stack.putAll(tmp);
			context.getRequest().setAttribute("dhvcontainers", dhvcontainers);
		} catch (Exception e) {
			LOGGER.error(e, e);
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "ViewOK";
	}
}
