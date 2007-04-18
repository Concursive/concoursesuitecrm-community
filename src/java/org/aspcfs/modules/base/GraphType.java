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
package org.aspcfs.modules.base;

import com.darkhorseventures.framework.beans.GenericBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import org.aspcfs.utils.DatabaseUtils;

/**
 * Represents a GraphType 
 * @author     dharmas
 * @created    Mar 30, 2007
 *
 */
public class GraphType extends GenericBean {

   private int id = -1;
  private String description = null;
  private int level = -1;
  private boolean enabled = false;
  private boolean defaultItem = false;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;



/**
   * Constructor for the GraphType object
   */
  public GraphType() {
  }

  /**
   * Constructor for the GraphType object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public GraphType(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the GraphType object
   *
   * @param db       Description of the Parameter
   * @param statusId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public GraphType(Connection db, int statusId) throws SQLException {
   // queryRecord(db, statusId);
  }


    /**
   * Sets the id attribute of the GraphType object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }

  /**
   * Sets the id attribute of the GraphType object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }

  /**
   * Sets the description attribute of the GraphType object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }

  /**
   * Sets the level attribute of the GraphType object
   *
   * @param tmp The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }

  /**
   * Sets the level attribute of the GraphType object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }

  /**
   * Sets the enabled attribute of the GraphType object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }

  /**
   * Sets the enabled attribute of the GraphType object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the defaultItem attribute of the GraphType object
   *
   * @param tmp The new defaultItem value
   */
  public void setDefaultItem(boolean tmp) {
    this.defaultItem = tmp;
  }

  /**
   * Sets the defaultItem attribute of the GraphType object
   *
   * @param tmp The new defaultItem value
   */
  public void setDefaultItem(String tmp) {
    this.defaultItem = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the entered attribute of the GraphType object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }

  /**
   *  Sets the entered attribute of the GraphType object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }

  /**
   *  Sets the entered attribute of the GraphType object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }
  /**
   *  Gets the modified attribute of the GraphType object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }

  /**
   *  Sets the modified attribute of the GraphType object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }

  /**
   *  Sets the modified attribute of the GraphType object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Gets the id attribute of the GraphType object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the description attribute of the GraphType object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the level attribute of the GraphType object
   *
   * @return The level value
   */
  public int getLevel() {
    return level;
  }

  /**
   * Gets the enabled attribute of the GraphType object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }

  /**
   * Gets the defaultItem attribute of the GraphType object
   *
   * @return The defaultItem value
   */
  public boolean getDefaultItem() {
    return defaultItem;
  }

  /**
   * Populates the GraphType object from a database resultset
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    //lookup_graph_type table
    id = rs.getInt("code");
    description = rs.getString("description");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
    defaultItem = rs.getBoolean("default_item");
    entered = rs.getTimestamp("entered");
    modified =rs.getTimestamp("modified");
  }

}

