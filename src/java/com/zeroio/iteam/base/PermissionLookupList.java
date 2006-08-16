/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Represents a list of possible permissions for a permission category
 *
 * @author matt rajkowski
 * @version $Id: PermissionLookupList.java,v 1.1.2.2 2004/04/08 14:55:53
 *          rvasista Exp $
 * @created August 11, 2003
 */
public class PermissionLookupList extends ArrayList {

  private int categoryId = -1;
  private int includeEnabled = Constants.UNDEFINED;


  /**
   * Constructor for the PermissionLookupList object
   */
  public PermissionLookupList() {
  }


  /**
   * Constructor for the PermissionLookupList object
   *
   * @param db             Description of the Parameter
   * @param categoryId     Description of the Parameter
   * @param includeEnabled Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public PermissionLookupList(Connection db, int categoryId, int includeEnabled) throws SQLException {
    this.categoryId = categoryId;
    this.includeEnabled = includeEnabled;
    this.buildList(db);
  }


  /**
   * Sets the categoryId attribute of the PermissionLookupList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the includeEnabled attribute of the PermissionLookupList object
   *
   * @param tmp The new includeEnabled value
   */
  public void setIncludeEnabled(int tmp) {
    this.includeEnabled = tmp;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM lookup_project_permission " +
        "WHERE code > 0 ");
    createFilter(sql);
    sql.append("ORDER BY category_id, " + DatabaseUtils.addQuotes(db, "level") + ", description ");
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
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
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
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
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

