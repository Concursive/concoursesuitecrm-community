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
package org.aspcfs.modules.pipeline.jobs;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.StatefulJob;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;


/**
 * A daily job for resetting expired graph data.
 *
 * @author mrajkowski
 * @version $Id$
 * @created Jun 22, 2005
 */

public class ResetGraphDataJob implements StatefulJob {

  public static String fs = System.getProperty("file.separator");

  public void execute(JobExecutionContext context) throws JobExecutionException {
    SchedulerContext schedulerContext = null;
    try {
      // Prepare items from context
      schedulerContext = context.getScheduler().getContext();
      String applicationPath = schedulerContext.getString("ApplicationPath");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ResetGraphDataJob-> Expiring graph data...");
      }
      // For each system that is cached, invalidate the data
      Hashtable globalStatus = (Hashtable) schedulerContext.get(
          "SystemStatus");
      Iterator systemIterator = globalStatus.values().iterator();
      while (systemIterator.hasNext()) {
        SystemStatus thisSystem = (SystemStatus) systemIterator.next();
        UserList thisList = thisSystem.getHierarchyList();
        UserList fullChildList = null;
        Iterator j = thisList.iterator();
        while (j.hasNext()) {
          User thisUser = (User) j.next();
          UserList shortChildList = thisUser.getShortChildList();
          fullChildList = thisUser.getFullChildList(
              shortChildList, new UserList());
          Iterator k = fullChildList.iterator();
          while (k.hasNext()) {
            User indUser = (User) k.next();
            indUser.setIsValid(false, true);
            indUser.setIsValidLead(false, true);
            indUser.setRevenueIsValid(false, true);
          }
        }
      }
      // Delete the graphs since they are no longer used
      File graphFolder = new File(applicationPath + "graphs" + fs);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ResetGraphDataJob-> Directory: " + graphFolder.getPath());
      }
      if (graphFolder.isDirectory()) {
        File[] files = graphFolder.listFiles();
        if (files != null) {
          for (int i = 0; i < files.length; i++) {
            File thisFile = files[i];
            thisFile.delete();
          }
        }
      }
    } catch (Exception e) {
      throw new JobExecutionException(e.getMessage());
    }
  }
}
