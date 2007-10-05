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
 * @version $Id: ActionItemWorkList.java,v 1.1.2.11 2005/09/22 17:12:48
 *          ananth Exp $
 * @created August 17, 2005
 */
public class ActionItemWorkList extends ArrayList  implements SyncableList {
  private PagedListInfo pagedListInfo = null;
  private int phaseWorkId = -1;
  private int planWorkId = -1;
  private boolean buildLinkedObject = false;
  private int actionStepId = -1;
  private boolean buildStep = false;
  private ActionPlanWork planWork = null;
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private boolean isCurrent = false;

  public final static String tableName = "action_item_work";
  public final static String uniqueField = "item_work_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  /**
   * Sets the lastAnchor attribute of the ActionItemWorkList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionItemWorkList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemWorkList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemWorkList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionItemWorkList object
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
   * Sets the PagedListInfo attribute of the ActionItemWorkList object. <p>
   * <p/>
   * The query results will be constrained to the PagedListInfo parameters.
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Gets the tableName attribute of the ActionItemWorkList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionItemWorkList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the buildStep attribute of the ActionItemWorkList object
   *
   * @return The buildStep value
   */
  public boolean getBuildStep() {
    return buildStep;
  }


  /**
   * Sets the buildStep attribute of the ActionItemWorkList object
   *
   * @param tmp The new buildStep value
   */
  public void setBuildStep(boolean tmp) {
    this.buildStep = tmp;
  }


  /**
   * Sets the buildStep attribute of the ActionItemWorkList object
   *
   * @param tmp The new buildStep value
   */
  public void setBuildStep(String tmp) {
    this.buildStep = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the planWorkId attribute of the ActionItemWorkList object
   *
   * @return The planWorkId value
   */
  public int getPlanWorkId() {
    return planWorkId;
  }


  /**
   * Sets the planWorkId attribute of the ActionItemWorkList object
   *
   * @param tmp The new planWorkId value
   */
  public void setPlanWorkId(int tmp) {
    this.planWorkId = tmp;
  }


  /**
   * Sets the planWorkId attribute of the ActionItemWorkList object
   *
   * @param tmp The new planWorkId value
   */
  public void setPlanWorkId(String tmp) {
    this.planWorkId = Integer.parseInt(tmp);
  }


  /**
   * Gets the buildLinkedObject attribute of the ActionItemWorkList object
   *
   * @return The buildLinkedObject value
   */
  public boolean getBuildLinkedObject() {
    return buildLinkedObject;
  }


  /**
   * Sets the buildLinkedObject attribute of the ActionItemWorkList object
   *
   * @param tmp The new buildLinkedObject value
   */
  public void setBuildLinkedObject(boolean tmp) {
    this.buildLinkedObject = tmp;
  }


  /**
   * Sets the buildLinkedObject attribute of the ActionItemWorkList object
   *
   * @param tmp The new buildLinkedObject value
   */
  public void setBuildLinkedObject(String tmp) {
    this.buildLinkedObject = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the phaseWorkId attribute of the ActionItemWorkList object
   *
   * @return The phaseWorkId value
   */
  public int getPhaseWorkId() {
    return phaseWorkId;
  }


  /**
   * Sets the phaseWorkId attribute of the ActionItemWorkList object
   *
   * @param tmp The new phaseWorkId value
   */
  public void setPhaseWorkId(int tmp) {
    this.phaseWorkId = tmp;
  }


  /**
   * Sets the phaseWorkId attribute of the ActionItemWorkList object
   *
   * @param tmp The new phaseWorkId value
   */
  public void setPhaseWorkId(String tmp) {
    this.phaseWorkId = Integer.parseInt(tmp);
  }


  /**
   * Gets the actionStepId attribute of the ActionItemWorkList object
   *
   * @return The actionStepId value
   */
  public int getActionStepId() {
    return actionStepId;
  }


  /**
   * Sets the actionStepId attribute of the ActionItemWorkList object
   *
   * @param tmp The new actionStepId value
   */
  public void setActionStepId(int tmp) {
    this.actionStepId = tmp;
  }


  /**
   * Sets the actionStepId attribute of the ActionItemWorkList object
   *
   * @param tmp The new actionStepId value
   */
  public void setActionStepId(String tmp) {
    this.actionStepId = Integer.parseInt(tmp);
  }


  /**
   * Gets the planWork attribute of the ActionItemWorkList object
   *
   * @return The planWork value
   */
  public ActionPlanWork getPlanWork() {
    return planWork;
  }


  /**
   * Sets the planWork attribute of the ActionItemWorkList object
   *
   * @param tmp The new planWork value
   */
  public void setPlanWork(ActionPlanWork tmp) {
    this.planWork = tmp;
  }


  /**
   * Gets the linkModuleId attribute of the ActionItemWorkList object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Sets the linkModuleId attribute of the ActionItemWorkList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the ActionItemWorkList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkItemId attribute of the ActionItemWorkList object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Sets the linkItemId attribute of the ActionItemWorkList object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Sets the linkItemId attribute of the ActionItemWorkList object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   * Gets the isCurrent attribute of the ActionItemWorkList object
   *
   * @return The isCurrent value
   */
  public boolean getIsCurrent() {
    return isCurrent;
  }


  /**
   * Sets the isCurrent attribute of the ActionItemWorkList object
   *
   * @param tmp The new isCurrent value
   */
  public void setIsCurrent(boolean tmp) {
    this.isCurrent = tmp;
  }


  /**
   * Sets the isCurrent attribute of the ActionItemWorkList object
   *
   * @param tmp The new isCurrent value
   */
  public void setIsCurrent(String tmp) {
    this.isCurrent = DatabaseUtils.parseBoolean(tmp);
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

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM action_item_work aiw " +
            "LEFT JOIN action_step acs ON (aiw.action_step_id = acs.step_id) " +
            "LEFT JOIN action_phase_work apw ON (aiw.phase_work_id = apw.phase_work_id) " +
            "WHERE aiw.item_work_id > 0 ");

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
                "AND " + DatabaseUtils.toLowerCase(db) + "(acs.description) < ? ");
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
      pagedListInfo.setDefaultSort("acs.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY apw." + DatabaseUtils.addQuotes(db, "level") +
          ", aiw." + DatabaseUtils.addQuotes(db, "level") +
          ", acs.description ");
    }

    pst = prepareList(db, sqlFilter.toString(), sqlOrder.toString());
    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    
    while (rs.next()) {
      ActionItemWork actionItemWork = new ActionItemWork(rs);
      actionItemWork.setPlanWork(planWork);
      this.add(actionItemWork);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }

    if (buildLinkedObject || buildStep) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        ActionItemWork thisItem = (ActionItemWork) i.next();
        thisItem.setPlanWork(planWork);
        if (buildLinkedObject) {
          thisItem.buildLinkedObject(db);
        }
        if (this.getBuildStep()) {
          thisItem.setBuildStep(true);
          thisItem.buildStep(db);
        }
      }
    }
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
      ActionItemWork thisItem = (ActionItemWork) i.next();
      thisItem.setPlanWork(this.getPlanWork());
      thisItem.delete(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (phaseWorkId > -1) {
      sqlFilter.append("AND aiw.phase_work_id = ? ");
    }
    if (planWorkId > -1) {
      sqlFilter.append("AND aiw.phase_work_id IN (SELECT phase_work_id FROM action_phase_work WHERE plan_work_id = ? ) ");
    }
    if (actionStepId != -1) {
      sqlFilter.append("AND aiw.action_step_id = ? ");
    }
    if (linkModuleId != -1) {
      sqlFilter.append("AND aiw.link_module_id = ? ");
    }
    if (linkItemId != -1) {
      sqlFilter.append("AND aiw.link_item_id = ? ");
    }
    if (isCurrent) {
      sqlFilter.append("AND aiw.start_date IS NOT NULL AND aiw.end_date IS NOT NULL AND aiw.status_id IS NULL ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND aiw.entered > ? ");
      }
      sqlFilter.append("AND aiw.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND aiw.modified > ? ");
      sqlFilter.append("AND aiw.entered < ? ");
      sqlFilter.append("AND aiw.modified < ? ");
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
    if (phaseWorkId > -1) {
      pst.setInt(++i, phaseWorkId);
    }
    if (planWorkId > -1) {
      pst.setInt(++i, planWorkId);
    }
    if (actionStepId != -1) {
      pst.setInt(++i, actionStepId);
    }
    if (linkModuleId != -1) {
      pst.setInt(++i, linkModuleId);
    }
    if (linkItemId != -1) {
      pst.setInt(++i, linkItemId);
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
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param status Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void updateStatus(Connection db, int status) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionItemWork thisItem = (ActionItemWork) i.next();
      thisItem.setStatusId(status);
      if (thisItem.getStartDate() == null) {
        thisItem.setStartDate(
            new java.sql.Timestamp(
                new java.util.Date().getTime()));
      }
      if (thisItem.getEndDate() == null) {
        thisItem.setEndDate(
            new java.sql.Timestamp(
                new java.util.Date().getTime()));
      }
      thisItem.updateStatus(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param status Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void revertStatus(Connection db, int status) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionItemWork thisItem = (ActionItemWork) i.next();
      thisItem.setStatusId(status);
      thisItem.setStartDate((java.sql.Timestamp) null);
      thisItem.setEndDate((java.sql.Timestamp) null);
      thisItem.updateStatus(db);
    }
  }


  /**
   * Gets the phaseWorks attribute of the ActionItemWorkList object
   *
   * @param db Description of the Parameter
   * @return The phaseWorks value
   * @throws SQLException Description of the Exception
   */
  public HashMap getPhaseWorks(Connection db) throws SQLException {
    HashMap map = new HashMap();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionItemWork thisItem = (ActionItemWork) i.next();
      if (map.get(new Integer(thisItem.getPhaseWorkId())) == null) {
        ActionPhaseWork phaseWork = new ActionPhaseWork();
        phaseWork.setBuildPhase(true);
        phaseWork.queryRecord(db, thisItem.getPhaseWorkId());
        map.put(new Integer(phaseWork.getId()), phaseWork);
      }
    }
    return map;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void reset(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE action_item_work " +
            "SET start_date = ?, end_date = ?, status_id = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "WHERE item_work_id = ? ");
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionItemWork itemWork = (ActionItemWork) i.next();
      pst.setNull(1, java.sql.Types.DATE);
      pst.setNull(2, java.sql.Types.DATE);
      pst.setNull(3, java.sql.Types.INTEGER);
      pst.setInt(4, itemWork.getId());
      pst.executeUpdate();
    }
    pst.close();
  }


  /**
   * Initialize the steps of the random phase.
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void startRandomSteps(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionItemWork thisItem = (ActionItemWork) i.next();
      if (thisItem.getStartDate() == null) {
        thisItem.setStartDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        thisItem.setStatusId(ActionPlanWork.INCOMPLETE);
        thisItem.update(db);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void resetAttachment(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionItemWork thisItem = (ActionItemWork) i.next();
      if (thisItem.getLinkItemId() != -1) {
        thisItem.setLinkItemId(-1);
        thisItem.updateAttachment(db);
      }
    }
  }


  /**
   * Gets the indexById attribute of the ActionItemWorkList object
   *
   * @param itemId Description of the Parameter
   * @return The indexById value
   */
  public int getIndexById(int itemId) {
    int result = -1;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionItemWork thisItem = (ActionItemWork) i.next();
      if (thisItem.getId() == itemId) {
        result = this.indexOf(thisItem);
        break;
      }
    }
    return result;
  }
  
  /**
   *  Gets the object attribute of the ActionItemWorkList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public ActionItemWork getObject(ResultSet rs) throws SQLException {
  	ActionItemWork obj = new ActionItemWork(rs);
    return obj;
  }
  
  public PreparedStatement prepareList(Connection db, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append(" SELECT ");
    }
    sqlSelect.append(
        "aiw.*, " +
            "acs.description, acs.estimated_duration, acs.parent_id, acs.action_id, " +
            "acs.permission_type, acs.allow_skip_to_here, acs.action_required, acs.label, acs.target_relationship, acs.allow_update " +
            "FROM action_item_work aiw " +
            "LEFT JOIN action_step acs ON (aiw.action_step_id = acs.step_id) " +
            "LEFT JOIN action_phase_work apw ON (aiw.phase_work_id = apw.phase_work_id) " +
            "WHERE aiw.item_work_id > 0 ");
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
   * @exception  SQLException  Description of the Exception
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
     return prepareList(db, "", "");
  }

  
}

