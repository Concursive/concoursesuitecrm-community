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

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id: CustomerProductHistory.java,v 1.3 2004/06/03 16:04:04
 *          mrajkowski Exp $
 * @created April 20, 2004
 */
public class CustomerProductHistory extends GenericBean {

  private int id = -1;
  private int customerProductId = -1;
  private int orgId = -1;
  private int orderId = -1;
  private int orderItemId = -1;
  private Timestamp productStartDate = null;
  private Timestamp productEndDate = null;
  // record status
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = true;
  // others
  private String productName = null;
  private String shortDescription = null;
  private String categoryName = null;
  private double priceAmount = 0.0;


  /**
   * Sets the shortDescription attribute of the CustomerProductHistory object
   *
   * @param tmp The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   * Gets the shortDescription attribute of the CustomerProductHistory object
   *
   * @return The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   * Sets the productName attribute of the CustomerProductHistory object
   *
   * @param tmp The new productName value
   */
  public void setProductName(String tmp) {
    this.productName = tmp;
  }


  /**
   * Sets the categoryName attribute of the CustomerProductHistory object
   *
   * @param tmp The new categoryName value
   */
  public void setCategoryName(String tmp) {
    this.categoryName = tmp;
  }


  /**
   * Sets the priceAmount attribute of the CustomerProductHistory object
   *
   * @param tmp The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   * Gets the productName attribute of the CustomerProductHistory object
   *
   * @return The productName value
   */
  public String getProductName() {
    return productName;
  }


  /**
   * Gets the categoryName attribute of the CustomerProductHistory object
   *
   * @return The categoryName value
   */
  public String getCategoryName() {
    return categoryName;
  }


  /**
   * Gets the priceAmount attribute of the CustomerProductHistory object
   *
   * @return The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   * Sets the orderItemId attribute of the CustomerProductHistory object
   *
   * @param tmp The new orderItemId value
   */
  public void setOrderItemId(int tmp) {
    this.orderItemId = tmp;
  }


  /**
   * Sets the orderItemId attribute of the CustomerProductHistory object
   *
   * @param tmp The new orderItemId value
   */
  public void setOrderItemId(String tmp) {
    this.orderItemId = Integer.parseInt(tmp);
  }


  /**
   * Gets the orderItemId attribute of the CustomerProductHistory object
   *
   * @return The orderItemId value
   */
  public int getOrderItemId() {
    return orderItemId;
  }


  /**
   * Sets the id attribute of the CustomerProductHistory object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the CustomerProductHistory object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the customerProductId attribute of the CustomerProductHistory object
   *
   * @param tmp The new customerProductId value
   */
  public void setCustomerProductId(int tmp) {
    this.customerProductId = tmp;
  }


  /**
   * Sets the customerProductId attribute of the CustomerProductHistory object
   *
   * @param tmp The new customerProductId value
   */
  public void setCustomerProductId(String tmp) {
    this.customerProductId = Integer.parseInt(tmp);
  }


  /**
   * Sets the orgId attribute of the CustomerProductHistory object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the CustomerProductHistory object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the orderId attribute of the CustomerProductHistory object
   *
   * @param tmp The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   * Sets the orderId attribute of the CustomerProductHistory object
   *
   * @param tmp The new orderId value
   */
  public void setOrderId(String tmp) {
    this.orderId = Integer.parseInt(tmp);
  }


  /**
   * Sets the productStartDate attribute of the CustomerProductHistory object
   *
   * @param tmp The new productStartDate value
   */
  public void setProductStartDate(Timestamp tmp) {
    this.productStartDate = tmp;
  }


  /**
   * Sets the productStartDate attribute of the CustomerProductHistory object
   *
   * @param tmp The new productStartDate value
   */
  public void setProductStartDate(String tmp) {
    this.productStartDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the productEndDate attribute of the CustomerProductHistory object
   *
   * @param tmp The new productEndDate value
   */
  public void setProductEndDate(Timestamp tmp) {
    this.productEndDate = tmp;
  }


  /**
   * Sets the productEndDate attribute of the CustomerProductHistory object
   *
   * @param tmp The new productEndDate value
   */
  public void setProductEndDate(String tmp) {
    this.productEndDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the entered attribute of the CustomerProductHistory object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the CustomerProductHistory object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the CustomerProductHistory object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the CustomerProductHistory object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the CustomerProductHistory object
   *
   * @param tmp The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the CustomerProductHistory object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the CustomerProductHistory object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the CustomerProductHistory object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the CustomerProductHistory object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the CustomerProductHistory object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the id attribute of the CustomerProductHistory object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the customerProductId attribute of the CustomerProductHistory object
   *
   * @return The customerProductId value
   */
  public int getCustomerProductId() {
    return customerProductId;
  }


  /**
   * Gets the orgId attribute of the CustomerProductHistory object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Gets the orderId attribute of the CustomerProductHistory object
   *
   * @return The orderId value
   */
  public int getOrderId() {
    return orderId;
  }


  /**
   * Gets the productStartDate attribute of the CustomerProductHistory object
   *
   * @return The productStartDate value
   */
  public Timestamp getProductStartDate() {
    return productStartDate;
  }


  /**
   * Gets the productEndDate attribute of the CustomerProductHistory object
   *
   * @return The productEndDate value
   */
  public Timestamp getProductEndDate() {
    return productEndDate;
  }


  /**
   * Gets the entered attribute of the CustomerProductHistory object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the CustomerProductHistory object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the CustomerProductHistory object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the CustomerProductHistory object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the enabled attribute of the CustomerProductHistory object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Constructor for the CustomerProductHistory object
   */
  public CustomerProductHistory() {
  }


  /**
   * Constructor for the CustomerProductHistory object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CustomerProductHistory(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the CustomerProductHistory object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CustomerProductHistory(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Customer Product History Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT cph.history_id, cph.customer_product_id, cph.org_id, cph.order_id, " +
        "        cph.product_start_date, cph.product_end_date, cph.entered, cph.enteredby, " +
        "        cph.modified, cph.modifiedby, cph.order_item_id, " +
        "        pc.product_name, pc.short_description, pcp.price_amount, pcat.category_name " +
        " FROM customer_product_history cph " +
        " LEFT JOIN order_entry o ON (cph.order_id = o.order_id) " +
        " LEFT JOIN order_product op ON (cph.order_item_id = op.item_id) " +
        " LEFT JOIN product_catalog pc ON (op.product_id = pc.product_id) " +
        " LEFT JOIN product_catalog_category_map pccm ON (pc.product_id = pccm.product_id) " +
        " LEFT JOIN product_category pcat ON (pccm.category_id = pcat.category_id) " +
        " LEFT JOIN product_catalog_pricing pcp ON (pc.product_id = pcp.product_id) " +
        " WHERE cph.history_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Customer Product History not found");
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    // customer_product_history table
    this.setId(rs.getInt("history_id"));
    customerProductId = rs.getInt("customer_product_id");
    orgId = rs.getInt("org_id");
    orderId = DatabaseUtils.getInt(rs, "order_id");

    productStartDate = rs.getTimestamp("product_start_date");
    productEndDate = rs.getTimestamp("product_end_date");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");

    orderItemId = rs.getInt("order_item_id");
    productName = rs.getString("product_name");
    shortDescription = rs.getString("short_description");
    categoryName = rs.getString("category_name");
    priceAmount = rs.getDouble("price_amount");
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

    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(
        db, "customer_product_history_history_id_seq");
    sql.append(
        "INSERT INTO customer_product_history (" + (id > -1 ? "history_id, " : "") +
        "customer_product_id, org_id, order_id, " +
        " product_start_date, product_end_date, ");

    sql.append("entered, ");
    sql.append("enteredby, ");
    sql.append("modified, ");
    sql.append("modifiedby, order_item_id) ");
    sql.append("VALUES( ?, ?, ?, ?, ?, ");
    if (id > -1) {
      sql.append("?, ");
    }
    if (entered != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("?, ");
    if (modified != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("?, ? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, this.getCustomerProductId());
    pst.setInt(++i, this.getOrgId());
    DatabaseUtils.setInt(pst, ++i, this.getOrderId());

    DatabaseUtils.setTimestamp(pst, ++i, this.getProductStartDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getProductEndDate());

    if (entered != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    pst.setInt(++i, this.getEnteredBy());
    if (modified != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getOrderItemId());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(
        db, "customer_product_history_history_id_seq", id);
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
    if (this.getId() == -1) {
      throw new SQLException("Customer Product History ID not specified");
    }

    // delete the customer product history
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM customer_product_history WHERE history_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();
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
        " UPDATE customer_product_history " +
        " SET order_id = ?, " +
        "     order_item_id = ? " +
        "     product_start_date = ?, " +
        "     product_end_date = ?, " +
        "     modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "     modifiedby = ? ");
    sql.append("WHERE history_id = ? ");
    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getOrderId());
    DatabaseUtils.setInt(pst, ++i, this.getOrderItemId());
    pst.setTimestamp(++i, this.getProductStartDate());
    pst.setTimestamp(++i, this.getProductEndDate());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    if(this.getModified() != null){
      pst.setTimestamp(++i, this.getModified());
    }
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

}

