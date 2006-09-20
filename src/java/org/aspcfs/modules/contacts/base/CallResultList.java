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
package org.aspcfs.modules.contacts.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a call result
 *
 * @author matt rajkowski
 * @version $Id: CallResultList.java,v 1.2 2004/08/04 20:01:56 mrajkowski Exp
 *          $
 * @created September 22, 2003
 */
public class CallResultList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private boolean enabledOnly = false;
  private boolean canceledOnly = false;


  /**
   * Sets the pagedListInfo attribute of the CallResultList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the enabledOnly attribute of the CallResultList object
   *
   * @param tmp The new enabledOnly value
   */
  public void setEnabledOnly(boolean tmp) {
    this.enabledOnly = tmp;
  }


  /**
   * Sets the enabledOnly attribute of the CallResultList object
   *
   * @param tmp The new enabledOnly value
   */
  public void setEnabledOnly(String tmp) {
    this.enabledOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the canceledOnly attribute of the CallResultList object
   *
   * @param canceledOnly The new canceledOnly value
   */
  public void setCanceledOnly(boolean canceledOnly) {
    this.canceledOnly = canceledOnly;
  }


  /**
   * Gets the canceledOnly attribute of the CallResultList object
   *
   * @return The canceledOnly value
   */
  public boolean getCanceledOnly() {
    return canceledOnly;
  }


  /**
   * Gets the pagedListInfo attribute of the CallResultList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the enabledOnly attribute of the CallResultList object
   *
   * @return The enabledOnly value
   */
  public boolean getEnabledOnly() {
    return enabledOnly;
  }


  /**
   * Constructor for the CallResultList object
   */
  public CallResultList() {
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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
      pagedListInfo.setDefaultSort("r." + DatabaseUtils.addQuotes(db, "level") + ", r.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY r." + DatabaseUtils.addQuotes(db, "level") + ", r.description ");
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
      CallResult thisResult = new CallResult(rs);
      this.add(thisResult);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
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
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
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
   * Gets the lookupList attribute of the CallResultList object
   *
   * @param defaultKey Description of the Parameter
   * @return The lookupList value
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
        select.appendItem(
            thisResult.getId(), thisResult.getDescription() + " (X)");
      }
    }
    return select;
  }


  /**
   * Gets the canceledLookupList attribute of the CallResultList object
   *
   * @param defaultKey Description of the Parameter
   * @return The canceledLookupList value
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
          select.appendItem(
              thisResult.getId(), thisResult.getDescription() + " (X)");
        }
      }
    }
    return select;
  }


  /**
   * Gets the completedLookupList attribute of the CallResultList object
   *
   * @param defaultKey Description of the Parameter
   * @return The completedLookupList value
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
          select.appendItem(
              thisResult.getId(), thisResult.getDescription() + " (X)");
        }
      }
    }
    return select;
  }


  /**
   * Gets the idFromValue attribute of the CallResultList object
   *
   * @param value Description of the Parameter
   * @return The idFromValue value
   */
  public int getIdFromValue(String value) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CallResult thisResult = (CallResult) i.next();
      if (value.equals(thisResult.getDescription())) {
        return thisResult.getId();
      }
    }
    return -1;
  }
}


