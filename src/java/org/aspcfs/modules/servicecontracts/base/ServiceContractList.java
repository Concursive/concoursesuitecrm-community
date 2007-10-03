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
package org.aspcfs.modules.servicecontracts.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * List class for Service Contracts.
 *
 * @author kbhoopal
 * @version $Id: ServiceContractList.java,v 1.1.2.4 2004/01/14 22:55:03
 *          kbhoopal Exp $
 * @created December 23, 2003
 */
public class ServiceContractList extends ArrayList {
  public final static String tableName = "service_contract";
  public final static String uniqueField = "contract_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private String jsEvent = null;
  private int id = -1;
  private int orgId = -1;
  private int subId = -1;
  private int contactId = -1;
  private String serviceContractNumber = null;
  private int enteredBy = -1;

  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;

  /**
   * Sets the lastAnchor attribute of the ServiceContractList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ServiceContractList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ServiceContractList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ServiceContractList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ServiceContractList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Gets the tableName attribute of the ServiceContractList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ServiceContractList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Constructor for the ServiceContractList object
   */
  public ServiceContractList() {
  }


  /**
   * Constructor for the ServiceContractList object
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ServiceContractList(Connection db) throws SQLException {
  }


  /**
   * Sets the pagedListInfo attribute of the ServiceContractList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the emptyHtmlSelectRecord attribute of the ServiceContractList object
   *
   * @param tmp The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   * Sets the jsEvent attribute of the ServiceContractList object
   *
   * @param tmp The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   * Sets the id attribute of the ServiceContractList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ServiceContractList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the orgId attribute of the ServiceContractList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }

  public void setSubId(int tmp) {
    this.subId = tmp;
  }

  /**
   * Sets the orgId attribute of the ServiceContractList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }

  public void setSubId(String tmp) {
    this.subId = Integer.parseInt(tmp);
  }

  /**
   * Sets the contactId attribute of the ServiceContractList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the ServiceContractList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Sets the serviceContractNumber attribute of the ServiceContractList object
   *
   * @param tmp The new serviceContractNumber value
   */
  public void setServiceContractNumber(String tmp) {
    this.serviceContractNumber = tmp;
  }


  /**
   * Sets the enteredBy attribute of the ServiceContractList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the ServiceContractList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the trashedDate attribute of the ServiceContractList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the ServiceContractList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the includeOnlyTrashed attribute of the ServiceContractList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   * Sets the includeOnlyTrashed attribute of the ServiceContractList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the pagedListInfo attribute of the ServiceContractList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the emptyHtmlSelectRecord attribute of the ServiceContractList object
   *
   * @return The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }


  /**
   * Gets the jsEvent attribute of the ServiceContractList object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   * Gets the id attribute of the ServiceContractList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the orgId attribute of the ServiceContractList object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }

  public int getSubId() {
    return subId;
  }

  /**
   * Gets the contactId attribute of the ServiceContractList object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Gets the serviceContractNumber attribute of the ServiceContractList object
   *
   * @return The serviceContractNumber value
   */
  public String getServiceContractNumber() {
    return serviceContractNumber;
  }


  /**
   * Gets the enteredBy attribute of the ServiceContractList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the trashedDate attribute of the ServiceContractList object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   * Gets the includeOnlyTrashed attribute of the ServiceContractList object
   *
   * @return The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      ServiceContract thisContract = this.getObject(rs);
      this.add(thisContract);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
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
            "FROM service_contract sc " +
            "WHERE contract_id > -1 ");

    createFilter(sqlFilter, db);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      PreparedStatement pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine column to sort by
      pagedListInfo.setDefaultSort("entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY entered ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "sc.*, " +
            "os.name AS subname " +
            "FROM service_contract sc " +
            "LEFT JOIN organization os ON (sc.submitter_id = os.org_id) " +
            "WHERE contract_id > -1 ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    return pst;
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
    if (enteredBy > -1) {
      sqlFilter.append("AND sc.enteredby = ? ");
    }

    if (id > -1) {
      sqlFilter.append("AND sc.contract_id = ? ");
    }

    if (orgId > -1) {
      sqlFilter.append("AND sc.account_id = ? ");
    }

    if (subId > -1) {
      sqlFilter.append("AND sc.submitter_id = ? ");
    }

    if (contactId > -1) {
      sqlFilter.append("AND sc.contact_id = ? ");
    }

    if (serviceContractNumber != null) {
      sqlFilter.append("AND sc.contract_number = ? ");
    }

    if (includeOnlyTrashed) {
      sqlFilter.append("AND sc.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND sc.trashed_date = ? ");
    } else {
      sqlFilter.append("AND sc.trashed_date IS NULL ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND sc.entered > ? ");
      }
      sqlFilter.append("AND sc.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND sc.modified > ? ");
      sqlFilter.append("AND sc.entered < ? ");
      sqlFilter.append("AND sc.modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {

    int i = 0;
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }

    if (id > -1) {
      pst.setInt(++i, id);
    }

    if (orgId > -1) {
      pst.setInt(++i, orgId);
    }

    if (subId > -1) {
      pst.setInt(++i, subId);
    }

    if (contactId > -1) {
      pst.setInt(++i, contactId);
    }

    if (serviceContractNumber != null) {
      pst.setString(++i, serviceContractNumber);
    }

    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
    }
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
    return i;
  }


  /**
   * Gets the object attribute of the ServiceContractList object
   *
   * @param rs Description of the Parameter
   * @return The object value
   * @throws SQLException Description of the Exception
   */
  public ServiceContract getObject(ResultSet rs) throws SQLException {
    ServiceContract thisContract = new ServiceContract(rs);
    return thisContract;
  }


  /**
   * Use for determing if there are dependencies before a related object is
   * deleted
   *
   * @param db       Description of the Parameter
   * @param moduleId Description of the Parameter
   * @param itemId   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int moduleId, int itemId) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT COUNT(*) AS itemcount " +
            "FROM service_contract sc " +
            "WHERE sc.contract_id > 0 AND sc.trashed_date IS NULL ");
    if (moduleId == Constants.ACCOUNTS) {
      sql.append("AND sc.account_id = ?");
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


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param baseFilePath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db, String baseFilePath) throws SQLException {
    Iterator contracts = this.iterator();
    while (contracts.hasNext()) {
      ServiceContract thisContract = (ServiceContract) contracts.next();
      thisContract.delete(db, baseFilePath);
    }
  }


  /**
   * Gets the htmlSelect attribute of the ServiceContractList object
   *
   * @param selectName Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   * Gets the htmlSelect attribute of the ServiceContractList object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect contractListSelect = new HtmlSelect();
    if (emptyHtmlSelectRecord != null) {
      contractListSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ServiceContract thisContract = (ServiceContract) i.next();
      contractListSelect.addItem(
          thisContract.getId(),
          thisContract.getServiceContractNumber());
    }
    return contractListSelect.getHtml(selectName, defaultKey);
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param toTrash   Description of the Parameter
   * @param tmpUserId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      ServiceContract tmpServiceContract = (ServiceContract) itr.next();
      tmpServiceContract.updateStatus(db, toTrash, tmpUserId);
    }
    return true;
  }

}

