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
import org.aspcfs.modules.servicecontracts.base.ServiceContractHoursList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractProductList;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Processes service contracts.
 *
 * @author srini
 * @created December 10, 2005
 */
public class ImportServiceContracts implements CFSDatabaseReaderImportModule {
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

    logger.info("ImportServiceContracts-> Inserting Service Contract records");
    writer.setAutoCommit(false);
    ServiceContractList ServiceContractList = new ServiceContractList();
    ServiceContractList.buildList(db);
    mappings.saveList(writer, ServiceContractList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportServiceContracts-> Inserting ServiceContractHoursList records");
    writer.setAutoCommit(false);
    ServiceContractHoursList serviceContractHoursList = new ServiceContractHoursList();
    serviceContractHoursList.buildList(db);
    mappings.saveList(writer, serviceContractHoursList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportServiceContracts-> Inserting ServiceContractProductList records");
    writer.setAutoCommit(false);
    ServiceContractProductList serviceContractProductList = new ServiceContractProductList();
    serviceContractProductList.buildList(db);
    mappings.saveList(writer, serviceContractProductList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    return true;
  }

}
