//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Represents a survey that can have introductory text and questions.
 *
 *@author     chris price
 *@created    August 7, 2002
 *@version    $Id$
 */
public class Survey extends SurveyBase {

  protected int id = -1;
  protected SurveyQuestionList questions = new SurveyQuestionList();

  protected int enteredBy = -1;
  protected int modifiedBy = -1;
  protected java.sql.Timestamp modified = null;
  protected java.sql.Timestamp entered = null;
  protected boolean enabled = true;


  /**
   *  Constructor for the Survey object
   */
  public Survey() { }


  /**
   *  Constructor for the Survey object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Survey(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Survey object
   *
   *@param  db                Description of the Parameter
   *@param  surveyId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Survey(Connection db, int surveyId) throws SQLException {
    Statement st = null;
    ResultSet rs = null;
    String sql = 
      "SELECT s.*, " +
      "st.description as typename " +
      "FROM survey s " +
      "LEFT JOIN lookup_survey_types st ON (s.type = st.code) " +
      "WHERE s.survey_id = " + surveyId;
    st = db.createStatement();
    rs = st.executeQuery(sql);
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Survey record not found.");
    }
    rs.close();
    st.close();

    questions.setSurveyId(this.getId());
    questions.buildList(db);
  }


  /**
   *  Gets the enteredString attribute of the Survey object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Sets the requestItems attribute of the Survey object
   *
   *@param  request  The new requestItems value
   */
  public void setRequestItems(HttpServletRequest request) {
    questions = new SurveyQuestionList(request);
  }


  public SurveyQuestionList getQuestions() {
    return questions;
  }


  public void setQuestions(SurveyQuestionList items) {
    this.questions = questions;
  }


  /**
   *  Gets the id attribute of the Survey object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }
  
  public static int getId(Connection db, int campaignId) throws SQLException {
    int surveyId = -1;
    String sql = 
      "SELECT survey_id " +
      "FROM campaign_survey_link " +
      "WHERE campaign_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, campaignId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      surveyId = rs.getInt("survey_id");
    }
    rs.close();
    return surveyId;
  }

  public static void removeLink(Connection db, int campaignId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "DELETE FROM campaign_survey_link " +
      "WHERE campaign_id = ? ");
    pst.setInt(1, campaignId);
    pst.execute();
    pst.close();
  }
  
  /**
   *  Gets the enteredBy attribute of the Survey object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the Survey object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the Survey object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the entered attribute of the Survey object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enabled attribute of the Survey object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the id attribute of the Survey object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Survey object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Survey object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Survey object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modified attribute of the Survey object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }

  public void setModified(String tmp) {
    if (tmp != null) {
      this.modified = java.sql.Timestamp.valueOf(tmp);
    }
  }

  /**
   *  Sets the entered attribute of the Survey object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the enabled attribute of the Survey object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Gets the enteredDateTimeString attribute of the Survey object
   *
   *@return    The enteredDateTimeString value
   */
  public String getEnteredDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Description of the Method
   *
   *@param  request  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String printHidden(HttpServletRequest request) {
    StringBuffer result = new StringBuffer();

    if (request != null) {
      java.util.Enumeration e = request.getParameterNames();
      ArrayList params = new ArrayList();
      java.lang.reflect.Field[] f = this.getClass().getFields();
      for (int i = 0; i < f.length; i++) {
        params.add(f[i].getName());
      }
      while (e.hasMoreElements()) {
        String tempString = e.nextElement().toString();
        String[] parameterValues = request.getParameterValues(tempString);
        if (params.contains(tempString)) {
          result.append("<input type=\"hidden\" name=\"" + tempString + "\" value=\"" + parameterValues[0] + "\">\n");
        }
      }
    }
    return (result.toString());
  }


  /**
   *  Gets the modifiedString attribute of the Survey object
   *
   *@return    The modifiedString value
   */
  public String getModifiedString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the modifiedDateTimeString attribute of the Survey object
   *
   *@return    The modifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    String sql = 
      "INSERT INTO survey " +
      "(name, description, intro, itemLength, type, enteredBy, modifiedBy) " +
      "VALUES (?, ?, ?, ?, ?, ?, ?) ";
    try {
      db.setAutoCommit(false);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setString(++i, name);
      pst.setString(++i, description);
      pst.setString(++i, intro);
      pst.setInt(++i, itemLength);
      pst.setInt(++i, type);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, enteredBy);
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "survey_survey_id_seq");

      //Insert the questions
      Iterator x = questions.iterator();
      while (x.hasNext()) {
        SurveyQuestion thisQuestion = (SurveyQuestion) x.next();
        thisQuestion.insert(db, this.getId(), this.getType());
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
        "FROM campaign_survey_link " +
        "WHERE survey_id = " + this.getId());
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
      
      if (commit) {
        db.setAutoCommit(false);
      }
      st.executeUpdate("DELETE FROM survey_questions WHERE survey_id = " + this.getId());
      st.executeUpdate("DELETE FROM survey WHERE survey_id = " + this.getId());
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
      st.close();
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
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    
    if (this.getId() == -1) {
      throw new SQLException("Survey ID was not specified");
    }

    try {
      db.setAutoCommit(false);

      //Update the questions
      questions.delete(db, this.getId());
      Iterator x = questions.iterator();
      while (x.hasNext()) {
        SurveyQuestion thisQuestion = (SurveyQuestion) x.next();
        thisQuestion.insert(db, this.getId(), this.getType());
      }

      //Update the survey
      PreparedStatement pst = null;
      String sql = 
        "UPDATE survey " +
        "SET name = ?, description = ?, intro = ?, itemlength = ?, " +
        "type = ?, " +
        "enabled = ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
        "WHERE survey_id = ? AND modified = ? ";
      int i = 0;
      pst = db.prepareStatement(sql.toString());
      pst.setString(++i, this.getName());
      pst.setString(++i, this.getDescription());
      pst.setString(++i, this.getIntro());
      pst.setInt(++i, this.getItemLength());
      pst.setInt(++i, this.getType());
      pst.setBoolean(++i, this.getEnabled());
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getId());
      pst.setTimestamp(++i, modified);
      resultCount = pst.executeUpdate();
      pst.close();
      
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
    //survey table
    this.setId(rs.getInt("survey_id"));
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

    //lookup_survey_types table
    typeName = rs.getString("typename");
  }

}

