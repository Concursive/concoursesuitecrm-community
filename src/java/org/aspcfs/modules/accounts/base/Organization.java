//Copyright 2001-2002 Dark Horse Ventures

package org.aspcfs.modules.accounts.base;

import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.zeroio.iteam.base.FileItemList;
import com.darkhorseventures.framework.beans.*;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;

/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal Exp
 *      $
 */
public class Organization extends GenericBean {
  protected double YTD = 0;

  private String errorMessage = "";
  private int orgId = -1;
  private String name = "";
  private String url = "";
  private String lastModified = "";
  private String notes = "";
  private int industry = 0;
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
  private String alertDateTimeZone = null;
  private String contractEndDateTimeZone = null;

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
   * @exception  SQLException  Description of Exception
   */
  public Organization(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @param  org_id            Description of Parameter
   * @exception  SQLException  Description of Exception
   */
  public Organization(Connection db, int org_id) throws SQLException {
    if (org_id == -1) {
      throw new SQLException("Invalid Account");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT o.*, " +
        "ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, " +
        "i.description as industry_name " +
        "FROM organization o " +
        "LEFT JOIN contact ct_owner ON (o.owner = ct_owner.user_id) " +
        "LEFT JOIN contact ct_eb ON (o.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (o.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN lookup_industry i ON (o.industry_temp_code = i.code) " +
        "WHERE o.org_id = ? ");
    pst.setInt(1, org_id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (orgId == -1) {
      throw new SQLException("Account not found");
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

    this.typeList = typeList;
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
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
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
   *  Sets the industry attribute of the Organization object
   *
   * @param  tmp  The new industry value
   */
  public void setIndustry(int tmp) {
    industry = tmp;
  }


  /**
   *  Sets the Miner_only attribute of the Organization object
   *
   * @param  tmp  The new Miner_only value
   */
  public void setMiner_only(boolean tmp) {
    this.minerOnly = tmp;
  }


  /**
   *  Sets the Miner_only attribute of the Organization object
   *
   * @param  tmp  The new Miner_only value
   */
  public void setMiner_only(String tmp) {
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(tmp));
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
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(tmp));
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
   * @param  request  The new RequestItems value
   * @since           1.15
   */
  public void setRequestItems(HttpServletRequest request) {
    phoneNumberList = new OrganizationPhoneNumberList(request);
    addressList = new OrganizationAddressList(request);
    emailAddressList = new OrganizationEmailAddressList(request);
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
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
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
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
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
   *  Gets the contractEndDateStringLongYear attribute of the Organization
   *  object
   *
   * @return    The contractEndDateStringLongYear value
   */
  public String getContractEndDateStringLongYear() {
    String tmp = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG);
      formatter.applyPattern("M/d/yyyy");
      return formatter.format(contractEndDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the enteredStringLongYear attribute of the Organization object
   *
   * @return    The enteredStringLongYear value
   */
  public String getEnteredStringLongYear() {
    String tmp = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG);
      formatter.applyPattern("M/d/yyyy");
      return formatter.format(entered);
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
   *  Gets the alertDateStringLongYear attribute of the Organization object
   *
   * @return    The alertDateStringLongYear value
   */
  public String getAlertDateStringLongYear() {
    String tmp = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG);
      formatter.applyPattern("M/d/yyyy");
      return formatter.format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the alertDateStringLongYear attribute of the Organization class
   *
   * @param  alertDate  Description of the Parameter
   * @return            The alertDateStringLongYear value
   */
  public static String getAlertDateStringLongYear(java.sql.Timestamp alertDate) {
    String tmp = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG);
      formatter.applyPattern("M/d/yyyy");
      return formatter.format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
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
   *  Gets the IndustryName attribute of the Organization object
   *
   * @return    The IndustryName value
   */
  public String getIndustryName() {
    return industryName;
  }


  /**
   *  Gets the PhoneNumber attribute of the Organization object
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
    return addressList.getAddress(thisType);
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
   *  Gets the EnteredBy attribute of the Organization object
   *
   * @return    The EnteredBy value
   */
  public int getEnteredBy() {
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
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    try {
      Dependency contactDependency = new Dependency();
      contactDependency.setName("Contacts");
      contactDependency.setCount(ContactList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      contactDependency.setCanDelete(true);

      //finding if any of the account contacts have user accounts
      if (contactDependency.getCount() > 0) {
        ContactList contactList = new ContactList();
        contactList.setOrgId(this.orgId);
        contactList.buildList(db);
        Iterator itr = contactList.iterator();
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

      Dependency revenueDependency = new Dependency();
      revenueDependency.setName("Revenue");
      revenueDependency.setCount(RevenueList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      revenueDependency.setCanDelete(true);
      dependencyList.add(revenueDependency);

      int oppCount = OpportunityList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId());

      if (oppCount > 0) {
        this.setHasOpportunities(true);
      }

      Dependency oppDependency = new Dependency();
      oppDependency.setName("Opportunities");
      oppDependency.setCount(oppCount);
      oppDependency.setCanDelete(false);
      dependencyList.add(oppDependency);

      Dependency scDependency = new Dependency();
      scDependency.setName("Service Contracts");
      scDependency.setCount(ServiceContractList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      scDependency.setCanDelete(true);
      dependencyList.add(scDependency);

      Dependency assetDependency = new Dependency();
      assetDependency.setName("Assets");
      assetDependency.setCount(AssetList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      assetDependency.setCanDelete(true);
      dependencyList.add(assetDependency);

      Dependency ticDependency = new Dependency();
      ticDependency.setName("Tickets");
      ticDependency.setCount(TicketList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      ticDependency.setCanDelete(true);
      dependencyList.add(ticDependency);

      Dependency docDependency = new Dependency();
      docDependency.setName("Documents");
      docDependency.setCount(FileItemList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      docDependency.setCanDelete(true);
      dependencyList.add(docDependency);

      Dependency folderDependency = new Dependency();
      folderDependency.setName("Folders");
      folderDependency.setCount(CustomFieldRecordList.retrieveRecordCount(db, Constants.ACCOUNTS, this.getId()));
      folderDependency.setCanDelete(true);
      dependencyList.add(folderDependency);
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @param  year              Description of Parameter
   * @param  type              Description of Parameter
   * @param  ownerId           Description of Parameter
   * @exception  SQLException  Description of Exception
   */
  public void buildRevenueYTD(Connection db, int year, int type, int ownerId) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT sum(rv.amount) as s " +
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
   * @param  db                The new typeListToTypes value
   * @exception  SQLException  Description of the Exception
   */
  public void setTypeListToTypes(Connection db) throws SQLException {
    Iterator itr = typeList.iterator();
    while (itr.hasNext()) {
      String tmpId = (String) itr.next();
      types.add(new LookupElement(db, Integer.parseInt(tmpId), "lookup_account_types"));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @exception  SQLException  Description of Exception
   */
  public void buildTypes(Connection db) throws SQLException {
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT atl.type_id " +
        "FROM account_type_levels atl " +
        "WHERE atl.org_id = ? ORDER BY atl.level ");

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, this.getId());
    rs = pst.executeQuery();
    while (rs.next()) {
      types.add(new LookupElement(db, rs.getInt("type_id"), "lookup_account_types"));
    }
    rs.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @param  type_id           Description of Parameter
   * @param  level             Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  public boolean insertType(Connection db, int type_id, int level) throws SQLException {
    if (orgId == -1) {
      throw new SQLException("No Organization ID Specified");
    }
    String sql =
        "INSERT INTO account_type_levels " +
        "(org_id, type_id, level) " +
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
   * @param  db                Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  public boolean resetType(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement("DELETE FROM account_type_levels WHERE org_id = ? ");
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  public boolean disable(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE organization set enabled = " + DatabaseUtils.getFalse(db) + " " +
        "WHERE org_id = ? ");

    sql.append("AND modified = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, orgId);

    pst.setTimestamp(++i, this.getModified());

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
   * @param  db                Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  public boolean enable(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE organization set enabled = " + DatabaseUtils.getTrue(db) + " " +
        "WHERE org_id = ? ");

    sql.append("AND modified = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, orgId);

    pst.setTimestamp(++i, this.getModified());

    int resultCount = pst.executeUpdate();
    pst.close();

    if (resultCount == 1) {
      success = true;
    }

    return success;
  }


  /**
   *  Description of the Method
   */
  public void listTypes() {
    for (int i = 0; i < typeList.size(); i++) {
      String val = (String) typeList.get(i);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @param  checkName         Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  public boolean checkIfExists(Connection db, String checkName) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sqlSelect =
        "SELECT name, org_id " +
        "FROM organization " +
        "WHERE lower(organization.name) = ? ";
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
   * @param  db                Description of the Parameter
   * @param  lookupName        Description of the Parameter
   * @param  importId          Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static int lookupAccount(Connection db, String lookupName, int importId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int lookupId = -1;
    String sqlSelect =
        "SELECT org_id " +
        "FROM organization o " +
        "WHERE lower(o.name) = ? AND (o.status_id IS NULL OR o.status_id = ? OR (o.status_id = ? AND o.import_id = ?) ) ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setString(++i, lookupName.toLowerCase());
    pst.setInt(++i, Import.PROCESSED_APPROVED);
    pst.setInt(++i, Import.PROCESSED_UNAPPROVED);
    pst.setInt(++i, importId);
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
   * @param  db                Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {

    if (!isValid(db)) {
      return false;
    }

    StringBuffer sql = new StringBuffer();

    try {
      modifiedBy = enteredBy;
      db.setAutoCommit(false);
      sql.append("INSERT INTO ORGANIZATION (name, industry_temp_code, url, miner_only, owner, duplicate_id, ");
      sql.append("notes, employees, revenue, ticker_symbol, account_number, nameFirst, nameLast, nameMiddle, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      if (statusId > -1) {
        sql.append("status_id, ");
      }
      if (importId > -1) {
        sql.append("import_id, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      if (statusId > -1) {
        sql.append("?, ");
      }
      if (importId > -1) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
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
      pst.setInt(++i, this.getEmployees());
      pst.setDouble(++i, this.getRevenue());
      pst.setString(++i, this.getTicker());
      pst.setString(++i, this.getAccountNumber());
      pst.setString(++i, this.getNameFirst());
      pst.setString(++i, this.getNameLast());
      pst.setString(++i, this.getNameMiddle());

      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (statusId > -1) {
        pst.setInt(++i, this.getStatusId());
      }
      if (importId > -1) {
        pst.setInt(++i, this.getImportId());
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }

      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();

      orgId = DatabaseUtils.getCurrVal(db, "organization_org_id_seq");

      //Insert primary contact if account is an individual
      if (nameLast != null && !"".equals(nameLast)) {
        primaryContact.setOrgId(orgId);
        primaryContact.setOrgName(this.getName());
        boolean contactInserted = primaryContact.insert(db);
        if (!contactInserted) {
          throw new SQLException("Contact could not be inserted");
        }
      }

      //Insert the phone numbers if there are any
      Iterator iphone = phoneNumberList.iterator();
      while (iphone.hasNext()) {
        OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber) iphone.next();
        //thisPhoneNumber.insert(db, this.getOrgId(), this.getEnteredBy());
        thisPhoneNumber.process(db, orgId, this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the addresses if there are any
      Iterator iaddress = addressList.iterator();
      while (iaddress.hasNext()) {
        OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
        //thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
        thisAddress.process(db, orgId, this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the email addresses if there are any
      Iterator iemail = emailAddressList.iterator();
      while (iemail.hasNext()) {
        OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress) iemail.next();
        //thisEmailAddress.insert(db, this.getOrgId(), this.getEnteredBy());
        thisEmailAddress.process(db, orgId, this.getEnteredBy(), this.getModifiedBy());
      }
      this.update(db, true);
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
   * @param  db                Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  public int update(Connection db) throws SQLException {
    int i = -1;
    if (!isValid(db)) {
      return -1;
    }
    try {
      db.setAutoCommit(false);
      i = this.update(db, false);
      //Process the phone numbers if there are any
      Iterator iphone = phoneNumberList.iterator();
      while (iphone.hasNext()) {
        OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber) iphone.next();
        thisPhoneNumber.process(db, this.getOrgId(), this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the addresses if there are any
      Iterator iaddress = addressList.iterator();
      while (iaddress.hasNext()) {
        OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
        thisAddress.process(db, this.getOrgId(), this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the email addresses if there are any
      Iterator iemail = emailAddressList.iterator();
      while (iemail.hasNext()) {
        OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress) iemail.next();
        thisEmailAddress.process(db, this.getOrgId(), this.getEnteredBy(), this.getModifiedBy());
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
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  newOwner          Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
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
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void populatePrimaryContact(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT contact_id " +
        "FROM contact " +
        "WHERE org_id = ? AND primary_contact = ? ");
    pst.setInt(1, this.getOrgId());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      primaryContact = new Contact();
      primaryContact.setBuildDetails(true);
      primaryContact.queryRecord(db, rs.getInt("contact_id"));
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @param  override          Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  public int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;

    if (!isValid(db)) {
      return -1;
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE organization " +
        "SET name = ?, industry_temp_code = ?, " +
        "url = ?, notes= ?, ");

    if (override == false) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append("modifiedby = ?, " +
        "employees = ?, revenue = ?, ticker_symbol = ?, account_number = ?, ");

    if (owner > -1) {
      sql.append("owner = ?, ");
    }

    sql.append("duplicate_id = ?, contract_end = ?, contract_end_timezone = ?, alertdate = ?, alertdate_timezone=?, alert = ?, nameFirst = ?, nameMiddle = ?, nameLast = ? " +
        "WHERE org_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, name);
    pst.setInt(++i, industry);
    pst.setString(++i, url);
    pst.setString(++i, notes);
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, employees);
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
    pst.setString(++i, nameFirst);
    pst.setString(++i, nameMiddle);
    pst.setString(++i, nameLast);
    pst.setInt(++i, orgId);
    if (!override) {
      pst.setTimestamp(++i, this.getModified());
    }
    resultCount = pst.executeUpdate();
    pst.close();

    // When an account name gets updated,
    // the stored org_name in contact needs to be updated
    pst = db.prepareStatement(
        "UPDATE contact " +
        "SET org_name = ? " +
        "WHERE org_id = ? " +
        "AND org_name NOT LIKE ? ");
    pst.setString(1, name);
    pst.setInt(2, orgId);
    pst.setString(3, name);
    pst.executeUpdate();
    pst.close();

    //Remove all account types, add new list
    if (this.getMinerOnly() == false && typeList != null) {
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
   *  Renames the CFS organization running this database
   *
   * @param  db                Description of the Parameter
   * @param  newName           Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public static void renameMyCompany(Connection db, String newName) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE organization " +
        "SET name = ? " +
        "WHERE org_id = 0 ");
    pst.setString(1, newName);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @param  baseFilePath      Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified.");
    }
    try {
      db.setAutoCommit(false);

      //Tickets have accounts, contacts, assets, service contracts related, so delete them first
      TicketList ticketList = new TicketList();
      ticketList.setOrgId(this.getOrgId());
      ticketList.buildList(db);
      ticketList.delete(db, baseFilePath);
      ticketList = null;

      // Delete after tickets
      AssetList assetList = new AssetList();
      assetList.setOrgId(this.getOrgId());
      assetList.buildList(db);
      assetList.delete(db);
      assetList = null;

      // Delete after tickets and after assets
      ServiceContractList scList = new ServiceContractList();
      scList.setOrgId(this.getOrgId());
      scList.buildList(db);
      scList.delete(db);
      scList = null;

      if (documentDelete) {
        FileItemList fileList = new FileItemList();
        fileList.setLinkModuleId(Constants.ACCOUNTS);
        fileList.setLinkItemId(this.getOrgId());
        fileList.buildList(db);
        fileList.delete(db, baseFilePath);
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

      //Save for next to last since other records related to contacts
      if (contactDelete) {
        ContactList contactList = new ContactList();
        contactList.setOrgId(this.getOrgId());
        contactList.buildList(db);
        contactList.delete(db);
        contactList = null;
      }

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
      db.commit();
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
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @exception  SQLException  Description of Exception
   */
  public void deleteMinerOnly(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("The Organization could not be found.");
    }
    try {
      db.setAutoCommit(false);
      Statement st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM news WHERE org_id = " + this.getOrgId());
      st.executeUpdate(
          "DELETE FROM organization " +
          "WHERE org_id = " + this.getOrgId() + " " +
          "AND miner_only = " + DatabaseUtils.getTrue(db) + " ");
      st.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   *  Approves all records for a specific import
   *
   * @param  db                Description of the Parameter
   * @param  importId          Description of the Parameter
   * @param  status            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
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
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  thisImportId      Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
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
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
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
   *  Gets the Valid attribute of the Organization object
   *
   * @param  db                Description of Parameter
   * @return                   The Valid value
   * @exception  SQLException  Description of Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    if ((name == null || name.trim().equals("")) && (nameLast == null || nameLast.trim().equals(""))) {
      errors.put("nameError", "Organization name is required.");
      errors.put("nameLastError", "Last name is required.");
    }
    if (hasErrors()) {
      //Check warnings
      checkWarnings();
      onlyWarnings = false;
      return false;
    } else {
      //Do not check for warnings if it was found that only warnings existed
      // in the previous call to isValid for the same form.
      if (!onlyWarnings) {
        //Check for warnings if there are no errors
        checkWarnings();
        if (hasWarnings()) {
          onlyWarnings = true;
          return false;
        }
      }
      return true;
    }
  }


  /**
   *  Generates warnings that need to be reviewed before the
   *  form can be submitted.
   */
  protected void checkWarnings() {
    if ((errors.get("alertDateError") == null) && (alertDate != null)) {
      if (alertDate.before(new java.util.Date())) {
        warnings.put("alertDateWarning", "Alert date is earlier than current date or set to current date");
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //organization table
    this.setOrgId(rs.getInt("org_id"));
    name = rs.getString("name");
    accountNumber = rs.getString("account_number");
    url = rs.getString("url");
    revenue = rs.getDouble("revenue");
    employees = rs.getInt("employees");
    notes = rs.getString("notes");
    //sicCode = rs.getString("sic_code");
    ticker = rs.getString("ticker_symbol");
    //taxId = rs.getString("taxid");
    minerOnly = rs.getBoolean("miner_only");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    industry = rs.getInt("industry_temp_code");
    owner = rs.getInt("owner");
    if (rs.wasNull()) {
      owner = -1;
    }
    duplicateId = rs.getInt("duplicate_id");
    contractEndDate = rs.getTimestamp("contract_end");
    alertDate = rs.getTimestamp("alertdate");
    alertText = rs.getString("alert");
    contractEndDateTimeZone = rs.getString("contract_end_timezone");
    alertDateTimeZone = rs.getString("alertdate_timezone");

    //contacts as accounts
    nameSalutation = rs.getString("nameSalutation");
    nameLast = rs.getString("nameLast");
    nameFirst = rs.getString("nameFirst");
    nameMiddle = rs.getString("nameMiddle");
    nameSuffix = rs.getString("nameSuffix");

    //import information
    importId = DatabaseUtils.getInt(rs, "import_id");
    statusId = DatabaseUtils.getInt(rs, "status_id");

    //contact table
    ownerName = Contact.getNameLastFirst(rs.getString("o_namelast"), rs.getString("o_namefirst"));
    enteredByName = Contact.getNameLastFirst(rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
    modifiedByName = Contact.getNameLastFirst(rs.getString("mb_namelast"), rs.getString("mb_namefirst"));

    //industry_temp table
    industryName = rs.getString("industry_name");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of Parameter
   * @return                   Description of the Returned Value
   * @exception  SQLException  Description of Exception
   */
  private boolean hasRelatedRecords(Connection db) throws SQLException {
    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT count(*) as count " +
        "FROM opportunity_component " +
        "WHERE acctlink = " + this.getOrgId());
    rs.next();
    int recordCount = rs.getInt("count");
    rs.close();
    st.close();

    return (recordCount > 0);
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
    return thisList;
  }
}


