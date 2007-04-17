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
package org.aspcfs.modules.contacts.base;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

/**
 * Description of the Class
 *
 * @author chris
 * @version $Id$
 * @created January 8, 2002
 */
public class CallList extends ArrayList implements SyncableList {

  public final static String tableName = "call_log";
  public final static String uniqueField = "call_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;

  protected PagedListInfo pagedListInfo = null;
  protected int contactId = -1;
  protected int orgId = -1;
  protected int oppHeaderId = -1;
  protected int enteredBy = -1;
  protected int owner = -1;
  protected boolean hasAlertDate = false;
  protected java.sql.Timestamp alertDate = null;
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;
  protected java.sql.Timestamp notificationRangeStart = null;
  protected java.sql.Timestamp notificationRangeEnd = null;
  protected boolean onlyPending = false;
  protected boolean excludeCanceled = false;
  protected boolean onlyCompleted = false;
  protected boolean onlyCompletedOrCanceled = false;
  protected boolean allContactsInAccount = false;
  protected int contactOrgId = -1;
  protected java.sql.Timestamp trashedDate = null;
  protected boolean includeOnlyTrashed = false;
  protected int oppCallsOnly = Constants.UNDEFINED;
  protected int avoidDisabledContacts = Constants.UNDEFINED;

  /**
   * Constructor for the CallList object
   */
  public CallList() {
  }

  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static Call getObject(ResultSet rs) throws SQLException {
    Call call = new Call(rs);
    return call;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getTableName()
   */
  public String getTableName() {
    return tableName;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getUniqueField()
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.sql.Timestamp)
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.lang.String)
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = java.sql.Timestamp.valueOf(lastAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.sql.Timestamp)
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.lang.String)
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = java.sql.Timestamp.valueOf(nextAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(int)
   */
  public void setSyncType(int syncType) {
    this.syncType = syncType;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }

  /**
   * Gets the onlyCompletedOrCancelled attribute of the CallList object
   *
   * @return onlyCompletedOrCancelled The onlyCompletedOrCancelled value
   */
  public boolean isOnlyCompletedOrCanceled() {
    return this.onlyCompletedOrCanceled;
  }


  /**
   * Sets the onlyCompletedOrCancelled attribute of the CallList object
   *
   * @param tmp The new onlyCompletedOrCancelled value
   */
  public void setOnlyCompletedOrCanceled(boolean tmp) {
    this.onlyCompletedOrCanceled = tmp;
  }

  /**
   * Sets the PagedListInfo attribute of the CallList object
   *
   * @param tmp The new PagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the ContactId attribute of the CallList object
   *
   * @param tmp The new ContactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the ContactId attribute of the CallList object
   *
   * @param tmp The new ContactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Sets the OrgId attribute of the CallList object
   *
   * @param tmp The new OrgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }

  /**
   * Sets the OrgId attribute of the CallList object
   *
   * @param tmp The new OrgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the oppHeaderId attribute of the CallList object
   *
   * @param oppHeaderId The new oppHeaderId value
   */
  public void setOppHeaderId(int oppHeaderId) {
    this.oppHeaderId = oppHeaderId;
  }


  /**
   * Sets the EnteredBy attribute of the CallList object
   *
   * @param tmp The new EnteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the AlertDate attribute of the CallList object
   *
   * @param alertDate The new AlertDate value
   */
  public void setAlertDate(java.sql.Timestamp alertDate) {
    this.alertDate = alertDate;
  }


  /**
   * Sets the HasAlertDate attribute of the CallList object
   *
   * @param tmp The new HasAlertDate value
   */
  public void setHasAlertDate(boolean tmp) {
    this.hasAlertDate = tmp;
  }


  /**
   * Sets the oppHeaderId attribute of the CallList object
   *
   * @param oppHeaderId The new oppHeaderId value
   */
  public void setOppHeaderId(String oppHeaderId) {
    this.oppHeaderId = Integer.parseInt(oppHeaderId);
  }


  /**
   * Sets the alertRangeStart attribute of the Task object
   *
   * @param alertRangeStart The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp alertRangeStart) {
    this.alertRangeStart = alertRangeStart;
  }


  /**
   * Sets the alertRangeStart attribute of the CallList object
   *
   * @param tmp The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the owner attribute of the CallList object
   *
   * @param owner The new owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   * Gets the owner attribute of the CallList object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }

  /**
   *  Sets the alertRangeStart attribute of the CallList object
   *
   * @param  tmp  The new alertRangeStart value
   */

  /**
   * Sets the alertRangeEnd attribute of the CallList object
   *
   * @param tmp The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   * Sets the alertRangeEnd attribute of the CallList object
   *
   * @param tmp The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the onlyPending attribute of the CallList object
   *
   * @param tmp The new onlyPending value
   */
  public void setOnlyPending(boolean tmp) {
    this.onlyPending = tmp;
  }


  /**
   * Sets the onlyPending attribute of the CallList object
   *
   * @param tmp The new onlyPending value
   */
  public void setOnlyPending(String tmp) {
    this.onlyPending = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the excludeCanceled attribute of the CallList object
   *
   * @param excludeCanceled The new excludeCanceled value
   */
  public void setExcludeCanceled(boolean excludeCanceled) {
    this.excludeCanceled = excludeCanceled;
  }


  /**
   * Sets the onlyCompleted attribute of the CallList object
   *
   * @param onlyCompleted The new onlyCompleted value
   */
  public void setOnlyCompleted(boolean onlyCompleted) {
    this.onlyCompleted = onlyCompleted;
  }


  /**
   * Sets the notificationRangeStart attribute of the CallList object
   *
   * @param notificationRangeStart The new notificationRangeStart value
   */
  public void setNotificationRangeStart(java.sql.Timestamp notificationRangeStart) {
    this.notificationRangeStart = notificationRangeStart;
  }


  /**
   * Sets the notificationRangeEnd attribute of the CallList object
   *
   * @param notificationRangeEnd The new notificationRangeEnd value
   */
  public void setNotificationRangeEnd(java.sql.Timestamp notificationRangeEnd) {
    this.notificationRangeEnd = notificationRangeEnd;
  }


  /**
   * Sets the allContactsInAccount attribute of the CallList object
   *
   * @param allContactsInAccount The new allContactsInAccount value
   * @param contactOrgId         The new allContactsInAccount value
   */
  public void setAllContactsInAccount(boolean allContactsInAccount, int contactOrgId) {
    this.allContactsInAccount = allContactsInAccount;
    this.contactOrgId = contactOrgId;
  }


  /**
   * Gets the allContactsInAccount attribute of the CallList object
   *
   * @return The allContactsInAccount value
   */
  public boolean getAllContactsInAccount() {
    return allContactsInAccount;
  }


  /**
   * Gets the contactOrgId attribute of the CallList object
   *
   * @return The contactOrgId value
   */
  public int getContactOrgId() {
    return contactOrgId;
  }


  /**
   * Gets the notificationRangeStart attribute of the CallList object
   *
   * @return The notificationRangeStart value
   */
  public java.sql.Timestamp getNotificationRangeStart() {
    return notificationRangeStart;
  }


  /**
   * Gets the notificationRangeEnd attribute of the CallList object
   *
   * @return The notificationRangeEnd value
   */
  public java.sql.Timestamp getNotificationRangeEnd() {
    return notificationRangeEnd;
  }


  /**
   * Gets the onlyCompleted attribute of the CallList object
   *
   * @return The onlyCompleted value
   */
  public boolean getOnlyCompleted() {
    return onlyCompleted;
  }


  /**
   * Gets the excludeCanceled attribute of the CallList object
   *
   * @return The excludeCanceled value
   */
  public boolean getExcludeCanceled() {
    return excludeCanceled;
  }


  /**
   * Gets the alertRangeStart attribute of the CallList object
   *
   * @return The alertRangeStart value
   */
  public java.sql.Timestamp getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   * Gets the alertRangeEnd attribute of the CallList object
   *
   * @return The alertRangeEnd value
   */
  public java.sql.Timestamp getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   * Gets the AlertDate attribute of the CallList object
   *
   * @return The AlertDate value
   */
  public java.sql.Timestamp getAlertDate() {
    return alertDate;
  }


  /**
   * Gets the EnteredBy attribute of the CallList object
   *
   * @return The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the HasAlertDate attribute of the CallList object
   *
   * @return The HasAlertDate value
   */
  public boolean getHasAlertDate() {
    return hasAlertDate;
  }

  /**
   * Sets the includeOnlyTrashed attribute of the CallList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   * Sets the includeOnlyTrashed attribute of the CallList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the includeOnlyTrashed attribute of the CallList object
   *
   * @return The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   * Sets the trashedDate attribute of the CallList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the CallList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the trashedDate attribute of the CallList object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   * Gets the lastAnchor attribute of the CallList object
   *
   * @return The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   * Gets the nextAnchor attribute of the CallList object
   *
   * @return The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   * Gets the syncType attribute of the CallList object
   *
   * @return The syncType value
   */
  public int getSyncType() {
    return syncType;
  }

  /**
   * Gets the PagedListInfo attribute of the CallList object
   *
   * @return The PagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the ContactId attribute of the CallList object
   *
   * @return The ContactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Gets the OrgId attribute of the CallList object
   *
   * @return The OrgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Gets the oppHeaderId attribute of the CallList object
   *
   * @return The oppHeaderId value
   */
  public int getOppHeaderId() {
    return oppHeaderId;
  }


  /**
   * Gets the onlyPending attribute of the CallList object
   *
   * @return The onlyPending value
   */
  public boolean getOnlyPending() {
    return onlyPending;
  }


  /**
   * Gets the oppCallsOnly attribute of the CallList object
   *
   * @return The oppCallsOnly value
   */
  public int getOppCallsOnly() {
    return oppCallsOnly;
  }


  /**
   * Sets the oppCallsOnly attribute of the CallList object
   *
   * @param tmp The new oppCallsOnly value
   */
  public void setOppCallsOnly(int tmp) {
    this.oppCallsOnly = tmp;
  }


  /**
   * Sets the oppCallsOnly attribute of the CallList object
   *
   * @param tmp The new oppCallsOnly value
   */
  public void setOppCallsOnly(String tmp) {
    this.oppCallsOnly = Integer.parseInt(tmp);
  }


  /**
   * Gets the avoidDisabledContacts attribute of the CallList object
   *
   * @return The avoidDisabledContacts value
   */
  public int getAvoidDisabledContacts() {
    return avoidDisabledContacts;
  }


  /**
   * Sets the avoidDisabledContacts attribute of the CallList object
   *
   * @param tmp The new avoidDisabledContacts value
   */
  public void setAvoidDisabledContacts(int tmp) {
    this.avoidDisabledContacts = tmp;
  }


  /**
   * Sets the avoidDisabledContacts attribute of the CallList object
   *
   * @param tmp The new avoidDisabledContacts value
   */
  public void setAvoidDisabledContacts(String tmp) {
    this.avoidDisabledContacts = Integer.parseInt(tmp);
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param timeZone Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public HashMap queryRecordCount(Connection db, TimeZone timeZone) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;

    HashMap events = new HashMap();
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlTail = new StringBuffer();

    String sqlDate = (onlyPending ? "alertdate" : "entered");

    createFilter(sqlFilter);

    sqlSelect.append(
        "SELECT " + sqlDate + " AS " + DatabaseUtils.addQuotes(db, "date") + " " +
            "FROM call_log c " +
            "WHERE call_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      String alertDate = DateUtils.getServerToUserDateString(
          timeZone, DateFormat.SHORT, rs.getTimestamp("date"));
      if (events.containsKey(alertDate)) {
        Integer count = (Integer) events.get(alertDate);
        int tmpCount = count.intValue();
        events.put(alertDate, new Integer(++tmpCount));
      } else {
        events.put(alertDate, new Integer(1));
      }
    }
    rs.close();
    pst.close();
    return events;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildShortList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    createFilter(sqlFilter);
    sqlSelect.append(
        "SELECT c.call_id, c.subject, c.contact_id, c.opp_id, c.opp_id, c.alertdate, c.alert, " +
            "c.owner, c.notes, c." + DatabaseUtils.addQuotes(db, "length") + ", c.followup_notes, c.complete_date, c.org_id as contact_org_id, ct.namelast as ctlast, ct.namefirst as ctfirst, " +
            "ct.org_name as ctcompany, o.name as orgname, c.status_id, c.entered, p.description as priority, fct.namelast AS fctlast, fct.namefirst AS fctfirst, fct.org_name AS fctcompany, " +
            "c.followup_contact_id " +
            "FROM call_log c " +
            "LEFT JOIN contact fct ON (c.followup_contact_id = fct.contact_id) " +
            "LEFT JOIN lookup_call_priority p ON (c.priority_id = p.code) " +
            "LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
            "LEFT JOIN organization o ON (c.org_id = o.org_id) " +
            "WHERE c.call_id > -1 ");
    if (onlyCompleted) {
      sqlOrder.append("ORDER BY c.entered ");
    } else {
      sqlOrder.append("ORDER BY c.alertdate, p.weight DESC ");
    }
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Call thisCall = new Call();
      thisCall.setId(rs.getInt("call_id"));
      thisCall.setSubject(rs.getString("subject"));
      thisCall.setContactId(DatabaseUtils.getInt(rs, "contact_id"));
      thisCall.setOppHeaderId(DatabaseUtils.getInt(rs, "opp_id"));

      thisCall.setAlertDate(rs.getTimestamp("alertdate"));
      thisCall.setAlertText(rs.getString("alert"));
      thisCall.setOwner(rs.getInt("owner"));
      thisCall.setNotes(rs.getString("notes"));
      thisCall.setLength(rs.getInt("length"));
      thisCall.setFollowupNotes(rs.getString("followup_notes"));
      thisCall.setCompleteDate(rs.getTimestamp("complete_date"));
      //contact details
      thisCall.setContactOrgId(DatabaseUtils.getInt(rs, "contact_org_id"));
      String contactName = Contact.getNameLastFirst(
          rs.getString("ctlast"), rs.getString("ctfirst"));
      if (contactName == null || "".equals(contactName)) {
        contactName = rs.getString("ctcompany");
      }
      thisCall.setOrgName(rs.getString("orgname"));
      thisCall.setContactName(contactName);
      thisCall.setStatusId(rs.getInt("status_id"));
      thisCall.setEntered(rs.getTimestamp("entered"));
      thisCall.setPriorityString(rs.getString("priority"));
      thisCall.setFollowupContactId(DatabaseUtils.getInt(rs, "followup_contact_id"));
      String followupContactName = Contact.getNameLastFirst(
          rs.getString("fctlast"), rs.getString("fctfirst"));
      if (followupContactName == null || "".equals(followupContactName)) {
        followupContactName = rs.getString("fctcompany");
      }
      thisCall.setFollowupContactName(followupContactName);
      //build Contact Details
      if (thisCall.getContactId() > 0) {
        Contact thisContact = new Contact();
        thisContact.setId(thisCall.getContactId());
        thisCall.setContact(thisContact);
      } else if (thisCall.getFollowupContactId() > 0) {
        Contact thisContact = new Contact();
        thisContact.setId(thisCall.getFollowupContactId());
        thisCall.setContact(thisContact);
      }

      //add call to list
      this.add(thisCall);
    }
    rs.close();
    pst.close();
    // Build resources
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Call thisCall = (Call) i.next();
      thisCall.getContact().buildPhoneNumberList(db);
    }
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

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "c.call_id, c.org_id, c.contact_id, c.opp_id, c.call_type_id, c." + DatabaseUtils.addQuotes(db, "length") + ", " +
            "c.subject, c.notes, c.entered, c.enteredby, c.modified, c.modifiedby, c.alertdate, " +
            "c.followup_date, c.parent_id, c.owner, c.assignedby, c.assign_date, c.completedby, " +
            "c.complete_date, c.result_id, c.priority_id, c.status_id, c.reminder_value, c.reminder_type_id, " +
            "c.alert_call_type_id, c.followup_contact_id, c.alert, c.followup_notes, c.alertdate_timezone, c.trashed_date, t.*, talert.description as alertType, " +
            "ct.namelast as ctlast, ct.namefirst as ctfirst, ct.org_name as ctcompany, fct.namelast AS fctlast, fct.namefirst AS fctfirst, fct.org_name AS fctcompany, o.name as orgname, p.description as priority " +
            "FROM " + tableName + " c " +
            "LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
            "LEFT JOIN contact fct ON (c.followup_contact_id = fct.contact_id) " +
            "LEFT JOIN lookup_call_types t ON (c.call_type_id = t.code) " +
            "LEFT JOIN lookup_call_types talert ON (c.alert_call_type_id = talert.code) " +
            "LEFT JOIN lookup_call_priority p ON (c.priority_id = p.code) " +
            "LEFT JOIN contact ct2 ON (c.followup_contact_id = ct2.contact_id) " +
            "LEFT JOIN organization o ON (c.org_id = o.org_id) " +
            "WHERE call_id > -1 ");
    if (sqlFilter == null || sqlFilter.length() == 0) {
      StringBuffer buff = new StringBuffer();
      createFilter(buff);
      sqlFilter = buff.toString();
    }
    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() + sqlFilter + sqlOrder);
    prepareFilter(pst);
    return pst;
  }

  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM call_log c " +
            "LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
            "LEFT JOIN lookup_call_types t ON (c.call_type_id = t.code) " +
            "LEFT JOIN lookup_call_types talert ON (c.alert_call_type_id = talert.code) " +
            "LEFT JOIN lookup_call_priority p ON (c.priority_id = p.code) " +
            "LEFT JOIN contact ct2 ON (c.followup_contact_id = ct2.contact_id) " +
            "WHERE call_id > -1 ");
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
      //Determine column to sort by
      pagedListInfo.setDefaultSort("c.entered", "desc");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY c.entered DESC ");
    }

    pst = prepareList(db, sqlFilter.toString(), sqlOrder.toString());
    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      Call thisCall = new Call(rs);
      this.add(thisCall);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator calls = this.iterator();
    while (calls.hasNext()) {
      Call thisCall = (Call) calls.next();
      thisCall.delete(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (contactId != -1) {
      sqlFilter.append("AND (c.contact_id = ? ");
      sqlFilter.append("OR c.followup_contact_id = ? )");
    }

    if (allContactsInAccount) {
      sqlFilter.append(
          "AND ");
      sqlFilter.append("(");
      sqlFilter.append("c.contact_id IN ( SELECT contact_id " +
          " FROM contact cnt WHERE cnt.org_id = ? ");
      if (this.getAvoidDisabledContacts() == Constants.TRUE) {
        sqlFilter.append(
            "AND cnt.enabled = ? " +
                "AND cnt.trashed_date IS NULL ");
      } else if (this.getAvoidDisabledContacts() == Constants.FALSE) {
        sqlFilter.append(
            "AND (cnt.enabled = ? " +
                "OR cnt.trashed_date IS NOT NULL) ");
      }
      sqlFilter.append(") ");

      sqlFilter.append("OR c.contact_id IS NULL) ");
    }

    if (hasAlertDate == true) {
      sqlFilter.append("AND c.alertdate IS NOT NULL ");
    }
    if (oppHeaderId != -1) {
      sqlFilter.append("AND c.opp_id = ? ");
    }
    if (enteredBy != -1) {
      sqlFilter.append("AND c.enteredby = ? ");
    }
    if (alertDate != null) {
      sqlFilter.append("AND c.alertdate = ? ");
    }
    if (orgId != -1) {
      sqlFilter.append("AND c.org_id = ? ");
    }

    if (owner != -1) {
      sqlFilter.append("AND c.owner = ? ");
    }

    if (alertRangeStart != null) {
      if (onlyCompleted) {
        sqlFilter.append("AND c.entered >= ? ");
      } else {
        sqlFilter.append("AND c.alertdate >= ? ");
      }
    }

    if (alertRangeEnd != null) {
      if (onlyCompleted) {
        sqlFilter.append("AND c.entered < ? ");
      } else {
        sqlFilter.append("AND c.alertdate < ? ");
      }
    }

    if (notificationRangeStart != null) {
      sqlFilter.append("AND c.followup_date >= ? ");
    }

    if (notificationRangeEnd != null) {
      sqlFilter.append("AND c.followup_date < ? ");
    }

    if (onlyPending) {
      sqlFilter.append("AND c.status_id = ? AND c.alertdate is NOT NULL ");
    }

    if (onlyCompleted) {
      sqlFilter.append("AND (c.status_id = ? OR c.status_id = ?) AND result_id IS NOT NULL ");
    }

    if (excludeCanceled) {
      sqlFilter.append("AND c.status_id != ? ");
    }

    if (onlyCompletedOrCanceled) {
      sqlFilter.append("AND (c.status_id = ?  OR  c.status_id = ? OR  c.status_id = ?) AND result_id is not NULL ");
    }

    if (includeOnlyTrashed) {
      sqlFilter.append("AND c.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND c.trashed_date = ? ");
    } else {
      sqlFilter.append("AND c.trashed_date IS NULL ");
    }
    if (oppCallsOnly == Constants.TRUE) {
      sqlFilter.append("AND c.opp_id IS NOT NULL ");
    } else if (oppCallsOnly == Constants.FALSE) {
      sqlFilter.append("AND c.opp_id IS NULL ");
    }
    if (!allContactsInAccount) {
      if (this.getAvoidDisabledContacts() == Constants.TRUE) {
        sqlFilter.append(
            "AND ct.enabled = ? " +
                "AND ct.trashed_date IS NULL ");
      } else if (this.getAvoidDisabledContacts() == Constants.FALSE) {
        sqlFilter.append(
            "AND (ct.enabled = ? " +
                "OR ct.trashed_date IS NOT NULL) ");
      }
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
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (contactId != -1) {
      pst.setInt(++i, contactId);
      pst.setInt(++i, contactId);
    }

    if (allContactsInAccount) {
      pst.setInt(++i, contactOrgId);
      if (this.getAvoidDisabledContacts() == Constants.TRUE) {
        pst.setBoolean(++i, true);
      } else if (this.getAvoidDisabledContacts() == Constants.FALSE) {
        pst.setBoolean(++i, false);
      }
    }

    if (oppHeaderId != -1) {
      pst.setInt(++i, oppHeaderId);
    }
    if (enteredBy != -1) {
      pst.setInt(++i, enteredBy);
    }
    if (alertDate != null) {
      pst.setTimestamp(++i, alertDate);
    }
    if (orgId != -1) {
      pst.setInt(++i, orgId);
    }
    if (owner != -1) {
      pst.setInt(++i, owner);
    }
    if (alertRangeStart != null) {
      pst.setTimestamp(++i, alertRangeStart);
    }
    if (alertRangeEnd != null) {
      pst.setTimestamp(++i, alertRangeEnd);
    }
    if (notificationRangeStart != null) {
      pst.setTimestamp(++i, notificationRangeStart);
    }
    if (notificationRangeEnd != null) {
      pst.setTimestamp(++i, notificationRangeEnd);
    }
    if (onlyPending) {
      pst.setInt(++i, Call.COMPLETE_FOLLOWUP_PENDING);
    }
    if (onlyCompleted) {
      pst.setInt(++i, Call.COMPLETE_FOLLOWUP_PENDING);
      pst.setInt(++i, Call.COMPLETE);
    }
    if (excludeCanceled) {
      pst.setInt(++i, Call.CANCELED);
    }
    if (onlyCompletedOrCanceled) {
      pst.setInt(++i, Call.CANCELED);
      pst.setInt(++i, Call.COMPLETE);
      pst.setInt(++i, Call.COMPLETE_FOLLOWUP_PENDING);
    }

    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
    }
    if (!allContactsInAccount) {
      if (this.getAvoidDisabledContacts() == Constants.TRUE) {
        pst.setBoolean(++i, true);
      } else if (this.getAvoidDisabledContacts() == Constants.FALSE) {
        pst.setBoolean(++i, false);
      }
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
   * @param db       Description of the Parameter
   * @param context  Description of the Parameter
   * @param newOwner Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int reassignElements(Connection db, ActionContext context, int newOwner) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Call thisActivity = (Call) i.next();
      if (thisActivity.reassign(db, context, newOwner)) {
        total++;
      }
    }
    return total;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param context  Description of the Parameter
   * @param newOwner Description of the Parameter
   * @param userId   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int reassignElements(Connection db, ActionContext context, int newOwner, int userId) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Call thisActivity = (Call) i.next();
      thisActivity.setModifiedBy(userId);
      if (thisActivity.reassign(db, context, newOwner)) {
        total++;
      }
    }
    return total;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param context  Description of the Parameter
   * @param newOrgId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int reassignAccount(Connection db, ActionContext context, int newOrgId) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Call thisActivity = (Call) i.next();
      if (thisActivity.reassignAccount(db, context, newOrgId)) {
        total++;
      }
    }
    return total;
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param toTrash   Description of the Parameter
   * @param tmpUserId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      Call tmpCall = (Call) itr.next();
      tmpCall.updateStatus(db, toTrash, tmpUserId);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
}


