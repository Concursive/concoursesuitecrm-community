/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.ScheduledActions;
import org.aspcfs.modules.mycfs.base.*;
import java.text.DateFormat;
import java.util.*;
import com.zeroio.iteam.base.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    October 2, 2002
 *@version    $Id: ProjectListScheduledActions.java,v 1.13 2003/09/26 18:49:28
 *      mrajkowski Exp $
 */

public class ProjectListScheduledActions extends ProjectList implements ScheduledActions {

  private ActionContext context = null;
  private CFSModule module = null;


  /**
   *  Constructor for the ProjectListScheduledActions object
   */
  public ProjectListScheduledActions() { }


  /**
   *  Sets the module attribute of the QuoteListScheduledActions object
   *
   *@param  tmp  The new module value
   */
  public void setModule(CFSModule tmp) {
    this.module = tmp;
  }


  /**
   *  Sets the context attribute of the QuoteListScheduledActions object
   *
   *@param  tmp  The new context value
   */
  public void setContext(ActionContext tmp) {
    this.context = tmp;
  }


  /**
   *  Gets the context attribute of the QuoteListScheduledActions object
   *
   *@return    The context value
   */
  public ActionContext getContext() {
    return context;
  }


  /**
   *  Gets the module attribute of the QuoteListScheduledActions object
   *
   *@return    The module value
   */
  public CFSModule getModule() {
    return module;
  }


  /**
   *  Description of the Method
   *
   *@param  companyCalendar   Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlerts(CalendarView companyCalendar, Connection db) throws SQLException {
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProjectListScheduledActions-> Building Project Alerts ");
      }
      
      //get the userId
      int userId = module.getUserId(context);
      
      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      this.setGroupId(-1);
      this.setOpenProjectsOnly(true);
      this.setProjectsWithAssignmentsOnly(true);
      this.setProjectsForUser(userId);
      this.setBuildAssignments(true);
      this.setAssignmentsForUser(userId);
      this.setOpenAssignmentsOnly(true);
      this.setBuildIssues(false);
      this.buildList(db);

      Iterator projectList = this.iterator();
      while (projectList.hasNext()) {
        com.zeroio.iteam.base.Project thisProject = (com.zeroio.iteam.base.Project) projectList.next();
        Iterator assignmentList = thisProject.getAssignments().iterator();
        while (assignmentList.hasNext()) {
          com.zeroio.iteam.base.Assignment thisAssignment = (com.zeroio.iteam.base.Assignment) assignmentList.next();
          String tmp = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, thisAssignment.getDueDate());
          java.sql.Timestamp dueDate = DateUtils.parseTimestampString(tmp);
          CalendarEvent thisEvent = new CalendarEvent();
          thisEvent.setDate(dueDate);
          thisEvent.setSubject(thisAssignment.getRole());
          thisEvent.setCategory("Assignments");
          thisEvent.setId(thisAssignment.getProjectId());
          thisEvent.setIdsub(thisAssignment.getId());
          thisEvent.setIcon(thisAssignment.getStatusGraphicTag());
          //companyCalendar.addEvent(thisEvent);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      throw new SQLException("Error Building Project Calendar Alerts");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  companyCalendar   Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlertCount(CalendarView companyCalendar, Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ProjectListScheduledActions-> Building Alert Counts ");
    }
    try {
      
      //get the userId
      int userId = module.getUserId(context);
      
      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      this.setGroupId(-1);
      this.setOpenProjectsOnly(true);
      this.setProjectsWithAssignmentsOnly(true);
      this.setProjectsForUser(userId);
      this.setBuildAssignments(true);
      this.setAssignmentsForUser(userId);
      this.setOpenAssignmentsOnly(true);
      this.setBuildIssues(false);
      HashMap dayEvents = this.queryAssignmentRecordCount(db, timeZone);
      Set s = dayEvents.keySet();
      Iterator i = s.iterator();
      while (i.hasNext()) {
        String thisDay = (String) i.next();
        companyCalendar.addEventCount(CalendarEventList.EVENT_TYPES[8], thisDay, dayEvents.get(thisDay));
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ProjectListScheduledActions-> Added Assignments for day " + thisDay + "- " + String.valueOf(dayEvents.get(thisDay)));
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Project Calendar Alert Counts");
    }
  }
}

