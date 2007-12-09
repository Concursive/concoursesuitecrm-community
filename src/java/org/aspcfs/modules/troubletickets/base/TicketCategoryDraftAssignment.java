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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    November 10, 2005
 * @version    $Id$
 */
public class TicketCategoryDraftAssignment extends GenericBean {
  public final static String DEPARTMENT = "department_id";
  public final static String USER = "assigned_to";
  public final static String GROUP = "group_id";
  private int id = -1;
  private int categoryId = -1;
  private int departmentId = -1;
  private int assignedTo = -1;
  private int userGroupId = -1;
  //related resources
  private boolean buildPlanMapList = false;
  private TicketCategoryDraftPlanMapList planMapList = null;
  private String userGroupName = null;


  /**
   *  Constructor for the TicketCategoryDraftAssignment object
   */
  public TicketCategoryDraftAssignment() { }


  /**
   *  Constructor for the TicketCategoryDraftAssignment object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryDraftAssignment(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the TicketCategoryDraftAssignment object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryDraftAssignment(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the TicketCategoryDraftAssignment object
   *
   * @param  db                Description of the Parameter
   * @param  categoryId        Description of the Parameter
   * @param  fieldName         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryDraftAssignment(Connection db, int categoryId, String fieldName) throws SQLException {
    String sql = new String(
        "SELECT tcda.*, ug.group_name " +
        "FROM " + DatabaseUtils.getTableName(db, "ticket_category_draft_assignment") + " tcda " +
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
      throw new SQLException("Ticket Category Draft Assignment not specified");
    }
    String sql = new String(
        "SELECT tcda.*, ug.group_name " +
        "FROM " + DatabaseUtils.getTableName(db, "ticket_category_draft_assignment") + " tcda " +
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
      throw new SQLException("Ticket Category Draft Assignment record not found.");
    }
    if (buildPlanMapList) {
      buildPlanMapList(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildPlanMapList(Connection db) throws SQLException {
    planMapList = new TicketCategoryDraftPlanMapList();
    planMapList.setBuildPlan(true);
    planMapList.setCategoryId(this.getCategoryId());
    if (this.getCategoryId() > -1) {
      planMapList.buildList(db);
    }
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
      id = DatabaseUtils.getNextSeq(db, "ticket_category_draft_assignment_map_id_seq");
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO " + DatabaseUtils.getTableName(db, "ticket_category_draft_assignment") + " " +
          "(" + (id > -1 ? "map_id, " : "") + "category_id, department_id, assigned_to, group_id) " +
          "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?) ");
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, this.getCategoryId());
      DatabaseUtils.setInt(pst, ++i, (departmentId == 0?-1:departmentId));
      DatabaseUtils.setInt(pst, ++i, assignedTo);
      DatabaseUtils.setInt(pst, ++i, userGroupId);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "ticket_category_draft_assignment_map_id_seq", id);
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
      throw new SQLException("Ticket Category Draft Assignment Id not specified");
    }
    int i = 0;
    int count = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE " + DatabaseUtils.getTableName(db, "ticket_category_draft_assignment") + " " +
          "SET category_id = ?, department_id = ?, assigned_to = ?, group_id = ? " +
          "WHERE  map_id = ? ");
      pst.setInt(++i, this.getCategoryId());
      DatabaseUtils.setInt(pst, ++i, (departmentId == 0?-1:departmentId));
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
      throw new SQLException("Ticket Category Draft Assignment Id not specified");
    }
    PreparedStatement pst = null;
    int recordCount = 0;
    pst = db.prepareStatement(
        "DELETE FROM " + DatabaseUtils.getTableName(db, "ticket_category_draft_assignment") + " " +
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
   *  Sets the fieldId attribute of the TicketCategoryDraftAssignment object
   *
   * @param  fieldId    The new fieldId value
   * @param  fieldName  The new fieldId value
   */
  public void setFieldId(int fieldId, String fieldName) {
    if (fieldName != null && fieldName.equals(TicketCategoryDraftAssignment.DEPARTMENT)) {
      departmentId = fieldId;
    } else if (fieldName != null && fieldName.equals(TicketCategoryDraftAssignment.USER)) {
      assignedTo = fieldId;
    } else if (fieldName != null && fieldName.equals(TicketCategoryDraftAssignment.GROUP)) {
      userGroupId = fieldId;
    }
  }


  /**
   *  Description of the Method
   *
   * @param  fieldId    Description of the Parameter
   * @param  fieldName  Description of the Parameter
   * @return            Description of the Return Value
   */
  public boolean checkField(int fieldId, String fieldName) {
    if (fieldName != null && fieldName.equals(TicketCategoryDraftAssignment.DEPARTMENT)) {
      return (departmentId == fieldId);
    } else if (fieldName != null && fieldName.equals(TicketCategoryDraftAssignment.USER)) {
      return (assignedTo == fieldId);
    } else if (fieldName != null && fieldName.equals(TicketCategoryDraftAssignment.GROUP)) {
      return (userGroupId == fieldId);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  assignment  Description of the Parameter
   */
  public void copyAssignment(TicketCategoryAssignment assignment) {
    departmentId = assignment.getDepartmentId();
    assignedTo = assignment.getAssignedTo();
    userGroupId = assignment.getUserGroupId();
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the id attribute of the TicketCategoryDraftAssignment object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the TicketCategoryDraftAssignment object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketCategoryDraftAssignment object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the categoryId attribute of the TicketCategoryDraftAssignment object
   *
   * @return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Sets the categoryId attribute of the TicketCategoryDraftAssignment object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the TicketCategoryDraftAssignment object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the departmentId attribute of the TicketCategoryDraftAssignment
   *  object
   *
   * @return    The departmentId value
   */
  public int getDepartmentId() {
    return departmentId;
  }


  /**
   *  Sets the departmentId attribute of the TicketCategoryDraftAssignment
   *  object
   *
   * @param  tmp  The new departmentId value
   */
  public void setDepartmentId(int tmp) {
    this.departmentId = tmp;
  }


  /**
   *  Sets the departmentId attribute of the TicketCategoryDraftAssignment
   *  object
   *
   * @param  tmp  The new departmentId value
   */
  public void setDepartmentId(String tmp) {
    this.departmentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the assignedTo attribute of the TicketCategoryDraftAssignment object
   *
   * @return    The assignedTo value
   */
  public int getAssignedTo() {
    return assignedTo;
  }


  /**
   *  Sets the assignedTo attribute of the TicketCategoryDraftAssignment object
   *
   * @param  tmp  The new assignedTo value
   */
  public void setAssignedTo(int tmp) {
    this.assignedTo = tmp;
  }


  /**
   *  Sets the assignedTo attribute of the TicketCategoryDraftAssignment object
   *
   * @param  tmp  The new assignedTo value
   */
  public void setAssignedTo(String tmp) {
    this.assignedTo = Integer.parseInt(tmp);
  }


  /**
   *  Gets the userGroupId attribute of the TicketCategoryDraftAssignment object
   *
   * @return    The userGroupId value
   */
  public int getUserGroupId() {
    return userGroupId;
  }


  /**
   *  Sets the userGroupId attribute of the TicketCategoryDraftAssignment object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(int tmp) {
    this.userGroupId = tmp;
  }


  /**
   *  Sets the userGroupId attribute of the TicketCategoryDraftAssignment object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(String tmp) {
    this.userGroupId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildPlanMapList attribute of the TicketCategoryDraftAssignment
   *  object
   *
   * @return    The buildPlanMapList value
   */
  public boolean getBuildPlanMapList() {
    return buildPlanMapList;
  }


  /**
   *  Sets the buildPlanMapList attribute of the TicketCategoryDraftAssignment
   *  object
   *
   * @param  tmp  The new buildPlanMapList value
   */
  public void setBuildPlanMapList(boolean tmp) {
    this.buildPlanMapList = tmp;
  }


  /**
   *  Sets the buildPlanMapList attribute of the TicketCategoryDraftAssignment
   *  object
   *
   * @param  tmp  The new buildPlanMapList value
   */
  public void setBuildPlanMapList(String tmp) {
    this.buildPlanMapList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the planMapList attribute of the TicketCategoryDraftAssignment object
   *
   * @return    The planMapList value
   */
  public TicketCategoryDraftPlanMapList getPlanMapList() {
    return planMapList;
  }


  /**
   *  Sets the planMapList attribute of the TicketCategoryDraftAssignment object
   *
   * @param  tmp  The new planMapList value
   */
  public void setPlanMapList(TicketCategoryDraftPlanMapList tmp) {
    this.planMapList = tmp;
  }


  /**
   *  Gets the userGroupName attribute of the TicketCategoryDraftAssignment
   *  object
   *
   * @return    The userGroupName value
   */
  public String getUserGroupName() {
    return userGroupName;
  }


  /**
   *  Sets the userGroupName attribute of the TicketCategoryDraftAssignment
   *  object
   *
   * @param  tmp  The new userGroupName value
   */
  public void setUserGroupName(String tmp) {
    this.userGroupName = tmp;
  }
  
  public String toString() {
    return (""+this.getId());
  }
}

