//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import java.util.*;
import java.text.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.utils.ObjectUtils;

public class OpportunityHeaderList extends Vector {
  
  public static final String tableName = "opportunity_header";
  public static final String uniqueField = "opp_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;

  protected PagedListInfo pagedListInfo = null;
  protected int orgId = -1;
  protected int contactId = -1;
  protected Vector ignoreTypeIdList = new Vector();
  protected String description = null;
  protected int enteredBy = -1;
  protected boolean hasAlertDate = false;
  protected java.sql.Date alertDate = null;
  protected int owner = -1;
  protected String ownerIdRange = null;
  protected String accountOwnerIdRange = null;
  protected java.sql.Date alertRangeStart = null;
  protected java.sql.Date alertRangeEnd = null;
  protected java.sql.Date closeDateStart = null;
  protected java.sql.Date closeDateEnd = null;
  private boolean queryOpenOnly = false;
  
  private boolean buildTotalValues = false;

  public OpportunityHeaderList() { }


  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }

  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }

  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }
  
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }
  
  public void setBuildTotalValues(boolean buildTotalValues) {
    this.buildTotalValues = buildTotalValues;
  }
  public boolean getBuildTotalValues() {
    return buildTotalValues;
  }

  public void setAccountOwnerIdRange(String accountOwnerIdRange) {
    this.accountOwnerIdRange = accountOwnerIdRange;
  }
  
  public void setOwnerIdRange(String ownerIdRange) {
    this.ownerIdRange = ownerIdRange;
  }
  
  public void setAlertRangeStart(java.sql.Date tmp) { this.alertRangeStart = tmp; }
  public void setAlertRangeStart(String tmp) { 
    this.alertRangeStart = java.sql.Date.valueOf(tmp);
  }
  public void setAlertRangeEnd(java.sql.Date tmp) { this.alertRangeEnd = tmp; }
  public void setAlertRangeEnd(String tmp) { 
    this.alertRangeEnd = java.sql.Date.valueOf(tmp);
  }
  
  public java.sql.Date getAlertRangeStart() { return alertRangeStart; }
  public java.sql.Date getAlertRangeEnd() { return alertRangeEnd; }
  
  public boolean getQueryOpenOnly() {
    return queryOpenOnly;
  }
  public void setQueryOpenOnly(boolean queryOpenOnly) {
    this.queryOpenOnly = queryOpenOnly;
  }

  public void setHasAlertDate(boolean tmp) {
    this.hasAlertDate = tmp;
  }

  public void setAlertDate(java.sql.Date tmp) {
    this.alertDate = tmp;
  }
  
  public String getTableName() {
    return tableName;
  }
  
  public String getUniqueField() {
    return uniqueField;
  }

public java.sql.Date getCloseDateStart() { return closeDateStart; }
public java.sql.Date getCloseDateEnd() { return closeDateEnd; }
public void setCloseDateStart(java.sql.Date tmp) { this.closeDateStart = tmp; }

public void setCloseDateStart(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      closeDateStart = new java.sql.Date(new java.util.Date().getTime());
      closeDateStart.setTime(tmpDate.getTime());
    } catch (Exception e) {
      closeDateStart = null;
    }
}
    
public void setCloseDateEnd(java.sql.Date tmp) { this.closeDateEnd = tmp; }

public void setCloseDateEnd(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      closeDateEnd = new java.sql.Date(new java.util.Date().getTime());
      closeDateEnd.setTime(tmpDate.getTime());
    } catch (Exception e) {
      closeDateEnd = null;
    }
}

  public void setOwner(int tmp) {
    this.owner = tmp;
  }

  public String getAccountOwnerIdRange() {
    return accountOwnerIdRange;
  }

  public String getOwnerIdRange() {
    return ownerIdRange;
  }

  public int getListSize() {
    return this.size();
  }

  public int getEnteredBy() {
    return enteredBy;
  }

  public boolean getHasAlertDate() {
    return hasAlertDate;
  }

  public String getDescription() {
    return description;
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
      "FROM opportunity_header x " +
      "WHERE x.opp_id > -1 ");

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
            "AND x.description < ? ");
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
      
      pagedListInfo.setDefaultSort("x.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY x.entered");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
      " x.*, org.name as acct_name, org.enabled as accountenabled, " +
        "ct.namelast as last_name, ct.namefirst as first_name, " +
        "ct.company as ctcompany, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst " +
        "FROM opportunity_header x " +
        "LEFT JOIN organization org ON (x.acctlink = org.org_id) " +
        "LEFT JOIN contact ct_eb ON (x.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (x.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN contact ct ON (x.contactlink = ct.contact_id) " +
        "WHERE x.opp_id > -1 ");
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
      OpportunityHeader thisOppHeader = new OpportunityHeader(rs);
      thisOppHeader.retrieveComponentCount(db);
      
      if (buildTotalValues) {
        thisOppHeader.buildTotal(db);
      }
      
      thisOppHeader.buildFiles(db);
      this.addElement(thisOppHeader);
    }
    rs.close();
    pst.close();
  }


  public void addIgnoreTypeId(String tmp) {
    ignoreTypeIdList.addElement(tmp);
  }

  public void addIgnoreTypeId(int tmp) {
    ignoreTypeIdList.addElement("" + tmp);
  }

  public void delete(Connection db) throws SQLException {
    Iterator opportunities = this.iterator();
    while (opportunities.hasNext()) {
      Opportunity thisOpportunity = (Opportunity) opportunities.next();
      thisOpportunity.delete(db);
    }
  }

  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (orgId != -1) {
      sqlFilter.append("AND x.acctlink = ? ");
    }

    if (contactId != -1) {
      sqlFilter.append("AND x.contactlink = ? ");
    }

    if (enteredBy != -1) {
      sqlFilter.append("AND x.enteredby = ? ");
    }

    if (ignoreTypeIdList.size() > 0) {
      Iterator iList = ignoreTypeIdList.iterator();
      sqlFilter.append("AND x.contactlink NOT IN (");
      while (iList.hasNext()) {
        String placeHolder = (String) iList.next();
        sqlFilter.append("?");
        if (iList.hasNext()) {
          sqlFilter.append(",");
        }
      }
      sqlFilter.append(") ");
    }

    if (description != null) {
      if (description.indexOf("%") >= 0) {
        sqlFilter.append("AND lower(x.description) like lower(?) ");
      } else {
        sqlFilter.append("AND lower(x.description) = lower(?) ");
      }
    }

    if (accountOwnerIdRange != null) {
      sqlFilter.append("AND x.acctlink IN (SELECT org_id FROM organization WHERE owner IN (" + accountOwnerIdRange + ")) ");
    }

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
    if (orgId != -1) {
      pst.setInt(++i, orgId);
    }

    if (contactId != -1) {
      pst.setInt(++i, contactId);
    }

    if (enteredBy != -1) {
      pst.setInt(++i, enteredBy);
    }

    if (ignoreTypeIdList.size() > 0) {
      Iterator iList = ignoreTypeIdList.iterator();
      while (iList.hasNext()) {
        int thisType = Integer.parseInt((String) iList.next());
        pst.setInt(++i, thisType);
      }
    }

    if (description != null) {
      pst.setString(++i, description);
    }

    return i;
  }
  
  public static int retrieveRecordCount(Connection db, int moduleId, int itemId) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
      "SELECT COUNT(*) as itemcount " +
      "FROM opportunity_header o " +
      "LEFT JOIN opportunity_component oc ON (o.opp_id = oc.opp_id) " +
      "WHERE opp_id > 0 ");
    if (moduleId == Constants.ACCOUNTS) {  
      sql.append("AND o.acctlink = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (moduleId == Constants.ACCOUNTS) {
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

}

