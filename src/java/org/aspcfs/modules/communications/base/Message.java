//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.utils.StringUtils;
import com.darkhorseventures.utils.DateUtils;

/**
 *  Represents an HTML message than can be emailed, faxed, or printed. Messages
 *  are intended to be used with Campaigns.
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
   *  Description of the Field
   */
  public final static String tableName = "message";
  /**
   *  Description of the Field
   */
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  /**
   *  Constructor for the Message object
   */
  public Message() { }


  /**
   *  Constructor for the Message object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
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
   */
  public Message(Connection db, String messageId) throws SQLException {
    queryRecord(db, Integer.parseInt(messageId));
  }


  /**
   *  Constructor for the Message object
   *
   *@param  db                Description of Parameter
   *@param  messageId         Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public Message(Connection db, int messageId) throws SQLException {
    queryRecord(db, messageId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  messageId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int messageId) throws SQLException {
    if (messageId == -1) {
      throw new SQLException("Invalid Message ID.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT m.* " +
        "FROM message m " +
        "LEFT JOIN contact ct_eb ON (m.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (m.modifiedby = ct_mb.user_id) " +
        "WHERE m.id = ? ");
    pst.setInt(1, messageId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Message not found.");
    }
  }



  /**
   *  Sets the id attribute of the Message object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Message object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.setId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the name attribute of the Message object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the tableName attribute of the Message object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the Message object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the Message object
   *
   *@return    The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the Message object
   *
   *@return    The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the Message object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Sets the lastAnchor attribute of the Message object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the Message object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the syncType attribute of the Message object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the description attribute of the Message object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the templateId attribute of the Message object
   *
   *@param  tmp  The new templateId value
   */
  public void setTemplateId(int tmp) {
    this.templateId = tmp;
  }


  /**
   *  Sets the templateId attribute of the Message object
   *
   *@param  tmp  The new templateId value
   */
  public void setTemplateId(String tmp) {
    this.setTemplateId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the messageSubject attribute of the Message object
   *
   *@param  tmp  The new messageSubject value
   */
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
   *  Gets the modified attribute of the Message object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Sets the replyTo attribute of the Message object
   *
   *@param  tmp  The new replyTo value
   */
  public void setReplyTo(String tmp) {
    this.replyTo = tmp;
  }


  /**
   *  Sets the url attribute of the Message object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the image attribute of the Message object
   *
   *@param  tmp  The new image value
   */
  public void setImage(String tmp) {
    this.image = tmp;
  }


  /**
   *  Sets the modified attribute of the Message object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Message object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Message object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the entered attribute of the Message object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Message object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Message object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Message object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Message object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the Message object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Gets the id attribute of the Message object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the name attribute of the Message object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the Message object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the templateId attribute of the Message object
   *
   *@return    The templateId value
   */
  public int getTemplateId() {
    return templateId;
  }


  /**
   *  Gets the messageSubject attribute of the Message object
   *
   *@return    The messageSubject value
   */
  public String getMessageSubject() {
    return messageSubject;
  }


  /**
   *  Gets the text attribute of the Message object
   *
   *@return    The text value
   */
  public String getMessageText() {
    return messageText;
  }


  /**
   *  Gets the replyTo attribute of the Message object
   *
   *@return    The replyTo value
   */
  public String getReplyTo() {
    return replyTo;
  }


  /**
   *  Gets the url attribute of the Message object
   *
   *@return    The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the image attribute of the Message object
   *
   *@return    The image value
   */
  public String getImage() {
    return image;
  }


  /**
   *  Gets the entered attribute of the Message object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredString attribute of the Message object
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
   *  Gets the enteredDateTimeString attribute of the Message object
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
   *  Gets the modifiedString attribute of the Message object
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
   *  Gets the modifiedDateTimeString attribute of the Message object
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
   *  Gets the enteredBy attribute of the Message object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the Message object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the enabled attribute of the Message object
   *
   *@return    The enabled value
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
          "(name, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, this.getName());

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

      id = DatabaseUtils.getCurrVal(db, "message_id_seq");

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
    boolean commit = true;
    Statement st = null;
    ResultSet rs = null;

    try {
      commit = db.getAutoCommit();

      //Check to see if a message is being used by any unfinished campaigns
      //If so, the message can't be deleted
      int inactiveCount = 0;
      st = db.createStatement();
      rs = st.executeQuery(
          "SELECT COUNT(*) AS message_count " +
          "FROM campaign " +
          "WHERE message_id = " + this.getId() + " " +
          "AND status_id <> " + Campaign.FINISHED);
      rs.next();
      inactiveCount = rs.getInt("message_count");
      rs.close();
      if (inactiveCount > 0) {
        st.close();
        errors.put("actionError", "Message could not be deleted because " +
            inactiveCount + " " +
            (inactiveCount == 1 ? "campaign is" : "campaigns are") +
            " being built that " +
            (inactiveCount == 1 ? "uses" : "use") +
            " this message.");
        return false;
      }

      //If not, then the message can be deleted because a copy is made for
      //activated campaigns
      if (commit) {
        db.setAutoCommit(false);
      }
      st.executeUpdate("DELETE FROM message WHERE id = " + this.getId());
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.toString());
    } finally {
      db.setAutoCommit(true);
      st.close();
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    ResultSet rs = null;
    String sql = "";
    DependencyList dependencyList = new DependencyList();
    try {
      db.setAutoCommit(false);
      sql = "SELECT COUNT(*) AS message_count " +
          "FROM campaign " +
          "WHERE message_id = ? " +
          "AND status_id <> " + Campaign.FINISHED + " ";

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int msgcount = rs.getInt("message_count");
        if (msgcount != 0) {
            Dependency thisDependency = new Dependency();
            thisDependency.setName("Campaigns");
            thisDependency.setCount(msgcount);
            thisDependency.setCanDelete(true);
            dependencyList.add(thisDependency);
        }
      }

      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return dependencyList;
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

    if (replyTo == null || replyTo.trim().equals("") ||
        replyTo.indexOf("@") == -1 || replyTo.indexOf("@") == replyTo.length() - 1) {
      errors.put("replyToError", "Full email address is required");
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
        "enabled = ?, ");

    if (override == false) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append("modifiedby = ? " +
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
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //message table
    this.setId(rs.getInt("id"));
    name = rs.getString("name");
    description = rs.getString("description");
    templateId = rs.getInt("template_id");
    messageSubject = rs.getString("subject");

    //when importing data, somehow several messages had null subjects
    if (rs.wasNull()) {
      messageSubject = "(no subject)";
    }

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

