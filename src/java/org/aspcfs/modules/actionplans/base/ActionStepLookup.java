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
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    August 31, 2005
 */
public class ActionStepLookup extends GenericBean {
  private int id = -1;
  private int stepId = -1;
  private String description = null;
  private boolean defaultItem = false;
  private int level = 0;
  private boolean enabled = true;
  protected Timestamp entered = null;
  protected Timestamp modified = null;


  /**
   *  Gets the entered attribute of the ActionStepLookup object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the ActionStepLookup object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ActionStepLookup object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the id attribute of the ActionStepLookup object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ActionStepLookup object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActionStepLookup object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the stepId attribute of the ActionStepLookup object
   *
   * @return    The stepId value
   */
  public int getStepId() {
    return stepId;
  }


  /**
   *  Sets the stepId attribute of the ActionStepLookup object
   *
   * @param  tmp  The new stepId value
   */
  public void setStepId(int tmp) {
    this.stepId = tmp;
  }


  /**
   *  Sets the stepId attribute of the ActionStepLookup object
   *
   * @param  tmp  The new stepId value
   */
  public void setStepId(String tmp) {
    this.stepId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the description attribute of the ActionStepLookup object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the ActionStepLookup object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the defaultItem attribute of the ActionStepLookup object
   *
   * @return    The defaultItem value
   */
  public boolean getDefaultItem() {
    return defaultItem;
  }


  /**
   *  Sets the defaultItem attribute of the ActionStepLookup object
   *
   * @param  tmp  The new defaultItem value
   */
  public void setDefaultItem(boolean tmp) {
    this.defaultItem = tmp;
  }


  /**
   *  Sets the defaultItem attribute of the ActionStepLookup object
   *
   * @param  tmp  The new defaultItem value
   */
  public void setDefaultItem(String tmp) {
    this.defaultItem = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the level attribute of the ActionStepLookup object
   *
   * @return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Sets the level attribute of the ActionStepLookup object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the ActionStepLookup object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Gets the enabled attribute of the ActionStepLookup object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the enabled attribute of the ActionStepLookup object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ActionStepLookup object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * @return the modified
   */
  public Timestamp getModified() {
    return modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(Timestamp modified) {
    this.modified = modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(String modified) {
    this.modified = DatabaseUtils.parseTimestamp(modified);
  }

  /**
   *  Constructor for the ActionStepLookup object
   */
  public ActionStepLookup() { }


  /**
   *  Constructor for the ActionStepLookup object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public ActionStepLookup(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ActionStepLookup object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public ActionStepLookup(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  id             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Step Lookup ID specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT asl.* " +
        "FROM action_step_lookup asl " +
        "WHERE asl.code = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    if (this.getId() == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("code");
    stepId = rs.getInt("step_id");
    description = rs.getString("description");
    defaultItem = rs.getBoolean("default_item");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "action_step_lookup_code_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO action_step_lookup " +
        "(" + (id > -1 ? "code, " : "") +
        "entered, modified, " +
        "step_id, description, default_item, " + DatabaseUtils.addQuotes(db, "level") + ", enabled) " +
        "VALUES (" + (id > -1 ? "?, " : "") +
        (entered != null ? "?, " : (DatabaseUtils.getCurrentTimestamp(db) + ", ")) +
        (modified != null ? "?, " : (DatabaseUtils.getCurrentTimestamp(db) + ", ")) +
        "?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (entered != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, stepId);
    pst.setString(++i, description);
    pst.setBoolean(++i, defaultItem);
    pst.setInt(++i, level);
    pst.setBoolean(++i, enabled);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "action_step_lookup_code_seq", id);
    return true;
  }

}

