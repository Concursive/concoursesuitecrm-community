//Copyright 2001-2002 Dark Horse Ventures

package org.aspcfs.modules.contacts.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.accounts.base.Organization;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 *  Represents a Contact in CFS
 *
 *@author     mrajkowski
 *@created    August 29, 2001
 *@version    $Id$
 */
public class Contact extends GenericBean {

  /**
   *  Type 1 in the database refers to an Employee
   */
  public final static int EMPLOYEE_TYPE = 1;

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
  private String site = "";
  private String notes = "";
  private String imName = "";
  private int imService = -1;
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

  private ContactEmailAddressList emailAddressList = new ContactEmailAddressList();
  private ContactPhoneNumberList phoneNumberList = new ContactPhoneNumberList();
  private ContactAddressList addressList = new ContactAddressList();
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

  
  /**
   *  Constructor for the Contact object
   *
   *@since    1.1
   */
  public Contact() { }


  /**
   *  Constructor for the Contact object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public Contact(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the Employee object, populates all attributes by
   *  performing a SQL query based on the employeeId parameter
   *
   *@param  db                Description of Parameter
   *@param  contactId         Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public Contact(Connection db, String contactId) throws SQLException {
    queryRecord(db, Integer.parseInt(contactId));
  }


  /**
   *  Constructor for the Contact object
   *
   *@param  db                Description of the Parameter
   *@param  contactId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Contact(Connection db, int contactId) throws SQLException {
    queryRecord(db, contactId);
  }


  /**
   *  Description of the Method
   *
   *@param  obj  Description of the Parameter
   *@return      Description of the Return Value
   */
  public boolean equals(Object obj) {
    if (this.getId() == ((Contact) obj).getId()) {
      return true;
    }

    return false;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void build(Connection db) throws SQLException {
    queryRecord(db, this.getId());
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  contactId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
      throw new SQLException("Contact record not found.");
    }
    if (buildDetails) {
      phoneNumberList.setContactId(this.getId());
      phoneNumberList.buildList(db);
      addressList.setContactId(this.getId());
      addressList.buildList(db);
      emailAddressList.setContactId(this.getId());
      emailAddressList.buildList(db);
    }
    if (buildTypes) {
      buildTypes(db);
    }
  }


  /**
   *  Sets the OwnerName attribute of the Contact object
   *
   *@param  ownerName  The new OwnerName value
   *@since
   */
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }


  /**
   *  Sets the hasEnabledOwnerAccount attribute of the Contact object
   *
   *@param  hasEnabledOwnerAccount  The new hasEnabledOwnerAccount value
   */
  public void setHasEnabledOwnerAccount(boolean hasEnabledOwnerAccount) {
    this.hasEnabledOwnerAccount = hasEnabledOwnerAccount;
  }


  /**
   *  Sets the employee attribute of the Contact object
   *
   *@param  employee  The new employee value
   */
  public void setEmployee(boolean employee) {
    this.employee = employee;
  }


  /**
   *  Sets the accessType attribute of the Contact object
   *
   *@param  accessType  The new accessType value
   */
  public void setAccessType(int accessType) {
    this.accessType = accessType;
  }


  /**
   *  Sets the accessType attribute of the Contact object
   *
   *@param  accessType  The new accessType value
   */
  public void setAccessType(String accessType) {
    this.accessType = Integer.parseInt(accessType);
  }


  /**
   *  Sets the statusId attribute of the Contact object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the Contact object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the importId attribute of the Contact object
   *
   *@param  tmp  The new importId value
   */
  public void setImportId(int tmp) {
    this.importId = tmp;
  }


  /**
   *  Sets the importId attribute of the Contact object
   *
   *@param  tmp  The new importId value
   */
  public void setImportId(String tmp) {
    this.importId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the statusId attribute of the Contact object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the importId attribute of the Contact object
   *
   *@return    The importId value
   */
  public int getImportId() {
    return importId;
  }


  /**
   *  Gets the accessType attribute of the Contact object
   *
   *@return    The accessType value
   */
  public int getAccessType() {
    return accessType;
  }


  /**
   *  Gets the accessType attribute of the Contact object
   *
   *@return    The accessType value
   */
  public String getAccessTypeString() {
    return String.valueOf(accessType);
  }


  /**
   *  Gets the employee attribute of the Contact object
   *
   *@return    The employee value
   */
  public boolean getEmployee() {
    return employee;
  }


  /**
   *  Gets the hasEnabledOwnerAccount attribute of the Contact object
   *
   *@return    The hasEnabledOwnerAccount value
   */
  public boolean getHasEnabledOwnerAccount() {
    return hasEnabledOwnerAccount;
  }


  /**
   *  Gets the url attribute of the Contact object
   *
   *@return    The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the hasOpportunities attribute of the Contact object
   *
   *@return    The hasOpportunities value
   */
  public boolean getHasOpportunities() {
    return hasOpportunities;
  }


  /**
   *  Sets the hasOpportunities attribute of the Contact object
   *
   *@param  hasOpportunities  The new hasOpportunities value
   */
  public void setHasOpportunities(boolean hasOpportunities) {
    this.hasOpportunities = hasOpportunities;
  }


  /**
   *  Sets the url attribute of the Contact object
   *
   *@param  url  The new url value
   */
  public void setUrl(String url) {
    this.url = url;
  }


  /**
   *  Sets the primaryContact attribute of the Contact object
   *
   *@param  primaryContact  The new primaryContact value
   */
  public void setPrimaryContact(boolean primaryContact) {
    this.primaryContact = primaryContact;
  }


  /**
   *  Sets the primaryContact attribute of the Contact object
   *
   *@param  tmp  The new primaryContact value
   */
  public void setPrimaryContact(String tmp) {
    this.primaryContact = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the typeList attribute of the Contact object
   *
   *@param  typeList  The new typeList value
   */
  public void setTypeList(ArrayList typeList) {
    this.typeList = typeList;
  }


  /**
   *  Gets the typeList attribute of the Contact object
   *
   *@return    The typeList value
   */
  public ArrayList getTypeList() {
    return typeList;
  }


  /**
   *  Gets the primaryContact attribute of the Contact object
   *
   *@return    The primaryContact value
   */
  public boolean getPrimaryContact() {
    return primaryContact;
  }


  /**
   *  Returns a name for the contact checking (last,first) name and company name
   *  in that order
   *
   *@return    The validName value
   */
  public String getValidName() {
    String validName = StringUtils.toString(getNameLastFirst());
    if ("".equals(validName) && !"".equals(StringUtils.toString(company))) {
      validName = company;
    }
    return validName;
  }


  /**
   *  Gets the fullNameAbbr attribute of the Contact object
   *
   *@return    The fullNameAbbr value
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
   *  Gets the custom1 attribute of the Contact object
   *
   *@return    The custom1 value
   */
  public int getCustom1() {
    return custom1;
  }


  /**
   *  Sets the custom1 attribute of the Contact object
   *
   *@param  custom1  The new custom1 value
   */
  public void setCustom1(int custom1) {
    this.custom1 = custom1;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasEnabledAccount() {
    return hasEnabledAccount;
  }


  /**
   *  Sets the hasEnabledAccount attribute of the Contact object
   *
   *@param  hasEnabledAccount  The new hasEnabledAccount value
   */
  public void setHasEnabledAccount(boolean hasEnabledAccount) {
    this.hasEnabledAccount = hasEnabledAccount;
  }


  /**
   *  Sets the custom1 attribute of the Contact object
   *
   *@param  custom1  The new custom1 value
   */
  public void setCustom1(String custom1) {
    this.custom1 = Integer.parseInt(custom1);
  }


  /**
   *  Sets the buildDetails attribute of the Contact object
   *
   *@param  tmp  The new buildDetails value
   */
  public void setBuildDetails(boolean tmp) {
    this.buildDetails = tmp;
  }


  /**
   *  Sets the buildTypes attribute of the Contact object
   *
   *@param  tmp  The new buildTypes value
   */
  public void setBuildTypes(boolean tmp) {
    this.buildTypes = tmp;
  }


  /**
   *  Sets the buildTypes attribute of the Contact object
   *
   *@param  tmp  The new buildTypes value
   */
  public void setBuildTypes(String tmp) {
    this.buildTypes = DatabaseUtils.parseBoolean(tmp);
  }



  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
   *  Sets the Id attribute of the Contact object
   *
   *@param  tmp  The new Id value
   *@since       1.1
   */
  public void setId(int tmp) {
    this.id = tmp;
    addressList.setContactId(tmp);
    phoneNumberList.setContactId(tmp);
    emailAddressList.setContactId(tmp);
  }


  /**
   *  Sets the entered attribute of the Contact object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the Contact object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Contact object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Contact object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the Owner attribute of the Opportunity object
   *
   *@param  owner  The new Owner value
   *@since
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the Owner attribute of the Opportunity object
   *
   *@param  owner  The new Owner value
   *@since
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the Id attribute of the Contact object
   *
   *@param  tmp  The new Id value
   *@since       1.1
   */
  public void setId(String tmp) {
    this.setId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the OrgId attribute of the Contact object
   *
   *@param  tmp  The new OrgId value
   *@since       1.1
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the OrgId attribute of the Contact object
   *
   *@param  tmp  The new OrgId value
   *@since       1.2
   */
  public void setOrgId(String tmp) {
    if (tmp != null) {
      this.orgId = Integer.parseInt(tmp);
    }
  }


  /**
   *  Sets the ModifiedByName attribute of the Contact object
   *
   *@param  modifiedByName  The new ModifiedByName value
   *@since
   */
  public void setModifiedByName(String modifiedByName) {
    this.modifiedByName = modifiedByName;
  }


  /**
   *  Sets the NameSalutation attribute of the Contact object
   *
   *@param  tmp  The new NameSalutation value
   *@since       1.1
   */
  public void setNameSalutation(String tmp) {
    this.nameSalutation = tmp;
  }


  /**
   *  Sets the NameFirst attribute of the Contact object
   *
   *@param  tmp  The new NameFirst value
   *@since       1.1
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Sets the EnteredByName attribute of the Contact object
   *
   *@param  enteredByName  The new EnteredByName value
   *@since
   */
  public void setEnteredByName(String enteredByName) {
    this.enteredByName = enteredByName;
  }


  /**
   *  Sets the NameMiddle attribute of the Contact object
   *
   *@param  tmp  The new NameMiddle value
   *@since       1.1
   */
  public void setNameMiddle(String tmp) {
    this.nameMiddle = tmp;
  }


  /**
   *  Sets the NameLast attribute of the Contact object
   *
   *@param  tmp  The new NameLast value
   *@since       1.1
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   *  Sets the NameSuffix attribute of the Contact object
   *
   *@param  tmp  The new NameSuffix value
   *@since       1.1
   */
  public void setNameSuffix(String tmp) {
    this.nameSuffix = tmp;
  }


  /**
   *  Sets the Company attribute of the Contact object
   *
   *@param  tmp  The new Company value
   *@since       1.34
   */
  public void setCompany(String tmp) {
    this.company = tmp;
  }


  /**
   *  Sets the Title attribute of the Contact object
   *
   *@param  tmp  The new Title value
   *@since       1.1
   */
  public void setTitle(String tmp) {
    if (tmp != null && tmp.length() > 80) {
      tmp = tmp.substring(0, 79);
    }
    this.title = tmp;
  }


  /**
   *  Sets the DepartmentName attribute of the Contact object
   *
   *@param  tmp  The new DepartmentName value
   *@since       1.1
   */
  public void setDepartmentName(String tmp) {
    this.departmentName = tmp;
  }


  /**
   *  Sets the Department attribute of the Contact object
   *
   *@param  tmp  The new Department value
   *@since       1.1
   */
  public void setDepartment(int tmp) {
    this.department = tmp;
  }


  /**
   *  Sets the Department attribute of the Contact object
   *
   *@param  tmp  The new Department value
   *@since       1.1
   */
  public void setDepartment(String tmp) {
    this.department = Integer.parseInt(tmp);
  }



  /**
   *  Sets the EmailAddresses attribute of the Contact object
   *
   *@param  tmp  The new EmailAddresses value
   *@since       1.1
   */
  public void setEmailAddressList(ContactEmailAddressList tmp) {
    this.emailAddressList = tmp;
  }


  /**
   *  Sets the ContactPhoneNumberList attribute of the Contact object
   *
   *@param  tmp  The new ContactPhoneNumberList value
   *@since       1.13
   */
  public void setPhoneNumberList(ContactPhoneNumberList tmp) {
    this.phoneNumberList = tmp;
  }


  /**
   *  Gets the orgEnabled attribute of the Contact object
   *
   *@return    The orgEnabled value
   */
  public boolean getOrgEnabled() {
    return orgEnabled;
  }


  /**
   *  Sets the orgEnabled attribute of the Contact object
   *
   *@param  orgEnabled  The new orgEnabled value
   */
  public void setOrgEnabled(boolean orgEnabled) {
    this.orgEnabled = orgEnabled;
  }


  /**
   *  Sets the excludedFromCampaign attribute of the Contact object
   *
   *@param  excludedFromCampaign  The new excludedFromCampaign value
   */
  public void setExcludedFromCampaign(boolean excludedFromCampaign) {
    this.excludedFromCampaign = excludedFromCampaign;
  }


  /**
   *  Sets the AddressList attribute of the Contact object
   *
   *@param  tmp  The new AddressList value
   *@since       1.1
   */
  public void setAddressList(ContactAddressList tmp) {
    this.addressList = tmp;
  }


  /**
   *  Sets the ImName attribute of the Contact object
   *
   *@param  tmp  The new ImName value
   *@since       1.1
   */
  public void setImName(String tmp) {
    this.imName = tmp;
  }


  /**
   *  Sets the ImService attribute of the Contact object
   *
   *@param  tmp  The new ImService value
   *@since       1.1
   */
  public void setImService(int tmp) {
    this.imService = tmp;
  }


  /**
   *  Sets the ImService attribute of the Contact object
   *
   *@param  tmp  The new ImService value
   *@since       1.2
   */
  public void setImService(String tmp) {
    this.imService = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Notes attribute of the Contact object
   *
   *@param  tmp  The new Notes value
   *@since       1.1
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the Site attribute of the Contact object
   *
   *@param  tmp  The new Site value
   *@since       1.1
   */
  public void setSite(String tmp) {
    this.site = tmp;
  }



  /**
   *  Sets the EmploymentType attribute of the Contact object
   *
   *@param  tmp  The new EmploymentType value
   *@since       1.1
   */
  public void setEmploymentType(int tmp) {
    this.employmentType = tmp;
  }


  /**
   *  Sets the EmploymentType attribute of the Contact object
   *
   *@param  tmp  The new EmploymentType value
   *@since       1.2
   */
  public void setEmploymentType(String tmp) {
    this.employmentType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Locale attribute of the Contact object
   *
   *@param  tmp  The new Locale value
   *@since       1.1
   */
  public void setLocale(int tmp) {
    this.locale = tmp;
  }


  /**
   *  Sets the Locale attribute of the Contact object
   *
   *@param  tmp  The new Locale value
   *@since       1.2
   */
  public void setLocale(String tmp) {
    this.locale = Integer.parseInt(tmp);
  }


  /**
   *  Sets the EmployeeId attribute of the Contact object
   *
   *@param  tmp  The new EmployeeId value
   *@since       1.1
   */
  public void setEmployeeId(String tmp) {
    this.employeeId = tmp;
  }



  /**
   *  Sets the StartOfDay attribute of the Contact object
   *
   *@param  tmp  The new StartOfDay value
   *@since       1.1
   */
  public void setStartOfDay(String tmp) {
    this.startOfDay = tmp;
  }


  /**
   *  Sets the EndOfDay attribute of the Contact object
   *
   *@param  tmp  The new EndOfDay value
   *@since       1.1
   */
  public void setEndOfDay(String tmp) {
    this.endOfDay = tmp;
  }


  /**
   *  Sets the Enabled attribute of the Contact object
   *
   *@param  tmp  The new Enabled value
   *@since       1.2
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Enabled attribute of the Contact object
   *
   *@param  tmp  The new Enabled value
   *@since       1.2
   */
  public void setEnabled(String tmp) {
    enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the EnteredBy attribute of the Contact object
   *
   *@param  tmp  The new EnteredBy value
   *@since       1.12
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Contact object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ModifiedBy attribute of the Contact object
   *
   *@param  tmp  The new ModifiedBy value
   *@since       1.12
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Contact object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the HasAccount attribute of the Contact object
   *
   *@param  tmp  The new HasAccount value
   *@since       1.20
   */
  public void setHasAccount(boolean tmp) {
    this.hasAccount = tmp;
  }



  public boolean isApproved(){
    return (statusId == Import.PROCESSED_UNAPPROVED ? false : true);
  }
  
  
  /**
   *  Since dynamic fields cannot be auto-populated, passing the request to this
   *  method will populate the indicated fields.
   *
   *@param  request  The new RequestItems value
   *@since           1.15
   */
  public void setRequestItems(ActionContext context) {
    phoneNumberList = new ContactPhoneNumberList(context);
    addressList = new ContactAddressList(context.getRequest());
    emailAddressList = new ContactEmailAddressList(context.getRequest());
  }


  /**
   *  Sets the typeList attribute of the Contact object
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


  /**
   *  Adds a feature to the Type attribute of the Contact object
   *
   *@param  typeId  The feature to be added to the Type attribute
   */
  public void addType(int typeId) {
    if (typeList == null) {
      typeList = new ArrayList();
    }
    typeList.add(String.valueOf(typeId));
  }


  /**
   *  Adds a feature to the Type attribute of the Contact object
   *
   *@param  typeId  The feature to be added to the Type attribute
   */
  public void addType(String typeId) {
    if (typeList == null) {
      typeList = new ArrayList();
    }
    typeList.add(typeId);
  }


  /**
   *  Gets the typesNameString attribute of the Contact object
   *
   *@return    The typesNameString value
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
   *  Description of the Method
   *
   *@param  type  Description of the Parameter
   *@return       Description of the Return Value
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
   *  Gets the entered attribute of the Contact object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the Contact object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the user_id attribute of the Contact object
   *
   *@return    The user_id value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Sets the userId attribute of the Contact object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(int tmp) {
    userId = tmp;
  }


  /**
   *  Sets the userId attribute of the Contact object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(String tmp) {
    userId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the types attribute of the Contact object
   *
   *@param  types  The new types value
   */
  public void setTypes(LookupList types) {
    this.types = types;
  }


  /**
   *  Gets the types attribute of the Contact object
   *
   *@return    The types value
   */
  public LookupList getTypes() {
    return types;
  }


  /**
   *  Gets the modifiedString attribute of the Contact object
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
   *  Gets the enteredString attribute of the Contact object
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
   *  Gets the ModifiedByName attribute of the Contact object
   *
   *@return    The ModifiedByName value
   *@since
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   *  Gets the EnteredByName attribute of the Contact object
   *
   *@return    The EnteredByName value
   *@since
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   *  Gets the OwnerName attribute of the Contact object
   *
   *@return    The OwnerName value
   *@since
   */
  public String getOwnerName() {
    return ownerName;
  }


  /**
   *  Gets the Owner attribute of the Opportunity object
   *
   *@return    The Owner value
   *@since
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the ownerString attribute of the Contact object
   *
   *@return    The ownerString value
   */
  public String getOwnerString() {
    return String.valueOf(owner);
  }


  /**
   *  Gets the Id attribute of the Contact object
   *
   *@return    The Id value
   *@since     1.1
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the OrgId attribute of the Contact object
   *
   *@return    The OrgId value
   *@since     1.1
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Sets the orgName attribute of the Contact object
   *
   *@param  orgName  The new orgName value
   */
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }


  /**
   *  Gets the OrgName attribute of the Contact object
   *
   *@return    The OrgName value
   *@since     1.28
   */
  public String getOrgName() {
    return orgName;
  }


  /**
   *  Gets the NameSalutation attribute of the Contact object
   *
   *@return    The NameSalutation value
   *@since     1.1
   */
  public String getNameSalutation() {
    return nameSalutation;
  }


  /**
   *  Gets the NameFirst attribute of the Contact object
   *
   *@return    The NameFirst value
   *@since     1.1
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Gets the NameMiddle attribute of the Contact object
   *
   *@return    The NameMiddle value
   *@since     1.1
   */
  public String getNameMiddle() {
    return nameMiddle;
  }


  /**
   *  Gets the NameLast attribute of the Contact object
   *
   *@return    The NameLast value
   *@since     1.1
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Gets the NameSuffix attribute of the Contact object
   *
   *@return    The NameSuffix value
   *@since     1.1
   */
  public String getNameSuffix() {
    return nameSuffix;
  }


  /**
   *  Gets the NameFull attribute of the Contact object
   *
   *@return    The NameFull value
   *@since     1.33
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
   *  Gets the NameFirstLast attribute of the Contact object
   *
   *@return    The NameFirstLast value
   *@since
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
   *  Gets the nameFirstInitialLast attribute of the Contact object
   *
   *@return    The nameFirstInitialLast value
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
   *  Gets the NameLastFirst attribute of the Contact object
   *
   *@return    The NameLastFirst value
   *@since
   */
  public String getNameLastFirst() {
    return Contact.getNameLastFirst(nameLast, nameFirst);
  }


  /**
   *  Gets the Company attribute of the Contact object
   *
   *@return    The Company value
   *@since     1.34
   */

  public String getCompany() {
    if (company == null || company.trim().equals("")) {
      return orgName;
    } else {
      return company;
    }
  }


  /**
   *  Gets the affiliation attribute of the Contact object
   *
   *@return    The affiliation value
   */
  public String getAffiliation() {
    if (orgId > -1) {
      return orgName;
    } else {
      return company;
    }
  }


  /**
   *  Gets the companyOnly attribute of the Contact object
   *
   *@return    The companyOnly value
   */
  public String getCompanyOnly() {
    return company;
  }


  /**
   *  Gets the Title attribute of the Contact object
   *
   *@return    The Title value
   *@since     1.1
   */
  public String getTitle() {
    return title;
  }


  /**
   *  Gets the DepartmentName attribute of the Contact object
   *
   *@return    The DepartmentName value
   *@since     1.1
   */
  public String getDepartmentName() {
    return departmentName;
  }


  /**
   *  Gets the Department attribute of the Contact object
   *
   *@return    The Department value
   *@since     1.1
   */
  public int getDepartment() {
    return department;
  }


  /**
   *  Gets the EmailAddresses attribute of the Contact object
   *
   *@return    The EmailAddresses value
   *@since     1.1
   */
  public ContactEmailAddressList getEmailAddressList() {
    return emailAddressList;
  }


  /**
   *  Gets the ContactPhoneNumberList attribute of the Contact object
   *
   *@param  thisType  Description of Parameter
   *@return           The PhoneNumber value
   *@since            1.13
   */
  public String getPhoneNumber(String thisType) {
    return phoneNumberList.getPhoneNumber(thisType);
  }


  /**
   *  Description of the Method
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
  }


  /**
   *  Gets the phoneNumber attribute of the Contact object
   *
   *@param  position  Description of the Parameter
   *@return           The phoneNumber value
   */
  public String getPhoneNumber(int position) {
    return phoneNumberList.getPhoneNumber(position);
  }


  /**
   *  Gets the EmailAddress attribute of the Contact object
   *
   *@param  thisType  Description of Parameter
   *@return           The EmailAddress value
   *@since            1.10
   */
  public String getEmailAddress(String thisType) {
    return emailAddressList.getEmailAddress(thisType);
  }


  /**
   *  Gets the EmailAddressTag attribute of the Contact object
   *
   *@param  thisType    Description of Parameter
   *@param  linkText    Description of Parameter
   *@param  noLinkText  Description of Parameter
   *@return             The EmailAddressTag value
   *@since              1.50
   */
  public String getEmailAddressTag(String thisType, String linkText, String noLinkText) {
    String tmpAddress = emailAddressList.getEmailAddress(thisType);
    if (tmpAddress != null && !tmpAddress.equals("")) {
      return "<a href=\"mailto:" + this.getEmailAddress(thisType) + "\">" + linkText + "</a>";
    } else {
      return noLinkText;
    }
  }


  /**
   *  Gets the EmailAddress attribute of the Contact object using the specified
   *  position in the emailAddressList
   *
   *@param  thisPosition  Description of Parameter
   *@return               The EmailAddress value
   *@since                1.24
   */
  public String getEmailAddress(int thisPosition) {
    return emailAddressList.getEmailAddress(thisPosition);
  }


  /**
   *  Gets the EmailAddressTypeId attribute of the Contact object
   *
   *@param  thisPosition  Description of Parameter
   *@return               The EmailAddressTypeId value
   *@since                1.24
   */
  public int getEmailAddressTypeId(int thisPosition) {
    return emailAddressList.getEmailAddressTypeId(thisPosition);
  }


  /**
   *  Gets the Address attribute of the Contact object
   *
   *@param  thisType  Description of Parameter
   *@return           The Address value
   *@since            1.1
   */
  public Address getAddress(String thisType) {
    return addressList.getAddress(thisType);
  }


  /**
   *  Gets the ImName attribute of the Contact object
   *
   *@return    The ImName value
   *@since     1.1
   */
  public String getImName() {
    return imName;
  }


  /**
   *  Gets the ImService attribute of the Contact object
   *
   *@return    The ImService value
   *@since     1.1
   */
  public int getImService() {
    return imService;
  }


  /**
   *  Gets the Addresses attribute of the Contact object
   *
   *@return    The Addresses value
   *@since     1.1
   */
  public ContactAddressList getAddressList() {
    return addressList;
  }


  /**
   *  ??????V$ Gets the Notes attribute of the Contact object
   *
   *@return    The Notes value
   *@since     1.1
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the Site attribute of the Contact object
   *
   *@return    The Site value
   *@since     1.1
   */
  public String getSite() {
    return site;
  }



  /**
   *  Gets the EmploymentType attribute of the Contact object
   *
   *@return    The EmploymentType value
   *@since     1.1
   */
  public int getEmploymentType() {
    return employmentType;
  }


  /**
   *  Gets the Locale attribute of the Contact object
   *
   *@return    The Locale value
   *@since     1.1
   */
  public int getLocale() {
    return locale;
  }


  /**
   *  Gets the EmployeeId attribute of the Contact object
   *
   *@return    The EmployeeId value
   *@since     1.1
   */
  public String getEmployeeId() {
    return employeeId;
  }



  /**
   *  Gets the StartOfDay attribute of the Contact object
   *
   *@return    The StartOfDay value
   *@since     1.esn'
   */
  public String getStartOfDay() {
    return startOfDay;
  }


  /**
   *  Gets the EndOfDay attribute of the Contact object
   *
   *@return    The EndOfDay value
   *@since     1.1
   */
  public String getEndOfDay() {
    return endOfDay;
  }


  /**
   *  Gets the Enabled attribute of the Contact object
   *
   *@return    The Enabled value
   *@since     1.2
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the PhoneNumberList attribute of the Contact object
   *
   *@return    The PhoneNumberList value
   *@since     1.13
   */
  public ContactPhoneNumberList getPhoneNumberList() {
    return phoneNumberList;
  }


  /**
   *  Gets the EnteredBy attribute of the Contact object
   *
   *@return    The EnteredBy value
   *@since     1.1
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the Contact object
   *
   *@return    The ModifiedBy value
   *@since     1.1
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean excludedFromCampaign() {
    return getExcludedFromCampaign();
  }


  /**
   *  Gets the excludedFromCampaign attribute of the Contact object
   *
   *@return    The excludedFromCampaign value
   */
  public boolean getExcludedFromCampaign() {
    return excludedFromCampaign;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  campaignId        Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
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
   *  Returns whether or not this Contact has a User Account in the system
   *
   *@return    Description of the Returned Value
   *@since     1.10
   */
  public boolean hasAccount() {
    return hasAccount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildPhoneNumberList(Connection db) throws SQLException {
    phoneNumberList.setContactId(this.getId());
    phoneNumberList.buildList(db);
  }
  
  
  /**
   *  Inserts this object into the database, and populates this Id. For
   *  maintenance, only the required fields are inserted, then an update is
   *  executed to finish the record.
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }
    StringBuffer sql = new StringBuffer();
    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      sql.append(
          "INSERT INTO contact " +
          "(user_id, namefirst, namelast, owner, primary_contact, org_name, access_type, ");

      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      if (employee) {
        sql.append("employee, ");
      }
      if (statusId > -1) {
        sql.append("status_id, ");
      }
      if (importId > -1) {
        sql.append("import_id, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ");

      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      if (employee) {
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
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      if (employee) {
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
      id = DatabaseUtils.getCurrVal(db, "contact_contact_id_seq");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Contact-> ContactID: " + this.getId());
      }

      //Insert the phone numbers if there are any
      Iterator iphone = phoneNumberList.iterator();
      while (iphone.hasNext()) {
        ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber) iphone.next();
        thisPhoneNumber.process(db, id, this.getEnteredBy(), this.getModifiedBy());
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
        thisEmailAddress.process(db, id, this.getEnteredBy(), this.getModifiedBy());
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
   *  Update the contact's information
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;
    if (!isValid(db)) {
      return -1;
    }
    try {
      db.setAutoCommit(false);
      resultCount = this.update(db, false);
      if (this.getPrimaryContact()) {
        Organization thisOrg = new Organization(db, this.getOrgId());
        thisOrg.setNameFirst(this.getNameFirst());
        thisOrg.setNameLast(this.getNameLast());
        thisOrg.setNameMiddle(this.getNameMiddle());
        thisOrg.setName(thisOrg.getNameLastFirstMiddle());
        thisOrg.update(db);
      }

      //Process the phone numbers if there are any
      Iterator iphone = phoneNumberList.iterator();
      while (iphone.hasNext()) {
        ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber) iphone.next();
        thisPhoneNumber.process(db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the addresses if there are any
      Iterator iaddress = addressList.iterator();
      while (iaddress.hasNext()) {
        ContactAddress thisAddress = (ContactAddress) iaddress.next();
        thisAddress.process(db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
      }

      //Insert the email addresses if there are any
      Iterator iemail = emailAddressList.iterator();
      while (iemail.hasNext()) {
        ContactEmailAddress thisEmailAddress = (ContactEmailAddress) iemail.next();
        thisEmailAddress.process(db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
      }

      db.commit();
    } catch (Exception e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return resultCount;
  }


  /**
   *  Delete the current object from the database
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public boolean delete(Connection db) throws SQLException {
    boolean hasAccess = false;
    boolean isEnabled = false;

    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM access " +
        "WHERE contact_id = ?");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      hasAccess = true;
      isEnabled = rs.getBoolean("enabled");
    }
    rs.close();
    pst.close();

    if (hasAccess && isEnabled) {
      errors.put("actionError", "This contact has an active user account and could not be deleted.");
      return false;
    } else if ((hasAccess && !isEnabled) || hasRelatedRecords(db)) {
      errors.put("actionError", "Contact disabled from view, since it has a related user account");
      this.disable(db);
      return true;
    } else {
      try {
        db.setAutoCommit(false);

        CustomFieldRecordList folderList = new CustomFieldRecordList();
        folderList.setLinkModuleId(Constants.CONTACTS);
        folderList.setLinkItemId(this.getId());
        folderList.buildList(db);
        folderList.delete(db);
        folderList = null;

        CallList callList = new CallList();
        callList.setContactId(this.getId());
        callList.buildList(db);
        callList.delete(db);
        callList = null;

        pst = db.prepareStatement(
            "DELETE FROM contact_phone " +
            "WHERE contact_id = ?");
        pst.setInt(1, this.getId());
        pst.execute();
        pst.close();

        pst = db.prepareStatement(
            "DELETE FROM contact_emailaddress " +
            "WHERE contact_id = ?");
        pst.setInt(1, this.getId());
        pst.execute();
        pst.close();

        pst = db.prepareStatement(
            "DELETE FROM contact_address " +
            "WHERE contact_id = ? ");
        pst.setInt(1, this.getId());
        pst.execute();
        pst.close();

        // For history, keep this contact if they previously received a comm. message
        if (RecipientList.retrieveRecordCount(db, Constants.CONTACTS, this.getId()) > 0) {
          errors.put("actionError", "Contact disabled from view, since it has related message records");
          this.disable(db);
          db.commit();
          return true;
        }

        // If we're not keeping this contact, get rid of some more data
        // TODO: Use the ExcludedRecipientList class when it exists
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
        
        // finally, delete the contact
        pst = db.prepareStatement(
            "DELETE FROM contact " +
            "WHERE contact_id = ?");
        pst.setInt(1, this.getId());
        pst.execute();
        pst.close();
        db.commit();
      } catch (SQLException e) {
        db.rollback();
        System.out.println(e.toString());
      } finally {
        db.setAutoCommit(true);
      }
      return true;
    }
  }


  /**
   *  Resets the types for this Contact
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean resetType(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Contact ID not specified");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement("DELETE FROM contact_type_levels WHERE contact_id = ? ");
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
   *  Inserts Type of Contact.
   *
   *@param  db                Description of the Parameter
   *@param  type_id           Description of the Parameter
   *@param  level             Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insertType(Connection db, int type_id, int level) throws SQLException {
    if (id == -1) {
      throw new SQLException("No Contact ID Specified");
    }
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO contact_type_levels " +
        "(contact_id, type_id, level) " +
        "VALUES (?, ?, ?) ");
    pst.setInt(++i, this.getId());
    pst.setInt(++i, type_id);
    pst.setInt(++i, level);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Performs a query and sets whether this user has an account or not
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.25
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
        "ORDER BY ctl.level ");
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
        System.out.println("Contact-> Built contact type: contact/" + id + " type/" + thisType.getCode() + " " + thisType.getDescription());
      }
    }
    rs.close();
    pst.close();
  }



  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void checkEnabledUserAccount(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }
    checkUserAccount(db);
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM access " +
        "WHERE contact_id = ? AND enabled = ? ");
    pst.setInt(1, this.getId());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next() || !hasAccount()) {
      setHasEnabledAccount(true);
    } else {
      setHasEnabledAccount(false);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  campaignId        Description of Parameter
   *@exception  SQLException  Description of Exception
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
   *  Returns whether the object has enough information to be saved or updated
   *
   *@param  db                Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   *@since                    1.35
   */
  public boolean isValid(Connection db) throws SQLException {
    errors.clear();
    //A contact must have at least a last name
    if (company == null || company.trim().equals("")) {
      if (nameLast == null || nameLast.trim().equals("")) {
        errors.put("nameLastError", "Last Name is required");
        errors.put("lastcompanyError", "Last Name or Company Name is required");
      }
    }

    //Prevent personal contacts from being associated with actions
    if (accessType != -1) {
      AccessType thisType = new AccessType(db, accessType);

      //all account contacts are public
      if (orgId > 0 && thisType.getRuleId() != AccessType.PUBLIC) {
        errors.put("accountAccessError", "All Account Contacts have public access");
      }

      //personal contacts should be owned by the user who enters it i.e they cannot be reassigned
      if (thisType.getRuleId() == AccessType.PERSONAL && owner != enteredBy) {
        errors.put("accessReassignError", "Personal Contact has to be owned by the user who entered it");
      }
    } else {
      errors.put("accessError", "Access Type is required");
    }

    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  On a typical update of the Contact record, do not execute this method
   *  directly, use Update(db), this was intended for the insert statement.
   *
   *@param  db                Description of Parameter
   *@param  override          Overrides checking the lastmodified date on an
   *      insert
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
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
        "org_id = ?, primary_contact = ?, org_name = ?, access_type = ?, ");

    if (imService > -1) {
      sql.append("imservice = ?, ");
    }
    sql.append("imname = ?, employee_id = ?, ");
    if (locale > -1) {
      sql.append("locale = ?, ");
    }
    if (employmentType > -1) {
      sql.append("employmentType = ?, ");
    }
    sql.append(
        "startofday = ?, endofday = ?, ");

    if (!override) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("modifiedby = ? WHERE contact_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getCompany());
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
    if (imService > -1) {
      pst.setInt(++i, this.getImService());
    }
    pst.setString(++i, this.getImName());
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
   *  Populates this object from a result set
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
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
    imName = rs.getString("imname");
    imService = rs.getInt("imservice");
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
    //lookup_department table
    departmentName = rs.getString("departmentname");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.30?
   */
  private boolean hasRelatedRecords(Connection db) throws SQLException {
    int recordCount = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) as count " +
        "FROM opportunity_header " +
        "WHERE contactlink = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = rs.getInt("count");
    }
    rs.close();
    pst.close();
    return (recordCount > 0);
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
    this.setOwner(newOwner);
    result = this.update(db);
    if (result == -1) {
      return false;
    }
    return true;
  }


  /**
   *  Updates the contact record with a new organization id
   *
   *@param  db                Description of the Parameter
   *@param  contactId         Description of the Parameter
   *@param  orgId             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void move(Connection db, int contactId, int orgId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE contact " +
        "SET org_id = ? " +
        "WHERE contact_id = ?");
    pst.setInt(1, orgId);
    pst.setInt(2, contactId);
    pst.executeUpdate();
    pst.close();
  }


  /**
   *  Combines the first and last name of a contact, depending on the length of
   *  the strings
   *
   *@param  nameLast   Description of the Parameter
   *@param  nameFirst  Description of the Parameter
   *@return            The nameLastFirst value
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
   *  Gets the nameFirstLast attribute of the Contact class
   *
   *@param  nameFirst  Description of the Parameter
   *@param  nameLast   Description of the Parameter
   *@return            The nameFirstLast value
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
   *  Makes a list of this a Contact's dependencies to other entities.
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    ResultSet rs = null;
    DependencyList dependencyList = new DependencyList();
    try {
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "SELECT count(*) as oppcount " +
          "FROM opportunity_header " +
          "WHERE opportunity_header.contactlink = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int oppCount = rs.getInt("oppcount");
        if (oppCount > 0) {
          this.setHasOpportunities(true);
        }
        Dependency thisDependency = new Dependency();
        thisDependency.setName("Opportunities");
        thisDependency.setCount(oppCount);
        thisDependency.setCanDelete(true);
        dependencyList.add(thisDependency);
      }
      rs.close();
      pst.close();

      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as callcount " +
          "FROM call_log " +
          "WHERE call_log.contact_id = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        Dependency thisDependency = new Dependency();
        thisDependency.setName("Activities");
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
        thisDependency.setName("Folders");
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
        thisDependency.setName("Tickets");
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
        thisDependency.setName("Tasks");
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
        thisDependency.setName("Service Contracts");
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
        thisDependency.setName("Assets");
        thisDependency.setCount(rs.getInt("assetcount"));
        thisDependency.setCanDelete(false);
        dependencyList.add(thisDependency);
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
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
  }


  /**
   *  Approves all records for a specific import
   *
   *@param  db                Description of the Parameter
   *@param  importId          Description of the Parameter
   *@param  status            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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

}

