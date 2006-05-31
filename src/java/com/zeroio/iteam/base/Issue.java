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
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.text.DateFormat;
import java.util.Calendar;

/**
 * Represents an issue in iTeam
 *
 * @author mrajkowski
 * @version $Id$
 * @created July 23, 2001
 */
public class Issue extends GenericBean {

  private int id = -1;
  private Project project = null;
  private int projectId = -1;
  private int categoryId = -1;
  private String subject = null;
  private String body = "";
  private int importance = -1;
  private boolean enabled = true;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;

  private int replyCount = 0;
  private java.sql.Timestamp replyDate = null;
  private int replyBy = -1;
  //Resources
  private IssueReplyList replyList = new IssueReplyList();
  private FileItemList files = null;


  /**
   * Constructor for the Issue object
   */
  public Issue() {
  }


  /**
   * Constructor for the Issue object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Issue(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the Issue object
   *
   * @param db        Description of the Parameter
   * @param issueId   Description of the Parameter
   * @param projectId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Issue(Connection db, int issueId, int projectId) throws SQLException {
    this.projectId = projectId;
    queryRecord(db, issueId);
  }


  /**
   * Constructor for the Issue object
   *
   * @param db      Description of the Parameter
   * @param issueId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Issue(Connection db, int issueId) throws SQLException {
    queryRecord(db, issueId);
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param issueId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int issueId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT i.* " +
        "FROM project_issues i " +
        "WHERE issue_id = ? ");
    if (projectId > -1) {
      sql.append("AND project_id = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, issueId);
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
      throw new SQLException("Issue record not found.");
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    //project_issues table
    id = rs.getInt("issue_id");
    projectId = rs.getInt("project_id");
    subject = rs.getString("subject");
    body = rs.getString("message");
    importance = DatabaseUtils.getInt(rs, "importance");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredBy");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedBy");
    categoryId = DatabaseUtils.getInt(rs, "category_id");
    replyCount = rs.getInt("reply_count");
    replyDate = rs.getTimestamp("last_reply_date");
    if (replyDate == null) {
      replyDate = modified;
    }
    replyBy = DatabaseUtils.getInt(rs, "last_reply_by");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildReplyList(Connection db) throws SQLException {
    //replyList = new IssueReplyList();
    replyList.setIssue(this);
    replyList.setIssueId(this.getId());
    replyList.buildList(db);
  }


  /**
   * Gets the RelativeIssueDateString attribute of the Issue object
   *
   * @return The RelativeIssueDateString value
   */
  public String getRelativeEnteredString() {
    Calendar rightNow = Calendar.getInstance();
    rightNow.set(Calendar.HOUR_OF_DAY, 0);
    rightNow.set(Calendar.MINUTE, 0);
    Calendar issuePostedDate = Calendar.getInstance();
    issuePostedDate.setTime(entered);
    issuePostedDate.set(Calendar.HOUR_OF_DAY, 0);
    issuePostedDate.set(Calendar.MINUTE, 0);
    issuePostedDate.add(Calendar.DATE, 1);
    if (rightNow.before(issuePostedDate)) {
      return "today";
    } else {
      issuePostedDate.add(Calendar.DATE, 1);
      if (rightNow.before(issuePostedDate)) {
        return "yesterday";
      } else {
        return getEnteredString();
      }
    }
  }


  /**
   * Sets the id attribute of the Issue object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Issue object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the project attribute of the Issue object
   *
   * @param tmp The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   * Sets the projectId attribute of the Issue object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   * Sets the projectId attribute of the Issue object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryId attribute of the Issue object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the Issue object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the subject attribute of the Issue object
   *
   * @param tmp The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   * Sets the body attribute of the Issue object
   *
   * @param tmp The new body value
   */
  public void setBody(String tmp) {
    this.body = tmp;
  }


  /**
   * Sets the importance attribute of the Issue object
   *
   * @param tmp The new importance value
   */
  public void setImportance(int tmp) {
    this.importance = tmp;
  }


  /**
   * Sets the importance attribute of the Issue object
   *
   * @param tmp The new importance value
   */
  public void setImportance(String tmp) {
    this.importance = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the Issue object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the Issue object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   * Sets the entered attribute of the Issue object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the Issue object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the Issue object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Issue object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the Issue object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the Issue object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the Issue object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the Issue object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the replyCount attribute of the Issue object
   *
   * @param tmp The new replyCount value
   */
  public void setReplyCount(int tmp) {
    this.replyCount = tmp;
  }


  /**
   * Sets the replyDate attribute of the Issue object
   *
   * @param tmp The new replyDate value
   */
  public void setReplyDate(java.sql.Timestamp tmp) {
    this.replyDate = tmp;
  }


  /**
   * Sets the replyDate attribute of the Issue object
   *
   * @param tmp The new replyDate value
   */
  public void setReplyDate(String tmp) {
    this.replyDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the replyBy attribute of the Issue object
   *
   * @param tmp The new replyBy value
   */
  public void setReplyBy(int tmp) {
    this.replyBy = tmp;
  }


  /**
   * Sets the replyBy attribute of the Issue object
   *
   * @param tmp The new replyBy value
   */
  public void setReplyBy(String tmp) {
    this.replyBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the Issue object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the project attribute of the Issue object
   *
   * @return The project value
   */
  public Project getProject() {
    return project;
  }


  /**
   * Gets the projectId attribute of the Issue object
   *
   * @return The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   * Gets the categoryId attribute of the Issue object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Gets the subject attribute of the Issue object
   *
   * @return The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Gets the body attribute of the Issue object
   *
   * @return The body value
   */
  public String getBody() {
    return body;
  }


  /**
   * Gets the importance attribute of the Issue object
   *
   * @return The importance value
   */
  public int getImportance() {
    return importance;
  }


  /**
   * Gets the enabled attribute of the Issue object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the entered attribute of the Issue object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredString attribute of the Issue object
   *
   * @return The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the enteredDateTimeString attribute of the Issue object
   *
   * @return The enteredDateTimeString value
   */
  public String getEnteredDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(3, 3).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the enteredBy attribute of the Issue object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the Issue object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the Issue object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the replyCount attribute of the Issue object
   *
   * @return The replyCount value
   */
  public int getReplyCount() {
    return replyCount;
  }


  /**
   * Gets the replyCountString attribute of the Issue object
   *
   * @return The replyCountString value
   */
  public String getReplyCountString() {
    if (replyCount == 0) {
      return "are no replies";
    } else if (replyCount == 1) {
      return "is 1 reply";
    } else {
      return "are " + replyCount + " replies";
    }
  }


  /**
   * Gets the replyDateTimeString attribute of the Issue object
   *
   * @return The replyDateTimeString value
   */
  public String getReplyDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          replyDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the replyDateString attribute of the Issue object
   *
   * @return The replyDateString value
   */
  public String getReplyDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(replyDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the replyDate attribute of the Issue object
   *
   * @return The replyDate value
   */
  public Timestamp getReplyDate() {
    return replyDate;
  }


  /**
   * Gets the replyBy attribute of the Issue object
   *
   * @return The replyBy value
   */
  public int getReplyBy() {
    return replyBy;
  }


  /**
   * Gets the replyList attribute of the Issue object
   *
   * @return The replyList value
   */
  public IssueReplyList getReplyList() {
    return replyList;
  }

  public FileItemList getFiles() {
    return files;
  }

  public boolean hasFiles() {
    return (files != null && files.size() > 0);
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
    id = DatabaseUtils.getNextSeq(db, "project_issues_issue_id_seq");
    sql.append(
        "INSERT INTO project_issues " +
        "(project_id, category_id, subject, \"message\", importance, enabled, ");
    if (id > -1) {
      sql.append("issue_id, ");
    }
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    if (replyDate != null) {
      sql.append("last_reply_date, ");
    }
    sql.append(
        "enteredBy, modifiedBy, " +
        "reply_count, last_reply_by) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ?, ");
    if (id > -1) {
      sql.append("?, ");
    }
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    if (replyDate != null) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?, ?) ");
    int i = 0;
    try {
      db.setAutoCommit(false);
      //Insert the topic
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, projectId);
      pst.setInt(++i, categoryId);
      pst.setString(++i, subject);
      pst.setString(++i, body);
      DatabaseUtils.setInt(pst, ++i, importance);
      pst.setBoolean(++i, enabled);
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      if (replyDate != null) {
        DatabaseUtils.setTimestamp(pst, ++i, replyDate);
      }
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      pst.setInt(++i, replyCount);
      DatabaseUtils.setInt(pst, ++i, replyBy);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "project_issues_issue_id_seq", id);
      //Update the category count
      i = 0;
      pst = db.prepareStatement(
          "UPDATE project_issues_categories " +
          "SET topics_count = topics_count + 1, " +
          "posts_count = posts_count + 1, " +
          "last_post_date = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
          "last_post_by = ? " +
          "WHERE project_id = ? " +
          "AND category_id = ? ");
      pst.setInt(++i, modifiedBy);
      pst.setInt(++i, projectId);
      pst.setInt(++i, categoryId);
      pst.executeUpdate();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw e;
    } finally {
      db.setAutoCommit(true);
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
  public synchronized boolean delete(Connection db, String filePath) throws SQLException {
    if (id == -1 || projectId == -1 || categoryId == -1) {
      throw new SQLException("Issue ID was not specified");
    }
    boolean canDelete = false;
    boolean commit = db.getAutoCommit();
    try {
      PreparedStatement pst = null;
      int i = 0;
      //Make sure the issue exists, then delete all
      pst = db.prepareStatement(
          "SELECT count(issue_id) AS issue_count " +
          "FROM project_issues " +
          "WHERE issue_id = ?");
      pst.setInt(1, id);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        canDelete = (rs.getInt("issue_count") == 1);
      }
      rs.close();
      pst.close();
      if (canDelete) {
        buildFiles(db);
        if (commit) {
          db.setAutoCommit(false);
        }
        files.delete(db, getFileLibraryPath(filePath, "projects"));
        //Delete the replies
        IssueReplyList issueReplyList = new IssueReplyList();
        issueReplyList.setIssueId(id);
        issueReplyList.setProjectId(projectId);
        issueReplyList.setCategoryId(categoryId);
        issueReplyList.buildList(db);
        int replyCount = issueReplyList.size();
        issueReplyList.delete(db, filePath);
        //Update the category count (plus the issue count=1)
        i = 0;
        pst = db.prepareStatement(
            "UPDATE project_issues_categories " +
            "SET posts_count = posts_count - " + (replyCount + 1) + ", " +
            "topics_count = topics_count - 1 " +
            "WHERE project_id = ? " +
            "AND category_id = ? ");
        pst.setInt(++i, projectId);
        pst.setInt(++i, categoryId);
        pst.executeUpdate();
        pst.close();
        //Delete the issue
        pst = db.prepareStatement(
            "DELETE FROM project_issues " +
            "WHERE issue_id = ? ");
        pst.setInt(1, id);
        pst.execute();
        pst.close();
        if (commit) {
          db.commit();
        }
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
    return true;
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
    if (this.getId() == -1) {
      return -1;
    }
    int resultCount = 0;
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_issues " +
        "SET subject = ?, \"message\" = ?, importance = ?, " +
        "modifiedBy = ?, modified = CURRENT_TIMESTAMP " +
        "WHERE issue_id = ? " +
        "AND modified = ? ");
    pst.setString(++i, subject);
    pst.setString(++i, body);
    DatabaseUtils.setInt(pst, ++i, importance);
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    pst.setTimestamp(++i, modified);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

  public void buildFiles(Connection db) throws SQLException {
    files = new FileItemList();
    files.setLinkModuleId(Constants.DISCUSSION_FILES_TOPIC);
    files.setLinkItemId(this.getId());
    files.buildList(db);
  }
}
