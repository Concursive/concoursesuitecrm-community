package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
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

    writer.setAutoCommit(true);
    
    logger.info("ImportCommunications-> Inserting Field Types");
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "fieldTypes");
    if (!processOK) {
      return false;
    }
    
    logger.info("ImportCommunications-> Inserting Search Field Elements");
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "searchFieldElement");
    if (!processOK) {
      return false;
    }
    
    logger.info("ImportCommunications-> Inserting Saved Criteria Elements");
    if (1 == 1) {
      CustomLookupList lookupList = new CustomLookupList();
      lookupList.setTableName("saved_criteriaelement");
      lookupList.addField("id");
      lookupList.addField("field");
      lookupList.addField("operator");
      lookupList.addField("operatorid");
      lookupList.addField("value");
      lookupList.buildList(db);
      Iterator queryRecords = lookupList.iterator();
      while (queryRecords.hasNext() && processOK) {
        CustomLookupElement thisElement = (CustomLookupElement) queryRecords.next();
        DataRecord thisRecord = new DataRecord();
        thisRecord.setName("savedCriteriaElement");
        thisRecord.setAction("insert");
        thisRecord.addField("savedCriteriaListId", thisElement.getValue("id"), "searchCriteriaElements", null);
        thisRecord.addField("fieldId", thisElement.getValue("field"), "searchFieldElement", null);
        thisRecord.addField("operator", thisElement.getValue("operator"));
        thisRecord.addField("operatorId", thisElement.getValue("operatorid"), "fieldTypes", null);
        switch (Integer.parseInt(thisElement.getValue("field"))) {
          case (8): thisRecord.addField("value", thisElement.getValue("value"), "lookupContactTypes", null); break;
          case (9): thisRecord.addField("value", thisElement.getValue("value"), "contact", null); break;
          case (10): thisRecord.addField("value", thisElement.getValue("value"), "lookupAccountTypes", null); break;
          default: thisRecord.addField("value", thisElement.getValue("value")); break;
        }
        processOK = writer.save(thisRecord);
      }
      if (!processOK) {
        return false;
      }
    }
//    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "savedCriteriaElement");
//    if (!processOK) {
//      return false;
//    }
    
    logger.info("ImportCommunications-> Inserting Campaign Records");
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "campaign");
    if (!processOK) {
      return false;
    }
    
    logger.info("ImportCommunications-> Inserting Campaign Run");
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "campaignRun");
    if (!processOK) {
      return false;
    }
    
    logger.info("ImportCommunications-> Inserting Scheduled Recipient Records");
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "scheduledRecipient");
    if (!processOK) {
      return false;
    }
    
    logger.info("ImportCommunications-> Inserting Excluded Recipients");
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "excludedRecipient");
    if (!processOK) {
      return false;
    }
    
    
    logger.info("ImportCommunications-> Inserting Campaign List Groups");
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "campaignListGroups");
    if (!processOK) {
      return false;
    }
    
    /**
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }
    */
    
    /**
    logger.info("ImportCommunications-> Inserting Campaign Records");
    CampaignList campaigns = new CampaignList();
    campaigns.buildList(db);
    mappings.saveList(writer, campaigns, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }
    */

    //update owners
    
    return true;
  }
  
}

