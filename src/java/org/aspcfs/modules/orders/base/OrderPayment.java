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
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 * This represents a Payment that is associated with an Order
 *
 * @author ananth
 * @version $Id$
 * @created March 18, 2004
 */
public class OrderPayment extends GenericBean {
  private int id = -1;
  private int orderId = -1;
  private int orderItemId = -1;
  private int historyId = -1;
  private int paymentMethodId = -1;
  private double amount = 0;

  private String authorizationRefNumber = null;
  private String authorizationCode = null;
  private Timestamp authorizationDate = null;
  private Timestamp dateToProcess = null;
  private int creditCardId = -1;
  private int bankId = -1;
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  private int statusId = -1;
  //resources
  private String status = null;
  private boolean buildOrderPaymentStatusList = false;
  private OrderPaymentStatusList orderPaymentStatusList = null;


  /**
   * Sets the dateToProcess attribute of the OrderPayment object
   *
   * @param tmp The new dateToProcess value
   */
  public void setDateToProcess(Timestamp tmp) {
    this.dateToProcess = tmp;
  }


  /**
   * Sets the dateToProcess attribute of the OrderPayment object
   *
   * @param tmp The new dateToProcess value
   */
  public void setDateToProcess(String tmp) {
    this.dateToProcess = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the creditCardId attribute of the OrderPayment object
   *
   * @param tmp The new creditCardId value
   */
  public void setCreditCardId(int tmp) {
    this.creditCardId = tmp;
  }


  /**
   * Sets the creditCardId attribute of the OrderPayment object
   *
   * @param tmp The new creditCardId value
   */
  public void setCreditCardId(String tmp) {
    this.creditCardId = Integer.parseInt(tmp);
  }


  /**
   * Sets the bankId attribute of the OrderPayment object
   *
   * @param tmp The new bankId value
   */
  public void setBankId(int tmp) {
    this.bankId = tmp;
  }


  /**
   * Sets the bankId attribute of the OrderPayment object
   *
   * @param tmp The new bankId value
   */
  public void setBankId(String tmp) {
    this.bankId = Integer.parseInt(tmp);
  }


  /**
   * Sets the orderItemId attribute of the OrderPayment object
   *
   * @param tmp The new orderItemId value
   */
  public void setOrderItemId(int tmp) {
    this.orderItemId = tmp;
  }


  /**
   * Sets the orderItemId attribute of the OrderPayment object
   *
   * @param tmp The new orderItemId value
   */
  public void setOrderItemId(String tmp) {
    this.orderItemId = Integer.parseInt(tmp);
  }


  /**
   * Sets the historyId attribute of the OrderPayment object
   *
   * @param tmp The new historyId value
   */
  public void setHistoryId(int tmp) {
    this.historyId = tmp;
  }


  /**
   * Sets the historyId attribute of the OrderPayment object
   *
   * @param tmp The new historyId value
   */
  public void setHistoryId(String tmp) {
    this.historyId = Integer.parseInt(tmp);
  }


  /**
   * Sets the statusId attribute of the OrderPayment object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the OrderPayment object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the status attribute of the OrderPayment object
   *
   * @param tmp The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   * Sets the buildPaymentStatusList attribute of the OrderPayment object
   *
   * @param tmp The new buildPaymentStatusList value
   */
  public void setBuildOrderPaymentStatusList(boolean tmp) {
    this.buildOrderPaymentStatusList = tmp;
  }


  /**
   * Sets the buildPaymentStatusList attribute of the OrderPayment object
   *
   * @param tmp The new buildPaymentStatusList value
   */
  public void setBuildOrderPaymentStatusList(String tmp) {
    this.buildOrderPaymentStatusList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the orderPaymentStatusList attribute of the OrderPayment object
   *
   * @param tmp The new orderPaymentStatusList value
   */
  public void setOrderPaymentStatusList(OrderPaymentStatusList tmp) {
    this.orderPaymentStatusList = tmp;
  }


  /**
   * Gets the buildPaymentStatusList attribute of the OrderPayment object
   *
   * @return The buildPaymentStatusList value
   */
  public boolean getBuildOrderPaymentStatusList() {
    return buildOrderPaymentStatusList;
  }


  /**
   * Gets the orderPaymentStatusList attribute of the OrderPayment object
   *
   * @return The orderPaymentStatusList value
   */
  public OrderPaymentStatusList getOrderPaymentStatusList() {
    return orderPaymentStatusList;
  }


  /**
   * Gets the status attribute of the OrderPayment object
   *
   * @return The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   * Gets the statusId attribute of the OrderPayment object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Gets the orderItemId attribute of the OrderPayment object
   *
   * @return The orderItemId value
   */
  public int getOrderItemId() {
    return orderItemId;
  }


  /**
   * Gets the historyId attribute of the OrderPayment object
   *
   * @return The historyId value
   */
  public int getHistoryId() {
    return historyId;
  }


  /**
   * Gets the dateToProcess attribute of the OrderPayment object
   *
   * @return The dateToProcess value
   */
  public Timestamp getDateToProcess() {
    return dateToProcess;
  }


  /**
   * Gets the creditCardId attribute of the OrderPayment object
   *
   * @return The creditCardId value
   */
  public int getCreditCardId() {
    return creditCardId;
  }


  /**
   * Gets the bankId attribute of the OrderPayment object
   *
   * @return The bankId value
   */
  public int getBankId() {
    return bankId;
  }


  /**
   * Sets the id attribute of the OrderPayment object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the OrderPayment object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the orderId attribute of the OrderPayment object
   *
   * @param tmp The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   * Sets the orderId attribute of the OrderPayment object
   *
   * @param tmp The new orderId value
   */
  public void setOrderId(String tmp) {
    this.orderId = Integer.parseInt(tmp);
  }


  /**
   * Sets the paymentMethodId attribute of the OrderPayment object
   *
   * @param tmp The new paymentMethodId value
   */
  public void setPaymentMethodId(int tmp) {
    this.paymentMethodId = tmp;
  }


  /**
   * Sets the paymentMethodId attribute of the OrderPayment object
   *
   * @param tmp The new paymentMethodId value
   */
  public void setPaymentMethodId(String tmp) {
    this.paymentMethodId = Integer.parseInt(tmp);
  }


  /**
   * Sets the amount attribute of the OrderPayment object
   *
   * @param tmp The new amount value
   */
  public void setAmount(double tmp) {
    this.amount = tmp;
  }


  /**
   * Sets the authorizationRefNumber attribute of the OrderPayment object
   *
   * @param tmp The new authorizationRefNumber value
   */
  public void setAuthorizationRefNumber(String tmp) {
    this.authorizationRefNumber = tmp;
  }


  /**
   * Sets the authorizationCode attribute of the OrderPayment object
   *
   * @param tmp The new authorizationCode value
   */
  public void setAuthorizationCode(String tmp) {
    this.authorizationCode = tmp;
  }


  /**
   * Sets the authorizationDate attribute of the OrderPayment object
   *
   * @param tmp The new authorizationDate value
   */
  public void setAuthorizationDate(Timestamp tmp) {
    this.authorizationDate = tmp;
  }


  /**
   * Sets the authorizationDate attribute of the OrderPayment object
   *
   * @param tmp The new authorizationDate value
   */
  public void setAuthorizationDate(String tmp) {
    this.authorizationDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the OrderPayment object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the OrderPayment object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the OrderPayment object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the OrderPayment object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the OrderPayment object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the OrderPayment object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the OrderPayment object
   *
   * @param tmp The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the OrderPayment object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the id attribute of the OrderPayment object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the orderId attribute of the OrderPayment object
   *
   * @return The orderId value
   */
  public int getOrderId() {
    return orderId;
  }


  /**
   * Gets the paymentMethodId attribute of the OrderPayment object
   *
   * @return The paymentMethodId value
   */
  public int getPaymentMethodId() {
    return paymentMethodId;
  }


  /**
   * Gets the amount attribute of the OrderPayment object
   *
   * @return The amount value
   */
  public double getAmount() {
    return amount;
  }


  /**
   * Gets the authorizationRefNumber attribute of the OrderPayment object
   *
   * @return The authorizationRefNumber value
   */
  public String getAuthorizationRefNumber() {
    return authorizationRefNumber;
  }


  /**
   * Gets the authorizationCode attribute of the OrderPayment object
   *
   * @return The authorizationCode value
   */
  public String getAuthorizationCode() {
    return authorizationCode;
  }


  /**
   * Gets the authorizationDate attribute of the OrderPayment object
   *
   * @return The authorizationDate value
   */
  public Timestamp getAuthorizationDate() {
    return authorizationDate;
  }


  /**
   * Gets the enteredBy attribute of the OrderPayment object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the entered attribute of the OrderPayment object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the modifiedBy attribute of the OrderPayment object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the modified attribute of the OrderPayment object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   * Constructor for the OrderPayment object
   */
  public OrderPayment() {
  }


  /**
   * Constructor for the OrderPayment object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OrderPayment(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the OrderPayment object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OrderPayment(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Payment Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT op.*, ps.description AS status_description " +
        " FROM order_payment op " +
        " LEFT JOIN lookup_payment_status ps " +
        " ON ( op.status_id = ps.code ) " +
        " WHERE op.payment_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Payment Entry not found");
    }
    if (buildOrderPaymentStatusList) {
      buildStatusList(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildStatusList(Connection db) throws SQLException {
    orderPaymentStatusList = new OrderPaymentStatusList();
    orderPaymentStatusList.setPaymentId(this.getId());
    orderPaymentStatusList.buildList(db);
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    //order_payment table
    this.setId(rs.getInt("payment_id"));
    orderId = rs.getInt("order_id");
    paymentMethodId = DatabaseUtils.getInt(rs, "payment_method_id");
    amount = DatabaseUtils.getDouble(rs, "payment_amount");
    authorizationRefNumber = rs.getString("authorization_ref_number");
    authorizationCode = rs.getString("authorization_code");
    authorizationDate = rs.getTimestamp("authorization_date");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = DatabaseUtils.getInt(rs, "modifiedby");
    orderItemId = DatabaseUtils.getInt(rs, "order_item_id");
    historyId = DatabaseUtils.getInt(rs, "history_id");
    dateToProcess = rs.getTimestamp("date_to_process");
    creditCardId = DatabaseUtils.getInt(rs, "creditcard_id");
    bankId = DatabaseUtils.getInt(rs, "bank_id");
    statusId = DatabaseUtils.getInt(rs, "status_id");

    //lookup_payment_status table
    status = rs.getString("status_description");
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
    id = DatabaseUtils.getNextSeq(db, "order_payment_payment_id_seq");
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO order_payment(order_id, order_item_id, history_id, payment_method_id, payment_amount, " +
        " 	authorization_ref_number, authorization_code, authorization_date, status_id, ");
    if (id > -1) {
      sql.append("payment_id, ");
    }
    if (entered != null) {
      sql.append(" entered, ");
    }
    sql.append(" enteredby, ");
    if (modified != null) {
      sql.append(" modified, ");
    }
    sql.append(" modifiedby, date_to_process, creditcard_id, bank_id )");
    sql.append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
    if (id > -1) {
      sql.append("?,");
    }
    if (entered != null) {
      sql.append("?, ");
    }
    sql.append("?, ");
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?, ? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getOrderId());
    DatabaseUtils.setInt(pst, ++i, this.getOrderItemId());
    DatabaseUtils.setInt(pst, ++i, this.getHistoryId());
    // payment method cannot be null since
    // an order must be associated with payment details either credit card or EFT details
    pst.setInt(++i, this.getPaymentMethodId());
    DatabaseUtils.setDouble(pst, ++i, this.getAmount());
    pst.setString(++i, this.getAuthorizationRefNumber());
    pst.setString(++i, this.getAuthorizationCode());
    pst.setTimestamp(++i, this.getAuthorizationDate());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (entered != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    pst.setInt(++i, this.getEnteredBy());
    if (modified != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setTimestamp(pst, ++i, this.getDateToProcess());
    DatabaseUtils.setInt(pst, ++i, this.getCreditCardId());
    DatabaseUtils.setInt(pst, ++i, this.getBankId());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "order_payment_payment_id_seq", id);
    insertPaymentStatus(db);
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
      throw new SQLException("Order ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      OrderPaymentStatusList statusList = new OrderPaymentStatusList();
      statusList.setPaymentId(this.getId());
      statusList.buildList(db);
      statusList.delete(db);
      statusList = null;
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM order_payment WHERE payment_id = ? ");
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
        "UPDATE order_payment " +
        " SET payment_method_id = ?, " +
        "     order_item_id = ?, " +
        "     history_id = ?, " +
        "     payment_amount = ?, " +
        "     authorization_ref_number = ?, " +
        "     authorization_code = ?, " +
        "     authorization_date = ?, " +
        "     status_id = ?, " +
        "     modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "     modifiedby = ? " +
        " WHERE payment_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    // payment method cannot be null since
    // an order must be associated with payment details either credit card or EFT details
    DatabaseUtils.setInt(pst, ++i, this.getPaymentMethodId());
    DatabaseUtils.setInt(pst, ++i, this.getOrderItemId());
    DatabaseUtils.setInt(pst, ++i, this.getHistoryId());
    DatabaseUtils.setDouble(pst, ++i, this.getAmount());
    pst.setString(++i, this.getAuthorizationRefNumber());
    pst.setString(++i, this.getAuthorizationCode());
    DatabaseUtils.setTimestamp(pst, ++i, this.getAuthorizationDate());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();
    insertPaymentStatus(db);
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insertPaymentStatus(Connection db) throws SQLException {
    OrderPaymentStatus thisStatus = new OrderPaymentStatus();
    thisStatus.setPaymentId(this.getId());
    thisStatus.setStatusId(this.getStatusId());
    // the enteredby field and modifiedby for this status should
    // be the user who is modifying the status
    thisStatus.setEnteredBy(this.getModifiedBy());
    thisStatus.setModifiedBy(this.getModifiedBy());
    thisStatus.insert(db);
  }
}

