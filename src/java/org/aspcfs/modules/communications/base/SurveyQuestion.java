package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import java.util.ArrayList;

/**
 *  A Survey Question
 *
 *@author     matt rajkowski
 *@created    August 13, 2002
 *@version    $Id$
 */
public class SurveyQuestion {
  private int id = -1;
  private String description = null;
  private int surveyId = -1;
  private int type = -1;


  /**
   *  Constructor for the SurveyQuestion object
   */
  public SurveyQuestion() { }

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
   *@param  surveyType        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db, int surveyId, int surveyType) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO survey_questions " +
        "(survey_id, type, description) " +
        "VALUES " +
        "(?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, surveyId);
    pst.setInt(++i, surveyType);
    pst.setString(++i, description);
    pst.execute();
    pst.close();

    this.setId(DatabaseUtils.getCurrVal(db, "survey_question_question_id_seq"));
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  surveyId          Description of the Parameter
   *@param  surveyType        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void process(Connection db, int surveyId, int surveyType) throws SQLException {
    if (this.getId() == -1) {
      this.insert(db, surveyId, surveyType);
    } else {
      this.update(db, surveyId, surveyType);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  surveyId          Description of the Parameter
   *@param  surveyType        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void update(Connection db, int surveyId, int surveyType) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE survey_questions " +
        "SET survey_id = ?, type = ?, description = ? " +
        "WHERE question_id = ? ");
    int i = 0;
    pst.setInt(++i, surveyId);
    pst.setInt(++i, surveyType);
    pst.setString(++i, description);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
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
  }

}

