//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import javax.servlet.http.*;

/**
 *  Maintains an Array of Items for a Active Survey Question.
 *
 *@author     akhi_m
 *@created    November 1, 2002
 *@version    $Id$
 */
public class ActiveSurveyQuestionItemList extends ArrayList {

  private int questionId = -1;


  /**
   *  Constructor for the ItemList object
   */
  public ActiveSurveyQuestionItemList() { }


  /**
   *  Builds ActiveSurveyItemList from a ItemList object (copy)
   *
   *@param  items  Description of the Parameter
   */
  public ActiveSurveyQuestionItemList(ItemList items) {
    if (items != null) {
      Iterator thisList = items.iterator();
      while (thisList.hasNext()) {
        Item inactiveItem = (Item) thisList.next();
        ActiveSurveyQuestionItem thisItem = new ActiveSurveyQuestionItem(inactiveItem);
        this.add(thisItem);
      }
    }
  }


  /**
   *  Gets the questionId attribute of the ActiveSurveyQuestionItemList object
   *
   *@return    The questionId value
   */
  public int getQuestionId() {
    return questionId;
  }


  /**
   *  Gets the object attribute of the ActiveSurveyQuestionItemList object
   *
   *@param  rs                Description of the Parameter
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public ActiveSurveyQuestionItem getObject(ResultSet rs) throws SQLException {
    ActiveSurveyQuestionItem thisItem = new ActiveSurveyQuestionItem(rs);
    return thisItem;
  }


  /**
   *  Sets the questionId attribute of the ActiveSurveyQuestionItemList object
   *
   *@param  questionId  The new questionId value
   */
  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }


  /**
   *  Sets the questionId attribute of the ActiveSurveyQuestionItemList object
   *
   *@param  questionId  The new questionId value
   */
  public void setQuestionId(String questionId) {
    this.questionId = Integer.parseInt(questionId);
  }



  /**
   *  Gets the question attribute of the ActiveSurveyQuestionItemList object
   *
   *@param  itemId      Description of the Parameter
   *@return             The question value
   */
  public ActiveSurveyQuestionItem getItem(int itemId) {
    Iterator thisList = this.iterator();
    ActiveSurveyQuestionItem item = null;
    while (thisList.hasNext()) {
      ActiveSurveyQuestionItem thisItem = (ActiveSurveyQuestionItem) thisList.next();
      if (thisItem.getId() == itemId) {
        item = thisItem;
      }
    }
    return item;
  }


  /**
   *  Description of the Method
   *
   *@param  db          Description of the Parameter
   *@param  questionId  Description of the Parameter
   *@return             Description of the Return Value
   */
  public boolean updateResponse(Connection db, int questionId) throws SQLException{
    PreparedStatement pst = null;
    ResultSet rs = null;
    try {
      pst = db.prepareStatement(
          "SELECT item_id,total " +
          "FROM active_survey_answer_itemavg " +
          "WHERE question_id = ? ");
      int i = 0;
      pst.setInt(++i, questionId);
      rs = pst.executeQuery();
      while (rs.next()) {
        ActiveSurveyQuestionItem thisItem = getItem(rs.getInt("item_id"));
        if (thisItem != null) {
          thisItem.setTotalResponse(rs.getInt("total"));
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.toString());
    }
    return true;
  }


  /**
   *  Delete all Items for a Active Survey Question
   *
   *@param  db                Description of the Parameter
   *@param  questionId        Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static boolean delete(Connection db, int questionId) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM active_survey_items WHERE question_id = ?");
      pst.setInt(1, questionId);
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.toString());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
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
      ActiveSurveyQuestionItem thisItem = this.getObject(rs);
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
        "FROM active_survey_items sq " +
        "WHERE question_id = ? ";
    pst = db.prepareStatement(sql);
    int i = 0;
    pst.setInt(++i, questionId);
    ResultSet rs = pst.executeQuery();
    return rs;
  }
}
