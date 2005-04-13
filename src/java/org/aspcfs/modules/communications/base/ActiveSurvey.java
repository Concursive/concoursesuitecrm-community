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

import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.DateFormat;
import java.util.Iterator;

/**
 *  Represents a copied survey that can have questions and answers and belongs
 *  to an active Campaign
 *
 *@author
 *@created    November 1, 2002
 *@version    $Id$
 */
public class ActiveSurvey extends SurveyBase {

  protected int id = -1;
  private int campaignId = -1;
  private ActiveSurveyQuestionList questions = new ActiveSurveyQuestionList();
  private SurveyAnswerList answers = new SurveyAnswerList();

  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private boolean enabled = true;


  /**
   *  Constructor for the ActiveSurvey object
   */
  public ActiveSurvey() { }


  /**
   *  Constructor for the ActiveSurvey object
   *
   *@param  is  Description of the Parameter
   */
  public ActiveSurvey(Survey is) {
    this.setName(is.getName());
    this.setDescription(is.getDescription());
    this.setIntro(is.getIntro());
    this.setOutro(is.getOutro());
    this.setItemLength(is.getItemLength());
    this.setType(is.getType());
    //copies each question individually
    this.setQuestions(is.getQuestions());
  }


  /**
   *  Constructor for the ActiveSurvey object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ActiveSurvey(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the ActiveSurvey object
   *
   *@param  db                Description of the Parameter
   *@param  surveyId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ActiveSurvey(Connection db, int surveyId) throws SQLException {
    if (surveyId < 1) {
      throw new SQLException("ActiveSurvey ID not specified.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT s.* " +
        "FROM active_survey s " +
        "WHERE s.active_survey_id = ? ");
    pst.setInt(1, surveyId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("ActiveSurvey record not found.");
    }
    questions.setActiveSurveyId(this.getId());
    questions.buildList(db);
  }


  /**
   *  Sets the id attribute of the ActiveSurvey object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActiveSurvey object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the campaignId attribute of the ActiveSurvey object
   *
   *@param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Gets the campaignId attribute of the ActiveSurvey object
   *
   *@return    The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   *  Gets the id attribute of the ActiveSurvey object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the id attribute of the ActiveSurvey class
   *
   *@param  db                Description of the Parameter
   *@param  activeCampaignId  Description of the Parameter
   *@return                   The id value
   *@exception  SQLException  Description of the Exception
   */
  public static int getId(Connection db, int activeCampaignId, int surveyType) throws SQLException {
    int surveyId = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT active_survey_id " +
        "FROM active_survey " +
        "WHERE campaign_id = ? " +
        "AND type = ? ");
    pst.setInt(1, activeCampaignId);
    pst.setInt(2, surveyType);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      surveyId = rs.getInt("active_survey_id");
    }
    rs.close();
    pst.close();
    return surveyId;
  }


  /**
   *  Sets the enteredBy attribute of the ActiveSurvey object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ActiveSurvey object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Gets the enteredBy attribute of the ActiveSurvey object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the ActiveSurvey object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the ActiveSurvey object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedString attribute of the ActiveSurvey object
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
   *  Gets the modifiedDateTimeString attribute of the ActiveSurvey object
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
   *  Gets the entered attribute of the ActiveSurvey object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredString attribute of the ActiveSurvey object
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
   *  Gets the enteredDateTimeString attribute of the ActiveSurvey object
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
   *  Sets the enabled attribute of the ActiveSurvey object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Gets the enabled attribute of the ActiveSurvey object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the answers attribute of the ActiveSurvey object
   *
   *@return    The answers value
   */
  public SurveyAnswerList getAnswers() {
    return answers;
  }


  /**
   *  Sets the answers attribute of the ActiveSurvey object
   *
   *@param  answers  The new answers value
   */
  public void setAnswers(SurveyAnswerList answers) {
    this.answers = answers;
  }



  /**
   *  Sets the questions attribute of the ActiveSurvey object
   *
   *@param  inactiveQuestions  The new questions value
   */
  public void setQuestions(SurveyQuestionList inactiveQuestions) {
    questions = new ActiveSurveyQuestionList(inactiveQuestions);
    questions.setActiveSurveyId(id);
  }


  /**
   *  Sets the answerItems attribute of the ActiveSurvey object
   *
   *@param  request  The new answerItems value
   */
  public void setAnswerItems(HttpServletRequest request) {
    answers = new SurveyAnswerList(request);
  }


  /**
   *  Gets the questions attribute of the ActiveSurvey object
   *
   *@return    The questions value
   */
  public ActiveSurveyQuestionList getQuestions() {
    return questions;
  }


  /**
   *  Sets the questions attribute of the ActiveSurvey object
   *
   *@param  items  The new questions value
   */
  public void setQuestions(ActiveSurveyQuestionList items) {
    questions = items;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO active_survey " +
          "(campaign_id, name, description, intro, outro, itemLength, type, " +
          "enteredBy, modifiedBy) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");
      pst.setInt(++i, campaignId);
      pst.setString(++i, name);
      pst.setString(++i, description);
      pst.setString(++i, intro);
      pst.setString(++i, outro);
      pst.setInt(++i, itemLength);
      pst.setInt(++i, type);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "active_survey_active_survey_seq");
      //Insert the questions
      Iterator x = questions.iterator();
      while (x.hasNext()) {
        ActiveSurveyQuestion thisQuestion = (ActiveSurveyQuestion) x.next();
        thisQuestion.insert(db, this.getId());
      }
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    boolean commit = true;
    Statement st = null;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      st = db.createStatement();
      st.executeUpdate("DELETE FROM active_survey_answer_items WHERE answer_id IN (SELECT answer_id FROM active_survey_answers sa, active_survey_responses sr WHERE active_survey_id = " + this.getId() + " AND sa.response_id = sr.response_id)");
      st.executeUpdate("DELETE FROM active_survey_answers WHERE response_id IN (SELECT response_id FROM active_survey_responses WHERE active_survey_id = " + this.getId() + ")");
      st.executeUpdate("DELETE FROM active_survey_responses WHERE active_survey_id = " + this.getId());
      st.executeUpdate("DELETE FROM active_survey_answer_avg WHERE question_id IN (SELECT question_id FROM active_survey_questions WHERE active_survey_id = " + this.getId() + ")");
      st.executeUpdate("DELETE FROM active_survey_items WHERE question_id IN (SELECT question_id FROM active_survey_questions WHERE active_survey_id = " + this.getId() + ")");
      st.executeUpdate("DELETE FROM active_survey_questions WHERE active_survey_id = " + this.getId());
      st.executeUpdate("DELETE FROM active_survey WHERE active_survey_id = " + this.getId());
      st.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.toString());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
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
  public int update(Connection db) throws SQLException {
    int resultCount = -1;

    try {
      db.setAutoCommit(false);

      /*
       *  Iterator x = questions.iterator();
       *  while (x.hasNext()) {
       *  ActiveSurveyQuestion thisQuestion = (ActiveSurveyQuestion) x.next();
       *  thisQuestion.process(db, this.getId(), this.getType());
       *  }
       */
      resultCount = this.update(db, false);
      db.commit();
    } catch (Exception e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  override          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      throw new SQLException("ActiveSurvey ID was not specified");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE active_survey " +
        "SET campaign_id = ?, name = ?, description = ?, intro = ?, outro = ?, itemlength = ?, " +
        "type = ?, " +
        "enabled = ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
        "WHERE active_survey_id = ? ");
    pst.setInt(++i, campaignId);
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setString(++i, this.getIntro());
    pst.setString(++i, this.getOutro());
    pst.setInt(++i, this.getItemLength());
    pst.setInt(++i, this.getType());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //active_survey table
    this.setId(rs.getInt("active_survey_id"));
    campaignId = rs.getInt("campaign_id");
    name = rs.getString("name");
    description = rs.getString("description");
    intro = rs.getString("intro");
    outro = rs.getString("outro");
    itemLength = rs.getInt("itemlength");
    type = rs.getInt("type");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");

    //lookup_survey_types table
  }
}

