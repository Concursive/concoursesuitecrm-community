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
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id: NumericalConfigurator.java,v 1.1.4.1 2004/10/18 19:56:27
 *          mrajkowski Exp $
 * @created September 29, 2004
 */
public class NumericalConfigurator extends Configurator implements OptionConfigurator {
  //properties
  private String label = null;
  private int minNum = -1;
  private int maxNum = -1;
  private int defaultNum = 0;
  private boolean allowFractions = false;
  private double priceAdjust = 0;
  //string property ids
  private final int LABEL_ID = 1;
  //integer property ids
  private final int MIN_NUM = 1;
  private final int MAX_NUM = 2;
  private final int DEFAULT_NUM = 3;
  //boolean property ids
  private final int ALLOW_FRACTIONS = 1;
  //double property ids
  private final int PRICE_ADJUST = 1;


  /**
   * Gets the priceAdjust attribute of the NumericalConfigurator object
   *
   * @return The priceAdjust value
   */
  public double getPriceAdjust() {
    return priceAdjust;
  }


  /**
   * Sets the priceAdjust attribute of the NumericalConfigurator object
   *
   * @param tmp The new priceAdjust value
   */
  public void setPriceAdjust(double tmp) {
    this.priceAdjust = tmp;
  }


  /**
   * Sets the priceAdjust attribute of the NumericalConfigurator object
   *
   * @param tmp The new priceAdjust value
   */
  public void setPriceAdjust(String tmp) {
    this.priceAdjust = Double.parseDouble(tmp);
  }


  /**
   * Gets the label attribute of the NumericalConfigurator object
   *
   * @return The label value
   */
  public String getLabel() {
    return label;
  }


  /**
   * Sets the label attribute of the NumericalConfigurator object
   *
   * @param tmp The new label value
   */
  public void setLabel(String tmp) {
    this.label = tmp;
  }


  /**
   * Gets the minNum attribute of the NumericalConfigurator object
   *
   * @return The minNum value
   */
  public int getMinNum() {
    return minNum;
  }


  /**
   * Sets the minNum attribute of the NumericalConfigurator object
   *
   * @param tmp The new minNum value
   */
  public void setMinNum(int tmp) {
    this.minNum = tmp;
  }


  /**
   * Sets the minNum attribute of the NumericalConfigurator object
   *
   * @param tmp The new minNum value
   */
  public void setMinNum(String tmp) {
    this.minNum = Integer.parseInt(tmp);
  }


  /**
   * Gets the maxNum attribute of the NumericalConfigurator object
   *
   * @return The maxNum value
   */
  public int getMaxNum() {
    return maxNum;
  }


  /**
   * Sets the maxNum attribute of the NumericalConfigurator object
   *
   * @param tmp The new maxNum value
   */
  public void setMaxNum(int tmp) {
    this.maxNum = tmp;
  }


  /**
   * Sets the maxNum attribute of the NumericalConfigurator object
   *
   * @param tmp The new maxNum value
   */
  public void setMaxNum(String tmp) {
    this.maxNum = Integer.parseInt(tmp);
  }


  /**
   * Gets the defaultNum attribute of the NumericalConfigurator object
   *
   * @return The defaultNum value
   */
  public int getDefaultNum() {
    return defaultNum;
  }


  /**
   * Sets the defaultNum attribute of the NumericalConfigurator object
   *
   * @param tmp The new defaultNum value
   */
  public void setDefaultNum(int tmp) {
    this.defaultNum = tmp;
  }


  /**
   * Sets the defaultNum attribute of the NumericalConfigurator object
   *
   * @param tmp The new defaultNum value
   */
  public void setDefaultNum(String tmp) {
    this.defaultNum = Integer.parseInt(tmp);
  }


  /**
   * Gets the allowFractions attribute of the NumericalConfigurator object
   *
   * @return The allowFractions value
   */
  public boolean getAllowFractions() {
    return allowFractions;
  }


  /**
   * Sets the allowFractions attribute of the NumericalConfigurator object
   *
   * @param tmp The new allowFractions value
   */
  public void setAllowFractions(boolean tmp) {
    this.allowFractions = tmp;
  }


  /**
   * Sets the allowFractions attribute of the NumericalConfigurator object
   *
   * @param tmp The new allowFractions value
   */
  public void setAllowFractions(String tmp) {
    this.allowFractions = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Constructor for the NumericalConfigurator object
   */
  public NumericalConfigurator() {
    //set the name
    this.name = "Number";
    //define the properties
    OptionProperty property = null;
    //Label
    property = new OptionProperty();
    property.setType(property.SIMPLE_PROPERTY);
    property.setName("text_label");
    property.setValue("");
    property.setDisplay("Label to display");
    property.setIsForPrompting(true);
    property.setIsRequired(true);
    property.setNote("");
    propertyList.add(property);
    //min number
    property = new OptionProperty();
    property.setType(property.SIMPLE_PROPERTY);
    property.setName("number_min");
    property.setValue("");
    property.setDisplay("Minimum Number Allowed");
    property.setIsForPrompting(true);
    property.setNote("");
    propertyList.add(property);
    //max number
    property = new OptionProperty();
    property.setType(property.SIMPLE_PROPERTY);
    property.setName("number_max");
    property.setValue("");
    property.setDisplay("Maximum Number Allowed");
    property.setIsForPrompting(true);
    property.setNote("");
    propertyList.add(property);
    //default number
    property = new OptionProperty();
    property.setType(property.SIMPLE_PROPERTY);
    property.setName("number_default");
    property.setValue("");
    property.setDisplay("Default Value");
    property.setIsForPrompting(true);
    property.setNote("");
    propertyList.add(property);
    //allow fractions
    property = new OptionProperty();
    property.setType(property.SIMPLE_PROPERTY);
    property.setName("boolean_allowfractions");
    property.setValue("false");
    property.setDisplay("Allow Fractions?");
    property.setIsForPrompting(true);
    property.setNote("");
    propertyList.add(property);
    //base adjust property
    property = new OptionProperty();
    property.setType(property.BASEADJUST_PROPERTY);
    property.setName("double_priceadjust");
    property.setValue("0");
    property.setDisplay("Adjust base price based on number specified");
    property.setIsForPrompting(true);
    property.setNote("base price + (number specified X amount)");
    propertyList.add(property);
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
   * Sets the properties attribute of the NumericalConfigurator object
   *
   * @param request The new properties value
   */
  public void setProperties(HttpServletRequest request) {
    propertyList.setOptionProperties(request);
    //set the properties
    label = propertyList.getOptionProperty("text_label").getValue();
    String tmp = propertyList.getOptionProperty("number_min").getValue();
    if (tmp != null && !"".equals(tmp.trim())) {
      minNum = Integer.parseInt(
          propertyList.getOptionProperty("number_min").getValue());
    }
    tmp = propertyList.getOptionProperty("number_max").getValue();
    if (tmp != null && !"".equals(tmp.trim())) {
      maxNum = Integer.parseInt(
          propertyList.getOptionProperty("number_max").getValue());
    }
    tmp = propertyList.getOptionProperty("number_default").getValue();
    if (tmp != null && !"".equals(tmp.trim())) {
      defaultNum = Integer.parseInt(
          propertyList.getOptionProperty("number_default").getValue());
    }
    tmp = propertyList.getOptionProperty("boolean_allowfractions").getValue();
    allowFractions = ("true".equals(tmp) ? true : false);
    priceAdjust = Double.parseDouble(
        propertyList.getOptionProperty("double_priceadjust").getValue());
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
    boolean result = false;
    try {
      db.setAutoCommit(false);

      // insert the option
      option.insert(db);

      // save the label
      saveText(db, option.getId(), LABEL_ID, label);
      // save the min num value
      saveInteger(db, option.getId(), MIN_NUM, minNum);
      // save the max num value
      saveInteger(db, option.getId(), MAX_NUM, maxNum);
      // save the default val
      saveInteger(db, option.getId(), DEFAULT_NUM, defaultNum);
      // save the allowfractions boolean val
      saveBoolean(db, option.getId(), ALLOW_FRACTIONS, allowFractions);
      // save the price adjust value
      saveDouble(db, option.getId(), PRICE_ADJUST, priceAdjust);

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
      // update the min num value
      updateInteger(db, option.getId(), MIN_NUM, minNum);
      // update the max num value
      updateInteger(db, option.getId(), MAX_NUM, maxNum);
      // update the default val
      updateInteger(db, option.getId(), DEFAULT_NUM, defaultNum);
      // update the allowfractions boolean val
      updateBoolean(db, option.getId(), ALLOW_FRACTIONS, allowFractions);
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
    // populate the min num
    minNum = getInteger(db, optionId, MIN_NUM);
    propertyList.setOptionProperty("number_min", String.valueOf(minNum));
    // populate the max num
    maxNum = getInteger(db, optionId, MAX_NUM);
    propertyList.setOptionProperty("number_max", String.valueOf(maxNum));
    // populate the default num
    defaultNum = getInteger(db, optionId, DEFAULT_NUM);
    propertyList.setOptionProperty(
        "number_default", String.valueOf(defaultNum));
    // populate the allow fractions boolean
    allowFractions = getBoolean(db, optionId, ALLOW_FRACTIONS);
    propertyList.setOptionProperty(
        "boolean_allowfractions", Boolean.toString(allowFractions));
    // populate the priceAdjust value
    priceAdjust = getDouble(db, optionId, PRICE_ADJUST);
    propertyList.setOptionProperty(
        "double_priceadjust", String.valueOf(priceAdjust));
    //set the built property to true
    built = true;
    //set the option id
    this.optionId = optionId;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean enableOption(Connection db, int optionId) throws SQLException {
    return true;
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
   * Gets the html attribute of the NumericalConfigurator object
   *
   * @return The html value
   */
  public String getHtml() {
    StringBuffer sb = new StringBuffer();
    sb.append(
        "<table class=\"empty\" cellspacing=\"4\" cellpadding=\"2\" width=\"100%\">");
    sb.append("	<tr>");
    sb.append("		<td width=\"100\" nowrap>");
    sb.append("    " + label);
    sb.append("   </td>");
    sb.append("		<td>" + "<input type=\"text\" size=\"" + "6" + "\"");
    sb.append(
        "            name=\"number" + optionId + "\" value=\"" + defaultNum + "\"/>");
    sb.append("   </td>");
    sb.append(" </tr>");
    sb.append("</table>");
    return sb.toString();
  }


  /**
   * Gets the quoteHtml attribute of the NumericalConfigurator object
   *
   * @return The quoteHtml value
   */
  public String getQuoteHtml() {
    StringBuffer sb = new StringBuffer();
    sb.append("<table cellspacing=\"0\" cellpadding=\"4\" width=\"100%\">");
    sb.append("	<tr>");
    sb.append("		<td width=\"100\" nowrap>");
    sb.append("    " + label);
    sb.append("   </td>");
    sb.append("		<td>" + "<input type=\"text\" size=\"" + "6" + "\"");
    sb.append(
        "            name=\"number" + optionId + "\" value=\"" + quoteUserInput + "\"/>");
    sb.append("   </td>");
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
    if (request.getParameter("number" + optionId) != null) {
      String input = request.getParameter("number" + optionId);
      try {
        if (allowFractions) {
          if (minNum != -1) {
            if ((Double.parseDouble(input) < minNum)) {
              isValid = false;
            }
          }
          if (maxNum != -1) {
            if ((Double.parseDouble(input) > maxNum)) {
              isValid = false;
            }
          }
          //minNum and maxNum are -1. User did not specify these values. So check to see if the input is valid
          double tmp = Double.parseDouble(input);
        } else {
          if (minNum != -1) {
            if ((Integer.parseInt(input) < minNum)) {
              isValid = false;
            }
          }
          if (maxNum != -1) {
            if ((Integer.parseInt(input) > maxNum)) {
              isValid = false;
            }
          }
          //minNum and maxNum are -1. User did not specify these values. So check to see if the input is valid
          int tmp = Integer.parseInt(input);
        }
      } catch (Exception e) {
        isValid = false;
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
    if (request.getParameter("number" + optionId) != null) {
      String input = request.getParameter("number" + optionId);
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
   * Description of the Method
   *
   * @param request Description of the Parameter
   * @return Description of the Return Value
   */
  public double computePriceAdjust(HttpServletRequest request) {
    if (request.getParameter("number" + optionId) != null) {
      String input = request.getParameter("number" + optionId);
      try {
        if (validateUserInput(request)) {
          if (request.getParameter("price" + optionId) != null) {
            double price = Double.parseDouble(
                request.getParameter("price" + optionId));
            if (allowFractions) {
              return (Double.parseDouble(input) * price);
            } else {
              return (Integer.parseInt(input) * price);
            }
          }
          if (allowFractions) {
            return (Double.parseDouble(input) * priceAdjust);
          } else {
            return (Integer.parseInt(input) * priceAdjust);
          }
        }
      } catch (Exception e) {
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
    if (request.getParameter("number" + optionId) != null) {
      String input = request.getParameter("number" + optionId);
      if (input != null) {
        saveQuoteText(db, quoteProductOptionId, QUOTE_USER_INPUT, input);
        saveQuoteDouble(
            db, quoteProductOptionId, QUOTE_PRICE_ADJUST, priceAdjust);
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
    if (request.getParameter("number" + optionId) != null) {
      String input = request.getParameter("number" + optionId);
      updateQuoteText(db, quoteProductOptionId, QUOTE_USER_INPUT, input);
    }
    if (request.getParameter("price" + optionId) != null) {
      String input = request.getParameter("price" + optionId);
      updateQuoteDouble(
          db, quoteProductOptionId, QUOTE_PRICE_ADJUST, Double.parseDouble(
              input));
    }
  }
}

