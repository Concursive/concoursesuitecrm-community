//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Represents a copied survey that can have questions and answers and
 *  belongs to an active Campaign
 *
 */
public class ActiveSurvey extends SurveyBase {

  protected int id = -1;
  private int campaignId = -1;
  //TODO: Change to ActiveSurveyQuestionList
  private ActiveSurveyQuestionList questions = new ActiveSurveyQuestionList();
  private SurveyAnswerList answers = new SurveyAnswerList();

  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private boolean enabled = true;

  public ActiveSurvey() { }
  
  public ActiveSurvey(Survey is) {
    this.setName(is.getName());
    this.setDescription(is.getDescription());
    this.setIntro(is.getIntro());
    this.setItemLength(is.getItemLength());
    this.setType(is.getType());
    this.setTypeName(is.getTypeName());
    this.setQuestions(is.getQuestions());
  }

  public ActiveSurvey(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public ActiveSurvey(Connection db, int surveyId) throws SQLException {
    if (surveyId < 1) {
      throw new SQLException("ActiveSurvey ID not specified.");
    }
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = 
      "SELECT s.*, " +
      "st.description as typename " +
      "FROM active_survey s " +
      "LEFT JOIN lookup_survey_types st ON (s.type = st.code) " +
      "WHERE s.active_survey_id = ? ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, surveyId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("ActiveSurvey record not found.");
    }
    rs.close();
    pst.close();

    questions.setActiveSurveyId(this.getId());
    questions.buildList(db);
  }
  
  
  public void setId(int tmp) { this.id = tmp; }
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }
  public void setCampaignId(int tmp) { this.campaignId = tmp; }
  public int getCampaignId() { return campaignId; }

  public int getId() { return id; }
  
  public static int getId(Connection db, int activeCampaignId) throws SQLException {
    int surveyId = -1;
    String sql = 
      "SELECT active_survey_id " +
      "FROM active_survey " +
      "WHERE campaign_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, activeCampaignId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      surveyId = rs.getInt("active_survey_id");
    }
    rs.close();
    return surveyId;
  }

  public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
  public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
  public int getEnteredBy() { return enteredBy; }
  public int getModifiedBy() { return modifiedBy; }
  public java.sql.Timestamp getModified() {
    return modified;
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
  public java.sql.Timestamp getEntered() {
    return entered;
  }
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
  
  public void setEnabled(boolean tmp) { this.enabled = tmp; }
  public boolean getEnabled() { return enabled; }
  
  public SurveyAnswerList getAnswers() {
    return answers;
  }


  public void setAnswers(SurveyAnswerList answers) {
    this.answers = answers;
  }
  


  public void setQuestions(SurveyQuestionList inactiveQuestions) {
    questions = new ActiveSurveyQuestionList(inactiveQuestions);
    questions.setActiveSurveyId(id);
  }


  public void setAnswerItems(HttpServletRequest request) {
    answers = new SurveyAnswerList(request);
  }


  public ActiveSurveyQuestionList getQuestions() {
    return questions;
  }


  public void setQuestions(ActiveSurveyQuestionList items) {
    this.questions = questions;
  }

  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    String sql = 
        "INSERT INTO active_survey " +
        "(campaign_id, name, description, intro, itemLength, type, " +
        "enteredBy, modifiedBy) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
    try {
      db.setAutoCommit(false);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, campaignId);
      pst.setString(++i, name);
      pst.setString(++i, description);
      pst.setString(++i, intro);
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
        thisQuestion.insert(db, this.getId(), this.getType());
      }

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


  public boolean delete(Connection db) throws SQLException {
    boolean commit = true;
    Statement st = null;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      st = db.createStatement();
      st.executeUpdate("DELETE FROM active_survey_answers WHERE response_id IN (SELECT response_id FROM active_survey_responses WHERE active_survey_id = " + this.getId() + ")");
      st.executeUpdate("DELETE FROM active_survey_responses WHERE active_survey_id = " + this.getId());
      st.executeUpdate("DELETE FROM active_survey_questions WHERE active_survey_id = " + this.getId());
      st.executeUpdate("DELETE FROM active_survey WHERE active_survey_id = " + this.getId());
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      System.out.println(e.toString());
      throw new SQLException(e.toString());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
      if (st != null) {
        st.close();
      }
    }
    return true;
  }
  
   public int update(Connection db) throws SQLException {
    int resultCount = -1;

    try {
      db.setAutoCommit(false);

      /* Iterator x = questions.iterator();
      while (x.hasNext()) {
        ActiveSurveyQuestion thisQuestion = (ActiveSurveyQuestion) x.next();
        thisQuestion.process(db, this.getId(), this.getType());
      } */

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


  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      throw new SQLException("ActiveSurvey ID was not specified");
    }

    PreparedStatement pst = null;
    String sql = 
      "UPDATE active_survey " +
      "SET campaign_id = ?, name = ?, description = ?, intro = ?, itemlength = ?, " +
      "type = ?, " +
      "enabled = ?, " +
      "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
      "WHERE active_survey_id = ? ";
    int i = 0;
    pst = db.prepareStatement(sql);
    pst.setInt(++i, campaignId);
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setString(++i, this.getIntro());
    pst.setInt(++i, this.getItemLength());
    pst.setInt(++i, this.getType());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  protected void buildRecord(ResultSet rs) throws SQLException {
    //active_survey table
    this.setId(rs.getInt("active_survey_id"));
    campaignId = rs.getInt("campaign_id");
    name = rs.getString("name");
    description = rs.getString("description");
    intro = rs.getString("intro");
    itemLength = rs.getInt("itemlength");
    type = rs.getInt("type");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");

    //lookup_survey_types table
    typeName = rs.getString("typename");
  }
}

