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
package org.aspcfs.modules.admin.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.aspcfs.utils.DatabaseUtils;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id$
 * @created January 13, 2003
 */
public class UserPermissionList extends Vector {
  private int roleId = -1;


  /**
   * Constructor for the UserPermissionList object
   */
  public UserPermissionList() {
  }


  /**
   * Constructor for the UserPermissionList object
   *
   * @param db     Description of the Parameter
   * @param roleId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public UserPermissionList(Connection db, int roleId) throws SQLException {
    this.roleId = roleId;
    buildList(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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
        "SELECT p.*, c.category, role_add, role_view, role_edit, role_delete, " +
        "role_offline_view, role_offline_add, role_offline_edit, role_offline_delete " +
        "FROM permission p, permission_category c, role_permission r " +
        "WHERE p.category_id = c.category_id " +
        "AND p.permission_id = r.permission_id ");
    sqlOrder.append("ORDER BY role_id, c." + DatabaseUtils.addQuotes(db, "level") + ", p." + DatabaseUtils.addQuotes(db, "level") + " ");
    createFilter(sqlFilter);
    sqlFilter.append("AND p.enabled = ? ");
    sqlFilter.append("AND c.enabled = ? ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    pst.setBoolean(++items, true);
    pst.setBoolean(++items, true);
    rs = pst.executeQuery();
    while (rs.next()) {
      Permission thisPermission = new Permission(rs);
      thisPermission.setAdd(rs.getBoolean("role_add"));
      thisPermission.setView(rs.getBoolean("role_view"));
      thisPermission.setEdit(rs.getBoolean("role_edit"));
      thisPermission.setDelete(rs.getBoolean("role_delete"));
      thisPermission.setOfflineView(rs.getBoolean("role_offline_view"));
      thisPermission.setOfflineAdd(rs.getBoolean("role_offline_add"));
      thisPermission.setOfflineEdit(rs.getBoolean("role_offline_edit"));
      thisPermission.setOfflineDelete(rs.getBoolean("role_offline_delete"));
      this.addElement(thisPermission);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
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
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (roleId > -1) {
      pst.setInt(++i, roleId);
    }
    return i;
  }

}

