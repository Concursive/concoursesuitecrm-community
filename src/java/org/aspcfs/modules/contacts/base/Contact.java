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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Represents a Contact
 *
 * @author mrajkowski
 * @version $Id: Contact.java,v 1.106.4.3.2.3 2005/01/10 17:58:55 kbhoopal
 *          Exp $
 * @created August 29, 2001
 */
public class Contact extends GenericBean {

  /**
   * Type 1 in the database refers to an Employee
   */
  public final static int EMPLOYEE_TYPE = 1;
  public final static int LEAD_TYPE = 2;
  public final static int LEAD_UNREAD = 0;
  public final static int LEAD_UNPROCESSED = 1;
  public final static int LEAD_ASSIGNED = 2;
  public final static int LEAD_TRASHED = 3;

  private int id = -1;
  private int orgId = -1;
  private String orgName = "";
  private String company = "";
  private String title = "";
  private int department = -1;
  private String nameSalutation = "";
  private String nameFirst = "";
  private String nameMiddle = "";
  private String nameLast = "";
  private String nameSuffix = "";
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
  private int statusId = -1;
  private int importId = -1;
  private boolean isLead = false;
  private int leadStatus = -1;
  private int source = -1;
  private int rating = -1;
  private String comments = null;
  private java.sql.Timestamp conversionDate = null;

  private ContactEmailAddressList emailAddressList = new ContactEmailAddressList();
  private ContactPhoneNumberList phoneNumberList = new ContactPhoneNumberList();
  private ContactAddressList addressList = new ContactAddressList();
  private ContactTextMessageAddressList textMessageAddressList = new ContactTextMessageAddressList();
  private ContactInstantMessageAddressList instantMessageAddressList = new ContactInstantMessageAddressList();
  private String departmentName = "";
  private String ownerName = "";
  private String enteredByName = "";
  private String modifiedByName = "";

  private boolean orgEnabled = true;
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
   * @throws SQLException Description of Exception
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
   * @throws SQLException Description of Exception
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
        "SELECT c.*, d.description as departmentname " +
        "FROM contact c " +
        "LEFT JOIN lookup_department d ON (c.department = d.code) " +
        "WHERE c.contact_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, contactId);
    ResultSet rs = pst.executeQuery();
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
    this.birthDate = DatabaseUtils.parseTimestamp(tmp);
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
   * Sets the employee attribute of the Contact object
   *
   * @param employee The new employee value
   */
  public void setEmployee(boolean employee) {
    this.employee = employee;
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
        "FROM access " +
        "WHERE user_id = ? AND enabled = ? ");
    pst.setInt(1, this.getOwner());
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
   * Sets the Company attribute of the Contact object
   *
   * @param tmp The new Company value
   * @since 1.34
   */
  public void setCompany(String tmp) {
    this.company = tmp;
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
   * Sets the orgEnabled attribute of the Contact object
   *
   * @param orgEnabled The new orgEnabled value
   */
  public void setOrgEnabled(boolean orgEnabled) {
    this.orgEnabled = orgEnabled;
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
    return (statusId == Import.PROCESSED_UNAPPROVED ? false : true);
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
   * ??????V$ Gets the Notes attribute of the Contact object
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
      return "Trashed";
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
          "(user_id, namefirst, namelast, owner, primary_contact, org_name, ");
      sql.append(
          "access_type, source, rating, comments, conversion_date, trashed_date, ");
      if (id > -1) {
        sql.append("contact_id, ");
      }
      sql.append("additional_names, nickname, birthdate, \"role\", ");

      if (this.getIsLead()) {
        sql.append("lead, lead_status,");
      }
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      if (employee || orgId == 0) {
        sql.append("employee, ");
      }
      if (statusId > -1) {
        sql.append("status_id, ");
      }
      if (importId > -1) {
        sql.append("import_id, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (id > -1) {
        sql.append("?, ");
      }
      if (this.getIsLead()) {
        sql.append("?, ?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
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
      pst.setInt(++i, accessType);
      DatabaseUtils.setInt(pst, ++i, this.getSource());
      DatabaseUtils.setInt(pst, ++i, this.getRating());
      pst.setString(++i, this.getComments());
      DatabaseUtils.setTimestamp(pst, ++i, this.getConversionDate());
      DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setString(++i, this.getAdditionalNames());
      pst.setString(++i, this.getNickname());
      pst.setTimestamp(++i, this.getBirthDate());
      pst.setString(++i, this.getRole());
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
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      resultCount = this.update(db, false);
      if (this.getPrimaryContact()) {
        Organization thisOrg = new Organization(db, this.getOrgId());
        if (!(thisOrg.getNameFirst().equals(this.getNameFirst()) &&
            thisOrg.getNameLast().equals(this.getNameLast()) &&
            thisOrg.getNameMiddle().equals(this.getNameMiddle()))) {
          thisOrg.setNameFirst(this.getNameFirst());
          thisOrg.setNameLast(this.getNameLast());
          thisOrg.setNameMiddle(this.getNameMiddle());
          thisOrg.setName(thisOrg.getNameLastFirstMiddle());
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
    PreparedStatement pst = null;
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
   * @param db Description of the Parameter
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
    ResultSet rs = pst.executeQuery();
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
        "set employee = ? " +
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
        "(contact_id, type_id, \"level\") " +
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
        "FROM access " +
        "WHERE contact_id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
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
        "ORDER BY ctl.\"level\" ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, id);
    rs = pst.executeQuery();
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
        "FROM access " +
        "WHERE contact_id = ? " +
        "AND enabled = ? ");
    pst.setInt(1, this.getId());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
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
    ResultSet rs = pst.executeQuery();
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
        "source = ?, rating = ?, comments = ?, conversion_date = ?, lead = ?, lead_status = ?, ");
    sql.append(
        "trashed_date = ?, additional_names = ?, nickname = ?, birthdate = ?, \"role\" = ?, ");
    sql.append("employee_id = ?, ");
    if (locale > -1) {
      sql.append("locale = ?, ");
    }
    if (employmentType > -1) {
      sql.append("employmentType = ?, ");
    }
    sql.append("startofday = ?, endofday = ?, ");

    if (!override) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("modifiedby = ? WHERE contact_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
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
    DatabaseUtils.setTimestamp(pst, ++i, this.getConversionDate());
    pst.setBoolean(++i, this.getIsLead());
    DatabaseUtils.setInt(pst, ++i, this.getLeadStatus());
    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
    pst.setString(++i, this.getAdditionalNames());
    pst.setString(++i, this.getNickname());
    pst.setTimestamp(++i, this.getBirthDate());
    pst.setString(++i, this.getRole());

    if (employeeId != null) {
      pst.setString(++i, this.getEmployeeId());
    } else {
      pst.setString(++i, null);
    }
    if (locale > -1) {
      pst.setInt(++i, this.getLocale());
    }
    if (employmentType > -1) {
      pst.setInt(++i, this.getEmploymentType());
    }
    pst.setString(++i, this.getStartOfDay());
    pst.setString(++i, this.getEndOfDay());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());
    if (!override) {
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
        "SET " +
        "information_update_date = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE contact_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getId());
    pst.executeUpdate();
    pst.close();
    //Process the phone numbers if there are any
    processPhoneNumbers(db);
    //Insert the addresses if there are any
    processAddress(db);
    //Insert the email addresses if there are any
    processEmailAddress(db);
    //Insert the text message addresses if there are any
    processTextMessageAddress(db);
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
    department = DatabaseUtils.getInt(rs, "department", 0);
    nameSalutation = rs.getString("namesalutation");
    nameLast = rs.getString("namelast");
    nameFirst = rs.getString("namefirst");
    nameMiddle = rs.getString("namemiddle");
    nameSuffix = rs.getString("namesuffix");
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

    //lookup_department table
    departmentName = rs.getString("departmentname");
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
    rs = pst.executeQuery();
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
    rs = pst.executeQuery();
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
    rs = pst.executeQuery();
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
    rs = pst.executeQuery();
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
      rs = pst.executeQuery();
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
      rs = pst.executeQuery();
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
          "WHERE contact_id = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
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
      rs = pst.executeQuery();
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
          "WHERE contact_id = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
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
          "WHERE contact_id = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
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
        "SET enabled = ? " +
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
          "SET status_id = ? " +
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
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param toTrash   Description of the Parameter
   * @param tmpUserId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db, ActionContext context, boolean toTrash, int tmpUserId) throws SQLException {
    int count = 0;
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
      count = pst.executeUpdate();
      pst.close();
      
      //Delete from the search criteria list
      if (toTrash) {
        int fieldId = -1;
        pst = db.prepareStatement(
            "SELECT id " +
            "FROM search_fields " +
            "WHERE description = ? ");
        pst.setString(1, "Contact ID");
        ResultSet rs = pst.executeQuery();
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
        "SET enabled = ? " +
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
    // delete all inbox message links associated with this contact
    PreparedStatement pst = null;
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
    ResultSet rs = pst.executeQuery();
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
    while (thisList.hasNext()) {
      Dependency thisDependency = (Dependency) thisList.next();
      if (!thisDependency.getCanDelete() && thisDependency.getCount() > 0) {
        if (thisDependency.getName().equals("opportunities") || thisDependency.getName().equals(
            "activities") || thisDependency.getName().equals("folders")) {
          canMove = false;
        }
      }
    }
    return canMove;
  }
}

