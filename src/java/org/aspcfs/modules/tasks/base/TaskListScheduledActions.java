/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.tasks.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.ScheduledActions;
import org.aspcfs.modules.mycfs.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.admin.base.User;
import java.text.DateFormat;
import java.util.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    October 2, 2002
 *@version    $Id: TaskListScheduledActions.java,v 1.4 2002/12/04 13:11:34
 *      mrajkowski Exp $
 */
public class TaskListScheduledActions extends TaskList implements ScheduledActions {

  private ActionContext context = null;
  private CFSModule module = null;



  /**
   *  Constructor for the TaskListScheduledActions object
   */
  public TaskListScheduledActions() { }


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
   *  Constructor for the calendarActions object
   *
   *@param  db                Description of the Parameter
   *@param  companyCalendar   Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlerts(CalendarView companyCalendar, Connection db) throws SQLException {

    try {
      //get the userId
      int userId = module.getUserId(context);

      if (System.getProperty("DEBUG") != null) {
        System.out.println("TaskListScheduledActions-> Building Task Alerts for user " + userId);
      }

      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      // Add Tasks to calendar details
      this.setOwner(userId);
      this.setComplete(Constants.FALSE);
      this.buildShortList(db);
      Iterator taskList = this.iterator();
      int taskCount = 0;
      while (taskList.hasNext()) {
        Task thisTask = (Task) taskList.next();
        thisTask.buildResources(db);
        String alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, thisTask.getDueDate());
        companyCalendar.addEvent(alertDate, CalendarEventList.EVENT_TYPES[0], thisTask);
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
   *@exception  SQLException  Description of the Exception
   */
  public void buildAlertCount(CalendarView companyCalendar, Connection db) throws SQLException {

    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TaskListScheduledActions-> Building Alert Count ");
      }

      //get the user
      int userId = module.getUserId(context);

      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      // Add Task count to calendar
      this.setOwner(userId);
      this.setComplete(Constants.FALSE);
      HashMap dayEvents = this.queryRecordCount(db, timeZone);
      Set s = dayEvents.keySet();
      Iterator i = s.iterator();
      while (i.hasNext()) {
        String thisDay = (String) i.next();
        companyCalendar.addEventCount(thisDay, CalendarEventList.EVENT_TYPES[0], dayEvents.get(thisDay));
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TaskListScheduledActions-> Added Tasks for " + thisDay + "- " + String.valueOf(dayEvents.get(thisDay)));
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Task Calendar Alerts: " + e.getMessage());
    }
  }
}

