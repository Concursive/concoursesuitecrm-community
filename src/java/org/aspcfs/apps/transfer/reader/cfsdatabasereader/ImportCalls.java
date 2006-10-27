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
package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.contacts.base.CallList;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Processes Opportunities
 *
 * @author mrajkowski
 * @version $Id$
 * @created January 14, 2003
 */
public class ImportCalls implements CFSDatabaseReaderImportModule {

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

    writer.setAutoCommit(true);
    logger.info("ImportBaseData-> Inserting lookup_call_priority");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupCallPriority");
    if (!processOK) {
      return false;
    }

    writer.setAutoCommit(true);
    logger.info("ImportBaseData-> Inserting lookup_call_reminder");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupCallReminder");
    if (!processOK) {
      return false;
    }

    writer.setAutoCommit(true);
    logger.info("ImportBaseData-> Inserting lookup_call_result");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupCallResult");
    if (!processOK) {
      return false;
    }

    logger.info("ImportCalls-> Inserting Calls");
    CallList callList = new CallList();
    callList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, callList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    return true;
  }

}

