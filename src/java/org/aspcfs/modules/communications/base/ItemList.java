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

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import javax.servlet.http.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    October 18, 2002
 *@version    $Id$
 */
public class ItemList extends ArrayList {

  private int questionId = -1;


  /**
   *  Constructor for the ItemList object
   */
  public ItemList() { }


  /**
   *  Constructor for the ItemList object
   *
   *@param  request  Description of the Parameter
   */
  public ItemList(HttpServletRequest request) {
    String items = null;
    if ((items = request.getParameter("items")) != null) {
      StringTokenizer itemList = new StringTokenizer(items, "^|^");
      while (itemList.hasMoreTokens()) {
        Item thisItem = new Item();
        thisItem.setDescription(itemList.nextToken());
        this.add(thisItem);
        System.out.println(" ItemList -- > Added Item " + thisItem.getDescription());
      }
    }
  }


  /**
   *  Gets the questionId attribute of the ItemList object
   *
   *@return    The questionId value
   */
  public int getQuestionId() {
    return questionId;
  }


  /**
   *  Gets the object attribute of the ItemList object
   *
   *@param  rs                Description of the Parameter
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public Item getObject(ResultSet rs) throws SQLException {
    Item thisItem = new Item(rs);
    return thisItem;
  }


  /**
   *  Sets the questionId attribute of the ItemList object
   *
   *@param  questionId  The new questionId value
   */
  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }


  /**
   *  Sets the questionId attribute of the ItemList object
   *
   *@param  questionId  The new questionId value
   */
  public void setQuestionId(String questionId) {
    this.questionId = Integer.parseInt(questionId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  questionId        Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static boolean delete(Connection db, int questionId) throws SQLException {
    boolean commit = true;
    ResultSet rs = null;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM survey_items WHERE question_id = ?");
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
      Item thisItem = this.getObject(rs);
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
        "FROM survey_items sq " +
        "WHERE question_id = ? ";
    pst = db.prepareStatement(sql);
    int i = 0;
    pst.setInt(++i, questionId);
    ResultSet rs = pst.executeQuery();
    return rs;
  }
}

