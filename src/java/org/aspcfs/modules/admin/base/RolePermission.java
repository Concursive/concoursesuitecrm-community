//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class RolePermission extends GenericBean {
  
  protected int id = -1;
  protected int roleId = -1;
  protected int permissionId = -1;
  protected boolean add = false;
  protected boolean view = false;
  protected boolean edit = false;
  protected boolean delete = false;
  
  public RolePermission() { }
  
  public RolePermission(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public RolePermission(Connection db, int thisId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "SELECT * " +
      "FROM role_permission rp " +
      "WHERE rp.id = ? ");
    pst.setInt(1, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("RolePermission record not found.");
    }
    rs.close();
    pst.close();
  }
  
  public void setId(int tmp) { this.id = tmp; }
  public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
  public void setRoleId(int tmp) { this.roleId = tmp; }
  public void setRoleId(String tmp) { this.roleId = Integer.parseInt(tmp); }
  public void setPermissionId(int tmp) { this.permissionId = tmp; }
  public void setPermissionId(String tmp) { this.permissionId = Integer.parseInt(tmp); }
  public void setAdd(boolean tmp) { this.add = tmp; }
  public void setAdd(String tmp) { this.add = DatabaseUtils.parseBoolean(tmp); }
  public void setView(boolean tmp) { this.view = tmp; }
  public void setView(String tmp) { this.view = DatabaseUtils.parseBoolean(tmp); }
  public void setEdit(boolean tmp) { this.edit = tmp; }
  public void setEdit(String tmp) { this.edit = DatabaseUtils.parseBoolean(tmp); }
  public void setDelete(boolean tmp) { this.delete = tmp; }
  public void setDelete(String tmp) { this.delete = DatabaseUtils.parseBoolean(tmp); }
  public int getId() { return id; }
  public int getRoleId() { return roleId; }
  public int getPermissionId() { return permissionId; }
  public boolean getAdd() { return add; }
  public boolean getView() { return view; }
  public boolean getEdit() { return edit; }
  public boolean getDelete() { return delete; }

  protected void buildRecord(ResultSet rs) throws SQLException {
    //role_permission table
    id = rs.getInt("id");
    roleId = rs.getInt("role_id");
    permissionId = rs.getInt("permission_id");
    view = rs.getBoolean("role_view");
    add = rs.getBoolean("role_add");
    edit = rs.getBoolean("role_edit");
    delete = rs.getBoolean("role_delete");
  }
  
  public boolean insert(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "INSERT INTO role_permission (role_id, permission_id, role_view, " +
      "role_add, role_edit, role_delete) " +
      "VALUES (?, ?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, roleId);
    pst.setInt(++i, permissionId);
    pst.setBoolean(++i, view);
    pst.setBoolean(++i, add);
    pst.setBoolean(++i, edit);
    pst.setBoolean(++i, delete);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "role_permission_id_seq");
    return true;
  }
}

