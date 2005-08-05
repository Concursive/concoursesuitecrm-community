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



/**
 * Description of the Class
 *
 * @author matt rajkowski (matt.rajkowski@teamelements.com)
 * @version $Id: HtmlSelectHours24.java,v 1.2 2004/07/21 19:00:44 mrajkowski
 *          Exp $
 * @created March 24, 2004
 */
public class HtmlSelectHours24 {

  /**
   * Gets the select attribute of the HtmlSelectHours24 class
   *
   * @param name         Description of the Parameter
   * @param defaultValue Description of the Parameter
   * @return The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    select.addItem("00");
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

