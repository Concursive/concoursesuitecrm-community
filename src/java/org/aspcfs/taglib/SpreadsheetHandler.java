/*
 *  Copyright(c) 2006 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INC OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */

package org.aspcfs.taglib;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.servlet.jsp.JspException;
import java.util.StringTokenizer;
import org.aspcfs.utils.FolderUtils;

/**
 * Constructs the spread sheet cells for given input rows and columns.
 */
public class SpreadsheetHandler extends TagSupport implements TryCatchFinally {

  private int rows = -1;
  private int cols = -1;
  private int prevRows = -1;
  private int prevCols = -1;
  private String configString=null;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    rows = -1;
    cols = -1;
  }

  /**
   * @param rows
   */
  public void setRows(int rows) {
    this.rows = rows;
  }

  /**
   * @param cols
   */
  public void setCols(int cols) {
    this.cols = cols;
  }

  /**
   * @param prevRows
   */
  public void setPrevRows(int prevRows) {
    this.prevRows = prevRows;
  }

  /**
   * @param prevCols
   */
  public void setPrevCols(int prevCols) {
    this.prevCols = prevCols;
  }

  /**
   * @return int
   */
  public int getRows() {
    return rows;
  }

  /**
   * @return int
   */
  public int getCols() {
    return cols;
  }

  /**
   * @return int
   */
  public int getPrevRows() {
    return prevRows;
  }

  /**
   * @return int
   */
  public int getPrevCols() {
    return prevCols;
  }

  /**
   * @param tmp
   */
  public void setConfigString(String tmp) {
    this.configString = tmp;
  }

  /**
   * @return String
   */
  public String getConfigString() {
    return configString;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws javax.servlet.jsp.JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {

      String[] cellNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
      StringBuffer buffer = new StringBuffer();
      int cellNamesSize = 0, counter = -1;
      buffer.append("<table class=\"details\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"100%\">");
      buffer.append("<tr><th>&nbsp;</th>");

      for (int i = 0; i < cols; i++) {
        if (cellNames.length == cellNamesSize && cols > cellNames.length) {
          cellNamesSize = 0;
          counter++;
        }
        if (counter == -1) {
          buffer.append("<th  align='center'><b>" + cellNames[cellNamesSize] + "</b></th>");
        } else {
          buffer.append("<th  align='center'><b>" + cellNames[counter] + cellNames[cellNamesSize] + "</b></th>");
        }
        cellNamesSize++;
      }
      buffer.append("</tr>");
      StringTokenizer configRows = null;
      StringTokenizer configColumns = null;
      String thisRow = null;
      int rowCount = 0;
      int columnCount = 0;
      String thisColumn = "";
      if (!"".equals(configString) && configString != null) {
        configRows = new StringTokenizer(configString, FolderUtils.ROW_SEPERATOR);
        rowCount = configRows.countTokens();
      }
      for (int i = 0; i < rows; i++) {
        if (configString != null && (rows == rowCount || rows > prevRows || rows < prevRows)) {
          if (i < prevRows) {
            thisRow = configRows.nextToken();
            thisRow = thisRow.substring(thisRow.indexOf("{") + 1, thisRow.length() - 1);
            configColumns = new StringTokenizer(thisRow, FolderUtils.COLUMN_SEPERATOR);
            columnCount = configColumns.countTokens();
          }
        }
        buffer.append("<tr>");
        cellNamesSize = 0;
        counter = -1;
        for (int j = 0; j < cols; j++) {
          if (configString != null && (cols == columnCount || cols > prevCols || cols < prevCols)) {
            if (j >= prevCols || i >= prevRows) {
              thisColumn = "";
            } else {
              thisColumn = configColumns.nextToken();
              thisColumn = !"EMPTY".equals(thisColumn) ? thisColumn : "";
            }
          }
          if (cellNames.length == cellNamesSize && cols > cellNames.length) {
            cellNamesSize = 0;
            counter++;
          }

          if (j == 0) {
            buffer.append("<th width=\"2%\"><b>" + (i + 1) + "</b></th>");
          }
          if (counter == -1) {
            buffer.append("<td><input type='text' id='" + cellNames[cellNamesSize] + (i + 1) + "' name='" + cellNames[cellNamesSize] + (i + 1) + "' value='" + thisColumn + "'></td>");
          } else {
            buffer.append("<td><input type='text' id='" + cellNames[counter] + cellNames[cellNamesSize] + (i + 1) + "' name='" + cellNames[counter] + cellNames[cellNamesSize] + (i + 1) + "' value='" + thisColumn + "'></td>");
          }
          cellNamesSize++;
        }
        buffer.append("</tr>");
      }
      buffer.append("</table>");

      this.pageContext.getOut().write(buffer.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return SKIP_BODY;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}
