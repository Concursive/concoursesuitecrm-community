//Copyright 2002 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.utils.DatabaseUtils;

public class CustomFieldDataList extends ArrayList {
  
  private PagedListInfo pagedListInfo = null;

  //Properties for building the list
  private int recordId = -1;

  public CustomFieldDataList() { }


  /**
   *  Sets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  public void setRecordId(int tmp) { this.recordId = tmp; }

  /**
   *  Gets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   *@return    The PagedListInfo value
   *@since
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }

  public int getRecordId() { return recordId; }

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
      pst.close();
      rs.close();

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
        "* " +
        "FROM custom_field_data cfd " +
        "WHERE cfd.record_id > -1 ");
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
      CustomFieldData thisField = new CustomFieldData(rs);
      this.add(thisField);
    }
    rs.close();
    pst.close();
  }


  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    
    if (recordId > -1) {
      sqlFilter.append("AND cfd.record_id = ? ");
    }
  }


  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (recordId > -1) {
      pst.setInt(++i, recordId);
    }
    return i;
  }
}

