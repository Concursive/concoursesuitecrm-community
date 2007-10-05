/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.reports.base;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author zhenya.zhidok
 * @version $Id: ReportType.java 24.01.2007 zhenya.zhidok $
 * @created 24.01.2007
 *
 */
public class ReportType {

  private int code = -1;
  private String description = "";
  private boolean defaultItem = false;
  private int level = 0;
  private boolean enabled = true;
  private int constant = -1;
  protected java.sql.Timestamp entered = null;
  protected java.sql.Timestamp modified = null;

  public ReportType() {

  }

  /**
   * Constructor 
   * 
   * @param db
   *          Description of the Parameter
   * @param id
   *          Description of the Parameter
   * @throws SQLException
   *           Description of the Exception
   */
  public ReportType(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }

  /**
   * Constructor 
   * 
   * @param rs
   *          Description of the Parameter
   * @throws SQLException
   *           Description of the Exception
   */
  public ReportType(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  /**
   * Gets the code attribute of the ReportType object
   * 
   * @return code The code value
   */
  public int getCode() {
    return this.code;
  }

  /**
   * Sets the code attribute of the ReportType object
   * 
   * @param code
   *          The new code value
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * Sets the code attribute of the ReportType object
   * 
   * @param code
   *          The new code value
   */
  public void setCode(String code) {
    this.code = Integer.parseInt(code);
  }

  /**
   * Gets the constant attribute of the ReportType object
   * 
   * @return constant The constant value
   */
  public int getConstant() {
    return this.constant;
  }

  /**
   * Sets the constant attribute of the ReportType object
   * 
   * @param constant
   *          The new constant value
   */
  public void setConstant(int constant) {
    this.constant = constant;
  }
  /**
   * Gets the modified attribute of the LookupElement object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
		return modified;
  }

  /**
   * Sets the constant attribute of the ReportType object
   * 
   * @param constant
   *          The new constant value
   */
  public void setConstant(String constant) {
    this.constant = Integer.parseInt(constant);
  }

  /**
   * 
   * /** Gets the description attribute of the ReportType object
   * 
   * @return description The description value
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the description attribute of the ReportType object
   * 
   * @param description
   *          The new description value
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the enabled attribute of the ReportType object
   * 
   * @return enabled The enabled value
   */
  public boolean getEnabled() {
    return this.enabled;
  }

  /**
   * Sets the enabled attribute of the ReportType object
   * 
   * @param enabled
   *          The new enabled value
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Sets the enabled attribute of the ReportType object
   * 
   * @param enabled
   *          The new enabled value
   */
  public void setEnabled(String enabled) {
    this.enabled = DatabaseUtils.parseBoolean(enabled);
  }

  /**
   * Gets the level attribute of the ReportType object
   * 
   * @return level The level value
   */
  public int getLevel() {
    return this.level;
  }

  /**
   * Sets the level attribute of the ReportType object
   * 
   * @param level
   *          The new level value
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * Gets the defaultItem attribute of the ReportType object
   * 
   * @return defaultItem The defaultItem value
   */
  public boolean getDefaultItem() {
    return this.defaultItem;
  }

  /**
   * Sets the defaultItem attribute of the ReportType object
   * 
   * @param defaultItem
   *          The new defaultItem value
   */
  public void setDefaultItem(boolean defaultItem) {
    this.defaultItem = defaultItem;
  }

  /**
   * Sets the defaultItem attribute of the ReportType object
   * 
   * @param defaultItem
   *          The new defaultItem value
   */
  public void setDefaultItem(String defaultItem) {
    this.defaultItem = DatabaseUtils.parseBoolean(defaultItem);
  }

  /**
   * @param db
   * @param code
   * @throws SQLException 
   */
  private void queryRecord(Connection db, int code) throws SQLException {
    if (code == -1) {
      throw new SQLException("Invalid ID");
    }

    String sql =
      "SELECT code, description, default_item, " + DatabaseUtils.addQuotes(db, "level") + 
              ", enabled, "+DatabaseUtils.addQuotes(db,"constant")+", entered, modified "+
      "FROM lookup_report_type " +
      "WHERE code = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, code);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new java.sql.SQLException("ID not found");
    }
    rs.close();
    pst.close();
  }

  /**
   * Description of the Method
   * 
   * @param rs
   *          Description of the Parameter
   * @throws java.sql.SQLException
   *           Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws java.sql.SQLException {
    code = rs.getInt("code");
    description = rs.getString("description");
    defaultItem = rs.getBoolean("default_item");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
    constant = rs.getInt("constant");
    if (!(this.getEnabled())) {
      description += " (X)";
    }
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
  }

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *           Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    code = DatabaseUtils.getNextSeq(db, "lookup_report_type_code_seq");
    sql.append("INSERT INTO lookup_report_type (");
    if (code > -1) {
      sql.append("code, ");
    }
    sql.append(" description, default_item, " + DatabaseUtils.addQuotes(db, "level")  
    	+", enabled, " + DatabaseUtils.addQuotes(db, "constant")+") "
        +"VALUES (");
    if (code > -1) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (code > -1) {
      pst.setInt(++i, this.getCode());
    }
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, this.getDefaultItem());
    pst.setInt(++i, this.getLevel());
    pst.setBoolean(++i, true);
    pst.setInt(++i, this.getConstant());
    pst.execute();
    pst.close();
    code = DatabaseUtils.getCurrVal(db, "lookup_report_type_code_seq", code);
    return true;
  }

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *           Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getCode() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE lookup_report_type SET description = ?, default_item = ?, "
        + " " + DatabaseUtils.addQuotes(db, "level") + " = ?, enabled = ?, "
        + DatabaseUtils.addQuotes(db,"constant")+" = ?, "
        + "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " "
        + "WHERE code = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, this.getDefaultItem());
    pst.setInt(++i, this.getLevel());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getConstant());
    pst.setInt(++i, this.getCode());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of the Parameter
   * @return Description of the Return Value
   * @exception SQLException
   *              Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM lookup_report_type WHERE code = ? ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, code);
    pst.execute();
    pst.close();
  }
  
}
