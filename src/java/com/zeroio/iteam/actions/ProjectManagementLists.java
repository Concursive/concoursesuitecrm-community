/*
 *  Copyright 2000-2003 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.tasks.base.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.HtmlSelect;

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
    /*
     *  if (!(hasPermission(context, "projects-issues-add"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("lists_add").toLowerCase());

      String categoryId = context.getRequest().getParameter("cid");
      if (categoryId == null) {
        categoryId = context.getRequest().getParameter("categoryId");
      }
      TaskCategory thisCategory = new TaskCategory(db, Integer.parseInt(categoryId));
      context.getRequest().setAttribute("category", thisCategory);

      LookupList priorityList = new LookupList(db, "lookup_task_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);

      String id = context.getParameter("id");
      if (id != null && !"".equals(id)) {
        Task thisTask = new Task(db, Integer.parseInt(id));
        context.getRequest().setAttribute("Task", thisTask);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "AddItem", "");
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
  public String executeCommandInsert(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    /*
     *  if (!(hasPermission(context, "myhomepage-inbox-view"))) {
     *  return ("DefaultError");
     *  }
     */
    String projectId = (String) context.getRequest().getParameter("pid");

    try {
      db = this.getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("lists_add").toLowerCase());

      Task newTask = (Task) context.getFormBean();
      newTask.setEnteredBy(getUserId(context));
      newTask.setModifiedBy(getUserId(context));
      //TODO: Need to do this in a transaction, but in the object
      recordInserted = newTask.insert(db);
      if (!recordInserted) {
        processErrors(context, newTask.getErrors());
      } else {
        newTask.insertProjectLink(db, thisProject.getId());
        context.getRequest().setAttribute("pid", projectId);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("AddOK");
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
  public String executeCommandUpdate(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    /*
     *  if (!(hasPermission(context, "myhomepage-inbox-view"))) {
     *  return ("DefaultError");
     *  }
     */
    String projectId = (String) context.getRequest().getParameter("pid");

    try {
      db = this.getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("lists_add").toLowerCase());

      Task updatedTask = (Task) context.getFormBean();
      updatedTask.setModifiedBy(getUserId(context));
      resultCount = updatedTask.update(db);
      if (resultCount == -1) {
        processErrors(context, updatedTask.getErrors());
      }
      context.getRequest().setAttribute("Task", updatedTask);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return executeCommandAdd(context);
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
  public String executeCommandAddCategory(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "projects-issues-add"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", "lists_categories_add");

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

    addModuleBean(context, "AddItem", "");
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
    /*
     *  if (!(hasPermission(context, "myhomepage-inbox-view"))) {
     *  return ("DefaultError");
     *  }
     */
    String projectId = (String) context.getRequest().getParameter("pid");

    try {
      db = this.getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("lists_categories_add").toLowerCase());

      TaskCategory newCategory = (TaskCategory) context.getFormBean();
      newCategory.setLinkModuleId(Constants.TASK_CATEGORY_PROJECTS);
      newCategory.setLinkItemId(thisProject.getId());
      recordInserted = newCategory.insert(db);
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
        return ("AddCategoryOK");
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
    /*
     *  if (!(hasPermission(context, "myhomepage-inbox-view"))) {
     *  return ("DefaultError");
     *  }
     */
    String projectId = (String) context.getRequest().getParameter("pid");

    try {
      db = this.getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("lists_categories_add").toLowerCase());

      TaskCategory updatedCategory = (TaskCategory) context.getFormBean();
      updatedCategory.setLinkModuleId(Constants.TASK_CATEGORY_PROJECTS);
      updatedCategory.setLinkItemId(thisProject.getId());
      resultCount = updatedCategory.update(db);
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
        return ("UpdateCategoryOK");
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

