//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import javax.servlet.http.*;

/**
 *  Description of the Class
 *
 *@author
 *@created    November 1, 2002
 *@version    $Id$
 */
public class ActiveSurveyQuestionList extends ArrayList {

  private int id = -1;
  private int activeSurveyId = -1;


  /**
   *  Constructor for the ActiveSurveyQuestionList object
   */
  public ActiveSurveyQuestionList() { }


  /**
   *  Builds a ActiveSurveyQuestionList from a SurveyQuestionList (copy)
   *
   *@param  inactiveQuestions  Description of the Parameter
   */
  public ActiveSurveyQuestionList(SurveyQuestionList inactiveQuestions) {
    Iterator i = inactiveQuestions.iterator();
    while (i.hasNext()) {
      SurveyQuestion inactiveQuestion = (SurveyQuestion) i.next();
      ActiveSurveyQuestion thisQuestion = new ActiveSurveyQuestion(inactiveQuestion);
      thisQuestion.setActiveSurveyId(activeSurveyId);
      this.add(thisQuestion);
    }
  }


  /**
   *  Gets the object attribute of the ActiveSurveyQuestionList object
   *
   *@param  rs                Description of the Parameter
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public ActiveSurveyQuestion getObject(ResultSet rs) throws SQLException {
    ActiveSurveyQuestion thisQuestion = new ActiveSurveyQuestion(rs);
    return thisQuestion;
  }


  /**
   *  Gets the id attribute of the ActiveSurveyQuestionList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ActiveSurveyQuestionList object
   *
   *@param  id  The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the id attribute of the ActiveSurveyQuestionList object
   *
   *@param  id  The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   *  Gets the activeSurveyId attribute of the ActiveSurveyQuestionList object
   *
   *@return    The activeSurveyId value
   */
  public int getActiveSurveyId() {
    return activeSurveyId;
  }


  /**
   *  Sets the activeSurveyId attribute of the ActiveSurveyQuestionList object
   *
   *@param  surveyId  The new activeSurveyId value
   */
  public void setActiveSurveyId(int surveyId) {
    this.activeSurveyId = surveyId;
  }


  /**
   *  Sets the activeSurveyId attribute of the ActiveSurveyQuestionList object
   *
   *@param  surveyId  The new activeSurveyId value
   */
  public void setActiveSurveyId(String surveyId) {
    this.activeSurveyId = Integer.parseInt(surveyId);
  }


  /**
   *  Build Question & associated ItemList if any
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      ActiveSurveyQuestion thisQuestion = this.getObject(rs);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ActiveSurveyQuestionList -- > Adding Question " + thisQuestion.getDescription());
      }
      if (thisQuestion.getType() == SurveyQuestion.ITEMLIST) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ActiveSurveyQuestionList -- > Building Items for " + thisQuestion.getDescription());
        }
        ActiveSurveyQuestionItemList itemList = new ActiveSurveyQuestionItemList();
        itemList.setQuestionId(thisQuestion.getId());
        itemList.buildList(db);
        thisQuestion.setItemList(itemList);
      }
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
        "WHERE sq.active_survey_id = ? " +
        "ORDER BY position ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, activeSurveyId);
    ResultSet rs = pst.executeQuery();
    return rs;
  }

}

