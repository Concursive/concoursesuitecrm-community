/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.actions;

//import org.theseus.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.HtmlSelect;
//import com.zeroio.controller.*;
/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    April 28, 2003
 *@version    $Id: ProjectManagementIssueCategories.java,v 1.2 2003/05/07
 *      20:39:03 matt Exp $
 */
public final class ProjectManagementIssueCategories extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    Exception errorMessage = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project (for access)
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-forums-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", "issues_categories_add");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-forums-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", "issues_categories_add");
      //Load the category
      IssueCategory thisCategory = new IssueCategory(db, Integer.parseInt(categoryId), thisProject.getId());
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    int resultCount = -1;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", "issues_add");
      //Process the issue category
      IssueCategory thisCategory = (IssueCategory) context.getFormBean();
      thisCategory.setModifiedBy(getUserId(context));
      if (thisCategory.getId() > 0) {
        if (!hasProjectAccess(context, db, thisProject, "project-discussion-forums-edit")) {
          return "PermissionError";
        }
        resultCount = thisCategory.update(db);
        indexAddItem(context, thisCategory);
      } else {
        if (!hasProjectAccess(context, db, thisProject, "project-discussion-forums-add")) {
          return "PermissionError";
        }
        thisCategory.setProjectId(thisProject.getId());
        thisCategory.setEnteredBy(getUserId(context));
        recordInserted = thisCategory.insert(db);
        indexAddItem(context, thisCategory);
      }
      if (!recordInserted && resultCount <= 0) {
        processErrors(context, thisCategory.getErrors());
      } else {
        context.getRequest().setAttribute("pid", projectId);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordInserted || resultCount == 1) {
        return ("SaveOK");
      } else {
        return (executeCommandAdd(context));
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
  public String executeCommandDelete(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String categoryId = (String) context.getRequest().getParameter("cid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-discussion-forums-delete")) {
        return "PermissionError";
      }
      //Load the issue category
      IssueCategory issueCategory = new IssueCategory(db, Integer.parseInt(categoryId), thisProject.getId());
      issueCategory.delete(db);
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

