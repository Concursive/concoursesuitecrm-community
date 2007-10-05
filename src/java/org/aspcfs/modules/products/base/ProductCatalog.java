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
import com.isavvix.tools.FileInfo;
import com.zeroio.iteam.base.FileItem;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.quotes.base.Quote;
import org.aspcfs.modules.quotes.base.QuoteProduct;
import org.aspcfs.modules.quotes.base.QuoteProductList;
import org.aspcfs.modules.servicecontracts.base.ServiceContract;
import org.aspcfs.modules.servicecontracts.base.ServiceContractProduct;
import org.aspcfs.modules.servicecontracts.base.ServiceContractProductList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A generic Product Catalog belongs to a Product Category and contains several
 * Product Options
 *
 * @author partha
 * @version $Id$
 * @created March 18, 2004
 */
public class ProductCatalog extends GenericBean {
  private static final long serialVersionUID = -8112814592797357413L;
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

  private boolean enabled = false;
  private boolean inStock = true;
  private Timestamp entered = null;
  private Timestamp modified = null;
  private Timestamp startDate = null;
  private Timestamp expirationDate = null;
  private Timestamp trashedDate = null;
  private boolean active = false;

  // other supplimentary data
  private int categoryId = -1;
  private String categoryName = null;
  private String parentName = null;
  private String typeName = null;
  private String formatName = null;
  private String shippingName = null;
  private String shippingTimeName = null;
  private String smallImageName = null;
  private String largeImageName = null;
  private String thumbnailImageName = null;

  // resources
  private int buildActiveOptions = Constants.UNDEFINED;
  private boolean buildOptions = false;
  private boolean buildCategories = false;
  private ProductOptionList optionList = null;
  private ProductCategoryList categoryList = new ProductCategoryList();
  private ProductCatalogPricingList priceList = new ProductCatalogPricingList();
  private ProductCatalogPricing activePrice = new ProductCatalogPricing();

  // helper properties
  private boolean hasCustomerProduct = false;
  private boolean buildPriceList = false;
  private boolean buildActivePrice = false;

  private int statusId = -1;
  private int importId = -1;
  private String comments = null;
  private FileInfo smallImageFile = null;
  private FileInfo largeImageFile = null;
  private FileInfo thumbnailImageFile = null;

  //pricing information
  private double msrpAmount = 0.0;
  private double priceAmount = 0.0;
  private double costAmount = 0.0;

  //Logger
  private long milies = -1;
  private static Logger logger = Logger.getLogger(ProductCatalog.class);

  static {
    if (System.getProperty("DEBUG") != null) {
      logger.setLevel(Level.DEBUG);
    }
  }

  /**
   * Gets the thumbnailImageFile attribute of the ProductCatalog object
   *
   * @return thumbnailImageFile The thumbnailImageFile value
   */
  public FileInfo getThumbnailImageFile() {
    return this.thumbnailImageFile;
  }

  /**
   * Sets the thumbnailImageFile attribute of the ProductCatalog object
   *
   * @param thumbnailImageFile The new thumbnailImageFile value
   */
  public void setThumbnailImageFile(FileInfo thumbnailImageFile) {
    this.thumbnailImageFile = thumbnailImageFile;
  }

  /**
   * Gets the thumbnailImageName attribute of the ProductCatalog object
   *
   * @return thumbnailImageName The thumbnailImageName value
   */
  public String getThumbnailImageName() {
    return this.thumbnailImageName;
  }

  /**
   * Sets the thumbnailImageName attribute of the ProductCatalog object
   *
   * @param thumbnailImageName The new thumbnailImageName value
   */
  public void setThumbnailImageName(String thumbnailImageName) {
    this.thumbnailImageName = thumbnailImageName;
  }

  /**
   * Gets the largeImageFile attribute of the ProductCatalog object
   *
   * @return largeImageFile The largeImageFile value
   */
  public FileInfo getLargeImageFile() {
    return largeImageFile;
  }

  /**
   * Sets the largeImageFile attribute of the ProductCatalog object
   *
   * @param largeImageFile The new largeImageFile value
   */
  public void setLargeImageFile(FileInfo largeImageFile) {
    this.largeImageFile = largeImageFile;
  }

  /**
   * Gets the smallImageFile attribute of the ProductCatalog object
   *
   * @return smallImageFile The smallImageFile value
   */
  public FileInfo getSmallImageFile() {
    return smallImageFile;
  }

  /**
   * Sets the smallImageFile attribute of the ProductCatalog object
   *
   * @param smallImageFile The new smallImageFile value
   */
  public void setSmallImageFile(FileInfo smallImageFile) {
    this.smallImageFile = smallImageFile;
  }

  /**
   * Gets the approved attribute of the Contact object
   *
   * @return The approved value
   */
  public boolean isApproved() {
    return (statusId != Import.PROCESSED_UNAPPROVED);
  }

  /**
   * Gets the largeImageName attribute of the ProductCatalog object
   *
   * @return largeImageName The largeImageName value
   */
  public String getLargeImageName() {
    return largeImageName;
  }

  /**
   * Sets the largeImageName attribute of the ProductCatalog object
   *
   * @param largeImageName The new largeImageName value
   */
  public void setLargeImageName(String largeImageName) {
    this.largeImageName = largeImageName;
  }

  /**
   * Gets the smallImageName attribute of the ProductCatalog object
   *
   * @return smallImageName The smallImageName value
   */
  public String getSmallImageName() {
    return smallImageName;
  }

  /**
   * Sets the smallImageName attribute of the ProductCatalog object
   *
   * @param smallImageName The new smallImageName value
   */
  public void setSmallImageName(String smallImageName) {
    this.smallImageName = smallImageName;
  }

  /**
   * Gets the comments attribute of the ProductCatalog object
   *
   * @return comments The comments value
   */
  public String getComments() {
    return comments;
  }

  /**
   * Sets the comments attribute of the ProductCatalog object
   *
   * @param comments The new comments value
   */
  public void setComments(String comments) {
    this.comments = comments;
  }

  /**
   * Gets the importId attribute of the ProductCatalog object
   *
   * @return importId The importId value
   */
  public int getImportId() {
    return importId;
  }

  /**
   * Sets the importId attribute of the ProductCatalog object
   *
   * @param importId The new importId value
   */
  public void setImportId(int importId) {
    this.importId = importId;
  }

  /**
   * Sets the importId attribute of the ProductCatalog object
   *
   * @param importId The new importId value
   */
  public void setImportId(String importId) {
    this.importId = Integer.parseInt(importId);
  }


  /**
   * Gets the statusId attribute of the ProductCatalog object
   *
   * @return statusId The statusId value
   */
  public int getStatusId() {
    return statusId;
  }

  /**
   * Sets the statusId attribute of the ProductCatalog object
   *
   * @param statusId The new statusId value
   */
  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }

  /**
   * Sets the statusId attribute of the ProductCatalog object
   *
   * @param statusId The new statusId value
   */
  public void setStatusId(String statusId) {
    this.statusId = Integer.parseInt(statusId);
  }

  /**
   * Gets the buildActiveOptions attribute of the ProductCatalog object
   *
   * @return The buildActiveOptions value
   */
  public int getBuildActiveOptions() {
    return buildActiveOptions;
  }

  /**
   * Sets the buildActiveOptions attribute of the ProductCatalog object
   *
   * @param tmp The new buildActiveOptions value
   */
  public void setBuildActiveOptions(int tmp) {
    this.buildActiveOptions = tmp;
  }

  /**
   * Sets the buildActiveOptions attribute of the ProductCatalog object
   *
   * @param tmp The new buildActiveOptions value
   */
  public void setBuildActiveOptions(String tmp) {
    this.buildActiveOptions = Integer.parseInt(tmp);
  }

  /**
   * Gets the buildActivePrice attribute of the ProductCatalog object
   *
   * @return The buildActivePrice value
   */
  public boolean getBuildActivePrice() {
    return buildActivePrice;
  }

  /**
   * Sets the buildActivePrice attribute of the ProductCatalog object
   *
   * @param tmp The new buildActivePrice value
   */
  public void setBuildActivePrice(boolean tmp) {
    this.buildActivePrice = tmp;
  }

  /**
   * Sets the buildActivePrice attribute of the ProductCatalog object
   *
   * @param tmp The new buildActivePrice value
   */
  public void setBuildActivePrice(String tmp) {
    this.buildActivePrice = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the buildPriceList attribute of the ProductCatalog object
   *
   * @return The buildPriceList value
   */
  public boolean getBuildPriceList() {
    return buildPriceList;
  }

  /**
   * Sets the buildPriceList attribute of the ProductCatalog object
   *
   * @param tmp The new buildPriceList value
   */
  public void setBuildPriceList(boolean tmp) {
    this.buildPriceList = tmp;
  }

  /**
   * Sets the buildPriceList attribute of the ProductCatalog object
   *
   * @param tmp The new buildPriceList value
   */
  public void setBuildPriceList(String tmp) {
    this.buildPriceList = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the priceList attribute of the ProductCatalog object
   *
   * @return The priceList value
   */
  public ProductCatalogPricingList getPriceList() {
    return priceList;
  }

  /**
   * Sets the priceList attribute of the ProductCatalog object
   *
   * @param tmp The new priceList value
   */
  public void setPriceList(ProductCatalogPricingList tmp) {
    this.priceList = tmp;
  }

  /**
   * Sets the buildCategories attribute of the ProductCatalog object
   *
   * @param tmp The new buildCategories value
   */
  public void setBuildCategories(boolean tmp) {
    this.buildCategories = tmp;
  }

  /**
   * Sets the buildCategories attribute of the ProductCatalog object
   *
   * @param tmp The new buildCategories value
   */
  public void setBuildCategories(String tmp) {
    this.buildCategories = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the activePrice attribute of the ProductCatalog object
   *
   * @param tmp The new activePrice value
   */
  public void setActivePrice(ProductCatalogPricing tmp) {
    this.activePrice = tmp;
  }

  /**
   * Gets the activePrice attribute of the ProductCatalog object
   *
   * @return The activePrice value
   */
  public ProductCatalogPricing getActivePrice() {
    return activePrice;
  }

  /**
   * Gets the buildCategories attribute of the ProductCatalog object
   *
   * @return The buildCategories value
   */
  public boolean getBuildCategories() {
    return buildCategories;
  }

  /**
   * Sets the categoryList attribute of the ProductCatalog object
   *
   * @param tmp The new categoryList value
   */
  public void setCategoryList(ProductCategoryList tmp) {
    this.categoryList = tmp;
  }

  /**
   * Gets the categoryList attribute of the ProductCatalog object
   *
   * @return The categoryList value
   */
  public ProductCategoryList getCategoryList() {
    return categoryList;
  }

  /**
   * Sets the id attribute of the ProductCatalog object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }

  /**
   * Sets the id attribute of the ProductCatalog object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }

  /**
   * Sets the parentId attribute of the ProductCatalog object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }

  /**
   * Sets the parentId attribute of the ProductCatalog object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }

  /**
   * Sets the typeId attribute of the ProductCatalog object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }

  /**
   * Sets the typeId attribute of the ProductCatalog object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }

  /**
   * Sets the formatId attribute of the ProductCatalog object
   *
   * @param tmp The new formatId value
   */
  public void setFormatId(int tmp) {
    this.formatId = tmp;
  }

  /**
   * Sets the formatId attribute of the ProductCatalog object
   *
   * @param tmp The new formatId value
   */
  public void setFormatId(String tmp) {
    this.formatId = Integer.parseInt(tmp);
  }

  /**
   * Sets the shippingId attribute of the ProductCatalog object
   *
   * @param tmp The new shippingId value
   */
  public void setShippingId(int tmp) {
    this.shippingId = tmp;
  }

  /**
   * Sets the shippingId attribute of the ProductCatalog object
   *
   * @param tmp The new shippingId value
   */
  public void setShippingId(String tmp) {
    this.shippingId = Integer.parseInt(tmp);
  }

  /**
   * Sets the estimatedShipTime attribute of the ProductCatalog object
   *
   * @param tmp The new estimatedShipTime value
   */
  public void setEstimatedShipTime(int tmp) {
    this.estimatedShipTime = tmp;
  }

  /**
   * Sets the estimatedShipTime attribute of the ProductCatalog object
   *
   * @param tmp The new estimatedShipTime value
   */
  public void setEstimatedShipTime(String tmp) {
    this.estimatedShipTime = Integer.parseInt(tmp);
  }

  /**
   * Sets the thumbnailImageId attribute of the ProductCatalog object
   *
   * @param tmp The new thumbnailImageId value
   */
  public void setThumbnailImageId(int tmp) {
    this.thumbnailImageId = tmp;
  }

  /**
   * Sets the thumbnailImageId attribute of the ProductCatalog object
   *
   * @param tmp The new thumbnailImageId value
   */
  public void setThumbnailImageId(String tmp) {
    this.thumbnailImageId = Integer.parseInt(tmp);
  }

  /**
   * Sets the smallImageId attribute of the ProductCatalog object
   *
   * @param tmp The new smallImageId value
   */
  public void setSmallImageId(int tmp) {
    this.smallImageId = tmp;
  }

  /**
   * Sets the smallImageId attribute of the ProductCatalog object
   *
   * @param tmp The new smallImageId value
   */
  public void setSmallImageId(String tmp) {
    this.smallImageId = Integer.parseInt(tmp);
  }

  /**
   * Sets the largeImageId attribute of the ProductCatalog object
   *
   * @param tmp The new largeImageId value
   */
  public void setLargeImageId(int tmp) {
    this.largeImageId = tmp;
  }

  /**
   * Sets the largeImageId attribute of the ProductCatalog object
   *
   * @param tmp The new largeImageId value
   */
  public void setLargeImageId(String tmp) {
    this.largeImageId = Integer.parseInt(tmp);
  }

  /**
   * Sets the listOrder attribute of the ProductCatalog object
   *
   * @param tmp The new listOrder value
   */
  public void setListOrder(int tmp) {
    this.listOrder = tmp;
  }

  /**
   * Sets the listOrder attribute of the ProductCatalog object
   *
   * @param tmp The new listOrder value
   */
  public void setListOrder(String tmp) {
    this.listOrder = Integer.parseInt(tmp);
  }

  /**
   * Sets the enteredBy attribute of the ProductCatalog object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }

  /**
   * Sets the enteredBy attribute of the ProductCatalog object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }

  /**
   * Sets the modifiedBy attribute of the ProductCatalog object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }

  /**
   * Sets the modifiedBy attribute of the ProductCatalog object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }

  /**
   * Sets the name attribute of the ProductCatalog object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }

  public void setProductName(String tmp) {
    this.name = tmp;
  }

  /**
   * Sets the abbreviation attribute of the ProductCatalog object
   *
   * @param tmp The new abbreviation value
   */
  public void setAbbreviation(String tmp) {
    this.abbreviation = tmp;
  }

  /**
   * Sets the shortDescription attribute of the ProductCatalog object
   *
   * @param tmp The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }

  /**
   * Sets the longDescription attribute of the ProductCatalog object
   *
   * @param tmp The new longDescription value
   */
  public void setLongDescription(String tmp) {
    this.longDescription = tmp;
  }

  /**
   * Sets the specialNotes attribute of the ProductCatalog object
   *
   * @param tmp The new specialNotes value
   */
  public void setSpecialNotes(String tmp) {
    this.specialNotes = tmp;
  }

  /**
   * Sets the sku attribute of the ProductCatalog object
   *
   * @param tmp The new sku value
   */
  public void setSku(String tmp) {
    this.sku = tmp;
  }

  /**
   * Sets the enabled attribute of the ProductCatalog object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }

  /**
   * Sets the enabled attribute of the ProductCatalog object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the inStock attribute of the ProductCatalog object
   *
   * @param tmp The new inStock value
   */
  public void setInStock(boolean tmp) {
    this.inStock = tmp;
  }

  /**
   * Sets the inStock attribute of the ProductCatalog object
   *
   * @param tmp The new inStock value
   */
  public void setInStock(String tmp) {
    this.inStock = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the entered attribute of the ProductCatalog object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }

  /**
   * Sets the entered attribute of the ProductCatalog object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Sets the modified attribute of the ProductCatalog object
   *
   * @param tmp The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }

  /**
   * Sets the modified attribute of the ProductCatalog object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Sets the startDate attribute of the ProductCatalog object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(Timestamp tmp) {
    this.startDate = tmp;
  }

  /**
   * Sets the startDate attribute of the ProductCatalog object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Sets the expirationDate attribute of the ProductCatalog object
   *
   * @param tmp The new expirationDate value
   */
  public void setExpirationDate(Timestamp tmp) {
    this.expirationDate = tmp;
  }

  /**
   * Sets the expirationDate attribute of the ProductCatalog object
   *
   * @param tmp The new expirationDate value
   */
  public void setExpirationDate(String tmp) {
    this.expirationDate = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Sets the trashedDate attribute of the ProductCatalog object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(Timestamp tmp) {
    this.trashedDate = tmp;
  }

  /**
   * Sets the trashedDate attribute of the ProductCatalog object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Gets the trashed attribute of the ProductCatalog object
   *
   * @return The trashed value
   */
  public boolean isTrashed() {
    return (trashedDate != null);
  }

  /**
   * Sets the active attribute of the ProductCatalog object
   *
   * @param tmp The new active value
   */
  public void setActive(boolean tmp) {
    this.active = tmp;
  }

  /**
   * Sets the active attribute of the ProductCatalog object
   *
   * @param tmp The new active value
   */
  public void setActive(String tmp) {
    this.active = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the categoryId attribute of the ProductCatalog object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }

  /**
   * Sets the categoryId attribute of the ProductCatalog object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }

  /**
   * Sets the categoryName attribute of the ProductCatalog object
   *
   * @param tmp The new categoryName value
   */
  public void setCategoryName(String tmp) {
    this.categoryName = tmp;
  }

  /**
   * Sets the buildOptions attribute of the ProductCatalog object
   *
   * @param tmp The new buildOptions value
   */
  public void setBuildOptions(boolean tmp) {
    this.buildOptions = tmp;
  }

  /**
   * Sets the buildOptions attribute of the ProductCatalog object
   *
   * @param tmp The new buildOptions value
   */
  public void setBuildOptions(String tmp) {
    this.buildOptions = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the optionList attribute of the ProductCatalog object
   *
   * @param tmp The new optionList value
   */
  public void setOptionList(ProductOptionList tmp) {
    this.optionList = tmp;
  }

  /**
   * Sets the hasCustomerProduct attribute of the ProductCatalog object
   *
   * @param tmp The new hasCustomerProduct value
   */
  public void setHasCustomerProduct(boolean tmp) {
    this.hasCustomerProduct = tmp;
  }

  /**
   * Sets the hasCustomerProduct attribute of the ProductCatalog object
   *
   * @param tmp The new hasCustomerProduct value
   */
  public void setHasCustomerProduct(String tmp) {
    this.hasCustomerProduct = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the parentName attribute of the ProductCatalog object
   *
   * @param tmp The new parentName value
   */
  public void setParentName(String tmp) {
    this.parentName = tmp;
  }

  /**
   * Sets the typeName attribute of the ProductCatalog object
   *
   * @param tmp The new typeName value
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }

  /**
   * Sets the formatName attribute of the ProductCatalog object
   *
   * @param tmp The new formatName value
   */
  public void setFormatName(String tmp) {
    this.formatName = tmp;
  }

  /**
   * Sets the shippingName attribute of the ProductCatalog object
   *
   * @param tmp The new shippingName value
   */
  public void setShippingName(String tmp) {
    this.shippingName = tmp;
  }

  /**
   * Sets the shippingTimeName attribute of the ProductCatalog object
   *
   * @param tmp The new shippingTimeName value
   */
  public void setShippingTimeName(String tmp) {
    this.shippingTimeName = tmp;
  }

  /**
   * Gets the typeName attribute of the ProductCatalog object
   *
   * @return The typeName value
   */
  public String getTypeName() {
    return typeName;
  }

  /**
   * Gets the formatName attribute of the ProductCatalog object
   *
   * @return The formatName value
   */
  public String getFormatName() {
    return formatName;
  }

  /**
   * Gets the shippingName attribute of the ProductCatalog object
   *
   * @return The shippingName value
   */
  public String getShippingName() {
    return shippingName;
  }

  /**
   * Gets the shippingTimeName attribute of the ProductCatalog object
   *
   * @return The shippingTimeName value
   */
  public String getShippingTimeName() {
    return shippingTimeName;
  }

  /**
   * Gets the parentName attribute of the ProductCatalog object
   *
   * @return The parentName value
   */
  public String getParentName() {
    return parentName;
  }

  /**
   * Gets the id attribute of the ProductCatalog object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the parentId attribute of the ProductCatalog object
   *
   * @return The parentId value
   */
  public int getParentId() {
    return parentId;
  }

  /**
   * Gets the typeId attribute of the ProductCatalog object
   *
   * @return The typeId value
   */
  public int getTypeId() {
    return typeId;
  }

  /**
   * Gets the formatId attribute of the ProductCatalog object
   *
   * @return The formatId value
   */
  public int getFormatId() {
    return formatId;
  }

  /**
   * Gets the shippingId attribute of the ProductCatalog object
   *
   * @return The shippingId value
   */
  public int getShippingId() {
    return shippingId;
  }

  /**
   * Gets the estimatedShipTime attribute of the ProductCatalog object
   *
   * @return The estimatedShipTime value
   */
  public int getEstimatedShipTime() {
    return estimatedShipTime;
  }

  /**
   * Gets the thumbnailImageId attribute of the ProductCatalog object
   *
   * @return The thumbnailImageId value
   */
  public int getThumbnailImageId() {
    return thumbnailImageId;
  }

  /**
   * Gets the smallImageId attribute of the ProductCatalog object
   *
   * @return The smallImageId value
   */
  public int getSmallImageId() {
    return smallImageId;
  }

  /**
   * Gets the largeImageId attribute of the ProductCatalog object
   *
   * @return The largeImageId value
   */
  public int getLargeImageId() {
    return largeImageId;
  }

  /**
   * Gets the largestImageId attribute of the ProductCatalog object
   *
   * @return The largestImageId value
   */
  public int getLargestImageId() {
    if (largeImageId > -1) {
      return largeImageId;
    }
    if (smallImageId > -1) {
      return smallImageId;
    }
    if (thumbnailImageId > -1) {
      return thumbnailImageId;
    }
    return -1;
  }

  /**
   * Gets the listOrder attribute of the ProductCatalog object
   *
   * @return The listOrder value
   */
  public int getListOrder() {
    return listOrder;
  }

  /**
   * Gets the enteredBy attribute of the ProductCatalog object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }

  /**
   * Gets the modifiedBy attribute of the ProductCatalog object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }

  /**
   * Gets the name attribute of the ProductCatalog object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the name attribute of the ProductCatalog object
   *
   * @return The name value
   */
  public String getProductName() {
    return name;
  }

  /**
   * Gets the abbreviation attribute of the ProductCatalog object
   *
   * @return The abbreviation value
   */
  public String getAbbreviation() {
    return abbreviation;
  }

  /**
   * Gets the shortDescription attribute of the ProductCatalog object
   *
   * @return The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }

  /**
   * Gets the longDescription attribute of the ProductCatalog object
   *
   * @return The longDescription value
   */
  public String getLongDescription() {
    return longDescription;
  }

  /**
   * Gets the specialNotes attribute of the ProductCatalog object
   *
   * @return The specialNotes value
   */
  public String getSpecialNotes() {
    return specialNotes;
  }

  /**
   * Gets the sku attribute of the ProductCatalog object
   *
   * @return The sku value
   */
  public String getSku() {
    return sku;
  }

  /**
   * Gets the enabled attribute of the ProductCatalog object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }

  /**
   * Gets the inStock attribute of the ProductCatalog object
   *
   * @return The inStock value
   */
  public boolean getInStock() {
    return inStock;
  }

  /**
   * Gets the entered attribute of the ProductCatalog object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }

  /**
   * Gets the modified attribute of the ProductCatalog object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }

  /**
   * Gets the startDate attribute of the ProductCatalog object
   *
   * @return The startDate value
   */
  public Timestamp getStartDate() {
    return startDate;
  }

  /**
   * Gets the expirationDate attribute of the ProductCatalog object
   *
   * @return The expirationDate value
   */
  public Timestamp getExpirationDate() {
    return expirationDate;
  }

  /**
   * Gets the trashedDate attribute of the ProductCatalog object
   *
   * @return The trashedDate value
   */
  public Timestamp getTrashedDate() {
    return trashedDate;
  }

  /**
   * Gets the active attribute of the ProductCatalog object
   *
   * @return The active value
   */
  public boolean getActive() {
    return active;
  }

  /**
   * Gets the categoryId attribute of the ProductCatalog object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }

  /**
   * Gets the categoryName attribute of the ProductCatalog object
   *
   * @return The categoryName value
   */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   * Gets the buildOptions attribute of the ProductCatalog object
   *
   * @return The buildOptions value
   */
  public boolean getBuildOptions() {
    return buildOptions;
  }

  /**
   * Gets the optionList attribute of the ProductCatalog object
   *
   * @return The optionList value
   */
  public ProductOptionList getOptionList() {
    return optionList;
  }

  /**
   * Gets the hasCustomerProduct attribute of the ProductCatalog object
   *
   * @return The hasCustomerProduct value
   */
  public boolean getHasCustomerProduct() {
    return hasCustomerProduct;
  }

  /**
   * Gets the msrpAmount attribute of the ProductCatalog object
   *
   * @return The msrpAmount value
   */
  public double getMsrpAmount() {
    return msrpAmount;
  }

  /**
   * Sets the msrpAmount attribute of the ProductCatalog object
   *
   * @param tmp The new msrpAmount value
   */
  public void setMsrpAmount(double tmp) {
    this.msrpAmount = tmp;
  }

  /**
   * Sets the msrpAmount attribute of the ProductCatalog object
   *
   * @param tmp The new msrpAmount value
   */
  public void setMsrpAmount(String tmp) {
    this.msrpAmount = Double.parseDouble(tmp);
  }

  /**
   * Gets the priceAmount attribute of the ProductCatalog object
   *
   * @return The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   * Sets the priceAmount attribute of the ProductCatalog object
   *
   * @param tmp The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   * Sets the priceAmount attribute of the ProductCatalog object
   *
   * @param tmp The new priceAmount value
   */
  public void setPriceAmount(String tmp) {
    this.priceAmount = Double.parseDouble(tmp);
  }


  /**
   * Gets the costAmount attribute of the ProductCatalog object
   *
   * @return The costAmount value
   */
  public double getCostAmount() {
    return costAmount;
  }


  /**
   * Sets the costAmount attribute of the ProductCatalog object
   *
   * @param tmp The new costAmount value
   */
  public void setCostAmount(double tmp) {
    this.costAmount = tmp;
  }


  /**
   * Sets the costAmount attribute of the ProductCatalog object
   *
   * @param tmp The new costAmount value
   */
  public void setCostAmount(String tmp) {
    this.costAmount = Double.parseDouble(tmp);
  }

  /**
   * Constructor for the ProductCatalog object
   */
  public ProductCatalog() {
  }

  /**
   * Constructor for the ProductCatalog object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public ProductCatalog(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }

  /**
   * Constructor for the ProductCatalog object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public ProductCatalog(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Product Catalog Number");
    }
    PreparedStatement pst = db
        .prepareStatement("SELECT "
            + "pctlg.*, "
            + "pctlg2.product_name AS parent_name, "
            + "pctlgtype.description AS type_name, "
            + "pctlgformat.description AS format_name, "
            + "pctlgshipping.description AS shipping_name, "
            + "pctlgshiptime.description AS shiptime_name "
            + "FROM product_catalog pctlg "
            + "LEFT JOIN product_catalog pctlg2 ON ( pctlg.parent_id = pctlg2.product_id ) "
            + "LEFT JOIN lookup_product_type pctlgtype ON ( pctlg.type_id = pctlgtype.code ) "
            + "LEFT JOIN lookup_product_format pctlgformat ON ( pctlg.format_id = pctlgformat.code ) "
            + "LEFT JOIN lookup_product_shipping pctlgshipping ON ( pctlg.shipping_id = pctlgshipping.code ) "
            + "LEFT JOIN lookup_product_ship_time pctlgshiptime ON ( pctlg.estimated_ship_time = pctlgshiptime.code ) "
            + "WHERE pctlg.product_id = ? ");
    pst.setInt(1, id);
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis();
      logger.debug(pst.toString());
    }
    ResultSet rs = pst.executeQuery();
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis() - milies;
      logger.debug(String.valueOf(milies) + " ms");
    }
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
    // build resources
    if (buildOptions) {
      this.buildOptions(db);
    }
    if (buildCategories) {
      this.buildCategories(db);
    }
    if (buildPriceList) {
      this.buildPriceList(db);
    }
    if (buildActivePrice) {
      this.buildActivePrice(db);
    }
    // TODO: Remove adsjet specific stuff
    // detemine the products actual category trail since it might
    // exist several levels down the category tree
    // determineActualCategory(db);
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void determineActualCategory(Connection db) throws SQLException {
    // Product categories is a tree of categories and products can exist
    // at any level in the category list. Hence a product needs to know
    // exactly
    // the path from the root of the tree which is the category trail
    if (categoryId != -1) {
      // TODO: determine if the following line needs to be changed
      // eg: Currently it eliminates "Publications, " from "Publications,
      // Flagship, Broadsheet"
      String str = determineActualCategoryName(db, categoryId);
      if (str.indexOf("Publications,") > -1) {
        str = str.substring(str.indexOf(",") + 1);
      }
      this.setCategoryName(str.trim());
    }
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public String determineCategoryByProductId(Connection db, int id) throws SQLException {
    String result = null;
    if (id != -1) {
      PreparedStatement pst = db.prepareStatement("select " +
          "pc.category_name " +
          "from product_catalog_category_map pctlgcm, product_category pc " +
          "where pctlgcm.category_id=pc.category_id and pctlgcm.product_id=?");
      pst.setInt(1, id);
      if (System.getProperty("DEBUG") != null) {
        milies = System.currentTimeMillis();
        logger.debug(pst.toString());
      }
      ResultSet rs = pst.executeQuery();
      if (System.getProperty("DEBUG") != null) {
        milies = System.currentTimeMillis() - milies;
        logger.debug(String.valueOf(milies) + " ms");
      }
      if (rs.next()) {
        result = rs.getString(1);
        while (rs.next()) {
          result += ", " + rs.getString(1);
        }
      }
      rs.close();
      pst.close();
    }
    return result;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void determineCategory(Connection db) throws SQLException {
    this.categoryName = determineCategoryByProductId(db, this.id);
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
  public boolean removeFileItem(Connection db, int itemId, String imageType,
                                String path) throws SQLException {
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
      FileItem thisItem = new FileItem(db, itemId, this.getId(),
          Constants.DOCUMENTS_PRODUCT_CATALOG);
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
   * @throws SQLException Description of the Exception
   */
  public void buildOptions(Connection db) throws SQLException {
    optionList = new ProductOptionList();
    optionList.setProductId(this.id);
    optionList.setEnabled(this.getBuildActiveOptions());
    optionList.setBuildConfigDetails(true);
    optionList.setBuildResources(true);
    optionList.buildList(db);
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildCategories(Connection db) throws SQLException {
    categoryList = new ProductCategoryList();
    categoryList.setProductId(this.id);
    categoryList.buildList(db);
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildPriceList(Connection db) throws SQLException {
    priceList = new ProductCatalogPricingList();
    priceList.setProductId(this.getId());
    priceList.buildList(db);
  }

  /**
   * Adds a feature to the Category attribute of the ProductCatalog object
   *
   * @param db         The feature to be added to the Category attribute
   * @param categoryId The feature to be added to the Category attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int addCategoryMapping(Connection db, int categoryId)
      throws SQLException {
    int result = -1;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      // Insert the new mapping
      if (categoryId > -1) {
        int i = 0;
        int seqId = DatabaseUtils.getNextSeq(db,
            "product_catalog_category_map_id_seq");
        PreparedStatement pst = db
            .prepareStatement("INSERT INTO product_catalog_category_map ("
                + (seqId > -1 ? "id, " : "")
                + "product_id, category_id) "
                + "VALUES ("
                + (seqId > -1 ? "?, " : "") + "?, ?)");
        if (seqId > -1) {
          pst.setInt(++i, seqId);
        }
        pst.setInt(++i, this.getId());
        pst.setInt(++i, categoryId);
        result = pst.executeUpdate();
        pst.close();
      }
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
    return result;
  }

  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param categoryId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void removeCategoryMapping(Connection db, int categoryId)
      throws SQLException {
    int i = 0;
    PreparedStatement pst = db
        .prepareStatement(" DELETE FROM product_catalog_category_map "
            + " WHERE product_id = ? AND category_id = ? ");
    pst.setInt(++i, this.getId());
    pst.setInt(++i, categoryId);
    pst.execute();
    pst.close();
  }

  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    // product_catalog table
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
    this.setEstimatedShipTime(DatabaseUtils.getInt(rs,
        "estimated_ship_time"));
    this
        .setThumbnailImageId(DatabaseUtils.getInt(rs,
            "thumbnail_image_id"));
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
    this.setTrashedDate(rs.getTimestamp("trashed_date"));
    this.setActive(rs.getBoolean("active"));
    this.setStatusId(DatabaseUtils.getInt(rs, "status_id"));
    this.setImportId(DatabaseUtils.getInt(rs, "import_id"));
    this.setComments(rs.getString("comments"));

    // product_catalog parent table
    this.setParentName(rs.getString("parent_name"));

    // product_catalog_type table
    this.setTypeName(rs.getString("type_name"));

    this.setFormatName(rs.getString("format_name"));
    this.setShippingName(rs.getString("shipping_name"));
    this.setShippingTimeName(rs.getString("shiptime_name"));
  }

  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param baseFilePath Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath)
      throws SQLException {
    boolean result = false;
    if (this.getId() == -1) {
      throw new SQLException("Product Catalog ID not specified.");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      /*
          * Delete any documents FileItemList fileList = new FileItemList();
          * fileList.setLinkModuleId(Constants.DOCUMENTS_PRODUCT_CATEGORY);
          * fileList.setLinkItemId(this.getId()); fileList.buildList(db);
          * fileList.delete(db, baseFilePath); fileList = null; Delete any
          * folder data CustomFieldRecordList folderList = new
          * CustomFieldRecordList();
          * folderList.setLinkModuleId(Constants.FOLDERS_PRODUCT_CATEGORY);
          * folderList.setLinkItemId(this.getId()); folderList.buildList(db);
          * folderList.delete(db); folderList = null;
          */
      // delete all the dependencies
      // delete all records that contain product_id in the
      // product_catalog_category_mapping
      int i = 0;
      PreparedStatement pst = db
          .prepareStatement("DELETE FROM product_catalog_category_map "
              + "WHERE product_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      // prepare a list of this product's options
      ProductOptionList optionList = new ProductOptionList();
      optionList.setProductId(this.getId());
      optionList.buildList(db);

      // delete all the records that contain product_id in the
      // product_catalog_option_map
      i = 0;
      pst = db.prepareStatement("DELETE FROM product_option_map "
          + "WHERE product_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      // delete all the product options
      optionList.delete(db);

      // delete all the records that contain product_id in the
      // product_catalog_pricing
      i = 0;
      pst = db.prepareStatement("DELETE FROM product_catalog_pricing "
          + "WHERE product_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      //Reset references in access logs
      pst = db.prepareStatement(
          "UPDATE web_product_access_log " +
              " SET product_id = ? " +
              " WHERE product_id = ? ");
      i = 0;
      DatabaseUtils.setInt(pst, ++i, -1);
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          "UPDATE web_product_email_log " +
              " SET product_id = ? " +
              " WHERE product_id = ? ");
      i = 0;
      DatabaseUtils.setInt(pst, ++i, -1);
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      // delete the product from the catalog
      i = 0;
      pst = db.prepareStatement("DELETE FROM product_catalog "
          + "WHERE product_id = ? ");
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
   * Gets the timeZoneParams attribute of the ProductCatalog class
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
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "product_catalog_product_id_seq");
      StringBuffer sql = new StringBuffer();
      sql
          .append(" INSERT INTO product_catalog (parent_id, "
              + " product_name, abbreviation, short_description, "
              + " long_description, type_id, special_notes, sku, in_stock, format_id, "
              + " shipping_id, estimated_ship_time , thumbnail_image_id, "
              + " small_image_id, large_image_id, list_order, "
              + " enteredby,");
      if (id > -1) {
        sql.append("product_id, ");
      }
      if (importId > -1) {
        sql.append("import_id, ");
      }
      if (statusId > -1) {
        sql.append("status_id, ");
      }
      if (comments != null) {
        sql.append("comments, ");
      }
      sql.append(" entered, ");
      sql.append(" modifiedBy, ");
      sql.append(" modified, ");
      sql.append("start_date, expiration_date, enabled, " + DatabaseUtils.addQuotes(db, "active") + ")");
      sql
          .append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?,");
      }
      if (importId > -1) {
        sql.append("?,");
      }
      if (statusId > -1) {
        sql.append("?,");
      }
      if (comments != null) {
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
        sql.append( DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ?, ?, ? )");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, this.getParentId());
      pst.setString(++i, this.getName());
      pst.setString(++i, this.getAbbreviation());
      pst.setString(++i, this.getShortDescription());
      pst.setString(++i, this.getLongDescription());
      DatabaseUtils.setInt(pst, ++i, this.getTypeId());
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
      if (comments != null) {
        pst.setString(++i, comments);
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
      pst.setBoolean(++i, this.getActive());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "product_catalog_product_id_seq",
          id);
      // Add a category mapping
      if (this.getCategoryId() > 0) {
        this.addCategoryMapping(db, this.getCategoryId());
      }
      Iterator j = categoryList.iterator();
      int thisCategoryId = -1;
      while (j.hasNext()) {
        ProductCategory thisCategory = (ProductCategory) j.next();
        if (thisCategory.getId() == -1) {
          int count = thisCategory.getCategoryCountByName(db,
              thisCategory.getName());
          if (count <= 0) {
            thisCategory.process(db, -1, this.enteredBy,
                this.modifiedBy);
            thisCategoryId = DatabaseUtils.getCurrVal(db,
                "product_category_category_id_seq", id);
          }
        }
        if (thisCategoryId < 0) {
          if (thisCategory.getId() < 0) {
            thisCategoryId = ProductCategory.getCategoryById(thisCategory.getName(), importId, db);
          } else {
            thisCategoryId = thisCategory.getId();
          }
        }
        this.addCategoryMapping(db, thisCategoryId);
      }

      // Insert the prices if there are any
      Iterator iprice = priceList.iterator();
      while (iprice.hasNext()) {
        ProductCatalogPricing thisPrice = (ProductCatalogPricing) iprice.next();
        thisPrice.setEnabled(true);
        thisPrice.process(db, id, this.getEnteredBy(), this.getModifiedBy());
      }
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
   * @param db          Description of the Parameter
   * @param cloneSource Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insertClone(Connection db, ProductCatalog cloneSource)
      throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      // Insert this clone product
      this.insert(db);
      // Clone the product prices
      Iterator i = cloneSource.getPriceList().iterator();
      while (i.hasNext()) {
        ProductCatalogPricing thisPrice = (ProductCatalogPricing) i
            .next();
        thisPrice.setId(-1);
        thisPrice.setProductId(this.getId());
        thisPrice.insert(db);
      }
      // Clone the product options
      Iterator j = cloneSource.getOptionList().iterator();
      while (j.hasNext()) {
        ProductOption thisOption = (ProductOption) j.next();
        thisOption.setProductId(this.getId());
        thisOption.insertClone(db);
      }
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
    sql.append("UPDATE product_catalog " + "SET " + "parent_id = ?, "
        + "product_name = ?, " + "abbreviation = ?, "
        + "short_description = ?, " + "long_description = ?, "
        + "type_id = ?, " + "special_notes = ?, " + "sku = ?, "
        + "in_stock = ?, " + "format_id = ?, " + "shipping_id = ?, "
        + "estimated_ship_time = ?, thumbnail_image_id = ?, "
        + "small_image_id = ?, " + "large_image_id = ?, "
        + "list_order = ?, " + "start_date = ?, "
        + "expiration_date = ?, " + "enabled = ?, "
        + "trashed_date = ?, " + DatabaseUtils.addQuotes(db, "active") + " = ?, "
        + "modifiedby = ?, " + "modified = "
        + DatabaseUtils.getCurrentTimestamp(db) + " ");
    sql.append("WHERE product_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getAbbreviation());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getLongDescription());
    DatabaseUtils.setInt(pst, ++i, this.getTypeId());
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
    DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
    pst.setBoolean(++i, this.getEnabled());
    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
    pst.setBoolean(++i, this.getActive());
    DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int updateImages(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE product_catalog " + "SET "
        + "thumbnail_image_id = ?," + "small_image_id = ?, "
        + "large_image_id = ?, " + "modifiedby = ?, " + "modified = "
        + DatabaseUtils.getCurrentTimestamp(db) + " ");
    sql.append("WHERE product_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getThumbnailImageId());
    DatabaseUtils.setInt(pst, ++i, this.getSmallImageId());
    DatabaseUtils.setInt(pst, ++i, this.getLargeImageId());
    DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param toTrash   Description of the Parameter
   * @param tmpUserId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId)
      throws SQLException {
    int resultCount = -1;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE product_catalog " + "SET trashed_date = ? , "
        + "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , "
        + "modifiedby = ? " + "WHERE product_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    if (toTrash) {
      DatabaseUtils.setTimestamp(pst, ++i, new Timestamp(System
          .currentTimeMillis()));
    } else {
      DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
    }
    DatabaseUtils.setInt(pst, ++i, tmpUserId);
    DatabaseUtils.setInt(pst, ++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    
    TicketList ticketList = new TicketList();
    ticketList.setProductId(this.getId());
    // //NEEDS SITE COMPLIANCE
    if (!toTrash) {
      ticketList.setIncludeOnlyTrashed(true);
    }
    ticketList.buildList(db);
    ticketList.updateStatus(db, toTrash, tmpUserId);

    // Fetching ServiceContract that have this product and trashing (or
    // restoring) them
    ServiceContractProductList serviceContractProductList = new ServiceContractProductList();
    serviceContractProductList.setProductId(this.getId());
    serviceContractProductList.buildList(db);
    Iterator itr = serviceContractProductList.iterator();
    while (itr.hasNext()) {
      ServiceContractProduct serviceContractProduct = (ServiceContractProduct) itr
          .next();
      int tmpContractId = serviceContractProduct.getContractId();
      ServiceContract tmpServiceContract = new ServiceContract(db,
          tmpContractId);
      tmpServiceContract.updateStatus(db, toTrash, tmpUserId);
    }

    // Fetching quotes that have this product and trashing (or restoring)
    // them
    QuoteProductList quoteProductList = new QuoteProductList();
    quoteProductList.setProductId(this.getId());
    quoteProductList.buildList(db);
    itr = quoteProductList.iterator();
    while (itr.hasNext()) {
      QuoteProduct quoteProduct = (QuoteProduct) itr.next();
      int tmpQuoteId = quoteProduct.getQuoteId();
      Quote tmpQuote = new Quote(db, tmpQuoteId);
      tmpQuote.updateStatus(db, toTrash, tmpUserId);
    }

    return true;
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
    // This method checks all the mappings for any product_catalog with the
    // current id
    // The currently known mappings are product_catalog_option_map,
    // product_catalog_category_map
    if (this.getId() == -1) {
      throw new SQLException("Product Catalog ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;

    // Check for the current product mappings in product_option_map
    pst = db.prepareStatement("SELECT count(*) AS optioncount "
        + "FROM product_option_map " + "WHERE product_id = ? ");
    pst.setInt(++i, this.getId());
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis();
      logger.debug(pst.toString());
    }
    rs = pst.executeQuery();
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis() - milies;
      logger.debug(String.valueOf(milies) + " ms");
    }
    if (rs.next()) {
      int catalogCount = rs.getInt("optioncount");
      if (catalogCount != 0) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("numberOfCatalogOptionMappings");
        thisDependency.setCount(catalogCount);
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }
    }
    rs.close();
    pst.close();

    // Check for the current product mappings in service_contract_product
    i = 0;
    pst = db.prepareStatement("SELECT count(*) AS catalogcount "
        + "FROM service_contract_products "
        + "WHERE link_product_id = ? ");
    pst.setInt(++i, this.getId());
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis();
      logger.debug(pst.toString());
    }
    rs = pst.executeQuery();
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis() - milies;
      logger.debug(String.valueOf(milies) + " ms");
    }
    if (rs.next()) {
      int catalogCount = rs.getInt("catalogcount");
      if (catalogCount != 0) {
        Dependency thisDependency = new Dependency();
        thisDependency
            .setName("numberOfServiceContractsMappedToTheProduct");
        thisDependency.setCount(catalogCount);
        // NOTE:set to true for dataline since
        // removal of dependencies has not been provided
        thisDependency.setCanDelete(false);
        dependencyList.add(thisDependency);
      }
    }
    rs.close();
    pst.close();

    // Check for the current product mappings in ticket
    i = 0;
    pst = db.prepareStatement("SELECT count(*) AS catalogcount "
        + "FROM ticket " + "WHERE product_id = ? ");
    pst.setInt(++i, this.getId());
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis();
      logger.debug(pst.toString());
    }
    rs = pst.executeQuery();
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis() - milies;
      logger.debug(String.valueOf(milies) + " ms");
    }
    if (rs.next()) {
      int catalogCount = rs.getInt("catalogcount");
      if (catalogCount != 0) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("numberOfTicketsMappedToTheProduct");
        thisDependency.setCount(catalogCount);
        // NOTE:set to true for dataline since
        // removal of dependencies has not been provided
        thisDependency.setCanDelete(false);
        dependencyList.add(thisDependency);
      }
    }
    rs.close();
    pst.close();

    // Check for the current quote product links
    i = 0;
    pst = db.prepareStatement("SELECT count(*) AS catalogcount "
        + "FROM quote_product " + "WHERE product_id = ? ");
    pst.setInt(++i, this.getId());
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis();
      logger.debug(pst.toString());
    }
    rs = pst.executeQuery();
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis() - milies;
      logger.debug(String.valueOf(milies) + " ms");
    }
    if (rs.next()) {
      int catalogCount = rs.getInt("catalogcount");
      if (catalogCount != 0) {
        Dependency thisDependency = new Dependency();
        thisDependency
            .setName("numberOfQuoteProductsMappedToTheProduct");
        thisDependency.setCount(catalogCount);
        thisDependency.setCanDelete(false);
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
   * @param db          Description of the Parameter
   * @param dimensions  Description of the Parameter
   * @param categoryId  Description of the Parameter
   * @param productName Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void getProductCatalogByName(Connection db, String productName,
                                      String dimensions, int categoryId) throws SQLException {
    int i = 0;
    StringBuffer sql = new StringBuffer(
        " SELECT pctlg.product_id AS product_id "
            + " FROM product_catalog pctlg "
            + " LEFT JOIN product_catalog_category_map pctlgmap "
            + " ON ( pctlg.product_id = pctlgmap.product_id ) "
            + " LEFT JOIN product_category pctgy "
            + " ON ( pctlgmap.category_id = pctgy.category_id ) "
            + " WHERE pctgy.category_id = ? ");
    if (dimensions != null && !"".equals(dimensions)) {
      sql.append(" AND pctlg.short_description = ? ");
    } else {
      sql.append(" AND pctlg.product_name = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, categoryId);
    if (dimensions != null && !"".equals(dimensions)) {
      pst.setString(++i, dimensions);
    } else {
      pst.setString(++i, productName);
    }
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis();
      logger.debug(pst.toString());
    }
    ResultSet rs = pst.executeQuery();
    if (System.getProperty("DEBUG") != null) {
      milies = System.currentTimeMillis() - milies;
      logger.debug(String.valueOf(milies) + " ms");
    }
    if (rs.next()) {
      this.setBuildOptions(true);
      this.queryRecord(db, rs.getInt("product_id"));
    }
    rs.close();
    pst.close();
  }

  /**
   * Gets the productCatalogPricing attribute of the ProductCatalog object
   *
   * @param db Description of the Parameter
   * @return The productCatalogPricing value
   * @throws SQLException Description of the Exception
   */
  public ProductCatalogPricing getActivePrice(Connection db)
      throws SQLException {
    if (this.getId() < 0) {
      throw new SQLException("Product ID not specified");
    }
    ProductCatalogPricingList priceList = new ProductCatalogPricingList();
    priceList.setProductId(this.getId());
    priceList.setEnabled(Constants.TRUE);
    priceList.setIsValidNow(Constants.TRUE);
    priceList.buildList(db);
    if (priceList.size() == 0) {
      // this.setEnabled(false);
      // this.update(db);
      return null;
    }
    return (ProductCatalogPricing) priceList.get(0);
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildActivePrice(Connection db) throws SQLException {
    this.setActivePrice((ProductCatalogPricing) this.getActivePrice(db));
  }

  /**
   * Description of the Method
   */
  public void resetBaseInfo() {
    this.name = null;
    this.id = -1;
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
      String sql = "UPDATE product_catalog " + "SET status_id = ?, " +
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

  public String determineActualCategoryName(Connection db, int ctgyId) throws SQLException {
    // Product categories is a tree of categories and products can exist
    // at any level in the category list. Hence a product needs to know
    // exactly
    // the path from the root of the tree which is the category trail
    StringBuffer sb = new StringBuffer();
    if (ctgyId != -1) {
      ProductCategory category = new ProductCategory(db, ctgyId);
      sb.append(category.getName());
      int ctgyParentId = category.getParentId();
      while (ctgyParentId != -1) {
        ProductCategory parent = new ProductCategory(db, ctgyParentId);
        sb.insert(0, parent.getName() + ", ");
        ctgyParentId = parent.getParentId();
      }
    }
    return sb.toString();
  }

  /**
   * Gets the numberParams attribute of the ProductCatalog class
   *
   * @return The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("priceAmount");
    thisList.add("msrpAmount");
    thisList.add("costAmount");
    return thisList;
  }

  public boolean hasPricing() {
    if (this.getMsrpAmount() > 0
        || this.getPriceAmount() > 0
        || this.getCostAmount() > 0) {
      return true;
    }
    return false;
  }
}
