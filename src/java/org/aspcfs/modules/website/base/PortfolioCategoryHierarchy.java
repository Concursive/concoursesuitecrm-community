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
package org.aspcfs.modules.website.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    February 28, 2006
 * @version    $Id: Exp$
 */
public class PortfolioCategoryHierarchy {

  private PortfolioCategoryList hierarchy = null;


  /**
   *  Sets the hierarchy attribute of the PortfolioCategoryHierarchy object
   *
   * @param  tmp  The new hierarchy value
   */
  public void setHierarchy(PortfolioCategoryList tmp) {
    this.hierarchy = tmp;
  }


  /**
   *  Gets the hierarchy attribute of the PortfolioCategoryHierarchy object
   *
   * @return    The hierarchy value
   */
  public PortfolioCategoryList getHierarchy() {
    return hierarchy;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void build(Connection db) throws SQLException {
    hierarchy = new PortfolioCategoryList();
    hierarchy.setParentId(-1);
    hierarchy.buildList(db);
    buildItems(db, hierarchy, 1);
    PortfolioCategoryList buffer = hierarchy.buildCompleteHierarchy();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  parentId          Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void build(Connection db, int parentId) throws SQLException {
    hierarchy = new PortfolioCategoryList();
    hierarchy.setParentId(parentId);
    hierarchy.buildList(db);
    buildItems(db, hierarchy, 1);
    PortfolioCategoryList buffer = hierarchy.buildCompleteHierarchy();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  categoryList      Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  private void buildItems(Connection db, PortfolioCategoryList categoryList) throws SQLException {
    Iterator i = categoryList.iterator();
    while (i.hasNext()) {
      PortfolioCategory category = (PortfolioCategory) i.next();
      category.setBuildChildCategories(true);
      category.buildResources(db);
      buildItems(db, category.getChildCategories());
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  categoryList      Description of the Parameter
   * @param  level             Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  private void buildItems(Connection db, PortfolioCategoryList categoryList, int level) throws SQLException {
    Iterator i = (Iterator) categoryList.iterator();
    while (i.hasNext()) {
      PortfolioCategory category = (PortfolioCategory) i.next();
      category.setLevel(level);
      category.setBuildChildCategories(true);
      category.buildResources(db);
      buildItems(db, category.getChildCategories(), level + 1);
    }
  }
}

