package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import java.util.*;
import javax.servlet.http.*;

/**
 *  A Survey Question
 *
 *@author     matt rajkowski
 *@created    August 13, 2002
 *@version    $Id: SurveyQuestion.java,v 1.1 2002/08/27 19:28:31 mrajkowski Exp
 */
public class SurveyQuestion {

  public final static int OPEN_ENDED = 1;
  public final static int QUANT_NOCOMMENTS = 2;
  public final static int QUANT_COMMENTS = 3;
  public final static int ITEMLIST = 4;

  private int id = -1;
  private int surveyId = -1;
  private int type = -1;
  private int position = 0;
  private String description = null;
  private boolean required = false;
  private ItemList itemList = null;


  /**
   *  Constructor for the SurveyQuestion object
   */
  public SurveyQuestion() { }


  /**
   *  Constructor for the SurveyQuestion object
   *
   *@param  request  Description of the Parameter
   */
  public SurveyQuestion(HttpServletRequest request) {
    this.setDescription(request.getParameter("questionText"));
    this.setType(Integer.parseInt(request.getParameter("type")));
    this.setPosition(Integer.parseInt(request.getParameter("position")));
    if (type == ITEMLIST) {
      this.buildItems(request);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(" SurveyQuestion -- > Building ItemList  " + request.getParameter("items"));
      }
    }
    this.setRequired(request.getParameter("required") != null ? true : false);
  }


  /**
   *  Constructor for the SurveyQuestion object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public SurveyQuestion(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Gets the id attribute of the SurveyQuestion object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the description attribute of the SurveyQuestion object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the id attribute of the SurveyQuestion object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the SurveyQuestion object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the description attribute of the SurveyQuestion object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the surveyId attribute of the SurveyQuestion object
   *
   *@param  tmp  The new surveyId value
   */
  public void setSurveyId(int tmp) {
    this.surveyId = tmp;
  }


  /**
   *  Sets the type attribute of the SurveyQuestion object
   *
   *@param  tmp  The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Description of the Method
   *
   *@param  request  Description of the Parameter
   */
  public void buildItems(HttpServletRequest request) {
    this.itemList = new ItemList(request);
  }


  /**
   *  Sets the itemList attribute of the SurveyQuestion object
   *
   *@param  itemList  The new itemList value
   */
  public void setItemList(ItemList itemList) {
    this.itemList = itemList;
  }


  /**
   *  Sets the required attribute of the SurveyQuestion object
   *
   *@param  required  The new required value
   */
  public void setRequired(boolean required) {
    this.required = required;
  }


  /**
   *  Sets the required attribute of the SurveyQuestion object
   *
   *@param  required  The new required value
   */
  public void setRequired(String required) {
    this.required = "yes".equalsIgnoreCase(required);
  }


  /**
   *  Sets the position attribute of the SurveyQuestion object
   *
   *@param  position  The new position value
   */
  public void setPosition(int position) {
    this.position = position;
  }


  /**
   *  Gets the position attribute of the SurveyQuestion object
   *
   *@return    The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   *  Gets the questionId attribute of the SurveyQuestion object
   *
   *@return    The questionId value
   */
  public int getQuestionId() {
    return id;
  }


  /**
   *  Gets the required attribute of the SurveyQuestion object
   *
   *@return    The required value
   */
  public boolean getRequired() {
    return required;
  }


  /**
   *  Gets the itemList attribute of the SurveyQuestion object
   *
   *@return    The itemList value
   */
  public ItemList getItemList() {
    return itemList;
  }


  /**
   *  Gets the items attribute of the SurveyQuestion object "^|^" is used as a
   *  tokenizer.
   *
   *@return    The items value
   */
  public String getItems() {
    String token = "^|^";
    StringBuffer tmp = new StringBuffer();
    if (itemList != null) {
      Iterator thisList = itemList.iterator();
      while (thisList.hasNext()) {
        Item thisItem = (Item) thisList.next();
        tmp.append(thisItem.getDescription() + token);
      }
      if (tmp.length() > 0) {
        return tmp.substring(0, tmp.length() - token.length());
      }
    }
    return "";
  }


  /**
   *  Gets the questionText attribute of the SurveyQuestion object
   *
   *@return    The questionText value
   */
  public String getQuestionText() {
    return description;
  }


  /**
   *  Gets the surveyId attribute of the SurveyQuestion object
   *
   *@return    The surveyId value
   */
  public int getSurveyId() {
    return surveyId;
  }


  /**
   *  Gets the type attribute of the SurveyQuestion object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Sets the surveyId attribute of the SurveyQuestion object
   *
   *@param  tmp  The new surveyId value
   */
  public void setSurveyId(String tmp) {
    this.surveyId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the type attribute of the SurveyQuestion object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  surveyId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db, int surveyId) throws SQLException {
    try {
      db.setAutoCommit(false);
      
      int i = 0;
      //calculate the next position for this question
      PreparedStatement pst = db.prepareStatement(
          "SELECT max(position) as maxPosition " +
          "FROM survey_questions " +
          "WHERE survey_id = ? ");
      pst.setInt(++i,surveyId);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
         position = rs.getInt("maxPosition") + 1;
      }
      pst.close();
      rs.close();
      //insert question
      pst = db.prepareStatement(
          "INSERT INTO survey_questions " +
          "(survey_id, type, description, required, position ) " +
          "VALUES " +
          "(?, ?, ?, ?, ?) ");
      i = 0;
      pst.setInt(++i, surveyId);
      pst.setInt(++i, type);
      pst.setString(++i, description);
      pst.setBoolean(++i, required);
      pst.setInt(++i, position);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(" SurveyQuestion -- > Insert Query " + pst.toString());
      }
      pst.execute();
      pst.close();
      this.setId(DatabaseUtils.getCurrVal(db, "survey_question_question_id_seq"));
      if (this.getType() == ITEMLIST) {
        Iterator y = this.getItemList().iterator();
        while (y.hasNext()) {
          Item thisItem = (Item) y.next();
          thisItem.insert(db, this.getId());
        }
      }
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  surveyId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void process(Connection db, int surveyId) throws SQLException {
    if (this.getId() == -1) {
      insert(db, surveyId);
    } else {
      update(db, surveyId);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  thisSurveyId      Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void update(Connection db, int thisSurveyId) throws SQLException {
    try {
      db.setAutoCommit(false);
      
      checkDependency(db,thisSurveyId);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE survey_questions " +
          "SET survey_id = ?, type = ?, description = ?, required = ?, position = ? " +
          "WHERE question_id = ? ");
      int i = 0;
      pst.setInt(++i, thisSurveyId);
      pst.setInt(++i, this.getType());
      pst.setString(++i, description);
      pst.setBoolean(++i, required);
      pst.setInt(++i, this.getPosition());
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      if (this.getType() == ITEMLIST) {
          Iterator y = this.getItemList().iterator();
          while (y.hasNext()) {
            Item thisItem = (Item) y.next();
            thisItem.insert(db, this.getId());
          }
      }
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   *  Check for any items related to question and if any delete them
   *
   *@param  db                Description of the Parameter
   *@param  thisSurveyId      Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void checkDependency(Connection db, int thisSurveyId) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "SELECT type " +
          "FROM survey_questions " +
          "WHERE survey_id = ? AND question_id = ? ");

      pst.setInt(++i, thisSurveyId);
      pst.setInt(++i, this.getId());
      pst.close();
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        if (rs.getInt("type") == ITEMLIST) {
          ItemList.delete(db, this.getId());
        }
      }
      rs.close();
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
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  thisSurveyId      Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, int thisSurveyId) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      int i = 0;
      if (commit) {
        db.setAutoCommit(false);
      }
      //check for related items
      checkDependency(db, thisSurveyId);

      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM survey_questions " +
          "WHERE survey_id = ? AND question_id = ? ");

      pst.setInt(++i, thisSurveyId);
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
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("question_id");
    surveyId = rs.getInt("survey_id");
    type = rs.getInt("type");
    description = rs.getString("description");
    required = rs.getBoolean("required");
    position = rs.getInt("position");
  }

}

