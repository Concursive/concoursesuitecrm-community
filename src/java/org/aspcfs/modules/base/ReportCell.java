package org.aspcfs.modules.base;

import java.util.*;
import java.text.DateFormat;
 
/**
 * ReportCell is an object that defines the properties of a report cell
 * for a Report object.
 */
public class ReportCell implements Comparable {

  //Report properties
  private String data = "";
  private String formatting = "";
  
  public ReportCell() {}
  
  public ReportCell(String tmp) {
    this.data = tmp;
  }
  
  public ReportCell(String tmp, String tmp2) {
    this.data = tmp;
    this.formatting = tmp2;
  }
  
  //Set
  public void setData(String tmp) { this.data = tmp; }
  public void setFormatting(String tmp) { this.formatting = tmp; }

  //Get
  public String getData() { return data; }
  public int getDataInt() { return Integer.parseInt(data); }
  public java.util.Date getDataDate() { 
    try { 
      DateFormat df = DateFormat.getDateInstance();
      java.util.Date date = df.parse(data);
      return date;
    } catch (java.text.ParseException pe) {
    }
    return null;
  }
  public String getFormatting() { return formatting; }

  public boolean isFormatted() {
    return !formatting.equals("");
  }
  
  private Comparator stringComparator = new cellComparator();
  private Comparator intComparator = new cellComparatorInt();
  private Comparator dateComparator = new cellComparatorDate();
  
  class cellComparator implements Comparator {
    public int compare(Object left, Object right) {
      return ( ((ReportCell)left).getData().compareTo(((ReportCell)right).getData()) );
    }
  }
  
  public int compareTo(Object object) {
    return (stringComparator.compare(this, object));
  }
  
  class cellComparatorInt implements Comparator {
    public int compare(Object left, Object right) {
      return ( ((ReportCell)left).getDataInt() - ((ReportCell)right).getDataInt() );
    }
  }
  
  public int compareIntTo(Object object) {
    return (intComparator.compare(this, object));
  }
  
  class cellComparatorDate implements Comparator {
    public int compare(Object left, Object right) {
      return ( ((ReportCell)left).getDataDate().compareTo(((ReportCell)right).getDataDate()) );
    }
  }
  
  public int compareDateTo(Object object) {
    return (dateComparator.compare(this, object));
  }

}
