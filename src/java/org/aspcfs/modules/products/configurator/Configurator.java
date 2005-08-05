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

import org.aspcfs.modules.quotes.base.QuoteProductOption;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.HashMap;

/**
 * Base Class for all option configurators
 *
 * @author ananth
 * @version $Id: Configurator.java,v 1.1.4.1 2004/10/18 19:56:27 mrajkowski
 *          Exp $
 * @created September 24, 2004
 */
public abstract class Configurator {
  protected int optionId = -1;
  protected String name = null;
  protected String description = null;
  protected boolean built = false;
  protected boolean hasValues = false;
  protected boolean allowMultiplePrices = false;
  // properties to be prompted
  protected OptionPropertyList propertyList = new OptionPropertyList();
  protected HashMap warnings = new HashMap();
  protected boolean onlyWarnings = false;

  // errors
  public final static String PROPERTY_ERROR = "property error";
  public final static String VALUE_ERROR = "value error";
  //quote property
  protected int quoteProductOptionId = -1;
  protected String quoteUserInput = null;
  protected double quotePriceAdjust = 0;
  protected final int QUOTE_USER_INPUT = 1;
  protected final int QUOTE_PRICE_ADJUST = 1;


  /**
   * Gets the quotePriceAdjust attribute of the Configurator object
   *
   * @return The quotePriceAdjust value
   */
  public double getQuotePriceAdjust() {
    return quotePriceAdjust;
  }


  /**
   * Sets the quotePriceAdjust attribute of the Configurator object
   *
   * @param tmp The new quotePriceAdjust value
   */
  public void setQuotePriceAdjust(double tmp) {
    this.quotePriceAdjust = tmp;
  }


  /**
   * Sets the quotePriceAdjust attribute of the Configurator object
   *
   * @param tmp The new quotePriceAdjust value
   */
  public void setQuotePriceAdjust(String tmp) {
    this.quotePriceAdjust = Double.parseDouble(tmp);
  }


  /**
   * Gets the quoteUserInput attribute of the Configurator object
   *
   * @return The quoteUserInput value
   */
  public String getQuoteUserInput() {
    return quoteUserInput;
  }


  /**
   * Gets the allowMultiplePrices attribute of the Configurator object
   *
   * @return The allowMultiplePrices value
   */
  public boolean getAllowMultiplePrices() {
    return allowMultiplePrices;
  }


  /**
   * Sets the allowMultiplePrices attribute of the Configurator object
   *
   * @param tmp The new allowMultiplePrices value
   */
  public void setAllowMultiplePrices(boolean tmp) {
    this.allowMultiplePrices = tmp;
  }


  /**
   * Sets the allowMultiplePrices attribute of the Configurator object
   *
   * @param tmp The new allowMultiplePrices value
   */
  public void setAllowMultiplePrices(String tmp) {
    this.allowMultiplePrices = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the optionId attribute of the Configurator object
   *
   * @param tmp The new optionId value
   */
  public void setOptionId(int tmp) {
    this.optionId = tmp;
  }


  /**
   * Sets the optionId attribute of the Configurator object
   *
   * @param tmp The new optionId value
   */
  public void setOptionId(String tmp) {
    this.optionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the name attribute of the Configurator object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the description attribute of the Configurator object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the built attribute of the Configurator object
   *
   * @param tmp The new built value
   */
  public void setBuilt(boolean tmp) {
    this.built = tmp;
  }


  /**
   * Sets the built attribute of the Configurator object
   *
   * @param tmp The new built value
   */
  public void setBuilt(String tmp) {
    this.built = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the hasValues attribute of the Configurator object
   *
   * @param tmp The new hasValues value
   */
  public void setHasValues(boolean tmp) {
    this.hasValues = tmp;
  }


  /**
   * Sets the hasValues attribute of the Configurator object
   *
   * @param tmp The new hasValues value
   */
  public void setHasValues(String tmp) {
    this.hasValues = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the propertyList attribute of the Configurator object
   *
   * @param tmp The new propertyList value
   */
  public void setPropertyList(OptionPropertyList tmp) {
    this.propertyList = tmp;
  }


  /**
   * Sets the warnings attribute of the Configurator object
   *
   * @param warnings The new warnings value
   */
  public void setWarnings(HashMap warnings) {
    this.warnings = warnings;
  }


  /**
   * Sets the onlyWarnings attribute of the Configurator object
   *
   * @param onlyWarnings The new onlyWarnings value
   */
  public void setOnlyWarnings(boolean onlyWarnings) {
    this.onlyWarnings = onlyWarnings;
  }


  /**
   * Gets the optionId attribute of the Configurator object
   *
   * @return The optionId value
   */
  public int getOptionId() {
    return optionId;
  }


  /**
   * Gets the name attribute of the Configurator object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the description attribute of the Configurator object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the built attribute of the Configurator object
   *
   * @return The built value
   */
  public boolean getBuilt() {
    return built;
  }


  /**
   * Gets the hasValues attribute of the Configurator object
   *
   * @return The hasValues value
   */
  public boolean getHasValues() {
    return hasValues;
  }


  /**
   * Gets the propertyList attribute of the Configurator object
   *
   * @return The propertyList value
   */
  public OptionPropertyList getPropertyList() {
    return propertyList;
  }


  /**
   * Gets the errors attribute of the Configurator object
   *
   * @return The errors value
   */
  public HashMap getErrors() {
    return propertyList.getErrors();
  }


  /**
   * Gets the warnings attribute of the Configurator object
   *
   * @return The warnings value
   */
  public HashMap getWarnings() {
    return warnings;
  }


  /**
   * Gets the onlyWarnings attribute of the Configurator object
   *
   * @return The onlyWarnings value
   */
  public boolean getOnlyWarnings() {
    return onlyWarnings;
  }


  /**
   * Constructor for the Configurator object
   */
  public Configurator() {
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  abstract boolean arePropertiesConfigured();


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param doClean  Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  abstract void queryProperties(Connection db, int optionId, boolean doClean) throws SQLException;


  /**
   * Gets the label attribute of the Configurator object
   *
   * @return The label value
   */
  abstract String getLabel();


  /**
   * Description of the Method
   *
   * @param db                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryQuoteProperties(Connection db, int quoteProductOptionId) throws SQLException {
    if (quoteProductOptionId == -1) {
      throw new SQLException("Invalid Qupte Product Option Id");
    }
    // populate the label
    quoteUserInput = getQuoteText(db, quoteProductOptionId, QUOTE_USER_INPUT);
    quotePriceAdjust = getQuoteDouble(
        db, quoteProductOptionId, QUOTE_PRICE_ADJUST);
    this.quoteProductOptionId = quoteProductOptionId;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param option Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean saveQuoteOption(Connection db, QuoteProductOption option) throws SQLException {
    //This method is used by clone operation on a quote product
    boolean status = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //Insert the quote product option
      option.insert(db);
      //Insert the user input for this option
      if (option.getQuoteUserInput() != null) {
        saveQuoteText(
            db, option.getId(), QUOTE_USER_INPUT, option.getQuoteUserInput());
      }
      saveQuoteDouble(
          db, option.getId(), QUOTE_PRICE_ADJUST, option.getQuotePriceAdjust());
      if (commit) {
        db.commit();
      }
      status = true;
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return status;
  }


  /**
   * Gets the text attribute of the Configurator object
   *
   * @param db                   Description of the Parameter
   * @param id                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @return The text value
   * @throws SQLException Description of the Exception
   */
  public String getQuoteText(Connection db, int quoteProductOptionId, int id) throws SQLException {
    if (quoteProductOptionId == -1) {
      throw new SQLException("Invalid Product Option Id");
    }
    String text = null;
    PreparedStatement pst = db.prepareStatement(
        "SELECT value " +
        "FROM quote_product_option_text " +
        "WHERE quote_product_option_id = ? AND id = ? ");
    pst.setInt(1, quoteProductOptionId);
    pst.setInt(2, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      text = rs.getString("value");
    }
    rs.close();
    pst.close();
    return text;
  }


  /**
   * Gets the quoteDouble attribute of the Configurator object
   *
   * @param db                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @param id                   Description of the Parameter
   * @return The quoteDouble value
   * @throws SQLException Description of the Exception
   */
  public double getQuoteDouble(Connection db, int quoteProductOptionId, int id) throws SQLException {
    if (quoteProductOptionId == -1) {
      throw new SQLException("Invalid Product Option Id");
    }
    double value = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT value " +
        "FROM quote_product_option_float " +
        "WHERE quote_product_option_id = ? AND id = ? ");
    pst.setInt(1, quoteProductOptionId);
    pst.setInt(2, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      value = rs.getDouble("value");
    }
    rs.close();
    pst.close();
    return value;
  }


  /**
   * Description of the Method
   *
   * @param db                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @param id                   Description of the Parameter
   * @param value                Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void saveQuoteText(Connection db, int quoteProductOptionId, int id, String value) throws SQLException {
    if (quoteProductOptionId == -1) {
      throw new SQLException("Invalid Product Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO quote_product_option_text(quote_product_option_id, id, value) " +
        "VALUES (?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, quoteProductOptionId);
    pst.setInt(++i, id);
    pst.setString(++i, value);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @param id                   Description of the Parameter
   * @param value                Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void saveQuoteDouble(Connection db, int quoteProductOptionId, int id, double value) throws SQLException {
    if (quoteProductOptionId == -1) {
      throw new SQLException("Invalid Product Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO quote_product_option_float(quote_product_option_id, id, value) " +
        "VALUES (?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, quoteProductOptionId);
    pst.setInt(++i, id);
    pst.setDouble(++i, value);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @param id                   Description of the Parameter
   * @param value                Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void updateQuoteText(Connection db, int quoteProductOptionId, int id, String value) throws SQLException {
    if (quoteProductOptionId == -1) {
      throw new SQLException("Invalid Product Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE quote_product_option_text " +
        "SET value = ? " +
        "WHERE quote_product_option_id = ? " +
        "AND id = ? ");
    int i = 0;
    pst.setString(++i, value);
    pst.setInt(++i, quoteProductOptionId);
    pst.setInt(++i, id);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db                   Description of the Parameter
   * @param quoteProductOptionId Description of the Parameter
   * @param id                   Description of the Parameter
   * @param value                Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void updateQuoteDouble(Connection db, int quoteProductOptionId, int id, double value) throws SQLException {
    if (quoteProductOptionId == -1) {
      throw new SQLException("Invalid Product Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE quote_product_option_float " +
        "SET value = ? " +
        "WHERE quote_product_option_id = ? " +
        "AND id = ? ");
    int i = 0;
    pst.setDouble(++i, value);
    pst.setInt(++i, quoteProductOptionId);
    pst.setInt(++i, id);
    pst.execute();
    pst.close();
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
    //TODO: Future implementation
    return true;
  }


  /**
   * Gets the text attribute of the Configurator object
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @return The text value
   * @throws SQLException Description of the Exception
   */
  public String getText(Connection db, int optionId, int id) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    String text = null;
    PreparedStatement pst = db.prepareStatement(
        "SELECT value " +
        "FROM product_option_text " +
        "WHERE product_option_id = ? AND id = ? ");
    pst.setInt(1, optionId);
    pst.setInt(2, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      text = rs.getString("value");
    }
    rs.close();
    pst.close();
    return text;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @param value    Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void saveText(Connection db, int optionId, int id, String value) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO product_option_text(product_option_id, id, value) " +
        "VALUES (?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, optionId);
    pst.setInt(++i, id);
    pst.setString(++i, value);
    pst.execute();
    pst.close();
  }


  /**
   * Gets the integer attribute of the Configurator object
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @return The integer value
   * @throws SQLException Description of the Exception
   */
  public int getInteger(Connection db, int optionId, int id) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    int value = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT value " +
        "FROM product_option_integer " +
        "WHERE product_option_id = ? AND id = ? ");
    pst.setInt(1, optionId);
    pst.setInt(2, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      value = rs.getInt("value");
    }
    rs.close();
    pst.close();
    return value;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @param value    Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void saveInteger(Connection db, int optionId, int id, int value) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO product_option_integer(product_option_id, id, value) " +
        "VALUES (?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, optionId);
    pst.setInt(++i, id);
    pst.setInt(++i, value);
    pst.execute();
    pst.close();
  }


  /**
   * Gets the boolean attribute of the Configurator object
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @return The boolean value
   * @throws SQLException Description of the Exception
   */
  public boolean getBoolean(Connection db, int optionId, int id) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    boolean value = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT value " +
        "FROM product_option_boolean " +
        "WHERE product_option_id = ? AND id = ? ");
    pst.setInt(1, optionId);
    pst.setInt(2, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      value = rs.getBoolean("value");
    }
    rs.close();
    pst.close();
    return value;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @param value    Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void saveBoolean(Connection db, int optionId, int id, boolean value) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO product_option_boolean(product_option_id, id, value) " +
        "VALUES (?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, optionId);
    pst.setInt(++i, id);
    pst.setBoolean(++i, value);
    pst.execute();
    pst.close();
  }


  /**
   * Gets the double attribute of the Configurator object
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @return The double value
   * @throws SQLException Description of the Exception
   */
  public double getDouble(Connection db, int optionId, int id) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    double value = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT value " +
        "FROM product_option_float " +
        "WHERE product_option_id = ? AND id = ? ");
    pst.setInt(1, optionId);
    pst.setInt(2, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      value = rs.getDouble("value");
    }
    rs.close();
    pst.close();
    return value;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @param value    Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void saveDouble(Connection db, int optionId, int id, double value) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO product_option_float(product_option_id, id, value) " +
        "VALUES (?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, optionId);
    pst.setInt(++i, id);
    pst.setDouble(++i, value);
    pst.execute();
    pst.close();
  }


  /**
   * Gets the timestamp attribute of the Configurator object
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @return The timestamp value
   * @throws SQLException Description of the Exception
   */
  public Timestamp getTimestamp(Connection db, int optionId, int id) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    Timestamp value = null;
    PreparedStatement pst = db.prepareStatement(
        "SELECT value " +
        "FROM product_option_timestamp " +
        "WHERE product_option_id = ? AND id = ? ");
    pst.setInt(1, optionId);
    pst.setInt(2, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      value = rs.getTimestamp("value");
    }
    rs.close();
    pst.close();
    return value;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @param value    Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void saveTimestamp(Connection db, int optionId, int id, Timestamp value) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO product_option_timestamp(product_option_id, id, value) " +
        "VALUES (?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, optionId);
    pst.setInt(++i, id);
    pst.setTimestamp(++i, value);
    pst.execute();
    pst.close();
  }


  /**
   * Gets the valueCount attribute of the Configurator object
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param resultId Description of the Parameter
   * @return The valueCount value
   * @throws SQLException Description of the Exception
   */
  public int getValueCount(Connection db, int optionId, int resultId) throws SQLException {
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(*) AS count " +
        "FROM product_option_values " +
        "WHERE option_id = ? AND result_id = ? ");
    pst.setInt(1, optionId);
    pst.setInt(2, resultId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      return (rs.getInt("count"));
    }
    return 0;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @param value    Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int updateText(Connection db, int optionId, int id, String value) throws SQLException {
    int resultCount = -1;
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE product_option_text " +
        "SET value = ? " +
        "WHERE product_option_id = ? AND id = ? ");
    int i = 0;
    pst.setString(++i, value);
    pst.setInt(++i, optionId);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @param value    Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int updateInteger(Connection db, int optionId, int id, int value) throws SQLException {
    int resultCount = -1;
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE product_option_integer " +
        "SET value = ? " +
        "WHERE product_option_id = ? AND id = ? ");
    int i = 0;
    pst.setInt(++i, value);
    pst.setInt(++i, optionId);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @param value    Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int updateBoolean(Connection db, int optionId, int id, boolean value) throws SQLException {
    int resultCount = -1;
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE product_option_boolean " +
        "SET value = ? " +
        "WHERE product_option_id = ? AND id = ? ");
    int i = 0;
    pst.setBoolean(++i, value);
    pst.setInt(++i, optionId);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @param value    Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int updateDouble(Connection db, int optionId, int id, double value) throws SQLException {
    int resultCount = -1;
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE product_option_float " +
        "SET value = ? " +
        "WHERE product_option_id = ? AND id = ? ");
    int i = 0;
    pst.setDouble(++i, value);
    pst.setInt(++i, optionId);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @param id       Description of the Parameter
   * @param value    Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int updateTimestamp(Connection db, int optionId, int id, Timestamp value) throws SQLException {
    int resultCount = -1;
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE product_option_timestamp " +
        "SET value = ? " +
        "WHERE product_option_id = ? AND id = ? ");
    int i = 0;
    pst.setTimestamp(++i, value);
    pst.setInt(++i, optionId);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param optionId  Description of the Parameter
   * @param id        Description of the Parameter
   * @param tableName Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean deleteValue(Connection db, int optionId, int id, String tableName) throws SQLException {
    boolean result = false;
    if (optionId == -1) {
      throw new SQLException("Invalid Option Id");
    }
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM product_option_" + tableName + " " +
        "WHERE product_option_id = ? AND id = ? ");
    int i = 0;
    pst.setInt(++i, optionId);
    pst.setInt(++i, id);
    pst.execute();
    pst.close();
    result = true;
    return result;
  }
}

