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
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;

/**
 *  This represents a Payment's Electronic Funds Transfer Details
 *
 *@author     ananth
 *@created    March 18, 2004
 *@version    $Id$
 */
public class PaymentEFT extends GenericBean {
  private int id = -1;
  private int paymentId = -1;
  private String bankName = null;
  private String routingNumber = null;
  private String accountNumber = null;
  private String nameOnAccount = null;
  private String companyNameOnAccount = null;

  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;


  /**
   *  Sets the id attribute of the PaymentEFT object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PaymentEFT object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the paymentId attribute of the PaymentEFT object
   *
   *@param  tmp  The new paymentId value
   */
  public void setPaymentId(int tmp) {
    this.paymentId = tmp;
  }


  /**
   *  Sets the paymentId attribute of the PaymentEFT object
   *
   *@param  tmp  The new paymentId value
   */
  public void setPaymentId(String tmp) {
    this.paymentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the bankName attribute of the PaymentEFT object
   *
   *@param  tmp  The new bankName value
   */
  public void setBankName(String tmp) {
    this.bankName = tmp;
  }



  /**
   *  Sets the routingNumber attribute of the PaymentEFT object
   *
   *@param  tmp  The new routingNumber value
   */
  public void setRoutingNumber(String tmp) {
    this.routingNumber = tmp;
  }



  /**
   *  Sets the accountNumber attribute of the PaymentEFT object
   *
   *@param  tmp  The new accountNumber value
   */
  public void setAccountNumber(String tmp) {
    this.accountNumber = tmp;
  }


  /**
   *  Sets the nameOnAccount attribute of the PaymentEFT object
   *
   *@param  tmp  The new nameOnAccount value
   */
  public void setNameOnAccount(String tmp) {
    this.nameOnAccount = tmp;
  }


  /**
   *  Sets the companyNameOnAccount attribute of the PaymentEFT object
   *
   *@param  tmp  The new companyNameOnAccount value
   */
  public void setCompanyNameOnAccount(String tmp) {
    this.companyNameOnAccount = tmp;
  }


  /**
   *  Sets the entered attribute of the PaymentEFT object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the PaymentEFT object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the PaymentEFT object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the PaymentEFT object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the PaymentEFT object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the PaymentEFT object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the PaymentEFT object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the PaymentEFT object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }



  /**
   *  Gets the id attribute of the PaymentEFT object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the paymentId attribute of the PaymentEFT object
   *
   *@return    The paymentId value
   */
  public int getPaymentId() {
    return paymentId;
  }


  /**
   *  Gets the bankName attribute of the PaymentEFT object
   *
   *@return    The bankName value
   */
  public String getBankName() {
    return bankName;
  }


  /**
   *  Gets the routingNumber attribute of the PaymentEFT object
   *
   *@return    The routingNumber value
   */
  public String getRoutingNumber() {
    return routingNumber;
  }


  /**
   *  Gets the accountNumber attribute of the PaymentEFT object
   *
   *@return    The accountNumber value
   */
  public String getAccountNumber() {
    return accountNumber;
  }


  /**
   *  Gets the nameOnAccount attribute of the PaymentEFT object
   *
   *@return    The nameOnAccount value
   */
  public String getNameOnAccount() {
    return nameOnAccount;
  }


  /**
   *  Gets the companyNameOnAccount attribute of the PaymentEFT object
   *
   *@return    The companyNameOnAccount value
   */
  public String getCompanyNameOnAccount() {
    return companyNameOnAccount;
  }


  /**
   *  Gets the entered attribute of the PaymentEFT object
   *
   *@return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the PaymentEFT object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the PaymentEFT object
   *
   *@return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the PaymentEFT object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }



  /**
   *  Constructor for the PaymentEFT object
   */
  public PaymentEFT() { }


  /**
   *  Constructor for the PaymentEFT object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public PaymentEFT(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the PaymentEFT object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public PaymentEFT(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid EFT ID Specified");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT eft.bank_id, eft.payment_id, " +
        "        eft.bank_name, eft.routing_number, " +
        "        eft.account_number, eft.name_on_account, " +
        "        eft.company_name_on_account, eft.entered, " +
        "        eft.enteredby, eft.modified, eft.modifiedby " +
        " FROM payment_eft eft " +
        " WHERE eft.bank_id = ? "
        );
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Credit Card Entry not found");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    //payment_eft table
    this.setId(rs.getInt("bank_id"));
    paymentId = rs.getInt("payment_id");
    bankName = rs.getString("bank_name");
    routingNumber = rs.getString("routing_number");
    accountNumber = rs.getString("account_number");
    nameOnAccount = rs.getString("name_on_account");
    companyNameOnAccount = rs.getString("company_name_on_account");

    entered = rs.getTimestamp("entered");
    enteredBy = DatabaseUtils.getInt(rs, "enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = DatabaseUtils.getInt(rs, "modifiedby");
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
        " INSERT INTO payment_eft(payment_id, bank_name, routing_number, account_number, " +
        " 	name_on_account, company_name_on_account "
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
    pst.setInt(++i, this.getPaymentId());
    pst.setString(++i, this.getBankName());
    pst.setString(++i, this.getRoutingNumber());
    pst.setString(++i, this.getAccountNumber());
    pst.setString(++i, this.getNameOnAccount());
    pst.setString(++i, this.getCompanyNameOnAccount());
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
    id = DatabaseUtils.getCurrVal(db, "payment_eft_bank_id_seq");
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
      throw new SQLException("Bank ID not specified");
    }
    try {
      db.setAutoCommit(false);
      // delete the credit card info

      PreparedStatement pst = db.prepareStatement(" DELETE FROM payment_eft WHERE bank_id = ? ");
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
    sql.append(" UPDATE order_eft " +
        " SET bank_name = ?, " +
        "     routing_number = ?, " +
        "     account_number = ?, " +
        "     name_on_account= ?, " +
        "     company_name_on_account = ? " +
        "     entered = ?, " +
        "     enteredby = ?, " +
        "     modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "     modifiedby = ?, " +
        " WHERE bank_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getBankName());
    pst.setString(++i, this.getRoutingNumber());
    pst.setString(++i, this.getAccountNumber());
    pst.setString(++i, this.getNameOnAccount());
    pst.setString(++i, this.getCompanyNameOnAccount());
    pst.setTimestamp(++i, this.getEntered());
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Gets the valid attribute of the PaymentEFT object
   *
   *@param  db                Description of the Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  public boolean isValid(Connection db) throws SQLException {
    return true;
  }
}

