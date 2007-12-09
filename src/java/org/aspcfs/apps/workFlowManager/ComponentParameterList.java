/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.apps.workFlowManager;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Contains a list of ComponentParameter objects and can be used for initially
 * building the list.
 *
 * @author matt rajkowski
 * @version $Id: ComponentParameterList.java,v 1.1.2.1 2003/06/11 18:32:13
 *          mrajkowski Exp $
 * @created June 6, 2003
 */
public class ComponentParameterList extends ArrayList {
  private int componentId = -1;
  private int enabled = Constants.UNDEFINED;


  /**
   * Constructor for the ComponentParameterList object
   */
  public ComponentParameterList() {
  }


  /**
   * Sets the componentId attribute of the ComponentParameterList object
   *
   * @param tmp The new componentId value
   */
  public void setComponentId(int tmp) {
    this.componentId = tmp;
  }


  /**
   * Sets the componentId attribute of the ComponentParameterList object
   *
   * @param tmp The new componentId value
   */
  public void setComponentId(String tmp) {
    this.componentId = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the ComponentParameterList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ComponentParameterList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Gets the componentId attribute of the ComponentParameterList object
   *
   * @return The componentId value
   */
  public int getComponentId() {
    return componentId;
  }


  /**
   * Gets the enabled attribute of the ComponentParameterList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Builds a list of parameters from a database using selected filters
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    sqlSelect.append(
        "SELECT p.id, p.component_id, p.parameter_id, p.param_value, " +
            "p.enabled, pl.param_name " +
            "FROM " + DatabaseUtils.getTableName(db, "business_process_component_parameter") + " p, " +
            DatabaseUtils.getTableName(db, "business_process_parameter_library") + " pl " +
            "WHERE p.id > 0 " +
            "AND p.parameter_id = pl.parameter_id ");
    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY id ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ComponentParameter thisParameter = new ComponentParameter(rs);
      this.add(thisParameter);
    }
    rs.close();
    pst.close();
  }


  /**
   * Adds filters to the WHERE clause of the database query
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (componentId > -1) {
      sqlFilter.append("AND p.component_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND p.enabled = ? ");
    }
  }


  /**
   * Adds database parameters based on selected filters
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (componentId > -1) {
      pst.setInt(++i, componentId);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, enabled == Constants.TRUE);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    Iterator params = this.iterator();
    while (params.hasNext()) {
      ComponentParameter param = (ComponentParameter) params.next();
      param.setComponentId(componentId);
      param.insert(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      ComponentParameter tmpComponentParameter = (ComponentParameter) itr.next();
      tmpComponentParameter.delete(db);
    }
  }

}
