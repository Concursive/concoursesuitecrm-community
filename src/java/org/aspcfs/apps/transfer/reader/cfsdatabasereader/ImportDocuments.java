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

import com.zeroio.iteam.base.FileDownloadLogList;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.base.FileItemVersionList;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Reads all Documents
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 18, 2002
 */
public class ImportDocuments implements CFSDatabaseReaderImportModule {

  DataWriter writer = null;
  PropertyMapList mappings = null;


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
    this.writer = writer;
    this.mappings = mappings;
    boolean processOK = true;
    writer.setAutoCommit(true);

    logger.info("ImportDocuments-> Inserting FileItemList");
    FileItemList fileItemList = new FileItemList();
    fileItemList.buildList(db);

    Iterator fileItems = fileItemList.iterator();
    while (fileItems.hasNext()) {
      FileItem thisItem = (FileItem) fileItems.next();
      DataRecord thisRecord = mappings.createDataRecord(thisItem, "insert");
      thisRecord.addField("doVersionInsert", "false");
      switch (thisItem.getLinkModuleId()) {
        case 1:
          thisRecord.addField(
              "linkItemId", String.valueOf(thisItem.getLinkItemId()), "account", null);
          break;
        case 3:
          thisRecord.addField(
              "linkItemId", String.valueOf(thisItem.getLinkItemId()), "opportunity", null);
          break;
        case 5:
          thisRecord.addField(
              "linkItemId", String.valueOf(thisItem.getLinkItemId()), "campaign", null);
          break;
        default:
          thisRecord.addField(
              "linkItemId", String.valueOf(thisItem.getLinkItemId()), null, null);
          break;
      }
      processOK = writer.save(thisRecord);
    }

    logger.info("ImportDocuments-> Inserting FileItemVersionList");
    FileItemVersionList fileItemVersionList = new FileItemVersionList();
    fileItemVersionList.buildList(db);
    mappings.saveList(writer, fileItemVersionList, "insert");

    logger.info("ImportDocuments-> Inserting FileDownloadLogList");
    FileDownloadLogList downloadLog = new FileDownloadLogList();
    downloadLog.buildList(db);
    mappings.saveList(writer, downloadLog, "insert");

    return true;
  }

}

