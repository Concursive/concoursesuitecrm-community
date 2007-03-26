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
import org.aspcfs.utils.web.CustomLookupElement;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Reads most of the lookup tables found in CFS -- scans for any PropertyMap
 * that is defined as com.darkhorseventures.webutils.LookupList
 *
 * @author matt rajkowski
 * @version $Id: ImportLookupTables.java,v 1.13 2002/09/17 19:15:09 mrajkowski
 *          Exp $
 * @created September 4, 2002
 */
public class ImportLookupTables implements CFSDatabaseReaderImportModule {

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
    boolean processOK = true;
    Iterator mapList = mappings.keySet().iterator();
    while (mapList.hasNext() && processOK) {
      writer.setAutoCommit(false);
      String mapClass = (String) mapList.next();
      if (mapClass.indexOf(".LookupList") > 0) {
        PropertyMap thisMap = (PropertyMap) mappings.get(mapClass);
        logger.info("ImportLookupTables-> Processing: " + thisMap.getTable());
        processOK = saveLookupList(writer, db, thisMap.getId(), thisMap.getTable());
      }
      processOK = writer.commit();
      writer.setAutoCommit(true);
    }
    return processOK;
  }


  /**
   * Description of the Method
   *
   * @param uniqueId  Description of the Parameter
   * @param tableName Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean saveLookupList(DataWriter writer, Connection db, String uniqueId, String tableName) throws SQLException {
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
      thisRecord.addField(
          "defaultItem", String.valueOf(thisElement.getDefaultItem()));
      thisRecord.addField("level", String.valueOf(thisElement.getLevel()));
      if (!writer.save(thisRecord)) {
        return false;
      }
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param writer   Description of the Parameter
   * @param db       Description of the Parameter
   * @param mappings Description of the Parameter
   * @param mapId    Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
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
          int type = thisElement.getType(fieldName);

          if (thisProperty.hasAlias() && "guid".equals(
              thisProperty.getAlias())) {
            thisRecord.addField("guid", value);
          } else if (thisProperty.hasName()) {
            thisRecord.addField(
                thisProperty.getName(), value, thisProperty.getLookupValue(), thisProperty.getAlias());
          } else {
            thisRecord.addField("field", fieldName);
            if (thisProperty.hasLookupValue()) {
              thisRecord.addField(
                  "data", (fieldName + "=" + (value != null ? value : "-1")), thisProperty.getLookupValue(), thisProperty.getAlias());
            } else {
              thisRecord.addField(
                  "data", value, thisProperty.getLookupValue(), thisProperty.getAlias());
            }
            thisRecord.addField("type", String.valueOf(type));
          }
        }
        processOK = writer.save(thisRecord);
      }
    }
    return processOK;
  }
}

