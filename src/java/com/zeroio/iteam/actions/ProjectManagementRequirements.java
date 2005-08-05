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
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.Project;
import com.zeroio.iteam.base.Requirement;
import com.zeroio.iteam.base.TeamMember;
import com.zeroio.iteam.utils.AssignmentImporter;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.util.HashMap;

/**
 * Project Management module for CFS
 *
 * @author matt rajkowski
 * @version $Id: ProjectManagementRequirements.java,v 1.6 2002/04/05 22:01:59
 *          mrajkowski Exp $
 * @created November 12, 2001
 */
public final class ProjectManagementRequirements extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("requirements_add").toLowerCase());

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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandInsert(ActionContext context) {
    Connection db = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    boolean recordInserted = false;
    boolean isValid = false;
    try {
      db = getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("requirements_add").toLowerCase());

      Requirement thisRequirement = (Requirement) context.getFormBean();
      thisRequirement.setProjectId(thisProject.getId());
      thisRequirement.setEnteredBy(getUserId(context));
      thisRequirement.setModifiedBy(getUserId(context));
      isValid = this.validateObject(context, db, thisRequirement);
      if (isValid) {
        recordInserted = thisRequirement.insert(db);
        indexAddItem(context, thisRequirement);
      }
      if (recordInserted) {
        context.getRequest().setAttribute("pid", projectId);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    if (recordInserted) {
      return ("AddOK");
    }
    return (executeCommandAdd(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    Exception errorMessage = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    String requirementId = (String) context.getRequest().getParameter("rid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-plan-view")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Load the requirement
      Requirement thisRequirement = new Requirement(
          db, Integer.parseInt(requirementId), thisProject.getId());
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    String requirementId = (String) context.getRequest().getParameter("rid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project and permissions
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("requirements_add").toLowerCase());
      //Requirement
      Requirement thisRequirement = new Requirement(
          db, Integer.parseInt(requirementId), thisProject.getId());
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    Requirement thisRequirement = (Requirement) context.getFormBean();
    Connection db = null;
    boolean isValid = false;
    int resultCount = -1;
    try {
      db = this.getConnection(context);
      Project thisProject = new Project(
          db, thisRequirement.getProjectId(), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-edit")) {
        return "PermissionError";
      }
      thisRequirement.setProject(thisProject);
      thisRequirement.setProjectId(thisProject.getId());
      thisRequirement.setModifiedBy(getUserId(context));
      isValid = this.validateObject(context, db, thisRequirement);
      if (isValid) {
        resultCount = thisRequirement.update(db, context);
      }
      if (resultCount == -1) {
        context.getRequest().setAttribute("Project", thisProject);
        context.getRequest().setAttribute("Requirement", thisRequirement);
        context.getRequest().setAttribute(
            "IncludeSection", ("requirements_add").toLowerCase());
      } else {
        indexAddItem(context, thisRequirement);
        context.getRequest().setAttribute(
            "pid", String.valueOf(thisProject.getId()));
        context.getRequest().setAttribute(
            "IncludeSection", ("requirements").toLowerCase());
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      return ("UpdateOK");
    } else {
      if (resultCount == -1 || !isValid) {
        return ("ProjectCenterOK");
      }
      context.getRequest().setAttribute(
          "Error",
          "<b>This record could not be updated because someone else updated it first.</b><p>" +
          "You can hit the back button to review the changes that could not be committed, " +
          "but you must reload the record and make the changes again.");
      return ("UserError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    //Params
    String projectId = (String) context.getRequest().getParameter("pid");
    String requirementId = (String) context.getRequest().getParameter("rid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project and permissions
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-delete")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Requirement
      Requirement thisRequirement = new Requirement(
          db, Integer.parseInt(requirementId), thisProject.getId());
      TeamMember currentMember = new TeamMember(
          db, thisProject.getId(), getUserId(context));
      if (currentMember.getRoleId() <= TeamMember.PROJECT_LEAD) {
        boolean recordDeleted = thisRequirement.delete(db);
        if (!recordDeleted) {
          SystemStatus systemStatus = this.getSystemStatus(context);
          thisRequirement.getErrors().put(
              "actionError", systemStatus.getLabel(
                  "object.validation.requirementDeletion"));
          processErrors(context, thisRequirement.getErrors());
        } else {
          indexDeleteItem(context, thisRequirement);
        }
      }
      context.getRequest().setAttribute(
          "IncludeSection", ("requirements").toLowerCase());
      return ("DeleteOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPrepareImport(ActionContext context) {
    String projectId = (String) context.getRequest().getParameter("pid");
    String requirementId = (String) context.getRequest().getParameter("rid");
    Connection db = null;
    try {
      db = this.getConnection(context);
      //Load the project and permissions
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-delete")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Load the requirement
      Requirement thisRequirement = new Requirement(
          db, Integer.parseInt(requirementId), thisProject.getId());
      context.getRequest().setAttribute("Requirement", thisRequirement);
    } catch (Exception e) {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }
    context.getRequest().setAttribute(
        "IncludeSection", ("requirements_import"));
    return ("ProjectCenterOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandImport(ActionContext context) {
    Connection db = null;
    try {
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      HashMap parts = multiPart.parseData(context.getRequest(), null);
      db = this.getConnection(context);
      //Load the project and permissions
      Project thisProject = loadProject(
          db, Integer.parseInt((String) parts.get("pid")), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-delete")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      // Requirement to use for saving plan
      Requirement thisRequirement = new Requirement(
          db, Integer.parseInt((String) parts.get("rid")), thisProject.getId());
      context.getRequest().setAttribute("Requirement", thisRequirement);
      // Import
      FileInfo fileInfo = (FileInfo) parts.get("file");
      // Determine file type
      if (fileInfo == null || !AssignmentImporter.parse(
          fileInfo, thisRequirement, db)) {
        HashMap errors = new HashMap();
        SystemStatus systemStatus = this.getSystemStatus(context);
        errors.put(
            "actionError", systemStatus.getLabel(
                "object.validation.incorrectFileNameSpecified"));
        processErrors(context, errors);
        return executeCommandPrepareImport(context);
      }
      context.getRequest().setAttribute(
          "IncludeSection", ("requirements_import_ok"));
      return ("ImportOK");
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return "ImportERROR";
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }
  }
}

