//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.utils.DatabaseUtils;
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
  protected PagedListInfo pagedListInfo = null;


  /**
   *  Constructor for the SurveyAnswerList object
   */
  public SurveyAnswerList() { }


  /**
   *  Constructor for the SurveyAnswerList object
   *
   *@param  db                Description of the Parameter
   *@param  questionId        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
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
   *  Sets the pagedListInfo attribute of the SurveyAnswerList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
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
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM active_survey_answers sa " +
        "WHERE sa.question_id > -1 ");

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
            sqlFilter.toString() +
            "AND sa.comments < ? ");
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
      pagedListInfo.setDefaultSort("sr.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY response_id, question_id ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append("sa.* " +
        "FROM active_survey_answers sa, active_survey_responses sr " +
        "WHERE sa.question_id > -1 AND sa.response_id = sr.response_id "
        );
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
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
      SurveyAnswer thisAnswer = this.getObject(db, rs);
      this.add(thisAnswer);
    }
    rs.close();
    pst.close();
    Iterator ans = this.iterator();
    while (ans.hasNext()) {
      SurveyAnswer thisAnswer = (SurveyAnswer) ans.next();
      thisAnswer.setContactId(db);
      thisAnswer.buildItems(db, thisAnswer.getId());
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

    if (questionId != -1) {
      sqlFilter.append("AND sa.question_id = ? ");
    }

    if (hasComments > -1) {
      if (hasComments == Constants.TRUE) {
        sqlFilter.append("AND sa.comments <> '' ");
      } else {
        sqlFilter.append("AND sa.comments = '' ");
      }
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

    if (questionId != -1) {
      pst.setInt(++i, questionId);
    }
    return i;
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

