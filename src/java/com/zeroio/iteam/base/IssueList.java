/*
 *  Copyright 2000-2003 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
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
import org.aspcfs.utils.web.HtmlSelect;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    July 23, 2001
 *@version    $Id$
 */
public class IssueList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private int lastIssues = -1;
  private Project project = null;
  private int projectId = -1;
  private int categoryId = -1;


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
    pst.close();
    rs.close();

    //Determine the offset, based on the filter, for the first record to show
    if (!pagedListInfo.getCurrentLetter().equals("")) {
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString() +
          "AND subject < ? ");
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
        "i.*, li.description " +
        "FROM project_issues i " +
        " LEFT JOIN lookup_project_issues li ON (i.type_id = li.code) " +
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

    Iterator i = this.iterator();
    while (i.hasNext()) {
      Issue thisIssue = (Issue) i.next();
      thisIssue.buildReplyCount(db);
    }
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
      sqlFilter.append("AND type_id = ? ");
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
    return i;
  }

}

