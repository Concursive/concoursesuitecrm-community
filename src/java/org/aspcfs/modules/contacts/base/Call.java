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

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.text.*;
import java.util.ArrayList;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.actionlist.base.*;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    January 8, 2002
 *@version    $Id$
 */
public class Call extends GenericBean {

  //static variables
  //PENDING :followup due of a completed activity
  public final static int CANCELED = 1;
  public final static int COMPLETE = 2;
  public final static int COMPLETE_FOLLOWUP_PENDING = 3;

  private int id = -1;
  private int orgId = -1;
  private int contactId = -1;
  private int callTypeId = -1;
  private int oppHeaderId = -1;
  private int length = 0;
  private int enteredBy = -1;
  private String enteredName = "";
  private int modifiedBy = -1;
  private String modifiedName = "";
  private int alertCallTypeId = -1;
  private int owner = -1;
  private int assignedBy = -1;
  private int completedBy = -1;
  private int resultId = -1;
  private int priorityId = -1;
  private int statusId = -1;
  private int reminderTypeId = -1;
  private int reminderId = -1;
  private int parentId = -1;
  private int contactOrgId = -1;

  private String callType = "";
  private String subject = null;
  private String notes = null;
  private String followupNotes = null;
  private String contactName = "";
  private String alertText = null;
  private String alertCallType = "";
  private String priorityString = "";
  private String orgName = null;
  private String alertDateTimeZone = null;

  private java.sql.Timestamp alertDate = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp followupDate = null;
  private java.sql.Timestamp assignDate = null;
  private java.sql.Timestamp completeDate = null;

  private boolean hasFollowup = false;

  //action list properties
  private int actionId = -1;
  private boolean checkAlertDate = true;

  Contact contact = new Contact();


  /**
   *  Constructor for the Call object
   *
   *@since
   */
  public Call() { }


  /**
   *  Constructor for the Call object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Call(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Call object
   *
   *@param  db                Description of Parameter
   *@param  callId            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Call(Connection db, String callId) throws SQLException {
    queryRecord(db, Integer.parseInt(callId));
  }


  /**
   *  Constructor for the Call object
   *
   *@param  db                Description of the Parameter
   *@param  callId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Call(Connection db, int callId) throws SQLException {
    queryRecord(db, callId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  callId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int callId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT c.call_id, c.org_id, c.contact_id, c.opp_id, c.call_type_id, c.length, " +
        "c.subject, c.notes, c.entered, c.enteredby, c.modified, c.modifiedby, c.alertdate, " +
        "c.followup_date, c.parent_id, c.owner, c.assignedby, c.assign_date, c.completedby, " +
        "c.complete_date, c.result_id, c.priority_id, c.status_id, c.reminder_value, c.reminder_type_id, " +
        "c.alert_call_type_id, c.alert, c.followup_notes, c.alertdate_timezone, t.*, talert.description as alertType, " +
        "ct.namelast as ctlast, ct.namefirst as ctfirst, ct.company as ctcompany, p.description as priority " +
        "FROM call_log c " +
        "LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
        "LEFT JOIN lookup_call_types t ON (c.call_type_id = t.code) " +
        "LEFT JOIN lookup_call_types talert ON (c.alert_call_type_id = talert.code) " +
        "LEFT JOIN lookup_call_priority p ON (c.priority_id = p.code) " +
        "WHERE call_id > -1 ");
    if (callId > -1) {
      sql.append("AND call_id = " + callId + " ");
    } else {
      throw new SQLException("Valid call ID not specified.");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Call record not found.");
    }
  }


  /**
   *  Sets the oppHeaderId attribute of the Call object
   *
   *@param  oppHeaderId  The new oppHeaderId value
   */
  public void setOppHeaderId(int oppHeaderId) {
    this.oppHeaderId = oppHeaderId;
  }


  /**
   *  Sets the oppHeaderId attribute of the Call object
   *
   *@param  oppHeaderId  The new oppHeaderId value
   */
  public void setOppHeaderId(String oppHeaderId) {
    this.oppHeaderId = Integer.parseInt(oppHeaderId);
  }


  /**
   *  Sets the contactName attribute of the Call object
   *
   *@param  contactName  The new contactName value
   */
  public void setContactName(String contactName) {
    this.contactName = contactName;
  }


  /**
   *  Sets the Id attribute of the Call object
   *
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the alertDate attribute of the Call object
   *
   *@param  alertDate  The new alertDate value
   */
  public void setAlertDate(java.sql.Timestamp alertDate) {
    this.alertDate = alertDate;
  }


  /**
   *  Sets the alertDate attribute of the Call object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(String tmp) {
    this.alertDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the Id attribute of the Call object
   *
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(String tmp) {
    try {
      this.id = Integer.parseInt(tmp);
    } catch (Exception e) {
    }
  }


  /**
   *  Sets the entered attribute of the Call object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the Call object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Call object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Call object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the OrgId attribute of the Call object
   *
   *@param  tmp  The new OrgId value
   *@since
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the actionId attribute of the Call object
   *
   *@param  actionId  The new actionId value
   */
  public void setActionId(int actionId) {
    this.actionId = actionId;
  }


  /**
   *  Sets the actionId attribute of the Call object
   *
   *@param  actionId  The new actionId value
   */
  public void setActionId(String actionId) {
    this.actionId = Integer.parseInt(actionId);
  }


  /**
   *  Sets the parentId attribute of the Call object
   *
   *@param  parentId  The new parentId value
   */
  public void setParentId(int parentId) {
    this.parentId = parentId;
  }


  /**
   *  Sets the parentId attribute of the Call object
   *
   *@param  parentId  The new parentId value
   */
  public void setParentId(String parentId) {
    this.parentId = Integer.parseInt(parentId);
  }


  /**
   *  Sets the owner attribute of the Call object
   *
   *@param  owner  The new owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the hasFollowup attribute of the Call object
   *
   *@param  hasFollowup  The new hasFollowup value
   */
  public void setHasFollowup(boolean hasFollowup) {
    this.hasFollowup = hasFollowup;
  }


  /**
   *  Sets the hasFollowup attribute of the Call object
   *
   *@param  hasFollowup  The new hasFollowup value
   */
  public void setHasFollowup(String hasFollowup) {
    this.hasFollowup = DatabaseUtils.parseBoolean(hasFollowup);
  }


  /**
   *  Gets the hasFollowup attribute of the Call object
   *
   *@return    The hasFollowup value
   */
  public boolean getHasFollowup() {
    return hasFollowup;
  }


  /**
   *  Sets the owner attribute of the Call object
   *
   *@param  owner  The new owner value
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the assignedBy attribute of the Call object
   *
   *@param  assignedBy  The new assignedBy value
   */
  public void setAssignedBy(int assignedBy) {
    this.assignedBy = assignedBy;
  }


  /**
   *  Sets the completedBy attribute of the Call object
   *
   *@param  completedBy  The new completedBy value
   */
  public void setCompletedBy(int completedBy) {
    this.completedBy = completedBy;
  }


  /**
   *  Sets the priorityId attribute of the Call object
   *
   *@param  priorityId  The new priorityId value
   */
  public void setPriorityId(int priorityId) {
    this.priorityId = priorityId;
  }


  /**
   *  Sets the priorityId attribute of the Call object
   *
   *@param  priorityId  The new priorityId value
   */
  public void setPriorityId(String priorityId) {
    if (Integer.parseInt(priorityId) > 0) {
      this.priorityId = Integer.parseInt(priorityId);
    }
  }


  /**
   *  Sets the statusId attribute of the Call object
   *
   *@param  statusId  The new statusId value
   */
  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }


  /**
   *  Sets the statusId attribute of the Call object
   *
   *@param  statusId  The new statusId value
   */
  public void setStatusId(String statusId) {
    this.statusId = Integer.parseInt(statusId);
  }


  /**
   *  Sets the reminderTypeId attribute of the Call object
   *
   *@param  reminderTypeId  The new reminderTypeId value
   */
  public void setReminderTypeId(int reminderTypeId) {
    this.reminderTypeId = reminderTypeId;
  }


  /**
   *  Sets the reminderTypeId attribute of the Call object
   *
   *@param  reminderTypeId  The new reminderTypeId value
   */
  public void setReminderTypeId(String reminderTypeId) {
    if (Integer.parseInt(reminderTypeId) > 0) {
      this.reminderTypeId = Integer.parseInt(reminderTypeId);
    }
  }


  /**
   *  Sets the reminderId attribute of the Call object
   *
   *@param  reminderId  The new reminderId value
   */
  public void setReminderId(int reminderId) {
    this.reminderId = reminderId;
  }


  /**
   *  Sets the reminderId attribute of the Call object
   *
   *@param  reminderId  The new reminderId value
   */
  public void setReminderId(String reminderId) {
    this.reminderId = Integer.parseInt(reminderId);
  }


  /**
   *  Sets the followupDate attribute of the Call object
   *
   *@param  followupDate  The new followupDate value
   */
  public void setFollowupDate(java.sql.Timestamp followupDate) {
    this.followupDate = followupDate;
  }


  /**
   *  Sets the followupDate attribute of the Call object
   *
   *@param  tmp  The new followupDate value
   */
  public void setFollowupDate(String tmp) {
    this.followupDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the assignDate attribute of the Call object
   *
   *@param  assignDate  The new assignDate value
   */
  public void setAssignDate(java.sql.Timestamp assignDate) {
    this.assignDate = assignDate;
  }


  /**
   *  Sets the completeDate attribute of the Call object
   *
   *@param  completeDate  The new completeDate value
   */
  public void setCompleteDate(java.sql.Timestamp completeDate) {
    this.completeDate = completeDate;
  }


  /**
   *  Sets the alertCallType attribute of the Call object
   *
   *@param  alertCallType  The new alertCallType value
   */
  public void setAlertCallType(String alertCallType) {
    this.alertCallType = alertCallType;
  }


  /**
   *  Sets the followupNotes attribute of the Call object
   *
   *@param  followupNotes  The new followupNotes value
   */
  public void setFollowupNotes(String followupNotes) {
    this.followupNotes = followupNotes;
  }


  /**
   *  Sets the priorityString attribute of the Call object
   *
   *@param  priorityString  The new priorityString value
   */
  public void setPriorityString(String priorityString) {
    this.priorityString = priorityString;
  }


  /**
   *  Sets the checkAlertDate attribute of the Call object
   *
   *@param  tmp  The new checkAlertDate value
   */
  public void setCheckAlertDate(boolean tmp) {
    this.checkAlertDate = tmp;
  }


  /**
   *  Sets the checkAlertDate attribute of the Call object
   *
   *@param  tmp  The new checkAlertDate value
   */
  public void setCheckAlertDate(String tmp) {
    this.checkAlertDate = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the contactOrgId attribute of the Call object
   *
   *@param  contactOrgId  The new contactOrgId value
   */
  public void setContactOrgId(int contactOrgId) {
    this.contactOrgId = contactOrgId;
  }


  /**
   *  Sets the orgName attribute of the Call object
   *
   *@param  orgName  The new orgName value
   */
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }


  /**
   *  Sets the contact attribute of the Call object
   *
   *@param  contact  The new contact value
   */
  public void setContact(Contact contact) {
    this.contact = contact;
  }


  /**
   *  Sets the alertDateTimeZone attribute of the Call object
   *
   *@param  tmp  The new alertDateTimeZone value
   */
  public void setAlertDateTimeZone(String tmp) {
    this.alertDateTimeZone = tmp;
  }


  /**
   *  Gets the alertDateTimeZone attribute of the Call object
   *
   *@return    The alertDateTimeZone value
   */
  public String getAlertDateTimeZone() {
    return alertDateTimeZone;
  }


  /**
   *  Gets the contact attribute of the Call object
   *
   *@return    The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   *  Gets the EnteredName attribute of the Call object
   *
   *@return    The EnteredName value
   *@since
   */
  public String getEnteredName() {
    return enteredName;
  }


  /**
   *  Gets the ModifiedName attribute of the Call object
   *
   *@return    The ModifiedName value
   *@since
   */
  public String getModifiedName() {
    return modifiedName;
  }


  /**
   *  Gets the orgName attribute of the Call object
   *
   *@return    The orgName value
   */
  public String getOrgName() {
    return orgName;
  }


  /**
   *  Gets the contactOrgId attribute of the Call object
   *
   *@return    The contactOrgId value
   */
  public int getContactOrgId() {
    return contactOrgId;
  }


  /**
   *  Gets the priorityString attribute of the Call object
   *
   *@return    The priorityString value
   */
  public String getPriorityString() {
    return priorityString;
  }


  /**
   *  Gets the followupNotes attribute of the Call object
   *
   *@return    The followupNotes value
   */
  public String getFollowupNotes() {
    return followupNotes;
  }


  /**
   *  Gets the alertCallType attribute of the Call object
   *
   *@return    The alertCallType value
   */
  public String getAlertCallType() {
    return alertCallType;
  }


  /**
   *  Gets the parentId attribute of the Call object
   *
   *@return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the parentId attribute of the Call object
   *
   *@return    The priority value
   */
  public int getPriorityId() {
    return priorityId;
  }


  /**
   *  Gets the owner attribute of the Call object
   *
   *@return    The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the assignedBy attribute of the Call object
   *
   *@return    The assignedBy value
   */
  public int getAssignedBy() {
    return assignedBy;
  }


  /**
   *  Gets the completedBy attribute of the Call object
   *
   *@return    The completedBy value
   */
  public int getCompletedBy() {
    return completedBy;
  }


  /**
   *  Gets the statusId attribute of the Call object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the statusString attribute of the Call object
   *
   *@return    The statusString value
   */
  public String getStatusString() {
    String tmp = "";
    if (statusId == Call.COMPLETE || statusId == Call.COMPLETE_FOLLOWUP_PENDING) {
      tmp = "Complete";
    } else if (statusId == Call.CANCELED) {
      tmp = "Canceled";
    }
    return tmp;
  }


  /**
   *  Gets the reminderTypeId attribute of the Call object
   *
   *@return    The reminderTypeId value
   */
  public int getReminderTypeId() {
    return reminderTypeId;
  }


  /**
   *  Gets the reminderId attribute of the Call object
   *
   *@return    The reminderId value
   */
  public int getReminderId() {
    return reminderId;
  }


  /**
   *  Gets the alertCallTypeId attribute of the Call object
   *
   *@return    The alertCallTypeId value
   */
  public int getAlertCallTypeId() {
    return alertCallTypeId;
  }


  /**
   *  Gets the followupDate attribute of the Call object
   *
   *@return    The followupDate value
   */
  public java.sql.Timestamp getFollowupDate() {
    return followupDate;
  }


  /**
   *  Gets the assignDate attribute of the Call object
   *
   *@return    The assignDate value
   */
  public java.sql.Timestamp getAssignDate() {
    return assignDate;
  }


  /**
   *  Gets the completeDate attribute of the Call object
   *
   *@return    The completeDate value
   */
  public java.sql.Timestamp getCompleteDate() {
    return completeDate;
  }


  /**
   *  Gets the actionId attribute of the Call object
   *
   *@return    The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   *  Gets the alertText attribute of the Call object
   *
   *@return    The alertText value
   */
  public String getAlertText() {
    return alertText;
  }


  /**
   *  Sets the alertText attribute of the Call object
   *
   *@param  alertText  The new alertText value
   */
  public void setAlertText(String alertText) {
    this.alertText = alertText;
  }


  /**
   *  Sets the OrgId attribute of the Call object
   *
   *@param  tmp  The new OrgId value
   *@since
   */
  public void setOrgId(String tmp) {
    try {
      this.orgId = Integer.parseInt(tmp);
    } catch (Exception e) {
    }
  }


  /**
   *  Sets the ContactId attribute of the Call object
   *
   *@param  tmp  The new ContactId value
   *@since
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the ContactId attribute of the Call object
   *
   *@param  tmp  The new ContactId value
   *@since
   */
  public void setContactId(String tmp) {
    try {
      this.contactId = Integer.parseInt(tmp);
    } catch (Exception e) {
    }
  }


  /**
   *  Sets the CallTypeId attribute of the Call object
   *
   *@param  tmp  The new CallTypeId value
   *@since
   */
  public void setCallTypeId(int tmp) {
    this.callTypeId = tmp;
  }


  /**
   *  Sets the CallTypeId attribute of the Call object
   *
   *@param  tmp  The new CallTypeId value
   *@since
   */
  public void setCallTypeId(String tmp) {
    try {
      this.callTypeId = Integer.parseInt(tmp);
    } catch (Exception e) {
    }
  }


  /**
   *  Sets the Length attribute of the Call object
   *
   *@param  tmp  The new Length value
   *@since
   */
  public void setLength(int tmp) {
    this.length = tmp;
  }


  /**
   *  Sets the Length attribute of the Call object
   *
   *@param  tmp  The new Length value
   *@since
   */
  public void setLength(String tmp) {
    try {
      this.length = Integer.parseInt(tmp);
    } catch (Exception e) {
    }
  }


  /**
   *  Sets the Subject attribute of the Call object
   *
   *@param  tmp  The new Subject value
   *@since
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   *  Sets the Notes attribute of the Call object
   *
   *@param  tmp  The new Notes value
   *@since
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the Call object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Call object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ModifiedBy attribute of the Call object
   *
   *@param  tmp  The new ModifiedBy value
   *@since
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Call object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the checkAlertDate attribute of the Call object
   *
   *@return    The checkAlertDate value
   */
  public boolean getCheckAlertDate() {
    return checkAlertDate;
  }


  /**
   *  Gets the oppHeaderId attribute of the Call object
   *
   *@return    The oppHeaderId value
   */
  public int getOppHeaderId() {
    return oppHeaderId;
  }


  /**
   *  Gets the contactName attribute of the Call object
   *
   *@return    The contactName value
   */
  public String getContactName() {
    return contactName;
  }


  /**
   *  Gets the alertDate attribute of the Call object
   *
   *@return    The alertDate value
   */
  public java.sql.Timestamp getAlertDate() {
    return alertDate;
  }


  /**
   *  Gets the entered attribute of the Call object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the Call object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedString attribute of the Call object
   *
   *@return    The modifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the enteredString attribute of the Call object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the Id attribute of the Call object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the idString attribute of the Call object
   *
   *@return    The idString value
   */
  public String getIdString() {
    return String.valueOf(id);
  }


  /**
   *  Gets the OrgId attribute of the Call object
   *
   *@return    The OrgId value
   *@since
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the ContactId attribute of the Call object
   *
   *@return    The ContactId value
   *@since
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the CallTypeId attribute of the Call object
   *
   *@return    The CallTypeId value
   *@since
   */
  public int getCallTypeId() {
    return callTypeId;
  }


  /**
   *  Gets the CallType attribute of the Call object
   *
   *@return    The CallType value
   *@since
   */
  public String getCallType() {
    return callType;
  }


  /**
   *  Gets the Length attribute of the Call object
   *
   *@return    The Length value
   *@since
   */
  public int getLength() {
    return length;
  }


  /**
   *  Gets the LengthString attribute of the Call object
   *
   *@return    The LengthString value
   *@since
   */
  public String getLengthString() {
    return (String.valueOf(length));
  }


  /**
   *  Gets the LengthText attribute of the Call object
   *
   *@return    The LengthText value
   *@since
   */
  public String getLengthText() {
    if (length > 0) {
      return (length + " min.");
    } else {
      return "";
    }
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasLength() {
    return (length > 0);
  }


  /**
   *  Gets the Subject attribute of the Call object
   *
   *@return    The Subject value
   *@since
   */
  public String getSubject() {
    return subject;
  }


  /**
   *  Gets the Notes attribute of the Call object
   *
   *@return    The Notes value
   *@since
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the EnteredBy attribute of the Call object
   *
   *@return    The EnteredBy value
   *@since
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the Call object
   *
   *@return    The ModifiedBy value
   *@since
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the resultId attribute of the Call object
   *
   *@param  tmp  The new resultId value
   */
  public void setResultId(int tmp) {
    this.resultId = tmp;
  }


  /**
   *  Sets the resultId attribute of the Call object
   *
   *@param  tmp  The new resultId value
   */
  public void setResultId(String tmp) {
    this.resultId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the resultId attribute of the Call object
   *
   *@return    The resultId value
   */
  public int getResultId() {
    return resultId;
  }


  /**
   *  Sets the alertCallTypeId attribute of the Call object
   *
   *@param  tmp  The new alertCallTypeId value
   */
  public void setAlertCallTypeId(int tmp) {
    this.alertCallTypeId = tmp;
  }


  /**
   *  Sets the alertCallTypeId attribute of the Call object
   *
   *@param  tmp  The new alertCallTypeId value
   */
  public void setAlertCallTypeId(String tmp) {
    if (Integer.parseInt(tmp) > 0) {
      this.alertCallTypeId = Integer.parseInt(tmp);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int lookupReminderSeconds(Connection db) throws SQLException {
    if (this.getReminderTypeId() == -1) {
      throw new SQLException("ID was not specified");
    }

    int scds = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT base_value FROM lookup_call_reminder " +
        "WHERE code = ? ");
    pst.setInt(1, reminderTypeId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      scds = rs.getInt("base_value");
    }
    pst.close();
    return scds;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean insert(Connection db, ActionContext context) throws SQLException {
    return (insert(db));
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }
    try {
      db.setAutoCommit(false);
      Contact thisContact = new Contact(db, this.getContactId());
      StringBuffer sql = new StringBuffer();
      sql.append(
          "INSERT INTO call_log " +
          "(org_id, contact_id, opp_id, call_type_id, length, subject, notes, " +
          "alertdate, alert, alert_call_type_id, result_id, parent_id, owner, followup_notes, status_id, " +
          "reminder_value, reminder_type_id, priority_id, followup_date, alertdate_timezone, ");

      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");

      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (thisContact.getOrgId() > 0) {
        pst.setInt(++i, thisContact.getOrgId());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }

      if (this.getContactId() > 0) {
        pst.setInt(++i, this.getContactId());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (this.getOppHeaderId() > 0) {
        pst.setInt(++i, this.getOppHeaderId());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (this.getCallTypeId() > 0) {
        pst.setInt(++i, this.getCallTypeId());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      pst.setInt(++i, this.getLength());
      pst.setString(++i, this.getSubject());
      pst.setString(++i, this.getNotes());
      DatabaseUtils.setTimestamp(pst, ++i, alertDate);
      pst.setString(++i, this.getAlertText());
      DatabaseUtils.setInt(pst, ++i, alertCallTypeId);
      DatabaseUtils.setInt(pst, ++i, resultId);
      DatabaseUtils.setInt(pst, ++i, parentId);
      DatabaseUtils.setInt(pst, ++i, owner);
      pst.setString(++i, this.getFollowupNotes());
      pst.setInt(++i, statusId);
      DatabaseUtils.setInt(pst, ++i, reminderId);
      DatabaseUtils.setInt(pst, ++i, reminderTypeId);
      DatabaseUtils.setInt(pst, ++i, priorityId);
      DatabaseUtils.setTimestamp(pst, ++i, followupDate);
      pst.setString(++i, alertDateTimeZone);
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "call_log_call_id_seq");

      //mark complete/canceled based on statusId
      if (this.getStatusId() == COMPLETE || this.getStatusId() == COMPLETE_FOLLOWUP_PENDING) {
        markComplete(db, this.getEnteredBy());
      } else if (this.getStatusId() == CANCELED) {
        markCanceled(db, this.getEnteredBy());
      }
      if (actionId > 0) {
        updateLog(db);
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void updateLog(Connection db) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      ActionItemLog thisLog = new ActionItemLog();
      thisLog.setEnteredBy(this.getEnteredBy());
      thisLog.setModifiedBy(this.getModifiedBy());
      thisLog.setItemId(this.getActionId());
      thisLog.setLinkItemId(this.getId());
      thisLog.setType(Constants.CALL_OBJECT);
      thisLog.insert(db);
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    ActionList actionList = ActionItemLogList.isItemLinked(db, this.getId(), Constants.CALL_OBJECT);
    if (actionList != null) {
      Dependency thisDependency = new Dependency();
      thisDependency.setName(actionList.getDescription());
      thisDependency.setCount(1);
      thisDependency.setCanDelete(true);
      dependencyList.add(thisDependency);
    }
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }

    int recordCount = 0;
    ActionItemLog.deleteLink(db, this.getId(), Constants.CALL_OBJECT);
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM call_log " +
        "WHERE call_id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
    pst.close();
    if (recordCount == 0) {
      errors.put("actionError", "Call could not be deleted because it no longer exists.");
      return false;
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Call ID was not specified");
    }
    if (!isValid(db)) {
      return -1;
    }
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE call_log " +
        "SET call_type_id = ?, length = ?, subject = ?, notes = ?, " +
        "modifiedby = ?, alertdate = ?, alert = ?, alert_call_type_id = ?, followup_notes = ?, status_id = ?, " +
        "result_id = ?, owner = ?, reminder_value = ?, reminder_type_id = ?, priority_id = ?, followup_date = ?, alertdate_timezone = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE call_id = ? " +
        "AND modified = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    if (this.getCallTypeId() > 0) {
      pst.setInt(++i, this.getCallTypeId());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setInt(++i, length);
    pst.setString(++i, subject);
    pst.setString(++i, notes);
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setTimestamp(pst, ++i, alertDate);
    pst.setString(++i, this.getAlertText());
    DatabaseUtils.setInt(pst, ++i, alertCallTypeId);
    pst.setString(++i, this.getFollowupNotes());
    pst.setInt(++i, statusId);
    DatabaseUtils.setInt(pst, ++i, resultId);
    if (this.getOwner() > 0) {
      pst.setInt(++i, this.getOwner());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    DatabaseUtils.setInt(pst, ++i, reminderId);
    DatabaseUtils.setInt(pst, ++i, reminderTypeId);
    DatabaseUtils.setInt(pst, ++i, priorityId);
    DatabaseUtils.setTimestamp(pst, ++i, followupDate);
    pst.setString(++i, alertDateTimeZone);
    pst.setInt(++i, this.getId());
    pst.setTimestamp(++i, this.getModified());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Updates the current record and marks it complete in the database
   *
   *@param  db                Description of the Parameter
   *@param  userId            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int markComplete(Connection db, int userId) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Call ID was not specified");
    }
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE call_log " +
        "SET completedby = ?, complete_date = CURRENT_TIMESTAMP, status_id = ? " +
        "WHERE call_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, userId);
    if (this.getStatusId() == COMPLETE_FOLLOWUP_PENDING) {
      pst.setInt(++i, COMPLETE_FOLLOWUP_PENDING);
    } else {
      pst.setInt(++i, COMPLETE);
    }
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  userId            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int markCanceled(Connection db, int userId) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Call ID was not specified");
    }
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE call_log " +
        "SET completedby = ?, complete_date = CURRENT_TIMESTAMP, status_id = ? " +
        "WHERE call_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, userId);
    pst.setInt(++i, CANCELED);
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Gets the Valid attribute of the Call object
   *
   *@param  db                Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected boolean isValid(Connection db) throws SQLException {

    if (subject == null || "".equals(subject)) {
      errors.put("subjectError", "Cannot insert a blank record");
    }

    if (contactId == -1 && orgId == -1 && oppHeaderId == -1) {
      errors.put("linkError", "Activity is not associated with a valid record");
    }

    if (length < 0) {
      errors.put("lengthError", "Length cannot be less than 0");
    }

    if (resultId == -1) {
      errors.put("resultError", "Result of the activity is required");
    }

    if (callTypeId < 1) {
      errors.put("typeError", "Type of the activity is required");
    }

    if (length < 0) {
      errors.put("lengthError", "Length cannot be less than 0");
    }

    if (alertDate != null || hasFollowup) {
      if ("".equals(StringUtils.toString(alertText.trim()))) {
        errors.put("descriptionError", "Description is required");
      }
      if (alertCallTypeId < 1) {
        errors.put("followupTypeError", "Type is required");
      }
      if (priorityId == -1) {
        errors.put("priorityError", "Priority is required");
      }
      if (alertDate == null) {
        errors.put("alertDateError", "Date is required");
      }
    } else {
      //reset priority Id as it does not have a "none" option
      priorityId = -1;
    }

    if (hasErrors()) {
      //Check warnings
      checkWarnings();
      onlyWarnings = false;
      return false;
    } else {
      //Do not check for warnings if it was found that only warnings existed
      // in the previous call to isValid for the same form.
      if (!onlyWarnings) {
        //Check for warnings if there are no errors
        checkWarnings();
        if (hasWarnings()) {
          onlyWarnings = true;
          return false;
        }
      }
      return true;
    }
  }


  /**
   *  Generates warnings that need to be reviewed before the form can be
   *  submitted.
   */
  protected void checkWarnings() {
    if (checkAlertDate && (errors.get("alertDateError") == null) && (alertDate != null)) {
      if (alertDate.before(new java.util.Date())) {
        warnings.put("alertDateWarning", "Alert date is earlier than current date or set to current date");
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //call_log table
    id = rs.getInt("call_id");
    orgId = DatabaseUtils.getInt(rs, "org_id");
    contactId = DatabaseUtils.getInt(rs, "contact_id");
    oppHeaderId = DatabaseUtils.getInt(rs, "opp_id");
    callTypeId = DatabaseUtils.getInt(rs, "call_type_id");
    length = rs.getInt("length");
    subject = rs.getString("subject");
    notes = rs.getString("notes");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    alertDate = rs.getTimestamp("alertdate");
    followupDate = rs.getTimestamp("followup_date");
    parentId = DatabaseUtils.getInt(rs, "parent_id");
    owner = DatabaseUtils.getInt(rs, "owner");
    assignedBy = DatabaseUtils.getInt(rs, "assignedBy");
    assignDate = rs.getTimestamp("assign_date");
    completedBy = DatabaseUtils.getInt(rs, "completedBy");
    completeDate = rs.getTimestamp("complete_date");
    resultId = DatabaseUtils.getInt(rs, "result_id");
    priorityId = DatabaseUtils.getInt(rs, "priority_id");
    statusId = rs.getInt("status_id");
    reminderId = DatabaseUtils.getInt(rs, "reminder_value");
    reminderTypeId = DatabaseUtils.getInt(rs, "reminder_type_id");
    alertCallTypeId = DatabaseUtils.getInt(rs, "alert_call_type_id");
    alertText = rs.getString("alert");
    followupNotes = rs.getString("followup_notes");
    resultId = DatabaseUtils.getInt(rs, "result_id");
    alertDateTimeZone = rs.getString("alertdate_timezone");
    //lookup_call_types table
    callTypeId = DatabaseUtils.getInt(rs, "code");
    callType = rs.getString("description");
    alertCallType = rs.getString("alertType");
    //contact table
    contactName = Contact.getNameLastFirst(rs.getString("ctlast"), rs.getString("ctfirst"));
    if (contactName == null || "".equals(contactName)) {
      contactName = rs.getString("ctcompany");
    }
    priorityString = rs.getString("priority");
  }


  /**
   *  Gets the properties that are TimeZone sensitive for a Call
   *
   *@return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("alertDate");
    return thisList;
  }

}

