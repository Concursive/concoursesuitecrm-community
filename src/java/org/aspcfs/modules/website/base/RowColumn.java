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
import org.aspcfs.modules.website.icelet.HtmlContentPortlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     kailash
 * @created    February 10, 2006
 * @version    $Id: Exp $
 */
public class RowColumn extends GenericBean {

  /**
   *  Description of the Field
   */
  public final static int INITIAL_POSITION = 0;
  private int id = -1;
  private int position = -1;
  private int width = -1;
  private int pageRowId = -1;
  private int iceletId = -1;
  private boolean enabled = true;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;

  private String iceletConfiguratorClass = null;

  private Icelet icelet = null;
  private boolean buildIcelet = false;

  private IceletPropertyMap iceletPropertyMap = null;
  private boolean buildIceletPropertyMap = false;
  private IceletPropertyMap defaultPropertyMap = null;

  private boolean buildSubRows = false;
  private PageRowList subRows = null;

  private boolean override = false;
  private int previousRowColumnId = -1;
  private int nextRowColumnId = -1;
  private int level = -1;
  private PageRow parentRow = null;
  /*
   *  This is used to avoid updating relative rowColumns when swapping
   *  the current column with the next super column.
   */
  private boolean swappingRowColumn = false;

  /*
   *  This is used to avoid switching single column row's column with its parent column
   */
  private boolean deletingFromList = false;


  /**
   *  Constructor for the RowColumn object
   */
  public RowColumn() { }


  /**
   *  Constructor for the RowColumn object
   *
   * @param  db                Description of the Parameter
   * @param  tmpRowColumnId    Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public RowColumn(Connection db, int tmpRowColumnId) throws SQLException {
    queryRecord(db, tmpRowColumnId);
  }


  /**
   *  Constructor for the RowColumn object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public RowColumn(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the RowColumn object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the RowColumn object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the position attribute of the RowColumn object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   *  Sets the position attribute of the RowColumn object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
  }


  /**
   *  Sets the width attribute of the RowColumn object
   *
   * @param  tmp  The new width value
   */
  public void setWidth(int tmp) {
    this.width = tmp;
  }


  /**
   *  Sets the width attribute of the RowColumn object
   *
   * @param  tmp  The new width value
   */
  public void setWidth(String tmp) {
    this.width = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pageRowId attribute of the RowColumn object
   *
   * @param  tmp  The new pageRowId value
   */
  public void setPageRowId(int tmp) {
    this.pageRowId = tmp;
  }


  /**
   *  Sets the pageRowId attribute of the RowColumn object
   *
   * @param  tmp  The new pageRowId value
   */
  public void setPageRowId(String tmp) {
    this.pageRowId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the iceletId attribute of the RowColumn object
   *
   * @param  tmp  The new iceletId value
   */
  public void setIceletId(int tmp) {
    this.iceletId = tmp;
  }


  /**
   *  Sets the iceletId attribute of the RowColumn object
   *
   * @param  tmp  The new iceletId value
   */
  public void setIceletId(String tmp) {
    this.iceletId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the RowColumn object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the RowColumn object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the RowColumn object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the RowColumn object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the RowColumn object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the RowColumn object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the RowColumn object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the RowColumn object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the RowColumn object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the RowColumn object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the iceletConfiguratorClass attribute of the RowColumn object
   *
   * @param  tmp  The new iceletConfiguratorClass value
   */
  public void setIceletConfiguratorClass(String tmp) {
    this.iceletConfiguratorClass = tmp;
  }


  /**
   *  Sets the icelet attribute of the RowColumn object
   *
   * @param  tmp  The new icelet value
   */
  public void setIcelet(Icelet tmp) {
    this.icelet = tmp;
  }


  /**
   *  Sets the buildIcelet attribute of the RowColumn object
   *
   * @param  tmp  The new buildIcelet value
   */
  public void setBuildIcelet(boolean tmp) {
    this.buildIcelet = tmp;
  }


  /**
   *  Sets the buildIcelet attribute of the RowColumn object
   *
   * @param  tmp  The new buildIcelet value
   */
  public void setBuildIcelet(String tmp) {
    this.buildIcelet = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the iceletPropertyMap attribute of the RowColumn object
   *
   * @param  tmp  The new iceletPropertyMap value
   */
  public void setIceletPropertyMap(IceletPropertyMap tmp) {
    this.iceletPropertyMap = tmp;
  }


  /**
   *  Sets the buildIceletPropertyMap attribute of the RowColumn object
   *
   * @param  tmp  The new buildIceletPropertyMap value
   */
  public void setBuildIceletPropertyMap(boolean tmp) {
    this.buildIceletPropertyMap = tmp;
  }


  /**
   *  Sets the buildIceletPropertyMap attribute of the RowColumn object
   *
   * @param  tmp  The new buildIceletPropertyMap value
   */
  public void setBuildIceletPropertyMap(String tmp) {
    this.buildIceletPropertyMap = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the override attribute of the RowColumn object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the RowColumn object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the RowColumn object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the position attribute of the RowColumn object
   *
   * @return    The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   *  Gets the width attribute of the RowColumn object
   *
   * @return    The width value
   */
  public int getWidth() {
    return width;
  }


  /**
   *  Gets the pageRowId attribute of the RowColumn object
   *
   * @return    The pageRowId value
   */
  public int getPageRowId() {
    return pageRowId;
  }


  /**
   *  Gets the iceletId attribute of the RowColumn object
   *
   * @return    The iceletId value
   */
  public int getIceletId() {
    return iceletId;
  }


  /**
   *  Gets the enabled attribute of the RowColumn object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the enteredBy attribute of the RowColumn object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the RowColumn object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the RowColumn object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the RowColumn object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the iceletConfiguratorClass attribute of the RowColumn object
   *
   * @return    The iceletConfiguratorClass value
   */
  public String getIceletConfiguratorClass() {
    return iceletConfiguratorClass;
  }


  /**
   *  Gets the icelet attribute of the RowColumn object
   *
   * @return    The icelet value
   */
  public Icelet getIcelet() {
    return icelet;
  }


  /**
   *  Gets the buildIcelet attribute of the RowColumn object
   *
   * @return    The buildIcelet value
   */
  public boolean getBuildIcelet() {
    return buildIcelet;
  }


  /**
   *  Gets the iceletPropertyMap attribute of the RowColumn object
   *
   * @return    The iceletPropertyMap value
   */
  public IceletPropertyMap getIceletPropertyMap() {
    return iceletPropertyMap;
  }


  /**
   *  Gets the buildIceletPropertyMap attribute of the RowColumn object
   *
   * @return    The buildIceletPropertyMap value
   */
  public boolean getBuildIceletPropertyMap() {
    return buildIceletPropertyMap;
  }


  /**
   *  Gets the override attribute of the RowColumn object
   *
   * @return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Gets the previousRowColumnId attribute of the RowColumn object
   *
   * @return    The previousRowColumnId value
   */
  public int getPreviousRowColumnId() {
    return previousRowColumnId;
  }


  /**
   *  Sets the previousRowColumnId attribute of the RowColumn object
   *
   * @param  tmp  The new previousRowColumnId value
   */
  public void setPreviousRowColumnId(int tmp) {
    this.previousRowColumnId = tmp;
  }


  /**
   *  Sets the previousRowColumnId attribute of the RowColumn object
   *
   * @param  tmp  The new previousRowColumnId value
   */
  public void setPreviousRowColumnId(String tmp) {
    this.previousRowColumnId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the nextRowColumnId attribute of the RowColumn object
   *
   * @return    The nextRowColumnId value
   */
  public int getNextRowColumnId() {
    return nextRowColumnId;
  }


  /**
   *  Sets the nextRowColumnId attribute of the RowColumn object
   *
   * @param  tmp  The new nextRowColumnId value
   */
  public void setNextRowColumnId(int tmp) {
    this.nextRowColumnId = tmp;
  }


  /**
   *  Sets the nextRowColumnId attribute of the RowColumn object
   *
   * @param  tmp  The new nextRowColumnId value
   */
  public void setNextRowColumnId(String tmp) {
    this.nextRowColumnId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the defaultPropertyMap attribute of the RowColumn object
   *
   * @return    The defaultPropertyMap value
   */
  public IceletPropertyMap getDefaultPropertyMap() {
    return defaultPropertyMap;
  }


  /**
   *  Sets the defaultPropertyMap attribute of the RowColumn object
   *
   * @param  tmp  The new defaultPropertyMap value
   */
  public void setDefaultPropertyMap(IceletPropertyMap tmp) {
    this.defaultPropertyMap = tmp;
  }


  /**
   *  Gets the buildSubRows attribute of the RowColumn object
   *
   * @return    The buildSubRows value
   */
  public boolean getBuildSubRows() {
    return buildSubRows;
  }


  /**
   *  Sets the buildSubRows attribute of the RowColumn object
   *
   * @param  tmp  The new buildSubRows value
   */
  public void setBuildSubRows(boolean tmp) {
    this.buildSubRows = tmp;
  }


  /**
   *  Sets the buildSubRows attribute of the RowColumn object
   *
   * @param  tmp  The new buildSubRows value
   */
  public void setBuildSubRows(String tmp) {
    this.buildSubRows = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the subRows attribute of the RowColumn object
   *
   * @return    The subRows value
   */
  public PageRowList getSubRows() {
    return subRows;
  }


  /**
   *  Sets the subRows attribute of the RowColumn object
   *
   * @param  tmp  The new subRows value
   */
  public void setSubRows(PageRowList tmp) {
    this.subRows = tmp;
  }


  /**
   *  Gets the level attribute of the RowColumn object
   *
   * @return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Sets the level attribute of the RowColumn object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the RowColumn object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Gets the parentRow attribute of the RowColumn object
   *
   * @return    The parentRow value
   */
  public PageRow getParentRow() {
    return parentRow;
  }


  /**
   *  Sets the parentRow attribute of the RowColumn object
   *
   * @param  tmp  The new parentRow value
   */
  public void setParentRow(PageRow tmp) {
    this.parentRow = tmp;
  }


  /**
   *  Gets the deletingFromList attribute of the RowColumn object
   *
   * @return    The deletingFromList value
   */
  public boolean getDeletingFromList() {
    return deletingFromList;
  }


  /**
   *  Sets the deletingFromList attribute of the RowColumn object
   *
   * @param  tmp  The new deletingFromList value
   */
  public void setDeletingFromList(boolean tmp) {
    this.deletingFromList = tmp;
  }


  /**
   *  Sets the deletingFromList attribute of the RowColumn object
   *
   * @param  tmp  The new deletingFromList value
   */
  public void setDeletingFromList(String tmp) {
    this.deletingFromList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the swappingRowColumn attribute of the RowColumn object
   *
   * @return    The swappingRowColumn value
   */
  public boolean getSwappingRowColumn() {
    return swappingRowColumn;
  }


  /**
   *  Sets the swappingRowColumn attribute of the RowColumn object
   *
   * @param  tmp  The new swappingRowColumn value
   */
  public void setSwappingRowColumn(boolean tmp) {
    this.swappingRowColumn = tmp;
  }


  /**
   *  Sets the swappingRowColumn attribute of the RowColumn object
   *
   * @param  tmp  The new swappingRowColumn value
   */
  public void setSwappingRowColumn(String tmp) {
    this.swappingRowColumn = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db              Description of the Parameter
   * @param  tmpRowColumnId  Description of the Parameter
   * @return                 Description of the Return Value
   * @throws  SQLException   Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpRowColumnId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM web_row_column " +
        " WHERE row_column_id = ? ");
    pst.setInt(1, tmpRowColumnId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    if (buildIcelet) {
      buildIcelet(db);
    }
    if (buildIceletPropertyMap) {
      buildIceletPropertyMap(db);
    }
    if (buildSubRows) {
      buildSubRows(db);
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

    id = DatabaseUtils.getNextSeq(db, "web_row_column_row_column_id_seq");

    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO web_row_column " +
        "(" + (id > -1 ? "row_column_id, " : "") +
        "column_position , " +
        "page_row_id , " +
        "icelet_id , " +
        "width , " +
        "enabled , " +
        "enteredby , " +
        "modifiedby ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, position);
    DatabaseUtils.setInt(pst, ++i, pageRowId);
    DatabaseUtils.setInt(pst, ++i, iceletId);
    DatabaseUtils.setInt(pst, ++i, width);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, modifiedBy);
//System.out.println(pst);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "web_row_column_row_column_id_seq", id);
    pst.close();
    if (!this.getSwappingRowColumn()) {
      updateRelatedRowColumns(db, true);
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
  public int update(Connection db) throws SQLException {
    int resultCount = -1;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE web_row_column " +
        "SET " +
        "column_position = ? , " +
        "width = ? , " +
        "page_row_id = ? , " +
        "icelet_id = ? , " +
        "enabled = ? ");

    if (!override) {
      sql.append(
          " , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE row_column_id = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, position);
    DatabaseUtils.setInt(pst, ++i, width);
    DatabaseUtils.setInt(pst, ++i, pageRowId);
    DatabaseUtils.setInt(pst, ++i, iceletId);
    pst.setBoolean(++i, enabled);
    if (!override) {
      pst.setInt(++i, modifiedBy);
    }
    pst.setInt(++i, id);
    if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, modified);
    }
//System.out.println(pst);
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
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
      //Delete the icelet properties for the column.
      iceletPropertyMap = new IceletPropertyMap();
      iceletPropertyMap.setIceletRowColumnId(this.getId());
      iceletPropertyMap.buildList(db);
      iceletPropertyMap.delete(db);
      iceletPropertyMap = null;

      //Delete the sub rows if they exist
      buildSubRows(db);
      if (subRows != null && subRows.size() > 0) {
        subRows.delete(db);
      }

      if (!this.getSwappingRowColumn()) {
        //Update the positions of the next columns
        updateRelatedRowColumns(db, false);
      }

      //Delete the column
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM web_row_column " +
          "WHERE row_column_id =  ? ");

      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      if (!this.getDeletingFromList()) {
        PageRow pageRow = new PageRow(db, this.getPageRowId());
        if (pageRow.getRowColumnId() != -1) {
          pageRow.fixBlankSubRow(db);
/*
          RowColumn rowColumn = new RowColumn();
          rowColumn.setBuildSubRows(true);
          rowColumn.queryRecord(db, pageRow.getRowColumnId());
          rowColumn.fixSingleCellSubRow(db);
*/
        }
      }
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
   *  Determine whether the rowColumn is part of an enabled page in an enabled
   *  tab of an active site.
   *
   * @param  db              Description of the Parameter
   * @param  tmpRowColumnId  Description of the Parameter
   * @return                 Description of the Return Value
   * @throws  SQLException   Description of the Exception
   */
  public static boolean isRowColumnViewable(Connection db, int tmpRowColumnId) throws SQLException {
    boolean canBeViewed = false;
    int numberOfRecords = 0;
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " select count(*) AS numberOfRecords" +
        " from web_site s, web_tab t, web_page_group pg, web_page p, " +
        " web_page_version pv, web_page_row pr, web_row_column rc " +
        " where rc.row_column_id = ? " +
        " and rc.enabled = ? " +
        " and pr.page_row_id = rc.page_row_id " +
        " and pr.enabled = ? " +
        " and pr.page_version_id = pv.page_version_id " +
        " and pv.page_id = p.active_page_version_id " +
        " and p.enabled = ? " +
        " and pg.page_group_id = p.page_group_id " +
        " and t.tab_id = pg.tab_id " +
        " and t.enabled = ? " +
        " and s.site_id = t.site_id " +
        " and s.enabled = ? "
        );
    int i = 0;
    pst.setInt(++i, tmpRowColumnId);
    pst.setBoolean(++i, true);
    pst.setBoolean(++i, true);
    pst.setBoolean(++i, true);
    pst.setBoolean(++i, true);
    pst.setBoolean(++i, true);
    rs = pst.executeQuery();
    if (rs.next()) {
      numberOfRecords = rs.getInt("numberOfRecords");
    }
    rs.close();
    pst.close();

    if (numberOfRecords != 0) {
      canBeViewed = true;
    }
    return canBeViewed;
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("row_column_id");
    position = rs.getInt("column_position");
    width = DatabaseUtils.getInt(rs, "width");
    pageRowId = DatabaseUtils.getInt(rs, "page_row_id");
    iceletId = DatabaseUtils.getInt(rs, "icelet_id");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildIcelet(Connection db) throws SQLException {
    if (iceletId != -1) {
      icelet = new Icelet(db, iceletId);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildIceletPropertyMap(Connection db) throws SQLException {
    iceletPropertyMap = new IceletPropertyMap();
    iceletPropertyMap.setIceletRowColumnId(id);
    iceletPropertyMap.setBuildNames(true);
    iceletPropertyMap.setDefaultProperties(this.getDefaultPropertyMap());
    iceletPropertyMap.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildSubRows(Connection db) throws SQLException {
    subRows = new PageRowList();
    subRows.setRowColumnId(this.getId());
    subRows.setBuildColumns(true);
    subRows.setBuildIcelet(this.getBuildIcelet());
    subRows.setBuildIceletPropertyMap(this.getBuildIceletPropertyMap());
    subRows.setBuildSubRows(this.getBuildSubRows());
    subRows.buildList(db);
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
      throw new SQLException("Column ID not specified");
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
  public int computeRowColumnPosition(Connection db) throws SQLException {
    int result = RowColumn.INITIAL_POSITION;
    RowColumn otherRowColumn = new RowColumn();
    if (previousRowColumnId != -1) {
      otherRowColumn.queryRecord(db, previousRowColumnId);
      result = otherRowColumn.getPosition() + 1;
    } else if (nextRowColumnId != -1) {
      otherRowColumn.queryRecord(db, nextRowColumnId);
      result = otherRowColumn.getPosition();
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
  public void updateRelatedRowColumns(Connection db, boolean add) throws SQLException {
    if (previousRowColumnId > -1) {
      RowColumnList.updateRelatedRowColumns(db, this.getId(), previousRowColumnId, this.getPageRowId(), false, add);
    } else if (nextRowColumnId > -1) {
      RowColumnList.updateRelatedRowColumns(db, this.getId(), nextRowColumnId, this.getPageRowId(), true, add);
    } else if (!add) {
      RowColumnList.updateRelatedRowColumns(db, this.getId(), -1, this.getPageRowId(), false, add);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  moveLeft          Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void move(Connection db, boolean moveLeft) throws SQLException {
    RowColumn switchRowColumn = null;
    RowColumnList columns = new RowColumnList();
    columns.setPageRowId(this.getPageRowId());
    columns.setPosition((moveLeft ? this.getPosition() - 1 : this.getPosition() + 1));
    columns.buildList(db);
    if (columns.size() > 0) {
      switchRowColumn = (RowColumn) columns.get(0);
      int switchRowColumnPosition = this.getPosition();
      this.setPosition(switchRowColumn.getPosition());
      this.update(db);
      switchRowColumn.setPosition(switchRowColumnPosition);
      switchRowColumn.update(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  oldSelection      Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void parsePropertyMapEntries(Connection db, RowColumn oldSelection) throws SQLException {
//    if (oldSelection.getIceletId() != this.getIceletId() && oldSelection.getIceletId() > -1) {
    if (oldSelection.getIceletId() > -1) {
      //Delete all old icelet properties.
      if (oldSelection.getIceletPropertyMap() != null) {
        oldSelection.getIceletPropertyMap().delete(db);
      }
    }
//    if (this.getIceletId() != oldSelection.getIceletId() && this.getIceletId() > -1) {
    if (this.getIceletId() > -1) {
      //Insert all new icelet properties.
      this.getIceletPropertyMap().insert(db);
//TODO::      Icelet update() method is blank for now. Hence this part is commented.
//    } else if (this.getIceletId() == oldSelection.getIceletId() && this.getIceletId() > -1) {
//      this.getIceletPropertyMap().updateEntries(db, oldSelection.getIceletPropertyMap());
    }
  }


  /**
   *  Adds a sub row and RowColumn
   *
   * @param  db                The feature to be added to the SubRow attribute
   * @exception  SQLException  Description of the Exception
   */
  public void addSubRow(Connection db) throws SQLException {
    PageRow pageRow = new PageRow();
    if (subRows.size() == 0) {
      //Create the new row column to replace the existing row column.
      RowColumn rowColumn = new RowColumn();
      rowColumn.setPageRowId(this.getPageRowId());
      rowColumn.setModifiedBy(this.getModifiedBy());
      rowColumn.setPosition(this.getPosition());
      rowColumn.setWidth(this.getWidth());
      rowColumn.setEnabled(this.getEnabled());
      rowColumn.setSwappingRowColumn(true);
      rowColumn.insert(db);

      //Insert the first page_row
      PageRow pageRow1 = new PageRow();
      pageRow1.setRowColumnId(rowColumn.getId());
      pageRow1.setPosition(PageRow.INITIAL_POSITION);
      pageRow1.setEnabled(true);
      pageRow1.setModifiedBy(this.getModifiedBy());
      pageRow1.insert(db);

      //Shift the current column to the new page_row
      this.setPosition(RowColumn.INITIAL_POSITION);
      this.setPageRowId(pageRow1.getId());
      this.update(db);

      //Insert the second page_row
      pageRow.setRowColumnId(rowColumn.getId());
      pageRow.setPosition(PageRow.INITIAL_POSITION + 1);
      pageRow.setEnabled(true);
      pageRow.setModifiedBy(this.getModifiedBy());
      pageRow.insert(db);
    } else {
      //Build the list of page rows for the column
      PageRowList pageRowList = new PageRowList();
      pageRowList.setRowColumnId(this.getId());
      pageRowList.setBuildLastPosition(true);
      pageRowList.buildList(db);

      //Insert the last page_row
      pageRow.setRowColumnId(this.getId());
      pageRow.setPosition((pageRowList.getLastPosition() != 0 ? pageRowList.getLastPosition() + 1 : pageRowList.getLastPosition()));
      pageRow.setEnabled(true);
      pageRow.setModifiedBy(this.getModifiedBy());
      pageRow.insert(db);
    }

    //Build the icelet from the database
    Icelet htmlIcelet = null;
    IceletList iceletList = new IceletList();
    iceletList.setConfiguratorClass("HtmlContentPortlet");
    iceletList.buildList(db);
    if (iceletList.size() > 0) {
      htmlIcelet = (Icelet) iceletList.get(0);
    }
    //Insert the row_column
    RowColumn rowColumn = new RowColumn();
    rowColumn.setPageRowId(pageRow.getId());
    rowColumn.setModifiedBy(this.getModifiedBy());
    rowColumn.setEnabled(true);
    rowColumn.setWidth(50);
    rowColumn.setPosition(RowColumn.INITIAL_POSITION);
    if (htmlIcelet != null && htmlIcelet.getId() > -1) {
      rowColumn.setIceletId(htmlIcelet.getId());
    }
    rowColumn.insert(db);
    //Insert the icelet.
    if (htmlIcelet != null && htmlIcelet.getId() > -1) {
      IceletProperty property = new IceletProperty();
      property.setRowColumnId(rowColumn.getId());
      property.setValue("Please enter your html text here");
      property.setTypeConstant(HtmlContentPortlet.PROPERTY_HTMLTEXT);
      property.setModifiedBy(this.getModifiedBy());
      property.insert(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  map  Description of the Parameter
   */
  public void buildSubColumns(HashMap map) {
    if (subRows != null && subRows.size() > 0) {
      Iterator rowIter = subRows.iterator();
      while (rowIter.hasNext()) {
        PageRow subRow = (PageRow) rowIter.next();
        if (subRow.getRowColumnList() != null && subRow.getRowColumnList().size() > 0) {
          Iterator columnIter = (Iterator) subRow.getRowColumnList().iterator();
          while (columnIter.hasNext()) {
            RowColumn subColumn = (RowColumn) columnIter.next();
            map.put(String.valueOf(subColumn.getId()), subColumn);
            subColumn.buildSubColumns(map);
          }
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void fixSingleCellSubRow(Connection db) throws SQLException {
    if (subRows != null && subRows.size() == 1) {
      PageRow subRow = (PageRow) this.getSubRows().get(0);
      if (subRow.getRowColumnList().size() == 1) {
        RowColumn subColumn = (RowColumn) subRow.getRowColumnList().get(0);
        //Switch the current Row Column with the subColumn.
        subColumn.setPageRowId(this.getPageRowId());
        subColumn.setWidth(this.getWidth());
        subColumn.setEnabled(this.getEnabled());
        subColumn.setPosition(this.getPosition());
        subColumn.setModifiedBy(this.getModifiedBy());
        subColumn.update(db);

        //Delete the current column and its sub row
        this.setSwappingRowColumn(true);
        this.delete(db);
      }
    }
  }
}

