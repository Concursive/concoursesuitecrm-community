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

import java.util.*;

/**
 *  ReportRow is an object that defines the properties of a report row for a
 *  Report object.
 *
 *@author     matt rajkowski
 *@created    September 16, 2004
 *@version    $Id$
 */
public class ReportRow {

  //Report properties
  private Vector cells = new Vector();


  /**
   *  Constructor for the ReportRow object
   */
  public ReportRow() { }


  //Set
  /**
   *  Adds a feature to the Cell attribute of the ReportRow object
   *
   *@param  tmp   The feature to be added to the Cell attribute
   *@param  tmp2  The feature to be added to the Cell attribute
   */
  public void addCell(String tmp, String tmp2) {
    ReportCell thisCell = new ReportCell();
    thisCell.setData(tmp);
    thisCell.setFormatting(tmp2);
    cells.addElement(thisCell);
  }


  /**
   *  Adds a feature to the Cell attribute of the ReportRow object
   *
   *@param  tmp  The feature to be added to the Cell attribute
   */
  public void addCell(String tmp) {
    ReportCell thisCell = new ReportCell();
    thisCell.setData(tmp);
    cells.addElement(thisCell);
  }


  /**
   *  Adds a feature to the Cell attribute of the ReportRow object
   *
   *@param  tmp   The feature to be added to the Cell attribute
   *@param  tmp2  The feature to be added to the Cell attribute
   */
  public void addCell(int tmp, String tmp2) {
    ReportCell thisCell = new ReportCell();
    thisCell.setData("" + tmp);
    thisCell.setFormatting(tmp2);
    cells.addElement(thisCell);
  }


  /**
   *  Adds a feature to the Cell attribute of the ReportRow object
   *
   *@param  tmp  The feature to be added to the Cell attribute
   */
  public void addCell(int tmp) {
    ReportCell thisCell = new ReportCell();
    thisCell.setData("" + tmp);
    cells.addElement(thisCell);
  }


  //Get
  /**
   *  Gets the cells attribute of the ReportRow object
   *
   *@return    The cells value
   */
  public Vector getCells() {
    return this.cells;
  }


  /**
   *  Gets the cell attribute of the ReportRow object
   *
   *@param  tmp  Description of the Parameter
   *@return      The cell value
   */
  public ReportCell getCell(int tmp) {
    return (ReportCell) this.cells.get(tmp);
  }
}

