/*
 *  Copyright 2000-2003 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    August 9, 2002
 *@version    $Id$
 */
public class ProjectList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private int groupId = -1;
  private int projectsForUser = -1;
  private String enteredByUserRange = null;
  private String userRange = null;
  private boolean openProjectsOnly = false;
  private int withProjectDaysComplete = -1;
  private boolean projectsWithAssignmentsOnly = false;

  private boolean openAssignmentsOnly = false;
  private int withAssignmentDaysComplete = -1;
  private boolean buildAssignments = false;
  private int assignmentsForUser = -1;
  private boolean buildIssues = false;
  private int lastIssues = -1;

  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;


  /**
   *  Constructor for the ProjectList object
   */
  public ProjectList() { }


  /**
   *  Sets the pagedListInfo attribute of the ProjectList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the emptyHtmlSelectRecord attribute of the ProjectList object
   *
   *@param  tmp  The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   *  Sets the groupId attribute of the ProjectList object
   *
   *@param  tmp  The new groupId value
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   *  Sets the openProjectsOnly attribute of the ProjectList object
   *
   *@param  tmp  The new openProjectsOnly value
   */
  public void setOpenProjectsOnly(boolean tmp) {
    this.openProjectsOnly = tmp;
  }


  /**
   *  Sets the withProjectDaysComplete attribute of the ProjectList object
   *
   *@param  tmp  The new withProjectDaysComplete value
   */
  public void setWithProjectDaysComplete(int tmp) {
    this.withProjectDaysComplete = tmp;
  }


  /**
   *  Sets the projectsWithAssignmentsOnly attribute of the ProjectList object
   *
   *@param  tmp  The new projectsWithAssignmentsOnly value
   */
  public void setProjectsWithAssignmentsOnly(boolean tmp) {
    this.projectsWithAssignmentsOnly = tmp;
  }


  /**
   *  Sets the openAssignmentsOnly attribute of the ProjectList object
   *
   *@param  tmp  The new openAssignmentsOnly value
   */
  public void setOpenAssignmentsOnly(boolean tmp) {
    this.openAssignmentsOnly = tmp;
  }


  /**
   *  Sets the withAssignmentDaysComplete attribute of the ProjectList object
   *
   *@param  tmp  The new withAssignmentDaysComplete value
   */
  public void setWithAssignmentDaysComplete(int tmp) {
    this.withAssignmentDaysComplete = tmp;
  }


  /**
   *  Sets the projectsForUser attribute of the ProjectList object
   *
   *@param  tmp  The new projectsForUser value
   */
  public void setProjectsForUser(int tmp) {
    this.projectsForUser = tmp;
  }


  /**
   *  Sets the userRange attribute of the ProjectList object
   *
   *@param  tmp  The new userRange value
   */
  public void setUserRange(String tmp) {
    this.userRange = tmp;
  }


  /**
   *  Sets the enteredByUserRange attribute of the ProjectList object
   *
   *@param  tmp  The new enteredByUserRange value
   */
  public void setEnteredByUserRange(String tmp) {
    this.enteredByUserRange = tmp;
  }


  /**
   *  Sets the buildAssignments attribute of the ProjectList object
   *
   *@param  tmp  The new buildAssignments value
   */
  public void setBuildAssignments(boolean tmp) {
    this.buildAssignments = tmp;
  }


  /**
   *  Sets the buildIssues attribute of the ProjectList object
   *
   *@param  tmp  The new buildIssues value
   */
  public void setBuildIssues(boolean tmp) {
    this.buildIssues = tmp;
  }


  /**
   *  Sets the assignmentsForUser attribute of the ProjectList object
   *
   *@param  tmp  The new assignmentsForUser value
   */
  public void setAssignmentsForUser(int tmp) {
    this.assignmentsForUser = tmp;
  }


  /**
   *  Sets the lastIssues attribute of the ProjectList object
   *
   *@param  tmp  The new lastIssues value
   */
  public void setLastIssues(int tmp) {
    this.lastIssues = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the ProjectList object
   *
   *@param  alertRangeStart  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp alertRangeStart) {
    this.alertRangeStart = alertRangeStart;
  }


  /**
   *  Sets the alertRangeEnd attribute of the ProjectList object
   *
   *@param  alertRangeEnd  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp alertRangeEnd) {
    this.alertRangeEnd = alertRangeEnd;
  }


  /**
   *  Gets the htmlSelect attribute of the ProjectList object
   *
   *@param  selectName  Description of Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the htmlSelect attribute of the ProjectList object
   *
   *@param  selectName  Description of Parameter
   *@param  defaultKey  Description of Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect listSelect = this.getHtmlSelect();
    return listSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Gets the htmlSelect attribute of the ProjectList object
   *
   *@return    The htmlSelect value
   */
  public HtmlSelect getHtmlSelect() {
    HtmlSelect listSelect = new HtmlSelect();
    if (emptyHtmlSelectRecord != null) {
      listSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Project thisProject = (Project) i.next();
      listSelect.addItem(
          thisProject.getId(),
          thisProject.getTitle());
    }
    return listSelect;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
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
        "FROM projects p " +
        "WHERE project_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo == null) {
      pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(0);
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
          "AND title < ? ");
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
    pagedListInfo.setDefaultSort("title", null);
    pagedListInfo.appendSqlTail(db, sqlOrder);

    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "* " +
        "FROM projects p " +
        "WHERE project_id > -1 ");

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
      Project thisProject = new Project(rs);
      this.add(thisProject);
    }
    rs.close();
    pst.close();

    Iterator i = this.iterator();
    while (i.hasNext()) {
      Project thisProject = (Project) i.next();
      if (buildAssignments) {
        thisProject.setIncompleteAssignmentsOnly(openAssignmentsOnly);
        thisProject.setWithAssignmentDaysComplete(withAssignmentDaysComplete);
        thisProject.setAssignmentsForUser(assignmentsForUser);
        thisProject.setAssignmentAlertRangeStart(alertRangeStart);
        thisProject.setAssignmentAlertRangeEnd(alertRangeEnd);
        thisProject.buildAssignmentList(db);
      }
      if (buildIssues) {
        thisProject.setLastIssues(lastIssues);
        thisProject.buildIssueList(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (groupId > -1) {
      sqlFilter.append("AND (group_id = ?) ");
    }
    if (projectsWithAssignmentsOnly) {
      sqlFilter.append("AND (p.project_id IN (SELECT DISTINCT project_id FROM project_assignments)) ");
    }
    if (openProjectsOnly && withProjectDaysComplete > -1) {
      sqlFilter.append("AND (closeDate IS NULL OR closeDate LIKE '' OR closeDate > ?) ");
    } else {
      if (openProjectsOnly) {
        sqlFilter.append("AND (closeDate IS NULL OR closeDate LIKE '') ");
      }
      if (withProjectDaysComplete > -1) {
        sqlFilter.append("AND (closeDate > ?) ");
      }
    }
    if (projectsForUser > -1) {
      sqlFilter.append("AND (p.project_id in (SELECT DISTINCT project_id FROM project_team WHERE user_id = ?)) ");
    }
    if (userRange != null) {
      sqlFilter.append("AND (p.project_id in (SELECT DISTINCT project_id FROM project_team WHERE user_id IN (" + userRange + ")) " +
          "OR p.enteredBy IN (" + userRange + ")) ");
    }
    if (enteredByUserRange != null) {
      sqlFilter.append("AND (p.enteredby IN (" + enteredByUserRange + ")) ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (groupId > -1) {
      pst.setInt(++i, groupId);
    }
    if (openProjectsOnly && withProjectDaysComplete > -1) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DATE, -withProjectDaysComplete);
      pst.setTimestamp(++i, new java.sql.Timestamp(cal.getTimeInMillis()));
    } else {
      if (withProjectDaysComplete > -1) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -withProjectDaysComplete);
        pst.setTimestamp(++i, new java.sql.Timestamp(cal.getTimeInMillis()));
      }
    }
    if (projectsForUser > -1) {
      pst.setInt(++i, projectsForUser);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public HashMap queryAssignmentRecordCount(Connection db, TimeZone timeZone) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    HashMap events = new HashMap();
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlTail = new StringBuffer();
    sqlSelect.append(
        "SELECT a.due_date, count(*) " +
        "FROM projects p, project_assignments a " +
        "WHERE p.project_id > -1 " +
        "AND p.project_id = a.project_id ");
    sqlTail.append("GROUP BY a.due_date ");
    createFilter(sqlFilter);
    if (assignmentsForUser > -1) {
      sqlFilter.append("AND a.user_assign_id = ? ");
    }
    if (openAssignmentsOnly) {
      sqlFilter.append("AND a.complete_date IS NULL ");
    }
    if (alertRangeStart != null) {
      sqlFilter.append("AND a.due_date >= ? ");
    }
    if (alertRangeEnd != null) {
      sqlFilter.append("AND a.due_date <= ? ");
    }
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    int i = prepareFilter(pst);
    if (assignmentsForUser > -1) {
      pst.setInt(++i, assignmentsForUser);
    }
    if (alertRangeStart != null) {
      pst.setTimestamp(++i, alertRangeStart);
    }
    if (alertRangeEnd != null) {
      pst.setTimestamp(++i, alertRangeEnd);
    }
    rs = pst.executeQuery();
    while (rs.next()) {
      String alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, rs.getTimestamp("due_date"));
      int alertCount = rs.getInt("count");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProjectList --> Added Days Assignments " + alertDate + ":" + alertCount);
      }
      events.put(alertDate, new Integer(alertCount));
    }
    rs.close();
    pst.close();
    return events;
  }


  /**
   *  Gets the alertDateStringLongYear attribute of the ProjectList class
   *
   *@param  alertDate  Description of the Parameter
   *@return            The alertDateStringLongYear value
   */
  public static String getAlertDateStringLongYear(java.sql.Date alertDate) {
    String tmp = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG);
      formatter.applyPattern("M/d/yyyy");
      return formatter.format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }

}

