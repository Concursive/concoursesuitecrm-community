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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: ProjectList.java,v 1.3.50.2 2004/04/08 14:55:53 rvasista Exp
 *          $
 * @created August 9, 2002
 */
public class ProjectList extends ArrayList {
  // main project filters
  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private int groupId = -1;
  private int projectId = -1;
  private int projectsForUser = -1;
  private int enteredByUser = -1;
  private String enteredByUserRange = null;
  private String userRange = null;
  private boolean openProjectsOnly = false;
  private boolean closedProjectsOnly = false;
  private int withProjectDaysComplete = -1;
  private boolean projectsWithAssignmentsOnly = false;
  private boolean invitationPendingOnly = false;
  private boolean invitationAcceptedOnly = false;
  private int daysLastAccessed = -1;
  private boolean includeGuestProjects = false;
  private int categoryId = -1;
  // filters that go into sub-objects
  private boolean openAssignmentsOnly = false;
  private int withAssignmentDaysComplete = -1;
  private boolean buildAssignments = false;
  private int assignmentsForUser = -1;
  private boolean buildIssueCategories = false;
  private boolean buildIssues = false;
  private int lastIssues = -1;
  private boolean buildNews = false;
  private boolean buildPermissions = false;
  private int lastNews = -1;
  private int currentNews = Constants.UNDEFINED;
  private int portalState = Constants.UNDEFINED;
  private boolean portalDefaultOnly = false;
  private String portalKey = null;
  private boolean publicOnly = false;
  private boolean approvedOnly = false;
  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;

  // calendar filters
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;
  private boolean buildOverallProgress = false;
  private int projectsForOrgId = -1;


  /**
   * Constructor for the ProjectList object
   */
  public ProjectList() {
  }


  /**
   * Sets the pagedListInfo attribute of the ProjectList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the emptyHtmlSelectRecord attribute of the ProjectList object
   *
   * @param tmp The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   * Sets the groupId attribute of the ProjectList object
   *
   * @param tmp The new groupId value
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   * Sets the projectId attribute of the ProjectList object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   * Sets the openProjectsOnly attribute of the ProjectList object
   *
   * @param tmp The new openProjectsOnly value
   */
  public void setOpenProjectsOnly(boolean tmp) {
    this.openProjectsOnly = tmp;
  }


  /**
   * Sets the closedProjectsOnly attribute of the ProjectList object
   *
   * @param tmp The new closedProjectsOnly value
   */
  public void setClosedProjectsOnly(boolean tmp) {
    this.closedProjectsOnly = tmp;
  }


  /**
   * Sets the closedProjectsOnly attribute of the ProjectList object
   *
   * @param tmp The new closedProjectsOnly value
   */
  public void setClosedProjectsOnly(String tmp) {
    this.closedProjectsOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the withProjectDaysComplete attribute of the ProjectList object
   *
   * @param tmp The new withProjectDaysComplete value
   */
  public void setWithProjectDaysComplete(int tmp) {
    this.withProjectDaysComplete = tmp;
  }


  /**
   * Sets the projectsWithAssignmentsOnly attribute of the ProjectList object
   *
   * @param tmp The new projectsWithAssignmentsOnly value
   */
  public void setProjectsWithAssignmentsOnly(boolean tmp) {
    this.projectsWithAssignmentsOnly = tmp;
  }


  /**
   * Sets the invitationPendingOnly attribute of the ProjectList object
   *
   * @param tmp The new invitationPendingOnly value
   */
  public void setInvitationPendingOnly(boolean tmp) {
    this.invitationPendingOnly = tmp;
  }


  /**
   * Sets the invitationAcceptedOnly attribute of the ProjectList object
   *
   * @param tmp The new invitationAcceptedOnly value
   */
  public void setInvitationAcceptedOnly(boolean tmp) {
    this.invitationAcceptedOnly = tmp;
  }


  /**
   * Gets the invitationPendingOnly attribute of the ProjectList object
   *
   * @return The invitationPendingOnly value
   */
  public boolean getInvitationPendingOnly() {
    return invitationPendingOnly;
  }


  /**
   * Gets the invitationAcceptedOnly attribute of the ProjectList object
   *
   * @return The invitationAcceptedOnly value
   */
  public boolean getInvitationAcceptedOnly() {
    return invitationAcceptedOnly;
  }


  /**
   * Gets the daysLastAccessed attribute of the ProjectList object
   *
   * @return The daysLastAccessed value
   */
  public int getDaysLastAccessed() {
    return daysLastAccessed;
  }


  /**
   * Sets the daysLastAccessed attribute of the ProjectList object
   *
   * @param tmp The new daysLastAccessed value
   */
  public void setDaysLastAccessed(int tmp) {
    this.daysLastAccessed = tmp;
  }


  /**
   * Sets the daysLastAccessed attribute of the ProjectList object
   *
   * @param tmp The new daysLastAccessed value
   */
  public void setDaysLastAccessed(String tmp) {
    this.daysLastAccessed = Integer.parseInt(tmp);
  }


  /**
   * Gets the includeGuestProjects attribute of the ProjectList object
   *
   * @return The includeGuestProjects value
   */
  public boolean getIncludeGuestProjects() {
    return includeGuestProjects;
  }


  /**
   * Sets the includeGuestProjects attribute of the ProjectList object
   *
   * @param tmp The new includeGuestProjects value
   */
  public void setIncludeGuestProjects(boolean tmp) {
    this.includeGuestProjects = tmp;
  }


  /**
   * Sets the includeGuestProjects attribute of the ProjectList object
   *
   * @param tmp The new includeGuestProjects value
   */
  public void setIncludeGuestProjects(String tmp) {
    this.includeGuestProjects = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the categoryId attribute of the ProjectList object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the categoryId attribute of the ProjectList object
   *
   * @param categoryId The new categoryId value
   */
  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }


  /**
   * Sets the categoryId attribute of the ProjectList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    categoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the openAssignmentsOnly attribute of the ProjectList object
   *
   * @param tmp The new openAssignmentsOnly value
   */
  public void setOpenAssignmentsOnly(boolean tmp) {
    this.openAssignmentsOnly = tmp;
  }


  /**
   * Sets the withAssignmentDaysComplete attribute of the ProjectList object
   *
   * @param tmp The new withAssignmentDaysComplete value
   */
  public void setWithAssignmentDaysComplete(int tmp) {
    this.withAssignmentDaysComplete = tmp;
  }


  /**
   * Sets the projectsForUser attribute of the ProjectList object
   *
   * @param tmp The new projectsForUser value
   */
  public void setProjectsForUser(int tmp) {
    this.projectsForUser = tmp;
  }


  /**
   * Sets the enteredByUser attribute of the ProjectList object
   *
   * @param tmp The new enteredByUser value
   */
  public void setEnteredByUser(int tmp) {
    this.enteredByUser = tmp;
  }


  /**
   * Sets the enteredByUser attribute of the ProjectList object
   *
   * @param tmp The new enteredByUser value
   */
  public void setEnteredByUser(String tmp) {
    this.enteredByUser = Integer.parseInt(tmp);
  }


  /**
   * Sets the userRange attribute of the ProjectList object
   *
   * @param tmp The new userRange value
   */
  public void setUserRange(String tmp) {
    this.userRange = tmp;
  }


  /**
   * Sets the enteredByUserRange attribute of the ProjectList object
   *
   * @param tmp The new enteredByUserRange value
   */
  public void setEnteredByUserRange(String tmp) {
    this.enteredByUserRange = tmp;
  }


  /**
   * Sets the buildAssignments attribute of the ProjectList object
   *
   * @param tmp The new buildAssignments value
   */
  public void setBuildAssignments(boolean tmp) {
    this.buildAssignments = tmp;
  }


  /**
   * Sets the buildIssues attribute of the ProjectList object
   *
   * @param tmp The new buildIssues value
   */
  public void setBuildIssues(boolean tmp) {
    this.buildIssues = tmp;
  }


  /**
   * Sets the buildNews attribute of the ProjectList object
   *
   * @param tmp The new buildNews value
   */
  public void setBuildNews(boolean tmp) {
    this.buildNews = tmp;
  }


  /**
   * Sets the assignmentsForUser attribute of the ProjectList object
   *
   * @param tmp The new assignmentsForUser value
   */
  public void setAssignmentsForUser(int tmp) {
    this.assignmentsForUser = tmp;
  }


  /**
   * Sets the lastIssues attribute of the ProjectList object
   *
   * @param tmp The new lastIssues value
   */
  public void setLastIssues(int tmp) {
    this.lastIssues = tmp;
  }


  /**
   * Sets the lastNews attribute of the ProjectList object
   *
   * @param tmp The new lastNews value
   */
  public void setLastNews(int tmp) {
    this.lastNews = tmp;
  }


  /**
   * Sets the currentNews attribute of the ProjectList object
   *
   * @param tmp The new currentNews value
   */
  public void setCurrentNews(int tmp) {
    this.currentNews = tmp;
  }


  /**
   * Gets the portalState attribute of the ProjectList object
   *
   * @return The portalState value
   */
  public int getPortalState() {
    return portalState;
  }


  /**
   * Sets the portalState attribute of the ProjectList object
   *
   * @param tmp The new portalState value
   */
  public void setPortalState(int tmp) {
    this.portalState = tmp;
  }


  /**
   * Sets the portalState attribute of the ProjectList object
   *
   * @param tmp The new portalState value
   */
  public void setPortalState(String tmp) {
    this.portalState = Integer.parseInt(tmp);
  }


  /**
   * Gets the portalDefaultOnly attribute of the ProjectList object
   *
   * @return The portalDefaultOnly value
   */
  public boolean getPortalDefaultOnly() {
    return portalDefaultOnly;
  }


  /**
   * Sets the portalDefaultOnly attribute of the ProjectList object
   *
   * @param tmp The new portalDefaultOnly value
   */
  public void setPortalDefaultOnly(boolean tmp) {
    this.portalDefaultOnly = tmp;
  }


  /**
   * Sets the portalDefaultOnly attribute of the ProjectList object
   *
   * @param tmp The new portalDefaultOnly value
   */
  public void setPortalDefaultOnly(String tmp) {
    this.portalDefaultOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the portalKey attribute of the ProjectList object
   *
   * @return The portalKey value
   */
  public String getPortalKey() {
    return portalKey;
  }


  /**
   * Sets the portalKey attribute of the ProjectList object
   *
   * @param tmp The new portalKey value
   */
  public void setPortalKey(String tmp) {
    this.portalKey = tmp;
  }


  /**
   * Sets the publicOnly attribute of the ProjectList object
   *
   * @param tmp The new publicOnly value
   */
  public void setPublicOnly(boolean tmp) {
    this.publicOnly = tmp;
  }


  /**
   * Sets the publicOnly attribute of the ProjectList object
   *
   * @param tmp The new publicOnly value
   */
  public void setPublicOnly(String tmp) {
    this.publicOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the approvedOnly attribute of the ProjectList object
   *
   * @return The approvedOnly value
   */
  public boolean getApprovedOnly() {
    return approvedOnly;
  }


  /**
   * Sets the approvedOnly attribute of the ProjectList object
   *
   * @param tmp The new approvedOnly value
   */
  public void setApprovedOnly(boolean tmp) {
    this.approvedOnly = tmp;
  }


  /**
   * Sets the approvedOnly attribute of the ProjectList object
   *
   * @param tmp The new approvedOnly value
   */
  public void setApprovedOnly(String tmp) {
    this.approvedOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the trashedDate attribute of the ProjectList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the ProjectList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the includeOnlyTrashed attribute of the ProjectList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   * Sets the includeOnlyTrashed attribute of the ProjectList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the trashedDate attribute of the ProjectList object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   * Gets the includeOnlyTrashed attribute of the ProjectList object
   *
   * @return The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   * Sets the buildIssueCategories attribute of the ProjectList object
   *
   * @param buildIssueCategories The new buildIssueCategories value
   */
  public void setBuildIssueCategories(boolean buildIssueCategories) {
    this.buildIssueCategories = buildIssueCategories;
  }


  /**
   * Sets the alertRangeStart attribute of the ProjectList object
   *
   * @param alertRangeStart The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp alertRangeStart) {
    this.alertRangeStart = alertRangeStart;
  }


  /**
   * Sets the alertRangeEnd attribute of the ProjectList object
   *
   * @param alertRangeEnd The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp alertRangeEnd) {
    this.alertRangeEnd = alertRangeEnd;
  }


  /**
   * Sets the buildPermissions attribute of the ProjectList object
   *
   * @param buildPermissions The new buildPermissions value
   */
  public void setBuildPermissions(boolean buildPermissions) {
    this.buildPermissions = buildPermissions;
  }


  /**
   * Gets the htmlSelect attribute of the ProjectList object
   *
   * @param selectName Description of Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   * Gets the htmlSelect attribute of the ProjectList object
   *
   * @param selectName Description of Parameter
   * @param defaultKey Description of Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect listSelect = this.getHtmlSelect();
    return listSelect.getHtml(selectName, defaultKey);
  }


  /**
   * Gets the htmlSelect attribute of the ProjectList object
   *
   * @return The htmlSelect value
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
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
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
    rs.close();
    pst.close();

    //Determine the offset, based on the filter, for the first record to show
    if (!pagedListInfo.getCurrentLetter().equals("")) {
      pst = db.prepareStatement(
          sqlCount.toString() +
          sqlFilter.toString() +
          "AND " + DatabaseUtils.toLowerCase(db) + "(title) < ? ");
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
        "p.* " +
        "FROM projects p " +
        "WHERE project_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
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
      if (buildIssueCategories) {
        thisProject.buildIssueCategoryList(db);
      }
      if (buildNews) {
        thisProject.setLastNews(lastNews);
        thisProject.setCurrentNews(currentNews);
        thisProject.buildNewsList(db);
      }
      if (buildOverallProgress) {
        //thisProject.getRequirements().setOpenOnly(true);
        thisProject.buildRequirementList(db);
        thisProject.getRequirements().buildPlanActivityCounts(db);
      }
      if (buildPermissions) {
        thisProject.buildPermissionList(db);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (groupId > -1) {
      sqlFilter.append("AND (group_id = ?) ");
    }
    if (projectId > -1) {
      sqlFilter.append("AND (project_id = ?) ");
    }
    if (projectsWithAssignmentsOnly) {
      sqlFilter.append(
          "AND (p.project_id IN (SELECT DISTINCT project_id FROM project_assignments)) ");
    }
    if (openProjectsOnly && withProjectDaysComplete > -1) {
      sqlFilter.append(
          "AND (closedate IS NULL OR closedate LIKE '' OR closedate > ?) ");
    } else {
      if (openProjectsOnly) {
        sqlFilter.append("AND (closedate IS NULL) ");
      }
      if (withProjectDaysComplete > -1) {
        sqlFilter.append("AND (closeDate > ?) ");
      }
    }
    if (closedProjectsOnly) {
      sqlFilter.append("AND (closedate IS NOT NULL) ");
    }
    if (projectsForUser > -1) {
      sqlFilter.append(
          "AND (p.project_id IN (SELECT DISTINCT project_id FROM project_team WHERE user_id = ? " +
          (invitationAcceptedOnly ? "AND status IS NULL " : "") +
          (invitationPendingOnly ? "AND status = ? " : "") +
          (daysLastAccessed > -1 ? "AND last_accessed > ? " : "") + ") " +
          (includeGuestProjects ? "OR (allow_guests = ? AND approvaldate IS NOT NULL) " : "") +
          ") ");
    }
    if (userRange != null) {
      sqlFilter.append(
          "AND (p.project_id in (SELECT DISTINCT project_id FROM project_team WHERE user_id IN (" + userRange + ")) " +
          "OR p.enteredBy IN (" + userRange + ")) ");
    }
    if (enteredByUser > -1) {
      sqlFilter.append("AND (p.enteredby = ?) ");
    }
    if (enteredByUserRange != null) {
      sqlFilter.append("AND (p.enteredby IN (" + enteredByUserRange + ")) ");
    }
    if (portalState != Constants.UNDEFINED) {
      sqlFilter.append("AND portal = ? ");
    }
    if (portalDefaultOnly) {
      sqlFilter.append("AND portal_default = ? ");
    }
    if (portalKey != null) {
      sqlFilter.append("AND portal_key = ? ");
    }
    if (publicOnly) {
      sqlFilter.append("AND allow_guests = ? ");
    }
    if (approvedOnly) {
      sqlFilter.append("AND approvaldate IS NOT NULL ");
    }
    if (categoryId > -1) {
      sqlFilter.append("AND p.category_id = ? ");
    }
    if (projectsForOrgId > -1) {
      sqlFilter.append(
          "AND p.project_id IN (SELECT project_id FROM project_accounts WHERE org_id = ?) ");
    }
    if (includeOnlyTrashed) {
      sqlFilter.append("AND p.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND p.trashed_date = ? ");
    } else {
      sqlFilter.append("AND p.trashed_date IS NULL ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (groupId > -1) {
      pst.setInt(++i, groupId);
    }
    if (projectId > -1) {
      pst.setInt(++i, projectId);
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
      if (invitationPendingOnly) {
        pst.setInt(++i, TeamMember.STATUS_PENDING);
      }
      if (daysLastAccessed > -1) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -daysLastAccessed);
        pst.setTimestamp(++i, new java.sql.Timestamp(cal.getTimeInMillis()));
      }
      if (includeGuestProjects) {
        pst.setBoolean(++i, true);
      }
    }
    if (enteredByUser > -1) {
      pst.setInt(++i, enteredByUser);
    }
    if (portalState != Constants.UNDEFINED) {
      pst.setBoolean(++i, (portalState == Constants.TRUE));
    }
    if (portalDefaultOnly) {
      pst.setBoolean(++i, true);
    }
    if (portalKey != null) {
      pst.setString(++i, portalKey);
    }
    if (publicOnly) {
      pst.setBoolean(++i, true);
    }
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    if (projectsForOrgId > -1) {
      pst.setInt(++i, projectsForOrgId);
    }
    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param timeZone Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public HashMap queryAssignmentRecordCount(Connection db, TimeZone timeZone) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    HashMap events = new HashMap();
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlTail = new StringBuffer();
    sqlSelect.append(
        "SELECT a.due_date, COUNT(*) AS ascount " +
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
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
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
      String alertDate = DateUtils.getServerToUserDateString(
          timeZone, DateFormat.SHORT, rs.getTimestamp("due_date"));
      int alertCount = rs.getInt("ascount");
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ProjectList-> Added Days Assignments " + alertDate + ":" + alertCount);
      }
      events.put(alertDate, new Integer(alertCount));
    }
    rs.close();
    pst.close();
    return events;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static HashMap buildNameList(Connection db) throws SQLException {
    HashMap nameList = new HashMap();
    PreparedStatement pst = db.prepareStatement(
        "SELECT project_id, title " +
        "FROM projects");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      nameList.put(
          new Integer(rs.getInt("project_id")), rs.getString("title"));
    }
    rs.close();
    pst.close();
    return nameList;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int buildProjectCount(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(*) AS recordcount " +
        "FROM projects p " +
        "WHERE project_id > -1 ");
    ResultSet rs = pst.executeQuery();
    rs.next();
    int count = rs.getInt("recordcount");
    rs.close();
    pst.close();
    return count;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param userId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int buildProjectCount(Connection db, int userId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(*) AS recordcount " +
        "FROM projects p " +
        "WHERE project_id > -1 " +
        "AND enteredby = ? ");
    pst.setInt(1, userId);
    ResultSet rs = pst.executeQuery();
    rs.next();
    int count = rs.getInt("recordcount");
    rs.close();
    pst.close();
    return count;
  }


  /**
   * Checks to see if any of the projects in the list are user only projects
   *
   * @return Description of the Return Value
   */
  public boolean hasUserProjects() {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Project thisProject = (Project) i.next();
      if (!thisProject.getAllowGuests()) {
        return true;
      }
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildTeam(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Project thisProject = (Project) i.next();
      thisProject.buildTeamMemberList(db);
    }
  }


  /**
   * Sets the buildOverallProgress attribute of the ProjectList object
   *
   * @param buildOverallProgress The new buildOverallProgress value
   */
  public void setBuildOverallProgress(boolean buildOverallProgress) {
    this.buildOverallProgress = buildOverallProgress;
  }


  /**
   * Sets the projectsForOrgId attribute of the ProjectList object
   *
   * @param projectsForOrgId The new projectsForOrgId value
   */
  public void setProjectsForOrgId(int projectsForOrgId) {
    this.projectsForOrgId = projectsForOrgId;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param filePath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db, String filePath) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      Project thisProject = (Project) itr.next();
      thisProject.delete(db, filePath);
    }
  }

}

