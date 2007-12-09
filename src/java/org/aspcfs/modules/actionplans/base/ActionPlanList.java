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

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
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
 * @author partha
 * @version $Id: ActionPlanList.java,v 1.1.2.1 2005/08/17 15:29:07 partha Exp
 *          $
 * @created August 17, 2005
 */
public class ActionPlanList extends ArrayList  implements SyncableList {
  public String tableName = "action_plan";
  public String uniqueField = "plan_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private int archived = Constants.UNDEFINED;
  private int categoryId = -1;

  private int enabled = Constants.UNDEFINED;
  private java.sql.Timestamp archiveDate = null;
  private int archiveDateBefore = Constants.UNDEFINED;

  private java.sql.Timestamp approved = null;
  private int includeOnlyApproved = Constants.UNDEFINED;
  private int approvedBefore = Constants.UNDEFINED;
  private int catCode = 0;
  private int subCat1 = 0;
  private int subCat2 = 0;
  private int subCat3 = 0;
  private int linkCatCode = 0;
  private int linkSubCat1 = 0;
  private int linkSubCat2 = 0;
  private int linkSubCat3 = 0;
  private int linkObjectId = -1;
  private String nameTable = null;
  private int siteId = -1;
  //related filters
  private boolean buildPhases = false;
  private boolean buildSteps = false;
  private boolean buildRelatedRecords = false;
  private int owner = -1;
  //resources
  private String jsEvent = null;
  private boolean displayNone = false;
  private boolean includeAllSites = false;
  private boolean exclusiveToSite = false;


  /**
   * Constructor for the ActionPlanList object
   */
  public ActionPlanList() {
  }

  /**
   * Sets the lastAnchor attribute of the ActionPlanList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionPlanList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionPlanList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionPlanList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionPlanList object
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
   * Sets the tableName attribute of the ActionPlanList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setTableName(String tmp) {
    this.tableName = tmp;
  }


  /**
   * Gets the tableName attribute of the ActionPlanList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionPlanList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the nameTable attribute of the ActionPlanList object
   *
   * @return The nameTable value
   */
  public String getNameTable() {
    return nameTable;
  }


  /**
   * Sets the nameTable attribute of the ActionPlanList object
   *
   * @param tmp The new nameTable value
   */
  public void setNameTable(String tmp) {
    this.nameTable = tmp;
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
    
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
            " FROM action_plan ap " +
            " LEFT JOIN action_plan_constants apc ON (ap.link_object_id = apc.map_id) " +
            " LEFT JOIN lookup_site_id ls ON (ap.site_id = ls.code) " +
            " WHERE ap.plan_id > -1 ");
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
      pagedListInfo.setDefaultSort("ap.plan_name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY ap.archive_date, ap.entered ");
    }
    
    pst = prepareList(db, sqlFilter.toString(), sqlOrder.toString());
    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    
    while (rs.next()) {
      ActionPlan thisPlan = new ActionPlan(rs);
      this.add(thisPlan);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    if (buildPhases || buildSteps || buildRelatedRecords) {
      Iterator iterator = (Iterator) this.iterator();
      while (iterator.hasNext()) {
        ActionPlan plan = (ActionPlan) iterator.next();
        plan.setBuildPhases(buildPhases || buildSteps);
        plan.setBuildSteps(buildSteps);
        if (buildRelatedRecords) {
          plan.buildRelatedRecords(db, owner);
        }
        if (buildPhases || buildSteps) {
          plan.buildPhases(db);
        }
      }
    }
  }


  /**
   * Description of the Method
   * @param db        Description of the Parameter
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (id > -1) {
      sqlFilter.append("AND ap.plan_id = ? ");
    }
    if (archived == Constants.TRUE) {
      sqlFilter.append("AND ap.expiration_date IS NOT NULL ");
    } else if (archived == Constants.FALSE) {
      sqlFilter.append("AND ap.expiration_date IS NULL ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND ap.enabled = ? ");
    }
    if (archiveDate != null) {
      if (archiveDateBefore == Constants.UNDEFINED) {
        sqlFilter.append("AND ap.archive_date = ? ");
      } else if (archiveDateBefore == Constants.TRUE) {
        sqlFilter.append("AND ap.archive_date < ? ");
      } else {
        sqlFilter.append("AND ap.archive_date > ? ");
      }
    }
    if (includeOnlyApproved == Constants.TRUE) {
      sqlFilter.append("AND ap.approved IS NOT NULL ");
    } else if (includeOnlyApproved == Constants.FALSE) {
      sqlFilter.append("AND ap.approved IS NULL ");
    }
    if (approved != null) {
      if (approvedBefore == Constants.UNDEFINED) {
        sqlFilter.append("AND ap.approved = ? ");
      } else if (approvedBefore == Constants.TRUE) {
        sqlFilter.append("AND ap.approved < ? ");
      } else {
        sqlFilter.append("AND ap.approved > ? ");
      }
    }
    if (subCat3 != 0) {
      sqlFilter.append("AND subcat_code3 = ? ");
    } else if (subCat2 != 0) {
      sqlFilter.append("AND subcat_code2 = ? ");
    } else if (subCat1 != 0) {
      sqlFilter.append("AND subcat_code1 = ? ");
    } else if (catCode != 0) {
      sqlFilter.append("AND cat_code = ? ");
    }

    if (linkObjectId != -1) {
      sqlFilter.append("AND link_object_id = ? ");
    }

    if (nameTable != null && (linkSubCat3 != 0 || linkSubCat2 != 0 || linkSubCat1 != 0 || linkCatCode != 0)) {
      sqlFilter.append(
          "AND ap.plan_id IN ( SELECT plan_id FROM " + nameTable + "_plan_map " +
              "WHERE category_id = ?) ");
    } else if (nameTable != null && displayNone) {
      sqlFilter.append("AND ap.plan_id = ? ");
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
        sqlFilter.append("AND ap.entered > ? ");
      }
      sqlFilter.append("AND ap.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND ap.modified > ? ");
      sqlFilter.append("AND ap.entered < ? ");
      sqlFilter.append("AND ap.modified < ? ");
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

    if (enabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (enabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }

    if (archiveDate != null) {
      pst.setTimestamp(++i, this.getArchiveDate());
    }

    if (approved != null) {
      pst.setTimestamp(++i, this.getApproved());
    }
    if (subCat3 != 0) {
      pst.setInt(++i, this.getSubCat3());
    } else if (subCat2 != 0) {
      pst.setInt(++i, this.getSubCat2());
    } else if (subCat1 != 0) {
      pst.setInt(++i, this.getSubCat1());
    } else if (catCode != 0) {
      pst.setInt(++i, this.getCatCode());
    }
    if (linkObjectId != -1) {
      pst.setInt(++i, this.getLinkObjectId());
    }
    if (nameTable != null) {
      if (linkSubCat3 != 0) {
        pst.setInt(++i, linkSubCat3);
      } else if (linkSubCat2 != 0) {
        pst.setInt(++i, linkSubCat2);
      } else if (linkSubCat1 != 0) {
        pst.setInt(++i, linkSubCat1);
      } else if (linkCatCode != 0) {
        pst.setInt(++i, linkCatCode);
      } else {
        if (displayNone) {
          DatabaseUtils.setInt(pst, ++i, -1);
        }
      }
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
   * Gets the htmlSelect attribute of the ActionPlanList object
   *
   * @param selectName Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   * Gets the htmlSelect attribute of the ActionPlanList object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect actionPlanListSelect = new HtmlSelect();
    if (jsEvent != null) {
      actionPlanListSelect.setJsEvent(jsEvent);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlan thisPlan = (ActionPlan) i.next();
      actionPlanListSelect.addItem(thisPlan.getId(), thisPlan.getName() + (thisPlan.getSiteId() > -1 ? thisPlan.getSiteName() : ""));
    }
    return actionPlanListSelect.getHtml(selectName, defaultKey);
  }


  /**
   * Gets the htmlSelectObj attribute of the ActionPlanList object
   *
   * @return The htmlSelectObj value
   */
  public HtmlSelect getHtmlSelectObj() {
    HtmlSelect actionPlanListSelect = new HtmlSelect();
    if (jsEvent != null) {
      actionPlanListSelect.setJsEvent(jsEvent);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlan thisPlan = (ActionPlan) i.next();
      actionPlanListSelect.addItem(thisPlan.getId(), thisPlan.getName() + (thisPlan.getSiteId() > -1 ? thisPlan.getSiteName() : ""));
    }
    return actionPlanListSelect;
  }


  /**
   * Gets the enabledPlanId attribute of the ActionPlanList object
   *
   * @return The enabledPlanId value
   */
  public int getEnabledPlanId() {
    int result = -1;
    if (this.size() == 1) {
      return ((ActionPlan) this.get(0)).getId();
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlan thisPlan = (ActionPlan) i.next();
      if (thisPlan.getEnabled()) {
        result = thisPlan.getId();
        break;
      }
    }
    return result;
  }


  /**
   * Gets the enabledPlanName attribute of the ActionPlanList object
   *
   * @return The enabledPlanName value
   */
  public String getEnabledPlanName() {
    String result = null;
    if (this.size() == 1) {
      return ((ActionPlan) this.get(0)).getName();
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlan thisPlan = (ActionPlan) i.next();
      if (thisPlan.getEnabled()) {
        result = thisPlan.getName();
        break;
      }
    }
    return result;
  }


  /**
   * Gets the enabledActionPlan attribute of the ActionPlanList object
   *
   * @return The enabledActionPlan value
   */
  public ActionPlan getEnabledActionPlan() {
    ActionPlan result = null;
    if (this.size() == 1) {
      return (ActionPlan) this.get(0);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlan thisPlan = (ActionPlan) i.next();
      if (thisPlan.getEnabled()) {
        result = thisPlan;
        break;
      }
    }
    return result;
  }


  /**
   * Gets the htmlSelectGroup attribute of the ActionPlanList object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @return The htmlSelectGroup value
   */
  public String getHtmlSelectGroup(SystemStatus thisSystem, String selectName, int defaultKey) {
    ArrayList archivedPlans = new ArrayList();
    ArrayList activePlans = new ArrayList();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlan thisPlan = (ActionPlan) i.next();
      if (thisPlan.getArchiveDate() == null) {
        activePlans.add(thisPlan);
      } else {
        archivedPlans.add(thisPlan);
      }
    }

    HtmlSelect actionPlanListSelect = new HtmlSelect();
    if (jsEvent != null) {
      actionPlanListSelect.setJsEvent(jsEvent);
    }
    int count = 0;
    //Add active plans
    actionPlanListSelect.addGroup(thisSystem.getLabel("actionPlan.activePlans", "Active Plans") + " (" + activePlans.size() + ")");
    Iterator active = activePlans.iterator();
    while (active.hasNext()) {
      count++;
      ActionPlan thisPlan = (ActionPlan) active.next();
      actionPlanListSelect.addItem(thisPlan.getId(), count + ". " + thisPlan.getName());
    }
    count = 0;
    //Add archived plans
    actionPlanListSelect.addGroup(thisSystem.getLabel("actionPlan.archivedPlans", "Archived Plans") + " (" + archivedPlans.size() + ")");
    Iterator archived = archivedPlans.iterator();
    while (archived.hasNext()) {
      count++;
      ActionPlan thisPlan = (ActionPlan) archived.next();
      actionPlanListSelect.addItem(thisPlan.getId(), count + ". " + thisPlan.getName());
    }
    return actionPlanListSelect.getHtml(selectName, defaultKey);
  }


  /**
   * Iterates through the action plans and returns the first active plan. The
   * plan returned is used to display the user's action plan dashboard
   *
   * @return The plan value
   */
  public ActionPlan getPlan() {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlan thisPlan = (ActionPlan) i.next();
      if (thisPlan.getArchiveDate() == null) {
        return thisPlan;
      }
    }
    return (this.size() > 0 ? (ActionPlan) this.get(0) : new ActionPlan());
  }


  /**
   * Adds a feature to the AtleastOne attribute of the ActionPlanList object
   *
   * @param db       The feature to be added to the AtleastOne
   *                 attribute
   * @param workList The feature to be added to the AtleastOne
   *                 attribute
   * @throws SQLException Description of the Exception
   */
  public void addAtleastOne(Connection db, ActionPlanWorkList workList) throws SQLException {
    if (workList == null || workList.size() == 0) {
      return;
    }
    Iterator iter = this.iterator();
    boolean included = false;
    HashMap workMap = workList.getPlanIdHashMap();
    while (iter.hasNext()) {
      ActionPlan plan = (ActionPlan) iter.next();
      if (workMap.get(String.valueOf(plan.getId())) != null) {
        included = true;
        break;
      }
    }
    if (!included) {
      ActionPlanWork work = (ActionPlanWork) workList.get(0);
      ActionPlan plan = new ActionPlan(db, work.getActionPlanId());
      this.add(plan);
    }
  }


  /*
   *  Get and Set methods
   */
  /**
   * Gets the pagedListInfo attribute of the ActionPlanList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the ActionPlanList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the id attribute of the ActionPlanList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the ActionPlanList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ActionPlanList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the enteredBy attribute of the ActionPlanList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Sets the enteredBy attribute of the ActionPlanList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the ActionPlanList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the modifiedBy attribute of the ActionPlanList object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Sets the modifiedBy attribute of the ActionPlanList object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the ActionPlanList object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the buildPhases attribute of the ActionPlanList object
   *
   * @return The buildPhases value
   */
  public boolean getBuildPhases() {
    return buildPhases;
  }


  /**
   * Sets the buildPhases attribute of the ActionPlanList object
   *
   * @param tmp The new buildPhases value
   */
  public void setBuildPhases(boolean tmp) {
    this.buildPhases = tmp;
  }


  /**
   * Sets the buildPhases attribute of the ActionPlanList object
   *
   * @param tmp The new buildPhases value
   */
  public void setBuildPhases(String tmp) {
    this.buildPhases = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildSteps attribute of the ActionPlanList object
   *
   * @return The buildSteps value
   */
  public boolean getBuildSteps() {
    return buildSteps;
  }


  /**
   * Sets the buildSteps attribute of the ActionPlanList object
   *
   * @param tmp The new buildSteps value
   */
  public void setBuildSteps(boolean tmp) {
    this.buildSteps = tmp;
  }


  /**
   * Sets the buildSteps attribute of the ActionPlanList object
   *
   * @param tmp The new buildSteps value
   */
  public void setBuildSteps(String tmp) {
    this.buildSteps = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the archived attribute of the ActionPlanList object
   *
   * @return The archived value
   */
  public int getArchived() {
    return archived;
  }


  /**
   * Sets the archived attribute of the ActionPlanList object
   *
   * @param tmp The new archived value
   */
  public void setArchived(int tmp) {
    this.archived = tmp;
  }


  /**
   * Sets the archived attribute of the ActionPlanList object
   *
   * @param tmp The new archived value
   */
  public void setArchived(String tmp) {
    this.archived = Integer.parseInt(tmp);
  }


  /**
   * Gets the approved attribute of the ActionPlanList object
   *
   * @return The approved value
   */
  public java.sql.Timestamp getApproved() {
    return approved;
  }


  /**
   * Sets the approved attribute of the ActionPlanList object
   *
   * @param tmp The new approved value
   */
  public void setApproved(java.sql.Timestamp tmp) {
    this.approved = tmp;
  }


  /**
   * Sets the approved attribute of the ActionPlanList object
   *
   * @param tmp The new approved value
   */
  public void setApproved(String tmp) {
    this.approved = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the includeOnlyApproved attribute of the ActionPlanList object
   *
   * @return The includeOnlyApproved value
   */
  public int getIncludeOnlyApproved() {
    return includeOnlyApproved;
  }


  /**
   * Sets the includeOnlyApproved attribute of the ActionPlanList object
   *
   * @param tmp The new includeOnlyApproved value
   */
  public void setIncludeOnlyApproved(int tmp) {
    this.includeOnlyApproved = tmp;
  }


  /**
   * Sets the includeOnlyApproved attribute of the ActionPlanList object
   *
   * @param tmp The new includeOnlyApproved value
   */
  public void setIncludeOnlyApproved(String tmp) {
    this.includeOnlyApproved = Integer.parseInt(tmp);
  }


  /**
   * Gets the enabled attribute of the ActionPlanList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Sets the enabled attribute of the ActionPlanList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ActionPlanList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Gets the archiveDate attribute of the ActionPlanList object
   *
   * @return The archiveDate value
   */
  public java.sql.Timestamp getArchiveDate() {
    return archiveDate;
  }


  /**
   * Sets the archiveDate attribute of the ActionPlanList object
   *
   * @param tmp The new archiveDate value
   */
  public void setArchiveDate(java.sql.Timestamp tmp) {
    this.archiveDate = tmp;
  }


  /**
   * Sets the archiveDate attribute of the ActionPlanList object
   *
   * @param tmp The new archiveDate value
   */
  public void setArchiveDate(String tmp) {
    this.archiveDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the archiveDateBefore attribute of the ActionPlanList object
   *
   * @return The archiveDateBefore value
   */
  public int getArchiveDateBefore() {
    return archiveDateBefore;
  }


  /**
   * Sets the archiveDateBefore attribute of the ActionPlanList object
   *
   * @param tmp The new archiveDateBefore value
   */
  public void setArchiveDateBefore(int tmp) {
    this.archiveDateBefore = tmp;
  }


  /**
   * Sets the archiveDateBefore attribute of the ActionPlanList object
   *
   * @param tmp The new archiveDateBefore value
   */
  public void setArchiveDateBefore(String tmp) {
    this.archiveDateBefore = Integer.parseInt(tmp);
  }


  /**
   * Gets the approvedBefore attribute of the ActionPlanList object
   *
   * @return The approvedBefore value
   */
  public int getApprovedBefore() {
    return approvedBefore;
  }


  /**
   * Sets the approvedBefore attribute of the ActionPlanList object
   *
   * @param tmp The new approvedBefore value
   */
  public void setApprovedBefore(int tmp) {
    this.approvedBefore = tmp;
  }


  /**
   * Sets the approvedBefore attribute of the ActionPlanList object
   *
   * @param tmp The new approvedBefore value
   */
  public void setApprovedBefore(String tmp) {
    this.approvedBefore = Integer.parseInt(tmp);
  }


  /**
   * Gets the buildRelatedRecords attribute of the ActionPlanList object
   *
   * @return The buildRelatedRecords value
   */
  public boolean getBuildRelatedRecords() {
    return buildRelatedRecords;
  }


  /**
   * Sets the buildRelatedRecords attribute of the ActionPlanList object
   *
   * @param tmp The new buildRelatedRecords value
   */
  public void setBuildRelatedRecords(boolean tmp) {
    this.buildRelatedRecords = tmp;
  }


  /**
   * Sets the buildRelatedRecords attribute of the ActionPlanList object
   *
   * @param tmp The new buildRelatedRecords value
   */
  public void setBuildRelatedRecords(String tmp) {
    this.buildRelatedRecords = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the owner attribute of the ActionPlanList object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Sets the owner attribute of the ActionPlanList object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the owner attribute of the ActionPlanList object
   *
   * @param tmp The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   * Gets the catCode attribute of the ActionPlanList object
   *
   * @return The catCode value
   */
  public int getCatCode() {
    return catCode;
  }


  /**
   * Sets the catCode attribute of the ActionPlanList object
   *
   * @param tmp The new catCode value
   */
  public void setCatCode(int tmp) {
    this.catCode = tmp;
  }


  /**
   * Sets the catCode attribute of the ActionPlanList object
   *
   * @param tmp The new catCode value
   */
  public void setCatCode(String tmp) {
    this.catCode = Integer.parseInt(tmp);
  }


  /**
   * Gets the subCat1 attribute of the ActionPlanList object
   *
   * @return The subCat1 value
   */
  public int getSubCat1() {
    return subCat1;
  }


  /**
   * Sets the subCat1 attribute of the ActionPlanList object
   *
   * @param tmp The new subCat1 value
   */
  public void setSubCat1(int tmp) {
    this.subCat1 = tmp;
  }


  /**
   * Sets the subCat1 attribute of the ActionPlanList object
   *
   * @param tmp The new subCat1 value
   */
  public void setSubCat1(String tmp) {
    this.subCat1 = Integer.parseInt(tmp);
  }


  /**
   * Gets the subCat2 attribute of the ActionPlanList object
   *
   * @return The subCat2 value
   */
  public int getSubCat2() {
    return subCat2;
  }


  /**
   * Sets the subCat2 attribute of the ActionPlanList object
   *
   * @param tmp The new subCat2 value
   */
  public void setSubCat2(int tmp) {
    this.subCat2 = tmp;
  }


  /**
   * Sets the subCat2 attribute of the ActionPlanList object
   *
   * @param tmp The new subCat2 value
   */
  public void setSubCat2(String tmp) {
    this.subCat2 = Integer.parseInt(tmp);
  }


  /**
   * Gets the subCat3 attribute of the ActionPlanList object
   *
   * @return The subCat3 value
   */
  public int getSubCat3() {
    return subCat3;
  }


  /**
   * Sets the subCat3 attribute of the ActionPlanList object
   *
   * @param tmp The new subCat3 value
   */
  public void setSubCat3(int tmp) {
    this.subCat3 = tmp;
  }


  /**
   * Sets the subCat3 attribute of the ActionPlanList object
   *
   * @param tmp The new subCat3 value
   */
  public void setSubCat3(String tmp) {
    this.subCat3 = Integer.parseInt(tmp);
  }


  /**
   * Sets the level0 attribute of the ActionPlanList object
   *
   * @param tmp The new level0 value
   */
  public void setLevel0(String tmp) {
    this.catCode = Integer.parseInt(tmp);
  }


  /**
   * Sets the level1 attribute of the ActionPlanList object
   *
   * @param tmp The new level1 value
   */
  public void setLevel1(String tmp) {
    this.subCat1 = Integer.parseInt(tmp);
  }


  /**
   * Sets the level2 attribute of the ActionPlanList object
   *
   * @param tmp The new level2 value
   */
  public void setLevel2(String tmp) {
    this.subCat2 = Integer.parseInt(tmp);
  }


  /**
   * Sets the level3 attribute of the ActionPlanList object
   *
   * @param tmp The new level3 value
   */
  public void setLevel3(String tmp) {
    this.subCat3 = Integer.parseInt(tmp);
  }


  /**
   * Gets the level0 attribute of the ActionPlanList object
   *
   * @return The level0 value
   */
  public int getLevel0() {
    return catCode;
  }


  /**
   * Gets the level1 attribute of the ActionPlanList object
   *
   * @return The level1 value
   */
  public int getLevel1() {
    return subCat1;
  }


  /**
   * Gets the level2 attribute of the ActionPlanList object
   *
   * @return The level2 value
   */
  public int getLevel2() {
    return subCat2;
  }


  /**
   * Gets the level3 attribute of the ActionPlanList object
   *
   * @return The level3 value
   */
  public int getLevel3() {
    return subCat3;
  }


  /**
   * Gets the linkObjectId attribute of the ActionPlanList object
   *
   * @return The linkObjectId value
   */
  public int getLinkObjectId() {
    return linkObjectId;
  }


  /**
   * Sets the linkObjectId attribute of the ActionPlanList object
   *
   * @param tmp The new linkObjectId value
   */
  public void setLinkObjectId(int tmp) {
    this.linkObjectId = tmp;
  }


  /**
   * Sets the linkObjectId attribute of the ActionPlanList object
   *
   * @param tmp The new linkObjectId value
   */
  public void setLinkObjectId(String tmp) {
    this.linkObjectId = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkCatCode attribute of the ActionPlanList object
   *
   * @return The linkCatCode value
   */
  public int getLinkCatCode() {
    return linkCatCode;
  }


  /**
   * Sets the linkCatCode attribute of the ActionPlanList object
   *
   * @param tmp The new linkCatCode value
   */
  public void setLinkCatCode(int tmp) {
    this.linkCatCode = tmp;
  }


  /**
   * Sets the linkCatCode attribute of the ActionPlanList object
   *
   * @param tmp The new linkCatCode value
   */
  public void setLinkCatCode(String tmp) {
    this.linkCatCode = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkSubCat1 attribute of the ActionPlanList object
   *
   * @return The linkSubCat1 value
   */
  public int getLinkSubCat1() {
    return linkSubCat1;
  }


  /**
   * Sets the linkSubCat1 attribute of the ActionPlanList object
   *
   * @param tmp The new linkSubCat1 value
   */
  public void setLinkSubCat1(int tmp) {
    this.linkSubCat1 = tmp;
  }


  /**
   * Sets the linkSubCat1 attribute of the ActionPlanList object
   *
   * @param tmp The new linkSubCat1 value
   */
  public void setLinkSubCat1(String tmp) {
    this.linkSubCat1 = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkSubCat2 attribute of the ActionPlanList object
   *
   * @return The linkSubCat2 value
   */
  public int getLinkSubCat2() {
    return linkSubCat2;
  }


  /**
   * Sets the linkSubCat2 attribute of the ActionPlanList object
   *
   * @param tmp The new linkSubCat2 value
   */
  public void setLinkSubCat2(int tmp) {
    this.linkSubCat2 = tmp;
  }


  /**
   * Sets the linkSubCat2 attribute of the ActionPlanList object
   *
   * @param tmp The new linkSubCat2 value
   */
  public void setLinkSubCat2(String tmp) {
    this.linkSubCat2 = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkSubCat3 attribute of the ActionPlanList object
   *
   * @return The linkSubCat3 value
   */
  public int getLinkSubCat3() {
    return linkSubCat3;
  }


  /**
   * Sets the linkSubCat3 attribute of the ActionPlanList object
   *
   * @param tmp The new linkSubCat3 value
   */
  public void setLinkSubCat3(int tmp) {
    this.linkSubCat3 = tmp;
  }


  /**
   * Sets the linkSubCat3 attribute of the ActionPlanList object
   *
   * @param tmp The new linkSubCat3 value
   */
  public void setLinkSubCat3(String tmp) {
    this.linkSubCat3 = Integer.parseInt(tmp);
  }


  /**
   * Gets the jsEvent attribute of the ActionPlanList object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   * Sets the jsEvent attribute of the ActionPlanList object
   *
   * @param tmp The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   * Gets the categoryId attribute of the ActionPlanList object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the categoryId attribute of the ActionPlanList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the ActionPlanList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Gets the displayNone attribute of the ActionPlanList object
   *
   * @return The displayNone value
   */
  public boolean getDisplayNone() {
    return displayNone;
  }


  /**
   * Sets the displayNone attribute of the ActionPlanList object
   *
   * @param tmp The new displayNone value
   */
  public void setDisplayNone(boolean tmp) {
    this.displayNone = tmp;
  }


  /**
   * Sets the displayNone attribute of the ActionPlanList object
   *
   * @param tmp The new displayNone value
   */
  public void setDisplayNone(String tmp) {
    this.displayNone = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the siteId attribute of the ActionPlanList object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   * Sets the siteId attribute of the ActionPlanList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   * Sets the siteId attribute of the ActionPlanList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the includeAllSites attribute of the ActionPlanList object
   *
   * @return The includeAllSites value
   */
  public boolean getIncludeAllSites() {
    return includeAllSites;
  }


  /**
   * Sets the includeAllSites attribute of the ActionPlanList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(boolean tmp) {
    this.includeAllSites = tmp;
  }


  /**
   * Sets the includeAllSites attribute of the ActionPlanList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(String tmp) {
    this.includeAllSites = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the exclusiveToSite attribute of the ActionPlanList object
   *
   * @return The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   * Sets the exclusiveToSite attribute of the ActionPlanList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   * Sets the exclusiveToSite attribute of the ActionPlanList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }
  
  /**
   *  Gets the object attribute of the ActionPlanList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public ActionPlan getObject(ResultSet rs) throws SQLException {
  	ActionPlan obj = new ActionPlan(rs);
    return obj;
  }
  
  public PreparedStatement prepareList(Connection db, String sqlFilter, String sqlOrder) throws SQLException {
  	StringBuffer sqlSelect = new StringBuffer();
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " ap.*, ls.description AS site_name " +
            " FROM action_plan ap " +
            " LEFT JOIN action_plan_constants apc ON (ap.link_object_id = apc.map_id) " +
            " LEFT JOIN lookup_site_id ls ON (ap.site_id = ls.code) " +
            " WHERE ap.plan_id > -1 ");
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

  /**
   * Gets the DisplayInPlanStepsCount attribute of the ActionPlanWorkList object
   *
   * @return The DisplayInPlanStepsCount value
   */
  public int getDisplayInPlanStepsCount() {
    int result = 0;
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      ActionPlan actionPlan = (ActionPlan) iter.next();
      Iterator phases = actionPlan.getPhases().iterator();
      while (phases.hasNext()) {
        ActionPhase thisPhase = (ActionPhase) phases.next();
        Iterator steps = thisPhase.getSteps().iterator();
        while (steps.hasNext()) {
          ActionStep  step = (ActionStep) steps.next();       
          if (step!=null && step.getDisplayInPlanList()){
           result++;	
          }
        }
      }
    }
    return result;
  }

}

