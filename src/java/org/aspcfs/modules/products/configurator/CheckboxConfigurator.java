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

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id: CheckboxConfigurator.java,v 1.1.4.1 2004/10/18 19:56:27
 *          mrajkowski Exp $
 * @created September 24, 2004
 */
public class CheckboxConfigurator extends Configurator implements OptionConfigurator {
  //properties
  private String label = null;
  private double priceAdjust = 0;
  //string property ids
  private final static int LABEL_ID = 1;
  //double property ids
  private final static int PRICE_ADJUST = 1;


  /**
   * Gets the priceAdjust attribute of the CheckboxConfigurator object
   *
   * @return The priceAdjust value
   */
  public double getPriceAdjust() {
    return priceAdjust;
  }


  /**
   * Sets the priceAdjust attribute of the CheckboxConfigurator object
   *
   * @param tmp The new priceAdjust value
   */
  public void setPriceAdjust(double tmp) {
    this.priceAdjust = tmp;
  }


  /**
   * Sets the priceAdjust attribute of the CheckboxConfigurator object
   *
   * @param tmp The new priceAdjust value
   */
  public void setPriceAdjust(String tmp) {
    this.priceAdjust = Double.parseDouble(tmp);
  }


  /**
   * Gets the label attribute of the CheckboxConfigurator object
   *
   * @return The label value
   */
  public String getLabel() {
    return label;
  }


  /**
   * Sets the label attribute of the CheckboxConfigurator object
   *
   * @param tmp The new label value
   */
  public void setLabel(String tmp) {
    this.label = tmp;
  }


  /**
   * Constructor for the CheckboxConfigurator object
   */
  public CheckboxConfigurator() {
    // set the name
    this.name = "Check Box";
    // define the properties
    OptionProperty property = null;
    // Label
    property = new OptionProperty();
    property.setType(property.SIMPLE_PROPERTY);
    property.setName("text_label");
    property.setValue("");
    property.setDisplay("Label to display");
    property.setIsForPrompting(true);
    property.setIsRequired(true);
    property.setNote("");
    propertyList.add(property);
    //base adjust property
    property = new OptionProperty();
    property.setType(property.BASEADJUST_PROPERTY);
    property.setName("double_priceadjust");
    property.setValue("0");
    property.setDisplay("Adjust base price when selected");
    property.setIsForPrompting(true);
    property.setNote("base price + (amount)");
    propertyList.add(property);
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean arePropertiesConfigured() {
    if (label != null && !label.trim().equals("")) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Sets the properties attribute of the CheckboxConfigurator object
   *
   * @param request The new properties value
   */
  public void setProperties(HttpServletRequest request) {
    propertyList.setOptionProperties(request);
    //set the text
    label = propertyList.getOptionProperty("text_label").getValue();
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
      // save the price adjust value
      saveDouble(db, option.getId(), PRICE_ADJUST, priceAdjust);
      db.commit();
      this.optionId = option.getId();
      result = true;
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
      // update the price adjust value
      updateDouble(db, option.getId(), PRICE_ADJUST, priceAdjust);
      db.commit();
      this.optionId = option.getId();
      result = true;
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
    // populate the priceAdjust value
    priceAdjust = getDouble(db, optionId, PRICE_ADJUST);
    propertyList.setOptionProperty(
        "double_priceadjust", String.valueOf(priceAdjust));
    built = true;
    this.optionId = optionId;
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
   * Gets the html attribute of the CheckboxConfigurator object
   *
   * @return The html value
   */
  public String getHtml() {
    StringBuffer sb = new StringBuffer();
    sb.append(
        "<table class=\"empty\" cellspacing=\"4\" cellpadding=\"2\" width=\"100%\">");
    sb.append("	<tr>");
    sb.append("		<td width=\"100\" align=\"left\" nowrap>");
    sb.append("		" + label + "</td>");
    sb.append(
        "		<td><input type=\"checkbox\" name=\"chk" + optionId + "\" value=\"true\" /></td>");
    sb.append(" </tr>");
    sb.append("</table>");
    return sb.toString();
  }


  /**
   * Gets the quoteHtml attribute of the CheckboxConfigurator object
   *
   * @return The quoteHtml value
   */
  public String getQuoteHtml() {
    StringBuffer sb = new StringBuffer();
    sb.append("<table cellspacing=\"0\" cellpadding=\"4\" width=\"100%\">");
    sb.append("	<tr>");
    sb.append("		<td width=\"100\" nowrap>");
    sb.append("		" + label + "</td>");
    sb.append(
        "		<td><input type=\"checkbox\" name=\"chk" + optionId + "\" value=\"true\" checked disabled></td>");
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
    if (request.getParameter("chk" + optionId) != null) {
      String input = request.getParameter("chk" + optionId);
      if (input != null && !"".equals(input.trim())) {
        if ("false".equals(input.trim())) {
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
    if (request.getParameter("chk" + optionId) != null) {
      String input = request.getParameter("chk" + optionId);
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
    if (request.getParameter("price" + optionId) != null) {
      double price = Double.parseDouble(
          request.getParameter("price" + optionId));
      return (price);
    }
    return priceAdjust;
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
    //save the product option price adjust value for this quote product option
    saveQuoteDouble(db, quoteProductOptionId, QUOTE_PRICE_ADJUST, priceAdjust);
  }

  public void updateQuoteOption(Connection db, int quoteProductOptionId, HttpServletRequest request) throws SQLException {
    //update the product option price adjust value for this quote product option
    if (request.getParameter("price" + optionId) != null) {
      String input = request.getParameter("price" + optionId);
      updateQuoteDouble(
          db, quoteProductOptionId, QUOTE_PRICE_ADJUST, Double.parseDouble(
              input));
    }
  }
}


