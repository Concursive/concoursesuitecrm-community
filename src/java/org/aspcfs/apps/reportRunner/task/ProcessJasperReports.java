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
 *  Class to compile and generate to PDF a JasperReport
 *
 *@author     matt rajkowski
 *@created    October 3, 2003
 *@version    $Id$
 */
public class ProcessJasperReports {

  public final static String fs = System.getProperty("file.separator");


  /**
   *  Constructor for the ProcessJasperReports object
   *
   *@param  db             Description of the Parameter
   *@param  thisSite       Description of the Parameter
   *@param  config         Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public ProcessJasperReports(Connection db, Site thisSite, HashMap config) throws Exception {
    //Load the report queue for this site, unprocessed only
    ReportQueueList queue = new ReportQueueList();
    queue.setSortAscending(true);
    queue.setUnprocessedOnly(true);
    queue.buildList(db);
    //Iterate the list
    Iterator list = queue.iterator();
    while (list.hasNext()) {
      ReportQueue thisQueue = (ReportQueue) list.next();
      if (queue.lockReport(thisQueue, db)) {
        try {
          //Load from the repository, save to the user's site
          String destDir =
              (String) config.get("FILELIBRARY") +
              thisSite.getDatabaseName() + fs +
              "reports-queue" + fs +
              DateUtils.getDatePath(thisQueue.getEntered());
          File destPath = new File(destDir);
          destPath.mkdirs();
          String filename = DateUtils.getFilename(thisQueue.getEntered()) + "-" + thisQueue.getEnteredBy();
          long size = processReport(
              thisQueue,
              db,
              (String) config.get("FILELIBRARY") + "reports" + fs,
              destDir + filename);
          thisQueue.setFilename(filename);
          thisQueue.setSize(size);
          thisQueue.setStatus(ReportQueue.STATUS_PROCESSED);
        } catch (Exception e) {
          thisQueue.setStatus(ReportQueue.STATUS_ERROR);
          e.printStackTrace(System.out);
        } finally {
          thisQueue.updateStatus(db);
        }
      }
    }
  }


  /**
   *  For the specified report queue, gets the report and performs the
   *  JasperReport to PDF
   *
   *@param  thisQueue      Description of the Parameter
   *@param  db             Description of the Parameter
   *@param  path           Description of the Parameter
   *@param  destFilename   Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  private static long processReport(ReportQueue thisQueue, Connection db, String path, String destFilename) throws Exception {
    Report thisReport = new Report(db, thisQueue.getReportId());
    //Determine the path and load JasperReport
    JasperReport jasperReport = JasperReportUtils.getReport(path + thisReport.getFilename());
    //Populate the criteria
    QueueCriteriaList criteria = new QueueCriteriaList();
    criteria.setQueueId(thisQueue.getId());
    criteria.buildList(db);
    //Export the pdf to fileLibrary for this site
    JasperRunManager.runReportToPdfFile(path + thisReport.getFilename() + ".jasper", destFilename, criteria.getParameters(jasperReport), db);
    //Determine the size
    File reportFile = new File(destFilename);
    if (reportFile.exists()) {
      return reportFile.length();
    } else {
      return -1;
    }
  }

}

