package org.aspcfs.modules.orders.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;

/**
 *  An Order comprises of products and product options.
 *
 * @author     ananth
 * @created    March 18, 2004
 * @version    $Id$
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
  private int statusId = -1;
  private Timestamp statusDate = null;
  private Timestamp contractDate = null;
  private Timestamp expirationDate = null;
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


  /**
   *  Sets the id attribute of the Order object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Order object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentId attribute of the Order object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the Order object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orgId attribute of the Order object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the Order object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the billingContactId attribute of the Order object
   *
   * @param  tmp  The new billingContactId value
   */
  public void setBillingContactId(int tmp) {
    this.billingContactId = tmp;
  }


  /**
   *  Sets the billingContactId attribute of the Order object
   *
   * @param  tmp  The new billingContactId value
   */
  public void setBillingContactId(String tmp) {
    this.billingContactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the sourceId attribute of the Order object
   *
   * @param  tmp  The new sourceId value
   */
  public void setSourceId(int tmp) {
    this.sourceId = tmp;
  }


  /**
   *  Sets the sourceId attribute of the Order object
   *
   * @param  tmp  The new sourceId value
   */
  public void setSourceId(String tmp) {
    this.sourceId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quoteId attribute of the Order object
   *
   * @param  tmp  The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   *  Sets the quoteId attribute of the Order object
   *
   * @param  tmp  The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the grandTotal attribute of the Order object
   *
   * @param  tmp  The new grandTotal value
   */
  public void setGrandTotal(double tmp) {
    this.grandTotal = tmp;
  }


  /**
   *  Sets the statusId attribute of the Order object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the Order object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusDate attribute of the Order object
   *
   * @param  tmp  The new statusDate value
   */
  public void setStatusDate(Timestamp tmp) {
    this.statusDate = tmp;
  }


  /**
   *  Sets the orderTermsId attribute of the Order object
   *
   * @param  tmp  The new orderTermsId value
   */
  public void setOrderTermsId(int tmp) {
    this.orderTermsId = tmp;
  }


  /**
   *  Sets the orderTermsId attribute of the Order object
   *
   * @param  tmp  The new orderTermsId value
   */
  public void setOrderTermsId(String tmp) {
    this.orderTermsId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orderTypeId attribute of the Order object
   *
   * @param  tmp  The new orderTypeId value
   */
  public void setOrderTypeId(int tmp) {
    this.orderTypeId = tmp;
  }


  /**
   *  Sets the orderTypeId attribute of the Order object
   *
   * @param  tmp  The new orderTypeId value
   */
  public void setOrderTypeId(String tmp) {
    this.orderTypeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the expirationDate attribute of the Order object
   *
   * @param  tmp  The new expirationDate value
   */
  public void setExpirationDate(Timestamp tmp) {
    this.expirationDate = tmp;
  }


  /**
   *  Sets the description attribute of the Order object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the notes attribute of the Order object
   *
   * @param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Order object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Order object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Order object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Order object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the Order object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the Order object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the name attribute of the Order object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the nameLast attribute of the Order object
   *
   * @param  tmp  The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   *  Sets the nameFirst attribute of the Order object
   *
   * @param  tmp  The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Sets the nameMiddle attribute of the Order object
   *
   * @param  tmp  The new nameMiddle value
   */
  public void setNameMiddle(String tmp) {
    this.nameMiddle = tmp;
  }


  /**
   *  Sets the buildProducts attribute of the Order object
   *
   * @param  tmp  The new buildProducts value
   */
  public void setBuildProducts(boolean tmp) {
    this.buildProducts = tmp;
  }


  /**
   *  Sets the productList attribute of the Order object
   *
   * @param  tmp  The new productList value
   */
  public void setProductList(OrderProductList tmp) {
    this.productList = tmp;
  }


  /**
   *  Sets the salesId attribute of the Order object
   *
   * @param  tmp  The new salesId value
   */
  public void setSalesId(int tmp) {
    this.salesId = tmp;
  }


  /**
   *  Sets the salesId attribute of the Order object
   *
   * @param  tmp  The new salesId value
   */
  public void setSalesId(String tmp) {
    this.salesId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orderedBy attribute of the Order object
   *
   * @param  tmp  The new orderedBy value
   */
  public void setOrderedBy(int tmp) {
    this.orderedBy = tmp;
  }


  /**
   *  Sets the orderedBy attribute of the Order object
   *
   * @param  tmp  The new orderedBy value
   */
  public void setOrderedBy(String tmp) {
    this.orderedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contractDate attribute of the Order object
   *
   * @param  tmp  The new contractDate value
   */
  public void setContractDate(Timestamp tmp) {
    this.contractDate = tmp;
  }


  /**
   *  Sets the contractDate attribute of the Order object
   *
   * @param  tmp  The new contractDate value
   */
  public void setContractDate(String tmp) {
    this.contractDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the contractDate attribute of the Order object
   *
   * @return    The contractDate value
   */
  public Timestamp getContractDate() {
    return contractDate;
  }


  /**
   *  Gets the salesId attribute of the Order object
   *
   * @return    The salesId value
   */
  public int getSalesId() {
    return salesId;
  }


  /**
   *  Gets the orderedBy attribute of the Order object
   *
   * @return    The orderedBy value
   */
  public int getOrderedBy() {
    return orderedBy;
  }


  /**
   *  Gets the id attribute of the Order object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the parentId attribute of the Order object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the orgId attribute of the Order object
   *
   * @return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the billingContactId attribute of the Order object
   *
   * @return    The billingContactId value
   */
  public int getBillingContactId() {
    return billingContactId;
  }


  /**
   *  Gets the sourceId attribute of the Order object
   *
   * @return    The sourceId value
   */
  public int getSourceId() {
    return sourceId;
  }


  /**
   *  Gets the quoteId attribute of the Order object
   *
   * @return    The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   *  Gets the statusId attribute of the Order object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the statusDate attribute of the Order object
   *
   * @return    The statusDate value
   */
  public Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   *  Gets the orderTermsId attribute of the Order object
   *
   * @return    The orderTermsId value
   */
  public int getOrderTermsId() {
    return orderTermsId;
  }


  /**
   *  Gets the orderTypeId attribute of the Order object
   *
   * @return    The orderTypeId value
   */
  public int getOrderTypeId() {
    return orderTypeId;
  }


  /**
   *  Gets the expirationDate attribute of the Order object
   *
   * @return    The expirationDate value
   */
  public Timestamp getExpirationDate() {
    return expirationDate;
  }


  /**
   *  Gets the description attribute of the Order object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the notes attribute of the Order object
   *
   * @return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the enteredBy attribute of the Order object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the Order object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the entered attribute of the Order object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the Order object
   *
   * @return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the grandTotal attribute of the Order object
   *
   * @return    The grandTotal value
   */
  public double getGrandTotal() {
    return grandTotal;
  }


  /**
   *  Gets the name attribute of the Order object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the nameLast attribute of the Order object
   *
   * @return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Gets the nameFirst attribute of the Order object
   *
   * @return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Gets the nameMiddle attribute of the Order object
   *
   * @return    The nameMiddle value
   */
  public String getNameMiddle() {
    return nameMiddle;
  }


  /**
   *  Gets the buildProducts attribute of the Order object
   *
   * @return    The buildProducts value
   */
  public boolean getBuildProducts() {
    return buildProducts;
  }


  /**
   *  Gets the productList attribute of the Order object
   *
   * @return    The productList value
   */
  public OrderProductList getProductList() {
    return productList;
  }


  /**
   *Constructor for the Order object
   */
  public Order() { }


  /**
   *Constructor for the Order object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public Order(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *Constructor for the Order object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public Order(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Order Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT oe.order_id, oe.parent_id, oe.org_id, oe.quote_id, oe.sales_id, oe.orderedby, oe.billing_contact_id, oe.source_id, " +
        "        oe.grand_total, oe.status_id, oe.status_date, oe.contract_date, oe.expiration_date, oe.order_terms_id, oe.order_type_id, " +
        "        oe.description, oe.notes, oe.entered, oe.enteredby, oe.modified, oe.modifiedby, " +
        "				 org.name, ct_billing.namelast, ct_billing.namefirst, ct_billing.namemiddle " +
        " FROM order_entry oe " +
        " LEFT JOIN organization org ON (oe.org_id = org.org_id) " +
        " LEFT JOIN contact ct_billing ON (oe.billing_contact_id = ct_billing.contact_id) " +
        " WHERE oe.order_id = ? "
        );
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
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
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

    // Organization and billing contact information
    name = rs.getString("name");
    nameFirst = rs.getString("namefirst");
    nameMiddle = rs.getString("namemiddle");
    nameLast = rs.getString("namelast");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildProducts(Connection db) throws SQLException {
    System.out.println("building product list");
    productList.setOrderId(this.getId());
    productList.setBuildResources(true);
    productList.buildList(db);
    determineTotal();
  }


  /**
   *  Description of the Method
   */
  public void determineTotal() {
    // determine the total
    System.out.println("performing product total");
    Iterator i = productList.iterator();
    while (i.hasNext()) {
      OrderProduct thisProduct = (OrderProduct) i.next();
      grandTotal += thisProduct.getPriceAmount();
      System.out.println("total : " + grandTotal);
    }
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
        "INSERT INTO order_entry(parent_id, org_id, quote_id, sales_id, orderedby, billing_contact_id, source_id, " +
        "grand_total, status_id, status_date, contract_date, expiration_date, order_terms_id, order_type_id, " +
        "description, notes, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    sql.append("enteredby, ");
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("modifiedby) ");
    sql.append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
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
    id = DatabaseUtils.getCurrVal(db, "order_entry_order_id_seq");
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
      // delete all the line items associated with this order
      this.buildProducts(db);
      productList.delete(db);
      productList = null;

      // delete the order
      PreparedStatement pst = db.prepareStatement(" DELETE FROM order_entry WHERE order_id = ? ");
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

    sql.append(" UPDATE order_entry " +
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
        "     entered = ?, " +
        "     enteredby = ?, " +
        "     modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "     modifiedby = ? "
        );
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
    pst.setTimestamp(++i, this.getEntered());
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
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
          " WHERE parent_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int parentCount = rs.getInt("parentcount");
        if (parentCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Number of children of this Order ");
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
          thisDependency.setName("Number of products associated with this order ");
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
   *  Gets the valid attribute of the Order object
   *
   * @param  db                Description of the Parameter
   * @return                   The valid value
   * @exception  SQLException  Description of the Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    return true;
  }
}

