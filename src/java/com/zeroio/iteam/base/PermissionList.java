/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  Represents a list of permissions for a project
 *
 *@author     matt rajkowski
 *@created    August 10, 2003
 *@version    $Id: PermissionList.java,v 1.1.2.2 2004/04/08 14:55:53 rvasista
 *      Exp $
 */
public class PermissionList extends HashMap {

  private int projectId = -1;


  /**
   *  Constructor for the PermissionList object
   */
  public PermissionList() { }


  /**
   *  Sets the projectId attribute of the PermissionList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT lp.permission, p.userlevel " +
        "FROM project_permissions p, lookup_project_permission lp " +
        "WHERE p.permission_id = lp.code ");
    createFilter(sql);
    PreparedStatement pst = db.prepareStatement(sql.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      this.put(rs.getString("permission"), new Integer(rs.getInt("userlevel")));
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
    if (projectId > -1) {
      sqlFilter.append("AND project_id = ? ");
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
    if (projectId > -1) {
      pst.setInt(++i, projectId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  permissionName  Description of the Parameter
   *@param  userLevel       Description of the Parameter
   *@return                 Description of the Return Value
   */
  public boolean hasPermission(String permissionName, int userLevel) {
    Integer value = (Integer) this.get(permissionName);

    return true;
  }


  /**
   *  Gets the accessLevel attribute of the PermissionList object
   *
   *@param  permissionName  Description of the Parameter
   *@return                 The accessLevel value
   */
  public int getAccessLevel(String permissionName) {
    Integer value = (Integer) this.get(permissionName);
    if (value == null) {
      return 1;
    } else {
      return value.intValue();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  request           Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void updateProjectPermissions(Connection db, HttpServletRequest request, int projectId) throws SQLException {
    //Look through the request and put the permissions in buckets
    try {
      db.setAutoCommit(false);
      //Delete the previous settings
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM project_permissions " +
          "WHERE project_id = ? ");
      pst.setInt(1, projectId);
      pst.execute();
      pst.close();
      //Insert the new settings
      int count = 0;
      String permissionId = null;
      while ((permissionId = request.getParameter("perm" + (++count))) != null) {
        pst = db.prepareStatement(
            "INSERT INTO project_permissions (project_id, permission_id, userlevel) " +
            "VALUES (?, ?, ?)");
        pst.setInt(1, projectId);
        pst.setInt(2, Integer.parseInt(permissionId));
        pst.setInt(3, Integer.parseInt(request.getParameter("perm" + count + "level")));
        pst.execute();
      }
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void delete(Connection db, int projectId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM project_permissions " +
        "WHERE project_id = ? ");
    pst.setInt(1, projectId);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void insertDefaultPermissions(Connection db, int projectId) throws SQLException {
    //Make sure no permissions exist, then insert
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS perm_count " +
        "FROM project_permissions " +
        "WHERE project_id = ? ");
    pst.setInt(1, projectId);
    ResultSet rs = pst.executeQuery();
    rs.next();
    int count = rs.getInt("perm_count");
    rs.close();
    pst.close();
    //Insert the permissions
    if (count == 0) {
      PermissionLookupList list = new PermissionLookupList();
      list.setIncludeEnabled(Constants.TRUE);
      list.buildList(db);
      Iterator i = list.iterator();
      while (i.hasNext()) {
        PermissionLookup thisPermission = (PermissionLookup) i.next();
        pst = db.prepareStatement(
            "INSERT INTO project_permissions " +
            "(project_id, permission_id, userlevel) VALUES (?, ?, ?) ");
        pst.setInt(1, projectId);
        pst.setInt(2, thisPermission.getId());
        pst.setInt(3, thisPermission.getDefaultRole());
        pst.execute();
      }
      pst.close();
    }
  }
}

