package com.darkhorseventures.cfsbase;

import com.darkhorseventures.cfsbase.*;
import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import com.darkhorseventures.utils.DatabaseUtils;
import org.theseus.actions.*;

public class TaskCategory extends GenericBean {

  private int id = -1;
  private String description = null;
  private boolean defaultItem = false;
  private int level = 0;
  private boolean enabled = true;
  
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int taskCount = 0;
  private java.sql.Timestamp lastTaskEntered = null;
  
  public void setId(int tmp) { this.id = tmp; }
  public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
  public void setDescription(String tmp) { this.description = tmp; }
  public void setDefaultItem(boolean tmp) { this.defaultItem = tmp; }
  public void setDefaultItem(String tmp) { this.defaultItem = DatabaseUtils.parseBoolean(tmp); }
  public void setLevel(int tmp) { this.level = tmp; }
  public void setLevel(String tmp) { this.level = Integer.parseInt(tmp); }
  public void setEnabled(boolean tmp) { this.enabled = tmp; }
  public void setEnabled(String tmp) { this.enabled = DatabaseUtils.parseBoolean(tmp); }
  public void setTaskCount(int tmp) { this.taskCount = tmp; }
  public void setTaskCount(String tmp) { this.taskCount = Integer.parseInt(tmp); }
  public void setLastTaskEntered(java.sql.Timestamp tmp) { this.lastTaskEntered = tmp; }
  public void setLastTaskEntered(String tmp) { this.lastTaskEntered = DatabaseUtils.parseTimestamp(tmp); }
  public void setLinkModuleId(int tmp) { this.linkModuleId = tmp; }
  public void setLinkModuleId(String tmp) { this.linkModuleId = Integer.parseInt(tmp); }
  public void setLinkItemId(int tmp) { this.linkItemId = tmp; }
  public void setLinkItemId(String tmp) { this.linkItemId = Integer.parseInt(tmp); }

  public int getId() { return id; }
  public String getDescription() { return description; }
  public boolean getDefaultItem() { return defaultItem; }
  public int getLevel() { return level; }
  public boolean getEnabled() { return enabled; }
  public int getTaskCount() { return taskCount; }
  public java.sql.Timestamp getLastTaskEntered() { return lastTaskEntered; }

  public TaskCategory() {}
  
  public TaskCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public TaskCategory(Connection db, int categoryId) throws SQLException {
    if (categoryId == -1) {
      throw new SQLException("Category ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
      "SELECT * " +
      "FROM lookup_task_category " +
      "WHERE code = ? ");
    pst.setInt(1, categoryId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Category ID not found");
    }
    buildResources(db);
  }
  
  public void buildResources(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "SELECT max(entered) AS latest, count(task_id) AS count " +
      "FROM task " +
      "WHERE category_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      lastTaskEntered = rs.getTimestamp("latest");
      taskCount = rs.getInt("count");
    }
    rs.close();
    pst.close();
  }
  
  public boolean insert(Connection db) throws SQLException {
    String sql = null;
    if (!isValid()) {
      return false;
    }
    try {
      db.setAutoCommit(false);
      sql = "INSERT INTO lookup_task_category " +
          "(description, default_item, level, enabled) " +
          "VALUES (?, ?, ?, ?) ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setString(++i, description);
      pst.setBoolean(++i, defaultItem);
      pst.setInt(++i, level);
      pst.setBoolean(++i, enabled);
      pst.execute();
      this.id = DatabaseUtils.getCurrVal(db, "lookup_task_category_code_seq");
      pst.close();
      if (linkModuleId == Constants.TASK_CATEGORY_PROJECTS) {
        pst = db.prepareStatement(
          "INSERT INTO taskcategory_project " +
          "(category_id, project_id) " +
          "VALUES " +
          "(?, ?) ");
        pst.setInt(1, id);
        pst.setInt(2, linkItemId);
        pst.execute();
        pst.close();
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }
  
  public int update(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Record ID was not specified");
    }

    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE lookup_task_category " +
        "SET description = ?, default_item = ?, level = ?, enabled = ? " +
        "WHERE code = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, description);
    pst.setBoolean(++i, defaultItem);
    pst.setInt(++i, level);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }
  
  private boolean isValid() {
    if (linkModuleId == -1) {
      return false;
    }
    
    if (linkItemId == -1) {
      return false;
    }
    
    if (description == null || "".equals(description.trim())) {
      return false;
    }
    return true;
  }
  
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("code");
    description = rs.getString("description");
    defaultItem = rs.getBoolean("default_item");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
  }
}
