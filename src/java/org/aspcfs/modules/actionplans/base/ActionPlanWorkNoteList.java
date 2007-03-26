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
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created September 22, 2005
 */
public class ActionPlanWorkNoteList extends ArrayList  implements SyncableList {
  private PagedListInfo pagedListInfo = null;
  private int planWorkId = -1;
  private int orgId = -1;

  public final static String tableName = "action_plan_work_notes";
  public final static String uniqueField = "note_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  /**
   * Gets the orgId attribute of the ActionPlanWorkNoteList object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Sets the orgId attribute of the ActionPlanWorkNoteList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the ActionPlanWorkNoteList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the lastAnchor attribute of the ActionPlanWorkNoteList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionPlanWorkNoteList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionPlanWorkNoteList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionPlanWorkNoteList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionPlanWorkNoteList object
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
   * Gets the tableName attribute of the ActionPlanWorkNoteList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionPlanWorkNoteList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the pagedListInfo attribute of the ActionPlanWorkNoteList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the ActionPlanWorkNoteList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the planWorkId attribute of the ActionPlanWorkNoteList object
   *
   * @return The planWorkId value
   */
  public int getPlanWorkId() {
    return planWorkId;
  }


  /**
   * Sets the planWorkId attribute of the ActionPlanWorkNoteList object
   *
   * @param tmp The new planWorkId value
   */
  public void setPlanWorkId(int tmp) {
    this.planWorkId = tmp;
  }


  /**
   * Sets the planWorkId attribute of the ActionPlanWorkNoteList object
   *
   * @param tmp The new planWorkId value
   */
  public void setPlanWorkId(String tmp) {
    this.planWorkId = Integer.parseInt(tmp);
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
            "FROM action_plan_work_notes apwn " +
            "WHERE apwn.note_id > 0 ");

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
                "AND " + DatabaseUtils.toLowerCase(db) + "(apwn.description) < ? ");
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
      pagedListInfo.setDefaultSort("apwn.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY submitted, description ");
    }
    
    rs = queryList(db, pst, sqlFilter.toString(), sqlOrder.toString());
    
    while (rs.next()) {
      ActionPlanWorkNote thisNote = new ActionPlanWorkNote(rs);
      this.add(thisNote);
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
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (planWorkId > -1) {
      sqlFilter.append("AND apwn.plan_work_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND apwn.entered > ? ");
      }
      sqlFilter.append("AND apwn.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND apwn.modified > ? ");
      sqlFilter.append("AND apwn.entered < ? ");
      sqlFilter.append("AND apwn.modified < ? ");
    }
    if (orgId > -1) {
      sqlFilter.append("AND apwn.plan_work_id IN " +
          "   (SELECT plan_work_id FROM action_plan_work " +
          "    WHERE link_module_id IN (SELECT map_id FROM action_plan_constants WHERE constant_id = ?) " +
          "    AND link_item_id = ? ) ");
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
    if (planWorkId != -1) {
      pst.setInt(++i, planWorkId);
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
    if (orgId != -1) {
      pst.setInt(++i, ActionPlan.ACCOUNTS);
      pst.setInt(++i, orgId);
    }
    return i;
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
      ActionPlanWorkNote thisNote = (ActionPlanWorkNote) i.next();
      thisNote.delete(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
  
  /**
   *  Gets the object attribute of the ActionPlanWorkNoteList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public ActionPlanWorkNote getObject(ResultSet rs) throws SQLException {
  	ActionPlanWorkNote obj = new ActionPlanWorkNote(rs);
    return obj;
  }
  
  public ResultSet queryList(Connection db, PreparedStatement pst, String sqlFilter, String sqlOrder) throws SQLException {
  	StringBuffer sqlSelect = new StringBuffer();
  	//Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append(" SELECT ");
    }
    sqlSelect.append(
        "apwn.* " +
            "FROM action_plan_work_notes apwn " +
            "WHERE apwn.note_id > 0 ");
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

