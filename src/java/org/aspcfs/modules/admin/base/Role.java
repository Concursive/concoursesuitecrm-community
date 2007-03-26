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
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Iterator;

/**
 * Represents a Role (User Group)
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 19, 2001
 */
public class Role extends GenericBean {

  private static final long serialVersionUID = 3296299115328316895L;
  //Role Properties
  protected int id = -1;
  protected String role = null;
  protected String description = null;
  protected int roleType = -1; //regular or portal user type (engineer, customer...etc)
  protected java.sql.Timestamp entered = null;
  protected java.sql.Timestamp modified = null;
  protected int enteredBy = -1;
  protected int modifiedBy = -1;
  protected boolean enabled = true;
  //Helper Properties
  protected int userCount = -1;
  //Related Collections
  protected RolePermissionList permissionList = new RolePermissionList();
  protected UserList userList = new UserList();
  //Rules for building object
  protected boolean buildHierarchy = false;


  /**
   * Constructor for the Role object
   */
  public Role() {
  }


  /**
   * Constructor for the Role object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  public Role(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the Role object
   *
   * @param db     Description of Parameter
   * @param roleId Description of Parameter
   * @throws SQLException Description of Exception
   */
  public Role(Connection db, int roleId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
            "FROM " + DatabaseUtils.addQuotes(db, "role") + " " +
            "WHERE role_id = ? ");
    pst.setInt(1, roleId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Role record not found.");
    }
    buildResources(db);
  }


  /**
   * Sets the Entered attribute of the Role object
   *
   * @param tmp The new Entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the Entered attribute of the Role object
   *
   * @param tmp The new Entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the Modified attribute of the Role object
   *
   * @param tmp The new Modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the Modified attribute of the Role object
   *
   * @param tmp The new Modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the Id attribute of the Role object
   *
   * @param tmp The new Id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the Id attribute of the Role object
   *
   * @param tmp The new Id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the Role attribute of the Role object
   *
   * @param tmp The new Role value
   */
  public void setRole(String tmp) {
    this.role = tmp;
  }


  /**
   * Sets the Description attribute of the Role object
   *
   * @param tmp The new Description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the roleType attribute of the Role object
   *
   * @param tmp The new roleType value
   */
  public void setRoleType(int tmp) {
    this.roleType = tmp;
  }


  /**
   * Sets the roleType attribute of the Role object
   *
   * @param tmp The new roleType value
   */
  public void setRoleType(String tmp) {
    this.roleType = Integer.parseInt(tmp);
  }


  /**
   * Sets the EnteredBy attribute of the Role object
   *
   * @param tmp The new EnteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the EnteredBy attribute of the Role object
   *
   * @param tmp The new EnteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the ModifiedBy attribute of the Role object
   *
   * @param tmp The new ModifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the ModifiedBy attribute of the Role object
   *
   * @param tmp The new ModifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the Enabled attribute of the Role object
   *
   * @param tmp The new Enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the Enabled attribute of the Role object
   *
   * @param tmp The new Enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the RequestItems attribute of the Role object
   *
   * @param request The new RequestItems value
   */
  public void setRequestItems(HttpServletRequest request) {
    permissionList = new RolePermissionList(request);
  }


  /**
   * Sets the userCount attribute of the Role object
   *
   * @param tmp The new userCount value
   */
  public void setUserCount(int tmp) {
    this.userCount = tmp;
  }


  /**
   * Sets the userCount attribute of the Role object
   *
   * @param tmp The new userCount value
   */
  public void setUserCount(String tmp) {
    this.userCount = Integer.parseInt(tmp);
  }


  /**
   * Gets the Entered attribute of the Role object
   *
   * @return The Entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the Modified attribute of the Role object
   *
   * @return The Modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the ModifiedString attribute of the Role object
   *
   * @return The ModifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the EnteredString attribute of the Role object
   *
   * @return The EnteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the Id attribute of the Role object
   *
   * @return The Id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the Role attribute of the Role object
   *
   * @return The Role value
   */
  public String getRole() {
    return role;
  }


  /**
   * Gets the Description attribute of the Role object
   *
   * @return The Description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the roleType attribute of the Role object
   *
   * @return The roleType value
   */
  public int getRoleType() {
    return roleType;
  }


  /**
   * Gets the EnteredBy attribute of the Role object
   *
   * @return The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the ModifiedBy attribute of the Role object
   *
   * @return The ModifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the Enabled attribute of the Role object
   *
   * @return The Enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the PermissionList attribute of the Role object
   *
   * @return The PermissionList value
   */
  public RolePermissionList getPermissionList() {
    return permissionList;
  }


  /**
   * Gets the UserList attribute of the Role object
   *
   * @return The UserList value
   */
  public UserList getUserList() {
    return userList;
  }


  /**
   * Gets the userCount attribute of the Role object
   *
   * @return The userCount value
   */
  public int getUserCount() {
    return userCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int update(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    int resultCount = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = null;
      StringBuffer sql = new StringBuffer();
      sql.append(
          "UPDATE " + DatabaseUtils.addQuotes(db, "role") + " " +
              "SET " + DatabaseUtils.addQuotes(db, "role") + " = ?, description = ?, role_type = ?, " +
              "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
              "modifiedby = ?, enabled = ? " +
              "WHERE modified " + ((this.getModified() == null) ? "IS NULL " : "= ? ") +
              "AND role_id = ? ");
      pst = db.prepareStatement(sql.toString());
      int i = 0;
      pst.setString(++i, this.getRole());
      pst.setString(++i, this.getDescription());
      DatabaseUtils.setInt(pst, ++i, getRoleType());
      pst.setInt(++i, this.getModifiedBy());
      pst.setBoolean(++i, this.getEnabled());
      if (this.getModified() != null) {
        pst.setTimestamp(++i, this.getModified());
      }
      pst.setInt(++i, id);
      resultCount = pst.executeUpdate();
      pst.close();
      //Reinsert the permissions for this role
      deletePermissions(db);
      insertPermissions(db);
      db.commit();
    } catch (Exception e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(*) AS user_count " +
            "FROM " + DatabaseUtils.addQuotes(db, "access") + " " +
            "WHERE role_id = ? " +
            "AND user_id <> 0 " +
            "AND enabled = ? ");
    pst.setInt(++i, this.getId());
    pst.setBoolean(++i, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      int usercount = rs.getInt("user_count");
      if (usercount != 0) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("activeUsers");
        thisDependency.setCount(usercount);
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }
    }
    rs.close();
    pst.close();
    return dependencyList;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      id = DatabaseUtils.getNextSeq(db, "role_role_id_seq");
      if (commit) {
        db.setAutoCommit(false);
      }
      StringBuffer sql = new StringBuffer();
      sql.append("INSERT INTO " + DatabaseUtils.addQuotes(db, "role") + " (" + DatabaseUtils.addQuotes(db, "role") + ", description, role_type, ");
      if (id > -1) {
        sql.append("role_id, ");
      }
      sql.append("entered, modified, ");
      sql.append(
          "enteredby, modifiedby, enabled ) " +
              "VALUES (?, ?, ?, ");
      if (id > -1) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      if (modified != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, getRole());
      pst.setString(++i, getDescription());
      DatabaseUtils.setInt(pst, ++i, getRoleType());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setInt(++i, getEnteredBy());
      pst.setInt(++i, getModifiedBy());
      pst.setBoolean(++i, enabled);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "role_role_id_seq", id);
      insertPermissions(db);
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = null;
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    if (buildUserCount(db, true)) {
      return false;
    }
    int recordCount = 0;
    try {
      db.setAutoCommit(false);
      if (buildUserCount(db, false)) {
        pst = db.prepareStatement(
            "UPDATE " + DatabaseUtils.addQuotes(db, "role") + " " +
                "SET enabled = ?, " +
                "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
                "WHERE role_id = ? ");
        pst.setBoolean(1, false);
        pst.setInt(2, id);
        recordCount = pst.executeUpdate();
      } else {
        deletePermissions(db);
        pst = db.prepareStatement(
            "DELETE FROM " + DatabaseUtils.addQuotes(db, "role") + " " +
                "WHERE role_id = ? ");
        pst.setInt(1, id);
        recordCount = pst.executeUpdate();
      }
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    if (recordCount == 0) {
      return false;
    } else {
      return true;
    }
  }


  /**
   * Adds a feature to the Permission attribute of the Role object
   *
   * @param db            The feature to be added to the Permission
   *                      attribute
   * @param permissionId  The feature to be added to the Permission
   *                      attribute
   * @param add           The feature to be added to the Permission
   *                      attribute
   * @param view          The feature to be added to the Permission
   *                      attribute
   * @param edit          The feature to be added to the Permission
   *                      attribute
   * @param delete        The feature to be added to the Permission
   *                      attribute
   * @param offlineAdd    The feature to be added to the Permission
   *                      attribute
   * @param offlineView   The feature to be added to the Permission
   *                      attribute
   * @param offlineEdit   The feature to be added to the Permission
   *                      attribute
   * @param offlineDelete The feature to be added to the Permission
   *                      attribute
   * @throws SQLException Description of Exception
   */
  public void addPermission(Connection db, int permissionId, boolean add, boolean view, boolean edit, boolean delete, boolean offlineAdd, boolean offlineView, boolean offlineEdit, boolean offlineDelete) throws SQLException {
    int i = 0;
    int rolePermissionId = DatabaseUtils.getNextSeq(
        db, "role_permission_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO role_permission " +
        "(" + (rolePermissionId > -1 ? "id, " : "") + "role_id, permission_id, role_add, role_view, role_edit, role_delete" +
        ", role_offline_add, role_offline_view, role_offline_edit, role_offline_delete" +
        ") " +
        "VALUES (" + (rolePermissionId > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ? " +
        ", ?, ?, ?, ?" +
        ") ");
    if (rolePermissionId > -1) {
      pst.setInt(++i, rolePermissionId);
    }
    pst.setInt(++i, getId());
    pst.setInt(++i, permissionId);
    pst.setBoolean(++i, add);
    pst.setBoolean(++i, view);
    pst.setBoolean(++i, edit);
    pst.setBoolean(++i, delete);
    pst.setBoolean(++i, offlineAdd);
    pst.setBoolean(++i, offlineView);
    pst.setBoolean(++i, offlineEdit);
    pst.setBoolean(++i, offlineDelete);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //role table
    id = rs.getInt("role_id");
    role = rs.getString("role");
    description = rs.getString("description");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    roleType = DatabaseUtils.getInt(rs, "role_type");
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildResources(Connection db) throws SQLException {
    permissionList = new RolePermissionList(db, id);
    buildUserList(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildUserList(Connection db) throws SQLException {
    userList.setRoleId(id);
    userList.setBuildHierarchy(buildHierarchy);
    userList.buildList(db);
  }


  /**
   * Gets the Duplicate attribute of the Role object
   *
   * @param db Description of Parameter
   * @return The Duplicate value
   * @throws SQLException Description of Exception
   */
  public boolean isDuplicate(Connection db) throws SQLException {
    boolean duplicate = false;
    int i = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
            "FROM " + DatabaseUtils.addQuotes(db, "role") + " " +
            "WHERE " + DatabaseUtils.toLowerCase(db) + "(" + DatabaseUtils.addQuotes(db, "role") + ") = ? " +
            "AND enabled = ? ");
    if (id > -1) {
      sql.append("AND role_id <> ? ");
    }
    if (roleType > -1) {
      sql.append("AND role_type = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setString(++i, getRole().toLowerCase());
    pst.setBoolean(++i, true);
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (roleType > -1) {
      pst.setInt(++i, roleType);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      duplicate = true;
    }
    rs.close();
    pst.close();
    return duplicate;
  }


  /**
   * Description of the Method
   *
   * @param db              Description of Parameter
   * @param activeUsersOnly Description of the Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean buildUserCount(Connection db, boolean activeUsersOnly) throws SQLException {
    int resultCount = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS thecount " +
            "FROM " + DatabaseUtils.addQuotes(db, "access") + " " +
            "WHERE role_id = ? " +
            "AND contact_id > 0 " +
            "AND (alias = -1 OR alias IS NULL) " +
            (activeUsersOnly ? "AND enabled = ? " : ""));
    pst.setInt(1, id);
    if (activeUsersOnly) {
      pst.setBoolean(2, true);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      resultCount = rs.getInt("thecount");
    }
    rs.close();
    pst.close();
    userCount = resultCount;
    if (resultCount > 0) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  private void deletePermissions(Connection db) throws SQLException {
    PreparedStatement pst = null;
    pst = db.prepareStatement(
        "DELETE FROM role_permission " +
            "WHERE role_id = " + id);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  private void insertPermissions(Connection db) throws SQLException {
    Iterator ipermission = permissionList.keySet().iterator();
    while (ipermission.hasNext()) {
      Permission thisPermission = (Permission) permissionList.get(
          (String) ipermission.next());
      if (thisPermission.getEnabled()) {
        addPermission(
            db,
            thisPermission.getId(),
            thisPermission.getAdd(),
            thisPermission.getView(),
            thisPermission.getEdit(),
            thisPermission.getDelete(),
            thisPermission.getOfflineAdd(),
            thisPermission.getOfflineView(),
            thisPermission.getOfflineEdit(),
            thisPermission.getOfflineDelete()
        );
      }
    }
  }
}

