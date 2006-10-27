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

import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityComponentList;
import org.aspcfs.modules.pipeline.base.OpportunityComponentLogList;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Processes Opportunities
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 16, 2004
 */
public class ImportOpportunities implements CFSDatabaseReaderImportModule {

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
    logger.info("ImportBaseData-> Inserting lookup_opportunity_types");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupOpportunityTypes");
    if (!processOK) {
      return false;
    }

    logger.info("ImportOpportunities-> Inserting Opps");
    OpportunityHeaderList oppHeaderList = new OpportunityHeaderList();
    oppHeaderList.buildList(db);

    //Build a list of trashed Opportunities
    OpportunityHeaderList trashedOppHeaderList = new OpportunityHeaderList();
    trashedOppHeaderList.setIncludeOnlyTrashed(true);
    trashedOppHeaderList.buildList(db);

    oppHeaderList.addAll(trashedOppHeaderList);

    writer.setAutoCommit(false);
    mappings.saveList(writer, oppHeaderList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportOpportunities-> Inserting Opps components");
    writer.setAutoCommit(false);
    OpportunityComponentList oppCompList = new OpportunityComponentList();
    oppCompList.buildList(db);

    //Build a list of trashed components
    OpportunityComponentList trashedCompList = new OpportunityComponentList();
    trashedCompList.setIncludeOnlyTrashed(true);
    trashedCompList.buildList(db);

    oppCompList.addAll(trashedCompList);

    this.saveOppComponentList(db, oppCompList);
    processOK = writer.commit();
    oppCompList = null;
    if (!processOK) {
      return false;
    }

    writer.setAutoCommit(true);
    logger.info("ImportBaseData-> Inserting Opportunity Component Levels");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "opportunityComponentLevels");
    if (!processOK) {
      return false;
    }

    logger.info("ImportOpportunities-> Inserting Opps Component Log");
    OpportunityComponentLogList logs = new OpportunityComponentLogList();
    logs.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, logs, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    return true;
  }


  /**
   * Description of the Method
   *
   * @param db          Description of the Parameter
   * @param oppCompList Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void saveOppComponentList(Connection db, OpportunityComponentList oppCompList) throws SQLException {
    Iterator components = oppCompList.iterator();
    while (components.hasNext()) {
      OpportunityComponent thisComponent = (OpportunityComponent) components.next();

      DataRecord thisRecord = mappings.createDataRecord(thisComponent, "insert");
      thisRecord.addField("updateOnInsert", "false");

      writer.save(thisRecord);
    }
  }

}

