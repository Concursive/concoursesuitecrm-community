//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.media.autoguide.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  Collection of Make objects
 *
 *@author     matt
 *@created    May 17, 2002
 *@version    $Id$
 */
public class MakeList extends ArrayList {

  public static String tableName = "autoguide_make";
  public static String uniqueField = "make_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private int year = -1;


  /**
   *  Constructor for the MakeList object
   */
  public MakeList() { }


  /**
   *  Sets the lastAnchor attribute of the MakeList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the MakeList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the MakeList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the MakeList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the syncType attribute of the MakeList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the MakeList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the year attribute of the MakeList object
   *
   *@param  tmp  The new year value
   */
  public void setYear(int tmp) {
    this.year = tmp;
  }


  /**
   *  Sets the year attribute of the MakeList object
   *
   *@param  tmp  The new year value
   */
  public void setYear(String tmp) {
    try {
      this.year = Integer.parseInt(tmp);
    } catch (Exception e) {
    }
  }


  /**
   *  Gets the tableName attribute of the MakeList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the MakeList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the object attribute of the MakeList object
   *
   *@param  rs                Description of Parameter
   *@return                   The object value
   *@exception  SQLException  Description of Exception
   */
  public Make getObject(ResultSet rs) throws SQLException {
    Make thisMake = new Make(rs);
    return thisMake;
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
      Make thisMake = this.getObject(rs);
      this.add(thisMake);
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
    ResultSet rs = null;
    int items = -1;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT make.make_id, make.make_name, " +
        "make.entered as make_entered, make.enteredby as make_enteredby, " +
        "make.modified as make_modified, make.modifiedby as make_modifiedby " +
        "FROM autoguide_make make ");
    sql.append("WHERE make_id > -1 ");
    createFilter(sql);
    sql.append("ORDER BY make_name ");
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
        sqlFilter.append("AND make.entered > ? ");
      }
      sqlFilter.append("AND make.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND make.modified > ? ");
      sqlFilter.append("AND make.entered < ? ");
      sqlFilter.append("AND make.modified < ? ");
    }
    if (year > -1) {
      sqlFilter.append("AND make.make_id IN (SELECT DISTINCT make_id FROM autoguide_vehicle WHERE year = ?) ");
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

