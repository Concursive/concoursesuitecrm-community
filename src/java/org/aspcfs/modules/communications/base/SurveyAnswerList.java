//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Contains a list of email addresses... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 */
public class SurveyAnswerList extends Vector {
	
	private int surveyId = -1;
	private int questionId = -1;

  public SurveyAnswerList() { }
  
public int getSurveyId() {
	return surveyId;
}
public void setSurveyId(int surveyId) {
	this.surveyId = surveyId;
}

  public SurveyAnswerList(HttpServletRequest request) {
    int i = 0;
    while (request.getParameter("quest" + (++i) + "id") != null) {
      SurveyAnswer thisAnswer = new SurveyAnswer();
      thisAnswer.buildRecord(request, i);
      this.addElement(thisAnswer);
      System.out.println("Added an answer: " + thisAnswer.getQuestionId());
    }
  }
 public int getQuestionId() {
	return questionId;
}
public void setQuestionId(int questionId) {
	this.questionId = questionId;
}
 
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      SurveyAnswer thisAnswer = this.getObject(rs);
      this.add(thisAnswer);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }
  
  public SurveyAnswer getObject(ResultSet rs) throws SQLException {
    SurveyAnswer thisAnswer = new SurveyAnswer(rs);
    return thisAnswer;
  }

  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    int items = -1;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT sa.* " +
        "FROM survey_answer sa ");
    sql.append("WHERE sa.survey_id > -1 ");
    createFilter(sql);
    //sql.append("ORDER BY level, o.option_name ");
    pst = db.prepareStatement(sql.toString());
    items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    return rs;
  }
  
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (surveyId > -1) {
      sqlFilter.append("AND sa.survey_id = ? ");
    }
    if (questionId > -1) {
      sqlFilter.append("AND sa.question_id = ? ");
    }
  }

  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (surveyId > -1) {
      pst.setInt(++i, surveyId);
    }
    if (questionId > -1) {
      pst.setInt(++i, questionId);
    }
    return i;
  }

}

