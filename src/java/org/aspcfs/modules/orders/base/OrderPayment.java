package org.aspcfs.modules.orders.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;

/**
 *  This represents a Payment that is associated with an Order
 *
 * @author     ananth
 * @created    March 18, 2004
 * @version    $Id$
 */
public class OrderPayment extends GenericBean {
  private int id = -1;
  private int orderId = -1;
  private int paymentMethodId = -1;
  private double amount = 0;

  private String authorizationRefNumber = null;
  private String authorizationCode = null;
  private Timestamp authorizationDate = null;

  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  


  /**
   *  Sets the id attribute of the OrderPayment object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the OrderPayment object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orderId attribute of the OrderPayment object
   *
   * @param  tmp  The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   *  Sets the orderId attribute of the OrderPayment object
   *
   * @param  tmp  The new orderId value
   */
  public void setOrderId(String tmp) {
    this.orderId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the paymentMethodId attribute of the OrderPayment object
   *
   * @param  tmp  The new paymentMethodId value
   */
  public void setPaymentMethodId(int tmp) {
    this.paymentMethodId = tmp;
  }


  /**
   *  Sets the paymentMethodId attribute of the OrderPayment object
   *
   * @param  tmp  The new paymentMethodId value
   */
  public void setPaymentMethodId(String tmp) {
    this.paymentMethodId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the amount attribute of the OrderPayment object
   *
   * @param  tmp  The new amount value
   */
  public void setAmount(double tmp) {
    this.amount = tmp;
  }


  /**
   *  Sets the authorizationRefNumber attribute of the OrderPayment object
   *
   * @param  tmp  The new authorizationRefNumber value
   */
  public void setAuthorizationRefNumber(String tmp) {
    this.authorizationRefNumber = tmp;
  }


  /**
   *  Sets the authorizationCode attribute of the OrderPayment object
   *
   * @param  tmp  The new authorizationCode value
   */
  public void setAuthorizationCode(String tmp) {
    this.authorizationCode = tmp;
  }


  /**
   *  Sets the authorizationDate attribute of the OrderPayment object
   *
   * @param  tmp  The new authorizationDate value
   */
  public void setAuthorizationDate(Timestamp tmp) {
    this.authorizationDate = tmp;
  }


  /**
   *  Sets the authorizationDate attribute of the OrderPayment object
   *
   * @param  tmp  The new authorizationDate value
   */
  public void setAuthorizationDate(String tmp) {
    this.authorizationDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the OrderPayment object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the OrderPayment object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the OrderPayment object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the OrderPayment object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the OrderPayment object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the OrderPayment object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the OrderPayment object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the OrderPayment object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the id attribute of the OrderPayment object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the orderId attribute of the OrderPayment object
   *
   * @return    The orderId value
   */
  public int getOrderId() {
    return orderId;
  }


  /**
   *  Gets the paymentMethodId attribute of the OrderPayment object
   *
   * @return    The paymentMethodId value
   */
  public int getPaymentMethodId() {
    return paymentMethodId;
  }


  /**
   *  Gets the amount attribute of the OrderPayment object
   *
   * @return    The amount value
   */
  public double getAmount() {
    return amount;
  }


  /**
   *  Gets the authorizationRefNumber attribute of the OrderPayment object
   *
   * @return    The authorizationRefNumber value
   */
  public String getAuthorizationRefNumber() {
    return authorizationRefNumber;
  }


  /**
   *  Gets the authorizationCode attribute of the OrderPayment object
   *
   * @return    The authorizationCode value
   */
  public String getAuthorizationCode() {
    return authorizationCode;
  }


  /**
   *  Gets the authorizationDate attribute of the OrderPayment object
   *
   * @return    The authorizationDate value
   */
  public Timestamp getAuthorizationDate() {
    return authorizationDate;
  }


  /**
   *  Gets the enteredBy attribute of the OrderPayment object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the OrderPayment object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the OrderPayment object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the OrderPayment object
   *
   * @return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *Constructor for the OrderPayment object
   */
  public OrderPayment() { }


  /**
   *Constructor for the OrderPayment object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public OrderPayment(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *Constructor for the OrderPayment object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public OrderPayment(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Payment Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT op.payment_id, op.order_id, op.payment_method_id, op.payment_amount, " +
        " 			 op.authorization_ref_number, op.authorization_code, op.authorization_date, " +
        "				 op.entered, op.enteredby, op.modified, op.modifiedby " +
        " FROM order_payment op " +
        " WHERE op.payment_id = ? "
        );
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
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
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
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    if (!isValid(db)) {
      return result;
    }

    StringBuffer sql = new StringBuffer();
    sql.append(
        " INSERT INTO order_payment(order_id, payment_method_id, payment_amount, " +
        " 	authorization_ref_number, authorization_code, authorization_date "
        );
    if (entered != null) {
      sql.append(" entered, ");
    }
    sql.append(" enteredby, ");
    if (modified != null) {
      sql.append(" modified, ");
    }
    sql.append(" modifiedby )");
    sql.append("VALUES( ?, ?, ?, ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    sql.append("?, ");
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getOrderId());
    // payment method cannot be null since
    // an order must be associated with payment details either credit card or EFT details
    pst.setInt(++i, this.getPaymentMethodId());
    DatabaseUtils.setDouble(pst, ++i, this.getAmount());
    pst.setString(++i, this.getAuthorizationRefNumber());
    pst.setString(++i, this.getAuthorizationCode());
    pst.setTimestamp(++i, this.getAuthorizationDate());
    if (entered != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    pst.setInt(++i, this.getEnteredBy());
    if (modified != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    pst.setInt(++i, this.getModifiedBy());

    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "order_payment_payment_id_seq");
    result = true;
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Order ID not specified");
    }
    try {
      db.setAutoCommit(false);
      
      // TODO : map a payment to the corresponding type of payment details and delete that detail
      // delete all the credit card details associated with this payment
      // delete all the eft(Electronic Fund Transfer) details associated with this payment
      // delete the payment
      
      PreparedStatement pst = null;
      pst = db.prepareStatement(" DELETE FROM payment_creditcard WHERE payment_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      
      pst = db.prepareStatement(" DELETE FROM payment_eft WHERE payment_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      
      pst = db.prepareStatement(" DELETE FROM order_payment WHERE order_id = ? ");
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
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (!isValid(db)) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(" UPDATE order_payment " + 
               " SET payment_method_id = ?, " + 
               "     payment_amount = ?, " +
               "     authorization_ref_number = ?, " + 
               "     authorization_code = ?, " + 
               "     authorization_date = ?, " + 
               "     entered = ?, " + 
               "     enteredby = ?, " +
               "     modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
               "     modifiedby = ? , " +
               " WHERE payment_id = ? " );

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    // payment method cannot be null since
    // an order must be associated with payment details either credit card or EFT details
    pst.setInt(++i, this.getPaymentMethodId());   
    DatabaseUtils.setDouble(pst, ++i, this.getAmount());
    pst.setString(++i, this.getAuthorizationRefNumber());
    pst.setString(++i, this.getAuthorizationCode());
    pst.setTimestamp(++i, this.getAuthorizationDate());
    pst.setTimestamp(++i, this.getEntered());
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Gets the valid attribute of the OrderPayment object
   *
   * @param  db                Description of the Parameter
   * @return                   The valid value
   * @exception  SQLException  Description of the Exception
   */
  public boolean isValid(Connection db) throws SQLException {
    return true;
  }
}

