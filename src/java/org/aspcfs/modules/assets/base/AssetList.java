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
package org.aspcfs.modules.assets.base;

import java.sql.*;
import java.text.*;
import java.util.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.Constants;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    January 8, 2004
 *@version    $Id: AssetList.java,v 1.1.6.1.2.3 2004/02/04 19:38:31 mrajkowski
 *      Exp $
 */
public class AssetList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int orgId = -1;
  private int serviceContractId = -1;
  private String serviceContractNumber = null;
  private int enteredBy = -1;
  private String serialNumber = null;
  private boolean allAssets = true;


  /**
   *  Constructor for the ServiceContractList object
   */
  public AssetList() { }


  /**
   *  Constructor for the AssetList object
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public AssetList(Connection db) throws SQLException { }


  /**
   *  Sets the pagedListInfo attribute of the AssetList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the AssetList object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the AssetList object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orgId attribute of the AssetList object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the AssetList object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the serviceContractId attribute of the AssetList object
   *
   *@param  tmp  The new serviceContractId value
   */
  public void setServiceContractId(int tmp) {
    this.serviceContractId = tmp;
  }


  /**
   *  Sets the serviceContractId attribute of the AssetList object
   *
   *@param  tmp  The new serviceContractId value
   */
  public void setServiceContractId(String tmp) {
    this.serviceContractId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the serviceContractNumber attribute of the AssetList object
   *
   *@param  tmp  The new serviceContractNumber value
   */
  public void setServiceContractNumber(String tmp) {
    this.serviceContractNumber = tmp;
  }


  /**
   *  Sets the serialNumber attribute of the AssetList object
   *
   *@param  tmp  The new serialNumber value
   */
  public void setSerialNumber(String tmp) {
    this.serialNumber = tmp;
  }


  /**
   *  Sets the allAssets attribute of the AssetList object
   *
   *@param  tmp  The new allAssets value
   */
  public void setAllAssets(boolean tmp) {
    this.allAssets = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the AssetList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the AssetList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the AssetList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the AssetList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the orgId attribute of the AssetList object
   *
   *@return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the serviceContractId attribute of the AssetList object
   *
   *@return    The serviceContractId value
   */
  public int getServiceContractId() {
    return serviceContractId;
  }


  /**
   *  Gets the serviceContractNumber attribute of the AssetList object
   *
   *@return    The serviceContractNumber value
   */
  public String getServiceContractNumber() {
    return serviceContractNumber;
  }


  /**
   *  Gets the serialNumber attribute of the AssetList object
   *
   *@return    The serialNumber value
   */
  public String getSerialNumber() {
    return serialNumber;
  }


  /**
   *  Gets the allAssets attribute of the AssetList object
   *
   *@return    The allAssets value
   */
  public boolean getAllAssets() {
    return allAssets;
  }


  /**
   *  Gets the enteredBy attribute of the AssetList object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.isEndOfOffset(db)) {
        break;
      }
      Asset thisAsset = this.getObject(rs);
      this.add(thisAsset);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
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
        "WHERE asset_id > -1 ");
    createFilter(sqlFilter, db);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
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
        "a.*, sc.contract_number AS service_contract_number " +
        "FROM asset a " +
        "LEFT JOIN service_contract sc ON (a.contract_id = sc.contract_id) " +
        "WHERE asset_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    return rs;
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter         Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
    if (enteredBy > -1) {
      sqlFilter.append("AND a.enteredby = ? ");
    }
    if (id > -1) {
      sqlFilter.append("AND a.asset_id = ? ");
    }
    if (orgId > -1) {
      sqlFilter.append("AND a.account_id = ? ");
    }
    if (serviceContractId > -1) {
      sqlFilter.append("AND (a.contract_id = ? ");
      sqlFilter.append((allAssets) ? "OR a.contract_id IS NULL)" : ")");
    } else {
      sqlFilter.append((allAssets) ? " " : "AND a.contract_id IS NOT NULL ");
    }
    if (serialNumber != null) {
      if (serialNumber.indexOf("%") >= 0) {
        sqlFilter.append("AND lower(a.serial_number) like lower(?) ");
      } else {
        sqlFilter.append("AND lower(a.serial_number) = lower(?) ");
      }
    }

    if (serviceContractNumber != null) {
      if (serviceContractNumber.indexOf("%") >= 0) {
        sqlFilter.append("AND lower(sc.contract_number) like lower(?) ");
      } else {
        sqlFilter.append("AND lower(sc.contract_number) = lower(?) ");
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (orgId > -1) {
      pst.setInt(++i, orgId);
    }
    if (serviceContractId > -1) {
      pst.setInt(++i, serviceContractId);
    }
    if (serialNumber != null) {
      pst.setString(++i, serialNumber);
    }
    if (serviceContractNumber != null) {
      pst.setString(++i, serviceContractNumber);
    }
    return i;
  }


  /**
   *  Gets the object attribute of the AssetList object
   *
   *@param  rs                Description of the Parameter
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public Asset getObject(ResultSet rs) throws SQLException {
    Asset thisAsset = new Asset(rs);
    return thisAsset;
  }


  /**
   *  Used for determing the count of related items
   *
   *@param  db                Description of the Parameter
   *@param  moduleId          Description of the Parameter
   *@param  itemId            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int moduleId, int itemId) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT COUNT(*) as itemcount " +
        "FROM asset a " +
        "WHERE asset_id > 0 ");
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
   *  Deletes the list of assets in this object
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator assets = this.iterator();
    while (assets.hasNext()) {
      Asset thisAsset = (Asset) assets.next();
      thisAsset.delete(db);
    }
  }
}

