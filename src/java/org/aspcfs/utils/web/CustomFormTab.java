/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils.web;

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
  private String onLoadEvent = null;
  private String returnLinkText = null;

  private StringBuffer buttonString = null;


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
   *  Sets the buttonString attribute of the CustomFormTab object
   *
   *@param  buttonString  The new buttonString value
   */
  public void setButtonString(StringBuffer buttonString) {
    this.buttonString = buttonString;
  }


  /**
   *  Sets the onLoadEvent attribute of the CustomFormTab object
   *
   *@param  onLoadEvent  The new onLoadEvent value
   */
  public void setOnLoadEvent(String onLoadEvent) {
    this.onLoadEvent = onLoadEvent;
  }


  /**
   *  Sets the returnLinkText attribute of the CustomFormTab object
   *
   *@param  returnLinkText  The new returnLinkText value
   */
  public void setReturnLinkText(String returnLinkText) {
    this.returnLinkText = returnLinkText;
  }


  /**
   *  Gets the returnLinkText attribute of the CustomFormTab object
   *
   *@return    The returnLinkText value
   */
  public String getReturnLinkText() {
    return returnLinkText;
  }


  /**
   *  Gets the onLoadEvent attribute of the CustomFormTab object
   *
   *@return    The onLoadEvent value
   */
  public String getOnLoadEvent() {
    return onLoadEvent;
  }


  /**
   *  Gets the buttonString attribute of the CustomFormTab object
   *
   *@return    The buttonString value
   */
  public String getButtonString() {
    return buttonString.toString();
  }


  /**
   *  Constructor for the addButton object
   *
   *@param  htmlButton  Description of the Parameter
   */
  public void addButton(String htmlButton) {
    if (buttonString == null) {
      buttonString = new StringBuffer();
    }
    buttonString.append("&nbsp;");
    buttonString.append(htmlButton);
    buttonString.append("&nbsp;");
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


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasDefaultField() {
    return (defaultField != null && !"".equals(defaultField));
  }

}

