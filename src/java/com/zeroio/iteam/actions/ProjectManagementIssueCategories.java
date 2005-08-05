/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.IssueCategory;
import com.zeroio.iteam.base.Project;
import org.aspcfs.modules.actions.CFSModule;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: ProjectManagementIssueCategories.java,v 1.2 2003/05/07
 *          20:39:03 matt Exp $
 * @created April 28, 2003
 */
public final class ProjectManagementIssueCategories extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    Exception errorMessage = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project (for access)
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-discussion-forums-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", "issues_categories_add");
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandEdit(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String categoryId = (String) context.getRequest().getParameter("cid");
    try {
      db = getConnection(context);
      //Load the project (for access)
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-discussion-forums-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", "issues_categories_add");
      //Load the category
      IssueCategory thisCategory = new IssueCategory(
          db, Integer.parseInt(categoryId), thisProject.getId());
      context.getRequest().setAttribute("IssueCategory", thisCategory);
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    int resultCount = -1;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", "issues_add");
      //Process the issue category
      IssueCategory thisCategory = (IssueCategory) context.getFormBean();
      thisCategory.setModifiedBy(getUserId(context));
      if (thisCategory.getId() > 0) {
        if (!hasProjectAccess(
            context, db, thisProject, "project-discussion-forums-edit")) {
          return "PermissionError";
        }
        isValid = this.validateObject(context, db, thisCategory);
        if (isValid) {
          resultCount = thisCategory.update(db);
          indexAddItem(context, thisCategory);
        }
      } else {
        if (!hasProjectAccess(
            context, db, thisProject, "project-discussion-forums-add")) {
          return "PermissionError";
        }
        thisCategory.setProjectId(thisProject.getId());
        thisCategory.setEnteredBy(getUserId(context));
        isValid = this.validateObject(context, db, thisCategory);
        if (isValid) {
          recordInserted = thisCategory.insert(db);
          indexAddItem(context, thisCategory);
        }
      }
      if (recordInserted || resultCount > 0) {
        context.getRequest().setAttribute("pid", projectId);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    if (recordInserted || resultCount == 1) {
      return ("SaveOK");
    }
    return (executeCommandAdd(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String categoryId = (String) context.getRequest().getParameter("cid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-discussion-forums-delete")) {
        return "PermissionError";
      }
      //Load the issue category
      IssueCategory issueCategory = new IssueCategory(
          db, Integer.parseInt(categoryId), thisProject.getId());
      issueCategory.delete(db, this.getDbNamePath(context));
      indexDeleteItem(context, issueCategory);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("DeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

