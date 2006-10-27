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

import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.troubletickets.base.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Processes Tickets
 *
 * @author mrajkowski
 * @version $Id: ImportTickets.java,v 1.18 2003/06/30 17:48:12 mrajkowski Exp
 *          $
 * @created January 14, 2003
 */
public class ImportTickets implements CFSDatabaseReaderImportModule {

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

    logger.info("ImportTickets-> Inserting Ticket Lookups");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "ticketSeverity");
    if (!processOK) {
      return false;
    }

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "ticketPriority");
    if (!processOK) {
      return false;
    }

    TicketCategoryList categoryList = new TicketCategoryList();
    categoryList.setEnabledState(-1);
    categoryList.buildList(db);
    Iterator categories = categoryList.iterator();
    while (categories.hasNext()) {
      DataRecord thisRecord = mappings.createDataRecord(
          categories.next(), "insert");
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

    logger.info("ImportTickets-> Inserting Tickets category drafts");
    writer.setAutoCommit(false);
    TicketCategoryDraftList ticketCategoryDraftList = new TicketCategoryDraftList();
    ticketCategoryDraftList.buildList(db, "ticket_category");
    mappings.saveList(writer, ticketCategoryDraftList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting Tickets category draft assignments");
    writer.setAutoCommit(false);
    TicketCategoryDraftAssignmentList ticketCategoryDraftAssgList = new TicketCategoryDraftAssignmentList();
    ticketCategoryDraftAssgList.buildList(db);
    mappings.saveList(writer, ticketCategoryDraftAssgList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting Tickets category draft assignments");
    writer.setAutoCommit(false);
    TicketCategoryAssignmentList ticketCategoryAssignmentList = new TicketCategoryAssignmentList();
    ticketCategoryAssignmentList.buildList(db);
    mappings.saveList(writer, ticketCategoryAssignmentList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting Tickets");
    writer.setAutoCommit(false);
    TicketList ticList = new TicketList();
    ticList.buildList(db);
    this.saveTicketList(db, ticList);
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    /*
    * When a ticket is added, the count is automatically determined and added. So no
    * need to process here
    
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "projectTicketCount");
    if (!processOK) {
      return false;
    }*/

    logger.info("ImportTickets-> Inserting Ticket Log");
    TicketLogList ticketLogList = new TicketLogList();
    ticketLogList.setDoSystemMessages(false);
    ticketLogList.buildList(db);
    mappings.saveList(writer, ticketLogList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting Ticket activity log");
    TicketActivityLogList ticketActivityLogList = new TicketActivityLogList();
    ticketActivityLogList.buildList(db);
    mappings.saveList(writer, ticketActivityLogList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting Ticket activity item");
    TicketPerDayDescriptionList ticketPerDayDescriptionList = new TicketPerDayDescriptionList();
    ticketPerDayDescriptionList.buildList(db);
    mappings.saveList(writer, ticketPerDayDescriptionList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting Ticket maintenance list");
    TicketMaintenanceNoteList ticketMaintenanceNoteList = new TicketMaintenanceNoteList();
    ticketMaintenanceNoteList.buildList(db);
    mappings.saveList(writer, ticketMaintenanceNoteList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting Ticket replacement part list");
    TicketReplacementPartList ticketReplacementPartList = new TicketReplacementPartList();
    ticketReplacementPartList.queryList(db, -1);
    mappings.saveList(writer, ticketReplacementPartList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "ticketLinkProject");
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting Ticket defect list");
    TicketDefectList ticketDefectList = new TicketDefectList();
    ticketDefectList.buildList(db);
    mappings.saveList(writer, ticketDefectList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    writer.setAutoCommit(true);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param ticList Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void saveTicketList(Connection db, TicketList ticList) throws SQLException {
    Iterator tickets = ticList.iterator();
    while (tickets.hasNext()) {
      Ticket ticket = (Ticket) tickets.next();

      DataRecord thisRecord = mappings.createDataRecord(ticket, "insert");
      if (ticket.getCatCode() == 0) {
        thisRecord.removeField("catCode");
        thisRecord.addField("catCode", -1);
      }

      if (ticket.getSubCat1() == 0) {
        thisRecord.removeField("subCat1");
        thisRecord.addField("subCat1", -1);
      }

      if (ticket.getSubCat2() == 0) {
        thisRecord.removeField("subCat2");
        thisRecord.addField("subCat2", -1);
      }

      if (ticket.getSubCat3() == 0) {
        thisRecord.removeField("subCat3");
        thisRecord.addField("subCat3", -1);
      }

      writer.save(thisRecord);
    }
  }
}

