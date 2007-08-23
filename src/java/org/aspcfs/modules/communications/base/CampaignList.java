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
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

/**
 * Represents the combination of a Message, Recipients, and Schedule details.
 *
 * @author Wesley_S_Gillette
 * @version $Id: CampaignList.java,v 1.12.22.2 2003/05/14 17:41:56 akhi_m Exp
 *          $
 * @created November 16, 2001
 */
public class CampaignList extends Vector implements SyncableList{

  public final static int TRUE = 1;
  public final static int FALSE = 0;

  private PagedListInfo pagedListInfo = null;
  private String name = "";
  private String description = "";
  private java.sql.Timestamp activeDate = null;
  private int enabled = -1;
  private int active = -1;

  private boolean incompleteOnly = false;
  private boolean completeOnly = false;
  private int owner = -1;
  private int type = -1;
  private String ownerIdRange = null;
  private String idRange = null;
  private int ready = -1;
  private int contactId = -1;
  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;
  private int userGroupUserId = -1;
  private int siteId = -1;
  private boolean includeAllSites = true;
  private boolean exclusiveToSite = false;

  private java.sql.Timestamp activeRangeStart = null;
  private java.sql.Timestamp activeRangeEnd = null;
  private java.sql.Timestamp runRangeStart = null;
  private java.sql.Timestamp runRangeEnd = null;

  private int id = -1;

  public final static String tableName = "campaign";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  /**
   * Constructor for the CampaignList object
   *
   * @since 1.1
   */
  public CampaignList() {
  }

  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static Campaign getObject(ResultSet rs) throws SQLException {
    Campaign campaign = new Campaign(rs);
    return campaign;
  }

  /**
   * Sets the pagedListInfo attribute of the CampaignList object
   *
   * @param tmp The new pagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the name attribute of the CampaignList object
   *
   * @param tmp The new name value
   * @since 1.1
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the description attribute of the CampaignList object
   *
   * @param tmp The new description value
   * @since 1.1
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the ActiveDate attribute of the CampaignList object
   *
   * @param tmp The new ActiveDate value
   * @since 1.2
   */
  public void setActiveDate(java.sql.Timestamp tmp) {
    this.activeDate = tmp;
  }


  /**
   * Sets the Enabled attribute of the CampaignList object
   *
   * @param tmp The new Enabled value
   * @since 1.2
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the Active attribute of the CampaignList object
   *
   * @param tmp The new Active value
   */
  public void setActive(int tmp) {
    this.active = tmp;
  }


  /**
   * Sets the ready attribute of the CampaignList object
   *
   * @param tmp The new ready value
   */
  public void setReady(int tmp) {
    this.ready = tmp;
  }


  /**
   * Sets the contactId attribute of the CampaignList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the trashedDate attribute of the CampaignList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the CampaignList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the includeOnlyTrashed attribute of the CampaignList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   * Sets the includeOnlyTrashed attribute of the CampaignList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the IncompleteOnly attribute of the CampaignList object
   *
   * @param tmp The new IncompleteOnly value
   */
  public void setIncompleteOnly(boolean tmp) {
    this.incompleteOnly = tmp;
  }


  /**
   * Sets the CompleteOnly attribute of the CampaignList object
   *
   * @param tmp The new CompleteOnly value
   */
  public void setCompleteOnly(boolean tmp) {
    this.completeOnly = tmp;
  }


  /**
   * Sets the owner attribute of the CampaignList object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the ownerIdRange attribute of the CampaignList object
   *
   * @param tmp The new ownerIdRange value
   */
  public void setOwnerIdRange(String tmp) {
    this.ownerIdRange = tmp;
  }


  /**
   * Sets the idRange attribute of the CampaignList object
   *
   * @param idRange The new idRange value
   */
  public void setIdRange(String idRange) {
    this.idRange = idRange;
  }


  /**
   * Sets the activeRangeStart attribute of the CampaignList object
   *
   * @param tmp The new activeRangeStart value
   */
  public void setActiveRangeStart(java.sql.Timestamp tmp) {
    this.activeRangeStart = tmp;
  }


  /**
   * Sets the activeRangeEnd attribute of the CampaignList object
   *
   * @param tmp The new activeRangeEnd value
   */
  public void setActiveRangeEnd(java.sql.Timestamp tmp) {
    this.activeRangeEnd = tmp;
  }


  /**
   * Sets the runRangeStart attribute of the CampaignList object
   *
   * @param tmp The new runRangeStart value
   */
  public void setRunRangeStart(java.sql.Timestamp tmp) {
    this.runRangeStart = tmp;
  }


  /**
   * Sets the runRangeEnd attribute of the CampaignList object
   *
   * @param tmp The new runRangeEnd value
   */
  public void setRunRangeEnd(java.sql.Timestamp tmp) {
    this.runRangeEnd = tmp;
  }


  /**
   * Gets the tableName attribute of the CampaignList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the CampaignList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the lastAnchor attribute of the CampaignList object
   *
   * @return The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   * Gets the nextAnchor attribute of the CampaignList object
   *
   * @return The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   * Gets the syncType attribute of the CampaignList object
   *
   * @return The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   * Sets the lastAnchor attribute of the CampaignList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }

  /**
   * Sets the lastAnchor attribute of the CampaignList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Sets the nextAnchor attribute of the CampaignList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }

  /**
   * Sets the nextAnchor attribute of the CampaignList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Sets the syncType attribute of the CampaignList object
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
   * Sets the type attribute of the CampaignList object
   *
   * @param type The new type value
   */
  public void setType(int type) {
    this.type = type;
  }


  /**
   * Gets the type attribute of the CampaignList object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }


  /**
   * Gets the pagedListInfo attribute of the CampaignList object
   *
   * @return The pagedListInfo value
   * @since 1.1
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the idRange attribute of the CampaignList object
   *
   * @return The idRange value
   */
  public String getIdRange() {
    return idRange;
  }


  /**
   * Gets the name attribute of the CampaignList object
   *
   * @return The name value
   * @since 1.1
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the description attribute of the CampaignList object
   *
   * @return The description value
   * @since 1.1
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }

  /**
   * Gets the htmlSelect attribute of the CampaignList object
   *
   * @param selectName Description of Parameter
   * @return The htmlSelect value
   * @since 1.1
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   * Gets the htmlSelect attribute of the CampaignList object
   *
   * @param selectName Description of Parameter
   * @param defaultKey Description of Parameter
   * @return The htmlSelect value
   * @since 1.1
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect campaignListSelect = new HtmlSelect();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Campaign thisCampaign = (Campaign) i.next();
      campaignListSelect.addItem(
          thisCampaign.getId(),
          thisCampaign.getName());
    }
    return campaignListSelect.getHtml(selectName, defaultKey);
  }


  /**
   * Gets the ActiveDate attribute of the CampaignList object
   *
   * @return The ActiveDate value
   * @since 1.2
   */
  public java.sql.Timestamp getActiveDate() {
    return activeDate;
  }


  /**
   * Gets the Enabled attribute of the CampaignList object
   *
   * @return The Enabled value
   * @since 1.2
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Gets the Active attribute of the CampaignList object
   *
   * @return The Active value
   */
  public int getActive() {
    return active;
  }


  /**
   * Gets the IncompleteOnly attribute of the CampaignList object
   *
   * @return The IncompleteOnly value
   */
  public boolean getIncompleteOnly() {
    return incompleteOnly;
  }


  /**
   * Gets the CompleteOnly attribute of the CampaignList object
   *
   * @return The CompleteOnly value
   */
  public boolean getCompleteOnly() {
    return completeOnly;
  }


  /**
   * Gets the userGroupUserId attribute of the CampaignList object
   *
   * @return The userGroupUserId value
   */
  public int getUserGroupUserId() {
    return userGroupUserId;
  }


  /**
   * Sets the userGroupUserId attribute of the CampaignList object
   *
   * @param tmp The new userGroupUserId value
   */
  public void setUserGroupUserId(int tmp) {
    this.userGroupUserId = tmp;
  }


  /**
   * Sets the userGroupUserId attribute of the CampaignList object
   *
   * @param tmp The new userGroupUserId value
   */
  public void setUserGroupUserId(String tmp) {
    this.userGroupUserId = Integer.parseInt(tmp);
  }


  /**
   * Gets the siteId attribute of the CampaignList object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   * Sets the siteId attribute of the CampaignList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   * Sets the siteId attribute of the CampaignList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the includeAllSites attribute of the CampaignList object
   *
   * @return The includeAllSites value
   */
  public boolean getIncludeAllSites() {
    return includeAllSites;
  }


  /**
   * Sets the includeAllSites attribute of the CampaignList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(boolean tmp) {
    this.includeAllSites = tmp;
  }


  /**
   * Sets the includeAllSites attribute of the CampaignList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(String tmp) {
    this.includeAllSites = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the exclusiveToSite attribute of the CampaignList object
   *
   * @return The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   * Sets the exclusiveToSite attribute of the CampaignList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   * Sets the exclusiveToSite attribute of the CampaignList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Description of the Method
   *
   * @param db
   * @param pst
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
   * @param pst
   * @param sqlFilter
   * @param sqlOrder
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "c.*, msg.name AS messageName, msg.subject AS messageSubject, dt.code AS deliveryType, dt.description AS deliveryTypeName " +
            "FROM campaign c " +
            "LEFT JOIN " + DatabaseUtils.addQuotes(db, "message") + " msg ON (c.message_id = msg.id) " +
            "LEFT JOIN lookup_delivery_options dt ON (c.send_method_id = dt.code) " +
            "WHERE ");
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
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM campaign c " +
            "WHERE ");
    createFilter(db, sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(
          sqlCount.toString() +
              sqlFilter.toString());
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
                "AND " + DatabaseUtils.toLowerCase(db) + "(c.name) < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
        pagedListInfo.setDefaultSort("c.name", null);
      } else {
        //Determine column to sort by
        if (completeOnly) {
          pagedListInfo.setDefaultSort("c.active_date", "desc");
        } else {
          pagedListInfo.setDefaultSort("c.active_date", null);
        }
      }
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY c.modified desc ");
    }

    pst = prepareList(db, sqlFilter.toString(), sqlOrder.toString());
    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      Campaign thisCamp = new Campaign(rs);
      this.add(thisCamp);
    }
    rs.close();
    if(pst != null){
      pst.close();
    }
    buildResources(db);
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of Parameter
   * @since 1.1
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if(id == -1){
      sqlFilter.append("c.campaign_id > ?");
    }else{
      sqlFilter.append("c.campaign_id = ?");
    }

    if (activeDate != null) {
      sqlFilter.append("AND c.active_date = ? ");
    }
    if (enabled == FALSE || enabled == TRUE) {
      sqlFilter.append("AND c.enabled = ? ");
    }
    if (active == FALSE || active == TRUE) {
      sqlFilter.append("AND c." + DatabaseUtils.addQuotes(db, "active") + " = ? ");
    }
    if (incompleteOnly) {
      sqlFilter.append("AND (active_date IS NULL OR " + DatabaseUtils.addQuotes(db, "active") + " = ?) ");
    }
    if (completeOnly) {
      sqlFilter.append("AND active_date IS NOT NULL AND " + DatabaseUtils.addQuotes(db, "active") + " = ? ");
    }
    if (owner > -1) {
      sqlFilter.append("AND c.enteredby = ? ");
    }
    if (!includeAllSites) {
      if (siteId > -1) {
        sqlFilter.append("AND (c.enteredby IN (SELECT user_id FROM " + DatabaseUtils.addQuotes(db, "access") + " WHERE site_id = ?) ");
        if (!exclusiveToSite) {
          sqlFilter.append("OR c.enteredby IN (SELECT user_id FROM " + DatabaseUtils.addQuotes(db, "access") + " WHERE site_id IS NULL) ");
        }
        sqlFilter.append(") ");
      } else {
        sqlFilter.append("AND c.enteredby IN (SELECT user_id FROM " + DatabaseUtils.addQuotes(db, "access") + " WHERE site_id IS NULL) ");
      }
    }
    if (type > -1) {
      sqlFilter.append("AND c." + DatabaseUtils.addQuotes(db, "type") + " = ? ");
    }
    if (userGroupUserId > -1) {
      sqlFilter.append("AND (c.campaign_id IN ( " +
          "SELECT campaign_id FROM campaign_group_map WHERE user_group_id IN ( " +
          "SELECT group_id FROM user_group_map where user_id = ? )) ");
      if (ownerIdRange != null) {
        sqlFilter.append(" OR c.enteredBy IN (" + ownerIdRange + ") ");
      }
      sqlFilter.append(") ");
    } else if (ownerIdRange != null) {
      sqlFilter.append("AND c.enteredBy IN (" + ownerIdRange + ") ");
    }
    if (idRange != null) {
      sqlFilter.append("AND c.campaign_id IN (" + idRange + ") ");
    }
    if (ready == TRUE) {
      sqlFilter.append(
          "AND c.status_id IN (" + Campaign.QUEUE + ", " + Campaign.STARTED + ") ");
    }
    if (contactId > -1) {
      sqlFilter.append(
          "AND c.campaign_id IN (SELECT campaign_id FROM scheduled_recipient WHERE contact_id = ?) ");
    }
    if (activeRangeStart != null) {
      sqlFilter.append("AND active_date >= ? ");
    }
    if (activeRangeEnd != null) {
      sqlFilter.append("AND active_date <= ? ");
    }
    if (runRangeStart != null) {
      sqlFilter.append(
          "AND c.campaign_id IN (SELECT campaign_id FROM campaign_run WHERE run_date >= ?) ");
    }
    if (runRangeEnd != null) {
      sqlFilter.append(
          "AND c.campaign_id IN (SELECT campaign_id FROM campaign_run WHERE run_date <= ?) ");
    }
    if (includeOnlyTrashed) {
      sqlFilter.append("AND c.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND c.trashed_date = ? ");
    } else {
      sqlFilter.append("AND c.trashed_date IS NULL ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND c.entered > ? ");
      }
      sqlFilter.append("AND c.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND c.modified > ? ");
      sqlFilter.append("AND c.entered < ? ");
      sqlFilter.append("AND c.modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    pst.setInt(++i, id);

    if (activeDate != null) {
      pst.setTimestamp(++i, activeDate);
    }
    if (enabled == FALSE) {
      pst.setBoolean(++i, false);
    } else if (enabled == TRUE) {
      pst.setBoolean(++i, true);
    }
    if (active == FALSE) {
      pst.setBoolean(++i, false);
    } else if (active == TRUE) {
      pst.setBoolean(++i, true);
    }
    if (incompleteOnly) {
      pst.setBoolean(++i, false);
    }
    if (completeOnly) {
      pst.setBoolean(++i, true);
    }
    if (owner > -1) {
      pst.setInt(++i, owner);
    }
    if (!includeAllSites && siteId > -1) {
      pst.setInt(++i, siteId);
    }
    if (type > -1) {
      pst.setInt(++i, type);
    }
    if (userGroupUserId > -1) {
      pst.setInt(++i, this.getUserGroupUserId());
    }
    if (contactId > -1) {
      pst.setInt(++i, contactId);
    }
    if (activeRangeStart != null) {
      pst.setTimestamp(++i, activeRangeStart);
    }
    if (activeRangeEnd != null) {
      pst.setTimestamp(++i, activeRangeEnd);
    }
    if (runRangeStart != null) {
      pst.setTimestamp(++i, runRangeStart);
    }
    if (runRangeEnd != null) {
      pst.setTimestamp(++i, runRangeEnd);
    }
    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
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
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  private void buildResources(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Campaign thisCampaign = (Campaign) i.next();
      thisCampaign.buildRecipientCount(db);
      thisCampaign.buildHasAddressRequest(db);
      thisCampaign.buildHasSurvey(db);
      thisCampaign.setGroupList(db);
      thisCampaign.buildFileCount(db);
      thisCampaign.buildMessageAttachments(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int queryRecordCount(Connection db) throws SQLException {
    int recordCount = 0;
    StringBuffer sqlFilter = new StringBuffer();
    String sqlCount =
        "SELECT COUNT(*) AS recordcount " +
            "FROM campaign c " +
            "WHERE ";
    createFilter(db, sqlFilter);
    PreparedStatement pst = db.prepareStatement(
        sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = DatabaseUtils.getInt(rs, "recordcount", 0);
    }
    rs.close();
    pst.close();
    return recordCount;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param filePath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db, String filePath) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      Campaign thisCampaign = (Campaign) itr.next();
      thisCampaign.delete(db, filePath);
    }
  }


  /**
   * Gets the nameById attribute of the CampaignList object
   *
   * @param id Description of the Parameter
   * @return The nameById value
   */
  public String getNameById(int id) {
    String result = null;
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      Campaign thisCampaign = (Campaign) itr.next();
      if (thisCampaign.getId() == id) {
        result = thisCampaign.getName();
        break;
      }
    }
    return result;
  }

  /**
   * Description of the Method
   *
   * @param db
   * @throws SQLException Description of the Returned Value
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
}

