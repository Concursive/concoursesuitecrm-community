/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.DatabaseUtils;
import java.sql.*;
import java.text.*;
import java.util.Calendar;
import java.util.TimeZone;
import org.aspcfs.modules.actions.*;
import org.aspcfs.utils.DateUtils;
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
   *  Populates this news article from a database result set
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("news_id");
    projectId = rs.getInt("project_id");
    categoryId = rs.getInt("category_id");
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
  }


  /**
   *  Gets the valid attribute of the NewsArticle object
   *
   *@return    The valid value
   */
  public boolean isValid() {
    if (projectId == -1) {
      errors.put("actionError", "Project ID not specified");
    }
    if (subject == null || subject.equals("")) {
      errors.put("subjectError", "Required field");
    }
    if (intro == null || intro.equals("") || intro.equals(" \r\n<br />\r\n ")) {
      errors.put("introError", "Required field");
    }
    if (hasErrors()) {
      //Check warnings
      checkWarnings();
      onlyWarnings = false;
      return false;
    } else {
      //Do not check for warnings if it was found that only warnings existed
      // in the previous call to isValid for the same form.
      if (!onlyWarnings) {
        //Check for warnings if there are no errors
        checkWarnings();
        if (hasWarnings()) {
          onlyWarnings = true;
          return false;
        }
      }
      return true;
    }
  }

  /**
   *  Generates warnings that need to be reviewed before the 
   *  form can be submitted.
   */
  protected void checkWarnings() {
    if ((errors.get("endDateError") == null) && 
        (endDate != null) &&
        (startDate != null)) {
      if (endDate.before(startDate)) {
        warnings.put("endDateWarning", "Archive date is earlier than start date");
      }
    }
  }

  
  /**
   *  Inserts a news article in the database
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
        "start_date, end_date, allow_replies, allow_rating, rating_count, avg_rating, priority_id, read_count) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
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
    DatabaseUtils.setTimestamp(pst, ++i, endDate);
    pst.setBoolean(++i, allowReplies);
    pst.setBoolean(++i, allowRatings);
    pst.setInt(++i, ratingCount);
    pst.setDouble(++i, avgRating);
    DatabaseUtils.setInt(pst, ++i, priorityId);
    pst.setInt(++i, readCount);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "project_news_news_id_seq");
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
    if (!isValid()) {
      return -1;
    }
    int resultCount = 0;
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_news " +
        "SET subject = ?, intro = ?, " +
        "modifiedBy = ?, modified = CURRENT_TIMESTAMP, " +
        "start_date = ?, end_date = ?, allow_replies = ?, allow_rating = ?, " +
        "priority_id = ?, enabled = ?, status = ? " +
        "WHERE news_id = ? " +
        "AND modified = ? ");
    pst.setString(++i, subject);
    pst.setString(++i, intro);
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setTimestamp(pst, ++i, startDate);
    DatabaseUtils.setTimestamp(pst, ++i, endDate);
    pst.setBoolean(++i, allowReplies);
    pst.setBoolean(++i, allowRatings);
    DatabaseUtils.setInt(pst, ++i, priorityId);
    pst.setBoolean(++i, enabled);
    DatabaseUtils.setInt(pst, ++i, status);
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
  public boolean delete(Connection db) throws SQLException {
    if (id == -1 || projectId == -1) {
      throw new SQLException("ID was not specified");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM project_news " +
        "WHERE news_id = ? ");
    pst.setInt(1, id);
    pst.execute();
    pst.close();
    return true;
  }
  

  /**
   *  The following fields depend on a timezone preference
   *@return    The dateTimeParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("startDate");
    thisList.add("endDate");
    return thisList;
  }
}

