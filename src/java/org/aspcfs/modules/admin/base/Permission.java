//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Represents a Permission item
 *
 *@author     mrajkowski
 *@created    September 20, 2001
 *@version    $Id$
 */
public class Permission extends GenericBean {
  
  protected int id = -1;
  protected int categoryId = -1;
  protected String categoryName = null;
  protected int permissionLevel = -1;
  protected String name = null;
  protected boolean add = false;
  protected boolean view = false;
  protected boolean edit = false;
  protected boolean delete = false;
  protected String description = null;
  protected boolean enabled = false;
  protected boolean active = true;
  
  public Permission() { }
  
  public Permission(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public Permission(Connection db, int permissionId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "SELECT p.*, c.category " +
      "FROM permission p, permission_category c " +
      "WHERE p.category_id = c.category_id " +
      "AND p.permission_id = ? ");
    pst.setInt(1, permissionId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Permission record not found.");
    }
    rs.close();
    pst.close();
  }
  
  public void setId(int tmp) { this.id = tmp; }
  public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
  public void setCategoryId(int tmp) { this.categoryId = tmp; }
  public void setCategoryId(String tmp) { this.categoryId = Integer.parseInt(tmp); }
  public void setCategoryName(String tmp) { this.categoryName = tmp; }
  public void setPermissionLevel(int tmp) { this.permissionLevel = tmp; }
  public void setPermissionLevel(String tmp) { this.permissionLevel = Integer.parseInt(tmp); }
  public void setName(String tmp) { this.name = tmp; }
  public void setAdd(boolean tmp) { this.add = tmp; }
  public void setAdd(String tmp) { this.add = DatabaseUtils.parseBoolean(tmp); }
  public void setView(boolean tmp) { this.view = tmp; }
  public void setView(String tmp) { this.view = DatabaseUtils.parseBoolean(tmp); }
  public void setEdit(boolean tmp) { this.edit = tmp; }
  public void setEdit(String tmp) { this.edit = DatabaseUtils.parseBoolean(tmp); }
  public void setDelete(boolean tmp) { this.delete = tmp; }
  public void setDelete(String tmp) { this.delete = DatabaseUtils.parseBoolean(tmp); }
  public void setDescription(String tmp) { this.description = tmp; }
  public void setEnabled(boolean tmp) { this.enabled = tmp; }
  public void setEnabled(String tmp) { this.enabled = DatabaseUtils.parseBoolean(tmp); }
  public void setActive(boolean tmp) { this.active = tmp; }
  public void setActive(String tmp) { this.active = DatabaseUtils.parseBoolean(tmp); }
  public int getId() { return id; }
  public int getCategoryId() { return categoryId; }
  public String getCategoryName() { return categoryName; }
  public int getPermissionLevel() { return permissionLevel; }
  public String getName() { return name; }
  public boolean getAdd() {
    if (enabled) {
      return add;
    } else {
      return false;
    }
  }
  public boolean getView() {
    if (enabled) {
      return view;
    } else {
      return false;
    }
  }
  public boolean getEdit() {
    if (enabled) {
      return edit;
    } else {
      return false;
    }
  }
  public boolean getDelete() {
    if (enabled) {
      return delete;
    } else {
      return false;
    }
  }
  public String getDescription() { return description; }
  public boolean getEnabled() { return enabled; }
  public boolean getActive() { return active; }

  protected void buildRecord(ResultSet rs) throws SQLException {
    //permission table
    id = rs.getInt("permission_id");
    categoryId = rs.getInt("category_id");
    name = rs.getString("permission");
    view = rs.getBoolean("permission_view");
    add = rs.getBoolean("permission_add");
    edit = rs.getBoolean("permission_edit");
    delete = rs.getBoolean("permission_delete");
    description = rs.getString("description");
    enabled = rs.getBoolean("enabled");
    active = rs.getBoolean("active");
    
    //permission_category table
    categoryName = rs.getString("category");
    permissionLevel = rs.getInt("level");
  }
  
  protected void buildRecord(HttpServletRequest request, int parseItem) {
    if (request.getParameter("permission" + parseItem + "id") != null) {
      this.setId(request.getParameter("permission" + parseItem + "id"));
    }
    if (request.getParameter("permission" + parseItem + "enabled") != null) {
      String action = request.getParameter("permission" + parseItem + "enabled").toLowerCase();
      this.setEnabled(action.equals("on"));
    }
    if (request.getParameter("permission" + parseItem + "add") != null) {
      String action = request.getParameter("permission" + parseItem + "add").toLowerCase();
      this.setAdd(action.equals("on"));
      this.setEnabled(true);
    }
    if (request.getParameter("permission" + parseItem + "view") != null) {
      String action = request.getParameter("permission" + parseItem + "view").toLowerCase();
      this.setView(action.equals("on"));
      this.setEnabled(true);
    }
    if (request.getParameter("permission" + parseItem + "edit") != null) {
      String action = request.getParameter("permission" + parseItem + "edit").toLowerCase();
      this.setEdit(action.equals("on"));
      this.setEnabled(true);
    }
    if (request.getParameter("permission" + parseItem + "delete") != null) {
      String action = request.getParameter("permission" + parseItem + "delete").toLowerCase();
      this.setDelete(action.equals("on"));
      this.setEnabled(true);
    }
  }
  
  public boolean insert(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "INSERT INTO permission (category_id, permission, permission_view, " +
      "permission_add, permission_edit, permission_delete, " +
      "description, level, enabled, active) " +
      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, categoryId);
    pst.setString(++i, name);
    pst.setBoolean(++i, view);
    pst.setBoolean(++i, add);
    pst.setBoolean(++i, edit);
    pst.setBoolean(++i, delete);
    pst.setString(++i, description);
    pst.setInt(++i, permissionLevel);
    pst.setBoolean(++i, enabled);
    pst.setBoolean(++i, active);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "permission_permission_id_seq");
    return true;
  }
}

