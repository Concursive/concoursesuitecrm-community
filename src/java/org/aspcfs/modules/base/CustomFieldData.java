//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class CustomFieldData extends GenericBean {

  private int recordId = -1;
  private int fieldId = -1;
  private int selectedItemId = -1;
  private String enteredValue = null;
  private int enteredNumber = 0;
  private double enteredDouble = 0;
  
  public CustomFieldData() {}
  
  public CustomFieldData(ResultSet rs) throws SQLException {
    build(rs);
  }
  
  public void setRecordId(int tmp) { this.recordId = tmp; }
  public void setRecordId(String tmp) { this.recordId = Integer.parseInt(tmp); }
  public void setFieldId(int tmp) { this.fieldId = tmp; }
  public void setFieldId(String tmp) { this.fieldId = Integer.parseInt(tmp); }
  public void setSelectedItemId(int tmp) { this.selectedItemId = tmp; }
  public void setSelectedItemId(String tmp) { this.selectedItemId = Integer.parseInt(tmp); }
  public void setEnteredValue(String tmp) { this.enteredValue = tmp; }
  public void setEnteredNumber(int tmp) { this.enteredNumber = tmp; }
  public void setEnteredNumber(String tmp) { this.enteredNumber = Integer.parseInt(tmp); }
  public void setEnteredDouble(double tmp) { this.enteredDouble = tmp; }
  public void setEnteredDouble(String tmp) { this.enteredDouble = Double.parseDouble(tmp); }
  
  public int getRecordId() { return recordId; }
  public int getFieldId() { return fieldId; }
  public int getSelectedItemId() { return selectedItemId; }
  public String getEnteredValue() { return enteredValue; }
  public int getEnteredNumber() { return enteredNumber; }
  public double getEnteredDouble() { return enteredDouble; }

  
  public void build(ResultSet rs) throws SQLException {
    recordId = rs.getInt("record_id");
    fieldId = rs.getInt("field_id");
    selectedItemId = rs.getInt("selected_item_id");
    enteredValue = rs.getString("entered_value");
    enteredNumber = rs.getInt("entered_number");
    enteredDouble = rs.getDouble("entered_float");
  }
  
  public boolean insert(Connection db) throws SQLException {
/*    if (!this.isValid()) {
      return false;
    }
*/
    String sql =
        "INSERT INTO custom_field_data " +
        "(record_id, field_id, selected_item_id, entered_value, entered_number, entered_float ) " +
        "VALUES (?, ?, ?, ?, ?, ?) ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, recordId);
    pst.setInt(++i, fieldId);
    pst.setInt(++i, selectedItemId);
    pst.setString(++i, enteredValue);
    pst.setInt(++i, enteredNumber);
    pst.setDouble(++i, enteredDouble);
    pst.execute();
    pst.close();
    return true;
  }

}
