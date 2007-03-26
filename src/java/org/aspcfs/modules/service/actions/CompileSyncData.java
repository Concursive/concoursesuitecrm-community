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
package org.aspcfs.modules.service.actions;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.service.base.CustomActionHandler;
import org.aspcfs.modules.service.base.PacketContext;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.modules.service.sync.SyncPackager;
import org.aspcfs.modules.service.sync.base.SyncPackage;
import org.quartz.Scheduler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 *  The Server's Sync Data Package Complier.
 *
 * @author     Ananth
 * @version
 * @created    November 7, 2006
 */
public class CompileSyncData implements CustomActionHandler {
  /**
   *  When a sync client needs to perform a sync with the server, the server
   *  needs to prepare a sync package for the client. This method reviews the
   *  client input and prepares the information required to package the client's
   *  sync data and triggers the CompileSyncDataJob asynchronously and returns
   *  true to the user. If there were conflicts during processing i/p or
   *  preparing package info it returns false
   *
   * @param  packetContext     Description of the Parameter
   * @param  db                Description of the Parameter
   * @param  values            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean process(PacketContext packetContext, Connection db,
      HashMap values) throws SQLException {
    
    int clientId = packetContext.getAuthenticationItem().getClientId();
    System.out.println("CompileSyncData-> Preparing Compilation Data for Client: " + clientId);
    
    Timestamp lastAnchor = packetContext.getAuthenticationItem().getLastAnchor();
    Timestamp nextAnchor = packetContext.getAuthenticationItem().getNextAnchor();
    
    System.out.println("CompileSyncData-> lastAnchor: " + lastAnchor);
    System.out.println("CompileSyncData-> nextAnchor: " + nextAnchor);
    
    SystemStatus systemStatus = packetContext.getSystemStatus();

    /*
     *  If any CompileSyncData input was provided by the client, it would be
     *  available as part of the HashMap values.
     */
    SyncClient syncClient = new SyncClient(db, clientId);

    if (syncClient.hasSyncPackage(db)) {
      /*
       *  Client should not be requesting for a new sync package when one already exists. 
       *  Let the client ping for the package status.
       */
      return true;
    }

    // Prepare Sync Package Data by instantiating a SyncPackager. Pass the client provided input to the 
    // Packager application
    SyncPackager packager = new SyncPackager(values);

    packager.process(db, packetContext, syncClient);

    // Trigger the ProcessSyncPackages task
    Scheduler scheduler = (Scheduler) packetContext.getActionContext()
        .getServletContext().getAttribute("Scheduler");
    try {
      scheduler.triggerJob("syncDataCompiler", Scheduler.DEFAULT_GROUP);
    } catch (Exception e) {
      System.out.println("CFSModule-> Scheduler failed: " + e.getMessage());
    }

    return true;
  }


  /**
   *  The client during sync, requests the server to see if the client's sync
   *  package is ready or not. This method returns true if the package is ready
   *  else returns false.
   *
   * @param  packetContext     Description of the Parameter
   * @param  db                Description of the Parameter
   * @param  values            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean status(PacketContext packetContext, Connection db,
      HashMap values) throws SQLException {
    int clientId = packetContext.getAuthenticationItem().getClientId();
    
    Timestamp lastAnchor = packetContext.getAuthenticationItem().getLastAnchor();
    Timestamp nextAnchor = packetContext.getAuthenticationItem().getNextAnchor();
    
    SyncClient syncClient = new SyncClient(db, clientId);

    if (syncClient.hasSyncPackage(db)) {
      SyncPackage syncPackage = syncClient.getSyncPackage();
      if (( (lastAnchor == null && syncPackage.getLastAnchor() == null) || lastAnchor.equals(syncPackage.getLastAnchor())) &&
                  nextAnchor.equals(syncPackage.getNextAnchor())) {
        if (syncPackage.isReady()) {
          return true;
        }
      }
    }
    return false;
    
  }
}

