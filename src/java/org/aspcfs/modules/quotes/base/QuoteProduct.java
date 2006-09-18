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

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.products.base.ProductCatalogPricing;
import org.aspcfs.modules.products.base.ProductOptionConfigurator;
import org.aspcfs.modules.products.configurator.OptionConfigurator;
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This represents a product which is associated with a quote
 *
 * @author ananth
 * @version $Id: QuoteProduct.java,v 1.4.12.4 2005/01/03 18:42:26 mrajkowski
 *          Exp $
 * @created March 24, 2004
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
  private String estimatedDelivery = null;
  private String comment = null;
  // resources
  private boolean buildProductOptions = false;
  private QuoteProductOptionList productOptionList = new QuoteProductOptionList();
  private boolean buildProduct = false;
  private ProductCatalog productCatalog = null;


  /**
   * Sets the id attribute of the QuoteProduct object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the QuoteProduct object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the quoteId attribute of the QuoteProduct object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   * Sets the quoteId attribute of the QuoteProduct object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   * Sets the productId attribute of the QuoteProduct object
   *
   * @param tmp The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   * Sets the productId attribute of the QuoteProduct object
   *
   * @param tmp The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   * Sets the quantity attribute of the QuoteProduct object
   *
   * @param tmp The new quantity value
   */
  public void setQuantity(int tmp) {
    this.quantity = tmp;
  }


  /**
   * Sets the quantity attribute of the QuoteProduct object
   *
   * @param tmp The new quantity value
   */
  public void setQuantity(String tmp) {
    this.quantity = Integer.parseInt(tmp);
  }


  /**
   * Sets the priceCurrency attribute of the QuoteProduct object
   *
   * @param tmp The new priceCurrency value
   */
  public void setPriceCurrency(int tmp) {
    this.priceCurrency = tmp;
  }


  /**
   * Sets the priceCurrency attribute of the QuoteProduct object
   *
   * @param tmp The new priceCurrency value
   */
  public void setPriceCurrency(String tmp) {
    this.priceCurrency = Integer.parseInt(tmp);
  }


  /**
   * Sets the priceAmount attribute of the QuoteProduct object
   *
   * @param tmp The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   * Sets the recurringCurrency attribute of the QuoteProduct object
   *
   * @param tmp The new recurringCurrency value
   */
  public void setRecurringCurrency(int tmp) {
    this.recurringCurrency = tmp;
  }


  /**
   * Sets the recurringCurrency attribute of the QuoteProduct object
   *
   * @param tmp The new recurringCurrency value
   */
  public void setRecurringCurrency(String tmp) {
    this.recurringCurrency = Integer.parseInt(tmp);
  }


  /**
   * Sets the recurringAmount attribute of the QuoteProduct object
   *
   * @param tmp The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   * Sets the recurringType attribute of the QuoteProduct object
   *
   * @param tmp The new recurringType value
   */
  public void setRecurringType(int tmp) {
    this.recurringType = tmp;
  }


  /**
   * Sets the recurringType attribute of the QuoteProduct object
   *
   * @param tmp The new recurringType value
   */
  public void setRecurringType(String tmp) {
    this.recurringType = Integer.parseInt(tmp);
  }


  /**
   * Sets the extendedPrice attribute of the QuoteProduct object
   *
   * @param tmp The new extendedPrice value
   */
  public void setExtendedPrice(double tmp) {
    this.extendedPrice = tmp;
  }


  /**
   * Sets the totalPrice attribute of the QuoteProduct object
   *
   * @param tmp The new totalPrice value
   */
  public void setTotalPrice(double tmp) {
    this.totalPrice = tmp;
  }


  /**
   * Sets the estimatedDeliveryDate attribute of the QuoteProduct object
   *
   * @param tmp The new estimatedDeliveryDate value
   */
  public void setEstimatedDeliveryDate(Timestamp tmp) {
    this.estimatedDeliveryDate = tmp;
  }


  /**
   * Sets the estimatedDeliveryDate attribute of the QuoteProduct object
   *
   * @param tmp The new estimatedDeliveryDate value
   */
  public void setEstimatedDeliveryDate(String tmp) {
    this.estimatedDeliveryDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the statusId attribute of the QuoteProduct object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the QuoteProduct object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the statusDate attribute of the QuoteProduct object
   *
   * @param tmp The new statusDate value
   */
  public void setStatusDate(Timestamp tmp) {
    this.statusDate = tmp;
  }


  /**
   * Sets the statusDate attribute of the QuoteProduct object
   *
   * @param tmp The new statusDate value
   */
  public void setStatusDate(String tmp) {
    this.statusDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the buildProductOptions attribute of the QuoteProduct object
   *
   * @param tmp The new buildProductOptions value
   */
  public void setBuildProductOptions(boolean tmp) {
    this.buildProductOptions = tmp;
  }


  /**
   * Sets the buildProductOptions attribute of the QuoteProduct object
   *
   * @param tmp The new buildProductOptions value
   */
  public void setBuildProductOptions(String tmp) {
    this.buildProductOptions = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the productOptionList attribute of the QuoteProduct object
   *
   * @param tmp The new productOptionList value
   */
  public void setProductOptionList(QuoteProductOptionList tmp) {
    this.productOptionList = tmp;
  }


  /**
   * Sets the productCatalog attribute of the QuoteProduct object
   *
   * @param tmp The new productCatalog value
   */
  public void setProductCatalog(ProductCatalog tmp) {
    this.productCatalog = tmp;
  }


  /**
   * Sets the buildProduct attribute of the QuoteProduct object
   *
   * @param tmp The new buildProduct value
   */
  public void setBuildProduct(boolean tmp) {
    this.buildProduct = tmp;
  }


  /**
   * Sets the buildProduct attribute of the QuoteProduct object
   *
   * @param tmp The new buildProduct value
   */
  public void setBuildProduct(String tmp) {
    this.buildProduct = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildProduct attribute of the QuoteProduct object
   *
   * @return The buildProduct value
   */
  public boolean getBuildProduct() {
    return buildProduct;
  }


  /**
   * Gets the productCatalog attribute of the QuoteProduct object
   *
   * @return The productCatalog value
   */
  public ProductCatalog getProductCatalog() {
    return productCatalog;
  }


  /**
   * Gets the id attribute of the QuoteProduct object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the quoteId attribute of the QuoteProduct object
   *
   * @return The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   * Gets the productId attribute of the QuoteProduct object
   *
   * @return The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   * Gets the quantity attribute of the QuoteProduct object
   *
   * @return The quantity value
   */
  public int getQuantity() {
    return quantity;
  }


  /**
   * Gets the priceCurrency attribute of the QuoteProduct object
   *
   * @return The priceCurrency value
   */
  public int getPriceCurrency() {
    return priceCurrency;
  }


  /**
   * Gets the priceAmount attribute of the QuoteProduct object
   *
   * @return The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   * Gets the recurringCurrency attribute of the QuoteProduct object
   *
   * @return The recurringCurrency value
   */
  public int getRecurringCurrency() {
    return recurringCurrency;
  }


  /**
   * Gets the recurringAmount attribute of the QuoteProduct object
   *
   * @return The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   * Gets the recurringType attribute of the QuoteProduct object
   *
   * @return The recurringType value
   */
  public int getRecurringType() {
    return recurringType;
  }


  /**
   * Gets the extendedPrice attribute of the QuoteProduct object
   *
   * @return The extendedPrice value
   */
  public double getExtendedPrice() {
    return extendedPrice;
  }


  /**
   * Gets the totalPrice attribute of the QuoteProduct object
   *
   * @return The totalPrice value
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
   * Gets the estimatedDeliveryDate attribute of the QuoteProduct object
   *
   * @return The estimatedDeliveryDate value
   */
  public Timestamp getEstimatedDeliveryDate() {
    return estimatedDeliveryDate;
  }


  /**
   * Gets the statusId attribute of the QuoteProduct object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Gets the statusDate attribute of the QuoteProduct object
   *
   * @return The statusDate value
   */
  public Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   * Gets the buildProductOptions attribute of the QuoteProduct object
   *
   * @return The buildProductOptions value
   */
  public boolean getBuildProductOptions() {
    return buildProductOptions;
  }


  /**
   * Gets the productOptionList attribute of the QuoteProduct object
   *
   * @return The productOptionList value
   */
  public QuoteProductOptionList getProductOptionList() {
    return productOptionList;
  }


  /**
   * Gets the estimatedDelivery attribute of the QuoteProduct object
   *
   * @return The estimatedDelivery value
   */
  public String getEstimatedDelivery() {
    return estimatedDelivery;
  }


  /**
   * Sets the estimatedDelivery attribute of the QuoteProduct object
   *
   * @param tmp The new estimatedDelivery value
   */
  public void setEstimatedDelivery(String tmp) {
    this.estimatedDelivery = tmp;
  }


  /**
   * Gets the comment attribute of the QuoteProduct object
   *
   * @return The comment value
   */
  public String getComment() {
    return comment;
  }


  /**
   * Sets the comment attribute of the QuoteProduct object
   *
   * @param tmp The new comment value
   */
  public void setComment(String tmp) {
    this.comment = tmp;
  }


  /**
   * Constructor for the QuoteProduct object
   */
  public QuoteProduct() {
  }


  /**
   * Constructor for the QuoteProduct object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public QuoteProduct(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the QuoteProduct object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public QuoteProduct(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Quote Product Number");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT qp.* " +
        "FROM quote_product qp " +
        "LEFT JOIN product_catalog pctlg ON (qp.product_id = pctlg.product_id) " +
        "WHERE qp.item_id = ? ");
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
    if (buildProduct) {
      this.buildProduct(db);
    }
    if (buildProductOptions) {
      this.buildProductOptions(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
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
    estimatedDelivery = rs.getString("estimated_delivery");
    comment = rs.getString("comment");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildProduct(Connection db) throws SQLException {
    productCatalog = new ProductCatalog(db, this.getProductId());
    productCatalog.buildActivePrice(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildProductOptions(Connection db) throws SQLException {
    productOptionList = new QuoteProductOptionList();
    productOptionList.setItemId(this.getId());
    productOptionList.setBuildConfigDetails(true);
    productOptionList.buildList(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "quote_product_item_id_seq");
    sql.append(
        " INSERT INTO quote_product(" + (id > -1 ? "item_id, " : "") + "quote_id, product_id, " +
        " quantity, price_currency, price_amount, recurring_currency, " +
        " recurring_amount, recurring_type, extended_price, " +
        " total_price, estimated_delivery_date, status_id, status_date, estimated_delivery, " + DatabaseUtils.addQuotes(db, "comment")+ ")");

    sql.append(
        "VALUES(" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
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
    pst.setString(++i, this.getEstimatedDelivery());
    pst.setString(++i, this.getComment());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "quote_product_item_id_seq", id);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param optionList Description of the Parameter
   * @param request    Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db, QuoteProductOptionList optionList, HttpServletRequest request) throws SQLException {
    boolean status = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //Insert the quote product
      status = this.insert(db);
      //Insert the quote product options
      Iterator iterator = (Iterator) optionList.iterator();
      while (iterator.hasNext()) {
        QuoteProductOption option = (QuoteProductOption) iterator.next();
        option.setItemId(this.getId());
        status = option.insert(db);

        OptionConfigurator configurator =
            (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
                db, option.getConfiguratorId());
        configurator.queryProperties(db, option.getOptionId(), false);
        configurator.saveQuoteOption(db, option.getId(), request);
      }
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
    return status;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Item ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      // delete all the quote product options associated with this quote product
      this.setBuildProductOptions(true);
      this.buildProductOptions(db);
      if (this.getProductOptionList().size() > 0) {
        productOptionList.delete(db);
      }
      productOptionList = null;

      // delete the quote product
      PreparedStatement pst = db.prepareStatement(
          " DELETE FROM quote_product WHERE item_id = ?");
      pst.setInt(1, this.getId());
      pst.execute();
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
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = null;
      StringBuffer sql = new StringBuffer();
      sql.append(
          " UPDATE quote_product " +
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
          "     status_date = ?, " +
          "     estimated_delivery = ?, " +
          "     " + DatabaseUtils.addQuotes(db, "comment")+ " = ? " +
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
      pst.setString(++i, this.getEstimatedDelivery());
      pst.setString(++i, this.getComment());
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
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param request Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db, HttpServletRequest request) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      throw new SQLException("Quote Product Option ID not specified");
    }
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      resultCount = this.update(db);
      //update the quote product options
      Iterator i = this.getProductOptionList().iterator();
      while (i.hasNext()) {
        QuoteProductOption thisOption = (QuoteProductOption) i.next();
        OptionConfigurator configurator =
            (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
                db, thisOption.getConfiguratorId());

        configurator.queryProperties(db, thisOption.getOptionId(), false);
        configurator.queryQuoteProperties(db, thisOption.getId());
        double priceAdjust = configurator.computePriceAdjust(request);
        thisOption.setPriceAmount(priceAdjust);
        thisOption.setTotalPrice(priceAdjust);
        thisOption.update(db);
        configurator.updateQuoteOption(db, thisOption.getId(), request);
      }
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
   * Gets the timeZoneParams attribute of the QuoteProduct class
   *
   * @return The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("estimatedDeliveryDate");
    thisList.add("statusDate");
    return thisList;
  }


  /**
   * Gets the numberParams attribute of the QuoteProduct class
   *
   * @return The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("price_amount");
    thisList.add("recurring_amount");
    thisList.add("extended_price");
    thisList.add("total_price");
    return thisList;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param quoteId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void copyQuoteProduct(Connection db, int quoteId) throws SQLException {
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      QuoteProduct copyProduct = new QuoteProduct();
      copyProduct.setBuildProduct(this.getBuildProduct());
      copyProduct.setBuildProductOptions(this.getBuildProductOptions());
      copyProduct.setEstimatedDeliveryDate(this.getEstimatedDeliveryDate());
      copyProduct.setExtendedPrice(this.getExtendedPrice());
      copyProduct.setPriceAmount(this.getPriceAmount());
      copyProduct.setPriceCurrency(this.getPriceCurrency());
      copyProduct.setProductId(this.getProductId());
      copyProduct.setQuantity(this.getQuantity());
      copyProduct.setQuoteId(quoteId);
      copyProduct.setRecurringAmount(this.getRecurringAmount());
      copyProduct.setRecurringCurrency(this.getRecurringCurrency());
      copyProduct.setRecurringType(this.getRecurringType());
      copyProduct.setComment(this.getComment());
      copyProduct.setEstimatedDelivery(this.getEstimatedDelivery());
      copyProduct.insert(db);

      Iterator optionIterator = (Iterator) this.getProductOptionList().iterator();
      while (optionIterator.hasNext()) {
        QuoteProductOption option = (QuoteProductOption) optionIterator.next();
        option.copyQuoteProductOption(db, copyProduct.getId());
      }

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
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildPricing(Connection db) throws SQLException {
    if (this.getProductCatalog() == null && this.getProductId() != -1) {
      ProductCatalog product = new ProductCatalog(db, this.getProductId());
      product.buildActivePrice(db);
      this.setProductCatalog(product);
    } else if (this.getProductCatalog() == null && this.getProductId() == -1) {
      throw new SQLException("Product ID not provided");
    }
    if (this.getProductCatalog().getActivePrice() == null) {
      throw new SQLException(
          "The selected product does not have an enabled price");
    }
    ProductCatalogPricing price = (ProductCatalogPricing) this.getProductCatalog().getActivePrice();
    this.setPriceAmount(price.getPriceAmount());
    this.setPriceCurrency(price.getPriceCurrency());
    this.setRecurringAmount(price.getRecurringAmount());
    this.setRecurringCurrency(price.getRecurringCurrency());
    this.setRecurringType(price.getRecurringType());
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int clone(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Quote Product not populated to be cloned");
    }
    boolean status = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //Insert the quote product clone
      status = this.insert(db);
      //Insert the quote option clones
      if (this.getProductOptionList() != null && this.getProductOptionList().size() > 0) {
        Iterator iterator = (Iterator) this.getProductOptionList().iterator();
        while (iterator.hasNext()) {
          QuoteProductOption option = (QuoteProductOption) iterator.next();
          OptionConfigurator configurator =
              (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
                  db, option.getConfiguratorId());
          option.setItemId(this.getId());
          configurator.queryProperties(db, option.getOptionId(), false);
          configurator.queryQuoteProperties(db, option.getId());
          status = configurator.saveQuoteOption(db, option);
        }
      }
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
    if (status) {
      this.buildProductOptions(db);
      return this.getId();
    } else {
      return -1;
    }
  }
}

