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

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.web.PagedListInfo;

import javax.servlet.http.HttpServletRequest;
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
 * @created October 28, 2004
 */
public class QuoteLogList extends ArrayList {
  public final static String tableName = "quotelog";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private PagedListInfo pagedListInfo = null;
  private int quoteId = -1;
  private boolean doSystemMessages = true;
  private SystemStatus systemStatus = null;

  /**
   * Sets the lastAnchor attribute of the QuoteLogList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the QuoteLogList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the QuoteLogList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the QuoteLogList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the QuoteLogList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Gets the tableName attribute of the QuoteLogList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the QuoteLogList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the pagedListInfo attribute of the QuoteLogList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the QuoteLogList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the quoteId attribute of the QuoteLogList object
   *
   * @return The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   * Sets the quoteId attribute of the QuoteLogList object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   * Sets the quoteId attribute of the QuoteLogList object
   *
   * @param tmp The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the doSystemMessages attribute of the QuoteLogList object
   *
   * @return The doSystemMessages value
   */
  public boolean getDoSystemMessages() {
    return doSystemMessages;
  }


  /**
   * Sets the doSystemMessages attribute of the QuoteLogList object
   *
   * @param tmp The new doSystemMessages value
   */
  public void setDoSystemMessages(boolean tmp) {
    this.doSystemMessages = tmp;
  }


  /**
   * Sets the doSystemMessages attribute of the QuoteLogList object
   *
   * @param tmp The new doSystemMessages value
   */
  public void setDoSystemMessages(String tmp) {
    this.doSystemMessages = DatabaseUtils.parseBoolean(tmp);
  }

  public SystemStatus getSystemStatus() {
    return systemStatus;
  }

  public void setSystemStatus(SystemStatus tmp) {
    this.systemStatus = tmp;
  }

  /**
   * Constructor for the QuoteLogList object
   */
  public QuoteLogList() {
  }


  /**
   * Constructor for the QuoteLogList object
   *
   * @param request Description of the Parameter
   * @param userId  Description of the Parameter
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

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM quotelog q " +
            "WHERE q.id > 0 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(
          sqlCount.toString() +
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
        pst = db.prepareStatement(
            sqlCount.toString() +
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
            "FROM quotelog q " +
            "LEFT JOIN contact ct_eb ON (q.enteredby = ct_eb.user_id) " +
            "LEFT JOIN lookup_quote_status lqst ON (q.status_id = lqst.code) " +
            "LEFT JOIN lookup_quote_source lqso ON (q.source_id = lqso.code) " +
            "LEFT JOIN lookup_quote_terms lqtm ON (q.terms_id = lqtm.code) " +
            "LEFT JOIN lookup_quote_type lqty ON (q.type_id = lqty.code) " +
            "LEFT JOIN lookup_quote_delivery lqd ON (q.delivery_id = lqd.code) " +
            "WHERE q.id > 0 ");
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
    QuoteLog prevQuoteLog = null;
    while (rs.next()) {
      QuoteLog thisQuoteLog = new QuoteLog(rs);
      if (doSystemMessages) {
        //Add the system generated messages
        this.setSystemMessages(thisQuoteLog, prevQuoteLog);
        if (thisQuoteLog.getNotes() != null && !"".equals(
            thisQuoteLog.getNotes().trim())) {
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
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (quoteId > -1) {
      sqlFilter.append("AND q.quote_id = ? ");
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
    if (quoteId > -1) {
      pst.setInt(++i, quoteId);
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

  public String getLabel(SystemStatus systemStatus, HashMap map, String input) {
    Template template = new Template(input);
    template.setParseElements(map);
    return template.getParsedText();
  }

  /**
   * Sets the systemMessages attribute of the QuoteLogList object
   *
   * @param current The new systemMessages value
   * @param prev    The new systemMessages value
   * @return Description of the Return Value
   */
  public boolean setSystemMessages(QuoteLog current, QuoteLog prev) {
    QuoteLog tempLog = null;
    if (prev == null) {
      //First log entry
      if (1 == 1) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          tempLog.setNotes(
              "[ " + systemStatus.getLabel("quotes.quotelog.quoteCreated") + " ]");
        } else {
          tempLog.setNotes("[ Quote Created ]");
        }
        this.add(tempLog);
      }
      if (current.getDeliveryId() > 0) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          String logEntry = systemStatus.getLabel(
              "quotes.quotelog.deliverySetTo.text");
          if (logEntry != null) {
            HashMap map = new HashMap();
            map.put("${name}", current.getDeliveryName());
            tempLog.setNotes(
                "[ " + this.getLabel(systemStatus, map, logEntry) + " ]");
          }
        } else {
          tempLog.setNotes(
              "[ Delivery set to " + current.getDeliveryName() + " ]");
        }
        this.add(tempLog);
      }
      if (current.getSourceId() > 0) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          String logEntry = systemStatus.getLabel(
              "quotes.quotelog.sourceIs.text");
          if (logEntry != null) {
            HashMap map = new HashMap();
            map.put("${name}", current.getSourceName());
            tempLog.setNotes(
                "[ " + this.getLabel(systemStatus, map, logEntry) + " ]");
          }
        } else {
          tempLog.setNotes(
              "[ The Source is " + current.getSourceName() + " ]");
        }
        this.add(tempLog);
      }
      if (current.getStatusId() > 0) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          String logEntry = systemStatus.getLabel(
              "quotes.quotelog.statusIs.text");
          if (logEntry != null) {
            HashMap map = new HashMap();
            map.put("${name}", current.getStatusName());
            tempLog.setNotes(
                "[ " + this.getLabel(systemStatus, map, logEntry) + " ]");
          }
        } else {
          tempLog.setNotes("[ Status is " + current.getStatusName() + " ]");
        }
        this.add(tempLog);
      }
      if (current.getIssuedDate() != null) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          tempLog.setNotes(
              "[ " + systemStatus.getLabel(
                  "quotes.quotelog.quoteIssuedToCustomer") + " ]");
        } else {
          tempLog.setNotes("[ Quote issued to the Customer ]");
        }
        this.add(tempLog);
      }
/*
      if (current.getGrandTotal() > 0) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          String logEntry = systemStatus.getLabel("quotes.quotelog.grandTotalValue.text");
          if (logEntry != null) {
            HashMap map = new HashMap();
            map.put("${grandTotalValue}", ""+current.getGrandTotal());
            tempLog.setNotes("[ " + this.getLabel(systemStatus, map, logEntry) + " ]");
          }
        } else {
          tempLog.setNotes("[ Grand total value of the Quote is " + current.getGrandTotal() + " ]");
        }
        this.add(tempLog);
      }
*/
      if (current.getClosed() != null) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          tempLog.setNotes(
              "[ " + systemStatus.getLabel("quotes.quotelog.quoteClosed") + " ]");
        } else {
          tempLog.setNotes("[ Quote closed ]");
        }
        this.add(tempLog);
      }
      return true;
    } else {
      //Comparative log entry
      if (current.getSourceId() != prev.getSourceId()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          String logEntry = systemStatus.getLabel(
              "quotes.quotelog.quoteSourceChangedFrom.text");
          if (logEntry != null) {
            HashMap map = new HashMap();
            map.put("${previousName}", isSet(prev.getSourceName()));
            map.put("${currentName}", isSet(current.getSourceName()));
            tempLog.setNotes(
                "[ " + this.getLabel(systemStatus, map, logEntry) + " ]");
          }
        } else {
          tempLog.setNotes(
              "[ Source changed from " + isSet(prev.getSourceName()) + " to " + isSet(
                  current.getSourceName()) + " ]");
        }
        this.add(tempLog);
      }
      if (current.getStatusId() != prev.getStatusId()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          String logEntry = systemStatus.getLabel(
              "quotes.quotelog.quoteStatusChangedFrom.text");
          if (logEntry != null) {
            HashMap map = new HashMap();
            map.put("${previousName}", isSet(prev.getStatusName()));
            map.put("${currentName}", isSet(current.getStatusName()));
            tempLog.setNotes(
                "[ " + this.getLabel(systemStatus, map, logEntry) + " ]");
          }
        } else {
          tempLog.setNotes(
              "[ Status changed from " + isSet(prev.getStatusName()) + " to " + isSet(
                  current.getStatusName()) + " ]");
        }
        this.add(tempLog);
      }
      if (current.getIssuedDate() != prev.getIssuedDate()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (prev.getIssuedDate() != null) {
          if (systemStatus != null) {
            tempLog.setNotes(
                "[ " + systemStatus.getLabel(
                    "quotes.quotelog.quoteModifiedAndReIssued") + " ]");
          } else {
            tempLog.setNotes("[ Quote modified and re-issued ]");
          }
        } else {
          if (systemStatus != null) {
            tempLog.setNotes(
                "[ " + systemStatus.getLabel(
                    "quotes.quotelog.quoteIssuedToCustomer") + " ]");
          } else {
            tempLog.setNotes("[ Quote issued to the Customer ]");
          }
        }
        this.add(tempLog);
      }
      if (current.getTermsId() != prev.getTermsId()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          String logEntry = systemStatus.getLabel(
              "quotes.quotelog.termsChangedFrom.text");
          if (logEntry != null) {
            HashMap map = new HashMap();
            map.put("${previousName}", isSet(prev.getTermsName()));
            map.put("${currentName}", isSet(current.getTermsName()));
            tempLog.setNotes(
                "[ " + this.getLabel(systemStatus, map, logEntry) + " ]");
          }
        } else {
          tempLog.setNotes(
              "[ Terms changed from " + isSet(prev.getTermsName()) + " to " + isSet(
                  current.getTermsName()) + " ]");
        }
        this.add(tempLog);
      }
      if (current.getTypeId() != prev.getTypeId()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          String logEntry = systemStatus.getLabel(
              "quotes.quotelog.typeChangedFrom.text");
          if (logEntry != null) {
            HashMap map = new HashMap();
            map.put("${previousName}", isSet(prev.getTypeName()));
            map.put("${currentName}", isSet(current.getTypeName()));
            tempLog.setNotes(
                "[ " + this.getLabel(systemStatus, map, logEntry) + " ]");
          }
        } else {
          tempLog.setNotes(
              "[ Type changed from " + isSet(prev.getTypeName()) + " to " + isSet(
                  current.getTypeName()) + " ]");
        }
        this.add(tempLog);
      }
      if (current.getDeliveryId() != prev.getDeliveryId()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          String logEntry = systemStatus.getLabel(
              "quotes.quotelog.deliveryChangedFrom.text");
          if (logEntry != null) {
            HashMap map = new HashMap();
            map.put("${previousName}", isSet(prev.getDeliveryName()));
            map.put("${currentName}", isSet(current.getDeliveryName()));
            tempLog.setNotes(
                "[ " + this.getLabel(systemStatus, map, logEntry) + " ]");
          }
        } else {
          tempLog.setNotes(
              "[ Delivery changed from " + isSet(prev.getDeliveryName()) + " to " + isSet(
                  current.getDeliveryName()) + " ]");
        }
        this.add(tempLog);
      }
/*
      if (Double.compare(current.getGrandTotal(), prev.getGrandTotal()) != 0) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (systemStatus != null) {
          String logEntry = systemStatus.getLabel("quotes.quotelog.grandTotalChangedFrom.text");
          if (logEntry != null) {
            HashMap map = new HashMap();
            map.put("${previousValue}", ""+prev.getGrandTotal());
            map.put("${currentValue}", ""+current.getGrandTotal());
            tempLog.setNotes("[ " + this.getLabel(systemStatus, map, logEntry) + " ]");
          }
        } else {
          tempLog.setNotes("[ Grand total value of the Quote changed from " + prev.getGrandTotal() + " to " + current.getGrandTotal() + " ]");
        }
        this.add(tempLog);
      }
*/
      if (current.getClosed() != prev.getClosed()) {
        tempLog = new QuoteLog();
        tempLog.createSysMsg(current);
        if (prev.getClosed() != null) {
          if (systemStatus != null) {
            tempLog.setNotes(
                "[ " + systemStatus.getLabel(
                    "quotes.quotelog.quoteClosedWithDifferentStatus") + " ]");
          } else {
            tempLog.setNotes("[ Quote closed with a different status ]");
          }
        } else {
          if (systemStatus != null) {
            tempLog.setNotes(
                "[ " + systemStatus.getLabel("quotes.quotelog.quoteClosed") + " ]");
          } else {
            tempLog.setNotes("[ Quote closed ]");
          }
        }
        this.add(tempLog);
      }
      return true;
    }
  }


  /**
   * Gets the set attribute of the QuoteLogList object
   *
   * @param value Description of the Parameter
   * @return The set value
   */
  private String isSet(String value) {
    if (value == null) {
      return "not set";
    } else {
      return value;
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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

