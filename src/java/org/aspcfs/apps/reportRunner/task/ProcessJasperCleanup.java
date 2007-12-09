/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.apps.reportRunner.task;

import org.aspcfs.modules.reports.base.ReportQueue;
import org.aspcfs.modules.reports.base.ReportQueueList;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.utils.DateUtils;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

/**
 * Class to delete old reports from the database and any files associated
 *
 * @author matt rajkowski
 * @version $Id: ProcessJasperCleanup.java,v 1.1 2003/11/12 14:40:10
 *          mrajkowski Exp $
 * @created November 11, 2003
 */
public class ProcessJasperCleanup {

  public final static String fs = System.getProperty("file.separator");


  /**
   * @param db       Description of the Parameter
   * @param thisSite Description of the Parameter
   * @param config   Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public ProcessJasperCleanup(Connection db, Site thisSite, Map config) throws Exception {
    //Only want reports older than 24 hours
    Calendar rangeEnd = Calendar.getInstance();
    rangeEnd.add(Calendar.DAY_OF_MONTH, -1);
    //Load the report queue for this site, processed only, whether successful or not
    ReportQueueList queue = new ReportQueueList();
    queue.setSortAscending(true);
    queue.setProcessedOnly(true);
    queue.setRangeEnd(new java.sql.Timestamp(rangeEnd.getTimeInMillis()));
    queue.buildList(db);
    //Iterate the list
    Iterator list = queue.iterator();
    while (list.hasNext()) {
      ReportQueue thisQueue = (ReportQueue) list.next();
      thisQueue.delete(
          db,
          (String) config.get("FILELIBRARY") +
          thisSite.getDatabaseName() + fs +
          "reports-queue" + fs +
          DateUtils.getDatePath(thisQueue.getEntered()) +
          thisQueue.getFilename());
    }
  }
}

