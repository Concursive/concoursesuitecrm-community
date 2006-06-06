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

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    February 28, 2006
 * @version    $Id: Exp$
 */
public class PortfolioCategoryList extends ArrayList {
  //filters
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int parentId = -1;
  private int enabledOnly = Constants.UNDEFINED;
  //other filters
  private boolean buildResources = false;


  /**
   *  Constructor for the PortfolioCategoryList object
   */
  public PortfolioCategoryList() { }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
        " FROM portfolio_category pc " +
        " WHERE pc.category_id > -1 ");
    createFilter(sqlFilter, db);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine column to sort by
      pagedListInfo.setDefaultSort("pc.entered", null);
      boolean flag = true;
      if (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL) {
        if (pagedListInfo.getColumnToSortBy().equals("pc.category_name")) {
          sqlOrder.append(
              "ORDER BY " + DatabaseUtils.convertToVarChar(
              db, "pc.category_name") + (pagedListInfo.getSortOrder() != null ? " DESC " : " "));
          flag = false;
        }
      }
      if (flag) {
        pagedListInfo.appendSqlTail(db, sqlOrder);
      }
    } else {
      sqlOrder.append("ORDER BY pc.category_id ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " pc.* " +
        " FROM portfolio_category pc " +
        " WHERE pc.category_id > -1 ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      PortfolioCategory thisCategory = new PortfolioCategory(rs);
      this.add(thisCategory);
    }
    rs.close();
    pst.close();
    if (buildResources) {
      buildEntries(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of the Parameter
   * @param  db         Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter, Connection db) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (id > -1) {
      sqlFilter.append("AND pc.category_id = ? ");
    }
    if (parentId > -1) {
      sqlFilter.append(" AND pc.parent_category_id = ? ");
    } else {
      sqlFilter.append(" AND pc.parent_category_id IS NULL ");
    }
//enabledOnly is checked at the reorder phase to enable proper ordering
//    if (enabledOnly != Constants.UNDEFINED) {
//      sqlFilter.append(" AND pc.enabled = ? ");
//    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (parentId > -1) {
      pst.setInt(++i, parentId);
    }
//enabledOnly is checked at the reorder phase to enable proper ordering
//    if (enabledOnly != Constants.UNDEFINED) {
//      pst.setBoolean(++i, (enabledOnly == Constants.TRUE));
//    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildEntries(Connection db) throws SQLException {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      PortfolioCategory category = (PortfolioCategory) iter.next();
      category.setBuildChildCategories(true);
      category.setBuildItems(true);
      category.setEnabledOnly(this.getEnabledOnly());
      category.buildResources(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public PortfolioCategoryList reorder() {
    HashMap positionMap = this.getCategoryIdsAsHashMap();
    HashMap categories = this.getCategoriesByPositionAsHashMap();
    PortfolioCategoryList reorderedList = new PortfolioCategoryList();
    reorderedList.setEnabledOnly(this.getEnabledOnly());
    reorderedList.setParentId(this.getParentId());
    String tempCategoryId = "-1";
    for (int i = 0; i < this.size(); i++) {
      PortfolioCategory thisCategory = (PortfolioCategory) categories.get(tempCategoryId);
      if (thisCategory != null) {
        tempCategoryId = (String) positionMap.get(tempCategoryId);
        if (enabledOnly != Constants.UNDEFINED) {
          if (thisCategory.getEnabled() == (enabledOnly == Constants.TRUE)) {
            reorderedList.add(thisCategory);
          }
        } else {
          reorderedList.add(thisCategory);
        }
      }
    }
    return reorderedList;
  }


  /**
   *  Description of the Method
   *
   * @param  context           Description of the Parameter
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public PortfolioCategoryList reset(ActionContext context, Connection db) throws SQLException {
    PortfolioCategoryList oldList = this.reorder();
    HashMap oldOrder = oldList.getCategoryPositionsAsHashMap();
    HashMap newOrder = new HashMap();
    Iterator iter = (Iterator) oldList.iterator();
    while (iter.hasNext()) {
      PortfolioCategory category = (PortfolioCategory) iter.next();
      String position = context.getRequest().getParameter("category" + category.getId());
      if (position.indexOf(".") > -1) {
        position = (new Integer((int) Math.ceil(Double.parseDouble(context.getRequest().getParameter("category" + category.getId()))))).toString();
      }
      if (position == null || "".equals(position)) {
        position = (String) oldOrder.get(String.valueOf(category.getId()));
      }
      //Process the new position and push either the categoryId into the hash map
      //or put the categoryId into the arrayList and put the arrayList into the hash map
      if (newOrder.get(position) != null) {
        Object content = newOrder.get(position);
        if (content instanceof ArrayList) {
          ArrayList tempList = (ArrayList) content;
          int oldPosition = Integer.parseInt((String) oldOrder.get(String.valueOf(category.getId())));
          if (oldPosition > Integer.parseInt(position)) {
            tempList.add(0, String.valueOf(category.getId()));
          } else {
            tempList.add(String.valueOf(category.getId()));
          }
          newOrder.put(position, tempList);
        } else if (content instanceof String) {
          String oldCatId = (String) content;
          ArrayList tempList = new ArrayList();
          tempList.add(oldCatId);
          int oldPosition = Integer.parseInt((String) oldOrder.get(String.valueOf(category.getId())));
          if (oldPosition > Integer.parseInt(position)) {
            tempList.add(0, String.valueOf(category.getId()));
          } else {
            tempList.add(String.valueOf(category.getId()));
          }
          newOrder.put(position, tempList);
        }
      } else {
        newOrder.put(position, String.valueOf(category.getId()));
      }
    }
    //Now the newOrder has the new position ids of the categories.
    HashMap categoriesMap = oldList.getCategoriesAsHashMap();
    PortfolioCategoryList currentList = new PortfolioCategoryList();
    int categoryCounter = 0;
    int currentSize = this.size();
    for (int i = 1; ; i++) {
      if (newOrder.get(String.valueOf(i)) != null) {
        Object content = newOrder.get(String.valueOf(i));
        if (content instanceof String) {
          PortfolioCategory tempCategory = (PortfolioCategory) categoriesMap.get((String) content);
          currentList.add(tempCategory);
          ++categoryCounter;
        } else if (content instanceof ArrayList) {
          ArrayList tempList = (ArrayList) content;
          for (int j = 0; j < tempList.size(); j++) {
            PortfolioCategory tempCategory = (PortfolioCategory) categoriesMap.get((String) tempList.get(j));
            currentList.add(tempCategory);
            ++categoryCounter;
          }
        }
      }
      if (categoryCounter >= (currentSize)) {
        break;
      }
    }
    currentList.setEnabledOnly(this.getEnabledOnly());
    currentList.setParentId(this.getParentId());
    //Reorder and reset the position and update the changes.
    iter = (Iterator) currentList.iterator();
    if (iter.hasNext()) {
      PortfolioCategory firstCategory = (PortfolioCategory) iter.next();
      firstCategory.setPositionId(-1);
      if (firstCategory.getPositionIdHasChanged()) {
        firstCategory.update(db);
      }
      Iterator iter2 = (Iterator) currentList.iterator();
      while (iter.hasNext()) {
        PortfolioCategory parentCategory = (PortfolioCategory) iter2.next();
        PortfolioCategory category = (PortfolioCategory) iter.next();
        category.setPositionId(parentCategory.getId());
        if (category.getPositionIdHasChanged()) {
          category.update(db);
        }
      }
    }
    return currentList;
  }


  /**
   *  Description of the Method
   *
   * @param  list  Description of the Parameter
   * @return       Description of the Return Value
   */
  public String printArray(ArrayList list) {
    StringBuffer str = new StringBuffer();
    for (int i = 0; i < list.size(); i++) {
      str.append("\nprintList:: list[" + i + "] = " + list.get(i));
    }
    return str.toString();
  }


  /**
   *  Gets the categoryIdsAsHashMap attribute of the PortfolioCategoryList
   *  object
   *
   * @return    The categoryIdsAsHashMap value
   */
  public HashMap getCategoryIdsAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      PortfolioCategory category = (PortfolioCategory) iter.next();
      map.put(String.valueOf(category.getPositionId()), String.valueOf(category.getId()));
    }
    return map;
  }


  /**
   *  Gets the categoriesByPositionAsHashMap attribute of the
   *  PortfolioCategoryList object
   *
   * @return    The categoriesByPositionAsHashMap value
   */
  public HashMap getCategoriesByPositionAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      PortfolioCategory category = (PortfolioCategory) iter.next();
      map.put(String.valueOf(category.getPositionId()), category);
    }
    return map;
  }


  /**
   *  Gets the categoriesAsHashMap attribute of the PortfolioCategoryList object
   *
   * @return    The categoriesAsHashMap value
   */
  public HashMap getCategoriesAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      PortfolioCategory category = (PortfolioCategory) iter.next();
      map.put(String.valueOf(category.getId()), category);
    }
    return map;
  }


  /**
   *  Gets the categoryPositionsAsHashMap attribute of the PortfolioCategoryList
   *  object
   *
   * @return    The categoryPositionsAsHashMap value
   */
  public HashMap getCategoryPositionsAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    for (int i = 1; iter.hasNext(); i++) {
      PortfolioCategory category = (PortfolioCategory) iter.next();
      map.put(String.valueOf(category.getId()), String.valueOf(i));
    }
    return map;
  }


  /**
   *  Gets the categoryById attribute of the PortfolioCategoryList object
   *
   * @param  id  Description of the Parameter
   * @return     The categoryById value
   */
  public PortfolioCategory getCategoryById(int id) {
    PortfolioCategory result = null;
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      PortfolioCategory category = (PortfolioCategory) iter.next();
      if (category.getId() == id) {
        result = category;
        break;
      }
    }
    return result;
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
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      PortfolioCategory category = (PortfolioCategory) iter.next();
      category.delete(db, baseFilePath);
    }
    return true;
  }


  /**
   *  Gets the idByIndex attribute of the PortfolioCategoryList object
   *
   * @param  index  Description of the Parameter
   * @return        The idByIndex value
   */
  public int getIdByIndex(int index) {
    int result = -1;
    if (this.size() > 0 && index < this.size()) {
      PortfolioCategory category = (PortfolioCategory) this.get(index);
      if (category != null) {
        result = category.getId();
      }
    }
    return result;
  }


  /**
   *  Gets the lastCategoryId attribute of the PortfolioCategoryList object
   *
   * @return    The lastCategoryId value
   */
  public int getLastCategoryId() {
    return this.getIdByIndex(this.size() - 1);
  }


  /**
   *  Description of the Method
   *
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public PortfolioCategoryList buildCompleteHierarchy() throws SQLException {
    for (int i = 0; i < this.size(); i++) {
      PortfolioCategory thisCategory = (PortfolioCategory) this.get(i);
      if (thisCategory.getChildCategories().size() > 0) {
        this.addAll(i + 1, thisCategory.getChildCategories());
      }
    }
    return this;
  }


  /*
   *  Get and Set Methods
   */
  /**
   *  Gets the pagedListInfo attribute of the PortfolioCategoryList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the pagedListInfo attribute of the PortfolioCategoryList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the id attribute of the PortfolioCategoryList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the PortfolioCategoryList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PortfolioCategoryList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the parentId attribute of the PortfolioCategoryList object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Sets the parentId attribute of the PortfolioCategoryList object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the PortfolioCategoryList object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the enabledOnly attribute of the PortfolioCategoryList object
   *
   * @return    The enabledOnly value
   */
  public int getEnabledOnly() {
    return enabledOnly;
  }


  /**
   *  Sets the enabledOnly attribute of the PortfolioCategoryList object
   *
   * @param  tmp  The new enabledOnly value
   */
  public void setEnabledOnly(int tmp) {
    this.enabledOnly = tmp;
  }


  /**
   *  Sets the enabledOnly attribute of the PortfolioCategoryList object
   *
   * @param  tmp  The new enabledOnly value
   */
  public void setEnabledOnly(String tmp) {
    this.enabledOnly = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildResources attribute of the PortfolioCategoryList object
   *
   * @return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Sets the buildResources attribute of the PortfolioCategoryList object
   *
   * @param  tmp  The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the buildResources attribute of the PortfolioCategoryList object
   *
   * @param  tmp  The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }

}

