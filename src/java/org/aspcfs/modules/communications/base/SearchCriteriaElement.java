//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.StringTokenizer;
import java.sql.*;

/**
 *  An element represents a specific search choice, and can be combined with
 *  other elements when forming a search query.
 *
 *@author     Wesley_S_Gillette
 *@created    November 12, 2001
 *@version    $Id: SearchCriteriaElement.java,v 1.1 2001/11/13 20:36:35
 *      mrajkowski Exp $
 */
public class SearchCriteriaElement {

  int fieldId = -1;
  int operatorId = -1;
  String text = null;
  String operator = null;
  String dataType = null;
  String operatorDisplayText = null;
  String contactTypeName = null;
  String contactNameFirst = null;
  String contactNameLast = null;


  /**
   *  Description of the Method
   *
   *@since    1.1
   */
  public SearchCriteriaElement() { }


  /**
   *  Description of the Method
   *
   *@param  elementText  Description of Parameter
   *@since               1.1
   */
  public SearchCriteriaElement(String elementText) {
    StringTokenizer st = new StringTokenizer(elementText, "[*|]");
    if (st.hasMoreTokens()) {
      fieldId = Integer.parseInt((String) st.nextToken());
    }
    if (st.hasMoreTokens()) {
      operatorId = Integer.parseInt((String) st.nextToken());
    }
    if (st.hasMoreTokens()) {
      text = (String) st.nextToken();
    }
  }


  /**
   *  Constructor for the SearchCriteriaElement object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public SearchCriteriaElement(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the contactTypeName attribute of the SearchCriteriaElement object
   *
   *@param  contactTypeName  The new contactTypeName value
   */
  public void setContactTypeName(String contactTypeName) {
    this.contactTypeName = contactTypeName;
  }


  /**
   *  Sets the contactNameFirst attribute of the SearchCriteriaElement object
   *
   *@param  tmp  The new contactNameFirst value
   */
  public void setContactNameFirst(String tmp) {
    this.contactNameFirst = tmp;
  }


  /**
   *  Sets the contactNameLast attribute of the SearchCriteriaElement object
   *
   *@param  tmp  The new contactNameLast value
   */
  public void setContactNameLast(String tmp) {
    this.contactNameLast = tmp;
  }


  /**
   *  Gets the fieldId attribute of the SearchCriteriaElement object
   *
   *@param  fieldId  The new fieldId value
   *@since           1.1
   */

  public void setFieldId(int fieldId) {
    this.fieldId = fieldId;
  }


  /**
   *  Sets the DataType attribute of the SearchCriteriaElement object
   *
   *@param  dataType  The new DataType value
   *@since
   */
  public void setDataType(String dataType) {
    this.dataType = dataType;
  }


  /**
   *  Sets the Operator attribute of the SearchCriteriaElement object
   *
   *@param  operator  The new Operator value
   *@since
   */
  public void setOperator(String operator) {
    this.operator = operator;
  }


  /**
   *  Sets the operatorId attribute of the SearchCriteriaElement object
   *
   *@param  operatorId  The new operatorId value
   *@since              1.1
   */
  public void setOperatorId(int operatorId) {
    this.operatorId = operatorId;
  }


  /**
   *  Sets the operatorDisplayText attribute of the SearchCriteriaElement object
   *
   *@param  tmp  The new operatorDisplayText value
   */
  public void setOperatorDisplayText(String tmp) {
    this.operatorDisplayText = tmp;
  }


  /**
   *  Sets the text attribute of the SearchCriteriaElement object
   *
   *@param  text  The new text value
   *@since        1.1
   */
  public void setText(String text) {
    this.text = text;
  }


  /**
   *  Gets the contactTypeName attribute of the SearchCriteriaElement object
   *
   *@return    The contactTypeName value
   */
  public String getContactTypeName() {
    return contactTypeName;
  }


  /**
   *  Gets the contactNameFirst attribute of the SearchCriteriaElement object
   *
   *@return    The contactNameFirst value
   */
  public String getContactNameFirst() {
    return contactNameFirst;
  }


  /**
   *  Gets the contactNameLast attribute of the SearchCriteriaElement object
   *
   *@return    The contactNameLast value
   */
  public String getContactNameLast() {
    return contactNameLast;
  }


  /**
   *  Gets the DataType attribute of the SearchCriteriaElement object
   *
   *@return    The DataType value
   *@since
   */
  public String getDataType() {
    return dataType;
  }


  /**
   *  Gets the Operator attribute of the SearchCriteriaElement object
   *
   *@return    The Operator value
   *@since
   */
  public String getOperator() {
    return operator;
  }


  /**
   *  Gets the operatorDisplayText attribute of the SearchCriteriaElement object
   *
   *@return    The operatorDisplayText value
   */
  public String getOperatorDisplayText() {
    return operatorDisplayText;
  }


  /**
   *  Gets the fieldId attribute of the SearchCriteriaElement object
   *
   *@return    The fieldId value
   *@since     1.1
   */
  public int getFieldId() {
    return fieldId;
  }


  /**
   *  Gets the FieldIdAsString attribute of the SearchCriteriaElement object
   *
   *@return    The FieldIdAsString value
   *@since
   */
  public String getFieldIdAsString() {
    return "" + fieldId;
  }


  /**
   *  Gets the operatorId attribute of the SearchCriteriaElement object
   *
   *@return    The operatorId value
   *@since     1.1
   */
  public int getOperatorId() {
    return operatorId;
  }


  /**
   *  Gets the OperatorIdAsString attribute of the SearchCriteriaElement object
   *
   *@return    The OperatorIdAsString value
   *@since
   */
  public String getOperatorIdAsString() {
    return "" + operatorId;
  }


  /**
   *  Gets the text attribute of the SearchCriteriaElement object
   *
   *@return    The text value
   *@since     1.1
   */
  public String getText() {
    return text;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void buildOperatorData(Connection db) throws SQLException {
    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT * " +
        "FROM field_types " +
        "WHERE id = " + this.getOperatorId());
    if (rs.next()) {
      this.dataType = rs.getString("data_type");
      this.operator = rs.getString("operator");
      this.operatorDisplayText = rs.getString("display_text");
    }
    rs.close();
    st.close();
  }


  /**
   *  Description of the Method
   *
   *@param  listid            Description of Parameter
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void insert(int listid, Connection db) throws SQLException {

    StringBuffer sql = new StringBuffer();

    this.buildOperatorData(db);

    try {
      sql.append(
          "INSERT INTO saved_criteriaelement ( id, field, operator, operatorid, value ) " +
          "VALUES ( ?, ?, ?, ?, ? ) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, listid);
      pst.setInt(++i, this.getFieldId());
      pst.setString(++i, this.getOperator());
      pst.setInt(++i, this.getOperatorId());
      pst.setString(++i, this.getText());
      pst.execute();
      pst.close();

      db.commit();
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
   *@return    Description of the Returned Value
   *@since
   */
  public String toString() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("=[ SearchCriteriaElement ]=====================\r\n");
    tmp.append("FieldId: " + fieldId + "\r\n");
    tmp.append("OperatorId: " + operatorId + "\r\n");
    tmp.append("Operator: " + operator + "\r\n");
    tmp.append("Operator Text: " + operatorDisplayText + "\r\n");
    tmp.append("Text: " + text + "\r\n");
    return tmp.toString();
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //saved_criteriaelement table
    fieldId = rs.getInt("field");
    operatorId = rs.getInt("operatorid");
    operator = rs.getString("operator");
    text = rs.getString("value");
    
    //lookup_contact_types table
    contactTypeName = rs.getString("ctype");
    
    //contact table
    contactNameFirst = rs.getString("cnamefirst");
    contactNameLast = rs.getString("cnamelast");
  }

}

