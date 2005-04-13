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
package org.aspcfs.modules.healthcare.edit.base;

import java.sql.*;
import java.util.*;
import java.text.DateFormat;
import java.text.*;
import com.darkhorseventures.database.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.framework.servlets.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.controller.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.*;
import javax.servlet.http.*;

/**
 *  Represents a billing transaction from EDIT terminals
 *
 *@author     chris
 *@created    February 6, 2003
 *@version    $Id: TransactionRecord.java,v 1.3 2003/03/21 22:30:51 mrajkowski
 *      Exp $
 */
public class TransactionRecord extends GenericBean {

  protected int id = -1;
  protected String taxId = null;
  protected String licenseNumber = null;
  protected String npi = null;
  protected String providerId = null;
  protected String nameLast = null;
  protected String nameFirst = null;
  protected String payerId = null;
  protected String type = null;
  protected String transactionId = null;
  protected String transactionDate = null;
  protected String transactionTime = null;
  protected java.sql.Date performedDate = null;
  protected java.sql.Timestamp performed = null;
  protected java.sql.Timestamp entered = null;


  /**
   *  Constructor for the TransactionRecord object
   */

  public TransactionRecord() { }


  /**
   *  Constructor for the TransactionRecord object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public TransactionRecord(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the TransactionRecord object
   *
   *@param  db                Description of Parameter
   *@param  id                unique record ID
   *@exception  SQLException  Description of Exception
   */
  public TransactionRecord(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   *  Constructor for the TransactionRecord object
   *
   *@param  db                Description of Parameter
   *@param  id                unique record ID
   *@exception  SQLException  Description of Exception
   */
  public TransactionRecord(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Gets the transactionDate attribute of the TransactionRecord object
   *
   *@return    The transactionDate value
   */
  public String getTransactionDate() {
    return transactionDate;
  }


  /**
   *  Sets the transactionDate attribute of the TransactionRecord object
   *
   *@param  transactionDate  The new transactionDate value
   */
  public void setTransactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
    this.setPerformedDate(transactionDate);
    this.processPerformed();
  }


  /**
   *  Gets the performedDate attribute of the TransactionRecord object
   *
   *@return    The performedDate value
   */
  public java.sql.Date getPerformedDate() {
    return performedDate;
  }


  /**
   *  Sets the performedDate attribute of the TransactionRecord object
   *
   *@param  performedDate  The new performedDate value
   */
  public void setPerformedDate(java.sql.Date performedDate) {
    this.performedDate = performedDate;
  }


  /**
   *  Sets the performedDate attribute of the TransactionRecord object
   *
   *@param  tmp  The new performedDate value
   */
  public void setPerformedDate(String tmp) {
    this.performedDate = DateUtils.parseDateString(tmp, "yyyy-MM-dd");
  }


  /**
   *  Gets the performedDateString attribute of the TransactionRecord object
   *
   *@return    The performedDateString value
   */
  public String getPerformedDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(performedDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the transactionTime attribute of the TransactionRecord object
   *
   *@return    The transactionTime value
   */
  public String getTransactionTime() {
    return transactionTime;
  }


  /**
   *  Sets the transactionTime attribute of the TransactionRecord object
   *
   *@param  transactionTime  The new transactionTime value
   */
  public void setTransactionTime(String transactionTime) {
    this.transactionTime = transactionTime;
    this.processPerformed();
  }


  /**
   *  Gets the id attribute of the TransactionRecord object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the TransactionRecord object
   *
   *@param  id  The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the id attribute of the TransactionRecord object
   *
   *@param  id  The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   *  Sets the taxId attribute of the TransactionRecord object
   *
   *@param  taxId  The new taxId value
   */
  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }


  /**
   *  Sets the providerTaxId attribute of the TransactionRecord object
   *
   *@param  taxId  The new providerTaxId value
   */
  public void setProviderTaxID(String taxId) {
    this.setTaxId(taxId);
  }


  /**
   *  Gets the taxId attribute of the TransactionRecord object
   *
   *@return    The taxId value
   */
  public String getTaxId() {
    return taxId;
  }


  /**
   *  Gets the licenseNumber attribute of the TransactionRecord object
   *
   *@return    The licenseNumber value
   */
  public String getLicenseNumber() {
    return licenseNumber;
  }


  /**
   *  Sets the licenseNumber attribute of the TransactionRecord object
   *
   *@param  licenseNumber  The new licenseNumber value
   */
  public void setLicenseNumber(String licenseNumber) {
    this.licenseNumber = licenseNumber;
  }


  /**
   *  Gets the npi attribute of the TransactionRecord object
   *
   *@return    The npi value
   */
  public String getNpi() {
    return npi;
  }


  /**
   *  Sets the npi attribute of the TransactionRecord object
   *
   *@param  npi  The new npi value
   */
  public void setNpi(String npi) {
    this.npi = npi;
  }


  /**
   *  Sets the providerNPI attribute of the TransactionRecord object
   *
   *@param  npi  The new providerNPI value
   */
  public void setProviderNPI(String npi) {
    this.setNpi(npi);
  }


  /**
   *  Gets the providerId attribute of the TransactionRecord object
   *
   *@return    The providerId value
   */
  public String getProviderId() {
    return providerId;
  }


  /**
   *  Sets the providerId attribute of the TransactionRecord object
   *
   *@param  providerId  The new providerId value
   */
  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }


  /**
   *  Gets the nameLast attribute of the TransactionRecord object
   *
   *@return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Sets the nameLast attribute of the TransactionRecord object
   *
   *@param  nameLast  The new nameLast value
   */
  public void setNameLast(String nameLast) {
    this.nameLast = nameLast;
  }


  /**
   *  Sets the providerLastName attribute of the TransactionRecord object
   *
   *@param  nameLast  The new providerLastName value
   */
  public void setProviderLastName(String nameLast) {
    this.setNameLast(nameLast);
  }


  /**
   *  Gets the nameFirst attribute of the TransactionRecord object
   *
   *@return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Sets the nameFirst attribute of the TransactionRecord object
   *
   *@param  nameFirst  The new nameFirst value
   */
  public void setNameFirst(String nameFirst) {
    this.nameFirst = nameFirst;
  }


  /**
   *  Sets the providerFirstName attribute of the TransactionRecord object
   *
   *@param  nameFirst  The new providerFirstName value
   */
  public void setProviderFirstName(String nameFirst) {
    this.setNameFirst(nameFirst);
  }


  /**
   *  Gets the payerId attribute of the TransactionRecord object
   *
   *@return    The payerId value
   */
  public String getPayerId() {
    return payerId;
  }


  /**
   *  Sets the payerId attribute of the TransactionRecord object
   *
   *@param  payerId  The new payerId value
   */
  public void setPayerId(String payerId) {
    this.payerId = payerId;
  }


  /**
   *  Gets the type attribute of the TransactionRecord object
   *
   *@return    The type value
   */
  public String getType() {
    return type;
  }


  /**
   *  Sets the type attribute of the TransactionRecord object
   *
   *@param  type  The new type value
   */
  public void setType(String type) {
    if (type != null) {
      this.type = type.toUpperCase();
    } else {
      this.type = null;
    }
  }


  /**
   *  Sets the transactionType attribute of the TransactionRecord object
   *
   *@param  type  The new transactionType value
   */
  public void setTransactionType(String type) {
    this.setType(type);
  }


  /**
   *  Gets the transactionId attribute of the TransactionRecord object
   *
   *@return    The transactionId value
   */
  public String getTransactionId() {
    return transactionId;
  }


  /**
   *  Sets the transactionId attribute of the TransactionRecord object
   *
   *@param  transactionId  The new transactionId value
   */
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }


  /**
   *  Sets the transactionID attribute of the TransactionRecord object
   *
   *@param  transactionId  The new transactionID value
   */
  public void setTransactionID(String transactionId) {
    this.setTransactionId(transactionId);
  }


  /**
   *  Sets the entered attribute of the User object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the performed attribute of the TransactionRecord object
   *
   *@param  tmp  The new performed value
   */
  public void setPerformed(java.sql.Timestamp tmp) {
    this.performed = tmp;
  }


  /**
   *  Sets the entered attribute of the User object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the performed attribute of the TransactionRecord object
   *
   *@param  tmp  The new performed value
   */
  public void setPerformed(String tmp) {
    this.performed = DateUtils.parseTimestampString(tmp, "yyyy-MM-dd hh:mm:ss a");
  }


  /**
   *  Sets the performed attribute of the TransactionRecord object
   */
  private void processPerformed() {
    if (transactionDate != null && transactionTime != null) {
      this.setPerformed(transactionDate + " " + transactionTime);
    }
  }


  /**
   *  Gets the entered attribute of the User object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the performed attribute of the TransactionRecord object
   *
   *@return    The performed value
   */
  public java.sql.Timestamp getPerformed() {
    return performed;
  }


  /**
   *  Gets the enteredString attribute of the User object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the performedString attribute of the TransactionRecord object
   *
   *@return    The performedString value
   */
  public String getPerformedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(performed);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Insert a transaction record into the database
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO billing_transaction " +
        "(tax_id, license_no, npi, provider_id, namelast, " +
        "namefirst, payer_id, type, ");
    if (performedDate != null) {
      sql.append("date_performed, ");
    }
    if (performed != null) {
      sql.append("performed, ");
    }
    if (entered != null) {
      sql.append("entered, ");
    }
    sql.append("trans_id ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ");
    if (performedDate != null) {
      sql.append("?, ");
    }
    if (performed != null) {
      sql.append("?, ");
    }
    if (entered != null) {
      sql.append("?, ");
    }
    sql.append("?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setString(++i, getTaxId());
    pst.setString(++i, getLicenseNumber());
    pst.setString(++i, getNpi());
    pst.setString(++i, getProviderId());
    pst.setString(++i, getNameLast());
    pst.setString(++i, getNameFirst());
    pst.setString(++i, getPayerId());
    pst.setString(++i, getType());
    if (performedDate != null) {
      pst.setDate(++i, performedDate);
    }
    if (performed != null) {
      pst.setTimestamp(++i, performed);
    }
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    pst.setString(++i, getTransactionId());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "billing_transaction_id_seq");
    return true;
  }


  /**
   *  Check if the record to be inserted is valid, then insert, otherwise report
   *  errors.
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db, ActionContext context) throws SQLException {
    return this.insert(db);
  }


  /**
   *  Deletes the specified TransactionRecord
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("TransactionRecord ID not specified.");
    }
    int resultCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM billing_transaction " +
        "WHERE id = ? ");
    pst.setInt(1, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    if (resultCount == 0) {
      errors.put("actionError", "Transaction Record (ID: " + getId() + ") could not be deleted.");
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Query record information from the database
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void queryRecord(Connection db, int id) throws SQLException {
    if (id < 0) {
      throw new SQLException("TransactionRecord ID not specified.");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT bt.* " +
        "FROM billing_transaction bt " +
    //"LEFT JOIN lookup_transaction_types t ON (bt.type = t.code) " +
        "WHERE bt.id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Transaction Record not found.");
    }
    rs.close();
    pst.close();
  }


  /**
   *  Populates the current TransactionRecord from a ResultSet
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    taxId = rs.getString("tax_id");
    licenseNumber = rs.getString("license_no");
    npi = rs.getString("npi");
    providerId = rs.getString("provider_id");
    nameLast = rs.getString("nameLast");
    nameFirst = rs.getString("nameFirst");
    payerId = rs.getString("payer_id");
    type = rs.getString("type");
    transactionId = rs.getString("trans_id");
    performedDate = rs.getDate("date_performed");
    performed = rs.getTimestamp("performed");
    entered = rs.getTimestamp("entered");
  }

}

