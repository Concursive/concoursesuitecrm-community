/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.documents.base;

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
 * @author Kailash
 * @version $Id$
 * @created 2004-12-02
 */
public class DocumentStorePermissionCategoryLookupList extends ArrayList {

  private int includeEnabled = Constants.UNDEFINED;


  /**
   * Constructor for the DocumentStorePermissionCategoryLookupList object
   */
  public DocumentStorePermissionCategoryLookupList() {
  }


  /**
   * Sets the includeEnabled attribute of the DocumentStorePermissionCategoryLookupList
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
        "FROM " + DatabaseUtils.getTableName(db, "lookup_document_store_permission_category") + " " +
        "WHERE code > 0 ");
    createFilter(sql);
    sql.append("ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ", description ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      DocumentStorePermissionCategoryLookup thisCategory = new DocumentStorePermissionCategoryLookup(
          rs);
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
      DocumentStorePermissionCategoryLookup category = (DocumentStorePermissionCategoryLookup) i.next();
      category.buildResources(db, includeEnabled);
    }
  }

  public static int retrieveMaxLevel(Connection db) throws SQLException {
    int maxLevel = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT MAX(" + DatabaseUtils.addQuotes(db, "level")+ ") AS max_level " +
        "FROM " + DatabaseUtils.getTableName(db, "lookup_document_store_permission_category") + " ");
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      maxLevel = rs.getInt("max_level");
    }
    rs.close();
    pst.close();
    return maxLevel;
  }
}

