//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.autoguide.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.cfsbase.Constants;

public class ModelList extends ArrayList {

  public static String tableName = "autoguide_model";
  public static String uniqueField = "model_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private int makeId = -1;
  private int year = -1;
  
  public ModelList() { }

  public void setLastAnchor(java.sql.Timestamp tmp) { this.lastAnchor = tmp; }
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }
  public void setNextAnchor(java.sql.Timestamp tmp) { this.nextAnchor = tmp; }
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }
  public void setSyncType(int tmp) { this.syncType = tmp; }
  public void setSyncType(String tmp) { this.syncType = Integer.parseInt(tmp); }
  public void setMakeId(int tmp) { this.makeId = tmp; }
  public void setMakeId(String tmp) { this.makeId = Integer.parseInt(tmp); }
  public int getMakeId() { return makeId; }
  public void setYear(int tmp) { this.year = tmp; }
  public void setYear(String tmp) { 
    try {
      this.year = Integer.parseInt(tmp); 
    } catch(Exception e) {
    }
  }
  public int getYear() { return year; }

  public String getTableName() { return tableName; }
  public String getUniqueField() { return uniqueField; }
  
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
  
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      Model thisModel = new Model(rs);
      this.add(thisModel);
    }
    rs.close();
    if (pst != null) pst.close();
  }
  
  public Model getObject(ResultSet rs) throws SQLException {
    Model thisModel = new Model(rs);
    return thisModel;
  }
  
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;
    
    StringBuffer sql = new StringBuffer(); 
    sql.append(  
      "SELECT model.model_id, model.make_id AS model_make_id, model.model_name, " +
      "model.entered, model.enteredby, " + 
      "model.modified, model.modifiedby, " +
      "make.make_id, make.make_name, " +
      "make.entered AS make_entered, make.enteredby AS make_enteredby, " +
      "make.modified AS make_modified, make.modifiedby AS make_modifiedby " +
      "FROM autoguide_model model LEFT JOIN autoguide_make make ON model.make_id = make.make_id ");
    sql.append("WHERE model.model_id > -1 ");
    createFilter(sql);
    sql.append("ORDER BY model_name ");
    pst = db.prepareStatement(sql.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    return rs;
  }
  
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND model.entered > ? ");
      }
      sqlFilter.append("AND model.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND model.modified > ? ");
      sqlFilter.append("AND model.entered < ? ");
      sqlFilter.append("AND model.modified < ? ");
    }
    if (makeId > -1) {
      sqlFilter.append("AND make.make_id = ? ");
      if (year > -1) {
        sqlFilter.append("AND model.model_id IN (SELECT DISTINCT model_id FROM autoguide_vehicle WHERE year = ?) ");
      }
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
    if (makeId > -1) {
      pst.setInt(++i, makeId);
      if (year > -1) {
        pst.setInt(++i, year);
      }
    }
    return i;
  }
}

