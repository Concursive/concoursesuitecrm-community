package org.aspcfs.utils.web;

import java.util.*;

/**
 *  Presents an HTML language selection based on implemented Java languages
 *
 *@author     matt rajkowski
 *@created    March 18, 2004
 *@version    $Id$
 */
public class HtmlSelectLanguage {

  /**
   *  Gets the select attribute of the HtmlSelectLanguage class
   *
   *@param  name          Description of the Parameter
   *@param  defaultValue  Description of the Parameter
   *@return               The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    if (defaultValue == null) {
      defaultValue = "en_US";
    }
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    select.addItem("zh", "Chinese");
    select.addItem("da", "Dansk");
    select.addItem("de_DE", "Deutsch");
    //select.addItem("de_DE_EURO", "Deutsch (EURO)");
    select.addItem("en_AU", "English - AU");
    select.addItem("en_CA", "English - CA");
    select.addItem("en_IE", "English - EU");
    select.addItem("en_GB", "English - UK");
    select.addItem("en_US", "English - US");
    select.addItem("es", "Español");
    select.addItem("fr_FR", "Français - FR");
    //select.addItem("fr_FR_EURO", "Français (EURO)");
    select.addItem("fr_CA", "Français - CA");
    select.addItem("it", "Italiano");
    select.addItem("ja", "Japanese");
    select.addItem("nl", "Nederlands");
    select.addItem("pt", "Portuguese");
    select.addItem("ru", "Russian");
    select.addItem("sv", "Svenska");
    return select;
  }
}

