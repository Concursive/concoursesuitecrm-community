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
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created October 19, 2005
 */
public class AssetMaterialList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int assetId = -1;
  private int code = -1;
  private double quantity = 0.0;
  private int qtyGT = Constants.UNDEFINED;
  private int qtyEquals = Constants.UNDEFINED;

  public final static String tableName = "lookup_asset_materials";
  public final static String uniqueField = "code";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  /**
   * Constructor for the AssetMaterialList object
   */
  public AssetMaterialList() {
  }

  /**
   * Sets the lastAnchor attribute of the AssetMaterialList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the AssetMaterialList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the AssetMaterialList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the AssetMaterialList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the AssetMaterialList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Gets the tableName attribute of the AssetMaterialList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the AssetMaterialList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Constructor for the AssetMaterialList object
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public AssetMaterialList(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM asset_materials_map amm " +
            "LEFT JOIN lookup_asset_materials lam ON (amm.code = lam.code) " +
            "WHERE map_id > -1 ");
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
      pagedListInfo.setDefaultSort("amm.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY amm.entered ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "amm.* " +
            "FROM asset_materials_map amm " +
            "LEFT JOIN lookup_asset_materials lam ON (amm.code = lam.code) " +
            "WHERE map_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      AssetMaterial thisAssetMaterial = new AssetMaterial(rs);
      this.add(thisAssetMaterial);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
    if (id > -1) {
      sqlFilter.append("AND amm.map_id = ? ");
    }
    if (assetId > -1) {
      sqlFilter.append("AND amm.asset_id = ? ");
    }
    if (code > -1) {
      sqlFilter.append("AND amm.code = ? ");
    }
    if (qtyGT == Constants.TRUE) {
      sqlFilter.append("AND amm.quantity >= ? ");
    } else if (qtyGT == Constants.FALSE) {
      sqlFilter.append("AND amm.quantity <= ? ");
    }
    if (qtyEquals == Constants.TRUE) {
      sqlFilter.append("AND amm.quantity = ? ");
    } else if (qtyEquals == Constants.FALSE) {
      sqlFilter.append("AND amm.quantity <> ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
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
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (assetId > -1) {
      pst.setInt(++i, assetId);
    }
    if (code > -1) {
      pst.setInt(++i, code);
    }
    if (qtyGT != Constants.UNDEFINED) {
      pst.setDouble(++i, quantity);
    }
    if (qtyEquals != Constants.UNDEFINED) {
      pst.setDouble(++i, quantity);
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    return i;
  }


  /**
   * Gets the quantityMap attribute of the AssetMaterialList class
   *
   * @param materials Description of the Parameter
   * @return The quantityMap value
   */
  public static HashMap getQuantityMap(String materials) {
    HashMap map = new HashMap();
    StringTokenizer str = new StringTokenizer(materials, "|");
    while (str.hasMoreTokens()) {
      String tmp = str.nextToken();
      if (tmp != null && !"".equals(tmp.trim())) {
        StringTokenizer str2 = new StringTokenizer(tmp, ",");
        Integer code = new Integer(str2.nextToken());
        map.put(code, str2.nextToken());
      }
    }
    return map;
  }


  /**
   * Sets the elements attribute of the AssetMaterialList object
   *
   * @param materials The new elements value
   */
  public void setElements(String materials) {
    StringTokenizer str = new StringTokenizer(materials, "|");
    while (str.hasMoreTokens()) {
      AssetMaterial material = new AssetMaterial();
      material.setAssetId(this.getAssetId());
      String temp = str.nextToken();
      if (temp != null && !"".equals(temp)) {
        material.setElement(temp);
        this.add(material);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      AssetMaterial material = (AssetMaterial) iter.next();
      material.delete(db);
      iter.remove();
    }
    return true;
  }


  /**
   * Sets the elementsDeletable attribute of the AssetMaterialList object
   *
   * @param canDelete The new elementsDeletable value
   */
  public void setElementsDeletable(boolean canDelete) {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      AssetMaterial material = (AssetMaterial) iter.next();
      material.setCanDelete(canDelete);
    }
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public HashMap createMapOfElements() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      AssetMaterial material = (AssetMaterial) iter.next();
      map.put(new Integer(material.getCode()), material);
    }
    return map;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void parseElements(Connection db) throws SQLException {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      AssetMaterial material = (AssetMaterial) iter.next();
      if (material.getAssetId() == -1) {
        material.setAssetId(this.getAssetId());
      }
      material.parse(db);
    }
  }


  /*
   *  Get and Set methods
   */
  /**
   * Gets the pagedListInfo attribute of the AssetMaterialList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the AssetMaterialList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the id attribute of the AssetMaterialList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the AssetMaterialList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the AssetMaterialList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the assetId attribute of the AssetMaterialList object
   *
   * @return The assetId value
   */
  public int getAssetId() {
    return assetId;
  }


  /**
   * Sets the assetId attribute of the AssetMaterialList object
   *
   * @param tmp The new assetId value
   */
  public void setAssetId(int tmp) {
    this.assetId = tmp;
  }


  /**
   * Sets the assetId attribute of the AssetMaterialList object
   *
   * @param tmp The new assetId value
   */
  public void setAssetId(String tmp) {
    this.assetId = Integer.parseInt(tmp);
  }


  /**
   * Gets the code attribute of the AssetMaterialList object
   *
   * @return The code value
   */
  public int getCode() {
    return code;
  }


  /**
   * Sets the code attribute of the AssetMaterialList object
   *
   * @param tmp The new code value
   */
  public void setCode(int tmp) {
    this.code = tmp;
  }


  /**
   * Sets the code attribute of the AssetMaterialList object
   *
   * @param tmp The new code value
   */
  public void setCode(String tmp) {
    this.code = Integer.parseInt(tmp);
  }


  /**
   * Gets the quantity attribute of the AssetMaterialList object
   *
   * @return The quantity value
   */
  public double getQuantity() {
    return quantity;
  }


  /**
   * Sets the quantity attribute of the AssetMaterialList object
   *
   * @param tmp The new quantity value
   */
  public void setQuantity(double tmp) {
    this.quantity = tmp;
  }


  /**
   * Sets the quantity attribute of the AssetMaterialList object
   *
   * @param tmp The new quantity value
   */
  public void setQuantity(String tmp) {
    this.quantity = Double.parseDouble(tmp);
  }


  /**
   * Gets the qtyGT attribute of the AssetMaterialList object
   *
   * @return The qtyGT value
   */
  public int getQtyGT() {
    return qtyGT;
  }


  /**
   * Sets the qtyGT attribute of the AssetMaterialList object
   *
   * @param tmp The new qtyGT value
   */
  public void setQtyGT(int tmp) {
    this.qtyGT = tmp;
  }


  /**
   * Sets the qtyGT attribute of the AssetMaterialList object
   *
   * @param tmp The new qtyGT value
   */
  public void setQtyGT(String tmp) {
    this.qtyGT = Integer.parseInt(tmp);
  }


  /**
   * Gets the qtyEquals attribute of the AssetMaterialList object
   *
   * @return The qtyEquals value
   */
  public int getQtyEquals() {
    return qtyEquals;
  }


  /**
   * Sets the qtyEquals attribute of the AssetMaterialList object
   *
   * @param tmp The new qtyEquals value
   */
  public void setQtyEquals(int tmp) {
    this.qtyEquals = tmp;
  }


  /**
   * Sets the qtyEquals attribute of the AssetMaterialList object
   *
   * @param tmp The new qtyEquals value
   */
  public void setQtyEquals(String tmp) {
    this.qtyEquals = Integer.parseInt(tmp);
  }
}

