package com.darkhorseventures.controller;

import java.util.ArrayList;

/**
 *  Description of the Class
 *
 *@author     chris price
 *@created    July 26, 2002
 *@version    $Id$
 */
public class CustomFormTab extends ArrayList {

  String name = "";
  String next = "";
  String prev = "";
  int id = 0;
  private String defaultField = null;


  /**
   *  Constructor for the CustomFormTab object
   */
  public CustomFormTab() { }


  /**
   *  Sets the name attribute of the CustomFormTab object
   *
   *@param  name  The new name value
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   *  Gets the name attribute of the CustomFormTab object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Sets the next attribute of the CustomFormTab object
   *
   *@param  tmp  The new next value
   */
  public void setNext(String tmp) {
    this.next = tmp;
  }


  /**
   *  Sets the prev attribute of the CustomFormTab object
   *
   *@param  tmp  The new prev value
   */
  public void setPrev(String tmp) {
    this.prev = tmp;
  }


  /**
   *  Gets the next attribute of the CustomFormTab object
   *
   *@return    The next value
   */
  public String getNext() {
    return next;
  }


  /**
   *  Gets the prev attribute of the CustomFormTab object
   *
   *@return    The prev value
   */
  public String getPrev() {
    return prev;
  }


  /**
   *  Gets the id attribute of the CustomFormTab object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the CustomFormTab object
   *
   *@param  id  The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the id attribute of the CustomFormTab object
   *
   *@param  id  The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   *  Sets the defaultField attribute of the CustomFormTab object
   *
   *@param  tmp  The new defaultField value
   */
  public void setDefaultField(String tmp) {
    this.defaultField = tmp;
  }


  /**
   *  Gets the defaultField attribute of the CustomFormTab object
   *
   *@return    The defaultField value
   */
  public String getDefaultField() {
    return defaultField;
  }
  
  public boolean hasDefaultField() {
    return (defaultField != null && !"".equals(defaultField));
  }

}

