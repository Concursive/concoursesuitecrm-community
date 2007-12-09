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
package org.aspcfs.controller;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.reader.CFSXMLReader;
import org.aspcfs.apps.transfer.writer.cfsdatabasewriter.CFSXMLDatabaseWriter;
import org.aspcfs.modules.offline.base.OfflinePrefs;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.utils.CRMConnection;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  When the webapp is started in offline mode for the first time, the sync hook
 *  verfies to see if a Sync Package has to be processed and records need to be
 *  inserted. Once the package is successfully installed, the server is
 *  requested to clean up the client related sync process data.
 *
 * @author     Ananth
 * @created    February 5, 2007
 */
public class SyncHook {

  static Logger log = Logger.getLogger(org.aspcfs.controller.SyncHook.class);
  final static String PROPERTY__SYNC_STATE = "sync.state";
  final static String PROPERTY__SYNC_PACKAGE = "sync.package";
  final static String PROPERTY__SYNC_ANCHOR = "sync.anchor";
  final static String SYNC_STATE__INIT = "init";
  final static String SYNC_STATE__UPDATE = "update";
  final static String SYNC_STATE__FAILED = "failed";
  final static String SYNC_STATE__READY = "ready";


  /**
   *  Gets the connection attribute of the SyncHook class
   *
   * @param  context        Description of the Parameter
   * @return                The connection value
   * @exception  Exception  Description of the Exception
   */
  static Connection getConnection(ServletContext context) throws Exception {
    ApplicationPrefs prefs = getPrefs(context);
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    String url = prefs.get("GATEKEEPER.URL");
    String user = prefs.get("GATEKEEPER.USER");
    String password = prefs.get("GATEKEEPER.PASSWORD");
    return DatabaseUtils.getConnection(url, user, password);
  }


  /**
   *  Gets the prefs attribute of the SyncHook class
   *
   * @param  context  Description of the Parameter
   * @return          The prefs value
   */
  static ApplicationPrefs getPrefs(ServletContext context) {
    return (ApplicationPrefs) context.getAttribute("applicationPrefs");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   */
  static void initSync(ServletContext context) {
    ApplicationPrefs prefs = getPrefs(context);
    String offlineMode = prefs.get("OFFLINE_MODE");
    String control = prefs.get("CONTROL");
    boolean configured = control != null && control.equalsIgnoreCase("configured");
    if (offlineMode != null && offlineMode.equalsIgnoreCase("true") && !configured) {
      String syncState = prefs.get(PROPERTY__SYNC_STATE);
      if (syncState != null) {
        if (syncState.equalsIgnoreCase(SYNC_STATE__INIT)) {
          log.info("DB configured");
          if (insertSyncClient(context)) {
            try {
              // insertDefautlData(getConnection(context));
              boolean processed = updatePackage(context);

              if (processed) {
                // Prepare Concursive server connection info
                CRMConnection crm = new CRMConnection();
                crm.setUrl(OfflinePrefs.getUrl());
                crm.setUsername(OfflinePrefs.getUserName());
                crm.setCode(OfflinePrefs.getCode());
                crm.setClientId(OfflinePrefs.getClientId());

                crm.setNextAnchor(java.sql.Timestamp.valueOf(prefs.get(PROPERTY__SYNC_ANCHOR)));

                System.out.println("SyncHook-> Notify server to cleanup");
                DataRecord record = new DataRecord();
                record.setName("syncClient");
                record.setAction("syncProcessed");
                crm.load(record);

                // update local sync_client's anchor (client_id == 0)
                setClientAnchor(context);

                System.out.println("SyncHook-> Closing sync session");
                record.setName("syncClient");
                record.setAction("syncEnd");
                crm.load(record);

                prefs.add("CONTROL", "configured");
                prefs.add(PROPERTY__SYNC_STATE, SYNC_STATE__READY);
                prefs.save();
                prefs.populateContext(context);
              }
            } catch (Exception e) {
              log.error("initial sync failed", e);
            }
          }
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context        Description of the Parameter
   * @return                Description of the Return Value
   * @exception  Exception  Description of the Exception
   */
  static boolean updatePackage(ServletContext context) throws Exception {
    ApplicationPrefs prefs = getPrefs(context);
    String path = prefs.get("FILELIBRARY") + File.separator + "sync-packages" + File.separator + prefs.get(PROPERTY__SYNC_PACKAGE);
    boolean f = false;
    try{
      java.sql.Connection db = getConnection(context);
      java.sql.Connection lookup = getConnection(context);
      CFSXMLReader reader = new CFSXMLReader();
      reader.setXmlDataFile(path);
      CFSXMLDatabaseWriter writer = new CFSXMLDatabaseWriter();
      // writer.setClientId(OfflinePrefs.getClientId());
      writer.setClientId(0);
      writer.setConnection(db);
      writer.setConnection(lookup);
      writer.initialize();
      f = reader.execute(writer);
      writer.close();
      if (!f) {
        log.error("Error while updating sync package.");
      }
    }finally{
      new File(path).delete();
    }

    return f;
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  private static boolean insertSyncClient(ServletContext context) {
    log.info("Inserting sync_client = 0 - default sync_client for Offline application...");
    SyncClient syncClient = new SyncClient();
    // syncClient.setId(OfflinePrefs.getClientId());
    syncClient.setId(0);
    syncClient.setType("Offline Local Client");
    syncClient.setEnabled(false);
    syncClient.setEnteredBy(0);
    syncClient.setModifiedBy(0);
    try {
      Connection db = getConnection(context);
      try {
        syncClient.insert(db);
        db.commit();
      } catch (Exception e) {
        db.rollback();
        return false;
      } finally {
        db.close();
      }
    } catch (Exception e) {
      log.error("SyncClient insert error", e);
      return false;
    }
    return true;
  }


  /**
   *  Sets the clientAnchor attribute of the SyncHook class
   *
   * @param  context  The new clientAnchor value
   * @return          Description of the Return Value
   */
  private static boolean setClientAnchor(ServletContext context) {
    ApplicationPrefs prefs = getPrefs(context);
    String dataVal = prefs.get(PROPERTY__SYNC_ANCHOR);
    Timestamp anchor = java.sql.Timestamp.valueOf(dataVal);
    try {
      Connection db = getConnection(context);
      try {
        // SyncClient syncClient = new SyncClient(db,
        // OfflinePrefs.getClientId());
        int userId = -1;
        PreparedStatement pst = db.prepareStatement(
            " SELECT user_id" + 
            " FROM " + DatabaseUtils.addQuotes(db, "access") +  
            " WHERE username = ? ");
        pst.setString(1, OfflinePrefs.getUserName());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
          userId = rs.getInt("user_id");
        }
        rs.close();
        pst.close();
        
        SyncClient syncClient = new SyncClient(db, 0);
        syncClient.setAnchor(anchor);
        if (userId > -1) {
          syncClient.setUserId(userId);
        }
        syncClient.updateSyncAnchor(db);
        db.commit();
      } catch (Exception e) {
        e.printStackTrace(System.out);
        db.rollback();
      } finally {
        db.close();
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      log.error(e);
      return false;
    }
    return true;
  }
}

