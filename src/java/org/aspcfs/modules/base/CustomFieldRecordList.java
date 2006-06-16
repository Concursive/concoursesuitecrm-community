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

import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class CustomFieldRecordList extends ArrayList {

  public static final int TRUE = 1;
  public static final int FALSE = 0;

  private PagedListInfo pagedListInfo = null;

  //Properties for building the list
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int categoryId = -1;
  private int includeEnabled = -1;

  public CustomFieldRecordList() {
  }


  /**
   * Sets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   * @param tmp The new PagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }

  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }

  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }

  /**
   * Gets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   * @return The PagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }

  public int getLinkModuleId() {
    return linkModuleId;
  }

  public int getLinkItemId() {
    return linkItemId;
  }

  public int getCategoryId() {
    return categoryId;
  }

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
        "FROM custom_field_record cfr " +
        "WHERE cfr.link_module_id > -1 ");

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
    
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "cfr.* " +
        "FROM custom_field_record cfr " +
        "WHERE cfr.link_module_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();

    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }

    while (rs.next()) {
      CustomFieldRecord thisRecord = new CustomFieldRecord(rs);
      this.add(thisRecord);
    }
    rs.close();
    pst.close();
  }


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
  }


  private int prepareFilter(PreparedStatement pst) throws SQLException {
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

    return i;
  }

  public void delete(Connection db) throws SQLException {
    Iterator customRecords = this.iterator();
    while (customRecords.hasNext()) {
      CustomFieldRecord thisRecord = (CustomFieldRecord) customRecords.next();
      thisRecord.delete(db);
    }
  }

  public void buildRecordColumns(Connection db, CustomFieldCategory thisCategory) throws SQLException {
    Iterator customRecords = this.iterator();
    while (customRecords.hasNext()) {
      CustomFieldRecord thisRecord = (CustomFieldRecord) customRecords.next();
      thisRecord.buildColumns(db, thisCategory);
    }
  }

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

