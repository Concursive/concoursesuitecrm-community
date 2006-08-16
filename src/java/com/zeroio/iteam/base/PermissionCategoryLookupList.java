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
import java.util.Iterator;

/**
 * Represents a list of possible permission categories of a project
 *
 * @author matt rajkowski
 * @version $Id: PermissionCategoryLookupList.java,v 1.1.2.2 2004/04/08
 *          14:55:53 rvasista Exp $
 * @created August 11, 2003
 */
public class PermissionCategoryLookupList extends ArrayList {

  private int includeEnabled = Constants.UNDEFINED;


  /**
   * Constructor for the PermissionCategoryLookupList object
   */
  public PermissionCategoryLookupList() {
  }


  /**
   * Sets the includeEnabled attribute of the PermissionCategoryLookupList
   * object
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
        "FROM " + DatabaseUtils.getTableName(db, "lookup_project_permission_category") +" " +
        "WHERE code > 0 ");
    createFilter(sql);
    sql.append("ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ", description ");
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
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
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
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (includeEnabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, includeEnabled == Constants.TRUE);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResources(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      PermissionCategoryLookup category = (PermissionCategoryLookup) i.next();
      category.buildResources(db, includeEnabled);
    }
  }
}

