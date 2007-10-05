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
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.quotes.base.Quote;
import org.aspcfs.modules.quotes.base.QuoteProduct;
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Iterator;

/**
 * An Order comprises of products and product options.
 *
 * @author ananth
 * @version $Id$
 * @created March 18, 2004
 */
public class Order extends GenericBean {
  private int id = -1;
  private int parentId = -1;
  private int orgId = -1;
  private int quoteId = -1;
  private int salesId = -1;
  private int orderedBy = -1;
  private int billingContactId = -1;
  private int sourceId = -1;
  private double grandTotal = 0;
  private Timestamp submitted = null;
  private int statusId = -1;
  private Timestamp statusDate = null;
  private Timestamp contractDate = null;
  private Timestamp expirationDate = null;
  private Timestamp approxShipDate = null;
  private Timestamp approxDeliveryDate = null;
  private int orderTermsId = -1;
  private int orderTypeId = -1;
  private String description = null;
  private String notes = null;
  // record status
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;

  // Organization & billing contact info
  private String name = null;
  private String nameLast = null;
  private String nameFirst = null;
  private String nameMiddle = null;

  // Resources
  private boolean buildProducts = false;
  private OrderProductList productList = new OrderProductList();
  private boolean buildAddressList = false;
  private OrderAddressList addressList = new OrderAddressList();


  /**
   * Sets the submitted attribute of the Order object
   *
   * @param tmp The new submitted value
   */
  public void setSubmitted(Timestamp tmp) {
    this.submitted = tmp;
  }


  /**
   * Sets the submitted attribute of the Order object
   *
   * @param tmp The new submitted value
   */
  public void setSubmitted(String tmp) {
    this.submitted = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the addressList attribute of the Order object
   *
   * @param tmp The new addressList value
   */
  public void setAddressList(OrderAddressList tmp) {
    this.addressList = tmp;
  }


  /**
   * Sets the buildAddressList attribute of the Order object
   *
   * @param tmp The new buildAddressList value
   */
  public void setBuildAddressList(boolean tmp) {
    this.buildAddressList = tmp;
  }


  /**
   * Sets the buildAddressList attribute of the Order object
   *
   * @param tmp The new buildAddressList value
   */
  public void setBuildAddressList(String tmp) {
    this.buildAddressList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildAddressList attribute of the Order object
   *
   * @return The buildAddressList value
   */
  public boolean getBuildAddressList() {
    return buildAddressList;
  }


  /**
   * Gets the addressList attribute of the Order object
   *
   * @return The addressList value
   */
  public OrderAddressList getAddressList() {
    return addressList;
  }


  /**
   * Gets the submitted attribute of the Order object
   *
   * @return The submitted value
   */
  public Timestamp getSubmitted() {
    return submitted;
  }


  /**
   * Sets the id attribute of the Order object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Order object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the parentId attribute of the Order object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   * Sets the parentId attribute of the Order object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   * Sets the orgId attribute of the Order object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the Order object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the billingContactId attribute of the Order object
   *
   * @param tmp The new billingContactId value
   */
  public void setBillingContactId(int tmp) {
    this.billingContactId = tmp;
  }


  /**
   * Sets the billingContactId attribute of the Order object
   *
   * @param tmp The new billingContactId value
   */
  public void setBillingContactId(String tmp) {
    this.billingContactId = Integer.parseInt(tmp);
  }


  /**
   * Sets the sourceId attribute of the Order object
   *
   * @param tmp The new sourceId value
   */
  public void setSourceId(int tmp) {
    this.sourceId = tmp;
  }


  /**
   * Sets the sourceId attribute of the Order object
   *
   * @param tmp The new sourceId value
   */
  public void setSourceId(String tmp) {
    this.sourceId = Integer.parseInt(tmp);
  }


  /**
   * Sets the quoteId attribute of the Order object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   * Sets the quoteId attribute of the Order object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   * Sets the grandTotal attribute of the Order object
   *
   * @param tmp The new grandTotal value
   */
  public void setGrandTotal(double tmp) {
    this.grandTotal = tmp;
  }


  /**
   * Sets the statusId attribute of the Order object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the Order object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the statusDate attribute of the Order object
   *
   * @param tmp The new statusDate value
   */
  public void setStatusDate(Timestamp tmp) {
    this.statusDate = tmp;
  }


  /**
   * Sets the orderTermsId attribute of the Order object
   *
   * @param tmp The new orderTermsId value
   */
  public void setOrderTermsId(int tmp) {
    this.orderTermsId = tmp;
  }


  /**
   * Sets the orderTermsId attribute of the Order object
   *
   * @param tmp The new orderTermsId value
   */
  public void setOrderTermsId(String tmp) {
    this.orderTermsId = Integer.parseInt(tmp);
  }


  /**
   * Sets the orderTypeId attribute of the Order object
   *
   * @param tmp The new orderTypeId value
   */
  public void setOrderTypeId(int tmp) {
    this.orderTypeId = tmp;
  }


  /**
   * Sets the orderTypeId attribute of the Order object
   *
   * @param tmp The new orderTypeId value
   */
  public void setOrderTypeId(String tmp) {
    this.orderTypeId = Integer.parseInt(tmp);
  }


  /**
   * Sets the expirationDate attribute of the Order object
   *
   * @param tmp The new expirationDate value
   */
  public void setExpirationDate(Timestamp tmp) {
    this.expirationDate = tmp;
  }


  /**
   * Sets the description attribute of the Order object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the notes attribute of the Order object
   *
   * @param tmp The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Order object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Order object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the Order object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the Order object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the Order object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the modified attribute of the Order object
   *
   * @param tmp The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the name attribute of the Order object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the nameLast attribute of the Order object
   *
   * @param tmp The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   * Sets the nameFirst attribute of the Order object
   *
   * @param tmp The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   * Sets the nameMiddle attribute of the Order object
   *
   * @param tmp The new nameMiddle value
   */
  public void setNameMiddle(String tmp) {
    this.nameMiddle = tmp;
  }


  /**
   * Sets the buildProducts attribute of the Order object
   *
   * @param tmp The new buildProducts value
   */
  public void setBuildProducts(boolean tmp) {
    this.buildProducts = tmp;
  }


  /**
   * Sets the productList attribute of the Order object
   *
   * @param tmp The new productList value
   */
  public void setProductList(OrderProductList tmp) {
    this.productList = tmp;
  }


  /**
   * Sets the salesId attribute of the Order object
   *
   * @param tmp The new salesId value
   */
  public void setSalesId(int tmp) {
    this.salesId = tmp;
  }


  /**
   * Sets the salesId attribute of the Order object
   *
   * @param tmp The new salesId value
   */
  public void setSalesId(String tmp) {
    this.salesId = Integer.parseInt(tmp);
  }


  /**
   * Sets the orderedBy attribute of the Order object
   *
   * @param tmp The new orderedBy value
   */
  public void setOrderedBy(int tmp) {
    this.orderedBy = tmp;
  }


  /**
   * Sets the orderedBy attribute of the Order object
   *
   * @param tmp The new orderedBy value
   */
  public void setOrderedBy(String tmp) {
    this.orderedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the contractDate attribute of the Order object
   *
   * @param tmp The new contractDate value
   */
  public void setContractDate(Timestamp tmp) {
    this.contractDate = tmp;
  }


  /**
   * Sets the contractDate attribute of the Order object
   *
   * @param tmp The new contractDate value
   */
  public void setContractDate(String tmp) {
    this.contractDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the requestItems attribute of the Order object
   *
   * @param request The new requestItems value
   */
  public void setRequestItems(HttpServletRequest request) {
    addressList = new OrderAddressList(request);
  }


  /**
   * Gets the contractDate attribute of the Order object
   *
   * @return The contractDate value
   */
  public Timestamp getContractDate() {
    return contractDate;
  }


  /**
   * Gets the salesId attribute of the Order object
   *
   * @return The salesId value
   */
  public int getSalesId() {
    return salesId;
  }


  /**
   * Gets the orderedBy attribute of the Order object
   *
   * @return The orderedBy value
   */
  public int getOrderedBy() {
    return orderedBy;
  }


  /**
   * Gets the id attribute of the Order object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the parentId attribute of the Order object
   *
   * @return The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   * Gets the orgId attribute of the Order object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Gets the billingContactId attribute of the Order object
   *
   * @return The billingContactId value
   */
  public int getBillingContactId() {
    return billingContactId;
  }


  /**
   * Gets the sourceId attribute of the Order object
   *
   * @return The sourceId value
   */
  public int getSourceId() {
    return sourceId;
  }


  /**
   * Gets the quoteId attribute of the Order object
   *
   * @return The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   * Gets the statusId attribute of the Order object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Gets the statusDate attribute of the Order object
   *
   * @return The statusDate value
   */
  public Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   * Gets the orderTermsId attribute of the Order object
   *
   * @return The orderTermsId value
   */
  public int getOrderTermsId() {
    return orderTermsId;
  }


  /**
   * Gets the orderTypeId attribute of the Order object
   *
   * @return The orderTypeId value
   */
  public int getOrderTypeId() {
    return orderTypeId;
  }


  /**
   * Gets the expirationDate attribute of the Order object
   *
   * @return The expirationDate value
   */
  public Timestamp getExpirationDate() {
    return expirationDate;
  }


  /**
   * Gets the description attribute of the Order object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the notes attribute of the Order object
   *
   * @return The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   * Gets the enteredBy attribute of the Order object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modifiedBy attribute of the Order object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the entered attribute of the Order object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the modified attribute of the Order object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the grandTotal attribute of the Order object
   *
   * @return The grandTotal value
   */
  public double getGrandTotal() {
    return grandTotal;
  }


  /**
   * Gets the name attribute of the Order object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the nameLast attribute of the Order object
   *
   * @return The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   * Gets the nameFirst attribute of the Order object
   *
   * @return The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   * Gets the nameMiddle attribute of the Order object
   *
   * @return The nameMiddle value
   */
  public String getNameMiddle() {
    return nameMiddle;
  }


  /**
   * Gets the buildProducts attribute of the Order object
   *
   * @return The buildProducts value
   */
  public boolean getBuildProducts() {
    return buildProducts;
  }


  /**
   * Gets the productList attribute of the Order object
   *
   * @return The productList value
   */
  public OrderProductList getProductList() {
    return productList;
  }


  /**
   * Constructor for the Order object
   */
  public Order() {
  }


  /**
   * Constructor for the Order object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Order(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the Order object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Order(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Order Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT oe.*, " +
        "				 org.name, ct_billing.namelast, ct_billing.namefirst, ct_billing.namemiddle " +
        " FROM order_entry oe " +
        " LEFT JOIN organization org ON (oe.org_id = org.org_id) " +
        " LEFT JOIN lookup_order_status los ON ( oe.status_id = los.code ) " +
        " LEFT JOIN contact ct_billing ON (oe.billing_contact_id = ct_billing.contact_id) " +
        " WHERE oe.order_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Order Entry not found");
    }
    if (buildProducts) {
      this.buildProducts(db);
    }
    if (buildAddressList) {
      this.buildAddressList(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    // order_entry table
    this.setId(rs.getInt("order_id"));
    parentId = DatabaseUtils.getInt(rs, "parent_id");
    orgId = rs.getInt("org_id");
    quoteId = DatabaseUtils.getInt(rs, "quote_id");
    salesId = DatabaseUtils.getInt(rs, "sales_id");
    orderedBy = DatabaseUtils.getInt(rs, "orderedby");
    billingContactId = DatabaseUtils.getInt(rs, "billing_contact_id");
    sourceId = DatabaseUtils.getInt(rs, "source_id");
    grandTotal = DatabaseUtils.getDouble(rs, "grand_total");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    statusDate = rs.getTimestamp("status_date");
    contractDate = rs.getTimestamp("contract_date");
    expirationDate = rs.getTimestamp("expiration_date");
    orderTermsId = DatabaseUtils.getInt(rs, "order_terms_id");
    orderTypeId = DatabaseUtils.getInt(rs, "order_type_id");
    description = rs.getString("description");
    notes = rs.getString("notes");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    approxDeliveryDate = rs.getTimestamp("approx_delivery_date");
    approxShipDate = rs.getTimestamp("approx_ship_date");
    // Organization and billing contact information
    name = rs.getString("name");
    nameFirst = rs.getString("namefirst");
    nameMiddle = rs.getString("namemiddle");
    nameLast = rs.getString("namelast");

    submitted = rs.getTimestamp("submitted");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildProducts(Connection db) throws SQLException {
    productList.setOrderId(this.getId());
    productList.setBuildResources(true);
    productList.buildList(db);
    determineTotal();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildAddressList(Connection db) throws SQLException {
    addressList.setOrderId(this.getId());
    addressList.buildList(db);
  }


  /**
   * Gets the address attribute of the Order object
   *
   * @param thisType Description of the Parameter
   * @return The address value
   */
  public OrderAddress getAddress(String thisType) {
    return (OrderAddress) addressList.getAddress(thisType);
  }


  /**
   * Description of the Method
   */
  public void determineTotal() {
    // determine the total
    grandTotal = 0.0;
    Iterator i = productList.iterator();
    while (i.hasNext()) {
      OrderProduct thisProduct = (OrderProduct) i.next();
      grandTotal += thisProduct.getTotalPrice();
    }
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
    id = DatabaseUtils.getNextSeq(db, "order_entry_order_id_seq");
    sql.append(
        "INSERT INTO order_entry(parent_id, org_id, quote_id, sales_id, orderedby, billing_contact_id, source_id, " +
        "grand_total, status_id, status_date, contract_date, expiration_date, order_terms_id, order_type_id, " +
        "description, notes, ");
    if (id > -1) {
      sql.append("order_id, ");
    }
    if (approxDeliveryDate !=null) {
      sql.append("approx_delivery_date, ");
    }
    if (approxShipDate !=null) {
      sql.append("approx_ship_date, ");
    }
    sql.append("entered, ");
    sql.append("enteredby, ");
    sql.append("modified, ");
    sql.append("modifiedby, submitted ) ");
    sql.append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
    if (id > -1) {
      sql.append("?,");
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
    sql.append("? , ? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    pst.setInt(++i, this.getOrgId());
    DatabaseUtils.setInt(pst, ++i, this.getQuoteId());
    DatabaseUtils.setInt(pst, ++i, this.getSalesId());
    DatabaseUtils.setInt(pst, ++i, this.getOrderedBy());
    DatabaseUtils.setInt(pst, ++i, this.getBillingContactId());
    DatabaseUtils.setInt(pst, ++i, this.getSourceId());
    DatabaseUtils.setDouble(pst, ++i, this.getGrandTotal());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStatusDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getContractDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
    DatabaseUtils.setInt(pst, ++i, this.getOrderTermsId());
    DatabaseUtils.setInt(pst, ++i, this.getOrderTypeId());
    pst.setString(++i, this.getDescription());
    pst.setString(++i, this.getNotes());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (approxDeliveryDate != null) {
      pst.setTimestamp(++i, this.getApproxDeliveryDate());
    }
    if (approxShipDate != null) {
      pst.setTimestamp(++i, this.getApproxShipDate());
    }
    if (entered != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    pst.setInt(++i, this.getEnteredBy());
    if (modified != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    pst.setInt(++i, this.getModifiedBy());
    pst.setTimestamp(++i, this.getSubmitted());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "order_entry_order_id_seq", id);

    //Insert the addresses if there are any
    Iterator iaddress = this.getAddressList().iterator();
    while (iaddress.hasNext()) {
      OrderAddress thisAddress = (OrderAddress) iaddress.next();
      thisAddress.process(
          db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
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
    if (this.getId() == -1) {
      throw new SQLException("Order ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      // delete the order address list
      this.getAddressList().delete(db);
      addressList = null;

      // delete all the order payments associated with this order
      OrderPaymentList paymentList = new OrderPaymentList();
      paymentList.setOrderId(this.getId());
      paymentList.buildList(db);
      paymentList.delete(db);
      paymentList = null;

      // delete all the line items associated with this order
      this.buildProducts(db);
      productList.delete(db);
      productList = null;

      // delete the credit card record associated with this order
      PreparedStatement pst = null;
      pst = db.prepareStatement(
          "DELETE FROM payment_creditcard WHERE order_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      // delete the eft record
      pst = db.prepareStatement("DELETE FROM payment_eft WHERE order_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      // delete the order address record
      pst = db.prepareStatement(
          "DELETE FROM order_address WHERE order_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      // delete the order
      pst = db.prepareStatement("DELETE FROM order_entry WHERE order_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
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
        "UPDATE order_entry " +
        " SET sales_id = ?, " +
        "     orderedby = ?, " +
        "     billing_contact_id = ?, " +
        "     source_id = ?, " +
        "     grand_total = ?, " +
        "     status_id = ?, " +
        "     status_date = ?, " +
        "     contract_date = ?, " +
        "     expiration_date = ?, " +
        "     order_terms_id = ?, " +
        "     order_type_id = ?, " +
        "     description = ?, " +
        "     notes = ?, " +
        "     modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "     modifiedby = ? ");
    sql.append(" WHERE order_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getSalesId());
    DatabaseUtils.setInt(pst, ++i, this.getOrderedBy());
    DatabaseUtils.setInt(pst, ++i, this.getBillingContactId());
    DatabaseUtils.setInt(pst, ++i, this.getSourceId());
    DatabaseUtils.setDouble(pst, ++i, this.getGrandTotal());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStatusDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getContractDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
    DatabaseUtils.setInt(pst, ++i, this.getOrderTermsId());
    DatabaseUtils.setInt(pst, ++i, this.getOrderTypeId());
    pst.setString(++i, this.getDescription());
    pst.setString(++i, this.getNotes());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
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
      throw new SQLException("Order ID not specified");
    }
    String sql = null;
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;

    // Check for this order's existence in a parent role
    try {
      i = 0;
      pst = db.prepareStatement(
          " SELECT count(*) as parentcount " +
          " FROM order_entry " +
          " WHERE parent_id = ?");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int parentCount = rs.getInt("parentcount");
        if (parentCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("numberOfChildrenOfThisOrder");
          thisDependency.setCount(parentCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }

    //Check the products that are associated with this order
    try {
      i = 0;
      pst = db.prepareStatement(
          " SELECT count(*) AS productcount " +
          " FROM order_product op " +
          " WHERE op.order_id = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int productCount = rs.getInt("productcount");
        if (productCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("numberOfProductsAssociatedWithThisOrder");
          thisDependency.setCount(productCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param quoteId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean createOrderFromQuote(Connection db, int quoteId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    boolean result = true;

    //Retrieve the quote from the quoteId
    Quote quote = new Quote(db, quoteId);
    quote.buildProducts(db);
    quote.retrieveTicket(db);

    pst = db.prepareStatement(
        " SELECT code from lookup_order_status " +
        " WHERE description = ? ");
    pst.setString(1, "Pending");
    rs = pst.executeQuery();
    if (rs.next()) {
      this.setStatusId(rs.getInt("code"));
    }
    rs.close();
    pst.close();

    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //Create the order from the quote
      this.setQuoteId(quoteId);
      this.setOrgId(quote.getOrgId());
      this.setBillingContactId(quote.getContactId());
      this.setDescription(quote.getShortDescription());
      this.setNotes(quote.getNotes());
      this.setGrandTotal(quote.getGrandTotal());
      this.setEnteredBy(quote.getModifiedBy());
      this.setModifiedBy(quote.getModifiedBy());
      this.insert(db);

      //Check the quote products and create the appropriate order products
      if (quote.getProductList().size() > 0) {
        Iterator quoteProducts = (Iterator) quote.getProductList().iterator();
        while (quoteProducts.hasNext()) {
          QuoteProduct quoteProduct = (QuoteProduct) quoteProducts.next();
          OrderProduct product = new OrderProduct();
          product.setEnteredBy(quote.getModifiedBy());
          product.setModifiedBy(quote.getModifiedBy());
          product.setOrderId(this.getId());
          product.setStatusId(this.getStatusId());
          product.setStatusDate(this.getEntered());
          if (!product.createProductFromQuoteProduct(db, quoteProduct.getId())) {
            result = false;
            break;
          }
        }
      }
      if (commit) {
        db.commit();
      }
    } catch (Exception e) {
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
   * Gets the approxDeliveryDate attribute of the Order object
   *
   * @return approxDeliveryDate The approxDeliveryDate value
   */
  public Timestamp getApproxDeliveryDate() {
    return this.approxDeliveryDate;
  }


  /**
   * Sets the approxDeliveryDate attribute of the Order object
   *
   * @param approxDeliveryDate The new approxDeliveryDate value
   */
  public void setApproxDeliveryDate(Timestamp approxDeliveryDate) {
    this.approxDeliveryDate = approxDeliveryDate;
  }

  /**
   * Sets the approxDeliveryDate attribute of the Order object
   *
   * @param approxDeliveryDate The new approxDeliveryDate value
   */
  public void setApproxDeliveryDate(String approxDeliveryDate) {
    this.approxDeliveryDate = DatabaseUtils.parseTimestamp(approxDeliveryDate);
  }


  /**
   * Gets the approxShipDate attribute of the Order object
   *
   * @return approxShipDate The approxShipDate value
   */
  public Timestamp getApproxShipDate() {
    return this.approxShipDate;
  }


  /**
   * Sets the approxShipDate attribute of the Order object
   *
   * @param approxShipDate The new approxShipDate value
   */
  public void setApproxShipDate(Timestamp approxShipDate) {
    this.approxShipDate = approxShipDate;
  }
  /**
   * Sets the approxShipDate attribute of the Order object
   *
   * @param approxShipDate The new approxShipDate value
   */
  public void setApproxShipDate(String approxShipDate) {
    this.approxShipDate = DatabaseUtils.parseTimestamp(approxShipDate);
  }
}

