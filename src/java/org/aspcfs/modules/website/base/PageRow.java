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

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.DependencyList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 * @author     kailash
 * @created    February 10, 2006 $Id: Exp $
 * @version    $Id: Exp $
 */
public class PageRow extends GenericBean {

  /**
   *  Description of the Field
   */
  public final static int INITIAL_POSITION = 0;
  private int id = -1;
  private int position = -1;
  private int pageVersionId = -1;
  private int rowColumnId = -1;
  private boolean enabled = true;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;

  private boolean buildRowColumnList = false;
  private RowColumnList rowColumnList = null;
  private boolean override = false;
  private int previousPageRowId = -1;
  private int nextPageRowId = -1;
  private int totalColumnWidth = 0;
  private int level = -1;
  /*
   *  This is used to avoid switching single column row's column with the parent column
   */
  private boolean deletingFromList = false;


  /**
   *  Constructor for the PageRow object
   */
  public PageRow() { }


  /**
   *  Constructor for the PageRow object
   *
   * @param  db                Description of the Parameter
   * @param  tmpPageRowId      Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public PageRow(Connection db, int tmpPageRowId) throws SQLException {
    queryRecord(db, tmpPageRowId);
  }


  /**
   *  Constructor for the PageRow object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public PageRow(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the PageRow object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PageRow object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the position attribute of the PageRow object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   *  Sets the position attribute of the PageRow object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pageVersionId attribute of the PageRow object
   *
   * @param  tmp  The new pageVersionId value
   */
  public void setPageVersionId(int tmp) {
    this.pageVersionId = tmp;
  }


  /**
   *  Sets the pageVersionId attribute of the PageRow object
   *
   * @param  tmp  The new pageVersionId value
   */
  public void setPageVersionId(String tmp) {
    this.pageVersionId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the rowColumnId attribute of the PageRow object
   *
   * @return    The rowColumnId value
   */
  public int getRowColumnId() {
    return rowColumnId;
  }


  /**
   *  Sets the rowColumnId attribute of the PageRow object
   *
   * @param  tmp  The new rowColumnId value
   */
  public void setRowColumnId(int tmp) {
    this.rowColumnId = tmp;
  }


  /**
   *  Sets the rowColumnId attribute of the PageRow object
   *
   * @param  tmp  The new rowColumnId value
   */
  public void setRowColumnId(String tmp) {
    this.rowColumnId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the PageRow object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the PageRow object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the PageRow object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the PageRow object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the PageRow object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the PageRow object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the PageRow object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the PageRow object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the PageRow object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the PageRow object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the buildRowColumnList attribute of the PageRow object
   *
   * @param  tmp  The new buildRowColumnList value
   */
  public void setBuildRowColumnList(boolean tmp) {
    this.buildRowColumnList = tmp;
  }


  /**
   *  Sets the buildRowColumnList attribute of the PageRow object
   *
   * @param  tmp  The new buildRowColumnList value
   */
  public void setBuildRowColumnList(String tmp) {
    this.buildRowColumnList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the rowColumnList attribute of the PageRow object
   *
   * @param  tmp  The new rowColumnList value
   */
  public void setRowColumnList(RowColumnList tmp) {
    this.rowColumnList = tmp;
  }


  /**
   *  Sets the override attribute of the PageRow object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the PageRow object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the PageRow object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the position attribute of the PageRow object
   *
   * @return    The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   *  Gets the pageVersionId attribute of the PageRow object
   *
   * @return    The pageVersionId value
   */
  public int getPageVersionId() {
    return pageVersionId;
  }


  /**
   *  Gets the enabled attribute of the PageRow object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the enteredBy attribute of the PageRow object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the PageRow object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the PageRow object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the PageRow object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the buildRowColumnList attribute of the PageRow object
   *
   * @return    The buildRowColumnList value
   */
  public boolean getBuildRowColumnList() {
    return buildRowColumnList;
  }



  /**
   *  Gets the rowColumnList attribute of the PageRow object
   *
   * @return    The rowColumnList value
   */
  public RowColumnList getRowColumnList() {
    return rowColumnList;
  }


  /**
   *  Gets the override attribute of the PageRow object
   *
   * @return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Gets the previousPageRowId attribute of the PagePageRow object
   *
   * @return    The previousPageRowId value
   */
  public int getPreviousPageRowId() {
    return previousPageRowId;
  }


  /**
   *  Sets the previousPageRowId attribute of the PagePageRow object
   *
   * @param  tmp  The new previousPageRowId value
   */
  public void setPreviousPageRowId(int tmp) {
    this.previousPageRowId = tmp;
  }


  /**
   *  Sets the previousPageRowId attribute of the PagePageRow object
   *
   * @param  tmp  The new previousPageRowId value
   */
  public void setPreviousPageRowId(String tmp) {
    this.previousPageRowId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the nextPageRowId attribute of the PagePageRow object
   *
   * @return    The nextPageRowId value
   */
  public int getNextPageRowId() {
    return nextPageRowId;
  }


  /**
   *  Sets the nextPageRowId attribute of the PagePageRow object
   *
   * @param  tmp  The new nextPageRowId value
   */
  public void setNextPageRowId(int tmp) {
    this.nextPageRowId = tmp;
  }


  /**
   *  Sets the nextPageRowId attribute of the PagePageRow object
   *
   * @param  tmp  The new nextPageRowId value
   */
  public void setNextPageRowId(String tmp) {
    this.nextPageRowId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the totalColumnWidth attribute of the PageRow object
   *
   * @return    The totalColumnWidth value
   */
  public int getTotalColumnWidth() {
    return totalColumnWidth;
  }


  /**
   *  Sets the totalColumnWidth attribute of the PageRow object
   *
   * @param  tmp  The new totalColumnWidth value
   */
  public void setTotalColumnWidth(int tmp) {
    this.totalColumnWidth = tmp;
  }


  /**
   *  Sets the totalColumnWidth attribute of the PageRow object
   *
   * @param  tmp  The new totalColumnWidth value
   */
  public void setTotalColumnWidth(String tmp) {
    this.totalColumnWidth = Integer.parseInt(tmp);
  }


  /**
   *  Gets the level attribute of the PageRow object
   *
   * @return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Sets the level attribute of the PageRow object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the PageRow object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Gets the deletingFromList attribute of the PageRow object
   *
   * @return    The deletingFromList value
   */
  public boolean getDeletingFromList() {
    return deletingFromList;
  }


  /**
   *  Sets the deletingFromList attribute of the PageRow object
   *
   * @param  tmp  The new deletingFromList value
   */
  public void setDeletingFromList(boolean tmp) {
    this.deletingFromList = tmp;
  }


  /**
   *  Sets the deletingFromList attribute of the PageRow object
   *
   * @param  tmp  The new deletingFromList value
   */
  public void setDeletingFromList(String tmp) {
    this.deletingFromList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  tmpPageRowId   Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpPageRowId) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM web_page_row " +
        " WHERE page_row_id = ? ");
    pst.setInt(1, tmpPageRowId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Page Row record not found");
    }
    if (buildRowColumnList) {
      buildRowColumnList(db);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {

    id = DatabaseUtils.getNextSeq(db, "web_page_row_page_row_id_seq");

    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO web_page_row " +
        "(" + (id > -1 ? "page_row_id, " : " ") +
        "row_position , " +
        "page_version_id , " +
        "row_column_id , " +
        "enabled , " +
        "enteredby , " +
        "modifiedby ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, position);
    DatabaseUtils.setInt(pst, ++i, pageVersionId);
    DatabaseUtils.setInt(pst, ++i, rowColumnId);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "web_page_row_page_row_id_seq", id);
    pst.close();
    updateRelatedPageRows(db, true);
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean update(Connection db) throws SQLException {

    int resultCount = -1;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE web_page_row " +
        "SET " +
        "row_position = ? , " +
        "page_version_id = ? , " +
        "row_column_id = ? , " +
        "enabled = ?  ");

    if (!override) {
      sql.append(
          " , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE page_row_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, position);
    DatabaseUtils.setInt(pst, ++i, pageVersionId);
    DatabaseUtils.setInt(pst, ++i, rowColumnId);
    pst.setBoolean(++i, enabled);
    if (!override) {
      pst.setInt(++i, modifiedBy);
    }
    pst.setInt(++i, id);
    if (!override) {
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {

    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }

      //Delete the columns in the current row
      rowColumnList = new RowColumnList();
      rowColumnList.setPageRowId(this.getId());
      rowColumnList.setBuildSubRows(true);
      rowColumnList.setBuildIceletPropertyMap(true);
      rowColumnList.buildList(db);
      rowColumnList.delete(db);
      rowColumnList = null;

      //Update the positions of the rows from the next position
      updateRelatedPageRows(db, false);

      //Delete the current row
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM web_page_row " +
          "WHERE page_row_id =  ? ");

      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

/*
      //If the current row is a subRow, fix the Column.
      if (this.getRowColumnId() > -1 && !this.getDeletingFromList()) {
        RowColumn rowColumn = new RowColumn();
        rowColumn.setBuildSubRows(true);
        rowColumn.queryRecord(db, this.getRowColumnId());
        rowColumn.fixSingleCellSubRow(db);
      }
*/
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("page_row_id");
    position = rs.getInt("row_position");
    pageVersionId = DatabaseUtils.getInt(rs, "page_version_id");
    enabled = rs.getBoolean("enabled");

    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    rowColumnId = DatabaseUtils.getInt(rs, "row_column_id");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRowColumnList(Connection db) throws SQLException {
    rowColumnList = new RowColumnList();
    rowColumnList.setPageRowId(this.getId());
    rowColumnList.setBuildIcelet(true);
    rowColumnList.setBuildIceletPropertyMap(true);
    rowColumnList.setBuildSubRows(true);
    rowColumnList.buildList(db);
    this.setTotalColumnWidth(rowColumnList.getTotalColumnWidth());
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Row ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    /*
     *  / Check for this site's dependencies
     *  try {
     *  i = 0;
     *  pst = db.prepareStatement(
     *  "SELECT count(*) as parentcount " +
     *  "FROM quote_entry " +
     *  "WHERE parent_id = ? ");
     *  pst.setInt(++i, this.getId());
     *  rs = pst.executeQuery();
     *  if (rs.next()) {
     *  int parentCount = rs.getInt("parentcount");
     *  if (parentCount != 0) {
     *  Dependency thisDependency = new Dependency();
     *  thisDependency.setName("numberOfParentsOfThisQuote");
     *  thisDependency.setCount(parentCount);
     *  thisDependency.setCanDelete(true);
     *  dependencyList.add(thisDependency);
     *  }
     *  }
     *  rs.close();
     *  pst.close();
     *  } catch (SQLException e) {
     *  throw new SQLException(e.getMessage());
     *  }
     */
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int computePageRowPosition(Connection db) throws SQLException {
    int result = PageRow.INITIAL_POSITION;
    PageRow otherPageRow = new PageRow();
    if (previousPageRowId != -1) {
      otherPageRow.queryRecord(db, previousPageRowId);
      result = otherPageRow.getPosition() + 1;
    } else if (nextPageRowId != -1) {
      otherPageRow.queryRecord(db, nextPageRowId);
      result = otherPageRow.getPosition();
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  add               Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void updateRelatedPageRows(Connection db, boolean add) throws SQLException {
    if (previousPageRowId > -1) {
      PageRowList.updateRelatedPageRows(db, this.getId(), previousPageRowId, this.getPageVersionId(), this.getRowColumnId(), false, add);
    } else if (nextPageRowId > -1) {
      PageRowList.updateRelatedPageRows(db, this.getId(), nextPageRowId, this.getPageVersionId(), this.getRowColumnId(), true, add);
    } else if (!add) {
      PageRowList.updateRelatedPageRows(db, this.getId(), -1, this.getPageVersionId(), this.getRowColumnId(), false, add);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  moveUp            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void move(Connection db, boolean moveUp) throws SQLException {
    PageRow switchPageRow = null;
    PageRowList rows = new PageRowList();
    rows.setPageVersionId(this.getPageVersionId());
    rows.setPosition((moveUp ? this.getPosition() - 1 : this.getPosition() + 1));
    rows.buildList(db);
    if (rows.size() > 0) {
      switchPageRow = (PageRow) rows.get(0);
      int switchPageRowPosition = this.getPosition();
      this.setPosition(switchPageRow.getPosition());
      this.update(db);
      switchPageRow.setPosition(switchPageRowPosition);
      switchPageRow.update(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void fixBlankSubRow(Connection db) throws SQLException {
    if (this.getRowColumnId() > -1) {
      this.buildRowColumnList(db);
      if (this.getRowColumnList() == null || this.getRowColumnList().size() == 0) {
        this.delete(db);
      }
    }
  }
}

