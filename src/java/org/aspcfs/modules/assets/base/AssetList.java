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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id: AssetList.java,v 1.1.6.1.2.3 2004/02/04 19:38:31 mrajkowski
 *          Exp $
 * @created January 8, 2004
 */
public class AssetList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int parentId = -1;
  private int orgId = -1;
  private int subId = -1;
  private int contactId = -1;
  private int serviceContractId = -1;
  private String serviceContractNumber = null;
  private int enteredBy = -1;
  private String serialNumber = null;
  private boolean allAssets = true;
  private boolean skipParentIdRequirement = false;

  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;
  private boolean buildCompleteHierarchy = false;


  /**
   * Constructor for the AssetList object
   */
  public AssetList() {
  }


  /**
   * Constructor for the AssetList object
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public AssetList(Connection db) throws SQLException {
  }


  /**
   * Sets the pagedListInfo attribute of the AssetList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the id attribute of the AssetList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the AssetList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the orgId attribute of the AssetList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }

  public void setSubId(int tmp) {
    this.subId = tmp;
  }

  /**
   * Sets the orgId attribute of the AssetList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }

  public void setSubId(String tmp) {
    this.subId = Integer.parseInt(tmp);
  }

  /**
   * Sets the contactId attribute of the AssetList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the AssetList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Sets the serviceContractId attribute of the AssetList object
   *
   * @param tmp The new serviceContractId value
   */
  public void setServiceContractId(int tmp) {
    this.serviceContractId = tmp;
  }


  /**
   * Sets the serviceContractId attribute of the AssetList object
   *
   * @param tmp The new serviceContractId value
   */
  public void setServiceContractId(String tmp) {
    this.serviceContractId = Integer.parseInt(tmp);
  }


  /**
   * Sets the serviceContractNumber attribute of the AssetList object
   *
   * @param tmp The new serviceContractNumber value
   */
  public void setServiceContractNumber(String tmp) {
    this.serviceContractNumber = tmp;
  }


  /**
   * Sets the serialNumber attribute of the AssetList object
   *
   * @param tmp The new serialNumber value
   */
  public void setSerialNumber(String tmp) {
    this.serialNumber = tmp;
  }


  /**
   * Sets the allAssets attribute of the AssetList object
   *
   * @param tmp The new allAssets value
   */
  public void setAllAssets(boolean tmp) {
    this.allAssets = tmp;
  }


  /**
   * Sets the enteredBy attribute of the AssetList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the AssetList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the trashedDate attribute of the AssetList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the AssetList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the includeOnlyTrashed attribute of the AssetList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   * Sets the includeOnlyTrashed attribute of the AssetList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the pagedListInfo attribute of the AssetList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the id attribute of the AssetList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the orgId attribute of the AssetList object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }

  public int getSubId() {
    return subId;
  }

  /**
   * Gets the contactId attribute of the AssetList object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Gets the serviceContractId attribute of the AssetList object
   *
   * @return The serviceContractId value
   */
  public int getServiceContractId() {
    return serviceContractId;
  }


  /**
   * Gets the serviceContractNumber attribute of the AssetList object
   *
   * @return The serviceContractNumber value
   */
  public String getServiceContractNumber() {
    return serviceContractNumber;
  }


  /**
   * Gets the serialNumber attribute of the AssetList object
   *
   * @return The serialNumber value
   */
  public String getSerialNumber() {
    return serialNumber;
  }


  /**
   * Gets the allAssets attribute of the AssetList object
   *
   * @return The allAssets value
   */
  public boolean getAllAssets() {
    return allAssets;
  }


  /**
   * Gets the trashedDate attribute of the AssetList object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   * Gets the includeOnlyTrashed attribute of the AssetList object
   *
   * @return The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   * Gets the enteredBy attribute of the AssetList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the parentId attribute of the AssetList object
   *
   * @return The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   * Sets the parentId attribute of the AssetList object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   * Sets the parentId attribute of the AssetList object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   * Gets the buildCompleteHierarchy attribute of the AssetList object
   *
   * @return The buildCompleteHierarchy value
   */
  public boolean getBuildCompleteHierarchy() {
    return buildCompleteHierarchy;
  }


  /**
   * Sets the buildCompleteHierarchy attribute of the AssetList object
   *
   * @param tmp The new buildCompleteHierarchy value
   */
  public void setBuildCompleteHierarchy(boolean tmp) {
    this.buildCompleteHierarchy = tmp;
  }


  /**
   * Sets the buildCompleteHierarchy attribute of the AssetList object
   *
   * @param tmp The new buildCompleteHierarchy value
   */
  public void setBuildCompleteHierarchy(String tmp) {
    this.buildCompleteHierarchy = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the skipParentIdRequirement attribute of the AssetList object
   *
   * @return The skipParentIdRequirement value
   */
  public boolean getSkipParentIdRequirement() {
    return skipParentIdRequirement;
  }


  /**
   * Sets the skipParentIdRequirement attribute of the AssetList object
   *
   * @param tmp The new skipParentIdRequirement value
   */
  public void setSkipParentIdRequirement(boolean tmp) {
    this.skipParentIdRequirement = tmp;
  }


  /**
   * Sets the skipParentIdRequirement attribute of the AssetList object
   *
   * @param tmp The new skipParentIdRequirement value
   */
  public void setSkipParentIdRequirement(String tmp) {
    this.skipParentIdRequirement = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      Asset thisAsset = this.getObject(rs);
      this.add(thisAsset);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    if (buildCompleteHierarchy) {
      Iterator iter = (Iterator) this.iterator();
      while (iter.hasNext()) {
        Asset asset = (Asset) iter.next();
        asset.setBuildCompleteHierarchy(true);
        asset.buildCompleteHierarchy(db);
        if (asset.getChildList() != null) {
          this.addAll(asset.getChildList());
        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db  Description of the Parameter
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM asset a " +
            "LEFT JOIN service_contract sc ON (a.contract_id = sc.contract_id) " +
            "LEFT JOIN lookup_asset_status las ON (a.status = las.code) " +
            "WHERE asset_id > -1 ");
    createFilter(sqlFilter, db);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      PreparedStatement pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine column to sort by
      pagedListInfo.setDefaultSort("a.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY a.entered ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "a.*, sc.contract_number AS service_contract_number, las.description AS status_name " +
            "FROM asset a " +
            "LEFT JOIN service_contract sc ON (a.contract_id = sc.contract_id) " +
            "LEFT JOIN lookup_asset_status las ON (a.status = las.code) " +
            "WHERE asset_id > -1 ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    return pst;
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (parentId > -1) {
      sqlFilter.append("AND a.parent_id = ? ");
    } else {
      if (!skipParentIdRequirement) {
        sqlFilter.append("AND a.parent_id IS NULL ");
      }
    }
    if (enteredBy > -1) {
      sqlFilter.append("AND a.enteredby = ? ");
    }
    if (id > -1) {
      sqlFilter.append("AND a.asset_id = ? ");
    }
    if (orgId > -1) {
      sqlFilter.append("AND a.account_id = ? ");
    }
    if (subId > -1) {
      sqlFilter.append("AND sc.submitter_id = ? ");
    }
    if (contactId > -1) {
      sqlFilter.append("AND a.contact_id = ? ");
    }
    if (serviceContractId > -1) {
      sqlFilter.append("AND (a.contract_id = ? ");
      sqlFilter.append((allAssets) ? "OR a.contract_id IS NULL)" : ")");
    } else {
      sqlFilter.append((allAssets) ? " " : "AND a.contract_id IS NOT NULL ");
    }
    if (serialNumber != null) {
      if (serialNumber.indexOf("%") >= 0) {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(a.serial_number) LIKE ? ");
      } else {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(a.serial_number) = ? ");
      }
    }

    if (serviceContractNumber != null) {
      if (serviceContractNumber.indexOf("%") >= 0) {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(sc.contract_number) LIKE ? ");
      } else {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(sc.contract_number) = ? ");
      }
    }

    if (includeOnlyTrashed) {
      sqlFilter.append("AND a.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND a.trashed_date = ? ");
    } else {
      sqlFilter.append("AND a.trashed_date IS NULL ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (parentId > -1) {
      DatabaseUtils.setInt(pst, ++i, parentId);
    }
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (orgId > -1) {
      pst.setInt(++i, orgId);
    }
    if (subId > -1) {
      pst.setInt(++i, subId);
    }
    if (contactId > -1) {
      pst.setInt(++i, contactId);
    }
    if (serviceContractId > -1) {
      pst.setInt(++i, serviceContractId);
    }
    if (serialNumber != null) {
      pst.setString(++i, serialNumber.toLowerCase());
    }
    if (serviceContractNumber != null) {
      pst.setString(++i, serviceContractNumber.toLowerCase());
    }
    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
    }
    return i;
  }


  /**
   * Gets the object attribute of the AssetList object
   *
   * @param rs Description of the Parameter
   * @return The object value
   * @throws SQLException Description of the Exception
   */
  public Asset getObject(ResultSet rs) throws SQLException {
    Asset thisAsset = new Asset(rs);
    return thisAsset;
  }


  /**
   * Used for determing the count of related items
   *
   * @param db       Description of the Parameter
   * @param moduleId Description of the Parameter
   * @param itemId   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int moduleId, int itemId) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT COUNT(*) as itemcount " +
            "FROM asset a " +
            "WHERE asset_id > 0 " +
            "AND trashed_date IS NULL ");
    if (moduleId == Constants.ACCOUNTS) {
      sql.append("AND a.account_id = ?");
    }
    if (moduleId == Constants.SERVICE_CONTRACTS) {
      sql.append("AND a.contract_id = ?");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (moduleId == Constants.ACCOUNTS) {
      pst.setInt(1, itemId);
    }
    if (moduleId == Constants.SERVICE_CONTRACTS) {
      pst.setInt(1, itemId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("itemcount");
    }
    rs.close();
    pst.close();
    return count;
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
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      Asset tmpAsset = (Asset) itr.next();
      tmpAsset.updateStatus(db, toTrash, tmpUserId);
    }
    return true;
  }


  /**
   * Deletes the list of assets in this object
   *
   * @param db           Description of the Parameter
   * @param baseFilePath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db, String baseFilePath) throws SQLException {
    Iterator assets = this.iterator();
    while (assets.hasNext()) {
      Asset thisAsset = (Asset) assets.next();
      thisAsset = new Asset(db, String.valueOf(thisAsset.getId()));
      if (this.getId() != -1) {
        thisAsset.delete(db, baseFilePath);
      }
    }
  }
}

