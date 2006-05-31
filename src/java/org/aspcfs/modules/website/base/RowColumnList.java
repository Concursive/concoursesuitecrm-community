/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     kailash
 * @created    February 10, 2006 $Id: Exp $
 * @version    $Id: Exp $
 */
public class RowColumnList extends ArrayList {

  private PagedListInfo pagedListInfo = null;

  private int id = -1;
  private int pageRowId = -1;
  private int iceletId = -1;
  private int enabled = Constants.UNDEFINED;

  private boolean buildIcelet = false;
  private boolean buildIceletPropertyMap = false;
  private boolean buildSubRows = false;
  private int ignoreRowColumnId = -1;
  private int lastPosition = RowColumn.INITIAL_POSITION;
  private int lastPositionColumnId = -1;
  private boolean buildLastPosition = false;
  private int afterPosition = -1;
  private int beforePosition = -1;
  private int position = -1;


  /**
   *  Constructor for the RowColumnList object
   */
  public RowColumnList() { }


  /**
   *  Sets the pagedListInfo attribute of the RowColumnList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the RowColumnList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the RowColumnList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pageRowId attribute of the RowColumnList object
   *
   * @param  tmp  The new pageRowId value
   */
  public void setPageRowId(int tmp) {
    this.pageRowId = tmp;
  }


  /**
   *  Sets the pageRowId attribute of the RowColumnList object
   *
   * @param  tmp  The new pageRowId value
   */
  public void setPageRowId(String tmp) {
    this.pageRowId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the iceletId attribute of the RowColumnList object
   *
   * @param  tmp  The new iceletId value
   */
  public void setIceletId(int tmp) {
    this.iceletId = tmp;
  }


  /**
   *  Sets the iceletId attribute of the RowColumnList object
   *
   * @param  tmp  The new iceletId value
   */
  public void setIceletId(String tmp) {
    this.iceletId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the RowColumnList object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the RowColumnList object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildIcelet attribute of the RowColumnList object
   *
   * @param  tmp  The new buildIcelet value
   */
  public void setBuildIcelet(boolean tmp) {
    this.buildIcelet = tmp;
  }


  /**
   *  Sets the buildIcelet attribute of the RowColumnList object
   *
   * @param  tmp  The new buildIcelet value
   */
  public void setBuildIcelet(String tmp) {
    this.buildIcelet = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the buildIceletPropertyMap attribute of the RowColumnList object
   *
   * @param  tmp  The new buildIceletPropertyMap value
   */
  public void setBuildIceletPropertyMap(boolean tmp) {
    this.buildIceletPropertyMap = tmp;
  }


  /**
   *  Sets the buildIceletPropertyMap attribute of the RowColumnList object
   *
   * @param  tmp  The new buildIceletPropertyMap value
   */
  public void setBuildIceletPropertyMap(String tmp) {
    this.buildIceletPropertyMap = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the ignoreRowColumnId attribute of the RowColumnList object
   *
   * @param  tmp  The new ignoreRowColumnId value
   */
  public void setIgnoreRowColumnId(int tmp) {
    this.ignoreRowColumnId = tmp;
  }


  /**
   *  Sets the ignoreRowColumnId attribute of the RowColumnList object
   *
   * @param  tmp  The new ignoreRowColumnId value
   */
  public void setIgnoreRowColumnId(String tmp) {
    this.ignoreRowColumnId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the RowColumnList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the RowColumnList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the pageRowId attribute of the RowColumnList object
   *
   * @return    The pageRowId value
   */
  public int getPageRowId() {
    return pageRowId;
  }


  /**
   *  Gets the iceletId attribute of the RowColumnList object
   *
   * @return    The iceletId value
   */
  public int getIceletId() {
    return iceletId;
  }


  /**
   *  Gets the enabled attribute of the RowColumnList object
   *
   * @return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the buildIcelet attribute of the RowColumnList object
   *
   * @return    The buildIcelet value
   */
  public boolean getBuildIcelet() {
    return buildIcelet;
  }


  /**
   *  Gets the buildIceletPropertyMap attribute of the RowColumnList object
   *
   * @return    The buildIceletPropertyMap value
   */
  public boolean getBuildIceletPropertyMap() {
    return buildIceletPropertyMap;
  }


  /**
   *  Gets the ignoreRowColumnId attribute of the RowColumnList object
   *
   * @return    The ignoreRowColumnId value
   */
  public int getIgnoreRowColumnId() {
    return ignoreRowColumnId;
  }


  /**
   *  Gets the lastPosition attribute of the RowColumnList object
   *
   * @return    The lastPosition value
   */
  public int getLastPosition() {
    return lastPosition;
  }


  /**
   *  Sets the lastPosition attribute of the RowColumnList object
   *
   * @param  tmp  The new lastPosition value
   */
  public void setLastPosition(int tmp) {
    this.lastPosition = tmp;
  }


  /**
   *  Sets the lastPosition attribute of the RowColumnList object
   *
   * @param  tmp  The new lastPosition value
   */
  public void setLastPosition(String tmp) {
    this.lastPosition = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildLastPosition attribute of the RowColumnList object
   *
   * @return    The buildLastPosition value
   */
  public boolean getBuildLastPosition() {
    return buildLastPosition;
  }


  /**
   *  Sets the buildLastPosition attribute of the RowColumnList object
   *
   * @param  tmp  The new buildLastPosition value
   */
  public void setBuildLastPosition(boolean tmp) {
    this.buildLastPosition = tmp;
  }


  /**
   *  Sets the buildLastPosition attribute of the RowColumnList object
   *
   * @param  tmp  The new buildLastPosition value
   */
  public void setBuildLastPosition(String tmp) {
    this.buildLastPosition = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the afterPosition attribute of the RowColumnList object
   *
   * @return    The afterPosition value
   */
  public int getAfterPosition() {
    return afterPosition;
  }


  /**
   *  Sets the afterPosition attribute of the RowColumnList object
   *
   * @param  tmp  The new afterPosition value
   */
  public void setAfterPosition(int tmp) {
    this.afterPosition = tmp;
  }


  /**
   *  Sets the afterPosition attribute of the RowColumnList object
   *
   * @param  tmp  The new afterPosition value
   */
  public void setAfterPosition(String tmp) {
    this.afterPosition = Integer.parseInt(tmp);
  }


  /**
   *  Gets the beforePosition attribute of the RowColumnList object
   *
   * @return    The beforePosition value
   */
  public int getBeforePosition() {
    return beforePosition;
  }


  /**
   *  Sets the beforePosition attribute of the RowColumnList object
   *
   * @param  tmp  The new beforePosition value
   */
  public void setBeforePosition(int tmp) {
    this.beforePosition = tmp;
  }


  /**
   *  Sets the beforePosition attribute of the RowColumnList object
   *
   * @param  tmp  The new beforePosition value
   */
  public void setBeforePosition(String tmp) {
    this.beforePosition = Integer.parseInt(tmp);
  }


  /**
   *  Gets the position attribute of the RowColumnList object
   *
   * @return    The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   *  Sets the position attribute of the RowColumnList object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   *  Sets the position attribute of the RowColumnList object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
  }


  /**
   *  Gets the lastPositionColumnId attribute of the RowColumnList object
   *
   * @return    The lastPositionColumnId value
   */
  public int getLastPositionColumnId() {
    return lastPositionColumnId;
  }


  /**
   *  Sets the lastPositionColumnId attribute of the RowColumnList object
   *
   * @param  tmp  The new lastPositionColumnId value
   */
  public void setLastPositionColumnId(int tmp) {
    this.lastPositionColumnId = tmp;
  }


  /**
   *  Sets the lastPositionColumnId attribute of the RowColumnList object
   *
   * @param  tmp  The new lastPositionColumnId value
   */
  public void setLastPositionColumnId(String tmp) {
    this.lastPositionColumnId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildSubRows attribute of the RowColumnList object
   *
   * @return    The buildSubRows value
   */
  public boolean getBuildSubRows() {
    return buildSubRows;
  }


  /**
   *  Sets the buildSubRows attribute of the RowColumnList object
   *
   * @param  tmp  The new buildSubRows value
   */
  public void setBuildSubRows(boolean tmp) {
    this.buildSubRows = tmp;
  }


  /**
   *  Sets the buildSubRows attribute of the RowColumnList object
   *
   * @param  tmp  The new buildSubRows value
   */
  public void setBuildSubRows(String tmp) {
    this.buildSubRows = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      RowColumn thisRowColumn = this.getObject(rs);
      this.add(thisRowColumn);
      if (buildLastPosition) {
        lastPosition = (thisRowColumn.getPosition() > this.getLastPosition() ? thisRowColumn.getPosition() : lastPosition);
        lastPositionColumnId = (lastPosition == thisRowColumn.getPosition() ? thisRowColumn.getId() : lastPositionColumnId);
      }
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }

    if (buildIceletPropertyMap || buildIcelet || buildSubRows) {
      Iterator rowColumnIterator = this.iterator();
      while (rowColumnIterator.hasNext()) {
        RowColumn thisRowColumn = (RowColumn) rowColumnIterator.next();
        if (buildIceletPropertyMap) {
          thisRowColumn.buildIceletPropertyMap(db);
        }
        if (buildIcelet) {
          thisRowColumn.buildIcelet(db);
        }
        if (buildSubRows) {
          thisRowColumn.buildSubRows(db);
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {

    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM row_column " +
        "WHERE row_column_id > -1 ");

    createFilter(sqlFilter, db);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine column to sort by
      pagedListInfo.setDefaultSort("column_position", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY column_position ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " * " +
        "FROM row_column " +
        "WHERE row_column_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    return rs;
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter      Description of the Parameter
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
    if (id != -1) {
      sqlFilter.append("AND row_column_id = ? ");
    }
    if (pageRowId != -1) {
      sqlFilter.append("AND page_row_id = ? ");
    }
    if (iceletId != -1) {
      sqlFilter.append("AND icelet_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
    }
    if (ignoreRowColumnId != -1) {
      sqlFilter.append("AND row_column_id <> ? ");
    }
    if (afterPosition > -1) {
      sqlFilter.append("AND column_position > ? ");
    }
    if (beforePosition > -1) {
      sqlFilter.append("AND column_position < ? ");
    }
    if (position > -1) {
      sqlFilter.append("AND column_position = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id != -1) {
      pst.setInt(++i, id);
    }
    if (pageRowId != -1) {
      pst.setInt(++i, pageRowId);
    }
    if (iceletId != -1) {
      pst.setInt(++i, iceletId);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, (enabled == Constants.TRUE));
    }
    if (ignoreRowColumnId != -1) {
      pst.setInt(++i, ignoreRowColumnId);
    }
    if (afterPosition > -1) {
      pst.setInt(++i, afterPosition);
    }
    if (beforePosition > -1) {
      pst.setInt(++i, beforePosition);
    }
    if (position > -1) {
      pst.setInt(++i, position);
    }

    return i;
  }


  /**
   *  Gets the object attribute of the RowColumnList object
   *
   * @param  rs             Description of the Parameter
   * @return                The object value
   * @throws  SQLException  Description of the Exception
   */
  public RowColumn getObject(ResultSet rs) throws SQLException {
    return new RowColumn(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator rowColumnIterator = this.iterator();
    while (rowColumnIterator.hasNext()) {
      RowColumn thisRowColumn = (RowColumn) rowColumnIterator.next();
      thisRowColumn.setDeletingFromList(true);
      thisRowColumn.delete(db);
    }
  }



  /**
   *  Description of the Method
   *
   * @param  db                      Description of the Parameter
   * @param  currentId               Description of the Parameter
   * @param  adjacentId              Description of the Parameter
   * @param  changeAdjacentPosition  Description of the Parameter
   * @param  addition                Description of the Parameter
   * @exception  SQLException        Description of the Exception
   */
  public static void updateRelatedRowColumns(Connection db, int currentId, int adjacentId, int pageRowId, boolean changeAdjacentPosition, boolean addition) throws SQLException {
    if (addition) {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE row_column " +
          "SET column_position = column_position + 1 " +
          "WHERE row_column_id <> ? " +
          "AND page_row_id = ? " +
          "AND " + (changeAdjacentPosition ? "column_position >= " : "column_position > ") +
          "(SELECT column_position FROM row_column WHERE row_column_id = ?) ");
      pst.setInt(1, currentId);
      pst.setInt(2, pageRowId);
      pst.setInt(3, adjacentId);
      pst.executeUpdate();
      pst.close();
    } else {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE row_column " +
          "SET column_position = column_position - 1 " +
          "WHERE page_row_id = ? " +
          "AND column_position > " +
          "(SELECT column_position FROM row_column WHERE row_column_id = ?) ");
      pst.setInt(1, pageRowId);
      pst.setInt(2, currentId);
      pst.executeUpdate();
      pst.close();
    }
  }


  /**
   *  Gets the totalColumnSize attribute of the RowColumnList object
   *
   * @return    The totalColumnSize value
   */
  public int getTotalColumnWidth() {
    int result = 0;
    Iterator rowColumnIterator = this.iterator();
    while (rowColumnIterator.hasNext()) {
      RowColumn thisRowColumn = (RowColumn) rowColumnIterator.next();
      result += thisRowColumn.getWidth();
    }
    return result;
  }
}

