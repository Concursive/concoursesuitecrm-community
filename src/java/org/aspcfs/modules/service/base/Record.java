package com.darkhorseventures.utils;

import java.util.*;

/**
 *  Represents a database record, with fields, used in the XML API.
 *
 *@author     matt rajkowski
 *@created    May 29, 2002
 *@version    $Id$
 */
public class Record extends LinkedHashMap {

  private int recordId = -1;
  private String action = null;


  /**
   *  Constructor for the Record object
   */
  public Record() { }


  /**
   *  Constructor for the Record object
   *
   *@param  thisAction  Description of Parameter
   */
  public Record(String thisAction) {
    action = thisAction;
  }


  /**
   *  Sets the action attribute of the Record object
   *
   *@param  tmp  The new action value
   */
  public void setAction(String tmp) {
    this.action = tmp;
  }


  /**
   *  Sets the recordId attribute of the Record object
   *
   *@param  tmp  The new recordId value
   */
  public void setRecordId(int tmp) {
    this.recordId = tmp;
  }


  /**
   *  Sets the recordId attribute of the Record object
   *
   *@param  tmp  The new recordId value
   */
  public void setRecordId(Integer tmp) {
    this.recordId = tmp.intValue();
  }


  /**
   *  Sets the recordId attribute of the Record object
   *
   *@param  tmp  The new recordId value
   */
  public void setRecordId(String tmp) {
    this.recordId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the action attribute of the Record object
   *
   *@return    The action value
   */
  public String getAction() {
    return action;
  }


  /**
   *  Gets the recordId attribute of the Record object
   *
   *@return    The recordId value
   */
  public int getRecordId() {
    return recordId;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasAction() {
    return (action != null);
  }
}

