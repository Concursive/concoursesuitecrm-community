//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     chris price
 *@created    February 21, 2002
 *@version
 */
public class CFSNote extends GenericBean {

  public final static int CALL = 1;

  public final static int NEW = 0;
  public final static int READ = 1;
  public final static int OLD = 2;

  private int id = -1;
  private String subject = "";
  private String body = "";
  private int replyId = -1;
  private int sentTo = -1;
  private int status = 0;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp viewed = null;
  private java.sql.Timestamp sent = null;
  private boolean enabled = true;
  private String sentName = "";
  private int type = -1;

  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;


  /**
   *  Constructor for the CFSNote object
   */
  public CFSNote() { }


  /**
   *  Constructor for the CFSNote object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public CFSNote(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the CFSNote object
   *
   *@param  db                Description of Parameter
   *@param  noteId            Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public CFSNote(Connection db, String noteId) throws SQLException {

    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT m.*, ml.*, ct_sent.namefirst as sent_namefirst, ct_sent.namelast as sent_namelast " +
        "FROM cfsinbox_message m " +
        "LEFT JOIN contact ct_eb ON (m.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (m.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN contact ct_sent ON (m.enteredby = ct_sent.user_id) " +
        "LEFT JOIN cfsinbox_messagelink ml ON (m.id = ml.id) " +
        "WHERE m.id > -1 ");

    if (noteId != null && !noteId.equals("")) {
      sql.append("AND m.id = " + noteId + " ");
    } else {
      throw new SQLException("CFS Note ID not specified.");
    }

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("CFS Note not found.");
    }
    rs.close();
    st.close();
  }


  /**
   *  Constructor for the CFSNote object
   *
   *@param  db                Description of Parameter
   *@param  noteId            Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public CFSNote(Connection db, int noteId) throws SQLException {

    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT m.*, ml.*, ct_sent.namefirst as sent_namefirst, ct_sent.namelast as sent_namelast " +
        "FROM cfsinbox_message m " +
        "LEFT JOIN contact ct_eb ON (m.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (m.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN contact ct_sent ON (m.enteredby = ct_sent.user_id) " +
        "LEFT JOIN cfsinbox_messagelink ml ON (m.id = ml.id) " +
        "WHERE m.id > -1 ");

    if (noteId > -1) {
      sql.append("AND m.id = " + noteId + " ");
    } else {
      throw new SQLException("Invalid CFS Note ID.");
    }

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("CFS Note not found.");
    }
    rs.close();
    st.close();
  }


  /**
   *  Sets the sentName attribute of the CFSNote object
   *
   *@param  sentName  The new sentName value
   */
  public void setSentName(String sentName) {
    this.sentName = sentName;
  }


  /**
   *  Sets the type attribute of the CFSNote object
   *
   *@param  type  The new type value
   */
  public void setType(int type) {
    this.type = type;
  }


  /**
   *  Sets the type attribute of the CFSNote object
   *
   *@param  type  The new type value
   */
  public void setType(String type) {
    this.type = Integer.parseInt(type);
  }


  /**
   *  Sets the modified attribute of the CFSNote object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the CFSNote object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    java.util.Date tmpDate = new java.util.Date();
    modified = new java.sql.Timestamp(tmpDate.getTime());
    modified = modified.valueOf(tmp);
  }


  /**
   *  Sets the entered attribute of the CFSNote object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the id attribute of the CFSNote object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the subject attribute of the CFSNote object
   *
   *@param  tmp  The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   *  Sets the body attribute of the CFSNote object
   *
   *@param  tmp  The new body value
   */
  public void setBody(String tmp) {
    this.body = tmp;
  }


  /**
   *  Sets the replyId attribute of the CFSNote object
   *
   *@param  tmp  The new replyId value
   */
  public void setReplyId(int tmp) {
    this.replyId = tmp;
  }


  /**
   *  Sets the status attribute of the CFSNote object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(int tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the CFSNote object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the CFSNote object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the viewed attribute of the CFSNote object
   *
   *@param  tmp  The new viewed value
   */
  public void setViewed(java.sql.Timestamp tmp) {
    this.viewed = tmp;
  }


  /**
   *  Sets the sent attribute of the CFSNote object
   *
   *@param  tmp  The new sent value
   */
  public void setSent(java.sql.Timestamp tmp) {
    this.sent = tmp;
  }


  /**
   *  Sets the enabled attribute of the CFSNote object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the sentTo attribute of the CFSNote object
   *
   *@param  sentTo  The new sentTo value
   */
  public void setSentTo(int sentTo) {
    this.sentTo = sentTo;
  }


  /**
   *  Sets the sentTo attribute of the CFSNote object
   *
   *@param  sentTo  The new sentTo value
   */
  public void setSentTo(String sentTo) {
    this.sentTo = Integer.parseInt(sentTo);
  }


  /**
   *  Gets the sentName attribute of the CFSNote object
   *
   *@return    The sentName value
   */
  public String getSentName() {
    return sentName;
  }


  /**
   *  Gets the statusText attribute of the CFSNote object
   *
   *@return    The statusText value
   */
  public String getStatusText() {
    if (status == 0) {
      return ("Unread");
    } else if (status == 1) {
      return ("Read");
    } else {
      return ("Archived");
    }
  }


  /**
   *  Gets the type attribute of the CFSNote object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the entered attribute of the CFSNote object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredString attribute of the CFSNote object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the enteredDateTimeString attribute of the CFSNote object
   *
   *@return    The enteredDateTimeString value
   */
  public String getEnteredDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the id attribute of the CFSNote object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the subject attribute of the CFSNote object
   *
   *@return    The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   *  Gets the body attribute of the CFSNote object
   *
   *@return    The body value
   */
  public String getBody() {
    return body;
  }


  /**
   *  Gets the replyId attribute of the CFSNote object
   *
   *@return    The replyId value
   */
  public int getReplyId() {
    return replyId;
  }


  /**
   *  Gets the status attribute of the CFSNote object
   *
   *@return    The status value
   */
  public int getStatus() {
    return status;
  }


  /**
   *  Gets the enteredBy attribute of the CFSNote object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the CFSNote object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the viewed attribute of the CFSNote object
   *
   *@return    The viewed value
   */
  public java.sql.Timestamp getViewed() {
    return viewed;
  }


  /**
   *  Gets the sent attribute of the CFSNote object
   *
   *@return    The sent value
   */
  public java.sql.Timestamp getSent() {
    return sent;
  }


  /**
   *  Gets the enabled attribute of the CFSNote object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the sentTo attribute of the CFSNote object
   *
   *@return    The sentTo value
   */
  public int getSentTo() {
    return sentTo;
  }


  /**
   *  Gets the modified attribute of the CFSNote object
   *
   *@return    The modified value
   */
  public String getModified() {
    return modified.toString();
  }


  /**
   *  Gets the modifiedString attribute of the CFSNote object
   *
   *@return    The modifiedString value
   */
  public String getModifiedString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the modifiedDateTimeString attribute of the CFSNote object
   *
   *@return    The modifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {

    if (!isValid(db)) {
      return false;
    }

    StringBuffer sql = new StringBuffer();

    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO cfsinbox_message " +
          "(enteredby, modifiedby, body, reply_id, type) " +
          "VALUES (?, ?, ?, ?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.setString(++i, this.getBody());
      pst.setInt(++i, this.getReplyId());
      pst.setInt(++i, this.getType());
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "cfsinbox_message_id_seq");
      this.insertLink(db);
      this.update(db, true);
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
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insertLink(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();

    try {
      sql.append(
          "INSERT INTO cfsinbox_messagelink " +
          "(id, sent_to, sent_from) " +
          "VALUES (?, ?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getId());
      pst.setInt(++i, this.getSentTo());
      pst.setInt(++i, this.getEnteredBy());

      pst.execute();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    } finally {
    }

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;

    if (!isValid(db)) {
      return -1;
    }

    try {
      db.setAutoCommit(false);
      resultCount = this.update(db, false);
      db.commit();
    } catch (Exception e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }

    db.setAutoCommit(true);
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  myId              Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean delete(Connection db, int myId) throws SQLException {

    try {
      db.setAutoCommit(false);
      Statement st = db.createStatement();
      st.executeUpdate(
        "DELETE FROM cfsinbox_messagelink " +
        "WHERE id = " + this.getId() + " " +
        "AND sent_to = " + myId + " ");
      st.executeUpdate(
        "DELETE FROM cfsinbox_message " +
        "WHERE id = " + this.getId() + " ");
      st.close();
    } catch (SQLException e) {
      db.rollback();
      System.out.println(e.toString());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int updateStatus(Connection db) throws SQLException {
    int resultCount = 0;

    if (this.getId() == -1) {
      throw new SQLException("CFS Note ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE cfsinbox_messagelink " +
        "SET status = ?, viewed = CURRENT_TIMESTAMP " +
        "WHERE id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getStatus());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Gets the valid attribute of the CFSNote object
   *
   *@param  db                Description of Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    errors.clear();

    if (body == null || body.trim().equals("")) {
      errors.put("bodyError", "Body is required");
    }

    if (subject == null || subject.trim().equals("")) {
      errors.put("messageSubjectError", "Subject is required");
    }

    //if (replyTo == null || replyTo.trim().equals("")) {
    //  errors.put("replyToError", "Email address is required");
    //}

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  override          Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;

    if (this.getId() == -1) {
      throw new SQLException("CFS Note ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE cfsinbox_message " +
        "SET subject = ?, body = ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
        "WHERE id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getSubject());
    pst.setString(++i, this.getBody());

    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    if (!override) {
      pst.setTimestamp(++i, modified);
    }

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //cfsinbox_message table
    this.setId(rs.getInt("id"));
    subject = rs.getString("subject");
    body = rs.getString("body");
    replyId = rs.getInt("reply_id");
    enteredBy = rs.getInt("enteredby");
    sent = rs.getTimestamp("sent");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
    type = rs.getInt("type");
    modifiedBy = rs.getInt("modifiedby");
    
    //cfsinbox_messagelink table
    sentTo = rs.getInt("sent_to");
    status = rs.getInt("status");
    viewed = rs.getTimestamp("viewed");

    //contact table
    sentName = rs.getString("sent_namefirst") + " " + rs.getString("sent_namelast");
  }

}

