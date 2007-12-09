/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
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
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.base;

import org.aspcfs.utils.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

/**
 * Report is an object that defines the properties of a report. Reports are
 * built in-memory then can be exported in a number of formats. <p>
 * <p/>
 * Usage: Create a report object, add column names, then add rows. Rows are
 * populated with cells. <p>
 * <p/>
 * Features: <br>
 * - Can output data in HTML or delimited (default TABs) as a String <br>
 * - Export data to a file <br>
 * - Data can be sorted by a column after rows are added <p>
 * <p/>
 * To do: <br>
 * - Add "Group By" <br>
 * - Add "Subtotal" -- used with Group By <br>
 * - Grand Total (requires a running total to be kept) <p>
 * <p/>
 * Thoughts: <br>
 * - XML output
 *
 * @author mrajkowski
 * @version $Id$
 * @created June 1, 2001
 */
public class Report {

  //Report properties
  private int id = 0;
  //Not used yet -- anticipated for organizing
  private int enteredById = 0;
  //Not used yet -- anticipated for organizing
  private String header = null;
  private String footer = null;
  private String subject = "";
  //Not used yet -- anticipated for organizing
  private String category = "";
  //Not used yet -- anticipated for organizing
  private java.util.Date timestamp = new java.util.Date();
  private Vector columns = new Vector();
  private Vector rows = new Vector();

  //Object properties
  private String delimitedCharacter = "\t";
  private boolean sorted = false;
  private int compareColumn = 0;
  private int borderSize = 1;
  private boolean showColumnHeaderWhenNoRecords = false;
  private boolean showColumnHeaders = true;
  protected static boolean newLine = true;


  /**
   * Constructor for the Report object
   *
   * @since 1.0
   */
  public Report() {
  }


  //Set
  /**
   * Sets the Id attribute of the Report object
   *
   * @param tmp The new Id value
   * @since 1.0
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the EnteredBy attribute of the Report object
   *
   * @param tmp The new EnteredBy value
   * @since 1.0
   */
  public void setEnteredBy(int tmp) {
    this.enteredById = tmp;
  }


  /**
   * Sets the Header attribute of the Report object
   *
   * @param tmp The new Header value
   * @since 1.0
   */
  public void setHeader(String tmp) {
    this.header = tmp;
  }


  /**
   * Sets the Footer attribute of the Report object
   *
   * @param tmp The new Footer value
   * @since 1.0
   */
  public void setFooter(String tmp) {
    this.footer = tmp;
  }


  /**
   * Sets the Subject attribute of the Report object
   *
   * @param tmp The new Subject value
   * @since 1.0
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   * Sets the Category attribute of the Report object
   *
   * @param tmp The new Category value
   * @since 1.0
   */
  public void setCategory(String tmp) {
    this.category = tmp;
  }


  /**
   * Defines a character or string used to delimit the fields on output,
   * Defaults to "TAB"
   *
   * @param tmp The new DelimitedCharacter value
   * @since 1.1
   */
  public void setDelimitedCharacter(String tmp) {
    this.delimitedCharacter = tmp;
  }


  /**
   * Defines the HTML table border size, or default is used
   *
   * @param tmp The new BorderSize value
   * @since 1.1
   */
  public void setBorderSize(int tmp) {
    borderSize = tmp;
  }


  /**
   * If you want to see the column headers when there are no records, set to
   * True; default is False
   *
   * @param tmp The new ShowColumnHeaderWhenNoRecords value
   * @since 1.1
   */
  public void setShowColumnHeaderWhenNoRecords(boolean tmp) {
    this.showColumnHeaderWhenNoRecords = tmp;
  }


  /**
   * To disable the column headers from showing, set to False
   *
   * @param tmp The new ShowColumnHeaders value
   * @since 1.1
   */
  public void setShowColumnHeaders(boolean tmp) {
    this.showColumnHeaders = tmp;
  }


  //Get
  /**
   * Gets the Id attribute of the Report object
   *
   * @return The Id value
   * @since 1.0
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the EnteredBy attribute of the Report object
   *
   * @return The EnteredBy value
   * @since 1.0
   */
  public int getEnteredBy() {
    return enteredById;
  }


  /**
   * Gets the Header attribute of the Report object
   *
   * @return The Header value
   * @since 1.0
   */
  public String getHeader() {
    return header;
  }


  /**
   * Gets the Footer attribute of the Report object
   *
   * @return The Footer value
   * @since 1.0
   */
  public String getFooter() {
    return footer;
  }


  /**
   * Gets the Subject attribute of the Report object
   *
   * @return The Subject value
   * @since 1.0
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Gets the Category attribute of the Report object
   *
   * @return The Category value
   * @since 1.0
   */
  public String getCategory() {
    return category;
  }


  /**
   * Gets the RowCount attribute of the Report object
   *
   * @return The RowCount value
   * @since 1.0
   */
  public int getRowCount() {
    return rows.size();
  }


  /**
   * Gets the ColumnCount attribute of the Report object
   *
   * @return The ColumnCount value
   * @since 1.0
   */
  public int getColumnCount() {
    return columns.size();
  }


  /**
   * Returns report in an HTML format as a string
   *
   * @return The Html value
   * @since 1.0
   */
  public String getHtml() {
    Iterator i = null;
    StringBuffer html = new StringBuffer();
    html.append("<table border='" + borderSize + "'>");

    //Draw header
    if (header != null) {
      html.append(
          "<tr><td colspan='" + columns.size() + "'>" + header + "</td></tr>");
    }

    //Draw the columns
    if (((showColumnHeaderWhenNoRecords && rows.size() == 0) || (rows.size() > 0)) &&
        (showColumnHeaders)) {
      html.append("<tr>");
      i = columns.iterator();
      while (i.hasNext()) {
        ReportColumn thisColumn = (ReportColumn) i.next();
        html.append("<td");
        if (thisColumn.isFormatted()) {
          html.append(" " + thisColumn.getFormatting());
        } else {
          html.append(" valign='top' bgcolor='#FFFFFF' nowrap");
        }
        html.append(">");
        html.append(thisColumn.getHtmlName());
        html.append("</td>");
      }
      html.append("</tr>");
    }

    //Draw the data
    i = rows.iterator();
    while (i.hasNext()) {
      html.append("<tr>");
      ReportRow thisRow = (ReportRow) i.next();
      Vector cells = (Vector) thisRow.getCells();
      Iterator j = cells.iterator();
      while (j.hasNext()) {
        ReportCell thisCell = (ReportCell) j.next();
        html.append("<td");
        if (thisCell.isFormatted()) {
          html.append(" " + thisCell.getFormatting());
        } else {
          html.append(" valign='top' nowrap");
        }
        html.append(">");
        html.append(check(thisCell.getData()));
        html.append("</td>");
      }
      html.append("</tr>");
    }

    //Draw footer
    if (footer != null) {
      html.append(
          "<tr><td colspan='" + columns.size() + "'>" + footer + "</td></tr>");
    }

    html.append("</table>");
    return html.toString();
  }


  /**
   * Returns report in a delimited format as a string
   *
   * @return The Delimited value
   * @since 1.0
   */
  public String getDelimited() {
    StringBuffer ascii = new StringBuffer();
    Iterator i = null;

    //Draw header
    if (header != null) {
      ascii.append(header + "\r\n");
    }

    //Draw the columns
    if (((showColumnHeaderWhenNoRecords && rows.size() == 0) || (rows.size() > 0)) &&
        (showColumnHeaders)) {
      i = columns.iterator();
      while (i.hasNext()) {
        ReportColumn thisColumn = (ReportColumn) i.next();
        String tmp = checkNull(thisColumn.getName());
        //ascii.append(tmp);
        ascii.append(prepareToWrite(tmp));
        //if (i.hasNext()) {
        //ascii.append(delimitedCharacter);
        //  newLine = false;
        //} else {
        //  newline = true;
        //}
      }
      ascii.append("\r\n");
      newLine = true;
    }

    //Draw the data
    i = rows.iterator();
    while (i.hasNext()) {
      ReportRow thisRow = (ReportRow) i.next();
      Vector cells = (Vector) thisRow.getCells();
      Iterator j = cells.iterator();
      while (j.hasNext()) {
        ReportCell thisCell = (ReportCell) j.next();
        String tmp = checkNull(thisCell.getData());
        //ascii.append(tmp);
        ascii.append(prepareToWrite(tmp));
        //if (j.hasNext()) {
        //  newLine = false;
        //ascii.append(delimitedCharacter);
        //} else {
        //  newLine = true;
        //}
      }
      ascii.append("\r\n");
      newLine = true;
    }

    //Draw footer
    if (footer != null) {
      ascii.append(footer + "\r\n");
    }

    return ascii.toString();
  }


  /**
   * Adds a Column to the report
   *
   * @param tmp The feature to be added to the Column attribute
   * @since 1.0
   */
  public void addColumn(String tmp) {
    ReportColumn rc = new ReportColumn(tmp);
    this.columns.addElement(rc);
  }


  /**
   * Adds a Column to the report, a second String can be used to specify the
   * HTML column header with tags
   *
   * @param tmp  The feature to be added to the Column attribute
   * @param tmp2 The feature to be added to the Column attribute
   * @since 1.0
   */
  public void addColumn(String tmp, String tmp2) {
    ReportColumn rc = new ReportColumn(tmp);
    rc.setHtmlName(tmp2);
    this.columns.addElement(rc);
  }


  /**
   * Adds a ReportRow to the report
   *
   * @param tmp The feature to be added to the Row attribute
   * @since 1.0
   */
  public void addRow(ReportRow tmp) {
    this.rows.addElement(tmp);
  }


  /**
   * Sort the report in-memory by the specified column, by default the column
   * is sorted by the character string
   *
   * @param tmp Description of Parameter
   * @since 1.0
   */
  public void sortByColumn(int tmp) {
    this.sortByColumn(tmp, "string");
  }


  /**
   * Sort the report in-memory by the specified column and column type; column
   * type can be "date", "int" or by default "string"
   *
   * @param tmp  Description of Parameter
   * @param type Description of Parameter
   * @since 1.0
   */
  public void sortByColumn(int tmp, String type) {
    this.compareColumn = tmp;
    Object sortArray[] = rows.toArray();
    Comparator comparator = null;
    if (type.equals("date")) {
      comparator = new cellComparatorDate();
    } else if (type.equals("int")) {
      comparator = new cellComparatorInt();
    } else {
      comparator = new cellComparatorString();
    }
    Arrays.sort(sortArray, comparator);
    rows.clear();
    for (int i = 0; i < sortArray.length; i++) {
      rows.addElement((ReportRow) sortArray[i]);
    }
  }


  /**
   * Exports report to a specified HTML file
   *
   * @param filename Description of Parameter
   * @return Description of the Returned Value
   * @throws java.io.IOException Description of Exception
   * @since 1.0
   */
  public int saveHtml(String filename) throws java.io.IOException {
    File outputFile = new File(filename);
    FileWriter out = new FileWriter(outputFile);
    out.write(this.getHtml());
    out.close();
    return 0;
  }


  /**
   * Exports report to a specified text file
   *
   * @param filename Description of Parameter
   * @return Description of the Returned Value
   * @throws java.io.IOException Description of Exception
   * @since 1.0
   */
  public int saveDelimited(String filename) throws java.io.IOException {
    File outputFile = new File(filename);
    FileWriter out = new FileWriter(outputFile);
    out.write(this.getDelimited());
    out.close();
    return 0;
  }


  /**
   * If the string is blank, send an html space
   *
   * @param tmp Description of Parameter
   * @return Description of the Returned Value
   * @since 1.0
   */
  private String check(String tmp) {
    if (tmp == null || tmp.trim().equals("")) {
      return "&nbsp;";
    } else {
      return tmp;
    }
  }


  /**
   * Description of the Method
   *
   * @param tmp Description of the Parameter
   * @return Description of the Return Value
   */
  private String checkNull(String tmp) {
    if (tmp == null) {
      return "";
    } else {
      return tmp;
    }
  }


  /**
   * Defines how report objects are compared (String)
   *
   * @author mrajkowski
   * @version $Id$
   * @created June 1, 2001
   */
  class cellComparatorString implements Comparator {
    /**
     * Description of the Method
     *
     * @param left  Description of Parameter
     * @param right Description of Parameter
     * @return Description of the Returned Value
     */
    public int compare(Object left, Object right) {
      return (
          ((ReportRow) left).getCell(compareColumn).compareTo(
              ((ReportRow) right).getCell(compareColumn)));
    }
  }


  /**
   * Defines how report objects are compared (int)
   *
   * @author mrajkowski
   * @version $Id$
   * @created June 1, 2001
   */
  class cellComparatorInt implements Comparator {
    /**
     * Description of the Method
     *
     * @param left  Description of Parameter
     * @param right Description of Parameter
     * @return Description of the Returned Value
     */
    public int compare(Object left, Object right) {
      return (
          ((ReportRow) left).getCell(compareColumn).compareIntTo(
              ((ReportRow) right).getCell(compareColumn)));
    }
  }


  /**
   * Description of the Method
   *
   * @param value Description of the Parameter
   * @return Description of the Return Value
   */
  private static String prepareToWrite(String value) {
    StringBuffer ascii = new StringBuffer();
    boolean quote = false;
    if (value.length() > 0) {
      for (int i = 0; i < value.length(); i++) {
        char c = value.charAt(i);
        if (c == '"' || c == ',' || c == '\n' || c == '\r') {
          quote = true;
        }
      }
    } else if (newLine) {
      // always quote an empty token that is the first
      // on the line, as it may be the only thing on the
      // line.  If it were not quoted in that case,
      // an empty line has no tokens.
      quote = true;
    }
    if (newLine) {
      newLine = false;
    } else {
      ascii.append(",");
    }
    if (quote) {
      ascii.append(escapeAndQuote(value));
    } else {
      ascii.append(value);
    }

    return ascii.toString();
  }


  /**
   * enclose the value in quotes and escape the quote and comma characters that
   * are inside.
   *
   * @param value needs to be escaped and quoted
   * @return the value, escaped and quoted.
   */
  private static String escapeAndQuote(String value) {
    String s = StringUtils.replace(value, "\"", "\"\"");
    return (new StringBuffer(2 + s.length())).append("\"").append(s).append(
        "\"").toString();
  }


  /**
   * Defines how report objects are compared (Date)
   *
   * @author mrajkowski
   * @version $Id$
   * @created June 1, 2001
   */
  class cellComparatorDate implements Comparator {
    /**
     * Description of the Method
     *
     * @param left  Description of Parameter
     * @param right Description of Parameter
     * @return Description of the Returned Value
     */
    public int compare(Object left, Object right) {
      return (((ReportRow) left).getCell(compareColumn).compareDateTo(
          ((ReportRow) right).getCell(compareColumn)));
    }
  }
}

