package com.zeroio.iteam.base;

import com.darkhorseventures.database.ConnectionPool;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.ScheduledActions;
import org.aspcfs.modules.mycfs.base.*; 
import java.util.*;
import com.zeroio.iteam.base.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    October 2, 2002
 *@version    $Id$
 *@version    $Id$
 */
 
public class ProjectListScheduledActions extends ProjectList implements ScheduledActions {

  private int userId = -1;

  /**
   *  Constructor for the ProjectListScheduledActions object
   */
  public ProjectListScheduledActions() { }

  /**
   *  Sets the userId attribute of the CallListScheduledActions object
   *
   *@param  userId  The new userId value
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   *  Gets the userId attribute of the CallListScheduledActions object
   *
   *@return    The userId value
   */
  public int getUserId() {
    return userId;
  }

  /**
   *  Description of the Method
   *
   *@param  companyCalendar  Description of the Parameter
   *@param  db               Description of the Parameter
   *@return                  Description of the Return Value
   */
  public void buildAlerts(CalendarView companyCalendar, Connection db) throws SQLException {
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProjectListScheduledActions --> Building Project Alerts ");
      }
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
          CalendarEvent thisEvent = new CalendarEvent();
          thisEvent.setDate(thisAssignment.getDueDate());
          thisEvent.setSubject(thisAssignment.getRole());
          thisEvent.setCategory("Assignments");
          thisEvent.setId(thisAssignment.getProjectId());
          thisEvent.setIdsub(thisAssignment.getId());
          thisEvent.setIcon(thisAssignment.getStatusGraphicTag());
          companyCalendar.addEvent(thisEvent);
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Project Calendar Alerts");
    }
  }
  
  public void buildAlertCount(CalendarView companyCalendar, Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ProjectListScheduledActions --> Building Alert Counts ");
    }
    try {
      this.setGroupId(-1);
      this.setOpenProjectsOnly(true);
      this.setProjectsWithAssignmentsOnly(true);
      this.setProjectsForUser(userId);
      this.setBuildAssignments(true);
      this.setAssignmentsForUser(userId);
      this.setOpenAssignmentsOnly(true);
      this.setBuildIssues(false);
      HashMap dayEvents = this.queryAssignmentRecordCount(db);
      Set s = dayEvents.keySet();
      Iterator i = s.iterator();
      while (i.hasNext()) {
        String thisDay = (String) i.next();
        companyCalendar.addEventCount(CalendarEventList.EVENT_TYPES[4], thisDay, dayEvents.get(thisDay));
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ProjectListScheduledActions --> Added Assignments for day " + thisDay + "- " + String.valueOf(dayEvents.get(thisDay)));
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Project Calendar Alert Counts");
    }
  }
}

