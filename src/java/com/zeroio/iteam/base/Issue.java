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
 *  Represents an issue in iTeam
 *
 *@author     mrajkowski
 *@created    July 23, 2001
 *@version    $Id$
 */
public class Issue extends GenericBean {

  private int id = -1;
  private Project project = null;
  private int projectId = -1;
  private int categoryId = -1;
  private String category = null;
  private String subject = null;
  private String body = "";
  private int importance = -1;
  private boolean enabled = false;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;

  private String user = "";
  private int replyCount = 0;
  private java.sql.Timestamp replyDate = null;
  private IssueReplyList replyList = null;


  /**
   *  Constructor for the Issue object
   *
   *@since
   */
  public Issue() { }


  /**
   *  Constructor for the Issue object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Issue(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Issue object
   *
   *@param  db                Description of the Parameter
   *@param  issueId           Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Issue(Connection db, int issueId, int projectId) throws SQLException {
    this.projectId = projectId;
    queryRecord(db, issueId);
  }


  /**
   *  Constructor for the Issue object
   *
   *@param  db                Description of the Parameter
   *@param  issueId           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Issue(Connection db, int issueId) throws SQLException {
    queryRecord(db, issueId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  issueId           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int issueId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT i.*, li.description " +
        "FROM project_issues i " +
        " LEFT JOIN lookup_project_issues li ON (i.type_id = li.code) " +
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
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Issue record not found.");
    }
    rs.close();
    pst.close();

    buildReplyCount(db);
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    //project_issues table
    id = rs.getInt("issue_id");
    projectId = rs.getInt("project_id");
    categoryId = DatabaseUtils.getInt(rs, "type_id");
    subject = rs.getString("subject");
    body = rs.getString("message");
    importance = DatabaseUtils.getInt(rs, "importance");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredBy");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedBy");

    //lookup_project_issues table
    category = rs.getString("description");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildReplyCount(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS replycount " +
        "FROM project_issue_replies " +
        "WHERE issue_id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      replyCount = rs.getInt("replycount");
    } else {
      replyCount = 0;
    }
    rs.close();
    pst.close();

    pst = db.prepareStatement(
        "SELECT max(modified) AS replydate " +
        "FROM project_issue_replies " +
        "WHERE issue_id = ? ");
    pst.setInt(1, this.getId());
    rs = pst.executeQuery();
    if (rs.next()) {
      replyDate = rs.getTimestamp("replydate");
    }
    rs.close();
    pst.close();

    if (replyDate == null) {
      replyDate = modified;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildReplyList(Connection db) throws SQLException {
    replyList = new IssueReplyList();
    replyList.setIssue(this);
    replyList.setIssueId(this.getId());
    replyList.buildList(db);
  }


  /**
   *  Gets the RelativeIssueDateString attribute of the Issue object
   *
   *@return    The RelativeIssueDateString value
   *@since
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
   *  Sets the id attribute of the Issue object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Issue object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the project attribute of the Issue object
   *
   *@param  tmp  The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   *  Sets the projectId attribute of the Issue object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the projectId attribute of the Issue object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryId attribute of the Issue object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the Issue object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the category attribute of the Issue object
   *
   *@param  tmp  The new category value
   */
  public void setCategory(String tmp) {
    this.category = tmp;
  }


  /**
   *  Sets the subject attribute of the Issue object
   *
   *@param  tmp  The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   *  Sets the body attribute of the Issue object
   *
   *@param  tmp  The new body value
   */
  public void setBody(String tmp) {
    this.body = tmp;
  }


  /**
   *  Sets the importance attribute of the Issue object
   *
   *@param  tmp  The new importance value
   */
  public void setImportance(int tmp) {
    this.importance = tmp;
  }


  /**
   *  Sets the importance attribute of the Issue object
   *
   *@param  tmp  The new importance value
   */
  public void setImportance(String tmp) {
    this.importance = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the Issue object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the Issue object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the entered attribute of the Issue object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Issue object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Issue object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Issue object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Issue object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Issue object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Issue object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Issue object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the replyCount attribute of the Issue object
   *
   *@param  tmp  The new replyCount value
   */
  public void setReplyCount(int tmp) {
    this.replyCount = tmp;
  }


  /**
   *  Sets the user attribute of the Issue object
   *
   *@param  tmp  The new user value
   */
  public void setUser(String tmp) {
    this.user = tmp;
  }


  /**
   *  Gets the id attribute of the Issue object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the project attribute of the Issue object
   *
   *@return    The project value
   */
  public Project getProject() {
    return project;
  }


  /**
   *  Gets the projectId attribute of the Issue object
   *
   *@return    The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   *  Gets the categoryId attribute of the Issue object
   *
   *@return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Gets the category attribute of the Issue object
   *
   *@return    The category value
   */
  public String getCategory() {
    return category;
  }


  /**
   *  Gets the subject attribute of the Issue object
   *
   *@return    The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   *  Gets the body attribute of the Issue object
   *
   *@return    The body value
   */
  public String getBody() {
    return body;
  }


  /**
   *  Gets the importance attribute of the Issue object
   *
   *@return    The importance value
   */
  public int getImportance() {
    return importance;
  }


  /**
   *  Gets the enabled attribute of the Issue object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the entered attribute of the Issue object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredString attribute of the Issue object
   *
   *@return    The enteredString value
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
   *  Gets the enteredDateTimeString attribute of the Issue object
   *
   *@return    The enteredDateTimeString value
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
   *  Gets the enteredBy attribute of the Issue object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the Issue object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the Issue object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the replyCount attribute of the Issue object
   *
   *@return    The replyCount value
   */
  public int getReplyCount() {
    return replyCount;
  }


  /**
   *  Gets the replyCountString attribute of the Issue object
   *
   *@return    The replyCountString value
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
   *  Gets the replyDateTimeString attribute of the Issue object
   *
   *@return    The replyDateTimeString value
   */
  public String getReplyDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(3, 3).format(replyDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the replyDateString attribute of the Issue object
   *
   *@return    The replyDateString value
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
   *  Gets the user attribute of the Issue object
   *
   *@return    The user value
   */
  public String getUser() {
    return user;
  }


  /**
   *  Gets the replyList attribute of the Issue object
   *
   *@return    The replyList value
   */
  public IssueReplyList getReplyList() {
    return replyList;
  }


  /**
   *  Gets the valid attribute of the Issue object
   *
   *@return    The valid value
   */
  private boolean isValid() {
    if (projectId == -1) {
      errors.put("actionError", "Project ID not specified");
    }

    if (subject == null || subject.equals("")) {
      errors.put("subjectError", "Required field");
    }

    if (body == null || body.equals("")) {
      errors.put("bodyError", "Required field");
    }

    if (categoryId == -1) {
      errors.put("categoryIdError", "Required");
    }

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid()) {
      return false;
    }

    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO project_issues ");
    sql.append("(project_id, type_id, subject, message, importance, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("enteredBy, modifiedBy ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?) ");

    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, projectId);
    DatabaseUtils.setInt(pst, ++i, categoryId);
    pst.setString(++i, subject);
    pst.setString(++i, body);
    DatabaseUtils.setInt(pst, ++i, importance);
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

    id = DatabaseUtils.getCurrVal(db, "project_issues_issue_id_seq");

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, ActionContext context) throws SQLException {
    if (this.getId() == -1 || this.projectId == -1) {
      throw new SQLException("ID was not specified");
    }

    int recordCount = 0;
    Statement st = db.createStatement();
    recordCount = st.executeUpdate(
        "DELETE FROM project_issues " +
        "WHERE issue_id = " + id + " ");
    st.close();

    if (recordCount == 0) {
      errors.put("actionError", "Issue could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    return update(db);
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

    if (!isValid()) {
      return -1;
    }

    int resultCount = 0;

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE project_issues " +
        "SET subject = ?, message = ?, importance = ?, " +
        "modifiedBy = ?, modified = CURRENT_TIMESTAMP " +
        "WHERE issue_id = ? " +
        "AND modified = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
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


}


