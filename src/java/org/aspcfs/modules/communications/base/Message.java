//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Class
 *
 *@author     Wesley_S_Gillette
 *@created    November 13, 2001
 *@version    $Id$
 */
public class Message extends GenericBean {

  private int id = -1;
  private String name = "";
  private String description = "";
  private int templateId = -1;
  private String replyTo = "";
	private String messageSubject = "";
	private String messageText = "";
  private String url = "";
  private String image = "";
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private boolean enabled = true;


  /**
   *  Constructor for the Message object
   *
   *@since
   */
  public Message() { }


  /**
   *  Constructor for the Message object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Message(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Message object
   *
   *@param  db                Description of Parameter
   *@param  messageId         Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Message(Connection db, String messageId) throws SQLException {

    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
      "SELECT m.*  FROM message m " +
      "LEFT JOIN contact ct_eb ON (m.enteredby = ct_eb.user_id) " +
      "LEFT JOIN contact ct_mb ON (m.modifiedby = ct_mb.user_id) " +
      "WHERE m.id > -1 ");

    if (messageId != null && !messageId.equals("")) {
      sql.append("AND m.id = " + messageId + " ");
    } else {
      throw new SQLException("Message ID not specified.");
    }

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Message not found.");
    }
    rs.close();
    st.close();
  }
  
  public Message(Connection db, int messageId) throws SQLException {

    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
      "SELECT m.*  FROM message m " +
      "LEFT JOIN contact ct_eb ON (m.enteredby = ct_eb.user_id) " +
      "LEFT JOIN contact ct_mb ON (m.modifiedby = ct_mb.user_id) " +
      "WHERE m.id > -1 ");

    if (messageId > -1) {
      sql.append("AND m.id = " + messageId + " ");
    } else {
      throw new SQLException("Invalid Message ID.");
    }

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Message not found.");
    }
    rs.close();
    st.close();
  }



  /**
   *  Sets the id attribute of the Message object
   *
   *@param  tmp  The new id value
   *@since
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Message object
   *
   *@param  tmp  The new id value
   *@since
   */
  public void setId(String tmp) {
    this.setId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the name attribute of the Message object
   *
   *@param  tmp  The new name value
   *@since
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the description attribute of the Message object
   *
   *@param  tmp  The new description value
   *@since
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the templateId attribute of the Message object
   *
   *@param  tmp  The new templateId value
   *@since
   */
  public void setTemplateId(int tmp) {
    this.templateId = tmp;
  }


  /**
   *  Sets the templateId attribute of the Message object
   *
   *@param  tmp  The new templateId value
   *@since
   */
  public void setTemplateId(String tmp) {
    this.setTemplateId(Integer.parseInt(tmp));
  }

	public void setMessageSubject(String tmp) {
		this.messageSubject = tmp;
	}

  /**
   *  Sets the text attribute of the Message object
   *
   *@param  tmp  The new text value
   *@since
   */
  public void setMessageText(String tmp) {
    this.messageText = tmp;
  }


  /**
   *  Sets the replyTo attribute of the Message object
   *
   *@param  tmp  The new replyTo value
   *@since
   */
  public void setReplyTo(String tmp) {
    this.replyTo = tmp;
  }


  /**
   *  Sets the url attribute of the Message object
   *
   *@param  tmp  The new url value
   *@since
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the image attribute of the Message object
   *
   *@param  tmp  The new image value
   *@since
   */
  public void setImage(String tmp) {
    this.image = tmp;
  }

  public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
  public void setModified(String tmp) { 
    java.util.Date tmpDate = new java.util.Date();
    modified = new java.sql.Timestamp(tmpDate.getTime());
    modified = modified.valueOf(tmp);
  }
  public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }

  /**
   *  Sets the enteredBy attribute of the Message object
   *
   *@param  tmp  The new enteredBy value
   *@since
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Message object
   *
   *@param  tmp  The new modifiedBy value
   *@since
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the enabled attribute of the Message object
   *
   *@param  tmp  The new enabled value
   *@since
   */
  public void setEnabled(String tmp) {
    if (tmp.toLowerCase().equals("false")) {
      this.enabled = false;
    } else {
      this.enabled = true;
    }
  }


  /**
   *  Gets the id attribute of the Message object
   *
   *@return    The id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the name attribute of the Message object
   *
   *@return    The name value
   *@since
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the Message object
   *
   *@return    The description value
   *@since
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the templateId attribute of the Message object
   *
   *@return    The templateId value
   *@since
   */
  public int getTemplateId() {
    return templateId;
  }
	
	public String getMessageSubject() {
		return messageSubject;
	}


  /**
   *  Gets the text attribute of the Message object
   *
   *@return    The text value
   *@since
   */
  public String getMessageText() {
    return messageText;
  }


  /**
   *  Gets the replyTo attribute of the Message object
   *
   *@return    The replyTo value
   *@since
   */
  public String getReplyTo() {
    return replyTo;
  }


  /**
   *  Gets the url attribute of the Message object
   *
   *@return    The url value
   *@since
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the image attribute of the Message object
   *
   *@return    The image value
   *@since
   */
  public String getImage() {
    return image;
  }


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


  /**
   *  Gets the enteredBy attribute of the Message object
   *
   *@return    The enteredBy value
   *@since
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the Message object
   *
   *@return    The modifiedBy value
   *@since
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the enabled attribute of the Message object
   *
   *@return    The enabled value
   *@since
   */
  public boolean getEnabled() {
    return enabled;
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

    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO MESSAGE " +
          "(enteredby, modifiedby, name) " +
          "VALUES (?, ?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.setString(++i, this.getName());

      pst.execute();
      pst.close();

      Statement st = db.createStatement();
      //ResultSet rs = st.executeQuery();
      ResultSet rs = st.executeQuery("select currval('message_id_seq')");
      if (rs.next()) {
        this.setId(rs.getInt(1));
      }
      rs.close();
      st.close();

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


  // end insert

  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
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
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean delete(Connection db) throws SQLException {

    Statement st = db.createStatement();

    try {
      db.setAutoCommit(false);
      st.executeUpdate("DELETE FROM message WHERE id = " + this.getId());
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      System.out.println(e.toString());
    } finally {
      db.setAutoCommit(true);
      st.close();
    }
    return true;
  }


  /**
   *  Gets the valid attribute of the Message object
   *
   *@param  db                Description of Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected boolean isValid(Connection db) throws SQLException {
    errors.clear();

    if (name == null || name.trim().equals("")) {
      errors.put("nameError", "Message name is required");
    }
		
		if (messageSubject == null || messageSubject.trim().equals("")) {
      errors.put("messageSubjectError", "Message subject is required");
    }
		
		if (replyTo == null || replyTo.trim().equals("")) {
      errors.put("replyToError", "Email address is required");
    }

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
   *@since
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;

    if (this.getId() == -1) {
      throw new SQLException("Message ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE message " +
        "SET name=?, description = ?, template_id = ?, subject = ?, " +
				"body = ?, reply_addr = ?, url = ?, img = ?, " +
        "enabled = ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
        "WHERE id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setInt(++i, this.getTemplateId());
    pst.setString(++i, this.getMessageSubject());
		pst.setString(++i, this.getMessageText());
    pst.setString(++i, this.getReplyTo());
    pst.setString(++i, this.getUrl());
    pst.setString(++i, this.getImage());
    pst.setBoolean(++i, this.getEnabled());

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
    name = rs.getString("name");
    description = rs.getString("description");
    templateId = rs.getInt("template_id");
    messageSubject = rs.getString("subject");
		messageText = rs.getString("body");
    replyTo = rs.getString("reply_addr");
    url = rs.getString("url");
    image = rs.getString("img");
    enabled = rs.getBoolean("enabled");

    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");

    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }

}

