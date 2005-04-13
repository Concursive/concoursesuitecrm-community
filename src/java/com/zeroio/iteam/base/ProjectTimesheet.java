/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *@author     matt rajkowski
 *@created    December 3, 2004
 *@version    $Id$
 */
public class ProjectTimesheet {

  private int id = -1;
  private int timesheetId = -1;
  private int projectId = -1;
  private double hours = 0;
  private DailyTimesheetList dailyTimesheetList = null;


  /**
   *  Gets the timesheetId attribute of the ProjectTimesheet object
   *
   *@return    The timesheetId value
   */
  public int getTimesheetId() {
    return timesheetId;
  }


  /**
   *  Sets the timesheetId attribute of the ProjectTimesheet object
   *
   *@param  tmp  The new timesheetId value
   */
  public void setTimesheetId(int tmp) {
    this.timesheetId = tmp;
  }


  /**
   *  Sets the timesheetId attribute of the ProjectTimesheet object
   *
   *@param  tmp  The new timesheetId value
   */
  public void setTimesheetId(String tmp) {
    this.timesheetId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the projectId attribute of the ProjectTimesheet object
   *
   *@return    The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   *  Sets the projectId attribute of the ProjectTimesheet object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the projectId attribute of the ProjectTimesheet object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the hours attribute of the ProjectTimesheet object
   *
   *@return    The hours value
   */
  public double getHours() {
    return hours;
  }


  /**
   *  Sets the hours attribute of the ProjectTimesheet object
   *
   *@param  tmp  The new hours value
   */
  public void setHours(double tmp) {
    this.hours = tmp;
  }


  /**
   *  Sets the hours attribute of the ProjectTimesheet object
   *
   *@param  tmp  The new hours value
   */
  public void setHours(String tmp) {
    this.hours = Double.parseDouble(tmp);
  }


  /**
   *  Gets the dailyTimesheetList attribute of the ProjectTimesheet object
   *
   *@return    The dailyTimesheetList value
   */
  public DailyTimesheetList getDailyTimesheetList() {
    if (dailyTimesheetList == null) {
      dailyTimesheetList = new DailyTimesheetList();
    }
    return dailyTimesheetList;
  }


  /**
   *  Sets the dailyTimesheetList attribute of the ProjectTimesheet object
   *
   *@param  tmp  The new dailyTimesheetList value
   */
  public void setDailyTimesheetList(DailyTimesheetList tmp) {
    this.dailyTimesheetList = tmp;
  }


  /**
   *  Constructor for the ProjectTimesheet object
   */
  public ProjectTimesheet() { }


  /**
   *  Constructor for the ProjectTimesheet object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ProjectTimesheet(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    timesheetId = rs.getInt("timesheet_id");
    projectId = DatabaseUtils.getInt(rs, "project_id");
    hours = rs.getDouble("hours");
  }


  /**
   *  Description of the Method
   *
   *@param  hoursAdjustment  Description of the Parameter
   */
  public void add(double hoursAdjustment) {
    hours += hoursAdjustment;
  }


  /**
   *  Description of the Method
   *
   *@param  dailyTimesheet  Description of the Parameter
   */
  public void add(DailyTimesheet dailyTimesheet) {
    getDailyTimesheetList().add(dailyTimesheet);
  }


  /**
   *  Gets the hours attribute of the ProjectTimesheet object
   *
   *@param  millis  Description of the Parameter
   *@return         The hours value
   */
  public double getHours(long millis) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(millis);
    DailyTimesheet dailyTimesheet = getDailyTimesheetList().getTimesheet(cal);
    if (dailyTimesheet != null) {
      return dailyTimesheet.getTotalHours();
    }
    return 0;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO timesheet_projects " +
        "(timesheet_id, project_id, hours) VALUES (?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, timesheetId);
    DatabaseUtils.setInt(pst, ++i, projectId);
    pst.setDouble(++i, hours);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "timesheet_projects_id_seq");
  }
}

