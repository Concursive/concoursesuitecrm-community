//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.webutils.PagedListInfo;

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
  protected PagedListInfo pagedListInfo = null;


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
   *  Sets the pagedListInfo attribute of the ActiveSurveyQuestionList object
   *
   *@param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
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
      pst.close();
      rs.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
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
      sqlOrder.append("ORDER BY sq.position ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append("sq.* " +
        "FROM active_survey_questions sq " +
        "WHERE sq.active_survey_id > -1 ");

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    
    items = prepareFilter(pst);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ActiveSurveyQuestionList Query --> " + pst.toString());
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }

    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      ActiveSurveyQuestion thisQuestion = this.getObject(rs);
      this.add(thisQuestion);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ActiveSurveyQuestionList -- > Added Question " + thisQuestion.getDescription());
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
      }else if (type == SurveyQuestion.QUANT_COMMENTS || type == SurveyQuestion.OPEN_ENDED) {
        thisQuestion.buildComments(db,5);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
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
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (activeSurveyId != -1) {
      pst.setInt(++i, activeSurveyId);
    }
    return i;
  }

}

