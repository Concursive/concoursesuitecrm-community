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

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id: SurveyResponse.java,v 1.4 2004/09/16 19:24:01 mrajkowski Exp
 *          $
 * @created January 14, 2003
 */
public class SurveyResponse {

  public static int ADDRESS_UPDATED = 1;
  public static int ADDRESS_VALID = 0;
  public static int ADDRESS_NO_RESPONSE = 2;

  protected int id = -1;
  private int activeSurveyId = -1;
  private int contactId = -1;
  private String uniqueCode = null;
  private String ipAddress = null;
  private java.sql.Timestamp entered = null;
  private SurveyAnswerList answers = new SurveyAnswerList();
  private Contact contact = null;
  private int addressUpdated = SurveyResponse.ADDRESS_NO_RESPONSE;


  /**
   * Constructor for the SurveyResponse object
   */
  public SurveyResponse() {
  }


  /**
   * Constructor for the SurveyResponse object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public SurveyResponse(ResultSet rs) throws SQLException {
    id = rs.getInt("response_id");
    activeSurveyId = rs.getInt("active_survey_id");
    contactId = rs.getInt("contact_id");
    uniqueCode = rs.getString("unique_code");
    ipAddress = rs.getString("ip_address");
    entered = rs.getTimestamp("entered");
  }


  /**
   * Constructor for the SurveyResponse object
   *
   * @param context Description of the Parameter
   */
  public SurveyResponse(ActionContext context) {
    this.setIpAddress(context.getIpAddress());
    answers = new SurveyAnswerList(context.getRequest());
  }


  /**
   * Sets the id attribute of the SurveyResponse object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the activeSurveyId attribute of the SurveyResponse object
   *
   * @param tmp The new activeSurveyId value
   */
  public void setActiveSurveyId(int tmp) {
    this.activeSurveyId = tmp;
  }


  /**
   * Sets the contactId attribute of the SurveyResponse object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the uniqueCode attribute of the SurveyResponse object
   *
   * @param tmp The new uniqueCode value
   */
  public void setUniqueCode(String tmp) {
    this.uniqueCode = tmp;
  }


  /**
   * Sets the ipAddress attribute of the SurveyResponse object
   *
   * @param tmp The new ipAddress value
   */
  public void setIpAddress(String tmp) {
    this.ipAddress = tmp;
  }


  /**
   * Sets the entered attribute of the SurveyResponse object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the answers attribute of the SurveyResponse object
   *
   * @param tmp The new answers value
   */
  public void setAnswers(SurveyAnswerList tmp) {
    this.answers = tmp;
  }


  /**
   * Sets the contact attribute of the SurveyResponse object
   *
   * @param contact The new contact value
   */
  public void setContact(Contact contact) {
    this.contact = contact;
  }


  /**
   * Sets the addressUpdated attribute of the SurveyResponse object
   *
   * @param tmp The new addressUpdated value
   */
  public void setAddressUpdated(int tmp) {
    this.addressUpdated = tmp;
  }


  /**
   * Sets the addressUpdated attribute of the SurveyResponse object
   *
   * @param tmp The new addressUpdated value
   */
  public void setAddressUpdated(String tmp) {
    this.addressUpdated = Integer.parseInt(tmp);
  }


  /**
   * Gets the contact attribute of the SurveyResponse object
   *
   * @return The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   * Gets the id attribute of the SurveyResponse object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the activeSurveyId attribute of the SurveyResponse object
   *
   * @return The activeSurveyId value
   */
  public int getActiveSurveyId() {
    return activeSurveyId;
  }


  /**
   * Gets the contactId attribute of the SurveyResponse object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Gets the uniqueCode attribute of the SurveyResponse object
   *
   * @return The uniqueCode value
   */
  public String getUniqueCode() {
    return uniqueCode;
  }


  /**
   * Gets the ipAddress attribute of the SurveyResponse object
   *
   * @return The ipAddress value
   */
  public String getIpAddress() {
    return ipAddress;
  }


  /**
   * Gets the entered attribute of the SurveyResponse object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredString attribute of the SurveyResponse object
   *
   * @return The enteredString value
   */
  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   * Gets the enteredDateTimeString attribute of the SurveyResponse object
   *
   * @return The enteredDateTimeString value
   */
  public String getEnteredDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   * Gets the answers attribute of the SurveyResponse object
   *
   * @return The answers value
   */
  public SurveyAnswerList getAnswers() {
    return answers;
  }


  /**
   * Gets the addressUpdated attribute of the SurveyResponse object
   *
   * @return The addressUpdated value
   */
  public int getAddressUpdated() {
    return addressUpdated;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "active_survey_r_response_id_seq");
      String sql = "INSERT INTO active_survey_responses " +
          "(" + (id > -1 ? "response_id, " : "") + "active_survey_id, contact_id, unique_code, ip_address, address_updated) " +
          "VALUES (" + (id > -1 ? "?, " : "") + "?,?, ?, ?, ?) ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, activeSurveyId);
      pst.setInt(++i, contactId);
      pst.setString(++i, uniqueCode);
      pst.setString(++i, ipAddress);
      pst.setInt(++i, addressUpdated);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "active_survey_r_response_id_seq", id);
      answers.insert(db, id);
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
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM active_survey_responses " +
        "WHERE response_id = ?");
    pst.setInt(1, id);
    pst.executeUpdate();
    pst.close();
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildContact(Connection db) throws SQLException {
    if (contactId == -1) {
      throw new SQLException("Contact Id not specified");
    }
    this.setContact(new Contact(db, contactId));
  }
}

