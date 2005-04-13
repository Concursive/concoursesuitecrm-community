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

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    October 29, 2004
 *@version    $Id: AssignmentAllocationList.java,v 1.3 2004/10/29 05:14:39 matt
 *      Exp $
 */
public class AssignmentAllocationList {

  private int projectsForUser = -1;
  private Timestamp startDate = null;
  private Timestamp endDate = null;
  private ArrayList assignmentList = null;
  private HashMap userList = null;
  private HashMap projectList = null;


  /**
   *  Constructor for the AssignmentAllocationList object
   */
  public AssignmentAllocationList() { }


  /**
   *  Gets the projectsForUser attribute of the AssignmentAllocationList object
   *
   *@return    The projectsForUser value
   */
  public int getProjectsForUser() {
    return projectsForUser;
  }


  /**
   *  Sets the projectsForUser attribute of the AssignmentAllocationList object
   *
   *@param  tmp  The new projectsForUser value
   */
  public void setProjectsForUser(int tmp) {
    this.projectsForUser = tmp;
  }


  /**
   *  Sets the projectsForUser attribute of the AssignmentAllocationList object
   *
   *@param  tmp  The new projectsForUser value
   */
  public void setProjectsForUser(String tmp) {
    this.projectsForUser = Integer.parseInt(tmp);
  }


  /**
   *  Gets the startDate attribute of the AssignmentAllocationList object
   *
   *@return    The startDate value
   */
  public Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Sets the startDate attribute of the AssignmentAllocationList object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the startDate attribute of the AssignmentAllocationList object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the endDate attribute of the AssignmentAllocationList object
   *
   *@return    The endDate value
   */
  public Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Sets the endDate attribute of the AssignmentAllocationList object
   *
   *@param  tmp  The new endDate value
   */
  public void setEndDate(Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   *  Sets the endDate attribute of the AssignmentAllocationList object
   *
   *@param  tmp  The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the userList attribute of the AssignmentAllocationList object
   *
   *@return    The userList value
   */
  public HashMap getUserList() {
    return userList;
  }


  /**
   *  Sets the userList attribute of the AssignmentAllocationList object
   *
   *@param  tmp  The new userList value
   */
  public void setUserList(HashMap tmp) {
    this.userList = tmp;
  }


  /**
   *  Gets the projectList attribute of the AssignmentAllocationList object
   *
   *@return    The projectList value
   */
  public HashMap getProjectList() {
    return projectList;
  }


  /**
   *  Sets the projectList attribute of the AssignmentAllocationList object
   *
   *@param  tmp  The new projectList value
   */
  public void setProjectList(HashMap tmp) {
    this.projectList = tmp;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildAssignmentList(Connection db) throws SQLException {
    assignmentList = new ArrayList();
    if (startDate == null || endDate == null || projectsForUser == -1) {
      throw new SQLException("Invalid parameters");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM project_assignments " +
        "WHERE user_assign_id IS NOT NULL " +
        "AND project_id IN (SELECT project_id FROM project_team WHERE user_id = ?) " +
        "AND ( " +
        " (start_date IS NOT NULL AND start_date >= ? AND start_date < ?) OR " +
        " (est_start_date IS NOT NULL AND est_start_date >= ? AND est_start_date < ?) OR " +
        " (due_date IS NOT NULL AND due_date > ? AND due_date <= ?) OR " +
        " (due_date IS NULL AND complete_date IS NOT NULL AND complete_date > ? AND complete_date <= ?) OR " +
        " (start_date IS NOT NULL AND due_date IS NOT NULL AND start_date <= ? AND due_date >= ?) OR " +
        " (start_date IS NOT NULL AND due_date IS NULL AND complete_date IS NOT NULL AND start_date <= ? AND complete_date >= ?) OR " +
        " (est_start_date IS NOT NULL AND due_date IS NOT NULL AND est_start_date <= ? AND due_date >= ?) OR " +
        " (est_start_date IS NOT NULL AND due_date IS NULL AND complete_date IS NOT NULL AND est_start_date <= ? AND complete_date >= ?) " +
        ") ");
    pst.setInt(++i, projectsForUser);
    // start
    pst.setTimestamp(++i, startDate);
    pst.setTimestamp(++i, endDate);
    // est start
    pst.setTimestamp(++i, startDate);
    pst.setTimestamp(++i, endDate);
    // due date
    pst.setTimestamp(++i, startDate);
    pst.setTimestamp(++i, endDate);
    // complete date
    pst.setTimestamp(++i, startDate);
    pst.setTimestamp(++i, endDate);
    // start date/due date
    pst.setTimestamp(++i, startDate);
    pst.setTimestamp(++i, endDate);
    // start date/complete date
    pst.setTimestamp(++i, startDate);
    pst.setTimestamp(++i, endDate);
    // est/due date
    pst.setTimestamp(++i, startDate);
    pst.setTimestamp(++i, endDate);
    // est/complete date
    pst.setTimestamp(++i, startDate);
    pst.setTimestamp(++i, endDate);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      Assignment assignment = getAssignment(rs);
      assignmentList.add(assignment);
    }
    rs.close();
    pst.close();
  }

  public void buildUserList(Connection db) throws SQLException {
    if (assignmentList == null) {
      buildAssignmentList(db);
    }
    userList = new HashMap();
    Iterator i = assignmentList.iterator();
    while (i.hasNext()) {
      Assignment assignment = (Assignment) i.next();
      addUserEntry(assignment, db);
    }
  }

  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildProjectList(Connection db) throws SQLException {
    if (assignmentList == null) {
      buildAssignmentList(db);
    }
    projectList = new HashMap();
    Iterator i = assignmentList.iterator();
    while (i.hasNext()) {
      Assignment assignment = (Assignment) i.next();
      addProjectEntry(assignment, db);
    }
  }


  /**
   *  Adds a feature to the Entry attribute of the AssignmentAllocationList
   *  object
   *
   *@param  rs                The feature to be added to the Entry attribute
   *@return                   The assignment value
   *@exception  SQLException  Description of the Exception
   */
  private Assignment getAssignment(ResultSet rs) throws SQLException {
    // retrieve values
    Assignment assignment = new Assignment();
    assignment.setId(rs.getInt("assignment_id"));
    assignment.setProjectId(rs.getInt("project_id"));
    assignment.setRequirementId(rs.getInt("requirement_id"));
    assignment.setUserAssignedId(rs.getInt("user_assign_id"));
    assignment.setEstimatedLoe(rs.getInt("estimated_loevalue"));
    assignment.setEstimatedLoeTypeId(rs.getInt("estimated_loetype"));
    assignment.setActualLoe(rs.getInt("actual_loevalue"));
    assignment.setActualLoeTypeId(rs.getInt("actual_loetype"));
    assignment.setEstStartDate(rs.getTimestamp("est_start_date"));
    assignment.setStartDate(rs.getTimestamp("start_date"));
    assignment.setDueDate(rs.getTimestamp("due_date"));
    assignment.setStatusId(rs.getInt("status_id"));
    assignment.setCompleteDate(rs.getTimestamp("complete_date"));
    return assignment;
  }


  /**
   *  Adds a feature to the UserEntry attribute of the AssignmentAllocationList
   *  object
   *
   *@param  assignment  The feature to be added to the UserEntry attribute
   */
  private void addUserEntry(Assignment assignment, Connection db) throws SQLException {
    // for each user, store the user's project allocation
    AssignmentUserAllocation userMap = getUserMap(assignment.getUserAssignedId(), db);
    userMap.addEntry(assignment);
    // for each of the user's projects, store the associated user
    AssignmentProjectAllocation projectMap = userMap.getProjectMap(assignment.getProjectId());
    projectMap.addEntry(assignment, userMap.getTimesheet());
  }


  /**
   *  Gets the userMap attribute of the AssignmentAllocationList object
   *
   *@param  id  Description of the Parameter
   *@return     The userMap value
   */
  private AssignmentUserAllocation getUserMap(int id, Connection db) throws SQLException {
    Integer userId = new Integer(id);
    AssignmentUserAllocation userMap = (AssignmentUserAllocation) userList.get(userId);
    if (userMap == null) {
      userMap = new AssignmentUserAllocation(id);
      // Generate the user's timesheet for display
      DailyTimesheetList timesheet = userMap.getTimesheet();
      timesheet.setUserId(id);
      timesheet.setStartDate(startDate);
      timesheet.setEndDate(endDate);
      timesheet.buildList(db);
      userList.put(userId, userMap);
    }
    return userMap;
  }


  /**
   *  Gets the user attribute of the AssignmentAllocationList object
   *
   *@param  userId  Description of the Parameter
   *@return         The user value
   */
  public AssignmentUserAllocation getUser(Integer userId) {
    return (AssignmentUserAllocation) userList.get(userId);
  }



  /**
   *  Adds a feature to the ProjectEntry attribute of the
   *  AssignmentAllocationList object
   *
   *@param  assignment  The feature to be added to the ProjectEntry attribute
   */
  private void addProjectEntry(Assignment assignment, Connection db) throws SQLException {
    // for each project, store the project's user allocation
    AssignmentProjectAllocation projectMap = getProjectMap(assignment.getProjectId());
    // for each of the project's users, store the associated project
    AssignmentUserAllocation userMap = projectMap.getUserMap(assignment.getUserAssignedId(), db, startDate, endDate);
    userMap.addEntry(assignment);
    projectMap.addEntry(assignment, userMap.getTimesheet());
  }


  /**
   *  Gets the projectMap attribute of the AssignmentAllocationList object
   *
   *@param  id  Description of the Parameter
   *@return     The projectMap value
   */
  private AssignmentProjectAllocation getProjectMap(int id) {
    Integer projectId = new Integer(id);
    AssignmentProjectAllocation projectMap = (AssignmentProjectAllocation) projectList.get(projectId);
    if (projectMap == null) {
      projectMap = new AssignmentProjectAllocation(id);
      projectList.put(projectId, projectMap);
    }
    return projectMap;
  }


  /**
   *  Gets the project attribute of the AssignmentAllocationList object
   *
   *@param  projectId  Description of the Parameter
   *@return            The project value
   */
  public AssignmentProjectAllocation getProject(Integer projectId) {
    return (AssignmentProjectAllocation) projectList.get(projectId);
  }
}

