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
import java.text.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Represents an issue category (forum) in iTeam
 *
 *@author     mrajkowski
 *@created    January 15, 2003
 *@version    $Id$
 */
public class IssueCategory extends GenericBean {

  private int id = -1;
  private String description = null;
  private Project project = null;
  private int projectId = -1;
  private int issueCount = 0;
  private int replyCount = 0;
  private java.sql.Timestamp lastIssueDate = null;
  private java.sql.Timestamp lastReplyDate = null;
  private String modifiedBy = null;


  /**
   *  Constructor for the IssueCategory object
   */
  public IssueCategory() { }


  /**
   *  Constructor for the IssueCategory object
   *
   *@param  db                Description of the Parameter
   *@param  categoryId        Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public IssueCategory(Connection db, int categoryId, int projectId) throws SQLException {
    String sql =
        "SELECT type_id, l.description, i.project_id " +
        "FROM lookup_project_issues l, project_issues i " +
        "WHERE l.code = i.type_id " +
        "AND i.type_id = ? " +
        "AND i.project_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, categoryId);
    pst.setInt(2, projectId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Issue Category record not found.");
    }
    rs.close();
    pst.close();
    this.buildCounts(db);
  }


  /**
   *  Constructor for the IssueCategory object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public IssueCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("type_id");
    description = rs.getString("description");
    projectId = rs.getInt("project_id");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildCounts(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = null;
    sql =
        "SELECT count(*) AS icount, max(i.modified) AS imodified " +
        "FROM project_issues i " +
        "WHERE i.project_id = ? " +
        "AND i.type_id = ? ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, projectId);
    pst.setInt(2, id);
    rs = pst.executeQuery();
    if (rs.next()) {
      issueCount = rs.getInt("icount");
      lastIssueDate = rs.getTimestamp("imodified");
    }
    rs.close();
    pst.close();

    sql =
        "SELECT count(*) AS rcount, max(r.modified) AS rmodified " +
        "FROM project_issue_replies r " +
        "LEFT JOIN project_issues i ON (i.issue_id = r.issue_id) " +
        "WHERE i.project_id = ? " +
        "AND i.type_id = ? ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, projectId);
    pst.setInt(2, id);
    rs = pst.executeQuery();
    if (rs.next()) {
      replyCount = rs.getInt("rcount");
      lastReplyDate = rs.getTimestamp("rmodified");
    }
    rs.close();
    pst.close();
  }


  /**
   *  Sets the project attribute of the IssueCategory object
   *
   *@param  tmp  The new project value
   */
  public void setProject(Project tmp) {
    project = tmp;
  }


  /**
   *  Sets the projectId attribute of the IssueCategory object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    projectId = tmp;
  }


  /**
   *  Gets the id attribute of the IssueCategory object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the projectId attribute of the IssueCategory object
   *
   *@return    The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   *  Gets the description attribute of the IssueCategory object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the issueCount attribute of the IssueCategory object
   *
   *@return    The issueCount value
   */
  public int getIssueCount() {
    return issueCount;
  }


  /**
   *  Gets the replyCount attribute of the IssueCategory object
   *
   *@return    The replyCount value
   */
  public int getReplyCount() {
    return replyCount;
  }


  /**
   *  Gets the lastIssueDate attribute of the IssueCategory object
   *
   *@return    The lastIssueDate value
   */
  public java.sql.Timestamp getLastIssueDate() {
    return lastIssueDate;
  }


  /**
   *  Gets the lastReplyDate attribute of the IssueCategory object
   *
   *@return    The lastReplyDate value
   */
  public java.sql.Timestamp getLastReplyDate() {
    return lastReplyDate;
  }


  /**
   *  Gets the modifiedBy attribute of the IssueCategory object
   *
   *@return    The modifiedBy value
   */
  public String getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the lastIssueDateString attribute of the IssueCategory object
   *
   *@return    The lastIssueDateString value
   */
  public String getLastIssueDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(lastIssueDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the lastIssueDateTimeString attribute of the IssueCategory object
   *
   *@return    The lastIssueDateTimeString value
   */
  public String getLastIssueDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(3, 3).format(lastIssueDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the lastReplyDateString attribute of the IssueCategory object
   *
   *@return    The lastReplyDateString value
   */
  public String getLastReplyDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(lastReplyDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the lastReplyDateTimeString attribute of the IssueCategory object
   *
   *@return    The lastReplyDateTimeString value
   */
  public String getLastReplyDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(3, 3).format(lastReplyDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the threadCount attribute of the IssueCategory object
   *
   *@return    The threadCount value
   */
  public int getThreadCount() {
    return issueCount;
  }


  /**
   *  Gets the postCount attribute of the IssueCategory object
   *
   *@return    The postCount value
   */
  public int getPostCount() {
    return (issueCount + replyCount);
  }


  /**
   *  Gets the latestPostDateTimeString attribute of the IssueCategory object
   *
   *@return    The latestPostDateTimeString value
   */
  public String getLatestPostDateTimeString() {
    if (lastReplyDate == null) {
      return getLastIssueDateTimeString();
    } else {
      if (lastReplyDate.after(lastIssueDate)) {
        return getLastReplyDateTimeString();
      } else {
        return getLastIssueDateTimeString();
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM project_issue_replies " +
          "WHERE issue_id IN (SELECT issue_id FROM project_issues WHERE " +
          "type_id = ? AND project_id = ?) ");
      pst.setInt(1, id);
      pst.setInt(2, projectId);
      pst.execute();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM project_issues " +
          "WHERE type_id = ? " +
          "AND project_id = ? ");
      pst.setInt(1, id);
      pst.setInt(2, projectId);
      pst.execute();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
  }
}


