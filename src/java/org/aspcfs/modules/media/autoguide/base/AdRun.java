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
  private boolean complete = false;
  private java.sql.Date completeDate = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  
  public AdRun() {}

  public AdRun(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public void setId(int tmp) { this.id = tmp; }
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
  public void setAdTypeName(String tmp) { this.adTypeName = tmp; }
  public void setIncludePhoto(boolean tmp) { this.includePhoto = tmp; }
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
  
  public int getId() { return id; }
  public int getInventoryId() { return inventoryId; }
  public java.sql.Date getRunDate() { return runDate; }
  public int getAdType() { return adType; }
  public String getAdTypeString() { return adTypeName; }
  public boolean getIncludePhoto() { return includePhoto; }
  public boolean getComplete() { return complete; }
  public java.sql.Date getCompleteDate() { return completeDate; }
  public java.sql.Timestamp getEntered() { return entered; }
  public int getEnteredBy() { return enteredBy; }
  public java.sql.Timestamp getModified() { return modified; }
  public int getModifiedBy() { return modifiedBy; }

  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("ad_run_id");
    inventoryId = rs.getInt("inventory_id");
    runDate = rs.getDate("run_date");
    adType = rs.getInt("ad_type");
    includePhoto = rs.getBoolean("include_photo");
    complete = rs.getBoolean("complete");
    completeDate =  rs.getDate("complete_date");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    adTypeName = rs.getString("type_name");
  }
}
