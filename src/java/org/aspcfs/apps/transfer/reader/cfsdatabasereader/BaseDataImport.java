package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.sql.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.cfsbase.*;
import com.zeroio.iteam.base.*;
import java.util.*;

/**
 *  Processes Users, Accounts, and Contacts based on the user hierarchy that
 *  created each item.
 *
 *@author     matt rajkowski
 *@created    September 4, 2002
 *@version    $Id$
 */
public class BaseDataImport implements CFSDatabaseReaderImportModule {
  
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
    logger.info("BaseDataImport-> Processing 1st user");
    
    //Loop through created items until complete, in the following order
    UserList userList = new UserList();
    User baseUser = new User(db, "0");
    userList.add(baseUser);
    writer.setAutoCommit(false);
    this.saveUserList(db, userList);
    writer.commit();

    
    //Save this user's role first
    //Get all accounts user entered
    //Get all contacts user entered
    //Get all users user entered

    //Afterwards... update all owners
    writer.setAutoCommit(true);
    return true;
  }
  
  private void saveUserList(Connection db, UserList userList) {
    Iterator users = userList.iterator();
    while (users.hasNext()) {
      User thisUser = (User)users.next();
      DataRecord thisRecord = mappings.createDataRecord(thisUser, "user");
      thisRecord.removeField("contactId");
      thisRecord.removeField("roleId");
      thisRecord.removeField("managerId");
      thisRecord.removeField("assistant");
      thisRecord.removeField("alias");
      writer.save(thisRecord);
      //TODO:saveUserList(db, 
    }
  }
}

