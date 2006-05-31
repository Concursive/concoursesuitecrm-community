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

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.troubletickets.base.*;

import java.sql.*;
import java.util.*;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    October 17, 2005
 * @version    $Id$
 */
public class TicketCategoryDraftPlanMapList extends ArrayList {
  PagedListInfo pagedListInfo = null;
  protected int id = -1;
  protected int categoryId = -1;
  protected int planId = -1;
  protected boolean buildPlan = false;


  /**
   *  Constructor for the TicketCategoryDraftPlanMapList object
   */
  public TicketCategoryDraftPlanMapList() { }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
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
        " FROM ticket_category_draft_plan_map tdpm " +
        " WHERE tdpm.map_id > -1 ");
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
      pagedListInfo.setDefaultSort("tdpm.map_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY tdpm.map_id ");
    }
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

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      TicketCategoryDraftPlanMap thisMap = new TicketCategoryDraftPlanMap(rs);
      this.add(thisMap);
    }
    rs.close();
    pst.close();
    if (buildPlan) {
      Iterator iter = (Iterator) this.iterator();
      while (iter.hasNext()) {
        TicketCategoryDraftPlanMap planMap = (TicketCategoryDraftPlanMap) iter.next();
        planMap.buildActionPlan(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of the Parameter
   * @param  db         Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter, Connection db) {
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
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
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
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  map               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean parsePlans(Connection db, HashMap map) throws SQLException {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      TicketCategoryDraftPlanMap planMap = (TicketCategoryDraftPlanMap) iter.next();
      if (map.get(new Integer(planMap.getPlanId())) != null) {
        map.remove(new Integer(planMap.getPlanId()));
      } else {
        planMap.delete(db);
        iter.remove();
      }
    }
    iter = (Iterator) map.keySet().iterator();
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
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  mapList           Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void parseDraftPlans(Connection db, TicketCategoryPlanMapList mapList) throws SQLException {
    try {
      if (mapList.size() <= 0) {
        return;
      }
      Iterator iter = (Iterator) this.iterator();
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

      Iterator iter1 = (Iterator) mapList.iterator();
      while (iter1.hasNext()) {
        Object map1 = (Object) iter1.next();
        TicketCategoryPlanMap map = (TicketCategoryPlanMap) map1;
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
   *  Gets the mapByPlanId attribute of the TicketCategoryDraftPlanMapList
   *  object
   *
   * @param  pId  Description of the Parameter
   * @return      The mapByPlanId value
   */
  public TicketCategoryDraftPlanMap getMapByPlanId(int pId) {
    TicketCategoryDraftPlanMap result = null;
    Iterator iter = (Iterator) this.iterator();
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
   *  Description of the Method
   *
   * @param  mapId  Description of the Parameter
   * @return        Description of the Return Value
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
   *  Gets the pagedListInfo attribute of the TicketCategoryDraftPlanMapList
   *  object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the pagedListInfo attribute of the TicketCategoryDraftPlanMapList
   *  object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the id attribute of the TicketCategoryDraftPlanMapList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the categoryId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Sets the categoryId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the planId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @return    The planId value
   */
  public int getPlanId() {
    return planId;
  }


  /**
   *  Sets the planId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param  tmp  The new planId value
   */
  public void setPlanId(int tmp) {
    this.planId = tmp;
  }


  /**
   *  Sets the planId attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param  tmp  The new planId value
   */
  public void setPlanId(String tmp) {
    this.planId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the buildPlan attribute of the TicketCategoryDraftPlanMapList object
   *
   * @return    The buildPlan value
   */
  public boolean getBuildPlan() {
    return buildPlan;
  }


  /**
   *  Sets the buildPlan attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param  tmp  The new buildPlan value
   */
  public void setBuildPlan(boolean tmp) {
    this.buildPlan = tmp;
  }


  /**
   *  Sets the buildPlan attribute of the TicketCategoryDraftPlanMapList object
   *
   * @param  tmp  The new buildPlan value
   */
  public void setBuildPlan(String tmp) {
    this.buildPlan = DatabaseUtils.parseBoolean(tmp);
  }
}


