//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.communications.base;

import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.communications.base.*;

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


  /**
   *  Sets the campaignId attribute of the RecipientList object
   *
   *@param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Sets the runId attribute of the RecipientList object
   *
   *@param  tmp  The new runId value
   */
  public void setRunId(int tmp) {
    this.runId = tmp;
  }


  /**
   *  Sets the statusId attribute of the RecipientList object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the scheduledDate attribute of the RecipientList object
   *
   *@param  tmp  The new scheduledDate value
   */
  public void setScheduledDate(java.sql.Timestamp tmp) {
    this.scheduledDate = tmp;
  }


  /**
   *  Sets the sentDate attribute of the RecipientList object
   *
   *@param  tmp  The new sentDate value
   */
  public void setSentDate(java.sql.Timestamp tmp) {
    this.sentDate = tmp;
  }


  /**
   *  Sets the hasNullSentDate attribute of the RecipientList object
   *
   *@param  tmp  The new hasNullSentDate value
   */
  public void setHasNullSentDate(boolean tmp) {
    this.hasNullSentDate = tmp;
  }


  /**
   *  Sets the buildContact attribute of the RecipientList object
   *
   *@param  tmp  The new buildContact value
   */
  public void setBuildContact(boolean tmp) {
    this.buildContact = tmp;
  }


  /**
   *  Sets the pagedListInfo attribute of the RecipientList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the statusRangeStart attribute of the RecipientList object
   *
   *@param  tmp  The new statusRangeStart value
   */
  public void setStatusRangeStart(java.sql.Timestamp tmp) {
    this.statusRangeStart = tmp;
  }


  /**
   *  Sets the statusRangeEnd attribute of the RecipientList object
   *
   *@param  tmp  The new statusRangeEnd value
   */
  public void setStatusRangeEnd(java.sql.Timestamp tmp) {
    this.statusRangeEnd = tmp;
  }


  /**
   *  Sets the status attribute of the RecipientList object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   *  Gets the campaignId attribute of the RecipientList object
   *
   *@return    The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   *  Gets the runId attribute of the RecipientList object
   *
   *@return    The runId value
   */
  public int getRunId() {
    return runId;
  }


  /**
   *  Gets the statusId attribute of the RecipientList object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the scheduledDate attribute of the RecipientList object
   *
   *@return    The scheduledDate value
   */
  public java.sql.Timestamp getScheduledDate() {
    return scheduledDate;
  }


  /**
   *  Gets the sentDate attribute of the RecipientList object
   *
   *@return    The sentDate value
   */
  public java.sql.Timestamp getSentDate() {
    return sentDate;
  }


  /**
   *  Gets the hasNullSentDate attribute of the RecipientList object
   *
   *@return    The hasNullSentDate value
   */
  public boolean getHasNullSentDate() {
    return hasNullSentDate;
  }


  /**
   *  Gets the buildContact attribute of the RecipientList object
   *
   *@return    The buildContact value
   */
  public boolean getBuildContact() {
    return buildContact;
  }


  /**
   *  Gets the pagedListInfo attribute of the RecipientList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
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
    } else {
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
        Recipient thisRecipient = (Recipient) i.next();
        thisRecipient.buildContact(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
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


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
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


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  moduleId          Description of the Parameter
   *@param  itemId            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
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


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
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

