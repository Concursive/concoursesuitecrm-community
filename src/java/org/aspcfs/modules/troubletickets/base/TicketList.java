//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.base;

import java.util.*;
import java.sql.*;
import java.text.DateFormat;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import java.util.Calendar;
import org.aspcfs.utils.DateUtils;

/**
 *  A collection of Ticket objects, can also be used for querying and filtering
 *  the tickets that are included in the list.
 *
 *@author     chris
 *@created    December 5, 2001
 *@version    $Id: TicketList.java,v 1.31.12.1 2004/01/30 16:35:15 kbhoopal Exp
 *      $
 */
public class TicketList extends ArrayList implements SyncableList {
  //sync api
  public final static String tableName = "ticket";
  public final static String uniqueField = "ticketid";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  //filters
  private PagedListInfo pagedListInfo = null;
  private int enteredBy = -1;
  private boolean onlyOpen = false;
  private boolean onlyClosed = false;
  private int id = -1;
  private int orgId = -1;
  private int serviceContractId = -1;
  private int assetId = -1;
  private int department = -1;
  private int assignedTo = -1;
  private int excludeAssignedTo = -1;
  private boolean onlyAssigned = false;
  private boolean onlyUnassigned = false;
  private boolean unassignedToo = false;
  private int severity = 0;
  private int priority = 0;
  private String accountOwnerIdRange = null;
  private String description = null;
  private int minutesOlderThan = -1;
  private int productId = -1;
  private int customerProductId = -1;
  private boolean onlyWithProducts = false;
  private boolean hasEstimatedResolutionDate = false;
  private int projectId = -1;
  private int forProjectUser = -1;
  //search filters
  private String searchText = "";
  //calendar
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;


  /**
   *  Constructor for the TicketList object
   *
   *@since
   */
  public TicketList() { }


  /**
   *  Sets the lastAnchor attribute of the TicketList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the TicketList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    try {
      this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
    } catch (Exception e) {
      this.lastAnchor = null;
    }
  }


  /**
   *  Sets the nextAnchor attribute of the TicketList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the TicketList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    try {
      this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
    } catch (Exception e) {
      this.nextAnchor = null;
    }
  }


  /**
   *  Sets the syncType attribute of the TicketList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the Id attribute of the TicketList object
   *
   *@param  id  The new Id value
   *@since
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the Id attribute of the TicketList object
   *
   *@param  id  The new Id value
   *@since
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   *  Sets the assignedTo attribute of the TicketList object
   *
   *@param  assignedTo  The new assignedTo value
   */
  public void setAssignedTo(int assignedTo) {
    this.assignedTo = assignedTo;
  }


  /**
   *  Sets the assignedTo attribute of the TicketList object
   *
   *@param  assignedTo  The new assignedTo value
   */
  public void setAssignedTo(String assignedTo) {
    this.assignedTo = Integer.parseInt(assignedTo);
  }


  /**
   *  Sets the excludeAssignedTo attribute of the TicketList object
   *
   *@param  tmp  The new excludeAssignedTo value
   */
  public void setExcludeAssignedTo(int tmp) {
    this.excludeAssignedTo = tmp;
  }


  /**
   *  Sets the excludeAssignedTo attribute of the TicketList object
   *
   *@param  tmp  The new excludeAssignedTo value
   */
  public void setExcludeAssignedTo(String tmp) {
    this.excludeAssignedTo = Integer.parseInt(tmp);
  }


  /**
   *  Sets the onlyAssigned attribute of the TicketList object
   *
   *@param  tmp  The new onlyAssigned value
   */
  public void setOnlyAssigned(boolean tmp) {
    this.onlyAssigned = tmp;
  }


  /**
   *  Sets the onlyAssigned attribute of the TicketList object
   *
   *@param  tmp  The new onlyAssigned value
   */
  public void setOnlyAssigned(String tmp) {
    this.onlyAssigned = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the onlyUnassigned attribute of the TicketList object
   *
   *@param  tmp  The new onlyUnassigned value
   */
  public void setOnlyUnassigned(boolean tmp) {
    this.onlyUnassigned = tmp;
  }


  /**
   *  Sets the onlyUnassigned attribute of the TicketList object
   *
   *@param  tmp  The new onlyUnassigned value
   */
  public void setOnlyUnassigned(String tmp) {
    this.onlyUnassigned = DatabaseUtils.parseBoolean(tmp);
  }



  /**
   *  Sets the unassignedToo attribute of the TicketList object
   *
   *@param  unassignedToo  The new unassignedToo value
   */
  public void setUnassignedToo(boolean unassignedToo) {
    this.unassignedToo = unassignedToo;
  }


  /**
   *  Sets the severity attribute of the TicketList object
   *
   *@param  tmp  The new severity value
   */
  public void setSeverity(int tmp) {
    this.severity = tmp;
  }


  /**
   *  Sets the priority attribute of the TicketList object
   *
   *@param  tmp  The new priority value
   */
  public void setPriority(int tmp) {
    this.priority = tmp;
  }


  /**
   *  Sets the severity attribute of the TicketList object
   *
   *@param  tmp  The new severity value
   */
  public void setSeverity(String tmp) {
    this.severity = Integer.parseInt(tmp);
  }


  /**
   *  Sets the projectId attribute of the TicketList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the forProjectUser attribute of the TicketList object
   *
   *@param  tmp  The new forProjectUser value
   */
  public void setForProjectUser(int tmp) {
    this.forProjectUser = tmp;
  }


  /**
   *  Sets the forProjectUser attribute of the TicketList object
   *
   *@param  tmp  The new forProjectUser value
   */
  public void setForProjectUser(String tmp) {
    this.forProjectUser = Integer.parseInt(tmp);
  }


  /**
   *  Sets the customerProductId attribute of the TicketList object
   *
   *@param  tmp  The new customerProductId value
   */
  public void setCustomerProductId(int tmp) {
    this.customerProductId = tmp;
  }


  /**
   *  Sets the customerProductId attribute of the TicketList object
   *
   *@param  tmp  The new customerProductId value
   */
  public void setCustomerProductId(String tmp) {
    this.customerProductId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the customerProductId attribute of the TicketList object
   *
   *@return    The customerProductId value
   */
  public int getCustomerProductId() {
    return customerProductId;
  }


  /**
   *  Sets the onlyWithProducts attribute of the TicketList object
   *
   *@param  tmp  The new onlyWithProducts value
   */
  public void setOnlyWithProducts(boolean tmp) {
    this.onlyWithProducts = tmp;
  }


  /**
   *  Sets the hasEstimatedResolutionDate attribute of the TicketList object
   *
   *@param  tmp  The new hasEstimatedResolutionDate value
   */
  public void setHasEstimatedResolutionDate(boolean tmp) {
    this.hasEstimatedResolutionDate = tmp;
  }


  /**
   *  Sets the hasEstimatedResolutionDate attribute of the TicketList object
   *
   *@param  tmp  The new hasEstimatedResolutionDate value
   */
  public void setHasEstimatedResolutionDate(String tmp) {
    this.hasEstimatedResolutionDate = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the tableName attribute of the TicketList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the TicketList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Sets the priority attribute of the TicketList object
   *
   *@param  tmp  The new priority value
   */
  public void setPriority(String tmp) {
    this.priority = Integer.parseInt(tmp);
  }


  /**
   *  Sets the searchText attribute of the TicketList object
   *
   *@param  searchText  The new searchText value
   */
  public void setSearchText(String searchText) {
    this.searchText = searchText;
  }


  /**
   *  Sets the accountOwnerIdRange attribute of the TicketList object
   *
   *@param  accountOwnerIdRange  The new accountOwnerIdRange value
   */
  public void setAccountOwnerIdRange(String accountOwnerIdRange) {
    this.accountOwnerIdRange = accountOwnerIdRange;
  }


  /**
   *  Sets the OrgId attribute of the TicketList object
   *
   *@param  orgId  The new OrgId value
   *@since
   */
  public void setOrgId(int orgId) {
    this.orgId = orgId;
  }


  /**
   *  Sets the OrgId attribute of the TicketList object
   *
   *@param  orgId  The new OrgId value
   *@since
   */
  public void setOrgId(String orgId) {
    this.orgId = Integer.parseInt(orgId);
  }


  /**
   *  Sets the serviceContractId attribute of the TicketList object
   *
   *@param  tmp  The new serviceContractId value
   */
  public void setServiceContractId(int tmp) {
    this.serviceContractId = tmp;
  }


  /**
   *  Sets the serviceContractId attribute of the TicketList object
   *
   *@param  tmp  The new serviceContractId value
   */
  public void setServiceContractId(String tmp) {
    this.serviceContractId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the assetId attribute of the TicketList object
   *
   *@param  tmp  The new assetId value
   */
  public void setAssetId(int tmp) {
    this.assetId = tmp;
  }


  /**
   *  Sets the assetId attribute of the TicketList object
   *
   *@param  tmp  The new assetId value
   */
  public void setAssetId(String tmp) {
    this.assetId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the PagedListInfo attribute of the TicketList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the TicketList object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Gets the description attribute of the TicketList object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the TicketList object
   *
   *@param  description  The new description value
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   *  Sets the minutesOlderThan attribute of the TicketList object
   *
   *@param  tmp  The new minutesOlderThan value
   */
  public void setMinutesOlderThan(int tmp) {
    this.minutesOlderThan = tmp;
  }


  /**
   *  Sets the minutesOlderThan attribute of the TicketList object
   *
   *@param  tmp  The new minutesOlderThan value
   */
  public void setMinutesOlderThan(String tmp) {
    this.minutesOlderThan = Integer.parseInt(tmp);
  }


  /**
   *  Sets the onlyClosed attribute of the TicketList object
   *
   *@param  onlyClosed  The new onlyClosed value
   */
  public void setOnlyClosed(boolean onlyClosed) {
    this.onlyClosed = onlyClosed;
  }


  /**
   *  Sets the OnlyOpen attribute of the TicketList object
   *
   *@param  onlyOpen  The new OnlyOpen value
   *@since
   */
  public void setOnlyOpen(boolean onlyOpen) {
    this.onlyOpen = onlyOpen;
  }


  /**
   *  Sets the Department attribute of the TicketList object
   *
   *@param  department  The new Department value
   *@since
   */
  public void setDepartment(int department) {
    this.department = department;
  }


  /**
   *  Sets the alertRangeStart attribute of the TicketList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the TicketList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the alertRangeEnd attribute of the TicketList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   *  Sets the alertRangeEnd attribute of the TicketList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the assignedTo attribute of the TicketList object
   *
   *@return    The assignedTo value
   */
  public int getAssignedTo() {
    return assignedTo;
  }


  /**
   *  Gets the excludeAssignedTo attribute of the TicketList object
   *
   *@return    The excludeAssignedTo value
   */
  public int getExcludeAssignedTo() {
    return excludeAssignedTo;
  }


  /**
   *  Gets the onlyAssigned attribute of the TicketList object
   *
   *@return    The onlyAssigned value
   */
  public boolean getOnlyAssigned() {
    return onlyAssigned;
  }


  /**
   *  Gets the onlyUnassigned attribute of the TicketList object
   *
   *@return    The onlyUnassigned value
   */
  public boolean getOnlyUnassigned() {
    return onlyUnassigned;
  }


  /**
   *  Gets the unassignedToo attribute of the TicketList object
   *
   *@return    The unassignedToo value
   */
  public boolean getUnassignedToo() {
    return unassignedToo;
  }


  /**
   *  Gets the severity attribute of the TicketList object
   *
   *@return    The severity value
   */
  public int getSeverity() {
    return severity;
  }


  /**
   *  Gets the priority attribute of the TicketList object
   *
   *@return    The priority value
   */
  public int getPriority() {
    return priority;
  }


  /**
   *  Gets the searchText attribute of the TicketList object
   *
   *@return    The searchText value
   */
  public String getSearchText() {
    return searchText;
  }


  /**
   *  Gets the accountOwnerIdRange attribute of the TicketList object
   *
   *@return    The accountOwnerIdRange value
   */
  public String getAccountOwnerIdRange() {
    return accountOwnerIdRange;
  }


  /**
   *  Gets the onlyClosed attribute of the TicketList object
   *
   *@return    The onlyClosed value
   */
  public boolean getOnlyClosed() {
    return onlyClosed;
  }


  /**
   *  Gets the OrgId attribute of the TicketList object
   *
   *@return    The OrgId value
   *@since
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the serviceContractId attribute of the TicketList object
   *
   *@return    The serviceContractId value
   */
  public int getServiceContractId() {
    return serviceContractId;
  }


  /**
   *  Gets the assetId attribute of the TicketList object
   *
   *@return    The assetId value
   */
  public int getAssetId() {
    return assetId;
  }


  /**
   *  Gets the Id attribute of the TicketList object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the OnlyOpen attribute of the TicketList object
   *
   *@return    The OnlyOpen value
   *@since
   */
  public boolean getOnlyOpen() {
    return onlyOpen;
  }


  /**
   *  Gets the Department attribute of the TicketList object
   *
   *@return    The Department value
   *@since
   */
  public int getDepartment() {
    return department;
  }


  /**
   *  Gets the hasEstimatedResolutionDate attribute of the TicketList object
   *
   *@return    The hasEstimatedResolutionDate value
   */
  public boolean getHasEstimatedResolutionDate() {
    return hasEstimatedResolutionDate;
  }


  /**
   *  Gets the pagedListInfo attribute of the TicketList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the alertRangeStart attribute of the TicketList object
   *
   *@return    The alertRangeStart value
   */
  public java.sql.Timestamp getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   *  Gets the alertRangeEnd attribute of the TicketList object
   *
   *@return    The alertRangeEnd value
   */
  public java.sql.Timestamp getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   *  Gets the forProjectUser attribute of the TicketList object
   *
   *@return    The forProjectUser value
   */
  public int getForProjectUser() {
    return forProjectUser;
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
        "SELECT COUNT(*) AS recordcount " +
        "FROM ticket t " +
        "WHERE t.ticketid > 0 ");
    createFilter(sqlFilter, db);
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
            "AND t.problem < ? ");
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
      pagedListInfo.setDefaultSort("t.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY t.entered ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "t.*, " +
        "o.name AS orgname, o.enabled AS orgenabled, " +
        "ld.description AS dept, " +
        "tp.description AS ticpri, " +
        "ts.description AS ticsev, " +
        "tc.description AS catname, " +
        "lu_ts.description AS sourcename, " +
        "sc.contract_number AS contractnumber, " +
        "sc.total_hours_remaining AS hoursremaining, " +
        "sc.current_start_date AS contractstartdate, " +
        "sc.current_end_date AS contractenddate, " +
        "sc.onsite_service_model AS contractonsiteservicemodel, " +
        "a.serial_number AS serialnumber, " +
        "a.manufacturer AS assetmanufacturer, " +
        "a.vendor AS assetvendor, " +
        "a.model_version AS modelversion, " +
        "a.location AS assetlocation, " +
        "a.onsite_service_model AS assetonsiteservicemodel , " +
        "pc.sku AS productsku , " +
        "pc.product_name AS productname, " +
        "tlp.project_id " +
        "FROM ticket t " +
        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
        "LEFT JOIN lookup_department ld ON (t.department_code = ld.code) " +
        "LEFT JOIN ticket_priority tp ON (t.pri_code = tp.code) " +
        "LEFT JOIN ticket_severity ts ON (t.scode = ts.code) " +
        "LEFT JOIN ticket_category tc ON (t.cat_code = tc.id) " +
        "LEFT JOIN lookup_ticketsource lu_ts ON (t.source_code = lu_ts.code) " +
        "LEFT JOIN service_contract sc ON (t.link_contract_id = sc.contract_id) " +
        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
        "LEFT JOIN product_catalog pc ON (t.product_id = pc.product_id) " +
        "LEFT JOIN ticketlink_project tlp ON (t.ticketid = tlp.ticket_id) " +
        "WHERE t.ticketid > 0 ");
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
      Ticket thisTicket = new Ticket(rs);
      this.add(thisTicket);
    }
    rs.close();
    pst.close();
    //Build resources
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Ticket thisTicket = (Ticket) i.next();
      thisTicket.buildFiles(db);
      if (thisTicket.getAssignedTo() > -1) {
        thisTicket.checkEnabledOwnerAccount(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  basePath          Description of the Parameter
   *@exception  SQLException  Description of Exception
   */
  public void delete(Connection db, String basePath) throws SQLException {
    Iterator tickets = this.iterator();
    while (tickets.hasNext()) {
      Ticket thisTicket = (Ticket) tickets.next();
      thisTicket.delete(db, basePath);
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
      Ticket thisTicket = (Ticket) i.next();
      if (thisTicket.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }


  /**
   *  Builds a base SQL where statement for filtering records to be used by
   *  sqlSelect and sqlCount
   *
   *@param  sqlFilter  Description of Parameter
   *@param  db         Description of the Parameter
   *@since             1.2
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    if (enteredBy > -1) {
      sqlFilter.append("AND t.enteredby = ? ");
    }
    if (description != null) {
      if (description.indexOf("%") >= 0) {
        sqlFilter.append("AND lower(t.problem) like lower(?) ");
      } else {
        sqlFilter.append("AND lower(t.problem) = lower(?) ");
      }
    }
    if (onlyOpen) {
      sqlFilter.append("AND t.closed IS NULL ");
    }
    if (onlyClosed) {
      sqlFilter.append("AND t.closed IS NOT NULL ");
    }
    if (id > -1) {
      sqlFilter.append("AND t.ticketid = ? ");
    }
    if (orgId > -1) {
      sqlFilter.append("AND t.org_id = ? ");
    }
    if (serviceContractId > -1) {
      sqlFilter.append("AND t.link_contract_id = ? ");
    }
    if (assetId > -1) {
      sqlFilter.append("AND t.link_asset_id = ? ");
    }
    if (department > -1) {
      if (unassignedToo) {
        sqlFilter.append("AND (t.department_code in (?, 0, -1) OR (t.department_code IS NULL)) ");
      } else {
        sqlFilter.append("AND t.department_code = ? ");
      }
    }
    if (assignedTo > -1) {
      sqlFilter.append("AND t.assigned_to = ? ");
    }
    if (excludeAssignedTo > -1) {
      sqlFilter.append("AND (t.assigned_to <> ? OR t.assigned_to IS NULL) ");
    }
    if (onlyAssigned) {
      sqlFilter.append("AND t.assigned_to > 0 AND t.assigned_to IS NOT NULL ");
    }
    if (onlyUnassigned) {
      sqlFilter.append("AND (t.assigned_to IS NULL OR t.assigned_to = 0 OR t.assigned_to = -1) ");
    }
    if (severity > 0) {
      sqlFilter.append("AND t.scode = ? ");
    }
    if (priority > 0) {
      sqlFilter.append("AND t.pri_code = ? ");
    }
    if (accountOwnerIdRange != null) {
      sqlFilter.append("AND t.org_id IN (SELECT org_id FROM organization WHERE owner IN (" + accountOwnerIdRange + ")) ");
    }
    if (productId != -1) {
      sqlFilter.append("AND t.product_id = ? ");
    }
    if (customerProductId != -1) {
      sqlFilter.append("AND t.customer_product_id = ? ");
    }
    if (onlyWithProducts == true) {
      sqlFilter.append("AND t.product_id IS NOT NULL ");
    }
    if (projectId > 0) {
      sqlFilter.append("AND t.ticketid IN (SELECT ticket_id FROM ticketlink_project WHERE project_id = ?) ");
    }
    if (forProjectUser > -1) {
      sqlFilter.append("AND t.ticketid IN (SELECT ticket_id FROM ticketlink_project WHERE project_id in (SELECT DISTINCT project_id FROM project_team WHERE user_id = ? " +
          "AND status IS NULL)) ");
    }
    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND t.entered >= ? ");
      }
      sqlFilter.append("AND t.entered < ? ");
    } else if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND t.modified >= ? ");
      sqlFilter.append("AND t.entered < ? ");
      sqlFilter.append("AND t.modified < ? ");
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        sqlFilter.append("AND t.entered >= ? ");
      }
      if (nextAnchor != null) {
        sqlFilter.append("AND t.entered < ? ");
      }
    } else {
      //No sync, but still need to factor in age
      if (minutesOlderThan > 0) {
        sqlFilter.append("AND t.entered <= ? ");
      }
    }
    if (searchText != null && !(searchText.equals(""))) {
      if (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL) {
        sqlFilter.append(
            "AND ( LOWER(CONVERT(VARCHAR(2000),t.problem)) LIKE LOWER(?) OR " +
            "LOWER(CONVERT(VARCHAR(2000),t.comment)) LIKE LOWER(?) OR " +
            "LOWER(CONVERT(VARCHAR(2000),t.solution)) LIKE LOWER(?) ) ");
      } else {
        sqlFilter.append(
            "AND ( LOWER(t.problem) LIKE LOWER(?) OR " +
            "LOWER(t.comment) LIKE LOWER(?) OR " +
            "LOWER(t.solution) LIKE LOWER(?) ) ");
      }
    }
    if (hasEstimatedResolutionDate == true) {
      sqlFilter.append("AND t.est_resolution_date IS NOT NULL ");
    }
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
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }
    if (description != null) {
      pst.setString(++i, description);
    }
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (orgId > -1) {
      pst.setInt(++i, orgId);
    }
    if (serviceContractId > -1) {
      pst.setInt(++i, serviceContractId);
    }
    if (assetId > -1) {
      pst.setInt(++i, assetId);
    }
    if (department > -1) {
      pst.setInt(++i, department);
    }
    if (assignedTo > -1) {
      pst.setInt(++i, assignedTo);
    }
    if (excludeAssignedTo > -1) {
      pst.setInt(++i, excludeAssignedTo);
    }
    if (severity > 0) {
      pst.setInt(++i, severity);
    }
    if (priority > 0) {
      pst.setInt(++i, priority);
    }
    if (productId > 0) {
      pst.setInt(++i, productId);
    }
    if (customerProductId > 0) {
      pst.setInt(++i, customerProductId);
    }
    if (projectId > 0) {
      pst.setInt(++i, projectId);
    }
    if (forProjectUser > -1) {
      pst.setInt(++i, forProjectUser);
    }
    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        java.sql.Timestamp adjustedDate = lastAnchor;
        if (minutesOlderThan > 0) {
          Calendar now = Calendar.getInstance();
          now.setTimeInMillis(lastAnchor.getTime());
          now.add(Calendar.MINUTE, minutesOlderThan - (2 * minutesOlderThan));
          adjustedDate = new java.sql.Timestamp(now.getTimeInMillis());
        }
        pst.setTimestamp(++i, adjustedDate);
      }
      if (nextAnchor != null) {
        java.sql.Timestamp adjustedDate = nextAnchor;
        if (minutesOlderThan > 0) {
          Calendar now = Calendar.getInstance();
          now.setTimeInMillis(nextAnchor.getTime());
          now.add(Calendar.MINUTE, minutesOlderThan - (2 * minutesOlderThan));
          adjustedDate = new java.sql.Timestamp(now.getTimeInMillis());
        }
        pst.setTimestamp(++i, adjustedDate);
      }
    } else {
      //No sync, but still need to factor in age
      if (minutesOlderThan > 0) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutesOlderThan - (2 * minutesOlderThan));
        java.sql.Timestamp adjustedDate = new java.sql.Timestamp(now.getTimeInMillis());
        pst.setTimestamp(++i, adjustedDate);
      }
    }

    if (searchText != null && !(searchText.equals(""))) {
      pst.setString(++i, searchText);
      pst.setString(++i, searchText);
      pst.setString(++i, searchText);
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
        "FROM ticket t " +
        "WHERE ticketid > 0 ");
    if (moduleId == Constants.ACCOUNTS) {
      sql.append("AND t.org_id = ?");
    }
    if (moduleId == Constants.SERVICE_CONTRACTS) {
      sql.append("AND t.link_contract_id = ?");
    }
    if (moduleId == Constants.ASSETS) {
      sql.append("AND t.link_asset_id = ?");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (moduleId == Constants.ACCOUNTS) {
      pst.setInt(1, itemId);
    }
    if (moduleId == Constants.SERVICE_CONTRACTS) {
      pst.setInt(1, itemId);
    }
    if (moduleId == Constants.ASSETS) {
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
   *  Each ticket in a project has its own unique count
   *
   *@param  db                Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void insertProjectTicketCount(Connection db, int projectId) throws SQLException {
    // Every new project needs a project_ticket_count record
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO project_ticket_count " +
        "(project_id) VALUES " +
        "(?) ");
    pst.setInt(1, projectId);
    pst.execute();
    pst.close();
  }

  public HashMap queryRecordCount(Connection db, TimeZone timeZone) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;

    HashMap events = new HashMap();
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlTail = new StringBuffer();

    createFilter(sqlFilter,db);

    sqlSelect.append(
        "SELECT est_resolution_date, count(*) as nocols " +
        "FROM ticket t " +
        "WHERE ticketid > -1 ");

    sqlTail.append("GROUP BY est_resolution_date ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    prepareFilter(pst);    
    rs = pst.executeQuery();
    while (rs.next()) {
      String estResolutionDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, rs.getTimestamp("est_resolution_date"));
      int tempcount=rs.getInt("nocols"); 
      events.put(estResolutionDate, new Integer(tempcount));
    }
    rs.close();
    pst.close();
    return events;
  }
  

  /**
   *  Each ticket in a project has its own unique count
   *
   *@param  db                Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void deleteProjectTicketCount(Connection db, int projectId) throws SQLException {
    // Every new project needs a project_ticket_count record
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM project_ticket_count " +
        "WHERE project_id = ? ");
    pst.setInt(1, projectId);
    pst.execute();
    pst.close();
  }
}

