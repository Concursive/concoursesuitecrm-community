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

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.database.ConnectionElement;
import java.sql.*;
import java.util.*;
import java.io.*;
import org.aspcfs.utils.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.zeroio.iteam.base.FileItem;
import org.aspcfs.modules.base.Import;
import org.aspcfs.controller.ImportManager;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.apps.transfer.reader.mapreader.*;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.utils.formatter.*;
import org.aspcfs.controller.ObjectValidator;

/**
 *  Represents importer for Contacts
 *
 * @author     Mathur
 * @created    March 30, 2004
 * @version    $Id: ContactImport.java,v 1.7.12.1 2004/11/12 19:55:25 mrajkowski
 *      Exp $
 */
public class ContactImport extends Import implements Runnable {
  public final static String fs = System.getProperty("file.separator");
  private PropertyMap propertyMap = null;
  private String filePath = null;
  private int owner = -1;
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



  /**
   *  Constructor for the ContactImport object
   */
  public ContactImport() { }


  /**
   *  Constructor for the ContactImport object
   *
   * @param  db                Description of the Parameter
   * @param  importId          Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ContactImport(Connection db, int importId) throws SQLException {
    super(db, importId);
  }


  /**
   *  Sets the properties attribute of the ContactImport object
   *
   * @param  request  The new properties value
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
   *  Sets the owner attribute of the ContactImport object
   *
   * @param  tmp  The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   *  Sets the owner attribute of the ContactImport object
   *
   * @param  tmp  The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   *  Sets the accessTypeId attribute of the ContactImport object
   *
   * @param  tmp  The new accessTypeId value
   */
  public void setAccessTypeId(int tmp) {
    this.accessTypeId = tmp;
  }


  /**
   *  Sets the accessTypeId attribute of the ContactImport object
   *
   * @param  tmp  The new accessTypeId value
   */
  public void setAccessTypeId(String tmp) {
    this.accessTypeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the userId attribute of the ContactImport object
   *
   * @param  tmp  The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   *  Sets the userId attribute of the ContactImport object
   *
   * @param  tmp  The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the lookupAccount attribute of the ContactImport object
   *
   * @param  tmp  The new lookupAccount value
   */
  public void setLookupAccount(boolean tmp) {
    this.lookupAccount = tmp;
  }


  /**
   *  Sets the lookupAccount attribute of the ContactImport object
   *
   * @param  tmp  The new lookupAccount value
   */
  public void setLookupAccount(String tmp) {
    this.lookupAccount = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the propertyMap attribute of the ContactImport object
   *
   * @param  tmp  The new propertyMap value
   */
  public void setPropertyMap(PropertyMap tmp) {
    this.propertyMap = tmp;
  }


  /**
   *  Sets the filePath attribute of the ContactImport object
   *
   * @param  tmp  The new filePath value
   */
  public void setFilePath(String tmp) {
    this.filePath = tmp;
  }


  /**
   *  Sets the db attribute of the ContactImport object
   *
   * @param  tmp  The new db value
   */
  public void setDb(Connection tmp) {
    this.db = tmp;
  }


  /**
   *  Sets the manager attribute of the ContactImport object
   *
   * @param  tmp  The new manager value
   */
  public void setManager(ImportManager tmp) {
    this.manager = tmp;
  }


  /**
   *  Sets the errorFile attribute of the ContactImport object
   *
   * @param  tmp  The new errorFile value
   */
  public void setErrorFile(File tmp) {
    this.errorFile = tmp;
  }


  /**
   *  Sets the fileItem attribute of the ContactImport object
   *
   * @param  tmp  The new fileItem value
   */
  public void setFileItem(FileItem tmp) {
    this.fileItem = tmp;
  }


  /**
   *  Sets the importThread attribute of the ContactImport object
   *
   * @param  tmp  The new importThread value
   */
  public void setImportThread(Thread tmp) {
    this.importThread = tmp;
  }


  /**
   *  Gets the importThread attribute of the ContactImport object
   *
   * @return    The importThread value
   */
  public Thread getImportThread() {
    return importThread;
  }


  /**
   *  Gets the fileItem attribute of the ContactImport object
   *
   * @return    The fileItem value
   */
  public FileItem getFileItem() {
    return fileItem;
  }


  /**
   *  Gets the errorFile attribute of the ContactImport object
   *
   * @return    The errorFile value
   */
  public File getErrorFile() {
    return errorFile;
  }


  /**
   *  Gets the manager attribute of the ContactImport object
   *
   * @return    The manager value
   */
  public ImportManager getManager() {
    return manager;
  }


  /**
   *  Gets the db attribute of the ContactImport object
   *
   * @return    The db value
   */
  public Connection getDb() {
    return db;
  }


  /**
   *  Gets the filePath attribute of the ContactImport object
   *
   * @return    The filePath value
   */
  public String getFilePath() {
    return filePath;
  }


  /**
   *  Gets the propertyMap attribute of the ContactImport object
   *
   * @return    The propertyMap value
   */
  public PropertyMap getPropertyMap() {
    return propertyMap;
  }


  /**
   *  Gets the lookupAccount attribute of the ContactImport object
   *
   * @return    The lookupAccount value
   */
  public boolean getLookupAccount() {
    return lookupAccount;
  }


  /**
   *  Gets the userId attribute of the ContactImport object
   *
   * @return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Gets the owner attribute of the ContactImport object
   *
   * @return    The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the accessTypeId attribute of the ContactImport object
   *
   * @return    The accessTypeId value
   */
  public int getAccessTypeId() {
    return accessTypeId;
  }


  /**
   *  Sets the connectionElement attribute of the ContactImport object
   *
   * @param  tmp  The new connectionElement value
   */
  public void setConnectionElement(ConnectionElement tmp) {
    this.connectionElement = tmp;
  }


  /**
   *  Gets the connectionElement attribute of the ContactImport object
   *
   * @return    The connectionElement value
   */
  public ConnectionElement getConnectionElement() {
    return connectionElement;
  }


  /**
   *  Gets the lead attribute of the ContactImport object
   *
   * @return    The lead value
   */
  public boolean getLead() {
    return lead;
  }


  /**
   *  Sets the lead attribute of the ContactImport object
   *
   * @param  tmp  The new lead value
   */
  public void setLead(boolean tmp) {
    this.lead = tmp;
  }


  /**
   *  Sets the lead attribute of the ContactImport object
   *
   * @param  tmp  The new lead value
   */
  public void setLead(String tmp) {
    this.lead = DatabaseUtils.parseBoolean(tmp);
  }

public int getLeadStatus() { return leadStatus; }
public void setLeadStatus(int tmp) { this.leadStatus = tmp; }
public void setLeadStatus(String tmp) { this.leadStatus = Integer.parseInt(tmp); }

  /**
   *  Description of the Method
   */
  public void start() {
    importThread = new Thread(this);
    importThread.start();
  }


  /**
   *  Process the import
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
    PhoneNumberFormatter phoneFormatter = new PhoneNumberFormatter();
    ContactNameFormatter nameFormatter = new ContactNameFormatter();

    try {
      //get connection from the manager
      db = manager.getConnection(connectionElement);

      //create a temporary log file under the $FILELIBRARY/database
      errorFile = new File(filePath + "_error");
      fos = new BufferedWriter(new FileWriter(errorFile));

      if (db == null) {
        //FATAL
        throw new Exception("FATAL: Could not get database connection!");
      }

      //update status
      if (updateStatus(db, RUNNING) < 1) {
        //FATAL
        throw new Exception("Error updating status to RUNNING");
      }

      //Read the file in
      CFSFileReader fileReader = new CFSFileReader(filePath, this.getFileType());

      //header
      CFSFileReader.Record record = fileReader.nextLine();
      header = record.data;

      //add the header to the log file
      recordError(null, record.line, 0);

      //process data
      while (importThread == currentThread && !done) {
        if ((record = fileReader.nextLine()) != null) {
          if (error.length() > 0) {
            error.delete(0, error.length());
          }
          recordInserted = false;
          ++recordCount;

          try {
            //get the record
            thisRecord = record.data;

            //get the line and pad it if necessary for missing columns
            line = fileReader.padLine(record.line, header.size() - thisRecord.size());

            Contact thisContact = new Contact();
            thisContact.setImportId(this.getId());
            thisContact.setStatusId(Import.PROCESSED_UNAPPROVED);
            //set contact properties
            String nameLast = this.getValue(thisRecord, propertyMap.getProperty("nameLast"));
            if (!"".equals(StringUtils.toString(nameLast))) {
              thisContact.setNameLast(nameLast);
            } else {
              String nameFull = this.getValue(thisRecord, propertyMap.getProperty("nameFull"));
              if (!"".equals(StringUtils.toString(nameFull))) {
                nameFormatter.format(thisContact, nameFull);
              }
              if ("".equals(StringUtils.toString(thisContact.getNameLast()))) {
                //log the record as failed
                error.append("Last Name is a required field");
              }
            }

            String nameFirst = this.getValue(thisRecord, propertyMap.getProperty("nameFirst"));
            if (!"".equals(StringUtils.toString(nameFirst))) {
              thisContact.setNameFirst(nameFirst);
            }
            thisContact.setNameMiddle(this.getValue(thisRecord, propertyMap.getProperty("nameMiddle")));
            thisContact.setNameSuffix(this.getValue(thisRecord, propertyMap.getProperty("nameSuffix")));
            thisContact.setTitle(this.getValue(thisRecord, propertyMap.getProperty("title")));
            thisContact.setNotes(this.getValue(thisRecord, propertyMap.getProperty("notes")));
            thisContact.setUrl(this.getValue(thisRecord, propertyMap.getProperty("url")));

            //is the contact a lead
            if (lead) {
              thisContact.setIsLead(true);
            }
            
            if (leadStatus != -1) {
              thisContact.setLeadStatus(leadStatus);
            }

            //entered by
            String propertyValue = this.getValue(thisRecord, propertyMap.getProperty("enteredBy"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisContact.setEnteredBy(propertyValue);
            } else {
              thisContact.setEnteredBy(userId);
            }

            //modified by
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("modifiedBy"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisContact.setModifiedBy(propertyValue);
            } else {
              thisContact.setModifiedBy(userId);
            }

            //owner
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("owner"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisContact.setOwner(propertyValue);
            } else {
              thisContact.setOwner(owner);
            }

            //access type
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("accessType"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisContact.setAccessType(propertyValue);
            } else {
              thisContact.setAccessType(accessTypeId);
            }

            //company
            Property companyProperty = propertyMap.getProperty("company");
            propertyValue = this.getValue(thisRecord, companyProperty);
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisContact.setCompany(propertyValue);
            }
            //check if user wants to lookup the account
            if (lookupAccount) {
              if (!"".equals(StringUtils.toString(propertyValue))) {
                int orgId = Organization.lookupAccount(db, propertyValue, this.getId());

                if (orgId < 0) {
                  //add a new account
                  Organization thisOrg = new Organization();
                  thisOrg.setName(propertyValue);
                  thisOrg.setEnteredBy(userId);
                  thisOrg.setModifiedBy(userId);
                  thisOrg.setModifiedBy(userId);
                  thisOrg.setOwner(owner);
                  thisOrg.setImportId(this.getId());
                  thisOrg.setStatusId(Import.PROCESSED_UNAPPROVED);
                  recordInserted = thisOrg.insert(db);
                  if (!recordInserted) {
                    error.append("; Error adding account");
                  } else {
                    orgId = thisOrg.getOrgId();
                  }
                }
                thisContact.setOrgId(orgId);
                thisContact.setOrgName(propertyValue);
              } else {
                error.append("; Invalid Account Name");
              }
            }

            //email addresses
            ArrayList emailInstances = propertyMap.getDependencyMapList("contactEmail");
            Iterator i = emailInstances.iterator();
            while (i.hasNext()) {
              PropertyMap thisMap = (PropertyMap) i.next();
              String email = this.getValue(thisRecord, thisMap.getProperty("email"));
              String type = this.getValue(thisRecord, thisMap.getProperty("type"));
              //format email
              EmailAddressFormatter thisFormatter = new EmailAddressFormatter();
              ContactEmailAddress emailAddress = new ContactEmailAddress();
              emailAddress.setEmail(email);
              if (!"".equals(StringUtils.toString(type))) {
                emailAddress.setType(type);
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

            //phone numbers
            ArrayList phoneInstances = propertyMap.getDependencyMapList("contactPhone");
            Iterator j = phoneInstances.iterator();
            while (j.hasNext()) {
              PropertyMap thisMap = (PropertyMap) j.next();
              String phone = this.getValue(thisRecord, thisMap.getProperty("number"));
              String phoneExt = this.getValue(thisRecord, thisMap.getProperty("extension"));
              String type = this.getValue(thisRecord, thisMap.getProperty("type"));
              ContactPhoneNumber phoneNumber = new ContactPhoneNumber();
              if (!"".equals(StringUtils.toString(phone))) {
                phoneNumber.setNumber(phone);
                if (!"".equals(StringUtils.toString(phoneExt))) {
                  phoneNumber.setExtension(phoneExt);
                }
                if (!"".equals(StringUtils.toString(type))) {
                  phoneNumber.setType(type);
                }
                if (phoneNumber.isValid()) {
                  phoneFormatter.format(phoneNumber);
                  //check if number is still valid
                  if (phoneNumber.isValid()) {
                    thisContact.getPhoneNumberList().add(phoneNumber);
                  } else {
                    error.append("; Invalid Phone Number");
                  }
                }
              }
            }

            //addresses
            ArrayList addressInstances = propertyMap.getDependencyMapList("contactAddress");
            Iterator k = addressInstances.iterator();
            while (k.hasNext()) {
              PropertyMap thisMap = (PropertyMap) k.next();
              String type = this.getValue(thisRecord, thisMap.getProperty("type"));
              ContactAddress address = new ContactAddress();
              address.setStreetAddressLine1(this.getValue(thisRecord, thisMap.getProperty("streetAddressLine1")));
              address.setStreetAddressLine2(this.getValue(thisRecord, thisMap.getProperty("streetAddressLine2")));
              address.setStreetAddressLine3(this.getValue(thisRecord, thisMap.getProperty("streetAddressLine3")));
              address.setCity(this.getValue(thisRecord, thisMap.getProperty("city")));
              address.setState(this.getValue(thisRecord, thisMap.getProperty("state")));
              address.setCountry(this.getValue(thisRecord, thisMap.getProperty("country")));
              address.setZip(this.getValue(thisRecord, thisMap.getProperty("zip")));
              if (!"".equals(StringUtils.toString(type))) {
                address.setType(type);
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

            if (error.length() == 0) {
              //insert the contact
              boolean isValid = ObjectValidator.validate(null, db, thisContact);
              if (isValid) {
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
          } catch (Exception unknownException) {
            unknownException.printStackTrace();
            if (error.length() == 0) {
              recordError(unknownException.toString(), line, recordCount);
            } else {
              recordError(error.toString() + "; " + unknownException.toString(), line, recordCount);
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
        //update status
        this.setStatusId(FAILED);
      } catch (Exception statusException) {
        e.printStackTrace();
        //error updating status
      }
    } finally {
      if (importThread == currentThread) {
        stop();
      }
    }
  }


  /**
   *  Cancel the import
   */
  public void cancel() {
    try {
      //do not use stop to stop the thread, nullify it
      importThread = null;

      //set the status to canceled
      this.setStatusId(Import.CANCELED);

      //perform clean up
      destroy();
    } catch (Exception e) {
    }
  }


  /**
   *  Stops the thread
   */
  public void stop() {
    //do not use stop() to stop the thread, nullify it
    importThread = null;

    //perform clean up
    destroy();
  }


  /**
   *  Description of the Method
   */
  public void destroy() {
    //set status of the import and clean up thread pool
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ContactImport -> Starting cleanup for ImportId: " + this.getId());
    }

    try {
      //update status, total imported/failed records
      recordResults(db);

      //flush the log file
      fos.flush();
      fos.close();

      if (this.getTotalFailedRecords() > 0) {
        //Store the error file as a version
        fileItem.setSubject("Error file");
        fileItem.setFilename(fileItem.getFilename() + "_error");
        fileItem.setClientFilename(this.getId() + "_error.csv");
        fileItem.setVersion(ERROR_FILE_VERSION);
        fileItem.setSize((int) errorFile.length());
        fileItem.insertVersion(db);
      } else {
        errorFile.delete();
      }

      //report back to manager
      manager.free(this);
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        e.printStackTrace();
      }
    } finally {
      //release the database connection
      manager.free(db);
    }
  }


  /**
   *  Logs an error when a record fails
   *
   * @param  error       Description of the Parameter
   * @param  line        Description of the Parameter
   * @param  lineNumber  Description of the Parameter
   */
  private void recordError(String error, String line, int lineNumber) {
    try {
      //log errors in the temp file created under $FILELIBRARY/_imports/
      if (lineNumber == 0) {
        //header:append error column
        line += "," + "\"_ERROR\"";
      } else if (lineNumber == -1) {
        //general error, mostly before import started
        line += error;
      } else if (lineNumber > 0) {
        //append the error
        line += ",\"" + error + "\"";

        //a record has failed, increment the failure count
        this.incrementTotalFailedRecords();
      }

      //add next line character
      //TODO: Change this to a CUSTOM row delimiter if import type is CUSTOM
      line += "\n";

      fos.write(line);
    } catch (IOException e) {
      //import should not fail because of logging error
    }
  }


  /**
   *  Gets the value attribute of the ContactImport object
   *
   * @param  thisRecord  Description of the Parameter
   * @param  type        Description of the Parameter
   * @return             The value value
   */
  private String getValue(ArrayList thisRecord, Property type) {
    String value = null;
    if (type.getMappedColumn() > -1 && thisRecord.size() > type.getMappedColumn()) {
      value = (String) thisRecord.get(type.getMappedColumn());
    } else if (!"".equals(StringUtils.toString(type.getDefaultValue()))) {
      value = type.getDefaultValue();
    }
    return value;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    int i = 0;
    int recordCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) as recordcount " +
        "FROM contact " +
        "WHERE import_id = ? ");
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
   *  Deletes the import
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);

      //delete all imported records
      if (this.getStatusId() != UNPROCESSED || this.getStatusId() != QUEUED) {
        deleteImportedRecords(db, this.getId());
      }

      //delete import record
      PreparedStatement pst = db.prepareStatement(
          "DELETE from import " +
          "WHERE import_id = ? ");
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
   *  Deletes all imported records
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
          "DELETE FROM contact_emailaddress " +
          "WHERE EXISTS (SELECT contact_id from contact c where c.contact_id = contact_emailaddress.contact_id AND import_id = ?) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM contact_phone " +
          "WHERE EXISTS (SELECT contact_id from contact c where c.contact_id = contact_phone.contact_id AND import_id = ?) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM contact_address " +
          "WHERE EXISTS (SELECT contact_id from contact c where c.contact_id = contact_address.contact_id AND import_id = ?) ");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();

      pst = db.prepareStatement(
          "DELETE FROM contact " +
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
}


