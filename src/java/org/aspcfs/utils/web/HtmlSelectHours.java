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
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: HtmlSelectHours.java,v 1.2 2004/07/21 19:00:44 mrajkowski Exp
 *          $
 * @created July 2, 2003
 */
public class HtmlSelectHours {

  /**
   * Gets the select attribute of the HtmlSelectHours class
   *
   * @param name         Description of the Parameter
   * @param defaultValue Description of the Parameter
   * @return The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    select.addItem("12");
    select.addItem("01");
    select.addItem("02");
    select.addItem("03");
    select.addItem("04");
    select.addItem("05");
    select.addItem("06");
    select.addItem("07");
    select.addItem("08");
    select.addItem("09");
    select.addItem("10");
    select.addItem("11");
    return select;
  }
}

