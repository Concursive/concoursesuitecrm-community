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
package org.aspcfs.modules.service.sync;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.modules.service.base.TransactionItem;
import org.aspcfs.modules.service.sync.base.SyncPackage;
import org.aspcfs.modules.service.base.SyncTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    November 8, 2006
 * @version
 */
public class SyncLookupData extends SyncData implements SyncModule {

  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  packetContext     Description of the Parameter
   * @param  syncClient        Description of the Parameter
   * @param  syncPackage       Description of the Parameter
   * @param  input             Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean process(Connection db, SystemStatus systemStatus, HashMap objectMap, SyncPackage syncPackage,
      SyncClient syncClient, HashMap input, Timestamp lastAnchor, Timestamp nextAnchor) 
          throws SQLException {
            
    System.out.println("SyncLookupData-> Adding Sync Package Data Records");

    this.lastAnchor = lastAnchor;
    this.nextAnchor = nextAnchor; 

    this.syncPackage = syncPackage;

    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupAccountTypesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupAccountSizeList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupAccountStageList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupContactAddressTypesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupContactEmailTypesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupContactPhoneTypesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupContactSourceList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupContactRatingList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupDepartmentList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupInstantMessengerTypesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupInstantMessengerServicesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupLocaleList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupOrgAddressTypesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupOrgEmailTypesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupOrgPhoneTypesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupTitleList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupTextMessageTypesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupOpportunityEnvironmentList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupOpportunityCompetitorsList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupOpportunityEventCompellingList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupOpportunityBudgetList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupCallTypesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupDurationTypeList"), TransactionItem.SYNC, input);
    
    return true;
  }


  /**
   *  Gets the syncTables attribute of the SyncLookupData object
   *
   * @return    The syncTables value
   */
  public ArrayList getSyncTables() {
    ArrayList syncTables = new ArrayList();

    syncTables.add("lookupAccountTypesList");
    syncTables.add("lookupAccountSizeList");
    syncTables.add("lookupAccountStageList");
    syncTables.add("lookupContactAddressTypesList");
    syncTables.add("lookupContactEmailTypesList");
    syncTables.add("lookupContactPhoneTypesList");
    syncTables.add("lookupContactSourceList");
    syncTables.add("lookupContactRatingList");
    syncTables.add("lookupDepartmentList");
    syncTables.add("lookupInstantMessengerTypesList");
    syncTables.add("lookupInstantMessengerServicesList");
    syncTables.add("lookupLocaleList");
    syncTables.add("lookupOrgAddressTypesList");
    syncTables.add("lookupOrgEmailTypesList");
    syncTables.add("lookupOrgPhoneTypesList");
    syncTables.add("lookupTitleList");
    syncTables.add("lookupTextMessageTypesList");
    syncTables.add("lookupOpportunityEnvironmentList");
    syncTables.add("lookupOpportunityCompetitorsList");
    syncTables.add("lookupOpportunityEventCompellingList");
    syncTables.add("lookupOpportunityBudgetList");
    syncTables.add("lookupCallTypesList");
    syncTables.add("lookupDurationTypeList");

    return syncTables;
  }
}

