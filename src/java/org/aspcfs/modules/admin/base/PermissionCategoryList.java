//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.utils.DatabaseUtils;
import javax.servlet.http.*;


public class PermissionCategoryList extends Vector {

  private PagedListInfo pagedListInfo = null;
  
  public final static String tableName = "permission_category";
  public final static String uniqueField = "category_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private boolean customizableModulesOnly = false;

  private int enabledState = -1;
  private int activeState = -1;
  
  public PermissionCategoryList() { }

  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }
  
  public PagedListInfo getPagedListInfo() {
	return pagedListInfo;
}

  public String getTableName() { return tableName; }
  public String getUniqueField() { return uniqueField; }
  public java.sql.Timestamp getLastAnchor() { return lastAnchor; }
  public java.sql.Timestamp getNextAnchor() { return nextAnchor; }
  public int getSyncType() { return syncType; }
  public void setLastAnchor(java.sql.Timestamp tmp) { this.lastAnchor = tmp; }
  public void setNextAnchor(java.sql.Timestamp tmp) { this.nextAnchor = tmp; }
  public void setSyncType(int tmp) { this.syncType = tmp; }

  public void setEnabledState(int tmp) { this.enabledState = tmp; }
  public void setActiveState(int tmp) { this.activeState = tmp; }
  public int getEnabledState() { return enabledState; }
  public int getActiveState() { return activeState; }
  
  public boolean getCustomizableModulesOnly() {
    return customizableModulesOnly;
  }
  public void setCustomizableModulesOnly(boolean customizableModulesOnly) {
    this.customizableModulesOnly = customizableModulesOnly;
  }


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
        "FROM permission_category pc " +
        "WHERE pc.category_id > 0 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      pst.close();
      rs.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND pc.category < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      pagedListInfo.setDefaultSort("pc.level, pc.category", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY pc.level, pc.category ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "* " +
        "FROM permission_category pc " +
        "WHERE pc.category_id > 0 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      PermissionCategory thisCategory = new PermissionCategory(rs);
      this.add(thisCategory);
    }
    rs.close();
    pst.close();
  }

  private void createFilter(StringBuffer sqlFilter) {
    if (enabledState != -1) {
      sqlFilter.append("AND enabled = ? ");
    }
    if (activeState != -1) {
      sqlFilter.append("AND active = ? ");
    }
    if (customizableModulesOnly) {
      sqlFilter.append("AND (lookups = ? OR folders = ?) ");
    }      
    
  }

  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (enabledState != -1) {
      pst.setBoolean(++i, enabledState == Constants.TRUE);
    }
    if (activeState != -1) {
      pst.setBoolean(++i, activeState == Constants.TRUE);
    }
    if (customizableModulesOnly) {
      pst.setBoolean(++i, true);
      pst.setBoolean(++i, true);
    }
    return i;
  }

}

