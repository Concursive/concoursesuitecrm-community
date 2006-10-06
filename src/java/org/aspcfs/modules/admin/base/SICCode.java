/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.admin.base;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author holub
 * @version $Id: Exp $
 * @created Sep 15, 2006
 */
public class SICCode {

  private int code = -1;
  private String description = null;
  private int level = -1;
  protected boolean enabled = true;
  protected boolean defaultItem = false;
  private String constantId = null;

  /**
   * @return the constantId
   */
  public String getConstantId() {
    return constantId;
  }

  public int getId() {
    return code;
  }

  /**
   * @return the code
   */
  public int getCode() {
    return code;
  }

  /**
   * @return the defaultItem
   */
  public boolean isDefaultItem() {
    return defaultItem;
  }

  /**
   * @return the defaultItem
   */
  public boolean getDefaultItem() {
    return defaultItem;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the enabled
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * @return the enabled
   */
  public boolean getEnabled() {
    return enabled;
  }

  /**
   * @return the level
   */
  public int getLevel() {
    return level;
  }

  /**
   * @param code the code to set
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * @param defaultItem the defaultItem to set
   */
  public void setDefaultItem(boolean defaultItem) {
    this.defaultItem = defaultItem;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @param enabled the enabled to set
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * @param constantId the constantId to set
   */
  public void setConstantId(String constantId) {
    this.constantId = constantId;
  }

  /**
   * Constructor for the SICCode object
   */
  public SICCode() {
  }

  /**
   * Constructor for the SICCode object
   *
   * @param rs Description of the Parameter
   * @throws java.sql.SQLException Description of the Exception
   */
  public SICCode(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  /**
   * Constructor for the SICCode object
   *
   * @param db   Description of the Parameter
   * @param code Description of the Parameter
   * @throws java.sql.SQLException Description of the Exception
   */
  public SICCode(Connection db, int code) throws SQLException {
    queryRecord(db, code);
  }

  /**
   * Build the SICCode record
   *
   * @param rs Description of the Parameter
   * @throws java.sql.SQLException Description of the Exception
   */
  public void build(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  public boolean update(Connection db) throws SQLException {
    return insert(db);
  }

  public boolean insert(Connection db) throws SQLException {
    int id = DatabaseUtils.getNextSeq(db, "lookup_sic_codes");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO lookup_sic_codes " +
            "(" + (id > -1 ? "code, " : "") + "description, default_item, " + DatabaseUtils.addQuotes(db, "level") + ", enabled, constant_id) " +
            "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?) ");
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, this.getDefaultItem());
    pst.setInt(++i, this.getLevel());
    pst.setBoolean(++i, this.isEnabled());
    pst.setString(++i, this.getConstantId());
    pst.execute();
    pst.close();
    code = DatabaseUtils.getCurrVal(db, "lookup_sic_codes_code_seq", id);
    return true;
  }

  public void buildRecord(ResultSet rs) throws SQLException {
    code = rs.getInt("code");
    description = rs.getString("description");
    defaultItem = rs.getBoolean("default_item");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
    constantId = rs.getString("constant_id");
    if (!(this.isEnabled())) {
      description += " (X)";
    }
  }

  public void queryRecord(Connection db, int tmpCode) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "SICCode-> Retrieving ID: " + tmpCode + " from lookup_sic_codes ");
    }
    this.setCode(tmpCode);
    PreparedStatement pst = db.prepareStatement(
        "SELECT code, description, default_item, " + DatabaseUtils.addQuotes(db, "level") + ", enabled, constant_id " +
            "FROM lookup_sic_codes " +
            "WHERE code = ? ");
    pst.setInt(1, getCode());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      build(rs);
    }
    rs.close();
    pst.close();
    if (code < 0) {
      throw new java.sql.SQLException("ID not found");
    }
  }
}
