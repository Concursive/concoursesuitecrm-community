package org.aspcfs.modules.communications.base;

import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import java.util.ArrayList;

/**
 *  Item for an Active Survey Question.
 *
 *@author     akhi_m
 *@created    November 1, 2002
 *@version    $Id: ActiveSurveyQuestionItem.java,v 1.4 2003/01/14 16:11:34
 *      akhi_m Exp $
 */
public class ActiveSurveyQuestionItem {
  private int id = -1;
  private String description = null;
  private int questionId = -1;
  private int type = -1;
  private int totalResponse = 0;


  /**
   *  Constructor for the ActiveSurveyQuestionItem object
   */
  public ActiveSurveyQuestionItem() { }


  /**
   *  Constructor for the ActiveSurveyQuestionItem object
   *
   *@param  thisItem  Description of the Parameter
   */
  public ActiveSurveyQuestionItem(Item thisItem) {
    this.id = thisItem.getId();
    this.description = thisItem.getDescription();
    this.questionId = thisItem.getQuestionId();
    this.type = thisItem.getType();
  }


  /**
   *  Constructor for the ActiveSurveyQuestionItem object
   *
   *@param  db                Description of the Parameter
   *@param  itemId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ActiveSurveyQuestionItem(Connection db, int itemId) throws SQLException {
    if (itemId == -1) {
      throw new SQLException("Item ID not specified");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT si.item_id, si.question_id, si.type, si.description " +
        "FROM active_survey_items si " +
        "WHERE item_id = ? ");
    int i = 0;
    pst.setInt(++i, itemId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Constructor for the ActiveSurveyQuestionItem object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ActiveSurveyQuestionItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Gets the id attribute of the ActiveSurveyQuestionItem object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the description attribute of the ActiveSurveyQuestionItem object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the id attribute of the ActiveSurveyQuestionItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActiveSurveyQuestionItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the description attribute of the ActiveSurveyQuestionItem object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the questionId attribute of the ActiveSurveyQuestionItem object
   *
   *@param  tmp  The new questionId value
   */
  public void setQuestionId(int tmp) {
    this.questionId = tmp;
  }



  /**
   *  Sets the type attribute of the ActiveSurveyQuestionItem object
   *
   *@param  tmp  The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the totalResponse attribute of the ActiveSurveyQuestionItem object
   *
   *@param  totalResponse  The new totalResponse value
   */
  public void setTotalResponse(int totalResponse) {
    this.totalResponse = totalResponse;
  }


  /**
   *  Gets the totalResponse attribute of the ActiveSurveyQuestionItem object
   *
   *@return    The totalResponse value
   */
  public int getTotalResponse() {
    return totalResponse;
  }


  /**
   *  Gets the questionId attribute of the ActiveSurveyQuestionItem object
   *
   *@return    The questionId value
   */
  public int getQuestionId() {
    return questionId;
  }


  /**
   *  Gets the type attribute of the ActiveSurveyQuestionItem object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Sets the questionId attribute of the ActiveSurveyQuestionItem object
   *
   *@param  tmp  The new questionId value
   */
  public void setQuestionId(String tmp) {
    this.questionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the type attribute of the ActiveSurveyQuestionItem object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   *  Insert an item
   *
   *@param  db                Description of the Parameter
   *@param  qid               Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db, int qid) throws SQLException {
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO active_survey_items " +
          "(question_id, type, description ) " +
          "VALUES (?, ?, ?) ");
      int i = 0;
      pst.setInt(++i, qid);
      pst.setInt(++i, this.getType());
      pst.setString(++i, this.getDescription());
      if (System.getProperty("DEBUG") != null) {
        System.out.println(" ActiveSurveyItem -- > Insert Query " + pst.toString());
      }
      pst.execute();
      pst.close();
      this.setId(DatabaseUtils.getCurrVal(db, "active_survey_items_item_id_seq"));

      //populate the active_survey_answer_avg table with 0 count for items
      i = 0;
      pst = db.prepareStatement(
          "INSERT INTO active_survey_answer_avg " +
          "(question_id, item_id, total ) " +
          "VALUES (?, ?, ?) ");
      pst.setInt(++i, qid);
      pst.setInt(++i, this.getId());
      pst.setInt(++i, 0);
      pst.execute();
      pst.close();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   *  Update an item
   *
   *@param  db                Description of the Parameter
   *@param  qId               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db, int qId) throws SQLException {
    int count = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE active_survey_items " +
          "SET description = ? " +
          "WHERE question_id = ? ");
      int i = 0;
      pst.setString(++i, description);
      pst.setInt(++i, qId);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(" SurveyQuestion -- > Update Item " + pst.toString());
      }
      count = pst.executeUpdate();
      pst.close();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return count;
  }


  /**
   *  Build Item record
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("item_id");
    questionId = rs.getInt("question_id");
    type = rs.getInt("type");
    description = rs.getString("description");
  }
}

