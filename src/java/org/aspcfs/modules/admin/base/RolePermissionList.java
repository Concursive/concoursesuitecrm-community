//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.admin.base;

import java.util.Hashtable;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.database.Connection;
import org.aspcfs.modules.utils.web.*;
import org.aspcfs.modules.utils.DatabaseUtils;
import org.aspcfs.modules.modules.admin.base.Permission;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    January 13, 2003
 *@version    $Id$
 */
public class RolePermissionList extends Hashtable {

  private int roleId = -1;
  private int enabledState = Constants.TRUE;


  /**
   *  Constructor for the RolePermissionList object
   */
  public RolePermissionList() { }


  /**
   *  Constructor for the RolePermissionList object
   *
   *@param  db                Description of the Parameter
   *@param  roleId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public RolePermissionList(Connection db, int roleId) throws SQLException {
    this.roleId = roleId;
    buildCombinedList(db);
  }


  /**
   *  Constructor for the RolePermissionList object
   *
   *@param  request  Description of the Parameter
   */
  public RolePermissionList(HttpServletRequest request) {
    int i = 0;
    while (request.getParameter("permission" + (++i) + "id") != null) {
      Permission thisPermission = new Permission();
      thisPermission.buildRecord(request, i);
      this.put("permission" + i, thisPermission);
    }
  }


  /**
   *  Sets the enabledState attribute of the RolePermissionList object
   *
   *@param  tmp  The new enabledState value
   */
  public void setEnabledState(int tmp) {
    this.enabledState = tmp;
  }


  /**
   *  Gets the enabledState attribute of the RolePermissionList object
   *
   *@return    The enabledState value
   */
  public int getEnabledState() {
    return enabledState;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildCombinedList(Connection db) throws SQLException {
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
        "AND p.permission_id = r.permission_id ");
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


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (enabledState != -1) {
      sqlFilter.append("AND p.enabled = ? ");
      sqlFilter.append("AND c.enabled = ? ");
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

    if (enabledState != -1) {
      pst.setBoolean(++i, enabledState == Constants.TRUE);
      pst.setBoolean(++i, enabledState == Constants.TRUE);
    }

    if (roleId > -1) {
      pst.setInt(++i, roleId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  thisName  Description of the Parameter
   *@param  thisType  Description of the Parameter
   *@return           Description of the Return Value
   */
  public boolean hasPermission(String thisName, String thisType) {
    Iterator i = this.keySet().iterator();
    while (i.hasNext()) {
      Permission thisPermission = (Permission) this.get((String) i.next());
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

