/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

/**
 * Represents a member of a project
 *
 * @author matt rajkowski
 * @version $Id: TeamMember.java,v 1.1.136.2 2004/04/08 14:55:53 rvasista Exp
 *          $
 * @created July 23, 2001
 */
public class TeamMember {
//Constants that control permissions within a project
  public final static int PROJECT_LEAD = 10;
  public final static int PROJECT_DEVELOPER = 20;
  public final static int OBSERVER = 30;
  public final static int GUEST = 100;
  //Constants that control invitations
  public final static int STATUS_ADDED = -1;
  public final static int STATUS_ACCEPTED = -1;
  public final static int STATUS_INVITING = 1;
  public final static int STATUS_MAILERROR = 2;
  public final static int STATUS_PENDING = 3;
  public final static int STATUS_REFUSED = 4;
  //References
  private Project project = null;
  private Object contact = null;
  private Object user = null;
  //Team Member Properties
  private int projectId = -1;
  private int userId = -1;
  private int userLevel = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private int roleId = -1;
  private int roleType = -1;
  private int status = STATUS_ADDED;
  private java.sql.Timestamp lastAccessed = null;
  //Other factors
  private boolean temporaryAdmin = false;

  /**
   * Constructor for the Assignment object
   */
  public TeamMember() {
  }


  /**
   * Constructor for the TeamMember object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public TeamMember(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the TeamMember object
   *
   * @param db        Description of the Parameter
   * @param projectId Description of the Parameter
   * @param teamId    Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public TeamMember(Connection db, int projectId, int teamId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT t.*, r." + DatabaseUtils.addQuotes(db, "level") + " " +
        "FROM project_team t, lookup_project_role r " +
        "WHERE t.project_id = ? " +
        "AND t.user_id = ? " +
        "AND t.userlevel = r.code ");
    pst.setInt(1, projectId);
    pst.setInt(2, teamId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Member record not found.");
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    //project_team
    projectId = rs.getInt("project_id");
    userId = rs.getInt("user_id");
    userLevel = DatabaseUtils.getInt(rs, "userlevel");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    status = DatabaseUtils.getInt(rs, "status");
    lastAccessed = rs.getTimestamp("last_accessed");
    //lookup_project_role
    roleId = rs.getInt("level");
    roleType = DatabaseUtils.getInt(rs, "role_type");
  }


  /**
   * Sets the project attribute of the TeamMember object
   *
   * @param tmp The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   * Sets the contact attribute of the TeamMember object
   *
   * @param tmp The new contact value
   */
  public void setContact(Object tmp) {
    this.contact = tmp;
  }


  /**
   * Sets the user attribute of the TeamMember object
   *
   * @param tmp The new user value
   */
  public void setUser(Object tmp) {
    this.user = tmp;
  }


  /**
   * Sets the projectId attribute of the TeamMember object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   * Sets the projectId attribute of the TeamMember object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   * Sets the userId attribute of the TeamMember object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the TeamMember object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   * Sets the userLevel attribute of the TeamMember object
   *
   * @param tmp The new userLevel value
   */
  public void setUserLevel(int tmp) {
    this.userLevel = tmp;
  }


  /**
   * Sets the userLevel attribute of the TeamMember object
   *
   * @param tmp The new userLevel value
   */
  public void setUserLevel(String tmp) {
    this.userLevel = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the TeamMember object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the TeamMember object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the TeamMember object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the TeamMember object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the TeamMember object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the TeamMember object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the TeamMember object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the TeamMember object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the roleId attribute of the TeamMember object
   *
   * @param tmp The new roleId value
   */
  public void setRoleId(int tmp) {
    this.roleId = tmp;
  }


  /**
   * Sets the roleId attribute of the TeamMember object
   *
   * @param tmp The new roleId value
   */
  public void setRoleId(String tmp) {
    this.roleId = Integer.parseInt(tmp);
  }


  /**
   * Sets the status attribute of the TeamMember object
   *
   * @param tmp The new status value
   */
  public void setStatus(int tmp) {
    this.status = tmp;
  }


  /**
   * Sets the status attribute of the TeamMember object
   *
   * @param tmp The new status value
   */
  public void setStatus(String tmp) {
    this.status = Integer.parseInt(tmp);
  }


  /**
   * Sets the lastAccessed attribute of the TeamMember object
   *
   * @param tmp The new lastAccessed value
   */
  public void setLastAccessed(java.sql.Timestamp tmp) {
    this.lastAccessed = tmp;
  }


  /**
   * Gets the project attribute of the TeamMember object
   *
   * @return The project value
   */
  public Project getProject() {
    return project;
  }


  /**
   * Gets the contact attribute of the TeamMember object
   *
   * @return The contact value
   */
  public Object getContact() {
    return contact;
  }


  /**
   * Gets the user attribute of the TeamMember object
   *
   * @return The user value
   */
  public Object getUser() {
    return user;
  }


  /**
   * Gets the projectId attribute of the TeamMember object
   *
   * @return The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   * Gets the userId attribute of the TeamMember object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Gets the userLevel attribute of the TeamMember object
   *
   * @return The userLevel value
   */
  public int getUserLevel() {
    return userLevel;
  }


  /**
   * Gets the entered attribute of the TeamMember object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredString attribute of the TeamMember object
   *
   * @return The enteredString value
   */
  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(3).format(entered);
    } catch (NullPointerException e) {
    }
    return ("--");
  }


  /**
   * Gets the enteredBy attribute of the TeamMember object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the TeamMember object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedString attribute of the TeamMember object
   *
   * @return The modifiedString value
   */
  public String getModifiedString() {
    try {
      return DateFormat.getDateInstance(3).format(modified);
    } catch (NullPointerException e) {
    }
    return ("--");
  }


  /**
   * Gets the modifiedBy attribute of the TeamMember object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the roleId attribute of the TeamMember object
   *
   * @return The roleId value
   */
  public int getRoleId() {
    return roleId;
  }


  /**
   * Gets the status attribute of the TeamMember object
   *
   * @return The status value
   */
  public int getStatus() {
    return status;
  }


  /**
   * Gets the lastAccessed attribute of the TeamMember object
   *
   * @return The lastAccessed value
   */
  public java.sql.Timestamp getLastAccessed() {
    return lastAccessed;
  }


  /**
   * Gets the lastAccessedString attribute of the TeamMember object
   *
   * @return The lastAccessedString value
   */
  public String getLastAccessedString() {
    try {
      return DateFormat.getDateInstance(3).format(lastAccessed);
    } catch (NullPointerException e) {
    }
    return ("--");
  }

  public boolean isTemporaryAdmin() {
    return temporaryAdmin;
  }

  public void setTemporaryAdmin(boolean temporaryAdmin) {
    this.temporaryAdmin = temporaryAdmin;
  }

  /**
	 * @return Returns the roleType.
	 */
	public int getRoleType() {
		return roleType;
	}


	/**
	 * @param roleType The roleType to set.
	 */
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}


	/**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO project_team ");
    sql.append("(project_id, user_id, userlevel, role_type, ");
    sql.append("entered, modified, ");
    if (lastAccessed != null) {
      sql.append("last_accessed, ");
    }
    sql.append("enteredby, modifiedby, status) ");
    sql.append("VALUES (?, ?, ?, ?, ");
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
    if (lastAccessed != null) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?) ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, projectId);
    pst.setInt(++i, userId);
    DatabaseUtils.setInt(pst, ++i, userLevel);
    DatabaseUtils.setInt(pst, ++i, roleType);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    if (lastAccessed != null) {
      pst.setTimestamp(++i, lastAccessed);
    }
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    DatabaseUtils.setInt(pst, ++i, status);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM project_team " +
        "WHERE project_id = ? " +
        "AND user_id = ? ");
    pst.setInt(1, projectId);
    pst.setInt(2, userId);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param projectId Description of the Parameter
   * @param userId    Description of the Parameter
   * @param userLevel Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean changeRole(Connection db, int projectId, int userId, int userLevel) throws SQLException {
    //Check current level, if user is not a leader than it doesn't matter what the change is
    PreparedStatement pst = db.prepareStatement(
        "SELECT " + DatabaseUtils.addQuotes(db, "level") + " " +
        "FROM lookup_project_role " +
        "WHERE code IN (SELECT userlevel FROM project_team WHERE project_id = ? AND user_id = ?) ");
    pst.setInt(1, projectId);
    pst.setInt(2, userId);
    ResultSet rs = pst.executeQuery();
    int previousLevel = -1;
    while (rs.next()) {
      previousLevel = rs.getInt("level");
      if (previousLevel <= TeamMember.PROJECT_LEAD) {
        break;
      }
    }
    rs.close();
    pst.close();
    if (previousLevel <= TeamMember.PROJECT_LEAD) {
      //Make sure that there is at least 1 other user with lead status before changing
      pst = db.prepareStatement(
          "SELECT count(user_id) AS other " +
          "FROM project_team " +
          "WHERE project_id = ? " +
          "AND userlevel IN (SELECT code FROM lookup_project_role WHERE " + DatabaseUtils.addQuotes(db, "level") + " <= ?) " +
          "AND user_id <> ? ");
      pst.setInt(1, projectId);
      pst.setInt(2, TeamMember.PROJECT_LEAD);
      pst.setInt(3, userId);
      rs = pst.executeQuery();
      int otherCount = -1;
      if (rs.next()) {
        otherCount = rs.getInt("other");
      }
      rs.close();
      pst.close();
      if (otherCount == 0) {
        return false;
      }
    }
    //Now update the team
    pst = db.prepareStatement(
        "UPDATE project_team " +
        "SET userlevel = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE project_id = ? " +
        "AND user_id = ? ");
    pst.setInt(1, userLevel);
    pst.setInt(2, projectId);
    pst.setInt(3, userId);
    int count = pst.executeUpdate();
    pst.close();
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void updateStatus(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_team " +
        "SET status = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE project_id = ? " +
        "AND user_id = ? ");
    DatabaseUtils.setInt(pst, 1, status);
    pst.setInt(2, projectId);
    pst.setInt(3, userId);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param projectId Description of the Parameter
   * @param userId    Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void updateLastAccessed(Connection db, int projectId, int userId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_team " +
        "SET last_accessed = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE project_id = ? " +
        "AND user_id = ? ");
    pst.setInt(1, projectId);
    pst.setInt(2, userId);
    pst.execute();
    pst.close();
  }
}


