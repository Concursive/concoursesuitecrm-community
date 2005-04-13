package com.zeroio.iteam.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Dec 16, 2004
 */

public class AssignmentNote extends GenericBean {
  private int id = -1;
  private int assignmentId = -1;
  private int userId = -1;
  private String description = null;
  private Timestamp entered = null;
  private int statusId = -1;
  private int percentComplete = -1;
  private int projectId = -1;
  private int userAssignedId = -1;

  public AssignmentNote() {
  }

  public AssignmentNote(Assignment assignment) {
    assignmentId = assignment.getId();
    userId = assignment.getModifiedBy();
    description = assignment.getAdditionalNote();
    statusId = assignment.getStatusId();
    percentComplete = assignment.getPercentComplete();
    projectId = assignment.getProjectId();
    userAssignedId = assignment.getUserAssignedId();
  }

  public AssignmentNote(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("status_id");
    assignmentId = rs.getInt("assignment_id");
    userId = rs.getInt("user_id");
    description = rs.getString("description");
    entered = rs.getTimestamp("status_date");
    //percentComplete = rs.getDouble("percent_complete");
    //statusId = rs.getInt("project_status_id");
    //userAssignedId = rs.getInt("user_assign_id");
    //projectId = rs.getInt("project_id");
  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAssignmentId() {
    return assignmentId;
  }

  public void setAssignmentId(int assignmentId) {
    this.assignmentId = assignmentId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Timestamp getEntered() {
    return entered;
  }

  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }

  public int getStatusId() {
    return statusId;
  }

  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }

  public int getPercentComplete() {
    return percentComplete;
  }

  public void setPercentComplete(int percentComplete) {
    if (percentComplete == -1) {
      this.percentComplete = 0;
    } else {
      this.percentComplete = percentComplete;
    }
  }

  public int getProjectId() {
    return projectId;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public int getUserAssignedId() {
    return userAssignedId;
  }

  public void setUserAssignedId(int userAssignedId) {
    this.userAssignedId = userAssignedId;
  }

  public boolean isValid() {
    if (description == null || "".equals(description.trim())) {
      return false;
    }
    if (userId == -1) {
      return false;
    }
    return true;
  }

  public void insert(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement("INSERT INTO project_assignments_status " +
        "(assignment_id, user_id, description, percent_complete, project_status_id, user_assign_id) " +
        "VALUES (?, ?, ?, ?, ?, ?)");
    int i = 0;
    pst.setInt(++i, assignmentId);
    pst.setInt(++i, userId);
    pst.setString(++i, description);
    pst.setInt(++i, percentComplete);
    pst.setInt(++i, statusId);
    DatabaseUtils.setInt(pst, ++i, userAssignedId);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "project_assignmen_status_id_seq");
  }


}
