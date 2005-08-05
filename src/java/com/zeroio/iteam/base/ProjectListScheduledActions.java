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
package com.zeroio.iteam.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.ScheduledActions;
import org.aspcfs.modules.mycfs.base.CalendarEventList;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.CalendarView;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;

/**
 * Description of the Class
 *
 * @author akhi_m
 * @version $Id: ProjectListScheduledActions.java,v 1.13 2003/09/26 18:49:28
 *          mrajkowski Exp $
 * @created October 2, 2002
 */

public class ProjectListScheduledActions extends ProjectList implements ScheduledActions {

  private ActionContext context = null;
  private CFSModule module = null;
  private int userId = -1;


  /**
   * Constructor for the ProjectListScheduledActions object
   */
  public ProjectListScheduledActions() {
  }


  /**
   * Sets the module attribute of the QuoteListScheduledActions object
   *
   * @param tmp The new module value
   */
  public void setModule(CFSModule tmp) {
    this.module = tmp;
  }


  /**
   * Sets the context attribute of the QuoteListScheduledActions object
   *
   * @param tmp The new context value
   */
  public void setContext(ActionContext tmp) {
    this.context = tmp;
  }


  /**
   * Sets the userId attribute of the ProjectListScheduledActions object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the ProjectListScheduledActions object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   * Gets the context attribute of the QuoteListScheduledActions object
   *
   * @return The context value
   */
  public ActionContext getContext() {
    return context;
  }


  /**
   * Gets the module attribute of the QuoteListScheduledActions object
   *
   * @return The module value
   */
  public CFSModule getModule() {
    return module;
  }


  /**
   * Gets the userId attribute of the ProjectListScheduledActions object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Description of the Method
   *
   * @param companyCalendar Description of the Parameter
   * @param db              Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildAlerts(CalendarView companyCalendar, Connection db) throws SQLException {
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ProjectListScheduledActions-> Building Project Alerts ");
      }

      /*
       *  /get the userId
       *  int userId = module.getUserId(context);
       */
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
          String dueDate = DateUtils.getServerToUserDateString(
              timeZone, DateFormat.SHORT, thisAssignment.getDueDate());
          thisAssignment.setProject(thisProject);
          companyCalendar.addEvent(
              dueDate, CalendarEventList.EVENT_TYPES[8], thisAssignment);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      throw new SQLException("Error Building Project Calendar Alerts");
    }
  }


  /**
   * Description of the Method
   *
   * @param companyCalendar Description of the Parameter
   * @param db              Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildAlertCount(CalendarView companyCalendar, Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "ProjectListScheduledActions-> Building Alert Counts ");
    }
    try {
      /*
       *  /get the userId
       *  int userId = module.getUserId(context);
       */
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
        companyCalendar.addEventCount(
            thisDay, CalendarEventList.EVENT_TYPES[8], dayEvents.get(thisDay));
        if (System.getProperty("DEBUG") != null) {
          System.out.println(
              "ProjectListScheduledActions-> Added Assignments for day " + thisDay + "- " + String.valueOf(
                  dayEvents.get(thisDay)));
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Project Calendar Alert Counts");
    }
  }
}

