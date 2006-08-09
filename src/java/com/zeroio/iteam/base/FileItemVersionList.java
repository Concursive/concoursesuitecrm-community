/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: FileItemVersionList.java,v 1.3.130.1 2004/03/19 21:00:50
 *          rvasista Exp $
 * @created January 15, 2003
 */
public class FileItemVersionList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int itemId = -1;
  private int owner = -1;
  private String ownerIdRange = null;
  private java.sql.Timestamp enteredRangeStart = null;
  private java.sql.Timestamp enteredRangeEnd = null;
  private int buildPortalRecords = Constants.UNDEFINED;

  /**
   * Constructor for the FileItemVersionList object
   */
  public FileItemVersionList() {
  }


  /**
   * Sets the pagedListInfo attribute of the FileItemList object
   *
   * @param pagedListInfo The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   * Sets the itemId attribute of the FileItemVersionList object
   *
   * @param tmp The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   * Gets the itemId attribute of the FileItemVersionList object
   *
   * @return The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   * Sets the owner attribute of the FileItemList object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the ownerIdRange attribute of the FileItemList object
   *
   * @param tmp The new ownerIdRange value
   */
  public void setOwnerIdRange(String tmp) {
    this.ownerIdRange = tmp;
  }


  /**
   * Sets the enteredRangeStart attribute of the FileItemVersionList object
   *
   * @param tmp The new enteredRangeStart value
   */
  public void setEnteredRangeStart(java.sql.Timestamp tmp) {
    this.enteredRangeStart = tmp;
  }


  /**
   * Sets the enteredRangeEnd attribute of the FileItemVersionList object
   *
   * @param tmp The new enteredRangeEnd value
   */
  public void setEnteredRangeEnd(java.sql.Timestamp tmp) {
    this.enteredRangeEnd = tmp;
  }


  /**
   * Gets the pagedListInfo attribute of the FileItemList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the owner attribute of the FileItemList object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Gets the ownerIdRange attribute of the FileItemList object
   *
   * @return The ownerIdRange value
   */
  public String getOwnerIdRange() {
    return ownerIdRange;
  }


  /**
	 * @return Returns the buildPortalRecords.
	 */
	public int getBuildPortalRecords() {
		return buildPortalRecords;
	}


	/**
	 * @param buildPortalRecords The buildPortalRecords to set.
	 */
	public void setBuildPortalRecords(int buildPortalRecords) {
		this.buildPortalRecords = buildPortalRecords;
	}


	/**
   * Generates a list of matching FileItems
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
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
        "SELECT COUNT(*) AS recordcount " +
        "FROM project_files_version v " +
        "WHERE v.item_id > -1 ");

    createFilter(sqlFilter);

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

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
            sqlFilter.toString() +
            "AND " + DatabaseUtils.toLowerCase(db) + "(subject) < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      pagedListInfo.setDefaultSort("version", "desc");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY \"version\" DESC ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "v.* " +
        "FROM project_files_version v " +
        "WHERE v.item_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      FileItemVersion thisItem = new FileItemVersion(rs);
      this.add(thisItem);
    }
    rs.close();
    pst.close();
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

    if (itemId > -1) {
      sqlFilter.append("AND item_id = ? ");
    }

    if (owner != -1) {
      sqlFilter.append("AND enteredby = ? ");
    }

    if (ownerIdRange != null) {
      sqlFilter.append("AND enteredby IN (" + ownerIdRange + ") ");
    }
    if (enteredRangeStart != null) {
      sqlFilter.append("AND entered >= ? ");
    }
    if (enteredRangeEnd != null) {
      sqlFilter.append("AND entered <= ? ");
    }
    if (buildPortalRecords != Constants.UNDEFINED) {
      sqlFilter.append("AND allow_portal_access = ? ");
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
    if (itemId > -1) {
      pst.setInt(++i, itemId);
    }
    if (owner != -1) {
      pst.setInt(++i, owner);
    }
    if (enteredRangeStart != null) {
      pst.setTimestamp(++i, enteredRangeStart);
    }
    if (enteredRangeEnd != null) {
      pst.setTimestamp(++i, enteredRangeEnd);
    }
    if (buildPortalRecords != Constants.UNDEFINED) {
      pst.setBoolean(++i, buildPortalRecords == Constants.TRUE);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int queryRecordCount(Connection db) throws SQLException {
    int recordCount = 0;
    StringBuffer sqlFilter = new StringBuffer();
    String sqlCount =
        "SELECT COUNT(*) AS recordcount " +
        "FROM project_files_version v " +
        "WHERE v.item_id > -1 ";
    createFilter(sqlFilter);
    PreparedStatement pst = db.prepareStatement(
        sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = DatabaseUtils.getInt(rs, "recordcount", 0);
    }
    rs.close();
    pst.close();
    return recordCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public long queryFileSize(Connection db) throws SQLException {
    long recordSize = 0;
    StringBuffer sqlFilter = new StringBuffer();
    String sqlCount =
        "SELECT SUM(\"size\") AS recordsize " +
        "FROM project_files_version v " +
        "WHERE v.item_id > -1 ";
    createFilter(sqlFilter);
    PreparedStatement pst = db.prepareStatement(
        sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordSize = DatabaseUtils.getLong(rs, "recordsize", 0);
    }
    rs.close();
    pst.close();
    return recordSize;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param ownerId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static long queryOwnerSize(Connection db, int ownerId) throws SQLException {
    long recordSize = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT SUM(\"size\") AS recordsize " +
        "FROM project_files_version v " +
        "WHERE v.item_id > -1 " +
        "AND v.enteredby = ? ");
    pst.setInt(1, ownerId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordSize = DatabaseUtils.getLong(rs, "recordsize", 0);
    }
    rs.close();
    pst.close();
    return recordSize;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int queryDownloadCount(Connection db) throws SQLException {
    int downloadCount = 0;
    StringBuffer sqlFilter = new StringBuffer();
    String sqlCount =
        "SELECT SUM(downloads) AS downloadcount " +
        "FROM project_files_version v " +
        "WHERE v.item_id > -1 ";
    createFilter(sqlFilter);
    PreparedStatement pst = db.prepareStatement(
        sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      downloadCount = DatabaseUtils.getInt(rs, "downloadcount", 0);
    }
    rs.close();
    pst.close();
    return downloadCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int queryDownloadSize(Connection db) throws SQLException {
    int downloadSize = 0;
    StringBuffer sqlFilter = new StringBuffer();
    String sqlCount =
        "SELECT SUM(downloads * \"size\") AS downloadsize " +
        "FROM project_files_version v " +
        "WHERE v.item_id > -1 ";
    createFilter(sqlFilter);
    PreparedStatement pst = db.prepareStatement(
        sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      downloadSize = DatabaseUtils.getInt(rs, "downloadsize", 0);
    }
    rs.close();
    pst.close();
    return downloadSize;
  }
}

