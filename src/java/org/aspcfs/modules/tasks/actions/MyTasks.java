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
      return ("PermissionError");
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
    if (!(hasPermission(context, "myhomepage-tasks-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    PagedListInfo taskListInfo = this.getPagedListInfo(context, "TaskListInfo");
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
      taskList.setPagedListInfo(taskListInfo);
      //Configure the first filter
      if ("tasksbyme".equals(taskListInfo.getFilterValue("listFilter1"))) {
        taskList.setTasksAssignedByUser(this.getUserId(context));
      } else {
        taskList.setOwner(this.getUserId(context));
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
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("TaskList", taskList);
    addModuleBean(context, "My Tasks", "Task Home");
    return ("TaskListOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandNew(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-tasks-add"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "My Tasks", "New Task");
    return this.getReturn(context, "NewTask");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "myhomepage-tasks-view")) {
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
    if (hasAuthority(context, thisTask.getOwner()) || hasAuthority(context, thisTask.getEnteredBy())) {
      return this.getReturn(context, "TaskDetails");
    }
    return ("PermissionError");
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
    if (!(hasPermission(context, "myhomepage-tasks-edit"))) {
      return ("PermissionError");
    }
    context.getSession().removeAttribute("contactListInfo");
    if (context.getRequest().getParameter("id") != null) {
      id = Integer.parseInt(context.getRequest().getParameter("id"));
    }
    try {
      db = this.getConnection(context);
      thisTask = new Task(db, id);
      thisTask.checkEnabledOwnerAccount(db);
      if (thisTask.getContactId() > -1) {
        thisTask.checkEnabledLinkAccount(db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      //make sure that either the task is owned by the user or that it was entered by the user(view mode applies in this case)
      if (!hasAuthority(context, thisTask.getOwner())) {
        if (hasAuthority(context, thisTask.getEnteredBy())) {
          context.getRequest().setAttribute("Task", thisTask);
          return this.getReturn(context, "TaskDetails");
        }
        return ("PermissionError");
      }
      addModuleBean(context, "My Tasks", "Task Home");
      context.getRequest().setAttribute("Task", thisTask);
      return this.getReturn(context, "NewTask");
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
    boolean inserted = false;
    if (!(hasPermission(context, "myhomepage-tasks-add"))) {
      return ("PermissionError");
    }

    if (context.getRequest().getParameter("id") != null) {
      id = Integer.parseInt(context.getRequest().getParameter("id"));
    }

    try {
      db = this.getConnection(context);
      Task newTask = (Task) context.getFormBean();
      newTask.setEnteredBy(getUserId(context));
      newTask.setModifiedBy(getUserId(context));
      inserted = newTask.insert(db);
      if (!inserted) {
        processErrors(context, newTask.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (inserted) {
        return this.getReturn(context, "InsertTask");
      } else {
        return executeCommandNew(context);
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
    if (!(hasPermission(context, "myhomepage-tasks-edit"))) {
      return ("PermissionError");
    }
    int count = -1;
    String id = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Task thisTask = (Task) context.getFormBean();
      thisTask.setModifiedBy(getUserId(context));
      Task oldTask = new Task(db, Integer.parseInt(id));
      if (!hasAuthority(context, oldTask.getOwner())) {
        return ("PermissionError");
      }
      count = thisTask.update(db);
      if (count == -1) {
        processErrors(context, thisTask.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (count == -1) {
        return executeCommandModify(context);
      } else {
        return this.getReturn(context, "InsertTask");
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
    if (!(hasPermission(context, "myhomepage-tasks-delete"))) {
      return ("PermissionError");
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
        htmlDialog.addButton("Delete All", "javascript:window.location.href='MyTasks.do?command=Delete&id=" + id + "'");
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
    if (!(hasPermission(context, "myhomepage-tasks-delete"))) {
      return ("PermissionError");
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
      //User either canceled the download or lost connection
      System.out.println("MyTasks-> ProcessImage : Download canceled or connection lost");
    } catch (Exception e) {
      errorMessage = e;
      this.freeConnection(context, db);
      System.out.println(e.toString());
    }
    return ("-none-");
  }
}

