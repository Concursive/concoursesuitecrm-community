package com.darkhorseventures.autoguide.base;

import java.sql.*;
import java.text.*;

public class AdRun {
  private int id = -1;
  private int inventoryId = -1;
  private java.sql.Date runDate = null;
  private int adType = -1;
  private String adTypeName = null;
  private boolean includePhoto = false;
  private java.sql.Date completeDate = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean remove = false;
  
  public AdRun() {}

  public AdRun(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public void setId(int tmp) { this.id = tmp; }
  public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
  public void setInventoryId(int tmp) { this.inventoryId = tmp; }
  public void setRunDate(java.sql.Date tmp) { this.runDate = tmp; }
  public void setRunDate(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      runDate = new java.sql.Date(new java.util.Date().getTime());
      runDate.setTime(tmpDate.getTime());
    } catch (Exception e) {
      runDate = null;
    }
  }
  public void setAdType(int tmp) { this.adType = tmp; }
  public void setAdType(String tmp) { this.adType = Integer.parseInt(tmp); }
  public void setAdTypeName(String tmp) { this.adTypeName = tmp; }
  public void setIncludePhoto(boolean tmp) { this.includePhoto = tmp; }
  public void setIncludePhoto(String tmp) { 
    this.includePhoto = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp)); 
  }
  public void setCompleteDate(java.sql.Date tmp) { this.completeDate = tmp; }
  public void setCompleteDate(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      completeDate = new java.sql.Date(new java.util.Date().getTime());
      completeDate.setTime(tmpDate.getTime());
    } catch (Exception e) {
      completeDate = null;
    }
  }
  public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
  public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
  public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
  public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
  public void setRemove(boolean tmp) { this.remove = tmp; }
  public void setRemove(String tmp) { 
    this.remove = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp)); 
  }
  
  public int getId() { return id; }
  public int getInventoryId() { return inventoryId; }
  public java.sql.Date getRunDate() { return runDate; }
  public int getAdType() { return adType; }
  public String getAdTypeName() { return adTypeName; }
  public boolean getIncludePhoto() { return includePhoto; }
  public java.sql.Date getCompleteDate() { return completeDate; }
  public java.sql.Timestamp getEntered() { return entered; }
  public int getEnteredBy() { return enteredBy; }
  public java.sql.Timestamp getModified() { return modified; }
  public int getModifiedBy() { return modifiedBy; }
  public boolean isComplete() { return completeDate != null; }
  
  public void insert(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AdRun-> Inserting new record: InventoryId(" + inventoryId + ")");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
      "INSERT INTO autoguide_ad_run (inventory_id, " +
      "run_date, ad_type, include_photo, complete_date, " +
      "enteredby, modifiedby) " +
      "VALUES (?, ?, ?, ?, ?, ?, ?)");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, inventoryId);
    pst.setDate(++i, runDate);
    pst.setInt(++i, adType);
    pst.setBoolean(++i, includePhoto);
    if (completeDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setDate(++i, completeDate);
    }
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    pst.close();
  }
  
  public void update(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AdRun-> Updating record: id(" + id + ")");
    }
    
    if (remove) {
      delete(db);
    } else if (id == -1) {
      insert(db);
    } else {
      StringBuffer sql = new StringBuffer();
      sql.append(
        "UPDATE autoguide_ad_run " +
        "SET run_date = ?, ad_type = ?, include_photo = ?, complete_date = ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
        "WHERE ad_run_id = ? ");
      PreparedStatement pst = db.prepareStatement(sql.toString());
      int i = 0;
      pst.setDate(++i, runDate);
      pst.setInt(++i, adType);
      pst.setBoolean(++i, includePhoto);
      if (completeDate == null) {
        pst.setNull(++i, java.sql.Types.DATE);
      } else {
        pst.setDate(++i, completeDate);
      }
      pst.setInt(++i, modifiedBy);
      pst.setInt(++i, id);
      pst.execute();
      pst.close();
    }
  }
  
  public void delete(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
      "DELETE FROM autoguide_ad_run " +
      "WHERE ad_run_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, id);
    pst.execute();
    pst.close();
  }
  
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("ad_run_id");
    inventoryId = rs.getInt("inventory_id");
    runDate = rs.getDate("run_date");
    adType = rs.getInt("ad_type");
    includePhoto = rs.getBoolean("include_photo");
    completeDate =  rs.getDate("complete_date");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    adTypeName = rs.getString("description");
  }
}
