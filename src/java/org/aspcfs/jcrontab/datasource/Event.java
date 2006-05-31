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
package org.aspcfs.jcrontab.datasource;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents an entry for the jcron [events] table
 *
 * @author matt rajkowski
 * @version $Id$
 * @created August 27, 2003
 */
public class Event {
  private int id = -1;
  private String minute = "*";
  private String hour = "*";
  private String dayOfMonth = "*";
  private String month = "*";
  private String dayOfWeek = "*";
  private String year = "*";
  private String task = null;
  private String extraInfo = null;
  private boolean enabled = true;


  /**
   * Constructor for the Event object
   */
  public Event() {
  }


  /**
   * Sets the minute attribute of the Event object
   *
   * @param tmp The new minute value
   */
  public void setMinute(String tmp) {
    this.minute = tmp;
  }


  /**
   * Sets the hour attribute of the Event object
   *
   * @param tmp The new hour value
   */
  public void setHour(String tmp) {
    this.hour = tmp;
  }


  /**
   * Sets the dayOfMonth attribute of the Event object
   *
   * @param tmp The new dayOfMonth value
   */
  public void setDayOfMonth(String tmp) {
    this.dayOfMonth = tmp;
  }


  /**
   * Sets the month attribute of the Event object
   *
   * @param tmp The new month value
   */
  public void setMonth(String tmp) {
    this.month = tmp;
  }


  /**
   * Sets the dayOfWeek attribute of the Event object
   *
   * @param tmp The new dayOfWeek value
   */
  public void setDayOfWeek(String tmp) {
    this.dayOfWeek = tmp;
  }


  /**
   * Sets the year attribute of the Event object
   *
   * @param tmp The new year value
   */
  public void setYear(String tmp) {
    this.year = tmp;
  }


  /**
   * Sets the task attribute of the Event object
   *
   * @param tmp The new task value
   */
  public void setTask(String tmp) {
    this.task = tmp;
  }


  /**
   * Sets the extraInfo attribute of the Event object
   *
   * @param tmp The new extraInfo value
   */
  public void setExtraInfo(String tmp) {
    this.extraInfo = tmp;
  }


  /**
   * Sets the enabled attribute of the Event object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the Event object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the minute attribute of the Event object
   *
   * @return The minute value
   */
  public String getMinute() {
    return minute;
  }


  /**
   * Gets the hour attribute of the Event object
   *
   * @return The hour value
   */
  public String getHour() {
    return hour;
  }


  /**
   * Gets the dayOfMonth attribute of the Event object
   *
   * @return The dayOfMonth value
   */
  public String getDayOfMonth() {
    return dayOfMonth;
  }


  /**
   * Gets the month attribute of the Event object
   *
   * @return The month value
   */
  public String getMonth() {
    return month;
  }


  /**
   * Gets the dayOfWeek attribute of the Event object
   *
   * @return The dayOfWeek value
   */
  public String getDayOfWeek() {
    return dayOfWeek;
  }


  /**
   * Gets the year attribute of the Event object
   *
   * @return The year value
   */
  public String getYear() {
    return year;
  }


  /**
   * Gets the task attribute of the Event object
   *
   * @return The task value
   */
  public String getTask() {
    return task;
  }


  /**
   * Gets the extraInfo attribute of the Event object
   *
   * @return The extraInfo value
   */
  public String getExtraInfo() {
    return extraInfo;
  }


  /**
   * Gets the enabled attribute of the Event object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "events_event_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO events (" +
        (id > -1 ? "event_id, " : "") +
        "\"minute\", \"hour\", dayofmonth, " +
        "\"month\", \"dayofweek\", \"year\", " +
        "task, extrainfo, enabled) " +
        "VALUES(" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, minute);
    pst.setString(++i, hour);
    pst.setString(++i, dayOfMonth);
    pst.setString(++i, month);
    pst.setString(++i, dayOfWeek);
    pst.setString(++i, year);
    pst.setString(++i, task);
    pst.setString(++i, extraInfo);
    pst.setBoolean(++i, enabled);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "events_event_id_seq", id);
  }

}
