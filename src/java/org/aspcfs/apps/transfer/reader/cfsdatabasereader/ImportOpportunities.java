package com.darkhorseventures.apps.dataimport.reader.cfsdatabasereader;

import java.sql.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.zeroio.iteam.base.*;
import java.util.*;

/**
 *  Processes Opportunities
 */
public class ImportOpportunities implements CFSDatabaseReaderImportModule {
  
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
    
    logger.info("ImportOpportunities-> Inserting Opps");
    OpportunityList oppList = new OpportunityList();
    oppList.buildList(db);
    
    writer.setAutoCommit(false);
    mappings.saveList(writer, oppList, "insert");
    
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }
    
    return true;
  }
  
}

