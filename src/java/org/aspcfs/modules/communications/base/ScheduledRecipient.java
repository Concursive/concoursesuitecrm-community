//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.StringTokenizer;
import java.sql.*;
import com.darkhorseventures.utils.DateUtils;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     chris price
 *@created    September 19, 2002
 *@version    $Id$
 */
public class ScheduledRecipient {

  int id = -1;
  int campaignId = -1;
  int contactId = -1;
  int runId = -1;
  int statusId = 0;
  String status = null;
  java.sql.Timestamp statusDate = null;
  java.sql.Timestamp scheduledDate = null;
  java.sql.Timestamp sentDate = null;
  java.sql.Timestamp replyDate = null;
  java.sql.Timestamp bounceDate = null;


  /**
   *  Constructor for the ScheduledRecipient object
   */
  public ScheduledRecipient() { }


  /**
   *  Constructor for the ScheduledRecipient object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ScheduledRecipient(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   *  Constructor for the ScheduledRecipient object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ScheduledRecipient(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Sets the id attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the campaignId attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Sets the campaignId attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contactId attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the contactId attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the runId attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new runId value
   */
  public void setRunId(int tmp) {
    this.runId = tmp;
  }


  /**
   *  Sets the runId attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new runId value
   */
  public void setRunId(String tmp) {
    this.runId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the status attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the statusDate attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new statusDate value
   */
  public void setStatusDate(java.sql.Timestamp tmp) {
    this.statusDate = tmp;
  }


  /**
   *  Sets the statusDate attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new statusDate value
   */
  public void setStatusDate(String tmp) {
    this.statusDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the scheduledDate attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new scheduledDate value
   */
  public void setScheduledDate(java.sql.Timestamp tmp) {
    this.scheduledDate = tmp;
  }


  /**
   *  Sets the scheduledDate attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new scheduledDate value
   */
  public void setScheduledDate(String tmp) {
    this.scheduledDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the sentDate attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new sentDate value
   */
  public void setSentDate(java.sql.Timestamp tmp) {
    this.sentDate = tmp;
  }


  /**
   *  Sets the sentDate attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new sentDate value
   */
  public void setSentDate(String tmp) {
    this.sentDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the replyDate attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new replyDate value
   */
  public void setReplyDate(java.sql.Timestamp tmp) {
    this.replyDate = tmp;
  }


  /**
   *  Sets the replyDate attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new replyDate value
   */
  public void setReplyDate(String tmp) {
    this.replyDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the bounceDate attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new bounceDate value
   */
  public void setBounceDate(java.sql.Timestamp tmp) {
    this.bounceDate = tmp;
  }


  /**
   *  Sets the bounceDate attribute of the ScheduledRecipient object
   *
   *@param  tmp  The new bounceDate value
   */
  public void setBounceDate(String tmp) {
    this.bounceDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Gets the id attribute of the ScheduledRecipient object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the campaignId attribute of the ScheduledRecipient object
   *
   *@return    The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   *  Gets the contactId attribute of the ScheduledRecipient object
   *
   *@return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the runId attribute of the ScheduledRecipient object
   *
   *@return    The runId value
   */
  public int getRunId() {
    return runId;
  }


  /**
   *  Gets the statusId attribute of the ScheduledRecipient object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the status attribute of the ScheduledRecipient object
   *
   *@return    The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   *  Gets the statusDate attribute of the ScheduledRecipient object
   *
   *@return    The statusDate value
   */
  public java.sql.Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   *  Gets the scheduledDate attribute of the ScheduledRecipient object
   *
   *@return    The scheduledDate value
   */
  public java.sql.Timestamp getScheduledDate() {
    return scheduledDate;
  }


  /**
   *  Gets the sentDate attribute of the ScheduledRecipient object
   *
   *@return    The sentDate value
   */
  public java.sql.Timestamp getSentDate() {
    return sentDate;
  }


  /**
   *  Gets the replyDate attribute of the ScheduledRecipient object
   *
   *@return    The replyDate value
   */
  public java.sql.Timestamp getReplyDate() {
    return replyDate;
  }


  /**
   *  Gets the bounceDate attribute of the ScheduledRecipient object
   *
   *@return    The bounceDate value
   */
  public java.sql.Timestamp getBounceDate() {
    return bounceDate;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Scheduled Recipient not found.");
    }
    
    PreparedStatement pst = db.prepareStatement(
      "SELECT s.* " +
      "FROM scheduled_recipient s " +
      "WHERE s.id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Scheduled Recipient not found.");
    }
    rs.close();
    pst.close();
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

    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO scheduled_recipient " +
          "(campaign_id, contact_id, run_id, status_id, status, status_date, scheduled_date, " +
          "sent_date, reply_date, bounce_date) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getCampaignId());
      pst.setInt(++i, this.getContactId());
      DatabaseUtils.setInt(pst, ++i, runId);
      pst.setInt(++i, this.getStatusId());
      pst.setString(++i, this.getStatus());
      if (statusDate != null) {
        pst.setTimestamp(++i, statusDate);
      } else {
        pst.setNull(++i, java.sql.Types.TIMESTAMP);
      }
      if (scheduledDate != null) {
        pst.setTimestamp(++i, scheduledDate);
      } else {
        pst.setNull(++i, java.sql.Types.TIMESTAMP);
      }
      if (sentDate != null) {
        pst.setTimestamp(++i, sentDate);
      } else {
        pst.setNull(++i, java.sql.Types.TIMESTAMP);
      }
      if (replyDate != null) {
        pst.setTimestamp(++i, replyDate);
      } else {
        pst.setNull(++i, java.sql.Types.TIMESTAMP);
      }
      if (bounceDate != null) {
        pst.setTimestamp(++i, bounceDate);
      } else {
        pst.setNull(++i, java.sql.Types.TIMESTAMP);
      }

      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "scheduled_recipient_id_seq");
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    campaignId = rs.getInt("campaign_id");
    if (rs.wasNull()) {
      campaignId = -1;
    }
    contactId = rs.getInt("contact_id");
    if (rs.wasNull()) {
      contactId = -1;
    }
    runId = rs.getInt("run_id");
    statusId = rs.getInt("status_id");
    status = rs.getString("status");
    statusDate = rs.getTimestamp("status_date");
    scheduledDate = rs.getTimestamp("scheduled_date");
    sentDate = rs.getTimestamp("sent_date");
    replyDate = rs.getTimestamp("reply_date");
    bounceDate = rs.getTimestamp("bounce_date");
  }

}

