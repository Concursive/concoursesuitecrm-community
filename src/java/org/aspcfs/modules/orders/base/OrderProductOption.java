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

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;

/**
 *  This represents a Product Option of a particular product associated with a
 *  particular Order
 *
 *@author     ananth
 *@created    March 18, 2004
 *@version    $Id: OrderProductOption.java,v 1.2 2004/05/04 15:52:27 mrajkowski
 *      Exp $
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
  // product details
  private int productId = -1;


  /**
   *  Sets the id attribute of the OrderProductOption object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the OrderProductOption object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the itemId attribute of the OrderProductOption object
   *
   *@param  tmp  The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   *  Sets the itemId attribute of the OrderProductOption object
   *
   *@param  tmp  The new itemId value
   */
  public void setItemId(String tmp) {
    this.itemId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productOptionId attribute of the OrderProductOption object
   *
   *@param  tmp  The new productOptionId value
   */
  public void setProductOptionId(int tmp) {
    this.productOptionId = tmp;
  }


  /**
   *  Sets the productOptionId attribute of the OrderProductOption object
   *
   *@param  tmp  The new productOptionId value
   */
  public void setProductOptionId(String tmp) {
    this.productOptionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quantity attribute of the OrderProductOption object
   *
   *@param  tmp  The new quantity value
   */
  public void setQuantity(int tmp) {
    this.quantity = tmp;
  }


  /**
   *  Sets the quantity attribute of the OrderProductOption object
   *
   *@param  tmp  The new quantity value
   */
  public void setQuantity(String tmp) {
    this.quantity = Integer.parseInt(tmp);
  }


  /**
   *  Sets the priceCurrency attribute of the OrderProductOption object
   *
   *@param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(int tmp) {
    this.priceCurrency = tmp;
  }


  /**
   *  Sets the priceCurrency attribute of the OrderProductOption object
   *
   *@param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(String tmp) {
    this.priceCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the priceAmount attribute of the OrderProductOption object
   *
   *@param  tmp  The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   *  Sets the priceAmount attribute of the OrderProductOption object
   *
   *@param  tmp  The new priceAmount value
   */
  public void setPriceAmount(String tmp) {
    this.priceAmount = Double.parseDouble(tmp);
  }


  /**
   *  Sets the recurringCurrency attribute of the OrderProductOption object
   *
   *@param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(int tmp) {
    this.recurringCurrency = tmp;
  }


  /**
   *  Sets the recurringCurrency attribute of the OrderProductOption object
   *
   *@param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(String tmp) {
    this.recurringCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the recurringAmount attribute of the OrderProductOption object
   *
   *@param  tmp  The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   *  Sets the recurringAmount attribute of the OrderProductOption object
   *
   *@param  tmp  The new recurringAmount value
   */
  public void setRecurringAmount(String tmp) {
    this.recurringAmount = Double.parseDouble(tmp);
  }


  /**
   *  Sets the recurringType attribute of the OrderProductOption object
   *
   *@param  tmp  The new recurringType value
   */
  public void setRecurringType(int tmp) {
    this.recurringType = tmp;
  }


  /**
   *  Sets the recurringType attribute of the OrderProductOption object
   *
   *@param  tmp  The new recurringType value
   */
  public void setRecurringType(String tmp) {
    this.recurringType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the extendedPrice attribute of the OrderProductOption object
   *
   *@param  tmp  The new extendedPrice value
   */
  public void setExtendedPrice(double tmp) {
    this.extendedPrice = tmp;
  }


  /**
   *  Sets the extendedPrice attribute of the OrderProductOption object
   *
   *@param  tmp  The new extendedPrice value
   */
  public void setExtendedPrice(String tmp) {
    this.extendedPrice = Double.parseDouble(tmp);
  }


  /**
   *  Sets the totalPrice attribute of the OrderProductOption object
   *
   *@param  tmp  The new totalPrice value
   */
  public void setTotalPrice(double tmp) {
    this.totalPrice = tmp;
  }


  /**
   *  Sets the totalPrice attribute of the OrderProductOption object
   *
   *@param  tmp  The new totalPrice value
   */
  public void setTotalPrice(String tmp) {
    this.totalPrice = Double.parseDouble(tmp);
  }


  /**
   *  Sets the statusId attribute of the OrderProductOption object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the OrderProductOption object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productId attribute of the OrderProductOption object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the OrderProductOption object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the id attribute of the OrderProductOption object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the itemId attribute of the OrderProductOption object
   *
   *@return    The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   *  Gets the productOptionId attribute of the OrderProductOption object
   *
   *@return    The productOptionId value
   */
  public int getProductOptionId() {
    return productOptionId;
  }


  /**
   *  Gets the quantity attribute of the OrderProductOption object
   *
   *@return    The quantity value
   */
  public int getQuantity() {
    return quantity;
  }


  /**
   *  Gets the priceCurrency attribute of the OrderProductOption object
   *
   *@return    The priceCurrency value
   */
  public int getPriceCurrency() {
    return priceCurrency;
  }


  /**
   *  Gets the priceAmount attribute of the OrderProductOption object
   *
   *@return    The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   *  Gets the recurringCurrency attribute of the OrderProductOption object
   *
   *@return    The recurringCurrency value
   */
  public int getRecurringCurrency() {
    return recurringCurrency;
  }


  /**
   *  Gets the recurringAmount attribute of the OrderProductOption object
   *
   *@return    The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   *  Gets the recurringType attribute of the OrderProductOption object
   *
   *@return    The recurringType value
   */
  public int getRecurringType() {
    return recurringType;
  }


  /**
   *  Gets the extendedPrice attribute of the OrderProductOption object
   *
   *@return    The extendedPrice value
   */
  public double getExtendedPrice() {
    return extendedPrice;
  }


  /**
   *  Gets the totalPrice attribute of the OrderProductOption object
   *
   *@return    The totalPrice value
   */
  public double getTotalPrice() {
    return totalPrice;
  }


  /**
   *  Gets the statusId attribute of the OrderProductOption object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the productId attribute of the OrderProductOption object
   *
   *@return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Constructor for the OrderProductOption object
   */
  public OrderProductOption() { }


  /**
   *  Constructor for the OrderProductOption object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public OrderProductOption(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the OrderProductOption object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public OrderProductOption(ResultSet rs) throws SQLException {
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
    if (this.id == -1) {
      throw new SQLException("Invalid Order Product Option ID Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT opt.order_product_option_id, opt.item_id, opt.product_option_id, " +
        "        opt.quantity, opt.price_currency, opt.price_amount, opt.recurring_currency, " +
        "   	   opt.recurring_amount, opt.recurring_type, opt.extended_price, opt.total_price, opt.status_id, " +
        "        prod.product_id " +
        " FROM order_product_options opt, order_product prod " +
        " WHERE opt.item_id = prod.item_id AND opt.order_product_option_id = ? "
        );
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
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
    productId = DatabaseUtils.getInt(rs, "product_id");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    if (!isValid(db)) {
      return result;
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        " INSERT INTO order_product_options(item_id, product_option_id, " +
        "   quantity, price_currency, price_amount, recurring_currency, " +
        "   recurring_amount, recurring_type, extended_price, total_price, status_id) "
        );

    sql.append(" VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
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
    id = DatabaseUtils.getCurrVal(db, "order_product_options_order_product_option_id_seq");
    result = true;
    return result;
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
      throw new SQLException("Order Product Option ID not specified");
    }
    try {
      PreparedStatement pst = db.prepareStatement(
          " DELETE FROM order_product_options WHERE order_product_option_id = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
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
    sql.append(" UPDATE order_product_options " +
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
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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
   *  Gets the valid attribute of the OrderProductOption object
   *
   *@param  db                Description of the Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    return true;
  }
}

