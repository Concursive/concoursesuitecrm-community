package org.aspcfs.modules.products.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;

/**
 *  Description of the Class
 *
 * @author     ananth
 * @created    April 20, 2004
 * @version    $Id$
 */
public class CustomerProductHistory extends GenericBean {

  private int id = -1;
  private int customerProductId = -1;
  private int orgId = -1;
  private int orderId = -1;

  private Timestamp productStartDate = null;
  private Timestamp productEndDate = null;
  // record status
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = true;


  /**
   *  Sets the id attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the customerProductId attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new customerProductId value
   */
  public void setCustomerProductId(int tmp) {
    this.customerProductId = tmp;
  }


  /**
   *  Sets the customerProductId attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new customerProductId value
   */
  public void setCustomerProductId(String tmp) {
    this.customerProductId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orgId attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orderId attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   *  Sets the orderId attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new orderId value
   */
  public void setOrderId(String tmp) {
    this.orderId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productStartDate attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new productStartDate value
   */
  public void setProductStartDate(Timestamp tmp) {
    this.productStartDate = tmp;
  }


  /**
   *  Sets the productStartDate attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new productStartDate value
   */
  public void setProductStartDate(String tmp) {
    this.productStartDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the productEndDate attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new productEndDate value
   */
  public void setProductEndDate(Timestamp tmp) {
    this.productEndDate = tmp;
  }


  /**
   *  Sets the productEndDate attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new productEndDate value
   */
  public void setProductEndDate(String tmp) {
    this.productEndDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the entered attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the CustomerProductHistory object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the CustomerProductHistory object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the customerProductId attribute of the CustomerProductHistory object
   *
   * @return    The customerProductId value
   */
  public int getCustomerProductId() {
    return customerProductId;
  }


  /**
   *  Gets the orgId attribute of the CustomerProductHistory object
   *
   * @return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the orderId attribute of the CustomerProductHistory object
   *
   * @return    The orderId value
   */
  public int getOrderId() {
    return orderId;
  }


  /**
   *  Gets the productStartDate attribute of the CustomerProductHistory object
   *
   * @return    The productStartDate value
   */
  public Timestamp getProductStartDate() {
    return productStartDate;
  }


  /**
   *  Gets the productEndDate attribute of the CustomerProductHistory object
   *
   * @return    The productEndDate value
   */
  public Timestamp getProductEndDate() {
    return productEndDate;
  }


  /**
   *  Gets the entered attribute of the CustomerProductHistory object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the CustomerProductHistory object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the CustomerProductHistory object
   *
   * @return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the CustomerProductHistory object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the enabled attribute of the CustomerProductHistory object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *Constructor for the CustomerProductHistory object
   */
  public CustomerProductHistory() { }


  /**
   *Constructor for the CustomerProductHistory object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public CustomerProductHistory(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *Constructor for the CustomerProductHistory object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public CustomerProductHistory(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Customer Product History Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT cph.history_id, cph.customer_product_id, cph.org_id, cph.order_id, " +
        "        cph.product_start_date, cph.product_end_date, cph.entered, cph.enteredby, " +
        "        cph.modified, cph.modifiedby " +
        " FROM customer_product_history cph " +
        " WHERE cph.history_id = ? "
        );
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
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
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
        "INSERT INTO customer_product_history(customer_product_id, org_id, order_id, " +
        " product_start_date, product_end_date, ");

    if (entered != null) {
      sql.append("entered, ");
    }
    sql.append("enteredby, ");
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("modifiedby) ");
    sql.append("VALUES( ?, ?, ?, ?, ?, ");
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
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "customer_product_history_history_id_seq");
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
      throw new SQLException("Customer Product History ID not specified");
    }
    try {
      db.setAutoCommit(false);

      // delete the customer product history
      PreparedStatement pst = db.prepareStatement(" DELETE FROM customer_product_history WHERE history_id = ? ");
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

    sql.append(" UPDATE customer_product_history " +
        " SET order_id = ?, " +
        "     product_start_date = ?, " +
        "     product_end_date = ?, " +
        "     modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "     modifiedby = ? "
        );
    sql.append("WHERE history_id = ? ");
    sql.append("AND modified = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setTimestamp(++i, this.getProductStartDate());
    pst.setTimestamp(++i, this.getProductEndDate());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    pst.setTimestamp(++i, this.getModified());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Gets the valid attribute of the CustomerProductHistory object
   *
   * @param  db                Description of the Parameter
   * @return                   The valid value
   * @exception  SQLException  Description of the Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    return true;
  }
}

