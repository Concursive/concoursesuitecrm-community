//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Represents a copied survey that can have items and answers and
 *  belongs to an active Campaign
 *
 */
public class ActiveSurvey extends Survey {

  private SurveyItemList items = new SurveyItemList();
  private SurveyAnswerList answers = new SurveyAnswerList();

  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;

  public ActiveSurvey() { }

  public ActiveSurvey(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  public SurveyAnswerList getAnswers() {
    return answers;
  }


  /**
   *  Sets the answers attribute of the Survey object
   *
   *@param  answers  The new answers value
   */
  public void setAnswers(SurveyAnswerList answers) {
    this.answers = answers;
  }


  /**
   *  Constructor for the Survey object
   *
   *@param  db                Description of the Parameter
   *@param  surveyId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ActiveSurvey(Connection db, int surveyId) throws SQLException {
    if (surveyId < 1) {
      throw new SQLException("Survey ID not specified.");
    }
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT s.*, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, " +
        "st.description as typename " +
        "FROM active_survey s " +
        "LEFT JOIN contact ct_eb ON (s.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (s.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN lookup_survey_types st ON (s.type = st.code) " +
        "WHERE s.active_survey_id = " + surveyId + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Survey record not found.");
    }
    rs.close();
    st.close();

    items.setSurveyId(this.getId());
    items.buildList(db);
  }


  /**
   *  Sets the requestItems attribute of the Survey object
   *
   *@param  request  The new requestItems value
   */
  public void setRequestItems(HttpServletRequest request) {
    items = new SurveyItemList(request);
  }


  /**
   *  Sets the answerItems attribute of the Survey object
   *
   *@param  request  The new answerItems value
   */
  public void setAnswerItems(HttpServletRequest request) {
    answers = new SurveyAnswerList(request);
  }


  /**
   *  Gets the itemsId attribute of the Survey object
   *
   *@return    The itemsId value
   */
  public int getItemsId() {
    return itemsId;
  }


  /**
   *  Sets the itemsId attribute of the Survey object
   *
   *@param  itemsId  The new itemsId value
   */
  public void setItemsId(int itemsId) {
    this.itemsId = itemsId;
  }


  /**
   *  Sets the itemsId attribute of the Survey object
   *
   *@param  itemsId  The new itemsId value
   */
  public void setItemsId(String itemsId) {
    this.itemsId = Integer.parseInt(itemsId);
  }


  /**
   *  Gets the items attribute of the Survey object
   *
   *@return    The items value
   */
  public SurveyItemList getItems() {
    return items;
  }


  /**
   *  Sets the items attribute of the Survey object
   *
   *@param  items  The new items value
   */
  public void setItems(SurveyItemList items) {
    this.items = items;
  }

  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO active_survey " +
        "(items_id, name, description, intro, itemLength, type, enteredBy, modifiedBy) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
    try {
      db.setAutoCommit(false);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, itemsId);
      pst.setString(++i, name);
      pst.setString(++i, description);
      pst.setString(++i, intro);
      pst.setInt(++i, itemLength);
      pst.setInt(++i, type);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "survey_id_seq");

      //Insert the questions
      Iterator x = items.iterator();
      while (x.hasNext()) {
        SurveyItem thisItem = (SurveyItem) x.next();
        thisItem.insert(db, this.getId(), this.getType());
      }

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
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    boolean commit = true;
    Statement st = null;
    ResultSet rs = null;
    try {
      commit = db.getAutoCommit();
      
      //Check to see if a survey is being used by any Inactive campaigns
      //If so, the survey can't be deleted
      int inactiveCount = 0;
      st = db.createStatement();
      rs = st.executeQuery(
        "SELECT COUNT(*) AS survey_count " +
        "FROM campaign " +
        "WHERE survey_id = " + this.getId() + " " +
        "AND status_id <> " + Campaign.FINISHED);
      rs.next();
      inactiveCount = rs.getInt("survey_count");
      rs.close();
      if (inactiveCount > 0) {
        st.close();
        errors.put("actionError", "Survey could not be deleted because " +
          inactiveCount + " " +
          (inactiveCount == 1?"campaign is":"campaigns are") +
          " being built that " +
          (inactiveCount == 1?"uses":"use") +
          " this survey.");
        return false;
      }
      
      //If not, Check to see if a survey is being used by any Active Campaigns
      //If so, the campaign will be marked disabled and hidden to the user
      int activeCount = 0;
      rs = st.executeQuery(
        "SELECT COUNT(*) AS survey_count " +
        "FROM campaign " +
        "WHERE survey_id = " + this.getId() + " " +
        "AND active = " + DatabaseUtils.getTrue(db));
      rs.next();
      activeCount = rs.getInt("survey_count");
      rs.close();
      
      if (commit) {
        db.setAutoCommit(false);
      }
      if (activeCount > 0) {
        st.executeUpdate(
          "UPDATE survey " +
          "SET enabled = " + DatabaseUtils.getFalse(db) + " " +
          "WHERE id = " + this.getId() + " " +
          "AND enabled = " + DatabaseUtils.getTrue(db));
      } else {
        st.executeUpdate("DELETE FROM survey WHERE id = " + this.getId());
        st.executeUpdate("DELETE FROM survey_answer WHERE question_id in (select id from survey_item where survey_id = " + this.getId() + ") ");
        st.executeUpdate("DELETE FROM survey_item WHERE survey_id = " + this.getId());
      }
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      System.out.println(e.toString());
      throw new SQLException(e.toString());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
      st.close();
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  override          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;

    if (this.getId() == -1) {
      throw new SQLException("Survey ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE active_survey " +
        "SET message_id = ?, items_id = ?, name = ?, description = ?, intro = ?, itemlength = ?, " +
        "type = ?, " +
        "enabled = ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
        "WHERE id = ? ");
    //if (!override) {
    //  sql.append("AND modified = ? ");
    //}

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getMessageId());
    pst.setInt(++i, this.getItemsId());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setString(++i, this.getIntro());
    pst.setInt(++i, this.getItemLength());
    pst.setInt(++i, this.getType());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;

    try {
      db.setAutoCommit(false);

      Iterator x = items.iterator();
      while (x.hasNext()) {
        SurveyItem thisItem = (SurveyItem) x.next();
        thisItem.process(db, this.getId(), this.getType());
      }

      resultCount = this.update(db, false);
      db.commit();
    } catch (Exception e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }

    db.setAutoCommit(true);
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("id"));
    messageId = rs.getInt("message_id");
    itemsId = rs.getInt("items_id");
    name = rs.getString("name");
    description = rs.getString("description");
    intro = rs.getString("intro");
    itemLength = rs.getInt("itemlength");
    type = rs.getInt("type");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");

    enteredByName = Contact.getNameLastFirst(rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
    modifiedByName = Contact.getNameLastFirst(rs.getString("mb_namelast"), rs.getString("mb_namefirst"));
    typeName = rs.getString("typename");
  }

}

