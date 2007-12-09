/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
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
import org.aspcfs.modules.actionlist.base.ActionContactsList;
import org.aspcfs.modules.actionlist.base.ActionItemLogList;
import org.aspcfs.modules.actionlist.base.ActionLists;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Processes Users, Accounts, and Contacts based on the user hierarchy that
 * created each item.
 *
 * @author srini
 * @version $Id:
 *          $
 * @created Novemeber 30, 2005
 */

public class ImportActionLists implements CFSDatabaseReaderImportModule {

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

    logger.info("ImportActionLists-> Inserting action lists");
    writer.setAutoCommit(false);
    ActionLists actionLists = new ActionLists();
    actionLists.buildList(db);
    mappings.saveList(writer, actionLists, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionLists-> Inserting action items");
    writer.setAutoCommit(false);
    ActionContactsList actionContactsLists = new ActionContactsList();
    actionContactsLists.buildList(db);
    mappings.saveList(writer, actionContactsLists, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    //import action item log
    logger.info("ImportActionLists-> Inserting action item log");
    writer.setAutoCommit(false);
    ActionItemLogList actionItemLogList = new ActionItemLogList();
    actionItemLogList.buildList(db);
    mappings.saveList(writer, actionItemLogList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    return true;
  }

}
