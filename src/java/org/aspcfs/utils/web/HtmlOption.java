package org.aspcfs.utils.web;

import java.util.*;

/**
 *  Represents a Html Option component of a combo box
 *
 *@author     akhi_m
 *@created    June 9, 2003
 *@version    $Id$
 */
public class HtmlOption {

  String value = null;
  String text = null;
  String jsEvent = null;
  HashMap attributeList = null;


  /**
   *  Constructor for the Option object
   *
   *@param  value  Description of the Parameter
   *@param  text   Description of the Parameter
   */
  public HtmlOption(String value, String text) {
    this.value = value;
    this.text = text;
  }


  /**
   *  Constructor for the HtmlOption object
   *
   *@param  value    Description of the Parameter
   *@param  text     Description of the Parameter
   *@param  jsEvent  Description of the Parameter
   */
  public HtmlOption(String value, String text, String jsEvent) {
    this.value = value;
    this.text = text;
    this.jsEvent = jsEvent;
  }


  /**
   *  Constructor for the HtmlOption object
   *
   *@param  value          Description of the Parameter
   *@param  text           Description of the Parameter
   *@param  attributeList  Description of the Parameter
   */
  public HtmlOption(String value, String text, HashMap attributeList) {
    this.value = value;
    this.text = text;
    this.attributeList = attributeList;
  }


  /**
   *  Sets the value attribute of the Option object
   *
   *@param  value  The new value value
   */
  public void setValue(String value) {
    this.value = value;
  }


  /**
   *  Sets the jsEvent attribute of the Option object
   *
   *@param  jsEvent  The new jsEvent value
   */
  public void setJsEvent(String jsEvent) {
    this.jsEvent = jsEvent;
  }


  /**
   *  Sets the text attribute of the HtmlOption object
   *
   *@param  text  The new text value
   */
  public void setText(String text) {
    this.text = text;
  }


  /**
   *  Sets the attributeList attribute of the HtmlOption object
   *
   *@param  attributeList  The new attributeList value
   */
  public void setAttributeList(HashMap attributeList) {
    this.attributeList = attributeList;
  }


  /**
   *  Gets the attributeList attribute of the HtmlOption object
   *
   *@return    The attributeList value
   */
  public HashMap getAttributeList() {
    return attributeList;
  }


  /**
   *  Gets the text attribute of the HtmlOption object
   *
   *@return    The text value
   */
  public String getText() {
    return text;
  }


  /**
   *  Gets the value attribute of the Option object
   *
   *@return    The value value
   */
  public String getValue() {
    return value;
  }


  /**
   *  Gets the jsEvent attribute of the Option object
   *
   *@return    The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   *  Adds a feature to the Attribute attribute of the Option object
   *
   *@param  attrName   The feature to be added to the Attribute attribute
   *@param  attrValue  The feature to be added to the Attribute attribute
   */
  public void addAttribute(String attrName, String attrValue) {
    if (attributeList == null) {
      attributeList = new HashMap();
    }
    attributeList.put(attrName, attrValue);
  }


  /**
   *  Gets the attributeList attribute of the Option object
   *
   *@return    The attributeList value
   */
  public String getAttributes() {
    StringBuffer tmpList = new StringBuffer();
    if (attributeList != null) {
      Set s = attributeList.keySet();
      Iterator i = s.iterator();
      tmpList.append(" ");
      while (i.hasNext()) {
        Object name = i.next();
        tmpList.append(name.toString() + "='" + attributeList.get(name).toString() + "' ");
      }
    }
    return tmpList.toString();
  }
}

