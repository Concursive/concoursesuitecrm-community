//Copyright 2002 Dark Horse Ventures
package org.aspcfs.modules.service.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

/**
 *  Everytime a client uses the sync API, the action is logged. Each action
 *  is made up of one or more transaction requests that result in a success or
 *  failure.
 *
 *@author     matt rajkowski
 *@created    October 25, 2002
 *@version    $Id$
 */
public class SyncTransactionLog extends ArrayList {

  private int id = -1;
  private int systemId = -1;
  private int clientId = -1;
  private String ip = null;
  private java.sql.Timestamp entered = null;


  /**
   *  Constructor for the SyncTransactionLog object
   */
  public SyncTransactionLog() { }


  /**
   *  Constructor for the SyncTransactionLog object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public SyncTransactionLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the SyncTransactionLog object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public SyncTransactionLog(Connection db, int id) throws SQLException {
    queryRecord(db, id);
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
      throw new SQLException("Invalid Transaction Log ID");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT sl.* " +
        "FROM sync_log sl " +
        "WHERE sl.log_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Sync Transaction Log not found");
    }
    rs.close();
    pst.close();
  }


  /**
   *  Sets the id attribute of the SyncTransactionLog object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the SyncTransactionLog object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the systemId attribute of the SyncTransactionLog object
   *
   *@param  tmp  The new systemId value
   */
  public void setSystemId(int tmp) {
    this.systemId = tmp;
  }


  /**
   *  Sets the systemId attribute of the SyncTransactionLog object
   *
   *@param  tmp  The new systemId value
   */
  public void setSystemId(String tmp) {
    this.systemId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the clientId attribute of the SyncTransactionLog object
   *
   *@param  tmp  The new clientId value
   */
  public void setClientId(int tmp) {
    this.clientId = tmp;
  }


  /**
   *  Sets the clientId attribute of the SyncTransactionLog object
   *
   *@param  tmp  The new clientId value
   */
  public void setClientId(String tmp) {
    this.clientId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ip attribute of the SyncTransactionLog object
   *
   *@param  tmp  The new ip value
   */
  public void setIp(String tmp) {
    this.ip = tmp;
  }


  /**
   *  Sets the entered attribute of the SyncTransactionLog object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the SyncTransactionLog object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Gets the id attribute of the SyncTransactionLog object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the systemId attribute of the SyncTransactionLog object
   *
   *@return    The systemId value
   */
  public int getSystemId() {
    return systemId;
  }


  /**
   *  Gets the clientId attribute of the SyncTransactionLog object
   *
   *@return    The clientId value
   */
  public int getClientId() {
    return clientId;
  }


  /**
   *  Gets the ip attribute of the SyncTransactionLog object
   *
   *@return    The ip value
   */
  public String getIp() {
    return ip;
  }


  /**
   *  Gets the entered attribute of the SyncTransactionLog object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredString attribute of the SyncTransactionLog object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    if (systemId == -1 || clientId == -1) {
      throw new SQLException("Log Entry must be associated with a System and Client");
    }

    StringBuffer sql = new StringBuffer();
    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO sync_log (system_id, client_id, ip ");
      if (entered != null) {
        sql.append(", entered ");
      }
      sql.append(") ");
      sql.append("VALUES (?, ?, ? ");
      if (entered != null) {
        sql.append(", ? ");
      }
      sql.append(") ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, systemId);
      pst.setInt(++i, clientId);
      pst.setString(++i, ip);
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "sync_log_log_id_seq");

      if (this.size() > 0) {
        Iterator items = this.iterator();
        while (items.hasNext()) {
          SyncTransactionLogItem thisItem = (SyncTransactionLogItem) items.next();
          thisItem.insert(db);
        }
      }

      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Access Log ID not specified.");
    }

    try {
      db.setAutoCommit(false);

      if (this.size() > 0) {
        Iterator items = this.iterator();
        while (items.hasNext()) {
          SyncTransactionLogItem thisItem = (SyncTransactionLogItem) items.next();
          thisItem.delete(db);
        }
      }

      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM sync_log " +
          "WHERE log_id = ? ");
      pst.setInt(1, id);
      pst.executeUpdate();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
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
    id = rs.getInt("log_id");
    systemId = rs.getInt("system_id");
    clientId = rs.getInt("client_id");
    ip = rs.getString("ip");
    entered = rs.getTimestamp("entered");
  }

}

