/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.actions;

import com.zeroio.iteam.base.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.HtmlSelect;

/**
 *  Project Management module for CFS
 *
 *@author     matt rajkowski
 *@created    November 12, 2001
 *@version    $Id: ProjectManagementRequirements.java,v 1.6 2002/04/05 22:01:59
 *      mrajkowski Exp $
 */
public final class ProjectManagementRequirements extends CFSModule {

  /**
   *  Description of the Method
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
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-plan-outline-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("requirements_add").toLowerCase());

      LookupList loeList = new LookupList(db, "lookup_project_loe");
      context.getRequest().setAttribute("LoeList", loeList);
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
  public String executeCommandInsert(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;

    String projectId = (String) context.getRequest().getParameter("pid");

    boolean recordInserted = false;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-plan-outline-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("requirements_add").toLowerCase());

      Requirement thisRequirement = (Requirement) context.getFormBean();
      thisRequirement.setProjectId(thisProject.getId());
      thisRequirement.setEnteredBy(getUserId(context));
      thisRequirement.setModifiedBy(getUserId(context));
      recordInserted = thisRequirement.insert(db);
      indexAddItem(context, thisRequirement);
      if (!recordInserted) {
        processErrors(context, thisRequirement.getErrors());
      } else {
        context.getRequest().setAttribute("pid", projectId);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("AddOK");
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
  public String executeCommandDetails(ActionContext context) {
    Exception errorMessage = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    String requirementId = (String) context.getRequest().getParameter("rid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-plan-view")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Load the requirement
      Requirement thisRequirement = new Requirement(db, Integer.parseInt(requirementId), thisProject.getId());
      context.getRequest().setAttribute("Requirement", thisRequirement);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("PopupOK");
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
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    String requirementId = (String) context.getRequest().getParameter("rid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project and permissions
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-plan-outline-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("requirements_add").toLowerCase());
      //Requirement
      Requirement thisRequirement = new Requirement(db, Integer.parseInt(requirementId), thisProject.getId());
      context.getRequest().setAttribute("Requirement", thisRequirement);
      //Form data
      LookupList loeList = new LookupList(db, "lookup_project_loe");
      context.getRequest().setAttribute("LoeList", loeList);
      return ("ProjectCenterOK");
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
  public String executeCommandUpdate(ActionContext context) {
    Exception errorMessage = null;
    Requirement thisRequirement = (Requirement) context.getFormBean();
    Connection db = null;
    int resultCount = 0;
    try {
      db = this.getConnection(context);
      Project thisProject = new Project(db, thisRequirement.getProjectId(), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-plan-outline-edit")) {
        return "PermissionError";
      }
      thisRequirement.setProject(thisProject);
      thisRequirement.setProjectId(thisProject.getId());
      thisRequirement.setModifiedBy(getUserId(context));
      resultCount = thisRequirement.update(db, context);
      if (resultCount == -1) {
        processErrors(context, thisRequirement.getErrors());
        context.getRequest().setAttribute("Project", thisProject);
        context.getRequest().setAttribute("Requirement", thisRequirement);
        context.getRequest().setAttribute("IncludeSection", ("requirements_add").toLowerCase());
      } else {
        indexAddItem(context, thisRequirement);
        context.getRequest().setAttribute("pid", String.valueOf(thisProject.getId()));
        context.getRequest().setAttribute("IncludeSection", ("requirements").toLowerCase());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return ("ProjectCenterOK");
      } else if (resultCount == 1) {
        return ("UpdateOK");
      } else {
        context.getRequest().setAttribute("Error",
            "<b>This record could not be updated because someone else updated it first.</b><p>" +
            "You can hit the back button to review the changes that could not be committed, " +
            "but you must reload the record and make the changes again.");
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
  public String executeCommandDelete(ActionContext context) {
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    String requirementId = (String) context.getRequest().getParameter("rid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project and permissions
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-plan-outline-delete")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Requirement
      Requirement thisRequirement = new Requirement(db, Integer.parseInt(requirementId), thisProject.getId());
      TeamMember currentMember = new TeamMember(db, thisProject.getId(), getUserId(context));
      if (currentMember.getRoleId() <= TeamMember.PROJECT_LEAD) {
        thisRequirement.delete(db);
        indexDeleteItem(context, thisRequirement);
      }
      context.getRequest().setAttribute("IncludeSection", ("requirements").toLowerCase());
      return ("DeleteOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }
}

