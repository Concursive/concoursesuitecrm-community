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
package org.aspcfs.modules.service.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A SyncClient represents a uniquely identifiable system that is performing
 * synchronization with the server. The server maintains specific information
 * about clients as well.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created April 10, 2002
 */
public class SyncClient extends GenericBean {

  private int id = -1;
  private String type = null;
  private String version = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp anchor = null;
  private boolean enabled = false;
  private String code = null;

  /**
   * Constructor for the SyncClient object
   */
  public SyncClient() {
  }

  /**
   * Constructor for the SyncClient object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  public SyncClient(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  /**
   * Constructor for the SyncClient object
   *
   * @param db       Description of Parameter
   * @param clientId Description of Parameter
   * @throws SQLException Description of Exception
   */
  public SyncClient(Connection db, int clientId) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SyncClient-> Looking up: " + clientId);
    }
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
            "FROM sync_client " +
            "WHERE client_id = ? ");
    pst = db.prepareStatement(sql.toString());
    pst.setInt(1, clientId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Sync Client record not found.");
    }
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean checkNormalSync(Connection db) throws SQLException {
    boolean result = false;
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
            "FROM sync_client " +
            "WHERE client_id = ? ");
    if (anchor == null) {
      sql.append("AND anchor is null ");
    } else {
      sql.append("AND anchor = ? ");
    }
    pst = db.prepareStatement(sql.toString());
    pst.setInt(1, id);
    if (anchor != null) {
      pst.setTimestamp(2, anchor);
    }
    rs = pst.executeQuery();
    if (rs.next()) {
      result = true;
    } else {
      result = false;
    }
    rs.close();
    pst.close();
    return result;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateSyncAnchor(Connection db) throws SQLException {
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE sync_client " +
            "SET anchor = ? " +
            "WHERE client_id = ? ");
    pst = db.prepareStatement(sql.toString());
    pst.setTimestamp(1, anchor);
    pst.setInt(2, id);
    pst.executeUpdate();
    pst.close();
    return true;
  }

  /**
   * Sets the id attribute of the SyncClient object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }

  /**
   * Sets the id attribute of the SyncClient object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }

  /**
   * Sets the type attribute of the SyncClient object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }

  /**
   * Sets the version attribute of the SyncClient object
   *
   * @param tmp The new version value
   */
  public void setVersion(String tmp) {
    this.version = tmp;
  }

  /**
   * Sets the entered attribute of the SyncClient object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }

  /**
   * Sets the enteredBy attribute of the SyncClient object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }

  /**
   * Sets the enteredBy attribute of the SyncClient object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }

  /**
   * Sets the modified attribute of the SyncClient object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }

  /**
   * Sets the modifiedBy attribute of the SyncClient object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }

  /**
   * Sets the modifiedBy attribute of the SyncClient object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }

  /**
   * Sets the anchor attribute of the SyncClient object
   *
   * @param tmp The new anchor value
   */
  public void setAnchor(java.sql.Timestamp tmp) {
    this.anchor = tmp;
  }

  /**
   * Gets the id attribute of the SyncClient object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the type attribute of the SyncClient object
   *
   * @return The type value
   */
  public String getType() {
    return type;
  }

  /**
   * Gets the version attribute of the SyncClient object
   *
   * @return The version value
   */
  public String getVersion() {
    return version;
  }

  /**
   * Gets the entered attribute of the SyncClient object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }

  /**
   * Gets the enteredBy attribute of the SyncClient object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }

  /**
   * Gets the modified attribute of the SyncClient object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }

  /**
   * Gets the modifiedBy attribute of the SyncClient object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }

  /**
   * Gets the anchor attribute of the SyncClient object
   *
   * @return The anchor value
   */
  public java.sql.Timestamp getAnchor() {
    return anchor;
  }

  /**
   * Gets the enabled attribute of the SyncClient object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }

  /**
   * Sets the enabled attribute of the SyncClient object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }

  /**
   * Sets the enabled attribute of the SyncClient object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the code attribute of the SyncClient object
   *
   * @return The code value
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets the code attribute of the SyncClient object
   *
   * @param tmp The new code value
   */
  public void setCode(String tmp) {
    this.code = tmp;
  }

  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "sync_client_client_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO sync_client " +
            "(" + (id > -1 ? "client_id, " : "") + "" + DatabaseUtils.addQuotes(db, "type")+ ", version, enabled, code, enteredby, modifiedby) " +
            "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, type);
    pst.setString(++i, version);
    pst.setBoolean(++i, enabled);
    pst.setString(++i, code);
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "sync_client_client_id_seq", id);
    return true;
  }

  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID was not specified");
    }
    boolean commit = db.getAutoCommit();

    try {
      if (commit) {
        db.setAutoCommit(false);
      }

      PreparedStatement pst = null;
      //Delete related records (mappings)

      //Delete the record
      int recordCount = 0;
      pst = db.prepareStatement(
          "DELETE FROM sync_client " +
              "WHERE client_id = ? ");
      pst.setInt(1, id);
      recordCount = pst.executeUpdate();
      pst.close();

      if (recordCount == 0) {
        errors.put(
            "actionError",
            "Sync Client could not be deleted because it no longer exists.");
        return false;
      } else {
        pst = db.prepareStatement(
            "DELETE FROM sync_map " +
                "WHERE client_id = ? ");
        pst.setInt(1, id);
        pst.executeUpdate();
        pst.close();

        pst = db.prepareStatement(
            "DELETE FROM sync_conflict_log " +
                "WHERE client_id = ? ");
        pst.setInt(1, id);
        pst.executeUpdate();
        pst.close();

        pst = db.prepareStatement(
            "DELETE FROM sync_log " +
                "WHERE client_id = ? ");
        pst.setInt(1, id);
        pst.executeUpdate();
        pst.close();

        pst = db.prepareStatement(
            "DELETE FROM process_log " +
                "WHERE client_id = ? ");
        pst.setInt(1, id);
        pst.executeUpdate();
        pst.close();

        if (commit) {
          db.commit();
        }
        return true;
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
  }

  /**
   * Description of the Method
   *
   * @param db      Description of Parameter
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Call ID was not specified");
    }

    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE sync_client " +
            "SET " + DatabaseUtils.addQuotes(db, "type")+ " = ?, version = ?, code = ?, modifiedby = ?, " +
            "enabled = ?, " +
            "modified = CURRENT_TIMESTAMP " +
            "WHERE client_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, type);
    pst.setString(++i, version);
    pst.setString(++i, code);
    pst.setInt(++i, modifiedBy);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("client_id");
    type = rs.getString("type");
    version = rs.getString("version");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    anchor = rs.getTimestamp("anchor");
    enabled = rs.getBoolean("enabled");
    code = rs.getString("code");
  }

}
