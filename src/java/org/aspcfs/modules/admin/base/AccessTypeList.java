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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Represents a list of access types
 *
 * @author Mathur
 * @version $id:exp$
 * @created June 25, 2003
 */
public class AccessTypeList extends LookupList {
  int linkModuleId = -1;


  /**
   * Constructor for the AccessTypeList object
   */
  public AccessTypeList() {
  }


  /**
   * Constructor for the AccessTypeList object
   *
   * @param db           Description of the Parameter
   * @param linkModuleId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public AccessTypeList(Connection db, int linkModuleId) throws SQLException {
    queryRecord(db, linkModuleId);
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param linkModuleId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int linkModuleId) throws SQLException {
    this.linkModuleId = linkModuleId;
    buildList(db);
  }


  /**
   * Sets the linkModuleId attribute of the AccessTypeList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the AccessTypeList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkModuleId attribute of the AccessTypeList object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Builds the Access Type list
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlSelect = new StringBuffer();

    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM lookup_access_types " +
            "WHERE code > -1 ");
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
            sqlCount.toString() + sqlFilter.toString() +
                "AND " + DatabaseUtils.toLowerCase(db) + "(description) < ? ");
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
      pagedListInfo.setDefaultSort("description ", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ",description ");
    }
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "lat.* " +
            "FROM lookup_access_types lat " +
            "WHERE code > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      AccessType thisType = new AccessType(rs);
      this.add(thisType);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   * Creates filters for the building the list
   *
   * @param sqlFilter Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (linkModuleId > -1) {
      sqlFilter.append("AND link_module_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND entered > ? ");
      }
      sqlFilter.append("AND entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND modified > ? ");
      sqlFilter.append("AND entered < ? ");
      sqlFilter.append("AND modified < ? ");
    }
  }


  /**
   * Prepares and sets values for filters created by the createFilter method
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (linkModuleId > -1) {
      pst.setInt(++i, linkModuleId);
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    return i;
  }


  /**
   * Gets the default item from the Access Types
   *
   * @return The defaultItem value
   */
  public int getDefaultItem() {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      AccessType thisType = (AccessType) i.next();
      if (thisType.getDefaultItem()) {
        return thisType.getCode();
      }
    }
    return -1;
  }


  /**
   * Gets the code for a specified rule in a access list
   *
   * @param ruleId Description of the Parameter
   * @return The code value
   */
  public int getCode(int ruleId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      AccessType thisType = (AccessType) i.next();
      if (thisType.getRuleId() == ruleId) {
        return thisType.getCode();
      }
    }
    return -1;
  }

  public int getRuleId(int code) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      AccessType thisType = (AccessType) i.next();
      if (thisType.getCode() == code) {
        return thisType.getRuleId();
      }
    }
    return -1;
  }

  public String getDescriptionFromRuleId(int ruleId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      AccessType thisType = (AccessType) i.next();
      if (thisType.getRuleId() == ruleId) {
        return thisType.getDescription();
      }
    }
    return null;
  }

  public static boolean exists(Connection db, int linkModuleId, int ruleId) throws SQLException {
    boolean result = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT code " +
            "FROM lookup_access_types " +
            "WHERE link_module_id = ? " +
            "AND rule_id = ? "
    );
    pst.setInt(1, linkModuleId);
    pst.setInt(2, ruleId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = true;
    }
    rs.close();
    pst.close();
    return result;
  }
}

