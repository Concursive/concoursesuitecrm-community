package com.darkhorseventures.autoguide.base;

import java.sql.*;
import java.text.*;

public class AdRun {
  private int id = -1;
  private int inventoryId = -1;
  private java.sql.Date startDate = null;
  private java.sql.Date endDate = null;
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
  public void setStartDate(java.sql.Date tmp) { this.startDate = tmp; }
  public void setStartDate(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      startDate = new java.sql.Date(new java.util.Date().getTime());
      startDate.setTime(tmpDate.getTime());
    } catch (Exception e) {
      startDate = null;
    }
  }
  public void setEndDate(java.sql.Date tmp) { this.endDate = tmp; }
  public void setEndDate(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      endDate = new java.sql.Date(new java.util.Date().getTime());
      endDate.setTime(tmpDate.getTime());
    } catch (Exception e) {
      endDate = null;
    }
  }
  public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
  public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
  public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
  public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
  
  public int getId() { return id; }
  public int getInventoryId() { return inventoryId; }
  public java.sql.Date getStartDate() { return startDate; }
  public java.sql.Date getEndDate() { return endDate; }
  public java.sql.Timestamp getEntered() { return entered; }
  public int getEnteredBy() { return enteredBy; }
  public java.sql.Timestamp getModified() { return modified; }
  public int getModifiedBy() { return modifiedBy; }

  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("ad_run_id");
    inventoryId = rs.getInt("inventory_id");
    startDate = rs.getDate("start_date");
    endDate =  rs.getDate("end_date");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }
}
