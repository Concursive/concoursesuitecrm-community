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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Vector;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    September 16, 2004
 *@version    $Id: ContractExpirationList.java,v 1.1.2.1 2004/09/16 22:05:21
 *      kbhoopal Exp $
 */
public class ContractExpirationList extends Vector implements SyncableList {

  public final static int TRUE = 1;
  public final static int FALSE = 0;
  protected int includeEnabled = TRUE;

  public final static String tableName = "netapp_contractexpiration";
  public final static String uniqueField = "expiration_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  protected PagedListInfo pagedListInfo = null;

  //potential search params
  private String serialNumber = null;
  private String agreementNumber = null;
  private String services = null;
  private java.sql.Timestamp startDate = null;
  private java.sql.Timestamp endDate = null;
  private String installedAtCompanyName = null;
  private String installedAtSiteName = null;
  protected java.sql.Date endDateStart = null;
  protected java.sql.Date endDateEnd = null;

  //import filters
  private int importId = -1;
  private int statusId = -1;
  private boolean excludeUnapprovedContracts = true;


  /**
   *  Sets the lastAnchor attribute of the ContractExpirationList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the ContractExpirationList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the ContractExpirationList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the ContractExpirationList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the syncType attribute of the ContractExpirationList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the ContractExpirationList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pagedListInfo attribute of the ContractExpirationList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the serialNumber attribute of the ContractExpirationList object
   *
   *@param  tmp  The new serialNumber value
   */
  public void setSerialNumber(String tmp) {
    this.serialNumber = tmp;
  }


  /**
   *  Sets the agreementNumber attribute of the ContractExpirationList object
   *
   *@param  tmp  The new agreementNumber value
   */
  public void setAgreementNumber(String tmp) {
    this.agreementNumber = tmp;
  }


  /**
   *  Sets the services attribute of the ContractExpirationList object
   *
   *@param  tmp  The new services value
   */
  public void setServices(String tmp) {
    this.services = tmp;
  }


  /**
   *  Sets the startDate attribute of the ContractExpirationList object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the startDate attribute of the ContractExpirationList object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the endDate attribute of the ContractExpirationList object
   *
   *@param  tmp  The new endDate value
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   *  Sets the endDate attribute of the ContractExpirationList object
   *
   *@param  tmp  The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the installedAtCompanyName attribute of the ContractExpirationList
   *  object
   *
   *@param  tmp  The new installedAtCompanyName value
   */
  public void setInstalledAtCompanyName(String tmp) {
    this.installedAtCompanyName = tmp;
  }


  /**
   *  Sets the installedAtSiteName attribute of the ContractExpirationList
   *  object
   *
   *@param  tmp  The new installedAtSiteName value
   */
  public void setInstalledAtSiteName(String tmp) {
    this.installedAtSiteName = tmp;
  }


  /**
   *  Sets the endDateStart attribute of the ContractExpirationList object
   *
   *@param  tmp  The new endDateStart value
   */
  public void setEndDateStart(java.sql.Date tmp) {
    this.endDateStart = tmp;
  }

  public void setEndDateStart(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      endDateStart = new java.sql.Date(new java.util.Date().getTime());
      endDateStart.setTime(tmpDate.getTime());
    } catch (Exception e) {
      endDateStart = null;
    }
  }

  /**
   *  Sets the endDateEnd attribute of the ContractExpirationList object
   *
   *@param  tmp  The new endDateEnd value
   */
  public void setEndDateEnd(java.sql.Date tmp) {
    this.endDateEnd = tmp;
  }

  public void setEndDateEnd(String tmp) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(3).parse(tmp);
      endDateEnd = new java.sql.Date(new java.util.Date().getTime());
      endDateEnd.setTime(tmpDate.getTime());
    } catch (Exception e) {
      endDateEnd = null;
    }
  }

  /**
   *  Sets the importId attribute of the ContractExpirationList object
   *
   *@param  tmp  The new importId value
   */
  public void setImportId(int tmp) {
    this.importId = tmp;
  }


  /**
   *  Sets the importId attribute of the ContractExpirationList object
   *
   *@param  tmp  The new importId value
   */
  public void setImportId(String tmp) {
    this.importId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the ContractExpirationList object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the ContractExpirationList object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the excludeUnapprovedContracts attribute of the
   *  ContractExpirationList object
   *
   *@param  tmp  The new excludeUnapprovedContracts value
   */
  public void setExcludeUnapprovedContracts(boolean tmp) {
    this.excludeUnapprovedContracts = tmp;
  }


  /**
   *  Sets the excludeUnapprovedContracts attribute of the
   *  ContractExpirationList object
   *
   *@param  tmp  The new excludeUnapprovedContracts value
   */
  public void setExcludeUnapprovedContracts(String tmp) {
    this.excludeUnapprovedContracts = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the tableName attribute of the ContractExpirationList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the ContractExpirationList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the ContractExpirationList object
   *
   *@return    The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the ContractExpirationList object
   *
   *@return    The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the ContractExpirationList object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Gets the pagedListInfo attribute of the ContractExpirationList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the serialNumber attribute of the ContractExpirationList object
   *
   *@return    The serialNumber value
   */
  public String getSerialNumber() {
    return serialNumber;
  }


  /**
   *  Gets the agreementNumber attribute of the ContractExpirationList object
   *
   *@return    The agreementNumber value
   */
  public String getAgreementNumber() {
    return agreementNumber;
  }


  /**
   *  Gets the services attribute of the ContractExpirationList object
   *
   *@return    The services value
   */
  public String getServices() {
    return services;
  }


  /**
   *  Gets the startDate attribute of the ContractExpirationList object
   *
   *@return    The startDate value
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Gets the endDate attribute of the ContractExpirationList object
   *
   *@return    The endDate value
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Gets the installedAtCompanyName attribute of the ContractExpirationList
   *  object
   *
   *@return    The installedAtCompanyName value
   */
  public String getInstalledAtCompanyName() {
    return installedAtCompanyName;
  }


  /**
   *  Gets the installedAtSiteName attribute of the ContractExpirationList
   *  object
   *
   *@return    The installedAtSiteName value
   */
  public String getInstalledAtSiteName() {
    return installedAtSiteName;
  }


  /**
   *  Gets the endDateStart attribute of the ContractExpirationList object
   *
   *@return    The endDateStart value
   */
  public java.sql.Date getEndDateStart() {
    return endDateStart;
  }


  /**
   *  Gets the endDateEnd attribute of the ContractExpirationList object
   *
   *@return    The endDateEnd value
   */
  public java.sql.Date getEndDateEnd() {
    return endDateEnd;
  }


  /**
   *  Gets the importId attribute of the ContractExpirationList object
   *
   *@return    The importId value
   */
  public int getImportId() {
    return importId;
  }


  /**
   *  Gets the statusId attribute of the ContractExpirationList object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the excludeUnapprovedContracts attribute of the
   *  ContractExpirationList object
   *
   *@return    The excludeUnapprovedContracts value
   */
  public boolean getExcludeUnapprovedContracts() {
    return excludeUnapprovedContracts;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      ContractExpiration thisExpiration = this.getObject(rs);
      this.add(thisExpiration);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM netapp_contractexpiration " +
        "WHERE expiration_id >= 0 ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      PreparedStatement pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

       //Determine column to sort by
      pagedListInfo.setDefaultSort("enddate, serial_number, agreement_number, services ", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY serial_number ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "ce.* " +
        "FROM netapp_contractexpiration ce " +
        "WHERE expiration_id >= 0 ");
    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    return pst;
  }


  /**
   *  Builds a base SQL where statement for filtering records to be used by
   *  sqlSelect and sqlCount
   *
   *@param  sqlFilter  Description of Parameter
   *@since             1.2
   */
  protected void createFilter(StringBuffer sqlFilter) {

    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND entered > ? ");
      }
      sqlFilter.append("AND entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND modified > ? ");
      sqlFilter.append("AND entered < ? ");
      sqlFilter.append("AND modified < ? ");
    }
    if (serialNumber != null) {
      sqlFilter.append("AND lower(serial_number) like lower(?) ");
    }
    if (agreementNumber != null) {
      sqlFilter.append("AND lower(agreement_number) like lower(?) ");
    }
    if (services != null) {
      sqlFilter.append("AND lower(services) like lower(?) ");
    }
    if (startDate != null) {
      sqlFilter.append("AND startdate = ? ");
    }
    if (endDate != null) {
      sqlFilter.append("AND enddate = ? ");
    }
    if (installedAtCompanyName != null) {
      sqlFilter.append("AND lower(installed_at_company_name) like lower(?) ");
    }
    if (installedAtSiteName != null) {
      sqlFilter.append("AND lower(installed_at_site_name) like lower(?) ");
    }
    if (endDateStart != null) {
      sqlFilter.append("AND enddate >= ? ");
    }
    if (endDateEnd != null) {
      sqlFilter.append("AND enddate <= ? ");
    }
    if (importId != -1) {
      sqlFilter.append("AND import_id = ? ");
    }
    if (statusId != -1) {
      sqlFilter.append("AND status_id = ? ");
    }
    if (excludeUnapprovedContracts) {
      sqlFilter.append("AND (status_id IS NULL OR status_id = ?) ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    if (serialNumber != null) {
      pst.setString(++i, "%"+serialNumber+"%");
    }
    if (agreementNumber != null) {
      pst.setString(++i, "%"+agreementNumber+"%");
    }
    if (services != null) {
      pst.setString(++i, "%"+services+"%");
    }
    if (startDate != null) {
      pst.setTimestamp(++i, startDate);
    }
    if (endDate != null) {
      pst.setTimestamp(++i, endDate);
    }
    if (installedAtCompanyName != null) {
      pst.setString(++i, "%"+installedAtCompanyName+"%");
    }
    if (installedAtSiteName != null) {
      pst.setString(++i, installedAtSiteName);
    }
    if (endDateStart != null) {
      pst.setDate(++i, endDateStart);
    }
    if (endDateEnd != null) {
      pst.setDate(++i, endDateEnd);
    }
    if (importId != -1) {
      pst.setInt(++i, importId);
    }
    if (statusId != -1) {
      pst.setInt(++i, statusId);
    }
    if (excludeUnapprovedContracts) {
      pst.setInt(++i, Import.PROCESSED_APPROVED);
    }
    return i;
  }


  /**
   *  Gets the object attribute of the ContractExpirationList object
   *
   *@param  rs                Description of the Parameter
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public ContractExpiration getObject(ResultSet rs) throws SQLException {
    return new ContractExpiration(rs);
  }

}

