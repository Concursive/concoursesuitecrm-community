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

/**
 *  Description of the Class
 *
 * @author     kailash
 * @created    February 10, 2006 $Id: Exp $
 * @version    $Id: Exp $
 */
public class PageRowList extends ArrayList {

  private PagedListInfo pagedListInfo = null;

  private int id = -1;
  private int position = -1;
  private int pageVersionId = -1;
  private int rowColumnId = -1;
  private int enabled = Constants.UNDEFINED;

  private boolean buildColumns = false;
  private int mode = -1;
  private int afterPosition = -1;
  private int beforePosition = -1;
  private int maxColumns = 1;
  private int lastPosition = PageRow.INITIAL_POSITION;
  private int lastPositionPageRowId = -1;
  private boolean buildLastPosition = false;

  private boolean buildIcelet = true;
  private boolean buildIceletPropertyMap = true;
  private boolean buildSubRows = true;


  /**
   *  Constructor for the PageRowList object
   */
  public PageRowList() { }


  /**
   *  Sets the pagedListInfo attribute of the PageRowList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the PageRowList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PageRowList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the position attribute of the PageRowList object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   *  Sets the position attribute of the PageRowList object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pageVersionId attribute of the PageRowList object
   *
   * @param  tmp  The new pageVersionId value
   */
  public void setPageVersionId(int tmp) {
    this.pageVersionId = tmp;
  }


  /**
   *  Sets the pageVersionId attribute of the PageRowList object
   *
   * @param  tmp  The new pageVersionId value
   */
  public void setPageVersionId(String tmp) {
    this.pageVersionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the PageRowList object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the PageRowList object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildColumns attribute of the PageRowList object
   *
   * @param  tmp  The new buildColumns value
   */
  public void setBuildColumns(boolean tmp) {
    this.buildColumns = tmp;
  }


  /**
   *  Sets the buildColumns attribute of the PageRowList object
   *
   * @param  tmp  The new buildColumns value
   */
  public void setBuildColumns(String tmp) {
    this.buildColumns = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the mode attribute of the PageRowList object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(int tmp) {
    this.mode = tmp;
    if (this.mode == Site.PORTAL_MODE) {
      this.setEnabled(Constants.TRUE);
    }
  }


  /**
   *  Sets the mode attribute of the PageRowList object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(String tmp) {
    this.mode = Integer.parseInt(tmp);
    if (this.mode == Site.PORTAL_MODE) {
      this.setEnabled(Constants.TRUE);
    }
  }


  /**
   *  Gets the pagedListInfo attribute of the PageRowList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the PageRowList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the position attribute of the PageRowList object
   *
   * @return    The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   *  Gets the pageVersionId attribute of the PageRowList object
   *
   * @return    The pageVersionId value
   */
  public int getPageVersionId() {
    return pageVersionId;
  }


  /**
   *  Gets the enabled attribute of the PageRowList object
   *
   * @return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the buildColumns attribute of the PageRowList object
   *
   * @return    The buildColumns value
   */
  public boolean getBuildColumns() {
    return buildColumns;
  }


  /**
   *  Gets the mode attribute of the PageRowList object
   *
   * @return    The mode value
   */
  public int getMode() {
    return mode;
  }


  /**
   *  Gets the afterPosition attribute of the PageRowList object
   *
   * @return    The afterPosition value
   */
  public int getAfterPosition() {
    return afterPosition;
  }


  /**
   *  Sets the afterPosition attribute of the PageRowList object
   *
   * @param  tmp  The new afterPosition value
   */
  public void setAfterPosition(int tmp) {
    this.afterPosition = tmp;
  }


  /**
   *  Sets the afterPosition attribute of the PageRowList object
   *
   * @param  tmp  The new afterPosition value
   */
  public void setAfterPosition(String tmp) {
    this.afterPosition = Integer.parseInt(tmp);
  }


  /**
   *  Gets the beforePosition attribute of the PageRowList object
   *
   * @return    The beforePosition value
   */
  public int getBeforePosition() {
    return beforePosition;
  }


  /**
   *  Sets the beforePosition attribute of the PageRowList object
   *
   * @param  tmp  The new beforePosition value
   */
  public void setBeforePosition(int tmp) {
    this.beforePosition = tmp;
  }


  /**
   *  Sets the beforePosition attribute of the PageRowList object
   *
   * @param  tmp  The new beforePosition value
   */
  public void setBeforePosition(String tmp) {
    this.beforePosition = Integer.parseInt(tmp);
  }


  /**
   *  Gets the maxColumns attribute of the PageRowList object
   *
   * @return    The maxColumns value
   */
  public int getMaxColumns() {
    return maxColumns;
  }


  /**
   *  Sets the maxColumns attribute of the PageRowList object
   *
   * @param  tmp  The new maxColumns value
   */
  public void setMaxColumns(int tmp) {
    this.maxColumns = tmp;
  }


  /**
   *  Sets the maxColumns attribute of the PageRowList object
   *
   * @param  tmp  The new maxColumns value
   */
  public void setMaxColumns(String tmp) {
    this.maxColumns = Integer.parseInt(tmp);
  }


  /**
   *  Gets the rowColumnId attribute of the PageRowList object
   *
   * @return    The rowColumnId value
   */
  public int getRowColumnId() {
    return rowColumnId;
  }


  /**
   *  Sets the rowColumnId attribute of the PageRowList object
   *
   * @param  tmp  The new rowColumnId value
   */
  public void setRowColumnId(int tmp) {
    this.rowColumnId = tmp;
  }


  /**
   *  Sets the rowColumnId attribute of the PageRowList object
   *
   * @param  tmp  The new rowColumnId value
   */
  public void setRowColumnId(String tmp) {
    this.rowColumnId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the lastPosition attribute of the PageRowList object
   *
   * @return    The lastPosition value
   */
  public int getLastPosition() {
    return lastPosition;
  }


  /**
   *  Sets the lastPosition attribute of the PageRowList object
   *
   * @param  tmp  The new lastPosition value
   */
  public void setLastPosition(int tmp) {
    this.lastPosition = tmp;
  }


  /**
   *  Sets the lastPosition attribute of the PageRowList object
   *
   * @param  tmp  The new lastPosition value
   */
  public void setLastPosition(String tmp) {
    this.lastPosition = Integer.parseInt(tmp);
  }


  /**
   *  Gets the lastPositionPageRowId attribute of the PageRowList object
   *
   * @return    The lastPositionPageRowId value
   */
  public int getLastPositionPageRowId() {
    return lastPositionPageRowId;
  }


  /**
   *  Sets the lastPositionPageRowId attribute of the PageRowList object
   *
   * @param  tmp  The new lastPositionPageRowId value
   */
  public void setLastPositionPageRowId(int tmp) {
    this.lastPositionPageRowId = tmp;
  }


  /**
   *  Sets the lastPositionPageRowId attribute of the PageRowList object
   *
   * @param  tmp  The new lastPositionPageRowId value
   */
  public void setLastPositionPageRowId(String tmp) {
    this.lastPositionPageRowId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildLastPosition attribute of the PageRowList object
   *
   * @return    The buildLastPosition value
   */
  public boolean getBuildLastPosition() {
    return buildLastPosition;
  }


  /**
   *  Sets the buildLastPosition attribute of the PageRowList object
   *
   * @param  tmp  The new buildLastPosition value
   */
  public void setBuildLastPosition(boolean tmp) {
    this.buildLastPosition = tmp;
  }


  /**
   *  Sets the buildLastPosition attribute of the PageRowList object
   *
   * @param  tmp  The new buildLastPosition value
   */
  public void setBuildLastPosition(String tmp) {
    this.buildLastPosition = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildIcelet attribute of the PageRowList object
   *
   * @return    The buildIcelet value
   */
  public boolean getBuildIcelet() {
    return buildIcelet;
  }


  /**
   *  Sets the buildIcelet attribute of the PageRowList object
   *
   * @param  tmp  The new buildIcelet value
   */
  public void setBuildIcelet(boolean tmp) {
    this.buildIcelet = tmp;
  }


  /**
   *  Sets the buildIcelet attribute of the PageRowList object
   *
   * @param  tmp  The new buildIcelet value
   */
  public void setBuildIcelet(String tmp) {
    this.buildIcelet = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildIceletPropertyMap attribute of the PageRowList object
   *
   * @return    The buildIceletPropertyMap value
   */
  public boolean getBuildIceletPropertyMap() {
    return buildIceletPropertyMap;
  }


  /**
   *  Sets the buildIceletPropertyMap attribute of the PageRowList object
   *
   * @param  tmp  The new buildIceletPropertyMap value
   */
  public void setBuildIceletPropertyMap(boolean tmp) {
    this.buildIceletPropertyMap = tmp;
  }


  /**
   *  Sets the buildIceletPropertyMap attribute of the PageRowList object
   *
   * @param  tmp  The new buildIceletPropertyMap value
   */
  public void setBuildIceletPropertyMap(String tmp) {
    this.buildIceletPropertyMap = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildSubRows attribute of the PageRowList object
   *
   * @return    The buildSubRows value
   */
  public boolean getBuildSubRows() {
    return buildSubRows;
  }


  /**
   *  Sets the buildSubRows attribute of the PageRowList object
   *
   * @param  tmp  The new buildSubRows value
   */
  public void setBuildSubRows(boolean tmp) {
    this.buildSubRows = tmp;
  }


  /**
   *  Sets the buildSubRows attribute of the PageRowList object
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
      PageRow thisPageRow = this.getObject(rs);
      this.add(thisPageRow);
      if (buildLastPosition) {
        lastPosition = (thisPageRow.getPosition() > this.getLastPosition() ? thisPageRow.getPosition() : lastPosition);
        lastPositionPageRowId = (lastPosition == thisPageRow.getPosition() ? thisPageRow.getId() : lastPositionPageRowId);
      }
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    if (buildColumns) {
      Iterator pageRowIterator = this.iterator();
      while (pageRowIterator.hasNext()) {
        PageRow thisPageRow = (PageRow) pageRowIterator.next();
        thisPageRow.setBuildIcelet(this.getBuildIcelet());
        thisPageRow.setBuildIceletPropertyMap(this.getBuildIceletPropertyMap());
        thisPageRow.setBuildSubRows(this.getBuildSubRows());
        thisPageRow.buildRowColumnList(db);
        maxColumns = (thisPageRow.getRowColumnList().size() > maxColumns ? thisPageRow.getRowColumnList().size() : maxColumns);
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
        "FROM web_page_row " +
        "WHERE page_row_id > -1 ");

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
      pagedListInfo.setDefaultSort("row_position", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY row_position ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "wpr.* " +
        "FROM web_page_row wpr " +
        "WHERE page_row_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
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
    if (id > -1) {
      sqlFilter.append("AND page_row_id = ? ");
    }
    if (position > -1) {
      sqlFilter.append("AND row_position = ? ");
    }
    if (pageVersionId > -1) {
      sqlFilter.append("AND page_version_id = ? ");
    } else if (rowColumnId > -1) {
      sqlFilter.append("AND row_column_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
    }
    if (afterPosition > -1) {
      sqlFilter.append("AND row_position > ? ");
    }
    if (beforePosition > -1) {
      sqlFilter.append("AND row_position < ? ");
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
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (position > -1) {
      pst.setInt(++i, position);
    }
    if (pageVersionId > -1) {
      pst.setInt(++i, pageVersionId);
    } else if (rowColumnId > -1) {
      pst.setInt(++i, rowColumnId);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, (enabled == Constants.TRUE));
    }
    if (afterPosition > -1) {
      pst.setInt(++i, afterPosition);
    }
    if (beforePosition > -1) {
      pst.setInt(++i, beforePosition);
    }
    return i;
  }


  /**
   *  Gets the object attribute of the PageRowList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public PageRow getObject(ResultSet rs) throws SQLException {
    return new PageRow(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator pageRowIterator = this.iterator();
    while (pageRowIterator.hasNext()) {
      PageRow thisPageRow = (PageRow) pageRowIterator.next();
      thisPageRow.setDeletingFromList(true);
      thisPageRow.delete(db);
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
   * @param  pageVersionId           Description of the Parameter
   * @param  rowColumnId             Description of the Parameter
   * @exception  SQLException        Description of the Exception
   */
  public static void updateRelatedPageRows(Connection db, int currentId, int adjacentId, int pageVersionId, int rowColumnId, boolean changeAdjacentPosition, boolean addition) throws SQLException {
    if (addition) {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE web_page_row " +
          "SET row_position = row_position + 1 " +
          "WHERE page_row_id <> ? " +
          "AND " + (pageVersionId == -1 ? " row_column_id = ? " : "page_version_id = ? ") +
          "AND " + (changeAdjacentPosition ? "row_position >= " : "row_position > ") +
          "(SELECT row_position FROM web_page_row WHERE page_row_id = ?) ");
      pst.setInt(1, currentId);
      pst.setInt(2, (pageVersionId == -1 ? rowColumnId : pageVersionId));
      pst.setInt(3, adjacentId);
      pst.executeUpdate();
      pst.close();
    } else {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE web_page_row " +
          "SET row_position = row_position - 1 " +
          "WHERE " + (pageVersionId == -1 ? " row_column_id = ? " : "page_version_id = ? ") +
          "AND row_position > " +
          "(SELECT row_position FROM web_page_row WHERE page_row_id = ?) ");
      pst.setInt(1, (pageVersionId == -1 ? rowColumnId : pageVersionId));
      pst.setInt(2, currentId);
      pst.executeUpdate();
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rowsColumns  Description of the Parameter
   * @param  level        Description of the Parameter
   */
  public void buildRowsColumns(ArrayList rowsColumns, int level) {
    Iterator pageRowIterator = this.iterator();
    while (pageRowIterator.hasNext()) {
      PageRow thisPageRow = (PageRow) pageRowIterator.next();
      thisPageRow.setLevel(level);
      rowsColumns.add(thisPageRow);
      Iterator iter = thisPageRow.getRowColumnList().iterator();
      while (iter.hasNext()) {
        RowColumn thisRowColumn = (RowColumn) iter.next();
        thisRowColumn.setLevel(level);
        thisRowColumn.setParentRow(thisPageRow);
        rowsColumns.add(thisRowColumn);
        if (thisRowColumn.getSubRows() != null && thisRowColumn.getSubRows().size() > 0) {
          thisRowColumn.getSubRows().buildRowsColumns(rowsColumns, level + 1);
        }
      }
    }
  }
}

