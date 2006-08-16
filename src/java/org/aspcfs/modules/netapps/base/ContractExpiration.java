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
package org.aspcfs.modules.netapps.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Import;
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    September 16, 2004
 *@version    $Id: ContractExpiration.java,v 1.1.2.1 2004/09/16 22:05:21
 *      kbhoopal Exp $
 */
public class ContractExpiration extends GenericBean {

  //from the csv file
  private int id = -1;
  private String serialNumber = null;
  private String agreementNumber = null;
  private String services = null;
  private java.sql.Timestamp startDate = null;
  private java.sql.Timestamp endDate = null;
  private String installedAtCompanyName = null;
  private String installedAtSiteName = null;
  private String groupName = null;
  private String productNumber = null;
  private String systemName = null;
  private String operatingSystem = null;
  private int noOfShelves = -1;
  private int noOfDisks = -1;
  private int nvram = -1;
  private int memory = -1;
  private String autosupportStatus = null;
  private String installedAtAddress = null;
  private String city = null;
  private String stateProvince = null;
  private String postalCode = null;
  private String country = null;
  private String installedAtContactFirstName = null;
  private String contactLastName = null;
  private String contactEmail = null;
  private String agreementCompany = null;

  //Additional fields that are added/modified
  private double quoteAmount = -1.0;
  private java.sql.Timestamp quoteGeneratedDate = null;
  private java.sql.Timestamp quoteAcceptedDate = null;
  private java.sql.Timestamp quoteRejectedDate = null;
  private String comment = null;

  //fields required for import maintenance
  private int importId = -1;
  private int statusId = -1;

  //General maintenance fields
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean override = false;

  private ContractExpirationLogList history = new ContractExpirationLogList();


  /**
   *  Constructor for the ContractExpiration object
   */
  public ContractExpiration() { }


  /**
   *  Constructor for the ContractExpiration object
   *
   *@param  db                Description of the Parameter
   *@param  tmpExpirationId   Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ContractExpiration(Connection db, int tmpExpirationId) throws SQLException {
    this.queryRecord(db, tmpExpirationId);
  }


  /**
   *  Constructor for the ContractExpiration object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ContractExpiration(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the ContractExpiration object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ContractExpiration object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the serialNumber attribute of the ContractExpiration object
   *
   *@param  tmp  The new serialNumber value
   */
  public void setSerialNumber(String tmp) {
    this.serialNumber = tmp;
  }


  /**
   *  Sets the agreementNumber attribute of the ContractExpiration object
   *
   *@param  tmp  The new agreementNumber value
   */
  public void setAgreementNumber(String tmp) {
    this.agreementNumber = tmp;
  }


  /**
   *  Sets the services attribute of the ContractExpiration object
   *
   *@param  tmp  The new services value
   */
  public void setServices(String tmp) {
    this.services = tmp;
  }


  /**
   *  Sets the startDate attribute of the ContractExpiration object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the startDate attribute of the ContractExpiration object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the endDate attribute of the ContractExpiration object
   *
   *@param  tmp  The new endDate value
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   *  Sets the endDate attribute of the ContractExpiration object
   *
   *@param  tmp  The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the installedAtCompanyName attribute of the ContractExpiration object
   *
   *@param  tmp  The new installedAtCompanyName value
   */
  public void setInstalledAtCompanyName(String tmp) {
    this.installedAtCompanyName = tmp;
  }


  /**
   *  Sets the installedAtSiteName attribute of the ContractExpiration object
   *
   *@param  tmp  The new installedAtSiteName value
   */
  public void setInstalledAtSiteName(String tmp) {
    this.installedAtSiteName = tmp;
  }


  /**
   *  Sets the groupName attribute of the ContractExpiration object
   *
   *@param  tmp  The new groupName value
   */
  public void setGroupName(String tmp) {
    this.groupName = tmp;
  }


  /**
   *  Sets the productNumber attribute of the ContractExpiration object
   *
   *@param  tmp  The new productNumber value
   */
  public void setProductNumber(String tmp) {
    this.productNumber = tmp;
  }


  /**
   *  Sets the systemName attribute of the ContractExpiration object
   *
   *@param  tmp  The new systemName value
   */
  public void setSystemName(String tmp) {
    this.systemName = tmp;
  }


  /**
   *  Sets the operatingSystem attribute of the ContractExpiration object
   *
   *@param  tmp  The new operatingSystem value
   */
  public void setOperatingSystem(String tmp) {
    this.operatingSystem = tmp;
  }


  /**
   *  Sets the noOfShelves attribute of the ContractExpiration object
   *
   *@param  tmp  The new noOfShelves value
   */
  public void setNoOfShelves(int tmp) {
    this.noOfShelves = tmp;
  }


  /**
   *  Sets the noOfShelves attribute of the ContractExpiration object
   *
   *@param  tmp  The new noOfShelves value
   */
  public void setNoOfShelves(String tmp) {
    this.noOfShelves = Integer.parseInt(tmp);
  }


  /**
   *  Sets the noOfDisks attribute of the ContractExpiration object
   *
   *@param  tmp  The new noOfDisks value
   */
  public void setNoOfDisks(int tmp) {
    this.noOfDisks = tmp;
  }


  /**
   *  Sets the noOfDisks attribute of the ContractExpiration object
   *
   *@param  tmp  The new noOfDisks value
   */
  public void setNoOfDisks(String tmp) {
    this.noOfDisks = Integer.parseInt(tmp);
  }


  /**
   *  Sets the nVRAM attribute of the ContractExpiration object
   *
   *@param  tmp  The new nVRAM value
   */
  public void setNvram(int tmp) {
    this.nvram = tmp;
  }


  /**
   *  Sets the nVRAM attribute of the ContractExpiration object
   *
   *@param  tmp  The new nVRAM value
   */
  public void setNvram(String tmp) {
    this.nvram = Integer.parseInt(tmp);
  }


  /**
   *  Sets the memory attribute of the ContractExpiration object
   *
   *@param  tmp  The new memory value
   */
  public void setMemory(int tmp) {
    this.memory = tmp;
  }


  /**
   *  Sets the memory attribute of the ContractExpiration object
   *
   *@param  tmp  The new memory value
   */
  public void setMemory(String tmp) {
    this.memory = Integer.parseInt(tmp);
  }


  /**
   *  Sets the autosupportStatus attribute of the ContractExpiration object
   *
   *@param  tmp  The new autosupportStatus value
   */
  public void setAutosupportStatus(String tmp) {
    this.autosupportStatus = tmp;
  }


  /**
   *  Sets the installedAtAddress attribute of the ContractExpiration object
   *
   *@param  tmp  The new installedAtAddress value
   */
  public void setInstalledAtAddress(String tmp) {
    this.installedAtAddress = tmp;
  }


  /**
   *  Sets the city attribute of the ContractExpiration object
   *
   *@param  tmp  The new city value
   */
  public void setCity(String tmp) {
    this.city = tmp;
  }


  /**
   *  Sets the stateProvince attribute of the ContractExpiration object
   *
   *@param  tmp  The new stateProvince value
   */
  public void setStateProvince(String tmp) {
    this.stateProvince = tmp;
  }


  /**
   *  Sets the postalCode attribute of the ContractExpiration object
   *
   *@param  tmp  The new postalCode value
   */
  public void setPostalCode(String tmp) {
    this.postalCode = tmp;
  }


  /**
   *  Sets the country attribute of the ContractExpiration object
   *
   *@param  tmp  The new country value
   */
  public void setCountry(String tmp) {
    this.country = tmp;
  }


  /**
   *  Sets the installedAtContactFirstName attribute of the ContractExpiration
   *  object
   *
   *@param  tmp  The new installedAtContactFirstName value
   */
  public void setInstalledAtContactFirstName(String tmp) {
    this.installedAtContactFirstName = tmp;
  }


  /**
   *  Sets the contactLastName attribute of the ContractExpiration object
   *
   *@param  tmp  The new contactLastName value
   */
  public void setContactLastName(String tmp) {
    this.contactLastName = tmp;
  }


  /**
   *  Sets the contactEmail attribute of the ContractExpiration object
   *
   *@param  tmp  The new contactEmail value
   */
  public void setContactEmail(String tmp) {
    this.contactEmail = tmp;
  }


  /**
   *  Sets the agreementCompany attribute of the ContractExpiration object
   *
   *@param  tmp  The new agreementCompany value
   */
  public void setAgreementCompany(String tmp) {
    this.agreementCompany = tmp;
  }


  /**
   *  Sets the quoteAmount attribute of the ContractExpiration object
   *
   *@param  tmp  The new quoteAmount value
   */
  public void setQuoteAmount(double tmp) {
    this.quoteAmount = tmp;
  }


  /**
   *  Sets the quoteAmount attribute of the ContractExpiration object
   *
   *@param  tmp  The new quoteAmount value
   */
  public void setQuoteAmount(String tmp) {
    this.quoteAmount = Double.parseDouble(tmp);
  }


  /**
   *  Sets the quoteGeneratedDate attribute of the ContractExpiration object
   *
   *@param  tmp  The new quoteGeneratedDate value
   */
  public void setQuoteGeneratedDate(java.sql.Timestamp tmp) {
    this.quoteGeneratedDate = tmp;
  }


  /**
   *  Sets the quoteGeneratedDate attribute of the ContractExpiration object
   *
   *@param  tmp  The new quoteGeneratedDate value
   */
  public void setQuoteGeneratedDate(String tmp) {
    this.quoteGeneratedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the quoteAcceptedDate attribute of the ContractExpiration object
   *
   *@param  tmp  The new quoteAcceptedDate value
   */
  public void setQuoteAcceptedDate(java.sql.Timestamp tmp) {
    this.quoteAcceptedDate = tmp;
  }


  /**
   *  Sets the quoteAcceptedDate attribute of the ContractExpiration object
   *
   *@param  tmp  The new quoteAcceptedDate value
   */
  public void setQuoteAcceptedDate(String tmp) {
    this.quoteAcceptedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the quoteRejectedDate attribute of the ContractExpiration object
   *
   *@param  tmp  The new quoteRejectedDate value
   */
  public void setQuoteRejectedDate(java.sql.Timestamp tmp) {
    this.quoteRejectedDate = tmp;
  }


  /**
   *  Sets the quoteRejectedDate attribute of the ContractExpiration object
   *
   *@param  tmp  The new quoteRejectedDate value
   */
  public void setQuoteRejectedDate(String tmp) {
    this.quoteRejectedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the comment attribute of the ContractExpiration object
   *
   *@param  tmp  The new comment value
   */
  public void setComment(String tmp) {
    this.comment = tmp;
  }


  /**
   *  Sets the importId attribute of the ContractExpiration object
   *
   *@param  tmp  The new importId value
   */
  public void setImportId(int tmp) {
    this.importId = tmp;
  }


  /**
   *  Sets the importId attribute of the ContractExpiration object
   *
   *@param  tmp  The new importId value
   */
  public void setImportId(String tmp) {
    this.importId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the ContractExpiration object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the ContractExpiration object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the ContractExpiration object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ContractExpiration object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the ContractExpiration object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ContractExpiration object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the ContractExpiration object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the ContractExpiration object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the ContractExpiration object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ContractExpiration object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the override attribute of the ContractExpiration object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the ContractExpiration object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the history attribute of the ContractExpiration object
   *
   *@param  tmp  The new history value
   */
  public void setHistory(ContractExpirationLogList tmp) {
    this.history = tmp;
  }


  /**
   *  Gets the id attribute of the ContractExpiration object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the serialNumber attribute of the ContractExpiration object
   *
   *@return    The serialNumber value
   */
  public String getSerialNumber() {
    return serialNumber;
  }


  /**
   *  Gets the agreementNumber attribute of the ContractExpiration object
   *
   *@return    The agreementNumber value
   */
  public String getAgreementNumber() {
    return agreementNumber;
  }


  /**
   *  Gets the services attribute of the ContractExpiration object
   *
   *@return    The services value
   */
  public String getServices() {
    return services;
  }


  /**
   *  Gets the startDate attribute of the ContractExpiration object
   *
   *@return    The startDate value
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Gets the endDate attribute of the ContractExpiration object
   *
   *@return    The endDate value
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Gets the installedAtCompanyName attribute of the ContractExpiration object
   *
   *@return    The installedAtCompanyName value
   */
  public String getInstalledAtCompanyName() {
    return installedAtCompanyName;
  }


  /**
   *  Gets the installedAtSiteName attribute of the ContractExpiration object
   *
   *@return    The installedAtSiteName value
   */
  public String getInstalledAtSiteName() {
    return installedAtSiteName;
  }


  /**
   *  Gets the groupName attribute of the ContractExpiration object
   *
   *@return    The groupName value
   */
  public String getGroupName() {
    return groupName;
  }


  /**
   *  Gets the productNumber attribute of the ContractExpiration object
   *
   *@return    The productNumber value
   */
  public String getProductNumber() {
    return productNumber;
  }


  /**
   *  Gets the systemName attribute of the ContractExpiration object
   *
   *@return    The systemName value
   */
  public String getSystemName() {
    return systemName;
  }


  /**
   *  Gets the operatingSystem attribute of the ContractExpiration object
   *
   *@return    The operatingSystem value
   */
  public String getOperatingSystem() {
    return operatingSystem;
  }


  /**
   *  Gets the noOfShelves attribute of the ContractExpiration object
   *
   *@return    The noOfShelves value
   */
  public int getNoOfShelves() {
    return noOfShelves;
  }


  /**
   *  Gets the noOfDisks attribute of the ContractExpiration object
   *
   *@return    The noOfDisks value
   */
  public int getNoOfDisks() {
    return noOfDisks;
  }


  /**
   *  Gets the nVRAM attribute of the ContractExpiration object
   *
   *@return    The nVRAM value
   */
  public int getNvram() {
    return nvram;
  }


  /**
   *  Gets the memory attribute of the ContractExpiration object
   *
   *@return    The memory value
   */
  public int getMemory() {
    return memory;
  }


  /**
   *  Gets the autosupportStatus attribute of the ContractExpiration object
   *
   *@return    The autosupportStatus value
   */
  public String getAutosupportStatus() {
    return autosupportStatus;
  }


  /**
   *  Gets the installedAtAddress attribute of the ContractExpiration object
   *
   *@return    The installedAtAddress value
   */
  public String getInstalledAtAddress() {
    return installedAtAddress;
  }


  /**
   *  Gets the city attribute of the ContractExpiration object
   *
   *@return    The city value
   */
  public String getCity() {
    return city;
  }


  /**
   *  Gets the stateProvince attribute of the ContractExpiration object
   *
   *@return    The stateProvince value
   */
  public String getStateProvince() {
    return stateProvince;
  }


  /**
   *  Gets the postalCode attribute of the ContractExpiration object
   *
   *@return    The postalCode value
   */
  public String getPostalCode() {
    return postalCode;
  }


  /**
   *  Gets the country attribute of the ContractExpiration object
   *
   *@return    The country value
   */
  public String getCountry() {
    return country;
  }


  /**
   *  Gets the installedAtContactFirstName attribute of the ContractExpiration
   *  object
   *
   *@return    The installedAtContactFirstName value
   */
  public String getInstalledAtContactFirstName() {
    return installedAtContactFirstName;
  }


  /**
   *  Gets the contactLastName attribute of the ContractExpiration object
   *
   *@return    The contactLastName value
   */
  public String getContactLastName() {
    return contactLastName;
  }


  /**
   *  Gets the contactEmail attribute of the ContractExpiration object
   *
   *@return    The contactEmail value
   */
  public String getContactEmail() {
    return contactEmail;
  }


  /**
   *  Gets the agreementCompany attribute of the ContractExpiration object
   *
   *@return    The agreementCompany value
   */
  public String getAgreementCompany() {
    return agreementCompany;
  }


  /**
   *  Gets the quoteAmount attribute of the ContractExpiration object
   *
   *@return    The quoteAmount value
   */
  public double getQuoteAmount() {
    return quoteAmount;
  }


  /**
   *  Gets the quoteGeneratedDate attribute of the ContractExpiration object
   *
   *@return    The quoteGeneratedDate value
   */
  public java.sql.Timestamp getQuoteGeneratedDate() {
    return quoteGeneratedDate;
  }


  /**
   *  Gets the quoteAcceptedDate attribute of the ContractExpiration object
   *
   *@return    The quoteAcceptedDate value
   */
  public java.sql.Timestamp getQuoteAcceptedDate() {
    return quoteAcceptedDate;
  }


  /**
   *  Gets the quoteRejectedDate attribute of the ContractExpiration object
   *
   *@return    The quoteRejectedDate value
   */
  public java.sql.Timestamp getQuoteRejectedDate() {
    return quoteRejectedDate;
  }


  /**
   *  Gets the comment attribute of the ContractExpiration object
   *
   *@return    The comment value
   */
  public String getComment() {
    return comment;
  }


  /**
   *  Gets the importId attribute of the ContractExpiration object
   *
   *@return    The importId value
   */
  public int getImportId() {
    return importId;
  }


  /**
   *  Gets the statusId attribute of the ContractExpiration object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the entered attribute of the ContractExpiration object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the ContractExpiration object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the ContractExpiration object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the ContractExpiration object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the override attribute of the ContractExpiration object
   *
   *@return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Gets the history attribute of the ContractExpiration object
   *
   *@return    The history value
   */
  public ContractExpirationLogList getHistory() {
    return history;
  }


  /**
   *  Gets the approved attribute of the ContractExpiration object
   *
   *@return    The approved value
   */
  public boolean isApproved() {
    return (statusId != Import.PROCESSED_UNAPPROVED);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  tmpExpirationId   Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpExpirationId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(" SELECT * " +
        " FROM netapp_contractexpiration " +
        " WHERE expiration_id = ? ");
    pst.setInt(1, tmpExpirationId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "netapp_contractexpiration_expiration_id_seq");
    PreparedStatement pst = null;
    pst = db.prepareStatement(
        "INSERT INTO netapp_contractexpiration " +
        "(" +
        (id > -1 ? "expiration_id, " : "") +
        "serial_number, " +
        "agreement_number, " +
        "services, " +
        "startdate, " +
        "enddate, " +
        "installed_at_company_name, " +
        "installed_at_site_name, " +
        "group_name, " +
        "product_number, " +
        "system_name, " +
        "operating_system, " +
        "no_of_shelves, " +
        "no_of_disks, " +
        "nvram, " +
        "memory, " +
        "auto_support_status, " +
        "installed_at_address, " +
        "city, " +
        "state_province, " +
        "postal_code, " +
        "country, " +
        "installed_at_contact_firstname, " +
        "contact_lastname, " +
        "contact_email, " +
        "agreement_company, " +
        "quote_amount, " +
        "quotegenerateddate, " +
        "quoteaccepteddate, " +
        "quoterejecteddate, " +
        "comment, " +
        "import_id , " +
        "status_id, " +
        "enteredBy, " +
        "modifiedBy )" +
        "VALUES (" +
        (id > -1 ? "?, " : "") +
        "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, this.serialNumber);
    pst.setString(++i, this.agreementNumber);
    pst.setString(++i, this.services);
    DatabaseUtils.setTimestamp(pst, ++i, this.startDate);
    DatabaseUtils.setTimestamp(pst, ++i, this.endDate);
    pst.setString(++i, this.installedAtCompanyName);
    pst.setString(++i, this.installedAtSiteName);
    pst.setString(++i, this.groupName);
    pst.setString(++i, this.productNumber);
    pst.setString(++i, this.systemName);
    pst.setString(++i, this.operatingSystem);
    DatabaseUtils.setInt(pst, ++i, this.noOfShelves);
    DatabaseUtils.setInt(pst, ++i, this.noOfDisks);
    DatabaseUtils.setInt(pst, ++i, this.nvram);
    DatabaseUtils.setInt(pst, ++i, this.memory);
    pst.setString(++i, this.autosupportStatus);
    pst.setString(++i, this.installedAtAddress);
    pst.setString(++i, this.city);
    pst.setString(++i, this.stateProvince);
    pst.setString(++i, this.postalCode);
    pst.setString(++i, this.country);
    pst.setString(++i, this.installedAtContactFirstName);
    pst.setString(++i, this.contactLastName);
    pst.setString(++i, this.contactEmail);
    pst.setString(++i, this.agreementCompany);
    DatabaseUtils.setDouble(pst, ++i, this.quoteAmount);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteGeneratedDate);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteAcceptedDate);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteRejectedDate);
    pst.setString(++i, this.comment);
    DatabaseUtils.setInt(pst, ++i, this.importId);
    DatabaseUtils.setInt(pst, ++i, this.statusId);
    DatabaseUtils.setInt(pst, ++i, this.enteredBy);
    DatabaseUtils.setInt(pst, ++i, this.modifiedBy);

    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "netapp_contractexpiration_expiration_id_seq", id);
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (!isValid()) {
      return resultCount;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE netapp_contractexpiration " +
        "SET  " +
        "serial_number = ? , " +
        "agreement_number = ? , " +
        "services = ? , " +
        "startdate = ? , " +
        "enddate = ? , " +
        "installed_at_company_name = ? , " +
        "installed_at_site_name = ? , " +
        "group_name = ? , " +
        "product_number = ? , " +
        "system_name = ? , " +
        "operating_system = ? , " +
        "no_of_shelves = ? , " +
        "no_of_disks = ? , " +
        "nvram = ? , " +
        "memory = ? , " +
        "auto_support_status = ? , " +
        "installed_at_address = ? , " +
        "city = ? , " +
        "state_province = ? , " +
        "postal_code = ? , " +
        "country = ? , " +
        "installed_at_contact_firstname = ? , " +
        "contact_lastname = ? , " +
        "contact_email = ? , " +
        "agreement_company = ? , " +
        "import_id = ? , " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
        "modifiedBy = ?  " +
        "WHERE expiration_id = ? "
        );

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setString(++i, this.serialNumber);
    pst.setString(++i, this.agreementNumber);
    pst.setString(++i, this.services);
    DatabaseUtils.setTimestamp(pst, ++i, this.startDate);
    DatabaseUtils.setTimestamp(pst, ++i, this.endDate);
    pst.setString(++i, this.installedAtCompanyName);
    pst.setString(++i, this.installedAtSiteName);
    pst.setString(++i, this.groupName);
    pst.setString(++i, this.productNumber);
    pst.setString(++i, this.systemName);
    pst.setString(++i, this.operatingSystem);
    DatabaseUtils.setInt(pst, ++i, this.noOfShelves);
    DatabaseUtils.setInt(pst, ++i, this.noOfDisks);
    DatabaseUtils.setInt(pst, ++i, this.nvram);
    DatabaseUtils.setInt(pst, ++i, this.memory);
    pst.setString(++i, this.autosupportStatus);
    pst.setString(++i, this.installedAtAddress);
    pst.setString(++i, this.city);
    pst.setString(++i, this.stateProvince);
    pst.setString(++i, this.postalCode);
    pst.setString(++i, this.country);
    pst.setString(++i, this.installedAtContactFirstName);
    pst.setString(++i, this.contactLastName);
    pst.setString(++i, this.contactEmail);
    pst.setString(++i, this.agreementCompany);
    DatabaseUtils.setInt(pst, ++i, this.importId);
    DatabaseUtils.setInt(pst, ++i, this.modifiedBy);
    DatabaseUtils.setInt(pst, ++i, this.id);

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int updateQuoteInformation(Connection db) throws SQLException {
    int resultCount = 0;
    if (!isValid()) {
      return resultCount;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE netapp_contractexpiration " +
        "SET  " +
        "quote_amount = ?, " +
        "quotegenerateddate = ?, " +
        "quoteaccepteddate = ?, " +
        "quoterejecteddate = ?, " +
        "comment = ? ");

    if (!override) {
      sql.append(" , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE expiration_id = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    DatabaseUtils.setDouble(pst, ++i, quoteAmount);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteGeneratedDate);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteAcceptedDate);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteRejectedDate);
    pst.setString(++i, comment);
    if (!override) {
      pst.setInt(++i, modifiedBy);
    }
    DatabaseUtils.setInt(pst, ++i, this.id);
    if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();

    this.updateLog(db);
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void updateLog(Connection db) throws SQLException {
    ContractExpirationLog thisEntry = new ContractExpirationLog();
    thisEntry.setEnteredBy(this.getModifiedBy());
    thisEntry.setModifiedBy(this.getModifiedBy());
    thisEntry.setQuoteAmount(this.getQuoteAmount());
    thisEntry.setQuoteGeneratedDate(this.getQuoteGeneratedDate());
    thisEntry.setQuoteAcceptedDate(this.getQuoteAcceptedDate());
    thisEntry.setQuoteRejectedDate(this.getQuoteRejectedDate());
    thisEntry.setComment(this.getComment());
    thisEntry.setExpirationId(this.getId());
    history.add(thisEntry);
    Iterator hist = history.iterator();
    while (hist.hasNext()) {
      ContractExpirationLog thisLog = (ContractExpirationLog) hist.next();
      thisLog.process(db, this.getId());
    }
  }


  /**
   *  Gets the valid attribute of the ContractExpiration object
   *
   *@return    The valid value
   */
  public boolean isValid() {
    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
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
      String sql = "UPDATE netapp_contractexpiration " +
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
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(HttpServletRequest request, Connection db) throws SQLException {

    //deleting history
    PreparedStatement pst = null;
    pst = db.prepareStatement(" DELETE " +
        " FROM netapp_contractexpiration_log " +
        " WHERE expiration_id = ? ");
    pst.setInt(1, this.id);
    pst.execute();
    pst.close();
    
    pst = null;
    pst = db.prepareStatement(" DELETE " +
        " FROM netapp_contractexpiration " +
        " WHERE expiration_id = ? ");
    pst.setInt(1, this.id);
    pst.execute();
    pst.close();

    return true;
  }


  /**
   *  Gets the timeZoneParams attribute of the ContractExpiration object
   *
   *@return    The timeZoneParams value
   */
  public ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("startDate");
    thisList.add("endDate");
    thisList.add("quoteGeneratedDate");
    thisList.add("quoteAcceptedDate");
    thisList.add("quoteRejectedDate");
    return thisList;
  }


  /**
   *  Gets the numberParams attribute of the ContractExpiration class
   *
   *@return    The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("quoteAmount");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = DatabaseUtils.getInt(rs, "expiration_id");
    serialNumber = rs.getString("serial_number");
    agreementNumber = rs.getString("agreement_number");
    services = rs.getString("services");
    startDate = rs.getTimestamp("startdate");
    endDate = rs.getTimestamp("enddate");
    installedAtCompanyName = rs.getString("installed_at_company_name");
    installedAtSiteName = rs.getString("installed_at_site_name");
    groupName = rs.getString("group_name");
    productNumber = rs.getString("product_number");
    systemName = rs.getString("system_name");
    operatingSystem = rs.getString("operating_system");
    noOfShelves = DatabaseUtils.getInt(rs, "no_of_shelves");
    noOfDisks = DatabaseUtils.getInt(rs, "no_of_disks");
    nvram = DatabaseUtils.getInt(rs, "nvram");
    memory = DatabaseUtils.getInt(rs, "memory");
    autosupportStatus = rs.getString("auto_support_status");
    installedAtAddress = rs.getString("installed_at_address");
    city = rs.getString("city");
    stateProvince = rs.getString("state_province");
    postalCode = rs.getString("postal_code");
    country = rs.getString("country");
    installedAtContactFirstName = rs.getString("installed_at_contact_firstname");
    contactLastName = rs.getString("contact_lastname");
    contactEmail = rs.getString("contact_email");
    agreementCompany = rs.getString("agreement_company");

    //Additional fields that are added/modified
    quoteAmount = DatabaseUtils.getDouble(rs, "quote_amount");
    quoteGeneratedDate = rs.getTimestamp("quotegenerateddate");
    quoteAcceptedDate = rs.getTimestamp("quoteaccepteddate");
    quoteRejectedDate = rs.getTimestamp("quoterejecteddate");
    comment = rs.getString("comment");

    //fields required for import maintenance
    importId = DatabaseUtils.getInt(rs, "import_id");
    statusId = DatabaseUtils.getInt(rs, "status_id");

    //General maintenance fields
    entered = rs.getTimestamp("entered");
    enteredBy = DatabaseUtils.getInt(rs, "enteredBy");
    modified = rs.getTimestamp("modified");
    modifiedBy = DatabaseUtils.getInt(rs, "modifiedBy");
  }

}

