//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.mycfs.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;

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
  public final static int DELETE = 3;
  public final static int SENDER = 1;
  public final static int RECIPIENT = 2;

  public String sentToList = "";
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
  private boolean deleteFlag = false;
  private String currentView = "none";
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  protected HashMap recipientList;


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
   *@param  myId              Description of the Parameter
   *@param  listView          Description of the Parameter
   *@exception  SQLException  Description of Exception
   */
  public CFSNote(Connection db, String noteId, int myId, String listView) throws SQLException {

    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT m.*, ml.*, ct_sent.namefirst as sent_namefirst, ct_sent.namelast as sent_namelast " +
        "FROM cfsinbox_messagelink ml,cfsinbox_message m " +
        "LEFT JOIN contact ct_eb ON (m.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (m.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN contact ct_sent ON (m.enteredby = ct_sent.user_id) " +
        "WHERE m.id > -1 AND (m.id = ml.id) ");

    if (noteId != null && !noteId.equals("")) {
      sql.append("AND m.id = " + noteId + " ");
      if (listView.equals("sent")) {
        sql.append("AND ml.sent_from = " + myId + " ");
      } else {
        sql.append("AND ml.sent_to = " + myId + " ");
      }
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
   *@param  myId              Description of the Parameter
   *@param  listView          Description of the Parameter
   *@exception  SQLException  Description of Exception
   */
  public CFSNote(Connection db, int noteId, int myId, String listView) throws SQLException {

    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT m.*, ml.*, ct_sent.namefirst as sent_namefirst, ct_sent.namelast as sent_namelast " +
        "FROM cfsinbox_messagelink ml,cfsinbox_message m " +
        "LEFT JOIN contact ct_eb ON (m.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (m.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN contact ct_sent ON (m.enteredby = ct_sent.user_id) " +
        "WHERE m.id > -1 AND (m.id = ml.id) ");

    if (noteId > -1) {
      sql.append("AND m.id = " + noteId + " ");
      //sql.append("AND ml.sent_to = " + myId + " ");
      if (listView.equalsIgnoreCase("sent")) {
        sql.append("AND ml.sent_from = " + myId + " ");
      } else {
        sql.append("AND ml.sent_to = " + myId + " ");
      }
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
   *  Sets the DeleteFlag attribute of the CFSNote object
   *
   *@param  deleteFlag  The new deleteFlag value
   */

  public void setDeleteFlag(boolean deleteFlag) {
    this.deleteFlag = deleteFlag;
  }


  /**
   *  Sets the CurrentView attribute of the CFSNote object
   *
   *@param  currentView  The new currentView value
   */
  public void setCurrentView(String currentView) {
    this.currentView = currentView;
  }



  /**
   *  Gets the recipientList attribute of the CFSNote object
   *
   *@return    The recipientList value
   */
  public HashMap getRecipientList() {
    return recipientList;
  }


  /**
   *  Gets the RecipientList attribute of the CFSNote object
   *
   *@param  db                Description of the Parameter
   *@return                   The RecipientList value
   *@exception  SQLException  Description of the Exception
   */
  public HashMap buildRecipientList(Connection db) throws SQLException {
    Statement st = null;
    ResultSet rs = null;
    sentToList = "";
    StringBuffer sql = new StringBuffer();
    HashMap recipients = new HashMap();

    sql.append(
        "SELECT ct_eb.namefirst as sent_namefirst, ct_eb.namelast as sent_namelast,ml.status " +
        "FROM cfsinbox_messagelink ml " +
        "LEFT JOIN contact ct_eb ON (ml.sent_to = ct_eb.contact_id) " +
        "WHERE ml.id > -1 AND ml.id = '" + this.getId() + "' ");

    try {
      if (id != -1) {
        st = db.createStatement();
        rs = st.executeQuery(sql.toString());
        while (rs.next()) {
          String recipient = rs.getString("sent_namefirst") + " " + rs.getString("sent_namelast");
          Integer status = new Integer(rs.getInt("status"));
          recipients.put(recipient, status);
        }
        rs.close();
        st.close();
      }
      this.recipientList = recipients;
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return recipients;
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
    } else if (status == 2) {
      return ("Archived");
    } else {
      return ("Deleted");
    }
  }


  /**
   *  Gets the statusColor of the CFSNote object
   *
   *@param  noteStatus  Description of the Parameter
   *@return             The statusColor value
   */
  public String getStatusColor(int noteStatus) {
    if (noteStatus == 0) {
      return ("red");
    } else if (noteStatus == 1) {
      return ("blue");
    } else if (noteStatus == 2) {
      return ("orange");
    } else {
      return ("green");
    }
  }


  /**
   *  Gets the statusText of the CFSNote object
   *
   *@param  noteStatus  Description of the Parameter
   *@return             The statusText value
   */
  public String getStatusText(int noteStatus) {
    if (noteStatus == 0) {
      return ("Unread");
    } else if (noteStatus == 1) {
      return ("Read");
    } else if (noteStatus == 2) {
      return ("Archived");
    } else {
      return ("Deleted");
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
   *  Gets the DeleteFlag attribute of the CFSNote object
   *
   *@return    The DeleteFlag value
   */
  public boolean getDeleteFlag() {
    return deleteFlag;
  }


  /**
   *  Gets the CurrentView attribute of the CFSNote object
   *
   *@return    The CurrentView value
   */
  public String getCurrentView() {
    return currentView;
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
          "(enteredby, modifiedby, body, reply_id, type, delete_flag) " +
          "VALUES (?, ?, ?, ?, ?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.setString(++i, this.getBody());
      pst.setInt(++i, this.getReplyId());
      pst.setInt(++i, this.getType());
      pst.setBoolean(++i, this.getDeleteFlag());
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "cfsinbox_message_id_seq");
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
   *@param  isUser            Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insertLink(Connection db, boolean isUser) throws SQLException {
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

      if (!isUser) {
        sql = new StringBuffer();
        sql.append("UPDATE cfsinbox_messagelink " +
            "SET status = 3 " +
            "WHERE  id ='" + this.getId() + "' AND sent_to ='" + this.getSentTo() + "' ");
        pst = db.prepareStatement(sql.toString());
        pst.execute();
      }
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
      PreparedStatement pst = null;
      ResultSet rs = null;
      int inboxCount = -1;
      int outboxCount = -1;
      String outboxQuery = "SELECT count(*) as outboxcount FROM cfsinbox_message " +
          "WHERE id ='" + this.getId() + "' " +
          "AND delete_flag =" + DatabaseUtils.getFalse(db) + " ";

      String inboxQuery = "SELECT count(*) as inboxcount FROM cfsinbox_messagelink " +
          "WHERE id =" + this.getId() + " " +
          "AND status <> 3 ";

      db.setAutoCommit(false);
      Statement st = db.createStatement();

      /*
       *  Get the status of deleted msgs by sender
       */
      pst = db.prepareStatement(outboxQuery);
      rs = pst.executeQuery();
      if (rs.next()) {
        outboxCount = rs.getInt("outboxcount");
      }
      pst.close();
      rs.close();
      /*
       *  Get the count of deleted msgs by the recipients
       */
      pst = db.prepareStatement(inboxQuery);
      rs = pst.executeQuery();
      if (rs.next()) {
        inboxCount = rs.getInt("inboxcount");
      }

      pst.close();
      rs.close();
      /*
       *  sender & recipient(s) have marked messages to be deleted
       */
      if ((outboxCount == 0 && inboxCount == 1) || (outboxCount == 1 && inboxCount == 0)) {
        st.executeUpdate(
            "DELETE FROM cfsinbox_messagelink " +
            "WHERE id = " + this.getId() + " ");

        st.executeUpdate(
            "DELETE FROM cfsinbox_message " +
            "WHERE id = " + this.getId() + " ");
      } else {
        /*
         *  atleast one of the sender or the recipient(s) has not deleted message
         */
        if (!getCurrentView().equalsIgnoreCase("sent")) {
          /*
           *  inbox or archived delete --> Mark status as 3
           */
          st.executeUpdate(
              "UPDATE cfsinbox_messagelink " +
              "SET status = 3 " +
              "WHERE  id ='" + this.getId() + "' AND sent_to ='" + myId + "' ");
        } else {
          /*
           *  Outbox delete --> Set delete_flag
           */
          st.executeUpdate(
              "UPDATE cfsinbox_message " +
              "SET delete_flag =" + DatabaseUtils.getTrue(db) + " " +
              "WHERE  id =" + this.getId() + " ");
        }
        st.close();
      }
    } catch (SQLException e) {
      db.rollback();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("CFSNote-> " + e.toString());
      }
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
        "WHERE id = ? AND sent_to = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getStatus());
    pst.setInt(++i, this.getId());
    pst.setInt(++i, this.getSentTo());
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

    if (!getCurrentView().equalsIgnoreCase("sent")) {
      /*
       *  cfsinbox_message table
       */
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
      deleteFlag = rs.getBoolean("delete_flag");
      sentTo = rs.getInt("sent_to");

      status = rs.getInt("status");
      viewed = rs.getTimestamp("viewed");

      /*
       *  contact table
       */
      sentName = rs.getString("sent_namefirst") + " " + rs.getString("sent_namelast");

    } else {
      /*
       *  outbox case
       */
      this.setId(rs.getInt("id"));
      subject = rs.getString("subject");
      body = rs.getString("body");
      entered = rs.getTimestamp("sent");
      deleteFlag = rs.getBoolean("delete_flag");
    }
  }

}

