package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.zeroio.iteam.base.*;
import java.util.*;

public class ImportDocuments implements CFSDatabaseReaderImportModule {
  
  DataWriter writer = null;
  PropertyMapList mappings = null;
  
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer; 
    this.mappings = mappings;
    boolean processOK = true;
    writer.setAutoCommit(true);
    
    logger.info("ImportBaseData-> Inserting FileItemList");
    FileItemList fileItemList = new FileItemList();
    fileItemList.buildList(db);
  
    Iterator fileItems = fileItemList.iterator();
    while (fileItems.hasNext()) {
      FileItem thisItem = (FileItem)fileItems.next();
      DataRecord thisRecord = mappings.createDataRecord(thisItem, "insert");
      thisRecord.addField("doVersionInsert", "false");
      switch (thisItem.getLinkModuleId()) {
        case 1: 
          thisRecord.addField("linkItemId", String.valueOf(thisItem.getLinkItemId()), "account", null);
          break;
        case 3:
          thisRecord.addField("linkItemId", String.valueOf(thisItem.getLinkItemId()), "opportunity", null);
          break;
        default:
          thisRecord.addField("linkItemId", String.valueOf(thisItem.getLinkItemId()), null, null);
          break;
      }
      processOK = writer.save(thisRecord);
    }
    
    logger.info("ImportBaseData-> Inserting FileItemVersionList");
    FileItemVersionList fileItemVersionList = new FileItemVersionList();
    fileItemVersionList.buildList(db);
    mappings.saveList(writer, fileItemVersionList, "insert");
    
    logger.info("ImportBaseData-> Inserting FileDownloadLogList");
    FileDownloadLogList downloadLog = new FileDownloadLogList();
    downloadLog.buildList(db);
    mappings.saveList(writer, downloadLog, "insert");
    
    return true;
  }
  
}

