package com.darkhorseventures.utils;

import java.util.*;
//import org.w3c.dom.*;
//import java.sql.*;

public class RecordList extends ArrayList {

  private String name = null;
  
  public RecordList(String tableName) {
    name = tableName;
  }
  
  public String getName() {
    return name;
  }
}
