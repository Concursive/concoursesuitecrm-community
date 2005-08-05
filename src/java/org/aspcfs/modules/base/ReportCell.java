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
package org.aspcfs.modules.base;

import java.text.DateFormat;
import java.util.Comparator;

/**
 * ReportCell is an object that defines the properties of a report cell for a
 * Report object.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 16, 2004
 */
public class ReportCell implements Comparable {

  //Report properties
  private String data = "";
  private String formatting = "";


  /**
   * Constructor for the ReportCell object
   */
  public ReportCell() {
  }


  /**
   * Constructor for the ReportCell object
   *
   * @param tmp Description of the Parameter
   */
  public ReportCell(String tmp) {
    this.data = tmp;
  }


  /**
   * Constructor for the ReportCell object
   *
   * @param tmp  Description of the Parameter
   * @param tmp2 Description of the Parameter
   */
  public ReportCell(String tmp, String tmp2) {
    this.data = tmp;
    this.formatting = tmp2;
  }


  //Set
  /**
   * Sets the data attribute of the ReportCell object
   *
   * @param tmp The new data value
   */
  public void setData(String tmp) {
    this.data = tmp;
  }


  /**
   * Sets the formatting attribute of the ReportCell object
   *
   * @param tmp The new formatting value
   */
  public void setFormatting(String tmp) {
    this.formatting = tmp;
  }


  //Get
  /**
   * Gets the data attribute of the ReportCell object
   *
   * @return The data value
   */
  public String getData() {
    return data;
  }


  /**
   * Gets the dataInt attribute of the ReportCell object
   *
   * @return The dataInt value
   */
  public int getDataInt() {
    return Integer.parseInt(data);
  }


  /**
   * Gets the dataDate attribute of the ReportCell object
   *
   * @return The dataDate value
   */
  public java.util.Date getDataDate() {
    try {
      DateFormat df = DateFormat.getDateInstance();
      java.util.Date date = df.parse(data);
      return date;
    } catch (java.text.ParseException pe) {
    }
    return null;
  }


  /**
   * Gets the formatting attribute of the ReportCell object
   *
   * @return The formatting value
   */
  public String getFormatting() {
    return formatting;
  }


  /**
   * Gets the formatted attribute of the ReportCell object
   *
   * @return The formatted value
   */
  public boolean isFormatted() {
    return !formatting.equals("");
  }


  private Comparator stringComparator = new cellComparator();
  private Comparator intComparator = new cellComparatorInt();
  private Comparator dateComparator = new cellComparatorDate();


  /**
   * Description of the Class
   *
   * @author matt rajkowski
   * @version $Id$
   * @created September 16, 2004
   */
  class cellComparator implements Comparator {
    /**
     * Description of the Method
     *
     * @param left  Description of the Parameter
     * @param right Description of the Parameter
     * @return Description of the Return Value
     */
    public int compare(Object left, Object right) {
      return (((ReportCell) left).getData().compareTo(
          ((ReportCell) right).getData()));
    }
  }


  /**
   * Description of the Method
   *
   * @param object Description of the Parameter
   * @return Description of the Return Value
   */
  public int compareTo(Object object) {
    return (stringComparator.compare(this, object));
  }


  /**
   * Description of the Class
   *
   * @author matt rajkowski
   * @version $Id$
   * @created September 16, 2004
   */
  class cellComparatorInt implements Comparator {
    /**
     * Description of the Method
     *
     * @param left  Description of the Parameter
     * @param right Description of the Parameter
     * @return Description of the Return Value
     */
    public int compare(Object left, Object right) {
      return (((ReportCell) left).getDataInt() - ((ReportCell) right).getDataInt());
    }
  }


  /**
   * Description of the Method
   *
   * @param object Description of the Parameter
   * @return Description of the Return Value
   */
  public int compareIntTo(Object object) {
    return (intComparator.compare(this, object));
  }


  /**
   * Description of the Class
   *
   * @author matt rajkowski
   * @version $Id$
   * @created September 16, 2004
   */
  class cellComparatorDate implements Comparator {
    /**
     * Description of the Method
     *
     * @param left  Description of the Parameter
     * @param right Description of the Parameter
     * @return Description of the Return Value
     */
    public int compare(Object left, Object right) {
      return (((ReportCell) left).getDataDate().compareTo(
          ((ReportCell) right).getDataDate()));
    }
  }


  /**
   * Description of the Method
   *
   * @param object Description of the Parameter
   * @return Description of the Return Value
   */
  public int compareDateTo(Object object) {
    return (dateComparator.compare(this, object));
  }

}

