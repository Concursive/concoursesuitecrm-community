package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.zeroio.iteam.base.*;
import java.util.*;

/**
 *  Processes Tickets
 */
public class ImportCommunications implements CFSDatabaseReaderImportModule {
  
  DataWriter writer = null;
  PropertyMapList mappings = null;
  
  /**
   *  Description of the Method
   *
   *@param  writer  Description of the Parameter
   *@param  db      Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer; 
    this.mappings = mappings;
    boolean processOK = true;
    
    logger.info("ImportCommunications-> Inserting Messages");
    writer.setAutoCommit(false);
    MessageList messageList = new MessageList();
    messageList.buildList(db);
    mappings.saveList(writer, messageList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }
    
    logger.info("ImportCommunications-> Inserting Saved Criteria");
    SearchCriteriaListList scl = new SearchCriteriaListList();
    scl.buildList(db);
    mappings.saveList(writer, scl, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }
    
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "savedCriteriaElement");
    if (!processOK) {
      return false;
    }
    
    logger.info("ImportCommunications-> Inserting Campaign Records");
    CampaignList campaigns = new CampaignList();
    campaigns.buildList(db);
    mappings.saveList(writer, campaigns, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    /**
    logger.info("ImportTickets-> Inserting Ticket Log");
    TicketLogList ticketLogList = new TicketLogList();
    ticketLogList.setDoSystemMessages(false);
    ticketLogList.buildList(db);
    logger.info("ImportTickets-> " + ticketLogList.size());
    mappings.saveList(writer, ticketLogList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }    
    */
    
    //update owners
    
    writer.setAutoCommit(true);
    return true;
  }
  
}

