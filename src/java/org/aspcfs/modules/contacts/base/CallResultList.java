//Copyright 2001-2003 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package org.aspcfs.modules.contacts.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.LookupList;

/**
 *  Represents a call result
 *
 *@author     matt rajkowski
 *@created    September 22, 2003
 *@version    $Id$
 */
public class CallResultList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private boolean enabledOnly = false;
  private boolean canceledOnly = false;


  /**
   *  Sets the pagedListInfo attribute of the CallResultList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the enabledOnly attribute of the CallResultList object
   *
   *@param  tmp  The new enabledOnly value
   */
  public void setEnabledOnly(boolean tmp) {
    this.enabledOnly = tmp;
  }


  /**
   *  Sets the enabledOnly attribute of the CallResultList object
   *
   *@param  tmp  The new enabledOnly value
   */
  public void setEnabledOnly(String tmp) {
    this.enabledOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the canceledOnly attribute of the CallResultList object
   *
   *@param  canceledOnly  The new canceledOnly value
   */
  public void setCanceledOnly(boolean canceledOnly) {
    this.canceledOnly = canceledOnly;
  }


  /**
   *  Gets the canceledOnly attribute of the CallResultList object
   *
   *@return    The canceledOnly value
   */
  public boolean getCanceledOnly() {
    return canceledOnly;
  }


  /**
   *  Gets the pagedListInfo attribute of the CallResultList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the enabledOnly attribute of the CallResultList object
   *
   *@return    The enabledOnly value
   */
  public boolean getEnabledOnly() {
    return enabledOnly;
  }


  /**
   *  Constructor for the CallResultList object
   */
  public CallResultList() { }


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
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) as recordcount " +
        "FROM lookup_call_result r " +
        "WHERE result_id > -1 ");
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
      pagedListInfo.setDefaultSort("r.level, r.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY r.level, r.description ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "r.* " +
        "FROM lookup_call_result r " +
        "WHERE result_id > -1 ");
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
      CallResult thisResult = new CallResult(rs);
      this.add(thisResult);
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
    if (enabledOnly) {
      sqlFilter.append("AND r.enabled = ? ");
    }

    if (canceledOnly) {
      sqlFilter.append("AND r.canceled_type = ? ");
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
    if (enabledOnly) {
      pst.setBoolean(++i, true);
    }
    if (canceledOnly) {
      pst.setBoolean(++i, true);
    }
    return i;
  }


  /**
   *  Gets the lookupList attribute of the CallResultList object
   *
   *@param  defaultKey  Description of the Parameter
   *@return             The lookupList value
   */
  public LookupList getLookupList(int defaultKey) {
    LookupList select = new LookupList();
    //select.setTableName("lookup_call_result");
    //select.setSelectSize(1);
    //select.setMultiple(false);
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CallResult thisResult = (CallResult) i.next();
      if (thisResult.getEnabled()) {
        select.appendItem(thisResult.getId(), thisResult.getDescription());
      } else if (thisResult.getId() == defaultKey) {
        select.appendItem(thisResult.getId(), thisResult.getDescription() + " (X)");
      }
    }
    return select;
  }


  /**
   *  Gets the canceledLookupList attribute of the CallResultList object
   *
   *@param  defaultKey  Description of the Parameter
   *@return             The canceledLookupList value
   */
  public LookupList getCanceledLookupList(int defaultKey) {
    LookupList select = new LookupList();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CallResult thisResult = (CallResult) i.next();
      if (thisResult.getCanceledType()) {
        if (thisResult.getEnabled()) {
          select.appendItem(thisResult.getId(), thisResult.getDescription());
        } else if (thisResult.getId() == defaultKey) {
          select.appendItem(thisResult.getId(), thisResult.getDescription() + " (X)");
        }
      }
    }
    return select;
  }


  /**
   *  Gets the completedLookupList attribute of the CallResultList object
   *
   *@param  defaultKey  Description of the Parameter
   *@return             The completedLookupList value
   */
  public LookupList getCompletedLookupList(int defaultKey) {
    LookupList select = new LookupList();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CallResult thisResult = (CallResult) i.next();
      if (!thisResult.getCanceledType()) {
        if (thisResult.getEnabled()) {
          select.appendItem(thisResult.getId(), thisResult.getDescription());
        } else if (thisResult.getId() == defaultKey) {
          select.appendItem(thisResult.getId(), thisResult.getDescription() + " (X)");
        }
      }
    }
    return select;
  }
}


