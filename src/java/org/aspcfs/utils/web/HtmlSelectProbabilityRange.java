/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils.web;



/**
 * Generates an HtmlSelect for choosing a percent range, for an opportunity
 * report
 *
 * @author matt rajkowski
 * @version $Id: HtmlSelectProbabilityRange.java,v 1.1.2.1 2003/10/09 15:57:19
 *          mrajkowski Exp $
 * @created October 6, 2003
 */
public class HtmlSelectProbabilityRange {

  /**
   * Gets the select attribute of the HtmlSelectProbabilityRange class
   *
   * @param name         Description of the Parameter
   * @param defaultValue Description of the Parameter
   * @return The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    populateSelect(select);
    return select;
  }


  /**
   * Gets the valueFromId attribute of the HtmlSelectProbabilityRange class
   *
   * @param key Description of the Parameter
   * @return The valueFromId value
   */
  public static String getValueFromId(String key) {
    HtmlSelect select = new HtmlSelect();
    populateSelect(select);
    return select.getValueFromId(key);
  }


  /**
   * Generates a list of choices for selecting a range of probabilities
   *
   * @param select Description of the Parameter
   */
  public static void populateSelect(HtmlSelect select) {
    select.addItem("-0.01|1.01", "All");
    select.addItem("0.1|1.01", "> 10%");
    select.addItem("0.2|1.01", "> 20%");
    select.addItem("0.3|1.01", "> 30%");
    select.addItem("0.4|1.01", "> 40%");
    select.addItem("0.5|1.01", "> 50%");
    select.addItem("0.6|1.01", "> 60%");
    select.addItem("0.7|1.01", "> 70%");
    select.addItem("0.8|1.01", "> 80%");
    select.addItem("0.9|1.01", "> 90%");
    select.addItem("-.01|0.9", "< 90%");
    select.addItem("-.01|0.8", "< 80%");
    select.addItem("-.01|0.7", "< 70%");
    select.addItem("-.01|0.6", "< 60%");
    select.addItem("-.01|0.5", "< 50%");
    select.addItem("-.01|0.4", "< 40%");
    select.addItem("-.01|0.3", "< 30%");
    select.addItem("-.01|0.2", "< 20%");
    select.addItem("-.01|0.1", "< 10%");
  }
}

