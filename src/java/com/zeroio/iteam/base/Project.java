/*
 *  Copyright 2000-2003 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.sql.*;
import java.text.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  Represents a Project in iTeam
 *
 *@author     mrajkowski
 *@created    July 23, 2001
 *@version    $Id$
 */
public class Project extends GenericBean {

  private int id = -1;
  private int groupId = -1;
  private int departmentId = -1;
  private int templateId = -1;
  private String title = "";
  private String shortDescription = "";
  private String requestedBy = "";
  private String requestedByDept = "";
  private java.sql.Date requestDate = new java.sql.Date(new java.util.Date().getTime());
  private boolean approved = false;
  private java.sql.Timestamp approvalDate = null;
  private boolean closed = false;
  private java.sql.Timestamp closeDate = null;

  private int owner = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;

  private int assignmentsForUser = -1;
  private int lastIssues = -1;
  private boolean incompleteAssignmentsOnly = false;
  private int withAssignmentDaysComplete = -1;

  private boolean buildRequirementAssignments = false;
  private String userRange = null;

  private java.sql.Timestamp assignmentAlertRangeStart = null;
  private java.sql.Timestamp assignmentAlertRangeEnd = null;

  private RequirementList requirements = new RequirementList();
  private TeamMemberList team = new TeamMemberList();
  private AssignmentList assignments = new AssignmentList();
  private IssueCategoryList issueCategories = new IssueCategoryList();
  private IssueList issues = new IssueList();
  private FileItemList files = new FileItemList();


  /**
   *  Constructor for the Project object
   *
   *@since
   */
  public Project() { }


  /**
   *  Constructor for the Project object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public Project(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Project object
   *
   *@param  db                Description of Parameter
   *@param  thisId            Description of Parameter
   *@param  userRange         Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Project(Connection db, int thisId, String userRange) throws SQLException {
    this.userRange = userRange;
    queryRecord(db, thisId);
  }


  /**
   *  Constructor for the Project object
   *
   *@param  db                Description of the Parameter
   *@param  thisId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Project(Connection db, int thisId) throws SQLException {
    queryRecord(db, thisId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  thisId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void queryRecord(Connection db, int thisId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM projects p " +
        "WHERE p.project_id = ? ");
    if (userRange != null) {
      sql.append(
          "AND (project_id in (SELECT DISTINCT project_id FROM project_team WHERE user_id IN (" + userRange + ") " +
          "AND project_id = ?) " +
          "OR p.enteredBy IN (" + userRange + ")) ");
    }

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, thisId);
    if (userRange != null) {
      pst.setInt(++i, thisId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Sets the Id attribute of the Project object
   *
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Project object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the GroupId attribute of the Project object
   *
   *@param  tmp  The new GroupId value
   *@since
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   *  Sets the groupId attribute of the Project object
   *
   *@param  tmp  The new groupId value
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the DepartmentId attribute of the Project object
   *
   *@param  tmp  The new DepartmentId value
   *@since
   */
  public void setDepartmentId(int tmp) {
    this.departmentId = tmp;
  }


  /**
   *  Sets the departmentId attribute of the Project object
   *
   *@param  tmp  The new departmentId value
   */
  public void setDepartmentId(String tmp) {
    this.departmentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the templateId attribute of the Project object
   *
   *@param  tmp  The new templateId value
   */
  public void setTemplateId(int tmp) {
    this.templateId = tmp;
  }


  /**
   *  Sets the templateId attribute of the Project object
   *
   *@param  tmp  The new templateId value
   */
  public void setTemplateId(String tmp) {
    this.templateId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Title attribute of the Project object
   *
   *@param  tmp  The new Title value
   *@since
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   *  Sets the ShortDescription attribute of the Project object
   *
   *@param  tmp  The new ShortDescription value
   *@since
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   *  Sets the RequestedBy attribute of the Project object
   *
   *@param  tmp  The new RequestedBy value
   *@since
   */
  public void setRequestedBy(String tmp) {
    this.requestedBy = tmp;
  }


  /**
   *  Sets the RequestedByDept attribute of the Project object
   *
   *@param  tmp  The new RequestedByDept value
   *@since
   */
  public void setRequestedByDept(String tmp) {
    this.requestedByDept = tmp;
  }


  /**
   *  Sets the RequestDate attribute of the Project object
   *
   *@param  tmp  The new RequestDate value
   *@since
   */
  public void setRequestDate(java.sql.Date tmp) {
    this.requestDate = tmp;
  }


  /**
   *  Sets the requestDate attribute of the Project object
   *
   *@param  tmp  The new requestDate value
   */
  public void setRequestDate(String tmp) {
    requestDate = DatabaseUtils.parseDate(tmp);
  }


  /**
   *  Sets the Approved attribute of the Project object
   *
   *@param  tmp  The new Approved value
   *@since
   */
  public void setApproved(boolean tmp) {
    this.approved = tmp;
  }


  /**
   *  Sets the approved attribute of the Project object
   *
   *@param  tmp  The new approved value
   */
  public void setApproved(String tmp) {
    approved = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the ApprovalDate attribute of the Project object
   *
   *@param  tmp  The new ApprovalDate value
   *@since
   */
  public void setApprovalDate(java.sql.Timestamp tmp) {
    this.approvalDate = tmp;
  }


  /**
   *  Sets the approvalDate attribute of the Project object
   *
   *@param  tmp  The new approvalDate value
   */
  public void setApprovalDate(String tmp) {
    approvalDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the closed attribute of the Project object
   *
   *@param  tmp  The new closed value
   */
  public void setClosed(boolean tmp) {
    this.closed = tmp;
  }


  /**
   *  Sets the closed attribute of the Project object
   *
   *@param  tmp  The new closed value
   */
  public void setClosed(String tmp) {
    closed = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the closeDate attribute of the Project object
   *
   *@param  tmp  The new closeDate value
   */
  public void setCloseDate(java.sql.Timestamp tmp) {
    this.closeDate = tmp;
  }


  /**
   *  Sets the closeDate attribute of the Project object
   *
   *@param  tmp  The new closeDate value
   */
  public void setCloseDate(String tmp) {
    this.closeDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the owner attribute of the Project object
   *
   *@param  tmp  The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   *  Sets the owner attribute of the Project object
   *
   *@param  tmp  The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Project object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the entered attribute of the Project object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Project object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Project object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Project object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Project object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }



  /**
   *  Sets the Requirements attribute of the Project object
   *
   *@param  tmp  The new Requirements value
   *@since
   */
  public void setRequirements(RequirementList tmp) {
    this.requirements = tmp;
  }


  /**
   *  Sets the Team attribute of the Project object
   *
   *@param  tmp  The new Team value
   *@since
   */
  public void setTeam(TeamMemberList tmp) {
    this.team = tmp;
  }


  /**
   *  Sets the Assignments attribute of the Project object
   *
   *@param  tmp  The new Assignments value
   *@since
   */
  public void setAssignments(AssignmentList tmp) {
    this.assignments = tmp;
  }


  /**
   *  Sets the Issues attribute of the Project object
   *
   *@param  tmp  The new Issues value
   *@since
   */
  public void setIssues(IssueList tmp) {
    this.issues = tmp;
  }


  /**
   *  Sets the Files attribute of the Project object
   *
   *@param  tmp  The new Files value
   *@since
   */
  public void setFiles(FileItemList tmp) {
    this.files = tmp;
  }


  /**
   *  Sets the assignmentsForUser attribute of the Project object
   *
   *@param  tmp  The new assignmentsForUser value
   */
  public void setAssignmentsForUser(int tmp) {
    this.assignmentsForUser = tmp;
  }


  /**
   *  Sets the lastIssues attribute of the Project object
   *
   *@param  tmp  The new lastIssues value
   */
  public void setLastIssues(int tmp) {
    this.lastIssues = tmp;
  }


  /**
   *  Sets the withAssignmentDaysComplete attribute of the Project object
   *
   *@param  tmp  The new withAssignmentDaysComplete value
   */
  public void setWithAssignmentDaysComplete(int tmp) {
    this.withAssignmentDaysComplete = tmp;
  }


  /**
   *  Sets the incompleteAssignmentsOnly attribute of the Project object
   *
   *@param  tmp  The new incompleteAssignmentsOnly value
   */
  public void setIncompleteAssignmentsOnly(boolean tmp) {
    this.incompleteAssignmentsOnly = tmp;
  }


  /**
   *  Sets the assignmentAlertRangeStart attribute of the Project object
   *
   *@param  tmp  The new assignmentAlertRangeStart value
   */
  public void setAssignmentAlertRangeStart(java.sql.Timestamp tmp) {
    assignmentAlertRangeStart = tmp;
  }


  /**
   *  Sets the assignmentAlertRangeEnd attribute of the Project object
   *
   *@param  tmp  The new assignmentAlertRangeEnd value
   */
  public void setAssignmentAlertRangeEnd(java.sql.Timestamp tmp) {
    assignmentAlertRangeEnd = tmp;
  }


  /**
   *  Sets the buildRequirementAssignments attribute of the Project object
   *
   *@param  tmp  The new buildRequirementAssignments value
   */
  public void setBuildRequirementAssignments(boolean tmp) {
    this.buildRequirementAssignments = tmp;
  }


  /**
   *  Gets the Id attribute of the Project object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the GroupId attribute of the Project object
   *
   *@return    The GroupId value
   *@since
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   *  Gets the DepartmentId attribute of the Project object
   *
   *@return    The DepartmentId value
   *@since
   */
  public int getDepartmentId() {
    return departmentId;
  }


  /**
   *  Gets the templateId attribute of the Project object
   *
   *@return    The templateId value
   */
  public int getTemplateId() {
    return templateId;
  }


  /**
   *  Gets the Title attribute of the Project object
   *
   *@return    The Title value
   *@since
   */
  public String getTitle() {
    return title;
  }


  /**
   *  Gets the ShortDescription attribute of the Project object
   *
   *@return    The ShortDescription value
   *@since
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   *  Gets the RequestedBy attribute of the Project object
   *
   *@return    The RequestedBy value
   *@since
   */
  public String getRequestedBy() {
    return requestedBy;
  }


  /**
   *  Gets the RequestedByString attribute of the Project object
   *
   *@return    The RequestedByString value
   *@since
   */
  public String getRequestedByString() {
    if ((requestedBy == null) || (requestedBy.equals(""))) {
      return "unknown request source";
    } else {
      return requestedBy;
    }
  }


  /**
   *  Gets the RequestedByDept attribute of the Project object
   *
   *@return    The RequestedByDept value
   *@since
   */
  public String getRequestedByDept() {
    return requestedByDept;
  }


  /**
   *  Gets the RequestedByDeptString attribute of the Project object
   *
   *@return    The RequestedByDeptString value
   *@since
   */
  public String getRequestedByDeptString() {
    if (requestedByDept == null || requestedByDept.equals("")) {
      return "";
    } else {
      return (" from " + requestedByDept);
    }
  }


  /**
   *  Gets the RequestDate attribute of the Project object
   *
   *@return    The RequestDate value
   *@since
   */
  public java.sql.Date getRequestDate() {
    return requestDate;
  }


  /**
   *  Gets the RequestDateString attribute of the Project object
   *
   *@return    The RequestDateString value
   *@since
   */
  public String getRequestDateString() {
    try {
      return DateFormat.getDateInstance(3).format(requestDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Gets the closeDateString attribute of the Project object
   *
   *@return    The closeDateString value
   */
  public String getCloseDateString() {
    try {
      return DateFormat.getDateInstance(3).format(closeDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Gets the approvedDateString attribute of the Project object
   *
   *@return    The approvedDateString value
   */
  public String getApprovedDateString() {
    try {
      return DateFormat.getDateInstance(3).format(approvalDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Gets the Approved attribute of the Project object
   *
   *@return    The Approved value
   *@since
   */
  public boolean getApproved() {
    return approved;
  }


  /**
   *  Gets the ApprovedString attribute of the Project object
   *
   *@return    The ApprovedString value
   *@since
   */
  public String getApprovedString() {
    if (approvalDate == null) {
      return "<font color='red'>Under Review</font>";
    } else {
      return "<font color='darkgreen'>Approved</font>";
    }
  }


  /**
   *  Gets the ApprovalDate attribute of the Project object
   *
   *@return    The ApprovalDate value
   *@since
   */
  public java.sql.Timestamp getApprovalDate() {
    return approvalDate;
  }


  /**
   *  Gets the CloseDate attribute of the Project object
   *
   *@return    The CloseDate value
   *@since
   */
  public java.sql.Timestamp getCloseDate() {
    return closeDate;
  }


  /**
   *  Gets the entered attribute of the Project object
   *
   *@return    The entered value
   */
  public String getEntered() {
    return entered.toString();
  }


  /**
   *  Gets the enteredString attribute of the Project object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the owner attribute of the Project object
   *
   *@return    The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the enteredBy attribute of the Project object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the Project object
   *
   *@return    The modified value
   */
  public String getModified() {
    return (modified.toString());
  }


  /**
   *  Gets the modifiedString attribute of the Project object
   *
   *@return    The modifiedString value
   */
  public String getModifiedString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the modifiedBy attribute of the Project object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }



  /**
   *  Gets the Requirements attribute of the Project object
   *
   *@return    The Requirements value
   *@since
   */
  public RequirementList getRequirements() {
    return requirements;
  }


  /**
   *  Gets the Team attribute of the Project object
   *
   *@return    The Team value
   *@since
   */
  public TeamMemberList getTeam() {
    return team;
  }


  /**
   *  Gets the Assignments attribute of the Project object
   *
   *@return    The Assignments value
   *@since
   */
  public AssignmentList getAssignments() {
    return assignments;
  }


  /**
   *  Gets the AssignmentCount attribute of the Project object
   *
   *@return    The AssignmentCount value
   *@since
   */
  public int getAssignmentCount() {
    return assignments.size();
  }


  /**
   *  Gets the Issues attribute of the Project object
   *
   *@return    The Issues value
   *@since
   */
  public IssueList getIssues() {
    return issues;
  }


  /**
   *  Gets the issueCategories attribute of the Project object
   *
   *@return    The issueCategories value
   */
  public IssueCategoryList getIssueCategories() {
    return issueCategories;
  }


  /**
   *  Gets the IssueCount attribute of the Project object
   *
   *@return    The IssueCount value
   *@since
   */
  public int getIssueCount() {
    return issues.size();
  }


  /**
   *  Gets the Files attribute of the Project object
   *
   *@return    The Files value
   *@since
   */
  public FileItemList getFiles() {
    return files;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public int buildIssueList(Connection db) throws SQLException {
    return buildIssueList(db, -1);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  categoryId        Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int buildIssueList(Connection db, int categoryId) throws SQLException {
    //issues = new IssueList();
    issues.setProject(this);
    issues.setProjectId(this.getId());
    issues.setLastIssues(lastIssues);
    issues.setCategoryId(categoryId);
    issues.buildList(db);
    return issues.size();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int buildIssueCategoryList(Connection db) throws SQLException {
    issueCategories.setProject(this);
    issueCategories.setProjectId(this.getId());
    issueCategories.buildList(db);
    return issueCategories.size();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int buildRequirementList(Connection db) throws SQLException {
    requirements.setProject(this);
    requirements.setProjectId(this.getId());
    requirements.setBuildAssignments(buildRequirementAssignments);
    requirements.buildList(db);
    return requirements.size();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int buildTeamMemberList(Connection db) throws SQLException {
    team.setProject(this);
    team.setProjectId(this.getId());
    team.buildList(db);
    return team.size();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int buildFileItemList(Connection db) throws SQLException {
    files.setLinkModuleId(Constants.DOCUMENTS_PROJECTS);
    files.setLinkItemId(this.getId());
    files.buildList(db);
    return files.size();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insert(Connection db, ActionContext context) throws SQLException {
    //TODO: Retrieve user information from the session to be used
    return insert(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid()) {
      return false;
    }

    Exception errorMessage = null;
    try {
      db.setAutoCommit(false);
      StringBuffer sql = new StringBuffer();
      sql.append("INSERT INTO projects ");
      sql.append("(group_id, department_id, owner, enteredBy, modifiedBy, template_id, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("title, shortDescription, requestedBy, ");
      sql.append("requestedDept, requestDate, approvalDate, closeDate) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?, ?, ?, ?, ?, ?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, groupId);
      DatabaseUtils.setInt(pst, ++i, departmentId);
      DatabaseUtils.setInt(pst, ++i, owner);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      DatabaseUtils.setInt(pst, ++i, templateId);
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
        pst.setDate(++i, requestDate);
      }

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
      if (closeDate == null) {
        pst.setNull(++i, java.sql.Types.DATE);
      } else {
        pst.setTimestamp(++i, closeDate);
      }

      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "projects_project_id_seq");

      if (templateId > -1) {
        //Add team, requirements and activities from an existing project
        Project previousProject = new Project(db, templateId, null);
        previousProject.buildTeamMemberList(db);
        previousProject.buildRequirementList(db);
        previousProject.buildAssignmentList(db);

        TeamMemberList prevTeam = previousProject.getTeam();
        prevTeam.setProject(this);
        prevTeam.setProjectId(this.getId());
        prevTeam.setEnteredBy(this.enteredBy);
        prevTeam.setModifiedBy(this.modifiedBy);
        prevTeam.insert(db);

        RequirementList prevRequirements = previousProject.getRequirements();
        prevRequirements.setProject(this);
        prevRequirements.setProjectId(this.getId());
        prevRequirements.setEnteredBy(this.enteredBy);
        prevRequirements.setModifiedBy(this.modifiedBy);
        //prevRequirements.offsetDates();
        prevRequirements.insert(db);

        AssignmentList prevAssignments = previousProject.getAssignments();
        prevAssignments.setProject(this);
        prevAssignments.setProjectId(this.getId());
        prevAssignments.setEnteredBy(this.enteredBy);
        prevAssignments.setModifiedBy(this.modifiedBy);
        prevAssignments.setOffsetDate(this.getRequestDate());
        prevAssignments.insert(db);
      }
      db.commit();
    } catch (Exception e) {
      errorMessage = e;
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    if (errorMessage != null) {
      throw new SQLException(errorMessage.getMessage());
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  basePath          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String basePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    buildFileItemList(db);
    files.delete(db, basePath);

    buildIssueCategoryList(db);
    issueCategories.delete(db);

    buildAssignmentList(db);
    assignments.delete(db);

    buildRequirementList(db);
    requirements.delete(db);

    buildTeamMemberList(db);
    team.delete(db);

    int recordCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM projects " +
        "WHERE project_id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
    pst.close();

    if (recordCount == 0) {
      errors.put("actionError", "Project could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    return update(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }

    if (!isValid()) {
      return -1;
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
      previousApprovalDate = rs.getTimestamp("approvalDate");
      previouslyApproved = (previousApprovalDate != null);
      previousCloseDate = rs.getTimestamp("closeDate");
      previouslyClosed = (previousCloseDate != null);
    }
    rs.close();
    pst.close();

    pst = db.prepareStatement(
        "UPDATE projects " +
        "SET department_id = ?, title = ?, shortDescription = ?, requestedBy = ?, " +
        "requestedDept = ?, requestDate = ?, " +
        "approvalDate = ?, closeDate = ?, owner = ?, " +
        "modifiedby = ?, modified = CURRENT_TIMESTAMP " +
        "WHERE project_id = ? " +
        "AND modified = ? ");
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, departmentId);
    pst.setString(++i, title);
    pst.setString(++i, shortDescription);
    pst.setString(++i, requestedBy);
    pst.setString(++i, requestedByDept);
    if (requestDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setDate(++i, requestDate);
    }

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
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    pst.setTimestamp(++i, modified);

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Gets the valid attribute of the Project object
   *
   *@return    The valid value
   */
  private boolean isValid() {

    if (title.equals("")) {
      errors.put("titleError", "Title is required");
    }

    if (shortDescription.equals("")) {
      errors.put("shortDescriptionError", "Description is required");
    }

    if (requestDate == null) {
      errors.put("requestDate", "Request date is required");
    }

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
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
    requestDate = rs.getDate("requestDate");
    String projectRequestDateString = "--";
    try {
      projectRequestDateString = requestDate.toString();
    } catch (NullPointerException e) {
    }
    approvalDate = rs.getTimestamp("approvalDate");
    approved = (approvalDate != null);
    closeDate = rs.getTimestamp("closeDate");
    closed = (closeDate != null);
    owner = DatabaseUtils.getInt(rs, "owner");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredBy");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedBy");
  }

}


