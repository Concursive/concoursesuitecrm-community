/*
 *  Copyright(c) 2005 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.admin.jobs;

import com.darkhorseventures.database.ConnectionPool;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.CFSDatabaseReader;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMapList;
import org.aspcfs.apps.transfer.writer.CFSXMLWriter;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.utils.SiteUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.StatefulJob;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created February 9, 2006
 */
public class BackupCentricJob implements StatefulJob {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @throws JobExecutionException Description of the Exception
   */
  public void execute(JobExecutionContext context) throws JobExecutionException {
    SchedulerContext schedulerContext = null;
    ConnectionPool cp = null;
    Connection db = null;
    Map config = null;

    try {
      // Prepare items from context
      schedulerContext = context.getScheduler().getContext();
      ApplicationPrefs prefs = (ApplicationPrefs) schedulerContext.get(
          "ApplicationPrefs");
      cp = (ConnectionPool) schedulerContext.get("ConnectionPool");
      config = prefs.getPrefs();

      if (System.getProperty("DEBUG") != null) {
        System.out.println("BackupJob-> Starting system backup...");
      }

      SiteList siteList = SiteUtils.getSiteList(prefs, cp);
      Iterator i = siteList.iterator();
      while (i.hasNext()) {
        Site thisSite = (Site) i.next();
        db = cp.getConnection(thisSite.getConnectionElement());

        //TODO: This connection needs to be configured to stay open until
        //the operation is complete

        //reader writer config and backup files
        String objectMappings = null;
        String backupXML = null;

        if (config.containsKey("WEB-INF")) {
          objectMappings = (String) config.get("WEB-INF") + "cfs-import-mappings.xml";
          backupXML = (String) config.get("WEB-INF") + "cfs-backup-data.xml";
        }

        //Instantiate a Database Reader
        CFSDatabaseReader reader = new CFSDatabaseReader();
        ArrayList modules = new ArrayList();
        PropertyMapList mappings = new PropertyMapList(objectMappings, modules);

        //Instantiate an Writer to backup data
        CFSXMLWriter writer = new CFSXMLWriter();
        writer.setFilename(backupXML);

        reader.executeJob(db, writer);

        //Backup fileLibrary

        cp.free(db);
        db = null;
      }
    } catch (Exception e) {
      throw new JobExecutionException(e.getMessage());
    } finally {
      if (cp != null && db != null) {
        cp.free(db);
      }
    }
  }
}

