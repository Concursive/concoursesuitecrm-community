package org.aspcfs.utils.web;

import java.util.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    July 2, 2003
 *@version    $Id$
 */
public class HtmlSelectHours {

  /**
   *  Gets the select attribute of the HtmlSelectHours class
   *
   *@param  name          Description of the Parameter
   *@param  defaultValue  Description of the Parameter
   *@return               The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    select.addItem("12");
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
    return select;
  }
}

