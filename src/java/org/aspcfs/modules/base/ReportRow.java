package org.aspcfs.modules.base;

import java.util.*;
 
/**
 * ReportRow is an object that defines the properties of a report row
 * for a Report object.
 */
public class ReportRow {

  //Report properties
  private Vector cells = new Vector();
  
  public ReportRow() {}
  
  //Set
  public void addCell(String tmp, String tmp2) {
    ReportCell thisCell = new ReportCell();
    thisCell.setData(tmp);
    thisCell.setFormatting(tmp2);
    cells.addElement(thisCell);
  }
  
  public void addCell(String tmp) {
    ReportCell thisCell = new ReportCell();
    thisCell.setData(tmp);
    cells.addElement(thisCell);
  }
  
  public void addCell(int tmp, String tmp2) {
    ReportCell thisCell = new ReportCell();
    thisCell.setData("" + tmp);
    thisCell.setFormatting(tmp2);
    cells.addElement(thisCell);
  }
  
  public void addCell(int tmp) {
    ReportCell thisCell = new ReportCell();
    thisCell.setData("" + tmp);
    cells.addElement(thisCell);
  }
  
  //Get
  public Vector getCells() { return this.cells; }
  
  public ReportCell getCell(int tmp) {
    return (ReportCell)this.cells.get(tmp);
  }
}
