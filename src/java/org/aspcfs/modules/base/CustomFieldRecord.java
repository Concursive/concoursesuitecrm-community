//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.sql.*;
import java.text.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Represents all of the data that is entered for a CustomFieldCategory
 *
 *@author     matt rajkowski
 *@created    March 22, 2002
 *@version    $Id$
 */
public class CustomFieldRecord {

  //Properties for a Field
  private int id = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = false;
  
  //A single record for display purposes only, maybe will be multiple
  //records someday
  private CustomField fieldData = null;

  //Properties for related data
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int categoryId = -1;


  /**
   *  Constructor for the CustomFieldRecord object
   */
  public CustomFieldRecord() { }

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
   *  Constructor for the CustomFieldRecord object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public CustomFieldRecord(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the CustomFieldRecord object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the entered attribute of the CustomFieldRecord object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the CustomFieldRecord object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modified attribute of the CustomFieldRecord object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the CustomFieldRecord object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the CustomFieldRecord object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkItemId attribute of the CustomFieldRecord object
   *
   *@param  tmp  The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the CustomFieldRecord object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Gets the id attribute of the CustomFieldRecord object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the entered attribute of the CustomFieldRecord object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredString attribute of the CustomFieldRecord object
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
   *  Gets the enteredBy attribute of the CustomFieldRecord object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the CustomFieldRecord object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedDateTimeString attribute of the CustomFieldRecord object
   *
   *@return    The modifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the modifiedBy attribute of the CustomFieldRecord object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }

  public CustomField getFieldData() {
    return fieldData;
  }

  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO custom_field_record " +
        "(link_module_id, link_item_id, category_id, enteredby, modifiedby) " +
        "VALUES (?, ?, ?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, linkModuleId);
    pst.setInt(++i, linkItemId);
    pst.setInt(++i, categoryId);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "custom_field_reco_record_id_seq");
  }


  /**
   *  When a CustomFieldCategory is deleted, then all of the associated
   *  data needs to go.
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
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

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
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
    pst.execute();
    pst.close();

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
    pst.execute();
    pst.close();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomFieldRecord-> Delete Complete");
    }
    return true;
  }

  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
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

  public void buildColumns(Connection db, CustomFieldCategory thisCategory) throws SQLException {
    //Get the first CustomField, then populate it
    String sql =
    "   SELECT " + (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL?"TOP 1 ":"") +
    "    * " +
    "   FROM custom_field_info cfi " +
    "   WHERE cfi.group_id IN " +
    "     (SELECT " + (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL?"TOP 1 ":"") +
    "        group_id " +
    "      FROM custom_field_group " +
    "      WHERE category_id = ? " +
    "      AND enabled = " + DatabaseUtils.getTrue(db) + " " +
    "      ORDER BY level, field_id, field_name " +
           (DatabaseUtils.getType(db) == DatabaseUtils.POSTGRESQL?"LIMIT 1 ":"") +
    "     ) " +
    "   AND enabled = " + DatabaseUtils.getTrue(db) + " " +
    "   ORDER BY level " +
        (DatabaseUtils.getType(db) == DatabaseUtils.POSTGRESQL?"LIMIT 1 ":"");
    
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, thisCategory.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      fieldData = new CustomField(rs);
      fieldData.setRecordId(this.id);
      fieldData.buildResources(db);
    }
  }
  
}

