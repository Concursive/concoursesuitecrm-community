/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.reports.base;

import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A collection of Criteria objects, can be used for retrieving from the
 * database.
 *
 * @author matt rajkowski
 * @version $Id: CriteriaList.java,v 1.1.2.1 2003/09/15 20:58:21 mrajkowski
 *          Exp $
 * @created September 15, 2003
 */
public class CriteriaList extends ArrayList {

  protected PagedListInfo pagedListInfo = null;
  protected int reportId = -1;
  protected int owner = -1;
  protected int criteriaId = -1;


  /**
   * Constructor for the CategoryList object
   */
  public CriteriaList() {
  }


  /**
   * Sets the pagedListInfo attribute of the ReportList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the pagedListInfo attribute of the ReportList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the reportId attribute of the CriteriaList object
   *
   * @param tmp The new reportId value
   */
  public void setReportId(int tmp) {
    this.reportId = tmp;
  }


  /**
   * Sets the reportId attribute of the CriteriaList object
   *
   * @param tmp The new reportId value
   */
  public void setReportId(String tmp) {
    this.reportId = Integer.parseInt(tmp);
  }


  /**
   * Sets the owner attribute of the CriteriaList object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the owner attribute of the CriteriaList object
   *
   * @param tmp The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   *  Sets the criteriaId attribute of the CriteriaList object
   *
   *@param  tmp  The new criteriaId value
   */
  public void setCriteriaId(int tmp) {
    this.criteriaId = tmp;
  }


  /**
   *  Sets the criteriaId attribute of the CriteriaList object
   *
   *@param  tmp  The new criteriaId value
   */
  public void setCriteriaId(String tmp) {
    this.criteriaId = Integer.parseInt(tmp);
  }


  /**
   * Gets the reportId attribute of the CriteriaList object
   *
   * @return The reportId value
   */
  public int getReportId() {
    return reportId;
  }


  /**
   * Gets the owner attribute of the CriteriaList object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the criteriaId attribute of the CriteriaList object
   *
   *@return    The criteriaId value
   */
  public int getCriteriaId() {
    return criteriaId;
  }


  /**
   * Builds a list of criteria objects based on the properties that have been
   * set for this object.
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
        "SELECT COUNT(*) as recordcount " +
        "FROM report_criteria c " +
        "WHERE criteria_id > -1 ");
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
      //Determine column to sort by
      pagedListInfo.setDefaultSort("c.modified", "DESC");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY c.modified DESC ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "c.* " +
        "FROM report_criteria c " +
        "WHERE criteria_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      Criteria thisCriteria = new Criteria(rs);
      this.add(thisCriteria);
    }
    rs.close();
    pst.close();
  }


  /**
   * Defines additional filters for the query
   *
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (reportId != -1) {
      sqlFilter.append("AND c.report_id = ? ");
    }
    if (owner != -1) {
      sqlFilter.append("AND c.owner = ? ");
    }
    if (criteriaId != -1) {
      sqlFilter.append("AND c.criteria_id = ? ");
    }
  }


  /**
   * Sets the parameters for the additional parameters specified for this query
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (reportId != -1) {
      pst.setInt(++i, reportId);
    }
    if (owner != -1) {
      pst.setInt(++i, owner);
    }
    if (criteriaId != -1) {
      pst.setInt(++i, criteriaId);
    }
    return i;
  }


  /**
   * Deletes all of the related data for a specific criteria id
   *
   * @param db         Description of the Parameter
   * @param criteriaId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void delete(Connection db, int criteriaId) throws SQLException {
    try {
      db.setAutoCommit(false);
      //Delete the params
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM report_criteria_parameter " +
          "WHERE criteria_id = ? ");
      pst.setInt(1, criteriaId);
      pst.execute();
      pst.close();
      //Delete the criteria item
      pst = db.prepareStatement(
          "DELETE FROM report_criteria " +
          "WHERE criteria_id = ? ");
      pst.setInt(1, criteriaId);
      pst.execute();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }
}


