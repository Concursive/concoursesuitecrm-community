package org.aspcfs.utils.web;

import java.util.*;
import java.sql.*;
import java.text.NumberFormat;

/**
 *  Presents an HTML currency selection based on allowed Java currencies
 *
 *@author     matt rajkowski
 *@created    March 17, 2004
 *@version    $Id$
 */
public class HtmlSelectCurrency {

  /**
   *  Gets the select attribute of the HtmlSelectCurrency class
   *
   *@param  name          Description of the Parameter
   *@param  defaultValue  Description of the Parameter
   *@return               The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    if (defaultValue == null) {
      defaultValue = NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode();
    }
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    select.addItem("AUD", "Australian Currency (AUD)");
    //select.addItem("ATS", "Austrian Schillings (ATS)");
    //select.addItem("BHD", "Bahrain Dinars (BHD)");
    //select.addItem("BEF", "Belgian Franc (BEF)");
    select.addItem("CAD", "Canadian Dollars (CAD)");
    //select.addItem("XPF", "Cfp Francs (XPF)");
    //select.addItem("CYP", "Cyprus Pounds (CYP)");
    //select.addItem("DKK", "Danish Kroner (DKK)");
    select.addItem("EUR", "European Currency (EUR)");
    //select.addItem("FIM", "Finnish Markka (FIM)");
    //select.addItem("FRF", "French Francs (FRF)");
    //select.addItem("DEM", "German D'Marks (DEM)");
    //select.addItem("GRD", "Greek Drachma (GRD)");
    //select.addItem("HKD", "Hong Kong Dollars (HKD)");
    //select.addItem("INR", "Indian Rupees (INR)");
    //select.addItem("IEP", "Irish Pounds (IEP)");
    //select.addItem("ITL", "Italian Lira (ITL)");
    select.addItem("JPY", "Japanese Yen (JPY)");
    //select.addItem("KES", "Kenyan Shilling (KES)");
    //select.addItem("KWD", "Kuwaiti Dinars (KWD)");
    //select.addItem("MTL", "Maltese Lira (MTL)");
    //select.addItem("NLG", "Netherlands Guilder (NLG)");
    //select.addItem("NZD", "New Zealand Dollars (NZD)");
    //select.addItem("NOK", "Norwegian Krone (NOK)");
    //select.addItem("PGK", "P.N.G. Kina (PGK)");
    //select.addItem("PKR", "Pakistani Rupees (PKR)");
    //select.addItem("PHP", "Philippine Pesos (PHP)");
    //select.addItem("PTE", "Portugese Escudos (PTE)");
    //select.addItem("SAR", "Saudi Arabian Riyals (SAR)");
    //select.addItem("SGD", "Singapore Dollars (SGD)");
    //select.addItem("ESP", "Spanish Pesetas (ESP)");
    //select.addItem("LKR", "Sri Lanka Rupees (LKR)");
    //select.addItem("ZAR", "South African Rand (ZAR)");
    //select.addItem("SEK", "Swedish Krona (SEK)");
    //select.addItem("CHF", "Swiss Francs (CHF)");
    //select.addItem("THB", "Thai Bahts (THB)");
    //select.addItem("AED", "Uae Dirhams (AED)");
    select.addItem("GBP", "UK Pounds Sterling (GBP)");
    select.addItem("USD", "United States Dollar (USD)");
    //select.addItem("WST", "Western Samoa Tala (WST)");
    if (!select.hasKey(defaultValue)) {
      select.addItem(defaultValue);
    }
    return select;
  }
}

