//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.sql.*;
import java.text.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    November 26, 2001
 *@version    $Id$
 */
public class Recipient extends GenericBean {

  private int id = -1;
  private int campaignId = -1;
  private int contactId = -1;
  private int runId = -1;
  private int statusId = -1;
  private String status = null;
  private java.sql.Timestamp statusDate = null;
  private java.sql.Timestamp scheduledDate = null;
  private java.sql.Timestamp sentDate = null;
  private java.sql.Timestamp replyDate = null;
  private java.sql.Timestamp bounceDate = null;
  private Contact contact = null;


  /**
   *  Constructor for the Recipient object
   *
   *@since
   */
  public Recipient() { }


  /**
   *  Constructor for the Recipient object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Recipient(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Recipient object
   *
   *@param  db                Description of Parameter
   *@param  recipientId       Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Recipient(Connection db, int recipientId) throws SQLException {

    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * FROM scheduled_recipient r " +
        "WHERE r.id > -1 ");

    if (recipientId > -1) {
      sql.append("AND r.id = " + recipientId + " ");
    } else {
      throw new SQLException("Recipient ID not specified.");
    }

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Recipient record not found.");
    }
    rs.close();
    st.close();
  }

  public void setId(int tmp) { this.id = tmp; }
  public void setCampaignId(int tmp) { this.campaignId = tmp; }
  public void setContactId(int tmp) { this.contactId = tmp; }
  public void setRunId(int tmp) { this.runId = tmp; }
  public void setStatusId(int tmp) { this.statusId = tmp; }
  public void setStatus(String tmp) { this.status = tmp; }
  public void setStatusDate(java.sql.Timestamp tmp) { this.statusDate = tmp; }
  public void setScheduledDate(java.sql.Timestamp tmp) { this.scheduledDate = tmp; }
  public void setSentDate(java.sql.Timestamp tmp) { this.sentDate = tmp; }
  public void setReplyDate(java.sql.Timestamp tmp) { this.replyDate = tmp; }
  public void setBounceDate(java.sql.Timestamp tmp) { this.bounceDate = tmp; }
  public void setContact(Contact tmp) { this.contact = tmp; }

  public int getId() { return id; }
  public int getCampaignId() { return campaignId; }
  public int getContactId() { return contactId; }
  public int getRunId() { return runId; }
  public int getStatusId() { return statusId; }
  public String getStatus() { return status; }
  public java.sql.Timestamp getStatusDate() { return statusDate; }
  public java.sql.Timestamp getScheduledDate() { return scheduledDate; }
  public java.sql.Timestamp getSentDate() { return sentDate; }
  public String getSentDateString() { 
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(sentDate);
    } catch (NullPointerException e) {
    }
    return ("");
  }
  public java.sql.Timestamp getReplyDate() { return replyDate; }
  public String getReplyDateString() { 
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(replyDate);
    } catch (NullPointerException e) {
    }
    return ("");
  }
  public java.sql.Timestamp getBounceDate() { return bounceDate; }
  public Contact getContact() { return contact; }


  protected boolean isValid(Connection db) throws SQLException {
    errors.clear();

    if (campaignId == -1) {
      errors.put("campaignError", "Campaign is required");
    }
    
    if (contactId == -1) {
      errors.put("contactError", "Contact is required");
    }

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }
  
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    campaignId = rs.getInt("campaign_id");
    contactId = rs.getInt("contact_id");
    runId = rs.getInt("run_id");
    statusId = rs.getInt("status_id");
    status = rs.getString("status");
    scheduledDate = rs.getTimestamp("scheduled_date");
    sentDate = rs.getTimestamp("sent_date");
    replyDate = rs.getTimestamp("reply_date");
    bounceDate = rs.getTimestamp("bounce_date");
  }

  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean insert(Connection db) throws SQLException {

    if (!isValid(db)) {
      return false;
    }

    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO scheduled_recipient " +
        "(campaign_id, contact_id) " +
        "VALUES (?, ?) ");

    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, campaignId);
    pst.setInt(++i, contactId);

    pst.execute();
    pst.close();

    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery("select currval('scheduled_recipient_id_seq')");
    if (rs.next()) {
      this.setId(rs.getInt(1));
    }
    rs.close();
    st.close();
    return true;
  }
  
  public int update(Connection db) throws SQLException {
    int resultCount = 0;

    if (id == -1) {
      throw new SQLException("Recipient ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE scheduled_recipient " +
        "SET run_id = ?, status_id = ?, status = ?, status_date = ?, " +
        "scheduled_date = ?, sent_date = ?, reply_date = ?, bounce_date = ? " +
        "WHERE id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, runId);
    pst.setInt(++i, statusId);
    pst.setString(++i, status);
    if (statusDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(++i, statusDate);
    }
    if (scheduledDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(++i, scheduledDate);
    }
    
    if (sentDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(++i, sentDate);
    }
    if (replyDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(++i, replyDate);
    }
    if (bounceDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(++i, bounceDate);
    }

    pst.setInt(++i, id);

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }
  
  public boolean delete(Connection db) throws SQLException {
    Statement st = db.createStatement();
    st.executeUpdate("DELETE FROM scheduled_recipient WHERE id = " + id);
    st.close();
    return true;
  }
  
  public void buildContact(Connection db) throws SQLException {
    contact = new Contact(db, "" + contactId);
  }

}

