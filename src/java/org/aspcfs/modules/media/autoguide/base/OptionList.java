//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.autoguide.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.cfsbase.Constants;
import javax.servlet.http.*;

public class OptionList extends ArrayList {

  public static String tableName = "autoguide_options";
  public static String uniqueField = "option_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private int inventoryId = -1;
  
  public OptionList() {}
  
  public OptionList(Connection db) throws SQLException {
    buildList(db);
  }
  
  public OptionList(HttpServletRequest request) {
    int i = 0;
    String thisId = null;
    while ((thisId = request.getParameter("option" + (++i) + "id")) != null) {
      String checked = request.getParameter("option" + thisId);
      if ("on".equalsIgnoreCase(checked)) {
        Option thisOption = new Option();
        thisOption.setId(Integer.parseInt(thisId));
        this.add(thisOption);
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
  
  public boolean hasOption(int optionId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Option thisOption = (Option)i.next();
      if (thisOption.getId() == optionId) {
        return true;
      }
    }
    return false;
  }

  public void select(Connection db) throws SQLException {
    buildList(db);
  }
  
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      Option thisOption = this.getObject(rs);
      this.add(thisOption);
    }
    rs.close();
    if (pst != null) pst.close();
  }
  
  public Option getObject(ResultSet rs) throws SQLException {
    Option thisOption = new Option(rs);
    return thisOption;
  }
  
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;
    
    StringBuffer sql = new StringBuffer(); 
    sql.append(  
      "SELECT o.option_id, o.option_name, o.entered, o.modified " +
      "FROM autoguide_options o ");
    if (inventoryId > -1) {
      sql.append(
        ", autoguide_inventory_options io ");
    }
    sql.append("WHERE o.option_id > -1 ");
    createFilter(sql);
    sql.append("ORDER BY level, o.option_id ");
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
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
    }
    if (inventoryId > -1) {
      sqlFilter.append("AND o.option_id = io.option_id AND io.inventory_id = ? ");
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
  
  public void insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
      "INSERT INTO autoguide_inventory_options (inventory_id, option_id) " +
      "VALUES (?, ?)");
    Iterator optionList = this.iterator();
    while (optionList.hasNext()) {
      Option thisOption = (Option)optionList.next();
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(1, inventoryId);
      pst.setInt(2, thisOption.getId());
      pst.execute();
      pst.close();
    }
  }
  
  public void update(Connection db) throws SQLException {
    this.delete(db);
    this.insert(db);
  }
  
  public void delete(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
      "DELETE FROM autoguide_inventory_options WHERE inventory_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, inventoryId);
    pst.execute();
    pst.close();
  }
}
