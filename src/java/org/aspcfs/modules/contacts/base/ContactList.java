//Copyright 2001 Dark Horse Ventures
//The createFilter method and the prepareFilter method need to have the same
//number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.sql.*;
import java.text.*;
import java.util.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Contains a list of contacts... currently used to build the list from the
 *  database with any of the parameters to limit the results.
 *
 *@author     mrajkowski
 *@created    August 29, 2001
 *@version    $Id: ContactList.java,v 1.1.1.1 2002/01/14 19:49:24 mrajkowski Exp
 *      $
 */
public class ContactList extends Vector {
  
  public static final int TRUE = 1;
  public static final int FALSE = 0;
  private int includeEnabled = TRUE;
  
  private boolean includeEnabledUsersOnly = false;
  private boolean includeNonUsersOnly = false;
  
  private PagedListInfo pagedListInfo = null;
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
  private int owner = -1;
  private String ownerIdRange = null;
  private String accountOwnerIdRange = null;
  private boolean withAccountsOnly = false;
  private boolean withProjectsOnly = false;
  private String emptyHtmlSelectRecord = null;
  private String jsEvent = null;
  
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
  private HashMap accountTypeIdHash = null;
  
  boolean firstCriteria = true;
  
  private String contactIdRange = null;
  
  private SearchCriteriaList scl = null;
  private boolean showEmployeeContacts = false;
  
  private String searchText = "";
  
  private int personalId = -1;
  
  
  /**
   *  Constructor for the ContactList object
   *
   *@since    1.1
   */
  public ContactList() { }
  
  /**
   *  Sets the ShowEmployeeContacts attribute of the ContactList object
   *
   *@param  showEmployeeContacts  The new ShowEmployeeContacts value
   *@since
   */
  public void setShowEmployeeContacts(boolean showEmployeeContacts) {
    this.showEmployeeContacts = showEmployeeContacts;
  }
  
  
  /**
   *  Sets the checkExcludedFromCampaign attribute of the ContactList object
   *
   *@param  checkExcludedFromCampaign  The new checkExcludedFromCampaign value
   */
  public void setCheckExcludedFromCampaign(int checkExcludedFromCampaign) {
    this.checkExcludedFromCampaign = checkExcludedFromCampaign;
  }
  
  
  /**
   *  Sets the contactIdRange attribute of the ContactList object
   *
   *@param  contactIdRange  The new contactIdRange value
   */
  public void setContactIdRange(String contactIdRange) {
    this.contactIdRange = contactIdRange;
  }
  
  public HashMap getAccountTypeIdHash() {
    return accountTypeIdHash;
  }
  public void setAccountTypeIdHash(HashMap accountTypeIdHash) {
    this.accountTypeIdHash = accountTypeIdHash;
  }
  
  public void setIncludeEnabledUsersOnly(boolean includeEnabledUsersOnly) {
    this.includeEnabledUsersOnly = includeEnabledUsersOnly;
  }
  public boolean getIncludeEnabledUsersOnly() {
    return includeEnabledUsersOnly;
  }
  
  /**
   *  Sets the FirstName attribute of the ContactList object
   *
   *@param  firstName  The new FirstName value
   *@since
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public HashMap getNameFirstHash() {
    return nameFirstHash;
  }
  public void setNameFirstHash(HashMap nameFirstHash) {
    this.nameFirstHash = nameFirstHash;
  }
  
  public HashMap getDateHash() {
    return dateHash;
  }
  public void setDateHash(HashMap dateHash) {
    this.dateHash = dateHash;
  }

  public HashMap getZipHash() {
    return zipHash;
  }
  public void setZipHash(HashMap zipHash) {
    this.zipHash = zipHash;
  }
  
  public void setContactIdHash(HashMap contactIdHash) {
    this.contactIdHash = contactIdHash;
  }
  public HashMap getContactIdHash() {
    return contactIdHash;
  }

  /**
   *  Sets the SearchText attribute of the ContactList object
   *
   *@param  searchText  The new SearchText value
   *@since
   */
  public void setSearchText(String searchText) {
    this.searchText = searchText;
  }
  
  public boolean getFirstCriteria() {
    return firstCriteria;
  }
  public void setFirstCriteria(boolean firstCriteria) {
    this.firstCriteria = firstCriteria;
  }

  /**
   *  Sets the Company attribute of the ContactList object
   *
   *@param  company  The new Company value
   *@since
   */
  public void setCompany(String company) {
    this.company = company;
  }
  
  public HashMap getCityHash() {
    return cityHash;
  }
  public void setCityHash(HashMap cityHash) {
    this.cityHash = cityHash;
  }
  
  /**
   *  Sets the OwnerIdRange attribute of the ContactList object
   *
   *@param  ownerIdRange  The new OwnerIdRange value
   *@since
   */
  public void setOwnerIdRange(String ownerIdRange) {
    this.ownerIdRange = ownerIdRange;
  }
  
  
  /**
   *  Gets the emptyHtmlSelectRecord attribute of the ContactList object
   *
   *@return    The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }
  
  
  /**
   *  Sets the emptyHtmlSelectRecord attribute of the ContactList object
   *
   *@param  emptyHtmlSelectRecord  The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String emptyHtmlSelectRecord) {
    this.emptyHtmlSelectRecord = emptyHtmlSelectRecord;
  }
  
  
  /**
   *  Sets the accountOwnerIdRange attribute of the ContactList object
   *
   *@param  tmp  The new accountOwnerIdRange value
   */
  public void setAccountOwnerIdRange(String tmp) {
    this.accountOwnerIdRange = tmp;
  }
  
  
  /**
   *  Sets the withAccountsOnly attribute of the ContactList object
   *
   *@param  tmp  The new withAccountsOnly value
   */
  public void setWithAccountsOnly(boolean tmp) {
    this.withAccountsOnly = tmp;
  }
  
  
  /**
   *  Sets the Owner attribute of the ContactList object
   *
   *@param  owner  The new Owner value
   *@since
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }
  
  
  /**
   *  Sets the PersonalId attribute of the ContactList object
   *
   *@param  personalId  The new PersonalId value
   *@since
   */
  public void setPersonalId(int personalId) {
    this.personalId = personalId;
  }
  
  public HashMap getNameLastHash() {
    return nameLastHash;
  }
  public void setNameLastHash(HashMap nameLastHash) {
    this.nameLastHash = nameLastHash;
  }
  
  /**
   *  Sets the Scl attribute of the ContactList object
   *
   *@param  scl            The new Scl value
   *@param  thisOwnerId    The new scl value
   *@param  thisUserRange  The new scl value
   *@since
   */
  public void setScl(SearchCriteriaList scl, int thisOwnerId, String thisUserRange) {
    this.scl = scl;
    this.sclOwnerId = thisOwnerId;
    this.sclOwnerIdRange = thisUserRange;
    this.setPersonalId(-2);
    
    buildQuery(thisOwnerId, thisUserRange);
  }
  
  public String getJsEvent() {
    return jsEvent;
  }
  public void setJsEvent(String jsEvent) {
    this.jsEvent = jsEvent;
  }
  
  public boolean getCheckEnabledUserAccess() {
    return checkEnabledUserAccess;
  }
  public void setCheckEnabledUserAccess(boolean checkEnabledUserAccess) {
    this.checkEnabledUserAccess = checkEnabledUserAccess;
  }

  
  /**
   *  Sets the MiddleName attribute of the ContactList object
   *
   *@param  tmp  The new MiddleName value
   *@since
   */
  public void setMiddleName(String tmp) {
    this.middleName = tmp;
  }
  
  
  /**
   *  Sets the LastName attribute of the ContactList object
   *
   *@param  tmp  The new LastName value
   *@since
   */
  public void setLastName(String tmp) {
    this.lastName = tmp;
  }
  
  public HashMap getCompanyHash() {
    return companyHash;
  }
  public void setCompanyHash(HashMap companyHash) {
    this.companyHash = companyHash;
  }
  
  public int getSclOwnerId() { return sclOwnerId; }
  public String getSclOwnerIdRange() { return sclOwnerIdRange; }
  public void setSclOwnerId(int tmp) { this.sclOwnerId = tmp; }
  public void setSclOwnerIdRange(String tmp) { this.sclOwnerIdRange = tmp; }

  /**
   *  Sets the PagedListInfo attribute of the ContactList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since       1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }
  
  
  /**
   *  Sets the Title attribute of the ContactList object
   *
   *@param  title  The new Title value
   *@since
   */
  public void setTitle(String title) {
    this.title = title;
  }
  
  
  /**
   *  Sets the OrgId attribute of the ContactList object
   *
   *@param  tmp  The new OrgId value
   *@since       1.1
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }
  
  
  /**
   *  Sets the EmailNotNull attribute of the ContactList object
   *
   *@param  emailNotNull  The new EmailNotNull value
   *@since
   */
  public void setEmailNotNull(boolean emailNotNull) {
    this.emailNotNull = emailNotNull;
  }
  
  
  /**
   *  Sets the TypeId attribute of the ContactList object
   *
   *@param  tmp  The new TypeId value
   *@since       1.1
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }
  
  
  /**
   *  Sets the CheckUserAccess attribute of the ContactList object
   *
   *@param  tmp  The new CheckUserAccess value
   *@since       1.8
   */
  public void setCheckUserAccess(boolean tmp) {
    this.checkUserAccess = tmp;
  }
  
  
  /**
   *  Sets the BuildDetails attribute of the ContactList object
   *
   *@param  tmp  The new BuildDetails value
   *@since
   */
  public void setBuildDetails(boolean tmp) {
    this.buildDetails = tmp;
  }
  
  /**
   *  Sets the SearchValues attribute of the ContactList object
   *
   *@param  outerHash  The new SearchValues value
   *@since
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
    this.accountTypeIdHash = outerHash[9];
  }
  
  
  
  /**
   *  Sets the departmentId attribute of the ContactList object
   *
   *@param  departmentId  The new departmentId value
   */
  public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
  }
  
  
  /**
   *  Sets the withProjectsOnly attribute of the ContactList object
   *
   *@param  withProjectsOnly  The new withProjectsOnly value
   */
  public void setWithProjectsOnly(boolean withProjectsOnly) {
    this.withProjectsOnly = withProjectsOnly;
  }
  
  
  /**
   *  Sets the projectId attribute of the ContactList object
   *
   *@param  projectId  The new projectId value
   */
  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }
  
  
  /**
   *  Gets the contactIdRange attribute of the ContactList object
   *
   *@return    The contactIdRange value
   */
  public String getContactIdRange() {
    return contactIdRange;
  }
  
  
  /**
   *  Gets the checkExcludedFromCampaign attribute of the ContactList object
   *
   *@return    The checkExcludedFromCampaign value
   */
  public int getCheckExcludedFromCampaign() {
    return checkExcludedFromCampaign;
  }
  
  
  /**
   *  Gets the pagedListInfo attribute of the ContactList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }
  
  public HashMap getTypeIdHash() {
    return typeIdHash;
  }
  public void setTypeIdHash(HashMap typeIdHash) {
    this.typeIdHash = typeIdHash;
  }

  /**
   *  Gets the PersonalId attribute of the ContactList object
   *
   *@return    The PersonalId value
   *@since
   */
  public int getPersonalId() {
    return personalId;
  }
  
  public int getIncludeEnabled() {
    return includeEnabled;
  }
  public void setIncludeEnabled(int includeEnabled) {
    this.includeEnabled = includeEnabled;
  }
  
  public HashMap getAreaCodeHash() {
    return areaCodeHash;
  }
  public void setAreaCodeHash(HashMap areaCodeHash) {
    this.areaCodeHash = areaCodeHash;
  }

  
  /**
   *  Gets the ShowEmployeeContacts attribute of the ContactList object
   *
   *@return    The ShowEmployeeContacts value
   *@since
   */
  public boolean getShowEmployeeContacts() {
    return showEmployeeContacts;
  }
  
  
  /**
   *  Gets the SearchText attribute of the ContactList object
   *
   *@return    The SearchText value
   *@since
   */
  public String getSearchText() {
    return searchText;
  }
  
  /**
   *  Gets the OwnerIdRange attribute of the ContactList object
   *
   *@return    The OwnerIdRange value
   *@since
   */
  public String getOwnerIdRange() {
    return ownerIdRange;
  }
  
  
  /**
   *  Gets the accountOwnerIdRange attribute of the ContactList object
   *
   *@return    The accountOwnerIdRange value
   */
  public String getAccountOwnerIdRange() {
    return accountOwnerIdRange;
  }
  
  
  /**
   *  Gets the withAccountsOnly attribute of the ContactList object
   *
   *@return    The withAccountsOnly value
   */
  public boolean getWithAccountsOnly() {
    return withAccountsOnly;
  }
  
  /**
   *  Gets the Scl attribute of the ContactList object
   *
   *@return    The Scl value
   *@since
   */
  public SearchCriteriaList getScl() {
    return scl;
  }
  
  /**
   *  Gets the EmailNotNull attribute of the ContactList object
   *
   *@return    The EmailNotNull value
   *@since
   */
  public boolean getEmailNotNull() {
    return emailNotNull;
  }
  
  
  /**
   *  Gets the Owner attribute of the ContactList object
   *
   *@return    The Owner value
   *@since
   */
  public int getOwner() {
    return owner;
  }
  
  
  /**
   *  Gets the Company attribute of the ContactList object
   *
   *@return    The Company value
   *@since
   */
  public String getCompany() {
    return company;
  }
  
  
  /**
   *  Gets the Title attribute of the ContactList object
   *
   *@return    The Title value
   *@since
   */
  public String getTitle() {
    return title;
  }
  
  public boolean getIncludeNonUsersOnly() {
    return includeNonUsersOnly;
  }
  public void setIncludeNonUsersOnly(boolean includeNonUsersOnly) {
    this.includeNonUsersOnly = includeNonUsersOnly;
  }
  
  /**
   *  Gets the MiddleName attribute of the ContactList object
   *
   *@return    The MiddleName value
   *@since
   */
  public String getMiddleName() {
    return middleName;
  }
  
  
  /**
   *  Gets the LastName attribute of the ContactList object
   *
   *@return    The LastName value
   *@since
   */
  public String getLastName() {
    return lastName;
  }
  
  
  /**
   *  Gets the FirstName attribute of the ContactList object
   *
   *@return    The FirstName value
   *@since
   */
  public String getFirstName() {
    return firstName;
  }
  
  
  /**
   *  Gets the HtmlSelect attribute of the ContactList object
   *
   *@param  selectName  Description of Parameter
   *@return             The HtmlSelect value
   *@since              1.8
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }
  
  
  /**
   *  Gets the EmptyHtmlSelect attribute of the ContactList object
   *
   *@param  selectName  Description of Parameter
   *@return             The EmptyHtmlSelect value
   *@since
   */
  public String getEmptyHtmlSelect(String selectName) {
    HtmlSelect contactListSelect = new HtmlSelect();
    contactListSelect.addItem(-1, "-- None --");
    return contactListSelect.getHtml(selectName);
  }
  
  
  /**
   *  Gets the HtmlSelect attribute of the ContactList object
   *
   *@param  selectName  Description of Parameter
   *@param  defaultKey  Description of Parameter
   *@return             The HtmlSelect value
   *@since              1.8
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
      contactListSelect.addItem(
      thisContact.getId(),
      Contact.getNameLastFirst(thisContact.getNameLast(),
      thisContact.getNameFirst()) +
      (checkUserAccess ? (thisContact.hasAccount() ? " (*)" : "") : ""));
    }
    return contactListSelect.getHtml(selectName, defaultKey);
  }
  
  public void buildQuery(int thisOwnerId, String thisUserRange) {
   
    String fieldName = "";
    String readyToGo = "";
    
    HashMap[] outerHash = null;
    
    //ONE FOR EACH IN THE FIELD LIST
    HashMap company = new HashMap();
    HashMap namefirst = new HashMap();
    HashMap namelast = new HashMap();
    HashMap entered = new HashMap();
    HashMap zip = new HashMap();
    HashMap areacode = new HashMap();
    HashMap city = new HashMap();
    HashMap typeId = new HashMap();
    HashMap contactId = new HashMap();
    HashMap accountTypeId = new HashMap();
    
    int count = 0;
    
    //THIS CORRESPONDS TO THE FIELD LIST
    
    outerHash = new HashMap[]{
      company,
      namefirst,
      namelast,
      entered,
      zip,
      areacode,
      city,
      typeId,
      contactId,
      accountTypeId
    };
    
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ContactList-> SCL Size: " + this.getScl().size() + " name: " + this.getScl().getGroupName());
    }
    Iterator i = this.getScl().keySet().iterator();
    while (i.hasNext()) {
      ++count;
      Integer group = (Integer) i.next();
      SearchCriteriaGroup thisGroup = (SearchCriteriaGroup) this.getScl().get(group);
      fieldName = thisGroup.getGroupField().getFieldName();
      
      Iterator j = thisGroup.iterator();
      
      while (j.hasNext()) {
        
        SearchCriteriaElement thisElement = (SearchCriteriaElement) j.next();
        
        //some alterations 
        if (thisElement.getFieldId() == 11) {
          thisElement.setFieldId(10);
        }
        
        readyToGo = replace(thisElement.getText().toLowerCase(), '\'', "\\'");
        //String check = (String) outerHash[(thisElement.getFieldId() - 1)].get(thisElement.getOperator());
        HashMap tempHash = (HashMap) outerHash[(thisElement.getFieldId() - 1)].get(thisElement.getOperator());
        
        //only if we have string data to deal with
        if (tempHash == null || tempHash.size() == 0 || thisElement.getDataType().equals("date")) {
          if (thisElement.getDataType().equals("date")) {
            int month = 0;
            int day = 0;
            int year = 0;
            
            StringTokenizer st = new StringTokenizer(readyToGo, "/");
            
            if (st.hasMoreTokens()) {
              month = Integer.parseInt(st.nextToken());
              day = Integer.parseInt(st.nextToken());
              year = Integer.parseInt(st.nextToken());
            }
            
            Calendar tmpCal = new GregorianCalendar(year, (month - 1), day);
            
            //fix it if "on or before" or "after" is selected.
            if (thisElement.getOperatorId() == 8 || thisElement.getOperatorId() == 10) {
              tmpCal.add(java.util.Calendar.DATE, +1);
            }
            
            HashMap tempTable = new HashMap();
            
            String backToString = (tmpCal.get(Calendar.MONTH) + 1) + "/" + tmpCal.get(Calendar.DAY_OF_MONTH) + "/" + tmpCal.get(Calendar.YEAR);
            tempTable.put(backToString, thisElement.getSourceId()+"");
            
            //outerHash[(thisElement.getFieldId() - 1)].put(thisElement.getOperator(), ("'" + backToString + "'"));
            outerHash[(thisElement.getFieldId() - 1)].put(thisElement.getOperator(), tempTable);
          } else {
            //first entry
            HashMap tempTable = new HashMap();
            tempTable.put(readyToGo, thisElement.getSourceId()+"");
            //outerHash[(thisElement.getFieldId() - 1)].put(thisElement.getOperator(), ("'" + readyToGo + "'"));
            outerHash[(thisElement.getFieldId() - 1)].put(thisElement.getOperator(), tempTable);
          }
        } else {
          //check = check + ", '" + readyToGo + "'";
          
          tempHash.put(readyToGo, thisElement.getSourceId()+"");
          outerHash[(thisElement.getFieldId() - 1)].remove(thisElement.getOperator());
          outerHash[(thisElement.getFieldId() - 1)].put(thisElement.getOperator(), tempHash);
          
          //outerHash[(thisElement.getFieldId() - 1)].put(thisElement.getOperator(), check);
        }
        //end of that
        
        
      }
    }
    
    //THIS PART IS ALSO DEPENDENT
    this.setSearchValues(outerHash);
  }
  
  /**
   *  Builds a list, a part of the XML API
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
  
  /**
   *  Builds a list of contacts based on several parameters. The parameters are
   *  set after this object is constructed, then the buildList method is called
   *  to generate the list.
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public void buildList(Connection db) throws SQLException {
    
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    
    //Need to build a base SQL statement for counting records
    sqlCount.append(
    "SELECT COUNT(*) AS recordcount " +
    "FROM contact c " +
    "LEFT JOIN lookup_contact_types t ON (c.type_id = t.code) " +
    "LEFT JOIN organization o ON (c.org_id = o.org_id) " +
    "LEFT JOIN lookup_department d ON (c.department = d.code) " +
    "LEFT JOIN contact ct_owner ON (c.owner = ct_owner.user_id) " +
    "LEFT JOIN contact ct_eb ON (c.enteredby = ct_eb.user_id) " +
    "LEFT JOIN contact ct_mb ON (c.modifiedby = ct_mb.user_id) " +
    "WHERE c.contact_id > -1 ");
    
    createFilter(sqlFilter);
    
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      pst.close();
      rs.close();
      
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
        sqlFilter.toString() +
        "AND c.namelast < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      
      //Determine column to sort by
      pagedListInfo.setDefaultSort("c.namelast", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY c.namelast ");
    }
    
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "c.*, d.description as departmentname, t.description as type_name, " +
        "ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, " +
        "o.name as org_name,o.enabled as orgenabled " +
        "FROM contact c " +
        "LEFT JOIN lookup_contact_types t ON (c.type_id = t.code) " +
        "LEFT JOIN organization o ON (c.org_id = o.org_id) " +
        "LEFT JOIN lookup_department d ON (c.department = d.code) " +
        "LEFT JOIN contact ct_owner ON (c.owner = ct_owner.user_id) " +
        "LEFT JOIN contact ct_eb ON (c.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (c.modifiedby = ct_mb.user_id) " +
        "WHERE c.contact_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
      DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
      count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      Contact thisContact = new Contact(rs);
      this.addElement(thisContact);
    }
    rs.close();
    pst.close();
    buildResources(db);
    
  }
  
  /**
   *  Adds a feature to the IgnoreTypeId attribute of the ContactList object
   *
   *@param  tmp  The feature to be added to the IgnoreTypeId attribute
   *@since       1.2
   */
  public void addIgnoreTypeId(String tmp) {
    ignoreTypeIdList.addElement(tmp);
  }
  
  
  /**
   *  Adds a feature to the IgnoreTypeId attribute of the ContactList object
   *
   *@param  tmp  The feature to be added to the IgnoreTypeId attribute
   *@since       1.2
   */
  public void addIgnoreTypeId(int tmp) {
    ignoreTypeIdList.addElement("" + tmp);
  }
  
  
  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator contacts = this.iterator();
    while (contacts.hasNext()) {
      Contact thisContact = (Contact) contacts.next();
      thisContact.delete(db);
    }
  }
  
  
  /**
   *  Description of the Method
   *
   *@param  str      Description of Parameter
   *@param  oldChar  Description of Parameter
   *@param  newStr   Description of Parameter
   *@return          Description of the Returned Value
   *@since
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
   *  Convenience method to get a list of phone numbers for each contact
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.5
   */
  private void buildResources(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Contact thisContact = (Contact) i.next();
      if (buildDetails) {
        thisContact.getPhoneNumberList().buildList(db);
        thisContact.getAddressList().buildList(db);
        thisContact.getEmailAddressList().buildList(db);
      }
      if (checkUserAccess) {
        thisContact.checkUserAccount(db);
      }
      if (checkEnabledUserAccess) {
        thisContact.checkEnabledUserAccount(db);
      }
      if (checkExcludedFromCampaign > -1) {
        thisContact.checkExcludedFromCampaign(db, checkExcludedFromCampaign);
      }
    }
  }
  
  
  /**
   *  Builds a base SQL where statement for filtering records to be used by
   *  sqlSelect and sqlCount
   *
   *@param  sqlFilter  Description of Parameter
   *@since             1.3
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    
    if (searchText == null || (searchText.equals(""))) {
      if (orgId != -1) {
        sqlFilter.append("AND c.org_id = ? ");
      }
      
      if (includeEnabled == TRUE || includeEnabled == FALSE) {
        sqlFilter.append("AND c.enabled = ? ");
      }
      
      if (owner != -1) {
        sqlFilter.append("AND c.owner = ? ");
      }
      
      if (typeId != -1) {
        sqlFilter.append("AND c.type_id = ? ");
      }
      
      if (departmentId != -1) {
        sqlFilter.append("AND c.department = ? ");
      }
      
      if (projectId != -1) {
        sqlFilter.append("AND c.user_id in (SELECT DISTINCT user_id FROM project_team WHERE project_id = ?) ");
      }
      
      
      if (firstName != null) {
        if (firstName.indexOf("%") >= 0) {
          sqlFilter.append("AND lower(c.namefirst) like lower(?) ");
        } else {
          sqlFilter.append("AND lower(c.namefirst) = lower(?) ");
        }
      }
      
      if (middleName != null) {
        if (middleName.indexOf("%") >= 0) {
          sqlFilter.append("AND lower(c.namemiddle) like lower(?) ");
        } else {
          sqlFilter.append("AND lower(c.namemiddle) = lower(?) ");
        }
      }
      
      if (lastName != null) {
        if (lastName.indexOf("%") >= 0) {
          sqlFilter.append("AND lower(c.namelast) like lower(?) ");
        } else {
          sqlFilter.append("AND lower(c.namelast) = lower(?) ");
        }
      }
      
      if (title != null) {
        if (title.indexOf("%") >= 0) {
          sqlFilter.append("AND lower(c.title) like lower(?) ");
        } else {
          sqlFilter.append("AND lower(c.title) = lower(?) ");
        }
      }
      
      if (company != null) {
        if (company.indexOf("%") >= 0) {
          sqlFilter.append("AND lower(c.company) like lower(?) ");
        } else {
          sqlFilter.append("AND lower(c.company) = lower(?) ");
        }
      }
      
      if (ownerIdRange != null) {
        sqlFilter.append("AND c.owner IN (" + ownerIdRange + ") ");
      }
      
      if (contactIdRange != null && scl.getOnlyContactIds() == true) {
        sqlFilter.append("AND c.contact_id IN (" + contactIdRange + ") ");
      }
      
      if (withAccountsOnly) {
        sqlFilter.append("AND c.org_id > 0 ");
      }
      
      if (withProjectsOnly) {
        sqlFilter.append("AND c.user_id in (Select distinct user_id from project_team) ");
      }
      
      if (includeEnabledUsersOnly) {
        sqlFilter.append("AND c.user_id IN (SELECT user_id FROM access WHERE enabled = ?) ");
      }
      
      if (includeNonUsersOnly) {
        sqlFilter.append("AND c.contact_id NOT IN (SELECT contact_id FROM access) ");
      }      
      
      if (accountOwnerIdRange != null) {
        sqlFilter.append("AND c.org_id IN (SELECT org_id FROM organization WHERE owner IN (" + accountOwnerIdRange + ")) ");
      }
      //Decide which contacts can be shown
      switch (personalId) {
        //System needs to get all contacts
        case -2: break;
        //Typical contact list
        case -1: sqlFilter.append("AND (c.type_id != 2 OR c.type_id is NULL) "); break;
        //Typical contact list by a specific user
        default:
          sqlFilter.append("AND ( (c.type_id != 2 OR c.type_id is NULL) OR (c.type_id = 2 AND c.owner = ?) ) ");
          break;
      }
      if (ignoreTypeIdList.size() > 0) {
        Iterator iList = ignoreTypeIdList.iterator();
        sqlFilter.append("AND (c.type_id not in (");
        while (iList.hasNext()) {
          String placeHolder = (String) iList.next();
          sqlFilter.append("?");
          if (iList.hasNext()) {
            sqlFilter.append(",");
          }
        }
        sqlFilter.append(") OR c.type_id is NULL) ");
      }
      
      //contactIds
      if (contactIdHash != null && contactIdHash.size() > 0) {
          boolean newTerm = true;
          
          HashMap innerHash = (HashMap)contactIdHash.get("=");
          if (innerHash != null) {
            int termsProcessed = 0;
            Iterator inner = innerHash.keySet().iterator();
            
               while(inner.hasNext()) {
                  String key2 = (String)inner.next();
                  
                    newTerm = processElementHeader(sqlFilter, newTerm, termsProcessed);
                    sqlFilter.append(" (c.contact_id  = '" + key2 + "' ) ");
                    termsProcessed++;
              }
              
              if (!newTerm) {
                sqlFilter.append(") ");
              }
          }
       }
      
      //loop on the types 
      
      for(int y=1; y<(SearchCriteriaList.CONTACT_SOURCE_ELEMENTS+1); y++) {
        boolean newTerm = true;
        int termsProcessed = 0;
        
        //company names
        if (companyHash != null && companyHash.size() > 0) {
          Iterator outer = companyHash.keySet().iterator();
          
          termsProcessed = 0;
          String previousKey = null;
          
          while(outer.hasNext()) {
            String key1 = (String)outer.next();
            HashMap innerHash = (HashMap)companyHash.get(key1);
            Iterator inner = innerHash.keySet().iterator();
            
               while(inner.hasNext()) {
                  String key2 = (String)inner.next();
                  int elementType = Integer.parseInt(((String)innerHash.get(key2)).toString());
                  
                  //equals and != are the only operators supported right now
                  if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
                    if (termsProcessed > 0 && !(previousKey.equals(key1))) {
                      newTerm = processElementHeader(sqlFilter, newTerm, 0);
                    } else {
                      if (termsProcessed > 0 && key1.equals("!=") && previousKey.equals(key1)) {
                        //if you're doing multiple != terms in a row, what you really want is an AND not an OR
                        newTerm = processElementHeader(sqlFilter, newTerm, 0);
                      } else {
                        newTerm = processElementHeader(sqlFilter, newTerm, termsProcessed);
                      }
                    }
                     
                    if (termsProcessed == 0) {
                      sqlFilter.append("(");
                    }
                    
                    sqlFilter.append(" (lower(o.name) " + key1 + " '" + key2 + "' OR lower(c.company) " + key1 + " '" + key2 + "' ) ");
                    
                    previousKey = key1;
                    processElementType(sqlFilter, elementType);
                    termsProcessed++;
                  }
                }
              }
              if (termsProcessed > 0) {
                sqlFilter.append(")");
              }
        }
            
        //first names
        if (nameFirstHash != null && nameFirstHash.size() > 0) {
          Iterator outer = nameFirstHash.keySet().iterator();
          
          termsProcessed = 0;
          String previousKey = null;
          
          while(outer.hasNext()) {
            String key1 = (String)outer.next();
            HashMap innerHash = (HashMap)nameFirstHash.get(key1);
            Iterator inner = innerHash.keySet().iterator();
            
               while(inner.hasNext()) {
                  String key2 = (String)inner.next();
                  int elementType = Integer.parseInt(((String)innerHash.get(key2)).toString());
                  
                  //equals and != are the only operators supported right now
                  if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
                    if (termsProcessed > 0 && !(previousKey.equals(key1))) {
                      newTerm = processElementHeader(sqlFilter, newTerm, 0);
                    } else {
                      if (termsProcessed > 0 && key1.equals("!=") && previousKey.equals(key1)) {
                        //if you're doing multiple != terms in a row, what you really want is an AND not an OR
                        newTerm = processElementHeader(sqlFilter, newTerm, 0);
                      } else {
                        newTerm = processElementHeader(sqlFilter, newTerm, termsProcessed);
                      }
                    }
                    
                    if (termsProcessed == 0) {
                      sqlFilter.append("(");
                    }
                    
                    sqlFilter.append(" (lower(c.namefirst) " + key1 + " '" + key2 + "' )");
                    previousKey = key1;
                    processElementType(sqlFilter, elementType);
                    termsProcessed++;
                  }
                }
              }
              if (termsProcessed > 0) {
                sqlFilter.append(")");
              }
        }
            
        //last names
        if (nameLastHash != null && nameLastHash.size() > 0) {
          Iterator outer = nameLastHash.keySet().iterator();

          termsProcessed = 0;
          String previousKey = null;
          
          while(outer.hasNext()) {
            String key1 = (String)outer.next();
            HashMap innerHash = (HashMap)nameLastHash.get(key1);
            Iterator inner = innerHash.keySet().iterator();
            
               while(inner.hasNext()) {
                  String key2 = (String)inner.next();
                  int elementType = Integer.parseInt(((String)innerHash.get(key2)).toString());
                  
                  //equals and != are the only operators supported right now
                  if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
                    if (termsProcessed > 0 && !(previousKey.equals(key1))) {
                      newTerm = processElementHeader(sqlFilter, newTerm, 0);
                    } else {
                      if (termsProcessed > 0 && key1.equals("!=") && previousKey.equals(key1)) {
                        //if you're doing multiple != terms in a row, what you really want is an AND not an OR
                        newTerm = processElementHeader(sqlFilter, newTerm, 0);
                      } else {
                        newTerm = processElementHeader(sqlFilter, newTerm, termsProcessed);
                      }
                    }
                     
                    if (termsProcessed == 0) {
                      sqlFilter.append("(");
                    }
                    
                    sqlFilter.append(" (lower(c.namelast) " + key1 + " '" + key2 + "' )");
                    previousKey = key1;
                    processElementType(sqlFilter, elementType);
                    termsProcessed++;
                  }
                }
              }
              
              if (termsProcessed > 0) {
                sqlFilter.append(") ");
              }              
              
         }         
            
        //Entered Dates
        if (dateHash != null && dateHash.size() > 0) {
          Iterator outer = dateHash.keySet().iterator();
          termsProcessed = 0;
          String previousKey = null;
          
          while(outer.hasNext()) {
            String key1 = (String)outer.next();
            HashMap innerHash = (HashMap)dateHash.get(key1);
            
            Iterator inner = innerHash.keySet().iterator();
            
               while(inner.hasNext()) {
                  String key2 = (String)inner.next();
                  int elementType = Integer.parseInt(((String)innerHash.get(key2)).toString());
                  
                  if (elementType == y && (key1.equals("<") || key1.equals(">") || key1.equals("<=") || key1.equals(">="))) {
                    
                    if (termsProcessed > 0 && !(previousKey.equals(key1))) {
                        //we want to get an 'AND' term if we have switched between operators here
                        //for example, enteredDate less than x AND enteredDate greater than y
                        newTerm = processElementHeader(sqlFilter, newTerm, 0);
                    } else {
                        newTerm = processElementHeader(sqlFilter, newTerm, termsProcessed);
                    }
                    
                    if (termsProcessed == 0) {
                      sqlFilter.append("(");
                    }
                    
                    sqlFilter.append(" (c.entered " + key1 + " '" + key2 + "') ");
                    
                    previousKey = key1;
                    processElementType(sqlFilter, elementType);
                    termsProcessed++;
                  }
                }
              }

              if (termsProcessed > 0) {
                sqlFilter.append(")");
              }
            }               
            
        //zip codes
        if (zipHash != null && zipHash.size() > 0) {
          Iterator outer = zipHash.keySet().iterator();

          termsProcessed = 0;
          String previousKey = null;
          
          while(outer.hasNext()) {
            String key1 = (String)outer.next();
            HashMap innerHash = (HashMap)zipHash.get(key1);
            Iterator inner = innerHash.keySet().iterator();
            
               while(inner.hasNext()) {
                  String key2 = (String)inner.next();
                  int elementType = Integer.parseInt(((String)innerHash.get(key2)).toString());
                  
                  //equals and != are the only operators supported right now
                  if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
                    if (termsProcessed > 0 && !(previousKey.equals(key1))) {
                      newTerm = processElementHeader(sqlFilter, newTerm, 0);
                    } else {
                      if (termsProcessed > 0 && key1.equals("!=") && previousKey.equals(key1)) {
                        //if you're doing multiple != terms in a row, what you really want is an AND not an OR
                        newTerm = processElementHeader(sqlFilter, newTerm, 0);
                      } else {
                        newTerm = processElementHeader(sqlFilter, newTerm, termsProcessed);
                      }
                    }
                     
                    if (termsProcessed == 0) {
                      sqlFilter.append("(");
                    }
                    
                    sqlFilter.append(" (c.contact_id in (select distinct contact_id from contact_address where address_type = 1 and postalcode " + key1 + " '" + key2 + "' )) ");
                    previousKey = key1;
                    processElementType(sqlFilter, elementType);
                    termsProcessed++;
                  }
                }
              }
              if (termsProcessed > 0) {
                sqlFilter.append(")");
              }
        }
            
        //contact types
        if (typeIdHash != null && typeIdHash.size() > 0) {
          Iterator outer = typeIdHash.keySet().iterator();
          
          termsProcessed = 0;
          String previousKey = null;
          
          while(outer.hasNext()) {
            String key1 = (String)outer.next();
            HashMap innerHash = (HashMap)typeIdHash.get(key1);
            Iterator inner = innerHash.keySet().iterator();
            
               while(inner.hasNext()) {
                  String key2 = (String)inner.next();
                  int elementType = Integer.parseInt(((String)innerHash.get(key2)).toString());
                  
                  //equals and != are the only operators supported right now
                  if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
                    if (termsProcessed > 0 && !(previousKey.equals(key1))) {
                      newTerm = processElementHeader(sqlFilter, newTerm, 0);
                    } else {
                      if (termsProcessed > 0 && key1.equals("!=") && previousKey.equals(key1)) {
                        //if you're doing multiple != terms in a row, what you really want is an AND not an OR
                        newTerm = processElementHeader(sqlFilter, newTerm, 0);
                      } else {
                        newTerm = processElementHeader(sqlFilter, newTerm, termsProcessed);
                      }
                    }
                     
                    if (termsProcessed == 0) {
                      sqlFilter.append("(");
                    }
                    
                    sqlFilter.append(" (c.type_id  " + key1 + " '" + key2 + "' ) ");
                    previousKey = key1;
                    processElementType(sqlFilter, elementType);
                    termsProcessed++;
                  }
                }
              }

              if (termsProcessed > 0) {
                sqlFilter.append(") ");
              }              
            }
            
            
      //account types
      if (accountTypeIdHash != null && accountTypeIdHash.size() > 0) {
        Iterator outer = accountTypeIdHash.keySet().iterator();
        
        termsProcessed = 0;
        String previousKey = null;
          
        while(outer.hasNext()) {
          String key1 = (String)outer.next();
          HashMap innerHash = (HashMap)accountTypeIdHash.get(key1);
          Iterator inner = innerHash.keySet().iterator();
          
             while(inner.hasNext()) {
                String key2 = (String)inner.next();
                int elementType = Integer.parseInt(((String)innerHash.get(key2)).toString());
                
                //equals and != are the only operators supported right now
                if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
                  if (termsProcessed > 0 && !(previousKey.equals(key1))) {
                    newTerm = processElementHeader(sqlFilter, newTerm, 0);
                  } else {
                    if (termsProcessed > 0 && key1.equals("!=") && previousKey.equals(key1)) {
                      //if you're doing multiple != terms in a row, what you really want is an AND not an OR
                      newTerm = processElementHeader(sqlFilter, newTerm, 0);
                    } else {
                      newTerm = processElementHeader(sqlFilter, newTerm, termsProcessed);
                    }
                  }
                  
                  if (termsProcessed == 0) {
                    sqlFilter.append("(");
                  }
                  
                  sqlFilter.append(" (c.org_id in (SELECT org_id FROM account_type_levels WHERE type_id " + key1 + " '" + key2 + "')) ");
                  previousKey = key1;
                  processElementType(sqlFilter, elementType);
                  termsProcessed++;
                }
              }
            }

            if (termsProcessed > 0) {
              sqlFilter.append(") ");
            }              
          }
            
        //area codes
        if (areaCodeHash != null && areaCodeHash.size() > 0) {
          Iterator outer = areaCodeHash.keySet().iterator();
          termsProcessed = 0;
          String previousKey = null;
          
          while(outer.hasNext()) {
            String key1 = (String)outer.next();
            HashMap innerHash = (HashMap)areaCodeHash.get(key1);
            Iterator inner = innerHash.keySet().iterator();
            
               while(inner.hasNext()) {
                  String key2 = (String)inner.next();
                  int elementType = Integer.parseInt(((String)innerHash.get(key2)).toString());
                  
                  //equals and != are the only operators supported right now
                  if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
                    if (termsProcessed > 0 && !(previousKey.equals(key1))) {
                      newTerm = processElementHeader(sqlFilter, newTerm, 0);
                    } else {
                      if (termsProcessed > 0 && key1.equals("!=") && previousKey.equals(key1)) {
                        //if you're doing multiple != terms in a row, what you really want is an AND not an OR
                        newTerm = processElementHeader(sqlFilter, newTerm, 0);
                      } else {
                        newTerm = processElementHeader(sqlFilter, newTerm, termsProcessed);
                      }
                    }
                    
                    if (termsProcessed == 0) {
                      sqlFilter.append("(");
                    }
                    
                    sqlFilter.append(" (c.contact_id in (select distinct contact_id from contact_phone where phone_type = 1 and substr(number,2,3) " + key1 + " '" + key2 + "' )) ");
                    previousKey = key1;
                    processElementType(sqlFilter, elementType);
                    termsProcessed++;
                  }
                }
              }
              if (termsProcessed > 0) {
                sqlFilter.append(")");
              }
            }    
            
        //cities
        if (cityHash != null && cityHash.size() > 0) {
          Iterator outer = cityHash.keySet().iterator();
          termsProcessed = 0;
          String previousKey = null;
          
          while(outer.hasNext()) {
            String key1 = (String)outer.next();
            HashMap innerHash = (HashMap)cityHash.get(key1);
            Iterator inner = innerHash.keySet().iterator();
            
               while(inner.hasNext()) {
                  String key2 = (String)inner.next();
                  int elementType = Integer.parseInt(((String)innerHash.get(key2)).toString());
                  
                  //equals and != are the only operators supported right now
                  if (elementType == y && (key1.equals("=") || key1.equals("!="))) {
                    if (termsProcessed > 0 && !(previousKey.equals(key1))) {
                      newTerm = processElementHeader(sqlFilter, newTerm, 0);
                    } else {
                      if (termsProcessed > 0 && key1.equals("!=") && previousKey.equals(key1)) {
                        //if you're doing multiple != terms in a row, what you really want is an AND not an OR
                        newTerm = processElementHeader(sqlFilter, newTerm, 0);
                      } else {
                        newTerm = processElementHeader(sqlFilter, newTerm, termsProcessed);
                      }
                    }
                    
                    if (termsProcessed == 0) {
                      sqlFilter.append("(");
                    }
                    
                    sqlFilter.append(" (c.contact_id in (select distinct contact_id from contact_address where address_type = 1 and lower(city) " + key1 + " '" + key2 + "' )) ");
                    previousKey = key1;
                    processElementType(sqlFilter, elementType);
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
      
    } else {
      if (typeId != -1) {
        sqlFilter.append("AND c.type_id = ? ");
      }
      
      if (ownerIdRange != null) {
        sqlFilter.append("AND c.owner IN (" + ownerIdRange + ") ");
      }
      
      sqlFilter.append("AND ( lower(c.namelast) like lower(?) OR lower(c.namefirst) like lower(?) OR lower(c.company) like lower(?) ) ");
      
      if (personalId != -1) {
        sqlFilter.append("AND ( (c.type_id != 2 OR c.type_id is NULL) OR (c.type_id = 2 AND c.owner = ?) ) ");
      }
    }
    
  }
  
  
  /**
   *  Sets the parameters for the preparedStatement - these items must
   *  correspond with the createFilter statement
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.3
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    
    if (searchText == null || (searchText.equals(""))) {
      
      if (orgId != -1) {
        pst.setInt(++i, orgId);
      }
      
      if (includeEnabled == TRUE) {
        pst.setBoolean(++i, true);
      } else if (includeEnabled == FALSE) {
        pst.setBoolean(++i, false);
      }
      
      if (owner != -1) {
        pst.setInt(++i, owner);
      }
      
      if (typeId != -1) {
        pst.setInt(++i, typeId);
      }
      
      if (departmentId != -1) {
        pst.setInt(++i, departmentId);
      }
      
      if (projectId != -1) {
        pst.setInt(++i, projectId);
      }
      
      if (firstName != null) {
        pst.setString(++i, firstName);
      }
      
      if (middleName != null) {
        pst.setString(++i, middleName);
      }
      
      if (lastName != null) {
        pst.setString(++i, lastName);
      }
      
      if (title != null) {
        pst.setString(++i, title);
      }
      
      if (company != null) {
        pst.setString(++i, company);
      }
      
      if (includeEnabledUsersOnly) {
        pst.setBoolean(++i, true);
      }
      
      switch (personalId) {
        case -2: break;
        case -1: break;
        default: pst.setInt(++i, personalId); break;
      }
      if (ignoreTypeIdList.size() > 0) {
        Iterator iList = ignoreTypeIdList.iterator();
        while (iList.hasNext()) {
          int thisType = Integer.parseInt((String) iList.next());
          pst.setInt(++i, thisType);
        }
      }
    } else {
      if (typeId != -1) {
        pst.setInt(++i, typeId);
      }
      
      pst.setString(++i, searchText);
      pst.setString(++i, searchText);
      pst.setString(++i, searchText);
      
      if (personalId != -1) {
        pst.setInt(++i, personalId);
      }
    }
    
    return i;
  }
  
  public int reassignElements(Connection db, int newOwner) throws SQLException {
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
  
  public void processElementType(StringBuffer sqlFilter, int type) {
        switch(type) {
        case SearchCriteriaList.SOURCE_MY_CONTACTS:
          sqlFilter.append(" AND c.owner = " + sclOwnerId + " ");
          sqlFilter.append(" AND (c.type_id not in (" + Contact.EMPLOYEE_TYPE + ") OR c.type_id is null) ");
          break;
        case SearchCriteriaList.SOURCE_ALL_CONTACTS:
          sqlFilter.append(" AND c.owner in (" + sclOwnerIdRange + ") ");
          sqlFilter.append(" AND (c.type_id not in (" + Contact.EMPLOYEE_TYPE + ") OR c.type_id is null) ");
          break;        
        case SearchCriteriaList.SOURCE_ALL_ACCOUNTS:
          sqlFilter.append(" AND c.org_id > 0 ");
          sqlFilter.append(" AND (c.type_id not in (" + Contact.EMPLOYEE_TYPE + ") OR c.type_id is null) ");
          break;
        case SearchCriteriaList.SOURCE_EMPLOYEES:
          sqlFilter.append(" AND (c.type_id = " + Contact.EMPLOYEE_TYPE + ") ");
          break;
        default:
        break;
        }
  }
  
  public boolean processElementHeader(StringBuffer sqlFilter, boolean newTerm, int termsProcessed) {
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
  
  public static int retrieveRecordCount(Connection db, int moduleId, int itemId) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
      "SELECT COUNT(*) as itemcount " +
      "FROM contact c " +
      "WHERE contact_id > 0 ");
    if (moduleId == Constants.ACCOUNTS) {  
      sql.append("AND c.org_id = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (moduleId == Constants.ACCOUNTS) {
      pst.setInt(1, itemId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("itemcount");
    }
    rs.close();
    pst.close();
    return count;
  }
}

