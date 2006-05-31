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

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Item for an Active Survey Question.
 *
 * @author akhi_m
 * @version $Id: ActiveSurveyQuestionItem.java,v 1.4 2003/01/14 16:11:34
 *          akhi_m Exp $
 * @created November 1, 2002
 */
public class ActiveSurveyQuestionItem {
  private int id = -1;
  private String description = null;
  private int questionId = -1;
  private int type = -1;
  private int totalResponse = 0;


  /**
   * Constructor for the ActiveSurveyQuestionItem object
   */
  public ActiveSurveyQuestionItem() {
  }


  /**
   * Constructor for the ActiveSurveyQuestionItem object
   *
   * @param thisItem Description of the Parameter
   */
  public ActiveSurveyQuestionItem(Item thisItem) {
    this.id = thisItem.getId();
    this.description = thisItem.getDescription();
    this.questionId = thisItem.getQuestionId();
    this.type = thisItem.getType();
  }


  /**
   * Constructor for the ActiveSurveyQuestionItem object
   *
   * @param db     Description of the Parameter
   * @param itemId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ActiveSurveyQuestionItem(Connection db, int itemId) throws SQLException {
    if (itemId == -1) {
      throw new SQLException("Item ID not specified");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT si.item_id, si.question_id, si.\"type\", si.description " +
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
   * Constructor for the ActiveSurveyQuestionItem object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ActiveSurveyQuestionItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Gets the id attribute of the ActiveSurveyQuestionItem object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the description attribute of the ActiveSurveyQuestionItem object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Sets the id attribute of the ActiveSurveyQuestionItem object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ActiveSurveyQuestionItem object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the description attribute of the ActiveSurveyQuestionItem object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the questionId attribute of the ActiveSurveyQuestionItem object
   *
   * @param tmp The new questionId value
   */
  public void setQuestionId(int tmp) {
    this.questionId = tmp;
  }


  /**
   * Sets the type attribute of the ActiveSurveyQuestionItem object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   * Sets the totalResponse attribute of the ActiveSurveyQuestionItem object
   *
   * @param totalResponse The new totalResponse value
   */
  public void setTotalResponse(int totalResponse) {
    this.totalResponse = totalResponse;
  }


  /**
   * Gets the totalResponse attribute of the ActiveSurveyQuestionItem object
   *
   * @return The totalResponse value
   */
  public int getTotalResponse() {
    return totalResponse;
  }


  /**
   * Gets the questionId attribute of the ActiveSurveyQuestionItem object
   *
   * @return The questionId value
   */
  public int getQuestionId() {
    return questionId;
  }


  /**
   * Gets the type attribute of the ActiveSurveyQuestionItem object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }


  /**
   * Sets the questionId attribute of the ActiveSurveyQuestionItem object
   *
   * @param tmp The new questionId value
   */
  public void setQuestionId(String tmp) {
    this.questionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the type attribute of the ActiveSurveyQuestionItem object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   * Insert an item
   *
   * @param db  Description of the Parameter
   * @param qid Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db, int qid) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      int i = 0;
      id = DatabaseUtils.getNextSeq(db, "active_survey_items_item_id_seq");
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO active_survey_items " +
          "(" + (id > -1 ? "item_id, " : "") + "question_id, \"type\", description ) " +
          "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?) ");
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, qid);
      pst.setInt(++i, this.getType());
      pst.setString(++i, this.getDescription());
      pst.execute();
      pst.close();
      this.setId(
          DatabaseUtils.getCurrVal(db, "active_survey_items_item_id_seq", id));
      //populate the active_survey_answer_avg table with 0 count for items
      i = 0;
      int seqId = DatabaseUtils.getNextSeq(
          db, "active_survey_answer_avg_id_seq");
      pst = db.prepareStatement(
          "INSERT INTO active_survey_answer_avg " +
          "(" + (seqId > -1 ? "id, " : "") + "question_id, item_id, total ) " +
          "VALUES (" + (seqId > -1 ? "?, " : "") + "?, ?, ?) ");
      if (seqId > -1) {
        pst.setInt(++i, seqId);
      }
      pst.setInt(++i, qid);
      pst.setInt(++i, this.getId());
      pst.setInt(++i, 0);
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   * Update an item
   *
   * @param db  Description of the Parameter
   * @param qId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db, int qId) throws SQLException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE active_survey_items " +
        "SET description = ? " +
        "WHERE question_id = ? ");
    int i = 0;
    pst.setString(++i, description);
    pst.setInt(++i, qId);
    count = pst.executeUpdate();
    pst.close();
    return count;
  }


  /**
   * Build Item record
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("item_id");
    questionId = rs.getInt("question_id");
    type = rs.getInt("type");
    description = rs.getString("description");
  }
}

