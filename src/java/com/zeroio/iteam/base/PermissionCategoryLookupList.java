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
 *  Represents a list of possible permission categories of a project
 *
 *@author     matt rajkowski
 *@created    August 11, 2003
 *@version    $Id: PermissionCategoryLookupList.java,v 1.1.2.2 2004/04/08
 *      14:55:53 rvasista Exp $
 */
public class PermissionCategoryLookupList extends ArrayList {

  private int includeEnabled = Constants.UNDEFINED;


  /**
   *  Constructor for the PermissionCategoryLookupList object
   */
  public PermissionCategoryLookupList() { }


  /**
   *  Sets the includeEnabled attribute of the PermissionCategoryLookupList
   *  object
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
        "FROM lookup_project_permission_category " +
        "WHERE code > 0 ");
    createFilter(sql);
    sql.append("ORDER BY level, description ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      PermissionCategoryLookup thisCategory = new PermissionCategoryLookup(rs);
      this.add(thisCategory);
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
    if (includeEnabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, includeEnabled == Constants.TRUE);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildResources(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      PermissionCategoryLookup category = (PermissionCategoryLookup) i.next();
      category.buildResources(db, includeEnabled);
    }
  }
}

