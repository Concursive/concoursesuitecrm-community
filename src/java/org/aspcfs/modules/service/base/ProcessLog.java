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

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

/**
 *  Represents the output of a process that can be inserted in a database
 *
 *@author     matt rajkowski
 *@created    October 23, 2002
 *@version    $Id$
 */
public class ProcessLog extends GenericBean {

  private int id = -1;
  private int systemId = -1;
  private int clientId = -1;
  private String name = null;
  private String version = null;
  private int status = -1;
  private String message = null;
  private java.sql.Timestamp entered = null;


  /**
   *  Constructor for the ProcessLog object
   */
  public ProcessLog() { }


  /**
   *  Constructor for the ProcessLog object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ProcessLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ProcessLog object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ProcessLog(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Sets the id attribute of the ProcessLog object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the systemId attribute of the ProcessLog object
   *
   *@param  tmp  The new systemId value
   */
  public void setSystemId(int tmp) {
    this.systemId = tmp;
  }


  /**
   *  Sets the systemId attribute of the ProcessLog object
   *
   *@param  tmp  The new systemId value
   */
  public void setSystemId(String tmp) {
    this.systemId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the clientId attribute of the ProcessLog object
   *
   *@param  tmp  The new clientId value
   */
  public void setClientId(int tmp) {
    this.clientId = tmp;
  }


  /**
   *  Sets the clientId attribute of the ProcessLog object
   *
   *@param  tmp  The new clientId value
   */
  public void setClientId(String tmp) {
    this.clientId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the ProcessLog object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the version attribute of the ProcessLog object
   *
   *@param  tmp  The new version value
   */
  public void setVersion(String tmp) {
    this.version = tmp;
  }


  /**
   *  Sets the status attribute of the ProcessLog object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(int tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the status attribute of the ProcessLog object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = Integer.parseInt(tmp);
  }


  /**
   *  Sets the message attribute of the ProcessLog object
   *
   *@param  tmp  The new message value
   */
  public void setMessage(String tmp) {
    this.message = tmp;
  }


  /**
   *  Sets the entered attribute of the ProcessLog object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ProcessLog object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Gets the id attribute of the ProcessLog object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the systemId attribute of the ProcessLog object
   *
   *@return    The systemId value
   */
  public int getSystemId() {
    return systemId;
  }


  /**
   *  Gets the clientId attribute of the ProcessLog object
   *
   *@return    The clientId value
   */
  public int getClientId() {
    return clientId;
  }


  /**
   *  Gets the name attribute of the ProcessLog object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the version attribute of the ProcessLog object
   *
   *@return    The version value
   */
  public String getVersion() {
    return version;
  }


  /**
   *  Gets the status attribute of the ProcessLog object
   *
   *@return    The status value
   */
  public int getStatus() {
    return status;
  }


  /**
   *  Gets the message attribute of the ProcessLog object
   *
   *@return    The message value
   */
  public String getMessage() {
    return message;
  }


  /**
   *  Gets the entered attribute of the ProcessLog object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredString attribute of the ProcessLog object
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
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Process Log ID");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT p.* " +
        "FROM process_log p " +
        "WHERE p.process_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Process Log not found");
    }
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
      sql.append(
          "INSERT INTO process_log (system_id, client_id, process_name, process_version, status, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      sql.append("message ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      sql.append("?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, systemId);
      pst.setInt(++i, clientId);
      pst.setString(++i, name);
      pst.setString(++i, version);
      pst.setInt(++i, status);
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      pst.setString(++i, message);
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "process_log_process_id_seq");
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
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
      throw new SQLException("Process Log ID not specified.");
    }

    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM process_log " +
        "WHERE process_id = ? ");
    pst.setInt(1, this.getId());
    pst.executeUpdate();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("process_id");
    systemId = rs.getInt("system_id");
    clientId = rs.getInt("client_id");
    entered = rs.getTimestamp("entered");
    name = rs.getString("process_name");
    version = rs.getString("process_version");
    status = rs.getInt("status");
    message = rs.getString("message");
  }
}

