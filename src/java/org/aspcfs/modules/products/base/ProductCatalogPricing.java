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
  //other supplimentary fields
  private String productName = null;


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
    if (this.getId() == -1) {
      throw new SQLException("Invalid Product Catalog Pricing ID");
    }
    PreparedStatement pst = db.prepareStatement(
        " SELECT " +
        " pctlgprice.* , pctlg.product_name AS product_name" +
        " FROM product_catalog_pricing pctlgprice, " +
        " LEFT JOIN product_catalog AS pctlg " +
        " ON ( pctlgprice.product_id = pctlg.product_id ) " +
        " WHERE pctlgprice.price_id = ? "
        );
    pst.setInt(1, this.getId());
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
    //product_catalog
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
      throw new SQLException("Product Catalog Pricing ID invalid");
    }
    int i = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = null;
      /*
       *  /Delete any documents
       *  FileItemList fileList = new FileItemList();
       *  fileList.setLinkModuleId(Constants.DOCUMENTS_PRODUCT_CATALOG_PRICING);
       *  fileList.setLinkItemId(this.getId());
       *  fileList.buildList(db);
       *  fileList.delete(db, baseFilePath);
       *  fileList = null;
       *  /Delete any folder data
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
    boolean result = false;
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
    sql.append(" start_date, expiration_date) ");
    sql.append(" VALUES ( ?,?,?,?,?,?,?,?,?,?, ");
    if (entered != null) {
      sql.append(" ?, ");
    }
    sql.append(" ?, ");
    if (modified != null) {
      sql.append(" ?, ");
    }
    sql.append("?,?)");
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
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "product_catalog_pricing_price_id_seq");
    result = true;
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
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " UPDATE product_catalog_pricing SET " +
        " product_id = ?, tax_id = ?, msrp_currency = ?, " +
        " msrp_amount = ?, price_currency = ?, price_amount = ?, " +
        " recurring_currency = ?, recurring_amount = ?, " +
        " recurring_type = ?, enteredby = ?, entered = ?, " +
        " modifiedby = ?, modified = ?, start_date = ?, expiration_date = ?, " +
        " WHERE price_id = ? AND modified = ? "
        );
    int i = 0;
    pst = db.prepareStatement(sql.toString());
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
    DatabaseUtils.setTimestamp(pst, ++i, this.getEntered());
    DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
    DatabaseUtils.setTimestamp(pst, ++i, this.getModified());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
    DatabaseUtils.setInt(pst, ++i, this.getId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getModified());
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
    String sql = null;
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    /*
     *  /Check for documents
     *  Dependency docDependency = new Dependency();
     *  docDependency.setName("Documents");
     *  docDependency.setCount(FileItemList.retrieveRecordCount(db, Constants.DOCUMENTS_PRODUCT_CATALOG, this.getId()));
     *  docDependency.setCanDelete(true);
     *  dependencyList.add(docDependency);
     *  /Check for folders
     *  Dependency folderDependency = new Dependency();
     *  folderDependency.setName("Folders");
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
          thisDependency.setName("Number of products that have only this price ");
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
   *  Gets the valid attribute of the ProductCatalogPricing object
   *
   *@param  db                Description of the Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  public boolean isValid(Connection db) throws SQLException {
    if (id == -1) {
      return false;
    }
    return true;
  }
}

