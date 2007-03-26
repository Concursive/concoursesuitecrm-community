/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
 * @created    November 8, 2006
 * @version
 */
public class SyncBaseData extends SyncData implements SyncModule {

  private final static Logger log = Logger.getLogger(org.aspcfs.modules.service.sync.SyncBaseData.class);

  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  packetContext     Description of the Parameter
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
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupAccessTypesList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupIndustryList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupSiteIdList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupStageList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupSegmentsList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("lookupSegmentsList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("userGroupList"), TransactionItem.SYNC, input);

    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("userList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("roleList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("permissionCategoryList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("permissionList"), TransactionItem.SYNC, input);
    this.insertSyncPackageData(
        db, (SyncTable) objectMap.get("rolePermissionList"), TransactionItem.SYNC, input);
    
    return true;
  }


  /**
   *  Gets the syncTables attribute of the SyncBaseData object
   *
   * @return    The syncTables value
   */
  public ArrayList getSyncTables() {
    ArrayList syncTables = new ArrayList();

    syncTables.add("lookupAccessTypesList");
    syncTables.add("lookupIndustryList");
    syncTables.add("lookupSiteIdList");
    syncTables.add("lookupStageList");
    syncTables.add("lookupSegmentsList");
    syncTables.add("lookupSubSegmentList");
    syncTables.add("userGroupList");
    syncTables.add("userList");
    syncTables.add("roleList");
    syncTables.add("permissionCategoryList");
    syncTables.add("permissionList");
    syncTables.add("rolePermissionList");
    
    return syncTables;
  }
}

