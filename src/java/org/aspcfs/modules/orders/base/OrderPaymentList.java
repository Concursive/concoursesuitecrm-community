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

import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  This represents a list of Order Payments
 *
 *@author     ananth
 *@created    March 19, 2004
 *@version    $Id: OrderPaymentList.java,v 1.2 2004/05/04 15:52:27 mrajkowski
 *      Exp $
 */
public class OrderPaymentList extends ArrayList {
  private PagedListInfo pagedListInfo = null;
  private int orderId = -1;
  private int orderItemId = -1;
  private int statusId = -1;
  private int historyId = -1;
  private int paymentMethodId = -1;
  private String status = null;


  /**
   *  Sets the orderId attribute of the OrderPaymentList object
   *
   *@param  tmp  The new orderId value
   */
  public void setOrderId(int tmp) {
    this.orderId = tmp;
  }


  /**
   *  Sets the orderId attribute of the OrderPaymentList object
   *
   *@param  tmp  The new orderId value
   */
  public void setOrderId(String tmp) {
    this.orderId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the paymentMethodId attribute of the OrderPaymentList object
   *
   *@param  tmp  The new paymentMethodId value
   */
  public void setPaymentMethodId(int tmp) {
    this.paymentMethodId = tmp;
  }


  /**
   *  Sets the paymentMethodId attribute of the OrderPaymentList object
   *
   *@param  tmp  The new paymentMethodId value
   */
  public void setPaymentMethodId(String tmp) {
    this.paymentMethodId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pagedListInfo attribute of the OrderPaymentList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the orderItemId attribute of the OrderPaymentList object
   *
   * @param  tmp  The new orderItemId value
   */
  public void setOrderItemId(int tmp) {
    this.orderItemId = tmp;
  }


  /**
   *  Sets the orderItemId attribute of the OrderPaymentList object
   *
   * @param  tmp  The new orderItemId value
   */
  public void setOrderItemId(String tmp) {
    this.orderItemId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the OrderPaymentList object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the OrderPaymentList object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the historyId attribute of the OrderPaymentList object
   *
   * @param  tmp  The new historyId value
   */
  public void setHistoryId(int tmp) {
    this.historyId = tmp;
  }


  /**
   *  Sets the historyId attribute of the OrderPaymentList object
   *
   * @param  tmp  The new historyId value
   */
  public void setHistoryId(String tmp) {
    this.historyId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the status attribute of the OrderPaymentList object
   *
   * @param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   *  Gets the status attribute of the OrderPaymentList object
   *
   * @return    The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   *  Gets the orderItemId attribute of the OrderPaymentList object
   *
   * @return    The orderItemId value
   */
  public int getOrderItemId() {
    return orderItemId;
  }


  /**
   *  Gets the statusId attribute of the OrderPaymentList object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the historyId attribute of the OrderPaymentList object
   *
   * @return    The historyId value
   */
  public int getHistoryId() {
    return historyId;
  }


  /**
   *  Gets the orderId attribute of the OrderPaymentList object
   *
   *@return    The orderId value
   */
  public int getOrderId() {
    return orderId;
  }


  /**
   *  Gets the paymentMethodId attribute of the OrderPaymentList object
   *
   *@return    The paymentMethodId value
   */
  public int getPaymentMethodId() {
    return paymentMethodId;
  }


  /**
   *  Gets the pagedListInfo attribute of the OrderPaymentList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Constructor for the OrderPaymentList object
   */
  public OrderPaymentList() { }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    // Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
        " FROM order_payment op " +
        " LEFT JOIN lookup_payment_status ps " +
        " ON ( op.status_id = ps.code ) " +
        " WHERE op.payment_id > -1 ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      // Get the total number of records matching the filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      // Determine the offset, based on the filter, for the first record to show
      //TODO : figure out why there is the below piece of code in Ticket.java
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
        items = prepareFilter(pst);
        //pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      pagedListInfo.setDefaultSort("op.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY op.entered");
    }

    //Need to build a base SQL statement for returning the records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT");
    }
    sqlSelect.append(
        " op.*, ps.description AS status_description " +
        " FROM order_payment op " +
        " LEFT JOIN lookup_payment_status ps " +
        " ON ( op.status_id = ps.code ) " +
        " WHERE op.payment_id > -1 "
        );

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      OrderPayment thisPayment = new OrderPayment(rs);
      this.add(thisPayment);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (orderId > -1) {
      sqlFilter.append("AND op.order_id = ? ");
    }
    if (orderItemId > -1) {
      sqlFilter.append(" AND op.order_item_id = ? ");
    }
    if (historyId > -1) {
      sqlFilter.append(" AND op.history_id = ? ");
    }
    if (statusId > -1) {
      sqlFilter.append(" AND op.status_id = ? ");
    }
    if (status != null) {
      sqlFilter.append(" AND ps.description = ? ");
    }
    if (paymentMethodId > -1) {
      sqlFilter.append("AND op.payment_method_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (orderId > -1) {
      pst.setInt(++i, orderId);
    }
    if (orderItemId > -1) {
      pst.setInt(++i, this.getOrderItemId());
    }
    if (historyId > -1) {
      pst.setInt(++i, this.getHistoryId());
    }
    if (statusId > -1) {
      pst.setInt(++i, this.getStatusId());
    }
    if (status != null) {
      pst.setString(++i, this.getStatus());
    }
    if (paymentMethodId > -1) {
      pst.setInt(++i, paymentMethodId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator iterator = this.iterator();
    while (iterator.hasNext()) {
      OrderPayment orderPayment = (OrderPayment) iterator.next();
      orderPayment.delete(db);
    }
  }
}

