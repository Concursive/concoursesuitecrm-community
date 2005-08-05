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

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Methods to work with JasperReports
 *
 * @author matt rajkowski
 * @version $Id: JasperReportUtils.java,v 1.4.34.1 2004/04/29 18:32:29
 *          kbhoopal Exp $
 * @created October 3, 2003
 */
public class JasperReportUtils {

  private final static int en_US = 1;    //US (English)
  private final static int de_DE = 2;    //Germany (German)
  private final static int el_GR = 3;     //Greece (Greek)
  private final static int es_VE = 4;    //Venezuela (Spanish)
  private final static int hu_HU = 5;   //Hungary (Hungarian)
  private final static int it_IT = 6;      //Italy (Italian)
  private final static int lt_LT = 7;     //Lithuania (Italian)
  private final static int nl_NL = 8;    //Netherlands (Dutch)
  private final static int pt_BR = 9;    //Brazil (Portuguese)
  private final static int ru_RU = 10;  //Russian Fed (Russian)
  private final static int sq_AL = 11;  //Albania (Albanian)
  private final static int zh_CN = 12; //China (Chinese)
  private final static int th_TH = 13;  //Thailand (Thai)
  private final static int ja_JP = 14;  //Japan (Japanese)


  /**
   * Loads the specified JasperReport, if the report has not been compiled then
   * that happens first. Only one report can be compiled at a time since a
   * duplicate request could ask to compile the same file.
   *
   * @param filename Description of the Parameter
   * @return The report value
   * @throws Exception Description of the Exception
   */
  public static synchronized JasperReport getReport(String filename) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("JasperReportUtils-> Checking report: " + filename);
    }
    System.setProperty(
        "jasper.reports.compiler.class",
        "net.sf.jasperreports.engine.design.JRBshCompiler");
    //Check to see if the .jasper exists
    String jasperFilename = filename.substring(
        0, filename.lastIndexOf(".xml"));
    File jasperFile = new File(jasperFilename + ".jasper");
    if (!jasperFile.exists()) {
      //The XML should exist so compile that
      File xmlFile = new File(filename);
      if (!xmlFile.exists()) {
        return null;
      }
      JasperCompileManager.compileReportToFile(
          xmlFile.getPath(), jasperFile.getPath());
    }
    if (!jasperFile.exists()) {
      return null;
    }
    //Load and return the report
    return (JasperReport) JRLoader.loadObject(jasperFile.getPath());
  }


  /**
   * Gets the reportAsBytes attribute of the JasperReportUtils class
   *
   * @param filename Description of the Parameter
   * @param map      Description of the Parameter
   * @param db       Description of the Parameter
   * @return The reportAsBytes value
   * @throws Exception Description of the Exception
   */
  public static byte[] getReportAsBytes(String filename, HashMap map, Connection db) throws Exception {
    JasperReport report = JasperReportUtils.getReport(filename);
    if (report == null) {
      return null;
    } else {
      return JasperRunManager.runReportToPdf(report, map, db);
    }
  }

  /**
   * Description of the Method
   */
  public static void readSystemFonts() {
    Map fontFaceNames = new HashMap();
      
    // Get all available font faces names
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Font[] fonts = ge.getAllFonts();
    
    // Process each font
    for (int i = 0; i < fonts.length; i++) {
      // Get font's family and face
      String familyName = fonts[i].getFamily();
      String faceName = fonts[i].getName();
      //System.out.println("FAMILY NAME: " + familyName);
      //System.out.println("FACE NAME: " + faceName);
    }
  }

  /**
   * A Jasper Report for successful generation of pdf output requires that the
   * correct pdf font and correct pdf encoding be specified. The font should
   * have definitions for all the character glyphs used in the language's
   * script. The font should also support the specified encoding scheme.
   *
   * @param jasperReport Description of the Parameter
   * @param fontPath     Description of the Parameter
   * @param language     Description of the Parameter
   */
  public static void modifyFontProperties(JasperReport jasperReport, String reportsPath, String fontPath, String language) throws Exception {
    String pdfFont = getPdfFont(language);
    String pdfEncoding = getPdfEncoding(language);

    if (fontPath != null) {
      boolean proceed = true;
      String fontFile = fontPath + pdfFont + ".ttf";

      /*
      if ("CP1250".equals(pdfEncoding)) {
        //Default encoding used in centric's jasper report templates. Modifying font properties not required
        proceed = false;
      }*/

      if (proceed) {
        if (pdfFont.startsWith("$") && pdfFont.endsWith("$")) {
          //External True-Type font file not required. Strip off "$"
          fontFile = StringUtils.replace(pdfFont, "$", "");
        } else {
          //Check to see if the external font file exists
          System.out.println("Font file: " + fontFile);
          File file = new File(fontFile);
          if (!file.exists()) {
            proceed = false;
          }
        }
      }

      if (proceed) {
        modifyBandProperties(jasperReport, reportsPath, fontFile, pdfEncoding);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param jasperReport Description of the Parameter
   */
  private static void modifyBandProperties(JasperReport jasperReport, String reportsPath, String fontFile, String pdfEncoding) throws Exception {
    //Title
    JRBand title = jasperReport.getTitle();
    if (title != null) {
      modifyFontProperties(
          title.getElements(), reportsPath, fontFile, pdfEncoding);
    }
    
    //Page Header
    JRBand pageHeader = jasperReport.getPageHeader();
    if (pageHeader != null) {
      modifyFontProperties(
          pageHeader.getElements(), reportsPath, fontFile, pdfEncoding);
    }
    
    //Column Header
    JRBand columnHeader = jasperReport.getColumnHeader();
    if (columnHeader != null) {
      modifyFontProperties(
          columnHeader.getElements(), reportsPath, fontFile, pdfEncoding);
    }
    
    //Groups
    JRGroup[] groups = jasperReport.getGroups();
    if (groups != null) {
      for (int i = 0; i < groups.length; ++i) {
        JRGroup group = groups[i];
        if (group != null) {
          if (group.getGroupHeader() != null) {
            modifyFontProperties(
                group.getGroupHeader().getElements(), reportsPath, fontFile, pdfEncoding);
          }
          if (group.getGroupFooter() != null) {
            modifyFontProperties(
                group.getGroupFooter().getElements(), reportsPath, fontFile, pdfEncoding);
          }
        }
      }
    }
    
    //Detail
    JRBand detail = jasperReport.getDetail();
    if (detail != null) {
      modifyFontProperties(
          detail.getElements(), reportsPath, fontFile, pdfEncoding);
    }
    
    //Column Footer
    JRBand columnFooter = jasperReport.getColumnFooter();
    if (columnFooter != null) {
      modifyFontProperties(
          columnFooter.getElements(), reportsPath, fontFile, pdfEncoding);
    }
    
    //Page Footer
    JRBand pageFooter = jasperReport.getPageFooter();
    if (pageFooter != null) {
      modifyFontProperties(
          pageFooter.getElements(), reportsPath, fontFile, pdfEncoding);
    }
    
    //LastPage Footer
    JRBand lastPageFooter = jasperReport.getLastPageFooter();
    if (lastPageFooter != null) {
      modifyFontProperties(
          lastPageFooter.getElements(), reportsPath, fontFile, pdfEncoding);
    }
    
    //Summary
    JRBand summary = jasperReport.getSummary();
    if (summary != null) {
      modifyFontProperties(
          summary.getElements(), reportsPath, fontFile, pdfEncoding);
    }
  }

  /**
   * Description of the Method
   *
   * @param elements    Description of the Parameter
   * @param fontFile    Description of the Parameter
   * @param pdfEncoding Description of the Parameter
   */
  private static void modifyFontProperties(JRElement[] elements, String reportsPath, String fontFile, String pdfEncoding) throws Exception {
    if (elements != null) {
      for (int i = 0; i < elements.length; ++i) {
        JRElement element = elements[i];
        if (element instanceof JRTextElement) {
          JRFont font = ((JRTextElement) element).getFont();
          if (font != null) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println(
                  "JasperReportUtils-> Specifying Font properties");
            }

            font.setPdfFontName(fontFile);
            font.setPdfEncoding(pdfEncoding);
            font.setPdfEmbedded(true);
          }
        } else if (element instanceof JRSubreport) {
          //Determine the subreport file path
          String expression = ((JRSubreport) element).getExpression().getText().trim();
          if (expression.indexOf("\"") > -1 && expression.indexOf("\"") < expression.length()) {
            expression = expression.substring(expression.indexOf("\"") + 1);
          }

          String jasperFileName = expression.substring(
              0, expression.indexOf("\""));
          String jasperFilePath = reportsPath + jasperFileName;
          
          //Load the jasper subreport. 
          File jasperFile = new File(jasperFilePath);
          if (jasperFile.exists()) {
            JasperReport subReport = (JasperReport) JRLoader.loadObject(
                jasperFile.getPath());
            modifyBandProperties(
                subReport, reportsPath, fontFile, pdfEncoding);
          }
        }
      }
    }
  }


  /**
   * Gets the pdfFont attribute of the JasperReportUtils class
   *
   * @param language Description of the Parameter
   * @return The pdfFont value
   */
  private static String getPdfFont(String language) {
    switch (getCode(language)) {
      case en_US:
      case de_DE:
      case el_GR:
      case es_VE:
      case hu_HU:
      case it_IT:
      case lt_LT:
      case nl_NL:
      case pt_BR:
      case sq_AL:
      case ru_RU:
        return "LucidaSansUnicode";
      case zh_CN:
        return "$STSong-Light$";  //Chinese Simplified. iTextAsian.jar required
      case ja_JP:
        //To fix
        return "$HeiseiMin-W3$";  //Japanese. iTextAsian.jar required
      case th_TH:
        return "Garuda-Bold";  //External True-Type font for Thai characters
      default:
        return "$Helvetica$";
    }
  }


  /**
   * Gets the pdfEncoding attribute of the JasperReportUtils class
   *
   * @param language Description of the Parameter
   * @return The pdfEncoding value
   */
  private static String getPdfEncoding(String language) {
    switch (getCode(language)) {
      case en_US:
      case de_DE:
      case hu_HU:
      case it_IT:
      case lt_LT:
      case nl_NL:
      case sq_AL:
      case pt_BR:
        return "CP1250";  //Central Europian
      case ru_RU:
        return "CP1251";  //Cyrillic
      case es_VE:
        return "CP1252";  //Western Europian ANSI
      case el_GR:
        return "CP1253";  //Greek
      case th_TH:
        return "Identity-H";  //Unicode with Horizontal Writing
      case zh_CN:
        return "UniGB-UCS2-H"; //Chinese
      case ja_JP:  //To fix
        return "UniJIS-UCS2-H"; //Japanese
      default:
        return "CP1250";   //Central Europian
    }
  }


  /**
   * Gets the code attribute of the JasperReportUtils class
   *
   * @param language Description of the Parameter
   * @return The code value
   */
  private static int getCode(String language) {
    if ("en_US".equals(language)) {
      return en_US;
    }
    if ("de_DE".equals(language)) {
      return de_DE;
    }
    if ("es_VE".equals(language)) {
      return es_VE;
    }
    if ("hu_HU".equals(language)) {
      return hu_HU;
    }
    if ("it_IT".equals(language)) {
      return it_IT;
    }
    if ("lt_LT".equals(language)) {
      return lt_LT;
    }
    if ("nl_NL".equals(language)) {
      return nl_NL;
    }
    if ("sq_AL".equals(language)) {
      return sq_AL;
    }
    if ("ru_RU".equals(language)) {
      return ru_RU;
    }
    if ("pt_BR".equals(language)) {
      return pt_BR;
    }
    if ("el_GR".equals(language)) {
      return el_GR;
    }
    if ("th_TH".equals(language)) {
      return th_TH;
    }
    if ("zh_CN".equals(language)) {
      return zh_CN;
    }
    if ("ja_JP".equals(language)) {
      return ja_JP;
    }

    return en_US; //Default
  }
}

