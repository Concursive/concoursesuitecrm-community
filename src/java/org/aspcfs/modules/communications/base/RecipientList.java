//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.sql.*;
import java.util.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    November 26, 2001
 *@version    $Id$
 */
public class RecipientList extends Vector {

  private PagedListInfo pagedListInfo = null;
  private int campaignId = -1;
  private int runId = -1;
  private int statusId = -1;
  private java.sql.Timestamp scheduledDate = null;
  private java.sql.Timestamp sentDate = null;
  private boolean hasNullSentDate = false;
  private boolean buildContact = false;

  private java.sql.Timestamp statusRangeStart = null;
  private java.sql.Timestamp statusRangeEnd = null;
  private String status = null;
  
  /**
   *  Constructor for the RecipientList object
   *
   *@since
   */
  public RecipientList() { }
  
  public void setCampaignId(int tmp) { this.campaignId = tmp; }
  public void setRunId(int tmp) { this.runId = tmp; }
  public void setStatusId(int tmp) { this.statusId = tmp; }
  public void setScheduledDate(java.sql.Timestamp tmp) { this.scheduledDate = tmp; }
  public void setSentDate(java.sql.Timestamp tmp) { this.sentDate = tmp; }
  public void setHasNullSentDate(boolean tmp) { this.hasNullSentDate = tmp; }
  public void setBuildContact(boolean tmp) { this.buildContact = tmp; }
  public void setPagedListInfo(PagedListInfo tmp) { this.pagedListInfo = tmp; }
  public void setStatusRangeStart(java.sql.Timestamp tmp) {
    this.statusRangeStart = tmp;
  }
  public void setStatusRangeEnd(java.sql.Timestamp tmp) {
    this.statusRangeEnd = tmp;
  }
  public void setStatus(String tmp) { this.status = tmp; }

  public int getCampaignId() { return campaignId; }
  public int getRunId() { return runId; }
  public int getStatusId() { return statusId; }
  public java.sql.Timestamp getScheduledDate() { return scheduledDate; }
  public java.sql.Timestamp getSentDate() { return sentDate; }
  public boolean getHasNullSentDate() { return hasNullSentDate; }
  public boolean getBuildContact() { return buildContact; }
  public PagedListInfo getPagedListInfo() { return pagedListInfo; }


  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    sqlCount.append(
      "SELECT COUNT(*) AS recordcount " +
      "FROM scheduled_recipient r " +
      "LEFT JOIN contact c ON (r.contact_id = c.contact_id) " +
      "WHERE r.id > -1 ");
        
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
			pst.close();
			rs.close();

			//Determine the offset, based on the filter, for the first record to show
			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString() +
          "AND c.namelast < ? ");
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
			pagedListInfo.setDefaultSort("id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
		}	else {
			sqlOrder.append("ORDER BY id ");
		}
    
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
      "r.* FROM scheduled_recipient r " +
      "WHERE r.id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }

    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      Recipient thisRecipient = new Recipient(rs);
      this.addElement(thisRecipient);
    }
    rs.close();
    pst.close();
    
    if (buildContact) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        Recipient thisRecipient = (Recipient)i.next();
        thisRecipient.buildContact(db);
      }
    }
  }


  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (campaignId > -1) {
      sqlFilter.append("AND campaign_id = " + campaignId + " ");
    }
    if (runId > -1) {
      sqlFilter.append("AND run_id = " + runId + " ");
    }
    if (statusId > -1) {
      sqlFilter.append("AND status_id = " + statusId + " ");
    }
    if (scheduledDate != null) {
      sqlFilter.append("AND scheduled_date = ? ");
    }
    if (sentDate != null) {
      sqlFilter.append("AND sent_date = ? ");
    }
    if (hasNullSentDate) {
      sqlFilter.append("AND sent_date IS NULL ");
    }
    if (statusRangeStart != null) {
      sqlFilter.append("AND status_date >= ? ");
    }
    if (statusRangeEnd != null) {
      sqlFilter.append("AND status_date <= ? ");
    }
    if (status != null) {
      sqlFilter.append("AND status = ? ");
    }
  }


  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (scheduledDate != null) {
      pst.setTimestamp(++i, scheduledDate);
    }
    if (sentDate != null) {
      pst.setTimestamp(++i, sentDate);
    }
    if (statusRangeStart != null) {
      pst.setTimestamp(++i, statusRangeStart);
    }
    if (statusRangeEnd != null) {
      pst.setTimestamp(++i, statusRangeEnd);
    }
    if (status != null) {
      pst.setString(++i, status);
    }
    return i;
  }
  
  public static int retrieveRecordCount(Connection db, int moduleId, int itemId) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
      "SELECT COUNT(*) as itemcount " +
      "FROM scheduled_recipient s " +
      "WHERE id > 0 ");
    if (moduleId == Constants.CONTACTS) {  
      sql.append("AND s.contact_id = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (moduleId == Constants.CONTACTS) {
      pst.setInt(1, itemId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("itemcount");
    }
    rs.close();
    pst.close();
    return count;
  }
  
  public int queryRecordCount(Connection db) throws SQLException {
    int recordCount = 0;
    StringBuffer sqlFilter = new StringBuffer();
    String sqlCount =
      "SELECT COUNT(*) AS recordcount " +
      "FROM scheduled_recipient r " +
      "LEFT JOIN contact c ON (r.contact_id = c.contact_id) " +
      "WHERE r.id > -1 ";
    createFilter(sqlFilter);
    PreparedStatement pst = db.prepareStatement(sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = DatabaseUtils.getInt(rs, "recordcount", 0);
    }
    pst.close();
    rs.close();
    return recordCount;
  }
}

