/*
 *  Copyright 2001 Dark Horse Ventures
 *  Uses iteam objects from matt@zeroio.com http://www.mavininteractive.com
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.zeroio.iteam.base.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    November 28, 2001
 *@version    $Id$
 */
public final class ProjectManagementAssignments extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandAdd(ActionContext context) {
	  
/* 	if (!(hasPermission(context, "projects-activities-add"))) {
	    return ("PermissionError");
    	}
*/
    Exception errorMessage = null;

    String projectId = (String)context.getRequest().getParameter("pid");

    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("assignments_add").toLowerCase());
      
      RequirementList requirements = new RequirementList();
      requirements.setProject(thisProject);
      requirements.setProjectId(thisProject.getId());
      requirements.buildList(db);
      requirements.setEmptyHtmlSelectRecord("-- Select Requirement --");
      context.getRequest().setAttribute("RequirementList", requirements);
      
      thisProject.buildTeamMemberList(db);
      java.util.Iterator i = thisProject.getTeam().iterator();
      while (i.hasNext()) {
        TeamMember thisMember = (TeamMember)i.next();
        User thisUser = new User();
        thisUser.setBuildContact(true);
        thisUser.buildRecord(db, thisMember.getUserId());
        thisMember.setUser(thisUser);
        thisMember.setContact(thisUser.getContact());
      }
      
      LookupList activityList = new LookupList(db, "lookup_project_activity");
      activityList.addItem(0, "-- Select Activity --");
      context.getRequest().setAttribute("ActivityList", activityList);
      
      LookupList priorityList = new LookupList(db, "lookup_project_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
      
      LookupList statusList = new LookupList(db, "lookup_project_status");
      statusList.addItem(0, "-- Select Status --");
      context.getRequest().setAttribute("StatusList", statusList);
      
      LookupList loeList = new LookupList(db, "lookup_project_loe");
      context.getRequest().setAttribute("LoeList", loeList);
      
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
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandInsert(ActionContext context) {
	  
/* 	if (!(hasPermission(context, "projects-activities-add"))) {
	    return ("PermissionError");
    	}
 */	
    Exception errorMessage = null;
    Connection db = null;

    String projectId = (String)context.getRequest().getParameter("pid");

    boolean recordInserted = false;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("assignments_add").toLowerCase());

      Assignment thisAssignment = (Assignment)context.getFormBean();
      thisAssignment.setProjectId(thisProject.getId());
      thisAssignment.setEnteredBy(getUserId(context));
      thisAssignment.setModifiedBy(getUserId(context));
      recordInserted = thisAssignment.insert(db);
      if (!recordInserted) {
        processErrors(context, thisAssignment.getErrors());
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
        if ("Requirements".equals(context.getRequest().getParameter("return"))) {
          return ("AddOKRequirements");
        } else {
          return ("AddOK");
        }
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

  public String executeCommandDetails(ActionContext context) {
	  
/* 	if (!(hasPermission(context, "projects-activities-view"))) {
	    return ("PermissionError");
    	}
 */	
    Exception errorMessage = null;

    String projectId = (String)context.getRequest().getParameter("pid");
    String assignmentId = (String)context.getRequest().getParameter("aid");
    
    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("assignments_details").toLowerCase());

      Assignment thisAssignment = new Assignment(db, Integer.parseInt(assignmentId), thisProject.getId());
      context.getRequest().setAttribute("Assignment", thisAssignment);
      
      RequirementList requirements = new RequirementList();
      requirements.setProject(thisProject);
      requirements.setProjectId(thisProject.getId());
      requirements.buildList(db);
      requirements.setEmptyHtmlSelectRecord("-- Select Requirement --");
      context.getRequest().setAttribute("RequirementList", requirements);
      
      thisProject.buildTeamMemberList(db);
      java.util.Iterator i = thisProject.getTeam().iterator();
      while (i.hasNext()) {
        TeamMember thisMember = (TeamMember)i.next();
        User thisUser = new User();
        thisUser.setBuildContact(true);
        thisUser.buildRecord(db, thisMember.getUserId());
        thisMember.setUser(thisUser);
        thisMember.setContact(thisUser.getContact());
      }
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Details", "");
    if (errorMessage == null) {
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandModify(ActionContext context) {
	  
/* 	if (!(hasPermission(context, "projects-activities-edit"))) {
	    return ("PermissionError");
    	}
 */	
    Exception errorMessage = null;

    String projectId = (String)context.getRequest().getParameter("pid");
    String assignmentId = (String)context.getRequest().getParameter("aid");
    this.checkReturnPage(context);
    
    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("assignments_modify").toLowerCase());

      Assignment thisAssignment = new Assignment(db, Integer.parseInt(assignmentId), thisProject.getId());
      context.getRequest().setAttribute("Assignment", thisAssignment);
      
      RequirementList requirements = new RequirementList();
      requirements.setProject(thisProject);
      requirements.setProjectId(thisProject.getId());
      requirements.buildList(db);
      requirements.setEmptyHtmlSelectRecord("-- Select Requirement --");
      context.getRequest().setAttribute("RequirementList", requirements);
      
      thisProject.buildTeamMemberList(db);
      java.util.Iterator i = thisProject.getTeam().iterator();
      while (i.hasNext()) {
        TeamMember thisMember = (TeamMember)i.next();
        User thisUser = new User();
        thisUser.setBuildContact(true);
        thisUser.buildRecord(db, thisMember.getUserId());
        thisMember.setUser(thisUser);
        thisMember.setContact(thisUser.getContact());
      }
      
      LookupList activityList = new LookupList(db, "lookup_project_activity");
      activityList.addItem(0, "-- Select Activity --");
      context.getRequest().setAttribute("ActivityList", activityList);
      
      LookupList priorityList = new LookupList(db, "lookup_project_priority");
      context.getRequest().setAttribute("PriorityList", priorityList);
      
      LookupList statusList = new LookupList(db, "lookup_project_status");
      statusList.addItem(0, "-- Select Status --");
      context.getRequest().setAttribute("StatusList", statusList);
      
      LookupList loeList = new LookupList(db, "lookup_project_loe");
      context.getRequest().setAttribute("LoeList", loeList);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Details", "");
    if (errorMessage == null) {
      if (context.getRequest().getParameter("popup") != null) {
        return ("PopupOK");
      } else {
        return ("ProjectCenterOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

  public String executeCommandUpdate(ActionContext context) {
	  
/* 	if (!(hasPermission(context, "projects-activities-edit"))) {
	    return ("PermissionError");
    	}
 */	
    Exception errorMessage = null;

    Assignment thisAssignment = (Assignment)context.getFormBean();
    this.checkReturnPage(context);
    
    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      
      Project thisProject = new Project(db, thisAssignment.getProjectId(), getUserRange(context));
      
      thisAssignment.setProject(thisProject);
      thisAssignment.setProjectId(thisProject.getId());
      thisAssignment.setModifiedBy(getUserId(context));
      
      resultCount = thisAssignment.update(db, context);
      if (resultCount == -1) {
        processErrors(context, thisAssignment.getErrors());
        context.getRequest().setAttribute("Project", thisProject);
        context.getRequest().setAttribute("Assignment", thisAssignment);
        context.getRequest().setAttribute("IncludeSection", ("assignments_modify").toLowerCase());
      } else {
        context.getRequest().setAttribute("pid", "" + thisProject.getId());
        context.getRequest().setAttribute("IncludeSection", ("assignments").toLowerCase());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return ("ProjectCenterOK");
      } else if (resultCount == 1) {
        if (context.getRequest().getParameter("popup") != null) {
          return "PopupCloseOK";
        } else {
          return ("UpdateOK");
        }
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  private static void checkReturnPage(ActionContext context) {
    String returnPage = (String)context.getRequest().getParameter("return");
    if (returnPage == null) returnPage = (String)context.getRequest().getAttribute("return");
    context.getRequest().setAttribute("return", returnPage);
    
    String param = (String)context.getRequest().getParameter("param");
    if (param == null) param = (String)context.getRequest().getAttribute("param");
    context.getRequest().setAttribute("param", param);
  }
}

