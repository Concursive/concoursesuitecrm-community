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
package org.aspcfs.modules.quotes.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This represents a list of quote products
 *
 * @author ananth
 * @version $Id: QuoteProductList.java,v 1.1.2.2 2004/04/24 14:37:47 partha
 *          Exp $
 * @created March 24, 2004
 */
public class QuoteProductList extends ArrayList {
  public final static String tableName = "quote_product";
  public final static String uniqueField = "item_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private PagedListInfo pagedListInfo = null;
  private int quoteId = -1;
  private int statusId = -1;
  private int productId = -1;
  private String productName = null;
  private boolean buildResources = false;
  private HashMap errors = new HashMap();
  private HashMap warnings = new HashMap();
  private boolean onlyWarnings = false;

  /**
   * Sets the lastAnchor attribute of the QuoteProductList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the QuoteProductList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the QuoteProductList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the QuoteProductList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the QuoteProductList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Gets the tableName attribute of the QuoteProductList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the QuoteProductList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Sets the pagedListInfo attribute of the QuoteProductList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the quoteId attribute of the QuoteProductList object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   * Sets the quoteId attribute of the QuoteProductList object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   * Sets the statusId attribute of the QuoteProductList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the QuoteProductList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the productId attribute of the QuoteProductList object
   *
   * @param tmp The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   * Sets the productId attribute of the QuoteProductList object
   *
   * @param tmp The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   * Sets the buildResources attribute of the QuoteProductList object
   *
   * @param tmp The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   * Sets the buildResources attribute of the QuoteProductList object
   *
   * @param tmp The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the productName attribute of the QuoteProductList object
   *
   * @param tmp The new productName value
   */
  public void setProductName(String tmp) {
    this.productName = tmp;
  }


  /**
   * Gets the productName attribute of the QuoteProductList object
   *
   * @return The productName value
   */
  public String getProductName() {
    return productName;
  }


  /**
   * Sets the errors attribute of the QuoteProductList object
   *
   * @param tmp The new errors value
   */
  public void setErrors(HashMap tmp) {
    this.errors = tmp;
  }


  /**
   * Gets the errors attribute of the QuoteProductList object
   *
   * @return The errors value
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   * Sets the warnings attribute of the QuoteProductList object
   *
   * @param tmp The new warnings value
   */
  public void setWarnings(HashMap tmp) {
    this.warnings = tmp;
  }


  /**
   * Gets the warnings attribute of the QuoteProductList object
   *
   * @return The warnings value
   */
  public HashMap getWarnings() {
    return warnings;
  }


  /**
   * Sets the onlyWarnings attribute of the QuoteProductList object
   *
   * @param tmp The new onlyWarnings value
   */
  public void setOnlyWarnings(boolean tmp) {
    this.onlyWarnings = tmp;
  }


  /**
   * Sets the onlyWarnings attribute of the QuoteProductList object
   *
   * @param tmp The new onlyWarnings value
   */
  public void setOnlyWarnings(String tmp) {
    this.onlyWarnings = "on".equalsIgnoreCase(tmp);
  }

  public boolean isOnlyWarnings() {
    return onlyWarnings;
  }

  /**
   * Gets the pagedListInfo attribute of the QuoteProductList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the quoteId attribute of the QuoteProductList object
   *
   * @return The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   * Gets the statusId attribute of the QuoteProductList object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Gets the productId attribute of the QuoteProductList object
   *
   * @return The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   * Gets the buildResources attribute of the QuoteProductList object
   *
   * @return The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   * Constructor for the QuoteProductList object
   */
  public QuoteProductList() {
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
        "SELECT COUNT(*) AS recordcount " +
            "FROM quote_product qp " +
            "LEFT JOIN product_catalog pctlg " +
            "ON (qp.product_id = pctlg.product_id) " +
            "WHERE qp.item_id > -1 ");
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
      pagedListInfo.setDefaultSort("qp.item_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY qp.item_id ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "qp.* " +
            "FROM quote_product qp " +
            "LEFT JOIN product_catalog pctlg " +
            "ON (qp.product_id = pctlg.product_id) " +
            "WHERE qp.item_id > -1 ");

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
      QuoteProduct thisQuoteProduct = new QuoteProduct(rs);
      this.add(thisQuoteProduct);
    }
    rs.close();
    pst.close();
    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        QuoteProduct thisQuoteProduct = (QuoteProduct) i.next();
        thisQuoteProduct.buildProductOptions(db);
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
    if (quoteId > -1) {
      sqlFilter.append("AND qp.quote_id = ? ");
    }
    if (statusId > -1) {
      sqlFilter.append("AND qp.status_id = ? ");
    }
    if (productId > -1) {
      sqlFilter.append("AND qp.product_id = ? ");
    }
    if (productName != null) {
      sqlFilter.append("AND pctlg.product_name LIKE ? ");
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
    if (quoteId > -1) {
      pst.setInt(++i, quoteId);
    }
    if (statusId > -1) {
      pst.setInt(++i, statusId);
    }
    if (productId > -1) {
      pst.setInt(++i, productId);
    }
    if (productName != null) {
      pst.setString(++i, productName);
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      QuoteProduct thisProduct = (QuoteProduct) i.next();
      thisProduct.insert(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      QuoteProduct thisProduct = (QuoteProduct) i.next();
      thisProduct.delete(db);
    }
  }


  /**
   * Gets the quoteProductFromProductId attribute of the QuoteProductList
   * object
   *
   * @param id Description of the Parameter
   * @return The quoteProductFromProductId value
   * @throws SQLException Description of the Exception
   */
  public QuoteProduct getQuoteProductFromProductId(int id) throws SQLException {
    QuoteProduct result = null;
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      QuoteProduct quoteProduct = (QuoteProduct) iterator.next();
      if (quoteProduct.getProductId() == id) {
        result = quoteProduct;
        break;
      }
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void populate(Connection db, ActionContext context, User user) throws Exception {
    NumberFormat nf = null;
    nf = NumberFormat.getInstance(user.getLocale());
    for (int i = 1; ; i++) {
      String productIdString = (String) context.getRequest().getParameter(
          "product_" + i);
      String quantityString = (String) context.getRequest().getParameter(
          "qty_" + i);
      String priceString = (String) context.getRequest().getParameter("price_" + i);
      String commentString = (String) context.getRequest().getParameter("comment_" + i);
      if (productIdString != null && !"".equals(productIdString)) {
        if (quantityString != null && !"".equals(quantityString)) {
          ProductCatalog product = new ProductCatalog(
              db, Integer.parseInt(productIdString));
          product.setBuildActiveOptions(Constants.TRUE);
          product.buildOptions(db);
          product.buildActivePrice(db);
          QuoteProduct quoteProduct = new QuoteProduct();
          quoteProduct.setProductCatalog(product);
          quoteProduct.setProductId(product.getId());
          quoteProduct.setComment(commentString);
          quoteProduct.setQuantity(Integer.parseInt(quantityString));
          quoteProduct.buildPricing(db);
          if ((priceString != null) && !"".equals(priceString)) {
            quoteProduct.setPriceAmount((nf.parse(StringUtils.replace(priceString, " ", ""))).doubleValue());
          } else {
            quoteProduct.setPriceAmount(0.0);
          }
          this.add(quoteProduct);
        }
      } else {
        break;
      }
    }
  }
}

