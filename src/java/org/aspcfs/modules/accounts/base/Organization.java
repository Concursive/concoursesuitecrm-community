//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.zeroio.iteam.base.FileItemList;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.webutils.LookupList;
import com.darkhorseventures.utils.DateUtils;

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
  private boolean minerOnly = false;
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

  private LookupList types = new LookupList();
  private ArrayList typeList = null;
  protected double YTD = 0;
  
  private boolean contactDelete = false;
  private boolean revenueDelete = false;
  private boolean documentDelete = false;
  
  
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
        "i.description as industry_name " +
        "FROM organization o " +
        "LEFT JOIN contact ct_owner ON (o.owner = ct_owner.user_id) " +
        "LEFT JOIN contact ct_eb ON (o.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (o.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN lookup_industry i ON (o.industry_temp_code = i.code) " +
        "WHERE o.org_id = " + org_id + " ");

    Statement st = null;
    ResultSet rs = null;
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());

    if (rs.next()) {
      buildRecord(rs);
      buildTypes(db);
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
   *  Sets the typeList attribute of the Organization object
   *
   *@param  typeList  The new typeList value
   */
  public void setTypeList(ArrayList typeList) {
    this.typeList = typeList;
  }


  /**
   *  Sets the typeList attribute of the Organization object
   *
   *@param  criteriaString  The new typeList value
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
  
public boolean getContactDelete() { return contactDelete; }
public boolean getRevenueDelete() { return revenueDelete; }
public boolean getDocumentDelete() { return documentDelete; }
public void setContactDelete(boolean tmp) { this.contactDelete = tmp; }
public void setRevenueDelete(boolean tmp) { this.revenueDelete = tmp; }
public void setDocumentDelete(boolean tmp) { this.documentDelete = tmp; }

  public HashMap canDelete(Connection db)  throws SQLException {
	  StringBuffer sqlCount = new StringBuffer();
	  ResultSet rs = null;
	  PreparedStatement pst = null;
	  HashMap dependencies = new HashMap();
	  
	  dependencies.put(new String("contacts"), new Integer(0));
	  dependencies.put(new String("revenue"), new Integer(0));
	  dependencies.put(new String("opportunities"), new Integer(0));
	  dependencies.put(new String("tickets"), new Integer(0));
	  dependencies.put(new String("documents"), new Integer(0));
	  dependencies.put(new String("folders"), new Integer(0));
	  
	  //get number of contacts
	  sqlCount.append(
	  	"SELECT COUNT(*) as contactcount FROM contact c WHERE c.org_id = " + this.getOrgId() + " ");
	
	  pst = db.prepareStatement(sqlCount.toString());
	  rs = pst.executeQuery();
	  if (rs.next()) {
		  dependencies.put(new String("contacts"), new Integer(rs.getInt("contactcount")));
          }
	  
	  rs.close();
	  pst.close();
	  
	  sqlCount = new StringBuffer();
	  
	  //get number of revenue
	  sqlCount.append(
	  	"SELECT COUNT(*) as revenuecount FROM revenue r WHERE r.org_id = " + this.getOrgId() + " ");
	
	  pst = db.prepareStatement(sqlCount.toString());
	  rs = pst.executeQuery();
	  if (rs.next()) {
		  dependencies.put(new String("revenue"), new Integer(rs.getInt("revenuecount")));
          }
	  
	  rs.close();
	  pst.close();
	  
	  sqlCount = new StringBuffer();
	  
	  //get number of opps
	  sqlCount.append(
	  	"SELECT COUNT(*) as oppcount FROM opportunity o WHERE o.acctlink = " + this.getOrgId() + " ");
	
	  pst = db.prepareStatement(sqlCount.toString());
	  rs = pst.executeQuery();
	  if (rs.next()) {
		  dependencies.put(new String("opportunities"), new Integer(rs.getInt("oppcount")));
          }
	  
	  rs.close();
	  pst.close();
	  
	  sqlCount = new StringBuffer();
	  
	  //get number of tickets
	  sqlCount.append(
	  	"SELECT COUNT(*) as ticketcount FROM ticket t WHERE t.org_id = " + this.getOrgId() + " ");
	
	  pst = db.prepareStatement(sqlCount.toString());
	  rs = pst.executeQuery();
	  if (rs.next()) {
		  dependencies.put(new String("tickets"), new Integer(rs.getInt("ticketcount")));
          }
	  
	  rs.close();
	  pst.close();
	  
	  sqlCount = new StringBuffer();
	  
	  //get number of docs
	  sqlCount.append(
	  	"SELECT COUNT(*) as documentcount FROM project_files pf WHERE pf.link_module_id = " + Constants.ACCOUNTS + " and pf.link_item_id = " + this.getOrgId() + " ");
	
	  pst = db.prepareStatement(sqlCount.toString());
	  rs = pst.executeQuery();
	  if (rs.next()) {
		  dependencies.put(new String("documents"), new Integer(rs.getInt("documentcount")));
          }
	  
	  rs.close();
	  pst.close();
	
	  sqlCount = new StringBuffer();
	  
	  //get number of folder records
	  sqlCount.append(
	  	"SELECT COUNT(*) as foldercount FROM custom_field_record cfr WHERE cfr.link_module_id = " + Constants.ACCOUNTS + " and cfr.link_item_id = " + this.getOrgId() + " ");
	
	  pst = db.prepareStatement(sqlCount.toString());
	  rs = pst.executeQuery();
	  if (rs.next()) {
		  dependencies.put(new String("folders"), new Integer(rs.getInt("foldercount")));
          }
	  
	  rs.close();
	  pst.close();
	  
	  return dependencies;
  }

  /**
   *  Sets the EnteredByName attribute of the Organization object
   *
   *@param  enteredByName  The new EnteredByName value
   */
  public void setEnteredByName(String enteredByName) {
    this.enteredByName = enteredByName;
  }


  /**
   *  Sets the ModifiedByName attribute of the Organization object
   *
   *@param  modifiedByName  The new ModifiedByName value
   */
  public void setModifiedByName(String modifiedByName) {
    this.modifiedByName = modifiedByName;
  }


  /**
   *  Sets the OwnerName attribute of the Organization object
   *
   *@param  ownerName  The new OwnerName value
   */
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }


  /**
   *  Sets the ErrorMessage attribute of the Organization object
   *
   *@param  tmp  The new ErrorMessage value
   */
  public void setErrorMessage(String tmp) {
    this.errorMessage = tmp;
  }


  /**
   *  Sets the types attribute of the Organization object
   *
   *@param  types  The new types value
   */
  public void setTypes(LookupList types) {
    this.types = types;
  }


  /**
   *  Sets the Owner attribute of the Organization object
   *
   *@param  owner  The new Owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }
  
  public void setOwnerId(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the Entered attribute of the Organization object
   *
   *@param  tmp  The new Entered value
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
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Organization object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Organization object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the Owner attribute of the Organization object
   *
   *@param  owner  The new Owner value
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }
  
  public void setOwnerId(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the ContractEndDate attribute of the Organization object
   *
   *@param  contractEndDate  The new ContractEndDate value
   */
  public void setContractEndDate(java.sql.Date contractEndDate) {
    this.contractEndDate = contractEndDate;
  }

public double getYTD() {
	return YTD;
}
public void setYTD(double YTD) {
	this.YTD = YTD;
}
  public String getYTDValue() {
    double value_2dp = (double) Math.round(YTD * 100.0) / 100.0;
    String toReturn = "" + value_2dp;
    if (toReturn.endsWith(".0")) {
      toReturn = toReturn.substring(0, toReturn.length() - 2);
    } 
    
    if (Integer.parseInt(toReturn) == 0) 
        toReturn = "";
	
    return toReturn;
  }
  
  public String getYTDCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(YTD);
    return amountOut;
  }
  /**
   *  Sets the ContractEndDate attribute of the Organization object
   *
   *@param  tmp  The new ContractEndDate value
   */
  public void setContractEndDate(String tmp) {
    this.contractEndDate = DateUtils.parseDateString(tmp);
  }

  public void buildRevenueYTD(Connection db, int year, int type, int ownerId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT sum(rv.amount) as s " +
        "FROM revenue rv " +
        "WHERE rv.org_id = ? AND rv.year = ? AND rv.owner = ? ");
    if (type > 0) {
	    sql.append("AND rv.type = ? ");
    }
    
    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, orgId);
    pst.setInt(++i, year);
    pst.setInt(++i, ownerId);
    if (type > 0) {
	    pst.setInt(++i, type);
    }	    
    rs = pst.executeQuery();
    if (rs.next()) {
	    this.setYTD(rs.getDouble("s"));
    } 
    System.out.println("Revenue Calc: " + pst.toString());
    rs.close();
    pst.close();
  }
  /**
   *  Sets the alertDate attribute of the Organization object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(String tmp) {
    this.alertDate = DateUtils.parseDateString(tmp);
  }


  /**
   *  Sets the Employees attribute of the Organization object
   *
   *@param  employees  The new Employees value
   */
  public void setEmployees(String employees) {
    this.employees = Integer.parseInt(employees);
  }


  /**
   *  Sets the DuplicateId attribute of the Organization object
   *
   *@param  duplicateId  The new DuplicateId value
   */
  public void setDuplicateId(int duplicateId) {
    this.duplicateId = duplicateId;
  }


  /**
   *  Sets the orgId attribute of the Organization object
   *
   *@param  tmp  The new orgId value
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
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }
  
  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = Integer.parseInt(modifiedBy);
  }


  /**
   *  Sets the AccountNumber attribute of the Organization obA  9
   *
   *@param  accountNumber  The new AccountNumber value
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }


  /**
   *  Sets the OrgId attribute of the Organization object
   *
   *@param  tmp  The new OrgId value
   */
  public void setOrgId(String tmp) {
    this.setOrgId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the Revenue attribute of the Organization object
   *
   *@param  revenue  The new Revenue value
   */
  public void setRevenue(String revenue) {
    this.revenue = Double.parseDouble(revenue);
  }


  /**
   *  Sets the Name attribute of the Organization object
   *
   *@param  tmp  The new Name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the Url attribute of the Organization object
   *
   *@param  tmp  The new Url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the Ticker attribute of the Organization object
   *
   *@param  ticker  The new Ticker value
   */
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }


  /**
   *  Sets the LastModified attribute of the Organization object
   *
   *@param  tmp  The new LastModified value
   */
  public void setLastModified(String tmp) {
    this.lastModified = tmp;
  }


  /**
   *  Sets the Notes attribute of the Organization object
   *
   *@param  tmp  The new Notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the Industry attribute of the Organization object
   *
   *@param  tmp  The new Industry value
   */
  public void setIndustry(String tmp) {
    this.industry = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Miner_only attribute of the Organization object
   *
   *@param  tmp  The new Miner_only value
   */
  public void setMiner_only(boolean tmp) {
    this.minerOnly = tmp;
  }
  
  public void setMiner_only(String tmp) {
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(tmp));
  }

  public void setMinerOnly(boolean tmp) {
    this.minerOnly = tmp;
  }
  
  public void setMinerOnly(String tmp) {
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(tmp));
  }

  /**
   *  Sets the AddressList attribute of the Organization object
   *
   *@param  tmp  The new AddressList value
   */
  public void setAddressList(OrganizationAddressList tmp) {
    this.addressList = tmp;
  }


  /**
   *  Sets the PhoneNumberList attribute of the Organization object
   *
   *@param  tmp  The new PhoneNumberList value
   */
  public void setPhoneNumberList(OrganizationPhoneNumberList tmp) {
    this.phoneNumberList = tmp;
  }

public boolean getEnabled() {
	return enabled;
}

  /**
   *  Sets the EmailAddressList attribute of the Organization object
   *
   *@param  tmp  The new EmailAddressList value
   */
  public void setEmailAddressList(OrganizationEmailAddressList tmp) {
    this.emailAddressList = tmp;
  }


  /**
   *  Sets the Enteredby attribute of the Organization object
   *
   *@param  tmp  The new Enteredby value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }
  
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Enabled attribute of the Organization object
   *
   *@param  tmp  The new Enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Enabled attribute of the Organization object
   *
   *@param  tmp  The new Enabled value
   */
  public void setEnabled(String tmp) {
    enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
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
   *  Gets the typeList attribute of the Organization object
   *
   *@return    The typeList value
   */
  public ArrayList getTypeList() {
    return typeList;
  }


  /**
   *  Gets the types attribute of the Organization object
   *
   *@return    The types value
   */
  public LookupList getTypes() {
    return types;
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
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the Modified attribute of the Organization object
   *
   *@return    The Modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the ModifiedString attribute of the Organization object
   *
   *@return    The ModifiedString value
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
   */
  public java.sql.Date getContractEndDate() {
    return contractEndDate;
  }


  /**
   *  Gets the ContractEndDateString attribute of the Organization object
   *
   *@return    The ContractEndDateString value
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
   *@return    The contractEndDateStringLongYear value
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
   *@return    The enteredStringLongYear value
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
   *@return    The asValuesArray value
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
   */
  public int getDuplicateId() {
    return duplicateId;
  }


  /**
   *  Gets the OwnerName attribute of the Organization object
   *
   *@return    The OwnerName value
   */
  public String getOwnerName() {
    return ownerName;
  }


  /**
   *  Gets the EnteredByName attribute of the Organization object
   *
   *@return    The EnteredByName value
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   *  Gets the ModifiedByName attribute of the Organization object
   *
   *@return    The ModifiedByName value
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   *  Gets the Owner attribute of the Organization object
   *
   *@return    The Owner value
   */
  public int getOwner() {
    return owner;
  }
  
  public int getOwnerId() {
    return owner;
  }


  /**
   *  Gets the AccountNumber attribute of the Organization object
   *
   *@return    The AccountNumber value
   */
  public String getAccountNumber() {
    return accountNumber;
  }


  /**
   *  Gets the Ticker attribute of the Organization object
   *
   *@return    The Ticker value
   */
  public String getTicker() {
    return ticker;
  }


  /**
   *  Gets the Revenue attribute of the Organization object
   *
   *@return    The Revenue value
   */
  public double getRevenue() {
    return revenue;
  }


  /**
   *  Gets the Employees attribute of the Organization object
   *
   *@return    The Employees value
   */
  public int getEmployees() {
    return employees;
  }


  /**
   *  Gets the ErrorMessage attribute of the Organization object
   *
   *@return    The ErrorMessage value
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   *  Gets the orgId attribute of the Organization object
   *
   *@return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the id attribute of the Organization object
   *
   *@return    The id value
   */
  public int getId() {
    return orgId;
  }


  /**
   *  Gets the Name attribute of the Organization object
   *
   *@return    The Name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the Url attribute of the Organization object
   *
   *@return    The Url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the LastModified attribute of the Organization object
   *
   *@return    The LastModified value
   */
  public String getLastModified() {
    return lastModified;
  }


  /**
   *  Gets the Notes attribute of the Organization object
   *
   *@return    The Notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the Industry attribute of the Organization object
   *
   *@return    The Industry value
   */
  public int getIndustry() {
    return industry;
  }


  /**
   *  Gets the IndustryName attribute of the Organization object
   *
   *@return    The IndustryName value
   */
  public String getIndustryName() {
    return industryName;
  }


  /**
   *  Gets the PhoneNumber attribute of the Organization object
   *
   *@param  thisType  Description of Parameter
   *@return           The PhoneNumber value
   */
  public String getPhoneNumber(String thisType) {
    return phoneNumberList.getPhoneNumber(thisType);
  }


  /**
   *  Gets the EmailAddress attribute of the Organization object
   *
   *@param  thisType  Description of Parameter
   *@return           The EmailAddress value
   */
  public String getEmailAddress(String thisType) {
    return emailAddressList.getEmailAddress(thisType);
  }


  /**
   *  Gets the Address attribute of the Organization object
   *
   *@param  thisType  Description of Parameter
   *@return           The Address value
   */
  public Address getAddress(String thisType) {
    return addressList.getAddress(thisType);
  }


  /**
   *  Gets the Enteredby attribute of the Organization object
   *
   *@return    The Enteredby value
   */
  public int getEnteredby() {
    return enteredBy;
  }


  public boolean getMiner_only() {
    return minerOnly;
  }
  public boolean getMinerOnly() {
    return minerOnly;
  }

  /**
   *  Gets the EnteredBy attribute of the Organization object
   *
   *@return    The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the Organization object
   *
   *@return    The ModifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the AddressList attribute of the Organization object
   *
   *@return    The AddressList value
   */
  public OrganizationAddressList getAddressList() {
    return addressList;
  }


  /**
   *  Gets the PhoneNumberList attribute of the Organization object
   *
   *@return    The PhoneNumberList value
   */
  public OrganizationPhoneNumberList getPhoneNumberList() {
    return phoneNumberList;
  }


  /**
   *  Gets the EmailAddressList attribute of the Organization object
   *
   *@return    The EmailAddressList value
   */
  public OrganizationEmailAddressList getEmailAddressList() {
    return emailAddressList;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildTypes(Connection db) throws SQLException {
    types.setSelectSize(3);
    types.setMultiple(true);

    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT atl.*, la.description as type_name " +
        "FROM account_type_levels atl " +
        "LEFT JOIN lookup_account_types la ON (atl.type_id = la.code) " +
        "WHERE atl.org_id = " + orgId + " ORDER BY atl.level ");

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());

    while (rs.next()) {
      types.appendItem(rs.getInt("type_id"), rs.getString("type_name"));
    }

    rs.close();
    st.close();
  }
  

  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  type_id           Description of Parameter
   *@param  level             Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
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
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean resetType(Connection db) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified");
    }
    Statement st = db.createStatement();
    st.executeUpdate(
      "DELETE FROM account_type_levels " +
      "WHERE id = " + this.getOrgId());
    st.close();
    return true;
  }
  
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
	
	if (resultCount == 1) 
		success = true;
	
	return success;
  }
  
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
	
	if (resultCount == 1) 
		success = true;
	
	return success;
  }

  /**
   *  Description of the Method
   */
  public void listTypes() {
    for (int i = 0; i < typeList.size(); i++) {
      String val = (String) typeList.get(i);
      //System.out.println(val);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  checkName         Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
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
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
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
      sql.append("notes, employees, revenue, ticker_symbol, account_number, ");
              if (entered != null) {
                      sql.append("entered, ");
              }
              if (modified != null) {
                      sql.append("modified, ");
              }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
              if (entered != null) {
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
      if (owner > -1) {
	      pst.setInt(++i, this.getOwner());
      } else {
	      pst.setNull(++i, java.sql.Types.INTEGER);
      }
      pst.setInt(++i, this.getDuplicateId());
      pst.setString(++i, this.getNotes());
      pst.setInt(++i, this.getEmployees());
      pst.setDouble(++i, this.getRevenue());
      pst.setString(++i, this.getTicker());
      pst.setString(++i, this.getAccountNumber());
      
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

      orgId = DatabaseUtils.getCurrVal(db, "organization_org_id_seq");

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
        
     sql.append("duplicate_id = ?, contract_end = ?, alertdate = ?, alert = ? " +
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
    
    //Remove all account types, add new list
    if (this.getMinerOnly() == false && typeList != null) {
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  baseFilePath      Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    if (this.getOrgId() == -1) {
      throw new SQLException("Organization ID not specified.");
    }

    try {
      db.setAutoCommit(false);

      if (contactDelete) {
	      ContactList contactList = new ContactList();
	      contactList.setOrgId(this.getOrgId());
	      contactList.buildList(db);
	      contactList.delete(db);
	      contactList = null;
      }

      OpportunityList opportunityList = new OpportunityList();
      opportunityList.setOrgId(this.getOrgId());
      opportunityList.buildList(db);
      opportunityList.delete(db);
      opportunityList = null;

      TicketList ticketList = new TicketList();
      ticketList.setOrgId(this.getOrgId());
      ticketList.buildList(db);
      ticketList.delete(db);
      ticketList = null;

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
      
      this.resetType(db);
      
      db.commit();
    } catch (SQLException e) {
      e.printStackTrace(System.out);
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
   *@exception  SQLException  Description of Exception
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
   *  Description of the Method
   *
   *@return    Description of the Returned Value
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
    contractEndDate = rs.getDate("contract_end");
    alertDate = rs.getDate("alertdate");
    alertText = rs.getString("alert");

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
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
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


