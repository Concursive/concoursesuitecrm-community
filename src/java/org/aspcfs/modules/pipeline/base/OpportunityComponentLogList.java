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
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *  Description of the Class
 *
 *@author     Srini
 *@version
 *@created    February 1, 2006
 */
public class OpportunityComponentLogList extends ArrayList {

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
   *@return    Returns the tableName.
   */
  public static String getTableName() {
    return tableName;
  }


  /**
   *@return    Returns the uniqueField.
   */
  public static String getUniqueField() {
    return uniqueField;
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
   *@param  lastAnchor  The lastAnchor to set.
   */
  public void setLastAnchor(java.sql.Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }


  /**
   *@return    Returns the nextAnchor.
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *@param  nextAnchor  The nextAnchor to set.
   */
  public void setNextAnchor(java.sql.Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
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
   *@param  syncType  The syncType to set.
   */
  public void setSyncType(int syncType) {
    this.syncType = syncType;
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

    StringBuffer sqlSelect = new StringBuffer();
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

    // Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "ocl.*, y.description AS stagename " +
        "FROM opportunity_component_log ocl " +
        "LEFT JOIN lookup_stage y ON (ocl.stage = y.code) " +
        "WHERE ocl.id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString()
         + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      OpportunityComponentLog thisOppComponentLog = new OpportunityComponentLog(
          rs);
      this.add(thisOppComponentLog);
    }
    rs.close();
    pst.close();
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
    return i;
  }

}

