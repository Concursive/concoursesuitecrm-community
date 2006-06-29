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
import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.ProjectList;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.modules.website.base.WebPageAccessLog;
import org.aspcfs.modules.website.base.WebProductAccessLog;
import org.aspcfs.modules.website.base.WebProductEmailLog;

import org.aspcfs.utils.SiteUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.StatefulJob;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
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
      Hashtable systemStatusList = (Hashtable) schedulerContext.get(
          "SystemStatus");
      SiteList siteList = SiteUtils.getSiteList(prefs, cp);
      Iterator i = siteList.iterator();
      while (i.hasNext()) {
        Site thisSite = (Site) i.next();
        SystemStatus thisSystem = (SystemStatus) systemStatusList.get(
            thisSite.getConnectionElement().getUrl());
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
            Iterator itr = webPageAccessLogList.iterator();
						PreparedStatement pst = WebPageAccessLog.prepareInsert(db);
						while (itr.hasNext()) {
							WebPageAccessLog webPageAccessLog = (WebPageAccessLog)itr.next();
							webPageAccessLog.insert(db);
							itr.remove();
						}
						WebPageAccessLog.closeInsert(pst);
					}

					Vector webProductAccessLogList = (Vector) schedulerContext.get("webProductAccessLog");
					if (webProductAccessLogList.size() > 0) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println(
                "LoggerJob-> Logging product access at " + (new Timestamp(
                    Calendar.getInstance().getTimeInMillis()).toString()));
            }
            Iterator itr = webProductAccessLogList.iterator();
						PreparedStatement pst = WebProductAccessLog.prepareInsert(db);
						while (itr.hasNext()) {
							WebProductAccessLog webProductAccessLog = (WebProductAccessLog)itr.next();
							webProductAccessLog.insertData(pst);
							itr.remove();
						}
						WebProductAccessLog.closeInsert(pst);
					}

					Vector webProductEmailLogList = (Vector) schedulerContext.get("webProductEmailLog");
					if (webProductEmailLogList.size() > 0) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println(
                "LoggerJob-> Logging product email access at " + (new Timestamp(
                    Calendar.getInstance().getTimeInMillis()).toString()));
            }
            Iterator itr = webProductEmailLogList.iterator();
						PreparedStatement pst = WebProductEmailLog.prepareInsert(db);
						while (itr.hasNext()) {
							WebProductEmailLog webProductEmailLog = (WebProductEmailLog)itr.next();
							webProductEmailLog.insertData(pst);
							itr.remove();
						}
						WebProductEmailLog.closeInsert(pst);
					}
          cp.free(db);
          db = null;
        }
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
