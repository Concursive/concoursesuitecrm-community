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

import com.zeroio.iteam.base.FileItem;

import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.reader.CFSXMLDatabaseReader;
import org.aspcfs.apps.transfer.writer.CFSXMLWriter;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.service.base.PacketContext;
import org.aspcfs.modules.service.base.TransactionItem;
import org.aspcfs.modules.service.sync.base.SyncPackage;
import org.aspcfs.modules.service.sync.base.SyncPackageData;
import org.aspcfs.modules.service.sync.base.SyncPackageDataList;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.modules.service.base.SyncTable;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.ZipUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    November 8, 2006
 * @version
 */
public class SyncPackager {
  private SyncPackage syncPackage = null;
  private ArrayList syncModules = new ArrayList();
  private HashMap input = null;
  private int recipient = SyncPackage.SYNC_CLIENT;


  /**
   *  Gets the syncPackage attribute of the SyncPackager object
   *
   * @return    The syncPackage value
   */
  public SyncPackage getSyncPackage() {
    return syncPackage;
  }


  /**
   *  Sets the syncPackage attribute of the SyncPackager object
   *
   * @param  tmp  The new syncPackage value
   */
  public void setSyncPackage(SyncPackage tmp) {
    this.syncPackage = tmp;
  }


  /**
   *  Gets the recipient attribute of the SyncPackager object
   *
   * @return    The recipient value
   */
  public int getRecipient() {
    return recipient;
  }


  /**
   *  Sets the recipient attribute of the SyncPackager object
   *
   * @param  tmp  The new recipient value
   */
  public void setRecipient(int tmp) {
    this.recipient = tmp;
  }


  /**
   *  Sets the recipient attribute of the SyncPackager object
   *
   * @param  tmp  The new recipient value
   */
  public void setRecipient(String tmp) {
    this.recipient = Integer.parseInt(tmp);
  }



  /**
   *  Gets the input attribute of the SyncPackager object
   *
   * @return    The input value
   */
  public HashMap getInput() {
    return input;
  }


  /**
   *  Sets the input attribute of the SyncPackager object
   *
   * @param  tmp  The new input value
   */
  public void setInput(HashMap tmp) {
    this.input = tmp;
  }



  /**
   *  Description of the Method
   */
  protected void initialize() {
    //Prepare a list of modules that need to be synced for this particular client
    if (recipient == SyncPackage.SYNC_CLIENT) {
      //offline client should receive lookups and base data
      syncModules.add("org.aspcfs.modules.service.sync.SyncLookupData");
      syncModules.add("org.aspcfs.modules.service.sync.SyncBaseData");
    }
    syncModules.add("org.aspcfs.modules.service.sync.SyncAccountData");
    syncModules.add("org.aspcfs.modules.service.sync.SyncRelationshipData");
    syncModules.add("org.aspcfs.modules.service.sync.SyncOpportunityData");
    syncModules.add("org.aspcfs.modules.service.sync.SyncCallsData");
    syncModules.add("org.aspcfs.modules.service.sync.SyncCustomFolderData");
    syncModules.add("org.aspcfs.modules.service.sync.SyncFilesData");
    syncModules.add("org.aspcfs.modules.service.sync.SyncActionPlanData");
    if (recipient == SyncPackage.SYNC_CLIENT) {
      syncModules.add("org.aspcfs.modules.service.sync.SyncSupportData");
    }
  }


  /**
   *  Constructor for the SyncPackager object
   */
  public SyncPackager() {
    initialize();
  }


  /**
   *  Constructor for the SyncPackager object
   *
   * @param  recipient  Description of the Parameter
   */
  public SyncPackager(int recipient) {
    this.recipient = recipient;
    initialize();
  }


  /**
   *  Constructor for the SyncPackager object
   *
   * @param  input  Description of the Parameter
   */
  public SyncPackager(HashMap input) {
    this.input = input;
    initialize();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  syncClient        Description of the Parameter
   * @param  packetContext     Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean process(Connection db, PacketContext packetContext, SyncClient syncClient) throws SQLException {
    //Determine if the syncClient is requesting an initial sync package by
    //verifying its anchor attribute. If anchor == null, it is an init sync
    Timestamp lastAnchor = packetContext.getAuthenticationItem().getLastAnchor();
    Timestamp nextAnchor = packetContext.getAuthenticationItem().getNextAnchor();

    SystemStatus systemStatus = packetContext.getSystemStatus();
    HashMap objectMap = packetContext.getObjectMap();

    return process(
        db, syncClient,
        systemStatus, objectMap,
        lastAnchor, nextAnchor);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  syncClient        Description of the Parameter
   * @param  systemStatus      Description of the Parameter
   * @param  objectMap         Description of the Parameter
   * @param  lastAnchor        Description of the Parameter
   * @param  nextAnchor        Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean process(Connection db, SyncClient syncClient, SystemStatus systemStatus,
      HashMap objectMap, Timestamp lastAnchor, Timestamp nextAnchor) throws SQLException {

    boolean processed = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //Insert a SyncPackage record for this client and update the syncClient
      if (syncPackage == null) {
        syncPackage = new SyncPackage();
      }
      syncPackage.setClientId(syncClient.getId());
      syncPackage.setType((lastAnchor == null ? SyncPackage.INIT : SyncPackage.UPDATE));
      syncPackage.setStatusId(SyncPackage.IDLE);
      syncPackage.setRecipient(recipient);
      syncPackage.setLastAnchor(lastAnchor);
      syncPackage.setNextAnchor(nextAnchor);
      syncPackage.insert(db);
      System.out.println("SyncPackager-> Added SyncPackage (" + syncPackage.getId() + ")");

      //Iterate through items in syncModules and instantiate each sync module
      //and trigger them.
      try {
        Iterator modules = syncModules.iterator();
        while (modules.hasNext()) {
          String module = (String) modules.next();
          Object object = Class.forName(module).newInstance();
          if (object instanceof SyncModule) {
            SyncModule syncModule = (SyncModule) object;
            processed = syncModule.process(
                db, systemStatus, objectMap,
                syncPackage, syncClient, input,
                lastAnchor, nextAnchor);
          }
        }
        //re-load the sync package
        syncPackage.queryRecord(db, syncPackage.getId());
      } catch (ClassNotFoundException cnf) {
        cnf.printStackTrace(System.out);
      } catch (InstantiationException ie) {
        ie.printStackTrace(System.out);
      } catch (IllegalAccessException iae) {
        iae.printStackTrace(System.out);
      }
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }

    return processed;
  }


  /**
   *  Gets the syncTables attribute of the SyncPackager object
   *
   * @return    The syncTables value
   */
  public ArrayList getSyncTables() {
    ArrayList syncTables = new ArrayList();

    try {
      Iterator modules = syncModules.iterator();
      while (modules.hasNext()) {
        String module = (String) modules.next();
        Object object = Class.forName(module).newInstance();
        if (object instanceof SyncModule) {
          SyncModule syncModule = (SyncModule) object;
          if (syncModule.getSyncTables() != null) {
            syncTables.addAll(
              syncModule.getSyncTables());
          }
        }
      }
    } catch (ClassNotFoundException cnf) {
      cnf.printStackTrace(System.out);
    } catch (InstantiationException ie) {
      ie.printStackTrace(System.out);
    } catch (IllegalAccessException iae) {
      iae.printStackTrace(System.out);
    }

    return syncTables;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  dbLookup       Description of the Parameter
   * @param  mappingsFile   Description of the Parameter
   * @param  syncFileDest   Description of the Parameter
   * @param  syncFile       Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  public void prepareSyncPackage(Connection db, Connection dbLookup,
      String mappingsFile, String syncFileDest, String syncFile) throws Exception {

    prepareSyncPackage(db, dbLookup, this.syncPackage,
        mappingsFile, syncFileDest, syncFile);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  dbLookup       Description of the Parameter
   * @param  syncPackage    Description of the Parameter
   * @param  mappingsFile   Description of the Parameter
   * @param  syncFileDest   Description of the Parameter
   * @param  syncFile       Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  public void prepareSyncPackage(Connection db, Connection dbLookup, SyncPackage syncPackage,
      String mappingsFile, String syncFileDest, String syncFile) throws Exception {
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //Set sync package status
      syncPackage.setStatusId(SyncPackage.START);
      syncPackage.update(db);

      SyncPackageDataList syncItemList = new SyncPackageDataList();
      syncItemList.setPackageId(syncPackage.getId());
      syncItemList.buildList(db);

      ArrayList dataRecords = new ArrayList();

      Iterator syncItems = syncItemList.iterator();
      while (syncItems.hasNext()) {
        SyncPackageData syncPackageData = (SyncPackageData) syncItems.next();
        SyncTable syncTable = new SyncTable(db, syncPackageData.getTableId());

        DataRecord record = new DataRecord();
        record.setName(syncTable.getName());
        record.setAction(getAction(syncPackageData.getAction()));
        if (syncPackageData.getIdentityStart() > -1) {
          record.setIdentity(String.valueOf(syncPackageData.getIdentityStart()));
        }
        if (syncPackageData.getOffset() > -1) {
          record.setOffset(String.valueOf(syncPackageData.getOffset()));
        }
        if (syncPackageData.getItems() > -1) {
          record.setItems(String.valueOf(syncPackageData.getItems()));
        }

        if (syncPackage.getType() == SyncPackage.INIT) {
          if ("userList".equals(syncTable.getName())) {
            record.setIdentity("0");
            record.addField("includeDHVAdmin", "true");
          }
          if ("accountList".equals(syncTable.getName())) {
            record.setIdentity("0");
            record.addField("showMyCompany", "true");
          }
          if ("roleList".equals(syncTable.getName())) {
            record.addField("enabledState", String.valueOf(Constants.UNDEFINED));
          }
        }
        dataRecords.add(record);
      }

      //Instantiate a DataReader that can read Sync data for this client.
      CFSXMLDatabaseReader reader = new CFSXMLDatabaseReader();
      reader.setClientId(syncPackage.getClientId());
      reader.setConnection(db);
      reader.setConnectionLookup(dbLookup);
      reader.setDataRecords(dataRecords);
      reader.setLastAnchor(syncPackage.getLastAnchor());
      reader.setNextAnchor(syncPackage.getNextAnchor());
      reader.setProcessConfigFile(mappingsFile);
      reader.setRecipient(syncPackage.getRecipient());

      //Instantiate a DataWriter that writes sync data to an xml file
      CFSXMLWriter writer = new CFSXMLWriter();
      writer.setFilename(syncFileDest + syncFile);
      writer.setRecipient(syncPackage.getRecipient());

      if (reader.isConfigured() && writer.isConfigured()) {
        reader.initialize();
        reader.execute(writer);
      }
      writer.close();

      //compress the sync package file using java.util.zip compression api
      String compressedFileName = syncFile + ".zip";
      File zipFile = new File(syncFileDest + compressedFileName);
      ZipUtils.compress(syncFileDest + syncFile, zipFile, syncFile);

      SyncClient syncClient = new SyncClient(db, syncPackage.getClientId());
      //Store the compressed sync package file in the file library
      FileItem fileItem = new FileItem();
      fileItem.setLinkModuleId(Constants.SYNC_PACKAGES);
      fileItem.setLinkItemId(syncClient.getId());
      fileItem.setEnteredBy(syncClient.getUserId());
      fileItem.setModifiedBy(syncClient.getUserId());
      fileItem.setSubject("Compressed Sync Package");
      fileItem.setClientFilename(compressedFileName);
      fileItem.setFilename(compressedFileName);
      fileItem.setVersion(1.0);
      fileItem.setSize((int) new File(syncFileDest + compressedFileName).length());
      fileItem.insert(db);

      //update the sync client record
      syncClient.setPackageFileId(fileItem.getId());
      syncClient.update(db);

      //update the sync package record to reflect the new details
      syncPackage.setPackageFileId(fileItem.getId());
      syncPackage.setStatusId(SyncPackage.COMPLETE);
      syncPackage.update(db);

      //delete the original un-compressed file
      File file = new File(syncFileDest + syncFile);
      file.delete();

      if (commit) {
        db.commit();
      }
    } catch (Exception e) {
      if (commit) {
        db.rollback();
      }
      e.printStackTrace(System.out);
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   *  Gets the action attribute of the ProcessSyncPackages object
   *
   * @param  action  Description of the Parameter
   * @return         The action value
   */
  protected String getAction(int action) {
    switch (action) {
        case TransactionItem.SELECT:
          return
              DataRecord.SELECT;
        case TransactionItem.INSERT:
          return
              DataRecord.INSERT;
        case TransactionItem.UPDATE:
          return
              DataRecord.UPDATE;
        case TransactionItem.DELETE:
          return
              DataRecord.DELETE;
        case TransactionItem.SYNC:
          return
              DataRecord.SYNC;
        case TransactionItem.SYNC_DELETE:
          return
              DataRecord.SYNC_DELETE;
        case TransactionItem.GET_DATETIME:
          return
              DataRecord.GET_DATETIME;
        case TransactionItem.GET_SYSTEM_PREFERENCES:
          return
              DataRecord.GET_SYSTEM_PREFERENCES;
        case TransactionItem.GET_SYSTEM_XML_FILE:
          return
              DataRecord.GET_SYSTEM_XML_FILE;
    }
    return "";
  }
}

