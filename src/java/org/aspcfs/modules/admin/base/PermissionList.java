//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.utils.DatabaseUtils;

public class PermissionList extends Vector {
  
  private String emptyHtmlSelectRecord = null;
  private int userId = -1;
  private String currentCategory = "!new";
  
  public PermissionList() { }
  
  public PermissionList(Connection db) throws SQLException {
    buildList(db);
  }
  
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT p.*, c.category " +
        "FROM permission p, permission_category c " +
        "WHERE p.category_id = c.category_id " +
        "AND p.enabled = " + DatabaseUtils.getTrue(db) + " ");
        

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM permission p, permission_category c " +
        "WHERE p.category_id = c.category_id " +
        "AND p.enabled = " + DatabaseUtils.getTrue(db) + " ");

    sqlSelect.append("ORDER BY c.level, p.level ");
        
    createFilter(sqlFilter);
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Permission thisPermission = new Permission(rs);
      this.addElement(thisPermission);
      thisPermission.setEnabled(true);
    }
    rs.close();
    pst.close();

  }
  
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
  }
  
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    return i;
  }
  
  public boolean isNewCategory(String thisCategory) {
    if (thisCategory.equals(currentCategory)) {
      return false;
    } else {
      currentCategory = thisCategory;
      return true;
    }
  }
  
}
