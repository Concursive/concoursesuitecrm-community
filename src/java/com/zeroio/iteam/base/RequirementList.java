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
 *@created    December 23, 2002
 *@version    $Id$
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


  /**
   *  Constructor for the RequirementList object
   */
  public RequirementList() { }


  /**
   *  Sets the pagedListInfo attribute of the RequirementList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the emptyHtmlSelectRecord attribute of the RequirementList object
   *
   *@param  tmp  The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   *  Sets the projectId attribute of the RequirementList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the project attribute of the RequirementList object
   *
   *@param  tmp  The new project value
   */
  public void setProject(Project tmp) {
    this.project = tmp;
  }


  /**
   *  Sets the buildAssignments attribute of the RequirementList object
   *
   *@param  tmp  The new buildAssignments value
   */
  public void setBuildAssignments(boolean tmp) {
    this.buildAssignments = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the RequirementList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the RequirementList object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the openOnly attribute of the RequirementList object
   *
   *@param  tmp  The new openOnly value
   */
  public void setOpenOnly(boolean tmp) {
    this.openOnly = tmp;
  }


  /**
   *  Sets the closedOnly attribute of the RequirementList object
   *
   *@param  tmp  The new closedOnly value
   */
  public void setClosedOnly(boolean tmp) {
    this.closedOnly = tmp;
  }


  /**
   *  Gets the htmlSelect attribute of the RequirementList object
   *
   *@param  selectName  Description of the Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the htmlSelect attribute of the RequirementList object
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
      Requirement thisRequirement = (Requirement) i.next();
      listSelect.addItem(
          thisRequirement.getId(),
          thisRequirement.getShortDescription());
    }
    return listSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Gets the project attribute of the RequirementList object
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
    pst.close();
    rs.close();

    //Determine the offset, based on the filter, for the first record to show
    if (!pagedListInfo.getCurrentLetter().equals("")) {
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString() +
          "AND shortDescription < ? ");
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
        "r.*, loe_e.description as loe_estimated_type, loe_a.description as loe_actual_type " +
        "FROM project_requirements r " +
        " LEFT JOIN lookup_project_loe loe_e ON (r.estimated_loetype = loe_e.code) " +
        " LEFT JOIN lookup_project_loe loe_a ON (r.actual_loetype = loe_a.code) " +
        "WHERE r.requirement_id > -1 ");

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
    if (openOnly) {
      sqlFilter.append("AND closedate IS NULL ");
    }
    if (closedOnly) {
      sqlFilter.append("AND closedate IS NOT NULL ");
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator requirements = this.iterator();
    while (requirements.hasNext()) {
      Requirement thisRequirement = (Requirement) requirements.next();
      thisRequirement.delete(db);
    }
  }
}

