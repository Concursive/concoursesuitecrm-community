//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  A SyncClient represents a uniquely identifiable system that is
 *  performing synchronization with the server.
 *  The server maintains specific information about clients as well.  
 *
 *@author     matt
 *@created    April 10, 2002
 *@version    $Id$
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

  /**
   *  Constructor for the SyncClient object
   */
  public SyncClient() { }


  /**
   *  Constructor for the SyncClient object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public SyncClient(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the SyncClient object
   *
   *@param  db                Description of Parameter
   *@param  clientId          Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public SyncClient(Connection db, int clientId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM sync_client " +
        "WHERE client_id = ? ");
    pst = db.prepareStatement(sql.toString());
    pst.setInt(1, id);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Call record not found.");
    }
    rs.close();
    pst.close();
  }
  
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
   *  Sets the id attribute of the SyncClient object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the SyncClient object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the type attribute of the SyncClient object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the version attribute of the SyncClient object
   *
   *@param  tmp  The new version value
   */
  public void setVersion(String tmp) {
    this.version = tmp;
  }


  /**
   *  Sets the entered attribute of the SyncClient object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the SyncClient object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the SyncClient object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the SyncClient object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the SyncClient object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the SyncClient object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }

  public void setAnchor(java.sql.Timestamp tmp) { this.anchor = tmp; }


  /**
   *  Gets the id attribute of the SyncClient object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the type attribute of the SyncClient object
   *
   *@return    The type value
   */
  public String getType() {
    return type;
  }


  /**
   *  Gets the version attribute of the SyncClient object
   *
   *@return    The version value
   */
  public String getVersion() {
    return version;
  }


  /**
   *  Gets the entered attribute of the SyncClient object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the SyncClient object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the SyncClient object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the SyncClient object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }
  
  public java.sql.Timestamp getAnchor() { return anchor; }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    String sql =
        "INSERT INTO sync_client " +
        "(type, version, enteredby, modifiedby) " +
        "VALUES (?, ?, ?, ?) ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setString(++i, type);
    pst.setString(++i, version);
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getEnteredBy());
    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "sync_client_client_id_seq");
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID was not specified");
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
      errors.put("actionError", "Sync Client could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
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
        "SET type = ?, version = ?, modifiedby = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE client_id = ? " +
        "AND modified = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, type);
    pst.setString(++i, version);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, id);
    pst.setTimestamp(++i, this.getModified());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
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
  }

}

