package org.aspcfs.modules.quotes.base;

import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import java.util.Calendar;

/**
 *  Description of the Class
 *
 *@author     partha
 *@created    May 18, 2004
 *@version    $Id$
 */
public class QuoteNoteList extends ArrayList implements SyncableList {
  //sync api
  /**
   *  Description of the Field
   */
  public final static String tableName = "quote_notes";
  /**
   *  Description of the Field
   */
  public final static String uniqueField = "notes_id";
  private Timestamp lastAnchor = null;
  private Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  //filters
  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int quoteId = -1;
  private String notes = null;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private boolean buildResources = false;


  /**
   *  Sets the pagedListInfo attribute of the QuoteNoteList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the QuoteNoteList object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the QuoteNoteList object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quoteId attribute of the QuoteNoteList object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   *  Sets the quoteId attribute of the QuoteNoteList object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the notes attribute of the QuoteNoteList object
   *
   *@param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the QuoteNoteList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the QuoteNoteList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the QuoteNoteList object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the QuoteNoteList object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildResources attribute of the QuoteNoteList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the buildResources attribute of the QuoteNoteList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the lastAnchor attribute of the QuoteNoteList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the QuoteNoteList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the QuoteNoteList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the QuoteNoteList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the syncType attribute of the QuoteNoteList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the QuoteNoteList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the QuoteNoteList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the QuoteNoteList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the quoteId attribute of the QuoteNoteList object
   *
   *@return    The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   *  Gets the notes attribute of the QuoteNoteList object
   *
   *@return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the enteredBy attribute of the QuoteNoteList object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the QuoteNoteList object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the buildResources attribute of the QuoteNoteList object
   *
   *@return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Gets the lastAnchor attribute of the QuoteNoteList object
   *
   *@return    The lastAnchor value
   */
  public Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the QuoteNoteList object
   *
   *@return    The nextAnchor value
   */
  public Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the QuoteNoteList object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Gets the tableName attribute of the QuoteNoteList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the QuoteNoteList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Constructor for the QuoteNoteList object
   */
  public QuoteNoteList() throws SQLException { }


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
        " FROM quote_notes n " +
        " WHERE n.notes_id > -1 "
        );
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
      pagedListInfo.setDefaultSort("n.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY notes_id");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " n.* " +
        " FROM quote_notes n " +
        " WHERE n.notes_id > -1 "
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
      QuoteNote thisQuoteNote = new QuoteNote(rs);
      this.add(thisQuoteNote);
    }
    rs.close();
    pst.close();
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
    if (id > -1) {
      sqlFilter.append("AND n.notes_id = ? ");
    }
    if (quoteId > -1) {
      sqlFilter.append("AND n.quote_id = ? ");
    }
    if (notes != null) {
      sqlFilter.append("AND n.notes = ? ");
    }
    if (enteredBy > -1) {
      sqlFilter.append("AND n.enteredby = ? ");
    }
    if (modifiedBy > -1) {
      sqlFilter.append("AND n.modifiedby = ? ");
    }
    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND n.entered >= ? ");
      }
      sqlFilter.append(" AND n.entered < ? ");
    } else if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append(" AND n.modified >= ? ");
      sqlFilter.append(" AND n.entered < ? ");
      sqlFilter.append(" AND n.modified < ? ");
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        sqlFilter.append("AND n.entered >= ? ");
      }
      if (nextAnchor != null) {
        sqlFilter.append("AND n.entered < ? ");
      }
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
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (quoteId > -1) {
      pst.setInt(++i, this.getQuoteId());
    }
    if (notes != null) {
      pst.setString(++i, this.getNotes());
    }
    if (enteredBy > -1) {
      pst.setInt(++i, this.getEnteredBy());
    }
    if (modifiedBy > -1) {
      pst.setInt(++i, this.getModifiedBy());
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
    return i;
  }
  
  public String getAllNotes() throws SQLException {
    StringBuffer result = new StringBuffer("");
    Iterator iterator = (Iterator) this.iterator();
    while(iterator.hasNext()){
      QuoteNote note = (QuoteNote) iterator.next();
      result.append("\n"+note.getNotes());
    }
    return result.toString();
  }
  
  public void delete(Connection db) throws SQLException {
    Iterator iterator = (Iterator) this.iterator();
    while(iterator.hasNext()){
      QuoteNote note = (QuoteNote) iterator.next();
      note.delete(db);
    }
  }
}

