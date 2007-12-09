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
package org.aspcfs.modules.sync.utils;

import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.offline.base.OfflinePrefs;
import org.aspcfs.utils.CRMConnection;

/**
 *  Description of the Class
 *
 * @author     holub
 * @created    Feb 20, 2007
 * @version    $Id: Exp $
 */
public class SyncUtils {
  static Logger log = Logger.getLogger(org.aspcfs.modules.sync.utils.SyncUtils.class);


  /**
   *  Gets the syncConflict attribute of the SyncUtils class
   *
   * @param  applicationPrefs  Description of the Parameter
   * @return                   The syncConflict value
   */
  public static boolean isSyncConflict(ApplicationPrefs applicationPrefs) {
    return isOfflineMode(applicationPrefs) && applicationPrefs.get("sync.state") != null
         && applicationPrefs.get("sync.state").toLowerCase().equals("conflict");
  }


  /**
   *  Gets the offlineMode attribute of the SyncUtils class
   *
   * @param  applicationPrefs  Description of the Parameter
   * @return                   The offlineMode value
   */
  public static boolean isOfflineMode(ApplicationPrefs applicationPrefs) {
    return Boolean.parseBoolean(applicationPrefs.get("OFFLINE_MODE"));
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   */
  public static void checkOfflineState(ServletContext context) {
    ApplicationPrefs appPrefs = (ApplicationPrefs) context.getAttribute("applicationPrefs");

    if (isOfflineMode(appPrefs)) {
      CRMConnection crm = new CRMConnection();
      crm.setUrl(OfflinePrefs.getUrl());
      crm.setUsername(OfflinePrefs.getUserName());
      crm.setCode(OfflinePrefs.getCode());
      crm.setClientId(OfflinePrefs.getClientId());

      // Get the server's current date/time for this sync session's nextAnchor
      // info
      DataRecord record = new DataRecord();
      record.setName("syncClient");
      record.setAction("syncStart");
      crm.setLastAnchor(appPrefs.get("sync.anchor"));
      crm.load(record);

      if (crm.getStatus() == 1) {
        appPrefs.add("sync.state", "conflict");
        appPrefs.save();
      }
    }
  }
}
