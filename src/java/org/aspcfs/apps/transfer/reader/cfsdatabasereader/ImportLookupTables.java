package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import java.util.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

/**
 *  Processes the related lookup data.
 *
 *@author     matt rajkowski
 *@created    September 4, 2002
 *@version    $Id$
 */
public class ImportLookupTables implements CFSDatabaseReaderImportModule {
  Connection db = null;
  DataWriter writer = null;
  
  /**
   *  Description of the Method
   *
   *@param  writer  Description of the Parameter
   *@param  db      Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer;
    this.db = db;
    
    boolean processOK = true;
    Iterator mapList = mappings.keySet().iterator();
    while (mapList.hasNext() && processOK) {
      writer.setAutoCommit(false);
      String mapClass = (String)mapList.next();
      if (mapClass.indexOf(".LookupList") > 0) {
        PropertyMap thisMap = (PropertyMap)mappings.get(mapClass);
        logger.info("ImportLookupTables-> Processing: " + thisMap.getTable());
        processOK = saveLookupList(thisMap.getId(), thisMap.getTable());
      }
      processOK = writer.commit();
      writer.setAutoCommit(true);
    }
    return processOK;
  }
  
  
  public boolean saveLookupList(String uniqueId, String tableName) throws SQLException {
    LookupList thisList = new LookupList(db, tableName);
    Iterator i = thisList.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement)i.next();
      DataRecord thisRecord = new DataRecord();
      thisRecord.setName(uniqueId);
      thisRecord.setAction("insert");
      thisRecord.addField("tableName", tableName);
      thisRecord.addField("guid", String.valueOf(thisElement.getCode()));
      thisRecord.addField("description", thisElement.getDescription());
      thisRecord.addField("defaultItem", String.valueOf(thisElement.getDefaultItem()));
      thisRecord.addField("level", String.valueOf(thisElement.getLevel()));
      if (!writer.save(thisRecord)) {
        return false;
      }
    }
    return true;
  }
}

