/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
//import com.zeroio.controller.User;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.tasks.base.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.HtmlSelect;
import com.zeroio.webutils.FileDownload;
//import com.zeroio.controller.*;
/**
 *  Handles web actions for the Project Management Lists sub module
 *
 *@author     matt rajkowski
 *@created    November 17, 2002
 *@version    $Id: ProjectManagementLists.java,v 1.4 2002/12/20 14:07:55
 *      mrajkowski Exp $
 */
public final class ProjectManagementLists extends CFSModule {

  /**
   *  Prepare form for adding or updating a list item
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-modify")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", "lists_add");
      //Prepare the category header
      String categoryId = context.getRequest().getParameter("cid");
      if (categoryId == null) {
        categoryId = context.getRequest().getParameter("categoryId");
      }
      TaskCategory thisCategory = new TaskCategory(db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("category", thisCategory);
      //Prepare the form lookups
      LookupList priorityList = new LookupList(db, "lookup_task_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
      //Prepare the form object
      //Task thisTask = (Task) context.getFormBean();
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action to insert a new list item
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    boolean recordInserted = false;
    try {
      db = this.getConnection(context);
      //Verify the user has access to the project
      String projectId = (String) context.getRequest().getParameter("pid");
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-modify")) {
        return "PermissionError";
      }
      Task thisTask = (Task) context.getFormBean();
      boolean newTask = (thisTask.getId() == -1);
      if (newTask) {
        thisTask.setEnteredBy(getUserId(context));
      }
      thisTask.setModifiedBy(getUserId(context));
      thisTask.setProjectId(thisProject.getId());
      //TODO: Need to do this in a transaction, but in the object
      if (newTask) {
        recordInserted = thisTask.insert(db);
        if (recordInserted) {
          thisTask.insertProjectLink(db, thisProject.getId());
          indexAddItem(context, thisTask);
        }
      } else {
        resultCount = thisTask.update(db);
        indexAddItem(context, thisTask);
      }
      if (!recordInserted && resultCount < 1) {
        processErrors(context, thisTask.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordInserted) {
        if ("true".equals(context.getRequest().getParameter("donew"))) {
          context.getRequest().removeAttribute("Task");
          return (executeCommandAdd(context));
        }
        return ("AddOK");
      } else if (resultCount == 1) {
        return ("UpdateOK");
      } else {
        return executeCommandAdd(context);
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
  public String executeCommandModify(ActionContext context) {
    Connection db = null;
    try {
      String projectId = (String) context.getRequest().getParameter("pid");
      String taskId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      //Verify the project permissions
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-modify")) {
        return "PermissionError";
      }
      Task thisTask = new Task(db, Integer.parseInt(taskId));
      context.getRequest().setAttribute("Task", thisTask);
      return executeCommandAdd(context);
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
    Connection db = null;
    try {
      String projectId = (String) context.getRequest().getParameter("pid");
      String taskId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      //Verify the project permissions
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-view")) {
        return "PermissionError";
      }
      Task thisTask = new Task(db, Integer.parseInt(taskId));
      context.getRequest().setAttribute("Task", thisTask);
      if ("true".equals(context.getRequest().getParameter("popup"))) {
        return ("PopupOK");
      } else {
        return ("DetailsOK");
      }
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
  public String executeCommandDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    String taskId = (String) context.getRequest().getParameter("id");
    boolean recordDeleted = false;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-modify")) {
        return "PermissionError";
      }
      Task thisTask = new Task(db, Integer.parseInt(taskId));
      thisTask.setProjectId(thisProject.getId());
      recordDeleted = thisTask.delete(db);
      indexDeleteItem(context, thisTask);
      if (!recordDeleted) {
        processErrors(context, thisTask.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "DeleteOK";
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
  public String executeCommandMove(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String categoryId = (String) context.getRequest().getParameter("cid");
    String taskId = (String) context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-modify")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Load the category list
      TaskCategoryList categoryList = new TaskCategoryList();
      categoryList.setProjectId(thisProject.getId());
      categoryList.buildList(db);
      context.getRequest().setAttribute("categoryList", categoryList);
      //Load the task
      Task thisTask = new Task(db, Integer.parseInt(taskId));
      context.getRequest().setAttribute("Task", thisTask);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "MoveOK";
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
  public String executeCommandSaveMove(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String newCategoryId = (String) context.getRequest().getParameter("cid");
    String taskId = (String) context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-modify")) {
        return "PermissionError";
      }
      //Load the task
      Task thisTask = new Task(db, Integer.parseInt(taskId));
      thisTask.updateCategoryId(db, Integer.parseInt(newCategoryId));
      return "PopupCloseOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddCategory(ActionContext context) {
    Exception errorMessage = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", "lists_categories_add");
      //Process the category
      String categoryId = context.getParameter("cid");
      if (categoryId != null && !"".equals(categoryId)) {
        TaskCategory thisCategory = new TaskCategory(db, Integer.parseInt(categoryId));
        context.getRequest().setAttribute("category", thisCategory);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action to insert a new list category
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsertCategory(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("lists_categories_add").toLowerCase());
      //Process the category
      TaskCategory newCategory = (TaskCategory) context.getFormBean();
      newCategory.setLinkModuleId(Constants.TASK_CATEGORY_PROJECTS);
      newCategory.setLinkItemId(thisProject.getId());
      recordInserted = newCategory.insert(db);
      indexAddItem(context, newCategory);
      if (!recordInserted) {
        processErrors(context, newCategory.getErrors());
      } else {
        context.getRequest().setAttribute("pid", projectId);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("SaveOK");
      } else {
        return executeCommandAddCategory(context);
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
  public String executeCommandUpdateCategory(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("lists_categories_add").toLowerCase());
//Process the task category
      TaskCategory updatedCategory = (TaskCategory) context.getFormBean();
      updatedCategory.setLinkModuleId(Constants.TASK_CATEGORY_PROJECTS);
      updatedCategory.setLinkItemId(thisProject.getId());
      resultCount = updatedCategory.update(db);
      indexAddItem(context, updatedCategory);
      if (resultCount == -1) {
        processErrors(context, updatedCategory.getErrors());
      }
      context.getRequest().setAttribute("category", updatedCategory);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return executeCommandAddCategory(context);
      } else if (resultCount == 1) {
        return ("UpdateOK");
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
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
  public String executeCommandDeleteCategory(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean recordDeleted = false;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String categoryId = (String) context.getRequest().getParameter("cid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-delete")) {
        return "PermissionError";
      }
      //Load the task category
      TaskCategory thisCategory = new TaskCategory(db, Integer.parseInt(categoryId));
      thisCategory.setLinkModuleId(Constants.TASK_CATEGORY_PROJECTS);
      thisCategory.setLinkItemId(thisProject.getId());
      recordDeleted = thisCategory.delete(db);
      indexDeleteItem(context, thisCategory);
      if (!recordDeleted) {
        processErrors(context, thisCategory.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "DeleteOK";
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
  public String executeCommandMarkItem(ActionContext context) {
    Connection db = null;
    //Process the params
    String projectId = context.getRequest().getParameter("pid");
    String taskId = context.getRequest().getParameter("id");
    String newState = context.getRequest().getParameter("check");
    try {
      db = this.getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-lists-modify")) {
        return "PermissionError";
      }
      //Toggle the list item
      if ("on".equals(newState)) {
        Task.markComplete(db, Integer.parseInt(taskId));
      } else {
        Task.markIncomplete(db, Integer.parseInt(taskId));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    FileDownload fileDownload = new FileDownload();
    if ("on".equals(newState)) {
      //"images/box-checked.gif"
      fileDownload.setFullPath(context.getServletContext().getRealPath("/") + "images" + fs + "box-checked.gif");
      fileDownload.setDisplayName("box-checked.gif");
    } else {
      //"images/box.gif"
      fileDownload.setFullPath(context.getServletContext().getRealPath("/") + "images" + fs + "box.gif");
      fileDownload.setDisplayName("box.gif");
    }
    if (fileDownload.fileExists()) {
      try {
        fileDownload.sendFile(context, "image/gif");
      } catch (Exception error) {
      }
    }
    return null;
  }
}

