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

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a Permission item
 *
 * @author mrajkowski
 * @version $Id$
 * @created September 20, 2001
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
  protected boolean viewpoints = false;


  /**
   * Constructor for the Permission object
   */
  public Permission() {
  }


  /**
   * Constructor for the Permission object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Permission(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the Permission object
   *
   * @param db           Description of the Parameter
   * @param permissionId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
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
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Permission record not found.");
    }
  }


  /**
   * Sets the id attribute of the Permission object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Permission object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryId attribute of the Permission object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the Permission object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryName attribute of the Permission object
   *
   * @param tmp The new categoryName value
   */
  public void setCategoryName(String tmp) {
    this.categoryName = tmp;
  }


  /**
   * Sets the permissionLevel attribute of the Permission object
   *
   * @param tmp The new permissionLevel value
   */
  public void setPermissionLevel(int tmp) {
    this.permissionLevel = tmp;
  }


  /**
   * Sets the permissionLevel attribute of the Permission object
   *
   * @param tmp The new permissionLevel value
   */
  public void setPermissionLevel(String tmp) {
    this.permissionLevel = Integer.parseInt(tmp);
  }


  /**
   * Sets the name attribute of the Permission object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the add attribute of the Permission object
   *
   * @param tmp The new add value
   */
  public void setAdd(boolean tmp) {
    this.add = tmp;
  }


  /**
   * Sets the add attribute of the Permission object
   *
   * @param tmp The new add value
   */
  public void setAdd(String tmp) {
    this.add = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the view attribute of the Permission object
   *
   * @param tmp The new view value
   */
  public void setView(boolean tmp) {
    this.view = tmp;
  }


  /**
   * Sets the view attribute of the Permission object
   *
   * @param tmp The new view value
   */
  public void setView(String tmp) {
    this.view = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the edit attribute of the Permission object
   *
   * @param tmp The new edit value
   */
  public void setEdit(boolean tmp) {
    this.edit = tmp;
  }


  /**
   * Sets the edit attribute of the Permission object
   *
   * @param tmp The new edit value
   */
  public void setEdit(String tmp) {
    this.edit = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the delete attribute of the Permission object
   *
   * @param tmp The new delete value
   */
  public void setDelete(boolean tmp) {
    this.delete = tmp;
  }


  /**
   * Sets the delete attribute of the Permission object
   *
   * @param tmp The new delete value
   */
  public void setDelete(String tmp) {
    this.delete = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the description attribute of the Permission object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the enabled attribute of the Permission object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the Permission object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the active attribute of the Permission object
   *
   * @param tmp The new active value
   */
  public void setActive(boolean tmp) {
    this.active = tmp;
  }


  /**
   * Sets the active attribute of the Permission object
   *
   * @param tmp The new active value
   */
  public void setActive(String tmp) {
    this.active = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the viewpoints attribute of the Permission object
   *
   * @param tmp The new viewpoints value
   */
  public void setViewpoints(boolean tmp) {
    this.viewpoints = tmp;
  }


  /**
   * Sets the viewpoints attribute of the Permission object
   *
   * @param tmp The new viewpoints value
   */
  public void setViewpoints(String tmp) {
    this.viewpoints = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the id attribute of the Permission object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the categoryId attribute of the Permission object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Gets the categoryName attribute of the Permission object
   *
   * @return The categoryName value
   */
  public String getCategoryName() {
    return categoryName;
  }


  /**
   * Gets the permissionLevel attribute of the Permission object
   *
   * @return The permissionLevel value
   */
  public int getPermissionLevel() {
    return permissionLevel;
  }


  /**
   * Gets the name attribute of the Permission object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the add attribute of the Permission object
   *
   * @return The add value
   */
  public boolean getAdd() {
    if (enabled) {
      return add;
    } else {
      return false;
    }
  }


  /**
   * Gets the view attribute of the Permission object
   *
   * @return The view value
   */
  public boolean getView() {
    if (enabled) {
      return view;
    } else {
      return false;
    }
  }


  /**
   * Gets the edit attribute of the Permission object
   *
   * @return The edit value
   */
  public boolean getEdit() {
    if (enabled) {
      return edit;
    } else {
      return false;
    }
  }


  /**
   * Gets the delete attribute of the Permission object
   *
   * @return The delete value
   */
  public boolean getDelete() {
    if (enabled) {
      return delete;
    } else {
      return false;
    }
  }


  /**
   * Gets the description attribute of the Permission object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the enabled attribute of the Permission object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the active attribute of the Permission object
   *
   * @return The active value
   */
  public boolean getActive() {
    return active;
  }


  /**
   * Gets the viewpoints attribute of the Permission object
   *
   * @return The viewpoints value
   */
  public boolean getViewpoints() {
    return viewpoints;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
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
    viewpoints = rs.getBoolean("viewpoints");

    //permission_category table
    categoryName = rs.getString("category");
    permissionLevel = rs.getInt("level");
  }


  /**
   * Description of the Method
   *
   * @param request   Description of the Parameter
   * @param parseItem Description of the Parameter
   */
  protected void buildRecord(HttpServletRequest request, int parseItem) {
    if (request.getParameter("permission" + parseItem + "id") != null) {
      this.setId(request.getParameter("permission" + parseItem + "id"));
    }
    if (request.getParameter("permission" + parseItem + "enabled") != null) {
      String action = request.getParameter(
          "permission" + parseItem + "enabled").toLowerCase();
      this.setEnabled(action.equals("on"));
    }
    if (request.getParameter("permission" + parseItem + "add") != null) {
      String action = request.getParameter("permission" + parseItem + "add").toLowerCase();
      this.setAdd(DatabaseUtils.parseBoolean(action));
      this.setEnabled(true);
    }
    if (request.getParameter("permission" + parseItem + "view") != null) {
      String action = request.getParameter("permission" + parseItem + "view").toLowerCase();
      this.setView(DatabaseUtils.parseBoolean(action));
      this.setEnabled(true);
    }
    if (request.getParameter("permission" + parseItem + "edit") != null) {
      String action = request.getParameter("permission" + parseItem + "edit").toLowerCase();
      this.setEdit(DatabaseUtils.parseBoolean(action));
      this.setEnabled(true);
    }
    if (request.getParameter("permission" + parseItem + "delete") != null) {
      String action = request.getParameter(
          "permission" + parseItem + "delete").toLowerCase();
      this.setDelete(DatabaseUtils.parseBoolean(action));
      this.setEnabled(true);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "permission_permission_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO permission (" + (id > -1 ? "permission_id, " : "") + "category_id, permission, permission_view, " +
        "permission_add, permission_edit, permission_delete, " +
        "description, \"level\", enabled, \"active\", viewpoints) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
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
    pst.setBoolean(++i, viewpoints);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "permission_permission_id_seq", id);
    return true;
  }

  public static int lookupId(Connection db, String name) throws SQLException {
    if (name == null) {
      throw new SQLException("Invalid Permission Name");
    }
    int i = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT permission_id " +
        "FROM permission " +
        "WHERE permission = ? ");
    pst.setString(1, name);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      i = rs.getInt("permission_id");
    }
    rs.close();
    pst.close();
    return i;
  }
}
