//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class SavedCriteriaElement {

  private int savedCriteriaListId = -1;
  private int fieldId = -1;
  private String operator = null;
  private int operatorId = -1;
  private String value = null;
  
  public SavedCriteriaElement() {}
  
  public void setSavedCriteriaListId(int tmp) { this.savedCriteriaListId = tmp; }
  public void setSavedCriteriaListId(String tmp) { this.savedCriteriaListId = Integer.parseInt(tmp); }
  
  public void setFieldId(int tmp) { this.fieldId = tmp; }
  public void setFieldId(String tmp) { this.fieldId = Integer.parseInt(tmp); }
  
  public void setOperator(String tmp) { this.operator = tmp; }
  
  public void setOperatorId(int tmp) { this.operatorId = tmp; }
  public void setOperatorId(String tmp) { this.operatorId = Integer.parseInt(tmp); }
  
  public void setValue(String tmp) { this.value = tmp; }
  
  public int getSavedCriteriaListId() { return savedCriteriaListId; }
  public int getFieldId() { return fieldId; }
  public String getOperator() { return operator; }
  public int getOperatorId() { return operatorId; }
  public String getValue() { return value; }

  
  public boolean insert(Connection db) throws SQLException {
    if (savedCriteriaListId == -1) {
      throw new SQLException("SavedCriteriaList ID not specified");
    }
    if (fieldId == -1) {
      throw new SQLException("Field ID not specified");
    }
    if (operatorId == -1) {
      throw new SQLException("Field ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
      "INSERT INTO saved_criteriaelement " +
      "(id, field, operator, operatorid, value) VALUES " +
      "(?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, savedCriteriaListId);
    pst.setInt(++i, fieldId);
    pst.setString(++i, operator);
    pst.setInt(++i, operatorId);
    pst.setString(++i, value);
    pst.execute();
    pst.close();
    return true;
  }
}
