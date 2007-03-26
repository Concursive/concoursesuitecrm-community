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
package org.aspcfs.apps.transfer.reader.csvreader;

import org.apache.log4j.Logger;
import org.aspcfs.apps.transfer.DataReader;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMapList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * A base class that can be extended when reading CSV files for import, not to
 * be used directly.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created June 9, 2003
 */
public class CSVReader implements DataReader {

  private static final Logger logger = Logger.getLogger(org.aspcfs.apps.transfer.reader.csvreader.CSVReader.class);

  public final static String lf = System.getProperty("line.separator");
  public final static String fs = System.getProperty("file.separator");

  protected String csvFile = null;
  protected String propertyFile = null;
  protected PropertyMapList mappings = null;
  protected boolean ignoreRow1 = true;
  protected int importUserId = -1;


  /**
   * Gets the version attribute of the CSVReader object
   *
   * @return The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   * Gets the name attribute of the CSVReader object
   *
   * @return The name value
   */
  public String getName() {
    return "CSVReader";
  }


  /**
   * Gets the description attribute of the CSVReader object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Base class for processing CSV files for import, should not be used directly by Reader application";
  }


  /**
   * Sets the csvFile attribute of the CSVReader object
   *  Gets the importUserId attribute of the CSVReader object
   *
   *@return    The importUserId value
   */
  public int getImportUserId() {
    return importUserId;
  }


  /**
   *  Sets the importUserId attribute of the CSVReader object
   *
   *@param  tmp  The new importUserId value
   */
  public void setImportUserId(int tmp) {
    this.importUserId = tmp;
  }


  /**
   *  Sets the importUserId attribute of the CSVReader object
   *
   *@param  tmp  The new importUserId value
   */
  public void setImportUserId(String tmp) {
    this.importUserId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the csvFile attribute of the CSVReader object
   *
   * @param tmp The new csvFile value
   */
  public void setCsvFile(String tmp) {
    this.csvFile = tmp;
  }


  /**
   * Sets the propertyFile attribute of the CSVReader object
   *
   * @param tmp The new propertyFile value
   */
  public void setPropertyFile(String tmp) {
    this.propertyFile = tmp;
  }


  /**
   * Sets the mappings attribute of the CSVReader object
   *
   * @param tmp The new mappings value
   */
  public void setMappings(PropertyMapList tmp) {
    this.mappings = tmp;
  }


  /**
   * Sets the ignoreRow1 attribute of the CSVReader object
   *
   * @param tmp The new ignoreRow1 value
   */
  public void setIgnoreRow1(boolean tmp) {
    this.ignoreRow1 = tmp;
  }


  /**
   * Sets the ignoreRow1 attribute of the CSVReader object
   *
   * @param tmp The new ignoreRow1 value
   */
  public void setIgnoreRow1(String tmp) {
    this.ignoreRow1 = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the csvFile attribute of the CSVReader object
   *
   * @return The csvFile value
   */
  public String getCsvFile() {
    return csvFile;
  }


  /**
   * Gets the mappings attribute of the CSVReader object
   *
   * @return The mappings value
   */
  public PropertyMapList getMappings() {
    return mappings;
  }


  /**
   * Gets the ignoreRow1 attribute of the CSVReader object
   *
   * @return The ignoreRow1 value
   */
  public boolean getIgnoreRow1() {
    return ignoreRow1;
  }


  /**
   * Checks to see if the file to be processed exists, as well as processes the
   * CSV reader configuration file.
   *
   * @return The configured value
   */
  public boolean isConfigured() {
    boolean configOK = true;
    String tmpFile = System.getProperty("processConfigFile");
    if (tmpFile != null && !"".equals(tmpFile)) {
      csvFile = tmpFile;
    }
    File importFile = new File(csvFile);
    if (!importFile.exists()) {
      logger.info("ImportAccounts-> Config: file not found: " + csvFile);
      configOK = false;
    }
    try {
      mappings = new PropertyMapList(propertyFile, new ArrayList());
    } catch (Exception e) {
      logger.info("ImportAccounts-> Config: mappings could not be loaded");
      configOK = false;
    }
    return configOK;
  }


  /**
   * Description of the Method
   *
   * @param writer Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    return false;
  }


  /**
   * Gets the value attribute of the CSVReader object
   *
   * @param record Description of the Parameter
   * @param column Description of the Parameter
   * @return The value value
   */
  public String getValue(ArrayList record, int column) {
    if (column > record.size() || column < 1) {
      return null;
    }
    String value = (String) record.get(column - 1);
    value = StringUtils.replace(value, "\\r\\n", lf);
    value = StringUtils.replace(value, "\\n\\r", lf);
    value = StringUtils.replace(value, "\\n", lf);
    value = StringUtils.replace(value, "\\r", lf);
    return value.trim();
  }


  /**
   * After the column row is processed, an array of column names can be queried
   * to find the corresponding column id.
   *
   * @param record Description of the Parameter
   * @param names  Description of the Parameter
   * @return Description of the Return Value
   */
  protected int findColumn(ArrayList record, String[] names) {
    int result = 0;
    for (int i = 0; i < names.length; i++) {
      int test = findColumn(record, names[i]);
      if (test > 0) {
        result = test;
      }
    }
    return result;
  }


  /**
   * After the column row is processed, the column names can be queried to find
   * the corresponding column id.
   *
   * @param record Description of the Parameter
   * @param name   Description of the Parameter
   * @return Description of the Return Value
   */
  protected int findColumn(ArrayList record, String name) {
    //Columns represent the worksheet column, starting with 1
    int column = 0;
    if (name != null) {
      column = (record.indexOf(name) + 1);
    }
    if (column == 0) {
      System.out.println(
          "ImportAccounts-> Column not found for parsing: " + name);
    } else {
      System.out.println(
          "ImportAccounts-> Column found for parsing: " + name + " (" + column + ")");
    }
    return column;
  }


  /**
   * Debug method to inspect the current line.
   *
   * @param out        Description of the Parameter
   * @param thisRecord Description of the Parameter
   */
  protected void writeln(PrintWriter out, ArrayList thisRecord) {
    System.out.println("Fields in line: " + thisRecord.size());
    for (int i = 0; i < thisRecord.size(); i++) {
      String field = (String) thisRecord.get(i);
      if (field.indexOf(",") > -1) {
        out.print("\"" + field + "\"");
      } else {
        out.print(field);
      }
      if (i < (thisRecord.size() - 1)) {
        out.print(",");
      }
    }
    out.println("");
  }
}

