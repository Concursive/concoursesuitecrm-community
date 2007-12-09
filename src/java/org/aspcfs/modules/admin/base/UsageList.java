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
package org.aspcfs.modules.admin.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Represents a list of Usage objects. Can be used for generating the list by
 * specifying parameters and then initiating buildList to query the database.
 * Can also be used for just querying the count of objects and the sum of
 * record objects.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created December 6, 2002
 */
public class UsageList extends ArrayList {

  public final static String tableName = "usage_log";
  public final static String uniqueField = "usage_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private int action = -1;
  private java.sql.Timestamp enteredRangeStart = null;
  private java.sql.Timestamp enteredRangeEnd = null;

  private long count = -1;
  private long size = -1;


  /**
   * Constructor for the UsageList object
   */
  public UsageList() {
  }

  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionItemList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Gets the tableName attribute of the ActionItemList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionItemList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Sets the action attribute of the UsageList object
   *
   * @param tmp The new action value
   */
  public void setAction(int tmp) {
    this.action = tmp;
  }


  /**
   * Sets the action attribute of the UsageList object
   *
   * @param tmp The new action value
   */
  public void setAction(String tmp) {
    this.action = Integer.parseInt(tmp);
  }


  /**
   * Sets the enteredRangeStart attribute of the UsageList object
   *
   * @param tmp The new enteredRangeStart value
   */
  public void setEnteredRangeStart(java.sql.Timestamp tmp) {
    this.enteredRangeStart = tmp;
  }


  /**
   * Sets the enteredRangeStart attribute of the UsageList object
   *
   * @param tmp The new enteredRangeStart value
   */
  public void setEnteredRangeStart(String tmp) {
    this.enteredRangeStart = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredRangeEnd attribute of the UsageList object
   *
   * @param tmp The new enteredRangeEnd value
   */
  public void setEnteredRangeEnd(java.sql.Timestamp tmp) {
    this.enteredRangeEnd = tmp;
  }


  /**
   * Sets the enteredRangeEnd attribute of the UsageList object
   *
   * @param tmp The new enteredRangeEnd value
   */
  public void setEnteredRangeEnd(String tmp) {
    this.enteredRangeEnd = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the count attribute of the UsageList object
   *
   * @param tmp The new count value
   */
  public void setCount(long tmp) {
    this.count = tmp;
  }


  /**
   * Sets the size attribute of the UsageList object
   *
   * @param tmp The new size value
   */
  public void setSize(long tmp) {
    this.size = tmp;
  }


  /**
   * Gets the action attribute of the UsageList object
   *
   * @return The action value
   */
  public int getAction() {
    return action;
  }


  /**
   * Gets the enteredRangeStart attribute of the UsageList object
   *
   * @return The enteredRangeStart value
   */
  public java.sql.Timestamp getEnteredRangeStart() {
    return enteredRangeStart;
  }


  /**
   * Gets the enteredRangeEnd attribute of the UsageList object
   *
   * @return The enteredRangeEnd value
   */
  public java.sql.Timestamp getEnteredRangeEnd() {
    return enteredRangeEnd;
  }


  /**
   * Gets the count attribute of the UsageList object
   *
   * @return The count value
   */
  public long getCount() {
    return count;
  }


  /**
   * Gets the size attribute of the UsageList object
   *
   * @return The size value
   */
  public long getSize() {
    return size;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT count(*) AS recordcount " +
            "FROM usage_log ul ");

    createFilter(db, sqlFilter);

    sqlOrder.append("ORDER BY ul.usage_id ");

    sqlSelect.append(" SELECT ");
    sqlSelect.append(
        "* " +
            "FROM usage_log ul " +
            "WHERE ul.usage_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Usage usage = new Usage(rs);
      this.add(usage);
    }
    rs.close();
    pst.close();
  }


  /**
   * Queries the count of records and the sum of recordSizes based on the
   * specified parameters.
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildUsage(Connection db) throws SQLException {
    StringBuffer sqlFilter = new StringBuffer();
    String sqlCount =
        "SELECT COUNT(*) AS recordcount, SUM(record_size) AS recordsize " +
            "FROM usage_log u " +
            "WHERE u.usage_id > -1 ";
    createFilter(db, sqlFilter);
    PreparedStatement pst = db.prepareStatement(
        sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = DatabaseUtils.getInt(rs, "recordcount", 0);
      size = DatabaseUtils.getInt(rs, "recordsize", 0);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (action > -1) {
      sqlFilter.append("AND " + DatabaseUtils.addQuotes(db, "action") + " = ? ");
    }
    if (enteredRangeStart != null) {
      sqlFilter.append("AND entered >= ? ");
    }
    if (enteredRangeEnd != null) {
      sqlFilter.append("AND entered <= ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (action > -1) {
      pst.setInt(++i, action);
    }
    if (enteredRangeStart != null) {
      pst.setTimestamp(++i, enteredRangeStart);
    }
    if (enteredRangeEnd != null) {
      pst.setTimestamp(++i, enteredRangeEnd);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    return i;
  }

}
