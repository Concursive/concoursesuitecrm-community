//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.webutils;

import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;

public class CustomLookupElement extends HashMap {

  protected int id = -1;
  protected String tableName = null;
  protected String uniqueField = null;
  protected String currentField = null;
  protected String currentValue = null;
  protected java.sql.Timestamp entered = null;
  protected java.sql.Timestamp modified = null;
  
  public CustomLookupElement() {
  }

  public CustomLookupElement(ResultSet rs) throws java.sql.SQLException {
    build(rs);
  }
  
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
  
  public void addField(String fieldName, String value) {
    this.put(fieldName, value);
  }
  
  public void setTableName(String tmp) { this.tableName = tmp; }
  public void setUniqueField(String tmp) { this.uniqueField = tmp; }
  public void setId(int tmp) { this.id = tmp; }
  public void setId(String tmp) { this.id = Integer.parseInt(tmp); }

  public void setField(String tmp) {
    currentField = tmp;
  }
  
  public void setData(String tmp) {
    currentValue = tmp;
    addProperty();
  }
  
  private void addProperty() {
    if (!"code".equals(currentField) && !"guid".equals(currentField)) {
      if (currentField != null && currentValue != null) {
        this.put(new String(currentField), new String(currentValue));
      }
    }
    currentField = null;
    currentValue = null;
  }
  
  public int getId() { return id; }

  public String getTableName() {
    return tableName;
  }
  public String getUniqueField() { return uniqueField; }
  public String getValue(String tmp) {
    return (String)this.get(tmp);
  }
  
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
      sql.append((String)fields.next());
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
      String paramName = ((String)paramters.next());
      String value = (String)this.get(paramName);
      pst.setString(++paramCount, value);
    }
    pst.execute();
    pst.close();
    
    String seqName = null;
    if (tableName.length() > 22) {
      seqName = tableName.substring(0,22);
    } else {
      seqName = tableName;
    }
    
    //id = DatabaseUtils.getCurrVal(db, seqName + "_" + getUniqueField() + "_seq");
    
    return true;
  }
}
