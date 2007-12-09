/*
 *  Copyright(c) 2006 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.orders.base.*;
import org.aspcfs.modules.products.base.CustomerProductHistoryList;
import org.aspcfs.modules.products.base.CustomerProductList;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Processes orders
 *
 * @author srini
 * @version $Id:  Exp
 *          $
 * @created December 10, 2005
 */
public class ImportOrders implements CFSDatabaseReaderImportModule {
  DataWriter writer = null;
  PropertyMapList mappings = null;

  /**
   * Description of the Method
   *
   * @param writer   Description of the Parameter
   * @param db       Description of the Parameter
   * @param mappings Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer;
    this.mappings = mappings;
    boolean processOK = true;

    logger.info("ImportOrders-> Inserting OrderList records");
    writer.setAutoCommit(false);
    OrderList orderList = new OrderList();
    orderList.buildList(db);
    mappings.saveList(writer, orderList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportOrders-> Inserting OrderProductList records");
    writer.setAutoCommit(false);
    OrderProductList orderProductList = new OrderProductList();
    orderProductList.buildList(db);
    mappings.saveList(writer, orderProductList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportOrders-> Inserting OrderProductStatusList records");
    writer.setAutoCommit(false);
    OrderProductStatusList orderProductStatusList = new OrderProductStatusList();
    orderProductStatusList.buildList(db);
    mappings.saveList(writer, orderProductStatusList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportOrders-> Inserting OrderProductOptionList records");
    writer.setAutoCommit(false);
    OrderProductOptionList orderProductOptionList = new OrderProductOptionList();
    orderProductOptionList.buildList(db);
    mappings.saveList(writer, orderProductOptionList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportOrders-> Inserting OrderProductOptionList records");
    writer.setAutoCommit(false);
    OrderAddressList orderAddressList = new OrderAddressList();
    orderAddressList.buildList(db);
    mappings.saveList(writer, orderAddressList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportOrders-> Inserting CustomerProductList records");
    writer.setAutoCommit(false);
    CustomerProductList customerProductList = new CustomerProductList();
    customerProductList.buildList(db);
    mappings.saveList(writer, customerProductList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportOrders-> Inserting CustomerProductHistoryList records");
    writer.setAutoCommit(false);
    CustomerProductHistoryList customerProductHistoryList = new CustomerProductHistoryList();
    customerProductHistoryList.buildList(db);
    mappings.saveList(writer, customerProductHistoryList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportOrders-> Inserting OrderPaymentList records");
    writer.setAutoCommit(false);
    OrderPaymentList orderPaymentList = new OrderPaymentList();
    orderPaymentList.buildList(db);
    mappings.saveList(writer, orderPaymentList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }


    return true;
  }

}
