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

import org.aspcfs.utils.StringUtils;

/**
 *  Presents an HTML language selection based on implemented Java languages
 *
 *@author     matt rajkowski
 *@created    March 18, 2004
 *@version    $Id: HtmlSelectLanguage.java,v 1.2 2004/07/21 19:00:44 mrajkowski
 *      Exp $
 */
public class HtmlSelectLanguage {

  /**
   *  Gets the select attribute of the HtmlSelectLanguage class
   *
   *@param  name          Description of the Parameter
   *@param  defaultValue  Description of the Parameter
   *@return               The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    if (defaultValue == null) {
      defaultValue = "en_US";
    }
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    select.addItem("zh", "Chinese");
    select.addItem("hr_HR", "Croat");
    select.addItem("da", "Dansk");
    select.addItem("de_DE", "Deutsch");
    //select.addItem("de_DE_EURO", "Deutsch (EURO)");
    select.addItem("en_AU", "English - AU");
    select.addItem("en_CA", "English - CA");
    select.addItem("en_IE", "English - EU");
    select.addItem("en_GB", "English - UK");
    select.addItem("en_US", "English - US");
    select.addItem("es", StringUtils.toHtml("Espa\u00F1ol"));
    select.addItem("fr_FR", StringUtils.toHtml("Fran\u00E7ais - FR"));
    //select.addItem("fr_FR_EURO", StringUtils.toHtml("Fran\u00E7ais (EURO)"));
    select.addItem("fr_CA", StringUtils.toHtml("Fran\u00E7ais - CA"));
    select.addItem("it", "Italiano");
    select.addItem("ja", "Japanese");
    select.addItem("nl", "Nederlands");
    select.addItem("pl_PL", "Polish");
    select.addItem("pt", "Portuguese");
    select.addItem("ru", "Russian");
    select.addItem("sl_SI", "Slovene");
    select.addItem("sv", "Svenska");
    return select;
  }
}

