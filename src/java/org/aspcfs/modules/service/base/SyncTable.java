//Copyright 2002 Dark Horse Ventures
package org.aspcfs.modules.service.base;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    June 24, 2002
 *@version    $Id$
 */
public class SyncTable extends GenericBean {

  private int id = -1;
  private int systemId = -1;
  private String name = null;
  private String mappedClassName = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private String createStatement = null;
  private int orderId = -1;
  private boolean syncItem = false;

  private boolean buildTextFields = true;


  /**
   *  Constructor for the SyncTable object
   */
  public SyncTable() { }


  /**
   *  Constructor for the SyncTable object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public SyncTable(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Looks up a table_id for the given System ID and Class Name.
   *
   *@param  db          Description of Parameter
   *@param  systemName  Description of Parameter
   *@param  className   Description of Parameter
   *@return             Description of the Returned Value
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


  /**
   *  Sets the id attribute of the SyncTable object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the systemId attribute of the SyncTable object
   *
   *@param  tmp  The new systemId value
   */
  public void setSystemId(int tmp) {
    this.systemId = tmp;
  }


  /**
   *  Sets the name attribute of the SyncTable object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the mappedClassName attribute of the SyncTable object
   *
   *@param  tmp  The new mappedClassName value
   */
  public void setMappedClassName(String tmp) {
    this.mappedClassName = tmp;
  }


  /**
   *  Sets the entered attribute of the SyncTable object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the SyncTable object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the createStatement attribute of the SyncTable object
   *
   *@param  tmp  The new createStatement value
   */
  public void setCreateStatement(String tmp) {
    this.createStatement = tmp;
  }


  /**
   *  Sets the orderId attribute of the SyncTable object
   *
   *@param  tmp  The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   *  Sets the buildTextFields attribute of the SyncTable object
   *
   *@param  tmp  The new buildTextFields value
   */
  public void setBuildTextFields(boolean tmp) {
    this.buildTextFields = tmp;
  }


  /**
   *  Gets the id attribute of the SyncTable object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the systemId attribute of the SyncTable object
   *
   *@return    The systemId value
   */
  public int getSystemId() {
    return systemId;
  }


  /**
   *  Gets the name attribute of the SyncTable object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the mappedClassName attribute of the SyncTable object
   *
   *@return    The mappedClassName value
   */
  public String getMappedClassName() {
    return mappedClassName;
  }


  /**
   *  Gets the entered attribute of the SyncTable object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the SyncTable object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the createStatement attribute of the SyncTable object
   *
   *@return    The createStatement value
   */
  public String getCreateStatement() {
    return createStatement;
  }


  /**
   *  Gets the orderId attribute of the SyncTable object
   *
   *@return    The orderId value
   */
  public int getOrderId() {
    return orderId;
  }


  /**
   *  Gets the syncItem attribute of the SyncTable object
   *
   *@return    The syncItem value
   */
  public boolean getSyncItem() {
    return syncItem;
  }


  /**
   *  Gets the buildTextFields attribute of the SyncTable object
   *
   *@return    The buildTextFields value
   */
  public boolean getBuildTextFields() {
    return buildTextFields;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
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
  }
}

