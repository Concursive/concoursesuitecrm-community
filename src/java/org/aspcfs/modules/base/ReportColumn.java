package com.darkhorseventures.utils;

import java.util.*;
 
/**
 * ReportColumn is an object that defines the properties of a report column
 * for a Report object.
 */
public class ReportColumn {

  //Report properties
  private String name = "";
  private String htmlName = null;
  private String htmlFormatting = "";
  private String type = null; //String, boolean, int, float
  private float runningTotal = 0;
  
  public ReportColumn() {}
  
  public ReportColumn(String tmp) {
    this.name = tmp;
  }
  
  public ReportColumn(String tmp, String tmp2) {
    this.name = tmp;
    this.htmlFormatting = tmp2;
  }
  
  //Set
  public void setName(String tmp) { this.name = tmp; }
  public void setHtmlName(String tmp) {
    this.htmlName = tmp;
  }
  public void setFormatting(String tmp) { this.htmlFormatting = tmp; }
  public void setType(String tmp) { this.type = tmp; }

  //Get
  public String getName() { return name; }
  public String getHtmlName() { 
    if (htmlName != null) {
      return htmlName;
    } else {
      return name;
    }
  }
  public String getType() { return type; }
  public float getRunningTotal() { return runningTotal; }
  public String getRunningTotalString() { return "" + runningTotal; }
  
  public String getFormatting() { return htmlFormatting; }

  public boolean isFormatted() {
    return !htmlFormatting.equals("");
  }
}
