package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import java.sql.*;
import org.aspcfs.apps.transfer.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.CFSDatabaseReaderImportModule;
import org.aspcfs.modules.contacts.base.CallList;
import java.util.*;

/**
 *  Processes Opportunities
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id$
 */
public class ImportCalls implements CFSDatabaseReaderImportModule {

  DataWriter writer = null;
  PropertyMapList mappings = null;


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
    this.mappings = mappings;
    boolean processOK = true;

    logger.info("ImportCalls-> Inserting Calls");
    CallList callList = new CallList();
    callList.buildList(db);

    writer.setAutoCommit(false);
    mappings.saveList(writer, callList, "insert");

    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    return true;
  }

}

