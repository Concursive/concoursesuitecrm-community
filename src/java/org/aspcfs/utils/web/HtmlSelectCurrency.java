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

import java.text.NumberFormat;

/**
 * Presents an HTML currency selection based on allowed Java currencies
 *
 * @author matt rajkowski
 * @version $Id: HtmlSelectCurrency.java,v 1.2 2004/07/21 19:00:44 mrajkowski
 *          Exp $
 * @created March 17, 2004
 */
public class HtmlSelectCurrency {

  /**
   * Gets the select attribute of the HtmlSelectCurrency class
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
    select.addItem("ARS", "Argentine Peso (ARS)");
    select.addItem("AUD", "Australian Dollar (AUD)");
    //select.addItem("ATS", "Austrian Schillings (ATS)");
    //select.addItem("BHD", "Bahrain Dinars (BHD)");
    //select.addItem("BEF", "Belgian Franc (BEF)");
    select.addItem("BRL", "Brazilian Real (BRL)");
    select.addItem("CAD", "Canadian Dollar (CAD)");
    select.addItem("CLP", "Chilean Peso (CLP)");
    select.addItem("COP", "Columbian Peso (COP)");
    select.addItem("CNY", "Chinese Yuan Renminbi (CNY)");
    //select.addItem("XPF", "Cfp Francs (XPF)");
    select.addItem("HRK", "Croatian Kuna (HRK)");
    //select.addItem("CYP", "Cyprus Pounds (CYP)");
    //select.addItem("DKK", "Danish Kroner (DKK)");
    select.addItem("EUR", "European Currency (EUR)");
    //select.addItem("FIM", "Finnish Markka (FIM)");
    //select.addItem("FRF", "French Francs (FRF)");
    //select.addItem("DEM", "German D'Marks (DEM)");
    //select.addItem("GRD", "Greek Drachma (GRD)");
    //select.addItem("HKD", "Hong Kong Dollars (HKD)");
    select.addItem("HUF", "Hungarian Forint");
    //select.addItem("INR", "Indian Rupees (INR)");
    //select.addItem("IEP", "Irish Pounds (IEP)");
    //select.addItem("ITL", "Italian Lira (ITL)");
    select.addItem("JPY", "Japanese Yen (JPY)");
    //select.addItem("KES", "Kenyan Shilling (KES)");
    //select.addItem("KWD", "Kuwaiti Dinars (KWD)");
    //select.addItem("LVL", "Latvian Lats (LVL)");
    select.addItem("LTL", "Lithuanian Litas (LTL)");
    //select.addItem("MTL", "Maltese Lira (MTL)");
    select.addItem("MXP", "Mexican Peso (MXP)");
    //select.addItem("NLG", "Netherlands Guilder (NLG)");
    //select.addItem("NZD", "New Zealand Dollars (NZD)");
    //select.addItem("NOK", "Norwegian Krone (NOK)");
    //select.addItem("PGK", "P.N.G. Kina (PGK)");
    //select.addItem("PKR", "Pakistani Rupees (PKR)");
    //select.addItem("PHP", "Philippine Pesos (PHP)");
    select.addItem("PTE", "Portugese Escudos (PTE)");
    select.addItem("PEN", "Peruvian Nuevo Sol (PEN)");
    //select.addItem("SAR", "Saudi Arabian Riyals (SAR)");
    //select.addItem("SGD", "Singapore Dollars (SGD)");
    select.addItem("ESP", "Spanish Pesetas (ESP)");
    //select.addItem("LKR", "Sri Lanka Rupees (LKR)");
    //select.addItem("ZAR", "South African Rand (ZAR)");
    //select.addItem("SEK", "Swedish Krona (SEK)");
    //select.addItem("CHF", "Swiss Francs (CHF)");
    select.addItem("THB", "Thai Bahts (THB)");
    //select.addItem("AED", "Uae Dirhams (AED)");
    select.addItem("GBP", "UK Pounds Sterling (GBP)");
    select.addItem("UAH", "Ukrainian Grivna (UAH)");
    select.addItem("USD", "United States Dollar (USD)");
    select.addItem("SIT", "Slovenian Tolar (SIT)");
    select.addItem("THB", "Thai Baht (THB)");
    select.addItem("VEB", StringUtils.toHtml("Venezuelan Bol\u00edvar (VEB)"));
    //select.addItem("WST", "Western Samoa Tala (WST)");
    if (!select.hasKey(defaultValue)) {
      select.addItem(defaultValue);
    }
    return select;
  }
}

