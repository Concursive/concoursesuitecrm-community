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

import java.sql.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.apps.transfer.*;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.CFSDatabaseReaderImportModule;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMapList;
import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.modules.troubletickets.base.TicketLogList;
import com.zeroio.iteam.base.*;
import java.util.*;

/**
 *  Processes Tickets
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id: ImportTickets.java,v 1.18 2003/06/30 17:48:12 mrajkowski Exp
 *      $
 */
public class ImportTickets implements CFSDatabaseReaderImportModule {

  DataWriter writer = null;
  PropertyMapList mappings = null;


  /**
   *  Description of the Method
   *
   *@param  writer            Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  mappings          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer;
    this.mappings = mappings;
    boolean processOK = true;

    TicketCategoryList categoryList = new TicketCategoryList();
    categoryList.setEnabledState(-1);
    categoryList.buildList(db);
    Iterator categories = categoryList.iterator();
    while (categories.hasNext()) {
      DataRecord thisRecord = mappings.createDataRecord(categories.next(), "insert");
      if ("0".equals(thisRecord.getValue("parentCode"))) {
        //Remove the lookup attribute by overwriting the previous field
        thisRecord.removeField("parentCode");
        thisRecord.addField("parentCode", "0", null, null);
      }
      writer.save(thisRecord);
    }
    //processOK = mappings.saveList(writer, categoryList, "insert");
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting Tickets");
    writer.setAutoCommit(false);
    TicketList ticList = new TicketList();
    ticList.buildList(db);
    mappings.saveList(writer, ticList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting Ticket Log");
    TicketLogList ticketLogList = new TicketLogList();
    ticketLogList.setDoSystemMessages(false);
    ticketLogList.buildList(db);
    mappings.saveList(writer, ticketLogList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    writer.setAutoCommit(true);
    return true;
  }

}

