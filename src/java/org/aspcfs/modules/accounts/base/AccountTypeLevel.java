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
package org.aspcfs.modules.accounts.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id: AccountTypeLevel.java 12404 2005-08-05 17:37:07Z mrajkowski
 *          $
 * @created January 13, 2003
 */
public class AccountTypeLevel {

  int orgId = -1;
  int typeId = -1;
  int level = -1;
  java.sql.Timestamp entered = null;
  java.sql.Timestamp modified = null;


  /**
   * Constructor for the AccountTypeLevel object
   */
  public AccountTypeLevel() {
  }


  /**
   * Constructor for the AccountTypeLevel object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public AccountTypeLevel(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   * Constructor for the AccountTypeLevel object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public AccountTypeLevel(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the AccountTypeLevel object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public AccountTypeLevel(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Gets the orgId attribute of the AccountTypeLevel object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Sets the orgId attribute of the AccountTypeLevel object
   *
   * @param orgId The new orgId value
   */
  public void setOrgId(int orgId) {
    this.orgId = orgId;
  }


  /**
   * Sets the orgId attribute of the AccountTypeLevel object
   *
   * @param orgId The new orgId value
   */
  public void setOrgId(String orgId) {
    this.orgId = Integer.parseInt(orgId);
  }


  /**
   * Sets the typeId attribute of the AccountTypeLevel object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   * Sets the typeId attribute of the AccountTypeLevel object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   * Sets the level attribute of the AccountTypeLevel object
   *
   * @param tmp The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the level attribute of the AccountTypeLevel object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the AccountTypeLevel object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the modified attribute of the AccountTypeLevel object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the entered attribute of the AccountTypeLevel object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the modified attribute of the AccountTypeLevel object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Gets the typeId attribute of the AccountTypeLevel object
   *
   * @return The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   * Gets the level attribute of the AccountTypeLevel object
   *
   * @return The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Gets the entered attribute of the AccountTypeLevel object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the modified attribute of the AccountTypeLevel object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
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
      throw new SQLException("Account Type Level entry not found.");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT a.* " +
            "FROM account_type_levels a " +
            "WHERE a.org_id = ? and a.type_id = ? ");
    pst.setInt(1, orgId);
    pst.setInt(2, typeId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    
    if (typeId == -1) {
      throw new SQLException("Account Type Level entry not found.");
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
    StringBuffer sql = new StringBuffer();
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      sql.append("INSERT INTO account_type_levels (org_id, type_id, ");
      sql.append("entered, modified, ");
      sql.append(DatabaseUtils.addQuotes(db, "level") + ") ");
      sql.append("VALUES (?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      if (modified != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (orgId > -1) {
        pst.setInt(++i, this.getOrgId());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (typeId > -1) {
        pst.setInt(++i, this.getTypeId());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setInt(++i, this.getLevel());
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
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    orgId = rs.getInt("org_id");
    if (rs.wasNull()) {
      orgId = -1;
    }
    typeId = rs.getInt("type_id");
    if (rs.wasNull()) {
      typeId = -1;
    }
    level = rs.getInt("level");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
  }


  /**
   * Returns all the AccountTypeLevel records from the database
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static ArrayList recordList(Connection db) throws SQLException {
    ArrayList records = new ArrayList();
    PreparedStatement pst = db.prepareStatement(
        "SELECT * FROM account_type_levels ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      AccountTypeLevel atl = new AccountTypeLevel(rs);
      records.add(atl);
    }
    rs.close();
    pst.close();
    return records;
  }
}

