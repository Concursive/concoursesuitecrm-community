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
package org.aspcfs.apps.workFlowManager;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a date/time pattern for executing a specified business process
 *
 * @author matt rajkowski
 * @version $Id: ScheduledEvent.java,v 1.1 2003/06/24 15:17:30 mrajkowski Exp
 *          $
 * @created June 23, 2003
 */
public class ScheduledEvent implements Serializable {

  private int id = -1;
  private String second = null;
  private String minute = null;
  private String hour = null;
  private String dayOfMonth = null;
  private String month = null;
  private String dayOfWeek = null;
  private String year = null;
  private String task = null;
  private String extraInfo = null;
  private boolean enabled = true;
  java.sql.Timestamp entered = null;
  private int processId = -1;


  /**
   * Constructor for the ScheduledEvent object
   */
  public ScheduledEvent() {
  }


  /**
   * Constructor for the ScheduledEvent object
   *
   * @param actionElement Description of the Parameter
   */
  public ScheduledEvent(Element actionElement) {
    this.setSecond((String) actionElement.getAttribute("second"));
    this.setMinute((String) actionElement.getAttribute("minute"));
    this.setHour((String) actionElement.getAttribute("hour"));
    this.setDayOfMonth((String) actionElement.getAttribute("dayOfMonth"));
    this.setMonth((String) actionElement.getAttribute("month"));
    this.setDayOfWeek((String) actionElement.getAttribute("dayOfWeek"));
    this.setYear((String) actionElement.getAttribute("year"));
    this.setTask((String) actionElement.getAttribute("process"));
    this.setEnabled((String) actionElement.getAttribute("enabled"));
  }


  /**
   * Constructor for the ScheduledEvent object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ScheduledEvent(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the ScheduledEvent object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ScheduledEvent object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the second attribute of the ScheduledEvent object
   *
   * @param tmp The new second value
   */
  public void setSecond(String tmp) {
    this.second = tmp;
  }


  /**
   * Sets the minute attribute of the ScheduledEvent object
   *
   * @param tmp The new minute value
   */
  public void setMinute(String tmp) {
    this.minute = tmp;
  }


  /**
   * Sets the hour attribute of the ScheduledEvent object
   *
   * @param tmp The new hour value
   */
  public void setHour(String tmp) {
    this.hour = tmp;
  }


  /**
   * Sets the dayOfMonth attribute of the ScheduledEvent object
   *
   * @param tmp The new dayOfMonth value
   */
  public void setDayOfMonth(String tmp) {
    this.dayOfMonth = tmp;
  }


  /**
   * Sets the month attribute of the ScheduledEvent object
   *
   * @param tmp The new month value
   */
  public void setMonth(String tmp) {
    this.month = tmp;
  }


  /**
   * Sets the dayOfWeek attribute of the ScheduledEvent object
   *
   * @param tmp The new dayOfWeek value
   */
  public void setDayOfWeek(String tmp) {
    this.dayOfWeek = tmp;
  }


  /**
   * Sets the year attribute of the ScheduledEvent object
   *
   * @param tmp The new year value
   */
  public void setYear(String tmp) {
    this.year = tmp;
  }


  /**
   * Sets the task attribute of the ScheduledEvent object
   *
   * @param tmp The new task value
   */
  public void setTask(String tmp) {
    this.task = tmp;
  }


  /**
   * Sets the extraInfo attribute of the ScheduledEvent object
   *
   * @param tmp The new extraInfo value
   */
  public void setExtraInfo(String tmp) {
    this.extraInfo = tmp;
  }


  /**
   * Sets the enabled attribute of the ScheduledEvent object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ScheduledEvent object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the processId attribute of the ScheduledEvent object
   *
   * @param tmp The new processId value
   */
  public void setProcessId(int tmp) {
    this.processId = tmp;
  }


  /**
   * Sets the processId attribute of the ScheduledEvent object
   *
   * @param tmp The new processId value
   */
  public void setProcessId(String tmp) {
    this.processId = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the ScheduledEvent object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the second attribute of the ScheduledEvent object
   *
   * @return The second value
   */
  public String getSecond() {
    return second;
  }


  /**
   * Gets the minute attribute of the ScheduledEvent object
   *
   * @return The minute value
   */
  public String getMinute() {
    return minute;
  }


  /**
   * Gets the hour attribute of the ScheduledEvent object
   *
   * @return The hour value
   */
  public String getHour() {
    return hour;
  }


  /**
   * Gets the dayOfMonth attribute of the ScheduledEvent object
   *
   * @return The dayOfMonth value
   */
  public String getDayOfMonth() {
    return dayOfMonth;
  }


  /**
   * Gets the month attribute of the ScheduledEvent object
   *
   * @return The month value
   */
  public String getMonth() {
    return month;
  }


  /**
   * Gets the dayOfWeek attribute of the ScheduledEvent object
   *
   * @return The dayOfWeek value
   */
  public String getDayOfWeek() {
    return dayOfWeek;
  }


  /**
   * Gets the year attribute of the ScheduledEvent object
   *
   * @return The year value
   */
  public String getYear() {
    return year;
  }


  /**
   * Gets the task attribute of the ScheduledEvent object
   *
   * @return The task value
   */
  public String getTask() {
    return task;
  }


  /**
   * Gets the extraInfo attribute of the ScheduledEvent object
   *
   * @return The extraInfo value
   */
  public String getExtraInfo() {
    return extraInfo;
  }


  /**
   * Gets the enabled attribute of the ScheduledEvent object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the processId attribute of the ScheduledEvent object
   *
   * @return The processId value
   */
  public int getProcessId() {
    return processId;
  }


  /**
   * Gets the entered attribute of the ScheduledEvent object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Sets the entered attribute of the ScheduledEvent object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the ScheduledEvent object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Populates a record from a database result set
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("event_id");
    second = rs.getString("second");
    minute = rs.getString("minute");
    hour = rs.getString("hour");
    dayOfMonth = rs.getString("dayofmonth");
    month = rs.getString("month");
    dayOfWeek = rs.getString("dayofweek");
    year = rs.getString("year");
    task = rs.getString("task");
    extraInfo = rs.getString("extrainfo");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    processId = rs.getInt("process_id");
  }


  /**
   * Generates a text explainable version of the scheduled event
   *
   * @return Description of the Return Value
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    //Minute repeats
    if (!minute.equals("*")) {
      if (minute.indexOf("*/") > -1) {
        String minuteRepeat = minute.substring(minute.indexOf("*/") + 2);
        appendNext(
            sb, "Every " + ("1".equals(minuteRepeat) ? "" : minuteRepeat + " ") + "minute" + ("1".equals(
                minuteRepeat) ? "" : "s"));
      }
    }
    //Hour repeats
    if (!hour.equals("*")) {
      if (hour.indexOf("*/") > -1) {
        String hourRepeat = hour.substring(hour.indexOf("*/") + 2);
        appendNext(
            sb, "Every " + ("1".equals(hourRepeat) ? "" : hourRepeat + " ") + "hour" + ("1".equals(
                hourRepeat) ? "" : "s"));
      }
      if (hour.indexOf("-") > -1) {
        String leftRange = hour.substring(0, hour.indexOf("-"));
        String rightRange = hour.substring(hour.indexOf("-") + 1);
        appendNext(
            sb, to12Hr(leftRange, null) + " - " + to12Hr(rightRange, null));
      }
    }
    //Actual Time
    int actualMinute = StringUtils.parseInt(minute, -1);
    int actualHour = StringUtils.parseInt(hour, -1);
    if (actualHour > -1 && actualMinute > -1) {
      appendNext(sb, "at " + to12Hr(hour, minute));
    } else {
      //Hour only specified
      if (actualHour > -1) {
        appendNext(sb, "During the hour of " + to12Hr(hour, null));
      }
    }
    //Actual Date
    int actualmonth = StringUtils.parseInt(month, -1);
    int actualDayOfMonth = StringUtils.parseInt(dayOfMonth, -1);
    int actualYear = StringUtils.parseInt(year, -1);
    if (actualmonth > -1 && actualDayOfMonth > -1 && actualYear > -1) {
      appendNext(
          sb, "on " + actualmonth + "/" + actualDayOfMonth + "/" + actualYear);
    }

    return sb.toString();
  }


  /**
   * Appends text to the output with a separator
   *
   * @param sb   Description of the Parameter
   * @param text Description of the Parameter
   */
  private static void appendNext(StringBuffer sb, String text) {
    if (sb.length() > 0) {
      sb.append("; ");
    }
    sb.append(text);
  }


  /**
   * Converts an hour and a minute to 12hr format using AM/PM
   *
   * @param hourValue   Description of the Parameter
   * @param minuteValue Description of the Parameter
   * @return Description of the Return Value
   */
  private static String to12Hr(String hourValue, String minuteValue) {
    StringBuffer sb = new StringBuffer();
    //Add the hour
    if (hourValue != null) {
      int hour = StringUtils.parseInt(hourValue, -1);
      if (hour == 0) {
        sb.append("12");
      } else if (hour >= 1 && hour <= 12) {
        sb.append(hour);
      } else if (hour >= 13 && hour <= 23) {
        sb.append(hour - 12);
      }
    }
    //Add the minutes
    if (minuteValue != null) {
      sb.append(":");
      int minute = StringUtils.parseInt(minuteValue, 0);
      if (minute >= 0 && minute <= 9) {
        sb.append("0" + minute);
      } else if (minute >= 10 && minute <= 59) {
        sb.append(minute);
      }
    }
    //Add AM/PM
    if (hourValue != null) {
      sb.append(" ");
      int hour = StringUtils.parseInt(hourValue, -1);
      if (hour >= 0 && hour <= 11) {
        sb.append("AM");
      } else if (hour >= 12 && hour <= 23) {
        sb.append("PM");
      }
    }
    return sb.toString();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    if (this.getId() != -1) {
      return (this.update(db) == 1);
    }
    id = DatabaseUtils.getNextSeq(db, "business_process_e_event_id_seq");
    StringBuffer sql = new StringBuffer(
        "INSERT INTO business_process_events( ");
    if (id > -1) {
      sql.append("event_id, ");
    }
    if (this.getSecond() != null && !"".equals(this.getSecond())) {
      sql.append("\"second\", ");
    }
    if (this.getMinute() != null && !"".equals(this.getMinute())) {
      sql.append("\"minute\", ");
    }
    if (this.getHour() != null && !"".equals(this.getHour())) {
      sql.append("\"hour\", ");
    }
    if (this.getDayOfMonth() != null && !"".equals(getDayOfMonth())) {
      sql.append("dayofmonth, ");
    }
    if (this.getMonth() != null && !"".equals(this.getMonth())) {
      sql.append("\"month\", ");
    }
    if (this.getDayOfWeek() != null && !"".equals(this.getDayOfWeek())) {
      sql.append("\"dayofweek\", ");
    }
    if (this.getYear() != null && !"".equals(this.getYear())) {
      sql.append("\"year\", ");
    }
    if (this.getTask() != null && !"".equals(this.getTask())) {
      sql.append("task, ");
    }
    if (this.getExtraInfo() != null && !"".equals(this.getExtraInfo())) {
      sql.append("extrainfo, ");
    }
    if (this.getEnabled()) {
      sql.append("enabled, ");
    }
    if (this.getEntered() != null) {
      sql.append("entered, ");
    }
    sql.append("process_id) VALUES( ");
    if (id > -1) {
      sql.append("?, ");
    }
    if (this.getSecond() != null && !"".equals(this.getSecond())) {
      sql.append("?, ");
    }
    if (this.getMinute() != null && !"".equals(this.getMinute())) {
      sql.append("?, ");
    }
    if (this.getHour() != null && !"".equals(this.getHour())) {
      sql.append("?, ");
    }
    if (this.getDayOfMonth() != null && !"".equals(getDayOfMonth())) {
      sql.append("?, ");
    }
    if (this.getMonth() != null && !"".equals(this.getMonth())) {
      sql.append("?, ");
    }
    if (this.getDayOfWeek() != null && !"".equals(this.getDayOfWeek())) {
      sql.append("?, ");
    }
    if (this.getYear() != null && !"".equals(this.getYear())) {
      sql.append("?, ");
    }
    if (this.getTask() != null && !"".equals(this.getTask())) {
      sql.append("?, ");
    }
    if (this.getExtraInfo() != null && !"".equals(this.getExtraInfo())) {
      sql.append("?, ");
    }
    if (this.getEnabled()) {
      sql.append("?, ");
    }
    if (this.getEntered() != null) {
      sql.append("?, ");
    }
    sql.append("?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (this.getSecond() != null && !"".equals(this.getSecond())) {
      pst.setString(++i, getSecond());
    }
    if (this.getMinute() != null && !"".equals(this.getMinute())) {
      pst.setString(++i, this.getMinute());
    }
    if (this.getHour() != null && !"".equals(this.getHour())) {
      pst.setString(++i, this.getHour());
    }
    if (this.getDayOfMonth() != null && !"".equals(getDayOfMonth())) {
      pst.setString(++i, this.getDayOfMonth());
    }
    if (this.getMonth() != null && !"".equals(this.getMonth())) {
      pst.setString(++i, this.getMonth());
    }
    if (this.getDayOfWeek() != null && !"".equals(this.getDayOfWeek())) {
      pst.setString(++i, this.getDayOfWeek());
    }
    if (this.getYear() != null && !"".equals(this.getYear())) {
      pst.setString(++i, this.getYear());
    }
    if (this.getTask() != null && !"".equals(this.getTask())) {
      pst.setString(++i, this.getTask());
    }
    if (this.getExtraInfo() != null && !"".equals(this.getExtraInfo())) {
      pst.setString(++i, this.getExtraInfo());
    }
    if (this.getEnabled()) {
      pst.setBoolean(++i, this.getEnabled());
    }
    if (this.getEntered() != null) {
      DatabaseUtils.setTimestamp(pst, ++i, this.getEntered());
    }
    pst.setInt(++i, this.getProcessId());
    result = pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "business_process_e_event_id_seq", id);
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int result = -1;
    if (this.getId() != -1) {
      return result;
    }
    StringBuffer sql = new StringBuffer("UPDATE business_process_events SET ");
    if (this.getSecond() != null && !"".equals(this.getSecond())) {
      sql.append("\"second\" = ?, ");
    }
    if (this.getMinute() != null && !"".equals(this.getMinute())) {
      sql.append("\"minute\" = ?, ");
    }
    if (this.getHour() != null && !"".equals(this.getHour())) {
      sql.append("\"hour\" = ?, ");
    }
    if (this.getDayOfMonth() != null && !"".equals(getDayOfMonth())) {
      sql.append("dayofmonth = ?, ");
    }
    if (this.getMonth() != null && !"".equals(this.getMonth())) {
      sql.append("\"month\" = ?, ");
    }
    if (this.getDayOfWeek() != null && !"".equals(this.getDayOfWeek())) {
      sql.append("\"dayofweek\" = ?, ");
    }
    if (this.getYear() != null && !"".equals(this.getYear())) {
      sql.append("\"year\" = ?, ");
    }
    if (this.getTask() != null && !"".equals(this.getTask())) {
      sql.append("task = ?, ");
    }
    if (this.getExtraInfo() != null && !"".equals(this.getExtraInfo())) {
      sql.append("extrainfo = ?, ");
    }
    if (this.getEnabled()) {
      sql.append("enabled = ?, ");
    }
    sql.append("WHERE process_id = ? ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (this.getSecond() != null && !"".equals(this.getSecond())) {
      pst.setString(++i, getSecond());
    }
    if (this.getMinute() != null && !"".equals(this.getMinute())) {
      pst.setString(++i, this.getMinute());
    }
    if (this.getHour() != null && !"".equals(this.getHour())) {
      pst.setString(++i, this.getHour());
    }
    if (this.getDayOfMonth() != null && !"".equals(getDayOfMonth())) {
      pst.setString(++i, this.getDayOfMonth());
    }
    if (this.getMonth() != null && !"".equals(this.getMonth())) {
      pst.setString(++i, this.getMonth());
    }
    if (this.getDayOfWeek() != null && !"".equals(this.getDayOfWeek())) {
      pst.setString(++i, this.getDayOfWeek());
    }
    if (this.getYear() != null && !"".equals(this.getYear())) {
      pst.setString(++i, this.getYear());
    }
    if (this.getTask() != null && !"".equals(this.getTask())) {
      pst.setString(++i, this.getTask());
    }
    if (this.getExtraInfo() != null && !"".equals(this.getExtraInfo())) {
      pst.setString(++i, this.getExtraInfo());
    }
    if (this.getEnabled()) {
      pst.setBoolean(++i, this.getEnabled());
    }
    pst.setInt(++i, this.getProcessId());
    result = pst.executeUpdate();
    pst.close();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM business_process_events " +
        "WHERE event_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   * Description of the Method
   *
   * @param processList Description of the Parameter
   */
  public void buildResources(BusinessProcessList processList) {
    //Build the business process by the task name
    BusinessProcess process = (BusinessProcess) processList.get(
        this.getTask());
    if (process != null) {
      //add the event to the list of events.
      if (process.getEvents() == null) {
        process.setEvents(new ScheduledEventList());
      }
      process.getEvents().add(this);
    }
  }


  public boolean isDuplicate(ScheduledEvent event) {
    int resultCount = 0;
    if (!this.getTask().equals(event.getTask())) {
      resultCount++;
    }
    if ((resultCount == 0) && !this.getSecond().equals(event.getSecond())) {
      resultCount++;
    }
    if ((resultCount == 0) && !this.getMinute().equals(event.getMinute())) {
      resultCount++;
    }
    if ((resultCount == 0) && !this.getHour().equals(event.getHour())) {
      resultCount++;
    }
    if ((resultCount == 0) && !this.getDayOfMonth().equals(
        event.getDayOfMonth())) {
      resultCount++;
    }
    if ((resultCount == 0) && !this.getMonth().equals(event.getMonth())) {
      resultCount++;
    }
    if ((resultCount == 0) && !this.getDayOfWeek().equals(
        event.getDayOfWeek())) {
      resultCount++;
    }
    if ((resultCount == 0) && !this.getYear().equals(event.getYear())) {
      resultCount++;
    }
    return (resultCount == 0);
  }
}

