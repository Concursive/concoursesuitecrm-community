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

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.products.base.*;
/**
 *  This represents a product which is associated with a quote
 *
 *@author     ananth
 *@created    March 24, 2004
 *@version    $Id$
 */
public class QuoteProduct extends GenericBean {
  private int id = -1;
  private int quoteId = -1;
  private int productId = -1;
  private int quantity = 0;
  private int priceCurrency = -1;
  private double priceAmount = 0;
  private int recurringCurrency = -1;
  private double recurringAmount = 0;
  private int recurringType = -1;
  private double extendedPrice = 0;
  private double totalPrice = 0;
  private Timestamp estimatedDeliveryDate = null;
  private int statusId = -1;
  private Timestamp statusDate = null;
  // resources
  private boolean buildProductOptions = false;
  private QuoteProductOptionList productOptionList = new QuoteProductOptionList();
  private ProductCatalog productCatalog = null;


  /**
   *  Sets the id attribute of the QuoteProduct object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the QuoteProduct object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quoteId attribute of the QuoteProduct object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   *  Sets the quoteId attribute of the QuoteProduct object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productId attribute of the QuoteProduct object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the QuoteProduct object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quantity attribute of the QuoteProduct object
   *
   *@param  tmp  The new quantity value
   */
  public void setQuantity(int tmp) {
    this.quantity = tmp;
  }


  /**
   *  Sets the quantity attribute of the QuoteProduct object
   *
   *@param  tmp  The new quantity value
   */
  public void setQuantity(String tmp) {
    this.quantity = Integer.parseInt(tmp);
  }


  /**
   *  Sets the priceCurrency attribute of the QuoteProduct object
   *
   *@param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(int tmp) {
    this.priceCurrency = tmp;
  }


  /**
   *  Sets the priceCurrency attribute of the QuoteProduct object
   *
   *@param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(String tmp) {
    this.priceCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the priceAmount attribute of the QuoteProduct object
   *
   *@param  tmp  The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   *  Sets the recurringCurrency attribute of the QuoteProduct object
   *
   *@param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(int tmp) {
    this.recurringCurrency = tmp;
  }


  /**
   *  Sets the recurringCurrency attribute of the QuoteProduct object
   *
   *@param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(String tmp) {
    this.recurringCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the recurringAmount attribute of the QuoteProduct object
   *
   *@param  tmp  The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   *  Sets the recurringType attribute of the QuoteProduct object
   *
   *@param  tmp  The new recurringType value
   */
  public void setRecurringType(int tmp) {
    this.recurringType = tmp;
  }


  /**
   *  Sets the recurringType attribute of the QuoteProduct object
   *
   *@param  tmp  The new recurringType value
   */
  public void setRecurringType(String tmp) {
    this.recurringType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the extendedPrice attribute of the QuoteProduct object
   *
   *@param  tmp  The new extendedPrice value
   */
  public void setExtendedPrice(double tmp) {
    this.extendedPrice = tmp;
  }


  /**
   *  Sets the totalPrice attribute of the QuoteProduct object
   *
   *@param  tmp  The new totalPrice value
   */
  public void setTotalPrice(double tmp) {
    this.totalPrice = tmp;
  }


  /**
   *  Sets the estimatedDeliveryDate attribute of the QuoteProduct object
   *
   *@param  tmp  The new estimatedDeliveryDate value
   */
  public void setEstimatedDeliveryDate(Timestamp tmp) {
    this.estimatedDeliveryDate = tmp;
  }


  /**
   *  Sets the estimatedDeliveryDate attribute of the QuoteProduct object
   *
   *@param  tmp  The new estimatedDeliveryDate value
   */
  public void setEstimatedDeliveryDate(String tmp) {
    this.estimatedDeliveryDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the statusId attribute of the QuoteProduct object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the QuoteProduct object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusDate attribute of the QuoteProduct object
   *
   *@param  tmp  The new statusDate value
   */
  public void setStatusDate(Timestamp tmp) {
    this.statusDate = tmp;
  }


  /**
   *  Sets the statusDate attribute of the QuoteProduct object
   *
   *@param  tmp  The new statusDate value
   */
  public void setStatusDate(String tmp) {
    this.statusDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the buildProductOptions attribute of the QuoteProduct object
   *
   *@param  tmp  The new buildProductOptions value
   */
  public void setBuildProductOptions(boolean tmp) {
    this.buildProductOptions = tmp;
  }


  /**
   *  Sets the buildProductOptions attribute of the QuoteProduct object
   *
   *@param  tmp  The new buildProductOptions value
   */
  public void setBuildProductOptions(String tmp) {
    this.buildProductOptions = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the productOptionList attribute of the QuoteProduct object
   *
   *@param  tmp  The new productOptionList value
   */
  public void setProductOptionList(QuoteProductOptionList tmp) {
    this.productOptionList = tmp;
  }


  /**
   *  Sets the productCatalog attribute of the QuoteProduct object
   *
   *@param  tmp  The new productCatalog value
   */
  public void setProductCatalog(ProductCatalog tmp) {
    this.productCatalog = tmp;
  }


  /**
   *  Gets the productCatalog attribute of the QuoteProduct object
   *
   *@return    The productCatalog value
   */
  public ProductCatalog getProductCatalog() {
    return productCatalog;
  }


  /**
   *  Gets the id attribute of the QuoteProduct object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the quoteId attribute of the QuoteProduct object
   *
   *@return    The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   *  Gets the productId attribute of the QuoteProduct object
   *
   *@return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Gets the quantity attribute of the QuoteProduct object
   *
   *@return    The quantity value
   */
  public int getQuantity() {
    return quantity;
  }


  /**
   *  Gets the priceCurrency attribute of the QuoteProduct object
   *
   *@return    The priceCurrency value
   */
  public int getPriceCurrency() {
    return priceCurrency;
  }


  /**
   *  Gets the priceAmount attribute of the QuoteProduct object
   *
   *@return    The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   *  Gets the recurringCurrency attribute of the QuoteProduct object
   *
   *@return    The recurringCurrency value
   */
  public int getRecurringCurrency() {
    return recurringCurrency;
  }


  /**
   *  Gets the recurringAmount attribute of the QuoteProduct object
   *
   *@return    The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   *  Gets the recurringType attribute of the QuoteProduct object
   *
   *@return    The recurringType value
   */
  public int getRecurringType() {
    return recurringType;
  }


  /**
   *  Gets the extendedPrice attribute of the QuoteProduct object
   *
   *@return    The extendedPrice value
   */
  public double getExtendedPrice() {
    return extendedPrice;
  }


  /**
   *  Gets the totalPrice attribute of the QuoteProduct object
   *
   *@return    The totalPrice value
   */
  public double getTotalPrice() {
    totalPrice = ((double) this.getQuantity()) * (this.getPriceAmount() - this.getExtendedPrice());
    Iterator iterator = this.getProductOptionList().iterator();
    while (iterator.hasNext()) {
      QuoteProductOption option = (QuoteProductOption) iterator.next();
      totalPrice += ((double) this.getQuantity()) * option.getTotalPrice();
    }
    return totalPrice;
  }


  /**
   *  Gets the estimatedDeliveryDate attribute of the QuoteProduct object
   *
   *@return    The estimatedDeliveryDate value
   */
  public Timestamp getEstimatedDeliveryDate() {
    return estimatedDeliveryDate;
  }


  /**
   *  Gets the statusId attribute of the QuoteProduct object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the statusDate attribute of the QuoteProduct object
   *
   *@return    The statusDate value
   */
  public Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   *  Gets the buildProductOptions attribute of the QuoteProduct object
   *
   *@return    The buildProductOptions value
   */
  public boolean getBuildProductOptions() {
    return buildProductOptions;
  }


  /**
   *  Gets the productOptionList attribute of the QuoteProduct object
   *
   *@return    The productOptionList value
   */
  public QuoteProductOptionList getProductOptionList() {
    return productOptionList;
  }


  /**
   *  Constructor for the QuoteProduct object
   */
  public QuoteProduct() { }


  /**
   *  Constructor for the QuoteProduct object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public QuoteProduct(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the QuoteProduct object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public QuoteProduct(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Quote Product Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT qp.* " +
        " FROM quote_product qp " +
        " WHERE qp.item_id = ? "
        );
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Quote Product not found");
    }
    if (buildProductOptions) {
      this.buildProductOptions(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //quote_product table
    this.setId(rs.getInt("item_id"));
    quoteId = rs.getInt("quote_id");
    productId = rs.getInt("product_id");
    quantity = rs.getInt("quantity");
    priceCurrency = DatabaseUtils.getInt(rs, "price_currency");
    priceAmount = DatabaseUtils.getDouble(rs, "price_amount");
    recurringCurrency = DatabaseUtils.getInt(rs, "recurring_currency");
    recurringAmount = DatabaseUtils.getDouble(rs, "recurring_amount");
    recurringType = DatabaseUtils.getInt(rs, "recurring_type");
    extendedPrice = DatabaseUtils.getDouble(rs, "extended_price");
    totalPrice = DatabaseUtils.getDouble(rs, "total_price");
    estimatedDeliveryDate = rs.getTimestamp("estimated_delivery_date");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    statusDate = rs.getTimestamp("status_date");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildProductOptions(Connection db) throws SQLException {
    productOptionList = new QuoteProductOptionList();
    productOptionList.setItemId(this.getId());
    productOptionList.buildList(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        " INSERT INTO quote_product(quote_id, product_id, " +
        " quantity, price_currency, price_amount, recurring_currency, " +
        " recurring_amount, recurring_type, extended_price, " +
        " total_price, estimated_delivery_date, status_id, status_date)");

    sql.append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getQuoteId());
    pst.setInt(++i, this.getProductId());
    pst.setInt(++i, this.getQuantity());
    DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
    pst.setDouble(++i, this.getPriceAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
    pst.setDouble(++i, this.getRecurringAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
    pst.setDouble(++i, this.getExtendedPrice());
    pst.setDouble(++i, this.getTotalPrice());
    DatabaseUtils.setTimestamp(pst, ++i, this.getEstimatedDeliveryDate());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStatusDate());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "quote_product_item_id_seq");
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Item ID not specified");
    }
    try {
      db.setAutoCommit(false);

      // delete all the quote product options associated with this quote product
      this.setBuildProductOptions(true);
      this.buildProductOptions(db);
      if (this.getProductOptionList().size() > 0) {
        productOptionList.delete(db);
      }
      productOptionList = null;

      // delete the quote product
      PreparedStatement pst = db.prepareStatement(" DELETE FROM quote_product WHERE item_id = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (!isValid(db)) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(" UPDATE quote_product " +
        " SET quantity = ? , " +
        "     price_currency = ?, " +
        "     price_amount = ?, " +
        "     recurring_currency = ?, " +
        "     recurring_amount = ?, " +
        "     recurring_type = ?, " +
        "     extended_price = ?, " +
        "     total_price = ?, " +
        "     estimated_delivery_date = ?, " +
        "     status_id = ?, " +
        "     status_date = ? " +
        " WHERE item_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getQuantity());
    DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
    pst.setDouble(++i, this.getPriceAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
    pst.setDouble(++i, this.getRecurringAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
    pst.setDouble(++i, this.getExtendedPrice());
    pst.setDouble(++i, this.getTotalPrice());
    pst.setTimestamp(++i, this.getEstimatedDeliveryDate());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.setTimestamp(++i, this.getStatusDate());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Gets the valid attribute of the QuoteProduct object
   *
   *@param  db                Description of the Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    return true;
  }
}

