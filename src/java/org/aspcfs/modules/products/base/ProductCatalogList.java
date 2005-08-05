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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * List class for the Product Catalog
 *
 * @author partha
 * @version $Id: ProductCatalogList.java,v 1.1.2.2 2004/03/19 20:46:01 partha
 *          Exp $
 * @created March 19, 2004
 */
public class ProductCatalogList extends ArrayList implements SyncableList {
  //sync api
  /**
   * Description of the Field
   */
  public final static String tableName = "product_catalog";
  /**
   * Description of the Field
   */
  public final static String uniqueField = "product_id";
  private Timestamp lastAnchor = null;
  private Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  //filters
  private PagedListInfo pagedListInfo = null;
  private int enteredBy = -1;
  private int id = -1;
  private int parentId = -1;
  private int typeId = -1;
  private int enabled = Constants.TRUE;
  private int topOnly = Constants.UNDEFINED;
  private int hasCategories = Constants.UNDEFINED;
  private int active = Constants.UNDEFINED;
  private int statusId = -1;
  //other supplimentary data
  private String name = null;
  private String abbreviation = null;
  private String sku = null;
  private String categoryName = null;
  private String parentName = null;
  private String typeName = null;
  private int categoryId = -1;
  private int serviceContractId = -1;

  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;

  //resources
  private boolean buildResources = false;
  private boolean buildActivePrice = false;

  private String optionText = null;
  // this will have a valid value if all the products have the same option
  private String optionPrice = null;
  // this will have a valid value if all the products have one
  // single option which has a price
  private String optionDefaultText = null;
  // This will have a valid value to display a default text for the option
  private boolean optionMatch = true;
  // determines if the products in the list have the same option
  private boolean optionPriceMatch = true;
  // determines if the products in the list have the same option which has a single price
  protected HashMap selectedItems = null;
  // display top level products only
  protected int buildTopLevelOnly = Constants.UNDEFINED;
  // display enabled products that have a valid price only
  protected int buildActiveProductsOnly = Constants.UNDEFINED;


  /**
   * Gets the serviceContractId attribute of the ProductCatalogList object
   *
   * @return The serviceContractId value
   */
  public int getServiceContractId() {
    return serviceContractId;
  }


  /**
   * Sets the serviceContractId attribute of the ProductCatalogList object
   *
   * @param tmp The new serviceContractId value
   */
  public void setServiceContractId(int tmp) {
    this.serviceContractId = tmp;
  }


  /**
   * Sets the serviceContractId attribute of the ProductCatalogList object
   *
   * @param tmp The new serviceContractId value
   */
  public void setServiceContractId(String tmp) {
    this.serviceContractId = Integer.parseInt(tmp);
  }


  /**
   * Sets the trashedDate attribute of the ProductCatalogList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the ProductCatalogList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the includeOnlyTrashed attribute of the ProductCatalogList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   * Sets the includeOnlyTrashed attribute of the ProductCatalogList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the trashedDate attribute of the ProductCatalogList object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   * Gets the includeOnlyTrashed attribute of the ProductCatalogList object
   *
   * @return The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   * Gets the buildActivePrice attribute of the ProductCatalogList object
   *
   * @return The buildActivePrice value
   */
  public boolean getBuildActivePrice() {
    return buildActivePrice;
  }


  /**
   * Sets the buildActivePrice attribute of the ProductCatalogList object
   *
   * @param tmp The new buildActivePrice value
   */
  public void setBuildActivePrice(boolean tmp) {
    this.buildActivePrice = tmp;
  }


  /**
   * Sets the buildActivePrice attribute of the ProductCatalogList object
   *
   * @param tmp The new buildActivePrice value
   */
  public void setBuildActivePrice(String tmp) {
    this.buildActivePrice = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the name attribute of the ProductCatalogList object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the abbreviation attribute of the ProductCatalogList object
   *
   * @param tmp The new abbreviation value
   */
  public void setAbbreviation(String tmp) {
    this.abbreviation = tmp;
  }


  /**
   * Sets the sku attribute of the ProductCatalogList object
   *
   * @param tmp The new sku value
   */
  public void setSku(String tmp) {
    this.sku = tmp;
  }


  /**
   * Sets the buildTopLevelOnly attribute of the ProductCatalogList object
   *
   * @param tmp The new buildTopLevelOnly value
   */
  public void setBuildTopLevelOnly(int tmp) {
    this.buildTopLevelOnly = tmp;
  }


  /**
   * Sets the buildTopLevelOnly attribute of the ProductCatalogList object
   *
   * @param tmp The new buildTopLevelOnly value
   */
  public void setBuildTopLevelOnly(String tmp) {
    this.buildTopLevelOnly = Integer.parseInt(tmp);
  }


  /**
   * Sets the buildActiveProductsOnly attribute of the ProductCatalogList
   * object
   *
   * @param tmp The new buildActiveProductsOnly value
   */
  public void setBuildActiveProductsOnly(int tmp) {
    this.buildActiveProductsOnly = tmp;
  }


  /**
   * Sets the buildActiveProductsOnly attribute of the ProductCatalogList
   * object
   *
   * @param tmp The new buildActiveProductsOnly value
   */
  public void setBuildActiveProductsOnly(String tmp) {
    this.buildActiveProductsOnly = Integer.parseInt(tmp);
  }


  /**
   * Gets the buildTopLevelOnly attribute of the ProductCatalogList object
   *
   * @return The buildTopLevelOnly value
   */
  public int getBuildTopLevelOnly() {
    return buildTopLevelOnly;
  }


  /**
   * Gets the buildActiveProductsOnly attribute of the ProductCatalogList
   * object
   *
   * @return The buildActiveProductsOnly value
   */
  public int getBuildActiveProductsOnly() {
    return buildActiveProductsOnly;
  }


  /**
   * Gets the name attribute of the ProductCatalogList object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the abbreviation attribute of the ProductCatalogList object
   *
   * @return The abbreviation value
   */
  public String getAbbreviation() {
    return abbreviation;
  }


  /**
   * Gets the sku attribute of the ProductCatalogList object
   *
   * @return The sku value
   */
  public String getSku() {
    return sku;
  }


  /**
   * Sets the optionDefaultText attribute of the ProductCatalogList object
   *
   * @param tmp The new optionDefaultText value
   */
  public void setOptionDefaultText(String tmp) {
    this.optionDefaultText = tmp;
  }


  /**
   * Gets the optionDefaultText attribute of the ProductCatalogList object
   *
   * @return The optionDefaultText value
   */
  public String getOptionDefaultText() {
    return optionDefaultText;
  }


  /**
   * Sets the optionPrice attribute of the ProductCatalogList object
   *
   * @param tmp The new optionPrice value
   */
  public void setOptionPrice(String tmp) {
    this.optionPrice = tmp;
  }


  /**
   * Sets the hasCategories attribute of the ProductCatalogList object
   *
   * @param tmp The new hasCategories value
   */
  public void setHasCategories(int tmp) {
    this.hasCategories = tmp;
  }


  /**
   * Sets the hasCategories attribute of the ProductCatalogList object
   *
   * @param tmp The new hasCategories value
   */
  public void setHasCategories(String tmp) {
    this.hasCategories = Integer.parseInt(tmp);
  }


  /**
   * Gets the hasCategories attribute of the ProductCatalogList object
   *
   * @return The hasCategories value
   */
  public int getHasCategories() {
    return hasCategories;
  }


  /**
   * Sets the active attribute of the ProductCatalogList object
   *
   * @param tmp The new active value
   */
  public void setActive(int tmp) {
    this.active = tmp;
  }


  /**
   * Sets the active attribute of the ProductCatalogList object
   *
   * @param tmp The new active value
   */
  public void setActive(String tmp) {
    this.active = Integer.parseInt(tmp);
  }


  /**
   * Gets the active attribute of the ProductCatalogList object
   *
   * @return The active value
   */
  public int getActive() {
    return active;
  }


  /**
   * Gets the optionPrice attribute of the ProductCatalogList object
   *
   * @return The optionPrice value
   */
  public String getOptionPrice() {
    return optionPrice;
  }


  /**
   * Sets the statusId attribute of the ProductCatalogList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the ProductCatalogList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Gets the statusId attribute of the ProductCatalogList object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Sets the optionText attribute of the ProductCatalogList object
   *
   * @param tmp The new optionText value
   */
  public void setOptionText(String tmp) {
    this.optionText = tmp;
  }


  /**
   * Gets the optionText attribute of the ProductCatalogList object
   *
   * @return The optionText value
   */
  public String getOptionText() {
    return optionText;
  }


  /**
   * Sets the optionMatch attribute of the ProductCatalogList object
   *
   * @param tmp The new optionMatch value
   */
  public void setOptionMatch(boolean tmp) {
    this.optionMatch = tmp;
  }


  /**
   * Sets the optionMatch attribute of the ProductCatalogList object
   *
   * @param tmp The new optionMatch value
   */
  public void setOptionMatch(String tmp) {
    this.optionMatch = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the optionPriceMatch attribute of the ProductCatalogList object
   *
   * @param tmp The new optionPriceMatch value
   */
  public void setOptionPriceMatch(boolean tmp) {
    this.optionPriceMatch = tmp;
  }


  /**
   * Sets the optionPriceMatch attribute of the ProductCatalogList object
   *
   * @param tmp The new optionPriceMatch value
   */
  public void setOptionPriceMatch(String tmp) {
    this.optionPriceMatch = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the optionMatch attribute of the ProductCatalogList object
   *
   * @return The optionMatch value
   */
  public boolean getOptionMatch() {
    return optionMatch;
  }


  /**
   * Gets the optionPriceMatch attribute of the ProductCatalogList object
   *
   * @return The optionPriceMatch value
   */
  public boolean getOptionPriceMatch() {
    return optionPriceMatch;
  }


  /**
   * Sets the buildResources attribute of the ProductCatalogList object
   *
   * @param tmp The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   * Sets the buildResources attribute of the ProductCatalogList object
   *
   * @param tmp The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildResources attribute of the ProductCatalogList object
   *
   * @return The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   * Gets the selectedItems attribute of the ProductCatalogList object
   *
   * @return The selectedItems value
   */
  public HashMap getSelectedItems() {
    return selectedItems;
  }


  /**
   * Sets the selectedItems attribute of the ProductCatalogList object
   *
   * @param tmp The new selectedItems value
   */
  public void setSelectedItems(HashMap tmp) {
    this.selectedItems = tmp;
  }


  /**
   * Gets the tableName attribute of the ProductCatalogList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ProductCatalogList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the lastAnchor attribute of the ProductCatalogList object
   *
   * @return The lastAnchor value
   */
  public Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   * Gets the nextAnchor attribute of the ProductCatalogList object
   *
   * @return The nextAnchor value
   */
  public Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   * Gets the syncType attribute of the ProductCatalogList object
   *
   * @return The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   * Gets the pagedListInfo attribute of the ProductCatalogList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the enteredBy attribute of the ProductCatalogList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the id attribute of the ProductCatalogList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the parentId attribute of the ProductCatalogList object
   *
   * @return The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   * Gets the typeId attribute of the ProductCatalogList object
   *
   * @return The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   * Gets the enabled attribute of the ProductCatalogList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Gets the categoryName attribute of the ProductCatalogList object
   *
   * @return The categoryName value
   */
  public String getCategoryName() {
    return categoryName;
  }


  /**
   * Gets the parentName attribute of the ProductCatalogList object
   *
   * @return The parentName value
   */
  public String getParentName() {
    return parentName;
  }


  /**
   * Gets the topOnly attribute of the ProductCatalogList object
   *
   * @return The topOnly value
   */
  public int getTopOnly() {
    return topOnly;
  }


  /**
   * Gets the typeName attribute of the ProductCatalogList object
   *
   * @return The typeName value
   */
  public String getTypeName() {
    return typeName;
  }


  /**
   * Gets the categoryId attribute of the ProductCatalogList object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the lastAnchor attribute of the ProductCatalogList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ProductCatalogList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ProductCatalogList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ProductCatalogList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the syncType attribute of the ProductCatalogList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Sets the syncType attribute of the ProductCatalogList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   * Sets the pagedListInfo attribute of the ProductCatalogList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the enteredBy attribute of the ProductCatalogList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the ProductCatalogList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the id attribute of the ProductCatalogList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ProductCatalogList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the parentId attribute of the ProductCatalogList object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   * Sets the parentId attribute of the ProductCatalogList object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   * Sets the typeId attribute of the ProductCatalogList object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   * Sets the typeId attribute of the ProductCatalogList object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the ProductCatalogList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ProductCatalogList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryName attribute of the ProductCatalogList object
   *
   * @param tmp The new categoryName value
   */
  public void setCategoryName(String tmp) {
    this.categoryName = tmp;
  }


  /**
   * Sets the parentName attribute of the ProductCatalogList object
   *
   * @param tmp The new parentName value
   */
  public void setParentName(String tmp) {
    this.parentName = tmp;
  }


  /**
   * Sets the topOnly attribute of the ProductCatalogList object
   *
   * @param tmp The new topOnly value
   */
  public void setTopOnly(int tmp) {
    this.topOnly = tmp;
  }


  /**
   * Sets the topOnly attribute of the ProductCatalogList object
   *
   * @param tmp The new topOnly value
   */
  public void setTopOnly(String tmp) {
    this.topOnly = Integer.parseInt(tmp);
  }


  /**
   * Sets the typeName attribute of the ProductCatalogList object
   *
   * @param tmp The new typeName value
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }


  /**
   * Sets the categoryId attribute of the ProductCatalogList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the ProductCatalogList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Constructor for the ProductCatalogList object
   */
  public ProductCatalogList() {
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM product_catalog AS pctlg " +
        "LEFT JOIN product_catalog AS pctlg2 ON ( pctlg.parent_id = pctlg2.product_id ) " +
        "LEFT JOIN lookup_product_type AS pctlgtype ON ( pctlg.type_id = pctlgtype.code ) " +
        "LEFT JOIN lookup_product_format AS pctlgformat ON ( pctlg.format_id = pctlgformat.code ) " +
        "LEFT JOIN lookup_product_shipping AS pctlgshipping ON ( pctlg.shipping_id = pctlgshipping.code ) " +
        "LEFT JOIN lookup_product_ship_time AS pctlgshiptime ON ( pctlg.estimated_ship_time = pctlgshiptime.code ) " +
        "WHERE pctlg.product_id > 0 ");

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

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
            sqlFilter.toString() +
            "AND " + DatabaseUtils.toLowerCase(db) + "(pctlg.product_name) < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      pagedListInfo.setDefaultSort("pctlg.product_name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY pctlg.product_name ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append(" SELECT ");
    }
    sqlSelect.append(
        "pctlg.*, " +
        "pctlg2.product_name AS parent_name, " +
        "pctlgtype.description AS type_name, " +
        "pctlgformat.description AS format_name, " +
        "pctlgshipping.description AS shipping_name, " +
        "pctlgshiptime.description AS shiptime_name " +
        "FROM product_catalog AS pctlg " +
        "LEFT JOIN product_catalog AS pctlg2 ON ( pctlg.parent_id = pctlg2.product_id ) " +
        "LEFT JOIN lookup_product_type AS pctlgtype ON ( pctlg.type_id = pctlgtype.code ) " +
        "LEFT JOIN lookup_product_format AS pctlgformat ON ( pctlg.format_id = pctlgformat.code ) " +
        "LEFT JOIN lookup_product_shipping AS pctlgshipping ON ( pctlg.shipping_id = pctlgshipping.code ) " +
        "LEFT JOIN lookup_product_ship_time AS pctlgshiptime ON ( pctlg.estimated_ship_time = pctlgshiptime.code ) " +
        "WHERE pctlg.product_id > 0 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      ProductCatalog productCatalog = new ProductCatalog(rs);
      this.add(productCatalog);
    }
    rs.close();
    pst.close();
    // Each product's option list is generated
    if (buildResources || buildActivePrice) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        ProductCatalog thisProduct = (ProductCatalog) i.next();
        if (buildResources) {
          thisProduct.buildOptions(db);
        }
        if (buildActivePrice) {
          thisProduct.buildActivePrice(db);
        }
      }
      //TODO: remove this adsjet specific stuff
      //determineMatch();
    }
    //TODO: remove this adsjet specific stuff
    // detemine the product's actual category trail since it might
    // exist several levels down the category tree
    /*
     *  Iterator j = this.iterator();
     *  while (j.hasNext()) {
     *  ProductCatalog thisProduct = (ProductCatalog) j.next();
     *  thisProduct.determineActualCategory(db);
     *  }
     */
  }


  /**
   * Description of the Method
   */
  public void determineMatch() {
    // list of products with every product's option list available
    // determine if all the options for all the products are the same
    // and also determine if all the options have the same values
    Iterator productIterator = this.iterator();
    int count = 0;
    int optionId = -1;
    int valueCount = 0;
    // determines the grand total number of values in all the value lists
    String value = "";
    while (productIterator.hasNext()) {
      // retrieve each product from this product list
      ProductCatalog thisProduct = (ProductCatalog) productIterator.next();
      ProductOptionList optionList = thisProduct.getOptionList();
      if (optionList != null) {
        Iterator optionIterator = optionList.iterator();
        int countOption = 0;
        // determines the total number of options a product has
        while (optionIterator.hasNext()) {
          // retrieve each option and determine if it is the same
          count++;
          countOption++;
          ProductOption thisOption = (ProductOption) optionIterator.next();
          if (count == 1) {
            optionId = thisOption.getId();
          } else {
            if (optionId != thisOption.getId()) {
              this.optionMatch = false;
            }
          }
          Iterator valueIterator = thisOption.getOptionValuesList().iterator();
          while (valueIterator.hasNext()) {
            valueCount++;
            ProductOptionValues thisValue = (ProductOptionValues) valueIterator.next();
            optionPrice = Double.toString(thisValue.getMsrpAmount());
            if (valueCount == 1) {
              value = Double.toString(thisValue.getMsrpAmount());
            } else {
              if (!value.equals(Double.toString(thisValue.getMsrpAmount()))) {
                this.optionPriceMatch = false;
              }
            }
            if (thisValue.getMsrpAmount() != 0) {
              this.optionText = thisValue.getDescription();
            } else {
              this.optionDefaultText = thisValue.getDescription();
            }
          }
        }
        // if any product in the list does not have options then
        // optionMatch should be false
        if (countOption == 0) {
          this.optionMatch = false;
          this.optionPriceMatch = false;
        }
      }
    }
    if (!this.optionMatch) {
      this.optionText = null;
      this.optionPrice = null;
    }
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param basePath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db, String basePath) throws SQLException {
    Iterator catalogs = this.iterator();
    while (catalogs.hasNext()) {
      ProductCatalog productCatalog = (ProductCatalog) catalogs.next();
      productCatalog.delete(db, basePath);
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {

    if (enteredBy > -1) {
      sqlFilter.append(" AND pctlg.enteredby = ? ");
    }

    if (id > -1) {
      sqlFilter.append(" AND pctlg.product_id = ? ");
    }

    if (topOnly == Constants.TRUE) {
      sqlFilter.append(" AND pctlg.parent_id IS NULL ");
    } else if (topOnly == Constants.FALSE) {
      sqlFilter.append(" AND pctlg.parent_id IS NOT NULL ");
    }

    if (parentId > -1) {
      sqlFilter.append(" AND pctlg.parent_id = ? ");
    }

    if (typeId > -1) {
      sqlFilter.append(" AND pctlg.type_id = ? ");
    }

    if (name != null) {
      if (name.indexOf("%") >= 0) {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(pctlg.product_name) LIKE " + DatabaseUtils.toLowerCase(
                db) + "(?) ");
      } else {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(pctlg.product_name) = " + DatabaseUtils.toLowerCase(
                db) + "(?) ");
      }
    }
    if (abbreviation != null) {
      if (abbreviation.indexOf("%") >= 0) {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(pctlg.abbreviation) LIKE " + DatabaseUtils.toLowerCase(
                db) + "(?) ");
      } else {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(pctlg.abbreviation) = " + DatabaseUtils.toLowerCase(
                db) + "(?) ");
      }
    }
    if (sku != null) {
      if (sku.indexOf("%") >= 0) {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(pctlg.sku) LIKE " + DatabaseUtils.toLowerCase(
                db) + "(?) ");
      } else {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(pctlg.sku) = " + DatabaseUtils.toLowerCase(
                db) + "(?) ");
      }
    }

    if (parentName != null) {
      sqlFilter.append(" AND pctlg2.product_name = ? ");
    }

    if (typeName != null) {
      sqlFilter.append(" AND pctlgtype.description = ? ");
    }

    if (categoryId > -1) {
      sqlFilter.append(
          "AND pctlg.product_id IN ( " +
          "  SELECT product_id FROM product_catalog_category_map " +
          "  WHERE category_id = ? )");
    }

    if (serviceContractId > -1) {
      sqlFilter.append(
          "AND pctlg.product_id IN ( " +
          "SELECT link_product_id FROM service_contract_products " +
          "WHERE link_contract_id = ? )");
    }

    if (hasCategories == Constants.TRUE) {
      sqlFilter.append(
          " AND pctlg.product_id IN ( " +
          " SELECT pcmap1.product_id FROM product_catalog_category_map AS pcmap1 ) ");
    } else if (hasCategories == Constants.FALSE) {
      sqlFilter.append(
          " AND pctlg.product_id NOT IN ( " +
          " SELECT pcmap1.product_id FROM product_catalog_category_map AS pcmap1 )");
    }

    if (buildTopLevelOnly == Constants.TRUE) {
      sqlFilter.append("AND pctlg.parent_id IS NULL ");
    } else if (buildTopLevelOnly == Constants.FALSE) {
      sqlFilter.append("AND pctlg.parent_id IS NOT NULL ");
    }

    if (buildActiveProductsOnly != Constants.UNDEFINED) {
      sqlFilter.append(
          "AND pctlg.product_id IN ( SELECT p.product_id " +
          " FROM product_catalog AS p LEFT JOIN product_catalog_pricing AS price ON " +
          " ( p.product_id = price.product_id ) " +
          " WHERE p.product_id > 0 ");
      if (buildActiveProductsOnly == Constants.TRUE) {
        sqlFilter.append(
            " AND p.active = ? " +
            " AND price.enabled = ? " +
            " AND (p.start_date < ? OR p.start_date IS NULL) " +
            " AND (p.expiration_date IS NULL OR p.expiration_date > ?) " +
            " AND (price.start_date < ? OR price.start_date IS NULL) " +
            " AND (price.expiration_date IS NULL OR price.expiration_date > ?) " +
            " ) ");
      } else if (buildActiveProductsOnly == Constants.FALSE) {
        sqlFilter.append(
            " AND (p.active = ? " +
            " OR price.enabled = ? " +
            " OR p.start_date > ? OR p.expiration_date < ? " +
            " OR price.start_date > ? OR price.expiration_date < ?) " +
            " ) OR pctlg.product_id NOT IN (SELECT pp.product_id " +
            " FROM product_catalog_pricing AS pp WHERE pp.product_id > 0 ))");
      }
    }
    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND pctlg.entered >= ? ");
      }
      sqlFilter.append(" AND pctlg.entered < ? ");
    } else if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append(" AND pctlg.modified >= ? ");
      sqlFilter.append(" AND pctlg.entered < ? ");
      sqlFilter.append(" AND pctlg.modified < ? ");
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        sqlFilter.append("AND pctlg.entered >= ? ");
      }
      if (nextAnchor != null) {
        sqlFilter.append("AND pctlg.entered < ? ");
      }
    }

    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append(" AND pctlg.enabled = ? ");
    }

    if (active != Constants.UNDEFINED) {
      sqlFilter.append(" AND pctlg.active = ? ");
    }

    if (selectedItems != null) {
      if (selectedItems.size() > 0) {
        sqlFilter.append(
            "AND (pctlg.active = ? OR pctlg.product_id IN (" + getItemsAsList() + ")) ");
      } else {
        sqlFilter.append("AND pctlg.active = ? ");
      }
    }
    if (includeOnlyTrashed) {
      sqlFilter.append("AND pctlg.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND pctlg.trashed_date = ? ");
    } else {
      sqlFilter.append("AND pctlg.trashed_date IS NULL ");
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

    if (name != null) {
      pst.setString(++i, name);
    }

    if (abbreviation != null) {
      pst.setString(++i, abbreviation);
    }

    if (sku != null) {
      pst.setString(++i, sku);
    }

    if (parentName != null) {
      pst.setString(++i, parentName);
    }

    if (typeName != null) {
      pst.setString(++i, typeName);
    }

    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }

    if (serviceContractId > -1) {
      pst.setInt(++i, serviceContractId);
    }

    if (buildActiveProductsOnly == Constants.TRUE) {
      pst.setBoolean(++i, true);
      pst.setBoolean(++i, true);
      Timestamp currentTimestamp = new Timestamp(
          Calendar.getInstance().getTimeInMillis());
      pst.setTimestamp(++i, currentTimestamp);
      pst.setTimestamp(++i, currentTimestamp);
      pst.setTimestamp(++i, currentTimestamp);
      pst.setTimestamp(++i, currentTimestamp);
    } else if (buildActiveProductsOnly == Constants.FALSE) {
      pst.setBoolean(++i, false);
      pst.setBoolean(++i, false);
      Timestamp currentTimestamp = new Timestamp(
          Calendar.getInstance().getTimeInMillis());
      pst.setTimestamp(++i, currentTimestamp);
      pst.setTimestamp(++i, currentTimestamp);
      pst.setTimestamp(++i, currentTimestamp);
      pst.setTimestamp(++i, currentTimestamp);
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
    if (active == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (active == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }

    if (selectedItems != null) {
      pst.setBoolean(++i, true);
    }
    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
    }

    return i;
  }


  /**
   * Gets the itemsAsList attribute of the ProductCatalogList object
   *
   * @return The itemsAsList value
   */
  private String getItemsAsList() {
    StringBuffer sb = new StringBuffer();
    if (selectedItems != null) {
      Iterator i = selectedItems.keySet().iterator();
      while (i.hasNext()) {
        sb.append(String.valueOf((Integer) i.next()));
        if (i.hasNext()) {
          sb.append(",");
        }
      }
    }
    return sb.toString();
  }


  /**
   * Gets the productFromId attribute of the ProductCatalogList object
   *
   * @param id Description of the Parameter
   * @return The productFromId value
   */
  public ProductCatalog getProductFromId(int id) {
    ProductCatalog result = null;
    Iterator products = (Iterator) this.iterator();
    while (products.hasNext()) {
      ProductCatalog product = (ProductCatalog) products.next();
      if (product.getId() == id) {
        result = product;
        break;
      }
    }
    return result;
  }


  /**
   * Adds a feature to the CategoryMappings attribute of the ProductCatalogList
   * object
   *
   * @param db         The feature to be added to the CategoryMappings
   *                   attribute
   * @param categoryId The feature to be added to the CategoryMappings
   *                   attribute
   * @throws SQLException Description of the Exception
   */
  public void addCategoryMapping(Connection db, int categoryId) throws SQLException {
    if (categoryId == -1) {
      throw new SQLException("Invalid category ID specified");
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ProductCatalog thisCatalog = (ProductCatalog) i.next();
      thisCatalog.addCategoryMapping(db, categoryId);
    }
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param categoryId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void removeCategoryMapping(Connection db, int categoryId) throws SQLException {
    if (categoryId == -1) {
      throw new SQLException("Invalid Category ID specified");
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ProductCatalog thisProduct = (ProductCatalog) i.next();
      thisProduct.removeCategoryMapping(db, categoryId);
    }
  }


  /**
   * Adds a feature to the CategoryMapping attribute of the ProductCatalogList
   * object
   *
   * @param db         The feature to be added to the CategoryMapping
   *                   attribute
   * @param oldList    The feature to be added to the CategoryMapping
   *                   attribute
   * @param categoryId The feature to be added to the CategoryMapping
   *                   attribute
   * @throws SQLException Description of the Exception
   */
  public void addCategoryMapping(Connection db, ProductCatalogList oldList, int categoryId) throws SQLException {
    try {
      db.setAutoCommit(false);
      // Remove the mappings of elements present in the oldList and not this list
      Iterator i = oldList.iterator();
      while (i.hasNext()) {
        ProductCatalog oldProduct = (ProductCatalog) i.next();
        boolean exists = false;
        Iterator j = this.iterator();
        while (j.hasNext()) {
          ProductCatalog thisProduct = (ProductCatalog) j.next();
          if (oldProduct.getId() == thisProduct.getId()) {
            exists = true;
            j.remove();
            break;
          }
        }
        if (!exists) {
          // old product does not exist in the new list. hence remove the mapping
          oldProduct.removeCategoryMapping(db, categoryId);
        }
      }

      // Add mappings for the elements now present in this list
      this.addCategoryMapping(db, categoryId);
      db.commit();
    } catch (Exception e) {
      e.printStackTrace(System.out);
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }
}

