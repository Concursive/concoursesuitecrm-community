//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Contains a list of survey answers for a campaign
 *
 *@author     chris price
 *@created    August 13, 2002
 *@version    $Id: SurveyAnswerList.java,v 1.4 2002/08/27 19:28:31 mrajkowski
 *      Exp $
 */
public class SurveyAnswerList extends Vector {

  private int questionId = -1;
  private int hasComments = -1;


  /**
   *  Constructor for the SurveyAnswerList object
   */
  public SurveyAnswerList() { }

  public SurveyAnswerList(Connection db, int questionId) throws SQLException {
    this.questionId = questionId;
    buildList(db);
  }

  /**
   *  Constructor for the SurveyAnswerList object
   *
   *@param  request  Description of the Parameter
   */
  public SurveyAnswerList(HttpServletRequest request) {
    int i = 0;
    while (request.getParameter("quest" + (++i) + "id") != null) {
      SurveyAnswer thisAnswer = new SurveyAnswer();
      thisAnswer.buildRecord(request, i);
      this.addElement(thisAnswer);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Added an answer: " + thisAnswer.getQuestionId());
      }
    }
  }


  /**
   *  Gets the questionId attribute of the SurveyAnswerList object
   *
   *@return    The questionId value
   */
  public int getQuestionId() {
    return questionId;
  }


  /**
   *  Sets the questionId attribute of the SurveyAnswerList object
   *
   *@param  questionId  The new questionId value
   */
  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }


  /**
   *  Sets the hasComments attribute of the SurveyAnswerList object
   *
   *@param  tmp  The new hasComments value
   */
  public void setHasComments(int tmp) {
    this.hasComments = tmp;
  }


  /**
   *  Gets the hasComments attribute of the SurveyAnswerList object
   *
   *@return    The hasComments value
   */
  public int getHasComments() {
    return hasComments;
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
      SurveyAnswer thisAnswer = this.getObject(db, rs);
      this.add(thisAnswer);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    Iterator ans = this.iterator();
    while (ans.hasNext()) {
      SurveyAnswer thisAnswer = (SurveyAnswer) ans.next();
      thisAnswer.setContactId(db);
      thisAnswer.buildItems(db, thisAnswer.getId());
    }
  }


  /**
   *  Gets the object attribute of the SurveyAnswerList object
   *
   *@param  rs                Description of the Parameter
   *@param  db                Database connection needed for related items
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public SurveyAnswer getObject(Connection db, ResultSet rs) throws SQLException {
    SurveyAnswer thisAnswer = new SurveyAnswer(rs);
    thisAnswer.buildItems(db, thisAnswer.getId());
    return thisAnswer;
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
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT sa.* " +
        "FROM active_survey_answers sa " +
        "WHERE sa.question_id = ? ");
    if (hasComments > -1) {
      if (hasComments == Constants.TRUE) {
        sql.append("AND comments <> '' ");
      } else {
        sql.append("AND comments = '' ");
      }
    }
    sql.append("ORDER BY response_id, question_id ");
    pst = db.prepareStatement(sql.toString());
    pst.setInt(1, questionId);
    ResultSet rs = pst.executeQuery();
    return rs;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  responseId        Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db, int responseId) throws SQLException {
    Iterator ans = this.iterator();
    while (ans.hasNext()) {
      SurveyAnswer thisAnswer = (SurveyAnswer) ans.next();
      thisAnswer.insert(db, responseId);
    }
    return true;
  }
}

