//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package org.aspcfs.modules.contacts.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    January 8, 2002
 *@version    $Id$
 */
public class CallList extends Vector {

  public final static String tableName = "call_log";
  public final static String uniqueField = "call_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;

  protected PagedListInfo pagedListInfo = null;
  protected int contactId = -1;
  protected int orgId = -1;
  protected int oppId = -1;
  protected int enteredBy = -1;
  protected boolean hasAlertDate = false;
  protected java.sql.Date alertDate = null;
  protected java.sql.Date alertRangeStart = null;
  protected java.sql.Date alertRangeEnd = null;



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


  /**
   *  Sets the OppId attribute of the CallList object
   *
   *@param  oppId  The new OppId value
   *@since
   */
  public void setOppId(int oppId) {
    this.oppId = oppId;
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
  public void setAlertDate(java.sql.Date alertDate) {
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


  /**
   *  Sets the OppId attribute of the CallList object
   *
   *@param  oppId  The new OppId value
   *@since
   */
  public void setOppId(String oppId) {
    this.oppId = Integer.parseInt(oppId);
  }


  /**
   *  Sets the alertRangeStart attribute of the CallList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Date tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the CallList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = java.sql.Date.valueOf(tmp);
  }


  /**
   *  Sets the alertRangeEnd attribute of the CallList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Date tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   *  Sets the alertRangeEnd attribute of the CallList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = java.sql.Date.valueOf(tmp);
  }


  /**
   *  Gets the alertRangeStart attribute of the CallList object
   *
   *@return    The alertRangeStart value
   */
  public java.sql.Date getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   *  Gets the alertRangeEnd attribute of the CallList object
   *
   *@return    The alertRangeEnd value
   */
  public java.sql.Date getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   *  Gets the AlertDate attribute of the CallList object
   *
   *@return    The AlertDate value
   *@since
   */
  public java.sql.Date getAlertDate() {
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


  /**
   *  Gets the OppId attribute of the CallList object
   *
   *@return    The OppId value
   *@since
   */
  public int getOppId() {
    return oppId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public HashMap queryRecordCount(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;

    HashMap events = new HashMap();
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlTail = new StringBuffer();

    createFilter(sqlFilter);

    sqlSelect.append(
        "SELECT alertdate, count(*) " +
        "FROM call_log c " +
        "WHERE call_id > -1 ");

    sqlTail.append("GROUP BY alertdate ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      String alertdate = Call.getAlertDateStringLongYear(rs.getDate("alertdate"));
      events.put(alertdate, new Integer(rs.getInt("count")));
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
        "c.call_id, c.subject, c.opp_id, c.contact_id, c.opp_id, c.alertdate " +
        "FROM call_log c, " +
        "contact e LEFT JOIN access a1 ON (e.contact_id = a1.contact_id), " +
        "contact m LEFT JOIN access a2 ON (m.contact_id = a2.contact_id) " +
        "WHERE c.enteredby = a1.user_id " +
        "AND c.modifiedby = a2.user_id ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Call thisCall = new Call();
      thisCall.setId(rs.getInt("call_id"));
      thisCall.setSubject(rs.getString("subject"));
      thisCall.setContactId(rs.getInt("contact_id"));
      if (rs.wasNull()) {
        thisCall.setContactId(-1);
      }
      thisCall.setOppId(rs.getInt("opp_id"));
      if (rs.wasNull()) {
        thisCall.setOppId(-1);
      }
      thisCall.setAlertDate(rs.getDate("alertdate"));
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
        "FROM call_log c, " +
        "contact e LEFT JOIN access a1 ON (e.contact_id = a1.contact_id), " +
        "contact m LEFT JOIN access a2 ON (m.contact_id = a2.contact_id), " +
        "lookup_call_types t " +
        "WHERE c.call_type_id = t.code " +
        "AND c.enteredby = a1.user_id " +
        "AND c.modifiedby = a2.user_id ");

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
        "e.namefirst as efirst, e.namelast as elast, " +
        "m.namefirst as mfirst, m.namelast as mlast, " +
        "ct.namefirst as ctfirst, ct.namelast as ctlast " +
        "FROM call_log c " +
        "LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
        "LEFT JOIN lookup_call_types t ON (c.call_type_id = t.code), " +
        "contact e LEFT JOIN access a1 ON (e.contact_id = a1.contact_id), " +
        "contact m LEFT JOIN access a2 ON (m.contact_id = a2.contact_id) " +
        "WHERE c.enteredby = a1.user_id " +
        "AND c.modifiedby = a2.user_id ");

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
      this.addElement(thisCall);
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

    if (oppId != -1) {
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
      sqlFilter.append("AND c.alertdate <= ? ");
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

    if (oppId != -1) {
      pst.setInt(++i, oppId);
    }

    if (enteredBy != -1) {
      pst.setInt(++i, enteredBy);
    }

    if (alertDate != null) {
      pst.setDate(++i, alertDate);
    }

    if (orgId != -1) {
      pst.setInt(++i, orgId);
    }

    if (alertRangeStart != null) {
      pst.setDate(++i, alertRangeStart);
    }

    if (alertRangeEnd != null) {
      pst.setDate(++i, alertRangeEnd);
    }
    return i;
  }
}


