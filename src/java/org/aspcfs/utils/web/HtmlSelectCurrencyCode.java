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

import java.text.NumberFormat;

/**
 * Presents an HTML currency selection based on allowed Java currencies
 *
 * @author matt rajkowski
 * @version $Id: HtmlSelectCurrencyCode.java,v 1.2 2004/03/19 04:56:17 matt
 *          Exp $
 * @created March 17, 2004
 */
public class HtmlSelectCurrencyCode {

  /**
   * Constructor for the HtmlSelectCurrencyCode object
   */
  public HtmlSelectCurrencyCode() {
  }


  /**
   * Gets the select attribute of the HtmlSelectCurrencyCode class
   *
   * @param name         Description of the Parameter
   * @param defaultValue Description of the Parameter
   * @return The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    if (defaultValue == null) {
      defaultValue = NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode();
    }
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    // TODO: Sort these items
    select.addItem("ARS");
    select.addItem("AUD");
    //select.addItem("ATS");
    //select.addItem("BHD");
    //select.addItem("BEF");
    select.addItem("BRL");
    select.addItem("CAD");
    select.addItem("CLP");
    select.addItem("CNY");
    select.addItem("COP");
    //select.addItem("XPF");
    //select.addItem("CYP");
    //select.addItem("DKK");
    select.addItem("ESP");
    select.addItem("EUR");
    //select.addItem("FIM");
    //select.addItem("FRF");
    //select.addItem("DEM");
    //select.addItem("GRD");
    //select.addItem("HKD");
    //select.addItem("INR");
    //select.addItem("IEP");
    //select.addItem("ITL");
    select.addItem("GBP");
    select.addItem("HUF");
    select.addItem("JPY");
    //select.addItem("KES");
    //select.addItem("KWD");
    select.addItem("LTL");
    //select.addItem("MTL");
    select.addItem("MXP");
    //select.addItem("NLG");
    //select.addItem("NZD");
    //select.addItem("NOK");
    //select.addItem("PGK");
    //select.addItem("PKR");
    //select.addItem("PHP");
    select.addItem("PEN");
    select.addItem("PTE");
    //select.addItem("SAR");
    //select.addItem("SGD");
    //select.addItem("ESP");
    //select.addItem("LKR");
    //select.addItem("ZAR");
    //select.addItem("SEK");
    //select.addItem("CHF");
    //select.addItem("THB");
    //select.addItem("AED");
    select.addItem("SIT");
    select.addItem("THB");
    select.addItem("UAH");
    select.addItem("USD");
    select.addItem("VEB");
    //select.addItem("WST");
    if (!select.hasKey(defaultValue)) {
      select.addItem(defaultValue);
    }
    return select;
  }
}

