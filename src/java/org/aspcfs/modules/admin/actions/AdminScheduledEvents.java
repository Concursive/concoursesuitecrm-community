package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import java.sql.Connection;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.apps.workFlowManager.*;

/**
 *  Admin Module: Scheduled Events Editor web site actions
 *
 *@author     matt rajkowski
 *@created    June 19, 2003
 *@version    $Id$
 */
public final class AdminScheduledEvents extends CFSModule {

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
    addModuleBean(context, "Configuration", "Scheduled Events");
    Connection db = null;
    //Parameters
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      db = this.getConnection(context);
      //Load the category for the web trails
      PermissionCategory category = new PermissionCategory(db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", category);
      //Prepare the content
      BusinessProcessList list = new BusinessProcessList();
      list.setLinkModuleId(Integer.parseInt(moduleId));
      list.setTypeId(BusinessProcess.SCHEDULED_EVENT);
      list.buildList(db);
      context.getRequest().setAttribute("processList", list);
      //Load the schedule for this module's processes
      //BusinessProcessEventList events = new BusinessProcessEventList();

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
    addModuleBean(context, "Configuration", "Scheduled Events");
    return "DetailsOK";
  }

}

