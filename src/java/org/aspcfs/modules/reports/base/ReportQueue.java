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
package org.aspcfs.modules.reports.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;

/**
 * Represents a report that has been queued
 *
 * @author matt rajkowski
 * @version $Id: ReportQueue.java,v 1.1.2.2 2003/10/02 21:07:25 mrajkowski Exp
 *          $
 * @created October 1, 2003
 */
public class ReportQueue extends GenericBean {

  public final static int STATUS_UNDEFINED = -1;
  public final static int STATUS_QUEUED = 0;
  public final static int STATUS_PROCESSING = 1;
  public final static int STATUS_PROCESSED = 2;
  public final static int STATUS_ERROR = 3;

  private int id = -1;
  private int reportId = -1;
  private Timestamp entered = null;
  private int enteredBy = -1;
  private int status = -1;
  private Timestamp processed = null;
  private String filename = null;
  private long size = -1;
  private boolean enabled = true;
  //Resources
  private int position = -1;
  private Report report = null;
  private boolean throwNotFoundException = true;


  /**
   * Constructor for the ReportQueue object
   */
  public ReportQueue() {
  }


  /**
   * Constructor for the ReportQueue object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ReportQueue(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the ReportQueue object
   *
   * @param db      Description of the Parameter
   * @param queueId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ReportQueue(Connection db, int queueId) throws SQLException {
    queryRecord(db, queueId);
  }


  /**
   * Constructor for the ReportQueue object
   *
   * @param db             Description of the Parameter
   * @param queueId        Description of the Parameter
   * @param throwException Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ReportQueue(Connection db, int queueId, boolean throwException) throws SQLException {
    throwNotFoundException = throwException;
    queryRecord(db, queueId);
  }


  /**
   * Loads the specified ReportQueue
   *
   * @param db      Description of the Parameter
   * @param queueId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int queueId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT q.* " +
        "FROM report_queue q " +
        "WHERE queue_id = ? ");
    pst.setInt(1, queueId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if ((id == -1) && (throwNotFoundException)) {
      throw new SQLException("Queue record not found.");
    }
  }


  /**
   * Sets the id attribute of the ReportQueue object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ReportQueue object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the reportId attribute of the ReportQueue object
   *
   * @param tmp The new reportId value
   */
  public void setReportId(int tmp) {
    this.reportId = tmp;
  }


  /**
   * Sets the reportId attribute of the ReportQueue object
   *
   * @param tmp The new reportId value
   */
  public void setReportId(String tmp) {
    this.reportId = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the ReportQueue object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the ReportQueue object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the ReportQueue object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the ReportQueue object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the status attribute of the ReportQueue object
   *
   * @param tmp The new status value
   */
  public void setStatus(int tmp) {
    this.status = tmp;
  }


  /**
   * Sets the status attribute of the ReportQueue object
   *
   * @param tmp The new status value
   */
  public void setStatus(String tmp) {
    this.status = Integer.parseInt(tmp);
  }


  /**
   * Sets the processed attribute of the ReportQueue object
   *
   * @param tmp The new processed value
   */
  public void setProcessed(Timestamp tmp) {
    this.processed = tmp;
  }


  /**
   * Sets the processed attribute of the ReportQueue object
   *
   * @param tmp The new processed value
   */
  public void setProcessed(String tmp) {
    this.processed = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the filename attribute of the ReportQueue object
   *
   * @param tmp The new filename value
   */
  public void setFilename(String tmp) {
    this.filename = tmp;
  }


  /**
   * Sets the size attribute of the ReportQueue object
   *
   * @param tmp The new size value
   */
  public void setSize(long tmp) {
    this.size = tmp;
  }


  /**
   * Sets the enabled attribute of the ReportQueue object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ReportQueue object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the position attribute of the ReportQueue object
   *
   * @param tmp The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   * Sets the position attribute of the ReportQueue object
   *
   * @param tmp The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
  }


  /**
   * Sets the report attribute of the ReportQueue object
   *
   * @param tmp The new report value
   */
  public void setReport(Report tmp) {
    this.report = tmp;
  }


  /**
   * Sets the throwNotFoundException attribute of the ReportQueue object
   *
   * @param tmp The new throwNotFoundException value
   */
  public void setThrowNotFoundException(boolean tmp) {
    this.throwNotFoundException = tmp;
  }


  /**
   * Sets the throwNotFoundException attribute of the ReportQueue object
   *
   * @param tmp The new throwNotFoundException value
   */
  public void setThrowNotFoundException(String tmp) {
    this.throwNotFoundException = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the id attribute of the ReportQueue object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the reportId attribute of the ReportQueue object
   *
   * @return The reportId value
   */
  public int getReportId() {
    return reportId;
  }


  /**
   * Gets the entered attribute of the ReportQueue object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the ReportQueue object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the status attribute of the ReportQueue object
   *
   * @return The status value
   */
  public int getStatus() {
    return status;
  }


  /**
   * Gets the processed attribute of the ReportQueue object
   *
   * @return The processed value
   */
  public Timestamp getProcessed() {
    return processed;
  }


  /**
   * Gets the filename attribute of the ReportQueue object
   *
   * @return The filename value
   */
  public String getFilename() {
    return filename;
  }


  /**
   * Gets the size attribute of the ReportQueue object
   *
   * @return The size value
   */
  public long getSize() {
    return size;
  }


  /**
   * Gets the enabled attribute of the ReportQueue object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the position attribute of the ReportQueue object
   *
   * @return The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   * Gets the report attribute of the ReportQueue object
   *
   * @return The report value
   */
  public Report getReport() {
    return report;
  }


  /**
   * Gets the throwNotFoundException attribute of the ReportQueue object
   *
   * @return The throwNotFoundException value
   */
  public boolean getThrowNotFoundException() {
    return throwNotFoundException;
  }


  /**
   * Populates this object from a resultset
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("queue_id");
    reportId = rs.getInt("report_id");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    processed = rs.getTimestamp("processed");
    status = rs.getInt("status");
    filename = rs.getString("filename");
    size = DatabaseUtils.getLong(rs, "filesize");
    enabled = rs.getBoolean("enabled");
  }


  /**
   * Based on Criteria that has been set, this methods makes a copy and stores
   * the settings for running the specified report
   *
   * @param db       Description of the Parameter
   * @param criteria Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int insert(Connection db, Criteria criteria) throws SQLException {
    try {
      db.setAutoCommit(false);
      //Insert the new report into the queue
      int id = DatabaseUtils.getNextSeq(db, "report_queue_queue_id_seq");
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO report_queue " +
          "(" + (id > -1 ? "queue_id, " : "") + "report_id, enteredby) " +
          "VALUES (" + (id > -1 ? "?, " : "") + "?, ?) ");
      int i = 0;
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, criteria.getReportId());
      pst.setInt(++i, criteria.getOwner());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "report_queue_queue_id_seq", id);
      //Insert the criteria for processing the report
      int rqcId = DatabaseUtils.getNextSeq(
          db, "report_queue_criteria_criteria_id_seq");
      pst = db.prepareStatement(
          "INSERT INTO report_queue_criteria " +
          "(" + (rqcId > -1 ? "criteria_id, " : "") + "queue_id, \"parameter\", value) " +
          "VALUES (" + (rqcId > -1 ? "?, " : "") + "?, ?, ?) ");
      Iterator params = criteria.getParameters().iterator();
      while (params.hasNext()) {
        Parameter param = (Parameter) params.next();
        int ip = 0;
        if (rqcId > -1) {
          pst.setInt(++ip, rqcId);
        }
        pst.setInt(++ip, id);
        pst.setString(++ip, param.getName());
        pst.setString(++ip, param.getValue());
        pst.execute();
        if (rqcId > -1 && params.hasNext()) {
          rqcId = DatabaseUtils.getNextSeq(
              db, "report_queue_criteria_criteria_id_seq");
        }
      }
      pst.close();
      //Get the total number of reports pending
      pst = db.prepareStatement(
          "SELECT count(*) AS position " +
          "FROM report_queue " +
          "WHERE processed IS NULL ");
      ResultSet rs = pst.executeQuery();
      rs.next();
      int position = rs.getInt("position");
      rs.close();
      pst.close();
      db.commit();
      return position;
    } catch (Exception e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   * Populates the report property when needed, otherwise the report property
   * is unset by default
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildReport(Connection db) throws SQLException {
    report = new Report(db, reportId);
  }


  /**
   * Updates the status of the report during different stages of processing
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE report_queue " +
        "SET status = ?, " +
        (filename != null ? "filename = ?, " : "") +
        "filesize = ?, " +
        "processed = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE queue_id = ? ");
    int i = 0;
    pst.setInt(++i, status);
    if (filename != null) {
      pst.setString(++i, filename);
    }
    DatabaseUtils.setLong(pst, ++i, size);
    pst.setInt(++i, id);
    int count = pst.executeUpdate();
    pst.close();
    return (count == 1);
  }


  /**
   * Deletes the ReportQueue reference and the associated file
   *
   * @param db            Description of the Parameter
   * @param localFilename Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   * @throws IOException  Description of the Exception
   */
  public boolean delete(Connection db, String localFilename) throws SQLException, IOException {
    if (id == -1) {
      return false;
    }
    if (report == null && reportId > -1) {
      buildReport(db);
    }
    //Try to delete the file first
    if (filename != null) {
      File file = new File(localFilename);
      if (file.exists()) {
        file.delete();
      }
    }
    //Delete the record and associated data
    try {
      db.setAutoCommit(false);
      //Delete the criteria
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM report_queue_criteria " +
          "WHERE queue_id = ? ");
      pst.setInt(1, id);
      pst.execute();
      pst.close();
      //Delete the queue info
      pst = db.prepareStatement(
          "DELETE FROM report_queue " +
          "WHERE queue_id = ? ");
      pst.setInt(1, id);
      pst.execute();
      pst.close();
      db.commit();
    } catch (Exception e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }
}

