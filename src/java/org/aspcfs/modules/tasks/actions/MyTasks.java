package org.aspcfs.modules.tasks.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.tasks.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.contacts.base.Contact;
import java.util.*;
import java.sql.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import java.io.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    August 15, 2002
 *@version    $Id$
 */
public final class MyTasks extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-tasks-view"))) {
      return ("DefaultError");
    }
    return (this.executeCommandListTasks(context));
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListTasks(ActionContext context) {
    Exception errorMessage = null;
    PagedListInfo taskListInfo = this.getPagedListInfo(context, "TaskListInfo");
    int contactId = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId();
    Contact thisContact = null;
    taskListInfo.setLink("MyTasks.do?command=ListTasks");
    Connection db = null;
    TaskList taskList = new TaskList();
    if (!taskListInfo.hasListFilters()) {
      taskListInfo.addFilter(1, "my");
      taskListInfo.addFilter(2, "false");
    }
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      taskList.setPagedListInfo(taskListInfo);
      //Configure the first filter
      if ("tasksbyme".equals(taskListInfo.getFilterValue("listFilter1"))) {
        taskList.setTasksAssignedByUser(this.getUserId(context));
      } else {
        taskList.setOwner(contactId);
      }
      //Configure the second filter
      if ("all".equals(taskListInfo.getFilterValue("listFilter2"))) {
        //Don't need to set anything
      } else if ("false".equals(taskListInfo.getFilterValue("listFilter2"))) {
        taskList.setComplete(Constants.FALSE);
      } else {
        taskList.setComplete(Constants.TRUE);
      }
      taskList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, thisContact.getOwner())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("TaskList", taskList);
      addModuleBean(context, "My Tasks", "Task Home");
      return ("TaskListOK");
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
  public String executeCommandNew(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-tasks-view"))) {
      return ("DefaultError");
    }
    Exception errorMessage = null;
    Connection db = null;
    addModuleBean(context, "My Tasks", "Task Home");
    try {
      db = this.getConnection(context);
      //build loe types
      LookupList estimatedLOETypeList = new LookupList(db, "lookup_task_loe");
      context.getRequest().setAttribute("EstimatedLOETypeList", estimatedLOETypeList);
      //build task priority levels (descritions)
      LookupList priorityList = new LookupList(db, "lookup_task_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    context.getSession().removeAttribute("contactListInfo");
    context.getRequest().setAttribute("Task", new Task());
    if (context.getRequest().getParameter("popup") != null) {
      return ("NewTaskPopupOK");
    } else {
      return ("NewTaskOK");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Task thisTask = null;
    int id = -1;
    if (!(hasPermission(context, "myhomepage-tasks-view"))) {
      return ("DefaultError");
    }
    context.getSession().removeAttribute("contactListInfo");
    if (context.getRequest().getParameter("id") != null) {
      id = Integer.parseInt(context.getRequest().getParameter("id"));
    }
    int ownerUserId = -1;
    try {
      db = this.getConnection(context);
      thisTask = new Task(db, id);
      if (!hasAuthority(context, thisTask.getOwner())) {
        return ("PermissionError");
      }
      if (thisTask.getOwner() > -1) {
        ownerUserId = thisTask.checkEnabledOwnerAccount(db);
      }
      if (thisTask.getContactId() > -1) {
        thisTask.checkEnabledLinkAccount(db);
      }
      //build loe types
      LookupList estimatedLOETypeList = new LookupList(db, "lookup_task_loe");
      context.getRequest().setAttribute("EstimatedLOETypeList", estimatedLOETypeList);

      //build task priority levels (descritions)
      LookupList priorityList = new LookupList(db, "lookup_task_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, ownerUserId)) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("Task", thisTask);
      addModuleBean(context, "My Tasks", "Task Home");
      if (context.getRequest().getParameter("popup") != null) {
        return ("NewTaskPopupOK");
      } else {
        return ("NewTaskOK");
      }
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
  public String executeCommandInsert(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int id = -1;
    boolean done = false;
    if (!(hasPermission(context, "myhomepage-tasks-view"))) {
      return ("DefaultError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = Integer.parseInt(context.getRequest().getParameter("id"));
    }

    try {
      db = this.getConnection(context);
      Task newTask = (Task) context.getFormBean();
      newTask.setEnteredBy(getUserId(context));
      newTask.setModifiedBy(getUserId(context));
      if (!newTask.insert(db)) {
        processErrors(context, newTask.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (context.getRequest().getParameter("popup") != null) {
        return ("PopupCloseOK");
      } else {
        return ("InsertTaskOK");
      }
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
  public String executeCommandUpdate(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int id = -1;
    if (!(hasPermission(context, "myhomepage-tasks-view"))) {
      return ("DefaultError");
    }

    try {
      db = this.getConnection(context);
      Task thisTask = (Task) context.getFormBean();
      thisTask.setModifiedBy(getUserId(context));
      if (!hasAuthority(context, thisTask.getOwner())) {
        return ("PermissionError");
      }
      int count = thisTask.update(db);
      if (count == -1) {
        processErrors(context, thisTask.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (context.getRequest().getParameter("popup") != null) {
        return ("PopupCloseOK");
      } else {
        return ("InsertTaskOK");
      }
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
    Exception errorMessage = null;
    Connection db = null;
    Task thisTask = null;
    HtmlDialog htmlDialog = new HtmlDialog();

    int id = -1;
    if (!(hasPermission(context, "myhomepage-tasks-view"))) {
      return ("DefaultError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = Integer.parseInt(context.getRequest().getParameter("id"));
    }
    try {
      db = this.getConnection(context);
      thisTask = new Task(db, id);
      if (!hasAuthority(context, thisTask.getOwner())) {
        return ("PermissionError");
      }
      DependencyList dependencies = thisTask.processDependencies(db);
      htmlDialog.addMessage(dependencies.getHtmlString());

      if (dependencies.size() == 0) {
        htmlDialog.setTitle("Confirm");
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl("javascript:window.location.href='MyTasks.do?command=Delete&id=" + id + "'");
      } else {
        htmlDialog.setTitle("Confirm");
        htmlDialog.setHeader("Are you sure you want to delete this item:");
        htmlDialog.addButton("Delete All", "javascript:window.location.href='/MyTasks.do?command=Delete&id=" + id + "'");
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
    int id = -1;
    int action = -1;
    if (!(hasPermission(context, "myhomepage-tasks-view"))) {
      return ("DefaultError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = Integer.parseInt(context.getRequest().getParameter("id"));
    }
    if (context.getRequest().getParameter("action") != null) {
      action = Integer.parseInt(context.getRequest().getParameter("action"));
    }
    try {
      db = this.getConnection(context);
      thisTask = new Task(db, id);
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
      context.getRequest().setAttribute("refreshUrl", "MyTasks.do?command=ListTasks");
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
  public String executeCommandProcessImage(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int count = 0;

    String id = (String) context.getRequest().getParameter("id");

    //Start the download
    try {
      StringTokenizer st = new StringTokenizer(id, "|");
      String fileName = st.nextToken();
      String imageType = st.nextToken();
      int taskId = Integer.parseInt(st.nextToken());
      int status = Integer.parseInt(st.nextToken());
      db = this.getConnection(context);
      Task thisTask = new Task(db, taskId);
      if (!hasAuthority(context, thisTask.getOwner())) {
        return ("PermissionError");
      }

      if (status == Task.DONE) {
        thisTask.setComplete(true);
      } else {
        thisTask.setComplete(false);
      }
      count = thisTask.update(db);
      this.freeConnection(context, db);
      if (count != -1) {
        String filePath = context.getServletContext().getRealPath("/") + "images" + fs + fileName;
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(fileName);
        if (fileDownload.fileExists()) {
          fileDownload.sendFile(context, "image/" + imageType);
        } else {
          System.err.println("Image-> Trying to send a file that does not exist");
        }
      } else {
        processErrors(context, thisTask.getErrors());
      }
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
      System.out.println("MyTasks -> ProcessImage : Download cancelled or connection lost");
    } catch (Exception e) {
      errorMessage = e;
      this.freeConnection(context, db);
      System.out.println(e.toString());
    }
    return ("-none-");
  }
}

