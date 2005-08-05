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
package org.aspcfs.modules.reports.jobs;

import com.darkhorseventures.database.ConnectionPool;
import org.aspcfs.apps.reportRunner.task.ProcessJasperCleanup;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.utils.SiteUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.StatefulJob;

import java.sql.Connection;
import java.util.Iterator;


/**
 * A job for cleaning up processed Centric CRM reports.  Since this class is stateful,
 * only one instance of this Job can run at a time.
 *
 * @author mrajkowski
 * @version $Id$
 * @created Jun 22, 2005
 */

public class ReportCleanupJob implements StatefulJob {

  public void execute(JobExecutionContext context) throws JobExecutionException {
    SchedulerContext schedulerContext = null;
    ConnectionPool cp = null;
    Connection db = null;
    try {
      // Prepare items from context
      schedulerContext = context.getScheduler().getContext();
      ApplicationPrefs prefs = (ApplicationPrefs) schedulerContext.get(
          "ApplicationPrefs");
      cp = (ConnectionPool) schedulerContext.get("ConnectionPool");
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ReportCleanupJob-> Cleaning up outdated reports...");
      }
      SiteList siteList = SiteUtils.getSiteList(prefs, cp);
      Iterator i = siteList.iterator();
      while (i.hasNext()) {
        Site thisSite = (Site) i.next();
        db = cp.getConnection(thisSite.getConnectionElement());
        ProcessJasperCleanup reportCleanup = new ProcessJasperCleanup(
            db, thisSite, prefs.getPrefs());
        reportCleanup = null;
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
