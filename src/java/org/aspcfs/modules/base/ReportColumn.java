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



/**
 * ReportColumn is an object that defines the properties of a report column for
 * a Report object.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 16, 2004
 */
public class ReportColumn {

  //Report properties
  private String name = "";
  private String htmlName = null;
  private String htmlFormatting = "";
  private String type = null;
  //String, boolean, int, float
  private float runningTotal = 0;


  /**
   * Constructor for the ReportColumn object
   */
  public ReportColumn() {
  }


  /**
   * Constructor for the ReportColumn object
   *
   * @param tmp Description of the Parameter
   */
  public ReportColumn(String tmp) {
    this.name = tmp;
  }


  /**
   * Constructor for the ReportColumn object
   *
   * @param tmp  Description of the Parameter
   * @param tmp2 Description of the Parameter
   */
  public ReportColumn(String tmp, String tmp2) {
    this.name = tmp;
    this.htmlFormatting = tmp2;
  }


  //Set
  /**
   * Sets the name attribute of the ReportColumn object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the htmlName attribute of the ReportColumn object
   *
   * @param tmp The new htmlName value
   */
  public void setHtmlName(String tmp) {
    this.htmlName = tmp;
  }


  /**
   * Sets the formatting attribute of the ReportColumn object
   *
   * @param tmp The new formatting value
   */
  public void setFormatting(String tmp) {
    this.htmlFormatting = tmp;
  }


  /**
   * Sets the type attribute of the ReportColumn object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  //Get
  /**
   * Gets the name attribute of the ReportColumn object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the htmlName attribute of the ReportColumn object
   *
   * @return The htmlName value
   */
  public String getHtmlName() {
    if (htmlName != null) {
      return htmlName;
    } else {
      return name;
    }
  }


  /**
   * Gets the type attribute of the ReportColumn object
   *
   * @return The type value
   */
  public String getType() {
    return type;
  }


  /**
   * Gets the runningTotal attribute of the ReportColumn object
   *
   * @return The runningTotal value
   */
  public float getRunningTotal() {
    return runningTotal;
  }


  /**
   * Gets the runningTotalString attribute of the ReportColumn object
   *
   * @return The runningTotalString value
   */
  public String getRunningTotalString() {
    return "" + runningTotal;
  }


  /**
   * Gets the formatting attribute of the ReportColumn object
   *
   * @return The formatting value
   */
  public String getFormatting() {
    return htmlFormatting;
  }


  /**
   * Gets the formatted attribute of the ReportColumn object
   *
   * @return The formatted value
   */
  public boolean isFormatted() {
    return !htmlFormatting.equals("");
  }
}

