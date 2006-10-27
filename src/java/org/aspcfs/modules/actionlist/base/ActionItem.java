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
package org.aspcfs.modules.actionlist.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.text.DateFormat;

/**
 * Item on a action list
 *
 * @author akhi_m
 * @created April 24, 2003
 */
public class ActionItem extends GenericBean {
  /**
   * Description of the Field
   */
  public static int DONE = 1;
  private int id = -1;
  private int actionId = -1;
  private int linkItemId = -1;
  private java.sql.Timestamp completeDate = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private boolean enabled = true;
  private boolean complete = false;


  /**
   * Sets the actionId attribute of the ActionItem object
   *
   * @param tmp The new actionId value
   */
  public void setActionId(String tmp) {
    this.actionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the linkItemId attribute of the ActionItem object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   * Sets the completeDate attribute of the ActionItem object
   *
   * @param tmp The new completeDate value
   */
  public void setCompleteDate(String tmp) {
    this.completeDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the ActionItem object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the ActionItem object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the ActionItem object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the entered attribute of the ActionItem object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enabled attribute of the ActionItem object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Constructor for the ActionItem object
   */
  public ActionItem() {
  }


  /**
   * Constructor for the ActionItem object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public ActionItem(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the ActionItem object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public ActionItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the ActionItem object
   *
   * @param id The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * Sets the actionId attribute of the ActionItem object
   *
   * @param actionId The new actionId value
   */
  public void setActionId(int actionId) {
    this.actionId = actionId;
  }


  /**
   * Sets the linkItemId attribute of the ActionItem object
   *
   * @param linkItemId The new linkItemId value
   */
  public void setLinkItemId(int linkItemId) {
    this.linkItemId = linkItemId;
  }


  /**
   * Sets the enteredBy attribute of the ActionItem object
   *
   * @param enteredBy The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   * Sets the modifiedBy attribute of the ActionItem object
   *
   * @param modifiedBy The new modifiedBy value
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   * Sets the modified attribute of the ActionItem object
   *
   * @param modified The new modified value
   */
  public void setModified(java.sql.Timestamp modified) {
    this.modified = modified;
  }


  /**
   * Sets the entered attribute of the ActionItem object
   *
   * @param entered The new entered value
   */
  public void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
  }


  /**
   * Sets the enabled attribute of the ActionItem object
   *
   * @param enabled The new enabled value
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }


  /**
   * Sets the complete attribute of the ActionItem object
   *
   * @param complete The new complete value
   */
  public void setComplete(boolean complete) {
    this.complete = complete;
  }


  /**
   * Sets the complete attribute of the ActionItem object
   *
   * @param complete The new complete value
   */
  public void setComplete(String complete) {
    this.complete = DatabaseUtils.parseBoolean(complete);
  }


  /**
   * Sets the completeDate attribute of the ActionItem object
   *
   * @param completeDate The new completeDate value
   */
  public void setCompleteDate(java.sql.Timestamp completeDate) {
    this.completeDate = completeDate;
  }


  /**
   * Gets the completeDateString attribute of the ActionItem object
   *
   * @return The completeDateString value
   */
  public String getCompleteDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(completeDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the completeDate attribute of the ActionItem object
   *
   * @return The completeDate value
   */
  public java.sql.Timestamp getCompleteDate() {
    return completeDate;
  }


  /**
   * Gets the complete attribute of the ActionItem object
   *
   * @return The complete value
   */
  public boolean getComplete() {
    return complete;
  }


  /**
   * Gets the id attribute of the ActionItem object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the actionId attribute of the ActionItem object
   *
   * @return The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   * Gets the linkItemId attribute of the ActionItem object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Gets the enteredBy attribute of the ActionItem object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modifiedBy attribute of the ActionItem object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the modified attribute of the ActionItem object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedString attribute of the ActionItem object
   *
   * @return The modifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the entered attribute of the ActionItem object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enabled attribute of the ActionItem object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Id not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT ai.item_id, ai.action_id, ai.link_item_id, ai.completedate, " +
            "ai.enteredby, ai.entered, ai.modifiedby, ai.modified, ai.enabled " +
            "FROM action_item ai " +
            "WHERE ai.item_id = ? ");
    int i = 0;
    pst.setInt(++i, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("ActionItem Id not found");
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      int i = 0;
      id = DatabaseUtils.getNextSeq(db, "action_item_code_seq");
      PreparedStatement pst = null;
      pst = db.prepareStatement(
          "INSERT INTO action_item " +
              "(" + (id > -1 ? "item_id," : "") + "action_id, link_item_id, enteredby, modifiedby, enabled) " +
              "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ? ) ");
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, this.getActionId());
      pst.setInt(++i, this.getLinkItemId());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.setBoolean(++i, this.getEnabled());
      pst.execute();
      this.id = DatabaseUtils.getCurrVal(db, "action_item_code_seq", id);
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   * Mark the item complete
   *
   * @param db       Description of the Parameter
   * @param complete Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int updateStatus(Connection db, boolean complete) throws SQLException {
    if (id == -1) {
      throw new SQLException("Id not specified");
    }
    int i = 0;
    int count = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE action_item " +
              "SET completedate = ? " +
              "WHERE item_id = ? ");
      if (this.getCompleteDate() != null && complete) {
        pst.setTimestamp(++i, this.getCompleteDate());
      } else if (complete && this.getCompleteDate() == null) {
        pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
      } else {
        pst.setTimestamp(++i, null);
      }
      pst.setInt(++i, this.getId());
      count = pst.executeUpdate();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return count;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("item_id");
    actionId = rs.getInt("action_id");
    linkItemId = rs.getInt("link_item_id");
    completeDate = rs.getTimestamp("completedate");
    if (!rs.wasNull()) {
      complete = true;
    }
    enteredBy = rs.getInt("enteredby");
    entered = rs.getTimestamp("entered");
    modifiedBy = rs.getInt("modifiedby");
    modified = rs.getTimestamp("modified");
    enabled = rs.getBoolean("enabled");
  }
}

