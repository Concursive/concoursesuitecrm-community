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

import java.io.*;
import java.util.*;
import org.aspcfs.modules.base.Import;

/**
 *  Provides helper methods to read data from a text file in various formats
 *
 *@author     Mathur
 *@created    April 14, 2004
 *@version    $id:exp$
 */
public class CFSFileReader {
  private String filePath = null;
  private int fileType = -1;
  private String recordDelimiter = null;
  private String columnDelimiter = null;
  private BufferedReader in = null;


  /**
   *  Constructor for the CFSFileReader object
   *
   *@param  filePath                   Description of the Parameter
   *@param  fileType                   Description of the Parameter
   *@exception  FileNotFoundException  Description of the Exception
   */
  public CFSFileReader(String filePath, int fileType) throws FileNotFoundException {
    this.filePath = filePath;
    this.fileType = fileType;
    in = new BufferedReader(new FileReader(filePath));
  }


  /**
   *  Sets the filePath attribute of the CFSFileReader object
   *
   *@param  tmp  The new filePath value
   */
  public void setFilePath(String tmp) {
    this.filePath = tmp;
  }


  /**
   *  Sets the fileType attribute of the CFSFileReader object
   *
   *@param  tmp  The new fileType value
   */
  public void setFileType(int tmp) {
    this.fileType = tmp;
  }


  /**
   *  Sets the fileType attribute of the CFSFileReader object
   *
   *@param  tmp  The new fileType value
   */
  public void setFileType(String tmp) {
    this.fileType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the recordDelimiter attribute of the CFSFileReader object
   *
   *@param  tmp  The new recordDelimiter value
   */
  public void setRecordDelimiter(String tmp) {
    this.recordDelimiter = tmp;
  }


  /**
   *  Sets the columnDelimiter attribute of the CFSFileReader object
   *
   *@param  tmp  The new columnDelimiter value
   */
  public void setColumnDelimiter(String tmp) {
    this.columnDelimiter = tmp;
  }


  /**
   *  Gets the filePath attribute of the CFSFileReader object
   *
   *@return    The filePath value
   */
  public String getFilePath() {
    return filePath;
  }


  /**
   *  Gets the fileType attribute of the CFSFileReader object
   *
   *@return    The fileType value
   */
  public int getFileType() {
    return fileType;
  }


  /**
   *  Gets the recordDelimiter attribute of the CFSFileReader object
   *
   *@return    The recordDelimiter value
   */
  public String getRecordDelimiter() {
    return recordDelimiter;
  }


  /**
   *  Gets the columnDelimiter attribute of the CFSFileReader object
   *
   *@return    The columnDelimiter value
   */
  public String getColumnDelimiter() {
    return columnDelimiter;
  }


  /**
   *  Gets the next line in the file based on the type
   *
   *@return    Description of the Return Value
   */
  public Record nextLine() {
    Record record = null;
    try {
      switch (fileType) {
          case Import.EXCEL_CSV:
            record = retrieveExcelCSVLine();
            break;
          case Import.CUSTOM:
            record = retrieveCustomLine();
            break;
          case Import.ACT:
            record = retrieveActLine();
            break;
          default:
            break;
      }
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        e.printStackTrace();
      }
      record = null;
    }
    return record;
  }


  /**
   *  Description of the Method
   *
   *@param  line  Description of the Parameter
   *@return       Description of the Return Value
   */
  public ArrayList parseLine(String line) {
    return null;
  }


  /**
   *  Retrieves line for a Excel/Outlook CSV file
   *
   *@return                  Description of the Return Value
   *@exception  IOException  Description of the Exception
   */
  public Record retrieveExcelCSVLine() throws IOException {
    if (in == null) {
      return null;
    }

    Record record = null;
    try {
      //does the column data have quotes around it?
      boolean quoteBegin = false;
      //trick is to set beginning of a column based on end of previous column
      boolean beginColumn = true;
      StringBuffer buffer = new StringBuffer();
      String line = null;
      record = new Record();
      record.data = new ArrayList();
      boolean done = false;
      while (!done) {
        if (((line = in.readLine()) != null)) {
          if ("".equals(line.trim())) {
            continue;
          }
          record.line += line;

          for (int i = 0; i < line.length(); i++) {
            char thisChar = line.charAt(i);

            //check if it is starting of column
            if (beginColumn) {
              quoteBegin = false;
              if (thisChar == '\"') {
                quoteBegin = true;
              } else {
                //can be start of line or start of unended column data on next line
                buffer.append(thisChar);
              }

              //check for empty columns
              if (thisChar == ',') {
                record.data.add("");
                buffer = buffer.delete(0, buffer.length());
              } else {
                //column beginning is history now
                beginColumn = false;
              }
            } else if (thisChar == ',') {
              //if comma special processing, including check to see if it is end of column
              if (quoteBegin) {
                //if it does begin with a quote, it is data
                buffer.append(thisChar);
              } else {
                //only other case for a comma to be is end of column data
                record.data.add(buffer.toString());
                buffer = buffer.delete(0, buffer.length());
                beginColumn = true;
              }
            } else if (thisChar == '\"') {
              //if " special processing including end of column
              char nextChar = ' ';
              if (i + 1 < line.length()) {
                nextChar = line.charAt(i + 1);
              }
              if (quoteBegin && nextChar == '\"') {
                //if it begins with a quote and next char is "", then it is data
                buffer.append(thisChar);
                i++;
              } else {
                //it has to be a processing tag for end of column data
                record.data.add(buffer.toString());
                buffer = buffer.delete(0, buffer.length());
                beginColumn = true;

                //filter out the column delimiter
                if (nextChar == ',') {
                  i++;
                }
              }
            } else {
              //add to data
              buffer.append(thisChar);
            }

            //boundary condition
            if (i == (line.length() - 1)) {
              if (quoteBegin && !beginColumn) {
                done = false;
              } else if (!quoteBegin) {
                record.data.add(buffer.toString());
                buffer = buffer.delete(0, buffer.length());
                done = true;
              } else {
                done = true;
              }
            }
          }
        } else {
          done = true;
        }
      }
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        e.printStackTrace();
      }
    }

    if ("".equals(record.line)) {
      return null;
    }
    return record;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public Record retrieveCustomLine() {
    return null;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public Record retrieveActLine() {
    return null;
  }


  /**
   *  Pads the line with default column delimiters after the last column data in
   *  the line
   *
   *@param  line      Description of the Parameter
   *@param  padCount  Number of columns to be padded
   *@return           Description of the Return Value
   */
  public String padLine(String line, int padCount) {
    String paddedLine = line;
    try {
      switch (fileType) {
          case Import.EXCEL_CSV:
            paddedLine = padExcelCSVLine(line, padCount);
            break;
          case Import.CUSTOM:
            paddedLine = padCustomLine(line, padCount);
            break;
          case Import.ACT:
            paddedLine = padActLine(line, padCount);
            break;
          default:
            break;
      }
    } catch (Exception e) {
      paddedLine = line;
    }
    return paddedLine;
  }


  /**
   *  Description of the Method
   *
   *@param  line      Description of the Parameter
   *@param  padCount  Description of the Parameter
   *@return           Description of the Return Value
   */
  public String padExcelCSVLine(String line, int padCount) {
    for (int i = 0; i < padCount; i++) {
      line += ",";
    }
    return line;
  }


  /**
   *  Description of the Method
   *
   *@param  line      Description of the Parameter
   *@param  padCount  Description of the Parameter
   *@return           Description of the Return Value
   */
  public String padCustomLine(String line, int padCount) {
    //TODO: Implement padding based on delimiters
    return line;
  }


  /**
   *  Description of the Method
   *
   *@param  line      Description of the Parameter
   *@param  padCount  Description of the Parameter
   *@return           Description of the Return Value
   */
  public String padActLine(String line, int padCount) {
    //TODO: Implement padding based on delimiters
    return line;
  }


  /**
   *  Data Structure for holding a line in a string and arraylist
   *
   *@author     Mathur
   *@created    April 15, 2004
   *@version    $id:exp$
   */
  public static class Record {
    /**
     *  Constructor for the record object
     */
    public Record() { }


    public String line = "";
    public ArrayList data = null;
  }
}

