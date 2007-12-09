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
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserGroup;
import org.aspcfs.modules.base.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    November 10, 2005
 * @version    $Id: TicketCategoryAssignment.java,v 1.1.2.2 2005/11/14 22:09:22
 *      partha Exp $
 */
public class TicketCategoryAssignment extends GenericBean {
  private int id = -1;
  private int categoryId = -1;
  private int departmentId = -1;
  private int assignedTo = -1;
  private int userGroupId = -1;
  private boolean buildPlanMapList = false;
  private TicketCategoryPlanMapList planMapList = null;
  private UserList users = null;
  private boolean buildDeptUsers = false;
  private boolean buildGroupUsers = false;
  private String userGroupName = null;
  private int siteId = -1;


  /**
   *  Constructor for the TicketCategoryAssignment object
   */
  public TicketCategoryAssignment() { }


  /**
   *  Constructor for the TicketCategoryAssignment object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryAssignment(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the TicketCategoryAssignment object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryAssignment(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the TicketCategoryAssignment object
   *
   * @param  db                Description of the Parameter
   * @param  categoryId        Description of the Parameter
   * @param  fieldName         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryAssignment(Connection db, int categoryId, String fieldName) throws SQLException {
    String sql = new String(
        "SELECT tcda.*, ug.group_name " +
        "FROM ticket_category_assignment tcda " +
        "LEFT JOIN user_group ug ON (tcda.group_id = ug.group_id) " +
        "WHERE tcda.category_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, categoryId);
    this.setCategoryId(categoryId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id < 0) {
      throw new SQLException("Ticket Category Assignment not specified");
    }
    String sql = new String(
        "SELECT tcda.*, ug.group_name " +
        "FROM ticket_category_assignment tcda " +
        "LEFT JOIN user_group ug ON (tcda.group_id = ug.group_id) " +
        "WHERE tcda.map_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Ticket Category Assignment record not found.");
    }
    if (buildPlanMapList) {
      buildPlanMapList(db);
    }
    if (buildDeptUsers) {
      buildDepartmentUsers(db);
    } else if (buildGroupUsers) {
      buildGroupUsers(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildDepartmentUsers(Connection db) throws SQLException {
    users = new UserList();
    users.setHidden(Constants.FALSE);
    users.setRoleType(Constants.ROLETYPE_REGULAR);
    users.setDepartment(this.getDepartmentId());
    users.setIncludeUsersWithAccessToAllSites(true);
    users.setSiteId(siteId);
    if (this.getDepartmentId() > -1) {
      users.buildList(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildGroupUsers(Connection db) throws SQLException {
    users = new UserList();
    users.setHidden(Constants.FALSE);
    users.setExcludeDisabledIfUnselected(true);
    users.setExcludeExpiredIfUnselected(true);
    users.setRoleType(Constants.ROLETYPE_REGULAR);
    users.setUserGroupId(this.getUserGroupId());
    users.setIncludeUsersWithAccessToAllSites(true);
    users.setSiteId(siteId);
    if (this.getUserGroupId() > -1) {
      users.buildList(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildPlanMapList(Connection db) throws SQLException {
    planMapList = new TicketCategoryPlanMapList();
    planMapList.setCategoryId(this.getCategoryId());
    planMapList.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("map_id");
    categoryId = rs.getInt("category_id");
    departmentId = DatabaseUtils.getInt(rs, "department_id", 0);
    assignedTo = DatabaseUtils.getInt(rs, "assigned_to");
    userGroupId = DatabaseUtils.getInt(rs, "group_id");
    //user_group table
    userGroupName = rs.getString("group_name");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      int i = 0;
      id = DatabaseUtils.getNextSeq(db, "ticket_category_assignment_map_id_seq");
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO ticket_category_assignment " +
          "(" + (id > -1 ? "map_id, " : "") + "category_id, department_id, assigned_to, group_id) " +
          "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?) ");
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, this.getCategoryId());
      DatabaseUtils.setInt(pst, ++i, (departmentId == 0 ? -1 : departmentId));
      DatabaseUtils.setInt(pst, ++i, assignedTo);
      DatabaseUtils.setInt(pst, ++i, userGroupId);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "ticket_category_assignment_map_id_seq", id);
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
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("Ticket Category Assignment Id not specified");
    }
    int i = 0;
    int count = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE ticket_category_assignment " +
          "SET category_id = ?, department_id = ?, assigned_to = ?, group_id = ? " +
          "WHERE  map_id = ? ");
      pst.setInt(++i, this.getCategoryId());
      DatabaseUtils.setInt(pst, ++i, (departmentId == 0 ? -1 : departmentId));
      DatabaseUtils.setInt(pst, ++i, assignedTo);
      DatabaseUtils.setInt(pst, ++i, userGroupId);
      pst.setInt(++i, this.getId());
      count = pst.executeUpdate();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      e.printStackTrace();
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return count;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Ticket Category Assignment Id not specified");
    }
    PreparedStatement pst = null;
    int recordCount = 0;
    pst = db.prepareStatement(
        "DELETE FROM ticket_category_assignment " +
        "WHERE map_id = ? ");
    pst.setInt(1, id);
    pst.execute();
    pst.close();
    if (recordCount == 0) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   * @param  draftAssignment  Description of the Parameter
   */
  public void copyAssignment(TicketCategoryDraftAssignment draftAssignment) {
    departmentId = draftAssignment.getDepartmentId();
    assignedTo = draftAssignment.getAssignedTo();
    userGroupId = draftAssignment.getUserGroupId();
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public TicketCategoryAssignment cloneAssignment(Connection db, int siteId) throws SQLException {
    TicketCategoryAssignment assignment = new TicketCategoryAssignment();
    if (this.getAssignedTo() != -1) {
      User user = new User(db, this.getAssignedTo());
      if (user.getSiteId() == -1 || user.getSiteId() == siteId) {
        assignment.setAssignedTo(this.getAssignedTo());
      }
    }
    if (this.getUserGroupId() != -1) {
      UserGroup group = new UserGroup(db, this.getUserGroupId());
      if (group.getSiteId() == -1 || group.getSiteId() == siteId) {
        assignment.setUserGroupId(this.getUserGroupId());
        assignment.setUserGroupName(this.getUserGroupName());
      }
    }
    assignment.setDepartmentId(this.getDepartmentId());
    if (assignment.getAssignedTo() == -1 && assignment.getUserGroupId() == -1 && assignment.getDepartmentId() == -1) {
      return (TicketCategoryAssignment) null;
    }
    return assignment;
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the id attribute of the TicketCategoryAssignment object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the categoryId attribute of the TicketCategoryAssignment object
   *
   * @return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Sets the categoryId attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the departmentId attribute of the TicketCategoryAssignment object
   *
   * @return    The departmentId value
   */
  public int getDepartmentId() {
    return departmentId;
  }


  /**
   *  Sets the departmentId attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new departmentId value
   */
  public void setDepartmentId(int tmp) {
    this.departmentId = tmp;
  }


  /**
   *  Sets the departmentId attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new departmentId value
   */
  public void setDepartmentId(String tmp) {
    this.departmentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the assignedTo attribute of the TicketCategoryAssignment object
   *
   * @return    The assignedTo value
   */
  public int getAssignedTo() {
    return assignedTo;
  }


  /**
   *  Sets the assignedTo attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new assignedTo value
   */
  public void setAssignedTo(int tmp) {
    this.assignedTo = tmp;
  }


  /**
   *  Sets the assignedTo attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new assignedTo value
   */
  public void setAssignedTo(String tmp) {
    this.assignedTo = Integer.parseInt(tmp);
  }


  /**
   *  Gets the userGroupId attribute of the TicketCategoryAssignment object
   *
   * @return    The userGroupId value
   */
  public int getUserGroupId() {
    return userGroupId;
  }


  /**
   *  Sets the userGroupId attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(int tmp) {
    this.userGroupId = tmp;
  }


  /**
   *  Sets the userGroupId attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(String tmp) {
    this.userGroupId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildPlanMapList attribute of the TicketCategoryAssignment object
   *
   * @return    The buildPlanMapList value
   */
  public boolean getBuildPlanMapList() {
    return buildPlanMapList;
  }


  /**
   *  Sets the buildPlanMapList attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new buildPlanMapList value
   */
  public void setBuildPlanMapList(boolean tmp) {
    this.buildPlanMapList = tmp;
  }


  /**
   *  Sets the buildPlanMapList attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new buildPlanMapList value
   */
  public void setBuildPlanMapList(String tmp) {
    this.buildPlanMapList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the planMapList attribute of the TicketCategoryAssignment object
   *
   * @return    The planMapList value
   */
  public TicketCategoryPlanMapList getPlanMapList() {
    return planMapList;
  }


  /**
   *  Sets the planMapList attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new planMapList value
   */
  public void setPlanMapList(TicketCategoryPlanMapList tmp) {
    this.planMapList = tmp;
  }


  /**
   *  Gets the users attribute of the TicketCategoryAssignment object
   *
   * @return    The users value
   */
  public UserList getUsers() {
    return users;
  }


  /**
   *  Sets the users attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new users value
   */
  public void setUsers(UserList tmp) {
    this.users = tmp;
  }


  /**
   *  Gets the buildDeptUsers attribute of the TicketCategoryAssignment object
   *
   * @return    The buildDeptUsers value
   */
  public boolean getBuildDeptUsers() {
    return buildDeptUsers;
  }


  /**
   *  Sets the buildDeptUsers attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new buildDeptUsers value
   */
  public void setBuildDeptUsers(boolean tmp) {
    this.buildDeptUsers = tmp;
  }


  /**
   *  Sets the buildDeptUsers attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new buildDeptUsers value
   */
  public void setBuildDeptUsers(String tmp) {
    this.buildDeptUsers = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildGroupUsers attribute of the TicketCategoryAssignment object
   *
   * @return    The buildGroupUsers value
   */
  public boolean getBuildGroupUsers() {
    return buildGroupUsers;
  }


  /**
   *  Sets the buildGroupUsers attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new buildGroupUsers value
   */
  public void setBuildGroupUsers(boolean tmp) {
    this.buildGroupUsers = tmp;
  }


  /**
   *  Sets the buildGroupUsers attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new buildGroupUsers value
   */
  public void setBuildGroupUsers(String tmp) {
    this.buildGroupUsers = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the userGroupName attribute of the TicketCategoryAssignment object
   *
   * @return    The userGroupName value
   */
  public String getUserGroupName() {
    return userGroupName;
  }


  /**
   *  Sets the userGroupName attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new userGroupName value
   */
  public void setUserGroupName(String tmp) {
    this.userGroupName = tmp;
  }


  /**
   *  Gets the siteId attribute of the TicketCategoryAssignment object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the siteId attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the TicketCategoryAssignment object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }
}

