//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;

public class CustomFieldRecordList extends Vector {
  
  public static final int TRUE = 1;
  public static final int FALSE = 0;
  
  private PagedListInfo pagedListInfo = null;

  //Properties for building the list
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int categoryId = -1;
  private int includeEnabled = -1;

  public CustomFieldRecordList() { }


  /**
   *  Sets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  public void setLinkModuleId(int tmp) { this.linkModuleId = tmp; }
  public void setLinkItemId(int tmp) { this.linkItemId = tmp; }
  public void setCategoryId(int tmp) { this.categoryId = tmp; }

  /**
   *  Gets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   *@return    The PagedListInfo value
   *@since
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }

  public int getLinkModuleId() { return linkModuleId; }
  public int getLinkItemId() { return linkItemId; }
  public int getCategoryId() { return categoryId; }

  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT * " +
        "FROM custom_field_record cfr " +
        "WHERE cfr.link_module_id = " + linkModuleId + " " +
        "AND cfr.link_item_id = " + linkItemId + " ");
        
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) as recordcount " +
        "FROM custom_field_record cfr " +
        "WHERE cfr.module_id = " + linkModuleId + " " +
        "AND cfr.item_id = " + linkItemId + " ");

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
      pst.close();
      rs.close();

      //Determine column to sort by
      if (pagedListInfo.getColumnToSortBy() == null || pagedListInfo.getColumnToSortBy().equals("")) {
        pagedListInfo.setColumnToSortBy("entered");
        pagedListInfo.setSortOrder("desc");
      }
      sqlOrder.append("ORDER BY " + pagedListInfo.getColumnToSortBy() + " ");
      if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals("")) {
        sqlOrder.append(pagedListInfo.getSortOrder() + " ");
      }

      //Determine items per page
      if (pagedListInfo.getItemsPerPage() > 0) {
        sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
      }

      sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
    } else {
      sqlOrder.append("ORDER BY entered DESC ");
    }

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      CustomFieldRecord thisRecord = new CustomFieldRecord(rs);
      this.addElement(thisRecord);
    }
    rs.close();
    pst.close();
  }


  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (includeEnabled == TRUE) {
      sqlFilter.append("AND cfr.enabled = true ");
    } else if (includeEnabled == FALSE) {
      sqlFilter.append("AND cfr.enabled = false ");
    }
    
    if (categoryId > -1) {
      sqlFilter.append("AND cfr.category_id = ? ");
    }
  }


  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    
    return i;
  }
  
  public void delete(Connection db) throws SQLException {
    Iterator customRecords = this.iterator();
    while (customRecords.hasNext()) {
      CustomFieldRecord thisRecord = (CustomFieldRecord)customRecords.next();
      thisRecord.delete(db);
    }
  }
}

