package com.darkhorseventures.utils;

import java.util.*;
//import org.w3c.dom.*;
//import java.sql.*;

public class Record extends Hashtable {
  
  private String action = null;

  public Record() { }
  
  public Record(String thisAction) {
    action = thisAction;
  }
  
  public void setAction(String tmp) { this.action = tmp; }
  public String getAction() { return action; }
  public boolean hasAction() { return (action != null); }
}
