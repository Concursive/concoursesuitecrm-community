package com.darkhorseventures.utils;

import java.util.*;

/**
 *  A class for merging a text template with variable elements.
 *
 *@author     Matt Rajkowski
 *@created    March 15, 2002
 */
public class Template {

  public static final int HTMLEncoding = 1;
  public static final int XMLEncoding = 2;
  
  HashMap parseElements = null;
  String text = null;
  int valueEncoding = -1;


  /**
   *  Constructor for the Template object
   */
  public Template() { }
  
  public Template(String theText) {
    text = theText;
  }

  public void setValueEncoding(int tmp) { this.valueEncoding = tmp; }
  public int getValueEncoding() { return valueEncoding; }

  /**
   *  Sets the parseElements attribute of the Template object
   *
   *@param  tmp  The new parseElements value
   */
  public void setParseElements(HashMap tmp) {
    this.parseElements = tmp;
  }


  /**
   *  Sets the text attribute of the Template object
   *
   *@param  tmp  The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }


  /**
   *  Gets the parseElements attribute of the Template object
   *
   *@return    The parseElements value
   */
  public HashMap getParseElements() {
    return parseElements;
  }


  /**
   *  Gets the text attribute of the Template object
   *
   *@return    The text value
   */
  public String getText() {
    return text;
  }


  /**
   *  Gets the parsedText attribute of the Template object
   *
   *@return    The parsedText value
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
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Template-> Replacing text: " + key + ", " + value);
        }
        text = StringHelper.replace(text, key, value);
      }
    }
    return text;
  }


  /**
   *  Adds a feature to the ParseElement attribute of the Template object
   *
   *@param  key    The feature to be added to the ParseElement attribute
   *@param  value  The feature to be added to the ParseElement attribute
   */
  public void addParseElement(String key, int value) {
    this.addParseElement(key, String.valueOf(value));
  }


  /**
   *  Adds a feature to the ParseElement attribute of the Template object
   *
   *@param  key    The feature to be added to the ParseElement attribute
   *@param  value  The feature to be added to the ParseElement attribute
   */
  public void addParseElement(String key, String value) {
    if (parseElements == null) {
      parseElements = new HashMap();
    }
    parseElements.put(key, value);
  }

}

