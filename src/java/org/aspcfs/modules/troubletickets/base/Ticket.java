//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.actionlist.base.ActionList;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionlist.base.ActionItemLogList;
import org.aspcfs.modules.base.CustomFieldRecordList;

/**
 *  Represents a Ticket in CFS
 *
 *@author     chris
 *@created    November 8, 2001
 *@version    $Id$
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
  private String assetManufacturer = null;
  private String assetVendor = null;
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
  private int expectation = -1;
  //Related descriptions
  private String companyName = "";
  private String categoryName = "";
  private String departmentName = "";
  private String priorityName = "";
  private String severityName = "";
  private String sourceName = "";

  private boolean closeIt = false;
  private boolean companyEnabled = true;

  private int ageDays = 0;
  private int ageHours = 0;
  private int campaignId = -1;
  private boolean hasEnabledOwnerAccount = true;
  //Resources
  private boolean buildFiles = false;
  private boolean buildTasks = false;
  private boolean buildHistory = false;

  private TicketLogList history = new TicketLogList();
  private FileItemList files = new FileItemList();
  private TaskList tasks = new TaskList();

  //action list properties
  private int actionId = -1;

  /**
   *  Constructor for the Ticket object, creates an empty Ticket
   *
   *@since    1.0
   */
  public Ticket() { }


  /**
   *  Constructor for the Ticket object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Ticket(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  id                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Ticket(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Ticket Number");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT t.*, " +
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
        "a.onsite_service_model AS assetonsiteservicemodel " +
        "FROM ticket t " +
        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
        "LEFT JOIN lookup_department ld ON (t.department_code = ld.code) " +
        "LEFT JOIN ticket_priority tp ON (t.pri_code = tp.code) " +
        "LEFT JOIN ticket_severity ts ON (t.scode = ts.code) " +
        "LEFT JOIN ticket_category tc ON (t.cat_code = tc.id) " +
        "LEFT JOIN lookup_ticketsource lu_ts ON (t.source_code = lu_ts.code) " +
        "LEFT JOIN service_contract sc ON (t.link_contract_id = sc.contract_id) " +
        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
        "WHERE t.ticketid = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Ticket not found");
    }
    if (this.getContactId() > 0 && checkContactRecord(db, this.getContactId())) {
      thisContact = new Contact(db, this.getContactId());
    } else {
      thisContact = null;
    }
    if (buildHistory) {
      this.buildHistory(db);
    }
    if (buildFiles) {
      this.buildFiles(db);
    }
    if (buildTasks) {
      this.buildTasks(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildHistory(Connection db) throws SQLException {
    history.setTicketId(this.getId());
    history.buildList(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildTasks(Connection db) throws SQLException {
    tasks.setTicketId(this.getId());
    tasks.buildList(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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
   *  Sets the closed attribute of the Ticket object
   *
   *@param  closed  The new closed value
   */
  public void setClosed(java.sql.Timestamp closed) {
    this.closed = closed;
  }


  /**
   *  Sets the closed attribute of the Ticket object
   *
   *@param  tmp  The new closed value
   */
  public void setClosed(String tmp) {
    this.closed = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the expectation attribute of the Ticket object
   *
   *@param  tmp  The new expectation value
   */
  public void setExpectation(int tmp) {
    this.expectation = tmp;
  }


  /**
   *  Sets the expectation attribute of the Ticket object
   *
   *@param  tmp  The new expectation value
   */
  public void setExpectation(String tmp) {
    this.expectation = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ThisContact attribute of the Ticket object
   *
   *@param  thisContact  The new ThisContact value
   *@since
   */
  public void setThisContact(Contact thisContact) {
    this.thisContact = thisContact;
  }



  /**
   *  Sets the actionId attribute of the Ticket object
   *
   *@param  actionId  The new actionId value
   */
  public void setActionId(int actionId) {
    this.actionId = actionId;
  }


  /**
   *  Sets the actionId attribute of the Ticket object
   *
   *@param  actionId  The new actionId value
   */
  public void setActionId(String actionId) {
    this.actionId = Integer.parseInt(actionId);
  }


  /**
   *  Gets the actionId attribute of the Ticket object
   *
   *@return    The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   *  Sets the tasks attribute of the Ticket object
   *
   *@param  tasks  The new tasks value
   */
  public void setTasks(TaskList tasks) {
    this.tasks = tasks;
  }


  /**
   *  Gets the tasks attribute of the Ticket object
   *
   *@return    The tasks value
   */
  public TaskList getTasks() {
    return tasks;
  }


  /**
   *  Gets the companyEnabled attribute of the Ticket object
   *
   *@return    The companyEnabled value
   */
  public boolean getCompanyEnabled() {
    return companyEnabled;
  }


  /**
   *  Sets the companyEnabled attribute of the Ticket object
   *
   *@param  companyEnabled  The new companyEnabled value
   */
  public void setCompanyEnabled(boolean companyEnabled) {
    this.companyEnabled = companyEnabled;
  }


  /**
   *  Sets the Newticketlogentry attribute of the Ticket object
   *
   *@param  newticketlogentry  The new Newticketlogentry value
   *@since
   */
  public void setNewticketlogentry(String newticketlogentry) {
    this.comment = newticketlogentry;
  }


  /**
   *  Sets the AssignedTo attribute of the Ticket object
   *
   *@param  assignedTo  The new AssignedTo value
   *@since
   */
  public void setAssignedTo(int assignedTo) {
    this.assignedTo = assignedTo;
  }


  /**
   *  Sets the assignedDate attribute of the Ticket object
   *
   *@param  tmp  The new assignedDate value
   */
  public void setAssignedDate(java.sql.Timestamp tmp) {
    this.assignedDate = tmp;
  }


  /**
   *  Sets the assignedDate attribute of the Ticket object
   *
   *@param  tmp  The new assignedDate value
   */
  public void setAssignedDate(String tmp) {
    this.assignedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the SubCat1 attribute of the Ticket object
   *
   *@param  tmp  The new SubCat1 value
   *@since
   */
  public void setSubCat1(int tmp) {
    this.subCat1 = tmp;
  }


  /**
   *  Sets the SubCat2 attribute of the Ticket object
   *
   *@param  tmp  The new SubCat2 value
   *@since
   */
  public void setSubCat2(int tmp) {
    this.subCat2 = tmp;
  }


  /**
   *  Sets the SourceName attribute of the Ticket object
   *
   *@param  sourceName  The new SourceName value
   *@since
   */
  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }


  /**
   *  Sets the entered attribute of the Ticket object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the Ticket object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Ticket object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Ticket object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the SubCat3 attribute of the Ticket object
   *
   *@param  tmp  The new SubCat3 value
   *@since
   */
  public void setSubCat3(int tmp) {
    this.subCat3 = tmp;
  }


  /**
   *  Sets the SubCat1 attribute of the Ticket object
   *
   *@param  tmp  The new SubCat1 value
   *@since
   */
  public void setSubCat1(String tmp) {
    this.subCat1 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the SubCat2 attribute of the Ticket object
   *
   *@param  tmp  The new SubCat2 value
   *@since
   */
  public void setSubCat2(String tmp) {
    this.subCat2 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the SubCat3 attribute of the Ticket object
   *
   *@param  tmp  The new SubCat3 value
   *@since
   */
  public void setSubCat3(String tmp) {
    this.subCat3 = Integer.parseInt(tmp);
  }


  /**
   *  Sets the AssignedTo attribute of the Ticket object
   *
   *@param  assignedTo  The new AssignedTo value
   *@since
   */
  public void setAssignedTo(String assignedTo) {
    this.assignedTo = Integer.parseInt(assignedTo);
  }


  /**
   *  Sets the DepartmentName attribute of the Ticket object
   *
   *@param  departmentName  The new DepartmentName value
   *@since
   */
  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }


  /**
   *  Sets the CloseIt attribute of the Ticket object
   *
   *@param  closeIt  The new CloseIt value
   *@since
   */
  public void setCloseIt(boolean closeIt) {
    this.closeIt = closeIt;
  }


  /**
   *  Sets the closeNow attribute of the Ticket object
   *
   *@param  tmp  The new closeNow value
   */
  public void setCloseNow(String tmp) {
    this.closeIt = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the SeverityName attribute of the Ticket object
   *
   *@param  severityName  The new SeverityName value
   *@since
   */
  public void setSeverityName(String severityName) {
    this.severityName = severityName;
  }


  /**
   *  Sets the ErrorMessage attribute of the Ticket object
   *
   *@param  tmp  The new ErrorMessage value
   *@since
   */
  public void setErrorMessage(String tmp) {
    this.errorMessage = tmp;
  }


  /**
   *  Sets the campaignId attribute of the Ticket object
   *
   *@param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Sets the campaignId attribute of the Ticket object
   *
   *@param  tmp  The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the History attribute of the Ticket object
   *
   *@param  history  The new History value
   *@since
   */
  public void setHistory(TicketLogList history) {
    this.history = history;
  }


  /**
   *  Sets the files attribute of the Ticket object
   *
   *@param  tmp  The new files value
   */
  public void setFiles(FileItemList tmp) {
    this.files = tmp;
  }


  /**
   *  Sets the Id attribute of the Ticket object
   *
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(int tmp) {
    this.id = tmp;
    history.setTicketId(tmp);
  }


  /**
   *  Sets the Id attribute of the Ticket object
   *
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(String tmp) {
    this.setId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the CompanyName attribute of the Ticket object
   *
   *@param  companyName  The new CompanyName value
   *@since
   */
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }


  /**
   *  Sets the OrgId attribute of the Ticket object
   *
   *@param  tmp  The new OrgId value
   *@since
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the OrgId attribute of the Ticket object
   *
   *@param  tmp  The new OrgId value
   *@since
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contractId attribute of the Ticket object
   *
   *@param  tmp  The new contractId value
   */
  public void setContractId(int tmp) {
    this.contractId = tmp;
  }


  /**
   *  Sets the contractId attribute of the Ticket object
   *
   *@param  tmp  The new contractId value
   */
  public void setContractId(String tmp) {
    this.contractId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the serviceContractNumber attribute of the Ticket object
   *
   *@param  tmp  The new serviceContractNumber value
   */
  public void setServiceContractNumber(String tmp) {
    this.serviceContractNumber = tmp;
  }


  /**
   *  Sets the totalHoursRemaining attribute of the Ticket object
   *
   *@param  tmp  The new totalHoursRemaining value
   */
  public void setTotalHoursRemaining(double tmp) {
    this.totalHoursRemaining = tmp;
  }


  /**
   *  Sets the totalHoursRemaining attribute of the Ticket object
   *
   *@param  tmp  The new totalHoursRemaining value
   */
  public void setTotalHoursRemaining(String tmp) {
    this.totalHoursRemaining = Double.parseDouble(tmp);
  }


  /**
   *  Sets the contractStartDate attribute of the Ticket object
   *
   *@param  tmp  The new contractStartDate value
   */
  public void setContractStartDate(java.sql.Timestamp tmp) {
    this.contractStartDate = tmp;
  }


  /**
   *  Sets the contractStartDate attribute of the Ticket object
   *
   *@param  tmp  The new contractStartDate value
   */
  public void setContractStartDate(String tmp) {
    this.contractStartDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the contractEndDate attribute of the Ticket object
   *
   *@param  tmp  The new contractEndDate value
   */
  public void setContractEndDate(java.sql.Timestamp tmp) {
    this.contractEndDate = tmp;
  }


  /**
   *  Sets the contractEndDate attribute of the Ticket object
   *
   *@param  tmp  The new contractEndDate value
   */
  public void setContractEndDate(String tmp) {
    this.contractEndDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the contractOnsiteResponseModel attribute of the Ticket object
   *
   *@param  tmp  The new contractOnsiteResponseModel value
   */
  public void setContractOnsiteResponseModel(int tmp) {
    this.contractOnsiteResponseModel = tmp;
  }


  /**
   *  Sets the contractOnsiteResponseModel attribute of the Ticket object
   *
   *@param  tmp  The new contractOnsiteResponseModel value
   */
  public void setContractOnsiteResponseModel(String tmp) {
    this.contractOnsiteResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the assetId attribute of the Ticket object
   *
   *@param  tmp  The new assetId value
   */
  public void setAssetId(int tmp) {
    this.assetId = tmp;
  }


  /**
   *  Sets the assetId attribute of the Ticket object
   *
   *@param  tmp  The new assetId value
   */
  public void setAssetId(String tmp) {
    this.assetId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the assetSerialNumber attribute of the Ticket object
   *
   *@param  tmp  The new assetSerialNumber value
   */
  public void setAssetSerialNumber(String tmp) {
    this.assetSerialNumber = tmp;
  }


  /**
   *  Sets the assetManufacturer attribute of the Ticket object
   *
   *@param  tmp  The new assetManufacturer value
   */
  public void setAssetManufacturer(String tmp) {
    this.assetManufacturer = tmp;
  }


  /**
   *  Sets the assetVendor attribute of the Ticket object
   *
   *@param  tmp  The new assetVendor value
   */
  public void setAssetVendor(String tmp) {
    this.assetVendor = tmp;
  }


  /**
   *  Sets the modelVersion attribute of the Ticket object
   *
   *@param  tmp  The new modelVersion value
   */
  public void setAssetModelVersion(String tmp) {
    this.assetModelVersion = tmp;
  }


  /**
   *  Sets the location attribute of the Ticket object
   *
   *@param  tmp  The new location value
   */
  public void setAssetLocation(String tmp) {
    this.assetLocation = tmp;
  }


  /**
   *  Sets the assetOnsiteResponseModel attribute of the Ticket object
   *
   *@param  tmp  The new assetOnsiteResponseModel value
   */
  public void setAssetOnsiteResponseModel(int tmp) {
    this.assetOnsiteResponseModel = tmp;
  }


  /**
   *  Sets the assetOnsiteResponseModel attribute of the Ticket object
   *
   *@param  tmp  The new assetOnsiteResponseModel value
   */
  public void setAssetOnsiteResponseModel(String tmp) {
    this.assetOnsiteResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildFiles attribute of the Ticket object
   *
   *@param  buildFiles  The new buildFiles value
   */
  public void setBuildFiles(boolean buildFiles) {
    this.buildFiles = buildFiles;
  }


  /**
   *  Sets the buildTasks attribute of the Ticket object
   *
   *@param  buildTasks  The new buildTasks value
   */
  public void setBuildTasks(boolean buildTasks) {
    this.buildTasks = buildTasks;
  }


  /**
   *  Sets the buildHistory attribute of the Ticket object
   *
   *@param  buildHistory  The new buildHistory value
   */
  public void setBuildHistory(boolean buildHistory) {
    this.buildHistory = buildHistory;
  }


  /**
   *  Gets the buildFiles attribute of the Ticket object
   *
   *@return    The buildFiles value
   */
  public boolean getBuildFiles() {
    return buildFiles;
  }


  /**
   *  Gets the buildTasks attribute of the Ticket object
   *
   *@return    The buildTasks value
   */
  public boolean getBuildTasks() {
    return buildTasks;
  }


  /**
   *  Gets the buildHistory attribute of the Ticket object
   *
   *@return    The buildHistory value
   */
  public boolean getBuildHistory() {
    return buildHistory;
  }


  /**
   *  Gets the hasEnabledOwnerAccount attribute of the Ticket object
   *
   *@return    The hasEnabledOwnerAccount value
   */
  public boolean getHasEnabledOwnerAccount() {
    return hasEnabledOwnerAccount;
  }


  /**
   *  Sets the hasEnabledOwnerAccount attribute of the Ticket object
   *
   *@param  hasEnabledOwnerAccount  The new hasEnabledOwnerAccount value
   */
  public void setHasEnabledOwnerAccount(boolean hasEnabledOwnerAccount) {
    this.hasEnabledOwnerAccount = hasEnabledOwnerAccount;
  }


  /**
   *  Sets the ContactId attribute of the Ticket object
   *
   *@param  tmp  The new ContactId value
   *@since
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the PriorityName attribute of the Ticket object
   *
   *@param  priorityName  The new PriorityName value
   *@since
   */
  public void setPriorityName(String priorityName) {
    this.priorityName = priorityName;
  }


  /**
   *  Sets the ContactId attribute of the Ticket object
   *
   *@param  tmp  The new ContactId value
   *@since
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the AgeOf attribute of the Ticket object
   *
   *@param  ageOf  The new AgeOf value
   *@since
   */
  public void setAgeDays(int ageOf) {
    this.ageDays = ageOf;
  }


  /**
   *  Sets the CategoryName attribute of the Ticket object
   *
   *@param  categoryName  The new CategoryName value
   *@since
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }


  /**
   *  Sets the Problem attribute of the Ticket object
   *
   *@param  tmp  The new Problem value
   *@since
   */
  public void setProblem(String tmp) {
    this.problem = tmp;
  }


  /**
   *  Sets the location attribute of the Ticket object
   *
   *@param  tmp  The new location value
   */
  public void setLocation(String tmp) {
    this.location = tmp;
  }


  /**
   *  Sets the Comment attribute of the Ticket object
   *
   *@param  tmp  The new Comment value
   *@since
   */
  public void setComment(String tmp) {
    this.comment = tmp;
  }


  /**
   *  Sets the estimatedResolutionDate attribute of the Ticket object
   *
   *@param  tmp  The new estimatedResolutionDate value
   */
  public void setEstimatedResolutionDate(java.sql.Timestamp tmp) {
    this.estimatedResolutionDate = tmp;
  }


  /**
   *  Sets the estimatedResolutionDate attribute of the Ticket object
   *
   *@param  tmp  The new estimatedResolutionDate value
   */
  public void setEstimatedResolutionDate(String tmp) {
    this.estimatedResolutionDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the cause attribute of the Ticket object
   *
   *@param  tmp  The new cause value
   */
  public void setCause(String tmp) {
    this.cause = tmp;
  }


  /**
   *  Sets the Solution attribute of the Ticket object
   *
   *@param  tmp  The new Solution value
   *@since
   */
  public void setSolution(String tmp) {
    this.solution = tmp;
  }


  /**
   *  Sets the PriorityCode attribute of the Ticket object
   *
   *@param  tmp  The new PriorityCode value
   *@since
   */
  public void setPriorityCode(int tmp) {
    this.priorityCode = tmp;
  }


  /**
   *  Sets the PriorityCode attribute of the Ticket object
   *
   *@param  tmp  The new PriorityCode value
   *@since
   */
  public void setPriorityCode(String tmp) {
    this.priorityCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the LevelCode attribute of the Ticket object
   *
   *@param  tmp  The new LevelCode value
   *@since
   */
  public void setLevelCode(int tmp) {
    this.levelCode = tmp;
  }


  /**
   *  Sets the levelCode attribute of the Ticket object
   *
   *@param  tmp  The new levelCode value
   */
  public void setLevelCode(String tmp) {
    this.levelCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the DepartmentCode attribute of the Ticket object
   *
   *@param  tmp  The new DepartmentCode value
   *@since
   */
  public void setDepartmentCode(int tmp) {
    this.departmentCode = tmp;
  }


  /**
   *  Sets the DepartmentCode attribute of the Ticket object
   *
   *@param  tmp  The new DepartmentCode value
   *@since
   */
  public void setDepartmentCode(String tmp) {
    this.departmentCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the SourceCode attribute of the Ticket object
   *
   *@param  tmp  The new SourceCode value
   *@since
   */
  public void setSourceCode(int tmp) {
    this.sourceCode = tmp;
  }


  /**
   *  Sets the SourceCode attribute of the Ticket object
   *
   *@param  tmp  The new SourceCode value
   *@since
   */
  public void setSourceCode(String tmp) {
    this.sourceCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the CatCode attribute of the Ticket object
   *
   *@param  tmp  The new CatCode value
   *@since
   */
  public void setCatCode(int tmp) {
    this.catCode = tmp;
  }


  /**
   *  Sets the CatCode attribute of the Ticket object
   *
   *@param  tmp  The new CatCode value
   *@since
   */
  public void setCatCode(String tmp) {
    this.catCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the SeverityCode attribute of the Ticket object
   *
   *@param  tmp  The new SeverityCode value
   *@since
   */
  public void setSeverityCode(int tmp) {
    this.severityCode = tmp;
  }


  /**
   *  Sets the SeverityCode attribute of the Ticket object
   *
   *@param  tmp  The new SeverityCode value
   *@since
   */
  public void setSeverityCode(String tmp) {
    this.severityCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the resolutionDate attribute of the Ticket object
   *
   *@param  tmp  The new resolutionDate value
   */
  public void setResolutionDate(java.sql.Timestamp tmp) {
    this.resolutionDate = tmp;
  }


  /**
   *  Sets the resolutionDate attribute of the Ticket object
   *
   *@param  tmp  The new resolutionDate value
   */
  public void setResolutionDate(String tmp) {
    this.resolutionDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the EnteredBy attribute of the Ticket object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the Ticket object
   *
   *@param  tmp  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ModifiedBy attribute of the Ticket object
   *
   *@param  tmp  The new ModifiedBy value
   *@since
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the ModifiedBy attribute of the Ticket object
   *
   *@param  tmp  The new ModifiedBy value
   *@since
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the RequestItems attribute of the Ticket object
   *
   *@param  request  The new RequestItems value
   *@since
   */
  public void setRequestItems(HttpServletRequest request) {
    history = new TicketLogList(request, this.getModifiedBy());
  }


  /**
   *  Gets the closed attribute of the Ticket object
   *
   *@return    The closed value
   */
  public java.sql.Timestamp getClosed() {
    return closed;
  }


  /**
   *  Gets the closedString attribute of the Ticket object
   *
   *@return    The closedString value
   */
  public String getClosedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(closed);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the closed attribute of the Ticket object
   *
   *@return    The closed value
   */
  public boolean isClosed() {
    return closed != null;
  }


  /**
   *  Gets the expectation attribute of the Ticket object
   *
   *@return    The expectation value
   */
  public int getExpectation() {
    return expectation;
  }


  /**
   *  Gets the paddedId attribute of the Ticket object
   *
   *@return    The paddedId value
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
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the Ticket object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedString attribute of the Ticket object
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
   *  Gets the enteredString attribute of the Ticket object
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
   *  Gets the modifiedDateTimeString attribute of the Ticket object
   *
   *@return    The modifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the ThisContact attribute of the Ticket object
   *
   *@return    The ThisContact value
   *@since
   */
  public Contact getThisContact() {
    return thisContact;
  }


  /**
   *  Gets the SourceName attribute of the Ticket object
   *
   *@return    The SourceName value
   *@since
   */
  public String getSourceName() {
    return sourceName;
  }


  /**
   *  Gets the SubCat1 attribute of the Ticket object
   *
   *@return    The SubCat1 value
   *@since
   */
  public int getSubCat1() {
    return subCat1;
  }


  /**
   *  Gets the SubCat2 attribute of the Ticket object
   *
   *@return    The SubCat2 value
   *@since
   */
  public int getSubCat2() {
    return subCat2;
  }


  /**
   *  Gets the SubCat3 attribute of the Ticket object
   *
   *@return    The SubCat3 value
   *@since
   */
  public int getSubCat3() {
    return subCat3;
  }


  /**
   *  Gets the Newticketlogentry attribute of the Ticket object
   *
   *@return    The Newticketlogentry value
   *@since
   */
  public String getNewticketlogentry() {
    return comment;
  }


  /**
   *  Gets the AssignedTo attribute of the Ticket object
   *
   *@return    The AssignedTo value
   *@since
   */
  public int getAssignedTo() {
    return assignedTo;
  }


  /**
   *  Gets the assigned attribute of the Ticket object
   *
   *@return    The assigned value
   */
  public boolean isAssigned() {
    return (assignedTo > 0);
  }


  /**
   *  Gets the assignedDate attribute of the Ticket object
   *
   *@return    The assignedDate value
   */
  public java.sql.Timestamp getAssignedDate() {
    return assignedDate;
  }


  /**
   *  Gets the CloseIt attribute of the Ticket object
   *
   *@return    The CloseIt value
   *@since
   */
  public boolean getCloseIt() {
    return closeIt;
  }


  /**
   *  Gets the SeverityName attribute of the Ticket object
   *
   *@return    The SeverityName value
   *@since
   */
  public String getSeverityName() {
    return severityName;
  }


  /**
   *  Gets the PriorityName attribute of the Ticket object
   *
   *@return    The PriorityName value
   *@since
   */
  public String getPriorityName() {
    return priorityName;
  }


  /**
   *  Gets the DepartmentName attribute of the Ticket object
   *
   *@return    The DepartmentName value
   *@since
   */
  public String getDepartmentName() {
    return departmentName;
  }


  /**
   *  Gets the campaignId attribute of the Ticket object
   *
   *@return    The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   *  Gets the History attribute of the Ticket object
   *
   *@return    The History value
   *@since
   */
  public TicketLogList getHistory() {
    return history;
  }


  /**
   *  Gets the files attribute of the Ticket object
   *
   *@return    The files value
   */
  public FileItemList getFiles() {
    return files;
  }


  /**
   *  Gets the AgeOf attribute of the Ticket object
   *
   *@return    The AgeOf value
   *@since
   */
  public String getAgeOf() {
    return ageDays + "d " + ageHours + "h";
  }


  /**
   *  Gets the CategoryName attribute of the Ticket object
   *
   *@return    The CategoryName value
   *@since
   */
  public String getCategoryName() {
    return categoryName;
  }


  /**
   *  Gets the CompanyName attribute of the Ticket object
   *
   *@return    The CompanyName value
   *@since
   */
  public String getCompanyName() {
    return companyName;
  }


  /**
   *  Gets the ErrorMessage attribute of the Ticket object
   *
   *@return    The ErrorMessage value
   *@since
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   *  Gets the Id attribute of the Ticket object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the OrgId attribute of the Ticket object
   *
   *@return    The OrgId value
   *@since
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the contractId attribute of the Ticket object
   *
   *@return    The contractId value
   */
  public int getContractId() {
    return contractId;
  }


  /**
   *  Gets the serviceContractNumber attribute of the Ticket object
   *
   *@return    The serviceContractNumber value
   */
  public String getServiceContractNumber() {
    return serviceContractNumber;
  }


  /**
   *  Gets the totalHoursRemaining attribute of the Ticket object
   *
   *@return    The totalHoursRemaining value
   */
  public double getTotalHoursRemaining() {
    return round(totalHoursRemaining, 2);
  }


  /**
   *  Gets the contractStartDate attribute of the Ticket object
   *
   *@return    The contractStartDate value
   */
  public java.sql.Timestamp getContractStartDate() {
    return contractStartDate;
  }


  /**
   *  Gets the contractEndDate attribute of the Ticket object
   *
   *@return    The contractEndDate value
   */
  public java.sql.Timestamp getContractEndDate() {
    return contractEndDate;
  }


  /**
   *  Gets the contractOnsiteResponseModel attribute of the Ticket object
   *
   *@return    The contractOnsiteResponseModel value
   */
  public int getContractOnsiteResponseModel() {
    return contractOnsiteResponseModel;
  }


  /**
   *  Gets the assetId attribute of the Ticket object
   *
   *@return    The assetId value
   */
  public int getAssetId() {
    return assetId;
  }


  /**
   *  Gets the assetSerialNumber attribute of the Ticket object
   *
   *@return    The assetSerialNumber value
   */
  public String getAssetSerialNumber() {
    return assetSerialNumber;
  }


  /**
   *  Gets the assetManufacturer attribute of the Ticket object
   *
   *@return    The assetManufacturer value
   */
  public String getAssetManufacturer() {
    return assetManufacturer;
  }


  /**
   *  Gets the assetVendor attribute of the Ticket object
   *
   *@return    The assetVendor value
   */
  public String getAssetVendor() {
    return assetVendor;
  }


  /**
   *  Gets the modelVersion attribute of the Ticket object
   *
   *@return    The modelVersion value
   */
  public String getAssetModelVersion() {
    return assetModelVersion;
  }


  /**
   *  Gets the location attribute of the Ticket object
   *
   *@return    The location value
   */
  public String getAssetLocation() {
    return assetLocation;
  }


  /**
   *  Gets the assetOnsiteResponseModel attribute of the Ticket object
   *
   *@return    The assetOnsiteResponseModel value
   */
  public int getAssetOnsiteResponseModel() {
    return assetOnsiteResponseModel;
  }


  /**
   *  Gets the ContactId attribute of the Ticket object
   *
   *@return    The ContactId value
   *@since
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the Problem attribute of the Ticket object
   *
   *@return    The Problem value
   *@since
   */
  public String getProblem() {
    return problem;
  }


  /**
   *  Gets the location attribute of the Ticket object
   *
   *@return    The location value
   */
  public String getLocation() {
    return location;
  }

  /**
   *  Gets the problemHeader attribute of the Ticket object
   *
   *@return    The problemHeader value
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
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void checkEnabledOwnerAccount(Connection db) throws SQLException {
    if (this.getAssignedTo() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM access " +
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
   *@return    The Comment value
   *@since
   */
  public String getComment() {
    return comment;
  }


  /**
   *  Gets the estimatedResolutionDate attribute of the Ticket object
   *
   *@return    The estimatedResolutionDate value
   */
  public java.sql.Timestamp getEstimatedResolutionDate() {
    return estimatedResolutionDate;
  }


  /**
   *  Gets the cause attribute of the Ticket object
   *
   *@return    The cause value
   */
  public String getCause() {
    return cause;
  }


  /**
   *  Gets the Solution attribute of the Ticket object
   *
   *@return    The Solution value
   *@since
   */
  public String getSolution() {
    return solution;
  }


  /**
   *  Gets the PriorityCode attribute of the Ticket object
   *
   *@return    The PriorityCode value
   *@since
   */
  public int getPriorityCode() {
    return priorityCode;
  }


  /**
   *  Gets the LevelCode attribute of the Ticket object
   *
   *@return    The LevelCode value
   *@since
   */
  public int getLevelCode() {
    return levelCode;
  }


  /**
   *  Gets the DepartmentCode attribute of the Ticket object
   *
   *@return    The DepartmentCode value
   *@since
   */
  public int getDepartmentCode() {
    return departmentCode;
  }


  /**
   *  Gets the SourceCode attribute of the Ticket object
   *
   *@return    The SourceCode value
   *@since
   */
  public int getSourceCode() {
    return sourceCode;
  }


  /**
   *  Gets the CatCode attribute of the Ticket object
   *
   *@return    The CatCode value
   *@since
   */
  public int getCatCode() {
    return catCode;
  }


  /**
   *  Gets the SeverityCode attribute of the Ticket object
   *
   *@return    The SeverityCode value
   *@since
   */
  public int getSeverityCode() {
    return severityCode;
  }


  /**
   *  Gets the resolutionDate attribute of the Ticket object
   *
   *@return    The resolutionDate value
   */
  public java.sql.Timestamp getResolutionDate() {
    return resolutionDate;
  }


  /**
   *  Gets the closedDateTimeString attribute of the Ticket object
   *
   *@return    The closedDateTimeString value
   */
  public String getClosedDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(closed);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the EnteredBy attribute of the Ticket object
   *
   *@return    The EnteredBy value
   *@since
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the Ticket object
   *
   *@return    The ModifiedBy value
   *@since
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildContactInformation(Connection db) throws SQLException {
    if (contactId > -1) {
      thisContact = new Contact(db, contactId + "");
    }
  }


  /**
   *  Inserts this ticket into the database, and populates this Id. Inserts
   *  required fields, then calls update to finish record entry
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }
    StringBuffer sql = new StringBuffer();
    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO ticket (contact_id, problem, pri_code, " +
          "department_code, cat_code, scode, org_id, link_contract_id, link_asset_id, expectation, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
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
      id = DatabaseUtils.getCurrVal(db, "ticket_ticketid_seq");
      //Update the rest of the fields
      if (this.getEntered() == null) {
        this.update(db);
      } else {
        this.update(db, true);
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
   *@param  db                Description of Parameter
   *@param  override          Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    if (!isValid(db)) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE ticket " +
        "SET link_contract_id = ?, link_asset_id = ?, department_code = ?, " +
        "pri_code = ?, scode = ?, " +
        "cat_code = ?, assigned_to = ?, " +
        "subcat_code1 = ?, subcat_code2 = ?, subcat_code3 = ?, " +
        "source_code = ?, contact_id = ?, problem = ?, ");
    if (override == false) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
    }
    if (this.getCloseIt()) {
      sql.append("closed = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    } else {
      if (closed != null) {
        sql.append("closed = ?, ");
      }
    }
    sql.append("solution = ?, location = ?, assigned_date = ?, " +
        "est_resolution_date = ?, resolution_date = ?, cause = ?, expectation = ? " +
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
    if (override == false) {
      pst.setInt(++i, this.getModifiedBy());
    }
    if (!this.getCloseIt() && closed != null) {
      pst.setTimestamp(++i, closed);
    }
    pst.setString(++i, this.getSolution());
    pst.setString(++i, location);
    DatabaseUtils.setTimestamp(pst, ++i, assignedDate);
    DatabaseUtils.setTimestamp(pst, ++i, estimatedResolutionDate);
    DatabaseUtils.setTimestamp(pst, ++i, resolutionDate);
    pst.setString(++i, cause);
    DatabaseUtils.setInt(pst, ++i, expectation);
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
      thisEntry.setTicketId(this.getId());
      thisEntry.setClosed(true);
      thisEntry.process(db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
    }
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  newOwner          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int reopen(Connection db) throws SQLException {
    int resultCount = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = null;
      String sql =
          "UPDATE ticket " +
          "SET closed = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ? " +
          "WHERE ticketid = ? ";
      int i = 0;
      pst = db.prepareStatement(sql);
      pst.setNull(++i, java.sql.Types.TIMESTAMP);
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getId());
      resultCount = pst.executeUpdate();
      pst.close();
      //Update the ticket log
      TicketLog thisEntry = new TicketLog();
      thisEntry.setEnteredBy(this.getModifiedBy());
      thisEntry.setDepartmentCode(this.getDepartmentCode());
      thisEntry.setAssignedTo(this.getAssignedTo());
      thisEntry.setPriorityCode(this.getPriorityCode());
      thisEntry.setSeverityCode(this.getSeverityCode());
      thisEntry.setEntryText(this.getComment());
      thisEntry.setTicketId(this.getId());
      thisEntry.process(db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
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
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    String sql = null;
    DependencyList dependencyList = new DependencyList();
    //Check for action list links
    ActionList actionList = ActionItemLogList.isItemLinked(db, this.getId(), Constants.TICKET_OBJECT);
    if (actionList != null) {
      Dependency thisDependency = new Dependency();
      thisDependency.setName(actionList.getDescription());
      thisDependency.setCount(1);
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
          thisDependency.setName("Tasks");
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
    docDependency.setName("Documents");
    docDependency.setCount(FileItemList.retrieveRecordCount(db, Constants.DOCUMENTS_TICKETS, this.getId()));
    docDependency.setCanDelete(true);
    dependencyList.add(docDependency);
    //Check for folders
    Dependency folderDependency = new Dependency();
    folderDependency.setName("Folders");
    folderDependency.setCount(CustomFieldRecordList.retrieveRecordCount(db, Constants.FOLDERS_TICKETS, this.getId()));
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
          thisDependency.setName("Maintenance Notes");
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
          thisDependency.setName("Activities");
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
   *@param  db                Description of Parameter
   *@param  baseFilePath      Description of the Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Ticket ID not specified.");
    }
    try {
      db.setAutoCommit(false);
      //delete any related action list items
      ActionItemLog.deleteLink(db, this.getId(), Constants.TICKET_OBJECT);

      //Delete any documents
      FileItemList fileList = new FileItemList();
      fileList.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
      fileList.setLinkItemId(this.getId());
      fileList.buildList(db);
      fileList.delete(db, baseFilePath);
      fileList = null;

      //Delete any folder data
      CustomFieldRecordList folderList = new CustomFieldRecordList();
      folderList.setLinkModuleId(Constants.FOLDERS_TICKETS);
      folderList.setLinkItemId(this.getId());
      folderList.buildList(db);
      folderList.delete(db);
      folderList = null;

      //delete all history data
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM ticketlog WHERE ticketid = ?");
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

      //delete the ticket
      pst = db.prepareStatement(
          "DELETE FROM ticket WHERE ticketid = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public int update(Connection db) throws SQLException {
    int i = -1;
    if (!isValid(db)) {
      return -1;
    }
    try {
      db.setAutoCommit(false);
      if (entered != null) {
        i = this.update(db, false);
      } else {
        i = this.update(db, true);
      }
      TicketLog thisEntry = new TicketLog();
      thisEntry.setEnteredBy(this.getModifiedBy());
      thisEntry.setDepartmentCode(this.getDepartmentCode());
      thisEntry.setAssignedTo(this.getAssignedTo());
      thisEntry.setEntryText(this.getComment());
      thisEntry.setTicketId(this.getId());
      thisEntry.setPriorityCode(this.getPriorityCode());
      thisEntry.setSeverityCode(this.getSeverityCode());
      if (this.getCloseIt() == true) {
        thisEntry.setClosed(true);
      }
      history.add(thisEntry);
      Iterator hist = history.iterator();
      while (hist.hasNext()) {
        TicketLog thisLog = (TicketLog) hist.next();
        thisLog.process(db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
      }
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
   *  Gets the Valid attribute of the Ticket object
   *
   *@param  db                Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected boolean isValid(Connection db) throws SQLException {
    errors.clear();
    if (problem == null || problem.trim().equals("")) {
      errors.put("problemError", "An issue is required");
    }
    if (closeIt == true && (solution == null || solution.trim().equals(""))) {
      errors.put("closedError", "A solution is required when closing a ticket");
    }
    if (contactId == -1) {
      errors.put("contactIdError", "You must associate a Contact with a Ticket");
    }
    if (hasErrors()) {
      return false;
    } else {
      return true;
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
    expectation = DatabaseUtils.getInt(rs, "expectation");
    //organization table
    companyName = rs.getString("orgname");
    companyEnabled = rs.getBoolean("orgenabled");

    //lookup_department table
    departmentName = rs.getString("dept");

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
    contractOnsiteResponseModel = DatabaseUtils.getInt(rs, "contractonsiteservicemodel");

    //asset table
    assetSerialNumber = rs.getString("serialnumber");
    assetManufacturer = rs.getString("assetmanufacturer");
    assetVendor = rs.getString("assetvendor");
    assetModelVersion = rs.getString("modelversion");
    assetLocation = rs.getString("assetlocation");
    assetOnsiteResponseModel = DatabaseUtils.getInt(rs, "assetonsiteservicemodel");

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
        float ageCheck = ((System.currentTimeMillis() - entered.getTime()) / 3600000);
        int totalHours = java.lang.Math.round(ageCheck);
        ageDays = java.lang.Math.round(totalHours / 24);
        ageHours = java.lang.Math.round(totalHours - (24 * ageDays));
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasFiles() {
    return (files != null && files.size() > 0);
  }


  /**
   *  Gets the properties that are TimeZone sensitive for auto-populating
   *
   *@return    The timeZoneParams value
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
}

