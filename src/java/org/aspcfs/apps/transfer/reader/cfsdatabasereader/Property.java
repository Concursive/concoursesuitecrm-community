package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

public class Property {
  
  private String name = null;
  private String lookupValue = null;
  
  public Property() {}
  public Property(String name) { this.name = name; }
  
  public void setName(String tmp) { this.name = tmp; }
  public void setLookupValue(String tmp) { this.lookupValue = tmp; }
  public String getName() { return name; }
  public String getLookupValue() { return lookupValue; }

  public boolean hasLookupValue() {
    return (lookupValue != null && !"".equals(lookupValue));
  }
}
