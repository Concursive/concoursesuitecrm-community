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

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import java.sql.Connection;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.controller.objectHookManager.*;

/**
 *  Admin Module: Object Events Editor web site actions
 */
public final class AdminObjectEvents extends CFSModule {

  /**
   *  This action prepares a list of processes for the selected module
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!hasPermission(context, "admin-object-workflow-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Object Events");
    Connection db = null;
    //Parameters
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      db = this.getConnection(context);
      //Load the category for the web trails
      PermissionCategory category = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", category);
      //Prepare the business process list
      BusinessProcessList list = new BusinessProcessList();
      list.setLinkModuleId(Integer.parseInt(moduleId));
      list.setTypeId(BusinessProcess.OBJECT_EVENT);
      list.buildList(db);
      context.getRequest().setAttribute("processList", list);
      //Prepare the hook list
      ObjectHookList hookList = new ObjectHookList();
      hookList.setLinkModuleId(Integer.parseInt(moduleId));
      hookList.buildList(db);
      context.getRequest().setAttribute("hookList", hookList);
      return "ListOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
  }
  
  /**
   *  This action prepares the selected workflow process components to be
   *  viewed.  AdminWorkflow must be chained to generate the process steps.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandWorkflow(ActionContext context) {
    if (!hasPermission(context, "admin-object-workflow-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Object Events");
    return "DetailsOK";
  }
}
