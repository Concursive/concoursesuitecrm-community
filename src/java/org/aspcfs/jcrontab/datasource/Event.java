package org.aspcfs.jcrontab.datasource;

import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Represents an entry for the jcron [events] table
 *
 *@author     matt rajkowski
 *@created    August 27, 2003
 *@version    $Id$
 */
public class Event {
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
   *  Constructor for the Event object
   */
  public Event() { }


  /**
   *  Sets the minute attribute of the Event object
   *
   *@param  tmp  The new minute value
   */
  public void setMinute(String tmp) {
    this.minute = tmp;
  }


  /**
   *  Sets the hour attribute of the Event object
   *
   *@param  tmp  The new hour value
   */
  public void setHour(String tmp) {
    this.hour = tmp;
  }


  /**
   *  Sets the dayOfMonth attribute of the Event object
   *
   *@param  tmp  The new dayOfMonth value
   */
  public void setDayOfMonth(String tmp) {
    this.dayOfMonth = tmp;
  }


  /**
   *  Sets the month attribute of the Event object
   *
   *@param  tmp  The new month value
   */
  public void setMonth(String tmp) {
    this.month = tmp;
  }


  /**
   *  Sets the dayOfWeek attribute of the Event object
   *
   *@param  tmp  The new dayOfWeek value
   */
  public void setDayOfWeek(String tmp) {
    this.dayOfWeek = tmp;
  }


  /**
   *  Sets the year attribute of the Event object
   *
   *@param  tmp  The new year value
   */
  public void setYear(String tmp) {
    this.year = tmp;
  }


  /**
   *  Sets the task attribute of the Event object
   *
   *@param  tmp  The new task value
   */
  public void setTask(String tmp) {
    this.task = tmp;
  }


  /**
   *  Sets the extraInfo attribute of the Event object
   *
   *@param  tmp  The new extraInfo value
   */
  public void setExtraInfo(String tmp) {
    this.extraInfo = tmp;
  }


  /**
   *  Sets the enabled attribute of the Event object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the Event object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the minute attribute of the Event object
   *
   *@return    The minute value
   */
  public String getMinute() {
    return minute;
  }


  /**
   *  Gets the hour attribute of the Event object
   *
   *@return    The hour value
   */
  public String getHour() {
    return hour;
  }


  /**
   *  Gets the dayOfMonth attribute of the Event object
   *
   *@return    The dayOfMonth value
   */
  public String getDayOfMonth() {
    return dayOfMonth;
  }


  /**
   *  Gets the month attribute of the Event object
   *
   *@return    The month value
   */
  public String getMonth() {
    return month;
  }


  /**
   *  Gets the dayOfWeek attribute of the Event object
   *
   *@return    The dayOfWeek value
   */
  public String getDayOfWeek() {
    return dayOfWeek;
  }


  /**
   *  Gets the year attribute of the Event object
   *
   *@return    The year value
   */
  public String getYear() {
    return year;
  }


  /**
   *  Gets the task attribute of the Event object
   *
   *@return    The task value
   */
  public String getTask() {
    return task;
  }


  /**
   *  Gets the extraInfo attribute of the Event object
   *
   *@return    The extraInfo value
   */
  public String getExtraInfo() {
    return extraInfo;
  }


  /**
   *  Gets the enabled attribute of the Event object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO events (" +
        "minute, hour, dayofmonth, " +
        "month, dayofweek, year, " +
        "task, extrainfo, enabled) " +
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ");
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
  }

}

