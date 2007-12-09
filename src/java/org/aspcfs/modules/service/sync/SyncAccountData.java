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

import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.modules.service.base.SyncTable;
import org.aspcfs.modules.service.base.TransactionItem;
import org.aspcfs.modules.service.sync.base.SyncPackage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    March 6, 2007
 */
public class SyncAccountData extends SyncData implements SyncModule {
  private final static Logger log = Logger.getLogger(org.aspcfs.modules.service.sync.SyncAccountData.class);


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  systemStatus     Description of the Parameter
   * @param  objectMap     Description of the Parameter
   * @param  syncPackage       Description of the Parameter
   * @param  syncClient        Description of the Parameter
   * @param  input             Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean process(Connection db, SystemStatus systemStatus, HashMap objectMap, SyncPackage syncPackage,
      SyncClient syncClient, HashMap input, Timestamp lastAnchor, Timestamp nextAnchor) 
          throws SQLException {

    log.info("Adding Sync Package Data Records");

    this.lastAnchor = lastAnchor;
    this.nextAnchor = nextAnchor;

    this.syncPackage = syncPackage;

    //The order in which records are read are important. This order should not be changed
    //in order to maintain referential integrity.
    if (systemStatus.hasPermission(
        syncClient.getUserId(), "accounts-accounts-view-offline")) {

      //Prepare a SyncPackageData entry for Accounts
      this.insertSyncPackageData(
          db, (SyncTable) objectMap.get("accountList"), TransactionItem.SYNC, input);
      //Prepare a SyncPackageData entry for Account Addresses
      this.insertSyncPackageData(
          db, (SyncTable) objectMap.get("organizationAddressList"), TransactionItem.SYNC, input);
      //Prepare a SyncPackageData entry for Account Phone Numbers
      this.insertSyncPackageData(
          db, (SyncTable) objectMap.get("organizationPhoneNumberList"), TransactionItem.SYNC, input);
      //Prepare a SyncPackageData entry for Account Email Addresses
      this.insertSyncPackageData(
          db, (SyncTable) objectMap.get("organizationEmailAddressList"), TransactionItem.SYNC, input);
    }

    if (systemStatus.hasPermission(
        syncClient.getUserId(), "accounts-accounts-contacts-view-offline")) {

      //TODO: Just Account Contacts. This now retreives all the contacts
      //this.insertSyncPackageData(
      //  db, (SyncTable) objectMap.get("contactList"), TransactionItem.SYNC, input);
    }

    if (systemStatus.hasPermission(
        syncClient.getUserId(), "contacts-external_contacts-view-offline")) {

      //TODO: General Contacts. How to tell the sync package compiler to fetch just the user records?
      this.insertSyncPackageData(
          db, (SyncTable) objectMap.get("lookupContactTypesList"), TransactionItem.SYNC, input);

      this.insertSyncPackageData(
          db, (SyncTable) objectMap.get("contactList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable) objectMap.get("contactAddressList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable) objectMap.get("contactPhoneNumberList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable) objectMap.get("contactEmailAddressList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable) objectMap.get("contactTypeLevelsList"), TransactionItem.SYNC, input);
    }

    if (systemStatus.hasPermission(
        syncClient.getUserId(), "accounts-accounts-history-view-offline")) {

      this.insertSyncPackageData(
          db, (SyncTable) objectMap.get("contactHistoryList"), TransactionItem.SYNC, input);
    }

    if (systemStatus.hasPermission(
        syncClient.getUserId(), "contacts-external_contacts-history-view-offline")) {
      //TODO: Just the contact history
      //this.insertSyncPackageData((SyncTable) objectMap.get("contactHistoryList"), TransactionItem.SYNC, input);
    }

    return true;
  }


  /**
   *  Gets the syncTables attribute of the SyncAccountData object
   *
   * @return    The syncTables value
   */
  public ArrayList getSyncTables() {
    ArrayList syncTables = new ArrayList();
    syncTables.add("accountList");
    syncTables.add("organizationAddressList");
    syncTables.add("organizationPhoneNumberList");
    syncTables.add("organizationEmailAddressList");
    syncTables.add("lookupContactTypesList");
    syncTables.add("contactList");
    syncTables.add("contactAddressList");
    syncTables.add("contactPhoneNumberList");
    syncTables.add("contactEmailAddressList");
    syncTables.add("contactTypeLevelsList");
    syncTables.add("contactHistoryList");
    return syncTables;
  }
}

