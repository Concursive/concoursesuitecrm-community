//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class CFSNote extends GenericBean {

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
  
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;

 


  public CFSNote() { }

  public CFSNote(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

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
  public String getSentName() {
	return sentName;
}
public void setSentName(String sentName) {
	this.sentName = sentName;
}

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


  public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
  public void setModified(String tmp) { 
    java.util.Date tmpDate = new java.util.Date();
    modified = new java.sql.Timestamp(tmpDate.getTime());
    modified = modified.valueOf(tmp);
  }
  public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }

  public java.sql.Timestamp getEntered() { return entered; }
  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }
  public String getEnteredDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }
  public int getId() { return id; }
public String getSubject() { return subject; }
public String getBody() { return body; }
public int getReplyId() { return replyId; }
public int getStatus() { return status; }
public int getEnteredBy() { return enteredBy; }
public int getModifiedBy() { return modifiedBy; }
public java.sql.Timestamp getViewed() { return viewed; }
public java.sql.Timestamp getSent() { return sent; }
public boolean getEnabled() { return enabled; }
public void setId(int tmp) { this.id = tmp; }
public void setSubject(String tmp) { this.subject = tmp; }
public void setBody(String tmp) { this.body = tmp; }
public void setReplyId(int tmp) { this.replyId = tmp; }
public void setStatus(int tmp) { this.status = tmp; }
public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
public void setViewed(java.sql.Timestamp tmp) { this.viewed = tmp; }
public void setSent(java.sql.Timestamp tmp) { this.sent = tmp; }
public void setEnabled(boolean tmp) { this.enabled = tmp; }

public int getSentTo() {
	return sentTo;
}
public void setSentTo(int sentTo) {
	this.sentTo = sentTo;
}

  public String getModified() { 
    return modified.toString();
  }
  public String getModifiedString() { 
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }
  public String getModifiedDateTimeString() { 
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }

  public boolean insert(Connection db) throws SQLException {

    if (!isValid(db)) {
      return false;
    }

    StringBuffer sql = new StringBuffer();

    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO cfsinbox_message " +
          "(enteredby, modifiedby, body, reply_id) " +
          "VALUES (?, ?, ?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.setString(++i, this.getBody());
      pst.setInt(++i, this.getReplyId());
      
      pst.execute();
      pst.close();

      Statement st = db.createStatement();
      ResultSet rs = st.executeQuery("select currval('cfsinbox_message_id_seq')");
      if (rs.next()) {
        this.setId(rs.getInt(1));
      }
      rs.close();
      st.close();

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

  public boolean delete(Connection db, int myId) throws SQLException {

    Statement st = db.createStatement();
    Statement newSt = db.createStatement();

    try {
      db.setAutoCommit(false);
      st.executeUpdate("DELETE FROM cfsinbox_messagelink WHERE id = " + this.getId() + " AND sent_to = " + myId + " ");
      db.commit();
      
      ResultSet rs = newSt.executeQuery("select sent_to FROM cfsinbox_messagelink where id = " + this.getId() + " ");
      if (!(rs.next())) {
	db.setAutoCommit(false);
      	newSt.executeUpdate("DELETE FROM cfsinbox_message WHERE id = " + this.getId() + " ");
	db.commit();
      }
      rs.close();
      
    } catch (SQLException e) {
      db.rollback();
      System.out.println(e.toString());
    } finally {
      db.setAutoCommit(true);
      newSt.close();
      st.close();
    }
    return true;
  }

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
    this.setId(rs.getInt("id"));
    subject = rs.getString("subject");
    body = rs.getString("body");
    replyId = rs.getInt("reply_id");
    status = rs.getInt("status");
    sentName = rs.getString("sent_namefirst") + " " + rs.getString("sent_namelast");
    
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    sentTo = rs.getInt("sent_to");
    viewed = rs.getTimestamp("viewed");
    sent = rs.getTimestamp("sent");
    
  }

}

