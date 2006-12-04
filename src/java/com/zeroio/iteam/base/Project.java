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

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.utils.ProjectUtils;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.tasks.base.TaskCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.ObjectUtils;

import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Represents a Project in iTeam
 *
 * @author mrajkowski
 * @version $Id$
 * @created July 23, 2001
 */
public class Project extends GenericBean {

  public final static int PORTAL_TYPE_ARTICLE = -1;
  public final static int PORTAL_TYPE_HOMEPAGE = 1;
  public final static int PORTAL_TYPE_COMMUNITY = 2;
  public final static int PORTAL_TYPE_ORDER = 3;
  public final static int PORTAL_TYPE_CONTACT_US = 4;
  public final static int PORTAL_TYPE_NEWS = 5;

  private int id = -1;
  private int groupId = -1;
  private int departmentId = -1;
  private int categoryId = -1;
  private int templateId = -1;
  private String title = "";
  private String shortDescription = "";
  private String requestedBy = "";
  private String requestedByDept = "";
  private Timestamp requestDate = null;
  private boolean approved = false;
  private Timestamp approvalDate = null;
  private boolean closed = false;
  private Timestamp closeDate = null;
  private Timestamp estimatedCloseDate = null;
  private double budget = -1;
  private String budgetCurrency = null;

  private int owner = -1;
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  private String requestDateTimeZone = null;
  private String estimatedCloseDateTimeZone = null;

  private int assignmentsForUser = -1;
  private int lastIssues = -1;
  private boolean incompleteAssignmentsOnly = false;
  private int withAssignmentDaysComplete = -1;

  private boolean buildRequirementAssignments = false;
  private String userRange = null;

  private Timestamp assignmentAlertRangeStart = null;
  private Timestamp assignmentAlertRangeEnd = null;

  private RequirementList requirements = new RequirementList();
  private TeamMemberList team = new TeamMemberList();
  private TeamMemberList employeeTeam = new TeamMemberList();
  private TeamMemberList accountContactTeam = new TeamMemberList();
  private TeamMemberList portalUserTeam = new TeamMemberList();
  private AssignmentList assignments = new AssignmentList();
  private IssueCategoryList issueCategories = new IssueCategoryList();
  private IssueList issues = new IssueList();
  private FileItemList files = new FileItemList();

  private NewsArticleList news = new NewsArticleList();
  private int lastNews = -1;
  private int currentNews = Constants.UNDEFINED;

  private Timestamp trashedDate = null;

  // Portal capabilities
  private boolean portal = false;
  private String portalHeader = null;
  private String portalFormat = null;
  private String portalKey = null;
  private boolean portalBuildNewsBody = false;
  private boolean portalNewsMenu = false;
  private int portalPageType = PORTAL_TYPE_ARTICLE;

  private boolean allowGuests = false;
  private boolean updateAllowGuests = false;
  private boolean allowsUserObservers = false;

  private boolean showCalendar = false;
  private boolean showNews = false;
  private boolean showDetails = false;
  private boolean showTeam = false;
  private boolean showAccounts = false;
  private boolean showPlan = false;
  private boolean showLists = false;
  private boolean showDiscussion = false;
  private boolean showTickets = false;
  private boolean showDocuments = false;

  private String labelCalendar = null;
  private String labelNews = null;
  private String labelDetails = null;
  private String labelTeam = null;
  private String labelAccounts = null;
  private String labelPlan = null;
  private String labelLists = null;
  private String labelDiscussion = null;
  private String labelTickets = null;
  private String labelDocuments = null;

  private PermissionList permissions = new PermissionList();
  // helper
  private boolean hasAccess = false;


  /**
   * Constructor for the Project object
   */
  public Project() {
  }


  /**
   * Constructor for the Project object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  public Project(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the Project object
   *
   * @param db        Description of Parameter
   * @param thisId    Description of Parameter
   * @param userRange Description of Parameter
   * @throws SQLException Description of Exception
   */
  public Project(Connection db, int thisId, String userRange) throws SQLException {
    this.userRange = userRange;
    queryRecord(db, thisId);
  }


  /**
   * Constructor for the Project object
   *
   * @param db     Description of the Parameter
   * @param thisId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Project(Connection db, int thisId) throws SQLException {
    queryRecord(db, thisId);
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param thisId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void queryRecord(Connection db, int thisId) throws SQLException {
    if (thisId == -1) {
      throw new SQLException("Invalid Project");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM projects p " +
        "WHERE p.project_id = ? ");
    if (userRange != null) {
      sql.append(
          "AND (project_id in (SELECT DISTINCT project_id FROM project_team WHERE user_id IN (" + userRange + ") " +
          "AND project_id = ?) " +
          "OR p.enteredBy IN (" + userRange + ") " +
          "OR p.allow_guests = ?) ");
    }

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, thisId);
    if (userRange != null) {
      pst.setInt(++i, thisId);
      pst.setBoolean(++i, true);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    if (this.id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
  }


  /**
   * Sets the Id attribute of the Project object
   *
   * @param tmp The new Id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Project object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the GroupId attribute of the Project object
   *
   * @param tmp The new GroupId value
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   * Sets the groupId attribute of the Project object
   *
   * @param tmp The new groupId value
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   * Sets the DepartmentId attribute of the Project object
   *
   * @param tmp The new DepartmentId value
   */
  public void setDepartmentId(int tmp) {
    this.departmentId = tmp;
  }


  /**
   * Sets the departmentId attribute of the Project object
   *
   * @param tmp The new departmentId value
   */
  public void setDepartmentId(String tmp) {
    this.departmentId = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryId attribute of the Project object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the Project object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    categoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the templateId attribute of the Project object
   *
   * @param tmp The new templateId value
   */
  public void setTemplateId(int tmp) {
    this.templateId = tmp;
  }


  /**
   * Sets the templateId attribute of the Project object
   *
   * @param tmp The new templateId value
   */
  public void setTemplateId(String tmp) {
    this.templateId = Integer.parseInt(tmp);
  }


  /**
   * Sets the Title attribute of the Project object
   *
   * @param tmp The new Title value
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   * Sets the ShortDescription attribute of the Project object
   *
   * @param tmp The new ShortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   * Sets the RequestedBy attribute of the Project object
   *
   * @param tmp The new RequestedBy value
   */
  public void setRequestedBy(String tmp) {
    this.requestedBy = tmp;
  }


  /**
   * Sets the RequestedByDept attribute of the Project object
   *
   * @param tmp The new RequestedByDept value
   */
  public void setRequestedByDept(String tmp) {
    this.requestedByDept = tmp;
  }


  /**
   * Sets the RequestDate attribute of the Project object
   *
   * @param tmp The new RequestDate value
   */
  public void setRequestDate(Timestamp tmp) {
    this.requestDate = tmp;
  }


  /**
   * Sets the requestDate attribute of the Project object
   *
   * @param tmp The new requestDate value
   */
  public void setRequestDate(String tmp) {
    requestDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the Approved attribute of the Project object
   *
   * @param tmp The new Approved value
   */
  public void setApproved(boolean tmp) {
    this.approved = tmp;
  }


  /**
   * Sets the approved attribute of the Project object
   *
   * @param tmp The new approved value
   */
  public void setApproved(String tmp) {
    approved = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   * Sets the ApprovalDate attribute of the Project object
   *
   * @param tmp The new ApprovalDate value
   */
  public void setApprovalDate(java.sql.Timestamp tmp) {
    this.approvalDate = tmp;
  }


  /**
   * Sets the approvalDate attribute of the Project object
   *
   * @param tmp The new approvalDate value
   */
  public void setApprovalDate(String tmp) {
    approvalDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   * Sets the closed attribute of the Project object
   *
   * @param tmp The new closed value
   */
  public void setClosed(boolean tmp) {
    this.closed = tmp;
  }


  /**
   * Sets the closed attribute of the Project object
   *
   * @param tmp The new closed value
   */
  public void setClosed(String tmp) {
    closed = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   * Sets the closeDate attribute of the Project object
   *
   * @param tmp The new closeDate value
   */
  public void setCloseDate(java.sql.Timestamp tmp) {
    this.closeDate = tmp;
  }


  /**
   * Sets the closeDate attribute of the Project object
   *
   * @param tmp The new closeDate value
   */
  public void setCloseDate(String tmp) {
    this.closeDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   * Sets the estimatedCloseDate attribute of the Project object
   *
   * @param tmp The new estimatedCloseDate value
   */
  public void setEstimatedCloseDate(Timestamp tmp) {
    this.estimatedCloseDate = tmp;
  }


  /**
   * Sets the estimatedCloseDate attribute of the Project object
   *
   * @param tmp The new estimatedCloseDate value
   */
  public void setEstimatedCloseDate(String tmp) {
    this.estimatedCloseDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the budget attribute of the Project object
   *
   * @param tmp The new budget value
   */
  public void setBudget(double tmp) {
    this.budget = tmp;
  }


  /**
   * Sets the budget attribute of the Project object
   *
   * @param tmp The new budget value
   */
  public void setBudget(String tmp) {
    this.budget = Double.parseDouble(tmp);
  }


  /**
   * Sets the budgetCurrency attribute of the Project object
   *
   * @param tmp The new budgetCurrency value
   */
  public void setBudgetCurrency(String tmp) {
    this.budgetCurrency = tmp;
  }


  /**
   * Sets the owner attribute of the Project object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the owner attribute of the Project object
   *
   * @param tmp The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   * Sets the enteredBy attribute of the Project object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the entered attribute of the Project object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the entered attribute of the Project object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    entered = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Project object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the Project object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modified attribute of the Project object
   *
   * @param tmp The new modified value
   */
  public void setModified(Timestamp tmp) {
    modified = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the Project object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the Project object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the Requirements attribute of the Project object
   *
   * @param tmp The new Requirements value
   */
  public void setRequirements(RequirementList tmp) {
    this.requirements = tmp;
  }


  /**
   * Sets the Team attribute of the Project object
   *
   * @param tmp The new Team value
   */
  public void setTeam(TeamMemberList tmp) {
    this.team = tmp;
  }


  /**
   * Sets the employeeTeam attribute of the Project object
   *
   * @param tmp The new employeeTeam value
   */
  public void setEmployeeTeam(TeamMemberList tmp) {
    this.employeeTeam = tmp;
  }


  /**
   * Sets the accountContactTeam attribute of the Project object
   *
   * @param tmp The new accountContactTeam value
   */
  public void setAccountContactTeam(TeamMemberList tmp) {
    this.accountContactTeam = tmp;
  }


  /**
   * Sets the portalUserTeam attribute of the Project object
   *
   * @param tmp The new portalUserTeam value
   */
  public void setPortalUserTeam(TeamMemberList tmp) {
    this.portalUserTeam = tmp;
  }


  /**
   * Sets the Assignments attribute of the Project object
   *
   * @param tmp The new Assignments value
   */
  public void setAssignments(AssignmentList tmp) {
    this.assignments = tmp;
  }


  /**
   * Sets the Issues attribute of the Project object
   *
   * @param tmp The new Issues value
   */
  public void setIssues(IssueList tmp) {
    this.issues = tmp;
  }


  /**
   * Sets the Files attribute of the Project object
   *
   * @param tmp The new Files value
   */
  public void setFiles(FileItemList tmp) {
    this.files = tmp;
  }


  /**
   * Sets the assignmentsForUser attribute of the Project object
   *
   * @param tmp The new assignmentsForUser value
   */
  public void setAssignmentsForUser(int tmp) {
    this.assignmentsForUser = tmp;
  }


  /**
   * Sets the lastIssues attribute of the Project object
   *
   * @param tmp The new lastIssues value
   */
  public void setLastIssues(int tmp) {
    this.lastIssues = tmp;
  }


  /**
   * Sets the lastNews attribute of the Project object
   *
   * @param tmp The new lastNews value
   */
  public void setLastNews(int tmp) {
    this.lastNews = tmp;
  }


  /**
   * Sets the currentNews attribute of the Project object
   *
   * @param tmp The new currentNews value
   */
  public void setCurrentNews(int tmp) {
    this.currentNews = tmp;
  }


  /**
   * Sets the trashedDate attribute of the Project object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the Project object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the trashedDate attribute of the Project object
   *
   * @return The trashedDate value
   */
  public Timestamp getTrashedDate() {
    return trashedDate;
  }

  public boolean isTrashed() {
    return (trashedDate != null);
  }


  /**
   * Sets the withAssignmentDaysComplete attribute of the Project object
   *
   * @param tmp The new withAssignmentDaysComplete value
   */
  public void setWithAssignmentDaysComplete(int tmp) {
    this.withAssignmentDaysComplete = tmp;
  }


  /**
   * Sets the incompleteAssignmentsOnly attribute of the Project object
   *
   * @param tmp The new incompleteAssignmentsOnly value
   */
  public void setIncompleteAssignmentsOnly(boolean tmp) {
    this.incompleteAssignmentsOnly = tmp;
  }


  /**
   * Sets the assignmentAlertRangeStart attribute of the Project object
   *
   * @param tmp The new assignmentAlertRangeStart value
   */
  public void setAssignmentAlertRangeStart(java.sql.Timestamp tmp) {
    assignmentAlertRangeStart = tmp;
  }


  /**
   * Sets the assignmentAlertRangeEnd attribute of the Project object
   *
   * @param tmp The new assignmentAlertRangeEnd value
   */
  public void setAssignmentAlertRangeEnd(java.sql.Timestamp tmp) {
    assignmentAlertRangeEnd = tmp;
  }


  /**
   * Sets the buildRequirementAssignments attribute of the Project object
   *
   * @param tmp The new buildRequirementAssignments value
   */
  public void setBuildRequirementAssignments(boolean tmp) {
    this.buildRequirementAssignments = tmp;
  }


  /**
   * Sets the portal attribute of the Project object
   *
   * @param tmp The new portal value
   */
  public void setPortal(boolean tmp) {
    this.portal = tmp;
  }


  /**
   * Sets the portal attribute of the Project object
   *
   * @param tmp The new portal value
   */
  public void setPortal(String tmp) {
    this.portal = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the portalHeader attribute of the Project object
   *
   * @return The portalHeader value
   */
  public String getPortalHeader() {
    return portalHeader;
  }


  /**
   * Sets the portalHeader attribute of the Project object
   *
   * @param tmp The new portalHeader value
   */
  public void setPortalHeader(String tmp) {
    this.portalHeader = tmp;
  }


  /**
   * Gets the portalFormat attribute of the Project object
   *
   * @return The portalFormat value
   */
  public String getPortalFormat() {
    return portalFormat;
  }


  /**
   * Sets the portalFormat attribute of the Project object
   *
   * @param tmp The new portalFormat value
   */
  public void setPortalFormat(String tmp) {
    this.portalFormat = tmp;
  }


  /**
   * Gets the portalKey attribute of the Project object
   *
   * @return The portalKey value
   */
  public String getPortalKey() {
    return portalKey;
  }


  /**
   * Sets the portalKey attribute of the Project object
   *
   * @param tmp The new portalKey value
   */
  public void setPortalKey(String tmp) {
    this.portalKey = tmp;
  }


  /**
   * Gets the portalBuildNewsBody attribute of the Project object
   *
   * @return The portalBuildNewsBody value
   */
  public boolean getPortalBuildNewsBody() {
    return portalBuildNewsBody;
  }


  /**
   * Sets the portalBuildNewsBody attribute of the Project object
   *
   * @param tmp The new portalBuildNewsBody value
   */
  public void setPortalBuildNewsBody(boolean tmp) {
    this.portalBuildNewsBody = tmp;
  }


  /**
   * Sets the portalBuildNewsBody attribute of the Project object
   *
   * @param tmp The new portalBuildNewsBody value
   */
  public void setPortalBuildNewsBody(String tmp) {
    this.portalBuildNewsBody = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the portalNewsMenu attribute of the Project object
   *
   * @return The portalNewsMenu value
   */
  public boolean getPortalNewsMenu() {
    return portalNewsMenu;
  }


  /**
   * Sets the portalNewsMenu attribute of the Project object
   *
   * @param tmp The new portalNewsMenu value
   */
  public void setPortalNewsMenu(boolean tmp) {
    this.portalNewsMenu = tmp;
  }


  /**
   * Sets the portalNewsMenu attribute of the Project object
   *
   * @param tmp The new portalNewsMenu value
   */
  public void setPortalNewsMenu(String tmp) {
    this.portalNewsMenu = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the portalPageType attribute of the Project object
   *
   * @return The portalPageType value
   */
  public int getPortalPageType() {
    return portalPageType;
  }


  /**
   * Sets the portalPageType attribute of the Project object
   *
   * @param tmp The new portalPageType value
   */
  public void setPortalPageType(int tmp) {
    this.portalPageType = tmp;
  }


  /**
   * Sets the portalPageType attribute of the Project object
   *
   * @param tmp The new portalPageType value
   */
  public void setPortalPageType(String tmp) {
    this.portalPageType = Integer.parseInt(tmp);
  }


  /**
   * Sets the allowGuests attribute of the Project object
   *
   * @param tmp The new allowGuests value
   */
  public void setAllowGuests(boolean tmp) {
    this.allowGuests = tmp;
  }


  /**
   * Sets the allowGuests attribute of the Project object
   *
   * @param tmp The new allowGuests value
   */
  public void setAllowGuests(String tmp) {
    this.allowGuests = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the updateAllowGuests attribute of the Project object
   *
   * @param tmp The new updateAllowGuests value
   */
  public void setUpdateAllowGuests(boolean tmp) {
    this.updateAllowGuests = tmp;
  }


  /**
   * Gets the allowsUserObservers attribute of the Project object
   *
   * @return The allowsUserObservers value
   */
  public boolean getAllowsUserObservers() {
    return allowsUserObservers;
  }


  /**
   * Sets the allowsUserObservers attribute of the Project object
   *
   * @param tmp The new allowsUserObservers value
   */
  public void setAllowsUserObservers(boolean tmp) {
    this.allowsUserObservers = tmp;
  }


  /**
   * Sets the allowsUserObservers attribute of the Project object
   *
   * @param tmp The new allowsUserObservers value
   */
  public void setAllowsUserObservers(String tmp) {
    this.allowsUserObservers = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showCalendar attribute of the Project object
   *
   * @param tmp The new showCalendar value
   */
  public void setShowCalendar(boolean tmp) {
    this.showCalendar = tmp;
  }


  /**
   * Sets the showCalendar attribute of the Project object
   *
   * @param tmp The new showCalendar value
   */
  public void setShowCalendar(String tmp) {
    this.showCalendar = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showNews attribute of the Project object
   *
   * @param tmp The new showNews value
   */
  public void setShowNews(boolean tmp) {
    this.showNews = tmp;
  }


  /**
   * Sets the showNews attribute of the Project object
   *
   * @param tmp The new showNews value
   */
  public void setShowNews(String tmp) {
    this.showNews = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showDetails attribute of the Project object
   *
   * @param tmp The new showDetails value
   */
  public void setShowDetails(boolean tmp) {
    this.showDetails = tmp;
  }


  /**
   * Sets the showDetails attribute of the Project object
   *
   * @param tmp The new showDetails value
   */
  public void setShowDetails(String tmp) {
    this.showDetails = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showTeam attribute of the Project object
   *
   * @param tmp The new showTeam value
   */
  public void setShowTeam(boolean tmp) {
    this.showTeam = tmp;
  }


  /**
   * Sets the showTeam attribute of the Project object
   *
   * @param tmp The new showTeam value
   */
  public void setShowTeam(String tmp) {
    this.showTeam = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showAccounts attribute of the Project object
   *
   * @param showAccounts The new showAccounts value
   */
  public void setShowAccounts(boolean showAccounts) {
    this.showAccounts = showAccounts;
  }


  /**
   * Sets the showAccounts attribute of the Project object
   *
   * @param tmp The new showAccounts value
   */
  public void setShowAccounts(String tmp) {
    this.showAccounts = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showPlan attribute of the Project object
   *
   * @param tmp The new showPlan value
   */
  public void setShowPlan(boolean tmp) {
    this.showPlan = tmp;
  }


  /**
   * Sets the showPlan attribute of the Project object
   *
   * @param tmp The new showPlan value
   */
  public void setShowPlan(String tmp) {
    this.showPlan = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showLists attribute of the Project object
   *
   * @param tmp The new showLists value
   */
  public void setShowLists(boolean tmp) {
    this.showLists = tmp;
  }


  /**
   * Sets the showLists attribute of the Project object
   *
   * @param tmp The new showLists value
   */
  public void setShowLists(String tmp) {
    this.showLists = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showDiscussion attribute of the Project object
   *
   * @param tmp The new showDiscussion value
   */
  public void setShowDiscussion(boolean tmp) {
    this.showDiscussion = tmp;
  }


  /**
   * Sets the showDiscussion attribute of the Project object
   *
   * @param tmp The new showDiscussion value
   */
  public void setShowDiscussion(String tmp) {
    this.showDiscussion = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showTickets attribute of the Project object
   *
   * @param tmp The new showTickets value
   */
  public void setShowTickets(boolean tmp) {
    this.showTickets = tmp;
  }


  /**
   * Sets the showTickets attribute of the Project object
   *
   * @param tmp The new showTickets value
   */
  public void setShowTickets(String tmp) {
    this.showTickets = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showDocuments attribute of the Project object
   *
   * @param tmp The new showDocuments value
   */
  public void setShowDocuments(boolean tmp) {
    this.showDocuments = tmp;
  }


  /**
   * Sets the showDocuments attribute of the Project object
   *
   * @param tmp The new showDocuments value
   */
  public void setShowDocuments(String tmp) {
    this.showDocuments = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the labelCalendar attribute of the Project object
   *
   * @param tmp The new labelCalendar value
   */
  public void setLabelCalendar(String tmp) {
    this.labelCalendar = tmp;
  }


  /**
   * Sets the labelNews attribute of the Project object
   *
   * @param tmp The new labelNews value
   */
  public void setLabelNews(String tmp) {
    this.labelNews = tmp;
  }


  /**
   * Sets the labelDetails attribute of the Project object
   *
   * @param tmp The new labelDetails value
   */
  public void setLabelDetails(String tmp) {
    this.labelDetails = tmp;
  }


  /**
   * Sets the labelTeam attribute of the Project object
   *
   * @param tmp The new labelTeam value
   */
  public void setLabelTeam(String tmp) {
    this.labelTeam = tmp;
  }


  /**
   * Sets the labelAccounts attribute of the Project object
   *
   * @param tmp The new labelAccounts value
   */
  public void setLabelAccounts(String tmp) {
    this.labelAccounts = tmp;
  }


  /**
   * Sets the labelPlan attribute of the Project object
   *
   * @param tmp The new labelPlan value
   */
  public void setLabelPlan(String tmp) {
    this.labelPlan = tmp;
  }


  /**
   * Sets the labelLists attribute of the Project object
   *
   * @param tmp The new labelLists value
   */
  public void setLabelLists(String tmp) {
    this.labelLists = tmp;
  }


  /**
   * Sets the labelDiscussion attribute of the Project object
   *
   * @param tmp The new labelDiscussion value
   */
  public void setLabelDiscussion(String tmp) {
    this.labelDiscussion = tmp;
  }


  /**
   * Sets the labelTickets attribute of the Project object
   *
   * @param tmp The new labelTickets value
   */
  public void setLabelTickets(String tmp) {
    this.labelTickets = tmp;
  }


  /**
   * Sets the labelDocuments attribute of the Project object
   *
   * @param tmp The new labelDocuments value
   */
  public void setLabelDocuments(String tmp) {
    this.labelDocuments = tmp;
  }


  /**
   * Sets the requestDateTimeZone attribute of the Project object
   *
   * @param tmp The new requestDateTimeZone value
   */
  public void setRequestDateTimeZone(String tmp) {
    this.requestDateTimeZone = tmp;
  }


  /**
   * Sets the estimatedCloseDate attribute of the Project object
   *
   * @param tmp The new estimatedCloseDate value
   */
  public void setEstimatedCloseDateTimeZone(String tmp) {
    this.estimatedCloseDateTimeZone = tmp;
  }


  /**
   * Gets the requestDateTimeZone attribute of the Project object
   *
   * @return The requestDateTimeZone value
   */
  public String getRequestDateTimeZone() {
    return requestDateTimeZone;
  }


  /**
   * Gets the estimatedCloseDate attribute of the Project object
   *
   * @return The estimatedCloseDate value
   */
  public String getEstimatedCloseDateTimeZone() {
    return estimatedCloseDateTimeZone;
  }


  /**
   * Gets the Id attribute of the Project object
   *
   * @return The Id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the GroupId attribute of the Project object
   *
   * @return The GroupId value
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   * Gets the DepartmentId attribute of the Project object
   *
   * @return The DepartmentId value
   */
  public int getDepartmentId() {
    return departmentId;
  }


  /**
   * Gets the categoryId attribute of the Project object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Gets the templateId attribute of the Project object
   *
   * @return The templateId value
   */
  public int getTemplateId() {
    return templateId;
  }


  /**
   * Gets the Title attribute of the Project object
   *
   * @return The Title value
   */
  public String getTitle() {
    return title;
  }


  /**
   * Gets the ShortDescription attribute of the Project object
   *
   * @return The ShortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   * Gets the RequestedBy attribute of the Project object
   *
   * @return The RequestedBy value
   */
  public String getRequestedBy() {
    return requestedBy;
  }


  /**
   * Gets the RequestedByString attribute of the Project object
   *
   * @return The RequestedByString value
   */
  public String getRequestedByString() {
    if ((requestedBy == null) || (requestedBy.equals(""))) {
      return "unknown request source";
    } else {
      return requestedBy;
    }
  }


  /**
   * Gets the RequestedByDept attribute of the Project object
   *
   * @return The RequestedByDept value
   */
  public String getRequestedByDept() {
    return requestedByDept;
  }


  /**
   * Gets the RequestedByDeptString attribute of the Project object
   *
   * @return The RequestedByDeptString value
   */
  public String getRequestedByDeptString() {
    if (requestedByDept == null || requestedByDept.equals("")) {
      return "";
    } else {
      return (" from " + requestedByDept);
    }
  }


  /**
   * Gets the RequestDate attribute of the Project object
   *
   * @return The RequestDate value
   */
  public Timestamp getRequestDate() {
    return requestDate;
  }


  /**
   * Gets the RequestDateString attribute of the Project object
   *
   * @return The RequestDateString value
   */
  public String getRequestDateString() {
    try {
      return DateFormat.getDateInstance(3).format(requestDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   * Gets the closeDateString attribute of the Project object
   *
   * @return The closeDateString value
   */
  public String getCloseDateString() {
    try {
      return DateFormat.getDateInstance(3).format(closeDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   * Gets the estimatedCloseDate attribute of the Project object
   *
   * @return The estimatedCloseDate value
   */
  public Timestamp getEstimatedCloseDate() {
    return estimatedCloseDate;
  }


  /**
   * Gets the budget attribute of the Project object
   *
   * @return The budget value
   */
  public double getBudget() {
    return budget;
  }


  /**
   * Gets the budgetCurrency attribute of the Project object
   *
   * @return The budgetCurrency value
   */
  public String getBudgetCurrency() {
    return budgetCurrency;
  }


  /**
   * Gets the Approved attribute of the Project object
   *
   * @return The Approved value
   */
  public boolean getApproved() {
    return approved;
  }


  /**
   * Gets the ApprovedString attribute of the Project object
   *
   * @return The ApprovedString value
   */
  public String getApprovedString() {
    if (approvalDate == null) {
      return "<font color='red'>Under Review</font>";
    } else {
      return "<font color='darkgreen'>Approved</font>";
    }
  }


  /**
   * Gets the ApprovalDate attribute of the Project object
   *
   * @return The ApprovalDate value
   */
  public java.sql.Timestamp getApprovalDate() {
    return approvalDate;
  }


  /**
   * Gets the closed attribute of the Project object
   *
   * @return The closed value
   */
  public boolean getClosed() {
    return closed;
  }


  /**
   * Gets the CloseDate attribute of the Project object
   *
   * @return The CloseDate value
   */
  public java.sql.Timestamp getCloseDate() {
    return closeDate;
  }


  /**
   * Gets the entered attribute of the Project object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the owner attribute of the Project object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Gets the enteredBy attribute of the Project object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the Project object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the Project object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the Requirements attribute of the Project object
   *
   * @return The Requirements value
   */
  public RequirementList getRequirements() {
    return requirements;
  }


  /**
   * Gets the Team attribute of the Project object
   *
   * @return The Team value
   */
  public TeamMemberList getTeam() {
    return team;
  }


  /**
   * Gets the employeeTeam attribute of the Project object
   *
   * @return The employeeTeam value
   */
  public TeamMemberList getEmployeeTeam() {
    return employeeTeam;
  }


  /**
   * Gets the accountContactTeam attribute of the Project object
   *
   * @return The accountContactTeam value
   */
  public TeamMemberList getAccountContactTeam() {
    return accountContactTeam;
  }


  /**
   * Gets the portalUserTeam attribute of the Project object
   *
   * @return The portalUserTeam value
   */
  public TeamMemberList getPortalUserTeam() {
    return portalUserTeam;
  }


  /**
   * Gets the Assignments attribute of the Project object
   *
   * @return The Assignments value
   */
  public AssignmentList getAssignments() {
    return assignments;
  }


  /**
   * Gets the AssignmentCount attribute of the Project object
   *
   * @return The AssignmentCount value
   */
  public int getAssignmentCount() {
    return assignments.size();
  }


  /**
   * Gets the Issues attribute of the Project object
   *
   * @return The Issues value
   */
  public IssueList getIssues() {
    return issues;
  }


  /**
   * Gets the news attribute of the Project object
   *
   * @return The news value
   */
  public NewsArticleList getNews() {
    return news;
  }


  /**
   * Gets the issueCategories attribute of the Project object
   *
   * @return The issueCategories value
   */
  public IssueCategoryList getIssueCategories() {
    return issueCategories;
  }


  /**
   * Gets the IssueCount attribute of the Project object
   *
   * @return The IssueCount value
   */
  public int getIssueCount() {
    return issues.size();
  }


  /**
   * Gets the newsCount attribute of the Project object
   *
   * @return The newsCount value
   */
  public int getNewsCount() {
    return news.size();
  }


  /**
   * Gets the Files attribute of the Project object
   *
   * @return The Files value
   */
  public FileItemList getFiles() {
    return files;
  }


  /**
   * Gets the paddedId attribute of the Project object
   *
   * @return The paddedId value
   */
  public String getPaddedId() {
    String padded = (String.valueOf(id));
    while (padded.length() < 6) {
      padded = "0" + padded;
    }
    return padded;
  }


  /**
   * Gets the portal attribute of the Project object
   *
   * @return The portal value
   */
  public boolean getPortal() {
    return portal;
  }


  /**
   * Gets the allowGuests attribute of the Project object
   *
   * @return The allowGuests value
   */
  public boolean getAllowGuests() {
    return allowGuests;
  }


  /**
   * Gets the updateAllowGuests attribute of the Project object
   *
   * @return The updateAllowGuests value
   */
  public boolean getUpdateAllowGuests() {
    return updateAllowGuests;
  }


  /**
   * Gets the showCalendar attribute of the Project object
   *
   * @return The showCalendar value
   */
  public boolean getShowCalendar() {
    return showCalendar;
  }


  /**
   * Gets the showNews attribute of the Project object
   *
   * @return The showNews value
   */
  public boolean getShowNews() {
    return showNews;
  }


  /**
   * Gets the showDetails attribute of the Project object
   *
   * @return The showDetails value
   */
  public boolean getShowDetails() {
    return showDetails;
  }


  /**
   * Gets the showTeam attribute of the Project object
   *
   * @return The showTeam value
   */
  public boolean getShowTeam() {
    return showTeam;
  }


  /**
   * Gets the showAccounts attribute of the Project object
   *
   * @return The showAccounts value
   */
  public boolean getShowAccounts() {
    return showAccounts;
  }


  /**
   * Gets the showPlan attribute of the Project object
   *
   * @return The showPlan value
   */
  public boolean getShowPlan() {
    return showPlan;
  }


  /**
   * Gets the showLists attribute of the Project object
   *
   * @return The showLists value
   */
  public boolean getShowLists() {
    return showLists;
  }


  /**
   * Gets the showDiscussion attribute of the Project object
   *
   * @return The showDiscussion value
   */
  public boolean getShowDiscussion() {
    return showDiscussion;
  }


  /**
   * Gets the showTickets attribute of the Project object
   *
   * @return The showTickets value
   */
  public boolean getShowTickets() {
    return showTickets;
  }


  /**
   * Gets the showDocuments attribute of the Project object
   *
   * @return The showDocuments value
   */
  public boolean getShowDocuments() {
    return showDocuments;
  }


  /**
   * Gets the labelNews attribute of the Project object
   *
   * @return The labelNews value
   */
  public String getLabelNews() {
    return labelNews;
  }


  /**
   * Gets the labelDetails attribute of the Project object
   *
   * @return The labelDetails value
   */
  public String getLabelDetails() {
    return labelDetails;
  }


  /**
   * Gets the labelTeam attribute of the Project object
   *
   * @return The labelTeam value
   */
  public String getLabelTeam() {
    return labelTeam;
  }


  /**
   * Gets the labelAccounts attribute of the Project object
   *
   * @return The labelAccounts value
   */
  public String getLabelAccounts() {
    return labelAccounts;
  }


  /**
   * Gets the labelPlan attribute of the Project object
   *
   * @return The labelPlan value
   */
  public String getLabelPlan() {
    return labelPlan;
  }


  /**
   * Gets the labelLists attribute of the Project object
   *
   * @return The labelLists value
   */
  public String getLabelLists() {
    return labelLists;
  }


  /**
   * Gets the labelDiscussion attribute of the Project object
   *
   * @return The labelDiscussion value
   */
  public String getLabelDiscussion() {
    return labelDiscussion;
  }


  /**
   * Gets the labelTickets attribute of the Project object
   *
   * @return The labelTickets value
   */
  public String getLabelTickets() {
    return labelTickets;
  }


  /**
   * Gets the labelDocuments attribute of the Project object
   *
   * @return The labelDocuments value
   */
  public String getLabelDocuments() {
    return labelDocuments;
  }


  /**
   * Gets the hasAccess attribute of the Project object
   *
   * @return The hasAccess value
   */
  public boolean getHasAccess() {
    return hasAccess;
  }


  /**
   * Sets the hasAccess attribute of the Project object
   *
   * @param hasAccess The new hasAccess value
   */
  public void setHasAccess(boolean hasAccess) {
    this.hasAccess = hasAccess;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int buildAssignmentList(Connection db) throws SQLException {
    assignments.setProject(this);
    assignments.setProjectId(this.getId());
    assignments.setAssignmentsForUser(assignmentsForUser);
    assignments.setIncompleteOnly(incompleteAssignmentsOnly);
    assignments.setWithDaysComplete(withAssignmentDaysComplete);
    assignments.setAlertRangeStart(assignmentAlertRangeStart);
    assignments.setAlertRangeEnd(assignmentAlertRangeEnd);
    assignments.buildList(db);
    return assignments.size();
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int buildIssueList(Connection db) throws SQLException {
    return buildIssueList(db, -1);
  }


  /**
   * Description of the Method
   *
   * @param db              Description of Parameter
   * @param issueCategoryId Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int buildIssueList(Connection db, int issueCategoryId) throws SQLException {
    //issues = new IssueList();
    issues.setProject(this);
    issues.setProjectId(this.getId());
    issues.setLastIssues(lastIssues);
    issues.setCategoryId(issueCategoryId);
    issues.buildList(db);
    return issues.size();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int buildNewsList(Connection db) throws SQLException {
    news.setProjectId(this.getId());
    news.setLastNews(lastNews);
    news.setCurrentNews(currentNews);
    news.buildList(db);
    return news.size();
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int buildIssueCategoryList(Connection db) throws SQLException {
    issueCategories.setProject(this);
    issueCategories.setProjectId(this.getId());
    issueCategories.buildList(db);
    return issueCategories.size();
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int buildRequirementList(Connection db) throws SQLException {
    requirements.setProject(this);
    requirements.setProjectId(this.getId());
    requirements.setBuildAssignments(buildRequirementAssignments);
    requirements.buildList(db);
    return requirements.size();
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildTeamMemberList(Connection db) throws SQLException {
    team.setProject(this);
    team.setProjectId(this.getId());
    team.buildList(db);

    employeeTeam.setProject(this);
    employeeTeam.setProjectId(this.getId());
    employeeTeam.setEmployeesOnly(true);
    employeeTeam.buildList(db);

    accountContactTeam.setProject(this);
    accountContactTeam.setProjectId(this.getId());
    accountContactTeam.setAccountContactsOnly(true);
    accountContactTeam.buildList(db);
    
    portalUserTeam.setProject(this);
    portalUserTeam.setProjectId(this.getId());
    portalUserTeam.setPortalUsersOnly(true);
    portalUserTeam.buildList(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int buildFileItemList(Connection db) throws SQLException {
    files.setLinkModuleId(Constants.PROJECTS_FILES);
    files.setLinkItemId(this.getId());
    files.buildList(db);
    return files.size();
  }

  public boolean hasPermissionList() {
    return (permissions != null && permissions.size() > 0);
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int buildPermissionList(Connection db) throws SQLException {
    permissions.setProjectId(this.getId());
    permissions.buildList(db);
    return permissions.size();
  }


  /**
   * Description of the Method
   *
   * @param db      Description of Parameter
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean insert(Connection db, ActionContext context) throws SQLException {
    //TODO: Retrieve user information from the session to be used
    return insert(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    Exception errorMessage = null;
    boolean autoCommit = db.getAutoCommit();
    try {
      if (autoCommit) {
        db.setAutoCommit(false);
      }
      StringBuffer sql = new StringBuffer();
      id = DatabaseUtils.getNextSeq(db, "projects_project_id_seq");
      sql.append(
          "INSERT INTO projects " +
          "(group_id, department_id, category_id, owner, enteredBy, modifiedBy, template_id, ");
      if (id > -1) {
        sql.append("project_id, ");
      }
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append(
          "title, shortDescription, requestedBy, requestedDept, requestDate, requestDate_timezone, " +
          "allow_guests, calendar_enabled, news_enabled, details_enabled, " +
          "team_enabled, accounts_enabled, plan_enabled, lists_enabled, discussion_enabled, " +
          "tickets_enabled, documents_enabled, " +
          "approvalDate, closeDate, est_closedate, est_closedate_timezone, budget, budget_currency) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?,");
      }
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append(
          "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, groupId);
      DatabaseUtils.setInt(pst, ++i, departmentId);
      DatabaseUtils.setInt(pst, ++i, categoryId);
      DatabaseUtils.setInt(pst, ++i, owner);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      DatabaseUtils.setInt(pst, ++i, templateId);
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setString(++i, title);
      pst.setString(++i, shortDescription);
      pst.setString(++i, requestedBy);
      pst.setString(++i, requestedByDept);
      if (requestDate == null) {
        pst.setNull(++i, java.sql.Types.DATE);
      } else {
        pst.setTimestamp(++i, requestDate);
      }
      pst.setString(++i, this.requestDateTimeZone);
      pst.setBoolean(++i, allowGuests);
      pst.setBoolean(++i, showCalendar);
      pst.setBoolean(++i, showNews);
      pst.setBoolean(++i, showDetails);
      pst.setBoolean(++i, showTeam);
      pst.setBoolean(++i, showAccounts);
      pst.setBoolean(++i, showPlan);
      pst.setBoolean(++i, showLists);
      pst.setBoolean(++i, showDiscussion);
      pst.setBoolean(++i, showTickets);
      pst.setBoolean(++i, showDocuments);
      if (approved && approvalDate == null) {
        java.util.Date tmpDate = new java.util.Date();
        approvalDate = new java.sql.Timestamp(tmpDate.getTime());
      }
      if (approvalDate == null) {
        pst.setNull(++i, java.sql.Types.DATE);
      } else {
        approvalDate.setNanos(0);
        pst.setTimestamp(++i, approvalDate);
      }
      if (closed) {
        java.util.Date tmpDate = new java.util.Date();
        closeDate = new java.sql.Timestamp(tmpDate.getTime());
        closeDate.setNanos(0);
      }
      DatabaseUtils.setTimestamp(pst, ++i, closeDate);
      DatabaseUtils.setTimestamp(pst, ++i, estimatedCloseDate);
      pst.setString(++i, this.estimatedCloseDateTimeZone);
      DatabaseUtils.setDouble(pst, ++i, budget);
      pst.setString(++i, budgetCurrency);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "projects_project_id_seq", id);
      //Insert the default permissions
      PermissionList.insertDefaultPermissions(db, id);
      TicketList.insertProjectTicketCount(db, id);
      //Add team, requirements and activities from an existing project
      //TODO: add news categories, etc.
      /*
       *  if (templateId > -1) {
       *  ProjectList projectList = new ProjectList();
       *  projectList.setGroupId(groupId);
       *  projectList.setProjectsForUser(enteredBy);
       *  projectList.setProjectId(templateId);
       *  projectList.buildList(db);
       *  if (projectList.size() == 1) {
       *  Project previousProject = (Project) projectList.get(0);
       *  previousProject.buildTeamMemberList(db);
       *  previousProject.buildRequirementList(db);
       *  previousProject.buildAssignmentList(db);
       *  TeamMemberList prevTeam = previousProject.getTeam();
       *  prevTeam.setProject(this);
       *  prevTeam.setProjectId(this.getId());
       *  prevTeam.setEnteredBy(this.enteredBy);
       *  prevTeam.setModifiedBy(this.modifiedBy);
       *  prevTeam.insert(db);
       *  RequirementList prevRequirements = previousProject.getRequirements();
       *  prevRequirements.setProject(this);
       *  prevRequirements.setProjectId(this.getId());
       *  prevRequirements.setEnteredBy(this.enteredBy);
       *  prevRequirements.setModifiedBy(this.modifiedBy);
       *  prevRequirements.insert(db);
       *  /TODO: Assignments need new requirement id before insert
       *  AssignmentList prevAssignments = previousProject.getAssignments();
       *  prevAssignments.setProject(this);
       *  prevAssignments.setProjectId(this.getId());
       *  prevAssignments.setEnteredBy(this.enteredBy);
       *  prevAssignments.setModifiedBy(this.modifiedBy);
       *  prevAssignments.setOffsetDate(this.getRequestDate());
       *  prevAssignments.insert(db);
       *  }
       *  }
       */
      if (portal) {
        int j = 0;
        pst = db.prepareStatement(
            "UPDATE projects " +
            "SET portal = ?, portal_key = ?, portal_page_type = ? " +
            "WHERE project_id = ? ");
        pst.setBoolean(++j, true);
        pst.setString(++j, portalKey);
        pst.setInt(++j, portalPageType);
        pst.setInt(++j, id);
        pst.execute();
        pst.close();
      }
      if (autoCommit) {
        db.commit();
      }
    } catch (Exception e) {
      errorMessage = e;
      if (autoCommit) {
        db.rollback();
      }
    } finally {
      if (autoCommit) {
        db.setAutoCommit(true);
      }
    }
    if (errorMessage != null) {
      throw new SQLException(errorMessage.getMessage());
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param toTrash   Description of the Parameter
   * @param tmpUserId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    int count = 0;
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      sql.append(
          "UPDATE projects " +
          "SET trashed_date = ? , " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
          "modifiedby = ? " +
          "WHERE project_id = ? ");
      int i = 0;
      pst = db.prepareStatement(sql.toString());
      if (toTrash) {
        DatabaseUtils.setTimestamp(
            pst, ++i, new Timestamp(System.currentTimeMillis()));
      } else {
        DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
      }
      DatabaseUtils.setInt(pst, ++i, tmpUserId);
      pst.setInt(++i, this.id);
      pst.executeUpdate();
      pst.close();

      TicketList ticketList = new TicketList();
      ticketList.setProjectId(this.getId());
      if (!toTrash) {
        ticketList.setIncludeOnlyTrashed(true);
      }
      ticketList.buildList(db);
      ticketList.updateStatus(db, toTrash, tmpUserId);

      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param basePath Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db, String basePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    int recordCount = 0;
    try {
      db.setAutoCommit(false);

      buildIssueCategoryList(db);
      issueCategories.delete(db, basePath);
      issueCategories = null;

      TicketList tickets = new TicketList();
      tickets.setProjectId(id);
      tickets.buildList(db);
      tickets.delete(db, basePath);
      tickets = null;

      tickets = new TicketList();
      tickets.setProjectId(id);
      tickets.setIncludeOnlyTrashed(true);
      tickets.buildList(db);
      tickets.delete(db, basePath);
      tickets = null;

      TicketList.deleteProjectTicketCount(db, id);

      TaskCategoryList taskCategories = new TaskCategoryList();
      taskCategories.setProjectId(id);
      taskCategories.buildList(db);
      taskCategories.delete(db);
      taskCategories = null;

      buildTeamMemberList(db);
      team.delete(db);
      team = null;

      buildRequirementList(db);
      requirements.delete(db);
      requirements = null;

      buildFileItemList(db);
      files.delete(db, getFileLibraryPath(basePath, "projects"));
      files = null;

      FileFolderList folders = new FileFolderList();
      folders.setLinkModuleId(Constants.PROJECTS_FILES);
      folders.setLinkItemId(id);
      folders.buildList(db);
      folders.delete(db);
      folders = null;

      NewsArticleList.delete(db, id);
      NewsArticleCategoryList.delete(db, id);
      PermissionList.delete(db, id);

      //Remove related accounts
      ProjectUtils.removeAccountsFromProject(db, this.id);

      //Delete the actual project
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM projects " +
          "WHERE project_id = ? ");
      pst.setInt(1, id);
      recordCount = pst.executeUpdate();
      pst.close();
      db.commit();
    } catch (Exception e) {
      db.rollback();
      e.printStackTrace(System.out);
    } finally {
      db.setAutoCommit(true);
    }
    if (recordCount == 0) {
      return false;
    } else {
      return true;
    }
  }


  /**
   * Description of the Method
   *
   * @param db      Description of Parameter
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    return update(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }

    int resultCount = 0;
    boolean previouslyApproved = false;
    java.sql.Timestamp previousApprovalDate = null;
    boolean previouslyClosed = false;
    java.sql.Timestamp previousCloseDate = null;

    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM projects " +
        "WHERE project_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      previousApprovalDate = rs.getTimestamp("approvaldate");
      previouslyApproved = (previousApprovalDate != null);
      previousCloseDate = rs.getTimestamp("closedate");
      previouslyClosed = (previousCloseDate != null);
    }
    rs.close();
    pst.close();

    pst = db.prepareStatement(
        "UPDATE projects " +
        "SET department_id = ?, category_id = ?, title = ?, shortDescription = ?, requestedBy = ?, " +
        "requestedDept = ?, requestDate = ?, requestDate_timezone = ?, " +
        "approvalDate = ?, closeDate = ?, owner = ?, est_closedate = ?, est_closedate_timezone = ?, budget = ?, " +
        "budget_currency = ?, " +
        "modifiedby = ?, modified = CURRENT_TIMESTAMP " +
        "WHERE project_id = ? " +
        "AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, departmentId);
    DatabaseUtils.setInt(pst, ++i, categoryId);
    pst.setString(++i, title);
    pst.setString(++i, shortDescription);
    pst.setString(++i, requestedBy);
    pst.setString(++i, requestedByDept);
    DatabaseUtils.setTimestamp(pst, ++i, requestDate);
    pst.setString(++i, this.requestDateTimeZone);
    if (previouslyApproved && approved) {
      pst.setTimestamp(++i, previousApprovalDate);
    } else if (!previouslyApproved && approved) {
      java.util.Date tmpDate = new java.util.Date();
      approvalDate = new java.sql.Timestamp(tmpDate.getTime());
      approvalDate.setNanos(0);
      pst.setTimestamp(++i, approvalDate);
    } else if (!approved) {
      pst.setNull(++i, java.sql.Types.DATE);
    }
    if (previouslyClosed && closed) {
      pst.setTimestamp(++i, previousCloseDate);
    } else if (!previouslyClosed && closed) {
      java.util.Date tmpDate = new java.util.Date();
      closeDate = new java.sql.Timestamp(tmpDate.getTime());
      closeDate.setNanos(0);
      pst.setTimestamp(++i, closeDate);
    } else if (!closed) {
      pst.setNull(++i, java.sql.Types.DATE);
    }
    DatabaseUtils.setInt(pst, ++i, owner);
    DatabaseUtils.setTimestamp(pst, ++i, estimatedCloseDate);
    pst.setString(++i, this.estimatedCloseDateTimeZone);
    DatabaseUtils.setDouble(pst, ++i, budget);
    pst.setString(++i, budgetCurrency);
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    if(this.getModified() != null){
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int updateFeatures(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    int resultCount = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE projects " +
          "SET " + (updateAllowGuests ? "allow_guests = ?," : "") + "calendar_enabled = ?, news_enabled = ?, details_enabled = ?, " +
          "team_enabled = ?, accounts_enabled = ?, plan_enabled = ?, lists_enabled = ?, discussion_enabled = ?, " +
          "tickets_enabled = ?, documents_enabled = ?, " +
          "calendar_label = ?, news_label = ?, details_label = ?, team_label = ?, accounts_label = ?, plan_label = ?, lists_label = ?, " +
          "discussion_label = ?, tickets_label = ?, documents_label = ?, " +
          "modifiedby = ?, modified = CURRENT_TIMESTAMP " +
          "WHERE project_id = ? " +
          "AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
      int i = 0;
      if (updateAllowGuests) {
        pst.setBoolean(++i, allowGuests);
      }
      pst.setBoolean(++i, showCalendar);
      pst.setBoolean(++i, showNews);
      pst.setBoolean(++i, showDetails);
      pst.setBoolean(++i, showTeam);
      pst.setBoolean(++i, showAccounts);
      pst.setBoolean(++i, showPlan);
      pst.setBoolean(++i, showLists);
      pst.setBoolean(++i, showDiscussion);
      pst.setBoolean(++i, showTickets);
      pst.setBoolean(++i, showDocuments);
      pst.setString(++i, labelCalendar);
      pst.setString(++i, labelNews);
      pst.setString(++i, labelDetails);
      pst.setString(++i, labelTeam);
      pst.setString(++i, labelAccounts);
      pst.setString(++i, labelPlan);
      pst.setString(++i, labelLists);
      pst.setString(++i, labelDiscussion);
      pst.setString(++i, labelTickets);
      pst.setString(++i, labelDocuments);
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getId());
      if(this.getModified() != null){
        pst.setTimestamp(++i, modified);
      }
      resultCount = pst.executeUpdate();
      pst.close();
      if (portal) {
        pst = db.prepareStatement(
            "UPDATE projects " +
            "SET portal = ? " +
            "WHERE portal = ? " +
            "AND project_id <> ? ");
        pst.setBoolean(1, false);
        pst.setBoolean(2, true);
        pst.setInt(3, id);
        pst.execute();
        pst.close();
      }
      db.commit();
    } catch (Exception e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("project_id");
    groupId = DatabaseUtils.getInt(rs, "group_id");
    departmentId = DatabaseUtils.getInt(rs, "department_id");
    //templateId
    title = rs.getString("title");
    shortDescription = rs.getString("shortDescription");
    requestedBy = rs.getString("requestedBy");
    requestedByDept = rs.getString("requestedDept");
    requestDate = rs.getTimestamp("requestDate");
    approvalDate = rs.getTimestamp("approvalDate");
    approved = (approvalDate != null);
    closeDate = rs.getTimestamp("closeDate");
    closed = (closeDate != null);
    owner = DatabaseUtils.getInt(rs, "owner");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredBy");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedBy");
    categoryId = DatabaseUtils.getInt(rs, "category_id");
    portal = rs.getBoolean("portal");
    allowGuests = rs.getBoolean("allow_guests");
    showNews = rs.getBoolean("news_enabled");
    showDetails = rs.getBoolean("details_enabled");
    showTeam = rs.getBoolean("team_enabled");
    showPlan = rs.getBoolean("plan_enabled");
    showLists = rs.getBoolean("lists_enabled");
    showDiscussion = rs.getBoolean("discussion_enabled");
    showTickets = rs.getBoolean("tickets_enabled");
    showDocuments = rs.getBoolean("documents_enabled");
    labelNews = rs.getString("news_label");
    labelDetails = rs.getString("details_label");
    labelTeam = rs.getString("team_label");
    labelPlan = rs.getString("plan_label");
    labelLists = rs.getString("lists_label");
    labelDiscussion = rs.getString("discussion_label");
    labelTickets = rs.getString("tickets_label");
    labelDocuments = rs.getString("documents_label");
    estimatedCloseDate = rs.getTimestamp("est_closedate");
    budget = DatabaseUtils.getDouble(rs, "budget");
    budgetCurrency = rs.getString("budget_currency");
    this.requestDateTimeZone = rs.getString("requestDate_timezone");
    this.estimatedCloseDateTimeZone = rs.getString("est_closedate_timezone");
    portalHeader = rs.getString("portal_header");
    portalFormat = rs.getString("portal_format");
    portalKey = rs.getString("portal_key");
    portalBuildNewsBody = rs.getBoolean("portal_build_news_body");
    portalNewsMenu = rs.getBoolean("portal_news_menu");
    allowsUserObservers = rs.getBoolean("allows_user_observers");
    portalPageType = DatabaseUtils.getInt(rs, "portal_page_type");
    showCalendar = rs.getBoolean("calendar_enabled");
    labelCalendar = rs.getString("calendar_label");
    showAccounts = rs.getBoolean("accounts_enabled");
    labelAccounts = rs.getString("accounts_label");
    trashedDate = rs.getTimestamp("trashed_date");
    //Set the related objects
    requirements.setProject(this);
    requirements.setProjectId(this.getId());
    assignments.setProject(this);
    assignments.setProjectId(this.getId());
    issues.setProject(this);
    issues.setProjectId(this.getId());
    issueCategories.setProject(this);
    issueCategories.setProjectId(this.getId());
    team.setProject(this);
    team.setProjectId(this.getId());
    files.setLinkModuleId(Constants.PROJECTS_FILES);
    files.setLinkItemId(this.getId());
    news.setProjectId(this.getId());
  }


  /**
   * Gets the accessUserLevel attribute of the Project object
   *
   * @param permission Description of the Parameter
   * @return The accessUserLevel value
   */
  public int getAccessUserLevel(String permission) {
    return permissions.getAccessLevel(permission);
  }


  /**
   * Gets the label attribute of the Project object
   *
   * @param name Description of the Parameter
   * @return The label value
   */
  public String getLabel(String name) {
    String value = name;
    value = ObjectUtils.getParam(this, "Label" + name);
    if (value != null && !"".equals(value)) {
      return value;
    }
    return name;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param userId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void accept(Connection db, int userId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_team " +
        "SET status = ? " +
        "WHERE project_id = ? " +
        "AND user_id = ? " +
        "AND status = ? ");
    DatabaseUtils.setInt(pst, 1, TeamMember.STATUS_ACCEPTED);
    pst.setInt(2, id);
    pst.setInt(3, userId);
    pst.setInt(4, TeamMember.STATUS_PENDING);
    pst.executeUpdate();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param userId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void reject(Connection db, int userId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_team " +
        "SET status = ? " +
        "WHERE project_id = ? " +
        "AND user_id = ? " +
        "AND status = ? ");
    pst.setInt(1, TeamMember.STATUS_REFUSED);
    pst.setInt(2, id);
    pst.setInt(3, userId);
    pst.setInt(4, TeamMember.STATUS_PENDING);
    pst.executeUpdate();
    pst.close();
  }


  /**
   * The following fields depend on a timezone preference
   *
   * @return The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("requestDate");
    thisList.add("estimatedCloseDate");
    return thisList;
  }


  /**
   * Gets the numberParams attribute of the Project class
   *
   * @return The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("budget");
    return thisList;
  }
}

