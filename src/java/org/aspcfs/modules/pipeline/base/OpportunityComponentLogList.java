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
package org.aspcfs.modules.pipeline.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
/**
 *  Description of the Class
 *
 *@author     Srini
 *@version
 *@created    February 1, 2006
 */
public class OpportunityComponentLogList extends ArrayList implements SyncableList {

  public final static String tableName = "opportunity_component_log";
  public final static String uniqueField = "id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  protected PagedListInfo pagedListInfo = null;
  protected int headerId = -1;
  protected int componentId = -1;


  /**
   *  Constructor for the OpportunityComponentLogList object
   */
  public OpportunityComponentLogList() { }


  /**
   *  Sets the pagedListInfo attribute of the OpportunityComponentLogList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public OpportunityComponentLogList(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static OpportunityComponentLog getObject(ResultSet rs) throws SQLException {
    OpportunityComponentLog opportunityComponentLog = new OpportunityComponentLog(rs);
    return opportunityComponentLog;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getTableName()
   */
  public String getTableName() {
    return tableName;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getUniqueField()
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.sql.Timestamp)
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.lang.String)
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = java.sql.Timestamp.valueOf(lastAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.sql.Timestamp)
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.lang.String)
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = java.sql.Timestamp.valueOf(nextAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(int)
   */
  public void setSyncType(int syncType) {
    this.syncType = syncType;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }
  
  /**
   *@return    Returns the componentId.
   */
  public int getComponentId() {
    return componentId;
  }


  /**
   *@param  componentId  The componentId to set.
   */
  public void setComponentId(int componentId) {
    this.componentId = componentId;
  }


  /**
   *@return    Returns the headerId.
   */
  public int getHeaderId() {
    return headerId;
  }


  /**
   *@param  headerId  The headerId to set.
   */
  public void setHeaderId(int headerId) {
    this.headerId = headerId;
  }


  /**
   *@return    Returns the lastAnchor.
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }

  /**
   *@return    Returns the nextAnchor.
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }

  /**
   *@return    Returns the pagedListInfo.
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *@param  pagedListInfo  The pagedListInfo to set.
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *@return    Returns the syncType.
   */
  public int getSyncType() {
    return syncType;
  }

  /**
   * Description of the Method
   *
   * @param db
   * @param pst
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    return queryList(db, pst, "", "");
  }
  
  /**
   * Description of the Method
   *
   * @param db
   * @param pst
   * @param sqlFilter
   * @param sqlOrder
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public ResultSet queryList(Connection db, PreparedStatement pst, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    // Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "ocl.*, y.description AS stagename " +
        "FROM " + tableName + " ocl " +
        "LEFT JOIN lookup_stage y ON (ocl.stage = y.code) " +
        "WHERE ocl.id > -1 ");
    if(sqlFilter == null || sqlFilter.length() == 0){
      StringBuffer buff = new StringBuffer();
      createFilter(db, buff);
      sqlFilter = buff.toString();
    }
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter + sqlOrder);
    prepareFilter(pst);
    return DatabaseUtils.executeQuery(db, pst, pagedListInfo);
  }

  /**
   *  Builds a list of component log based on several parameters. The parameters
   *  are set after this object is constructed, then the buildList method is
   *  called to generate the list.
   *
   *@param  db             Description of Parameter
   *@throws  SQLException  Description of Exception
   *@since                 1.1
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    // Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM opportunity_component_log ocl " +
        "LEFT JOIN lookup_stage y ON (ocl.stage = y.code) " +
        "WHERE ocl.id > -1 ");

    createFilter(db, sqlFilter);

    if (pagedListInfo != null) {
      // Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString()
           + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      // Determine the offset, based on the filter, for the first record
      // to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString()
             + sqlFilter.toString() + "AND "
             + DatabaseUtils.toLowerCase(db)
             + "(ocl.description) < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter()
            .toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      // pagedListInfo.setDefaultSort("oc.description", null);
      pagedListInfo.setDefaultSort("ocl.entered", "asc");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY ocl.entered");
    }

    rs = queryList(db, pst, sqlFilter.toString(), sqlOrder.toString());
    while (rs.next()) {
      OpportunityComponentLog thisOppComponentLog = new OpportunityComponentLog(
          rs);
      this.add(thisOppComponentLog);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db         Description of the Parameter
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (headerId != -1) {
      sqlFilter.append("AND ocl.header_id = ? ");
    }
    if (componentId != -1) {
      sqlFilter.append("AND ocl.component_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND ocl.entered > ? ");
      }
      sqlFilter.append("AND ocl.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND ocl.modified > ? ");
      sqlFilter.append("AND ocl.entered < ? ");
      sqlFilter.append("AND ocl.modified < ? ");
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
    if (headerId != -1) {
      pst.setInt(++i, headerId);
    }
    if (componentId != -1) {
      pst.setInt(++i, componentId);
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

}

