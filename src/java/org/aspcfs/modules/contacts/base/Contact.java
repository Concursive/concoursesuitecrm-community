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
package org.aspcfs.modules.contacts.base;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import org.apache.log4j.Level;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.actionplans.base.ActionItemWorkList;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.ExcludedRecipient;
import org.aspcfs.modules.communications.base.RecipientList;
import org.aspcfs.modules.communications.base.SurveyResponseList;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import java.sql.*;
import java.text.DateFormat;
import java.util.*;

/**
 * Represents a Contact
 *
 * @author mrajkowski
 * @version $Id: Contact.java,v 1.106.4.3.2.3 2005/01/10 17:58:55 kbhoopal
 *          Exp $
 * @created August 29, 2001
 */
public class Contact extends GenericBean {

  private static final long serialVersionUID = -177466129659625417L;

  /**
   * HTTP API User Mode checks to see if the user has the required permission
   * to execute the corresponding method on the object. If the object can exist
   * in various forms and each form has a unique permission then override
   * method 'getPermission()' eg: Contact can be an Account Contact or General
   * Contact
   */
  protected String permission = "contacts-external_contacts";

  /**
   * Type 1 in the database refers to an Employee
   */
  public final static int EMPLOYEE_TYPE = 1;
  public final static int LEAD_TYPE = 2;
  public final static int LEAD_UNREAD = 0;
  public final static int LEAD_UNPROCESSED = 1;
  public final static int LEAD_ASSIGNED = 2;
  public final static int LEAD_TRASHED = 3;
  public final static int LEAD_WORKED = 4;

  private int id = -1;
  private int orgId = -1;
  private String orgName = "";
  private String company = "";
  private String accountNumber = "";
  private String title = "";
  private int department = -1;
  private String nameSalutation = "";
  private int listSalutation = -1;
  private int orgSiteId = -1;
  private boolean prospectClient = false;
  private boolean noEmail = false;
  private boolean noMail = false;
  private boolean noPhone = false;
  private boolean noTextMessage = false;
  private boolean noInstantMessage = false;
  private boolean noFax = false;
  private String nameFirst = "";
  private String nameMiddle = "";
  private String nameLast = "";
  private String nameSuffix = "";
  private int assistant = -1;
  private String additionalNames = "";
  private String nickname = "";
  private String role = "";
  private java.sql.Timestamp birthDate = null;
  private String site = "";
  private String notes = "";
  private int locale = -1;
  private String employeeId = null;
  private int employmentType = -1;
  private String startOfDay = "";
  private String endOfDay = "";
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private boolean hasAccount = false;
  private boolean excludedFromCampaign = false;
  private int owner = -1;
  private int custom1 = -1;
  private String url = null;
  private int userId = -1;
  private int accessType = -1;
  private int clientId = -1;
  private int statusId = -1;
  private int importId = -1;
  private boolean isLead = false;
  private int leadStatus = -1;
  private int source = -1;
  private int rating = -1;
  private String comments = null;
  private java.sql.Timestamp conversionDate = null;
  private java.sql.Timestamp assignedDate = null;
  private java.sql.Timestamp leadTrashedDate = null;
  private String secretWord = null;
  private double revenue = 0;
  private double potential = 0;
  private int industryTempCode = -1;
  private String city = null;
  private String postalcode = null;
  private int siteId = -1;
  private String siteName = null;

  private ContactEmailAddressList emailAddressList = new ContactEmailAddressList();
  private ContactPhoneNumberList phoneNumberList = new ContactPhoneNumberList();
  private ContactAddressList addressList = new ContactAddressList();
  private ContactTextMessageAddressList textMessageAddressList = new ContactTextMessageAddressList();
  private ContactInstantMessageAddressList instantMessageAddressList = new ContactInstantMessageAddressList();
  private String departmentName = "";
  private String ownerName = "";
  private String enteredByName = "";
  private String modifiedByName = "";
  private String industryName = "";
  private String sourceName = "";
  private String ratingName = "";

  private boolean orgEnabled = true;
  private java.sql.Timestamp orgTrashedDate = null;
  private boolean hasEnabledOwnerAccount = true;
  private boolean hasEnabledAccount = true;

  private boolean primaryContact = false;
  private boolean employee = false;
  private boolean hasOpportunities = false;
  private LookupList types = new LookupList();
  private ArrayList typeList = null;

  private boolean buildDetails = true;
  private boolean buildTypes = true;
  private boolean hasAccess = false;
  private boolean isEnabled = false;

  private java.sql.Timestamp trashedDate = null;
  private boolean forceDelete = false;
  private boolean checkRevertingBackToLead = false;

  private int employees = -1;
  private String dunsType = null;
  private String dunsNumber = null;
  private String businessNameTwo = null;
  private int sicCode = -1;
  private int yearStarted = -1;
  private String sicDescription = null;

  //Logger
  private static long milies = -1;
  //private static Logger logger = Logger.getLogger(Contact.class);

  static {
    if (System.getProperty("DEBUG") != null) {
    //  logger.setLevel(Level.DEBUG);
    }
  }

  //filter used by xml api to retrieve the contact info for a particular user
  private String username = null;

  public void setUsername(String tmp) {
    this.username = tmp;
  }

  public String getUsername() {
    return username;
  }

  /**
   * Gets the permission attribute of the Contact object
   *
   * @return The permission value
   */
  public String getPermission() {
    if (orgId != -1) {
      permission = "accounts-accounts-contacts";
    } else if (employee) {
      permission = "contacts-internal_contacts";
    }
    return permission;
  }


  /**
   * Gets the industryName attribute of the Contact object
   *
   * @return The industryName value
   */
  public String getIndustryName() {
    return industryName;
  }


  /**
   * Gets the sourceName attribute of the Contact object
   *
   * @return The sourceName value
   */
  public String getSourceName() {
    return sourceName;
  }


  /**
   * Gets the ratingName attribute of the Contact object
   *
   * @return The ratingName value
   */
  public String getRatingName() {
    return ratingName;
  }


  /**
   * Gets the instantMessageAddressList attribute of the Contact object
   *
   * @return The instantMessageAddressList value
   */
  public ContactInstantMessageAddressList getInstantMessageAddressList() {
    return instantMessageAddressList;
  }


  /**
   * Sets the instantMessageAddressList attribute of the Contact object
   *
   * @param tmp The new instantMessageAddressList value
   */
  public void setInstantMessageAddressList(ContactInstantMessageAddressList tmp) {
    this.instantMessageAddressList = tmp;
  }


  /**
   * Gets the city attribute of the Contact object
   *
   * @return The city value
   */
  public String getCity() {
    return city;
  }


  /**
   * Sets the city attribute of the Contact object
   *
   * @param tmp The new city value
   */
  public void setCity(String tmp) {
    this.city = tmp;
  }


  /**
   * Gets the postalcode attribute of the Contact object
   *
   * @return The postalcode value
   */
  public String getPostalcode() {
    return postalcode;
  }


  /**
   * Sets the postalcode attribute of the Contact object
   *
   * @param tmp The new postalcode value
   */
  public void setPostalcode(String tmp) {
    this.postalcode = tmp;
  }


  /**
   * Sets the siteId attribute of the Contact object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   * Sets the siteId attribute of the Contact object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the siteId attribute of the Contact object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   * Constructor for the Contact object
   *
   * @since 1.1
   */
  public Contact() {
  }


  /**
   * Constructor for the Contact object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of the Exception
   * @since 1.1
   */
  public Contact(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }



  /**
   * Constructor for the Employee object, populates all attributes by
   * performing a SQL query based on the employeeId parameter
   *
   * @param db        Description of Parameter
   * @param contactId Description of Parameter
   * @throws SQLException Description of the Exception
   * @since 1.1
   */
  public Contact(Connection db, String contactId) throws SQLException {
    queryRecord(db, Integer.parseInt(contactId));
  }


  /**
   * Constructor for the Contact object
   *
   * @param db        Description of the Parameter
   * @param contactId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Contact(Connection db, int contactId) throws SQLException {
    queryRecord(db, contactId);
  }


  /**
   * Description of the Method
   *
   * @param obj Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean equals(Object obj) {
    if (this.getId() == ((Contact) obj).getId()) {
      return true;
    }

    return false;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void build(Connection db) throws SQLException {
    queryRecord(db, this.getId());
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param contactId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int contactId) throws SQLException {
    if (contactId < 0) {
      throw new SQLException("Contact ID not specified.");
    }
    StringBuffer sql = new StringBuffer();
    //NOTE: Update the UserList query if any changes are made to the contact query
    sql.append(
        "SELECT c.*, o.enabled AS orgenabled, o.trashed_date AS orgtrasheddate," +
            " d.description as departmentname, " +
            " ca.city AS city, " +
            " ca.postalcode AS postalcode, " +
            " lsi.description AS site_id_name, " +
            " lind.description AS industry_name, " +
            " lcs.description AS source_name, " +
            " lcr.description AS rating_name " +
            "FROM contact c " +
            "LEFT JOIN organization o ON (c.org_id = o.org_id) " +
            "LEFT JOIN lookup_department d ON (c.department = d.code) " +
            "LEFT JOIN lookup_industry lind ON (c.industry_temp_code = lind.code) " +
            "LEFT JOIN lookup_contact_source lcs ON (c.source = lcs.code) " +
            "LEFT JOIN lookup_contact_rating lcr ON (c.rating = lcr.code) " +
            "LEFT JOIN contact_address ca ON (c.contact_id = ca.contact_id) " +
            "LEFT JOIN lookup_site_id lsi ON (c.site_id = lsi.code) " +
            "WHERE c.contact_id = ? " +
            "AND (ca.address_id IS NULL OR ca.address_id IN ( " +
            "SELECT cta.address_id FROM contact_address cta WHERE cta.contact_id = c.contact_id AND cta.primary_address = ?) " +
            "OR ca.address_id IN (SELECT MIN(ctadd.address_id) FROM contact_address ctadd WHERE ctadd.contact_id = c.contact_id AND " +
            " ctadd.contact_id NOT IN (SELECT contact_id FROM contact_address WHERE contact_address.primary_address = ?))) ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, contactId);
    pst.setBoolean(2, true);
    pst.setBoolean(3, true);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
    if (buildDetails) {
      phoneNumberList.setContactId(this.getId());
      phoneNumberList.buildList(db);
      addressList.setContactId(this.getId());
      addressList.buildList(db);
      emailAddressList.setContactId(this.getId());
      emailAddressList.buildList(db);
      textMessageAddressList.setContactId(this.getId());
      textMessageAddressList.buildList(db);
      instantMessageAddressList.setContactId(this.getId());
      instantMessageAddressList.buildList(db);
    }
    if (buildTypes) {
      buildTypes(db);
    }
  }


  /**
   * Gets the additionalNames attribute of the Contact object
   *
   * @return The additionalNames value
   */
  public String getAdditionalNames() {
    return additionalNames;
  }


  /**
   * Sets the additionalNames attribute of the Contact object
   *
   * @param tmp The new additionalNames value
   */
  public void setAdditionalNames(String tmp) {
    this.additionalNames = tmp;
  }


  /**
   * Gets the nickname attribute of the Contact object
   *
   * @return The nickname value
   */
  public String getNickname() {
    return nickname;
  }


  /**
   * Sets the nickname attribute of the Contact object
   *
   * @param tmp The new nickname value
   */
  public void setNickname(String tmp) {
    this.nickname = tmp;
  }


  /**
   * Gets the role attribute of the Contact object
   *
   * @return The role value
   */
  public String getRole() {
    return role;
  }


  /**
   * Sets the role attribute of the Contact object
   *
   * @param tmp The new role value
   */
  public void setRole(String tmp) {
    this.role = tmp;
  }


  /**
   * Gets the birthDate attribute of the Contact object
   *
   * @return The birthDate value
   */
  public java.sql.Timestamp getBirthDate() {
    return birthDate;
  }


  /**
   * Gets the secretWord attribute of the Contact object
   *
   * @return The secretWord value
   */
  public String getSecretWord() {
    return secretWord;
  }


  /**
   * Sets the secretWord attribute of the Contact object
   *
   * @param tmp The new secretWord value
   */
  public void setSecretWord(String tmp) {
    this.secretWord = tmp;
  }


  /**
   * Sets the revenue attribute of the Contact object
   *
   * @param tmp The new revenue value
   */
  public void setRevenue(double tmp) {
    this.revenue = tmp;
  }

  /**
   * Sets the revenue attribute of the Contact object
   *
   * @param tmp The new revenue value
   */
  public void setRevenue(String tmp) {
    try{
      this.revenue = Double.parseDouble(tmp);
    }catch (Exception e) {
      this.revenue = 0;
    }
  }

  /**
   * Sets the potential attribute of the Contact object
   *
   * @param tmp The new potential value
   */
  public void setPotential(double tmp) {
    this.potential = tmp;
  }


  /**
   * Sets the potential attribute of the Contact object
   *
   * @param tmp The new potential value
   */
  public void setPotential(String tmp) {
    this.potential = Double.parseDouble(tmp);
  }


  /**
   * Sets the industryTempCode attribute of the Contact object
   *
   * @param tmp The new industryTempCode value
   */
  public void setIndustryTempCode(int tmp) {
    this.industryTempCode = tmp;
  }


  /**
   * Sets the industryTempCode attribute of the Contact object
   *
   * @param tmp The new industryTempCode value
   */
  public void setIndustryTempCode(String tmp) {
    this.industryTempCode = Integer.parseInt(tmp);
  }


  /**
   * Gets the revenue attribute of the Contact object
   *
   * @return The revenue value
   */
  public double getRevenue() {
    return revenue;
  }


  /**
   * Gets the potential attribute of the Contact object
   *
   * @return The potential value
   */
  public double getPotential() {
    return potential;
  }


  /**
   * Gets the grossPotential attribute of the Contact object
   *
   * @param divisor Description of the Parameter
   * @return The grossPotential value
   */
  public double getGrossPotential(int divisor) {
    return (java.lang.Math.round(potential) / (double) divisor);
  }


  /**
   * Gets the industryTempCode attribute of the Contact object
   *
   * @return The industryTempCode value
   */
  public int getIndustryTempCode() {
    return industryTempCode;
  }


  /**
   * Sets the birthDate attribute of the Contact object
   *
   * @param tmp The new birthDate value
   */
  public void setBirthDate(java.sql.Timestamp tmp) {
    this.birthDate = tmp;
  }


  /**
   * Sets the birthDate attribute of the Contact object
   *
   * @param tmp The new birthDate value
   */
  public void setBirthDate(String tmp) {
    this.birthDate = DatabaseUtils.parseTimestamp(tmp, Locale.getDefault(), true);
  }


  /**
   * Sets the OwnerName attribute of the Contact object
   *
   * @param ownerName The new OwnerName value
   */
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }


  /**
   * Sets the hasEnabledOwnerAccount attribute of the Contact object
   *
   * @param hasEnabledOwnerAccount The new hasEnabledOwnerAccount value
   */
  public void setHasEnabledOwnerAccount(boolean hasEnabledOwnerAccount) {
    this.hasEnabledOwnerAccount = hasEnabledOwnerAccount;
  }


  /**
   * Sets the noEmail attribute of the Contact object
   *
   * @param tmp The new noEmail value
   */
  public void setNoEmail(boolean tmp) {
    this.noEmail = tmp;
  }


  /**
   * Sets the employee attribute of the Contact object
   *
   * @param tmp The new employee value
   */
  public void setEmployee(boolean tmp) {
    this.employee = tmp;
  }


  /**
   * Sets the employee attribute of the Contact object
   *
   * @param tmp The new employee value
   */
  public void setEmployee(String tmp) {
    this.employee = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the noMail attribute of the Contact object
   *
   * @param tmp The new noMail value
   */
  public void setNoMail(boolean tmp) {
    this.noMail = tmp;
  }


  /**
   * Sets the noPhone attribute of the Contact object
   *
   * @param tmp The new noPhone value
   */
  public void setNoPhone(boolean tmp) {
    this.noPhone = tmp;
  }


  /**
   * Sets the noPhone attribute of the Contact object
   *
   * @param tmp The new noPhone value
   */
  public void setNoPhone(String tmp) {
    this.noPhone = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the noTextMessage attribute of the Contact object
   *
   * @param tmp The new noTextMessage value
   */
  public void setNoTextMessage(boolean tmp) {
    this.noTextMessage = tmp;
  }


  /**
   * Sets the noTextMessage attribute of the Contact object
   *
   * @param tmp The new noTextMessage value
   */
  public void setNoTextMessage(String tmp) {
    this.noTextMessage = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the noInstantMessage attribute of the Contact object
   *
   * @param tmp The new noInstantMessage value
   */
  public void setNoInstantMessage(boolean tmp) {
    this.noInstantMessage = tmp;
  }


  /**
   * Sets the noInstantMessage attribute of the Contact object
   *
   * @param tmp The new noInstantMessage value
   */
  public void setNoInstantMessage(String tmp) {
    this.noInstantMessage = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the noFax attribute of the Contact object
   *
   * @param tmp The new noFax value
   */
  public void setNoFax(boolean tmp) {
    this.noFax = tmp;
  }


  /**
   * Sets the noFax attribute of the Contact object
   *
   * @param tmp The new noFax value
   */
  public void setNoFax(String tmp) {
    this.noFax = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the accessType attribute of the Contact object
   *
   * @param accessType The new accessType value
   */
  public void setAccessType(int accessType) {
    this.accessType = accessType;
  }


  /**
   * Sets the accessType attribute of the Contact object
   *
   * @param accessType The new accessType value
   */
  public void setAccessType(String accessType) {
    this.accessType = Integer.parseInt(accessType);
  }


  /**
   * Sets the clientId attribute of the Contact object
   *
   * @param clientId The new clientId value
   */
  public void setClientId(int clientId) {
    this.clientId = clientId;
  }


  /**
   * Sets the clientId attribute of the Contact object
   *
   * @param clientId The new clientId value
   */
  public void setClientId(String clientId) {
    this.clientId = Integer.parseInt(clientId);
  }


  /**
   * Gets the clientId attribute of the Contact object
   *
   * @return The clientId value
   */
  public int getClientId() {
    return clientId;
  }


  /**
   * Sets the statusId attribute of the Contact object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the Contact object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the importId attribute of the Contact object
   *
   * @param tmp The new importId value
   */
  public void setImportId(int tmp) {
    this.importId = tmp;
  }


  /**
   * Sets the importId attribute of the Contact object
   *
   * @param tmp The new importId value
   */
  public void setImportId(String tmp) {
    this.importId = Integer.parseInt(tmp);
  }


  /**
   * Sets the hasAccess attribute of the Contact object
   *
   * @param tmp The new hasAccess value
   */
  public void setHasAccess(boolean tmp) {
    this.hasAccess = tmp;
  }


  /**
   * Sets the hasAccess attribute of the Contact object
   *
   * @param tmp The new hasAccess value
   */
  public void setHasAccess(String tmp) {
    this.hasAccess = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the isEnabled attribute of the Contact object
   *
   * @param tmp The new isEnabled value
   */
  public void setIsEnabled(boolean tmp) {
    this.isEnabled = tmp;
  }


  /**
   * Sets the isEnabled attribute of the Contact object
   *
   * @param tmp The new isEnabled value
   */
  public void setIsEnabled(String tmp) {
    this.isEnabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the forceDelete attribute of the Contact object
   *
   * @param tmp The new forceDelete value
   */
  public void setForceDelete(boolean tmp) {
    this.forceDelete = tmp;
  }


  /**
   * Sets the forceDelete attribute of the Contact object
   *
   * @param tmp The new forceDelete value
   */
  public void setForceDelete(String tmp) {
    this.forceDelete = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the hasAccess attribute of the Contact object
   *
   * @return The hasAccess value
   */
  public boolean getHasAccess() {
    return hasAccess;
  }


  /**
   * Gets the isEnabled attribute of the Contact object
   *
   * @return The isEnabled value
   */
  public boolean getIsEnabled() {
    return isEnabled;
  }


  /**
   * Sets the trashedDate attribute of the Contact object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the Contact object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the trashedDate attribute of the Contact object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   * Gets the trashed attribute of the Contact object
   *
   * @return The trashed value
   */
  public boolean isTrashed() {
    return (trashedDate != null);
  }


  /**
   * Gets the forceDelete attribute of the Contact object
   *
   * @return The forceDelete value
   */
  public boolean getForceDelete() {
    return forceDelete;
  }


  /**
   * Gets the statusId attribute of the Contact object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Gets the importId attribute of the Contact object
   *
   * @return The importId value
   */
  public int getImportId() {
    return importId;
  }


  /**
   * Gets the accessType attribute of the Contact object
   *
   * @return The accessType value
   */
  public int getAccessType() {
    return accessType;
  }


  /**
   * Gets the accessType attribute of the Contact object
   *
   * @return The accessType value
   */
  public String getAccessTypeString() {
    return String.valueOf(accessType);
  }


  /**
   * Gets the employee attribute of the Contact object
   *
   * @return The employee value
   */
  public boolean getEmployee() {
    return employee;
  }


  /**
   * Gets the hasEnabledOwnerAccount attribute of the Contact object
   *
   * @return The hasEnabledOwnerAccount value
   */
  public boolean getHasEnabledOwnerAccount() {
    return hasEnabledOwnerAccount;
  }


  /**
   * Gets the url attribute of the Contact object
   *
   * @return The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   * Gets the hasOpportunities attribute of the Contact object
   *
   * @return The hasOpportunities value
   */
  public boolean getHasOpportunities() {
    return hasOpportunities;
  }


  /**
   * Sets the hasOpportunities attribute of the Contact object
   *
   * @param hasOpportunities The new hasOpportunities value
   */
  public void setHasOpportunities(boolean hasOpportunities) {
    this.hasOpportunities = hasOpportunities;
  }


  /**
   * Sets the url attribute of the Contact object
   *
   * @param url The new url value
   */
  public void setUrl(String url) {
    this.url = url;
  }


  /**
   * Sets the primaryContact attribute of the Contact object
   *
   * @param primaryContact The new primaryContact value
   */
  public void setPrimaryContact(boolean primaryContact) {
    this.primaryContact = primaryContact;
  }


  /**
   * Sets the primaryContact attribute of the Contact object
   *
   * @param tmp The new primaryContact value
   */
  public void setPrimaryContact(String tmp) {
    this.primaryContact = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the typeList attribute of the Contact object
   *
   * @param typeList The new typeList value
   */
  public void setTypeList(ArrayList typeList) {
    this.typeList = typeList;
  }


  /**
   * Gets the typeList attribute of the Contact object
   *
   * @return The typeList value
   */
  public ArrayList getTypeList() {
    return typeList;
  }


  /**
   * Gets the primaryContact attribute of the Contact object
   *
   * @return The primaryContact value
   */
  public boolean getPrimaryContact() {
    return primaryContact;
  }


  /**
   * Returns a name for the contact checking (last,first) name and company name
   * in that order
   *
   * @return The validName value
   */
  public String getValidName() {
    String validName = StringUtils.toString(getNameLastFirst());
    if ("".equals(validName) && !"".equals(StringUtils.toString(company))) {
      validName = company;
    }
    return validName;
  }


  /**
   * Gets the fullNameAbbr attribute of the Contact object
   *
   * @return The fullNameAbbr value
   */
  public String getFullNameAbbr() {
    StringBuffer out = new StringBuffer();
    if (this.getNameFirst() != null && this.getNameFirst().length() > 0) {
      out.append(this.getNameFirst().charAt(0) + ". ");
    }
    if (this.getNameLast() != null && this.getNameLast().length() > 0) {
      out.append(this.getNameLast());
    }
    return out.toString();
  }


  /**
   * Gets the custom1 attribute of the Contact object
   *
   * @return The custom1 value
   */
  public int getCustom1() {
    return custom1;
  }


  /**
   * Sets the custom1 attribute of the Contact object
   *
   * @param custom1 The new custom1 value
   */
  public void setCustom1(int custom1) {
    this.custom1 = custom1;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasEnabledAccount() {
    return hasEnabledAccount;
  }


  /**
   * Sets the hasEnabledAccount attribute of the Contact object
   *
   * @param hasEnabledAccount The new hasEnabledAccount value
   */
  public void setHasEnabledAccount(boolean hasEnabledAccount) {
    this.hasEnabledAccount = hasEnabledAccount;
  }


  /**
   * Sets the custom1 attribute of the Contact object
   *
   * @param custom1 The new custom1 value
   */
  public void setCustom1(String custom1) {
    this.custom1 = Integer.parseInt(custom1);
  }


  /**
   * Sets the buildDetails attribute of the Contact object
   *
   * @param tmp The new buildDetails value
   */
  public void setBuildDetails(boolean tmp) {
    this.buildDetails = tmp;
  }


  /**
   * Sets the buildTypes attribute of the Contact object
   *
   * @param tmp The new buildTypes value
   */
  public void setBuildTypes(boolean tmp) {
    this.buildTypes = tmp;
  }


  /**
   * Sets the buildTypes attribute of the Contact object
   *
   * @param tmp The new buildTypes value
   */
  public void setBuildTypes(String tmp) {
    this.buildTypes = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * @return the businessNameTwo
   */
  public String getBusinessNameTwo() {
    return businessNameTwo;
  }


  /**
   * @return the dunsNumber
   */
  public String getDunsNumber() {
    return dunsNumber;
  }


  /**
   * @return the dunsType
   */
  public String getDunsType() {
    return dunsType;
  }


  /**
   * Gets the Employees attribute of the Organization object
   *
   * @return The Employees value
   */
  public int getEmployees() {
    return employees;
  }


  /**
   * @return the sicCode
   */
  public int getSicCode() {
    return sicCode;
  }


  /**
   * @return the yearStarted
   */
  public int getYearStarted() {
    return yearStarted;
  }

  public void setSicDescription(String tmp) {
    this.sicDescription = tmp;
  }

  public String getSicDescription() {
    return sicDescription;
  }

  /**
   * @param businessNameTwo the businessNameTwo to set
   */
  public void setBusinessNameTwo(String businessNameTwo) {
    this.businessNameTwo = businessNameTwo;
  }


  /**
   * @param dunsNumber the dunsNumber to set
   */
  public void setDunsNumber(String dunsNumber) {
    this.dunsNumber = dunsNumber;
  }


  /**
   * @param dunsType the dunsType to set
   */
  public void setDunsType(String dunsType) {
    this.dunsType = dunsType;
  }


  /**
   * @param employees the employees to set
   */
  public void setEmployees(int employees) {
    this.employees = employees;
  }


  /**
   * Sets the Employees attribute of the Organization object
   *
   * @param employees The new Employees value
   */
  public void setEmployees(String employees) {
    try {
      this.employees = Integer.parseInt(employees);
    } catch (Exception e) {
      this.employees = 0;
    }
  }


  /**
   * @param sicCode the sicCode to set
   */
  public void setSicCode(int sicCode) {
    this.sicCode = sicCode;
  }


  /**
   * @param sicCode the sicCode to set
   */
  public void setSicCode(String sicCode) {
    this.sicCode = Integer.parseInt(sicCode);
  }


  /**
   * @param yearStarted the yearStarted to set
   */
  public void setYearStarted(int yearStarted) {
    this.yearStarted = yearStarted;
  }


  /**
   * @param yearStarted the yearStarted to set
   */
  public void setYearStarted(String yearStarted) {
    if (!"".equals(yearStarted) && yearStarted != null) {
      this.yearStarted = Integer.parseInt(yearStarted);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void checkEnabledOwnerAccount(Connection db) throws SQLException {
    if (this.getOwner() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
            "FROM " + DatabaseUtils.addQuotes(db, "access") + " " +
            "WHERE user_id = ? AND enabled = ? ");
    pst.setInt(1, this.getOwner());
    pst.setBoolean(2, true);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      this.setHasEnabledOwnerAccount(true);
    } else {
      this.setHasEnabledOwnerAccount(false);
    }
    rs.close();
    pst.close();
  }


  /**
   * Sets the Id attribute of the Contact object
   *
   * @param tmp The new Id value
   * @since 1.1
   */
  public void setId(int tmp) {
    this.id = tmp;
    addressList.setContactId(tmp);
    phoneNumberList.setContactId(tmp);
    emailAddressList.setContactId(tmp);
    textMessageAddressList.setContactId(tmp);
    instantMessageAddressList.setContactId(tmp);
  }


  /**
   * Sets the entered attribute of the Contact object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the modified attribute of the Contact object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the entered attribute of the Contact object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the modified attribute of the Contact object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the Owner attribute of the Opportunity object
   *
   * @param owner The new Owner value
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   * Sets the Owner attribute of the Opportunity object
   *
   * @param owner The new Owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   * Sets the Id attribute of the Contact object
   *
   * @param tmp The new Id value
   * @since 1.1
   */
  public void setId(String tmp) {
    this.setId(Integer.parseInt(tmp));
  }


  /**
   * Sets the OrgId attribute of the Contact object
   *
   * @param tmp The new OrgId value
   * @since 1.1
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the OrgId attribute of the Contact object
   *
   * @param tmp The new OrgId value
   * @since 1.2
   */
  public void setOrgId(String tmp) {
    if (tmp != null) {
      this.orgId = Integer.parseInt(tmp);
    }
  }


  /**
   * Sets the prospectClient attribute of the Contact object
   *
   * @param prospectClient The new prospectClient value
   */
  public void setProspectClient(boolean prospectClient) {
    this.prospectClient = prospectClient;
  }


  /**
   * Gets the prospectClient attribute of the Contact object
   *
   * @return The prospectClient value
   */
  public boolean getProspectClient() {
    return prospectClient;
  }


  /**
   * Sets the listSalutation attribute of the Contact object
   *
   * @param tmp The new listSalutation value
   * @since 1.2
   */
  public void setListSalutation(String tmp) {
    if (tmp != null) {
      this.listSalutation = Integer.parseInt(tmp);
    }
  }


  /**
   * Sets the listSalutation attribute of the Contact object
   *
   * @param tmp The new listSalutation value
   */
  public void setListSalutation(int tmp) {
    this.listSalutation = tmp;
  }


  /**
   * Sets the orgSiteId attribute of the Contact object
   *
   * @param orgSiteId The new orgSiteId value
   */
  public void setOrgSiteId(int orgSiteId) {
    this.orgSiteId = orgSiteId;
  }


  /**
   * Sets the orgSiteId attribute of the Contact object
   *
   * @param tmp The new orgSiteId value
   */
  public void setOrgSiteId(String tmp) {
    this.orgSiteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the orgSiteId attribute of the Organization object
   *
   * @return The orgSiteId value
   */
  public int getOrgSiteId() {
    return orgSiteId;
  }


  /**
   * Sets the ModifiedByName attribute of the Contact object
   *
   * @param modifiedByName The new ModifiedByName value
   */
  public void setModifiedByName(String modifiedByName) {
    this.modifiedByName = modifiedByName;
  }


  /**
   * Sets the NameSalutation attribute of the Contact object
   *
   * @param tmp The new NameSalutation value
   * @since 1.1
   */
  public void setNameSalutation(String tmp) {
    this.nameSalutation = tmp;
  }


  /**
   * Sets the NameFirst attribute of the Contact object
   *
   * @param tmp The new NameFirst value
   * @since 1.1
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   * Sets the EnteredByName attribute of the Contact object
   *
   * @param enteredByName The new EnteredByName value
   */
  public void setEnteredByName(String enteredByName) {
    this.enteredByName = enteredByName;
  }


  /**
   * Sets the NameMiddle attribute of the Contact object
   *
   * @param tmp The new NameMiddle value
   * @since 1.1
   */
  public void setNameMiddle(String tmp) {
    this.nameMiddle = tmp;
  }


  /**
   * Sets the NameLast attribute of the Contact object
   *
   * @param tmp The new NameLast value
   * @since 1.1
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   * Sets the NameSuffix attribute of the Contact object
   *
   * @param tmp The new NameSuffix value
   * @since 1.1
   */
  public void setNameSuffix(String tmp) {
    this.nameSuffix = tmp;
  }


  /**
   * Gets the assistant attribute of the Contact object
   *
   * @return The assistant value
   */
  public int getAssistant() {
    return assistant;
  }


  /**
   * Sets the assistant attribute of the Contact object
   *
   * @param tmp The new assistant value
   */
  public void setAssistant(int tmp) {
    this.assistant = tmp;
  }


  /**
   * Sets the assistant attribute of the Contact object
   *
   * @param tmp The new assistant value
   */
  public void setAssistant(String tmp) {
    this.assistant = Integer.parseInt(tmp);
  }


  /**
   * Sets the Company attribute of the Contact object
   *
   * @param tmp The new Company value
   * @since 1.34
   */
  public void setCompany(String tmp) {
    this.company = tmp;
  }


  /**
   * Sets the AccountNumber attribute of the Contact object
   *
   * @param tmp The new AccountNumber value
   */
  public void setAccountNumber(String tmp) {
    this.accountNumber = tmp;
  }


  /**
   * Sets the Title attribute of the Contact object
   *
   * @param tmp The new Title value
   * @since 1.1
   */
  public void setTitle(String tmp) {
    if (tmp != null && tmp.length() > 80) {
      tmp = tmp.substring(0, 79);
    }
    this.title = tmp;
  }


  /**
   * Sets the DepartmentName attribute of the Contact object
   *
   * @param tmp The new DepartmentName value
   * @since 1.1
   */
  public void setDepartmentName(String tmp) {
    this.departmentName = tmp;
  }


  /**
   * Sets the Department attribute of the Contact object
   *
   * @param tmp The new Department value
   * @since 1.1
   */
  public void setDepartment(int tmp) {
    this.department = tmp;
  }


  /**
   * Sets the Department attribute of the Contact object
   *
   * @param tmp The new Department value
   * @since 1.1
   */
  public void setDepartment(String tmp) {
    this.department = Integer.parseInt(tmp);
  }


  /**
   * Sets the EmailAddresses attribute of the Contact object
   *
   * @param tmp The new EmailAddresses value
   * @since 1.1
   */
  public void setEmailAddressList(ContactEmailAddressList tmp) {
    this.emailAddressList = tmp;
  }


  /**
   * Sets the ContactPhoneNumberList attribute of the Contact object
   *
   * @param tmp The new ContactPhoneNumberList value
   * @since 1.13
   */
  public void setPhoneNumberList(ContactPhoneNumberList tmp) {
    this.phoneNumberList = tmp;
  }


  /**
   * Sets the textMessageAddressList attribute of the Contact object
   *
   * @param tmp The new textMessageAddressList value
   */
  public void setTextMessageAddressList(ContactTextMessageAddressList tmp) {
    this.textMessageAddressList = tmp;
  }


  /**
   * Gets the orgEnabled attribute of the Contact object
   *
   * @return The orgEnabled value
   */
  public boolean getOrgEnabled() {
    return orgEnabled;
  }


  /**
   * Gets the orgTrashedDate attribute of the Contact object
   *
   * @return The orgTrashedDate value
   */
  public java.sql.Timestamp getOrgTrashedDate() {
    return orgTrashedDate;
  }


  /**
   * Gets the noEmail attribute of the Contact object
   *
   * @return The getNoEmail value
   */
  public boolean getNoEmail() {
    return noEmail;
  }


  /**
   * Sets the NoEmail and noMail attribute of the Organization object
   *
   * @param tmp The new NoEmail value
   */
  public void setNoEmail(String tmp) {
    this.noEmail = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the noMail attribute of the Contact object
   *
   * @param tmp The new noMail value
   */
  public void setNoMail(String tmp) {
    this.noMail = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the noMail attribute of the Contact object
   *
   * @return The noMail value
   */
  public boolean getNoMail() {
    return noMail;
  }


  /**
   * Gets the noPhone attribute of the Contact object
   *
   * @return The noPhone value
   */
  public boolean getNoPhone() {
    return noPhone;
  }


  /**
   * Gets the noTextMessage attribute of the Contact object
   *
   * @return The noTextMessage value
   */
  public boolean getNoTextMessage() {
    return noTextMessage;
  }


  /**
   * Gets the noInstantMessage attribute of the Contact object
   *
   * @return The noInstantMessage value
   */
  public boolean getNoInstantMessage() {
    return noInstantMessage;
  }


  /**
   * Gets the noFax attribute of the Contact object
   *
   * @return The noFax value
   */
  public boolean getNoFax() {
    return noFax;
  }


  /**
   * Sets the orgEnabled attribute of the Contact object
   *
   * @param orgEnabled The new orgEnabled value
   */
  public void setOrgEnabled(boolean orgEnabled) {
    this.orgEnabled = orgEnabled;
  }


  /**
   * Sets the orgTrashedDate attribute of the Contact object
   *
   * @param tmp The new orgTrashedDate value
   */
  public void setOrgTrashedDate(java.sql.Timestamp tmp) {
    this.orgTrashedDate = tmp;
  }


  /**
   * Sets the orgTrashedDate attribute of the Contact object
   *
   * @param tmp The new orgTrashedDate value
   */
  public void setOrgTrashedDate(String tmp) {
    this.orgTrashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the excludedFromCampaign attribute of the Contact object
   *
   * @param excludedFromCampaign The new excludedFromCampaign value
   */
  public void setExcludedFromCampaign(boolean excludedFromCampaign) {
    this.excludedFromCampaign = excludedFromCampaign;
  }


  /**
   * Sets the AddressList attribute of the Contact object
   *
   * @param tmp The new AddressList value
   * @since 1.1
   */
  public void setAddressList(ContactAddressList tmp) {
    this.addressList = tmp;
  }


  /**
   * Sets the Notes attribute of the Contact object
   *
   * @param tmp The new Notes value
   * @since 1.1
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   * Sets the Site attribute of the Contact object
   *
   * @param tmp The new Site value
   * @since 1.1
   */
  public void setSite(String tmp) {
    this.site = tmp;
  }


  /**
   * Sets the EmploymentType attribute of the Contact object
   *
   * @param tmp The new EmploymentType value
   * @since 1.1
   */
  public void setEmploymentType(int tmp) {
    this.employmentType = tmp;
  }


  /**
   * Sets the EmploymentType attribute of the Contact object
   *
   * @param tmp The new EmploymentType value
   * @since 1.2
   */
  public void setEmploymentType(String tmp) {
    this.employmentType = Integer.parseInt(tmp);
  }


  /**
   * Sets the Locale attribute of the Contact object
   *
   * @param tmp The new Locale value
   * @since 1.1
   */
  public void setLocale(int tmp) {
    this.locale = tmp;
  }


  /**
   * Sets the Locale attribute of the Contact object
   *
   * @param tmp The new Locale value
   * @since 1.2
   */
  public void setLocale(String tmp) {
    this.locale = Integer.parseInt(tmp);
  }


  /**
   * Sets the EmployeeId attribute of the Contact object
   *
   * @param tmp The new EmployeeId value
   * @since 1.1
   */
  public void setEmployeeId(String tmp) {
    this.employeeId = tmp;
  }


  /**
   * Sets the StartOfDay attribute of the Contact object
   *
   * @param tmp The new StartOfDay value
   * @since 1.1
   */
  public void setStartOfDay(String tmp) {
    this.startOfDay = tmp;
  }


  /**
   * Sets the EndOfDay attribute of the Contact object
   *
   * @param tmp The new EndOfDay value
   * @since 1.1
   */
  public void setEndOfDay(String tmp) {
    this.endOfDay = tmp;
  }


  /**
   * Sets the Enabled attribute of the Contact object
   *
   * @param tmp The new Enabled value
   * @since 1.2
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the Enabled attribute of the Contact object
   *
   * @param tmp The new Enabled value
   * @since 1.2
   */
  public void setEnabled(String tmp) {
    enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   * Sets the EnteredBy attribute of the Contact object
   *
   * @param tmp The new EnteredBy value
   * @since 1.12
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Contact object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the ModifiedBy attribute of the Contact object
   *
   * @param tmp The new ModifiedBy value
   * @since 1.12
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the Contact object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the HasAccount attribute of the Contact object
   *
   * @param tmp The new HasAccount value
   * @since 1.20
   */
  public void setHasAccount(boolean tmp) {
    this.hasAccount = tmp;
  }


  /**
   * Gets the approved attribute of the Contact object
   *
   * @return The approved value
   */
  public boolean isApproved() {
    return (statusId != Import.PROCESSED_UNAPPROVED);
  }


  /**
   * Since dynamic fields cannot be auto-populated, passing the request to this
   * method will populate the indicated fields.
   *
   * @param context The new requestItems value
   * @since 1.15
   */
  public void setRequestItems(ActionContext context) {
    phoneNumberList = new ContactPhoneNumberList(context);
    addressList = new ContactAddressList(context.getRequest());
    emailAddressList = new ContactEmailAddressList(context.getRequest());
    textMessageAddressList = new ContactTextMessageAddressList(
        context.getRequest());
    instantMessageAddressList = new ContactInstantMessageAddressList(
        context.getRequest());
  }


  /**
   * Sets the typeList attribute of the Contact object
   *
   * @param criteriaString The new typeList value
   */
  public void setTypeList(String[] criteriaString) {
    if (criteriaString != null) {
      String[] params = criteriaString;
      typeList = new ArrayList(Arrays.asList(params));
    } else {
      typeList = new ArrayList();
    }
  }


  /**
   * Adds a feature to the Type attribute of the Contact object
   *
   * @param typeId The feature to be added to the Type attribute
   */
  public void addType(int typeId) {
    if (typeList == null) {
      typeList = new ArrayList();
    }
    typeList.add(String.valueOf(typeId));
  }


  /**
   * Adds a feature to the Type attribute of the Contact object
   *
   * @param typeId The feature to be added to the Type attribute
   */
  public void addType(String typeId) {
    if (typeList == null) {
      typeList = new ArrayList();
    }
    typeList.add(typeId);
  }


  /**
   * Gets the typesNameString attribute of the Contact object
   *
   * @return The typesNameString value
   */
  public String getTypesNameString() {
    StringBuffer types = new StringBuffer();
    Iterator i = getTypes().iterator();
    while (i.hasNext()) {
      LookupElement thisElt = (LookupElement) i.next();
      types.append(thisElt.getDescription());
      if (i.hasNext()) {
        types.append(", ");
      }
    }
    return types.toString();
  }


  /**
   * Description of the Method
   *
   * @param type Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasType(int type) {
    boolean gotType = false;
    Iterator i = getTypes().iterator();
    while (i.hasNext()) {
      LookupElement thisElt = (LookupElement) i.next();
      if (thisElt.getCode() == type) {
        gotType = true;
        break;
      }
    }
    return gotType;
  }


  /**
   * Gets the entered attribute of the Contact object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the modified attribute of the Contact object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the user_id attribute of the Contact object
   *
   * @return The user_id value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Sets the userId attribute of the Contact object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    userId = tmp;
  }


  /**
   * Sets the userId attribute of the Contact object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    userId = Integer.parseInt(tmp);
  }


  /**
   * Sets the types attribute of the Contact object
   *
   * @param types The new types value
   */
  public void setTypes(LookupList types) {
    this.types = types;
  }


  /**
   * Gets the types attribute of the Contact object
   *
   * @return The types value
   */
  public LookupList getTypes() {
    return types;
  }


  /**
   * Gets the modifiedString attribute of the Contact object
   *
   * @return The modifiedString value
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
   * Gets the enteredString attribute of the Contact object
   *
   * @return The enteredString value
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
   * Gets the ModifiedByName attribute of the Contact object
   *
   * @return The ModifiedByName value
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   * Gets the EnteredByName attribute of the Contact object
   *
   * @return The EnteredByName value
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   * Gets the OwnerName attribute of the Contact object
   *
   * @return The OwnerName value
   */
  public String getOwnerName() {
    return ownerName;
  }


  /**
   * Gets the Owner attribute of the Opportunity object
   *
   * @return The Owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Gets the ownerString attribute of the Contact object
   *
   * @return The ownerString value
   */
  public String getOwnerString() {
    return String.valueOf(owner);
  }


  /**
   * Gets the Id attribute of the Contact object
   *
   * @return The Id value
   * @since 1.1
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the OrgId attribute of the Contact object
   *
   * @return The OrgId value
   * @since 1.1
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Gets the listSalutation attribute of the Contact object
   *
   * @return The OrgId value
   * @since 1.1
   */
  public int getListSalutation() {
    return listSalutation;
  }


  /**
   * Sets the orgName attribute of the Contact object
   *
   * @param orgName The new orgName value
   */
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }


  /**
   * Gets the OrgName attribute of the Contact object
   *
   * @return The OrgName value
   * @since 1.28
   */
  public String getOrgName() {
    return orgName;
  }


  /**
   * Gets the NameSalutation attribute of the Contact object
   *
   * @return The NameSalutation value
   * @since 1.1
   */
  public String getNameSalutation() {
    return nameSalutation;
  }


  /**
   * Gets the NameFirst attribute of the Contact object
   *
   * @return The NameFirst value
   * @since 1.1
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   * Gets the NameMiddle attribute of the Contact object
   *
   * @return The NameMiddle value
   * @since 1.1
   */
  public String getNameMiddle() {
    return nameMiddle;
  }


  /**
   * Gets the NameLast attribute of the Contact object
   *
   * @return The NameLast value
   * @since 1.1
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   * Gets the NameSuffix attribute of the Contact object
   *
   * @return The NameSuffix value
   * @since 1.1
   */
  public String getNameSuffix() {
    return nameSuffix;
  }


  /**
   * Gets the NameFull attribute of the Contact object
   *
   * @return The NameFull value
   * @since 1.33
   */
  public String getNameFull() {
    StringBuffer out = new StringBuffer();

    if (nameSalutation != null && nameSalutation.length() > 0) {
      out.append(nameSalutation + " ");
    }

    if (nameFirst != null && nameFirst.length() > 0) {
      out.append(nameFirst + " ");
    }

    if (nameMiddle != null && nameMiddle.length() > 0) {
      out.append(nameMiddle + " ");
    }

    if (nameLast != null && nameLast.length() > 0) {
      out.append(nameLast + " ");
    }

    if (nameSuffix != null && nameSuffix.length() > 0) {
      out.append(nameSuffix + " ");
    }

    if (out.toString().length() == 0) {
      return company;
    }

    return out.toString().trim();
  }


  /**
   * Gets the NameFirstLast attribute of the Contact object
   *
   * @return The NameFirstLast value
   */
  public String getNameFirstLast() {
    StringBuffer out = new StringBuffer();

    if (nameFirst != null && nameFirst.length() > 0) {
      out.append(nameFirst + " ");
    }

    if (nameLast != null && nameLast.length() > 0) {
      out.append(nameLast);
    }

    if (out.toString().length() == 0) {
      return company;
    }

    return out.toString().trim();
  }


  /**
   * Gets the userIdParams attribute of the Contact class
   *
   * @return The userIdParams value
   */
  public static ArrayList getUserIdParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("enteredBy");
    thisList.add("modifiedBy");
    thisList.add("owner");
    return thisList;
  }


  /**
   * Gets the timeZoneParams attribute of the Contact class
   *
   * @return The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("birthDate");
    return thisList;
  }


  /**
   * Gets the numberParams attribute of the Organization class
   *
   * @return The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("revenue");
    thisList.add("potential");
    return thisList;
  }


  /**
   * Gets the nameFirstInitialLast attribute of the Contact object
   *
   * @return The nameFirstInitialLast value
   */
  public String getNameFirstInitialLast() {
    StringBuffer out = new StringBuffer();
    if (nameFirst != null && nameFirst.trim().length() > 0) {
      out.append(String.valueOf(nameFirst.charAt(0)) + ".");
    }
    if (nameLast != null && nameLast.trim().length() > 0) {
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(nameLast);
    }
    if (out.toString().length() == 0) {
      return null;
    }
    return out.toString().trim();
  }


  /**
   * Gets the NameLastFirst attribute of the Contact object
   *
   * @return The NameLastFirst value
   */
  public String getNameLastFirst() {
    return Contact.getNameLastFirst(nameLast, nameFirst);
  }


  /**
   * Gets the Company attribute of the Contact object
   *
   * @return The Company value
   * @since 1.34
   */

  public String getCompany() {
    if (company == null || company.trim().equals("")) {
      return orgName;
    } else {
      return company;
    }
  }


  /**
   * Gets the accountNumber attribute of the Contact object
   *
   * @return The accountNumber value
   */
  public String getAccountNumber() {
    return accountNumber;
  }


  /**
   * Gets the affiliation attribute of the Contact object
   *
   * @return The affiliation value
   */
  public String getAffiliation() {
    if (orgId > -1) {
      return orgName;
    } else {
      return company;
    }
  }


  /**
   * Gets the companyOnly attribute of the Contact object
   *
   * @return The companyOnly value
   */
  public String getCompanyOnly() {
    return company;
  }


  /**
   * Gets the Title attribute of the Contact object
   *
   * @return The Title value
   * @since 1.1
   */
  public String getTitle() {
    return title;
  }


  /**
   * Gets the DepartmentName attribute of the Contact object
   *
   * @return The DepartmentName value
   * @since 1.1
   */
  public String getDepartmentName() {
    return departmentName;
  }


  /**
   * Gets the Department attribute of the Contact object
   *
   * @return The Department value
   * @since 1.1
   */
  public int getDepartment() {
    return department;
  }


  /**
   * Gets the EmailAddresses attribute of the Contact object
   *
   * @return The EmailAddresses value
   * @since 1.1
   */
  public ContactEmailAddressList getEmailAddressList() {
    return emailAddressList;
  }


  /**
   * Gets the ContactPhoneNumberList attribute of the Contact object
   *
   * @param thisType Description of Parameter
   * @return The PhoneNumber value
   * @since 1.13
   */
  public String getPhoneNumber(String thisType) {
    return phoneNumberList.getPhoneNumber(thisType);
  }


  /**
   * Description of the Method
   */
  public void resetBaseInfo() {
    this.nameFirst = null;
    this.nameLast = null;
    this.nameMiddle = null;
    this.nameSalutation = null;
    this.nameSuffix = null;
    this.id = -1;
    this.notes = null;
    this.title = null;

    Iterator i = emailAddressList.iterator();
    while (i.hasNext()) {
      ContactEmailAddress thisAddress = (ContactEmailAddress) i.next();
      thisAddress.setId(-1);
    }

    Iterator j = phoneNumberList.iterator();
    while (j.hasNext()) {
      ContactPhoneNumber thisNumber = (ContactPhoneNumber) j.next();
      thisNumber.setId(-1);
    }

    Iterator k = addressList.iterator();
    while (k.hasNext()) {
      ContactAddress thisAddress = (ContactAddress) k.next();
      thisAddress.setId(-1);
    }

    Iterator l = textMessageAddressList.iterator();
    while (l.hasNext()) {
      ContactTextMessageAddress thisAddress = (ContactTextMessageAddress) l.next();
      thisAddress.setId(-1);
    }

    Iterator m = instantMessageAddressList.iterator();
    while (m.hasNext()) {
      ContactInstantMessageAddress thisAddress = (ContactInstantMessageAddress) m.next();
      thisAddress.setId(-1);
    }
  }


  /**
   * Gets the phoneNumber attribute of the Contact object
   *
   * @param position Description of the Parameter
   * @return The phoneNumber value
   */
  public String getPhoneNumber(int position) {
    return phoneNumberList.getPhoneNumber(position);
  }


  /**
   * Gets the EmailAddress attribute of the Contact object
   *
   * @param thisType Description of Parameter
   * @return The EmailAddress value
   * @since 1.10
   */
  public String getEmailAddress(String thisType) {
    return emailAddressList.getEmailAddress(thisType);
  }


  /**
   * Gets the EmailAddressTag attribute of the Contact object
   *
   * @param thisType   Description of Parameter
   * @param linkText   Description of Parameter
   * @param noLinkText Description of Parameter
   * @return The EmailAddressTag value
   * @since 1.50
   */
  public String getEmailAddressTag(String thisType, String linkText, String noLinkText) {
    String tmpAddress = emailAddressList.getEmailAddress(thisType);
    if ("".equals(thisType) && !("".equals(linkText) || "&nbsp;".equals(
        linkText) || (linkText == null))) {
      return "<a href=\"mailto:" + this.getPrimaryEmailAddress() + "\">" + linkText + "</a>";
    } else if (tmpAddress != null && !"".equals(tmpAddress)) {
      return "<a href=\"mailto:" + this.getEmailAddress(thisType) + "\">" + linkText + "</a>";
    } else {
      return noLinkText;
    }
  }


  /**
   * Gets the EmailAddress attribute of the Contact object using the specified
   * position in the emailAddressList
   *
   * @param thisPosition Description of Parameter
   * @return The EmailAddress value
   * @since 1.24
   */
  public String getEmailAddress(int thisPosition) {
    return emailAddressList.getEmailAddress(thisPosition);
  }


  /**
   * Gets the EmailAddressTypeId attribute of the Contact object
   *
   * @param thisPosition Description of Parameter
   * @return The EmailAddressTypeId value
   * @since 1.24
   */
  public int getEmailAddressTypeId(int thisPosition) {
    return emailAddressList.getEmailAddressTypeId(thisPosition);
  }


  /**
   * Gets the Address attribute of the Contact object
   *
   * @param thisType Description of Parameter
   * @return The Address value
   * @since 1.1
   */
  public Address getAddress(String thisType) {
    return addressList.getAddress(thisType);
  }


  /**
   * Gets the primaryEmailAddress attribute of the Contact object
   *
   * @return The primaryEmailAddress value
   */
  public String getPrimaryEmailAddress() {
    return emailAddressList.getPrimaryEmailAddress();
  }


  /**
   * Gets the primaryPhoneNumber attribute of the Contact object
   *
   * @return The primaryPhoneNumber value
   */
  public String getPrimaryPhoneNumber() {
    return phoneNumberList.getPrimaryPhoneNumber();
  }


  /**
   * Gets the primaryTextMessageAddress attribute of the Contact object
   *
   * @return The primaryTextMessageAddress value
   */
  public String getPrimaryTextMessageAddress() {
    return textMessageAddressList.getPrimaryTextMessageAddress();
  }


  /**
   * Gets the primaryInstantMessageAddress attribute of the Contact object
   *
   * @return The primaryInstantMessageAddress value
   */
  public String getPrimaryInstantMessageAddress() {
    return instantMessageAddressList.getPrimaryInstantMessageAddress();
  }


  /**
   * Gets the primaryAddress attribute of the Contact object
   *
   * @return The primaryAddress value
   */
  public Address getPrimaryAddress() {
    return addressList.getPrimaryAddress();
  }


  /**
   * Gets the Addresses attribute of the Contact object
   *
   * @return The Addresses value
   * @since 1.1
   */
  public ContactAddressList getAddressList() {
    return addressList;
  }


  /**
   * Gets the Notes attribute of the Contact object
   *
   * @return The Notes value
   * @since 1.1
   */
  public String getNotes() {
    return notes;
  }


  /**
   * Gets the Site attribute of the Contact object
   *
   * @return The Site value
   * @since 1.1
   */
  public String getSite() {
    return site;
  }


  /**
   * Gets the EmploymentType attribute of the Contact object
   *
   * @return The EmploymentType value
   * @since 1.1
   */
  public int getEmploymentType() {
    return employmentType;
  }


  /**
   * Gets the Locale attribute of the Contact object
   *
   * @return The Locale value
   * @since 1.1
   */
  public int getLocale() {
    return locale;
  }


  /**
   * Gets the EmployeeId attribute of the Contact object
   *
   * @return The EmployeeId value
   * @since 1.1
   */
  public String getEmployeeId() {
    return employeeId;
  }


  /**
   * Gets the StartOfDay attribute of the Contact object
   *
   * @return The StartOfDay value
   * @since 1.esn'
   */
  public String getStartOfDay() {
    return startOfDay;
  }


  /**
   * Gets the EndOfDay attribute of the Contact object
   *
   * @return The EndOfDay value
   * @since 1.1
   */
  public String getEndOfDay() {
    return endOfDay;
  }


  /**
   * Gets the Enabled attribute of the Contact object
   *
   * @return The Enabled value
   * @since 1.2
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the PhoneNumberList attribute of the Contact object
   *
   * @return The PhoneNumberList value
   * @since 1.13
   */
  public ContactPhoneNumberList getPhoneNumberList() {
    return phoneNumberList;
  }


  /**
   * Gets the textMessageAddressList attribute of the Contact object
   *
   * @return The textMessageAddressList value
   */
  public ContactTextMessageAddressList getTextMessageAddressList() {
    return textMessageAddressList;
  }


  /**
   * Gets the textMessageAddressTypeId attribute of the Contact object
   *
   * @param thisPosition Description of the Parameter
   * @return The textMessageAddressTypeId value
   */
  public int getTextMessageAddressTypeId(int thisPosition) {
    return textMessageAddressList.getTextMessageAddressTypeId(thisPosition);
  }


  /**
   * Gets the instantMessageAddressTypeId attribute of the Contact object
   *
   * @param thisPosition Description of the Parameter
   * @return The instantMessageAddressTypeId value
   */
  public int getInstantMessageAddressTypeId(int thisPosition) {
    return instantMessageAddressList.getInstantMessageAddressTypeId(
        thisPosition);
  }


  /**
   * Gets the instantMessageAddressServiceId attribute of the Contact object
   *
   * @param thisPosition Description of the Parameter
   * @return The instantMessageAddressServiceId value
   */
  public int getInstantMessageAddressServiceId(int thisPosition) {
    return instantMessageAddressList.getInstantMessageAddressServiceId(
        thisPosition);
  }


  /**
   * Gets the EnteredBy attribute of the Contact object
   *
   * @return The EnteredBy value
   * @since 1.1
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the ModifiedBy attribute of the Contact object
   *
   * @return The ModifiedBy value
   * @since 1.1
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public boolean excludedFromCampaign() {
    return getExcludedFromCampaign();
  }


  /**
   * Gets the excludedFromCampaign attribute of the Contact object
   *
   * @return The excludedFromCampaign value
   */
  public boolean getExcludedFromCampaign() {
    return excludedFromCampaign;
  }


  /**
   * Gets the isLead attribute of the Contact object
   *
   * @return The isLead value
   */
  public boolean getIsLead() {
    return isLead;
  }


  /**
   * Sets the isLead attribute of the Contact object
   *
   * @param tmp The new isLead value
   */
  public void setIsLead(boolean tmp) {
    this.isLead = tmp;
  }


  /**
   * Sets the isLead attribute of the Contact object
   *
   * @param tmp The new isLead value
   */
  public void setIsLead(String tmp) {
    this.isLead = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the leadStatus attribute of the Contact object
   *
   * @return The leadStatus value
   */
  public int getLeadStatus() {
    return leadStatus;
  }


  /**
   * Sets the leadStatus attribute of the Contact object
   *
   * @param tmp The new leadStatus value
   */
  public void setLeadStatus(int tmp) {
    this.leadStatus = tmp;
  }


  /**
   * Sets the leadStatus attribute of the Contact object
   *
   * @param tmp The new leadStatus value
   */
  public void setLeadStatus(String tmp) {
    this.leadStatus = Integer.parseInt(tmp);
  }


  /**
   * Gets the leadStatusString attribute of the Contact object
   *
   * @return The leadStatusString value
   */
  public String getLeadStatusString() {
    if (leadStatus == LEAD_ASSIGNED) {
      return "Assigned";
    } else if (leadStatus == LEAD_TRASHED) {
      return "Archived";
    } else if (leadStatus == LEAD_UNPROCESSED) {
      return "Unprocessed";
    }
    return "Error";
  }


  /**
   * Gets the source attribute of the Contact object
   *
   * @return The source value
   */
  public int getSource() {
    return source;
  }


  /**
   * Sets the source attribute of the Contact object
   *
   * @param tmp The new source value
   */
  public void setSource(int tmp) {
    this.source = tmp;
  }


  /**
   * Sets the source attribute of the Contact object
   *
   * @param tmp The new source value
   */
  public void setSource(String tmp) {
    this.source = Integer.parseInt(tmp);
  }


  /**
   * Gets the rating attribute of the Contact object
   *
   * @return The rating value
   */
  public int getRating() {
    return rating;
  }


  /**
   * Sets the rating attribute of the Contact object
   *
   * @param tmp The new rating value
   */
  public void setRating(int tmp) {
    this.rating = tmp;
  }


  /**
   * Sets the rating attribute of the Contact object
   *
   * @param tmp The new rating value
   */
  public void setRating(String tmp) {
    this.rating = Integer.parseInt(tmp);
  }


  /**
   * Gets the comments attribute of the Contact object
   *
   * @return The comments value
   */
  public String getComments() {
    return comments;
  }


  /**
   * Sets the comments attribute of the Contact object
   *
   * @param tmp The new comments value
   */
  public void setComments(String tmp) {
    this.comments = tmp;
  }


  /**
   * Gets the conversionDate attribute of the Contact object
   *
   * @return The conversionDate value
   */
  public java.sql.Timestamp getConversionDate() {
    return conversionDate;
  }


  /**
   * Sets the conversionDate attribute of the Contact object
   *
   * @param tmp The new conversionDate value
   */
  public void setConversionDate(java.sql.Timestamp tmp) {
    this.conversionDate = tmp;
  }


  /**
   * Sets the conversionDate attribute of the Contact object
   *
   * @param tmp The new conversionDate value
   */
  public void setConversionDate(String tmp) {
    this.conversionDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the assignedDate attribute of the Contact object
   *
   * @return The assignedDate value
   */
  public java.sql.Timestamp getAssignedDate() {
    return assignedDate;
  }


  /**
   * Sets the assignedDate attribute of the Contact object
   *
   * @param tmp The new assignedDate value
   */
  public void setAssignedDate(java.sql.Timestamp tmp) {
    this.assignedDate = tmp;
  }


  /**
   * Sets the assignedDate attribute of the Contact object
   *
   * @param tmp The new assignedDate value
   */
  public void setAssignedDate(String tmp) {
    this.assignedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the leadTrashedDate attribute of the Contact object
   *
   * @return The leadTrashedDate value
   */
  public java.sql.Timestamp getLeadTrashedDate() {
    return leadTrashedDate;
  }


  /**
   * Sets the leadTrashedDate attribute of the Contact object
   *
   * @param tmp The new leadTrashedDate value
   */
  public void setLeadTrashedDate(java.sql.Timestamp tmp) {
    this.leadTrashedDate = tmp;
  }


  /**
   * Sets the leadTrashedDate attribute of the Contact object
   *
   * @param tmp The new leadTrashedDate value
   */
  public void setLeadTrashedDate(String tmp) {
    this.leadTrashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the siteName attribute of the Contact object
   *
   * @return The siteName value
   */
  public String getSiteName() {
    return siteName;
  }


  /**
   * Sets the siteName attribute of the Contact object
   *
   * @param tmp The new siteName value
   */
  public void setSiteName(String tmp) {
    this.siteName = tmp;
  }


  /**
   * Gets the checkRevertingBackToLead attribute of the Contact object
   *
   * @return The checkRevertingBackToLead value
   */
  public boolean getCheckRevertingBackToLead() {
    return checkRevertingBackToLead;
  }


  /**
   * Sets the checkRevertingBackToLead attribute of the Contact object
   *
   * @param tmp The new checkRevertingBackToLead value
   */
  public void setCheckRevertingBackToLead(boolean tmp) {
    this.checkRevertingBackToLead = tmp;
  }


  /**
   * Sets the checkRevertingBackToLead attribute of the Contact object
   *
   * @param tmp The new checkRevertingBackToLead value
   */
  public void setCheckRevertingBackToLead(String tmp) {
    this.checkRevertingBackToLead = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Description of the Method
   *
   * @param db         Description of Parameter
   * @param campaignId Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean toggleExcluded(Connection db, int campaignId) throws SQLException {
    if (id == -1) {
      return false;
    }

    ExcludedRecipient thisRecipient = new ExcludedRecipient();
    thisRecipient.setCampaignId(campaignId);
    thisRecipient.setContactId(this.getId());
    if (this.excludedFromCampaign()) {
      thisRecipient.delete(db);
    } else {
      thisRecipient.insert(db);
    }

    this.excludedFromCampaign = !excludedFromCampaign;
    return true;
  }


  /**
   * Returns whether or not this Contact has a User Account in the system
   *
   * @return Description of the Returned Value
   * @since 1.10
   */
  public boolean hasAccount() {
    return hasAccount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildPhoneNumberList(Connection db) throws SQLException {
    phoneNumberList.setContactId(this.getId());
    phoneNumberList.buildList(db);
  }


  /**
   * Inserts this object into the database, and populates this Id. For
   * maintenance, only the required fields are inserted, then an update is
   * executed to finish the record.
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "contact_contact_id_seq");
      sql.append(
          "INSERT INTO contact " +
              "(user_id, namefirst, namelast, owner, primary_contact, org_name, account_number, ");
      sql.append(
          "access_type, source, rating, comments, assigned_date, conversion_date, lead_trashed_date, trashed_date, ");
      if (id > -1) {
        sql.append("contact_id, ");
      }
      sql.append(
          "additional_names, nickname, birthdate, " + DatabaseUtils.addQuotes(db, "role") + ", site_id, " +
              "revenue, industry_temp_code, potential, ");
      sql.append("employees, duns_type, duns_number, business_name_two, year_started, sic_code, sic_description, ");

      if (this.getIsLead()) {
        sql.append("lead, lead_status,");
      }
      sql.append("entered, modified, ");
      if (employee || orgId == 0) {
        sql.append("employee, ");
      }
      if (statusId > -1) {
        sql.append("status_id, ");
      }
      if (importId > -1) {
        sql.append("import_id, ");
      }
      if (secretWord != null) {
        sql.append("secret_word, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?, ");
      }
      sql.append("?, ?, ?, ?, ?, ?, ?, ?, ");
      sql.append("?,?,?,?,?,?,?,");
      if (this.getIsLead()) {
        sql.append("?, ?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      if (modified != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      if (employee || orgId == 0) {
        sql.append("?, ");
      }
      if (statusId > -1) {
        sql.append("?, ");
      }
      if (importId > -1) {
        sql.append("?, ");
      }
      if (secretWord != null) {
        sql.append("?, ");
      }
      sql.append("?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, this.getUserId());
      pst.setString(++i, this.getNameFirst());
      pst.setString(++i, this.getNameLast());
      DatabaseUtils.setInt(pst, ++i, this.getOwner());
      pst.setBoolean(++i, this.getPrimaryContact());
      if (orgId > 0) {
        pst.setString(++i, orgName);
      } else {
        pst.setString(++i, company);
      }
      pst.setString(++i, accountNumber);
      pst.setInt(++i, accessType);
      DatabaseUtils.setInt(pst, ++i, this.getSource());
      DatabaseUtils.setInt(pst, ++i, this.getRating());
      pst.setString(++i, this.getComments());
      DatabaseUtils.setTimestamp(pst, ++i, this.getAssignedDate());
      DatabaseUtils.setTimestamp(pst, ++i, this.getConversionDate());
      DatabaseUtils.setTimestamp(pst, ++i, this.getLeadTrashedDate());
      DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setString(++i, this.getAdditionalNames());
      pst.setString(++i, this.getNickname());
      pst.setTimestamp(++i, this.getBirthDate());
      pst.setString(++i, this.getRole());
      DatabaseUtils.setInt(pst, ++i, this.getSiteId());
      pst.setDouble(++i, this.getRevenue());
      DatabaseUtils.setInt(pst, ++i, this.getIndustryTempCode());
      pst.setDouble(++i, this.getPotential());
      DatabaseUtils.setInt(pst, ++i, this.getEmployees());
      pst.setString(++i, this.getDunsType());
      pst.setString(++i, this.getDunsNumber());
      pst.setString(++i, this.getBusinessNameTwo());
      pst.setInt(++i, this.getYearStarted());
      DatabaseUtils.setInt(pst, ++i, this.getSicCode());
      pst.setString(++i, this.getSicDescription());
      if (this.getIsLead()) {
        pst.setBoolean(++i, this.getIsLead());
        DatabaseUtils.setInt(pst, ++i, this.getLeadStatus());
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      if (employee || orgId == 0) {
        pst.setBoolean(++i, true);
      }
      if (statusId > -1) {
        pst.setInt(++i, this.getStatusId());
      }
      if (importId > -1) {
        pst.setInt(++i, this.getImportId());
      }
      if (secretWord != null) {
        pst.setString(++i, this.getSecretWord());
      }
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "contact_contact_id_seq", id);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Contact-> ContactID: " + this.getId());
      }

      //Insert the phone numbers if there are any
      Iterator iphone = phoneNumberList.iterator();
      while (iphone.hasNext()) {
        ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber) iphone.next();
        thisPhoneNumber.process(
            db, id, this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the addresses if there are any
      Iterator iaddress = addressList.iterator();
      while (iaddress.hasNext()) {
        ContactAddress thisAddress = (ContactAddress) iaddress.next();
        thisAddress.process(db, id, this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the email addresses if there are any
      Iterator iemail = emailAddressList.iterator();
      while (iemail.hasNext()) {
        ContactEmailAddress thisEmailAddress = (ContactEmailAddress) iemail.next();
        thisEmailAddress.process(
            db, id, this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the text message addresses if there are any
      Iterator itextMessageAddress = textMessageAddressList.iterator();
      while (itextMessageAddress.hasNext()) {
        ContactTextMessageAddress thisTextMessageAddress = (ContactTextMessageAddress) itextMessageAddress.next();
        thisTextMessageAddress.process(
            db, id, this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the instant message addresses if there are any
      Iterator instantMessageAddress = instantMessageAddressList.iterator();
      while (instantMessageAddress.hasNext()) {
        ContactInstantMessageAddress thisInstantMessageAddress = (ContactInstantMessageAddress) instantMessageAddress.next();
        thisInstantMessageAddress.process(
            db, id, this.getEnteredBy(), this.getModifiedBy());
      }

      this.update(db, true);
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   * Update the contact's information
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;
    boolean doCommit = false;
    try {
      if (doCommit = db.getAutoCommit()) {
        db.setAutoCommit(false);
      }
      resultCount = this.update(db, false);
      if (this.getPrimaryContact() && !this.getIsLead() && !this.getEmployee()) {
        Organization thisOrg = new Organization(db, this.getOrgId());
        if (!((thisOrg.getNameFirst() != null && this.getNameFirst() != null && thisOrg.getNameFirst().equals(this.getNameFirst())) &&
            (thisOrg.getNameLast() != null && this.getNameLast() != null && thisOrg.getNameLast().equals(this.getNameLast())) &&
            (thisOrg.getNameMiddle() != null && this.getNameMiddle() != null && thisOrg.getNameMiddle().equals(this.getNameMiddle()))) ||
            (thisOrg.getOwnerId() != this.getOwner() && this.getLeadStatus() > -1)) {
          thisOrg.setNameFirst(this.getNameFirst());
          thisOrg.setNameLast(this.getNameLast());
          thisOrg.setNameMiddle(this.getNameMiddle());
          thisOrg.setName(thisOrg.getNameLastFirstMiddle());
          if (this.getLeadStatus() != -1) {
            thisOrg.setOwner(this.getOwner());
          }
          thisOrg.update(db);
        }
      }
      //Process the phone numbers if there are any
      processPhoneNumbers(db);
      //Process the addresses if there are any
      processAddress(db);
      //Process the email addresses if there are any
      processEmailAddress(db);
      //Process the text message addresses if there are any
      this.processTextMessageAddress(db);
      //Process the instant message addresses if there are any
      this.processInstantMessageAddress(db);
      if (doCommit) {
        db.commit();
      }
    } catch (Exception e) {
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  void processPhoneNumbers(Connection db) throws SQLException {
    Iterator iphone = phoneNumberList.iterator();
    while (iphone.hasNext()) {
      ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber) iphone.next();
      thisPhoneNumber.process(
          db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  void processAddress(Connection db) throws SQLException {
    Iterator iaddress = addressList.iterator();
    while (iaddress.hasNext()) {
      ContactAddress thisAddress = (ContactAddress) iaddress.next();
      thisAddress.process(
          db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  void processEmailAddress(Connection db) throws SQLException {
    Iterator iemail = emailAddressList.iterator();
    while (iemail.hasNext()) {
      ContactEmailAddress thisEmailAddress = (ContactEmailAddress) iemail.next();
      thisEmailAddress.process(
          db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  void processTextMessageAddress(Connection db) throws SQLException {
    Iterator itextMessageAddress = textMessageAddressList.iterator();
    while (itextMessageAddress.hasNext()) {
      ContactTextMessageAddress thistextMessageAddress = (ContactTextMessageAddress) itextMessageAddress.next();
      thistextMessageAddress.process(
          db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  void processInstantMessageAddress(Connection db) throws SQLException {
    Iterator instantMessageAddress = instantMessageAddressList.iterator();
    while (instantMessageAddress.hasNext()) {
      ContactInstantMessageAddress thisInstantMessageAddress = (ContactInstantMessageAddress) instantMessageAddress.next();
      thisInstantMessageAddress.process(
          db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
    }
  }


  /**
   * Delete the current object from the database
   *
   * @param db           Description of Parameter
   * @param baseFilePath Description of the Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      if (this.orgId == -1) {
        // if the contact is a general contact
        // check if contact has related records
        if (this.hasRelatedRecords(db)) {
          this.disable(db);
        } else {
          this.deleteFromDatabase(db, baseFilePath);
        }
      } else if (this.orgId > 0) {
        // if the contact is an account contact
        //is user (irrespective of whether they are enabled or disabled)
        this.checkUserAccount(db);
        if (this.hasAccount) {
          User tmpUser = new User(db, userId);
          //disable user account
          tmpUser.disable(db);
          //disable contact
          this.disable(db);
          //if account is being deleted
          if (forceDelete) {
            //remove association from account
            this.setOrgId(Constants.UNDEFINED);
            // Copy Organization name to company name to infer earlier
            // association.
            this.setCompany(this.getOrgName());
            if (this.getPrimaryContact()) {
              this.setPrimaryContact(false);
            }
            this.update(db, false);
          }
        } else {
          // if not a user
          // check if contact has related records
          if (this.hasRelatedRecords(db)) {
            this.disable(db);
            //if account is being deleted
            if (forceDelete) {
              //remove association from account
              this.setOrgId(Constants.UNDEFINED);
              // Copy Organization name to company name to infer earlier
              // association.
              this.setCompany(this.getOrgName());
              this.setOrgName("");
              if (this.getPrimaryContact()) {
                this.setPrimaryContact(false);
              }
              this.update(db, false);
            }
          } else {
            this.deleteFromDatabase(db, baseFilePath);
          }
        }
      } else if (this.orgId == 0) {
        // if the contact is an employee
        //is user (irrespective of whether they are enabled or disabled)
        this.checkUserAccount(db);
        if (this.hasAccount) {
          User tmpUser = new User(db, userId);
          //disable user account
          tmpUser.disable(db);
          //disable contact
          this.disable(db);
        } else {
          // if not a user
          // check if contact has related records
          if (this.hasRelatedRecords(db)) {
            this.disable(db);
          } else {
            this.deleteFromDatabase(db, baseFilePath);
          }
        }
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
   * Deletes contact records from the database
   *
   * @param db           Description of the Parameter
   * @param baseFilePath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void deleteFromDatabase(Connection db, String baseFilePath) throws SQLException {

    //delete folder records
    CustomFieldRecordList folderList = new CustomFieldRecordList();
    folderList.setLinkModuleId(Constants.CONTACTS);
    folderList.setLinkItemId(this.getId());
    folderList.buildList(db);
    folderList.delete(db);
    folderList = null;

    CallList callList = new CallList();
    callList.setContactId(this.getId());
    if (this.getOrgId() != -1) {
      callList.setOppCallsOnly(Constants.FALSE);
    }
    if (this.isTrashed()) {
      callList.setIncludeOnlyTrashed(true);
    }
    callList.buildList(db);
    callList.delete(db);
    callList = null;

    if (this.getOrgId() == -1) {
      OpportunityHeaderList opportunityHeaderList = new OpportunityHeaderList();
      opportunityHeaderList.setContactId(this.id);
      opportunityHeaderList.buildList(db);
      opportunityHeaderList.delete(db, (ActionContext) null, baseFilePath);

      opportunityHeaderList = new OpportunityHeaderList();
      opportunityHeaderList.setContactId(this.id);
      opportunityHeaderList.setIncludeOnlyTrashed(true);
      opportunityHeaderList.buildList(db);
      opportunityHeaderList.delete(db, (ActionContext) null, baseFilePath);
    }

    PreparedStatement pst = null;

    pst = db.prepareStatement(
        "DELETE FROM contact_phone " +
            "WHERE contact_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    pst = db.prepareStatement(
        "DELETE FROM contact_emailaddress " +
            "WHERE contact_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    pst = db.prepareStatement(
        "DELETE FROM contact_address " +
            "WHERE contact_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    pst = db.prepareStatement(
        "DELETE FROM contact_textmessageaddress " +
            "WHERE contact_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    pst = db.prepareStatement(
        "DELETE FROM contact_imaddress " +
            "WHERE contact_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    int fieldId = -1;
    pst = db.prepareStatement(
        "SELECT id " +
            "FROM search_fields " +
            "WHERE description = ? ");
    pst.setString(1, "Contact ID");
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      fieldId = rs.getInt("id");
    }
    rs.close();
    pst.close();

    if (fieldId > -1) {
      pst = db.prepareStatement(
          "DELETE FROM saved_criteriaelement " +
              "WHERE field = ? " +
              "AND value_id = ? ");
      pst.setInt(1, fieldId);
      pst.setInt(2, this.getId());
      pst.execute();
      pst.close();
    }

    pst = db.prepareStatement(
        "DELETE FROM excluded_recipient " +
            "WHERE contact_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    // delete all types associated with this contact
    pst = db.prepareStatement(
        "DELETE FROM contact_type_levels " +
            "WHERE contact_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    // delete all task links associated with this contact
    pst = db.prepareStatement(
        "DELETE FROM tasklink_contact " +
            "WHERE contact_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    //Delete the history of the inbox message associated with this contact
    pst = db.prepareStatement(
        "DELETE FROM history " +
            "WHERE link_object_id = ? " +
            "AND link_item_id IN ( " +
            "SELECT id FROM cfsinbox_messagelink WHERE sent_to = ?) ");
    pst.setInt(1, OrganizationHistory.CFSNOTE);
    pst.setInt(2, this.getId());
    pst.execute();
    pst.close();

    // delete all inbox message links associated with this contact
    pst = db.prepareStatement(
        "DELETE FROM cfsinbox_messagelink " +
            "WHERE sent_to = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    // delete the action item log data for this contact
    pst = db.prepareStatement(
        "DELETE FROM action_item_log " +
            "WHERE item_id IN (SELECT item_id FROM action_item WHERE link_item_id = ?) ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    // delete from any action list this contact appeared on
    pst = db.prepareStatement(
        "DELETE FROM action_item " +
            "WHERE link_item_id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    // delete the contact history
    pst = db.prepareStatement(
        "DELETE FROM history " +
            "WHERE contact_id = ?");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

    //remove any links to this object from action step work
    int i = 0;
    pst = db.prepareStatement(
        "UPDATE action_item_work " +
            "SET link_item_id = ?, " +
            "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "WHERE link_module_id = ? " +
            "AND link_item_id = ? ");
    DatabaseUtils.setInt(pst, ++i, -1);
    pst.setInt(++i, Constants.CONTACTS);
    pst.setInt(++i, this.getId());
    pst.executeUpdate();
    pst.close();

    // finally, delete the contact
    pst = db.prepareStatement(
        "DELETE FROM contact " +
            "WHERE contact_id = ?");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();

  }


  /**
   * Resets the types for this Contact
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean resetType(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Contact ID not specified");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM contact_type_levels WHERE contact_id = ? ");
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();

    i = 0;
    pst = db.prepareStatement(
        "UPDATE contact " +
            "set employee = ?, " +
            "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "WHERE contact_id = ? ");
    pst.setBoolean(++i, false);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();

    return true;
  }


  /**
   * Inserts Type of Contact.
   *
   * @param db      Description of the Parameter
   * @param type_id Description of the Parameter
   * @param level   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insertType(Connection db, int type_id, int level) throws SQLException {
    if (id == -1) {
      throw new SQLException("No Contact ID Specified");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO contact_type_levels " +
            "(contact_id, type_id, " + DatabaseUtils.addQuotes(db, "level") + ") " +
            "VALUES (?, ?, ?) ");
    pst.setInt(++i, this.getId());
    pst.setInt(++i, type_id);
    pst.setInt(++i, level);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   * Performs a query and sets whether this user has an account or not
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.25
   */
  public void checkUserAccount(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT user_id " +
            "FROM " + DatabaseUtils.addQuotes(db, "access") + " " +
            "WHERE contact_id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      userId = rs.getInt("user_id");
      setHasAccount(true);
    } else {
      userId = -1;
      setHasAccount(false);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildTypes(Connection db) throws SQLException {
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT ctl.type_id, lct.description, lct.enabled " +
            "FROM contact_type_levels ctl " +
            "LEFT JOIN lookup_contact_types lct " +
            "ON ctl.type_id = lct.code " +
            "WHERE ctl.contact_id = ? " +
            "ORDER BY ctl." + DatabaseUtils.addQuotes(db, "level") + " ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, id);
    rs = DatabaseUtils.executeQuery(db, pst);
    while (rs.next()) {
      LookupElement thisType = new LookupElement();
      thisType.setCode(rs.getInt("type_id"));
      thisType.setDescription(rs.getString("description"));
      thisType.setEnabled(rs.getBoolean("enabled"));
      types.add(thisType);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "Contact-> Built contact type: contact/" + id + " type/" + thisType.getCode() + " " + thisType.getDescription());
      }
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void checkEnabledUserAccount(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }
    checkUserAccount(db);
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
            "FROM " + DatabaseUtils.addQuotes(db, "access") + " " +
            "WHERE contact_id = ? " +
            "AND enabled = ? ");
    pst.setInt(1, this.getId());
    pst.setBoolean(2, true);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      setHasEnabledAccount(true);
    } else {
      setHasEnabledAccount(false);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db         Description of Parameter
   * @param campaignId Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void checkExcludedFromCampaign(Connection db, int campaignId) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
            "FROM excluded_recipient " +
            "WHERE contact_id = ? " +
            "AND campaign_id = ? ");
    pst.setInt(1, this.getId());
    pst.setInt(2, campaignId);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      setExcludedFromCampaign(true);
    } else {
      setExcludedFromCampaign(false);
    }
    rs.close();
    pst.close();
  }


  /**
   * On a typical update of the Contact record, do not execute this method
   * directly, use Update(db), this was intended for the insert statement.
   *
   * @param db       Description of Parameter
   * @param override Overrides checking the lastmodified date on an
   *                 insert
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      throw new SQLException("Contact ID was not specified");
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE contact " +
            "SET company = ?, title = ?, department = ?, namesalutation = ?, " +
            "namefirst = ?, namelast = ?, " +
            "namemiddle = ?, namesuffix = ?, notes = ?, owner = ?, custom1 = ?, url = ?, " +
            "org_id = ?, primary_contact = ?, org_name = ?, access_type = ?,");
    sql.append(
        "source = ?, rating = ?, comments = ?, assigned_date = ?, conversion_date = ?, lead_trashed_date = ?, lead = ?, lead_status = ?, industry_temp_code = ?, potential = ?, ");
    sql.append(
        "no_email = ?, " +
            "no_mail = ?, " +
            "no_phone = ?, " +
            "no_textmessage = ?, " +
            "no_im = ?, " +
            "no_fax = ?, "
    );
    sql.append(
        "trashed_date = ?, additional_names = ?, nickname = ?, birthdate = ?, " +
            "" + DatabaseUtils.addQuotes(db, "role") + " = ?, employee_id = ?, site_id = ?, ");
    if (locale > -1) {
      sql.append("locale = ?, ");
    }
    if (employmentType > -1) {
      sql.append("employmentType = ?, ");
    }
    if (secretWord != null) {
      sql.append("secret_word = ?, ");
    }
    sql.append("startofday = ?, endofday = ?, ");
    sql.append("employees = ?, duns_type = ?, duns_number = ?, business_name_two = ?, year_started = ?, sic_code = ?, sic_description = ?, ");

    if (!override) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("modifiedby = ? WHERE contact_id = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null) ? "IS NULL " : "= ? "));
    }
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    if (orgId <= 0) {
      pst.setString(++i, this.getCompany());
    } else {
      pst.setNull(++i, java.sql.Types.VARCHAR);
    }
    pst.setString(++i, this.getTitle());
    if (department > 0) {
      pst.setInt(++i, this.getDepartment());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setString(++i, this.getNameSalutation());
    pst.setString(++i, this.getNameFirst());
    pst.setString(++i, this.getNameLast());
    pst.setString(++i, this.getNameMiddle());
    pst.setString(++i, this.getNameSuffix());
    pst.setString(++i, this.getNotes());
    DatabaseUtils.setInt(pst, ++i, this.getOwner());
    pst.setInt(++i, this.getCustom1());
    pst.setString(++i, this.getUrl());
    DatabaseUtils.setInt(pst, ++i, orgId);
    pst.setBoolean(++i, this.getPrimaryContact());
    if (orgId > 0) {
      pst.setString(++i, orgName);
    } else {
      pst.setString(++i, company);
    }
    pst.setInt(++i, accessType);
    DatabaseUtils.setInt(pst, ++i, this.getSource());
    DatabaseUtils.setInt(pst, ++i, this.getRating());
    pst.setString(++i, this.getComments());
    DatabaseUtils.setTimestamp(pst, ++i, this.getAssignedDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getConversionDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getLeadTrashedDate());
    pst.setBoolean(++i, this.getIsLead());
    DatabaseUtils.setInt(pst, ++i, this.getLeadStatus());
    DatabaseUtils.setInt(pst, ++i, this.getIndustryTempCode());
    pst.setDouble(++i, this.getPotential());
    pst.setBoolean(++i, this.getNoEmail());
    pst.setBoolean(++i, this.getNoMail());
    pst.setBoolean(++i, this.getNoPhone());
    pst.setBoolean(++i, this.getNoTextMessage());
    pst.setBoolean(++i, this.getNoInstantMessage());
    pst.setBoolean(++i, this.getNoFax());
    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
    pst.setString(++i, this.getAdditionalNames());
    pst.setString(++i, this.getNickname());
    pst.setTimestamp(++i, this.getBirthDate());
    pst.setString(++i, this.getRole());

    if (employeeId != null && !"".equals(employeeId.trim())) {
      pst.setString(++i, this.getEmployeeId());
    } else {
      pst.setString(++i, null);
      // NOTE: the following was submitted for Derby, and fails the others. why?
      //pst.setString(++i, "");
    }
    DatabaseUtils.setInt(pst, ++i, this.getSiteId());
    if (locale > -1) {
      pst.setInt(++i, this.getLocale());
    }
    if (employmentType > -1) {
      pst.setInt(++i, this.getEmploymentType());
    }
    if (secretWord != null) {
      pst.setString(++i, this.getSecretWord());
    }
    pst.setString(++i, this.getStartOfDay());
    pst.setString(++i, this.getEndOfDay());
    DatabaseUtils.setInt(pst, ++i, this.getEmployees());
    pst.setString(++i, this.getDunsType());
    pst.setString(++i, this.getDunsNumber());
    pst.setString(++i, this.getBusinessNameTwo());
    pst.setInt(++i, this.getYearStarted());
    DatabaseUtils.setInt(pst, ++i, this.getSicCode());
    pst.setString(++i, this.getSicDescription());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    resultCount = pst.executeUpdate();
    pst.close();

    //Remove all contact types, add new list
    if (typeList != null) {
      resetType(db);
      int lvlcount = 0;
      for (int k = 0; k < typeList.size(); k++) {
        String val = (String) typeList.get(k);
        if (val != null && !(val.equals(""))) {
          int type_id = Integer.parseInt((String) typeList.get(k));
          lvlcount++;
          insertType(db, type_id, lvlcount);
        } else {
          lvlcount--;
        }
      }
    }
    return resultCount;
  }


  /**
   * Update name and address
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void updateNameandAddress(Connection db) throws SQLException {
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE contact " +
            "SET secret_word = ?, " +
            "additional_names = ?, " +
            "nickname = ?, " +
            "birthdate = ?, " +
            "title = ?, " +
            "" + DatabaseUtils.addQuotes(db, "role") + " = ?, " +
            "information_update_date = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "WHERE contact_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getSecretWord());
    pst.setString(++i, this.getAdditionalNames());
    pst.setString(++i, this.getNickname());
    pst.setTimestamp(++i, this.getBirthDate());
    pst.setString(++i, this.getTitle());
    pst.setString(++i, this.getRole());
    pst.setInt(++i, this.getId());
    pst.executeUpdate();
    pst.close();
    //Process the phone numbers if there are any
    processPhoneNumbers(db);
    //Insert the addresses if there are any
    processAddress(db);
    //Insert the email addresses if there are any
    processEmailAddress(db);
    //Insert the instant message addresses if there are any
    processInstantMessageAddress(db);
    //Insert the text message addresses if there are any
    processTextMessageAddress(db);
  }

 /**
  * Populates this object from a shortened result set
  *
  * @param rs
  * @throws SQLException
  */
  protected void buildShortRecord(ResultSet rs) throws SQLException{
	  // c.user_id, c.contact_id, c.namelast, c.namefirst, o.name, c.owner, c.status_id, c.entered
	  this.setId(rs.getInt("contact_id"));
	    userId = DatabaseUtils.getInt(rs, "user_id");
	    nameLast = rs.getString("namelast");
	    nameFirst = rs.getString("namefirst");
	    company = rs.getString("company");
	    orgName = rs.getString("name");
	    statusId = DatabaseUtils.getInt(rs, "status_id");
	    owner = DatabaseUtils.getInt(rs, "owner");
	    entered = rs.getTimestamp("entered");
      isLead = rs.getBoolean("lead");
      leadStatus = DatabaseUtils.getInt(rs, "lead_status");
      orgId = DatabaseUtils.getInt(rs, "org_id");
      siteId = DatabaseUtils.getInt(rs, "site_id");
  }

  /**
   * Populates this object from a result set
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //contact table
    this.setId(rs.getInt("contact_id"));
    userId = DatabaseUtils.getInt(rs, "user_id");
    orgId = DatabaseUtils.getInt(rs, "org_id");
    company = rs.getString("company");
    title = rs.getString("title");
    //TODO: Determine why department was made to default to 0? department = DatabaseUtils.getInt(rs, "department", 0);
    department = DatabaseUtils.getInt(rs, "department");
    nameSalutation = rs.getString("namesalutation");
    nameLast = rs.getString("namelast");
    nameFirst = rs.getString("namefirst");
    nameMiddle = rs.getString("namemiddle");
    nameSuffix = rs.getString("namesuffix");
    assistant = DatabaseUtils.getInt(rs, "assistant");
    notes = rs.getString("notes");
    site = rs.getString("site");
    locale = rs.getInt("locale");
    employeeId = rs.getString("employee_id");
    employmentType = rs.getInt("employmenttype");
    startOfDay = rs.getString("startofday");
    endOfDay = rs.getString("endofday");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    owner = DatabaseUtils.getInt(rs, "owner");
    custom1 = rs.getInt("custom1");
    url = rs.getString("url");
    primaryContact = rs.getBoolean("primary_contact");
    employee = rs.getBoolean("employee");
    orgName = rs.getString("org_name");
    accessType = rs.getInt("access_type");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    importId = DatabaseUtils.getInt(rs, "import_id");
    isLead = rs.getBoolean("lead");
    leadStatus = DatabaseUtils.getInt(rs, "lead_status");
    source = DatabaseUtils.getInt(rs, "source");
    rating = DatabaseUtils.getInt(rs, "rating");
    comments = rs.getString("comments");
    conversionDate = rs.getTimestamp("conversion_date");
    additionalNames = rs.getString("additional_names");
    nickname = rs.getString("nickname");
    birthDate = rs.getTimestamp("birthdate");
    role = rs.getString("role");
    trashedDate = rs.getTimestamp("trashed_date");
    secretWord = rs.getString("secret_word");
    accountNumber = rs.getString("account_number");
    revenue = rs.getDouble("revenue");
    industryTempCode = DatabaseUtils.getInt(rs, "industry_temp_code");
    potential = rs.getDouble("potential");
    noEmail = rs.getBoolean("no_email");
    noMail = rs.getBoolean("no_mail");
    noPhone = rs.getBoolean("no_phone");
    noTextMessage = rs.getBoolean("no_textmessage");
    noInstantMessage = rs.getBoolean("no_im");
    noFax = rs.getBoolean("no_fax");
    siteId = DatabaseUtils.getInt(rs, "site_id");
    assignedDate = rs.getTimestamp("assigned_date");
    leadTrashedDate = rs.getTimestamp("lead_trashed_date");
    employees = DatabaseUtils.getInt(rs, "employees");
    dunsType = rs.getString("duns_type");
    dunsNumber = rs.getString("duns_number");
    businessNameTwo = rs.getString("business_name_two");
    sicCode = DatabaseUtils.getInt(rs, "sic_code");
    yearStarted = rs.getInt("year_started");
    sicDescription = rs.getString("sic_description");

    //organization table
    orgEnabled = rs.getBoolean("orgenabled");
    orgTrashedDate = rs.getTimestamp("orgtrasheddate");

    //lookup_department table
    departmentName = rs.getString("departmentname");

    //contact_address table
    city = rs.getString("city");
    postalcode = rs.getString("postalcode");
    //lookup_site_id table
    siteName = rs.getString("site_id_name");

    //lead descriptions
    industryName = rs.getString("industry_name");
    sourceName = rs.getString("source_name");
    ratingName = rs.getString("rating_name");
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   * @since 1.30?
   */
  private boolean hasRelatedRecords(Connection db) throws SQLException {

    if (this.getOrgId() > -1) {
      OpportunityHeaderList opportunityHeaderList = new OpportunityHeaderList();
      opportunityHeaderList.setContactId(this.id);
      opportunityHeaderList.buildList(db);
      if (opportunityHeaderList.size() > 0) {
        return true;
      }

      //Pipeline calls can not be deleted for an account contact.
      CallList calls = new CallList();
      calls.setContactId(this.getId());
      calls.setOppCallsOnly(Constants.TRUE);
      calls.buildList(db);
      if (calls.size() > 0) {
        return true;
      }
    }

    TicketList ticketList = new TicketList();
    ticketList.setContactId(this.id);
    ticketList.buildList(db);
    if (ticketList.size() > 0) {
      return true;
    }

    AssetList assetList = new AssetList();
    assetList.setContactId(this.id);
    assetList.buildList(db);
    if (assetList.size() > 0) {
      return true;
    }

    ServiceContractList serviceContractList = new ServiceContractList();
    serviceContractList.setContactId(this.id);
    serviceContractList.buildList(db);
    if (serviceContractList.size() > 0) {
      return true;
    }

    QuoteList quoteList = new QuoteList();
    quoteList.setContactId(this.id);
    quoteList.buildList(db);
    if (quoteList.size() > 0) {
      return true;
    }

    RecipientList recipientList = new RecipientList();
    recipientList.setContactId(this.id);
    recipientList.buildList(db);
    if (recipientList.size() > 0) {
      return true;
    }

    SurveyResponseList surveyResponseList = new SurveyResponseList();
    surveyResponseList.setContactId(this.id);
    surveyResponseList.buildList(db);
    if (surveyResponseList.size() > 0) {
      return true;
    }

    // is an excluded from recieving a campaign message
    int excludedCount = 0;
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT COUNT(*) AS  excludedcount " +
            "FROM excluded_recipient " +
            "WHERE contact_id = ? ");
    pst.setInt(1, this.getId());
    rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      excludedCount = rs.getInt("excludedcount");
    }
    rs.close();
    pst.close();
    if (excludedCount > 0) {
      return true;
    }

    // has been sent messages
    //note: use list classes
    int messageCount = 0;
    pst = null;
    pst = db.prepareStatement(
        "SELECT COUNT(*) AS  messagecount " +
            "FROM cfsinbox_messagelink " +
            "WHERE sent_to = ? ");
    pst.setInt(1, this.getId());
    rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      messageCount = rs.getInt("messagecount");
    }
    rs.close();
    pst.close();
    if (messageCount > 0) {
      return true;
    }

    // associated with a project?

    // associated with a document store?

    // associated with an action items and logs?
    //note: use list classes (ActionItemLogList)
    int actionItemLogCount = 0;
    pst = db.prepareStatement(
        "SELECT COUNT(*) AS  actionitemlogcount " +
            "FROM action_item_log " +
            "WHERE item_id IN (SELECT item_id FROM action_item WHERE link_item_id = ?) ");
    pst.setInt(1, this.getId());
    rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      actionItemLogCount = rs.getInt("actionitemlogcount");
    }
    rs.close();
    pst.close();
    if (actionItemLogCount > 0) {
      return true;
    }

    //note: use list classes (create ActionItemList)
    int actionItemCount = 0;
    pst = db.prepareStatement(
        "SELECT COUNT(*) AS  actionitemcount " +
            "FROM action_item " +
            "WHERE link_item_id = ? ");
    pst.setInt(1, this.getId());
    rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      actionItemCount = rs.getInt("actionitemcount");
    }
    rs.close();
    pst.close();
    if (actionItemCount > 0) {
      return true;
    }

    return false;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param newOwner Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean reassign(Connection db, int newOwner) throws SQLException {
    int result = -1;
    this.setOwner(newOwner);
    result = this.update(db);
    if (result == -1) {
      return false;
    }
    return true;
  }


  /**
   * Updates the contact record with a new organization id
   *
   * @param db        Description of the Parameter
   * @param contactId Description of the Parameter
   * @param orgId     Description of the Parameter
   * @param orgName   Description of the Parameter
   * @param userId    Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void move(Connection db, int contactId, int orgId, String orgName, int userId) throws SQLException {
    int i = 0;
    String timestamp = DatabaseUtils.getCurrentTimestamp(db);
    PreparedStatement pst = db.prepareStatement(
        "UPDATE contact " +
            "SET org_id = ?, org_name = ?, modifiedBy = ?, modified = " + timestamp + " " +
            "WHERE contact_id = ? ");
    pst.setInt(++i, orgId);
    pst.setString(++i, orgName);
    pst.setInt(++i, userId);
    pst.setInt(++i, contactId);
    pst.executeUpdate();
    pst.close();
  }


  /**
   * Combines the first and last name of a contact, depending on the length of
   * the strings
   *
   * @param nameLast  Description of the Parameter
   * @param nameFirst Description of the Parameter
   * @return The nameLastFirst value
   */
  public static String getNameLastFirst(String nameLast, String nameFirst) {
    StringBuffer out = new StringBuffer();
    if (nameLast != null && nameLast.trim().length() > 0) {
      out.append(nameLast);
    }
    if (nameFirst != null && nameFirst.trim().length() > 0) {
      if (out.length() > 0) {
        out.append(", ");
      }
      out.append(nameFirst);
    }
    if (out.toString().length() == 0) {
      return null;
    }
    return out.toString().trim();
  }

  /**
   * Combines the first and last name of a contact and the title, depending on the length of
   * the strings
   *
   * @param nameLast  Description of the Parameter
   * @param nameFirst Description of the Parameter
   * @title nameFirst Description of the Parameter
   * @return The nameLastFirst value
   */
  public static String getNameLastFirstAndTitle(String nameLast, String nameFirst, String title) {
		if (title != null && !"".equals(title)) {
			return Contact.getNameLastFirst(nameLast, nameFirst) + " - " + title;
		}

		return Contact.getNameLastFirst(nameLast, nameFirst);
	}


  /**
   * Gets the nameFirstLast attribute of the Contact class
   *
   * @param nameFirst Description of the Parameter
   * @param nameLast  Description of the Parameter
   * @return The nameFirstLast value
   */
  public static String getNameFirstLast(String nameFirst, String nameLast) {
    StringBuffer out = new StringBuffer();
    if (nameFirst != null && nameFirst.trim().length() > 0) {
      out.append(nameFirst);
    }
    if (nameLast != null && nameLast.trim().length() > 0) {
      if (out.length() > 0) {
        out.append(" ");
      }
      out.append(nameLast);
    }
    if (out.toString().length() == 0) {
      return null;
    }
    return out.toString().trim();
  }


  /**
   * Gets the nameFirstLastOrCompany attribute of the Contact class
   *
   * @param tmpNameFirst Description of the Parameter
   * @param tmpNameLast  Description of the Parameter
   * @param tmpCompany   Description of the Parameter
   * @return The nameFirstLastOrCompany value
   */
  public static String getNameFirstLastOrCompany(String tmpNameFirst, String tmpNameLast, String tmpCompany) {
    String firstLastOrCompany = Contact.getNameFirstLast(tmpNameFirst, tmpNameLast);

    if ((firstLastOrCompany == null) || "".equals(firstLastOrCompany)) {
      firstLastOrCompany = tmpCompany;
    }

    return firstLastOrCompany;
  }


  /**
   * Makes a list of this a Contact's dependencies to other entities.
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    DependencyList dependencyList = new DependencyList();
    try {
      int oppCount = OpportunityList.retrieveRecordCount(
          db, Constants.CONTACTS, this.getId());
      if (oppCount > 0) {
        this.setHasOpportunities(true);
        Dependency thisDependency = new Dependency();
        thisDependency.setName("opportunities");
        thisDependency.setCount(oppCount);
        thisDependency.setCanDelete(this.getOrgId() == -1);
        dependencyList.add(thisDependency);
      }

      int i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as callcount " +
              "FROM call_log " +
              "WHERE contact_id = ? " +
              (this.getOrgId() != -1 ? "AND opp_id IS NULL " : ""));
      pst.setInt(++i, this.getId());
      rs = DatabaseUtils.executeQuery(db, pst);
      if (rs.next()) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("activities");
        thisDependency.setCount(rs.getInt("callcount"));
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }
      rs.close();
      pst.close();

      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as foldercount " +
              "FROM custom_field_record cfr " +
              "WHERE cfr.link_module_id = " + Constants.CONTACTS + " " +
              "AND cfr.link_item_id = ? ");
      pst.setInt(++i, this.getId());
      rs = DatabaseUtils.executeQuery(db, pst);
      if (rs.next()) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("folders");
        thisDependency.setCount(rs.getInt("foldercount"));
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }
      rs.close();
      pst.close();

      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as ticketcount " +
              "FROM ticket " +
              "WHERE contact_id = ? " +
              "AND trashed_date IS NULL " +
              "AND ticketid NOT IN (SELECT ticket_id FROM ticketlink_project) ");
      pst.setInt(++i, this.getId());
      rs = DatabaseUtils.executeQuery(db, pst);
      if (rs.next()) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("tickets");
        thisDependency.setCount(rs.getInt("ticketcount"));
        thisDependency.setCanDelete(false);
        dependencyList.add(thisDependency);
      }
      rs.close();
      pst.close();

      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as taskcount " +
              "FROM tasklink_contact " +
              "WHERE contact_id = ? ");
      pst.setInt(++i, this.getId());
      rs = DatabaseUtils.executeQuery(db, pst);
      if (rs.next()) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("tasks");
        thisDependency.setCount(rs.getInt("taskcount"));
        thisDependency.setCanDelete(false);
        dependencyList.add(thisDependency);
      }

      rs.close();
      pst.close();

      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as servicecontractcount " +
              "FROM service_contract " +
              "WHERE contact_id = ? AND trashed_date IS NULL");
      pst.setInt(++i, this.getId());
      rs = DatabaseUtils.executeQuery(db, pst);
      if (rs.next()) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("contracts");
        thisDependency.setCount(rs.getInt("servicecontractcount"));
        thisDependency.setCanDelete(false);
        dependencyList.add(thisDependency);
      }

      rs.close();
      pst.close();

      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as assetcount " +
              "FROM asset " +
              "WHERE contact_id = ? AND trashed_date IS NULL");
      pst.setInt(++i, this.getId());
      rs = DatabaseUtils.executeQuery(db, pst);
      if (rs.next()) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("assets");
        thisDependency.setCount(rs.getInt("assetcount"));
        thisDependency.setCanDelete(false);
        dependencyList.add(thisDependency);
      }

      rs.close();
      pst.close();

      Dependency quoteDependency = new Dependency();
      quoteDependency.setName("quotes");
      quoteDependency.setCount(
          QuoteList.retrieveRecordCount(db, Constants.CONTACTS, this.getId()));
      quoteDependency.setCanDelete(false);
      dependencyList.add(quoteDependency);
      /*
       *  i = 0;
       *  pst = db.prepareStatement(
       *  "SELECT count(*) as quotecount " +
       *  "FROM quote_entry " +
       *  "WHERE contact_id = ? ");
       *  pst.setInt(++i, this.getId());
       *  rs = pst.executeQuery();
       *  if (rs.next()) {
       *  Dependency thisDependency = new Dependency();
       *  thisDependency.setName("quotes");
       *  thisDependency.setCount(rs.getInt("quotecount"));
       *  thisDependency.setCanDelete(false);
       *  dependencyList.add(thisDependency);
       *  }
       *  rs.close();
       *  pst.close();
       */
      int recipientCount = 0;
      if ((recipientCount = RecipientList.retrieveRecordCount(
          db, Constants.CONTACTS, this.getId())) > 0) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("campaigns");
        thisDependency.setCount(recipientCount);
        thisDependency.setCanDelete(false);
        dependencyList.add(thisDependency);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void disable(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE contact " +
            "SET enabled = ?, " +
            "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "WHERE contact_id = ?");
    pst.setBoolean(1, false);
    pst.setInt(2, this.getId());
    pst.executeUpdate();
    pst.close();

    this.setEnabled(false);
    //Reset trashed timestamp as it does not have to
    //be checked by the CRON
    this.setTrashedDate((java.sql.Timestamp) null);
    this.update(db);
  }


  /**
   * Approves all records for a specific import
   *
   * @param db       Description of the Parameter
   * @param importId Description of the Parameter
   * @param status   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int updateImportStatus(Connection db, int importId, int status) throws SQLException {
    int count = 0;
    boolean commit = true;

    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      String sql = "UPDATE contact " +
          "SET status_id = ?, " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
          "WHERE import_id = ? ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, status);
      pst.setInt(++i, importId);
      count = pst.executeUpdate();
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
    return count;
  }


  /**
   * Gets the contactSiteId attribute of the Contact class
   *
   * @param db           Description of the Parameter
   * @param tmpContactId Description of the Parameter
   * @return The contactSiteId value
   * @throws SQLException Description of the Exception
   */
  public static int getContactSiteId(Connection db, int tmpContactId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int contactSiteId = -1;
    String sqlSelect =
        "SELECT site_id " +
            "FROM contact " +
            "WHERE contact_id = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setInt(++i, tmpContactId);
    rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      contactSiteId = DatabaseUtils.getInt(rs, "site_id");
    }
    rs.close();
    pst.close();
    return contactSiteId;
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param toTrash   Description of the Parameter
   * @param tmpUserId Description of the Parameter
   * @param context   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db, ActionContext context, boolean toTrash, int tmpUserId) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      StringBuffer sql = new StringBuffer();
      sql.append(
          "UPDATE contact " +
              "SET trashed_date = ? , " +
              "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
              "modifiedby = ? " +
              "WHERE contact_id = ? ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (toTrash) {
        DatabaseUtils.setTimestamp(
            pst, ++i, new Timestamp(System.currentTimeMillis()));
      } else {
        DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
      }
      DatabaseUtils.setInt(pst, ++i, tmpUserId);
      pst.setInt(++i, this.getId());
      /*
       *  If the contact is being reverted back to lead, trash only related data and history
       */
      if (!this.getCheckRevertingBackToLead()) {
        pst.executeUpdate();
      }
      pst.close();

      //Delete from the search criteria list and the action_item and its log
      if (toTrash) {
        int fieldId = -1;
        pst = db.prepareStatement(
            "SELECT id " +
                "FROM search_fields " +
                "WHERE description = ? ");
        pst.setString(1, "Contact ID");
        ResultSet rs = DatabaseUtils.executeQuery(db, pst);
        if (rs.next()) {
          fieldId = rs.getInt("id");
        }
        rs.close();
        pst.close();

        if (fieldId > -1) {
          pst = db.prepareStatement(
              "DELETE FROM saved_criteriaelement " +
                  "WHERE field = ? " +
                  "AND value_id = ? ");
          pst.setInt(1, fieldId);
          pst.setInt(2, this.getId());
          pst.execute();
          pst.close();
        }

        // delete the action item log data for this contact
        pst = db.prepareStatement(
            "DELETE FROM action_item_log " +
                "WHERE item_id IN (SELECT item_id FROM action_item WHERE link_item_id = ?) ");
        pst.setInt(1, this.getId());
        pst.execute();
        pst.close();

        // delete from any action list this contact appeared on
        pst = db.prepareStatement(
            "DELETE FROM action_item " +
                "WHERE link_item_id = ? ");
        pst.setInt(1, this.getId());
        pst.execute();
        pst.close();
      }

      //Disable the contact history for notes, activities and email messages
      // All the rest stay in the account history.
      ContactHistoryList historyList = new ContactHistoryList();
      historyList.setContactId(this.getId());
      historyList.setNotes(true);
      historyList.setActivities(true);
      historyList.setEmail(true);
      historyList.setShowDisabledWithEnabled(true);
      historyList.buildList(db);
      historyList.disableNotesInHistory(db, !toTrash);

      CallList callList = new CallList();
      callList.setContactId(this.getId());
      if (!toTrash) {
        callList.setIncludeOnlyTrashed(true);
      }
      callList.buildList(db);
      callList.updateStatus(db, toTrash, tmpUserId);

      if (this.getOrgId() == -1) {
        OpportunityList opportunityList = new OpportunityList();
        opportunityList.setContactId(this.getId());
        if (!toTrash) {
          opportunityList.setIncludeOnlyTrashed(true);
        }
        opportunityList.buildList(db);
        opportunityList.updateStatus(db, context, toTrash, tmpUserId);
      }

      this.checkUserAccount(db);
      if (this.hasAccount()) {
        User user = new User(db, userId);
        if (toTrash) {
          user.disable(db);
        } else {
          user.enable(db);
        }
      }

      //remove any links to this object from action step work
      i = 0;
      int contactLinkModuleId = ActionPlan.getMapIdGivenConstantId(db, ActionPlan.CONTACTS);
      pst = db.prepareStatement(
          "UPDATE action_item_work " +
              "SET link_item_id = ?, " +
              "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
              "WHERE link_module_id = ? " +
              "AND link_item_id = ? ");
      DatabaseUtils.setInt(pst, ++i, -1);
      pst.setInt(++i, contactLinkModuleId);
      pst.setInt(++i, this.getId());
      pst.executeUpdate();
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void enable(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE contact " +
            "SET enabled = ?, " +
            "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "WHERE contact_id = ?");
    pst.setBoolean(1, true);
    pst.setInt(2, this.getId());
    pst.executeUpdate();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void deleteMessages(Connection db) throws SQLException {
    PreparedStatement pst = null;
    //Delete the history of the inbox message associated with this contact
    pst = db.prepareStatement(
        "DELETE FROM history " +
            "WHERE link_object_id = ? " +
            "AND link_item_id IN ( " +
            "SELECT id FROM cfsinbox_messagelink WHERE sent_to = ?) ");
    pst.setInt(1, OrganizationHistory.CFSNOTE);
    pst.setInt(2, this.getId());
    pst.execute();
    pst.close();

    // delete all inbox message links associated with this contact
    pst = db.prepareStatement(
        "DELETE FROM cfsinbox_messagelink " +
            "WHERE sent_to = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param context      Description of the Parameter
   * @param baseFilePath Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db, ActionContext context, String baseFilePath) throws SQLException {
    if (delete(db, baseFilePath)) {
      invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    int oldId = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT owner " +
            "FROM contact " +
            "WHERE contact_id = ?");
    pst.setInt(1, this.getId());
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      oldId = rs.getInt("owner");
    }
    rs.close();
    pst.close();
    int result = update(db);
    if (result == 1) {
      invalidateUserData(context);
      if (oldId != this.getOwner()) {
        invalidateUserData(context, oldId);
      }
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db, ActionContext context) throws SQLException {
    if (insert(db)) {
      invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   */
  public void invalidateUserData(ActionContext context) {
    invalidateUserData(context, owner);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @param userId  Description of the Parameter
   */
  public void invalidateUserData(ActionContext context, int userId) {
    if (userId != -1) {
      ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute(
          "ConnectionElement");
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
          "SystemStatus")).get(ce.getUrl());
      systemStatus.getHierarchyList().getUser(userId).setIsValidLead(
          false, true);
    }
  }


  /**
   * Gets the htmlString attribute of the Contact object
   *
   * @param dependencies Description of the Parameter
   * @param systemStatus Description of the Parameter
   * @return The htmlString value
   */
  public String getHtmlString(DependencyList dependencies, SystemStatus systemStatus) {
    boolean canMove = true;
    Iterator i = dependencies.iterator();
    StringBuffer html = new StringBuffer();
    html.append("<br />");
    int count = 0;
    while (i.hasNext()) {
      Dependency thisDependency = (Dependency) i.next();
      if (thisDependency.getCount() > 0) {
        ++count;
        html.append("&nbsp;&nbsp;");
        if (thisDependency.getCanDelete()) {
          html.append("- ");
        } else {
          if ((thisDependency.getName().equals("opportunities") && this.getOrgId() == -1) || thisDependency.getName().equals(
              "activities") || thisDependency.getName().equals("folders")) {
            html.append("- ");
          } else {
            html.append("* ");
            canMove = false;
          }
        }
        if (systemStatus != null) {
          html.append(
              systemStatus.getLabel("dependency." + thisDependency.getName()) + " (" + thisDependency.getCount() + ")");
        }
        html.append("<br />");
      }
    }
    if (count == 0) {
      if (systemStatus != null) {
        html.append(
            "&nbsp;&nbsp;" + systemStatus.getLabel(
                "dependency.noDependencyForAction") + "<br />");
      }
    }
    if (!canMove) {
      if (systemStatus != null) {
        html.append(
            "<br />(*) " + systemStatus.getLabel(
                "dependency.preventingContactMove"));
        html.append(
            "<br />" + systemStatus.getLabel("dependency.note") + "<br />");
      }
    }
    return html.toString();
  }


  /**
   * Description of the Method
   *
   * @param dependencies Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean canMoveContact(DependencyList dependencies) {
    Iterator thisList = dependencies.iterator();
    boolean canMove = true;
    boolean othersPresent = false;
    while (thisList.hasNext()) {
      Dependency thisDependency = (Dependency) thisList.next();
      if (!thisDependency.getCanDelete() && thisDependency.getCount() > 0) {
        if (thisDependency.getName().equals("opportunities") || thisDependency.getName().equals(
            "activities") || thisDependency.getName().equals("folders")) {
          canMove = false;
        } else {
          othersPresent = true;
        }
      }
    }
    return (!othersPresent && canMove);
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public String toString() {
    return this.getNameFull();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void updatePrimaryContactInformation(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE contact SET primary_contact = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
            "WHERE contact_id = ? ");
    pst.setBoolean(1, this.getPrimaryContact());
    pst.setInt(2, this.getId());
    pst.executeUpdate();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db                Description of the Parameter
   * @param context           Description of the Parameter
   * @param dbNamePath        Description of the Parameter
   * @param newOrgId          Description of the Parameter
   * @param moveOpportunities Description of the Parameter
   * @param moveFolders       Description of the Parameter
   * @param moveActivities    Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean moveContact(Connection db, ActionContext context, String dbNamePath, int newOrgId, int moveOpportunities, int moveFolders, int moveActivities) throws SQLException {
    Organization organization = new Organization(db, newOrgId);
    OpportunityHeaderList opportunities = new OpportunityHeaderList();
    opportunities.setContactId(this.getId());
    opportunities.buildList(db);
    if (opportunities.size() > 0) {
      if (moveOpportunities == Constants.FALSE) {
        opportunities.delete(db, context, dbNamePath);
      } else if (moveOpportunities == Constants.TRUE) {
        opportunities.moveOpportunitiesToAccount(db, this.getOrgId());
      }
    }
    if (moveFolders == Constants.FALSE) {
      CustomFieldRecordList folderList = new CustomFieldRecordList();
      folderList.setLinkModuleId(Constants.CONTACTS);
      folderList.setLinkItemId(this.getId());
      folderList.buildList(db);
      if (folderList.size() > 0) {
        folderList.delete(db);
      }
    }
    CallList activities = new CallList();
    activities.setContactId(this.getId());
    activities.buildList(db);
    if (activities.size() > 0) {
      if (moveActivities == Constants.TRUE) {
        activities.reassignAccount(db, context, newOrgId);
      } else if (moveActivities == Constants.FALSE) {
        activities.delete(db);
      }
    }
    //Reset the contact information in any of the action steps.
    ActionItemWorkList workList = new ActionItemWorkList();
    workList.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.CONTACTS));
    workList.setLinkItemId(this.getId());
    workList.buildList(db);
    workList.resetAttachment(db);
    //Reset the contact history notes to the previous organization.
    OrganizationHistoryList historyList = new OrganizationHistoryList();
    historyList.setContactId(this.getId());
    historyList.setNotes(true);
    historyList.buildList(db);
    historyList.moveNotesToAccountHistory(db, this.getOrgId());
    //Move the contact
    Contact.move(db, this.getId(), newOrgId, organization.getName(), this.getModifiedBy());
    return true;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean canWorkAsContact() {
    boolean result = true;
    if ((this.getCompany() == null || "".equals(this.getCompany().trim())) &&
        (this.getNameLast() == null || "".equals(this.getNameLast().trim()))) {
      result = false;
    }
    return result;
  }

  public void select(Connection db) throws SQLException {
    int contactId = -1;

    if (username != null) {
      PreparedStatement pst = db.prepareStatement(
        "SELECT c.contact_id " +
        "FROM contact c " +
        "LEFT JOIN access a ON (c.user_id = a.user_id) " +
        "WHERE a.username = ? ");
      pst.setString(1, this.username);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        contactId = rs.getInt("contact_id");
      }
      rs.close();
      pst.close();

      if (contactId > -1) {
        queryRecord(db, contactId);
      }
    }
  }
}

