package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import java.sql.*;
import org.aspcfs.apps.transfer.*;
import java.util.logging.*;

/**
 *  Interface for all modules that will be executed by the CFSDatabaseReader
 *
 *@author     matt rajkowski
 *@created    September 4, 2002
 *@version    $Id$
 */
public interface CFSDatabaseReaderImportModule {
  public static Logger logger = Logger.getLogger(Transfer.class.getName());


  /**
   *  Description of the Method
   *
   *@param  writer            Description of the Parameter
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException;
}

