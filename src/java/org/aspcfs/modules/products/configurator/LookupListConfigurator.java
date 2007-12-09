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
package org.aspcfs.modules.products.configurator;

import org.aspcfs.modules.products.base.ProductOption;
import org.aspcfs.utils.web.HtmlSelect;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id: LookupListConfigurator.java,v 1.1.4.2 2005/02/24 13:54:44
 *          mrajkowski Exp $
 * @created September 28, 2004
 */
public class LookupListConfigurator extends Configurator implements OptionConfigurator {
  //  properties
  private String label = null;
  private String[] items = new String[30];
  private double[] priceAdjust = new double[30];
  private boolean[] enabled = new boolean[30];
  private int size = -1;
  //helper attributes
  private int maxListSize = 30;
  //string property ids
  private final int LABEL_ID = 1;
  private final int ITEM_ID = 2;
  //integer property ids
  private final int SIZE_ID = 1;
  //double property ids
  private final int PRICE_ADJUST = 1;
  //boolean property ids
  private final int ENABLED = 1;


  /**
   * Gets the label attribute of the LookupListConfigurator object
   *
   * @return The label value
   */
  public String getLabel() {
    return label;
  }


  /**
   * Sets the label attribute of the LookupListConfigurator object
   *
   * @param tmp The new label value
   */
  public void setLabel(String tmp) {
    this.label = tmp;
  }


  /**
   * Gets the priceAdjust attribute of the LookupListConfigurator object
   *
   * @return The priceAdjust value
   */
  public double getPriceAdjust() {
    return priceAdjust[0];
  }


  /**
   * Constructor for the LookupListConfigurator object
   */
  public LookupListConfigurator() {
    this.setAllowMultiplePrices(true);
    // define the properties
    this.name = "Lookup List";
    OptionProperty property = null;
    // Label
    property = new OptionProperty();
    property.setType(property.SIMPLE_PROPERTY);
    property.setName("text_label");
    property.setValue("");
    property.setDisplay("Label");
    property.setIsForPrompting(true);
    property.setIsRequired(true);
    property.setNote("");
    propertyList.add(property);

    for (int i = 0; i < maxListSize; ++i) {
      // Lookup Item
      property = new OptionProperty();
      property.setType(property.LOOKUP_PROPERTY);
      property.setName("text_item" + i);
      property.setValue("");
      property.setDisplay("");
      property.setIsForPrompting(true);
      property.setNote("");
      propertyList.add(property);
      //base adjust property
      property = new OptionProperty();
      property.setType(property.BASEADJUST_PROPERTY);
      property.setName("double_priceadjust" + i);
      property.setValue("0");
      property.setDisplay("");
      property.setIsForPrompting(true);
      property.setNote("");
      propertyList.add(property);
      //item enabled
      property = new OptionProperty();
      property.setType(property.LOOKUP_PROPERTY);
      property.setName("boolean_enabled" + i);
      property.setValue("false");
      property.setDisplay("");
      property.setIsForPrompting(true);
      property.setNote("");
      propertyList.add(property);
    }
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean arePropertiesConfigured() {
    return true;
  }


  /**
   * Sets the properties attribute of the LookupListConfigurator object
   *
   * @param request The new properties value
   */
  public void setProperties(HttpServletRequest request) {
    propertyList.setOptionProperties(request);
    // set the label
    label = propertyList.getOptionProperty("text_label").getValue();
    // populate list items
    int i = 0;
    int index = 0;
    while (i < maxListSize) {
      OptionProperty itemProperty = propertyList.getOptionProperty(
          "text_item" + i);
      OptionProperty priceAdjustProperty = propertyList.getOptionProperty(
          "double_priceadjust" + i);
      OptionProperty enabledProperty = propertyList.getOptionProperty(
          "boolean_enabled" + i);
      if (itemProperty == null || priceAdjustProperty == null || enabledProperty == null) {
        break;
      }
      ++i;
      //if ("".equals(itemProperty.getValue().trim()) && Double.parseDouble(priceAdjustProperty.getValue()) == 0 &&
      // "false".equals(enabledProperty.getValue())) {
      if ("".equals(itemProperty.getValue().trim())) {
        //propertyList.remove(itemProperty);
        //propertyList.remove(priceAdjustProperty);
        //propertyList.remove(enabledProperty);
        continue;
      } else {
        items[index] = itemProperty.getValue();
        priceAdjust[index] = Double.parseDouble(
            priceAdjustProperty.getValue());
        enabled[index] = ("true".equals(enabledProperty.getValue()) ? true : false);
        index++;
      }
    }
    size = index;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param option Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean saveProperties(Connection db, ProductOption option) throws SQLException {
    /*
     *  if (this.optionId == -1) {
     *  throw new SQLException("Invalid Option Id Specified");
     *  }
     */
    boolean result = false;
    try {
      db.setAutoCommit(false);
      // insert the option
      option.insert(db);

      // save the label
      saveText(db, option.getId(), LABEL_ID, label);
      // save the list items, prices and enabled values
      for (int i = 0; i < size; ++i) {
        saveText(db, option.getId(), ITEM_ID + i, items[i]);
        saveDouble(db, option.getId(), PRICE_ADJUST + i, priceAdjust[i]);
        saveBoolean(db, option.getId(), ENABLED + i, enabled[i]);
      }

      // save the size property
      saveInteger(db, option.getId(), SIZE_ID, size);
      db.commit();
      result = true;
      // set the configurator's option id
      this.optionId = option.getId();
    } catch (SQLException e) {
      db.rollback();
      e.printStackTrace(System.out);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    cleanProperties();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param option Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateProperties(Connection db, ProductOption option) throws SQLException {
    boolean result = false;
    try {
      db.setAutoCommit(false);
      // update the option
      option.update(db);

      // update the label
      updateText(db, option.getId(), LABEL_ID, label);
      // update the list items, prices and enabled values
      for (int i = 0; i < size; ++i) {
        updateText(db, option.getId(), ITEM_ID + i, items[i]);
        updateDouble(db, option.getId(), PRICE_ADJUST + i, priceAdjust[i]);
        updateBoolean(db, option.getId(), ENABLED + i, enabled[i]);
      }
      int previousSize = getInteger(db, option.getId(), SIZE_ID);
      if (size > previousSize) {
        //new items need to be saved
        for (int j = previousSize; j < size; ++j) {
          saveText(db, option.getId(), ITEM_ID + j, items[j]);
          saveDouble(db, option.getId(), PRICE_ADJUST + j, priceAdjust[j]);
          saveBoolean(db, option.getId(), ENABLED + j, enabled[j]);
        }
      }
      //update the list size
      updateInteger(db, option.getId(), SIZE_ID, size);
      db.commit();
      result = true;
      // set the configurator's option id
      this.optionId = option.getId();
    } catch (SQLException e) {
      db.rollback();
      e.printStackTrace(System.out);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    cleanProperties();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param doClean  Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryProperties(Connection db, int optionId, boolean doClean) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    // populate the label
    label = getText(db, optionId, LABEL_ID);
    propertyList.setOptionProperty("text_label", label);
    // populate the size property
    size = getInteger(db, optionId, SIZE_ID);
    // populate the list items, prices and enabled values
    for (int i = 0; i < size; ++i) {
      items[i] = getText(db, optionId, ITEM_ID + i);
      propertyList.setOptionProperty("text_item" + i, items[i]);

      priceAdjust[i] = getDouble(db, optionId, PRICE_ADJUST + i);
      propertyList.setOptionProperty(
          "double_priceadjust" + i, String.valueOf(priceAdjust[i]));

      enabled[i] = getBoolean(db, optionId, ENABLED + i);
      propertyList.setOptionProperty(
          "boolean_enabled" + i, Boolean.toString(enabled[i]));
    }
    if (doClean) {
      cleanProperties();
    }
    // set built attribute to true
    built = true;
    // set the optionId attribute
    this.optionId = optionId;
  }


  /**
   * Description of the Method
   */
  private void cleanProperties() {
    //remove the list item, prices and enabled properties which are unused
    for (int j = 0; j < maxListSize; ++j) {
      OptionProperty itemProperty = propertyList.getOptionProperty(
          "text_item" + j);
      OptionProperty priceAdjustProperty = propertyList.getOptionProperty(
          "double_priceadjust" + j);
      OptionProperty enabledProperty = propertyList.getOptionProperty(
          "boolean_enabled" + j);
      if (itemProperty != null && priceAdjustProperty != null && enabledProperty != null) {
        //if ("".equals(itemProperty.getValue().trim()) && Double.parseDouble(priceAdjustProperty.getValue()) == 0 &&
        // "false".equals(enabledProperty.getValue())) {
        if ("".equals(itemProperty.getValue().trim())) {
          propertyList.remove(propertyList.getOptionProperty("text_item" + j));
          propertyList.remove(
              propertyList.getOptionProperty("double_priceadjust" + j));
          propertyList.remove(
              propertyList.getOptionProperty("boolean_enabled" + j));
        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param request Description of the Parameter
   * @param db      Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void prepareContext(HttpServletRequest request, Connection db) throws SQLException {

  }


  /**
   * Gets the itemIndex attribute of the LookupListConfigurator object
   *
   * @param value Description of the Parameter
   * @return The itemIndex value
   */
  private int getItemIndex(String value) {
    for (int i = 0; i < items.length; ++i) {
      if (value.equals(items[i])) {
        return i;
      }
    }
    return -1;
  }


  /**
   * Gets the html attribute of the LookupListConfigurator object
   *
   * @return The html value
   */
  public String getHtml() {
    HtmlSelect select = new HtmlSelect();
    select.addItem(-1, "----");
    for (int i = 0; i < size; ++i) {
      if (enabled[i]) {
        select.addItem(i, items[i]);
      }
    }
    StringBuffer sb = new StringBuffer();
    sb.append(
        "<table class=\"empty\" cellspacing=\"4\" cellpadding=\"2\" width=\"100%\">");
    sb.append("	<tr>");
    sb.append("		<td width=\"100\" nowrap>" + label + "</td>");
    sb.append("		<td>" + select.getHtml("select" + optionId) + "</td>");
    sb.append(" </tr>");
    sb.append("</table>");
    return sb.toString();
  }


  /**
   * Gets the quoteHtml attribute of the LookupListConfigurator object
   *
   * @return The quoteHtml value
   */
  public String getQuoteHtml() {
    HtmlSelect select = new HtmlSelect();
    for (int i = 0; i < size; ++i) {
      if (enabled[i]) {
        select.addItem(i, items[i]);
      }
    }
    StringBuffer sb = new StringBuffer();
    sb.append("<table cellspacing=\"0\" cellpadding=\"4\" width=\"100%\">");
    sb.append("	<tr>");
    sb.append("		<td width=\"100\" nowrap>" + label + "</td>");
    sb.append(
        "		<td>" + select.getHtml(
            "select" + optionId, getItemIndex(quoteUserInput)) + "</td>");
    sb.append("		<td width=\"25%\">" + "<input type=\"text\" size=\"8\"");
    sb.append(
        "            name=\"price" + optionId + "\" value=\"" + quotePriceAdjust + "\"/>");
    sb.append("   </td>");
    sb.append(" </tr>");
    sb.append("</table>");
    return sb.toString();
  }


  /**
   * Description of the Method
   *
   * @param request Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean validateUserInput(HttpServletRequest request) {
    boolean isValid = true;
    if (request.getParameter("select" + optionId) != null) {
      String input = request.getParameter("select" + optionId);
      if (input != null && !"".equals(input.trim())) {
        if ("-1".equals(input.trim())) {
          isValid = false;
        }
      }
    }
    try {
      if (request.getParameter("price" + optionId) != null) {
        double price = Double.parseDouble(
            request.getParameter("price" + optionId));
      }
    } catch (Exception e) {
      isValid = false;
    }
    return isValid;
  }


  /**
   * Description of the Method
   *
   * @param request Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasUserInput(HttpServletRequest request) {
    boolean hasInput = false;
    if (request.getParameter("select" + optionId) != null) {
      String input = request.getParameter("select" + optionId);
      if (input != null && !"".equals(input.trim())) {
        if (!"-1".equals(input.trim())) {
          hasInput = true;
        }
      }
    }
    if (request.getParameter("price" + optionId) != null) {
      String input = request.getParameter("price" + optionId);
      if (input != null && !"".equals(input.trim())) {
        hasInput = true;
      }
    }
    return hasInput;
  }


  /**
   * Description of the Method
   *
   * @param request Description of the Parameter
   * @return Description of the Return Value
   */
  public double computePriceAdjust(HttpServletRequest request) {
    if (request.getParameter("select" + optionId) != null) {
      String input = request.getParameter("select" + optionId);
      if (request.getParameter("price" + optionId) != null) {
        //If it is a quote, then perform this action
        double price = Double.parseDouble(
            request.getParameter("price" + optionId));
        return (price);
      }
      try {
        if (Integer.parseInt(input) >= 0) {
          return priceAdjust[Integer.parseInt(input)];
        }
      } catch (NumberFormatException e) {
        return 0;
      }
    }
    return 0;
  }


  /**
   * Description of the Method
   *
   * @param db                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @param request              Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void saveQuoteOption(Connection db, int quoteProductOptionId, HttpServletRequest request) throws SQLException {
    if (request.getParameter("select" + optionId) != null) {
      String input = request.getParameter("select" + optionId);
      if (input != null) {
        saveQuoteText(
            db, quoteProductOptionId, QUOTE_USER_INPUT, items[Integer.parseInt(
                input)]);
        saveQuoteDouble(
            db, quoteProductOptionId, QUOTE_PRICE_ADJUST, priceAdjust[Integer.parseInt(
                input)]);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @param request              Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void updateQuoteOption(Connection db, int quoteProductOptionId, HttpServletRequest request) throws SQLException {
    if (request.getParameter("select" + optionId) != null) {
      String input = request.getParameter("select" + optionId);
      updateQuoteText(
          db, quoteProductOptionId, QUOTE_USER_INPUT, items[Integer.parseInt(
              input)]);
    }
    if (request.getParameter("price" + optionId) != null) {
      String input = request.getParameter("price" + optionId);
      updateQuoteDouble(
          db, quoteProductOptionId, QUOTE_PRICE_ADJUST, Double.parseDouble(
              input));
    }
  }
}

