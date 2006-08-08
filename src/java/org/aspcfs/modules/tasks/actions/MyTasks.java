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

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author akhi_m
 * @version $Id$
 * @created August 15, 2002
 */
public final class MyTasks extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-tasks-view"))) {
      return ("PermissionError");
    }
    return (this.executeCommandListTasks(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandListTasks(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-tasks-view"))) {
      return ("PermissionError");
    }
    PagedListInfo taskListInfo = this.getPagedListInfo(
        context, "TaskListInfo");
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandNew(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-tasks-add"))) {
      return ("PermissionError");
    }
    Task newTask = (Task) context.getFormBean();
    context.getRequest().setAttribute("Task", newTask);
    addModuleBean(context, "My Tasks", "New Task");
    return getReturn(context, "NewTask");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "myhomepage-tasks-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Task thisTask = null;
    String id = context.getRequest().getParameter("id");
    if (id == null || "".equals(id.trim())) {
      id = (String) context.getRequest().getAttribute("id");
    }
    try {
      db = this.getConnection(context);
      thisTask = new Task(db, Integer.parseInt(id));
      context.getRequest().setAttribute("Task", thisTask);
      LookupList list = this.getSystemStatus(context).getLookupList(db, "lookup_ticket_task_category");
      context.getRequest().setAttribute("ticketTaskCategoryList", list);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (hasAuthority(context, thisTask.getOwner()) || hasAuthority(
        context, thisTask.getEnteredBy())) {
      return getReturn(context, "TaskDetails");
    }
    return ("PermissionError");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Task thisTask = null;
    int id = -1;
    String forward = context.getRequest().getParameter("return");
    if (forward != null && !"".equals(forward.trim())) {
      context.getRequest().setAttribute("return",forward);
    }
    if (!(hasPermission(context, "myhomepage-tasks-edit"))) {
      return ("PermissionError");
    }
    context.getSession().removeAttribute("contactListInfo");
    try {
      if (context.getRequest().getParameter("id") != null) {
        id = Integer.parseInt(context.getRequest().getParameter("id"));
      }
      db = this.getConnection(context);
      thisTask = (Task) context.getFormBean();
      if (thisTask.getId() == -1) {
        thisTask = new Task(db, id);
        thisTask.buildResources(db);
      }
      thisTask.checkEnabledOwnerAccount(db);
      if (thisTask.getContactId() > -1) {
        thisTask.checkEnabledLinkAccount(db);
        Contact contact = new Contact(db, thisTask.getContactId());
        thisTask.setContactName(contact.getNameFull());
      }
      if (thisTask.getTicketId() > -1) {
        LookupList list = this.getSystemStatus(context).getLookupList(db, "lookup_ticket_task_category");
        context.getRequest().setAttribute("ticketTaskCategoryList", list);
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
          return getReturn(context, "TaskDetails");
        }
        return ("PermissionError");
      }
      addModuleBean(context, "My Tasks", "Task Home");
      context.getRequest().setAttribute("Task", thisTask);
      return getReturn(context, "NewTask");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    Connection db = null;
    int id = -1;
    boolean inserted = false;
    boolean isValid = false;
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
      isValid = this.validateObject(context, db, newTask);
      if (isValid) {
        inserted = newTask.insert(db);
      }
      if (inserted) {
        Task bufferTask = new Task(db, newTask.getId());
        processInsertHook(context, bufferTask);
      } else {
        if (newTask.getContactId() > -1) {
          newTask.checkEnabledLinkAccount(db);
          Contact contact = new Contact(db, newTask.getContactId());
          newTask.setContactName(contact.getNameFull());
          context.getRequest().setAttribute("Task", newTask);
        }
        if (newTask.getTicketId() > -1) {
          LookupList list = this.getSystemStatus(context).getLookupList(db, "lookup_ticket_task_category");
          context.getRequest().setAttribute("ticketTaskCategoryList", list);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    String addAnother = (String) context.getRequest().getParameter("addAnother");
    if (addAnother != null && "true".equals(addAnother.trim())) {
      context.getRequest().setAttribute("Task", new Task());
      return getReturn(context, "NewTask");
    }
    if (inserted) {
      return getReturn(context, "InsertTask");
    } else {
      return executeCommandNew(context);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    Connection db = null;
    boolean isValid = false;
    if (!(hasPermission(context, "myhomepage-tasks-edit"))) {
      return ("PermissionError");
    }
    int count = -1;
    String forward = context.getRequest().getParameter("return");
    if (forward != null && !"".equals(forward.trim())) {
      context.getRequest().setAttribute("return",forward);
    }
    String id = context.getRequest().getParameter("id");
    Task thisTask = (Task) context.getFormBean();
    try {
      db = this.getConnection(context);
      Task previousTask = new Task(db, thisTask.getId());
      thisTask.setModifiedBy(getUserId(context));
      Task oldTask = new Task(db, Integer.parseInt(id));
      if (!hasAuthority(context, oldTask.getOwner())) {
        return ("PermissionError");
      }
      isValid = this.validateObject(context, db, thisTask);
      if (isValid) {
        count = thisTask.update(db);
      }
      if (count == 1) {
        this.processUpdateHook(context, previousTask, thisTask);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (count == -1 || !isValid) {
      return executeCommandModify(context);
    }
    if (forward != null && "details".equals(forward.trim())) {
      context.getRequest().setAttribute("id", String.valueOf(thisTask.getId()));
      return getReturn(context, "DetailsTask");
    }
    return getReturn(context, "InsertTask");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
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
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisTask = new Task(db, id);
      if (!hasAuthority(context, thisTask.getOwner())) {
        return ("PermissionError");
      }
      DependencyList dependencies = thisTask.processDependencies(db);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());

      if (dependencies.size() == 0) {
        htmlDialog.setShowAndConfirm(false);
        htmlDialog.setDeleteUrl(
            "javascript:window.location.href='MyTasks.do?command=Delete&id=" + id + "'");
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header2"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='MyTasks.do?command=Delete&id=" + id + "'");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
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
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("Task", thisTask);
    context.getRequest().setAttribute(
        "refreshUrl", "MyTasks.do?command=ListTasks");
    return ("DeleteOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandProcessImage(ActionContext context) {
    Connection db = null;
    int count = 0;
    boolean isValid = false;

    String id = (String) context.getRequest().getParameter("id");
    String from = (String) context.getRequest().getParameter("return");
    String imageId = (String) context.getRequest().getParameter("imageId");
    if (imageId != null && !"".equals(imageId)) {
      context.getRequest().setAttribute("imageId", imageId);
    }

    //Start the download
    try {
      StringTokenizer st = new StringTokenizer(id, "|");
      String fileName = st.nextToken();
      String imageType = st.nextToken();
      int taskId = Integer.parseInt(st.nextToken());
      int status = Integer.parseInt(st.nextToken());
      db = this.getConnection(context);
      Task thisTask = new Task(db, taskId);
      Task previousTask = new Task(db, taskId);
      if (!hasAuthority(context, thisTask.getOwner())) {
        return ("PermissionError");
      }

      if (status == Task.DONE) {
        thisTask.setComplete(true);
      } else {
        thisTask.setComplete(false);
      }
      //bypass the warnings
      thisTask.setOnlyWarnings(true);
      isValid = this.validateObject(context, db, thisTask);
      if (isValid) {
        count = thisTask.update(db);
      }
      if (count == 1) {
        this.processUpdateHook(context, previousTask, thisTask);
      }
      if (from != null && "list".equals(from)) {
        context.getRequest().setAttribute("image", "images/" + fileName);
      } else {
        if (count != -1) {
          String filePath = context.getServletContext().getRealPath("/") + "images" + fs + fileName;
          FileDownload fileDownload = new FileDownload();
          fileDownload.setFullPath(filePath);
          fileDownload.setDisplayName(fileName);
          if (fileDownload.fileExists()) {
            fileDownload.sendFile(context, "image/" + imageType);
          } else {
            System.err.println(
                "Image-> Trying to send a file that does not exist");
          }
        }
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
      System.out.println(
          "MyTasks-> ProcessImage : Download canceled or connection lost");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (from != null && "list".equals(from)) {
      return "SetStatusOK";
    }
    return ("-none-");
  }


  public String executeCommandReassignTask(ActionContext context) {
    Connection db = null;
    boolean isValid = false;
    if (!(hasPermission(context, "myhomepage-tasks-edit"))) {
      return ("PermissionError");
    }
    int count = -1;
    String forward = context.getRequest().getParameter("return");
    if (forward != null && !"".equals(forward.trim())) {
      context.getRequest().setAttribute("return",forward);
    }
    String id = context.getRequest().getParameter("id");
    String ownerId = context.getRequest().getParameter("ownerId");
    Task thisTask = null;
    try {
      db = this.getConnection(context);
      thisTask = new Task(db, Integer.parseInt(id));
      thisTask.setOwner(ownerId);
      Task previousTask = new Task(db, thisTask.getId());
      thisTask.setModifiedBy(getUserId(context));
      Task oldTask = new Task(db, Integer.parseInt(id));
      if (!hasAuthority(context, oldTask.getOwner())) {
        return ("PermissionError");
      }
      count = thisTask.update(db);
      if (count == 1) {
        this.processUpdateHook(context, previousTask, thisTask);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AssignTaskOK";
  }
}

