package com.darkhorseventures.cfsbase;

import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    October 2, 2002
 *@version    $Id$
 */
public class TaskListScheduledActions extends TaskList implements ScheduledActions {

  private int userId = -1;


  /**
   *  Constructor for the TaskListScheduledActions object
   */
  public TaskListScheduledActions() { }


  /**
   *  Sets the userId attribute of the TaskListScheduledActions object
   *
   *@param  userId  The new userId value
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }


  /**
   *  Gets the userId attribute of the TaskListScheduledActions object
   *
   *@return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Constructor for the calendarActions object
   *
   *@param  db               Description of the Parameter
   *@param  companyCalendar  Description of the Parameter
   *@return                  Description of the Return Value
   */
  public String calendarAlerts(CalendarView companyCalendar, Connection db) throws SQLException{

    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TaskListScheduledActions --> Getting user Record for " + this.getUserId());
      }
      User thisUser = new User(db, this.getUserId());

      // Add Tasks to calendar
      this.setEnteredBy(this.getUserId());
      this.setTasksAssignedToMeOnly(true);
      this.setOwner(thisUser.getContactId());
      this.buildList(db);
      System.out.println("TaskListScheduledActions --> Getting Tasks ");
      Iterator taskList = this.iterator();
      int taskCount = 0;
      while (taskList.hasNext()) {
        Task thisTask = (Task) taskList.next();
        int status = thisTask.getComplete() ? 1 : 0;
        companyCalendar.addEvent(thisTask.getAlertDateStringLongYear(), "", thisTask.getDescription(), "Tasks", thisTask.getId(), -1, status);
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Task Calendar Alerts");
    }
    return "CalendarTasksOK";
  }
}

