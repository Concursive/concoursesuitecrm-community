//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.autoguide.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.cfsbase.Constants;

public class VehicleList extends ArrayList {

  public static String tableName = "autoguide_vehicle";
  public static String uniqueField = "vehicle_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private int year = -1;
  
  public VehicleList() { }

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
  public void setYear(int tmp) { this.year = tmp; }
  public void setYear(String tmp) { this.year = Integer.parseInt(tmp); }
  
  public String getTableName() { return tableName; }
  public String getUniqueField() { return uniqueField; }
  
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
  
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      Vehicle thisVehicle = this.getObject(rs);
      this.add(thisVehicle);
    }
    rs.close();
    if (pst != null) pst.close();
  }
  
  public Vehicle getObject(ResultSet rs) throws SQLException {
    Vehicle thisVehicle = new Vehicle(rs);
    return thisVehicle;
  }
   
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;
    
    StringBuffer sql = new StringBuffer(); 
    sql.append(  
      "SELECT v.vehicle_id, v.year, v.make_id AS vehicle_make_id, " +
      "v.model_id AS vehicle_model_id, v.entered AS vehicle_entered, " +
      "v.enteredby AS vehicle_enteredby, v.modified AS vehicle_modified, " +
      "v.modifiedby AS vehicle_modifiedby, " +
      "model.model_id, model.make_id AS model_make_id, model.model_name, " +
      "model.entered, model.enteredby, " + 
      "model.modified, model.modifiedby, " +
      "make.make_id, make.make_name, " +
      "make.entered AS make_entered, make.enteredby AS make_enteredby, " +
      "make.modified AS make_modified, make.modifiedby AS make_modifiedby " +
      "FROM autoguide_vehicle v " +
      " LEFT JOIN autoguide_make make ON v.make_id = make.make_id " +
      " LEFT JOIN autoguide_model model ON v.model_id = model.model_id ");
    sql.append("WHERE vehicle_id > -1 ");
    createFilter(sql);
    sql.append("ORDER BY vehicle_id ");
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
        sqlFilter.append("AND v.entered > ? ");
      }
      sqlFilter.append("AND v.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND v.modified > ? ");
      sqlFilter.append("AND v.entered < ? ");
      sqlFilter.append("AND v.modified < ? ");
    }
    if (year > -1) {
      sqlFilter.append("AND v.year = ? ");
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
    if (year > -1) {
      pst.setInt(++i, year);
    }
    return i;
  }
  
  public static ArrayList buildYearList(Connection db) throws SQLException {
    ArrayList years = new ArrayList();
    String sql = 
      "SELECT DISTINCT year FROM autoguide_vehicle ORDER BY year DESC";
    PreparedStatement pst = db.prepareStatement(sql);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      String thisYear = String.valueOf(rs.getInt("year"));
      years.add(thisYear);
    }
    rs.close();
    pst.close();
    return years;
  }
}

