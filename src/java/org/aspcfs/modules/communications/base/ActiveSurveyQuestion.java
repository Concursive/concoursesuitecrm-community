package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import java.util.ArrayList;

public class ActiveSurveyQuestion {
  private int id = -1;
  private String description = null;
  private int activeSurveyId = -1;
  private int type = -1;
  private double average = 0.0;

  private ArrayList responseTotals = new ArrayList();

  public ActiveSurveyQuestion() { }
  
  public ActiveSurveyQuestion(SurveyQuestion question) { 
    this.description = question.getDescription();
    this.type = question.getType();
  }
  
  public ActiveSurveyQuestion(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  public int getId() {
    return id;
  }


  public String getDescription() {
    return description;
  }


  public void setId(int tmp) {
    this.id = tmp;
  }


  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  public void setDescription(String tmp) {
    this.description = tmp;
  }


  public void setActiveSurveyId(int tmp) {
    this.activeSurveyId = tmp;
  }


  public void setType(int tmp) {
    this.type = tmp;
  }


  public int getActiveSurveyId() {
    return activeSurveyId;
  }


  public int getType() {
    return type;
  }


  public void setActiveSurveyId(String tmp) {
    this.activeSurveyId = Integer.parseInt(tmp);
  }


  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  public ArrayList getResponseTotals() {
    return responseTotals;
  }


  public void setResponseTotals(ArrayList responseTotals) {
    this.responseTotals = responseTotals;
  }


  public double getAverage() {
    return average;
  }


  public void setAverage(double average) {
    this.average = average;
  }


  public String getAverageValue() {
    double value_2dp = (double) Math.round(average * 100.0) / 100.0;
    String toReturn = "" + value_2dp;
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  public void insert(Connection db, int activeSurveyId, int surveyType) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO active_survey_questions " +
        "(active_survey_id, type, description) " +
        "VALUES " +
        "(?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, activeSurveyId);
    pst.setInt(++i, surveyType);
    pst.setString(++i, description);
    pst.execute();
    pst.close();

    this.setId(DatabaseUtils.getCurrVal(db, "active_survey_q_question_id_seq"));
  }


  public void update(Connection db, int activeSurveyId, int surveyType) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE active_survey_questions " +
        "SET active_survey_id = ?, type = ?, description = ? " +
        "WHERE question_id = ? ");
    int i = 0;
    pst.setInt(++i, activeSurveyId);
    pst.setInt(++i, surveyType);
    pst.setString(++i, description);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }


  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("question_id");
    activeSurveyId = rs.getInt("active_survey_id");
    type = rs.getInt("type");
    description = rs.getString("description");
    average = rs.getDouble("average");
    responseTotals.add(0, new Integer(rs.getInt("total1")));
    responseTotals.add(1, new Integer(rs.getInt("total2")));
    responseTotals.add(2, new Integer(rs.getInt("total3")));
    responseTotals.add(3, new Integer(rs.getInt("total4")));
    responseTotals.add(4, new Integer(rs.getInt("total5")));
    responseTotals.add(5, new Integer(rs.getInt("total6")));
    responseTotals.add(6, new Integer(rs.getInt("total7")));
  }

}

