/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.actionplans.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Ananth
 * @version $Id: ActionPlanWorkList.java,v 1.1.2.6 2005/08/29 14:45:22 partha
 *          Exp $
 * @created August 17, 2005
 */
public class ActionPlanWorkList extends ArrayList  implements SyncableList {
  private PagedListInfo pagedListInfo = null;
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int owner = -1;
  private int manager = -1;
  private int currentStepOwner = -1;
  private int viewpoint = -1;
  private int actionPlanId = -1;
  private int enabled = Constants.UNDEFINED;
  private int hasCurrentPhase = Constants.UNDEFINED;
  private String ownerRange = null;
  private int opportunityId = -1;
  private int userGroupId = -1;
  private int siteId = -1;
  private boolean exclusiveToSite = false;
  private boolean includeAllSites = false;
  //resources
  private boolean buildPhaseWork = false;
  private boolean buildStepWork = false;
  private boolean buildLinkedObject = false;
  private boolean allMyPlans = false;
  private boolean isTicket = false;
  private boolean buildCurrentPhaseOnly = false;
  private boolean buildCurrentStepOnly = false;

  public final static String tableName = "action_plan_work";
  public final static String uniqueField = "plan_work_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  /**
   * Sets the lastAnchor attribute of the ActionPlanWorkList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionPlanWorkList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionPlanWorkList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionPlanWorkList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionPlanWorkList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  
  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }

  /**
   * Gets the tableName attribute of the ActionPlanWorkList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionPlanWorkList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the userGroupId attribute of the ActionPlanWorkList object
   *
   * @return The userGroupId value
   */
  public int getUserGroupId() {
    return userGroupId;
  }


  /**
   * Sets the userGroupId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new userGroupId value
   */
  public void setUserGroupId(int tmp) {
    this.userGroupId = tmp;
  }


  /**
   * Sets the userGroupId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new userGroupId value
   */
  public void setUserGroupId(String tmp) {
    this.userGroupId = Integer.parseInt(tmp);
  }


  /**
   * Gets the allMyPlans attribute of the ActionPlanWorkList object
   *
   * @return The allMyPlans value
   */
  public boolean getAllMyPlans() {
    return allMyPlans;
  }


  /**
   * Sets the allMyPlans attribute of the ActionPlanWorkList object
   *
   * @param tmp The new allMyPlans value
   */
  public void setAllMyPlans(boolean tmp) {
    this.allMyPlans = tmp;
  }


  /**
   * Sets the allMyPlans attribute of the ActionPlanWorkList object
   *
   * @param tmp The new allMyPlans value
   */
  public void setAllMyPlans(String tmp) {
    this.allMyPlans = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the opportunityId attribute of the ActionPlanWorkList object
   *
   * @return The opportunityId value
   */
  public int getOpportunityId() {
    return opportunityId;
  }


  /**
   * Sets the opportunityId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new opportunityId value
   */
  public void setOpportunityId(int tmp) {
    this.opportunityId = tmp;
  }


  /**
   * Sets the opportunityId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new opportunityId value
   */
  public void setOpportunityId(String tmp) {
    this.opportunityId = Integer.parseInt(tmp);
  }


  /**
   * Gets the ownerRange attribute of the ActionPlanWorkList object
   *
   * @return The ownerRange value
   */
  public String getOwnerRange() {
    return ownerRange;
  }


  /**
   * Sets the ownerRange attribute of the ActionPlanWorkList object
   *
   * @param tmp The new ownerRange value
   */
  public void setOwnerRange(String tmp) {
    this.ownerRange = tmp;
  }


  /**
   * Gets the hasCurrentPhase attribute of the ActionPlanWorkList object
   *
   * @return The hasCurrentPhase value
   */
  public int getHasCurrentPhase() {
    return hasCurrentPhase;
  }


  /**
   * Sets the hasCurrentPhase attribute of the ActionPlanWorkList object
   *
   * @param tmp The new hasCurrentPhase value
   */
  public void setHasCurrentPhase(int tmp) {
    this.hasCurrentPhase = tmp;
  }


  /**
   * Sets the hasCurrentPhase attribute of the ActionPlanWorkList object
   *
   * @param tmp The new hasCurrentPhase value
   */
  public void setHasCurrentPhase(String tmp) {
    this.hasCurrentPhase = Integer.parseInt(tmp);
  }


  /**
   * Gets the currentStepOwner attribute of the ActionPlanWorkList object
   *
   * @return The currentStepOwner value
   */
  public int getCurrentStepOwner() {
    return currentStepOwner;
  }


  /**
   * Sets the currentStepOwner attribute of the ActionPlanWorkList object
   *
   * @param tmp The new currentStepOwner value
   */
  public void setCurrentStepOwner(int tmp) {
    this.currentStepOwner = tmp;
  }


  /**
   * Sets the currentStepOwner attribute of the ActionPlanWorkList object
   *
   * @param tmp The new currentStepOwner value
   */
  public void setCurrentStepOwner(String tmp) {
    this.currentStepOwner = Integer.parseInt(tmp);
  }


  /**
   * Gets the viewpoint attribute of the ActionPlanWorkList object
   *
   * @return The viewpoint value
   */
  public int getViewpoint() {
    return viewpoint;
  }


  /**
   * Sets the viewpoint attribute of the ActionPlanWorkList object
   *
   * @param viewpoint The new viewpoint value
   */
  public void setViewpoint(int viewpoint) {
    this.viewpoint = viewpoint;
  }


  /**
   * Gets the linkItemId attribute of the ActionPlanWorkList object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Sets the linkItemId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Sets the linkItemId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   * Gets the manager attribute of the ActionPlanWorkList object
   *
   * @return The manager value
   */
  public int getManager() {
    return manager;
  }


  /**
   * Sets the manager attribute of the ActionPlanWorkList object
   *
   * @param tmp The new manager value
   */
  public void setManager(int tmp) {
    this.manager = tmp;
  }


  /**
   * Sets the manager attribute of the ActionPlanWorkList object
   *
   * @param tmp The new manager value
   */
  public void setManager(String tmp) {
    this.manager = Integer.parseInt(tmp);
  }


  /**
   * Gets the enabled attribute of the ActionPlanWorkList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Sets the enabled attribute of the ActionPlanWorkList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ActionPlanWorkList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Gets the buildLinkedObject attribute of the ActionPlanWorkList object
   *
   * @return The buildLinkedObject value
   */
  public boolean getBuildLinkedObject() {
    return buildLinkedObject;
  }


  /**
   * Sets the buildLinkedObject attribute of the ActionPlanWorkList object
   *
   * @param tmp The new buildLinkedObject value
   */
  public void setBuildLinkedObject(boolean tmp) {
    this.buildLinkedObject = tmp;
  }


  /**
   * Sets the buildLinkedObject attribute of the ActionPlanWorkList object
   *
   * @param tmp The new buildLinkedObject value
   */
  public void setBuildLinkedObject(String tmp) {
    this.buildLinkedObject = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildPhaseWork attribute of the ActionPlanWorkList object
   *
   * @return The buildPhaseWork value
   */
  public boolean getBuildPhaseWork() {
    return buildPhaseWork;
  }


  /**
   * Sets the buildPhaseWork attribute of the ActionPlanWorkList object
   *
   * @param tmp The new buildPhaseWork value
   */
  public void setBuildPhaseWork(boolean tmp) {
    this.buildPhaseWork = tmp;
  }


  /**
   * Sets the buildPhaseWork attribute of the ActionPlanWorkList object
   *
   * @param tmp The new buildPhaseWork value
   */
  public void setBuildPhaseWork(String tmp) {
    this.buildPhaseWork = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildStepWork attribute of the ActionPlanWorkList object
   *
   * @return The buildStepWork value
   */
  public boolean getBuildStepWork() {
    return buildStepWork;
  }


  /**
   * Sets the buildStepWork attribute of the ActionPlanWorkList object
   *
   * @param tmp The new buildStepWork value
   */
  public void setBuildStepWork(boolean tmp) {
    this.buildStepWork = tmp;
  }


  /**
   * Sets the buildStepWork attribute of the ActionPlanWorkList object
   *
   * @param tmp The new buildStepWork value
   */
  public void setBuildStepWork(String tmp) {
    this.buildStepWork = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the pagedListInfo attribute of the ActionPlanWorkList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the ActionPlanWorkList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the linkModuleId attribute of the ActionPlanWorkList object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Sets the linkModuleId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }
  
  
  /**
   * Gets the owner attribute of the ActionPlanWorkList object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Sets the owner attribute of the ActionPlanWorkList object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the owner attribute of the ActionPlanWorkList object
   *
   * @param tmp The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   * Gets the actionPlanId attribute of the ActionPlanWorkList object
   *
   * @return The actionPlanId value
   */
  public int getActionPlanId() {
    return actionPlanId;
  }


  /**
   * Sets the actionPlanId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new actionPlanId value
   */
  public void setActionPlanId(int tmp) {
    this.actionPlanId = tmp;
  }


  /**
   * Sets the actionPlanId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new actionPlanId value
   */
  public void setActionPlanId(String tmp) {
    this.actionPlanId = Integer.parseInt(tmp);
  }


  /**
   * Gets the siteId attribute of the ActionPlanWorkList object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   * Sets the siteId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   * Sets the siteId attribute of the ActionPlanWorkList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the exclusiveToSite attribute of the ActionPlanWorkList object
   *
   * @return The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   * Sets the exclusiveToSite attribute of the ActionPlanWorkList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   * Sets the exclusiveToSite attribute of the ActionPlanWorkList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the includeAllSites attribute of the ActionPlanWorkList object
   *
   * @return The includeAllSites value
   */
  public boolean getIncludeAllSites() {
    return includeAllSites;
  }


  /**
   * Sets the includeAllSites attribute of the ActionPlanWorkList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(boolean tmp) {
    this.includeAllSites = tmp;
  }


  /**
   * Sets the includeAllSites attribute of the ActionPlanWorkList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(String tmp) {
    this.includeAllSites = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildCurrentPhaseOnly attribute of the ActionPlanWorkList object
   *
   * @return The buildCurrentPhaseOnly value
   */
  public boolean getBuildCurrentPhaseOnly() {
    return buildCurrentPhaseOnly;
  }


  /**
   * Sets the buildCurrentPhaseOnly attribute of the ActionPlanWorkList object
   *
   * @param tmp The new buildCurrentPhaseOnly value
   */
  public void setBuildCurrentPhaseOnly(boolean tmp) {
    this.buildCurrentPhaseOnly = tmp;
  }


  /**
   * Sets the buildCurrentPhaseOnly attribute of the ActionPlanWorkList object
   *
   * @param tmp The new buildCurrentPhaseOnly value
   */
  public void setBuildCurrentPhaseOnly(String tmp) {
    this.buildCurrentPhaseOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildCurrentStepOnly attribute of the ActionPlanWorkList object
   *
   * @return The buildCurrentStepOnly value
   */
  public boolean getBuildCurrentStepOnly() {
    return buildCurrentStepOnly;
  }


  /**
   * Sets the buildCurrentStepOnly attribute of the ActionPlanWorkList object
   *
   * @param tmp The new buildCurrentStepOnly value
   */
  public void setBuildCurrentStepOnly(boolean tmp) {
    this.buildCurrentStepOnly = tmp;
  }


  /**
   * Sets the buildCurrentStepOnly attribute of the ActionPlanWorkList object
   *
   * @param tmp The new buildCurrentStepOnly value
   */
  public void setBuildCurrentStepOnly(String tmp) {
    this.buildCurrentStepOnly = DatabaseUtils.parseBoolean(tmp);
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
    isTicket = (linkModuleId == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));

    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM action_plan_work apw " +
            "LEFT JOIN action_plan ap ON (apw.action_plan_id = ap.plan_id) " +
            "LEFT JOIN contact c ON (apw.assignedTo = c.user_id) " +
            "LEFT JOIN action_plan_constants apc ON (apw.link_module_id = apc.map_id) " +
            "WHERE apw.plan_work_id > 0 ");

    createFilter(db, sqlFilter);

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
        pst = db.prepareStatement(
            sqlCount.toString() +
                sqlFilter.toString() +
                "AND " + DatabaseUtils.toLowerCase(db) + "(ap.plan_name) < ? ");
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
      pagedListInfo.setDefaultSort("c.namefirst, c.namelast, apw.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY c.namefirst, c.namelast, apw.entered ");
    }
    
    pst = prepareList(db, sqlFilter.toString(), sqlOrder.toString());
    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    
    while (rs.next()) {
      ActionPlanWork actionPlanWork = new ActionPlanWork(rs);
      this.add(actionPlanWork);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }

    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlanWork actionPlanWork = (ActionPlanWork) i.next();
			//TODO: needs to be optimized
      if (buildPhaseWork) {
        actionPlanWork.setBuildStepWork(buildStepWork);
				actionPlanWork.setBuildLinkedObject(buildLinkedObject);
        actionPlanWork.buildPhaseWork(db);
      }
      if (buildCurrentPhaseOnly) {
        actionPlanWork.setBuildCurrentStepWork(buildCurrentStepOnly);
        actionPlanWork.buildCurrentPhaseWork(db);
      }
      if (buildLinkedObject) {
        actionPlanWork.buildLinkedObject(db);
      }
    }
  }


  /**
   * Description of the Method
   * @param db        Description of the Parameter
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (linkModuleId > -1) { 
      sqlFilter.append("AND apw.link_module_id = ? ");
    }
    if (linkItemId > -1) {
      sqlFilter.append("AND apw.link_item_id = ? ");
    }
    if (!allMyPlans) {
      if (owner > -1) {
        sqlFilter.append("AND apw.assignedTo = ? ");
      }
      if (manager > -1) {
        sqlFilter.append("AND apw.manager = ? ");
      }
      if (viewpoint > -1) {
        sqlFilter.append("AND (apw.assignedTo = ? OR apw.manager = ?) ");
      }
      if (currentStepOwner > -1) {
        sqlFilter.append("AND apw.plan_work_id IN (SELECT aphw.plan_work_id FROM action_phase_work aphw " +
            " WHERE aphw.phase_work_id IN (SELECT phase_work_id FROM action_item_work aiw " +
            " WHERE aiw.start_date IS NOT NULL " +
            " AND aiw.end_date IS NULL " +
            " AND (aiw.owner = ? ");
        if (isTicket) {
          sqlFilter.append(" OR aiw.action_step_id IN (SELECT s.step_id FROM action_step s " +
              " WHERE (s.permission_type = ? " +
              " AND apw.link_module_id = ? " +
              " AND apw.link_item_id IN (SELECT t.ticketid FROM ticket t " +
              " WHERE t.user_group_id IN (SELECT group_id FROM user_group_map WHERE user_id = ? )))) ");
        }
        sqlFilter.append(
            " OR aiw.action_step_id IN (SELECT s.step_id FROM action_step s " +
                " WHERE (s.permission_type = ? AND s.role_id IN (SELECT role_id FROM " + DatabaseUtils.addQuotes(db, "access") + " WHERE user_id = ? ))) " +
                " OR aiw.action_step_id IN (SELECT s.step_id FROM action_step s " +
                " WHERE s.permission_type = ? AND s.department_id IN (SELECT department FROM contact WHERE user_id = ? )) " +
                " OR aiw.action_step_id IN (SELECT s.step_id FROM action_step s " +
                " WHERE s.permission_type = ? AND s.group_id IN (SELECT group_id from user_group_map WHERE user_id = ? )) " +
                " )) " +
                "AND aphw.start_date IS NOT NULL AND aphw.end_date IS NULL AND aphw.status_id IS NULL ) ");
      }
    } else {
      if (owner > -1 || manager > -1 || currentStepOwner > -1) {
        sqlFilter.append("AND ( ");
      }
      if (owner > -1) {
        sqlFilter.append(" apw.assignedTo = ? ");
      }
      if (manager > -1) {
        sqlFilter.append((owner > -1 ? "OR " : "") + " apw.manager = ? ");
      }
      if (currentStepOwner > -1) {
        sqlFilter.append((owner > -1 || manager > -1 ? "OR " : "") +
            " apw.plan_work_id IN (SELECT aphw.plan_work_id FROM action_phase_work aphw " +
            " WHERE aphw.phase_work_id IN (SELECT phase_work_id FROM action_item_work aiw " +
            " WHERE aiw.start_date IS NOT NULL " +
            " AND aiw.end_date IS NULL " +
            " AND (aiw.owner = ? ");
        if (isTicket) {
          sqlFilter.append(" OR aiw.action_step_id IN (SELECT s.step_id FROM action_step s " +
              " WHERE (s.permission_type = ? " +
              " AND apw.link_module_id = ? " +
              " AND apw.link_item_id IN (SELECT t.ticketid FROM ticket t " +
              " WHERE t.user_group_id IN (SELECT group_id FROM user_group_map WHERE user_id = ? )))) ");
        }
        sqlFilter.append(
            " OR aiw.action_step_id IN (SELECT s.step_id FROM action_step s " +
                " WHERE (s.permission_type = ? AND s.role_id IN (SELECT role_id FROM " + DatabaseUtils.addQuotes(db, "access") + " WHERE user_id = ? ))) " +
                " OR aiw.action_step_id IN (SELECT s.step_id FROM action_step s " +
                " WHERE s.permission_type = ? AND s.department_id IN (SELECT department FROM contact WHERE user_id = ? )) " +
                " OR aiw.action_step_id IN (SELECT s.step_id FROM action_step s " +
                " WHERE s.permission_type = ? AND s.group_id IN (SELECT group_id from user_group_map WHERE user_id = ? )) " +
                " )) " +
                "AND aphw.start_date IS NOT NULL AND aphw.end_date IS NULL AND aphw.status_id IS NULL ) ");
      }
      if (owner > -1 || manager > -1 || currentStepOwner > -1) {
        sqlFilter.append(") ");
      }
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND apw.enabled = ? ");
    }
    if (hasCurrentPhase != Constants.UNDEFINED) {
      if (hasCurrentPhase == Constants.TRUE) {
        sqlFilter.append("AND apw.current_phase IS NOT NULL ");
      } else if (hasCurrentPhase == Constants.FALSE) {
        sqlFilter.append("AND apw.current_phase IS NULL ");
      }
    }
    if (actionPlanId != -1) {
      sqlFilter.append("AND apw.action_plan_id = ? ");
    }
    if (ownerRange != null && !"".equals(ownerRange.trim())) {
      sqlFilter.append("AND apw.assignedto IN (" + ownerRange + ") ");
    }
    if (opportunityId > -1) {
      sqlFilter.append("AND apw.plan_work_id IN (SELECT plan_work_id FROM action_phase_work " +
          " WHERE phase_work_id IN (SELECT phase_work_id FROM action_item_work " +
          " WHERE link_module_id IN (SELECT map_id FROM action_plan_constants WHERE constant_id = " + ActionPlan.PIPELINE_COMPONENT + ") AND link_item_id IN (SELECT id FROM opportunity_component WHERE opp_id = ? ))) ");
    }
    if (!includeAllSites) {
      if (siteId > -1) {
        sqlFilter.append("AND (ap.site_id = ? ");
        if (!exclusiveToSite) {
          sqlFilter.append("OR ap.site_id IS NULL ");
        }
        sqlFilter.append(") ");
      } else {
        sqlFilter.append("AND ap.site_id IS NULL ");
      }
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND apw.entered > ? ");
      }
      sqlFilter.append("AND apw.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND apw.modified > ? ");
      sqlFilter.append("AND apw.entered < ? ");
      sqlFilter.append("AND apw.modified < ? ");
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
    if (linkModuleId > -1) {
      pst.setInt(++i, linkModuleId);
    }
    if (linkItemId > -1) {
      pst.setInt(++i, linkItemId);
    }
    if (owner > -1) {
      pst.setInt(++i, owner);
    }
    if (manager > -1) {
      pst.setInt(++i, manager);
    }
    if (!allMyPlans && viewpoint > -1) {
      pst.setInt(++i, viewpoint);
      pst.setInt(++i, viewpoint);
    }
    if (currentStepOwner > -1) {
      pst.setInt(++i, currentStepOwner);
      if (isTicket) {
        pst.setInt(++i, ActionStep.USER_GROUP);
        pst.setInt(++i, linkModuleId);
        pst.setInt(++i, currentStepOwner);
      }
      pst.setInt(++i, ActionStep.ROLE);
      pst.setInt(++i, currentStepOwner);
      pst.setInt(++i, ActionStep.DEPARTMENT);
      pst.setInt(++i, currentStepOwner);
      pst.setInt(++i, ActionStep.SPECIFIC_USER_GROUP);
      pst.setInt(++i, currentStepOwner);
    }
    if (enabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (enabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }
    if (actionPlanId != -1) {
      pst.setInt(++i, actionPlanId);
    }
    if (opportunityId > -1) {
      pst.setInt(++i, opportunityId);
    }
    if (!includeAllSites && siteId > -1) {
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


  /**
   * Gets the enabledItem attribute of the ActionPlanWorkList object
   *
   * @return The enabledItem value
   */
  public int getEnabledItem() {
    int result = -1;
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ActionPlanWork work = (ActionPlanWork) iterator.next();
      if (work.getEnabled()) {
        result = work.getId();
        break;
      }
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlanWork thisWork = (ActionPlanWork) i.next();
      thisWork.delete(db);
    }
  }


  /**
   * Enable or disable Action Plan Work entries
   *
   * @param db     Description of the Parameter
   * @param enable Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void enable(Connection db, boolean enable) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlanWork thisWork = (ActionPlanWork) i.next();
      thisWork.setEnabled(enable);
      thisWork.update(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param linkModuleId Description of the Parameter
   * @param linkItemId   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int linkModuleId, int linkItemId) throws SQLException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(plan_work_id) as itemcount " +
            "FROM action_plan_work apw " +
            "WHERE apw.plan_work_id > 0 " +
            "AND apw.link_module_id = ? " +
            "AND apw.link_item_id = ? ");
    //TODO: implement trashing and uncomment this
    //"AND apw.trashed_date IS NULL ");
    pst.setInt(1, linkModuleId);
    pst.setInt(2, linkItemId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("itemcount");
    }
    rs.close();
    pst.close();
    return count;
  }


  /**
   * Gets the latestPlan attribute of the ActionPlanWorkList object
   *
   * @return The latestPlan value
   */
  public ActionPlanWork getLatestPlan() {
    ActionPlanWork result = null;
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      ActionPlanWork work = (ActionPlanWork) iter.next();
      if (result != null && work.getEntered().after(result.getEntered())) {
        result = work;
      } else {
        result = work;
      }
    }
    return result;
  }
  
  /**
   * Gets the DisplayInPlanStepsCount attribute of the ActionPlanWorkList object
   *
   * @return The DisplayInPlanStepsCount value
   */
  public int getDisplayInPlanStepsCount() {
    int result = 0;
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      ActionPlanWork work = (ActionPlanWork) iter.next();
      Iterator phases = work.getPhaseWorkList().iterator();
      while (phases.hasNext()) {
        ActionPhaseWork thisPhase = (ActionPhaseWork) phases.next();
        Iterator items = thisPhase.getItemWorkList().iterator();
        while (items.hasNext()) {
          ActionItemWork thisItem = (ActionItemWork) items.next();
          ActionStep step = thisItem.getStep();
          if (step!=null && step.getDisplayInPlanList()){
           result++;	
          }
        }
      }
    }
    return result;
  }


  /**
   * Gets the planIdHashMap attribute of the ActionPlanWorkList object
   *
   * @return The planIdHashMap value
   */
  public HashMap getPlanIdHashMap() {
    HashMap result = new HashMap();
    Iterator iter = this.iterator();
    while (iter.hasNext()) {
      ActionPlanWork work = (ActionPlanWork) iter.next();
      String workPlanId = String.valueOf(work.getActionPlanId());
      result.put(workPlanId, workPlanId);
    }
    return result;
  }
  
  /**
   *  Gets the object attribute of the ActionPlanWorkList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public ActionPlanWork getObject(ResultSet rs) throws SQLException {
  	ActionPlanWork obj = new ActionPlanWork(rs);
    return obj;
  }
  
  public PreparedStatement prepareList(Connection db, String sqlFilter, String sqlOrder) throws SQLException {
  	StringBuffer sqlSelect = new StringBuffer();
//  Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append(" SELECT ");
    }
    sqlSelect.append(
        "apw.*, " +
            "ap.plan_name, ap.description, ap.site_id, " +
            "c.namefirst, c.namelast, apc.constant_id as link_module_id_constant " +
            "FROM action_plan_work apw " +
            "LEFT JOIN action_plan ap ON (apw.action_plan_id = ap.plan_id) " +
            "LEFT JOIN contact c ON (apw.assignedTo = c.user_id) " +
            "LEFT JOIN action_plan_constants apc ON (apw.link_module_id = apc.map_id) " +
            "WHERE apw.plan_work_id > 0 ");
    if(sqlFilter == null || sqlFilter.length() == 0){
    	StringBuffer buff = new StringBuffer();
    	createFilter(db, buff);
    	sqlFilter = buff.toString();
    }
    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() + sqlFilter + sqlOrder);
    prepareFilter(pst);
    return pst;
  }

  
  /**
   * @param  db                Description of the Parameter
   * @param  pst               Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
  	return prepareList(db, "", "");
  }

  
}

