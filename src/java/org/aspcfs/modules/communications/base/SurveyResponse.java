//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.communications.base;

import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.contacts.base.Contact;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    January 14, 2003
 *@version    $Id$
 */
public class SurveyResponse {

  protected int id = -1;
  private int activeSurveyId = -1;
  private int contactId = -1;
  private String uniqueCode = null;
  private String ipAddress = null;
  private java.sql.Timestamp entered = null;
  private SurveyAnswerList answers = new SurveyAnswerList();
  private Contact contact = null;


  /**
   *  Constructor for the SurveyResponse object
   */
  public SurveyResponse() { }


  /**
   *  Constructor for the SurveyResponse object
   *
   *@param  rs  Description of the Parameter
   */
  public SurveyResponse(ResultSet rs) throws SQLException{
    id = rs.getInt("response_id");
    activeSurveyId = rs.getInt("active_survey_id");
    contactId = rs.getInt("contact_id");
    uniqueCode = rs.getString("unique_code");
    ipAddress = rs.getString("ip_address");
    entered = rs.getTimestamp("entered");
  }


  /**
   *  Constructor for the SurveyResponse object
   *
   *@param  context  Description of the Parameter
   */
  public SurveyResponse(ActionContext context) {
    this.setIpAddress(context.getIpAddress());
    answers = new SurveyAnswerList(context.getRequest());
  }


  /**
   *  Sets the id attribute of the SurveyResponse object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the activeSurveyId attribute of the SurveyResponse object
   *
   *@param  tmp  The new activeSurveyId value
   */
  public void setActiveSurveyId(int tmp) {
    this.activeSurveyId = tmp;
  }


  /**
   *  Sets the contactId attribute of the SurveyResponse object
   *
   *@param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the uniqueCode attribute of the SurveyResponse object
   *
   *@param  tmp  The new uniqueCode value
   */
  public void setUniqueCode(String tmp) {
    this.uniqueCode = tmp;
  }


  /**
   *  Sets the ipAddress attribute of the SurveyResponse object
   *
   *@param  tmp  The new ipAddress value
   */
  public void setIpAddress(String tmp) {
    this.ipAddress = tmp;
  }


  /**
   *  Sets the entered attribute of the SurveyResponse object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the answers attribute of the SurveyResponse object
   *
   *@param  tmp  The new answers value
   */
  public void setAnswers(SurveyAnswerList tmp) {
    this.answers = tmp;
  }


  /**
   *  Sets the contact attribute of the SurveyResponse object
   *
   *@param  contact  The new contact value
   */
  public void setContact(Contact contact) {
    this.contact = contact;
  }


  /**
   *  Gets the contact attribute of the SurveyResponse object
   *
   *@return    The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   *  Gets the id attribute of the SurveyResponse object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the activeSurveyId attribute of the SurveyResponse object
   *
   *@return    The activeSurveyId value
   */
  public int getActiveSurveyId() {
    return activeSurveyId;
  }


  /**
   *  Gets the contactId attribute of the SurveyResponse object
   *
   *@return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the uniqueCode attribute of the SurveyResponse object
   *
   *@return    The uniqueCode value
   */
  public String getUniqueCode() {
    return uniqueCode;
  }


  /**
   *  Gets the ipAddress attribute of the SurveyResponse object
   *
   *@return    The ipAddress value
   */
  public String getIpAddress() {
    return ipAddress;
  }


  /**
   *  Gets the entered attribute of the SurveyResponse object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredString attribute of the SurveyResponse object
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
   *  Gets the enteredDateTimeString attribute of the SurveyResponse object
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
   *  Gets the answers attribute of the SurveyResponse object
   *
   *@return    The answers value
   */
  public SurveyAnswerList getAnswers() {
    return answers;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      String sql =
          "INSERT INTO active_survey_responses " +
          "(active_survey_id, contact_id, unique_code, ip_address) VALUES (?, ?, ?, ?) ";
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(1, activeSurveyId);
      pst.setInt(2, contactId);
      pst.setString(3, uniqueCode);
      pst.setString(4, ipAddress);
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "active_survey_r_response_id_seq");
      answers.insert(db, id);

      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildContact(Connection db) throws SQLException {
    if (contactId == -1) {
      throw new SQLException("Contact Id not specified");
    }
    this.setContact(new Contact(db, contactId));
  }
}

