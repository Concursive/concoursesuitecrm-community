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
  private int surveyId = -1;


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
    //this.setTextAns(request.getParameter("address" + parseItem + "country"));
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
   *  Gets the surveyId attribute of the SurveyAnswer object
   *
   *@return    The surveyId value
   */
  public int getSurveyId() {
    return surveyId;
  }


  /**
   *  Sets the surveyId attribute of the SurveyAnswer object
   *
   *@param  surveyId  The new surveyId value
   */
  public void setSurveyId(int surveyId) {
    this.surveyId = surveyId;
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
    this.setId(rs.getInt("id"));
    this.setQuestionId(rs.getInt("question_id"));
    this.setComments(rs.getString("comments"));
    this.setQuantAns(rs.getInt("quant_ans"));
    this.setTextAns(rs.getString("text_ans"));
    this.setSurveyId(rs.getInt("survey_id"));
    //this.setEnteredBy(rs.getInt("enteredby"));
  }


  /**
   *  Constructor for the SurveyAnswer object
   *
   *@param  db                Description of the Parameter
   *@param  passedId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public SurveyAnswer(Connection db, String passedId) throws SQLException {
    if (passedId == null) {
      throw new SQLException("Question Answer ID not specified.");
    }

    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * " +
        "FROM survey_answer s " +
        "WHERE id = " + passedId + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Question Answer record not found.");
    }
    rs.close();
    st.close();
  }


  /**
   *  Sets the newAverage attribute of the SurveyAnswer object
   *
   *@param  db                The new newAverage value
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int setNewAverage(Connection db) throws SQLException {
    if (questionId == -1) {
      throw new SQLException("Question ID not specified.");
    }

    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT avg(quant_ans) as av " +
        "FROM survey_answer s " +
        "WHERE question_id = " + this.getQuestionId() + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());

    if (rs.next()) {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE survey_item " +
          "SET average = ? " +
          "WHERE id = ? ");
      int i = 0;
      pst.setDouble(++i, rs.getDouble("av"));
      pst.setInt(++i, this.getQuestionId());
      pst.execute();
      pst.close();
    }

    //newAvg = new Double((double)total/rs.getFetchSize());
    //System.out.println(newAvg + " " + rs.getFetchSize());
    //newAverage = (Double)(newAverage/((Double)count));

    rs.close();
    st.close();

    return 1;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void updateTotal(Connection db) throws SQLException {
    if (questionId == -1) {
      throw new SQLException("Question ID not specified.");
    }

    PreparedStatement pst = db.prepareStatement(
        "UPDATE survey_item " +
        "SET total" + quantAns + " = total" + quantAns + "+1 " +
        "WHERE id = ? ");
    int i = 0;
    pst.setInt(++i, this.getQuestionId());
    System.out.println(pst.toString());
    pst.execute();
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
   *@param  surveyId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db, int enteredBy, int surveyId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO survey_answer " +
        "(question_id, comments, quant_ans, text_ans, enteredBy, survey_id) " +
        "VALUES " +
        "(?, ?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, questionId);
    pst.setString(++i, comments);
    pst.setInt(++i, quantAns);
    pst.setString(++i, textAns);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, surveyId);

    pst.execute();
    pst.close();

    //id = DatabaseUtils.getCurrVal(db, "aurvey_answer_id_seq");
    setNewAverage(db);
    updateTotal(db);
  }


  /**
   *  public void update(Connection db, int modifiedBy) throws SQLException {
   *  PreparedStatement pst = db.prepareStatement( "UPDATE contact_emailaddress
   *  " + "SET emailaddress_type = ?, email = ?, modifiedby = ?, " + "modified =
   *  CURRENT_TIMESTAMP " + "WHERE emailaddress_id = ? "); int i = 0;
   *  pst.setInt(++i, this.getType()); pst.setString(++i, this.getEmail());
   *  pst.setInt(++i, modifiedBy); pst.setInt(++i, this.getId()); pst.execute();
   *  pst.close(); }
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */

  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM survey_answer " +
        "WHERE id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }

}

