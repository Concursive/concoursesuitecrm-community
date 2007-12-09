/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.service.utils;

import org.aspcfs.apps.transfer.reader.CFSXMLDatabaseReader;
import org.aspcfs.apps.transfer.reader.CFSXMLReader;
import org.aspcfs.apps.transfer.writer.CFSXMLWriter;
import org.aspcfs.apps.transfer.writer.cfsdatabasewriter.CFSXMLDatabaseWriter;
import org.aspcfs.modules.service.base.SyncClient;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    November 2, 2006
 */
public class BackupUtils {
  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  backupFile        Description of the Parameter
   * @param  syncClient        Description of the Parameter
   * @param  dbLookup          Description of the Parameter
   * @exception  Exception     Description of the Exception
   */
  public static void restore(Connection db, Connection dbLookup, 
                  String backupFile, SyncClient syncClient) throws Exception {
    //Instantiate a reader to read datarecords
    CFSXMLReader reader = new CFSXMLReader();
    reader.setXmlDataFile(backupFile);

    //Instantiate a writer to write datarecords to the database
    CFSXMLDatabaseWriter writer = new CFSXMLDatabaseWriter();
    writer.setClientId(syncClient.getId());
    writer.setConnection(db);
    writer.setConnectionLookup(dbLookup);
    
    writer.initialize();
    reader.execute(writer);
    writer.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  dbLookup       Description of the Parameter
   * @param  backupFile     Description of the Parameter
   * @param  syncClient     Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  public static void backup(Connection db, Connection dbLookup,
      String backupFile, String mappingsFile, SyncClient syncClient, ArrayList dataRecords) throws Exception {
    //Instantiate a reader to read datarecords
    CFSXMLDatabaseReader reader = new CFSXMLDatabaseReader();
    reader.setClientId(syncClient.getId());
    reader.setConnection(db);
    reader.setConnectionLookup(dbLookup);
    reader.setDataRecords(dataRecords);
    reader.setProcessConfigFile(mappingsFile);
    reader.setNextAnchor(new java.sql.Timestamp((new java.util.Date()).getTime()));
    
    //Instantiate a writer to write datarecords to the database
    CFSXMLWriter writer = new CFSXMLWriter();
    writer.setFilename(backupFile);
    
    if (writer.isConfigured() && reader.isConfigured()) {
      reader.initialize();
      reader.execute(writer);
    }
    writer.close();
  }
}

