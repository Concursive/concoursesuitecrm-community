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
  
  public static boolean saveCustomLookupList(DataWriter writer, Connection db, PropertyMapList mappings, String mapId) throws SQLException {
    boolean processOK = true;
    PropertyMap thisMap = mappings.getMap(mapId);
    if (thisMap == null) {
      return false;
    }
    //Configure the query
    CustomLookupList lookupList = new CustomLookupList();
    lookupList.setTableName(thisMap.getTable());
    
    //Set the fields to query
    Iterator properties = thisMap.iterator();
    while (properties.hasNext()) {
      Property thisProperty = (Property)properties.next();
      lookupList.addField(thisProperty.getField());
    }
    lookupList.buildList(db);
    if (lookupList.size() > 0) {
      //Insert each record
      Iterator queryRecords = lookupList.iterator();
      while (queryRecords.hasNext() && processOK) {
        CustomLookupElement thisElement = (CustomLookupElement)queryRecords.next();
        DataRecord thisRecord = new DataRecord();
        thisRecord.setName(thisMap.getId());
        thisRecord.setAction("insert");
        thisRecord.addField("tableName", thisMap.getTable());
        if (thisMap.getUniqueField() != null) {
                thisRecord.addField("uniqueField", thisMap.getUniqueField());
        }
        //Each record has a bunch of fields
        properties = thisMap.iterator();
        while (properties.hasNext()) {
          Property thisProperty = (Property)properties.next();
          String fieldName = thisProperty.getField();
          String value = thisElement.getValue(fieldName);
          
          if (thisProperty.hasAlias()) {
            if ("guid".equals(thisProperty.getAlias())) {
              thisRecord.addField("guid", value);
            }
          } else {
            thisRecord.addField("field", fieldName);
            thisRecord.addField("data", value);
          }
        }
        processOK = writer.save(thisRecord);
        
      }
    }
    return processOK;
  }
}

