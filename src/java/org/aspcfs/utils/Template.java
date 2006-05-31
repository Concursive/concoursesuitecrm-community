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
package org.aspcfs.utils;

import org.aspcfs.controller.SystemStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A class for merging a text template with variable elements.
 *
 * @author     Matt Rajkowski
 * @version $Id$
 * @created March 15, 2002
 */
public class Template {

  public final static int HTMLEncoding = 1;
  public final static int XMLEncoding = 2;

  private HashMap parseElements = null;
  private String text = null;
  private int valueEncoding = -1;


  /**
   * Constructor for the Template object
   */
  public Template() {
  }


  /**
   * Constructor for the Template object
   *
   * @param  theText  Description of the Parameter
   */
  public Template(String theText) {
    text = theText;
  }


  /**
   * Sets the valueEncoding attribute of the Template object
   *
   * @param  tmp  The new valueEncoding value
   */
  public void setValueEncoding(int tmp) {
    this.valueEncoding = tmp;
  }


  /**
   * Gets the valueEncoding attribute of the Template object
   *
   * @return    The valueEncoding value
   */
  public int getValueEncoding() {
    return valueEncoding;
  }


  /**
   * Sets the parseElements attribute of the Template object
   *
   * @param  tmp  The new parseElements value
   */
  public void setParseElements(HashMap tmp) {
    this.parseElements = tmp;
  }


  /**
   * Sets the text attribute of the Template object
   *
   * @param  tmp  The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }


  /**
   * Gets the parseElements attribute of the Template object
   *
   * @return    The parseElements value
   */
  public HashMap getParseElements() {
    return parseElements;
  }


  /**
   * Gets the text attribute of the Template object
   *
   * @return    The text value
   */
  public String getText() {
    return text;
  }


  /**
   * Gets the parsedText attribute of the Template object
   *
   * @return    The parsedText value
   */
  public String getParsedText() {
    if (parseElements != null) {
      Iterator elements = parseElements.keySet().iterator();
      while (elements.hasNext()) {
        String key = (String) elements.next();
        String value = (String) parseElements.get(key);
        if (valueEncoding == HTMLEncoding) {
          value = HTTPUtils.toHtmlValue(value);
        } else if (valueEncoding == XMLEncoding) {
          value = XMLUtils.toXMLValue(value);
        }
        text = StringUtils.replace(text, key, value);
      }
    }
    return text;
  }


  /**
   * Adds a feature to the ParseElement attribute of the Template object
   *
   * @param  key    The feature to be added to the ParseElement attribute
   * @param  value  The feature to be added to the ParseElement attribute
   */
  public void addParseElement(String key, int value) {
    this.addParseElement(key, String.valueOf(value));
  }


  /**
   * Adds a feature to the ParseElement attribute of the Template object
   *
   * @param  key    The feature to be added to the ParseElement attribute
   * @param  value  The feature to be added to the ParseElement attribute
   */
  public void addParseElement(String key, String value) {
    if (parseElements == null) {
      parseElements = new HashMap();
    }
    if (value != null) {
      parseElements.put(key, value);
    }
  }


  /**
   * Gets the value attribute of the Template object
   *
   * @param  key  Description of the Parameter
   * @return      The value value
   */
  public String getValue(String key) {
    String value = null;
    int keyIndex = text.indexOf("${" + key + "=");
    if (keyIndex > -1) {
      StringBuffer parsedValue = new StringBuffer();
      boolean start = false;
      boolean end = false;
      while (keyIndex < text.length() && !end) {
        String val = text.substring(keyIndex, ++keyIndex);
        if (!start && "=".equals(val)) {
          start = true;
        } else {
          if (start) {
            if ("}".equals(val)) {
              end = true;
            } else {
              parsedValue.append(val);
            }
          }
        }
      }
      if (parsedValue.length() > 0) {
        value = parsedValue.toString();
      }
    }
    return value;
  }


  /**
   * Gets the variables attribute of the Template object
   *
   * @return    The variables value
   */
  public ArrayList getVariables() {
    ArrayList variables = new ArrayList();
    if (text != null) {
      int posCount = 0;
      int loc = -1;
      while ((loc = text.indexOf("${", posCount)) > -1) {
        int endLoc = text.indexOf("}", loc);
        String variable = text.substring(loc + 2, endLoc);
        variables.add(variable);
        posCount = endLoc;
      }
    }
    return variables;
  }


  /**
   *  Description of the Method
   *
   * @param  input  Description of the Parameter
   * @return        Description of the Return Value
   */
  public boolean hasVariable(String input) {
    ArrayList variables = getVariables();
    Iterator i = variables.iterator();
    while (i.hasNext()) {
      String variable = (String) i.next();
      if (variable.equals(input)) {
        return true;
      }
    }
    return false;
  }


  /**
   *  Gets the keyValuePairs attribute of the Template object
   *
   * @return    The keyValuePairs value
   */
  public HashMap getKeyValuePairs() {
    HashMap keyValues = new HashMap();
    String key = "";
    String value = "";
    if (text != null) {
      int posCount = 0;
      int loc = -1;
      while ((loc = text.indexOf("${", posCount)) > -1) {
        int endLoc = text.indexOf("}", loc);
        if (text.substring(loc + 2, endLoc).indexOf("=") > 0) {
          key = text.substring(loc + 2, endLoc).split("=")[0];
          value = text.substring(loc + 2, endLoc).split("=")[1];
          keyValues.put(key, value);
        } else {
          keyValues.put(key, "");
        }
        posCount = endLoc;
      }
    }
    return keyValues;
  }


  /**
   * Description of the Method
   *
   * @param object Description of the Parameter
   */
  public void populateVariables(Object object) {
    ArrayList variables = getVariables();
    Iterator i = variables.iterator();
    while (i.hasNext()) {
      String variableName = (String) i.next();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Template-> populateVariables: " + variableName);
      }
      String value = ObjectUtils.getParam(object, variableName);
      if (value == null) {
        value = "";
      }
      addParseElement("${" + variableName + "}", value);
    }
  }


  /**
   * Description of the Method
   *
   * @param thisSystem Description of the Parameter
   */
  public void populateSystemVariables(SystemStatus thisSystem) {
    ArrayList variables = getVariables();
    Iterator i = variables.iterator();
    while (i.hasNext()) {
      String variableName = (String) i.next();
      String value = thisSystem.getLabel(variableName);
      if (value == null) {
        value = "";
      }
      addParseElement("${" + variableName + "}", value);
    }
  }
}

