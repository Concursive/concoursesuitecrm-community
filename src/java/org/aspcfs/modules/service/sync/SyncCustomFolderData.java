/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
public class SyncCustomFolderData extends SyncData implements SyncModule {

  private final static Logger log = Logger.getLogger(org.aspcfs.modules.service.sync.SyncCustomFolderData.class);

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of the Parameter
   * @param packetContext
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

    if (systemStatus.hasPermission(syncClient.getUserId(), "accounts-accounts-folders-view-offline")) {
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("moduleFieldCategoryLinkList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("customFieldCategoryList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("customFieldGroupList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("customFieldList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("customFieldLookupList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("customFieldRecordList"), TransactionItem.SYNC, input);
      this.insertSyncPackageData(
          db, (SyncTable)objectMap.get("customFieldDataList"), TransactionItem.SYNC, input);
    }

    return true;
  }

  /**
   * Gets the syncTables attribute of the SyncCustomFolderData object
   * 
   * @return The syncTables value
   */
  public ArrayList getSyncTables() {
    ArrayList syncTables = new ArrayList();

    syncTables.add("moduleFieldCategoryLinkList");
    syncTables.add("customFieldCategoryList");
    syncTables.add("customFieldGroupList");
    syncTables.add("customFieldList");
    syncTables.add("customFieldLookupList");
    syncTables.add("customFieldRecordList");
    syncTables.add("customFieldDataList");

    return syncTables;
  }
}