//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    June, 2002
 *@version    $Id$
 */
public class SyncTableList extends ArrayList {

  private int systemId = -1;
  private boolean buildTextFields = true;
  private boolean buildSyncElementsOnly = false;
  private boolean buildCreateStatementsOnly = false;


  /**
   *  Constructor for the SyncTableList object
   */
  public SyncTableList() { }


  /**
   *  Sets the systemId attribute of the SyncTableList object
   *
   *@param  tmp  The new systemId value
   */
  public void setSystemId(int tmp) {
    this.systemId = tmp;
  }


  /**
   *  Sets the systemId attribute of the SyncTableList object
   *
   *@param  tmp  The new systemId value
   */
  public void setSystemId(String tmp) {
    this.systemId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildTextFields attribute of the SyncTableList object
   *
   *@param  tmp  The new buildTextFields value
   */
  public void setBuildTextFields(boolean tmp) {
    this.buildTextFields = tmp;
  }


  /**
   *  Sets the buildSyncElementsOnly attribute of the SyncTableList object
   *
   *@param  tmp  The new buildSyncElementsOnly value
   */
  public void setBuildSyncElementsOnly(boolean tmp) {
    this.buildSyncElementsOnly = tmp;
  }


  /**
   *  Sets the buildSyncElementsOnly attribute of the SyncTableList object
   *
   *@param  tmp  The new buildSyncElementsOnly value
   */
  public void setBuildSyncElementsOnly(String tmp) {
    this.buildSyncElementsOnly =
        (tmp.equalsIgnoreCase("true") ||
        tmp.equalsIgnoreCase("on"));
  }


  /**
   *  Sets the buildCreateStatementsOnly attribute of the SyncTableList object
   *
   *@param  tmp  The new buildCreateStatementsOnly value
   */
  public void setBuildCreateStatementsOnly(boolean tmp) {
    this.buildCreateStatementsOnly = tmp;
  }


  /**
   *  Sets the buildCreateStatementsOnly attribute of the SyncTableList object
   *
   *@param  tmp  The new buildCreateStatementsOnly value
   */
  public void setBuildCreateStatementsOnly(String tmp) {
    this.buildCreateStatementsOnly =
        (tmp.equalsIgnoreCase("true") ||
        tmp.equalsIgnoreCase("on"));
  }


  /**
   *  Gets the systemId attribute of the SyncTableList object
   *
   *@return    The systemId value
   */
  public int getSystemId() {
    return systemId;
  }


  /**
   *  Gets the buildTextFields attribute of the SyncTableList object
   *
   *@return    The buildTextFields value
   */
  public boolean getBuildTextFields() {
    return buildTextFields;
  }


  /**
   *  Gets the buildSyncElementsOnly attribute of the SyncTableList object
   *
   *@return    The buildSyncElementsOnly value
   */
  public boolean getBuildSyncElementsOnly() {
    return buildSyncElementsOnly;
  }


  /**
   *  Gets the buildCreateStatementsOnly attribute of the SyncTableList object
   *
   *@return    The buildCreateStatementsOnly value
   */
  public boolean getBuildCreateStatementsOnly() {
    return buildCreateStatementsOnly;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
    sql.append(", order_id, sync_item ");
    sql.append(
        "FROM sync_table ");
    sql.append("WHERE table_id > -1 ");
    createFilter(sql);
    sql.append(
        "ORDER BY order_id ");
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
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
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
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (systemId != -1) {
      pst.setInt(++i, systemId);
    }
    if (buildSyncElementsOnly) {
      pst.setBoolean(++i, true);
    }
    return i;
  }


  /**
   *  Gets the objectMapping attribute of the SyncTableList object
   *
   *@param  thisSystemId  Description of the Parameter
   *@return               The objectMapping value
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
   *  Removes a specific systemId from the cached list. TODO: Test this
   *
   *@param  thisSystemId  Description of the Parameter
   *@return               Description of the Return Value
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

