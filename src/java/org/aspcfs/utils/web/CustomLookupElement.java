//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.webutils;

import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;

public class CustomLookupElement extends HashMap {

  protected String tableName = null;
  protected String currentField = null;
  protected String currentValue = null;
  
  public CustomLookupElement() {
  }

  public CustomLookupElement(ResultSet rs) throws java.sql.SQLException {
    build(rs);
  }
    
  public void build(ResultSet rs) throws java.sql.SQLException {
    ResultSetMetaData meta = rs.getMetaData();
    for (int i = 1; i < meta.getColumnCount(); i++) {
      String columnName = meta.getColumnName(i);
      Object data = rs.getObject(i);
      if (data != null) {
        this.put(columnName, String.valueOf(data));
      }
    }
  }
  
  public void addField(String fieldName, String value) {
    this.put(fieldName, value);
  }
  
  public void setField(String tmp) {
    currentField = tmp;
  }
  
  public void setData(String tmp) {
    currentValue = tmp;
    addProperty();
  }
  
  private void addProperty() {
    if (currentField != null && currentValue != null) {
      this.put(new String(currentField), new String(currentValue));
    }
    currentField = null;
    currentValue = null;
  }
  
  public String getTableName() {
    return tableName;
  }
}
