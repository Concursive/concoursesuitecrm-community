//Copyright 2004 Dark Horse Ventures

package org.aspcfs.modules.servicecontracts.base;

import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.database.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.*;
import org.aspcfs.controller.*;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.modules.assets.base.AssetList;

/**
 * Service Contracts are contractual agreements with accounts (a.k.a. organizations)
 *  that specify the service model, start and end dates, hours and other information. 
 *  An account can have number of service contracts
 *
 *@author     kbhoopal
 *@created    December 31, 2003
 *@version    $Id: ServiceContract.java,v 1.1.2.2 2004/01/08 18:50:49 kbhoopal
 *      Exp $
 */
public class ServiceContract extends GenericBean {

  private int id = -1;
  private String serviceContractNumber = null;
  private int orgId = -1;
  private double contractValue = -1;
  private java.sql.Timestamp initialStartDate = null;
  private java.sql.Timestamp currentStartDate = null;
  private java.sql.Timestamp currentEndDate = null;
  private int category = -1;
  private int type = -1;
  private int contactId = -1;
  private String description = null;
  private String contractBillingNotes = null;
  private int totalHoursPurchased = -1;
  private double totalHoursRemaining = 0; //special case as -1 is also valid
  private int responseTime = -1;
  private int telephoneResponseModel = -1;
  private int onsiteResponseModel = -1;
  private int emailResponseModel = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private boolean override = false;


  /**
   *  Constructor for the ServiceContract object
   */
  public ServiceContract() { }


  /**
   *  Constructor for the ServiceContract object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ServiceContract(Connection db, String id) throws SQLException {

    queryRecord(db, Integer.parseInt(id));

  }


  /**
   *  Constructor for the ServiceContract object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ServiceContract(ResultSet rs) throws SQLException {

    buildRecord(rs);

  }


  /**
   *  Sets the id attribute of the ServiceContract object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ServiceContract object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the serviceContractNumber attribute of the ServiceContract object
   *
   *@param  tmp  The new serviceContractNumber value
   */
  public void setServiceContractNumber(String tmp) {
    this.serviceContractNumber = tmp;
  }


  /**
   *  Sets the orgId attribute of the ServiceContract object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the ServiceContract object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contractValue attribute of the ServiceContract object
   *
   *@param  tmp  The new contractValue value
   */
  public void setContractValue(double tmp) {
    this.contractValue = tmp;
  }


  /**
   *  Sets the contractValue attribute of the ServiceContract object
   *
   *@param  tmp  The new contractValue value
   */
  public void setContractValue(String tmp) {
    this.contractValue = Double.parseDouble(tmp);
  }


  /**
   *  Sets the initialStartDate attribute of the ServiceContract object
   *
   *@param  tmp  The new initialStartDate value
   */
  public void setInitialStartDate(java.sql.Timestamp tmp) {
    this.initialStartDate = tmp;
  }


  /**
   *  Sets the initialStartDate attribute of the ServiceContract object
   *
   *@param  tmp  The new initialStartDate value
   */
  public void setInitialStartDate(String tmp) {
    this.initialStartDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the currentStartDate attribute of the ServiceContract object
   *
   *@param  tmp  The new currentStartDate value
   */
  public void setCurrentStartDate(java.sql.Timestamp tmp) {
    this.currentStartDate = tmp;
  }


  /**
   *  Sets the currentStartDate attribute of the ServiceContract object
   *
   *@param  tmp  The new currentStartDate value
   */
  public void setCurrentStartDate(String tmp) {
    this.currentStartDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the currentEndDate attribute of the ServiceContract object
   *
   *@param  tmp  The new currentEndDate value
   */
  public void setCurrentEndDate(java.sql.Timestamp tmp) {
    this.currentEndDate = tmp;
  }


  /**
   *  Sets the currentEndDate attribute of the ServiceContract object
   *
   *@param  tmp  The new currentEndDate value
   */
  public void setCurrentEndDate(String tmp) {
    this.currentEndDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the category attribute of the ServiceContract object
   *
   *@param  tmp  The new category value
   */
  public void setCategory(int tmp) {
    this.category = tmp;
  }


  /**
   *  Sets the category attribute of the ServiceContract object
   *
   *@param  tmp  The new category value
   */
  public void setCategory(String tmp) {
    this.category = Integer.parseInt(tmp);
  }


  /**
   *  Sets the type attribute of the ServiceContract object
   *
   *@param  tmp  The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the type attribute of the ServiceContract object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contactId attribute of the ServiceContract object
   *
   *@param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the contactId attribute of the ServiceContract object
   *
   *@param  tmp  The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the description attribute of the ServiceContract object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the contractBillingNotes attribute of the ServiceContract object
   *
   *@param  tmp  The new contractBillingNotes value
   */
  public void setContractBillingNotes(String tmp) {
    this.contractBillingNotes = tmp;
  }


  /**
   *  Sets the totalHoursPurchased attribute of the ServiceContract object
   *
   *@param  tmp  The new totalHoursPurchased value
   */
  public void setTotalHoursPurchased(int tmp) {
    this.totalHoursPurchased = tmp;
  }


  /**
   *  Sets the totalHoursPurchased attribute of the ServiceContract object
   *
   *@param  tmp  The new totalHoursPurchased value
   */
  public void setTotalHoursPurchased(String tmp) {
    this.totalHoursPurchased = Integer.parseInt(tmp);
  }


  /**
   *  Sets the totalHoursRemaining attribute of the ServiceContract object
   *
   *@param  tmp  The new totalHoursRemaining value
   */
  public void setTotalHoursRemaining(double tmp) {
    this.totalHoursRemaining = tmp;
  }


  /**
   *  Sets the totalHoursRemaining attribute of the ServiceContract object
   *
   *@param  tmp  The new totalHoursRemaining value
   */
  public void setTotalHoursRemaining(String tmp) {
    this.totalHoursRemaining = Double.parseDouble(tmp);
  }


  /**
   *  Sets the responseTime attribute of the ServiceContract object
   *
   *@param  tmp  The new responseTime value
   */
  public void setResponseTime(int tmp) {
    this.responseTime = tmp;
  }


  /**
   *  Sets the responseTime attribute of the ServiceContract object
   *
   *@param  tmp  The new responseTime value
   */
  public void setResponseTime(String tmp) {
    this.responseTime = Integer.parseInt(tmp);
  }


  /**
   *  Sets the telephoneResponseModel attribute of the ServiceContract object
   *
   *@param  tmp  The new telephoneResponseModel value
   */
  public void setTelephoneResponseModel(int tmp) {
    this.telephoneResponseModel = tmp;
  }


  /**
   *  Sets the telephoneResponseModel attribute of the ServiceContract object
   *
   *@param  tmp  The new telephoneResponseModel value
   */
  public void setTelephoneResponseModel(String tmp) {
    this.telephoneResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the onsiteResponseModel attribute of the ServiceContract object
   *
   *@param  tmp  The new onsiteResponseModel value
   */
  public void setOnsiteResponseModel(int tmp) {
    this.onsiteResponseModel = tmp;
  }


  /**
   *  Sets the onsiteResponseModel attribute of the ServiceContract object
   *
   *@param  tmp  The new onsiteResponseModel value
   */
  public void setOnsiteResponseModel(String tmp) {
    this.onsiteResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the emailResponseModel attribute of the ServiceContract object
   *
   *@param  tmp  The new emailResponseModel value
   */
  public void setEmailResponseModel(int tmp) {
    this.emailResponseModel = tmp;
  }


  /**
   *  Sets the emailResponseModel attribute of the ServiceContract object
   *
   *@param  tmp  The new emailResponseModel value
   */
  public void setEmailResponseModel(String tmp) {
    this.emailResponseModel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the ServiceContract object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ServiceContract object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the ServiceContract object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ServiceContract object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the ServiceContract object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the ServiceContract object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the ServiceContract object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ServiceContract object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the ServiceContract object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ServiceContract object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the override attribute of the ServiceContract object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the ServiceContract object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the ServiceContract object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the serviceContractNumber attribute of the ServiceContract object
   *
   *@return    The serviceContractNumber value
   */
  public String getServiceContractNumber() {
    return serviceContractNumber;
  }


  /**
   *  Gets the orgId attribute of the ServiceContract object
   *
   *@return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the contractValue attribute of the ServiceContract object
   *
   *@return    The contractValue value
   */
  public double getContractValue() {
    return contractValue;
  }


  /**
   *  Gets the contractValueCurrency attribute of the ServiceContract object
   *
   *@return    The contractValueCurrency value
   */
  public String getContractValueCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(contractValue);
    return amountOut;
  }


  /**
   *  Gets the initialStartDate attribute of the ServiceContract object
   *
   *@return    The initialStartDate value
   */
  public java.sql.Timestamp getInitialStartDate() {
    return initialStartDate;
  }


  /**
   *  Gets the initialStartDateString attribute of the ServiceContract object
   *
   *@return    The initialStartDateString value
   */
  public String getInitialStartDateString() {
    String tmp = "";
    try {
      return initialStartDate.toString();
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the currentStartDate attribute of the ServiceContract object
   *
   *@return    The currentStartDate value
   */
  public java.sql.Timestamp getCurrentStartDate() {
    return currentStartDate;
  }


  /**
   *  Gets the currentStartDateString attribute of the ServiceContract object
   *
   *@return    The currentStartDateString value
   */
  public String getCurrentStartDateString() {
    String tmp = "";
    try {
      return currentStartDate.toString();
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the currentEndDate attribute of the ServiceContract object
   *
   *@return    The currentEndDate value
   */
  public java.sql.Timestamp getCurrentEndDate() {
    return currentEndDate;
  }


  /**
   *  Gets the currentEndDateString attribute of the ServiceContract object
   *
   *@return    The currentEndDateString value
   */
  public String getCurrentEndDateString() {
    String tmp = "";
    try {
      return currentEndDate.toString();
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the category attribute of the ServiceContract object
   *
   *@return    The category value
   */
  public int getCategory() {
    return category;
  }


  /**
   *  Gets the type attribute of the ServiceContract object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the contactId attribute of the ServiceContract object
   *
   *@return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the description attribute of the ServiceContract object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the contractBillingNotes attribute of the ServiceContract object
   *
   *@return    The contractBillingNotes value
   */
  public String getContractBillingNotes() {
    return contractBillingNotes;
  }


  /**
   *  Gets the totalHoursPurchased attribute of the ServiceContract object
   *
   *@return    The totalHoursPurchased value
   */
  public int getTotalHoursPurchased() {
    return totalHoursPurchased;
  }


  /**
   *  Gets the totalHoursRemaining attribute of the ServiceContract object
   *
   *@return    The totalHoursRemaining value
   */
  public double getTotalHoursRemaining() {
    return round(totalHoursRemaining,2);
  }


  /**
   *  Gets the responseTime attribute of the ServiceContract object
   *
   *@return    The responseTime value
   */
  public int getResponseTime() {
    return responseTime;
  }


  /**
   *  Gets the telephoneResponseModel attribute of the ServiceContract object
   *
   *@return    The telephoneResponseModel value
   */
  public int getTelephoneResponseModel() {
    return telephoneResponseModel;
  }


  /**
   *  Gets the onsiteResponseModel attribute of the ServiceContract object
   *
   *@return    The onsiteResponseModel value
   */
  public int getOnsiteResponseModel() {
    return onsiteResponseModel;
  }


  /**
   *  Gets the emailResponseModel attribute of the ServiceContract object
   *
   *@return    The emailResponseModel value
   */
  public int getEmailResponseModel() {
    return emailResponseModel;
  }


  /**
   *  Gets the entered attribute of the ServiceContract object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the ServiceContract object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the ServiceContract object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the ServiceContract object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the enabled attribute of the ServiceContract object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the override attribute of the ServiceContract object
   *
   *@return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Gets the properties that are TimeZone sensitive
   *
   *@return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {

    ArrayList thisList = new ArrayList();
    thisList.add("initialStartDate");
    thisList.add("currentStartDate");
    thisList.add("currentEndDate");

    return thisList;
  }



  /**
   *  Gets the valid attribute of the ServiceContract object
   *
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean isValid() throws SQLException {
    errors.clear();
    if (initialStartDate == null) {
      errors.put("initialStartDateError", "Initial contract date is required");
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
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;
    //May Need additional checks if a ticket is assigned based on this contract
    if (!isValid()) {
      return resultCount;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE service_contract " +
        "SET " +
        "contract_number = ? , " +
        "account_id = ? , " +
        "contract_value = ? , " +
        "initial_start_date = ? , " +
        "current_start_date = ? , " +
        "current_end_date = ? , " +
        "category = ? , " +
        "type = ? , " +
        "contact_id = ? , " +
        "description = ? , " +
        "contract_billing_notes = ? , " +
        "total_hours_purchased = ? , " +
        "total_hours_remaining = ? , " +
        "response_time = ? , " +
        "telephone_service_model= ? , " +
        "onsite_service_model = ? , " +
        "email_service_model = ? ");

    if (!override) {
      sql.append(" , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE contract_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setString(++i, serviceContractNumber);
    pst.setInt(++i, orgId);
    pst.setDouble(++i, contractValue);
    pst.setTimestamp(++i, initialStartDate);
    if (currentStartDate == null) {
      pst.setTimestamp(++i, initialStartDate);
    } else {
      pst.setTimestamp(++i, currentStartDate);
    }
    DatabaseUtils.setTimestamp(pst, ++i, currentEndDate);
    DatabaseUtils.setInt(pst, ++i, category);
    DatabaseUtils.setInt(pst, ++i, type);
    DatabaseUtils.setInt(pst, ++i, contactId);
    pst.setString(++i, description);
    pst.setString(++i, contractBillingNotes);
    DatabaseUtils.setInt(pst, ++i, totalHoursPurchased);
    pst.setDouble(++i, totalHoursRemaining);
    DatabaseUtils.setInt(pst, ++i, responseTime);
    DatabaseUtils.setInt(pst, ++i, telephoneResponseModel);
    DatabaseUtils.setInt(pst, ++i, onsiteResponseModel);
    DatabaseUtils.setInt(pst, ++i, emailResponseModel);
    if (!override) {
      pst.setInt(++i, modifiedBy);
    }
    pst.setInt(++i, id);
    if (!override) {
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Updates only the change in  the hours remaining in the service contract
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void updateHoursRemaining(Connection db, int tmpContractId, double tmpHoursChanged) throws SQLException {
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE service_contract " +
        "SET " +
        "total_hours_remaining = total_hours_remaining + ? " +
        "WHERE contract_id = ? ");

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setDouble(++i, tmpHoursChanged);
    pst.setInt(++i, tmpContractId);
    pst.executeUpdate();
    pst.close();
  }


  /**
   *  Finds all dependencies of the service contract
   *  to inform the user before the service contract is deleted
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    try {

      Dependency assetDependency = new Dependency();
      assetDependency.setName("Assets");
      assetDependency.setCount(AssetList.retrieveRecordCount(db, Constants.SERVICE_CONTRACTS, this.getId()));
      assetDependency.setCanDelete(true);
      dependencyList.add(assetDependency);

      Dependency ticDependency = new Dependency();
      ticDependency.setName("Tickets");
      ticDependency.setCount(TicketList.retrieveRecordCount(db, Constants.SERVICE_CONTRACTS, this.getId()));
      ticDependency.setCanDelete(true);
      dependencyList.add(ticDependency);

    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   *  Seperate method to delete the dependencies and then
   *  the object. This seperation allows service contract alone
   *  to be deleted when other objects are deleted (for e.g., tickets)   
   *
   *@param  db                Description of the Parameter
   *@param  baseFilePath      File path of documents related to a ticket
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean deleteAll(Connection db, String baseFilePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Service Contract Id not specified.");
    }
    try {
      db.setAutoCommit(false);
      //Service Contracts have  tickets, assets, hours history related  related, so delete them first
      TicketList ticketList = new TicketList();
      ticketList.setServiceContractId(this.getId());
      ticketList.buildList(db);
      ticketList.delete(db, baseFilePath);
      ticketList = null;

      // Delete after tickets
      AssetList assetList = new AssetList();
      assetList.setServiceContractId(this.getId());
      assetList.buildList(db);
      assetList.delete(db);
      assetList = null;
      
      delete(db);
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Deletes this object from the database
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {

    //Delete Service Contract Hours History
    ServiceContractHoursList scHoursList = new ServiceContractHoursList();
    scHoursList.setContractId(this.id);
    scHoursList.buildList(db);
    scHoursList.delete(db);
    scHoursList = null;

    Statement st = db.createStatement();
    st.executeUpdate("DELETE FROM service_contract WHERE contract_id = " + this.getId());
    st.close();

  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {

    if (!isValid()) {
      return false;
    }
    PreparedStatement pst = null;
    pst = db.prepareStatement(
        "INSERT INTO service_contract " +
        "(contract_number , " +
        "account_id , " +
        "contract_value , " +
        "initial_start_date , " +
        "current_start_date , " +
        "current_end_date , " +
        "category , " +
        "type , " +
        "contact_id , " +
        "description , " +
        "contract_billing_notes , " +
        "total_hours_purchased , " +
        "total_hours_remaining , " +
        "response_time , " +
        "telephone_service_model , " +
        "onsite_service_model , " +
        "email_service_model , " +
        "enteredby , " +
        "modifiedby ) " +
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    int i = 0;
    pst.setString(++i, serviceContractNumber);
    pst.setInt(++i, orgId);
    pst.setDouble(++i, contractValue);
    pst.setTimestamp(++i, initialStartDate);
    if (currentStartDate == null) {
      pst.setTimestamp(++i, initialStartDate);
    } else {
      pst.setTimestamp(++i, currentStartDate);
    }
    DatabaseUtils.setTimestamp(pst, ++i, currentEndDate);
    DatabaseUtils.setInt(pst, ++i, category);
    DatabaseUtils.setInt(pst, ++i, type);
    DatabaseUtils.setInt(pst, ++i, contactId);
    pst.setString(++i, description);
    pst.setString(++i, contractBillingNotes);
    DatabaseUtils.setInt(pst, ++i, totalHoursPurchased);
    pst.setDouble(++i, totalHoursRemaining);
    DatabaseUtils.setInt(pst, ++i, responseTime);
    DatabaseUtils.setInt(pst, ++i, telephoneResponseModel);
    DatabaseUtils.setInt(pst, ++i, onsiteResponseModel);
    DatabaseUtils.setInt(pst, ++i, emailResponseModel);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "service_contract_contract_id_seq");
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  tmpContractId     Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int tmpContractId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(" SELECT * " +
        " FROM service_contract " +
        " WHERE contract_id = ? ");
    pst.setInt(1, tmpContractId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("contract_id");
    serviceContractNumber = rs.getString("contract_number");
    orgId = rs.getInt("account_id");
    initialStartDate = rs.getTimestamp("initial_start_date");
    currentStartDate = rs.getTimestamp("current_start_date");
    currentEndDate = rs.getTimestamp("current_end_date");
    category = DatabaseUtils.getInt(rs, "category");
    type = DatabaseUtils.getInt(rs, "type");
    contactId = DatabaseUtils.getInt(rs, "contact_id");
    description = rs.getString("description");
    contractBillingNotes = rs.getString("contract_billing_notes");
    totalHoursPurchased = DatabaseUtils.getInt(rs, "total_hours_purchased");
    responseTime = DatabaseUtils.getInt(rs, "response_time");
    telephoneResponseModel = DatabaseUtils.getInt(rs, "telephone_service_model");
    onsiteResponseModel = DatabaseUtils.getInt(rs, "onsite_service_model");
    emailResponseModel = DatabaseUtils.getInt(rs, "email_service_model");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    contractValue = rs.getDouble("contract_value");
    totalHoursRemaining = rs.getDouble("total_hours_remaining");
  }
}
