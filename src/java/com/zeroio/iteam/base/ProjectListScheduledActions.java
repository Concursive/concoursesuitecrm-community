package com.darkhorseventures.cfsbase;

import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.CalendarView;
import java.util.*;
import com.zeroio.iteam.base.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    October 2, 2002
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
  public String calendarAlerts(CalendarView companyCalendar, Connection db) throws SQLException{

    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProjectListScheduledActions --> Building Project Alerts ");
      }
      this.setGroupId(-1);
      this.setOpenProjectsOnly(true);
      this.setProjectsWithAssignmentsOnly(true);
      this.setProjectsForUser(this.getUserId());
      this.setBuildAssignments(true);
      this.setAssignmentsForUser(this.getUserId());
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
    return "CalendarProjectsOK";
  }
}

