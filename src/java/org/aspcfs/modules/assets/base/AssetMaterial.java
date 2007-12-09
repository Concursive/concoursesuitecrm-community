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
import java.util.StringTokenizer;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    September 29, 2005
 * @version    $Id$
 */
public class AssetMaterial extends GenericBean {
  private int id = -1;
  private int assetId = -1;
  private int code = -1;
  private double quantity = 0.0;
  private Timestamp entered = null;
  private boolean canDelete = false;


  /**
   *  Constructor for the AssetMaterial object
   */
  public AssetMaterial() { }


  /**
   *  Constructor for the AssetMaterial object
   *
   * @param  db                Description of the Parameter
   * @param  tmpId             Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public AssetMaterial(Connection db, String tmpId) throws SQLException {
    queryRecord(db, Integer.parseInt(tmpId));
  }


  /**
   *  Constructor for the AssetMaterial object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public AssetMaterial(ResultSet rs) throws SQLException {
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
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT amm.*  " +
        "FROM asset_materials_map amm LEFT JOIN lookup_asset_materials lam ON (amm.code = lam.code) " +
        "WHERE amm.map_id = ? ");
    pst.setInt(1, id);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  void buildRecord(ResultSet rs) throws SQLException {
    //asset_materials_map table
    id = rs.getInt("map_id");
    assetId = rs.getInt("asset_id");
    code = rs.getInt("code");
    quantity = rs.getDouble("quantity");
    entered = rs.getTimestamp("entered");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean parse(Connection db) throws SQLException {
    if (canDelete && this.getId() > -1) {
      return delete(db);
    }
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    if (this.getId() > -1) {
      pst = db.prepareStatement(
          "UPDATE asset_materials_map " +
          "SET quantity = ? WHERE map_id = ? ");
      pst.setDouble(++i, quantity);
      pst.setInt(++i, this.getId());
      pst.executeUpdate();
      pst.close();
    } else {
      id = DatabaseUtils.getNextSeq(db, "asset_materials_map_map_id_seq");
      pst = db.prepareStatement("INSERT INTO asset_materials_map (" + (id > -1 ? "map_id," : "") + " asset_id, code, quantity) " +
          " VALUES (" + (this.getId() > -1 ? "?," : "") + "?,?,?) ");
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, this.getAssetId());
      pst.setInt(++i, this.getCode());
      pst.setDouble(++i, this.getQuantity());
      pst.execute();
      pst.close();
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
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement("DELETE FROM asset_materials_map WHERE map_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean deleteAssetMaterials(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement("DELETE FROM asset_materials_map WHERE asset_id = ? ");
    pst.setInt(1, this.getAssetId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Sets the element attribute of the AssetMaterial object
   *
   * @param  codeAndQuantity  The new element value
   * @return                  Description of the Return Value
   */
  public boolean setElement(String codeAndQuantity) {
    if (codeAndQuantity != null && !"".equals(codeAndQuantity) && !",".equals(codeAndQuantity)) {
      String[] tmp = codeAndQuantity.split(",");
      code = Integer.parseInt(tmp[0]);
      quantity = Double.parseDouble(tmp[1]);
    } else {
      return false;
    }
    return true;
  }


  /**
   *  Gets the timeZoneParams attribute of the AssetMaterial class
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("entered");
    return thisList;
  }


  /**
   *  Gets the numberParams attribute of the AssetMaterial class
   *
   * @return    The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("quantity");
    return thisList;
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the id attribute of the AssetMaterial object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the AssetMaterial object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the AssetMaterial object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the assetId attribute of the AssetMaterial object
   *
   * @return    The assetId value
   */
  public int getAssetId() {
    return assetId;
  }


  /**
   *  Sets the assetId attribute of the AssetMaterial object
   *
   * @param  tmp  The new assetId value
   */
  public void setAssetId(int tmp) {
    this.assetId = tmp;
  }


  /**
   *  Sets the assetId attribute of the AssetMaterial object
   *
   * @param  tmp  The new assetId value
   */
  public void setAssetId(String tmp) {
    this.assetId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the code attribute of the AssetMaterial object
   *
   * @return    The code value
   */
  public int getCode() {
    return code;
  }


  /**
   *  Sets the code attribute of the AssetMaterial object
   *
   * @param  tmp  The new code value
   */
  public void setCode(int tmp) {
    this.code = tmp;
  }


  /**
   *  Sets the code attribute of the AssetMaterial object
   *
   * @param  tmp  The new code value
   */
  public void setCode(String tmp) {
    this.code = Integer.parseInt(tmp);
  }


  /**
   *  Gets the quantity attribute of the AssetMaterial object
   *
   * @return    The quantity value
   */
  public double getQuantity() {
    return quantity;
  }


  /**
   *  Sets the quantity attribute of the AssetMaterial object
   *
   * @param  tmp  The new quantity value
   */
  public void setQuantity(double tmp) {
    this.quantity = tmp;
  }


  /**
   *  Sets the quantity attribute of the AssetMaterial object
   *
   * @param  tmp  The new quantity value
   */
  public void setQuantity(String tmp) {
    this.quantity = Double.parseDouble(tmp);
  }


  /**
   *  Gets the entered attribute of the AssetMaterial object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the AssetMaterial object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the AssetMaterial object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the canDelete attribute of the AssetMaterial object
   *
   * @return    The canDelete value
   */
  public boolean getCanDelete() {
    return canDelete;
  }


  /**
   *  Sets the canDelete attribute of the AssetMaterial object
   *
   * @param  tmp  The new canDelete value
   */
  public void setCanDelete(boolean tmp) {
    this.canDelete = tmp;
  }


  /**
   *  Sets the canDelete attribute of the AssetMaterial object
   *
   * @param  tmp  The new canDelete value
   */
  public void setCanDelete(String tmp) {
    this.canDelete = DatabaseUtils.parseBoolean(tmp);
  }
}

