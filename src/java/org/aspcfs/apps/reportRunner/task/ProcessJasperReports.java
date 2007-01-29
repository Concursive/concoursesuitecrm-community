/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.apps.reportRunner.task;
import org.aspcfs.modules.reports.base.QueueCriteriaList;
import org.aspcfs.modules.reports.base.Report;
import org.aspcfs.modules.reports.base.ReportQueue;
import org.aspcfs.modules.reports.base.ReportQueueList;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.JasperReportUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *  Class to compile and generate to PDF a JasperReport
 *
 * @author     matt rajkowski
 * @version    $Id: ProcessJasperReports.java,v 1.6 2004/06/29 14:08:22
 *      mrajkowski Exp $
 * @created    October 3, 2003
 */
public class ProcessJasperReports {

  public final static String fs = System.getProperty("file.separator");
  public final static String CENTRIC_DICTIONARY = "CENTRIC_DICTIONARY";
  public final static String SCRIPT_DB_CONNECTION = "SCRIPT_DB_CONNECTION";


  /**
   *  Constructor for the ProcessJasperReports object
   *
   * @param  db             Description of the Parameter
   * @param  thisSite       Description of the Parameter
   * @param  config         Description of the Parameter
   * @param  dictionary     Description of the Parameter
   * @param  scriptdb       Description of the Parameter
   * @exception  Exception  Description of the Exception
   * @throws  Exception     Description of the Exception
   */
  public ProcessJasperReports(Connection db, Connection scriptdb, Site thisSite, Map config, Map dictionary) throws Exception {
    //Load the report queue for this site, unprocessed only
    ReportQueueList queue = new ReportQueueList();
    queue.setSortAscending(true);
    queue.setUnprocessedOnly(true);
    queue.buildList(db);
    //Iterate the list
    Iterator list = queue.iterator();
    while (list.hasNext()) {
      ReportQueue thisQueue = (ReportQueue) list.next();
      if (ReportQueueList.lockReport(thisQueue, db)) {
        try {
          String reportDir = "";
          String fontPath = "";
          if (((String) config.get("FILELIBRARY")).indexOf("WEB-INF") > 0) {
            reportDir = (String) config.get("FILELIBRARY") + ".." + fs + "reports" + fs;
            fontPath = (String) config.get("FILELIBRARY") + ".." + fs + "fonts" + fs;
          }
          if (config.containsKey("WEB-INF")) {
            reportDir = (String) config.get("WEB-INF") + "reports" + fs;
            fontPath = (String) config.get("WEB-INF") + "fonts" + fs;
          }

          if (dictionary == null) {
            dictionary = new LinkedHashMap();
          }

          //Load from the repository, save to the user's site
          String destDir =
              (String) config.get("FILELIBRARY") +
              thisSite.getDatabaseName() + fs +
              "reports-queue" + fs +
              DateUtils.getDatePath(thisQueue.getEntered());
          File destPath = new File(destDir);
          destPath.mkdirs();
          String filename = DateUtils.getFilename() + "-" + thisQueue.getId();
          long size = processReport(
              thisQueue,
              db,
              scriptdb,
              reportDir,
              destDir + filename,
              fontPath,
              dictionary);
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
   *  Description of the Method
   *
   * @param  thisQueue          Description of the Parameter
   * @param  db                 Description of the Parameter
   * @param  path               Description of the Parameter
   * @param  destFilename       Description of the Parameter
   * @param  localizationPrefs  Description of the Parameter
   * @param  scriptdb           Description of the Parameter
   * @param  fontPath           Description of the Parameter
   * @return                    Description of the Return Value
   * @throws  Exception         Description of the Exception
   */
  private static long processReport(ReportQueue thisQueue, Connection db, Connection scriptdb, String path, String destFilename, String fontPath, Map localizationPrefs) throws Exception {
    Report thisReport = new Report(db, thisQueue.getReportId());
    //Determine the path and load JasperReport
    JasperReport jasperReport = JasperReportUtils.getReport(
        path + thisReport.getFilename());
    //Populate the criteria
    QueueCriteriaList criteria = new QueueCriteriaList();
    criteria.setQueueId(thisQueue.getId());
    criteria.buildList(db);
    Map parameters = criteria.getParameters(jasperReport, path);
    parameters.put(CENTRIC_DICTIONARY, localizationPrefs);
    parameters.put(SCRIPT_DB_CONNECTION, scriptdb);
    if (parameters.containsKey("configure_hierarchy_list")) {
      configureHierarchyList(db, parameters);
    }
    //Modify pdf font and encoding properties of all text fields if not default language
    String language = (String) parameters.get("language") + "_" + (String) parameters.get(
        "country");
    JasperReportUtils.modifyFontProperties(
        jasperReport, path, fontPath, language);
    //Export the pdf to fileLibrary for this site
    /*
     *  JasperRunManager.runReportToPdfFile(
     *  path +
     *  thisReport.getFilename().substring(0, thisReport.getFilename().lastIndexOf(".xml")) + ".jasper",
     *  destFilename,
     *  parameters, db);
     */
    byte[] bytes = JasperRunManager.runReportToPdf(
        jasperReport, parameters, db);
    File reportFile = new File(destFilename);
    FileOutputStream destination = new FileOutputStream(reportFile);
    destination.write(bytes, 0, bytes.length);
    //clean up
    if (parameters.containsKey("configure_hierarchy_list")) {
      removeHierarchyList(db, parameters);
    }
    //Determine the size
    if (reportFile.exists()) {
      return reportFile.length();
    } else {
      return -1;
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  parameters        Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  private static void configureHierarchyList(Connection db, Map parameters) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ProcessJasperReports-> configureHierarchyList: temp table");
    }
    StringBuffer sql = new StringBuffer();
    int typeId = DatabaseUtils.getType(db);
    if (typeId == DatabaseUtils.POSTGRESQL) {
      sql.append(
          "CREATE TEMPORARY TABLE search_order (" +
          " id INT, " +
          " user_id INT " +
          ")");
    } else if (typeId == DatabaseUtils.DB2) {
      sql.append(
          "DECLARE GLOBAL TEMPORARY TABLE search_order (" +
          " id INT, " +
          " user_id INT " + 
          ")" + 
          "ON COMMIT PRESERVE ROWS NOT LOGGED");
    } else if (typeId == DatabaseUtils.MSSQL) {
      sql.append(
          "CREATE TABLE #search_order (" +
          " id INT, " +
          " user_id INT " +
          ")");
    } else {
      throw new SQLException("Database Support Missing for Report");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.execute();
    sql = new StringBuffer();
    sql.append(
        "INSERT INTO ").append(
        DatabaseUtils.getTempTableName(db, "search_order")).append(
        " (id, user_id) VALUES (?, ?)");
    pst = db.prepareStatement(sql.toString());
    int counter = 0;
    String userIdRange = (String) parameters.get("userid_range");
    if (userIdRange != null) {
      StringTokenizer st =
          new StringTokenizer(userIdRange, ",");
      while (st.hasMoreTokens()) {
        String userId = st.nextToken().trim();
        pst.setInt(1, ++counter);
        pst.setInt(2, Integer.parseInt(userId));
        pst.execute();
      }
    }
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  parameters        Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  private static void removeHierarchyList(Connection db, Map parameters) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DROP TABLE " + DatabaseUtils.getTempTableName(db, "search_order"));
    pst.execute();
    pst.close();
  }
}

