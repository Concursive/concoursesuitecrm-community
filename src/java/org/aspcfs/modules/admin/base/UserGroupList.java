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
package org.aspcfs.modules.admin.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.troubletickets.base.TicketCategoryDraft;
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
 * @version $Id: UserGroupList.java,v 1.1.2.3 2005/09/29 21:09:01 partha Exp
 *          $
 * @created September 23, 2005
 */
public class UserGroupList extends ArrayList implements SyncableList{
  PagedListInfo pagedListInfo = null;
  protected int groupId = -1;
  protected int userId = -1;
  protected int campaignId = -1;
  protected int enabled = Constants.UNDEFINED;
  protected boolean buildResources = false;
  protected boolean getEnabledForUser = false;
  protected boolean getEnabledForCampaign = false;
  private String labelNoneSelected = null;
  private int siteId = -1;
  private boolean includeAllSites = false;
  private boolean exclusiveToSite = false;
  private boolean buildUserCount = false;

  public final static String tableName = "user_group";
  public final static String uniqueField = "group_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  /**
   * Constructor for the UserGroupList object
   */
  public UserGroupList() {
  }

  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static UserGroup getObject(ResultSet rs) throws SQLException {
    UserGroup userGroup = new UserGroup(rs);
    return userGroup;
  }

  /**
   * Sets the lastAnchor attribute of the UserGroupList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Description of the Method
   * Sets the lastAnchor attribute of the UserGroupList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the UserGroupList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the UserGroupList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the UserGroupList object
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
   * Gets the tableName attribute of the UserGroupList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the UserGroupList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /**
   * Description of the Method
   *
   * @param db
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    return prepareList(db, "", "");
  }

  /**
   * Description of the Method
   *
   * @param db
   * @param sqlFilter
   * @param sqlOrder
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();

    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " ug.*, ls.description AS site_name " +
            "FROM user_group ug " +
            "LEFT JOIN lookup_site_id ls ON (ug.site_id = ls.code) " +
            "WHERE ug.group_id > -1 ");
    if(sqlFilter == null || sqlFilter.length() == 0){
      StringBuffer buff = new StringBuffer();
      createFilter(buff);
      sqlFilter = buff.toString();
    }
    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    prepareFilter(pst);
    return pst;
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
        "SELECT COUNT(*) AS recordcount " +
            "FROM user_group ug " +
            "LEFT JOIN lookup_site_id ls ON (ug.site_id = ls.code) " +
            "WHERE ug.group_id > -1 ");
    createFilter(sqlFilter);
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
                "AND " + DatabaseUtils.toLowerCase(db) + "(ug.group_name) < ? ");
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
      pagedListInfo.setDefaultSort("ug.group_name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY ug.group_id ");
    }

    pst = prepareList(db, sqlFilter.toString(), sqlOrder.toString());
    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      UserGroup thisGroup = new UserGroup(rs);
      this.add(thisGroup);
    }
    rs.close();
    if (pst != null){
      pst.close();
    }
    if (buildResources || buildUserCount) {
      Iterator iter = (Iterator) this.iterator();
      while (iter.hasNext()) {
        UserGroup group = (UserGroup) iter.next();
        if (buildResources) {
          group.buildResources(db);
        } else if (buildUserCount) {
          group.buildUserCount(db);
        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (groupId > -1) {
      sqlFilter.append("AND ug.group_id = ? ");
    }
    if (!getEnabledForUser && !getEnabledForCampaign) {
      if (userId > -1) {
        sqlFilter.append("AND ug.group_id IN (SELECT group_id FROM user_group_map WHERE user_id = ? ) ");
      }
      if (campaignId > -1) {
        sqlFilter.append("AND ug.group_id IN (SELECT user_group_id FROM campaign_group_map WHERE campaign_id = ?) ");
      }
      if (enabled != Constants.UNDEFINED) {
        sqlFilter.append("AND ug.enabled = ? ");
      }
    } else if (getEnabledForUser) {
      sqlFilter.append("AND (ug.group_id IN (SELECT group_id FROM user_group_map WHERE user_id = ? ) " + (enabled != Constants.UNDEFINED ? " OR ug.enabled = ?" : "") + ") ");
      if (campaignId > -1) {
        sqlFilter.append("AND ug.group_id IN (SELECT user_group_id FROM campaign_group_map WHERE campaign_id = ?) ");
      }
    } else if (getEnabledForCampaign) {
      sqlFilter.append("AND (ug.group_id IN (SELECT user_group_id FROM campaign_group_map WHERE campaign_id = ?) " + (enabled != Constants.UNDEFINED ? " OR ug.enabled = ?" : "") + ") ");
      if (userId > -1) {
        sqlFilter.append("AND ug.group_id IN (SELECT group_id FROM user_group_map WHERE user_id = ?) ");
      }
    } else {
      sqlFilter.append(" AND (ug.group_id IN (SELECT group_id FROM user_group_map WHERE user_id = ? ) OR ug.enabled = ?) ");
    }
    if (!includeAllSites) {
      if (siteId > -1) {
        if (exclusiveToSite) {
          sqlFilter.append(" AND site_id = ? ");
        } else {
          sqlFilter.append(" AND (site_id = ? OR site_id IS NULL) ");
        }
      } else {
        sqlFilter.append(" AND site_id IS NULL ");
      }
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND ug.entered > ? ");
      }
      sqlFilter.append("AND ug.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND ug.modified > ? ");
      sqlFilter.append("AND ug.entered < ? ");
      sqlFilter.append("AND ug.modified < ? ");
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
    if (groupId > -1) {
      pst.setInt(++i, groupId);
    }
    if (!getEnabledForUser && !getEnabledForCampaign) {
      if (userId > -1) {
        pst.setInt(++i, userId);
      }
      if (enabled != Constants.UNDEFINED) {
        pst.setBoolean(++i, (enabled == Constants.TRUE));
      }
      if (campaignId > -1) {
        pst.setInt(++i, campaignId);
      }
    } else if (getEnabledForUser) {
      pst.setInt(++i, userId);
      if (enabled != Constants.UNDEFINED) {
        pst.setBoolean(++i, (enabled == Constants.TRUE));
      }
      if (campaignId > -1) {
        pst.setInt(++i, campaignId);
      }
    } else if (getEnabledForCampaign) {
      pst.setInt(++i, campaignId);
      if (enabled != Constants.UNDEFINED) {
        pst.setBoolean(++i, (enabled == Constants.TRUE));
      }
      if (userId > -1) {
        pst.setInt(++i, userId);
      }
    }
    if (siteId > -1 && !includeAllSites) {
      pst.setInt(++i, this.getSiteId());
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
   * Gets the hashMap attribute of the UserGroupList object
   *
   * @return The hashMap value
   */
  public HashMap getHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      UserGroup group = (UserGroup) iter.next();
      map.put(String.valueOf(group.getId()), group.getName());
    }
    return map;
  }


  /**
   * Adds a feature to the UsersForGroups attribute of the UserGroupList object
   *
   * @param db     The feature to be added to the UsersForGroups
   *               attribute
   * @param map    The feature to be added to the UsersForGroups
   *               attribute
   * @param userId The feature to be added to the UsersForGroups
   *               attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean addUsersForGroups(Connection db, HashMap map, int userId) throws SQLException {
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      UserGroup group = (UserGroup) iter.next();
      if (map.get(String.valueOf(group.getId())) != null) {
        map.remove(String.valueOf(group.getId()));
      } else {
        group.removeUser(db, userId);
      }
    }
    Iterator keySet = (Iterator) map.keySet().iterator();
    while (keySet.hasNext()) {
      Integer key = (Integer) keySet.next();
      UserGroup group = new UserGroup();
      group.setBuildResources(true);
      group.queryRecord(db, key.intValue());
      group.addUser(db, userId);
    }
    return true;
  }


  /**
   * Sets the getEnabledForUser attribute of the UserGroupList object
   *
   * @param enabledOK The new getEnabledForUser value
   * @param uId       The new getEnabledForUser value
   */
  public void setGetEnabledForUser(int enabledOK, int uId) {
    enabled = enabledOK;
    userId = uId;
    getEnabledForUser = true;
  }


  /**
   * Sets the getEnabledForCampaign attribute of the UserGroupList object
   *
   * @param enabledOK The new getEnabledForCampaign value
   * @param cId       The new getEnabledForCampaign value
   */
  public void setGetEnabledForCampaign(int enabledOK, int cId) {
    enabled = enabledOK;
    campaignId = cId;
    getEnabledForCampaign = true;
  }


  /**
   * Gets the htmlSelectObj attribute of the UserGroupList object
   *
   * @param selectedKey Description of the Parameter
   * @return The htmlSelectObj value
   */
  public HtmlSelect getHtmlSelectObj(int selectedKey) {
    HtmlSelect groupListSelect = new HtmlSelect();
    if (labelNoneSelected != null) {
      groupListSelect.addItem(-1, this.getLabelNoneSelected());
    }
    Iterator iter = this.iterator();
    while (iter.hasNext()) {
      UserGroup thisGroup = (UserGroup) iter.next();
      if (thisGroup.getEnabled()) {
        groupListSelect.addItem(thisGroup.getId(), thisGroup.getName() + (thisGroup.getSiteId() != -1 ? thisGroup.getSiteName() : ""));
      } else if (thisGroup.getId() == selectedKey) {
        groupListSelect.addItem(thisGroup.getId(), thisGroup.getName() + (thisGroup.getSiteId() != -1 ? thisGroup.getSiteName() : "") + " (X)");
      }
    }
    return groupListSelect;
  }


  /*
   *  Get and Set methods
   */
  /**
   * Gets the pagedListInfo attribute of the UserGroupList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the UserGroupList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the groupId attribute of the UserGroupList object
   *
   * @return The groupId value
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   * Sets the groupId attribute of the UserGroupList object
   *
   * @param tmp The new groupId value
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   * Sets the groupId attribute of the UserGroupList object
   *
   * @param tmp The new groupId value
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   * Gets the userId attribute of the UserGroupList object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Sets the userId attribute of the UserGroupList object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the UserGroupList object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   * Gets the enabled attribute of the UserGroupList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Sets the enabled attribute of the UserGroupList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the UserGroupList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Gets the buildResources attribute of the UserGroupList object
   *
   * @return The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   * Sets the buildResources attribute of the UserGroupList object
   *
   * @param tmp The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   * Sets the buildResources attribute of the UserGroupList object
   *
   * @param tmp The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the getEnabledForUser attribute of the UserGroupList object
   *
   * @return The getEnabledForUser value
   */
  public boolean getGetEnabledForUser() {
    return getEnabledForUser;
  }


  /**
   * Sets the getEnabledForUser attribute of the UserGroupList object
   *
   * @param tmp The new getEnabledForUser value
   */
  public void setGetEnabledForUser(boolean tmp) {
    this.getEnabledForUser = tmp;
  }


  /**
   * Sets the getEnabledForUser attribute of the UserGroupList object
   *
   * @param tmp The new getEnabledForUser value
   */
  public void setGetEnabledForUser(String tmp) {
    this.getEnabledForUser = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the labelNoneSelected attribute of the UserGroupList object
   *
   * @return The labelNoneSelected value
   */
  public String getLabelNoneSelected() {
    return labelNoneSelected;
  }


  /**
   * Sets the labelNoneSelected attribute of the UserGroupList object
   *
   * @param tmp The new labelNoneSelected value
   */
  public void setLabelNoneSelected(String tmp) {
    this.labelNoneSelected = tmp;
  }


  /**
   * Gets the siteId attribute of the UserGroupList object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   * Sets the siteId attribute of the UserGroupList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   * Sets the siteId attribute of the UserGroupList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the includeAllSites attribute of the UserGroupList object
   *
   * @return The includeAllSites value
   */
  public boolean getIncludeAllSites() {
    return includeAllSites;
  }


  /**
   * Sets the includeAllSites attribute of the UserGroupList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(boolean tmp) {
    this.includeAllSites = tmp;
  }


  /**
   * Sets the includeAllSites attribute of the UserGroupList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(String tmp) {
    this.includeAllSites = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the exclusiveToSite attribute of the UserGroupList object
   *
   * @return The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   * Sets the exclusiveToSite attribute of the UserGroupList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   * Sets the exclusiveToSite attribute of the UserGroupList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the campaignId attribute of the UserGroupList object
   *
   * @return The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   * Sets the campaignId attribute of the UserGroupList object
   *
   * @param tmp The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   * Sets the campaignId attribute of the UserGroupList object
   *
   * @param tmp The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   * Gets the getEnabledForCampaign attribute of the UserGroupList object
   *
   * @return The getEnabledForCampaign value
   */
  public boolean getGetEnabledForCampaign() {
    return getEnabledForCampaign;
  }


  /**
   * Sets the getEnabledForCampaign attribute of the UserGroupList object
   *
   * @param tmp The new getEnabledForCampaign value
   */
  public void setGetEnabledForCampaign(boolean tmp) {
    this.getEnabledForCampaign = tmp;
  }


  /**
   * Sets the getEnabledForCampaign attribute of the UserGroupList object
   *
   * @param tmp The new getEnabledForCampaign value
   */
  public void setGetEnabledForCampaign(String tmp) {
    this.getEnabledForCampaign = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildUserCount attribute of the UserGroupList object
   *
   * @return The buildUserCount value
   */
  public boolean getBuildUserCount() {
    return buildUserCount;
  }


  /**
   * Sets the buildUserCount attribute of the UserGroupList object
   *
   * @param tmp The new buildUserCount value
   */
  public void setBuildUserCount(boolean tmp) {
    this.buildUserCount = tmp;
  }


  /**
   * Sets the buildUserCount attribute of the UserGroupList object
   *
   * @param tmp The new buildUserCount value
   */
  public void setBuildUserCount(String tmp) {
    this.buildUserCount = DatabaseUtils.parseBoolean(tmp);
  }
}

