//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.autoguide.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.cfsbase.Constants;
import javax.servlet.http.*;

public class AdRunList extends ArrayList {

  public static String tableName = "autoguide_ad_run";
  public static String uniqueField = "ad_run_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private int inventoryId = -1;
  
  public AdRunList() {}
  
  public AdRunList(Connection db) throws SQLException {
    buildList(db);
  }
  
  public AdRunList(HttpServletRequest request) {
    int i = 0;
    String thisId = null;
    while ((thisId = request.getParameter("adrun" + (++i) + "id")) != null) {
      String startDate = request.getParameter("adrun" + thisId + "startDate");
      String endDate = request.getParameter("adrun" + thisId + "endDate");
      String remove = request.getParameter("adrun" + thisId + "remove");
      if ("on".equalsIgnoreCase(remove)) {
        AdRun thisAdRun = new AdRun();
        //thisAdRun.setId(Integer.parseInt(thisId));
        this.add(thisAdRun);
      }
    }
  }
  
  public void setLastAnchor(java.sql.Timestamp tmp) { this.lastAnchor = tmp; }
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }
  public void setNextAnchor(java.sql.Timestamp tmp) { this.nextAnchor = tmp; }
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }
  public void setSyncType(int tmp) { this.syncType = tmp; }
  public void setSyncType(String tmp) { this.syncType = Integer.parseInt(tmp); }
  public void setInventoryId(int tmp) { this.inventoryId = tmp; }

  public String getTableName() { return tableName; }
  public String getUniqueField() { return uniqueField; }
  public int getInventoryId() { return inventoryId; }

  public void select(Connection db) throws SQLException {
    buildList(db);
  }
  
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      AdRun thisAdRun = this.getObject(rs);
      this.add(thisAdRun);
    }
    rs.close();
    if (pst != null) pst.close();
  }
  
  public AdRun getObject(ResultSet rs) throws SQLException {
    AdRun thisAdRun = new AdRun(rs);
    return thisAdRun;
  }
  
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;
    
    StringBuffer sql = new StringBuffer(); 
    sql.append(  
      "SELECT ad.ad_run_id, ad.inventory_id, " +
      "ad.start_date, ad.end_date, " +
      "ad.entered, ad.enteredby, " +
      "ad.modified, ad.modifiedby " +
      "FROM autoguide_ad_run ad ");
    sql.append("WHERE ad.ad_run_id > -1 ");
    createFilter(sql);
    sql.append("ORDER BY start_date DESC ");
    //sql.append("ORDER BY inventory_id, start_date DESC ");
    pst = db.prepareStatement(sql.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    return rs;
  }
  
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND ad.entered > ? ");
      }
      sqlFilter.append("AND ad.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND ad.modified > ? ");
      sqlFilter.append("AND ad.entered < ? ");
      sqlFilter.append("AND ad.modified < ? ");
    }
    if (inventoryId > -1) {
      sqlFilter.append("AND ad.inventory_id = ? ");
    }
  }
  
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
    if (inventoryId > -1) {
      pst.setInt(++i, inventoryId);
    }
    return i;
  }
}
