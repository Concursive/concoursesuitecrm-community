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
package org.aspcfs.modules.servicecontracts.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Service Contracts are contractual agreements with accounts (a.k.a.
 * organizations) that specify the service model, start and end dates, hours
 * and other information. An account can have number of service contracts
 *
 * @author kbhoopal
 * @version $Id: ServiceContract.java,v 1.1.2.2 2004/01/08 18:50:49 kbhoopal
 *          Exp $
 * @created December 31, 2003
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
  //special case as -1 is also valid
  private double totalHoursRemaining = 0;
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
  private String serviceModelNotes = null;
  private ArrayList productList = null;
  private ServiceContractProductList serviceContractProductList = null;
  private String initialStartDateTimeZone = null;
  private String currentStartDateTimeZone = null;
  private String currentEndDateTimeZone = null;
  private java.sql.Timestamp trashedDate = null;
  //special case as -1 is also valid
  private double adjustmentHours = 0;
  private int adjustmentReason = -1;
  private String adjustmentNotes = null;
  private double netHours = 0.0;


  /**
   * Constructor for the ServiceContract object
   */
  public ServiceContract() {
    errors.clear();
  }


  /**
   * Constructor for the ServiceContract object
   *
   * @param db   Description of the Parameter
   * @param scId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ServiceContract(Connection db, String scId) throws SQLException {
    errors.clear();
    queryRecord(db, Integer.parseInt(scId));
  }


  /**
   * Constructor for the ServiceContract object
   *
   * @param db   Description of the Parameter
   * @param scId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ServiceContract(Connection db, int scId) throws SQLException {
    errors.clear();
    queryRecord(db, scId);
  }


  /**
   * Constructor for the ServiceContract object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ServiceContract(ResultSet rs) throws SQLException {
    errors.clear();
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the ServiceContract object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ServiceContract object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the serviceContractNumber attribute of the ServiceContract object
   *
   * @param tmp The new serviceContractNumber value
   */
  public void setServiceContractNumber(String tmp) {
    this.serviceContractNumber = tmp;
  }


  /**
   * Sets the orgId attribute of the ServiceContract object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the ServiceContract object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the contractValue attribute of the ServiceContract object
   *
   * @param tmp The new contractValue value
   */
  public void setContractValue(double tmp) {
    this.contractValue = tmp;
  }


  /**
   * Sets the contractValue attribute of the ServiceContract object
   *
   * @param tmp The new contractValue value
   */
  public void setContractValue(String tmp) {
    this.contractValue = Double.parseDouble(tmp);
  }


  /**
   * Sets the initialStartDate attribute of the ServiceContract object
   *
   * @param tmp The new initialStartDate value
   */
  public void setInitialStartDate(java.sql.Timestamp tmp) {
    this.initialStartDate = tmp;
  }


  /**
   * Sets the initialStartDate attribute of the ServiceContract object
   *
   * @param tmp The new initialStartDate value
   */
  public void setInitialStartDate(String tmp) {
    this.initialStartDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   * Sets the currentStartDate attribute of the ServiceContract object
   *
   * @param tmp The new currentStartDate value
   */
  public void setCurrentStartDate(java.sql.Timestamp tmp) {
    this.currentStartDate = tmp;
  }


  /**
   * Sets the currentStartDate attribute of the ServiceContract object
   *
   * @param tmp The new currentStartDate value
   */
  public void setCurrentStartDate(String tmp) {
    this.currentStartDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   * Sets the currentEndDate attribute of the ServiceContract object
   *
   * @param tmp The new currentEndDate value
   */
  public void setCurrentEndDate(java.sql.Timestamp tmp) {
    this.currentEndDate = tmp;
  }


  /**
   * Sets the currentEndDate attribute of the ServiceContract object
   *
   * @param tmp The new currentEndDate value
   */
  public void setCurrentEndDate(String tmp) {
    this.currentEndDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   * Sets the category attribute of the ServiceContract object
   *
   * @param tmp The new category value
   */
  public void setCategory(int tmp) {
    this.category = tmp;
  }


  /**
   * Sets the category attribute of the ServiceContract object
   *
   * @param tmp The new category value
   */
  public void setCategory(String tmp) {
    this.category = Integer.parseInt(tmp);
  }


  /**
   * Sets the type attribute of the ServiceContract object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   * Sets the type attribute of the ServiceContract object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   * Sets the contactId attribute of the ServiceContract object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the ServiceContract object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Sets the description attribute of the ServiceContract object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the contractBillingNotes attribute of the ServiceContract object
   *
   * @param tmp The new contractBillingNotes value
   */
  public void setContractBillingNotes(String tmp) {
    this.contractBillingNotes = tmp;
  }


  /**
   * Sets the totalHoursRemaining attribute of the ServiceContract object
   *
   * @param tmp The new totalHoursRemaining value
   */
  public void setTotalHoursRemaining(double tmp) {
    this.totalHoursRemaining = tmp;
  }


  /**
   * Sets the totalHoursRemaining attribute of the ServiceContract object
   *
   * @param tmp The new totalHoursRemaining value
   */
  public void setTotalHoursRemaining(String tmp) {
    this.totalHoursRemaining = Double.parseDouble(tmp);
  }


  /**
   * Sets the responseTime attribute of the ServiceContract object
   *
   * @param tmp The new responseTime value
   */
  public void setResponseTime(int tmp) {
    this.responseTime = tmp;
  }


  /**
   * Sets the responseTime attribute of the ServiceContract object
   *
   * @param tmp The new responseTime value
   */
  public void setResponseTime(String tmp) {
    this.responseTime = Integer.parseInt(tmp);
  }


  /**
   * Sets the telephoneResponseModel attribute of the ServiceContract object
   *
   * @param tmp The new telephoneResponseModel value
   */
  public void setTelephoneResponseModel(int tmp) {
    this.telephoneResponseModel = tmp;
  }


  /**
   * Sets the telephoneResponseModel attribute of the ServiceContract object
   *
   * @param tmp The new telephoneResponseModel value
   */
  public void setTelephoneResponseModel(String tmp) {
    this.telephoneResponseModel = Integer.parseInt(tmp);
  }


  /**
   * Sets the onsiteResponseModel attribute of the ServiceContract object
   *
   * @param tmp The new onsiteResponseModel value
   */
  public void setOnsiteResponseModel(int tmp) {
    this.onsiteResponseModel = tmp;
  }


  /**
   * Sets the onsiteResponseModel attribute of the ServiceContract object
   *
   * @param tmp The new onsiteResponseModel value
   */
  public void setOnsiteResponseModel(String tmp) {
    this.onsiteResponseModel = Integer.parseInt(tmp);
  }


  /**
   * Sets the emailResponseModel attribute of the ServiceContract object
   *
   * @param tmp The new emailResponseModel value
   */
  public void setEmailResponseModel(int tmp) {
    this.emailResponseModel = tmp;
  }


  /**
   * Sets the emailResponseModel attribute of the ServiceContract object
   *
   * @param tmp The new emailResponseModel value
   */
  public void setEmailResponseModel(String tmp) {
    this.emailResponseModel = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the ServiceContract object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the ServiceContract object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the enteredBy attribute of the ServiceContract object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the ServiceContract object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the ServiceContract object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the ServiceContract object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the ServiceContract object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the ServiceContract object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the ServiceContract object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ServiceContract object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the products attribute of the ServiceContract object
   *
   * @param tmpProducts The new products value
   */
  public void setProductList(ArrayList tmpProducts) {
    this.productList = tmpProducts;
  }


  /**
   * Sets the productList attribute of the ServiceContract object
   *
   * @param criteriaString The new productList value
   */
  public void setProductList(String[] criteriaString) {
    if (criteriaString != null) {
      productList = new ArrayList(Arrays.asList(criteriaString));
    } else {
      productList = new ArrayList();
    }
  }


  /**
   * Sets the serviceContractProductList attribute of the ServiceContract
   * object
   *
   * @param tmp The new serviceContractProductList value
   */
  public void setServiceContractProductList(ServiceContractProductList tmp) {
    this.serviceContractProductList = tmp;
  }


  /**
   * Adds a feature to the Product attribute of the ServiceContract object
   *
   * @param productId The feature to be added to the Product attribute
   */
  public void addProduct(int productId) {
    if (productList == null) {
      productList = new ArrayList();
    }
    productList.add(String.valueOf(productId));
  }


  /**
   * Adds a feature to the Type attribute of the ServiceContract object
   *
   * @param productId The feature to be added to the Type attribute
   */
  public void addType(String productId) {
    if (productList == null) {
      productList = new ArrayList();
    }
    productList.add(productId);
  }


  /**
   * Sets the initialStartDateTimeZone attribute of the ServiceContract object
   *
   * @param tmp The new initialStartDateTimeZone value
   */
  public void setInitialStartDateTimeZone(String tmp) {
    this.initialStartDateTimeZone = tmp;
  }


  /**
   * Sets the currentStartDateTimeZone attribute of the ServiceContract object
   *
   * @param tmp The new currentStartDateTimeZone value
   */
  public void setCurrentStartDateTimeZone(String tmp) {
    this.currentStartDateTimeZone = tmp;
  }


  /**
   * Sets the currentEndDateTimeZone attribute of the ServiceContract object
   *
   * @param tmp The new currentEndDateTimeZone value
   */
  public void setCurrentEndDateTimeZone(String tmp) {
    this.currentEndDateTimeZone = tmp;
  }


  /**
   * Sets the trashedDate attribute of the ServiceContract object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the ServiceContract object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the trashedDate attribute of the ServiceContract object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }

  public boolean isTrashed() {
    return (trashedDate != null);
  }


  /**
   * Gets the initialStartDateTimeZone attribute of the ServiceContract object
   *
   * @return The initialStartDateTimeZone value
   */
  public String getInitialStartDateTimeZone() {
    return initialStartDateTimeZone;
  }


  /**
   * Gets the currentStartDateTimeZone attribute of the ServiceContract object
   *
   * @return The currentStartDateTimeZone value
   */
  public String getCurrentStartDateTimeZone() {
    return currentStartDateTimeZone;
  }


  /**
   * Gets the currentEndDateTimeZone attribute of the ServiceContract object
   *
   * @return The currentEndDateTimeZone value
   */
  public String getCurrentEndDateTimeZone() {
    return currentEndDateTimeZone;
  }


  /**
   * Gets the products attribute of the ServiceContract object
   *
   * @return The products value
   */
  public ArrayList getProductList() {
    return productList;
  }


  /**
   * Gets the serviceContractProductList attribute of the ServiceContract
   * object
   *
   * @return The serviceContractProductList value
   */
  public ServiceContractProductList getServiceContractProductList() {
    return serviceContractProductList;
  }


  /**
   * Sets the override attribute of the ServiceContract object
   *
   * @param tmp The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   * Sets the override attribute of the ServiceContract object
   *
   * @param tmp The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the serviceModelNotes attribute of the ServiceContract object
   *
   * @param tmp The new serviceModelNotes value
   */
  public void setServiceModelNotes(String tmp) {
    this.serviceModelNotes = tmp;
  }


  /**
   * Sets the adjustmentHours attribute of the ServiceContract object
   *
   * @param tmp The new adjustmentHours value
   */
  public void setAdjustmentHours(double tmp) {
    this.adjustmentHours = tmp;
  }


  /**
   * Sets the adjustmentHours attribute of the ServiceContract object
   *
   * @param tmp The new adjustmentHours value
   */
  public void setAdjustmentHours(String tmp) {
    this.adjustmentHours = Double.parseDouble(tmp);
  }


  /**
   * Sets the adjustmentReason attribute of the ServiceContract object
   *
   * @param tmp The new adjustmentReason value
   */
  public void setAdjustmentReason(int tmp) {
    this.adjustmentReason = tmp;
  }


  /**
   * Sets the adjustmentReason attribute of the ServiceContract object
   *
   * @param tmp The new adjustmentReason value
   */
  public void setAdjustmentReason(String tmp) {
    this.adjustmentReason = Integer.parseInt(tmp);
  }


  /**
   * Sets the adjustmentNotes attribute of the ServiceContract object
   *
   * @param tmp The new adjustmentNotes value
   */
  public void setAdjustmentNotes(String tmp) {
    this.adjustmentNotes = tmp;
  }


  /**
   * Sets the netHours attribute of the ServiceContract object
   *
   * @param tmp The new netHours value
   */
  public void setNetHours(String tmp) {
    this.netHours = Double.parseDouble(tmp);
  }


  /**
   * Sets the netHours attribute of the ServiceContract object
   *
   * @param tmp The new netHours value
   */
  public void setNetHours(double tmp) {
    this.netHours = tmp;
  }


  /**
   * Gets the id attribute of the ServiceContract object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the serviceContractNumber attribute of the ServiceContract object
   *
   * @return The serviceContractNumber value
   */
  public String getServiceContractNumber() {
    return serviceContractNumber;
  }


  /**
   * Gets the orgId attribute of the ServiceContract object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Gets the contractValue attribute of the ServiceContract object
   *
   * @return The contractValue value
   */
  public double getContractValue() {
    return contractValue;
  }


  /**
   * Gets the initialStartDate attribute of the ServiceContract object
   *
   * @return The initialStartDate value
   */
  public java.sql.Timestamp getInitialStartDate() {
    return initialStartDate;
  }


  /**
   * Gets the initialStartDateString attribute of the ServiceContract object
   *
   * @return The initialStartDateString value
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
   * Gets the currentStartDate attribute of the ServiceContract object
   *
   * @return The currentStartDate value
   */
  public java.sql.Timestamp getCurrentStartDate() {
    return currentStartDate;
  }


  /**
   * Gets the currentStartDateString attribute of the ServiceContract object
   *
   * @return The currentStartDateString value
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
   * Gets the currentEndDate attribute of the ServiceContract object
   *
   * @return The currentEndDate value
   */
  public java.sql.Timestamp getCurrentEndDate() {
    return currentEndDate;
  }


  /**
   * Gets the currentEndDateString attribute of the ServiceContract object
   *
   * @return The currentEndDateString value
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
   * Gets the category attribute of the ServiceContract object
   *
   * @return The category value
   */
  public int getCategory() {
    return category;
  }


  /**
   * Gets the type attribute of the ServiceContract object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }


  /**
   * Gets the contactId attribute of the ServiceContract object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Gets the description attribute of the ServiceContract object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the contractBillingNotes attribute of the ServiceContract object
   *
   * @return The contractBillingNotes value
   */
  public String getContractBillingNotes() {
    return contractBillingNotes;
  }


  /**
   * Gets the totalHoursRemaining attribute of the ServiceContract object
   *
   * @return The totalHoursRemaining value
   */
  public double getTotalHoursRemaining() {
    return round(totalHoursRemaining, 2);
  }


  /**
   * Gets the responseTime attribute of the ServiceContract object
   *
   * @return The responseTime value
   */
  public int getResponseTime() {
    return responseTime;
  }


  /**
   * Gets the telephoneResponseModel attribute of the ServiceContract object
   *
   * @return The telephoneResponseModel value
   */
  public int getTelephoneResponseModel() {
    return telephoneResponseModel;
  }


  /**
   * Gets the onsiteResponseModel attribute of the ServiceContract object
   *
   * @return The onsiteResponseModel value
   */
  public int getOnsiteResponseModel() {
    return onsiteResponseModel;
  }


  /**
   * Gets the emailResponseModel attribute of the ServiceContract object
   *
   * @return The emailResponseModel value
   */
  public int getEmailResponseModel() {
    return emailResponseModel;
  }


  /**
   * Gets the entered attribute of the ServiceContract object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the ServiceContract object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the ServiceContract object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the ServiceContract object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the enabled attribute of the ServiceContract object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the override attribute of the ServiceContract object
   *
   * @return The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   * Gets the serviceModelNotes attribute of the ServiceContract object
   *
   * @return The serviceModelNotes value
   */
  public String getServiceModelNotes() {
    return serviceModelNotes;
  }


  /**
   * Gets the adjustmentHours attribute of the ServiceContract object
   *
   * @return The adjustmentHours value
   */
  public double getAdjustmentHours() {
    return adjustmentHours;
  }


  /**
   * Gets the adjustmentReason attribute of the ServiceContract object
   *
   * @return The adjustmentReason value
   */
  public int getAdjustmentReason() {
    return adjustmentReason;
  }


  /**
   * Gets the adjustmentHoursNotes attribute of the ServiceContract object
   *
   * @return The adjustmentHoursNotes value
   */
  public String getAdjustmentNotes() {
    return adjustmentNotes;
  }


  /**
   * Gets the netHours attribute of the ServiceContract object
   *
   * @return The netHours value
   */
  public double getNetHours() {
    return netHours;
  }


  /**
   * Gets the properties that are TimeZone sensitive
   *
   * @return The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("initialStartDate");
    thisList.add("currentStartDate");
    thisList.add("currentEndDate");
    return thisList;
  }


  /**
   * Gets the numberParams attribute of the ServiceContract class
   *
   * @return The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("contractValue");
    return thisList;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;
    //May Need additional checks if a ticket is assigned based on this contract
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE service_contract " +
        "SET " +
        "contract_number = ? , " +
        "account_id = ? , " +
        "contract_value = ? , " +
        "initial_start_date = ? , " +
        "initial_start_date_timezone = ? , " +
        "current_start_date = ? , " +
        "current_start_date_timezone = ? , " +
        "current_end_date = ? , " +
        "current_end_date_timezone = ? , " +
        "category = ? , " +
        "type = ? , " +
        "contact_id = ? , " +
        "description = ? , " +
        "contract_billing_notes = ? , " +
        "total_hours_remaining = ? , " +
        "response_time = ? , " +
        "telephone_service_model= ? , " +
        "onsite_service_model = ? , " +
        "email_service_model = ? , " +
        "service_model_notes = ? , " +
        "trashed_date = ? ");

    if (!override) {
      sql.append(
          " , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE contract_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setString(++i, serviceContractNumber);
    pst.setInt(++i, orgId);
    DatabaseUtils.setDouble(pst, ++i, contractValue);
    pst.setTimestamp(++i, initialStartDate);
    pst.setString(++i, this.initialStartDateTimeZone);
    if (currentStartDate == null) {
      pst.setTimestamp(++i, initialStartDate);
    } else {
      pst.setTimestamp(++i, currentStartDate);
    }
    pst.setString(++i, this.currentStartDateTimeZone);
    DatabaseUtils.setTimestamp(pst, ++i, currentEndDate);
    pst.setString(++i, this.currentEndDateTimeZone);
    DatabaseUtils.setInt(pst, ++i, category);
    DatabaseUtils.setInt(pst, ++i, type);
    DatabaseUtils.setInt(pst, ++i, contactId);
    pst.setString(++i, description);
    pst.setString(++i, contractBillingNotes);
    pst.setDouble(++i, totalHoursRemaining);
    DatabaseUtils.setInt(pst, ++i, responseTime);
    DatabaseUtils.setInt(pst, ++i, telephoneResponseModel);
    DatabaseUtils.setInt(pst, ++i, onsiteResponseModel);
    DatabaseUtils.setInt(pst, ++i, emailResponseModel);
    pst.setString(++i, serviceModelNotes);
    DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
    if (!override) {
      pst.setInt(++i, modifiedBy);
    }
    pst.setInt(++i, id);
    if (!override) {
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();

    insertProductList(db);

    //Inserts all the products (labor categories) associated with the service contract
    return resultCount;
  }


  /**
   * Inserts all the products (labor categories) associated with the service
   * contract
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insertProductList(Connection db) throws SQLException {
    //Remove all products, add new list
    ServiceContractProductList scpList = new ServiceContractProductList();
    scpList.setContractId(this.getId());
    scpList.buildList(db);
    scpList.delete(db);
    if (productList != null) {
      for (int k = 0; k < productList.size(); k++) {
        String productId = (String) productList.get(k);
        if (productId != null && !("".equals(productId))) {
          ServiceContractProduct scp = new ServiceContractProduct();
          scp.setContractId(this.getId());
          scp.setProductId(Integer.parseInt(productId));
          scp.insert(db);
        }
      }
    }

    return true;
  }


  /**
   * Updates only the change in the hours remaining in the service contract
   *
   * @param db              Description of the Parameter
   * @param tmpContractId   Description of the Parameter
   * @param tmpHoursChanged Description of the Parameter
   * @throws SQLException Description of the Exception
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
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param toTrash   Description of the Parameter
   * @param tmpUserId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    int count = 0;
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      sql.append(
          "UPDATE service_contract " +
          "SET trashed_date = ? , " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
          "modifiedby = ? " +
          "WHERE contract_id = ? ");
      int i = 0;
      pst = db.prepareStatement(sql.toString());
      if (toTrash) {
        DatabaseUtils.setTimestamp(
            pst, ++i, new Timestamp(System.currentTimeMillis()));
      } else {
        DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
      }
      DatabaseUtils.setInt(pst, ++i, tmpUserId);
      pst.setInt(++i, this.id);
      resultCount = pst.executeUpdate();
      pst.close();
      // Disable the account or the contact history for the service contract
      ContactHistory.trash(
          db, OrganizationHistory.SERVICE_CONTRACT, this.getId(), !toTrash);

      AssetList assetList = new AssetList();
      assetList.setServiceContractId(this.getId());
      if (!toTrash) {
        assetList.setIncludeOnlyTrashed(true);
      }
      assetList.buildList(db);
      assetList.updateStatus(db, toTrash, tmpUserId);

      TicketList ticketList = new TicketList();
      ticketList.setServiceContractId(this.getId());
      if (!toTrash) {
        ticketList.setIncludeOnlyTrashed(true);
      }
      ticketList.buildList(db);
      ticketList.updateStatus(db, toTrash, tmpUserId);
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
   * Finds all dependencies of the service contract to inform the user before
   * the service contract is deleted
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    try {

      Dependency assetDependency = new Dependency();
      assetDependency.setName("assets");
      assetDependency.setCount(
          AssetList.retrieveRecordCount(
              db, Constants.SERVICE_CONTRACTS, this.getId()));
      assetDependency.setCanDelete(true);
      dependencyList.add(assetDependency);

      Dependency ticDependency = new Dependency();
      ticDependency.setName("tickets");
      ticDependency.setCount(
          TicketList.retrieveRecordCount(
              db, Constants.SERVICE_CONTRACTS, this.getId()));
      ticDependency.setCanDelete(true);
      dependencyList.add(ticDependency);

    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   * Seperate method to delete the dependencies and then the object. This
   * seperation allows service contract alone to be deleted when other objects
   * are deleted (for e.g., tickets)
   *
   * @param db           Description of the Parameter
   * @param baseFilePath File path of documents related to a ticket
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Service Contract Id not specified.");
    }
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      // Delete Contact History
      ContactHistory.deleteObject(
          db, OrganizationHistory.SERVICE_CONTRACT, this.getId());
      
      //Service Contracts have  tickets, assets, hours history related  related, so delete them first
      TicketList ticketList = new TicketList();
      ticketList.setServiceContractId(this.getId());
      ticketList.buildList(db);
      ticketList.delete(db, baseFilePath);
      ticketList = null;

      ticketList = new TicketList();
      ticketList.setServiceContractId(this.getId());
      ticketList.setIncludeOnlyTrashed(false);
      ticketList.buildList(db);
      ticketList.delete(db, baseFilePath);
      ticketList = null;

      // Delete assets
      AssetList assetList = new AssetList();
      assetList.setServiceContractId(this.getId());
      assetList.setAllAssets(false);
      assetList.buildList(db);
      assetList.delete(db, baseFilePath);
      assetList = null;

      assetList = new AssetList();
      assetList.setServiceContractId(this.getId());
      assetList.setIncludeOnlyTrashed(true);
      assetList.setAllAssets(false);
      assetList.buildList(db);
      assetList.delete(db, baseFilePath);
      assetList = null;

      //Delete Service Contract Hours History
      ServiceContractHoursList scHoursList = new ServiceContractHoursList();
      scHoursList.setContractId(this.id);
      scHoursList.buildList(db);
      scHoursList.delete(db);
      scHoursList = null;

      //Delete Service Contract Products
      ServiceContractProductList scProductList = new ServiceContractProductList();
      scProductList.setContractId(this.id);
      scProductList.buildList(db);
      scProductList.delete(db);
      scProductList = null;
      // Delete Contact History
      ContactHistory.deleteObject(
          db, OrganizationHistory.SERVICE_CONTRACT, this.getId());

      Statement st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM service_contract " +
          "WHERE contract_id = " + this.getId());
      st.close();

      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "service_contract_contract_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO service_contract " +
        "(" + (id > -1 ? "contract_id, " : "") + "contract_number , " +
        "account_id , " +
        "contract_value , " +
        "initial_start_date , " +
        "initial_start_date_timezone, " +
        "current_start_date , " +
        "current_start_date_timezone, " +
        "current_end_date , " +
        "current_end_date_timezone , " +
        "category , " +
        "type , " +
        "contact_id , " +
        "description , " +
        "contract_billing_notes , " +
        "total_hours_remaining , " +
        "response_time , " +
        "telephone_service_model , " +
        "onsite_service_model , " +
        "email_service_model , " +
        "service_model_notes , " +
        "enteredby , " +
        "modifiedby , " +
        "trashed_date ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, serviceContractNumber);
    pst.setInt(++i, orgId);
    DatabaseUtils.setDouble(pst, ++i, contractValue);
    pst.setTimestamp(++i, initialStartDate);
    pst.setString(++i, this.initialStartDateTimeZone);
    if (currentStartDate == null) {
      pst.setTimestamp(++i, initialStartDate);
    } else {
      pst.setTimestamp(++i, currentStartDate);
    }
    pst.setString(++i, this.currentStartDateTimeZone);
    DatabaseUtils.setTimestamp(pst, ++i, currentEndDate);
    pst.setString(++i, this.currentEndDateTimeZone);
    DatabaseUtils.setInt(pst, ++i, category);
    DatabaseUtils.setInt(pst, ++i, type);
    DatabaseUtils.setInt(pst, ++i, contactId);
    pst.setString(++i, description);
    pst.setString(++i, contractBillingNotes);
    pst.setDouble(++i, totalHoursRemaining);
    DatabaseUtils.setInt(pst, ++i, responseTime);
    DatabaseUtils.setInt(pst, ++i, telephoneResponseModel);
    DatabaseUtils.setInt(pst, ++i, onsiteResponseModel);
    DatabaseUtils.setInt(pst, ++i, emailResponseModel);
    pst.setString(++i, serviceModelNotes);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "service_contract_contract_id_seq", id);
    pst.close();
    //Inserts all the products (labor categories) associated with the service contract
    insertProductList(db);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db            Description of the Parameter
   * @param tmpContractId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int tmpContractId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM service_contract " +
        " WHERE contract_id = ? ");
    pst.setInt(1, tmpContractId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    buildServiceContractProductList(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildServiceContractProductList(Connection db) throws SQLException {
    serviceContractProductList = new ServiceContractProductList();
    serviceContractProductList.setContractId(this.id);
    serviceContractProductList.buildList(db);
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
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
    responseTime = DatabaseUtils.getInt(rs, "response_time");
    telephoneResponseModel = DatabaseUtils.getInt(
        rs, "telephone_service_model");
    onsiteResponseModel = DatabaseUtils.getInt(rs, "onsite_service_model");
    emailResponseModel = DatabaseUtils.getInt(rs, "email_service_model");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    contractValue = DatabaseUtils.getDouble(rs, "contract_value");
    totalHoursRemaining = rs.getDouble("total_hours_remaining");
    serviceModelNotes = rs.getString("service_model_notes");
    initialStartDateTimeZone = rs.getString("initial_start_date_timezone");
    currentStartDateTimeZone = rs.getString("current_start_date_timezone");
    currentEndDateTimeZone = rs.getString("current_end_date_timezone");
    trashedDate = rs.getTimestamp("trashed_date");
  }
}

