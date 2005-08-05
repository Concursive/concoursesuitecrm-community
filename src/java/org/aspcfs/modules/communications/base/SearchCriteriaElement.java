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

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

/**
 * An element represents a specific search choice, and can be combined with
 * other elements when forming a search query.
 *
 * @author Wesley_S_Gillette
 * @version $Id: SearchCriteriaElement.java,v 1.1 2001/11/13 20:36:35
 * @created November 12, 2001
 */
public class SearchCriteriaElement {
  //Properties
  int fieldId = -1;
  int operatorId = -1;
  int sourceId = -1;
  //Display/Lookup Properties
  String text = null;
  String operator = null;
  String dataType = null;
  String operatorDisplayText = null;
  String contactTypeName = null;
  String accountTypeName = null;
  String contactNameFirst = null;
  String contactNameLast = null;


  /**
   * SearchCriteriaElement default constructor
   *
   * @since 1.1
   */
  public SearchCriteriaElement() {
  }


  /**
   * SearchCriteriaElement constructor that parses a delimited string and
   * populates the object based on that
   *
   * @param elementText delimited string to be parsed
   * @since 1.1
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
    if (st.hasMoreTokens()) {
      sourceId = Integer.parseInt((String) st.nextToken());
    }
  }


  /**
   * Constructor for the SearchCriteriaElement object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  public SearchCriteriaElement(ResultSet rs) throws SQLException {

    buildRecord(rs);
  }


  /**
   * Sets the SourceId attribute of the SearchCriteriaElement object
   *
   * @param sourceId The new SourceId value
   */
  public void setSourceId(int sourceId) {
    this.sourceId = sourceId;
  }


  /**
   * Sets the SourceId attribute of the SearchCriteriaElement object
   *
   * @param sourceId The new SourceId value
   */
  public void setSourceId(String sourceId) {
    this.sourceId = Integer.parseInt(sourceId);
  }


  /**
   * Sets the AccountTypeName attribute of the SearchCriteriaElement object
   *
   * @param accountTypeName The new AccountTypeName value
   */
  public void setAccountTypeName(String accountTypeName) {
    this.accountTypeName = accountTypeName;
  }


  /**
   * Sets the contactTypeName attribute of the SearchCriteriaElement object
   *
   * @param contactTypeName The new contactTypeName value
   */
  public void setContactTypeName(String contactTypeName) {
    this.contactTypeName = contactTypeName;
  }


  /**
   * Sets the contactNameFirst attribute of the SearchCriteriaElement object
   *
   * @param tmp The new contactNameFirst value
   */
  public void setContactNameFirst(String tmp) {
    this.contactNameFirst = tmp;
  }


  /**
   * Sets the contactNameLast attribute of the SearchCriteriaElement object
   *
   * @param tmp The new contactNameLast value
   */
  public void setContactNameLast(String tmp) {
    this.contactNameLast = tmp;
  }


  /**
   * Gets the fieldId attribute of the SearchCriteriaElement object
   *
   * @param fieldId The new fieldId value
   * @since 1.1
   */

  public void setFieldId(int fieldId) {
    this.fieldId = fieldId;
  }


  /**
   * Sets the DataType attribute of the SearchCriteriaElement object
   *
   * @param dataType The new DataType value
   */
  public void setDataType(String dataType) {
    this.dataType = dataType;
  }


  /**
   * Sets the Operator attribute of the SearchCriteriaElement object
   *
   * @param operator The new Operator value
   */
  public void setOperator(String operator) {
    this.operator = operator;
  }


  /**
   * Sets the operatorId attribute of the SearchCriteriaElement object
   *
   * @param operatorId The new operatorId value
   * @since 1.1
   */
  public void setOperatorId(int operatorId) {
    this.operatorId = operatorId;
  }


  /**
   * Sets the operatorDisplayText attribute of the SearchCriteriaElement object
   *
   * @param tmp The new operatorDisplayText value
   */
  public void setOperatorDisplayText(String tmp) {
    this.operatorDisplayText = tmp;
  }


  /**
   * Sets the text attribute of the SearchCriteriaElement object
   *
   * @param text The new text value
   * @since 1.1
   */
  public void setText(String text) {
    this.text = text;
  }


  /**
   * Gets the SourceId attribute of the SearchCriteriaElement object
   *
   * @return The SourceId value
   */
  public int getSourceId() {
    return sourceId;
  }


  /**
   * Gets the AccountTypeName attribute of the SearchCriteriaElement object
   *
   * @return The AccountTypeName value
   */
  public String getAccountTypeName() {
    return accountTypeName;
  }


  /**
   * Gets the contactTypeName attribute of the SearchCriteriaElement object
   *
   * @return The contactTypeName value
   */
  public String getContactTypeName() {
    return contactTypeName;
  }


  /**
   * Gets the contactNameFirst attribute of the SearchCriteriaElement object
   *
   * @return The contactNameFirst value
   */
  public String getContactNameFirst() {
    return contactNameFirst;
  }


  /**
   * Gets the contactNameLast attribute of the SearchCriteriaElement object
   *
   * @return The contactNameLast value
   */
  public String getContactNameLast() {
    return contactNameLast;
  }


  /**
   * Gets the DataType attribute of the SearchCriteriaElement object
   *
   * @return The DataType value
   */
  public String getDataType() {
    return dataType;
  }


  /**
   * Gets the Operator attribute of the SearchCriteriaElement object
   *
   * @return The Operator value
   */
  public String getOperator() {
    return operator;
  }


  /**
   * Gets the operatorDisplayText attribute of the SearchCriteriaElement object
   *
   * @return The operatorDisplayText value
   */
  public String getOperatorDisplayText() {
    return operatorDisplayText;
  }


  /**
   * Gets the fieldId attribute of the SearchCriteriaElement object
   *
   * @return The fieldId value
   * @since 1.1
   */
  public int getFieldId() {
    return fieldId;
  }


  /**
   * Gets the FieldIdAsString attribute of the SearchCriteriaElement object
   *
   * @return The FieldIdAsString value
   */
  public String getFieldIdAsString() {
    return String.valueOf(fieldId);
  }


  /**
   * Gets the operatorId attribute of the SearchCriteriaElement object
   *
   * @return The operatorId value
   * @since 1.1
   */
  public int getOperatorId() {
    return operatorId;
  }


  /**
   * Gets the OperatorIdAsString attribute of the SearchCriteriaElement object
   *
   * @return The OperatorIdAsString value
   */
  public String getOperatorIdAsString() {
    return String.valueOf(operatorId);
  }


  /**
   * Gets the text attribute of the SearchCriteriaElement object
   *
   * @return The text value
   * @since 1.1
   */
  public String getText() {
    return text;
  }


  /**
   * Retrieves descriptor information from the field_types table that pertains
   * to the operator that is associated with this SearchCriteriaElement
   *
   * @param db db connection
   * @throws SQLException SQL Exception
   */
  public void buildOperatorData(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM field_types " +
        "WHERE id = ?");
    pst.setInt(1, this.getOperatorId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.dataType = rs.getString("data_type");
      this.operator = rs.getString("operator");
      this.operatorDisplayText = rs.getString("display_text");
    }
    rs.close();
    pst.close();
  }


  /**
   * Inserts this SearchCriteriaElement into the database
   *
   * @param listid unique ID of the SearchCriteriaList that contains
   *               this element
   * @param db     db connection
   * @throws SQLException SQL Exception
   */
  public boolean insert(int listid, Connection db) throws SQLException {
    this.buildOperatorData(db);
    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO saved_criteriaelement " +
          "(id, field, operator, operatorid, value, source, value_id) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?) ");
      int i = 0;
      pst.setInt(++i, listid);
      pst.setInt(++i, this.getFieldId());
      pst.setString(++i, this.getOperator());
      pst.setInt(++i, this.getOperatorId());
      pst.setString(++i, this.getText());
      pst.setInt(++i, this.getSourceId());
      int valueId = DatabaseUtils.parseInt(this.getText(), -1);
      DatabaseUtils.setInt(pst, ++i, valueId);
      pst.execute();
      pst.close();
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   * Retrieves element information in string format
   *
   * @return String representation of this element's info
   */
  public String toString() {
    StringBuffer tmp = new StringBuffer();
    tmp.append("=[ SearchCriteriaElement ]=====================\r\n");
    tmp.append("FieldId: " + fieldId + "\r\n");
    tmp.append("Operator: " + operator + "\r\n");
    tmp.append("OperatorId: " + operatorId + "\r\n");
    tmp.append("Operator Text: " + operatorDisplayText + "\r\n");
    tmp.append("Text: " + text + "\r\n");
    return tmp.toString();
  }


  /**
   * Populates object from ResultSet data
   *
   * @param rs data resultset from a query
   * @throws SQLException SQL Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //saved_criteriaelement table
    fieldId = rs.getInt("field");
    operator = rs.getString("operator");
    operatorId = rs.getInt("operatorid");
    text = rs.getString("value");
    sourceId = rs.getInt("source");
    //lookup_contact_types table
    contactTypeName = rs.getString("ctype");
    accountTypeName = rs.getString("atype");
    //contact table
    contactNameFirst = rs.getString("cnamefirst");
    contactNameLast = rs.getString("cnamelast");
  }
}

