package com.darkhorseventures.apps.dataimport.reader.cfsdatabasereader;

import java.sql.*;
import com.darkhorseventures.apps.dataimport.*;
import java.util.logging.*;

/**
 *  Interface for all modules that will be executed by the CFSDatabaseReader
 *
 *@author     matt rajkowski
 *@created    September 4, 2002
 *@version    $Id$
 */
public interface CFSDatabaseReaderImportModule {
  public static Logger logger = Logger.getLogger(DataImport.class.getName());


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

