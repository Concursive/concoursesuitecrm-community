//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.util.Hashtable;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class SyncTableList extends Vector {

  private int systemId = -1;
  private boolean buildTextFields = true;

  public SyncTableList() { }

  public void setSystemId(int tmp) { this.systemId = tmp; }
  public void setSystemId(String tmp) { this.systemId = Integer.parseInt(tmp); }
  public void setBuildTextFields(boolean tmp) { this.buildTextFields = tmp; }

  public int getSystemId() { return systemId; }
  public boolean getBuildTextFields() { return buildTextFields; }
  
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
  
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    
    StringBuffer sql = new StringBuffer(); 
    sql.append(  
      "SELECT table_id, system_id, table_name, mapped_class_name, entered, modified ");
    if (buildTextFields) {
      sql.append(", create_statement ");
    }
    sql.append(", order_id ");
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
      this.addElement(thisTable);
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
  }
  
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (systemId != -1) {
      pst.setInt(++i, systemId);
    }
    return i;
  }

  public Hashtable getObjectMapping(int thisSystemId)  {
    Hashtable objectMap = new Hashtable();
    Iterator iList = this.iterator();
    while (iList.hasNext()) {
      SyncTable thisTable = (SyncTable)iList.next();
      if (thisTable.getSystemId() == thisSystemId) {
        objectMap.put(thisTable.getTableName(), thisTable.getMappedClassName());
      }
    }
    return objectMap;
  }

}

