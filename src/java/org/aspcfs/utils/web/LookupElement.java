//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.webutils;

import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;

/**
 *  Represents an item from a Lookup table, to be used primarily with HtmlSelect
 *  objects and the LookupList object.
 *
 *@author     mrajkowski
 *@created    September 5, 2001
 *@version    $Id$
 */
public class LookupElement {

  protected String tableName = null;
  protected int code = 0;
  protected String description = "";
  protected boolean defaultItem = false;
  protected int level = 0;
  protected boolean enabled = true;
  protected java.sql.Timestamp entered = null;
  protected java.sql.Timestamp modified = null;
  protected int fieldId = -1;


  /**
   *  Constructor for the LookupElement object
   *
   *@since    1.1
   */
  public LookupElement() { }

  public LookupElement(Connection db, int code, String tableName) throws java.sql.SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("LookupElement-> Retrieving ID: " + code + " from table: " + tableName);
    }
    String sql =
      "SELECT code, description, default_item, level, enabled " +
      "FROM " + tableName + " " +
      "WHERE code = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, code);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      build(rs);
    } else {
      throw new java.sql.SQLException("ID not found");
    }
  }

  /**
   *  Constructor for the LookupElement object
   *
   *@param  rs                         Description of Parameter
   *@exception  java.sql.SQLException  Description of Exception
   *@since                             1.1
   */
  public LookupElement(ResultSet rs) throws java.sql.SQLException {
    build(rs);
  }
    
  public void build(ResultSet rs) throws java.sql.SQLException {
    code = rs.getInt("code");
    description = rs.getString("description");
    
    defaultItem = rs.getBoolean("default_item");
    level = rs.getInt("level");
    //startDate = rs.getTimestamp("start_date");
    //endDate = rs.getTimestamp("end_date");
    enabled = rs.getBoolean("enabled");
    
    if (!(this.getEnabled())) {
      description += " (X)";
    }
    
    //not guaranteed to be here
    //entered = rs.getTimestamp("entered");
    //modified = rs.getTimestamp("modified");
  }

  public void setTableName(String tmp) { this.tableName = tmp; }

  /**
   *  Sets the newOrder attribute of the LookupElement object
   *
   *@param  db                The new newOrder value
   *@param  tableName         The new newOrder value
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int setNewOrder(Connection db, String tableName) throws SQLException {
    int resultCount = 0;

    if (this.getCode() == 0) {
      throw new SQLException("Element Code not specified.");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE " + tableName + " " +
        "SET level = ? " +
        "WHERE code = ? ");

    int i = 0;

    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getLevel());
    pst.setInt(++i, this.getCode());

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Sets the Code attribute of the LookupElement object
   *
   *@param  tmp  The new Code value
   *@since       1.1
   */
  public void setCode(int tmp) {
    this.code = tmp;
  }
  public void setCode(String tmp) {
    this.code = Integer.parseInt(tmp);
  }
  public void setId(int tmp) {
    this.code = tmp;
  }
  public void setId(String tmp) {
    this.code = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Description attribute of the LookupElement object
   *
   *@param  tmp  The new Description value
   *@since       1.1
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the DefaultItem attribute of the LookupElement object
   *
   *@param  tmp  The new DefaultItem value
   *@since       1.2
   */
  public void setDefaultItem(boolean tmp) {
    this.defaultItem = tmp;
  }
  public void setDefaultItem(String tmp) {
    this.defaultItem = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the Level attribute of the LookupElement object
   *
   *@param  tmp  The new Level value
   *@since       1.2
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }



  /**
   *  Sets the Enabled attribute of the LookupElement object
   *
   *@param  tmp  The new Enabled value
   *@since       1.1
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }
  public void setEnabled(String tmp) {
    this.enabled = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(tmp));
  }
  
  public void setFieldId(int tmp) { this.fieldId = tmp; }
  public void setFieldId(String tmp) { this.fieldId = Integer.parseInt(tmp); }

  public String getTableName() { return tableName; }


  /**
   *  Gets the Code attribute of the LookupElement object
   *
   *@return    The Code value
   *@since     1.1
   */
  public int getCode() {
    return code;
  }


  /**
   *  Gets the id attribute of the LookupElement object, id is a required
   *  name for some CFS reflection parsing
   *
   *@return    The id value
   */
  public int getId() {
    return code;
  }


  /**
   *  Gets the Description attribute of the LookupElement object
   *
   *@return    The Description value
   *@since     1.1
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the DefaultItem attribute of the LookupElement object
   *
   *@return    The DefaultItem value
   *@since     1.2
   */
  public boolean getDefaultItem() {
    return defaultItem;
  }


  /**
   *  Gets the Level attribute of the LookupElement object
   *
   *@return    The Level value
   *@since     1.2
   */
  public int getLevel() {
    return level;
  }



  /**
   *  Gets the Enabled attribute of the LookupElement object
   *
   *@return    The Enabled value
   *@since     1.1
   */
  public boolean getEnabled() {
    return enabled;
  }
  
  public java.sql.Timestamp getModified() { 
    if (modified == null) {
      return (new java.sql.Timestamp(new java.util.Date().getTime()));
    } else {
      return modified;
    }
  }

  public int getFieldId() { return fieldId; }



  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  tableName         Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int disableElement(Connection db, String tableName) throws SQLException {
    int resultCount = 0;

    if (this.getCode() == 0) {
      throw new SQLException("Element Code not specified.");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE " + tableName + " " +
        "SET enabled = false " +
        "WHERE code = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getCode());
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  tableName         Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insertElement(Connection db, String tableName) throws SQLException {
    return insertElement(db, tableName, -1);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  tableName         Description of Parameter
   *@param  fieldId           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insertElement(Connection db, String tableName, int fieldId) throws SQLException {
    this.tableName = tableName;
    this.fieldId = fieldId;
    return insert(db);
  }

  public boolean insert(Connection db) throws SQLException {  
    StringBuffer sql = new StringBuffer();
    int i = 0;

    sql.append(
        "INSERT INTO " + tableName + " " +
        "(description, level, enabled" + (fieldId > -1 ? ", field_id" : "") + ") " +
        "VALUES (?, ?, ?" + (fieldId > -1 ? ", ?" : "") + ") ");
    i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getDescription());
    
    pst.setInt(++i, this.getLevel());
    pst.setBoolean(++i, true);
    if (fieldId > -1) {
      pst.setInt(++i, fieldId);
    }
    pst.execute();
    pst.close();

    String seqName = null;
    if (tableName.length() > 22) {
      seqName = tableName.substring(0,22);
    } else {
      seqName = tableName;
    }
    
    code = DatabaseUtils.getCurrVal(db, seqName + "_code_seq");
    
    return true;
  }

}

