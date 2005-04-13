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
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 *  This class contains the price info for a Product Catalog.
 *
 *@author     partha
 *@created    March 19, 2004
 *@version    $Id: ProductCatalogPricing.java,v 1.1.2.2 2004/03/19 20:46:01
 *      partha Exp $
 */
public class ProductCatalogPricing extends GenericBean {

  private int id = -1;
  private int productId = -1;
  private int taxId = -1;
  private int msrpCurrency = -1;
  private double msrpAmount = 0.0;
  private int priceCurrency = -1;
  private double priceAmount = 0.0;
  private int recurringCurrency = -1;
  private double recurringAmount = 0.0;
  private int recurringType = -1;
  private int enteredBy = -1;
  private Timestamp entered = null;
  private int modifiedBy = -1;
  private Timestamp modified = null;
  private Timestamp startDate = null;
  private Timestamp expirationDate = null;
  private boolean enabled = false;
  private int costCurrency = -1;
  private double costAmount = 0.0;

  //other supplimentary fields
  private String productName = null;
  private String taxName = null;
  private String msrpCurrencyName = null;
  private String priceCurrencyName = null;
  private String recurringCurrencyName = null;
  private String recurringTypeName = null;
  private String costCurrencyName = null;



  /**
   *  Sets the enabled attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the costCurrency attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new costCurrency value
   */
  public void setCostCurrency(int tmp) {
    this.costCurrency = tmp;
  }


  /**
   *  Sets the costCurrency attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new costCurrency value
   */
  public void setCostCurrency(String tmp) {
    this.costCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the costAmount attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new costAmount value
   */
  public void setCostAmount(double tmp) {
    this.costAmount = tmp;
  }


  /**
   *  Sets the costAmount attribute of the ProductOptionValues object
   *
   *@param  tmp  The new costAmount value
   */
  public void setCostAmount(String tmp) {
    this.costAmount = Double.parseDouble(tmp);
  }


  /**
   *  Sets the costCurrencyName attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new costCurrencyName value
   */
  public void setCostCurrencyName(String tmp) {
    this.costCurrencyName = tmp;
  }


  /**
   *  Gets the costCurrencyName attribute of the ProductCatalogPricing object
   *
   *@return    The costCurrencyName value
   */
  public String getCostCurrencyName() {
    return costCurrencyName;
  }


  /**
   *  Gets the costCurrency attribute of the ProductCatalogPricing object
   *
   *@return    The costCurrency value
   */
  public int getCostCurrency() {
    return costCurrency;
  }


  /**
   *  Gets the costAmount attribute of the ProductCatalogPricing object
   *
   *@return    The costAmount value
   */
  public double getCostAmount() {
    return costAmount;
  }


  /**
   *  Gets the enabled attribute of the ProductCatalogPricing object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the taxName attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new taxName value
   */
  public void setTaxName(String tmp) {
    this.taxName = tmp;
  }


  /**
   *  Gets the taxName attribute of the ProductCatalogPricing object
   *
   *@return    The taxName value
   */
  public String getTaxName() {
    return taxName;
  }


  /**
   *  Sets the msrpCurrencyName attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new msrpCurrencyName value
   */
  public void setMsrpCurrencyName(String tmp) {
    this.msrpCurrencyName = tmp;
  }


  /**
   *  Sets the priceCurrencyName attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new priceCurrencyName value
   */
  public void setPriceCurrencyName(String tmp) {
    this.priceCurrencyName = tmp;
  }


  /**
   *  Sets the recurringCurrencyName attribute of the ProductCatalogPricing
   *  object
   *
   *@param  tmp  The new recurringCurrencyName value
   */
  public void setRecurringCurrencyName(String tmp) {
    this.recurringCurrencyName = tmp;
  }


  /**
   *  Sets the recurringTypeName attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new recurringTypeName value
   */
  public void setRecurringTypeName(String tmp) {
    this.recurringTypeName = tmp;
  }


  /**
   *  Gets the msrpCurrencyName attribute of the ProductCatalogPricing object
   *
   *@return    The msrpCurrencyName value
   */
  public String getMsrpCurrencyName() {
    return msrpCurrencyName;
  }


  /**
   *  Gets the priceCurrencyName attribute of the ProductCatalogPricing object
   *
   *@return    The priceCurrencyName value
   */
  public String getPriceCurrencyName() {
    return priceCurrencyName;
  }


  /**
   *  Gets the recurringCurrencyName attribute of the ProductCatalogPricing
   *  object
   *
   *@return    The recurringCurrencyName value
   */
  public String getRecurringCurrencyName() {
    return recurringCurrencyName;
  }


  /**
   *  Gets the recurringTypeName attribute of the ProductCatalogPricing object
   *
   *@return    The recurringTypeName value
   */
  public String getRecurringTypeName() {
    return recurringTypeName;
  }


  /**
   *  Gets the id attribute of the ProductCatalogPricing object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the productId attribute of the ProductCatalogPricing object
   *
   *@return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Gets the taxId attribute of the ProductCatalogPricing object
   *
   *@return    The taxId value
   */
  public int getTaxId() {
    return taxId;
  }


  /**
   *  Gets the msrpCurrency attribute of the ProductCatalogPricing object
   *
   *@return    The msrpCurrency value
   */
  public int getMsrpCurrency() {
    return msrpCurrency;
  }


  /**
   *  Gets the msrpAmount attribute of the ProductCatalogPricing object
   *
   *@return    The msrpAmount value
   */
  public double getMsrpAmount() {
    return msrpAmount;
  }


  /**
   *  Gets the priceCurrency attribute of the ProductCatalogPricing object
   *
   *@return    The priceCurrency value
   */
  public int getPriceCurrency() {
    return priceCurrency;
  }


  /**
   *  Gets the priceAmount attribute of the ProductCatalogPricing object
   *
   *@return    The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   *  Gets the recurringCurrency attribute of the ProductCatalogPricing object
   *
   *@return    The recurringCurrency value
   */
  public int getRecurringCurrency() {
    return recurringCurrency;
  }


  /**
   *  Gets the recurringAmount attribute of the ProductCatalogPricing object
   *
   *@return    The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   *  Gets the recurringType attribute of the ProductCatalogPricing object
   *
   *@return    The recurringType value
   */
  public int getRecurringType() {
    return recurringType;
  }


  /**
   *  Gets the enteredBy attribute of the ProductCatalogPricing object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the ProductCatalogPricing object
   *
   *@return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the ProductCatalogPricing object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the ProductCatalogPricing object
   *
   *@return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the startDate attribute of the ProductCatalogPricing object
   *
   *@return    The startDate value
   */
  public Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Gets the expirationDate attribute of the ProductCatalogPricing object
   *
   *@return    The expirationDate value
   */
  public Timestamp getExpirationDate() {
    return expirationDate;
  }


  /**
   *  Gets the productName attribute of the ProductCatalogPricing object
   *
   *@return    The productName value
   */
  public String getProductName() {
    return productName;
  }


  /**
   *  Sets the id attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productId attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the taxId attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new taxId value
   */
  public void setTaxId(int tmp) {
    this.taxId = tmp;
  }


  /**
   *  Sets the taxId attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new taxId value
   */
  public void setTaxId(String tmp) {
    this.taxId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the msrpCurrency attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new msrpCurrency value
   */
  public void setMsrpCurrency(int tmp) {
    this.msrpCurrency = tmp;
  }


  /**
   *  Sets the msrpCurrency attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new msrpCurrency value
   */
  public void setMsrpCurrency(String tmp) {
    this.msrpCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the msrpAmount attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new msrpAmount value
   */
  public void setMsrpAmount(double tmp) {
    this.msrpAmount = tmp;
  }


  /**
   *  Sets the msrpAmount attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new msrpAmount value
   */
  public void setMsrpAmount(String tmp) {
    this.msrpAmount = Double.parseDouble(tmp);
  }


  /**
   *  Sets the priceCurrency attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(int tmp) {
    this.priceCurrency = tmp;
  }


  /**
   *  Sets the priceCurrency attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new priceCurrency value
   */
  public void setPriceCurrency(String tmp) {
    this.priceCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the priceAmount attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   *  Sets the priceAmount attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new priceAmount value
   */
  public void setPriceAmount(String tmp) {
    this.priceAmount = Double.parseDouble(tmp);
  }


  /**
   *  Sets the recurringCurrency attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(int tmp) {
    this.recurringCurrency = tmp;
  }


  /**
   *  Sets the recurringCurrency attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new recurringCurrency value
   */
  public void setRecurringCurrency(String tmp) {
    this.recurringCurrency = Integer.parseInt(tmp);
  }


  /**
   *  Sets the recurringAmount attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   *  Sets the recurringAmount attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new recurringAmount value
   */
  public void setRecurringAmount(String tmp) {
    this.recurringAmount = Double.parseDouble(tmp);
  }


  /**
   *  Sets the recurringType attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new recurringType value
   */
  public void setRecurringType(int tmp) {
    this.recurringType = tmp;
  }


  /**
   *  Sets the recurringType attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new recurringType value
   */
  public void setRecurringType(String tmp) {
    this.recurringType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the startDate attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the startDate attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the expirationDate attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new expirationDate value
   */
  public void setExpirationDate(Timestamp tmp) {
    this.expirationDate = tmp;
  }


  /**
   *  Sets the expirationDate attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new expirationDate value
   */
  public void setExpirationDate(String tmp) {
    this.expirationDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the productName attribute of the ProductCatalogPricing object
   *
   *@param  tmp  The new productName value
   */
  public void setProductName(String tmp) {
    this.productName = tmp;
  }


  /**
   *  Constructor for the ProductCatalogPricing object
   */
  public ProductCatalogPricing() { }


  /**
   *  Constructor for the ProductCatalogPricing object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ProductCatalogPricing(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the ProductCatalogPricing object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ProductCatalogPricing(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Product Catalog Pricing ID");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT " +
        "pctlgprice.*, " +
        "pctlg.product_name AS product_name, " +
        "lpt.description AS tax_name, " +
        "lcmsrp.description AS msrp_currency_name, " +
        "lcpc.description AS price_currency_name, " +
        "lcrc.description AS recurring_currency_name, " +
        "lrt.description AS recurring_type_name, " +
        "lccc.description AS cost_currency_name " +
        "FROM product_catalog_pricing pctlgprice " +
        "LEFT JOIN product_catalog AS pctlg ON (pctlgprice.product_id = pctlg.product_id) " +
        "LEFT JOIN lookup_product_tax AS lpt ON (pctlgprice.tax_id = lpt.code) " +
        "LEFT JOIN lookup_currency AS lcmsrp ON (pctlgprice.msrp_currency = lcmsrp.code) " +
        "LEFT JOIN lookup_currency AS lcpc ON (pctlgprice.price_currency = lcpc.code) " +
        "LEFT JOIN lookup_currency AS lcrc ON (pctlgprice.recurring_currency = lcrc.code) " +
        "LEFT JOIN lookup_recurring_type AS lrt ON (pctlgprice.recurring_type = lrt.code) " +
        "LEFT JOIN lookup_currency AS lccc ON (pctlgprice.cost_currency = lccc.code) " +
        "WHERE pctlgprice.price_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Product Catalog Pricing not found");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("price_id"));
    this.setProductId(DatabaseUtils.getInt(rs, "product_id"));
    this.setTaxId(DatabaseUtils.getInt(rs, "tax_id"));
    this.setMsrpCurrency(DatabaseUtils.getInt(rs, "msrp_currency"));
    this.setMsrpAmount(rs.getDouble("msrp_amount"));
    this.setPriceCurrency(DatabaseUtils.getInt(rs, "price_currency"));
    this.setPriceAmount(rs.getDouble("price_amount"));
    this.setRecurringCurrency(DatabaseUtils.getInt(rs, "recurring_currency"));
    this.setRecurringAmount(rs.getDouble("recurring_amount"));
    this.setRecurringType(DatabaseUtils.getInt(rs, "recurring_type"));
    this.setEnteredBy(DatabaseUtils.getInt(rs, "enteredBy"));
    this.setEntered(rs.getTimestamp("entered"));
    this.setModifiedBy(DatabaseUtils.getInt(rs, "modifiedby"));
    this.setModified(rs.getTimestamp("modified"));
    this.setStartDate(rs.getTimestamp("start_date"));
    this.setExpirationDate(rs.getTimestamp("expiration_date"));
    this.setEnabled(rs.getBoolean("enabled"));
    this.setCostCurrency(DatabaseUtils.getInt(rs, "cost_currency"));
    this.setCostAmount(rs.getDouble("cost_amount"));
    //product_catalog
    this.setProductName(rs.getString("product_name"));
    this.setTaxName(rs.getString("tax_name"));
    this.setMsrpCurrencyName(rs.getString("msrp_currency_name"));
    this.setPriceCurrencyName(rs.getString("price_currency_name"));
    this.setRecurringCurrencyName(rs.getString("recurring_currency_name"));
    this.setRecurringTypeName(rs.getString("recurring_type_name"));
    this.setCostCurrencyName(rs.getString("cost_currency_name"));
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
      throw new SQLException("Product Catalog Pricing ID invalid");
    }
    boolean commit = true;
    int i = 0;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = null;
      /*
       *  Delete any documents
       *  FileItemList fileList = new FileItemList();
       *  fileList.setLinkModuleId(Constants.DOCUMENTS_PRODUCT_CATALOG_PRICING);
       *  fileList.setLinkItemId(this.getId());
       *  fileList.buildList(db);
       *  fileList.delete(db, baseFilePath);
       *  fileList = null;
       *  Delete any folder data
       *  CustomFieldRecordList folderList = new CustomFieldRecordList();
       *  folderList.setLinkModuleId(Constants.FOLDERS_PRODUCT_CATALOG_PRICING);
       *  folderList.setLinkItemId(this.getId());
       *  folderList.buildList(db);
       *  folderList.delete(db);
       *  folderList = null;
       */
      //delete all the dependencies
      //delete all records that contain price_id in the product_catalog_pricing table
      pst = db.prepareStatement(
          " DELETE from product_catalog_pricing " +
          " WHERE price_id = ? "
          );
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
   *  Gets the timeZoneParams attribute of the ProductCatalogPricing class
   *
   *@return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("startDate");
    thisList.add("expirationDate");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    // check to see if the price being inserted is enabled.
    // If enabled then disable all the other prices associated with
    // the product
    boolean canCommit = false;
    boolean result = false;
    try {
      canCommit = db.getAutoCommit();
      if (canCommit) {
        db.setAutoCommit(false);
      }
      // insert the new price which is active
      StringBuffer sql = new StringBuffer();
      sql.append(
          " INSERT INTO product_catalog_pricing( " +
          " product_id, tax_id, msrp_currency, msrp_amount, " +
          " price_currency , price_amount , recurring_currency , " +
          " recurring_amount, recurring_type, enteredby, "
          );
      if (entered != null) {
        sql.append(" entered, ");
      }
      sql.append(" modifiedby, ");
      if (modified != null) {
        sql.append(" modified, ");
      }
      sql.append(" start_date, expiration_date, enabled, cost_currency, cost_amount) ");
      sql.append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (entered != null) {
        sql.append(" ?, ");
      }
      sql.append(" ?, ");
      if (modified != null) {
        sql.append(" ?, ");
      }
      sql.append("?, ?, ?, ?, ? )");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, this.getProductId());
      DatabaseUtils.setInt(pst, ++i, this.getTaxId());
      DatabaseUtils.setInt(pst, ++i, this.getMsrpCurrency());
      pst.setDouble(++i, this.getMsrpAmount());
      DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
      pst.setDouble(++i, this.getPriceAmount());
      DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
      pst.setDouble(++i, this.getRecurringAmount());
      DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
      DatabaseUtils.setInt(pst, ++i, this.getEnteredBy());
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
      if (modified != null) {
        pst.setTimestamp(++i, this.getModified());
      }
      DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
      DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
      pst.setBoolean(++i, this.getEnabled());
      DatabaseUtils.setInt(pst, ++i, this.getCostCurrency());
      pst.setDouble(++i, this.getCostAmount());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "product_catalog_pricing_price_id_seq");
      result = true;
      if (canCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (canCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (canCommit) {
        db.setAutoCommit(true);
      }
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
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE product_catalog_pricing SET " +
        "product_id = ?, tax_id = ?, msrp_currency = ?, " +
        "msrp_amount = ?, price_currency = ?, price_amount = ?, " +
        "recurring_currency = ?, recurring_amount = ?, " +
        "recurring_type = ?, modifiedby = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "start_date = ?, expiration_date = ?, enabled = ?, " +
        "cost_currency = ?, cost_amount = ? " +
        "WHERE price_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getProductId());
    DatabaseUtils.setInt(pst, ++i, this.getTaxId());
    DatabaseUtils.setInt(pst, ++i, this.getMsrpCurrency());
    pst.setDouble(++i, this.getMsrpAmount());
    DatabaseUtils.setInt(pst, ++i, this.getPriceCurrency());
    pst.setDouble(++i, this.getPriceAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringCurrency());
    pst.setDouble(++i, this.getRecurringAmount());
    DatabaseUtils.setInt(pst, ++i, this.getRecurringType());
    DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
    pst.setBoolean(++i, this.getEnabled());
    DatabaseUtils.setInt(pst, ++i, this.getCostCurrency());
    pst.setDouble(++i, this.getCostAmount());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    // This method checks all the records of product_catalog_pricing with the current id

    if (this.getId() == -1) {
      throw new SQLException("Product Catalog Pricing ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    /*
     *  Check for documents
     *  Dependency docDependency = new Dependency();
     *  docDependency.setName("documents");
     *  docDependency.setCount(FileItemList.retrieveRecordCount(db, Constants.DOCUMENTS_PRODUCT_CATALOG, this.getId()));
     *  docDependency.setCanDelete(true);
     *  dependencyList.add(docDependency);
     *  Check for folders
     *  Dependency folderDependency = new Dependency();
     *  folderDependency.setName("folders");
     *  folderDependency.setCount(CustomFieldRecordList.retrieveRecordCount(db, Constants.FOLDERS_PRODUCT_CATALOG, this.getId()));
     *  folderDependency.setCanDelete(true);
     *  dependencyList.add(folderDependency);
     */
    //Check all the product_catalog records that have only this price
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) AS pricecount " +
          " FROM product_catalog_pricing AS pcp " +
          " WHERE pcp.price_id = ? AND " +
          " pcp.product_id NOT IN ( " +
          " SELECT product_id " +
          " FROM product_catalog_pricing " +
          " WHERE price_id <> ? ) "
          );
      pst.setInt(++i, this.getId());
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int priceCount = rs.getInt("pricecount");
        if (priceCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("numberOfProductsThatHaveOnlyThisPrice");
          thisDependency.setCount(priceCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    }
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int disableAllPrices(Connection db) throws SQLException {
    if (this.getProductId() == -1) {
      throw new SQLException("Product Catalog Id not specified");
    }
    int resultCount = -1;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE product_catalog_pricing " +
        "SET enabled = false " +
        "WHERE product_catalog_pricing.product_id = ? ");
    pst.setInt(1, this.getProductId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  status            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean updatePriceStatus(Connection db, boolean status) throws SQLException {
    if (this.getProductId() == -1) {
      throw new SQLException("Product Catalog Id not specified");
    }
    if (status) {
      //price is being enabled. hence check to see if it is in a valid state
      if (!isValidPriceState(db)) {
        return false;
      }
    }
    int resultCount = -1;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "UPDATE product_catalog_pricing " +
          "SET enabled = ? " +
          "WHERE product_id = ? AND price_id = ? ");
      pst.setBoolean(++i, status);
      pst.setInt(++i, this.getProductId());
      pst.setInt(++i, this.getId());
      resultCount = pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (Exception e) {
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
    return (resultCount > 0);
  }


  /**
   *  Gets the priceValid attribute of the ProductCatalogPricing object
   *
   *@return                   The priceValid value
   *@exception  SQLException  Description of the Exception
   */
  public boolean isPriceValid() throws SQLException {
    if (this.id == -1) {
      throw new SQLException("Price ID not specified");
    }
    boolean valid = true;
    if (expirationDate != null) {
      //check if expiration date is before the todays date
      if (expirationDate.before(DateUtils.getDate(Calendar.getInstance()))) {
        valid = false;
      } else if (startDate != null) {
        if (expirationDate.before(startDate)) {
          valid = false;
        }
      }
    }
    return valid;
  }


  /**
   *  Gets the numberParams attribute of the ProductCatalogPricing class
   *
   *@return    The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("priceAmount");
    thisList.add("msrpAmount");
    thisList.add("costAmount");
    thisList.add("recurringAmount");
    return thisList;
  }


  /**
   *  Gets the validPriceState attribute of the ProductCatalogPricing object
   *
   *@param  db                Description of the Parameter
   *@param  status            Description of the Parameter
   *@return                   The validPriceState value
   *@exception  SQLException  Description of the Exception
   */
  public boolean isValidPriceState(Connection db) throws SQLException {
    // No Active prices exist. Check to see if this price is value
    if (!isPriceValid()) {
      return false;
    }
    // price needs to be enabled. check if active pricings already exists
    ProductCatalogPricingList priceList = new ProductCatalogPricingList();
    priceList.setProductId(this.productId);
    priceList.setEnabled(Constants.TRUE);
    priceList.buildList(db);
    if (priceList.size() > 0) {
      Iterator activePrices = (Iterator) priceList.iterator();
      while (activePrices.hasNext()) {
        ProductCatalogPricing activePrice = (ProductCatalogPricing) activePrices.next();
        if (this.getStartDate() != null && this.getExpirationDate() != null) {
//            System.out.println("Case1:: startDate is "+this.getStartDate().toString()+" and the exp date is "+ this.getExpirationDate().toString());
          if (!compatiblePriceBounds(db, activePrice)) {
            return false;
          }
        } else if (this.getStartDate() == null && this.getExpirationDate() != null) {
//            System.out.println("Case2:: the exp date is "+ this.getExpirationDate().toString());
          if (!compatiblePriceExpirationBound(db, activePrice)) {
            return false;
          }
        } else if (this.getStartDate() != null && this.getExpirationDate() == null) {
//            System.out.println("Case3:: startDate is "+this.getStartDate().toString());
          if (!compatiblePriceStartBound(db, activePrice)) {
            return false;
          }
        } else {
//            System.out.println("Case4:: hence false");
          // Open bounded price can not be inserted when active prices already exist
          return false;
        }
      }
    }
    return true;
  }


  /**
   *  Check price compatibility with a given ProductCatalogPricing when the
   *  start and expiration dates of this are not null
   *
   *@param  db                Description of the Parameter
   *@param  price             Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean compatiblePriceBounds(Connection db, ProductCatalogPricing price) throws SQLException {
//    System.out.println("Compatible price bounds method..");
    if (price.getStartDate() != null && price.getExpirationDate() != null) {
//      System.out.println("Trial 1:: active price exp date is "+price.getStartDate().toString()+" and current exp date is "+this.getStartDate().toString());
      if (price.getStartDate().before(this.getStartDate()) && price.getExpirationDate().after(this.getStartDate())) {
        return false;
      }
//      System.out.println("Trial 1:: active price exp date is "+price.getExpirationDate().toString()+" and current exp date is "+this.getExpirationDate().toString());
      if (price.getStartDate().before(this.getExpirationDate()) && price.getExpirationDate().after(this.getExpirationDate())) {
        return false;
      }
    } else if (price.getStartDate() == null && price.getExpirationDate() != null) {
//      System.out.println("Trial 2:: active price exp is "+price.getExpirationDate().toString()+" and current price st date is "+this.getStartDate().toString());
      if (price.getExpirationDate().after(this.getStartDate())) {
        return false;
      }
//      System.out.println("Trial 2:: active price exp date is "+price.getExpirationDate().toString()+" and current exp date is "+this.getExpirationDate().toString());
      if (price.getExpirationDate().after(this.getExpirationDate())) {
        return false;
      }
    } else if (price.getStartDate() != null && price.getExpirationDate() == null) {
//      System.out.println("Trial 3:: active price start date is "+ price.getStartDate().toString()+" and the current start date is "+ this.getStartDate().toString());
      if (price.getStartDate().before(this.getStartDate())) {
        return false;
      }
      if (price.getStartDate().before(this.getExpirationDate())) {
        return false;
      }
    } else {
//      System.out.println("Trial 4:: hence false");
      return false;
    }
    return true;
  }


  /**
   *  Check price compatibility when the start date of the current price is null
   *
   *@param  db                Description of the Parameter
   *@param  price             Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean compatiblePriceExpirationBound(Connection db, ProductCatalogPricing price) throws SQLException {
    if (price.getStartDate() != null && price.getExpirationDate() != null) {
//      System.out.println("Trial 1:: active price start data is "+price.getStartDate().toString()+" and current price exp date is "+this.getExpirationDate().toString());
      if (price.getStartDate().before(this.getExpirationDate())) {
        return false;
      }
    } else if (price.getStartDate() == null && price.getExpirationDate() != null) {
//      System.out.println("Trial 2:: hence false");
      return false;
    } else if (price.getStartDate() != null && price.getExpirationDate() == null) {
//      System.out.println("Trial 3:: active price start date is "+price.getStartDate().toString()+" and the current exp date is "+this.getExpirationDate().toString());
      if (price.getStartDate().before(this.getExpirationDate())) {
        return false;
      }
    } else {
//      System.out.println("Trial 4:: hence false ");
      return false;
    }
    return true;
  }


  /**
   *  Check price compatibility when the expiration date of the current price is
   *  null
   *
   *@param  db                Description of the Parameter
   *@param  price             Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean compatiblePriceStartBound(Connection db, ProductCatalogPricing price) throws SQLException {
    if (price.getStartDate() != null && price.getExpirationDate() != null) {
//      System.out.println("Trial 1:: active price exp date is "+price.getExpirationDate().toString()+" and the start date is "+this.getStartDate().toString());
      if (price.getExpirationDate().after(this.getStartDate())) {
        return false;
      }
    } else if (price.getStartDate() == null && price.getExpirationDate() != null) {
//      System.out.println("Trial 2:: active price exp date is "+ price.getExpirationDate().toString()+" and the current price start date is "+ this.getStartDate().toString());
      if (price.getExpirationDate().after(this.getStartDate())) {
        return false;
      }
    } else if (price.getStartDate() != null && price.getExpirationDate() == null) {
//      System.out.println("Trial 3:: hence false");
      return false;
    } else {
//      System.out.println("Trial 4:: hence false");
      return false;
    }
    return true;
  }

}

