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

/**
 *  This represents a Product's Order Status
 *
 *@author     ananth
 *@created    March 18, 2004
 *@version    $Id: OrderProductStatus.java,v 1.2 2004/05/04 15:52:27 mrajkowski
 *      Exp $
 */
public class OrderProductStatus extends GenericBean {
  private int id = -1;
  private int orderId = -1;
  private int itemId = -1;
  private int statusId = -1;

  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;

  private int productId = -1;


  /**
   *  Sets the id attribute of the OrderProductStatus object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the OrderProductStatus object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orderId attribute of the OrderProductStatus object
   *
   *@param  tmp  The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   *  Sets the orderId attribute of the OrderProductStatus object
   *
   *@param  tmp  The new orderId value
   */
  public void setOrderId(String tmp) {
    this.orderId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the itemId attribute of the OrderProductStatus object
   *
   *@param  tmp  The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   *  Sets the itemId attribute of the OrderProductStatus object
   *
   *@param  tmp  The new itemId value
   */
  public void setItemId(String tmp) {
    this.itemId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the OrderProductStatus object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the OrderProductStatus object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the OrderProductStatus object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the OrderProductStatus object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the OrderProductStatus object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the OrderProductStatus object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the OrderProductStatus object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the OrderProductStatus object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modified attribute of the OrderProductStatus object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the OrderProductStatus object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the productId attribute of the OrderProductStatus object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the OrderProductStatus object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the id attribute of the OrderProductStatus object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the orderId attribute of the OrderProductStatus object
   *
   *@return    The orderId value
   */
  public int getOrderId() {
    return orderId;
  }


  /**
   *  Gets the itemId attribute of the OrderProductStatus object
   *
   *@return    The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   *  Gets the statusId attribute of the OrderProductStatus object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the enteredBy attribute of the OrderProductStatus object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the OrderProductStatus object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the entered attribute of the OrderProductStatus object
   *
   *@return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the OrderProductStatus object
   *
   *@return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the productId attribute of the OrderProductStatus object
   *
   *@return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Constructor for the OrderProductStatus object
   */
  public OrderProductStatus() { }


  /**
   *  Constructor for the OrderProductStatus object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public OrderProductStatus(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the OrderProductStatus object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public OrderProductStatus(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Order Product Status ID Number");
    }
    PreparedStatement pst = db.prepareStatement(
        " SELECT ops.order_product_status_id, ops.order_id, ops.item_id, " +
        "        ops.status_id, ops.entered, ops.enteredby, " +
        "   	   ops.modified, ops.modifiedby, " +
        "        prod.product_id " +
        " FROM order_product_status ops, order_product prod " +
        " WHERE ops.item_id = prod.item_id AND ops.order_product_status_id = ? "
        );
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Order Product Status ID not found");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    // order_product_status table
    this.setId(rs.getInt("order_product_status_id"));
    orderId = rs.getInt("order_id");
    itemId = rs.getInt("item_id");
    statusId = DatabaseUtils.getInt(rs, "status_id");

    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredBy");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedBy");
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
    StringBuffer sql = new StringBuffer();
    sql.append(" INSERT INTO order_product_status(order_id, item_id, status_id, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    sql.append("enteredby, ");
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append(" modifiedby ) ");
    sql.append(" VALUES( ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    sql.append("?, ");
    if (modified != null) {
      sql.append("?, ");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getOrderId());
    pst.setInt(++i, this.getItemId());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
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
    id = DatabaseUtils.getCurrVal(db, "order_product_status_order_product_status_id_seq");
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
      throw new SQLException("Order ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      // delete the product order status
      PreparedStatement pst = db.prepareStatement(
          " DELETE FROM order_product_status WHERE order_product_status_id = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(" UPDATE order_product_status " +
        " SET status_id = ? " +
        "     entered = ?, " +
        "     enteredby = ?, " +
        "     modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "     modifiedby = ?, " +
        " WHERE order_product_status = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.setTimestamp(++i, this.getEntered());
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

}

