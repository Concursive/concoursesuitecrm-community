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
package org.aspcfs.modules.troubletickets.base;

import org.aspcfs.modules.base.Constants;
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
 * @author partha
 * @version $Id: TicketCategoryDraftAssignmentList.java,v 1.1.2.1 2005/11/14
 *          21:15:37 partha Exp $
 * @created November 10, 2005
 */
public class TicketCategoryDraftAssignmentList extends ArrayList {
  PagedListInfo pagedListInfo = null;
  protected int id = -1;
  protected int categoryId = -1;
  protected int departmentId = -1;
  protected int assignedTo = -1;
  protected int userGroupId = -1;
  protected boolean buildPlanMapList = false;
  protected int siteId = -1;
  protected boolean checkSite = false;
  protected boolean exclusiveToSite = false;

  public final static String tableName = "ticket_category_draft_assignment";
  public final static String uniqueField = "map_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  /**
   * Constructor for the TicketCategoryDraftAssignmentList object
   */
  public TicketCategoryDraftAssignmentList() {
  }

  /**
   * Sets the lastAnchor attribute of the TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Gets the tableName attribute of the TicketCategoryDraftAssignmentList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the TicketCategoryDraftAssignmentList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
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
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
            " FROM " + DatabaseUtils.getTableName(db, "ticket_category_draft_assignment") + " tcda " +
            " LEFT JOIN user_group ug ON (tcda.group_id = ug.group_id) " +
            " WHERE tcda.map_id > -1 ");
    createFilter(sqlFilter, db);
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
      pagedListInfo.setDefaultSort("tcda.map_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY tcda.map_id ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " tcda.* , ug.group_name " +
            " FROM " + DatabaseUtils.getTableName(db, "ticket_category_draft_assignment") + " tcda " +
            " LEFT JOIN user_group ug ON (tcda.group_id = ug.group_id) " +
            " WHERE tcda.map_id > -1 ");

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
      TicketCategoryDraftAssignment thisMap = new TicketCategoryDraftAssignment(rs);
      this.add(thisMap);
    }
    rs.close();
    pst.close();
    if (buildPlanMapList) {
      Iterator iter = (Iterator) this.iterator();
      while (iter.hasNext()) {
        TicketCategoryDraftAssignment assignment = (TicketCategoryDraftAssignment) iter.next();
        assignment.buildPlanMapList(db);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter, Connection db) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (id > -1) {
      sqlFilter.append("AND tcda.map_id = ? ");
    }
    if (categoryId > -1) {
      sqlFilter.append("AND tcda.category_id = ? ");
    }
    if (departmentId > -1) {
      if (departmentId == 0) {
        sqlFilter.append("AND tcda.department_id IS NULL ");
      } else {
        sqlFilter.append("AND tcda.department_id = ? ");
      }
    }
    if (assignedTo > -1) {
      sqlFilter.append("AND tcda.assigned_to = ? ");
    }
    if (userGroupId > -1) {
      sqlFilter.append("AND tcda.group_id = ? ");
    }
    if (checkSite) {
      if (siteId > -1) {
        sqlFilter.append("AND tcda.category_id IN (SELECT id from ticket_category_draft tcd WHERE tcd.site_id = ? ");
        if (!exclusiveToSite) {
          sqlFilter.append("AND tcd.site_id IS NULL ");
        }
        sqlFilter.append(") ");
      } else {
        sqlFilter.append("AND tcda.category_id IN (SELECT id from ticket_category_draft tcd WHERE tcd.site_id IS NULL) ");
      }
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    if (departmentId > 0) {
      pst.setInt(++i, departmentId);
    }
    if (assignedTo > -1) {
      pst.setInt(++i, assignedTo);
    }
    if (userGroupId > -1) {
      pst.setInt(++i, userGroupId);
    }
    if (checkSite && siteId > -1) {
      pst.setInt(++i, siteId);
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    return i;
  }


  /*
   *  Get and Set methods
   */
  /**
   * Gets the pagedListInfo attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the id attribute of the TicketCategoryDraftAssignmentList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the categoryId attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the categoryId attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Gets the departmentId attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @return The departmentId value
   */
  public int getDepartmentId() {
    return departmentId;
  }


  /**
   * Sets the departmentId attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @param tmp The new departmentId value
   */
  public void setDepartmentId(int tmp) {
    this.departmentId = tmp;
  }


  /**
   * Sets the departmentId attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @param tmp The new departmentId value
   */
  public void setDepartmentId(String tmp) {
    this.departmentId = Integer.parseInt(tmp);
  }


  /**
   * Gets the assignedTo attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @return The assignedTo value
   */
  public int getAssignedTo() {
    return assignedTo;
  }


  /**
   * Sets the assignedTo attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @param tmp The new assignedTo value
   */
  public void setAssignedTo(int tmp) {
    this.assignedTo = tmp;
  }


  /**
   * Sets the assignedTo attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @param tmp The new assignedTo value
   */
  public void setAssignedTo(String tmp) {
    this.assignedTo = Integer.parseInt(tmp);
  }


  /**
   * Gets the userGroupId attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @return The userGroupId value
   */
  public int getUserGroupId() {
    return userGroupId;
  }


  /**
   * Sets the userGroupId attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @param tmp The new userGroupId value
   */
  public void setUserGroupId(int tmp) {
    this.userGroupId = tmp;
  }


  /**
   * Sets the userGroupId attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @param tmp The new userGroupId value
   */
  public void setUserGroupId(String tmp) {
    this.userGroupId = Integer.parseInt(tmp);
  }


  /**
   * Gets the buildPlanMapList attribute of the
   * TicketCategoryDraftAssignmentList object
   *
   * @return The buildPlanMapList value
   */
  public boolean getBuildPlanMapList() {
    return buildPlanMapList;
  }


  /**
   * Sets the buildPlanMapList attribute of the
   * TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new buildPlanMapList value
   */
  public void setBuildPlanMapList(boolean tmp) {
    this.buildPlanMapList = tmp;
  }


  /**
   * Sets the buildPlanMapList attribute of the
   * TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new buildPlanMapList value
   */
  public void setBuildPlanMapList(String tmp) {
    this.buildPlanMapList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the siteId attribute of the TicketCategoryDraftAssignmentList object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   * Sets the siteId attribute of the TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   * Sets the siteId attribute of the TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the checkSite attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @return The checkSite value
   */
  public boolean getCheckSite() {
    return checkSite;
  }


  /**
   * Sets the checkSite attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @param tmp The new checkSite value
   */
  public void setCheckSite(boolean tmp) {
    this.checkSite = tmp;
  }


  /**
   * Sets the checkSite attribute of the TicketCategoryDraftAssignmentList
   * object
   *
   * @param tmp The new checkSite value
   */
  public void setCheckSite(String tmp) {
    this.checkSite = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the exclusiveToSite attribute of the
   * TicketCategoryDraftAssignmentList object
   *
   * @return The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   * Sets the exclusiveToSite attribute of the
   * TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   * Sets the exclusiveToSite attribute of the
   * TicketCategoryDraftAssignmentList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }

}


