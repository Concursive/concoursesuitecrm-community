package com.darkhorseventures.utils;

import java.util.*;

/**
 *  Maintains a list of records copied from a ResultSet.  Used for the
 *  XML API.
 *
 *@author     matt rajkowski
 *@created    May 29, 2002
 *@version    $Id$
 */
public class RecordList extends ArrayList {

  private String name = null;
  private int totalRecords = -1;


  /**
   *  Constructor for the RecordList object
   *
   *@param  tableName  Description of Parameter
   */
  public RecordList(String tableName) {
    name = tableName;
  }


  /**
   *  Sets the name attribute of the RecordList object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the totalRecords attribute of the RecordList object
   *
   *@param  tmp  The new totalRecords value
   */
  public void setTotalRecords(int tmp) {
    this.totalRecords = tmp;
  }


  /**
   *  Gets the name attribute of the RecordList object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the totalRecords attribute of the RecordList object
   *
   *@return    The totalRecords value
   */
  public int getTotalRecords() {
    return totalRecords;
  }

}

