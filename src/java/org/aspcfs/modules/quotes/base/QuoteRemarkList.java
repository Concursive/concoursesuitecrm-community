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
package org.aspcfs.modules.quotes.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created November 1, 2004
 */
public class QuoteRemarkList extends ArrayList {
  //filters
  public final static String tableName = "quote_remark";
  public final static String uniqueField = "map_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int quoteId = -1;
  private int remarkId = -1;

  /**
   * Sets the lastAnchor attribute of the QuoteRemarkList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the QuoteRemarkList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the QuoteRemarkList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the QuoteRemarkList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the QuoteRemarkList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Gets the tableName attribute of the QuoteRemarkList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the QuoteRemarkList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the pagedListInfo attribute of the QuoteRemarkList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the QuoteRemarkList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the id attribute of the QuoteRemarkList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the QuoteRemarkList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the QuoteRemarkList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the quoteId attribute of the QuoteRemarkList object
   *
   * @return The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   * Sets the quoteId attribute of the QuoteRemarkList object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   * Sets the quoteId attribute of the QuoteRemarkList object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the remarkId attribute of the QuoteRemarkList object
   *
   * @return The remarkId value
   */
  public int getRemarkId() {
    return remarkId;
  }


  /**
   * Sets the remarkId attribute of the QuoteRemarkList object
   *
   * @param tmp The new remarkId value
   */
  public void setRemarkId(int tmp) {
    this.remarkId = tmp;
  }


  /**
   * Sets the remarkId attribute of the QuoteRemarkList object
   *
   * @param tmp The new remarkId value
   */
  public void setRemarkId(String tmp) {
    this.remarkId = Integer.parseInt(tmp);
  }


  /**
   * Constructor for the QuoteRemarkList object
   */
  public QuoteRemarkList() {
  }


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
        "SELECT COUNT(*) AS recordcount " +
            "FROM quote_remark qr " +
            "LEFT JOIN quote_entry qe ON (qr.quote_id = qe.quote_id) " +
            "LEFT JOIN lookup_quote_remarks lqr ON (qr.remark_id = lqr.code) " +
            "WHERE qr.map_id > -1 ");
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
      rs.close();
      pst.close();
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
                sqlFilter.toString() +
                "AND " + DatabaseUtils.toLowerCase(db) + "(lqr.description) < ? ");
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
      pagedListInfo.setDefaultSort("qr.map_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY qr.map_id ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "qr.*, " +
            "lqr.description AS remark_name " +
            "FROM quote_remark qr " +
            "LEFT JOIN quote_entry qe ON (qr.quote_id = qe.quote_id) " +
            "LEFT JOIN lookup_quote_remarks lqr ON (qr.remark_id = lqr.code) " +
            "WHERE qr.map_id > -1 ");

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
      QuoteRemark thisQuoteRemark = new QuoteRemark(rs);
      this.add(thisQuoteRemark);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (id > -1) {
      sqlFilter.append("AND qr.map_id = ? ");
    }
    if (quoteId > -1) {
      sqlFilter.append("AND qr.quote_id = ? ");
    }
    if (remarkId > -1) {
      sqlFilter.append("AND qr.remark_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
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
    if (quoteId > -1) {
      pst.setInt(++i, quoteId);
    }
    if (remarkId > -1) {
      pst.setInt(++i, remarkId);
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator iterator = this.iterator();
    while (iterator.hasNext()) {
      QuoteRemark quoteRemark = (QuoteRemark) iterator.next();
      quoteRemark.delete(db);
      iterator.remove();
    }
  }


  /**
   * Description of the Method
   *
   * @param id Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasRemark(int id) {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      QuoteRemark remark = (QuoteRemark) iterator.next();
      if (remark.getRemarkId() == id) {
        return true;
      }
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public HashMap retrieveMap() {
    HashMap map = new HashMap();
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      QuoteRemark remark = (QuoteRemark) iterator.next();
      map.put("" + remark.getRemarkId(), "" + remark.getId());
    }
    return map;
  }


  /**
   * Description of the Method
   *
   * @param db  Description of the Parameter
   * @param map Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean fixDeletedRemarks(Connection db, HashMap map) throws SQLException {
    if (map.isEmpty()) {
      return true;
    }
    Iterator deleteIterator = (Iterator) map.keySet().iterator();
    while (deleteIterator.hasNext()) {
      String key = (String) deleteIterator.next();
      String mapId = (String) map.get(key);
      if (mapId != null || !"".equals(mapId)) {
        QuoteRemark remark = new QuoteRemark(db, Integer.parseInt(mapId));
        if (!remark.delete(db)) {
          return false;
        }
      }
    }
    return true;
  }

}

