/*
 *  Copyright 2001 Dark Horse Ventures
 *  Uses iteam objects from matt@zeroio.com http://www.mavininteractive.com
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.zeroio.iteam.base.*;

/**
 *  Project Management module for CFS
 *
 *@author     matt
 *@created    November 6, 2001
 *@version    $Id$
 */
public final class ProjectManagement extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDefault(ActionContext context) {
    //based on user settings...go to their default page
    return executeCommandPersonalView(context);
  }


  /**
   *  Lists the users in the system that have a corresponding contact record
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.6
   */
  public String executeCommandPersonalView(ActionContext context) {
    Exception errorMessage = null;

    Connection db = null;
    ProjectList projects = new ProjectList();

    try {
      db = getConnection(context);
      //Project Info
      projects.setGroupId(-1);
      projects.setOpenProjectsOnly(true);
      projects.setProjectsForUser(getUserId(context));
      //Assignment Info
      projects.setBuildAssignments(true);
      projects.setAssignmentsForUser(getUserId(context));
      projects.setOpenAssignmentsOnly(true);
      projects.setWithAssignmentDaysComplete(6);
      //Issue Info
      projects.setBuildIssues(true);
      projects.setLastIssues(3);
      projects.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "PersonalView", "");
    if (errorMessage == null) {
      context.getRequest().setAttribute("ProjectList", projects);
      return ("PersonalViewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandEnterpriseView(ActionContext context) {
    Exception errorMessage = null;

    Connection db = null;
    ProjectList projects = new ProjectList();

    try {
      db = getConnection(context);
      //Project Info
      projects.setGroupId(-1);
      //projects.setOpenProjectsOnly(true);
      //projects.setProjectsWithAssignmentsOnly(true);
      projects.setUserRange(getUserRange(context));
      //Assignment Info
      projects.setBuildAssignments(true);
      //projects.setAssignmentsForUser(getUserId(context));
      projects.setOpenAssignmentsOnly(true);
      projects.setWithAssignmentDaysComplete(6);
      //Issue Info
      projects.setBuildIssues(true);
      projects.setLastIssues(3);
      projects.buildList(db);
      
      Iterator i = projects.iterator();
      while (i.hasNext()) {
        Project thisProject = (Project)i.next();
        Iterator assignments = thisProject.getAssignments().iterator();
        while (assignments.hasNext()) {
          Assignment thisAssignment = (Assignment)assignments.next();
          Contact userAssigned = this.getUser(context, thisAssignment.getUserAssignedId()).getContact();
          thisAssignment.setUserAssigned(userAssigned.getNameFirstLast());
        }
      }
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "EnterpriseView", "");
    if (errorMessage == null) {
      context.getRequest().setAttribute("ProjectList", projects);
      return ("EnterpriseViewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandAddProject(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(0, "--None--");
      context.getRequest().setAttribute("DepartmentList", departmentList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "AddProject", "");
    if (errorMessage == null) {
      return ("AddProjectOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsertProject(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    try {
      db = getConnection(context);
      Project thisProject = (Project)context.getFormBean();
      thisProject.setEnteredBy(getUserId(context));
      thisProject.setModifiedBy(getUserId(context));
      recordInserted = thisProject.insert(db, context);
      if (!recordInserted) {
        processErrors(context, thisProject.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return (executeCommandEnterpriseView(context));
      } else {
        return (executeCommandAddProject(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandModifyProject(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    
    Project thisProject = null;
    String projectId = (String)context.getRequest().getParameter("pid");

    try {
      db = this.getConnection(context);
      thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("modifyproject").toLowerCase());
      
      LookupList departmentList = new LookupList(db, "lookup_department");
      departmentList.addItem(0, "--None--");
      context.getRequest().setAttribute("DepartmentList", departmentList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      //return ("ModifyProjectOK");
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandUpdateProject(ActionContext context) {
    Exception errorMessage = null;

    Project thisProject = (Project)context.getFormBean();
    //thisProject.setRequestItems(context.getRequest());
    //Organization thisOrganization = null;
    //String orgid = context.getRequest().getParameter("orgId");
    
    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      thisProject.setModifiedBy(getUserId(context));
      resultCount = thisProject.update(db, context);
      if (resultCount == -1) {
        processErrors(context, thisProject.getErrors());
        LookupList departmentList = new LookupList(db, "lookup_department");
        departmentList.addItem(0, "--None--");
        context.getRequest().setAttribute("DepartmentList", departmentList);
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        context.getRequest().setAttribute("Project", thisProject);
        return ("ModifyProjectOK");
      } else if (resultCount == 1) {
        context.getRequest().setAttribute("pid", "" + thisProject.getId());
        return ("UpdateProjectOK");
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
  
  
  
  public String executeCommandProjectCenter(ActionContext context) {
    Exception errorMessage = null;

    Connection db = null;
    Project thisProject = null;
    String projectId = (String)context.getRequest().getParameter("pid");
    if (projectId == null) {
      projectId = (String)context.getRequest().getAttribute("pid");
    }
    
    String section = (String)context.getRequest().getParameter("section");
    if (section == null || section.equals("")) {
      section = "Home";
    }
    
    try {
      db = getConnection(context);
      thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if ("Requirements".equals(section)) {
        thisProject.buildRequirementList(db);
        Iterator i = thisProject.getRequirements().iterator();
        while (i.hasNext()) {
          Requirement thisRequirement = (Requirement)i.next();
          Contact enteredBy = this.getUser(context, thisRequirement.getEnteredBy()).getContact();
          Contact modifiedBy = this.getUser(context, thisRequirement.getModifiedBy()).getContact();
          thisRequirement.setEnteredByString(enteredBy.getNameFirstLast());
          thisRequirement.setModifiedByString(modifiedBy.getNameFirstLast()); 
        }
      } else if ("Team".equals(section)) {
        thisProject.buildTeamMemberList(db);
        Iterator i = thisProject.getTeam().iterator();
        while (i.hasNext()) {
          TeamMember thisMember = (TeamMember)i.next();
          User thisUser = this.getUser(context, thisMember.getUserId());
          thisMember.setUser(thisUser);
          thisMember.setContact(thisUser.getContact());
        }
      } else if ("Assignments".equals(section)) {
        thisProject.buildAssignmentList(db);
        Iterator i = thisProject.getAssignments().iterator();
        while (i.hasNext()) {
          Assignment thisAssignment = (Assignment)i.next();
          Contact userAssigned = this.getUser(context, thisAssignment.getUserAssignedId()).getContact();
          thisAssignment.setUserAssigned(userAssigned.getNameFirstLast()); 
        }
      } else if ("Issues".equals(section)) {
        thisProject.buildIssueList(db);
        Iterator i = thisProject.getIssues().iterator();
        while (i.hasNext()) {
          Issue thisIssue = (Issue)i.next();
          Contact user = this.getUser(context, thisIssue.getEnteredBy()).getContact();
          thisIssue.setUser(user.getNameFirstLast()); 
        }
      } else if ("File_Library".equals(section)) {
        thisProject.buildFileItemList(db);
        Iterator i = thisProject.getFiles().iterator();
        while (i.hasNext()) {
          FileItem thisItem = (FileItem)i.next();
          Contact user = this.getUser(context, thisItem.getEnteredBy()).getContact();
          thisItem.setEnteredByString(user.getNameFirstLast()); 
        }
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", section.toLowerCase());
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
  
}

