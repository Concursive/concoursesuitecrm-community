package org.aspcfs.utils.web;

import java.util.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    July 2, 2003
 *@version    $Id$
 */
public class HtmlSelectDurationHours {

  /**
  *  Gets the select attribute of the HtmlSelectDurationHours class
  *
  *@param  name          Description of the Parameter
  *@param  defaultValue  Description of the Parameter
  *@return               The select value
  */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    select.addItem("0");
    select.addItem("1");
    select.addItem("2");
    select.addItem("3");
    select.addItem("4");
    select.addItem("5");
    select.addItem("6");
    select.addItem("7");
    select.addItem("8");
    select.addItem("9");
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
