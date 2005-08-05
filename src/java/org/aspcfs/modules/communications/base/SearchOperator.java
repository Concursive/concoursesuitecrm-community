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

import com.darkhorseventures.framework.beans.GenericBean;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author Wesley S. Gillette
 * @version $Id$
 * @created November 1, 2001
 */
public class SearchOperator extends GenericBean {

  private int id = -1;
  private int dataTypeId = -1;
  private String dataType = "";
  private String operator = "";
  private String displayText = "";


  /**
   * Constructor for the SearchOperator object
   */
  public SearchOperator() {
  }


  /**
   * Constructor for the SearchOperator object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  public SearchOperator(ResultSet rs) throws SQLException {
    buildSearchOperator(rs);
  }


  /**
   * Sets the id attribute of the SearchOperator object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the dataTypeId attribute of the SearchOperator object
   *
   * @param tmp The new dataTypeId value
   */
  public void setDataTypeId(int tmp) {
    this.dataTypeId = tmp;
  }


  /**
   * Sets the dataType attribute of the SearchOperator object
   *
   * @param tmp The new dataType value
   */
  public void setDataType(String tmp) {
    this.dataType = tmp;
  }


  /**
   * Sets the operator attribute of the SearchOperator object
   *
   * @param tmp The new operator value
   */
  public void setOperator(String tmp) {
    this.operator = tmp;
  }


  /**
   * Sets the displayText attribute of the SearchOperator object
   *
   * @param tmp The new displayText value
   */
  public void setDisplayText(String tmp) {
    this.displayText = tmp;
  }


  /**
   * Gets the id attribute of the SearchOperator object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the dataTypeId attribute of the SearchOperator object
   *
   * @return The dataTypeId value
   */
  public int getDataTypeId() {
    return dataTypeId;
  }


  /**
   * Gets the dataType attribute of the SearchOperator object
   *
   * @return The dataType value
   */
  public String getDataType() {
    return dataType;
  }


  /**
   * Gets the operator attribute of the SearchOperator object
   *
   * @return The operator value
   */
  public String getOperator() {
    return operator;
  }


  /**
   * Gets the displayText attribute of the SearchOperator object
   *
   * @return The displayText value
   */
  public String getDisplayText() {
    return displayText;
  }


  /**
   * Populates this object from a result set
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  protected void buildSearchOperator(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("id"));
    dataTypeId = rs.getInt("data_typeid");
    dataType = rs.getString("data_type");
    operator = rs.getString("operator");
    displayText = rs.getString("display_text");
  }

}

