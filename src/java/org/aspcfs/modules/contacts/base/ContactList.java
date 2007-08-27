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

import com.darkhorseventures.framework.actions.ActionContext;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.base.UserCentric;
import org.aspcfs.modules.communications.base.SearchCriteriaElement;
import org.aspcfs.modules.communications.base.SearchCriteriaGroup;
import org.aspcfs.modules.communications.base.SearchCriteriaList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.*;
import java.text.DateFormat;
import java.util.*;

/**
 * Contains a list of contacts... currently used to build the list from the
 * database with any of the parameters to limit the results.
 *
 * @author mrajkowski
 * @version $Id$
 * @created August 29, 2001
 */
public class ContactList extends Vector implements UserCentric, SyncableList {

  private static final long serialVersionUID = -8573897895619490285L;

  public final static String tableName = "contact";
  public final static String uniqueField = "contact_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;

  //EXCLUDE_PERSONAL excludes all personal contacts, IGNORE_PERSONAL ignores personal contacts. By default the
  // list excludes personal contacts
  public final static int EXCLUDE_PERSONAL = -1;
  public final static int IGNORE_PERSONAL = -2;
  private int includeEnabled = Constants.TRUE;

  private boolean includeEnabledUsersOnly = false;
  private boolean includeNonUsersOnly = false;
  private boolean includeUsersOnly = false;
  private int userRoleType = -1;
  private int defaultContactId = -1;

  private PagedListInfo pagedListInfo = null;
  private int contactUserId = -1;
  private int orgId = -1;
  private int typeId = -1;
  private int departmentId = -1;
  private int projectId = -1;
  private String firstName = null;
  private String middleName = null;
  private String lastName = null;
  private String title = null;
  private String company = null;
  private boolean emailNotNull = false;
  private Vector ignoreTypeIdList = new Vector();
  private boolean checkUserAccess = false;
  private boolean checkEnabledUserAccess = false;
  private int checkExcludedFromCampaign = -1;
  private boolean buildDetails = true;
  private boolean buildPhoneNumbers = false;
  private boolean buildTypes = true;
  private int owner = -1;
  private String ownerIdRange = null;
  private String accountOwnerIdRange = null;
  private boolean withAccountsOnly = false;
  private boolean withProjectsOnly = false;
  private int employeesOnly = Constants.UNDEFINED;
  private int leadsOnly = Constants.UNDEFINED;
  private int leadStatusExists = Constants.UNDEFINED;
  private boolean excludeAccountContacts = false;
  private int hierarchialUsers = -1;
  private int leadStatus = -1;
  private int source = -1;
  private int stage = -1;
  private int rating = -1;
  private int industry = -1;
  private String comments = null;
  private int readBy = -1;
  private Timestamp enteredStart = null;
  private Timestamp enteredEnd = null;
  private Timestamp conversionDateStart = null;
  private Timestamp conversionDateEnd = null;
  private String postalCode = null;
  private int hasConversionDate = Constants.UNDEFINED;
  private String emailAddress = null;
  private String country = null;
  private boolean ownerOrReader = false;
  private String city = null;
  private String state = null;
  private String state1 = null;
  // Combination filters
  private boolean allContacts = false;
  private boolean controlledHierarchyOnly = false;
  private String permission = null;

  // Html drop-down helper properties
  private String emptyHtmlSelectRecord = null;
  private String jsEvent = null;

  // Properties for combining multiple criteria into a single contact list
  private int sclOwnerId = -1;
  private String sclOwnerIdRange = null;
  private HashMap companyHash = null;
  private HashMap nameFirstHash = null;
  private HashMap nameLastHash = null;
  private HashMap dateHash = null;
  private HashMap zipHash = null;
  private HashMap areaCodeHash = null;
  private HashMap cityHash = null;
  private HashMap typeIdHash = null;
  private HashMap contactIdHash = null;
  private HashMap titleHash = null;
  private HashMap accountTypeIdHash = null;
  private HashMap siteIdHash = null;
  private HashMap importNameHash = null;
  boolean firstCriteria = true;
  private String contactIdRange = null;
  private SearchCriteriaList scl = null;
  private int userId = -1;
  // Global search property
  private String searchText = "";
  // access type filters
  private int ruleId = -1;
  private int personalId = EXCLUDE_PERSONAL;

  // objects for speed up
  AccessTypeList accessTypes = null;
  AccessTypeList generalContactAccessTypes = null;
  UserList users = new UserList();

  // import filters
  private int importId = -1;
  private int statusId = -1;
  private boolean excludeUnapprovedContacts = true;
  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;
  private boolean showTrashedAndNormal = false;

  // sorting filters
  private int oldestFirst = Constants.UNDEFINED;
  private boolean zipCodeAscPotentialDesc = false;

  // errors and warnings
  private HashMap errors = new HashMap();
  private HashMap warnings = new HashMap();

  // other variables
  private String nextValue = null;

  // search fields
  private String accountName = null;
  private String contactPhoneNumber = null;
  private String accountSegment = null;
  private String contactCity = null;
  private String contactState = null;
  private String accountPostalCode = null;
  private String accountNumber = null;
  private int accountTypeId = -1;
  private int accountOwnerId = -1;
  private int includeEnabledAccount = -1;
  private String assetSerialNumber = null;

  // site filters
  private int orgSiteId = -1;
  private int userSiteId = -1;
  private int importSiteId = -1;
  private boolean includeContactsFromImportsOfAllSites = false;
  private boolean includeContactsFromOrganizationsWithoutSite = false;
  private boolean includeUsersWithAccessToAllSites = false;

  private int siteId = -1;
  private boolean exclusiveToSite = false;
  private boolean includeAllSites = false;
  private int portalUsersOnly = Constants.UNDEFINED;

  private int id = -1;

  // Logger
  private static Logger logger = Logger.getLogger(org.aspcfs.modules.contacts.base.ContactList.class);

  /**
   * Constructor for the ContactList object
   *
   * @since 1.1
   */
  public ContactList() {
  }

  public static Contact getObject(ResultSet rs) throws SQLException {
    Contact contact = new Contact(rs);
    return contact;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getTableName()
   */
  public String getTableName() {
    return tableName;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getUniqueField()
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.sql.Timestamp)
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.lang.String)
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = java.sql.Timestamp.valueOf(lastAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.sql.Timestamp)
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.lang.String)
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = java.sql.Timestamp.valueOf(nextAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(int)
   */
  public void setSyncType(int syncType) {
    this.syncType = syncType;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }
  
  /**
   * Gets the contactUserId attribute of the ContactList object
   *
   * @return The contactUserId value
   */
  public int getContactUserId() {
    return contactUserId;
  }


  /**
   * Sets the contactUserId attribute of the ContactList object
   *
   * @param tmp The new contactUserId value
   */
  public void setContactUserId(int tmp) {
    this.contactUserId = tmp;
  }


  /**
   * Sets the contactUserId attribute of the ContactList object
   *
   * @param tmp The new contactUserId value
   */
  public void setContactUserId(String tmp) {
    this.contactUserId = Integer.parseInt(tmp);
  }


  /**
   * Sets the includeUsersOnly attribute of the ContactList object
   *
   * @param includeUsersOnly The new includeUsersOnly value
   */
  public void setIncludeUsersOnly(boolean includeUsersOnly) {
    this.includeUsersOnly = includeUsersOnly;
  }


  /**
   * Sets the includeUsersOnly attribute of the ContactList object
   *
   * @param includeUsersOnly The new includeUsersOnly value
   */
  public void setIncludeUsersOnly(String includeUsersOnly) {
    this.includeUsersOnly = DatabaseUtils.parseBoolean(includeUsersOnly);
  }

  /**
   * Gets the userRoleType attribute of the ContactList object
   *
   * @return The userRoleType value
   */
  public int getUserRoleType() {
    return userRoleType;
  }

  /**
   * Sets the userRoleType attribute of the ContactList object
   *
   * @param tmp The new userRoleType value
   */
  public void setUserRoleType(int tmp) {
    this.userRoleType = tmp;
  }

  /**
   * Sets the userRoleType attribute of the ContactList object
   *
   * @param tmp The new userRoleType value
   */
  public void setUserRoleType(String tmp) {
    this.userRoleType = Integer.parseInt(tmp);
  }

  /**
   * Gets the industry attribute of the ContactList object
   *
   * @return The industry value
   */
  public int getIndustry() {
    return industry;
  }

  /**
   * Sets the industry attribute of the ContactList object
   *
   * @param tmp The new industry value
   */
  public void setIndustry(int tmp) {
    this.industry = tmp;
  }

  /**
   * Sets the industry attribute of the ContactList object
   *
   * @param tmp The new industry value
   */
  public void setIndustry(String tmp) {
    this.industry = Integer.parseInt(tmp);
  }

  /**
   * Sets the checkExcludedFromCampaign attribute of the ContactList object
   *
   * @param checkExcludedFromCampaign The new checkExcludedFromCampaign value
   */
  public void setCheckExcludedFromCampaign(int checkExcludedFromCampaign) {
    this.checkExcludedFromCampaign = checkExcludedFromCampaign;
  }

  /**
   * Sets the contactIdRange attribute of the ContactList object
   *
   * @param contactIdRange The new contactIdRange value
   */
  public void setContactIdRange(String contactIdRange) {
    this.contactIdRange = contactIdRange;
  }

  /**
   * Sets the excludeAccountContacts attribute of the ContactList object
   *
   * @param excludeAccountContacts The new excludeAccountContacts value
   */
  public void setExcludeAccountContacts(boolean excludeAccountContacts) {
    this.excludeAccountContacts = excludeAccountContacts;
  }

  /**
   * Gets the excludeAccountContacts attribute of the ContactList object
   *
   * @return The excludeAccountContacts value
   */
  public boolean getExcludeAccountContacts() {
    return excludeAccountContacts;
  }

  /**
   * Gets the employeesOnly attribute of the ContactList object
   *
   * @return The employeesOnly value
   */
  public int getEmployeesOnly() {
    return employeesOnly;
  }

  /**
   * Sets the employeesOnly attribute of the ContactList object
   *
   * @param tmp The new employeesOnly value
   */
  public void setEmployeesOnly(int tmp) {
    this.employeesOnly = tmp;
  }

  /**
   * Sets the employeesOnly attribute of the ContactList object
   *
   * @param tmp The new employeesOnly value
   */
  public void setEmployeesOnly(String tmp) {
    this.employeesOnly = Integer.parseInt(tmp);
  }

  /**
   * Gets the accountTypeIdHash attribute of the ContactList object
   *
   * @return The accountTypeIdHash value
   */
  public HashMap getAccountTypeIdHash() {
    return accountTypeIdHash;
  }

  /**
   * Sets the accountTypeIdHash attribute of the ContactList object
   *
   * @param accountTypeIdHash The new accountTypeIdHash value
   */
  public void setAccountTypeIdHash(HashMap accountTypeIdHash) {
    this.accountTypeIdHash = accountTypeIdHash;
  }

  /**
   * Sets the siteIdHash attribute of the ContactList object
   *
   * @param tmp The new siteIdHash value
   */
  public void setSiteIdHash(HashMap tmp) {
    this.siteIdHash = tmp;
  }


  /**
   * Gets the siteIdHash attribute of the ContactList object
   *
   * @return The siteIdHash value
   */
  public HashMap getSiteIdHash() {
    return siteIdHash;
  }

  /**
   * Sets the importNameHash attribute of the ContactList object
   *
   * @param tmp The new mportNameHash value
   */
  public void setImportNameHash(HashMap tmp) {
    this.importNameHash = tmp;
  }
  /**
   * Gets the importNameHash attribute of the ContactList object
   *
   * @return The importNameHash value
   */
  public HashMap getImportNameHash() {
    return importNameHash;
  }

  /**
   * Sets the includeEnabledUsersOnly attribute of the ContactList object
   *
   * @param includeEnabledUsersOnly The new includeEnabledUsersOnly value
   */
  public void setIncludeEnabledUsersOnly(boolean includeEnabledUsersOnly) {
    this.includeEnabledUsersOnly = includeEnabledUsersOnly;
  }

  /**
   * Sets the includeEnabledUsersOnly attribute of the ContactList object
   *
   * @param tmp The new includeEnabledUsersOnly value
   */
  public void setIncludeEnabledUsersOnly(String tmp) {
    this.includeEnabledUsersOnly = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the accessTypes attribute of the ContactList object
   *
   * @param accessTypes The new accessTypes value
   */
  public void setAccessTypes(AccessTypeList accessTypes) {
    this.accessTypes = accessTypes;
  }

  /**
   * Gets the accessTypes attribute of the ContactList object
   *
   * @return The accessTypes value
   */
  public AccessTypeList getAccessTypes() {
    return accessTypes;
  }

  /**
   * Gets the generalContactAccessTypes attribute of the ContactList object
   *
   * @return The generalContactAccessTypes value
   */
  public AccessTypeList getGeneralContactAccessTypes() {
    return generalContactAccessTypes;
  }

  /**
   * Sets the generalContactAccessTypes attribute of the ContactList object
   *
   * @param tmp The new generalContactAccessTypes value
   */
  public void setGeneralContactAccessTypes(AccessTypeList tmp) {
    this.generalContactAccessTypes = tmp;
  }

  /**
   * Gets the includeEnabledUsersOnly attribute of the ContactList object
   *
   * @return The includeEnabledUsersOnly value
   */
  public boolean getIncludeEnabledUsersOnly() {
    return includeEnabledUsersOnly;
  }

  /**
   * Sets the FirstName attribute of the ContactList object
   *
   * @param firstName The new FirstName value
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Filters personal contacts based on the argument specified
   * <p/>
   * <p/> Usage: EXCLUDE_PERSONAL to exclude personal contacts IGNORE_PERSONAL
   * to ignore personal contacts Set UserId to include personal contacts
   * <p/>
   * <p/> Note: If owner is set personal contacts are included by default so
   * personalId can be set to IGNORE_PERSONAL<br>
   * Also set the AccessTypeList for speed up of the query
   *
   * @param personalId The new personalId value
   */
  public void setPersonalId(int personalId) {
    this.personalId = personalId;
  }

  /**
   * Filters personal contacts based on the argument specified
   * <p/>
   * <p/> Usage: EXCLUDE_PERSONAL to exclude personal contacts IGNORE_PERSONAL
   * to ignore personal contacts Set UserId to include personal contacts
   * <p/>
   * <p/> Note: If owner is set personal contacts are included by default so
   * personalId can be set to IGNORE_PERSONAL<br>
   * For external applications using ContactList it works without accessTypes
   * too
   *
   * @param personalId  The new personalId value
   * @param accessTypes The new personalId value
   */
  public void setPersonalId(int personalId, AccessTypeList accessTypes) {
    this.personalId = personalId;
    this.accessTypes = accessTypes;
  }

  /**
   * Gets the personalId attribute of the ContactList object
   *
   * @return The personalId value
   */
  public int getPersonalId() {
    return personalId;
  }

  /**
   * Gets the nameFirstHash attribute of the ContactList object
   *
   * @return The nameFirstHash value
   */
  public HashMap getNameFirstHash() {
    return nameFirstHash;
  }

  /**
   * Sets the nameFirstHash attribute of the ContactList object
   *
   * @param nameFirstHash The new nameFirstHash value
   */
  public void setNameFirstHash(HashMap nameFirstHash) {
    this.nameFirstHash = nameFirstHash;
  }

  /**
   * Sets the allContacts attribute of the ContactList object
   *
   * @param allContacts The new allContacts value
   */
  public void setAllContacts(boolean allContacts) {
    this.allContacts = allContacts;
  }

  /**
   * Method to get all contacts including personal Optionally the three
   * arguments can be set seperately but it is highly recommended to use this
   * method Note: AccessTypeList has to be set for the personalId to work
   *
   * @param allContacts  The new allContacts value
   * @param ownerIdRange The new allContacts value
   * @param owner        The new allContacts value
   */
  public void setAllContacts(boolean allContacts, int owner,
                             String ownerIdRange) {
    this.ownerIdRange = ownerIdRange;
    this.allContacts = allContacts;
    this.personalId = owner;
  }

  /**
   * Sets the accountName attribute of the ContactList object
   *
   * @param accountName The new accountName value
   */
  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  /**
   * Sets the contactPhoneNumber attribute of the ContactList object
   *
   * @param tmp The new contactPhoneNumber value
   */
  public void setContactPhoneNumber(String tmp) {
    this.contactPhoneNumber = tmp;
  }

  /**
   * Sets the accountSegment attribute of the ContactList object
   *
   * @param accountSegment The new accountSegment value
   */
  public void setAccountSegment(String accountSegment) {
    this.accountSegment = accountSegment;
  }

  /**
   * Sets the contactCity attribute of the ContactList object
   *
   * @param tmp The new contactCity value
   */
  public void setContactCity(String tmp) {
    this.contactCity = tmp;
  }

  /**
   * Sets the contactState attribute of the ContactList object
   *
   * @param tmp The new contactState value
   */
  public void setContactState(String tmp) {
    this.contactState = tmp;
  }

  /**
   * Sets the contactCountry attribute of the ContactList object
   *
   * @param tmp The new contactCountry value
   */
  public void setContactCountry(String tmp) {
    this.country = tmp;
  }

  /**
   * Sets the accountPostalCode attribute of the ContactList object
   *
   * @param tmp The new accountPostalCode value
   */
  public void setAccountPostalCode(String tmp) {
    this.accountPostalCode = tmp;
  }

  /**
   * Sets the orgSiteId attribute of the ContactList object
   *
   * @param orgSiteId The new orgSiteId value
   */
  public void setOrgSiteId(int orgSiteId) {
    this.orgSiteId = orgSiteId;
  }

  /**
   * Sets the orgSiteId attribute of the ContactList object
   *
   * @param orgSiteId The new orgSiteId value
   */
  public void setOrgSiteId(String orgSiteId) {
    this.orgSiteId = Integer.parseInt(orgSiteId);
  }

  /**
   * Gets the orgSiteId attribute of the ContactList object
   *
   * @return The orgSiteId value
   */
  public int getOrgSiteId() {
    return orgSiteId;
  }

  /**
   * Gets the orgSiteId attribute of the ContactList object
   *
   * @param tmp The new userSiteId value
   */
  public void setUserSiteId(int tmp) {
    this.userSiteId = tmp;
  }

  /**
   * Gets the userSiteId attribute of the ContactList object
   *
   * @param tmp The new userSiteId value
   */
  public void setUserSiteId(String tmp) {
    this.userSiteId = Integer.parseInt(tmp);
  }

  /**
   * Gets the userSiteId attribute of the ContactList object
   *
   * @return The userSiteId value
   */
  public int getUserSiteId() {
    return userSiteId;
  }

  /**
   * Gets the includeContactsFromOrganizationsWithoutSite attribute of the
   * ContactList object
   *
   * @param tmp The new includeContactsFromOrganizationsWithoutSite value
   */
  public void setIncludeContactsFromOrganizationsWithoutSite(boolean tmp) {
    this.includeContactsFromOrganizationsWithoutSite = tmp;
  }

  /**
   * Gets the includeContactsFromOrganizationsWithoutSite attribute of the
   * ContactList object
   *
   * @param tmp The new includeContactsFromOrganizationsWithoutSite value
   */
  public void setIncludeContactsFromOrganizationsWithoutSite(String tmp) {
    this.includeContactsFromOrganizationsWithoutSite = DatabaseUtils
        .parseBoolean(tmp);
  }

  /**
   * Gets the includeUsersWithAccessToAllSites attribute of the ContactList
   * object
   *
   * @param tmp The new includeUsersWithAccessToAllSites value
   */
  public void setIncludeUsersWithAccessToAllSites(boolean tmp) {
    this.includeUsersWithAccessToAllSites = tmp;
  }

  /**
   * Gets the includeUsersWithAccessToAllSites attribute of the ContactList
   * object
   *
   * @param tmp The new includeUsersWithAccessToAllSites value
   */
  public void setIncludeUsersWithAccessToAllSites(String tmp) {
    this.includeUsersWithAccessToAllSites = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the includeContactsFromOrganizationsWithoutSite attribute of the
   * ContactList object
   *
   * @return The includeContactsFromOrganizationsWithoutSite value
   */
  public boolean getIncludeContactsFromOrganizationsWithoutSite() {
    return includeContactsFromOrganizationsWithoutSite;
  }

  /**
   * Gets the includeUsersWithAccessToAllSites attribute of the ContactList
   * object
   *
   * @return The includeUsersWithAccessToAllSites value
   */
  public boolean getIncludeUsersWithAccessToAllSites() {
    return includeUsersWithAccessToAllSites;
  }

  /**
   * Sets the importSiteId attribute of the ContactList object
   *
   * @param tmp The new importSiteId value
   */
  public void setImportSiteId(int tmp) {
    this.importSiteId = tmp;
  }

  /**
   * Sets the importSiteId attribute of the ContactList object
   *
   * @param tmp The new importSiteId value
   */
  public void setImportSiteId(String tmp) {
    this.importSiteId = Integer.parseInt(tmp);
  }

  /**
   * Sets the includeContactsFromImportsOfAllSites attribute of the
   * ContactList object
   *
   * @param tmp The new includeContactsFromImportsOfAllSites value
   */
  public void setIncludeContactsFromImportsOfAllSites(boolean tmp) {
    this.includeContactsFromImportsOfAllSites = tmp;
  }

  /**
   * Sets the includeContactsFromImportsOfAllSites attribute of the
   * ContactList object
   *
   * @param tmp The new includeContactsFromImportsOfAllSites value
   */
  public void setIncludeContactsFromImportsOfAllSites(String tmp) {
    this.includeContactsFromImportsOfAllSites = DatabaseUtils
        .parseBoolean(tmp);
  }

  /**
   * Gets the importSiteId attribute of the ContactList object
   *
   * @return The importSiteId value
   */
  public int getImportSiteId() {
    return importSiteId;
  }

  /**
   * Gets the includeContactsFromImportsOfAllSites attribute of the
   * ContactList object
   *
   * @return The includeContactsFromImportsOfAllSites value
   */
  public boolean getIncludeContactsFromImportsOfAllSites() {
    return includeContactsFromImportsOfAllSites;
  }

  /**
   * Sets the siteId attribute of the ContactList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }

  /**
   * Sets the siteId attribute of the ContactList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }

  /**
   * Gets the siteId attribute of the ContactList object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }

  /**
   * Sets the accountNumber attribute of the ContactList object
   *
   * @param tmp The new accountNumber value
   */
  public void setAccountNumber(String tmp) {
    this.accountNumber = tmp;
  }

  /**
   * Gets the accountNumber attribute of the ContactList object
   *
   * @return The accountNumber value
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * Sets the accountTypeId attribute of the ContactList object
   *
   * @param tmp The new accountTypeId value
   */
  public void setAccountTypeId(int tmp) {
    this.accountTypeId = tmp;
  }

  /**
   * Sets the accountTypeId attribute of the ContactList object
   *
   * @param tmp The new accountTypeId value
   */
  public void setAccountTypeId(String tmp) {
    this.accountTypeId = Integer.parseInt(tmp);
  }

  /**
   * Gets the accountTypeId attribute of the ContactList object
   *
   * @return The accountTypeId value
   */
  public int getAccountTypeId() {
    return accountTypeId;
  }

  /**
   * Sets the accountOwnerId attribute of the ContactList object
   *
   * @param tmp The new accountOwnerId value
   */
  public void setAccountOwnerId(int tmp) {
    this.accountOwnerId = tmp;
  }

  /**
   * Sets the accountOwnerId attribute of the ContactList object
   *
   * @param tmp The new accountOwnerId value
   */
  public void setAccountOwnerId(String tmp) {
    this.accountOwnerId = Integer.parseInt(tmp);
  }

  /**
   * Gets the accountOwnerId attribute of the ContactList object
   *
   * @return The accountOwnerId value
   */
  public int getAccountOwnerId() {
    return accountOwnerId;
  }

  /**
   * Sets the includeEnabledAccount attribute of the ContactList object
   *
   * @param tmp The new includeEnabledAccount value
   */
  public void setIncludeEnabledAccount(int tmp) {
    this.includeEnabledAccount = tmp;
  }

  /**
   * Sets the includeEnabledAccount attribute of the ContactList object
   *
   * @param tmp The new includeEnabledAccount value
   */
  public void setIncludeEnabledAccount(String tmp) {
    this.includeEnabledAccount = Integer.parseInt(tmp);
  }

  /**
   * Gets the includeEnabledAccount attribute of the ContactList object
   *
   * @return The includeEnabledAccount value
   */
  public int getIncludeEnabledAccount() {
    return includeEnabledAccount;
  }

  /**
   * Sets the assetSerialNumber attribute of the ContactList object
   *
   * @param tmp The new assetSerialNumber value
   */
  public void setAssetSerialNumber(String tmp) {
    this.assetSerialNumber = tmp;
  }

  /**
   * Gets the assetSerialNumber attribute of the ContactList object
   *
   * @return The assetSerialNumber value
   */
  public String getAssetSerialNumber() {
    return assetSerialNumber;
  }

  /**
   * Gets the accountName attribute of the ContactList object
   *
   * @return The accountName value
   */
  public String getAccountName() {
    return accountName;
  }

  /**
   * Gets the contactPhoneNumber attribute of the ContactList object
   *
   * @return The contactPhoneNumber value
   */
  public String getContactPhoneNumber() {
    return contactPhoneNumber;
  }

  /**
   * Gets the accountSegment attribute of the ContactList object
   *
   * @return The accountSegment value
   */
  public String getAccountSegment() {
    return accountSegment;
  }

  /**
   * Gets the contactCity attribute of the ContactList object
   *
   * @return The contactCity value
   */
  public String getContactCity() {
    return contactCity;
  }

  /**
   * Gets the contactState attribute of the ContactList object
   *
   * @return The contactState value
   */
  public String getContactState() {
    return contactState;
  }

  /**
   * Sets the accountPostalCode attribute of the ContactList object
   *
   * @return The accountPostalCode value
   */
  public String getAccountPostalCode() {
    return accountPostalCode;
  }

  /**
   * Gets the allContacts attribute of the ContactList object
   *
   * @return The allContacts value
   */
  public boolean getAllContacts() {
    return allContacts;
  }

  /**
   * Gets the dateHash attribute of the ContactList object
   *
   * @return The dateHash value
   */
  public HashMap getDateHash() {
    return dateHash;
  }

  /**
   * Sets the dateHash attribute of the ContactList object
   *
   * @param dateHash The new dateHash value
   */
  public void setDateHash(HashMap dateHash) {
    this.dateHash = dateHash;
  }

  /**
   * Gets the zipHash attribute of the ContactList object
   *
   * @return The zipHash value
   */
  public HashMap getZipHash() {
    return zipHash;
  }

  /**
   * Sets the ruleId attribute of the ContactList object
   *
   * @param ruleId The new ruleId value
   */
  public void setRuleId(int ruleId) {
    this.ruleId = ruleId;
  }

  /**
   * Set the rule Id to get only contacts which follow a certain rule e.g
   * Personal
   *
   * @return The ruleId value
   */
  public int getRuleId() {
    return ruleId;
  }

  /**
   * Sets the zipHash attribute of the ContactList object
   *
   * @param zipHash The new zipHash value
   */
  public void setZipHash(HashMap zipHash) {
    this.zipHash = zipHash;
  }

  /**
   * Sets the contactIdHash attribute of the ContactList object
   *
   * @param contactIdHash The new contactIdHash value
   */
  public void setContactIdHash(HashMap contactIdHash) {
    this.contactIdHash = contactIdHash;
  }

  /**
   * Gets the contactIdHash attribute of the ContactList object
   *
   * @return The contactIdHash value
   */
  public HashMap getContactIdHash() {
    return contactIdHash;
  }

  /**
   * Sets the SearchText attribute of the ContactList object
   *
   * @param searchText The new SearchText value
   */
  public void setSearchText(String searchText) {
    this.searchText = searchText;
  }

  /**
   * Sets the controlledHierarchyOnly attribute of the ContactList object
   *
   * @param controlledHierarchyOnly The new controlledHierarchyOnly value
   */
  public void setControlledHierarchyOnly(boolean controlledHierarchyOnly) {
    this.controlledHierarchyOnly = controlledHierarchyOnly;
  }

  /**
   * Gets all hierarchy contacts<br>
   * Optionally could set the two arguments seperately but using this method
   * is highly recommended for clarity purposes Note: Also set the
   * AccessTypeList for speed up of the query
   *
   * @param controlledHierarchyOnly The new controlledHierarchyOnly value
   * @param ownerIdRange            The new controlledHierarchyOnly value
   */
  public void setControlledHierarchyOnly(boolean controlledHierarchyOnly,
                                         String ownerIdRange) {
    this.controlledHierarchyOnly = controlledHierarchyOnly;
    this.ownerIdRange = ownerIdRange;
  }

  /**
   * Gets the controlledHierarchyOnly attribute of the ContactList object
   *
   * @return The controlledHierarchyOnly value
   */
  public boolean getControlledHierarchyOnly() {
    return controlledHierarchyOnly;
  }

  /**
   * Gets the firstCriteria attribute of the ContactList object
   *
   * @return The firstCriteria value
   */
  public boolean getFirstCriteria() {
    return firstCriteria;
  }

  /**
   * Sets the firstCriteria attribute of the ContactList object
   *
   * @param firstCriteria The new firstCriteria value
   */
  public void setFirstCriteria(boolean firstCriteria) {
    this.firstCriteria = firstCriteria;
  }

  /**
   * Sets the Company attribute of the ContactList object
   *
   * @param company The new Company value
   */
  public void setCompany(String company) {
    this.company = company;
  }

  /**
   * Gets the cityHash attribute of the ContactList object
   *
   * @return The cityHash value
   */
  public HashMap getCityHash() {
    return cityHash;
  }

  /**
   * Sets the cityHash attribute of the ContactList object
   *
   * @param cityHash The new cityHash value
   */
  public void setCityHash(HashMap cityHash) {
    this.cityHash = cityHash;
  }

  /**
   * Sets the OwnerIdRange attribute of the ContactList object
   *
   * @param ownerIdRange The new OwnerIdRange value
   */
  public void setOwnerIdRange(String ownerIdRange) {
    this.ownerIdRange = ownerIdRange;
  }

  /**
   * Gets the emptyHtmlSelectRecord attribute of the ContactList object
   *
   * @return The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }

  /**
   * Sets the emptyHtmlSelectRecord attribute of the ContactList object
   *
   * @param emptyHtmlSelectRecord The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String emptyHtmlSelectRecord) {
    this.emptyHtmlSelectRecord = emptyHtmlSelectRecord;
  }

  /**
   * Sets the accountOwnerIdRange attribute of the ContactList object
   *
   * @param tmp The new accountOwnerIdRange value
   */
  public void setAccountOwnerIdRange(String tmp) {
    this.accountOwnerIdRange = tmp;
  }

  /**
   * Sets the withAccountsOnly attribute of the ContactList object
   *
   * @param tmp The new withAccountsOnly value
   */
  public void setWithAccountsOnly(boolean tmp) {
    this.withAccountsOnly = tmp;
  }

  /**
   * Sets the Owner attribute of the ContactList object
   *
   * @param owner The new Owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }

  /**
   * Sets the owner attribute of the ContactList object
   *
   * @param owner The new owner value
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }

  /**
   * Gets the nameLastHash attribute of the ContactList object
   *
   * @return The nameLastHash value
   */
  public HashMap getNameLastHash() {
    return nameLastHash;
  }

  /**
   * Sets the nameLastHash attribute of the ContactList object
   *
   * @param nameLastHash The new nameLastHash value
   */
  public void setNameLastHash(HashMap nameLastHash) {
    this.nameLastHash = nameLastHash;
  }

  /**
   * Sets the Scl attribute of the ContactList object
   *
   * @param scl           The new Scl value
   * @param thisOwnerId   The new scl value
   * @param thisUserRange The new scl value
   */
  public void setScl(SearchCriteriaList scl, int thisOwnerId,
                     String thisUserRange) {
    this.scl = scl;
    this.sclOwnerId = thisOwnerId;
    this.sclOwnerIdRange = thisUserRange;
    buildQuery(thisOwnerId, thisUserRange);
  }

  /**
   * Gets the jsEvent attribute of the ContactList object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }

  /**
   * Sets the jsEvent attribute of the ContactList object
   *
   * @param jsEvent The new jsEvent value
   */
  public void setJsEvent(String jsEvent) {
    this.jsEvent = jsEvent;
  }

  /**
   * Gets the checkEnabledUserAccess attribute of the ContactList object
   *
   * @return The checkEnabledUserAccess value
   */
  public boolean getCheckEnabledUserAccess() {
    return checkEnabledUserAccess;
  }

  /**
   * Sets the checkEnabledUserAccess attribute of the ContactList object
   *
   * @param checkEnabledUserAccess The new checkEnabledUserAccess value
   */
  public void setCheckEnabledUserAccess(boolean checkEnabledUserAccess) {
    this.checkEnabledUserAccess = checkEnabledUserAccess;
  }

  /**
   * Sets the MiddleName attribute of the ContactList object
   *
   * @param tmp The new MiddleName value
   */
  public void setMiddleName(String tmp) {
    this.middleName = tmp;
  }

  /**
   * Sets the LastName attribute of the ContactList object
   *
   * @param tmp The new LastName value
   */
  public void setLastName(String tmp) {
    this.lastName = tmp;
  }

  /**
   * Gets the companyHash attribute of the ContactList object
   *
   * @return The companyHash value
   */
  public HashMap getCompanyHash() {
    return companyHash;
  }

  /**
   * Sets the companyHash attribute of the ContactList object
   *
   * @param companyHash The new companyHash value
   */
  public void setCompanyHash(HashMap companyHash) {
    this.companyHash = companyHash;
  }

  /**
   * Gets the sclOwnerId attribute of the ContactList object
   *
   * @return The sclOwnerId value
   */
  public int getSclOwnerId() {
    return sclOwnerId;
  }

  /**
   * Gets the sclOwnerIdRange attribute of the ContactList object
   *
   * @return The sclOwnerIdRange value
   */
  public String getSclOwnerIdRange() {
    return sclOwnerIdRange;
  }

  /**
   * Sets the sclOwnerId attribute of the ContactList object
   *
   * @param tmp The new sclOwnerId value
   */
  public void setSclOwnerId(int tmp) {
    this.sclOwnerId = tmp;
  }

  /**
   * Sets the sclOwnerIdRange attribute of the ContactList object
   *
   * @param tmp The new sclOwnerIdRange value
   */
  public void setSclOwnerIdRange(String tmp) {
    this.sclOwnerIdRange = tmp;
  }

  /**
   * Sets the PagedListInfo attribute of the ContactList object
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Sets the Title attribute of the ContactList object
   *
   * @param title The new Title value
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the orgId attribute of the ContactList object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }

  /**
   * Sets the orgId attribute of the ContactList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }

  /**
   * Sets the orgId attribute of the ContactList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }

  /**
   * Sets the EmailNotNull attribute of the ContactList object
   *
   * @param emailNotNull The new EmailNotNull value
   */
  public void setEmailNotNull(boolean emailNotNull) {
    this.emailNotNull = emailNotNull;
  }

  /**
   * Sets the TypeId attribute of the ContactList object
   *
   * @param tmp The new TypeId value
   * @since 1.1
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }

  /**
   * Sets the CheckUserAccess attribute of the ContactList object
   *
   * @param tmp The new CheckUserAccess value
   * @since 1.8
   */
  public void setCheckUserAccess(boolean tmp) {
    this.checkUserAccess = tmp;
  }

  /**
   * Sets the BuildDetails attribute of the ContactList object
   *
   * @param tmp The new BuildDetails value
   */
  public void setBuildDetails(boolean tmp) {
    this.buildDetails = tmp;
  }

  /**
   * Sets the BuildPhoneNumbers attribute of the ContactList object
   *
   * @param tmp The new BuildPhoneNumbers value
   */
  public void setBuildPhoneNumbers(boolean tmp) {
    this.buildPhoneNumbers = tmp;
  }

  /**
   * Sets the BuildPhoneNumbers attribute of the ContactList object
   *
   * @param tmp The new BuildPhoneNumbers value
   */
  public void setBuildPhoneNumbers(String tmp) {
    this.buildPhoneNumbers = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the buildTypes attribute of the ContactList object
   *
   * @param tmp The new buildTypes value
   */
  public void setBuildTypes(boolean tmp) {
    this.buildTypes = tmp;
  }

  /**
   * Sets the SearchValues attribute of the ContactList object
   *
   * @param outerHash The new SearchValues value
   */
  public void setSearchValues(HashMap[] outerHash) {
    this.companyHash = outerHash[0];
    this.nameFirstHash = outerHash[1];
    this.nameLastHash = outerHash[2];
    this.dateHash = outerHash[3];
    this.zipHash = outerHash[4];
    this.areaCodeHash = outerHash[5];
    this.cityHash = outerHash[6];
    this.typeIdHash = outerHash[7];
    this.contactIdHash = outerHash[8];
    this.titleHash = outerHash[9];
    this.accountTypeIdHash = outerHash[10];
    this.siteIdHash = outerHash[11];
    this.importNameHash = outerHash[12];
  }

  /**
   * Sets the departmentId attribute of the ContactList object
   *
   * @param departmentId The new departmentId value
   */
  public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
  }

  /**
   * Sets the withProjectsOnly attribute of the ContactList object
   *
   * @param withProjectsOnly The new withProjectsOnly value
   */
  public void setWithProjectsOnly(boolean withProjectsOnly) {
    this.withProjectsOnly = withProjectsOnly;
  }

  /**
   * Sets the projectId attribute of the ContactList object
   *
   * @param projectId The new projectId value
   */
  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  /**
   * Gets the contactIdRange attribute of the ContactList object
   *
   * @return The contactIdRange value
   */
  public String getContactIdRange() {
    return contactIdRange;
  }

  /**
   * Gets the checkExcludedFromCampaign attribute of the ContactList object
   *
   * @return The checkExcludedFromCampaign value
   */
  public int getCheckExcludedFromCampaign() {
    return checkExcludedFromCampaign;
  }

  /**
   * Gets the pagedListInfo attribute of the ContactList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }

  /**
   * Gets the typeIdHash attribute of the ContactList object
   *
   * @return The typeIdHash value
   */
  public HashMap getTypeIdHash() {
    return typeIdHash;
  }

  /**
   * Sets the typeIdHash attribute of the ContactList object
   *
   * @param typeIdHash The new typeIdHash value
   */
  public void setTypeIdHash(HashMap typeIdHash) {
    this.typeIdHash = typeIdHash;
  }

  /**
   * Gets the includeEnabled attribute of the ContactList object
   *
   * @return The includeEnabled value
   */
  public int getIncludeEnabled() {
    return includeEnabled;
  }

  /**
   * Sets the includeEnabled attribute of the ContactList object
   *
   * @param includeEnabled The new includeEnabled value
   */
  public void setIncludeEnabled(int includeEnabled) {
    this.includeEnabled = includeEnabled;
  }

  /**
   * Gets the areaCodeHash attribute of the ContactList object
   *
   * @return The areaCodeHash value
   */
  public HashMap getAreaCodeHash() {
    return areaCodeHash;
  }

  /**
   * Sets the areaCodeHash attribute of the ContactList object
   *
   * @param areaCodeHash The new areaCodeHash value
   */
  public void setAreaCodeHash(HashMap areaCodeHash) {
    this.areaCodeHash = areaCodeHash;
  }

  /**
   * Gets the SearchText attribute of the ContactList object
   *
   * @return The SearchText value
   */
  public String getSearchText() {
    return searchText;
  }

  /**
   * Gets the OwnerIdRange attribute of the ContactList object
   *
   * @return The OwnerIdRange value
   */
  public String getOwnerIdRange() {
    return ownerIdRange;
  }

  /**
   * Gets the accountOwnerIdRange attribute of the ContactList object
   *
   * @return The accountOwnerIdRange value
   */
  public String getAccountOwnerIdRange() {
    return accountOwnerIdRange;
  }

  /**
   * Gets the withAccountsOnly attribute of the ContactList object
   *
   * @return The withAccountsOnly value
   */
  public boolean getWithAccountsOnly() {
    return withAccountsOnly;
  }

  /**
   * Gets the Scl attribute of the ContactList object
   *
   * @return The Scl value
   */
  public SearchCriteriaList getScl() {
    return scl;
  }

  /**
   * Sets the importId attribute of the ContactList object
   *
   * @param tmp The new importId value
   */
  public void setImportId(int tmp) {
    this.importId = tmp;
  }

  /**
   * Sets the excludeUnapprovedContacts attribute of the ContactList object
   *
   * @param tmp The new excludeUnapprovedContacts value
   */
  public void setExcludeUnapprovedContacts(boolean tmp) {
    this.excludeUnapprovedContacts = tmp;
  }

  /**
   * Sets the excludeUnapprovedContacts attribute of the ContactList object
   *
   * @param tmp The new excludeUnapprovedContacts value
   */
  public void setExcludeUnapprovedContacts(String tmp) {
    this.excludeUnapprovedContacts = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the excludeUnapprovedContacts attribute of the ContactList object
   *
   * @return The excludeUnapprovedContacts value
   */
  public boolean getExcludeUnapprovedContacts() {
    return excludeUnapprovedContacts;
  }

  /**
   * Sets the trashedDate attribute of the ContactList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }

  /**
   * Sets the trashedDate attribute of the ContactList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Sets the includeOnlyTrashed attribute of the ContactList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }

  /**
   * Sets the includeOnlyTrashed attribute of the ContactList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the trashedDate attribute of the ContactList object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }

  /**
   * Gets the includeOnlyTrashed attribute of the ContactList object
   *
   * @return The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }

  /**
   * Gets the showTrashedAndNormal attribute of the ContactList object
   *
   * @return The showTrashedAndNormal value
   */
  public boolean getShowTrashedAndNormal() {
    return showTrashedAndNormal;
  }

  /**
   * Sets the showTrashedAndNormal attribute of the ContactList object
   *
   * @param tmp The new showTrashedAndNormal value
   */
  public void setShowTrashedAndNormal(boolean tmp) {
    this.showTrashedAndNormal = tmp;
  }

  /**
   * Sets the showTrashedAndNormal attribute of the ContactList object
   *
   * @param tmp The new showTrashedAndNormal value
   */
  public void setShowTrashedAndNormal(String tmp) {
    this.showTrashedAndNormal = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the importId attribute of the ContactList object
   *
   * @param tmp The new importId value
   */
  public void setImportId(String tmp) {
    this.importId = Integer.parseInt(tmp);
  }

  /**
   * Sets the statusId attribute of the ContactList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }

  /**
   * Sets the statusId attribute of the ContactList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }

  /**
   * Gets the importId attribute of the ContactList object
   *
   * @return The importId value
   */
  public int getImportId() {
    return importId;
  }

  /**
   * Gets the statusId attribute of the ContactList object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }

  /**
   * Gets the EmailNotNull attribute of the ContactList object
   *
   * @return The EmailNotNull value
   */
  public boolean getEmailNotNull() {
    return emailNotNull;
  }

  /**
   * Gets the Owner attribute of the ContactList object
   *
   * @return The Owner value
   */
  public int getOwner() {
    return owner;
  }

  /**
   * Gets the Company attribute of the ContactList object
   *
   * @return The Company value
   */
  public String getCompany() {
    return company;
  }

  /**
   * Gets the Title attribute of the ContactList object
   *
   * @return The Title value
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the includeNonUsersOnly attribute of the ContactList object
   *
   * @return The includeNonUsersOnly value
   */
  public boolean getIncludeNonUsersOnly() {
    return includeNonUsersOnly;
  }

  /**
   * Sets the includeNonUsersOnly attribute of the ContactList object
   *
   * @param includeNonUsersOnly The new includeNonUsersOnly value
   */
  public void setIncludeNonUsersOnly(boolean includeNonUsersOnly) {
    this.includeNonUsersOnly = includeNonUsersOnly;
  }

  /**
   * Gets the MiddleName attribute of the ContactList object
   *
   * @return The MiddleName value
   */
  public String getMiddleName() {
    return middleName;
  }

  /**
   * Gets the LastName attribute of the ContactList object
   *
   * @return The LastName value
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Gets the FirstName attribute of the ContactList object
   *
   * @return The FirstName value
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Gets the hierarchialUsers attribute of the ContactList object
   *
   * @return The hierarchialUsers value
   */
  public int getHierarchialUsers() {
    return hierarchialUsers;
  }

  /**
   * Sets the hierarchialUsers attribute of the ContactList object
   *
   * @param tmp The new hierarchialUsers value
   */
  public void setHierarchialUsers(int tmp) {
    this.hierarchialUsers = tmp;
  }

  /**
   * Sets the hierarchialUsers attribute of the ContactList object
   *
   * @param tmp The new hierarchialUsers value
   */
  public void setHierarchialUsers(String tmp) {
    this.hierarchialUsers = Integer.parseInt(tmp);
  }

  /**
   * Gets the users attribute of the ContactList object
   *
   * @return The users value
   */
  public UserList getUsers() {
    return users;
  }

  /**
   * Sets the users attribute of the ContactList object
   *
   * @param tmp The new users value
   */
  public void setUsers(UserList tmp) {
    this.users = tmp;
  }

  /**
   * Gets the HtmlSelect attribute of the ContactList object
   *
   * @param selectName Description of Parameter
   * @return The HtmlSelect value
   * @since 1.8
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }

  /**
   * Gets the EmptyHtmlSelect attribute of the ContactList object
   *
   * @param selectName Description of Parameter
   * @param thisSystem Description of the Parameter
   * @return The EmptyHtmlSelect value
   */
  public String getEmptyHtmlSelect(SystemStatus thisSystem, String selectName) {
    HtmlSelect contactListSelect = new HtmlSelect();
    contactListSelect.addItem(-1, thisSystem
        .getLabel("calendar.none.4dashes"));

    return contactListSelect.getHtml(selectName);
  }

  /**
   * Gets the leadStatus attribute of the ContactList object
   *
   * @return The leadStatus value
   */
  public int getLeadStatus() {
    return leadStatus;
  }

  /**
   * Sets the leadStatus attribute of the ContactList object
   *
   * @param tmp The new leadStatus value
   */
  public void setLeadStatus(int tmp) {
    this.leadStatus = tmp;
  }

  /**
   * Sets the leadStatus attribute of the ContactList object
   *
   * @param tmp The new leadStatus value
   */
  public void setLeadStatus(String tmp) {
    this.leadStatus = Integer.parseInt(tmp);
  }

  /**
   * Gets the source attribute of the ContactList object
   *
   * @return The source value
   */
  public int getSource() {
    return source;
  }

  /**
   * Sets the source attribute of the ContactList object
   *
   * @param tmp The new source value
   */
  public void setSource(int tmp) {
    this.source = tmp;
  }

  /**
   * Sets the source attribute of the ContactList object
   *
   * @param tmp The new source value
   */
  public void setSource(String tmp) {
    this.source = Integer.parseInt(tmp);
  }

  
  /**
   * Gets the stage attribute of the ContactList object
   *
   * @return The stage value
   */
  public int getStage() {
    return stage;
  }

  /**
   * Sets the stage attribute of the ContactList object
   *
   * @param tmp The new stage value
   */
  public void setStage(int tmp) {
    this.stage = tmp;
  }

  /**
   * Sets the stage attribute of the ContactList object
   *
   * @param tmp The new stage value
   */
  public void setStage(String tmp) {
    this.stage = Integer.parseInt(tmp);
  }
  
  
  /**
   * Gets the rating attribute of the ContactList object
   *
   * @return The rating value
   */
  public int getRating() {
    return rating;
  }

  /**
   * Sets the rating attribute of the ContactList object
   *
   * @param tmp The new rating value
   */
  public void setRating(int tmp) {
    this.rating = tmp;
  }

  /**
   * Sets the rating attribute of the ContactList object
   *
   * @param tmp The new rating value
   */
  public void setRating(String tmp) {
    this.rating = Integer.parseInt(tmp);
  }

  /**
   * Gets the comments attribute of the ContactList object
   *
   * @return The comments value
   */
  public String getComments() {
    return comments;
  }

  /**
   * Sets the comments attribute of the ContactList object
   *
   * @param tmp The new comments value
   */
  public void setComments(String tmp) {
    this.comments = tmp;
  }

  /**
   * Gets the leadsOnly attribute of the ContactList object
   *
   * @return The leadsOnly value
   */
  public int getLeadsOnly() {
    return leadsOnly;
  }

  /**
   * Sets the leadsOnly attribute of the ContactList object
   *
   * @param tmp The new leadsOnly value
   */
  public void setLeadsOnly(int tmp) {
    this.leadsOnly = tmp;
  }

  /**
   * Sets the leadsOnly attribute of the ContactList object
   *
   * @param tmp The new leadsOnly value
   */
  public void setLeadsOnly(String tmp) {
    this.leadsOnly = Integer.parseInt(tmp);
  }

  /**
   * Gets the userId attribute of the ContactList object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Sets the userId attribute of the ContactList object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }

  /**
   * Sets the userId attribute of the ContactList object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }

  /**
   * Gets the leadStatusExists attribute of the ContactList object
   *
   * @return The leadStatusExists value
   */
  public int getLeadStatusExists() {
    return leadStatusExists;
  }

  /**
   * Sets the leadStatusExists attribute of the ContactList object
   *
   * @param tmp The new leadStatusExists value
   */
  public void setLeadStatusExists(int tmp) {
    this.leadStatusExists = tmp;
  }

  /**
   * Sets the leadStatusExists attribute of the ContactList object
   *
   * @param tmp The new leadStatusExists value
   */
  public void setLeadStatusExists(String tmp) {
    this.leadStatusExists = Integer.parseInt(tmp);
  }

  /**
   * Gets the readBy attribute of the ContactList object
   *
   * @return The readBy value
   */
  public int getReadBy() {
    return readBy;
  }

  /**
   * Sets the readBy attribute of the ContactList object
   *
   * @param tmp The new readBy value
   */
  public void setReadBy(int tmp) {
    this.readBy = tmp;
  }

  /**
   * Sets the readBy attribute of the ContactList object
   *
   * @param tmp The new readBy value
   */
  public void setReadBy(String tmp) {
    this.readBy = Integer.parseInt(tmp);
  }

  /**
   * Gets the enteredStart attribute of the ContactList object
   *
   * @return The enteredStart value
   */
  public Timestamp getEnteredStart() {
    return enteredStart;
  }

  /**
   * Sets the enteredStart attribute of the ContactList object
   *
   * @param tmp The new enteredStart value
   */
  public void setEnteredStart(Timestamp tmp) {
    this.enteredStart = tmp;
  }

  /**
   * Sets the enteredStart attribute of the ContactList object
   *
   * @param tmp The new enteredStart value
   */
  public void setEnteredStart(java.sql.Date tmp) {
    try {
      this.enteredStart = new Timestamp(tmp.getTime());
    } catch (Exception e) {
    }
  }

  /**
   * Sets the enteredStart attribute of the ContactList object
   *
   * @param tmp The new enteredStart value
   */
  public void setEnteredStart(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.LONG).parse(tmp);
      this.enteredStart = new java.sql.Timestamp(new java.util.Date()
          .getTime());
      this.enteredStart.setTime(tmpDate.getTime());
    } catch (Exception e) {
      this.enteredStart = null;
    }
    // this.enteredStart = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Gets the enteredEnd attribute of the ContactList object
   *
   * @return The enteredEnd value
   */
  public Timestamp getEnteredEnd() {
    return enteredEnd;
  }

  /**
   * Sets the enteredEnd attribute of the ContactList object
   *
   * @param tmp The new enteredEnd value
   */
  public void setEnteredEnd(Timestamp tmp) {
    this.enteredEnd = tmp;
  }

  /**
   * Sets the enteredEnd attribute of the ContactList object
   *
   * @param tmp The new enteredEnd value
   */
  public void setEnteredEnd(java.sql.Date tmp) {
    try {
      this.enteredEnd = new Timestamp(tmp.getTime());
    } catch (Exception e) {
    }
  }

  /**
   * Sets the enteredEnd attribute of the ContactList object
   *
   * @param tmp The new enteredEnd value
   */
  public void setEnteredEnd(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.LONG).parse(tmp);
      this.enteredEnd = new java.sql.Timestamp(new java.util.Date()
          .getTime());
      this.enteredEnd.setTime(tmpDate.getTime());
    } catch (Exception e) {
      this.enteredEnd = null;
    }
    // this.enteredEnd = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   * Gets the conversionDateStart attribute of the ContactList object
   *
   * @return The conversionDateStart value
   */
  public Timestamp getConversionDateStart() {
    return conversionDateStart;
  }

  /**
   * Sets the conversionDateStart attribute of the ContactList object
   *
   * @param tmp The new conversionDateStart value
   */
  public void setConversionDateStart(Timestamp tmp) {
    this.conversionDateStart = tmp;
  }

  /**
   * Sets the conversionDateStart attribute of the ContactList object
   *
   * @param tmp The new conversionDateStart value
   */
  public void setConversionDateStart(java.sql.Date tmp) {
    try {
      this.conversionDateStart = new Timestamp(tmp.getTime());
    } catch (Exception e) {
    }
  }

  /**
   * Sets the conversionDateStart attribute of the ContactList object
   *
   * @param tmp The new conversionDateStart value
   */
  public void setConversionDateStart(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.LONG).parse(tmp);
      this.conversionDateStart = new java.sql.Timestamp(
          new java.util.Date().getTime());
      this.conversionDateStart.setTime(tmpDate.getTime());
    } catch (Exception e) {
      this.conversionDateStart = null;
    }
  }

  /**
   * Gets the conversionDateEnd attribute of the ContactList object
   *
   * @return The conversionDateEnd value
   */
  public Timestamp getConversionDateEnd() {
    return conversionDateEnd;
  }

  /**
   * Sets the conversionDateEnd attribute of the ContactList object
   *
   * @param tmp The new conversionDateEnd value
   */
  public void setConversionDateEnd(Timestamp tmp) {
    this.conversionDateEnd = tmp;
  }

  /**
   * Sets the conversionDateEnd attribute of the ContactList object
   *
   * @param tmp The new conversionDateEnd value
   */
  public void setConversionDateEnd(java.sql.Date tmp) {
    try {
      this.conversionDateEnd = new Timestamp(tmp.getTime());
    } catch (Exception e) {
    }
  }

  /**
   * Sets the conversionDateEnd attribute of the ContactList object
   *
   * @param tmp The new conversionDateEnd value
   */
  public void setConversionDateEnd(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.LONG).parse(tmp);
      this.conversionDateEnd = new java.sql.Timestamp(
          new java.util.Date().getTime());
      this.conversionDateEnd.setTime(tmpDate.getTime());
    } catch (Exception e) {
      this.conversionDateEnd = null;
    }
  }

  /**
   * Gets the hasConversionDate attribute of the ContactList object
   *
   * @return The hasConversionDate value
   */
  public int getHasConversionDate() {
    return hasConversionDate;
  }

  /**
   * Sets the hasConversionDate attribute of the ContactList object
   *
   * @param tmp The new hasConversionDate value
   */
  public void setHasConversionDate(int tmp) {
    this.hasConversionDate = tmp;
  }

  /**
   * Sets the hasConversionDate attribute of the ContactList object
   *
   * @param tmp The new hasConversionDate value
   */
  public void setHasConversionDate(String tmp) {
    this.hasConversionDate = Integer.parseInt(tmp);
  }

  /**
   * Gets the oldestFirst attribute of the ContactList object
   *
   * @return The oldestFirst value
   */
  public int getOldestFirst() {
    return oldestFirst;
  }

  /**
   * Sets the oldestFirst attribute of the ContactList object
   *
   * @param tmp The new oldestFirst value
   */
  public void setOldestFirst(int tmp) {
    this.oldestFirst = tmp;
  }

  /**
   * Sets the oldestFirst attribute of the ContactList object
   *
   * @param tmp The new oldestFirst value
   */
  public void setOldestFirst(String tmp) {
    this.oldestFirst = Integer.parseInt(tmp);
  }

  /**
   * Gets the emailAddress attribute of the ContactList object
   *
   * @return The emailAddress value
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * Sets the emailAddress attribute of the ContactList object
   *
   * @param tmp The new emailAddress value
   */
  public void setEmailAddress(String tmp) {
    this.emailAddress = tmp;
  }

  /**
   * Gets the country attribute of the ContactList object
   *
   * @return The country value
   */
  public String getCountry() {
    return country;
  }

  /**
   * Sets the country attribute of the ContactList object
   *
   * @param tmp The new country value
   */
  public void setCountry(String tmp) {
    this.country = tmp;
  }

  /**
   * Gets the ownerOrReader attribute of the ContactList object
   *
   * @return The ownerOrReader value
   */
  public boolean getOwnerOrReader() {
    return ownerOrReader;
  }

  /**
   * Sets the ownerOrReader attribute of the ContactList object
   *
   * @param tmp The new ownerOrReader value
   */
  public void setOwnerOrReader(boolean tmp) {
    this.ownerOrReader = tmp;
  }

  /**
   * Sets the ownerOrReader attribute of the ContactList object
   *
   * @param tmp The new ownerOrReader value
   */
  public void setOwnerOrReader(String tmp) {
    this.ownerOrReader = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the permission attribute of the ContactList object
   *
   * @return The permission value
   */
  public String getPermission() {
    return permission;
  }

  /**
   * Sets the permission attribute of the ContactList object
   *
   * @param tmp The new permission value
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }

  /**
   * Gets the errors attribute of the ContactList object
   *
   * @return The errors value
   */
  public HashMap getErrors() {
    return errors;
  }

  /**
   * Sets the errors attribute of the ContactList object
   *
   * @param tmp The new errors value
   */
  public void setErrors(HashMap tmp) {
    this.errors = tmp;
  }

  /**
   * Gets the warnings attribute of the ContactList object
   *
   * @return The warnings value
   */
  public HashMap getWarnings() {
    return warnings;
  }

  /**
   * Sets the warnings attribute of the ContactList object
   *
   * @param tmp The new warnings value
   */
  public void setWarnings(HashMap tmp) {
    this.warnings = tmp;
  }

  /**
   * Gets the nextValue attribute of the ContactList object
   *
   * @return The nextValue value
   */
  public String getNextValue() {
    return nextValue;
  }

  /**
   * Sets the nextValue attribute of the ContactList object
   *
   * @param tmp The new nextValue value
   */
  public void setNextValue(String tmp) {
    this.nextValue = tmp;
  }

  /**
   * Gets the defaultContactId attribute of the ContactList object
   *
   * @return The defaultContactId value
   */
  public int getDefaultContactId() {
    return defaultContactId;
  }

  /**
   * Sets the defaultContactId attribute of the ContactList object
   *
   * @param defaultContactId The new defaultContactId value
   */
  public void setDefaultContactId(int defaultContactId) {
    this.defaultContactId = defaultContactId;
  }

  /**
   * Gets the city attribute of the ContactList object
   *
   * @return The city value
   */
  public String getCity() {
    return city;
  }

  /**
   * Sets the city attribute of the ContactList object
   *
   * @param tmp The new city value
   */
  public void setCity(String tmp) {
    this.city = tmp;
  }

  /**
   * Gets the zipCodeAscPotentialDesc attribute of the ContactList object
   *
   * @return The zipCodeAscPotentialDesc value
   */
  public boolean getZipCodeAscPotentialDesc() {
    return zipCodeAscPotentialDesc;
  }

  /**
   * Sets the zipCodeAscPotentialDesc attribute of the ContactList object
   *
   * @param tmp The new zipCodeAscPotentialDesc value
   */
  public void setZipCodeAscPotentialDesc(boolean tmp) {
    this.zipCodeAscPotentialDesc = tmp;
  }

  /**
   * Sets the zipCodeAscPotentialDesc attribute of the ContactList object
   *
   * @param tmp The new zipCodeAscPotentialDesc value
   */
  public void setZipCodeAscPotentialDesc(String tmp) {
    this.zipCodeAscPotentialDesc = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the postalCode attribute of the ContactList object
   *
   * @return The postalCode value
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * Sets the postalCode attribute of the ContactList object
   *
   * @param tmp The new postalCode value
   */
  public void setPostalCode(String tmp) {
    this.postalCode = tmp;
  }

  /**
   * Gets the exclusiveToSite attribute of the ContactList object
   *
   * @return The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }

  /**
   * Sets the exclusiveToSite attribute of the ContactList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }

  /**
   * Sets the exclusiveToSite attribute of the ContactList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the includeAllSites attribute of the ContactList object
   *
   * @return The includeAllSites value
   */
  public boolean getIncludeAllSites() {
    return includeAllSites;
  }

  /**
   * Sets the includeAllSites attribute of the ContactList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(boolean tmp) {
    this.includeAllSites = tmp;
  }

  /**
   * Sets the includeAllSites attribute of the ContactList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(String tmp) {
    this.includeAllSites = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the portalUsersOnly attribute of the ContactList object
   *
   * @return The portalUsersOnly value
   */
  public int getPortalUsersOnly() {
    return portalUsersOnly;
  }

  /**
   * Sets the portalUsersOnly attribute of the ContactList object
   *
   * @param tmp The new portalUsersOnly value
   */
  public void setPortalUsersOnly(int tmp) {
    this.portalUsersOnly = tmp;
  }

  /**
   * Gets the state attribute of the ContactList object
   *
   * @return The state value
   */
  public String getState() {
    return state;
  }

  /**
   * Sets the state attribute of the ContactList object
   *
   * @param tmp The new state value
   */
  public void setState(String tmp) {
    this.state = tmp;
  }

  /**
   * Gets the state1 attribute of the ContactList object
   *
   * @return The state1 value
   */
  public String getState1() {
    return state1;
  }

  /**
   * Sets the state1 attribute of the ContactList object
   *
   * @param tmp The new state1 value
   */
  public void setState1(String tmp) {
    this.state1 = tmp;
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }

  /**
   * Gets the HtmlSelect attribute of the ContactList object
   *
   * @param selectName Description of Parameter
   * @param defaultKey Description of Parameter
   * @return The HtmlSelect value
   * @since 1.8
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect contactListSelect = new HtmlSelect();
    contactListSelect.setJsEvent(jsEvent);

    if (emptyHtmlSelectRecord != null) {
      contactListSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Contact thisContact = (Contact) i.next();
      if (thisContact.getEnabled() == false) {
        if (thisContact.getId() != defaultKey) {
          continue;
        }
      }
      contactListSelect
          .addItem(thisContact.getId(), Contact.getNameLastFirst(
              thisContact.getNameLast(), thisContact
              .getNameFirst())
              + ((!thisContact.getEnabled() || thisContact
              .isTrashed()) ? " (X)"
              : (checkUserAccess ? (thisContact
              .hasAccount() ? " (*)" : "") : "")));
    }

    return contactListSelect.getHtml(selectName, defaultKey);
  }

  /**
   * Gets the htmlSelectObj attribute of the ContactList object
   *
   * @return The htmlSelectObj value
   */
  public HtmlSelect getHtmlSelectObj() {
    HtmlSelect contactListSelect = new HtmlSelect();
    contactListSelect.setJsEvent(jsEvent);

    if (emptyHtmlSelectRecord != null) {
      contactListSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Contact thisContact = (Contact) i.next();
      if (thisContact.getEnabled() == false) {
        continue;
      }
      contactListSelect
          .addItem(thisContact.getId(), Contact.getNameLastFirst(
              thisContact.getNameLast(), thisContact
              .getNameFirst())
              + ((!thisContact.getEnabled() || thisContact
              .isTrashed()) ? " (X)"
              : (checkUserAccess ? (thisContact
              .hasAccount() ? " (*)" : "") : "")));
    }

    return contactListSelect;
  }

  /**
   * Description of the Method
   *
   * @param thisOwnerId   Description of the Parameter
   * @param thisUserRange Description of the Parameter
   */
  public void buildQuery(int thisOwnerId, String thisUserRange) {
    String readyToGo = "";

    HashMap[] outerHash = null;

    // ONE FOR EACH IN THE FIELD LIST
    HashMap company = new HashMap();
    HashMap namefirst = new HashMap();
    HashMap namelast = new HashMap();
    HashMap entered = new HashMap();
    HashMap zip = new HashMap();
    HashMap areacode = new HashMap();
    HashMap city = new HashMap();
    HashMap typeId = new HashMap();
    HashMap contactId = new HashMap();
    HashMap title = new HashMap();
    HashMap accountTypeId = new HashMap();
    HashMap siteId = new HashMap();
    HashMap importName = new HashMap();

    // THIS CORRESPONDS TO THE FIELD LIST
    outerHash = new HashMap[]{company, namefirst, namelast, entered, zip,
        areacode, city, typeId, contactId, title, accountTypeId, siteId, importName};
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ContactList-> SCL Size: "
          + this.getScl().size() + " name: "
          + this.getScl().getGroupName());
    }
    Iterator i = this.getScl().keySet().iterator();
    while (i.hasNext()) {
      Integer group = (Integer) i.next();
      SearchCriteriaGroup thisGroup = (SearchCriteriaGroup) this.getScl()
          .get(group);

      Iterator j = thisGroup.iterator();

      while (j.hasNext()) {
        SearchCriteriaElement thisElement = (SearchCriteriaElement) j
            .next();
        readyToGo = replace(thisElement.getText().toLowerCase(), '\'', "\\'");
        //String check = (String) outerHash[(thisElement.getFieldId() - 1)].get(thisElement.getOperator());
        HashMap tempHash = (HashMap) outerHash[(thisElement.getFieldId() - 1)].get(
            thisElement.getOperator());

        // only if we have string data to deal with
        if (tempHash == null || tempHash.size() == 0
            || thisElement.getDataType().equals("date")) {
          if (thisElement.getDataType().equals("date")) {
            int month = 0;
            int day = 0;
            int year = 0;

            StringTokenizer st = new StringTokenizer(readyToGo, "/");
            if (st.hasMoreTokens()) {
              month = Integer.parseInt(st.nextToken());
              day = Integer.parseInt(st.nextToken());
              year = Integer.parseInt(st.nextToken());
              if (year < 50) {
                year += 2000;
              }
            }

            Calendar tmpCal = new GregorianCalendar(year,
                (month - 1), day);
            // fix it if "on or before" or "after" is selected.
            if (thisElement.getOperatorId() == 8
                || thisElement.getOperatorId() == 10) {
              tmpCal.add(java.util.Calendar.DATE, +1);
            }

            HashMap tempTable = new HashMap();

            String backToString = (tmpCal.get(Calendar.MONTH) + 1)
                + "/" + tmpCal.get(Calendar.DAY_OF_MONTH) + "/"
                + tmpCal.get(Calendar.YEAR);
            tempTable.put(backToString, thisElement.getSourceId()
                + "|" + thisElement.getSiteId());

            //outerHash[(thisElement.getFieldId() - 1)].put(thisElement.getOperator(), ("'" + backToString + "'"));
            outerHash[(thisElement.getFieldId() - 1)].put(
                thisElement.getOperator(), tempTable);
          } else {
            // first entry
            HashMap tempTable = new HashMap();
            tempTable.put(readyToGo, thisElement.getSourceId() + "|" + thisElement.getSiteId());
            //outerHash[(thisElement.getFieldId() - 1)].put(thisElement.getOperator(), ("'" + readyToGo + "'"));
            outerHash[(thisElement.getFieldId() - 1)].put(
                thisElement.getOperator(), tempTable);
          }
        } else {
          // check = check + ", '" + readyToGo + "'";
          tempHash.put(readyToGo, thisElement.getSourceId() + "|"
              + thisElement.getSiteId());
          outerHash[(thisElement.getFieldId() - 1)].remove(thisElement.getOperator());
          outerHash[(thisElement.getFieldId() - 1)].put(thisElement
              .getOperator(), tempHash);
          //outerHash[(thisElement.getFieldId() - 1)].put(thisElement.getOperator(), check);
        }
        // end of that
      }
    }

    // THIS PART IS ALSO DEPENDENT
    this.setSearchValues(outerHash);
  }

  /**
   * This will force the contact list to include records that are owned by the
   * user
   *
   * @param userId Description of the Parameter
   */
  public void accessedBy(int userId) {
    if (userId > -1) {
      this.setOwner(userId);
    }
  }

  /**
   * Builds a list, a part of the XML API
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }

  /**
   * Builds a list of contacts based on several parameters. The parameters are
   * set after this object is constructed, then the buildList method is called
   * to generate the list.
   *
   * @param db Description of Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
  
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    // Need to build a base SQL statement for counting records
    sqlCount
        .append("SELECT COUNT(*) AS recordcount "
            + "FROM contact c "
            + "LEFT JOIN organization o ON (c.org_id = o.org_id) "
            + "LEFT JOIN lookup_department d ON (c.department = d.code) "
            + "LEFT JOIN lookup_industry lind ON (c.industry_temp_code = lind.code) "
            + "LEFT JOIN lookup_contact_source lcs ON (c.source = lcs.code) "
            + "LEFT JOIN lookup_contact_stage lcst ON (c.stage = lcst.code) "
            + "LEFT JOIN lookup_contact_rating lcr ON (c.rating = lcr.code) "
            + "LEFT JOIN contact_address ca ON (c.contact_id = ca.contact_id) "
            + "LEFT JOIN lookup_site_id lsi ON (c.site_id = lsi.code) "
            + "WHERE ");

    createFilter(db, sqlFilter);

    if (pagedListInfo != null) {
      // Get the total number of records matching filter
      PreparedStatement pst = db.prepareStatement(sqlCount.toString()
          + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = DatabaseUtils.executeQuery(db, pst);
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      // Determine the offset, based on the filter, for the first record
      // to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString()
            + sqlFilter.toString() + "AND "
            + DatabaseUtils.toLowerCase(db) + "(c.namelast) < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter()
            .toLowerCase());
        rs = DatabaseUtils.executeQuery(db, pst);
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      // Determine column to sort by
      if (this.getZipCodeAscPotentialDesc()) {
        if (this.oldestFirst == Constants.TRUE
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.entered, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.FALSE
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.entered DESC, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.UNDEFINED
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.namelast, c.namefirst, c.org_name");
        }
      } else {
        if (this.oldestFirst == Constants.TRUE
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("c.entered, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.FALSE
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("c.entered DESC, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.UNDEFINED
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("c.namelast, c.namefirst, c.org_name");
        }
      }
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      if (this.getZipCodeAscPotentialDesc()) {
        if (this.oldestFirst == Constants.TRUE) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.entered, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.FALSE) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.entered DESC, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.UNDEFINED) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.namelast, c.namefirst, c.org_name");
        }
      } else {
        if (this.oldestFirst == Constants.TRUE) {
          sqlOrder
              .append("ORDER BY c.entered, c.namelast, c.namefirst, c.org_name ");
        } else if (this.oldestFirst == Constants.FALSE) {
          sqlOrder
              .append("ORDER BY c.entered DESC, c.namelast, c.namefirst, c.org_name ");
        } else if (this.oldestFirst == Constants.UNDEFINED) {
          sqlOrder
              .append("ORDER BY c.namelast, c.namefirst, c.org_name ");
        }
      }
    }

    // Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect
        .append("c.*, o.enabled AS orgenabled, o.trashed_date AS orgtrasheddate, "
            + " d.description as departmentname, "
            + " ca.city AS city, "
            + " ca.postalcode AS postalcode, "
            + " lsi.description AS site_id_name, "
            + " lind.description AS industry_name, "
            + " lcs.description AS source_name, "
            + " lcst.description AS stage_name, "
            + " lcr.description AS rating_name, "
            + " i.name AS import_name "
            + "FROM contact c "
            + "LEFT JOIN organization o ON (c.org_id = o.org_id) "
            + "LEFT JOIN lookup_department d ON (c.department = d.code) "
            + "LEFT JOIN lookup_industry lind ON (c.industry_temp_code = lind.code) "
            + "LEFT JOIN lookup_contact_source lcs ON (c.source = lcs.code) "
            + "LEFT JOIN lookup_contact_stage lcst ON (c.stage = lcst.code) "
            + "LEFT JOIN lookup_contact_rating lcr ON (c.rating = lcr.code) "
            + "LEFT JOIN contact_address ca ON (c.contact_id = ca.contact_id) "
            + "LEFT JOIN lookup_site_id lsi ON (c.site_id = lsi.code) "
            + "LEFT JOIN import i ON (c.import_id = i.import_id) "
            + "WHERE ");
    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString()
        + sqlOrder.toString());
    items = prepareFilter(pst);
    return pst;
  }
  
  
  /**
   * Builds a list of contacts based on several parameters. The parameters are
   * set after this object is constructed, then the buildList method is called
   * to generate the list.
   *
   * @param db Description of Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public boolean buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    boolean foundDefaultContact = false;
    while (rs.next()) {
      Contact thisContact = ContactList.getObject(rs);
      if (thisContact.getId() == defaultContactId) {
        foundDefaultContact = true;
      }
      this.addElement(thisContact);
    }
    rs.close();
    if(pst != null){
      pst.close();
    }
    if (defaultContactId != -1 && !foundDefaultContact) {
      Contact thisContact = new Contact(db, defaultContactId);
      this.addElement(thisContact);
    }
    buildResources(db);
    return true;
  }

  /**
   * Adds a feature to the IgnoreTypeId attribute of the ContactList object
   *
   * @param tmp The feature to be added to the IgnoreTypeId attribute
   * @since 1.2
   */
  public void addIgnoreTypeId(String tmp) {
    ignoreTypeIdList.addElement(tmp);
  }

  /**
   * Adds a feature to the IgnoreTypeId attribute of the ContactList object
   *
   * @param tmp The feature to be added to the IgnoreTypeId attribute
   * @since 1.2
   */
  public void addIgnoreTypeId(int tmp) {
    ignoreTypeIdList.addElement(String.valueOf(tmp));
  }

  /**
   * Description of the Method
   *
   * @param db           Description of Parameter
   * @param baseFilePath Description of the Parameter
   * @param forceDelete  Description of the Parameter
   * @throws SQLException Description of Exception
   */
  public void delete(Connection db, String baseFilePath, boolean forceDelete)
      throws SQLException {
    Iterator contacts = this.iterator();
    while (contacts.hasNext()) {
      Contact thisContact = (Contact) contacts.next();
      thisContact.setForceDelete(forceDelete);
      thisContact.delete(db, baseFilePath);
    }
  }

  /**
   * Description of the Method
   *
   * @param str     Description of Parameter
   * @param oldChar Description of Parameter
   * @param newStr  Description of Parameter
   * @return Description of the Returned Value
   */
  String replace(String str, char oldChar, String newStr) {
    String replacedStr = "";
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == oldChar) {
        replacedStr += newStr;
      } else {
        replacedStr += c;
      }
    }
    return replacedStr;
  }

  /**
   * Convenience method to get a list of phone numbers for each contact
   *
   * @param db Description of Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of Exception
   * @since 1.5
   */
  private boolean buildResources(Connection db) throws SQLException {
    if (!buildTypes && !buildDetails && !buildPhoneNumbers
        && !checkUserAccess && !checkEnabledUserAccess
        && checkExcludedFromCampaign == -1) {
      return false;
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Contact thisContact = (Contact) i.next();
      if (buildTypes) {
        thisContact.buildTypes(db);
      }
      if (buildDetails) {
        thisContact.getPhoneNumberList().setContactId(
            thisContact.getId());
        thisContact.getPhoneNumberList().buildList(db);
        thisContact.getAddressList().setContactId(thisContact.getId());
        thisContact.getAddressList().buildList(db);
        thisContact.getEmailAddressList().setContactId(
            thisContact.getId());
        thisContact.getEmailAddressList().buildList(db);
      }

      if (!buildDetails && buildPhoneNumbers) {
        thisContact.getPhoneNumberList().setContactId(
            thisContact.getId());
        thisContact.getPhoneNumberList().buildList(db);
      }

      if (checkUserAccess) {
        thisContact.checkUserAccount(db);
      }
      if (checkEnabledUserAccess) {
        thisContact.checkEnabledUserAccount(db);
      }
      if (checkExcludedFromCampaign > -1) {
        thisContact.checkExcludedFromCampaign(db,
            checkExcludedFromCampaign);
      }
    }
    return true;
  }

  /**
   * Builds a base SQL where statement for filtering records to be used by
   * sqlSelect and sqlCount
   *
   * @param sqlFilter Description of Parameter
   * @param db        Description of the Parameter
   * @since 1.3
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if(id == -1){
      sqlFilter.append("c.contact_id > ?");
    }else{
      sqlFilter.append("c.contact_id = ?");
    }

    sqlFilter
        .append("AND (ca.address_id IS NULL OR ca.address_id IN ( "
            + "SELECT cta.address_id FROM contact_address cta WHERE cta.contact_id = c.contact_id AND cta.primary_address = ?) "
            + "OR ca.address_id IN (SELECT MIN(ctadd.address_id) FROM contact_address ctadd WHERE ctadd.contact_id = c.contact_id AND "
            + " ctadd.contact_id NOT IN (SELECT contact_id FROM contact_address WHERE contact_address.primary_address = ?))) ");

    if (contactUserId != -1) {
      sqlFilter.append("AND c.user_id = ? ");
    }
    if (orgId != -1) {
      sqlFilter.append("AND c.org_id = ? ");
    }

    if (includeEnabled == Constants.TRUE
        || includeEnabled == Constants.FALSE) {
      sqlFilter.append("AND c.enabled = ? ");
    }

    if (owner != -1 && !ownerOrReader) {
      sqlFilter
          .append("AND c.owner = ? "
              + "AND ((c.org_id = 0 AND employee = ?) OR c.org_id <> 0 OR c.org_id IS NULL) ");
    }

    if (typeId != -1) {
      sqlFilter
          .append("AND (c.contact_id in (SELECT contact_id from contact_type_levels ctl where ctl.type_id = ?) )");
    }

    if (departmentId > -1) {
      if (departmentId != 0) {
        sqlFilter.append("AND c.department = ? ");
      } else {
        sqlFilter.append("AND c.department IS NULL ");
      }
    }

    if (ruleId != -1) {
      sqlFilter
          .append("AND c.access_type IN (SELECT code from lookup_access_types where rule_id = ? AND code = c.access_type) ");
    }
    if (projectId != -1) {
      sqlFilter
          .append("AND c.user_id in (SELECT DISTINCT user_id FROM project_team WHERE project_id = ?) ");
    }

    if (firstName != null) {
      if (firstName.indexOf("%") >= 0) {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.namefirst) LIKE ? ");
      } else {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.namefirst) = ? ");
      }
    }

    if (middleName != null) {
      if (middleName.indexOf("%") >= 0) {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.namemiddle) LIKE ? ");
      } else {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.namemiddle) = ? ");
      }
    }

    if (lastName != null) {
      if (lastName.indexOf("%") >= 0) {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.namelast) LIKE ? ");
      } else {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.namelast) = ? ");
      }
    }

    if (accountName != null) {
      if (accountName.indexOf("%") >= 0) {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.org_name) LIKE ? ");
      } else {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.org_name) = ? ");
      }
    }

    if (accountNumber != null) {
      if (accountNumber.indexOf("%") >= 0) {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(o.account_number) LIKE ? ");
      } else {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(o.account_number) = ? ");
      }
    }

    if (accountTypeId != -1) {
      sqlFilter
          .append("AND EXISTS (SELECT * FROM account_type_levels atl WHERE atl.type_id = ? AND o.org_id = atl.org_id) ");
    }

    if (accountOwnerId != -1) {
      sqlFilter.append("AND o.owner = ? ");
    }

    if (includeEnabledAccount == Constants.TRUE
        || includeEnabledAccount == Constants.FALSE) {
      sqlFilter.append("AND o.enabled = ? ");
    }

    if (assetSerialNumber != null) {
      sqlFilter
          .append("AND EXISTS (SELECT o.org_id FROM asset a WHERE o.org_id = a.account_id AND "
              + DatabaseUtils.toLowerCase(db)
              + " (a.serial_number) = ? )");
    }

    if (accountSegment != null) {
      if (accountSegment.indexOf("%") >= 0) {
        sqlFilter
            .append("AND o.segment_id in (select code from lookup_segments WHERE "
                + DatabaseUtils.toLowerCase(db)
                + "(description) LIKE ?) ");
      } else {
        sqlFilter
            .append("AND o.segment_id in (select code from lookup_segments where "
                + DatabaseUtils.toLowerCase(db)
                + "(description) = ?) ");
      }
    }

    if (contactPhoneNumber != null) {
      if (contactPhoneNumber.indexOf("%") >= 0) {
        sqlFilter
            .append("AND c.contact_id IN (SELECT cp.contact_id from contact_phone cp  where "
                + DatabaseUtils.toLowerCase(db)
                + "(cp.number) like ? ) ");
      } else {
        sqlFilter
            .append("AND c.contact_id IN (SELECT cp.contact_id from contact_phone cp where  "
                + DatabaseUtils.toLowerCase(db)
                + "(cp.number) = ?) ");
      }
    }

    if (contactState != null && !"-1".equals(contactState)) {
      if (contactState.indexOf("%") >= 0) {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(ca.state) like ? ");
      } else {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(ca.state) = ? ");
      }
    }

    if (accountPostalCode != null) {
      if (accountPostalCode.indexOf("%") >= 0) {
        sqlFilter
            .append("AND EXISTS (SELECT o.org_id FROM organization o WHERE o.org_id = c.org_id AND o.org_id IN (SELECT org_id FROM organization_address "
                + "WHERE "
                + DatabaseUtils.toLowerCase(db, "postalcode")
                + " LIKE ? " + "AND postalcode IS NOT NULL)) ");
      } else {
        sqlFilter
            .append("AND EXISTS (SELECT o.org_id FROM organization o WHERE o.org_id = c.org_id AND o.org_id IN (SELECT org_id FROM organization_address "
                + "WHERE "
                + DatabaseUtils.toLowerCase(db, "postalcode")
                + " = ? " + "AND postalcode IS NOT NULL)) ");
      }
    }

    if (contactCity != null) {
      if (contactCity.indexOf("%") >= 0) {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(ca.city) like ? ");
      } else {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(ca.city) = ? ");
      }
    }

    if (title != null) {
      if (title.indexOf("%") >= 0) {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.title) LIKE ? ");
      } else {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.title) = ? ");
      }
    }

    if (company != null) {
      if (company.indexOf("%") >= 0) {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.org_name) LIKE ? ");
      } else {
        sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
            + "(c.org_name) = ? ");
      }
    }

    if (orgSiteId != -1) {
      sqlFilter
          .append("AND EXISTS (SELECT c.contact_id FROM organization o WHERE  o.org_id = c.org_id AND ( o.site_id = ? ");
      if (includeContactsFromOrganizationsWithoutSite) {
        sqlFilter.append(" OR o.site_id IS NULL ");
      }
      sqlFilter.append("))");
      // revisit this line to ensure that it does not have side effects
      // sqlFilter.append(") OR c.org_id IS NULL ) ");
    }

    if (orgSiteId == -1) {
      if (includeContactsFromOrganizationsWithoutSite) {
        sqlFilter
            .append("AND EXISTS (SELECT c.contact_id FROM organization o WHERE  o.org_id = c.org_id AND o.site_id IS NULL )");
      }
    }

    if (userSiteId != -1) {
      sqlFilter
          .append("AND EXISTS (SELECT c.contact_id FROM "
              + DatabaseUtils.addQuotes(db, "access")
              + " a WHERE  a.contact_id = c.contact_id AND ( a.site_id = ? ");
      if (includeUsersWithAccessToAllSites) {
        sqlFilter.append(" OR a.site_id IS NULL ");
      }
      sqlFilter.append(")) ");
    }

    if (userSiteId == -1) {
      if (includeUsersWithAccessToAllSites) {
        sqlFilter
            .append("AND EXISTS (SELECT c.contact_id FROM "
                + DatabaseUtils.addQuotes(db, "access")
                + " a WHERE  a.contact_id = c.contact_id AND a.site_id IS NULL )");
      }
    }

    if (importSiteId != -1) {
      sqlFilter
          .append("AND EXISTS (SELECT c.contact_id FROM import i WHERE  i.import_id = c.import_id AND ( i.site_id = ? ");
      if (includeContactsFromImportsOfAllSites) {
        sqlFilter.append(" OR i.site_id IS NULL ");
      }
      sqlFilter.append(")) ");
    }

    if (importSiteId == -1) {
      if (includeContactsFromImportsOfAllSites) {
        sqlFilter
            .append("AND EXISTS (SELECT c.contact_id FROM import i WHERE  i.import_id = c.import_id AND i.site_id IS NULL )");
      }
    }

    if (!includeAllSites && orgId == -1 && !(scl != null && scl.size() > 0)) {
      if (siteId != -1) {
        sqlFilter.append("AND ( c.site_id = ? ");
        if (!exclusiveToSite) {
          sqlFilter.append(" OR c.site_id IS NULL ");
        }
        sqlFilter.append(") ");
      } else {
        sqlFilter.append(" AND c.site_id IS NULL ");
      }
    }

    if (controlledHierarchyOnly) {
      sqlFilter.append("AND (c.owner IN (" + ownerIdRange
          + ") OR c.owner IS NULL ) ");
    }

    if (contactIdRange != null && scl.getOnlyContactIds() == true) {
      sqlFilter.append("AND c.contact_id IN (" + contactIdRange + ") ");
    }

    if (withAccountsOnly) {
      sqlFilter.append("AND c.org_id > 0 ");
    }

    if (withProjectsOnly) {
      sqlFilter
          .append("AND c.user_id in (Select distinct pt.user_id from project_team pt LEFT JOIN "
              + DatabaseUtils.addQuotes(db, "access")
              + " a ON (pt.user_id = a.user_id) ");
      if (!includeAllSites && orgId == -1) {
        if (siteId != -1) {
          sqlFilter.append("WHERE (a.site_id = ? ");
          if (!exclusiveToSite) {
            sqlFilter.append("OR a.site_id IS NULL ");
          }
          sqlFilter.append(") ");
        } else {
          sqlFilter.append("WHERE a.site_id IS NULL ");
        }
      }
      sqlFilter.append(") ");
    }

    if (includeEnabledUsersOnly) {
      if (userRoleType > -1) {
        sqlFilter.append("AND EXISTS (SELECT user_id FROM "
            + DatabaseUtils.addQuotes(db, "access") + " a "
            + "WHERE c.user_id = a.user_id AND a.enabled = ? "
            + "AND a.role_id IN (SELECT r.role_id FROM "
            + DatabaseUtils.addQuotes(db, "role")
            + " r WHERE r.role_type = ?)) ");
      } else {
        sqlFilter.append("AND c.user_id IN (SELECT user_id FROM "
            + DatabaseUtils.addQuotes(db, "access")
            + " WHERE enabled = ?) ");
      }
    }

    if (portalUsersOnly == Constants.TRUE) {
      sqlFilter.append("AND EXISTS (SELECT user_id FROM "
          + DatabaseUtils.addQuotes(db, "access") + " a "
          + "WHERE c.user_id = a.user_id AND a.enabled = ? "
          + "AND a.role_id IN (SELECT r.role_id FROM "
          + DatabaseUtils.addQuotes(db, "role")
          + " r WHERE r.role_type = ?)) ");
    } else if (portalUsersOnly == Constants.FALSE) {
      sqlFilter.append("AND EXISTS (SELECT user_id FROM "
          + DatabaseUtils.addQuotes(db, "access") + " a "
          + "WHERE c.user_id = a.user_id "
          + "AND a.role_id IN (SELECT r.role_id FROM "
          + DatabaseUtils.addQuotes(db, "role")
          + " r WHERE r.role_type != ?)) ");
    }

    if (includeNonUsersOnly) {
      sqlFilter.append("AND c.contact_id NOT IN (SELECT contact_id FROM "
          + DatabaseUtils.addQuotes(db, "access") + ") ");
    }

    if (includeUsersOnly) {
      sqlFilter.append("AND c.user_id IN (SELECT user_id FROM "
          + DatabaseUtils.addQuotes(db, "access") + ") ");
    }

    if ((includeEnabledUsersOnly || includeUsersOnly) && permission != null
        && !"".equals(permission)) {
      sqlFilter
          .append("AND EXISTS (SELECT user_id FROM "
              + DatabaseUtils.addQuotes(db, "access")
              + " a "
              + "WHERE c.user_id = a.user_id AND a.role_id IN "
              + "(SELECT rp.role_id FROM role_permission rp "
              + "LEFT JOIN permission p ON (rp.permission_id = p.permission_id) "
              + "WHERE rp.role_id > 0 ");
      String[] temp = permission.split(",");
      for (int i = 0; i < temp.length; i++) {
        String str = temp[i];
        if (i == 0) {
          sqlFilter.append("AND ( ");
        }
        sqlFilter.append("p.permission = ? " + "AND role_"
            + str.substring(str.lastIndexOf("-") + 1, str.length())
            + " = ? ");
        if (i < temp.length - 1) {
          sqlFilter.append(") OR ( ");
        } else if (i == temp.length - 1) {
          sqlFilter.append(") ");
        }
      }
      sqlFilter.append(")) ");
    }

    if (employeesOnly != Constants.UNDEFINED) {
      sqlFilter.append("AND c.employee = ? ");
    }
    if (accountOwnerIdRange != null) {
      sqlFilter
          .append("AND c.org_id IN (SELECT org_id FROM organization WHERE owner IN ("
              + accountOwnerIdRange + ")) ");
    }

    if (excludeAccountContacts) {
      sqlFilter.append("AND c.org_id IS NULL ");
    }

    if (importId != -1) {
      sqlFilter.append("AND c.import_id = ? ");
    }

    if (statusId != -1) {
      sqlFilter.append("AND c.status_id = ? ");
    }

    if (excludeUnapprovedContacts) {
      sqlFilter.append("AND (c.status_id IS NULL OR c.status_id = ?) ");
    }

    if (!showTrashedAndNormal) {
      if (includeOnlyTrashed) {
        sqlFilter.append("AND c.trashed_date IS NOT NULL ");
      } else if (trashedDate != null) {
        sqlFilter.append("AND trashed_date = ? ");
      } else {
        sqlFilter.append("AND c.trashed_date IS NULL ");
      }
    }
    if (leadsOnly != Constants.UNDEFINED) {
      sqlFilter.append("AND c.lead = ? ");
    }
    if (leadStatus > 0 && employeesOnly == Constants.UNDEFINED) {
      if (leadStatus == Contact.LEAD_UNPROCESSED
          || leadStatus == Contact.LEAD_TRASHED
          || leadStatus == Contact.LEAD_ASSIGNED) {
        sqlFilter.append("AND c.lead_status = ? ");
      }
    } else if (leadsOnly == Constants.TRUE
        && leadStatus == Contact.LEAD_UNREAD && readBy == -1
        && !ownerOrReader && employeesOnly == Constants.UNDEFINED) {
      sqlFilter.append("AND c.lead_status = ? ");
      sqlFilter
          .append("AND c.contact_id NOT IN ( "
              + "SELECT contact_id FROM contact_lead_read_map WHERE user_id <> ? ) "
              + "AND c.contact_id NOT IN ( "
              + "SELECT contact_id FROM contact_lead_skipped_map WHERE user_id = ?) "
              + "");
    } else if (leadStatus == -1 && readBy == -1
        && employeesOnly == Constants.UNDEFINED
        && leadsOnly == Constants.TRUE) {
      sqlFilter.append("AND c.lead_status IN (?, ?, ?) ");
    }
    if (source > -1) {
      sqlFilter.append("AND c.source = ? ");
    }
    if (stage > -1) {
      sqlFilter.append("AND c.stage = ? ");
    }
    if (rating > -1) {
      sqlFilter.append("AND c.rating = ? ");
    }

    if (industry > -1) {
      sqlFilter.append("AND c.industry_temp_code = ? ");
    }

    if (leadsOnly == Constants.TRUE && readBy > -1 && !ownerOrReader) {
      sqlFilter
          .append("AND c.contact_id NOT IN ( "
              + "SELECT contact_id FROM contact_lead_skipped_map WHERE user_id = ?) "
              + "AND c.contact_id IN ( "
              + "SELECT contact_id FROM contact_lead_read_map WHERE user_id = ? ) ");
    }

    if (leadStatusExists == Constants.TRUE) {
      sqlFilter.append("AND c.lead_status IS NOT NULL ");
    } else if (leadStatusExists == Constants.FALSE) {
      sqlFilter.append("AND c.lead_status IS NULL ");
    }

    if (enteredStart != null) {
      sqlFilter.append("AND c.entered >= ? ");
    }

    if (enteredEnd != null) {
      sqlFilter.append("AND c.entered <= ? ");
    }

    if (conversionDateStart != null) {
      sqlFilter.append("AND c.conversion_date >= ? ");
    }

    if (conversionDateEnd != null) {
      sqlFilter.append("AND c.conversion_date <= ? ");
    }

    if (emailAddress != null) {
      if (emailAddress.indexOf("%") >= 0) {
        sqlFilter
            .append("AND c.contact_id IN (SELECT cc.contact_id FROM "
                + "contact cc LEFT JOIN contact_emailaddress ce ON (cc.contact_id = ce.contact_id ) "
                + "WHERE cc.contact_id = c.contact_id AND ("
                + DatabaseUtils.toLowerCase(db)
                + "(ce.email) LIKE ?)) ");
      } else {
        sqlFilter
            .append("AND c.contact_id IN (SELECT cc.contact_id FROM "
                + "contact cc LEFT JOIN contact_emailaddress ce ON (cc.contact_id = ce.contact_id ) "
                + "WHERE cc.contact_id = c.contact_id AND ce.email = ? ) ");
      }
    }

    /*
       * if (postalCode != -1 || city != null) { sqlFilter.append( "AND
       * c.contact_id IN (" + "SELECT cc.contact_id FROM contact cc LEFT JOIN
       * contact_address ca " + "ON (cc.contact_id = ca.contact_id) " + "WHERE
       * cc.contact_id > -1 ");
       */
    if (postalCode != null) {
      sqlFilter.append("AND ca.postalcode LIKE ? ");
    }
    if (city != null) {
      sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
          + "(ca.city) = ? ");
    }
    if (state != null && !"-1".equals(state)) {
      sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
          + "(ca.state) = ? ");
    } else if (state1 != null) {
      sqlFilter.append("AND " + DatabaseUtils.toLowerCase(db)
          + "(ca.state) = ? ");
    }
    /*
     *  sqlFilter.append(") ");
     *  }
       */
    if (hasConversionDate == Constants.TRUE) {
      sqlFilter.append("AND c.conversion_date IS NOT NULL ");
    } else if (hasConversionDate == Constants.FALSE) {
      sqlFilter.append("AND c.conversion_date IS NULL ");
    }

    if (country != null && !"-1".equals(country)) {
      sqlFilter.append("AND ca.country = ? ");
      /*
       *  "AND c.contact_id IN (SELECT cc.contact_id FROM " +
       *  "contact cc LEFT JOIN contact_address ca ON (cc.contact_id = ca.contact_id) " +
          * "WHERE ca.country = ? ) ");
          */
    }

    if (ownerOrReader) {
      sqlFilter
          .append("AND c.contact_id NOT IN ( "
              + "SELECT contact_id FROM contact_lead_skipped_map WHERE user_id = ?) "
              + "AND (c.owner = ? OR c.contact_id IN (SELECT contact_id "
              + "FROM contact_lead_read_map WHERE user_id = ?)) ");
    }

    if (this.getHierarchialUsers() != -1) {
      try {
        users = new UserList();
        User thisRec = new User(db, this.getHierarchialUsers());
        thisRec.setBuildHierarchy(true);
        thisRec.buildResources(db);
        UserList shortChildList = thisRec.getShortChildList();
        UserList newUserList = thisRec.getFullChildList(shortChildList,
            new UserList());
        sqlFilter.append("AND c.user_id IN ( ? ");
        users.add(thisRec);
        Iterator iterator = (Iterator) newUserList.iterator();
        while (iterator.hasNext()) {
          User thisUser = (User) iterator.next();
          users.add(thisUser);
          sqlFilter.append(",?");
        }
        sqlFilter.append(") ");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    // TODO: Use cached AccessTypeList to get the public codes for Account &
    // General contacts
    if (allContacts) {
      sqlFilter
          .append("AND (c.owner IN ("
              + ownerIdRange
              + ") "
              + "OR c.access_type IN (SELECT code from lookup_access_types WHERE rule_id = ? AND code = c.access_type)) "
              + "AND ((c.org_id = 0 AND employee = ?) OR c.org_id <> 0 OR c.org_id IS NULL) ");
    }

    // NOTE: Only general contacts can be personal and so AccessTypeList has
    // to be for the General Contacts
    switch (personalId) {
      case IGNORE_PERSONAL:
        break;
      case EXCLUDE_PERSONAL:
        if (accessTypes == null) {
          sqlFilter
              .append("AND c.access_type NOT IN (SELECT code from lookup_access_types WHERE rule_id = ? AND code = c.access_type) ");
        } else {
          sqlFilter.append("AND c.access_type NOT IN ("
              + accessTypes.getCode(AccessType.PERSONAL) + ") ");
        }
        break;
      default:
        if (accessTypes == null) {
          sqlFilter
              .append("AND (c.access_type NOT IN (SELECT code from lookup_access_types WHERE rule_id = ? AND code = c.access_type)  OR (c.access_type IN (SELECT code from lookup_access_types WHERE rule_id = ? AND code = c.access_type) AND c.owner = ?)) ");
        } else {
          sqlFilter.append("AND (c.access_type NOT IN ("
              + accessTypes.getCode(AccessType.PERSONAL)
              + ")  OR (c.access_type IN ("
              + accessTypes.getCode(AccessType.PERSONAL)
              + ") AND c.owner = ?)) ");
        }
        break;
    }

    if (searchText != null && !"".equals(searchText)) {
      sqlFilter.append(
          "AND ( " + DatabaseUtils.toLowerCase(db) + "(c.namelast) LIKE ? " +
            "OR " + DatabaseUtils.toLowerCase(db) + "(c.namefirst) LIKE ? " +
            "OR " + DatabaseUtils.toLowerCase(db) + "(c.org_name) LIKE ? " +
          ")"
      );
    }

    if (ignoreTypeIdList.size() > 0) {
      Iterator iterator = ignoreTypeIdList.iterator();
      while (iterator.hasNext()) {
        String nextValue = (String) iterator.next();
        if (nextValue.equals("" + Contact.EMPLOYEE_TYPE)) {
          sqlFilter.append("AND c.employee = ? ");
        } else if (nextValue.equals("" + Contact.LEAD_TYPE)) {
          sqlFilter.append("AND c.lead = ? ");
        }
      }
    }

    // contactIds
    if (contactIdHash != null && contactIdHash.size() > 0) {
      boolean newTerm = true;

      HashMap innerHash = (HashMap) contactIdHash.get("=");
      if (innerHash != null) {
        int termsProcessed = 0;
        Iterator inner = innerHash.keySet().iterator();

        while (inner.hasNext()) {
          String key2 = (String) inner.next();

          newTerm = processElementHeader(sqlFilter, newTerm,
              termsProcessed);
          sqlFilter.append(" (c.contact_id  = " + key2 + ") ");
          termsProcessed++;
        }

        if (!newTerm) {
          sqlFilter.append(") ");
        }
      }
    }

    // loop on the types
    for (int y = 1; y < (SearchCriteriaList.CONTACT_SOURCE_ELEMENTS + 1); y++) {
      boolean newTerm = true;
      int termsProcessed = 0;

      // company names
      if (companyHash != null && companyHash.size() > 0) {
        Iterator outer = companyHash.keySet().iterator();

        termsProcessed = 0;
        String previousKey = null;

        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) companyHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();

          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            String site = processSite(elementTypeString);

            // equals and != are the only operators supported right
            // now
            if (elementType == y
                && (key1.equals("=") || key1.equals("!="))) {
              if (termsProcessed > 0
                  && !(previousKey.equals(key1))) {
                newTerm = processElementHeader(sqlFilter,
                    newTerm, 0);
              } else {
                if (termsProcessed > 0 && key1.equals("!=")
                    && previousKey.equals(key1)) {
                  // if you're doing multiple != terms in a
                  // row, what you really want is an AND not
                  // an OR
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, 0);
                } else {
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, termsProcessed);
                }
              }

              if (termsProcessed == 0) {
                sqlFilter.append("( ");
              }

              sqlFilter.append(" ("
                  + DatabaseUtils.toLowerCase(db)
                  + "(c.org_name) " + key1 + " '" + key2
                  + "' ) ");

              previousKey = key1;
              processElementType(db, sqlFilter, elementType);
              processSite(sqlFilter, elementType, site);
              termsProcessed++;
            }
          }
        }
        if (termsProcessed > 0) {
          sqlFilter.append(")");
        }
      }

      // first names
      if (nameFirstHash != null && nameFirstHash.size() > 0) {
        Iterator outer = nameFirstHash.keySet().iterator();

        termsProcessed = 0;
        String previousKey = null;

        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) nameFirstHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();

          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            String site = processSite(elementTypeString);

            //equals and != are the only operators supported right now
            if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
              if (termsProcessed > 0 && !(previousKey.equals(key1))) {
                newTerm = processElementHeader(sqlFilter, newTerm, 0);
              } else {
                if (termsProcessed > 0 && key1.equals("!=")
                    && previousKey.equals(key1)) {
                  //if you're doing multiple != terms in a row, what you really want is an AND not an OR
                  newTerm = processElementHeader(sqlFilter, newTerm, 0);
                } else {
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, termsProcessed);
                }
              }

              if (termsProcessed == 0) {
                sqlFilter.append("(");
              }

              sqlFilter.append(" ("
                  + DatabaseUtils.toLowerCase(db)
                  + "(c.namefirst) " + key1 + " '" + key2
                  + "' )");
              previousKey = key1;
              processElementType(db, sqlFilter, elementType);
              processSite(sqlFilter, elementType, site);
              termsProcessed++;
            }
          }
        }
        if (termsProcessed > 0) {
          sqlFilter.append(")");
        }
      }

      // last names
      if (nameLastHash != null && nameLastHash.size() > 0) {
        Iterator outer = nameLastHash.keySet().iterator();

        termsProcessed = 0;
        String previousKey = null;

        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) nameLastHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();

          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash
                .get(key2)).toString();
            int elementType = processType(elementTypeString);
            String site = processSite(elementTypeString);

            //equals and != are the only operators supported right now
            if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
              if (termsProcessed > 0 && !(previousKey.equals(key1))) {
                newTerm = processElementHeader(sqlFilter, newTerm, 0);
              } else {
                if (termsProcessed > 0 && key1.equals("!=")
                    && previousKey.equals(key1)) {
                  //if you're doing multiple != terms in a row, what you really want is an AND not an OR
                  newTerm = processElementHeader(sqlFilter, newTerm, 0);
                } else {
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, termsProcessed);
                }
              }

              if (termsProcessed == 0) {
                sqlFilter.append("(");
              }

              sqlFilter.append(" ("
                  + DatabaseUtils.toLowerCase(db)
                  + "(c.namelast) " + key1 + " '" + key2
                  + "' )");
              previousKey = key1;
              processElementType(db, sqlFilter, elementType);
              processSite(sqlFilter, elementType, site);
              termsProcessed++;
            }
          }
        }

        if (termsProcessed > 0) {
          sqlFilter.append(") ");
        }
      }

      // Entered Dates
      if (dateHash != null && dateHash.size() > 0) {
        Iterator outer = dateHash.keySet().iterator();
        termsProcessed = 0;
        String previousKey = null;

        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) dateHash.get(key1);

          Iterator inner = innerHash.keySet().iterator();

          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash
                .get(key2)).toString();
            int elementType = processType(elementTypeString);
            String site = processSite(elementTypeString);

            if (elementType == y
                && (key1.equals("<") || key1.equals(">")
                || key1.equals("<=") || key1
                .equals(">="))) {

              if (termsProcessed > 0
                  && !(previousKey.equals(key1))) {
                // we want to get an 'AND' term if we have
                // switched between operators here
                // for example, enteredDate less than x AND
                // enteredDate greater than y
                newTerm = processElementHeader(sqlFilter,
                    newTerm, 0);
              } else {
                newTerm = processElementHeader(sqlFilter,
                    newTerm, termsProcessed);
              }

              if (termsProcessed == 0) {
                sqlFilter.append("(");
              }

              sqlFilter.append(" (c.entered " + key1 + " '"
                  + key2 + "') ");

              previousKey = key1;
              processElementType(db, sqlFilter, elementType);
              processSite(sqlFilter, elementType, site);
              termsProcessed++;
            }
          }
        }

        if (termsProcessed > 0) {
          sqlFilter.append(")");
        }
      }

      // zip codes
      if (zipHash != null && zipHash.size() > 0) {
        Iterator outer = zipHash.keySet().iterator();

        termsProcessed = 0;
        String previousKey = null;

        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) zipHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();

          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash
                .get(key2)).toString();
            int elementType = processType(elementTypeString);
            String site = processSite(elementTypeString);

            // equals and != are the only operators supported right
            // now
            if (elementType == y
                && (key1.equals("=") || key1.equals("!="))) {
              if (termsProcessed > 0
                  && !(previousKey.equals(key1))) {
                newTerm = processElementHeader(sqlFilter,
                    newTerm, 0);
              } else {
                if (termsProcessed > 0 && key1.equals("!=")
                    && previousKey.equals(key1)) {
                  // if you're doing multiple != terms in a
                  // row, what you really want is an AND not
                  // an OR
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, 0);
                } else {
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, termsProcessed);
                }
              }

              if (termsProcessed == 0) {
                sqlFilter.append("(");
              }

              sqlFilter
                  .append(" (c.contact_id in (select distinct contact_id from contact_address where address_type = 1 AND "
                      + DatabaseUtils.toLowerCase(db)
                      + " (postalcode) "
                      + key1
                      + " '"
                      + key2 + "' )) ");
              previousKey = key1;
              processElementType(db, sqlFilter, elementType);
              processSite(sqlFilter, elementType, site);
              termsProcessed++;
            }
          }
        }
        if (termsProcessed > 0) {
          sqlFilter.append(")");
        }
      }

      // contact types
      if (typeIdHash != null && typeIdHash.size() > 0) {
        Iterator outer = typeIdHash.keySet().iterator();

        termsProcessed = 0;
        String previousKey = null;

        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) typeIdHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();

          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash
                .get(key2)).toString();
            int elementType = processType(elementTypeString);
            String site = processSite(elementTypeString);

            // equals and != are the only operators supported right
            // now
            if (elementType == y
                && (key1.equals("=") || key1.equals("!="))) {
              if (termsProcessed > 0
                  && !(previousKey.equals(key1))) {
                newTerm = processElementHeader(sqlFilter,
                    newTerm, 0);
              } else {
                if (termsProcessed > 0 && key1.equals("!=")
                    && previousKey.equals(key1)) {
                  // if you're doing multiple != terms in a
                  // row, what you really want is an AND not
                  // an OR
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, 0);
                } else {
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, termsProcessed);
                }
              }

              if (termsProcessed == 0) {
                sqlFilter.append("(");
              }

              sqlFilter
                  .append(" ( c.contact_id in (SELECT contact_id from contact_type_levels ctl where ctl.type_id "
                      + key1 + " '" + key2 + "' ) ) ");
              previousKey = key1;
              processElementType(db, sqlFilter, elementType);
              processSite(sqlFilter, elementType, site);
              termsProcessed++;
            }
          }
        }

        if (termsProcessed > 0) {
          sqlFilter.append(") ");
        }
      }

      // account types
      if (accountTypeIdHash != null && accountTypeIdHash.size() > 0) {
        Iterator outer = accountTypeIdHash.keySet().iterator();

        termsProcessed = 0;
        String previousKey = null;

        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) accountTypeIdHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();

          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash
                .get(key2)).toString();
            int elementType = processType(elementTypeString);
            String site = processSite(elementTypeString);

            // equals and != are the only operators supported right
            // now
            if (elementType == y
                && (key1.equals("=") || key1.equals("!="))) {
              if (termsProcessed > 0
                  && !(previousKey.equals(key1))) {
                newTerm = processElementHeader(sqlFilter,
                    newTerm, 0);
              } else {
                if (termsProcessed > 0 && key1.equals("!=")
                    && previousKey.equals(key1)) {
                  // if you're doing multiple != terms in a
                  // row, what you really want is an AND not
                  // an OR
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, 0);
                } else {
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, termsProcessed);
                }
              }

              if (termsProcessed == 0) {
                sqlFilter.append("(");
              }

              sqlFilter
                  .append(" (c.org_id in (SELECT org_id FROM account_type_levels WHERE type_id "
                      + key1 + " " + key2 + ")) ");
              previousKey = key1;
              processElementType(db, sqlFilter, elementType);
              processSite(sqlFilter, elementType, site);
              termsProcessed++;
            }
          }
        }

        if (termsProcessed > 0) {
          sqlFilter.append(") ");
        }
      }

      if (siteIdHash != null && siteIdHash.size() > 0) {
        Iterator outer = siteIdHash.keySet().iterator();

        termsProcessed = 0;
        String previousKey = null;

        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) siteIdHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();

          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash
                .get(key2)).toString();
            int elementType = processType(elementTypeString);
            // equals and != are the only operators supported right
            // now
            // if (Integer.parseInt(key2) != -1){
            if (elementType == y
                && (key1.equals("=") || key1.equals("!="))) {
              if (termsProcessed > 0
                  && !(previousKey.equals(key1))) {
                newTerm = processElementHeader(sqlFilter,
                    newTerm, 0);
              } else {
                if (termsProcessed > 0 && key1.equals("!=")
                    && previousKey.equals(key1)) {
                  // if you're doing multiple != terms in a
                  // row, what you really want is an AND not
                  // an OR
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, 0);
                } else {
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, termsProcessed);
                }
              }

              if (termsProcessed == 0) {
                sqlFilter.append("(");
              }

              if (Integer.parseInt(key2) != -1) {
                System.out.println("key1: " + key1);
                System.out.println("key2: " + key2);
                sqlFilter
                    .append(" EXISTS (SELECT c.contact_id FROM contact ic WHERE c.site_id "
                        + key1 + " " + key2 + " ) ");
              } else {
                System.out.println("none");
                sqlFilter
                    .append(" EXISTS (SELECT c.contact_id FROM contact ic WHERE (c.site_id IS NULL OR c.site_id IS NOT NULL) ) ");
              }

              processElementType(db, sqlFilter, elementType);
              previousKey = key1;
              termsProcessed++;
            }
          }
        }

        if (termsProcessed > 0) {
          sqlFilter.append(") ");
        }
      }

      // area codes
      if (areaCodeHash != null && areaCodeHash.size() > 0) {
        Iterator outer = areaCodeHash.keySet().iterator();
        termsProcessed = 0;
        String previousKey = null;

        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) areaCodeHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();

          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash
                .get(key2)).toString();
            int elementType = processType(elementTypeString);
            String site = processSite(elementTypeString);

            // equals and != are the only operators supported right
            // now
            if (elementType == y
                && (key1.equals("=") || key1.equals("!="))) {
              if (termsProcessed > 0
                  && !(previousKey.equals(key1))) {
                newTerm = processElementHeader(sqlFilter,
                    newTerm, 0);
              } else {
                if (termsProcessed > 0 && key1.equals("!=")
                    && previousKey.equals(key1)) {
                  // if you're doing multiple != terms in a
                  // row, what you really want is an AND not
                  // an OR
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, 0);
                } else {
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, termsProcessed);
                }
              }

              if (termsProcessed == 0) {
                sqlFilter.append("(");
              }

              sqlFilter
                  .append(" (c.contact_id in (select distinct contact_id from contact_phone where phone_type = 1 and "
                      + DatabaseUtils.getSubString(db, ""
                      + DatabaseUtils.addQuotes(
                      db, "number") + "",
                      2, 3)
                      + " "
                      + key1
                      + " '"
                      + key2 + "' )) ");
              previousKey = key1;
              processElementType(db, sqlFilter, elementType);
              processSite(sqlFilter, elementType, site);
              termsProcessed++;
            }
          }
        }
        if (termsProcessed > 0) {
          sqlFilter.append(")");
        }
      }

      // cities
      if (cityHash != null && cityHash.size() > 0) {
        Iterator outer = cityHash.keySet().iterator();
        termsProcessed = 0;
        String previousKey = null;

        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) cityHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();

          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash
                .get(key2)).toString();
            int elementType = processType(elementTypeString);
            String site = processSite(elementTypeString);

            // equals and != are the only operators supported right
            // now
            if (elementType == y
                && (key1.equals("=") || key1.equals("!="))) {
              if (termsProcessed > 0
                  && !(previousKey.equals(key1))) {
                newTerm = processElementHeader(sqlFilter,
                    newTerm, 0);
              } else {
                if (termsProcessed > 0 && key1.equals("!=")
                    && previousKey.equals(key1)) {
                  // if you're doing multiple != terms in a
                  // row, what you really want is an AND not
                  // an OR
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, 0);
                } else {
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, termsProcessed);
                }
              }

              if (termsProcessed == 0) {
                sqlFilter.append("(");
              }

              sqlFilter
                  .append(" (c.contact_id IN (SELECT distinct contact_id FROM contact_address WHERE address_type = 1 AND "
                      + DatabaseUtils.toLowerCase(db)
                      + "(city) "
                      + key1
                      + " '"
                      + key2
                      + "' )) ");
              previousKey = key1;
              processElementType(db, sqlFilter, elementType);
              processSite(sqlFilter, elementType, site);
              termsProcessed++;
            }
          }
        }

        if (termsProcessed > 0) {
          sqlFilter.append(")");
        }
      }
      // import
      if (importNameHash != null && importNameHash.size() > 0) {
        Iterator outer = importNameHash.keySet().iterator();
        termsProcessed = 0;
        String previousKey = null;

        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) importNameHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();

          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash
                .get(key2)).toString();
            int elementType = processType(elementTypeString);
            String site = processSite(elementTypeString);

            // equals and != are the only operators supported right
            // now
            if (elementType == y
                && (key1.equals("=") || key1.equals("!="))) {
              if (termsProcessed > 0
                  && !(previousKey.equals(key1))) {
                newTerm = processElementHeader(sqlFilter,
                    newTerm, 0);
              } else {
                if (termsProcessed > 0 && key1.equals("!=")
                    && previousKey.equals(key1)) {
                  // if you're doing multiple != terms in a
                  // row, what you really want is an AND not
                  // an OR
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, 0);
                } else {
                  newTerm = processElementHeader(sqlFilter,
                      newTerm, termsProcessed);
                }
              }

              if (termsProcessed == 0) {
                sqlFilter.append("(");
              }
              sqlFilter
                  .append(" (c.import_id IN (SELECT distinct  I.import_id FROM import I WHERE I.name "
                      
                      + key1 + " '" + key2 + "' )) ");
              previousKey = key1;
              processElementType(db, sqlFilter, elementType);
              processSite(sqlFilter, elementType, site);
              termsProcessed++;
            }
          }
        }
        if (termsProcessed > 0) {
          sqlFilter.append(")");
        }
      }

      if (!newTerm) {
        sqlFilter.append(") ");
      }
    }
    
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND c.entered > ? ");
      }
      sqlFilter.append("AND c.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND c.modified > ? ");
      sqlFilter.append("AND c.entered < ? ");
      sqlFilter.append("AND c.modified < ? ");
    }    
  }

  /**
   * Sets the parameters for the preparedStatement - these items must
   * correspond with the createFilter statement
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   * @since 1.3
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    pst.setInt(++i, id);

    pst.setBoolean(++i, true);
    pst.setBoolean(++i, true);

    if (contactUserId != -1) {
      pst.setInt(++i, contactUserId);
    }
    if (orgId != -1) {
      pst.setInt(++i, orgId);
    }

    if (includeEnabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (includeEnabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }

    if (owner != -1 && !ownerOrReader) {
      pst.setInt(++i, owner);
      pst.setBoolean(++i, true);
    }

    if (typeId != -1) {
      pst.setInt(++i, typeId);
    }

    if (departmentId > 0) {
      pst.setInt(++i, departmentId);
    }

    if (ruleId != -1) {
      pst.setInt(++i, ruleId);
    }

    if (projectId != -1) {
      pst.setInt(++i, projectId);
    }

    if (firstName != null) {
      pst.setString(++i, firstName.toLowerCase());
    }

    if (middleName != null) {
      pst.setString(++i, middleName.toLowerCase());
    }

    if (lastName != null) {
      pst.setString(++i, lastName.toLowerCase());
    }

    if (accountName != null) {
      pst.setString(++i, accountName.toLowerCase());
    }

    if (accountNumber != null) {
      pst.setString(++i, accountNumber.toLowerCase());
    }

    if (accountTypeId != -1) {
      pst.setInt(++i, accountTypeId);
    }

    if (accountOwnerId != -1) {
      pst.setInt(++i, accountOwnerId);
    }

    if (includeEnabledAccount == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (includeEnabledAccount == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }

    if (assetSerialNumber != null) {
      pst.setString(++i, assetSerialNumber.toLowerCase());
    }

    if (accountSegment != null) {
      pst.setString(++i, accountSegment.toLowerCase());
    }

    if (contactPhoneNumber != null) {
      pst.setString(++i, contactPhoneNumber.toLowerCase());
    }

    if (contactState != null && !"-1".equals(contactState)) {
      pst.setString(++i, contactState.toLowerCase());
    }

    if (accountPostalCode != null) {
      pst.setString(++i, accountPostalCode.toLowerCase());
    }

    if (contactCity != null) {
      pst.setString(++i, contactCity.toLowerCase());
    }

    if (title != null) {
      pst.setString(++i, title.toLowerCase());
    }

    if (company != null) {
      pst.setString(++i, company.toLowerCase());
    }

    if (orgSiteId != -1) {
      pst.setInt(++i, orgSiteId);
    }

    if (userSiteId != -1) {
      pst.setInt(++i, userSiteId);
    }

    if (importSiteId != -1) {
      pst.setInt(++i, importSiteId);
    }

    if (!includeAllSites && siteId != -1 && orgId == -1) {
      pst.setInt(++i, siteId);
    }

    if (withProjectsOnly && !includeAllSites && orgId == -1 && siteId != -1) {
      pst.setInt(++i, siteId);
    }

    if (includeEnabledUsersOnly) {
      if (userRoleType > -1) {
        pst.setBoolean(++i, true);
        pst.setInt(++i, userRoleType);
      } else {
        pst.setBoolean(++i, true);
      }
    }

    if (portalUsersOnly != Constants.UNDEFINED) {
      pst.setInt(++i, Constants.ROLETYPE_CUSTOMER);
    }

    if ((includeEnabledUsersOnly || includeUsersOnly) && permission != null
        && !"".equals(permission)) {
      String[] temp = permission.split(",");
      for (int j = 0; j < temp.length; j++) {
        String str = temp[j];
        pst.setString(++i, str.substring(0, str.lastIndexOf("-")));
        pst.setBoolean(++i, true);
      }
    }

    if (employeesOnly != Constants.UNDEFINED) {
      pst.setBoolean(++i, (employeesOnly == Constants.TRUE));
    }

    if (importId != -1) {
      pst.setInt(++i, importId);
    }

    if (statusId != -1) {
      pst.setInt(++i, statusId);
    }

    if (excludeUnapprovedContacts) {
      pst.setInt(++i, Import.PROCESSED_APPROVED);
    }
    if (!showTrashedAndNormal) {
      if (includeOnlyTrashed) {
        // do nothing
      } else if (trashedDate != null) {
        pst.setTimestamp(++i, trashedDate);
      } else {
        // do nothing
      }
    }

    if (leadsOnly != Constants.UNDEFINED) {
      pst.setBoolean(++i, (leadsOnly == Constants.TRUE));
    }
    if (leadStatus > 0 && employeesOnly == Constants.UNDEFINED) {
      if (leadStatus == Contact.LEAD_UNPROCESSED
          || leadStatus == Contact.LEAD_TRASHED
          || leadStatus == Contact.LEAD_ASSIGNED) {
        pst.setInt(++i, leadStatus);
      }
    } else if (leadsOnly == Constants.TRUE
        && leadStatus == Contact.LEAD_UNREAD && readBy == -1
        && !ownerOrReader && employeesOnly == Constants.UNDEFINED) {
      pst.setInt(++i, Contact.LEAD_UNPROCESSED);
      pst.setInt(++i, userId);
      pst.setInt(++i, userId);
    } else if (leadStatus == -1 && readBy == -1
        && employeesOnly == Constants.UNDEFINED
        && leadsOnly == Constants.TRUE) {
      pst.setInt(++i, Contact.LEAD_TRASHED);
      pst.setInt(++i, Contact.LEAD_ASSIGNED);
      pst.setInt(++i, Contact.LEAD_UNPROCESSED);
    }
    if (source > -1) {
      pst.setInt(++i, source);
    }
    if (stage > -1) {
      pst.setInt(++i, stage);
    }
    if (rating > -1) {
      pst.setInt(++i, rating);
    }
    if (industry > -1) {
      pst.setInt(++i, industry);
    }
    if (leadsOnly == Constants.TRUE && readBy > -1 && !ownerOrReader) {
      pst.setInt(++i, readBy);
      pst.setInt(++i, readBy);
    }

    if (enteredStart != null) {
      pst.setTimestamp(++i, enteredStart);
    }

    if (enteredEnd != null) {
      pst.setTimestamp(++i, enteredEnd);
    }

    if (conversionDateStart != null) {
      pst.setTimestamp(++i, conversionDateStart);
    }

    if (conversionDateEnd != null) {
      pst.setTimestamp(++i, conversionDateEnd);
    }

    if (emailAddress != null) {
      pst.setString(++i, emailAddress.toLowerCase());
    }

    if (postalCode != null) {
      pst.setString(++i, postalCode);
    }
    if (city != null) {
      pst.setString(++i, city.toLowerCase());
    }

    if (state != null && !"-1".equals(state)) {
      pst.setString(++i, state.toLowerCase());
    } else if (state1 != null) {
      pst.setString(++i, state1.toLowerCase());
    }

    if (country != null && !"-1".equals(country)) {
      pst.setString(++i, country);
    }

    if (ownerOrReader) {
      pst.setInt(++i, this.getReadBy());
      pst.setInt(++i, this.getOwner());
      pst.setInt(++i, this.getReadBy());
    }
    if (this.getHierarchialUsers() != -1) {
      Iterator iterator = (Iterator) users.iterator();
      while (iterator.hasNext()) {
        User thisUser = (User) iterator.next();
        pst.setInt(++i, thisUser.getId());
      }
    }

    if (allContacts) {
      pst.setInt(++i, AccessType.PUBLIC);
      pst.setBoolean(++i, true);
    }

    switch (personalId) {
      case IGNORE_PERSONAL:
        break;
      case EXCLUDE_PERSONAL:
        if (accessTypes == null) {
          pst.setInt(++i, AccessType.PERSONAL);
        }
        break;
      default:
        if (accessTypes == null) {
          pst.setInt(++i, AccessType.PERSONAL);
          pst.setInt(++i, AccessType.PERSONAL);
        }
        pst.setInt(++i, personalId);
        break;
    }

    if (searchText != null && !"".equals(searchText)) {
      pst.setString(++i, searchText.toLowerCase());
      pst.setString(++i, searchText.toLowerCase());
      pst.setString(++i, searchText.toLowerCase());
    }

    if (ignoreTypeIdList.size() > 0) {
      Iterator iterator = ignoreTypeIdList.iterator();
      while (iterator.hasNext()) {
        iterator.next();
        pst.setBoolean(++i, false);
      }
    }

    // loop on the types
    for (int y = 1; y < (SearchCriteriaList.CONTACT_SOURCE_ELEMENTS + 1); y++) {
      // company names
      if (companyHash != null && companyHash.size() > 0) {
        Iterator outer = companyHash.keySet().iterator();
        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) companyHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            //equals and != are the only operators supported right now
            if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
              i = processElementTypeParam(pst, i, elementType);
            }
          }
        }
      }

      // first names
      if (nameFirstHash != null && nameFirstHash.size() > 0) {
        Iterator outer = nameFirstHash.keySet().iterator();
        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) nameFirstHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            //equals and != are the only operators supported right now
            if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
              i = processElementTypeParam(pst, i, elementType);
            }
          }
        }
      }

      // last names
      if (nameLastHash != null && nameLastHash.size() > 0) {
        Iterator outer = nameLastHash.keySet().iterator();
        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) nameLastHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            // equals and != are the only operators supported right now
            if (elementType == y
                && (key1.equals("=") || key1.equals("!="))) {
              i = processElementTypeParam(pst, i, elementType);
            }
          }
        }
      }

      // Entered Dates
      if (dateHash != null && dateHash.size() > 0) {
        Iterator outer = dateHash.keySet().iterator();
        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) dateHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            if (elementType == y
                && (key1.equals("<") || key1.equals(">")
                || key1.equals("<=") || key1
                .equals(">="))) {
              i = processElementTypeParam(pst, i, elementType);
            }
          }
        }
      }

      // zip codes
      if (zipHash != null && zipHash.size() > 0) {
        Iterator outer = zipHash.keySet().iterator();
        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) zipHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            // equals and != are the only operators supported right now
            if (elementType == y
                && (key1.equals("=") || key1.equals("!="))) {
              i = processElementTypeParam(pst, i, elementType);
            }
          }
        }
      }

      // contact types
      if (typeIdHash != null && typeIdHash.size() > 0) {
        Iterator outer = typeIdHash.keySet().iterator();
        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) typeIdHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            //equals and != are the only operators supported right now
            if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
              i = processElementTypeParam(pst, i, elementType);
            }
          }
        }
      }

      // account types
      if (accountTypeIdHash != null && accountTypeIdHash.size() > 0) {
        Iterator outer = accountTypeIdHash.keySet().iterator();
        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) accountTypeIdHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            //equals and != are the only operators supported right now
            if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
              i = processElementTypeParam(pst, i, elementType);
            }
          }
        }
      }

      if (siteIdHash != null && siteIdHash.size() > 0) {
        Iterator outer = siteIdHash.keySet().iterator();
        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) siteIdHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            //equals and != are the only operators supported right now
            // if (Integer.parseInt(key2) != -1){
            if (elementType == y
                && (key1.equals("=") || key1.equals("!="))) {
              i = processElementTypeParam(pst, i, elementType);
            }
          }
        }
      }

      // area codes
      if (areaCodeHash != null && areaCodeHash.size() > 0) {
        Iterator outer = areaCodeHash.keySet().iterator();
        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) areaCodeHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            //equals and != are the only operators supported right now
            if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
              i = processElementTypeParam(pst, i, elementType);
            }
          }
        }
      }

      // cities
      if (cityHash != null && cityHash.size() > 0) {
        Iterator outer = cityHash.keySet().iterator();
        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) cityHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            //equals and != are the only operators supported right now
            if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
              i = processElementTypeParam(pst, i, elementType);
            }
          }
        }
      }
      // import
      if (importNameHash != null && importNameHash.size() > 0) {
        Iterator outer = importNameHash.keySet().iterator();
        while (outer.hasNext()) {
          String key1 = (String) outer.next();
          HashMap innerHash = (HashMap) importNameHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          while (inner.hasNext()) {
            String key2 = (String) inner.next();
            String elementTypeString = ((String) innerHash.get(key2)).toString();
            int elementType = processType(elementTypeString);
            //equals and != are the only operators supported right now
            if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
              i = processElementTypeParam(pst, i, elementType);
            }
          }
        }
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

    return i;
  }

  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param newOwner Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int reassignElements(Connection db, int newOwner)
      throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Contact thisContact = (Contact) i.next();
      if (thisContact.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }

  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param newOwner Description of the Parameter
   * @param userId   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int reassignElements(Connection db, int newOwner, int userId)
      throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Contact thisContact = (Contact) i.next();
      thisContact.setModifiedBy(userId);
      if (thisContact.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
  }

  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param type      Description of the Parameter
   * @param db        Description of the Parameter
   */
  private void processElementType(Connection db, StringBuffer sqlFilter,
                                  int type) {
    switch (type) {
      case SearchCriteriaList.SOURCE_MY_CONTACTS:
        sqlFilter.append("AND c.owner = ? ");
        sqlFilter.append("AND c.employee = ? ");
        break;
      case SearchCriteriaList.SOURCE_ALL_CONTACTS:
        if (this.getGeneralContactAccessTypes() != null) {
          sqlFilter.append("AND ((c.owner IN ("
              + sclOwnerIdRange
              + ") AND c.access_type = "
              + this.getGeneralContactAccessTypes().getCode(
              AccessType.CONTROLLED_HIERARCHY)
              + ") OR (c.access_type = "
              + this.getGeneralContactAccessTypes().getCode(
              AccessType.PUBLIC) + "))");
        } else {
          sqlFilter.append("AND c.owner IN (" + sclOwnerIdRange + ") ");
        }
        sqlFilter.append("AND c.employee = ? ");
        break;
      case SearchCriteriaList.SOURCE_ALL_ACCOUNTS:
        sqlFilter.append("AND c.org_id > 0 ");
        sqlFilter.append("AND c.employee = ? ");
        break;
      case SearchCriteriaList.SOURCE_EMPLOYEES:
        sqlFilter.append("AND c.employee = ? ");
        break;
      case SearchCriteriaList.SOURCE_LEADS:
        sqlFilter.append("AND c.employee = ? ");
        break;
      default:
        break;
    }
  }

  /**
   * Description of the Method
   *
   * @param pst  Description of the Parameter
   * @param i    Description of the Parameter
   * @param type Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int processElementTypeParam(PreparedStatement pst, int i, int type)
      throws SQLException {
    switch (type) {
      case SearchCriteriaList.SOURCE_MY_CONTACTS:
        pst.setInt(++i, sclOwnerId);
        pst.setBoolean(++i, false);
        break;
      case SearchCriteriaList.SOURCE_ALL_CONTACTS:
        pst.setBoolean(++i, false);
        break;
      case SearchCriteriaList.SOURCE_ALL_ACCOUNTS:
        pst.setBoolean(++i, false);
        break;
      case SearchCriteriaList.SOURCE_EMPLOYEES:
        pst.setBoolean(++i, true);
        break;
      case SearchCriteriaList.SOURCE_LEADS:
        pst.setBoolean(++i, false);
        break;
      default:
        break;
    }
    return i;
  }

  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param type      Description of the Parameter
   * @param site      Description of the Parameter
   */
  public void processSite(StringBuffer sqlFilter, int type, String site) {
    if (Integer.parseInt(site) != -1) {
      sqlFilter
          .append(" AND EXISTS (SELECT c.contact_id FROM contact ic WHERE c.site_id = "
              + site + " ) ");
    } else {
      sqlFilter
          .append(" AND EXISTS (SELECT c.contact_id FROM contact ic WHERE (c.site_id IS NULL OR c.site_id IS NOT NULL)) ");
    }
  }

  /**
   * Description of the Method
   *
   * @param sqlFilter      Description of the Parameter
   * @param newTerm        Description of the Parameter
   * @param termsProcessed Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean processElementHeader(StringBuffer sqlFilter,
                                      boolean newTerm, int termsProcessed) {
    if (firstCriteria && newTerm) {
      sqlFilter.append(" AND (");
      firstCriteria = false;
      newTerm = false;
    } else if (newTerm && !(firstCriteria)) {
      sqlFilter.append(" OR (");
      newTerm = false;
    } else if (termsProcessed > 0) {
      sqlFilter.append(" OR ");
    } else {
      sqlFilter.append(" AND ");
    }
    return newTerm;
  }

  /**
   * Description of the Method
   *
   * @param inputString Description of the Parameter
   * @return Description of the Return Value
   */
  private int processType(String inputString) {
    StringTokenizer st = new StringTokenizer(inputString, "[*|]");
    int type = -1;
    if (st.hasMoreTokens()) {
      type = Integer.parseInt((String) st.nextToken());
    }
    return type;
  }

  /**
   * Description of the Method
   *
   * @param inputString Description of the Parameter
   * @return Description of the Return Value
   */
  private String processSite(String inputString) {
    StringTokenizer st = new StringTokenizer(inputString, "[*|]");
    String site = "";
    if (st.hasMoreTokens()) {
      int type = Integer.parseInt((String) st.nextToken());
    }
    if (st.hasMoreTokens()) {
      site = (String) st.nextToken();
    }

    return site;
  }

  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param moduleId   Description of the Parameter
   * @param itemId     Description of the Parameter
   * @param tmpEnabled Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int moduleId,
                                        int itemId, boolean tmpEnabled) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT COUNT(*) as itemcount " + "FROM contact c "
        + "WHERE contact_id > 0 " + "AND c.enabled = ? "
        + (tmpEnabled ? "AND c.trashed_date IS NULL " : ""));
    if (moduleId == Constants.ACCOUNTS) {
      sql.append("AND c.org_id = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setBoolean(1, tmpEnabled);
    if (moduleId == Constants.ACCOUNTS) {
      pst.setInt(2, itemId);
    }
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      count = rs.getInt("itemcount");
    }
    rs.close();
    pst.close();
    return count;
  }

  /**
   * Updates the organization name of all contacts linked to this organization
   *
   * @param db      Description of the Parameter
   * @param thisOrg Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void updateOrgName(Connection db, Organization thisOrg)
      throws SQLException {
    PreparedStatement pst = db.prepareStatement("UPDATE contact "
        + "SET org_name = ? " + "WHERE org_id = ?");
    pst.setString(1, thisOrg.getName());
    pst.setInt(2, thisOrg.getOrgId());
    pst.executeUpdate();
    pst.close();
  }

  /**
   * Gets the contactFromId attribute of the ContactList object
   *
   * @param id Description of the Parameter
   * @return The contactFromId value
   */
  public Contact getContactFromId(int id) {
    Iterator iterator = this.iterator();
    while (iterator.hasNext()) {
      Contact contact = (Contact) iterator.next();
      if (contact.getUserId() == id) {
        return contact;
      }
    }
    return null;
  }

  /**
   * Gets the hashMapOfContacts attribute of the ContactList object
   *
   * @return The hashMapOfContacts value
   */
  public HashMap getHashMapOfContacts() {
    HashMap contactList = new HashMap();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Contact thisContact = (Contact) i.next();
      contactList.put(new Integer(thisContact.getId()), (thisContact
          .isTrashed() ? "<font color=\"red\">" : "")
          + Contact.getNameLastFirst(thisContact.getNameLast(),
          thisContact.getNameFirst())
          + (checkUserAccess ? (thisContact.hasAccount() ? " (*)"
          : "") : "")
          + (thisContact.isTrashed() ? "</font>" : ""));
    }
    return contactList;
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
  public boolean updateStatus(Connection db, ActionContext context,
                              boolean toTrash, int tmpUserId) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      Contact tmpContact = (Contact) itr.next();
      tmpContact.updateStatus(db, context, toTrash, tmpUserId);
    }
    return true;
  }

  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @param userId  Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int revertBackToLead(Connection db, ActionContext context, int userId)
      throws SQLException {
    int contactId = -1;
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      Contact tmpContact = (Contact) itr.next();
      contactId = tmpContact.getId();
      tmpContact.setOrgId(-1);
      tmpContact.setIsLead(true);
      tmpContact.setLeadStatus(Contact.LEAD_ASSIGNED);
      tmpContact.setCompany(tmpContact.getCompany());
      tmpContact.setConversionDate((java.sql.Timestamp) null);
      tmpContact.setCheckRevertingBackToLead(true);
      tmpContact.updateStatus(db, context, true, userId);
      tmpContact.update(db, true);
      if (!tmpContact.getEnabled()) {
        tmpContact.enable(db);
      }
    }
    return contactId;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildShortList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    // Need to build a base SQL statement for counting records
    sqlCount
        .append("SELECT COUNT(*) AS recordcount "
            + "FROM contact c "
            + "LEFT JOIN organization o ON (c.org_id = o.org_id) "
            + "LEFT JOIN lookup_department d ON (c.department = d.code) "
            + "LEFT JOIN lookup_industry lind ON (c.industry_temp_code = lind.code) "
            + "LEFT JOIN lookup_contact_source lcs ON (c.source = lcs.code) "
            + "LEFT JOIN lookup_contact_stage lcst ON (c.stage = lcst.code) "
            + "LEFT JOIN lookup_contact_rating lcr ON (c.rating = lcr.code) "
            + "LEFT JOIN contact_address ca ON (c.contact_id = ca.contact_id) "
            + "LEFT JOIN lookup_site_id lsi ON (c.site_id = lsi.code) "
            + "WHERE ");

    createFilter(db, sqlFilter);

    if (pagedListInfo != null) {
      // Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString()
          + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = DatabaseUtils.executeQuery(db, pst);
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      // Determine the offset, based on the filter, for the first record
      // to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString()
            + sqlFilter.toString() + "AND "
            + DatabaseUtils.toLowerCase(db) + "(c.namelast) < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter()
            .toLowerCase());
        rs = DatabaseUtils.executeQuery(db, pst);
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      // Determine column to sort by
      if (this.getZipCodeAscPotentialDesc()) {
        if (this.oldestFirst == Constants.TRUE
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.entered, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.FALSE
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.entered DESC, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.UNDEFINED
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.namelast, c.namefirst, c.org_name");
        }
      } else {
        if (this.oldestFirst == Constants.TRUE
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("c.entered, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.FALSE
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("c.entered DESC, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.UNDEFINED
            && (pagedListInfo.getColumnToSortBy() == null || ""
            .equals(pagedListInfo.getColumnToSortBy()))) {
          pagedListInfo
              .setColumnToSortBy("c.namelast, c.namefirst, c.org_name");
        }
      }
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      if (this.getZipCodeAscPotentialDesc()) {
        if (this.oldestFirst == Constants.TRUE) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.entered, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.FALSE) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.entered DESC, c.namelast, c.namefirst, c.org_name");
        } else if (this.oldestFirst == Constants.UNDEFINED) {
          pagedListInfo
              .setColumnToSortBy("ca.postalcode asc, c.potential desc, c.namelast, c.namefirst, c.org_name");
        }
      } else {
        if (this.oldestFirst == Constants.TRUE) {
          sqlOrder
              .append("ORDER BY c.entered, c.namelast, c.namefirst, c.org_name ");
        } else if (this.oldestFirst == Constants.FALSE) {
          sqlOrder
              .append("ORDER BY c.entered DESC, c.namelast, c.namefirst, c.org_name ");
        } else if (this.oldestFirst == Constants.UNDEFINED) {
          sqlOrder
              .append("ORDER BY c.namelast, c.namefirst, c.org_name ");
        }
      }
    }

    // Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    // c.user_id, c.contact_id, c.namelast, c.namefirst, o.name, c.owner,
    // c.status_id, c.entered,
    sqlSelect
        .append("c.user_id, c.contact_id, c.namelast, c.namefirst, c.company, o.name, c.owner, c.status_id, c.entered, c.lead, c.lead_status, c.org_id, c.site_id "
            + "FROM contact c "
            + "LEFT JOIN organization o ON (c.org_id = o.org_id) "
            + "LEFT JOIN lookup_department d ON (c.department = d.code) "
            + "LEFT JOIN lookup_industry lind ON (c.industry_temp_code = lind.code) "
            + "LEFT JOIN lookup_contact_source lcs ON (c.source = lcs.code) "
            + "LEFT JOIN lookup_contact_stage lcst ON (c.stage = lcst.code) "
            + "LEFT JOIN lookup_contact_rating lcr ON (c.rating = lcr.code) "
            + "LEFT JOIN contact_address ca ON (c.contact_id = ca.contact_id) "
            + "LEFT JOIN lookup_site_id lsi ON (c.site_id = lsi.code) "
            + "WHERE ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString()
        + sqlOrder.toString());
    items = prepareFilter(pst);
		rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
		boolean foundDefaultContact = false;
		while (rs.next()) {
			Contact thisContact = new Contact();
			thisContact.buildShortRecord(rs);
			if (thisContact.getId() == defaultContactId) {
				foundDefaultContact = true;
			}
			this.addElement(thisContact);
		}
		rs.close();
		pst.close();
		if (defaultContactId != -1 && !foundDefaultContact) {
			Contact thisContact = new Contact(db, defaultContactId);
			this.addElement(thisContact);
		}
		buildResources(db);

	}
}