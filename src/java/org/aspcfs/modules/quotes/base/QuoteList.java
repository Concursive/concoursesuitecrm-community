package org.aspcfs.modules.quotes.base;

import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

/**
 *  This represents a list of quotes.
 *
 *@author     ananth
 *@created    March 24, 2004
 *@version    $Id$
 */
public class QuoteList extends ArrayList {
  private PagedListInfo pagedListInfo = null;
  private int orgId = -1;
  private int sourceId = -1;
  private int statusId = -1;
  private int typeId = -1;
  private int ticketId = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private boolean buildResources = false;
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;


  /**
   *  Sets the typeId attribute of the QuoteList object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the QuoteList object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ticketId attribute of the QuoteList object
   *
   *@param  tmp  The new ticketId value
   */
  public void setTicketId(int tmp) {
    this.ticketId = tmp;
  }


  /**
   *  Sets the ticketId attribute of the QuoteList object
   *
   *@param  tmp  The new ticketId value
   */
  public void setTicketId(String tmp) {
    this.ticketId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the QuoteList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the QuoteList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the QuoteList object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the QuoteList object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the modifiedBy attribute of the QuoteList object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the enteredBy attribute of the QuoteList object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the typeId attribute of the QuoteList object
   *
   *@return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Sets the pagedListInfo attribute of the QuoteList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the orgId attribute of the QuoteList object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the QuoteList object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the sourceId attribute of the QuoteList object
   *
   *@param  tmp  The new sourceId value
   */
  public void setSourceId(int tmp) {
    this.sourceId = tmp;
  }


  /**
   *  Sets the sourceId attribute of the QuoteList object
   *
   *@param  tmp  The new sourceId value
   */
  public void setSourceId(String tmp) {
    this.sourceId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the QuoteList object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the QuoteList object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildResources attribute of the QuoteList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the buildResources attribute of the QuoteList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the alertRangeStart attribute of the QuoteList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the QuoteList object
   *
   *@param  tmp  The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the alertRangeEnd attribute of the QuoteList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   *  Sets the alertRangeEnd attribute of the QuoteList object
   *
   *@param  tmp  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the alertRangeStart attribute of the QuoteList object
   *
   *@return    The alertRangeStart value
   */
  public java.sql.Timestamp getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   *  Gets the alertRangeEnd attribute of the QuoteList object
   *
   *@return    The alertRangeEnd value
   */
  public java.sql.Timestamp getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   *  Gets the pagedListInfo attribute of the QuoteList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the orgId attribute of the QuoteList object
   *
   *@return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the sourceId attribute of the QuoteList object
   *
   *@return    The sourceId value
   */
  public int getSourceId() {
    return sourceId;
  }


  /**
   *  Gets the statusId attribute of the QuoteList object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the buildResources attribute of the QuoteList object
   *
   *@return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Gets the ticketId attribute of the QuoteList object
   *
   *@return    The ticketId value
   */
  public int getTicketId() {
    return ticketId;
  }


  /**
   *  Constructor for the QuoteList object
   */
  public QuoteList() { }


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
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
        " FROM quote_entry qe " +
        " WHERE qe.quote_id > -1 ");

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
        pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
        items = prepareFilter(pst);
        //pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      //Determine column to sort by
      pagedListInfo.setDefaultSort("qe.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY qe.org_id");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " qe.*, " +
        " org.name, ct.namelast, ct.namefirst, ct.namemiddle " +
        " FROM quote_entry qe " +
        " LEFT JOIN organization org ON (qe.org_id = org.org_id) " +
        " LEFT JOIN contact ct ON (qe.contact_id = ct.contact_id) " +
        " WHERE qe.quote_id > -1 "
        );

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
      Quote thisQuote = new Quote(rs);
      thisQuote.retrieveTicket(db);
      this.add(thisQuote);
    }
    rs.close();
    pst.close();
    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        Quote thisQuote = (Quote) i.next();
        thisQuote.buildProducts(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (orgId > -1) {
      sqlFilter.append("AND qe.org_id = ? ");
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
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (orgId > -1) {
      pst.setInt(++i, orgId);
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
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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
}

