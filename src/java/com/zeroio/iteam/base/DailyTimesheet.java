/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.sql.*;
import java.text.*;

import org.aspcfs.utils.DatabaseUtils;

/**
 * Represents one day on the timesheet
 *
 * @author matt rajkowski
 * @version $Id$
 * @created November 8, 2004
 */
public class DailyTimesheet {

  // properties
  private int id = -1;
  private int userId = -1;
  private Timestamp entryDate = null;
  private double totalHours = 0;
  private Timestamp startTime = null;
  private Timestamp endTime = null;
  private boolean verified = false;
  private boolean approved = false;
  private int approvedBy = -1;
  private boolean available = false;
  private boolean unavailable = false;
  private boolean vacation = false;
  private boolean vacationApproved = false;
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  // projects linked to this timesheet
  private ProjectTimesheetList projectTimesheetList = null;


  /**
   * Constructor for the DailyTimesheet object
   */
  public DailyTimesheet() {
  }


  /**
   * Constructor for the DailyTimesheet object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public DailyTimesheet(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Gets the id attribute of the DailyTimesheet object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the DailyTimesheet object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the DailyTimesheet object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the userId attribute of the DailyTimesheet object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Sets the userId attribute of the DailyTimesheet object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the DailyTimesheet object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   * Gets the entryDate attribute of the DailyTimesheet object
   *
   * @return The entryDate value
   */
  public Timestamp getEntryDate() {
    return entryDate;
  }


  /**
   * Sets the entryDate attribute of the DailyTimesheet object
   *
   * @param tmp The new entryDate value
   */
  public void setEntryDate(Timestamp tmp) {
    this.entryDate = tmp;
  }


  /**
   * Sets the entryDate attribute of the DailyTimesheet object
   *
   * @param tmp The new entryDate value
   */
  public void setEntryDate(String tmp) {
    this.entryDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the totalHours attribute of the DailyTimesheet object
   *
   * @return The totalHours value
   */
  public double getTotalHours() {
    return totalHours;
  }


  /**
   * Sets the totalHours attribute of the DailyTimesheet object
   *
   * @param tmp The new totalHours value
   */
  public void setTotalHours(double tmp) {
    this.totalHours = tmp;
  }


  /**
   * Sets the totalHours attribute of the DailyTimesheet object
   *
   * @param tmp The new totalHours value
   */
  public void setTotalHours(String tmp) {
    this.totalHours = Double.parseDouble(tmp);
  }


  /**
   * Gets the startTime attribute of the DailyTimesheet object
   *
   * @return The startTime value
   */
  public Timestamp getStartTime() {
    return startTime;
  }


  /**
   * Sets the startTime attribute of the DailyTimesheet object
   *
   * @param tmp The new startTime value
   */
  public void setStartTime(Timestamp tmp) {
    this.startTime = tmp;
  }


  /**
   * Sets the startTime attribute of the DailyTimesheet object
   *
   * @param tmp The new startTime value
   */
  public void setStartTime(String tmp) {
    this.startTime = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the endTime attribute of the DailyTimesheet object
   *
   * @return The endTime value
   */
  public Timestamp getEndTime() {
    return endTime;
  }


  /**
   * Sets the endTime attribute of the DailyTimesheet object
   *
   * @param tmp The new endTime value
   */
  public void setEndTime(Timestamp tmp) {
    this.endTime = tmp;
  }


  /**
   * Sets the endTime attribute of the DailyTimesheet object
   *
   * @param tmp The new endTime value
   */
  public void setEndTime(String tmp) {
    this.endTime = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the verified attribute of the DailyTimesheet object
   *
   * @return The verified value
   */
  public boolean getVerified() {
    return verified;
  }


  /**
   * Sets the verified attribute of the DailyTimesheet object
   *
   * @param tmp The new verified value
   */
  public void setVerified(boolean tmp) {
    this.verified = tmp;
  }


  /**
   * Sets the verified attribute of the DailyTimesheet object
   *
   * @param tmp The new verified value
   */
  public void setVerified(String tmp) {
    this.verified = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the approved attribute of the DailyTimesheet object
   *
   * @return The approved value
   */
  public boolean getApproved() {
    return approved;
  }


  /**
   * Sets the approved attribute of the DailyTimesheet object
   *
   * @param tmp The new approved value
   */
  public void setApproved(boolean tmp) {
    this.approved = tmp;
  }


  /**
   * Sets the approved attribute of the DailyTimesheet object
   *
   * @param tmp The new approved value
   */
  public void setApproved(String tmp) {
    this.approved = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the approvedBy attribute of the DailyTimesheet object
   *
   * @return The approvedBy value
   */
  public int getApprovedBy() {
    return approvedBy;
  }


  /**
   * Sets the approvedBy attribute of the DailyTimesheet object
   *
   * @param tmp The new approvedBy value
   */
  public void setApprovedBy(int tmp) {
    this.approvedBy = tmp;
  }


  /**
   * Sets the approvedBy attribute of the DailyTimesheet object
   *
   * @param tmp The new approvedBy value
   */
  public void setApprovedBy(String tmp) {
    this.approvedBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the available attribute of the DailyTimesheet object
   *
   * @return The available value
   */
  public boolean getAvailable() {
    return available;
  }


  /**
   * Sets the available attribute of the DailyTimesheet object
   *
   * @param tmp The new available value
   */
  public void setAvailable(boolean tmp) {
    this.available = tmp;
  }


  /**
   * Sets the available attribute of the DailyTimesheet object
   *
   * @param tmp The new available value
   */
  public void setAvailable(String tmp) {
    this.available = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the unavailable attribute of the DailyTimesheet object
   *
   * @return The unavailable value
   */
  public boolean getUnavailable() {
    return unavailable;
  }


  /**
   * Sets the unavailable attribute of the DailyTimesheet object
   *
   * @param tmp The new unavailable value
   */
  public void setUnavailable(boolean tmp) {
    this.unavailable = tmp;
  }


  /**
   * Sets the unavailable attribute of the DailyTimesheet object
   *
   * @param tmp The new unavailable value
   */
  public void setUnavailable(String tmp) {
    this.unavailable = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the vacation attribute of the DailyTimesheet object
   *
   * @return The vacation value
   */
  public boolean getVacation() {
    return vacation;
  }


  /**
   * Sets the vacation attribute of the DailyTimesheet object
   *
   * @param tmp The new vacation value
   */
  public void setVacation(boolean tmp) {
    this.vacation = tmp;
  }


  /**
   * Sets the vacation attribute of the DailyTimesheet object
   *
   * @param tmp The new vacation value
   */
  public void setVacation(String tmp) {
    this.vacation = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the vacationApproved attribute of the DailyTimesheet object
   *
   * @return The vacationApproved value
   */
  public boolean getVacationApproved() {
    return vacationApproved;
  }


  /**
   * Sets the vacationApproved attribute of the DailyTimesheet object
   *
   * @param tmp The new vacationApproved value
   */
  public void setVacationApproved(boolean tmp) {
    this.vacationApproved = tmp;
  }


  /**
   * Sets the vacationApproved attribute of the DailyTimesheet object
   *
   * @param tmp The new vacationApproved value
   */
  public void setVacationApproved(String tmp) {
    this.vacationApproved = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the entered attribute of the DailyTimesheet object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * Sets the entered attribute of the DailyTimesheet object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the DailyTimesheet object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the enteredBy attribute of the DailyTimesheet object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Sets the enteredBy attribute of the DailyTimesheet object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the DailyTimesheet object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the modified attribute of the DailyTimesheet object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   * Sets the modified attribute of the DailyTimesheet object
   *
   * @param tmp The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the DailyTimesheet object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the modifiedBy attribute of the DailyTimesheet object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Sets the modifiedBy attribute of the DailyTimesheet object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the DailyTimesheet object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the projectTimesheetList attribute of the DailyTimesheet object
   *
   * @return The projectTimesheetList value
   */
  public ProjectTimesheetList getProjectTimesheetList() {
    if (projectTimesheetList == null) {
      projectTimesheetList = new ProjectTimesheetList();
    }
    return projectTimesheetList;
  }


  /**
   * Sets the projectTimesheetList attribute of the DailyTimesheet object
   *
   * @param tmp The new projectTimesheetList value
   */
  public void setProjectTimesheetList(ProjectTimesheetList tmp) {
    this.projectTimesheetList = tmp;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("timesheet_id");
    userId = rs.getInt("user_id");
    entryDate = rs.getTimestamp("entry_date");
    totalHours = rs.getDouble("hours");
    startTime = rs.getTimestamp("start_time");
    endTime = rs.getTimestamp("end_time");
    verified = rs.getBoolean("verified");
    approved = rs.getBoolean("approved");
    approvedBy = DatabaseUtils.getInt(rs, "approved_by");
    available = rs.getBoolean("available");
    unavailable = rs.getBoolean("unavailable");
    vacation = rs.getBoolean("vacation");
    vacationApproved = rs.getBoolean("vacation_approved");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }


  /**
   * Gets the formattedEntryDate attribute of the DailyTimesheet object
   *
   * @return The formattedEntryDate value
   */
  public String getFormattedEntryDate() {
    // Store days in a hash map by date
    SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
    formatter.applyPattern("M/d/yyyy");
    return formatter.format(this.getEntryDate());
  }


  /**
   * Description of the Method
   *
   * @param hoursAdjustment Description of the Parameter
   */
  public void add(double hoursAdjustment) {
    totalHours += hoursAdjustment;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    if (userId == -1 || entryDate == null) {
      throw new SQLException("Invalid parameters");
    }
    // TODO: Add transaction
    // Avoid saving empty records
    if (totalHours != 0 || unavailable || vacation || verified) {
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO timesheet " +
          "(user_id, entry_date, hours, verified, approved, approved_by, " +
          "available, unavailable, vacation, enteredby, modifiedby) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
      int i = 0;
      pst.setInt(++i, userId);
      pst.setTimestamp(++i, entryDate);
      pst.setDouble(++i, totalHours);
      pst.setBoolean(++i, verified);
      pst.setBoolean(++i, approved);
      DatabaseUtils.setInt(pst, ++i, approvedBy);
      pst.setBoolean(++i, available);
      pst.setBoolean(++i, unavailable);
      pst.setBoolean(++i, vacation);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "timesheet_timesheet_id_seq");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("DailyTimesheetList-> INSERTED: " + id);
      }
      // Insert any project data
      if (projectTimesheetList != null) {
        projectTimesheetList.setTimesheetId(id);
        projectTimesheetList.insert(db);
      }
    }
  }

  public boolean isSubmitted(Connection db) throws SQLException {
    if (userId == -1 || entryDate == null) {
      throw new SQLException("Invalid parameters");
    }
    boolean submitted = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT verified " +
        "FROM timesheet " +
        "WHERE user_id = ? " +
        "AND entry_date = ? ");
    int i = 0;
    pst.setInt(++i, userId);
    pst.setTimestamp(++i, entryDate);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      submitted = rs.getBoolean("verified");
    }
    rs.close();
    pst.close();
    return submitted;
  }

  public void delete(Connection db) throws SQLException {
    if (userId == -1 || entryDate == null) {
      throw new SQLException("Invalid parameters");
    }
    PreparedStatement pst = null;
    // Delete dependencies
    pst = db.prepareStatement(
        "DELETE FROM timesheet_projects " +
        "WHERE timesheet_id IN " +
        "(SELECT timesheet_id FROM timesheet WHERE user_id = ? " +
        "AND entry_date = ?) ");
    int i = 0;
    pst.setInt(++i, userId);
    pst.setTimestamp(++i, entryDate);
    pst.execute();
    pst.close();
    // Delete the timesheet
    pst = db.prepareStatement(
        "DELETE FROM timesheet " +
        "WHERE user_id = ? " +
        "AND entry_date = ? ");
    i = 0;
    pst.setInt(++i, userId);
    pst.setTimestamp(++i, entryDate);
    pst.execute();
    pst.close();
  }

}

