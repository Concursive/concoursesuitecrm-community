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
import java.util.Calendar;
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
 *@created    January 3, 2003
 *@version    $Id: AssignmentList.java,v 1.4.50.1 2004/03/19 21:00:50 rvasista
 *      Exp $
 */
public class AssignmentList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private int assignmentsForUser = -1;
  private int forProjectUser = -1;
  private Project project = null;
  private int projectId = -1;
  private boolean incompleteOnly = false;
  private boolean closedOnly = false;
  private int withDaysComplete = -1;
  private int requirementId = -1;
  private int folderId = -1;
  private boolean onlyIfRequirementOpen = false;

  private int enteredBy = -1;
  private int modifiedBy = -1;

  private java.sql.Date offsetDate = null;
  //calendar
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;


  /**
   *  Constructor for the AssignmentList object
   */
  public AssignmentList() { }


  /**
   *  Sets the pagedListInfo attribute of the AssignmentList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the pagedListInfo attribute of the AssignmentList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the emptyHtmlSelectRecord attribute of the AssignmentList object
   *
   *@param  tmp  The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   *  Sets the project attribute of the AssignmentList object
   *
   *@param  tmp  The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   *  Sets the projectId attribute of the AssignmentList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the assignmentsForUser attribute of the AssignmentList object
   *
   *@param  tmp  The new assignmentsForUser value
   */
  public void setAssignmentsForUser(int tmp) {
    this.assignmentsForUser = tmp;
  }


  /**
   *  Sets the forProjectUser attribute of the AssignmentList object
   *
   *@param  tmp  The new forProjectUser value
   */
  public void setForProjectUser(int tmp) {
    this.forProjectUser = tmp;
  }


  /**
   *  Sets the forProjectUser attribute of the AssignmentList object
   *
   *@param  tmp  The new forProjectUser value
   */
  public void setForProjectUser(String tmp) {
    this.forProjectUser = Integer.parseInt(tmp);
  }


  /**
   *  Sets the incompleteOnly attribute of the AssignmentList object
   *
   *@param  tmp  The new incompleteOnly value
   */
  public void setIncompleteOnly(boolean tmp) {
    this.incompleteOnly = tmp;
  }


  /**
   *  Sets the closedOnly attribute of the AssignmentList object
   *
   *@param  tmp  The new closedOnly value
   */
  public void setClosedOnly(boolean tmp) {
    this.closedOnly = tmp;
  }


  /**
   *  Sets the withDaysComplete attribute of the AssignmentList object
   *
   *@param  tmp  The new withDaysComplete value
   */
  public void setWithDaysComplete(int tmp) {
    this.withDaysComplete = tmp;
  }


  /**
   *  Sets the requirementId attribute of the AssignmentList object
   *
   *@param  tmp  The new requirementId value
   */
  public void setRequirementId(int tmp) {
    this.requirementId = tmp;
  }


  /**
   *  Gets the onlyIfRequirementOpen attribute of the AssignmentList object
   *
   *@return    The onlyIfRequirementOpen value
   */
  public boolean getOnlyIfRequirementOpen() {
    return onlyIfRequirementOpen;
  }


  /**
   *  Sets the onlyIfRequirementOpen attribute of the AssignmentList object
   *
   *@param  tmp  The new onlyIfRequirementOpen value
   */
  public void setOnlyIfRequirementOpen(boolean tmp) {
    this.onlyIfRequirementOpen = tmp;
  }


  /**
   *  Sets the onlyIfRequirementOpen attribute of the AssignmentList object
   *
   *@param  tmp  The new onlyIfRequirementOpen value
   */
  public void setOnlyIfRequirementOpen(String tmp) {
    this.onlyIfRequirementOpen = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the folderId attribute of the AssignmentList object
   *
   *@param  tmp  The new folderId value
   */
  public void setFolderId(int tmp) {
    this.folderId = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the AssignmentList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the AssignmentList object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the offsetDate attribute of the AssignmentList object
   *
   *@param  tmp  The new offsetDate value
   */
  public void setOffsetDate(java.sql.Date tmp) {
    this.offsetDate = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the AssignmentList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   *  Sets the alertRangeEnd attribute of the AssignmentList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   *  Gets the htmlSelect attribute of the AssignmentList object
   *
   *@param  selectName  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the htmlSelect attribute of the AssignmentList object
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
      Assignment thisAssignment = (Assignment) i.next();
      listSelect.addItem(
          thisAssignment.getId(),
          thisAssignment.getRole());
    }
    return listSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Gets the project attribute of the AssignmentList object
   *
   *@return    The project value
   */
  public Project getProject() {
    return project;
  }


  /**
   *  Gets the closedOnly attribute of the AssignmentList object
   *
   *@return    The closedOnly value
   */
  public boolean getClosedOnly() {
    return closedOnly;
  }


  /**
   *  Gets the incompleteOnly attribute of the AssignmentList object
   *
   *@return    The incompleteOnly value
   */
  public boolean getIncompleteOnly() {
    return incompleteOnly;
  }


  /**
   *  Gets the requirementId attribute of the AssignmentList object
   *
   *@return    The requirementId value
   */
  public int getRequirementId() {
    return requirementId;
  }


  /**
   *  Gets the folderId attribute of the AssignmentList object
   *
   *@return    The folderId value
   */
  public int getFolderId() {
    return folderId;
  }


  /**
   *  Gets the notStartedCount attribute of the AssignmentList object
   *
   *@return    The notStartedCount value
   */
  public int getNotStartedCount() {
    int count = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Assignment thisItem = (Assignment) i.next();
      if (thisItem.getStatusType() == thisItem.NOTSTARTED) {
        ++count;
      }
    }
    return count;
  }


  /**
   *  Gets the onHoldCount attribute of the AssignmentList object
   *
   *@return    The onHoldCount value
   */
  public int getOnHoldCount() {
    int count = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Assignment thisItem = (Assignment) i.next();
      if (thisItem.getStatusType() == thisItem.ONHOLD) {
        ++count;
      }
    }
    return count;
  }


  /**
   *  Gets the inProgressCount attribute of the AssignmentList object
   *
   *@return    The inProgressCount value
   */
  public int getInProgressCount() {
    int count = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Assignment thisItem = (Assignment) i.next();
      if (thisItem.getStatusType() == thisItem.INPROGRESS) {
        ++count;
      }
    }
    return count;
  }


  /**
   *  Gets the completeCount attribute of the AssignmentList object
   *
   *@return    The completeCount value
   */
  public int getCompleteCount() {
    int count = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Assignment thisItem = (Assignment) i.next();
      if (thisItem.getStatusType() == thisItem.COMPLETE) {
        ++count;
      }
    }
    return count;
  }


  /**
   *  Gets the closedCount attribute of the AssignmentList object
   *
   *@return    The closedCount value
   */
  public int getClosedCount() {
    int count = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Assignment thisItem = (Assignment) i.next();
      if (thisItem.getStatusType() == thisItem.CLOSED) {
        ++count;
      }
    }
    return count;
  }


  /**
   *  Gets the alertRangeStart attribute of the AssignmentList object
   *
   *@return    The alertRangeStart value
   */
  public java.sql.Timestamp getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   *  Gets the alertRangeEnd attribute of the AssignmentList object
   *
   *@return    The alertRangeEnd value
   */
  public java.sql.Timestamp getAlertRangeEnd() {
    return alertRangeEnd;
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
        "FROM project_assignments a " +
        "WHERE assignment_id > -1 ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
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
            "AND lower(activity) < ? ");
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
      pagedListInfo.setDefaultSort("due_date, assign_date, assignment_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY due_date, assign_date, assignment_id ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "a.*, s.description as status, s.type as status_type, s.graphic as status_graphic, " +
        "loe_e.description as loe_estimated_type, loe_a.description as loe_actual_type " +
        "FROM project_assignments a " +
        " LEFT JOIN lookup_project_status s ON (a.status_id = s.code) " +
        " LEFT JOIN lookup_project_loe loe_e ON (a.estimated_loetype = loe_e.code) " +
        " LEFT JOIN lookup_project_loe loe_a ON (a.actual_loetype = loe_a.code) " +
        "WHERE assignment_id > -1 ");
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
      Assignment thisAssignment = new Assignment(rs);
      thisAssignment.setProject(project);
      this.add(thisAssignment);
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
    if (assignmentsForUser > -1) {
      sqlFilter.append("AND user_assign_id = ? ");
    }
    if (projectId > -1) {
      sqlFilter.append("AND a.project_id = ? ");
    }
    if (forProjectUser > -1) {
      sqlFilter.append("AND (a.project_id in (SELECT DISTINCT project_id FROM project_team WHERE user_id = ? " +
          "AND status IS NULL )) ");
    }
    if (closedOnly) {
      sqlFilter.append("AND complete_date IS NOT NULL ");
    }
    if (incompleteOnly && withDaysComplete > -1) {
      sqlFilter.append("AND (complete_date IS NULL OR complete_date > ?) ");
    } else {
      if (incompleteOnly) {
        sqlFilter.append("AND complete_date IS NULL ");
      }
      if (withDaysComplete > -1) {
        sqlFilter.append("AND complete_date > ? ");
      }
    }
    if (requirementId > -1) {
      sqlFilter.append("AND a.requirement_id = ? ");
    }
    if (folderId > -1) {
      if (folderId == 0) {
        sqlFilter.append("AND folder_id IS NULL ");
      } else {
        sqlFilter.append("AND folder_id = ? ");
      }
    }
    if (alertRangeStart != null) {
      sqlFilter.append("AND a.due_date >= ? ");
    }
    if (alertRangeEnd != null) {
      sqlFilter.append("AND a.due_date < ? ");
    }
    if (onlyIfRequirementOpen) {
      sqlFilter.append("AND a.requirement_id IN (SELECT requirement_id FROM project_requirements WHERE closedate IS NULL) ");
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
    if (assignmentsForUser > -1) {
      pst.setInt(++i, assignmentsForUser);
    }
    if (projectId > -1) {
      pst.setInt(++i, projectId);
    }
    if (forProjectUser > -1) {
      pst.setInt(++i, forProjectUser);
    }
    if (incompleteOnly && withDaysComplete > -1) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DATE, -withDaysComplete);
      pst.setTimestamp(++i, new java.sql.Timestamp(cal.getTimeInMillis()));
    } else {
      if (withDaysComplete > -1) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -withDaysComplete);
        pst.setTimestamp(++i, new java.sql.Timestamp(cal.getTimeInMillis()));
      }
    }
    if (requirementId > -1) {
      pst.setInt(++i, requirementId);
    }
    if (folderId > 0) {
      //0 is the null case here
      pst.setInt(++i, folderId);
    }
    if (alertRangeStart != null) {
      pst.setTimestamp(++i, alertRangeStart);
    }
    if (alertRangeEnd != null) {
      pst.setTimestamp(++i, alertRangeEnd);
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
  public boolean insert(Connection db) throws SQLException {
    java.sql.Timestamp lowestDueDate = null;
    java.sql.Timestamp lowestStartDate = null;
    Iterator assignments = this.iterator();
    while (assignments.hasNext()) {
      Assignment thisAssignment = (Assignment) assignments.next();
      java.sql.Timestamp thisDueDate = thisAssignment.getDueDate();
      if ((lowestDueDate == null) || (lowestDueDate != null && thisDueDate != null && thisDueDate.before(lowestDueDate))) {
        lowestDueDate = thisDueDate;
      }
      java.sql.Timestamp thisStartDate = thisAssignment.getEstStartDate();
      if ((lowestStartDate == null) || (lowestStartDate != null && thisStartDate != null && thisStartDate.before(lowestStartDate))) {
        lowestStartDate = thisStartDate;
      }
    }
    int age = 0;
    if (lowestStartDate != null) {
      float ageCheck = ((offsetDate.getTime() - lowestStartDate.getTime()) / 86400000);
      age = java.lang.Math.round(ageCheck);
    }
    if (age == 0 && lowestDueDate != null) {
      float ageCheck = ((offsetDate.getTime() - lowestDueDate.getTime()) / 86400000);
      age = java.lang.Math.round(ageCheck);
    }

    assignments = this.iterator();
    while (assignments.hasNext()) {
      Assignment thisAssignment = (Assignment) assignments.next();
      thisAssignment.setProject(project);
      thisAssignment.setProjectId(projectId);
      thisAssignment.setEnteredBy(enteredBy);
      thisAssignment.setModifiedBy(modifiedBy);
      thisAssignment.setAssignedBy(enteredBy);
      if (thisAssignment.getEstStartDate() != null) {
        Calendar dateTest = Calendar.getInstance();
        dateTest.setTime(thisAssignment.getEstStartDate());
        dateTest.set(Calendar.HOUR_OF_DAY, 0);
        dateTest.set(Calendar.MINUTE, 0);
        dateTest.add(Calendar.DATE, age);
        //thisAssignment.setEstStartDate(new java.sql.Timestamp(dateTest.getTimeInMillis()));
        java.util.Date newDate = dateTest.getTime();
        thisAssignment.setEstStartDate(new java.sql.Timestamp(newDate.getTime()));
      }
      if (thisAssignment.getDueDate() != null) {
        Calendar dateTest = Calendar.getInstance();
        dateTest.setTime(thisAssignment.getDueDate());
        dateTest.set(Calendar.HOUR_OF_DAY, 0);
        dateTest.set(Calendar.MINUTE, 0);
        dateTest.add(Calendar.DATE, age);
        //thisAssignment.setDueDate(new java.sql.Timestamp(dateTest.getTimeInMillis()));
        java.util.Date newDate = dateTest.getTime();
        thisAssignment.setDueDate(new java.sql.Timestamp(newDate.getTime()));
      }
      thisAssignment.setAssignDate((String) null);
      thisAssignment.setStartDate((String) null);
      thisAssignment.setStatusId(Assignment.NOTSTARTED);
      //thisAssignment.setStatusTypeId(Assignment.NOTSTARTED);
      //thisAssignment.setStatus("");
      //thisAssignment.setStatusDate(null);
      //thisAssignment.setStatusType(-1);
      thisAssignment.setCompleteDate((String) null);

      thisAssignment.insert(db);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator assignments = this.iterator();
    while (assignments.hasNext()) {
      Assignment thisAssignment = (Assignment) assignments.next();
      thisAssignment.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  requirementId     Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void delete(Connection db, int requirementId) throws SQLException {
    //Assignment status
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM project_assignments_status " +
        "WHERE assignment_id IN " +
        "(SELECT assignment_id FROM project_assignments WHERE requirement_id = ?) ");
    pst.setInt(1, requirementId);
    pst.execute();
    pst.close();
    //Delete the assignments
    pst = db.prepareStatement(
        "DELETE FROM project_assignments " +
        "WHERE requirement_id = ? ");
    pst.setInt(1, requirementId);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  newOwner          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int reassignElements(Connection db, int newOwner) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Assignment thisAssignment = (Assignment) i.next();
      if (thisAssignment.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }


  /**
   *  Gets the assignment attribute of the AssignmentList object
   *
   *@param  id  Description of the Parameter
   *@return     The assignment value
   */
  public Assignment getAssignment(int id) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Assignment thisAssignment = (Assignment) i.next();
      if (thisAssignment.getId() == id) {
        return thisAssignment;
      }
    }
    return null;
  }
}

