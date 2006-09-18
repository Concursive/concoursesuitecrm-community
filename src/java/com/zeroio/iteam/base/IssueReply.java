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

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.text.DateFormat;
import java.util.Calendar;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: IssueReply.java,v 1.1.136.1 2004/03/19 21:00:50 rvasista Exp
 *          $
 * @created January 15, 2003
 */
public class IssueReply extends GenericBean {

  private int id = -1;
  private int replyToId = -1;
  private String subject = null;
  private String body = "";
  private int importance = -1;
  //private boolean enabled = false;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private int issueId = -1;
  private Issue issue = null;
  //helpers
  private int projectId = -1;
  private int categoryId = -1;
  //Resources
  private FileItemList files = null;

  /**
   * Constructor for the IssueReply object
   */
  public IssueReply() {
  }


  /**
   * Constructor for the IssueReply object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public IssueReply(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the IssueReply object
   *
   * @param db      Description of the Parameter
   * @param replyId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public IssueReply(Connection db, int replyId) throws SQLException {
    queryRecord(db, replyId);
  }


  /**
   * Constructor for the IssueReply object
   *
   * @param db      Description of the Parameter
   * @param replyId Description of the Parameter
   * @param issueId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public IssueReply(Connection db, int replyId, int issueId) throws SQLException {
    this.setIssueId(issueId);
    queryRecord(db, replyId);
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param replyId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void queryRecord(Connection db, int replyId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT r.* " +
        "FROM project_issue_replies r " +
        "WHERE reply_id = ? ");
    if (issueId > -1) {
      sql.append("AND issue_id = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, replyId);
    if (issueId > -1) {
      pst.setInt(2, issueId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Issue Reply record not found.");
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
    //project_issue_replies table
    id = rs.getInt("reply_id");
    issueId = rs.getInt("issue_id");
    replyToId = rs.getInt("reply_to");
    subject = rs.getString("subject");
    body = rs.getString("message");
    importance = rs.getInt("importance");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredBy");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedBy");
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
   * Sets the id attribute of the IssueReply object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the IssueReply object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the replyToId attribute of the IssueReply object
   *
   * @param tmp The new replyToId value
   */
  public void setReplyToId(int tmp) {
    this.replyToId = tmp;
  }


  /**
   * Sets the replyToId attribute of the IssueReply object
   *
   * @param tmp The new replyToId value
   */
  public void setReplyToId(String tmp) {
    this.replyToId = Integer.parseInt(tmp);
  }


  /**
   * Sets the subject attribute of the IssueReply object
   *
   * @param tmp The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   * Sets the body attribute of the IssueReply object
   *
   * @param tmp The new body value
   */
  public void setBody(String tmp) {
    this.body = tmp;
  }


  /**
   * Sets the importance attribute of the IssueReply object
   *
   * @param tmp The new importance value
   */
  public void setImportance(int tmp) {
    this.importance = tmp;
  }


  /**
   * Sets the importance attribute of the IssueReply object
   *
   * @param tmp The new importance value
   */
  public void setImportance(String tmp) {
    this.importance = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the IssueReply object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the IssueReply object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the IssueReply object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the IssueReply object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the IssueReply object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the IssueReply object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the IssueReply object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the IssueReply object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the issueId attribute of the IssueReply object
   *
   * @param tmp The new issueId value
   */
  public void setIssueId(int tmp) {
    this.issueId = tmp;
  }


  /**
   * Sets the issueId attribute of the IssueReply object
   *
   * @param tmp The new issueId value
   */
  public void setIssueId(String tmp) {
    this.issueId = Integer.parseInt(tmp);
  }


  /**
   * Sets the issue attribute of the IssueReply object
   *
   * @param tmp The new issue value
   */
  public void setIssue(Issue tmp) {
    this.issue = tmp;
  }


  /**
   * Sets the categoryId attribute of the IssueReply object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the IssueReply object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the projectId attribute of the IssueReply object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   * Sets the projectId attribute of the IssueReply object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the IssueReply object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the replyToId attribute of the IssueReply object
   *
   * @return The replyToId value
   */
  public int getReplyToId() {
    return replyToId;
  }


  /**
   * Gets the subject attribute of the IssueReply object
   *
   * @return The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Gets the body attribute of the IssueReply object
   *
   * @return The body value
   */
  public String getBody() {
    return body;
  }


  /**
   * Gets the importance attribute of the IssueReply object
   *
   * @return The importance value
   */
  public int getImportance() {
    return importance;
  }


  /**
   * Gets the entered attribute of the IssueReply object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the IssueReply object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the IssueReply object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the IssueReply object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the issueId attribute of the IssueReply object
   *
   * @return The issueId value
   */
  public int getIssueId() {
    return issueId;
  }


  /**
   * Gets the issue attribute of the IssueReply object
   *
   * @return The issue value
   */
  public Issue getIssue() {
    return issue;
  }


  /**
   * Gets the enteredString attribute of the IssueReply object
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
   * Gets the enteredDateTimeString attribute of the IssueReply object
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
   * Gets the categoryId attribute of the IssueReply object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Gets the projectId attribute of the IssueReply object
   *
   * @return The projectId value
   */
  public int getProjectId() {
    return projectId;
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
    id = DatabaseUtils.getNextSeq(db, "project_issue_repl_reply_id_seq");
    sql.append(
        "INSERT INTO project_issue_replies " +
        "(issue_id, reply_to, subject, " + DatabaseUtils.addQuotes(db, "message")+ ", importance, ");
    if (id > -1) {
      sql.append("reply_id, ");
    }
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("enteredBy, modifiedBy ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ");
    if (id > -1) {
      sql.append("?,");
    }
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?) ");
    int i = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, issueId);
      pst.setInt(++i, replyToId);
      pst.setString(++i, subject);
      pst.setString(++i, body);
      pst.setInt(++i, importance);
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
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "project_issue_repl_reply_id_seq", id);
      //Update the issue count
      i = 0;
      pst = db.prepareStatement(
          "UPDATE project_issues " +
          "SET reply_count = reply_count + 1, " +
          "last_reply_date = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
          "last_reply_by = ? " +
          "WHERE project_id = ? " +
          "AND issue_id = ? ");
      pst.setInt(++i, modifiedBy);
      pst.setInt(++i, projectId);
      pst.setInt(++i, issueId);
      pst.executeUpdate();
      pst.close();
      //Update the category count
      i = 0;
      pst = db.prepareStatement(
          "UPDATE project_issues_categories " +
          "SET posts_count = posts_count + 1, " +
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
    if (id == -1 || issueId == -1 || projectId == -1 || categoryId == -1) {
      throw new SQLException("IssueReply ID was not specified");
    }
    boolean isLastReply = false;
    int previousReplyId = -1;
    Timestamp previousReplyTime = null;
    int previousReplyBy = -1;
    boolean noRepliesLeft = false;
    boolean canDelete = false;
    boolean commit = db.getAutoCommit();
    try {
      PreparedStatement pst = null;
      ResultSet rs = null;
      int i = 0;
      //Make sure the reply exists, then delete all
      pst = db.prepareStatement(
          "SELECT count(reply_id) AS reply_count " +
          "FROM project_issue_replies " +
          "WHERE reply_id = ?");
      pst.setInt(1, id);
      rs = pst.executeQuery();
      if (rs.next()) {
        canDelete = (rs.getInt("reply_count") == 1);
      }
      rs.close();
      pst.close();
      if (canDelete) {
        buildFiles(db);
        if (commit) {
          db.setAutoCommit(false);
        }
        files.delete(db, getFileLibraryPath(filePath, "projects"));
        i = 0;
        pst = db.prepareStatement(
            "SELECT MAX(reply_id) AS previous_reply_id " +
            "FROM project_issue_replies " +
            "WHERE issue_id = ? " +
            "AND reply_id < ? ");
        pst.setInt(++i, issueId);
        pst.setInt(++i, id);
        rs = pst.executeQuery();
        if (rs.next()) {
          previousReplyId = DatabaseUtils.getInt(rs, "previous_reply_id");
        }
        if (previousReplyId == -1) {
          noRepliesLeft = true;
        }
        rs.close();
        pst.close();
        i = 0;
        pst = db.prepareStatement(
            "SELECT MIN(reply_id) AS next_reply_id " +
            "FROM project_issue_replies " +
            "WHERE issue_id = ? " +
            "AND reply_id > ? ");
        pst.setInt(++i, issueId);
        pst.setInt(++i, id);
        rs = pst.executeQuery();
        if (rs.next()) {
          isLastReply = (DatabaseUtils.getInt(rs, "next_reply_id") == -1);
        }
        rs.close();
        pst.close();
        if (noRepliesLeft) {
          pst = db.prepareStatement(
              "SELECT enteredby, entered FROM project_issues " +
              "WHERE issue_id = ? ");
          pst.setInt(1, issueId);
          rs = pst.executeQuery();
          if (rs.next()) {
            previousReplyBy = rs.getInt("enteredby");
            previousReplyTime = rs.getTimestamp("entered");
          }
          rs.close();
          pst.close();
        } else {
          pst = db.prepareStatement(
              "SELECT enteredby, entered FROM project_issue_replies " +
              "WHERE reply_id = ? ");
          pst.setInt(1, previousReplyId);
          rs = pst.executeQuery();
          if (rs.next()) {
            previousReplyBy = rs.getInt("enteredby");
            previousReplyTime = rs.getTimestamp("entered");
          }
          rs.close();
          pst.close();
        }
        i = 0;
        //Update the category count
        StringBuffer sql = new StringBuffer(
            "UPDATE project_issues_categories " +
            "SET posts_count = posts_count - 1 ");
        if (isLastReply && (previousReplyId != -1 || noRepliesLeft)) {
          sql.append(", last_post_date = ?, last_post_by = ? ");
        }
        sql.append(
            "WHERE project_id = ? " +
            "AND category_id = ? ");
        pst = db.prepareStatement(sql.toString());
        if (isLastReply && (previousReplyId != -1 || noRepliesLeft)) {
          DatabaseUtils.setTimestamp(pst, ++i, previousReplyTime);
          DatabaseUtils.setInt(pst, ++i, previousReplyBy);
        }
        pst.setInt(++i, projectId);
        pst.setInt(++i, categoryId);
        pst.executeUpdate();
        pst.close();
        //Update the issue count
        i = 0;
        sql = new StringBuffer(
            "UPDATE project_issues " +
            "SET reply_count = reply_count - 1 ");
        if (isLastReply && (previousReplyId != -1 || noRepliesLeft)) {
          sql.append(", last_reply_date = ?, last_reply_by = ? ");
        }
        sql.append(
            "WHERE project_id = ? " +
            "AND issue_id = ? ");
        pst = db.prepareStatement(sql.toString());
        if (isLastReply && (previousReplyId != -1 || noRepliesLeft)) {
          DatabaseUtils.setTimestamp(pst, ++i, previousReplyTime);
          DatabaseUtils.setInt(pst, ++i, previousReplyBy);
        }
        pst.setInt(++i, projectId);
        pst.setInt(++i, issueId);
        pst.executeUpdate();
        pst.close();
        //Delete the reply
        pst = db.prepareStatement(
            "DELETE FROM project_issue_replies " +
            "WHERE reply_id = ? ");
        pst.setInt(1, id);
        pst.execute();
        pst.close();
      }
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      if (commit) {
        db.rollback();
      }
      return false;
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
    if (this.getId() == -1 || this.issueId == -1) {
      throw new SQLException("ID was not specified");
    }
    if (this.getId() == -1) {
      return -1;
    }
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE project_issue_replies " +
        "SET subject = ?, " + DatabaseUtils.addQuotes(db, "message")+ " = ?, importance = ?, " +
        "modifiedBy = ?, modified = CURRENT_TIMESTAMP " +
        "WHERE reply_id = ? " +
        "AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, subject);
    pst.setString(++i, body);
    pst.setInt(++i, importance);
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    if(this.getModified() != null){
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

  public void buildFiles(Connection db) throws SQLException {
    files = new FileItemList();
    files.setLinkModuleId(Constants.DISCUSSION_FILES_REPLY);
    files.setLinkItemId(this.getId());
    files.buildList(db);
  }
}


