package com.darkhorseventures.webutils;

import java.util.*;
import java.sql.*;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.cfsbase.Constants;

public class CustomLookupList extends LookupList {

  ArrayList fields = new ArrayList();
  
  public CustomLookupList() {
    super();
  }
  
  public void buildList(Connection db) throws SQLException {
    int items = -1;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT ");
    Iterator i = fields.iterator();
    while (i.hasNext()) {
      sql.append((String)i.next());
      if (i.hasNext()) {
        sql.append(",");
      }
      sql.append(" ");
    }
    sql.append("FROM " + tableName);
    PreparedStatement pst = db.prepareStatement(sql.toString());

    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      CustomLookupElement thisElement = new CustomLookupElement(rs);
      this.add(thisElement);
    }
    rs.close();
    pst.close();
  }

  public void addField(String fieldName) {
    if (fields == null) {
      fields = new ArrayList();
    }
    fields.add(fieldName);
  }
}
