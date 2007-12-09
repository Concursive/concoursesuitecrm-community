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

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Defines a Viewpoint
 *
 * @author Mathur
 * @version $Id$
 * @created February 20, 2003
 */
public class Viewpoint extends GenericBean {
  private int id = -1;
  private int userId = -1;
  private int vpUserId = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private User vpUser = null;
  private ViewpointPermissionList permissionList = new ViewpointPermissionList();


  /**
   * Constructor for the Viewpoint object
   */
  public Viewpoint() {
  }


  /**
   * Constructor for the Viewpoint object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Viewpoint(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the Viewpoint object
   *
   * @param db          Description of the Parameter
   * @param viewpointId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Viewpoint(Connection db, int viewpointId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM viewpoint " +
        "WHERE viewpoint_id = ? ");
    pst.setInt(1, viewpointId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Viewpoint record not found.");
    }
    buildResources(db);
  }


  /**
   * Sets the id attribute of the Viewpoint object
   *
   * @param id The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * Sets the id attribute of the Viewpoint object
   *
   * @param id The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   * Sets the userId attribute of the Viewpoint object
   *
   * @param userId The new userId value
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }


  /**
   * Sets the userId attribute of the Viewpoint object
   *
   * @param userId The new userId value
   */
  public void setUserId(String userId) {
    this.userId = Integer.parseInt(userId);
  }


  /**
   * Sets the vpUserId attribute of the Viewpoint object
   *
   * @param vpUserId The new vpUserId value
   */
  public void setVpUserId(int vpUserId) {
    this.vpUserId = vpUserId;
  }


  /**
   * Sets the vpUserId attribute of the Viewpoint object
   *
   * @param vpUserId The new vpUserId value
   */
  public void setVpUserId(String vpUserId) {
    this.vpUserId = Integer.parseInt(vpUserId);
  }


  /**
   * Sets the enteredBy attribute of the Viewpoint object
   *
   * @param enteredBy The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   * Sets the enteredBy attribute of the Viewpoint object
   *
   * @param enteredBy The new enteredBy value
   */
  public void setEnteredBy(String enteredBy) {
    this.enteredBy = Integer.parseInt(enteredBy);
  }


  /**
   * Sets the modifiedBy attribute of the Viewpoint object
   *
   * @param modifiedBy The new modifiedBy value
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   * Sets the modifiedBy attribute of the Viewpoint object
   *
   * @param modifiedBy The new modifiedBy value
   */
  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = Integer.parseInt(modifiedBy);
  }


  /**
   * Sets the modified attribute of the Viewpoint object
   *
   * @param modified The new modified value
   */
  public void setModified(java.sql.Timestamp modified) {
    this.modified = modified;
  }


  /**
   * Sets the modified attribute of the Viewpoint object
   *
   * @param modified The new modified value
   */
  public void setModified(String modified) {
    this.modified = DatabaseUtils.parseTimestamp(modified);
  }


  /**
   * Sets the entered attribute of the Viewpoint object
   *
   * @param entered The new entered value
   */
  public void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
  }


  /**
   * Sets the entered attribute of the Viewpoint object
   *
   * @param entered The new entered value
   */
  public void setEntered(String entered) {
    this.entered = DatabaseUtils.parseTimestamp(entered);
  }


  /**
   * Sets the vpUser attribute of the Viewpoint object
   *
   * @param vpUser The new vpUser value
   */
  public void setVpUser(User vpUser) {
    this.vpUser = vpUser;
  }


  /**
   * Sets the requestItems attribute of the Viewpoint object
   *
   * @param request The new requestItems value
   */
  public void setRequestItems(HttpServletRequest request) {
    permissionList = new ViewpointPermissionList(request);
    if (request.getParameter("enabled") == null) {
      this.enabled = false;
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildVpUserDetails(Connection db) throws SQLException {
    if (vpUserId == -1) {
      throw new SQLException("Id not specified");
    }
    vpUser = new User();
    vpUser.setBuildContact(true);
    vpUser.buildRecord(db, vpUserId);
  }


  /**
   * Sets the permissionList attribute of the Viewpoint object
   *
   * @param permissionList The new permissionList value
   */
  public void setPermissionList(ViewpointPermissionList permissionList) {
    this.permissionList = permissionList;
  }


  /**
   * Sets the enabled attribute of the Viewpoint object
   *
   * @param enabled The new enabled value
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }


  /**
   * Sets the enabled attribute of the Viewpoint object
   *
   * @param enabled The new enabled value
   */
  public void setEnabled(String enabled) {
    this.enabled = DatabaseUtils.parseBoolean(enabled);
  }


  /**
   * Gets the enabled attribute of the Viewpoint object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the permissionList attribute of the Viewpoint object
   *
   * @return The permissionList value
   */
  public ViewpointPermissionList getPermissionList() {
    return permissionList;
  }


  /**
   * Gets the vpUser attribute of the Viewpoint object
   *
   * @return The vpUser value
   */
  public User getVpUser() {
    return vpUser;
  }


  /**
   * Gets the id attribute of the Viewpoint object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the userId attribute of the Viewpoint object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Gets the vpUserId attribute of the Viewpoint object
   *
   * @return The vpUserId value
   */
  public int getVpUserId() {
    return vpUserId;
  }


  /**
   * Gets the enteredBy attribute of the Viewpoint object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modifiedBy attribute of the Viewpoint object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the modified attribute of the Viewpoint object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the entered attribute of the Viewpoint object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the vpUserValid attribute of the Viewpoint object
   *
   * @param db Description of the Parameter
   * @return The vpUserValid value
   * @throws SQLException Description of the Exception
   */
  public boolean isVpUserValid(Connection db) throws SQLException {
    if (vpUserId == -1) {
      return false;
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean commit = false;
    try {
      if ((commit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "viewpoint_viewpoint_id_seq");
      StringBuffer sql = new StringBuffer();
      sql.append("INSERT INTO viewpoint ");
      sql.append("(user_id, vp_user_id, enteredby, ");
      if (id > -1) {
        sql.append("viewpoint_id, ");
      }
      sql.append("entered, modified, ");
      sql.append("modifiedby) ");
      sql.append("VALUES (?, ?, ?, ");
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
      sql.append("?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, userId);
      DatabaseUtils.setInt(pst, ++i, vpUserId);
      pst.setInt(++i, enteredBy);
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setInt(++i, modifiedBy);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "viewpoint_viewpoint_id_seq", id);
      insertPermissions(db);
      if (commit) {
        db.commit();
      }
    } catch (Exception e) {
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
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    int resultCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE viewpoint " +
        "SET vp_user_id = ?, " +
        "modifiedby = ?, modified = "+ DatabaseUtils.getCurrentTimestamp(db) + ", enabled = ? " +
        "WHERE viewpoint_id = ? " +
        "AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    int i = 0;
    pst.setInt(++i, vpUserId);
    pst.setInt(++i, this.getModifiedBy());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getId());
    if(this.getModified() != null){
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();
    deletePermissions(db);
    insertPermissions(db);
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void deletePermissions(Connection db) throws SQLException {
    boolean commit = false;
    try {
      int i = 0;
      if ((commit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM viewpoint_permission " +
          "WHERE viewpoint_id = ? ");
      pst.setInt(++i, id);
      pst.execute();
      pst.close();
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
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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
            thisPermission.getDelete());
      }
    }
  }


  /**
   * Adds a feature to the Permission attribute of the Viewpoint object
   *
   * @param db           The feature to be added to the Permission
   *                     attribute
   * @param permissionId The feature to be added to the Permission
   *                     attribute
   * @param add          The feature to be added to the Permission
   *                     attribute
   * @param view         The feature to be added to the Permission
   *                     attribute
   * @param edit         The feature to be added to the Permission
   *                     attribute
   * @param delete       The feature to be added to the Permission
   *                     attribute
   * @throws SQLException Description of the Exception
   */
  public void addPermission(Connection db, int permissionId, boolean add, boolean view, boolean edit, boolean delete) throws SQLException {
    int viewpointPermissionId = DatabaseUtils.getNextSeq(
        db, "viewpoint_per_vp_permission_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO viewpoint_permission " +
        "(" + (viewpointPermissionId > -1 ? "vp_permission_id, " : "") + "viewpoint_id, permission_id, viewpoint_add, viewpoint_view, viewpoint_edit, viewpoint_delete) " +
        "VALUES (" + (viewpointPermissionId > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ? ) ");
    int i = 0;
    if (viewpointPermissionId > -1) {
      pst.setInt(++i, viewpointPermissionId);
    }
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    int recordCount = 0;
    boolean commit = false;
    try {
      if ((commit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      deletePermissions(db);
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM viewpoint " +
          "WHERE viewpoint_id = ? ");
      pst.setInt(1, id);
      recordCount = pst.executeUpdate();
      pst.close();
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
    if (recordCount == 0) {
      return false;
    } else {
      return true;
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildResources(Connection db) throws SQLException {
    permissionList = new ViewpointPermissionList(db, id);
    buildVpUserDetails(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildPermissions(Connection db) throws SQLException {
    permissionList = new ViewpointPermissionList(db, id);
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("viewpoint_id");
    userId = rs.getInt("user_id");
    vpUserId = rs.getInt("vp_user_id");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
  }
}

