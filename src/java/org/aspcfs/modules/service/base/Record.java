package com.darkhorseventures.utils;

import java.util.*;
//import org.w3c.dom.*;
//import java.sql.*;

public class Record extends LinkedHashMap {
  
  private int recordId = -1;
  private String action = null;

  public Record() { }
  
  public Record(String thisAction) {
    action = thisAction;
  }
  
  public void setAction(String tmp) { this.action = tmp; }
  public String getAction() { return action; }
  
  public void setRecordId(int tmp) { this.recordId = tmp; }
  public void setRecordId(Integer tmp) { this.recordId = tmp.intValue(); }
  public void setRecordId(String tmp) { this.recordId = Integer.parseInt(tmp); }
  public int getRecordId() { return recordId; }

  public boolean hasAction() { return (action != null); }
}
