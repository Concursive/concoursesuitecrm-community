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
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Represents details about a message received from a contact, by a centric
 * user
 *
 * @author Ananth
 * @created January 1, 2006
 */
public class ContactMessage extends GenericBean {
  private int id = -1;
  private int messageId = -1;
  private java.sql.Timestamp receivedDate = null;
  private int receivedFrom = -1;
  private int receivedBy = -1;
  //resources
  private Message contactMessage = null;
  private boolean buildMessage = false;


  /**
   * Gets the contactMessage attribute of the ContactMessage object
   *
   * @return The contactMessage value
   */
  public Message getContactMessage() {
    return contactMessage;
  }


  /**
   * Sets the contactMessage attribute of the ContactMessage object
   *
   * @param tmp The new contactMessage value
   */
  public void setContactMessage(Message tmp) {
    this.contactMessage = tmp;
  }


  /**
   * Gets the buildMessage attribute of the ContactMessage object
   *
   * @return The buildMessage value
   */
  public boolean getBuildMessage() {
    return buildMessage;
  }


  /**
   * Sets the buildMessage attribute of the ContactMessage object
   *
   * @param tmp The new buildMessage value
   */
  public void setBuildMessage(boolean tmp) {
    this.buildMessage = tmp;
  }


  /**
   * Sets the buildMessage attribute of the ContactMessage object
   *
   * @param tmp The new buildMessage value
   */
  public void setBuildMessage(String tmp) {
    this.buildMessage = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the id attribute of the ContactMessage object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the ContactMessage object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ContactMessage object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the messageId attribute of the ContactMessage object
   *
   * @return The messageId value
   */
  public int getMessageId() {
    return messageId;
  }


  /**
   * Sets the messageId attribute of the ContactMessage object
   *
   * @param tmp The new messageId value
   */
  public void setMessageId(int tmp) {
    this.messageId = tmp;
  }


  /**
   * Sets the messageId attribute of the ContactMessage object
   *
   * @param tmp The new messageId value
   */
  public void setMessageId(String tmp) {
    this.messageId = Integer.parseInt(tmp);
  }


  /**
   * Gets the receivedDate attribute of the ContactMessage object
   *
   * @return The receivedDate value
   */
  public java.sql.Timestamp getReceivedDate() {
    return receivedDate;
  }


  /**
   * Sets the receivedDate attribute of the ContactMessage object
   *
   * @param tmp The new receivedDate value
   */
  public void setReceivedDate(java.sql.Timestamp tmp) {
    this.receivedDate = tmp;
  }


  /**
   * Sets the receivedDate attribute of the ContactMessage object
   *
   * @param tmp The new receivedDate value
   */
  public void setReceivedDate(String tmp) {
    this.receivedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the receivedFrom attribute of the ContactMessage object
   *
   * @return The receivedFrom value
   */
  public int getReceivedFrom() {
    return receivedFrom;
  }


  /**
   * Sets the receivedFrom attribute of the ContactMessage object
   *
   * @param tmp The new receivedFrom value
   */
  public void setReceivedFrom(int tmp) {
    this.receivedFrom = tmp;
  }


  /**
   * Sets the receivedFrom attribute of the ContactMessage object
   *
   * @param tmp The new receivedFrom value
   */
  public void setReceivedFrom(String tmp) {
    this.receivedFrom = Integer.parseInt(tmp);
  }


  /**
   * Gets the receivedBy attribute of the ContactMessage object
   *
   * @return The receivedBy value
   */
  public int getReceivedBy() {
    return receivedBy;
  }


  /**
   * Sets the receivedBy attribute of the ContactMessage object
   *
   * @param tmp The new receivedBy value
   */
  public void setReceivedBy(int tmp) {
    this.receivedBy = tmp;
  }


  /**
   * Sets the receivedBy attribute of the ContactMessage object
   *
   * @param tmp The new receivedBy value
   */
  public void setReceivedBy(String tmp) {
    this.receivedBy = Integer.parseInt(tmp);
  }


  /**
   * Constructor for the ContactMessage object
   */
  public ContactMessage() { }


  /**
   * Constructor for the ContactMessage object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ContactMessage(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the ContactMessage object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ContactMessage(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Contact Message ID.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT cm.* " +
            "FROM contact_message cm " +
            "LEFT JOIN " + DatabaseUtils.addQuotes(db, "message")+ " m ON (cm.message_id = m.id) " +
            "LEFT JOIN contact ct_rf ON (cm.received_from = ct_rf.contact_id) " +
            "LEFT JOIN contact ct_rb ON (cm.received_by = ct_rb.user_id) " +
            "WHERE cm.id = ? ");
    pst.setInt(1, messageId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Contact Message not found.");
    }

    if (buildMessage) {
      this.buildMessage(db);
    }
  }

  /**
   * Gets the userIdParams attribute of the Task class
   *
   * @return The userIdParams value
   */
  public static ArrayList getUserIdParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("receivedBy");
    return thisList;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildMessage(Connection db) throws SQLException {
    if (messageId > -1) {
      contactMessage = new Message(db, messageId);
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //message table
    this.setId(rs.getInt("id"));
    messageId = rs.getInt("message_id");
    receivedDate = rs.getTimestamp("received_date");
    receivedFrom = rs.getInt("received_from");
    receivedBy = rs.getInt("received_by");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO contact_message (message_id, received_date, received_from, received_by) " +
            "VALUES (?, ?, ?, ? ) ");
    int i = 0;
    pst.setInt(++i, messageId);
    pst.setTimestamp(++i, receivedDate);
    pst.setInt(++i, receivedFrom);
    pst.setInt(++i, receivedBy);
    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "contact_message_id_seq", id);

    return true;
  }
}

