/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.admin.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id$
 * @created January 13, 2003
 */
public class RolePermission extends GenericBean {

  protected int id = -1;
  protected int roleId = -1;
  protected int permissionId = -1;
  protected boolean add = false;
  protected boolean view = false;
  protected boolean edit = false;
  protected boolean delete = false;


  /**
   * Constructor for the RolePermission object
   */
  public RolePermission() {
  }


  /**
   * Constructor for the RolePermission object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public RolePermission(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the RolePermission object
   *
   * @param db     Description of the Parameter
   * @param thisId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public RolePermission(Connection db, int thisId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM role_permission rp " +
        "WHERE rp.id = ? ");
    pst.setInt(1, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (roleId == -1) {
      throw new SQLException("RolePermission record not found.");
    }
  }


  /**
   * Sets the id attribute of the RolePermission object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the RolePermission object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the roleId attribute of the RolePermission object
   *
   * @param tmp The new roleId value
   */
  public void setRoleId(int tmp) {
    this.roleId = tmp;
  }


  /**
   * Sets the roleId attribute of the RolePermission object
   *
   * @param tmp The new roleId value
   */
  public void setRoleId(String tmp) {
    this.roleId = Integer.parseInt(tmp);
  }


  /**
   * Sets the permissionId attribute of the RolePermission object
   *
   * @param tmp The new permissionId value
   */
  public void setPermissionId(int tmp) {
    this.permissionId = tmp;
  }


  /**
   * Sets the permissionId attribute of the RolePermission object
   *
   * @param tmp The new permissionId value
   */
  public void setPermissionId(String tmp) {
    this.permissionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the add attribute of the RolePermission object
   *
   * @param tmp The new add value
   */
  public void setAdd(boolean tmp) {
    this.add = tmp;
  }


  /**
   * Sets the add attribute of the RolePermission object
   *
   * @param tmp The new add value
   */
  public void setAdd(String tmp) {
    this.add = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the view attribute of the RolePermission object
   *
   * @param tmp The new view value
   */
  public void setView(boolean tmp) {
    this.view = tmp;
  }


  /**
   * Sets the view attribute of the RolePermission object
   *
   * @param tmp The new view value
   */
  public void setView(String tmp) {
    this.view = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the edit attribute of the RolePermission object
   *
   * @param tmp The new edit value
   */
  public void setEdit(boolean tmp) {
    this.edit = tmp;
  }


  /**
   * Sets the edit attribute of the RolePermission object
   *
   * @param tmp The new edit value
   */
  public void setEdit(String tmp) {
    this.edit = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the delete attribute of the RolePermission object
   *
   * @param tmp The new delete value
   */
  public void setDelete(boolean tmp) {
    this.delete = tmp;
  }


  /**
   * Sets the delete attribute of the RolePermission object
   *
   * @param tmp The new delete value
   */
  public void setDelete(String tmp) {
    this.delete = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the id attribute of the RolePermission object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the roleId attribute of the RolePermission object
   *
   * @return The roleId value
   */
  public int getRoleId() {
    return roleId;
  }


  /**
   * Gets the permissionId attribute of the RolePermission object
   *
   * @return The permissionId value
   */
  public int getPermissionId() {
    return permissionId;
  }


  /**
   * Gets the add attribute of the RolePermission object
   *
   * @return The add value
   */
  public boolean getAdd() {
    return add;
  }


  /**
   * Gets the view attribute of the RolePermission object
   *
   * @return The view value
   */
  public boolean getView() {
    return view;
  }


  /**
   * Gets the edit attribute of the RolePermission object
   *
   * @return The edit value
   */
  public boolean getEdit() {
    return edit;
  }


  /**
   * Gets the delete attribute of the RolePermission object
   *
   * @return The delete value
   */
  public boolean getDelete() {
    return delete;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
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


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "role_permission_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO role_permission (" + (id > -1 ? "id, " : "") + "role_id, permission_id, role_view, " +
        "role_add, role_edit, role_delete) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?,?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, roleId);
    pst.setInt(++i, permissionId);
    pst.setBoolean(++i, view);
    pst.setBoolean(++i, add);
    pst.setBoolean(++i, edit);
    pst.setBoolean(++i, delete);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "role_permission_id_seq", id);
    return true;
  }
}

