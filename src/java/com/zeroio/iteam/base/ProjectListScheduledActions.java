package com.darkhorseventures.cfsbase;

import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.webutils.*;
import java.util.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.zeroio.iteam.base.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    October 2, 2002
 *@version    $Id$
 */
public class ProjectListScheduledActions extends ProjectList implements ScheduledActions {

  private int userId = -1;
  private java.sql.Date alertRangeStart = null;
  private java.sql.Date alertRangeEnd = null;


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
   *  Sets the alertRangeStart attribute of the ProjectListScheduledActions
   *  object
   *
   *@param  alertRangeStart  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Date alertRangeStart) {
    this.alertRangeStart = alertRangeStart;
  }


  /**
   *  Sets the alertRangeEnd attribute of the ProjectListScheduledActions object
   *
   *@param  alertRangeEnd  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Date alertRangeEnd) {
    this.alertRangeEnd = alertRangeEnd;
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
      //build projects
      this.setGroupId(-1);
      //setOpenThisOnly(true);
      //setThisWithAssignmentsOnly(true);
      //setThisForUser(this.getUserId());
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
          if (thisAssignment.getDueDate() != null) {
            CalendarEvent thisEvent = new CalendarEvent();
            thisEvent.setDate(thisAssignment.getDueDate());
            thisEvent.setSubject(thisAssignment.getRole());
            thisEvent.setCategory("Assignment");
            thisEvent.setId(thisAssignment.getProjectId());
            thisEvent.setIdsub(thisAssignment.getId());
            thisEvent.setIcon(thisAssignment.getStatusGraphicTag());
            companyCalendar.addEvent(thisEvent);
          }
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Project Calendar Alerts");
    }
    return "CalendarProjectsOK";
  }
}

