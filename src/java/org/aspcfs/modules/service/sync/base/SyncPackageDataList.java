/*
 *  Copyright(c) 2006 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.modules.service.sync.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

/**
 * Description of the Class
 * 
 * @author Ananth
 * @version
 * @created November 8, 2006
 */
public class SyncPackageDataList extends ArrayList {

  private static final long serialVersionUID = -6018771513134244774L;

  private PagedListInfo pagedListInfo = null;

  private int packageId = -1;
  private int tableId = -1;

  /**
   * @return the packageId
   */
  public int getPackageId() {
    return packageId;
  }

  /**
   * @param packageId
   *          the packageId to set
   */
  public void setPackageId(int packageId) {
    this.packageId = packageId;
  }

  /**
   * @param packageId
   *          the packageId to set
   */
  public void setPackageId(String packageId) {
    this.packageId = Integer.parseInt(packageId);
  }

  /**
   * @return the tableId
   */
  public int getTableId() {
    return tableId;
  }

  /**
   * @param tableId
   *          the tableId to set
   */
  public void setTableId(int tableId) {
    this.tableId = tableId;
  }

  /**
   * @param tableId
   *          the tableId to set
   */
  public void setTableId(String tableId) {
    this.tableId = Integer.parseInt(tableId);
  }

  /**
   * Constructor for the SyncPackageDataList object
   */
  public SyncPackageDataList(){
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
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Build a base SQL statement for counting records
    sqlCount.append(" SELECT COUNT(*) AS recordcount "
        + " FROM sync_package_data spd " + " WHERE spd.data_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
        prepareFilter(pst);
        //pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      //Determine column to sort by
      pagedListInfo.setDefaultSort("spd.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY spd.data_id");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append("spd.data_id, spd.package_id, spd.table_id, "
        + "spd." + DatabaseUtils.addQuotes(db, "action") + ", "
        + "spd.identity_start, "
        + "spd." + DatabaseUtils.addQuotes(db, "offset") + ", "
        + "spd." + DatabaseUtils.addQuotes(db, "items") + ", "
        + "spd.last_anchor, spd.next_anchor, spd.entered "
        + "FROM sync_package_data spd " 
        + "WHERE spd.data_id > -1 ");

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString()
        + sqlOrder.toString());
    prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      SyncPackageData packageData = new SyncPackageData(rs);
      this.add(packageData);
    }
    rs.close();
    pst.close();
  }

  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (this.getPackageId() > -1) {
      sqlFilter.append("AND package_id = ? ");
    }
    if (this.getTableId() > -1) {
      sqlFilter.append("AND table_id = ? ");
    }
  }

  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (this.getPackageId() > -1) {
      pst.setInt(++i, this.getPackageId());
    }
    if (this.getTableId() > -1) {
      pst.setInt(++i, this.getTableId());
    }
    return i;
  }
}
