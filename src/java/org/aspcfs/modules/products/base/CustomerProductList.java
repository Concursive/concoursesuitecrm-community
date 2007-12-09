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
package org.aspcfs.modules.products.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id: CustomerProductList.java,v 1.1.2.7 2004/05/20 00:05:49 ananth
 *          Exp $
 * @created April 20, 2004
 */
public class CustomerProductList extends ArrayList {
  public final static String tableName = "customer_product";
  public final static String uniqueField = "customer_product_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private PagedListInfo pagedListInfo = null;
  private int orgId = -1;
  private int orderId = -1;
  private int orderItemId = -1;
  private int statusId = -1;
  private int enabled = Constants.UNDEFINED;
  private int productId = -1;
  private boolean matchProduct = true;
  private boolean buildProductCatalog = false;
  private boolean buildFileList = false;
  private boolean buildHistoryList = false;
  private boolean svgProductsOnly = false;
  private boolean historyExists = false;

  /**
   * Sets the lastAnchor attribute of the CustomerProductList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the CustomerProductList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the CustomerProductList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the CustomerProductList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the CustomerProductList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Gets the tableName attribute of the CustomerProductList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the CustomerProductList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Sets the historyExists attribute of the CustomerProductList object
   *
   * @param tmp The new historyExists value
   */
  public void setHistoryExists(boolean tmp) {
    this.historyExists = tmp;
  }


  /**
   * Sets the historyExists attribute of the CustomerProductList object
   *
   * @param tmp The new historyExists value
   */
  public void setHistoryExists(String tmp) {
    this.historyExists = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the historyExists attribute of the CustomerProductList object
   *
   * @return The historyExists value
   */
  public boolean getHistoryExists() {
    return historyExists;
  }


  /**
   * Sets the buildProductCatalog attribute of the CustomerProductList object
   *
   * @param tmp The new buildProductCatalog value
   */
  public void setBuildProductCatalog(boolean tmp) {
    this.buildProductCatalog = tmp;
  }


  /**
   * Sets the buildProductCatalog attribute of the CustomerProductList object
   *
   * @param tmp The new buildProductCatalog value
   */
  public void setBuildProductCatalog(String tmp) {
    this.buildProductCatalog = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildProductCatalog attribute of the CustomerProductList object
   *
   * @return The buildProductCatalog value
   */
  public boolean getBuildProductCatalog() {
    return buildProductCatalog;
  }


  /**
   * Sets the svgProductsOnly attribute of the CustomerProductList object
   *
   * @param tmp The new svgProductsOnly value
   */
  public void setSvgProductsOnly(boolean tmp) {
    this.svgProductsOnly = tmp;
  }


  /**
   * Sets the svgProductsOnly attribute of the CustomerProductList object
   *
   * @param tmp The new svgProductsOnly value
   */
  public void setSvgProductsOnly(String tmp) {
    this.svgProductsOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the svgProductsOnly attribute of the CustomerProductList object
   *
   * @return The svgProductsOnly value
   */
  public boolean getSvgProductsOnly() {
    return svgProductsOnly;
  }


  /**
   * Sets the matchProduct attribute of the CustomerProductList object
   *
   * @param tmp The new matchProduct value
   */
  public void setMatchProduct(boolean tmp) {
    this.matchProduct = tmp;
  }


  /**
   * Sets the matchProduct attribute of the CustomerProductList object
   *
   * @param tmp The new matchProduct value
   */
  public void setMatchProduct(String tmp) {
    this.matchProduct = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the matchProduct attribute of the CustomerProductList object
   *
   * @return The matchProduct value
   */
  public boolean getMatchProduct() {
    return matchProduct;
  }


  /**
   * Sets the buildHistoryList attribute of the CustomerProductList object
   *
   * @param tmp The new buildHistoryList value
   */
  public void setBuildHistoryList(boolean tmp) {
    this.buildHistoryList = tmp;
  }


  /**
   * Sets the buildHistoryList attribute of the CustomerProductList object
   *
   * @param tmp The new buildHistoryList value
   */
  public void setBuildHistoryList(String tmp) {
    this.buildHistoryList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildHistoryList attribute of the CustomerProductList object
   *
   * @return The buildHistoryList value
   */
  public boolean getBuildHistoryList() {
    return buildHistoryList;
  }


  /**
   * Sets the buildFileList attribute of the CustomerProductList object
   *
   * @param tmp The new buildFileList value
   */
  public void setBuildFileList(boolean tmp) {
    this.buildFileList = tmp;
  }


  /**
   * Sets the buildFileList attribute of the CustomerProductList object
   *
   * @param tmp The new buildFileList value
   */
  public void setBuildFileList(String tmp) {
    this.buildFileList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildFileList attribute of the CustomerProductList object
   *
   * @return The buildFileList value
   */
  public boolean getBuildFileList() {
    return buildFileList;
  }


  /**
   * Sets the productId attribute of the CustomerProductList object
   *
   * @param tmp The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   * Sets the productId attribute of the CustomerProductList object
   *
   * @param tmp The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   * Gets the productId attribute of the CustomerProductList object
   *
   * @return The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   * Sets the pagedListInfo attribute of the CustomerProductList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the orgId attribute of the CustomerProductList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the CustomerProductList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the orderId attribute of the CustomerProductList object
   *
   * @param tmp The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   * Sets the orderId attribute of the CustomerProductList object
   *
   * @param tmp The new orderId value
   */
  public void setOrderId(String tmp) {
    this.orderId = Integer.parseInt(tmp);
  }


  /**
   * Sets the orderItemId attribute of the CustomerProductList object
   *
   * @param tmp The new orderItemId value
   */
  public void setOrderItemId(int tmp) {
    this.orderItemId = tmp;
  }


  /**
   * Sets the orderItemId attribute of the CustomerProductList object
   *
   * @param tmp The new orderItemId value
   */
  public void setOrderItemId(String tmp) {
    this.orderItemId = Integer.parseInt(tmp);
  }


  /**
   * Sets the statusId attribute of the CustomerProductList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the CustomerProductList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the CustomerProductList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the CustomerProductList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Gets the pagedListInfo attribute of the CustomerProductList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the orgId attribute of the CustomerProductList object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Gets the orderId attribute of the CustomerProductList object
   *
   * @return The orderId value
   */
  public int getOrderId() {
    return orderId;
  }


  /**
   * Gets the orderItemId attribute of the CustomerProductList object
   *
   * @return The orderItemId value
   */
  public int getOrderItemId() {
    return orderItemId;
  }


  /**
   * Gets the statusId attribute of the CustomerProductList object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Gets the enabled attribute of the CustomerProductList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Constructor for the CustomerProductList object
   */
  public CustomerProductList() {
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
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
            " FROM customer_product cp " +
            " LEFT JOIN order_product op ON (cp.order_item_id = op.item_id) " +
            " LEFT JOIN order_entry oe ON (cp.order_id = oe.order_id) " +
            " LEFT JOIN quote_entry qe ON (oe.quote_id = qe.quote_id) " +
            " WHERE cp.customer_product_id > -1 ");

    createFilter(sqlFilter);
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
        pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
        items = prepareFilter(pst);
        //pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      //Determine column to sort by
      pagedListInfo.setDefaultSort("cp.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY cp.order_id");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " cp.*, op.product_id AS product_id, " +
            " qe.product_id AS quote_product_id " +
            " FROM customer_product cp " +
            " LEFT JOIN order_product op ON (cp.order_item_id = op.item_id) " +
            " LEFT JOIN order_entry oe ON (cp.order_id = oe.order_id) " +
            " LEFT JOIN quote_entry qe ON (oe.quote_id = qe.quote_id) " +
            " WHERE cp.customer_product_id > -1 ");

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
      CustomerProduct product = new CustomerProduct(rs);
      this.add(product);
    }
    rs.close();
    pst.close();
    if (buildFileList) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomerProduct thisProduct = (CustomerProduct) i.next();
        thisProduct.buildFileList(db);
      }
    }
    if (buildHistoryList) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomerProduct thisProduct = (CustomerProduct) i.next();
        thisProduct.buildHistoryList(db);
      }
    }
    // builds the product catalog associated with this
    // customer product
    if (buildProductCatalog) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomerProduct thisProduct = (CustomerProduct) i.next();
        thisProduct.buildProductCatalog(db);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (orgId > -1) {
      sqlFilter.append("AND cp.org_id = ? ");
    }
    if (orderId > -1) {
      sqlFilter.append("AND cp.order_id = ? ");
    }
    if (orderItemId > -1) {
      sqlFilter.append("AND cp.order_item_id = ? ");
    }
    if (statusId > -1) {
      sqlFilter.append("AND cp.status = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND cp.enabled = ? ");
    }
    if (productId > -1) {
      if (matchProduct) {
        sqlFilter.append("AND ( qe.product_id = ? OR op.product_id = ? ) ");
      } else {
        sqlFilter.append("AND ( qe.product_id <> ? OR op.product_id <> ? ) ");
      }
    }
    if (svgProductsOnly) {
      sqlFilter.append(
          "AND cp.customer_product_id IN (SELECT link_item_id FROM project_files WHERE client_filename LIKE '%.svg') ");
    }
    if (historyExists) {
      sqlFilter.append(
          "AND cp.customer_product_id IN (" +
              " SELECT customer_product_id FROM (" +
              "   SELECT customer_product_id, count(history_id) as history" +
              "   FROM customer_product_history" +
              "   GROUP BY customer_product_id) tmp" +
              " WHERE tmp.history > 1) ");
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
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (orgId > -1) {
      pst.setInt(++i, orgId);
    }
    if (orderId > -1) {
      pst.setInt(++i, orderId);
    }
    if (orderItemId > -1) {
      pst.setInt(++i, orderItemId);
    }
    if (statusId > -1) {
      pst.setInt(++i, statusId);
    }
    if (enabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (enabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }
    if (productId > -1) {
      pst.setInt(++i, productId);
      pst.setInt(++i, productId);
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


  /**
   * Gets the customerProductIdFromOrderProductId attribute of the
   * CustomerProductList object
   *
   * @param id Description of the Parameter
   * @return The customerProductIdFromOrderProductId value
   * @throws SQLException Description of the Exception
   */
  public int getCustomerProductIdFromOrderProductId(int id) throws SQLException {
    int result = -1;
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      CustomerProduct customerProduct = (CustomerProduct) iterator.next();
      if (customerProduct.getOrderItemId() == id) {
        result = customerProduct.getId();
        break;
      }
    }
    return result;
  }
}

