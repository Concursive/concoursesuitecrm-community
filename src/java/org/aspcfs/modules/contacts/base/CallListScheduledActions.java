/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.contacts.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.ScheduledActions;
import org.aspcfs.modules.mycfs.base.CalendarEventList;
import org.aspcfs.modules.mycfs.base.CallEventList;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.CalendarView;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

/**
 * Description of the Class
 *
 * @author akhi_m
 * @version $Id: CallListScheduledActions.java,v 1.2 2002/10/04 19:25:45
 *          mrajkowski Exp $
 * @created October 2, 2002
 */
public class CallListScheduledActions extends CallList implements ScheduledActions {

  private ActionContext context = null;
  private CFSModule module = null;
  private int userId = -1;


  /**
   * Constructor for the CallListScheduledActions object
   */
  public CallListScheduledActions() {
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
   * Sets the userId attribute of the CallListScheduledActions object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the CallListScheduledActions object
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
   * Gets the userId attribute of the CallListScheduledActions object
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
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CallListScheduledActions --> Building Call Alerts ");
    }
    try {
      //get the userId
      //int userId = module.getUserId(context);

      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      //add all pending activities
      this.setOnlyPending(true);
      this.setOwner(userId);
      this.setEnteredBy(-1);
      this.buildShortList(db);
      Iterator m = this.iterator();
      while (m.hasNext()) {
        Call thisCall = (Call) m.next();
        if(thisCall.getAlertDate() != null){
          String alertDate = DateUtils.getServerToUserDateString(
              timeZone, DateFormat.SHORT, thisCall.getAlertDate());
          CallEventList thisList = (CallEventList) companyCalendar.getEventList(
              alertDate, CalendarEventList.EVENT_TYPES[1]);
          thisList.getPendingCalls().add(thisCall);
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "CallListScheduledActions-> Pending Call: " + thisCall.getAlertText() + " added on " + alertDate);
          }
        }
      }

      //add all completed activities
      this.clear();
      this.setOnlyPending(false);
      this.setOnlyCompleted(true);
      this.setOwner(-1);
      this.setEnteredBy(userId);
      this.buildShortList(db);
      m = this.iterator();
      while (m.hasNext()) {
        Call thisCall = (Call) m.next();
        if(thisCall.getCallStartDate() != null){
          String alertDate = DateUtils.getServerToUserDateString(
              timeZone, DateFormat.SHORT, thisCall.getCallStartDate());
          CallEventList thisList = (CallEventList) companyCalendar.getEventList(
              alertDate, CalendarEventList.EVENT_TYPES[1]);
          thisList.getCompletedCalls().add(thisCall);
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "CallListScheduledActions-> Complete Call: " + thisCall.getSubject() + " added on " + alertDate);
          }
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Call Calendar Alerts: " + e.getMessage());
    }
  }


  /**
   * Build event categories and count of occurance of each category.
   *
   * @param companyCalendar Description of the Parameter
   * @param db              Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildAlertCount(CalendarView companyCalendar, Connection db) throws SQLException {

    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "CallListScheduledActions --> Building Alert Counts ");
    }
    try {
      //get the userId
      //int userId = module.getUserId(context);

      //get TimeZone
      TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();

      //add pending activities count
      this.setOwner(userId);
      this.setOnlyPending(true);
      HashMap pendingEvents = this.queryRecordCount(db, timeZone);
      Iterator j = pendingEvents.keySet().iterator();
      while (j.hasNext()) {
        String thisDay = (String) j.next();
        companyCalendar.addEventCount(
            thisDay, CalendarEventList.EVENT_TYPES[13], pendingEvents.get(
                thisDay));
      }

      //add completed activities count
      this.clear();
      this.setOnlyPending(false);
      this.setOnlyCompleted(true);
      this.setOwner(-1);
      this.setEnteredBy(userId);
      HashMap completedEvents = this.queryRecordCount(db, timeZone);
      Iterator comp = completedEvents.keySet().iterator();
      while (comp.hasNext()) {
        String thisDay = (String) comp.next();
        companyCalendar.addEventCount(
            thisDay, CalendarEventList.EVENT_TYPES[1], completedEvents.get(
                thisDay));
      }
    } catch (SQLException e) {
      throw new SQLException("Error Building Call Calendar Alert Counts");
    }
  }
}

