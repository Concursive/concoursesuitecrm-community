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
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Import;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * This is a generic Product Category that contains Product Catalogs.
 *
 * @author partha
 * @version $Id$
 * @created March 18, 2004
 */
public class ProductCategory extends GenericBean {

  private int id = -1;
  private int parentId = -1;
  private String name = null;
  private String abbreviation = null;
  private String shortDescription = null;
  private String longDescription = null;
  private int typeId = -1;
  private int thumbnailImageId = -1;
  private int smallImageId = -1;
  private int largeImageId = -1;
  private int listOrder = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private Timestamp entered = null;
  private Timestamp modified = null;
  private Timestamp startDate = null;
  private Timestamp expirationDate = null;
  private boolean enabled = false;

  // other supplimentary fields
  private String parentName = null;
  private String typeName = null;
  private boolean buildChildList = false;
  private boolean buildChildCount = false;
  private boolean buildProductList = false;
  private int buildEnabledProducts = Constants.UNDEFINED;
  private int buildActiveProducts = Constants.UNDEFINED;
  private ProductCategoryList childList = new ProductCategoryList();
  private ProductCatalogList productList = new ProductCatalogList();
  private boolean buildCompleteHierarchy = false;
  private boolean buildActivePrice = false;
  private int statusId = -1;
  private int importId = -1;
  private ProductCategoryList fullPath = null;
  private int childCount = -1;

  // hierarchy builder helper
  private int level = -1;

  //Logger
  private long milies = -1;
  private static Logger logger = Logger.getLogger(ProductCategory.class);

  static{
    if(System.getProperty("DEBUG")!= null){
      logger.setLevel(Level.DEBUG);
    }
  }
  
  /**
   * Gets the buildChildCount attribute of the ProductCategory object
   *
   * @return buildChildCount The buildChildCount value
   */
  public boolean getBuildChildCount() {
    return this.buildChildCount;
  }

  /**
   * Sets the buildChildCount attribute of the ProductCategory object
   *
   * @param buildChildCount The new buildChildCount value
   */
  public void setBuildChildCount(boolean buildChildCount) {
    this.buildChildCount = buildChildCount;
  }

  /**
   * Sets the buildChildCount attribute of the ProductCategory object
   *
   * @param buildChildCount The new buildChildCount value
   */
  public void setBuildChildCount(String buildChildCount) {
    this.buildChildCount = DatabaseUtils.parseBoolean(buildChildCount);
  }

  /**
   * Gets the childCount attribute of the ProductCategory object
   *
   * @return childCount The childCount value
   */
  public int getChildCount() {
    return this.childCount;
  }

  /**
   * Sets the childCount attribute of the ProductCategory object
   *
   * @param childCount The new childCount value
   */
  public void setChildCount(int childCount) {
    this.childCount = childCount;
  }

  /**
   * Sets the childCount attribute of the ProductCategory object
   *
   * @param childCount The new childCount value
   */
  public void setChildCount(String childCount) {
    this.childCount = Integer.parseInt(childCount);
  }

  /**
   * Gets the fullPath attribute of the ProductCategory object
   *
   * @return fullPath The fullPath value
   */
  public ProductCategoryList getFullPath() {
    return fullPath;
  }

  /**
   * Sets the fullPath attribute of the ProductCategory object
   *
   * @param fullPath The new fullPath value
   */
  public void setFullPath(ProductCategoryList fullPath) {
    this.fullPath = fullPath;
  }

  /**
   * Gets the importId attribute of the ProductCategory object
   *
   * @return importId The importId value
   */
  public int getImportId() {
    return importId;
  }

  /**
   * Sets the importId attribute of the ProductCategory object
   *
   * @param importId The new importId value
   */
  public void setImportId(int importId) {
    this.importId = importId;
  }

  /**
   * Sets the importId attribute of the ProductCategory object
   *
   * @param importId The new importId value
   */
  public void setImportId(String importId) {
    this.importId = Integer.parseInt(importId);
  }

  /**
   * Gets the statusId attribute of the ProductCategory object
   *
   * @return statusId The statusId value
   */
  public int getStatusId() {
    return statusId;
  }

  /**
   * Sets the statusId attribute of the ProductCategory object
   *
   * @param statusId The new statusId value
   */
  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }

  /**
   * Sets the statusId attribute of the ProductCategory object
   *
   * @param statusId The new statusId value
   */
  public void setStatusId(String statusId) {
    this.statusId = Integer.parseInt(statusId);
  }

  /**
   * Gets the buildActivePrice attribute of the ProductCategory object
   *
   * @return The buildActivePrice value
   */
  public boolean getBuildActivePrice() {
    return buildActivePrice;
  }

  /**
   * Sets the buildActivePrice attribute of the ProductCategory object
   *
   * @param tmp The new buildActivePrice value
   */
  public void setBuildActivePrice(boolean tmp) {
    this.buildActivePrice = tmp;
  }

  /**
   * Sets the buildActivePrice attribute of the ProductCategory object
   *
   * @param tmp The new buildActivePrice value
   */
  public void setBuildActivePrice(String tmp) {
    this.buildActivePrice = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the level attribute of the ProductCategory object
   *
   * @return The level value
   */
  public int getLevel() {
    return level;
  }

  /**
   * Sets the level attribute of the ProductCategory object
   *
   * @param tmp The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }

  /**
   * Sets the level attribute of the ProductCategory object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }

  /**
   * Sets the buildChildList attribute of the ProductCategory object
   *
   * @param tmp The new buildChildList value
   */
  public void setBuildChildList(boolean tmp) {
    this.buildChildList = tmp;
  }

  /**
   * Sets the buildChildList attribute of the ProductCategory object
   *
   * @param tmp The new buildChildList value
   */
  public void setBuildChildList(String tmp) {
    this.buildChildList = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the buildProductList attribute of the ProductCategory object
   *
   * @param tmp The new buildProductList value
   */
  public void setBuildProductList(boolean tmp) {
    this.buildProductList = tmp;
  }

  /**
   * Sets the buildProductList attribute of the ProductCategory object
   *
   * @param tmp The new buildProductList value
   */
  public void setBuildProductList(String tmp) {
    this.buildProductList = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the buildCompleteHierarchy attribute of the ProductCategory object
   *
   * @param tmp The new buildCompleteHierarchy value
   */
  public void setBuildCompleteHierarchy(boolean tmp) {
    this.buildCompleteHierarchy = tmp;
  }

  /**
   * Sets the buildCompleteHierarchy attribute of the ProductCategory object
   *
   * @param tmp The new buildCompleteHierarchy value
   */
  public void setBuildCompleteHierarchy(String tmp) {
    this.buildCompleteHierarchy = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the buildCompleteHierarchy attribute of the ProductCategory object
   *
   * @return The buildCompleteHierarchy value
   */
  public boolean getBuildCompleteHierarchy() {
    return buildCompleteHierarchy;
  }

  /**
   * Gets the buildEnabledProducts attribute of the ProductCategory object
   *
   * @return The buildEnabledProducts value
   */
  public int getBuildEnabledProducts() {
    return buildEnabledProducts;
  }

  /**
   * Sets the buildEnabledProducts attribute of the ProductCategory object
   *
   * @param tmp The new buildEnabledProducts value
   */
  public void setBuildEnabledProducts(int tmp) {
    this.buildEnabledProducts = tmp;
  }

  /**
   * Gets the buildActiveProducts attribute of the ProductCategory object
   *
   * @return The buildActiveProducts value
   */
  public int getBuildActiveProducts() {
    return buildActiveProducts;
  }

  /**
   * Sets the buildActiveProducts attribute of the ProductCategory object
   *
   * @param tmp The new buildActiveProducts value
   */
  public void setBuildActiveProducts(int tmp) {
    this.buildActiveProducts = tmp;
  }

  /**
   * Sets the buildActiveProducts attribute of the ProductCategory object
   *
   * @param tmp The new buildActiveProducts value
   */
  public void setBuildActiveProducts(String tmp) {
    this.buildActiveProducts = Integer.parseInt(tmp);
  }

  /**
   * Gets the buildChildList attribute of the ProductCategory object
   *
   * @return The buildChildList value
   */
  public boolean getBuildChildList() {
    return buildChildList;
  }

  /**
   * Gets the buildProductList attribute of the ProductCategory object
   *
   * @return The buildProductList value
   */
  public boolean getBuildProductList() {
    return buildProductList;
  }

  /**
   * Sets the childList attribute of the ProductCategory object
   *
   * @param tmp The new childList value
   */
  public void setChildList(ProductCategoryList tmp) {
    this.childList = tmp;
  }

  /**
   * Sets the productList attribute of the ProductCategory object
   *
   * @param tmp The new productList value
   */
  public void setProductList(ProductCatalogList tmp) {
    this.productList = tmp;
  }

  /**
   * Gets the childList attribute of the ProductCategory object
   *
   * @return The childList value
   */
  public ProductCategoryList getChildList() {
    return childList;
  }

  /**
   * Gets the productList attribute of the ProductCategory object
   *
   * @return The productList value
   */
  public ProductCatalogList getProductList() {
    return productList;
  }

  /**
   * Gets the id attribute of the ProductCategory object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the parentId attribute of the ProductCategory object
   *
   * @return The parentId value
   */
  public int getParentId() {
    return parentId;
  }

  /**
   * Gets the name attribute of the ProductCategory object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the abbreviation attribute of the ProductCategory object
   *
   * @return The abbreviation value
   */
  public String getAbbreviation() {
    return abbreviation;
  }

  /**
   * Gets the shortDescription attribute of the ProductCategory object
   *
   * @return The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }

  /**
   * Gets the longDescription attribute of the ProductCategory object
   *
   * @return The longDescription value
   */
  public String getLongDescription() {
    return longDescription;
  }

  /**
   * Gets the typeId attribute of the ProductCategory object
   *
   * @return The typeId value
   */
  public int getTypeId() {
    return typeId;
  }

  /**
   * Gets the thumbnailImageId attribute of the ProductCategory object
   *
   * @return The thumbnailImageId value
   */
  public int getThumbnailImageId() {
    return thumbnailImageId;
  }

  /**
   * Gets the smallImageId attribute of the ProductCategory object
   *
   * @return The smallImageId value
   */
  public int getSmallImageId() {
    return smallImageId;
  }

  /**
   * Gets the largeImageId attribute of the ProductCategory object
   *
   * @return The largeImageId value
   */
  public int getLargeImageId() {
    return largeImageId;
  }

  /**
   * Gets the listOrder attribute of the ProductCategory object
   *
   * @return The listOrder value
   */
  public int getListOrder() {
    return listOrder;
  }

  /**
   * Gets the enteredBy attribute of the ProductCategory object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }

  /**
   * Gets the modifiedBy attribute of the ProductCategory object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }

  /**
   * Gets the entered attribute of the ProductCategory object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }

  /**
   * Gets the modified attribute of the ProductCategory object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }

  /**
   * Gets the startDate attribute of the ProductCategory object
   *
   * @return The startDate value
   */
  public Timestamp getStartDate() {
    return startDate;
  }

  /**
   * Gets the expirationDate attribute of the ProductCategory object
   *
   * @return The expirationDate value
   */
  public Timestamp getExpirationDate() {
    return expirationDate;
  }

  /**
   * Gets the enabled attribute of the ProductCategory object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }

  /**
   * Gets the parentName attribute of the ProductCategory object
   *
   * @return The parentName value
   */
  public String getParentName() {
    return parentName;
  }

  /**
   * Sets the id attribute of the ProductCategory object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }

  /**
   * Gets the typeName attribute of the ProductCategory object
   *
   * @return The typeName value
   */
  public String getTypeName() {
    return typeName;
  }

  /**
   * Sets the id attribute of the ProductCategory object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }

  /**
   * Sets the parentId attribute of the ProductCategory object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }

  /**
   * Sets the parentId attribute of the ProductCategory object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }

  /**
   * Sets the name attribute of the ProductCategory object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }

  /**
   * Sets the abbreviation attribute of the ProductCategory object
   *
   * @param tmp The new abbreviation value
   */
  public void setAbbreviation(String tmp) {
    this.abbreviation = tmp;
  }

  /**
   * Sets the shortDescription attribute of the ProductCategory object
   *
   * @param tmp The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }

  /**
   * Sets the longDescription attribute of the ProductCategory object
   *
   * @param tmp The new longDescription value
   */
  public void setLongDescription(String tmp) {
    this.longDescription = tmp;
  }

  /**
   * Sets the typeId attribute of the ProductCategory object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }

  /**
   * Sets the typeId attribute of the ProductCategory object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }

  /**
   * Sets the thumbnailImageId attribute of the ProductCategory object
   *
   * @param tmp The new thumbnailImageId value
   */
  public void setThumbnailImageId(int tmp) {
    this.thumbnailImageId = tmp;
  }

  /**
   * Sets the thumbnailImageId attribute of the ProductCategory object
   *
   * @param tmp The new thumbnailImageId value
   */
  public void setThumbnailImageId(String tmp) {
    this.thumbnailImageId = Integer.parseInt(tmp);
  }

  /**
   * Sets the smallImageId attribute of the ProductCategory object
   *
   * @param tmp The new smallImageId value
   */
  public void setSmallImageId(int tmp) {
    this.smallImageId = tmp;
  }

  /**
   * Sets the smallImageId attribute of the ProductCategory object
   *
   * @param tmp The new smallImageId value
   */
  public void setSmallImageId(String tmp) {
    this.smallImageId = Integer.parseInt(tmp);
  }

  /**
   * Sets the largeImageId attribute of the ProductCategory object
   *
   * @param tmp The new largeImageId value
   */
  public void setLargeImageId(int tmp) {
    this.largeImageId = tmp;
  }

  /**
   * Sets the largeImageId attribute of the ProductCategory object
   *
   * @param tmp The new largeImageId value
   */
  public void setLargeImageId(String tmp) {
    this.largeImageId = Integer.parseInt(tmp);
  }

  /**
   * Sets the listOrder attribute of the ProductCategory object
   *
   * @param tmp The new listOrder value
   */
  public void setListOrder(int tmp) {
    this.listOrder = tmp;
  }

  /**
   * Sets the listOrder attribute of the ProductCategory object
   *
   * @param tmp The new listOrder value
   */
  public void setListOrder(String tmp) {
    this.listOrder = Integer.parseInt(tmp);
  }

  /**
   * Sets the enteredBy attribute of the ProductCategory object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }

  /**
   * Sets the enteredBy attribute of the ProductCategory object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }

  /**
   * Sets the modifiedBy attribute of the ProductCategory object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }

  /**
   * Sets the modifiedBy attribute of the ProductCategory object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }

  /**
   * Sets the entered attribute of the ProductCategory object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }

  /**
   * Sets the entered attribute of the ProductCategory object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Sets the modified attribute of the ProductCategory object
   *
   * @param tmp The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }

  /**
   * Sets the modified attribute of the ProductCategory object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Sets the startDate attribute of the ProductCategory object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(Timestamp tmp) {
    this.startDate = tmp;
  }

  /**
   * Sets the startDate attribute of the ProductCategory object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Sets the expirationDate attribute of the ProductCategory object
   *
   * @param tmp The new expirationDate value
   */
  public void setExpirationDate(Timestamp tmp) {
    this.expirationDate = tmp;
  }

  /**
   * Sets the expirationDate attribute of the ProductCategory object
   *
   * @param tmp The new expirationDate value
   */
  public void setExpirationDate(String tmp) {
    this.expirationDate = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Sets the enabled attribute of the ProductCategory object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }

  /**
   * Sets the enabled attribute of the ProductCategory object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the parentName attribute of the ProductCategory object
   *
   * @param tmp The new parentName value
   */
  public void setParentName(String tmp) {
    this.parentName = tmp;
  }

  /**
   * Sets the typeName attribute of the ProductCategory object
   *
   * @param tmp The new typeName value
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }

  /**
   * Constructor for the ProductCategory object
   */
  public ProductCategory() {
  }

  /**
   * Constructor for the ProductCategory object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ProductCategory(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }

  /**
   * Constructor for the ProductCategory object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ProductCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildChildList(Connection db) throws SQLException {
    // gets the child categories of this category
    childList.setParentId(this.getId());
    childList.setBuildProducts(buildProductList);
    childList.setBuildActiveProducts(buildActiveProducts);
    childList.setBuildCompleteHierarchy(buildCompleteHierarchy);
    childList.setBuildActivePrice(this.getBuildActivePrice());
    childList.buildList(db);
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildProductList(Connection db) throws SQLException {
    // gets all products of this category
    productList.setCategoryId(this.getId());
    productList.setActive(buildActiveProducts);
    productList.setBuildResources(true);
    productList.setBuildActivePrice(this.getBuildActivePrice());
    productList.buildList(db);
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
      throw new SQLException("Invalid Product Category Number");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT " +
            " pctgy.*, " +
            " pctgy2.category_name AS parent_name, " +
            " pctgytype.description AS type_name " +
            " FROM product_category pctgy " +
            " LEFT JOIN product_category pctgy2 " +
            " ON ( pctgy.parent_id = pctgy2.category_id ) " +
            " LEFT JOIN lookup_product_category_type pctgytype " +
            " ON ( pctgy.type_id = pctgytype.code ) " +
            " WHERE pctgy.category_id = ? " +
            " ORDER BY pctgy.category_name ");
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
    if (buildChildList) {
      this.buildChildList(db);
    }
    if (buildChildCount) {
      this.buildChildCount(db);
    }
    if (buildProductList) {
      this.buildProductList(db);
    }
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildChildCount(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Product Category Number");
    }

    PreparedStatement pst = db.prepareStatement(
        "select count('X') AS child_count from product_category where parent_id=?");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.childCount = DatabaseUtils.getInt(rs, "child_count");
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Product Category not found");
    }
  }

  /**
   * Adds a feature to the Category attribute of the ProductCategory object
   *
   * @param db     The feature to be added to the Category attribute
   * @param cat2id The feature to be added to the Category attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int addCategory(Connection db, int cat2id) throws SQLException {
    int seqId = -1;
    int result = -1;
    int i = 0;
    if (cat2id == -1 || this.getId() == -1) {
      throw new SQLException("Invalid category ID ");
    }
    //To simplify the query procedure, insert two records for category_map
    //Insert the first record
    seqId = DatabaseUtils.getNextSeq(db, "product_category_map_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO product_category_map (" +
            (seqId > -1 ? "id, " : "") + "category1_id, category2_id) " +
            "VALUES(" +
            (seqId > -1 ? "?" : "") + "? , ? );");
    if (seqId > -1) {
      pst.setInt(++i, seqId);
    }
    pst.setInt(++i, this.getId());
    pst.setInt(++i, cat2id);
    result = pst.executeUpdate();
    pst.close();

    i = 0;
    //Insert the second record
    seqId = DatabaseUtils.getNextSeq(db, "product_category_map_id_seq");
    pst = db.prepareStatement(
        "INSERT INTO product_category_map(" +
            (seqId > -1 ? "id, " : "") + "category1_id, category2_id) " +
            "VALUES(" +
            (seqId > -1 ? "?" : "") + "? , ? );");
    if (seqId > -1) {
      pst.setInt(++i, seqId);
    }
    pst.setInt(++i, cat2id);
    pst.setInt(++i, this.getId());
    result += pst.executeUpdate();
    pst.close();
    return result;
  }

  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param cat2id Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean removeCategoryMapping(Connection db, int cat2id) throws SQLException {
    boolean result = false;
    int i = 0;
    if (cat2id == -1 || this.getId() == -1) {
      throw new SQLException("Invalid category ID ");
    }

    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM product_category_map " +
            "WHERE category1_id = ? " +
            "AND   category2_id = ? ");
    pst.setInt(++i, this.getId());
    pst.setInt(++i, cat2id);
    result = pst.execute();
    pst.close();

    i = 0;
    pst = db.prepareStatement(
        "DELETE FROM product_category_map " +
            "WHERE category1_id = ? " +
            "AND 	 category2_id = ? ");
    pst.setInt(++i, cat2id);
    pst.setInt(++i, this.getId());
    result = pst.execute();
    pst.close();
    return result;
  }

  /**
   * Adds a feature to the Catalog attribute of the ProductCategory object
   *
   * @param db        The feature to be added to the Catalog attribute
   * @param catalogId The feature to be added to the Catalog attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int addCatalog(Connection db, int catalogId) throws SQLException {
    int result = -1;
    int i = 0;
    if (catalogId == -1 || this.getId() == -1) {
      throw new SQLException("Invalid catalog id or category id ");
    }
    int seqId = DatabaseUtils.getNextSeq(
        db, "product_catalog_category_map_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO product_catalog_category_map (" +
            (seqId > -1 ? "id, " : "") + "product_id, category_id) " +
            "VALUES (" +
            (seqId > -1 ? "?, " : "") + "? , ? );");
    if (seqId > -1) {
      pst.setInt(++i, seqId);
    }
    pst.setInt(++i, catalogId);
    pst.setInt(++i, this.getId());
    result = pst.executeUpdate();
    pst.close();
    return result;
  }

  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    // product_category table
    this.setId(rs.getInt("category_id"));
    parentId = DatabaseUtils.getInt(rs, "parent_id");
    name = rs.getString("category_name");
    abbreviation = rs.getString("abbreviation");
    shortDescription = rs.getString("short_description");
    longDescription = rs.getString("long_description");
    typeId = DatabaseUtils.getInt(rs, "type_id");
    thumbnailImageId = DatabaseUtils.getInt(rs, "thumbnail_image_id");
    smallImageId = DatabaseUtils.getInt(rs, "small_image_id");
    largeImageId = DatabaseUtils.getInt(rs, "large_image_id");
    listOrder = DatabaseUtils.getInt(rs, "list_order");
    enteredBy = rs.getInt("enteredby");
    entered = rs.getTimestamp("entered");
    modifiedBy = rs.getInt("modifiedby");
    modified = rs.getTimestamp("modified");
    startDate = rs.getTimestamp("start_date");
    expirationDate = rs.getTimestamp("expiration_date");
    enabled = rs.getBoolean("enabled");
    this.setStatusId(DatabaseUtils.getInt(rs, "status_id"));
    this.setImportId(DatabaseUtils.getInt(rs, "import_id"));

    // product_category parent table
    parentName = rs.getString("parent_name");

    // product_category_type table
    typeName = rs.getString("type_name");

  }

  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param baseFilePath Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    boolean result = false;
    if (this.getId() == -1) {
      throw new SQLException("Product Category ID not specified.");
    }
    boolean commit = true;
    int i = 0;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      /*
          * Delete any folder data CustomFieldRecordList folderList = new
          * CustomFieldRecordList();
       *  folderList.setLinkModuleId(Constants.FOLDERS_PRODUCT_CATEGORY);
          * folderList.setLinkItemId(this.getId()); folderList.buildList(db);
          * folderList.delete(db); folderList = null;
       */
      //delete all the dependencies that contain the product_category id
      //delete the product_category_map s that have category1_id = id
      PreparedStatement pst = db.prepareStatement(
          " DELETE from product_category_map " +
              " WHERE category1_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      //delete the product_category_map s that have category2_id = id
      i = 0;
      pst = db.prepareStatement(
          " DELETE from product_category_map " +
              " WHERE category2_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      // delete the product_catalog_category_map that contain the
      // category_id =
      // id
      i = 0;
      pst = db.prepareStatement(
          " DELETE from product_catalog_category_map " +
              " WHERE category_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      //delete any associated product_category s with the same parent_id
      ProductCategoryList list = new ProductCategoryList();
      list.setParentId(this.getId());
      list.buildList(db);
      list.delete(db, baseFilePath);
      //delete any associated product_category s with the same parent_id
      // also delete all the mappings associated with the current id
      //Delete any documents
      FileItemList fileList = new FileItemList();
      fileList.setLinkModuleId(Constants.DOCUMENTS_PRODUCT_CATEGORY);
      fileList.setLinkItemId(this.getId());
      fileList.buildList(db);
      fileList.delete(db, baseFilePath);
      fileList = null;
      //delete the product_category with category_id = id
      i = 0;
      pst = db.prepareStatement(
          "DELETE FROM product_category WHERE category_id = ?");
      pst.setInt(1, this.getId());
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
   * Gets the timeZoneParams attribute of the ProductCategory class
   *
   * @return The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("startDate");
    thisList.add("expirationDate");
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
    StringBuffer sql = new StringBuffer();
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "product_category_category_id_seq");
      sql.append(
          " INSERT INTO product_category (" +
              " parent_id, " +
              " category_name,abbreviation, short_description, " +
              " long_description,type_id,thumbnail_image_id, " +
              " small_image_id, large_image_id,list_order, " +
              " enteredby, ");
      if (id > -1) {
        sql.append("category_id, ");
      }
      if (importId > -1) {
        sql.append("import_id, ");
      }
      if (statusId > -1) {
        sql.append("status_id, ");
      }
      sql.append("entered, ");
      sql.append("modifiedBy, ");
      sql.append("modified, ");
      sql.append("start_date,expiration_date, enabled)");
      sql.append("VALUES ( ?,?,?,?,?,?,?,?,?,?,?,");
      if (id > -1) {
        sql.append("?, ");
      }
      if (importId > -1) {
        sql.append("?, ");
      }
      if (statusId > -1) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ");
      if (modified != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
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
      DatabaseUtils.setInt(pst, ++i, this.getThumbnailImageId());
      DatabaseUtils.setInt(pst, ++i, this.getSmallImageId());
      DatabaseUtils.setInt(pst, ++i, this.getLargeImageId());
      DatabaseUtils.setInt(pst, ++i, this.getListOrder());
      DatabaseUtils.setInt(pst, ++i, this.getEnteredBy());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (importId > -1) {
        pst.setInt(++i, importId);
      }
      if (statusId > -1) {
        pst.setInt(++i, statusId);
      }
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
      id = DatabaseUtils.getCurrVal(
          db, "product_category_category_id_seq", id);
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
        "UPDATE product_category SET " +
            "parent_id = ?, category_name = ?, abbreviation = ?, " +
            "short_description = ?, long_description = ?, type_id = ?, " +
            "thumbnail_image_id = ?, small_image_id = ?, large_image_id = ?, ");
    sql.append(
        "modifiedby = ? , modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    sql.append("start_date = ?, expiration_date = ?, enabled = ? ");
    sql.append("WHERE category_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getAbbreviation());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getLongDescription());
    DatabaseUtils.setInt(pst, ++i, this.getTypeId());
    DatabaseUtils.setInt(pst, ++i, this.getThumbnailImageId());
    DatabaseUtils.setInt(pst, ++i, this.getSmallImageId());
    DatabaseUtils.setInt(pst, ++i, this.getLargeImageId());
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param parentId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int updateParent(Connection db, int parentId) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Product Category ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE product_category " +
            "SET parent_id = ?, " +
            "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "WHERE category_id = ? ");
    DatabaseUtils.setInt(pst, 1, parentId);
    pst.setInt(2, this.getId());
    int resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param itemId    Description of the Parameter
   * @param imageType Description of the Parameter
   * @param path      Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean removeFileItem(Connection db, int itemId, String imageType, String path) throws SQLException {
    boolean recordDeleted = false;
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      if ("thumbnail".equals(imageType)) {
        this.setThumbnailImageId(-1);
      } else if ("small".equals(imageType)) {
        this.setSmallImageId(-1);
      } else if ("large".equals(imageType)) {
        this.setLargeImageId(-1);
      }
      this.update(db);
      FileItem thisItem = new FileItem(
          db, itemId, this.getId(), Constants.DOCUMENTS_PRODUCT_CATEGORY);
      recordDeleted = thisItem.delete(db, path);
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return recordDeleted;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public DependencyList processDependencies(Connection db)
      throws SQLException {
    // This method checks all the mappings for any product_category with the
    // current id
    // The currently known mappings are product_category_map,
    // product_catalog_category_map
    if (this.getId() == -1) {
      throw new SQLException("Product Category ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;

    // check for documents
    Dependency docDependency = new Dependency();
    docDependency.setName("images");
    docDependency.setCount(
        FileItemList.retrieveRecordCount(
            db, Constants.DOCUMENTS_PRODUCT_CATEGORY, this.getId()));
    docDependency.setCanDelete(true);
    dependencyList.add(docDependency);

    /*
       * /Check for documents Dependency docDependency = new Dependency();
     *  docDependency.setName("documents");
       * docDependency.setCount(FileItemList.retrieveRecordCount(db,
       * Constants.DOCUMENTS_PRODUCT_CATEGORY, this.getId()));
       * docDependency.setCanDelete(true); dependencyList.add(docDependency);
       * /Check for folders Dependency folderDependency = new Dependency();
     *  folderDependency.setName("folders");
       * folderDependency.setCount(CustomFieldRecordList.retrieveRecordCount(db,
       * Constants.FOLDERS_PRODUCT_CATEGORY, this.getId()));
     *  folderDependency.setCanDelete(true);
     *  dependencyList.add(folderDependency);
     */
    //Check for product_category with parent_id = id
    i = 0;
    pst = db.prepareStatement(
        "SELECT count(*) as parentcount " +
            "FROM product_category " +
            "WHERE parent_id = ?");
    pst.setInt(++i, this.getId());
    rs = pst.executeQuery();
    if (rs.next()) {
      int categoryCount = rs.getInt("parentcount");
      if (categoryCount != 0) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("subcategories");
        thisDependency.setCount(categoryCount);
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }
    }
    rs.close();
    pst.close();

    //Check the products that belong ONLY to this category
    // TODO: Remove this if not necessary
    /*
       * i = 0; pst = db.prepareStatement( "SELECT count(*) AS categorycount " +
       * "FROM product_catalog_category_map map " + "WHERE map.category_id = ? " +
       * "AND map.product_id NOT IN ( " + " SELECT product_id " + " FROM
       * product_catalog_category_map " + " WHERE category_id <> ? ) " );
       * pst.setInt(++i, this.getId()); pst.setInt(++i, this.getId()); rs =
       * pst.executeQuery(); if (rs.next()) { int categoryCount =
       * rs.getInt("categorycount"); if (categoryCount != 0) { Dependency
       * thisDependency = new Dependency();
     *  thisDependency.setName("numberOfProductsBelongOnlyToThisCategory");
     *  thisDependency.setCount(categoryCount);
     *  thisDependency.setCanDelete(true);
       * dependencyList.add(thisDependency); } } rs.close(); pst.close();
     */
    //Check for other categories that link to the current category
    // TODO: uncomment this in future versions
    /*
       * i = 0; pst = db.prepareStatement( "SELECT count(*) as categorycount " +
       * "FROM product_category_map " + "WHERE category1_id = ?" );
       * pst.setInt(++i, this.getId()); rs = pst.executeQuery(); if
       * (rs.next()) { int categoryCount = rs.getInt("categorycount"); if
       * (categoryCount != 0) { Dependency thisDependency = new Dependency();
     *  thisDependency.setName("numberOfOtherCategoriesThatThisCategoryLinksTo");
     *  thisDependency.setCount(categoryCount);
     *  thisDependency.setCanDelete(true);
       * dependencyList.add(thisDependency); } } rs.close(); pst.close();
     */
    //Check for other categories that are linked with the current category
    // TODO: uncomment this in future versions
    /*
       * i = 0; pst = db.prepareStatement( "SELECT count(*) as categorycount " + "
       * FROM product_category_map " + "WHERE category2_id = ?" );
       * pst.setInt(++i, this.getId()); rs = pst.executeQuery(); if
       * (rs.next()) { int categoryCount = rs.getInt("categorycount"); if
       * (categoryCount != 0) { Dependency thisDependency = new Dependency();
     *  thisDependency.setName("numberOfCategoriesThatHaveLinkToThisCategory");
     *  thisDependency.setCount(categoryCount);
     *  thisDependency.setCanDelete(false);
       * dependencyList.add(thisDependency); } } rs.close(); pst.close();
     */
    //Check for product catalogs linked to this category
    i = 0;
    pst = db.prepareStatement(
        "SELECT count(*) as productcount " +
            "FROM product_catalog_category_map " +
            "WHERE category_id = ?");
    pst.setInt(++i, this.getId());
    rs = pst.executeQuery();
    if (rs.next()) {
      int categoryCount = rs.getInt("productcount");
      if (categoryCount != 0) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("products");
        thisDependency.setCount(categoryCount);
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }
    }
    rs.close();
    pst.close();
    return dependencyList;
  }

  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param catName Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean lookupId(Connection db, String catName) throws SQLException {
    boolean result = false;
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) as counter " +
            "FROM product_category " +
            "WHERE category_name = ? ");
    pst.setString(++i, catName);
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
   * @param db         Description of the Parameter
   * @param categoryId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static String lookupDateConfiguratorClass(Connection db, int categoryId) throws SQLException {
    String className = "";
    PreparedStatement pst = db.prepareStatement(
        "SELECT class_name " +
            "FROM product_date_configurator " +
            "WHERE category_id = ? ");
    int i = 0;
    pst.setInt(++i, categoryId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      className = rs.getString("class_name");
    }
    rs.close();
    pst.close();
    return className;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean checkForProducts(Connection db) throws SQLException {
  this.buildChildList(db);
  this.buildProductList(db);
  if (this.getProductList().size() > 0) {
      return true;
    }
    if (!this.getChildList().filterProductCategories(db)) {
      return false;
    }
    if (this.getChildList().size() <= 0 && this.getProductList().size() <= 0) {
      return false;
    }
    return true;
  }

  /**
   * Retrieves the hierarch of ids and category names upto the specified
   * currentId The String array can be used to retrieve more details of a
   * specific category
   *
   * @param db        Description of the Parameter
   * @param hierarchy Description of the Parameter
   * @param currentId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void buildHierarchy(Connection db, Map hierarchy, int currentId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT parent_id, category_name " +
            "FROM product_category " +
            "WHERE category_id = ? ");
    pst.setInt(1, currentId);
    ResultSet rs = pst.executeQuery();
    int parentId = 0;
    String name = null;
    if (rs.next()) {
      parentId = DatabaseUtils.getInt(rs, "parent_id");
      name = rs.getString("category_name");
    }
    rs.close();
    pst.close();
    hierarchy.put(new Integer(currentId), new String[]{name});
    if (parentId > -1) {
      ProductCategory.buildHierarchy(db, hierarchy, parentId);
    }
  }

  /**
   * This method returns the child category with categoryId as the id else it
   * returns the child which has a node in the child's sub-tree with category
   * id as id
   *
   * @param db         Description of the Parameter
   * @param categoryId Description of the Parameter
   * @return The child value
   * @throws SQLException Description of the Exception
   */
  public ProductCategory getChild(Connection db, int categoryId) throws SQLException {
    this.buildChildList(db);
    Iterator i = this.getChildList().iterator();
    while (i.hasNext()) {
      ProductCategory thisCategory = (ProductCategory) i.next();
      if (thisCategory.getId() == categoryId) {
        return thisCategory;
      }
    }
    // For each child populate the sub-tree and determine if the category
    // exists
    //in the child's sub-tree. If it exists, then return the child
    Iterator j = this.getChildList().iterator();
    while (j.hasNext()) {
      ProductCategory thisChild = (ProductCategory) j.next();
      thisChild.buildChildList(db);
      //populate the entire sub tree under this child
      ProductCategoryList.buildHierarchy(db, thisChild.getChildList());
      thisChild.getChildList().buildCompleteHierarchy();
      Iterator k = thisChild.getChildList().iterator();
      while (k.hasNext()) {
        ProductCategory node = (ProductCategory) k.next();
        if (node.getId() == categoryId) {
          return thisChild;
        }
      }
    }
    return null;
  }

  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param contractId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean hasServiceContractProducts(Connection db, int contractId) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Product Category ID not specified");
    }
    ProductCatalogList productList = new ProductCatalogList();
    productList.setCategoryId(this.getId());
    productList.setServiceContractId(contractId);
    productList.buildList(db);
    if (productList.size() > 0) {
      return true;
    }

    ProductCategoryList childList = new ProductCategoryList();
    childList.setParentId(this.getId());
    childList.buildList(db);
    Iterator i = childList.iterator();
    while (i.hasNext()) {
      ProductCategory thisCategory = (ProductCategory) i.next();
      if (thisCategory.hasServiceContractProducts(db, contractId)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines what to do if this record is marked for INSERT, UPDATE, or
   * DELETE
   *
   * @param db         Description of Parameter
   * @param categoryId Description of Parameter
   * @param enteredBy  Description of Parameter
   * @param modifiedBy Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void process(Connection db, int categoryId, int enteredBy,
                      int modifiedBy) throws SQLException {

    if (this.getId() == -1) {
      this.setId(categoryId);
      this.setEnteredBy(enteredBy);
      this.setModifiedBy(modifiedBy);
      this.insert(db);
    } else {
      this.setModifiedBy(modifiedBy);
      this.update(db);
    }

  }

  public int getCategoryCountByName(Connection db, String categoryName)
      throws SQLException {
    int result = -1;
    if (categoryName == null || "".equals(categoryName.trim())) {
      throw new SQLException("Invalid Product Category Name");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) " +
            " FROM product_category pctgy " +
            " WHERE pctgy.category_NAME = ? ");
    pst.setString(1, categoryName);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = rs.getInt(1);
    }
    rs.close();
    pst.close();
    return result;
  }

  public static int updateImportStatus(Connection db, int importId, int status)
      throws SQLException {
    int count = 0;
    boolean commit = true;

    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      String sql = "UPDATE product_category " +
          "SET status_id = ?, " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
          "WHERE import_id = ? ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, status);
      pst.setInt(++i, importId);
      count = pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
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
    return count;
  }

  public static ProductCategoryList buildFullName(Connection db, ProductCategoryList fullName, int currentId, boolean includeCurrent)
      throws SQLException {
    PreparedStatement pst = db
        .prepareStatement("SELECT parent_id, category_name, category_id "
            + "FROM product_category " + "WHERE category_id = ? ");
    pst.setInt(1, currentId);
    ResultSet rs = pst.executeQuery();
    int parentId = 0;
    String name = null;
    if (rs.next()) {
      parentId = DatabaseUtils.getInt(rs, "parent_id");
      name = rs.getString("category_name");
    }
    rs.close();
    pst.close();
    ProductCategory pc = new ProductCategory();
    pc.setId(currentId);
    pc.setName(name);
    if (parentId > -1) {
      fullName = ProductCategory.buildFullName(db, fullName, parentId, true);
    }
    if (includeCurrent) {
      fullName.add(pc);
    }
    return fullName;
  }
  
  public static int getCategoryById(String catalogName,int importId,Connection db) throws SQLException {
    String QUERY = "SELECT category_id FROM product_category WHERE category_name = ? "
                  + " and (import_id = ? or status_id = ? )";
    PreparedStatement pst = db.prepareStatement(QUERY);
    pst.setString(1, catalogName);
    pst.setInt(2,importId);
    pst.setInt(3,Import.PROCESSED_APPROVED);
    ResultSet rs = pst.executeQuery();                        
    int id = -1;
    if(rs.next()) {
      id = DatabaseUtils.getInt(rs,"category_id");
    }
    rs.close();
    pst.close();
    return id; 
  }
  
}

