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
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.modules.admin.base.User;

import java.io.*;
import java.util.*;
import com.jrefinery.chart.*;
import com.jrefinery.chart.data.*;
import com.jrefinery.chart.ui.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.entity.StandardEntityCollection;
import com.jrefinery.chart.tooltips.TimeSeriesToolTipGenerator;
import java.awt.Color;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
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
        processInsertHook(context, thisAssignment);
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
  
  
  public String executeCommandGantt(ActionContext context) {
    Exception errorMessage = null;
    String projectId = (String)context.getRequest().getParameter("pid");
    Connection db = null;
    Project thisProject = null;
    LookupList activityList = null;
    try {
      db = getConnection(context);
      thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("assignments_add").toLowerCase());
      thisProject.setBuildRequirementAssignments(true);
      thisProject.buildRequirementList(db);
      activityList = new LookupList(db, "lookup_project_activity");
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    //Now that the data is ready, compile it into a Gantt chart
    GanttSeriesCollection categoryData = new GanttSeriesCollection();
    Iterator requirements = thisProject.getRequirements().iterator();
    GanttSeries series = new GanttSeries("Scheduled");
    while (requirements.hasNext()) {
      Requirement thisRequirement = (Requirement) requirements.next();
      Iterator assignments = thisRequirement.getAssignments().iterator();
      while (assignments.hasNext()) {
        Assignment thisAssignment = (Assignment) assignments.next();
        java.util.Date start = thisAssignment.getStartDate();
        if (start == null) {
          start = thisAssignment.getAssignDate();
        }
        
        java.util.Date end = null;
        if (thisAssignment.getComplete()) {
          end = thisAssignment.getCompleteDate();
        } else {
          end = thisAssignment.getDueDate();
        }
        
        if (end == null) {
          end = start;
        }
        
        if (start != null && end != null) {
          if (end.before(start)) {
            end.setTime(start.getTime() + 60000);
          }
          
          TimeAllocation timeFrame = new TimeAllocation(start, end);
          series.add(thisAssignment.getActivity(), timeFrame);
          
          System.out.println("ADDED-----> " + thisAssignment.getRole() + String.valueOf(start) + " " + String.valueOf(end));
        }
      }
    }
    
    if (series.getItemCount() > 0) {
      System.out.println("ProjectManagementAssignments-> Gantt Series: " + series.getItemCount());
      categoryData.add(series);
    }
    
    System.out.println("ProjectManagementAssignments-> Gantt Categories: " + categoryData.getCategoryCount());
    
    IntervalCategoryDataset dataset = categoryData;
    
    JFreeChart chart = ChartFactory.createGanttChart(thisProject.getShortDescription(),  // chart title
        "Task",              // domain axis label
        "Date",              // range axis label
        dataset,             // data
        true,                 // include legend
        true,                // tooltips
        false                // urls
        );
    chart.setBackgroundPaint(Color.white);
    int width = 600;
    int height = 500;
    try {
      String realPath = context.getServletContext().getRealPath("/");
      String filePath = realPath + "graphs" + fs;
      java.util.Date testDate = new java.util.Date();
      String fileName = String.valueOf(getUserId(context)) + String.valueOf(testDate.getTime()) + String.valueOf(context.getSession().getCreationTime());

      // Write the chart image
      File imageFile = new File(filePath + fileName + ".jpg");
      ChartUtilities.saveChartAsJPEG(imageFile, 1.0f, chart, width, height);
    } catch (IOException e) {
      e.printStackTrace(System.out);
    }
      
    addModuleBean(context, "Gantt", "");
    if (errorMessage == null) {
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

