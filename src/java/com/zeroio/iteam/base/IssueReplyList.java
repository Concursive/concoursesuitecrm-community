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
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: IssueReplyList.java,v 1.1.136.1 2004/03/19 21:00:50 rvasista
 *          Exp $
 * @created January 15, 2003
 */
public class IssueReplyList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int lastReplies = -1;
  private Issue issue = null;
  private int issueId = -1;
  // resources
  private boolean buildFiles = false;
  private int categoryId = -1;
  private int projectId = -1;

  /**
   * Constructor for the IssueReplyList object
   */
  public IssueReplyList() {
  }


  /**
   * Sets the pagedListInfo attribute of the IssueReplyList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the lastReplies attribute of the IssueReplyList object
   *
   * @param tmp The new lastReplies value
   */
  public void setLastReplies(int tmp) {
    this.lastReplies = tmp;
  }


  /**
   * Sets the issue attribute of the IssueReplyList object
   *
   * @param tmp The new issue value
   */
  public void setIssue(Issue tmp) {
    this.issue = tmp;
  }


  /**
   * Sets the issueId attribute of the IssueReplyList object
   *
   * @param tmp The new issueId value
   */
  public void setIssueId(int tmp) {
    this.issueId = tmp;
  }


  /**
   * Gets the issue attribute of the IssueReplyList object
   *
   * @return The issue value
   */
  public Issue getIssue() {
    return issue;
  }


  public void setBuildFiles(boolean buildFiles) {
    this.buildFiles = buildFiles;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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
        "FROM project_issue_replies r " +
        "WHERE r.reply_id > -1 ");

    createFilter(sqlFilter);

    if (pagedListInfo == null) {
      pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(lastReplies);
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
      pst = db.prepareStatement(
          sqlCount.toString() +
          sqlFilter.toString() +
          "AND " + DatabaseUtils.toLowerCase(db) + "(subject) < ? ");
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
    pagedListInfo.setDefaultSort("entered", null);
    pagedListInfo.appendSqlTail(db, sqlOrder);

    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "r.* " +
        "FROM project_issue_replies r " +
        "WHERE r.reply_id > -1 ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();

    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      IssueReply thisReply = new IssueReply(rs);
      thisReply.setIssue(issue);
      this.add(thisReply);
    }
    rs.close();
    pst.close();
    // build resources
    if (buildFiles) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        IssueReply thisReply = (IssueReply) i.next();
        thisReply.buildFiles(db);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (issueId > -1) {
      sqlFilter.append("AND issue_id = ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (issueId > -1) {
      pst.setInt(++i, issueId);
    }

    return i;
  }

  public void delete(Connection db, String basePath) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      IssueReply thisIssueReply = (IssueReply) i.next();
      thisIssueReply.setProjectId(projectId);
      thisIssueReply.setCategoryId(categoryId);
      thisIssueReply.delete(db, basePath);
    }
  }
}

