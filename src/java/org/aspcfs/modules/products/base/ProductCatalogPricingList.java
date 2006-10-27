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

/**
 * List class for ProductCatalogPricing
 *
 * @author partha
 * @version $Id: ProductCatalogPricingList.java,v 1.1.2.2 2004/03/19 20:46:01
 *          partha Exp $
 * @created March 19, 2004
 */
public class ProductCatalogPricingList extends ArrayList implements SyncableList {
  //sync api
  public final static String tableName = "product_catalog_pricing";
  public final static String uniqueField = "price_id";
  private int syncType = Constants.NO_SYNC;
  private Timestamp lastAnchor = null;
  private Timestamp nextAnchor = null;

  //filters
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int productId = -1;
  private int enabled = Constants.UNDEFINED;
  private int isValidNow = Constants.UNDEFINED;

  //other supplimentary fields
  private String productName = null;
  private Timestamp currentTime = null;


  /**
   * Sets the enabled attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Sets the currentTime attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new currentTime value
   */
  public void setCurrentTime(Timestamp tmp) {
    this.currentTime = tmp;
  }


  /**
   * Sets the currentTime attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new currentTime value
   */
  public void setCurrentTime(String tmp) {
    this.currentTime = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the isValidNow attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new isValidNow value
   */
  public void setIsValidNow(int tmp) {
    this.isValidNow = tmp;
  }


  /**
   * Sets the isValidNow attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new isValidNow value
   */
  public void setIsValidNow(String tmp) {
    this.isValidNow = Integer.parseInt(tmp);
  }


  /**
   * Gets the isValidNow attribute of the ProductCatalogPricingList object
   *
   * @return The isValidNow value
   */
  public int getIsValidNow() {
    return isValidNow;
  }


  /**
   * Gets the currentTime attribute of the ProductCatalogPricingList object
   *
   * @return The currentTime value
   */
  public Timestamp getCurrentTime() {
    return currentTime;
  }


  /**
   * Gets the enabled attribute of the ProductCatalogPricingList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Sets the productId attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   * Sets the productId attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   * Gets the productId attribute of the ProductCatalogPricingList object
   *
   * @return The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   * Gets the tableName attribute of the ProductCatalogPricingList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ProductCatalogPricingList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the pagedListInfo attribute of the ProductCatalogPricingList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the id attribute of the ProductCatalogPricingList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the syncType attribute of the ProductCatalogPricingList object
   *
   * @return The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   * Gets the lastAnchor attribute of the ProductCatalogPricingList object
   *
   * @return The lastAnchor value
   */
  public Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   * Gets the nextAnchor attribute of the ProductCatalogPricingList object
   *
   * @return The nextAnchor value
   */
  public Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   * Gets the productName attribute of the ProductCatalogPricingList object
   *
   * @return The productName value
   */
  public String getProductName() {
    return productName;
  }


  /**
   * Sets the pagedListInfo attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the id attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the syncType attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Sets the syncType attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   * Sets the lastAnchor attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the productName attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new productName value
   */
  public void setProductName(String tmp) {
    this.productName = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ProductCatalogPricingList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Constructor for the ProductCatalogPricingList object
   */
  public ProductCatalogPricingList() {
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
            "FROM product_catalog_pricing pctlgprice " +
            "LEFT JOIN product_catalog pctlg ON ( pctlgprice.product_id = pctlg.product_id ) " +
            "LEFT JOIN lookup_product_tax lpt ON (pctlgprice.tax_id = lpt.code) " +
            "LEFT JOIN lookup_currency lcmsrp ON (pctlgprice.msrp_currency = lcmsrp.code) " +
            "LEFT JOIN lookup_currency lcpc ON (pctlgprice.price_currency = lcpc.code) " +
            "LEFT JOIN lookup_currency lcrc ON (pctlgprice.recurring_currency = lcrc.code) " +
            "LEFT JOIN lookup_recurring_type lrt ON (pctlgprice.recurring_type = lrt.code) " +
            "LEFT JOIN lookup_currency lccc ON (pctlgprice.cost_currency = lccc.code) " +
            "WHERE pctlgprice.price_id > 0 ");
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
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "pctlgprice.*, " +
            "pctlg.product_name AS product_name, " +
            "lpt.description AS tax_name, " +
            "lcmsrp.description AS msrp_currency_name, " +
            "lcpc.description AS price_currency_name, " +
            "lcrc.description AS recurring_currency_name, " +
            "lrt.description AS recurring_type_name, " +
            "lccc.description AS cost_currency_name " +
            "FROM product_catalog_pricing pctlgprice " +
            "LEFT JOIN product_catalog pctlg ON ( pctlgprice.product_id = pctlg.product_id ) " +
            "LEFT JOIN lookup_product_tax lpt ON (pctlgprice.tax_id = lpt.code) " +
            "LEFT JOIN lookup_currency lcmsrp ON (pctlgprice.msrp_currency = lcmsrp.code) " +
            "LEFT JOIN lookup_currency lcpc ON (pctlgprice.price_currency = lcpc.code) " +
            "LEFT JOIN lookup_currency lcrc ON (pctlgprice.recurring_currency = lcrc.code) " +
            "LEFT JOIN lookup_recurring_type lrt ON (pctlgprice.recurring_type = lrt.code) " +
            "LEFT JOIN lookup_currency lccc ON (pctlgprice.cost_currency = lccc.code) " +
            "WHERE pctlgprice.price_id > 0 ");
    sqlOrder.append("ORDER BY pctlgprice.price_amount ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      ProductCatalogPricing catalogPricing = new ProductCatalogPricing(rs);
      this.add(catalogPricing);
    }
    rs.close();
    pst.close();

  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {

    if (id > -1) {
      sqlFilter.append("AND pctlgprice.price_id = ? ");
    }
    if (productName != null) {
      sqlFilter.append("AND pctlg.product_name = ? ");
    }
    if (productId > -1) {
      sqlFilter.append("AND pctlgprice.product_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND pctlgprice.enabled = ? ");
    }
    if (isValidNow == Constants.TRUE) {
      sqlFilter.append(
          "AND (pctlgprice.expiration_date IS NULL OR pctlgprice.expiration_date > ?) ");
      sqlFilter.append(
          "AND (pctlgprice.start_date < ? OR pctlgprice.start_date IS NULL) ");
    } else if (isValidNow == Constants.FALSE) {
      sqlFilter.append(
          "AND (pctlgprice.start_date > ? OR pctlgprice.expiration_date < ?) ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
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
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (productName != null) {
      pst.setString(++i, productName);
    }
    if (productId > -1) {
      pst.setInt(++i, productId);
    }
    if (enabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (enabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }
    if (isValidNow != Constants.UNDEFINED) {
      currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
      pst.setTimestamp(++i, currentTime);
      pst.setTimestamp(++i, currentTime);
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    return i;
  }
}

