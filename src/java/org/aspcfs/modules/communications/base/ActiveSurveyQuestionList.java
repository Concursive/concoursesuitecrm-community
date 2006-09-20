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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author
 * @version $Id: ActiveSurveyQuestionList.java,v 1.6 2003/01/14 16:11:33
 *          akhi_m Exp $
 * @created November 1, 2002
 */
public class ActiveSurveyQuestionList extends ArrayList {

  private int id = -1;
  private int activeSurveyId = -1;
  protected PagedListInfo pagedListInfo = null;
  private boolean buildResults = false;


  /**
   * Constructor for the ActiveSurveyQuestionList object
   */
  public ActiveSurveyQuestionList() {
  }


  /**
   * Builds a ActiveSurveyQuestionList from a SurveyQuestionList (copy)
   *
   * @param inactiveQuestions Description of the Parameter
   */
  public ActiveSurveyQuestionList(SurveyQuestionList inactiveQuestions) {
    Iterator i = inactiveQuestions.iterator();
    while (i.hasNext()) {
      SurveyQuestion inactiveQuestion = (SurveyQuestion) i.next();
      ActiveSurveyQuestion thisQuestion = new ActiveSurveyQuestion(
          inactiveQuestion);
      thisQuestion.setActiveSurveyId(activeSurveyId);
      this.add(thisQuestion);
    }
  }


  /**
   * Gets the object attribute of the ActiveSurveyQuestionList object
   *
   * @param rs Description of the Parameter
   * @return The object value
   * @throws SQLException Description of the Exception
   */
  public ActiveSurveyQuestion getObject(ResultSet rs) throws SQLException {
    ActiveSurveyQuestion thisQuestion = new ActiveSurveyQuestion(rs);
    return thisQuestion;
  }


  /**
   * Gets the id attribute of the ActiveSurveyQuestionList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the ActiveSurveyQuestionList object
   *
   * @param id The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * Sets the id attribute of the ActiveSurveyQuestionList object
   *
   * @param id The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   * Sets the pagedListInfo attribute of the ActiveSurveyQuestionList object
   *
   * @param pagedListInfo The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   * Sets the buildResults attribute of the ActiveSurveyQuestionList object
   *
   * @param buildResults The new buildResults value
   */
  public void setBuildResults(boolean buildResults) {
    this.buildResults = buildResults;
  }


  /**
   * Gets the buildResults attribute of the ActiveSurveyQuestionList object
   *
   * @return The buildResults value
   */
  public boolean getBuildResults() {
    return buildResults;
  }


  /**
   * Gets the activeSurveyId attribute of the ActiveSurveyQuestionList object
   *
   * @return The activeSurveyId value
   */
  public int getActiveSurveyId() {
    return activeSurveyId;
  }


  /**
   * Sets the activeSurveyId attribute of the ActiveSurveyQuestionList object
   *
   * @param surveyId The new activeSurveyId value
   */
  public void setActiveSurveyId(int surveyId) {
    this.activeSurveyId = surveyId;
  }


  /**
   * Sets the activeSurveyId attribute of the ActiveSurveyQuestionList object
   *
   * @param surveyId The new activeSurveyId value
   */
  public void setActiveSurveyId(String surveyId) {
    this.activeSurveyId = Integer.parseInt(surveyId);
  }


  /**
   * Build Question & associated ItemList if any
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */

  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM active_survey_questions sq " +
        "WHERE sq.active_survey_id > -1 ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
            sqlFilter.toString());
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      pagedListInfo.setDefaultSort("sq.position", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY sq." + DatabaseUtils.addQuotes(db, "position")+ " ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "sq.* " +
        "FROM active_survey_questions sq " +
        "WHERE sq.active_survey_id > -1 ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());

    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      ActiveSurveyQuestion thisQuestion = this.getObject(rs);
      this.add(thisQuestion);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ActiveSurveyQuestionList -- > Added Question " + thisQuestion.getDescription());
      }
    }
    rs.close();
    pst.close();

    //build items & comments
    Iterator thisList = this.iterator();
    while (thisList.hasNext()) {
      ActiveSurveyQuestion thisQuestion = (ActiveSurveyQuestion) thisList.next();
      int type = thisQuestion.getType();
      if (type == SurveyQuestion.ITEMLIST) {
        ActiveSurveyQuestionItemList itemList = new ActiveSurveyQuestionItemList();
        itemList.setQuestionId(thisQuestion.getId());
        itemList.buildList(db);
        thisQuestion.setItemList(itemList);
      } else if (buildResults && (type == SurveyQuestion.QUANT_COMMENTS || type == SurveyQuestion.OPEN_ENDED)) {
        SurveyAnswerList answerList = new SurveyAnswerList();
        answerList.setQuestionId(thisQuestion.getId());
        answerList.setHasComments(Constants.TRUE);
        answerList.setItemsPerPage(5);
        answerList.setLastAnswers(true);
        answerList.buildList(db);
        thisQuestion.setAnswerList(answerList);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (activeSurveyId != -1) {
      sqlFilter.append("AND sq.active_survey_id = ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (activeSurveyId != -1) {
      pst.setInt(++i, activeSurveyId);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param contactId  Description of the Parameter
   * @param responseId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResponse(Connection db, int contactId, int responseId) throws SQLException {
    Iterator thisList = this.iterator();
    while (thisList.hasNext()) {
      ActiveSurveyQuestion thisQuestion = (ActiveSurveyQuestion) thisList.next();
      SurveyAnswerList answers = new SurveyAnswerList();
      answers.setQuestionId(thisQuestion.getId());
      answers.setContactId(contactId);
      answers.setResponseId(responseId);
      answers.buildList(db);
      thisQuestion.setAnswerList(answers);
    }
  }

}

