/*
 *  Copyright(c) 2005 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package com.zeroio.webdav.utils;

import com.zeroio.iteam.base.Assignment;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Call;
import org.aspcfs.modules.mycfs.base.*;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.CalendarView;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents an ICalendar Object(.ics) for a particular Centric CRM User's
 * Calendar of Events. The calendar consists of various components like events,
 * alarms, todo lists and others. The calendar has a sequence of properties
 * followed by one or more components.<p>
 * <p/>
 * TODOs:<br>
 * - Task Events (Pending) <br>
 * <p/>
 * <p/>
 * EVENTS:<br>
 * - Call Events (Pending, Complete)<br>
 * - Project Assignments (Any Status)<br>
 * - Opportunity Events<br>
 * - Account Events<br>
 * - Account Contract Events<br>
 * - Ticket Request Events<br>
 *
 * @author ananth
 * @created December 1, 2004
 */
public class ICalendar {
  private User user = null;
  private StringBuffer buffer = new StringBuffer();
  private CalendarView companyCalendar = new CalendarView();
  private Timestamp created = null;
  private Calendar start = null;
  private Calendar end = null;

  private final static String CRLF = System.getProperty("line.separator");
  private String[] EVENT_TYPES = {
    "Tasks",
    "Activities",
    "Opportunities",
    "Account Alerts",
    "Account Contract Alerts",
    "Contact Activities",
    "Opportunity Activities",
    "Holiday",
    "Assignments",
    "System Alerts",
    "Quotes",
    "Tickets",
    "Ticket Requests",
    "Pending Activities",
    "Project Tickets"};


  /**
   * Sets the created attribute of the ICalendar object
   *
   * @param tmp The new created value
   */
  public void setCreated(Timestamp tmp) {
    this.created = tmp;
  }


  /**
   * Sets the created attribute of the ICalendar object
   *
   * @param tmp The new created value
   */
  public void setCreated(String tmp) {
    this.created = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the start attribute of the ICalendar object
   *
   * @param tmp The new start value
   */
  public void setStart(Calendar tmp) {
    this.start = tmp;
  }


  /**
   * Sets the end attribute of the ICalendar object
   *
   * @param tmp The new end value
   */
  public void setEnd(Calendar tmp) {
    this.end = tmp;
  }


  /**
   * Gets the created attribute of the ICalendar object
   *
   * @return The created value
   */
  public Timestamp getCreated() {
    return created;
  }


  /**
   * Gets the start attribute of the ICalendar object
   *
   * @return The start value
   */
  public Calendar getStart() {
    return start;
  }


  /**
   * Gets the end attribute of the ICalendar object
   *
   * @return The end value
   */
  public Calendar getEnd() {
    return end;
  }


  /**
   * Sets the user attribute of the ICalendar object
   *
   * @param tmp The new user value
   */
  public void setUser(User tmp) {
    this.user = tmp;
  }


  /**
   * Gets the user attribute of the ICalendar object
   *
   * @return The user value
   */
  public User getUser() {
    return user;
  }


  /**
   * Constructor for the ICalendar object
   */
  public ICalendar() {
  }


  /**
   * Constructor for the ICalendar object
   *
   * @param db           Description of the Parameter
   * @param user         Description of the Parameter
   * @param calendarInfo Description of the Parameter
   * @param ownerId      Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ICalendar(Connection db, User user, CalendarBean calendarInfo, int ownerId) throws SQLException {
    this.user = user;
    //current time in UTC
    created = new Timestamp(
        Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis());
    buildEvents(db, calendarInfo, ownerId);
    generateCalendar(db);
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param calendarInfo Description of the Parameter
   * @param ownerId      Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildEvents(Connection db, CalendarBean calendarInfo, int ownerId) throws SQLException {
    calendarInfo.setTimeZone(TimeZone.getTimeZone(user.getTimeZone()));
    companyCalendar.setTimeZone(TimeZone.getTimeZone(user.getTimeZone()));
    companyCalendar.setCalendarInfo(calendarInfo);

    //Determine start and end dates for the calendar.
    //Default start: 2 months in the past
    //Default end: 4 months in the future
    String startDateStr = null;
    String endDateStr = null;
    Calendar cal = Calendar.getInstance(calendarInfo.getTimeZone());
    if (start != null && end != null) {
      if (start.before(end)) {
        startDateStr = DateUtils.getDateString(start);
        endDateStr = DateUtils.getDateString(end);
      } else {
        cal.add(Calendar.MONTH, -2);
        startDateStr = DateUtils.getDateString(cal);
        cal.add(Calendar.MONTH, 6);
        endDateStr = DateUtils.getDateString(cal);
      }
    } else {
      cal.add(Calendar.MONTH, -2);
      startDateStr = DateUtils.getDateString(cal);
      cal.add(Calendar.MONTH, 6);
      endDateStr = DateUtils.getDateString(cal);
    }
    String param1 = "org.aspcfs.utils.web.CalendarView";
    String param2 = "java.sql.Connection";
    try {
      ArrayList alertTypes = calendarInfo.getAlertTypes();
      for (int i = 0; i < alertTypes.size(); i++) {
        AlertType thisAlert = (AlertType) alertTypes.get(i);
        Object thisInstance = Class.forName(thisAlert.getClassName()).newInstance();
        //Try setting the owner id
        try {
          Method method = Class.forName(thisAlert.getClassName()).getMethod(
              "setLoginId", new Class[]{Class.forName("java.lang.String")});
          method.invoke(thisInstance, new Object[]{String.valueOf(ownerId)});
        } catch (Exception e) {
          //This alert does not need a owner id. Tasks need owner id to filter personal tasks
          //Silent catch
        }
        //set userId
        Method method = Class.forName(thisAlert.getClassName()).getMethod(
            "setUserId", new Class[]{Class.forName("java.lang.String")});
        method.invoke(
            thisInstance, new Object[]{String.valueOf(user.getId())});
        //set Start and End Dates
        method = Class.forName(thisAlert.getClassName()).getMethod(
            "setAlertRangeStart", new Class[]{Class.forName(
                "java.sql.Timestamp")});
        java.sql.Timestamp startDate = DatabaseUtils.parseTimestamp(
            DateUtils.getUserToServerDateTimeString(
                calendarInfo.getTimeZone(), DateFormat.SHORT, DateFormat.LONG, startDateStr, Locale.US));
        method.invoke(thisInstance, new Object[]{startDate});

        method = Class.forName(thisAlert.getClassName()).getMethod(
            "setAlertRangeEnd", new Class[]{Class.forName(
                "java.sql.Timestamp")});
        java.sql.Timestamp endDate = DatabaseUtils.parseTimestamp(
            DateUtils.getUserToServerDateTimeString(
                calendarInfo.getTimeZone(), DateFormat.SHORT, DateFormat.LONG, endDateStr, Locale.US));
        method.invoke(thisInstance, new Object[]{endDate});

        //Add Events
        method = Class.forName(thisAlert.getClassName()).getMethod(
            "buildAlerts", new Class[]{Class.forName(param1), Class.forName(
                param2)});
        method.invoke(thisInstance, new Object[]{companyCalendar, db});
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }


  /**
   * Description of the Method
   */
  public void generateCalendar(Connection db) throws SQLException {
    //Begin calendar
    buffer.append("BEGIN:VCALENDAR" + CRLF);
    buffer.append("CALSCALE:GREGORIAN" + CRLF);
    buffer.append(
        "X-WR-CALNAME:" + user.getContact().getNameFirstLast() + CRLF);
    buffer.append("PRODID:Centric CRM" + CRLF);
    buffer.append("METHOD:PUBLISH" + CRLF);
    buffer.append(
        "X-WR-TIMEZONE:" + companyCalendar.getTimeZone().getID() + CRLF);
    buffer.append("VERSION:2.0" + CRLF);

    // Prepare the events
    HashMap events = companyCalendar.getEventList();
    Iterator days = events.keySet().iterator();
    while (days.hasNext()) {
      String day = (String) days.next();
      CalendarEventList thisDay = (CalendarEventList) events.get(day);
      try {
        SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
            DateFormat.SHORT);
        df.applyPattern("MM/dd/yyyy");
        thisDay.setDate(df.parse(day));
      } catch (java.text.ParseException e) {
        e.printStackTrace(System.out);
      }
      for (int i = 0; i < java.lang.reflect.Array.getLength(EVENT_TYPES); i++) {
        String category = EVENT_TYPES[i];
        if (thisDay.containsKey(category)) {
          if (category.equals("Activities")) {
            generateCallEvents(thisDay, category);
          } else if (category.equals("Tasks")) {
            generateTaskEvents(thisDay, category);
          } else if (category.equals("Opportunities")) {
            generateOpportunityEvents(thisDay, category);
          } else if (category.equals("Account Alerts")) {
            generateAccountEvents(thisDay, category);
            generateAccountContractEvents(thisDay, category);
          } else if (category.equals("Assignments")) {
            generateProjectAssignmentEvents(thisDay, category);
          } else if (category.equals("Ticket Requests")) {
            generateTicketRequestEvents(thisDay, category);
          } else if (category.equals("Project Tickets")) {
            generateProjectTicketEvents(thisDay, category);
          } else if (category.equals("Holiday")) {
            //TODO
            //generateHolidayEvents(thisDay, category);
          } else if (category.equals("System Alerts")) {
            //TODO
            //generateSystemEvents(thisDay, category);
          }
        }
      }
    }
    buffer.append("END:VCALENDAR");
  }


  /**
   * Priority values based on the iCalendar specification<br>
   * 0 - Undefined Priority<br>
   * 1 - Highest Priority<br>
   * 9 - Lowest Priority
   *
   * @param priority Description of the Parameter
   * @return The priority value
   */
  public static int getPriority(String priority) {
    if (priority != null) {
      if ("HIGH".equals(priority.toUpperCase())) {
        return 1;
      } else if ("LOW".equals(priority.toUpperCase())) {
        return 9;
      }
    }
    return 5; // default to Normal/Medium
  }


  /**
   * Task priority has a value in the range 1 - 5. <br>
   * 1 is lowest and 5 is highest for a task
   *
   * @param priority Description of the Parameter
   * @return The priority value
   */
  public static int getPriority(int priority) {
    if (priority < 3) {
      return 9;
    }
    if (priority == 3) {
      return 5;
    }
    if (priority > 3) {
      return 1;
    }
    return 3; //default to medium
  }


  /**
   * Description of the Method
   *
   * @param thisDay  Description of the Parameter
   * @param category Description of the Parameter
   */
  private void generateCallEvents(CalendarEventList thisDay, String category) {
    CallEventList callEventList = (CallEventList) thisDay.get(category);
    TimeZone tz = companyCalendar.getTimeZone();
    //Completed Calls
    Iterator i = callEventList.getCompletedCalls().iterator();
    while (i.hasNext()) {
      Call completedCall = (Call) i.next();
      buffer.append(
          completedCall.generateWebcalEvent(tz, created, Call.COMPLETE));
    }
    
    //Pending Calls
    Iterator j = callEventList.getPendingCalls().iterator();
    while (j.hasNext()) {
      Call pendingCall = (Call) j.next();
      buffer.append(
          pendingCall.generateWebcalEvent(
              tz, created, Call.COMPLETE_FOLLOWUP_PENDING));
    }
  }


  /**
   * Description of the Method
   *
   * @param thisDay  Description of the Parameter
   * @param category Description of the Parameter
   */
  private void generateTaskEvents(CalendarEventList thisDay, String category) {
    TaskEventList taskEventList = (TaskEventList) thisDay.get(category);
    TimeZone tz = companyCalendar.getTimeZone();
    //Pending tasks
    Iterator i = taskEventList.getPendingTasks().iterator();
    while (i.hasNext()) {
      Task pendingTask = (Task) i.next();
      buffer.append(pendingTask.generateWebcalEvent(category, tz, created));
    }
    //Completed Tasks
    Iterator j = taskEventList.getCompletedTasks().iterator();
    while (j.hasNext()) {
      Task completedTask = (Task) j.next();
      buffer.append(completedTask.generateWebcalEvent(category, tz, created));
    }
  }


  /**
   * Description of the Method
   *
   * @param thisDay  Description of the Parameter
   * @param category Description of the Parameter
   */
  private void generateOpportunityEvents(CalendarEventList thisDay, String category) {
    OpportunityEventList oppEventList = (OpportunityEventList) thisDay.get(
        category);
    TimeZone tz = companyCalendar.getTimeZone();
    Timestamp alertDate = new Timestamp(thisDay.getDate().getTime());
    //Opportunity Alerts
    Iterator j = oppEventList.getAlertOpps().iterator();
    while (j.hasNext()) {
      OpportunityComponent alertOpp = (OpportunityComponent) j.next();
      buffer.append(
          alertOpp.generateWebcalEvent(
              tz, created, category, user.getCurrency(), user.getLocale(), alertDate));
    }
  }


  /**
   * Description of the Method
   *
   * @param thisDay  Description of the Parameter
   * @param category Description of the Parameter
   */
  private void generateAccountEvents(CalendarEventList thisDay, String category) {
    OrganizationEventList orgEventList = (OrganizationEventList) thisDay.get(
        category);
    TimeZone tz = companyCalendar.getTimeZone();
    Timestamp alertDate = new Timestamp(thisDay.getDate().getTime());
    //Account Alerts
    Iterator j = orgEventList.getAlertOrgs().iterator();
    while (j.hasNext()) {
      Organization thisOrg = (Organization) j.next();
      buffer.append(
          thisOrg.generateWebcalEvent(
              tz, created, alertDate, user,
              category, orgEventList.ACCOUNT_EVENT_ALERT));
    }
  }


  /**
   * Description of the Method
   *
   * @param thisDay  Description of the Parameter
   * @param category Description of the Parameter
   */
  private void generateAccountContractEvents(CalendarEventList thisDay, String category) {
    //Account Contract End alerts
    OrganizationEventList orgEventList = (OrganizationEventList) thisDay.get(
        category);
    TimeZone tz = companyCalendar.getTimeZone();
    Timestamp alertDate = new Timestamp(thisDay.getDate().getTime());
    Iterator k = orgEventList.getContractEndOrgs().iterator();
    while (k.hasNext()) {
      Organization thisOrg = (Organization) k.next();
      buffer.append(
          thisOrg.generateWebcalEvent(
              tz, created, alertDate, user,
              category, orgEventList.ACCOUNT_CONTRACT_ALERT));
    }
  }


  /**
   * Description of the Method
   *
   * @param thisDay  Description of the Parameter
   * @param category Description of the Parameter
   */
  private void generateTicketRequestEvents(CalendarEventList thisDay, String category) {
    TicketEventList ticketEventList = (TicketEventList) thisDay.get(category);
    TimeZone tz = companyCalendar.getTimeZone();
    Iterator i = ticketEventList.getOpenTickets().iterator();
    while (i.hasNext()) {
      Ticket thisTicket = (Ticket) i.next();
      buffer.append(
          thisTicket.generateWebcalEvent(
              tz, created, TicketEventList.OPEN_TICKET));
    }
  }


  /**
   * Description of the Method
   *
   * @param thisDay  Description of the Parameter
   * @param category Description of the Parameter
   */
  private void generateProjectAssignmentEvents(CalendarEventList thisDay, String category) {
    ProjectEventList projectEventList = (ProjectEventList) thisDay.get(
        category);
    TimeZone tz = companyCalendar.getTimeZone();

    Iterator i = projectEventList.getPendingAssignments().iterator();
    while (i.hasNext()) {
      Assignment thisAssignment = (Assignment) i.next();
      buffer.append(thisAssignment.generateWebcalEvent(tz, created));
    }
  }


  /**
   * Description of the Method
   *
   * @param thisDay  Description of the Parameter
   * @param category Description of the Parameter
   */
  private void generateProjectTicketEvents(CalendarEventList thisDay, String category)
      throws SQLException {

    TicketEventList ticketEventList = (TicketEventList) thisDay.get(category);
    TimeZone tz = companyCalendar.getTimeZone();

    Iterator i = ticketEventList.getOpenProjectTickets().iterator();
    while (i.hasNext()) {
      Ticket thisTicket = (Ticket) i.next();
      buffer.append(
          thisTicket.generateWebcalEvent(
              tz, created, TicketEventList.OPEN_PROJECT_TICKET));
    }
  }


  /**
   * Description of the Method
   *
   * @param thisDay  Description of the Parameter
   * @param category Description of the Parameter
   */
  private void generateHolidayEvents(CalendarEventList thisDay, String category) {
  }


  /**
   * Description of the Method
   *
   * @param thisDay  Description of the Parameter
   * @param category Description of the Parameter
   */
  private void generateSystemEvents(CalendarEventList thisDay, String category) {
  }


  /**
   * returns the timestamp in the format: yyyymmdd"T"hhmmss
   *
   * @param ts Description of the Parameter
   * @param tz Description of the Parameter
   * @return The dateTime value
   */
  public static String getDateTime(TimeZone tz, Timestamp ts) {
    Calendar cal = Calendar.getInstance(tz);
    cal.setTimeInMillis(ts.getTime());

    String year = String.valueOf(cal.get(Calendar.YEAR));
    String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
    String date = String.valueOf(cal.get(Calendar.DATE));
    String hours = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
    String minutes = String.valueOf(cal.get(Calendar.MINUTE));
    String seconds = String.valueOf(cal.get(Calendar.SECOND));

    return ((year.length() == 2 ? "00" + year : year) + (month.length() == 1 ? "0" + month : month) +
        (date.length() == 1 ? "0" + date : date) + "T" + (hours.length() == 1 ? "0" + hours : hours) +
        (minutes.length() == 1 ? "0" + minutes : minutes) + (seconds.length() == 1 ? "0" + seconds : seconds));
  }


  /**
   * Gets the dateTimeUTC attribute of the ICalendar object
   *
   * @param ts Description of the Parameter
   * @return The dateTimeUTC value
   */
  public static String getDateTimeUTC(Timestamp ts) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(ts.getTime());

    String year = String.valueOf(cal.get(Calendar.YEAR));
    String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
    String date = String.valueOf(cal.get(Calendar.DATE));
    String hours = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
    String minutes = String.valueOf(cal.get(Calendar.MINUTE));
    String seconds = String.valueOf(cal.get(Calendar.SECOND));
    return ((year.length() == 2 ? "00" + year : year) + (month.length() == 1 ? "0" + month : month) +
        (date.length() == 1 ? "0" + date : date) + "T" + (hours.length() == 1 ? "0" + hours : hours) +
        (minutes.length() == 1 ? "0" + minutes : minutes) + (seconds.length() == 1 ? "0" + seconds : seconds) + "Z");
  }


  /**
   * Description of the Method
   *
   * @param input Description of the Parameter
   * @return Description of the Return Value
   */
  public static String parseNewLine(String input) {
    StringBuffer clean = new StringBuffer();
    for (int i = 0; i < input.length(); ++i) {
      if (input.charAt(i) == '\n') {
        clean.append("\\n");
      } else {
        clean.append(input.charAt(i));
      }
    }
    return clean.toString().trim();
  }

  /**
   * The iCalendar object is organized into individual lines of text, called content lines. Content lines are delimited
   * by a line break, which is a CRLF sequence (US-ASCII decimal 13, followed by US-ASCII decimal 10). Lines of
   * text SHOULD NOT be longer than 75 octets, excluding the line break. Long content lines SHOULD be split into
   * a multiple line representations using a line "folding" technique. That is, a long line can be split between any
   * two characters by inserting a CRLF immediately followed by a single linear white space character (i.e., SPACE,
   * US-ASCII decimal 32 or HTAB, US-ASCII decimal 9). Any sequence of CRLF followed immediately by a single
   * linear white space character is ignored (i.e., removed) when processing the content type.
   *
   * @param input Description of the Parameter
   * @return Description of the Return Value
   */
  public static String foldLine(String input) {
    if (input != null) {
      int length = input.length();
      if (length <= 70) {
        return input;
      } else {
        //Fold the input string
        String result = "";
        int fold = length / 70; //number of times to fold
        int begin = 0, end = 70;
        for (int count = 0; count < fold; count++) {
          result += input.substring(begin, end) + CRLF + " "; //Folding
          begin = end;
          end = end + 70; //gets the next 70 chars
        }
        if (begin <= length) {
          result += input.substring(begin, length);
        }
        return result;
      }
    }
    return "";
  }

  /**
   * Gets the bytes attribute of the ICalendar object
   *
   * @return The bytes value
   */
  public byte[] getBytes() {
    return buffer.toString().getBytes();
  }


  /**
   * Gets the currencyFormat attribute of the ICalendar object
   *
   * @param value Description of the Parameter
   * @return The currencyFormat value
   */
  public static String getCurrencyFormat(double value, String currency, Locale locale) {
    NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
    formatter.setMaximumFractionDigits(4);
    if (currency != null) {
      Currency cur = Currency.getInstance(currency);
      formatter.setCurrency(cur);
    }
    return (formatter.format(value));
  }
}

