/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.base.Constants;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    January 15, 2003
 *@version    $Id: IssueCategoryList.java,v 1.3.134.2 2004/04/08 14:55:53
 *      rvasista Exp $
 */
public class IssueCategoryList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private Project project = null;
  private int projectId = -1;
  private int enabled = -1;


  /**
   *  Constructor for the IssueCategoryList object
   */
  public IssueCategoryList() { }


  /**
   *  Sets the pagedListInfo attribute of the IssueCategoryList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the projectId attribute of the IssueCategoryList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the enabled attribute of the IssueCategoryList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the IssueCategoryList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the project attribute of the IssueCategoryList object
   *
   *@param  tmp  The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) as recordcount " +
        "FROM project_issues_categories c " +
        "WHERE c.category_id > 0 ");
    createFilter(sqlFilter, db);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine column to sort by
      pagedListInfo.setDefaultSort("c.subject", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY c.subject ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "* " +
        "FROM project_issues_categories c " +
        "WHERE c.category_id > 0 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      IssueCategory thisCategory = new IssueCategory(rs);
      thisCategory.setProject(project);
      thisCategory.setProjectId(projectId);
      this.add(thisCategory);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      IssueCategory thisIssueCategory = (IssueCategory) i.next();
      thisIssueCategory.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   *@param  db         Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    if (projectId > -1) {
      sqlFilter.append("AND project_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND c.enabled = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (projectId > -1) {
      pst.setInt(++i, projectId);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, enabled == Constants.TRUE);
    }
    return i;
  }
}

