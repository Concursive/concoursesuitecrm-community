package org.aspcfs.apps.reportRunner.task;

import java.sql.*;
import java.util.*;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.reports.base.*;
import org.aspcfs.utils.JasperReportUtils;
import dori.jasper.engine.*;
import dori.jasper.engine.util.*;
import java.io.File;
import org.aspcfs.utils.DateUtils;

/**
 *  Class to delete old reports from the database and any files associated
 *
 *@author     matt rajkowski
 *@created    November 11, 2003
 *@version    $Id$
 */
public class ProcessJasperCleanup {

  public final static String fs = System.getProperty("file.separator");


  /**
   *@param  db             Description of the Parameter
   *@param  thisSite       Description of the Parameter
   *@param  config         Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public ProcessJasperCleanup(Connection db, Site thisSite, HashMap config) throws Exception {
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
      thisQueue.delete(db, 
          (String) config.get("FILELIBRARY") +
          thisSite.getDatabaseName() + fs +
          "reports-queue" + fs +
          DateUtils.getDatePath(thisQueue.getEntered()) +
          thisQueue.getFilename());
    }
  }
}

