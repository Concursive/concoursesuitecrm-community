/*
 *  Copyright(c) 2005 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.modules.admin.jobs;

import com.darkhorseventures.database.ConnectionPool;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.utils.SiteUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.StatefulJob;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;
import java.util.Iterator;


/**
 * A job for deleting records that have been marked as Trash.
 *
 * @author Kailash
 * @version $Id: LoggerJob.java $
 * @created Jun 18, 2006
 */

public class LoggerJob implements StatefulJob {

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
      SiteList siteList = SiteUtils.getSiteList(prefs, cp);
      Iterator i = siteList.iterator();
      while (i.hasNext()) {
        Site thisSite = (Site) i.next();
        if (thisSite != null) {
          db = cp.getConnection(thisSite.getConnectionElement());
          // WebPageAccess
					Vector webPageAccessLogList = (Vector) schedulerContext.get("webPageAccessLog");
					if (webPageAccessLogList.size() > 0) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println(
                "LoggerJob-> Logging site access at " + (new Timestamp(
                    Calendar.getInstance().getTimeInMillis()).toString()));
            }
					Vector webProductAccessLogList = (Vector) schedulerContext.get("webProductAccessLog");
					if (webProductAccessLogList.size() > 0) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println(
                "LoggerJob-> Logging product access at " + (new Timestamp(
                    Calendar.getInstance().getTimeInMillis()).toString()));
            }
					Vector webProductEmailLogList = (Vector) schedulerContext.get("webProductEmailLog");
					if (webProductEmailLogList.size() > 0) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println(
                "LoggerJob-> Logging product email access at " + (new Timestamp(
                    Calendar.getInstance().getTimeInMillis()).toString()));
            }
          cp.free(db);
          db = null;
					}
					}
					}
        }
      }
    }
catch (Exception e) {
      throw new JobExecutionException(e.getMessage());
    } finally {
      if (cp != null && db != null) {
        cp.free(db);
      }
    }
  }
}
