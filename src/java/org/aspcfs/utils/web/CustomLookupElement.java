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
package org.aspcfs.utils.web;

import java.sql.*;
import java.util.*;
import org.aspcfs.utils.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    September 16, 2004
 *@version    $Id$
 */
public class CustomLookupElement extends HashMap {

  protected int id = -1;
  protected String tableName = null;
  protected String uniqueField = null;
  protected String currentField = null;
  protected String currentValue = null;
  protected java.sql.Timestamp entered = null;
  protected java.sql.Timestamp modified = null;


  /**
   *  Constructor for the CustomLookupElement object
   */
  public CustomLookupElement() { }


  /**
   *  Constructor for the CustomLookupElement object
   *
   *@param  rs                         Description of the Parameter
   *@exception  java.sql.SQLException  Description of the Exception
   */
  public CustomLookupElement(ResultSet rs) throws java.sql.SQLException {
    build(rs);
  }


  /**
   *  Constructor for the CustomLookupElement object
   *
   *@param  db                         Description of the Parameter
   *@param  code                       Description of the Parameter
   *@param  tableName                  Description of the Parameter
   *@param  uniqueField                Description of the Parameter
   *@exception  java.sql.SQLException  Description of the Exception
   */
  public CustomLookupElement(Connection db, int code, String tableName, String uniqueField) throws java.sql.SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomLookupElement-> Retrieving ID: " + code + " from table: " + tableName);
    }
    String sql =
        "SELECT " + uniqueField + " " +
        "FROM " + tableName + " " +
        "WHERE " + uniqueField + " = ? ";
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
   *  Description of the Method
   *
   *@param  rs                         Description of the Parameter
   *@exception  java.sql.SQLException  Description of the Exception
   */
  public void build(ResultSet rs) throws java.sql.SQLException {
    ResultSetMetaData meta = rs.getMetaData();
    if (meta.getColumnCount() == 1) {
      id = rs.getInt(1);
    } else {
      for (int i = 1; i < meta.getColumnCount() + 1; i++) {
        String columnName = meta.getColumnName(i);
        Object data = rs.getObject(i);
        if (data != null) {
          this.put(columnName, String.valueOf(data));
        }
      }
    }
  }


  /**
   *  Adds a feature to the Field attribute of the CustomLookupElement object
   *
   *@param  fieldName  The feature to be added to the Field attribute
   *@param  value      The feature to be added to the Field attribute
   */
  public void addField(String fieldName, String value) {
    this.put(fieldName, value);
  }


  /**
   *  Sets the tableName attribute of the CustomLookupElement object
   *
   *@param  tmp  The new tableName value
   */
  public void setTableName(String tmp) {
    this.tableName = tmp;
  }


  /**
   *  Sets the uniqueField attribute of the CustomLookupElement object
   *
   *@param  tmp  The new uniqueField value
   */
  public void setUniqueField(String tmp) {
    this.uniqueField = tmp;
  }


  /**
   *  Sets the id attribute of the CustomLookupElement object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the CustomLookupElement object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the field attribute of the CustomLookupElement object
   *
   *@param  tmp  The new field value
   */
  public void setField(String tmp) {
    currentField = tmp;
  }


  /**
   *  Sets the data attribute of the CustomLookupElement object
   *
   *@param  tmp  The new data value
   */
  public void setData(String tmp) {
    currentValue = tmp;
    addProperty();
  }


  /**
   *  Adds a feature to the Property attribute of the CustomLookupElement object
   */
  private void addProperty() {
    if (!"code".equals(currentField) && !"guid".equals(currentField)) {
      if (currentField != null && currentValue != null) {
        this.put(new String(currentField), new String(currentValue));
      }
    }
    currentField = null;
    currentValue = null;
  }


  /**
   *  Gets the id attribute of the CustomLookupElement object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the tableName attribute of the CustomLookupElement object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the CustomLookupElement object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the value attribute of the CustomLookupElement object
   *
   *@param  tmp  Description of the Parameter
   *@return      The value value
   */
  public String getValue(String tmp) {
    return (String) this.get(tmp);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (tableName == null) {
      throw new SQLException("Table name not specified");
    }
    if (this.size() == 0) {
      throw new SQLException("Fields not specified");
    }
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO " + tableName + " ");

    sql.append("(");
    Iterator fields = this.keySet().iterator();
    while (fields.hasNext()) {
      sql.append((String) fields.next());
      if (fields.hasNext()) {
        sql.append(", ");
      }
    }
    sql.append(") VALUES (");
    for (int i = 0; i < this.size(); i++) {
      sql.append("?");
      if (i < this.size() - 1) {
        sql.append(",");
      }
    }
    sql.append(")");

    PreparedStatement pst = db.prepareStatement(sql.toString());
    Iterator paramters = this.keySet().iterator();
    int paramCount = 0;
    while (paramters.hasNext()) {
      String paramName = ((String) paramters.next());
      String value = (String) this.get(paramName);
      pst.setString(++paramCount, value);
    }
    pst.execute();
    pst.close();

    String seqName = null;
    if (tableName.length() > 22) {
      seqName = tableName.substring(0, 22);
    } else {
      seqName = tableName;
    }

    //TODO: Update this to accomodate the length of uniqueField
    if (this.getUniqueField() != null) {
      id = DatabaseUtils.getCurrVal(db, seqName + "_" + getUniqueField() + "_seq");
    }

    return true;
  }
}

