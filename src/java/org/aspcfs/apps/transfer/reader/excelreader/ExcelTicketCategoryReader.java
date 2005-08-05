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
package org.aspcfs.apps.transfer.reader.excelreader;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.aspcfs.apps.transfer.DataReader;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: ExcelTicketCategoryReader.java,v 1.3 2003/01/13 14:41:54
 *          mrajkowski Exp $
 * @created September 17, 2002
 */
public class ExcelTicketCategoryReader implements DataReader {
  public boolean ignoreRow1 = true;

  private String excelFile = "workbook.xls";


  /**
   * Sets the excelFile attribute of the ExcelTicketCategoryReader object
   *
   * @param tmp The new excelFile value
   */
  public void setExcelFile(String tmp) {
    this.excelFile = tmp;
  }


  /**
   * Sets the ignoreRow1 attribute of the ExcelTicketCategoryReader object
   *
   * @param tmp The new ignoreRow1 value
   */
  public void setIgnoreRow1(boolean tmp) {
    this.ignoreRow1 = tmp;
  }


  /**
   * Gets the excelFile attribute of the ExcelTicketCategoryReader object
   *
   * @return The excelFile value
   */
  public String getExcelFile() {
    return excelFile;
  }


  /**
   * Gets the ignoreRow1 attribute of the ExcelTicketCategoryReader object
   *
   * @return The ignoreRow1 value
   */
  public boolean getIgnoreRow1() {
    return ignoreRow1;
  }


  /**
   * Gets the version attribute of the CFSDatabaseReader object
   *
   * @return The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   * Gets the name attribute of the CFSDatabaseReader object
   *
   * @return The name value
   */
  public String getName() {
    return "Excel Ticket Category Reader";
  }


  /**
   * Gets the description attribute of the CFSDatabaseReader object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Reads data from an Excel file formatted with ticket category data";
  }


  /**
   * Gets the configured attribute of the CFSDatabaseReader object
   *
   * @return The configured value
   */
  public boolean isConfigured() {
    if (excelFile == null || "".equals(excelFile)) {
      return false;
    }
    File testFile = new File(excelFile);
    if (!testFile.exists()) {
      return false;
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param writer Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    logger.info("Processing excel file");
    try {
      POIFSFileSystem fs =
          new POIFSFileSystem(new FileInputStream(excelFile));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);
      int rows = sheet.getLastRowNum();
      int beginningRow = 0;
      if (ignoreRow1) {
        ++beginningRow;
      }
      for (int r = beginningRow; r < rows + 1; r++) {
        HSSFRow row = sheet.getRow(r);
        if (row != null) {
          int cells = 4;
          System.out.println("ROW " + row.getRowNum());
          for (int c = 0; c < cells; c++) {
            HSSFCell cell = row.getCell((short) c);
            if (cell != null) {
              DataRecord thisRecord = new DataRecord();
              thisRecord.addField(
                  "catLevel", String.valueOf(cell.getCellNum()));
              thisRecord.addField("description", cell.getStringCellValue());
              writer.save(thisRecord);
            }
          }
        }
      }
    } catch (IOException io) {
      io.printStackTrace(System.out);
    }
    return true;
  }
}

