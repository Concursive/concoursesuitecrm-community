//Copyright 2001-2002 Dark Horse Ventures
package org.aspcfs.modules.communications.base;

import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import javax.servlet.http.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    November 1, 2002
 *@version    $Id$
 */
public class SurveyAnswerItem {
  private int id = -1;
  private int answerId = -1;
  private String comments = null;
  private ActiveSurveyQuestionItem item = null;


  /**
   *  Constructor for the SurveyAnswerItem object
   */
  public SurveyAnswerItem() { }


  /**
   *  Constructor for the SurveyAnswerItem object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public SurveyAnswerItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  request    Description of the Parameter
   *@param  parseItem  Description of the Parameter
   */
  public void buildRecord(HttpServletRequest request, String parseItem) {
    if (request.getParameter(parseItem + "id") != null) {
      this.setId(request.getParameter(parseItem + "id"));
    }
  }


  /**
   *  Sets the id attribute of the SurveyAnswerItem object
   *
   *@param  id  The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the answerId attribute of the SurveyAnswerItem object
   *
   *@param  answerId  The new answerId value
   */
  public void setAnswerId(int answerId) {
    this.answerId = answerId;
  }


  /**
   *  Sets the comments attribute of the SurveyAnswerItem object
   *
   *@param  comments  The new comments value
   */
  public void setComments(String comments) {
    this.comments = comments;
  }


  /**
   *  Sets the item attribute of the SurveyAnswerItem object
   *
   *@param  item  The new item value
   */
  public void setItem(ActiveSurveyQuestionItem item) {
    this.item = item;
  }


  /**
   *  Gets the item attribute of the SurveyAnswerItem object
   *
   *@return    The item value
   */
  public ActiveSurveyQuestionItem getItem() {
    return item;
  }


  /**
   *  Gets the comments attribute of the SurveyAnswerItem object
   *
   *@return    The comments value
   */
  public String getComments() {
    return comments;
  }


  /**
   *  Gets the answerId attribute of the SurveyAnswerItem object
   *
   *@return    The answerId value
   */
  public int getAnswerId() {
    return answerId;
  }


  /**
   *  Gets the id attribute of the SurveyAnswerItem object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }



  /**
   *  Sets the id attribute of the SurveyAnswerItem object
   *
   *@param  id  The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildItemDetails(Connection db) throws SQLException {
    if (id < 1) {
      throw new SQLException("Answer ID not specified.");
    }
    item = new ActiveSurveyQuestionItem(db, id);
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("item_id"));
    this.setAnswerId(rs.getInt("answer_id"));
    this.setComments(rs.getString("comments"));
  }


  /**
   *  Constructor for the SurveyAnswer object
   *
   *@param  db                Description of the Parameter
   *@param  passedId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public SurveyAnswerItem(Connection db, int passedId) throws SQLException {
    if (passedId < 1) {
      throw new SQLException("Answer ID not specified.");
    }

    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement("SELECT * " +
        "FROM active_survey_answer_items s " +
        "WHERE answer_id = ? ");
    pst.setInt(1, passedId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Question AnswerItem record not found.");
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  thisAnswerId      Description of the Parameter
   *@param  questionId        Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db, int thisAnswerId, int questionId) throws SQLException {
    if (thisAnswerId == -1) {
      throw new SQLException("Answer ID not specified");
    }
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO active_survey_answer_items " +
          "(item_id, answer_id, comments) " +
          "VALUES " +
          "(?, ?, ?) ");
      int i = 0;
      pst.setInt(++i, this.getId());
      pst.setInt(++i, thisAnswerId);
      pst.setString(++i, comments);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("SurveyAnswerItem -- > Insert : " + pst.toString());
      }
      pst.execute();
      pst.close();
      updateSurveyAverage(db, questionId);
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  questionId        Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int updateSurveyAverage(Connection db, int questionId) throws SQLException {
    if (questionId == -1) {
      throw new SQLException("Question ID not specified.");
    }

    int count = 0;
    int i = 0;
    try {
      PreparedStatement pst = null;
      i = 0;
      pst = db.prepareStatement(
          "UPDATE active_survey_answer_avg " +
          "SET total = total + 1 " +
          "WHERE question_id = ? AND item_id =? ");

      pst.setInt(++i, questionId);
      pst.setInt(++i, this.getId());
      if (System.getProperty("DEBUG") != null) {
        System.out.println("SurveyAnswerItem -- > Update Avg : " + pst.toString());
      }
      count = pst.executeUpdate();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return count;
  }

}

