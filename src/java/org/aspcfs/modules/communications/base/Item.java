/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
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
 * Description of the Class
 *
 * @author akhi_m
 * @version $Id$
 * @created October 18, 2002
 */
public class Item {
  private int id = -1;
  private String description = null;
  private int questionId = -1;
  private int type = -1;


  /**
   * Constructor for the Item object
   */
  public Item() {
  }


  /**
   * Constructor for the Item object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   * @throws SQLException Description of the Exception
   */
  public Item(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Gets the id attribute of the Item object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the description attribute of the Item object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Sets the id attribute of the Item object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Item object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the description attribute of the Item object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the questionId attribute of the Item object
   *
   * @param tmp The new questionId value
   */
  public void setQuestionId(int tmp) {
    this.questionId = tmp;
  }


  /**
   * Sets the type attribute of the Item object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   * Gets the questionId attribute of the Item object
   *
   * @return The questionId value
   */
  public int getQuestionId() {
    return questionId;
  }


  /**
   * Gets the type attribute of the Item object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }


  /**
   * Sets the questionId attribute of the Item object
   *
   * @param tmp The new questionId value
   */
  public void setQuestionId(String tmp) {
    this.questionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the type attribute of the Item object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    this.insert(db, this.questionId);
  }


  /**
   * Description of the Method
   *
   * @param db  Description of the Parameter
   * @param qid Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db, int qid) throws SQLException {
    if (qid == -1) {
      throw new SQLException("Question ID not specified");
    }
    boolean doCommit = db.getAutoCommit();
    try {
      if (doCommit) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "survey_items_item_id_seq");
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO survey_items " +
              "(" + (id > -1 ? "item_id, " : "") + "question_id, " + DatabaseUtils.addQuotes(db, "type") + ", description ) " +
              "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?) ");
      int i = 0;
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, qid);
      pst.setInt(++i, this.getType());
      pst.setString(++i, this.getDescription());
      pst.execute();
      pst.close();
      setId(DatabaseUtils.getCurrVal(db, "survey_items_item_id_seq", id));
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db  Description of the Parameter
   * @param qId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db, int qId) throws SQLException {
    int count = 0;
    boolean doCommit = db.getAutoCommit();
    try {
      if (doCommit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "UPDATE survey_items " +
              "SET description = ?, " +
              "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
              "WHERE question_id = ? ");
      int i = 0;
      pst.setString(++i, description);
      pst.setInt(++i, qId);
      count = pst.executeUpdate();
      pst.close();
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return count;
  }


  /**
   * Description of the Method
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

