//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.accounts.base;

import java.util.StringTokenizer;
import java.sql.*;
import org.aspcfs.utils.DateUtils;
import com.darkhorseventures.database.Connection;
import org.aspcfs.utils.DatabaseUtils;
/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    January 13, 2003
 *@version    $Id$
 */
public class AccountTypeLevel {

  int orgId = -1;
  int typeId = -1;
  int level = -1;
  java.sql.Timestamp entered = null;
  java.sql.Timestamp modified = null;


  /**
   *  Constructor for the AccountTypeLevel object
   */
  public AccountTypeLevel() { }


  /**
   *  Constructor for the AccountTypeLevel object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public AccountTypeLevel(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   *  Constructor for the AccountTypeLevel object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public AccountTypeLevel(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Gets the orgId attribute of the AccountTypeLevel object
   *
   *@return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Sets the orgId attribute of the AccountTypeLevel object
   *
   *@param  orgId  The new orgId value
   */
  public void setOrgId(int orgId) {
    this.orgId = orgId;
  }


  /**
   *  Sets the orgId attribute of the AccountTypeLevel object
   *
   *@param  orgId  The new orgId value
   */
  public void setOrgId(String orgId) {
    this.orgId = Integer.parseInt(orgId);
  }


  /**
   *  Sets the typeId attribute of the AccountTypeLevel object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the AccountTypeLevel object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the level attribute of the AccountTypeLevel object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the AccountTypeLevel object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the AccountTypeLevel object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the AccountTypeLevel object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the AccountTypeLevel object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the AccountTypeLevel object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Gets the typeId attribute of the AccountTypeLevel object
   *
   *@return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the level attribute of the AccountTypeLevel object
   *
   *@return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the entered attribute of the AccountTypeLevel object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the AccountTypeLevel object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Account Type Level entry not found.");
    }

    PreparedStatement pst = db.prepareStatement("SELECT a.* " +
        "FROM account_type_levels a " +
        "WHERE a.org_id = ? and a.type_id = ? ");
    pst.setInt(1, orgId);
    pst.setInt(2, typeId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      throw new SQLException("Account Type Level entry not found.");
    }
    rs.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {

    StringBuffer sql = new StringBuffer();

    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO account_type_levels (org_id, type_id, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("level) ");
      sql.append("VALUES (?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
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

      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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

}

