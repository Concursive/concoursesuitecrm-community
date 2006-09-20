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
package org.aspcfs.modules.documents.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author
 * @created
 * @version    $Id: DocumentStoreList.java,v 1.1.2.4 2004/11/19 21:31:09
 *      kbhoopal Exp $
 */
public class DocumentStoreList extends ArrayList {
  // main document store filters
  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private int groupId = -1;
  private int documentStoreId = -1;
  private int documentStoresForUser = -1;
  private int userRole = -1;
  private int departmentId = -1;
  private int enteredByUser = -1;
  private String enteredByUserRange = null;
  private String userRange = null;
  private boolean openDocumentStoresOnly = false;
  private boolean closedDocumentStoresOnly = false;
  private boolean invitationPendingOnly = false;
  private boolean invitationAcceptedOnly = false;
  private int daysLastAccessed = -1;
  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;
  // calendar filters
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;
  int siteId = -1;


  /**
   *  Constructor for the DocumentStoreList object
   */
  public DocumentStoreList() { }


  /**
   *  Sets the pagedListInfo attribute of the DocumentStoreList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the emptyHtmlSelectRecord attribute of the DocumentStoreList object
   *
   * @param  tmp  The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   *  Sets the groupId attribute of the DocumentStoreList object
   *
   * @param  tmp  The new groupId value
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   *  Sets the groupId attribute of the DocumentStoreList object
   *
   * @param  tmp  The new groupId value
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the documentStoreId attribute of the DocumentStoreList object
   *
   * @param  tmp  The new documentStoreId value
   */
  public void setDocumentStoreId(int tmp) {
    this.documentStoreId = tmp;
  }


  /**
   *  Sets the documentStoreId attribute of the DocumentStoreList object
   *
   * @param  tmp  The new documentStoreId value
   */
  public void setDocumentStoreId(String tmp) {
    this.documentStoreId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the documentStoresForUser attribute of the DocumentStoreList object
   *
   * @param  tmp  The new documentStoresForUser value
   */
  public void setDocumentStoresForUser(int tmp) {
    this.documentStoresForUser = tmp;
  }


  /**
   *  Sets the documentStoresForUser attribute of the DocumentStoreList object
   *
   * @param  tmp  The new documentStoresForUser value
   */
  public void setDocumentStoresForUser(String tmp) {
    this.documentStoresForUser = Integer.parseInt(tmp);
  }


  /**
   *  Sets the userRole attribute of the DocumentStoreList object
   *
   * @param  tmp  The new userRole value
   */
  public void setUserRole(int tmp) {
    this.userRole = tmp;
  }


  /**
   *  Sets the userRole attribute of the DocumentStoreList object
   *
   * @param  tmp  The new userRole value
   */
  public void setUserRole(String tmp) {
    this.userRole = Integer.parseInt(tmp);
  }


  /**
   *  Sets the departmentId attribute of the DocumentStoreList object
   *
   * @param  tmp  The new departmentId value
   */
  public void setDepartmentId(int tmp) {
    this.departmentId = tmp;
  }


  /**
   *  Sets the departmentId attribute of the DocumentStoreList object
   *
   * @param  tmp  The new departmentId value
   */
  public void setDepartmentId(String tmp) {
    this.departmentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredByUser attribute of the DocumentStoreList object
   *
   * @param  tmp  The new enteredByUser value
   */
  public void setEnteredByUser(int tmp) {
    this.enteredByUser = tmp;
  }


  /**
   *  Sets the enteredByUser attribute of the DocumentStoreList object
   *
   * @param  tmp  The new enteredByUser value
   */
  public void setEnteredByUser(String tmp) {
    this.enteredByUser = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredByUserRange attribute of the DocumentStoreList object
   *
   * @param  tmp  The new enteredByUserRange value
   */
  public void setEnteredByUserRange(String tmp) {
    this.enteredByUserRange = tmp;
  }


  /**
   *  Sets the userRange attribute of the DocumentStoreList object
   *
   * @param  tmp  The new userRange value
   */
  public void setUserRange(String tmp) {
    this.userRange = tmp;
  }


  /**
   *  Sets the openDocumentStoresOnly attribute of the DocumentStoreList object
   *
   * @param  tmp  The new openDocumentStoresOnly value
   */
  public void setOpenDocumentStoresOnly(boolean tmp) {
    this.openDocumentStoresOnly = tmp;
  }


  /**
   *  Sets the openDocumentStoresOnly attribute of the DocumentStoreList object
   *
   * @param  tmp  The new openDocumentStoresOnly value
   */
  public void setOpenDocumentStoresOnly(String tmp) {
    this.openDocumentStoresOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the closedDocumentStoresOnly attribute of the DocumentStoreList
   *  object
   *
   * @param  tmp  The new closedDocumentStoresOnly value
   */
  public void setClosedDocumentStoresOnly(boolean tmp) {
    this.closedDocumentStoresOnly = tmp;
  }


  /**
   *  Sets the closedDocumentStoresOnly attribute of the DocumentStoreList
   *  object
   *
   * @param  tmp  The new closedDocumentStoresOnly value
   */
  public void setClosedDocumentStoresOnly(String tmp) {
    this.closedDocumentStoresOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the invitationPendingOnly attribute of the DocumentStoreList object
   *
   * @param  tmp  The new invitationPendingOnly value
   */
  public void setInvitationPendingOnly(boolean tmp) {
    this.invitationPendingOnly = tmp;
  }


  /**
   *  Sets the invitationPendingOnly attribute of the DocumentStoreList object
   *
   * @param  tmp  The new invitationPendingOnly value
   */
  public void setInvitationPendingOnly(String tmp) {
    this.invitationPendingOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the invitationAcceptedOnly attribute of the DocumentStoreList object
   *
   * @param  tmp  The new invitationAcceptedOnly value
   */
  public void setInvitationAcceptedOnly(boolean tmp) {
    this.invitationAcceptedOnly = tmp;
  }


  /**
   *  Sets the invitationAcceptedOnly attribute of the DocumentStoreList object
   *
   * @param  tmp  The new invitationAcceptedOnly value
   */
  public void setInvitationAcceptedOnly(String tmp) {
    this.invitationAcceptedOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the daysLastAccessed attribute of the DocumentStoreList object
   *
   * @param  tmp  The new daysLastAccessed value
   */
  public void setDaysLastAccessed(int tmp) {
    this.daysLastAccessed = tmp;
  }


  /**
   *  Sets the daysLastAccessed attribute of the DocumentStoreList object
   *
   * @param  tmp  The new daysLastAccessed value
   */
  public void setDaysLastAccessed(String tmp) {
    this.daysLastAccessed = Integer.parseInt(tmp);
  }


  /**
   *  Sets the trashedDate attribute of the DocumentStoreList object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the DocumentStoreList object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the includeOnlyTrashed attribute of the DocumentStoreList object
   *
   * @param  tmp  The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   *  Sets the includeOnlyTrashed attribute of the DocumentStoreList object
   *
   * @param  tmp  The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the alertRangeStart attribute of the DocumentStoreList object
   *
   * @param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the DocumentStoreList object
   *
   * @param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the alertRangeEnd attribute of the DocumentStoreList object
   *
   * @param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   *  Sets the alertRangeEnd attribute of the DocumentStoreList object
   *
   * @param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the DocumentStoreList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the emptyHtmlSelectRecord attribute of the DocumentStoreList object
   *
   * @return    The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }


  /**
   *  Gets the groupId attribute of the DocumentStoreList object
   *
   * @return    The groupId value
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   *  Gets the documentStoreId attribute of the DocumentStoreList object
   *
   * @return    The documentStoreId value
   */
  public int getDocumentStoreId() {
    return documentStoreId;
  }


  /**
   *  Gets the documentStoresForUser attribute of the DocumentStoreList object
   *
   * @return    The documentStoresForUser value
   */
  public int getDocumentStoresForUser() {
    return documentStoresForUser;
  }


  /**
   *  Gets the userRole attribute of the DocumentStoreList object
   *
   * @return    The userRole value
   */
  public int getUserRole() {
    return userRole;
  }


  /**
   *  Gets the departmentId attribute of the DocumentStoreList object
   *
   * @return    The departmentId value
   */
  public int getDepartmentId() {
    return departmentId;
  }


  /**
   *  Gets the enteredByUser attribute of the DocumentStoreList object
   *
   * @return    The enteredByUser value
   */
  public int getEnteredByUser() {
    return enteredByUser;
  }


  /**
   *  Gets the enteredByUserRange attribute of the DocumentStoreList object
   *
   * @return    The enteredByUserRange value
   */
  public String getEnteredByUserRange() {
    return enteredByUserRange;
  }


  /**
   *  Gets the userRange attribute of the DocumentStoreList object
   *
   * @return    The userRange value
   */
  public String getUserRange() {
    return userRange;
  }


  /**
   *  Gets the openDocumentStoresOnly attribute of the DocumentStoreList object
   *
   * @return    The openDocumentStoresOnly value
   */
  public boolean getOpenDocumentStoresOnly() {
    return openDocumentStoresOnly;
  }


  /**
   *  Gets the closedDocumentStoresOnly attribute of the DocumentStoreList
   *  object
   *
   * @return    The closedDocumentStoresOnly value
   */
  public boolean getClosedDocumentStoresOnly() {
    return closedDocumentStoresOnly;
  }


  /**
   *  Gets the invitationPendingOnly attribute of the DocumentStoreList object
   *
   * @return    The invitationPendingOnly value
   */
  public boolean getInvitationPendingOnly() {
    return invitationPendingOnly;
  }


  /**
   *  Gets the invitationAcceptedOnly attribute of the DocumentStoreList object
   *
   * @return    The invitationAcceptedOnly value
   */
  public boolean getInvitationAcceptedOnly() {
    return invitationAcceptedOnly;
  }


  /**
   *  Gets the daysLastAccessed attribute of the DocumentStoreList object
   *
   * @return    The daysLastAccessed value
   */
  public int getDaysLastAccessed() {
    return daysLastAccessed;
  }


  /**
   *  Gets the trashedDate attribute of the DocumentStoreList object
   *
   * @return    The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Gets the includeOnlyTrashed attribute of the DocumentStoreList object
   *
   * @return    The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   *  Gets the alertRangeStart attribute of the DocumentStoreList object
   *
   * @return    The alertRangeStart value
   */
  public java.sql.Timestamp getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   *  Gets the alertRangeEnd attribute of the DocumentStoreList object
   *
   * @return    The alertRangeEnd value
   */
  public java.sql.Timestamp getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   *  Gets the siteId attribute of the DocumentStoreList object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the siteId attribute of the DocumentStoreList object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the DocumentStoreList object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the htmlSelect attribute of the DocumentStoreList object
   *
   * @param  selectName  Description of Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the htmlSelect attribute of the DocumentStoreList object
   *
   * @param  selectName  Description of Parameter
   * @param  defaultKey  Description of Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect listSelect = this.getHtmlSelect();
    return listSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Gets the htmlSelect attribute of the DocumentStoreList object
   *
   * @return    The htmlSelect value
   */
  public HtmlSelect getHtmlSelect() {
    HtmlSelect listSelect = new HtmlSelect();
    if (emptyHtmlSelectRecord != null) {
      listSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      DocumentStore thisDocumentStore = (DocumentStore) i.next();
      listSelect.addItem(
          thisDocumentStore.getId(),
          thisDocumentStore.getTitle());
    }
    return listSelect;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
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
        "FROM document_store ds " +
        "WHERE ds.document_store_id > -1 ");
    createFilter(sqlFilter, db);
    if (pagedListInfo == null) {
      pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(0);
    }

    //Get the total number of records matching filter
    pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (rs.next()) {
      int maxRecords = rs.getInt("recordcount");
      pagedListInfo.setMaxRecords(maxRecords);
    }
    rs.close();
    pst.close();

    //Determine the offset, based on the filter, for the first record to show
    if (!pagedListInfo.getCurrentLetter().equals("")) {
      pst = db.prepareStatement(
          sqlCount.toString() +
          sqlFilter.toString() +
          "AND " + DatabaseUtils.toLowerCase(db) + "(title) < ? ");
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
    pagedListInfo.setDefaultSort("title", null);
    pagedListInfo.appendSqlTail(db, sqlOrder);

    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "ds.* " +
        "FROM document_store ds " +
        "WHERE ds.document_store_id > -1 ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      DocumentStore thisDocumentStore = new DocumentStore(rs);
      this.add(thisDocumentStore);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of Parameter
   * @param  db         Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (groupId > -1) {
      sqlFilter.append("AND (group_id = ?) ");
    }
    if (documentStoreId > -1) {
      sqlFilter.append("AND (document_store_id = ?) ");
    }
    if (openDocumentStoresOnly) {
      sqlFilter.append("AND (closedate IS NULL) ");
    }
    if (closedDocumentStoresOnly) {
      sqlFilter.append("AND (closedate IS NOT NULL) ");
    }
    if (documentStoresForUser > -1) {
      sqlFilter.append(
          "AND ((ds.document_store_id IN (SELECT DISTINCT document_store_id FROM document_store_user_member WHERE item_id = ? AND site_id " + (siteId == -1 ? "IS NULL " : " = ? ") + "))");
      sqlFilter.append(
          "OR (ds.document_store_id IN (SELECT DISTINCT document_store_id FROM document_store_role_member WHERE item_id = ? AND site_id " + (siteId == -1 ? "IS NULL " : " = ? ") + "))");
      sqlFilter.append(
          "OR (ds.document_store_id IN (SELECT DISTINCT document_store_id FROM " + DatabaseUtils.getTableName(db, "document_store_department_member") + " WHERE item_id = ? AND site_id " + (siteId == -1 ? "IS NULL " : " = ? ") + ")))");
    }
    if (userRange != null) {
      sqlFilter.append(
          "AND (ds.document_store_id IN (SELECT DISTINCT document_store_id FROM document_store_user_member WHERE item_id IN (" + userRange + ")) " +
          "OR ds.enteredBy IN (" + userRange + ")) ");
    }
    if (enteredByUser > -1) {
      sqlFilter.append("AND (ds.enteredby = ?) ");
    }
    if (enteredByUserRange != null) {
      sqlFilter.append("AND (ds.enteredby IN (" + enteredByUserRange + ")) ");
    }
    if (includeOnlyTrashed) {
      sqlFilter.append("AND ds.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND ds.trashed_date = ? ");
    } else {
      sqlFilter.append("AND ds.trashed_date IS NULL ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst            Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (groupId > -1) {
      pst.setInt(++i, groupId);
    }
    if (documentStoreId > -1) {
      pst.setInt(++i, documentStoreId);
    }
    if (documentStoresForUser > -1) {
      pst.setInt(++i, documentStoresForUser);
      if (siteId != -1) {
        pst.setInt(++i, siteId);
      }
      pst.setInt(++i, userRole);
      if (siteId != -1) {
        pst.setInt(++i, siteId);
      }
      pst.setInt(++i, departmentId);
      if (siteId != -1) {
        pst.setInt(++i, siteId);
      }
    }
    if (enteredByUser > -1) {
      pst.setInt(++i, enteredByUser);
    }
    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static HashMap buildNameList(Connection db) throws SQLException {
    HashMap nameList = new HashMap();
    PreparedStatement pst = db.prepareStatement(
        "SELECT document_store_id, title " +
        "FROM document_store");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      nameList.put(
          new Integer(rs.getInt("document_store_id")), rs.getString("title"));
    }
    rs.close();
    pst.close();
    return nameList;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int buildDocumentStoreCount(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(*) AS recordcount " +
        "FROM document_store " +
        "WHERE document_store_id > -1 ");
    ResultSet rs = pst.executeQuery();
    rs.next();
    int count = rs.getInt("recordcount");
    rs.close();
    pst.close();
    return count;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  tmpUserId      Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static int buildDocumentStoreCount(Connection db, int tmpUserId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(*) AS recordcount " +
        "FROM document_store " +
        "WHERE document_store_id > -1 " +
        "AND enteredby = ? ");
    pst.setInt(1, tmpUserId);
    ResultSet rs = pst.executeQuery();
    rs.next();
    int count = rs.getInt("recordcount");
    rs.close();
    pst.close();
    return count;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  filePath       Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void delete(Connection db, String filePath) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      DocumentStore thisDocumentStore = (DocumentStore) itr.next();
      thisDocumentStore.delete(db, filePath);
    }
  }

}

