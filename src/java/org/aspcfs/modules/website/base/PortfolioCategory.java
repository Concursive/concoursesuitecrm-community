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
package org.aspcfs.modules.website.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    February 28, 2006
 * @version    $Id: Exp$
 */
public class PortfolioCategory extends GenericBean {
  private int id = -1;
  private String name = null;
  private String description = null;
  private int positionId = -1;
  private int parentId = -1;
  private boolean enabled = false;
  private Timestamp entered = null;
  private Timestamp modified = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  //related fields
  private boolean buildChildCategories = false;
  private PortfolioCategoryList childCategories = null;
  private boolean buildItems = false;
  private PortfolioItemList items = null;
  private int enabledOnly = Constants.UNDEFINED;
  private boolean positionIdHasChanged = false;
  private int level = -1;


  /**
   *  Constructor for the PortfolioCategory object
   */
  public PortfolioCategory() { }


  /**
   *  Constructor for the PortfolioCategory object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public PortfolioCategory(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the PortfolioCategory object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public PortfolioCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Portfolio Category ID");
    }
    StringBuffer sb = new StringBuffer(
        " SELECT pc.* " +
        " FROM portfolio_category pc " +
        " WHERE pc.category_id = ? ");
    PreparedStatement pst = db.prepareStatement(sb.toString());
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //portfolio_category table
    this.setId(rs.getInt("category_id"));
    name = rs.getString("category_name");
    description = rs.getString("category_description");
    positionId = DatabaseUtils.getInt(rs, "category_position_id");
    parentId = DatabaseUtils.getInt(rs, "parent_category_id");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      StringBuffer sql = new StringBuffer();
      id = DatabaseUtils.getNextSeq(db, "portfolio_cat_y_category_id_seq");
      sql.append(
          "INSERT INTO portfolio_category (category_name, category_description, " +
          "category_position_id, parent_category_id, enabled, ");
      if (id > -1) {
        sql.append("category_id, ");
      }
      sql.append("entered, ");
      sql.append("enteredby, ");
      sql.append("modified, ");
      sql.append("modifiedby )");
      sql.append(" VALUES (?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ");
      if (modified != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("? )");
      int i = 0;

      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, this.getName());
      pst.setString(++i, this.getDescription());
      DatabaseUtils.setInt(pst, ++i, this.getPositionId());
      DatabaseUtils.setInt(pst, ++i, this.getParentId());
      pst.setBoolean(++i, this.getEnabled());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      pst.setInt(++i, this.getModifiedBy());
      if (modified != null) {
        pst.setTimestamp(++i, this.getModified());
      }
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "portfolio_cat_y_category_id_seq", id);
      if (commit) {
        db.commit();
      }
      result = true;
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    boolean commit = false;
    if (this.getId() == -1) {
      return -1;
    }
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      // NOTE: For daffodil, needs to happen before the UPDATE quote_entry
      PreparedStatement pst = null;
      StringBuffer sql = new StringBuffer();
      sql.append(
          "UPDATE portfolio_category " +
          "SET category_name = ?, " +
          "category_description = ?, " +
          "category_position_id = ?, " +
          "parent_category_id = ?, " +
          "enabled = ?, " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
          "modifiedby = ? " +
          "WHERE category_id = ? ");
      int i = 0;
      pst = db.prepareStatement(sql.toString());
      pst.setString(++i, this.getName());
      pst.setString(++i, this.getDescription());
      DatabaseUtils.setInt(pst, ++i, this.getPositionId());
      DatabaseUtils.setInt(pst, ++i, this.getParentId());
      pst.setBoolean(++i, this.getEnabled());
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getId());
      resultCount = pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Portfolio Category ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    // Check for this portfolio_category's dependencies
    //Check the items in the portfolio category
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as itemcount " +
          "FROM portfolio_item " +
          "WHERE portfolio_category_id = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int itemCount = rs.getInt("itemcount");
        if (itemCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("portfolioItems");
          thisDependency.setCount(itemCount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }

    //Check the items in the portfolio category
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as categorycount " +
          "FROM portfolio_category " +
          "WHERE parent_category_id = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int categoryCount = rs.getInt("categorycount");
        if (categoryCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("portfolioCategories");
          thisDependency.setCount(categoryCount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    /*
     *  try {
     *  i = 0;
     *  pst = db.prepareStatement(
     *  "SELECT count(*) as parentcount " +
     *  "FROM portfolio_category " +
     *  "WHERE XXX = ? ");
     *  pst.setInt(++i, this.getId());
     *  rs = pst.executeQuery();
     *  if (rs.next()) {
     *  int XXXCount = rs.getInt("XXXcount");
     *  if (XXXCount != 0) {
     *  Dependency thisDependency = new Dependency();
     *  thisDependency.setName("XXXXXXXXX");
     *  thisDependency.setCount(XXXCount);
     *  thisDependency.setCanDelete(true);
     *  dependencyList.add(thisDependency);
     *  }
     *  }
     *  rs.close();
     *  pst.close();
     *  } catch (SQLException e) {
     *  throw new SQLException(e.getMessage());
     *  }
     */
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  baseFilePath      Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Portfolio Category ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      PreparedStatement pst = null;
      ResultSet rs = null;

      this.setBuildChildCategories(true);
      this.setBuildItems(true);
      this.buildResources(db);

      //delete the child categories
      this.getChildCategories().delete(db, baseFilePath);

      //delete the items under this category
      this.getItems().delete(db, baseFilePath);

      //reset the position of all the categories
      int nextPositionId = this.getNextPositionId(db);
      if (nextPositionId != -1) {
        PortfolioCategory nextPosition = new PortfolioCategory(db, nextPositionId);
        nextPosition.setPositionId(this.getPositionId());
        nextPosition.update(db);
      }
      // delete the quote
      pst = db.prepareStatement("DELETE FROM portfolio_category WHERE category_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Gets the timeZoneParams attribute of the PortfolioCategory class
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("entered");
    thisList.add("modified");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildResources(Connection db) throws SQLException {
    if (buildChildCategories) {
      childCategories = new PortfolioCategoryList();
      childCategories.setParentId(this.getId());
      childCategories.setEnabledOnly(this.getEnabledOnly());
      childCategories.buildList(db);
    }
    if (buildItems) {
      items = new PortfolioItemList();
      items.setCategoryId(this.getId());
      items.setEnabledOnly(this.getEnabledOnly());
      items.buildList(db);
    }
//System.out.println("PortfolioCategory::buildResources the childCategories is "+ (childCategories!= null? childCategories:" it is null"));
//System.out.println("PortfolioCategory::buildResources the items is "+ (items!= null? items:" it is null"));
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  hierarchy         Description of the Parameter
   * @param  currentId         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void buildHierarchy(Connection db, Map hierarchy, int currentId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT parent_category_id, category_name " +
        "FROM portfolio_category " +
        "WHERE category_id = ? ");
    pst.setInt(1, currentId);
    ResultSet rs = pst.executeQuery();
    int parentId = 0;
    String name = null;
    if (rs.next()) {
      parentId = DatabaseUtils.getInt(rs, "parent_category_id");
      name = rs.getString("category_name");
    }
    rs.close();
    pst.close();
    hierarchy.put(new Integer(currentId), new String[]{name});
    if (parentId > -1) {
      PortfolioCategory.buildHierarchy(db, hierarchy, parentId);
    }
  }


  /**
   *  Gets the nextPositionId attribute of the PortfolioCategory object
   *
   * @param  db                Description of the Parameter
   * @return                   The nextPositionId value
   * @exception  SQLException  Description of the Exception
   */
  public int getNextPositionId(Connection db) throws SQLException {
    int result = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT category_id FROM portfolio_category WHERE category_position_id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = DatabaseUtils.getInt(rs, "category_id");
    }
    rs.close();
    pst.close();
    return result;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public String toString() {
    return this.getName();
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the id attribute of the PortfolioCategory object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the PortfolioCategory object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PortfolioCategory object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the name attribute of the PortfolioCategory object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Sets the name attribute of the PortfolioCategory object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the description attribute of the PortfolioCategory object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the PortfolioCategory object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Gets the positionId attribute of the PortfolioCategory object
   *
   * @return    The positionId value
   */
  public int getPositionId() {
    return positionId;
  }


  /**
   *  Sets the positionId attribute of the PortfolioCategory object
   *
   * @param  tmp  The new positionId value
   */
  public void setPositionId(int tmp) {
    if (this.positionId != tmp) {
      this.setPositionIdHasChanged(true);
    }
    this.positionId = tmp;
  }


  /**
   *  Sets the positionId attribute of the PortfolioCategory object
   *
   * @param  tmp  The new positionId value
   */
  public void setPositionId(String tmp) {
    if (Integer.parseInt(tmp) != this.positionId) {
      this.setPositionIdHasChanged(true);
    }
    this.positionId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the parentId attribute of the PortfolioCategory object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Sets the parentId attribute of the PortfolioCategory object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the PortfolioCategory object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the enabled attribute of the PortfolioCategory object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the enabled attribute of the PortfolioCategory object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the PortfolioCategory object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the entered attribute of the PortfolioCategory object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the PortfolioCategory object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the PortfolioCategory object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the modified attribute of the PortfolioCategory object
   *
   * @return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the modified attribute of the PortfolioCategory object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the PortfolioCategory object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the PortfolioCategory object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the PortfolioCategory object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the PortfolioCategory object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modifiedBy attribute of the PortfolioCategory object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the PortfolioCategory object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the PortfolioCategory object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildChildCategories attribute of the PortfolioCategory object
   *
   * @return    The buildChildCategories value
   */
  public boolean getBuildChildCategories() {
    return buildChildCategories;
  }


  /**
   *  Sets the buildChildCategories attribute of the PortfolioCategory object
   *
   * @param  tmp  The new buildChildCategories value
   */
  public void setBuildChildCategories(boolean tmp) {
    this.buildChildCategories = tmp;
  }


  /**
   *  Sets the buildChildCategories attribute of the PortfolioCategory object
   *
   * @param  tmp  The new buildChildCategories value
   */
  public void setBuildChildCategories(String tmp) {
    this.buildChildCategories = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the childCategories attribute of the PortfolioCategory object
   *
   * @return    The childCategories value
   */
  public PortfolioCategoryList getChildCategories() {
    return childCategories;
  }


  /**
   *  Sets the childCategories attribute of the PortfolioCategory object
   *
   * @param  tmp  The new childCategories value
   */
  public void setChildCategories(PortfolioCategoryList tmp) {
    this.childCategories = tmp;
  }


  /**
   *  Gets the buildItems attribute of the PortfolioCategory object
   *
   * @return    The buildItems value
   */
  public boolean getBuildItems() {
    return buildItems;
  }


  /**
   *  Sets the buildItems attribute of the PortfolioCategory object
   *
   * @param  tmp  The new buildItems value
   */
  public void setBuildItems(boolean tmp) {
    this.buildItems = tmp;
  }


  /**
   *  Sets the buildItems attribute of the PortfolioCategory object
   *
   * @param  tmp  The new buildItems value
   */
  public void setBuildItems(String tmp) {
    this.buildItems = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the items attribute of the PortfolioCategory object
   *
   * @return    The items value
   */
  public PortfolioItemList getItems() {
    return items;
  }


  /**
   *  Sets the items attribute of the PortfolioCategory object
   *
   * @param  tmp  The new items value
   */
  public void setItems(PortfolioItemList tmp) {
    this.items = tmp;
  }


  /**
   *  Gets the enabledOnly attribute of the PortfolioCategory object
   *
   * @return    The enabledOnly value
   */
  public int getEnabledOnly() {
    return enabledOnly;
  }


  /**
   *  Sets the enabledOnly attribute of the PortfolioCategory object
   *
   * @param  tmp  The new enabledOnly value
   */
  public void setEnabledOnly(int tmp) {
    this.enabledOnly = tmp;
  }


  /**
   *  Sets the enabledOnly attribute of the PortfolioCategory object
   *
   * @param  tmp  The new enabledOnly value
   */
  public void setEnabledOnly(String tmp) {
    this.enabledOnly = Integer.parseInt(tmp);
  }


  /**
   *  Gets the positionIdHasChanged attribute of the PortfolioCategory object
   *
   * @return    The positionIdHasChanged value
   */
  public boolean getPositionIdHasChanged() {
    return positionIdHasChanged;
  }


  /**
   *  Sets the positionIdHasChanged attribute of the PortfolioCategory object
   *
   * @param  tmp  The new positionIdHasChanged value
   */
  public void setPositionIdHasChanged(boolean tmp) {
    this.positionIdHasChanged = tmp;
  }


  /**
   *  Sets the positionIdHasChanged attribute of the PortfolioCategory object
   *
   * @param  tmp  The new positionIdHasChanged value
   */
  public void setPositionIdHasChanged(String tmp) {
    this.positionIdHasChanged = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the level attribute of the PortfolioCategory object
   *
   * @return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Sets the level attribute of the PortfolioCategory object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the PortfolioCategory object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }
}

