package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import java.sql.*;
import java.util.*;
import org.aspcfs.apps.transfer.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.CFSDatabaseReaderImportModule;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMapList;

/**
 *  Reads most of the lookup tables found in CFS -- scans for any PropertyMap
 *  that is defined as com.darkhorseventures.webutils.LookupList
 *
 *@author     matt rajkowski
 *@created    September 4, 2002
 *@version    $Id: ImportLookupTables.java,v 1.13 2002/09/17 19:15:09 mrajkowski
 *      Exp $
 */
public class ImportLookupTables implements CFSDatabaseReaderImportModule {
  Connection db = null;
  DataWriter writer = null;


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
    this.db = db;

    boolean processOK = true;
    Iterator mapList = mappings.keySet().iterator();
    while (mapList.hasNext() && processOK) {
      writer.setAutoCommit(false);
      String mapClass = (String) mapList.next();
      if (mapClass.indexOf(".LookupList") > 0) {
        PropertyMap thisMap = (PropertyMap) mappings.get(mapClass);
        logger.info("ImportLookupTables-> Processing: " + thisMap.getTable());
        processOK = saveLookupList(thisMap.getId(), thisMap.getTable());
      }
      processOK = writer.commit();
      writer.setAutoCommit(true);
    }
    return processOK;
  }


  /**
   *  Description of the Method
   *
   *@param  uniqueId          Description of the Parameter
   *@param  tableName         Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean saveLookupList(String uniqueId, String tableName) throws SQLException {
    LookupList thisList = new LookupList(db, tableName);
    Iterator i = thisList.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
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


  /**
   *  Description of the Method
   *
   *@param  writer            Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  mappings          Description of the Parameter
   *@param  mapId             Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
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
      Property thisProperty = (Property) properties.next();
      lookupList.addField(thisProperty.getField());
    }
    lookupList.buildList(db);
    if (lookupList.size() > 0) {
      //Insert each record
      Iterator queryRecords = lookupList.iterator();
      while (queryRecords.hasNext() && processOK) {
        CustomLookupElement thisElement = (CustomLookupElement) queryRecords.next();
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
          Property thisProperty = (Property) properties.next();
          String fieldName = thisProperty.getField();
          String value = thisElement.getValue(fieldName);

          if (thisProperty.hasAlias() && "guid".equals(thisProperty.getAlias())) {
            thisRecord.addField("guid", value);
          } else if (thisProperty.hasName()) {
            thisRecord.addField(thisProperty.getName(), value, thisProperty.getLookupValue(), thisProperty.getAlias());
          } else {
            thisRecord.addField("field", fieldName);
            thisRecord.addField("data", value, thisProperty.getLookupValue(), thisProperty.getAlias());
          }
        }
        processOK = writer.save(thisRecord);

      }
    }
    return processOK;
  }
}

