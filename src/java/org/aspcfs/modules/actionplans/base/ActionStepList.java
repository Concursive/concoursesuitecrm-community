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
 * @author partha
 * @version $Id: ActionStepList.java,v 1.1.2.2 2005/08/19 03:32:06 ananth Exp
 *          $
 * @created August 17, 2005
 */
public class ActionStepList extends ArrayList  implements SyncableList {
  public final static String tableName = "action_step";
  public final static String uniqueField = "step_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int parentId = -1;
  private int phaseId = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private int categoryId = -1;
  private int campaignId = -1;

  private boolean buildCompleteStepList = false;


  /**
   * Constructor for the ActionStepList object
   */
  public ActionStepList() {
  }

  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionItemList object
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
   * Gets the tableName attribute of the ActionItemList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionItemList object
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
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
            " FROM action_step astp " +
            " LEFT JOIN lookup_duration_type ldt ON (astp.duration_type_id = ldt.code) " +
            " LEFT JOIN custom_field_category cfc ON (astp.category_id = cfc.category_id) " +
            " LEFT JOIN custom_field_info cfi ON (astp.field_id = cfi.field_id) " +
            " LEFT JOIN " + DatabaseUtils.addQuotes(db, "role") + " r ON (astp.role_id = r.role_id) " +
            " LEFT JOIN lookup_department dpt ON (astp.department_id = dpt.code) " +
            " WHERE astp.step_id > -1 ");
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

      //Determine column to sort by
      pagedListInfo.setDefaultSort("astp.entered", null);
      boolean flag = true;
      sqlOrder.append(
          "ORDER BY astp.entered" + (pagedListInfo.getSortOrder() != null ? " DESC " : " "));
    } else {
      sqlOrder.append("ORDER BY astp.entered ");
    }
    rs = queryList(db, pst, sqlFilter.toString(), sqlOrder.toString());
    while (rs.next()) {
      ActionStep thisPlan = new ActionStep(rs);
      this.add(thisPlan);
    }
    rs.close();
    if (pst != null) { 
      pst.close();
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
      sqlFilter.append("AND astp.step_id = ? ");
    }
    if (parentId == 0) {
      sqlFilter.append("AND astp.parent_id IS NULL ");
    } else if (parentId > -1) {
      sqlFilter.append("AND astp.parent_id = ? ");
    }
    if (phaseId > -1) {
      sqlFilter.append("AND astp.phase_id = ? ");
    }
    if (categoryId > -1) {
      sqlFilter.append("AND cfc.category_id = ? ");
    }
    if (campaignId > -1) {
      sqlFilter.append("AND astp.campaign_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND astp.entered > ? ");
      }
      sqlFilter.append("AND astp.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND astp.modified > ? ");
      sqlFilter.append("AND astp.entered < ? ");
      sqlFilter.append("AND astp.modified < ? ");
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
    if (parentId > 0) {
      DatabaseUtils.setInt(pst, ++i, parentId);
    }
    if (phaseId > -1) {
      pst.setInt(++i, phaseId);
    }
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    if (campaignId > -1) {
      pst.setInt(++i, campaignId);
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
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    boolean result = false;
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ActionStep step = (ActionStep) iterator.next();
      result = step.delete(db) && result;
    }
    return result;
  }


  /**
   * Gets the lastStepId attribute of the ActionStepList object
   *
   * @return The lastStepId value
   */
  public int getLastStepId() {
    int result = -1;
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ActionStep step = (ActionStep) iterator.next();
      if (!iterator.hasNext()) {
        result = step.getId();
      }
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasUserGroupStep() {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ActionStep step = (ActionStep) iterator.next();
      if (step.getPermissionType() == ActionStep.USER_GROUP) {
        return true;
      }
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void resetFolderInformation(Connection db) throws SQLException {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ActionStep step = (ActionStep) iterator.next();
      step.queryRecord(db, step.getId());
      ActionItemWorkList workList = new ActionItemWorkList();
      workList.setActionStepId(step.getId());
      workList.buildList(db);
      workList.resetAttachment(db);
      step.setCustomFieldCategoryId(-1);
      step.update(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void resetCampaignInformation(Connection db) throws SQLException {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ActionStep step = (ActionStep) iterator.next();
      step.queryRecord(db, step.getId());
      ActionItemWorkList workList = new ActionItemWorkList();
      workList.setActionStepId(step.getId());
      workList.buildList(db);
      workList.resetAttachment(db);
      step.setCampaignId(-1);
      step.setActionId(ActionStep.ATTACH_NOTHING);
      step.setAllowDuplicateRecipient(false);
      step.update(db);
    }
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public ActionStepList reorder() {
    HashMap positionMap = this.getStepIdsAsHashMap();
    HashMap steps = this.getStepsByParentAsHashMap();
    ActionStepList reorderedList = new ActionStepList();
    reorderedList.setPhaseId(this.getPhaseId());
    String tempStepId = "0";
    for (int i = 0; i < this.size(); i++) {
      ActionStep thisStep = (ActionStep) steps.get(tempStepId);
      if (thisStep != null) {
        tempStepId = (String) positionMap.get(tempStepId);
        reorderedList.add(thisStep);
      }
    }
    return reorderedList;
  }


  /**
   * Description of the Method
   *
   * @param list Description of the Parameter
   * @return Description of the Return Value
   */
  public String printArray(ArrayList list) {
    StringBuffer str = new StringBuffer();
    for (int i = 0; i < list.size(); i++) {
      str.append("\nActionStepList:: list[" + i + "] = " + list.get(i));
    }
    return str.toString();
  }


  /**
   * Gets the stepIdsAsHashMap attribute of the ActionStepList object
   *
   * @return The stepIdsAsHashMap value
   */
  public HashMap getStepIdsAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      ActionStep step = (ActionStep) iter.next();
      map.put(String.valueOf(step.getParentId()), String.valueOf(step.getId()));
    }
    return map;
  }


  /**
   * Gets the stepsByParentAsHashMap attribute of the ActionStepList object
   *
   * @return The stepsByParentAsHashMap value
   */
  public HashMap getStepsByParentAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      ActionStep step = (ActionStep) iter.next();
      map.put(String.valueOf(step.getParentId()), step);
    }
    return map;
  }


  /**
   * Gets the stepsAsHashMap attribute of the ActionStepList object
   *
   * @return The stepsAsHashMap value
   */
  public HashMap getStepsAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      ActionStep step = (ActionStep) iter.next();
      map.put(String.valueOf(step.getId()), step);
    }
    return map;
  }


  /**
   * Gets the stepParentsAsHashMap attribute of the ActionStepList object
   *
   * @return The stepParentsAsHashMap value
   */
  public HashMap getStepParentsAsHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    for (int i = 1; iter.hasNext(); i++) {
      ActionStep step = (ActionStep) iter.next();
      map.put(String.valueOf(step.getId()), String.valueOf(i));
    }
    return map;
  }


  /**
   * Gets the stepById attribute of the ActionStepList object
   *
   * @param id Description of the Parameter
   * @return The stepById value
   */
  public ActionStep getStepById(int id) {
    ActionStep result = null;
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      ActionStep step = (ActionStep) iter.next();
      if (step.getId() == id) {
        result = step;
        break;
      }
    }
    return result;
  }


  /*
   *  Get and Set methods
   */
  /**
   * Gets the pagedListInfo attribute of the ActionStepList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the ActionStepList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the id attribute of the ActionStepList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the ActionStepList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ActionStepList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the parentId attribute of the ActionStepList object
   *
   * @return The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   * Sets the parentId attribute of the ActionStepList object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   * Sets the parentId attribute of the ActionStepList object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   * Gets the phaseId attribute of the ActionStepList object
   *
   * @return The phaseId value
   */
  public int getPhaseId() {
    return phaseId;
  }


  /**
   * Sets the phaseId attribute of the ActionStepList object
   *
   * @param tmp The new phaseId value
   */
  public void setPhaseId(int tmp) {
    this.phaseId = tmp;
  }


  /**
   * Sets the phaseId attribute of the ActionStepList object
   *
   * @param tmp The new phaseId value
   */
  public void setPhaseId(String tmp) {
    this.phaseId = Integer.parseInt(tmp);
  }


  /**
   * Gets the enteredBy attribute of the ActionStepList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Sets the enteredBy attribute of the ActionStepList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the ActionStepList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the modifiedBy attribute of the ActionStepList object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Sets the modifiedBy attribute of the ActionStepList object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the ActionStepList object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the buildCompleteStepList attribute of the ActionStepList object
   *
   * @return The buildCompleteStepList value
   */
  public boolean getBuildCompleteStepList() {
    return buildCompleteStepList;
  }


  /**
   * Sets the buildCompleteStepList attribute of the ActionStepList object
   *
   * @param tmp The new buildCompleteStepList value
   */
  public void setBuildCompleteStepList(boolean tmp) {
    this.buildCompleteStepList = tmp;
  }


  /**
   * Sets the buildCompleteStepList attribute of the ActionStepList object
   *
   * @param tmp The new buildCompleteStepList value
   */
  public void setBuildCompleteStepList(String tmp) {
    this.buildCompleteStepList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the categoryId attribute of the ActionStepList object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the categoryId attribute of the ActionStepList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the ActionStepList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Gets the campaignId attribute of the ActionStepList object
   *
   * @return The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   * Sets the campaignId attribute of the ActionStepList object
   *
   * @param tmp The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   * Sets the campaignId attribute of the ActionStepList object
   *
   * @param tmp The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }
  
  /**
   *  Gets the object attribute of the ActionStepList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public ActionStep getObject(ResultSet rs) throws SQLException {
  	ActionStep obj = new ActionStep(rs);
    return obj;
  }
  
  public ResultSet queryList(Connection db, PreparedStatement pst, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " astp.*, " +
            " ldt.description AS duration, ug.group_name as groupname " +
            " FROM action_step astp " +
            " LEFT JOIN lookup_duration_type ldt ON (astp.duration_type_id = ldt.code) " +
            " LEFT JOIN custom_field_category cfc ON (astp.category_id = cfc.category_id) " +
            " LEFT JOIN custom_field_info cfi ON (astp.field_id = cfi.field_id) " +
            " LEFT JOIN " + DatabaseUtils.addQuotes(db, "role") + " r ON (astp.role_id = r.role_id) " +
            " LEFT JOIN lookup_department dpt ON (astp.department_id = dpt.code) " +
            " LEFT JOIN user_group ug ON (astp.group_id = ug.group_id) " +
            " WHERE astp.step_id > -1 ");
    if(sqlFilter == null || sqlFilter.length() == 0){
    	StringBuffer buff = new StringBuffer();
    	createFilter(db, buff);
    	sqlFilter = buff.toString();
    }
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter + sqlOrder);
    prepareFilter(pst);
    return DatabaseUtils.executeQuery(db, pst, pagedListInfo);
  }
  
  /**
   * @param  db                Description of the Parameter
   * @param  pst               Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
  	return queryList(db, pst, "", "");
  }
  
}

