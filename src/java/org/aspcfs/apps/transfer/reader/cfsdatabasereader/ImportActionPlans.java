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
package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.troubletickets.base.TicketCategoryDraftPlanMapList;
import org.aspcfs.modules.troubletickets.base.TicketCategoryPlanMapList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Processes action plans
 *
 * @author srini
 * @version $Id:
 *          $
 * @created February 03, 2006
 */

public class ImportActionPlans implements CFSDatabaseReaderImportModule {

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

    logger.info("ImportTickets-> Inserting Lookup Step Actions");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "lookupStepActions");
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting ActionPlanCategory records");
    ActionPlanCategoryList actionPlanCategoryList = new ActionPlanCategoryList();
    actionPlanCategoryList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, actionPlanCategoryList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting Action Plan Constants");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "actionPlanConstants");
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting Action Plan Editor Lookup");
    PlanEditorList planEditorList = new PlanEditorList();
    planEditorList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, planEditorList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting ActionPlan");
    ActionPlanList actionPlanList = new ActionPlanList();
    actionPlanList.buildList(db);
    writer.setAutoCommit(false);
    this.saveActionPlanList(db, actionPlanList);
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting ActionPhase");
    ActionPhaseList actionPhaseList = new ActionPhaseList();
    actionPhaseList.buildList(db);
    writer.setAutoCommit(false);
    this.saveActionPhaseList(db, actionPhaseList);
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting ActionStep");
    ActionStepList actionStepList = new ActionStepList();
    actionStepList.buildList(db);
    writer.setAutoCommit(false);
    this.saveActionStepList(db, actionStepList);
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting StepActionMap records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "stepActionMap");
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting ActionPlan Work");
    ActionPlanWorkList actionPlanWorkList = new ActionPlanWorkList();
    actionPlanWorkList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, actionPlanWorkList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting ActionPlan WorkNote");
    ActionPlanWorkNoteList actionPlanWorkNoteList = new ActionPlanWorkNoteList();
    actionPlanWorkNoteList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, actionPlanWorkNoteList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting ActionPhase Work");
    ActionPhaseWorkList actionPhaseWorkList = new ActionPhaseWorkList();
    actionPhaseWorkList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, actionPhaseWorkList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting ActionItem Work");
    ActionItemWorkList actionItemWorkList = new ActionItemWorkList();
    actionItemWorkList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, actionItemWorkList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting ActionItemWorkNote records");
    ActionItemWorkNoteList actionItemWorkNoteList = new ActionItemWorkNoteList();
    actionItemWorkNoteList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, actionItemWorkNoteList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting ActionStepLookup records");
    ActionStepLookupList actionStepLookupList = new ActionStepLookupList();
    actionStepLookupList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, actionStepLookupList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportTickets-> Inserting ActionStepAccountType records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "actionStepAccountTypes");
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting ActionItemWorkSelection records");
    ActionItemWorkSelectionList actionItemWorkSelectionList = new ActionItemWorkSelectionList();
    actionItemWorkSelectionList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, actionItemWorkSelectionList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting TicketCategoryPlanMap records ");
    TicketCategoryPlanMapList ticketCategoryPlanMapList = new TicketCategoryPlanMapList();
    ticketCategoryPlanMapList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, ticketCategoryPlanMapList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportActionPlans-> Inserting TicketCategoryDraftPlanMap records ");
    TicketCategoryDraftPlanMapList ticketCategoryDraftPlanMapList = new TicketCategoryDraftPlanMapList();
    ticketCategoryDraftPlanMapList.buildList(db);
    writer.setAutoCommit(false);
    mappings.saveList(writer, ticketCategoryDraftPlanMapList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    return true;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param planList Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void saveActionPlanList(Connection db, ActionPlanList planList) throws SQLException {
    Iterator plans = planList.iterator();
    while (plans.hasNext()) {
      ActionPlan plan = (ActionPlan) plans.next();

      DataRecord thisRecord = mappings.createDataRecord(plan, "insert");
      if (plan.getCatCode() == 0) {
        thisRecord.removeField("catCode");
        thisRecord.addField("catCode", -1);
      }

      if (plan.getSubCat1() == 0) {
        thisRecord.removeField("subCat1");
        thisRecord.addField("subCat1", -1);
      }

      if (plan.getSubCat2() == 0) {
        thisRecord.removeField("subCat2");
        thisRecord.addField("subCat2", -1);
      }

      if (plan.getSubCat3() == 0) {
        thisRecord.removeField("subCat3");
        thisRecord.addField("subCat3", -1);
      }

      writer.save(thisRecord);
    }
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param phaseList Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void saveActionPhaseList(Connection db, ActionPhaseList phaseList) throws SQLException {
    Iterator phases = phaseList.iterator();
    while (phases.hasNext()) {
      ActionPhase phase = (ActionPhase) phases.next();

      DataRecord thisRecord = mappings.createDataRecord(phase, "insert");
      if (phase.getParentId() == 0) {
        thisRecord.removeField("parentId");
        thisRecord.addField("parentId", -1);
      }
      writer.save(thisRecord);
    }
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param stepList Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void saveActionStepList(Connection db, ActionStepList stepList) throws SQLException {
    Iterator steps = stepList.iterator();
    while (steps.hasNext()) {
      ActionStep step = (ActionStep) steps.next();
      DataRecord thisRecord = mappings.createDataRecord(step, "insert");
      if (step.getParentId() == 0) {
        thisRecord.removeField("parentId");
        thisRecord.addField("parentId", -1);
      }
      writer.save(thisRecord);
    }
  }
}

