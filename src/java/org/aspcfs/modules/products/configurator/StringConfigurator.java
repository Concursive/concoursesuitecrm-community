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
package org.aspcfs.modules.products.configurator;

import org.aspcfs.modules.products.base.ProductOption;
import org.aspcfs.utils.HTTPUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    September 16, 2004
 *@version    $Id: StringConfigurator.java,v 1.1.4.1 2004/10/18 19:56:27
 *      mrajkowski Exp $
 */
public class StringConfigurator extends Configurator implements OptionConfigurator {
  //properties
  private String label = null;
  private int minChars = -1;
  private int maxChars = -1;
  private String defaultText = null;
  private double priceAdjust = 0;
  //string property ids
  private final int LABEL_ID = 1;
  private final int DEFAULT_TEXT_ID = 2;
  //integer property ids
  private final int MIN_CHARS = 1;
  private final int MAX_CHARS = 2;
  //double property ids
  private final int PRICE_ADJUST = 1;


  /**
   *  Gets the priceAdjust attribute of the StringConfigurator object
   *
   *@return    The priceAdjust value
   */
  public double getPriceAdjust() {
    return priceAdjust;
  }


  /**
   *  Sets the priceAdjust attribute of the StringConfigurator object
   *
   *@param  tmp  The new priceAdjust value
   */
  public void setPriceAdjust(double tmp) {
    this.priceAdjust = tmp;
  }


  /**
   *  Sets the priceAdjust attribute of the StringConfigurator object
   *
   *@param  tmp  The new priceAdjust value
   */
  public void setPriceAdjust(String tmp) {
    this.priceAdjust = Double.parseDouble(tmp);
  }



  /**
   *  Gets the label attribute of the StringConfigurator object
   *
   *@return    The label value
   */
  public String getLabel() {
    return label;
  }


  /**
   *  Sets the label attribute of the StringConfigurator object
   *
   *@param  tmp  The new label value
   */
  public void setLabel(String tmp) {
    this.label = tmp;
  }


  /**
   *  Gets the minChars attribute of the StringConfigurator object
   *
   *@return    The minChars value
   */
  public int getMinChars() {
    return minChars;
  }


  /**
   *  Sets the minChars attribute of the StringConfigurator object
   *
   *@param  tmp  The new minChars value
   */
  public void setMinChars(int tmp) {
    this.minChars = tmp;
  }


  /**
   *  Sets the minChars attribute of the StringConfigurator object
   *
   *@param  tmp  The new minChars value
   */
  public void setMinChars(String tmp) {
    this.minChars = Integer.parseInt(tmp);
  }


  /**
   *  Gets the maxChars attribute of the StringConfigurator object
   *
   *@return    The maxChars value
   */
  public int getMaxChars() {
    return maxChars;
  }


  /**
   *  Sets the maxChars attribute of the StringConfigurator object
   *
   *@param  tmp  The new maxChars value
   */
  public void setMaxChars(int tmp) {
    this.maxChars = tmp;
  }


  /**
   *  Sets the maxChars attribute of the StringConfigurator object
   *
   *@param  tmp  The new maxChars value
   */
  public void setMaxChars(String tmp) {
    this.maxChars = Integer.parseInt(tmp);
  }


  /**
   *  Gets the defaultText attribute of the StringConfigurator object
   *
   *@return    The defaultText value
   */
  public String getDefaultText() {
    return defaultText;
  }


  /**
   *  Sets the defaultText attribute of the StringConfigurator object
   *
   *@param  tmp  The new defaultText value
   */
  public void setDefaultText(String tmp) {
    this.defaultText = tmp;
  }


  /**
   *  Constructor for the StringConfigurator object
   */
  public StringConfigurator() {
    // set the name
    this.name = "Text";
    // define the properties
    OptionProperty property = null;
    //Label
    property = new OptionProperty();
    property.setType(OptionProperty.SIMPLE_PROPERTY);
    property.setName("text_label");
    property.setValue("");
    property.setDisplay("Label to display");
    property.setIsForPrompting(true);
    property.setIsRequired(true);
    property.setNote("");
    propertyList.add(property);
    //min chars
    property = new OptionProperty();
    property.setType(OptionProperty.SIMPLE_PROPERTY);
    property.setName("number_minchars");
    property.setValue("1");
    property.setDisplay("Minimum Chars Allowed");
    property.setIsForPrompting(true);
    property.setIsRequired(true);
    property.setNote("");
    propertyList.add(property);
    //max chars
    property = new OptionProperty();
    property.setType(OptionProperty.SIMPLE_PROPERTY);
    property.setName("number_maxchars");
    property.setValue("255");
    property.setDisplay("Maximum Chars Allowed");
    property.setIsForPrompting(true);
    property.setIsRequired(true);
    property.setNote("");
    propertyList.add(property);
    // default value
    property = new OptionProperty();
    property.setType(OptionProperty.SIMPLE_PROPERTY);
    property.setName("text_default");
    property.setValue("");
    property.setDisplay("Default Text");
    property.setIsForPrompting(true);
    property.setNote("");
    propertyList.add(property);
    //base adjust property
    property = new OptionProperty();
    property.setType(OptionProperty.BASEADJUST_PROPERTY);
    property.setName("double_priceadjust");
    property.setValue("0");
    property.setDisplay("Adjust base price based on number of characters");
    property.setIsForPrompting(true);
    property.setNote("base price + (number of characters X amount)");
    propertyList.add(property);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean arePropertiesConfigured() {
    return true;
  }


  /**
   *  Sets the properties attribute of the StringConfigurator object
   *
   *@param  request  The new properties value
   */
  public void setProperties(HttpServletRequest request) {
    propertyList.setOptionProperties(request);
    //set the label
    label = propertyList.getOptionProperty("text_label").getValue();
    minChars = Integer.parseInt(propertyList.getOptionProperty("number_minchars").getValue());
    maxChars = Integer.parseInt(propertyList.getOptionProperty("number_maxchars").getValue());
    defaultText = propertyList.getOptionProperty("text_default").getValue();
    priceAdjust = Double.parseDouble(propertyList.getOptionProperty("double_priceadjust").getValue());
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  option            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean saveProperties(Connection db, ProductOption option) throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      // insert the option
      option.insert(db);
      // save the label
      saveText(db, option.getId(), LABEL_ID, label);
      // save the default text value
      saveText(db, option.getId(), DEFAULT_TEXT_ID, defaultText);
      // save the min chars value
      saveInteger(db, option.getId(), MIN_CHARS, minChars);
      // save the max chars value
      saveInteger(db, option.getId(), MAX_CHARS, maxChars);
      // save the price adjust value
      saveDouble(db, option.getId(), PRICE_ADJUST, priceAdjust);
      if (commit) {
        db.commit();
      }
      result = true;
      this.optionId = option.getId();
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      e.printStackTrace(System.out);
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  option            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean updateProperties(Connection db, ProductOption option) throws SQLException {
    boolean result = false;
    try {
      db.setAutoCommit(false);
      // update the option
      option.update(db);

      // update the label
      updateText(db, option.getId(), LABEL_ID, label);
      // update the default text value
      updateText(db, option.getId(), DEFAULT_TEXT_ID, defaultText);
      // update the min chars value
      updateInteger(db, option.getId(), MIN_CHARS, minChars);
      // update the max chars value
      updateInteger(db, option.getId(), MAX_CHARS, maxChars);
      // update the price adjust value
      updateDouble(db, option.getId(), PRICE_ADJUST, priceAdjust);
      db.commit();
      result = true;
      this.optionId = option.getId();
    } catch (SQLException e) {
      db.rollback();
      e.printStackTrace(System.out);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  optionId          Description of the Parameter
   *@param  doClean           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryProperties(Connection db, int optionId, boolean doClean) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    // populate the label
    label = getText(db, optionId, LABEL_ID);
    propertyList.setOptionProperty("text_label", label);

    // populate the default text value
    defaultText = getText(db, optionId, DEFAULT_TEXT_ID);
    propertyList.setOptionProperty("text_default", defaultText);

    // populate the min chars value
    minChars = getInteger(db, optionId, MIN_CHARS);
    propertyList.setOptionProperty("number_minchars", String.valueOf(minChars));

    // populate the max chars value
    maxChars = getInteger(db, optionId, MAX_CHARS);
    propertyList.setOptionProperty("number_maxchars", String.valueOf(maxChars));

    // populate the priceAdjust value
    priceAdjust = getDouble(db, optionId, PRICE_ADJUST);
    propertyList.setOptionProperty("double_priceadjust", String.valueOf(priceAdjust));
    built = true;
    // set the optionId attribute
    this.optionId = optionId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  optionId          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean enableOption(Connection db, int optionId) throws SQLException {
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  request           Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void prepareContext(HttpServletRequest request, Connection db) throws SQLException { }


  /**
   *  Gets the html attribute of the StringConfigurator object
   *
   *@return    The html value
   */
  public String getHtml() {
    StringBuffer sb = new StringBuffer();
    sb.append("<table class=\"empty\" cellspacing=\"4\" cellpadding=\"2\" width=\"100%\">");
    sb.append("	<tr>");
    sb.append("		<td width=\"100\" nowrap>");
    sb.append("    " + label);
    sb.append("   </td>");
    sb.append("		<td>" + "<input type=\"text\" size=\"" + (maxChars > 20 ? 20 : maxChars) + "\" maxlength=\"" + maxChars + "\" ");
    sb.append("            name=\"text" + optionId + "\" value=\"" + HTTPUtils.toHtmlValue(defaultText) + "\"/>");
    sb.append("   </td>");
    sb.append(" </tr>");
    sb.append("</table>");
    return sb.toString();
  }


  /**
   *  Gets the quoteHtml attribute of the StringConfigurator object
   *
   *@return    The quoteHtml value
   */
  public String getQuoteHtml() {
    StringBuffer sb = new StringBuffer();
    sb.append("<table cellspacing=\"0\" cellpadding=\"4\" width=\"100%\">");
    sb.append("	<tr>");
    sb.append("		<td width=\"100\" nowrap>");
    sb.append("    " + label);
    sb.append("   </td>");
    sb.append("		<td>" + "<input type=\"text\" size=\"" + (maxChars > 20 ? 20 : maxChars) + "\" maxlength=\"" + maxChars + "\" ");
    sb.append("            name=\"text" + optionId + "\" value=\"" + HTTPUtils.toHtmlValue(quoteUserInput) + "\"/>");
    sb.append("   </td>");
    sb.append("		<td width=\"25%\">" + "<input type=\"text\" size=\"8\"");
    sb.append("            name=\"price" + optionId + "\" value=\"" + quotePriceAdjust + "\"/>");
    sb.append("   </td>");
    sb.append(" </tr>");
    sb.append("</table>");
    return sb.toString();
  }


  /**
   *  Description of the Method
   *
   *@param  request  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean validateUserInput(HttpServletRequest request) {
    //TODO: This method needs to be modified if an option can
    //be mapped with more than one product. currently assumes 1:1 product-option mapping
    boolean isValid = true;
    if (request.getParameter("text" + optionId) != null) {
      String input = request.getParameter("text" + optionId);
      if ((input.length() < minChars) || (input.length() > maxChars)) {
         isValid = false;
      }
    }
    try {
      if (request.getParameter("price" + optionId) != null) {
          double price = Double.parseDouble(request.getParameter("price" + optionId));
      }
    } catch (Exception e) {
      isValid = false;
    }
    return isValid;
  }


  /**
   *  Description of the Method
   *
   *@param  request  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean hasUserInput(HttpServletRequest request) {
    boolean hasInput = false;
    if (request.getParameter("text" + optionId) != null) {
      String input = request.getParameter("text" + optionId);
      if (input != null && !"".equals(input.trim())) {
        hasInput = true;
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
   *  Description of the Method
   *
   *@param  request  Description of the Parameter
   *@return          Description of the Return Value
   */
  public double computePriceAdjust(HttpServletRequest request) {
    if (request.getParameter("text" + optionId) != null) {
      String input = request.getParameter("text" + optionId);
      if (request.getParameter("price" + optionId) != null) {
        double price = Double.parseDouble(request.getParameter("price" + optionId));
        return (input.length() * price);
      }
      return (input.length() * priceAdjust);
    }
    return 0;
  }


  /**
   *  Description of the Method
   *
   *@param  db                    Description of the Parameter
   *@param  quoteProductOptionId  Description of the Parameter
   *@param  request               Description of the Parameter
   *@exception  SQLException      Description of the Exception
   */
  public void saveQuoteOption(Connection db, int quoteProductOptionId, HttpServletRequest request) throws SQLException {
    if (request.getParameter("text" + optionId) != null) {
      String input = request.getParameter("text" + optionId);
      if (input != null) {
        saveQuoteText(db, quoteProductOptionId, QUOTE_USER_INPUT, input);
        saveQuoteDouble(db, quoteProductOptionId, QUOTE_PRICE_ADJUST, priceAdjust);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                    Description of the Parameter
   *@param  quoteProductOptionId  Description of the Parameter
   *@param  request               Description of the Parameter
   *@exception  SQLException      Description of the Exception
   */
  public void updateQuoteOption(Connection db, int quoteProductOptionId, HttpServletRequest request) throws SQLException {
    if (request.getParameter("text" + optionId) != null) {
      String input = request.getParameter("text" + optionId);
      updateQuoteText(db, quoteProductOptionId, QUOTE_USER_INPUT, input);
    }
    if (request.getParameter("price" + optionId) != null) {
      String input = request.getParameter("price" + optionId);
      updateQuoteDouble(db, quoteProductOptionId, QUOTE_PRICE_ADJUST, Double.parseDouble(input));
    }
  }
}

