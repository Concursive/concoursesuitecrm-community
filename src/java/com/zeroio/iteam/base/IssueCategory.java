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
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

/**
 *  Represents an issue category (forum) in iTeam
 *
 *@author     mrajkowski
 *@created    January 15, 2003
 *@version    $Id: IssueCategory.java,v 1.1.136.1 2004/03/19 21:00:50 rvasista
 *      Exp $
 */
public class IssueCategory extends GenericBean {
  //base properties
  private int id = -1;
  private int projectId = -1;
  private String subject = null;
  private String description = null;
  private boolean enabled = true;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  //referential speedups
  private int topicsCount = 0;
  private int postsCount = 0;
  private java.sql.Timestamp lastPostDate = null;
  private int lastPostBy = -1;
  private boolean allowFileAttachments = false;
  //other
  private Project project = null;


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
    if (categoryId == -1) {
      throw new SQLException("Invalid Issue Category.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM project_issues_categories c " +
        "WHERE c.category_id = ? " +
        "AND c.project_id = ? ");
    pst.setInt(1, categoryId);
    pst.setInt(2, projectId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Issue Category record not found.");
    }
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
   *  Sets the id attribute of the IssueCategory object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the IssueCategory object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the projectId attribute of the IssueCategory object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the projectId attribute of the IssueCategory object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the subject attribute of the IssueCategory object
   *
   *@param  tmp  The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   *  Sets the description attribute of the IssueCategory object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the enabled attribute of the IssueCategory object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the IssueCategory object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the entered attribute of the IssueCategory object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the IssueCategory object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the IssueCategory object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the IssueCategory object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the IssueCategory object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the IssueCategory object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the IssueCategory object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the IssueCategory object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the topicsCount attribute of the IssueCategory object
   *
   *@param  tmp  The new topicsCount value
   */
  public void setTopicsCount(int tmp) {
    this.topicsCount = tmp;
  }


  /**
   *  Sets the topicsCount attribute of the IssueCategory object
   *
   *@param  tmp  The new topicsCount value
   */
  public void setTopicsCount(String tmp) {
    this.topicsCount = Integer.parseInt(tmp);
  }


  /**
   *  Sets the postsCount attribute of the IssueCategory object
   *
   *@param  tmp  The new postsCount value
   */
  public void setPostsCount(int tmp) {
    this.postsCount = tmp;
  }


  /**
   *  Sets the postsCount attribute of the IssueCategory object
   *
   *@param  tmp  The new postsCount value
   */
  public void setPostsCount(String tmp) {
    this.postsCount = Integer.parseInt(tmp);
  }


  /**
   *  Sets the lastPostDate attribute of the IssueCategory object
   *
   *@param  tmp  The new lastPostDate value
   */
  public void setLastPostDate(java.sql.Timestamp tmp) {
    this.lastPostDate = tmp;
  }


  /**
   *  Sets the lastPostDate attribute of the IssueCategory object
   *
   *@param  tmp  The new lastPostDate value
   */
  public void setLastPostDate(String tmp) {
    this.lastPostDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the lastPostBy attribute of the IssueCategory object
   *
   *@param  tmp  The new lastPostBy value
   */
  public void setLastPostBy(int tmp) {
    this.lastPostBy = tmp;
  }


  /**
   *  Sets the lastPostBy attribute of the IssueCategory object
   *
   *@param  tmp  The new lastPostBy value
   */
  public void setLastPostBy(String tmp) {
    this.lastPostBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the project attribute of the IssueCategory object
   *
   *@param  tmp  The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
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
   *  Gets the subject attribute of the IssueCategory object
   *
   *@return    The subject value
   */
  public String getSubject() {
    return subject;
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
   *  Gets the enabled attribute of the IssueCategory object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the entered attribute of the IssueCategory object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the IssueCategory object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the IssueCategory object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the IssueCategory object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the topicsCount attribute of the IssueCategory object
   *
   *@return    The topicsCount value
   */
  public int getTopicsCount() {
    return topicsCount;
  }


  /**
   *  Gets the postsCount attribute of the IssueCategory object
   *
   *@return    The postsCount value
   */
  public int getPostsCount() {
    return postsCount;
  }


  /**
   *  Gets the lastPostDate attribute of the IssueCategory object
   *
   *@return    The lastPostDate value
   */
  public java.sql.Timestamp getLastPostDate() {
    return lastPostDate;
  }


  /**
   *  Gets the lastPostBy attribute of the IssueCategory object
   *
   *@return    The lastPostBy value
   */
  public int getLastPostBy() {
    return lastPostBy;
  }


  /**
   *  Gets the project attribute of the IssueCategory object
   *
   *@return    The project value
   */
  public Project getProject() {
    return project;
  }

  public boolean getAllowFileAttachments() {
    return allowFileAttachments;
  }

  public void setAllowFileAttachments(boolean allowFileAttachments) {
    this.allowFileAttachments = allowFileAttachments;
  }

  public void setAllowFileAttachments(String tmp) {
    allowFileAttachments = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("category_id");
    projectId = rs.getInt("project_id");
    subject = rs.getString("subject");
    description = rs.getString("description");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    topicsCount = rs.getInt("topics_count");
    postsCount = rs.getInt("posts_count");
    lastPostDate = rs.getTimestamp("last_post_date");
    lastPostBy = DatabaseUtils.getInt(rs, "last_post_by");
    allowFileAttachments = rs.getBoolean("allow_files");
  }


  /**
   *  Gets the lastIssueDateString attribute of the IssueCategory object
   *
   *@return    The lastIssueDateString value
   */
  public String getLastPostDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(lastPostDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the lastIssueDateTimeString attribute of the IssueCategory object
   *
   *@return    The lastIssueDateTimeString value
   */
  public String getLastPostDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(lastPostDate);
    } catch (NullPointerException e) {
    }
    return tmp;
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
    sql.append(
        "INSERT INTO project_issues_categories " +
        "(project_id, subject, description, enabled, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append(
        "enteredBy, modifiedBy, " +
        "topics_count, posts_count, last_post_date, last_post_by, allow_files) ");
    sql.append("VALUES (?, ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, projectId);
    pst.setString(++i, subject);
    pst.setString(++i, description);
    pst.setBoolean(++i, enabled);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, topicsCount);
    pst.setInt(++i, postsCount);
    DatabaseUtils.setTimestamp(pst, ++i, lastPostDate);
    DatabaseUtils.setInt(pst, ++i, lastPostBy);
    pst.setBoolean(++i, allowFileAttachments);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "project_issue_cate_categ_id_seq");
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (this.getId() == -1 || this.projectId == -1) {
      throw new SQLException("ID was not specified");
    }
    int resultCount = 0;
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_issues_categories " +
        "SET subject = ?, description = ?, " +
        "modifiedby = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "allow_files = ? " +
        "WHERE category_id = ? " +
        "AND modified = ? ");
    pst.setString(++i, subject);
    pst.setString(++i, description);
    pst.setInt(++i, modifiedBy);
    pst.setBoolean(++i, allowFileAttachments);
    pst.setInt(++i, id);
    pst.setTimestamp(++i, modified);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db, String basePath) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      IssueList issueList = new IssueList();
      issueList.setCategoryId(id);
      issueList.setProjectId(projectId);
      issueList.buildList(db);
      issueList.delete(db, basePath);

      //Delete this category
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM project_issues_categories " +
          "WHERE category_id = ? " +
          "AND project_id = ? ");
      pst.setInt(1, id);
      pst.setInt(2, projectId);
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
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
  }
}


