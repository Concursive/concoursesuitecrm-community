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
package org.aspcfs.modules.communications.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.admin.base.UserGroupList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    March 10, 2006
 * @version    $Id: Exp$
 */
public class CampaignUserGroupMapList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int campaignId = -1;
  private int userGroupId = -1;


  /**
   *  Constructor for the CampaignUserGroupMapList object
   */
  public CampaignUserGroupMapList() { }


  /**
   *  Constructor for the CampaignUserGroupMapList object
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public CampaignUserGroupMapList(Connection db) throws SQLException {
    buildList(db);
  }


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

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM campaign_group_map cgm " +
        "LEFT JOIN user_group ug ON (cgm.user_group_id = ug.group_id) " +
        "WHERE map_id > -1 ");
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
      pagedListInfo.setDefaultSort("cgm.map_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY cgm.map_id ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "cgm.*, ug.group_name AS name " +
        "FROM campaign_group_map cgm " +
        "LEFT JOIN user_group ug ON (cgm.user_group_id = ug.group_id) " +
        "WHERE map_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      CampaignUserGroupMap thisCampaignUserGroupMap = new CampaignUserGroupMap(rs);
      this.add(thisCampaignUserGroupMap);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter         Description of the Parameter
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
    if (id > -1) {
      sqlFilter.append("AND cgm.map_id = ? ");
    }
    if (campaignId > -1) {
      sqlFilter.append("AND cgm.campaign_id = ? ");
    }
    if (userGroupId > -1) {
      sqlFilter.append("AND cgm.user_group_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (campaignId > -1) {
      pst.setInt(++i, campaignId);
    }
    if (userGroupId > -1) {
      pst.setInt(++i, userGroupId);
    }
    return i;
  }


  /**
   *  Sets the elements attribute of the CampaignUserGroupMapList object
   *
   * @param  groups  The new elements value
   */
  public void setElements(String groups) {
    StringTokenizer str = new StringTokenizer(groups, "|");
    while (str.hasMoreTokens()) {
      CampaignUserGroupMap groupMap = new CampaignUserGroupMap();
      groupMap.setCampaignId(this.getCampaignId());
      String temp = str.nextToken();
      if (temp != null && !"".equals(temp)) {
        String[] elements = temp.split(",");
        if (elements.length > 0) {
          groupMap.setUserGroupId(elements[0]);
          groupMap.setGroupName(elements[1]);
          this.add(groupMap);
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  map  Description of the Parameter
   */
  public void updateElements(HashMap map) {
    HashMap mapOfElements = this.createMapOfElements();
    Iterator iter = (Iterator) map.keySet().iterator();
    while (iter.hasNext()) {
      Integer key = (Integer) iter.next();
      if (mapOfElements.get(key) == null) {
        CampaignUserGroupMap groupMap = new CampaignUserGroupMap();
        groupMap.setCampaignId(this.getCampaignId());
        groupMap.setUserGroupId(key.intValue());
        groupMap.setGroupName((String) map.get(key));
        this.add(groupMap);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      CampaignUserGroupMap groupMap = (CampaignUserGroupMap) iter.next();
      if (groupMap.getCampaignId() == -1) {
        groupMap.setCampaignId(this.getCampaignId());
      }
      groupMap.parse(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      CampaignUserGroupMap groupMap = (CampaignUserGroupMap) iter.next();
      groupMap.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public HashMap createMapOfElements() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      CampaignUserGroupMap groupMap = (CampaignUserGroupMap) iter.next();
      map.put(new Integer(groupMap.getUserGroupId()), groupMap);
    }
    return map;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  userId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean checkUserAccess(Connection db, int userId, int siteId) throws SQLException {
    UserGroupList userGroups = new UserGroupList();
    userGroups.setGetEnabledForUser(Constants.TRUE, userId);
    userGroups.setCampaignId(this.getCampaignId());
    userGroups.setExclusiveToSite(true);
    userGroups.setSiteId(siteId);
    userGroups.buildList(db);
    return (userGroups.size() > 0);
  }


  /*
   *  Get and Set methods
   */
  /**
   *  Gets the pagedListInfo attribute of the CampaignUserGroupMapList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the pagedListInfo attribute of the CampaignUserGroupMapList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the id attribute of the CampaignUserGroupMapList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the CampaignUserGroupMapList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the CampaignUserGroupMapList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the campaignId attribute of the CampaignUserGroupMapList object
   *
   * @return    The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   *  Sets the campaignId attribute of the CampaignUserGroupMapList object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Sets the campaignId attribute of the CampaignUserGroupMapList object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the userGroupId attribute of the CampaignUserGroupMapList object
   *
   * @return    The userGroupId value
   */
  public int getUserGroupId() {
    return userGroupId;
  }


  /**
   *  Sets the userGroupId attribute of the CampaignUserGroupMapList object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(int tmp) {
    this.userGroupId = tmp;
  }


  /**
   *  Sets the userGroupId attribute of the CampaignUserGroupMapList object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(String tmp) {
    this.userGroupId = Integer.parseInt(tmp);
  }
}

