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

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.reports.base.QueueCriteriaList;
import org.aspcfs.modules.reports.base.Report;
import org.aspcfs.modules.reports.base.ReportQueue;
import org.aspcfs.modules.reports.base.ReportQueueList;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.utils.*;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Class to compile and generate to PDF a JasperReport
 *
 * @author matt rajkowski
 * @version $Id: ProcessJasperReports.java,v 1.6 2004/06/29 14:08:22
 *          mrajkowski Exp $
 * @created October 3, 2003
 */
public class ProcessJasperReports {

  public final static String fs = System.getProperty("file.separator");
  public final static String CENTRIC_DICTIONARY = "CENTRIC_DICTIONARY";
  public final static String SCRIPT_DB_CONNECTION = "SCRIPT_DB_CONNECTION";


  /**
   * Constructor for the ProcessJasperReports object
   *
   * @param db         Description of the Parameter
   * @param thisSite   Description of the Parameter
   * @param config     Description of the Parameter
   * @param dictionary Description of the Parameter
   * @param scriptdb   Description of the Parameter
   * @throws Exception Description of the Exception
   * @throws Exception Description of the Exception
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
          // To Email
          if (thisQueue.getEmail()) {
            User user = new User();
            user.setBuildContact(true);
            user.setBuildContactDetails(true);
            user.buildRecord(db, thisQueue.getEnteredBy());
            SMTPMessage message = new SMTPMessage();
            message.setHost((String) config.get("MAILSERVER"));
            message.setFrom((String) config.get("EMAILADDRESS"));
            message.addReplyTo((String) config.get("EMAILADDRESS"));
            message.addTo(user.getContact().getPrimaryEmailAddress());
            thisQueue.buildReport(db);
            message.setType("text/html");
            String dbName = thisSite.getDatabaseName();
            String templateFilePath = (String) config.get("FILELIBRARY") + fs + dbName + fs + "templates_" + thisSite.getLanguage() + ".xml";
            if (!FileUtils.fileExists(templateFilePath)) {
              templateFilePath = (String) config.get("FILELIBRARY") + fs + dbName + fs + "templates_en_US.xml";
            }
            File configFile = new File(templateFilePath);
            XMLUtils xml = new XMLUtils(configFile);
            Element mappings = xml.getFirstChild("mappings");
            // Construct the subject
            Template messageSubject = new Template();
            messageSubject.setText(
                XMLUtils.getNodeText(
                    XMLUtils.getElement(
                        mappings, "map", "id", "report.email.subject")));
            String subject = messageSubject.getParsedText();
            message.setSubject(subject);
            // Construct the body
            Template messageBody = new Template();
            messageBody.setText(
                XMLUtils.getNodeText(
                    XMLUtils.getElement(
                        mappings, "map", "id", "report.alert.email.body")));
            String body = messageBody.getParsedText();

            switch (thisQueue.getOutputTypeConstant()) {
              case ReportQueue.REPORT_TYPE_HTML:
                // Just place the HTML in the email body, not as an
                // attachment...
                message.setBody(body + StringUtils.loadText(destDir + filename));
                break;
              case ReportQueue.REPORT_TYPE_CSV:
                // Attach the CSV
                message.setBody(body);
                message.addFileAttachment(destDir + filename, filename + ".csv");
                break;
              case ReportQueue.REPORT_TYPE_PDF:
                //Attach the PDF
                message.setBody(body);
                message.addFileAttachment(destDir + filename, filename + ".pdf");
                break;
              case ReportQueue.REPORT_TYPE_EXCEL:
                //Attach the PDF
                message.setBody(body);
                message.addFileAttachment(destDir + filename, filename + ".xls");
                break;
            }
            message.send();
          }
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
   * Description of the Method
   *
   * @param thisQueue         Description of the Parameter
   * @param db                Description of the Parameter
   * @param path              Description of the Parameter
   * @param destFilename      Description of the Parameter
   * @param localizationPrefs Description of the Parameter
   * @param scriptdb          Description of the Parameter
   * @param fontPath          Description of the Parameter
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
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
    JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, db);
    File reportFile = new File(destFilename);
    switch (thisQueue.getOutputTypeConstant()) {
      case ReportQueue.REPORT_TYPE_HTML:
        JRHtmlExporter exporterHTML = new JRHtmlExporter();
        exporterHTML.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporterHTML.setParameter(JRExporterParameter.OUTPUT_FILE, reportFile);
        exporterHTML.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        exporterHTML.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
        exporterHTML.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporterHTML.exportReport();
        break;
      case ReportQueue.REPORT_TYPE_CSV:
        JRCsvExporter exporterCSV = new JRCsvExporter();
        exporterCSV.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporterCSV.setParameter(JRExporterParameter.OUTPUT_FILE, reportFile);
        exporterCSV.exportReport();
        break;
      case ReportQueue.REPORT_TYPE_EXCEL:
        JRXlsExporter exporterXls = new JRXlsExporter();
        exporterXls.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE, reportFile);
        exporterXls.exportReport();
        break;
      default:
        byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, db);
        if (reportFile.exists()) {
          return reportFile.length();
        }
        FileOutputStream destination = new FileOutputStream(reportFile);
        destination.write(bytes, 0, bytes.length);
        break;
    }
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
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param parameters Description of the Parameter
   * @throws SQLException Description of the Exception
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
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param parameters Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private static void removeHierarchyList(Connection db, Map parameters) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DROP TABLE " + DatabaseUtils.getTempTableName(db, "search_order"));
    pst.execute();
    pst.close();
  }
}

