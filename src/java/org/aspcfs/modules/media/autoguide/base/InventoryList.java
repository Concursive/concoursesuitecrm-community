//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.autoguide.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.cfsbase.Constants;

/**
 *  Collection of Inventory objects
 *
 *@author     matt
 *@created    May 17, 2002
 *@version    $Id$
 */
public class InventoryList extends ArrayList {

  public static String tableName = "autoguide_inventory";
  public static String uniqueField = "inventory_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private boolean buildOrganizationInfo = false;
  private boolean buildPictureId = false;
  private int orgId = -1;


  /**
   *  Constructor for the InventoryList object
   */
  public InventoryList() { }


  /**
   *  Sets the lastAnchor attribute of the InventoryList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the InventoryList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the InventoryList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the InventoryList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the syncType attribute of the InventoryList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the InventoryList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orgId attribute of the InventoryList object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the buildOrganizationInfo attribute of the InventoryList object
   *
   *@param  tmp  The new buildOrganizationInfo value
   */
  public void setBuildOrganizationInfo(boolean tmp) {
    this.buildOrganizationInfo = tmp;
  }


  /**
   *  Sets the buildPictureId attribute of the InventoryList object
   *
   *@param  tmp  The new buildPictureId value
   */
  public void setBuildPictureId(boolean tmp) {
    this.buildPictureId = tmp;
  }


  /**
   *  Gets the tableName attribute of the InventoryList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the InventoryList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the object attribute of the InventoryList object
   *
   *@param  rs                Description of Parameter
   *@return                   The object value
   *@exception  SQLException  Description of Exception
   */
  public Inventory getObject(ResultSet rs) throws SQLException {
    return (new Inventory(rs));
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      Inventory thisItem = this.getObject(rs);
      this.add(thisItem);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("InventoryList-> buildList generated items: " + this.size());
    }
    if (buildOrganizationInfo || buildPictureId) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        Inventory thisItem = (Inventory) i.next();
        if (System.getProperty("DEBUG") != null) {
          System.out.println("InventoryList-> Building info for: " + thisItem.getId());
        }
        if (buildOrganizationInfo) {
          thisItem.buildOrganizationInfo(db);
        }
        if (buildPictureId) {
          thisItem.buildPictureId(db);
        }
        thisItem.buildAdRuns(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT i.inventory_id, i.vehicle_id AS inventory_vehicle_id, " +
        "i.account_id, vin, mileage, is_new, " +
        "condition, comments, stock_no, ext_color, int_color, invoice_price, " +
        "selling_price, sold, i.status, i.entered, i.enteredby, i.modified, i.modifiedby, " +
        "v.vehicle_id, v.year, v.make_id AS vehicle_make_id, " +
        "v.model_id AS vehicle_model_id, v.entered AS vehicle_entered, " +
        "v.enteredby AS vehicle_enteredby, v.modified AS vehicle_modified, " +
        "v.modifiedby AS vehicle_modifiedby, " +
        "model.model_id, model.make_id AS model_make_id, model.model_name, " +
        "model.entered, model.enteredby, " +
        "model.modified, model.modifiedby, " +
        "make.make_id, make.make_name, " +
        "make.entered AS make_entered, make.enteredby AS make_enteredby, " +
        "make.modified AS make_modified, make.modifiedby AS make_modifiedby " +
        "FROM autoguide_inventory i " +
        " LEFT JOIN autoguide_vehicle v ON i.vehicle_id = v.vehicle_id " +
        " LEFT JOIN autoguide_make make ON v.make_id = make.make_id " +
        " LEFT JOIN autoguide_model model ON v.model_id = model.model_id ");
    sql.append("WHERE i.inventory_id > -1 ");
    createFilter(sql);
    sql.append("ORDER BY inventory_id ");
    pst = db.prepareStatement(sql.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    return rs;
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND i.entered > ? ");
      }
      sqlFilter.append("AND i.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND i.modified > ? ");
      sqlFilter.append("AND i.entered < ? ");
      sqlFilter.append("AND i.modified < ? ");
    }
    if (orgId > -1) {
      sqlFilter.append("AND i.account_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
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
    if (orgId > -1) {
      pst.setInt(++i, orgId);
    }
    return i;
  }
}

