/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.actionlist.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.text.DateFormat;

/**
 * Description of the Class
 *
 * @author akhi_m
 * @version $Id$
 * @created April 18, 2003
 */
public class ActionList extends GenericBean {
  private int id = -1;
  private String description = null;
  private int owner = -1;
  private int ownerContactId = -1;
  private java.sql.Timestamp completeDate = null;
  private boolean complete = false;
  private int linkModuleId = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private boolean enabled = true;
  private int total = 0;
  private int totalComplete = 0;


  /**
   * Sets the enteredBy attribute of the ActionList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the ActionList object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the ActionList object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Constructor for the ActionList object
   */
  public ActionList() {
  }


  /**
   * Constructor for the ActionList object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public ActionList(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the ActionList object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public ActionList(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the ActionList object
   *
   * @param id The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * Sets the id attribute of the ActionList object
   *
   * @param id The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   * Sets the description attribute of the ActionList object
   *
   * @param description The new description value
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   * Sets the owner attribute of the ActionList object
   *
   * @param owner The new owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   * Sets the owner attribute of the ActionList object
   *
   * @param owner The new owner value
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   * Sets the completeDate attribute of the ActionList object
   *
   * @param completeDate The new completeDate value
   */
  public void setCompleteDate(java.sql.Timestamp completeDate) {
    this.completeDate = completeDate;
  }


  /**
   * Sets the total attribute of the ActionList object
   *
   * @param total The new total value
   */
  public void setTotal(int total) {
    this.total = total;
  }


  /**
   * Sets the totalComplete attribute of the ActionList object
   *
   * @param totalComplete The new totalComplete value
   */
  public void setTotalComplete(int totalComplete) {
    this.totalComplete = totalComplete;
  }


  /**
   * Gets the total attribute of the ActionList object
   *
   * @return The total value
   */
  public int getTotal() {
    return total;
  }


  /**
   * Gets the totalComplete attribute of the ActionList object
   *
   * @return The totalComplete value
   */
  public int getTotalComplete() {
    return totalComplete;
  }


  /**
   * Gets the completeDateString attribute of the ActionList object
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
   * Sets the linkModuleId attribute of the ActionList object
   *
   * @param linkModuleId The new linkModuleId value
   */
  public void setLinkModuleId(int linkModuleId) {
    this.linkModuleId = linkModuleId;
  }


  /**
   * Sets the linkModuleId attribute of the ActionList object
   *
   * @param linkModuleId The new linkModuleId value
   */
  public void setLinkModuleId(String linkModuleId) {
    this.linkModuleId = Integer.parseInt(linkModuleId);
  }


  /**
   * Sets the enteredBy attribute of the ActionList object
   *
   * @param enteredBy The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   * Sets the modifiedBy attribute of the ActionList object
   *
   * @param modifiedBy The new modifiedBy value
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   * Sets the modified attribute of the ActionList object
   *
   * @param modified The new modified value
   */
  public void setModified(java.sql.Timestamp modified) {
    this.modified = modified;
  }


  /**
   * Sets the modified attribute of the ActionList object
   *
   * @param modified The new modified value
   */
  public void setModified(String modified) {
    this.modified = DatabaseUtils.parseTimestamp(modified);
  }


  /**
   * Sets the complete attribute of the ActionList object
   *
   * @param complete The new complete value
   */
  public void setComplete(boolean complete) {
    this.complete = complete;
  }


  /**
   * Sets the complete attribute of the ActionList object
   *
   * @param complete The new complete value
   */
  public void setComplete(String complete) {
    this.complete = DatabaseUtils.parseBoolean(complete);
  }


  /**
   * Sets the ownerContactId attribute of the ActionList object
   *
   * @param ownerContactId The new ownerContactId value
   */
  public void setOwnerContactId(int ownerContactId) {
    this.ownerContactId = ownerContactId;
  }


  /**
   * Sets the ownerContactId attribute of the ActionList object
   *
   * @param ownerContactId The new ownerContactId value
   */
  public void setOwnerContactId(String ownerContactId) {
    this.ownerContactId = Integer.parseInt(ownerContactId);
  }


  /**
   * Gets the ownerContactId attribute of the ActionList object
   *
   * @return The ownerContactId value
   */
  public int getOwnerContactId() {
    return ownerContactId;
  }


  /**
   * Gets the complete attribute of the ActionList object
   *
   * @return The complete value
   */
  public boolean getComplete() {
    return complete;
  }


  /**
   * Gets the modifiedString attribute of the ActionList object
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
   * Sets the entered attribute of the ActionList object
   *
   * @param entered The new entered value
   */
  public void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
  }


  /**
   * Gets the enteredString attribute of the ActionList object
   *
   * @return The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Sets the enabled attribute of the ActionList object
   *
   * @param enabled The new enabled value
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }


  /**
   * Sets the enabled attribute of the ActionList object
   *
   * @param enabled The new enabled value
   */
  public void setEnabled(String enabled) {
    this.enabled = DatabaseUtils.parseBoolean(enabled);
  }


  /**
   * Gets the id attribute of the ActionList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the description attribute of the ActionList object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the owner attribute of the ActionList object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Gets the completeDate attribute of the ActionList object
   *
   * @return The completeDate value
   */
  public java.sql.Timestamp getCompleteDate() {
    return completeDate;
  }


  /**
   * Gets the linkModuleId attribute of the ActionList object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Gets the enteredBy attribute of the ActionList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modifiedBy attribute of the ActionList object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the modified attribute of the ActionList object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the entered attribute of the ActionList object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enabled attribute of the ActionList object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the ownerValid attribute of the ActionList object
   *
   * @param db Description of the Parameter
   * @return The ownerValid value
   * @throws SQLException Description of the Exception
   */
  public boolean isOwnerValid(Connection db) throws SQLException {
    if (owner == -1 && ownerContactId != -1) {
      Contact thisContact = new Contact(db, ownerContactId);
      this.setOwner(thisContact.getUserId());
    }
    if (owner == -1) {
      return false;
    }
    return true;
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
        "SELECT al.action_id, al.description, al.owner, al.completedate, al.link_module_id, " +
            "al.enteredby, al.entered, al.modifiedby, al.modified, al.enabled " +
            "FROM action_list al " +
            "WHERE action_id = ? ");
    int i = 0;
    pst.setInt(++i, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Action ID not found");
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
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      int i = 0;
      id = DatabaseUtils.getNextSeq(db, "action_list_code_seq");
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO action_list " +
              "(" + (id > -1 ? "action_id, " : "") + "description, owner, link_module_id, enteredby, modifiedby, enabled) " +
              "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ? ) ");
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getOwner());
      pst.setInt(++i, this.getLinkModuleId());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.setBoolean(++i, this.getEnabled());
      pst.execute();
      this.id = DatabaseUtils.getCurrVal(db, "action_list_code_seq", id);
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int count = 0;
    if (id == -1) {
      throw new SQLException("Id not specified");
    }
    try {
      db.setAutoCommit(false);
      ActionList previousList = new ActionList(db, id);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "UPDATE action_list " +
              "SET modifiedby = ?, description = ?, " +
              "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", completedate = ?, owner = ? " +
              "WHERE action_id = ? AND modified " + ((this.getModified() == null) ? "IS NULL " : "= ? "));
      pst.setInt(++i, this.getModifiedBy());
      pst.setString(++i, this.getDescription());
      if (previousList.getCompleteDate() != null && this.getComplete()) {
        pst.setTimestamp(++i, previousList.getCompleteDate());
      } else if (this.getComplete() && previousList.getCompleteDate() == null) {
        pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
      } else {
        pst.setTimestamp(++i, null);
      }
      pst.setInt(++i, this.getOwner());
      pst.setInt(++i, id);
      if (this.getModified() != null) {
        pst.setTimestamp(++i, this.getModified());
      }
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
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Action Id not specified");
    }
    try {
      db.setAutoCommit(false);
      deleteRelationships(db);
      String sql =
          "DELETE from action_list " +
              "WHERE action_id = ? ";
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean deleteRelationships(Connection db) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "DELETE from action_item_log " +
              "WHERE item_id IN (SELECT item_id from action_item where action_id = ? ) ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      
      pst = db.prepareStatement(
          "DELETE from action_item " +
              "WHERE action_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    ResultSet rs = null;
    DependencyList dependencyList = new DependencyList();
    try {
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) as total " +
              "FROM action_item " +
              "WHERE action_id = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int linkcount = rs.getInt("total");
        if (linkcount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("items");
          thisDependency.setCount(linkcount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildTotal(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("Id not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) as total " +
            "FROM action_item " +
            "WHERE action_id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.total = rs.getInt("total");
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildTotalComplete(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("Id not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) as total_complete " +
            "FROM action_item " +
            "WHERE action_id = ? AND completedate IS NOT NULL ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.totalComplete = rs.getInt("total_complete");
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
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("action_id");
    description = rs.getString("description");
    owner = rs.getInt("owner");
    completeDate = rs.getTimestamp("completedate");
    if (!rs.wasNull()) {
      complete = true;
    }
    linkModuleId = rs.getInt("link_module_id");
    enteredBy = rs.getInt("enteredby");
    entered = rs.getTimestamp("entered");
    modifiedBy = rs.getInt("modifiedby");
    modified = rs.getTimestamp("modified");
    enabled = rs.getBoolean("enabled");
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param newOwner Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean reassign(Connection db, int newOwner) throws SQLException {
    int result = -1;
    this.setOwner(newOwner);
    result = this.update(db);
    if (result == -1) {
      return false;
    }
    return true;
  }

}

