package com.darkhorseventures.utils;

import java.util.*;
//import org.w3c.dom.*;
//import java.sql.*;

public class Record extends Hashtable {

  public Record() { 
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Record-> Initialized");
    }
  }
}
