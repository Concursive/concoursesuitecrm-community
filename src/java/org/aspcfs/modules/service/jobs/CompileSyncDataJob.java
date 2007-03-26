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
package org.aspcfs.modules.service.jobs;

import java.sql.Connection;
import java.util.Iterator;

import org.aspcfs.apps.syncCompiler.task.ProcessSyncPackages;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.utils.SiteUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.StatefulJob;

import com.darkhorseventures.database.ConnectionPool;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    November 7, 2006
 */
public class CompileSyncDataJob implements StatefulJob {

  /**
   *  Prepares a list of sites and triggers a Sync Package Compiler
   *
   * @param  context                    Description of the Parameter
   * @exception  JobExecutionException  Description of the Exception
   */
  public void execute(JobExecutionContext context) throws JobExecutionException {
    //TODO: Prepare a list of sites and trigger CompileSyncPackage
    SchedulerContext schedulerContext = null;
    ConnectionPool cp = null;
    Connection db = null;
    Connection dbLookup = null;
    try {
      // Prepare items from context
      schedulerContext = context.getScheduler().getContext();
      ApplicationPrefs prefs = (ApplicationPrefs) schedulerContext
          .get("ApplicationPrefs");
      cp = (ConnectionPool) schedulerContext.get("ConnectionPool");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("CompileSyncDataJob-> Checking for new jobs...");
      }
      SiteList siteList = SiteUtils.getSiteList(prefs, cp);
      Iterator i = siteList.iterator();
      while (i.hasNext()) {
        Site thisSite = (Site) i.next();
        db = cp.getConnection(thisSite.getConnectionElement());
        dbLookup = cp.getConnection(thisSite.getConnectionElement());
        ProcessSyncPackages syncPackages = new ProcessSyncPackages();
        syncPackages.process(db, dbLookup, thisSite, prefs.getPrefs());
        syncPackages = null;
        cp.free(db);
        cp.free(dbLookup);
      }
    } catch (Exception e) {
      throw new JobExecutionException(e.getMessage());
    } finally {
      if (cp != null) {
        if (db != null)
          cp.free(db);
        if (dbLookup != null) 
          cp.free(dbLookup);
      }
    }
  }
}
