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
 *  Represents a list of possible permissions for a permission category
 *
 *@author     matt rajkowski
 *@created    August 11, 2003
 *@version    $Id: PermissionLookupList.java,v 1.1.2.2 2004/04/08 14:55:53
 *      rvasista Exp $
 */
public class PermissionLookupList extends ArrayList {

  private int categoryId = -1;
  private int includeEnabled = Constants.UNDEFINED;


  /**
   *  Constructor for the PermissionLookupList object
   */
  public PermissionLookupList() { }


  /**
   *  Constructor for the PermissionLookupList object
   *
   *@param  db                Description of the Parameter
   *@param  categoryId        Description of the Parameter
   *@param  includeEnabled    Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public PermissionLookupList(Connection db, int categoryId, int includeEnabled) throws SQLException {
    this.categoryId = categoryId;
    this.includeEnabled = includeEnabled;
    this.buildList(db);
  }


  /**
   *  Sets the categoryId attribute of the PermissionLookupList object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the includeEnabled attribute of the PermissionLookupList object
   *
   *@param  tmp  The new includeEnabled value
   */
  public void setIncludeEnabled(int tmp) {
    this.includeEnabled = tmp;
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
        "SELECT * " +
        "FROM lookup_project_permission " +
        "WHERE code > 0 ");
    createFilter(sql);
    sql.append("ORDER BY category_id, level, description ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      PermissionLookup thisPermission = new PermissionLookup(rs);
      this.add(thisPermission);
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
    if (categoryId > -1) {
      sqlFilter.append("AND category_id = ? ");
    }
    if (includeEnabled != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
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
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    if (includeEnabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, includeEnabled == Constants.TRUE);
    }
    return i;
  }
}

