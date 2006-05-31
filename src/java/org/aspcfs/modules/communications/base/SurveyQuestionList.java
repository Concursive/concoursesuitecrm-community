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

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * An array of SurveyQuestion objects
 *
 * @author matt rajkowski
 * @version $Id: SurveyQuestionList.java,v 1.1 2002/08/27 19:28:31 mrajkowski
 *          Exp $
 * @created August 13, 2002
 */
public class SurveyQuestionList extends ArrayList {

  private int id = -1;
  private int surveyId = -1;
  private int questionId = -1;


  /**
   * Constructor for the SurveyQuestionList object
   */
  public SurveyQuestionList() {
  }


  /**
   * Constructor for the SurveyQuestionList object
   *
   * @param request Description of the Parameter
   */
  public SurveyQuestionList(HttpServletRequest request) {
    String question = null;
    String type = null;
    if (request.getParameter("questionId") != null) {
      questionId = Integer.parseInt(request.getParameter("questionId"));
    }
    if ((question = request.getParameter("questionText")) != null) {
      type = request.getParameter("type");
      if ((!question.equals("")) && (type != null)) {
        if (Integer.parseInt(type) > 0) {
          SurveyQuestion thisItem = new SurveyQuestion(request);
          thisItem.setId(questionId);
          this.add(thisItem);
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                " SurveyQuestionList -- > Added Question " + questionId + ":" + request.getParameter(
                    "questionText"));
          }
        }
      }
    }
  }


  /**
   * Gets the object attribute of the SurveyQuestionList object
   *
   * @param rs Description of the Parameter
   * @return The object value
   * @throws SQLException Description of the Exception
   */
  public SurveyQuestion getObject(ResultSet rs) throws SQLException {
    SurveyQuestion thisItem = new SurveyQuestion(rs);
    return thisItem;
  }


  /**
   * Gets the id attribute of the SurveyQuestionList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the SurveyQuestionList object
   *
   * @param id The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * Sets the id attribute of the SurveyQuestionList object
   *
   * @param id The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   * Sets the questionId attribute of the SurveyQuestionList object
   *
   * @param questionId The new questionId value
   */
  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }


  /**
   * Gets the questionId attribute of the SurveyQuestionList object
   *
   * @return The questionId value
   */
  public int getQuestionId() {
    return questionId;
  }


  /**
   * Gets the surveyId attribute of the SurveyQuestionList object
   *
   * @return The surveyId value
   */
  public int getSurveyId() {
    return surveyId;
  }


  /**
   * Sets the surveyId attribute of the SurveyQuestionList object
   *
   * @param surveyId The new surveyId value
   */
  public void setSurveyId(int surveyId) {
    this.surveyId = surveyId;
  }


  /**
   * Sets the surveyId attribute of the SurveyQuestionList object
   *
   * @param surveyId The new surveyId value
   */
  public void setSurveyId(String surveyId) {
    this.surveyId = Integer.parseInt(surveyId);
  }


  /**
   * Gets the question attribute of the SurveyQuestionList object
   *
   * @return The question value
   */
  public SurveyQuestion getSurveyQuestion() {
    Iterator thisList = this.iterator();
    SurveyQuestion question = new SurveyQuestion();
    if (thisList.hasNext()) {
      while (thisList.hasNext()) {
        SurveyQuestion thisQuestion = (SurveyQuestion) thisList.next();
        if (thisQuestion.getId() == questionId) {
          question = thisQuestion;
        }
      }
    }
    return question;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param surveyId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void delete(Connection db, int surveyId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM survey_questions WHERE survey_id = ?");
    pst.setInt(1, surveyId);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param surveyId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void process(Connection db, int surveyId) throws SQLException {
    SurveyQuestion question = getQuestion(questionId);
    if (question != null) {
      if (questionId == -1) {
        question.insert(db, surveyId);
      } else {
        question.update(db, surveyId);
      }
    }
  }


  /**
   * Gets the question attribute of the SurveyQuestionList object
   *
   * @param questionId Description of the Parameter
   * @return The question value
   */
  public SurveyQuestion getQuestion(int questionId) {
    Iterator thisList = this.iterator();
    SurveyQuestion question = null;
    while (thisList.hasNext()) {
      SurveyQuestion thisQuestion = (SurveyQuestion) thisList.next();
      if (thisQuestion.getId() == questionId) {
        question = thisQuestion;
      }
    }
    return question;
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param thisId    Description of the Parameter
   * @param direction Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateOrder(Connection db, int thisId, String direction) throws SQLException {
    SurveyQuestion thisQuestion = getQuestion(thisId);
    SurveyQuestion swapQuestion = null;
    if (direction.equalsIgnoreCase("U")) {
      if ((this.indexOf(thisQuestion)) > 0) {
        swapQuestion = (SurveyQuestion) this.get(
            this.indexOf(thisQuestion) - 1);
      }
    } else {
      if (this.indexOf(thisQuestion) + 1 < this.size()) {
        swapQuestion = (SurveyQuestion) this.get(
            this.indexOf(thisQuestion) + 1);
      }
    }
    if (swapQuestion != null) {
      int tmp = thisQuestion.getPosition();
      thisQuestion.setPosition(swapQuestion.getPosition());
      swapQuestion.setPosition(tmp);
      try {
        thisQuestion.update(db, surveyId);
        swapQuestion.update(db, surveyId);
      } catch (SQLException e) {
        throw new SQLException(e.getMessage());
      }
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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
    //build items
    Iterator thisList = this.iterator();
    while (thisList.hasNext()) {
      SurveyQuestion thisQuestion = (SurveyQuestion) thisList.next();
      if (thisQuestion.getType() == SurveyQuestion.ITEMLIST) {
        ItemList itemList = new ItemList();
        itemList.setQuestionId(thisQuestion.getId());
        itemList.buildList(db);
        thisQuestion.setItemList(itemList);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db  Description of the Parameter
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    String sql =
        "SELECT sq.* " +
            "FROM survey_questions sq " +
            "WHERE sq.survey_id = ? " +
            "ORDER BY sq.\"position\" ";
    pst = db.prepareStatement(sql);
    int i = 0;
    pst.setInt(++i, surveyId);
    return pst.executeQuery();
  }
}

