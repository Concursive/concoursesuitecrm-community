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
 * @author partha
 * @version $Id: TicketCategoryPlanMapList.java,v 1.1.2.2 2005/10/17 21:11:22
 *          partha Exp $
 * @created October 17, 2005
 */
public class TicketCategoryPlanMapList extends ArrayList  implements SyncableList {
  PagedListInfo pagedListInfo = null;
  protected int id = -1;
  protected int categoryId = -1;
  protected int planId = -1;

  public final static String tableName = "ticket_category_plan_map";
  public final static String uniqueField = "map_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  /**
   * Constructor for the TicketCategoryPlanMapList object
   */
  public TicketCategoryPlanMapList() {
  }

  /**
   * Sets the lastAnchor attribute of the TicketCategoryPlanMapList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the TicketCategoryPlanMapList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the TicketCategoryPlanMapList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the TicketCategoryPlanMapList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the TicketCategoryPlanMapList object
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
   * Gets the tableName attribute of the TicketCategoryPlanMapList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the TicketCategoryPlanMapList object
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
            " FROM ticket_category_plan_map tpm " +
            " WHERE tpm.map_id > -1 ");
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
      pagedListInfo.setDefaultSort("tpm.map_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY tpm.map_id ");
    }
    rs = queryList(db, pst, sqlFilter.toString(), sqlOrder.toString());
    while (rs.next()) {
      TicketCategoryPlanMap thisMap = new TicketCategoryPlanMap(rs);
      this.add(thisMap);
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
      sqlFilter.append("AND tpm.map_id = ? ");
    }
    if (planId > -1) {
      sqlFilter.append("AND tpm.plan_id = ? ");
    }
    if (categoryId > -1) {
      sqlFilter.append("AND tpm.category_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND tpm.entered > ? ");
      }
      sqlFilter.append("AND tpm.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND tpm.modified > ? ");
      sqlFilter.append("AND tpm.entered < ? ");
      sqlFilter.append("AND tpm.modified < ? ");
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
   * @param db           Description of the Parameter
   * @param draftMapList Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void parsePlans(Connection db, TicketCategoryDraftPlanMapList draftMapList) throws SQLException {
    if (draftMapList.size() <= 0) {
      return;
    }
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      TicketCategoryPlanMap planMap = (TicketCategoryPlanMap) iter.next();
      TicketCategoryDraftPlanMap draftPlanMap = draftMapList.getMapByPlanId(planMap.getPlanId());
      if (draftPlanMap != null) {
        boolean removed = draftMapList.removeMapById(draftPlanMap.getId());
      } else {
        planMap.delete(db);
        iter.remove();
      }
    }

    iter = (Iterator) draftMapList.iterator();
    while (iter.hasNext()) {
      TicketCategoryDraftPlanMap draftMap = (TicketCategoryDraftPlanMap) iter.next();
      TicketCategoryPlanMap planMap = new TicketCategoryPlanMap();
      planMap.setPlanId(draftMap.getPlanId());
      planMap.setCategoryId(this.getCategoryId());
      planMap.insert(db);
    }
  }


  /**
   * Gets the mapByPlanId attribute of the TicketCategoryPlanMapList object
   *
   * @param pId Description of the Parameter
   * @return The mapByPlanId value
   */
  public TicketCategoryPlanMap getMapByPlanId(int pId) {
    TicketCategoryPlanMap result = null;
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      TicketCategoryPlanMap map = (TicketCategoryPlanMap) iter.next();
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
      TicketCategoryPlanMap map = (TicketCategoryPlanMap) iter.next();
      if (map.getId() == mapId) {
        result = this.remove(map);
        iter.remove();
        break;
      }
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param siteId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public TicketCategoryPlanMapList clonePlanMapList(Connection db, int siteId) throws SQLException {
    TicketCategoryPlanMapList result = new TicketCategoryPlanMapList();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      TicketCategoryPlanMap map = (TicketCategoryPlanMap) iter.next();
      TicketCategoryPlanMap newMap = this.getMapByPlanId(map.getPlanId());
      newMap = map.clonePlanMap(db, newMap, siteId);
      if (newMap != null) {
        result.add(newMap);
      }
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean parse(Connection db) throws SQLException {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      TicketCategoryPlanMap map = (TicketCategoryPlanMap) iter.next();
      if (map.getId() > -1) {
        map.update(db);
      } else {
        map.setCategoryId(this.getCategoryId());
        map.insert(db);
      }
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      TicketCategoryPlanMap map = (TicketCategoryPlanMap) iter.next();
      map.delete(db);
      iter.remove();
    }
    return true;
  }


  /*
   *  Get and Set methods
   */
  /**
   * Gets the pagedListInfo attribute of the TicketCategoryPlanMapList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the TicketCategoryPlanMapList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the id attribute of the TicketCategoryPlanMapList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the TicketCategoryPlanMapList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the TicketCategoryPlanMapList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the categoryId attribute of the TicketCategoryPlanMapList object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the categoryId attribute of the TicketCategoryPlanMapList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the TicketCategoryPlanMapList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Gets the planId attribute of the TicketCategoryPlanMapList object
   *
   * @return The planId value
   */
  public int getPlanId() {
    return planId;
  }


  /**
   * Sets the planId attribute of the TicketCategoryPlanMapList object
   *
   * @param tmp The new planId value
   */
  public void setPlanId(int tmp) {
    this.planId = tmp;
  }


  /**
   * Sets the planId attribute of the TicketCategoryPlanMapList object
   *
   * @param tmp The new planId value
   */
  public void setPlanId(String tmp) {
    this.planId = Integer.parseInt(tmp);
  }
  
  /**
   *  Gets the object attribute of the TicketCategoryPlanMapList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public TicketCategoryPlanMap getObject(ResultSet rs) throws SQLException {
  	TicketCategoryPlanMap obj = new TicketCategoryPlanMap(rs);
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
        " tpm.* " +
            " FROM ticket_category_plan_map tpm " +
            " WHERE tpm.map_id > -1 ");
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


