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
package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.troubletickets.base.TicketTask;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 *  Represents an Accounts Ticket Tasks
 *
 *@author     Mathur
 *@created    June 9, 2003
 *@version    $id: exp$
 */
public final class AccountTicketTasks extends CFSModule {

  /**
   *  Lists the tasks for the specified ticket
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-tasks-view")) {
      return ("PermissionError");
    }
    // Parameters
    String ticketId = context.getRequest().getParameter("ticketId");
    TaskList taskList = new TaskList();
    Connection db = null;
    // Paged List
    PagedListInfo ticTaskListInfo = this.getPagedListInfo(context, "AccountTicketTaskListInfo");
    ticTaskListInfo.setItemsPerPage(0);
    try {
      db = this.getConnection(context);
      //Load the task list
      taskList.setTicketId(Integer.parseInt(ticketId));
      taskList.setPagedListInfo(ticTaskListInfo);
      taskList.buildList(db);
      context.getRequest().setAttribute("TaskList", taskList);
      //Load the ticket
      Ticket thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("TicketDetails", thisTicket);
      //Load the organization
      Organization thisOrganization = new Organization(db, thisTicket.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      addModuleBean(context, "View Accounts", "List Tasks");
      return getReturn(context, "ListTasks");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-tickets-tasks-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Task thisTask = null;
    String id = context.getRequest().getParameter("id");
    try{
      db = this.getConnection(context);
      thisTask = new Task(db, Integer.parseInt(id));
      context.getRequest().setAttribute("Task", thisTask);
    }catch (Exception e){
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "View Tickets");
    return getReturn(context, "TaskDetails");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-tickets-tasks-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean recordInserted = false;
    boolean isValid = false;
    String ticketId = context.getRequest().getParameter("ticketId");
    TicketTask thisTask = (TicketTask) context.getFormBean();
    String action = (thisTask.getId() > 0 ? "modify" : "insert");
    thisTask.setModifiedBy(getUserId(context));
    try {
      db = this.getConnection(context);
      if ("insert".equals(action)) {
        thisTask.setEnteredBy(getUserId(context));
        thisTask.setTicketId(Integer.parseInt(ticketId));
        isValid = this.validateObject(context, db, thisTask);
        if (isValid) {
          recordInserted = thisTask.insert(db);
        }
      } else {
        Task oldTask = new Task(db, thisTask.getId());
        if (!hasAuthority(context, oldTask.getOwner())) {
          return ("PermissionError");
        }
        isValid = this.validateObject(context, db, thisTask);
        if (isValid) {
          resultCount = thisTask.update(db);
        }
      }

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted || resultCount == 1) {
      addModuleBean(context, "View Accounts", "Ticket Save OK");
      return ("SaveOK");
    }
    if ("insert".equals(action)){
      return executeCommandAdd(context);
    }else{
      return executeCommandModify(context);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-tickets-tasks-add"))) {
      return ("PermissionError");
    }

    TicketTask thisTicketTask = (TicketTask) context.getFormBean();
    if (thisTicketTask.getEnteredBy() != -1){
      Task  thisTask = thisTicketTask;
      context.getRequest().setAttribute("Task", thisTask);
    }
        
    addModuleBean(context, "View Accounts", "Add Ticket");
    return ("AddTaskOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Connection db = null;
    Task thisTask = null;
    String id = context.getRequest().getParameter("id");
    addModuleBean(context, "View Accounts", "Add Ticket");
    try {
      db = this.getConnection(context);
      thisTask = (TicketTask) context.getFormBean();
      if (thisTask.getId() == -1){
        thisTask = new Task(db, Integer.parseInt(id));
      }
      thisTask.checkEnabledOwnerAccount(db);
      if (thisTask.getContactId() > -1) {
        thisTask.checkEnabledLinkAccount(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("Task", thisTask);
    if (!hasAuthority(context, thisTask.getOwner()) || !(hasPermission(context, "accounts-accounts-tickets-tasks-edit"))) {
      if (hasPermission(context, "accounts-accounts-tickets-tasks-view")) {
        return "TaskDetailsOK";
      }
      return ("PermissionError");
    }
    return ("AddTaskOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Task thisTask = null;
    String id = context.getRequest().getParameter("id");
    if (!(hasPermission(context, "accounts-accounts-tickets-tasks-delete"))) {
      return ("PermissionError");
    }
    try {
      db = this.getConnection(context);
      thisTask = new Task(db, Integer.parseInt(id));
      thisTask.buildLinkDetails(db);
      if (!hasAuthority(context, thisTask.getOwner())) {
        return ("PermissionError");
      }
      thisTask.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("Task", thisTask);
      context.getRequest().setAttribute("refreshUrl", "AccountTicketTasks.do?command=List&ticketId=" + thisTask.getLinkDetails().getLinkItemId());
      return ("DeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-tickets-tasks-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Task thisTask = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisTask = new Task(db, Integer.parseInt(id));
      if (!hasAuthority(context, thisTask.getOwner())) {
        return ("PermissionError");
      }
      DependencyList dependencies = thisTask.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution")+"\n"+dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='AccountTicketTasks.do?command=Delete&id=" + id + "'");
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
        htmlDialog.addButton(systemStatus.getLabel("button.delete"), "javascript:window.location.href='AccountTicketTasks.do?command=Delete&id=" + id + "'");
        htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }
}

