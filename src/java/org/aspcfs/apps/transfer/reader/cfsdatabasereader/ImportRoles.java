package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import java.util.*;

public class ImportRoles implements CFSDatabaseReaderImportModule {
  
  DataWriter writer = null;
  PropertyMapList mappings = null;
  
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer; 
    this.mappings = mappings;
    boolean processOK = true;
    writer.setAutoCommit(true);
    
    logger.info("ImportRoles-> Inserting Roles");
    RoleList roles = new RoleList();
    roles.setEnabledState(-1);
    roles.buildList(db);
    processOK = mappings.saveList(writer, roles, "insert");
    if (!processOK) {
      return false;
    }
    
    logger.info("ImportRoles-> Inserting Permission Categories");
    PermissionCategoryList categories = new PermissionCategoryList();
    categories.buildList(db);
    processOK = mappings.saveList(writer, categories, "insert");
    if (!processOK) {
      return false;
    }
    
    logger.info("ImportRoles-> Inserting Permissions");
    PermissionList permissions = new PermissionList();
    permissions.buildList(db);
    processOK = mappings.saveList(writer, permissions, "insert");
    if (!processOK) {
      return false;
    }
    
    logger.info("ImportRoles-> Inserting Role Permissions");
    processOK = ImportLookupTables.saveCustomLookupList(writer, db, mappings, "rolePermission");
    if (!processOK) {
      return false;
    }
    
    //Update the user records now that they all exist
    UserList finalUserList = new UserList();
    finalUserList.buildList(db);
    processOK = mappings.saveList(writer, finalUserList, "update");
    if (!processOK) {
      return false;
    }
    
    return true;
  }
  
}

