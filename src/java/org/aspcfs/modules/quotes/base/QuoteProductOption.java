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
package org.aspcfs.modules.quotes.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.products.base.ProductOptionConfigurator;
import org.aspcfs.modules.products.configurator.OptionConfigurator;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id: QuoteProductOption.java,v 1.1.2.3 2004/05/09 17:31:53 partha
 *          Exp $
 * @created March 24, 2004
 */
public class QuoteProductOption extends GenericBean {
  private int id = -1;
  private int itemId = -1;
  private int productOptionId = -1;
  private int quantity = 0;
  private int priceCurrency = -1;
  private double priceAmount = 0;
  private int recurringCurrency = -1;
  private double recurringAmount = 0;
  private int recurringType = -1;
  private double extendedPrice = 0;
  private double totalPrice = 0;
  private int statusId = -1;
  private boolean booleanValue = false;
  private double floatValue = 0.0;
  private Timestamp timestampValue = null;
  private int integerValue = -1;
  private String textValue = null;
  // helper properties
  private int productId = -1;
  private int optionId = -1;
  private int configuratorId = -1;

  //configurator properties
  private String quoteUserInput = null;
  private double quotePriceAdjust = 0;
  private String quoteHtml = null;

  private boolean buildConfigDetails = false;


  /**
   * Gets the quotePriceAdjust attribute of the QuoteProductOption object
   *
   * @return The quotePriceAdjust value
   */
  public double getQuotePriceAdjust() {
    return quotePriceAdjust;
  }


  /**
   * Sets the quotePriceAdjust attribute of the QuoteProductOption object
   *
   * @param tmp The new quotePriceAdjust value
   */
  public void setQuotePriceAdjust(double tmp) {
    this.quotePriceAdjust = tmp;
  }


  /**
   * Sets the quotePriceAdjust attribute of the QuoteProductOption object
   *
   * @param tmp The new quotePriceAdjust value
   */
  public void setQuotePriceAdjust(String tmp) {
    this.quotePriceAdjust = Double.parseDouble(tmp);
  }


  /**
   * Gets the quoteHtml attribute of the QuoteProductOption object
   *
   * @return The quoteHtml value
   */
  public String getQuoteHtml() {
    return quoteHtml;
  }


  /**
   * Sets the quoteHtml attribute of the QuoteProductOption object
   *
   * @param tmp The new quoteHtml value
   */
  public void setQuoteHtml(String tmp) {
    this.quoteHtml = tmp;
  }


  /**
   * Gets the quoteUserInput attribute of the QuoteProductOption object
   *
   * @return The quoteUserInput value
   */
  public String getQuoteUserInput() {
    return quoteUserInput;
  }


  /**
   * Sets the quoteUserInput attribute of the QuoteProductOption object
   *
   * @param tmp The new quoteUserInput value
   */
  public void setQuoteUserInput(String tmp) {
    this.quoteUserInput = tmp;
  }


  /**
   * Gets the buildConfigDetails attribute of the QuoteProductOption object
   *
   * @return The buildConfigDetails value
   */
  public boolean getBuildConfigDetails() {
    return buildConfigDetails;
  }


  /**
   * Sets the buildConfigDetails attribute of the QuoteProductOption object
   *
   * @param tmp The new buildConfigDetails value
   */
  public void setBuildConfigDetails(boolean tmp) {
    this.buildConfigDetails = tmp;
  }


  /**
   * Sets the buildConfigDetails attribute of the QuoteProductOption object
   *
   * @param tmp The new buildConfigDetails value
   */
  public void setBuildConfigDetails(String tmp) {
    this.buildConfigDetails = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the configuratorId attribute of the QuoteProductOption object
   *
   * @return The configuratorId value
   */
  public int getConfiguratorId() {
    return configuratorId;
  }


  /**
   * Sets the configuratorId attribute of the QuoteProductOption object
   *
   * @param tmp The new configuratorId value
   */
  public void setConfiguratorId(int tmp) {
    this.configuratorId = tmp;
  }


  /**
   * Sets the configuratorId attribute of the QuoteProductOption object
   *
   * @param tmp The new configuratorId value
   */
  public void setConfiguratorId(String tmp) {
    this.configuratorId = Integer.parseInt(tmp);
  }


  /**
   * Gets the optionId attribute of the QuoteProductOption object
   *
   * @return The optionId value
   */
  public int getOptionId() {
    return optionId;
  }


  /**
   * Sets the optionId attribute of the QuoteProductOption object
   *
   * @param tmp The new optionId value
   */
  public void setOptionId(int tmp) {
    this.optionId = tmp;
  }


  /**
   * Sets the optionId attribute of the QuoteProductOption object
   *
   * @param tmp The new optionId value
   */
  public void setOptionId(String tmp) {
    this.optionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the id attribute of the QuoteProductOption object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the QuoteProductOption object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the itemId attribute of the QuoteProductOption object
   *
   * @param tmp The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   * Sets the itemId attribute of the QuoteProductOption object g
   *
   * @param tmp The new itemId value
   */
  public void setItemId(String tmp) {
    this.itemId = Integer.parseInt(tmp);
  }


  /**
   * Sets the productOptionId attribute of the QuoteProductOption object
   *
   * @param tmp The new productOptionId value
   */
  public void setProductOptionId(int tmp) {
    this.productOptionId = tmp;
  }


  /**
   * Sets the productOptionId attribute of the QuoteProductOption object
   *
   * @param tmp The new productOptionId value
   */
  public void setProductOptionId(String tmp) {
    this.productOptionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the quantity attribute of the QuoteProductOption object
   *
   * @param tmp The new quantity value
   */
  public void setQuantity(int tmp) {
    this.quantity = tmp;
  }


  /**
   * Sets the quantity attribute of the QuoteProductOption object
   *
   * @param tmp The new quantity value
   */
  public void setQuantity(String tmp) {
    this.quantity = Integer.parseInt(tmp);
  }


  /**
   * Sets the priceCurrency attribute of the QuoteProductOption object
   *
   * @param tmp The new priceCurrency value
   */
  public void setPriceCurrency(int tmp) {
    this.priceCurrency = tmp;
  }


  /**
   * Sets the priceCurrency attribute of the QuoteProductOption object
   *
   * @param tmp The new priceCurrency value
   */
  public void setPriceCurrency(String tmp) {
    this.priceCurrency = Integer.parseInt(tmp);
  }


  /**
   * Sets the priceAmount attribute of the QuoteProductOption object
   *
   * @param tmp The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   * Sets the recurringCurrency attribute of the QuoteProductOption object
   *
   * @param tmp The new recurringCurrency value
   */
  public void setRecurringCurrency(int tmp) {
    this.recurringCurrency = tmp;
  }


  /**
   * Sets the recurringCurrency attribute of the QuoteProductOption object
   *
   * @param tmp The new recurringCurrency value
   */
  public void setRecurringCurrency(String tmp) {
    this.recurringCurrency = Integer.parseInt(tmp);
  }


  /**
   * Sets the recurringAmount attribute of the QuoteProductOption object
   *
   * @param tmp The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   * Sets the recurringType attribute of the QuoteProductOption object
   *
   * @param tmp The new recurringType value
   */
  public void setRecurringType(int tmp) {
    this.recurringType = tmp;
  }


  /**
   * Sets the recurringType attribute of the QuoteProductOption object
   *
   * @param tmp The new recurringType value
   */
  public void setRecurringType(String tmp) {
    this.recurringType = Integer.parseInt(tmp);
  }


  /**
   * Sets the extendedPrice attribute of the QuoteProductOption object
   *
   * @param tmp The new extendedPrice value
   */
  public void setExtendedPrice(double tmp) {
    this.extendedPrice = tmp;
  }


  /**
   * Sets the totalPrice attribute of the QuoteProductOption object
   *
   * @param tmp The new totalPrice value
   */
  public void setTotalPrice(double tmp) {
    this.totalPrice = tmp;
  }


  /**
   * Sets the statusId attribute of the QuoteProductOption object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the QuoteProductOption object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the productId attribute of the QuoteProductOption object
   *
   * @param tmp The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   * Sets the productId attribute of the QuoteProductOption object
   *
   * @param tmp The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   * Sets the booleanValue attribute of the QuoteProductOption object
   *
   * @param tmp The new booleanValue value
   */
  public void setBooleanValue(boolean tmp) {
    this.booleanValue = tmp;
  }


  /**
   * Sets the booleanValue attribute of the QuoteProductOption object
   *
   * @param tmp The new booleanValue value
   */
  public void setBooleanValue(String tmp) {
    this.booleanValue = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the floatValue attribute of the QuoteProductOption object
   *
   * @param tmp The new floatValue value
   */
  public void setFloatValue(double tmp) {
    this.floatValue = tmp;
  }


  /**
   * Sets the timestampValue attribute of the QuoteProductOption object
   *
   * @param tmp The new timestampValue value
   */
  public void setTimestampValue(Timestamp tmp) {
    this.timestampValue = tmp;
  }


  /**
   * Sets the timestampValue attribute of the QuoteProductOption object
   *
   * @param tmp The new timestampValue value
   */
  public void setTimestampValue(String tmp) {
    this.timestampValue = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the integerValue attribute of the QuoteProductOption object
   *
   * @param tmp The new integerValue value
   */
  public void setIntegerValue(int tmp) {
    this.integerValue = tmp;
  }


  /**
   * Sets the integerValue attribute of the QuoteProductOption object
   *
   * @param tmp The new integerValue value
   */
  public void setIntegerValue(String tmp) {
    this.integerValue = Integer.parseInt(tmp);
  }


  /**
   * Sets the textValue attribute of the QuoteProductOption object
   *
   * @param tmp The new textValue value
   */
  public void setTextValue(String tmp) {
    this.textValue = tmp;
  }


  /**
   * Gets the booleanValue attribute of the QuoteProductOption object
   *
   * @return The booleanValue value
   */
  public boolean getBooleanValue() {
    return booleanValue;
  }


  /**
   * Gets the floatValue attribute of the QuoteProductOption object
   *
   * @return The floatValue value
   */
  public double getFloatValue() {
    return floatValue;
  }


  /**
   * Gets the timestampValue attribute of the QuoteProductOption object
   *
   * @return The timestampValue value
   */
  public Timestamp getTimestampValue() {
    return timestampValue;
  }


  /**
   * Gets the integerValue attribute of the QuoteProductOption object
   *
   * @return The integerValue value
   */
  public int getIntegerValue() {
    return integerValue;
  }


  /**
   * Gets the textValue attribute of the QuoteProductOption object
   *
   * @return The textValue value
   */
  public String getTextValue() {
    return textValue;
  }


  /**
   * Gets the id attribute of the QuoteProductOption object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the itemId attribute of the QuoteProductOption object
   *
   * @return The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   * Gets the productOptionId attribute of the QuoteProductOption object
   *
   * @return The productOptionId value
   */
  public int getProductOptionId() {
    return productOptionId;
  }


  /**
   * Gets the quantity attribute of the QuoteProductOption object
   *
   * @return The quantity value
   */
  public int getQuantity() {
    return quantity;
  }


  /**
   * Gets the priceCurrency attribute of the QuoteProductOption object
   *
   * @return The priceCurrency value
   */
  public int getPriceCurrency() {
    return priceCurrency;
  }


  /**
   * Gets the priceAmount attribute of the QuoteProductOption object
   *
   * @return The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   * Gets the recurringCurrency attribute of the QuoteProductOption object
   *
   * @return The recurringCurrency value
   */
  public int getRecurringCurrency() {
    return recurringCurrency;
  }


  /**
   * Gets the recurringAmount attribute of the QuoteProductOption object
   *
   * @return The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   * Gets the recurringType attribute of the QuoteProductOption object
   *
   * @return The recurringType value
   */
  public int getRecurringType() {
    return recurringType;
  }


  /**
   * Gets the extendedPrice attribute of the QuoteProductOption object
   *
   * @return The extendedPrice value
   */
  public double getExtendedPrice() {
    return extendedPrice;
  }


  /**
   * Gets the totalPrice attribute of the QuoteProductOption object
   *
   * @return The totalPrice value
   */
  public double getTotalPrice() {
    return totalPrice;
  }


  /**
   * Gets the statusId attribute of the QuoteProductOption object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Gets the productId attribute of the QuoteProductOption object
   *
   * @return The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   * Constructor for the QuoteProductOption object
   */
  public QuoteProductOption() {
  }


  /**
   * Constructor for the QuoteProductOption object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public QuoteProductOption(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the QuoteProductOption object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public QuoteProductOption(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Quote Product Option ID Number");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT opt.*, " +
        "bool." + DatabaseUtils.addQuotes(db, "value")+ " AS boolean_value, " +
        "float." + DatabaseUtils.addQuotes(db, "value")+ " AS float_value, intr." + DatabaseUtils.addQuotes(db, "value")+ " AS integer_value, " +
        "tst." + DatabaseUtils.addQuotes(db, "value")+ " AS timestamp_value, txt." + DatabaseUtils.addQuotes(db, "value")+ " AS text_value, " +
        "pom.option_id, po.configurator_id, " +
        "prod.product_id " +
        "FROM quote_product_options opt " +
        "LEFT JOIN quote_product_option_boolean bool ON ( opt.quote_product_option_id = bool.quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_float float ON ( opt.quote_product_option_id = float.quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_timestamp tst ON ( opt.quote_product_option_id = tst.quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_integer intr ON ( opt.quote_product_option_id = intr.quote_product_option_id ) " +
        "LEFT JOIN quote_product_option_text txt ON ( opt.quote_product_option_id = txt.quote_product_option_id ) " +
        "LEFT JOIN product_option_map pom ON (opt.product_option_id = pom.product_option_id) " +
        "LEFT JOIN product_option po ON (pom.option_id = po.option_id), " +
        "quote_product prod " +
        "WHERE opt.item_id = prod.item_id " +
        "AND opt.quote_product_option_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Quote Product Option not found");
    }
    if (buildConfigDetails) {
      this.buildConfigDetails(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildConfigDetails(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Quote Product Option ID not specified");
    }
    // load the configurator
    OptionConfigurator configurator =
        (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
            db, this.getConfiguratorId());
    // query the properties
    configurator.queryProperties(db, this.getOptionId(), false);
    configurator.queryQuoteProperties(db, this.getId());
    this.setQuoteHtml(configurator.getQuoteHtml());
    this.setQuoteUserInput(configurator.getQuoteUserInput());
    this.setQuotePriceAdjust(configurator.getQuotePriceAdjust());
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    // quote_product_options table
    this.setId(rs.getInt("quote_product_option_id"));
    itemId = rs.getInt("item_id");
    productOptionId = rs.getInt("product_option_id");
    quantity = DatabaseUtils.getInt(rs, "quantity");
    priceCurrency = DatabaseUtils.getInt(rs, "price_currency");
    priceAmount = DatabaseUtils.getDouble(rs, "price_amount");
    recurringCurrency = DatabaseUtils.getInt(rs, "recurring_currency");
    recurringAmount = DatabaseUtils.getDouble(rs, "recurring_amount");
    recurringType = DatabaseUtils.getInt(rs, "recurring_type");
    extendedPrice = DatabaseUtils.getDouble(rs, "extended_price");
    totalPrice = DatabaseUtils.getDouble(rs, "total_price");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    optionId = DatabaseUtils.getInt(rs, "option_id");
    configuratorId = DatabaseUtils.getInt(rs, "configurator_id");
    productId = DatabaseUtils.getInt(rs, "product_id");
    // quote_product_option_ values tables
    booleanValue = rs.getBoolean("boolean_value");
    floatValue = DatabaseUtils.getDouble(rs, "float_value");
    integerValue = DatabaseUtils.getInt(rs, "integer_value");
    textValue = rs.getString("text_value");
    timestampValue = rs.getTimestamp("timestamp_value");
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
    id = DatabaseUtils.getNextSeq(
        db, "quote_product_options_quote_product_option_id_seq");
    sql.append(
        " INSERT INTO quote_product_options " +
        "(" + (id > -1 ? "quote_product_option_id, " : "") + "item_id, product_option_id, " +
        "   quantity, price_currency, price_amount, recurring_currency, " +
        "   recurring_amount, recurring_type, extended_price, total_price, status_id) ");
    sql.append(
        " VALUES(" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, this.getItemId());
    pst.setInt(++i, this.getProductOptionId());
    DatabaseUtils.setInt(pst, ++i, this.getQuantity());
    DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
    pst.setDouble(++i, this.getPriceAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
    pst.setDouble(++i, this.getRecurringAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
    pst.setDouble(++i, this.getExtendedPrice());
    pst.setDouble(++i, this.getTotalPrice());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.execute();
    pst.close();
    this.setId(
        DatabaseUtils.getCurrVal(
            db, "quote_product_options_quote_product_option_id_seq", id));
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    boolean result = false;
    PreparedStatement pst = null;
    if (this.getId() == -1) {
      throw new SQLException("Quote Product Option ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      pst = db.prepareStatement(
          " DELETE FROM quote_product_option_boolean WHERE quote_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          " DELETE FROM quote_product_option_float WHERE quote_product_option_id = ? ");
      pst.setInt(1, this.getId());
      result = pst.execute();
      pst.close();

      pst = db.prepareStatement(
          " DELETE FROM quote_product_option_timestamp WHERE quote_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          " DELETE FROM quote_product_option_integer WHERE quote_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          " DELETE FROM quote_product_option_text WHERE quote_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          " DELETE FROM quote_product_options WHERE quote_product_option_id = ? ");
      pst.setInt(1, this.getId());
      result = pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
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
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " UPDATE quote_product_options " +
        " SET quantity = ?, " +
        "     price_currency = ?, " +
        "     price_amount = ?, " +
        "     recurring_currency = ?, " +
        "     recurring_amount = ?, " +
        "     recurring_type = ?, " +
        "     extended_price = ?, " +
        "     total_price = ?, " +
        "     status_id = ? ");
    sql.append(" WHERE quote_product_option_id = ? ");

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
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param productId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void copyQuoteProductOption(Connection db, int productId) throws SQLException {
    QuoteProductOption copyOption = new QuoteProductOption();
    copyOption.setExtendedPrice(this.getExtendedPrice());
    copyOption.setItemId(productId);
    copyOption.setPriceAmount(this.getPriceAmount());
    copyOption.setPriceCurrency(this.getPriceCurrency());
    copyOption.setProductId(this.getProductId());
    copyOption.setProductOptionId(this.getProductOptionId());
    copyOption.setQuantity(this.getQuantity());
    copyOption.setRecurringAmount(this.getRecurringAmount());
    copyOption.setRecurringCurrency(this.getRecurringCurrency());
    copyOption.setRecurringType(this.getRecurringType());
    copyOption.setStatusId(this.getStatusId());
    copyOption.setTotalPrice(this.getTotalPrice());

    copyOption.setOptionId(this.getOptionId());
    copyOption.setConfiguratorId(this.getConfiguratorId());
    copyOption.setQuoteUserInput(this.getQuoteUserInput());
    copyOption.setQuotePriceAdjust(this.getQuotePriceAdjust());
    OptionConfigurator configurator =
        (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
            db, copyOption.getConfiguratorId());
    configurator.queryProperties(db, copyOption.getOptionId(), false);
    //save the quote product option and its properties
    configurator.saveQuoteOption(db, copyOption);
  }


  /**
   * Gets the numberParams attribute of the QuoteProductOption class
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

}

