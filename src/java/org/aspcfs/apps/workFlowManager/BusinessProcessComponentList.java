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
package org.aspcfs.apps.workFlowManager;

import java.util.*;
import org.w3c.dom.Element;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;
import java.io.*;
import java.sql.*;

/**
 *  Contains a list of BusinessProcessComponent objects and can be used for
 *  initially building the list.
 *
 *@author     matt rajkowski
 *@created    June 6, 2003
 *@version    $Id: BusinessProcessComponentList.java,v 1.2 2003/06/19 20:50:05
 *      mrajkowski Exp $
 */
public class BusinessProcessComponentList extends HashMap {
  private int enabled = Constants.UNDEFINED;
  private int processId = -1;
  private int componentId = -1;


  /**
   *  Constructor for the BusinessProcessComponentList object
   */
  public BusinessProcessComponentList() { }


  /**
   *  Sets the enabled attribute of the BusinessProcessComponentList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the BusinessProcessComponentList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the processId attribute of the BusinessProcessComponentList object
   *
   *@param  tmp  The new processId value
   */
  public void setProcessId(int tmp) {
    this.processId = tmp;
  }


  /**
   *  Sets the processId attribute of the BusinessProcessComponentList object
   *
   *@param  tmp  The new processId value
   */
  public void setProcessId(String tmp) {
    this.processId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the componentId attribute of the BusinessProcessComponentList object
   *
   *@param  tmp  The new componentId value
   */
  public void setComponentId(int tmp) {
    this.componentId = tmp;
  }


  /**
   *  Sets the componentId attribute of the BusinessProcessComponentList object
   *
   *@param  tmp  The new componentId value
   */
  public void setComponentId(String tmp) {
    this.componentId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the enabled attribute of the BusinessProcessComponentList object
   *
   *@return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the processId attribute of the BusinessProcessComponentList object
   *
   *@return    The processId value
   */
  public int getProcessId() {
    return processId;
  }


  /**
   *  Gets the componentId attribute of the BusinessProcessComponentList object
   *
   *@return    The componentId value
   */
  public int getComponentId() {
    return componentId;
  }


  /**
   *  Builds a list of components based on selected filters
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    //Get a list of business processes and store the name and the object
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    sqlSelect.append(
        "SELECT c.id, c.process_id, c.component_id, c.parent_id, " +
        "c.parent_result_id, c.enabled, " +
        "cl.component_name, cl.class_name, cl.description " +
        "FROM business_process_component c, business_process_component_library cl " +
        "WHERE c.id > 0 " +
        "AND c.component_id = cl.component_id ");
    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY id ");
    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      BusinessProcessComponent thisComponent = new BusinessProcessComponent(rs);
      this.put(new Integer(thisComponent.getId()), thisComponent);
    }
    rs.close();
    pst.close();
    //Build component resources
    Iterator i = this.values().iterator();
    while (i.hasNext()) {
      BusinessProcessComponent thisComponent = (BusinessProcessComponent) i.next();
      thisComponent.buildResources(db);
    }
  }


  /**
   *  Adds filters to the WHERE clause of the database query
   *
   *@param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (processId > -1) {
      sqlFilter.append("AND process_id = ? ");
    }
    if (componentId > -1) {
      sqlFilter.append("AND component_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
    }
  }


  /**
   *  Adds parameters to the query based on the selected filters
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (processId > -1) {
      pst.setInt(++i, processId);
    }
    if (componentId > -1) {
      pst.setInt(++i, componentId);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, enabled == Constants.TRUE);
    }
    return i;
  }
}

