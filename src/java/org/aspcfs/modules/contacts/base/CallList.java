//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package org.aspcfs.modules.contacts.base;

import java.util.*;
import java.sql.*;
import java.text.DateFormat;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DateUtils;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    January 8, 2002
 *@version    $Id$
 */
public class CallList extends ArrayList {

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
  protected boolean hasAlertDate = false;
  protected java.sql.Timestamp alertDate = null;
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;



  /**
   *  Constructor for the CallList object
   *
   *@since
   */
  public CallList() { }


  /**
   *  Sets the PagedListInfo attribute of the CallList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the ContactId attribute of the CallList object
   *
   *@param  tmp  The new ContactId value
   *@since
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the ContactId attribute of the CallList object
   *
   *@param  tmp  The new ContactId value
   *@since
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the OrgId attribute of the CallList object
   *
   *@param  tmp  The new OrgId value
   *@since
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  public void setOppHeaderId(int oppHeaderId) {
    this.oppHeaderId = oppHeaderId;
  }


  /**
   *  Sets the EnteredBy attribute of the CallList object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the AlertDate attribute of the CallList object
   *
   *@param  alertDate  The new AlertDate value
   *@since
   */
  public void setAlertDate(java.sql.Timestamp alertDate) {
    this.alertDate = alertDate;
  }


  /**
   *  Sets the HasAlertDate attribute of the CallList object
   *
   *@param  tmp  The new HasAlertDate value
   *@since
   */
  public void setHasAlertDate(boolean tmp) {
    this.hasAlertDate = tmp;
  }


  public void setOppHeaderId(String oppHeaderId) {
    this.oppHeaderId = Integer.parseInt(oppHeaderId);
  }


  /**
   *  Sets the alertRangeStart attribute of the CallList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the CallList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the alertRangeEnd attribute of the CallList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   *  Sets the alertRangeEnd attribute of the CallList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Gets the alertRangeStart attribute of the CallList object
   *
   *@return    The alertRangeStart value
   */
  public java.sql.Timestamp getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   *  Gets the alertRangeEnd attribute of the CallList object
   *
   *@return    The alertRangeEnd value
   */
  public java.sql.Timestamp getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   *  Gets the AlertDate attribute of the CallList object
   *
   *@return    The AlertDate value
   *@since
   */
  public java.sql.Timestamp getAlertDate() {
    return alertDate;
  }


  /**
   *  Gets the EnteredBy attribute of the CallList object
   *
   *@return    The EnteredBy value
   *@since
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the HasAlertDate attribute of the CallList object
   *
   *@return    The HasAlertDate value
   *@since
   */
  public boolean getHasAlertDate() {
    return hasAlertDate;
  }


  /**
   *  Gets the tableName attribute of the CallList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the CallList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the CallList object
   *
   *@return    The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the CallList object
   *
   *@return    The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the CallList object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Sets the lastAnchor attribute of the CallList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the CallList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the syncType attribute of the CallList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Gets the PagedListInfo attribute of the CallList object
   *
   *@return    The PagedListInfo value
   *@since
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the ContactId attribute of the CallList object
   *
   *@return    The ContactId value
   *@since
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the OrgId attribute of the CallList object
   *
   *@return    The OrgId value
   *@since
   */
  public int getOrgId() {
    return orgId;
  }


  public int getOppHeaderId() {
    return oppHeaderId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public HashMap queryRecordCount(Connection db, TimeZone timeZone) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;

    HashMap events = new HashMap();
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlTail = new StringBuffer();

    createFilter(sqlFilter);

    sqlSelect.append(
        "SELECT alertdate, count(*) as nocols " +
        "FROM call_log c " +
        "WHERE call_id > -1 ");

    sqlTail.append("GROUP BY alertdate ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    prepareFilter(pst);    
    rs = pst.executeQuery();
    while (rs.next()) {
      String alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, rs.getTimestamp("alertdate"));
      int tempcount=rs.getInt("nocols"); 
      events.put(alertDate, new Integer(tempcount));
    }
    rs.close();
    pst.close();
    return events;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildShortList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    createFilter(sqlFilter);
    sqlSelect.append(
        "c.call_id, c.subject, c.contact_id, c.opp_id, c.opp_id, c.alertdate " +
        "FROM call_log c " +
        "WHERE c.call_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Call thisCall = new Call();
      thisCall.setId(rs.getInt("call_id"));
      thisCall.setSubject(rs.getString("subject"));
      thisCall.setContactId(DatabaseUtils.getInt(rs, "contact_id"));
      thisCall.setOppHeaderId(DatabaseUtils.getInt(rs, "opp_id"));
      thisCall.setAlertDate(rs.getTimestamp("alertdate"));
      this.add(thisCall);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
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
        "SELECT COUNT(*) as recordcount " +
        "FROM call_log c " +
        "LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
        "LEFT JOIN lookup_call_types t ON (c.call_type_id = t.code) " +
        "LEFT JOIN contact e ON (c.enteredby = e.user_id) " +
        "LEFT JOIN contact m ON (c.modifiedby = m.user_id) " +
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

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "c.*, t.*, " +
        "e.namelast as elast, e.namefirst as efirst, " +
        "m.namelast as mlast, m.namefirst as mfirst, " +
        "ct.namelast as ctlast, ct.namefirst as ctfirst, ct.company as ctcompany " +
        "FROM call_log c " +
        "LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
        "LEFT JOIN lookup_call_types t ON (c.call_type_id = t.code) " +
        "LEFT JOIN contact e ON (c.enteredby = e.user_id) " +
        "LEFT JOIN contact m ON (c.modifiedby = m.user_id) " +
        "WHERE call_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CallList --> Building List ");
    }
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
      Call thisCall = new Call(rs);
      this.add(thisCall);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator calls = this.iterator();
    while (calls.hasNext()) {
      Call thisCall = (Call) calls.next();
      thisCall.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of Parameter
   *@since
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (contactId != -1) {
      sqlFilter.append("AND c.contact_id = ? ");
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

    if (alertRangeStart != null) {
      sqlFilter.append("AND c.alertdate >= ? ");
    }
    if (alertRangeEnd != null) {
      sqlFilter.append("AND c.alertdate < ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (contactId != -1) {
      pst.setInt(++i, contactId);
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

    if (alertRangeStart != null) {
      pst.setTimestamp(++i, alertRangeStart);
    }

    if (alertRangeEnd != null) {
      pst.setTimestamp(++i, alertRangeEnd);
    }
    return i;
  }
}


