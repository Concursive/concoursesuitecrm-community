/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.system.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Builds a list of gatekeeper Site objects
 *
 * @author matt rajkowski
 * @version $Id$
 * @created May 13, 2003
 */
public class SiteList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int enabled = Constants.UNDEFINED;
  private String siteCode = null;
  private String virtualHost = null;
  private String databaseName = null;
  private String dbHost = null;

  /**
   * Constructor for the SiteList object
   */
  public SiteList() {
  }


  /**
   * Sets the pagedListInfo attribute of the SiteList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the pagedListInfo attribute of the SiteList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the enabled attribute of the SiteList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the SiteList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Gets the enabled attribute of the SiteList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Sets the siteCode attribute of the SiteList object
   *
   * @param tmp The new siteCode value
   */
  public void setSiteCode(String tmp) {
    this.siteCode = tmp;
  }


  /**
   * Gets the siteCode attribute of the SiteList object
   *
   * @return The siteCode value
   */
  public String getSiteCode() {
    return siteCode;
  }


  /**
   * Sets the virtualHost attribute of the SiteList object
   *
   * @param tmp The new virtualHost value
   */
  public void setVirtualHost(String tmp) {
    this.virtualHost = tmp;
  }


  /**
   * Gets the virtualHost attribute of the SiteList object
   *
   * @return The virtualHost value
   */
  public String getVirtualHost() {
    return virtualHost;
  }

  public String getDbHost() {
    return dbHost;
  }

  public void setDbHost(String dbHost) {
    this.dbHost = dbHost;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  /**
   * Builds a list of sites based on the specified parameters, requires a
   * Gatekeeper Connection
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
        "SELECT COUNT(*) AS recordcount " +
        "FROM sites s " +
        "WHERE s.site_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(
          sqlCount.toString() +
          sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine column to sort by
      pagedListInfo.setDefaultSort("site_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY site_id ");
    }
    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT * " +
        "FROM sites s " +
        "WHERE s.site_id > -1 ");
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
    while (rs.next()) {
      Site thisSite = new Site(rs);
      this.add(thisSite);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
    }
    if (siteCode != null) {
      sqlFilter.append("AND sitecode = ? ");
    }
    if (virtualHost != null) {
      sqlFilter.append("AND vhost = ? ");
    }
    if (dbHost != null) {
      sqlFilter.append("AND dbhost = ? ");
    }
    if (databaseName != null) {
      sqlFilter.append("AND dbname = ? ");
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
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, enabled == Constants.TRUE);
    }
    if (siteCode != null) {
      pst.setString(++i, siteCode);
    }
    if (virtualHost != null) {
      pst.setString(++i, virtualHost);
    }
    if (dbHost != null) {
      pst.setString(++i, dbHost);
    }
    if (databaseName != null) {
      pst.setString(++i, databaseName);
    }
    return i;
  }

}
