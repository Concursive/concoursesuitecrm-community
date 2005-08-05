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
import com.zeroio.iteam.base.*;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: ProjectManagementAssignments.java,v 1.12.134.1 2004/03/19
 *          21:00:50 rvasista Exp $
 * @created November 28, 2001
 */
public final class ProjectManagementAssignments extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAdd(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    checkReturnPage(context);
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("assignments_add").toLowerCase());
      //Load team member drop-down list
      thisProject.buildTeamMemberList(db);
      java.util.Iterator i = thisProject.getTeam().iterator();
      while (i.hasNext()) {
        TeamMember thisMember = (TeamMember) i.next();
        User thisUser = new User();
        thisUser.setBuildContact(true);
        thisUser.setBuildContactDetails(true);
        thisUser.buildRecord(db, thisMember.getUserId());
        thisMember.setUser(thisUser);
      }
      //Load priority drop-down list
      LookupList priorityList = new LookupList(db, "lookup_project_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
      //Load status drop-down list
      LookupList statusList = new LookupList(db, "lookup_project_status");
      context.getRequest().setAttribute("StatusList", statusList);
      //Load status percent drop-down list
      HtmlPercentList statusPercentList = new HtmlPercentList();
      context.getRequest().setAttribute(
          "StatusPercentList", statusPercentList);
      //Load LOE drop-down list
      LookupList loeList = new LookupList(db, "lookup_project_loe");
      context.getRequest().setAttribute("LoeList", loeList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    String popUp = context.getRequest().getParameter("popup");
    if (popUp != null && !"null".equals(popUp)) {
      return ("PopupOK");
    } else {
      return ("ProjectCenterOK");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandSave(ActionContext context) {
    Connection db = null;
    int resultCount = -1;
    boolean recordInserted = false;
    boolean isValid = false;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    checkReturnPage(context);
    Assignment thisAssignment = (Assignment) context.getFormBean();
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      //Process the assignment
      thisAssignment.setModifiedBy(getUserId(context));
      thisAssignment.setProjectId(thisProject.getId());
      //Check permissions
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify") &&
          !hasProjectAccess(context, db, thisProject, "project-plan-view")) {
        return "PermissionError";
      }
      // Only assign to users of the project
      if (thisAssignment.getUserAssignedId() > -1 && !TeamMemberList.isOnTeam(
          db, thisProject.getId(), thisAssignment.getUserAssignedId())) {
        return "PermissionError";
      }
      if (thisAssignment.getId() > 0) {
        //Check user permissions
        TeamMember currentMember = new TeamMember(
            db, thisProject.getId(), getUserId(context));
        if (thisAssignment.getUserAssignedId() != getUserId(context) &&
            currentMember.getRoleId() > TeamMember.PROJECT_LEAD) {
          return "PermissionError";
        }
        thisAssignment.setProject(thisProject);
        isValid = this.validateObject(context, db, thisAssignment);
        if (isValid) {
          resultCount = thisAssignment.update(db);
          // Index some items
          indexAddItem(context, thisAssignment);
          AssignmentNote assignmentNote = thisAssignment.getAssignmentNote();
          if (assignmentNote != null && assignmentNote.isValid()) {
            indexAddItem(context, assignmentNote);
          }
        }
      } else {
        if (!hasProjectAccess(
            context, db, thisProject, "project-plan-outline-modify")) {
          return "PermissionError";
        }
        thisAssignment.setEnteredBy(getUserId(context));
        isValid = this.validateObject(context, db, thisAssignment);
        if (isValid) {
          recordInserted = thisAssignment.insert(db);
          // Index some items
          indexAddItem(context, thisAssignment);
          AssignmentNote assignmentNote = thisAssignment.getAssignmentNote();
          if (assignmentNote != null && assignmentNote.isValid()) {
            indexAddItem(context, assignmentNote);
          }
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
    if (resultCount == 0) {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    } else if (recordInserted || resultCount == 1) {
      if ("true".equals(context.getRequest().getParameter("donew"))) {
        context.getRequest().removeAttribute("Assignment");
        Assignment empty = new Assignment();
        empty.setIndent(thisAssignment.getIndent());
        empty.setPrevIndent(thisAssignment.getIndent());
        empty.setPrevMapId(thisAssignment.getPrevMapId());
        context.getRequest().setAttribute("Assignment", empty);
        return (executeCommandAdd(context));
      }
      String popUp = context.getRequest().getParameter("popup");
      if (popUp != null && !"null".equals(popUp)) {
        return "PopupCloseOK";
      } else {
        return ("SaveOK");
      }
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
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String assignmentId = (String) context.getRequest().getParameter("aid");
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
      context.getRequest().setAttribute(
          "IncludeSection", ("assignments_details").toLowerCase());
      //Load the assignment
      Assignment thisAssignment = new Assignment(
          db, Integer.parseInt(assignmentId), thisProject.getId());
      context.getRequest().setAttribute("Assignment", thisAssignment);
      //Load priority drop-down list
      LookupList priorityList = new LookupList(db, "lookup_project_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
      //Load status drop-down list
      LookupList statusList = new LookupList(db, "lookup_project_status");
      context.getRequest().setAttribute("StatusList", statusList);
      //Load status percent drop-down list
      HtmlPercentList statusPercentList = new HtmlPercentList();
      context.getRequest().setAttribute(
          "StatusPercentList", statusPercentList);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (isPopup(context)) {
      return ("PopupDetailsOK");
    } else {
      return ("ProjectCenterOK");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String assignmentId = (String) context.getRequest().getParameter("aid");
    checkReturnPage(context);
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      //Check permissions
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify") &&
          !hasProjectAccess(context, db, thisProject, "project-plan-view")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("assignments_add").toLowerCase());
      //Load the assignment
      Assignment thisAssignment = new Assignment(
          db, Integer.parseInt(assignmentId), thisProject.getId());
      context.getRequest().setAttribute("Assignment", thisAssignment);
      //Check user permissions
      if (!hasModifyAccess(context, db, thisProject, thisAssignment)) {
        return "DetailsREDIRECT";
      }
      //Generate form data
      thisProject.buildTeamMemberList(db);
      java.util.Iterator i = thisProject.getTeam().iterator();
      while (i.hasNext()) {
        TeamMember thisMember = (TeamMember) i.next();
        User thisUser = new User();
        thisUser.setBuildContact(true);
        thisUser.setBuildContactDetails(true);
        thisUser.buildRecord(db, thisMember.getUserId());
        thisMember.setUser(thisUser);
      }
      //Load priority drop-down
      LookupList priorityList = new LookupList(db, "lookup_project_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
      //Load status drop-down
      LookupList statusList = new LookupList(db, "lookup_project_status");
      context.getRequest().setAttribute("StatusList", statusList);
      //Load status percent drop-down list
      HtmlPercentList statusPercentList = new HtmlPercentList();
      context.getRequest().setAttribute(
          "StatusPercentList", statusPercentList);
      //Load LOE drop-down
      LookupList loeList = new LookupList(db, "lookup_project_loe");
      context.getRequest().setAttribute("LoeList", loeList);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (isPopup(context)) {
      return ("PopupOK");
    } else {
      return ("ProjectCenterOK");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddFolder(ActionContext context) {
    String projectId = (String) context.getRequest().getParameter("pid");
    checkReturnPage(context);
    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("assignments_folder_add").toLowerCase());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    String popUp = context.getRequest().getParameter("popup");
    if (popUp != null && !"null".equals(popUp)) {
      return ("PopupOK");
    } else {
      return ("ProjectCenterOK");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveFolder(ActionContext context) {
    Connection db = null;
    int resultCount = -1;
    boolean recordInserted = false;
    boolean isValid = false;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    checkReturnPage(context);
    AssignmentFolder thisFolder = (AssignmentFolder) context.getFormBean();
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("assignments_add").toLowerCase());
      //Process the folder
      thisFolder.setModifiedBy(getUserId(context));
      if (thisFolder.getId() > 0) {
        thisFolder.setProjectId(thisProject.getId());
        isValid = this.validateObject(context, db, thisFolder);
        if (isValid) {
          resultCount = thisFolder.update(db, context);
          indexAddItem(context, thisFolder);
        }
      } else {
        thisFolder.setEnteredBy(getUserId(context));
        isValid = this.validateObject(context, db, thisFolder);
        if (isValid) {
          recordInserted = thisFolder.insert(db);
          indexAddItem(context, thisFolder);
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
    if (resultCount == 0) {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    } else if (recordInserted || resultCount == 1) {
      if ("true".equals(context.getRequest().getParameter("donew"))) {
        context.getRequest().removeAttribute("assignmentFolder");
        AssignmentFolder empty = new AssignmentFolder();
        empty.setIndent(thisFolder.getIndent());
        empty.setPrevIndent(thisFolder.getIndent());
        empty.setPrevMapId(thisFolder.getPrevMapId());
        context.getRequest().setAttribute("assignmentFolder", empty);
        return (executeCommandAddFolder(context));
      }
      String popUp = context.getRequest().getParameter("popup");
      if (popUp != null && !"null".equals(popUp)) {
        return "PopupCloseOK";
      } else {
        return ("SaveOK");
      }
    }
    return (executeCommandAddFolder(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    Connection db = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    String assignmentId = (String) context.getRequest().getParameter("aid");
    boolean recordDeleted = false;
    try {
      db = getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify")) {
        return "PermissionError";
      }

      Assignment thisAssignment = new Assignment(
          db, Integer.parseInt(assignmentId), thisProject.getId());
      recordDeleted = thisAssignment.delete(db);
      indexDeleteItem(context, thisAssignment);
      if (!recordDeleted) {
        SystemStatus systemStatus = this.getSystemStatus(context);
        thisAssignment.getErrors().put(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.assignmentDoesNotExist"));
        processErrors(context, thisAssignment.getErrors());
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "DeleteOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    String projectId = (String) context.getRequest().getParameter("pid");
    String requirementId = (String) context.getRequest().getParameter("rid");
    String assignmentId = (String) context.getRequest().getParameter("aid");
    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify")) {
        return "PermissionError";
      }
      Requirement thisRequirement = new Requirement(
          db, Integer.parseInt(requirementId), thisProject.getId());
      thisRequirement.buildFolderHierarchy(db);
      Assignment thisAssignment = new Assignment(
          db, Integer.parseInt(assignmentId), thisProject.getId());
      context.getRequest().setAttribute("project", thisProject);
      context.getRequest().setAttribute("requirement", thisRequirement);
      context.getRequest().setAttribute("assignment", thisAssignment);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "MoveOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveMove(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String assignmentId = (String) context.getRequest().getParameter("aid");
    String newFolderId = (String) context.getRequest().getParameter("parent");
    try {
      checkReturnPage(context);
      db = getConnection(context);
      //Load the project and permissions
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify")) {
        return "PermissionError";
      }
      //Load the assignment
      Assignment thisAssignment = new Assignment(
          db, Integer.parseInt(assignmentId), thisProject.getId());
      thisAssignment.updateFolderId(db, Integer.parseInt(newFolderId));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "PopupCloseOK";
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
  public String executeCommandDeleteFolder(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;

    String projectId = (String) context.getRequest().getParameter("pid");
    String folderId = (String) context.getRequest().getParameter("folderId");

    boolean recordDeleted = false;
    try {
      db = getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify")) {
        return "PermissionError";
      }
      AssignmentFolder thisFolder = new AssignmentFolder(
          db, Integer.parseInt(folderId), thisProject.getId());
      recordDeleted = thisFolder.delete(db);
      if (!recordDeleted) {
        SystemStatus systemStatus = this.getSystemStatus(context);
        thisFolder.getErrors().put(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.folderDeletion"));
        processErrors(context, thisFolder.getErrors());
      } else {
        indexDeleteItem(context, thisFolder);
        context.getRequest().setAttribute("pid", projectId);
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyFolder(ActionContext context) {
    Exception errorMessage = null;

    String projectId = (String) context.getRequest().getParameter("pid");
    String folderId = (String) context.getRequest().getParameter("folderId");
    checkReturnPage(context);

    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("assignments_folder_add").toLowerCase());
      //Load the folder
      AssignmentFolder thisFolder = new AssignmentFolder(
          db, Integer.parseInt(folderId), thisProject.getId());
      context.getRequest().setAttribute("assignmentFolder", thisFolder);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      String popUp = context.getRequest().getParameter("popup");
      if (popUp != null && !"null".equals(popUp)) {
        return ("PopupOK");
      } else {
        return ("ProjectCenterOK");
      }
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
  public String executeCommandFolderDetails(ActionContext context) {
    String projectId = (String) context.getRequest().getParameter("pid");
    String folderId = (String) context.getRequest().getParameter("folderId");
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
      context.getRequest().setAttribute(
          "IncludeSection", ("assignments_folder_details").toLowerCase());
      //Load the folder
      AssignmentFolder thisFolder = new AssignmentFolder(
          db, Integer.parseInt(folderId), thisProject.getId());
      context.getRequest().setAttribute("assignmentFolder", thisFolder);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    String popUp = context.getRequest().getParameter("popup");
    if (popUp != null && !"null".equals(popUp)) {
      context.getRequest().setAttribute("popup", "true");
      return ("PopupDetailsOK");
    } else {
      return ("ProjectCenterOK");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   */
  private static void checkReturnPage(ActionContext context) {
    String returnPage = (String) context.getRequest().getParameter("return");
    if (returnPage == null) {
      returnPage = (String) context.getRequest().getAttribute("return");
    }
    context.getRequest().setAttribute("return", returnPage);
    //1st param
    String param = (String) context.getRequest().getParameter("param");
    if (param == null) {
      param = (String) context.getRequest().getAttribute("param");
    }
    context.getRequest().setAttribute("param", param);
    //2nd param
    String param2 = (String) context.getRequest().getParameter("param2");
    if (param2 == null) {
      param2 = (String) context.getRequest().getAttribute("param2");
    }
    context.getRequest().setAttribute("param2", param2);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public synchronized String executeCommandMoveItem(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String requirementId = (String) context.getRequest().getParameter("rid");
    String assignmentId = (String) context.getRequest().getParameter("aid");
    String folderId = (String) context.getRequest().getParameter("folderId");
    String direction = (String) context.getRequest().getParameter("dir");
    try {
      //this.checkReturnPage(context);
      db = getConnection(context);
      //Load the project and permissions
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify")) {
        return "PermissionError";
      }
      //Configure the item to be moved
      RequirementMapItem mapItem = new RequirementMapItem();
      mapItem.setProjectId(Integer.parseInt(projectId));
      mapItem.setRequirementId(Integer.parseInt(requirementId));
      mapItem.setFolderId(DatabaseUtils.parseInt(folderId, -1));
      mapItem.setAssignmentId(DatabaseUtils.parseInt(assignmentId, -1));
      try {
        db.setAutoCommit(false);
        if ("r".equals(direction)) {
          mapItem.moveRight(db);
        } else if ("l".equals(direction)) {
          mapItem.moveLeft(db);
        } else if ("u".equals(direction)) {
          mapItem.moveUp(db);
        } else if ("d".equals(direction)) {
          mapItem.moveDown(db);
        }
        db.commit();
      } catch (SQLException e) {
        db.rollback();
      } finally {
        db.setAutoCommit(true);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "MoveItemOK";
  }


  /**
   * Description of the Method
   *
   * @param context        Description of the Parameter
   * @param db             Description of the Parameter
   * @param thisProject    Description of the Parameter
   * @param thisAssignment Description of the Parameter
   * @return Description of the Return Value
   */
  private boolean hasModifyAccess(ActionContext context, Connection db, Project thisProject, Assignment thisAssignment) throws SQLException {
    //See if the team member has access to perform an assignment action
    TeamMember thisMember = (TeamMember) context.getRequest().getAttribute(
        "currentMember");
    if (thisMember == null) {
      try {
        //Load from project
        thisMember = new TeamMember(
            db, thisProject.getId(), this.getUserId(context));
      } catch (Exception notValid) {
        //Create a guest
        thisMember = new TeamMember();
        thisMember.setProjectId(thisProject.getId());
        thisMember.setUserLevel(getUserLevel(context, db, TeamMember.GUEST));
        thisMember.setRoleId(TeamMember.GUEST);
      }
      context.getRequest().setAttribute("currentMember", thisMember);
    }
    //Check the permission
    return (thisAssignment.getUserAssignedId() == getUserId(context) || thisMember.getRoleId() <= TeamMember.PROJECT_LEAD);
  }

  /**
   * Prepares the list of notes for the specified assignment
   *
   * @param context
   * @return
   */
  public String executeCommandShowNotes(ActionContext context) {
    String projectId = (String) context.getRequest().getParameter("pid");
    String assignmentId = (String) context.getRequest().getParameter("aid");
    Connection db = null;
    // Assignment has not been created yet (new assignment)
    if ("-1".equals(assignmentId)) {
      return "ShowNotesOK";
    }
    // Assignment exists
    try {
      db = getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-plan-outline-modify") &&
          !hasProjectAccess(context, db, thisProject, "project-plan-view")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("project", thisProject);
      // Check assignment details
      Assignment thisAssignment = new Assignment(
          db, Integer.parseInt(assignmentId), thisProject.getId());
      context.getRequest().setAttribute("assignment", thisAssignment);
      // Get the notes
      AssignmentNoteList assignmentNoteList = new AssignmentNoteList();
      assignmentNoteList.setAssignmentId(thisAssignment.getId());
      assignmentNoteList.buildList(db);
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "assignmentNoteList", assignmentNoteList);
      context.getRequest().setAttribute(
          "IncludeSection", ("assignments_notes").toLowerCase());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    String popUp = context.getRequest().getParameter("popup");
    if (popUp != null && !"null".equals(popUp)) {
      context.getRequest().setAttribute("popup", "true");
      return "ShowNotesOK";
    } else {
      return "ProjectCenterOK";
    }
  }
}

