//Copyright 2003 Dark Horse Ventures

package org.aspcfs.modules.reports.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  A collection of ReportQueue objects
 *
 *@author     matt rajkowski
 *@created    October 1, 2003
 *@version    $Id: CriteriaList.java,v 1.1.2.1 2003/09/15 20:58:21 mrajkowski
 *      Exp $
 */
public class ReportQueueList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int enteredBy = -1;
  private boolean buildResources = false;
  private boolean processedOnly = false;
  private boolean unprocessedOnly = false;
  private boolean inQueueOnly = false;
  private boolean sortAscending = false;
  private java.sql.Timestamp rangeStart = null;
  private java.sql.Timestamp rangeEnd = null;


  /**
   *  Constructor for the CategoryList object
   */
  public ReportQueueList() { }


  /**
   *  Sets the pagedListInfo attribute of the ReportList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the pagedListInfo attribute of the ReportList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the enteredBy attribute of the ReportQueueList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ReportQueueList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the enteredBy attribute of the ReportQueueList object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Sets the buildResources attribute of the ReportQueueList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the buildResources attribute of the ReportQueueList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildResources attribute of the ReportQueueList object
   *
   *@return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Sets the processedOnly attribute of the ReportQueueList object
   *
   *@param  tmp  The new processedOnly value
   */
  public void setProcessedOnly(boolean tmp) {
    this.processedOnly = tmp;
  }


  /**
   *  Sets the processedOnly attribute of the ReportQueueList object
   *
   *@param  tmp  The new processedOnly value
   */
  public void setProcessedOnly(String tmp) {
    this.processedOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the unprocessedOnly attribute of the ReportQueueList object
   *
   *@param  tmp  The new unprocessedOnly value
   */
  public void setUnprocessedOnly(boolean tmp) {
    this.unprocessedOnly = tmp;
  }


  /**
   *  Sets the unprocessedOnly attribute of the ReportQueueList object
   *
   *@param  tmp  The new unprocessedOnly value
   */
  public void setUnprocessedOnly(String tmp) {
    this.unprocessedOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the inQueueOnly attribute of the ReportQueueList object
   *
   *@param  tmp  The new inQueueOnly value
   */
  public void setInQueueOnly(boolean tmp) {
    this.inQueueOnly = tmp;
  }


  /**
   *  Sets the inQueueOnly attribute of the ReportQueueList object
   *
   *@param  tmp  The new inQueueOnly value
   */
  public void setInQueueOnly(String tmp) {
    this.inQueueOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the sortAscending attribute of the ReportQueueList object
   *
   *@param  tmp  The new sortAscending value
   */
  public void setSortAscending(boolean tmp) {
    this.sortAscending = tmp;
  }


  /**
   *  Sets the sortAscending attribute of the ReportQueueList object
   *
   *@param  tmp  The new sortAscending value
   */
  public void setSortAscending(String tmp) {
    this.sortAscending = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the rangeStart attribute of the ReportQueueList object
   *
   *@param  tmp  The new rangeStart value
   */
  public void setRangeStart(java.sql.Timestamp tmp) {
    this.rangeStart = tmp;
  }


  /**
   *  Sets the rangeStart attribute of the ReportQueueList object
   *
   *@param  tmp  The new rangeStart value
   */
  public void setRangeStart(String tmp) {
    this.rangeStart = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the rangeEnd attribute of the ReportQueueList object
   *
   *@param  tmp  The new rangeEnd value
   */
  public void setRangeEnd(java.sql.Timestamp tmp) {
    this.rangeEnd = tmp;
  }


  /**
   *  Sets the rangeEnd attribute of the ReportQueueList object
   *
   *@param  tmp  The new rangeEnd value
   */
  public void setRangeEnd(String tmp) {
    this.rangeEnd = DatabaseUtils.parseTimestamp(tmp);
  }



  /**
   *  Gets the processedOnly attribute of the ReportQueueList object
   *
   *@return    The processedOnly value
   */
  public boolean getProcessedOnly() {
    return processedOnly;
  }


  /**
   *  Gets the unprocessedOnly attribute of the ReportQueueList object
   *
   *@return    The unprocessedOnly value
   */
  public boolean getUnprocessedOnly() {
    return unprocessedOnly;
  }


  /**
   *  Gets the inQueueOnly attribute of the ReportQueueList object
   *
   *@return    The inQueueOnly value
   */
  public boolean getInQueueOnly() {
    return inQueueOnly;
  }


  /**
   *  Gets the sortAscending attribute of the ReportQueueList object
   *
   *@return    The sortAscending value
   */
  public boolean getSortAscending() {
    return sortAscending;
  }


  /**
   *  Gets the rangeStart attribute of the ReportQueueList object
   *
   *@return    The rangeStart value
   */
  public java.sql.Timestamp getRangeStart() {
    return rangeStart;
  }


  /**
   *  Gets the rangeEnd attribute of the ReportQueueList object
   *
   *@return    The rangeEnd value
   */
  public java.sql.Timestamp getRangeEnd() {
    return rangeEnd;
  }


  /**
   *  Builds a list of ReportQueue objects based on the filter properties that
   *  have been set
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
        "SELECT COUNT(*) as recordcount " +
        "FROM report_queue q " +
        "WHERE queue_id > -1 ");
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
      //Determine column to sort by
      pagedListInfo.setDefaultSort("q.entered", "DESC");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY q.entered " + (sortAscending ? "" : "DESC") + " ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "q.* " +
        "FROM report_queue q " +
        "WHERE queue_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      ReportQueue thisQueue = new ReportQueue(rs);
      this.add(thisQueue);
    }
    rs.close();
    pst.close();
    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        ReportQueue thisQueue = (ReportQueue) i.next();
        thisQueue.buildReport(db);
      }
    }
  }


  /**
   *  Defines additional parameters to be used by the query
   *
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (enteredBy != -1) {
      sqlFilter.append("AND q.enteredby = ? ");
    }
    if (processedOnly) {
      sqlFilter.append("AND q.processed IS NOT NULL ");
    }
    if (unprocessedOnly) {
      sqlFilter.append(
          "AND q.processed IS NULL " +
          "AND q.status = " + ReportQueue.STATUS_QUEUED + " ");
    }
    if (inQueueOnly) {
      sqlFilter.append("AND q.processed IS NULL ");
    }
    if (rangeStart != null) {
      sqlFilter.append("AND q.processed >= ? ");
    }
    if (rangeEnd != null) {
      sqlFilter.append("AND q.processed < ? ");
    }
  }


  /**
   *  Sets the additional parameters for the query
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (enteredBy != -1) {
      pst.setInt(++i, enteredBy);
    }
    if (rangeStart != null) {
      pst.setTimestamp(++i, rangeStart);
    }
    if (rangeEnd != null) {
      pst.setTimestamp(++i, rangeEnd);
    }
    return i;
  }


  /**
   *  Returns whether the specified report was just locked, false if it already
   *  locked
   *
   *@param  thisReport        Description of the Parameter
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static boolean lockReport(ReportQueue thisReport, Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE report_queue " +
        "SET status = ? " +
        "WHERE queue_id = ? " +
        "AND status = ? ");
    pst.setInt(1, ReportQueue.STATUS_PROCESSING);
    pst.setInt(2, thisReport.getId());
    pst.setInt(3, ReportQueue.STATUS_QUEUED);
    int count = pst.executeUpdate();
    pst.close();
    return (count == 1);
  }
}


