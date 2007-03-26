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
package org.aspcfs.modules.service.sync.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    November 8, 2006
 */
public class SyncPackageList extends ArrayList {

  private static final long serialVersionUID = 7196449429345120465L;

  private PagedListInfo pagedListInfo = null;

  private int clientId = -1;
  private int statusId = -1;

  /**
   * @return the clientId
   */
  public int getClientId() {
    return clientId;
  }

  /**
   * @param clientId the clientId to set
   */
  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  /**
   * @param clientId the clientId to set
   */
  public void setClientId(String clientId) {
    this.clientId = Integer.parseInt(clientId);
  }

  /**
   * @return the statusId
   */
  public int getStatusId() {
    return statusId;
  }

  /**
   * @param statusId the statusId to set
   */
  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }

  /**
   * @param statusId the statusId to set
   */
  public void setStatusId(String statusId) {
    this.statusId = Integer.parseInt(statusId);
  }

  /**
   * Constructor for the SyncPackageList object
   */
  public SyncPackageList() {
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
        + " FROM sync_package sp " + " WHERE sp.package_id > -1 ");
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
      pagedListInfo.setDefaultSort("sp.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY sp.package_id");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append("sp.package_id, sp.client_id, "
        + "sp." + DatabaseUtils.addQuotes(db, "type") + ", "
        + "sp." + DatabaseUtils.addQuotes(db, "size") + ", "
        + "recipient, sp.last_anchor, sp.next_anchor, sp.status_id, sp.status_date, sp.package_file_id, sp.entered "
        + "FROM sync_package sp " + " WHERE sp.package_id > -1 ");

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
      SyncPackage thisPackage = new SyncPackage(rs);
      this.add(thisPackage);
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
    if (clientId > -1) {
      sqlFilter.append("AND client_id = ? ");
    }
    if (statusId > -1) {
      sqlFilter.append("AND status_id = ? ");
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
    if (clientId > -1) {
      pst.setInt(++i, clientId);
    }
    if (statusId > -1) {
      pst.setInt(++i, statusId);
    }
    return i;
  }

}
