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



/**
 * @author matt rajkowski
 * @version $Id$
 * @created July 2, 2003
 */
public class HtmlSelectDurationHours {

  /**
   * Gets the select attribute of the HtmlSelectDurationHours class
   *
   * @param name         Description of the Parameter
   * @param defaultValue Description of the Parameter
   * @return The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    select.addItem("0");
    select.addItem("1");
    select.addItem("2");
    select.addItem("3");
    select.addItem("4");
    select.addItem("5");
    select.addItem("6");
    select.addItem("7");
    select.addItem("8");
    select.addItem("9");
    select.addItem("10");
    select.addItem("11");
    select.addItem("12");
    select.addItem("13");
    select.addItem("14");
    select.addItem("15");
    select.addItem("16");
    select.addItem("17");
    select.addItem("18");
    select.addItem("19");
    select.addItem("20");
    select.addItem("21");
    select.addItem("22");
    select.addItem("23");
    return select;
  }
}

