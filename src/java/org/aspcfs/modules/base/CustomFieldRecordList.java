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
package org.aspcfs.modules.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    February 9, 2007
 */
public class CustomFieldRecordList extends ArrayList implements SyncableList {

  public final static String tableName = "custom_field_record";
  public final static String uniqueField = "record_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  public final static int TRUE = 1;
  public final static int FALSE = 0;

  private PagedListInfo pagedListInfo = null;

  //Properties for building the list
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int categoryId = -1;
  private int includeEnabled = -1;


  /**
   *  Constructor for the CustomFieldRecordList object
   */
  public CustomFieldRecordList() { }


  /**
   *  Description of the Method
   *
   * @param  rs
   * @return
   * @throws  SQLException  Description of the Returned Value
   */
  public static CustomFieldRecord getObject(ResultSet rs) throws SQLException {
    CustomFieldRecord customFieldRecord = new CustomFieldRecord(rs);
    return customFieldRecord;
  }


  /*
   *  Gets the tableName attribute of the CustomFieldRecordList object
   *
   * @return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /*
  *  Gets the uniqueField attribute of the CustomFieldRecordList object
   *
   * @return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /*
   *  Sets the lastAnchor attribute of the CustomFieldRecordList object
   *
   * @param  lastAnchor  The new lastAnchor value
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }


  /*
   *  Sets the lastAnchor attribute of the CustomFieldRecordList object
   *
   * @param  lastAnchor  The new lastAnchor value
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = java.sql.Timestamp.valueOf(lastAnchor);
  }


  /*
   *  Sets the nextAnchor attribute of the CustomFieldRecordList object
   *
   * @param  nextAnchor  The new nextAnchor value
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }


  /*
   *  Sets the nextAnchor attribute of the CustomFieldRecordList object
   *
   * @param  nextAnchor  The new nextAnchor value
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = java.sql.Timestamp.valueOf(nextAnchor);
  }


  /*
   *  Sets the syncType attribute of the CustomFieldRecordList object
   *
   * @param  syncType  The new syncType value
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
   *  Sets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   * @param  tmp  The new PagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the CustomFieldRecordList object
   *
   * @param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkItemId attribute of the CustomFieldRecordList object
   *
   * @param  tmp  The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the CustomFieldRecordList object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }

  /**
   *  Gets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   * @return    The PagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the linkModuleId attribute of the CustomFieldRecordList object
   *
   * @return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Gets the linkItemId attribute of the CustomFieldRecordList object
   *
   * @return    The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   *  Gets the categoryId attribute of the CustomFieldRecordList object
   *
   * @return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }

  /**
   *  Description of the Method
   *
   * @param  db
   * @param  pst
   * @return
   * @throws  SQLException  Description of the Returned Value
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    return queryList(db, pst, "", "");
  }

  /**
   *  Description of the Method
   *
   * @param  db
   * @param  pst
   * @param  sqlFilter
   * @param  sqlOrder
   * @return
   * @throws  SQLException  Description of the Returned Value
   */
  public ResultSet queryList(Connection db, PreparedStatement pst, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "cfr.* " +
        "FROM " + tableName + " cfr " +
        "WHERE cfr.record_id > -1 ");
    if (sqlFilter == null || sqlFilter.length() == 0) {
      StringBuffer buff = new StringBuffer();
      createFilter(buff);
      sqlFilter = buff.toString();
    }
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter + sqlOrder);
    prepareFilter(pst);

    return DatabaseUtils.executeQuery(db, pst, pagedListInfo);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) as recordcount " +
        "FROM custom_field_record cfr " +
        "WHERE cfr.record_id > -1 ");

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
      pagedListInfo.setDefaultSort("entered", "desc");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY entered DESC ");
    }

    rs = queryList(db, pst, sqlFilter.toString(), sqlOrder.toString());
    while (rs.next()) {
      CustomFieldRecord thisRecord = new CustomFieldRecord(rs);
      this.add(thisRecord);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (linkModuleId > -1) {
      sqlFilter.append("AND cfr.link_module_id = ? ");
    }
    if (linkItemId > -1) {
      sqlFilter.append("AND cfr.link_item_id = ? ");
    }
    if (includeEnabled == TRUE || includeEnabled == FALSE) {
      sqlFilter.append("AND cfr.enabled = ? ");
    }
    if (categoryId > -1) {
      sqlFilter.append("AND cfr.category_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND cfr.entered > ? ");
      }
      sqlFilter.append("AND cfr.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND cfr.modified > ? ");
      sqlFilter.append("AND cfr.entered < ? ");
      sqlFilter.append("AND cfr.modified < ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (linkModuleId > -1) {
      pst.setInt(++i, linkModuleId);
    }
    if (linkItemId > -1) {
      pst.setInt(++i, linkItemId);
    }
    if (includeEnabled == TRUE) {
      pst.setBoolean(++i, true);
    } else if (includeEnabled == FALSE) {
      pst.setBoolean(++i, false);
    }
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
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
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator customRecords = this.iterator();
    while (customRecords.hasNext()) {
      CustomFieldRecord thisRecord = (CustomFieldRecord) customRecords.next();
      thisRecord.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  thisCategory      Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRecordColumns(Connection db, CustomFieldCategory thisCategory) throws SQLException {
    Iterator customRecords = this.iterator();
    while (customRecords.hasNext()) {
      CustomFieldRecord thisRecord = (CustomFieldRecord) customRecords.next();
      thisRecord.buildColumns(db, thisCategory);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  linkModuleId      Description of the Parameter
   * @param  linkItemId        Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int linkModuleId, int linkItemId) throws SQLException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(*) as foldercount " +
        "FROM custom_field_record cfr " +
        "WHERE cfr.link_module_id = ? and cfr.link_item_id = ?");
    pst.setInt(1, linkModuleId);
    pst.setInt(2, linkItemId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("foldercount");
    }
    rs.close();
    pst.close();
    return count;
  }
}

