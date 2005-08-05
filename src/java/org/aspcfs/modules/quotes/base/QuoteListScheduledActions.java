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
package org.aspcfs.modules.quotes.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.ScheduledActions;
import org.aspcfs.utils.web.CalendarView;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $id:exp$
 * @created July 16, 2004
 */
public class QuoteListScheduledActions extends QuoteList implements ScheduledActions {

  private ActionContext context = null;
  private CFSModule module = null;
  private int userId = -1;


  /**
   * Constructor for the QuoteListScheduledActions object
   */
  public QuoteListScheduledActions() {
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
   * Sets the userId attribute of the QuoteListScheduledActions object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the QuoteListScheduledActions object
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
   * Gets the userId attribute of the QuoteListScheduledActions object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Constructor for the calendarActions object
   *
   * @param db              Description of the Parameter
   * @param companyCalendar Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildAlerts(CalendarView companyCalendar, Connection db) throws SQLException {

    /*
     *  try {
     *  if (System.getProperty("DEBUG") != null) {
     *  System.out.println("QuoteListScheduledActions-> Building Quote Alerts for user " + module.getUserId(context));
     *  }
     *  /get User
     *  User thisUser = module.getUser(context, module.getUserId(context));
     *  /get TimeZone
     *  TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();
     *  Timestamp todayTimestamp = new Timestamp(System.currentTimeMillis());
     *  String alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, todayTimestamp);
     *  / Retrieve the quote status lookup list
     *  SystemStatus systemStatus = module.getSystemStatus(context);
     *  LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
     *  / Build quotes for this user that are in their account
     *  / and pending their approval
     *  this.setOrgId(thisUser.getContact().getOrgId());
     *  this.setStatusId(list.getIdFromValue("Pending customer acceptance"));
     *  quoteList.buildList(db);
     *  Iterator quotes = quoteList.iterator();
     *  while (quotes.hasNext()) {
     *  Quote thisQuote = (Quote) quotes.next();
     *  companyCalendar.addEvent(alertDate, CalendarEventList.EVENT_TYPES[10], thisQuote);
     *  }
     *  } catch (SQLException e) {
     *  throw new SQLException("Error Building Quote Calendar Alerts");
     *  }
     */
  }


  /**
   * Description of the Method
   *
   * @param companyCalendar Description of the Parameter
   * @param db              Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildAlertCount(CalendarView companyCalendar, Connection db) throws SQLException {
    /*
     *  try {
     *  if (System.getProperty("DEBUG") != null) {
     *  System.out.println("TaskListScheduledActions-> Building Alert Count ");
     *  }
     *  /get TimeZone
     *  TimeZone timeZone = companyCalendar.getCalendarInfo().getTimeZone();
     *  / Add Task count to calendar
     *  this.setOwner(module.getUserId(context));
     *  HashMap dayEvents = this.queryRecordCount(db, timeZone);
     *  Set s = dayEvents.keySet();
     *  Iterator i = s.iterator();
     *  while (i.hasNext()) {
     *  String thisDay = (String) i.next();
     *  companyCalendar.addEventCount(thisDay, CalendarEventList.EVENT_TYPES[0], dayEvents.get(thisDay));
     *  if (System.getProperty("DEBUG") != null) {
     *  System.out.println("TaskListScheduledActions-> Added Tasks for " + thisDay + "- " + String.valueOf(dayEvents.get(thisDay)));
     *  }
     *  }
     *  } catch (SQLException e) {
     *  throw new SQLException("Error Building Task Calendar Alerts");
     *  }
     */
  }

}

