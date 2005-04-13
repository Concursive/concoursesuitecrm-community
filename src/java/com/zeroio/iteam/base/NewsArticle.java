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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    June 23, 2003
 *@version    $Id$
 */
public class NewsArticle extends GenericBean {
  //display priorities
  public final static int HIGH = 1;
  public final static int NORMAL = 10;

  //status properties
  public final static int DRAFT = -1;
  public final static int UNAPPROVED = 1;
  public final static int PUBLISHED = 2;
  //portal
  public final static int HOMEPAGE = 10;
  public final static int ARTICLE = 20;
  //templates
  public final static int TEMPLATE_ARTICLE = 1;
  public final static int TEMPLATE_ARTICLE_LINKEDLIST = 2;
  public final static int TEMPLATE_ARTICLE_LIST_PROJECTS = 3;
  public final static int TEMPLATE_LIST_BY_CATEGORIES = 4;
  public final static int TEMPLATE_LIST_PROJECTS = 5;

  //article properties
  private int id = -1;
  private int projectId = -1;
  private int categoryId = -1;
  private String subject = null;
  private String intro = null;
  private String message = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp startDate = null;
  private java.sql.Timestamp endDate = null;
  private int priorityId = NORMAL;
  private boolean enabled = true;
  private boolean allowReplies = false;
  private boolean allowRatings = false;
  private int ratingCount = 0;
  private double avgRating = 0;
  private int readCount = 0;
  private int status = -1;
  private String startDateTimeZone = null;
  private String endDateTimeZone = null;
  private int taskCategoryId = -1;
  private int classificationId = ARTICLE;
  private int templateId = -1;

  
  /**
   *  Constructor for the NewsArticle object
   */
  public NewsArticle() { }


  /**
   *  Constructor for the NewsArticle object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public NewsArticle(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the NewsArticle object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public NewsArticle(Connection db, int id, int projectId) throws SQLException {
    this.projectId = projectId;
    queryRecord(db, id);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  newsId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int newsId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT n.* " +
        "FROM project_news n " +
        "WHERE news_id = ? ");
    if (projectId > -1) {
      sql.append("AND project_id = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, newsId);
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
      throw new SQLException("News record not found.");
    }
    buildResources(db);
  }


  /**
   *  Sets the id attribute of the NewsArticle object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the NewsArticle object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the projectId attribute of the NewsArticle object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the projectId attribute of the NewsArticle object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryId attribute of the NewsArticle object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the NewsArticle object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the subject attribute of the NewsArticle object
   *
   *@param  tmp  The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   *  Sets the intro attribute of the NewsArticle object
   *
   *@param  tmp  The new intro value
   */
  public void setIntro(String tmp) {
    this.intro = tmp;
  }


  /**
   *  Sets the message attribute of the NewsArticle object
   *
   *@param  tmp  The new message value
   */
  public void setMessage(String tmp) {
    this.message = tmp;
  }


  /**
   *  Sets the entered attribute of the NewsArticle object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the NewsArticle object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the NewsArticle object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the NewsArticle object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the NewsArticle object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the NewsArticle object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the NewsArticle object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the NewsArticle object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the startDate attribute of the NewsArticle object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the startDate attribute of the NewsArticle object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the endDate attribute of the NewsArticle object
   *
   *@param  tmp  The new endDate value
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   *  Sets the endDate attribute of the NewsArticle object
   *
   *@param  tmp  The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the priorityId attribute of the NewsArticle object
   *
   *@param  tmp  The new priorityId value
   */
  public void setPriorityId(int tmp) {
    this.priorityId = tmp;
  }


  /**
   *  Sets the priorityId attribute of the NewsArticle object
   *
   *@param  tmp  The new priorityId value
   */
  public void setPriorityId(String tmp) {
    this.priorityId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the NewsArticle object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the allowReplies attribute of the NewsArticle object
   *
   *@param  tmp  The new allowReplies value
   */
  public void setAllowReplies(boolean tmp) {
    this.allowReplies = tmp;
  }


  /**
   *  Sets the allowReplies attribute of the NewsArticle object
   *
   *@param  tmp  The new allowReplies value
   */
  public void setAllowReplies(String tmp) {
    this.allowReplies = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the allowRatings attribute of the NewsArticle object
   *
   *@param  tmp  The new allowRatings value
   */
  public void setAllowRatings(boolean tmp) {
    this.allowRatings = tmp;
  }


  /**
   *  Sets the allowRatings attribute of the NewsArticle object
   *
   *@param  tmp  The new allowRatings value
   */
  public void setAllowRatings(String tmp) {
    this.allowRatings = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the ratingCount attribute of the NewsArticle object
   *
   *@param  tmp  The new ratingCount value
   */
  public void setRatingCount(int tmp) {
    this.ratingCount = tmp;
  }


  /**
   *  Sets the ratingCount attribute of the NewsArticle object
   *
   *@param  tmp  The new ratingCount value
   */
  public void setRatingCount(String tmp) {
    this.ratingCount = Integer.parseInt(tmp);
  }


  /**
   *  Sets the avgRating attribute of the NewsArticle object
   *
   *@param  tmp  The new avgRating value
   */
  public void setAvgRating(double tmp) {
    this.avgRating = tmp;
  }


  /**
   *  Sets the readCount attribute of the NewsArticle object
   *
   *@param  tmp  The new readCount value
   */
  public void setReadCount(int tmp) {
    this.readCount = tmp;
  }


  /**
   *  Sets the readCount attribute of the NewsArticle object
   *
   *@param  tmp  The new readCount value
   */
  public void setReadCount(String tmp) {
    this.readCount = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the NewsArticle object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the status attribute of the NewsArticle object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(int tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the status attribute of the NewsArticle object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = Integer.parseInt(tmp);
  }


  /**
   *  Gets the taskCategoryId attribute of the NewsArticle object
   *
   *@return    The taskCategory value
   */
  public int getTaskCategoryId() {
    return taskCategoryId;
  }


  /**
   *  Sets the taskCategoryId attribute of the NewsArticle object
   *
   *@param  tmp  The new taskCategoryId value
   */
  public void setTaskCategoryId(int tmp) {
    this.taskCategoryId = tmp;
  }


  /**
   *  Sets the taskCategoryId attribute of the NewsArticle object
   *
   *@param  tmp  The new taskCategoryId value
   */
  public void setTaskCategoryId(String tmp) {
    this.taskCategoryId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the classificationId attribute of the NewsArticle object
   *
   *@return    The classificationId value
   */
  public int getClassificationId() {
    return classificationId;
  }
  
  
  /**
   *  Sets the classificationId attribute of the NewsArticle object
   *
   *@param  tmp  The new classificationId value
   */
  public void setClassificationId(int tmp) {
    this.classificationId = tmp;
  }


  /**
   *  Sets the classificationId attribute of the NewsArticle object
   *
   *@param  tmp  The new classificationId value
   */
  public void setClassificationId(String tmp) {
    this.classificationId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the templateId attribute of the NewsArticle object
   *
   *@return    The templateId value
   */
  public int getTemplateId() {
    return templateId;
  }


  /**
   *  Sets the templateId attribute of the NewsArticle object
   *
   *@param  tmp  The new templateId value
   */
  public void setTemplateId(int tmp) {
    this.templateId = tmp;
  }


  /**
   *  Sets the templateId attribute of the NewsArticle object
   *
   *@param  tmp  The new templateId value
   */
  public void setTemplateId(String tmp) {
    this.templateId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the startDateTimeZone attribute of the NewsArticle object
   *
   *@param  tmp  The new startDateTimeZone value
   */
  public void setStartDateTimeZone(String tmp) {
    this.startDateTimeZone = tmp;
  }


  /**
   *  Sets the endDateTimeZone attribute of the NewsArticle object
   *
   *@param  tmp  The new endDateTimeZone value
   */
  public void setEndDateTimeZone(String tmp) {
    this.endDateTimeZone = tmp;
  }


  /**
   *  Gets the startDateTimeZone attribute of the NewsArticle object
   *
   *@return    The startDateTimeZone value
   */
  public String getStartDateTimeZone() {
    return startDateTimeZone;
  }


  /**
   *  Gets the endDateTimeZone attribute of the NewsArticle object
   *
   *@return    The endDateTimeZone value
   */
  public String getEndDateTimeZone() {
    return endDateTimeZone;
  }


  /**
   *  Gets the id attribute of the NewsArticle object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the projectId attribute of the NewsArticle object
   *
   *@return    The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   *  Gets the categoryId attribute of the NewsArticle object
   *
   *@return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Gets the subject attribute of the NewsArticle object
   *
   *@return    The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   *  Gets the intro attribute of the NewsArticle object
   *
   *@return    The intro value
   */
  public String getIntro() {
    return intro;
  }


  /**
   *  Gets the message attribute of the NewsArticle object
   *
   *@return    The message value
   */
  public String getMessage() {
    return message;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasMessage() {
    return (message != null && !"".equals(message));
  }


  /**
   *  Gets the entered attribute of the NewsArticle object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the NewsArticle object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the NewsArticle object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the NewsArticle object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the startDate attribute of the NewsArticle object
   *
   *@return    The startDate value
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Gets the startDateValue attribute of the NewsArticle object
   *
   *@return    The startDateValue value
   */
  public String getStartDateValue() {
    try {
      return DateFormat.getDateInstance(3).format(startDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Gets the endDate attribute of the NewsArticle object
   *
   *@return    The endDate value
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Gets the endDateValue attribute of the NewsArticle object
   *
   *@return    The endDateValue value
   */
  public String getEndDateValue() {
    try {
      return DateFormat.getDateInstance(3).format(endDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Gets the priorityId attribute of the NewsArticle object
   *
   *@return    The priorityId value
   */
  public int getPriorityId() {
    return priorityId;
  }


  /**
   *  Gets the enabled attribute of the NewsArticle object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the allowReplies attribute of the NewsArticle object
   *
   *@return    The allowReplies value
   */
  public boolean getAllowReplies() {
    return allowReplies;
  }


  /**
   *  Gets the allowRatings attribute of the NewsArticle object
   *
   *@return    The allowRatings value
   */
  public boolean getAllowRatings() {
    return allowRatings;
  }


  /**
   *  Gets the ratingCount attribute of the NewsArticle object
   *
   *@return    The ratingCount value
   */
  public int getRatingCount() {
    return ratingCount;
  }


  /**
   *  Gets the avgRating attribute of the NewsArticle object
   *
   *@return    The avgRating value
   */
  public double getAvgRating() {
    return avgRating;
  }


  /**
   *  Gets the readCount attribute of the NewsArticle object
   *
   *@return    The readCount value
   */
  public int getReadCount() {
    return readCount;
  }


  /**
   *  Gets the status attribute of the NewsArticle object
   *
   *@return    The status value
   */
  public int getStatus() {
    return status;
  }


  /**
   *  Gets the enteredDateTimeString attribute of the NewsArticle object
   *
   *@return    The enteredDateTimeString value
   */
  public String getEnteredDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the enteredDateString attribute of the NewsArticle object
   *
   *@return    The enteredDateString value
   */
  public String getEnteredDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the modifiedDateTimeString attribute of the NewsArticle object
   *
   *@return    The modifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the startDateTimeString attribute of the NewsArticle object
   *
   *@return    The startDateTimeString value
   */
  public String getStartDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(startDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasTaskCategoryId() {
    return taskCategoryId > -1;
  }


  /**
   *  Populates this news article from a database result set
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("news_id");
    projectId = rs.getInt("project_id");
    categoryId = DatabaseUtils.getInt(rs, "category_id");
    subject = rs.getString("subject");
    intro = rs.getString("intro");
    message = rs.getString("message");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    startDate = rs.getTimestamp("start_date");
    endDate = rs.getTimestamp("end_date");
    allowReplies = rs.getBoolean("allow_replies");
    allowRatings = rs.getBoolean("allow_rating");
    ratingCount = rs.getInt("rating_count");
    avgRating = rs.getDouble("avg_rating");
    priorityId = rs.getInt("priority_id");
    readCount = rs.getInt("read_count");
    enabled = rs.getBoolean("enabled");
    status = DatabaseUtils.getInt(rs, "status");
    startDateTimeZone = rs.getString("start_date_timezone");
    endDateTimeZone = rs.getString("end_date_timezone");
    classificationId = DatabaseUtils.getInt(rs, "classification_id");
    templateId = DatabaseUtils.getInt(rs, "template_id");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildResources(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT category_id " +
        "FROM taskcategorylink_news " +
        "WHERE news_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      taskCategoryId = rs.getInt("category_id");
    }
    rs.close();
    pst.close();
  }


  /**
   *  Inserts a news article in the database
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO project_news " +
        "(project_id, category_id, subject, intro, message, enabled, status, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append(
        "enteredBy, modifiedBy, " +
        "start_date, start_date_timezone, end_date, end_date_timezone, allow_replies, " +
        "allow_rating, rating_count, avg_rating, priority_id, read_count, classification_id, template_id) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    //Insert the topic
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, projectId);
    DatabaseUtils.setInt(pst, ++i, categoryId);
    pst.setString(++i, subject);
    pst.setString(++i, intro);
    pst.setString(++i, message);
    pst.setBoolean(++i, enabled);
    DatabaseUtils.setInt(pst, ++i, status);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    DatabaseUtils.setTimestamp(pst, ++i, startDate);
    pst.setString(++i, this.startDateTimeZone);
    DatabaseUtils.setTimestamp(pst, ++i, endDate);
    pst.setString(++i, this.endDateTimeZone);
    pst.setBoolean(++i, allowReplies);
    pst.setBoolean(++i, allowRatings);
    pst.setInt(++i, ratingCount);
    pst.setDouble(++i, avgRating);
    DatabaseUtils.setInt(pst, ++i, priorityId);
    pst.setInt(++i, readCount);
    DatabaseUtils.setInt(pst, ++i, classificationId);
    DatabaseUtils.setInt(pst, ++i, templateId);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "project_news_news_id_seq");
    // If there is a task category being associated, insert that too
    if (taskCategoryId > -1) {
      insertTaskCategoryLink(db);
    }
    return true;
  }


  /**
   *  Updates the specified news article in the database
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    int resultCount = 0;
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_news " +
        "SET subject = ?, intro = ?, " +
        "modifiedBy = ?, modified = CURRENT_TIMESTAMP, " +
        "start_date = ?, start_date_timezone = ?, end_date = ?, " +
        "end_date_timezone = ?, allow_replies = ?, allow_rating = ?, " +
        "priority_id = ?, enabled = ?, status = ?, category_id = ?, " +
        "classification_id = ?, template_id = ? " +
        "WHERE news_id = ? " +
        "AND modified = ? ");
    pst.setString(++i, subject);
    pst.setString(++i, intro);
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setTimestamp(pst, ++i, startDate);
    pst.setString(++i, this.startDateTimeZone);
    DatabaseUtils.setTimestamp(pst, ++i, endDate);
    pst.setString(++i, this.endDateTimeZone);
    pst.setBoolean(++i, allowReplies);
    pst.setBoolean(++i, allowRatings);
    DatabaseUtils.setInt(pst, ++i, priorityId);
    pst.setBoolean(++i, enabled);
    DatabaseUtils.setInt(pst, ++i, status);
    DatabaseUtils.setInt(pst, ++i, categoryId);
    DatabaseUtils.setInt(pst, ++i, classificationId);
    DatabaseUtils.setInt(pst, ++i, templateId);
    pst.setInt(++i, id);
    pst.setTimestamp(++i, modified);
    resultCount = pst.executeUpdate();
    pst.close();
    if (resultCount == 1) {
      // See if there is a link already
      boolean hasTaskCategoryLink = false;
      boolean sameTaskCategoryLink = false;
      pst = db.prepareStatement(
          "SELECT category_id " +
          "FROM taskcategorylink_news " +
          "WHERE news_id = ? ");
      pst.setInt(1, id);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        hasTaskCategoryLink = true;
        if (rs.getInt("category_id") == taskCategoryId) {
          sameTaskCategoryLink = true;
        }
      }
      rs.close();
      pst.close();
      //
      if (hasTaskCategoryLink && taskCategoryId > -1 && !sameTaskCategoryLink) {
        // Delete the previous link(s)
        deleteTaskCategoryLink(db);
        // Insert the new link
        insertTaskCategoryLink(db);
      }
      //
      if (hasTaskCategoryLink && taskCategoryId == -1) {
        // Delete the previous link(s)
        deleteTaskCategoryLink(db);
      }
      //
      if (!hasTaskCategoryLink && taskCategoryId > -1) {
        // Insert the new link
        insertTaskCategoryLink(db);
      }
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insertTaskCategoryLink(Connection db) throws SQLException {
    PreparedStatement pst = null;
    // First, make sure the category belongs to this project
    boolean belongsToProject = false;
    pst = db.prepareStatement(
        "SELECT category_id " +
        "FROM taskcategory_project " +
        "WHERE category_id = ? " +
        "AND project_id = ? ");
    pst.setInt(1, taskCategoryId);
    pst.setInt(2, projectId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      belongsToProject = true;
    }
    rs.close();
    pst.close();
    if (belongsToProject) {
      // Then insert the link
      pst = db.prepareStatement(
          "INSERT INTO taskcategorylink_news " +
          "(category_id, news_id) VALUES (?, ?) ");
      pst.setInt(1, taskCategoryId);
      pst.setInt(2, id);
      pst.execute();
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void deleteTaskCategoryLink(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE from taskcategorylink_news " +
        "WHERE news_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int updatePage(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    int resultCount = 0;
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_news " +
        "SET message = ?, " +
        "modifiedBy = ?, modified = CURRENT_TIMESTAMP " +
        "WHERE news_id = ? ");
    pst.setString(++i, message);
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int deletePage(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    int resultCount = 0;
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_news " +
        "SET message = null, " +
        "modifiedBy = ?, modified = CURRENT_TIMESTAMP " +
        "WHERE news_id = ? ");
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    message = null;
    return resultCount;
  }


  /**
   *  Deletes the specified news article from the database
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String basePath) throws SQLException {
    if (id == -1 || projectId == -1) {
      throw new SQLException("ID was not specified");
    }
    PreparedStatement pst = null;
    try {
      db.setAutoCommit(false);
      // Delete task category link
      deleteTaskCategoryLink(db);
      // Delete attached files
      FileItemList files = new FileItemList();
      files.setLinkModuleId(Constants.NEWSARTICLE_FILES);
      files.setLinkItemId(id);
      files.buildList(db);
      files.delete(db, basePath);
      // Delete the news
      pst = db.prepareStatement(
          "DELETE FROM project_news " +
          "WHERE news_id = ? ");
      pst.setInt(1, id);
      pst.execute();
      pst.close();
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
   *  The following fields depend on a timezone preference
   *
   *@return    The dateTimeParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("startDate");
    thisList.add("endDate");
    return thisList;
  }
}

