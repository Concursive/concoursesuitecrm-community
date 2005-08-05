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

import org.aspcfs.modules.base.Constants;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id$
 * @created February 24, 2003
 */
public class ViewpointPermissionList extends HashMap {

  private int viewpointId = -1;
  private int enabledState = Constants.TRUE;


  /**
   * Constructor for the ViewpointPermissionList object
   */
  public ViewpointPermissionList() {
  }


  /**
   * Constructor for the ViewpointPermissionList object
   *
   * @param db          Description of the Parameter
   * @param viewpointId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ViewpointPermissionList(Connection db, int viewpointId) throws SQLException {
    this.viewpointId = viewpointId;
    buildCombinedList(db);
  }


  /**
   * Constructor for the ViewpointPermissionList object
   *
   * @param request Description of the Parameter
   */
  public ViewpointPermissionList(HttpServletRequest request) {
    int i = 0;
    while (request.getParameter("permission" + (++i) + "id") != null) {
      Permission thisPermission = new Permission();
      thisPermission.buildRecord(request, i);
      this.put("permission" + i, thisPermission);
    }
  }


  /**
   * Sets the enabledState attribute of the ViewpointPermissionList object
   *
   * @param tmp The new enabledState value
   */
  public void setEnabledState(int tmp) {
    this.enabledState = tmp;
  }


  /**
   * Gets the enabledState attribute of the ViewpointPermissionList object
   *
   * @return The enabledState value
   */
  public int getEnabledState() {
    return enabledState;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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
        "SELECT p.*, c.category, viewpoint_add, viewpoint_view, viewpoint_edit, viewpoint_delete " +
        "FROM permission p, permission_category c, viewpoint_permission v " +
        "WHERE p.category_id = c.category_id " +
        "AND p.permission_id = v.permission_id ");
    sqlOrder.append("ORDER BY v.viewpoint_id, c.\"level\", p.\"level\" ");
    createFilter(sqlFilter);
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Permission thisPermission = new Permission(rs);
      thisPermission.setView(rs.getBoolean("viewpoint_view"));
      thisPermission.setAdd(rs.getBoolean("viewpoint_add"));
      thisPermission.setEdit(rs.getBoolean("viewpoint_edit"));
      thisPermission.setDelete(rs.getBoolean("viewpoint_delete"));
      this.put(thisPermission.getName(), thisPermission);
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

    if (enabledState != -1) {
      sqlFilter.append("AND p.enabled = ? ");
      sqlFilter.append("AND c.enabled = ? ");
    }

    if (viewpointId > -1) {
      sqlFilter.append("AND v.viewpoint_id = ? ");
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

    if (enabledState != -1) {
      pst.setBoolean(++i, enabledState == Constants.TRUE);
      pst.setBoolean(++i, enabledState == Constants.TRUE);
    }

    if (viewpointId > -1) {
      pst.setInt(++i, viewpointId);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param thisName Description of the Parameter
   * @param thisType Description of the Parameter
   * @return Description of the Return Value
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
      if ("delete".equals(thisType) && thisName.equals(
          thisPermission.getName()) && thisPermission.getDelete()) {
        return true;
      }
    }
    return false;
  }
}

