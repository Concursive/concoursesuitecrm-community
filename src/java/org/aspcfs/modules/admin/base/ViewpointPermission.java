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
 * @created February 24, 2003
 */
public class ViewpointPermission extends GenericBean {
  private int id = -1;
  private int viewpointId = -1;
  private int permissionId = -1;
  private boolean add = false;
  private boolean view = false;
  private boolean edit = false;
  private boolean delete = false;


  /**
   * Constructor for the ViewpointPermission object
   */
  public ViewpointPermission() {
  }


  /**
   * Constructor for the ViewpointPermission object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ViewpointPermission(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the ViewpointPermission object
   *
   * @param db     Description of the Parameter
   * @param thisId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ViewpointPermission(Connection db, int thisId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
            "FROM viewpoint_permission vp " +
            "WHERE vp.vp_permission_id = ? ");
    pst.setInt(1, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("ViewpointPermission record not found.");
    }
  }


  /**
   * Sets the id attribute of the ViewpointPermission object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ViewpointPermission object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the viewpointId attribute of the ViewpointPermission object
   *
   * @param tmp The new viewpointId value
   */
  public void setViewpointId(int tmp) {
    this.viewpointId = tmp;
  }


  /**
   * Sets the viewpointId attribute of the ViewpointPermission object
   *
   * @param tmp The new viewpointId value
   */
  public void setViewpointId(String tmp) {
    this.viewpointId = Integer.parseInt(tmp);
  }


  /**
   * Sets the permissionId attribute of the ViewpointPermission object
   *
   * @param tmp The new permissionId value
   */
  public void setPermissionId(int tmp) {
    this.permissionId = tmp;
  }


  /**
   * Sets the permissionId attribute of the ViewpointPermission object
   *
   * @param tmp The new permissionId value
   */
  public void setPermissionId(String tmp) {
    this.permissionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the add attribute of the ViewpointPermission object
   *
   * @param tmp The new add value
   */
  public void setAdd(boolean tmp) {
    this.add = tmp;
  }


  /**
   * Sets the add attribute of the ViewpointPermission object
   *
   * @param tmp The new add value
   */
  public void setAdd(String tmp) {
    this.add = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the view attribute of the ViewpointPermission object
   *
   * @param tmp The new view value
   */
  public void setView(boolean tmp) {
    this.view = tmp;
  }


  /**
   * Sets the view attribute of the ViewpointPermission object
   *
   * @param tmp The new view value
   */
  public void setView(String tmp) {
    this.view = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the edit attribute of the ViewpointPermission object
   *
   * @param tmp The new edit value
   */
  public void setEdit(boolean tmp) {
    this.edit = tmp;
  }


  /**
   * Sets the edit attribute of the ViewpointPermission object
   *
   * @param tmp The new edit value
   */
  public void setEdit(String tmp) {
    this.edit = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the delete attribute of the ViewpointPermission object
   *
   * @param tmp The new delete value
   */
  public void setDelete(boolean tmp) {
    this.delete = tmp;
  }


  /**
   * Sets the delete attribute of the ViewpointPermission object
   *
   * @param tmp The new delete value
   */
  public void setDelete(String tmp) {
    this.delete = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the id attribute of the ViewpointPermission object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the viewpointId attribute of the ViewpointPermission object
   *
   * @return The viewpointId value
   */
  public int getViewpointId() {
    return viewpointId;
  }


  /**
   * Gets the permissionId attribute of the ViewpointPermission object
   *
   * @return The permissionId value
   */
  public int getPermissionId() {
    return permissionId;
  }


  /**
   * Gets the add attribute of the ViewpointPermission object
   *
   * @return The add value
   */
  public boolean getAdd() {
    return add;
  }


  /**
   * Gets the view attribute of the ViewpointPermission object
   *
   * @return The view value
   */
  public boolean getView() {
    return view;
  }


  /**
   * Gets the edit attribute of the ViewpointPermission object
   *
   * @return The edit value
   */
  public boolean getEdit() {
    return edit;
  }


  /**
   * Gets the delete attribute of the ViewpointPermission object
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
  private void buildRecord(ResultSet rs) throws SQLException {
    //vp_permission table
    id = rs.getInt("vp_permission_id");
    viewpointId = rs.getInt("viewpoint_id");
    permissionId = rs.getInt("permission_id");
    view = rs.getBoolean("viewpoint_view");
    add = rs.getBoolean("viewpoint_add");
    edit = rs.getBoolean("viewpoint_edit");
    delete = rs.getBoolean("viewpoint_delete");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "viewpoint_per_vp_permission_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO viewpoint_permission (" +
            (id > -1 ? "vp_permission_id, " : "") + "viewpoint_id, permission_id, viewpoint_view, " +
            "viewpoint_add, viewpoint_edit, viewpoint_delete) " +
            "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, viewpointId);
    pst.setInt(++i, permissionId);
    pst.setBoolean(++i, view);
    pst.setBoolean(++i, add);
    pst.setBoolean(++i, edit);
    pst.setBoolean(++i, delete);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "viewpoint_per_vp_permission_seq", id);
    return true;
  }
}

