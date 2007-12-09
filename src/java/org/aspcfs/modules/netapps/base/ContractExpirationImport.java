/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.netapps.base;

import com.darkhorseventures.database.ConnectionElement;
import com.zeroio.iteam.base.FileItem;
import org.aspcfs.apps.transfer.reader.mapreader.Property;
import org.aspcfs.apps.transfer.reader.mapreader.PropertyMap;
import org.aspcfs.controller.ImportManager;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Import;
import org.aspcfs.utils.CFSFileReader;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    September 16, 2004
 *@version    $Id: ContractExpirationImport.java,v 1.1.2.1 2004/09/16 22:05:01
 *      kbhoopal Exp $
 */
public class ContractExpirationImport extends Import implements Runnable {
  public final static String fs = System.getProperty("file.separator");
  private PropertyMap propertyMap = null;
  private String filePath = null;
  private int userId = -1;
  private int accessTypeId = -1;
  private ImportManager manager = null;
  private Connection db = null;
  private ConnectionElement connectionElement = null;
  private File errorFile = null;
  private BufferedWriter fos = null;
  private FileItem fileItem = null;
  private Thread importThread = null;

  private ContractExpirationList contractExpirationList = null;


  /**
   *  Constructor for the ContractExpirationImport object
   */
  public ContractExpirationImport() { }


  /**
   *  Constructor for the ContractExpirationImport object
   *
   *@param  db                Description of the Parameter
   *@param  importId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ContractExpirationImport(Connection db, int importId) throws SQLException {
    super(db, importId);
  }


  /**
   *  Sets the properties attribute of the ContractExpirationImport object
   *
   *@param  request  The new properties value
   */
  public void setProperties(HttpServletRequest request) {
    if (request.getParameter("accessType") != null) {
      setAccessTypeId(request.getParameter("accessType"));
    }
  }


  /**
   *  Sets the accessTypeId attribute of the ContractExpirationImport object
   *
   *@param  tmp  The new accessTypeId value
   */
  public void setAccessTypeId(int tmp) {
    this.accessTypeId = tmp;
  }


  /**
   *  Sets the accessTypeId attribute of the ContractExpirationImport object
   *
   *@param  tmp  The new accessTypeId value
   */
  public void setAccessTypeId(String tmp) {
    this.accessTypeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the userId attribute of the ContractExpirationImport object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   *  Sets the userId attribute of the ContractExpirationImport object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the propertyMap attribute of the ContractExpirationImport object
   *
   *@param  tmp  The new propertyMap value
   */
  public void setPropertyMap(PropertyMap tmp) {
    this.propertyMap = tmp;
  }


  /**
   *  Sets the filePath attribute of the ContractExpirationImport object
   *
   *@param  tmp  The new filePath value
   */
  public void setFilePath(String tmp) {
    this.filePath = tmp;
  }


  /**
   *  Sets the db attribute of the ContractExpirationImport object
   *
   *@param  tmp  The new db value
   */
  public void setDb(Connection tmp) {
    this.db = tmp;
  }


  /**
   *  Sets the manager attribute of the ContractExpirationImport object
   *
   *@param  tmp  The new manager value
   */
  public void setManager(ImportManager tmp) {
    this.manager = tmp;
  }


  /**
   *  Sets the errorFile attribute of the ContractExpirationImport object
   *
   *@param  tmp  The new errorFile value
   */
  public void setErrorFile(File tmp) {
    this.errorFile = tmp;
  }


  /**
   *  Sets the fileItem attribute of the ContractExpirationImport object
   *
   *@param  tmp  The new fileItem value
   */
  public void setFileItem(FileItem tmp) {
    this.fileItem = tmp;
  }


  /**
   *  Sets the importThread attribute of the ContractExpirationImport object
   *
   *@param  tmp  The new importThread value
   */
  public void setImportThread(Thread tmp) {
    this.importThread = tmp;
  }


  /**
   *  Sets the contractExpirationList attribute of the ContractExpirationImport
   *  object
   *
   *@param  tmp  The new contractExpirationList value
   */
  public void setContractExpirationList(ContractExpirationList tmp) {
    this.contractExpirationList = tmp;
  }


  /**
   *  Gets the importThread attribute of the ContractExpirationImport object
   *
   *@return    The importThread value
   */
  public Thread getImportThread() {
    return importThread;
  }


  /**
   *  Gets the fileItem attribute of the ContractExpirationImport object
   *
   *@return    The fileItem value
   */
  public FileItem getFileItem() {
    return fileItem;
  }


  /**
   *  Gets the errorFile attribute of the ContractExpirationImport object
   *
   *@return    The errorFile value
   */
  public File getErrorFile() {
    return errorFile;
  }


  /**
   *  Gets the manager attribute of the ContractExpirationImport object
   *
   *@return    The manager value
   */
  public ImportManager getManager() {
    return manager;
  }


  /**
   *  Gets the db attribute of the ContractExpirationImport object
   *
   *@return    The db value
   */
  public Connection getDb() {
    return db;
  }


  /**
   *  Gets the filePath attribute of the ContractExpirationImport object
   *
   *@return    The filePath value
   */
  public String getFilePath() {
    return filePath;
  }


  /**
   *  Gets the propertyMap attribute of the ContractExpirationImport object
   *
   *@return    The propertyMap value
   */
  public PropertyMap getPropertyMap() {
    return propertyMap;
  }


  /**
   *  Gets the userId attribute of the ContractExpirationImport object
   *
   *@return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Gets the accessTypeId attribute of the ContractExpirationImport object
   *
   *@return    The accessTypeId value
   */
  public int getAccessTypeId() {
    return accessTypeId;
  }


  /**
   *  Sets the connectionElement attribute of the ContractExpirationImport
   *  object
   *
   *@param  tmp  The new connectionElement value
   */
  public void setConnectionElement(ConnectionElement tmp) {
    this.connectionElement = tmp;
  }


  /**
   *  Gets the connectionElement attribute of the ContactExpirationImport object
   *
   *@return    The connectionElement value
   */
  public ConnectionElement getConnectionElement() {
    return connectionElement;
  }


  /**
   *  Gets the contractExpirationList attribute of the ContractExpirationImport
   *  object
   *
   *@return    The contractExpirationList value
   */
  public ContractExpirationList getContractExpirationList() {
    return contractExpirationList;
  }


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

    try {
      //get connection from the manager
      db = manager.getConnection(connectionElement);

      //Fetch existing contract expirations
      contractExpirationList = new ContractExpirationList();
      contractExpirationList.buildList(db);

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

            ContractExpiration thisExpiration = new ContractExpiration();
            thisExpiration.setImportId(this.getId());
            thisExpiration.setStatusId(Import.PROCESSED_UNAPPROVED);

            //set expiration contract properties
            //entered by
            String propertyValue = "";
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("serialNumber"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setSerialNumber(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("agreementNumber"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setAgreementNumber(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("services"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setServices(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("startDate"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              Timestamp tmp = DateUtils.getUserToServerDateTime(null, DateFormat.SHORT, DateFormat.LONG, propertyValue, Locale.getDefault());
              thisExpiration.setStartDate(tmp);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("endDate"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              Timestamp tmp = DateUtils.getUserToServerDateTime(null, DateFormat.SHORT, DateFormat.LONG, propertyValue, Locale.getDefault());
              thisExpiration.setEndDate(tmp);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("installedAtCompanyName"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setInstalledAtCompanyName(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("installedAtSiteName"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setInstalledAtSiteName(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("groupName"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setGroupName(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("productNumber"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setProductNumber(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("systemName"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setSystemName(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("operatingSystem"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setOperatingSystem(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("noOfShelves"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setNoOfShelves(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("noOfDisks"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setNoOfDisks(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("nvram"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setNvram(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("memory"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setMemory(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("autosupportStatus"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setAutosupportStatus(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("installedAtAddress"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setInstalledAtAddress(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("city"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setCity(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("stateProvince"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setStateProvince(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("postalCode"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setPostalCode(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("country"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setCountry(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("installedAtContactFirstName"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setInstalledAtContactFirstName(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("contactLastName"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setContactLastName(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("contactEmail"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setContactEmail(propertyValue);
            }
            propertyValue = this.getValue(thisRecord, propertyMap.getProperty("agreementCompany"));
            if (!"".equals(StringUtils.toString(propertyValue))) {
              thisExpiration.setAgreementCompany(propertyValue);
            }

            if (error.length() == 0) {
              int thisExpirationId = 0;
              //Determine whether to insert a new record or to update an existing record
              if ((thisExpirationId = isExists(thisExpiration)) > 0){
                thisExpiration.setId(thisExpirationId);
                thisExpiration.setModifiedBy(userId);
                int resultCount = 0;
                resultCount = thisExpiration.update(db);
                recordInserted = ((resultCount == 1));
              }else{
                //insert the expiration contract
                thisExpiration.setEnteredBy(userId);
                thisExpiration.setModifiedBy(userId);
                recordInserted = thisExpiration.insert(db);
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
   *  Description of the Method
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
      System.out.println("ContactExpirationImport -> Starting cleanup for ImportId: " + this.getId());
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
   *@param  error       Description of the Parameter
   *@param  line        Description of the Parameter
   *@param  lineNumber  Description of the Parameter
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
   *  Gets the value attribute of the ContactExpirationImport object
   *
   *@param  thisRecord  Description of the Parameter
   *@param  type        Description of the Parameter
   *@return             The value value
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
   *  Deletes the import
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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
   *  Examines whether a net app contract with a similar serial number, agreement number and services
   *  exists in the database
   *
   *@param  thisContractExpiration  Description of the Parameter
   *@return                         The exist value
   */
  private int isExists(ContractExpiration thisContractExpiration) {

    Iterator itr = contractExpirationList.iterator();
    while (itr.hasNext()) {
      ContractExpiration tmpContractExpiration = (ContractExpiration) itr.next();
      if (tmpContractExpiration.getSerialNumber().equals(thisContractExpiration.getSerialNumber()) &&
          tmpContractExpiration.getAgreementNumber().equals(thisContractExpiration.getAgreementNumber()) &&
          tmpContractExpiration.getServices().equals(thisContractExpiration.getServices())) {

        return tmpContractExpiration.getId();
      }
    }

    return -1;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    int i = 0;
    int recordCount = 0;
    return dependencyList;
  }


  /**
   *  Deletes all imported records
   *
   *@param  db                Description of the Parameter
   *@param  tmpImportId       Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static boolean deleteImportedRecords(Connection db, int tmpImportId) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM netapp_contractexpiration " +
          "WHERE import_id = ? " + 
          "AND status_id = ?");
      int i = 0;    
      pst.setInt(++i, tmpImportId);
      pst.setInt(++i, Import.PROCESSED_UNAPPROVED);
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


