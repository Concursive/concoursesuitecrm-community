//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.reports.base;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.text.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.actionlist.base.*;

/**
 *  Represents a report that has been added to the system
 *
 *@author     matt rajkowski
 *@created    October 1, 2003
 *@version    $Id$
 */
public class Report extends GenericBean {

  private int id = -1;
  private int categoryId = -1;
  private int permissionId = -1;
  private String filename = null;
  private int type = -1;
  private String title = null;
  private String description = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private boolean custom = false;


  /**
   *  Constructor for the Report object
   */
  public Report() { }


  /**
   *  Constructor for the Report object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Report(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Report object
   *
   *@param  db                Description of the Parameter
   *@param  reportId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Report(Connection db, int reportId) throws SQLException {
    queryRecord(db, reportId);
  }


  /**
   *  Reads the specified reportId and populates this object
   *
   *@param  db                Description of the Parameter
   *@param  reportId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int reportId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT r.* " +
        "FROM report r " +
        "WHERE report_id = ? ");
    pst.setInt(1, reportId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Report record not found.");
    }
  }


  /**
   *  Populates this object from a resultset
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //report table
    id = rs.getInt("report_id");
    categoryId = rs.getInt("category_id");
    permissionId = DatabaseUtils.getInt(rs, "permission_id");
    filename = rs.getString("filename");
    type = rs.getInt("type");
    title = rs.getString("title");
    description = rs.getString("description");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    custom = rs.getBoolean("custom");
  }


  /**
   *  Sets the id attribute of the Report object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Report object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryId attribute of the Report object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the Report object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the permissionId attribute of the Report object
   *
   *@param  tmp  The new permissionId value
   */
  public void setPermissionId(int tmp) {
    this.permissionId = tmp;
  }


  /**
   *  Sets the permissionId attribute of the Report object
   *
   *@param  tmp  The new permissionId value
   */
  public void setPermissionId(String tmp) {
    this.permissionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the filename attribute of the Report object
   *
   *@param  tmp  The new filename value
   */
  public void setFilename(String tmp) {
    this.filename = tmp;
  }


  /**
   *  Sets the type attribute of the Report object
   *
   *@param  tmp  The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the type attribute of the Report object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   *  Sets the title attribute of the Report object
   *
   *@param  tmp  The new title value
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   *  Sets the description attribute of the Report object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the entered attribute of the Report object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Report object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Report object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Report object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Report object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Report object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Report object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Report object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the Report object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the Report object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the custom attribute of the Report object
   *
   *@param  tmp  The new custom value
   */
  public void setCustom(boolean tmp) {
    this.custom = tmp;
  }


  /**
   *  Sets the custom attribute of the Report object
   *
   *@param  tmp  The new custom value
   */
  public void setCustom(String tmp) {
    this.custom = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the Report object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the categoryId attribute of the Report object
   *
   *@return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Gets the permissionId attribute of the Report object
   *
   *@return    The permissionId value
   */
  public int getPermissionId() {
    return permissionId;
  }


  /**
   *  Gets the filename attribute of the Report object
   *
   *@return    The filename value
   */
  public String getFilename() {
    return filename;
  }


  /**
   *  Gets the type attribute of the Report object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the title attribute of the Report object
   *
   *@return    The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   *  Gets the description attribute of the Report object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the entered attribute of the Report object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the Report object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the Report object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the Report object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the enabled attribute of the Report object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the custom attribute of the Report object
   *
   *@return    The custom value
   */
  public boolean getCustom() {
    return custom;
  }
}

