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
package org.aspcfs.utils;

import dori.jasper.engine.*;
import dori.jasper.engine.util.*;
import java.io.*;
import java.sql.*;
import java.util.HashMap;

/**
 *  Methods to work with JasperReports
 *
 *@author     matt rajkowski
 *@created    October 3, 2003
 *@version    $Id: JasperReportUtils.java,v 1.4.34.1 2004/04/29 18:32:29
 *      kbhoopal Exp $
 */
public class JasperReportUtils {

  /**
   *  Loads the specified JasperReport, if the report has not been compiled then
   *  that happens first. Only one report can be compiled at a time since a
   *  duplicate request could ask to compile the same file.
   *
   *@param  filename       Description of the Parameter
   *@return                The report value
   *@exception  Exception  Description of the Exception
   */
  public static synchronized JasperReport getReport(String filename) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("JasperReportUtils-> Checking report: " + filename);
    }
    System.setProperty("jasper.reports.compiler.class",
        "dori.jasper.engine.design.JRBshCompiler");
    //Check to see if the .jasper exists
    String jasperFilename = filename.substring(0, filename.lastIndexOf(".xml"));
    File jasperFile = new File(jasperFilename + ".jasper");
    if (!jasperFile.exists()) {
      //The XML should exist so compile that
      File xmlFile = new File(filename);
      if (!xmlFile.exists()) {
        return null;
      }
      JasperCompileManager.compileReportToFile(xmlFile.getPath(), jasperFile.getPath());
    }
    if (!jasperFile.exists()) {
      return null;
    }
    //Load and return the report
    return (JasperReport) JRLoader.loadObject(jasperFile.getPath());
  }


  /**
   *  Gets the reportAsBytes attribute of the JasperReportUtils class
   *
   *@param  filename       Description of the Parameter
   *@param  map            Description of the Parameter
   *@param  db             Description of the Parameter
   *@return                The reportAsBytes value
   *@exception  Exception  Description of the Exception
   */
  public static byte[] getReportAsBytes(String filename, HashMap map, Connection db) throws Exception {
    JasperReport report = JasperReportUtils.getReport(filename);
    if (report == null) {
      return null;
    } else {
      return JasperRunManager.runReportToPdf(report, map, db);
    }
  }
}

