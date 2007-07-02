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
package org.aspcfs.modules.website.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.website.base.PageRoleMap;
import org.aspcfs.modules.website.base.PageRoleMapList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 * @author Aliaksei.Yarotski
 * 
 */
public class PageRoles extends CFSModule {
  /**
   * Logger.
   */
  private static final Logger LOGGER = Logger.getLogger(PageRoles.class.getName());

  public String executeCommandList(ActionContext context) {
    String moduleId = context.getRequest().getParameter("moduleId");
    Connection db = null;
    try {
      String pageId = context.getRequest().getParameter("pageId");      
      db = this.getConnection(context);
      PageRoleMapList pageRoleMapList = new PageRoleMapList();
      pageRoleMapList.setWebPageId(Integer.parseInt(pageId));
      pageRoleMapList.buildList(db);
      RoleList roleList = new RoleList();
      roleList.buildList(db);
      PermissionCategory permissionCategory = new PermissionCategory(db, Integer.parseInt(moduleId));
      
      context.getRequest().setAttribute("roleList", roleList);
      context.getRequest().setAttribute("moduleId", moduleId);
      context.getRequest().setAttribute("pageRoleMapList", pageRoleMapList);
      context.getRequest().setAttribute("permissionCategory", permissionCategory);
      context.getRequest().setAttribute("pageId",pageId);            
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      LOGGER.error(e, e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
      

    return ("ListOK");
  }

  public String executeCommandSave(ActionContext context) {
    String moduleId = context.getRequest().getParameter("moduleId");
    String pageId = context.getRequest().getParameter("pageId");
    String roles[] = context.getRequest().getParameterValues("role");
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      PageRoleMapList pageRoleMapList = new PageRoleMapList();
      pageRoleMapList.setWebPageId(Integer.parseInt(pageId));      
      pageRoleMapList.buildList(db);      
      pageRoleMapList.delete(db);
      if(roles!=null) {
        for(int i=0;i<roles.length;i++){
        PageRoleMap pageRoleMap = new PageRoleMap();
        pageRoleMap.setRoleId(Integer.parseInt(roles[i]));
        pageRoleMap.setWebPageId(Integer.parseInt(pageId));
        pageRoleMap.setEnteredBy(this.getUserId(context));
        pageRoleMap.setModifiedBy(this.getUserId(context));
        pageRoleMap.insert(db);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      LOGGER.error(e, e);
      return ("SystemError");
    } finally {
    	systemStatus.updateTabs(context.getServletContext(), db);
      this.freeConnection(context, db);
    }
    return ("SaveOK");
  }

}
