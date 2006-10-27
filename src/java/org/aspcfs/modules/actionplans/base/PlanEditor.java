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
package org.aspcfs.modules.actionplans.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created September 9, 2005
 */
public class PlanEditor extends GenericBean {
  // fields
  protected int id = -1;
  private int moduleId = -1;
  private int categoryId = -1;
  private int constantId = -1;
  private int level = -1;
  private String description = null;
  private java.sql.Timestamp entered = null;
  // Helper properties for working with editors
  private SystemStatus systemStatus = null;


  /**
   * Gets the entered attribute of the PlanEditor object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Sets the entered attribute of the PlanEditor object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the PlanEditor object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Constructor for the PlanEditor object
   */
  public PlanEditor() {
  }


  /**
   * Constructor for the PlanEditor object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public PlanEditor(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the PlanEditor object
   *
   * @param db         Description of the Parameter
   * @param constantId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public PlanEditor(Connection db, int constantId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT apel.* " +
            "FROM action_plan_editor_lookup apel " +
            "LEFT JOIN action_plan_constants apc ON (apel.constant_id = apc.map_id) " +
            "WHERE apc.constant_id = ? ");
    pst.setInt(1, constantId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    moduleId = rs.getInt("module_id");
    constantId = rs.getInt("constant_id");
    level = rs.getInt("level");
    description = rs.getString("description");
    entered = rs.getTimestamp("entered");
    categoryId = rs.getInt("category_id");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "action_plan_editor_loo_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO action_plan_editor_lookup " +
            "(" + (id > -1 ? "id, " : "") + "module_id, constant_id, " + DatabaseUtils.addQuotes(db, "level") +
            ", description, category_id) " +
            "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, moduleId);
    pst.setInt(++i, constantId);
    pst.setInt(++i, level);
    pst.setString(++i, description);
    pst.setInt(++i, categoryId);
    pst.executeUpdate();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "action_plan_editor_loo_id_seq", id);
  }


  /*
   *  Get and Set methods
   */
  /**
   * Gets the id attribute of the PlanEditor object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the PlanEditor object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the PlanEditor object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the moduleId attribute of the PlanEditor object
   *
   * @return The moduleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   * Sets the moduleId attribute of the PlanEditor object
   *
   * @param tmp The new moduleId value
   */
  public void setModuleId(int tmp) {
    this.moduleId = tmp;
  }


  /**
   * Sets the moduleId attribute of the PlanEditor object
   *
   * @param tmp The new moduleId value
   */
  public void setModuleId(String tmp) {
    this.moduleId = Integer.parseInt(tmp);
  }


  /**
   * Gets the categoryId attribute of the PlanEditor object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the categoryId attribute of the PlanEditor object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the PlanEditor object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Gets the constantId attribute of the PlanEditor object
   *
   * @return The constantId value
   */
  public int getConstantId() {
    return constantId;
  }


  /**
   * Sets the constantId attribute of the PlanEditor object
   *
   * @param tmp The new constantId value
   */
  public void setConstantId(int tmp) {
    this.constantId = tmp;
  }


  /**
   * Sets the constantId attribute of the PlanEditor object
   *
   * @param tmp The new constantId value
   */
  public void setConstantId(String tmp) {
    this.constantId = Integer.parseInt(tmp);
  }


  /**
   * Gets the level attribute of the PlanEditor object
   *
   * @return The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Sets the level attribute of the PlanEditor object
   *
   * @param tmp The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the level attribute of the PlanEditor object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   * Gets the description attribute of the PlanEditor object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Sets the description attribute of the PlanEditor object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Gets the systemStatus attribute of the PlanEditor object
   *
   * @return The systemStatus value
   */
  public SystemStatus getSystemStatus() {
    return systemStatus;
  }


  /**
   * Sets the systemStatus attribute of the PlanEditor object
   *
   * @param tmp The new systemStatus value
   */
  public void setSystemStatus(SystemStatus tmp) {
    this.systemStatus = tmp;
  }

}

