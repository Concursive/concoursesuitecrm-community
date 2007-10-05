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

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

/**
 *  Description of the Class
 *
 * @author     mrajkowski
 * @created    November 26, 2001
 * @version    $Id: Recipient.java 12404 2005-08-05 13:37:07 -0400 (Fri, 05 Aug
 *      2005) mrajkowski $
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
  private boolean allowDuplicates = false;


  /**
   *  Constructor for the Recipient object
   */
  public Recipient() { }


  /**
   *  Constructor for the Recipient object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Recipient(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Recipient object
   *
   * @param  db                Description of Parameter
   * @param  recipientId       Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Recipient(Connection db, int recipientId) throws SQLException {
    if (recipientId == -1) {
      throw new SQLException("Recipient ID not specified.");
    }

    String sql =
        "SELECT * " +
        "FROM scheduled_recipient r " +
        "WHERE r.id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, recipientId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Recipient record not found.");
    }
  }


  /**
   *  Sets the id attribute of the Recipient object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the campaignId attribute of the Recipient object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Sets the contactId attribute of the Recipient object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the contactId attribute of the Recipient object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the runId attribute of the Recipient object
   *
   * @param  tmp  The new runId value
   */
  public void setRunId(int tmp) {
    this.runId = tmp;
  }


  /**
   *  Sets the statusId attribute of the Recipient object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the status attribute of the Recipient object
   *
   * @param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the statusDate attribute of the Recipient object
   *
   * @param  tmp  The new statusDate value
   */
  public void setStatusDate(java.sql.Timestamp tmp) {
    this.statusDate = tmp;
  }


  /**
   *  Sets the scheduledDate attribute of the Recipient object
   *
   * @param  tmp  The new scheduledDate value
   */
  public void setScheduledDate(java.sql.Timestamp tmp) {
    this.scheduledDate = tmp;
  }


  /**
   *  Sets the sentDate attribute of the Recipient object
   *
   * @param  tmp  The new sentDate value
   */
  public void setSentDate(java.sql.Timestamp tmp) {
    this.sentDate = tmp;
  }


  /**
   *  Sets the replyDate attribute of the Recipient object
   *
   * @param  tmp  The new replyDate value
   */
  public void setReplyDate(java.sql.Timestamp tmp) {
    this.replyDate = tmp;
  }


  /**
   *  Sets the bounceDate attribute of the Recipient object
   *
   * @param  tmp  The new bounceDate value
   */
  public void setBounceDate(java.sql.Timestamp tmp) {
    this.bounceDate = tmp;
  }


  /**
   *  Sets the contact attribute of the Recipient object
   *
   * @param  tmp  The new contact value
   */
  public void setContact(Contact tmp) {
    this.contact = tmp;
  }


  /**
   *  Gets the id attribute of the Recipient object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the campaignId attribute of the Recipient object
   *
   * @return    The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   *  Gets the contactId attribute of the Recipient object
   *
   * @return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the runId attribute of the Recipient object
   *
   * @return    The runId value
   */
  public int getRunId() {
    return runId;
  }


  /**
   *  Gets the statusId attribute of the Recipient object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the status attribute of the Recipient object
   *
   * @return    The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   *  Gets the statusDate attribute of the Recipient object
   *
   * @return    The statusDate value
   */
  public java.sql.Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   *  Gets the scheduledDate attribute of the Recipient object
   *
   * @return    The scheduledDate value
   */
  public java.sql.Timestamp getScheduledDate() {
    return scheduledDate;
  }


  /**
   *  Gets the sentDate attribute of the Recipient object
   *
   * @return    The sentDate value
   */
  public java.sql.Timestamp getSentDate() {
    return sentDate;
  }


  /**
   *  Gets the sentDateString attribute of the Recipient object
   *
   * @return    The sentDateString value
   */
  public String getSentDateString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(sentDate);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the replyDate attribute of the Recipient object
   *
   * @return    The replyDate value
   */
  public java.sql.Timestamp getReplyDate() {
    return replyDate;
  }


  /**
   *  Gets the replyDateString attribute of the Recipient object
   *
   * @return    The replyDateString value
   */
  public String getReplyDateString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(replyDate);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the bounceDate attribute of the Recipient object
   *
   * @return    The bounceDate value
   */
  public java.sql.Timestamp getBounceDate() {
    return bounceDate;
  }


  /**
   *  Gets the contact attribute of the Recipient object
   *
   * @return    The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   *  Gets the allowDuplicates attribute of the Recipient object
   *
   * @return    The allowDuplicates value
   */
  public boolean getAllowDuplicates() {
    return allowDuplicates;
  }


  /**
   *  Sets the allowDuplicates attribute of the Recipient object
   *
   * @param  tmp  The new allowDuplicates value
   */
  public void setAllowDuplicates(boolean tmp) {
    this.allowDuplicates = tmp;
  }


  /**
   *  Sets the allowDuplicates attribute of the Recipient object
   *
   * @param  tmp  The new allowDuplicates value
   */
  public void setAllowDuplicates(String tmp) {
    this.allowDuplicates = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the duplicate attribute of the Recipient object
   *
   * @param  db             Description of the Parameter
   * @return                The duplicate value
   * @throws  SQLException  Description of the Exception
   */
  protected boolean isDuplicate(Connection db) throws SQLException {
    if (allowDuplicates) {
      return false;
    }
    boolean result = false;
    String sql =
        "SELECT * " +
        "FROM scheduled_recipient " +
        "WHERE campaign_id = ? AND contact_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, campaignId);
    pst.setInt(++i, contactId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = true;
    }
    rs.close();
    pst.close();
    return result;
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
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (isDuplicate(db)) {
      return false;
    }
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "scheduled_recipient_id_seq");
    sql.append(
        "INSERT INTO scheduled_recipient " +
        "(" + (id > -1 ? "id, " : "") + "campaign_id, contact_id) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, campaignId);
    pst.setInt(++i, contactId);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "scheduled_recipient_id_seq", id);
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("Recipient ID was not specified");
    }

    int resultCount = 0;
    PreparedStatement pst = null;
    String sql =
        "UPDATE scheduled_recipient " +
        "SET run_id = ?, status_id = ?, status = ?, status_date = ?, " +
        "scheduled_date = ?, sent_date = ?, reply_date = ?, bounce_date = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE id = ? ";
    int i = 0;
    pst = db.prepareStatement(sql);
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


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM scheduled_recipient WHERE id = ?");
    pst.setInt(1, id);
    pst.executeUpdate();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildContact(Connection db) throws SQLException {
    contact = new Contact();
    contact.setId(contactId);
    contact.setBuildDetails(true);
    contact.build(db);
  }

}

