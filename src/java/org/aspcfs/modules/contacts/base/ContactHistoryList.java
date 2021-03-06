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
package org.aspcfs.modules.contacts.base;

import org.aspcfs.modules.accounts.base.OrganizationHistory;
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
 *  Description of the Class
 *
 * @author     partha
 * @version    $Id: ContactHistoryList.java,v 1.1.2.6 2005/06/06 20:23:50 partha
 *      Exp $
 * @created    May 27, 2005
 */
public class ContactHistoryList extends ArrayList implements SyncableList {
  //sync api
  public final static String tableName = "history";
  public final static String uniqueField = "history_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  //filters
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int contactId = -1;
  private int level = -1;
  private boolean showDisabledWithEnabled = false;
  protected java.sql.Date startDateRange = null;
  protected java.sql.Date endDateRange = null;
  //Other filters
  private boolean notes = false;
  private boolean activities = false;
  private boolean email = false;
  private boolean quotes = false;
  private boolean opportunities = false;
  private boolean serviceContracts = false;
  private boolean tickets = false;
  private boolean tasks = false;
  private boolean assets = false;


  /**
   *  Gets the tableName attribute of the ContactHistoryList object
   *
   * @return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the ContactHistoryList object
   *
   * @return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the ContactHistoryList object
   *
   * @return    The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Sets the lastAnchor attribute of the ContactHistoryList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the ContactHistoryList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the nextAnchor attribute of the ContactHistoryList object
   *
   * @return    The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Sets the nextAnchor attribute of the ContactHistoryList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the ContactHistoryList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the syncType attribute of the ContactHistoryList object
   *
   * @return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Sets the syncType attribute of the ContactHistoryList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the ContactHistoryList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the ContactHistoryList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the pagedListInfo attribute of the ContactHistoryList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the id attribute of the ContactHistoryList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the ContactHistoryList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ContactHistoryList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the contactId attribute of the ContactHistoryList object
   *
   * @return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Sets the contactId attribute of the ContactHistoryList object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the contactId attribute of the ContactHistoryList object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the level attribute of the ContactHistoryList object
   *
   * @return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Sets the level attribute of the ContactHistoryList object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the ContactHistoryList object
   *
   * @param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Gets the startDateRange attribute of the ContactHistoryList object
   *
   * @return    The startDateRange value
   */
  public java.sql.Date getStartDateRange() {
    return startDateRange;
  }


  /**
   *  Sets the startDateRange attribute of the ContactHistoryList object
   *
   * @param  tmp  The new startDateRange value
   */
  public void setStartDateRange(java.sql.Date tmp) {
    this.startDateRange = tmp;
  }


  /**
   *  Gets the endDateRange attribute of the ContactHistoryList object
   *
   * @return    The endDateRange value
   */
  public java.sql.Date getEndDateRange() {
    return endDateRange;
  }


  /**
   *  Sets the endDateRange attribute of the ContactHistoryList object
   *
   * @param  tmp  The new endDateRange value
   */
  public void setEndDateRange(java.sql.Date tmp) {
    this.endDateRange = tmp;
  }


  /**
   *  Gets the notes attribute of the ContactHistoryList object
   *
   * @return    The notes value
   */
  public boolean getNotes() {
    return notes;
  }


  /**
   *  Sets the notes attribute of the ContactHistoryList object
   *
   * @param  tmp  The new notes value
   */
  public void setNotes(boolean tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the notes attribute of the ContactHistoryList object
   *
   * @param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the activities attribute of the ContactHistoryList object
   *
   * @return    The activities value
   */
  public boolean getActivities() {
    return activities;
  }


  /**
   *  Sets the activities attribute of the ContactHistoryList object
   *
   * @param  tmp  The new activities value
   */
  public void setActivities(boolean tmp) {
    this.activities = tmp;
  }


  /**
   *  Sets the activities attribute of the ContactHistoryList object
   *
   * @param  tmp  The new activities value
   */
  public void setActivities(String tmp) {
    this.activities = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the email attribute of the ContactHistoryList object
   *
   * @return    The email value
   */
  public boolean getEmail() {
    return email;
  }


  /**
   *  Sets the email attribute of the ContactHistoryList object
   *
   * @param  tmp  The new email value
   */
  public void setEmail(boolean tmp) {
    this.email = tmp;
  }


  /**
   *  Sets the email attribute of the ContactHistoryList object
   *
   * @param  tmp  The new email value
   */
  public void setEmail(String tmp) {
    this.email = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the quotes attribute of the ContactHistoryList object
   *
   * @return    The quotes value
   */
  public boolean getQuotes() {
    return quotes;
  }


  /**
   *  Sets the quotes attribute of the ContactHistoryList object
   *
   * @param  tmp  The new quotes value
   */
  public void setQuotes(boolean tmp) {
    this.quotes = tmp;
  }


  /**
   *  Sets the quotes attribute of the ContactHistoryList object
   *
   * @param  tmp  The new quotes value
   */
  public void setQuotes(String tmp) {
    this.quotes = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the opportunities attribute of the ContactHistoryList object
   *
   * @return    The opportunities value
   */
  public boolean getOpportunities() {
    return opportunities;
  }


  /**
   *  Sets the opportunities attribute of the ContactHistoryList object
   *
   * @param  tmp  The new opportunities value
   */
  public void setOpportunities(boolean tmp) {
    this.opportunities = tmp;
  }


  /**
   *  Sets the opportunities attribute of the ContactHistoryList object
   *
   * @param  tmp  The new opportunities value
   */
  public void setOpportunities(String tmp) {
    this.opportunities = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the serviceContracts attribute of the ContactHistoryList object
   *
   * @return    The serviceContracts value
   */
  public boolean getServiceContracts() {
    return serviceContracts;
  }


  /**
   *  Sets the serviceContracts attribute of the ContactHistoryList object
   *
   * @param  tmp  The new serviceContracts value
   */
  public void setServiceContracts(boolean tmp) {
    this.serviceContracts = tmp;
  }


  /**
   *  Sets the serviceContracts attribute of the ContactHistoryList object
   *
   * @param  tmp  The new serviceContracts value
   */
  public void setServiceContracts(String tmp) {
    this.serviceContracts = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the tickets attribute of the ContactHistoryList object
   *
   * @return    The tickets value
   */
  public boolean getTickets() {
    return tickets;
  }


  /**
   *  Sets the tickets attribute of the ContactHistoryList object
   *
   * @param  tmp  The new tickets value
   */
  public void setTickets(boolean tmp) {
    this.tickets = tmp;
  }


  /**
   *  Sets the tickets attribute of the ContactHistoryList object
   *
   * @param  tmp  The new tickets value
   */
  public void setTickets(String tmp) {
    this.tickets = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the tasks attribute of the ContactHistoryList object
   *
   * @return    The tasks value
   */
  public boolean getTasks() {
    return tasks;
  }


  /**
   *  Sets the tasks attribute of the ContactHistoryList object
   *
   * @param  tmp  The new tasks value
   */
  public void setTasks(boolean tmp) {
    this.tasks = tmp;
  }


  /**
   *  Sets the tasks attribute of the ContactHistoryList object
   *
   * @param  tmp  The new tasks value
   */
  public void setTasks(String tmp) {
    this.tasks = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the assets attribute of the ContactHistoryList object
   *
   * @return    The assets value
   */
  public boolean getAssets() {
    return assets;
  }


  /**
   *  Sets the assets attribute of the ContactHistoryList object
   *
   * @param  tmp  The new assets value
   */
  public void setAssets(boolean tmp) {
    this.assets = tmp;
  }


  /**
   *  Sets the assets attribute of the ContactHistoryList object
   *
   * @param  tmp  The new assets value
   */
  public void setAssets(String tmp) {
    this.assets = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the showDisabledWithEnabled attribute of the ContactHistoryList
   *  object
   *
   * @return    The showDisabledWithEnabled value
   */
  public boolean getShowDisabledWithEnabled() {
    return showDisabledWithEnabled;
  }


  /**
   *  Sets the showDisabledWithEnabled attribute of the ContactHistoryList
   *  object
   *
   * @param  tmp  The new showDisabledWithEnabled value
   */
  public void setShowDisabledWithEnabled(boolean tmp) {
    this.showDisabledWithEnabled = tmp;
  }


  /**
   *  Sets the showDisabledWithEnabled attribute of the ContactHistoryList
   *  object
   *
   * @param  tmp  The new showDisabledWithEnabled value
   */
  public void setShowDisabledWithEnabled(String tmp) {
    this.showDisabledWithEnabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Constructor for the ContactHistoryList object
   */
  public ContactHistoryList() { }

  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
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
    		"WHERE history_id > 0 ");

    createFilter(db, sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      PreparedStatement pst = db.prepareStatement(
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
        pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
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
      pagedListInfo.setDefaultSort("entered", "desc");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY entered desc");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "ch.* " +
        "FROM history ch " +
    		"WHERE history_id > 0 ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    return pst;
  }

  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      ContactHistory thisHistoryElement = this.getObject(rs);
      this.add(thisHistoryElement);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }

  /**
   *  Gets the object attribute of the ContactHistoryList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public ContactHistory getObject(ResultSet rs) throws SQLException {
    ContactHistory thisHistoryElement = new ContactHistory(rs);
    return thisHistoryElement;
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of the Parameter
   * @param  db         Description of the Parameter
   */
  protected void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (id != -1) {
      sqlFilter.append("AND history_id = ? ");
    }
    if (contactId != -1) {
      sqlFilter.append("AND contact_id = ? ");
    }
    if (level != -1) {
      sqlFilter.append("AND " + DatabaseUtils.addQuotes(db, "level") + " > ? ");
    }
    if (startDateRange != null) {
      sqlFilter.append("AND modified > ? ");
    }
    if (endDateRange != null) {
      sqlFilter.append("AND modified < ? ");
    }
    if (!notes) {
      sqlFilter.append("AND link_object_id NOT IN (?) ");
    }
    if (!activities) {
      sqlFilter.append("AND link_object_id NOT IN (?, ?) ");
    }
    if (!email) {
      sqlFilter.append("AND link_object_id NOT IN (?, ?) ");
    }
    if (!quotes) {
      sqlFilter.append("AND link_object_id NOT IN (?) ");
    }
    if (!opportunities) {
      sqlFilter.append("AND link_object_id NOT IN (?) ");
    }
    if (!serviceContracts) {
      sqlFilter.append("AND link_object_id NOT IN (?) ");
    }
    if (!tickets) {
      sqlFilter.append("AND link_object_id NOT IN (?) ");
    }
    if (!tasks) {
      sqlFilter.append("AND link_object_id NOT IN (?) ");
    }
    if (!assets) {
      sqlFilter.append("AND link_object_id NOT IN (?) ");
    }
    if (!showDisabledWithEnabled) {
      sqlFilter.append("AND enabled = ? ");
    }

    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND entered > ? ");
      }
      sqlFilter.append("AND entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND modified > ? ");
      sqlFilter.append("AND entered < ? ");
      sqlFilter.append("AND modified < ? ");
    }
  }

  /**
   *  Description of the Method
   *
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id != -1) {
      pst.setInt(++i, this.getId());
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
   *  Sets the defaultFilters attribute of the ContactHistoryList object
   *
   * @param  value  The new defaultFilters value
   */
  public void setDefaultFilters(boolean value) {
    notes = value;
    activities = value;
    email = value;
    quotes = value;
    opportunities = value;
    serviceContracts = value;
    tickets = value;
    tasks = value;
    assets = value;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  value          Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void disableNotesInHistory(Connection db, boolean value) throws SQLException {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ContactHistory history = (ContactHistory) iterator.next();
      if (history.getEnabled() != value) {
        history.setEnabled(value);
        history.update(db);
      }
    }
  }
}

