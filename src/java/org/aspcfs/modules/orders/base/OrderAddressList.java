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

import org.aspcfs.modules.base.AddressList;
import org.aspcfs.modules.base.SyncableList;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id: OrderAddressList.java,v 1.2 2004/05/04 15:52:27 mrajkowski
 *          Exp $
 * @created March 23, 2004
 */
public class OrderAddressList extends AddressList implements SyncableList{
  public final static String tableName = "order_address";
  public final static String uniqueField = "address_id";


  /**
   * Gets the tableName attribute of the OrderAddressList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the OrderAddressList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Constructor for the OrderAddressList object
   */
  public OrderAddressList() {
  }


  /**
   * Constructor for the OrderAddressList object
   *
   * @param request Description of the Parameter
   */
  public OrderAddressList(HttpServletRequest request) {
    int i = 0;
    while (request.getParameter("address" + (++i) + "type") != null) {
      OrderAddress thisAddress = new OrderAddress();
      thisAddress.buildRecord(request, i);
      if (thisAddress.isValid()) {
        this.addElement(thisAddress);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT * " +
        "FROM order_address a " +
        "left join lookup_orderaddress_types l " +
        "on (a.address_type = l.code) WHERE 1=1 ");

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM order_address a, lookup_orderaddress_types l " +
        "WHERE a.address_type = l.code ");

    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(
          sqlCount.toString() +
          sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
            sqlFilter.toString() +
            "AND city < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      if (pagedListInfo.getColumnToSortBy() != null && !pagedListInfo.getColumnToSortBy().equals(
          "")) {
        sqlOrder.append(
            "ORDER BY " + pagedListInfo.getColumnToSortBy() + ", city ");
        if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals(
            "")) {
          sqlOrder.append(pagedListInfo.getSortOrder() + " ");
        }
      } else {
        sqlOrder.append("ORDER BY city ");
      }

      //Determine items per page
      if (pagedListInfo.getItemsPerPage() > 0) {
        sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
      }

      sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
    }

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      OrderAddress thisAddress = new OrderAddress(rs);
      this.addElement(thisAddress);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator addressIterator = this.iterator();
    while (addressIterator.hasNext()) {
      OrderAddress thisAddress = (OrderAddress) addressIterator.next();
      thisAddress.delete(db);
    }
  }
}

