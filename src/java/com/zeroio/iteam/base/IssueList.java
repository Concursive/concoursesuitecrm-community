/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    July 23, 2001
 *@version    $Id$
 */
public class IssueList extends ArrayList {
  //filters
  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private int lastIssues = -1;
  private Project project = null;
  private int projectId = -1;
  private int categoryId = -1;
  private int forUser = -1;
  //calendar
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;


  /**
   *  Constructor for the IssueList object
   */
  public IssueList() { }


  /**
   *  Sets the pagedListInfo attribute of the IssueList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the emptyHtmlSelectRecord attribute of the IssueList object
   *
   *@param  tmp  The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   *  Sets the lastIssues attribute of the IssueList object
   *
   *@param  tmp  The new lastIssues value
   */
  public void setLastIssues(int tmp) {
    this.lastIssues = tmp;
  }


  /**
   *  Sets the projectId attribute of the IssueList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the project attribute of the IssueList object
   *
   *@param  tmp  The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   *  Sets the categoryId attribute of the IssueList object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the forUser attribute of the IssueList object
   *
   *@param  tmp  The new forUser value
   */
  public void setForUser(int tmp) {
    this.forUser = tmp;
  }


  /**
   *  Sets the forUser attribute of the IssueList object
   *
   *@param  tmp  The new forUser value
   */
  public void setForUser(String tmp) {
    this.forUser = Integer.parseInt(tmp);
  }


  /**
   *  Sets the alertRangeStart attribute of the IssueList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the IssueList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the alertRangeEnd attribute of the IssueList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   *  Sets the alertRangeEnd attribute of the IssueList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the htmlSelect attribute of the IssueList object
   *
   *@param  selectName  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the htmlSelect attribute of the IssueList object
   *
   *@param  selectName  Description of the Parameter
   *@param  defaultKey  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect listSelect = new HtmlSelect();
    if (emptyHtmlSelectRecord != null) {
      listSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Issue thisIssue = (Issue) i.next();
      listSelect.addItem(
          thisIssue.getId(),
          thisIssue.getSubject());
    }
    return listSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Gets the project attribute of the IssueList object
   *
   *@return    The project value
   */
  public Project getProject() {
    return project;
  }


  /**
   *  Gets the pagedListInfo attribute of the IssueList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the alertRangeStart attribute of the IssueList object
   *
   *@return    The alertRangeStart value
   */
  public java.sql.Timestamp getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   *  Gets the alertRangeEnd attribute of the IssueList object
   *
   *@return    The alertRangeEnd value
   */
  public java.sql.Timestamp getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   *  Gets the forUser attribute of the IssueList object
   *
   *@return    The forUser value
   */
  public int getForUser() {
    return forUser;
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
        "SELECT COUNT(*) AS recordcount " +
        "FROM project_issues i " +
        "WHERE i.issue_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo == null) {
      pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(lastIssues);
    }
    //Get the total number of records matching filter
    pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (rs.next()) {
      int maxRecords = rs.getInt("recordcount");
      pagedListInfo.setMaxRecords(maxRecords);
    }
    rs.close();
    pst.close();
    //Determine the offset, based on the filter, for the first record to show
    if (!pagedListInfo.getCurrentLetter().equals("")) {
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString() +
          "AND lower(subject) < ? ");
      items = prepareFilter(pst);
      pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
      rs = pst.executeQuery();
      if (rs.next()) {
        int offsetCount = rs.getInt("recordcount");
        pagedListInfo.setCurrentOffset(offsetCount);
      }
      rs.close();
      pst.close();
    }
    //Determine column to sort by
    pagedListInfo.setDefaultSort("entered", "desc");
    pagedListInfo.appendSqlTail(db, sqlOrder);
    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "i.* " +
        "FROM project_issues i " +
        "WHERE i.issue_id > -1 ");
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
      Issue thisIssue = new Issue(rs);
      thisIssue.setProject(project);
      this.add(thisIssue);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (projectId > -1) {
      sqlFilter.append("AND project_id = ? ");
    }
    if (categoryId > -1) {
      sqlFilter.append("AND category_id = ? ");
    }
    if (alertRangeStart != null) {
      sqlFilter.append("AND i.last_reply_date >= ? ");
    }
    if (alertRangeEnd != null) {
      sqlFilter.append("AND i.last_reply_date < ? ");
    }
    if (forUser > -1) {
      sqlFilter.append("AND (i.project_id IN (SELECT DISTINCT project_id FROM project_team WHERE user_id = ? " +
          "AND status IS NULL) OR i.project_id IN (SELECT project_id FROM projects WHERE allow_guests = ? AND approvaldate IS NOT NULL)) ");
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
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    if (alertRangeStart != null) {
      pst.setTimestamp(++i, alertRangeStart);
    }
    if (alertRangeEnd != null) {
      pst.setTimestamp(++i, alertRangeEnd);
    }
    if (forUser > -1) {
      pst.setInt(++i, forUser);
      pst.setBoolean(++i, true);
    }
    return i;
  }

  public void delete(Connection db, String basePath) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Issue thisIssue = (Issue) i.next();
      thisIssue.delete(db, basePath);
    }
  }
}

