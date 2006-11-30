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

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * For each SurveyItem (question), a user's response is stored as a
 * SurveyAnswer
 *
 * @author chris price
 * @version $Id$
 * @created August 7, 2002
 */
public class SurveyAnswer {
  private int id = -1;
  private int questionId = -1;
  private int quantAns = -1;
  private int responseId = -1;
  private int contactId = -1;
  private ArrayList itemList = null;
  private String comments = null;
  private String textAns = null;
  private java.sql.Timestamp entered = null;


  /**
   * Sets the responseId attribute of the SurveyAnswer object
   *
   * @param tmp The new responseId value
   */
  public void setResponseId(String tmp) {
    this.responseId = Integer.parseInt(tmp);
  }


  /**
   * Constructor for the SurveyAnswer object
   */
  public SurveyAnswer() {
  }


  /**
   * Constructor for the SurveyAnswer object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public SurveyAnswer(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the SurveyAnswer object
   *
   * @param db       Description of the Parameter
   * @param passedId Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public SurveyAnswer(Connection db, int passedId) throws SQLException {
    if (passedId < 1) {
      throw new SQLException("Question ID not specified.");
    }

    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql =
        "SELECT * " +
            "FROM active_survey_answers s " +
            "WHERE answer_id = ? ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, passedId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Question Answer record not found.");
    }
    buildItems(db, passedId);
  }


  /**
   * Description of the Method
   *
   * @param request   Description of the Parameter
   * @param parseItem Description of the Parameter
   */
  public void buildRecord(HttpServletRequest request, int parseItem) {
    String question = "quest" + parseItem;
    this.setQuestionId(request.getParameter(question + "id"));

    if (request.getParameter(question + "comments") != null) {
      this.setComments(request.getParameter(question + "comments"));
    }

    if (request.getParameter(question + "qans") != null) {
      this.setQuantAns(request.getParameter(question + "qans"));
    }

    if (request.getParameter(question + "text") != null) {
      this.setTextAns(request.getParameter(question + "text"));
    }

    //add items if any.
    if (request.getParameter(question + "itemCount") != null) {
      int i = 0;
      int itemCount = Integer.parseInt(
          request.getParameter(question + "itemCount"));
      for (int j = 0; j < itemCount; j++) {
        if (request.getParameter(question + "item" + (++i)) != null) {
          if (itemList == null) {
            itemList = new ArrayList();
          }
          SurveyAnswerItem thisItem = new SurveyAnswerItem();
          thisItem.buildRecord(request, question + "item" + i);
          itemList.add(thisItem);
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Added an item: " + thisItem.getId());
          }
        }
      }
    }
  }


  /**
   * Sets the id attribute of the SurveyAnswer object
   *
   * @param id The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * Sets the entered attribute of the SurveyAnswer object
   *
   * @param entered The new entered value
   */
  public void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
  }


  /**
   * Gets the entered attribute of the SurveyAnswer object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the id attribute of the SurveyAnswer object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the responseId attribute of the SurveyAnswer object
   *
   * @return The responseId value
   */
  public int getResponseId() {
    return responseId;
  }


  /**
   * Sets the responseId attribute of the SurveyAnswer object
   *
   * @param responseId The new responseId value
   */
  public void setResponseId(int responseId) {
    this.responseId = responseId;
  }


  /**
   * Sets the contactId attribute of the SurveyAnswer object
   *
   * @param contactId The new contactId value
   */
  public void setContactId(int contactId) {
    this.contactId = contactId;
  }


  /**
   * Sets the itemList attribute of the SurveyAnswer object
   *
   * @param itemList The new itemList value
   */
  public void setItemList(ArrayList itemList) {
    this.itemList = itemList;
  }


  /**
   * Gets the itemList attribute of the SurveyAnswer object
   *
   * @return The itemList value
   */
  public ArrayList getItemList() {
    return itemList;
  }


  /**
   * Gets the contactId attribute of the SurveyAnswer object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Sets the id attribute of the SurveyAnswer object
   *
   * @param id The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   * Gets the questionId attribute of the SurveyAnswer object
   *
   * @return The questionId value
   */
  public int getQuestionId() {
    return questionId;
  }


  /**
   * Gets the comments attribute of the SurveyAnswer object
   *
   * @return The comments value
   */
  public String getComments() {
    return comments;
  }


  /**
   * Gets the quantAns attribute of the SurveyAnswer object
   *
   * @return The quantAns value
   */
  public int getQuantAns() {
    return quantAns;
  }


  /**
   * Gets the textAns attribute of the SurveyAnswer object
   *
   * @return The textAns value
   */
  public String getTextAns() {
    return textAns;
  }


  /**
   * Sets the questionId attribute of the SurveyAnswer object
   *
   * @param tmp The new questionId value
   */
  public void setQuestionId(int tmp) {
    this.questionId = tmp;
  }


  /**
   * Sets the comments attribute of the SurveyAnswer object
   *
   * @param tmp The new comments value
   */
  public void setComments(String tmp) {
    this.comments = tmp;
  }


  /**
   * Sets the quantAns attribute of the SurveyAnswer object
   *
   * @param tmp The new quantAns value
   */
  public void setQuantAns(int tmp) {
    this.quantAns = tmp;
  }


  /**
   * Sets the textAns attribute of the SurveyAnswer object
   *
   * @param tmp The new textAns value
   */
  public void setTextAns(String tmp) {
    this.textAns = tmp;
  }


  /**
   * Sets the questionId attribute of the SurveyAnswer object
   *
   * @param tmp The new questionId value
   */
  public void setQuestionId(String tmp) {
    this.questionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the quantAns attribute of the SurveyAnswer object
   *
   * @param tmp The new quantAns value
   */
  public void setQuantAns(String tmp) {
    this.quantAns = Integer.parseInt(tmp);
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("answer_id"));
    this.setResponseId(rs.getInt("response_id"));
    this.setQuestionId(rs.getInt("question_id"));
    this.setComments(rs.getString("comments"));
    this.setQuantAns(rs.getInt("quant_ans"));
    this.setTextAns(rs.getString("text_ans"));
    this.setEntered(rs.getTimestamp("entered"));
  }


  /**
   * Build Items related to Answer
   *
   * @param db           Description of the Parameter
   * @param thisAnswerId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildItems(Connection db, int thisAnswerId) throws SQLException {
    try {
      PreparedStatement pst = db.prepareStatement(
          "SELECT * " +
              "FROM active_survey_answer_items t " +
              "WHERE t.answer_id = ? ");
      pst.setInt(1, thisAnswerId);
      ResultSet rs = pst.executeQuery();
      while (rs.next()) {
        if (itemList == null) {
          itemList = new ArrayList();
        }
        SurveyAnswerItem thisAnswerItem = new SurveyAnswerItem();
        thisAnswerItem.buildRecord(rs);
        itemList.add(thisAnswerItem);
      }
      rs.close();
      pst.close();

      if (itemList != null) {
        Iterator i = itemList.iterator();
        while (i.hasNext()) {
          SurveyAnswerItem thisItem = (SurveyAnswerItem) i.next();
          thisItem.buildItemDetails(db);
        }
      }
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }


  /**
   * Builds the contactId of the person who responded to this Question
   *
   * @param db The new contactId value
   * @throws SQLException Description of the Exception
   */
  public void setContactId(Connection db) throws SQLException {
    try {
      PreparedStatement pst = db.prepareStatement(
          "SELECT sr.contact_id as contactid " +
              "FROM active_survey_answers sa, active_survey_responses sr " +
              "WHERE sa.answer_id = ? AND (sa.response_id = sr.response_id) ");
      pst.setInt(1, this.getId());
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        setContactId(rs.getInt("contactid"));
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    return insert(db, this.responseId);
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param responseId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db, int responseId) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "active_survey_ans_answer_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO active_survey_answers " +
            "(" + (id > -1 ? "answer_id, " : "") + "response_id, question_id, comments, quant_ans, text_ans) " +
            "VALUES " +
            "(" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, responseId);
    pst.setInt(++i, questionId);
    pst.setString(++i, comments);
    pst.setInt(++i, quantAns);
    pst.setString(++i, textAns);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "active_survey_ans_answer_id_seq", id);
    if (itemList != null) {
      insertItemList(db, id);
    }
    if (this.quantAns > -1) {
      this.updateSurveyAverage(db);
      this.updateAnswerTotal(db);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param thisId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void insertItemList(Connection db, int thisId) throws SQLException {
    if (thisId == -1) {
      throw new SQLException("Answer ID not specified.");
    }
    Iterator items = itemList.iterator();
    while (items.hasNext()) {
      SurveyAnswerItem thisItem = (SurveyAnswerItem) items.next();
      thisItem.insert(db, thisId, this.getQuestionId());
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int updateSurveyAverage(Connection db) throws SQLException {
    if (questionId == -1) {
      throw new SQLException("Question ID not specified.");
    }

    double thisAverage = 0;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql =
        "SELECT avg(quant_ans) as av " +
            "FROM active_survey_answers s " +
            "WHERE question_id = ? AND quant_ans > 0";
    pst = db.prepareStatement(sql);
    pst.setInt(1, this.getQuestionId());
    rs = pst.executeQuery();
    if (rs.next()) {
      thisAverage = rs.getDouble("av");
    }
    rs.close();
    pst.close();

    pst = db.prepareStatement(
        "UPDATE active_survey_questions " +
            "SET average = ? " +
            "WHERE question_id = ? ");
    int i = 0;
    pst.setDouble(++i, thisAverage);
    pst.setInt(++i, this.getQuestionId());
    pst.execute();
    pst.close();

    return 1;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private boolean updateAnswerTotal(Connection db) throws SQLException {
    PreparedStatement pst = null;
    String sql =
        "UPDATE active_survey_questions " +
            "SET total" + this.getQuantAns() + " = total" + this.getQuantAns() + " + 1 " +
            "WHERE question_id = ? ";
    pst = db.prepareStatement(sql);
    pst.setInt(1, this.getQuestionId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Answer ID not specified");
    }
    int i = 0;
    PreparedStatement pst = null;
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      //delete any item dependency first
      pst = db.prepareStatement(
          "DELETE FROM active_survey_answer_items " +
              "WHERE answer_id = ? ");
      pst.setInt(++i, this.getId());
      pst.execute();

      //delete the answer
      pst = db.prepareStatement(
          "DELETE FROM active_survey_answers " +
              "WHERE answer_id = ? ");
      pst.setInt(++i, this.getId());
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
    return true;
  }

}

