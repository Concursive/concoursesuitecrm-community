//Copyright 2001-2002 Dark Horse Ventures
package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import javax.servlet.http.*;

/**
 *  For each SurveyItem (question), a user's response is stored as a
 *  SurveyAnswer
 *
 *@author     chris price
 *@created    August 7, 2002
 *@version    $Id$
 */
public class SurveyAnswer {
  private int id = -1;
  private int questionId = -1;
  private String comments = "";
  private int quantAns = -1;
  private String textAns = "";
  private int responseId = -1;


  /**
   *  Constructor for the SurveyAnswer object
   */
  public SurveyAnswer() { }


  /**
   *  Constructor for the SurveyAnswer object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public SurveyAnswer(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  request    Description of the Parameter
   *@param  parseItem  Description of the Parameter
   */
  public void buildRecord(HttpServletRequest request, int parseItem) {
    this.setQuestionId(request.getParameter("quest" + parseItem + "id"));

    if (request.getParameter("quest" + parseItem + "comments") != null) {
      this.setComments(request.getParameter("quest" + parseItem + "comments"));
    }

    if (request.getParameter("quest" + parseItem + "qans") != null) {
      this.setQuantAns(request.getParameter("quest" + parseItem + "qans"));
    }
  }


  /**
   *  Sets the id attribute of the SurveyAnswer object
   *
   *@param  id  The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Gets the id attribute of the SurveyAnswer object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the responseId attribute of the SurveyAnswer object
   *
   *@return    The responseId value
   */
  public int getResponseId() {
    return responseId;
  }


  /**
   *  Sets the responseId attribute of the SurveyAnswer object
   *
   *@param  responseId  The new responseId value
   */
  public void setResponseId(int responseId) {
    this.responseId = responseId;
  }


  /**
   *  Sets the id attribute of the SurveyAnswer object
   *
   *@param  id  The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("answer_id"));
    this.setResponseId(rs.getInt("response_id"));
    this.setQuestionId(rs.getInt("question_id"));
    this.setComments(rs.getString("comments"));
    this.setQuantAns(rs.getInt("quant_ans"));
    this.setTextAns(rs.getString("text_ans"));
  }


  /**
   *  Constructor for the SurveyAnswer object
   *
   *@param  db                Description of the Parameter
   *@param  passedId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public SurveyAnswer(Connection db, int passedId) throws SQLException {
    if (passedId < 1) {
      throw new SQLException("Question Answer ID not specified.");
    }

    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = 
      "SELECT * " +
      "FROM active_survey_answers s " +
      "WHERE answer_id = ? ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, passedId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Question Answer record not found.");
    }
    rs.close();
    pst.close();
  }


  /**
   *  Gets the questionId attribute of the SurveyAnswer object
   *
   *@return    The questionId value
   */
  public int getQuestionId() {
    return questionId;
  }


  /**
   *  Gets the comments attribute of the SurveyAnswer object
   *
   *@return    The comments value
   */
  public String getComments() {
    return comments;
  }


  /**
   *  Gets the quantAns attribute of the SurveyAnswer object
   *
   *@return    The quantAns value
   */
  public int getQuantAns() {
    return quantAns;
  }


  /**
   *  Gets the textAns attribute of the SurveyAnswer object
   *
   *@return    The textAns value
   */
  public String getTextAns() {
    return textAns;
  }


  /**
   *  Sets the questionId attribute of the SurveyAnswer object
   *
   *@param  tmp  The new questionId value
   */
  public void setQuestionId(int tmp) {
    this.questionId = tmp;
  }


  /**
   *  Sets the comments attribute of the SurveyAnswer object
   *
   *@param  tmp  The new comments value
   */
  public void setComments(String tmp) {
    this.comments = tmp;
  }


  /**
   *  Sets the quantAns attribute of the SurveyAnswer object
   *
   *@param  tmp  The new quantAns value
   */
  public void setQuantAns(int tmp) {
    this.quantAns = tmp;
  }


  /**
   *  Sets the textAns attribute of the SurveyAnswer object
   *
   *@param  tmp  The new textAns value
   */
  public void setTextAns(String tmp) {
    this.textAns = tmp;
  }


  /**
   *  Sets the questionId attribute of the SurveyAnswer object
   *
   *@param  tmp  The new questionId value
   */
  public void setQuestionId(String tmp) {
    this.questionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quantAns attribute of the SurveyAnswer object
   *
   *@param  tmp  The new quantAns value
   */
  public void setQuantAns(String tmp) {
    this.quantAns = Integer.parseInt(tmp);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  enteredBy         Description of the Parameter
   *@param  responseId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db, int responseId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "INSERT INTO active_survey_answers " +
      "(response_id, question_id, comments, quant_ans, text_ans) " +
      "VALUES " +
      "(?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, responseId);
    pst.setInt(++i, questionId);
    pst.setString(++i, comments);
    pst.setInt(++i, quantAns);
    pst.setString(++i, textAns);
    pst.execute();
    pst.close();

    //id = DatabaseUtils.getCurrVal(db, "active_survey_ans_answer_id_seq");
    if (this.quantAns > -1) {
      this.updateSurveyAverage(db);
      this.updateAnswerTotal(db);
    }
  }

  private int updateSurveyAverage(Connection db) throws SQLException {
    if (questionId == -1) {
      throw new SQLException("Question ID not specified.");
    }

    double thisAverage = 0;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = 
      "SELECT avg(quant_ans) as av " +
      "FROM active_survey_answers s " +
      "WHERE question_id = ? ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, this.getQuestionId());
    rs = pst.executeQuery();
    if (rs.next()) {
      thisAverage = rs.getDouble("av");
    }
    rs.close();
    
    pst = db.prepareStatement(
      "UPDATE active_survey_questions " +
      "SET average = ? " +
      "WHERE question_id = ? ");
    int i = 0;
    pst.setDouble(++i, thisAverage);
    pst.setInt(++i, this.getQuestionId());
    pst.execute();
    pst.close();

    return 1;
  }
  
  private boolean updateAnswerTotal(Connection db) throws SQLException {
    PreparedStatement pst = null;
    String sql = 
      "UPDATE active_survey_questions " +
      "SET total" + this.getQuantAns() + " = total" + this.getQuantAns() + " + 1 " +
      "WHERE question_id = ? ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, this.getQuestionId());
    pst.execute();
    pst.close();
    return true;
  }


  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM active_survey_answers " +
        "WHERE answer_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }

}

