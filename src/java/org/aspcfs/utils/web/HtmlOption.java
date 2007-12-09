/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.io.Serializable;

/**
 * Represents a Html Option component of a combo box
 *
 * @author akhi_m
 * @version $Id$
 * @created June 9, 2003
 */
public class HtmlOption implements Serializable {

  String value = null;
  String text = null;
  String jsEvent = null;
  HashMap attributeList = null;
  boolean group = false;
  boolean enabled = true;


  /**
   * Constructor for the HtmlOption object
   */
  public HtmlOption() {
  }


  /**
   * Constructor for the Option object
   *
   * @param value Description of the Parameter
   * @param text  Description of the Parameter
   */
  public HtmlOption(String value, String text) {
    this.value = value;
    this.text = text;
  }


  /**
   * Constructor for the HtmlOption object
   *
   * @param value   Description of the Parameter
   * @param text    Description of the Parameter
   * @param jsEvent Description of the Parameter
   */
  public HtmlOption(String value, String text, String jsEvent) {
    this.value = value;
    this.text = text;
    this.jsEvent = jsEvent;
  }


  /**
   * Constructor for the HtmlOption object
   *
   * @param value         Description of the Parameter
   * @param text          Description of the Parameter
   * @param attributeList Description of the Parameter
   */
  public HtmlOption(String value, String text, HashMap attributeList) {
    this.value = value;
    this.text = text;
    this.attributeList = attributeList;
  }


  /**
   * Constructor for the HtmlOption object
   *
   * @param value         Description of the Parameter
   * @param text          Description of the Parameter
   * @param attributeList Description of the Parameter
   * @param enabled       Description of the Parameter
   */
  public HtmlOption(String value, String text, HashMap attributeList, boolean enabled) {
    this.value = value;
    this.text = text;
    this.attributeList = attributeList;
    this.enabled = enabled;
  }


  /**
   * Sets the value attribute of the Option object
   *
   * @param value The new value value
   */
  public void setValue(String value) {
    this.value = value;
  }


  /**
   * Sets the jsEvent attribute of the Option object
   *
   * @param jsEvent The new jsEvent value
   */
  public void setJsEvent(String jsEvent) {
    this.jsEvent = jsEvent;
  }


  /**
   * Sets the text attribute of the HtmlOption object
   *
   * @param text The new text value
   */
  public void setText(String text) {
    this.text = text;
  }


  /**
   * Sets the attributeList attribute of the HtmlOption object
   *
   * @param attributeList The new attributeList value
   */
  public void setAttributeList(HashMap attributeList) {
    this.attributeList = attributeList;
  }


  /**
   * Gets the attributeList attribute of the HtmlOption object
   *
   * @return The attributeList value
   */
  public HashMap getAttributeList() {
    return attributeList;
  }


  /**
   * Sets the enabled attribute of the HtmlOption object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Gets the enabled attribute of the HtmlOption object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the text attribute of the HtmlOption object
   *
   * @return The text value
   */
  public String getText() {
    return text;
  }


  /**
   * Gets the value attribute of the Option object
   *
   * @return The value value
   */
  public String getValue() {
    return value;
  }


  /**
   * Gets the jsEvent attribute of the Option object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   * Sets the group attribute of the HtmlOption object
   *
   * @param tmp The new group value
   */
  public void setGroup(boolean tmp) {
    this.group = tmp;
  }


  /**
   * Gets the group attribute of the HtmlOption object
   *
   * @return The group value
   */
  public boolean getGroup() {
    return group;
  }


  /**
   * Gets the group attribute of the HtmlOption object
   *
   * @return The group value
   */
  public boolean isGroup() {
    return group;
  }


  /**
   * Adds a feature to the Attribute attribute of the Option object
   *
   * @param attrName  The feature to be added to the Attribute attribute
   * @param attrValue The feature to be added to the Attribute attribute
   */
  public void addAttribute(String attrName, String attrValue) {
    if (attributeList == null) {
      attributeList = new HashMap();
    }
    attributeList.put(attrName, attrValue);
  }


  /**
   * Gets the attributeList attribute of the Option object
   *
   * @return The attributeList value
   */
  public String getAttributes() {
    StringBuffer tmpList = new StringBuffer();
    if (attributeList != null) {
      Set s = attributeList.keySet();
      Iterator i = s.iterator();
      tmpList.append(" ");
      while (i.hasNext()) {
        Object name = i.next();
        tmpList.append(
            name.toString() + "='" + attributeList.get(name).toString() + "' ");
      }
    }
    return tmpList.toString();
  }
}

