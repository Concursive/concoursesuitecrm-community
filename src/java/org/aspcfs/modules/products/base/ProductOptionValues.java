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

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.actionlist.base.ActionList;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionlist.base.ActionItemLogList;
import org.aspcfs.modules.base.CustomFieldRecordList;

/**
 *  Option Prices for any Product Option
 *
 *@author     partha
 *@created    March 19, 2004
 *@version    $Id: ProductOptionValues.java,v 1.1.2.2 2004/03/19 20:46:00 partha
 *      Exp $
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
  //other supplimentary fields
  private String optionName = null;
  private String productName = null;


  /**
   *  Gets the id attribute of the ProductOptionValues object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the optionId attribute of the ProductOptionValues object
   *
   *@return    The optionId value
   */
  public int getOptionId() {
    return optionId;
  }


  /**
   *  Gets the resultId attribute of the ProductOptionValues object
   *
   *@return    The resultId value
   */
  public int getResultId() {
    return resultId;
  }


  /**
   *  Gets the priceCurrency attribute of the ProductOptionValues object
   *
   *@return    The priceCurrency value
   */
  public int getPriceCurrency() {
    return priceCurrency;
  }


  /**
   *  Gets the priceAmount attribute of the ProductOptionValues object
   *
   *@return    The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   *  Gets the recurringCurrency attribute of the ProductOptionValues object
   *
   *@return    The recurringCurrency value
   */
  public int getRecurringCurrency() {
    return recurringCurrency;
  }


  /**
   *  Gets the recurringAmount attribute of the ProductOptionValues object
   *
   *@return    The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   *  Gets the recurringType attribute of the ProductOptionValues object
   *
   *@return    The recurringType value
   */
  public int getRecurringType() {
    return recurringType;
  }


  /**
   *  Gets the optionName attribute of the ProductOptionValues object
   *
   *@return    The optionName value
   */
  public String getOptionName() {
    return optionName;
  }


  /**
   *  Gets the productName attribute of the ProductOptionValues object
   *
   *@return    The productName value
   */
  public String getProductName() {
    return productName;
  }


  /**
   *  Gets the description attribute of the ProductOptionValues object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the msrpCurrency attribute of the ProductOptionValues object
   *
   *@return    The msrpCurrency value
   */
  public int getMsrpCurrency() {
    return msrpCurrency;
  }


  /**
   *  Gets the msrpAmount attribute of the ProductOptionValues object
   *
   *@return    The msrpAmount value
   */
  public double getMsrpAmount() {
    return msrpAmount;
  }


  /**
   *  Sets the id attribute of the ProductOptionValues object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ProductOptionValues object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the optionId attribute of the ProductOptionValues object
   *
   *@param  tmp  The new optionId value
   */
  public void setOptionId(int tmp) {
    this.optionId = tmp;
  }


  /**
   *  Sets the optionId attribute of the ProductOptionValues object
   *
   *@param  tmp  The new optionId value
   */
  public void setOptionId(String tmp) {
    this.optionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the resultId attribute of the ProductOptionValues object
   *
   *@param  tmp  The new resultId value
   */
  public void setResultId(int tmp) {
    this.resultId = tmp;
  }


  /**
   *  Sets the resultId attribute of the ProductOptionValues object
   *
   *@param  tmp  The new resultId value
   */
  public void setResultId(String tmp) {
    this.resultId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the priceCurrency attribute of the ProductOptionValues object
   *
   *@param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(int tmp) {
    this.priceCurrency = tmp;
  }


  /**
   *  Sets the priceCurrency attribute of the ProductOptionValues object
   *
   *@param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(String tmp) {
    this.priceCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the priceAmount attribute of the ProductOptionValues object
   *
   *@param  tmp  The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   *  Sets the recurringCurrency attribute of the ProductOptionValues object
   *
   *@param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(int tmp) {
    this.recurringCurrency = tmp;
  }


  /**
   *  Sets the recurringCurrency attribute of the ProductOptionValues object
   *
   *@param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(String tmp) {
    this.recurringCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the recurringAmount attribute of the ProductOptionValues object
   *
   *@param  tmp  The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   *  Sets the recurringType attribute of the ProductOptionValues object
   *
   *@param  tmp  The new recurringType value
   */
  public void setRecurringType(int tmp) {
    this.recurringType = tmp;
  }


  /**
   *  Sets the recurringType attribute of the ProductOptionValues object
   *
   *@param  tmp  The new recurringType value
   */
  public void setRecurringType(String tmp) {
    this.recurringType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the optionName attribute of the ProductOptionValues object
   *
   *@param  tmp  The new optionName value
   */
  public void setOptionName(String tmp) {
    this.optionName = tmp;
  }


  /**
   *  Sets the productName attribute of the ProductOptionValues object
   *
   *@param  tmp  The new productName value
   */
  public void setProductName(String tmp) {
    this.productName = tmp;
  }


  /**
   *  Sets the description attribute of the ProductOptionValues object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the msrpCurrency attribute of the ProductOptionValues object
   *
   *@param  tmp  The new msrpCurrency value
   */
  public void setMsrpCurrency(int tmp) {
    this.msrpCurrency = tmp;
  }


  /**
   *  Sets the msrpCurrency attribute of the ProductOptionValues object
   *
   *@param  tmp  The new msrpCurrency value
   */
  public void setMsrpCurrency(String tmp) {
    this.msrpCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the msrpAmount attribute of the ProductOptionValues object
   *
   *@param  tmp  The new msrpAmount value
   */
  public void setMsrpAmount(double tmp) {
    this.msrpAmount = tmp;
  }


  /**
   *  Constructor for the ProductOptionValues object
   */
  public ProductOptionValues() { }


  /**
   *  Constructor for the ProductOptionValues object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ProductOptionValues(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the ProductOptionValues object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ProductOptionValues(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Product Option Values Number");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT " +
        " poptvalues.*, " +
        " popt.short_description AS option_name , " +
        " pctlg.product_name AS product_name " +
        " FROM product_option_values AS poptvalues " +
        " LEFT JOIN product_option as popt " +
        " ON ( poptvalues.option_id = popt.option_id ) " +
        " LEFT JOIN product_option_map as poptmap " +
        " ON ( poptvalues.value_id = poptmap.value_id ) " +
        " LEFT JOIN product_catalog AS pctlg " +
        " ON ( poptmap.product_id = pctlg.product_id ) " +
        " WHERE poptvalues.value_id = ? "
        );
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Product Category not found");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    // product_option_values table
    this.setId(rs.getInt("value_id"));
    this.setOptionId(DatabaseUtils.getInt(rs, "option_id"));
    this.setResultId(DatabaseUtils.getInt(rs, "result_id"));
    this.setDescription(rs.getString("description"));
    this.setMsrpCurrency(rs.getInt("msrp_currency"));
    this.setMsrpAmount(rs.getDouble("msrp_amount"));
    this.setPriceCurrency(DatabaseUtils.getInt(rs, "price_currency"));
    this.setPriceAmount(rs.getDouble("price_amount"));
    this.setRecurringCurrency(DatabaseUtils.getInt(rs, "price_currency"));
    this.setRecurringAmount(rs.getDouble("recurring_amount"));
    this.setRecurringType(DatabaseUtils.getInt(rs, "recurring_type"));

    // product_option table
    this.setOptionName(rs.getString("option_name"));

    // product_catalog table
    this.setProductName(rs.getString("product_name"));
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  baseFilePath      Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    boolean result = false;
    if (this.getId() == -1) {
      throw new SQLException("Product Category ID not specified.");
    }
    try {
      int i = 0;
      db.setAutoCommit(false);
      /*
       *  /Delete any documents
       *  FileItemList fileList = new FileItemList();
       *  fileList.setLinkModuleId(Constants.DOCUMENTS_PRODUCT_CATEGORY);
       *  fileList.setLinkItemId(this.getId());
       *  fileList.buildList(db);
       *  fileList.delete(db, baseFilePath);
       *  fileList = null;
       *  /Delete any folder data
       *  CustomFieldRecordList folderList = new CustomFieldRecordList();
       *  folderList.setLinkModuleId(Constants.FOLDERS_PRODUCT_CATEGORY);
       *  folderList.setLinkItemId(this.getId());
       *  folderList.buildList(db);
       *  folderList.delete(db);
       *  folderList = null;
       */
      //delete all the dependencies that contain the product_option_values id

      //delete the product_option_values_map s that have category1_id = id
      PreparedStatement pst = db.prepareStatement(
          " DELETE from product_option_values " +
          " WHERE value_id = ? "
          );
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      db.commit();
      result = true;
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    StringBuffer sql = new StringBuffer();
    try {
      db.setAutoCommit(false);
      sql.append(
          " INSERT INTO product_option_values(  option_id, " +
          " result_id, description, msrp_currency, msrp_amount, " +
          " price_currency, price_amount, " +
          " recurring_currency, recurring_amount, recurring_type )"
          );
      sql.append("VALUES ( ?,?,?,?,?,?,?,?,?,? )");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
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
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "product_option_values_value_id_seq");
      db.commit();
      result = true;
    } catch (SQLException e) {
      db.rollback();
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
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (!isValid(db)) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " UPDATE product_option_values SET option_id = ?, result_id = ?, " +
        " description = ?, msrp_currency = ?, mspr_amount = ? " +
        " price_currency = ?, price_amount = ?, recurring_currency = ?, " +
        " recurring_amount = ?, recurring_type = ? "
        );
    sql.append(" WHERE value_id = ? ");

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
    DatabaseUtils.setInt(pst, ++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Gets the valid attribute of the ProductOptionValues object
   *
   *@param  db                Description of the Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  public boolean isValid(Connection db) throws SQLException {
// This method contains additional error catching statements
    if (this.getId() == -1) {
      return false;
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    // This method checks all the mappings for any product_option_values with the current id
    // The currently known mappings are product_option_values_map, product_catalog_category_map
    if (this.getId() == -1) {
      throw new SQLException("Product Category ID not specified");
    }
    String sql = null;
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    /*
     *  /Check for documents
     *  Dependency docDependency = new Dependency();
     *  docDependency.setName("Documents");
     *  docDependency.setCount(FileItemList.retrieveRecordCount(db, Constants.DOCUMENTS_PRODUCT_CATEGORY, this.getId()));
     *  docDependency.setCanDelete(true);
     *  dependencyList.add(docDependency);
     *  /Check for folders
     *  Dependency folderDependency = new Dependency();
     *  folderDependency.setName("Folders");
     *  folderDependency.setCount(CustomFieldRecordList.retrieveRecordCount(db, Constants.FOLDERS_PRODUCT_CATEGORY, this.getId()));
     *  folderDependency.setCanDelete(true);
     *  dependencyList.add(folderDependency);
     */
    return dependencyList;
  }
}

