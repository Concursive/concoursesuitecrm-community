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
package org.aspcfs.modules.quotes.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This represents a list of quotes.
 *
 * @author ananth
 * @version $Id: QuoteList.java,v 1.5.16.1 2004/10/19 20:19:03 mrajkowski Exp
 *          $
 * @created March 24, 2004
 */
public class QuoteList extends ArrayList implements SyncableList {

  //sync api
  public final static String tableName = "quote_entry";
  public final static String uniqueField = "quote_id";
  private Timestamp lastAnchor = null;
  private Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  //filters
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int parentId = -1;
  private int orgId = -1;
  private int contactId = -1;
  private int sourceId = -1;
  private int statusId = -1;
  private int typeId = -1;
  private int ticketId = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private int categoryId = -1;
  private int groupId = -1;
  private String productName = null;
  private boolean buildResources = false;
  private int closedOnly = Constants.UNDEFINED;
  private int headerId = -1;
  private String version = null;
  private int topOnly = Constants.TRUE;
  private boolean buildCompleteVersionList = false;
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;
  protected int deliveryId = -1;
  protected int submitAction = Constants.UNDEFINED;
  protected Timestamp closed = null;
  protected String sku = null;
  private int logoFileId = -1;
  private boolean deleteAllQuotes = false;
  private int siteId = -1;
  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;
  private boolean exclusiveToSite = false;
  private boolean includeAllSites = true;


  /**
   * Gets the logoFileId attribute of the QuoteList object
   *
   * @return The logoFileId value
   */
  public int getLogoFileId() {
    return logoFileId;
  }


  /**
   * Sets the logoFileId attribute of the QuoteList object
   *
   * @param tmp The new logoFileId value
   */
  public void setLogoFileId(int tmp) {
    this.logoFileId = tmp;
  }


  /**
   * Sets the logoFileId attribute of the QuoteList object
   *
   * @param tmp The new logoFileId value
   */
  public void setLogoFileId(String tmp) {
    this.logoFileId = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryId attribute of the QuoteList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the QuoteList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the closedOnly attribute of the QuoteList object
   *
   * @param tmp The new closedOnly value
   */
  public void setClosedOnly(int tmp) {
    this.closedOnly = tmp;
  }


  /**
   * Sets the closedOnly attribute of the QuoteList object
   *
   * @param tmp The new closedOnly value
   */
  public void setClosedOnly(String tmp) {
    this.closedOnly = Integer.parseInt(tmp);
  }


  /**
   * Sets the lastAnchor attribute of the QuoteList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the QuoteList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the QuoteList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the QuoteList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the syncType attribute of the QuoteList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Sets the syncType attribute of the QuoteList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   * Sets the productName attribute of the QuoteList object
   *
   * @param tmp The new productName value
   */
  public void setProductName(String tmp) {
    this.productName = tmp;
  }


  /**
   * Sets the headerId attribute of the QuoteList object
   *
   * @param tmp The new headerId value
   */
  public void setHeaderId(int tmp) {
    this.headerId = tmp;
  }


  /**
   * Sets the headerId attribute of the QuoteList object
   *
   * @param tmp The new headerId value
   */
  public void setHeaderId(String tmp) {
    this.headerId = Integer.parseInt(tmp);
  }


  /**
   * Sets the version attribute of the QuoteList object
   *
   * @param tmp The new version value
   */
  public void setVersion(String tmp) {
    this.version = tmp;
  }


  /**
   * Sets the topOnly attribute of the QuoteList object
   *
   * @param tmp The new topOnly value
   */
  public void setTopOnly(int tmp) {
    this.topOnly = tmp;
  }


  /**
   * Sets the topOnly attribute of the QuoteList object
   *
   * @param tmp The new topOnly value
   */
  public void setTopOnly(String tmp) {
    this.topOnly = Integer.parseInt(tmp);
  }


  /**
   * Sets the parentId attribute of the QuoteList object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   * Sets the parentId attribute of the QuoteList object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   * Sets the buildCompleteVersionList attribute of the QuoteList object
   *
   * @param tmp The new buildCompleteVersionList value
   */
  public void setBuildCompleteVersionList(boolean tmp) {
    this.buildCompleteVersionList = tmp;
  }


  /**
   * Sets the buildCompleteVersionList attribute of the QuoteList object
   *
   * @param tmp The new buildCompleteVersionList value
   */
  public void setBuildCompleteVersionList(String tmp) {
    this.buildCompleteVersionList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the groupId attribute of the QuoteList object
   *
   * @param tmp The new groupId value
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   * Sets the groupId attribute of the QuoteList object
   *
   * @param tmp The new groupId value
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   * Sets the siteId attribute of the QuoteList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   * Sets the siteId attribute of the QuoteList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the groupId attribute of the QuoteList object
   *
   * @return The groupId value
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   * Gets the buildCompleteVersionList attribute of the QuoteList object
   *
   * @return The buildCompleteVersionList value
   */
  public boolean getBuildCompleteVersionList() {
    return buildCompleteVersionList;
  }


  /**
   * Gets the parentId attribute of the QuoteList object
   *
   * @return The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   * Gets the topOnly attribute of the QuoteList object
   *
   * @return The topOnly value
   */
  public int getTopOnly() {
    return topOnly;
  }


  /**
   * Gets the version attribute of the QuoteList object
   *
   * @return The version value
   */
  public String getVersion() {
    return version;
  }


  /**
   * Gets the headerId attribute of the QuoteList object
   *
   * @return The headerId value
   */
  public int getHeaderId() {
    return headerId;
  }


  /**
   * Gets the productName attribute of the QuoteList object
   *
   * @return The productName value
   */
  public String getProductName() {
    return productName;
  }


  /**
   * Gets the tableName attribute of the QuoteList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the QuoteList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the lastAnchor attribute of the QuoteList object
   *
   * @return The lastAnchor value
   */
  public Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   * Gets the nextAnchor attribute of the QuoteList object
   *
   * @return The nextAnchor value
   */
  public Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   * Gets the syncType attribute of the QuoteList object
   *
   * @return The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   * Gets the closedOnly attribute of the QuoteList object
   *
   * @return The closedOnly value
   */
  public int getClosedOnly() {
    return closedOnly;
  }


  /**
   * Gets the categoryId attribute of the QuoteList object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the typeId attribute of the QuoteList object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   * Sets the typeId attribute of the QuoteList object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   * Sets the ticketId attribute of the QuoteList object
   *
   * @param tmp The new ticketId value
   */
  public void setTicketId(int tmp) {
    this.ticketId = tmp;
  }


  /**
   * Sets the ticketId attribute of the QuoteList object
   *
   * @param tmp The new ticketId value
   */
  public void setTicketId(String tmp) {
    this.ticketId = Integer.parseInt(tmp);
  }


  /**
   * Sets the enteredBy attribute of the QuoteList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the QuoteList object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the QuoteList object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the QuoteList object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the modifiedBy attribute of the QuoteList object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the enteredBy attribute of the QuoteList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the typeId attribute of the QuoteList object
   *
   * @return The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   * Sets the pagedListInfo attribute of the QuoteList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the id attribute of the QuoteList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the QuoteList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the QuoteList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the orgId attribute of the QuoteList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the QuoteList object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the contactId attribute of the QuoteList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the QuoteList object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Sets the sourceId attribute of the QuoteList object
   *
   * @param tmp The new sourceId value
   */
  public void setSourceId(int tmp) {
    this.sourceId = tmp;
  }


  /**
   * Sets the sourceId attribute of the QuoteList object
   *
   * @param tmp The new sourceId value
   */
  public void setSourceId(String tmp) {
    this.sourceId = Integer.parseInt(tmp);
  }


  /**
   * Sets the statusId attribute of the QuoteList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the QuoteList object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the buildResources attribute of the QuoteList object
   *
   * @param tmp The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   * Sets the buildResources attribute of the QuoteList object
   *
   * @param tmp The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the alertRangeStart attribute of the QuoteList object
   *
   * @param tmp The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   * Sets the alertRangeStart attribute of the QuoteList object
   *
   * @param tmp The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the alertRangeEnd attribute of the QuoteList object
   *
   * @param tmp The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   * Sets the alertRangeEnd attribute of the QuoteList object
   *
   * @param tmp The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the alertRangeStart attribute of the QuoteList object
   *
   * @return The alertRangeStart value
   */
  public java.sql.Timestamp getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   * Gets the alertRangeEnd attribute of the QuoteList object
   *
   * @return The alertRangeEnd value
   */
  public java.sql.Timestamp getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   * Gets the pagedListInfo attribute of the QuoteList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the orgId attribute of the QuoteList object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Gets the contactId attribute of the QuoteList object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Gets the sourceId attribute of the QuoteList object
   *
   * @return The sourceId value
   */
  public int getSourceId() {
    return sourceId;
  }


  /**
   * Gets the statusId attribute of the QuoteList object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Gets the buildResources attribute of the QuoteList object
   *
   * @return The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   * Gets the ticketId attribute of the QuoteList object
   *
   * @return The ticketId value
   */
  public int getTicketId() {
    return ticketId;
  }


  /**
   * Gets the deliveryId attribute of the QuoteList object
   *
   * @return The deliveryId value
   */
  public int getDeliveryId() {
    return deliveryId;
  }


  /**
   * Sets the deliveryId attribute of the QuoteList object
   *
   * @param tmp The new deliveryId value
   */
  public void setDeliveryId(int tmp) {
    this.deliveryId = tmp;
  }


  /**
   * Sets the deliveryId attribute of the QuoteList object
   *
   * @param tmp The new deliveryId value
   */
  public void setDeliveryId(String tmp) {
    this.deliveryId = Integer.parseInt(tmp);
  }


  /**
   * Gets the submitAction attribute of the QuoteList object
   *
   * @return The submitAction value
   */
  public int getSubmitAction() {
    return submitAction;
  }


  /**
   * Sets the submitAction attribute of the QuoteList object
   *
   * @param tmp The new submitAction value
   */
  public void setSubmitAction(int tmp) {
    this.submitAction = tmp;
  }


  /**
   * Sets the submitAction attribute of the QuoteList object
   *
   * @param tmp The new submitAction value
   */
  public void setSubmitAction(String tmp) {
    this.submitAction = Integer.parseInt(tmp);
  }


  /**
   * Gets the closed attribute of the QuoteList object
   *
   * @return The closed value
   */
  public Timestamp getClosed() {
    return closed;
  }


  /**
   * Sets the closed attribute of the QuoteList object
   *
   * @param tmp The new closed value
   */
  public void setClosed(Timestamp tmp) {
    this.closed = tmp;
  }


  /**
   * Sets the closed attribute of the QuoteList object
   *
   * @param tmp The new closed value
   */
  public void setClosed(String tmp) {
    this.closed = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the sku attribute of the QuoteList object
   *
   * @return The sku value
   */
  public String getSku() {
    return sku;
  }


  /**
   * Sets the sku attribute of the QuoteList object
   *
   * @param tmp The new sku value
   */
  public void setSku(String tmp) {
    this.sku = tmp;
  }


  /**
   * Gets the deleteAllQuotes attribute of the QuoteList object
   *
   * @return The deleteAllQuotes value
   */
  public boolean getDeleteAllQuotes() {
    return deleteAllQuotes;
  }


  /**
   * Sets the deleteAllQuotes attribute of the QuoteList object
   *
   * @param tmp The new deleteAllQuotes value
   */
  public void setDeleteAllQuotes(boolean tmp) {
    this.deleteAllQuotes = tmp;
  }


  /**
   * Sets the deleteAllQuotes attribute of the QuoteList object
   *
   * @param tmp The new deleteAllQuotes value
   */
  public void setDeleteAllQuotes(String tmp) {
    this.deleteAllQuotes = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the trashedDate attribute of the QuoteList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Gets the siteId attribute of the QuoteList object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   * Sets the trashedDate attribute of the QuoteList object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the includeOnlyTrashed attribute of the QuoteList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   * Sets the includeOnlyTrashed attribute of the QuoteList object
   *
   * @param tmp The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the trashedDate attribute of the QuoteList object
   *
   * @return The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   * Gets the includeOnlyTrashed attribute of the QuoteList object
   *
   * @return The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   * Gets the exclusiveToSite attribute of the QuoteList object
   *
   * @return The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   * Sets the exclusiveToSite attribute of the QuoteList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   * Sets the exclusiveToSite attribute of the QuoteList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the includeAllSites attribute of the QuoteList object
   *
   * @return The includeAllSites value
   */
  public boolean getIncludeAllSites() {
    return includeAllSites;
  }


  /**
   * Sets the includeAllSites attribute of the QuoteList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(boolean tmp) {
    this.includeAllSites = tmp;
  }


  /**
   * Sets the includeAllSites attribute of the QuoteList object
   *
   * @param tmp The new includeAllSites value
   */
  public void setIncludeAllSites(String tmp) {
    this.includeAllSites = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Constructor for the QuoteList object
   */
  public QuoteList() { }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
            " FROM quote_entry qe " +
            " LEFT JOIN quote_group qg ON (qe.group_id = qg.group_id) " +
            " LEFT JOIN organization org ON (qe.org_id = org.org_id) " +
            " LEFT JOIN lookup_site_id lsi ON (org.site_id = lsi.code) " +
            " LEFT JOIN contact ct ON (qe.contact_id = ct.contact_id) " +
            " LEFT JOIN lookup_quote_status lqs ON ( qe.status_id = lqs.code ) " +
            " LEFT JOIN opportunity_header opp ON (qe.opp_id = opp.opp_id) " +
            " WHERE qe.quote_id > -1 ");
    createFilter(sqlFilter, db);
    if (pagedListInfo != null) {
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

      //Determine column to sort by
      pagedListInfo.setDefaultSort("qe.entered", null);
      boolean flag = true;
      if (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL) {
        if (pagedListInfo.getColumnToSortBy().equals("qe.short_description")) {
          sqlOrder.append(
              "ORDER BY " + DatabaseUtils.convertToVarChar(
                  db, "qe.short_description") + (pagedListInfo.getSortOrder() != null ? " DESC " : " "));
          flag = false;
        }
      }
      if (flag) {
        pagedListInfo.appendSqlTail(db, sqlOrder);
      }
    } else {
      sqlOrder.append("ORDER BY qe.quote_id ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " qe.*, " +
            " org.name, ct.namelast, ct.namefirst, ct.namemiddle, lqs.description AS statusName, opp." + DatabaseUtils.addQuotes(db, "lock")+ " AS opplock, " +
            " lsi.description AS sitename " +
            " FROM quote_entry qe " +
            " LEFT JOIN quote_group qg ON (qe.group_id = qg.group_id) " +
            " LEFT JOIN organization org ON (qe.org_id = org.org_id) " +
            " LEFT JOIN lookup_site_id lsi ON (org.site_id = lsi.code) " +
            " LEFT JOIN contact ct ON (qe.contact_id = ct.contact_id) " +
            " LEFT JOIN lookup_quote_status lqs ON ( qe.status_id = lqs.code ) " +
            " LEFT JOIN opportunity_header opp ON (qe.opp_id = opp.opp_id) " +
            " WHERE qe.quote_id > -1 ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      Quote thisQuote = new Quote(rs);
      this.add(thisQuote);
    }
    rs.close();
    pst.close();
    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        Quote thisQuote = (Quote) i.next();
        thisQuote.buildProducts(db);
        thisQuote.retrieveTicket(db);
      }
    }
    if (this.getBuildCompleteVersionList() == true) {
      int size = this.size();
      for (int i = 0; i < size; i++) {
        Quote quote = (Quote) this.get(i);
        quote.setBuildCompleteVersionList(true);
        quote.buildVersionList(db);
        if (quote.getVersionList().size() > 0) {
          this.addAll(this.size(), quote.getVersionList());
        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int filterOnProductName(Connection db) throws SQLException {
    int counter = 0;
    Iterator iterator = this.iterator();
    while (iterator.hasNext()) {
      Quote quote = (Quote) iterator.next();
      quote.setBuildProducts(true);
      quote.getProductList().setProductName(this.getProductName());
      quote.queryRecord(db, quote.getId());
      if (quote.getProductList().size() == 0) {
        iterator.remove();
      } else {
        counter++;
      }
    }
    return counter;
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter, Connection db) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (id > -1) {
      sqlFilter.append("AND qe.quote_id = ? ");
    }
    if (parentId > -1) {
      sqlFilter.append(" AND qe.parent_id = ? ");
    }
    if (orgId > -1) {
      sqlFilter.append("AND qe.org_id = ? ");
    }
    if (contactId > -1) {
      sqlFilter.append("AND qe.contact_id = ? ");
    }
    if (sourceId > -1) {
      sqlFilter.append("AND qe.source_id = ? ");
    }
    if (statusId > -1) {
      sqlFilter.append("AND qe.status_id = ? ");
    }
    if (typeId > -1) {
      sqlFilter.append("AND qe.quote_type_id = ? ");
    }
    if (enteredBy > -1) {
      sqlFilter.append(" AND qe.enteredby = ? ");
    }
    if (modifiedBy > -1) {
      sqlFilter.append(" AND qe.modifiedby = ? ");
    }
    if (ticketId > -1) {
      sqlFilter.append(" AND qe.ticketid = ? ");
    }
    if (categoryId > -1) {
      sqlFilter.append(
          "AND qe.product_id IN ( SELECT product_id " +
              " FROM product_catalog_category_map pccm " +
              " WHERE pccm.category_id = ? ) ");
    }
    if (closedOnly == Constants.TRUE) {
      sqlFilter.append(
          " AND qe.closed < " + DatabaseUtils.getCurrentTimestamp(db) + " ");
    } else if (closedOnly == Constants.FALSE) {
      sqlFilter.append(
          " AND (qe.closed > " + DatabaseUtils.getCurrentTimestamp(db) +
              " OR qe.closed IS NULL ) ");
    }
    if (headerId > -1) {
      sqlFilter.append(" AND opp.opp_id = ? ");
    }
    if (version != null) {
      sqlFilter.append(" AND qe." + DatabaseUtils.addQuotes(db, "version")+ " = ? ");
    }
    if (groupId != -1) {
      sqlFilter.append(" AND qe.group_id = ? ");
    }
    if (!deleteAllQuotes) {
      if (topOnly == Constants.TRUE) {
        sqlFilter.append(" AND qe.parent_id IS NULL ");
      } else if (topOnly == Constants.FALSE) {
        sqlFilter.append(" AND qe.parent_id IS NOT NULL ");
      }
    }
    if (deliveryId != -1) {
      sqlFilter.append(" AND qe.delivery_id = ? ");
    }
    if (submitAction == Constants.FALSE) {
      sqlFilter.append(" AND qe.submit_action IS NULL ");
    } else if (submitAction == Constants.TRUE) {
      sqlFilter.append(" AND qe.submit_action IS NOT NULL ");
    }

    if (productName != null && !"".equals(productName) && !"%%".equals(
        productName)) {
      sqlFilter.append(
          "AND qe.quote_id IN ( " +
              "SELECT pq.quote_id " +
              "FROM quote_product pq " +
              "LEFT JOIN product_catalog pctlg ON (pq.product_id = pctlg.product_id) " +
              "WHERE pctlg.product_name LIKE ? ) ");
    }
    if (sku != null && !"".equals(sku) && !"%%".equals(sku)) {
      sqlFilter.append(
          "AND qe.quote_id IN ( " +
              "SELECT pq.quote_id " +
              "FROM quote_product pq " +
              "LEFT JOIN product_catalog pctlg ON (pq.product_id = pctlg.product_id) " +
              "WHERE pctlg.sku LIKE ? ) ");
    }

    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND pctlg.entered >= ? ");
      }
      sqlFilter.append(" AND pctlg.entered < ? ");
    } else if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append(" AND pctlg.modified >= ? ");
      sqlFilter.append(" AND pctlg.entered < ? ");
      sqlFilter.append(" AND pctlg.modified < ? ");
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        sqlFilter.append("AND pctlg.entered >= ? ");
      }
      if (nextAnchor != null) {
        sqlFilter.append("AND pctlg.entered < ? ");
      }
    }
    if (logoFileId != -1) {
      sqlFilter.append("AND qe.logo_file_id = ? ");
    }
    if (!includeAllSites && orgId == -1 && contactId == -1 && enteredBy == -1
        && ticketId == -1 && parentId == -1 && headerId == -1) {
      if (siteId != -1) {
        sqlFilter.append("AND (org.site_id = ? ");
        if (!exclusiveToSite) {
          sqlFilter.append("OR org.site_id IS NULL ");
        }
        sqlFilter.append(") ");
      } else {
        sqlFilter.append("AND org.site_id IS NULL ");
      }
    }
    if (includeOnlyTrashed) {
      sqlFilter.append("AND qe.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND qe.trashed_date = ? ");
    } else {
      sqlFilter.append("AND qe.trashed_date IS NULL ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (parentId > -1) {
      pst.setInt(++i, parentId);
    }
    if (orgId > -1) {
      pst.setInt(++i, orgId);
    }
    if (contactId > -1) {
      pst.setInt(++i, contactId);
    }
    if (sourceId > -1) {
      pst.setInt(++i, sourceId);
    }
    if (statusId > -1) {
      pst.setInt(++i, statusId);
    }
    if (typeId > -1) {
      pst.setInt(++i, typeId);
    }
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }
    if (modifiedBy > -1) {
      pst.setInt(++i, modifiedBy);
    }
    if (ticketId > -1) {
      pst.setInt(++i, ticketId);
    }
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    if (headerId > -1) {
      pst.setInt(++i, headerId);
    }
    if (version != null) {
      pst.setString(++i, version);
    }
    if (groupId != -1) {
      pst.setInt(++i, groupId);
    }
    if (deliveryId != -1) {
      pst.setInt(++i, deliveryId);
    }
    if (productName != null && !"".equals(productName) && !"%%".equals(
        productName)) {
      pst.setString(++i, productName);
    }
    if (sku != null && !"".equals(sku) && !"%%".equals(sku)) {
      pst.setString(++i, sku);
    }
    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      if (nextAnchor != null) {
        pst.setTimestamp(++i, nextAnchor);
      }
    }
    if (logoFileId != -1) {
      pst.setInt(++i, logoFileId);
    }
    if (!includeAllSites && orgId == -1 && contactId == -1 && enteredBy == -1
        && ticketId == -1 && parentId == -1 && headerId == -1 && siteId != -1) {
      pst.setInt(++i, siteId);
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator quotes = this.iterator();
    while (quotes.hasNext()) {
      Quote quote = (Quote) quotes.next();
      quote.delete(db);
      quotes.remove();
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public QuoteList filterMyQuotes(Connection db) throws SQLException {
    Iterator quotes = this.iterator();
    while (quotes.hasNext()) {
      Quote quote = (Quote) quotes.next();
      if (quote.getEnteredBy() != quote.getTicket().getAssignedTo()) {
        this.remove(quote);
      }
    }
    return this;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void removeLogoLink(Connection db) throws SQLException {
    Iterator quotes = this.iterator();
    while (quotes.hasNext()) {
      Quote quote = (Quote) quotes.next();
      quote.setLogoFileId(-1);
      quote.update(db);
    }
  }


  /**
   * Description of the Method
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
        "SELECT COUNT(DISTINCT(group_id)) AS itemcount " +
            "FROM quote_entry qe " +
            "WHERE qe.quote_id > 0 " +
            "AND qe.trashed_date IS NULL ");
    if (moduleId == Constants.ACCOUNTS) {
      sql.append("AND qe.org_id = ? ");
    } else if (moduleId == Constants.CONTACTS) {
      sql.append("AND qe.contact_id = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (moduleId == Constants.ACCOUNTS) {
      pst.setInt(1, itemId);
    } else if (moduleId == Constants.CONTACTS) {
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
   * @param db        Description of the Parameter
   * @param toTrash   Description of the Parameter
   * @param tmpUserId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      Quote tmpQuote = (Quote) itr.next();
      tmpQuote.updateStatus(db, toTrash, tmpUserId);
    }
    return true;
  }

}

