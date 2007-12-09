/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.apps.workFlowManager.BusinessProcess;
import org.aspcfs.apps.workFlowManager.BusinessProcessList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;

import java.sql.Connection;

/**
 * Admin Module: Scheduled Events Editor web site actions
 *
 * @author matt rajkowski
 * @version $Id$
 * @created June 19, 2003
 */
public final class AdminScheduledEvents extends CFSModule {

  /**
   * This action prepares a list of processes for the selected module
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!hasPermission(context, "admin-object-workflow-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Scheduled Events");
    Connection db = null;
    //Parameters
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      db = this.getConnection(context);
      //Load the category for the web trails
      PermissionCategory category = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", category);
      //Prepare the content
      BusinessProcessList list = new BusinessProcessList();
      list.setLinkModuleId(Integer.parseInt(moduleId));
      list.setTypeId(BusinessProcess.SCHEDULED_EVENT);
      list.setBuildScheduledEvents(true);
      list.buildList(db);
      context.getRequest().setAttribute("processList", list);
      return "ListOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * This action prepares the selected workflow process components to be
   * viewed.  AdminWorkflow must be chained to generate the process steps.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandWorkflow(ActionContext context) {
    if (!hasPermission(context, "admin-object-workflow-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Scheduled Events");
    return "DetailsOK";
  }

}

