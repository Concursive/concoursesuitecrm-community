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
package org.aspcfs.modules.troubletickets.base;

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
 * @version $Id$
 * @created October 17, 2005
 */
public class TicketCategoryDraftPlanMapList extends ArrayList  implements SyncableList {
  PagedListInfo pagedListInfo = null;
  protected int id = -1;
  protected int categoryId = -1;
  protected int planId = -1;
  protected boolean buildPlan = false;

  public final static String tableName = "ticket_category_draft_plan_map";
  public final static String uniqueField = "map_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  /**
   * Constructor for the TicketCategoryDraftPlanMapList object
   */
  public TicketCategoryDraftPlanMapList() {
  }

  /**
   * Sets the lastAnchor attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the TicketCategoryDraftPlanMapList object
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
   * Gets the tableName attribute of the TicketCategoryDraftPlanMapList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the TicketCategoryDraftPlanMapList object
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
            " FROM ticket_category_draft_plan_map tdpm " +
            " WHERE tdpm.map_id > -1 ");
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
      pagedListInfo.setDefaultSort("tdpm.map_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY tdpm.map_id ");
    }
    pst = prepareList(db, sqlFilter.toString(), sqlOrder.toString());
    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      TicketCategoryDraftPlanMap thisMap = new TicketCategoryDraftPlanMap(rs);
      this.add(thisMap);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    if (buildPlan) {
      Iterator iter = (Iterator) this.iterator();
      while (iter.hasNext()) {
        TicketCategoryDraftPlanMap planMap = (TicketCategoryDraftPlanMap) iter.next();
        planMap.buildActionPlan(db);
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
      sqlFilter.append("AND tdpm.map_id = ? ");
    }
    if (planId > -1) {
      sqlFilter.append("AND tdpm.plan_id = ? ");
    }
    if (categoryId > -1) {
      sqlFilter.append("AND tdpm.category_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND tdpm.entered > ? ");
      }
      sqlFilter.append("AND tdpm.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND tdpm.modified > ? ");
      sqlFilter.append("AND tdpm.entered < ? ");
      sqlFilter.append("AND tdpm.modified < ? ");
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
    if (planId > -1) {
      pst.setInt(++i, planId);
    }
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
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
   * @param db  Description of the Parameter
   * @param map Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean parsePlans(Connection db, HashMap map) throws SQLException {
    Iterator iter = this.iterator();
    while (iter.hasNext()) {
      TicketCategoryDraftPlanMap planMap = (TicketCategoryDraftPlanMap) iter.next();
      if (map.get(new Integer(planMap.getPlanId())) != null) {
        map.remove(new Integer(planMap.getPlanId()));
      } else {
        planMap.delete(db);
        iter.remove();
      }
    }
    iter = map.keySet().iterator();
    while (iter.hasNext()) {
      Integer key = (Integer) iter.next();
      TicketCategoryDraftPlanMap planMap = new TicketCategoryDraftPlanMap();
      planMap.setCategoryId(this.getCategoryId());
      planMap.setPlanId(key.intValue());
      planMap.insert(db);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param mapList Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void parseDraftPlans(Connection db, TicketCategoryPlanMapList mapList) throws SQLException {
    try {
      if (mapList.size() <= 0) {
        return;
      }
      Iterator iter = this.iterator();
      while (iter.hasNext()) {
        TicketCategoryDraftPlanMap draftPlanMap = (TicketCategoryDraftPlanMap) iter.next();
        TicketCategoryPlanMap planMap = mapList.getMapByPlanId(draftPlanMap.getPlanId());
        if (planMap != null) {
          boolean removed = mapList.removeMapById(planMap.getId());
        } else {
          draftPlanMap.delete(db);
          iter.remove();
        }
      }

      Iterator iter1 = mapList.iterator();
      while (iter1.hasNext()) {
        TicketCategoryPlanMap map = (TicketCategoryPlanMap) iter1.next();
        TicketCategoryDraftPlanMap draftPlanMap = new TicketCategoryDraftPlanMap();
        draftPlanMap.setPlanId(map.getPlanId());
        draftPlanMap.setCategoryId(this.getCategoryId());
        draftPlanMap.insert(db);
        this.add(draftPlanMap);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new SQLException(e.getMessage());
    }
  }


  /**
   * Gets the mapByPlanId attribute of the TicketCategoryDraftPlanMapList
   * object
   *
   * @param pId Description of the Parameter
   * @return The mapByPlanId value
   */
  public TicketCategoryDraftPlanMap getMapByPlanId(int pId) {
    TicketCategoryDraftPlanMap result = null;
    Iterator iter = this.iterator();
    while (iter.hasNext()) {
      TicketCategoryDraftPlanMap map = (TicketCategoryDraftPlanMap) iter.next();
      if (map.getPlanId() == pId) {
        result = map;
        break;
      }
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param mapId Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean removeMapById(int mapId) {
    boolean result = false;
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      TicketCategoryDraftPlanMap map = (TicketCategoryDraftPlanMap) iter.next();
      if (map.getId() == mapId) {
        iter.remove();
        result = this.remove(map);
        break;
      }
    }
    return result;
  }


  /*
   *  Get and Set methods
   */
  /**
   * Gets the pagedListInfo attribute of the TicketCategoryDraftPlanMapList
   * object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the TicketCategoryDraftPlanMapList
   * object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the id attribute of the TicketCategoryDraftPlanMapList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the categoryId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the categoryId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Gets the planId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @return The planId value
   */
  public int getPlanId() {
    return planId;
  }


  /**
   * Sets the planId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new planId value
   */
  public void setPlanId(int tmp) {
    this.planId = tmp;
  }


  /**
   * Sets the planId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new planId value
   */
  public void setPlanId(String tmp) {
    this.planId = Integer.parseInt(tmp);
  }


  /**
   * Gets the buildPlan attribute of the TicketCategoryDraftPlanMapList object
   *
   * @return The buildPlan value
   */
  public boolean getBuildPlan() {
    return buildPlan;
  }


  /**
   * Sets the buildPlan attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new buildPlan value
   */
  public void setBuildPlan(boolean tmp) {
    this.buildPlan = tmp;
  }


  /**
   * Sets the buildPlan attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param tmp The new buildPlan value
   */
  public void setBuildPlan(String tmp) {
    this.buildPlan = DatabaseUtils.parseBoolean(tmp);
  }
  
  /**
   *  Gets the object attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryDraftPlanMap getObject(ResultSet rs) throws SQLException {
  	TicketCategoryDraftPlanMap obj = new TicketCategoryDraftPlanMap(rs);
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
        " tdpm.* " +
            " FROM ticket_category_draft_plan_map tdpm " +
            " WHERE tdpm.map_id > -1 ");
    if(sqlFilter == null || sqlFilter.length() == 0){
    	StringBuffer buff = new StringBuffer();
    	createFilter(db, buff);
    	sqlFilter = buff.toString();
    }
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter + sqlOrder);
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


