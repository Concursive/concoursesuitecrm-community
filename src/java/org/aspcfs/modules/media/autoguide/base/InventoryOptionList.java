//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.media.autoguide.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.sql.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  A collection of options a specific Inventory object has
 *
 *@author     matt rajkowski
 *@created    June 16, 2002
 *@version    $Id: InventoryOptionList.java,v 1.3 2002/06/17 18:29:12 mrajkowski
 *      Exp $
 */
public class InventoryOptionList extends ArrayList {

  /*
   *  public static String tableName = "autoguide_inventory_options";
   *  public static String uniqueField = "inventory_id";
   *  private java.sql.Timestamp lastAnchor = null;
   *  private java.sql.Timestamp nextAnchor = null;
   *  private int syncType = Constants.NO_SYNC;
   */
  private int inventoryId = -1;


  /**
   *  Constructor for the OptionList object
   */
  public InventoryOptionList() { }


  /**
   *  Constructor for the OptionList object
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public InventoryOptionList(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   *  Constructor for the OptionList object
   *
   *@param  request  Description of Parameter
   */
  public InventoryOptionList(HttpServletRequest request) {
    int i = 0;
    String thisId = null;
    while ((thisId = request.getParameter("option" + (++i) + "id")) != null) {
      String checked = request.getParameter("option" + thisId);
      if ("on".equalsIgnoreCase(checked)) {
        InventoryOption thisOption = new InventoryOption();
        thisOption.setOptionId(Integer.parseInt(thisId));
        thisOption.setInventoryId(inventoryId);
        this.add(thisOption);
      }
    }
  }


  /*
   *  public void setLastAnchor(java.sql.Timestamp tmp) {
   *  this.lastAnchor = tmp;
   *  }
   *  public void setLastAnchor(String tmp) {
   *  this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
   *  }
   *  public void setNextAnchor(java.sql.Timestamp tmp) {
   *  this.nextAnchor = tmp;
   *  }
   *  public void setNextAnchor(String tmp) {
   *  this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
   *  }
   *  public void setSyncType(int tmp) {
   *  this.syncType = tmp;
   *  }
   *  public void setSyncType(String tmp) {
   *  this.syncType = Integer.parseInt(tmp);
   *  }
   */
  /**
   *  Sets the inventoryId attribute of the OptionList object
   *
   *@param  tmp  The new inventoryId value
   */
  public void setInventoryId(int tmp) {
    this.inventoryId = tmp;
  }


  /**
   *  Sets the inventoryId attribute of the InventoryOptionList object
   *
   *@param  tmp  The new inventoryId value
   */
  public void setInventoryId(String tmp) {
    this.inventoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the accountInventoryId attribute of the InventoryOptionList object
   *
   *@param  tmp  The new accountInventoryId value
   */
  public void setAccountInventoryId(int tmp) {
    this.setInventoryId(tmp);
  }


  /**
   *  Sets the accountInventoryId attribute of the InventoryOptionList object
   *
   *@param  tmp  The new accountInventoryId value
   */
  public void setAccountInventoryId(String tmp) {
    this.setInventoryId(tmp);
  }


  /*
   *  public String getTableName() {
   *  return tableName;
   *  }
   *  public String getUniqueField() {
   *  return uniqueField;
   *  }
   */
  /**
   *  Gets the inventoryId attribute of the InventoryOptionList object
   *
   *@return    The inventoryId value
   */
  public int getInventoryId() {
    return inventoryId;
  }


  /**
   *  Gets the object attribute of the OptionList object
   *
   *@param  rs                Description of Parameter
   *@return                   The object value
   *@exception  SQLException  Description of Exception
   */
  public InventoryOption getObject(ResultSet rs) throws SQLException {
    InventoryOption thisOption = new InventoryOption(rs);
    return thisOption;
  }


  /**
   *  Description of the Method
   *
   *@param  optionId  Description of Parameter
   *@return           Description of the Returned Value
   */
  public boolean hasOption(int optionId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      InventoryOption thisOption = (InventoryOption) i.next();
      if (thisOption.getOptionId() == optionId) {
        return true;
      }
    }
    return false;
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
      InventoryOption thisOption = this.getObject(rs);
      this.add(thisOption);
    }
    rs.close();
    if (pst != null) {
      pst.close();
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
    int items = -1;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT io.inventory_id, io.option_id " +
        "FROM autoguide_inventory_options io " +
        " LEFT JOIN autoguide_options o ON (io.option_id = o.option_id) " +
        "WHERE io.option_id > -1 ");
    createFilter(sql);
    sql.append("ORDER BY o.level, o.option_name ");
    pst = db.prepareStatement(sql.toString());
    items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    return rs;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void insert(Connection db) throws SQLException {
    Iterator optionList = this.iterator();
    while (optionList.hasNext()) {
      InventoryOption thisOption = (InventoryOption) optionList.next();
      thisOption.setInventoryId(inventoryId);
      thisOption.insert(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void update(Connection db) throws SQLException {
    this.delete(db);
    this.insert(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void delete(Connection db) throws SQLException {
    String sql =
        "DELETE FROM autoguide_inventory_options " +
        "WHERE inventory_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, inventoryId);
    pst.execute();
    pst.close();
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
    /*
     *  if (syncType == Constants.SYNC_INSERTS) {
     *  if (lastAnchor != null) {
     *  sqlFilter.append("AND o.entered > ? ");
     *  }
     *  sqlFilter.append("AND o.entered < ? ");
     *  }
     *  if (syncType == Constants.SYNC_UPDATES) {
     *  sqlFilter.append("AND o.modified > ? ");
     *  sqlFilter.append("AND o.entered < ? ");
     *  sqlFilter.append("AND o.modified < ? ");
     *  }
     */
    if (inventoryId > -1) {
      sqlFilter.append("AND io.inventory_id = ? ");
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
    /*
     *  if (syncType == Constants.SYNC_INSERTS) {
     *  if (lastAnchor != null) {
     *  pst.setTimestamp(++i, lastAnchor);
     *  }
     *  pst.setTimestamp(++i, nextAnchor);
     *  }
     *  if (syncType == Constants.SYNC_UPDATES) {
     *  pst.setTimestamp(++i, lastAnchor);
     *  pst.setTimestamp(++i, lastAnchor);
     *  pst.setTimestamp(++i, nextAnchor);
     *  }
     */
    if (inventoryId > -1) {
      pst.setInt(++i, inventoryId);
    }
    return i;
  }
}

