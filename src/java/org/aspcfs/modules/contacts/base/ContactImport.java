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
import com.zeroio.iteam.base.FileItem;
import org.aspcfs.apps.transfer.reader.mapreader.Property;
import org.aspcfs.apps.transfer.reader.mapreader.PropertyMap;
import org.aspcfs.controller.ImportManager;
import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddress;
import org.aspcfs.modules.accounts.base.OrganizationPhoneNumber;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Import;
import org.aspcfs.utils.CFSFileReader;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.formatter.AddressFormatter;
import org.aspcfs.utils.formatter.ContactNameFormatter;
import org.aspcfs.utils.formatter.EmailAddressFormatter;
import org.aspcfs.utils.formatter.PhoneNumberFormatter;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.StateSelect;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents importer for Contacts
 * 
 * @author Mathur
 * @version $Id: ContactImport.java,v 1.7.12.1 2004/11/12 19:55:25 mrajkowski
 *          Exp $
 * @created March 30, 2004
 */
public class ContactImport extends Import implements Runnable {
  public final static String fs = System.getProperty("file.separator");
  private PropertyMap propertyMap = null;
  private String filePath = null;
  private int owner = -1;
  private String ownerName = null;
  private int userId = -1;
  private int accessTypeId = -1;
  private boolean lookupAccount = false;
  private ImportManager manager = null;
  private Connection db = null;
  private ConnectionElement connectionElement = null;
  private File errorFile = null;
  private BufferedWriter fos = null;
  private FileItem fileItem = null;
  private Thread importThread = null;
  private boolean lead = false;
  private int leadStatus = -1;
  private int ownerSiteId = -1;

  /**
   * Constructor for the ContactImport object
   */
  public ContactImport() {
  }

  /**
   * Constructor for the ContactImport object
   * 
   * @param db
   *          Description of the Parameter
   * @param importId
   *          Description of the Parameter
   * @throws SQLException
   *           Description of the Exception
   */
  public ContactImport(Connection db, int importId) throws SQLException {
    super(db, importId);
  }

  /**
   * Sets the properties attribute of the ContactImport object
   * 
   * @param request
   *          The new properties value
   */
  public void setProperties(HttpServletRequest request) {
    if (request.getParameter("owner") != null) {
      setOwner(request.getParameter("owner"));
    }
    if (request.getParameter("accessType") != null) {
      setAccessTypeId(request.getParameter("accessType"));
    }
  }

  /**
   * Sets the owner attribute of the ContactImport object
   * 
   * @param tmp
   *          The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }

  /**
   * Sets the owner attribute of the ContactImport object
   * 
   * @param tmp
   *          The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }

  /**
   * Gets the ownerName attribute of the ContactImport.java object
   * 
   * @return the ownerName
   */
  public String getOwnerName() {
    return ownerName;
  }

  /**
   * Sets the properties attribute of the ContactImport.java object
   * 
   * @param ownerName
   *          the ownerName to set
   */
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  /**
   * Sets the accessTypeId attribute of the ContactImport object
   * 
   * @param tmp
   *          The new accessTypeId value
   */
  public void setAccessTypeId(int tmp) {
    this.accessTypeId = tmp;
  }

  /**
   * Sets the accessTypeId attribute of the ContactImport object
   * 
   * @param tmp
   *          The new accessTypeId value
   */
  public void setAccessTypeId(String tmp) {
    this.accessTypeId = Integer.parseInt(tmp);
  }

  /**
   * Sets the userId attribute of the ContactImport object
   * 
   * @param tmp
   *          The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }

  /**
   * Sets the userId attribute of the ContactImport object
   * 
   * @param tmp
   *          The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }

  /**
   * Sets the lookupAccount attribute of the ContactImport object
   * 
   * @param tmp
   *          The new lookupAccount value
   */
  public void setLookupAccount(boolean tmp) {
    this.lookupAccount = tmp;
  }

  /**
   * Sets the lookupAccount attribute of the ContactImport object
   * 
   * @param tmp
   *          The new lookupAccount value
   */
  public void setLookupAccount(String tmp) {
    this.lookupAccount = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the propertyMap attribute of the ContactImport object
   * 
   * @param tmp
   *          The new propertyMap value
   */
  public void setPropertyMap(PropertyMap tmp) {
    this.propertyMap = tmp;
  }

  /**
   * Sets the filePath attribute of the ContactImport object
   * 
   * @param tmp
   *          The new filePath value
   */
  public void setFilePath(String tmp) {
    this.filePath = tmp;
  }

  /**
   * Sets the db attribute of the ContactImport object
   * 
   * @param tmp
   *          The new db value
   */
  public void setDb(Connection tmp) {
    this.db = tmp;
  }

  /**
   * Sets the manager attribute of the ContactImport object
   * 
   * @param tmp
   *          The new manager value
   */
  public void setManager(ImportManager tmp) {
    this.manager = tmp;
  }

  /**
   * Sets the errorFile attribute of the ContactImport object
   * 
   * @param tmp
   *          The new errorFile value
   */
  public void setErrorFile(File tmp) {
    this.errorFile = tmp;
  }

  /**
   * Sets the fileItem attribute of the ContactImport object
   * 
   * @param tmp
   *          The new fileItem value
   */
  public void setFileItem(FileItem tmp) {
    this.fileItem = tmp;
  }

  /**
   * Sets the importThread attribute of the ContactImport object
   * 
   * @param tmp
   *          The new importThread value
   */
  public void setImportThread(Thread tmp) {
    this.importThread = tmp;
  }

  /**
   * Gets the importThread attribute of the ContactImport object
   * 
   * @return The importThread value
   */
  public Thread getImportThread() {
    return importThread;
  }

  /**
   * Gets the fileItem attribute of the ContactImport object
   * 
   * @return The fileItem value
   */
  public FileItem getFileItem() {
    return fileItem;
  }

  /**
   * Gets the errorFile attribute of the ContactImport object
   * 
   * @return The errorFile value
   */
  public File getErrorFile() {
    return errorFile;
  }

  /**
   * Gets the manager attribute of the ContactImport object
   * 
   * @return The manager value
   */
  public ImportManager getManager() {
    return manager;
  }

  /**
   * Gets the db attribute of the ContactImport object
   * 
   * @return The db value
   */
  public Connection getDb() {
    return db;
  }

  /**
   * Gets the filePath attribute of the ContactImport object
   * 
   * @return The filePath value
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Gets the propertyMap attribute of the ContactImport object
   * 
   * @return The propertyMap value
   */
  public PropertyMap getPropertyMap() {
    return propertyMap;
  }

  /**
   * Gets the lookupAccount attribute of the ContactImport object
   * 
   * @return The lookupAccount value
   */
  public boolean getLookupAccount() {
    return lookupAccount;
  }

  /**
   * Gets the userId attribute of the ContactImport object
   * 
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Gets the owner attribute of the ContactImport object
   * 
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }

  /**
   * Gets the accessTypeId attribute of the ContactImport object
   * 
   * @return The accessTypeId value
   */
  public int getAccessTypeId() {
    return accessTypeId;
  }

  /**
   * Sets the connectionElement attribute of the ContactImport object
   * 
   * @param tmp
   *          The new connectionElement value
   */
  public void setConnectionElement(ConnectionElement tmp) {
    this.connectionElement = tmp;
  }

  /**
   * Gets the connectionElement attribute of the ContactImport object
   * 
   * @return The connectionElement value
   */
  public ConnectionElement getConnectionElement() {
    return connectionElement;
  }

  /**
   * Gets the lead attribute of the ContactImport object
   * 
   * @return The lead value
   */
  public boolean getLead() {
    return lead;
  }

  /**
   * Sets the lead attribute of the ContactImport object
   * 
   * @param tmp
   *          The new lead value
   */
  public void setLead(boolean tmp) {
    this.lead = tmp;
  }

  /**
   * Sets the lead attribute of the ContactImport object
   * 
   * @param tmp
   *          The new lead value
   */
  public void setLead(String tmp) {
    this.lead = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the leadStatus attribute of the ContactImport object
   * 
   * @return The leadStatus value
   */
  public int getLeadStatus() {
    return leadStatus;
  }

  /**
   * Sets the leadStatus attribute of the ContactImport object
   * 
   * @param tmp
   *          The new leadStatus value
   */
  public void setLeadStatus(int tmp) {
    this.leadStatus = tmp;
  }

  /**
   * Sets the leadStatus attribute of the ContactImport object
   * 
   * @param tmp
   *          The new leadStatus value
   */
  public void setLeadStatus(String tmp) {
    this.leadStatus = Integer.parseInt(tmp);
  }

  /**
   * Description of the Method
   */
  public void start() {
    importThread = new Thread(this);
    importThread.start();
  }

  /**
   * Process the import
   */
  public void run() {
    ArrayList thisRecord = new ArrayList();
    String line = null;
    StringBuffer error = new StringBuffer();
    int recordCount = 1;
    boolean recordInserted = false;
    boolean done = false;
    ArrayList header = null;
    Thread currentThread = Thread.currentThread();
    ContactNameFormatter nameFormatter = new ContactNameFormatter();
    boolean withoutContact = false;

    try {
      // get connection from the manager
      db = manager.getConnection(connectionElement);

      // create a temporary log file under the $FILELIBRARY/database
      errorFile = new File(filePath + "_error");
      fos = new BufferedWriter(new FileWriter(errorFile));

      if (db == null) {
        // FATAL
        throw new Exception("FATAL: Could not get database connection!");
      }

      // update status
      if (updateStatus(db, RUNNING) < 1) {
        // FATAL
        throw new Exception("Error updating status to RUNNING");
      }

      // Read the file in
      CFSFileReader fileReader = new CFSFileReader(filePath, this.getFileType());

      // header
      CFSFileReader.Record record = fileReader.nextLine();
      header = record.data;

      // add the header to the log file
      recordError(null, record.line, 0);

      // get salutation list
      LookupList salutationList = new LookupList(db, "lookup_title");
      LookupList emailAddressTypeList = new LookupList(db,
          "lookup_contactemail_types");
      LookupList addressTypeList = new LookupList(db,
          "lookup_contactaddress_types");
      LookupList phoneNumberTypeList = new LookupList(db,
          "lookup_contactphone_types");
      SICCodeList sicCodeList = new SICCodeList(db);
      LookupList siteIdList = new LookupList(db, "lookup_site_id");

      UserList userList = new UserList();
      userList.setBuildContact(true);
      userList.setSiteId(this.getSiteId());
			if (this.getSiteId() != -1){ 
				userList.setIncludeUsersWithAccessToAllSites(true);
			}
      userList.buildList(db);

      // process data
      while (importThread == currentThread && !done) {
        if ((record = fileReader.nextLine()) != null) {
          if (error.length() > 0) {
            error.delete(0, error.length());
          }
          recordInserted = false;
          withoutContact = false;
          ++recordCount;

          try {

            if (!record.isEmpty()) {
              // get the record
              thisRecord = record.data;

              // get the line and pad it if necessary for missing columns
              line = fileReader.padLine(record.line, header.size()
                  - thisRecord.size());

              Contact thisContact = new Contact();
              thisContact.setImportId(this.getId());
              thisContact.setStatusId(Import.PROCESSED_UNAPPROVED);
              thisContact.setSiteId(this.getSiteId());
              thisContact.setRating(this.getRating());
              thisContact.setComments(this.getComments());
              // set contact properties
              String nameLast = this.getValue(thisRecord, propertyMap
                  .getProperty("nameLast"));
              if (!"".equals(StringUtils.toString(nameLast))) {
                thisContact.setNameLast(nameLast);
              } else {
                String nameFull = this.getValue(thisRecord, propertyMap
                    .getProperty("nameFull"));
                if (!"".equals(StringUtils.toString(nameFull))) {
                  nameFormatter.format(thisContact, nameFull);
                }
                if ("".equals(StringUtils.toString(thisContact.getNameLast()))) {
                  // Last Name not found.
                  if (getType() == Constants.IMPORT_ACCOUNT_CONTACTS) {
                    withoutContact = true;
                  } else {
                    // Check to see if company information is found else log
                    // error
                    String company = this.getValue(thisRecord, propertyMap
                        .getProperty("company"));
                    if ("".equals(StringUtils.toString(company))) {
                      // log the record as failed since both last name and
                      // company information missing
                      error
                          .append("Last Name or Company info should be present");
                    }
                  }
                }
              }

              String nameFirst = this.getValue(thisRecord, propertyMap
                  .getProperty("nameFirst"));
              if (!"".equals(StringUtils.toString(nameFirst))) {
                thisContact.setNameFirst(nameFirst);
              }
              thisContact.setNameMiddle(this.getValue(thisRecord, propertyMap
                  .getProperty("nameMiddle")));
              thisContact.setNameSuffix(this.getValue(thisRecord, propertyMap
                  .getProperty("nameSuffix")));
              thisContact.setAdditionalNames(this.getValue(thisRecord,
                  propertyMap.getProperty("additionalNames")));
              thisContact.setNickname(this.getValue(thisRecord, propertyMap
                  .getProperty("nickname")));
              thisContact.setBirthDate(this.getValue(thisRecord, propertyMap
                  .getProperty("birthDate")));
              thisContact.setRole(this.getValue(thisRecord, propertyMap
                  .getProperty("role")));
              thisContact.setTitle(this.getValue(thisRecord, propertyMap
                  .getProperty("title")));
              thisContact.setNotes(this.getValue(thisRecord, propertyMap
                  .getProperty("notes")));
              thisContact.setUrl(this.getValue(thisRecord, propertyMap
                  .getProperty("url")));

              // is the contact a lead
              if (lead) {
                thisContact.setIsLead(true);
              }

              if (leadStatus != -1) {
                thisContact.setLeadStatus(leadStatus);
              }

              // entered by
              String propertyValue = this.getValue(thisRecord, propertyMap
                  .getProperty("enteredBy"));
              if (!"".equals(StringUtils.toString(propertyValue))) {
                try {
                  User user = userList.getUserById(Integer
                      .parseInt(propertyValue));
                  thisContact.setEnteredBy((user != null ? user.getId() : -1));
                } catch (NumberFormatException nfe) {
                  thisContact.setEnteredBy(userList
                      .getUserIdByName(propertyValue));
                }
                if (thisContact.getEnteredBy() == -1) {
                  thisContact.setEnteredBy(userId);
                }
              } else {
                thisContact.setEnteredBy(userId);
              }

              // modified by
              propertyValue = this.getValue(thisRecord, propertyMap
                  .getProperty("modifiedBy"));
              if (!"".equals(StringUtils.toString(propertyValue))) {
                try {
                  User user = userList.getUserById(Integer
                      .parseInt(propertyValue));
                  thisContact.setModifiedBy((user != null ? user.getId() : -1));
                } catch (NumberFormatException nfe) {
                  thisContact.setModifiedBy(userList
                      .getUserIdByName(propertyValue));
                }
                if (thisContact.getModifiedBy() == -1) {
                  thisContact.setModifiedBy(userId);
                }
              } else {
                thisContact.setModifiedBy(userId);
              }

              // owner
              String ownerIdProp = this.getValue(thisRecord, propertyMap
                  .getProperty("ownerId"));
              if (!"".equals(StringUtils.toString(ownerIdProp))) {
                try {
                  User user = userList.getUserById(Integer
                      .parseInt(ownerIdProp));
                  thisContact.setOwner((user != null ? user.getId() : -1));
                } catch (NumberFormatException nfe) {
                  thisContact.setOwner(userList.getUserIdByName(ownerIdProp));
                }
              }
              // owner name
              if (thisContact.getOwner() < 0) {
                String ownerNameProp = this.getValue(thisRecord, propertyMap
                    .getProperty("ownerName"));
                if (!"".equals(StringUtils.toString(ownerNameProp))) {
                  thisContact.setOwner(userList.getUserIdByName(ownerNameProp));
                }
                if (thisContact.getOwner() < 0) {
                  if(!"".equals(StringUtils.toString(ownerIdProp)) || !"".equals(StringUtils.toString(ownerNameProp))){
                    error.append("Owner is not valid");
                  }
                  thisContact.setOwner(owner);
                }
              }
              // Site
              User user = userList.getUserById(thisContact.getOwner());
              int ownerSiteIdProp = user.getSiteId();
              propertyValue = this.getValue(thisRecord, propertyMap
                  .getProperty("siteId"));
              if (!"".equals(StringUtils.toString(propertyValue))) {
                try {
                  if (siteIdList.hasCode(Integer.parseInt(propertyValue))) {
                    thisContact.setSiteId(Integer.parseInt(propertyValue));
                  } else {
                    thisContact.setSiteId(-1);
                  }
                } catch (NumberFormatException e) {
                  System.out.println("number format exception siteId = "
                      + propertyValue);
                }
                if (thisContact.getSiteId() < 0) {
                  error.append("Site id is not valid");
                } else if (thisContact.getSiteId() != ownerSiteIdProp
                    && ownerSiteIdProp > 0) {
                  error
                      .append("Site name does not match the site of the owner");
                }
              } else {
                propertyValue = this.getValue(thisRecord, propertyMap
                    .getProperty("siteName"));
                if (!"".equals(StringUtils.toString(propertyValue))) {
                  thisContact.setSiteId(siteIdList
                      .getIdFromValue(propertyValue));
                  if (thisContact.getSiteId() < 0) {
                    error.append("Site name is not valid");
                  } else if (thisContact.getSiteId() != ownerSiteIdProp
                      && ownerSiteIdProp > 0) {
                    error
                        .append("Site name does not match the site of the owner");
                  }
                } else {
                  thisContact.setSiteId(super.getSiteId());
                }
              }
              if (super.getSiteId() > 0 && thisContact.getSiteId() < 0) {
                thisContact.setSiteId(super.getSiteId());
              }

              // access type
              propertyValue = this.getValue(thisRecord, propertyMap
                  .getProperty("accessType"));
              if (!"".equals(StringUtils.toString(propertyValue))) {
                thisContact.setAccessType(propertyValue);
              } else {
                thisContact.setAccessType(accessTypeId);
              }
              Property accountIndustryPropertyForContact = propertyMap
                  .getProperty("industryTempCode");
              propertyValue = this.getValue(thisRecord,
                  accountIndustryPropertyForContact);
              if (!"".equals(StringUtils.toString(propertyValue))) {
                LookupList contactIndustryTypeList = new LookupList(db,
                    "lookup_industry");
                int industryCode = contactIndustryTypeList
                    .getIdFromValue(propertyValue);
                if (industryCode == -1) {
                  LookupElement thisElement = new LookupElement();
                  thisElement.setDescription(propertyValue);
                  thisElement.insertElement(db, "lookup_industry");
                  industryCode = thisElement.getCode();
                  // invalidate cache
                  this.getSystemStatus().removeLookup("lookup_industry");
                }
                thisContact.setIndustryTempCode(industryCode);
              }

              propertyValue = this.getValue(thisRecord, propertyMap
                  .getProperty("potential"));
              propertyValue = StringUtils.replace(propertyValue, "$", "");
              propertyValue = StringUtils.replace(propertyValue, ",", "");
              thisContact.setPotential(StringUtils.parseDouble(propertyValue,
                  0.0));

              propertyValue = this.getValue(thisRecord, propertyMap
                  .getProperty("revenue"));
              propertyValue = StringUtils.replace(propertyValue, "$", "");
              propertyValue = StringUtils.replace(propertyValue, ",", "");
              thisContact.setRevenue(StringUtils.parseDouble(propertyValue, 0.0));
              if (thisContact.getRevenue() <= 0) {
                propertyValue = this.getValue(thisRecord, propertyMap
                    .getProperty("revenueInThousands"));
                propertyValue = StringUtils.replace(propertyValue, "$", "");
                propertyValue = StringUtils.replace(propertyValue, ",", "");
                thisContact.setRevenue(StringUtils.parseDouble(propertyValue,
                    0.0) * 1000);
              }

              thisContact.setEmployees(this.getValue(thisRecord, propertyMap
                  .getProperty("employees")));
              thisContact.setDunsType(this.getValue(thisRecord, propertyMap
                  .getProperty("dunsType")));
              thisContact.setYearStarted(this.getValue(thisRecord, propertyMap
                  .getProperty("yearStarted")));
              thisContact.setDunsNumber(this.getValue(thisRecord, propertyMap
                  .getProperty("dunsNumber")));
              thisContact.setBusinessNameTwo(this.getValue(thisRecord,
                  propertyMap.getProperty("businessNameTwo")));
									
							//SIC Description
              thisContact.setSicDescription(this.getValue(thisRecord,
                  propertyMap.getProperty("sicDescription")));

							//SIC Code
              propertyValue = this.getValue(thisRecord, propertyMap
                  .getProperty("sicCode"));
              thisContact.setSicCode(sicCodeList
                  .getIdFromConstantId(propertyValue));
              if (!"".equals(propertyValue) && propertyValue != null
                  && thisContact.getSicCode() < 0) {
                error.append("Error adding SIC");
							}
							if (thisContact.getSicCode() > 0){
								thisContact.setSicDescription(sicCodeList.getDescriptionByCode(thisContact.getSicCode()));
							}
              // salutation
              propertyValue = this.getValue(thisRecord, propertyMap
                  .getProperty("nameSalutation"));
              if (!(propertyValue == null || "".equals(propertyValue))) {
                int salutationId = salutationList.getIdFromValue(propertyValue);
                thisContact.setListSalutation(salutationId);
              }

              thisContact.setSource(this.getSourceType());
              // addresses
              ArrayList addressInstances = propertyMap
                  .getDependencyMapList("contactAddress");
              Iterator k = addressInstances.iterator();
              while (k.hasNext()) {
                PropertyMap thisMap = (PropertyMap) k.next();
                String type = this.getValue(thisRecord, thisMap
                    .getProperty("type"));
                ContactAddress address = new ContactAddress();
                address.setStreetAddressLine1(this.getValue(thisRecord, thisMap
                    .getProperty("streetAddressLine1")));
                address.setStreetAddressLine2(this.getValue(thisRecord, thisMap
                    .getProperty("streetAddressLine2")));
                address.setStreetAddressLine3(this.getValue(thisRecord, thisMap
                    .getProperty("streetAddressLine3")));
                address.setStreetAddressLine4(this.getValue(thisRecord, thisMap
                    .getProperty("streetAddressLine4")));
                address.setCity(this.getValue(thisRecord, thisMap
                    .getProperty("city")));
                address.setCountry(this.getValue(thisRecord, thisMap
                    .getProperty("country")));
                if (address.getCountry() == null
                    || "".equals(address.getCountry())
                    && this.getSystemStatus() != null) {
                  address.setCountry(this.getSystemStatus()
                      .getApplicationPrefs().get("SYSTEM.COUNTRY"));
                }
                StateSelect stateSelect = new StateSelect(address.getCountry());
                if (stateSelect.hasCountry(address.getCountry())) {
                  address.setState(this.getValue(thisRecord, thisMap
                      .getProperty("state")));
                } else {
                  address.setOtherState(this.getValue(thisRecord, thisMap
                      .getProperty("state")));
                }
                address.setZip(this.getValue(thisRecord, thisMap
                    .getProperty("zip")));
                address.setCounty(this.getValue(thisRecord, thisMap
                    .getProperty("county")));
                address.setLatitude(this.getValue(thisRecord, thisMap
                    .getProperty("latitude")));
                address.setLongitude(this.getValue(thisRecord, thisMap
                    .getProperty("longitude")));
                if (!"".equals(StringUtils.toString(type).trim())) {
                  try {
                    address.setType((new Integer(type).intValue()));
                  } catch (NumberFormatException nfe) {
                    address.setType(addressTypeList.getIdFromValue(type));
                  }
                } else {
                  address
                      .setType((addressTypeList.get(0) != null ? ((LookupElement) addressTypeList
                          .get(0)).getCode()
                          : -1));
                }
                if (address.isValid()) {
                  AddressFormatter thisFormatter = new AddressFormatter();
                  thisFormatter.format(address);
                  if (address.isValid()) {
                    thisContact.getAddressList().add(address);
                  } else {
                    error.append("; Invalid Address");
                  }
                }
              }
              // email addresses
              ArrayList emailInstances = propertyMap
                  .getDependencyMapList("contactEmail");
              Iterator i = emailInstances.iterator();
              while (i.hasNext()) {
                PropertyMap thisMap = (PropertyMap) i.next();
                String email = this.getValue(thisRecord, thisMap
                    .getProperty("email"));
                String type = this.getValue(thisRecord, thisMap
                    .getProperty("type"));
                // format email
                EmailAddressFormatter thisFormatter = new EmailAddressFormatter();
                ContactEmailAddress emailAddress = new ContactEmailAddress();
                emailAddress.setEmail(email);
                if (!"".equals(StringUtils.toString(type))) {
                  try {
                    emailAddress.setType((new Integer(type).intValue()));
                  } catch (NumberFormatException nfe) {
                    emailAddress.setType(emailAddressTypeList
                        .getIdFromValue(type));
                  }
                } else {
                  emailAddress
                      .setType((emailAddressTypeList.get(0) != null ? ((LookupElement) emailAddressTypeList
                          .get(0)).getCode()
                          : -1));
                }
                if (emailAddress.isValid()) {
                  thisFormatter.format(emailAddress);
                  if (emailAddress.isValid()) {
                    thisContact.getEmailAddressList().add(emailAddress);
                  } else {
                    error.append("; Invalid Email");
                  }
                }
              }

              // phone numbers
              ArrayList phoneInstances = propertyMap
                  .getDependencyMapList("contactPhone");
              Iterator j = phoneInstances.iterator();
              while (j.hasNext()) {
                PropertyMap thisMap = (PropertyMap) j.next();
                String phone = this.getValue(thisRecord, thisMap
                    .getProperty("number"));
                String phoneExt = this.getValue(thisRecord, thisMap
                    .getProperty("extension"));
                String type = this.getValue(thisRecord, thisMap
                    .getProperty("type"));
                ContactPhoneNumber phoneNumber = new ContactPhoneNumber();
                if (!"".equals(StringUtils.toString(phone))) {
                  phoneNumber.setNumber(phone);
                  if (!"".equals(StringUtils.toString(phoneExt))) {
                    phoneNumber.setExtension(phoneExt);
                  }
                  if (!"".equals(StringUtils.toString(type))) {
                    try {
                      phoneNumber.setType((new Integer(type).intValue()));
                    } catch (NumberFormatException nfe) {
                      phoneNumber.setType(phoneNumberTypeList
                          .getIdFromValue(type));
                    }
                  } else {
                    phoneNumber
                        .setType((phoneNumberTypeList.get(0) != null ? ((LookupElement) phoneNumberTypeList
                            .get(0)).getCode()
                            : -1));
                  }
                  if (phoneNumber.isValid()) {
                    PhoneNumberFormatter.format(phoneNumber);
                    // check if number is still valid
                    if (phoneNumber.isValid()) {
                      thisContact.getPhoneNumberList().add(phoneNumber);
                    } else {
                      error.append("; Invalid Phone Number");
                    }
                  }
                }
              }
              // company
              Property companyProperty = propertyMap.getProperty("company");
              propertyValue = this.getValue(thisRecord, companyProperty);
              if (!"".equals(StringUtils.toString(propertyValue))) {
                thisContact.setCompany(propertyValue);
                thisContact.setOrgName(propertyValue);
              }

              // check if user wants to lookup the account
              if (lookupAccount) {
                if (propertyValue != null
                    && !"".equals(StringUtils.toString(propertyValue))) {
                  int orgId = Organization.lookupAccount(db, propertyValue,
                      this.getId(), thisContact.getSiteId());
                  if (orgId < 0) {
                    // add a new account
                    Organization thisOrg = new Organization();
                    thisOrg.setName(propertyValue);
                    thisOrg.setEnteredBy(userId);
                    thisOrg.setModifiedBy(userId);
                    thisOrg.setModifiedBy(userId);
                    thisOrg.setOwner(thisContact.getOwner());
                    thisOrg.setImportId(this.getId());
                    thisOrg.setPotential(thisContact.getPotential());
                    thisOrg.setStatusId(Import.PROCESSED_UNAPPROVED);
                    thisOrg.setSiteId(thisContact.getSiteId());
                    if(withoutContact){
                    copyAddressAndPhones(thisContact,
                        thisOrg);}
                    copyPropertiesFromContactToOrganization(thisContact,
                        thisOrg);
                    if (error.length() == 0) {
                      recordInserted = thisOrg.insert(db);
                      if (!recordInserted) {
                        error.append("; Error adding account");
                      } else {
                        orgId = thisOrg.getOrgId();
                      }
                    }
                  }
                  if (orgId > 0) {
                    Organization thisOrg = new Organization(db, orgId);
                    if (!thisOrg.getEnabled() || thisOrg.isTrashed()) {
                      orgId = -1;
                    }
                  }
                  thisContact.setOrgId(orgId);
                  thisContact.setOrgName(propertyValue);
                } else {
                  // Company Information NOT available. An individual account
                  // needs to be inserted
                  if (thisContact.getNameLastFirst() != null
                      && !"".equals(thisContact.getNameLastFirst())) {
                    Organization thisOrg = new Organization();
                    // Disable automatic insertion of primary contact
                    // information, since this contact is
                    // inserted in the end after inserting an organization
                    // record.
                    thisOrg.setInsertPrimaryContact(false);
                    thisOrg.setName(thisContact.getNameLastFirst());
                    thisOrg.setNameLast(thisContact.getNameLast());
                    thisOrg.setNameFirst(thisContact.getNameFirst());
                    thisOrg.setEnteredBy(userId);
                    thisOrg.setModifiedBy(userId);
                    thisOrg.setModifiedBy(userId);
                    thisOrg.setOwner(thisContact.getOwner());
                    thisOrg.setImportId(this.getId());
                    thisOrg.setPotential(thisContact.getPotential());
                    thisOrg.setStatusId(Import.PROCESSED_UNAPPROVED);
                    thisOrg.setSiteId(this.getSiteId());
                    if(withoutContact){
                      copyAddressAndPhones(thisContact,
                          thisOrg);}
                    copyPropertiesFromContactToOrganization(thisContact,
                        thisOrg);
                    if (error.length() == 0) {
                      recordInserted = thisOrg.insert(db);
                      if (!recordInserted) {
                        error.append("; Error adding account");
                      }
                      thisContact.setOrgId(thisOrg.getOrgId());
                      thisContact.setOrgName(thisContact.getNameLastFirst());
                      thisContact.setPrimaryContact(true);
                    }
                  } else {
                    error.append("; Invalid Individual Account Name");
                  }
                }
              }

              // account number
              Property accountNumberPropertyForContact = propertyMap
                  .getProperty("accountNumber");
              propertyValue = this.getValue(thisRecord,
                  accountNumberPropertyForContact);
              if (!"".equals(StringUtils.toString(propertyValue))) {
                thisContact.setAccountNumber(propertyValue);
              }

              // account number
              if (lookupAccount) {
                // check if user wants to lookup the account
                if (propertyValue != null
                    && !"".equals(StringUtils.toString(propertyValue))) {
                  int orgId = Organization.lookupAccount(db, thisContact
                      .getOrgName(), this.getId(), thisContact.getSiteId());
                  if (orgId > 0) {
                    // add the account number
                    Organization thisOrg = new Organization(db, orgId);
                    thisOrg.setAccountNumber(propertyValue);
                    thisOrg.setModifiedBy(userId);
                    int resultCount = thisOrg.update(db);
                    if (resultCount != 1) {
                      error.append("; Error adding account number");
                    }
                  }
                }
              }


              if (error.length() == 0) {
                // insert the contact
                boolean isValid = ObjectValidator.validate(null, db,
                    thisContact);
                if (isValid && !withoutContact) {
                  recordInserted = thisContact.insert(db);
                }
                if (recordInserted) {
                  incrementTotalImportedRecords();
                } else {
                  incrementTotalFailedRecords();
                }
              } else {
                recordError(error.toString(), line, recordCount);
              }
            }
          } catch (Exception unknownException) {
            unknownException.printStackTrace();
            if (error.length() == 0) {
              recordError(unknownException.toString(), line, recordCount);
            } else {
              recordError(
                  error.toString() + "; " + unknownException.toString(), line,
                  recordCount);
            }
          }
        } else {
          done = true;
        }
      }

      if (done) {
        this.setStatusId(Import.PROCESSED_UNAPPROVED);
      }
    } catch (Exception e) {
      e.printStackTrace();
      recordError(e.toString(), "", -1);
      try {
        // update status
        this.setStatusId(FAILED);
      } catch (Exception statusException) {
        e.printStackTrace();
        // error updating status
      }
    } finally {
      if (importThread == currentThread) {
        stop();
      }
    }
  }

  /**
   * @param thisContact
   * @param thisOrg
   */
  private static void copyAddressAndPhones(Contact thisContact, Organization thisOrg) {
    // addresses
    ContactAddressList addressInstances = thisContact.getAddressList();
    Iterator k = addressInstances.iterator();
    while (k.hasNext()) {
      ContactAddress contactAddress = (ContactAddress) k.next();
      OrganizationAddress  address = new OrganizationAddress();
      address.setStreetAddressLine1(contactAddress.getStreetAddressLine1());
      address.setStreetAddressLine2(contactAddress.getStreetAddressLine2());
      address.setStreetAddressLine3(contactAddress.getStreetAddressLine3());
      address.setStreetAddressLine4(contactAddress.getStreetAddressLine4());
      address.setCity(contactAddress.getCity());
      address.setCountry(contactAddress.getCountry());
      StateSelect stateSelect = new StateSelect(address.getCountry());
      if (stateSelect.hasCountry(address.getCountry())) {
        address.setState(contactAddress.getState());
      } else {
        address.setOtherState(contactAddress.getOtherState());
      }
      address.setZip(contactAddress.getZip());
      address.setCounty(contactAddress.getCounty());
      address.setLatitude(contactAddress.getLatitude());
      address.setLongitude(contactAddress.getLongitude());
      address.setType(contactAddress.getType());
       
      if (address.isValid()) {
        AddressFormatter thisFormatter = new AddressFormatter();
        thisFormatter.format(address);
        if (address.isValid()) {
          thisOrg.getAddressList().add(address);
        } 
      }
    }
    // email addresses
    ContactEmailAddressList emailInstances = thisContact.getEmailAddressList();
    Iterator i = emailInstances.iterator();
    while (i.hasNext()) {
      ContactEmailAddress contactEmailAddress = (ContactEmailAddress) i.next();
      // format email
      EmailAddressFormatter thisFormatter = new EmailAddressFormatter();
      OrganizationEmailAddress emailAddress = new OrganizationEmailAddress();
      emailAddress.setEmail(contactEmailAddress.getEmail());
      emailAddress.setType(contactEmailAddress.getType());
        
      if (emailAddress.isValid()) {
        thisFormatter.format(emailAddress);
        if (emailAddress.isValid()) {
          thisOrg.getEmailAddressList().add(emailAddress);
        } 
      }
    }

    // phone numbers
    ContactPhoneNumberList phoneInstances =thisContact.getPhoneNumberList();
    Iterator j = phoneInstances.iterator();
    while (j.hasNext()) {
      ContactPhoneNumber contactPhoneNumber = (ContactPhoneNumber) j.next();
      String phone = contactPhoneNumber.getNumber();
      String phoneExt = contactPhoneNumber.getExtension();
      int type = contactPhoneNumber.getType();
      OrganizationPhoneNumber phoneNumber = new OrganizationPhoneNumber();
      if (!"".equals(StringUtils.toString(phone))) {
        phoneNumber.setNumber(phone);
        if (!"".equals(StringUtils.toString(phoneExt))) {
          phoneNumber.setExtension(phoneExt);
        }
          phoneNumber.setType(type);
        if (phoneNumber.isValid()) {
          PhoneNumberFormatter.format(phoneNumber);
          // check if number is still valid
          if (phoneNumber.isValid()) {
            thisOrg.getPhoneNumberList().add(phoneNumber);
          }
        }
      }
    }  
    
  }

  private static void copyPropertiesFromContactToOrganization(Contact from,
      Organization to) {
    to.setRevenue(from.getRevenue());
    to.setEmployees(from.getEmployees());
    to.setDunsType(from.getDunsType());
    to.setYearStarted(from.getYearStarted());
    to.setDunsNumber(from.getDunsNumber());
    to.setBusinessNameTwo(from.getBusinessNameTwo());
    to.setSicDescription(from.getSicDescription());
    to.setSicCode(from.getSicCode());
    to.setNotes(from.getNotes());
  }

  /**
   * Cancel the import
   */
  public void cancel() {
    try {
      // do not use stop to stop the thread, nullify it
      importThread = null;

      // set the status to canceled
      this.setStatusId(Import.CANCELED);

      // perform clean up
      destroy();
    } catch (Exception e) {
    }
  }

  /**
   * Stops the thread
   */
  public void stop() {
    // do not use stop() to stop the thread, nullify it
    importThread = null;

    // perform clean up
    destroy();
  }

  /**
   * Description of the Method
   */
  public void destroy() {
    // set status of the import and clean up thread pool
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ContactImport -> Starting cleanup for ImportId: "
          + this.getId());
    }

    try {
      // update status, total imported/failed records
      recordResults(db);

      // flush the log file
      fos.flush();
      fos.close();

      if (this.getTotalFailedRecords() > 0) {
        // Store the error file as a version
        fileItem.setSubject("Error file");
        fileItem.setFilename(fileItem.getFilename() + "_error");
        fileItem.setClientFilename(this.getId() + "_error.csv");
        fileItem.setVersion(ERROR_FILE_VERSION);
        fileItem.setSize((int) errorFile.length());
        fileItem.insertVersion(db);
      } else {
        errorFile.delete();
      }

      // report back to manager
      manager.free(this);
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        e.printStackTrace();
      }
    } finally {
      // release the database connection
      manager.free(db);
    }
  }

  /**
   * Logs an error when a record fails
   * 
   * @param error
   *          Description of the Parameter
   * @param line
   *          Description of the Parameter
   * @param lineNumber
   *          Description of the Parameter
   */
  private void recordError(String error, String line, int lineNumber) {
    try {
      // log errors in the temp file created under $FILELIBRARY/_imports/
      if (lineNumber == 0) {
        // header:append error column
        line += "," + "\"_ERROR\"";
      } else if (lineNumber == -1) {
        // general error, mostly before import started
        line += error;
      } else if (lineNumber > 0) {
        // append the error
        line += ",\"" + error + "\"";

        // a record has failed, increment the failure count
        this.incrementTotalFailedRecords();
      }

      // add next line character
      // TODO: Change this to a CUSTOM row delimiter if import type is CUSTOM
      line += "\n";

      fos.write(line);
    } catch (IOException e) {
      // import should not fail because of logging error
    }
  }

  /**
   * Gets the value attribute of the ContactImport object
   * 
   * @param thisRecord
   *          Description of the Parameter
   * @param type
   *          Description of the Parameter
   * @return The value value
   */
  private String getValue(ArrayList thisRecord, Property type) {
    String value = null;
    if (type.getMappedColumn() > -1
        && thisRecord.size() > type.getMappedColumn()) {
      value = (String) thisRecord.get(type.getMappedColumn());
    } else if (!"".equals(StringUtils.toString(type.getDefaultValue()))) {
      value = type.getDefaultValue();
    }
    return value;
  }

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *           Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    int i = 0;
    int recordCount = 0;
    PreparedStatement pst = db
        .prepareStatement("SELECT count(*) as recordcount " + "FROM contact "
            + "WHERE import_id = ? ");
    pst.setInt(++i, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = rs.getInt("recordCount");
    }
    rs.close();
    pst.close();
    Dependency thisDependency = new Dependency();
    thisDependency.setName("contacts");
    thisDependency.setCount(recordCount);
    thisDependency.setCanDelete(true);
    dependencyList.add(thisDependency);
    return dependencyList;
  }

  /**
   * Deletes the import
   * 
   * @param db
   *          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *           Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);

      // delete all imported records
      if (this.getStatusId() != UNPROCESSED || this.getStatusId() != QUEUED) {
        deleteImportedRecords(db, this.getId());
      }

      // delete import record
      PreparedStatement pst = db.prepareStatement("DELETE from import "
          + "WHERE import_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

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
   * Deletes all imported records
   * 
   * @param db
   *          Description of the Parameter
   * @param thisImportId
   *          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *           Description of the Exception
   */
  public static boolean deleteImportedRecords(Connection db, int thisImportId)
      throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db
          .prepareStatement("DELETE FROM contact_emailaddress "
              + "WHERE EXISTS (SELECT contact_id from contact c where c.contact_id = contact_emailaddress.contact_id AND import_id = ?) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db
          .prepareStatement("DELETE FROM contact_phone "
              + "WHERE EXISTS (SELECT contact_id from contact c where c.contact_id = contact_phone.contact_id AND import_id = ?) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db
          .prepareStatement("DELETE FROM contact_address "
              + "WHERE EXISTS (SELECT contact_id from contact c where c.contact_id = contact_address.contact_id AND import_id = ?) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement("DELETE FROM contact " + "WHERE import_id = ?");
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
}
