package com.darkhorseventures.apps.dataimport.reader.cfsdatabasereader;

import java.util.ArrayList;

/**
 *  Contains a list of Property objects and defines an object Mapping
 *
 *@author     matt rajkowski
 *@created    September 18, 2002
 *@version    $Id$
 */
public class PropertyMap extends ArrayList {
  private String id = null;
  private String table = null;
  private String uniqueField = null;


  /**
   *  Sets the id attribute of the PropertyMap object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = tmp;
  }


  /**
   *  Gets the id attribute of the PropertyMap object
   *
   *@return    The id value
   */
  public String getId() {
    return id;
  }


  /**
   *  Sets the table attribute of the PropertyMap object
   *
   *@param  tmp  The new table value
   */
  public void setTable(String tmp) {
    this.table = tmp;
  }


  /**
   *  Gets the table attribute of the PropertyMap object
   *
   *@return    The table value
   */
  public String getTable() {
    return table;
  }


  /**
   *  Sets the uniqueField attribute of the PropertyMap object
   *
   *@param  tmp  The new uniqueField value
   */
  public void setUniqueField(String tmp) {
    this.uniqueField = tmp;
  }


  /**
   *  Gets the uniqueField attribute of the PropertyMap object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasTable() {
    return (table != null && !"".equals(table));
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasProperties() {
    return (this.size() > 0);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasUniqueField() {
    return (uniqueField != null && !"".equals(uniqueField));
  }

}

