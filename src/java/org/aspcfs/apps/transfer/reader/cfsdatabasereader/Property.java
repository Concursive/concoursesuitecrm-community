package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

public class Property {
  
  private String name = null;
  private String lookupValue = null;
  private String alias = null;
  private String field = null;
  
  public Property() {}
  public Property(String name) { this.name = name; }
  
  public void setName(String tmp) { this.name = tmp; }
  public void setLookupValue(String tmp) { this.lookupValue = tmp; }
  public void setAlias(String tmp) { this.alias = tmp; }
  public void setField(String tmp) { this.field = tmp; }

  public String getName() { return name; }
  public String getLookupValue() { return lookupValue; }
  public String getAlias() { return alias; }
  public String getField() { return field; }

  public boolean hasName() {
    return (name != null && !"".equals(name));
  }
  
  public boolean hasLookupValue() {
    return (lookupValue != null && !"".equals(lookupValue));
  }
  
  public boolean hasAlias() {
    return (alias != null && !"".equals(alias));
  }
  
  public boolean hasField() {
    return (field != null && !"".equals(field));
  }
}
