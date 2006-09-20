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
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.HtmlSelect;
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
 * @version $Id: RequirementList.java,v 1.1.136.1 2004/03/19 21:00:50 rvasista
 *          Exp $
 * @created December 23, 2002
 */
public class RequirementList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private Project project = null;
  private int projectId = -1;
  private boolean buildAssignments = false;
  private boolean openOnly = false;
  private boolean closedOnly = false;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  // helpers
  private int planActivityCount = -1;
  private int planClosedCount = -1;
  private int planUpcomingCount = -1;
  private int planOverdueCount = -1;


  /**
   * Constructor for the RequirementList object
   */
  public RequirementList() {
  }


  /**
   * Sets the pagedListInfo attribute of the RequirementList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the emptyHtmlSelectRecord attribute of the RequirementList object
   *
   * @param tmp The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   * Sets the projectId attribute of the RequirementList object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   * Sets the project attribute of the RequirementList object
   *
   * @param tmp The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   * Sets the buildAssignments attribute of the RequirementList object
   *
   * @param tmp The new buildAssignments value
   */
  public void setBuildAssignments(boolean tmp) {
    this.buildAssignments = tmp;
  }


  /**
   * Sets the enteredBy attribute of the RequirementList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the RequirementList object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the openOnly attribute of the RequirementList object
   *
   * @param tmp The new openOnly value
   */
  public void setOpenOnly(boolean tmp) {
    this.openOnly = tmp;
  }


  /**
   * Sets the closedOnly attribute of the RequirementList object
   *
   * @param tmp The new closedOnly value
   */
  public void setClosedOnly(boolean tmp) {
    this.closedOnly = tmp;
  }


  /**
   * Gets the htmlSelect attribute of the RequirementList object
   *
   * @param selectName Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   * Gets the planActivityCount attribute of the RequirementList object
   *
   * @return The planActivityCount value
   */
  public int getPlanActivityCount() {
    return planActivityCount;
  }


  /**
   * Sets the planActivityCount attribute of the RequirementList object
   *
   * @param planActivityCount The new planActivityCount value
   */
  public void setPlanActivityCount(int planActivityCount) {
    this.planActivityCount = planActivityCount;
  }


  /**
   * Gets the planClosedCount attribute of the RequirementList object
   *
   * @return The planClosedCount value
   */
  public int getPlanClosedCount() {
    return planClosedCount;
  }


  /**
   * Sets the planClosedCount attribute of the RequirementList object
   *
   * @param planClosedCount The new planClosedCount value
   */
  public void setPlanClosedCount(int planClosedCount) {
    this.planClosedCount = planClosedCount;
  }


  /**
   * Gets the planUpcomingCount attribute of the RequirementList object
   *
   * @return The planUpcomingCount value
   */
  public int getPlanUpcomingCount() {
    return planUpcomingCount;
  }


  /**
   * Sets the planUpcomingCount attribute of the RequirementList object
   *
   * @param planUpcomingCount The new planUpcomingCount value
   */
  public void setPlanUpcomingCount(int planUpcomingCount) {
    this.planUpcomingCount = planUpcomingCount;
  }


  /**
   * Gets the planOverdueCount attribute of the RequirementList object
   *
   * @return The planOverdueCount value
   */
  public int getPlanOverdueCount() {
    return planOverdueCount;
  }


  /**
   * Sets the planOverdueCount attribute of the RequirementList object
   *
   * @param planOverdueCount The new planOverdueCount value
   */
  public void setPlanOverdueCount(int planOverdueCount) {
    this.planOverdueCount = planOverdueCount;
  }


  /**
   * Gets the htmlSelect attribute of the RequirementList object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect listSelect = new HtmlSelect();
    if (emptyHtmlSelectRecord != null) {
      listSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Requirement thisRequirement = (Requirement) i.next();
      listSelect.addItem(
          thisRequirement.getId(),
          StringUtils.toHtml(thisRequirement.getShortDescription()));
    }
    return listSelect.getHtml(selectName, defaultKey);
  }


  /**
   * Gets the project attribute of the RequirementList object
   *
   * @return The project value
   */
  public Project getProject() {
    return project;
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
        "FROM project_requirements r " +
        "WHERE r.requirement_id > -1 ");

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
          "AND " + DatabaseUtils.toLowerCase(db) + "(shortDescription) < ? ");
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
    pagedListInfo.setDefaultSort("startdate,r.shortdescription", null);
    pagedListInfo.appendSqlTail(db, sqlOrder);

    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "r.*, loe_e.description as loe_estimated_type, loe_a.description as loe_actual_type " +
        "FROM project_requirements r " +
        " LEFT JOIN lookup_project_loe loe_e ON (r.estimated_loetype = loe_e.code) " +
        " LEFT JOIN lookup_project_loe loe_a ON (r.actual_loetype = loe_a.code) " +
        "WHERE r.requirement_id > -1 ");

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
      Requirement thisRequirement = new Requirement(rs);
      thisRequirement.setProject(project);
      this.add(thisRequirement);
    }
    rs.close();
    pst.close();

    if (buildAssignments) {
      Iterator requirements = this.iterator();
      while (requirements.hasNext()) {
        Requirement thisRequirement = (Requirement) requirements.next();
        thisRequirement.buildAssignmentList(db);
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
    if (projectId > -1) {
      sqlFilter.append("AND project_id = ? ");
    }
    if (openOnly) {
      sqlFilter.append("AND closedate IS NULL ");
    }
    if (closedOnly) {
      sqlFilter.append("AND closedate IS NOT NULL ");
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
    if (projectId > -1) {
      pst.setInt(++i, projectId);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    Iterator requirements = this.iterator();
    while (requirements.hasNext()) {
      Requirement thisRequirement = (Requirement) requirements.next();
      thisRequirement.setProject(project);
      thisRequirement.setProjectId(projectId);
      thisRequirement.setEnteredBy(enteredBy);
      thisRequirement.setModifiedBy(modifiedBy);
      thisRequirement.setApproved(false);
      thisRequirement.setClosed(false);
      thisRequirement.insert(db);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator requirements = this.iterator();
    while (requirements.hasNext()) {
      Requirement thisRequirement = (Requirement) requirements.next();
      thisRequirement.delete(db);
    }
  }


  /**
   * Gets the percentClosed attribute of the RequirementList object
   *
   * @return The percentClosed value
   */
  public int getPercentClosed() {
    if (planActivityCount == 0 || planClosedCount == planActivityCount) {
      return 100;
    }
    return (int) Math.round(
        ((double) planClosedCount / (double) planActivityCount) * 100.0);
  }


  /**
   * Gets the percentUpcoming attribute of the RequirementList object
   *
   * @return The percentUpcoming value
   */
  public int getPercentUpcoming() {
    if (planActivityCount == 0 || planUpcomingCount == 0) {
      return 0;
    }
    return (int) Math.round(
        ((double) planUpcomingCount / (double) planActivityCount) * 100.0);
  }


  /**
   * Gets the percentOverdue attribute of the RequirementList object
   *
   * @return The percentOverdue value
   */
  public int getPercentOverdue() {
    if (planActivityCount == 0 || planOverdueCount == 0) {
      return 0;
    }
    return (int) Math.round(
        ((double) planOverdueCount / (double) planActivityCount) * 100.0);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildPlanActivityCounts(Connection db) throws SQLException {
    planActivityCount = 0;
    planClosedCount = 0;
    planUpcomingCount = 0;
    planOverdueCount = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Requirement thisRequirement = (Requirement) i.next();
      thisRequirement.buildPlanActivityCounts(db);
      planActivityCount += thisRequirement.getPlanActivityCount();
      planClosedCount += thisRequirement.getPlanClosedCount();
      planUpcomingCount += thisRequirement.getPlanUpcomingCount();
      planOverdueCount += thisRequirement.getPlanOverdueCount();
    }
  }
}

