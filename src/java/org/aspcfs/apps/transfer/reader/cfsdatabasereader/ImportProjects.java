package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.zeroio.iteam.base.*;
import java.util.*;

public class ImportProjects implements CFSDatabaseReaderImportModule {
  
  DataWriter writer = null;
  PropertyMapList mappings = null;
  
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer; 
    this.mappings = mappings;
    boolean processOK = true;
    
    //Copy Projects
    logger.info("ImportBaseData-> Inserting Projects");
    ProjectList projectList = new ProjectList();
    projectList.setGroupId(-1);
    projectList.buildList(db);
    writer.setAutoCommit(false);
    processOK = mappings.saveList(writer, projectList, "insert");
    if (!processOK) {
      return true;
    }
    
    return true;
  }
  
}

