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
package org.aspcfs.controller;

import org.aspcfs.modules.admin.jobs.DeleteTrashJob;
import org.aspcfs.modules.admin.jobs.NotifierJob;
import org.aspcfs.modules.admin.jobs.UserCleanupJob;
import org.aspcfs.modules.pipeline.jobs.ResetGraphDataJob;
import org.aspcfs.modules.reports.jobs.ReportCleanupJob;
import org.aspcfs.modules.reports.jobs.ReportRunnerJob;
import org.quartz.*;

import java.text.ParseException;
import java.util.Date;

/**
 * Adds Centric CRM related jobs to the scheduler.
 *
 * @author mrajkowski
 * @version $Id$
 * @created Jun 20, 2005
 */

public class ScheduledJobs {

  public static void addJobs(Scheduler scheduler) throws SchedulerException, ParseException {
    if (1 == 1) {
      // Notifier
      // Execute every 5 minutes, starting in 5 minutes
      JobDetail job = new JobDetail(
          "notifier", Scheduler.DEFAULT_GROUP, NotifierJob.class);
      long startTime = System.currentTimeMillis() + (5L * 60L * 1000L);
      SimpleTrigger trigger = new SimpleTrigger(
          "notifier",
          Scheduler.DEFAULT_GROUP,
          new Date(startTime),
          null,
          SimpleTrigger.REPEAT_INDEFINITELY,
          5L * 60L * 1000L);
      scheduler.scheduleJob(job, trigger);
    }

    if (1 == 1) {
      // Report runner
      // Execute every 3 minutes, starting in 5 minutes
      // This job is also executed immediately when a new report is added
      JobDetail job = new JobDetail(
          "reportRunner", Scheduler.DEFAULT_GROUP, ReportRunnerJob.class);
      long startTime = System.currentTimeMillis() + (5L * 60L * 1000L);
      SimpleTrigger trigger = new SimpleTrigger(
          "reportRunner",
          Scheduler.DEFAULT_GROUP,
          new Date(startTime),
          null,
          SimpleTrigger.REPEAT_INDEFINITELY,
          3L * 60L * 1000L);
      scheduler.scheduleJob(job, trigger);
    }

    if (1 == 1) {
      // Report cleanup
      // Execute every 8 hours, starting in 4 minutes
      JobDetail job = new JobDetail(
          "reportCleanup", Scheduler.DEFAULT_GROUP, ReportCleanupJob.class);
      long startTime = System.currentTimeMillis() + (4L * 60L * 1000L);
      SimpleTrigger trigger = new SimpleTrigger(
          "reportCleanup",
          Scheduler.DEFAULT_GROUP,
          new Date(startTime),
          null,
          SimpleTrigger.REPEAT_INDEFINITELY,
          8L * 60L * 60L * 1000L);
      scheduler.scheduleJob(job, trigger);
    }

    if (1 == 1) {
      // User cleanup
      // Execute every hour, starting in 20 minutes
      JobDetail job = new JobDetail(
          "userCleanup", Scheduler.DEFAULT_GROUP, UserCleanupJob.class);
      long startTime = System.currentTimeMillis() + (20L * 60L * 1000L);
      SimpleTrigger trigger = new SimpleTrigger(
          "userCleanup",
          Scheduler.DEFAULT_GROUP,
          new Date(startTime),
          null,
          SimpleTrigger.REPEAT_INDEFINITELY,
          1L * 60L * 60L * 1000L);
      scheduler.scheduleJob(job, trigger);
    }

    if (1 == 1) {
      // Expired graph cleanup
      // Execute at 12am every day
      JobDetail job = new JobDetail(
          "resetGraphData", Scheduler.DEFAULT_GROUP, ResetGraphDataJob.class);
      CronTrigger trigger = new CronTrigger(
          "resetGraphData",
          Scheduler.DEFAULT_GROUP,
          "0 0 0 * * ?");
      scheduler.scheduleJob(job, trigger);
    }

    if (1 == 1) {
      // Remove trashed records from database
      // Execute at 10pm every day
      JobDetail job = new JobDetail(
          "deleteTrash", Scheduler.DEFAULT_GROUP, DeleteTrashJob.class);
      CronTrigger trigger = new CronTrigger(
          "deleteTrash",
          Scheduler.DEFAULT_GROUP,
          "0 0 22 * * ?");
      scheduler.scheduleJob(job, trigger);
    }

  }
}
