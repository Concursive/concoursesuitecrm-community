package org.aspcfs.utils.web;

import java.util.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski (matt.rajkowski@teamelements.com)
 *@created    March 24, 2004
 *@version    $Id$
 */
public class HtmlSelectHours24 {

  /**
   *  Gets the select attribute of the HtmlSelectHours24 class
   *
   *@param  name          Description of the Parameter
   *@param  defaultValue  Description of the Parameter
   *@return               The select value
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

