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
import org.aspcfs.modules.quotes.base.QuoteProductOption;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * The option configurator interface defines the basic functionality of any
 * option configurator
 *
 * @author ananth
 * @version $Id: OptionConfigurator.java,v 1.2 2004/05/04 15:52:27 mrajkowski
 *          Exp $
 * @created March 31, 2004
 */
public interface OptionConfigurator {
  // constants
  public final static String INTEGER = "integer";
  public final static String BOOLEAN = "boolean";
  public final static String FLOAT = "float";
  public final static String TIMESTAMP = "timestamp";
  public final static String TEXT = "text";


  /**
   * Gets the name attribute of the OptionConfigurator object
   *
   * @return The name value
   */
  public String getName();


  /**
   * Gets the description attribute of the OptionConfigurator object
   *
   * @return The description value
   */
  public String getDescription();


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean arePropertiesConfigured();


  /**
   * Gets the propertyList attribute of the OptionConfigurator object
   *
   * @return The propertyList value
   */
  public OptionPropertyList getPropertyList();


  /**
   * Sets the properties attribute of the OptionConfigurator object
   *
   * @param request The new properties value
   */
  public void setProperties(HttpServletRequest request);


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param option Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean saveProperties(Connection db, ProductOption option) throws SQLException;


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param option Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateProperties(Connection db, ProductOption option) throws SQLException;


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param doClean  Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryProperties(Connection db, int optionId, boolean doClean) throws SQLException;


  /**
   * Description of the Method
   *
   * @param request Description of the Parameter
   * @param db      Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void prepareContext(HttpServletRequest request, Connection db) throws SQLException;


  /**
   * Gets the html attribute of the OptionConfigurator object
   *
   * @return The html value
   */
  public String getHtml();


  /**
   * Gets the quoteHtml attribute of the OptionConfigurator object
   *
   * @return The quoteHtml value
   */
  public String getQuoteHtml();


  /**
   * Gets the quotePriceAdjust attribute of the OptionConfigurator object
   *
   * @return The quotePriceAdjust value
   */
  public double getQuotePriceAdjust();


  /**
   * Gets the errors attribute of the OptionConfigurator object
   *
   * @return The errors value
   */
  public HashMap getErrors();


  /**
   * Gets the warnings attribute of the OptionConfigurator object
   *
   * @return The warnings value
   */
  public HashMap getWarnings();


  /**
   * Sets the onlyWarnings attribute of the OptionConfigurator object
   *
   * @param tmp The new onlyWarnings value
   */
  public void setOnlyWarnings(boolean tmp);


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean enableOption(Connection db, int optionId) throws SQLException;


  /**
   * Gets the label attribute of the OptionConfigurator object
   *
   * @return The label value
   */
  public String getLabel();


  /**
   * Gets the priceAdjust attribute of the OptionConfigurator object
   *
   * @return The priceAdjust value
   */
  public double getPriceAdjust();


  /**
   * Gets the quoteUserInput attribute of the OptionConfigurator object
   *
   * @return The quoteUserInput value
   */
  public String getQuoteUserInput();


  /**
   * Description of the Method
   *
   * @param db                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryQuoteProperties(Connection db, int quoteProductOptionId) throws SQLException;


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean getAllowMultiplePrices();


  /**
   * Description of the Method
   *
   * @param request Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean validateUserInput(HttpServletRequest request);


  /**
   * Description of the Method
   *
   * @param request Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasUserInput(HttpServletRequest request);


  /**
   * Description of the Method
   *
   * @param request Description of the Parameter
   * @return Description of the Return Value
   */
  public double computePriceAdjust(HttpServletRequest request);


  /**
   * Description of the Method
   *
   * @param db                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @param request              Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void saveQuoteOption(Connection db, int quoteProductOptionId, HttpServletRequest request) throws SQLException;


  /**
   * Description of the Method
   *
   * @param db                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @param request              Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void updateQuoteOption(Connection db, int quoteProductOptionId, HttpServletRequest request) throws SQLException;


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param option Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean saveQuoteOption(Connection db, QuoteProductOption option) throws SQLException;
}

