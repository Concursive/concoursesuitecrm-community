//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Represents a Contact in CFS
 *
 *@author     mrajkowski
 *@created    August 29, 2001
 *@version    $Id$
 */
public class Contact extends GenericBean {

  /**
   *  Description of the Field
   */
  public final static int EMPLOYEE_TYPE = 1;

  private int id = -1;
  private int orgId = -1;
  private String orgName = "";
  private String company = "";
  private String title = "";
  private int department = 0;
  private String nameSalutation = "";
  private String nameFirst = "";
  private String nameMiddle = "";
  private String nameLast = "";
  private String nameSuffix = "";
  private int typeId = -1;
  private String typeName = "";
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

  private ContactEmailAddressList emailAddressList = new ContactEmailAddressList();
  private ContactPhoneNumberList phoneNumberList = new ContactPhoneNumberList();
  private ContactAddressList addressList = new ContactAddressList();
  private String departmentName = "";
  private String ownerName = "";
  private String enteredByName = "";
  private String modifiedByName = "";


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

    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT c.*, d.description as departmentname, t.description as type_name, " +
        "ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, " +
        "o.name as org_name " +
        "FROM contact c " +
        "LEFT JOIN lookup_contact_types t ON (c.type_id = t.code) " +
        "LEFT JOIN organization o ON (c.org_id = o.org_id) " +
        "LEFT JOIN lookup_department d ON (c.department = d.code) " +
        "LEFT JOIN contact ct_owner ON (c.owner = ct_owner.user_id) " +
        "LEFT JOIN contact ct_eb ON (c.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (c.modifiedby = ct_mb.user_id) " +
        "WHERE c.contact_id > -1 ");

    if (contactId != null && !contactId.equals("")) {
      sql.append("AND c.contact_id = " + contactId + " ");
    } else {
      throw new SQLException("Contact ID not specified.");
    }

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Contact record not found.");
    }
    rs.close();
    st.close();

    phoneNumberList.setContactId(this.getId());
    phoneNumberList.buildList(db);
    addressList.setContactId(this.getId());
    addressList.buildList(db);
    emailAddressList.setContactId(this.getId());
    emailAddressList.buildList(db);
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
    this.entered = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the modified attribute of the Contact object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = java.sql.Timestamp.valueOf(tmp);
    ;
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
   *  Sets the TypeId attribute of the Contact object
   *
   *@param  tmp  The new TypeId value
   *@since       1.2
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the TypeId attribute of the Contact object
   *
   *@param  tmp  The new TypeId value
   *@since       1.2
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
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
    if (tmp.toLowerCase().equals("false")) {
      this.enabled = false;
    } else {
      this.enabled = true;
    }
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
   *  Sets the ModifiedBy attribute of the Contact object
   *
   *@param  tmp  The new ModifiedBy value
   *@since       1.12
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
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



  /**
   *  Since dynamic fields cannot be auto-populated, passing the request to this
   *  method will populate the indicated fields.
   *
   *@param  request  The new RequestItems value
   *@since           1.15
   */
  public void setRequestItems(HttpServletRequest request) {
    phoneNumberList = new ContactPhoneNumberList(request);
    addressList = new ContactAddressList(request);
    emailAddressList = new ContactEmailAddressList(request);
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
   *  Gets the NameLastFirst attribute of the Contact object
   *
   *@return    The NameLastFirst value
   *@since
   */
  public String getNameLastFirst() {
    StringBuffer out = new StringBuffer();

    if (nameLast != null && nameLast.length() > 0) {
      out.append(nameLast);
    }

    if (nameFirst != null && nameFirst.length() > 0) {
      if (nameLast.length() > 0) {
        out.append(", ");
      }
      out.append(nameFirst);
    }

    if (out.toString().length() == 0) {
      return company;
    }

    return out.toString().trim();
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
   *  üýV$ Gets the Notes attribute of the Contact object
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
   *  Gets the TypeId attribute of the Contact object
   *
   *@return    The TypeId value
   *@since     1.1
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the TypeName attribute of the Contact object
   *
   *@return    The TypeName value
   *@since     1.30?
   */
  public String getTypeName() {
    return typeName;
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
   *  Gets the campaignMessageRange attribute of the Contact object
   *
   *@param  db                Description of Parameter
   *@return                   The campaignMessageRange value
   *@exception  SQLException  Description of Exception
   */
  public String getCampaignMessageRange(Connection db) throws SQLException {
    Statement st = null;
    ResultSet rs = null;
    StringBuffer r = new StringBuffer();

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT campaign_id from scheduled_recipient " +
        "WHERE contact_id = " + id + " ");

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    while (rs.next()) {
      if (r.length() > 0) {
        r.append(", " + rs.getInt("campaign_id"));
      } else {
        r.append(rs.getInt("campaign_id"));
      }
    }
    rs.close();
    st.close();

    if (r.length() == 0) {
      r.append("''");
    }

    return r.toString();
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean excludedFromCampaign() {
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

    String sql = "";

    if (this.excludedFromCampaign()) {
      sql =
          "DELETE FROM excluded_recipient " +
          "WHERE campaign_id = ? AND contact_id = ? ";
    } else {
      sql =
          "INSERT INTO excluded_recipient " +
          "(campaign_id, contact_id) VALUES (?, ?) ";
    }

    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, campaignId);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();

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

    if (typeId == -1) {
      throw new SQLException("Contact does not have a type specified");
    }

    StringBuffer sql = new StringBuffer();

    try {
      db.setAutoCommit(false);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Contact-> Inserting contact");
      }
      sql.append(
          "INSERT INTO contact " +
          "(type_id, enteredby, modifiedby, namefirst, namelast, owner) " +
          "VALUES (?, ?, ?, ?, ?, ?) ");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getTypeId());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.setString(++i, this.getNameFirst());
      pst.setString(++i, this.getNameLast());
      pst.setInt(++i, this.getOwner());
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
        thisPhoneNumber.insert(db, this.getId(), this.getEnteredBy());
      }

      //Insert the addresses if there are any
      Iterator iaddress = addressList.iterator();
      while (iaddress.hasNext()) {
        ContactAddress thisAddress = (ContactAddress) iaddress.next();
        thisAddress.insert(db, this.getId(), this.getEnteredBy());
      }

      //Insert the email addresses if there are any
      Iterator iemail = emailAddressList.iterator();
      while (iemail.hasNext()) {
        ContactEmailAddress thisEmailAddress = (ContactEmailAddress) iemail.next();
        thisEmailAddress.insert(db, this.getId(), this.getEnteredBy());
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

    if (typeId == -1) {
      throw new SQLException("Contact does not have a TYPE specified");
    }

    try {
      db.setAutoCommit(false);
      resultCount = this.update(db, false);

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
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }

    db.setAutoCommit(true);
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

    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT * " +
        "FROM access " +
        "WHERE contact_id = " + this.getId());
    if (rs.next()) {
      hasAccess = true;
      isEnabled = rs.getBoolean("enabled");
    }
    rs.close();
    st.close();

    if (hasAccess && isEnabled) {
      errors.put("actionError", "This contact has an active user account and could not be deleted.");
      return false;
    } else if ((hasAccess && !isEnabled) || hasRelatedRecords(db)) {
      errors.put("actionError", "Contact disabled from view, since it has a related user account");
      st.executeUpdate(
          "UPDATE contact " +
          "SET enabled = " + DatabaseUtils.getFalse(db) + " " +
          "WHERE contact_id = " + this.getId());
      st.close();
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

        st.executeUpdate("DELETE FROM contact_phone WHERE contact_id = " + this.getId());
        st.executeUpdate("DELETE FROM contact_emailaddress WHERE contact_id = " + this.getId());
        st.executeUpdate("DELETE FROM contact_address WHERE contact_id = " + this.getId());
        st.executeUpdate("DELETE FROM contact WHERE contact_id = " + this.getId());
        db.commit();
      } catch (SQLException e) {
        db.rollback();
        System.out.println(e.toString());
      } finally {
        db.setAutoCommit(true);
        st.close();
      }
      return true;
    }
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

    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT * " +
        "FROM access " +
        "WHERE contact_id = " + getId() + " ");
    if (rs.next()) {
      setHasAccount(true);
    } else {
      setHasAccount(false);
    }
    rs.close();
    st.close();
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

    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT * " +
        "FROM excluded_recipient " +
        "WHERE contact_id = " + getId() + " AND campaign_id = " + campaignId + " ");
    if (rs.next()) {
      setExcludedFromCampaign(true);
    } else {
      setExcludedFromCampaign(false);
    }
    rs.close();
    st.close();
  }


  /**
   *  Returns whether the object has enough information to be saved or updated
   *
   *@param  db                Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   *@since                    1.35
   */
  protected boolean isValid(Connection db) throws SQLException {
    errors.clear();

    if (company == null || company.trim().equals("")) {
      if (nameLast == null || nameLast.trim().equals("")) {
        errors.put("nameLastError", "Last name is required");
        errors.put("lastcompanyError", "Last Name or Company Name is required");
      }
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
        "namemiddle = ?, namesuffix = ?, type_id = ?, notes = ?, owner = ?, ");
    if (orgId > -1) {
      sql.append("org_id = ?, ");
    }
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
        "startofday = ?, endofday = ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ? ");
    sql.append("WHERE contact_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getCompany());
    pst.setString(++i, this.getTitle());
    pst.setInt(++i, this.getDepartment());
    pst.setString(++i, this.getNameSalutation());
    pst.setString(++i, this.getNameFirst());
    pst.setString(++i, this.getNameLast());
    pst.setString(++i, this.getNameMiddle());
    pst.setString(++i, this.getNameSuffix());
    pst.setInt(++i, this.getTypeId());
    pst.setString(++i, this.getNotes());
    pst.setInt(++i, this.getOwner());

    if (orgId > -1) {
      pst.setInt(++i, this.getOrgId());
    }
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
    orgId = rs.getInt("org_id");
    company = rs.getString("company");
    title = rs.getString("title");
    department = rs.getInt("department");
    nameSalutation = rs.getString("namesalutation");
    nameLast = rs.getString("namelast");
    nameFirst = rs.getString("namefirst");
    nameMiddle = rs.getString("namemiddle");
    nameSuffix = rs.getString("namesuffix");
    typeId = rs.getInt("type_id");
    notes = rs.getString("notes");
    site = rs.getString("site");
    imName = rs.getString("imname");
    imService = rs.getInt("imservice");
    locale = rs.getInt("locale");
    employeeId = rs.getString("employee_id");
    employmentType = rs.getInt("employmenttype");
    startOfDay = rs.getString("startofday");
    endOfDay = rs.getString("endofDay");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
    owner = rs.getInt("owner");
    
    //lookup_department table
    departmentName = rs.getString("departmentname");
    
    //lookup_contact_types table
    typeName = rs.getString("type_name");
    
    //contact table
    ownerName = Contact.getNameLastFirst(rs.getString("o_namelast"), rs.getString("o_namefirst"));
    enteredByName = Contact.getNameLastFirst(rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
    modifiedByName = Contact.getNameLastFirst(rs.getString("mb_namelast"), rs.getString("mb_namefirst"));
    
    //organization table
    orgName = rs.getString("org_name");
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
    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT count(*) as count " +
        "FROM opportunity " +
        "WHERE contactlink = " + this.getId());
    rs.next();
    int recordCount = rs.getInt("count");
    rs.close();
    st.close();

    return (recordCount > 0);
  }
  
  public static String getNameLastFirst(String nameLast, String nameFirst) {
    StringBuffer out = new StringBuffer();
    if (nameLast != null && nameLast.length() > 0) {
      out.append(nameLast);
    }
    if (nameFirst != null && nameFirst.length() > 0) {
      if (nameLast.length() > 0) {
        out.append(", ");
      }
      out.append(nameFirst);
    }
    if (out.toString().length() == 0) {
      return null;
    }
    return out.toString().trim();
  }
}

