//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Represents a Role (User Group)
 *
 *@author     mrajkowski
 *@created    September 19, 2001
 *@version    $Id$
 */
public class Role extends GenericBean {
  protected int id = -1;
  protected String role = null;
  protected String description = null;
  protected String entered = "";
  protected int enteredBy = -1;
  protected String modified = null;
  protected int modifiedBy = -1;
  protected boolean enabled = true;
  protected RolePermissionList permissionList = new RolePermissionList();
  protected UserList userList = new UserList();
  
  protected boolean buildHierarchy = false;
  
  public Role() {}
  
  public Role(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public Role(Connection db, int roleId) throws SQLException {
    Statement st = null;
    ResultSet rs = null;

    String sql =
      "SELECT * FROM role " +
      "WHERE role_id = " + roleId + " ";
    
    st = db.createStatement();
    rs = st.executeQuery(sql);
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Role record not found.");
    }
    rs.close();
    st.close();
    
    buildResources(db);
  }
  
  public void setId(int tmp) { this.id = tmp; }
  public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
  public void setRole(String tmp) { this.role = tmp; }
  public void setDescription(String tmp) { this.description = tmp; }
  public void setEntered(String tmp) { this.entered = tmp; }
  public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
  public void setEnteredBy(String tmp) { this.enteredBy = Integer.parseInt(tmp); }
  public void setModified(String tmp) { this.modified = tmp; }
  public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
  public void setModifiedBy(String tmp) { this.modifiedBy = Integer.parseInt(tmp); }
  public void setEnabled(boolean tmp) { this.enabled = tmp; }
  public void setEnabled(String tmp) {
    if (tmp.toLowerCase().equals("false")) {
      this.enabled = false;
    } else {
      this.enabled = true;
    }
  }
  
  public void setRequestItems(HttpServletRequest request) {
    permissionList = new RolePermissionList(request);
  }
  
  public int getId() { return id; }
  public String getRole() { return role; }
  public String getDescription() { return description; }
  public String getEntered() { return entered; }
  public int getEnteredBy() { return enteredBy; }
  public String getModified() { return modified; }
  public int getModifiedBy() { return modifiedBy; }
  public boolean getEnabled() { return enabled; }
  public RolePermissionList getPermissionList() { return permissionList; }
  public UserList getUserList() { return userList; }
  
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("role_id");
    role = rs.getString("role");
    description = rs.getString("description");
    
    java.sql.Timestamp tmpDateCreated = rs.getTimestamp("entered");
    if (tmpDateCreated != null) {
      entered = shortDateTimeFormat.format(tmpDateCreated);
    } else {
      entered = "";
    }

    enteredBy = rs.getInt("enteredby");
    java.sql.Timestamp tmpLastModified = rs.getTimestamp("modified");
    modified = tmpLastModified.toString();
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
  }
  
  protected void buildResources(Connection db) throws SQLException {
    permissionList = new RolePermissionList(db, id);
    buildUserList(db);
  }
  
  protected void buildUserList(Connection db) throws SQLException {
    userList.setRoleId(id);
    userList.setBuildHierarchy(buildHierarchy);
    userList.buildList(db);
  }
  
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
  
  private boolean isDuplicate(Connection db) throws SQLException {
    boolean duplicate = false;
    
    StringBuffer sql = new StringBuffer();
    sql.append(      
        "SELECT * " +
        "FROM role " +
        "WHERE lower(role) = lower(?)  " +
        "AND enabled = true ");
        
    if (id > -1) {
      sql.append("AND role_id <> " + id + " ");
    }
    
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setString(1, getRole());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      duplicate = true;
    }
    rs.close();
    pst.close();
    return duplicate;
  }
  
  private boolean hasUsers(Connection db, boolean activeUsers) throws SQLException {
    int resultCount = -1;
    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT count(*) AS count " +
        "FROM access " +
        "WHERE role_id = " + id + " " +
        (activeUsers?"AND enabled = true ":""));
    if (rs.next()) {
      resultCount = rs.getInt("count");
    }
    rs.close();
    st.close();    
    if (resultCount > 0) {
      return true;
    } else {
      return false;
    }
  }
  
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
      
      //Process the base role record
      sql.append(
        "UPDATE role " +
        "SET role = ?, description = ?, modified = CURRENT_TIMESTAMP, " +
        "modifiedby = ? " +
        "WHERE modified = ? AND role_id = " + id);
      
      pst = db.prepareStatement(sql.toString());
      int i = 0;
      pst.setString(++i, this.getRole());
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getModifiedBy());
      pst.setTimestamp(++i, java.sql.Timestamp.valueOf(this.getModified()));
      
      resultCount = pst.executeUpdate();
      pst.close();
      
      //Process the permissions
      deletePermissions(db);
      insertPermissions(db); 
      
      db.commit();
    } catch (Exception e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    
    db.setAutoCommit(true);
    return resultCount;
  }
  
  public synchronized boolean insert(Connection db) throws SQLException {

    if (!isValid(db)) {
      return false;
    }

    try {
      db.setAutoCommit(false);

      StringBuffer sql = new StringBuffer();
      sql.append("INSERT INTO role ");
      sql.append("(role, description, ");
      sql.append("enteredby, modifiedby ) ");
      sql.append("VALUES (?, ?, ");
      sql.append("?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, getRole());
      pst.setString(++i, getDescription());
      pst.setInt(++i, getEnteredBy());
      pst.setInt(++i, getModifiedBy());
      pst.execute();
      pst.close();

      Statement st = db.createStatement();
      ResultSet rs = st.executeQuery("select currval('role_role_id_seq')");
      if (rs.next()) {
        this.setId(rs.getInt(1));
      }
      rs.close();
      st.close();
      
      insertPermissions(db);
      
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } 
    db.setAutoCommit(true);
    return true;
  }
  
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    
    if (hasUsers(db, true)) {
      errors.put("actionError", "Role cannot be deleted... there are active users assigned to this role.");
      return false;
    }
    
    int recordCount = 0;
    
    try {
      db.setAutoCommit(false);
      Statement st = db.createStatement();
      
      if (hasUsers(db, false)) {
        recordCount = st.executeUpdate(
            "UPDATE role " +
            "SET enabled = false " +
            "WHERE role_id = " + id + " ");
      } else {
        deletePermissions(db);
        recordCount = st.executeUpdate(
            "DELETE FROM role " +
            "WHERE role_id = " + id + " ");
      }
      st.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    db.setAutoCommit(true);
    
    if (recordCount == 0) {
      errors.put("actionError", "Role could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }
  
  private void deletePermissions(Connection db) throws SQLException {
    Statement st = db.createStatement();
    st.execute("DELETE FROM role_permission " +
               "WHERE role_id = " + id);
    st.close();
  }
  
  private void insertPermissions(Connection db) throws SQLException {
    Iterator ipermission = permissionList.keySet().iterator();
    while (ipermission.hasNext()) {
      Permission thisPermission = (Permission)permissionList.get((String)ipermission.next());
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
}

