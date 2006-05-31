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

import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author akhi_m
 * @version $Id$
 * @created November 1, 2002
 */
public class SurveyAnswerItem {
  private int id = -1;
  private int answerId = -1;
  private int contactId = -1;
  private String comments = null;
  private Contact recipient = null;
  private java.sql.Timestamp entered = null;
  private ActiveSurveyQuestionItem item = null;


  /**
   * Constructor for the SurveyAnswerItem object
   */
  public SurveyAnswerItem() {
  }


  /**
   * Constructor for the SurveyAnswerItem object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public SurveyAnswerItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Description of the Method
   *
   * @param request   Description of the Parameter
   * @param parseItem Description of the Parameter
   */
  public void buildRecord(HttpServletRequest request, String parseItem) {
    if (request.getParameter(parseItem + "id") != null) {
      this.setId(request.getParameter(parseItem + "id"));
    }
  }


  /**
   * Sets the id attribute of the SurveyAnswerItem object
   *
   * @param id The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * Sets the answerId attribute of the SurveyAnswerItem object
   *
   * @param answerId The new answerId value
   */
  public void setAnswerId(int answerId) {
    this.answerId = answerId;
  }


  /**
   * Sets the comments attribute of the SurveyAnswerItem object
   *
   * @param comments The new comments value
   */
  public void setComments(String comments) {
    this.comments = comments;
  }


  /**
   * Sets the item attribute of the SurveyAnswerItem object
   *
   * @param item The new item value
   */
  public void setItem(ActiveSurveyQuestionItem item) {
    this.item = item;
  }


  /**
   * Sets the entered attribute of the ActiveSurveyAnswerItem object
   *
   * @param entered The new entered value
   */
  public void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
  }


  /**
   * Sets the recipient attribute of the ActiveSurveyAnswerItem object
   *
   * @param recipient The new recipient value
   */
  public void setRecipient(Contact recipient) {
    this.recipient = recipient;
  }


  /**
   * Sets the contactId attribute of the SurveyAnswerItem object
   *
   * @param contactId The new contactId value
   */
  public void setContactId(int contactId) {
    this.contactId = contactId;
  }


  /**
   * Gets the contactId attribute of the SurveyAnswerItem object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Gets the recipient attribute of the ActiveSurveyAnswerItem object
   *
   * @return The recipient value
   */
  public Contact getRecipient() {
    return recipient;
  }


  /**
   * Gets the entered attribute of the ActiveSurveyAnswerItem object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the item attribute of the SurveyAnswerItem object
   *
   * @return The item value
   */
  public ActiveSurveyQuestionItem getItem() {
    return item;
  }


  /**
   * Gets the comments attribute of the SurveyAnswerItem object
   *
   * @return The comments value
   */
  public String getComments() {
    return comments;
  }


  /**
   * Gets the answerId attribute of the SurveyAnswerItem object
   *
   * @return The answerId value
   */
  public int getAnswerId() {
    return answerId;
  }


  /**
   * Gets the id attribute of the SurveyAnswerItem object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the SurveyAnswerItem object
   *
   * @param id The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildItemDetails(Connection db) throws SQLException {
    if (id < 1) {
      throw new SQLException("Answer ID not specified.");
    }
    item = new ActiveSurveyQuestionItem(db, id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildContactDetails(Connection db) throws SQLException {
    if (contactId == -1) {
      throw new SQLException("Contact ID not specified");
    }
    recipient = new Contact(db, contactId);
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("item_id"));
    this.setAnswerId(rs.getInt("answer_id"));
    this.setComments(rs.getString("comments"));
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildDetailedRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("item_id"));
    this.setAnswerId(rs.getInt("answer_id"));
    this.setComments(rs.getString("comments"));
    this.setContactId(rs.getInt("contact_id"));
    this.setEntered(rs.getTimestamp("entered"));
  }


  /**
   * Constructor for the SurveyAnswer object
   *
   * @param db       Description of the Parameter
   * @param passedId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public SurveyAnswerItem(Connection db, int passedId) throws SQLException {
    if (passedId < 1) {
      throw new SQLException("Answer ID not specified.");
    }

    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT * " +
        "FROM active_survey_answer_items s " +
        "WHERE answer_id = ? ");
    pst.setInt(1, passedId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Survey Answer Item record not found.");
    }
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param thisAnswerId Description of the Parameter
   * @param questionId   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db, int thisAnswerId, int questionId) throws SQLException {
    if (thisAnswerId == -1) {
      throw new SQLException("Answer ID not specified");
    }
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      int seqId = DatabaseUtils.getNextSeq(
          db, "active_survey_answer_ite_id_seq");
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO active_survey_answer_items " +
          "(" + (seqId > -1 ? "id, " : "") + "item_id, answer_id, comments) " +
          "VALUES " +
          "(" + (seqId > -1 ? "?, " : "") + "?, ?, ?) ");
      int i = 0;
      if (seqId > -1) {
        pst.setInt(++i, seqId);
      }
      pst.setInt(++i, this.getId());
      pst.setInt(++i, thisAnswerId);
      pst.setString(++i, comments);
      pst.execute();
      pst.close();
      updateSurveyAverage(db, questionId);
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
   * @param db         Description of the Parameter
   * @param questionId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int updateSurveyAverage(Connection db, int questionId) throws SQLException {
    if (questionId == -1) {
      throw new SQLException("Question ID not specified.");
    }
    int count = 0;
    int i = 0;
    PreparedStatement pst = null;
    i = 0;
    pst = db.prepareStatement(
        "UPDATE active_survey_answer_avg " +
        "SET total = total + 1 " +
        "WHERE question_id = ? AND item_id =? ");
    pst.setInt(++i, questionId);
    pst.setInt(++i, this.getId());
    count = pst.executeUpdate();
    pst.close();
    return count;
  }

}

