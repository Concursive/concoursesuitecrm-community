//Copyright 2001 Dark Horse Ventures
//The createFilter method and the prepareFilter method need to have the same
//number of parameters if modified.

package org.aspcfs.modules.accounts.base;

import java.sql.*;
import java.text.*;
import java.util.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.accounts.base.*;
/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    January 13, 2003
 *@version    $Id$
 */
public class RevenueDetailList extends Vector {

  private PagedListInfo pagedListInfo = null;
  private int revenueId = -1;


  /**
   *  Constructor for the RevenueDetailList object
   */
  public RevenueDetailList() { }


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
        "SELECT COUNT(*) AS recordcount " +
        "FROM revenue_detail rd " +
        "LEFT JOIN contact ct_eb ON (rd.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (rd.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN lookup_revenue_types rdt ON (rd.type = rdt.code) " +
        "WHERE rd.id > -1 ");

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

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND rd.description < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      pagedListInfo.setDefaultSort("rd.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY rd.description ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "rd.*, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, rdt.description as typename " +
        "FROM revenue_detail rd " +
        "LEFT JOIN contact ct_eb ON (rd.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (rd.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN lookup_revenuedetail_types rdt ON (rd.type = rdt.code) " +
        "WHERE rd.id > -1 ");

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
      RevenueDetail thisRevenueDetail = new RevenueDetail(rs);
      this.addElement(thisRevenueDetail);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Gets the revenueId attribute of the RevenueDetailList object
   *
   *@return    The revenueId value
   */
  public int getRevenueId() {
    return revenueId;
  }


  /**
   *  Sets the revenueId attribute of the RevenueDetailList object
   *
   *@param  revenueId  The new revenueId value
   */
  public void setRevenueId(int revenueId) {
    this.revenueId = revenueId;
  }


  /**
   *  Sets the revenueId attribute of the RevenueDetailList object
   *
   *@param  revenueId  The new revenueId value
   */
  public void setRevenueId(String revenueId) {
    this.revenueId = Integer.parseInt(revenueId);
  }


  /**
   *  Sets the pagedListInfo attribute of the RevenueDetailList object
   *
   *@param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Gets the pagedListInfo attribute of the RevenueDetailList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (revenueId != -1) {
      sqlFilter.append("AND rd.revenue_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (revenueId != -1) {
      pst.setInt(++i, revenueId);
    }

    return i;
  }
}

