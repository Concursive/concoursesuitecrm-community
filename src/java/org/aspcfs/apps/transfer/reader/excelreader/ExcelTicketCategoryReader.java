package org.aspcfs.apps.transfer.reader.excelreader;

import org.aspcfs.apps.transfer.*;
import java.util.*;
import java.io.*;
import java.util.logging.*;
import org.w3c.dom.*;

import java.util.Random;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    September 17, 2002
 *@version    $Id$
 */
public class ExcelTicketCategoryReader implements DataReader {
  public boolean ignoreRow1 = true;

  private String excelFile = "workbook.xls";


  /**
   *  Sets the excelFile attribute of the ExcelTicketCategoryReader object
   *
   *@param  tmp  The new excelFile value
   */
  public void setExcelFile(String tmp) {
    this.excelFile = tmp;
  }


  /**
   *  Sets the ignoreRow1 attribute of the ExcelTicketCategoryReader object
   *
   *@param  tmp  The new ignoreRow1 value
   */
  public void setIgnoreRow1(boolean tmp) {
    this.ignoreRow1 = tmp;
  }


  /**
   *  Gets the excelFile attribute of the ExcelTicketCategoryReader object
   *
   *@return    The excelFile value
   */
  public String getExcelFile() {
    return excelFile;
  }


  /**
   *  Gets the ignoreRow1 attribute of the ExcelTicketCategoryReader object
   *
   *@return    The ignoreRow1 value
   */
  public boolean getIgnoreRow1() {
    return ignoreRow1;
  }


  /**
   *  Gets the version attribute of the CFSDatabaseReader object
   *
   *@return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Gets the name attribute of the CFSDatabaseReader object
   *
   *@return    The name value
   */
  public String getName() {
    return "Excel Ticket Category Reader";
  }


  /**
   *  Gets the description attribute of the CFSDatabaseReader object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Reads data from an Excel file formatted with ticket category data";
  }


  /**
   *  Gets the configured attribute of the CFSDatabaseReader object
   *
   *@return    The configured value
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
   *  Description of the Method
   *
   *@param  writer           Description of the Parameter
   *@return                  Description of the Return Value
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
              thisRecord.addField("catLevel", String.valueOf(cell.getCellNum()));
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

