package com.darkhorseventures.apps.dataimport;

import java.util.logging.*;

/**
 *  A field of a record
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id$
 */
public class DataField {
  public static Logger logger = Logger.getLogger(DataImport.class.getName());
  
  protected String name = null;
  protected String alias = null;
  protected String value = null;
  protected String valueLookup = null;
  
  public DataField() {}
  public DataField(String thisName, String thisValue) {
    this.name = thisName;
    this.value = thisValue;
  }
  public DataField(String thisName, String thisValue, String thisValueLookup, String thisAlias) {
    this.name = thisName;
    this.value = thisValue;
    this.valueLookup = thisValueLookup;
    this.alias = thisAlias;
  }
  
  public void setName(String tmp) { this.name = tmp; }
  public void setAlias(String tmp) { this.alias = tmp; }
  public void setValue(String tmp) { this.value = tmp; }
  public void setValueLookup(String tmp) { this.valueLookup = tmp; }
  public String getName() { return name; }
  public String getAlias() { return alias; }
  public String getValue() { return value; }
  public String getValueLookup() { return valueLookup; }

  public boolean hasValue() {
    return (value != null && !"".equals(value));
  }
  
  public boolean hasValueLookup() {
    return (valueLookup != null && !"".equals(valueLookup));
  }
  
  public boolean hasAlias() {
    return (alias != null && !"".equals(alias));
  }
}

