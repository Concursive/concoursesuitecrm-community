package com.darkhorseventures.apps.dataimport;

import java.util.*;

/**
 *  A record to be created by a DataReader and saved by a DataWriter, contains
 *  DataField objects.
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id$
 */
public class DataRecord extends ArrayList {

  protected String name = null;
  protected String action = "insert";
  
  public String getName() {
  	return this.name;
  }

  public void setName(String name) {
  	this.name = name;
  }
  
  public void setAction(String tmp) { this.action = tmp; }
  public String getAction() { return action; }
  
  public void addField(String thisName, String thisValue) {
    this.add(new DataField(thisName, thisValue));
  }
  
  public void addField(String thisName, String thisValue, String thisLookupValue) {
    this.add(new DataField(thisName, thisValue, thisLookupValue));
  }
  
  public boolean removeField(String fieldName) {
    Iterator fields = this.iterator();
    while (fields.hasNext()) {
      DataField thisField = (DataField)fields.next();
      if (fieldName.equals(thisField.getName())) {
        fields.remove();
        return true;
      }
    }
    return false;
  }

}

