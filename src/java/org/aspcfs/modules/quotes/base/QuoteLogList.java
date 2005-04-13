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

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

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
 *@author     partha
 *@created    October 28, 2004
 *@version    $Id$
 */
public class QuoteLogList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int quoteId = -1;
  private boolean doSystemMessages = true;


  /**
   *  Gets the pagedListInfo attribute of the QuoteLogList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the pagedListInfo attribute of the QuoteLogList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the quoteId attribute of the QuoteLogList object
   *
   *@return    The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   *  Sets the quoteId attribute of the QuoteLogList object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   *  Sets the quoteId attribute of the QuoteLogList object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the doSystemMessages attribute of the QuoteLogList object
   *
   *@return    The doSystemMessages value
   */
  public boolean getDoSystemMessages() {
    return doSystemMessages;
  }


  /**
   *  Sets the doSystemMessages attribute of the QuoteLogList object
   *
   *@param  tmp  The new doSystemMessages value
   */
  public void setDoSystemMessages(boolean tmp) {
    this.doSystemMessages = tmp;
  }


  /**
   *  Sets the doSystemMessages attribute of the QuoteLogList object
   *
   *@param  tmp  The new doSystemMessages value
   */
  public void setDoSystemMessages(String tmp) {
    this.doSystemMessages = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Constructor for the QuoteLogList object
   */
  public QuoteLogList() { }


  /**
   *  Constructor for the QuoteLogList object
   *
   *@param  request  Description of the Parameter
   *@param  userId   Description of the Parameter
   */
  public QuoteLogList(HttpServletRequest request, int userId) {
    if (request.getParameter("newquotelogentry") != null) {
      QuoteLog thisEntry = new QuoteLog();
      thisEntry.setEnteredBy(userId);
      thisEntry.buildRecord(request);
      if (thisEntry.isValid()) {
        this.add(thisEntry);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
        "FROM quotelog q " +
        "WHERE q.id > 0 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString());
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
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND q.notes < ? ");
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
      pagedListInfo.setDefaultSort("q.entered", null);
      //NOTE: Do not change the order due to the system message method
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      //NOTE: Do not change the order due to the system message method
      sqlOrder.append("ORDER BY q.entered ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "q.*, " +
        "lqso.description AS source_name, " +
        "lqst.description AS status_name, " +
        "lqtm.description AS terms_name, " +
        "lqty.description AS type_name, " +
        "lqd.description AS delivery_name," +
        "ct_eb.namelast AS eb_namelast, ct_eb.namefirst AS eb_namefirst " +
        "FROM quotelog AS q " +
        "LEFT JOIN contact ct_eb ON (q.enteredby = ct_eb.user_id) " +
        "LEFT JOIN lookup_quote_status AS lqst ON (q.status_id = lqst.code) " +
        "LEFT JOIN lookup_quote_source AS lqso ON (q.source_id = lqso.code) " +
        "LEFT JOIN lookup_quote_terms AS lqtm ON (q.terms_id = lqtm.code) " +
        "LEFT JOIN lookup_quote_type AS lqty ON (q.type_id = lqty.code) " +
        "LEFT JOIN lookup_quote_delivery AS lqd ON (q.delivery_id = lqd.code) " +
        "WHERE q.id > 0 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    QuoteLog prevQuoteLog = null;
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      QuoteLog thisQuoteLog = new QuoteLog(rs);
      if (doSystemMessages) {
        //Add the system generated messages
        this.setSystemMessages(thisQuoteLog, prevQuoteLog);
        if (thisQuoteLog.getNotes() != null && !"".equals(thisQuoteLog.getNotes().trim())) {
          //Add the comments
          this.add(thisQuoteLog);
        }
      } else {
        //Just add the whole thing
        this.add(thisQuoteLog);
      }
      prevQuoteLog = thisQuoteLog;
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (quoteId > -1) {
      sqlFilter.append("AND q.quote_id = ? ");
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
    if (quoteId > -1) {
      pst.setInt(++i, quoteId);
    }
    return i;
  }


  /**
   *  Sets the systemMessages attribute of the QuoteLogList object
   *
   *@param  current  The new systemMessages value
   *@param  prev     The new systemMessages value
   *@return          Description of the Return Value
   */
  public boolean setSystemMessages(QuoteLog current, QuoteLog prev) {
    QuoteLog tempLog = null;
    if (prev == null) {
      //First log entry
      if (1 == 1) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        tempLog.setNotes("[ Quote Created ]");
        this.add(tempLog);
      }
      if (current.getDeliveryId() > 0) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        tempLog.setNotes("[ Delivery set to " + current.getDeliveryName() + " ]");
        this.add(tempLog);
      }
      if (current.getSourceId() > 0) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        tempLog.setNotes("[ The Source is " + current.getSourceName() + " ]");
        this.add(tempLog);
      }
      if (current.getStatusId() > 0) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        tempLog.setNotes("[ Status is " + current.getStatusName() + " ]");
        this.add(tempLog);
      }
      if (current.getIssuedDate() != null) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        tempLog.setNotes("[ Quote issued to the Customer ]");
        this.add(tempLog);
      }
      if (current.getGrandTotal() > 0) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        tempLog.setNotes("[ Grand total value of the Quote is " + current.getGrandTotal() + " ]");
        this.add(tempLog);
      }
      if (current.getClosed() != null) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        tempLog.setNotes("[ Quote closed ]");
        this.add(tempLog);
      }
      return true;
    } else {
      //Comparative log entry
      if (current.getSourceId() != prev.getSourceId()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(prev);
        tempLog.setNotes("[ Source changed from " + isSet(prev.getSourceName()) + " to " + isSet(current.getSourceName()) + " ]");
        this.add(tempLog);
      }
      if (current.getStatusId() != prev.getStatusId()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(prev);
        tempLog.setNotes("[ Status changed from " + isSet(prev.getStatusName()) + " to " + isSet(current.getStatusName()) + " ]");
        this.add(tempLog);
      }
      if (current.getIssuedDate() != prev.getIssuedDate()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(prev);
        if (prev.getIssuedDate() != null) {
          tempLog.setNotes("[ Quote modified and re-issued ]");
        } else {
          tempLog.setNotes("[ Quote issued to the Customer ]");
        }
        this.add(tempLog);
      }
      if (current.getTermsId() != prev.getTermsId()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(prev);
        tempLog.setNotes("[ Terms changed from " + isSet(prev.getTermsName()) + " to " + isSet(current.getTermsName()) + " ]");
        this.add(tempLog);
      }
      if (current.getTypeId() != prev.getTypeId()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(prev);
        tempLog.setNotes("[ Type changed from " + isSet(prev.getTypeName()) + " to " + isSet(current.getTypeName()) + " ]");
        this.add(tempLog);
      }
      if (current.getDeliveryId() != prev.getDeliveryId()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(prev);
        tempLog.setNotes("[ Delivery changed from " + isSet(prev.getDeliveryName()) + " to " + isSet(current.getDeliveryName()) + " ]");
        this.add(tempLog);
      }
      if (Double.compare(current.getGrandTotal(), prev.getGrandTotal()) != 0) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(prev);
        tempLog.setNotes("[ Grand total value of the Quote changed from " + prev.getGrandTotal() + " to " + current.getGrandTotal() + " ]");
        this.add(tempLog);
      }
      if (current.getClosed() != prev.getClosed()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(prev);
        if (prev.getClosed() != null) {
          tempLog.setNotes("[ Quote closed with a different status ]");
        } else {
          tempLog.setNotes("[ Quote closed ]");
        }
        this.add(tempLog);
      }
      return true;
    }
  }


  /**
   *  Gets the set attribute of the QuoteLogList object
   *
   *@param  value  Description of the Parameter
   *@return        The set value
   */
  private String isSet(String value) {
    if (value == null) {
      return "not set";
    } else {
      return value;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      QuoteLog log = (QuoteLog) iterator.next();
      log.delete(db);
      iterator.remove();
    }
  }

}

