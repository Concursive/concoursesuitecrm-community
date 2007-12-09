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
package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.troubletickets.base.TicketTask;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;

/**
 * Represents an Accounts Ticket Tasks
 *
 * @author Mathur
 * @version $Id: TroubleTicketTasks.java,v 1.11 2003/08/29 20:02:40 akhi_m Exp
 *          $
 * @created June 9, 2003
 */
public final class AccountTicketTasks extends CFSModule {

  /**
   * Lists the tasks for the specified ticket
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-tasks-view")) {
      return ("PermissionError");
    }
    // Parameters
    String ticketId = context.getRequest().getParameter("ticketId");
    Connection db = null;
    TaskList taskList = new TaskList();
    // Paged List
    PagedListInfo ticTaskListInfo = this.getPagedListInfo(
        context, "AccountTicketTaskListInfo");
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
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("TicketDetails", thisTicket);
      //Load the organization
      Organization thisOrganization = new Organization(
          db, thisTicket.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      addModuleBean(context, "View Accounts", "List Tasks");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ListTasks");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-tickets-tasks-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Task thisTask = null;
    String id = context.getRequest().getParameter("id");
    if (id == null || "".equals(id.trim())) {
      id = (String) context.getRequest().getAttribute("id");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      thisTask = new Task(db, Integer.parseInt(id));
      context.getRequest().setAttribute("Task", thisTask);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisTask.getTicket().getOrgId())) {
        return ("PermissionError");
      }
      LookupList list = systemStatus.getLookupList(db, "lookup_ticket_task_category");
      context.getRequest().setAttribute("ticketTaskCategoryList", list);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "View Tickets");
    return getReturn(context, "TaskDetails");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    String permission = "accounts-accounts-tickets-tasks-edit";
    Connection db = null;
    int resultCount = -1;
    boolean recordInserted = false;
    boolean isValid = false;
    String addAnother = (String) context.getRequest().getParameter("addAnother");
    if (addAnother != null && !"".equals(addAnother.trim())) {
      context.getRequest().setAttribute("addAnother",addAnother);
    }
    String forward = context.getRequest().getParameter("forward");
    if (forward != null && !"".equals(forward.trim())) {
      context.getRequest().setAttribute("return",forward);
    }
    String ticketId = context.getRequest().getParameter("ticketId");
    TicketTask thisTask = (TicketTask) context.getFormBean();
    Ticket thisTicket = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    String action = (thisTask.getId() > 0 ? "modify" : "insert");
    if ("insert".equals(action)) {
      permission = "accounts-accounts-tickets-tasks-add";
    }
    if (!hasPermission(context, permission)) {
      return ("PermissionError");
    }
    thisTask.setModifiedBy(getUserId(context));
    try {
      db = this.getConnection(context);
      if ("insert".equals(action)) {
        if (forward != null || addAnother != null) {
          LookupList list = systemStatus.getLookupList(db, "lookup_ticket_task_category");
          list.addItem(-1,systemStatus.getLabel("calendar.none.4dashes","-- None --"));
          context.getRequest().setAttribute("ticketTaskCategoryList", list);
        }
        //Load the ticket
        thisTicket = new Ticket(db, Integer.parseInt(ticketId));
        //Check access permission to organization record
        if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
          return ("PermissionError");
        }
        thisTask.setEnteredBy(getUserId(context));
        thisTask.setTicketId(Integer.parseInt(ticketId));
        isValid = this.validateObject(context, db, thisTask);
        if (isValid) {
          recordInserted = thisTask.insert(db);
        }
        if (recordInserted) {
          this.processInsertHook(context, thisTask);
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
        if (resultCount == 1) {
          this.processUpdateHook(context, oldTask, thisTask);
        }
      }
      if (!isValid || !(recordInserted || resultCount == 1)) {
        if (thisTask.getContactId() > -1) {
          thisTask.checkEnabledLinkAccount(db);
          Contact contact = new Contact(db, thisTask.getContactId());
          thisTask.setContactName(contact.getNameFull());
          context.getRequest().setAttribute("Task", thisTask);
        }
      }
      context.getRequest().setAttribute("ticketId", ticketId);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted || resultCount == 1) {
      if (resultCount == 1 && forward != null && "details".equals(forward.trim())) {
          context.getRequest().setAttribute("id", String.valueOf(thisTask.getId()));
          context.getRequest().setAttribute("forward", forward);
          return executeCommandDetails(context);
      }
      if (addAnother != null && "true".equals(addAnother.trim()) && recordInserted) {
        TicketTask task = new TicketTask();
        task.setTicketId(ticketId);
        context.getRequest().setAttribute("ticketId", ticketId);
        context.getRequest().setAttribute("Task", task);
        return ("AddTaskOK");
      }
      
      addModuleBean(context, "View Accounts", "Ticket Save OK");
      return ("SaveOK");
    }
    if ("insert".equals(action)) {
      return executeCommandAdd(context);
    } else {
      return executeCommandModify(context);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-tasks-add")) {
      return ("PermissionError");
    }
    Connection db = null;

    TicketTask thisTicketTask = (TicketTask) context.getFormBean();
    if (thisTicketTask.getEnteredBy() != -1) {
      Task thisTask = thisTicketTask;
      context.getRequest().setAttribute("Task", thisTask);
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_ticket_task_category");
      list.addItem(-1,systemStatus.getLabel("calendar.none.4dashes","-- None --"));
      context.getRequest().setAttribute("ticketTaskCategoryList", list);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Add Ticket");
    return ("AddTaskOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-tickets-tasks-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    Task thisTask = null;
    Ticket thisTicket = null;
    int id = -1;
    String ticketId = (String) context.getRequest().getAttribute("ticketId");
    if (ticketId == null || "".equals(ticketId.trim())) {
      ticketId = context.getRequest().getParameter("ticketId");
    }
    addModuleBean(context, "View Accounts", "Add Ticket");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      if (context.getRequest().getParameter("id") != null) {
        id = Integer.parseInt(context.getRequest().getParameter("id"));
      }
      db = this.getConnection(context);
      thisTask = (Task) context.getFormBean();
      if (thisTask.getId() == -1) {
        thisTask = new Task(db, id);
      }
      
      if (thisTask.getTicketId() == -1 && ticketId != null && !"".equals(ticketId)) {
        thisTask.setTicketId(ticketId);
      }
      
      // Load the ticket
      if (ticketId != null && !"".equals(ticketId)) {
        thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      } else {
        thisTicket = new Ticket(db, thisTask.getTicketId());
      }
      
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
        return ("PermissionError");
      }
      thisTask.checkEnabledOwnerAccount(db);
      if (thisTask.getContactId() > -1) {
        thisTask.checkEnabledLinkAccount(db);
        Contact contact = new Contact(db, thisTask.getContactId());
        thisTask.setContactName(contact.getNameFull());
      }
      LookupList list = systemStatus.getLookupList(db, "lookup_ticket_task_category");
      list.addItem(-1,systemStatus.getLabel("calendar.none.4dashes","-- None --"));
      context.getRequest().setAttribute("ticketTaskCategoryList", list);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("Task", thisTask);
    if (!hasAuthority(context, thisTask.getOwner()) || !(hasPermission(
        context, "accounts-accounts-tickets-tasks-edit"))) {
      if (hasPermission(context, "accounts-accounts-tickets-tasks-view")) {
        return "TaskDetailsOK";
      }
      return ("PermissionError");
    }
    return ("AddTaskOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-tickets-tasks-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Task thisTask = null;
    String id = context.getRequest().getParameter("id");
    String sourcePopup = context.getRequest().getParameter("sourcePopup");
    try {
      db = this.getConnection(context);
      thisTask = new Task(db, Integer.parseInt(id));
      thisTask.buildLinkDetails(db);
      if (!hasAuthority(context, thisTask.getOwner())) {
        return ("PermissionError");
      }
      thisTask.delete(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
      context.getRequest().setAttribute("Task", thisTask);
      if (sourcePopup != null && "true".equals(sourcePopup.trim())) {
        return ("DeletePopupOK");
      }
      context.getRequest().setAttribute(
          "refreshUrl", "AccountTicketTasks.do?command=List&ticketId=" + thisTask.getLinkDetails().getLinkItemId());
      return ("DeleteOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-tickets-tasks-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Task thisTask = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = context.getRequest().getParameter("id");
    String sourcePopup = context.getRequest().getParameter("sourcePopup");
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisTask = new Task(db, Integer.parseInt(id));
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      if (!hasAuthority(context, thisTask.getOwner())) {
        htmlDialog.setHeader(
            systemStatus.getLabel("confirmdelete.taskNotOwnerHeader"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      } else {
        DependencyList dependencies = thisTask.processDependencies(db);
        htmlDialog.addMessage(
            systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
        if (dependencies.size() == 0) {
          htmlDialog.setShowAndConfirm(false);
          htmlDialog.setDeleteUrl(
              "javascript:window.location.href='AccountTicketTasks.do?command=Delete&id=" + id + "&sourcePopup="+sourcePopup + "'");
        } else {
          htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
          htmlDialog.addButton(
              systemStatus.getLabel("button.delete"), "javascript:window.location.href='AccountTicketTasks.do?command=Delete&id=" + id + "&sourcePopup="+sourcePopup + "'");
          htmlDialog.addButton(
              systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
        }
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
