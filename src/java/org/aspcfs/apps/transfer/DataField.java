package com.darkhorseventures.apps.dataimport;

/**
 *  A field of a record
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id$
 */
public class DataField {

  protected String name = null;
  protected String value = null;
  protected String valueLookup = null;
  
  public DataField() {}
  public DataField(String thisName, String thisValue) {
    this.name = thisName;
    this.value = thisValue;
  }
  public DataField(String thisName, String thisValue, String thisValueLookup) {
    this.name = thisName;
    this.value = thisValue;
    this.valueLookup = thisValueLookup;
  }
  
  public void setName(String tmp) { this.name = tmp; }
  public void setValue(String tmp) { this.value = tmp; }
  public void setValueLookup(String tmp) { this.valueLookup = tmp; }
  public String getName() { return name; }
  public String getValue() { return value; }
  public String getValueLookup() { return valueLookup; }

  public boolean hasValueLookup() {
    return (valueLookup != null && !"".equals(valueLookup));
  }
}

