package com.darkhorseventures.autoguide.base;

import java.sql.*;
import java.text.*;

/**
 *  Represents a scheduled date when an ad is going to run, along with details
 *  about the ad
 *
 *@author     matt rajkowski
 *@created    May 17, 2002
 *@version    $Id$
 */
public class AdRun {
  private int id = -1;
  private int inventoryId = -1;
  private java.sql.Date runDate = null;
  private int adTypeId = -1;
  private String adTypeName = null;
  private boolean includePhoto = false;
  private java.sql.Date completeDate = null;
  private int completedBy = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean remove = false;


  /**
   *  Constructor for the AdRun object
   */
  public AdRun() { }


  /**
   *  Constructor for the AdRun object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public AdRun(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the AdRun object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the AdRun object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the inventoryId attribute of the AdRun object
   *
   *@param  tmp  The new inventoryId value
   */
  public void setInventoryId(int tmp) {
    this.inventoryId = tmp;
  }


  /**
   *  Sets the inventoryId attribute of the AdRun object
   *
   *@param  tmp  The new inventoryId value
   */
  public void setInventoryId(String tmp) {
    this.inventoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the runDate attribute of the AdRun object
   *
   *@param  tmp  The new runDate value
   */
  public void setRunDate(java.sql.Date tmp) {
    this.runDate = tmp;
  }


  /**
   *  Sets the runDate attribute of the AdRun object
   *
   *@param  tmp  The new runDate value
   */
  public void setRunDate(String tmp) {
    boolean success = false;
    try {
      this.runDate = java.sql.Date.valueOf(tmp);
      success = true;
    } catch (Exception e) {
      runDate = null;
    }
    
    if (!success) {
      try {
        java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
        runDate = new java.sql.Date(new java.util.Date().getTime());
        runDate.setTime(tmpDate.getTime());
      } catch (Exception e) {
        runDate = null;
      }
    }
  }


  /**
   *  Sets the adType attribute of the AdRun object
   *
   *@param  tmp  The new adType value
   */
  public void setAdTypeId(int tmp) {
    this.adTypeId = tmp;
  }


  /**
   *  Sets the adType attribute of the AdRun object
   *
   *@param  tmp  The new adType value
   */
  public void setAdType(int tmp) {
    this.adTypeId = tmp;
  }


  /**
   *  Sets the adType attribute of the AdRun object
   *
   *@param  tmp  The new adType value
   */
  public void setAdType(String tmp) {
    this.adTypeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the adTypeId attribute of the AdRun object
   *
   *@param  tmp  The new adTypeId value
   */
  public void setAdTypeId(String tmp) {
    this.adTypeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the adTypeName attribute of the AdRun object
   *
   *@param  tmp  The new adTypeName value
   */
  public void setAdTypeName(String tmp) {
    this.adTypeName = tmp;
  }


  /**
   *  Sets the includePhoto attribute of the AdRun object
   *
   *@param  tmp  The new includePhoto value
   */
  public void setIncludePhoto(boolean tmp) {
    this.includePhoto = tmp;
  }


  /**
   *  Sets the includePhoto attribute of the AdRun object
   *
   *@param  tmp  The new includePhoto value
   */
  public void setIncludePhoto(String tmp) {
    this.includePhoto = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the completeDate attribute of the AdRun object
   *
   *@param  tmp  The new completeDate value
   */
  public void setCompleteDate(java.sql.Date tmp) {
    this.completeDate = tmp;
  }


  /**
   *  Sets the completeDate attribute of the AdRun object
   *
   *@param  tmp  The new completeDate value
   */
  public void setCompleteDate(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      completeDate = new java.sql.Date(new java.util.Date().getTime());
      completeDate.setTime(tmpDate.getTime());
    } catch (Exception e) {
      completeDate = null;
    }
  }


  /**
   *  Sets the completedBy attribute of the AdRun object
   *
   *@param  tmp  The new completedBy value
   */
  public void setCompletedBy(int tmp) {
    this.completedBy = tmp;
  }


  /**
   *  Sets the completedBy attribute of the AdRun object
   *
   *@param  tmp  The new completedBy value
   */
  public void setCompletedBy(String tmp) {
    this.completedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the AdRun object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the AdRun object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }
  
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the AdRun object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the AdRun object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }
  
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the remove attribute of the AdRun object
   *
   *@param  tmp  The new remove value
   */
  public void setRemove(boolean tmp) {
    this.remove = tmp;
  }


  /**
   *  Sets the remove attribute of the AdRun object
   *
   *@param  tmp  The new remove value
   */
  public void setRemove(String tmp) {
    this.remove = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Gets the completedBy attribute of the AdRun object
   *
   *@return    The completedBy value
   */
  public int getCompletedBy() {
    return completedBy;
  }


  /**
   *  Gets the id attribute of the AdRun object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the inventoryId attribute of the AdRun object
   *
   *@return    The inventoryId value
   */
  public int getInventoryId() {
    return inventoryId;
  }


  /**
   *  Gets the runDate attribute of the AdRun object
   *
   *@return    The runDate value
   */
  public java.sql.Date getRunDate() {
    return runDate;
  }


  /**
   *  Gets the adTypeId attribute of the AdRun object
   *
   *@return    The adType value
   */
  public int getAdType() {
    return adTypeId;
  }


  /**
   *  Gets the adTypeId attribute of the AdRun object
   *
   *@return    The adTypeId value
   */
  public int getAdTypeId() {
    return adTypeId;
  }


  /**
   *  Gets the adTypeName attribute of the AdRun object
   *
   *@return    The adTypeName value
   */
  public String getAdTypeName() {
    return adTypeName;
  }


  /**
   *  Gets the includePhoto attribute of the AdRun object
   *
   *@return    The includePhoto value
   */
  public boolean getIncludePhoto() {
    return includePhoto;
  }


  /**
   *  Gets the completeDate attribute of the AdRun object
   *
   *@return    The completeDate value
   */
  public java.sql.Date getCompleteDate() {
    return completeDate;
  }


  /**
   *  Gets the entered attribute of the AdRun object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the AdRun object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the AdRun object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the AdRun object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the complete attribute of the AdRun object
   *
   *@return    The complete value
   */
  public boolean isComplete() {
    return completeDate != null;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void insert(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AdRun-> Inserting new record: InventoryId(" + inventoryId + ")");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO autoguide_ad_run (inventory_id, " +
        "run_date, ad_type, include_photo, complete_date, completedby, " +
        "enteredby, modifiedby) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, inventoryId);
    pst.setDate(++i, runDate);
    pst.setInt(++i, adTypeId);
    pst.setBoolean(++i, includePhoto);
    if (completeDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
      pst.setInt(++i, -1);
    } else {
      pst.setDate(++i, completeDate);
      pst.setInt(++i, completedBy);
    }
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, enteredBy);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
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
          "completedby = ?, " +
          "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
          "WHERE ad_run_id = ? ");
      PreparedStatement pst = db.prepareStatement(sql.toString());
      int i = 0;
      pst.setDate(++i, runDate);
      pst.setInt(++i, adTypeId);
      pst.setBoolean(++i, includePhoto);
      if (completeDate == null) {
        pst.setNull(++i, java.sql.Types.DATE);
        pst.setInt(++i, -1);
      } else {
        pst.setDate(++i, completeDate);
        pst.setInt(++i, completedBy);
      }
      pst.setInt(++i, modifiedBy);
      pst.setInt(++i, id);
      pst.execute();
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void markComplete(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE autoguide_ad_run " +
        "SET complete_date = CURRENT_TIMESTAMP, completedby = ? " +
        "WHERE ad_run_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, completedBy);
    pst.setInt(++i, id);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void markIncomplete(Connection db, String intHierarchyList) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE autoguide_ad_run " +
        "SET complete_date = ?, completedby = ? " +
        "WHERE ad_run_id = ? AND completedby IN (" + intHierarchyList + ") ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setNull(++i, java.sql.Types.DATE);
    pst.setInt(++i, -1);
    pst.setInt(++i, id);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
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


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("ad_run_id");
    inventoryId = rs.getInt("inventory_id");
    runDate = rs.getDate("run_date");
    adTypeId = rs.getInt("ad_type");
    includePhoto = rs.getBoolean("include_photo");
    completeDate = rs.getDate("complete_date");
    completedBy = rs.getInt("completedby");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    adTypeName = rs.getString("description");
  }
}

