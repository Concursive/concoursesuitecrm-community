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
import org.aspcfs.modules.quotes.base.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created August 24, 2006
 */
public class ImportQuotes implements CFSDatabaseReaderImportModule {
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

    logger.info("ImportQuotes-> Inserting Quote Groups");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "quoteGroup");
    if (!processOK) {
      return false;
    }

    logger.info("ImportQuotes-> Inserting Quote entry records");
    writer.setAutoCommit(false);
    QuoteList quoteList = new QuoteList();
    quoteList.buildList(db);
    mappings.saveList(writer, quoteList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportQuotes-> Inserting Quote Product records");
    writer.setAutoCommit(false);
    QuoteProductList quoteProductList = new QuoteProductList();
    quoteProductList.buildList(db);
    mappings.saveList(writer, quoteProductList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportQuotes-> Inserting QuoteProductOptionList records");
    writer.setAutoCommit(false);
    QuoteProductOptionList quoteProductOptionList = new QuoteProductOptionList();
    quoteProductOptionList.buildList(db);
    mappings.saveList(writer, quoteProductOptionList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportQuotes-> Inserting Quote Product Option Value Types");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "quoteProductOptionBoolean");
    if (!processOK) {
      return false;
    }

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "quoteProductOptionFloat");
    if (!processOK) {
      return false;
    }

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "quoteProductOptionTimestamp");
    if (!processOK) {
      return false;
    }

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "quoteProductOptionInteger");
    if (!processOK) {
      return false;
    }

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "quoteProductOptionText");
    if (!processOK) {
      return false;
    }

    logger.info("ImportQuotes-> Inserting QuoteConditionList records");
    writer.setAutoCommit(false);
    QuoteConditionList quoteConditionList = new QuoteConditionList();
    quoteConditionList.buildList(db);
    mappings.saveList(writer, quoteConditionList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportQuotes-> Inserting QuoteLogList records");
    writer.setAutoCommit(false);
    QuoteLogList quoteLogList = new QuoteLogList();
    quoteLogList.buildList(db);
    mappings.saveList(writer, quoteLogList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportQuotes-> Inserting QuoteRemarkList records");
    writer.setAutoCommit(false);
    QuoteRemarkList quoteRemarkList = new QuoteRemarkList();
    quoteRemarkList.buildList(db);
    mappings.saveList(writer, quoteRemarkList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportQuotes-> Inserting QuoteNoteList records");
    writer.setAutoCommit(false);
    QuoteNoteList quoteNoteList = new QuoteNoteList();
    quoteNoteList.buildList(db);
    mappings.saveList(writer, quoteNoteList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    return true;
  }

}

