package org.aspcfs.utils;

import dori.jasper.engine.*;
import dori.jasper.engine.util.*;
import java.io.*;

/**
 *  Methods to work with JasperReports
 *
 *@author     matt rajkowski
 *@created    October 3, 2003
 *@version    $Id$
 */
public class JasperReportUtils {

  /**
   *  Loads the specified JasperReport, if the report has not been compiled then
   *  that happens first
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

}

