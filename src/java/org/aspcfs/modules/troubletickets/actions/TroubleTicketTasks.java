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
 *@version    $Id$
 */
public final class TroubleTicketTasks extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-tasks-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
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
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("TaskList", taskList);
      addModuleBean(context, "ViewTickets", "List Tasks");
      return this.getReturn(context, "ListTasks");
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
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-tasks-view"))) {
      return ("PermissionError");
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
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    boolean recordInserted = false;
    boolean contactRecordInserted = false;
    String ticketId = context.getRequest().getParameter("ticketId");
    TicketTask thisTask = (TicketTask) context.getFormBean();
    String action = (thisTask.getId() > 0 ? "modify" : "insert");
    
    if("insert".equals(action)){
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
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordInserted || resultCount == 1) {
        addModuleBean(context, "View Tickets", "Ticket Save OK");
        return ("SaveOK");
      }
      return this.getReturn(context, "PrepareTask");
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
  public String executeCommandAdd(ActionContext context) {
    if (!(hasPermission(context, "tickets-tickets-tasks-add"))) {
      return ("PermissionError");
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
    Connection db = null;
    Task thisTask = null;
    String id = context.getRequest().getParameter("id");
    addModuleBean(context, "View Tickets", "Add Ticket");
    try {
      db = this.getConnection(context);
      thisTask = new Task(db, Integer.parseInt(id));
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
        if(hasPermission(context, "tickets-tickets-tasks-view")){
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
    int action = -1;

    if (!(hasPermission(context, "tickets-tickets-tasks-delete"))) {
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
      context.getRequest().setAttribute("refreshUrl", "TroubleTicketTasks.do?command=List&ticketId=" + thisTask.getLinkDetails().getLinkItemId());
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
        return ("PermissionError");
      }
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
        htmlDialog.addButton("No", "javascript:parent.window.close()");
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

