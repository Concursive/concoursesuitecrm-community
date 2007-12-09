/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.admin.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Represents a Permission item
 *
 * @author mrajkowski
 * @version $Id$
 * @created September 20, 2001
 */
public class Permission extends GenericBean {

  private static final long serialVersionUID = 2044602526197322790L;

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

  protected boolean offlineAdd = false;
  protected boolean offlineView = false;
  protected boolean offlineEdit = false;
  protected boolean offlineDelete = false;
  private Timestamp entered = null;
  private Timestamp modified = null;
  

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
   * Constructor for the Permission object
   *
   * @param db           Description of the Parameter
   * @param permissionName Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Permission(Connection db, String permissionName) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT p.*, c.category " +
        "FROM permission p, permission_category c " +
        "WHERE p.category_id = c.category_id " +
        "AND p.permission = ? ");
    pst.setString(1, permissionName);
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
   * @return the offlineAdd
   */
  public boolean getOfflineAdd() {
    if (enabled) {
      return offlineAdd;
    } else {
      return false;
    }
  }

  /**
   * @param offlineAdd the offlineAdd to set
   */
  public void setOfflineAdd(boolean offlineAdd) {
    this.offlineAdd = offlineAdd;
  }

  /**
   * @param offlineAdd the offlineAdd to set
   */
  public void setOfflineAdd(String offlineAdd) {
    this.offlineAdd = DatabaseUtils.parseBoolean(offlineAdd);
  }

  /**
   * @return the offlineDelete
   */
  public boolean getOfflineDelete() {
    if (enabled) {
      return offlineDelete;
    } else {
      return false;
    }
  }

  /**
   * @param offlineDelete the offlineDelete to set
   */
  public void setOfflineDelete(boolean offlineDelete) {
    this.offlineDelete = offlineDelete;
  }

  /**
   * @param offlineDelete the offlineDelete to set
   */
  public void setOfflineDelete(String offlineDelete) {
    this.offlineDelete = DatabaseUtils.parseBoolean(offlineDelete);
  }

  /**
   * @return the offlineEdit
   */
  public boolean getOfflineEdit() {
    if (enabled) {
      return offlineEdit;
    } else {
      return false;
    }
  }

  /**
   * @param offlineEdit the offlineEdit to set
   */
  public void setOfflineEdit(boolean offlineEdit) {
    this.offlineEdit = offlineEdit;
  }

  /**
   * @param offlineEdit the offlineEdit to set
   */
  public void setOfflineEdit(String offlineEdit) {
    this.offlineEdit = DatabaseUtils.parseBoolean(offlineEdit);
  }

  /**
   * @return the offlineView
   */
  public boolean getOfflineView() {
    if (enabled) {
      return offlineView;
    } else {
      return false;
    }
  }

  /**
   * @param offlineView the offlineView to set
   */
  public void setOfflineView(boolean offlineView) {
    this.offlineView = offlineView;
  }

  /**
   * @param offlineView the offlineView to set
   */
  public void setOfflineView(String offlineView) {
    this.offlineView = DatabaseUtils.parseBoolean(offlineView);
  }

  /**
   * @return the entered
   */
  public Timestamp getEntered() {
    return entered;
  }

  /**
   * @param entered the entered to set
   */
  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }

  /**
   * @param entered the entered to set
   */
  public void setEntered(String entered) {
    this.entered = DateUtils.parseTimestampString(entered);
  }

  /**
   * @return the modified
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   * @param modified the modified to set
   */
  public void setModified(Timestamp modified) {
    this.modified = modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(String modified) {
    this.modified = DateUtils.parseTimestampString(modified);
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
    
    //offline permissions
    offlineView = rs.getBoolean("permission_offline_view");
    offlineAdd = rs.getBoolean("permission_offline_add");
    offlineEdit = rs.getBoolean("permission_offline_edit");
    offlineDelete = rs.getBoolean("permission_offline_delete");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
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
    if (request.getParameter("permission" + parseItem + "offline_add") != null) {
      String action = request.getParameter("permission" + parseItem + "offline_add").toLowerCase();
      this.setOfflineAdd(DatabaseUtils.parseBoolean(action));
      this.setEnabled(true);
    }
    if (request.getParameter("permission" + parseItem + "offline_view") != null) {
      String action = request.getParameter("permission" + parseItem + "offline_view").toLowerCase();
      this.setOfflineView(DatabaseUtils.parseBoolean(action));
      this.setEnabled(true);
    }
    if (request.getParameter("permission" + parseItem + "offline_edit") != null) {
      String action = request.getParameter("permission" + parseItem + "offline_edit").toLowerCase();
      this.setOfflineEdit(DatabaseUtils.parseBoolean(action));
      this.setEnabled(true);
    }
    if (request.getParameter("permission" + parseItem + "offline_delete") != null) {
      String action = request.getParameter(
          "permission" + parseItem + "offline_delete").toLowerCase();
      this.setOfflineDelete(DatabaseUtils.parseBoolean(action));
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
    StringBuffer sqlInsert = new StringBuffer( 
      "INSERT INTO permission (" + (id > -1 ? "permission_id, " : "") + "category_id, permission, permission_view, " +
      "permission_add, permission_edit, permission_delete, " +
      "description, " + DatabaseUtils.addQuotes(db, "level") +
      ", enabled, " + DatabaseUtils.addQuotes(db, "active") +
      ", viewpoints" +
      ", permission_offline_view, permission_offline_add, permission_offline_edit, permission_offline_delete");
      sqlInsert.append(", entered, modified");
      
      sqlInsert.append(") VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
          ", ?, ?, ?, ?");
      if(entered != null){
        sqlInsert.append(", ?");
      } else {
        sqlInsert.append(", " + DatabaseUtils.getCurrentTimestamp(db));
      }
      if(modified != null){
        sqlInsert.append(", ?");
      } else {
        sqlInsert.append(", " + DatabaseUtils.getCurrentTimestamp(db));
      }
      sqlInsert.append(") ");

      PreparedStatement pst = db.prepareStatement(sqlInsert.toString());
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
    pst.setBoolean(++i, offlineView);
    pst.setBoolean(++i, offlineAdd);
    pst.setBoolean(++i, offlineEdit);
    pst.setBoolean(++i, offlineDelete);
    if(entered != null){
      pst.setTimestamp(++i, entered);
    }
    if(modified != null){
      pst.setTimestamp(++i, modified);
    }
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "permission_permission_id_seq", id);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db
   * @return Description of the Returned Value
   * @throws SQLException 
   */
  public boolean update(Connection db) throws SQLException{
    if(id < 0){
      return false;
    }
    
    StringBuffer sqlInsert = new StringBuffer( 
      "UPDATE permission SET " +
      "category_id = ?, " +
      "permission = ?, " +
      "permission_view = ?, " + "permission_add = ?, " + "permission_edit = ?, " + "permission_delete = ?, " +
      "description = ?, " +
      "level = ?, " +
      "enabled = ?, " +
      "active = ?, " +
      "viewpoints = ?, " +
      "permission_offline_view = ?, " + "permission_offline_add = ?, " + "permission_offline_edit = ?, " + "permission_offline_delete = ?, " +
      "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
      "WHERE permission_id = ?");

    PreparedStatement pst = db.prepareStatement(sqlInsert.toString());
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
    pst.setBoolean(++i, viewpoints);
    pst.setBoolean(++i, offlineView);
    pst.setBoolean(++i, offlineAdd);
    pst.setBoolean(++i, offlineEdit);
    pst.setBoolean(++i, offlineDelete);
    pst.setInt(++i, id);
    pst.execute();
    pst.close();
    return true;
  }
  
  /**
   * Description of the Method
   *
   * @param db
   * @param name
   * @return
   * @throws SQLException Description of the Returned Value
   */
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
