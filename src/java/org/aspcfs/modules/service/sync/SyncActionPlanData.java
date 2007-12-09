/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.modules.service.sync;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.modules.service.base.SyncTable;
import org.aspcfs.modules.service.base.TransactionItem;
import org.aspcfs.modules.service.sync.base.SyncPackage;

/**
 * Description of the Class
 *
 * @author holub
 * @version $Id: Exp $
 * @created Feb 5, 2007
 *
 */
public class SyncActionPlanData extends SyncData implements SyncModule {

  private final static Logger log = Logger.getLogger(org.aspcfs.modules.service.sync.SyncActionPlanData.class);

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of the Parameter
   * @param systemStatus
   *          Description of the Parameter
   * @param syncClient
   *          Description of the Parameter
   * @param syncPackage
   *          Description of the Parameter
   * @param input
   *          Description of the Parameter
   * @return Description of the Return Value
   * @exception SQLException
   *              Description of the Exception
   */
  public boolean process(Connection db, SystemStatus systemStatus, HashMap objectMap, SyncPackage syncPackage,
      SyncClient syncClient, HashMap input, Timestamp lastAnchor, Timestamp nextAnchor)
          throws SQLException {

    log.info("Adding Sync Package Data Records");

    this.lastAnchor = lastAnchor;
    this.nextAnchor = nextAnchor;

    this.syncPackage = syncPackage;

    if (systemStatus.hasPermission(syncClient.getUserId(), "accounts-action-plans-view-offline")) {
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("lookupStepActionsList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionPlanCategoryList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionPlanConstantsList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionPlanEditorLookupList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionPlanList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionPhaseList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionStepList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("stepActionMapList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionPlanWorkList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionPlanWorkNoteList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionPhaseWorkList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionItemWorkList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionItemWorkNoteList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionStepLookupList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionStepAccountTypesList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("actionItemWorkSelectionList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("ticketCategoryList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("ticketCategoryDraftList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("ticketCategoryPlanMapList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("ticketCategoryDraftPlanMapList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("campaignList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("campaignRunList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("scheduledRecipientList"), TransactionItem.SYNC, input);
    }

    return true;
  }

  /**
   * Gets the syncTables attribute of the SyncActionPlanData object
   *
   * @return The syncTables value
   */
  public ArrayList<String> getSyncTables() {
    ArrayList<String> syncTables = new ArrayList<String>();

    syncTables.add("lookupStepActionsList");
    syncTables.add("actionPlanCategoryList");
    syncTables.add("actionPlanConstantsList");
    syncTables.add("actionPlanEditorLookupList");
    syncTables.add("actionPlanList");
    syncTables.add("actionPhaseList");
    syncTables.add("actionStepList");
    syncTables.add("stepActionMapList");
    syncTables.add("actionPlanWorkList");
    syncTables.add("actionPlanWorkNoteList");
    syncTables.add("actionPhaseWorkList");
    syncTables.add("actionItemWorkList");
    syncTables.add("actionItemWorkNoteList");
    syncTables.add("actionStepLookupList");
    syncTables.add("actionStepAccountTypesList");
    syncTables.add("actionItemWorkSelectionList");
    syncTables.add("ticketCategoryList");
    syncTables.add("ticketCategoryDraftList");
    syncTables.add("ticketCategoryPlanMapList");
    syncTables.add("ticketCategoryDraftPlanMapList");
    syncTables.add("campaignList");
    syncTables.add("campaignRunList");
    syncTables.add("scheduledRecipientList");

    return syncTables;
  }
}