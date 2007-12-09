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
package org.aspcfs.modules.products.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.products.configurator.OptionConfigurator;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id: ProductOption.java,v 1.4.12.2 2004/11/12 19:55:25 mrajkowski
 *          Exp $
 * @created March 19, 2004
 */
public class ProductOption extends GenericBean {
  private int id = -1;
  private String name = null;
  private int configuratorId = -1;
  private int parentId = -1;
  private String shortDescription = null;
  private String longDescription = null;
  private boolean allowCustomerConfigure = false;
  private boolean allowUserConfigure = false;
  private boolean required = false;
  private boolean enabled = true;
  private Timestamp startDate = null;
  private Timestamp endDate = null;
  private boolean hasMultiplier = false;
  private boolean hasRange = false;
  private String parentName = null;

  //configurator properties
  private String configuratorName = null;
  private int resultType = -1;
  private String label = null;
  private String html = null;
  private double priceAdjust = 0;

  private int productId = -1;
  // resources
  private boolean buildOptionValues = true;
  private ProductOptionValuesList optionValuesList = null;
  private boolean buildConfigDetails = false;


  /**
   * Gets the priceAdjust attribute of the ProductOption object
   *
   * @return The priceAdjust value
   */
  public double getPriceAdjust() {
    return priceAdjust;
  }


  /**
   * Sets the priceAdjust attribute of the ProductOption object
   *
   * @param tmp The new priceAdjust value
   */
  public void setPriceAdjust(double tmp) {
    this.priceAdjust = tmp;
  }


  /**
   * Sets the priceAdjust attribute of the ProductOption object
   *
   * @param tmp The new priceAdjust value
   */
  public void setPriceAdjust(String tmp) {
    this.priceAdjust = Double.parseDouble(tmp);
  }


  /**
   * Gets the html attribute of the ProductOption object
   *
   * @return The html value
   */
  public String getHtml() {
    return html;
  }


  /**
   * Sets the html attribute of the ProductOption object
   *
   * @param tmp The new html value
   */
  public void setHtml(String tmp) {
    this.html = tmp;
  }


  /**
   * Gets the buildConfigDetails attribute of the ProductOption object
   *
   * @return The buildConfigDetails value
   */
  public boolean getBuildConfigDetails() {
    return buildConfigDetails;
  }


  /**
   * Sets the buildConfigDetails attribute of the ProductOption object
   *
   * @param tmp The new buildConfigDetails value
   */
  public void setBuildConfigDetails(boolean tmp) {
    this.buildConfigDetails = tmp;
  }


  /**
   * Sets the buildConfigDetails attribute of the ProductOption object
   *
   * @param tmp The new buildConfigDetails value
   */
  public void setBuildConfigDetails(String tmp) {
    this.buildConfigDetails = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the label attribute of the ProductOption object
   *
   * @return The label value
   */
  public String getLabel() {
    return label;
  }


  /**
   * Sets the label attribute of the ProductOption object
   *
   * @param tmp The new label value
   */
  public void setLabel(String tmp) {
    this.label = tmp;
  }


  /**
   * Sets the hasMultiplier attribute of the ProductOption object
   *
   * @param tmp The new hasMultiplier value
   */
  public void setHasMultiplier(boolean tmp) {
    this.hasMultiplier = tmp;
  }


  /**
   * Sets the hasMultiplier attribute of the ProductOption object
   *
   * @param tmp The new hasMultiplier value
   */
  public void setHasMultiplier(String tmp) {
    this.hasMultiplier = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the hasRange attribute of the ProductOption object
   *
   * @param tmp The new hasRange value
   */
  public void setHasRange(boolean tmp) {
    this.hasRange = tmp;
  }


  /**
   * Sets the hasRange attribute of the ProductOption object
   *
   * @param tmp The new hasRange value
   */
  public void setHasRange(String tmp) {
    this.hasRange = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the hasMultiplier attribute of the ProductOption object
   *
   * @return The hasMultiplier value
   */
  public boolean getHasMultiplier() {
    return hasMultiplier;
  }


  /**
   * Gets the hasRange attribute of the ProductOption object
   *
   * @return The hasRange value
   */
  public boolean getHasRange() {
    return hasRange;
  }


  /**
   * Sets the productId attribute of the ProductOption object
   *
   * @param tmp The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   * Sets the productId attribute of the ProductOption object
   *
   * @param tmp The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   * Gets the productId attribute of the ProductOption object
   *
   * @return The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   * Sets the id attribute of the ProductOption object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ProductOption object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the name attribute of the ProductOption object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the configuratorId attribute of the ProductOption object
   *
   * @param tmp The new configuratorId value
   */
  public void setConfiguratorId(int tmp) {
    this.configuratorId = tmp;
  }


  /**
   * Sets the configuratorId attribute of the ProductOption object
   *
   * @param tmp The new configuratorId value
   */
  public void setConfiguratorId(String tmp) {
    this.configuratorId = Integer.parseInt(tmp);
  }


  /**
   * Sets the parentId attribute of the ProductOption object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   * Sets the parentId attribute of the ProductOption object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   * Sets the shortDescription attribute of the ProductOption object
   *
   * @param tmp The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   * Sets the longDescription attribute of the ProductOption object
   *
   * @param tmp The new longDescription value
   */
  public void setLongDescription(String tmp) {
    this.longDescription = tmp;
  }


  /**
   * Sets the allowCustomerConfigure attribute of the ProductOption object
   *
   * @param tmp The new allowCustomerConfigure value
   */
  public void setAllowCustomerConfigure(boolean tmp) {
    this.allowCustomerConfigure = tmp;
  }


  /**
   * Sets the allowCustomerConfigure attribute of the ProductOption object
   *
   * @param tmp The new allowCustomerConfigure value
   */
  public void setAllowCustomerConfigure(String tmp) {
    this.allowCustomerConfigure = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the allowUserConfigure attribute of the ProductOption object
   *
   * @param tmp The new allowUserConfigure value
   */
  public void setAllowUserConfigure(boolean tmp) {
    this.allowUserConfigure = tmp;
  }


  /**
   * Sets the allowUserConfigure attribute of the ProductOption object
   *
   * @param tmp The new allowUserConfigure value
   */
  public void setAllowUserConfigure(String tmp) {
    this.allowUserConfigure = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the required attribute of the ProductOption object
   *
   * @param tmp The new required value
   */
  public void setRequired(boolean tmp) {
    this.required = tmp;
  }


  /**
   * Sets the required attribute of the ProductOption object
   *
   * @param tmp The new required value
   */
  public void setRequired(String tmp) {
    this.required = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the enabled attribute of the ProductOption object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ProductOption object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the startDate attribute of the ProductOption object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   * Sets the startDate attribute of the ProductOption object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the endDate attribute of the ProductOption object
   *
   * @param tmp The new endDate value
   */
  public void setEndDate(Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   * Sets the endDate attribute of the ProductOption object
   *
   * @param tmp The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the configuratorName attribute of the ProductOption object
   *
   * @param tmp The new configuratorName value
   */
  public void setConfiguratorName(String tmp) {
    this.configuratorName = tmp;
  }


  /**
   * Sets the resultType attribute of the ProductOption object
   *
   * @param tmp The new resultType value
   */
  public void setResultType(int tmp) {
    this.resultType = tmp;
  }


  /**
   * Sets the resultType attribute of the ProductOption object
   *
   * @param tmp The new resultType value
   */
  public void setResultType(String tmp) {
    this.resultType = Integer.parseInt(tmp);
  }


  /**
   * Sets the parentName attribute of the ProductOption object
   *
   * @param tmp The new parentName value
   */
  public void setParentName(String tmp) {
    this.parentName = tmp;
  }


  /**
   * Sets the buildOptionValues attribute of the ProductOption object
   *
   * @param tmp The new buildOptionValues value
   */
  public void setBuildOptionValues(boolean tmp) {
    this.buildOptionValues = tmp;
  }


  /**
   * Sets the buildOptionValues attribute of the ProductOption object
   *
   * @param tmp The new buildOptionValues value
   */
  public void setBuildOptionValues(String tmp) {
    this.buildOptionValues = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the optionValuesList attribute of the ProductOption object
   *
   * @param tmp The new optionValuesList value
   */
  public void setOptionValuesList(ProductOptionValuesList tmp) {
    this.optionValuesList = tmp;
  }


  /**
   * Gets the id attribute of the ProductOption object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the name attribute of the ProductOption object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the configuratorId attribute of the ProductOption object
   *
   * @return The configuratorId value
   */
  public int getConfiguratorId() {
    return configuratorId;
  }


  /**
   * Gets the parentId attribute of the ProductOption object
   *
   * @return The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   * Gets the shortDescription attribute of the ProductOption object
   *
   * @return The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   * Gets the longDescription attribute of the ProductOption object
   *
   * @return The longDescription value
   */
  public String getLongDescription() {
    return longDescription;
  }


  /**
   * Gets the allowCustomerConfigure attribute of the ProductOption object
   *
   * @return The allowCustomerConfigure value
   */
  public boolean getAllowCustomerConfigure() {
    return allowCustomerConfigure;
  }


  /**
   * Gets the allowUserConfigure attribute of the ProductOption object
   *
   * @return The allowUserConfigure value
   */
  public boolean getAllowUserConfigure() {
    return allowUserConfigure;
  }


  /**
   * Gets the required attribute of the ProductOption object
   *
   * @return The required value
   */
  public boolean getRequired() {
    return required;
  }


  /**
   * Gets the enabled attribute of the ProductOption object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the startDate attribute of the ProductOption object
   *
   * @return The startDate value
   */
  public Timestamp getStartDate() {
    return startDate;
  }


  /**
   * Gets the endDate attribute of the ProductOption object
   *
   * @return The endDate value
   */
  public Timestamp getEndDate() {
    return endDate;
  }


  /**
   * Gets the configuratorName attribute of the ProductOption object
   *
   * @return The configuratorName value
   */
  public String getConfiguratorName() {
    return configuratorName;
  }


  /**
   * Gets the resultType attribute of the ProductOption object
   *
   * @return The resultType value
   */
  public int getResultType() {
    return resultType;
  }


  /**
   * Gets the parentName attribute of the ProductOption object
   *
   * @return The parentName value
   */
  public String getParentName() {
    return parentName;
  }


  /**
   * Gets the buildOptionValues attribute of the ProductOption object
   *
   * @return The buildOptionValues value
   */
  public boolean getBuildOptionValues() {
    return buildOptionValues;
  }


  /**
   * Gets the optionValuesList attribute of the ProductOption object
   *
   * @return The optionValuesList value
   */
  public ProductOptionValuesList getOptionValuesList() {
    return optionValuesList;
  }


  /**
   * Constructor for the ProductOption object
   */
  public ProductOption() {
  }


  /**
   * Constructor for the ProductOption object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ProductOption(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the ProductOption object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ProductOption(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Product Option ID");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT " +
        "popt.*, " +
        "poptconf.result_type AS result_type, " +
        "poptconf.configurator_name AS conf_name, " +
        "popt2.option_name AS parent_name " +
        "FROM product_option popt " +
        "LEFT JOIN product_option_configurator poptconf ON ( popt.configurator_id = poptconf.configurator_id ) " +
        "LEFT JOIN product_option popt2 ON ( popt.parent_id = popt2.option_id ) " +
        "WHERE popt.option_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Product Option not found");
    }
    //build resources
    if (buildOptionValues) {
      this.buildOptionValues(db);
    }
    if (buildConfigDetails) {
      this.buildConfigDetails(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildConfigDetails(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Product Option ID not specified");
    }
    // load the configurator
    OptionConfigurator configurator =
        (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
            db, this.getConfiguratorId());
    // query the properties
    configurator.queryProperties(db, this.getId(), false);
    this.setConfiguratorName(configurator.getName());
    this.setLabel(configurator.getLabel());
    this.setHtml(configurator.getHtml());
    this.setPriceAdjust(configurator.getPriceAdjust());
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildOptionValues(Connection db) throws SQLException {
    optionValuesList = new ProductOptionValuesList();
    optionValuesList.setOptionId(this.id);
    optionValuesList.buildList(db);
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    // product_option table
    this.setId(rs.getInt("option_id"));
    this.setConfiguratorId(DatabaseUtils.getInt(rs, "configurator_id"));
    this.setParentId(DatabaseUtils.getInt(rs, "parent_id"));
    this.setShortDescription(rs.getString("short_description"));
    this.setLongDescription(rs.getString("long_description"));
    this.setAllowCustomerConfigure(rs.getBoolean("allow_customer_configure"));
    this.setAllowUserConfigure(rs.getBoolean("allow_user_configure"));
    this.setRequired(rs.getBoolean("required"));
    this.setStartDate(rs.getTimestamp("start_date"));
    this.setEndDate(rs.getTimestamp("end_date"));
    this.setEnabled(rs.getBoolean("enabled"));
    this.setName(rs.getString("option_name"));
    this.setHasMultiplier(rs.getBoolean("has_multiplier"));
    this.setHasRange(rs.getBoolean("has_range"));
    // product_option_configurator table
    this.setConfiguratorName(rs.getString("conf_name"));
    this.setResultType(rs.getInt("result_type"));
    // product_option parent table
    this.setParentName(rs.getString("parent_name"));
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    boolean result = false;
    if (this.getId() == -1) {
      throw new SQLException("Product Option ID not specified.");
    }
    boolean commit = true;
    try {
      int i = 0;
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = null;
      //delete all records that contain option_id in the product_option_map
      pst = db.prepareStatement(
          "DELETE FROM product_option_map " +
          "WHERE option_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      //delete the option values
      pst = db.prepareStatement(
          "DELETE FROM product_option_values " +
          "WHERE option_id = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      //delete the option properties
      pst = db.prepareStatement(
          "DELETE FROM product_option_text WHERE product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      pst = db.prepareStatement(
          "DELETE FROM product_option_integer WHERE product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      pst = db.prepareStatement(
          "DELETE FROM product_option_float WHERE product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      pst = db.prepareStatement(
          "DELETE FROM product_option_timestamp WHERE product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      pst = db.prepareStatement(
          "DELETE FROM product_option_boolean WHERE product_option_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      //delete the option
      i = 0;
      pst = db.prepareStatement(
          "DELETE FROM product_option " +
          "WHERE option_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
      result = true;
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
    return result;
  }


  /**
   * Gets the timeZoneParams attribute of the ProductOption class
   *
   * @return The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("startDate");
    thisList.add("endDate");
    return thisList;
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
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      // insert the product option
      StringBuffer sql = new StringBuffer();
      id = DatabaseUtils.getNextSeq(db, "product_option_option_id_seq");
      sql.append(
          "INSERT INTO product_option(" + (id > -1 ? "option_id, " : "") + "option_name, configurator_id, " +
          "parent_id, short_description, long_description, allow_customer_configure, " +
          "allow_user_configure, required, start_date, end_date, enabled, has_range, has_multiplier ) " +
          "VALUES(" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ? ) ");
      PreparedStatement pst = db.prepareStatement(sql.toString());
      int i = 0;
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setString(++i, this.getName());
      DatabaseUtils.setInt(pst, ++i, this.getConfiguratorId());
      DatabaseUtils.setInt(pst, ++i, this.getParentId());
      pst.setString(++i, this.getShortDescription());
      pst.setString(++i, this.getLongDescription());
      pst.setBoolean(++i, this.getAllowCustomerConfigure());
      pst.setBoolean(++i, this.getAllowUserConfigure());
      pst.setBoolean(++i, this.getRequired());
      DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
      DatabaseUtils.setTimestamp(pst, ++i, this.getEndDate());
      pst.setBoolean(++i, this.getEnabled());
      pst.setBoolean(++i, this.getHasRange());
      pst.setBoolean(++i, this.getHasMultiplier());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "product_option_option_id_seq", id);
      if (this.productId != -1) {
        int seqId = DatabaseUtils.getNextSeq(
            db, "product_option_map_product_option_id_seq");
        pst = db.prepareStatement(
            "INSERT INTO product_option_map (" + (seqId > -1 ? "product_option_id, " : "") + "option_id, product_id) " +
            "VALUES (" + (seqId > -1 ? "?, " : "") + "?, ? )");
        i = 0;
        if (seqId > -1) {
          pst.setInt(++i, seqId);
        }
        pst.setInt(++i, this.id);
        pst.setInt(++i, this.productId);
        pst.execute();
        pst.close();
      }
      if (commit) {
        db.commit();
      }
      result = true;
    } catch (Exception e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insertClone(Connection db) throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      //insert the option and its properties
      OptionConfigurator configurator =
          (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
              db, this.getConfiguratorId());
      //option to be cloned has an optionId of the source option.
      configurator.queryProperties(db, this.getId(), false);
      configurator.saveProperties(db, this);

      if (commit) {
        db.commit();
      }
      result = true;
    } catch (Exception e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
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
        "UPDATE product_option " +
        "SET option_name = ?, configurator_id = ?, parent_id = ?, " +
        "short_description = ?, long_description = ?, " +
        "allow_customer_configure = ?, allow_user_configure = ?, " +
        "required = ?, start_date = ?, end_date = ?, enabled = ?, has_range = ?, has_multiplier = ? " +
        "WHERE option_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getName());
    DatabaseUtils.setInt(pst, ++i, this.getConfiguratorId());
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getLongDescription());
    pst.setBoolean(++i, this.getAllowCustomerConfigure());
    pst.setBoolean(++i, this.getAllowUserConfigure());
    pst.setBoolean(++i, this.getRequired());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getEndDate());
    pst.setBoolean(++i, this.getEnabled());
    pst.setBoolean(++i, this.getHasRange());
    pst.setBoolean(++i, this.getHasMultiplier());
    DatabaseUtils.setInt(pst, ++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Gets the valid attribute of the ProductOption object
   *
   * @param db Description of the Parameter
   * @return The valid value
   * @throws SQLException Description of the Exception
   */
  public boolean isValid(Connection db) throws SQLException {
    if (startDate != null && endDate != null) {
      if (startDate.after(endDate)) {
        errors.put(
            "startDateError", "Start Date cannot be after Expiration Date");
      }
    }
    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   * Adds a feature to the Catalog attribute of the ProductOption object
   *
   * @param db        The feature to be added to the Catalog attribute
   * @param productId The feature to be added to the Catalog attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int addProductMapping(Connection db, int productId) throws SQLException {
    int result = -1;
    int i = 0;
    if (this.getId() == -1) {
      throw new SQLException("Invalid option id");
    }
    int seqId = DatabaseUtils.getNextSeq(
        db, "product_option_map_product_option_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO product_option_map (" + (seqId > -1 ? "product_option_id, " : "") + "product_id, option_id) " +
        "VALUES (" + (seqId > -1 ? "?, " : "") + "?, ? ); ");
    if (seqId > -1) {
      pst.setInt(++i, seqId);
    }
    pst.setInt(++i, productId);
    pst.setInt(++i, this.getId());
    result = pst.executeUpdate();
    pst.close();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param productId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean removeProductMapping(Connection db, int productId) throws SQLException {
    boolean result = false;
    if (this.getId() == -1) {
      throw new SQLException("Invalid Option ID");
    }
    int i = 0;
    // Delete the mapping between this product and option
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM product_option_map " +
        "WHERE product_id = ? AND option_id = ? ");
    pst.setInt(++i, productId);
    pst.setInt(++i, this.getId());
    result = pst.execute();
    pst.close();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param optName Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean lookupId(Connection db, String optName) throws SQLException {
    boolean result = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS counter " +
        "FROM product_option " +
        "WHERE short_description = ? ");
    pst.setString(1, optName);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      int buffer = rs.getInt("counter");
      if (buffer != 0) {
        result = true;
      }
    }
    rs.close();
    pst.close();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param optionId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int getNextRangeMin(Connection db, int optionId) throws SQLException {
    int nextMin = -1;
    if (optionId == -1) {
      throw new SQLException("Invalid Option ID specified");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT MAX(range_max) AS maxrange " +
        "FROM product_option_values " +
        "WHERE option_id = ? ");
    pst.setInt(1, optionId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      nextMin = rs.getInt("maxrange");
    }
    rs.close();
    pst.close();
    return nextMin + 1;
  }


  /**
   * Gets the productOptionId attribute of the ProductOption object
   *
   * @param db        Description of the Parameter
   * @param productId Description of the Parameter
   * @return The productOptionId value
   * @throws SQLException Description of the Exception
   */
  public int getProductOptionId(Connection db, int productId) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Invalid ID");
    }
    int result = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT product_option_id " +
        "FROM product_option_map " +
        "WHERE product_id = ? " +
        "AND option_id = ? ");
    pst.setInt(1, productId);
    pst.setInt(2, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = rs.getInt("product_option_id");
    }
    rs.close();
    pst.close();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Product Option ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    //Check for the current product mappings in product_option_map
    int i = 0;
    pst = db.prepareStatement(
        "SELECT count(*) AS productcount " +
        "FROM product_option_map " +
        "WHERE option_id = ? ");
    pst.setInt(++i, this.getId());
    rs = pst.executeQuery();
    if (rs.next()) {
      int productCount = rs.getInt("productcount");
      if (productCount != 0) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("numberOfCatalogOptionMappings");
        thisDependency.setCount(productCount);
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }
    }
    rs.close();
    pst.close();
    
    //Check for the current product mappings in product_option_map
    i = 0;
    pst = db.prepareStatement(
        "SELECT count(*) AS quoteoptioncount " +
        "FROM quote_product_options qpo, product_option_map pom " +
        "WHERE qpo.product_option_id = pom.product_option_id AND option_id = ? ");
    pst.setInt(++i, this.getId());
    rs = pst.executeQuery();
    if (rs.next()) {
      int quoteCount = rs.getInt("quoteoptioncount");
      if (quoteCount != 0) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("numberOfQuoteOptionMappings");
        thisDependency.setCount(quoteCount);
        thisDependency.setCanDelete(false);
        dependencyList.add(thisDependency);
      }
    }
    rs.close();
    pst.close();
    return dependencyList;
  }
}

