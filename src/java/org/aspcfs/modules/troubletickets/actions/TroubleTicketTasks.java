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
package org.aspcfs.modules.troubletickets.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.tasks.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;

/**
 *  Represents Tasks created for a Ticket
 *
 *@author     akhi_m
 *@created    May 14, 2003
 *@version    $Id: TroubleTicketTasks.java,v 1.11 2003/08/29 20:02:40 akhi_m Exp
 *      $
 */
public final class TroubleTicketTasks extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-tasks-view")) {
      return ("PermissionError");
    }
    String ticketId = context.getRequest().getParameter("ticketId");
    Connection db = null;
    TaskList taskList = new TaskList();

    PagedListInfo ticTaskListInfo = this.getPagedListInfo(context, "TicketTaskListInfo");
    ticTaskListInfo.setItemsPerPage(0);
    try {
      db = this.getConnection(context);
      taskList.setTicketId(Integer.parseInt(ticketId));
      taskList.setPagedListInfo(ticTaskListInfo);
      taskList.buildList(db);

      //get the ticket
      Ticket thisTicket = new Ticket(db, Integer.parseInt(ticketId));
      context.getRequest().setAttribute("TicketDetails", thisTicket);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("TaskList", taskList);
    addModuleBean(context, "ViewTickets", "List Tasks");
    return this.getReturn(context, "ListTasks");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-tasks-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Task thisTask = null;
    String id = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      thisTask = new Task(db, Integer.parseInt(id));
      context.getRequest().setAttribute("Task", thisTask);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Tickets", "View Tickets");
    return this.getReturn(context, "TaskDetails");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    String permission = "tickets-tickets-tasks-edit";
    Connection db = null;
    int resultCount = -1;
    boolean recordInserted = false;
    boolean contactRecordInserted = false;
    String ticketId = context.getRequest().getParameter("ticketId");
    TicketTask thisTask = (TicketTask) context.getFormBean();
    String action = (thisTask.getId() > 0 ? "modify" : "insert");

    if ("insert".equals(action)) {
      permission = "tickets-tickets-tasks-add";
    }
    if (!(hasPermission(context, permission))) {
      return ("PermissionError");
    }
    thisTask.setModifiedBy(getUserId(context));
    try {
      db = this.getConnection(context);
      if ("insert".equals(action)) {
        thisTask.setEnteredBy(getUserId(context));
        thisTask.setTicketId(Integer.parseInt(ticketId));
        recordInserted = thisTask.insert(db);
      } else {
        Task oldTask = new Task(db, thisTask.getId());
        if (!hasAuthority(context, oldTask.getOwner())) {
          return ("PermissionError");
        }
        resultCount = thisTask.update(db);
      }

      if (!recordInserted && resultCount == -1) {
        processErrors(context, thisTask.getErrors());
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted || resultCount == 1) {
      addModuleBean(context, "View Tickets", "Ticket Save OK");
      return ("SaveOK");
    }
    if ("insert".equals(action)) {
      return executeCommandAdd(context);
    } else {
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
    if (!hasPermission(context, "tickets-tickets-tasks-add")) {
      return ("PermissionError");
    }

    TicketTask thisTicketTask = (TicketTask) context.getFormBean();
    if (thisTicketTask.getEnteredBy() != -1) {
      Task thisTask = thisTicketTask;
      context.getRequest().setAttribute("Task", thisTask);
    }

    addModuleBean(context, "View Tickets", "Add Ticket");
    return ("AddTaskOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-tasks-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Task thisTask = null;
    String id = context.getRequest().getParameter("id");
    addModuleBean(context, "View Tickets", "Add Ticket");
    try {
      db = this.getConnection(context);
      thisTask = (Task) context.getFormBean();
      if (thisTask.getId() == -1) {
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
    if (!hasAuthority(context, thisTask.getOwner()) || !(hasPermission(context, "tickets-tickets-tasks-edit"))) {
      if (hasPermission(context, "tickets-tickets-tasks-view")) {
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
    if (!hasPermission(context, "tickets-tickets-tasks-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    Task thisTask = null;
    String id = context.getRequest().getParameter("id");
    int action = -1;
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
    context.getRequest().setAttribute("refreshUrl", "TroubleTicketTasks.do?command=List&ticketId=" + thisTask.getLinkDetails().getLinkItemId());
    return ("DeleteOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-tasks-delete"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    Task thisTask = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      thisTask = new Task(db, Integer.parseInt(id));
      if (!hasAuthority(context, thisTask.getOwner())) {
        htmlDialog.setTitle("Confirm");
        htmlDialog.setHeader("You are not permitted to delete this task because it is not owned by you or by a user reporting to you.");
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
      } else {
        DependencyList dependencies = thisTask.processDependencies(db);
        htmlDialog.addMessage(dependencies.getHtmlString());

        if (dependencies.size() == 0) {
          htmlDialog.setTitle("Confirm");
          htmlDialog.setShowAndConfirm(false);
          htmlDialog.setDeleteUrl("javascript:window.location.href='TroubleTicketTasks.do?command=Delete&id=" + id + "'");
        } else {
          htmlDialog.setTitle("Confirm");
          htmlDialog.setHeader("Are you sure you want to delete this item:");
          htmlDialog.addButton("Delete", "javascript:window.location.href='TroubleTicketTasks.do?command=Delete&id=" + id + "'");
          htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

