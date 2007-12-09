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

import java.sql.Connection;
import java.sql.SQLException;

public class ImportServices implements CFSDatabaseReaderImportModule {
  DataWriter writer = null;
  PropertyMapList mappings = null;

  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer;
    this.mappings = mappings;
    boolean processOK = true;

    /*
    logger.info("ImportServices-> Inserting RecordList records");
    writer.setAutoCommit(false);
    RecordList recordList = new RecordList();
    recordList.buildList(db);
    mappings.saveList(writer, recordList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }
    logger.info("ImportServices-> Inserting SyncTableList records");
    writer.setAutoCommit(false);
    SyncTableList syncTableList = new SyncTableList();
    syncTableList.buildList(db);
    mappings.saveList(writer, syncTableList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }
    logger.info("ImportServices-> Inserting TransactionStatusList records");
    writer.setAutoCommit(false);
    TransactionStatusList transactionStatusList = new TransactionStatusList();
    transactionStatusList.buildList(db);
    mappings.saveList(writer, transactionStatusList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }
    */
    return true;
  }

}
