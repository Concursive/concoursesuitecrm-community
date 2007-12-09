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
package org.aspcfs.modules.media.autoguide.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Collection of Model objects
 *
 * @author matt
 * @version $Id$
 * @created May 17, 2002
 */
public class ModelList extends ArrayList implements SyncableList {

  public static String tableName = "autoguide_model";
  public static String uniqueField = "model_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private int makeId = -1;
  private int year = -1;


  /**
   * Constructor for the ModelList object
   */
  public ModelList() {
  }


  /**
   * Sets the lastAnchor attribute of the ModelList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ModelList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ModelList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ModelList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ModelList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Sets the syncType attribute of the ModelList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   * Sets the makeId attribute of the ModelList object
   *
   * @param tmp The new makeId value
   */
  public void setMakeId(int tmp) {
    this.makeId = tmp;
  }


  /**
   * Sets the makeId attribute of the ModelList object
   *
   * @param tmp The new makeId value
   */
  public void setMakeId(String tmp) {
    this.makeId = Integer.parseInt(tmp);
  }


  /**
   * Sets the year attribute of the ModelList object
   *
   * @param tmp The new year value
   */
  public void setYear(int tmp) {
    this.year = tmp;
  }


  /**
   * Sets the year attribute of the ModelList object
   *
   * @param tmp The new year value
   */
  public void setYear(String tmp) {
    try {
      this.year = Integer.parseInt(tmp);
    } catch (Exception e) {
    }
  }


  /**
   * Gets the makeId attribute of the ModelList object
   *
   * @return The makeId value
   */
  public int getMakeId() {
    return makeId;
  }


  /**
   * Gets the year attribute of the ModelList object
   *
   * @return The year value
   */
  public int getYear() {
    return year;
  }


  /**
   * Gets the tableName attribute of the ModelList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ModelList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the object attribute of the ModelList object
   *
   * @param rs Description of Parameter
   * @return The object value
   * @throws SQLException Description of Exception
   */
  public Model getObject(ResultSet rs) throws SQLException {
    Model thisModel = new Model(rs);
    return thisModel;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    while (rs.next()) {
      Model thisModel = new Model(rs);
      this.add(thisModel);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   * This method is required for synchronization, it allows for the resultset
   * to be streamed with lower overhead
   *
   * @param db  Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    int items = -1;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT model.model_id, model.make_id AS model_make_id, model.model_name, " +
        "model.entered, model.enteredby, " +
        "model.modified, model.modifiedby, " +
        "make.make_id, make.make_name, " +
        "make.entered AS make_entered, make.enteredby AS make_enteredby, " +
        "make.modified AS make_modified, make.modifiedby AS make_modifiedby " +
        "FROM autoguide_model model LEFT JOIN autoguide_make make ON model.make_id = make.make_id ");
    sql.append("WHERE model.model_id > -1 ");
    createFilter(sql);
    sql.append("ORDER BY model_name ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    items = prepareFilter(pst);
    return pst;
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND model.entered > ? ");
      }
      sqlFilter.append("AND model.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND model.modified > ? ");
      sqlFilter.append("AND model.entered < ? ");
      sqlFilter.append("AND model.modified < ? ");
    }
    if (makeId > -1) {
      sqlFilter.append("AND make.make_id = ? ");
      if (year > -1) {
        sqlFilter.append(
            "AND model.model_id IN (SELECT DISTINCT model_id FROM autoguide_vehicle WHERE year = ?) ");
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    if (makeId > -1) {
      pst.setInt(++i, makeId);
      if (year > -1) {
        pst.setInt(++i, year);
      }
    }
    return i;
  }
}

