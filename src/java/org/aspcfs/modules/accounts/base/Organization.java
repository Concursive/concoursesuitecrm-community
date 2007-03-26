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
package org.aspcfs.modules.accounts.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.utils.ProjectUtils;
import com.zeroio.webdav.utils.ICalendar;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.RecipientList;
import org.aspcfs.modules.contacts.base.CallList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.mycfs.base.OrganizationEventList;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TimeZone;

/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 *      Exp $
 */
public class Organization extends GenericBean {

  private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.Organization.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  
  protected double YTD = 0;

  private String errorMessage = "";
  private int orgId = -1;
  private String name = "";
  private String url = "";
  private String lastModified = "";
  private String notes = "";
  private int industry = 0;
  private int accountSize = -1;
  private boolean directBill = false;
  private String accountSizeName = null;
  private int listSalutation = -1;
  private int segmentList = -1;
  private int siteId = -1;
  private int stageId = -1;
  private String stageName = null; 
  private String siteClient = null;
  public int segmentId = -1;
  private int subSegmentId = -1;
  private String industryName = null;
  private boolean minerOnly = false;
  private int enteredBy = -1;

  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp contractEndDate = null;

  private java.sql.Timestamp alertDate = null;
  private String alertText = "";

  private int modifiedBy = -1;
  private boolean enabled = true;
  private int employees = 0;
  private double revenue = 0;
  private String ticker = "";
  private String accountNumber = "";
  private int owner = -1;
  private int duplicateId = -1;
  private int importId = -1;
  private int statusId = -1;
  private Timestamp trashedDate = null;
  private String alertDateTimeZone = null;
  private String contractEndDateTimeZone = null;
  private int source = -1;
  private int rating = -1;
  private double potential = 0;
  private int primaryContactId = -1;
  private String city = null;
  private String state = null;
  private String postalCode = null;
  private String county = null;

  private OrganizationAddressList addressList = new OrganizationAddressList();
  private OrganizationPhoneNumberList phoneNumberList = new OrganizationPhoneNumberList();
  private OrganizationEmailAddressList emailAddressList = new OrganizationEmailAddressList();
  private String ownerName = "";
  private String enteredByName = "";
  private String modifiedByName = "";

  private LookupList types = new LookupList();
  private ArrayList typeList = null;

  private boolean contactDelete = false;
  private boolean revenueDelete = false;
  private boolean documentDelete = false;

  //By default, insert a primary contact record if contact information is available.
  //Account contact import disables this automatic insertion when importing
  private boolean insertPrimaryContact = true;

  private boolean hasEnabledOwnerAccount = true;

  //contact as account stuff
  private String nameSalutation = null;
  private String nameFirst = null;
  private String nameMiddle = null;
  private String nameLast = null;
  private String nameSuffix = null;

  private Contact primaryContact = null;
  private boolean hasOpportunities = false;
  private boolean hasPortalUsers = false;
  private boolean isIndividual = false;

  private boolean forceDelete = false;

  private String dunsType = null;
  private String dunsNumber = null;
  private String businessNameTwo = null;
  private int sicCode = -1;
  private int yearStarted = -1;
  private String sicDescription = null;
  
  /**
   *  Constructor for the Organization object, creates an empty Organization
   *
   * @since    1.0
   */
  public Organization() { }


  /**
   *  Constructor for the Organization object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Organization(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @param  org_id            Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Organization(Connection db, int org_id) throws SQLException {
    if (org_id == -1) {
      throw new SQLException("Invalid Account");
    } 
    PreparedStatement pst = db.prepareStatement(
        "SELECT o.*, " +
        "ct_owner.namelast AS o_namelast, ct_owner.namefirst AS o_namefirst, " +
        "ct_eb.namelast AS eb_namelast, ct_eb.namefirst AS eb_namefirst, " +
        "ct_mb.namelast AS mb_namelast, ct_mb.namefirst AS mb_namefirst, " +
        "i.description AS industry_name, a.description AS account_size_name, " +
        "oa.city as o_city, oa.state as o_state, oa.postalcode as o_postalcode, oa.county as o_county, " +
        "ast.description as stage_name "+
        "FROM organization o " +
        "LEFT JOIN contact ct_owner ON (o.owner = ct_owner.user_id) " +
        "LEFT JOIN contact ct_eb ON (o.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (o.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN lookup_industry i ON (o.industry_temp_code = i.code) " +
        "LEFT JOIN lookup_account_size a ON (o.account_size = a.code) " +
        "LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) " +
        "LEFT JOIN lookup_account_stage ast ON (o.stage_id = ast.code) " +
        "WHERE o.org_id = ? " +
        " AND (oa.address_id IS NULL OR oa.address_id IN ( "
		+ "SELECT ora.address_id FROM organization_address ora WHERE ora.org_id = o.org_id AND ora.primary_address = ?) "
		+ "OR oa.address_id IN (SELECT MIN(ctodd.address_id) FROM organization_address ctodd WHERE ctodd.org_id = o.org_id AND "
		+ " ctodd.org_id NOT IN (SELECT org_id FROM organization_address WHERE organization_address.primary_address = ?)))");
    pst.setInt(1, org_id);
    pst.setBoolean(2, true);
    pst.setBoolean(3, true);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (orgId == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
    buildTypes(db);
    //if this is an individual account, populate the primary contact record
    if (this.getNameLast() != null) {
      this.populatePrimaryContact(db);
    }
    phoneNumberList.setOrgId(this.getOrgId());
    phoneNumberList.buildList(db);
    addressList.setOrgId(this.getOrgId());
    addressList.buildList(db);
    emailAddressList.setOrgId(this.getOrgId());
    emailAddressList.buildList(db);
  }


  /**
   *  Sets the typeList attribute of the Organization object
   *
   * @param  typeList  The new typeList value
   */
  public void setTypeList(ArrayList typeList) {
    this.typeList = typeList;
  }


  /**
   *  Sets the typeList attribute of the Organization object
   *
   * @param  criteriaString  The new typeList value
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
   *  Sets the ContactDelete attribute of the Organization object
   *
   * @param  tmp  The new ContactDelete value
   */
  public void setContactDelete(boolean tmp) {
    this.contactDelete = tmp;
  }


  /**
   *  Gets the insertPrimaryContact attribute of the Organization object
   *
   * @return    The insertPrimaryContact value
   */
  public boolean getInsertPrimaryContact() {
    return insertPrimaryContact;
  }


  /**
   *  Sets the insertPrimaryContact attribute of the Organization object
   *
   * @param  tmp  The new insertPrimaryContact value
   */
  public void setInsertPrimaryContact(boolean tmp) {
    this.insertPrimaryContact = tmp;
  }


  /**
   *  Sets the insertPrimaryContact attribute of the Organization object
   *
   * @param  tmp  The new insertPrimaryContact value
   */
  public void setInsertPrimaryContact(String tmp) {
    this.insertPrimaryContact = DatabaseUtils.parseBoolean(tmp);
  }
  /**
   *  Gets the approved attribute of the Contact object
   *
   * @return    The approved value
   */
  public boolean isApproved() {
    return (statusId == Import.PROCESSED_UNAPPROVED ? false : true);
  }

  /**
   *  Sets the RevenueDelete attribute of the Organization object
   *
   * @param  tmp  The new RevenueDelete value
   */
  public void setRevenueDelete(boolean tmp) {
    this.revenueDelete = tmp;
  }


  /**
   *  Sets the DocumentDelete attribute of the Organization object
   *
   * @param  tmp  The new DocumentDelete value
   */
  public void setDocumentDelete(boolean tmp) {
    this.documentDelete = tmp;
  }


  /**
   *  Sets the EnteredByName attribute of the Organization object
   *
   * @param  enteredByName  The new EnteredByName value
   */
  public void setEnteredByName(String enteredByName) {
    this.enteredByName = enteredByName;
  }


  /**
   *  Sets the ModifiedByName attribute of the Organization object
   *
   * @param  modifiedByName  The new ModifiedByName value
   */
  public void setModifiedByName(String modifiedByName) {
    this.modifiedByName = modifiedByName;
  }


  /**
   *  Sets the hasEnabledOwnerAccount attribute of the Organization object
   *
   * @param  hasEnabledOwnerAccount  The new hasEnabledOwnerAccount value
   */
  public void setHasEnabledOwnerAccount(boolean hasEnabledOwnerAccount) {
    this.hasEnabledOwnerAccount = hasEnabledOwnerAccount;
  }


  /**
   *  Sets the trashedDate attribute of the Organization object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the Organization object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the statusId attribute of the Organization object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the Organization object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the alertDateTimeZone attribute of the Organization object
   *
   * @param  tmp  The new alertDateTimeZone value
   */
  public void setAlertDateTimeZone(String tmp) {
    this.alertDateTimeZone = tmp;
  }


  /**
   *  Sets the contractEndDateTimeZone attribute of the Organization object
   *
   * @param  tmp  The new contractEndDateTimeZone value
   */
  public void setContractEndDateTimeZone(String tmp) {
    this.contractEndDateTimeZone = tmp;
  }


  /**
   *  Sets the source attribute of the Organization object
   *
   * @param  tmp  The new source value
   */
  public void setSource(int tmp) {
    this.source = tmp;
  }


  /**
   *  Sets the source attribute of the Organization object
   *
   * @param  tmp  The new source value
   */
  public void setSource(String tmp) {
    this.source = Integer.parseInt(tmp);
  }


  /**
   *  Sets the rating attribute of the Organization object
   *
   * @param  tmp  The new rating value
   */
  public void setRating(int tmp) {
    this.rating = tmp;
  }


  /**
   *  Sets the rating attribute of the Organization object
   *
   * @param  tmp  The new rating value
   */
  public void setRating(String tmp) {
    this.rating = Integer.parseInt(tmp);
  }


  /**
   *  Sets the potential attribute of the Organization object
   *
   * @param  tmp  The new potential value
   */
  public void setPotential(double tmp) {
    this.potential = tmp;
  }

  /**
   *  Sets the potential attribute of the Organization object
   *
   * @param  tmp  The new potential value
   */
  public void setPotential(String tmp) {
    this.potential = Double.parseDouble(tmp);
  }

  /**
   *  Gets the alertDateTimeZone attribute of the Organization object
   *
   * @return    The alertDateTimeZone value
   */
  public String getAlertDateTimeZone() {
    return alertDateTimeZone;
  }


  /**
   *  Gets the contractEndDateTimeZone attribute of the Organization object
   *
   * @return    The contractEndDateTimeZone value
   */
  public String getContractEndDateTimeZone() {
    return contractEndDateTimeZone;
  }


  /**
   *  Gets the source attribute of the Organization object
   *
   * @return    The source value
   */
  public int getSource() {
    return source;
  }


  /**
   *  Gets the rating attribute of the Organization object
   *
   * @return    The rating value
   */
  public int getRating() {
    return rating;
  }


  /**
   *  Gets the potential attribute of the Organization object
   *
   * @return    The potential value
   */
  public double getPotential() {
    return potential;
  }


  /**
   *  Gets the trashedDate attribute of the Organization object
   *
   * @return    The trashedDate value
   */
  public Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Gets the statusId attribute of the Organization object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the hasEnabledOwnerAccount attribute of the Organization object
   *
   * @return    The hasEnabledOwnerAccount value
   */
  public boolean getHasEnabledOwnerAccount() {
    return hasEnabledOwnerAccount;
  }


  /**
   *  Sets the OwnerName attribute of the Organization object
   *
   * @param  ownerName  The new OwnerName value
   */
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
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
   *  Sets the ErrorMessage attribute of the Organization object
   *
   * @param  tmp  The new ErrorMessage value
   */
  public void setErrorMessage(String tmp) {
    this.errorMessage = tmp;
  }


  /**
   *  Sets the types attribute of the Organization object
   *
   * @param  types  The new types value
   */
  public void setTypes(LookupList types) {
    this.types = types;
  }


  /**
   *  Sets the Owner attribute of the Organization object
   *
   * @param  owner  The new Owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the importId attribute of the Organization object
   *
   * @param  tmp  The new importId value
   */
  public void setImportId(int tmp) {
    this.importId = tmp;
  }


  /**
   *  Sets the importId attribute of the Organization object
   *
   * @param  tmp  The new importId value
   */
  public void setImportId(String tmp) {
    this.importId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the importId attribute of the Organization object
   *
   * @return    The importId value
   */
  public int getImportId() {
    return importId;
  }


  /**
   *  Gets the primaryContact attribute of the Organization object
   *
   * @return    The primaryContact value
   */
  public Contact getPrimaryContact() {
    return primaryContact;
  }


  /**
   *  Sets the primaryContact attribute of the Organization object
   *
   * @param  primaryContact  The new primaryContact value
   */
  public void setPrimaryContact(Contact primaryContact) {
    this.primaryContact = primaryContact;
  }


  /**
   *  Sets the OwnerId attribute of the Organization object
   *
   * @param  owner  The new OwnerId value
   */
  public void setOwnerId(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the Entered attribute of the Organization object
   *
   * @param  tmp  The new Entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the alertDate attribute of the Organization object
   *
   * @param  tmp  The new alertDate value
   */
  public void setAlertDate(java.sql.Timestamp tmp) {
    this.alertDate = tmp;
  }


  /**
   *  Sets the alertText attribute of the Organization object
   *
   * @param  tmp  The new alertText value
   */
  public void setAlertText(String tmp) {
    this.alertText = tmp;
  }


  /**
   *  Sets the Modified attribute of the Organization object
   *
   * @param  tmp  The new Modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Organization object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Organization object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the Owner attribute of the Organization object
   *
   * @param  owner  The new Owner value
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the OwnerId attribute of the Organization object
   *
   * @param  owner  The new OwnerId value
   */
  public void setOwnerId(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the ContractEndDate attribute of the Organization object
   *
   * @param  contractEndDate  The new ContractEndDate value
   */
  public void setContractEndDate(java.sql.Timestamp contractEndDate) {
    this.contractEndDate = contractEndDate;
  }


  /**
   *  Sets the YTD attribute of the Organization object
   *
   * @param  YTD  The new YTD value
   */
  public void setYTD(double YTD) {
    this.YTD = YTD;
  }


  /**
   *  Sets the ContractEndDate attribute of the Organization object
   *
   * @param  tmp  The new ContractEndDate value
   */
  public void setContractEndDate(String tmp) {
    this.contractEndDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the alertDate attribute of the Organization object
   *
   * @param  tmp  The new alertDate value
   */
  public void setAlertDate(String tmp) {
    this.alertDate = DatabaseUtils.parseDateToTimestamp(tmp);
  }


  /**
   *  Sets the Employees attribute of the Organization object
   *
   * @param  employees  The new Employees value
   */
  public void setEmployees(String employees) {
    try {
      this.employees = Integer.parseInt(employees);
    } catch (Exception e) {
      this.employees = 0;
    }
  }


  /**
   *  Sets the DuplicateId attribute of the Organization object
   *
   * @param  duplicateId  The new DuplicateId value
   */
  public void setDuplicateId(int duplicateId) {
    this.duplicateId = duplicateId;
  }

  /**
   *  Sets the DuplicateId attribute of the Organization object
   *
   * @param  duplicateId  The new DuplicateId value
   */
  public void setDuplicateId(String duplicateId) {
    this.duplicateId = Integer.parseInt(duplicateId);
  }

  /**
   *  Sets the orgId attribute of the Organization object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
    addressList.setOrgId(tmp);
    phoneNumberList.setOrgId(tmp);
    emailAddressList.setOrgId(tmp);
  }


  /**
   *  Sets the ModifiedBy attribute of the Organization object
   *
   * @param  modifiedBy  The new ModifiedBy value
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   *  Sets the ModifiedBy attribute of the Organization object
   *
   * @param  modifiedBy  The new ModifiedBy value
   */
  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = Integer.parseInt(modifiedBy);
  }


  /**
   *  Sets the AccountNumber attribute of the Organization obA  9
   *
   * @param  accountNumber  The new AccountNumber value
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   *  Sets the OrgId attribute of the Organization object
   *
   * @param  tmp  The new OrgId value
   */
  public void setId(String tmp) {
    this.setOrgId(Integer.parseInt(tmp));
  }
  
  /**
   *  Sets the OrgId attribute of the Organization object
   *
   * @param  tmp  The new OrgId value
   */
  public void setOrgId(String tmp) {
    this.setOrgId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the Revenue attribute of the Organization object
   *
   * @param  revenue  The new Revenue value
   */
  public void setRevenue(String revenue) {
    this.revenue = Double.parseDouble(revenue);
  }


  /**
   *  Sets the revenue attribute of the Organization object
   *
   * @param  tmp  The new revenue value
   */
  public void setRevenue(double tmp) {
    this.revenue = tmp;
  }


  /**
   *  Sets the Name attribute of the Organization object
   *
   * @param  tmp  The new Name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the Url attribute of the Organization object
   *
   * @param  tmp  The new Url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the Ticker attribute of the Organization object
   *
   * @param  ticker  The new Ticker value
   */
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }


  /**
   *  Sets the LastModified attribute of the Organization object
   *
   * @param  tmp  The new LastModified value
   */
  public void setLastModified(String tmp) {
    this.lastModified = tmp;
  }


  /**
   *  Sets the Notes attribute of the Organization object
   *
   * @param  tmp  The new Notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the Industry attribute of the Organization object
   *
   * @param  tmp  The new Industry value
   */
  public void setIndustry(String tmp) {
    this.industry = Integer.parseInt(tmp);
  }


  /**
   *  Sets the account size attribute of the Organization object
   *
   * @param  tmp  The new account size value
   */
  public void setAccountSize(String tmp) {
    this.accountSize = Integer.parseInt(tmp);
  }


  /**
   *  Sets the listSalutation attribute of the Organization object
   *
   * @param  tmp  The new listSalutation value
   */
  public void setListSalutation(int tmp) {
    this.listSalutation = tmp;
  }


  /**
   *  Sets the listSalutation attribute of the Organization object
   *
   * @param  tmp  The new listSalutation value
   */
  public void setListSalutation(String tmp) {
    this.listSalutation = Integer.parseInt(tmp);
  }


  /**
   *  Sets the segmentList attribute of the Organization object
   *
   * @param  segmentList  The new segmentList value
   */
  public void setSegmentList(int segmentList) {
    this.segmentList = segmentList;
  }


  /**
   *  Sets the siteId attribute of the Organization object
   *
   * @param  siteId  The new siteId value
   */
  public void setSiteId(int siteId) {
    this.siteId = siteId;
  }


  /**
   *  Sets the siteId attribute of the Organization object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }
  
  /**
   *  Gets the siteId attribute of the Organization object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }
  
  /**
   *  Sets the stageId attribute of the Organization object
   *
   * @param  stageId  The new siteId value
   */
  public void setStageId(int stageId) {
    this.stageId = stageId;
  }


  /**
   *  Sets the stageId attribute of the Organization object
   *
   * @param  tmp  The new stageId value
   */
  public void setStageId(String tmp) {
    this.stageId = Integer.parseInt(tmp);
  }

  /**
   *  Gets the stageId attribute of the Organization object
   *
   * @return    The stageId value
   */
  
  public int getStageId() {
    return stageId;
  }  

  /**
   *  Gets the stageName attribute of the Organization object
   *
   * @return    The stageName value
   */
  
  public String getStageName() {
    return stageName;
  }

  /**
   *  Sets the segmentId attribute of the Organization object
   *
   * @param  tmp  The new segmentId value
   */
  public void setSegmentList(String tmp) {
    this.segmentList = Integer.parseInt(tmp);
  }


  /**
   *  Sets the siteClient attribute of the Organization object
   *
   * @param  siteClient  The new siteClient value
   */
  public void setSiteClient(String siteClient) {
    this.siteClient = siteClient;
  }


  /**
   *  Gets the siteClient attribute of the Organization object
   *
   * @return    The siteClient value
   */
  public String getSiteClient() {
    return siteClient;
  }


  /**
   *  Sets the segmentId attribute of the Organization object
   *
   * @param  segmentId  The new segmentId value
   */
  public void setSegmentId(int segmentId) {
    this.segmentId = segmentId;
  }


  /**
   *  Gets the segmentId attribute of the Organization object
   *
   * @return    The segmentId value
   */
  public int getSegmentId() {
    return segmentId;
  }


  /**
   *  Sets the segmentId attribute of the Organization object
   *
   * @param  tmp  The new segmentId value
   */
  public void setSegmentId(String tmp) {
    this.segmentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the subSegmentId attribute of the Organization object
   *
   * @param  tmp  The new subSegmentId value
   */
  public void setSubSegmentId(String tmp) {
    this.subSegmentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the subSegmentId attribute of the Organization object
   *
   * @param  SubSegmentId  The new subSegmentId value
   */
  public void setSubSegmentId(int SubSegmentId) {
    this.subSegmentId = SubSegmentId;
  }


  /**
   *  Gets the subSegmentId attribute of the Organization object
   *
   * @return    The subSegmentId value
   */
  public int getSubSegmentId() {
    return subSegmentId;
  }


  /**
   *  Gets the segmentList attribute of the Organization object
   *
   * @return    The segmentList value
   */
  public int getSegmentList() {
    return segmentList;
  }


  /**
   *  Gets the listSalutation attribute of the Organization object
   *
   * @return    The listSalutation value
   */
  public int getListSalutation() {
    return listSalutation;
  }


  /**
   *  Sets the industry attribute of the Organization object
   *
   * @param  tmp  The new industry value
   */
  public void setIndustry(int tmp) {
    industry = tmp;
  }


  /**
   *  Sets the directBill attribute of the Organization object
   *
   * @param  tmp  The new directBill value
   */
  public void setDirectBill(boolean tmp) {
    this.directBill = tmp;
  }


  /**
   *  Sets the directBill attribute of the Organization object
   *
   * @param  tmp  The new directBill value
   */
  public void setDirectBill(String tmp) {
    this.directBill = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the Miner_only attribute of the Organization object
   *
   * @param  tmp  The new Miner_only value
   */
  public void setMiner_only(String tmp) {
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(
        tmp));
  }


  /**
   *  Sets the MinerOnly attribute of the Organization object
   *
   * @param  tmp  The new MinerOnly value
   */
  public void setMinerOnly(boolean tmp) {
    this.minerOnly = tmp;
  }


  /**
   *  Sets the MinerOnly attribute of the Organization object
   *
   * @param  tmp  The new MinerOnly value
   */
  public void setMinerOnly(String tmp) {
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(
        tmp));
  }


  /**
   *  Sets the AddressList attribute of the Organization object
   *
   * @param  tmp  The new AddressList value
   */
  public void setAddressList(OrganizationAddressList tmp) {
    this.addressList = tmp;
  }


  /**
   *  Sets the PhoneNumberList attribute of the Organization object
   *
   * @param  tmp  The new PhoneNumberList value
   */
  public void setPhoneNumberList(OrganizationPhoneNumberList tmp) {
    this.phoneNumberList = tmp;
  }


  /**
   *  Sets the EmailAddressList attribute of the Organization object
   *
   * @param  tmp  The new EmailAddressList value
   */
  public void setEmailAddressList(OrganizationEmailAddressList tmp) {
    this.emailAddressList = tmp;
  }


  /**
   *  Sets the Enteredby attribute of the Organization object
   *
   * @param  tmp  The new Enteredby value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the Organization object
   *
   * @param  tmp  The new EnteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Enabled attribute of the Organization object
   *
   * @param  tmp  The new Enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Enabled attribute of the Organization object
   *
   * @param  tmp  The new Enabled value
   */
  public void setEnabled(String tmp) {
    enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Since dynamic fields cannot be auto-populated, passing the request to this
   *  method will populate the indicated fields.
   *
   * @param  context  The new requestItems value
   * @since           1.15
   */
  public void setRequestItems(ActionContext context) {
    phoneNumberList = new OrganizationPhoneNumberList(context);
    addressList = new OrganizationAddressList(context.getRequest());
    emailAddressList = new OrganizationEmailAddressList(context.getRequest());
  }


  /**
   *  Gets the ContactDelete attribute of the Organization object
   *
   * @return    The ContactDelete value
   */
  public boolean getContactDelete() {
    return contactDelete;
  }


  /**
   *  Gets the RevenueDelete attribute of the Organization object
   *
   * @return    The RevenueDelete value
   */
  public boolean getRevenueDelete() {
    return revenueDelete;
  }


  /**
   *  Gets the hasOpportunities attribute of the Organization object
   *
   * @return    The hasOpportunities value
   */
  public boolean getHasOpportunities() {
    return hasOpportunities;
  }


  /**
   *  Sets the hasOpportunities attribute of the Organization object
   *
   * @param  hasOpportunities  The new hasOpportunities value
   */
  public void setHasOpportunities(boolean hasOpportunities) {
    this.hasOpportunities = hasOpportunities;
  }


  /**
   *  Sets the hasPortalUsers attribute of the Organization object
   *
   * @param  tmp  The new hasPortalUsers value
   */
  public void setHasPortalUsers(boolean tmp) {
    this.hasPortalUsers = tmp;
  }


  /**
   *  Gets the hasPortalUsers attribute of the Organization object
   *
   * @return    The hasPortalUsers value
   */
  public boolean getHasPortalUsers() {
    return hasPortalUsers;
  }


  /**
   *  Gets the DocumentDelete attribute of the Organization object
   *
   * @return    The DocumentDelete value
   */
  public boolean getDocumentDelete() {
    return documentDelete;
  }


  /**
   *  Gets the YTD attribute of the Organization object
   *
   * @return    The YTD value
   */
  public double getYTD() {
    return YTD;
  }


  /**
   *  Gets the YTDValue attribute of the Organization object
   *
   * @return    The YTDValue value
   */
  public String getYTDValue() {
    double value_2dp = (double) Math.round(YTD * 100.0) / 100.0;
    String toReturn = String.valueOf(value_2dp);
    if (toReturn.endsWith(".0")) {
      toReturn = toReturn.substring(0, toReturn.length() - 2);
    }

    if (Integer.parseInt(toReturn) == 0) {
      toReturn = "";
    }

    return toReturn;
  }


  /**
   *  Gets the Enabled attribute of the Organization object
   *
   * @return    The Enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the typeList attribute of the Organization object
   *
   * @return    The typeList value
   */
  public ArrayList getTypeList() {
    return typeList;
  }


  /**
   *  Gets the types attribute of the Organization object
   *
   * @return    The types value
   */
  public LookupList getTypes() {
    return types;
  }


  /**
   *  Gets the alertDate attribute of the Organization object
   *
   * @return    The alertDate value
   */
  public java.sql.Timestamp getAlertDate() {
    return alertDate;
  }


  /**
   *  Gets the alertText attribute of the Organization object
   *
   * @return    The alertText value
   */
  public String getAlertText() {
    return alertText;
  }


  /**
   *  Gets the EnteredBy attribute of the Organization object
   *
   * @return    The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the Entered attribute of the Organization object
   *
   * @return    The Entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the Modified attribute of the Organization object
   *
   * @return    The Modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the ModifiedString attribute of the Organization object
   *
   * @return    The ModifiedString value
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
   *  Gets the EnteredString attribute of the Organization object
   *
   * @return    The EnteredString value
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
   *  Gets the nameSalutation attribute of the Organization object
   *
   * @return    The nameSalutation value
   */
  public String getNameSalutation() {
    return nameSalutation;
  }


  /**
   *  Gets the nameFirst attribute of the Organization object
   *
   * @return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Gets the nameMiddle attribute of the Organization object
   *
   * @return    The nameMiddle value
   */
  public String getNameMiddle() {
    return nameMiddle;
  }


  /**
   *  Gets the nameLast attribute of the Organization object
   *
   * @return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Gets the nameSuffix attribute of the Organization object
   *
   * @return    The nameSuffix value
   */
  public String getNameSuffix() {
    return nameSuffix;
  }


  /**
   *  Sets the nameSalutation attribute of the Organization object
   *
   * @param  tmp  The new nameSalutation value
   */
  public void setNameSalutation(String tmp) {
    this.nameSalutation = tmp;
  }


  /**
   *  Sets the nameFirst attribute of the Organization object
   *
   * @param  tmp  The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Sets the nameMiddle attribute of the Organization object
   *
   * @param  tmp  The new nameMiddle value
   */
  public void setNameMiddle(String tmp) {
    this.nameMiddle = tmp;
  }


  /**
   *  Sets the nameLast attribute of the Organization object
   *
   * @param  tmp  The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   *  Sets the nameSuffix attribute of the Organization object
   *
   * @param  tmp  The new nameSuffix value
   */
  public void setNameSuffix(String tmp) {
    this.nameSuffix = tmp;
  }


  /**
   *  Gets the ContractEndDate attribute of the Organization object
   *
   * @return    The ContractEndDate value
   */
  public java.sql.Timestamp getContractEndDate() {
    return contractEndDate;
  }


  /**
   *  Gets the ContractEndDateString attribute of the Organization object
   *
   * @return    The ContractEndDateString value
   */
  public String getContractEndDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(contractEndDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the contractEndDateBufferedString attribute of the Organization
   *  object
   *
   * @return    The contractEndDateBufferedString value
   */
  public String getContractEndDateBufferedString() {
    String tmp = "None";
    try {
      return DateFormat.getDateInstance(3).format(contractEndDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the asValuesArray attribute of the Organization object
   *
   * @return    The asValuesArray value
   */
  public String[] getAsValuesArray() {
    String[] temp = new String[5];
    temp[0] = this.getName();
    temp[1] = this.getUrl();
    temp[2] = this.getOwnerName();
    temp[3] = this.getEnteredByName();
    temp[4] = this.getModifiedByName();

    return temp;
  }


  /**
   *  Gets the alertDateString attribute of the Organization object
   *
   * @return    The alertDateString value
   */
  public String getAlertDateString() {
    String tmp = "";

    try {
      return DateFormat.getDateInstance(3).format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the alertDetails attribute of the Organization object
   *
   * @return    The alertDetails value
   */
  public String getAlertDetails() {
    StringBuffer out = new StringBuffer();

    if (alertDate == null) {
      return out.toString();
    } else {
      out.append(getAlertText() + " - " + getAlertDateString());
      return out.toString().trim();
    }
  }


  /**
   *  Gets the DuplicateId attribute of the Organization object
   *
   * @return    The DuplicateId value
   */
  public int getDuplicateId() {
    return duplicateId;
  }


  /**
   *  Gets the OwnerName attribute of the Organization object
   *
   * @return    The OwnerName value
   */
  public String getOwnerName() {
    return ownerName;
  }


  /**
   *  Gets the EnteredByName attribute of the Organization object
   *
   * @return    The EnteredByName value
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   *  Gets the ModifiedByName attribute of the Organization object
   *
   * @return    The ModifiedByName value
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   *  Gets the Owner attribute of the Organization object
   *
   * @return    The Owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the OwnerId attribute of the Organization object
   *
   * @return    The OwnerId value
   */
  public int getOwnerId() {
    return owner;
  }


  /**
   *  Gets the AccountNumber attribute of the Organization object
   *
   * @return    The AccountNumber value
   */
  public String getAccountNumber() {
    return accountNumber;
  }


  /**
   *  Gets the Ticker attribute of the Organization object
   *
   * @return    The Ticker value
   */
  public String getTicker() {
    return ticker;
  }


  /**
   *  Gets the Revenue attribute of the Organization object
   *
   * @return    The Revenue value
   */
  public double getRevenue() {
    return revenue;
  }


  /**
   *  Gets the Employees attribute of the Organization object
   *
   * @return    The Employees value
   */
  public int getEmployees() {
    return employees;
  }


  /**
   *  Gets the ErrorMessage attribute of the Organization object
   *
   * @return    The ErrorMessage value
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   *  Gets the orgId attribute of the Organization object
   *
   * @return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the id attribute of the Organization object
   *
   * @return    The id value
   */
  public int getId() {
    return orgId;
  }


  /**
   *  Gets the Name attribute of the Organization object
   *
   * @return    The Name value
   */
  public String getName() {
    if (name != null && name.trim().length() > 0) {
      return name;
    }
    return this.getNameLastFirstMiddle();
  }


  /**
   *  Gets the accountNameOnly attribute of the Organization object
   *
   * @return    The accountNameOnly value
   */
  public String getAccountNameOnly() {
    return name;
  }


  /**
   *  Gets the Url attribute of the Organization object
   *
   * @return    The Url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the urlString attribute of the Organization object when a url or link
   *  needs to be displayed
   *
   * @return    The urlString value
   */
  public String getUrlString() {
    if (url != null) {
      if (url.indexOf("://") == -1) {
        return "http://" + url;
      }
    }
    return url;
  }


  /**
   *  Gets the LastModified attribute of the Organization object
   *
   * @return    The LastModified value
   */
  public String getLastModified() {
    return lastModified;
  }


  /**
   *  Gets the Notes attribute of the Organization object
   *
   * @return    The Notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the Industry attribute of the Organization object
   *
   * @return    The Industry value
   */
  public int getIndustry() {
    return industry;
  }


  /**
   *  Gets the AccountSize attribute of the Organization object adding account
   *  size to the ad account form
   *
   * @return    The Account size value
   */
  public int getAccountSize() {
    return accountSize;
  }


  /**
   *  Gets the IndustryName attribute of the Organization object
   *
   * @return    The IndustryName value
   */
  public String getIndustryName() {
    return industryName;
  }


  /**
   *  Gets the AccountSize attribute of the Organization object
   *
   * @return    The IndustryName value
   */
  public String getAccountSizeName() {
    return accountSizeName;
  }


  /**
   *  Gets the phone number attribute of the Organization object
   *
   * @param  thisType  Description of Parameter
   * @return           The PhoneNumber value
   */
  public String getPhoneNumber(String thisType) {
    return phoneNumberList.getPhoneNumber(thisType);
  }


  /**
   *  Gets the EmailAddress attribute of the Organization object
   *
   * @param  thisType  Description of Parameter
   * @return           The EmailAddress value
   */
  public String getEmailAddress(String thisType) {
    return emailAddressList.getEmailAddress(thisType);
  }


  /**
   *  Gets the Address attribute of the Organization object
   *
   * @param  thisType  Description of Parameter
   * @return           The Address value
   */
  public Address getAddress(String thisType) {
    return getAddressList().getAddress(thisType);
  }


  /**
   *  Gets the primaryEmailAddress attribute of the Organization object
   *
   * @return    The primaryEmailAddress value
   */
  public String getPrimaryEmailAddress() {
    return emailAddressList.getPrimaryEmailAddress();
  }


  /**
   *  Gets the primaryPhoneNumber attribute of the Organization object
   *
   * @return    The primaryPhoneNumber value
   */
  public String getPrimaryPhoneNumber() {
    return phoneNumberList.getPrimaryPhoneNumber();
  }


  /**
   *  Gets the primaryAddress attribute of the Organization object
   *
   * @return    The primaryAddress value
   */
  public Address getPrimaryAddress() {
    return getAddressList().getPrimaryAddress();
  }


  /**
   *  Gets the Enteredby attribute of the Organization object
   *
   * @return    The Enteredby value
   */
  public int getEnteredby() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the Organization object
   *
   * @return    The ModifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the Miner_only attribute of the Organization object
   *
   * @return    The Miner_only value
   */
  public boolean getMiner_only() {
    return minerOnly;
  }


  /**
   *  Gets the MinerOnly attribute of the Organization object
   *
   * @return    The MinerOnly value
   */
  public boolean getMinerOnly() {
    return minerOnly;
  }


  /**
   *  Gets the DirectBill attribute of the Organization object
   *
   * @return    The DirectBill value
   */
  public boolean getDirectBill() {
    return directBill;
  }


  /**
   *  Sets the forceDelete attribute of the Organization object
   *
   * @param  tmp  The new forceDelete value
   */
  public void setForceDelete(boolean tmp) {
    this.forceDelete = tmp;
  }


  /**
   *  Sets the forceDelete attribute of the Organization object
   *
   * @param  tmp  The new forceDelete value
   */
  public void setForceDelete(String tmp) {
    this.forceDelete = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the forceDelete attribute of the Organization object
   *
   * @return    The forceDelete value
   */
  public boolean getForceDelete() {
    return forceDelete;
  }


  /**
   *  Gets the primaryContactId attribute of the Organization object
   *
   * @return    The primaryContactId value
   */
  public int getPrimaryContactId() {
    return primaryContactId;
  }


  /**
   *  Sets the primaryContactId attribute of the Organization object
   *
   * @param  tmp  The new primaryContactId value
   */
  public void setPrimaryContactId(int tmp) {
    this.primaryContactId = tmp;
  }

  /**
   * Gets the city attribute of the Organization object
   *
   * @return The city value
   */
  public String getCity() {
    return city;
  }


  /**
   * Sets the city attribute of the Organization object
   *
   * @param tmp The new city value
   */
  public void setCity(String tmp) {
    this.city = tmp;
  }
 
  /**
   * Sets the state attribute of the Organization object
   *
   * @param tmp The new state value
   */
	 public void setState(String tmp) {
	 	this.state = tmp;
	 }


	/**
   * Sets the postalCode attribute of the Organization object
   *
   * @param tmp The new postalCode value
   */
	 public void setPostalCode(String tmp) {
	 	this.postalCode = tmp;
	 }
	 
	 
  /**
   * Gets the postalCode attribute of the Organization object
   *
   * @return The postalCode value
   */
	 public String getPostalCode() {
	 	return postalCode;
	 }


  /**
   * Sets the county attribute of the Organization object
   *
   * @param tmp The new county value
   */
	 public void setCounty(String tmp) {
	 	this.county = tmp;
	 }


  /**
   * Gets the state attribute of the Organization object
   *
   * @return The state value
   */
	 public String getState() {
	 	return state;
	 }


  /**
   * Gets the county attribute of the Organization object
   *
   * @return The county value
   */
	 public String getCounty() {
	 	return county;
	 }

  /**
   *  Sets the primaryContactId attribute of the Organization object
   *
   * @param  tmp  The new primaryContactId value
   */
  public void setPrimaryContactId(String tmp) {
    this.primaryContactId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the trashed attribute of the Organization object
   *
   * @return    The trashed value
   */
  public boolean isTrashed() {
    return (trashedDate != null);
  }


  /**
   *  Gets the AddressList attribute of the Organization object
   *
   * @return    The AddressList value
   */
  public OrganizationAddressList getAddressList() {
    return addressList;
  }


  /**
   *  Gets the PhoneNumberList attribute of the Organization object
   *
   * @return    The PhoneNumberList value
   */
  public OrganizationPhoneNumberList getPhoneNumberList() {
    return phoneNumberList;
  }


  /**
   *  Gets the EmailAddressList attribute of the Organization object
   *
   * @return    The EmailAddressList value
   */
  public OrganizationEmailAddressList getEmailAddressList() {
    return emailAddressList;
  }


  /**
   *  Gets the isIndividual attribute of the Organization object
   *
   * @return    The isIndividual value
   */
  public boolean getIsIndividual() {
    return isIndividual;
  }


  /**
   *  Sets the isIndividual attribute of the Organization object
   *
   * @param  tmp  The new isIndividual value
   */
  public void setIsIndividual(boolean tmp) {
    this.isIndividual = tmp;
  }


  /**
   *  Sets the isIndividual attribute of the Organization object
   *
   * @param  tmp  The new isIndividual value
   */
  public void setIsIndividual(String tmp) {
    this.isIndividual = DatabaseUtils.parseBoolean(tmp);
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
   * @return the sicCode
   */
  public int getSicCode() {
    return sicCode;
  }

	public String getSicDescription() {
		return sicDescription;
	}

  /**
   * @return the yearStarted
   */
  public int getYearStarted() {
    return yearStarted;
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

	public void setSicDescription(String tmp) {
		this.sicDescription = tmp;
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
    this.yearStarted = Integer.parseInt(yearStarted);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    try {
      Dependency contactDependency = new Dependency();
      contactDependency.setName("contacts");
      contactDependency.setCount(
          ContactList.retrieveRecordCount(
          db, Constants.ACCOUNTS, this.getId(), true));
      contactDependency.setCanDelete(true);

      if (contactDependency.getCount() > 0) {
        ContactList contactList = new ContactList();
        contactList.setOrgId(this.orgId);
        contactList.setIncludeEnabledUsersOnly(true);
        contactList.buildList(db);
        Iterator itr = contactList.iterator();

        // For history, keep this contact if they previously received a comm. message
        while (itr.hasNext()) {
          Contact contact = (Contact) itr.next();
          if (RecipientList.retrieveRecordCount(
              db, Constants.CONTACTS, contact.getId()) > 0) {
            contactDependency.setCanDelete(false);
            break;
          }
        }

        //finding if any of the account contacts have user accounts
        itr = contactList.iterator();
        //Setting canDelete to false if an account contact has had portal access
        while (itr.hasNext()) {
          Contact contact = (Contact) itr.next();
          contact.checkUserAccount(db);
          if (contact.hasAccount()) {
            contactDependency.setCanDelete(false);
            this.setHasPortalUsers(true);
            break;
          }
        }
      }
      dependencyList.add(contactDependency);

      //Check if there exist any relationships
      Dependency relationshipDependency = new Dependency();
      relationshipDependency.setName("relationships");
      RelationshipList thisList = new RelationshipList();
      thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
      thisList.setObjectIdMapsFrom(this.getOrgId());
      thisList.setBuildDualMappings(true);
      thisList.buildList(db);
      relationshipDependency.setCount(thisList.getNumberOfRelationships());
      relationshipDependency.setCanDelete(true);
      dependencyList.add(relationshipDependency);

      Dependency revenueDependency = new Dependency();
      revenueDependency.setName("revenue");
      revenueDependency.setCount(
          RevenueList.retrieveRecordCount(
          db, Constants.ACCOUNTS, this.getId()));
      revenueDependency.setCanDelete(true);
      dependencyList.add(revenueDependency);

      int oppCount = OpportunityList.retrieveRecordCount(
          db, Constants.ACCOUNTS, this.getId());

      if (oppCount > 0) {
        this.setHasOpportunities(true);
      }

      Dependency oppDependency = new Dependency();
      oppDependency.setName("opportunities");
      oppDependency.setCount(oppCount);
      oppDependency.setCanDelete(false);
      dependencyList.add(oppDependency);

      Dependency scDependency = new Dependency();
      scDependency.setName("contracts");
      scDependency.setCount(
          ServiceContractList.retrieveRecordCount(
          db, Constants.ACCOUNTS, this.getId()));
      scDependency.setCanDelete(true);
      dependencyList.add(scDependency);

      Dependency assetDependency = new Dependency();
      assetDependency.setName("assets");
      assetDependency.setCount(
          AssetList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      assetDependency.setCanDelete(true);
      dependencyList.add(assetDependency);

      Dependency ticDependency = new Dependency();
      ticDependency.setName("tickets");
      ticDependency.setCount(
          TicketList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      ticDependency.setCanDelete(true);
      dependencyList.add(ticDependency);

      Dependency docDependency = new Dependency();
      docDependency.setName("documents");
      docDependency.setCount(
          FileItemList.retrieveRecordCount(
          db, Constants.ACCOUNTS, this.getId()));
      docDependency.setCanDelete(true);
      dependencyList.add(docDependency);

      Dependency quoteDependency = new Dependency();
      quoteDependency.setName("quotes");
      quoteDependency.setCount(
          QuoteList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      quoteDependency.setCanDelete(true);
      dependencyList.add(quoteDependency);

      Dependency folderDependency = new Dependency();
      folderDependency.setName("folders");
      folderDependency.setCount(
          CustomFieldRecordList.retrieveRecordCount(
          db, Constants.ACCOUNTS, this.getId()));
      folderDependency.setCanDelete(true);
      dependencyList.add(folderDependency);

      //Action Plan Work
      Dependency planWorkDependency = new Dependency();
      planWorkDependency.setName("actionplanworks");
      planWorkDependency.setCount(
          ActionPlanWorkList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      planWorkDependency.setCanDelete(true);
      dependencyList.add(planWorkDependency);
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  year           Description of Parameter
   * @param  type           Description of Parameter
   * @param  ownerId        Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void buildRevenueYTD(Connection db, int year, int type, int ownerId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT SUM(rv.amount) AS s " +
        "FROM revenue rv " +
        "WHERE rv.org_id = ? AND rv.year = ? AND rv.owner = ? ");
    if (type > 0) {
      sql.append("AND rv.type = ? ");
    }

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, orgId);
    pst.setInt(++i, year);
    pst.setInt(++i, ownerId);
    if (type > 0) {
      pst.setInt(++i, type);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setYTD(rs.getDouble("s"));
    }
    rs.close();
    pst.close();
  }


  /**
   *  sets the items in the type list to the the lookup list 'types'
   *  Organization object
   *
   * @param  db             The new typeListToTypes value
   * @throws  SQLException  Description of the Exception
   */
  public void setTypeListToTypes(Connection db) throws SQLException {
    Iterator itr = typeList.iterator();
    while (itr.hasNext()) {
      String tmpId = (String) itr.next();
      types.add(
          new LookupElement(
          db, Integer.parseInt(tmpId), "lookup_account_types"));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void buildTypes(Connection db) throws SQLException {
    ArrayList list = new ArrayList();
    PreparedStatement pst = db.prepareStatement(
        "SELECT atl.type_id " +
        "FROM account_type_levels atl " +
        "WHERE atl.org_id = ? " +
        "ORDER BY atl." + DatabaseUtils.addQuotes(db, "level") + " ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      list.add(new Integer(rs.getInt("type_id")));
    }
    rs.close();
    pst.close();
    Iterator li = list.iterator();
    while (li.hasNext()) {
      int thisTypeId = ((Integer) li.next()).intValue();
      types.add(
          new LookupElement(db, thisTypeId, "lookup_account_types"));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  type_id        Description of Parameter
   * @param  level          Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean insertType(Connection db, int type_id, int level) throws SQLException {
    if (orgId == -1) {
      throw new SQLException("No Organization ID Specified");
    }
    String sql =
        "INSERT INTO account_type_levels " +
        "(org_id, type_id, " + DatabaseUtils.addQuotes(db, "level") + ") " +
        "VALUES (?, ?, ?) ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, this.getOrgId());
    pst.setInt(++i, type_id);
    pst.setInt(++i, level);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean resetType(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM account_type_levels WHERE org_id = ? ");
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean disable(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE organization " +
        "SET enabled = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE org_id = ? ");

    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, false);
    pst.setInt(++i, orgId);

    if(this.getModified() != null){
      pst.setTimestamp(++i, this.getModified());
    }

    int resultCount = pst.executeUpdate();
    pst.close();

    if (resultCount == 1) {
      success = true;
    }

    return success;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean enable(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE organization " +
        "SET enabled = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE org_id = ? ");
    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, true);
    pst.setInt(++i, orgId);
    if(this.getModified() != null){
      pst.setTimestamp(++i, this.getModified());
    }
    int resultCount = pst.executeUpdate();
    pst.close();
    if (resultCount == 1) {
      success = true;
    }
    return success;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  checkName      Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean checkIfExists(Connection db, String checkName) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sqlSelect =
        "SELECT name, org_id " +
        "FROM organization " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(organization.name) = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setString(++i, checkName.toLowerCase());
    rs = pst.executeQuery();
    if (rs.next()) {
      this.setDuplicateId(rs.getInt("org_id"));
      rs.close();
      pst.close();
      return true;
    } else {
      rs.close();
      pst.close();
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  lookupName     Description of the Parameter
   * @param  importId       Description of the Parameter
   * @param  siteId         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int lookupAccount(Connection db, String lookupName, int importId, int siteId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int lookupId = -1;
    String sqlSelect =
        "SELECT org_id " +
        "FROM organization o " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(o.name) = ? " +
        "AND (o.status_id IS NULL OR o.status_id = ? OR (o.status_id = ? AND o.import_id = ?) ) " +
        "AND " + (siteId > -1 ? "o.site_id = ? " : "o.site_id IS NULL") + " " +
        "AND o.trashed_date IS NULL " +
        "AND o.enabled = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setString(++i, lookupName.toLowerCase());
    pst.setInt(++i, Import.PROCESSED_APPROVED);
    pst.setInt(++i, Import.PROCESSED_UNAPPROVED);
    pst.setInt(++i, importId);
    if (siteId > -1) {
      pst.setInt(++i, siteId);
    }
    pst.setBoolean(++i, true);
    rs = pst.executeQuery();
    if (rs.next()) {
      lookupId = rs.getInt("org_id");
    }
    rs.close();
    pst.close();
    return lookupId;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  first             Description of the Parameter
   * @param  middle            Description of the Parameter
   * @param  last              Description of the Parameter
   * @param  importId          Description of the Parameter
   * @param  siteId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static int lookupAccount(Connection db, String first, String middle, String last, int importId, int siteId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int lookupId = -1;
    String sqlSelect =
        "SELECT org_id " +
        "FROM organization o " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(o.namefirst) = ? " +
        "AND " + DatabaseUtils.toLowerCase(db) + "(o.namemiddle) = ? " +
        "AND " + DatabaseUtils.toLowerCase(db) + "(o.namelast) = ? " +
        "AND (o.status_id IS NULL OR o.status_id = ? OR (o.status_id = ? AND o.import_id = ?) ) " +
        "AND " + (siteId > -1 ? "o.site_id = ? " : "o.site_id IS NULL") + " " +
        "AND o.trashed_date IS NULL " +
        "AND o.enabled = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setString(++i, (first != null ? first.toLowerCase() : (String) null));
    pst.setString(++i, (middle != null ? middle.toLowerCase() : (String) null));
    pst.setString(++i, (last != null ? last.toLowerCase() : (String) null));
    pst.setInt(++i, Import.PROCESSED_APPROVED);
    pst.setInt(++i, Import.PROCESSED_UNAPPROVED);
    pst.setInt(++i, importId);
    if (siteId > -1) {
      pst.setInt(++i, siteId);
    }
    pst.setBoolean(++i, true);
    rs = pst.executeQuery();
    if (rs.next()) {
      lookupId = rs.getInt("org_id");
    }
    rs.close();
    pst.close();
    return lookupId;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  tmpOrgId       Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int getOrganizationSiteId(Connection db, int tmpOrgId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int organizationSiteId = -1;
    String sqlSelect =
        "SELECT site_id " +
        "FROM organization " +
        "WHERE org_id = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setInt(++i, tmpOrgId);
    rs = pst.executeQuery();
    if (rs.next()) {
      organizationSiteId = DatabaseUtils.getInt(rs, "site_id");
    }
    rs.close();
    pst.close();
    return organizationSiteId;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    boolean doCommit = false;
    try {
      modifiedBy = enteredBy;
      
      if (stageId==-1){
    	  LookupList stageList = new LookupList();
    	  stageList.tableName = "lookup_account_stage";
    	  stageList.setShowDisabledFlag(false);
    	  stageList.buildList(db);
    	  if (stageList.getFirstEnabledElement()>0){
    	   stageId = stageList.getFirstEnabledElement();
    	  }
      }
      
      if (doCommit = db.getAutoCommit()) {
        db.setAutoCommit(false);
      }
      orgId = DatabaseUtils.getNextSeq(db, "organization_org_id_seq");
      sql.append(
          "INSERT INTO organization (name, industry_temp_code, url, " +
          "miner_only, owner, duplicate_id, notes, employees, revenue, " +
          "ticker_symbol, account_number, namesalutation, namefirst, namelast, " +
          "namemiddle, trashed_date, segment_id,  direct_bill, account_size,  " +
          "sub_segment_id, site_id, source, rating, potential, " +
          "duns_type, duns_number, business_name_two, year_started, sic_code, sic_description, ");
      if (orgId > -1) {
        sql.append("org_id, ");
      }
      sql.append("entered, ");
      if (statusId > -1) {
        sql.append("status_id, ");
      }
      if (stageId > -1) {
          sql.append("stage_id, ");
        }
      if (importId > -1) {
        sql.append("import_id, ");
      }
      sql.append("modified, ");
      sql.append("enteredBy, modifiedBy) ");
      sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
      sql.append("?,?,?,?,?,?,");
      if (orgId > -1) {
        sql.append("?,");
      }
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      if (statusId > -1) {
        sql.append("?, ");
      }
      if (stageId > -1) {
          sql.append("?, ");
        }
      if (importId > -1) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setString(++i, this.getName());
      pst.setInt(++i, this.getIndustry());
      pst.setString(++i, this.getUrl());
      pst.setBoolean(++i, this.getMinerOnly());
      DatabaseUtils.setInt(pst, ++i, this.getOwner());
      pst.setInt(++i, this.getDuplicateId());
      pst.setString(++i, this.getNotes());
      DatabaseUtils.setInt(pst, ++i, this.getEmployees());
      pst.setDouble(++i, this.getRevenue());
      pst.setString(++i, this.getTicker());
      pst.setString(++i, this.getAccountNumber());
      pst.setString(++i, this.getNameSalutation());
      pst.setString(++i, this.getNameFirst());
      pst.setString(++i, this.getNameLast());
      pst.setString(++i, this.getNameMiddle());
      DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
      DatabaseUtils.setInt(pst, ++i, this.getSegmentId());
      pst.setBoolean(++i, this.getDirectBill());
      DatabaseUtils.setInt(pst, ++i, this.getAccountSize());
      DatabaseUtils.setInt(pst, ++i, this.getSubSegmentId());
      DatabaseUtils.setInt(pst, ++i, this.getSiteId());
      DatabaseUtils.setInt(pst, ++i, this.getSource());
      DatabaseUtils.setInt(pst, ++i, this.getRating());
      pst.setDouble(++i, this.getPotential());
      pst.setString(++i, this.getDunsType());
      pst.setString(++i, this.getDunsNumber());
      pst.setString(++i, this.getBusinessNameTwo());
      DatabaseUtils.setInt(pst, ++i, this.getYearStarted());
      DatabaseUtils.setInt(pst, ++i, this.getSicCode());
      pst.setString(++i, this.getSicDescription());
      if (orgId > -1) {
        pst.setInt(++i, orgId);
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (statusId > -1) {
        pst.setInt(++i, this.getStatusId());
      }
      if (stageId > -1) {
          pst.setInt(++i, this.getStageId());
        }
      if (importId > -1) {
        pst.setInt(++i, this.getImportId());
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();

      orgId = DatabaseUtils.getCurrVal(db, "organization_org_id_seq", orgId);

      if (insertPrimaryContact) {
        //Insert primary contact if account is an individual
        if (nameLast != null && !"".equals(nameLast)) {
          primaryContact.setOrgId(orgId);
          primaryContact.setOrgName(this.getName());
          boolean contactInserted = ObjectValidator.validate(
              null, db, primaryContact);
          if (contactInserted) {
            contactInserted = primaryContact.insert(db);
          }
          if (!contactInserted) {
            throw new SQLException("Contact could not be inserted");
          }
        }
      }
      //Insert the phone numbers if there are any
      Iterator iphone = phoneNumberList.iterator();
      while (iphone.hasNext()) {
        OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber) iphone.next();
        //thisPhoneNumber.insert(db, this.getOrgId(), this.getEnteredBy());
        thisPhoneNumber.process(
            db, orgId, this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the addresses if there are any
      Iterator iaddress = getAddressList().iterator();
      while (iaddress.hasNext()) {
        OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
        //thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
        thisAddress.process(
            db, orgId, this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the email addresses if there are any
      Iterator iemail = emailAddressList.iterator();
      while (iemail.hasNext()) {
        OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress) iemail.next();
        //thisEmailAddress.insert(db, this.getOrgId(), this.getEnteredBy());
        thisEmailAddress.process(
            db, orgId, this.getEnteredBy(), this.getModifiedBy());
      }
      this.update(db, true);
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
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
    boolean doCommit = false;
    try {
      if (doCommit = db.getAutoCommit()) {
        db.setAutoCommit(false);
      }
      i = this.update(db, false);
      //Process the phone numbers if there are any
      Iterator iphone = phoneNumberList.iterator();
      while (iphone.hasNext()) {
        OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber) iphone.next();
        thisPhoneNumber.process(
            db, this.getOrgId(), this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the addresses if there are any
      Iterator iaddress = getAddressList().iterator();
      while (iaddress.hasNext()) {
        OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
        thisAddress.process(
            db, this.getOrgId(), this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the email addresses if there are any
      Iterator iemail = emailAddressList.iterator();
      while (iemail.hasNext()) {
        OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress) iemail.next();
        thisEmailAddress.process(
            db, this.getOrgId(), this.getEnteredBy(), this.getModifiedBy());
      }
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return i;
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
    this.setOwner(newOwner);
    result = this.update(db);

    if (result == -1) {
      return false;
    }

    return true;
  }


  /**
   *  This function populates the primaryContact with information from the
   *  account The Contact addresses, etc. must be set using the context in the
   *  actual Account module.
   */

  public void populatePrimaryContact() {
    primaryContact = new Contact();
    primaryContact.setNameFirst(this.getNameFirst());
    primaryContact.setNameMiddle(this.getNameMiddle());
    primaryContact.setOrgId(this.getOrgId());
    primaryContact.setEnteredBy(this.getEnteredBy());
    primaryContact.setModifiedBy(this.getModifiedBy());
    primaryContact.setOwner(this.getOwner());
    primaryContact.setNameLast(this.getNameLast());
    primaryContact.setOrgName(this.getName());
    //designate this as a primary contact
    primaryContact.setPrimaryContact(true);
    primaryContact.setTitle(this.getAccountNumber());
    primaryContact.setListSalutation(this.getListSalutation());
  }


  /**
   *  Description of the Method
   */
  public void populateRegularContact() {
    primaryContact = new Contact();
    primaryContact.setNameFirst(this.getNameFirst());
    primaryContact.setNameMiddle(this.getNameMiddle());
    primaryContact.setOrgId(this.getOrgId());
    primaryContact.setEnteredBy(this.getEnteredBy());
    primaryContact.setModifiedBy(this.getModifiedBy());
    primaryContact.setOwner(this.getOwner());
    primaryContact.setNameLast(this.getNameLast());
    primaryContact.setOrgName(this.getName());
    //designate this as a primary contact
    primaryContact.setPrimaryContact(false);
    primaryContact.setTitle(this.getAccountNumber());
    primaryContact.setListSalutation(this.getListSalutation());
  }


  /**
   *  Description of the Method
   */
  public void updatePrimaryContact() {
    primaryContact.setNameFirst(this.getNameFirst());
    primaryContact.setNameLast(this.getNameLast());
    primaryContact.setNameMiddle(this.getNameMiddle());
    primaryContact.setModifiedBy(this.getModifiedBy());
    primaryContact.setOwner(this.getOwner());
    primaryContact.setOrgName(this.getName());
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void populatePrimaryContact(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT contact_id " +
        "FROM contact " +
        "WHERE org_id = ? AND primary_contact = ? ");
    pst.setInt(1, this.getOrgId());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    int tmpContactId = -1;
    if (rs.next()) {
      tmpContactId = rs.getInt("contact_id");
    }
    rs.close();
    pst.close();
    if (tmpContactId != -1) {
      primaryContact = new Contact();
      primaryContact.setBuildDetails(true);
      primaryContact.queryRecord(db, tmpContactId);
    }
  }


  /**
   *  Description of the Method
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
        "UPDATE organization " +
        "SET name = ?, industry_temp_code = ?, " +
        "url = ?, notes= ?, ");

    if (!override) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append(
        "modifiedby = ?, " +
        "employees = ?, revenue = ?, ticker_symbol = ?, account_number = ?, ");

    if (owner > -1) {
      sql.append("owner = ?, ");
    }

    sql.append(
        "duplicate_id = ?, contract_end = ?, contract_end_timezone = ?, " +
        "alertdate = ?, alertdate_timezone=?, alert = ?, namesalutation = ?, namefirst = ?, " +
        "namemiddle = ?, namelast = ?, trashed_date = ?, segment_id = ?, " +
        "direct_bill = ?, account_size = ?, site_id = ?, sub_segment_id = ?, " +
        "source = ?, rating = ?, potential = ?, " +
        "duns_type = ?, duns_number = ?, business_name_two = ?, year_started = ?, sic_code = ?, sic_description = ?, ");
    sql.append("stage_id = ? ");
    sql.append("WHERE org_id = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, name);
    pst.setInt(++i, industry);
    pst.setString(++i, url);
    pst.setString(++i, notes);
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setInt(pst, ++i, employees);
    pst.setDouble(++i, revenue);
    pst.setString(++i, ticker);
    pst.setString(++i, accountNumber);
    if (owner > -1) {
      pst.setInt(++i, this.getOwner());
    }
    pst.setInt(++i, this.getDuplicateId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getContractEndDate());
    pst.setString(++i, this.getContractEndDateTimeZone());
    DatabaseUtils.setTimestamp(pst, ++i, this.getAlertDate());
    pst.setString(++i, this.getAlertDateTimeZone());
    pst.setString(++i, alertText);
    pst.setString(++i, this.getNameSalutation());
    pst.setString(++i, nameFirst);
    pst.setString(++i, nameMiddle);
    pst.setString(++i, nameLast);
    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
    DatabaseUtils.setInt(pst, ++i, segmentId);
    pst.setBoolean(++i, directBill);
    DatabaseUtils.setInt(pst, ++i, accountSize);
    DatabaseUtils.setInt(pst, ++i, siteId);
    DatabaseUtils.setInt(pst, ++i, subSegmentId);
    DatabaseUtils.setInt(pst, ++i, this.getSource());
    DatabaseUtils.setInt(pst, ++i, this.getRating());
    pst.setDouble(++i, this.getPotential());
    pst.setString(++i, this.getDunsType());
    pst.setString(++i, this.getDunsNumber());
    pst.setString(++i, this.getBusinessNameTwo());
    DatabaseUtils.setInt(pst, ++i, this.getYearStarted());
    DatabaseUtils.setInt(pst, ++i, this.getSicCode());
    pst.setString(++i, this.getSicDescription());
    DatabaseUtils.setInt(pst, ++i, stageId);
    pst.setInt(++i, orgId);
    if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    resultCount = pst.executeUpdate();
    pst.close();

    // When an account name gets updated,
    // the stored org_name in contact needs to be updated
    pst = db.prepareStatement(
        "UPDATE contact " +
        "SET org_name = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE org_id = ? " +
        "AND org_name NOT LIKE ? ");
    pst.setString(1, name);
    pst.setInt(2, orgId);
    pst.setString(3, name);
    pst.executeUpdate();
    pst.close();

    //Remove all account types, add new list
    if (!this.getMinerOnly() && typeList != null) {
      resetType(db);
      int lvlcount = 0;
      for (int k = 0; k < typeList.size(); k++) {
        String val = (String) typeList.get(k);
        if (val != null && !(val.equals("")) && !(val.equals("-1"))) {
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
   *  Renames the organization running this database
   *
   * @param  db             Description of the Parameter
   * @param  newName        Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public static void renameMyCompany(Connection db, String newName) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE organization " +
        "SET name = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE org_id = 0 ");
    pst.setString(1, newName);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  baseFilePath   Description of Parameter
   * @param  context        Description of the Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean delete(Connection db, ActionContext context, String baseFilePath) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified.");
    }
    boolean commit = db.getAutoCommit();
    try { 
      if (commit) {
        db.setAutoCommit(false);
      }
 
      //build the relationship list(both from and to mappings)
      RelationshipList thisList = new RelationshipList();
      thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
      thisList.setObjectIdMapsFrom(this.getOrgId());
      thisList.setBuildDualMappings(true);
      thisList.buildList(db);
      thisList.delete(db);

      //Tickets have accounts, contacts, assets, service contracts related, so delete them first
      TicketList ticketList = new TicketList();
      ticketList.setOrgId(this.getOrgId());
      ticketList.buildList(db);
      ticketList.delete(db, baseFilePath);
      ticketList = null;

      ticketList = new TicketList();
      ticketList.setOrgId(this.getOrgId());
      ticketList.setIncludeOnlyTrashed(true);
      ticketList.buildList(db); 
      ticketList.delete(db, baseFilePath);
      ticketList = null;

      // Delete after tickets
      AssetList assetList = new AssetList();
      assetList.setOrgId(this.getOrgId());
      assetList.buildList(db);
      assetList.delete(db, baseFilePath);
      assetList = null;

      assetList = new AssetList();
      assetList.setOrgId(this.getOrgId());
      assetList.setIncludeOnlyTrashed(true);
      assetList.buildList(db);
      assetList.delete(db, baseFilePath);
      assetList = null;

      // Delete after tickets and after assets
      ServiceContractList scList = new ServiceContractList();
      scList.setOrgId(this.getOrgId());
      scList.buildList(db);
      scList.delete(db, baseFilePath);
      scList = null;

      scList = new ServiceContractList();
      scList.setOrgId(this.getOrgId());
      scList.setIncludeOnlyTrashed(true);
      scList.buildList(db);
      scList.delete(db, baseFilePath);
      scList = null;

      if (documentDelete) {
        FileItemList fileList = new FileItemList();
        fileList.setLinkModuleId(Constants.ACCOUNTS);
        fileList.setLinkItemId(this.getOrgId());
        fileList.buildList(db);
        fileList.delete(db, getFileLibraryPath(baseFilePath, "accounts"));
        fileList = null;
      }

      CustomFieldRecordList folderList = new CustomFieldRecordList();
      folderList.setLinkModuleId(Constants.ACCOUNTS);
      folderList.setLinkItemId(this.getOrgId());
      folderList.buildList(db);
      folderList.delete(db);
      folderList = null;

      if (revenueDelete) {
        RevenueList revenueList = new RevenueList();
        revenueList.setOrgId(this.getOrgId());
        revenueList.buildList(db);
        revenueList.delete(db);
        revenueList = null;
      }

      CallList callList = new CallList();
      callList.setOrgId(this.getOrgId());
      callList.buildList(db);
      callList.delete(db);
      callList = null;

      //Delete all the associated quotes
      QuoteList quotes = new QuoteList();
      quotes.setOrgId(this.getOrgId());
      quotes.setDeleteAllQuotes(true);
      quotes.buildList(db);
      quotes.delete(db);
      quotes = null;

      //Delete all the project associations
      ProjectUtils.removeAccounts(db, this.getOrgId());

      if (forceDelete) {
        // Delete opportunities associated with the organization
        OpportunityHeaderList opportunityList = new OpportunityHeaderList();
        opportunityList.setOrgId(this.getOrgId());
        opportunityList.buildList(db);
        opportunityList.delete(db, context, baseFilePath);

        opportunityList = new OpportunityHeaderList();
        opportunityList.setOrgId(this.getOrgId());
        opportunityList.setIncludeOnlyTrashed(true);
        opportunityList.buildList(db);
        opportunityList.delete(db, context, baseFilePath);
      }

      //Save for next to last since other records related to contacts
      if (contactDelete) {
        //delete enabled records
        ContactList contactList = new ContactList();
        contactList.setOrgId(this.getOrgId());
        contactList.buildList(db);
        contactList.delete(db, baseFilePath, forceDelete);
        contactList = null;

        //delete archived/disabled records
        contactList = new ContactList();
        contactList.setOrgId(this.getOrgId());
        contactList.setIncludeEnabled(Constants.FALSE);
        contactList.buildList(db);
        contactList.delete(db, baseFilePath, forceDelete);
        contactList = null;

        //delete records marked for trashing
        contactList = new ContactList();
        contactList.setOrgId(this.getOrgId());
        contactList.setIncludeOnlyTrashed(true);
        contactList.buildList(db);
        contactList.delete(db, baseFilePath, forceDelete);
        contactList = null;
        
        contactList = new ContactList();
        contactList.setImportId(this.getImportId());
        contactList.setOrgId(this.getOrgId());
        contactList.setIncludeAllSites(true);
        contactList.setExcludeUnapprovedContacts(false);
        contactList.buildList(db); 	
        contactList.delete(db, baseFilePath, forceDelete);
      }

      //delete related action plan records
      ActionPlanWorkList planWorkList = new ActionPlanWorkList();
      planWorkList.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
      planWorkList.setSiteId(this.getSiteId());
      planWorkList.setLinkItemId(this.getOrgId());
      planWorkList.buildList(db);
      planWorkList.delete(db);

      OrganizationHistoryList history = new OrganizationHistoryList();
      history.setOrgId(this.getOrgId());
      history.buildList(db);
      history.delete(db);

      this.resetType(db);

      Statement st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM organization_phone WHERE org_id = " + this.getOrgId());
      st.executeUpdate(
          "DELETE FROM organization_emailaddress WHERE org_id = " + this.getOrgId());
      st.executeUpdate(
          "DELETE FROM organization_address WHERE org_id = " + this.getOrgId());
      st.executeUpdate(
          "DELETE FROM organization WHERE org_id = " + this.getOrgId());
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
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void deleteMinerOnly(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("The Organization could not be found.");
    }
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      
      Statement st = db.createStatement();
      st.executeUpdate("DELETE FROM news WHERE org_id = " + this.getOrgId());
      st.close();
      
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM organization " +
          "WHERE org_id = ? " +
          "AND miner_only = ? ");
      pst.setInt(1, this.getOrgId());
      pst.setBoolean(2, true);
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
  }


  /**
   *  Approves all records for a specific import
   *
   * @param  db             Description of the Parameter
   * @param  importId       Description of the Parameter
   * @param  status         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int updateImportStatus(Connection db, int importId, int status) throws SQLException {
    int count = 0;
    boolean commit = true;

    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      String sql = "UPDATE organization " +
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
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  toTrash        Description of the Parameter
   * @param  tmpUserId      Description of the Parameter
   * @param  context        Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
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
          "UPDATE organization " +
          "SET trashed_date = ?, " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
          "modifiedby = ? " +
          "WHERE org_id = ? ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (toTrash) {
        DatabaseUtils.setTimestamp(
            pst, ++i, new Timestamp(System.currentTimeMillis()));
      } else {
        DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
      }
      pst.setInt(++i, tmpUserId);
      pst.setInt(++i, this.getId());
      count = pst.executeUpdate();
      pst.close();

      OrganizationHistoryList historyList = new OrganizationHistoryList();
      historyList.setOrgId(this.getOrgId());
      historyList.setDefaultFilters(true);
      historyList.setShowDisabledWithEnabled(true);
      historyList.buildList(db);
      historyList.disableNotesInHistory(db, (!toTrash));

      //build the relationship list(both from and to mappings)
      RelationshipList thisList = new RelationshipList();
      thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
      thisList.setObjectIdMapsFrom(this.getOrgId());
      thisList.setBuildDualMappings(true);
      thisList.buildList(db);
      thisList.updateStatus(db, toTrash, tmpUserId);

      // mark opportunities of trashed and active contacts to trashed status
      ContactList contactList = new ContactList();
      contactList.setShowTrashedAndNormal(true);
      contactList.setOrgId(this.getId());
      contactList.buildList(db);
      Iterator iterator = (Iterator) contactList.iterator();
      while (iterator.hasNext()) {
        Contact contact = (Contact) iterator.next();
        OpportunityHeaderList oppList = new OpportunityHeaderList();
        if (!toTrash) {
          oppList.setIncludeOnlyTrashed(true);
        }
        oppList.setContactId(contact.getId());
        oppList.buildList(db);
        oppList.updateStatus(db, context, toTrash, tmpUserId);
      }

      contactList = new ContactList();
      contactList.setOrgId(this.getId());
      if (!toTrash) {
        contactList.setIncludeOnlyTrashed(true);
      }
      contactList.buildList(db);
      contactList.updateStatus(db, context, toTrash, tmpUserId);

      CallList callList = new CallList();
      callList.setOrgId(this.getOrgId());
      if (!toTrash) {
        callList.setIncludeOnlyTrashed(true);
      }
      callList.buildList(db);
      callList.updateStatus(db, toTrash, tmpUserId);

      QuoteList quoteList = new QuoteList();
      quoteList.setOrgId(this.getId());
      if (!toTrash) {
        quoteList.setIncludeOnlyTrashed(true);
      }
      quoteList.buildList(db);
      quoteList.updateStatus(db, toTrash, tmpUserId);

      OpportunityList opportunityList = new OpportunityList();
      opportunityList.setOrgId(this.getId());
      if (!toTrash) {
        opportunityList.setIncludeOnlyTrashed(true);
      }
      opportunityList.buildList(db);
      opportunityList.updateStatus(db, context, toTrash, tmpUserId);

      AssetList assetList = new AssetList();
      assetList.setOrgId(this.getId());
      if (!toTrash) {
        assetList.setIncludeOnlyTrashed(true);
      }
      assetList.buildList(db);
      assetList.updateStatus(db, toTrash, tmpUserId);

      ServiceContractList serviceContractList = new ServiceContractList();
      serviceContractList.setOrgId(this.getId());
      if (!toTrash) {
        serviceContractList.setIncludeOnlyTrashed(true);
      }
      serviceContractList.buildList(db);
      serviceContractList.updateStatus(db, toTrash, tmpUserId);

      TicketList ticketList = new TicketList();
      ticketList.setOrgId(this.getId());
      if (!toTrash) {
        ticketList.setIncludeOnlyTrashed(true);
      }
      ticketList.buildList(db);
      ticketList.updateStatus(db, toTrash, tmpUserId);

      //TODO: implement trashing of related action plan work records
      ActionPlanWorkList planWorkList = new ActionPlanWorkList();
      planWorkList.setLinkModuleId(ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS));
      planWorkList.setIncludeAllSites(true);
      planWorkList.setLinkItemId(this.getOrgId());
      planWorkList.buildList(db);
      planWorkList.delete(db);
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
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
   * @param  thisImportId   Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static boolean deleteImportedRecords(Connection db, int thisImportId) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM organization_emailaddress " +
          "WHERE org_id IN (SELECT org_id from organization o where import_id = ? AND o.org_id = organization_emailaddress.org_id) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM organization_phone " +
          "WHERE org_id IN (SELECT org_id from organization o where import_id = ? AND o.org_id = organization_phone.org_id)");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM organization_address " +
          "WHERE org_id IN (SELECT org_id from organization o where import_id = ? AND o.org_id = organization_address.org_id) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM organization " +
          "WHERE import_id = ?");
      pst.setInt(1, thisImportId);
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
   *  Checks to see if the this account has any associated contacts
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean hasContacts(Connection db) throws SQLException {
    int recordCount = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) as recordcount " +
        "FROM contact " +
        "WHERE org_id = ? ");
    pst.setInt(1, this.getOrgId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = rs.getInt("recordCount");
    }
    rs.close();
    pst.close();
    if (recordCount > 0) {
      return true;
    }
    return false;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Returned Value
   */
  public String toString() {
    StringBuffer out = new StringBuffer();
    out.append("=================================================\r\n");
    out.append("Organization Name: " + this.getName() + "\r\n");
    out.append("ID: " + this.getOrgId() + "\r\n");
    return out.toString();
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //organization table
    this.setOrgId(rs.getInt("org_id"));
    name = rs.getString("name");
    accountNumber = rs.getString("account_number");
    url = rs.getString("url");
    revenue = rs.getDouble("revenue");
    employees = DatabaseUtils.getInt(rs, "employees");
    notes = rs.getString("notes");
    ticker = rs.getString("ticker_symbol");
    //taxId = rs.getString("taxid");
    minerOnly = rs.getBoolean("miner_only");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    industry = rs.getInt("industry_temp_code");
    owner = DatabaseUtils.getInt(rs, "owner");
    stageId = DatabaseUtils.getInt(rs, "stage_id");
    duplicateId = rs.getInt("duplicate_id");
    contractEndDate = rs.getTimestamp("contract_end");
    alertDate = rs.getTimestamp("alertdate");
    alertText = rs.getString("alert");
    nameSalutation = rs.getString("namesalutation");
    nameLast = rs.getString("namelast");
    nameFirst = rs.getString("namefirst");
    nameMiddle = rs.getString("namemiddle");
    nameSuffix = rs.getString("namesuffix");
    importId = DatabaseUtils.getInt(rs, "import_id");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    alertDateTimeZone = rs.getString("alertdate_timezone");
    contractEndDateTimeZone = rs.getString("contract_end_timezone");
    trashedDate = rs.getTimestamp("trashed_date");
    source = DatabaseUtils.getInt(rs, "source");
    rating = DatabaseUtils.getInt(rs, "rating");
    potential = rs.getDouble("potential");
    segmentId = DatabaseUtils.getInt(rs, "segment_id");
    directBill = rs.getBoolean("direct_bill");
    accountSize = DatabaseUtils.getInt(rs, "account_size");
    subSegmentId = DatabaseUtils.getInt(rs, "sub_segment_id");
    siteId = DatabaseUtils.getInt(rs, "site_id");
    dunsType = rs.getString("duns_type");
    dunsNumber = rs.getString("duns_number");
    businessNameTwo = rs.getString("business_name_two");
    sicCode = DatabaseUtils.getInt(rs, "sic_code");
    yearStarted = DatabaseUtils.getInt(rs, "year_started");
    sicDescription = rs.getString("sic_description");

    //contact table
    ownerName = Contact.getNameLastFirst(
        rs.getString("o_namelast"), rs.getString("o_namefirst"));
    enteredByName = Contact.getNameLastFirst(
        rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
    modifiedByName = Contact.getNameLastFirst(
        rs.getString("mb_namelast"), rs.getString("mb_namefirst"));

    //industry table
    industryName = rs.getString("industry_name");

    //account size table
    accountSizeName = rs.getString("account_size_name");

		//organization address table
    city=rs.getString("o_city");
    state=rs.getString("o_state");
    postalCode=rs.getString("o_postalcode");
    county=rs.getString("o_county");
    stageName=rs.getString("stage_name");

  }


  /**
   *  Gets the nameLastFirstMiddle attribute of the Organization object
   *
   * @return    The nameLastFirstMiddle value
   */
  public String getNameLastFirstMiddle() {
    StringBuffer out = new StringBuffer();
    if (nameLast != null && nameLast.trim().length() > 0) {
      out.append(nameLast);
    }
    if (nameFirst != null && nameFirst.trim().length() > 0) {
      if (nameLast.length() > 0) {
        out.append(", ");
      }
      out.append(nameFirst);
    }
    if (nameMiddle != null && nameMiddle.trim().length() > 0) {
      if (nameMiddle.length() > 0) {
        out.append(" ");
      }
      out.append(nameMiddle);
    }
    if (out.toString().length() == 0) {
      return null;
    }
    return out.toString().trim();
  }


  /**
   *  Gets the properties that are TimeZone sensitive for a Call
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("alertDate");
    thisList.add("contractEndDate");
    return thisList;
  }


  /**
   *  Gets the numberParams attribute of the Organization class
   *
   * @return    The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("revenue");
    thisList.add("potential");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   * @param  tz         Description of the Parameter
   * @param  created    Description of the Parameter
   * @param  alertDate  Description of the Parameter
   * @param  user       Description of the Parameter
   * @param  category   Description of the Parameter
   * @param  type       Description of the Parameter
   * @return            Description of the Return Value
   */
  public String generateWebcalEvent(TimeZone tz, Timestamp created, Timestamp alertDate,
      User user, String category, int type) {

    StringBuffer webcal = new StringBuffer();
    String CRLF = System.getProperty("line.separator");

    String description = "";
    if (name != null) {
      description += "Account: " + name;
    }

    if (user != null && user.getContact() != null) {
      description += "\\nOwner: " + user.getContact().getNameFirstLast();
    }

    //write the event
    webcal.append("BEGIN:VEVENT" + CRLF);

    if (type == OrganizationEventList.ACCOUNT_EVENT_ALERT) {
      webcal.append(
          "UID:www.centriccrm.com-accounts-alerts-" + this.getOrgId() + CRLF);
    } else if (type == OrganizationEventList.ACCOUNT_CONTRACT_ALERT) {
      webcal.append(
          "UID:www.centriccrm.com-accounts-contract-alerts-" + this.getOrgId() + CRLF);
    }

    if (created != null) {
      webcal.append("DTSTAMP:" + ICalendar.getDateTimeUTC(created) + CRLF);
    }
    if (entered != null) {
      webcal.append("CREATED:" + ICalendar.getDateTimeUTC(entered) + CRLF);
    }
    if (alertDate != null) {
      webcal.append("DTSTART;TZID=" + tz.getID() + ":" + ICalendar.getDateTime(tz, alertDate) + CRLF);
    }
    if (type == OrganizationEventList.ACCOUNT_EVENT_ALERT) {
      if (alertText != null) {
        webcal.append(ICalendar.foldLine("SUMMARY:" + ICalendar.parseNewLine(alertText)) + CRLF);
      }
    } else if (type == OrganizationEventList.ACCOUNT_CONTRACT_ALERT) {
      webcal.append("SUMMARY:Account Contract Expiry!" + CRLF);
    }

    if (description != null) {
      webcal.append(ICalendar.foldLine("DESCRIPTION:" + ICalendar.parseNewLine(description)) + CRLF);
    }
    if (category != null) {
      webcal.append("CATEGORIES:" + category + CRLF);
    }

    webcal.append("END:VEVENT" + CRLF);

    return webcal.toString();
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  id             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int countEmployees(Connection db, int id) throws SQLException {
    int result = -1;
    PreparedStatement pst = db.prepareStatement("SELECT employees FROM organization WHERE org_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      result = rs.getInt("employees");
    }
    rs.close();
    pst.close();
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @param  employeeCount     Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void updateEmployeeCount(Connection db, int id, int employeeCount) throws SQLException {
    StringBuffer sql = new StringBuffer();
    if (employeeCount != -1) {
      sql.append("UPDATE organization SET employees = ?, " + "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " WHERE org_id = ? AND employees <> ? ");
    } else {
      sql.append("UPDATE organization SET employees = ?, " + "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " WHERE org_id = ? AND employees IS NOT NULL ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, 1, employeeCount);
    pst.setInt(2, id);
    if (employeeCount != -1) {
      pst.setInt(3, employeeCount);
    }
    pst.executeUpdate();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  typeId  Description of the Parameter
   * @return         Description of the Return Value
   */
  public boolean hasType(int typeId) {
    if (types != null) {
      Iterator i = types.iterator();
      while (i.hasNext()) {
        LookupElement element = (LookupElement) i.next();
        if (element.getCode() == typeId) {
          return true;
        }
      }
    }
    return false;
  }


  /**
   *  Method to retrieve the dependencies that allow lead account contacts to be
   *  reverted.
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public DependencyList processResetToLeadDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    try {
      Dependency contactDependency = new Dependency();
      contactDependency.setName("contacts");
      ContactList contacts = new ContactList();
      contacts.setOrgId(this.getOrgId());
      contacts.setHasConversionDate(Constants.FALSE);
      contacts.buildList(db);
      contactDependency.setCount(contacts.size());
      contactDependency.setCanDelete(false);
      dependencyList.add(contactDependency);

      //Check if there exist any relationships
      Dependency relationshipDependency = new Dependency();
      relationshipDependency.setName("relationships");
      RelationshipList thisList = new RelationshipList();
      thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
      thisList.setObjectIdMapsFrom(this.getOrgId());
      thisList.setBuildDualMappings(true);
      thisList.buildList(db);
      relationshipDependency.setCount(thisList.getNumberOfRelationships());
      relationshipDependency.setCanDelete(true);
      dependencyList.add(relationshipDependency);

      Dependency revenueDependency = new Dependency();
      revenueDependency.setName("revenue");
      revenueDependency.setCount(RevenueList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      revenueDependency.setCanDelete(true);
      dependencyList.add(revenueDependency);

      int oppCount = OpportunityList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId());

      if (oppCount > 0) {
        this.setHasOpportunities(true);
      }

      Dependency oppDependency = new Dependency();
      oppDependency.setName("opportunities");
      oppDependency.setCount(oppCount);
      oppDependency.setCanDelete(false);
      dependencyList.add(oppDependency);

      Dependency scDependency = new Dependency();
      scDependency.setName("contracts");
      scDependency.setCount(ServiceContractList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      scDependency.setCanDelete(true);
      dependencyList.add(scDependency);

      Dependency assetDependency = new Dependency();
      assetDependency.setName("assets");
      assetDependency.setCount(AssetList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      assetDependency.setCanDelete(true);
      dependencyList.add(assetDependency);

      Dependency ticDependency = new Dependency();
      ticDependency.setName("tickets");
      ticDependency.setCount(TicketList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      ticDependency.setCanDelete(true);
      dependencyList.add(ticDependency);

      Dependency docDependency = new Dependency();
      docDependency.setName("documents");
      docDependency.setCount(FileItemList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      docDependency.setCanDelete(true);
      dependencyList.add(docDependency);

      Dependency quoteDependency = new Dependency();
      quoteDependency.setName("quotes");
      quoteDependency.setCount(QuoteList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      quoteDependency.setCanDelete(true);
      dependencyList.add(quoteDependency);

      Dependency folderDependency = new Dependency();
      folderDependency.setName("folders");
      folderDependency.setCount(CustomFieldRecordList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      folderDependency.setCanDelete(true);
      dependencyList.add(folderDependency);

      //Action Plan Work
      Dependency planWorkDependency = new Dependency();
      planWorkDependency.setName("actionplanworks");
      planWorkDependency.setCount(ActionPlanWorkList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      planWorkDependency.setCanDelete(true);
      dependencyList.add(planWorkDependency);
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  context           Description of the Parameter
   * @param  userId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int revertBackToLead(Connection db, ActionContext context, int userId) throws SQLException {
    boolean commit = true;
    int contactId = -1;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      ContactList leadAccountContacts = new ContactList();
      leadAccountContacts.setBuildDetails(true);
      leadAccountContacts.setOrgId(this.getOrgId());
      leadAccountContacts.setHasConversionDate(Constants.TRUE);
      leadAccountContacts.setIncludeEnabled(Constants.UNDEFINED);
      leadAccountContacts.buildList(db);
      contactId = leadAccountContacts.revertBackToLead(db, context, userId);

      this.updateStatus(db, context, true, userId);
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
    return contactId;
  }


  /**
   *  This method creates the primary contact for the account and updates the
   *  account while changing an org account into an individual account
   *
   * @param  db                Description of the Parameter
   * @param  create            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void updatePrimaryContact(Connection db, boolean create) throws SQLException {
    Contact primaryContact = new Contact();
    primaryContact.setBuildDetails(true);
    if (this.getPrimaryContactId() != -1) {
      primaryContact.queryRecord(db, this.getPrimaryContactId());
    } else if (this.getPrimaryContact() != null) {
      primaryContact.queryRecord(db, this.getPrimaryContact().getId());
    }
    if (primaryContact.getId() != -1) {
      primaryContact.setPrimaryContact(create);
      primaryContact.updatePrimaryContactInformation(db);
      if (create) {
        this.setNameFirst(primaryContact.getNameFirst());
        this.setNameLast(primaryContact.getNameLast());
        this.setNameMiddle(primaryContact.getNameMiddle());
        this.setName(this.getNameLastFirstMiddle());
      } else {
        this.setNameFirst((String) null);
        this.setNameMiddle((String) null);
        this.setNameLast((String) null);
      }
    }
  }
}

