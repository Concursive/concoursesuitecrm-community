package org.aspcfs.utils.web;

import java.util.*;
import java.sql.*;

/**
 *  Generates an HtmlSelect for choosing a record source
 *
 *@author     matt rajkowski
 *@created    October 13, 2003
 *@version    $Id$
 */
public class HtmlSelectRecordSource {

  /**
   *  Gets the select attribute of the HtmlSelectProbabilityRange class
   *
   *@param  name          Description of the Parameter
   *@param  defaultValue  Description of the Parameter
   *@return               The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    populateSelect(select);
    return select;
  }


  /**
   *  Gets the valueFromId attribute of the HtmlSelectProbabilityRange class
   *
   *@param  key  Description of the Parameter
   *@return      The valueFromId value
   */
  public static String getValueFromId(String key) {
    HtmlSelect select = new HtmlSelect();
    populateSelect(select);
    return select.getValueFromId(key);
  }


  /**
   *  Generates a list of choices for selecting a range of probabilities
   *
   *@param  select  Description of the Parameter
   */
  public static void populateSelect(HtmlSelect select) {
    select.addItem("my", "My Records");
    select.addItem("hierarchy", "Controlled-Hierarchy Records");
    //select.addItem("all", "All Records");
  }
}

