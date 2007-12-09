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
package org.aspcfs.utils.web;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: CustomLookupElement.java 12404 2005-08-05 17:37:07Z
 *          mrajkowski $
 * @created September 16, 2004
 */
public class CustomLookupElement extends HashMap {

  protected int id = -1;
  protected String tableName = null;
  protected String uniqueField = null;
  protected String currentField = null;
  protected String currentValue = null;
  protected String currentType = null;
  protected java.sql.Timestamp entered = null;
  protected java.sql.Timestamp modified = null;


  /**
   * Constructor for the CustomLookupElement object
   */
  public CustomLookupElement() {
  }


  /**
   * Constructor for the CustomLookupElement object
   *
   * @param rs Description of the Parameter
   * @throws java.sql.SQLException Description of the Exception
   * @throws java.sql.SQLException Description of the Exception
   */
  public CustomLookupElement(ResultSet rs) throws java.sql.SQLException {
    build(rs);
  }


  /**
   * Constructor for the CustomLookupElement object
   *
   * @param db          Description of the Parameter
   * @param code        Description of the Parameter
   * @param tableName   Description of the Parameter
   * @param uniqueField Description of the Parameter
   * @throws java.sql.SQLException Description of the Exception
   * @throws java.sql.SQLException Description of the Exception
   */
  public CustomLookupElement(Connection db, int code, String tableName, String uniqueField) throws java.sql.SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "CustomLookupElement-> Retrieving ID: " + code + " from table: " + tableName);
    }
    String sql =
        "SELECT " + uniqueField + " " +
            "FROM " + DatabaseUtils.getTableName(db, tableName) + " " +
            "WHERE " + uniqueField + " = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, code);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      build(rs);
    } else {
      throw new java.sql.SQLException("ID not found");
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws java.sql.SQLException Description of the Exception
   */
  public void build(ResultSet rs) throws java.sql.SQLException {
    ResultSetMetaData meta = rs.getMetaData();
    if (meta.getColumnCount() == 1) {
      id = rs.getInt(1);
    } else {
      for (int i = 1; i < meta.getColumnCount() + 1; i++) {
        String columnName = meta.getColumnName(i);
        int columnType = meta.getColumnType(i);
        String data = null;
        if (columnType == Types.CLOB || columnType == Types.BLOB || columnType == Types.LONGVARCHAR) {
          data = rs.getString(i);
          columnType = Types.VARCHAR;
        } else {
          Object obj = rs.getObject(i);
          if (obj != null) {
            data = String.valueOf(obj);
          }
          //if (Class.forName("net.sourceforge.jtds.jdbc.ClobImpl").isInstance(obj)) {
          //data = rs.getString(i);
          //columnType = java.sql.Types.VARCHAR;//Treat a clob as a string
        }
        CustomLookupColumn thisColumn =
            new CustomLookupColumn(columnName, data, columnType);
        this.put(columnName, thisColumn);
      }
    }
  }


  /**
   * Adds a feature to the Field attribute of the CustomLookupElement object
   *
   * @param fieldName The feature to be added to the Field attribute
   * @param value     The feature to be added to the Field attribute
   * @param type      The feature to be added to the Field attribute
   */
  public void addField(String fieldName, String value, int type) {
    CustomLookupColumn thisColumn =
        new CustomLookupColumn(fieldName, value, type);
    this.put(fieldName, thisColumn);
  }


  /**
   * Sets the tableName attribute of the CustomLookupElement object
   *
   * @param tmp The new tableName value
   */
  public void setTableName(String tmp) {
    this.tableName = tmp;
  }


  /**
   * Sets the uniqueField attribute of the CustomLookupElement object
   *
   * @param tmp The new uniqueField value
   */
  public void setUniqueField(String tmp) {
    this.uniqueField = tmp;
  }


  /**
   * Sets the id attribute of the CustomLookupElement object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the CustomLookupElement object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the field attribute of the CustomLookupElement object
   *
   * @param tmp The new field value
   */
  public void setField(String tmp) {
    currentField = tmp;
  }


  /**
   * Sets the data attribute of the CustomLookupElement object
   *
   * @param tmp The new data value
   */
  public void setData(String tmp) {
    currentValue = tmp;
  }


  /**
   * Gets the currentType attribute of the CustomLookupElement object
   *
   * @return The currentType value
   */
  public String getCurrentType() {
    return currentType;
  }


  /**
   * Sets the currentType attribute of the CustomLookupElement object
   *
   * @param tmp The new currentType value
   */
  public void setType(String tmp) {
    this.currentType = tmp;
    addProperty();
  }


  /**
   * Adds a feature to the Property attribute of the CustomLookupElement object
   */
  private void addProperty() {
    if (!"code".equals(currentField) && !"guid".equals(currentField)) {
      if (currentField != null && currentValue != null && currentType != null) {
        CustomLookupColumn thisColumn =
            new CustomLookupColumn(currentField, currentValue,
                Integer.parseInt(currentType));
        this.put(new String(currentField), thisColumn);
      }
    }
    currentField = null;
    currentValue = null;
    currentType = null;
  }


  /**
   * Sets the serverMapId attribute of the CustomLookupElement object
   *
   * @param value The new serverMapId value
   */
  public void setServerMapId(String value) {
    String field = value.substring(0, value.indexOf("="));
    String recordId = value.substring(value.indexOf("=") + 1);
    CustomLookupColumn thisColumn = (CustomLookupColumn) this.get(field);
    if (thisColumn != null) {
      thisColumn.setValue(recordId);
    }
  }


  /**
   * Gets the id attribute of the CustomLookupElement object
   *
   * @return The id value
   */
  public int getId() {
    if (uniqueField != null && getValue(uniqueField) != null) {
      return Integer.parseInt(getValue(uniqueField));
    }
    return id;
  }


  /**
   * Gets the tableName attribute of the CustomLookupElement object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the CustomLookupElement object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the value attribute of the CustomLookupElement object
   *
   * @param tmp Description of the Parameter
   * @return The value value
   */
  public String getValue(String tmp) {
    CustomLookupColumn thisColumn = (CustomLookupColumn) this.get(tmp);
    if (thisColumn != null) {
      return thisColumn.getValue();
    }
    return null;
  }


  /**
   * Gets the type attribute of the CustomLookupElement object
   *
   * @param tmp Description of the Parameter
   * @return The type value
   */
  public int getType(String tmp) {
    CustomLookupColumn thisColumn = (CustomLookupColumn) this.get(tmp);
    if (thisColumn != null) {
      return thisColumn.getType();
    }
    return -1;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (tableName == null) {
      throw new SQLException("Table name not specified");
    }
    if (this.size() == 0) {
      throw new SQLException("Fields not specified");
    }

    String seqName = null;
    if (id < 0) {
      if (this.getUniqueField() != null && !"".equals(this.getUniqueField().trim())) {
        seqName = getPostgresSeqName(tableName, getUniqueField());
        id = DatabaseUtils.getNextSeq(db, seqName);
      }
    }
    tableName = DatabaseUtils.getTableName(db, tableName);

    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO " + tableName + " ");
    sql.append("(");
    if (this.getUniqueField() != null && id > -1) {
      sql.append(this.getUniqueField() + ", ");
    }
    Iterator fields = this.keySet().iterator();
    while (fields.hasNext()) {
      sql.append(DatabaseUtils.parseReservedWord(db, (String) fields.next()));
      if (fields.hasNext()) {
        sql.append(", ");
      }
    }
    sql.append(") VALUES (");
    if (this.getUniqueField() != null && id > -1) {
      sql.append("?, ");
    }
    for (int i = 0; i < this.size(); i++) {
      sql.append("?");
      if (i < this.size() - 1) {
        sql.append(",");
      }
    }
    sql.append(")");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int paramCount = 0;
    if (this.getUniqueField() != null && id > -1) {
      pst.setInt(++paramCount, id);
    }
    Iterator paramters = this.keySet().iterator();
    while (paramters.hasNext()) {
      String paramName = ((String) paramters.next());

      CustomLookupColumn thisColumn =
          (CustomLookupColumn) this.get(paramName);

      //This code needs to be maintained. If support for new column types are
      //required, then the corresponding "else if" needs to be added.
      if (thisColumn.getType() == java.sql.Types.CHAR ||
          thisColumn.getType() == java.sql.Types.VARCHAR ||
          thisColumn.getType() == java.sql.Types.LONGVARCHAR) {
        pst.setString(++paramCount, thisColumn.getValue());
      } else if (thisColumn.getType() == java.sql.Types.INTEGER) {
        if ("saved_criteriaelement".equals(getTableName()) &&
            "source".equals(paramName)) {
          //TODO: source defaults to -1 and has constraint NOT NULL
          pst.setInt(++paramCount,
              StringUtils.hasText(thisColumn.getValue()) ?
                  Integer.parseInt(thisColumn.getValue()) : -1);
        } else {
          DatabaseUtils.setInt(pst, ++paramCount,
              StringUtils.hasText(thisColumn.getValue()) ?
                  Integer.parseInt(thisColumn.getValue()) : -1);
        }
      } else if (thisColumn.getType() == java.sql.Types.DOUBLE) {
        DatabaseUtils.setDouble(pst, ++paramCount,
            StringUtils.hasText(thisColumn.getValue()) ?
                Double.parseDouble(thisColumn.getValue()) : -1.0);
      } else if (thisColumn.getType() == java.sql.Types.BOOLEAN ||
          thisColumn.getType() == java.sql.Types.BIT) {
        pst.setBoolean(++paramCount,
            StringUtils.hasText(thisColumn.getValue()) ?
                DatabaseUtils.parseBoolean(thisColumn.getValue()) : false);
      } else if (thisColumn.getType() == java.sql.Types.TIMESTAMP) {
        DatabaseUtils.setTimestamp(pst, ++paramCount,
            StringUtils.hasText(thisColumn.getValue()) ?
                DateUtils.parseTimestampString(thisColumn.getValue()) : 
                new java.sql.Timestamp(System.currentTimeMillis()));
      }
    }
    pst.execute();
    pst.close();
    if (id < 0) {
      if (this.getUniqueField() != null && !"".equals(this.getUniqueField().trim())) {
        id = DatabaseUtils.getCurrVal(db, seqName, id);
      }
    }
    return true;
  }


  /**
   * Gets the sequenceName attribute of the CustomLookupElement object
   *
   * @param tableName   Description of the Parameter
   * @param uniqueField Description of the Parameter
   * @return The sequenceName value
   */
  private String getPostgresSeqName(String tableName, String uniqueField) {
    String seqName = null;
    if ("module_field_categorylink".equals(tableName)) {
      seqName = "module_field_categorylin" + "_" + uniqueField + "_seq";
    } else if ("lookup_document_store_permission_category".equals(tableName)) {
      seqName = "lookup_document_store_permission_category" + "_" + uniqueField + "_seq";
    } else if ("lookup_document_store_role".equals(tableName)) {
      seqName = "lookup_document_store_role" + "_" + uniqueField + "_seq";
    } else if ("lookup_document_store_permission".equals(tableName)) {
      seqName = "lookup_document_store_permission" + "_" + uniqueField + "_seq";
    } else if ("document_store_permissions".equals(tableName)) {
      seqName = "document_store_permissions" + "_" + uniqueField + "_seq";
    } else if ("active_survey".equals(tableName)) {
      seqName = "active_survey_active_survey" + "_seq";
    } else if ("active_survey_answer_avg".equals(tableName)) {
      seqName = "active_survey_answer_avg_id" + "_seq";
    } else if ("product_catalog_category_map".equals(tableName)) {
      seqName = "product_catalog_category_map_id" + "_seq";
    } else if (tableName.length() > 22) {
      seqName = tableName.substring(0, 22) + "_" + uniqueField + "_seq";
    } else {
      seqName = tableName + "_" + uniqueField + "_seq";
    }
    return seqName;
  }
}

