package org.aspcfs.utils.web;

import java.util.*;
import java.sql.*;
import java.text.NumberFormat;

/**
 *  Presents an HTML currency selection based on allowed Java currencies
 *
 *@author     matt rajkowski
 *@created    March 17, 2004
 *@version    $Id: HtmlSelectCurrencyCode.java,v 1.2 2004/03/19 04:56:17 matt
 *      Exp $
 */
public class HtmlSelectCurrencyCode {

  /**
   *  Constructor for the HtmlSelectCurrencyCode object
   */
  public HtmlSelectCurrencyCode() { }


  /**
   *  Gets the select attribute of the HtmlSelectCurrencyCode class
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
    // TODO: Sort these items
    select.addItem("AUD");
    //select.addItem("ATS");
    //select.addItem("BHD");
    //select.addItem("BEF");
    select.addItem("CAD");
    //select.addItem("XPF");
    //select.addItem("CYP");
    //select.addItem("DKK");
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
    select.addItem("JPY");
    //select.addItem("KES");
    //select.addItem("KWD");
    //select.addItem("MTL");
    //select.addItem("NLG");
    //select.addItem("NZD");
    //select.addItem("NOK");
    //select.addItem("PGK");
    //select.addItem("PKR");
    //select.addItem("PHP");
    //select.addItem("PTE");
    //select.addItem("SAR");
    //select.addItem("SGD");
    //select.addItem("ESP");
    //select.addItem("LKR");
    //select.addItem("ZAR");
    //select.addItem("SEK");
    //select.addItem("CHF");
    //select.addItem("THB");
    //select.addItem("AED");
    select.addItem("USD");
    //select.addItem("WST");
    if (!select.hasKey(defaultValue)) {
      select.addItem(defaultValue);
    }
    return select;
  }
}

