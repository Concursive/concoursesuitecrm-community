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
package org.aspcfs.modules.accounts.base;

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
 * @version $Id: OrganizationHistoryList.java,v 1.1.2.3 2005/06/02 18:22:46
 *          partha Exp $
 * @created June 1, 2005
 */
public class OrganizationHistoryList extends ArrayList implements SyncableList {
  //sync api
  public final static String tableName = "ticket";
  public final static String uniqueField = "ticketid";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  //filters
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int contactId = -1;
  private int orgId = -1;
  private int level = -1;
  private boolean showDisabledWithEnabled = false;
  protected java.sql.Date startDateRange = null;
  protected java.sql.Date endDateRange = null;
  //Other filters
  private boolean notes = false;
  private boolean activities = false;
  private boolean email = false;
  private boolean documents = false;
  private boolean quotes = false;
  private boolean opportunities = false;
  private boolean serviceContracts = false;
  private boolean tickets = false;
  private boolean tasks = false;
  private boolean relationships = false;
  private boolean assets = false;


  /**
   * Gets the tableName attribute of the OrganizationHistoryList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the OrganizationHistoryList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the lastAnchor attribute of the OrganizationHistoryList object
   *
   * @return The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   * Sets the lastAnchor attribute of the OrganizationHistoryList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the OrganizationHistoryList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the nextAnchor attribute of the OrganizationHistoryList object
   *
   * @return The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   * Sets the nextAnchor attribute of the OrganizationHistoryList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the OrganizationHistoryList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the syncType attribute of the OrganizationHistoryList object
   *
   * @return The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   * Sets the syncType attribute of the OrganizationHistoryList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Sets the syncType attribute of the OrganizationHistoryList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   * Gets the pagedListInfo attribute of the OrganizationHistoryList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the OrganizationHistoryList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the id attribute of the OrganizationHistoryList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the OrganizationHistoryList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the OrganizationHistoryList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the orgId attribute of the OrganizationHistoryList object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Sets the orgId attribute of the OrganizationHistoryList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the OrganizationHistoryList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Gets the level attribute of the OrganizationHistoryList object
   *
   * @return The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Sets the level attribute of the OrganizationHistoryList object
   *
   * @param tmp The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the level attribute of the OrganizationHistoryList object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   * Gets the startDateRange attribute of the OrganizationHistoryList object
   *
   * @return The startDateRange value
   */
  public java.sql.Date getStartDateRange() {
    return startDateRange;
  }


  /**
   * Sets the startDateRange attribute of the OrganizationHistoryList object
   *
   * @param tmp The new startDateRange value
   */
  public void setStartDateRange(java.sql.Date tmp) {
    this.startDateRange = tmp;
  }


  /**
   * Gets the endDateRange attribute of the OrganizationHistoryList object
   *
   * @return The endDateRange value
   */
  public java.sql.Date getEndDateRange() {
    return endDateRange;
  }


  /**
   * Sets the endDateRange attribute of the OrganizationHistoryList object
   *
   * @param tmp The new endDateRange value
   */
  public void setEndDateRange(java.sql.Date tmp) {
    this.endDateRange = tmp;
  }


  /**
   * Gets the notes attribute of the OrganizationHistoryList object
   *
   * @return The notes value
   */
  public boolean getNotes() {
    return notes;
  }


  /**
   * Sets the notes attribute of the OrganizationHistoryList object
   *
   * @param tmp The new notes value
   */
  public void setNotes(boolean tmp) {
    this.notes = tmp;
  }


  /**
   * Sets the notes attribute of the OrganizationHistoryList object
   *
   * @param tmp The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the activities attribute of the OrganizationHistoryList object
   *
   * @return The activities value
   */
  public boolean getActivities() {
    return activities;
  }


  /**
   * Sets the activities attribute of the OrganizationHistoryList object
   *
   * @param tmp The new activities value
   */
  public void setActivities(boolean tmp) {
    this.activities = tmp;
  }


  /**
   * Sets the activities attribute of the OrganizationHistoryList object
   *
   * @param tmp The new activities value
   */
  public void setActivities(String tmp) {
    this.activities = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the email attribute of the OrganizationHistoryList object
   *
   * @return The email value
   */
  public boolean getEmail() {
    return email;
  }


  /**
   * Sets the email attribute of the OrganizationHistoryList object
   *
   * @param tmp The new email value
   */
  public void setEmail(boolean tmp) {
    this.email = tmp;
  }


  /**
   * Sets the email attribute of the OrganizationHistoryList object
   *
   * @param tmp The new email value
   */
  public void setEmail(String tmp) {
    this.email = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the documents attribute of the OrganizationHistoryList object
   *
   * @return The documents value
   */
  public boolean getDocuments() {
    return documents;
  }


  /**
   * Sets the documents attribute of the OrganizationHistoryList object
   *
   * @param tmp The new documents value
   */
  public void setDocuments(boolean tmp) {
    this.documents = tmp;
  }


  /**
   * Sets the documents attribute of the OrganizationHistoryList object
   *
   * @param tmp The new documents value
   */
  public void setDocuments(String tmp) {
    this.documents = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the quotes attribute of the OrganizationHistoryList object
   *
   * @return The quotes value
   */
  public boolean getQuotes() {
    return quotes;
  }


  /**
   * Sets the quotes attribute of the OrganizationHistoryList object
   *
   * @param tmp The new quotes value
   */
  public void setQuotes(boolean tmp) {
    this.quotes = tmp;
  }


  /**
   * Sets the quotes attribute of the OrganizationHistoryList object
   *
   * @param tmp The new quotes value
   */
  public void setQuotes(String tmp) {
    this.quotes = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the opportunities attribute of the OrganizationHistoryList object
   *
   * @return The opportunities value
   */
  public boolean getOpportunities() {
    return opportunities;
  }


  /**
   * Sets the opportunities attribute of the OrganizationHistoryList object
   *
   * @param tmp The new opportunities value
   */
  public void setOpportunities(boolean tmp) {
    this.opportunities = tmp;
  }


  /**
   * Sets the opportunities attribute of the OrganizationHistoryList object
   *
   * @param tmp The new opportunities value
   */
  public void setOpportunities(String tmp) {
    this.opportunities = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the serviceContracts attribute of the OrganizationHistoryList object
   *
   * @return The serviceContracts value
   */
  public boolean getServiceContracts() {
    return serviceContracts;
  }


  /**
   * Sets the serviceContracts attribute of the OrganizationHistoryList object
   *
   * @param tmp The new serviceContracts value
   */
  public void setServiceContracts(boolean tmp) {
    this.serviceContracts = tmp;
  }


  /**
   * Sets the serviceContracts attribute of the OrganizationHistoryList object
   *
   * @param tmp The new serviceContracts value
   */
  public void setServiceContracts(String tmp) {
    this.serviceContracts = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the tickets attribute of the OrganizationHistoryList object
   *
   * @return The tickets value
   */
  public boolean getTickets() {
    return tickets;
  }


  /**
   * Sets the tickets attribute of the OrganizationHistoryList object
   *
   * @param tmp The new tickets value
   */
  public void setTickets(boolean tmp) {
    this.tickets = tmp;
  }


  /**
   * Sets the tickets attribute of the OrganizationHistoryList object
   *
   * @param tmp The new tickets value
   */
  public void setTickets(String tmp) {
    this.tickets = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the tasks attribute of the OrganizationHistoryList object
   *
   * @return The tasks value
   */
  public boolean getTasks() {
    return tasks;
  }


  /**
   * Sets the tasks attribute of the OrganizationHistoryList object
   *
   * @param tmp The new tasks value
   */
  public void setTasks(boolean tmp) {
    this.tasks = tmp;
  }


  /**
   * Sets the tasks attribute of the OrganizationHistoryList object
   *
   * @param tmp The new tasks value
   */
  public void setTasks(String tmp) {
    this.tasks = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the relationships attribute of the OrganizationHistoryList object
   *
   * @return The relationships value
   */
  public boolean getRelationships() {
    return relationships;
  }


  /**
   * Sets the relationships attribute of the OrganizationHistoryList object
   *
   * @param tmp The new relationships value
   */
  public void setRelationships(boolean tmp) {
    this.relationships = tmp;
  }


  /**
   * Sets the relationships attribute of the OrganizationHistoryList object
   *
   * @param tmp The new relationships value
   */
  public void setRelationships(String tmp) {
    this.relationships = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the assets attribute of the OrganizationHistoryList object
   *
   * @return The assets value
   */
  public boolean getAssets() {
    return assets;
  }


  /**
   * Sets the assets attribute of the OrganizationHistoryList object
   *
   * @param tmp The new assets value
   */
  public void setAssets(boolean tmp) {
    this.assets = tmp;
  }


  /**
   * Sets the assets attribute of the OrganizationHistoryList object
   *
   * @param tmp The new assets value
   */
  public void setAssets(String tmp) {
    this.assets = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the showDisabledWithEnabled attribute of the OrganizationHistoryList
   * object
   *
   * @return The showDisabledWithEnabled value
   */
  public boolean getShowDisabledWithEnabled() {
    return showDisabledWithEnabled;
  }


  /**
   * Sets the showDisabledWithEnabled attribute of the OrganizationHistoryList
   * object
   *
   * @param tmp The new showDisabledWithEnabled value
   */
  public void setShowDisabledWithEnabled(boolean tmp) {
    this.showDisabledWithEnabled = tmp;
  }


  /**
   * Sets the showDisabledWithEnabled attribute of the OrganizationHistoryList
   * object
   *
   * @param tmp The new showDisabledWithEnabled value
   */
  public void setShowDisabledWithEnabled(String tmp) {
    this.showDisabledWithEnabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the contactId attribute of the OrganizationHistoryList object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Sets the contactId attribute of the OrganizationHistoryList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the OrganizationHistoryList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
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

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM history " +
        "LEFT JOIN contact c ON (history.contact_id = c.contact_id) " +
        "LEFT JOIN organization o ON (history.org_id = o.org_id) " +
        "WHERE history_id > 0 ");

    createFilter(sqlFilter);

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
            "AND " + DatabaseUtils.toLowerCase(db) + "(\"type\") < ? ");
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
      pagedListInfo.setDefaultSort("history.entered", "desc");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY history.entered ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "history.*, c.namelast AS namelast " +
        "FROM history " +
        "LEFT JOIN contact c ON (history.contact_id = c.contact_id) " +
        "LEFT JOIN organization o ON (history.org_id = o.org_id) " +
        "WHERE history_id > 0 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      OrganizationHistory thisHistoryElement = new OrganizationHistory(rs);
      this.add(thisHistoryElement);
    }
    rs.close();
    if (pst != null) {
      pst.close();
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
    if (id != -1) {
      sqlFilter.append("AND history.history_id = ? ");
    }
    if (orgId != -1) {
      sqlFilter.append(
          "AND (history.org_id = ? " +
          "OR history.contact_id IN (SELECT ct.contact_id FROM contact ct WHERE ct.org_id = ? )) ");
    }
    if (contactId != -1) {
      sqlFilter.append(
          "AND history.contact_id = ? AND history.org_id IS NULL ");
    }
    if (level != -1) {
      sqlFilter.append("AND history.\"level\" > ? ");
    }
    if (startDateRange != null) {
      sqlFilter.append("AND history.event_date > ? ");
    }
    if (endDateRange != null) {
      sqlFilter.append("AND history.event_date < ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND history.entered > ? ");
      }
      sqlFilter.append("AND history.entered < ? ");
    }
    if (!notes) {
      sqlFilter.append("AND history.link_object_id NOT IN (?) ");
    }
    if (!activities) {
      sqlFilter.append("AND history.link_object_id NOT IN (?, ?) ");
    }
    if (!email) {
      sqlFilter.append("AND history.link_object_id NOT IN (?, ?) ");
    }
    if (!quotes) {
      sqlFilter.append("AND history.link_object_id NOT IN (?) ");
    }
    if (!opportunities) {
      sqlFilter.append("AND history.link_object_id NOT IN (?) ");
    }
    if (!serviceContracts) {
      sqlFilter.append("AND history.link_object_id NOT IN (?) ");
    }
    if (!tickets) {
      sqlFilter.append("AND history.link_object_id NOT IN (?) ");
    }
    if (!tasks) {
      sqlFilter.append("AND history.link_object_id NOT IN (?) ");
    }
    if (!documents) {
      sqlFilter.append("AND history.link_object_id NOT IN (?) ");
    }
    if (!relationships) {
      sqlFilter.append("AND history.link_object_id NOT IN (?) ");
    }
    if (!assets) {
      sqlFilter.append("AND history.link_object_id NOT IN (?) ");
    }
    if (!showDisabledWithEnabled) {
      sqlFilter.append("AND history.enabled = ? ");
    }

    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND history.modified > ? ");
      sqlFilter.append("AND history.entered < ? ");
      sqlFilter.append("AND history.modified < ? ");
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
    if (id != -1) {
      pst.setInt(++i, this.getId());
    }
    if (orgId != -1) {
      pst.setInt(++i, this.getOrgId());
      pst.setInt(++i, this.getOrgId());
    }
    if (contactId != -1) {
      pst.setInt(++i, this.getContactId());
    }
    if (level != -1) {
      pst.setInt(++i, this.getLevel());
    }
    if (startDateRange != null) {
      pst.setDate(++i, this.getStartDateRange());
    }
    if (endDateRange != null) {
      pst.setDate(++i, this.getEndDateRange());
    }
    if (!notes) {
      pst.setInt(++i, OrganizationHistory.NOTE);
    }
    if (!activities) {
      pst.setInt(++i, OrganizationHistory.COMPLETE_ACTIVITY);
      pst.setInt(++i, OrganizationHistory.CANCELED_ACTIVITY);
    }
    if (!email) {
      pst.setInt(++i, OrganizationHistory.CFSNOTE);
      pst.setInt(++i, OrganizationHistory.CAMPAIGN);
    }
    if (!quotes) {
      pst.setInt(++i, OrganizationHistory.QUOTE);
    }
    if (!opportunities) {
      pst.setInt(++i, OrganizationHistory.OPPORTUNITY);
    }
    if (!serviceContracts) {
      pst.setInt(++i, OrganizationHistory.SERVICE_CONTRACT);
    }
    if (!tickets) {
      pst.setInt(++i, OrganizationHistory.TICKET);
    }
    if (!tasks) {
      pst.setInt(++i, OrganizationHistory.TASK);
    }
    if (!documents) {
      pst.setInt(++i, OrganizationHistory.ACCOUNT_DOCUMENT);
    }
    if (!relationships) {
      pst.setInt(++i, OrganizationHistory.RELATIONSHIP);
    }
    if (!assets) {
      pst.setInt(++i, OrganizationHistory.ASSET);
    }
    if (!showDisabledWithEnabled) {
      pst.setBoolean(++i, true);
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
   * Sets the defaultFilters attribute of the OrganizationHistoryList object
   *
   * @param value The new defaultFilters value
   */
  public void setDefaultFilters(boolean value) {
    notes = value;
    activities = value;
    email = value;
    documents = value;
    quotes = value;
    opportunities = value;
    serviceContracts = value;
    tickets = value;
    tasks = value;
    relationships = value;
    assets = value;
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
      OrganizationHistory history = (OrganizationHistory) iterator.next();
      result = history.delete(db);
      if (!result) {
        return false;
      }
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db    Description of the Parameter
   * @param orgId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void moveNotesToAccountHistory(Connection db, int orgId) throws SQLException {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      OrganizationHistory history = (OrganizationHistory) iterator.next();
      history.setReset(true);
      history.setOrgId(orgId);
      history.setContactId(-1);
      history.update(db);
    }
  }

  public void disableNotesInHistory(Connection db, boolean value) throws SQLException {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      OrganizationHistory history = (OrganizationHistory) iterator.next();
      history.setEnabled(value);
      history.update(db);
    }
  }
  
  
  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
}

