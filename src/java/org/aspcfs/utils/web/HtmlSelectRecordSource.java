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

import java.util.*;
import java.sql.*;

/**
 *  Generates an HtmlSelect for choosing a record source
 *
 *@author     matt rajkowski
 *@created    October 13, 2003
 *@version    $Id: HtmlSelectRecordSource.java,v 1.1 2003/10/13 18:48:38
 *      mrajkowski Exp $
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

