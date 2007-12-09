/*
 *  Copyright(c) 2006 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.reports.base.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created August 4, 2006
 */
public class ImportReports implements CFSDatabaseReaderImportModule {
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

    logger.info("ImportReports-> Inserting ReportList records");
    writer.setAutoCommit(false);
    ReportList reportList = new ReportList();
    reportList.buildList(db);
    mappings.saveList(writer, reportList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportReports-> Inserting CriteriaList records");
    writer.setAutoCommit(false);
    CriteriaList criteriaList = new CriteriaList();
    criteriaList.buildList(db);
    mappings.saveList(writer, criteriaList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportReports-> Inserting ParameterList records");
    writer.setAutoCommit(false);
    ParameterList parameterList = new ParameterList();
    parameterList.buildList(db);
    mappings.saveList(writer, parameterList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportReports-> Inserting ReportQueueList records");
    writer.setAutoCommit(false);
    ReportQueueList reportQueueList = new ReportQueueList();
    reportQueueList.buildList(db);
    mappings.saveList(writer, reportQueueList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportReports-> Inserting QueueCriteriaList records");
    writer.setAutoCommit(false);
    QueueCriteriaList queueCriteriaList = new QueueCriteriaList();
    queueCriteriaList.buildList(db);
    mappings.saveList(writer, queueCriteriaList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    return true;
  }

}

