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

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.webdav.utils.ICalendar;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Represents an Assignment in iTeam
 *
 * @author mrajkowski
 * @version $Id: Assignment.java,v 1.1.136.1 2004/03/19 21:00:50 rvasista Exp
 *          $
 * @created July 23, 2001
 */
public class Assignment extends GenericBean {

  public final static int NOTSTARTED = 1;
  public final static int INPROGRESS = 2;
  public final static int COMPLETE = 3;
  public final static int CLOSED = 4;
  public final static int ONHOLD = 5;

  private Project project = null;
  private int id = -1;
  private int projectId = -1;
  private int requirementId = -1;
  private int folderId = -1;
  private int assignedBy = -1;
  private int userAssignedId = -1;
  private String technology = "";
  private String role = "";
  private int estimatedLoe = -1;
  private int estimatedLoeTypeId = -1;
  private String estimatedLoeType = null;
  private int actualLoe = -1;
  private int actualLoeTypeId = -1;
  private String actualLoeType = null;
  private int priorityId = -1;
  private String priority = null;
  private java.sql.Timestamp assignDate = null;
  private java.sql.Timestamp estStartDate = null;
  private java.sql.Timestamp startDate = null;
  private java.sql.Timestamp dueDate = null;
  private int statusId = -1;
  private int statusTypeId = -1;
  private String status = "";
  private String statusGraphic = "";
  private int percentComplete = -1;
  private java.sql.Timestamp statusDate = null;
  private int statusType = -1;
  private java.sql.Timestamp completeDate = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private String dueDateTimeZone = null;
  //Tree variables -- move into new object
  private int displayLevel = 0;
  private boolean levelOpen = false;
  private int indent = -1;
  private int prevIndent = -1;
  private int prevMapId = -1;
  private String additionalNote = null;
  private int noteCount = 0;
  private AssignmentNote assignmentNote = null;

  private boolean recordMapItem = true;
  private boolean recordNote = true;


  /**
   * Gets the recordMapItem attribute of the Assignment object
   *
   * @return The recordMapItem value
   */
  public boolean getRecordMapItem() {
    return recordMapItem;
  }


  /**
   * Sets the recordMapItem attribute of the Assignment object
   *
   * @param tmp The new recordMapItem value
   */
  public void setRecordMapItem(boolean tmp) {
    this.recordMapItem = tmp;
  }


  /**
   * Sets the recordMapItem attribute of the Assignment object
   *
   * @param tmp The new recordMapItem value
   */
  public void setRecordMapItem(String tmp) {
    this.recordMapItem = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the recordNote attribute of the Assignment object
   *
   * @return The recordNote value
   */
  public boolean getRecordNote() {
    return recordNote;
  }


  /**
   * Sets the recordNote attribute of the Assignment object
   *
   * @param tmp The new recordNote value
   */
  public void setRecordNote(boolean tmp) {
    this.recordNote = tmp;
  }


  /**
   * Sets the recordNote attribute of the Assignment object
   *
   * @param tmp The new recordNote value
   */
  public void setRecordNote(String tmp) {
    this.recordNote = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Constructor for the Assignment object
   */
  public Assignment() {
  }


  /**
   * Constructor for the Assignment object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public Assignment(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the Assignment object
   *
   * @param db           Description of the Parameter
   * @param assignmentId Description of the Parameter
   * @param projectId    Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public Assignment(Connection db, int assignmentId, int projectId) throws SQLException {
    this.setProjectId(projectId);
    queryRecord(db, assignmentId);
  }


  /**
   * Constructor for the Assignment object
   *
   * @param db           Description of the Parameter
   * @param assignmentId Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public Assignment(Connection db, int assignmentId) throws SQLException {
    queryRecord(db, assignmentId);
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param assignmentId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void queryRecord(Connection db, int assignmentId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT a.*, s.description AS status, s." + DatabaseUtils.addQuotes(db, "type") + " AS status_type, s.graphic AS status_graphic, " +
            "loe_e.description AS loe_estimated_type, loe_a.description AS loe_actual_type, pr.description AS priority " +
            "FROM projects p, project_assignments a " +
            " LEFT JOIN lookup_project_status s ON (a.status_id = s.code) " +
            " LEFT JOIN lookup_project_loe loe_e ON (a.estimated_loetype = loe_e.code) " +
            " LEFT JOIN lookup_project_loe loe_a ON (a.actual_loetype = loe_a.code) " +
            " LEFT JOIN lookup_project_priority pr ON (a.priority_id = pr.code) " +
            "WHERE assignment_id = ? " +
            "AND p.project_id = a.project_id ");
    if (projectId > -1) {
      sql.append("AND a.project_id = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, assignmentId);
    if (projectId > -1) {
      pst.setInt(++i, projectId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Assignment record not found.");
    }
    statusTypeId = lookupStatusIdType(db, statusId);
    AssignmentNoteList.queryNoteCount(db, this);
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    //project_assignments table
    id = rs.getInt("assignment_id");
    projectId = rs.getInt("project_id");
    requirementId = DatabaseUtils.getInt(rs, "requirement_id");
    assignedBy = DatabaseUtils.getInt(rs, "assignedBy");
    userAssignedId = DatabaseUtils.getInt(rs, "user_assign_id");
    technology = rs.getString("technology");
    role = rs.getString("role");
    estimatedLoe = DatabaseUtils.getInt(rs, "estimated_loevalue");
    estimatedLoeTypeId = DatabaseUtils.getInt(rs, "estimated_loetype");
    actualLoe = DatabaseUtils.getInt(rs, "actual_loevalue");
    actualLoeTypeId = DatabaseUtils.getInt(rs, "actual_loetype");
    priorityId = DatabaseUtils.getInt(rs, "priority_id");
    assignDate = rs.getTimestamp("assign_date");
    estStartDate = rs.getTimestamp("est_start_date");
    startDate = rs.getTimestamp("start_date");
    dueDate = rs.getTimestamp("due_date");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    statusDate = rs.getTimestamp("status_date");
    completeDate = rs.getTimestamp("complete_date");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredBy");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedBy");
    folderId = DatabaseUtils.getInt(rs, "folder_id");
    percentComplete = DatabaseUtils.getInt(rs, "percent_complete");
    this.dueDateTimeZone = rs.getString("due_date_timezone");

    //lookup_project_status table
    status = rs.getString("status");
    statusType = DatabaseUtils.getInt(rs, "status_type");
    statusGraphic = rs.getString("status_graphic");

    //lookup_project_loe
    estimatedLoeType = rs.getString("loe_estimated_type");
    actualLoeType = rs.getString("loe_actual_type");

    //lookup_project_priority
    priority = rs.getString("priority");
  }


  /**
   * Gets the priority attribute of the Assignment object
   *
   * @return The priority value
   */
  public String getPriority() {
    return priority;
  }


  /**
   * Sets the priority attribute of the Assignment object
   *
   * @param tmp The new priority value
   */
  public void setPriority(String tmp) {
    this.priority = tmp;
  }


  /**
   * Sets the project attribute of the Assignment object
   *
   * @param tmp The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   * Sets the id attribute of the Assignment object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Assignment object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the projectId attribute of the Assignment object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   * Sets the projectId attribute of the Assignment object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   * Sets the requirementId attribute of the Assignment object
   *
   * @param tmp The new requirementId value
   */
  public void setRequirementId(int tmp) {
    this.requirementId = tmp;
  }


  /**
   * Sets the requirementId attribute of the Assignment object
   *
   * @param tmp The new requirementId value
   */
  public void setRequirementId(String tmp) {
    this.requirementId = Integer.parseInt(tmp);
  }


  /**
   * Sets the folderId attribute of the Assignment object
   *
   * @param tmp The new folderId value
   */
  public void setFolderId(int tmp) {
    this.folderId = tmp;
  }


  /**
   * Sets the folderId attribute of the Assignment object
   *
   * @param tmp The new folderId value
   */
  public void setFolderId(String tmp) {
    this.folderId = Integer.parseInt(tmp);
  }


  /**
   * Sets the assignedBy attribute of the Assignment object
   *
   * @param tmp The new assignedBy value
   */
  public void setAssignedBy(int tmp) {
    this.assignedBy = tmp;
  }


  /**
   * Sets the assignedBy attribute of the Assignment object
   *
   * @param tmp The new assignedBy value
   */
  public void setAssignedBy(String tmp) {
    this.assignedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the userAssignedId attribute of the Assignment object
   *
   * @param tmp The new userAssignedId value
   */
  public void setUserAssignedId(int tmp) {
    this.userAssignedId = tmp;
  }


  /**
   * Sets the userAssignedId attribute of the Assignment object
   *
   * @param tmp The new userAssignedId value
   */
  public void setUserAssignedId(String tmp) {
    if (tmp != null) {
      try {
        if (tmp.indexOf(".") > -1) {
          this.userAssignedId = Integer.parseInt(
              tmp.substring(0, tmp.indexOf(".")));
        } else {
          this.userAssignedId = Integer.parseInt(tmp);
        }
      } catch (Exception e) {
        System.out.println("Assignment-> Error: " + e.getMessage());
      }
    }
  }


  /**
   * Sets the technology attribute of the Assignment object
   *
   * @param tmp The new technology value
   */
  public void setTechnology(String tmp) {
    this.technology = tmp;
  }


  /**
   * Sets the role attribute of the Assignment object
   *
   * @param tmp The new role value
   */
  public void setRole(String tmp) {
    this.role = tmp;
  }


  /**
   * Sets the estimatedLoe attribute of the Assignment object
   *
   * @param tmp The new estimatedLoe value
   */
  public void setEstimatedLoe(int tmp) {
    this.estimatedLoe = tmp;
  }


  /**
   * Sets the estimatedLoe attribute of the Assignment object
   *
   * @param tmp The new estimatedLoe value
   */
  public void setEstimatedLoe(String tmp) {
    try {
      if (tmp.indexOf(".0") > -1) {
        tmp = tmp.substring(0, tmp.indexOf(".0"));
      }
      if (tmp.toLowerCase().endsWith("d")) {
        setEstimatedLoe(tmp.substring(0, tmp.indexOf("d")));
        setEstimatedLoeTypeId(3);
      } else if (tmp.toLowerCase().endsWith("h")) {
        setEstimatedLoe(tmp.substring(0, tmp.indexOf("h")));
        setEstimatedLoeTypeId(2);
      } else if (tmp.toLowerCase().endsWith("m")) {
        setEstimatedLoe(tmp.substring(0, tmp.indexOf("m")));
        setEstimatedLoeTypeId(1);
      } else if (tmp.toLowerCase().endsWith("w")) {
        setEstimatedLoe(tmp.substring(0, tmp.indexOf("w")));
        setEstimatedLoeTypeId(4);
      } else {
        this.estimatedLoe = Integer.parseInt(tmp.trim());
      }
    } catch (Exception e) {
    }
  }


  /**
   * Sets the estimatedLoeTypeId attribute of the Assignment object
   *
   * @param tmp The new estimatedLoeTypeId value
   */
  public void setEstimatedLoeTypeId(int tmp) {
    this.estimatedLoeTypeId = tmp;
  }


  /**
   * Sets the estimatedLoeTypeId attribute of the Assignment object
   *
   * @param tmp The new estimatedLoeTypeId value
   */
  public void setEstimatedLoeTypeId(String tmp) {
    this.estimatedLoeTypeId = Integer.parseInt(tmp);
  }


  /**
   * Sets the estimatedLoeType attribute of the Assignment object
   *
   * @param tmp The new estimatedLoeType value
   */
  public void setEstimatedLoeType(String tmp) {
    this.estimatedLoeType = tmp;
  }


  /**
   * Sets the actualLoe attribute of the Assignment object
   *
   * @param tmp The new actualLoe value
   */
  public void setActualLoe(int tmp) {
    this.actualLoe = tmp;
  }


  /**
   * Sets the actualLoe attribute of the Assignment object
   *
   * @param tmp The new actualLoe value
   */
  public void setActualLoe(String tmp) {
    this.actualLoe = Integer.parseInt(tmp);
  }


  /**
   * Sets the actualLoeTypeId attribute of the Assignment object
   *
   * @param tmp The new actualLoeTypeId value
   */
  public void setActualLoeTypeId(int tmp) {
    this.actualLoeTypeId = tmp;
  }


  /**
   * Sets the actualLoeTypeId attribute of the Assignment object
   *
   * @param tmp The new actualLoeTypeId value
   */
  public void setActualLoeTypeId(String tmp) {
    this.actualLoeTypeId = Integer.parseInt(tmp);
  }


  /**
   * Sets the actualLoeType attribute of the Assignment object
   *
   * @param tmp The new actualLoeType value
   */
  public void setActualLoeType(String tmp) {
    this.actualLoeType = tmp;
  }


  /**
   * Sets the priorityId attribute of the Assignment object
   *
   * @param tmp The new priorityId value
   */
  public void setPriorityId(int tmp) {
    this.priorityId = tmp;
  }


  /**
   * Sets the priorityId attribute of the Assignment object
   *
   * @param tmp The new priorityId value
   */
  public void setPriorityId(String tmp) {
    try {
      if (tmp.toLowerCase().startsWith("h")) {
        setPriorityId(3);
      } else if (tmp.toLowerCase().startsWith("l")) {
        setPriorityId(1);
      } else {
        this.priorityId = Integer.parseInt(tmp);
      }
    } catch (Exception e) {
      setPriorityId(2);
    }
  }


  /**
   * Sets the assignDate attribute of the Assignment object
   *
   * @param tmp The new assignDate value
   */
  public void setAssignDate(java.sql.Timestamp tmp) {
    this.assignDate = tmp;
  }


  /**
   * Sets the assignDate attribute of the Assignment object
   *
   * @param tmp The new assignDate value
   */
  public void setAssignDate(String tmp) {
    this.assignDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the estStartDate attribute of the Assignment object
   *
   * @param tmp The new estStartDate value
   */
  public void setEstStartDate(java.sql.Timestamp tmp) {
    this.estStartDate = tmp;
  }


  /**
   * Sets the estStartDate attribute of the Assignment object
   *
   * @param tmp The new estStartDate value
   */
  public void setEstStartDate(java.util.Date tmp) {
    if (tmp != null) {
      this.estStartDate = new java.sql.Timestamp(tmp.getTime());
    } else {
      this.estStartDate = null;
    }
  }


  /**
   * Sets the estStartDate attribute of the Assignment object
   *
   * @param tmp The new estStartDate value
   */
  public void setEstStartDate(String tmp) {
    this.estStartDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   * Sets the startDate attribute of the Assignment object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   * Sets the startDate attribute of the Assignment object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the dueDate attribute of the Assignment object
   *
   * @param tmp The new dueDate value
   */
  public void setDueDate(java.sql.Timestamp tmp) {
    this.dueDate = tmp;
  }


  /**
   * Sets the dueDate attribute of the Assignment object
   *
   * @param tmp The new dueDate value
   */
  public void setDueDate(java.util.Date tmp) {
    if (tmp != null) {
      this.dueDate = new java.sql.Timestamp(tmp.getTime());
    } else {
      this.dueDate = null;
    }
  }


  /**
   * Sets the dueDate attribute of the Assignment object
   *
   * @param tmp The new dueDate value
   */
  public void setDueDate(String tmp) {
    this.dueDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   * Sets the statusId attribute of the Assignment object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the Assignment object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the status attribute of the Assignment object
   *
   * @param tmp The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   * Sets the statusDate attribute of the Assignment object
   *
   * @param tmp The new statusDate value
   */
  public void setStatusDate(java.sql.Timestamp tmp) {
    this.statusDate = tmp;
  }


  /**
   * Sets the statusDate attribute of the Assignment object
   *
   * @param tmp The new statusDate value
   */
  public void setStatusDate(String tmp) {
    this.statusDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the statusGraphic attribute of the Assignment object
   *
   * @param tmp The new statusGraphic value
   */
  public void setStatusGraphic(String tmp) {
    this.statusGraphic = tmp;
  }


  /**
   * Sets the percentComplete attribute of the Assignment object
   *
   * @param tmp The new percentComplete value
   */
  public void setPercentComplete(int tmp) {
    this.percentComplete = tmp;
  }


  /**
   * Sets the percentComplete attribute of the Assignment object
   *
   * @param tmp The new percentComplete value
   */
  public void setPercentComplete(String tmp) {
    this.percentComplete = Integer.parseInt(tmp);
  }


  /**
   * Sets the completeDate attribute of the Assignment object
   *
   * @param tmp The new completeDate value
   */
  public void setCompleteDate(java.sql.Timestamp tmp) {
    this.completeDate = tmp;
  }


  /**
   * Sets the completeDate attribute of the Assignment object
   *
   * @param tmp The new completeDate value
   */
  public void setCompleteDate(String tmp) {
    this.completeDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the entered attribute of the Assignment object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the Assignment object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the Assignment object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Assignment object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the Assignment object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the Assignment object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the Assignment object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the Assignment object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.setModifiedBy(Integer.parseInt(tmp));
  }


  /**
   * Sets the displayLevel attribute of the Assignment object
   *
   * @param tmp The new displayLevel value
   */
  public void setDisplayLevel(int tmp) {
    this.displayLevel = tmp;
  }


  /**
   * Sets the levelOpen attribute of the Assignment object
   *
   * @param tmp The new levelOpen value
   */
  public void setLevelOpen(boolean tmp) {
    this.levelOpen = tmp;
  }


  /**
   * Sets the indent attribute of the Assignment object
   *
   * @param tmp The new indent value
   */
  public void setIndent(int tmp) {
    this.indent = tmp;
  }


  /**
   * Sets the indent attribute of the Assignment object
   *
   * @param tmp The new indent value
   */
  public void setIndent(String tmp) {
    this.indent = Integer.parseInt(tmp);
  }


  /**
   * Sets the prevIndent attribute of the Assignment object
   *
   * @param tmp The new prevIndent value
   */
  public void setPrevIndent(int tmp) {
    this.prevIndent = tmp;
  }


  /**
   * Sets the prevIndent attribute of the Assignment object
   *
   * @param tmp The new prevIndent value
   */
  public void setPrevIndent(String tmp) {
    this.prevIndent = Integer.parseInt(tmp);
  }


  /**
   * Sets the prevMapId attribute of the Assignment object
   *
   * @param tmp The new prevMapId value
   */
  public void setPrevMapId(int tmp) {
    this.prevMapId = tmp;
  }


  /**
   * Sets the prevMapId attribute of the Assignment object
   *
   * @param tmp The new prevMapId value
   */
  public void setPrevMapId(String tmp) {
    this.prevMapId = Integer.parseInt(tmp);
  }


  /**
   * Sets the dueDateTimeZone attribute of the Assignment object
   *
   * @param tmp The new dueDateTimeZone value
   */
  public void setDueDateTimeZone(String tmp) {
    this.dueDateTimeZone = tmp;
  }


  /**
   * Gets the dueDateTimeZone attribute of the Assignment object
   *
   * @return The dueDateTimeZone value
   */
  public String getDueDateTimeZone() {
    return dueDateTimeZone;
  }


  /**
   * Gets the additionalNote attribute of the Assignment object
   *
   * @return The additionalNote value
   */
  public String getAdditionalNote() {
    return additionalNote;
  }


  /**
   * Sets the additionalNote attribute of the Assignment object
   *
   * @param additionalNote The new additionalNote value
   */
  public void setAdditionalNote(String additionalNote) {
    this.additionalNote = additionalNote;
  }


  /**
   * Gets the project attribute of the Assignment object
   *
   * @return The project value
   */
  public Project getProject() {
    return project;
  }


  /**
   * Gets the id attribute of the Assignment object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the projectId attribute of the Assignment object
   *
   * @return The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   * Gets the requirementId attribute of the Assignment object
   *
   * @return The requirementId value
   */
  public int getRequirementId() {
    return requirementId;
  }


  /**
   * Gets the folderId attribute of the Assignment object
   *
   * @return The folderId value
   */
  public int getFolderId() {
    return folderId;
  }


  /**
   * Gets the assignedBy attribute of the Assignment object
   *
   * @return The assignedBy value
   */
  public int getAssignedBy() {
    return assignedBy;
  }


  /**
   * Gets the userAssignedId attribute of the Assignment object
   *
   * @return The userAssignedId value
   */
  public int getUserAssignedId() {
    return userAssignedId;
  }


  /**
   * Gets the technology attribute of the Assignment object
   *
   * @return The technology value
   */
  public String getTechnology() {
    return technology;
  }


  /**
   * Gets the role attribute of the Assignment object
   *
   * @return The role value
   */
  public String getRole() {
    return role;
  }


  /**
   * Gets the estimatedLoe attribute of the Assignment object
   *
   * @return The estimatedLoe value
   */
  public int getEstimatedLoe() {
    return estimatedLoe;
  }


  /**
   * Gets the estimatedLoeValue attribute of the Assignment object
   *
   * @return The estimatedLoeValue value
   */
  public String getEstimatedLoeValue() {
    return (estimatedLoe == -1 ? "" : "" + estimatedLoe);
  }


  /**
   * Gets the estimatedLoeTypeId attribute of the Assignment object
   *
   * @return The estimatedLoeTypeId value
   */
  public int getEstimatedLoeTypeId() {
    return estimatedLoeTypeId;
  }


  /**
   * Gets the estimatedLoeType attribute of the Assignment object
   *
   * @return The estimatedLoeType value
   */
  public String getEstimatedLoeType() {
    return estimatedLoeType;
  }


  /**
   * Gets the actualLoe attribute of the Assignment object
   *
   * @return The actualLoe value
   */
  public int getActualLoe() {
    return actualLoe;
  }


  /**
   * Gets the actualLoeValue attribute of the Assignment object
   *
   * @return The actualLoeValue value
   */
  public String getActualLoeValue() {
    return (actualLoe == -1 ? "" : "" + actualLoe);
  }


  /**
   * Gets the actualLoeTypeId attribute of the Assignment object
   *
   * @return The actualLoeTypeId value
   */
  public int getActualLoeTypeId() {
    return actualLoeTypeId;
  }


  /**
   * Gets the actualLoeType attribute of the Assignment object
   *
   * @return The actualLoeType value
   */
  public String getActualLoeType() {
    return actualLoeType;
  }


  /**
   * Gets the priorityId attribute of the Assignment object
   *
   * @return The priorityId value
   */
  public int getPriorityId() {
    return priorityId;
  }


  /**
   * Gets the assignDate attribute of the Assignment object
   *
   * @return The assignDate value
   */
  public java.sql.Timestamp getAssignDate() {
    return assignDate;
  }


  /**
   * Gets the estStartDate attribute of the Assignment object
   *
   * @return The estStartDate value
   */
  public java.sql.Timestamp getEstStartDate() {
    return estStartDate;
  }


  /**
   * Gets the estStartDateString attribute of the Assignment object
   *
   * @return The estStartDateString value
   */
  public String getEstStartDateString() {
    String estStartDateString = "--";
    try {
      return DateFormat.getDateInstance(3).format(estStartDate);
    } catch (NullPointerException e) {
    }
    return estStartDateString;
  }


  /**
   * Gets the startDate attribute of the Assignment object
   *
   * @return The startDate value
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   * Gets the dueDate attribute of the Assignment object
   *
   * @return The dueDate value
   */
  public java.sql.Timestamp getDueDate() {
    return dueDate;
  }


  /**
   * Gets the statusId attribute of the Assignment object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Gets the status attribute of the Assignment object
   *
   * @return The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   * Gets the statusDate attribute of the Assignment object
   *
   * @return The statusDate value
   */
  public java.sql.Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   * Gets the statusType attribute of the Assignment object
   *
   * @return The statusType value
   */
  public int getStatusType() {
    return statusType;
  }


  /**
   * Gets the statusTypeId attribute of the Assignment object
   *
   * @return The statusTypeId value
   */
  public int getStatusTypeId() {
    return statusTypeId;
  }


  /**
   * Gets the completeDate attribute of the Assignment object
   *
   * @return The completeDate value
   */
  public java.sql.Timestamp getCompleteDate() {
    return completeDate;
  }


  /**
   * Gets the entered attribute of the Assignment object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredString attribute of the Assignment object
   *
   * @return The enteredString value
   */
  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   * Gets the enteredDateTimeString attribute of the Assignment object
   *
   * @return The enteredDateTimeString value
   */
  public String getEnteredDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   * Gets the enteredBy attribute of the Assignment object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the Assignment object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedString attribute of the Assignment object
   *
   * @return The modifiedString value
   */
  public String getModifiedString() {
    if (modified != null) {
      return modified.toString();
    } else {
      return "";
    }
  }


  /**
   * Gets the modifiedBy attribute of the Assignment object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the estimatedLoeString attribute of the Assignment object
   *
   * @return The estimatedLoeString value
   */
  public String getEstimatedLoeString() {
    if (estimatedLoe == -1) {
      return "--";
    } else {
      String loeTmp = estimatedLoeType;
      if (loeTmp.endsWith("(s)")) {
        return estimatedLoe + " " + estimatedLoeType.substring(
            0, estimatedLoeType.indexOf("(s)")) + (estimatedLoe == 1 ? "" : "s");
      } else {
        return loeTmp;
      }
    }
  }


  /**
   * Gets the estimatedLoeHours attribute of the Assignment object
   *
   * @return The estimatedLoeHours value
   */
  public double getEstimatedLoeHours() {
    if (estimatedLoe == -1) {
      return 0;
    }
    if (estimatedLoeTypeId == -1) {
      return 0;
    }
    switch (estimatedLoeTypeId) {
      case 1:
        return (estimatedLoe / 60);
      case 2:
        return (estimatedLoe);
      case 3:
        return (estimatedLoe * 24);
      case 4:
        return (estimatedLoe * 24 * 7);
    }
    return 0;
  }


  /**
   * Gets the actualLoeHours attribute of the Assignment object
   *
   * @return The actualLoeHours value
   */
  public double getActualLoeHours() {
    if (actualLoe == -1) {
      return 0;
    }
    if (actualLoeTypeId == -1) {
      return 0;
    }
    switch (actualLoeTypeId) {
      case 1:
        return (actualLoe / 60);
      case 2:
        return (actualLoe);
      case 3:
        return (actualLoe * 24);
      case 4:
        return (actualLoe * 24 * 7);
    }
    return 0;
  }


  /**
   * Gets the actualLoeString attribute of the Assignment object
   *
   * @return The actualLoeString value
   */
  public String getActualLoeString() {
    if (actualLoe == -1) {
      return "--";
    } else {
      String loeTmp = actualLoeType;
      if (loeTmp.endsWith("(s)")) {
        return actualLoe + " " + actualLoeType.substring(
            0, actualLoeType.indexOf("(s)")) + (actualLoe == 1 ? "" : "s");
      } else {
        return loeTmp;
      }
    }
  }


  /**
   * Gets the assignDateString attribute of the Assignment object
   *
   * @return The assignDateString value
   */
  public String getAssignDateString() {
    String assignString = "--";
    try {
      return DateFormat.getDateInstance(3).format(assignDate);
    } catch (NullPointerException e) {
    }
    return assignString;
  }


  /**
   * Gets the assignDateValue attribute of the Assignment object
   *
   * @return The assignDateValue value
   */
  public String getAssignDateValue() {
    String assignString = "";
    try {
      return DateFormat.getDateInstance(3).format(assignDate);
    } catch (NullPointerException e) {
    }
    return assignString;
  }


  /**
   * Gets the DueDateString attribute of the Assignment object
   *
   * @param timeZone Description of the Parameter
   * @return The DueDateString value
   */
  public String getDueDateString(String timeZone) {
    String dueDateString = "--";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
          3);
      TimeZone tz = TimeZone.getTimeZone(timeZone);
      formatter.setTimeZone(tz);
      return formatter.format(dueDate);
    } catch (NullPointerException e) {
    }
    return dueDateString;
  }


  /**
   * Gets the dueDateString attribute of the Assignment object
   *
   * @return The dueDateString value
   */
  public String getDueDateString() {
    return getDueDateString(null);
  }


  /**
   * Gets the dueDateValue attribute of the Assignment object
   *
   * @return The dueDateValue value
   */
  public String getDueDateValue() {
    String dueDateString = "";
    try {
      return DateFormat.getDateInstance(3).format(dueDate);
    } catch (NullPointerException e) {
    }
    return dueDateString;
  }


  /**
   * Gets the RelativeDueDateString attribute of the Assignment object
   *
   * @param timeZone Description of the Parameter
   * @param locale   Description of the Parameter
   * @return The RelativeDueDateString value
   */
  public String getRelativeDueDateString(String timeZone, Locale locale) {
    String dueDateString = getDueDateString(timeZone);
    if (!dueDateString.equals("--")) {
      Calendar rightNow = Calendar.getInstance();
      rightNow.set(Calendar.HOUR_OF_DAY, 0);
      rightNow.set(Calendar.MINUTE, 0);
      Calendar assignDueDateTest = Calendar.getInstance();
      assignDueDateTest.setTime(dueDate);
      assignDueDateTest.set(Calendar.HOUR_OF_DAY, 0);
      assignDueDateTest.set(Calendar.MINUTE, 0);
      SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
          DateFormat.SHORT, locale);
      formatter.applyPattern(formatter.toPattern() + " z");
      formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
      if (!this.getComplete() && rightNow.after(assignDueDateTest)) {
        return "<font color='red'>" + formatter.format(dueDate) + "</font>";
      } else {
        assignDueDateTest.add(Calendar.DATE, -1);
        if (!this.getComplete() && rightNow.after(assignDueDateTest)) {
          return "<font color='orange'>" + formatter.format(dueDate) + "</font>";
        } else {
          return "<font color='darkgreen'>" + formatter.format(dueDate) + "</font>";
        }
      }
    } else {
      return dueDateString;
    }
  }


  /**
   * Gets the statusDateValue attribute of the Assignment object
   *
   * @return The statusDateValue value
   */
  public String getStatusDateValue() {
    String statusString = "";
    try {
      return DateFormat.getDateInstance(3).format(statusDate);
    } catch (NullPointerException e) {
    }
    return statusString;
  }


  /**
   * Gets the statusDateString attribute of the Assignment object
   *
   * @return The statusDateString value
   */
  public String getStatusDateString() {
    String statusString = "--";
    try {
      return DateFormat.getDateInstance(3).format(statusDate);
    } catch (NullPointerException e) {
    }
    return statusString;
  }


  /**
   * Gets the statusGraphic attribute of the Assignment object
   *
   * @return The statusGraphic value
   */
  public String getStatusGraphic() {
    return statusGraphic;
  }


  /**
   * Gets the statusGraphicTag attribute of the Assignment object
   *
   * @return The statusGraphicTag value
   */
  public String getStatusGraphicTag() {
    return "<img border=\"0\" src=\"images/" + statusGraphic + "\" align=\"absmiddle\" alt=\"" + status + "\">";
  }


  /**
   * Gets the percentComplete attribute of the Assignment object
   *
   * @return The percentComplete value
   */
  public int getPercentComplete() {
    return percentComplete;
  }


  /**
   * Gets the complete attribute of the Assignment object
   *
   * @return The complete value
   */
  public boolean getComplete() {
    return (completeDate != null);
  }


  /**
   * Gets the displayLevel attribute of the Assignment object
   *
   * @return The displayLevel value
   */
  public int getDisplayLevel() {
    return displayLevel;
  }


  /**
   * Gets the levelOpen attribute of the Assignment object
   *
   * @return The levelOpen value
   */
  public boolean getLevelOpen() {
    return levelOpen;
  }


  /**
   * Gets the indent attribute of the Assignment object
   *
   * @return The indent value
   */
  public int getIndent() {
    return indent;
  }


  /**
   * Gets the prevIndent attribute of the Assignment object
   *
   * @return The prevIndent value
   */
  public int getPrevIndent() {
    return prevIndent;
  }


  /**
   * Gets the prevMapId attribute of the Assignment object
   *
   * @return The prevMapId value
   */
  public int getPrevMapId() {
    return prevMapId;
  }


  /**
   * Gets the paddedId attribute of the Assignment object
   *
   * @return The paddedId value
   */
  public String getPaddedId() {
    String padded = (String.valueOf(id));
    while (padded.length() < 6) {
      padded = "0" + padded;
    }
    return padded;
  }


  /**
   * Gets the noteCount attribute of the Assignment object
   *
   * @return The noteCount value
   */
  public int getNoteCount() {
    return noteCount;
  }


  /**
   * Sets the noteCount attribute of the Assignment object
   *
   * @param noteCount The new noteCount value
   */
  public void setNoteCount(int noteCount) {
    this.noteCount = noteCount;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasNotes() {
    return noteCount > 0;
  }


  /**
   * Gets the assignmentNote attribute of the Assignment object
   *
   * @return The assignmentNote value
   */
  public AssignmentNote getAssignmentNote() {
    return assignmentNote;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    statusTypeId = lookupStatusIdType(db, statusId);
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "project_assig_assignment_id_seq");
    sql.append(
        "INSERT INTO project_assignments " +
            "(project_id, requirement_id, assignedBy, user_assign_id, technology, " +
            DatabaseUtils.addQuotes(db, "role") + ", estimated_loevalue, estimated_loetype, actual_loevalue, actual_loetype, " +
            "priority_id, assign_date, est_start_date, start_date, " +
            "due_date, due_date_timezone, status_id, status_date, percent_complete, complete_date, ");
    if (id > -1) {
      sql.append("assignment_id, ");
    }

    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("enteredBy, modifiedBy, folder_id ) ");
    sql.append(
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
    if (id > -1) {
      sql.append("?,");
    }
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?) ");

    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, projectId);
    DatabaseUtils.setInt(pst, ++i, requirementId);
    if (userAssignedId > -1) {
      pst.setInt(++i, enteredBy);
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    DatabaseUtils.setInt(pst, ++i, userAssignedId);
    pst.setString(++i, technology);
    pst.setString(++i, role);
    DatabaseUtils.setInt(pst, ++i, estimatedLoe);
    DatabaseUtils.setInt(pst, ++i, estimatedLoeTypeId);
    DatabaseUtils.setInt(pst, ++i, actualLoe);
    DatabaseUtils.setInt(pst, ++i, actualLoeTypeId);
    DatabaseUtils.setInt(pst, ++i, priorityId);
    //Assigned?
    if (userAssignedId > -1 && assignDate == null) {
      java.util.Date tmpDate = new java.util.Date();
      assignDate = new java.sql.Timestamp(tmpDate.getTime());
      assignDate.setNanos(0);
    }
    DatabaseUtils.setTimestamp(pst, ++i, assignDate);
    DatabaseUtils.setTimestamp(pst, ++i, estStartDate);
    if (statusTypeId != NOTSTARTED && statusTypeId != ONHOLD && startDate == null) {
      java.util.Date tmpDate = new java.util.Date();
      startDate = new java.sql.Timestamp(tmpDate.getTime());
      startDate.setNanos(0);
    }
    DatabaseUtils.setTimestamp(pst, ++i, startDate);
    DatabaseUtils.setTimestamp(pst, ++i, dueDate);
    pst.setString(++i, this.dueDateTimeZone);
    DatabaseUtils.setInt(pst, ++i, statusId);
    //Status Date
    if (statusId > -1 && statusDate == null) {
      java.util.Date tmpDate = new java.util.Date();
      statusDate = new java.sql.Timestamp(tmpDate.getTime());
      statusDate.setNanos(0);
    }
    DatabaseUtils.setTimestamp(pst, ++i, statusDate);
    DatabaseUtils.setInt(pst, ++i, percentComplete);
    //Handle assignment complete date
    if ((statusTypeId == COMPLETE || statusTypeId == CLOSED) && completeDate == null) {
      java.util.Date tmpDate = new java.util.Date();
      completeDate = new java.sql.Timestamp(tmpDate.getTime());
      completeDate.setNanos(0);
    }
    DatabaseUtils.setTimestamp(pst, ++i, completeDate);
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    DatabaseUtils.setInt(pst, ++i, folderId);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "project_assig_assignment_id_seq", id);
    if (recordMapItem) {
      //Record the position of this entry
      RequirementMapItem mapItem = new RequirementMapItem();
      mapItem.setProjectId(projectId);
      mapItem.setRequirementId(requirementId);
      mapItem.setAssignmentId(id);
      mapItem.setIndent(indent);
      mapItem.setPrevIndent(prevIndent);
      mapItem.setPrevMapId(prevMapId);
      mapItem.append(db);
      indent = mapItem.getIndent();
      prevIndent = mapItem.getIndent();
      prevMapId = mapItem.getId();
    }
    if (recordNote) {
      // Insert any notes as well
      assignmentNote = new AssignmentNote(this);
      if (assignmentNote.isValid()) {
        assignmentNote.insert(db);
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
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1 || this.projectId == -1) {
      throw new SQLException("Assignment ID was not specified");
    }
    boolean commit = db.getAutoCommit();
    int recordCount = 0;
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      // Remove any assignment notes
      AssignmentNoteList.delete(db, id);
      //Remove the mapped item
      RequirementMapItem mapItem = new RequirementMapItem();
      mapItem.setProjectId(projectId);
      mapItem.setRequirementId(requirementId);
      mapItem.setAssignmentId(id);
      mapItem.remove(db);
      //Delete related status items
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM project_assignments_status " +
              "WHERE assignment_id = ? ");
      pst.setInt(1, id);
      pst.executeUpdate();
      pst.close();
      //Delete the actual assignment
      pst = db.prepareStatement(
          "DELETE FROM project_assignments " +
              "WHERE assignment_id = ? ");
      pst.setInt(1, id);
      recordCount = pst.executeUpdate();
      pst.close();
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
    if (recordCount == 0) {
      return false;
    } else {
      return true;
    }
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    return update(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (this.getId() == -1 || this.projectId == -1) {
      throw new SQLException("ID was not specified");
    }
    int resultCount = 0;
    boolean newStatus = false;
    Assignment previousState = new Assignment(db, id);
    statusTypeId = lookupStatusIdType(db, statusId);
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_assignments " +
            "SET requirement_id = ?, assignedBy = ?, user_assign_id = ?, technology = ?, " +
            DatabaseUtils.addQuotes(db, "role") + " = ?, estimated_loevalue = ?, estimated_loetype = ?, actual_loevalue = ?, " +
            "actual_loetype = ?, priority_id = ?, assign_date = ?, est_start_date = ?, start_date = ?, " +
            "due_date = ?, due_date_timezone = ?, status_id = ?, status_date = ?, percent_complete = ?, complete_date = ?, " +
            "modifiedBy = ?, modified = CURRENT_TIMESTAMP, folder_id = ? " +
            "WHERE assignment_id = ? " +
            "AND modified " + ((this.getModified() == null) ? "IS NULL " : "= ? "));
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, requirementId);
    if (userAssignedId > -1) {
      DatabaseUtils.setInt(pst, ++i, assignedBy);
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    DatabaseUtils.setInt(pst, ++i, userAssignedId);
    pst.setString(++i, technology);
    pst.setString(++i, role);
    DatabaseUtils.setInt(pst, ++i, estimatedLoe);
    DatabaseUtils.setInt(pst, ++i, estimatedLoeTypeId);
    DatabaseUtils.setInt(pst, ++i, actualLoe);
    DatabaseUtils.setInt(pst, ++i, actualLoeTypeId);
    DatabaseUtils.setInt(pst, ++i, priorityId);
    if (previousState.getPriorityId() != priorityId) {
      newStatus = true;
    }
    if (previousState.getUserAssignedId() != userAssignedId) {
      if (assignDate == null) {
        java.util.Date tmpDate = new java.util.Date();
        assignDate = new java.sql.Timestamp(tmpDate.getTime());
        assignDate.setNanos(0);
      }
      newStatus = true;
    } else {
      assignDate = previousState.getAssignDate();
    }
    DatabaseUtils.setTimestamp(pst, ++i, assignDate);
    DatabaseUtils.setTimestamp(pst, ++i, estStartDate);
    //Handle assignment start date
    startDate = previousState.getStartDate();
    if (statusTypeId == NOTSTARTED || statusTypeId == ONHOLD) {
      startDate = null;
    }
    if (previousState.getStartDate() == null && statusTypeId != NOTSTARTED && statusTypeId != ONHOLD) {
      java.util.Date tmpDate = new java.util.Date();
      startDate = new java.sql.Timestamp(tmpDate.getTime());
      startDate.setNanos(0);
    }
    DatabaseUtils.setTimestamp(pst, ++i, startDate);
    DatabaseUtils.setTimestamp(pst, ++i, dueDate);
    pst.setString(++i, this.dueDateTimeZone);
    DatabaseUtils.setInt(pst, ++i, statusId);
    //Handle assignment status date
    if (previousState.getStatusId() != statusId ||
        previousState.getPercentComplete() != percentComplete) {
      if (statusDate == null) {
        java.util.Date tmpDate = new java.util.Date();
        statusDate = new java.sql.Timestamp(tmpDate.getTime());
        statusDate.setNanos(0);
      }
      newStatus = true;
    }
    if (newStatus) {
      java.util.Date tmpDate = new java.util.Date();
      statusDate = new java.sql.Timestamp(tmpDate.getTime());
      statusDate.setNanos(0);
    } else {
      statusDate = previousState.getStatusDate();
    }
    DatabaseUtils.setTimestamp(pst, ++i, statusDate);
    DatabaseUtils.setInt(pst, ++i, percentComplete);
    //Handle assignment complete date
    //A date is saved when the assignment is saved, otherwise it is erased
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "Assignment-> Assignment previous status type id = " + previousState.getStatusTypeId());
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "Assignment-> Assignment status type id = " + statusTypeId);
    }
    if (statusTypeId == COMPLETE || statusTypeId == CLOSED) {
      if (previousState.getStatusTypeId() != COMPLETE && previousState.getStatusTypeId() != CLOSED) {
        java.util.Date tmpDate = new java.util.Date();
        completeDate = new java.sql.Timestamp(tmpDate.getTime());
        completeDate.setNanos(0);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("     * NEW CLOSED DATE");
        }
      } else {
        completeDate = previousState.getCompleteDate();
        if (System.getProperty("DEBUG") != null) {
          System.out.println("     * EXISTING CLOSED DATE");
        }
      }
    } else {
      completeDate = null;
      if (System.getProperty("DEBUG") != null) {
        System.out.println("     * it's not finished");
      }
    }
    DatabaseUtils.setTimestamp(pst, ++i, completeDate);
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setInt(pst, ++i, folderId);
    pst.setInt(++i, this.getId());
    if (this.getModified() != null) {
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();
    // Insert any notes as well
    assignmentNote = new AssignmentNote(this);
    if (assignmentNote.isValid()) {
      assignmentNote.insert(db);
    }
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int updateDueDate(Connection db, ActionContext context) throws SQLException {
    if (this.getId() == -1 || this.projectId == -1 || this.getModifiedBy() == -1) {
      throw new SQLException("ID was not specified");
    }
    String sql =
        "UPDATE project_assignments " +
            "SET due_date = ?, due_date_timezone = ?, " +
            "modifiedBy = ?, modified = CURRENT_TIMESTAMP " +
            "WHERE assignment_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    int i = 0;
    if (dueDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(++i, dueDate);
    }
    pst.setString(++i, this.dueDateTimeZone);
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    int resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db          Description of the Parameter
   * @param newFolderId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void updateFolderId(Connection db, int newFolderId) throws SQLException {
    if (this.getId() == -1 || newFolderId == -1) {
      throw new SQLException("ID was not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_assignments " +
            "SET folder_id = ? " +
            "WHERE assignment_id = ? ");
    if (newFolderId == 0) {
      pst.setNull(1, java.sql.Types.INTEGER);
    } else {
      pst.setInt(1, newFolderId);
    }
    pst.setInt(2, id);
    pst.executeUpdate();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param statusId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int lookupStatusIdType(Connection db, int statusId) throws SQLException {
    int tmpStatusTypeId = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT " + DatabaseUtils.addQuotes(db, "type") + " " +
            "FROM lookup_project_status s " +
            "WHERE s.code = ? ");
    pst.setInt(1, statusId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      tmpStatusTypeId = rs.getInt("type");
    }
    rs.close();
    pst.close();
    return tmpStatusTypeId;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param assignId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean reassign(Connection db, int assignId) throws SQLException {
    int result = -1;
    userAssignedId = assignId;
    result = this.update(db);
    if (result == -1) {
      return false;
    }
    return true;
  }


  /**
   * The following fields depend on a timezone preference
   *
   * @return The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("dueDate");
    thisList.add("estStartDate");
    return thisList;
  }


  /**
   * Gets the overdue attribute of the Assignment object
   *
   * @return The overdue value
   */
  public boolean isOverdue() {
    if (dueDate == null || this.getComplete()) {
      return false;
    }
    Timestamp rightNow = new Timestamp(System.currentTimeMillis());
    return (rightNow.after(dueDate));
  }


  /**
   * Description of the Method
   *
   * @param tz      Description of the Parameter
   * @param created Description of the Parameter
   * @return Description of the Return Value
   */
  public String generateWebcalEvent(TimeZone tz, Timestamp created) {
    StringBuffer webcal = new StringBuffer();
    String CRLF = System.getProperty("line.separator");

    String description = "";
    if (project != null && project.getTitle() != null) {
      description += "Project: " + project.getTitle();
    }
    if (status != null) {
      description += "\\nStatus: " + status;
      description += "(" + (percentComplete > 0 ? percentComplete : 0) + "%)";
    }

    //write the event
    webcal.append("BEGIN:VEVENT" + CRLF);
    webcal.append(
        "UID:www.centriccrm.com-projects-assignment-events" + this.getId() + CRLF);

    if (created != null) {
      webcal.append("DTSTAMP:" + ICalendar.getDateTimeUTC(created) + CRLF);
    }
    if (entered != null) {
      webcal.append("CREATED:" + ICalendar.getDateTimeUTC(entered) + CRLF);
    }
    if (dueDate != null) {
      webcal.append(
          "DTSTART;TZID=" + tz.getID() + ":" + ICalendar.getDateTime(
              tz, dueDate) + CRLF);
    }
    if (role != null) {
      webcal.append(ICalendar.foldLine("SUMMARY:" + role) + CRLF);
    }
    if (description != null) {
      webcal.append(
          ICalendar.foldLine(
              "DESCRIPTION:" + ICalendar.parseNewLine(description)) + CRLF);
    }
    if (priority != null) {
      webcal.append(
          "PRIORITY;VALUE=INTEGER:" + ICalendar.getPriority(priority) + CRLF);
    }
    if (dueDate != null) {
      webcal.append(
          "DTEND;TZID=" + tz.getID() + ":" + ICalendar.getDateTime(
              tz, dueDate) + CRLF);
    }

    webcal.append("END:VEVENT" + CRLF);

    return webcal.toString();
  }
}

