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
 *@version    $Id: TaskListScheduledActions.java,v 1.4 2002/12/04 13:11:34
 *      mrajkowski Exp $
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
   *@param  db                Description of the Parameter
   *@param  companyCalendar   Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlerts(CalendarView companyCalendar, Connection db) throws SQLException {

    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TaskListScheduledActions --> Getting user Record for " + this.getUserId());
      }

      // Add Tasks to calendar
      this.setOwner(User.getContactId(db, this.getUserId()));
      this.buildShortList(db);
      Iterator taskList = this.iterator();
      int taskCount = 0;
      while (taskList.hasNext()) {
        Task thisTask = (Task) taskList.next();
        int status = thisTask.getComplete() ? 1 : 0;
        companyCalendar.addEvent(thisTask.getAlertDateStringLongYear(), "", thisTask.getDescription(), CalendarEventList.EVENT_TYPES[0], thisTask.getId(), -1, status);
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Task Calendar Alerts");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  companyCalendar   Description of the Parameter
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlertCount(CalendarView companyCalendar, Connection db) throws SQLException {

    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TaskListScheduledActions --> Building Alert Counts ");
      }

      // Add Tasks to calendar
      this.setOwner(User.getContactId(db, this.getUserId()));

      HashMap dayEvents = this.queryRecordCount(db);
      Set s = dayEvents.keySet();
      Iterator i = s.iterator();
      while (i.hasNext()) {
        String thisDay = (String) i.next();
        companyCalendar.addEventCount(CalendarEventList.EVENT_TYPES[0], thisDay, dayEvents.get(thisDay));
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TaskListScheduledActions --> Added Tasks for Day " + thisDay + "- " + String.valueOf(dayEvents.get(thisDay)));
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Task Calendar Alerts");
    }
  }
}

