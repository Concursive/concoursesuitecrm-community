/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  Contains a collection of news articles
 *
 *@author     matt rajkowski
 *@created    June 23, 2003
 *@version    $Id: NewsArticleList.java,v 1.1.2.2 2004/04/08 14:55:53 rvasista
 *      Exp $
 */
public class NewsArticleList extends ArrayList {
  //filters
  private int projectId = -1;
  private boolean overviewAll = false;
  private int currentNews = Constants.UNDEFINED;
  private int archivedNews = Constants.UNDEFINED;
  private int unreleasedNews = Constants.UNDEFINED;
  private int incompleteNews = Constants.UNDEFINED;
  private PagedListInfo pagedListInfo = null;
  private int lastNews = -1;
  private int forUser = -1;
  //calendar
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;


  /**
   *  Constructor for the NewsArticleList object
   */
  public NewsArticleList() { }


  /**
   *  Sets the projectId attribute of the NewsArticleList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the projectId attribute of the NewsArticleList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the overview attribute of the NewsArticleList object
   *
   *@param  tmp  The new overview value
   */
  public void setOverviewAll(boolean tmp) {
    this.overviewAll = tmp;
  }


  /**
   *  Sets the overview attribute of the NewsArticleList object
   *
   *@param  tmp  The new overview value
   */
  public void setOverviewAll(String tmp) {
    this.overviewAll = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the overview attribute of the NewsArticleList object
   *
   *@return    The overview value
   */
  public boolean getOverviewAll() {
    return overviewAll;
  }


  /**
   *  Sets the currentNews attribute of the NewsArticleList object
   *
   *@param  tmp  The new currentNews value
   */
  public void setCurrentNews(int tmp) {
    this.currentNews = tmp;
  }


  /**
   *  Sets the currentNews attribute of the NewsArticleList object
   *
   *@param  tmp  The new currentNews value
   */
  public void setCurrentNews(String tmp) {
    this.currentNews = Integer.parseInt(tmp);
  }


  /**
   *  Sets the archivedNews attribute of the NewsArticleList object
   *
   *@param  tmp  The new archivedNews value
   */
  public void setArchivedNews(int tmp) {
    this.archivedNews = tmp;
  }


  /**
   *  Sets the archivedNews attribute of the NewsArticleList object
   *
   *@param  tmp  The new archivedNews value
   */
  public void setArchivedNews(String tmp) {
    this.archivedNews = Integer.parseInt(tmp);
  }


  /**
   *  Sets the unreleasedNews attribute of the NewsArticleList object
   *
   *@param  tmp  The new unreleasedNews value
   */
  public void setUnreleasedNews(int tmp) {
    this.unreleasedNews = tmp;
  }


  /**
   *  Sets the unreleasedNews attribute of the NewsArticleList object
   *
   *@param  tmp  The new unreleasedNews value
   */
  public void setUnreleasedNews(String tmp) {
    this.unreleasedNews = Integer.parseInt(tmp);
  }


  /**
   *  Sets the incompleteNews attribute of the NewsArticleList object
   *
   *@param  tmp  The new incompleteNews value
   */
  public void setIncompleteNews(int tmp) {
    this.incompleteNews = tmp;
  }


  /**
   *  Sets the incompleteNews attribute of the NewsArticleList object
   *
   *@param  tmp  The new incompleteNews value
   */
  public void setIncompleteNews(String tmp) {
    this.incompleteNews = Integer.parseInt(tmp);
  }


  /**
   *  Sets the lastNews attribute of the NewsArticleList object
   *
   *@param  tmp  The new lastNews value
   */
  public void setLastNews(int tmp) {
    this.lastNews = tmp;
  }


  /**
   *  Sets the forUser attribute of the NewsArticleList object
   *
   *@param  tmp  The new forUser value
   */
  public void setForUser(int tmp) {
    this.forUser = tmp;
  }


  /**
   *  Sets the forUser attribute of the NewsArticleList object
   *
   *@param  tmp  The new forUser value
   */
  public void setForUser(String tmp) {
    this.forUser = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pagedListInfo attribute of the NewsArticleList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the NewsArticleList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the NewsArticleList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the alertRangeEnd attribute of the NewsArticleList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   *  Sets the alertRangeEnd attribute of the NewsArticleList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the projectId attribute of the NewsArticleList object
   *
   *@return    The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   *  Gets the currentNews attribute of the NewsArticleList object
   *
   *@return    The currentNews value
   */
  public int getCurrentNews() {
    return currentNews;
  }


  /**
   *  Gets the archivedNews attribute of the NewsArticleList object
   *
   *@return    The archivedNews value
   */
  public int getArchivedNews() {
    return archivedNews;
  }


  /**
   *  Gets the unreleasedNews attribute of the NewsArticleList object
   *
   *@return    The unreleasedNews value
   */
  public int getUnreleasedNews() {
    return unreleasedNews;
  }


  /**
   *  Gets the incompleteNews attribute of the NewsArticleList object
   *
   *@return    The incompleteNews value
   */
  public int getIncompleteNews() {
    return incompleteNews;
  }


  /**
   *  Gets the pagedListInfo attribute of the NewsArticleList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the alertRangeStart attribute of the NewsArticleList object
   *
   *@return    The alertRangeStart value
   */
  public java.sql.Timestamp getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   *  Gets the alertRangeEnd attribute of the NewsArticleList object
   *
   *@return    The alertRangeEnd value
   */
  public java.sql.Timestamp getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   *  Gets the forUser attribute of the NewsArticleList object
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
        "FROM project_news n " +
        "WHERE n.news_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo == null) {
      pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(lastNews);
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
          "AND lower(n.subject) < ? ");
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
    pagedListInfo.setDefaultSort("n.priority_id asc, n.start_date desc", null);
    pagedListInfo.appendSqlTail(db, sqlOrder);
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "n.* " +
        "FROM project_news n " +
        "WHERE n.news_id > -1 ");
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
      NewsArticle thisArticle = new NewsArticle(rs);
      this.add(thisArticle);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (projectId > 0) {
      sqlFilter.append("AND n.project_id = ? ");
    }
    if (currentNews == Constants.TRUE) {
      sqlFilter.append(
          "AND n.start_date <= CURRENT_TIMESTAMP " +
          "AND (CURRENT_TIMESTAMP <= n.end_date OR n.end_date IS NULL) " +
          "AND status = ? ");
    }
    if (archivedNews == Constants.TRUE) {
      sqlFilter.append(
          "AND CURRENT_TIMESTAMP > n.end_date " +
          "AND n.end_date IS NOT NULL " +
          "AND n.start_date IS NOT NULL " +
          "AND status = ? ");
    }
    if (unreleasedNews == Constants.TRUE) {
      sqlFilter.append(
          "AND (CURRENT_TIMESTAMP < n.start_date OR n.start_date IS NULL) " +
          "AND status = ? ");
    }
    if (incompleteNews == Constants.TRUE) {
      sqlFilter.append("AND (status = ? OR status IS NULL) ");
    }
    if (alertRangeStart != null) {
      sqlFilter.append("AND n.start_date >= ? ");
    }
    if (alertRangeEnd != null) {
      sqlFilter.append("AND n.start_date < ? ");
    }
    if (forUser > -1) {
      sqlFilter.append("AND (n.project_id in (SELECT DISTINCT project_id FROM project_team WHERE user_id = ? " +
          "AND status IS NULL )) ");
    }
    if (overviewAll) {
      sqlFilter.append(
          "AND (( " +
          "    n.start_date <= CURRENT_TIMESTAMP " +
          "    AND (CURRENT_TIMESTAMP <= n.end_date OR n.end_date IS NULL) " +
          "    AND status = ?) " +
          "  OR ( " +
          "          (CURRENT_TIMESTAMP < n.start_date OR n.start_date IS NULL)  " +
          "          AND status = ?) " +
          "  OR ( " +
          "          (status = ? OR status IS NULL) " +
          ")) ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (projectId > 0) {
      pst.setInt(++i, projectId);
    }
    if (currentNews == Constants.TRUE) {
      pst.setInt(++i, NewsArticle.PUBLISHED);
    }
    if (archivedNews == Constants.TRUE) {
      pst.setInt(++i, NewsArticle.PUBLISHED);
    }
    if (unreleasedNews == Constants.TRUE) {
      pst.setInt(++i, NewsArticle.PUBLISHED);
    }
    if (incompleteNews == Constants.TRUE) {
      pst.setInt(++i, NewsArticle.UNAPPROVED);
    }
    if (alertRangeStart != null) {
      pst.setTimestamp(++i, alertRangeStart);
    }
    if (alertRangeEnd != null) {
      pst.setTimestamp(++i, alertRangeEnd);
    }
    if (forUser > -1) {
      pst.setInt(++i, forUser);
    }
    if (overviewAll) {
      pst.setInt(++i, NewsArticle.PUBLISHED);
      pst.setInt(++i, NewsArticle.PUBLISHED);
      pst.setInt(++i, NewsArticle.UNAPPROVED);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void delete(Connection db, int projectId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM project_news " +
        "WHERE project_id = ? ");
    pst.setInt(1, projectId);
    pst.execute();
    pst.close();
  }
}

