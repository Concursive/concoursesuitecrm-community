//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.database.Connection;
import org.aspcfs.modules.utils.web.*;
import org.aspcfs.modules.modules.admin.base.*;
import org.aspcfs.modules.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    January 13, 2003
 *@version    $Id$
 */
public class UserPermissionList extends Vector {

  private int roleId = -1;


  /**
   *  Constructor for the UserPermissionList object
   */
  public UserPermissionList() { }


  /**
   *  Constructor for the UserPermissionList object
   *
   *@param  db                Description of the Parameter
   *@param  roleId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public UserPermissionList(Connection db, int roleId) throws SQLException {
    this.roleId = roleId;
    buildList(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
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
        "AND p.enabled = " + DatabaseUtils.getTrue(db) + " " +
        "AND c.enabled = " + DatabaseUtils.getTrue(db) + " ");

    sqlOrder.append("ORDER BY role_id, c.level, p.level ");

    createFilter(sqlFilter);
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Permission thisPermission = new Permission(rs);
      thisPermission.setAdd(rs.getBoolean("role_add"));
      thisPermission.setView(rs.getBoolean("role_view"));
      thisPermission.setEdit(rs.getBoolean("role_edit"));
      thisPermission.setDelete(rs.getBoolean("role_delete"));
      this.addElement(thisPermission);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (roleId > -1) {
      sqlFilter.append("AND r.role_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (roleId > -1) {
      pst.setInt(++i, roleId);
    }
    return i;
  }

}

