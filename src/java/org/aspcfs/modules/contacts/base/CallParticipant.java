/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.contacts.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

/**
 * Description of the Class
 *
 */
public class CallParticipant extends GenericBean {

  //static variables
  //PENDING :followup due of a completed activity
  public final static int TENTATIVE = 1;
  public final static int ACCEPT = 2;
  public final static int REFUSE = 3;
  
  public final static int LOGGED_CALL = 0;
  public final static int SCHEDULED_CALL = 1;

  private int participantId = -1;
  private int callId = -1;
  private int contactId = -1;
  private int isAvailable = 1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private int isFollowup = 0;

  private String firstName = null;
  private String lastName = null;
  private String eMail = null;
  
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;

  /**
   * Constructor for the CallParticipant object
   */
  public CallParticipant() {
  }


  /**
   * Constructor for the CallParticipant object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of Exception
   */
  public CallParticipant(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the CallParticipant object
   *
   * @param db     Description of Parameter
   * @param participantId Description of Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of Exception
   */
  public CallParticipant(Connection db, String participantId) throws SQLException {
    queryRecord(db, Integer.parseInt(participantId));
  }


  /**
   * Constructor for the CallParticipant object
   *
   * @param db     Description of the Parameter
   * @param participantId Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public CallParticipant(Connection db, int participantId) throws SQLException {
    queryRecord(db, participantId);
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param participantId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int participantId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT participant_id, call_id, contact_id, is_available, entered, modified, enteredby, modifiedby, is_followup, " +
        "ct.namelast as namelast, ct.namefirst as namefirst, ctea.email " +
            "FROM call_log_participant c " +
            "LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
            "LEFT JOIN contact_emailaddress ctea ON (ct.contact_id = ctea.contact_id) ");
    if (participantId!=-1)
    {
      sql.append("where participant_id = "+participantId);
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (participantId == -1) {
      throw new SQLException("Participant record not found.");
    }
  }


  /**
   * Sets the Id attribute of the CallParticipant object
   *
   * @param tmp The new Id value
   */
  public void setParticipantId(String tmp) {
    try {
      this.participantId = Integer.parseInt(tmp);
    } catch (Exception e) {
    }
  }


  /**
   * Sets the entered attribute of the CallParticipant object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the modified attribute of the CallParticipant object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the entered attribute of the CallParticipant object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the modified attribute of the CallParticipant object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


   /**
   * Sets the ContactId attribute of the CallParticipant object
   *
   * @param tmp The new ContactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the ContactId attribute of the CallParticipant object
   *
   * @param tmp The new ContactId value
   */
  public void setContactId(String tmp) {
    try {
      this.contactId = Integer.parseInt(tmp);
    } catch (Exception e) {
    }
  }

  /**
   * Sets the EnteredBy attribute of the CallParticipant object
   *
   * @param tmp The new EnteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the CallParticipant object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the ModifiedBy attribute of the CallParticipant object
   *
   * @param tmp The new ModifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the CallParticipant object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }



  /**
   * Gets the modified attribute of the CallParticipant object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedString attribute of the CallParticipant object
   *
   * @return The modifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the enteredString attribute of the CallParticipant object
   *
   * @return The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Gets the Id attribute of the CallParticipant object
   *
   * @return The Id value
   */
  public int getParticipantId() {
    return participantId;
  }


  /**
   * Gets the idString attribute of the CallParticipant object
   *
   * @return The idString value
   */
  public String getParticipantIdString() {
    return String.valueOf(participantId);
  }


 
  /**
   * Gets the ContactId attribute of the CallParticipant object
   *
   * @return The ContactId value
   */
  public int getContactId() {
    return contactId;
  }

  /**
   * Gets the EnteredBy attribute of the CallParticipant object
   *
   * @return The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the ModifiedBy attribute of the CallParticipant object
   *
   * @return The ModifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      StringBuffer sql = new StringBuffer();
      participantId = DatabaseUtils.getNextSeq(db, "call_log_participant_participant_id_seq");
      sql.append(
          "INSERT INTO call_log_participant " +
              "(call_id, contact_id, is_available, is_followup, ");
      if (participantId > -1) {
        sql.append("participant_id, ");
      }
      sql.append("entered, modified, ");
      sql.append("enteredBy, modifiedBy ) ");
      sql.append(
          "VALUES (?, ?, ?, ?, ");
      if (participantId > -1) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      if (modified != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, callId);
      DatabaseUtils.setInt(pst, ++i, contactId);
      DatabaseUtils.setInt(pst, ++i, isAvailable);
      DatabaseUtils.setInt(pst, ++i, isFollowup);
     
      if (participantId > -1) {
        pst.setInt(++i, participantId);
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();
      participantId = DatabaseUtils.getCurrVal(db, "call_log_participant_participant_id_seq", participantId);

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
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getParticipantId() == -1) {
      throw new SQLException("ID was not specified");
    }

    int recordCount = 0;
   
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM call_log_participant " +
            "WHERE participant_id = ? ");
    pst.setInt(1, participantId);
    recordCount = pst.executeUpdate();
    pst.close();
    if (recordCount == 0) {
      return false;
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of Parameter
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    if (this.getParticipantId() == -1) {
      throw new SQLException("Participant ID was not specified");
    }
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE call_log_participant " +
            "SET  " +
            "call_id = ?, " +
            "contact_id = ?, " +
            "isAvailiable = ?, " +
            "isFollowup = ?,  " +
            "modifiedby = ?, " +
            "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "WHERE participant_id = ? " +
            "AND modified " + ((this.getModified() == null) ? "IS NULL " : "= ? "));
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    
    pst.setInt(++i, callId);
    pst.setInt(++i, contactId);
    pst.setInt(++i, isAvailable);
    pst.setInt(++i, isFollowup);
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getParticipantId());
    if (this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


 


  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
	participantId = DatabaseUtils.getInt(rs, "participant_id");
	callId = DatabaseUtils.getInt(rs, "call_id");
	contactId = DatabaseUtils.getInt(rs, "contact_id");
	isAvailable = rs.getInt("is_available");
	entered = rs.getTimestamp("entered");
	modified = rs.getTimestamp("modified");
	enteredBy = rs.getInt("enteredby");
	modifiedBy = rs.getInt("modifiedby");
  isFollowup = rs.getInt("is_followup");
	lastName = rs.getString("namelast");
	firstName = rs.getString("namefirst");
	eMail = rs.getString("email");
  }

  /**
   * Gets the callId attribute of the CallParticipant object
   *
   * @return callId The callId value
   */
  public int getCallId() {
    return this.callId;
  }

  /**
   * Sets the callId attribute of the CallParticipant object
   *
   * @param callId The new callId value
   */
  public void setCallId(int callId) {
    this.callId = callId;
  }

  /**
   * Sets the callId attribute of the CallParticipant object
   *
   * @param callId The new callId value
   */
  public void setCallId(String callId) {
    this.callId = Integer.parseInt(callId);
  }

  /**
   * Gets the isAvailable attribute of the CallParticipant object
   *
   * @return isAvailable The isAvailable value
   */
  public int getIsAvailable() {
    return this.isAvailable;
  }


  /**
   * Sets the isAvailable attribute of the CallParticipant object
   *
   * @param isAvailable The new isAvailable value
   */
  public void setIsAvailable(int isAvailable) {
    this.isAvailable = isAvailable;
  }

  /**
   * Sets the isAvailable attribute of the CallParticipant object
   *
   * @param isAvailable The new isAvailable value
   */
  public void setIsAvailable(String isAvailable) {
    this.isAvailable = Integer.parseInt(isAvailable);
  }

  /**
   * Gets the isAvailable attribute of the CallParticipant object
   *
   * @return isAvailable The isAvailable value
   */
  public int getIsFollowup() {
    return this.isFollowup;
  }


  /**
   * Sets the isAvailable attribute of the CallParticipant object
   *
   * @param temp The new isFollowup value
   */
  public void setIsFollowup(int temp) {
    this.isFollowup = temp;
  }

  /**
   * Sets the isAvailable attribute of the CallParticipant object
   *
   * @param temp The new isFollowup value
   */
  public void setIsFollowup(String temp) {
    this.isFollowup = Integer.parseInt(temp);
  }

  /**
   * Gets the entered attribute of the CallParticipant object
   *
   * @return entered The entered value
   */
  public java.sql.Timestamp getEntered() {
    return this.entered;
  }


  /**
   * Sets the participantId attribute of the CallParticipant object
   *
   * @param participantId The new participantId value
   */
  public void setParticipantId(int participantId) {
    this.participantId = participantId;
  }


  /**
   * Gets the nameFirstLast attribute of the Contact class
   *
   * @return The nameFirstLast value
   */
  public String getNameFirstLast() {
    return Contact.getNameFirstLast(firstName, lastName);
  }
  
  /**
   * Combines the first and last name of a contact, depending on the length of
   * the strings
   *
   * @return The nameLastFirst value
   */
  public String getNameLastFirst() {
    return Contact.getNameLastFirst(lastName, firstName);
  }
  
  /**
   * Gets the firstName attribute of the CallParticipant object
   *
   * @return firstName The firstName value
   */
  public String getFirstName() {
    return this.firstName;
  }


  /**
   * Sets the firstName attribute of the CallParticipant object
   *
   * @param firstName The new firstName value
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  /**
   * Gets the lastName attribute of the CallParticipant object
   *
   * @return lastName The lastName value
   */
  public String getLastName() {
    return this.lastName;
  }


  /**
   * Sets the lastName attribute of the CallParticipant object
   *
   * @param lastName The new lastName value
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


	public String getEMail() {
		return eMail;
	}


	public void setEMail(String mail) {
		eMail = mail;
	}
}
