//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Hashtable;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.utils.DatabaseUtils;
import javax.servlet.*;
import javax.servlet.http.*;

public class RolePermissionList extends Hashtable {
  
  private int roleId = -1;
  
  public RolePermissionList() { }
  
  public RolePermissionList(Connection db, int roleId) throws SQLException {
    this.roleId = roleId;
    buildList(db);
  }
  
  public RolePermissionList(HttpServletRequest request) {
    int i = 0;
    while (request.getParameter("permission" + (++i) + "id") != null) {
      Permission thisPermission = new Permission();
      thisPermission.buildRecord(request, i);
      this.put("permission" + i, thisPermission);
    }
  }
  
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT p.*, c.category, role_add, role_view, role_edit, role_delete " +
        "FROM permission p, permission_category c, role_permission r " +
        "WHERE p.category_id = c.category_id " +
        "AND p.permission_id = r.permission_id " +
        "AND p.enabled = " + DatabaseUtils.getTrue(db) + " ");

    sqlOrder.append("ORDER BY r.role_id, c.level, p.level ");
        
    createFilter(sqlFilter);
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Permission thisPermission = new Permission(rs);
      thisPermission.setView(rs.getBoolean("role_view"));
      thisPermission.setAdd(rs.getBoolean("role_add"));
      thisPermission.setEdit(rs.getBoolean("role_edit"));
      thisPermission.setDelete(rs.getBoolean("role_delete"));
      this.put(thisPermission.getName(), thisPermission);
    }
    rs.close();
    pst.close();
    
  }
  
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (roleId > -1) {
      sqlFilter.append("AND r.role_id = ? ");
    }
  }
  
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (roleId > -1) {
      pst.setInt(++i, roleId);
    }
    return i;
  }
  
  public boolean hasPermission(String thisName, String thisType) {
    Iterator i = this.keySet().iterator();
    while (i.hasNext()) {
      Permission thisPermission = (Permission)this.get((String)i.next());
      if ("add".equals(thisType) && thisName.equals(thisPermission.getName()) && thisPermission.getAdd()) {
        return true;
      }
      if ("view".equals(thisType) && thisName.equals(thisPermission.getName()) && thisPermission.getView()) {
        return true;
      }
      if ("edit".equals(thisType) && thisName.equals(thisPermission.getName()) && thisPermission.getEdit()) {
        return true;
      }
      if ("delete".equals(thisType) && thisName.equals(thisPermission.getName()) && thisPermission.getDelete()) {
        return true;
      }
    }
    return false;
  }
}
