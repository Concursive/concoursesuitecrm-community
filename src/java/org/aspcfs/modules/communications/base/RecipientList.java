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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     mrajkowski
 * @created    November 26, 2001
 * @version    $Id: RecipientList.java,v 1.10 2004/09/16 19:24:01 mrajkowski Exp
 *      $
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

  private boolean onlyResponded = false;
  private int surveyId = -1;
  private int contactId = -1;
  private boolean allowDuplicates = false;


  /**
   *  Constructor for the RecipientList object
   */
  public RecipientList() { }


  /**
   *  Sets the campaignId attribute of the RecipientList object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Sets the runId attribute of the RecipientList object
   *
   * @param  tmp  The new runId value
   */
  public void setRunId(int tmp) {
    this.runId = tmp;
  }


  /**
   *  Sets the statusId attribute of the RecipientList object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the scheduledDate attribute of the RecipientList object
   *
   * @param  tmp  The new scheduledDate value
   */
  public void setScheduledDate(java.sql.Timestamp tmp) {
    this.scheduledDate = tmp;
  }


  /**
   *  Sets the sentDate attribute of the RecipientList object
   *
   * @param  tmp  The new sentDate value
   */
  public void setSentDate(java.sql.Timestamp tmp) {
    this.sentDate = tmp;
  }


  /**
   *  Sets the hasNullSentDate attribute of the RecipientList object
   *
   * @param  tmp  The new hasNullSentDate value
   */
  public void setHasNullSentDate(boolean tmp) {
    this.hasNullSentDate = tmp;
  }


  /**
   *  Sets the buildContact attribute of the RecipientList object
   *
   * @param  tmp  The new buildContact value
   */
  public void setBuildContact(boolean tmp) {
    this.buildContact = tmp;
  }


  /**
   *  Sets the pagedListInfo attribute of the RecipientList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the statusRangeStart attribute of the RecipientList object
   *
   * @param  tmp  The new statusRangeStart value
   */
  public void setStatusRangeStart(java.sql.Timestamp tmp) {
    this.statusRangeStart = tmp;
  }


  /**
   *  Sets the statusRangeEnd attribute of the RecipientList object
   *
   * @param  tmp  The new statusRangeEnd value
   */
  public void setStatusRangeEnd(java.sql.Timestamp tmp) {
    this.statusRangeEnd = tmp;
  }


  /**
   *  Sets the status attribute of the RecipientList object
   *
   * @param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the onlyResponded attribute of the RecipientList object
   *
   * @param  tmp  The new onlyResponded value
   */
  public void setOnlyResponded(boolean tmp) {
    this.onlyResponded = tmp;
  }


  /**
   *  Sets the onlyResponded attribute of the RecipientList object
   *
   * @param  tmp  The new onlyResponded value
   */
  public void setOnlyResponded(String tmp) {
    this.onlyResponded = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the surveyId attribute of the RecipientList object
   *
   * @param  tmp  The new surveyId value
   */
  public void setSurveyId(int tmp) {
    this.surveyId = tmp;
  }


  /**
   *  Sets the surveyId attribute of the RecipientList object
   *
   * @param  tmp  The new surveyId value
   */
  public void setSurveyId(String tmp) {
    this.surveyId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contactId attribute of the RecipientList object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the contactId attribute of the RecipientList object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the campaignId attribute of the RecipientList object
   *
   * @return    The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   *  Gets the runId attribute of the RecipientList object
   *
   * @return    The runId value
   */
  public int getRunId() {
    return runId;
  }


  /**
   *  Gets the statusId attribute of the RecipientList object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the scheduledDate attribute of the RecipientList object
   *
   * @return    The scheduledDate value
   */
  public java.sql.Timestamp getScheduledDate() {
    return scheduledDate;
  }


  /**
   *  Gets the sentDate attribute of the RecipientList object
   *
   * @return    The sentDate value
   */
  public java.sql.Timestamp getSentDate() {
    return sentDate;
  }


  /**
   *  Gets the hasNullSentDate attribute of the RecipientList object
   *
   * @return    The hasNullSentDate value
   */
  public boolean getHasNullSentDate() {
    return hasNullSentDate;
  }


  /**
   *  Gets the buildContact attribute of the RecipientList object
   *
   * @return    The buildContact value
   */
  public boolean getBuildContact() {
    return buildContact;
  }


  /**
   *  Gets the pagedListInfo attribute of the RecipientList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the allowDuplicates attribute of the RecipientList object
   *
   * @return    The allowDuplicates value
   */
  public boolean getAllowDuplicates() {
    return allowDuplicates;
  }


  /**
   *  Sets the allowDuplicates attribute of the RecipientList object
   *
   * @param  tmp  The new allowDuplicates value
   */
  public void setAllowDuplicates(boolean tmp) {
    this.allowDuplicates = tmp;
  }


  /**
   *  Sets the allowDuplicates attribute of the RecipientList object
   *
   * @param  tmp  The new allowDuplicates value
   */
  public void setAllowDuplicates(String tmp) {
    this.allowDuplicates = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
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
      rs.close();
      pst.close();

      //Determine the offset, based on the filter, for the first record to show
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
                sqlFilter.toString() +
                "AND " + DatabaseUtils.toLowerCase(db) + "(c.namelast) < ? ");
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
      pagedListInfo.setDefaultSort("c.namelast", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY c.namelast ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "r.* FROM scheduled_recipient r " +
        "LEFT JOIN contact c ON (r.contact_id = c.contact_id) " +
        "WHERE r.id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
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
   * @param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (campaignId > -1) {
      sqlFilter.append("AND campaign_id = ? ");
    }
    if (runId > -1) {
      sqlFilter.append("AND run_id = ? ");
    }
    if (statusId > -1) {
      sqlFilter.append("AND status_id = ? ");
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
    if (this.onlyResponded) {
      sqlFilter.append(
          "AND r.contact_id NOT IN (SELECT contact_id FROM active_survey_responses WHERE active_survey_id = ?) ");
    }
    if (this.contactId != -1) {
      sqlFilter.append("AND r.contact_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (campaignId > -1) {
      pst.setInt(++i, campaignId);
    }
    if (runId > -1) {
      pst.setInt(++i, runId);
    }
    if (statusId > -1) {
      pst.setInt(++i, statusId);
    }
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
    if (onlyResponded) {
      pst.setInt(++i, surveyId);
    }
    if (this.contactId != -1) {
      pst.setInt(++i, contactId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  moduleId       Description of the Parameter
   * @param  itemId         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int moduleId, int itemId) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT COUNT("+(moduleId == Constants.CONTACTS?"DISTINCT c.campaign_id":"*")+") as itemcount " +
        "FROM scheduled_recipient s " +
        (moduleId == Constants.CONTACTS?"LEFT JOIN campaign c ON (s.campaign_id = c.campaign_id) ":" ") +
        "WHERE id > 0 " +
        (moduleId == Constants.CONTACTS?"AND c.trashed_date IS NULL ":""));
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
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      Recipient recipient = (Recipient) itr.next();
      recipient.delete(db);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
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
    PreparedStatement pst = db.prepareStatement(
        sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = DatabaseUtils.getInt(rs, "recordcount", 0);
    }
    pst.close();
    rs.close();
    return recordCount;
  }


  /**
   *  Adds a feature to the Recipient attribute of the RecipientList object
   *
   * @param  db                The feature to be added to the Recipient
   *      attribute
   * @param  contactId         The feature to be added to the Recipient
   *      attribute
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean addRecipient(Connection db, String contactId) throws SQLException {
    if (!this.getAllowDuplicates()) {
      Iterator itr = this.iterator();
      while (itr.hasNext()) {
        Recipient recipient = (Recipient) itr.next();
        if (recipient.getContact().getId() == Integer.parseInt(contactId.trim())) {
          return false;
        }
      }
    }
    Recipient recipient = new Recipient();
    recipient.setContactId(contactId);
    recipient.setCampaignId(this.getCampaignId());
    recipient.setAllowDuplicates(this.getAllowDuplicates());
    recipient.insert(db);
    return (recipient.getId() != -1);
  }
}

