//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.autoguide.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.cfsbase.Constants;

public class MakeList extends ArrayList {

  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  
  public MakeList() { }

  public void setLastAnchor(java.sql.Timestamp tmp) { this.lastAnchor = tmp; }
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }
  public void setNextAnchor(java.sql.Timestamp tmp) { this.nextAnchor = tmp; }
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }
  

  public void select(Connection db) throws SQLException {
    buildList(db);
  }
  
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    
    StringBuffer sql = new StringBuffer(); 
    sql.append(  
      "SELECT make_id, make_name, entered, enteredby, modified, modifiedby " +
      "FROM autoguide_make ");
    sql.append("WHERE make_id > -1 ");
    createFilter(sql);
    pst = db.prepareStatement(sql.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Make thisMake = new Make(rs);
      this.add(thisMake);
    }
    rs.close();
    pst.close();
  }
  
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
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
  
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
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
}

