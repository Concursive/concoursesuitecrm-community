//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *@author     chris
 *@created    July 12, 2001
 *@version    $Id$
 */
public class Organization extends GenericBean {

  private String errorMessage = "";
  private int orgId = -1;
  private String name = "";
  private String url = "";
  private String lastModified = "";
  private String notes = "";
  private int industry = 0;
  private String industryName = null;
  private boolean miner_only = false;
  private int enteredBy = -1;

  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private java.sql.Date contractEndDate = null;

  private java.sql.Date alertDate = null;
  private String alertText = "";

  private int modifiedBy = -1;
  private boolean enabled = true;
  private int employees = 0;
  private double revenue = 0;
  private String ticker = "";
  private String accountNumber = "";
  private int owner = -1;
  private int duplicateId = -1;

  private OrganizationAddressList addressList = new OrganizationAddressList();
  private OrganizationPhoneNumberList phoneNumberList = new OrganizationPhoneNumberList();
  private OrganizationEmailAddressList emailAddressList = new OrganizationEmailAddressList();
  private String ownerName = "";
  private String enteredByName = "";
  private String modifiedByName = "";


  /**
   *  Constructor for the Organization object, creates an empty Organization
   *
   *@since    1.0
   */
  public Organization() { }


  /**
   *  Constructor for the Organization object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Organization(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }



  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  org_id            Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public Organization(Connection db, int org_id) throws SQLException {

    if (org_id == -1) {
      throw new SQLException("Invalid Account");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT o.*, " +
        "ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, " +
        "i.name as industry_name " +
        "FROM organization o " +
        "LEFT JOIN contact ct_owner ON (o.owner = ct_owner.user_id) " +
        "LEFT JOIN contact ct_eb ON (o.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (o.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN industry_temp i ON (o.industry_temp_code = i.code) " +
        "WHERE o.org_id = " + org_id + " ");

    Statement st = null;
    ResultSet rs = null;
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());

    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Account not found");
    }
    rs.close();
    st.close();

    phoneNumberList.setOrgId(this.getOrgId());
    phoneNumberList.buildList(db);
    addressList.setOrgId(this.getOrgId());
    addressList.buildList(db);
    emailAddressList.setOrgId(this.getOrgId());
    emailAddressList.buildList(db);
  }


  /**
   *  Sets the EnteredByName attribute of the Organization object
   *
   *@param  enteredByName  The new EnteredByName value
   *@since
   */
  public void setEnteredByName(String enteredByName) {
    this.enteredByName = enteredByName;
  }


  /**
   *  Sets the ModifiedByName attribute of the Organization object
   *
   *@param  modifiedByName  The new ModifiedByName value
   *@since
   */
  public void setModifiedByName(String modifiedByName) {
    this.modifiedByName = modifiedByName;
  }


  /**
   *  Sets the OwnerName attribute of the Organization object
   *
   *@param  ownerName  The new OwnerName value
   *@since
   */
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }


  /**
   *  Sets the ErrorMessage attribute of the Organization object
   *
   *@param  tmp  The new ErrorMessage value
   *@since
   */
  public void setErrorMessage(String tmp) {
    this.errorMessage = tmp;
  }


  /**
   *  Sets the Owner attribute of the Organization object
   *
   *@param  owner  The new Owner value
   *@since
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the Entered attribute of the Organization object
   *
   *@param  tmp  The new Entered value
   *@since
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the alertDate attribute of the Organization object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(java.sql.Date tmp) {
    this.alertDate = tmp;
  }


  /**
   *  Sets the alertText attribute of the Organization object
   *
   *@param  tmp  The new alertText value
   */
  public void setAlertText(String tmp) {
    this.alertText = tmp;
  }


  /**
   *  Sets the Modified attribute of the Organization object
   *
   *@param  tmp  The new Modified value
   *@since
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the Entered attribute of the Organization object
   *
   *@param  tmp  The new Entered value
   *@since
   */
  public void setEntered(String tmp) {
    this.entered = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the Modified attribute of the Organization object
   *
   *@param  tmp  The new Modified value
   *@since
   */
  public void setModified(String tmp) {
    this.modified = java.sql.Timestamp.valueOf(tmp);
    ;
  }


  /**
   *  Sets the Owner attribute of the Organization object
   *
   *@param  owner  The new Owner value
   *@since
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the ContractEndDate attribute of the Organization object
   *
   *@param  contractEndDate  The new ContractEndDate value
   *@since
   */
  public void setContractEndDate(java.sql.Date contractEndDate) {
    this.contractEndDate = contractEndDate;
  }


  /**
   *  Sets the ContractEndDate attribute of the Organization object
   *
   *@param  tmp  The new ContractEndDate value
   *@since
   */
  public void setContractEndDate(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      contractEndDate = new java.sql.Date(new java.util.Date().getTime());
      contractEndDate.setTime(tmpDate.getTime());
    } catch (Exception e) {
      contractEndDate = null;
    }
  }


  /**
   *  Sets the alertDate attribute of the Organization object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      alertDate = new java.sql.Date(new java.util.Date().getTime());
      alertDate.setTime(tmpDate.getTime());
    } catch (Exception e) {
      alertDate = null;
    }
  }


  /**
   *  Sets the Employees attribute of the Organization object
   *
   *@param  employees  The new Employees value
   *@since
   */
  public void setEmployees(String employees) {
    this.employees = Integer.parseInt(employees);
  }


  /**
   *  Sets the DuplicateId attribute of the Organization object
   *
   *@param  duplicateId  The new DuplicateId value
   *@since
   */
  public void setDuplicateId(int duplicateId) {
    this.duplicateId = duplicateId;
  }


  /**
   *  Sets the orgId attribute of the Organization object
   *
   *@param  tmp  The new orgId value
   *@since
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
   *@param  modifiedBy  The new ModifiedBy value
   *@since
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   *  Sets the AccountNumber attribute of the Organization obA  9
   *
   *@param  accountNumber  The new AccountNumber value
   *@since
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }


  /**
   *  Sets the OrgId attribute of the Organization object
   *
   *@param  tmp  The new OrgId value
   *@since
   */
  public void setOrgId(String tmp) {
    this.setOrgId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the Revenue attribute of the Organization object
   *
   *@param  revenue  The new Revenue value
   *@since
   */
  public void setRevenue(String revenue) {
    this.revenue = Double.parseDouble(revenue);
  }


  /**
   *  Sets the Name attribute of the Organization object
   *
   *@param  tmp  The new Name value
   *@since
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the Url attribute of the Organization object
   *
   *@param  tmp  The new Url value
   *@since
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the Ticker attribute of the Organization object
   *
   *@param  ticker  The new Ticker value
   *@since
   */
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }


  /**
   *  Sets the LastModified attribute of the Organization object
   *
   *@param  tmp  The new LastModified value
   *@since
   */
  public void setLastModified(String tmp) {
    this.lastModified = tmp;
  }


  /**
   *  Sets the Notes attribute of the Organization object
   *
   *@param  tmp  The new Notes value
   *@since
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the Industry attribute of the Organization object
   *
   *@param  tmp  The new Industry value
   *@since
   */
  public void setIndustry(String tmp) {
    this.industry = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Miner_only attribute of the Organization object
   *
   *@param  tmp  The new Miner_only value
   *@since
   */
  public void setMiner_only(boolean tmp) {
    this.miner_only = tmp;
  }


  /**
   *  Sets the AddressList attribute of the Organization object
   *
   *@param  tmp  The new AddressList value
   *@since
   */
  public void setAddressList(OrganizationAddressList tmp) {
    this.addressList = tmp;
  }


  /**
   *  Sets the PhoneNumberList attribute of the Organization object
   *
   *@param  tmp  The new PhoneNumberList value
   *@since
   */
  public void setPhoneNumberList(OrganizationPhoneNumberList tmp) {
    this.phoneNumberList = tmp;
  }


  /**
   *  Sets the EmailAddressList attribute of the Organization object
   *
   *@param  tmp  The new EmailAddressList value
   *@since
   */
  public void setEmailAddressList(OrganizationEmailAddressList tmp) {
    this.emailAddressList = tmp;
  }


  /**
   *  Sets the Enteredby attribute of the Organization object
   *
   *@param  tmp  The new Enteredby value
   *@since
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the Enabled attribute of the Organization object
   *
   *@param  tmp  The new Enabled value
   *@since
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Enabled attribute of the Organization object
   *
   *@param  tmp  The new Enabled value
   *@since
   */
  public void setEnabled(String tmp) {
    if (tmp.toLowerCase().equals("false")) {
      this.enabled = false;
    } else {
      this.enabled = true;
    }
  }


  /**
   *  Since dynamic fields cannot be auto-populated, passing the request to this
   *  method will populate the indicated fields.
   *
   *@param  request  The new RequestItems value
   *@since           1.15
   */
  public void setRequestItems(HttpServletRequest request) {
    phoneNumberList = new OrganizationPhoneNumberList(request);
    addressList = new OrganizationAddressList(request);
    emailAddressList = new OrganizationEmailAddressList(request);
  }


  /**
   *  Gets the alertDate attribute of the Organization object
   *
   *@return    The alertDate value
   */
  public java.sql.Date getAlertDate() {
    return alertDate;
  }


  /**
   *  Gets the alertText attribute of the Organization object
   *
   *@return    The alertText value
   */
  public String getAlertText() {
    return alertText;
  }


  /**
   *  Gets the Entered attribute of the Organization object
   *
   *@return    The Entered value
   *@since
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the Modified attribute of the Organization object
   *
   *@return    The Modified value
   *@since
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the ModifiedString attribute of the Organization object
   *
   *@return    The ModifiedString value
   *@since
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
   *@return    The EnteredString value
   *@since
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
   *  Gets the ContractEndDate attribute of the Organization object
   *
   *@return    The ContractEndDate value
   *@since
   */
  public java.sql.Date getContractEndDate() {
    return contractEndDate;
  }


  /**
   *  Gets the ContractEndDateString attribute of the Organization object
   *
   *@return    The ContractEndDateString value
   *@since
   */
  public String getContractEndDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(contractEndDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }
  
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
   *  Gets the alertDateString attribute of the Organization object
   *
   *@return    The alertDateString value
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
   *@return    The alertDetails value
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
   *@return    The alertDateStringLongYear value
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
   *  Gets the DuplicateId attribute of the Organization object
   *
   *@return    The DuplicateId value
   *@since
   */
  public int getDuplicateId() {
    return duplicateId;
  }


  /**
   *  Gets the OwnerName attribute of the Organization object
   *
   *@return    The OwnerName value
   *@since
   */
  public String getOwnerName() {
    return ownerName;
  }


  /**
   *  Gets the EnteredByName attribute of the Organization object
   *
   *@return    The EnteredByName value
   *@since
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   *  Gets the ModifiedByName attribute of the Organization object
   *
   *@return    The ModifiedByName value
   *@since
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   *  Gets the Owner attribute of the Organization object
   *
   *@return    The Owner value
   *@since
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the AccountNumber attribute of the Organization object
   *
   *@return    The AccountNumber value
   *@since
   */
  public String getAccountNumber() {
    return accountNumber;
  }


  /**
   *  Gets the Ticker attribute of the Organization object
   *
   *@return    The Ticker value
   *@since
   */
  public String getTicker() {
    return ticker;
  }


  /**
   *  Gets the Revenue attribute of the Organization object
   *
   *@return    The Revenue value
   *@since
   */
  public double getRevenue() {
    return revenue;
  }


  /**
   *  Gets the Employees attribute of the Organization object
   *
   *@return    The Employees value
   *@since
   */
  public int getEmployees() {
    return employees;
  }


  /**
   *  Gets the ErrorMessage attribute of the Organization object
   *
   *@return    The ErrorMessage value
   *@since
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   *  Gets the orgId attribute of the Organization object
   *
   *@return    The orgId value
   *@since
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the Name attribute of the Organization object
   *
   *@return    The Name value
   *@since
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the Url attribute of the Organization object
   *
   *@return    The Url value
   *@since
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the LastModified attribute of the Organization object
   *
   *@return    The LastModified value
   *@since
   */
  public String getLastModified() {
    return lastModified;
  }


  /**
   *  Gets the Notes attribute of the Organization object
   *
   *@return    The Notes value
   *@since
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the Industry attribute of the Organization object
   *
   *@return    The Industry value
   *@since
   */
  public int getIndustry() {
    return industry;
  }


  /**
   *  Gets the IndustryName attribute of the Organization object
   *
   *@return    The IndustryName value
   *@since
   */
  public String getIndustryName() {
    return industryName;
  }


  /**
   *  Gets the PhoneNumber attribute of the Organization object
   *
   *@param  thisType  Description of Parameter
   *@return           The PhoneNumber value
   *@since
   */
  public String getPhoneNumber(String thisType) {
    return phoneNumberList.getPhoneNumber(thisType);
  }


  /**
   *  Gets the EmailAddress attribute of the Organization object
   *
   *@param  thisType  Description of Parameter
   *@return           The EmailAddress value
   *@since
   */
  public String getEmailAddress(String thisType) {
    return emailAddressList.getEmailAddress(thisType);
  }


  /**
   *  Gets the Address attribute of the Organization object
   *
   *@param  thisType  Description of Parameter
   *@return           The Address value
   *@since
   */
  public Address getAddress(String thisType) {
    return addressList.getAddress(thisType);
  }


  /**
   *  Gets the Enteredby attribute of the Organization object
   *
   *@return    The Enteredby value
   *@since
   */
  public int getEnteredby() {
    return enteredBy;
  }


  /**
   *  Gets the Miner_only attribute of the Organization object
   *
   *@return    The Miner_only value
   *@since
   */
  public boolean getMiner_only() {
    return miner_only;
  }


  /**
   *  Gets the EnteredBy attribute of the Organization object
   *
   *@return    The EnteredBy value
   *@since
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the Organization object
   *
   *@return    The ModifiedBy value
   *@since
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the AddressList attribute of the Organization object
   *
   *@return    The AddressList value
   *@since
   */
  public OrganizationAddressList getAddressList() {
    return addressList;
  }


  /**
   *  Gets the PhoneNumberList attribute of the Organization object
   *
   *@return    The PhoneNumberList value
   *@since
   */
  public OrganizationPhoneNumberList getPhoneNumberList() {
    return phoneNumberList;
  }


  /**
   *  Gets the EmailAddressList attribute of the Organization object
   *
   *@return    The EmailAddressList value
   *@since
   */
  public OrganizationEmailAddressList getEmailAddressList() {
    return emailAddressList;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  checkName         Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean checkIfExists(Connection db, String checkName) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sqlSelect = new StringBuffer();

    db.setAutoCommit(false);
    sqlSelect.append("select name,org_id from organization where lower(organization.name) = ? ");

    int i = 0;
    pst = db.prepareStatement(sqlSelect.toString());
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
          "INSERT INTO ORGANIZATION (name,industry_temp_code,url,miner_only,enteredby,modifiedby,owner,duplicate_id,notes,employees,revenue,ticker_symbol,account_number) " +
          "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());

      pst.setString(++i, this.getName());
      pst.setInt(++i, this.getIndustry());
      pst.setString(++i, this.getUrl());
      pst.setBoolean(++i, this.getMiner_only());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getOwner());
      pst.setInt(++i, this.getDuplicateId());
      pst.setString(++i, this.getNotes());
      pst.setInt(++i, this.getEmployees());
      pst.setDouble(++i, this.getRevenue());
      pst.setString(++i, this.getTicker());
      pst.setString(++i, this.getAccountNumber());

      pst.execute();
      pst.close();

      Statement st = db.createStatement();
      ResultSet rs = st.executeQuery("select currval('organization_org_id_seq')");

      if (rs.next()) {
        this.setOrgId(rs.getInt(1));
      }
      rs.close();
      st.close();

      //Insert the phone numbers if there are any
      Iterator iphone = phoneNumberList.iterator();
      while (iphone.hasNext()) {
        OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber) iphone.next();
        thisPhoneNumber.insert(db, this.getOrgId(), this.getEnteredBy());
      }

      //Insert the addresses if there are any
      Iterator iaddress = addressList.iterator();
      while (iaddress.hasNext()) {
        OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
        thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
      }

      //Insert the email addresses if there are any
      Iterator iemail = emailAddressList.iterator();
      while (iemail.hasNext()) {
        OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress) iemail.next();
        thisEmailAddress.insert(db, this.getOrgId(), this.getEnteredBy());
      }

      this.update(db, true);
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
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
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }

    db.setAutoCommit(true);
    return i;
  }



  /**
   *  Description of the Method
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
        "UPDATE organization SET name = ?, industry_temp_code = ?, " +
        "url = ?, notes= ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ?, employees = ?, revenue = ?, ticker_symbol = ?, account_number = ?, owner = ?, duplicate_id = ?, contract_end = ?, alertdate = ?, alert = ? " +
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
    pst.setInt(++i, this.getOwner());
    pst.setInt(++i, this.getDuplicateId());

    if (contractEndDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setDate(++i, this.getContractEndDate());
    }

    if (alertDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setDate(++i, this.getAlertDate());
    }

    pst.setString(++i, alertText);

    pst.setInt(++i, orgId);
    if (!override) {
      pst.setTimestamp(++i, this.getModified());
    }

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified.");
    }

    Statement st = db.createStatement();

    if (hasRelatedRecords(db)) {
      errors.put("actionError", "Contact disabled from view, since it has a related user account");
      st.executeUpdate(
          "UPDATE organization " +
          "SET enabled = false " +
          "WHERE org_id = " + this.getOrgId());
      st.close();
      return true;
    } else {
      try {
        db.setAutoCommit(false);
        st.executeUpdate("DELETE FROM organization_phone WHERE org_id = " + this.getOrgId());
        st.executeUpdate("DELETE FROM organization_emailaddress WHERE org_id = " + this.getOrgId());
        st.executeUpdate("DELETE FROM organization_address WHERE org_id = " + this.getOrgId());
        st.executeUpdate("DELETE FROM organization WHERE org_id = " + this.getOrgId());
        db.commit();
      } catch (SQLException e) {
        db.rollback();
      } finally {
        db.setAutoCommit(true);
        st.close();
      }
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void deleteMinerOnly(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("The Organization could not be found.");
    }

    try {
      db.setAutoCommit(false);
      Statement st = db.createStatement();
      st.executeUpdate("DELETE FROM news WHERE org_id = " + this.getOrgId());
      st.executeUpdate("DELETE FROM organization WHERE org_id= " + this.getOrgId() + " AND miner_only='t' ");
      st.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since
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
   *@param  db                Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected boolean isValid(Connection db) throws SQLException {
    errors.clear();

    if (name == null || name.trim().equals("")) {
      errors.put("nameError", "An account name is required.");
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
    this.setOrgId(rs.getInt("org_id"));
    name = rs.getString("name");
    notes = rs.getString("notes");
    industry = rs.getInt("industry_temp_code");
    industryName = rs.getString("industry_name");
    url = rs.getString("url");
    employees = rs.getInt("employees");
    revenue = rs.getDouble("revenue");
    ticker = rs.getString("ticker_symbol");
    accountNumber = rs.getString("account_number");
    owner = rs.getInt("owner");
    duplicateId = rs.getInt("duplicate_id");
    enteredByName = rs.getString("eb_namelast") + ", " + rs.getString("eb_namefirst");
    modifiedByName = rs.getString("mb_namelast") + ", " + rs.getString("mb_namefirst");
    ownerName = rs.getString("o_namelast") + ", " + rs.getString("o_namefirst");

    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
    contractEndDate = rs.getDate("contract_end");
    alertDate = rs.getDate("alertdate");
    alertText = rs.getString("alert");

    enteredBy = rs.getInt("enteredby");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  private boolean hasRelatedRecords(Connection db) throws SQLException {
    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT count(*) as count " +
        "FROM opportunity " +
        "WHERE acctlink = " + this.getOrgId());
    rs.next();
    int recordCount = rs.getInt("count");
    rs.close();
    st.close();

    return (recordCount > 0);
  }
}


