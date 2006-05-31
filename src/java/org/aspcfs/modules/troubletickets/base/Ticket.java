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
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webdav.utils.ICalendar;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.UserGroupList;
import org.aspcfs.modules.admin.base.UserGroup;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionlist.base.ActionItemLogList;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.products.base.ProductCatalogList;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.mycfs.base.TicketEventList;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.HashMap;

/**
 *  Represents a Ticket
 *
 * @author     chris
 * @created    November 8, 2001
 * @version    $Id: Ticket.java 14021 2006-01-25 15:15:37 -0500 (Wed, 25 Jan
 *      2006) partha@darkhorseventures.com $
 */
public class Ticket extends GenericBean {

  Contact thisContact = new Contact();

  private String errorMessage = "";
  private int id = -1;
  private int orgId = -1;

  private int contractId = -1;
  private String serviceContractNumber = null;
  private double totalHoursRemaining = -1;
  private java.sql.Timestamp contractStartDate = null;
  private java.sql.Timestamp contractEndDate = null;
  private int contractOnsiteResponseModel = -1;

  private int assetId = -1;
  private String assetSerialNumber = null;
  private int assetManufacturerCode = -1;
  private int assetVendorCode = -1;
  private String assetModelVersion = null;
  private String assetLocation = null;
  private int assetOnsiteResponseModel = -1;

  private int contactId = -1;
  private int assignedTo = -1;
  private java.sql.Timestamp assignedDate = null;
  private String problem = "";
  private String location = null;
  private String comment = "";
  private java.sql.Timestamp estimatedResolutionDate = null;
  private String estimatedResolutionDateTimeZone = null;
  private String cause = null;
  private String solution = "";
  private int priorityCode = -1;
  private int levelCode = -1;
  private int departmentCode = -1;
  private int sourceCode = -1;
  private int catCode = 0;
  private int subCat1 = 0;
  private int subCat2 = 0;
  private int subCat3 = 0;
  private int severityCode = -1;
  private java.sql.Timestamp resolutionDate = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp closed = null;
  private int productId = -1;
  private int statusId = -1;
  private int customerProductId = -1;
  private int expectation = -1;
  private String productSku = null;
  private String productName = null;
  private String assignedDateTimeZone = null;
  private String resolutionDateTimeZone = null;
  private java.sql.Timestamp trashedDate = null;
  private int userGroupId = -1;
  private int causeId = -1;
  private int resolutionId = -1;
  private int defectId = -1;
  private int escalationLevel = -1;
  private boolean resolvable = true;
  private int resolvedBy = -1;
  private int resolvedByDeptCode = -1;
  private int stateId = -1;
  private int siteId = -1;

  //Related descriptions
  private String companyName = "";
  private String categoryName = "";
  private String departmentName = "";
  private String resolvedByDeptName = "";
  private String priorityName = "";
  private String severityName = "";
  private String sourceName = "";
  private String projectName = "";
  private String escalationLevelName = "";
  private boolean closeIt = false;
  private boolean companyEnabled = true;
  private int orgSiteId = -1;

  private int ageDays = 0;
  private int ageHours = 0;
  private int campaignId = -1;
  private boolean hasEnabledOwnerAccount = true;
  private int projectId = -1;
  private int projectTicketCount = -1;
  //Resources
  private boolean buildFiles = false;
  private boolean buildTasks = false;
  private boolean buildHistory = false;
  private boolean buildOrgHierarchy = true;

  private TicketLogList history = new TicketLogList();
  private FileItemList files = new FileItemList();
  private TaskList tasks = new TaskList();

  //action list properties
  private int actionId = -1;
  private SystemStatus systemStatus = null;
  //action plan work item
  private int actionPlanId = -1;
  private boolean insertActionPlan = false;
  private String userGroupName = null;
  private ActionPlan plan = null;
  private String companyNameHierarchy = null;


  /**
   *  Constructor for the Ticket object, creates an empty Ticket
   *
   * @since    1.0
   */
  public Ticket() { }


  /**
   *  Constructor for the Ticket object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Ticket(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @param  id                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Ticket(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  id             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Ticket Number");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT t.*, " +
        "o.name AS orgname, " +
        "o.enabled AS orgenabled, " +
        "o.site_id AS orgsiteid, " +
        "ld.description AS dept, " +
        "lrd.description AS resolvedept, " +
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
        "a.manufacturer_code AS assetmanufacturercode, " +
        "a.vendor_code AS assetvendorcode, " +
        "a.model_version AS modelversion, " +
        "a.location AS assetlocation, " +
        "a.onsite_service_model AS assetonsiteservicemodel, " +
        "pc.sku AS productsku , " +
        "pc.product_name AS productname, " +
        "tlp.project_id, " +
        "proj.title as projectname, ug.group_name as usergroupname, " +
        "lu_te.description AS escalationlevelname " +
        "FROM ticket t " +
        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
        "LEFT JOIN lookup_department ld ON (t.department_code = ld.code) " +
        "LEFT JOIN lookup_department lrd ON (t.resolvedby_department_code = lrd.code) " +
        "LEFT JOIN ticket_priority tp ON (t.pri_code = tp.code) " +
        "LEFT JOIN ticket_severity ts ON (t.scode = ts.code) " +
        "LEFT JOIN ticket_category tc ON (t.cat_code = tc.id) " +
        "LEFT JOIN lookup_ticketsource lu_ts ON (t.source_code = lu_ts.code) " +
        "LEFT JOIN service_contract sc ON (t.link_contract_id = sc.contract_id) " +
        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
        "LEFT JOIN product_catalog pc ON (t.product_id = pc.product_id) " +
        "LEFT JOIN ticketlink_project tlp ON (t.ticketid = tlp.ticket_id) " +
        "LEFT JOIN projects proj ON (tlp.project_id = proj.project_id) " +
        "LEFT JOIN user_group ug ON (t.user_group_id = ug.group_id) " +
        "LEFT JOIN lookup_ticket_escalation lu_te ON (t.escalation_level = lu_te.code) " +
        "WHERE t.ticketid = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
    if (this.getContactId() > 0 && checkContactRecord(db, this.getContactId())) {
      thisContact = new Contact(db, this.getContactId());
    } else {
      thisContact = null;
    }
    if (buildHistory) {
      if (this.getSystemStatus() != null) {
        this.buildHistory(db, this.getSystemStatus());
      } else {
        this.buildHistory(db);
      }
    }
    if (buildFiles) {
      this.buildFiles(db);
    }
    if (buildTasks) {
      this.buildTasks(db);
    }
    if (buildOrgHierarchy) {
      companyNameHierarchy = OrganizationList.buildParentNameHierarchy(db, this.getOrgId(), true, new HashMap());
    }
    buildActionPlan(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRelatedInformation(Connection db) throws SQLException {
    if (this.getProductId() != -1) {
      ProductCatalogList list = new ProductCatalogList();
      list.setId(this.getProductId());
      list.buildList(db);
      if (list.size() == 1) {
        ProductCatalog catalogEntry = (ProductCatalog) list.get(0);
        this.setProductName(catalogEntry.getName());
        this.setProductSku(catalogEntry.getSku());
      }
    }
    if (this.getUserGroupId() != -1) {
      UserGroupList groupList = new UserGroupList();
      groupList.setGroupId(this.getUserGroupId());
      groupList.buildList(db);
      if (groupList.size() == 1) {
        UserGroup group = (UserGroup) groupList.get(0);
        this.setUserGroupName(group.getName());
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildActionPlan(Connection db) throws SQLException {
    ActionPlanWorkList list = new ActionPlanWorkList();
    list.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
    list.setLinkItemId(this.getId());
    list.setSiteId(this.getSiteId());
    list.setIncludeAllSites(true);
    list.buildList(db);
    if (list.size() > 0) {
      ActionPlanWork work = null;
      if (list.size() == 1) {
        work = (ActionPlanWork) list.get(0);
      } else {
        work = list.getLatestPlan();
      }
      if (work != null && work.getId() != -1) {
        this.setActionPlanId(work.getActionPlanId());
        plan = new ActionPlan();
        plan.setBuildPhases(true);
        plan.setBuildSteps(true);
        plan.queryRecord(db, work.getActionPlanId());
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildHistory(Connection db) throws SQLException {
    history.setTicketId(this.getId());
    history.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  systemStatus   Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildHistory(Connection db, SystemStatus systemStatus) throws SQLException {
    history.setTicketId(this.getId());
    history.setSystemStatus(systemStatus);
    history.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildFiles(Connection db) throws SQLException {
    files.clear();
    files.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
    files.setLinkItemId(this.getId());
    files.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildTasks(Connection db) throws SQLException {
    tasks.setTicketId(this.getId());
    tasks.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  id             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean checkContactRecord(Connection db, int id) throws SQLException {
    boolean contactFound = false;
    if (id != -1) {
      PreparedStatement pst = db.prepareStatement(
          "SELECT contact_id from contact c " +
          "WHERE c.contact_id = ? ");
      pst.setInt(1, id);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        contactFound = true;
      }
      rs.close();
      pst.close();
    }
    return contactFound;
  }


  /**
   *  Gets the resolvedByDeptName attribute of the Ticket object
   *
   * @return    The resolvedByDeptName value
   */
  public String getResolvedByDeptName() {
    return resolvedByDeptName;
  }


  /**
   *  Sets the resolvedByDeptName attribute of the Ticket object
   *
   * @param  tmp  The new resolvedByDeptName value
   */
  public void setResolvedByDeptName(String tmp) {
    this.resolvedByDeptName = tmp;
  }


  /**
   *  Sets the productId attribute of the Ticket object
   *
   * @param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the Ticket object
   *
   * @param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the systemStatus attribute of the Ticket object
   *
   * @param  tmp  The new systemStatus value
   */
  public void setSystemStatus(SystemStatus tmp) {
    this.systemStatus = tmp;
  }


  /**
   *  Gets the projectName attribute of the Ticket object
   *
   * @return    The projectName value
   */
  public String getProjectName() {
    return projectName;
  }


  /**
   *  Sets the projectName attribute of the Ticket object
   *
   * @param  tmp  The new projectName value
   */
  public void setProjectName(String tmp) {
    this.projectName = tmp;
  }


  /**
   *  Gets the systemStatus attribute of the Ticket object
   *
   * @return    The systemStatus value
   */
  public SystemStatus getSystemStatus() {
    return systemStatus;
  }


  /**
   *  Gets the productId attribute of the Ticket object
   *
   * @return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Sets the closed attribute of the Ticket object
   *
   * @param  closed  The new closed value
   */
  public void setClosed(java.sql.Timestamp closed) {
    this.closed = closed;
  }


  /**
   *  Sets the closed attribute of the Ticket object
   *
   * @param  tmp  The new closed value
   */
  public void setClosed(String tmp) {
    this.closed = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the expectation attribute of the Ticket object
   *
   * @param  tmp  The new expectation value
   */
  public void setExpectation(int tmp) {
    this.expectation = tmp;
  }


  /**
   *  Sets the expectation attribute of the Ticket object
   *
   * @param  tmp  The new expectation value
   */
  public void setExpectation(String tmp) {
    this.expectation = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productSku attribute of the Ticket object
   *
   * @param  tmp  The new productSku value
   */
  public void setProductSku(String tmp) {
    this.productSku = tmp;
  }


  /**
   *  Sets the productName attribute of the Ticket object
   *
   * @param  tmp  The new productName value
   */
  public void setProductName(String tmp) {
    this.productName = tmp;
  }


  /**
   *  Sets the ThisContact attribute of the Ticket object
   *
   * @param  thisContact  The new ThisContact value
   */
  public void setThisContact(Contact thisContact) {
    this.thisContact = thisContact;
  }


  /**
   *  Sets the actionId attribute of the Ticket object
   *
   * @param  actionId  The new actionId value
   */
  public void setActionId(int actionId) {
    this.actionId = actionId;
  }


  /**
   *  Gets the resolvable attribute of the Ticket object
   *
   * @return    The resolvable value
   */
  public boolean getResolvable() {
    return resolvable;
  }


  /**
   *  Sets the resolvable attribute of the Ticket object
   *
   * @param  tmp  The new resolvable value
   */
  public void setResolvable(boolean tmp) {
    this.resolvable = tmp;
  }


  /**
   *  Sets the resolvable attribute of the Ticket object
   *
   * @param  tmp  The new resolvable value
   */
  public void setResolvable(String tmp) {
    this.resolvable = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the resolvedBy attribute of the Ticket object
   *
   * @return    The resolvedBy value
   */
  public int getResolvedBy() {
    return resolvedBy;
  }


  /**
   *  Sets the resolvedBy attribute of the Ticket object
   *
   * @param  tmp  The new resolvedBy value
   */
  public void setResolvedBy(int tmp) {
    this.resolvedBy = tmp;
  }


  /**
   *  Sets the resolvedBy attribute of the Ticket object
   *
   * @param  tmp  The new resolvedBy value
   */
  public void setResolvedBy(String tmp) {
    this.resolvedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the resolvedByDeptCode attribute of the Ticket object
   *
   * @return    The resolvedByDeptCode value
   */
  public int getResolvedByDeptCode() {
    return resolvedByDeptCode;
  }


  /**
   *  Sets the resolvedByDeptCode attribute of the Ticket object
   *
   * @param  tmp  The new resolvedByDeptCode value
   */
  public void setResolvedByDeptCode(int tmp) {
    this.resolvedByDeptCode = tmp;
  }


  /**
   *  Sets the resolvedByDeptCode attribute of the Ticket object
   *
   * @param  tmp  The new resolvedByDeptCode value
   */
  public void setResolvedByDeptCode(String tmp) {
    this.resolvedByDeptCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the actionId attribute of the Ticket object
   *
   * @param  actionId  The new actionId value
   */
  public void setActionId(String actionId) {
    this.actionId = Integer.parseInt(actionId);
  }


  /**
   *  Sets the statusId attribute of the Ticket object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the Ticket object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the statusId attribute of the Ticket object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Sets the assignedDateTimeZone attribute of the Ticket object
   *
   * @param  tmp  The new assignedDateTimeZone value
   */
  public void setAssignedDateTimeZone(String tmp) {
    this.assignedDateTimeZone = tmp;
  }


  /**
   *  Sets the resolutionDateTimeZone attribute of the Ticket object
   *
   * @param  tmp  The new resolutionDateTimeZone value
   */
  public void setResolutionDateTimeZone(String tmp) {
    this.resolutionDateTimeZone = tmp;
  }


  /**
   *  Gets the assignedDateTimeZone attribute of the Ticket object
   *
   * @return    The assignedDateTimeZone value
   */
  public String getAssignedDateTimeZone() {
    return assignedDateTimeZone;
  }


  /**
   *  Gets the resolutionDateTimeZone attribute of the Ticket object
   *
   * @return    The resolutionDateTimeZone value
   */
  public String getResolutionDateTimeZone() {
    return resolutionDateTimeZone;
  }


  /**
   *  Sets the trashedDate attribute of the Ticket object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the Ticket object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the trashedDate attribute of the Ticket object
   *
   * @return    The statusId value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Gets the trashed attribute of the Ticket object
   *
   * @return    The trashed value
   */
  public boolean isTrashed() {
    return (trashedDate != null);
  }


  /**
   *  Gets the actionId attribute of the Ticket object
   *
   * @return    The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   *  Sets the tasks attribute of the Ticket object
   *
   * @param  tasks  The new tasks value
   */
  public void setTasks(TaskList tasks) {
    this.tasks = tasks;
  }


  /**
   *  Gets the tasks attribute of the Ticket object
   *
   * @return    The tasks value
   */
  public TaskList getTasks() {
    return tasks;
  }


  /**
   *  Gets the companyEnabled attribute of the Ticket object
   *
   * @return    The companyEnabled value
   */
  public boolean getCompanyEnabled() {
    return companyEnabled;
  }


  /**
   *  Gets the orgSiteId attribute of the Ticket object
   *
   * @return    The orgSiteId value
   */
  public int getOrgSiteId() {
    return orgSiteId;
  }


  /**
   *  Sets the companyEnabled attribute of the Ticket object
   *
   * @param  companyEnabled  The new companyEnabled value
   */
  public void setCompanyEnabled(boolean companyEnabled) {
    this.companyEnabled = companyEnabled;
  }


  /**
   *  Sets the orgSiteId attribute of the Ticket object
   *
   * @param  tmp  The new orgSiteId value
   */
  public void setOrgSiteId(int tmp) {
    this.orgSiteId = tmp;
  }


  /**
   *  Sets the orgSiteId attribute of the Ticket object
   *
   * @param  tmp  The new orgSiteId value
   */
  public void setOrgSiteId(String tmp) {
    this.orgSiteId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Newticketlogentry attribute of the Ticket object
   *
   * @param  newticketlogentry  The new Newticketlogentry value
   */
  public void setNewticketlogentry(String newticketlogentry) {
    this.comment = newticketlogentry;
  }


  /**
   *  Sets the AssignedTo attribute of the Ticket object
   *
   * @param  assignedTo  The new AssignedTo value
   */
  public void setAssignedTo(int assignedTo) {
    this.assignedTo = assignedTo;
  }


  /**
   *  Sets the assignedDate attribute of the Ticket object
   *
   * @param  tmp  The new assignedDate value
   */
  public void setAssignedDate(java.sql.Timestamp tmp) {
    this.assignedDate = tmp;
  }


  /**
   *  Sets the assignedDate attribute of the Ticket object
   *
   * @param  tmp  The new assignedDate value
   */
  public void setAssignedDate(String tmp) {
    this.assignedDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the SubCat1 attribute of the Ticket object
   *
   * @param  tmp  The new SubCat1 value
   */
  public void setSubCat1(int tmp) {
    this.subCat1 = tmp;
  }


  /**
   *  Sets the SubCat2 attribute of the Ticket object
   *
   * @param  tmp  The new SubCat2 value
   */
  public void setSubCat2(int tmp) {
    this.subCat2 = tmp;
  }


  /**
   *  Sets the SourceName attribute of the Ticket object
   *
   * @param  sourceName  The new SourceName value
   */
  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }


  /**
   *  Sets the escalationLevelName attribute of the Ticket object
   *
   * @param  tmp  The new escalationLevelName value
   */
  public void setEscalationLevelName(String tmp) {
    this.escalationLevelName = tmp;
  }


  /**
   *  Sets the entered attribute of the Ticket object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the Ticket object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Ticket object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Ticket object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the SubCat3 attribute of the Ticket object
   *
   * @param  tmp  The new SubCat3 value
   */
  public void setSubCat3(int tmp) {
    this.subCat3 = tmp;
  }


  /**
   *  Sets the SubCat1 attribute of the Ticket object
   *
   * @param  tmp  The new SubCat1 value
   */
  public void setSubCat1(String tmp) {
    this.subCat1 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the SubCat2 attribute of the Ticket object
   *
   * @param  tmp  The new SubCat2 value
   */
  public void setSubCat2(String tmp) {
    this.subCat2 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the SubCat3 attribute of the Ticket object
   *
   * @param  tmp  The new SubCat3 value
   */
  public void setSubCat3(String tmp) {
    this.subCat3 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the AssignedTo attribute of the Ticket object
   *
   * @param  assignedTo  The new AssignedTo value
   */
  public void setAssignedTo(String assignedTo) {
    this.assignedTo = Integer.parseInt(assignedTo);
  }


  /**
   *  Sets the DepartmentName attribute of the Ticket object
   *
   * @param  departmentName  The new DepartmentName value
   */
  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }


  /**
   *  Sets the CloseIt attribute of the Ticket object
   *
   * @param  closeIt  The new CloseIt value
   */
  public void setCloseIt(boolean closeIt) {
    this.closeIt = closeIt;
  }


  /**
   *  Sets the closeNow attribute of the Ticket object
   *
   * @param  tmp  The new closeNow value
   */
  public void setCloseNow(String tmp) {
    this.closeIt = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the SeverityName attribute of the Ticket object
   *
   * @param  severityName  The new SeverityName value
   */
  public void setSeverityName(String severityName) {
    this.severityName = severityName;
  }


  /**
   *  Sets the ErrorMessage attribute of the Ticket object
   *
   * @param  tmp  The new ErrorMessage value
   */
  public void setErrorMessage(String tmp) {
    this.errorMessage = tmp;
  }


  /**
   *  Sets the campaignId attribute of the Ticket object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Sets the campaignId attribute of the Ticket object
   *
   * @param  tmp  The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the History attribute of the Ticket object
   *
   * @param  history  The new History value
   */
  public void setHistory(TicketLogList history) {
    this.history = history;
  }


  /**
   *  Sets the files attribute of the Ticket object
   *
   * @param  tmp  The new files value
   */
  public void setFiles(FileItemList tmp) {
    this.files = tmp;
  }


  /**
   *  Sets the Id attribute of the Ticket object
   *
   * @param  tmp  The new Id value
   */
  public void setId(int tmp) {
    this.id = tmp;
    history.setTicketId(tmp);
  }


  /**
   *  Sets the Id attribute of the Ticket object
   *
   * @param  tmp  The new Id value
   */
  public void setId(String tmp) {
    this.setId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the CompanyName attribute of the Ticket object
   *
   * @param  companyName  The new CompanyName value
   */
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }


  /**
   *  Sets the OrgId attribute of the Ticket object
   *
   * @param  tmp  The new OrgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the OrgId attribute of the Ticket object
   *
   * @param  tmp  The new OrgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contractId attribute of the Ticket object
   *
   * @param  tmp  The new contractId value
   */
  public void setContractId(int tmp) {
    this.contractId = tmp;
  }


  /**
   *  Sets the contractId attribute of the Ticket object
   *
   * @param  tmp  The new contractId value
   */
  public void setContractId(String tmp) {
    this.contractId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the serviceContractNumber attribute of the Ticket object
   *
   * @param  tmp  The new serviceContractNumber value
   */
  public void setServiceContractNumber(String tmp) {
    this.serviceContractNumber = tmp;
  }


  /**
   *  Sets the totalHoursRemaining attribute of the Ticket object
   *
   * @param  tmp  The new totalHoursRemaining value
   */
  public void setTotalHoursRemaining(double tmp) {
    this.totalHoursRemaining = tmp;
  }


  /**
   *  Sets the totalHoursRemaining attribute of the Ticket object
   *
   * @param  tmp  The new totalHoursRemaining value
   */
  public void setTotalHoursRemaining(String tmp) {
    this.totalHoursRemaining = Double.parseDouble(tmp);
  }


  /**
   *  Sets the contractStartDate attribute of the Ticket object
   *
   * @param  tmp  The new contractStartDate value
   */
  public void setContractStartDate(java.sql.Timestamp tmp) {
    this.contractStartDate = tmp;
  }


  /**
   *  Sets the contractStartDate attribute of the Ticket object
   *
   * @param  tmp  The new contractStartDate value
   */
  public void setContractStartDate(String tmp) {
    this.contractStartDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the contractEndDate attribute of the Ticket object
   *
   * @param  tmp  The new contractEndDate value
   */
  public void setContractEndDate(java.sql.Timestamp tmp) {
    this.contractEndDate = tmp;
  }


  /**
   *  Sets the contractEndDate attribute of the Ticket object
   *
   * @param  tmp  The new contractEndDate value
   */
  public void setContractEndDate(String tmp) {
    this.contractEndDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the contractOnsiteResponseModel attribute of the Ticket object
   *
   * @param  tmp  The new contractOnsiteResponseModel value
   */
  public void setContractOnsiteResponseModel(int tmp) {
    this.contractOnsiteResponseModel = tmp;
  }


  /**
   *  Sets the contractOnsiteResponseModel attribute of the Ticket object
   *
   * @param  tmp  The new contractOnsiteResponseModel value
   */
  public void setContractOnsiteResponseModel(String tmp) {
    this.contractOnsiteResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the assetId attribute of the Ticket object
   *
   * @param  tmp  The new assetId value
   */
  public void setAssetId(int tmp) {
    this.assetId = tmp;
  }


  /**
   *  Sets the assetId attribute of the Ticket object
   *
   * @param  tmp  The new assetId value
   */
  public void setAssetId(String tmp) {
    this.assetId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the assetSerialNumber attribute of the Ticket object
   *
   * @param  tmp  The new assetSerialNumber value
   */
  public void setAssetSerialNumber(String tmp) {
    this.assetSerialNumber = tmp;
  }


  /**
   *  Sets the assetManufacturerCode attribute of the Ticket object
   *
   * @param  tmp  The new assetManufacturerCode value
   */
  public void setAssetManufacturerCode(int tmp) {
    this.assetManufacturerCode = tmp;
  }


  /**
   *  Sets the assetManufacturerCode attribute of the Ticket object
   *
   * @param  tmp  The new assetManufacturerCode value
   */
  public void setAssetManufacturerCode(String tmp) {
    this.assetManufacturerCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the assetVendorCode attribute of the Ticket object
   *
   * @param  tmp  The new assetVendorCode value
   */
  public void setAssetVendorCode(int tmp) {
    this.assetVendorCode = tmp;
  }


  /**
   *  Sets the assetVendorCode attribute of the Ticket object
   *
   * @param  tmp  The new assetVendorCode value
   */
  public void setAssetVendorCode(String tmp) {
    this.assetVendorCode = Integer.parseInt(tmp);
  }



  /**
   *  Sets the modelVersion attribute of the Ticket object
   *
   * @param  tmp  The new modelVersion value
   */
  public void setAssetModelVersion(String tmp) {
    this.assetModelVersion = tmp;
  }


  /**
   *  Sets the location attribute of the Ticket object
   *
   * @param  tmp  The new location value
   */
  public void setAssetLocation(String tmp) {
    this.assetLocation = tmp;
  }


  /**
   *  Sets the assetOnsiteResponseModel attribute of the Ticket object
   *
   * @param  tmp  The new assetOnsiteResponseModel value
   */
  public void setAssetOnsiteResponseModel(int tmp) {
    this.assetOnsiteResponseModel = tmp;
  }


  /**
   *  Sets the assetOnsiteResponseModel attribute of the Ticket object
   *
   * @param  tmp  The new assetOnsiteResponseModel value
   */
  public void setAssetOnsiteResponseModel(String tmp) {
    this.assetOnsiteResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildFiles attribute of the Ticket object
   *
   * @param  buildFiles  The new buildFiles value
   */
  public void setBuildFiles(boolean buildFiles) {
    this.buildFiles = buildFiles;
  }


  /**
   *  Sets the buildTasks attribute of the Ticket object
   *
   * @param  buildTasks  The new buildTasks value
   */
  public void setBuildTasks(boolean buildTasks) {
    this.buildTasks = buildTasks;
  }


  /**
   *  Sets the buildHistory attribute of the Ticket object
   *
   * @param  buildHistory  The new buildHistory value
   */
  public void setBuildHistory(boolean buildHistory) {
    this.buildHistory = buildHistory;
  }


  /**
   *  Sets the customerProductId attribute of the Ticket object
   *
   * @param  tmp  The new customerProductId value
   */
  public void setCustomerProductId(int tmp) {
    this.customerProductId = tmp;
  }


  /**
   *  Sets the customerProductId attribute of the Ticket object
   *
   * @param  tmp  The new customerProductId value
   */
  public void setCustomerProductId(String tmp) {
    this.customerProductId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the customerProductId attribute of the Ticket object
   *
   * @return    The customerProductId value
   */
  public int getCustomerProductId() {
    return customerProductId;
  }


  /**
   *  Gets the buildFiles attribute of the Ticket object
   *
   * @return    The buildFiles value
   */
  public boolean getBuildFiles() {
    return buildFiles;
  }


  /**
   *  Gets the buildTasks attribute of the Ticket object
   *
   * @return    The buildTasks value
   */
  public boolean getBuildTasks() {
    return buildTasks;
  }


  /**
   *  Gets the buildHistory attribute of the Ticket object
   *
   * @return    The buildHistory value
   */
  public boolean getBuildHistory() {
    return buildHistory;
  }


  /**
   *  Gets the hasEnabledOwnerAccount attribute of the Ticket object
   *
   * @return    The hasEnabledOwnerAccount value
   */
  public boolean getHasEnabledOwnerAccount() {
    return hasEnabledOwnerAccount;
  }


  /**
   *  Sets the hasEnabledOwnerAccount attribute of the Ticket object
   *
   * @param  hasEnabledOwnerAccount  The new hasEnabledOwnerAccount value
   */
  public void setHasEnabledOwnerAccount(boolean hasEnabledOwnerAccount) {
    this.hasEnabledOwnerAccount = hasEnabledOwnerAccount;
  }


  /**
   *  Sets the projectId attribute of the Ticket object
   *
   * @param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the projectId attribute of the Ticket object
   *
   * @param  tmp  The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the projectId attribute of the Ticket object
   *
   * @return    The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   *  Sets the projectTicketCount attribute of the Ticket object
   *
   * @param  tmp  The new projectTicketCount value
   */
  public void setProjectTicketCount(int tmp) {
    this.projectTicketCount = tmp;
  }


  /**
   *  Sets the projectTicketCount attribute of the Ticket object
   *
   * @param  tmp  The new projectTicketCount value
   */
  public void setProjectTicketCount(String tmp) {
    this.projectTicketCount = Integer.parseInt(tmp);
  }


  /**
   *  Gets the projectTicketCount attribute of the Ticket object
   *
   * @return    The projectTicketCount value
   */
  public int getProjectTicketCount() {
    return projectTicketCount;
  }


  /**
   *  Gets the paddedProjectTicketCount attribute of the Ticket object
   *
   * @return    The paddedProjectTicketCount value
   */
  public String getPaddedProjectTicketCount() {
    String padded = (String.valueOf(this.getProjectTicketCount()));
    while (padded.length() < 6) {
      padded = "0" + padded;
    }
    return padded;
  }


  /**
   *  Gets the paddedTicketId attribute of the Ticket object
   *
   * @return    The paddedTicketId value
   */
  public String getPaddedTicketId() {
    if (projectId == -1) {
      return getPaddedId();
    }
    return getPaddedProjectTicketCount();
  }


  /**
   *  Sets the ContactId attribute of the Ticket object
   *
   * @param  tmp  The new ContactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the PriorityName attribute of the Ticket object
   *
   * @param  priorityName  The new PriorityName value
   */
  public void setPriorityName(String priorityName) {
    this.priorityName = priorityName;
  }


  /**
   *  Sets the ContactId attribute of the Ticket object
   *
   * @param  tmp  The new ContactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the AgeOf attribute of the Ticket object
   *
   * @param  ageOf  The new AgeOf value
   */
  public void setAgeDays(int ageOf) {
    this.ageDays = ageOf;
  }


  /**
   *  Sets the CategoryName attribute of the Ticket object
   *
   * @param  categoryName  The new CategoryName value
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }


  /**
   *  Sets the Problem attribute of the Ticket object
   *
   * @param  tmp  The new Problem value
   */
  public void setProblem(String tmp) {
    this.problem = tmp;
  }


  /**
   *  Sets the location attribute of the Ticket object
   *
   * @param  tmp  The new location value
   */
  public void setLocation(String tmp) {
    this.location = tmp;
  }


  /**
   *  Sets the Comment attribute of the Ticket object
   *
   * @param  tmp  The new Comment value
   */
  public void setComment(String tmp) {
    this.comment = tmp;
  }


  /**
   *  Sets the estimatedResolutionDate attribute of the Ticket object
   *
   * @param  tmp  The new estimatedResolutionDate value
   */
  public void setEstimatedResolutionDate(java.sql.Timestamp tmp) {
    this.estimatedResolutionDate = tmp;
  }


  /**
   *  Sets the estimatedResolutionDate attribute of the Ticket object
   *
   * @param  tmp  The new estimatedResolutionDate value
   */
  public void setEstimatedResolutionDate(String tmp) {
    this.estimatedResolutionDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the estimatedResolutionDateTimeZone attribute of the Ticket object
   *
   * @param  tmp  The new estimatedResolutionDateTimeZone value
   */
  public void setEstimatedResolutionDateTimeZone(String tmp) {
    this.estimatedResolutionDateTimeZone = tmp;
  }


  /**
   *  Sets the cause attribute of the Ticket object
   *
   * @param  tmp  The new cause value
   */
  public void setCause(String tmp) {
    this.cause = tmp;
  }


  /**
   *  Sets the Solution attribute of the Ticket object
   *
   * @param  tmp  The new Solution value
   */
  public void setSolution(String tmp) {
    this.solution = tmp;
  }


  /**
   *  Sets the PriorityCode attribute of the Ticket object
   *
   * @param  tmp  The new PriorityCode value
   */
  public void setPriorityCode(int tmp) {
    this.priorityCode = tmp;
  }


  /**
   *  Sets the PriorityCode attribute of the Ticket object
   *
   * @param  tmp  The new PriorityCode value
   */
  public void setPriorityCode(String tmp) {
    this.priorityCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the LevelCode attribute of the Ticket object
   *
   * @param  tmp  The new LevelCode value
   */
  public void setLevelCode(int tmp) {
    this.levelCode = tmp;
  }


  /**
   *  Sets the levelCode attribute of the Ticket object
   *
   * @param  tmp  The new levelCode value
   */
  public void setLevelCode(String tmp) {
    this.levelCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the DepartmentCode attribute of the Ticket object
   *
   * @param  tmp  The new DepartmentCode value
   */
  public void setDepartmentCode(int tmp) {
    this.departmentCode = tmp;
  }


  /**
   *  Sets the DepartmentCode attribute of the Ticket object
   *
   * @param  tmp  The new DepartmentCode value
   */
  public void setDepartmentCode(String tmp) {
    this.departmentCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the SourceCode attribute of the Ticket object
   *
   * @param  tmp  The new SourceCode value
   */
  public void setSourceCode(int tmp) {
    this.sourceCode = tmp;
  }


  /**
   *  Sets the SourceCode attribute of the Ticket object
   *
   * @param  tmp  The new SourceCode value
   */
  public void setSourceCode(String tmp) {
    this.sourceCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the CatCode attribute of the Ticket object
   *
   * @param  tmp  The new CatCode value
   */
  public void setCatCode(int tmp) {
    this.catCode = tmp;
  }


  /**
   *  Sets the CatCode attribute of the Ticket object
   *
   * @param  tmp  The new CatCode value
   */
  public void setCatCode(String tmp) {
    this.catCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the SeverityCode attribute of the Ticket object
   *
   * @param  tmp  The new SeverityCode value
   */
  public void setSeverityCode(int tmp) {
    this.severityCode = tmp;
  }


  /**
   *  Sets the SeverityCode attribute of the Ticket object
   *
   * @param  tmp  The new SeverityCode value
   */
  public void setSeverityCode(String tmp) {
    this.severityCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the resolutionDate attribute of the Ticket object
   *
   * @param  tmp  The new resolutionDate value
   */
  public void setResolutionDate(java.sql.Timestamp tmp) {
    this.resolutionDate = tmp;
  }


  /**
   *  Sets the resolutionDate attribute of the Ticket object
   *
   * @param  tmp  The new resolutionDate value
   */
  public void setResolutionDate(String tmp) {
    this.resolutionDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the escalationLevel attribute of the Ticket object
   *
   * @param  tmp  The new escalationLevel value
   */
  public void setEscalationLevel(int tmp) {
    this.escalationLevel = tmp;
  }


  /**
   *  Sets the escalationLevel attribute of the Ticket object
   *
   * @param  tmp  The new escalationLevel value
   */
  public void setEscalationLevel(String tmp) {
    this.escalationLevel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the EnteredBy attribute of the Ticket object
   *
   * @param  tmp  The new EnteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the Ticket object
   *
   * @param  tmp  The new EnteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ModifiedBy attribute of the Ticket object
   *
   * @param  tmp  The new ModifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the ModifiedBy attribute of the Ticket object
   *
   * @param  tmp  The new ModifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the RequestItems attribute of the Ticket object
   *
   * @param  request     The new RequestItems value
   * @throws  Exception  Description of the Exception
   */
  public void setRequestItems(HttpServletRequest request) throws Exception {
    history = new TicketLogList(request, this.getModifiedBy());
  }


  /**
   *  Gets the closed attribute of the Ticket object
   *
   * @return    The closed value
   */
  public java.sql.Timestamp getClosed() {
    return closed;
  }


  /**
   *  Gets the closedString attribute of the Ticket object
   *
   * @return    The closedString value
   */
  public String getClosedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          closed);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the closed attribute of the Ticket object
   *
   * @return    The closed value
   */
  public boolean isClosed() {
    return closed != null;
  }


  /**
   *  Gets the expectation attribute of the Ticket object
   *
   * @return    The expectation value
   */
  public int getExpectation() {
    return expectation;
  }


  /**
   *  Gets the productSku attribute of the Ticket object
   *
   * @return    The productSku value
   */
  public String getProductSku() {
    return productSku;
  }


  /**
   *  Gets the productName attribute of the Ticket object
   *
   * @return    The productName value
   */
  public String getProductName() {
    return productName;
  }


  /**
   *  Gets the paddedId attribute of the Ticket object
   *
   * @return    The paddedId value
   */
  public String getPaddedId() {
    String padded = (String.valueOf(this.getId()));
    while (padded.length() < 6) {
      padded = "0" + padded;
    }
    return padded;
  }


  /**
   *  Gets the entered attribute of the Ticket object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the Ticket object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedString attribute of the Ticket object
   *
   * @return    The modifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the enteredString attribute of the Ticket object
   *
   * @return    The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the enteredString attribute of the Ticket object
   *
   * @param  dateStyle  Description of the Parameter
   * @param  timeStyle  Description of the Parameter
   * @return            The enteredString value
   */
  public String getEnteredString(int dateStyle, int timeStyle) {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(dateStyle, timeStyle).format(
          entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the modifiedDateTimeString attribute of the Ticket object
   *
   * @return    The modifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the ThisContact attribute of the Ticket object
   *
   * @return    The ThisContact value
   */
  public Contact getThisContact() {
    return thisContact;
  }


  /**
   *  Gets the SourceName attribute of the Ticket object
   *
   * @return    The SourceName value
   */
  public String getSourceName() {
    return sourceName;
  }


  /**
   *  Gets the escalationLevelName attribute of the Ticket object
   *
   * @return    The escalationLevelName value
   */
  public String getEscalationLevelName() {
    return escalationLevelName;
  }


  /**
   *  Gets the SubCat1 attribute of the Ticket object
   *
   * @return    The SubCat1 value
   */
  public int getSubCat1() {
    return subCat1;
  }


  /**
   *  Gets the SubCat2 attribute of the Ticket object
   *
   * @return    The SubCat2 value
   */
  public int getSubCat2() {
    return subCat2;
  }


  /**
   *  Gets the SubCat3 attribute of the Ticket object
   *
   * @return    The SubCat3 value
   */
  public int getSubCat3() {
    return subCat3;
  }


  /**
   *  Gets the Newticketlogentry attribute of the Ticket object
   *
   * @return    The Newticketlogentry value
   */
  public String getNewticketlogentry() {
    return comment;
  }


  /**
   *  Gets the AssignedTo attribute of the Ticket object
   *
   * @return    The AssignedTo value
   */
  public int getAssignedTo() {
    return assignedTo;
  }


  /**
   *  Gets the assigned attribute of the Ticket object
   *
   * @return    The assigned value
   */
  public boolean isAssigned() {
    return (assignedTo > 0);
  }


  /**
   *  Gets the assignedDate attribute of the Ticket object
   *
   * @return    The assignedDate value
   */
  public java.sql.Timestamp getAssignedDate() {
    return assignedDate;
  }


  /**
   *  Gets the CloseIt attribute of the Ticket object
   *
   * @return    The CloseIt value
   */
  public boolean getCloseIt() {
    return closeIt;
  }


  /**
   *  Gets the SeverityName attribute of the Ticket object
   *
   * @return    The SeverityName value
   */
  public String getSeverityName() {
    return severityName;
  }


  /**
   *  Gets the PriorityName attribute of the Ticket object
   *
   * @return    The PriorityName value
   */
  public String getPriorityName() {
    return priorityName;
  }


  /**
   *  Gets the DepartmentName attribute of the Ticket object
   *
   * @return    The DepartmentName value
   */
  public String getDepartmentName() {
    return departmentName;
  }


  /**
   *  Gets the campaignId attribute of the Ticket object
   *
   * @return    The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   *  Gets the History attribute of the Ticket object
   *
   * @return    The History value
   */
  public TicketLogList getHistory() {
    return history;
  }


  /**
   *  Gets the files attribute of the Ticket object
   *
   * @return    The files value
   */
  public FileItemList getFiles() {
    return files;
  }


  /**
   *  Gets the AgeOf attribute of the Ticket object
   *
   * @return    The AgeOf value
   */
  public String getAgeOf() {
    return ageDays + "d " + ageHours + "h";
  }


  /**
   *  Gets the CategoryName attribute of the Ticket object
   *
   * @return    The CategoryName value
   */
  public String getCategoryName() {
    return categoryName;
  }


  /**
   *  Gets the CompanyName attribute of the Ticket object
   *
   * @return    The CompanyName value
   */
  public String getCompanyName() {
    return companyName;
  }


  /**
   *  Gets the ErrorMessage attribute of the Ticket object
   *
   * @return    The ErrorMessage value
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   *  Gets the Id attribute of the Ticket object
   *
   * @return    The Id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the OrgId attribute of the Ticket object
   *
   * @return    The OrgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the contractId attribute of the Ticket object
   *
   * @return    The contractId value
   */
  public int getContractId() {
    return contractId;
  }


  /**
   *  Gets the serviceContractNumber attribute of the Ticket object
   *
   * @return    The serviceContractNumber value
   */
  public String getServiceContractNumber() {
    return serviceContractNumber;
  }


  /**
   *  Gets the totalHoursRemaining attribute of the Ticket object
   *
   * @return    The totalHoursRemaining value
   */
  public double getTotalHoursRemaining() {
    return round(totalHoursRemaining, 2);
  }


  /**
   *  Gets the contractStartDate attribute of the Ticket object
   *
   * @return    The contractStartDate value
   */
  public java.sql.Timestamp getContractStartDate() {
    return contractStartDate;
  }


  /**
   *  Gets the contractEndDate attribute of the Ticket object
   *
   * @return    The contractEndDate value
   */
  public java.sql.Timestamp getContractEndDate() {
    return contractEndDate;
  }


  /**
   *  Gets the contractOnsiteResponseModel attribute of the Ticket object
   *
   * @return    The contractOnsiteResponseModel value
   */
  public int getContractOnsiteResponseModel() {
    return contractOnsiteResponseModel;
  }


  /**
   *  Gets the assetId attribute of the Ticket object
   *
   * @return    The assetId value
   */
  public int getAssetId() {
    return assetId;
  }


  /**
   *  Gets the assetSerialNumber attribute of the Ticket object
   *
   * @return    The assetSerialNumber value
   */
  public String getAssetSerialNumber() {
    return assetSerialNumber;
  }


  /**
   *  Gets the assetManufacturerCode attribute of the Ticket object
   *
   * @return    The assetManufacturerCode value
   */
  public int getAssetManufacturerCode() {
    return assetManufacturerCode;
  }


  /**
   *  Gets the assetVendorCode attribute of the Ticket object
   *
   * @return    The assetVendorCode value
   */
  public int getAssetVendorCode() {
    return assetVendorCode;
  }


  /**
   *  Gets the modelVersion attribute of the Ticket object
   *
   * @return    The modelVersion value
   */
  public String getAssetModelVersion() {
    return assetModelVersion;
  }


  /**
   *  Gets the location attribute of the Ticket object
   *
   * @return    The location value
   */
  public String getAssetLocation() {
    return assetLocation;
  }


  /**
   *  Gets the assetOnsiteResponseModel attribute of the Ticket object
   *
   * @return    The assetOnsiteResponseModel value
   */
  public int getAssetOnsiteResponseModel() {
    return assetOnsiteResponseModel;
  }


  /**
   *  Gets the ContactId attribute of the Ticket object
   *
   * @return    The ContactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the Problem attribute of the Ticket object
   *
   * @return    The Problem value
   */
  public String getProblem() {
    return problem;
  }


  /**
   *  Gets the location attribute of the Ticket object
   *
   * @return    The location value
   */
  public String getLocation() {
    return location;
  }


  /**
   *  Gets the problemHeader attribute of the Ticket object
   *
   * @return    The problemHeader value
   */
  public String getProblemHeader() {
    if (problem.trim().length() > 100) {
      return (problem.substring(0, 100) + "...");
    } else {
      return getProblem();
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void checkEnabledOwnerAccount(Connection db) throws SQLException {
    if (this.getAssignedTo() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM \"access\" " +
        "WHERE user_id = ? AND enabled = ? ");
    pst.setInt(1, this.getAssignedTo());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setHasEnabledOwnerAccount(true);
    } else {
      this.setHasEnabledOwnerAccount(false);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Gets the Comment attribute of the Ticket object
   *
   * @return    The Comment value
   */
  public String getComment() {
    return comment;
  }


  /**
   *  Gets the estimatedResolutionDate attribute of the Ticket object
   *
   * @return    The estimatedResolutionDate value
   */
  public java.sql.Timestamp getEstimatedResolutionDate() {
    return estimatedResolutionDate;
  }


  /**
   *  Gets the estimatedResolutionDateTimeZone attribute of the Ticket object
   *
   * @return    The estimatedResolutionDateTimeZone value
   */
  public String getEstimatedResolutionDateTimeZone() {
    return estimatedResolutionDateTimeZone;
  }


  /**
   *  Gets the cause attribute of the Ticket object
   *
   * @return    The cause value
   */
  public String getCause() {
    return cause;
  }


  /**
   *  Gets the Solution attribute of the Ticket object
   *
   * @return    The Solution value
   */
  public String getSolution() {
    return solution;
  }


  /**
   *  Gets the PriorityCode attribute of the Ticket object
   *
   * @return    The PriorityCode value
   */
  public int getPriorityCode() {
    return priorityCode;
  }


  /**
   *  Gets the LevelCode attribute of the Ticket object
   *
   * @return    The LevelCode value
   */
  public int getLevelCode() {
    return levelCode;
  }


  /**
   *  Gets the DepartmentCode attribute of the Ticket object
   *
   * @return    The DepartmentCode value
   */
  public int getDepartmentCode() {
    return departmentCode;
  }


  /**
   *  Gets the SourceCode attribute of the Ticket object
   *
   * @return    The SourceCode value
   */
  public int getSourceCode() {
    return sourceCode;
  }


  /**
   *  Gets the CatCode attribute of the Ticket object
   *
   * @return    The CatCode value
   */
  public int getCatCode() {
    return catCode;
  }


  /**
   *  Gets the SeverityCode attribute of the Ticket object
   *
   * @return    The SeverityCode value
   */
  public int getSeverityCode() {
    return severityCode;
  }


  /**
   *  Gets the resolutionDate attribute of the Ticket object
   *
   * @return    The resolutionDate value
   */
  public java.sql.Timestamp getResolutionDate() {
    return resolutionDate;
  }


  /**
   *  Gets the EnteredBy attribute of the Ticket object
   *
   * @return    The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the Ticket object
   *
   * @return    The ModifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the actionPlanId attribute of the Ticket object
   *
   * @return    The actionPlanId value
   */
  public int getActionPlanId() {
    return actionPlanId;
  }


  /**
   *  Sets the actionPlanId attribute of the Ticket object
   *
   * @param  tmp  The new actionPlanId value
   */
  public void setActionPlanId(int tmp) {
    this.actionPlanId = tmp;
  }


  /**
   *  Sets the actionPlanId attribute of the Ticket object
   *
   * @param  tmp  The new actionPlanId value
   */
  public void setActionPlanId(String tmp) {
    this.actionPlanId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the insertActionPlan attribute of the Ticket object
   *
   * @return    The insertActionPlan value
   */
  public boolean getInsertActionPlan() {
    return insertActionPlan;
  }


  /**
   *  Sets the insertActionPlan attribute of the Ticket object
   *
   * @param  tmp  The new insertActionPlan value
   */
  public void setInsertActionPlan(boolean tmp) {
    this.insertActionPlan = tmp;
  }


  /**
   *  Sets the insertActionPlan attribute of the Ticket object
   *
   * @param  tmp  The new insertActionPlan value
   */
  public void setInsertActionPlan(String tmp) {
    this.insertActionPlan = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the userGroupId attribute of the Ticket object
   *
   * @return    The userGroupId value
   */
  public int getUserGroupId() {
    return userGroupId;
  }


  /**
   *  Sets the userGroupId attribute of the Ticket object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(int tmp) {
    this.userGroupId = tmp;
  }


  /**
   *  Sets the userGroupId attribute of the Ticket object
   *
   * @param  tmp  The new userGroupId value
   */
  public void setUserGroupId(String tmp) {
    this.userGroupId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the userGroupName attribute of the Ticket object
   *
   * @return    The userGroupName value
   */
  public String getUserGroupName() {
    return userGroupName;
  }


  /**
   *  Sets the userGroupName attribute of the Ticket object
   *
   * @param  tmp  The new userGroupName value
   */
  public void setUserGroupName(String tmp) {
    this.userGroupName = tmp;
  }


  /**
   *  Gets the causeId attribute of the Ticket object
   *
   * @return    The causeId value
   */
  public int getCauseId() {
    return causeId;
  }


  /**
   *  Sets the causeId attribute of the Ticket object
   *
   * @param  tmp  The new causeId value
   */
  public void setCauseId(int tmp) {
    this.causeId = tmp;
  }


  /**
   *  Sets the causeId attribute of the Ticket object
   *
   * @param  tmp  The new causeId value
   */
  public void setCauseId(String tmp) {
    this.causeId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the resolutionId attribute of the Ticket object
   *
   * @return    The resolutionId value
   */
  public int getResolutionId() {
    return resolutionId;
  }


  /**
   *  Sets the resolutionId attribute of the Ticket object
   *
   * @param  tmp  The new resolutionId value
   */
  public void setResolutionId(int tmp) {
    this.resolutionId = tmp;
  }


  /**
   *  Sets the resolutionId attribute of the Ticket object
   *
   * @param  tmp  The new resolutionId value
   */
  public void setResolutionId(String tmp) {
    this.resolutionId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the defectId attribute of the Ticket object
   *
   * @return    The defectId value
   */
  public int getDefectId() {
    return defectId;
  }


  /**
   *  Sets the defectId attribute of the Ticket object
   *
   * @param  tmp  The new defectId value
   */
  public void setDefectId(int tmp) {
    this.defectId = tmp;
  }


  /**
   *  Sets the defectId attribute of the Ticket object
   *
   * @param  tmp  The new defectId value
   */
  public void setDefectId(String tmp) {
    this.defectId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the escalationLevel attribute of the Ticket object
   *
   * @return    The escalationLevel value
   */
  public int getEscalationLevel() {
    return escalationLevel;
  }


  /**
   *  Gets the EnteredBy attribute of the Ticket object
   *
   * @return    The plan value
   */
  public ActionPlan getPlan() {
    return plan;
  }


  /**
   *  Sets the plan attribute of the Ticket object
   *
   * @param  tmp  The new plan value
   */
  public void setPlan(ActionPlan tmp) {
    this.plan = tmp;
  }


  /**
   *  Gets the buildOrgHierarchy attribute of the Ticket object
   *
   * @return    The buildOrgHierarchy value
   */
  public boolean getBuildOrgHierarchy() {
    return buildOrgHierarchy;
  }


  /**
   *  Sets the buildOrgHierarchy attribute of the Ticket object
   *
   * @param  tmp  The new buildOrgHierarchy value
   */
  public void setBuildOrgHierarchy(boolean tmp) {
    this.buildOrgHierarchy = tmp;
  }


  /**
   *  Sets the buildOrgHierarchy attribute of the Ticket object
   *
   * @param  tmp  The new buildOrgHierarchy value
   */
  public void setBuildOrgHierarchy(String tmp) {
    this.buildOrgHierarchy = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the companyNameHierarchy attribute of the Ticket object
   *
   * @return    The companyNameHierarchy value
   */
  public String getCompanyNameHierarchy() {
    return companyNameHierarchy;
  }


  /**
   *  Sets the companyNameHierarchy attribute of the Ticket object
   *
   * @param  tmp  The new companyNameHierarchy value
   */
  public void setCompanyNameHierarchy(String tmp) {
    this.companyNameHierarchy = tmp;
  }


  /**
   *  Gets the stateId attribute of the Ticket object
   *
   * @return    The stateId value
   */
  public int getStateId() {
    return stateId;
  }


  /**
   *  Sets the stateId attribute of the Ticket object
   *
   * @param  tmp  The new stateId value
   */
  public void setStateId(int tmp) {
    this.stateId = tmp;
  }


  /**
   *  Sets the stateId attribute of the Ticket object
   *
   * @param  tmp  The new stateId value
   */
  public void setStateId(String tmp) {
    this.stateId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the siteId attribute of the Ticket object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the Ticket object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the siteId attribute of the Ticket object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void buildContactInformation(Connection db) throws SQLException {
    if (contactId > -1) {
      thisContact = new Contact(db, contactId);
    }
  }


  /**
   *  Inserts this ticket into the database, and populates this Id. Inserts
   *  required fields, then calls update to finish record entry
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      if (projectId > -1 && projectTicketCount == -1) {
        updateProjectTicketCount(db, projectId);
      }
      id = DatabaseUtils.getNextSeq(db, "ticket_ticketid_seq");
      sql.append(
          "INSERT INTO ticket (contact_id, problem, pri_code, " +
          "department_code, cat_code, scode, org_id, link_contract_id, " +
          "link_asset_id, expectation, product_id, customer_product_id, " +
          "key_count, status_id, trashed_date, user_group_id, cause_id, " +
          "resolution_id, defect_id, escalation_level, resolvable, " +
          "resolvedby, resolvedby_department_code, state_id, site_id, ");
      if (id > -1) {
        sql.append("ticketid, ");
      }
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      sql.append("?, ?, ?, ");
      if (id > -1) {
        sql.append("?,");
      }
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, this.getContactId());
      pst.setString(++i, this.getProblem());
      if (this.getPriorityCode() > 0) {
        pst.setInt(++i, this.getPriorityCode());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (this.getDepartmentCode() > 0) {
        pst.setInt(++i, this.getDepartmentCode());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (this.getCatCode() > 0) {
        pst.setInt(++i, this.getCatCode());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (this.getSeverityCode() > 0) {
        pst.setInt(++i, this.getSeverityCode());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      DatabaseUtils.setInt(pst, ++i, orgId);
      DatabaseUtils.setInt(pst, ++i, contractId);
      DatabaseUtils.setInt(pst, ++i, assetId);
      DatabaseUtils.setInt(pst, ++i, expectation);
      DatabaseUtils.setInt(pst, ++i, productId);
      DatabaseUtils.setInt(pst, ++i, customerProductId);
      DatabaseUtils.setInt(pst, ++i, projectTicketCount);
      DatabaseUtils.setInt(pst, ++i, statusId);
      DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
      DatabaseUtils.setInt(pst, ++i, userGroupId);
      DatabaseUtils.setInt(pst, ++i, causeId);
      DatabaseUtils.setInt(pst, ++i, resolutionId);
      DatabaseUtils.setInt(pst, ++i, defectId);
      DatabaseUtils.setInt(pst, ++i, escalationLevel);
      pst.setBoolean(++i, resolvable);
      if (resolvedBy > 0) {
        pst.setInt(++i, resolvedBy);
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (this.getResolvedByDeptCode() > 0) {
        pst.setInt(++i, this.getResolvedByDeptCode());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      DatabaseUtils.setInt(pst, ++i, this.getStateId());
      DatabaseUtils.setInt(pst, ++i, this.getSiteId());
      if (id > -1) {
        pst.setInt(++i, id);
      }
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
      id = DatabaseUtils.getCurrVal(db, "ticket_ticketid_seq", id);
      //Update the rest of the fields
      this.update(db, true);
      if (this.getEntered() == null) {
        this.updateEntry(db);
      }
      if (actionId > 0) {
        updateLog(db);
      }
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
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
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
      thisLog.setType(Constants.TICKET_OBJECT);
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
   *  Update this ticket in the database
   *
   * @param  db             Description of Parameter
   * @param  override       Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE ticket " +
        "SET link_contract_id = ?, link_asset_id = ?, department_code = ?, " +
        "pri_code = ?, scode = ?, " +
        "cat_code = ?, assigned_to = ?, " +
        "subcat_code1 = ?, subcat_code2 = ?, subcat_code3 = ?, " +
        "source_code = ?, contact_id = ?, problem = ?, " +
        "status_id = ?, trashed_date = ?, site_id = ? , ");
    if (!override) {
      sql.append(
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
    }
    if (this.getCloseIt()) {
      sql.append("closed = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    } else {
      if (closed != null) {
        sql.append("closed = ?, ");
      }
    }
    if (orgId != -1) {
      sql.append(" org_id = ?, ");
    }
    sql.append(
        "solution = ?, location = ?, assigned_date = ?, assigned_date_timezone = ?, " +
        "est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, " +
        "cause = ?, expectation = ?, product_id = ?, customer_product_id = ?, " +
        "user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, " +
        "escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ? " +
        "WHERE ticketid = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getContractId());
    DatabaseUtils.setInt(pst, ++i, this.getAssetId());
    if (this.getDepartmentCode() > 0) {
      pst.setInt(++i, this.getDepartmentCode());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getPriorityCode() > 0) {
      pst.setInt(++i, this.getPriorityCode());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getSeverityCode() > 0) {
      pst.setInt(++i, this.getSeverityCode());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getCatCode() > 0) {
      pst.setInt(++i, this.getCatCode());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (assignedTo > 0) {
      pst.setInt(++i, assignedTo);
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getSubCat1() > 0) {
      pst.setInt(++i, this.getSubCat1());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getSubCat2() > 0) {
      pst.setInt(++i, this.getSubCat2());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getSubCat3() > 0) {
      pst.setInt(++i, this.getSubCat3());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getSourceCode() > 0) {
      pst.setInt(++i, this.getSourceCode());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    DatabaseUtils.setInt(pst, ++i, this.getContactId());
    pst.setString(++i, this.getProblem());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
    DatabaseUtils.setInt(pst, ++i, this.getSiteId());
    if (!override) {
      pst.setInt(++i, this.getModifiedBy());
    }
    if (!this.getCloseIt() && closed != null) {
      pst.setTimestamp(++i, closed);
    }
    if (orgId != -1) {
      DatabaseUtils.setInt(pst, ++i, orgId);
    }
    pst.setString(++i, this.getSolution());
    pst.setString(++i, location);
    DatabaseUtils.setTimestamp(pst, ++i, assignedDate);
    pst.setString(++i, this.assignedDateTimeZone);
    DatabaseUtils.setTimestamp(pst, ++i, estimatedResolutionDate);
    pst.setString(++i, estimatedResolutionDateTimeZone);
    DatabaseUtils.setTimestamp(pst, ++i, resolutionDate);
    pst.setString(++i, this.resolutionDateTimeZone);
    pst.setString(++i, cause);
    DatabaseUtils.setInt(pst, ++i, expectation);
    DatabaseUtils.setInt(pst, ++i, productId);
    DatabaseUtils.setInt(pst, ++i, customerProductId);
    DatabaseUtils.setInt(pst, ++i, userGroupId);
    DatabaseUtils.setInt(pst, ++i, causeId);
    DatabaseUtils.setInt(pst, ++i, resolutionId);
    DatabaseUtils.setInt(pst, ++i, defectId);
    DatabaseUtils.setInt(pst, ++i, this.getStateId());
    DatabaseUtils.setInt(pst, ++i, escalationLevel);
    pst.setBoolean(++i, resolvable);
    if (resolvedBy > 0) {
      pst.setInt(++i, resolvedBy);
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getResolvedByDeptCode() > 0) {
      pst.setInt(++i, this.getResolvedByDeptCode());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setInt(++i, id);
    if (!override) {
      pst.setTimestamp(++i, this.getModified());
    }
    resultCount = pst.executeUpdate();
    pst.close();
    if (this.getCloseIt()) {
      TicketLog thisEntry = new TicketLog();
      thisEntry.setEnteredBy(this.getModifiedBy());
      thisEntry.setDepartmentCode(this.getDepartmentCode());
      thisEntry.setAssignedTo(this.getAssignedTo());
      thisEntry.setPriorityCode(this.getPriorityCode());
      thisEntry.setSeverityCode(this.getSeverityCode());
      thisEntry.setEscalationCode(this.getEscalationLevel());
      thisEntry.setTicketId(this.getId());
      thisEntry.setClosed(true);
      thisEntry.setStateId(this.getStateId());
      thisEntry.process(
          db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
    }
    if (actionPlanId != -1 && insertActionPlan) {
      parseActionPlanMapping(db);
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean parseActionPlanMapping(Connection db) throws SQLException {
    ActionPlan actionPlan = new ActionPlan();
    actionPlan.setBuildPhases(true);
    actionPlan.setBuildSteps(true);
    actionPlan.queryRecord(db, actionPlanId);

    boolean exists = false;
    ActionPlanWorkList workList = new ActionPlanWorkList();
    workList.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
    workList.setLinkItemId(this.getId());
    workList.buildList(db);
    Iterator iter = (Iterator) workList.iterator();
    while (iter.hasNext()) {
      ActionPlanWork work = (ActionPlanWork) iter.next();
      if (work.getActionPlanId() == this.getActionPlanId()) {
        exists = true;
        break;
      }
    }
    if (!exists && this.getAssignedTo() != -1) {
      ActionPlanWork actionPlanWork = new ActionPlanWork();
      actionPlanWork.setActionPlanId(actionPlanId);
      actionPlanWork.setManagerId(this.getAssignedTo());
      actionPlanWork.setAssignedTo(this.getAssignedTo());
      actionPlanWork.setLinkModuleId(workList.getLinkModuleId());
      actionPlanWork.setLinkItemId(this.getId());
      actionPlanWork.setEnteredBy(this.getModifiedBy());
      actionPlanWork.setModifiedBy(this.getModifiedBy());
      actionPlanWork.insert(db, actionPlan);
    }
    //TODO:: send an email to the users about the first step.
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  toTrash        Description of the Parameter
   * @param  tmpUserId      Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {

    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE ticket " +
        "SET trashed_date = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "user_group_id = ? , " +
        "modifiedby = ? " +
        "WHERE ticketid = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    if (toTrash) {
      DatabaseUtils.setTimestamp(
          pst, ++i, new Timestamp(System.currentTimeMillis()));
    } else {
      DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
    }
    DatabaseUtils.setInt(pst, ++i, -1);
    DatabaseUtils.setInt(pst, ++i, tmpUserId);
    pst.setInt(++i, this.id);
    resultCount = pst.executeUpdate();
    pst.close();

    // Trash the tasks related to the ticket
    if (!toTrash) {
      this.getTasks().setIncludeOnlyTrashed(true);
    }
    this.buildTasks(db);
    this.getTasks().updateStatus(db, toTrash, tmpUserId);

    if (toTrash) {
      ActionPlanWorkList workList = new ActionPlanWorkList();
      workList.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
      workList.setLinkItemId(this.getId());
      workList.setSiteId(this.getSiteId());
      workList.buildList(db);
      workList.delete(db);
    }

    // Enable/Disable the contact history for the ticket
    ContactHistory.trash(
        db, OrganizationHistory.TICKET, this.getId(), !toTrash);
    return (resultCount == 1);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  newOwner       Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean reassign(Connection db, int newOwner) throws SQLException {
    int result = -1;
    this.setAssignedTo(newOwner);
    result = this.update(db);
    if (result == -1) {
      return false;
    }
    return true;
  }


  /**
   *  Reopens a ticket so that it can be modified again
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public int reopen(Connection db) throws SQLException {
    int resultCount = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = null;
      String sql =
          "UPDATE ticket " +
          "SET closed = ?, modified = " + DatabaseUtils.getCurrentTimestamp(
          db) + ", modifiedby = ? " +
          "WHERE ticketid = ? ";
      int i = 0;
      pst = db.prepareStatement(sql);
      pst.setNull(++i, java.sql.Types.TIMESTAMP);
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getId());
      resultCount = pst.executeUpdate();
      pst.close();
      this.setClosed((java.sql.Timestamp) null);
      //Update the ticket log
      TicketLog thisEntry = new TicketLog();
      thisEntry.setEnteredBy(this.getModifiedBy());
      thisEntry.setDepartmentCode(this.getDepartmentCode());
      thisEntry.setAssignedTo(this.getAssignedTo());
      thisEntry.setPriorityCode(this.getPriorityCode());
      thisEntry.setSeverityCode(this.getSeverityCode());
      thisEntry.setEscalationCode(this.getEscalationLevel());
      thisEntry.setEntryText(this.getComment());
      thisEntry.setTicketId(this.getId());
      thisEntry.setStateId(this.getStateId());
      thisEntry.process(
          db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    //Check for action list links
    int numberOfLinkedActionItems = ActionItemLogList.getLinkedActionItemLogCount(
        db, this.getId(), Constants.TICKET_OBJECT);
    if (numberOfLinkedActionItems != 0) {
      Dependency thisDependency = new Dependency();
      thisDependency.setName("actionLists");
      thisDependency.setCount(numberOfLinkedActionItems);
      thisDependency.setCanDelete(true);
      dependencyList.add(thisDependency);
    }
    //Check for task links
    try {
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) as linkcount " +
          "FROM tasklink_ticket " +
          "WHERE ticket_id = ? ");
      pst.setInt(++i, this.getId());
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int linkcount = rs.getInt("linkcount");
        if (linkcount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("tasks");
          thisDependency.setCount(linkcount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    //Check for documents
    Dependency docDependency = new Dependency();
    docDependency.setName("documents");
    docDependency.setCount(
        FileItemList.retrieveRecordCount(
        db, Constants.DOCUMENTS_TICKETS, this.getId()));
    docDependency.setCanDelete(true);
    dependencyList.add(docDependency);
    //Check for folders
    Dependency folderDependency = new Dependency();
    folderDependency.setName("folders");
    folderDependency.setCount(
        CustomFieldRecordList.retrieveRecordCount(
        db, Constants.FOLDERS_TICKETS, this.getId()));
    folderDependency.setCanDelete(true);
    dependencyList.add(folderDependency);
    //Check for Form - Asset Maintenance links
    try {
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) as linkcount " +
          "FROM ticket_sun_form " +
          "WHERE link_ticket_id = ? ");
      pst.setInt(++i, this.getId());
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int linkcount = rs.getInt("linkcount");
        if (linkcount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("maintenanceNotes");
          thisDependency.setCount(linkcount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    //Check for Quotes
    try {
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "SELECT COUNT(DISTINCT(group_id)) AS quotecount " +
          "FROM quote_entry " +
          "WHERE ticketid = ? " +
          "AND trashed_date IS NULL ");
      pst.setInt(++i, this.getId());
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int quotecount = rs.getInt("quotecount");
        if (quotecount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("quotes");
          thisDependency.setCount(quotecount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    //Check for Action Plan Records
    try {
      int i = 0;
      int linkModuleId = ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS);
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) AS plancount " +
          "FROM action_plan_work " +
          "WHERE link_module_id = ? " +
          "AND link_item_id = ? ");
      pst.setInt(++i, linkModuleId);
      pst.setInt(++i, this.getId());
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int plancount = rs.getInt("plancount");
        if (plancount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("actionPlanRecords");
          thisDependency.setCount(plancount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    //Check for Form - Activities links
    try {
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) as linkcount " +
          "FROM ticket_csstm_form " +
          "WHERE link_ticket_id = ? ");
      pst.setInt(++i, this.getId());
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int linkcount = rs.getInt("linkcount");
        if (linkcount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("activities");
          thisDependency.setCount(linkcount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }

    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  baseFilePath   Description of the Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Ticket ID not specified.");
    }
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      //delete any related action list items
      ActionItemLog.deleteLink(db, this.getId(), Constants.TICKET_OBJECT);

      //Delete any documents
      FileItemList fileList = new FileItemList();
      fileList.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
      fileList.setLinkItemId(this.getId());
      fileList.buildList(db);
      fileList.delete(db, getFileLibraryPath(baseFilePath, "tickets"));
      fileList = null;

      //Delete any folder data
      CustomFieldRecordList folderList = new CustomFieldRecordList();
      folderList.setLinkModuleId(Constants.FOLDERS_TICKETS);
      folderList.setLinkItemId(this.getId());
      folderList.buildList(db);
      folderList.delete(db);
      folderList = null;

      ActionPlanWorkList workList = new ActionPlanWorkList();
      workList.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS));
      workList.setIncludeAllSites(true);
      workList.setSiteId(this.getSiteId());
      workList.setLinkItemId(this.getId());
      workList.buildList(db);
      workList.delete(db);

      //Delete the ticket tasks
      if (tasks == null || tasks.size() == 0) {
        this.buildTasks(db);
      }
      this.getTasks().delete(db);

      //delete all history data
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM ticketlog WHERE ticketid = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      //Delete the related project link
      pst = db.prepareStatement(
          "DELETE FROM ticketlink_project " +
          "WHERE ticket_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      //delete related task links
      pst = db.prepareStatement(
          "DELETE FROM tasklink_ticket WHERE ticket_id = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      //delete related activity items
      pst = db.prepareStatement(
          "DELETE FROM trouble_asset_replacement WHERE link_form_id IN (SELECT form_id FROM ticket_sun_form WHERE link_ticket_id = ?)");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      //delete related activity items
      pst = db.prepareStatement(
          "DELETE FROM ticket_sun_form WHERE link_ticket_id = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      //delete related activity items
      pst = db.prepareStatement(
          "DELETE FROM ticket_activity_item WHERE link_form_id IN (SELECT form_id FROM ticket_csstm_form WHERE link_ticket_id = ?) ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      //delete related activity items
      pst = db.prepareStatement(
          "DELETE FROM ticket_csstm_form WHERE link_ticket_id = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      //delete related contact history
      ContactHistory.deleteObject(
          db, OrganizationHistory.TICKET, this.getId());

      //Delete the ticket
      pst = db.prepareStatement("DELETE FROM ticket WHERE ticketid = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
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
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int update(Connection db) throws SQLException {
    int i = -1;
    try {
      db.setAutoCommit(false);
      i = this.update(db, false);
      updateEntry(db);
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void updateEntry(Connection db) throws SQLException {
    TicketLog thisEntry = new TicketLog();
    thisEntry.setEnteredBy(this.getModifiedBy());
    thisEntry.setDepartmentCode(this.getDepartmentCode());
    thisEntry.setAssignedTo(this.getAssignedTo());
    thisEntry.setEntryText(this.getComment());
    thisEntry.setTicketId(this.getId());
    thisEntry.setPriorityCode(this.getPriorityCode());
    thisEntry.setSeverityCode(this.getSeverityCode());
    thisEntry.setStateId(this.getStateId());
    thisEntry.setEscalationCode(this.getEscalationLevel());
    if (this.getCloseIt()) {
      thisEntry.setClosed(true);
    }
    history.add(thisEntry);
    Iterator hist = history.iterator();
    while (hist.hasNext()) {
      TicketLog thisLog = (TicketLog) hist.next();
      thisLog.process(
          db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //ticket table
    this.setId(rs.getInt("ticketid"));
    orgId = DatabaseUtils.getInt(rs, "org_id");
    contactId = DatabaseUtils.getInt(rs, "contact_id");
    problem = rs.getString("problem");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    closed = rs.getTimestamp("closed");
    if (!rs.wasNull()) {
      closeIt = true;
    }
    priorityCode = DatabaseUtils.getInt(rs, "pri_code");
    levelCode = DatabaseUtils.getInt(rs, "level_code");
    departmentCode = DatabaseUtils.getInt(rs, "department_code");
    sourceCode = DatabaseUtils.getInt(rs, "source_code");
    catCode = DatabaseUtils.getInt(rs, "cat_code", 0);
    subCat1 = DatabaseUtils.getInt(rs, "subcat_code1", 0);
    subCat2 = DatabaseUtils.getInt(rs, "subcat_code2", 0);
    subCat3 = DatabaseUtils.getInt(rs, "subcat_code3", 0);
    assignedTo = DatabaseUtils.getInt(rs, "assigned_to");
    solution = rs.getString("solution");
    severityCode = DatabaseUtils.getInt(rs, "scode");
    location = rs.getString("location");
    assignedDate = rs.getTimestamp("assigned_date");
    estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
    resolutionDate = rs.getTimestamp("resolution_date");
    cause = rs.getString("cause");
    contractId = DatabaseUtils.getInt(rs, "link_contract_id");
    assetId = DatabaseUtils.getInt(rs, "link_asset_id");
    productId = DatabaseUtils.getInt(rs, "product_id");
    customerProductId = DatabaseUtils.getInt(rs, "customer_product_id");
    expectation = DatabaseUtils.getInt(rs, "expectation");
    projectTicketCount = rs.getInt("key_count");
    estimatedResolutionDateTimeZone = rs.getString(
        "est_resolution_date_timezone");
    assignedDateTimeZone = rs.getString("assigned_date_timezone");
    resolutionDateTimeZone = rs.getString("resolution_date_timezone");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    trashedDate = rs.getTimestamp("trashed_date");
    userGroupId = DatabaseUtils.getInt(rs, "user_group_id");
    causeId = DatabaseUtils.getInt(rs, "cause_id");
    resolutionId = DatabaseUtils.getInt(rs, "resolution_id");
    defectId = DatabaseUtils.getInt(rs, "defect_id");
    escalationLevel = DatabaseUtils.getInt(rs, "escalation_level");
    resolvable = rs.getBoolean("resolvable");
    resolvedBy = rs.getInt("resolvedby");
    resolvedByDeptCode = DatabaseUtils.getInt(rs, "resolvedby_department_code");
    stateId = DatabaseUtils.getInt(rs, "state_id");
    siteId = DatabaseUtils.getInt(rs, "site_id");
    //organization table
    companyName = rs.getString("orgname");
    companyEnabled = rs.getBoolean("orgenabled");
    orgSiteId = DatabaseUtils.getInt(rs, "orgsiteid");

    //lookup_department table
    departmentName = rs.getString("dept");
    resolvedByDeptName = rs.getString("resolvedept");

    //ticket_priority table
    priorityName = rs.getString("ticpri");

    //ticket_severity table
    severityName = rs.getString("ticsev");

    //ticket_category table
    categoryName = rs.getString("catname");
    //lookup_ticket_source table
    sourceName = rs.getString("sourcename");

    //service_contract table
    serviceContractNumber = rs.getString("contractnumber");
    totalHoursRemaining = rs.getFloat("hoursremaining");
    contractStartDate = rs.getTimestamp("contractstartdate");
    contractEndDate = rs.getTimestamp("contractenddate");
    contractOnsiteResponseModel = DatabaseUtils.getInt(
        rs, "contractonsiteservicemodel");

    //asset table
    assetSerialNumber = rs.getString("serialnumber");
    assetManufacturerCode = DatabaseUtils.getInt(rs, "assetmanufacturercode");
    assetVendorCode = DatabaseUtils.getInt(rs, "assetvendorcode");
    assetModelVersion = rs.getString("modelversion");
    assetLocation = rs.getString("assetlocation");
    assetOnsiteResponseModel = DatabaseUtils.getInt(
        rs, "assetonsiteservicemodel");

    //product catalog
    productSku = rs.getString("productsku");
    productName = rs.getString("productname");

    //ticketlink_project
    projectId = DatabaseUtils.getInt(rs, "project_id");

    //projects
    projectName = rs.getString("projectname");

    // user groups
    userGroupName = rs.getString("usergroupname");

    //from lookup_ticket_escalation table
    escalationLevelName = rs.getString("escalationlevelname");

    //Calculations
    if (entered != null) {
      if (closed != null) {
        //float ageCheck = ((closed.getTime() - entered.getTime()) / 86400000);
        //ageDays = java.lang.Math.round(ageCheck);
        float ageCheck = ((closed.getTime() - entered.getTime()) / 3600000);
        int totalHours = java.lang.Math.round(ageCheck);
        ageDays = java.lang.Math.round(totalHours / 24);
        ageHours = java.lang.Math.round(totalHours - (24 * ageDays));
      } else {
        //float ageCheck = ((System.currentTimeMillis() - entered.getTime()) / 86400000);
        //ageDays = java.lang.Math.round(ageCheck);
        float ageCheck = (
            (System.currentTimeMillis() - entered.getTime()) / 3600000);
        int totalHours = java.lang.Math.round(ageCheck);
        ageDays = java.lang.Math.round(totalHours / 24);
        ageHours = java.lang.Math.round(totalHours - (24 * ageDays));
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean hasFiles() {
    return (files != null && files.size() > 0);
  }


  /**
   *  Gets the properties that are TimeZone sensitive for auto-populating
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("assignedDate");
    thisList.add("estimatedResolutionDate");
    thisList.add("resolutionDate");
    thisList.add("contractStartDate");
    thisList.add("contractEndDate");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  projectId      Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void insertProjectLink(Connection db, int projectId) throws SQLException {
    String sql = "INSERT INTO ticketlink_project " +
        "(ticket_id, project_id) " +
        "VALUES (?, ?) ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, this.getId());
    pst.setInt(++i, projectId);
    pst.execute();
    pst.close();
  }


  /**
   *  Each ticket in a project has its own unique count
   *
   * @param  db             Description of the Parameter
   * @param  projectId      Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void updateProjectTicketCount(Connection db, int projectId) throws SQLException {
    Exception errorMessage = null;
    boolean autoCommit = db.getAutoCommit();
    try {
      if (autoCommit) {
        db.setAutoCommit(false);
      }
      int i = 0;
      // Lock the row with the new value
      PreparedStatement pst = db.prepareStatement(
          "UPDATE project_ticket_count " +
          "SET key_count = key_count + 1 " +
          "WHERE project_id = ? ");
      pst.setInt(++i, projectId);
      pst.execute();
      pst.close();
      // Retrieve the new value
      i = 0;
      pst = db.prepareStatement(
          "SELECT key_count " +
          "FROM project_ticket_count " +
          "WHERE project_id = ? ");
      pst.setInt(++i, projectId);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        projectTicketCount = rs.getInt("key_count");
      }
      rs.close();
      pst.close();
      if (autoCommit) {
        db.commit();
      }
    } catch (Exception e) {
      errorMessage = e;
      if (autoCommit) {
        db.rollback();
      }
    } finally {
      if (autoCommit) {
        db.setAutoCommit(true);
      }
    }
    if (errorMessage != null) {
      throw new SQLException(errorMessage.getMessage());
    }
  }


  /**
   *  Each ticket in a project has its own unique count
   *
   * @param  tz       Description of the Parameter
   * @param  created  Description of the Parameter
   * @param  type     Description of the Parameter
   * @return          Description of the Return Value
   */
  public String generateWebcalEvent(TimeZone tz, Timestamp created, int type) {
    StringBuffer webcal = new StringBuffer();
    String CRLF = System.getProperty("line.separator");

    String description = "";
    if (this.getId() != -1) {
      description += "Ticket #: ";
      if (type == TicketEventList.OPEN_TICKET) {
        description += getPaddedId();
      } else if (type == TicketEventList.OPEN_PROJECT_TICKET) {
        description += getProjectTicketCount();
        //??
      }
    }

    if (type == TicketEventList.OPEN_TICKET) {
      if (companyName != null) {
        description += "\\nCompany: " + companyName;
      }
      if (thisContact != null) {
        description += "\\nContact: " + thisContact.getNameFirstLast();
      }
    } else if (type == TicketEventList.OPEN_PROJECT_TICKET) {
      if (projectName != null) {
        description += "\\nProject: " + projectName;
      }
    }

    //write the event
    webcal.append("BEGIN:VEVENT" + CRLF);

    if (type == TicketEventList.OPEN_TICKET) {
      webcal.append(
          "UID:www.centriccrm.com-ticket-alerts-" + this.getId() + CRLF);
    } else if (type == TicketEventList.OPEN_PROJECT_TICKET) {
      webcal.append(
          "UID:www.centriccrm.com-project-ticket-alerts-" + this.getId() + CRLF);
    }

    if (created != null) {
      webcal.append("DTSTAMP:" + ICalendar.getDateTimeUTC(created) + CRLF);
    }
    if (entered != null) {
      webcal.append("CREATED:" + ICalendar.getDateTimeUTC(entered) + CRLF);
    }
    if (estimatedResolutionDate != null) {
      webcal.append(
          "DTSTART;TZID=" + tz.getID() + ":" + ICalendar.getDateTime(
          tz, estimatedResolutionDate) + CRLF);
    }
    if (problem != null) {
      webcal.append(
          ICalendar.foldLine("SUMMARY:" + ICalendar.parseNewLine(problem)) + CRLF);
    }
    if (description != null) {
      webcal.append(ICalendar.foldLine("DESCRIPTION:" + description) + CRLF);
    }

    webcal.append("END:VEVENT" + CRLF);

    return webcal.toString();
  }


  /**
   *  Gets the projectIdByTicket attribute of the Ticket object
   *
   * @param  db                Description of the Parameter
   * @return                   The projectIdByTicket value
   * @exception  SQLException  Description of the Exception
   */
  public int getProjectIdByTicket(Connection db) throws SQLException {
    int result = -1;
    PreparedStatement pst = db.prepareStatement("SELECT project_id FROM ticketlink_project WHERE ticket_id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = DatabaseUtils.getInt(rs, "project_id");
    }
    rs.close();
    pst.close();
    return result;
  }
}

