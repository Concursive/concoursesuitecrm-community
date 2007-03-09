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
package org.aspcfs.modules.orders.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.quotes.base.QuoteProductOption;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 * This represents a Product Option of a particular product associated with a
 * particular Order
 *
 * @author ananth
 * @version $Id: OrderProductOption.java,v 1.2 2004/05/04 15:52:27 mrajkowski
 *          Exp $
 * @created March 18, 2004
 */
public class OrderProductOption extends GenericBean {
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


  /**
   * Sets the id attribute of the OrderProductOption object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the OrderProductOption object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the itemId attribute of the OrderProductOption object
   *
   * @param tmp The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   * Sets the itemId attribute of the OrderProductOption object
   *
   * @param tmp The new itemId value
   */
  public void setItemId(String tmp) {
    this.itemId = Integer.parseInt(tmp);
  }


  /**
   * Sets the productOptionId attribute of the OrderProductOption object
   *
   * @param tmp The new productOptionId value
   */
  public void setProductOptionId(int tmp) {
    this.productOptionId = tmp;
  }


  /**
   * Sets the productOptionId attribute of the OrderProductOption object
   *
   * @param tmp The new productOptionId value
   */
  public void setProductOptionId(String tmp) {
    this.productOptionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the quantity attribute of the OrderProductOption object
   *
   * @param tmp The new quantity value
   */
  public void setQuantity(int tmp) {
    this.quantity = tmp;
  }


  /**
   * Sets the quantity attribute of the OrderProductOption object
   *
   * @param tmp The new quantity value
   */
  public void setQuantity(String tmp) {
    this.quantity = Integer.parseInt(tmp);
  }


  /**
   * Sets the priceCurrency attribute of the OrderProductOption object
   *
   * @param tmp The new priceCurrency value
   */
  public void setPriceCurrency(int tmp) {
    this.priceCurrency = tmp;
  }


  /**
   * Sets the priceCurrency attribute of the OrderProductOption object
   *
   * @param tmp The new priceCurrency value
   */
  public void setPriceCurrency(String tmp) {
    this.priceCurrency = Integer.parseInt(tmp);
  }


  /**
   * Sets the priceAmount attribute of the OrderProductOption object
   *
   * @param tmp The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   * Sets the priceAmount attribute of the OrderProductOption object
   *
   * @param tmp The new priceAmount value
   */
  public void setPriceAmount(String tmp) {
    this.priceAmount = Double.parseDouble(tmp);
  }


  /**
   * Sets the recurringCurrency attribute of the OrderProductOption object
   *
   * @param tmp The new recurringCurrency value
   */
  public void setRecurringCurrency(int tmp) {
    this.recurringCurrency = tmp;
  }


  /**
   * Sets the recurringCurrency attribute of the OrderProductOption object
   *
   * @param tmp The new recurringCurrency value
   */
  public void setRecurringCurrency(String tmp) {
    this.recurringCurrency = Integer.parseInt(tmp);
  }


  /**
   * Sets the recurringAmount attribute of the OrderProductOption object
   *
   * @param tmp The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   * Sets the recurringAmount attribute of the OrderProductOption object
   *
   * @param tmp The new recurringAmount value
   */
  public void setRecurringAmount(String tmp) {
    this.recurringAmount = Double.parseDouble(tmp);
  }


  /**
   * Sets the recurringType attribute of the OrderProductOption object
   *
   * @param tmp The new recurringType value
   */
  public void setRecurringType(int tmp) {
    this.recurringType = tmp;
  }


  /**
   * Sets the recurringType attribute of the OrderProductOption object
   *
   * @param tmp The new recurringType value
   */
  public void setRecurringType(String tmp) {
    this.recurringType = Integer.parseInt(tmp);
  }


  /**
   * Sets the extendedPrice attribute of the OrderProductOption object
   *
   * @param tmp The new extendedPrice value
   */
  public void setExtendedPrice(double tmp) {
    this.extendedPrice = tmp;
  }


  /**
   * Sets the extendedPrice attribute of the OrderProductOption object
   *
   * @param tmp The new extendedPrice value
   */
  public void setExtendedPrice(String tmp) {
    this.extendedPrice = Double.parseDouble(tmp);
  }


  /**
   * Sets the totalPrice attribute of the OrderProductOption object
   *
   * @param tmp The new totalPrice value
   */
  public void setTotalPrice(double tmp) {
    this.totalPrice = tmp;
  }


  /**
   * Sets the totalPrice attribute of the OrderProductOption object
   *
   * @param tmp The new totalPrice value
   */
  public void setTotalPrice(String tmp) {
    this.totalPrice = Double.parseDouble(tmp);
  }


  /**
   * Sets the statusId attribute of the OrderProductOption object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the OrderProductOption object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the booleanValue attribute of the OrderProductOption object
   *
   * @param tmp The new booleanValue value
   */
  public void setBooleanValue(boolean tmp) {
    this.booleanValue = tmp;
  }


  /**
   * Sets the booleanValue attribute of the OrderProductOption object
   *
   * @param tmp The new booleanValue value
   */
  public void setBooleanValue(String tmp) {
    this.booleanValue = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the floatValue attribute of the OrderProductOption object
   *
   * @param tmp The new floatValue value
   */
  public void setFloatValue(double tmp) {
    this.floatValue = tmp;
  }


  /**
   * Sets the timestampValue attribute of the OrderProductOption object
   *
   * @param tmp The new timestampValue value
   */
  public void setTimestampValue(Timestamp tmp) {
    this.timestampValue = tmp;
  }


  /**
   * Sets the timestampValue attribute of the OrderProductOption object
   *
   * @param tmp The new timestampValue value
   */
  public void setTimestampValue(String tmp) {
    this.timestampValue = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the integerValue attribute of the OrderProductOption object
   *
   * @param tmp The new integerValue value
   */
  public void setIntegerValue(int tmp) {
    this.integerValue = tmp;
  }


  /**
   * Sets the integerValue attribute of the OrderProductOption object
   *
   * @param tmp The new integerValue value
   */
  public void setIntegerValue(String tmp) {
    this.integerValue = Integer.parseInt(tmp);
  }


  /**
   * Sets the textValue attribute of the OrderProductOption object
   *
   * @param tmp The new textValue value
   */
  public void setTextValue(String tmp) {
    this.textValue = tmp;
  }


  /**
   * Gets the booleanValue attribute of the OrderProductOption object
   *
   * @return The booleanValue value
   */
  public boolean getBooleanValue() {
    return booleanValue;
  }


  /**
   * Gets the floatValue attribute of the OrderProductOption object
   *
   * @return The floatValue value
   */
  public double getFloatValue() {
    return floatValue;
  }


  /**
   * Gets the timestampValue attribute of the OrderProductOption object
   *
   * @return The timestampValue value
   */
  public Timestamp getTimestampValue() {
    return timestampValue;
  }


  /**
   * Gets the integerValue attribute of the OrderProductOption object
   *
   * @return The integerValue value
   */
  public int getIntegerValue() {
    return integerValue;
  }


  /**
   * Gets the textValue attribute of the OrderProductOption object
   *
   * @return The textValue value
   */
  public String getTextValue() {
    return textValue;
  }


  /**
   * Gets the id attribute of the OrderProductOption object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the itemId attribute of the OrderProductOption object
   *
   * @return The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   * Gets the productOptionId attribute of the OrderProductOption object
   *
   * @return The productOptionId value
   */
  public int getProductOptionId() {
    return productOptionId;
  }


  /**
   * Gets the quantity attribute of the OrderProductOption object
   *
   * @return The quantity value
   */
  public int getQuantity() {
    return quantity;
  }


  /**
   * Gets the priceCurrency attribute of the OrderProductOption object
   *
   * @return The priceCurrency value
   */
  public int getPriceCurrency() {
    return priceCurrency;
  }


  /**
   * Gets the priceAmount attribute of the OrderProductOption object
   *
   * @return The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   * Gets the recurringCurrency attribute of the OrderProductOption object
   *
   * @return The recurringCurrency value
   */
  public int getRecurringCurrency() {
    return recurringCurrency;
  }


  /**
   * Gets the recurringAmount attribute of the OrderProductOption object
   *
   * @return The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   * Gets the recurringType attribute of the OrderProductOption object
   *
   * @return The recurringType value
   */
  public int getRecurringType() {
    return recurringType;
  }


  /**
   * Gets the extendedPrice attribute of the OrderProductOption object
   *
   * @return The extendedPrice value
   */
  public double getExtendedPrice() {
    return extendedPrice;
  }


  /**
   * Gets the totalPrice attribute of the OrderProductOption object
   *
   * @return The totalPrice value
   */
  public double getTotalPrice() {
    return totalPrice;
  }


  /**
   * Gets the statusId attribute of the OrderProductOption object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Constructor for the OrderProductOption object
   */
  public OrderProductOption() {
  }


  /**
   * Constructor for the OrderProductOption object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OrderProductOption(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the OrderProductOption object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OrderProductOption(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Description of the Method
   */
  public void determineTotal() {
    // determine the total
    totalPrice = priceAmount * quantity;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (this.id == -1) {
      throw new SQLException("Invalid Order Product Option ID Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT opt.*, " +
        " bool.value AS boolean_value,  " +
        " float.value AS float_value, intr.value AS integer_value, " +
        " tst.value AS timestamp_value, txt.value AS text_value " +
        " FROM order_product_options opt " +
        " LEFT JOIN order_product_option_boolean bool " +
        " ON ( opt.order_product_option_id = bool.order_product_option_id ) " +
        " LEFT JOIN order_product_option_float float " +
        " ON ( opt.order_product_option_id = float.order_product_option_id ) " +
        " LEFT JOIN order_product_option_timestamp tst " +
        " ON ( opt.order_product_option_id = tst.order_product_option_id ) " +
        " LEFT JOIN order_product_option_integer intr " +
        " ON ( opt.order_product_option_id = intr.order_product_option_id ) " +
        " LEFT JOIN order_product_option_text txt " +
        " ON ( opt.order_product_option_id = txt.order_product_option_id ) " +
        " WHERE opt.order_product_option_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Order Product Option not found");
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    // order_product_options table
    this.setId(rs.getInt("order_product_option_id"));
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

    // order_product_option_ values tables
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
    boolean result = false;
    if (!isValid(db)) {
      return result;
    }
    //TODO: remove this by making quantity INTEGER NOT NULL DEFAULT 1
    if (quantity == 0) {
      quantity = 1;
    }
    determineTotal();
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(
        db, "order_product_options_order_product_option_id_seq");
    sql.append(
        " INSERT INTO order_product_options(" + (id > -1 ? "order_product_option_id, " : "") + "item_id, product_option_id, " +
        "   quantity, price_currency, price_amount, recurring_currency, " +
        "   recurring_amount, recurring_type, extended_price, total_price, status_id) ");
    sql.append(
        " VALUES(" + (id > -1 ? "?, " : "") + "?, ?,?, ?, ?, ?, ?, ?, ?, ?, ? )");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, this.getItemId());
    pst.setInt(++i, this.getProductOptionId());
    DatabaseUtils.setInt(pst, ++i, this.getQuantity());
    DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
    DatabaseUtils.setDouble(pst, ++i, this.getPriceAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
    DatabaseUtils.setDouble(pst, ++i, this.getRecurringAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
    DatabaseUtils.setDouble(pst, ++i, this.getExtendedPrice());
    DatabaseUtils.setDouble(pst, ++i, this.getTotalPrice());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(
        db, "order_product_options_order_product_option_id_seq", id);

    if (this.getBooleanValue() != false) {
      sql = new StringBuffer("");
      sql.append(
          " INSERT INTO order_product_option_boolean ( order_product_option_id, value ) " +
          " VALUES ( ? , ? ) ");
      pst = db.prepareStatement(sql.toString());
      pst.setInt(1, id);
      pst.setBoolean(2, this.getBooleanValue());
      pst.execute();
      pst.close();
    }
    if (((int) this.getFloatValue()) > 0) {
      sql = new StringBuffer("");
      sql.append(
          " INSERT INTO order_product_option_float ( order_product_option_id, value )" +
          " VALUES ( ? , ? ) ");
      pst = db.prepareStatement(sql.toString());
      pst.setInt(1, id);
      pst.setDouble(2, this.getFloatValue());
      pst.execute();
      pst.close();
    }

    if (this.getTimestampValue() != null) {
      sql = new StringBuffer("");
      sql.append(
          " INSERT INTO order_product_option_timestamp ( order_product_option_id, value )" +
          " VALUES ( ? , ? ) ");
      pst = db.prepareStatement(sql.toString());
      pst.setInt(1, id);
      pst.setTimestamp(2, this.getTimestampValue());
      pst.execute();
      pst.close();
    }
    if (this.getIntegerValue() > -1) {
      sql = new StringBuffer("");
      sql.append(
          " INSERT INTO order_product_option_integer ( order_product_option_id, value )" +
          " VALUES ( ? , ? ) ");
      pst = db.prepareStatement(sql.toString());
      pst.setInt(1, id);
      DatabaseUtils.setInt(pst, 2, this.getIntegerValue());
      pst.execute();
      pst.close();
    }
    if (this.getTextValue() != null) {
      sql = new StringBuffer("");
      sql.append(
          " INSERT INTO order_product_option_text ( order_product_option_id, value )" +
          " VALUES ( ? , ? ) ");
      pst = db.prepareStatement(sql.toString());
      pst.setInt(1, id);
      pst.setString(2, this.getTextValue());
      pst.execute();
      pst.close();
    }
    result = true;
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = null;
    if (this.getId() == -1) {
      throw new SQLException("Order Product Option ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      pst = db.prepareStatement(
          " DELETE FROM order_product_option_boolean WHERE order_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          " DELETE FROM order_product_option_float WHERE order_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          " DELETE FROM order_product_option_timestamp WHERE order_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          " DELETE FROM order_product_option_integer WHERE order_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          " DELETE FROM order_product_option_text WHERE order_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          " DELETE FROM order_product_options WHERE order_product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
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
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " UPDATE order_product_options " +
        " SET quantity = ?, " +
        "     price_currency = ?, " +
        "     price_amount = ?, " +
        "     recurring_currency = ?, " +
        "     recurring_amount = ?, " +
        "     recurring_type = ?, " +
        "     extended_price = ?, " +
        "     total_price = ?, " +
        "     status_id = ? ");
    sql.append(" WHERE order_product_option_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getQuantity());
    DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
    DatabaseUtils.setDouble(pst, ++i, this.getPriceAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
    DatabaseUtils.setDouble(pst, ++i, this.getRecurringAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
    DatabaseUtils.setDouble(pst, ++i, this.getExtendedPrice());
    DatabaseUtils.setDouble(pst, ++i, this.getTotalPrice());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();

    sql = new StringBuffer("");
    sql.append(
        " UPDATE order_product_option_boolean " +
        " SET " + DatabaseUtils.addQuotes(db, "value")+ " = ? " +
        " WHERE order_product_option_id = ? ");
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(1, this.getBooleanValue());
    DatabaseUtils.setInt(pst, 2, this.getId());
    int result1Count = pst.executeUpdate();
    pst.close();

    sql = new StringBuffer("");
    sql.append(
        " UPDATE order_product_option_float " +
        " SET " + DatabaseUtils.addQuotes(db, "value")+ " = ? " +
        " WHERE order_product_option_id = ? ");
    pst = db.prepareStatement(sql.toString());
    pst.setDouble(1, this.getFloatValue());
    DatabaseUtils.setInt(pst, 2, this.getId());
    result1Count = pst.executeUpdate();
    pst.close();

    sql = new StringBuffer("");
    sql.append(
        " UPDATE order_product_option_timestamp " +
        " SET " + DatabaseUtils.addQuotes(db, "value")+ " = ? " +
        " WHERE order_product_option_id = ? ");
    pst = db.prepareStatement(sql.toString());
    pst.setTimestamp(1, this.getTimestampValue());
    DatabaseUtils.setInt(pst, 2, this.getId());
    result1Count = pst.executeUpdate();
    pst.close();

    sql = new StringBuffer("");
    sql.append(
        " UPDATE order_product_option_integer " +
        " SET " + DatabaseUtils.addQuotes(db, "value")+ " = ? " +
        " WHERE order_product_option_id = ? ");
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, 1, this.getIntegerValue());
    DatabaseUtils.setInt(pst, 2, this.getId());
    result1Count = pst.executeUpdate();
    pst.close();

    sql = new StringBuffer("");
    sql.append(
        " UPDATE order_product_option_text " +
        " SET " + DatabaseUtils.addQuotes(db, "value")+ " = ? " +
        " WHERE order_product_option_id = ? ");
    pst = db.prepareStatement(sql.toString());
    pst.setString(1, this.getTextValue());
    DatabaseUtils.setInt(pst, 2, this.getId());
    result1Count = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Order Product Option ID not specified");
    }
    String sql = null;
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;

    // Add code to determine the dependencies of an OrderProduct
    return dependencyList;
  }


  /**
   * Gets the valid attribute of the OrderProductOption object
   *
   * @param db Description of the Parameter
   * @return The valid value
   * @throws SQLException Description of the Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean createOptionFromQuoteProductOption(Connection db, int id) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    QuoteProductOption quoteProductOption = new QuoteProductOption(db, id);

    this.setProductOptionId(quoteProductOption.getProductOptionId());
    this.setQuantity(quoteProductOption.getQuantity());
    this.setPriceCurrency(quoteProductOption.getPriceCurrency());
    this.setPriceAmount(quoteProductOption.getPriceAmount());
    this.setRecurringCurrency(quoteProductOption.getRecurringCurrency());
    this.setRecurringAmount(quoteProductOption.getRecurringAmount());
    this.setRecurringType(quoteProductOption.getRecurringType());
    this.setExtendedPrice(quoteProductOption.getExtendedPrice());
    this.setIntegerValue(quoteProductOption.getIntegerValue());
    this.setTotalPrice(quoteProductOption.getTotalPrice());
    /*
     *  TODO: set the status
     */
    this.insert(db);
    return true;
  }
}

