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
package org.aspcfs.modules.products.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.web.*;
import java.util.Calendar;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;

/**
 *  The List class of Product Category.
 *
 *@author     partha
 *@created    March 19, 2004
 *@version    $Id: ProductCategoryList.java,v 1.1.2.2 2004/03/19 20:46:01 partha
 *      Exp $
 */
public class ProductCategoryList extends ArrayList implements SyncableList {

  //sync api
  /**
   *  Description of the Field
   */
  public final static String tableName = "product_category";
  /**
   *  Description of the Field
   */
  public final static String uniqueField = "category_id";
  private Timestamp lastAnchor = null;
  private Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  //filters
  private PagedListInfo pagedListInfo = null;
  private int enteredBy = -1;
  private int id = -1;
  private int parentId = -1;
  private int typeId = -1;
  private int enabled = Constants.UNDEFINED;
  private boolean hasExpireDate = false;
  private int topOnly = Constants.UNDEFINED;
  private int masterCategoryId = -1;
  //other descriptors
  private String parentName = null;
  private String typeName = null;
  private int hasProducts = Constants.UNDEFINED;
  private boolean buildProducts = false;


  /**
   *  Sets the buildProducts attribute of the ProductCategoryList object
   *
   *@param  tmp  The new buildProducts value
   */
  public void setBuildProducts(boolean tmp) {
    this.buildProducts = tmp;
  }


  /**
   *  Sets the buildProducts attribute of the ProductCategoryList object
   *
   *@param  tmp  The new buildProducts value
   */
  public void setBuildProducts(String tmp) {
    this.buildProducts = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildProducts attribute of the ProductCategoryList object
   *
   *@return    The buildProducts value
   */
  public boolean getBuildProducts() {
    return buildProducts;
  }


  private String emptyHtmlSelectRecord = null;


  /**
   *  Gets the tableName attribute of the ProductCategoryList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the ProductCategoryList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the ProductCategoryList object
   *
   *@return    The lastAnchor value
   */
  public Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the ProductCategoryList object
   *
   *@return    The nextAnchor value
   */
  public Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the ProductCategoryList object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Gets the pagedListInfo attribute of the ProductCategoryList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the enteredBy attribute of the ProductCategoryList object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the id attribute of the ProductCategoryList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the enabled attribute of the ProductCategoryList object
   *
   *@return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the parentName attribute of the ProductCategoryList object
   *
   *@return    The parentName value
   */
  public String getParentName() {
    return parentName;
  }


  /**
   *  Gets the typeId attribute of the ProductCategoryList object
   *
   *@return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the parentId attribute of the ProductCategoryList object
   *
   *@return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the topOnly attribute of the ProductCategoryList object
   *
   *@return    The topOnly value
   */
  public int getTopOnly() {
    return topOnly;
  }


  /**
   *  Gets the masterCategoryId attribute of the ProductCategoryList object
   *
   *@return    The masterCategoryId value
   */
  public int getMasterCategoryId() {
    return masterCategoryId;
  }


  /**
   *  Gets the typeName attribute of the ProductCategoryList object
   *
   *@return    The typeName value
   */
  public String getTypeName() {
    return typeName;
  }


  /**
   *  Gets the hasProducts attribute of the ProductCategoryList object
   *
   *@return    The hasProducts value
   */
  public int getHasProducts() {
    return hasProducts;
  }


  /**
   *  Gets the emptyHtmlSelectRecord attribute of the ProductCategoryList object
   *
   *@return    The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }


  /**
   *  Gets the htmlSelect attribute of the RoleList object
   *
   *@param  selectName  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the htmlSelect attribute of the ProductCategoryList object
   *
   *@param  selectName  Description of the Parameter
   *@param  defaultKey  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect categoryListSelect = new HtmlSelect();
    if (emptyHtmlSelectRecord != null) {
      categoryListSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ProductCategory thisCategory = (ProductCategory) i.next();
      categoryListSelect.addItem(
          thisCategory.getId(),
          thisCategory.getName());
    }
    return categoryListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Sets the lastAnchor attribute of the ProductCategoryList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the ProductCategoryList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the ProductCategoryList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the ProductCategoryList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the syncType attribute of the ProductCategoryList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the ProductCategoryList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pagedListInfo attribute of the ProductCategoryList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ProductCategoryList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ProductCategoryList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the id attribute of the ProductCategoryList object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ProductCategoryList object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the ProductCategoryList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ProductCategoryList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentName attribute of the ProductCategoryList object
   *
   *@param  tmp  The new parentName value
   */
  public void setParentName(String tmp) {
    this.parentName = tmp;
  }


  /**
   *  Sets the typeId attribute of the ProductCategoryList object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the ProductCategoryList object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentId attribute of the ProductCategoryList object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the ProductCategoryList object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the topOnly attribute of the ProductCategoryList object
   *
   *@param  tmp  The new topOnly value
   */
  public void setTopOnly(int tmp) {
    this.topOnly = tmp;
  }


  /**
   *  Sets the topOnly attribute of the ProductCategoryList object
   *
   *@param  tmp  The new topOnly value
   */
  public void setTopOnly(String tmp) {
    this.topOnly = Integer.parseInt(tmp);
  }


  /**
   *  Sets the masterCategoryId attribute of the ProductCategoryList object
   *
   *@param  tmp  The new masterCategoryId value
   */
  public void setMasterCategoryId(int tmp) {
    this.masterCategoryId = tmp;
  }


  /**
   *  Sets the masterCategoryId attribute of the ProductCategoryList object
   *
   *@param  tmp  The new masterCategoryId value
   */
  public void setMasterCategoryId(String tmp) {
    this.masterCategoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the typeName attribute of the ProductCategoryList object
   *
   *@param  tmp  The new typeName value
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }


  /**
   *  Sets the emptyHtmlSelectRecord attribute of the ProductCategoryList object
   *
   *@param  tmp  The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   *  Sets the hasProducts attribute of the ProductCategoryList object
   *
   *@param  tmp  The new hasProducts value
   */
  public void setHasProducts(int tmp) {
    this.hasProducts = tmp;
  }


  /**
   *  Sets the hasProducts attribute of the ProductCategoryList object
   *
   *@param  tmp  The new hasProducts value
   */
  public void setHasProducts(String tmp) {
    this.hasProducts = Integer.parseInt(tmp);
  }


  /**
   *  Constructor for the ProductCategoryList object
   */
  public ProductCategoryList() { }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer("");
    StringBuffer sqlCount = new StringBuffer("");
    StringBuffer sqlFilter = new StringBuffer("");
    StringBuffer sqlOrder = new StringBuffer("");

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
        " FROM product_category AS pctgy " +
        " LEFT JOIN product_category AS pctgy2 " +
        " ON ( pctgy.parent_id = pctgy2.category_id ) " +
        " LEFT JOIN lookup_product_category_type AS pctgytype " +
        " ON ( pctgy.type_id = pctgytype.code ) " +
        " WHERE pctgy.category_id > 0 "
        );
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
      pagedListInfo.setDefaultSort("pctgy.category_name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY pctgy.category_name ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " pctgy.*, " +
        " pctgy2.category_name as parent_name," +
        " pctgytype.description AS type_name " +
        " FROM product_category AS pctgy " +
        " LEFT JOIN product_category AS pctgy2 " +
        " ON ( pctgy.parent_id = pctgy2.category_id ) " +
        " LEFT JOIN lookup_product_category_type AS pctgytype " +
        " ON ( pctgy.type_id = pctgytype.code ) " +
        " WHERE pctgy.category_id > -1 "
        );
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.isEndOfOffset(db)) {
        break;
      }
      ProductCategory productCategory = new ProductCategory(rs);
      this.add(productCategory);
    }
    rs.close();
    pst.close();
    if (buildProducts) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        ProductCategory thisCategory = (ProductCategory) i.next();
        thisCategory.buildProductList(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  basePath          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db, String basePath) throws SQLException {
    Iterator categories = this.iterator();
    while (categories.hasNext()) {
      ProductCategory productCategory = (ProductCategory) categories.next();
      productCategory.delete(db, basePath);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   *@param  db         Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    if (enteredBy > -1) {
      sqlFilter.append("AND pctgy.enteredby = ? ");
    }
    if (id > -1) {
      sqlFilter.append("AND pctgy.category_id = ? ");
    }
    if (topOnly == Constants.TRUE) {
      sqlFilter.append("AND pctgy.parent_id IS NULL ");
    } else if (topOnly == Constants.FALSE) {
      sqlFilter.append("AND pctgy.parent_id IS NOT NULL ");
    }
    if (parentId > -1) {
      sqlFilter.append("AND pctgy.parent_id = ? ");
    }
    if (typeId > -1) {
      sqlFilter.append("AND pctgy.type_id = ? ");
    }
    if (parentName != null) {
      sqlFilter.append("AND pctgy3.category_name = ? ");
    }
    if (typeName != null) {
      sqlFilter.append("AND pctgytype.description = ? ");
    }
    if (hasProducts == Constants.TRUE) {
      sqlFilter.append(
          " AND pctgy.category_id IN ( " +
          " SELECT category_id " +
          " FROM product_catalog_category_map " +
          " WHERE id > -1 ) "
          );
    } else if (hasProducts == Constants.FALSE) {
      sqlFilter.append(
          " AND pctgy.category_id NOT IN ( " +
          " SELECT category_id " +
          " FROM product_catalog_category_map " +
          " WHERE id > -1 ) "
          );
    }
    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND pctgy.entered >= ? ");
      }
      sqlFilter.append("AND pctgy.entered < ? ");
    } else if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND pctgy.modified >= ? ");
      sqlFilter.append("AND pctgy.entered < ? ");
      sqlFilter.append("AND pctgy.modified < ? ");
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        sqlFilter.append("AND pctgy.entered >= ? ");
      }
      if (nextAnchor != null) {
        sqlFilter.append("AND pctgy.entered < ? ");
      }
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND pctgy.enabled = ? ");
    }
    if (masterCategoryId > -1) {
      sqlFilter.append(
          "AND pctgy.category_id IN " +
          "(SELECT category2_id FROM product_category_map WHERE category1_id = ?) ");
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
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }

    if (id > -1) {
      pst.setInt(++i, id);
    }

    if (parentId > -1) {
      pst.setInt(++i, parentId);
    }

    if (typeId > -1) {
      pst.setInt(++i, typeId);
    }

    if (parentName != null) {
      pst.setString(++i, parentName);
    }

    if (typeName != null) {
      pst.setString(++i, typeName);
    }

    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      if (nextAnchor != null) {
        pst.setTimestamp(++i, nextAnchor);
      }
    }
    if (enabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (enabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }
    if (masterCategoryId > -1) {
      pst.setInt(++i, masterCategoryId);
    }
    return i;
  }


  /**
   *  Iterates through this list and returns the first category with the
   *  specified name
   *
   *@param  name              Description of the Parameter
   *@return                   The categoryByName value
   *@exception  SQLException  Description of the Exception
   */
  public ProductCategory getCategoryByName(String name) throws SQLException {
    Iterator categories = this.iterator();
    while (categories.hasNext()) {
      ProductCategory productCategory = (ProductCategory) categories.next();
      if (name.equals(productCategory.getName())) {
        return productCategory;
      }
    }
    return null;
  }


  /**
   *  Gets the htmlSelect attribute of the ProductCategoryList object
   *
   *@param  typeId            Description of the Parameter
   *@return                   The htmlSelect value
   *@exception  SQLException  Description of the Exception
   */
  public HtmlSelect getHtmlSelect(int typeId) throws SQLException {
    HtmlSelect select = new HtmlSelect();
    select.addItem("-1", "--All--");
    Iterator categories = this.iterator();
    while (categories.hasNext()) {
      ProductCategory thisCategory = (ProductCategory) categories.next();
      if (thisCategory.getTypeId() == typeId) {
        int value = thisCategory.getId();
        String name = thisCategory.getName();
        select.addItem(value, name);
      }
    }
    return select;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void removeNonProductCategories(Connection db) throws SQLException {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ProductCategory category = (ProductCategory) iterator.next();
      if (!category.checkForProducts(db)) {
        iterator.remove();
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean filterProductCategories(Connection db) throws SQLException {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ProductCategory category = (ProductCategory) iterator.next();
      if (category.checkForProducts(db)) {
        return true;
      }
    }
    return false;
  }
}

