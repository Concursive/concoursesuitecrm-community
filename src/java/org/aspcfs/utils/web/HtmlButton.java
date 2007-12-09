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

import org.w3c.dom.Element;

/**
 * Description of the Class
 *
 * @author akhi_m
 * @version $Id$
 * @created October 14, 2002
 */
public class HtmlButton {

  private String name = null;
  private String value = null;
  private String onClick = null;
  private String jsEvent = null;
  private boolean enabled = true;
  //submit(2) type or button(1) type
  private int type = 1;


  /**
   * Constructor for the HtmlButton object
   */
  public HtmlButton() {
  }


  /**
   * Constructor for the HtmlButton object
   *
   * @param button Description of the Parameter
   */
  public HtmlButton(Element button) {
    processXMLButton(button);
  }


  /**
   * Sets the name attribute of the HtmlButton object
   *
   * @param name The new name value
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   * Sets the value attribute of the HtmlButton object
   *
   * @param value The new value value
   */
  public void setValue(String value) {
    this.value = value;
  }


  /**
   * Sets the onChange attribute of the HtmlButton object
   *
   * @param onClick The new onClick value
   */
  public void setOnClick(String onClick) {
    this.onClick = onClick;
  }


  /**
   * Sets the jsEvent attribute of the HtmlButton object
   *
   * @param jsEvent The new jsEvent value
   */
  public void setJsEvent(String jsEvent) {
    this.jsEvent = jsEvent;
  }


  /**
   * Sets the type attribute of the HtmlButton object
   *
   * @param type The new type value
   */
  public void setType(int type) {
    this.type = type;
  }


  /**
   * Sets the enabled attribute of the HtmlButton object
   *
   * @param enabled The new enabled value
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }


  /**
   * Gets the enabled attribute of the HtmlButton object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the name attribute of the HtmlButton object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the value attribute of the HtmlButton object
   *
   * @return The value value
   */
  public String getValue() {
    return value;
  }


  /**
   * Gets the onChange attribute of the HtmlButton object
   *
   * @return The onChange value
   */
  public String getOnClick() {
    return onClick;
  }


  /**
   * Gets the jsEvent attribute of the HtmlButton object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   * Gets the type attribute of the HtmlButton object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }


  /**
   * Gets the typeString attribute of the HtmlButton object
   *
   * @return The typeString value
   */
  public String getTypeString() {
    if (type == 1) {
      return "button";
    } else {
      return "submit";
    }
  }


  /**
   * Description of the Method
   *
   * @param button Description of the Parameter
   */
  public void processXMLButton(Element button) {
    if (button != null) {
      this.setName(button.getAttribute("name"));
      this.setValue(button.getAttribute("display"));
      this.setOnClick(
          button.getAttribute("onClick") != null ? button.getAttribute(
              "onclick") : "");
      this.setType(Integer.parseInt(button.getAttribute("type")));
      this.setEnabled(
          button.getAttribute("enabled").equalsIgnoreCase("no") ? false : true);
    }
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public String toString() {
    return "<input type=\"" + this.getTypeString() + "\" name=\"" + this.getName() + "\" value=\"" + this.getValue() + "\" onClick=\"" + this.getOnClick() + "\"" + (this.getEnabled() ? "" : " disabled") + ">";
  }
}


