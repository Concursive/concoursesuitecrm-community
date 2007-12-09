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
package org.aspcfs.modules.reports.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a piece of criteria for a report queue
 *
 * @author matt rajkowski
 * @version $Id: QueueCriteria.java,v 1.1.2.1 2003/10/03 20:54:54 mrajkowski
 *          Exp $
 * @created October 3, 2003
 */
public class QueueCriteria extends GenericBean {

  private int id = -1;
  private int queueId = -1;
  private String parameter = null;
  private String value = null;


  /**
   * Constructor for the QueueCriteria object
   */
  public QueueCriteria() {
  }


  /**
   * Constructor for the QueueCriteria object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public QueueCriteria(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT q.* " +
            "FROM report_queue_criteria q " +
            "WHERE criteria_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Queue Criteria record not found.");
    }
  }


  /**
   * Constructor for the QueueCriteria object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public QueueCriteria(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the QueueCriteria object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the QueueCriteria object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the queueId attribute of the QueueCriteria object
   *
   * @param tmp The new queueId value
   */
  public void setQueueId(int tmp) {
    this.queueId = tmp;
  }


  /**
   * Sets the queueId attribute of the QueueCriteria object
   *
   * @param tmp The new queueId value
   */
  public void setQueueId(String tmp) {
    this.queueId = Integer.parseInt(tmp);
  }


  /**
   * Sets the parameter attribute of the QueueCriteria object
   *
   * @param tmp The new parameter value
   */
  public void setParameter(String tmp) {
    this.parameter = tmp;
  }


  /**
   * Sets the value attribute of the QueueCriteria object
   *
   * @param tmp The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp;
  }


  /**
   * Gets the id attribute of the QueueCriteria object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the queueId attribute of the QueueCriteria object
   *
   * @return The queueId value
   */
  public int getQueueId() {
    return queueId;
  }


  /**
   * Gets the parameter attribute of the QueueCriteria object
   *
   * @return The parameter value
   */
  public String getParameter() {
    return parameter;
  }


  /**
   * Gets the value attribute of the QueueCriteria object
   *
   * @return The value value
   */
  public String getValue() {
    return value;
  }


  /**
   * Populates this object from a ResetSet record
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //report_queue_criteria table
    id = rs.getInt("criteria_id");
    queueId = rs.getInt("queue_id");
    parameter = rs.getString("parameter");
    value = rs.getString("value");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "report_queue_criteria_criteria_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO report_queue_criteria " +
            "(" + (id > -1 ? "criteria_id, " : "") + "queue_id, " + DatabaseUtils.addQuotes(db, "parameter") + ", " + DatabaseUtils.addQuotes(db, "value") + ") " +
            "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, queueId);
    pst.setString(++i, parameter);
    pst.setString(++i, value);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "report_queue_criteria_criteria_id_seq", id);
  }

}

