//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import javax.servlet.http.*;

/**
 *  An array of SurveyQuestion objects
 *
 *@author     matt rajkowski
 *@created    August 13, 2002
 *@version    $Id$
 */
public class SurveyQuestionList extends ArrayList {

  private int id = -1;
  private int surveyId = -1;


  /**
   *  Constructor for the SurveyQuestionList object
   */
  public SurveyQuestionList() { }


  /**
   *  Constructor for the SurveyQuestionList object
   *
   *@param  request  Description of the Parameter
   */
  public SurveyQuestionList(HttpServletRequest request) {
    int i = 0;
    String thisId = null;
    while ((thisId = request.getParameter("questions" + (++i) + "id")) != null) {
      SurveyQuestion thisItem = new SurveyQuestion();
      thisItem.setId(Integer.parseInt(thisId));
      thisItem.setDescription(request.getParameter("questions" + i + "text"));
      this.add(thisItem);
    }
  }


  /**
   *  Gets the object attribute of the SurveyQuestionList object
   *
   *@param  rs                Description of the Parameter
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public SurveyQuestion getObject(ResultSet rs) throws SQLException {
    SurveyQuestion thisItem = new SurveyQuestion(rs);
    return thisItem;
  }


  /**
   *  Gets the id attribute of the SurveyQuestionList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the SurveyQuestionList object
   *
   *@param  id  The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the id attribute of the SurveyQuestionList object
   *
   *@param  id  The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   *  Gets the surveyId attribute of the SurveyQuestionList object
   *
   *@return    The surveyId value
   */
  public int getSurveyId() {
    return surveyId;
  }


  /**
   *  Sets the surveyId attribute of the SurveyQuestionList object
   *
   *@param  surveyId  The new surveyId value
   */
  public void setSurveyId(int surveyId) {
    this.surveyId = surveyId;
  }


  /**
   *  Sets the surveyId attribute of the SurveyQuestionList object
   *
   *@param  surveyId  The new surveyId value
   */
  public void setSurveyId(String surveyId) {
    this.surveyId = Integer.parseInt(surveyId);
  }
  
  public static void delete(Connection db, int surveyId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "DELETE FROM survey_questions WHERE survey_id = ?");
    pst.setInt(1, surveyId);
    pst.execute();
    pst.close();
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
      SurveyQuestion thisItem = this.getObject(rs);
      this.add(thisItem);
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
    String sql =
        "SELECT sq.* " +
        "FROM survey_questions sq " +
        "WHERE sq.survey_id = ? ";
    pst = db.prepareStatement(sql);
    int i = 0;
    pst.setInt(++i, surveyId);
    ResultSet rs = pst.executeQuery();
    return rs;
  }
}

