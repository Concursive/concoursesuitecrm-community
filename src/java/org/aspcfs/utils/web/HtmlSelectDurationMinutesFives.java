package org.aspcfs.utils.web;

import java.util.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    July 7, 2003
 *@version    $Id: HtmlSelectMinutesQuarterly.java,v 1.1 2003/07/02 05:25:00
 *      matt Exp $
 */
public class HtmlSelectDurationMinutesFives {

  /**
  *  Gets the select attribute of the HtmlSelectDurationMinutesFives class
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
    select.addItem("5");
    select.addItem("10");
    select.addItem("15");
    select.addItem("20");
    select.addItem("25");
    select.addItem("30");
    select.addItem("35");
    select.addItem("40");
    select.addItem("45");
    select.addItem("50");
    select.addItem("55");
    return select;
  }
}
