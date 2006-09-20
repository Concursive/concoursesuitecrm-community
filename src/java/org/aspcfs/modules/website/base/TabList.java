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
public class TabList extends ArrayList {

  private PagedListInfo pagedListInfo = null;

  private int id = -1;
  private int siteId = -1;
  private int enabled = Constants.UNDEFINED;
  private int afterPosition = -1;
  private int beforePosition = -1;
  private int position = -1;


  /**
   *  Constructor for the SiteLogList object
   */
  public TabList() { }


  /**
   *  Sets the pagedListInfo attribute of the TabList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the TabList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TabList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the siteId attribute of the TabList object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the TabList object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the TabList object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the TabList object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the TabList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the TabList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the siteId attribute of the TabList object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Gets the enabled attribute of the TabList object
   *
   * @return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the afterPosition attribute of the TabList object
   *
   * @return    The afterPosition value
   */
  public int getAfterPosition() {
    return afterPosition;
  }


  /**
   *  Sets the afterPosition attribute of the TabList object
   *
   * @param  tmp  The new afterPosition value
   */
  public void setAfterPosition(int tmp) {
    this.afterPosition = tmp;
  }


  /**
   *  Sets the afterPosition attribute of the TabList object
   *
   * @param  tmp  The new afterPosition value
   */
  public void setAfterPosition(String tmp) {
    this.afterPosition = Integer.parseInt(tmp);
  }


  /**
   *  Gets the beforePosition attribute of the TabList object
   *
   * @return    The beforePosition value
   */
  public int getBeforePosition() {
    return beforePosition;
  }


  /**
   *  Sets the beforePosition attribute of the TabList object
   *
   * @param  tmp  The new beforePosition value
   */
  public void setBeforePosition(int tmp) {
    this.beforePosition = tmp;
  }


  /**
   *  Sets the beforePosition attribute of the TabList object
   *
   * @param  tmp  The new beforePosition value
   */
  public void setBeforePosition(String tmp) {
    this.beforePosition = Integer.parseInt(tmp);
  }


  /**
   *  Gets the position attribute of the TabList object
   *
   * @return    The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   *  Sets the position attribute of the TabList object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   *  Sets the position attribute of the TabList object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
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
      Tab thisTab = this.getObject(rs);
      this.add(thisTab);
    }
    rs.close();
    if (pst != null) {
      pst.close();
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
        "FROM web_tab " +
        "WHERE tab_id > -1 ");

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
      pagedListInfo.setDefaultSort("tab_position", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY tab_position ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "wt.* " +
        "FROM web_tab wt " +
        "WHERE tab_id > -1 ");
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
    if (id != -1) {
      sqlFilter.append("AND tab_id = ? ");
    }
    if (siteId != -1) {
      sqlFilter.append("AND site_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
    }
    if (afterPosition > -1) {
      sqlFilter.append("AND tab_position > ? ");
    }
    if (beforePosition > -1) {
      sqlFilter.append("AND tab_position < ? ");
    }
    if (position > -1) {
      sqlFilter.append("AND tab_position = ? ");
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
    if (siteId != -1) {
      pst.setInt(++i, siteId);
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
    if (position > -1) {
      pst.setInt(++i, position);
    }
    return i;
  }


  /**
   *  Gets the object attribute of the TabList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public Tab getObject(ResultSet rs) throws SQLException {
    return new Tab(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator tabIterator = this.iterator();
    while (tabIterator.hasNext()) {
      Tab thisTab = (Tab) tabIterator.next();
      thisTab.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  tmpSiteId         Description of the Parameter
   * @param  mode              Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static int queryDefault(Connection db, int tmpSiteId, int mode) throws SQLException {
    int tabId = -1;
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT tab_id " +
        " FROM web_tab " +
        " WHERE site_id = ? " +
        ((mode == Site.PORTAL_MODE) ? "AND enabled = ? " : "") +
        " ORDER BY tab_position ");
    int i = 0;
    pst.setInt(++i, tmpSiteId);
    if (mode == Site.PORTAL_MODE) {
      pst.setBoolean(++i, true);
    }
    rs = pst.executeQuery();
    if (rs.next()) {
      tabId = rs.getInt("tab_id");
    }
    rs.close();
    pst.close();
    return tabId;
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
  public static void updateRelatedTabs(Connection db, int currentId, int adjacentId, int siteId, boolean changeAdjacentPosition, boolean addition) throws SQLException {
    if (addition) {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE web_tab " +
          "SET tab_position = tab_position + 1 " +
          "WHERE tab_id <> ? " +
          "AND site_id = ? " +
          "AND " + (changeAdjacentPosition ? "tab_position >= " : "tab_position > ") +
          "(SELECT tab_position FROM web_tab WHERE tab_id = ?) ");
      pst.setInt(1, currentId);
      pst.setInt(2, siteId);
      pst.setInt(3, adjacentId);
      pst.executeUpdate();
      pst.close();
    } else {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE web_tab " +
          "SET tab_position = tab_position - 1 " +
          "WHERE site_id = ? " +
          "AND tab_position > " +
          "(SELECT tab_position FROM web_tab WHERE tab_id = ?) ");
      pst.setInt(1, siteId);
      pst.setInt(2, currentId);
      pst.executeUpdate();
      pst.close();
    }
  }

  public static boolean queryEnabled(Connection db, int tabId) throws SQLException {
    boolean enabled = false;
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT enabled " +
        " FROM web_tab " +
        " WHERE tab_id = ? " +
        " AND enabled = ? ");
    int i = 0;
    pst.setInt(++i, tabId);
    pst.setBoolean(++i, true);
    rs = pst.executeQuery();
    if (rs.next()) {
      enabled = true;
    }
    rs.close();
    pst.close();
    return enabled;
  }
}

