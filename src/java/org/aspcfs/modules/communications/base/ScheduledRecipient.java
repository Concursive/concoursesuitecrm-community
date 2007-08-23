/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.communications.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import com.darkhorseventures.framework.beans.GenericBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  Description of the Class
 *
 * @author     chris price
 * @created    September 19, 2002
 * @version    $Id: ScheduledRecipient.java,v 1.5 2002/09/19 19:24:02 mrajkowski
 *      Exp $
 */
public class ScheduledRecipient extends GenericBean{

  private int id = -1;
  private int campaignId = -1;
  private int contactId = -1;
  private int runId = -1;
  private int statusId = 0;
  private String status = null;
  private java.sql.Timestamp statusDate = null;
  private java.sql.Timestamp scheduledDate = null;
  private java.sql.Timestamp sentDate = null;
  private java.sql.Timestamp replyDate = null;
  private java.sql.Timestamp bounceDate = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;

  /**
   *  Constructor for the ScheduledRecipient object
   */
  public ScheduledRecipient() { }

  /**
   *  Constructor for the ScheduledRecipient object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public ScheduledRecipient(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }

  /**
   *  Constructor for the ScheduledRecipient object
   *
   * @param  db                Description of the Parameter
   * @param  campaignId        Description of the Parameter
   * @param  contactId         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ScheduledRecipient(Connection db, int campaignId, int contactId) throws SQLException {
    queryRecord(db, campaignId, contactId);
  }

  /**
   *  Constructor for the ScheduledRecipient object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public ScheduledRecipient(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }

  /**
   * Constructor for the ScheduledRecipient object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of the Exception
   */
  public ScheduledRecipient(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  /**
   *  Sets the id attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }

  /**
   *  Sets the id attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }

  /**
   *  Sets the campaignId attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Sets the campaignId attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contactId attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the contactId attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the runId attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new runId value
   */
  public void setRunId(int tmp) {
    this.runId = tmp;
  }


  /**
   *  Sets the runId attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new runId value
   */
  public void setRunId(String tmp) {
    this.runId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the status attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the statusDate attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new statusDate value
   */
  public void setStatusDate(java.sql.Timestamp tmp) {
    this.statusDate = tmp;
  }


  /**
   *  Sets the statusDate attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new statusDate value
   */
  public void setStatusDate(String tmp) {
    this.statusDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the scheduledDate attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new scheduledDate value
   */
  public void setScheduledDate(java.sql.Timestamp tmp) {
    this.scheduledDate = tmp;
  }


  /**
   *  Sets the scheduledDate attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new scheduledDate value
   */
  public void setScheduledDate(String tmp) {
    this.scheduledDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the sentDate attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new sentDate value
   */
  public void setSentDate(java.sql.Timestamp tmp) {
    this.sentDate = tmp;
  }


  /**
   *  Sets the sentDate attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new sentDate value
   */
  public void setSentDate(String tmp) {
    this.sentDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the replyDate attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new replyDate value
   */
  public void setReplyDate(java.sql.Timestamp tmp) {
    this.replyDate = tmp;
  }


  /**
   *  Sets the replyDate attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new replyDate value
   */
  public void setReplyDate(String tmp) {
    this.replyDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the bounceDate attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new bounceDate value
   */
  public void setBounceDate(java.sql.Timestamp tmp) {
    this.bounceDate = tmp;
  }


  /**
   *  Sets the bounceDate attribute of the ScheduledRecipient object
   *
   * @param  tmp  The new bounceDate value
   */
  public void setBounceDate(String tmp) {
    this.bounceDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Gets the id attribute of the ScheduledRecipient object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the campaignId attribute of the ScheduledRecipient object
   *
   * @return    The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   *  Gets the contactId attribute of the ScheduledRecipient object
   *
   * @return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the runId attribute of the ScheduledRecipient object
   *
   * @return    The runId value
   */
  public int getRunId() {
    return runId;
  }


  /**
   *  Gets the statusId attribute of the ScheduledRecipient object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the status attribute of the ScheduledRecipient object
   *
   * @return    The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   *  Gets the statusDate attribute of the ScheduledRecipient object
   *
   * @return    The statusDate value
   */
  public java.sql.Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   *  Gets the scheduledDate attribute of the ScheduledRecipient object
   *
   * @return    The scheduledDate value
   */
  public java.sql.Timestamp getScheduledDate() {
    return scheduledDate;
  }


  /**
   *  Gets the sentDate attribute of the ScheduledRecipient object
   *
   * @return    The sentDate value
   */
  public java.sql.Timestamp getSentDate() {
    return sentDate;
  }


  /**
   *  Gets the replyDate attribute of the ScheduledRecipient object
   *
   * @return    The replyDate value
   */
  public java.sql.Timestamp getReplyDate() {
    return replyDate;
  }


  /**
   *  Gets the bounceDate attribute of the ScheduledRecipient object
   *
   * @return    The bounceDate value
   */
  public java.sql.Timestamp getBounceDate() {
    return bounceDate;
  }

  /**
   * @return the entered
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }

  /**
   * @param entered the entered to set
   */
  public void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
  }

  /**
   * @param entered the entered to set
   */
  public void setEntered(String entered) {
    this.entered = DatabaseUtils.parseTimestamp(entered);
  }

  /**
   * @return the modified
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(java.sql.Timestamp modified) {
    this.modified = modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(String modified) {
    this.modified = DatabaseUtils.parseTimestamp(modified);
  }

  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  id             Description of the Parameter
   * @throws  SQLException  Description of the Exception
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
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Scheduled Recipient not found.");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  campaignId        Description of the Parameter
   * @param  contactId         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int campaignId, int contactId) throws SQLException {
    if (campaignId == -1) {
      throw new SQLException("Invalid Campaign ID specified");
    }
    if (contactId == -1) {
      throw new SQLException("Invalid Contact ID specified");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT s.* " +
        "FROM scheduled_recipient s " +
        "WHERE s.campaign_id = ? " +
        "AND s.contact_id = ? ");
    pst.setInt(1, campaignId);
    pst.setInt(2, contactId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Scheduled Recipient not found.");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();

    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "scheduled_recipient_id_seq");
      sql.append(
          "INSERT INTO scheduled_recipient " +
          "(" + (id > -1 ? "id, " : "") + "campaign_id, contact_id, run_id, status_id, status, status_date, scheduled_date, " +
          "sent_date, reply_date, bounce_date, modified, entered) ");
      sql.append(
          "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
      if (modified != null) {
        sql.append(", ?");
      }else{
        sql.append(", " + DatabaseUtils.getCurrentTimestamp(db));
      }
      if (entered != null) {
        sql.append(", ?");
      }else{
        sql.append(", " +DatabaseUtils.getCurrentTimestamp(db));
      }
      sql.append(") ");

      PreparedStatement pst = db.prepareStatement(sql.toString());
      int i = 0;
      if (id > -1) {
        pst.setInt(++i, id);
      }
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
      if (modified != null) {
        pst.setTimestamp(++i, this.getModified());
      }
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      pst.execute();
      if(pst != null){
        pst.close();
      }
      id = DatabaseUtils.getCurrVal(db, "scheduled_recipient_id_seq", id);
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
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int records = -1;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE scheduled_recipient " +
        " SET run_id = ?, status_id = ?, status = ?, status_date = ?, scheduled_date = ?, " +
        " sent_date = ?, reply_date = ?, bounce_date = ?, modified =  " + DatabaseUtils.getCurrentTimestamp(db) +
        " WHERE id = ? ");
    int i = 0;
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
    pst.setInt(++i, this.getId());
    records = pst.executeUpdate();
    pst.close();
    return records;
  }

  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }

    int recordCount = 0;
    boolean commit = true;
    Statement st = db.createStatement();

    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      recordCount = st.executeUpdate("DELETE FROM scheduled_recipient WHERE id = " + this.getId());
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.toString());
    } finally {
      if(st != null){
        st.close();
      }
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return (recordCount != 0);
  }

  /**
   *  Description of the Method
   *
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
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
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
  }

}

