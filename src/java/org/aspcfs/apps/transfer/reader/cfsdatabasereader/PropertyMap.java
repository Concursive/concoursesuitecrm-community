package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.util.ArrayList;

public class PropertyMap extends ArrayList {
  private String id = null;
  private String table = null;
  
  public void setId(String tmp) { this.id = tmp; }
  public String getId() { return id; }
  
  public void setTable(String tmp) { this.table = tmp; }
  public String getTable() { return table; }
  
  public boolean hasTable() { 
    return (table != null && !"".equals(table));
  }
  
}
