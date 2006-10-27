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
package org.aspcfs.modules.service.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Used for building and storing a list of SyncTable objects
 *
 * @author matt rajkowski
 * @version $Id$
 * @created June, 2002
 */
public class SyncTableList extends ArrayList {
  public final static String tableName = "sync_table";
  public final static String uniqueField = "table_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private PagedListInfo pagedListInfo = null;

  private int systemId = -1;
  private boolean buildTextFields = true;
  private boolean buildSyncElementsOnly = false;
  private boolean buildCreateStatementsOnly = false;

  /**
   * Sets the lastAnchor attribute of the SyncTableList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the SyncTableList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the SyncTableList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the SyncTableList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the SyncTableList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Sets the PagedListInfo attribute of the SyncTableList object. <p>
   * <p/>
   * The query results will be constrained to the PagedListInfo parameters.
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Gets the tableName attribute of the SyncTableList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the SyncTableList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Constructor for the SyncTableList object
   */
  public SyncTableList() {
  }


  /**
   * Sets the systemId attribute of the SyncTableList object
   *
   * @param tmp The new systemId value
   */
  public void setSystemId(int tmp) {
    this.systemId = tmp;
  }


  /**
   * Sets the systemId attribute of the SyncTableList object
   *
   * @param tmp The new systemId value
   */
  public void setSystemId(String tmp) {
    this.systemId = Integer.parseInt(tmp);
  }


  /**
   * Sets the buildTextFields attribute of the SyncTableList object
   *
   * @param tmp The new buildTextFields value
   */
  public void setBuildTextFields(boolean tmp) {
    this.buildTextFields = tmp;
  }


  /**
   * Sets the buildSyncElementsOnly attribute of the SyncTableList object
   *
   * @param tmp The new buildSyncElementsOnly value
   */
  public void setBuildSyncElementsOnly(boolean tmp) {
    this.buildSyncElementsOnly = tmp;
  }


  /**
   * Sets the buildSyncElementsOnly attribute of the SyncTableList object
   *
   * @param tmp The new buildSyncElementsOnly value
   */
  public void setBuildSyncElementsOnly(String tmp) {
    this.buildSyncElementsOnly =
        (tmp.equalsIgnoreCase("true") ||
            tmp.equalsIgnoreCase("on"));
  }


  /**
   * Sets the buildCreateStatementsOnly attribute of the SyncTableList object
   *
   * @param tmp The new buildCreateStatementsOnly value
   */
  public void setBuildCreateStatementsOnly(boolean tmp) {
    this.buildCreateStatementsOnly = tmp;
  }


  /**
   * Sets the buildCreateStatementsOnly attribute of the SyncTableList object
   *
   * @param tmp The new buildCreateStatementsOnly value
   */
  public void setBuildCreateStatementsOnly(String tmp) {
    this.buildCreateStatementsOnly =
        (tmp.equalsIgnoreCase("true") ||
            tmp.equalsIgnoreCase("on"));
  }


  /**
   * Gets the systemId attribute of the SyncTableList object
   *
   * @return The systemId value
   */
  public int getSystemId() {
    return systemId;
  }


  /**
   * Gets the buildTextFields attribute of the SyncTableList object
   *
   * @return The buildTextFields value
   */
  public boolean getBuildTextFields() {
    return buildTextFields;
  }


  /**
   * Gets the buildSyncElementsOnly attribute of the SyncTableList object
   *
   * @return The buildSyncElementsOnly value
   */
  public boolean getBuildSyncElementsOnly() {
    return buildSyncElementsOnly;
  }


  /**
   * Gets the buildCreateStatementsOnly attribute of the SyncTableList object
   *
   * @return The buildCreateStatementsOnly value
   */
  public boolean getBuildCreateStatementsOnly() {
    return buildCreateStatementsOnly;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
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

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT table_id, system_id, element_name, mapped_class_name, entered, modified ");
    if (buildTextFields) {
      sql.append(", create_statement ");
    }
    sql.append(", order_id, sync_item, object_key ");
    sql.append("FROM sync_table ");
    sql.append("WHERE table_id > -1 ");
    createFilter(sql);
    sql.append("ORDER BY order_id ");
    pst = db.prepareStatement(sql.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      SyncTable thisTable = new SyncTable();
      thisTable.setBuildTextFields(buildTextFields);
      thisTable.buildRecord(rs);
      this.add(thisTable);
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

    if (systemId != -1) {
      sqlFilter.append("AND system_id = ? ");
    }

    if (buildSyncElementsOnly) {
      sqlFilter.append("AND sync_item = ? ");
    }

    if (buildCreateStatementsOnly) {
      sqlFilter.append("AND create_statement IS NOT NULL ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
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
    if (systemId != -1) {
      pst.setInt(++i, systemId);
    }
    if (buildSyncElementsOnly) {
      pst.setBoolean(++i, true);
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
   * Gets the objectMapping attribute of the SyncTableList object
   *
   * @param thisSystemId Description of the Parameter
   * @return The objectMapping value
   */
  public HashMap getObjectMapping(int thisSystemId) {
    HashMap objectMap = new HashMap();
    Iterator iList = this.iterator();
    while (iList.hasNext()) {
      SyncTable thisTable = (SyncTable) iList.next();
      if (thisTable.getSystemId() == thisSystemId && thisTable.getMappedClassName() != null) {
        objectMap.put(thisTable.getName(), thisTable);
      }
    }
    return objectMap;
  }


  /**
   * Removes a specific systemId from the cached list. TODO: Test this
   *
   * @param thisSystemId Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean clearObjectMapping(int thisSystemId) {
    Iterator iList = this.iterator();
    while (iList.hasNext()) {
      SyncTable thisTable = (SyncTable) iList.next();
      if (thisTable.getSystemId() == thisSystemId) {
        iList.remove();
        return true;
      }
    }
    return false;
  }

}

