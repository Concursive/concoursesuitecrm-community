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

/**
 *  Description of the Class
 *
 *@author     Wesley S. Gillette
 *@created    November 1, 2001
 *@version    $Id$
 */
public class SearchOperator extends GenericBean {

  private int id = -1;
  private int dataTypeId = -1;
  private String dataType = "";
  private String operator = "";
  private String displayText = "";


  /**
   *  Constructor for the SearchOperator object
   *
   *@since
   */
  public SearchOperator() { }


  /**
   *  Constructor for the SearchOperator object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public SearchOperator(ResultSet rs) throws SQLException {
    buildSearchOperator(rs);
  }


  /**
   *  Sets the id attribute of the SearchOperator object
   *
   *@param  tmp  The new id value
   *@since
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the dataTypeId attribute of the SearchOperator object
   *
   *@param  tmp  The new dataTypeId value
   *@since
   */
  public void setDataTypeId(int tmp) {
    this.dataTypeId = tmp;
  }


  /**
   *  Sets the dataType attribute of the SearchOperator object
   *
   *@param  tmp  The new dataType value
   *@since
   */
  public void setDataType(String tmp) {
    this.dataType = tmp;
  }


  /**
   *  Sets the operator attribute of the SearchOperator object
   *
   *@param  tmp  The new operator value
   *@since
   */
  public void setOperator(String tmp) {
    this.operator = tmp;
  }


  /**
   *  Sets the displayText attribute of the SearchOperator object
   *
   *@param  tmp  The new displayText value
   *@since
   */
  public void setDisplayText(String tmp) {
    this.displayText = tmp;
  }


  /**
   *  Gets the id attribute of the SearchOperator object
   *
   *@return    The id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the dataTypeId attribute of the SearchOperator object
   *
   *@return    The dataTypeId value
   *@since
   */
  public int getDataTypeId() {
    return dataTypeId;
  }


  /**
   *  Gets the dataType attribute of the SearchOperator object
   *
   *@return    The dataType value
   *@since
   */
  public String getDataType() {
    return dataType;
  }


  /**
   *  Gets the operator attribute of the SearchOperator object
   *
   *@return    The operator value
   *@since
   */
  public String getOperator() {
    return operator;
  }


  /**
   *  Gets the displayText attribute of the SearchOperator object
   *
   *@return    The displayText value
   *@since
   */
  public String getDisplayText() {
    return displayText;
  }


  /**
   *  Populates this object from a result set
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  protected void buildSearchOperator(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("id"));
    dataTypeId = rs.getInt("data_typeid");
    dataType = rs.getString("data_type");
    operator = rs.getString("operator");
    displayText = rs.getString("display_text");
  }

}

