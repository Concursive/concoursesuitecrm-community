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
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.PagedListInfo;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id: ContractExpirationLogList.java,v 1.1.2.3 2004/10/05 18:24:01
 *          kbhoopal Exp $
 * @created September 27, 2004
 */
public class ContractExpirationLogList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int expirationId = -1;

  public final static String tableName = "netapp_contractexpiration_log";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private boolean doSystemMessages = true;


  /**
   * Constructor for the ContractExpirationLogList object
   */
  public ContractExpirationLogList() {
  }


  /**
   * Sets the pagedListInfo attribute of the ContractExpirationLogList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the expirationId attribute of the ContractExpirationLogList object
   *
   * @param tmp The new expirationId value
   */
  public void setExpirationId(int tmp) {
    this.expirationId = tmp;
  }


  /**
   * Sets the expirationId attribute of the ContractExpirationLogList object
   *
   * @param tmp The new expirationId value
   */
  public void setExpirationId(String tmp) {
    this.expirationId = Integer.parseInt(tmp);
  }


  /**
   * Gets the expirationId attribute of the ContractExpirationLogList object
   *
   * @return The expirationId value
   */
  public int getExpirationId() {
    return expirationId;
  }


  /**
   * Gets the doSystemMessages attribute of the ContractExpirationLogList
   * object
   *
   * @return The doSystemMessages value
   */
  public boolean getDoSystemMessages() {
    return doSystemMessages;
  }


  /**
   * Sets the doSystemMessages attribute of the ContractExpirationLogList
   * object
   *
   * @param doSystemMessages The new doSystemMessages value
   */
  public void setDoSystemMessages(boolean doSystemMessages) {
    this.doSystemMessages = doSystemMessages;
  }


  /**
   * Sets the systemMessages attribute of the ContractExpirationLogList object
   *
   * @param current The new systemMessages value
   * @param prev    The new systemMessages value
   * @param request The new systemMessages value
   * @return Description of the Return Value
   */
  public boolean setSystemMessages(HttpServletRequest request, ContractExpirationLog current, ContractExpirationLog prev) {
    ContractExpirationLog tempLog = null;
    Locale locale = ((UserBean) request.getSession().getAttribute("User")).getUserRecord().getLocale();
    SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
        DateFormat.SHORT, locale);
    formatter.applyPattern(DateUtils.get4DigitYearDateFormat(formatter.toPattern()));

    if (prev == null) {
      //First log entry
      /*
       *  if (1 == 1) {
       *  tempLog = new ContractExpirationLog();
       *  tempLog.createSysMsg(current);
       *  tempLog.setEntryText("[ Contract Expiration Inserted ]");
       *  this.add(tempLog);
       *  }
       */
      /*
      if (current.getQuoteAmount() > -1.0) {
        tempLog = new ContractExpirationLog();
        tempLog.createSysMsg(current);
        tempLog.setEntryText("[ Quote amount is " + current.getQuoteAmount() + " ]");
        this.add(tempLog);
      }
      */
      if (current.getQuoteGeneratedDate() != null) {
        tempLog = new ContractExpirationLog();
        tempLog.createSysMsg(current);
        tempLog.setEntryText("[ Quote generated on " + formatter.format(current.getQuoteGeneratedDate()) + " ]");
        this.add(tempLog);
      }
      if (current.getQuoteAcceptedDate() != null) {
        tempLog = new ContractExpirationLog();
        tempLog.createSysMsg(current);
        tempLog.setEntryText("[ Quote accepted on " + formatter.format(current.getQuoteAcceptedDate()) + " ]");
        this.add(tempLog);
      }
      if (current.getQuoteRejectedDate() != null) {
        tempLog = new ContractExpirationLog();
        tempLog.createSysMsg(current);
        tempLog.setEntryText("[ Quote rejected on " + formatter.format(current.getQuoteRejectedDate()) + " ]");
        this.add(tempLog);
      }
      if (current.getComment() != null) {
        tempLog = new ContractExpirationLog();
        tempLog.createSysMsg(current);
        tempLog.setEntryText("Comment:" + (("".equals(current.getComment())) ? "[blank]" : current.getComment()));
        this.add(tempLog);
      }
      return true;
    } else {
      //Comparative log entry
      /*
      if (current.getQuoteAmount() != prev.getQuoteAmount()) {
        tempLog = new ContractExpirationLog();
        tempLog.createSysMsg(current);

        tempLog.setEntryText("[ Quote amount changed from " + ((prev.getQuoteAmount() != -1.0) ? prev.getQuoteAmount() : 0.0) + " to " + ((current.getQuoteAmount() != -1.0) ? current.getQuoteAmount() : 0.0) + " ]");
        this.add(tempLog);
      }
      */
      if ((current.getQuoteGeneratedDate() != null) && (!current.getQuoteGeneratedDate().equals(prev.getQuoteGeneratedDate()))) {
        tempLog = new ContractExpirationLog();
        tempLog.createSysMsg(current);
        tempLog.setEntryText("[ Quote generated on " + formatter.format(current.getQuoteGeneratedDate()) + " ]");
        this.add(tempLog);
      }
      if ((current.getQuoteAcceptedDate() != null) && (!current.getQuoteAcceptedDate().equals(prev.getQuoteAcceptedDate()))) {
        tempLog = new ContractExpirationLog();
        tempLog.createSysMsg(current);
        tempLog.setEntryText("[ Quote accepted on " + formatter.format(current.getQuoteAcceptedDate()) + " ]");
        this.add(tempLog);
      }
      if ((current.getQuoteRejectedDate() != null) && (!current.getQuoteRejectedDate().equals(prev.getQuoteRejectedDate()))) {
        tempLog = new ContractExpirationLog();
        tempLog.createSysMsg(current);
        tempLog.setEntryText("[ Quote rejected on " + formatter.format(current.getQuoteRejectedDate()) + " ]");
        this.add(tempLog);
      }
      if (current.getComment() != null) {
        tempLog = new ContractExpirationLog();
        tempLog.createSysMsg(current);
        tempLog.setEntryText("Comment:" + (("".equals(current.getComment())) ? "[blank]" : current.getComment()));
        if (prev.getComment() == null) {
          this.add(tempLog);
        } else if (!prev.equals(current.getComment())) {
          this.add(tempLog);
        }
      }

      if ((current.getComment() == null) && (prev.getComment() != null)) {
        tempLog = new ContractExpirationLog();
        tempLog.createSysMsg(current);
        tempLog.setEntryText("Comment: [blank]");
        this.add(tempLog);
      }
      return true;
    }
  }


  /**
   * Gets the tableName attribute of the ContractExpirationLogList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ContractExpirationLogList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the lastAnchor attribute of the ContractExpirationLogList object
   *
   * @return The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   * Gets the nextAnchor attribute of the ContractExpirationLogList object
   *
   * @return The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   * Gets the syncType attribute of the ContractExpirationLogList object
   *
   * @return The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   * Sets the lastAnchor attribute of the ContractExpirationLogList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ContractExpirationLogList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the syncType attribute of the ContractExpirationLogList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param request Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(HttpServletRequest request, Connection db) throws SQLException {
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
            "FROM netapp_contractexpiration_log ncl " +
            "WHERE id > 0 ");
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

    }
    //NOTE: Do not change the order due to the system message method
    sqlOrder.append(" ORDER BY ncl.entered ");
    //Need to build a base SQL statement for returning records
    sqlSelect.append("SELECT ");
    sqlSelect.append(
        "ncl.* , " +
            "ct_eb.namelast AS eb_namelast, ct_eb.namefirst AS eb_namefirst " +
            "FROM netapp_contractexpiration_log ncl " +
            "LEFT JOIN contact ct_eb ON (ncl.enteredby = ct_eb.user_id) " +
            "WHERE ncl.id > 0 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    ContractExpirationLog prevContractExpirationLog = null;
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      ContractExpirationLog thisContractExpirationLog = new ContractExpirationLog(rs);
      if (doSystemMessages) {
        //Add the system generated messages
        this.setSystemMessages(request, thisContractExpirationLog, prevContractExpirationLog);
        if (thisContractExpirationLog.getEntryText() != null && !thisContractExpirationLog.getEntryText().equals("")) {
          //Add the comments
          this.add(thisContractExpirationLog);
        }
      } else {
        //Just add the whole thing
        this.add(thisContractExpirationLog);
      }
      prevContractExpirationLog = thisContractExpirationLog;
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (expirationId > -1) {
      sqlFilter.append("AND ncl.expiration_id = ? ");
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
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (expirationId > -1) {
      pst.setInt(++i, expirationId);
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
   * Gets the comments attribute of the ContractExpirationLogList object
   *
   * @return The comments value
   */
  public String getComments() {
    StringBuffer comments = new StringBuffer("");
    Iterator iterator = (Iterator) this.iterator();
    while (iterator.hasNext()) {
      ContractExpirationLog log = (ContractExpirationLog) iterator.next();
      comments.append(log.getEntryText() + " | ");
    }
    return comments.toString();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void deleteList(Connection db) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      ContractExpirationLog contractExpirationLog = (ContractExpirationLog) itr.next();
      contractExpirationLog.delete(db);
    }
  }
}

