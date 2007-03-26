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
package org.aspcfs.modules.offline.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.apps.test.HttpPost;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.reader.CFSXMLReader;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMap;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMapList;
import org.aspcfs.apps.transfer.writer.cfsdatabasewriter.CFSXMLDatabaseWriter;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.offline.base.OfflinePrefs;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.modules.service.base.SyncSystem;
import org.aspcfs.modules.service.sync.SyncPackager;
import org.aspcfs.modules.service.sync.base.SyncPackage;
import org.aspcfs.utils.CRMConnection;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    December 21, 2006
 */
public final class RequestSyncUpdates extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (isOfflineMode(context)) {
      Connection db = null;
      Connection dbLookup = null;
      try {
        db = this.getConnection(context);
        dbLookup = this.getConnection(context);

        ApplicationPrefs prefs = new ApplicationPrefs(context.getServletContext());

        //Needs to be cached when in offline mode. Not sure where!
        String processConfigFile = context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "import-mappings.xml";
        PropertyMapList mappings = new PropertyMapList(processConfigFile, new ArrayList());
        System.out.println("RequestSyncUpdates-> Mappings Loaded");

        SystemStatus systemStatus = this.getSystemStatus(context);

        //Prepare centric crm server connection info
        CRMConnection crm = new CRMConnection();
        crm.setUrl(OfflinePrefs.getUrl());
        crm.setUsername(OfflinePrefs.getUserName());
        crm.setCode(OfflinePrefs.getCode());
        crm.setClientId(OfflinePrefs.getClientId());

        //Get the server's current date/time for this sync session's nextAnchor info
        String nextAnchor = RequestSyncUpdates.getNextAnchor(crm);

        //Determine the offline client's lastAnchor
        SyncClient syncClient = new SyncClient(db, 0);
        Timestamp lastAnchor = syncClient.getAnchor();
        System.out.println("RequestSyncUpdates-> nextAnchor: " + nextAnchor + ", lastAnchor: " + lastAnchor);

        crm.setLastAnchor(lastAnchor);
        crm.setNextAnchor(nextAnchor);

        //Request the server to confirm if the client can perform a sync at this time.
        int status = RequestSyncUpdates.getStatus(crm);

        if (status == 0) {
          System.out.println("RequestSyncUpdates-> Proceeding with Sync");
          System.out.println("RequestSyncUpdates-> Preparing client sync package");

          //TODO: Review Client Sync Preferences

          HashMap objectMap = systemStatus.getXMLObjectMap(db, SyncSystem.HTTP_XML_API);

          //Prepare the client's sync package to be sent to the server
          SyncPackager serverPackager = new SyncPackager(SyncPackage.SYNC_SERVER);

          System.out.println("RequestSyncUpdates-> Invoking Server Packager");
          serverPackager.process(
              db, syncClient,
              systemStatus, objectMap,
              crm.getLastAnchor(), crm.getNextAnchor());

          SyncPackage serverSyncPackage = serverPackager.getSyncPackage();

          String syncFileDest = prefs.get("FILELIBRARY") + File.separator + "sync-packages" + File.separator;
          String syncFile = DateUtils.getFilename(serverSyncPackage.getEntered()) + "-" + serverSyncPackage.getId();

          System.out.println("RequestSyncUpdates-> Preparing Client Sync Package");
          serverPackager.prepareSyncPackage(
              db, dbLookup, processConfigFile, syncFileDest, syncFile);

          System.out.println("RequestSyncUpdates-> Transmitting Client Sync Package");
          //Transmit the client's sync package to the server
          transmitPackage(crm, syncFileDest, syncFile);

          System.out.println("RequestSyncUpdates-> Preparing Sync Compiler Input");
          //prepare sync compiler input
          DataRecord compiler = new DataRecord();
          compiler.setName("compileSyncData");
          compiler.setAction("execute");

          SyncPackager packager = new SyncPackager();

          ArrayList meta = new ArrayList();
          ArrayList syncTables = packager.getSyncTables();

          if (syncTables != null) {
            Iterator tables = syncTables.iterator();
            while (tables.hasNext()) {
              String syncTable = (String) tables.next();
              int identity = getMaxId(db, mappings, syncTable);
              System.out.println("RequestSyncUpdates-> Sync Table: " + syncTable + ", Identity: " + identity);
              meta.add(syncTable + "-identity");
              compiler.addField(syncTable + "-identity", (identity + 1));
            }
          }

          //Trigger the compiler
          crm.setTransactionMeta(meta);
          crm.load(compiler);

          status = crm.getStatus();
          if (status == 0) {
            //compiler triggered successfully
            System.out.println("RequestSyncUpdates-> Compiler triggered successfully");
            DataRecord record = new DataRecord();
            record.setName("compileSyncData");
            record.setAction("status");
            crm.load(record);

            //Wait for a certain period of time and request the sync package status
            Thread.sleep(30 * 1000);
            status = crm.getStatus();
            for (int tries = 10; status != 0 && tries > 0; tries--) {
              Thread.sleep(15 * 1000);
              crm.load(record);
              status = crm.getStatus();
            }

            if (status == 0) {
              //Download the sync package when ready
              System.out.println("RequestSyncUpdates-> Downloading Sync Package");
              CRMConnection packet = new CRMConnection();
              packet.setUrl(OfflinePrefs.getUrl() + "/ProcessSyncPackageDownload.do");
              packet.setUsername(crm.getUsername());
              packet.setClientId(crm.getClientId());
              packet.setCode(crm.getCode());
              packet.setLastAnchor(crm.getLastAnchor());
              packet.setNextAnchor(crm.getNextAnchor());

              record = new DataRecord();
              record.setName("processSyncPackageDownload");
              record.setAction("execute");
              packet.download(record);

              String syncXML = "";
              InputStream is = packet.getInputStream();
              System.out.println("RequestSyncUpdates-> Input Stream: " + is);

              File dir = new File(prefs.get("FILELIBRARY") + File.separator + "sync-packages" + File.separator);
              JarInputStream zip = new JarInputStream(is);
              ZipEntry entry = null;
              while ((entry = zip.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                  File directory = new File(dir, entry.getName());
                  if (!directory.exists()) {
                    directory.mkdirs();
                  }
                } else {
                  syncXML = dir.getAbsolutePath().concat(File.separator) + entry.getName();
                  copy(zip, syncXML);
                }
              }
              zip.close();

              boolean processed = false;
              try {
                //Process package
                System.out.println("RequestSyncUpdates-> Processing downloaded package");
                CFSXMLReader reader = new CFSXMLReader();
                reader.setXmlDataFile(syncXML);

                CFSXMLDatabaseWriter writer = new CFSXMLDatabaseWriter();
                writer.setClientId(OfflinePrefs.getClientId());
                writer.setConnection(db);
                writer.setConnection(dbLookup);
                writer.setRecipient(SyncPackage.SYNC_SERVER);
                
                writer.initialize();
                processed = reader.execute(writer);
                writer.close();
              } finally {
                new File(syncXML).delete();
              }

              //TODO:Now that the sync package is installed, delete the sync package file

              if (processed) {
                //send server notification regarding successful sync package processing
                System.out.println("RequestSyncUpdates-> Notify server to cleanup");
                record.setName("syncClient");
                record.setAction("syncProcessed");
                crm.load(record);

                //update local sync_client's anchor (client_id == 0)
                System.out.println("RequestSyncUpdates-> Updating local sync client anchor");
                syncClient.setAnchor(nextAnchor);
                syncClient.updateSyncAnchor(db);
              }
            }
          }

          System.out.println("RequestSyncUpdates-> Closing sync session");
          RequestSyncUpdates.syncEnd(crm);
        }
      } catch (Exception e) {
        e.printStackTrace(System.out);
      } finally {
        if (dbLookup != null) {
          this.freeConnection(context, dbLookup);
        }
        if (db != null) {
          this.freeConnection(context, db);
        }
      }
    }

    return "UpdateOK";
  }


  /**
   *  Description of the Method
   *
   * @param  crm
   * @return      Description of the Returned Value
   */
  private static String getNextAnchor(CRMConnection crm) {
    DataRecord record = new DataRecord();
    record.setName("syncClient");
    record.setAction("getDateTime");
    crm.load(record);
    return crm.getResponseValue("dateTime");
  }


  /**
   *  Description of the Method
   *
   * @param  crm
   * @return      Description of the Returned Value
   */
  private static int getStatus(CRMConnection crm) {
    DataRecord record = new DataRecord();
    record.setName("syncClient");
    record.setAction("syncStart");
    crm.load(record);
    return crm.getStatus();
  }


  /**
   *  Description of the Method
   *
   * @param  crm
   * @return      Description of the Returned Value
   */
  private static int syncEnd(CRMConnection crm) {
    DataRecord record = new DataRecord();
    record.setName("syncClient");
    record.setAction("syncEnd");
    crm.load(record);
    return crm.getStatus();
  }


  /**
   *  Gets the maxId attribute of the RequestSyncUpdates object
   *
   * @param  db                Description of the Parameter
   * @param  mappings          Description of the Parameter
   * @param  syncTable         Description of the Parameter
   * @return                   The maxId value
   * @exception  SQLException  Description of the Exception
   */
  private int getMaxId(Connection db, PropertyMapList mappings, String syncTable) throws SQLException {
    //TODO: Test the correct working of this method
    String objectName = syncTable.substring(0, syncTable.indexOf("List"));
    PropertyMap thisMap = (PropertyMap) mappings.getMap(objectName);

    if (thisMap.hasTable() && thisMap.hasSequence() && thisMap.hasUniqueField()) {
      String tableName = thisMap.getTable();
      String sequenceName = thisMap.getSequence();
      String uniqueField = thisMap.getUniqueField();

      return DatabaseUtils.getMaxId(
          db, tableName, sequenceName, uniqueField);
    }
    return -1;
  }


  /**
   *  Description of the Method
   *
   * @param  is
   * @param  dest
   * @throws  IOException  Description of the Returned Value
   */
  private static void copy(InputStream is, String dest) throws IOException {
    byte[] buffer = new byte[1024];
    if (dest.lastIndexOf(File.separator) > -1) {
      String path = dest.substring(0, dest.lastIndexOf(File.separator));
      File directory = new File(path);
      if (!directory.exists()) {
        directory.mkdirs();
      }
    }
    FileOutputStream file = new FileOutputStream(dest);
    int count;
    while ((count = is.read(buffer)) != -1) {
      file.write(buffer, 0, count);
    }
    file.close();
  }


  /**
   *  Description of the Method
   *
   * @param  syncFileDest   Description of the Parameter
   * @param  syncFile       Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  private void transmitPackage(CRMConnection crm, String syncFileDest, String syncFile) throws Exception {
    String compressedFileName = syncFile + ".zip";
    File syncPackage = new File(syncFileDest + compressedFileName);
    if (syncPackage.exists()) {
      try {
        URL servlet = new URL(crm.getUrl() + "/ProcessClientSyncPackage.do");
        URLConnection conn = servlet.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        String boundary = "---------------------------7d226f700d0";
        conn.setRequestProperty(
            "Content-type", "multipart/form-data; boundary=" + boundary);
        //conn.setRequestProperty("Referer", "http://127.0.0.1/index.jsp");
        //conn.setRequestProperty("Cache-Control", "no-cache");

        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeBytes("--" + boundary + "\r\n");

        HttpPost.writeParam("username", crm.getUsername(), out, boundary);
        HttpPost.writeParam("clientId", String.valueOf(crm.getClientId()), out, boundary);
        HttpPost.writeParam("code", crm.getCode(), out, boundary);
        HttpPost.writeParam("lastAnchor", StringUtils.toDateTimeString(crm.getLastAnchor()), out, boundary);
        HttpPost.writeParam("nextAnchor", StringUtils.toDateTimeString(crm.getNextAnchor()), out, boundary);

        HttpPost.writeFile("clientSyncPackage", (syncFileDest + compressedFileName), out, boundary);

        out.flush();
        out.close();
        System.out.println("RequestSyncUpdates-> Transmitted Client Sync Package: " + (syncFileDest + compressedFileName));

        InputStream stream = conn.getInputStream();
        BufferedInputStream in = new BufferedInputStream(stream);
        int i = 0;
        while ((i = in.read()) != -1) {
          System.out.write(i);
        }
        in.close();
      } catch (Exception e) {
        System.out.println(e.toString());
      }
    }
  }
}

