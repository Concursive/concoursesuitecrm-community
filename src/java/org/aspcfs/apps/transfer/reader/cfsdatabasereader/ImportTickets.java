package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.zeroio.iteam.base.*;
import java.util.*;

/**
 *  Processes Tickets
 */
public class ImportTickets implements CFSDatabaseReaderImportModule {
  
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
    ticList.setSendNotification(false);
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

