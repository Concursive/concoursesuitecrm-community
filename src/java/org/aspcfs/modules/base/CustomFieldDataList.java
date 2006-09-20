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

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    April 3, 2006
 */
public class CustomFieldDataList extends ArrayList {

  private PagedListInfo pagedListInfo = null;

  //Properties for building the list
  private int fieldId = -1;
  private int recordId = -1;
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int categoryId = -1;


  /**
   *  Gets the fieldId attribute of the CustomFieldDataList object
   *
   * @return    The fieldId value
   */
  public int getFieldId() {
    return fieldId;
  }


  /**
   *  Sets the fieldId attribute of the CustomFieldDataList object
   *
   * @param  tmp  The new fieldId value
   */
  public void setFieldId(int tmp) {
    this.fieldId = tmp;
  }


  /**
   *  Sets the fieldId attribute of the CustomFieldDataList object
   *
   * @param  tmp  The new fieldId value
   */
  public void setFieldId(String tmp) {
    this.fieldId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the linkModuleId attribute of the CustomFieldDataList object
   *
   * @return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Sets the linkModuleId attribute of the CustomFieldDataList object
   *
   * @param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the CustomFieldDataList object
   *
   * @param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the linkItemId attribute of the CustomFieldDataList object
   *
   * @return    The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   *  Sets the linkItemId attribute of the CustomFieldDataList object
   *
   * @param  tmp  The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the linkItemId attribute of the CustomFieldDataList object
   *
   * @param  tmp  The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the categoryId attribute of the CustomFieldDataList object
   *
   * @return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Sets the categoryId attribute of the CustomFieldDataList object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the CustomFieldDataList object
   *
   * @param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }



  /**
   *  Constructor for the CustomFieldDataList object
   */
  public CustomFieldDataList() { }


  /**
   *  Sets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   * @param  tmp  The new PagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the recordId attribute of the CustomFieldDataList object
   *
   * @param  tmp  The new recordId value
   */
  public void setRecordId(int tmp) {
    this.recordId = tmp;
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
   *  Gets the recordId attribute of the CustomFieldDataList object
   *
   * @return    The recordId value
   */
  public int getRecordId() {
    return recordId;
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

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) as recordcount " +
        "FROM custom_field_data cfd " +
        "WHERE cfd.record_id > -1 ");

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
      pagedListInfo.setDefaultSort("record_id", "field_id");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY record_id, field_id ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "cfd.* " +
        "FROM custom_field_data cfd " +
        "WHERE cfd.record_id > -1 ");
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
    while (rs.next()) {
      CustomFieldData thisField = new CustomFieldData(rs);
      this.add(thisField);
    }
    rs.close();
    pst.close();
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

    if (fieldId > -1) {
      sqlFilter.append("AND cfd.field_id = ? ");
    }

    if (recordId > -1) {
      sqlFilter.append("AND cfd.record_id = ? ");
    }

    if (linkModuleId > -1 && linkItemId > -1 && categoryId > -1) {
      sqlFilter.append("AND cfd.record_id IN (SELECT record_id FROM custom_field_record WHERE " +
          "link_module_id = ? AND link_item_id = ? AND category_id = ? ) ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (fieldId > -1) {
      pst.setInt(++i, fieldId);
    }
    if (recordId > -1) {
      pst.setInt(++i, recordId);
    }
    if (linkModuleId > -1 && linkItemId > -1 && categoryId > -1) {
      pst.setInt(++i, linkModuleId);
      pst.setInt(++i, linkItemId);
      pst.setInt(++i, categoryId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
}

