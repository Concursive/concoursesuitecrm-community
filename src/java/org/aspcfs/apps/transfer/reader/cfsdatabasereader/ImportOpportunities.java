package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

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
    saveOppList(db, oppList);
    //mappings.saveList(writer, oppList, "insert");
    
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }
    
    //update owners
    //writer.setAutoCommit(true);
    return true;
  }
  
  private void saveOppList(Connection db, OpportunityList oppList) throws SQLException {
          Iterator opps = oppList.iterator();
        
            while (opps.hasNext()) {
              Opportunity thisOpp = (Opportunity)opps.next();
              DataRecord thisRecord = mappings.createDataRecord(thisOpp, "insert");
              writer.save(thisRecord);
              
              logger.info("ImportOpportunities (Calls)-> Inserting Calls for " + thisOpp.getId());
              
              CallList callList = new CallList();
              callList.setOppId(thisOpp.getId());
              callList.buildList(db);
              Iterator calls = callList.iterator();
              
                      while(calls.hasNext()) {
                              Call thisCall = (Call)calls.next();
                              DataRecord anotherRecord = mappings.createDataRecord(thisCall, "insert");
                              writer.save(anotherRecord);
                      }
            }
  }
}

