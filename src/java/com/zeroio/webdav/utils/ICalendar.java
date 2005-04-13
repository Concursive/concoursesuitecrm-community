package com.zeroio.webdav.utils;

import java.sql.*;
import java.util.*;
import java.lang.reflect.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;

import org.aspcfs.utils.*;
import org.aspcfs.utils.web.CalendarView;
import org.aspcfs.modules.base.PhoneNumber;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.mycfs.base.*;
import org.aspcfs.modules.tasks.base.*;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.troubletickets.base.*;

import com.zeroio.iteam.base.Assignment;

/**
 *  Represents an ICalendar Object(.ics) for a particular Centric CRM User's
 *  Calendar of Events. The calendar consists of various components like events,
 *  alarms, todo lists and others. The calendar has a sequence of properties
 *  followed by one or more components.
 *
 *@author     ananth
 *@created    December 1, 2004
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
      "Pending Activities"};


  /**
   *  Sets the created attribute of the ICalendar object
   *
   *@param  tmp  The new created value
   */
  public void setCreated(Timestamp tmp) {
    this.created = tmp;
  }


  /**
   *  Sets the created attribute of the ICalendar object
   *
   *@param  tmp  The new created value
   */
  public void setCreated(String tmp) {
    this.created = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the start attribute of the ICalendar object
   *
   *@param  tmp  The new start value
   */
  public void setStart(Calendar tmp) {
    this.start = tmp;
  }


  /**
   *  Sets the end attribute of the ICalendar object
   *
   *@param  tmp  The new end value
   */
  public void setEnd(Calendar tmp) {
    this.end = tmp;
  }


  /**
   *  Gets the created attribute of the ICalendar object
   *
   *@return    The created value
   */
  public Timestamp getCreated() {
    return created;
  }


  /**
   *  Gets the start attribute of the ICalendar object
   *
   *@return    The start value
   */
  public Calendar getStart() {
    return start;
  }


  /**
   *  Gets the end attribute of the ICalendar object
   *
   *@return    The end value
   */
  public Calendar getEnd() {
    return end;
  }


  /**
   *  Sets the user attribute of the ICalendar object
   *
   *@param  tmp  The new user value
   */
  public void setUser(User tmp) {
    this.user = tmp;
  }


  /**
   *  Gets the user attribute of the ICalendar object
   *
   *@return    The user value
   */
  public User getUser() {
    return user;
  }


  /**
   *  Constructor for the ICalendar object
   */
  public ICalendar() { }


  /**
   *  Constructor for the ICalendar object
   *
   *@param  db                Description of the Parameter
   *@param  user              Description of the Parameter
   *@param  calendarInfo      Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ICalendar(Connection db, User user, CalendarBean calendarInfo) throws SQLException {
    this.user = user;
    //current time in UTC
    created = new Timestamp(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis());
    buildEvents(db, calendarInfo);
    generateCalendar();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  calendarInfo      Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildEvents(Connection db, CalendarBean calendarInfo) throws SQLException {
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
        //set userId
        Method method = Class.forName(thisAlert.getClassName()).getMethod("setUserId", new Class[]{Class.forName("java.lang.String")});
        method.invoke(thisInstance, new Object[]{String.valueOf(user.getId())});
        //set Start and End Dates
        method = Class.forName(thisAlert.getClassName()).getMethod("setAlertRangeStart", new Class[]{Class.forName("java.sql.Timestamp")});
        java.sql.Timestamp startDate = DatabaseUtils.parseTimestamp(DateUtils.getUserToServerDateTimeString(calendarInfo.getTimeZone(), DateFormat.SHORT, DateFormat.LONG, startDateStr, Locale.US));
        method.invoke(thisInstance, new Object[]{startDate});

        method = Class.forName(thisAlert.getClassName()).getMethod("setAlertRangeEnd", new Class[]{Class.forName("java.sql.Timestamp")});
        java.sql.Timestamp endDate = DatabaseUtils.parseTimestamp(DateUtils.getUserToServerDateTimeString(calendarInfo.getTimeZone(), DateFormat.SHORT, DateFormat.LONG, endDateStr, Locale.US));
        method.invoke(thisInstance, new Object[]{endDate});

        //Add Events
        method = Class.forName(thisAlert.getClassName()).getMethod("buildAlerts", new Class[]{Class.forName(param1), Class.forName(param2)});
        method.invoke(thisInstance, new Object[]{companyCalendar, db});
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }


  /**
   *  Description of the Method
   *
   */
  public void generateCalendar() {
    //Begin calendar
    buffer.append("BEGIN:VCALENDAR" + CRLF);
    buffer.append("CALSCALE:GREGORIAN" + CRLF);
    buffer.append("X-WR-CALNAME:" + user.getContact().getNameFirstLast() + CRLF);
    buffer.append("PRODID:Centric CRM" + CRLF);
    buffer.append("METHOD:PUBLISH" + CRLF);
    buffer.append("X-WR-TIMEZONE:" + companyCalendar.getTimeZone().getID() + CRLF);
    buffer.append("VERSION:2.0" + CRLF);

    // Prepare the events
    HashMap events = companyCalendar.getEventList();
    Iterator days = events.keySet().iterator();
    while (days.hasNext()) {
      String day = (String) days.next();
      CalendarEventList thisDay = (CalendarEventList) events.get(day);
      try {
        SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.SHORT);
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
          } else if (category.equals("Account Contract Alerts")) {
            generateAccountContractEvents(thisDay, category);
          } else if (category.equals("Assignments")) {
            generateProjectAssignmentEvents(thisDay, category);
          } else if (category.equals("Ticket Requests")) {
            generateTicketRequestEvents(thisDay, category);
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
   *  Description of the Method
   *
   *@param  thisDay           Description of the Parameter
   *@param  category          Description of the Parameter
   */
  private void generateCallEvents(CalendarEventList thisDay, String category) {
    CallEventList callEventList = (CallEventList) thisDay.get(category);
    TimeZone tz = companyCalendar.getTimeZone();
    //Completed Calls
    Iterator i = callEventList.getCompletedCalls().iterator();
    while (i.hasNext()) {
      Call completedCall = (Call) i.next();
      String summary = completedCall.getAlertText();
      Timestamp created = completedCall.getEntered();
      String contact = completedCall.getContactName();
      //write the event
      buffer.append("BEGIN:VTODO" + CRLF);
      buffer.append("UID:www.centriccrm.com-myhomepage-calls-" + completedCall.getId() + CRLF);
      buffer.append("DTSTAMP:" + getDateTimeUTC(created) + CRLF);
      buffer.append("CREATED;TZID=" + tz.getID() + ":" + getDateTime(tz, created) + CRLF);
      buffer.append("SUMMARY:" + summary + CRLF);
      if (contact != null) {
        buffer.append("DESCRIPTION:" + contact + CRLF);
      }
      buffer.append("END:VTODO" + CRLF);
    }
    //Pending Calls
    Iterator j = callEventList.getPendingCalls().iterator();
    while (j.hasNext()) {
      Call pendingCall = (Call) j.next();
      String summary = pendingCall.getAlertText();
      Timestamp created = pendingCall.getEntered();
      Timestamp dueDate = pendingCall.getAlertDate();
      String description = "Priority: " + pendingCall.getPriorityString();
      description += "\\n\\n" + pendingCall.getContactName();
      if (pendingCall.getOrgName() != null && !"".equals(pendingCall.getOrgName().trim())) {
        description += "\\n" + pendingCall.getOrgName();
      }

      Iterator k = pendingCall.getContact().getPhoneNumberList().iterator();
      while (k.hasNext()) {
        PhoneNumber thisNumber = (PhoneNumber) k.next();
        description += "\\n" + String.valueOf(thisNumber.getTypeName().charAt(0)) + ":" + thisNumber.getNumber();
      }

      //write the event
      buffer.append("BEGIN:VTODO" + CRLF);
      buffer.append("UID:www.centriccrm.com-myhomepage-calls-" + pendingCall.getId() + CRLF);
      buffer.append("DTSTAMP:" + getDateTimeUTC(created) + CRLF);
      buffer.append("CREATED;TZID=" + tz.getID() + ":" + getDateTime(tz, created) + CRLF);
      buffer.append("SUMMARY:" + summary + CRLF);
      buffer.append("DUE;TZID=" + tz.getID() + ":" + getDateTime(tz, dueDate) + CRLF);
      buffer.append("DESCRIPTION:" + description + CRLF);
      buffer.append("END:VTODO" + CRLF);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  thisDay   Description of the Parameter
   *@param  category  Description of the Parameter
   */
  private void generateTaskEvents(CalendarEventList thisDay, String category) {
    TaskEventList taskEventList = (TaskEventList) thisDay.get(category);
    TimeZone tz = companyCalendar.getTimeZone();
    Iterator j = taskEventList.getPendingTasks().iterator();
    while (j.hasNext()) {
      Task pendingTask = (Task) j.next();
      String summary = pendingTask.getDescription();
      Timestamp created = pendingTask.getEntered();
      Timestamp dueDate = pendingTask.getDueDate();
      String description = "Age: " + pendingTask.getAgeString();
      //write the event
      buffer.append("BEGIN:VTODO" + CRLF);
      buffer.append("UID:www.centriccrm.com-myhomepage-tasks-" + pendingTask.getId() + CRLF);
      buffer.append("DTSTAMP:" + getDateTimeUTC(created) + CRLF);
      buffer.append("CREATED;TZID=" + tz.getID() + ":" + getDateTime(tz, created) + CRLF);
      buffer.append("SUMMARY:" + summary + CRLF);
      buffer.append("PRIORITY;VALUE=INTEGER:" + pendingTask.getPriority() + CRLF);
      buffer.append("DESCRIPTION:" + description + CRLF);
      buffer.append("CATEGORIES:" + category + CRLF);
      buffer.append("DUE;TZID=" + tz.getID() + ":" + getDateTime(tz, dueDate) + CRLF);
      //buffer.append("PERCENT:")
      if (pendingTask.getContactName() != null) {
        buffer.append("CONTACT:" + pendingTask.getContactName() + CRLF);
      }
      if (!pendingTask.getComplete()) {
        buffer.append("STATUS:NEEDS-ACTION" + CRLF);
      }
      buffer.append("END:VTODO" + CRLF);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  thisDay           Description of the Parameter
   *@param  category          Description of the Parameter
   */
  private void generateOpportunityEvents(CalendarEventList thisDay, String category) {
    OpportunityEventList oppEventList = (OpportunityEventList) thisDay.get(category);
    TimeZone tz = companyCalendar.getTimeZone();
    //Account Alerts
    Iterator j = oppEventList.getAlertOpps().iterator();
    while (j.hasNext()) {
      OpportunityComponent alertOpp = (OpportunityComponent) j.next();
      String alertText = alertOpp.getAlertText();
      Timestamp alertDate = new Timestamp(thisDay.getDate().getTime());
      String description = "     Component: " + alertOpp.getDescription().trim() + "\\n" +
          "Guess Amount: " + getCurrencyFormat(alertOpp.getGuess()) + "\\n" +
          "      Close Date: " + String.valueOf(alertOpp.getCloseDateString());

      //write the event
      buffer.append("BEGIN:VEVENT" + CRLF);
      buffer.append("UID:www.centriccrm.com-opportunity-alerts-" + alertOpp.getId() + CRLF);
      buffer.append("DTSTAMP:" + getDateTimeUTC(created) + CRLF);
      buffer.append("DTSTART;TZID=" + tz.getID() + ":" + getDateTime(tz, alertDate) + CRLF);
      buffer.append("SUMMARY:" + alertText + CRLF);
      buffer.append("DESCRIPTION:" + description + CRLF);
      buffer.append("CATEGORIES:" + category + CRLF);
      buffer.append("END:VEVENT" + CRLF);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  thisDay           Description of the Parameter
   *@param  category          Description of the Parameter
   */
  private void generateAccountEvents(CalendarEventList thisDay, String category) {
    OrganizationEventList orgEventList = (OrganizationEventList) thisDay.get(category);
    TimeZone tz = companyCalendar.getTimeZone();
    //Account Alerts
    Iterator j = orgEventList.getAlertOrgs().iterator();
    while (j.hasNext()) {
      Organization thisOrg = (Organization) j.next();
      String alertText = thisOrg.getAlertText();
      Timestamp alertDate = new Timestamp(thisDay.getDate().getTime());
      String description = "Account: " + thisOrg.getName() + "\\n" +
          "    Owner: " + user.getContact().getNameFirstLast();
      //write the event
      buffer.append("BEGIN:VEVENT" + CRLF);
      buffer.append("UID:www.centriccrm.com-accounts-alerts-" + thisOrg.getOrgId() + CRLF);
      buffer.append("DTSTAMP:" + getDateTimeUTC(created) + CRLF);
      buffer.append("DTSTART;TZID=" + tz.getID() + ":" + getDateTime(tz, alertDate) + CRLF);
      buffer.append("SUMMARY:" + alertText + CRLF);
      buffer.append("DESCRIPTION:" + description + CRLF);
      buffer.append("CATEGORIES:" + category + CRLF);
      buffer.append("END:VEVENT" + CRLF);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  thisDay           Description of the Parameter
   *@param  category          Description of the Parameter
   */
  private void generateAccountContractEvents(CalendarEventList thisDay, String category) {
    //Account Contract End alerts
    OrganizationEventList orgEventList = (OrganizationEventList) thisDay.get(category);
    TimeZone tz = companyCalendar.getTimeZone();
    Iterator k = orgEventList.getContractEndOrgs().iterator();
    while (k.hasNext()) {
      Organization thisOrg = (Organization) k.next();
      Timestamp alertDate = new Timestamp(thisDay.getDate().getTime());
      String description = "Account: " + thisOrg.getName() + "\\n" +
          "   Owner:  " + user.getContact().getNameFirstLast();
      //write the event
      buffer.append("BEGIN:VEVENT" + CRLF);
      buffer.append("UID:www.centriccrm.com-accounts-contract-alerts-" + thisOrg.getOrgId() + CRLF);
      buffer.append("DTSTAMP:" + getDateTimeUTC(created) + CRLF);
      buffer.append("DTSTART;TZID=" + tz.getID() + ":" + getDateTime(tz, alertDate) + CRLF);
      buffer.append("SUMMARY:Account Contract End Alert" + CRLF);
      buffer.append("DESCRIPTION: " + description + CRLF);
      buffer.append("CATEGORIES:" + category + CRLF);
      buffer.append("END:VEVENT" + CRLF);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  thisDay           Description of the Parameter
   *@param  category          Description of the Parameter
   */
  private void generateTicketRequestEvents(CalendarEventList thisDay, String category) {
    TicketEventList ticketEventList = (TicketEventList) thisDay.get(category);
    TimeZone tz = companyCalendar.getTimeZone();
    Iterator i = ticketEventList.getOpenTickets().iterator();
    while (i.hasNext()) {
      Ticket thisTicket = (Ticket) i.next();
      //write the event
      buffer.append("BEGIN:VEVENT" + CRLF);
      buffer.append("UID:www.centriccrm.com-ticket-alerts-" + thisTicket.getId() + CRLF);
      buffer.append("DTSTAMP:" + getDateTimeUTC(created) + CRLF);
      buffer.append("DTSTART;TZID=" + tz.getID() + ":" + getDateTime(tz, thisTicket.getEstimatedResolutionDate()) + CRLF);
      buffer.append("SUMMARY:" + thisTicket.getProblem() + CRLF);
      buffer.append("DESCRIPTION:Ticket #: " + thisTicket.getPaddedId() + "\\nCompany: " + thisTicket.getCompanyName() + CRLF);
      buffer.append("END:VEVENT" + CRLF);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  thisDay           Description of the Parameter
   *@param  category          Description of the Parameter
   */
  private void generateProjectAssignmentEvents(CalendarEventList thisDay, String category) {
    ProjectEventList projectEventList = (ProjectEventList) thisDay.get(category);
    TimeZone tz = companyCalendar.getTimeZone();

    Iterator i = projectEventList.getPendingAssignments().iterator();
    while (i.hasNext()) {
      Assignment thisAssignment = (Assignment) i.next();
      String summary = thisAssignment.getRole();
      Timestamp created = thisAssignment.getEntered();
      Timestamp dueDate = thisAssignment.getDueDate();
      String description = " Project: " + thisAssignment.getProject().getTitle() + "\\n" +
          "  Status: " + thisAssignment.getStatus() + "(" + thisAssignment.getPercentComplete() + "%)" + "\\n" +
          "Priority: " + thisAssignment.getPriorityId();

      //write the event
      buffer.append("BEGIN:VTODO" + CRLF);
      buffer.append("UID:www.centriccrm.com-projects-assignment-events" + thisAssignment.getId() + CRLF);
      buffer.append("DTSTAMP:" + getDateTimeUTC(created) + CRLF);
      buffer.append("CREATED;TZID=" + tz.getID() + ":" + getDateTime(tz, created) + CRLF);
      buffer.append("SUMMARY:" + summary + CRLF);
      buffer.append("DESCRIPTION:" + description + CRLF);
      buffer.append("DUE;TZID=" + tz.getID() + ":" + getDateTime(tz, dueDate) + CRLF);
      buffer.append("END:VTODO" + CRLF);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  thisDay           Description of the Parameter
   *@param  category          Description of the Parameter
   */
  private void generateHolidayEvents(CalendarEventList thisDay, String category) { }


  /**
   *  Description of the Method
   *
   *@param  thisDay           Description of the Parameter
   *@param  category          Description of the Parameter
   */
  private void generateSystemEvents(CalendarEventList thisDay, String category) { }


  /**
   *  returns the timestamp in the format: yyyymmdd"T"hhmmss
   *
   *@param  ts  Description of the Parameter
   *@param  tz  Description of the Parameter
   *@return     The dateTime value
   */
  private String getDateTime(TimeZone tz, Timestamp ts) {
    Calendar cal = Calendar.getInstance(tz);
    cal.setTimeInMillis(ts.getTime());
    String year = String.valueOf(cal.get(Calendar.YEAR));
    String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
    String date = String.valueOf(cal.get(Calendar.DATE));
    String hours = String.valueOf(cal.get(Calendar.HOUR));
    String minutes = String.valueOf(cal.get(Calendar.MINUTE));
    String seconds = String.valueOf(cal.get(Calendar.SECOND));
    return ((year.length() == 2 ? "00" + year : year) + (month.length() == 1 ? "0" + month : month) +
        (date.length() == 1 ? "0" + date : date) + "T" + (hours.length() == 1 ? "0" + hours : hours) +
        (minutes.length() == 1 ? "0" + minutes : minutes) + (seconds.length() == 1 ? "0" + seconds : seconds));
  }


  /**
   *  Gets the dateTimeUTC attribute of the ICalendar object
   *
   *@param  ts  Description of the Parameter
   *@return     The dateTimeUTC value
   */
  private String getDateTimeUTC(Timestamp ts) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(ts.getTime());
    String year = String.valueOf(cal.get(Calendar.YEAR));
    String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
    String date = String.valueOf(cal.get(Calendar.DATE));
    String hours = String.valueOf(cal.get(Calendar.HOUR));
    String minutes = String.valueOf(cal.get(Calendar.MINUTE));
    String seconds = String.valueOf(cal.get(Calendar.SECOND));
    return ((year.length() == 2 ? "00" + year : year) + (month.length() == 1 ? "0" + month : month) +
        (date.length() == 1 ? "0" + date : date) + "T" + (hours.length() == 1 ? "0" + hours : hours) +
        (minutes.length() == 1 ? "0" + minutes : minutes) + (seconds.length() == 1 ? "0" + seconds : seconds) + "Z");
  }


  /**
   *  Gets the bytes attribute of the ICalendar object
   *
   *@return    The bytes value
   */
  public byte[] getBytes() {
    return buffer.toString().getBytes();
  }


  /**
   *  Gets the currencyFormat attribute of the ICalendar object
   *
   *@param  value  Description of the Parameter
   *@return        The currencyFormat value
   */
  private String getCurrencyFormat(double value) {
    NumberFormat formatter = NumberFormat.getCurrencyInstance(user.getLocale());
    formatter.setMaximumFractionDigits(4);
    if (user.getCurrency() != null) {
      Currency currency = Currency.getInstance(user.getCurrency());
      formatter.setCurrency(currency);
    }
    return (formatter.format(value));
  }
}

