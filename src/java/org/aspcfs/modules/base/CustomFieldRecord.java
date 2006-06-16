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
package org.aspcfs.modules.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.actionplans.base.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

/**
 * Represents all of the data that is entered for a CustomFieldCategory
 *
 * @author matt rajkowski
 * @version $Id: CustomFieldRecord.java,v 1.6 2002/07/12 21:56:40 mrajkowski
 *          Exp $
 * @created March 22, 2002
 */
public class CustomFieldRecord {

  //Properties for a Field
  private int id = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = false;

  //A single record for display purposes only, may be multiple records someday
  private CustomField fieldData = null;

  //Properties for related data
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int categoryId = -1;


  /**
   * Constructor for the CustomFieldRecord object
   */
  public CustomFieldRecord() {
  }


  /**
   * Constructor for the CustomFieldRecord object
   *
   * @param db       Description of the Parameter
   * @param recordId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CustomFieldRecord(Connection db, int recordId) throws SQLException {
    String sql =
        "SELECT * " +
        "FROM custom_field_record cfr " +
        "WHERE record_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, recordId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   * Constructor for the CustomFieldRecord object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  public CustomFieldRecord(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the CustomFieldRecord object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the CustomFieldRecord object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the CustomFieldRecord object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the CustomFieldRecord object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the enteredBy attribute of the CustomFieldRecord object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the CustomFieldRecord object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the CustomFieldRecord object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the CustomFieldRecord object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the CustomFieldRecord object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the CustomFieldRecord object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the linkModuleId attribute of the CustomFieldRecord object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the CustomFieldRecord object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   * Sets the linkItemId attribute of the CustomFieldRecord object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Sets the linkItemId attribute of the CustomFieldRecord object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryId attribute of the CustomFieldRecord object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the CustomFieldRecord object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the CustomFieldRecord object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the CustomFieldRecord object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   * Gets the id attribute of the CustomFieldRecord object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the entered attribute of the CustomFieldRecord object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredString attribute of the CustomFieldRecord object
   *
   * @return The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the enteredBy attribute of the CustomFieldRecord object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the CustomFieldRecord object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedDateTimeString attribute of the CustomFieldRecord object
   *
   * @return The modifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the modifiedBy attribute of the CustomFieldRecord object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the fieldData attribute of the CustomFieldRecord object
   *
   * @return The fieldData value
   */
  public CustomField getFieldData() {
    return fieldData;
  }


  /**
   * Gets the enabled attribute of the CustomFieldRecord object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the linkModuleId attribute of the CustomFieldRecord object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Gets the linkItemId attribute of the CustomFieldRecord object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Gets the categoryId attribute of the CustomFieldRecord object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "custom_field_reco_record_id_seq");
    sql.append(
        "INSERT INTO custom_field_record " +
        "(link_module_id, link_item_id, category_id, ");
    if (id > -1) {
      sql.append("record_id, ");
    }
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("enteredBy, modifiedBy ) ");
    sql.append("VALUES (?, ?, ?, ");
    if (id > -1) {
      sql.append("?,");
    }
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, linkModuleId);
    pst.setInt(++i, linkItemId);
    pst.setInt(++i, categoryId);
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "custom_field_reco_record_id_seq", id);
  }


  /**
   * When a CustomFieldCategory is deleted, then all of the associated data
   * needs to go.
   *
   * @param db Description of Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    int i = 0;
    StringBuffer sql = new StringBuffer();
    int accountMapId = ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS);
    //Delete the action item work data.
    sql.append("UPDATE action_item_work SET link_item_id = NULL WHERE link_item_id = ? AND link_module_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getId());
    pst.setInt(++i, accountMapId);
    pst.executeUpdate();
    pst.close();
    //Delete the related data
    sql.setLength(0);
    sql.append(
        "DELETE FROM custom_field_data " +
        "WHERE record_id IN (SELECT record_id FROM custom_field_record WHERE link_module_id = ? ");
    if (categoryId > -1) {
      sql.append("AND category_id = ? ");
    }
    if (linkItemId > -1) {
      sql.append("AND link_item_id = ? ");
    }
    if (id > -1) {
      sql.append("AND record_id = ? ");
    }
    sql.append(") ");
    pst = db.prepareStatement(sql.toString());
    i = 0;
    pst.setInt(++i, linkModuleId);
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    if (linkItemId > -1) {
      pst.setInt(++i, linkItemId);
    }
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.executeUpdate();
    pst.close();
    //Delete the records
    sql.setLength(0);
    sql.append(
        "DELETE FROM custom_field_record " +
        "WHERE link_module_id = ? ");
    if (categoryId > -1) {
      sql.append("AND category_id = ? ");
    }
    if (linkItemId > -1) {
      sql.append("AND link_item_id = ? ");
    }
    if (id > -1) {
      sql.append("AND record_id = ? ");
    }
    pst = db.prepareStatement(sql.toString());
    i = 0;
    pst.setInt(++i, linkModuleId);
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    if (linkItemId > -1) {
      pst.setInt(++i, linkItemId);
    }
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.executeUpdate();
    pst.close();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomFieldRecord-> Delete Complete");
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    linkModuleId = rs.getInt("link_module_id");
    linkItemId = rs.getInt("link_item_id");
    categoryId = rs.getInt("category_id");
    id = rs.getInt("record_id");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param thisCategory Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildColumns(Connection db, CustomFieldCategory thisCategory) throws SQLException {
    //Get the first CustomField, then populate it
    String sql =
        (DatabaseUtils.getType(db) == DatabaseUtils.ORACLE ? "SELECT * FROM ( " : "") +
        "SELECT " +
        (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL ? "TOP 1 " : "") +
        (DatabaseUtils.getType(db) == DatabaseUtils.DAFFODILDB ? "TOP (1) " : "") +
        (DatabaseUtils.getType(db) == DatabaseUtils.FIREBIRD ? "FIRST 1 " : "") +
        "cfi.* " +
        "FROM custom_field_info cfi " +
        "WHERE cfi.group_id IN " +
        " (SELECT " +
        (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL ? "TOP 1 " : "") +
        (DatabaseUtils.getType(db) == DatabaseUtils.DAFFODILDB ? "TOP (1) " : "") +
        (DatabaseUtils.getType(db) == DatabaseUtils.FIREBIRD ? "FIRST 1 " : "") +
        "    group_id " +
        "  FROM custom_field_group " +
        "  WHERE category_id = ? " +
        "  AND enabled = ? " +
        "  ORDER BY \"level\", group_id, group_name " +
        (DatabaseUtils.getType(db) == DatabaseUtils.POSTGRESQL ? "LIMIT 1 " : "") +
        " ) " +
        "AND enabled = ? " +
        "ORDER BY \"level\", field_id, field_name " +
        (DatabaseUtils.getType(db) == DatabaseUtils.POSTGRESQL ? "LIMIT 1 " : "") +
        (DatabaseUtils.getType(db) == DatabaseUtils.DB2 ? "FETCH FIRST 1 ROWS ONLY " : "") +
        (DatabaseUtils.getType(db) == DatabaseUtils.ORACLE ? ") WHERE ROWNUM <= 1 " : "");
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, thisCategory.getId());
    pst.setBoolean(2, true);
    pst.setBoolean(3, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      fieldData = new CustomField(rs);
      fieldData.setRecordId(this.id);
      fieldData.buildResources(db);
    }
  }

  public boolean deleteData(Connection db) throws SQLException {
    //Delete the related data
    PreparedStatement pst = db.prepareStatement(
        " DELETE FROM custom_field_data " +
        " WHERE record_id = ? ");
    pst.setInt(1, this.getId());
    pst.executeUpdate();
    pst.close();

    return true;
  }
}

