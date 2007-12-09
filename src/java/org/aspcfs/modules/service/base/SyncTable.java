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
package org.aspcfs.modules.service.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Represents an entry in the sync_table database table, used for mapping XML
 *  object names to Java classes during an XML Transaction
 *
 * @author     matt rajkowski
 * @version    $Id$
 * @created    June 24, 2002
 */
public class SyncTable extends GenericBean {

  //object properties
  private int id = -1;
  private int systemId = -1;
  private String name = null;
  private String mappedClassName = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private String createStatement = null;
  private int orderId = -1;
  private boolean syncItem = false;
  private String key = null;
  //build properties
  private boolean buildTextFields = true;


  /**
   *  Constructor for the SyncTable object
   */
  public SyncTable() { }


  /**
   *  Constructor for the SyncTable object
   *
   * @param  db                Description of the Parameter
   * @param  tableId           Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public SyncTable(Connection db, int tableId) throws SQLException {
    if (tableId == -1) {
      throw new SQLException("Table ID Not Specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM sync_table " +
        "WHERE table_id = ? ");
    pst.setInt(1, tableId);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Constructor for the SyncTable object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public SyncTable(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Looks up a table_id for the given System ID and Class Name.
   *
   * @param  db             Description of Parameter
   * @param  className      Description of Parameter
   * @param  systemId       Description of the Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of the Exception
   */
  public static int lookupTableId(Connection db, int systemId, String className) throws SQLException {
    int tableId = -1;
    String sql =
        "SELECT table_id " +
        "FROM sync_table " +
        "WHERE system_id = ? " +
        "AND mapped_class_name = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, systemId);
    pst.setString(2, className);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      tableId = rs.getInt("table_id");
    }
    rs.close();
    pst.close();
    return tableId;
  }


  public static int lookupElementName(Connection db, int systemId, String elementName) throws SQLException {
    int tableId = -1;
    String sql =
        "SELECT table_id " +
        "FROM sync_table " +
        "WHERE system_id = ? " +
        "AND element_name = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, systemId);
    pst.setString(2, elementName);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      tableId = rs.getInt("table_id");
    }
    rs.close();
    pst.close();
    return tableId;
  }


  /**
   *  Sets the id attribute of the SyncTable object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the systemId attribute of the SyncTable object
   *
   * @param  tmp  The new systemId value
   */
  public void setSystemId(int tmp) {
    this.systemId = tmp;
  }


  /**
   *  Sets the name attribute of the SyncTable object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the mappedClassName attribute of the SyncTable object
   *
   * @param  tmp  The new mappedClassName value
   */
  public void setMappedClassName(String tmp) {
    this.mappedClassName = tmp;
  }


  /**
   *  Sets the key attribute of the SyncTable object
   *
   * @param  tmp  The new key value
   */
  public void setKey(String tmp) {
    this.key = tmp;
  }


  /**
   *  Sets the entered attribute of the SyncTable object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the SyncTable object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the createStatement attribute of the SyncTable object
   *
   * @param  tmp  The new createStatement value
   */
  public void setCreateStatement(String tmp) {
    this.createStatement = tmp;
  }


  /**
   *  Sets the orderId attribute of the SyncTable object
   *
   * @param  tmp  The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   *  Sets the buildTextFields attribute of the SyncTable object
   *
   * @param  tmp  The new buildTextFields value
   */
  public void setBuildTextFields(boolean tmp) {
    this.buildTextFields = tmp;
  }


  /**
   *  Gets the id attribute of the SyncTable object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the systemId attribute of the SyncTable object
   *
   * @return    The systemId value
   */
  public int getSystemId() {
    return systemId;
  }


  /**
   *  Gets the name attribute of the SyncTable object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the mappedClassName attribute of the SyncTable object
   *
   * @return    The mappedClassName value
   */
  public String getMappedClassName() {
    return mappedClassName;
  }


  /**
   *  Gets the key attribute of the SyncTable object
   *
   * @return    The key value
   */
  public String getKey() {
    return key;
  }


  /**
   *  Gets the entered attribute of the SyncTable object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the SyncTable object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the createStatement attribute of the SyncTable object
   *
   * @return    The createStatement value
   */
  public String getCreateStatement() {
    return createStatement;
  }


  /**
   *  Gets the orderId attribute of the SyncTable object
   *
   * @return    The orderId value
   */
  public int getOrderId() {
    return orderId;
  }


  /**
   *  Gets the syncItem attribute of the SyncTable object
   *
   * @return    The syncItem value
   */
  public boolean getSyncItem() {
    return syncItem;
  }


  /**
   *  Gets the buildTextFields attribute of the SyncTable object
   *
   * @return    The buildTextFields value
   */
  public boolean getBuildTextFields() {
    return buildTextFields;
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("table_id");
    systemId = rs.getInt("system_id");
    name = rs.getString("element_name");
    mappedClassName = rs.getString("mapped_class_name");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
    if (buildTextFields) {
      createStatement = rs.getString("create_statement");
    }
    orderId = rs.getInt("order_id");
    syncItem = rs.getBoolean("sync_item");
    key = rs.getString("object_key");
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "sync_table_table_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO sync_table " +
        "(" + (id > -1 ? "table_id, " : "") + "system_id, element_name, mapped_class_name, create_statement, order_id, sync_item, object_key" + ") " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, systemId);
    pst.setString(++i, name);
    pst.setString(++i, mappedClassName);
    pst.setString(++i, createStatement);
    pst.setInt(++i, orderId);
    pst.setBoolean(++i, syncItem);
    pst.setString(++i, key);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "sync_table_table_id_seq", id);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void updateKey(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE sync_table " +
        "SET object_key = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE system_id = ? AND element_name = ? ");
    int i = 0;
    pst.setString(++i, key);
    pst.setInt(++i, systemId);
    pst.setString(++i, name);
    pst.executeUpdate();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  name           Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static boolean hasMapping(Connection db, String name) throws SQLException {
    int count = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(*) as recordcount " +
        "FROM sync_table " +
        "WHERE element_name = ? ");
    pst.setString(1, name);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("recordcount");
    }
    rs.close();
    pst.close();
    return (count > 0);
  }
}

