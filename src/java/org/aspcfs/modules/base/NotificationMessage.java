/*
 *  Copyright(c) 2006 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 * Base class to represent "notification"
 *
 * @author Ananth
 * @created September 19, 2006
 */
public class NotificationMessage extends GenericBean {
  private int id = -1;
  private int notifyUser = -1;
  private String module = null;
  private int itemId = -1;
  private Timestamp itemModified = null;
  private Timestamp attempt = null;
  private String notifyType = null;
  private String subject = null;
  private String message = null;
  private int result = -1;
  private String errorMessage = null;


  /**
   * Gets the id attribute of the NotificationMessage object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the NotificationMessage object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the NotificationMessage object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the notifyUser attribute of the NotificationMessage object
   *
   * @return The notifyUser value
   */
  public int getNotifyUser() {
    return notifyUser;
  }


  /**
   * Sets the notifyUser attribute of the NotificationMessage object
   *
   * @param tmp The new notifyUser value
   */
  public void setNotifyUser(int tmp) {
    this.notifyUser = tmp;
  }


  /**
   * Sets the notifyUser attribute of the NotificationMessage object
   *
   * @param tmp The new notifyUser value
   */
  public void setNotifyUser(String tmp) {
    this.notifyUser = Integer.parseInt(tmp);
  }


  /**
   * Gets the module attribute of the NotificationMessage object
   *
   * @return The module value
   */
  public String getModule() {
    return module;
  }


  /**
   * Sets the module attribute of the NotificationMessage object
   *
   * @param tmp The new module value
   */
  public void setModule(String tmp) {
    this.module = tmp;
  }


  /**
   * Gets the itemId attribute of the NotificationMessage object
   *
   * @return The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   * Sets the itemId attribute of the NotificationMessage object
   *
   * @param tmp The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   * Sets the itemId attribute of the NotificationMessage object
   *
   * @param tmp The new itemId value
   */
  public void setItemId(String tmp) {
    this.itemId = Integer.parseInt(tmp);
  }


  /**
   * Gets the itemModified attribute of the NotificationMessage object
   *
   * @return The itemModified value
   */
  public Timestamp getItemModified() {
    return itemModified;
  }


  /**
   * Sets the itemModified attribute of the NotificationMessage object
   *
   * @param tmp The new itemModified value
   */
  public void setItemModified(Timestamp tmp) {
    this.itemModified = tmp;
  }


  /**
   * Sets the itemModified attribute of the NotificationMessage object
   *
   * @param tmp The new itemModified value
   */
  public void setItemModified(String tmp) {
    this.itemModified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the attempt attribute of the NotificationMessage object
   *
   * @return The attempt value
   */
  public Timestamp getAttempt() {
    return attempt;
  }


  /**
   * Sets the attempt attribute of the NotificationMessage object
   *
   * @param tmp The new attempt value
   */
  public void setAttempt(Timestamp tmp) {
    this.attempt = tmp;
  }


  /**
   * Sets the attempt attribute of the NotificationMessage object
   *
   * @param tmp The new attempt value
   */
  public void setAttempt(String tmp) {
    this.attempt = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the notifyType attribute of the NotificationMessage object
   *
   * @return The notifyType value
   */
  public String getNotifyType() {
    return notifyType;
  }


  /**
   * Sets the notifyType attribute of the NotificationMessage object
   *
   * @param tmp The new notifyType value
   */
  public void setNotifyType(String tmp) {
    this.notifyType = tmp;
  }


  /**
   * Gets the subject attribute of the NotificationMessage object
   *
   * @return The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Sets the subject attribute of the NotificationMessage object
   *
   * @param tmp The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   * Gets the message attribute of the NotificationMessage object
   *
   * @return The message value
   */
  public String getMessage() {
    return message;
  }


  /**
   * Sets the message attribute of the NotificationMessage object
   *
   * @param tmp The new message value
   */
  public void setMessage(String tmp) {
    this.message = tmp;
  }


  /**
   * Gets the result attribute of the NotificationMessage object
   *
   * @return The result value
   */
  public int getResult() {
    return result;
  }


  /**
   * Sets the result attribute of the NotificationMessage object
   *
   * @param tmp The new result value
   */
  public void setResult(int tmp) {
    this.result = tmp;
  }


  /**
   * Sets the result attribute of the NotificationMessage object
   *
   * @param tmp The new result value
   */
  public void setResult(String tmp) {
    this.result = Integer.parseInt(tmp);
  }


  /**
   * Gets the errorMessage attribute of the NotificationMessage object
   *
   * @return The errorMessage value
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   * Sets the errorMessage attribute of the NotificationMessage object
   *
   * @param tmp The new errorMessage value
   */
  public void setErrorMessage(String tmp) {
    this.errorMessage = tmp;
  }


  /**
   * Constructor for the NotificationMessage object
   */
  public NotificationMessage() {
  }


  /**
   * Constructor for the NotificationMessage object
   *
   * @param db             Description of the Parameter
   * @param notificationId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public NotificationMessage(Connection db, int notificationId) throws SQLException {
    queryRecord(db, notificationId);
  }


  /**
   * Constructor for the NotificationMessage object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public NotificationMessage(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Description of the Method
   *
   * @param db             Description of the Parameter
   * @param notificationId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int notificationId) throws SQLException {
    if (notificationId == -1) {
      throw new SQLException("Invalid Notification ID specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT n.* " +
            "FROM notification n " +
            "WHERE n.notification_id = ? ");
    pst.setInt(1, notificationId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Record Not Found");
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    this.id = rs.getInt("notification_id");
    this.notifyUser = rs.getInt("notify_user");
    this.module = rs.getString("module");
    this.itemId = rs.getInt("item_id");
    this.itemModified = rs.getTimestamp("item_modified");
    this.attempt = rs.getTimestamp("attempt");
    this.notifyType = rs.getString("notify_type");
    this.subject = rs.getString("subject");
    this.message = rs.getString("message");
    this.result = rs.getInt("result");
    this.errorMessage = rs.getString("errorMessage");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "notification_notification_i_seq");
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO notification (notify_user, " + DatabaseUtils.addQuotes(db, "module") + ", item_id, item_modified, attempt, notify_type, " +
        "subject, " + DatabaseUtils.addQuotes(db, "message") + ", ");
    if (id > -1) {
      sql.append("notification_id, ");
    }
    sql.append("result, errorMessage) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ");
    if (id > -1) {
      sql.append("?, ");
    }
    sql.append("?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.notifyUser);
    pst.setString(++i, this.module);
    pst.setInt(++i, this.itemId);
    pst.setTimestamp(++i, this.itemModified);
    pst.setTimestamp(++i, this.attempt);
    pst.setString(++i, this.notifyType);
    pst.setString(++i, this.subject);
    pst.setString(++i, this.message);
    if (this.getId() > -1) {
      pst.setInt(++i, this.id);
    }
    pst.setInt(++i, this.result);
    pst.setString(++i, this.errorMessage);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "notification_notification_i_seq", id);
    return true;
  }
}

