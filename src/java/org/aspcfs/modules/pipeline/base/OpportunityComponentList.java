//Copyright 2003 Dark Horse Ventures

package org.aspcfs.modules.pipeline.base;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import java.util.*;
import java.text.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.modules.contacts.base.Call;
import org.aspcfs.modules.base.Constants;

/**
 *  Container for OpportunityComponent objects.
 *
 *@author     chris
 *@created    January 7, 2003
 *@version    $Id: OpportunityComponentList.java,v 1.5 2003/01/10 16:17:48
 *      mrajkowski Exp $
 */

public class OpportunityComponentList extends Vector {

  public final static String tableName = "opportunity_component";
  public final static String uniqueField = "id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;

  protected PagedListInfo pagedListInfo = null;
  protected int oppId = -1;
  protected int enteredBy = -1;
  protected boolean hasAlertDate = false;
  protected java.sql.Date alertDate = null;
  protected int owner = -1;
  protected String ownerIdRange = null;
  protected java.sql.Date alertRangeStart = null;
  protected java.sql.Date alertRangeEnd = null;
  protected java.sql.Date closeDateStart = null;
  protected java.sql.Date closeDateEnd = null;
  protected boolean queryOpenOnly = false;


  /**
   *  Constructor for the OpportunityComponentList object
   */
  public OpportunityComponentList() { }


  /**
   *  Sets the pagedListInfo attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the oppId attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new oppId value
   */
  public void setOppId(String tmp) {
    this.oppId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the oppId attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new oppId value
   */
  public void setOppId(int tmp) {
    this.oppId = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the ownerIdRange attribute of the OpportunityComponentList object
   *
   *@param  ownerIdRange  The new ownerIdRange value
   */
  public void setOwnerIdRange(String ownerIdRange) {
    this.ownerIdRange = ownerIdRange;
  }


  /**
   *  Sets the alertRangeStart attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Date tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = java.sql.Date.valueOf(tmp);
  }


  /**
   *  Sets the alertRangeEnd attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Date tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   *  Sets the alertRangeEnd attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = java.sql.Date.valueOf(tmp);
  }


  /**
   *  Gets the alertRangeStart attribute of the OpportunityComponentList object
   *
   *@return    The alertRangeStart value
   */
  public java.sql.Date getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   *  Gets the alertRangeEnd attribute of the OpportunityComponentList object
   *
   *@return    The alertRangeEnd value
   */
  public java.sql.Date getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   *  Sets the hasAlertDate attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new hasAlertDate value
   */
  public void setHasAlertDate(boolean tmp) {
    this.hasAlertDate = tmp;
  }


  /**
   *  Sets the alertDate attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(java.sql.Date tmp) {
    this.alertDate = tmp;
  }


  /**
   *  Gets the tableName attribute of the OpportunityComponentList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the OpportunityComponentList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the closeDateStart attribute of the OpportunityComponentList object
   *
   *@return    The closeDateStart value
   */
  public java.sql.Date getCloseDateStart() {
    return closeDateStart;
  }


  /**
   *  Gets the closeDateEnd attribute of the OpportunityComponentList object
   *
   *@return    The closeDateEnd value
   */
  public java.sql.Date getCloseDateEnd() {
    return closeDateEnd;
  }


  /**
   *  Sets the closeDateStart attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new closeDateStart value
   */
  public void setCloseDateStart(java.sql.Date tmp) {
    this.closeDateStart = tmp;
  }


  /**
   *  Sets the closeDateStart attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new closeDateStart value
   */
  public void setCloseDateStart(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      closeDateStart = new java.sql.Date(new java.util.Date().getTime());
      closeDateStart.setTime(tmpDate.getTime());
    } catch (Exception e) {
      closeDateStart = null;
    }
  }


  /**
   *  Sets the closeDateEnd attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new closeDateEnd value
   */
  public void setCloseDateEnd(java.sql.Date tmp) {
    this.closeDateEnd = tmp;
  }


  /**
   *  Sets the closeDateEnd attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new closeDateEnd value
   */
  public void setCloseDateEnd(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      closeDateEnd = new java.sql.Date(new java.util.Date().getTime());
      closeDateEnd.setTime(tmpDate.getTime());
    } catch (Exception e) {
      closeDateEnd = null;
    }
  }


  /**
   *  Sets the owner attribute of the OpportunityComponentList object
   *
   *@param  tmp  The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   *  Gets the ownerIdRange attribute of the OpportunityComponentList object
   *
   *@return    The ownerIdRange value
   */
  public String getOwnerIdRange() {
    return ownerIdRange;
  }


  /**
   *  Gets the queryOpenOnly attribute of the OpportunityComponentList object
   *
   *@return    The queryOpenOnly value
   */
  public boolean getQueryOpenOnly() {
    return queryOpenOnly;
  }


  /**
   *  Sets the queryOpenOnly attribute of the OpportunityComponentList object
   *
   *@param  queryOpenOnly  The new queryOpenOnly value
   */
  public void setQueryOpenOnly(boolean queryOpenOnly) {
    this.queryOpenOnly = queryOpenOnly;
  }


  /**
   *  Gets the listSize attribute of the OpportunityComponentList object
   *
   *@return    The listSize value
   */
  public int getListSize() {
    return this.size();
  }


  /**
   *  Gets the enteredBy attribute of the OpportunityComponentList object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the hasAlertDate attribute of the OpportunityComponentList object
   *
   *@return    The hasAlertDate value
   */
  public boolean getHasAlertDate() {
    return hasAlertDate;
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
        "FROM opportunity_component oc " +
        "WHERE oc.opp_id > -1 ");

    sqlTail.append("GROUP BY alertdate ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      String alertdate = Call.getAlertDateStringLongYear(rs.getDate("alertdate"));
      if (System.getProperty("DEBUG") != null) {
        System.out.println("OppList --> Added Days Calls " + alertdate + ":" + rs.getInt("count"));
      }
      events.put(alertdate, new Integer(rs.getInt("count")));
    }
    rs.close();
    pst.close();
    return events;
  }


  /**
   *  Builds a subset of attributes of the Opprtunity primarily for the
   *  Calendar.
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
        "SELECT oc.id, oc.opp_id, oc.description, org.name as acct_name, oc.alertdate, oc.alert " +
        "FROM opportunity_component oc  " +
        "LEFT JOIN opportunity_header oh ON (oc.opp_id = oh.opp_id) " +
        "LEFT JOIN organization org ON (oh.acctlink = org.org_id) " +
        "WHERE oc.opp_id > -1 ");

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      OpportunityComponent thisOpp = new OpportunityComponent();
      thisOpp.setOppId(rs.getInt("opp_id"));
      thisOpp.setId(rs.getInt("id"));
      thisOpp.setDescription(rs.getString("description"));
      thisOpp.setAccountName(rs.getString("acct_name"));
      thisOpp.setAlertDate(rs.getDate("alertdate"));
      thisOpp.setAlertText(rs.getString("alert"));
      this.add(thisOpp);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Builds a list of contacts based on several parameters. The parameters are
   *  set after this object is constructed, then the buildList method is called
   *  to generate the list.
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
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
        "FROM opportunity_component oc " +
        "WHERE oc.id > -1 ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString());
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
            "AND oc.description < ? ");
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

      pagedListInfo.setDefaultSort("oc.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY oc.entered");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " oc.*, y.description as stagename, " +
        "ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, oc.modified as c_mod, oc.modifiedby as c_modby, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst " +
        "FROM opportunity_component oc " +
        "LEFT JOIN contact ct_eb ON (oc.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (oc.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN contact ct_owner ON (oc.owner = ct_owner.user_id), " +
        "lookup_stage y " +
        "WHERE y.code = oc.stage " +
        "AND oc.opp_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() +
        sqlFilter.toString() +
        sqlOrder.toString());
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
      OpportunityComponent thisOppComponent = new OpportunityComponent(rs);
      this.addElement(thisOppComponent);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (oppId != -1) {
      sqlFilter.append("AND oc.opp_id = ? ");
    }
    if (enteredBy != -1) {
      sqlFilter.append("AND oc.enteredby = ? ");
    }
    if (hasAlertDate == true) {
      sqlFilter.append("AND oc.alertdate IS NOT NULL ");
    }
    if (alertDate != null) {
      sqlFilter.append("AND oc.alertdate = ? ");
    }
    if (alertRangeStart != null) {
      sqlFilter.append("AND oc.alertdate >= ? ");
    }
    if (alertRangeEnd != null) {
      sqlFilter.append("AND oc.alertdate <= ? ");
    }
    if (closeDateStart != null) {
      sqlFilter.append("AND oc.closedate >= ? ");
    }
    if (closeDateEnd != null) {
      sqlFilter.append("AND oc.closedate <= ? ");
    }
    if (owner != -1) {
      sqlFilter.append("AND oc.owner = ? ");
    }
    if (ownerIdRange != null) {
      sqlFilter.append("AND oc.owner in (" + this.ownerIdRange + ") ");
    }
    if (queryOpenOnly) {
      sqlFilter.append("AND oc.closed IS NULL ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  newOwner          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int reassignElements(Connection db, int newOwner) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      OpportunityComponent thisOpp = (OpportunityComponent) i.next();
      if (thisOpp.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }


  /**
   *  Sets the parameters for the preparedStatement - these items must
   *  correspond with the createFilter statement
   *
   *@param  pst               Description os.gerameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.3
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (oppId != -1) {
      pst.setInt(++i, oppId);
    }
    if (enteredBy != -1) {
      pst.setInt(++i, enteredBy);
    }
    if (alertDate != null) {
      pst.setDate(++i, alertDate);
    }
    if (alertRangeStart != null) {
      pst.setDate(++i, alertRangeStart);
    }
    if (alertRangeEnd != null) {
      pst.setDate(++i, alertRangeEnd);
    }
    if (closeDateStart != null) {
      pst.setDate(++i, closeDateStart);
    }
    if (closeDateEnd != null) {
      pst.setDate(++i, closeDateEnd);
    }
    if (owner != -1) {
      pst.setInt(++i, owner);
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
        "FROM opportunity_component oc " +
        "WHERE opp_id > 0 ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("itemcount");
    }
    rs.close();
    pst.close();
    return count;
  }

}

