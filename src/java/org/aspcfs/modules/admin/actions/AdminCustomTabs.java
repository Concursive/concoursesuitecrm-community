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
package org.aspcfs.modules.admin.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SubmenuItem;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.base.ContainerMenu;
import org.aspcfs.modules.base.ContainerMenuList;
import org.aspcfs.modules.website.actions.Pages;
import org.aspcfs.modules.website.base.Page;
import org.aspcfs.modules.website.base.PageGroup;
import org.aspcfs.modules.website.base.PageGroupList;
import org.aspcfs.modules.website.base.PageList;
import org.aspcfs.modules.website.base.PageRoleMapList;
import org.aspcfs.modules.website.base.PageRowList;
import org.aspcfs.modules.website.base.Site;
import org.aspcfs.modules.website.framework.IceletManager;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 * Description of the AdminCustomTabs
 * 
 * @author Artem.Zakolodkin
 * @created Feb 6, 2007
 */
public class AdminCustomTabs extends CFSModule {
	private static final String groupName = "customtabs";
	/**
   * Logger.
   */
	private static final Logger LOGGER = Logger.getLogger(AdminCustomTabs.class.getName());

  public String executeCommandList(ActionContext context) {
    //TODO: Add permission checking
    Connection db = null;
    String result = null; 
    try {
      db = this.getConnection(context);
      PageGroupList pageGroups = new PageGroupList();
      pageGroups.setGroupName(groupName);
      pageGroups.buildList(db);
      PageGroup pageGroup = pageGroups.size() > 0 ? (PageGroup) pageGroups.get(0): null; 
      if (pageGroups.size() == 0) {
      	pageGroup = new PageGroup();
      	pageGroup.setName(groupName);
      	pageGroup.setEnteredBy(this.getUserId(context));
      	pageGroup.setModifiedBy(this.getUserId(context));
      	pageGroup.setPosition(PageGroup.INITIAL_POSITION);
      	pageGroup.insert(db);
      }
      context.getSession().setAttribute("pageGroupId", Integer.toString(pageGroup.getId()));
      result = executeCommandList(context,db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      LOGGER.error(e, e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (result);
      
  }    

	
  public String executeCommandAddCustomTab(ActionContext context) {
    //TODO: Add permission checking
    return executeCommandModifyDetails(context);
  }

  /**
   * @param context
   * @return
   */
  public String executeCommandModifyDetails(ActionContext context) {
    //TODO: Add permission checking
    String moduleId = context.getRequest().getParameter("moduleId");
    String customtabId = context.getRequest().getParameter("customtabId");
    Connection db = null;
    
    Page customTab = (Page)context.getFormBean();
    try {
      db = this.getConnection(context);
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      ContainerMenuList containerMenuList = new ContainerMenuList();
      containerMenuList.setLinkModuleId(Integer.parseInt(moduleId));
      containerMenuList.buildList(db);
      
      if(customTab.getId() == -1 && customtabId != null && !customtabId.equals("-1")){
      	customTab.queryRecord(db,Integer.parseInt(customtabId));        
      }

      context.getRequest().setAttribute("customtab", customTab);
      context.getRequest().setAttribute("previousLinkContainerId", String.valueOf(customTab.getLinkContainerId()));
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      context.getRequest().setAttribute("containerMenuList", containerMenuList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      LOGGER.error(e, e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AddCustomTabOK");
  }
  
  private String executeCommandList(ActionContext context,Connection db) throws NumberFormatException, SQLException {
  	//TODO: Add permission checking
    String moduleId = context.getRequest().getParameter("moduleId");
		String linkContainerId = context.getRequest().getParameter("containerId");
		String pageGroupId = (String) context.getSession().getAttribute("pageGroupId");
		ContainerMenuList containerMenuList = new ContainerMenuList();
		containerMenuList.setLinkModuleId(Integer.parseInt(moduleId));
		containerMenuList.buildList(db);
		PageList customTabList = new PageList();
		if (linkContainerId != null && !"".equals(linkContainerId)) {
			customTabList.setLinkContainerId(Integer.parseInt(linkContainerId));
		}
		customTabList.setCustomTab(true);
		customTabList.setPageGroupId(Integer.parseInt(pageGroupId));
		PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
		// Configure the pagedList
		PagedListInfo listInfo = getPagedListInfo(context, "customTabListInfo");
		listInfo.setLink("AdminCustomTab.do?command=List&moduleId=" + moduleId);
		customTabList.setPagedListInfo(listInfo);
		customTabList.buildList(db);
		Iterator i = customTabList.iterator();
		PageList tmp = new PageList();
		while (i.hasNext()) {
			Page page = (Page) i.next();
			ContainerMenuList tmpList = new ContainerMenuList();
			tmpList.setLinkModuleId(Integer.parseInt(moduleId));
			tmpList.setContainerId(page.getLinkContainerId());
			tmpList.buildList(db);
			if (tmpList.size() == 0) {
				tmp.add(page);
			}
		}
		customTabList.removeAll(tmp);
		i = customTabList.iterator();
		int index = 0;
		while (i.hasNext()) {
			Page page = (Page) i.next();
			int previousId = ((Page) customTabList.get(index > 0 ? index - 1 : 0)).getId() != page.getId() ? ((Page) customTabList.get(index > 0 ? index - 1 : 0))
					.getId() : 0;
			page.setPreviousPageId(previousId);
			int nextId = ((Page) customTabList.get(index < customTabList.size() - 1 ? index + 1 : index)).getId() != page.getId() ? ((Page) customTabList
					.get(index < containerMenuList.size() - 1 ? index + 1 : index)).getId() : -1;
			page.setNextPageId(nextId);
			index = index < customTabList.size() - 1 ? index + 1 : index;
		}
		context.getRequest().setAttribute("customTabList", customTabList);
		context.getRequest().setAttribute("permissionCategory", permissionCategory);
		context.getRequest().setAttribute("containerMenuList", containerMenuList);
		return ("ListOK");
  }
  
  
  public String executeCommandSave(ActionContext context) {
    //TODO: Add permission checking
    String pageGroupId = (String) context.getSession().getAttribute("pageGroupId");
    int previousLinkContainerId = Integer.parseInt(context.getRequest().getParameter("previousLinkContainerId"));
		Connection db = null;
		Page customTab = (Page) context.getFormBean();
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);
			customTab.setModifiedBy(this.getUserId(context));
			customTab.setPageGroupId(Integer.parseInt(pageGroupId));
			if (customTab.getId() == -1) {
				customTab.setEnteredBy(this.getUserId(context));
				customTab.setNextPageId(customTab.getId());
				customTab.setPageGroupId(Integer.parseInt(pageGroupId));
				customTab.setPosition(customTab.computePagePosition(db));
				if (customTab.insert(db)) {
					Pages pages = new Pages();
					pages.insertDefaultData(context, db, customTab);
				}
			} else {
				customTab.setOverride(true);
				customTab.update(db);
			}
			LinkedHashMap menu = systemStatus.getMenu();
			previousLinkContainerId = previousLinkContainerId == -1 ? customTab.getLinkContainerId() : previousLinkContainerId;
			ContainerMenu container = new ContainerMenu(db, previousLinkContainerId);
			LinkedList menuItems = (LinkedList) menu.get(container.getCname());
			LinkedList tmp = new LinkedList();
			Iterator j = menuItems.iterator();
			while (j.hasNext()) {
				SubmenuItem menuItem = (SubmenuItem) j.next();
				if (menuItem.isCustomTab()) {
					tmp.add(menuItem);
				}
			}
			menuItems.removeAll(tmp);
			systemStatus.updateTabs(context.getServletContext(), db);
			executeCommandList(context, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			LOGGER.error(e, e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("SaveOK");
	}
  
  public String executeCommandConfirmDelete(ActionContext context) {
    //TODO: Add permission checking
    String moduleId = context.getRequest().getParameter("moduleId");
  	int customtabId = Integer.parseInt(context.getRequest().getParameter("customtabId"));
  	Connection db = null;
  	HtmlDialog htmlDialog = new HtmlDialog();
  	try {
  		db = this.getConnection(context);
  		SystemStatus systemStatus = this.getSystemStatus(context);
  		htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
  		htmlDialog.setDeleteUrl("javascript:window.location.href='AdminCustomTabs.do?command=DeleteCustomTab&customtabId=" + customtabId + "&moduleId="+moduleId+"'");
  		htmlDialog.setShowAndConfirm(false);
  	}
  	catch(Exception e){
      context.getRequest().setAttribute("Error", e);
      LOGGER.error(e, e);
      return ("SystemError");
  	}
  	finally {
  		this.freeConnection(context, db);
  	}
  	context.getSession().setAttribute("Dialog", htmlDialog);
  	return ("ConfirmDeleteOK");
  }
  public String executeCommandDeleteCustomTab(ActionContext context) {
    //TODO: Add permission checking
    String moduleId = context.getRequest().getParameter("moduleId");
  	Connection db = null;
  	try {
  		db = this.getConnection(context);
  		int customtabId = Integer.parseInt(context.getRequest().getParameter("customtabId"));
  		PageRoleMapList pageRoleMapList = new PageRoleMapList();
  		pageRoleMapList.setWebPageId(customtabId);
  		pageRoleMapList.buildList(db);
  		pageRoleMapList.delete(db);
  		
  		PageList customTabList = new PageList();
  		customTabList.setId(customtabId);
  		customTabList.setCustomTab(true);
  		customTabList.buildList(db);
  		customTabList.delete(db);
  	}
  	catch(Exception e){
      context.getRequest().setAttribute("Error", e);
      LOGGER.error(e, e);
      return ("SystemError");
  	}
  	finally {
  		this.freeConnection(context, db);
  	}
    context.getRequest().setAttribute("refreshUrl", "AdminCustomTabs.do?command=List&moduleId="+ moduleId);
    return getReturn(context, "Delete");
  }
  
  public String executeCommandConfigureCustomTab(ActionContext context) {
    //TODO: Add permission checking
    String customtabId = context.getRequest().getParameter("customtabId");
		Connection db = null;
		try {
			db = this.getConnection(context);
			Page page = new Page(db, Integer.parseInt(customtabId));
			page.setMode(Site.EDIT_MODE);
			page.buildPageVersionToView(db);
			page.getPageVersionToView().buildPageRowList(db);
			PageRowList pageRowList = page.getPageVersionToView().getPageRowList();
			ArrayList rowColumnList = new ArrayList();
			pageRowList.buildRowsColumns(rowColumnList, 0);
			context.getRequest().setAttribute("rowsColumns", (ArrayList) rowColumnList);
			context.getRequest().setAttribute("Page", page);
			context.getRequest().setAttribute("portal", "false");
      IceletManager manager = IceletManager.getManager(context);
      manager.prepare(context, page, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			LOGGER.error(e, e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "Configure");
	}

 /**
  * Refresh URL for the form while deleting item (Row, Column) from dashboard.
  * 
 * @param context
 * @return
 */
private String getRefreshUrl(ActionContext context) {
   return "AdminCustomTabs.do?command=ConfigureDashboard&customtabId=" + context.getRequest().getParameter("customtabId") + "&moduleId="
		+ context.getRequest().getParameter("moduleId") + "&containerId="+ context.getRequest().getParameter("containerId") + "&popup=true";
 }
 /**
 * @param context
 * @return
 */
public String executeCommandDeleteColumn(ActionContext context) {
	context.getSession().setAttribute("refreshUrl", getRefreshUrl(context));
	return getReturn(context, "DeleteColumn");
}
 
 /**
 * @param context
 * @return
 */
public String executeCommandDeleteRow(ActionContext context) {
	context.getSession().setAttribute("refreshUrl", getRefreshUrl(context));
	return getReturn(context, "DeleteRow");
 }

public String executeCommandMove(ActionContext context) {
  //TODO: Add permission checking
  String moduleId = context.getRequest().getParameter("moduleId");
	String customtabId = context.getRequest().getParameter("customtabId");
  String previousId = context.getRequest().getParameter("previousId");
  String nextId = context.getRequest().getParameter("nextId");
	
	boolean isUp = Boolean.parseBoolean((String) context.getRequest().getParameter("moveUp"));
	Connection db = null;
	try {
		db = this.getConnection(context);
    Page page = new Page(db, Integer.parseInt(customtabId));
    page.setLinkModuleId(Integer.parseInt(moduleId));
    page.setPreviousPageId(previousId);
    page.setNextPageId(nextId);
    page.move(db, isUp);
		
	} catch (Exception e) {
		context.getRequest().setAttribute("Error", e);
		LOGGER.error(e, e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	return executeCommandList(context);
}
}
