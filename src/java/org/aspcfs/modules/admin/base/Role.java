//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.admin.base;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.DateFormat;
import com.darkhorseventures.framework.beans.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.utils.web.*;

/**
 *  Represents a Role (User Group)
 *
 *@author     matt rajkowski
 *@created    September 19, 2001
 *@version    $Id$
 */
public class Role extends GenericBean {
  //Role Properties
  protected int id = -1;
  protected String role = null;
  protected String description = null;
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
   *  Constructor for the Role object
   */
  public Role() { }


  /**
   *  Constructor for the Role object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public Role(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Role object
   *
   *@param  db                Description of Parameter
   *@param  roleId            Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public Role(Connection db, int roleId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM role " +
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
   *  Sets the Entered attribute of the Role object
   *
   *@param  tmp  The new Entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the Entered attribute of the Role object
   *
   *@param  tmp  The new Entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the Modified attribute of the Role object
   *
   *@param  tmp  The new Modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the Modified attribute of the Role object
   *
   *@param  tmp  The new Modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the Id attribute of the Role object
   *
   *@param  tmp  The new Id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the Id attribute of the Role object
   *
   *@param  tmp  The new Id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Role attribute of the Role object
   *
   *@param  tmp  The new Role value
   */
  public void setRole(String tmp) {
    this.role = tmp;
  }


  /**
   *  Sets the Description attribute of the Role object
   *
   *@param  tmp  The new Description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the Role object
   *
   *@param  tmp  The new EnteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the Role object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ModifiedBy attribute of the Role object
   *
   *@param  tmp  The new ModifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the ModifiedBy attribute of the Role object
   *
   *@param  tmp  The new ModifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Enabled attribute of the Role object
   *
   *@param  tmp  The new Enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Enabled attribute of the Role object
   *
   *@param  tmp  The new Enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the RequestItems attribute of the Role object
   *
   *@param  request  The new RequestItems value
   */
  public void setRequestItems(HttpServletRequest request) {
    permissionList = new RolePermissionList(request);
  }


  /**
   *  Sets the userCount attribute of the Role object
   *
   *@param  tmp  The new userCount value
   */
  public void setUserCount(int tmp) {
    this.userCount = tmp;
  }


  /**
   *  Sets the userCount attribute of the Role object
   *
   *@param  tmp  The new userCount value
   */
  public void setUserCount(String tmp) {
    this.userCount = Integer.parseInt(tmp);
  }


  /**
   *  Gets the Entered attribute of the Role object
   *
   *@return    The Entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the Modified attribute of the Role object
   *
   *@return    The Modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the ModifiedString attribute of the Role object
   *
   *@return    The ModifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the EnteredString attribute of the Role object
   *
   *@return    The EnteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the Id attribute of the Role object
   *
   *@return    The Id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the Role attribute of the Role object
   *
   *@return    The Role value
   */
  public String getRole() {
    return role;
  }


  /**
   *  Gets the Description attribute of the Role object
   *
   *@return    The Description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the EnteredBy attribute of the Role object
   *
   *@return    The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the Role object
   *
   *@return    The ModifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the Enabled attribute of the Role object
   *
   *@return    The Enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the PermissionList attribute of the Role object
   *
   *@return    The PermissionList value
   */
  public RolePermissionList getPermissionList() {
    return permissionList;
  }


  /**
   *  Gets the UserList attribute of the Role object
   *
   *@return    The UserList value
   */
  public UserList getUserList() {
    return userList;
  }


  /**
   *  Gets the userCount attribute of the Role object
   *
   *@return    The userCount value
   */
  public int getUserCount() {
    return userCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int update(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    if (!isValid(db)) {
      return -1;
    }
    int resultCount = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = null;
      StringBuffer sql = new StringBuffer();
      sql.append(
          "UPDATE role " +
          "SET role = ?, description = ?, modified = CURRENT_TIMESTAMP, " +
          "modifiedby = ? " +
          "WHERE modified = ? AND role_id = ? ");
      pst = db.prepareStatement(sql.toString());
      int i = 0;
      pst.setString(++i, this.getRole());
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getModifiedBy());
      pst.setTimestamp(++i, this.getModified());
      pst.setInt(++i, id);
      resultCount = pst.executeUpdate();
      pst.close();
      //Re-insert the permissions for this role
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(*) AS user_count " +
        "FROM access " +
        "WHERE role_id = ? " +
        "AND enabled = ? ");
    pst.setInt(++i, this.getId());
    pst.setBoolean(++i, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      int usercount = rs.getInt("user_count");
      if (usercount != 0) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("Active Users");
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }
    try {
      db.setAutoCommit(false);
      StringBuffer sql = new StringBuffer();
      sql.append("INSERT INTO role (role, description, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enteredby, modifiedby, enabled ) " +
          "VALUES (?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?, ?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, getRole());
      pst.setString(++i, getDescription());
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
      id = DatabaseUtils.getCurrVal(db, "role_role_id_seq");
      insertPermissions(db);
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = null;
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    if (buildUserCount(db, true)) {
      errors.put("actionError", "Role cannot be deleted... there are active users assigned to this role.");
      return false;
    }
    int recordCount = 0;
    try {
      db.setAutoCommit(false);
      if (buildUserCount(db, false)) {
        pst = db.prepareStatement(
            "UPDATE role " +
            "SET enabled = " + DatabaseUtils.getFalse(db) + " " +
            "WHERE role_id = " + id + " ");
        recordCount = pst.executeUpdate();
      } else {
        deletePermissions(db);
        pst = db.prepareStatement(
            "DELETE FROM role " +
            "WHERE role_id = " + id + " ");
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
      errors.put("actionError", "Role could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Adds a feature to the Permission attribute of the Role object
   *
   *@param  db                The feature to be added to the Permission
   *      attribute
   *@param  permissionId      The feature to be added to the Permission
   *      attribute
   *@param  add               The feature to be added to the Permission
   *      attribute
   *@param  view              The feature to be added to the Permission
   *      attribute
   *@param  edit              The feature to be added to the Permission
   *      attribute
   *@param  delete            The feature to be added to the Permission
   *      attribute
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void addPermission(Connection db, int permissionId, boolean add, boolean view, boolean edit, boolean delete) throws SQLException {
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO role_permission " +
        "(role_id, permission_id, role_add, role_view, role_edit, role_delete) " +
        "VALUES (?, ?, ?, ?, ?, ? ) ");
    pst.setInt(++i, getId());
    pst.setInt(++i, permissionId);
    pst.setBoolean(++i, add);
    pst.setBoolean(++i, view);
    pst.setBoolean(++i, edit);
    pst.setBoolean(++i, delete);
    pst.execute();
    pst.close();
  }


  /**
   *  Gets the Valid attribute of the Role object
   *
   *@param  db                Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected boolean isValid(Connection db) throws SQLException {
    errors.clear();
    if (role == null || role.trim().equals("")) {
      errors.put("roleError", "Role cannot be left blank");
    } else {
      if (isDuplicate(db)) {
        errors.put("roleError", "Role name is already in use");
      }
    }
    if (description == null || description.trim().equals("")) {
      errors.put("descriptionError", "Description cannot be left blank");
    }
    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
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
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected void buildResources(Connection db) throws SQLException {
    permissionList = new RolePermissionList(db, id);
    buildUserList(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected void buildUserList(Connection db) throws SQLException {
    userList.setRoleId(id);
    userList.setBuildHierarchy(buildHierarchy);
    userList.buildList(db);
  }


  /**
   *  Gets the Duplicate attribute of the Role object
   *
   *@param  db                Description of Parameter
   *@return                   The Duplicate value
   *@exception  SQLException  Description of Exception
   *@since
   */
  private boolean isDuplicate(Connection db) throws SQLException {
    boolean duplicate = false;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM role " +
        "WHERE lower(role) = lower(?)  " +
        "AND enabled = ? ");
    if (id > -1) {
      sql.append("AND role_id <> ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setString(1, getRole());
    pst.setBoolean(2, true);
    if (id > -1) {
      pst.setInt(3, id);
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  activeUsers       Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean buildUserCount(Connection db, boolean activeUsersOnly) throws SQLException {
    int resultCount = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS count " +
        "FROM access " +
        "WHERE role_id = ? " +
        "AND contact_id > 0 " +
        "AND (alias = -1 OR alias IS NULL) " +
        (activeUsersOnly ? "AND enabled = ? " : ""));
    pst.setInt(1, id);
    if(activeUsersOnly){
      pst.setBoolean(2, true);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      resultCount = rs.getInt("count");
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  private void insertPermissions(Connection db) throws SQLException {
    Iterator ipermission = permissionList.keySet().iterator();
    while (ipermission.hasNext()) {
      Permission thisPermission = (Permission) permissionList.get((String) ipermission.next());
      if (thisPermission.getEnabled()) {
        addPermission(db,
            thisPermission.getId(),
            thisPermission.getAdd(),
            thisPermission.getView(),
            thisPermission.getEdit(),
            thisPermission.getDelete());
      }
    }
  }
}

