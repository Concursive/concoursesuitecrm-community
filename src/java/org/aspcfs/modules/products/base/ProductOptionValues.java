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
package org.aspcfs.modules.products.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Option Prices for any Product Option
 *
 * @author partha
 * @version $Id: ProductOptionValues.java,v 1.1.2.2 2004/03/19 20:46:00 partha
 *          Exp $
 * @created March 19, 2004
 */
public class ProductOptionValues extends GenericBean {

  private int id = -1;
  private int optionId = -1;
  private int resultId = -1;
  private String description = null;
  private int msrpCurrency = -1;
  private double msrpAmount = 0.0;
  private int priceCurrency = -1;
  private double priceAmount = 0.0;
  private int recurringCurrency = -1;
  private double recurringAmount = 0.0;
  private int recurringType = -1;
  // multiplier
  private double value = 0.0;
  private double multiplier = 0.0;
  // range
  private int rangeMin = -1;
  private int rangeMax = -1;
  private int costCurrency = -1;
  private double costAmount = 0.0;


  /**
   * Sets the rangeMin attribute of the ProductOptionValues object
   *
   * @param tmp The new rangeMin value
   */
  public void setRangeMin(int tmp) {
    this.rangeMin = tmp;
  }


  /**
   * Sets the rangeMin attribute of the ProductOptionValues object
   *
   * @param tmp The new rangeMin value
   */
  public void setRangeMin(String tmp) {
    this.rangeMin = Integer.parseInt(tmp);
  }


  /**
   * Sets the rangeMax attribute of the ProductOptionValues object
   *
   * @param tmp The new rangeMax value
   */
  public void setRangeMax(int tmp) {
    this.rangeMax = tmp;
  }


  /**
   * Sets the rangeMax attribute of the ProductOptionValues object
   *
   * @param tmp The new rangeMax value
   */
  public void setRangeMax(String tmp) {
    this.rangeMax = Integer.parseInt(tmp);
  }


  /**
   * Sets the costCurrency attribute of the ProductOptionValues object
   *
   * @param tmp The new costCurrency value
   */
  public void setCostCurrency(int tmp) {
    this.costCurrency = tmp;
  }


  /**
   * Sets the costCurrency attribute of the ProductOptionValues object
   *
   * @param tmp The new costCurrency value
   */
  public void setCostCurrency(String tmp) {
    this.costCurrency = Integer.parseInt(tmp);
  }


  /**
   * Sets the costAmount attribute of the ProductOptionValues object
   *
   * @param tmp The new costAmount value
   */
  public void setCostAmount(double tmp) {
    this.costAmount = tmp;
  }


  /**
   * Sets the costAmount attribute of the ProductOptionValues object
   *
   * @param tmp The new costAmount value
   */
  public void setCostAmount(String tmp) {
    this.costAmount = Double.parseDouble(tmp);
  }


  /**
   * Gets the costCurrency attribute of the ProductOptionValues object
   *
   * @return The costCurrency value
   */
  public int getCostCurrency() {
    return costCurrency;
  }


  /**
   * Gets the costAmount attribute of the ProductOptionValues object
   *
   * @return The costAmount value
   */
  public double getCostAmount() {
    return costAmount;
  }


  /**
   * Gets the rangeMin attribute of the ProductOptionValues object
   *
   * @return The rangeMin value
   */
  public int getRangeMin() {
    return rangeMin;
  }


  /**
   * Gets the rangeMax attribute of the ProductOptionValues object
   *
   * @return The rangeMax value
   */
  public int getRangeMax() {
    return rangeMax;
  }


  /**
   * Sets the value attribute of the ProductOptionValues object
   *
   * @param tmp The new value value
   */
  public void setValue(double tmp) {
    this.value = tmp;
  }


  /**
   * Sets the value attribute of the ProductOptionValues object
   *
   * @param tmp The new value value
   */
  public void setValue(String tmp) {
    this.value = Double.parseDouble(tmp);
  }


  /**
   * Sets the multiplier attribute of the ProductOptionValues object
   *
   * @param tmp The new multiplier value
   */
  public void setMultiplier(double tmp) {
    this.multiplier = tmp;
  }


  /**
   * Sets the multiplier attribute of the ProductOptionValues object
   *
   * @param tmp The new multiplier value
   */
  public void setMultiplier(String tmp) {
    this.multiplier = Double.parseDouble(tmp);
  }


  /**
   * Gets the value attribute of the ProductOptionValues object
   *
   * @return The value value
   */
  public double getValue() {
    return value;
  }


  /**
   * Gets the multiplier attribute of the ProductOptionValues object
   *
   * @return The multiplier value
   */
  public double getMultiplier() {
    return multiplier;
  }


  /**
   * Sets the id attribute of the ProductOptionValues object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ProductOptionValues object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the optionId attribute of the ProductOptionValues object
   *
   * @param tmp The new optionId value
   */
  public void setOptionId(int tmp) {
    this.optionId = tmp;
  }


  /**
   * Sets the optionId attribute of the ProductOptionValues object
   *
   * @param tmp The new optionId value
   */
  public void setOptionId(String tmp) {
    this.optionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the resultId attribute of the ProductOptionValues object
   *
   * @param tmp The new resultId value
   */
  public void setResultId(int tmp) {
    this.resultId = tmp;
  }


  /**
   * Sets the resultId attribute of the ProductOptionValues object
   *
   * @param tmp The new resultId value
   */
  public void setResultId(String tmp) {
    this.resultId = Integer.parseInt(tmp);
  }


  /**
   * Sets the description attribute of the ProductOptionValues object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the msrpCurrency attribute of the ProductOptionValues object
   *
   * @param tmp The new msrpCurrency value
   */
  public void setMsrpCurrency(int tmp) {
    this.msrpCurrency = tmp;
  }


  /**
   * Sets the msrpCurrency attribute of the ProductOptionValues object
   *
   * @param tmp The new msrpCurrency value
   */
  public void setMsrpCurrency(String tmp) {
    this.msrpCurrency = Integer.parseInt(tmp);
  }


  /**
   * Sets the msrpAmount attribute of the ProductOptionValues object
   *
   * @param tmp The new msrpAmount value
   */
  public void setMsrpAmount(double tmp) {
    this.msrpAmount = tmp;
  }


  /**
   * Sets the msrpAmount attribute of the ProductOptionValues object
   *
   * @param tmp The new msrpAmount value
   */
  public void setMsrpAmount(String tmp) {
    this.msrpAmount = Double.parseDouble(tmp);
  }


  /**
   * Sets the priceCurrency attribute of the ProductOptionValues object
   *
   * @param tmp The new priceCurrency value
   */
  public void setPriceCurrency(int tmp) {
    this.priceCurrency = tmp;
  }


  /**
   * Sets the priceCurrency attribute of the ProductOptionValues object
   *
   * @param tmp The new priceCurrency value
   */
  public void setPriceCurrency(String tmp) {
    this.priceCurrency = Integer.parseInt(tmp);
  }


  /**
   * Sets the priceAmount attribute of the ProductOptionValues object
   *
   * @param tmp The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   * Sets the priceAmount attribute of the ProductOptionValues object
   *
   * @param tmp The new priceAmount value
   */
  public void setPriceAmount(String tmp) {
    this.priceAmount = Double.parseDouble(tmp);
  }


  /**
   * Sets the recurringCurrency attribute of the ProductOptionValues object
   *
   * @param tmp The new recurringCurrency value
   */
  public void setRecurringCurrency(int tmp) {
    this.recurringCurrency = tmp;
  }


  /**
   * Sets the recurringCurrency attribute of the ProductOptionValues object
   *
   * @param tmp The new recurringCurrency value
   */
  public void setRecurringCurrency(String tmp) {
    this.recurringCurrency = Integer.parseInt(tmp);
  }


  /**
   * Sets the recurringAmount attribute of the ProductOptionValues object
   *
   * @param tmp The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   * Sets the recurringAmount attribute of the ProductOptionValues object
   *
   * @param tmp The new recurringAmount value
   */
  public void setRecurringAmount(String tmp) {
    this.recurringAmount = Double.parseDouble(tmp);
  }


  /**
   * Sets the recurringType attribute of the ProductOptionValues object
   *
   * @param tmp The new recurringType value
   */
  public void setRecurringType(int tmp) {
    this.recurringType = tmp;
  }


  /**
   * Sets the recurringType attribute of the ProductOptionValues object
   *
   * @param tmp The new recurringType value
   */
  public void setRecurringType(String tmp) {
    this.recurringType = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the ProductOptionValues object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the optionId attribute of the ProductOptionValues object
   *
   * @return The optionId value
   */
  public int getOptionId() {
    return optionId;
  }


  /**
   * Gets the resultId attribute of the ProductOptionValues object
   *
   * @return The resultId value
   */
  public int getResultId() {
    return resultId;
  }


  /**
   * Gets the description attribute of the ProductOptionValues object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the msrpCurrency attribute of the ProductOptionValues object
   *
   * @return The msrpCurrency value
   */
  public int getMsrpCurrency() {
    return msrpCurrency;
  }


  /**
   * Gets the msrpAmount attribute of the ProductOptionValues object
   *
   * @return The msrpAmount value
   */
  public double getMsrpAmount() {
    return msrpAmount;
  }


  /**
   * Gets the priceCurrency attribute of the ProductOptionValues object
   *
   * @return The priceCurrency value
   */
  public int getPriceCurrency() {
    return priceCurrency;
  }


  /**
   * Gets the priceAmount attribute of the ProductOptionValues object
   *
   * @return The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   * Gets the recurringCurrency attribute of the ProductOptionValues object
   *
   * @return The recurringCurrency value
   */
  public int getRecurringCurrency() {
    return recurringCurrency;
  }


  /**
   * Gets the recurringAmount attribute of the ProductOptionValues object
   *
   * @return The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   * Gets the recurringType attribute of the ProductOptionValues object
   *
   * @return The recurringType value
   */
  public int getRecurringType() {
    return recurringType;
  }


  /**
   * Constructor for the ProductOptionValues object
   */
  public ProductOptionValues() {
  }


  /**
   * Constructor for the ProductOptionValues object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ProductOptionValues(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the ProductOptionValues object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ProductOptionValues(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Product Option Value");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT " +
        "poptvalues.* " +
        "FROM product_option_values poptvalues " +
        "WHERE poptvalues.value_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Product Option Value not found");
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    // product_option_values table
    this.setId(rs.getInt("value_id"));
    this.setOptionId(DatabaseUtils.getInt(rs, "option_id"));
    this.setResultId(DatabaseUtils.getInt(rs, "result_id"));
    this.setDescription(rs.getString("description"));
    // pricing info
    this.setMsrpCurrency(rs.getInt("msrp_currency"));
    this.setMsrpAmount(rs.getDouble("msrp_amount"));
    this.setPriceCurrency(DatabaseUtils.getInt(rs, "price_currency"));
    this.setPriceAmount(rs.getDouble("price_amount"));
    this.setRecurringCurrency(DatabaseUtils.getInt(rs, "price_currency"));
    this.setRecurringAmount(rs.getDouble("recurring_amount"));
    this.setRecurringType(DatabaseUtils.getInt(rs, "recurring_type"));
    // multiplier info
    this.setValue(rs.getDouble("value"));
    this.setMultiplier(rs.getDouble("multiplier"));
    // range info
    this.setRangeMin(rs.getInt("range_min"));
    this.setRangeMax(rs.getInt("range_max"));
    this.setCostCurrency(DatabaseUtils.getInt(rs, "cost_currency"));
    this.setCostAmount(rs.getDouble("cost_amount"));
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "product_option_values_value_id_seq");
    sql.append(
        "INSERT INTO product_option_values (" + (id > -1 ? "value_id, " : "") + "option_id, " +
        "result_id, description, msrp_currency, msrp_amount, " +
        "price_currency, price_amount, " +
        "recurring_currency, recurring_amount, recurring_type, \"value\", multiplier, range_min, range_max, cost_currency, cost_amount ) ");
    sql.append(
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    DatabaseUtils.setInt(pst, ++i, this.getOptionId());
    DatabaseUtils.setInt(pst, ++i, this.getResultId());
    pst.setString(++i, this.getDescription());
    DatabaseUtils.setInt(pst, ++i, this.getMsrpCurrency());
    pst.setDouble(++i, this.getMsrpAmount());
    DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
    pst.setDouble(++i, this.getPriceAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
    pst.setDouble(++i, this.getRecurringAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
    pst.setDouble(++i, this.getValue());
    pst.setDouble(++i, this.getMultiplier());
    pst.setInt(++i, this.getRangeMin());
    pst.setInt(++i, this.getRangeMax());
    DatabaseUtils.setInt(pst, ++i, this.getCostCurrency());
    pst.setDouble(++i, this.getCostAmount());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(
        db, "product_option_values_value_id_seq", id);
    result = true;
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE product_option_values SET option_id = ?, result_id = ?, " +
        "description = ?, msrp_currency = ?, mspr_amount = ? " +
        "price_currency = ?, price_amount = ?, recurring_currency = ?, " +
        "recurring_amount = ?, recurring_type = ?, \"value\" = ?, multiplier = ?, " +
        "range_min = ?, range_max = ?, cost_currency=?, cost_amount = ? ");
    sql.append("WHERE value_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getOptionId());
    DatabaseUtils.setInt(pst, ++i, this.getResultId());
    pst.setString(++i, this.getDescription());
    DatabaseUtils.setInt(pst, ++i, this.getMsrpCurrency());
    pst.setDouble(++i, this.getMsrpAmount());
    DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
    pst.setDouble(++i, this.getPriceAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
    pst.setDouble(++i, this.getRecurringAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
    pst.setDouble(++i, this.getValue());
    pst.setDouble(++i, this.getMultiplier());
    pst.setInt(++i, this.getRangeMin());
    pst.setInt(++i, this.getRangeMax());
    DatabaseUtils.setInt(pst, ++i, this.getCostCurrency());
    pst.setDouble(++i, this.getCostAmount());
    DatabaseUtils.setInt(pst, ++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Gets the valid attribute of the ProductOptionValues object
   *
   * @param db Description of the Parameter
   * @return The valid value
   * @throws SQLException Description of the Exception
   */
  public boolean isValid(Connection db) throws SQLException {
    if (this.getId() == -1) {
      return false;
    }
    return true;
  }


  /**
   * Gets the numberParams attribute of the ProductOptionValues class
   *
   * @return The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("price_amount");
    thisList.add("msrp_amount");
    thisList.add("cost_amount");
    return thisList;
  }

}

