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
    int linearId = 0;
    String thisId = null;
    while ((thisId = request.getParameter("adrun" + (++linearId) + "id")) != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.print("AdRunList-> Adding run from request...");
      }
      String runDate = request.getParameter("adrun" + linearId + "runDate");
      String adType = request.getParameter("adrun" + linearId + "adType");
      String includePhoto = request.getParameter("adrun" + linearId + "includePhoto");
      String remove = request.getParameter("adrun" + linearId + "remove");
      if (runDate != null && !"".equalsIgnoreCase(runDate.trim())) {
        AdRun thisAdRun = new AdRun();
        thisAdRun.setRemove(remove);
        thisAdRun.setId(thisId);
        thisAdRun.setRunDate(runDate);
        thisAdRun.setAdType(adType);
        thisAdRun.setIncludePhoto(includePhoto);
        this.add(thisAdRun);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("added");
        }
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("not added");
        }
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
      "ad.run_date, ad.ad_type, ad.include_photo, complete_date, " +
      "ad.entered, ad.enteredby, " +
      "ad.modified, ad.modifiedby, " +
      "adtype.description " +
      "FROM autoguide_ad_run ad, autoguide_ad_run_types adtype ");
    sql.append("WHERE ad.ad_run_id > -1 AND ad.ad_type = adtype.code ");
    createFilter(sql);
    sql.append("ORDER BY run_date ");
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
  
  public void update(Connection db) throws SQLException {
    Iterator adRunList = this.iterator();
    while (adRunList.hasNext()) {
      AdRun thisAdRun = (AdRun)adRunList.next();
      thisAdRun.setInventoryId(inventoryId);
      thisAdRun.update(db);
    }
  }
  
  public void insert(Connection db) throws SQLException {
    Iterator adRunList = this.iterator();
    while (adRunList.hasNext()) {
      AdRun thisAdRun = (AdRun)adRunList.next();
      thisAdRun.setInventoryId(inventoryId);
      thisAdRun.insert(db);
    }
  }
  
  public void delete(Connection db) throws SQLException {
    Iterator adRunList = this.iterator();
    while (adRunList.hasNext()) {
      AdRun thisAdRun = (AdRun)adRunList.next();
      thisAdRun.delete(db);
    }
  }
  
  public AdRun getNextAdRun() {
    if (this.size() > 0) {
      return (AdRun)this.get(0);
    } else {
      return null;
    }
  }
}
