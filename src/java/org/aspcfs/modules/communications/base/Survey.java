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

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Dependency;

/**
 *  Represents a survey that can have introductory text and questions.
 *
 *@author     chris price
 *@created    August 7, 2002
 *@version    $Id$
 */
public class Survey extends SurveyBase {

  public final static int INCOMPLETE = 1;
  public final static int COMPLETE = 2;
  protected int id = -1;
  protected SurveyQuestionList questions = new SurveyQuestionList();

  protected int enteredBy = -1;
  protected int modifiedBy = -1;
  protected java.sql.Timestamp modified = null;
  protected java.sql.Timestamp entered = null;
  protected boolean enabled = true;
  //TODO: used later on to make sure that survey is not activated until all steps are completed
  protected int status = INCOMPLETE;


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

  public Survey(Connection db, String surveyId) throws SQLException {
    queryRecord(db, Integer.parseInt(surveyId));
  }


  /**
   *  Constructor for the Survey object
   *
   *@param  db                Description of the Parameter
   *@param  surveyId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Survey(Connection db, int surveyId) throws SQLException {
    queryRecord(db, surveyId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  surveyId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int surveyId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT s.* " +
        "FROM survey s " +
        "WHERE s.survey_id = ? ");
    pst.setInt(1, surveyId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Survey record not found.");
    }
    questions.setSurveyId(this.getId());
    questions.buildList(db);
  }


  /**
   *  Gets the enteredString attribute of the Survey object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Sets the requestItems attribute of the Survey object
   *
   *@param  request  The new requestItems value
   */
  public void setRequestItems(HttpServletRequest request) {
    questions = new SurveyQuestionList(request);
  }


  /**
   *  Sets the status attribute of the Survey object
   *
   *@param  status  The new status value
   */
  public void setStatus(int status) {
    this.status = status;
  }


  /**
   *  Gets the status attribute of the Survey object
   *
   *@return    The status value
   */
  public int getStatus() {
    return status;
  }


  /**
   *  Gets the questions attribute of the Survey object
   *
   *@return    The questions value
   */
  public SurveyQuestionList getQuestions() {
    return questions;
  }


  /**
   *  Sets the questions attribute of the Survey object
   *
   *@param  items  The new questions value
   */
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


  /**
   *  Gets the id attribute of the Survey class
   *
   *@param  db                Description of the Parameter
   *@param  campaignId        Description of the Parameter
   *@return                   The id value
   *@exception  SQLException  Description of the Exception
   */
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
    pst.close();
    return surveyId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  campaignId        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
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


  /**
   *  Sets the modified attribute of the Survey object
   *
   *@param  tmp  The new modified value
   */
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
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
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
        "(name, description, intro, outro, itemLength, type, status, enteredBy, modifiedBy) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    try {
      db.setAutoCommit(false);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setString(++i, name);
      pst.setString(++i, description);
      pst.setString(++i, intro);
      pst.setString(++i, outro);
      pst.setInt(++i, itemLength);
      pst.setInt(++i, 1);
      pst.setInt(++i, status);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, enteredBy);
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "survey_survey_id_seq");

      //Insert the questions
      Iterator x = questions.iterator();
      while (x.hasNext()) {
        SurveyQuestion thisQuestion = (SurveyQuestion) x.next();
        thisQuestion.process(db, this.getId());
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
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
            (inactiveCount == 1 ? "campaign is" : "campaigns are") +
            " being built that " +
            (inactiveCount == 1 ? "uses" : "use") +
            " this survey.");
        return false;
      }

      if (commit) {
        db.setAutoCommit(false);
      }

      Iterator x = questions.iterator();
      while (x.hasNext()) {
        SurveyQuestion thisQuestion = (SurveyQuestion) x.next();
        thisQuestion.delete(db, this.getId());
      }
      st.executeUpdate("DELETE FROM survey_questions WHERE survey_id = " + this.getId());
      st.executeUpdate("DELETE FROM survey WHERE survey_id = " + this.getId());
      st.close();
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
      System.out.println("Survey -- > Update Questions ");
      //update the question
      questions.process(db, this.getId());

      //Update the survey
      PreparedStatement pst = null;
      String sql =
          "UPDATE survey " +
          "SET name = ?, description = ?, intro = ?, outro = ?, itemlength = ?, " +
          "type = ?, " +
          "enabled = ?, " +
          "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
          "WHERE survey_id = ? AND modified = ? ";
      int i = 0;
      pst = db.prepareStatement(sql.toString());
      pst.setString(++i, this.getName());
      pst.setString(++i, this.getDescription());
      pst.setString(++i, this.getIntro());
      pst.setString(++i, this.getOutro());
      pst.setInt(++i, this.getItemLength());
      pst.setInt(++i, 1);
      pst.setBoolean(++i, this.getEnabled());
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getId());
      pst.setTimestamp(++i, modified);
      resultCount = pst.executeUpdate();
      pst.close();

      db.commit();
    } catch (Exception e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    ResultSet rs = null;
    String sql = null;
    DependencyList dependencyList = new DependencyList();
    try {
      db.setAutoCommit(false);
      sql = "SELECT COUNT(*) AS survey_count " +
          "FROM campaign_survey_link " +
          "WHERE survey_id = ? ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int surveycount = rs.getInt("survey_count") ;
        if (surveycount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Campaigns");
          thisDependency.setCount(surveycount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }

      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return dependencyList;
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
    outro = rs.getString("outro");
    itemLength = rs.getInt("itemlength");
    type = rs.getInt("type");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }

}

