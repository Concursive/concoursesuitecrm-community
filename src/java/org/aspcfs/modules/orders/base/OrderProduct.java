package org.aspcfs.modules.orders.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.products.base.*;

/**
 *  This represents a Product that a customer requested and is associated with
 *  a particular Order
 *
 * @author     ananth
 * @created    March 18, 2004
 * @version    $Id$
 */
public class OrderProduct extends GenericBean {
  private int id = -1;
  private int orderId = -1;
  private int productId = -1;
  private int quantity = 0;
  private int msrpCurrency = -1;
  private double msrpAmount = 0.0;
  private int priceCurrency = -1;
  private double priceAmount = 0.0;
  private int recurringCurrency = -1;
  private double recurringAmount = 0.0;
  private int recurringType = -1;
  private double extendedPrice = 0.0;
  private double totalPrice = 0;
  private int statusId = -1;
  private Timestamp statusDate = null;

  private boolean buildProduct = false;
  protected ProductCatalog product = null;
  // resources
  private boolean buildProductOptions = false;
  private OrderProductOptionList productOptionList = new OrderProductOptionList();
  private boolean buildProductStatus = false;
  private OrderProductStatusList productStatusList = new OrderProductStatusList();


  /**
   *  Sets the buildProduct attribute of the OrderProduct object
   *
   * @param  tmp  The new buildProduct value
   */
  public void setBuildProduct(boolean tmp) {
    this.buildProduct = tmp;
  }


  /**
   *  Sets the buildProduct attribute of the OrderProduct object
   *
   * @param  tmp  The new buildProduct value
   */
  public void setBuildProduct(String tmp) {
    this.buildProduct = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildProduct attribute of the OrderProduct object
   *
   * @return    The buildProduct value
   */
  public boolean getBuildProduct() {
    return buildProduct;
  }


  /**
   *  Sets the product attribute of the OrderProduct object
   *
   * @param  tmp  The new product value
   */
  public void setProduct(ProductCatalog tmp) {
    this.product = tmp;
  }


  /**
   *  Gets the product attribute of the OrderProduct object
   *
   * @return    The product value
   */
  public ProductCatalog getProduct() {
    return product;
  }


  /**
   *  Sets the id attribute of the OrderProduct object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the OrderProduct object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orderId attribute of the OrderProduct object
   *
   * @param  tmp  The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   *  Sets the orderId attribute of the OrderProduct object
   *
   * @param  tmp  The new orderId value
   */
  public void setOrderId(String tmp) {
    this.orderId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productId attribute of the OrderProduct object
   *
   * @param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the OrderProduct object
   *
   * @param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quantity attribute of the OrderProduct object
   *
   * @param  tmp  The new quantity value
   */
  public void setQuantity(int tmp) {
    this.quantity = tmp;
  }


  /**
   *  Sets the quantity attribute of the OrderProduct object
   *
   * @param  tmp  The new quantity value
   */
  public void setQuantity(String tmp) {
    this.quantity = Integer.parseInt(tmp);
  }


  /**
   *  Sets the priceCurrency attribute of the OrderProduct object
   *
   * @param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(int tmp) {
    this.priceCurrency = tmp;
  }


  /**
   *  Sets the priceCurrency attribute of the OrderProduct object
   *
   * @param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(String tmp) {
    this.priceCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the msrpAmount attribute of the OrderProduct object
   *
   * @param  tmp  The new msrpAmount value
   */
  public void setMsrpAmount(double tmp) {
    this.msrpAmount = tmp;
  }


  /**
   *  Sets the priceAmount attribute of the OrderProduct object
   *
   * @param  tmp  The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   *  Sets the recurringAmount attribute of the OrderProduct object
   *
   * @param  tmp  The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   *  Sets the msrpCurrency attribute of the OrderProduct object
   *
   * @param  tmp  The new msrpCurrency value
   */
  public void setMsrpCurrency(int tmp) {
    this.msrpCurrency = tmp;
  }


  /**
   *  Sets the msrpCurrency attribute of the OrderProduct object
   *
   * @param  tmp  The new msrpCurrency value
   */
  public void setMsrpCurrency(String tmp) {
    this.msrpCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the recurringCurrency attribute of the OrderProduct object
   *
   * @param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(int tmp) {
    this.recurringCurrency = tmp;
  }


  /**
   *  Sets the recurringCurrency attribute of the OrderProduct object
   *
   * @param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(String tmp) {
    this.recurringCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the recurringType attribute of the OrderProduct object
   *
   * @param  tmp  The new recurringType value
   */
  public void setRecurringType(int tmp) {
    this.recurringType = tmp;
  }


  /**
   *  Sets the recurringType attribute of the OrderProduct object
   *
   * @param  tmp  The new recurringType value
   */
  public void setRecurringType(String tmp) {
    this.recurringType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the extendedPrice attribute of the OrderProduct object
   *
   * @param  tmp  The new extendedPrice value
   */
  public void setExtendedPrice(double tmp) {
    this.extendedPrice = tmp;
  }


  /**
   *  Sets the totalPrice attribute of the OrderProduct object
   *
   * @param  tmp  The new totalPrice value
   */
  public void setTotalPrice(double tmp) {
    this.totalPrice = tmp;
  }


  /**
   *  Sets the statusId attribute of the OrderProduct object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the OrderProduct object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusDate attribute of the OrderProduct object
   *
   * @param  tmp  The new statusDate value
   */
  public void setStatusDate(Timestamp tmp) {
    this.statusDate = tmp;
  }


  /**
   *  Sets the statusDate attribute of the OrderProduct object
   *
   * @param  tmp  The new statusDate value
   */
  public void setStatusDate(String tmp) {
    this.statusDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the buildProductOptions attribute of the OrderProduct object
   *
   * @param  tmp  The new buildProductOptions value
   */
  public void setBuildProductOptions(boolean tmp) {
    this.buildProductOptions = tmp;
  }


  /**
   *  Sets the buildProductOptions attribute of the OrderProduct object
   *
   * @param  tmp  The new buildProductOptions value
   */
  public void setBuildProductOptions(String tmp) {
    this.buildProductOptions = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the productOptionList attribute of the OrderProduct object
   *
   * @param  tmp  The new productOptionList value
   */
  public void setProductOptionList(OrderProductOptionList tmp) {
    this.productOptionList = tmp;
  }


  /**
   *  Sets the buildProductStatus attribute of the OrderProduct object
   *
   * @param  tmp  The new buildProductStatus value
   */
  public void setBuildProductStatus(boolean tmp) {
    this.buildProductStatus = tmp;
  }


  /**
   *  Sets the buildProductStatus attribute of the OrderProduct object
   *
   * @param  tmp  The new buildProductStatus value
   */
  public void setBuildProductStatus(String tmp) {
    this.buildProductStatus = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the productStatusList attribute of the OrderProduct object
   *
   * @param  tmp  The new productStatusList value
   */
  public void setProductStatusList(OrderProductStatusList tmp) {
    this.productStatusList = tmp;
  }


  /**
   *  Gets the id attribute of the OrderProduct object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the orderId attribute of the OrderProduct object
   *
   * @return    The orderId value
   */
  public int getOrderId() {
    return orderId;
  }


  /**
   *  Gets the productId attribute of the OrderProduct object
   *
   * @return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Gets the quantity attribute of the OrderProduct object
   *
   * @return    The quantity value
   */
  public int getQuantity() {
    return quantity;
  }


  /**
   *  Gets the priceCurrency attribute of the OrderProduct object
   *
   * @return    The priceCurrency value
   */
  public int getPriceCurrency() {
    return priceCurrency;
  }


  /**
   *  Gets the msrpAmount attribute of the OrderProduct object
   *
   * @return    The msrpAmount value
   */
  public double getMsrpAmount() {
    return msrpAmount;
  }


  /**
   *  Gets the priceAmount attribute of the OrderProduct object
   *
   * @return    The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   *  Gets the recurringAmount attribute of the OrderProduct object
   *
   * @return    The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   *  Gets the msrpCurrency attribute of the OrderProduct object
   *
   * @return    The msrpCurrency value
   */
  public int getMsrpCurrency() {
    return msrpCurrency;
  }


  /**
   *  Gets the recurringCurrency attribute of the OrderProduct object
   *
   * @return    The recurringCurrency value
   */
  public int getRecurringCurrency() {
    return recurringCurrency;
  }


  /**
   *  Gets the recurringType attribute of the OrderProduct object
   *
   * @return    The recurringType value
   */
  public int getRecurringType() {
    return recurringType;
  }


  /**
   *  Gets the extendedPrice attribute of the OrderProduct object
   *
   * @return    The extendedPrice value
   */
  public double getExtendedPrice() {
    return extendedPrice;
  }


  /**
   *  Gets the totalPrice attribute of the OrderProduct object
   *
   * @return    The totalPrice value
   */
  public double getTotalPrice() {
    return totalPrice;
  }


  /**
   *  Gets the statusId attribute of the OrderProduct object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the statusDate attribute of the OrderProduct object
   *
   * @return    The statusDate value
   */
  public Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   *  Gets the buildProductOptions attribute of the OrderProduct object
   *
   * @return    The buildProductOptions value
   */
  public boolean getBuildProductOptions() {
    return buildProductOptions;
  }


  /**
   *  Gets the productOptionList attribute of the OrderProduct object
   *
   * @return    The productOptionList value
   */
  public OrderProductOptionList getProductOptionList() {
    return productOptionList;
  }


  /**
   *  Gets the buildProductStatus attribute of the OrderProduct object
   *
   * @return    The buildProductStatus value
   */
  public boolean getBuildProductStatus() {
    return buildProductStatus;
  }


  /**
   *  Gets the productStatusList attribute of the OrderProduct object
   *
   * @return    The productStatusList value
   */
  public OrderProductStatusList getProductStatusList() {
    return productStatusList;
  }



  /**
   *Constructor for the OrderProduct object
   */
  public OrderProduct() { }


  /**
   *Constructor for the OrderProduct object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public OrderProduct(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *Constructor for the OrderProduct object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public OrderProduct(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Order Product Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT op.item_id, op.order_id, op.product_id, op.quantity, op.msrp_currency, op.msrp.amount, " +
        "   op.price_currency, op.price_amount, op.recurring_currency, op.recurring_amount, op.recurring_type, " +
        "   op.extended_price, op.total_price, op.status_id, op.status_date " +
        " FROM order_product op " +
        " WHERE op.item_id = ? "
        );
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Order Product not found");
    }
    if (buildProduct) {
      this.buildProduct(db);
    }
    if (buildProductOptions) {
      this.buildProductOptions(db);
    }
    if (buildProductStatus) {
      this.buildProductStatus(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    // order_product table
    this.setId(rs.getInt("item_id"));
    orderId = rs.getInt("order_id");
    productId = rs.getInt("product_id");
    quantity = rs.getInt("quantity");
    msrpCurrency = DatabaseUtils.getInt(rs, "msrp_currency");
    msrpAmount = DatabaseUtils.getDouble(rs, "msrp_amount");
    priceCurrency = DatabaseUtils.getInt(rs, "price_currency");
    priceAmount = DatabaseUtils.getDouble(rs, "price_amount");
    recurringCurrency = DatabaseUtils.getInt(rs, "recurring_currency");
    recurringAmount = DatabaseUtils.getDouble(rs, "recurring_amount");
    recurringType = DatabaseUtils.getInt(rs, "recurring_type");
    extendedPrice = DatabaseUtils.getDouble(rs, "extended_price");
    totalPrice = DatabaseUtils.getDouble(rs, "total_price");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    statusDate = rs.getTimestamp("status_date");

  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildProductOptions(Connection db) throws SQLException {
    productOptionList.setItemId(this.getId());
    productOptionList.buildList(db);
  }
  
  
  public void determineTotal() {
    // determine the total
    System.out.println("performing option total");
    Iterator i = productOptionList.iterator();
    while (i.hasNext()) {
      OrderProductOption thisOption = (OrderProductOption) i.next();
      totalPrice += thisOption.getPriceAmount();
    }
  }
  
  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildProductStatus(Connection db) throws SQLException {
    productStatusList.setItemId(this.getId());
    productStatusList.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public void buildProduct(Connection db) throws SQLException {
    product = new ProductCatalog(db, this.getProductId());
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
    db.setAutoCommit(false);

    StringBuffer sql = new StringBuffer();
    sql.append(
        " INSERT INTO order_product(order_id, product_id, " +
        "   quantity, msrp_currency, msrp_amount, price_currency, price_amount, recurring_currency, " +
        "   recurring_amount, recurring_type, extended_price, " +
        "   total_price, status_id, status_date)");

    sql.append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getOrderId());
    pst.setInt(++i, this.getProductId());
    pst.setInt(++i, this.getQuantity());
    DatabaseUtils.setInt(pst, ++i, this.getMsrpCurrency());
    pst.setDouble(++i, this.getMsrpAmount());
    DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
    pst.setDouble(++i, this.getPriceAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
    pst.setDouble(++i, this.getRecurringAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
    pst.setDouble(++i, this.getExtendedPrice());
    pst.setDouble(++i, this.getTotalPrice());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStatusDate());

    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "order_product_item_id_seq");

    // insert the order product's options
    productOptionList.insert(db);

    // insert the order product's status
    productStatusList.insert(db);
    db.commit();
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
      throw new SQLException("Item ID not specified");
    }
    try {
      db.setAutoCommit(false);

      // delete all the order product options associated with this order product
      this.buildProductOptions(db);
      productOptionList.delete(db);
      productOptionList = null;

      // delete all the order product status for this order product
      this.buildProductStatus(db);
      productStatusList.delete(db);
      productStatusList = null;

      // delete the order product
      PreparedStatement pst = db.prepareStatement(
          " DELETE FROM order_product WHERE item_id = ?");
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
    sql.append(" UPDATE order_product " +
        " SET quantity = ? , " +
        "     msrp_currency = ?, " +
        "     msrp_amount = ?, " +
        "     price_currency = ?, " +
        "     price_amount = ?, " +
        "     recurring_currency = ?, " +
        "     recurring_amount = ?, " +
        "     recurring_type = ?, " +
        "     extended_price = ?, " +
        "     total_price = ?, " +
        "     status_id = ?, " +
        "     status_date = ? " +
        " WHERE item_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getQuantity());
    DatabaseUtils.setInt(pst, ++i, this.getMsrpCurrency());
    pst.setDouble(++i, this.getMsrpAmount());
    DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
    pst.setDouble(++i, this.getPriceAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
    pst.setDouble(++i, this.getRecurringAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
    pst.setDouble(++i, this.getExtendedPrice());
    pst.setDouble(++i, this.getTotalPrice());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.setTimestamp(++i, this.getStatusDate());
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
      throw new SQLException("Item ID not specified");
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
   *  Gets the valid attribute of the OrderProduct object
   *
   * @param  db                Description of the Parameter
   * @return                   The valid value
   * @exception  SQLException  Description of the Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    return true;
  }
}

