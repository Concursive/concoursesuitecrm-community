//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.media.autoguide.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;

/**
 *  Represents a list of vehicles
 *
 *@author     matt
 *@created    May 17, 2002
 *@version    $Id$
 */
public class VehicleList extends ArrayList implements SyncableList {

  public final static String tableName = "autoguide_vehicle";
  public final static String uniqueField = "vehicle_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private int year = -1;
  private PagedListInfo pagedListInfo = null;


  /**
   *  Constructor for the VehicleList object
   */
  public VehicleList() { }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public static ArrayList buildYearList(Connection db) throws SQLException {
    ArrayList years = new ArrayList();
    String sql =
        "SELECT DISTINCT year FROM autoguide_vehicle ORDER BY year DESC";
    PreparedStatement pst = db.prepareStatement(sql);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      String thisYear = String.valueOf(rs.getInt("year"));
      years.add(thisYear);
    }
    rs.close();
    pst.close();
    return years;
  }


  /**
   *  Sets the pagedListInfo attribute of the VehicleList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the pagedListInfo attribute of the VehicleList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the lastAnchor attribute of the VehicleList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the VehicleList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the VehicleList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the VehicleList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the syncType attribute of the VehicleList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the VehicleList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the year attribute of the VehicleList object
   *
   *@param  tmp  The new year value
   */
  public void setYear(int tmp) {
    this.year = tmp;
  }


  /**
   *  Sets the year attribute of the VehicleList object
   *
   *@param  tmp  The new year value
   */
  public void setYear(String tmp) {
    this.year = Integer.parseInt(tmp);
  }


  /**
   *  Gets the tableName attribute of the VehicleList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the VehicleList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the object attribute of the VehicleList object
   *
   *@param  rs                Description of Parameter
   *@return                   The object value
   *@exception  SQLException  Description of Exception
   */
  public Vehicle getObject(ResultSet rs) throws SQLException {
    Vehicle thisVehicle = new Vehicle(rs);
    return thisVehicle;
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
      if (pagedListInfo != null && pagedListInfo.isEndOfOffset(db)) {
        break;
      }
      Vehicle thisVehicle = this.getObject(rs);
      this.add(thisVehicle);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  This method is required for synchronization, it allows for the resultset
   *  to be streamed with lower overhead
   *
   *@param  db                Description of Parameter
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM autoguide_vehicle v " +
        " LEFT JOIN autoguide_make make ON v.make_id = make.make_id " +
        " LEFT JOIN autoguide_model model ON v.model_id = model.model_id " +
        "WHERE vehicle_id > -1 ");

    createFilter(sqlFilter);

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
      pagedListInfo.setDefaultSort("v.vehicle_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY v.vehicle_id ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
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
        "FROM autoguide_vehicle v " +
        " LEFT JOIN autoguide_make make ON v.make_id = make.make_id " +
        " LEFT JOIN autoguide_model model ON v.model_id = model.model_id " +
        "WHERE vehicle_id > -1 ");
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
   *@param  sqlFilter  Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND v.entered > ? ");
      }
      sqlFilter.append("AND v.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND v.modified > ? ");
      sqlFilter.append("AND v.entered < ? ");
      sqlFilter.append("AND v.modified < ? ");
    }
    if (year > -1) {
      sqlFilter.append("AND v.year = ? ");
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
    if (year > -1) {
      pst.setInt(++i, year);
    }
    return i;
  }
}

