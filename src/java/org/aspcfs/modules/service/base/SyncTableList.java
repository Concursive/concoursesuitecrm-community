//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class SyncTableList extends ArrayList {

  private int systemId = -1;
  private boolean buildTextFields = true;
  private boolean buildSyncElementsOnly = false;
  private boolean buildCreateStatementsOnly = false;

  public SyncTableList() { }

  public void setSystemId(int tmp) { this.systemId = tmp; }
  public void setSystemId(String tmp) { this.systemId = Integer.parseInt(tmp); }
  public void setBuildTextFields(boolean tmp) { this.buildTextFields = tmp; }
  public void setBuildSyncElementsOnly(boolean tmp) { this.buildSyncElementsOnly = tmp; }
  public void setBuildSyncElementsOnly(String tmp) { 
    this.buildSyncElementsOnly = 
      (tmp.equalsIgnoreCase("true") || 
      tmp.equalsIgnoreCase("on")); 
  }
  public void setBuildCreateStatementsOnly(boolean tmp) { this.buildCreateStatementsOnly = tmp; }
  public void setBuildCreateStatementsOnly(String tmp) { 
    this.buildCreateStatementsOnly = 
      (tmp.equalsIgnoreCase("true") || 
      tmp.equalsIgnoreCase("on")); 
  }

  public int getSystemId() { return systemId; }
  public boolean getBuildTextFields() { return buildTextFields; }
  public boolean getBuildSyncElementsOnly() { return buildSyncElementsOnly; }
  public boolean getBuildCreateStatementsOnly() { return buildCreateStatementsOnly; }

  public void select(Connection db) throws SQLException {
    buildList(db);
  }
  
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

  public HashMap getObjectMapping(int thisSystemId)  {
    HashMap objectMap = new HashMap();
    Iterator iList = this.iterator();
    while (iList.hasNext()) {
      SyncTable thisTable = (SyncTable)iList.next();
      if (thisTable.getSystemId() == thisSystemId && thisTable.getMappedClassName() != null) {
        objectMap.put(thisTable.getName(), thisTable);
      }
    }
    return objectMap;
  }

}

