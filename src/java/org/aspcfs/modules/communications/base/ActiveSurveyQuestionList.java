//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import javax.servlet.http.*;

public class ActiveSurveyQuestionList extends ArrayList {

  private int id = -1;
  private int activeSurveyId = -1;

  public ActiveSurveyQuestionList() { }
  
  public ActiveSurveyQuestionList(SurveyQuestionList inactiveQuestions) { 
    Iterator i = inactiveQuestions.iterator();
    while (i.hasNext()) {
      SurveyQuestion inactiveQuestion = (SurveyQuestion)i.next();
      ActiveSurveyQuestion thisQuestion = new ActiveSurveyQuestion(inactiveQuestion);
      thisQuestion.setActiveSurveyId(activeSurveyId);
      this.add(thisQuestion);
    }
  }

  public ActiveSurveyQuestion getObject(ResultSet rs) throws SQLException {
    ActiveSurveyQuestion thisQuestion = new ActiveSurveyQuestion(rs);
    return thisQuestion;
  }


  public int getId() {
    return id;
  }


  public void setId(int id) {
    this.id = id;
  }


  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  public int getActiveSurveyId() {
    return activeSurveyId;
  }


  public void setActiveSurveyId(int surveyId) {
    this.activeSurveyId = surveyId;
  }


  public void setActiveSurveyId(String surveyId) {
    this.activeSurveyId = Integer.parseInt(surveyId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      ActiveSurveyQuestion thisQuestion = this.getObject(rs);
      this.add(thisQuestion);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    int items = -1;

    String sql = 
      "SELECT sq.* " +
      "FROM active_survey_questions sq " +
      "WHERE sq.active_survey_id = ? ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, activeSurveyId);
    ResultSet rs = pst.executeQuery();
    return rs;
  }

}

