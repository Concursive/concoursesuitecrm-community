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
 *  A generic Product Catalog belongs to a Product Category and contains several
 *  Product Options
 *
 * @author     partha
 * @created    March 18, 2004
 * @version    $Id: ProductCatalog.java,v 1.1.2.1 2004/03/18 22:11:33 partha Exp
 *      $
 */
public class ProductCatalog extends GenericBean {
  private int id = -1;
  private int parentId = -1;
  private int typeId = -1;
  private int formatId = -1;
  private int shippingId = -1;
  private int estimatedShipTime = -1;

  private int thumbnailImageId = -1;
  private int smallImageId = -1;
  private int largeImageId = -1;
  private int listOrder = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private String name = null;
  private String abbreviation = null;
  private String shortDescription = null;
  private String longDescription = null;
  private String specialNotes = null;
  private String sku = null;

  private boolean enabled = true;
  private boolean inStock = true;
  private Timestamp entered = null;
  private Timestamp modified = null;
  private Timestamp startDate = null;
  private Timestamp expirationDate = null;

  //other supplimentary data
  private String categoryName = null;
  private double msrpAmount = 0.0;
  private double priceAmount = 0.0;
  private double recurringAmount = 0.0;
  private Timestamp priceStartDate = null;
  private Timestamp priceEndDate = null;
  private String parentName = null;
  private String typeName = null;

  // resources
  private boolean buildProductOptions = true;
  private ProductOptionList productOptionList = null;


  /**
   *  Sets the productOptionList attribute of the ProductCatalog object
   *
   * @param  tmp  The new productOptionList value
   */
  public void setProductOptionList(ProductOptionList tmp) {
    this.productOptionList = tmp;
  }


  /**
   *  Gets the productOptionList attribute of the ProductCatalog object
   *
   * @return    The productOptionList value
   */
  public ProductOptionList getProductOptionList() {
    return productOptionList;
  }


  /**
   *  Sets the buildProductOptions attribute of the ProductCatalog object
   *
   * @param  tmp  The new buildProductOptions value
   */
  public void setBuildProductOptions(boolean tmp) {
    this.buildProductOptions = tmp;
  }


  /**
   *  Sets the buildProductOptions attribute of the ProductCatalog object
   *
   * @param  tmp  The new buildProductOptions value
   */
  public void setBuildProductOptions(String tmp) {
    this.buildProductOptions = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildProductOptions attribute of the ProductCatalog object
   *
   * @return    The buildProductOptions value
   */
  public boolean getBuildProductOptions() {
    return buildProductOptions;
  }


  /**
   *  Gets the id attribute of the ProductCatalog object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the parentId attribute of the ProductCatalog object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the typeId attribute of the ProductCatalog object
   *
   * @return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the formatId attribute of the ProductCatalog object
   *
   * @return    The formatId value
   */
  public int getFormatId() {
    return formatId;
  }


  /**
   *  Gets the shippingId attribute of the ProductCatalog object
   *
   * @return    The shippingId value
   */
  public int getShippingId() {
    return shippingId;
  }


  /**
   *  Gets the estimatedShipTime attribute of the ProductCatalog object
   *
   * @return    The estimatedShipTime value
   */
  public int getEstimatedShipTime() {
    return estimatedShipTime;
  }


  /**
   *  Gets the thumbnailImageId attribute of the ProductCatalog object
   *
   * @return    The thumbnailImageId value
   */
  public int getThumbnailImageId() {
    return thumbnailImageId;
  }


  /**
   *  Gets the smallImageId attribute of the ProductCatalog object
   *
   * @return    The smallImageId value
   */
  public int getSmallImageId() {
    return smallImageId;
  }


  /**
   *  Gets the largeImageId attribute of the ProductCatalog object
   *
   * @return    The largeImageId value
   */
  public int getLargeImageId() {
    return largeImageId;
  }


  /**
   *  Gets the listOrder attribute of the ProductCatalog object
   *
   * @return    The listOrder value
   */
  public int getListOrder() {
    return listOrder;
  }


  /**
   *  Gets the enteredBy attribute of the ProductCatalog object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the ProductCatalog object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the name attribute of the ProductCatalog object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the abbreviation attribute of the ProductCatalog object
   *
   * @return    The abbreviation value
   */
  public String getAbbreviation() {
    return abbreviation;
  }


  /**
   *  Gets the shortDescription attribute of the ProductCatalog object
   *
   * @return    The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   *  Gets the longDescription attribute of the ProductCatalog object
   *
   * @return    The longDescription value
   */
  public String getLongDescription() {
    return longDescription;
  }


  /**
   *  Gets the specialNotes attribute of the ProductCatalog object
   *
   * @return    The specialNotes value
   */
  public String getSpecialNotes() {
    return specialNotes;
  }


  /**
   *  Gets the sku attribute of the ProductCatalog object
   *
   * @return    The sku value
   */
  public String getSku() {
    return sku;
  }


  /**
   *  Gets the enabled attribute of the ProductCatalog object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the inStock attribute of the ProductCatalog object
   *
   * @return    The inStock value
   */
  public boolean getInStock() {
    return inStock;
  }


  /**
   *  Gets the entered attribute of the ProductCatalog object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the ProductCatalog object
   *
   * @return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the startDate attribute of the ProductCatalog object
   *
   * @return    The startDate value
   */
  public Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Gets the expirationDate attribute of the ProductCatalog object
   *
   * @return    The expirationDate value
   */
  public Timestamp getExpirationDate() {
    return expirationDate;
  }


  /**
   *  Gets the categoryName attribute of the ProductCatalog object
   *
   * @return    The categoryName value
   */
  public String getCategoryName() {
    return categoryName;
  }


  /**
   *  Gets the msrpAmount attribute of the ProductCatalog object
   *
   * @return    The msrpAmount value
   */
  public double getMsrpAmount() {
    return msrpAmount;
  }


  /**
   *  Gets the priceAmount attribute of the ProductCatalog object
   *
   * @return    The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   *  Gets the recurringAmount attribute of the ProductCatalog object
   *
   * @return    The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   *  Gets the priceStartDate attribute of the ProductCatalog object
   *
   * @return    The priceStartDate value
   */
  public Timestamp getPriceStartDate() {
    return priceStartDate;
  }


  /**
   *  Gets the priceEndDate attribute of the ProductCatalog object
   *
   * @return    The priceEndDate value
   */
  public Timestamp getPriceEndDate() {
    return priceEndDate;
  }


  /**
   *  Gets the parentName attribute of the ProductCatalog object
   *
   * @return    The parentName value
   */
  public String getParentName() {
    return parentName;
  }


  /**
   *  Gets the typeName attribute of the ProductCatalog object
   *
   * @return    The typeName value
   */
  public String getTypeName() {
    return typeName;
  }


  /**
   *  Sets the id attribute of the ProductCatalog object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ProductCatalog object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentId attribute of the ProductCatalog object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the ProductCatalog object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the typeId attribute of the ProductCatalog object
   *
   * @param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the ProductCatalog object
   *
   * @param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the formatId attribute of the ProductCatalog object
   *
   * @param  tmp  The new formatId value
   */
  public void setFormatId(int tmp) {
    this.formatId = tmp;
  }


  /**
   *  Sets the formatId attribute of the ProductCatalog object
   *
   * @param  tmp  The new formatId value
   */
  public void setFormatId(String tmp) {
    this.formatId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the shippingId attribute of the ProductCatalog object
   *
   * @param  tmp  The new shippingId value
   */
  public void setShippingId(int tmp) {
    this.shippingId = tmp;
  }


  /**
   *  Sets the shippingId attribute of the ProductCatalog object
   *
   * @param  tmp  The new shippingId value
   */
  public void setShippingId(String tmp) {
    this.shippingId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the estimatedShipTime attribute of the ProductCatalog object
   *
   * @param  tmp  The new estimatedShipTime value
   */
  public void setEstimatedShipTime(int tmp) {
    this.estimatedShipTime = tmp;
  }


  /**
   *  Sets the estimatedShipTime attribute of the ProductCatalog object
   *
   * @param  tmp  The new estimatedShipTime value
   */
  public void setEstimatedShipTime(String tmp) {
    this.estimatedShipTime = Integer.parseInt(tmp);
  }


  /**
   *  Sets the thumbnailImageId attribute of the ProductCatalog object
   *
   * @param  tmp  The new thumbnailImageId value
   */
  public void setThumbnailImageId(int tmp) {
    this.thumbnailImageId = tmp;
  }


  /**
   *  Sets the thumbnailImageId attribute of the ProductCatalog object
   *
   * @param  tmp  The new thumbnailImageId value
   */
  public void setThumbnailImageId(String tmp) {
    this.thumbnailImageId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the smallImageId attribute of the ProductCatalog object
   *
   * @param  tmp  The new smallImageId value
   */
  public void setSmallImageId(int tmp) {
    this.smallImageId = tmp;
  }


  /**
   *  Sets the smallImageId attribute of the ProductCatalog object
   *
   * @param  tmp  The new smallImageId value
   */
  public void setSmallImageId(String tmp) {
    this.smallImageId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the largeImageId attribute of the ProductCatalog object
   *
   * @param  tmp  The new largeImageId value
   */
  public void setLargeImageId(int tmp) {
    this.largeImageId = tmp;
  }


  /**
   *  Sets the largeImageId attribute of the ProductCatalog object
   *
   * @param  tmp  The new largeImageId value
   */
  public void setLargeImageId(String tmp) {
    this.largeImageId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the listOrder attribute of the ProductCatalog object
   *
   * @param  tmp  The new listOrder value
   */
  public void setListOrder(int tmp) {
    this.listOrder = tmp;
  }


  /**
   *  Sets the listOrder attribute of the ProductCatalog object
   *
   * @param  tmp  The new listOrder value
   */
  public void setListOrder(String tmp) {
    this.listOrder = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the ProductCatalog object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ProductCatalog object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the ProductCatalog object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ProductCatalog object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the ProductCatalog object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the abbreviation attribute of the ProductCatalog object
   *
   * @param  tmp  The new abbreviation value
   */
  public void setAbbreviation(String tmp) {
    this.abbreviation = tmp;
  }


  /**
   *  Sets the shortDescription attribute of the ProductCatalog object
   *
   * @param  tmp  The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   *  Sets the longDescription attribute of the ProductCatalog object
   *
   * @param  tmp  The new longDescription value
   */
  public void setLongDescription(String tmp) {
    this.longDescription = tmp;
  }


  /**
   *  Sets the specialNotes attribute of the ProductCatalog object
   *
   * @param  tmp  The new specialNotes value
   */
  public void setSpecialNotes(String tmp) {
    this.specialNotes = tmp;
  }


  /**
   *  Sets the sku attribute of the ProductCatalog object
   *
   * @param  tmp  The new sku value
   */
  public void setSku(String tmp) {
    this.sku = tmp;
  }


  /**
   *  Sets the enabled attribute of the ProductCatalog object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ProductCatalog object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the inStock attribute of the ProductCatalog object
   *
   * @param  tmp  The new inStock value
   */
  public void setInStock(boolean tmp) {
    this.inStock = tmp;
  }


  /**
   *  Sets the inStock attribute of the ProductCatalog object
   *
   * @param  tmp  The new inStock value
   */
  public void setInStock(String tmp) {
    this.inStock = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the entered attribute of the ProductCatalog object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the ProductCatalog object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the startDate attribute of the ProductCatalog object
   *
   * @param  tmp  The new startDate value
   */
  public void setStartDate(Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the expirationDate attribute of the ProductCatalog object
   *
   * @param  tmp  The new expirationDate value
   */
  public void setExpirationDate(Timestamp tmp) {
    this.expirationDate = tmp;
  }


  /**
   *  Sets the categoryName attribute of the ProductCatalog object
   *
   * @param  tmp  The new categoryName value
   */
  public void setCategoryName(String tmp) {
    this.categoryName = tmp;
  }


  /**
   *  Sets the msrpAmount attribute of the ProductCatalog object
   *
   * @param  tmp  The new msrpAmount value
   */
  public void setMsrpAmount(double tmp) {
    this.msrpAmount = tmp;
  }


  /**
   *  Sets the priceAmount attribute of the ProductCatalog object
   *
   * @param  tmp  The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   *  Sets the recurringAmount attribute of the ProductCatalog object
   *
   * @param  tmp  The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   *  Sets the priceStartDate attribute of the ProductCatalog object
   *
   * @param  tmp  The new priceStartDate value
   */
  public void setPriceStartDate(Timestamp tmp) {
    this.priceStartDate = tmp;
  }


  /**
   *  Sets the priceStartDate attribute of the ProductCatalog object
   *
   * @param  tmp  The new priceStartDate value
   */
  public void setPriceStartDate(String tmp) {
    this.priceStartDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the priceEndDate attribute of the ProductCatalog object
   *
   * @param  tmp  The new priceEndDate value
   */
  public void setPriceEndDate(Timestamp tmp) {
    this.priceEndDate = tmp;
  }


  /**
   *  Sets the priceEndDate attribute of the ProductCatalog object
   *
   * @param  tmp  The new priceEndDate value
   */
  public void setPriceEndDate(String tmp) {
    this.priceEndDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the parentName attribute of the ProductCatalog object
   *
   * @param  tmp  The new parentName value
   */
  public void setParentName(String tmp) {
    this.parentName = tmp;
  }


  /**
   *  Sets the typeName attribute of the ProductCatalog object
   *
   * @param  tmp  The new typeName value
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }


  /**
   *  Constructor for the ProductCatalog object
   */
  public ProductCatalog() { }


  /**
   *  Constructor for the ProductCatalog object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ProductCatalog(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the ProductCatalog object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ProductCatalog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Product Catalog Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT " +
        " pctlg.*, " +
        " pctgy.category_name AS category_name, " +
        " pctlgpricing.msrp_amount AS msrp_amount, " +
        " pctlgpricing.price_amount AS price_amount, " +
        " pctlgpricing.recurring_amount AS recurring_amount, " +
        " pctlgpricing.start_date AS price_start_date, " +
        " pctlgpricing.expiration_date AS price_end_date, " +
        " pctlg2.product_name AS parent_name, " +
        " pctlgtype.description AS type_name " +
        " FROM product_catalog AS pctlg " +
        " LEFT JOIN product_catalog_category_map AS pccmap " +
        " ON ( pctlg.product_id = pccmap.product_id ) " +
        " LEFT JOIN product_category AS pctgy " +
        " ON ( pccmap.category_id =  pctgy.category_id ) " +
        " LEFT JOIN product_catalog_pricing AS pctlgpricing " +
        " ON ( pctlg.product_id = pctlgpricing.product_id ) " +
        " LEFT JOIN product_catalog AS pctlg2 " +
        " ON ( pctlg.parent_id = pctlg2.product_id ) " +
        " LEFT JOIN lookup_product_type AS pctlgtype " +
        " ON ( pctlg.type_id = pctlgtype.code ) " +
        " WHERE pctlg.product_id = ? "
        );
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Product Catalog not found");
    }
    // build resources
    if (buildProductOptions) {
      this.buildProductOptions(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildProductOptions(Connection db) throws SQLException {
    productOptionList = new ProductOptionList();
    productOptionList.setProductId(this.id);
    productOptionList.setBuildResources(true);
    productOptionList.buildList(db);
  }


  /**
   *  Adds a feature to the Category attribute of the ProductCatalog object
   *
   * @param  db                The feature to be added to the Category attribute
   * @param  category_id       The feature to be added to the Category attribute
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int addCategory(Connection db, int categoryId) throws SQLException {
    int result = -1;
    int i = 0;
    if (categoryId == -1 || this.getId() == -1) {
      throw new SQLException("Invalid catalog id or category id ");
    }
    PreparedStatement pst = db.prepareStatement(
        " INSERT INTO product_catalog_category_map( product_id, category_id) " +
        " VALUES ( ? , ? ); "
        );
    pst.setInt(++i, this.getId());
    pst.setInt(++i, categoryId);
    result = pst.executeUpdate();
    pst.close();
    return result;
  }


  /**
   *  Adds a feature to the Option attribute of the ProductCatalog object
   *
   * @param  db                The feature to be added to the Option attribute
   * @param  option_id         The feature to be added to the Option attribute
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int addOption(Connection db, int optionId) throws SQLException {
    int result = -1;
    int i = 0;
    if (optionId == -1 || this.getId() == -1) {
      throw new SQLException("Invalid catalog id or option id ");
    }
    PreparedStatement pst = db.prepareStatement(
        " INSERT INTO product_option_map( product_id, option_id , value_id) " +
        " VALUES ( ? , ? , 0 ); "
        );
    pst.setInt(++i, this.getId());
    pst.setInt(++i, optionId);
    result = pst.executeUpdate();
    pst.close();
    return result;
  }


  /**
   *  Adds a feature to the Option attribute of the ProductCatalog object
   *
   * @param  db                The feature to be added to the Option attribute
   * @param  option_id         The feature to be added to the Option attribute
   * @param  value_id          The feature to be added to the Option attribute
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int addOption(Connection db, int optionId, int valueId) throws SQLException {
    int result = -1;
    int i = 0;
    if (optionId == -1 || this.getId() == -1 || valueId == -1) {
      throw new SQLException("Invalid catalog id or option id or value_id");
    }
    PreparedStatement pst = db.prepareStatement(
        " INSERT INTO product_option_map( product_id, option_id, value_id ) " +
        " VALUES ( ? , ? , ? ); "
        );
    pst.setInt(++i, this.getId());
    pst.setInt(++i, optionId);
    pst.setInt(++i, valueId);
    result = pst.executeUpdate();
    pst.close();
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    //product_catalog table
    this.setId(rs.getInt("product_id"));
    this.setParentId(DatabaseUtils.getInt(rs, "parent_id"));
    this.setName(rs.getString("product_name"));
    this.setAbbreviation(rs.getString("abbreviation"));
    this.setShortDescription(rs.getString("short_description"));
    this.setLongDescription(rs.getString("long_description"));
    this.setTypeId(DatabaseUtils.getInt(rs, "type_id"));
    this.setSpecialNotes(rs.getString("special_notes"));
    this.setSku(rs.getString("sku"));
    this.setInStock(rs.getBoolean("in_stock"));
    this.setFormatId(DatabaseUtils.getInt(rs, "format_id"));
    this.setShippingId(DatabaseUtils.getInt(rs, "shipping_id"));
    this.setEstimatedShipTime(DatabaseUtils.getInt(rs, "estimated_ship_time"));
    this.setThumbnailImageId(DatabaseUtils.getInt(rs, "thumbnail_image_id"));
    this.setSmallImageId(DatabaseUtils.getInt(rs, "small_image_id"));
    this.setLargeImageId(DatabaseUtils.getInt(rs, "large_image_id"));
    this.setListOrder(DatabaseUtils.getInt(rs, "list_order"));
    this.setEnteredBy(rs.getInt("enteredby"));
    this.setEntered(rs.getTimestamp("entered"));
    this.setModifiedBy(rs.getInt("modifiedby"));
    this.setModified(rs.getTimestamp("modified"));
    this.setStartDate(rs.getTimestamp("start_date"));
    this.setExpirationDate(rs.getTimestamp("expiration_date"));
    this.setEnabled(rs.getBoolean("enabled"));

    // product_category table
    this.setCategoryName(rs.getString("category_name"));

    // product_catalog_pricing table
    this.setMsrpAmount(rs.getFloat("msrp_amount"));
    this.setPriceAmount(rs.getFloat("price_amount"));
    this.setRecurringAmount(rs.getFloat("recurring_amount"));
    this.setPriceStartDate(rs.getTimestamp("price_start_date"));
    this.setPriceEndDate(rs.getTimestamp("price_end_date"));

    // product_catalog parent table
    this.setParentName(rs.getString("parent_name"));

    // product_catalog_type table
    this.setTypeName(rs.getString("type_name"));
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  baseFilePath      Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    boolean result = false;
    if (this.getId() == -1) {
      throw new SQLException("Product Catalog ID not specified.");
    }
    try {
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
      //delete all the dependencies
      //delete all records that contain product_id in the product_catalog_category_mapping
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          " DELETE from product_catalog_category_map " +
          " WHERE product_id = ? "
          );
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      //delete all the records that contain product_id in the product_catalog_option_map
      i = 0;
      pst = db.prepareStatement(
          " DELETE from product_option_map " +
          " WHERE product_id = ? "
          );
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      //delete the product from the catalog
      i = 0;
      pst = db.prepareStatement(
          " DELETE from product_catalog " +
          " WHERE product_id = ? "
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
   *  Gets the timeZoneParams attribute of the ProductCatalog class
   *
   * @return    The timeZoneParams value
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
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " INSERT INTO product_catalog( parent_id, " +
        " product_name, abbreviation, short_description, " +
        " long_description, type_id, sku, in_stock, format_id, " +
        " shipping_id, estimated_ship_time , thumbnail_image_id, " +
        " small_image_id, large_image_id, list_order, " +
        " enteredby,"
        );
    if (entered != null) {
      sql.append(" entered, ");
    }
    sql.append(" modifiedBy, ");
    if (modified != null) {
      sql.append(" modified, ");
    }
    sql.append(" start_date,expiration_date,enabled)");
    sql.append("VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
    if (entered != null) {
      sql.append("?, ");
    }
    sql.append("?, ");
    if (modified != null) {
      sql.append(" ?, ");
    }
    sql.append("?,?,?)");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getAbbreviation());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getLongDescription());
    DatabaseUtils.setInt(pst, ++i, this.getTypeId());
    pst.setString(++i, this.getSku());
    pst.setBoolean(++i, this.getInStock());
    DatabaseUtils.setInt(pst, ++i, this.getFormatId());
    DatabaseUtils.setInt(pst, ++i, this.getShippingId());
    DatabaseUtils.setInt(pst, ++i, this.getEstimatedShipTime());
    DatabaseUtils.setInt(pst, ++i, this.getThumbnailImageId());
    DatabaseUtils.setInt(pst, ++i, this.getSmallImageId());
    DatabaseUtils.setInt(pst, ++i, this.getLargeImageId());
    DatabaseUtils.setInt(pst, ++i, this.getListOrder());
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
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "product_catalog_product_id_seq");
    result = true;
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (!isValid(db)) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " UPDATE product_catalog SET parent_id = ?, abbreviation = ?, " +
        " short_description = ?, long_description = ?, " +
        " special_notes = ?, sku = ?, in_stock = ?, format_id = ?, " +
        " shipping_id = ?, estimated_ship_time = ?, thumbnail_image_id = ?," +
        " small_image_id = ?, large_image_id = ?, list_order = ?,"
        );
    sql.append(" modifiedBy = ? , modified = " + DatabaseUtils.getCurrentTimestamp(db));
    sql.append(" , start_date = ?, expiration_date = ?, enabled = ? ");
    sql.append(" WHERE product_id = ? ");
    sql.append(" AND modified = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    pst.setString(++i, this.getAbbreviation());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getLongDescription());
    pst.setString(++i, this.getSpecialNotes());
    pst.setString(++i, this.getSku());
    pst.setBoolean(++i, this.getInStock());
    DatabaseUtils.setInt(pst, ++i, this.getFormatId());
    DatabaseUtils.setInt(pst, ++i, this.getShippingId());
    DatabaseUtils.setInt(pst, ++i, this.getEstimatedShipTime());
    DatabaseUtils.setInt(pst, ++i, this.getThumbnailImageId());
    DatabaseUtils.setInt(pst, ++i, this.getSmallImageId());
    DatabaseUtils.setInt(pst, ++i, this.getLargeImageId());
    DatabaseUtils.setInt(pst, ++i, this.getListOrder());
    DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getId());
    pst.setTimestamp(++i, this.getModified());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    // This method checks all the mappings for any product_catalog with the current id
    // The currently known mappings are product_catalog_option_map, product_catalog_category_map
    if (this.getId() == -1) {
      throw new SQLException("Product Catalog ID not specified");
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
    //Check for product_catalog with parent_id = id
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as parentcount " +
          " FROM product_catalog " +
          "WHERE parent_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int catalogCount = rs.getInt("parentcount");
        if (catalogCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Number of children of this catalog ");
          thisDependency.setCount(catalogCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    }

    //Check for the current product mappings in product_option_map
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as catalogcount " +
          " FROM product_option_map " +
          "WHERE product_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int catalogCount = rs.getInt("catalogcount");
        if (catalogCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Number of catalog_option mappings are ");
          thisDependency.setCount(catalogCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    }

    //Check for the current product mappings in product_catalog_category
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as catalogcount " +
          " FROM product_catalog_category_map " +
          "WHERE product_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int catalogCount = rs.getInt("catalogcount");
        if (catalogCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Number of catalog_category mappings are ");
          thisDependency.setCount(catalogCount);
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
   *  Gets the valid attribute of the ProductCatalog object
   *
   * @param  db                Description of the Parameter
   * @return                   The valid value
   * @exception  SQLException  Description of the Exception
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
   * @param  db                Description of the Parameter
   * @param  dimensions        Description of the Parameter
   * @param  categoryId        Description of the Parameter
   * @param  productName       Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static boolean lookupId(Connection db, String productName, String dimensions, int categoryId) throws SQLException {
    boolean result = false;
    try {
      int i = 0;
      StringBuffer sql = new StringBuffer(
          " SELECT count(*) AS counter " +
          " FROM product_catalog AS pctlg " +
          " LEFT JOIN product_catalog_category_map AS pctlgmap " +
          " ON ( pctlg.product_id = pctlgmap.product_id ) " +
          " LEFT JOIN product_category AS pctgy " +
          " ON ( pctlgmap.category_id = pctgy.category_id ) " +
          " WHERE pctgy.category_id = ? "
          );
      if (dimensions != null) {
        sql.append(" AND short_description = ? ");
      } else {
        sql.append(" AND pctlg.product_name = ? ");
      }
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, categoryId);
      if (dimensions != null) {
        pst.setString(++i, dimensions);
      } else {
        pst.setString(++i, productName);
      }
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int buffer = rs.getInt("counter");
        if (buffer != 0) {
          result = true;
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    }
    return result;
  }
}

