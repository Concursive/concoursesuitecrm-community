package org.aspcfs.utils.web;

import java.util.*;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     Akhilesh Mathur
 *@created    July 5, 2004
 *Exp $
 *@version    $id:exp$
 */
public class HtmlSelectDurationAMPM {

  /**
   *  Gets the select attribute of the HtmlSelectAMPM class
   *
   *@param  name          Description of the Parameter
   *@param  defaultValue  Description of the Parameter
   *@return               The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    select.addItem(Calendar.AM, "AM");
    select.addItem(Calendar.PM, "PM");
    return select;
  }
}

