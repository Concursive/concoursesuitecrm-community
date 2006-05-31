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
public class PortfolioItemList extends ArrayList {
  //filters
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int categoryId = -1;
  private int enabledOnly = Constants.UNDEFINED;


  /**
   *  Constructor for the PortfolioItemList object
   */
  public PortfolioItemList() { }


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
        " FROM portfolio_item pi " +
        " WHERE pi.item_id > -1 ");
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
      pagedListInfo.setDefaultSort("pi.entered", null);
      boolean flag = true;
      if (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL) {
        if (pagedListInfo.getColumnToSortBy().equals("pi.item_name")) {
          sqlOrder.append(
              "ORDER BY " + DatabaseUtils.convertToVarChar(
              db, "pi.item_name") + (pagedListInfo.getSortOrder() != null ? " DESC " : " "));
          flag = false;
        }
      }
      if (flag) {
        pagedListInfo.appendSqlTail(db, sqlOrder);
      }
    } else {
      sqlOrder.append("ORDER BY pi.item_id ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " pi.* " +
        " FROM portfolio_item pi " +
        " WHERE pi.item_id > -1 ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
		if (System.getProperty("DEBUG") != null){
//			System.out.println(pst);
		}
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      PortfolioItem thisItem = new PortfolioItem(rs);
      this.add(thisItem);
    }
    rs.close();
    pst.close();
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
      sqlFilter.append("AND pi.item_id = ? ");
    }
    if (categoryId > -1) {
      sqlFilter.append(" AND pi.portfolio_category_id = ? ");
    } else {
      sqlFilter.append("AND pi.portfolio_category_id IS NULL ");
    }
    if (enabledOnly != Constants.UNDEFINED) {
      sqlFilter.append(" AND pi.enabled = ? ");
    }
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
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    if (enabledOnly != Constants.UNDEFINED) {
      pst.setBoolean(++i, (enabledOnly == Constants.TRUE));
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public PortfolioItemList reorder() {
    HashMap positionMap = this.getItemIdsAsHashMap();
    HashMap items = this.getItemsByPositionAsHashMap();
    PortfolioItemList reorderedList = new PortfolioItemList();
    reorderedList.setEnabledOnly(this.getEnabledOnly());
    reorderedList.setCategoryId(this.getCategoryId());
    String tempItemId = "-1";
    for (int i = 0; i < this.size(); i++) {
      PortfolioItem thisItem = (PortfolioItem) items.get(tempItemId);
      if (thisItem != null) {
        tempItemId = (String) positionMap.get(tempItemId);
        if (enabledOnly != Constants.UNDEFINED) {
          if (thisItem.getEnabled() == (enabledOnly == Constants.TRUE)) {
            reorderedList.add(thisItem);
          }
        } else {
          reorderedList.add(thisItem);
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
  public PortfolioItemList reset(ActionContext context, Connection db) throws SQLException {
    PortfolioItemList oldList = this.reorder();
    HashMap oldOrder = oldList.getItemPositionsAsHashMap();
    HashMap newOrder = new HashMap();
    Iterator iter = (Iterator) oldList.iterator();
    while (iter.hasNext()) {
      PortfolioItem item = (PortfolioItem) iter.next();
      String position = context.getRequest().getParameter("item" + item.getId());
      if (position == null || "".equals(position)) {
        position = (String) oldOrder.get(String.valueOf(item.getId()));
      }
      //Process the new position and push either the itemId into the hash map
      //or put the itemId into the arrayList and put the arrayList into the hash map
      if (newOrder.get(position) != null) {
        Object content = newOrder.get(position);
        if (content instanceof ArrayList) {
          ArrayList tempList = (ArrayList) content;
          int oldPosition = Integer.parseInt((String) oldOrder.get(String.valueOf(item.getId())));
          if (oldPosition > Integer.parseInt(position)) {
            tempList.add(0, String.valueOf(item.getId()));
          } else {
            tempList.add(String.valueOf(item.getId()));
          }
          newOrder.put(position, tempList);
        } else if (content instanceof String) {
          String oldCatId = (String) content;
          ArrayList tempList = new ArrayList();
          tempList.add(oldCatId);
          int oldPosition = Integer.parseInt((String) oldOrder.get(String.valueOf(item.getId())));
          if (oldPosition > Integer.parseInt(position)) {
            tempList.add(0, String.valueOf(item.getId()));
          } else {
            tempList.add(String.valueOf(item.getId()));
          }
          newOrder.put(position, tempList);
        }
      } else {
        newOrder.put(position, String.valueOf(item.getId()));
      }
    }
    //Now the newOrder has the new position ids of the items.
    HashMap itemsMap = oldList.getItemsAsHashMap();
    PortfolioItemList currentList = new PortfolioItemList();
    int itemCounter = 0;
    int currentSize = this.size();
    for (int i = 1; ; i++) {
      if (newOrder.get(String.valueOf(i)) != null) {
        Object content = newOrder.get(String.valueOf(i));
        if (content instanceof String) {
          PortfolioItem tempItem = (PortfolioItem) itemsMap.get((String) content);
          currentList.add(tempItem);
          ++itemCounter;
        } else if (content instanceof ArrayList) {
          ArrayList tempList = (ArrayList) content;
          for (int j = 0; j < tempList.size(); j++) {
            PortfolioItem tempItem = (PortfolioItem) itemsMap.get((String) tempList.get(j));
            currentList.add(tempItem);
            ++itemCounter;
          }
        }
      }
      if (itemCounter >= (currentSize)) {
        break;
      }
    }
    currentList.setEnabledOnly(this.getEnabledOnly());
    currentList.setCategoryId(this.getCategoryId());
    //Reorder and reset the position and update the changes.
    iter = (Iterator) currentList.iterator();
    if (iter.hasNext()) {
      PortfolioItem firstItem = (PortfolioItem) iter.next();
      firstItem.setPositionId(-1);
      if (firstItem.getPositionIdHasChanged()) {
        firstItem.update(db);
      }
      Iterator iter2 = (Iterator) currentList.iterator();
      while (iter.hasNext()) {
        PortfolioItem parentItem = (PortfolioItem) iter2.next();
        PortfolioItem item = (PortfolioItem) iter.next();
        item.setPositionId(parentItem.getId());
        if (item.getPositionIdHasChanged()) {
          item.update(db);
        }
      }
    }
    return currentList;
  }


  /**
   *  Gets the itemIdsAsHashMap attribute of the PortfolioItemList object
   *
   * @return    The itemIdsAsHashMap value
   */
  public HashMap getItemIdsAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      PortfolioItem item = (PortfolioItem) iter.next();
      map.put(String.valueOf(item.getPositionId()), String.valueOf(item.getId()));
    }
    return map;
  }


  /**
   *  Gets the itemsByPositionAsHashMap attribute of the PortfolioItemList
   *  object
   *
   * @return    The itemsByPositionAsHashMap value
   */
  public HashMap getItemsByPositionAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      PortfolioItem item = (PortfolioItem) iter.next();
      map.put(String.valueOf(item.getPositionId()), item);
    }
    return map;
  }


  /**
   *  Gets the itemsAsHashMap attribute of the PortfolioItemList object
   *
   * @return    The itemsAsHashMap value
   */
  public HashMap getItemsAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      PortfolioItem item = (PortfolioItem) iter.next();
      map.put(String.valueOf(item.getId()), item);
    }
    return map;
  }


  /**
   *  Gets the itemPositionsAsHashMap attribute of the PortfolioItemList object
   *
   * @return    The itemPositionsAsHashMap value
   */
  public HashMap getItemPositionsAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    for (int i = 1; iter.hasNext(); i++) {
      PortfolioItem item = (PortfolioItem) iter.next();
      map.put(String.valueOf(item.getId()), String.valueOf(i));
    }
    return map;
  }


  /**
   *  Gets the itemById attribute of the PortfolioItemList object
   *
   * @param  id  Description of the Parameter
   * @return     The itemById value
   */
  public PortfolioItem getItemById(int id) {
    PortfolioItem result = null;
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      PortfolioItem item = (PortfolioItem) iter.next();
      if (item.getId() == id) {
        result = item;
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
      PortfolioItem item = (PortfolioItem) iter.next();
      item.delete(db, baseFilePath);
    }
    return true;
  }


  /**
   *  Gets the idByIndex attribute of the PortfolioItemList object
   *
   * @param  index  Description of the Parameter
   * @return        The idByIndex value
   */
  public int getIdByIndex(int index) {
    int result = -1;
    if (this.size() > 0 && index < this.size()) {
      PortfolioItem item = (PortfolioItem) this.get(index);
      if (item != null) {
        result = item.getId();
      }
    }
    return result;
  }


  /**
   *  Gets the lastItemId attribute of the PortfolioItemList object
   *
   * @return    The lastItemId value
   */
  public int getLastItemId() {
    return this.getIdByIndex(this.size() - 1);
  }


  /*
   *  Get and Set Methods
   */
  /**
   *  Gets the pagedListInfo attribute of the PortfolioItemList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the pagedListInfo attribute of the PortfolioItemList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the id attribute of the PortfolioItemList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the PortfolioItemList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PortfolioItemList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the categoryId attribute of the PortfolioItemList object
   *
   * @return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Sets the categoryId attribute of the PortfolioItemList object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the PortfolioItemList object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the enabledOnly attribute of the PortfolioItemList object
   *
   * @return    The enabledOnly value
   */
  public int getEnabledOnly() {
    return enabledOnly;
  }


  /**
   *  Sets the enabledOnly attribute of the PortfolioItemList object
   *
   * @param  tmp  The new enabledOnly value
   */
  public void setEnabledOnly(int tmp) {
    this.enabledOnly = tmp;
  }


  /**
   *  Sets the enabledOnly attribute of the PortfolioItemList object
   *
   * @param  tmp  The new enabledOnly value
   */
  public void setEnabledOnly(String tmp) {
    this.enabledOnly = Integer.parseInt(tmp);
  }

}

