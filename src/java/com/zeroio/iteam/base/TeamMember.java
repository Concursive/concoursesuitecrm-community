/*
 *  Copyright 2000-2003 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.sql.*;
import java.util.Calendar;
import java.text.DateFormat;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Represents a member of a project
 *
 *@author     matt rajkowski
 *@created    July 23, 2001
 *@version    $Id$
 */
public class TeamMember {

  private Project project = null;
  private Object contact = null;
  private Object user = null;

  private int projectId = -1;
  private int userId = -1;
  private int userLevel = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;



  /**
   *  Constructor for the Assignment object
   *
   *@since
   */
  public TeamMember() { }


  /**
   *  Constructor for the TeamMember object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public TeamMember(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the TeamMember object
   *
   *@param  db                Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@param  teamId            Description of the Parameter
   *@param  userRange         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public TeamMember(Connection db, int projectId, int teamId, String userRange) throws SQLException {
    String sql =
        "SELECT t.* " +
        "FROM project_team t " +
        "WHERE t.project_id = ? " +
        "  AND t.user_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
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
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    projectId = rs.getInt("project_id");
    userId = rs.getInt("user_id");
    userLevel = rs.getInt("userLevel");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }


  /**
   *  Sets the project attribute of the TeamMember object
   *
   *@param  tmp  The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   *  Sets the contact attribute of the TeamMember object
   *
   *@param  tmp  The new contact value
   */
  public void setContact(Object tmp) {
    this.contact = tmp;
  }


  /**
   *  Sets the user attribute of the TeamMember object
   *
   *@param  tmp  The new user value
   */
  public void setUser(Object tmp) {
    this.user = tmp;
  }


  /**
   *  Sets the projectId attribute of the TeamMember object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the projectId attribute of the TeamMember object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the userId attribute of the TeamMember object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   *  Sets the userId attribute of the TeamMember object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the userLevel attribute of the TeamMember object
   *
   *@param  tmp  The new userLevel value
   */
  public void setUserLevel(int tmp) {
    this.userLevel = tmp;
  }


  /**
   *  Sets the userLevel attribute of the TeamMember object
   *
   *@param  tmp  The new userLevel value
   */
  public void setUserLevel(String tmp) {
    this.userLevel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the TeamMember object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the TeamMember object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the TeamMember object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the TeamMember object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the TeamMember object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the TeamMember object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the TeamMember object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the TeamMember object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the project attribute of the TeamMember object
   *
   *@return    The project value
   */
  public Project getProject() {
    return project;
  }


  /**
   *  Gets the contact attribute of the TeamMember object
   *
   *@return    The contact value
   */
  public Object getContact() {
    return contact;
  }


  /**
   *  Gets the user attribute of the TeamMember object
   *
   *@return    The user value
   */
  public Object getUser() {
    return user;
  }


  /**
   *  Gets the projectId attribute of the TeamMember object
   *
   *@return    The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   *  Gets the userId attribute of the TeamMember object
   *
   *@return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Gets the userLevel attribute of the TeamMember object
   *
   *@return    The userLevel value
   */
  public int getUserLevel() {
    return userLevel;
  }


  /**
   *  Gets the entered attribute of the TeamMember object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredString attribute of the TeamMember object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(3).format(entered);
    } catch (NullPointerException e) {
    }
    return ("--");
  }


  /**
   *  Gets the enteredBy attribute of the TeamMember object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the TeamMember object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedString attribute of the TeamMember object
   *
   *@return    The modifiedString value
   */
  public String getModifiedString() {
    try {
      return DateFormat.getDateInstance(3).format(modified);
    } catch (NullPointerException e) {
    }
    return ("--");
  }


  /**
   *  Gets the modifiedBy attribute of the TeamMember object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO project_team ");
    sql.append("(project_id, user_id, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("enteredby, modifiedby) ");
    sql.append("VALUES (?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?) ");

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, projectId);
    pst.setInt(++i, userId);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    String sql =
        "DELETE FROM project_team " +
        "WHERE project_id = ? " +
        "AND user_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, projectId);
    pst.setInt(2, userId);
    pst.execute();
    pst.close();
  }
}


