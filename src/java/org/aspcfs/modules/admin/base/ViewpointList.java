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
package org.aspcfs.modules.admin.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id: ViewpointList.java,v 1.4 2004/09/16 19:24:00 mrajkowski Exp
 *          $
 * @created Februagetry 24, 2003
 */
public class ViewpointList extends ArrayList {
  private int enteredBy = -1;
  private int userId = -1;
  private int vpUserId = -1;
  private boolean includeEnabledOnly = false;
  private PagedListInfo pagedListInfo = null;
  private boolean buildResources = false;


  /**
   * Sets the enteredBy attribute of the ViewpointList object
   *
   * @param enteredBy The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   * Sets the userId attribute of the ViewpointList object
   *
   * @param userId The new userId value
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }


  /**
   * Sets the vpUserId attribute of the ViewpointList object
   *
   * @param vpUserId The new vpUserId value
   */
  public void setVpUserId(int vpUserId) {
    this.vpUserId = vpUserId;
  }


  /**
   * Sets the pagedListInfo attribute of the ViewpointList object
   *
   * @param pagedListInfo The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   * Sets the buildResources attribute of the ViewpointList object
   *
   * @param buildResources The new buildResources value
   */
  public void setBuildResources(boolean buildResources) {
    this.buildResources = buildResources;
  }


  /**
   * Sets the includeEnabledOnly attribute of the ViewpointList object
   *
   * @param includeEnabledOnly The new includeEnabledOnly value
   */
  public void setIncludeEnabledOnly(boolean includeEnabledOnly) {
    this.includeEnabledOnly = includeEnabledOnly;
  }


  /**
   * Gets the includeEnabledOnly attribute of the ViewpointList object
   *
   * @return The includeEnabledOnly value
   */
  public boolean getIncludeEnabledOnly() {
    return includeEnabledOnly;
  }


  /**
   * Gets the buildResources attribute of the ViewpointList object
   *
   * @return The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   * Gets the enteredBy attribute of the ViewpointList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the userId attribute of the ViewpointList object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Gets the vpUserId attribute of the ViewpointList object
   *
   * @return The vpUserId value
   */
  public int getVpUserId() {
    return vpUserId;
  }


  /**
   * Gets the pagedListInfo attribute of the ViewpointList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
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
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM viewpoint vp " +
        "LEFT JOIN contact c ON c.user_id = vp.vp_user_id " +
        "WHERE vp.viewpoint_id > -1 ");

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
            "AND (" + DatabaseUtils.toLowerCase(db) + "(c.namelast) < ? AND c.namelast IS NOT NULL) ");
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
      pagedListInfo.setDefaultSort("c.namelast", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY c.namelast ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "vp.viewpoint_id, vp.user_id, vp.vp_user_id, " +
        "vp.entered, vp.enteredby, vp.modified, vp.modifiedby, vp.enabled " +
        "FROM viewpoint vp " +
        "LEFT JOIN contact c ON c.user_id = vp.vp_user_id " +
        "WHERE vp.viewpoint_id > -1 ");
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
      Viewpoint thisViewpoint = new Viewpoint(rs);
      this.add(thisViewpoint);
    }
    rs.close();
    pst.close();

    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        Viewpoint thisViewpoint = (Viewpoint) i.next();
        thisViewpoint.buildVpUserDetails(db);
        thisViewpoint.buildPermissions(db);
      }
    }
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

    if (enteredBy != -1) {
      sqlFilter.append("AND vp.enteredby = ? ");
    }

    if (userId != -1) {
      sqlFilter.append("AND vp.user_id = ? ");
    }

    if (includeEnabledOnly) {
      sqlFilter.append("AND vp.enabled = ? ");
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

    if (enteredBy != -1) {
      pst.setInt(++i, enteredBy);
    }

    if (userId != -1) {
      pst.setInt(++i, userId);
    }

    if (includeEnabledOnly) {
      pst.setBoolean(++i, true);
    }

    return i;
  }


  /**
   * Description of the Method
   *
   * @param id Description of the Parameter
   * @return Description of the Return Value
   */
  public int checkForDuplicates(int id) {
    int result = 0;
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      Viewpoint viewpoint = (Viewpoint) iterator.next();
      if (viewpoint.getVpUserId() == id) {
        ++result;
      }
    }
    return result;
  }

}

