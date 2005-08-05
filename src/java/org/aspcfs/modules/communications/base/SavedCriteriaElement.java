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
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id: SavedCriteriaElement.java,v 1.3 2003/01/14 20:40:41 akhi_m
 *          Exp $
 * @created January 14, 2003
 */
public class SavedCriteriaElement {

  private int savedCriteriaListId = -1;
  private int fieldId = -1;
  private String operator = null;
  private int operatorId = -1;
  private String value = null;
  private int source = -1;


  /**
   * Constructor for the SavedCriteriaElement object
   */
  public SavedCriteriaElement() {
  }


  /**
   * Sets the savedCriteriaListId attribute of the SavedCriteriaElement object
   *
   * @param tmp The new savedCriteriaListId value
   */
  public void setSavedCriteriaListId(int tmp) {
    this.savedCriteriaListId = tmp;
  }


  /**
   * Sets the savedCriteriaListId attribute of the SavedCriteriaElement object
   *
   * @param tmp The new savedCriteriaListId value
   */
  public void setSavedCriteriaListId(String tmp) {
    this.savedCriteriaListId = Integer.parseInt(tmp);
  }


  /**
   * Sets the fieldId attribute of the SavedCriteriaElement object
   *
   * @param tmp The new fieldId value
   */
  public void setFieldId(int tmp) {
    this.fieldId = tmp;
  }


  /**
   * Sets the fieldId attribute of the SavedCriteriaElement object
   *
   * @param tmp The new fieldId value
   */
  public void setFieldId(String tmp) {
    this.fieldId = Integer.parseInt(tmp);
  }


  /**
   * Sets the operator attribute of the SavedCriteriaElement object
   *
   * @param tmp The new operator value
   */
  public void setOperator(String tmp) {
    this.operator = tmp;
  }


  /**
   * Sets the operatorId attribute of the SavedCriteriaElement object
   *
   * @param tmp The new operatorId value
   */
  public void setOperatorId(int tmp) {
    this.operatorId = tmp;
  }


  /**
   * Sets the operatorId attribute of the SavedCriteriaElement object
   *
   * @param tmp The new operatorId value
   */
  public void setOperatorId(String tmp) {
    this.operatorId = Integer.parseInt(tmp);
  }


  /**
   * Sets the value attribute of the SavedCriteriaElement object
   *
   * @param tmp The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp;
  }


  /**
   * Sets the source attribute of the SavedCriteriaElement object
   *
   * @param tmp The new source value
   */
  public void setSource(int tmp) {
    this.source = tmp;
  }


  /**
   * Gets the savedCriteriaListId attribute of the SavedCriteriaElement object
   *
   * @return The savedCriteriaListId value
   */
  public int getSavedCriteriaListId() {
    return savedCriteriaListId;
  }


  /**
   * Gets the fieldId attribute of the SavedCriteriaElement object
   *
   * @return The fieldId value
   */
  public int getFieldId() {
    return fieldId;
  }


  /**
   * Gets the operator attribute of the SavedCriteriaElement object
   *
   * @return The operator value
   */
  public String getOperator() {
    return operator;
  }


  /**
   * Gets the operatorId attribute of the SavedCriteriaElement object
   *
   * @return The operatorId value
   */
  public int getOperatorId() {
    return operatorId;
  }


  /**
   * Gets the value attribute of the SavedCriteriaElement object
   *
   * @return The value value
   */
  public String getValue() {
    return value;
  }


  /**
   * Gets the source attribute of the SavedCriteriaElement object
   *
   * @return The source value
   */
  public int getSource() {
    return source;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
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
    savedCriteriaListId = DatabaseUtils.getNextSeq(
        db, "saved_criteriaelement_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO saved_criteriaelement " +
        "( " +
        (savedCriteriaListId > 0 ? "id, " : " ") +
        "field, operator, operatorid, value, source, value_id) VALUES " +
        "(" +
        (savedCriteriaListId > 0 ? "?, " : "") +
        "?, ?, ?, ?, ?, ?) ");
    int i = 0;
    if (savedCriteriaListId > 0) {
      pst.setInt(++i, savedCriteriaListId);
    }
    pst.setInt(++i, fieldId);
    pst.setString(++i, operator);
    pst.setInt(++i, operatorId);
    pst.setString(++i, value);
    pst.setInt(++i, source);
    int valueId = DatabaseUtils.parseInt(value, -1);
    DatabaseUtils.setInt(pst, ++i, valueId);
    pst.execute();
    pst.close();
    return true;
  }
}

