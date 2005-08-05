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
 * Presents an HTML language selection based on implemented Java languages
 *
 * @author matt rajkowski
 * @version $Id: HtmlSelectLanguage.java,v 1.2 2004/07/21 19:00:44 mrajkowski
 *          Exp $
 * @created March 18, 2004
 */
public class HtmlSelectLanguage {

  /**
   * Gets the select attribute of the HtmlSelectLanguage class
   *
   * @param name         Description of the Parameter
   * @param defaultValue Description of the Parameter
   * @return The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    if (defaultValue == null) {
      defaultValue = "en_US";
    }
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    // Chinese - Simplified ????
    select.addItem("sq_AL", StringUtils.toHtml("Alban\u00e9s"));
    select.addItem("zh_CN", StringUtils.toHtml("\u7b80\u4f53\u4e2d\u6587"));
    select.addItem("zh_TW", StringUtils.toHtml("Chinese Traditional"));
    // Croat (Croatian)
    select.addItem("hr_HR", "hrvatski (Hrvatska)");
    select.addItem("da_DK", "Dansk");
    // German
    select.addItem("de_DE", "Deutsch");
    //select.addItem("de_DE_EURO", "Deutsch (EURO)");
    select.addItem("en_AU", "English - AU");
    select.addItem("en_CA", "English - CA");
    select.addItem("en_IE", "English - EU");
    select.addItem("en_GB", "English - UK");
    select.addItem("en_US", "English - US");
    //Spanish
    select.addItem("es_VE", StringUtils.toHtml("Espa\u00f1ol - VE"));
    //French
    select.addItem("fr_FR", StringUtils.toHtml("Fran\u00e7ais - FR"));
    //select.addItem("fr_FR_EURO", StringUtils.toHtml("Fran\u00E7ais (EURO)"));
    select.addItem("fr_CA", StringUtils.toHtml("Fran\u00e7ais - CA"));
    //Greek
    select.addItem(
        "el_GR", StringUtils.toHtml(
            "\u0395\u03bb\u03bb\u03b7\u03bd\u03b9\u03ba\u03ac"));
    //Italian
    select.addItem("it_IT", "Italiano");
    //Japanese
    select.addItem("ja_JP", "Japanese");
    //Korean "ko" \uc548\ub155\ud558\uc138\uc694\uc138\uacc4
    select.addItem("lt_LT", StringUtils.toHtml("Lietuvi\u0161kai"));
    select.addItem("nl_NL", "Nederlands");
    select.addItem("pl_PL", "Polish");
    select.addItem("pt_BR", "Portuguese - BR");
    select.addItem(
        "ru_RU", StringUtils.toHtml(
            "\u0420\u0443\u0441\u0441\u043a\u0438\u0439"));
    select.addItem("sl_SI", "Slovene");
    select.addItem("sv_SE", "Svenska");
    select.addItem(
        "th_TH", StringUtils.toHtml(
            "\u0e20\u0e32\u0e29\u0e32\u0e44\u0e17\u0e22"));
    select.addItem("tr_TR", StringUtils.toHtml("T\u00fcrk\u00e7e"));
    return select;
  }
}

