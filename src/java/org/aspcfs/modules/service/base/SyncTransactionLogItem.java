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

import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  For each sync API request, this class represents one or more of the
 *  transactions that occurs. An action can result in records so that count can
 *  be stored.
 *
 *@author     matt rajkowski
 *@created    October 25, 2002
 *@version    $Id: SyncTransactionLogItem.java,v 1.3 2003/03/07 14:47:27
 *      mrajkowski Exp $
 */
public class SyncTransactionLogItem {

  private int id = -1;
  private int logId = -1;
  private String referenceId = null;
  private String elementName = null;
  private String action = null;
  private int linkItemId = -1;
  private int statusCode = -1;
  private int recordCount = -1;
  private String message = null;


  /**
   *  Constructor for the SyncTransactionLogItem object
   */
  public SyncTransactionLogItem() { }


  /**
   *  Constructor for the SyncTransactionLogItem object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public SyncTransactionLogItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the SyncTransactionLogItem object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public SyncTransactionLogItem(Connection db, int id) throws SQLException {
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
      throw new SQLException("Invalid Transaction Log Item ID");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT stl.* " +
        "FROM sync_transaction_log stl " +
        "WHERE stl.transaction_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Sync Transaction Log not found");
    }
  }


  /**
   *  Sets the id attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the logId attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new logId value
   */
  public void setLogId(int tmp) {
    this.logId = tmp;
  }


  /**
   *  Sets the logId attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new logId value
   */
  public void setLogId(String tmp) {
    this.logId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the referenceId attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new referenceId value
   */
  public void setReferenceId(String tmp) {
    this.referenceId = tmp;
  }


  /**
   *  Sets the elementName attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new elementName value
   */
  public void setElementName(String tmp) {
    this.elementName = tmp;
  }


  /**
   *  Sets the action attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new action value
   */
  public void setAction(String tmp) {
    this.action = tmp;
  }


  /**
   *  Sets the linkItemId attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the linkItemId attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusCode attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new statusCode value
   */
  public void setStatusCode(int tmp) {
    this.statusCode = tmp;
  }


  /**
   *  Sets the statusCode attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new statusCode value
   */
  public void setStatusCode(String tmp) {
    this.statusCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the recordCount attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new recordCount value
   */
  public void setRecordCount(int tmp) {
    this.recordCount = tmp;
  }


  /**
   *  Sets the recordCount attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new recordCount value
   */
  public void setRecordCount(String tmp) {
    this.recordCount = Integer.parseInt(tmp);
  }


  /**
   *  Sets the message attribute of the SyncTransactionLogItem object
   *
   *@param  tmp  The new message value
   */
  public void setMessage(String tmp) {
    this.message = tmp;
  }


  /**
   *  Gets the id attribute of the SyncTransactionLogItem object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the logId attribute of the SyncTransactionLogItem object
   *
   *@return    The logId value
   */
  public int getLogId() {
    return logId;
  }


  /**
   *  Gets the referenceId attribute of the SyncTransactionLogItem object
   *
   *@return    The referenceId value
   */
  public String getReferenceId() {
    return referenceId;
  }


  /**
   *  Gets the elementName attribute of the SyncTransactionLogItem object
   *
   *@return    The elementName value
   */
  public String getElementName() {
    return elementName;
  }


  /**
   *  Gets the action attribute of the SyncTransactionLogItem object
   *
   *@return    The action value
   */
  public String getAction() {
    return action;
  }


  /**
   *  Gets the linkItemId attribute of the SyncTransactionLogItem object
   *
   *@return    The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   *  Gets the statusCode attribute of the SyncTransactionLogItem object
   *
   *@return    The statusCode value
   */
  public int getStatusCode() {
    return statusCode;
  }


  /**
   *  Gets the recordCount attribute of the SyncTransactionLogItem object
   *
   *@return    The recordCount value
   */
  public int getRecordCount() {
    return recordCount;
  }


  /**
   *  Gets the message attribute of the SyncTransactionLogItem object
   *
   *@return    The message value
   */
  public String getMessage() {
    return message;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    if (logId == -1) {
      throw new SQLException("Log Transaction entry must be associated with a Log");
    }

    String sql =
        "INSERT INTO sync_transaction_log " +
        "(log_id, reference_id, element_name, " +
        "action, link_item_id, status_code, record_count, message) VALUES " +
        "(?, ?, ?, ?, ?, ?, ?, ?)";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, logId);
    pst.setString(++i, referenceId);
    pst.setString(++i, elementName);
    pst.setString(++i, action);
    DatabaseUtils.setInt(pst, ++i, linkItemId);
    DatabaseUtils.setInt(pst, ++i, statusCode);
    DatabaseUtils.setInt(pst, ++i, recordCount);
    pst.setString(++i, message);
    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "sync_transact_transaction_i_seq");
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
      throw new SQLException("Transaction Log ID not specified.");
    }
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM sync_transaction_log " +
        "WHERE transaction_id = ? ");
    pst.setInt(1, id);
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
    id = rs.getInt("transaction_id");
    logId = rs.getInt("log_id");
    referenceId = rs.getString("reference_id");
    elementName = rs.getString("element_name");
    action = rs.getString("action");
    linkItemId = DatabaseUtils.getInt(rs, "link_item_id");
    statusCode = DatabaseUtils.getInt(rs, "status_code");
    recordCount = DatabaseUtils.getInt(rs, "record_count");
    message = rs.getString("message");
  }

}

