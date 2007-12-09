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
package org.aspcfs.modules.assets.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Assets are h/w, s/w or systems that are serviced under the service contract.
 *
 * @author     kbhoopal
 * @created    March 16, 2004
 * @version    $Id$
 */
public class Asset extends GenericBean {

  private int id = -1;
  private int contractId = -1;
  private String serviceContractNumber = null;
  private int orgId = -1;
  private java.sql.Timestamp dateListed = null;
  private String assetTag = null;
  private int status = -1;
  private String location = null;
  private int level1 = -1;
  private int level2 = -1;
  private int level3 = -1;
  private String serialNumber = null;
  private String modelVersion = null;
  private String description = null;
  private java.sql.Timestamp expirationDate = null;
  private String inclusions = null;
  private String exclusions = null;
  private java.sql.Timestamp purchaseDate = null;
  private double purchaseCost = -1;
  private String poNumber = null;
  private String purchasedFrom = null;
  private int contactId = -1;
  private String notes = null;
  private int responseTime = -1;
  private int telephoneResponseModel = -1;
  private int onsiteResponseModel = -1;
  private int emailResponseModel = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private boolean override = false;
  private String dateListedTimeZone = null;
  private String expirationDateTimeZone = null;
  private String purchaseDateTimeZone = null;
  private String statusName = null;
  private java.sql.Timestamp trashedDate = null;
  private int parentId = -1;
  private boolean buildMaterials = false;
  private AssetMaterialList materials = new AssetMaterialList();
  private boolean buildCompleteHierarchy = false;
  private AssetList childList = null;
  private AssetList parentList = null;
  private boolean buildCompleteParentList = false;
  private boolean includeMe = false;
  private boolean buildChildList = false;
  private int vendorCode = -1;
  private int manufacturerCode = -1;


  /**
   *  Constructor for the Asset object
   */
  public Asset() {
    errors.clear();
  }


  /**
   *  Constructor for the Asset object
   *
   * @param  db                Description of the Parameter
   * @param  tmpId             Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public Asset(Connection db, String tmpId) throws SQLException {
    errors.clear();
    queryRecord(db, Integer.parseInt(tmpId));
  }


  /**
   *  Constructor for the Asset object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public Asset(ResultSet rs) throws SQLException {
    errors.clear();
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the Asset object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Asset object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contractId attribute of the Asset object
   *
   * @param  tmp  The new contractId value
   */
  public void setContractId(int tmp) {
    this.contractId = tmp;
  }


  /**
   *  Sets the contractId attribute of the Asset object
   *
   * @param  tmp  The new contractId value
   */
  public void setContractId(String tmp) {
    this.contractId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the serviceContractNumber attribute of the Asset object
   *
   * @param  tmp  The new serviceContractNumber value
   */
  public void setServiceContractNumber(String tmp) {
    this.serviceContractNumber = tmp;
  }


  /**
   *  Sets the orgId attribute of the Asset object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the Asset object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the dateListed attribute of the Asset object
   *
   * @param  tmp  The new dateListed value
   */
  public void setDateListed(java.sql.Timestamp tmp) {
    this.dateListed = tmp;
  }


  /**
   *  Sets the dateListed attribute of the Asset object
   *
   * @param  tmp  The new dateListed value
   */
  public void setDateListed(String tmp) {
    this.dateListed = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the assetTag attribute of the Asset object
   *
   * @param  tmp  The new assetTag value
   */
  public void setAssetTag(String tmp) {
    this.assetTag = tmp;
  }


  /**
   *  Sets the status attribute of the Asset object
   *
   * @param  tmp  The new status value
   */
  public void setStatus(int tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the status attribute of the Asset object
   *
   * @param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = Integer.parseInt(tmp);
  }


  /**
   *  Sets the location attribute of the Asset object
   *
   * @param  tmp  The new location value
   */
  public void setLocation(String tmp) {
    this.location = tmp;
  }


  /**
   *  Sets the level1 attribute of the Asset object
   *
   * @param  tmp  The new level1 value
   */
  public void setLevel1(int tmp) {
    this.level1 = tmp;
  }


  /**
   *  Sets the level1 attribute of the Asset object
   *
   * @param  tmp  The new level1 value
   */
  public void setLevel1(String tmp) {
    this.level1 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the level2 attribute of the Asset object
   *
   * @param  tmp  The new level2 value
   */
  public void setLevel2(int tmp) {
    this.level2 = tmp;
  }


  /**
   *  Sets the level2 attribute of the Asset object
   *
   * @param  tmp  The new level2 value
   */
  public void setLevel2(String tmp) {
    this.level2 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the level3 attribute of the Asset object
   *
   * @param  tmp  The new level3 value
   */
  public void setLevel3(int tmp) {
    this.level3 = tmp;
  }


  /**
   *  Sets the level3 attribute of the Asset object
   *
   * @param  tmp  The new level3 value
   */
  public void setLevel3(String tmp) {
    this.level3 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the serialNumber attribute of the Asset object
   *
   * @param  tmp  The new serialNumber value
   */
  public void setSerialNumber(String tmp) {
    this.serialNumber = tmp;
  }


  /**
   *  Sets the modelVersion attribute of the Asset object
   *
   * @param  tmp  The new modelVersion value
   */
  public void setModelVersion(String tmp) {
    this.modelVersion = tmp;
  }


  /**
   *  Sets the description attribute of the Asset object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the expirationDate attribute of the Asset object
   *
   * @param  tmp  The new expirationDate value
   */
  public void setExpirationDate(java.sql.Timestamp tmp) {
    this.expirationDate = tmp;
  }


  /**
   *  Sets the expirationDate attribute of the Asset object
   *
   * @param  tmp  The new expirationDate value
   */
  public void setExpirationDate(String tmp) {
    this.expirationDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the inclusions attribute of the Asset object
   *
   * @param  tmp  The new inclusions value
   */
  public void setInclusions(String tmp) {
    this.inclusions = tmp;
  }


  /**
   *  Sets the exclusions attribute of the Asset object
   *
   * @param  tmp  The new exclusions value
   */
  public void setExclusions(String tmp) {
    this.exclusions = tmp;
  }


  /**
   *  Sets the purchaseDate attribute of the Asset object
   *
   * @param  tmp  The new purchaseDate value
   */
  public void setPurchaseDate(java.sql.Timestamp tmp) {
    this.purchaseDate = tmp;
  }


  /**
   *  Sets the purchaseDate attribute of the Asset object
   *
   * @param  tmp  The new purchaseDate value
   */
  public void setPurchaseDate(String tmp) {
    this.purchaseDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the purchaseCost attribute of the Asset object
   *
   * @param  tmp  The new purchaseCost value
   */
  public void setPurchaseCost(String tmp) {
    this.purchaseCost = Double.parseDouble(tmp);
  }


  /**
   *  Sets the purchaseCost attribute of the Asset object
   *
   * @param  tmp  The new purchaseCost value
   */
  public void setPurchaseCost(double tmp) {
    this.purchaseCost = tmp;
  }


  /**
   *  Sets the poNumber attribute of the Asset object
   *
   * @param  tmp  The new poNumber value
   */
  public void setPoNumber(String tmp) {
    this.poNumber = tmp;
  }


  /**
   *  Sets the purchasedFrom attribute of the Asset object
   *
   * @param  tmp  The new purchasedFrom value
   */
  public void setPurchasedFrom(String tmp) {
    this.purchasedFrom = tmp;
  }


  /**
   *  Sets the contactId attribute of the Asset object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the contactId attribute of the Asset object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the notes attribute of the Asset object
   *
   * @param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the responseTime attribute of the Asset object
   *
   * @param  tmp  The new responseTime value
   */
  public void setResponseTime(int tmp) {
    this.responseTime = tmp;
  }


  /**
   *  Sets the responseTime attribute of the Asset object
   *
   * @param  tmp  The new responseTime value
   */
  public void setResponseTime(String tmp) {
    this.responseTime = Integer.parseInt(tmp);
  }


  /**
   *  Sets the telephoneResponseModel attribute of the Asset object
   *
   * @param  tmp  The new telephoneResponseModel value
   */
  public void setTelephoneResponseModel(int tmp) {
    this.telephoneResponseModel = tmp;
  }


  /**
   *  Sets the telephoneResponseModel attribute of the Asset object
   *
   * @param  tmp  The new telephoneResponseModel value
   */
  public void setTelephoneResponseModel(String tmp) {
    this.telephoneResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the onsiteResponseModel attribute of the Asset object
   *
   * @param  tmp  The new onsiteResponseModel value
   */
  public void setOnsiteResponseModel(int tmp) {
    this.onsiteResponseModel = tmp;
  }


  /**
   *  Sets the onsiteResponseModel attribute of the Asset object
   *
   * @param  tmp  The new onsiteResponseModel value
   */
  public void setOnsiteResponseModel(String tmp) {
    this.onsiteResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the emailResponseModel attribute of the Asset object
   *
   * @param  tmp  The new emailResponseModel value
   */
  public void setEmailResponseModel(int tmp) {
    this.emailResponseModel = tmp;
  }


  /**
   *  Sets the emailResponseModel attribute of the Asset object
   *
   * @param  tmp  The new emailResponseModel value
   */
  public void setEmailResponseModel(String tmp) {
    this.emailResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the Asset object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Asset object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Asset object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Asset object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Asset object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Asset object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Asset object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Asset object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the Asset object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the Asset object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the override attribute of the Asset object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the Asset object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the dateListedTimeZone attribute of the Asset object
   *
   * @param  tmp  The new dateListedTimeZone value
   */
  public void setDateListedTimeZone(String tmp) {
    this.dateListedTimeZone = tmp;
  }


  /**
   *  Sets the expirationDateTimeZone attribute of the Asset object
   *
   * @param  tmp  The new expirationDateTimeZone value
   */
  public void setExpirationDateTimeZone(String tmp) {
    this.expirationDateTimeZone = tmp;
  }


  /**
   *  Sets the purchaseDateTimeZone attribute of the Asset object
   *
   * @param  tmp  The new purchaseDateTimeZone value
   */
  public void setPurchaseDateTimeZone(String tmp) {
    this.purchaseDateTimeZone = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the Asset object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the Asset object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the trashedDate attribute of the Asset object
   *
   * @return    The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Gets the trashed attribute of the Asset object
   *
   * @return    The trashed value
   */
  public boolean isTrashed() {
    return (trashedDate != null);
  }


  /**
   *  Sets the vendorCode attribute of the Asset object
   *
   *@param  tmp  The new vendorCode value
   */
  public void setVendorCode(int tmp) {
    this.vendorCode = tmp;
  }


  /**
   *  Sets the vendorCode attribute of the Asset object
   *
   *@param  tmp  The new vendorCode value
   */
  public void setVendorCode(String tmp) {
    this.vendorCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the manufacturerCode attribute of the Asset object
   *
   *@param  tmp  The new manufacturerCode value
   */
  public void setManufacturerCode(int tmp) {
    this.manufacturerCode = tmp;
  }


  /**
   *  Sets the manufacturerCode attribute of the Asset object
   *
   *@param  tmp  The new manufacturerCode value
   */
  public void setManufacturerCode(String tmp) {
    this.manufacturerCode = Integer.parseInt(tmp);
  }


  /**
   *  Gets the dateListedTimeZone attribute of the Asset object
   *
   * @return    The dateListedTimeZone value
   */
  public String getDateListedTimeZone() {
    return dateListedTimeZone;
  }


  /**
   *  Gets the expirationDateTimeZone attribute of the Asset object
   *
   * @return    The expirationDateTimeZone value
   */
  public String getExpirationDateTimeZone() {
    return expirationDateTimeZone;
  }


  /**
   *  Gets the purchaseDateTimeZone attribute of the Asset object
   *
   * @return    The purchaseDateTimeZone value
   */
  public String getPurchaseDateTimeZone() {
    return purchaseDateTimeZone;
  }


  /**
   *  Gets the id attribute of the Asset object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the contractId attribute of the Asset object
   *
   * @return    The contractId value
   */
  public int getContractId() {
    return contractId;
  }


  /**
   *  Gets the serviceContractNumber attribute of the Asset object
   *
   * @return    The serviceContractNumber value
   */
  public String getServiceContractNumber() {
    return serviceContractNumber;
  }


  /**
   *  Gets the orgId attribute of the Asset object
   *
   * @return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the dateListed attribute of the Asset object
   *
   * @return    The dateListed value
   */
  public java.sql.Timestamp getDateListed() {
    return dateListed;
  }


  /**
   *  Gets the assetTag attribute of the Asset object
   *
   * @return    The assetTag value
   */
  public String getAssetTag() {
    return assetTag;
  }


  /**
   *  Gets the status attribute of the Asset object
   *
   * @return    The status value
   */
  public int getStatus() {
    return status;
  }


  /**
   *  Gets the location attribute of the Asset object
   *
   * @return    The location value
   */
  public String getLocation() {
    return location;
  }


  /**
   *  Gets the level1 attribute of the Asset object
   *
   * @return    The level1 value
   */
  public int getLevel1() {
    return level1;
  }


  /**
   *  Gets the level2 attribute of the Asset object
   *
   * @return    The level2 value
   */
  public int getLevel2() {
    return level2;
  }


  /**
   *  Gets the level3 attribute of the Asset object
   *
   * @return    The level3 value
   */
  public int getLevel3() {
    return level3;
  }


  /**
   *  Gets the serialNumber attribute of the Asset object
   *
   * @return    The serialNumber value
   */
  public String getSerialNumber() {
    return serialNumber;
  }


  /**
   *  Gets the modelVersion attribute of the Asset object
   *
   * @return    The modelVersion value
   */
  public String getModelVersion() {
    return modelVersion;
  }


  /**
   *  Gets the description attribute of the Asset object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the expirationDate attribute of the Asset object
   *
   * @return    The expirationDate value
   */
  public java.sql.Timestamp getExpirationDate() {
    return expirationDate;
  }


  /**
   *  Gets the inclusions attribute of the Asset object
   *
   * @return    The inclusions value
   */
  public String getInclusions() {
    return inclusions;
  }


  /**
   *  Gets the exclusions attribute of the Asset object
   *
   * @return    The exclusions value
   */
  public String getExclusions() {
    return exclusions;
  }


  /**
   *  Gets the purchaseDate attribute of the Asset object
   *
   * @return    The purchaseDate value
   */
  public java.sql.Timestamp getPurchaseDate() {
    return purchaseDate;
  }


  /**
   *  Gets the purchaseCost attribute of the Asset object
   *
   * @return    The purchaseCost value
   */
  public double getPurchaseCost() {
    return purchaseCost;
  }


  /**
   *  Gets the poNumber attribute of the Asset object
   *
   * @return    The poNumber value
   */
  public String getPoNumber() {
    return poNumber;
  }


  /**
   *  Gets the purchasedFrom attribute of the Asset object
   *
   * @return    The purchasedFrom value
   */
  public String getPurchasedFrom() {
    return purchasedFrom;
  }


  /**
   *  Gets the contactId attribute of the Asset object
   *
   * @return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the notes attribute of the Asset object
   *
   * @return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the responseTime attribute of the Asset object
   *
   * @return    The responseTime value
   */
  public int getResponseTime() {
    return responseTime;
  }


  /**
   *  Gets the telephoneResponseModel attribute of the Asset object
   *
   * @return    The telephoneResponseModel value
   */
  public int getTelephoneResponseModel() {
    return telephoneResponseModel;
  }


  /**
   *  Gets the onsiteResponseModel attribute of the Asset object
   *
   * @return    The onsiteResponseModel value
   */
  public int getOnsiteResponseModel() {
    return onsiteResponseModel;
  }


  /**
   *  Gets the emailResponseModel attribute of the Asset object
   *
   * @return    The emailResponseModel value
   */
  public int getEmailResponseModel() {
    return emailResponseModel;
  }


  /**
   *  Gets the entered attribute of the Asset object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the Asset object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the Asset object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the Asset object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the enabled attribute of the Asset object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the vendorCode attribute of the Asset object
   *
   *@return    The vendorCode value
   */
  public int getVendorCode() {
    return vendorCode;
  }


  /**
   *  Gets the manufacturerCode attribute of the Asset object
   *
   *@return    The manufacturerCode value
   */
  public int getManufacturerCode() {
    return manufacturerCode;
  }


  /**
   *  Gets the override attribute of the Asset object
   *
   * @return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Gets the statusName attribute of the Asset object
   *
   * @return    The statusName value
   */
  public String getStatusName() {
    return statusName;
  }


  /**
   *  Sets the statusName attribute of the Asset object
   *
   * @param  tmp  The new statusName value
   */
  public void setStatusName(String tmp) {
    this.statusName = tmp;
  }


  /**
   *  Gets the buildMaterials attribute of the Asset object
   *
   * @return    The buildMaterials value
   */
  public boolean getBuildMaterials() {
    return buildMaterials;
  }


  /**
   *  Sets the buildMaterials attribute of the Asset object
   *
   * @param  tmp  The new buildMaterials value
   */
  public void setBuildMaterials(boolean tmp) {
    this.buildMaterials = tmp;
  }


  /**
   *  Sets the buildMaterials attribute of the Asset object
   *
   * @param  tmp  The new buildMaterials value
   */
  public void setBuildMaterials(String tmp) {
    this.buildMaterials = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the materials attribute of the Asset object
   *
   * @return    The materials value
   */
  public AssetMaterialList getMaterials() {
    return materials;
  }


  /**
   *  Sets the materials attribute of the Asset object
   *
   * @param  tmp  The new materials value
   */
  public void setMaterials(AssetMaterialList tmp) {
    this.materials = tmp;
  }


  /**
   *  Gets the parentId attribute of the Asset object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Sets the parentId attribute of the Asset object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the Asset object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildCompleteHierarchy attribute of the Asset object
   *
   * @return    The buildCompleteHierarchy value
   */
  public boolean getBuildCompleteHierarchy() {
    return buildCompleteHierarchy;
  }


  /**
   *  Sets the buildCompleteHierarchy attribute of the Asset object
   *
   * @param  tmp  The new buildCompleteHierarchy value
   */
  public void setBuildCompleteHierarchy(boolean tmp) {
    this.buildCompleteHierarchy = tmp;
  }


  /**
   *  Sets the buildCompleteHierarchy attribute of the Asset object
   *
   * @param  tmp  The new buildCompleteHierarchy value
   */
  public void setBuildCompleteHierarchy(String tmp) {
    this.buildCompleteHierarchy = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the childList attribute of the Asset object
   *
   * @return    The childList value
   */
  public AssetList getChildList() {
    return childList;
  }


  /**
   *  Sets the childList attribute of the Asset object
   *
   * @param  tmp  The new childList value
   */
  public void setChildList(AssetList tmp) {
    this.childList = tmp;
  }


  /**
   *  Gets the parentList attribute of the Asset object
   *
   * @return    The parentList value
   */
  public AssetList getParentList() {
    return parentList;
  }


  /**
   *  Sets the parentList attribute of the Asset object
   *
   * @param  tmp  The new parentList value
   */
  public void setParentList(AssetList tmp) {
    this.parentList = tmp;
  }


  /**
   *  Gets the buildCompleteParentList attribute of the Asset object
   *
   * @return    The buildCompleteParentList value
   */
  public boolean getBuildCompleteParentList() {
    return buildCompleteParentList;
  }


  /**
   *  Sets the buildCompleteParentList attribute of the Asset object
   *
   * @param  tmp  The new buildCompleteParentList value
   */
  public void setBuildCompleteParentList(boolean tmp) {
    this.buildCompleteParentList = tmp;
  }


  /**
   *  Sets the buildCompleteParentList attribute of the Asset object
   *
   * @param  tmp  The new buildCompleteParentList value
   */
  public void setBuildCompleteParentList(String tmp) {
    this.buildCompleteParentList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the includeMe attribute of the Asset object
   *
   * @return    The includeMe value
   */
  public boolean getIncludeMe() {
    return includeMe;
  }


  /**
   *  Sets the includeMe attribute of the Asset object
   *
   * @param  tmp  The new includeMe value
   */
  public void setIncludeMe(boolean tmp) {
    this.includeMe = tmp;
  }


  /**
   *  Sets the includeMe attribute of the Asset object
   *
   * @param  tmp  The new includeMe value
   */
  public void setIncludeMe(String tmp) {
    this.includeMe = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildChildList attribute of the Asset object
   *
   * @return    The buildChildList value
   */
  public boolean getBuildChildList() {
    return buildChildList;
  }


  /**
   *  Sets the buildChildList attribute of the Asset object
   *
   * @param  tmp  The new buildChildList value
   */
  public void setBuildChildList(boolean tmp) {
    this.buildChildList = tmp;
  }


  /**
   *  Sets the buildChildList attribute of the Asset object
   *
   * @param  tmp  The new buildChildList value
   */
  public void setBuildChildList(String tmp) {
    this.buildChildList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the properties that are TimeZone sensitive
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("dateListed");
    thisList.add("expirationDate");
    thisList.add("purchaseDate");
    return thisList;
  }


  /**
   *  Gets the numberParams attribute of the Asset class
   *
   * @return    The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("purchaseCost");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  tmpAssetId     Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int tmpAssetId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT a.*, sc.contract_number AS service_contract_number, las.description as status_name " +
        "FROM asset a LEFT JOIN service_contract sc ON (a.contract_id = sc.contract_id) " +
        "LEFT JOIN lookup_asset_status las ON (a.status = las.code) " +
        "WHERE a.asset_id = ? ");
    pst.setInt(1, tmpAssetId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (buildMaterials) {
      buildMaterials(db);
    }
    if (buildChildList) {
      buildChildren(db);
    }

    if (buildCompleteHierarchy) {
      buildCompleteHierarchy(db);
    }
    if (buildCompleteParentList) {
      AssetList temp = buildCompleteParentList(db);
    }
  }


  public void buildChildren(Connection db) throws SQLException {
    childList = new AssetList();
    childList.setParentId(this.getId());
    childList.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildCompleteHierarchy(Connection db) throws SQLException {
    childList = new AssetList();
    childList.setBuildCompleteHierarchy(this.getBuildCompleteHierarchy());
    childList.setParentId(this.getId());
    childList.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public AssetList buildCompleteParentList(Connection db) throws SQLException {
    if (parentId != -1) {
      parentList = new AssetList();
      Asset parent = new Asset();
      parent.setBuildCompleteParentList(true);
      parent.queryRecord(db, this.getParentId());
      AssetList tempList = parent.getParentList();
      if (tempList != null && tempList.size() > 0) {
        parentList.addAll(tempList);
      }
      parentList.add(parent);
    }
    if (includeMe) {
      if (parentList == null) {
        parentList = new AssetList();
      }
      parentList.add(this);
    }
    return parentList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildMaterials(Connection db) throws SQLException {
    if (materials == null) {
      materials = new AssetMaterialList();
    }
    materials.setAssetId(this.getId());
    materials.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    PreparedStatement pst = null;
    id = DatabaseUtils.getNextSeq(db, "asset_asset_id_seq");
    pst = db.prepareStatement(
        "INSERT INTO asset " +
        "(" + (id > -1 ? "asset_id, " : "") + "account_id," +
        "contract_id," +
        "parent_id, " +
        "date_listed," +
        "date_listed_timezone," +
        "asset_tag," +
        "status," +
        "location," +
        "level1," +
        "level2," +
        "level3," +
        "serial_number," +
        "model_version," +
        "description," +
        "expiration_date," +
        "expiration_date_timezone," +
        "inclusions," +
        "exclusions," +
        "purchase_date," +
        "purchase_date_timezone," +
        "purchase_cost," +
        "po_number," +
        "purchased_from," +
        "contact_id," +
        "notes," +
        "response_time," +
        "telephone_service_model," +
        "onsite_service_model," +
        "email_service_model," +
        "enteredby," +
        "modifiedby, " +
        "trashed_date, " +
        "vendor_code, " +
        "manufacturer_code )" +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, orgId);
    DatabaseUtils.setInt(pst, ++i, contractId);
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    DatabaseUtils.setTimestamp(pst, ++i, dateListed);
    pst.setString(++i, this.getDateListedTimeZone());
    pst.setString(++i, assetTag);
    DatabaseUtils.setInt(pst, ++i, status);
    pst.setString(++i, location);
    DatabaseUtils.setInt(pst, ++i, level1);
    DatabaseUtils.setInt(pst, ++i, level2);
    DatabaseUtils.setInt(pst, ++i, level3);
    pst.setString(++i, serialNumber);
    pst.setString(++i, modelVersion);
    pst.setString(++i, description);
    DatabaseUtils.setTimestamp(pst, ++i, expirationDate);
    pst.setString(++i, this.expirationDateTimeZone);
    pst.setString(++i, inclusions);
    pst.setString(++i, exclusions);
    DatabaseUtils.setTimestamp(pst, ++i, purchaseDate);
    pst.setString(++i, this.purchaseDateTimeZone);
    pst.setDouble(++i, purchaseCost);
    pst.setString(++i, poNumber);
    pst.setString(++i, purchasedFrom);
    DatabaseUtils.setInt(pst, ++i, contactId);
    pst.setString(++i, notes);
    DatabaseUtils.setInt(pst, ++i, responseTime);
    DatabaseUtils.setInt(pst, ++i, telephoneResponseModel);
    DatabaseUtils.setInt(pst, ++i, onsiteResponseModel);
    DatabaseUtils.setInt(pst, ++i, emailResponseModel);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
    DatabaseUtils.setInt(pst, ++i, vendorCode);
    DatabaseUtils.setInt(pst, ++i, manufacturerCode);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "asset_asset_id_seq", id);
    pst.close();
    if (materials != null && materials.size() > 0) {
      parseMaterials(db);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean parseMaterials(Connection db) throws SQLException {
    AssetMaterialList oldList = new AssetMaterialList();
    oldList.setAssetId(this.getId());
    oldList.buildList(db);
    if (oldList.size() > 0) {
      HashMap oldMap = oldList.createMapOfElements();
      Iterator iter = null;
      if (materials != null) {
        iter = (Iterator) materials.iterator();
        while (iter.hasNext()) {
          AssetMaterial material = (AssetMaterial) iter.next();
          // check if the mapping exists
          if (oldMap.get(new Integer(material.getCode())) != null) {
            AssetMaterial oldMaterial = (AssetMaterial) oldMap.get(new Integer(material.getCode()));
            if (oldMaterial.getQuantity() != material.getQuantity()) {
              material.setId(oldMaterial.getId());
            } else {
              //remove from the materials so that nothing is changed
              iter.remove();
            }
            // remove from oldMap so that the material is not deleted
            oldMap.remove(new Integer(material.getCode()));
          }
        }
      }
      //delete the rest of the materials in the oldMap as they are not supposed to exist
      iter = (Iterator) oldMap.keySet().iterator();
      while (iter.hasNext()) {
        AssetMaterial toRemove = (AssetMaterial) oldMap.get((Integer) iter.next());
        toRemove.delete(db);
      }
    }
    // parse existingmappings
    if (materials != null) {
      materials.setAssetId(this.getId());
      materials.parseElements(db);
    }

    //build a new list of materials to include unchanged elements
    materials = new AssetMaterialList();
    materials.setAssetId(this.getId());
    materials.buildList(db);
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE asset SET " +
        "contract_id = ? , " +
        "parent_id = ? , " +
        "date_listed = ? , " +
        "date_listed_timezone = ? , " +
        "asset_tag = ? , " +
        "status = ? , " +
        "location = ? , " +
        "level1 = ? , " +
        "level2 = ? , " +
        "level3 = ? , " +
        "serial_number = ? , " +
        "model_version = ? , " +
        "description = ? , " +
        "expiration_date = ? , " +
        "expiration_date_timezone = ? , " +
        "inclusions = ? , " +
        "exclusions = ? , " +
        "purchase_date = ? , " +
        "purchase_date_timezone = ? , " +
        "purchase_cost = ? , " +
        "po_number = ? , " +
        "purchased_from = ? , " +
        "contact_id = ? , " +
        "notes = ? , " +
        "response_time = ? , " +
        "telephone_service_model = ? , " +
        "onsite_service_model = ? , " +
        "email_service_model = ?, " +
        "trashed_date = ?, " +
        "vendor_code = ? , " +
        "manufacturer_code = ? ");
    if (!override) {
      sql.append(
          " , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE asset_id = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, contractId);
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    DatabaseUtils.setTimestamp(pst, ++i, dateListed);
    pst.setString(++i, this.dateListedTimeZone);
    pst.setString(++i, assetTag);
    DatabaseUtils.setInt(pst, ++i, status);
    pst.setString(++i, location);
    DatabaseUtils.setInt(pst, ++i, level1);
    DatabaseUtils.setInt(pst, ++i, level2);
    DatabaseUtils.setInt(pst, ++i, level3);
    pst.setString(++i, serialNumber);
    pst.setString(++i, modelVersion);
    pst.setString(++i, description);
    DatabaseUtils.setTimestamp(pst, ++i, expirationDate);
    pst.setString(++i, this.expirationDateTimeZone);
    pst.setString(++i, inclusions);
    pst.setString(++i, exclusions);
    DatabaseUtils.setTimestamp(pst, ++i, purchaseDate);
    pst.setString(++i, this.purchaseDateTimeZone);
    pst.setDouble(++i, purchaseCost);
    pst.setString(++i, poNumber);
    pst.setString(++i, purchasedFrom);
    DatabaseUtils.setInt(pst, ++i, contactId);
    pst.setString(++i, notes);
    DatabaseUtils.setInt(pst, ++i, responseTime);
    DatabaseUtils.setInt(pst, ++i, telephoneResponseModel);
    DatabaseUtils.setInt(pst, ++i, onsiteResponseModel);
    DatabaseUtils.setInt(pst, ++i, emailResponseModel);
    DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
    DatabaseUtils.setInt(pst, ++i, vendorCode);
    DatabaseUtils.setInt(pst, ++i, manufacturerCode);
    if (!override) {
      pst.setInt(++i, modifiedBy);
    }
    pst.setInt(++i, id);
    if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();
    parseMaterials(db);
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  toTrash        Description of the Parameter
   * @param  tmpUserId      Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      sql.append(
          "UPDATE asset " +
          "SET trashed_date = ? , " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
          "modifiedby = ? " +
          "WHERE asset_id = ? ");
      int i = 0;
      pst = db.prepareStatement(sql.toString());
      if (toTrash) {
        DatabaseUtils.setTimestamp(
            pst, ++i, new Timestamp(System.currentTimeMillis()));
      } else {
        DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
      }
      pst.setInt(++i, tmpUserId);
      pst.setInt(++i, this.id);
      resultCount = pst.executeUpdate();
      pst.close();
      
      this.buildCompleteHierarchy(db);
      childList.updateStatus(db, toTrash, tmpUserId);

      // Disable the account or the contact history for asset
      ContactHistory.trash(
          db, OrganizationHistory.ASSET, this.getId(), !toTrash);

      //Assets have tickets related, so delete them first
      TicketList ticketList = new TicketList();
      ticketList.setAssetId(this.getId());
      if (!toTrash) {
        ticketList.setIncludeOnlyTrashed(true);
      }
      ticketList.buildList(db);
      ticketList.updateStatus(db, toTrash, tmpUserId);

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

    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    try {
      Dependency ticDependency = new Dependency();
      ticDependency.setName("tickets");
      ticDependency.setCount(
          TicketList.retrieveRecordCount(db, Constants.ASSETS, this.getId()));
      ticDependency.setCanDelete(false);
      dependencyList.add(ticDependency);

    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   *  Seperate method to delete the dependencies and then the object. This
   *  seperation allows asset alone to be deleted when other objects are deleted
   *  (for e.g., tickets)
   *
   * @param  db             Description of the Parameter
   * @param  baseFilePath   File path of documents related to a ticket
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Asset Id not specified.");
    }
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      //Assets have tickets related, so delete them first
      TicketList ticketList = new TicketList();
      ticketList.setAssetId(this.getId());
      ticketList.setIncludeOnlyTrashed(true);
      ticketList.buildList(db);
      ticketList.delete(db, baseFilePath);
      ticketList = null;

      ticketList = new TicketList();
      ticketList.setAssetId(this.getId());
      ticketList.setIncludeOnlyTrashed(false);
      ticketList.buildList(db);
      ticketList.delete(db, baseFilePath);
      ticketList = null;

      ContactHistory.deleteObject(db, OrganizationHistory.ASSET, this.getId());

      materials = null;
      buildMaterials(db);
      if (materials != null && materials.size() > 0) {
        materials.delete(db);
      }

      //Delete the complete hierarchy of the asset
      this.buildChildren(db);
      this.getChildList().delete(db, baseFilePath);

      PreparedStatement pst = null;
      pst = db.prepareStatement(
          "DELETE FROM asset " +
          "WHERE asset_id = ? ");
      pst.setInt(1, id);
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  void buildRecord(ResultSet rs) throws SQLException {
    //asset table
    id = rs.getInt("asset_id");
    orgId = DatabaseUtils.getInt(rs, "account_id");
    contractId = DatabaseUtils.getInt(rs, "contract_id");
    dateListed = rs.getTimestamp("date_listed");
    assetTag = rs.getString("asset_tag");
    status = DatabaseUtils.getInt(rs, "status");
    location = rs.getString("location");
    level1 = DatabaseUtils.getInt(rs, "level1");
    level2 = DatabaseUtils.getInt(rs, "level2");
    level3 = DatabaseUtils.getInt(rs, "level3");
    serialNumber = rs.getString("serial_number");
    modelVersion = rs.getString("model_version");
    description = rs.getString("description");
    expirationDate = rs.getTimestamp("expiration_date");
    inclusions = rs.getString("inclusions");
    exclusions = rs.getString("exclusions");
    purchaseDate = rs.getTimestamp("purchase_date");
    poNumber = rs.getString("po_number");
    purchasedFrom = rs.getString("purchased_from");
    contactId = DatabaseUtils.getInt(rs, "contact_id");
    notes = rs.getString("notes");
    responseTime = DatabaseUtils.getInt(rs, "response_time");
    telephoneResponseModel = DatabaseUtils.getInt(
        rs, "telephone_service_model");
    onsiteResponseModel = DatabaseUtils.getInt(rs, "onsite_service_model");
    emailResponseModel = DatabaseUtils.getInt(rs, "email_service_model");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    purchaseCost = rs.getDouble("purchase_cost");
    this.dateListedTimeZone = rs.getString("date_listed_timezone");
    this.expirationDateTimeZone = rs.getString("expiration_date_timezone");
    this.purchaseDateTimeZone = rs.getString("purchase_date_timezone");
    this.trashedDate = rs.getTimestamp("trashed_date");
    this.setParentId(DatabaseUtils.getInt(rs, "parent_id"));
    vendorCode = DatabaseUtils.getInt(rs,"vendor_code");
    manufacturerCode = DatabaseUtils.getInt(rs,"manufacturer_code");
    // service contract table
    serviceContractNumber = rs.getString("service_contract_number");
    // lookup asset status table
    statusName = rs.getString("status_name");
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public String toString() {
    return this.getSerialNumber();
  }
}
