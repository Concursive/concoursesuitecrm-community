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
package org.aspcfs.modules.admin.base;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    November 9, 2005
 */
public class CustomListViewList extends ArrayList {
  private int editorId = -1;
  private boolean buildFields = false;


  /**
   *  Gets the buildFields attribute of the CustomListViewList object
   *
   * @return    The buildFields value
   */
  public boolean getBuildFields() {
    return buildFields;
  }


  /**
   *  Sets the buildFields attribute of the CustomListViewList object
   *
   * @param  tmp  The new buildFields value
   */
  public void setBuildFields(boolean tmp) {
    this.buildFields = tmp;
  }


  /**
   *  Sets the buildFields attribute of the CustomListViewList object
   *
   * @param  tmp  The new buildFields value
   */
  public void setBuildFields(String tmp) {
    this.buildFields = DatabaseUtils.parseBoolean(tmp);
  }



  /**
   *  Gets the editorId attribute of the CustomListViewList object
   *
   * @return    The editorId value
   */
  public int getEditorId() {
    return editorId;
  }


  /**
   *  Sets the editorId attribute of the CustomListViewList object
   *
   * @param  tmp  The new editorId value
   */
  public void setEditorId(int tmp) {
    this.editorId = tmp;
  }


  /**
   *  Sets the editorId attribute of the CustomListViewList object
   *
   * @param  tmp  The new editorId value
   */
  public void setEditorId(String tmp) {
    this.editorId = Integer.parseInt(tmp);
  }


  /**
   *  Constructor for the CustomListViewList object
   */
  public CustomListViewList() { }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer("");
    StringBuffer sqlCount = new StringBuffer("");
    StringBuffer sqlFilter = new StringBuffer("");
    StringBuffer sqlOrder = new StringBuffer("");

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
        " FROM custom_list_view clv" +
        " WHERE clv.view_id > 0 ");
    createFilter(sqlFilter, db);
    sqlOrder.append("ORDER BY name ");

    sqlSelect.append("SELECT ");
    sqlSelect.append(
        "clv.* FROM custom_list_view clv " +
        "WHERE clv.view_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();

    while (rs.next()) {
      CustomListView customListView = new CustomListView(rs);
      this.add(customListView);
    }
    rs.close();
    pst.close();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomListView thisView = (CustomListView) i.next();
      thisView.buildFields(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of the Parameter
   * @param  db         Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    if (editorId > -1) {
      sqlFilter.append("AND clv.editor_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (editorId > -1) {
      pst.setInt(++i, editorId);
    }
    return i;
  }
}

