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
package org.aspcfs.modules.admin.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.website.actions.Pages;
import org.aspcfs.modules.website.base.*;
import org.aspcfs.modules.website.framework.IceletManager;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 * Description of the AdminDashboard.
 * 
 * @author Artem.Zakolodkin
 * @created Jan 8, 2007
 */
public final class AdminDashboards extends CFSModule {
  private static final String groupName = "dashboards";
	/**
   * Logger.
   */
  private static final Logger LOGGER = Logger.getLogger(AdminDashboards.class.getName());

  /**
   * list data preparation
   * 
   * @param context
   * @return
   */
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
    
  private String executeCommandList(ActionContext context, Connection db) throws NumberFormatException, SQLException {
		String moduleId = context.getRequest().getParameter("moduleId");
		String pageGroupId = (String) context.getSession().getAttribute("pageGroupId");
		PageList dashboardList = new PageList();
		dashboardList.setLinkModuleId(Integer.parseInt(moduleId));
		dashboardList.setDashboard(true);
		dashboardList.setPageGroupId(Integer.parseInt(pageGroupId));
		PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
		// Configure the pagedList
		PagedListInfo listInfo = getPagedListInfo(context, "dashboardListInfo");
		listInfo.setLink("AdminDashboards.do?command=List&moduleId=" + moduleId);
		dashboardList.setPagedListInfo(listInfo);
		dashboardList.buildList(db);
		Iterator i = dashboardList.iterator();
		int index = 0;
		while (i.hasNext()) {
			Page page = (Page) i.next();
			int previousId = ((Page) dashboardList.get(index > 0 ? index - 1 : 0)).getId() != page.getId() ? ((Page) dashboardList.get(index > 0 ? index - 1 : 0))
					.getId() : 0;
			page.setPreviousPageId(previousId);
			int nextId = ((Page) dashboardList.get(index < dashboardList.size() - 1 ? index + 1 : index)).getId() != page.getId() ? ((Page) dashboardList
					.get(index < dashboardList.size() - 1 ? index + 1 : index)).getId() : -1;
			page.setNextPageId(nextId);
			index = index < dashboardList.size() - 1 ? index + 1 : index;
		}
		context.getRequest().setAttribute("dashboardList", dashboardList);
		context.getRequest().setAttribute("permissionCategory", permissionCategory);
		return ("ListOK");
	}

  public String executeCommandAddDashboard(ActionContext context) {
    return executeCommandModifyDetails(context);
  }
  
  
  public String executeCommandModifyDetails(ActionContext context){
    String moduleId = context.getRequest().getParameter("moduleId");
    String dashboardId =  context.getRequest().getParameter("dashboardId");
    Connection db = null;    
    Page dashboard = (Page)context.getFormBean();    
    try {
      db = this.getConnection(context);
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      if(dashboard.getId()==-1&& dashboardId!=null&&!dashboardId.equals("-1")){
        dashboard.queryRecord(db,Integer.parseInt(dashboardId));        
      }
      context.getRequest().setAttribute("dashboard",dashboard);
      context.getRequest().setAttribute("permissionCategory", permissionCategory);            
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      LOGGER.error(e, e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }    
    return ("ModifyDetailsOK");
  }
  

   public String executeCommandSave(ActionContext context){ 
     String moduleId = context.getRequest().getParameter("moduleId");
     String pageGroupId = (String) context.getSession().getAttribute("pageGroupId");
     Connection db = null;
     Page dashboard = (Page)context.getFormBean();
     SystemStatus systemStatus = this.getSystemStatus(context);
     try {
       db = this.getConnection(context);
       dashboard.setLinkModuleId(Integer.parseInt(moduleId));
       dashboard.setModifiedBy(this.getUserId(context));
       dashboard.setPageGroupId(Integer.parseInt(pageGroupId));
       if(dashboard.getId()==-1){
         dashboard.setEnteredBy(this.getUserId(context));
      	 dashboard.setNextPageId(dashboard.getId());
         dashboard.setPosition(dashboard.computePagePosition(db));
         if (dashboard.insert(db)) {
        	 Pages pages = new Pages();
        	 pages.insertDefaultData(context, db, dashboard);
         }
       }else{
      	 dashboard.setOverride(true);
      	 dashboard.update(db);
       }
       systemStatus.updateTabs(context.getServletContext(), db);
       executeCommandList(context, db);
     } catch (Exception e) {
       context.getRequest().setAttribute("Error", e);
       LOGGER.error(e, e);
       return ("SystemError");
     } finally {
       this.freeConnection(context, db);
     }     
    return("SaveOK"); 
   }
   
   
   public String executeCommandConfirmDelete(ActionContext context) {
    String moduleId = context.getRequest().getParameter("moduleId");
    String dashboardId =  context.getRequest().getParameter("dashboardId");
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.setDeleteUrl("javascript:window.location.href='AdminDashboards.do?command=DeleteDashboard&dashboardId=" 
                                    + dashboardId + "&moduleId="+moduleId+"'");
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
   
   public String executeCommandDeleteDashboard(ActionContext context) {
    String moduleId = context.getRequest().getParameter("moduleId");
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      String dashboardId =  context.getRequest().getParameter("dashboardId");

      Page page = new Page(db, Integer.parseInt(dashboardId));
      page.buildPageVersionToView(db);
      page.getPageVersionToView().buildPageRowList(db);
      PageRowList pageRowList = page.getPageVersionToView().getPageRowList();
      ArrayList rowColumnList = new ArrayList();
      pageRowList.buildRowsColumns(rowColumnList, 0);
      // TODO: Cleanup various items centrally
      this.deleteFolderGraphImageFiles(context, rowColumnList);

      PageRoleMapList pageRoleMapList = new PageRoleMapList();
      pageRoleMapList.setWebPageId(Integer.parseInt(dashboardId));
      pageRoleMapList.buildList(db);
      pageRoleMapList.delete(db);
      
      PageList dashboardList = new PageList();
      dashboardList.setId(Integer.parseInt(dashboardId));
      dashboardList.setDashboard(true);
      dashboardList.buildList(db);
      dashboardList.delete(db);
      systemStatus.updateTabs(context.getServletContext(), db);
    }
    catch(Exception e){
       context.getRequest().setAttribute("Error", e);
       LOGGER.error(e, e);
       return ("SystemError");
    }
    finally {
      this.freeConnection(context, db);
    }
     context.getRequest().setAttribute("refreshUrl", "AdminDashboards.do?command=List&moduleId="+ moduleId);
     return getReturn(context, "Delete");
   }
   
   public String executeCommandConfigureDashboard(ActionContext context) {
  		String dashboardId = context.getRequest().getParameter("dashboardId");
  		Connection db = null;
  		try {
  			db = this.getConnection(context);
  			Page page = new Page(db, Integer.parseInt(dashboardId));
  			page.setMode(Site.EDIT_MODE);
  			page.buildPageVersionToView(db);
  			page.getPageVersionToView().buildPageRowList(db);
  			PageRowList pageRowList = page.getPageVersionToView().getPageRowList();
  			ArrayList rowColumnList = new ArrayList();
  			pageRowList.buildRowsColumns(rowColumnList, 0);
  			context.getRequest().setAttribute("rowsColumns", (ArrayList) rowColumnList);
  			context.getRequest().setAttribute("Page", page);
  			context.getRequest().setAttribute("viewContent", "false");
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
  	 return "AdminDashboards.do?command=ConfigureDashboard&dashboardId=" + context.getRequest().getParameter("dashboardId") + "&moduleId="
			+ context.getRequest().getParameter("moduleId") + "&popup=true";
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
  	String moduleId = context.getRequest().getParameter("moduleId");
		String dashboardId = context.getRequest().getParameter("dashboardId");
    String previousId = context.getRequest().getParameter("previousId");
    String nextId = context.getRequest().getParameter("nextId");
		
		boolean isUp = Boolean.parseBoolean((String) context.getRequest().getParameter("moveUp"));
		Connection db = null;
		try {
			db = this.getConnection(context);
      Page page = new Page(db, Integer.parseInt(dashboardId));
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
