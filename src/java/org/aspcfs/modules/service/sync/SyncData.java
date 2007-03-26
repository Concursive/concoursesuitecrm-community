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
import org.aspcfs.modules.service.sync.base.SyncPackage;
import org.aspcfs.modules.service.sync.base.SyncPackageData;
import org.aspcfs.modules.service.base.SyncTable;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    December 12, 2006
 */
public class SyncData {

  private final static Logger log = Logger.getLogger(org.aspcfs.modules.service.sync.SyncData.class);

  public SyncPackage syncPackage = null;

  public Timestamp lastAnchor = null;
  public Timestamp nextAnchor = null;


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  syncTable         Description of the Parameter
   * @param  action            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void insertSyncPackageData(Connection db, SyncTable syncTable, int action, HashMap values) throws SQLException {
    log.info("Adding " + syncTable.getClass().getName());
    
    int identity = 1; //default GUID starts at 1
    
    try {
      if (values != null) {
        String elementName = syncTable.getName();
        if (values.containsKey(elementName + "-identity")) {
          String value = (String) values.get(elementName + "-identity");
          if (value != null) {
            identity = Integer.parseInt(value);
          }
        }
      }
    } catch (NumberFormatException nfe) {
      nfe.printStackTrace(System.out);
    }
    
    if (syncPackage != null) {
      SyncPackageData syncPackageData = new SyncPackageData(syncPackage, syncTable);
      syncPackageData.setAction(action);
      syncPackageData.setLastAnchor(this.lastAnchor);
      syncPackageData.setNextAnchor(this.nextAnchor);
      syncPackageData.setIdentityStart(identity);
      syncPackageData.insert(db);
    }
  }
}

