//Copyright 2001-2002 Dark Horse Ventures

package org.aspcfs.modules.accounts.base;

import java.util.*;
import java.sql.*;
import java.text.DateFormat;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;

/**
 *  Contains a list of organizations... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 *@author     mrajkowski
 *@created    August 30, 2001
 *@version    $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski
 *      Exp $
 */
public class OrganizationList extends Vector implements SyncableList {

  public final static int TRUE = 1;
  public final static int FALSE = 0;
  protected int includeEnabled = TRUE;

  public final static String tableName = "organization";
  public final static String uniqueField = "org_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  protected PagedListInfo pagedListInfo = null;

  protected Boolean minerOnly = null;
  protected int enteredBy = -1;
  protected String name = null;
  protected int ownerId = -1;
  protected int orgId = -1;
  protected String HtmlJsEvent = "";
  protected boolean showMyCompany = false;
  protected String ownerIdRange = null;
  protected boolean hasAlertDate = false;
  protected boolean hasExpireDate = false;
  protected String accountNumber = null;

  protected int revenueType = 0;
  protected int revenueYear = -1;
  protected int revenueOwnerId = -1;
  protected boolean buildRevenueYTD = false;
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;

  protected int typeId = 0;


  /**
   *  Constructor for the OrganizationList object, creates an empty list. After
   *  setting parameters, call the build method.
   *
   *@since    1.1
   */
  public OrganizationList() { }


  /**
   *  Sets the lastAnchor attribute of the OrganizationList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the OrganizationList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the OrganizationList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the OrganizationList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the syncType attribute of the OrganizationList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Gets the typeId attribute of the OrganizationList object
   *
   *@return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Sets the typeId attribute of the OrganizationList object
   *
   *@param  typeId  The new typeId value
   */
  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }


  /**
   *  Sets the typeId attribute of the OrganizationList object
   *
   *@param  typeId  The new typeId value
   */
  public void setTypeId(String typeId) {
    this.typeId = Integer.parseInt(typeId);
  }


  /**
   *  Sets the syncType attribute of the OrganizationList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the PagedListInfo attribute of the OrganizationList object. <p>
   *
   *  The query results will be constrained to the PagedListInfo parameters.
   *
   *@param  tmp  The new PagedListInfo value
   *@since       1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the MinerOnly attribute of the OrganizationList object to limit the
   *  results to miner only, or non-miner only.
   *
   *@param  tmp  The new MinerOnly value
   *@since       1.1
   */
  public void setMinerOnly(boolean tmp) {
    this.minerOnly = new Boolean(tmp);
  }


  /**
   *  Sets the revenueType attribute of the OrganizationList object
   *
   *@param  tmp  The new revenueType value
   */
  public void setRevenueType(int tmp) {
    this.revenueType = tmp;
  }


  /**
   *  Sets the revenueYear attribute of the OrganizationList object
   *
   *@param  tmp  The new revenueYear value
   */
  public void setRevenueYear(int tmp) {
    this.revenueYear = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the OrganizationList object
   *
   *@param  alertRangeStart  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp alertRangeStart) {
    this.alertRangeStart = alertRangeStart;
  }



  /**
   *  Sets the alertRangeEnd attribute of the OrganizationList object
   *
   *@param  alertRangeEnd  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp alertRangeEnd) {
    this.alertRangeEnd = alertRangeEnd;
  }


  /**
   *  Gets the revenueType attribute of the OrganizationList object
   *
   *@return    The revenueType value
   */
  public int getRevenueType() {
    return revenueType;
  }


  /**
   *  Gets the revenueYear attribute of the OrganizationList object
   *
   *@return    The revenueYear value
   */
  public int getRevenueYear() {
    return revenueYear;
  }


  /**
   *  Gets the includeEnabled attribute of the OrganizationList object
   *
   *@return    The includeEnabled value
   */
  public int getIncludeEnabled() {
    return includeEnabled;
  }


  /**
   *  Sets the includeEnabled attribute of the OrganizationList object
   *
   *@param  includeEnabled  The new includeEnabled value
   */
  public void setIncludeEnabled(int includeEnabled) {
    this.includeEnabled = includeEnabled;
  }


  /**
   *  Sets the ShowMyCompany attribute of the OrganizationList object
   *
   *@param  showMyCompany  The new ShowMyCompany value
   */
  public void setShowMyCompany(boolean showMyCompany) {
    this.showMyCompany = showMyCompany;
  }


  /**
   *  Sets the hasAlertDate attribute of the OrganizationList object
   *
   *@param  hasAlertDate  The new hasAlertDate value
   */
  public void setHasAlertDate(boolean hasAlertDate) {
    this.hasAlertDate = hasAlertDate;
  }


  /**
   *  Sets the HtmlJsEvent attribute of the OrganizationList object
   *
   *@param  HtmlJsEvent  The new HtmlJsEvent value
   */
  public void setHtmlJsEvent(String HtmlJsEvent) {
    this.HtmlJsEvent = HtmlJsEvent;
  }


  /**
   *  Gets the accountNumber attribute of the OrganizationList object
   *
   *@return    The accountNumber value
   */
  public String getAccountNumber() {
    return accountNumber;
  }


  /**
   *  Sets the accountNumber attribute of the OrganizationList object
   *
   *@param  accountNumber  The new accountNumber value
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }


  /**
   *  Sets the EnteredBy attribute of the OrganizationList object to limit
   *  results to those records entered by that user.
   *
   *@param  tmp  The new EnteredBy value
   *@since       1.1
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Gets the buildRevenueYTD attribute of the OrganizationList object
   *
   *@return    The buildRevenueYTD value
   */
  public boolean getBuildRevenueYTD() {
    return buildRevenueYTD;
  }


  /**
   *  Sets the buildRevenueYTD attribute of the OrganizationList object
   *
   *@param  buildRevenueYTD  The new buildRevenueYTD value
   */
  public void setBuildRevenueYTD(boolean buildRevenueYTD) {
    this.buildRevenueYTD = buildRevenueYTD;
  }


  /**
   *  Sets the ownerIdRange attribute of the OrganizationList object
   *
   *@param  tmp  The new ownerIdRange value
   */
  public void setOwnerIdRange(String tmp) {
    this.ownerIdRange = tmp;
  }


  /**
   *  Sets the Name attribute of the OrganizationList object to limit results to
   *  those records matching the name. Use a % in the name for wildcard
   *  matching.
   *
   *@param  tmp  The new Name value
   *@since       1.1
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the revenueOwnerId attribute of the OrganizationList object
   *
   *@return    The revenueOwnerId value
   */
  public int getRevenueOwnerId() {
    return revenueOwnerId;
  }


  /**
   *  Building Alert Counts Sets the revenueOwnerId attribute of the
   *  OrganizationList object
   *
   *@param  revenueOwnerId  The new revenueOwnerId value
   */
  public void setRevenueOwnerId(int revenueOwnerId) {
    this.revenueOwnerId = revenueOwnerId;
  }


  /**
   *  Sets the hasExpireDate attribute of the OrganizationList object
   *
   *@param  hasExpireDate  The new hasExpireDate value
   */
  public void setHasExpireDate(boolean hasExpireDate) {
    this.hasExpireDate = hasExpireDate;
  }


  /**
   *  Sets the OwnerId attribute of the OrganizationList object
   *
   *@param  ownerId  The new OwnerId value
   */
  public void setOwnerId(int ownerId) {
    this.ownerId = ownerId;
  }


  /**
   *  Sets the orgId attribute of the OrganizationList object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the OrganizationList object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the tableName attribute of the OrganizationList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the OrganizationList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the hasAlertDate attribute of the OrganizationList object
   *
   *@return    The hasAlertDate value
   */
  public boolean getHasAlertDate() {
    return hasAlertDate;
  }


  /**
   *  Gets the ownerIdRange attribute of the OrganizationList object
   *
   *@return    The ownerIdRange value
   */
  public String getOwnerIdRange() {
    return ownerIdRange;
  }


  /**
   *  Gets the hasExpireDate attribute of the OrganizationList object
   *
   *@return    The hasExpireDate value
   */
  public boolean getHasExpireDate() {
    return hasExpireDate;
  }


  /**
   *  Gets the ShowMyCompany attribute of the OrganizationList object
   *
   *@return    The ShowMyCompany value
   */
  public boolean getShowMyCompany() {
    return showMyCompany;
  }


  /**
   *  Gets the HtmlJsEvent attribute of the OrganizationList object
   *
   *@return    The HtmlJsEvent value
   */
  public String getHtmlJsEvent() {
    return HtmlJsEvent;
  }


  /**
   *  Gets the OwnerId attribute of the OrganizationList object
   *
   *@return    The OwnerId value
   */
  public int getOwnerId() {
    return ownerId;
  }


  /**
   *  Gets the orgId attribute of the OrganizationList object
   *
   *@return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the HtmlSelect attribute of the ContactList object
   *
   *@param  selectName  Description of Parameter
   *@return             The HtmlSelect value
   *@since              1.8
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the HtmlSelect attribute of the ContactList object
   *
   *@param  selectName  Description of Parameter
   *@param  defaultKey  Description of Parameter
   *@return             The HtmlSelect value
   *@since              1.8
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect orgListSelect = new HtmlSelect();

    Iterator i = this.iterator();
    while (i.hasNext()) {
      Organization thisOrg = (Organization) i.next();
      orgListSelect.addItem(
          thisOrg.getOrgId(),
          thisOrg.getName());
    }

    if (!(this.getHtmlJsEvent().equals(""))) {
      orgListSelect.setJsEvent(this.getHtmlJsEvent());
    }

    return orgListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Gets the HtmlSelectDefaultNone attribute of the OrganizationList object
   *
   *@param  selectName  Description of Parameter
   *@return             The HtmlSelectDefaultNone value
   */
  public String getHtmlSelectDefaultNone(String selectName) {
    return getHtmlSelectDefaultNone(selectName, -1);
  }


  /**
   *  Gets the htmlSelectDefaultNone attribute of the OrganizationList object
   *
   *@param  selectName  Description of the Parameter
   *@param  defaultKey  Description of the Parameter
   *@return             The htmlSelectDefaultNone value
   */
  public String getHtmlSelectDefaultNone(String selectName, int defaultKey) {
    HtmlSelect orgListSelect = new HtmlSelect();
    orgListSelect.addItem(-1, "-- None --");

    Iterator i = this.iterator();
    while (i.hasNext()) {
      Organization thisOrg = (Organization) i.next();
      orgListSelect.addItem(
          thisOrg.getOrgId(),
          thisOrg.getName());
    }

    if (!(this.getHtmlJsEvent().equals(""))) {
      orgListSelect.setJsEvent(this.getHtmlJsEvent());
    }

    return orgListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  timeZone          Description of the Parameter
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

    String sqlDate = ((hasAlertDate ? "alertdate " : "") + (hasExpireDate ? "contract_end " : ""));
    createFilter(sqlFilter);

    sqlSelect.append(
        "SELECT " + sqlDate + ", count(*) as nocols " +
        "FROM organization o " +
        "WHERE o.org_id >= 0 ");

    sqlTail.append("GROUP BY " + sqlDate);
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      String alertDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, rs.getTimestamp(sqlDate.trim()));
      int temp = rs.getInt("nocols");
      events.put(alertDate, new Integer(temp));
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
        "SELECT o.org_id, o.name, o.alertdate, o.alert, o.contract_end " +
        "FROM organization o " +
        "WHERE o.org_id >= 0 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Organization thisOrg = new Organization();
      thisOrg.setOrgId(rs.getInt("org_id"));
      thisOrg.setName(rs.getString("name"));
      thisOrg.setAlertDate(rs.getTimestamp("alertdate"));
      thisOrg.setAlertText(rs.getString("alert"));
      thisOrg.setContractEndDate(rs.getTimestamp("contract_end"));
      this.add(thisOrg);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Queries the database, using any of the filters, to retrieve a list of
   *  organizations. The organizations are appended, so build can be run any
   *  number of times to generate a larger list for a report.
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.isEndOfOffset(db)) {
        break;
      }
      Organization thisOrganization = this.getObject(rs);

      //if this is an individual account, populate the primary contact record
      if (thisOrganization.getNameLast() != null) {
        thisOrganization.populatePrimaryContact(db);
      }

      if (buildRevenueYTD && revenueYear > -1 && revenueOwnerId > -1) {
        thisOrganization.buildRevenueYTD(db, this.getRevenueYear(), this.getRevenueType(), this.getRevenueOwnerId());
        if (thisOrganization.getYTD() != 0) {
          this.add(thisOrganization);
        }
      } else {
        this.add(thisOrganization);
      }
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    buildResources(db);
  }


  /**
   *  Gets the object attribute of the OrganizationList object
   *
   *@param  rs                Description of the Parameter
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public Organization getObject(ResultSet rs) throws SQLException {
    Organization thisOrganization = new Organization(rs);
    return thisOrganization;
  }


  /**
   *  This method is required for synchronization, it allows for the resultset
   *  to be streamed with lower overhead
   *
   *@param  db                Description of the Parameter
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM organization o " +
        "WHERE o.org_id >= 0 ");

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
      rs.close();
      pst.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND o.name < ? ");
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
      pagedListInfo.setDefaultSort("o.name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY o.name ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "o.*, " +
        "ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, " +
        "i.description as industry_name " +
        "FROM organization o " +
        "LEFT JOIN contact ct_owner ON (o.owner = ct_owner.user_id) " +
        "LEFT JOIN contact ct_eb ON (o.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (o.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN lookup_industry i ON (o.industry_temp_code = i.code) " +
        "WHERE o.org_id >= 0 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();

    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    return rs;
  }


  /**
   *  Builds a base SQL where statement for filtering records to be used by
   *  sqlSelect and sqlCount
   *
   *@param  sqlFilter  Description of Parameter
   *@since             1.2
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (minerOnly != null) {
      sqlFilter.append("AND miner_only = ? ");
    }

    if (enteredBy > -1) {
      sqlFilter.append("AND o.enteredby = ? ");
    }

    if (name != null) {
      if (name.indexOf("%") >= 0) {
        sqlFilter.append("AND lower(o.name) like lower(?) ");
      } else {
        sqlFilter.append("AND lower(o.name) = lower(?) ");
      }
    }

    if (ownerId > -1) {
      sqlFilter.append("AND o.owner = ? ");
    }

    if (ownerIdRange != null) {
      sqlFilter.append("AND o.owner IN (" + ownerIdRange + ") ");
    }

    if (includeEnabled == TRUE || includeEnabled == FALSE) {
      sqlFilter.append("AND o.enabled = ? ");
    }

    if (showMyCompany == false) {
      sqlFilter.append("AND o.org_id != 0 ");
    }

    if (hasAlertDate == true) {
      sqlFilter.append("AND o.alertdate is not null ");
      if (alertRangeStart != null) {
        sqlFilter.append("AND o.alertdate >= ? ");
      }

      if (alertRangeEnd != null) {
        sqlFilter.append("AND o.alertdate < ? ");
      }
    }

    if (hasExpireDate == true) {
      sqlFilter.append("AND o.contract_end is not null ");
      if (alertRangeStart != null) {
        sqlFilter.append("AND o.contract_end >= ? ");
      }

      if (alertRangeEnd != null) {
        sqlFilter.append("AND o.contract_end <= ? ");
      }
    }

    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
    }

    if (revenueOwnerId > -1) {
      sqlFilter.append("AND o.org_id in (SELECT org_id from revenue WHERE owner = ?) ");
    }

    if (accountNumber != null) {
      if (accountNumber.indexOf("%") >= 0) {
        sqlFilter.append("AND lower(o.account_number) like lower(?) ");
      } else {
        sqlFilter.append("AND lower(o.account_number) = lower(?) ");
      }
    }

    if (typeId > 0) {
      sqlFilter.append("AND o.org_id IN (select atl.org_id from account_type_levels atl where atl.type_id = ?) ");
    }
    
    if (orgId > 0){
      sqlFilter.append("AND o.org_id = ? ");
    }
  }


  /**
   *  Convenience method to get a list of phone numbers for each contact
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.5
   */
  protected void buildResources(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Organization thisOrg = (Organization) i.next();
      thisOrg.getPhoneNumberList().buildList(db);
      thisOrg.getAddressList().buildList(db);
      thisOrg.getEmailAddressList().buildList(db);
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
      Organization thisOrg = (Organization) i.next();
      if (thisOrg.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }


  /**
   *  Sets the parameters for the preparedStatement - these items must
   *  correspond with the createFilter statement
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.2
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (minerOnly != null) {
      pst.setBoolean(++i, minerOnly.booleanValue());
    }

    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }

    if (name != null) {
      pst.setString(++i, name);
    }

    if (ownerId > -1) {
      pst.setInt(++i, ownerId);
    }

    if (includeEnabled == TRUE) {
      pst.setBoolean(++i, true);
    } else if (includeEnabled == FALSE) {
      pst.setBoolean(++i, false);
    }

    if (hasAlertDate == true) {
      if (alertRangeStart != null) {
        pst.setTimestamp(++i, alertRangeStart);
      }
      if (alertRangeEnd != null) {
        pst.setTimestamp(++i, alertRangeEnd);
      }
    }

    if (hasExpireDate == true) {
      if (alertRangeStart != null) {
        pst.setTimestamp(++i, alertRangeStart);
      }
      if (alertRangeEnd != null) {
        pst.setTimestamp(++i, alertRangeEnd);
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

    if (revenueOwnerId > -1) {
      pst.setInt(++i, revenueOwnerId);
    }

    if (accountNumber != null) {
      pst.setString(++i, accountNumber);
    }

    if (typeId > 0) {
      pst.setInt(++i, typeId);
    }

    if (orgId > 0){
      pst.setInt(++i, orgId);
    }

    return i;
  }

}


