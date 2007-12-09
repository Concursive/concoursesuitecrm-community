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
package org.aspcfs.apps.syncCompiler.task;

import org.aspcfs.modules.service.sync.SyncPackager;
import org.aspcfs.modules.service.sync.base.SyncPackage;
import org.aspcfs.modules.service.sync.base.SyncPackageList;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.utils.DateUtils;

import java.io.File;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    November 7, 2006
 */
public class ProcessSyncPackages {

  public final static String fs = System.getProperty("file.separator");


  /**
   *  Constructor for the ProcessSyncPackages object
   *
   * @exception  Exception  Description of the Exception
   */
  public ProcessSyncPackages() throws Exception { }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  dbLookup       Description of the Parameter
   * @param  thisSite       Description of the Parameter
   * @param  config         Description of the Parameter
   * @return                Description of the Return Value
   * @exception  Exception  Description of the Exception
   */
  public synchronized boolean process(Connection db, Connection dbLookup, Site thisSite,
      Map config) throws Exception {

    String webinfPath = "";
    String fileLibrary = "";
    
    if (config.containsKey("FILELIBRARY")) {
    	fileLibrary = (String) config.get("FILELIBRARY");
    }
    
    if (((String) config.get("FILELIBRARY")).indexOf("WEB-INF") > 0) {
      webinfPath = (String) config.get("FILELIBRARY") + ".." + fs;
      fileLibrary = (String) config.get("FILELIBRARY");
    }
    if (config.containsKey("WEB-INF")) {
      webinfPath = (String) config.get("WEB-INF");
    }
    
    String mappingsFile = webinfPath + "import-mappings.xml";
    
    //Instantiate a packager to prepare a sync package for the sync client 
    SyncPackager syncPackager = new SyncPackager(SyncPackage.SYNC_CLIENT);
    
    SyncPackageList syncPackageList = new SyncPackageList();
    syncPackageList.setStatusId(SyncPackage.IDLE);
    syncPackageList.buildList(db);

    Iterator packages = syncPackageList.iterator();
    while (packages.hasNext()) {
      SyncPackage syncPackage = (SyncPackage) packages.next();
      
      String backupDest = 
           fileLibrary + thisSite.getDatabaseName() + fs +
                "sync-packages" + fs + DateUtils.getDatePath(syncPackage.getEntered());
      File backupPath = new File(backupDest);
      if (!backupPath.exists()) {
        backupPath.mkdirs();
      }
      String backupFile = DateUtils.getFilename(syncPackage.getEntered()) + "-" + syncPackage.getId();
      
      //prepare the sync package
      syncPackager.prepareSyncPackage(
           db, dbLookup, syncPackage, mappingsFile, backupDest, backupFile);
    }
    return true;
  }
}

