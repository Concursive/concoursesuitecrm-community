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
package org.aspcfs.modules.actionlist.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;

/**
 *  List of Action Lists
 *
 * @author     akhi_m
 * @created    April 18, 2003
 */
public class ActionLists extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int linkModuleId = -1;
  private int owner = -1;
  private boolean buildDetails = false;
  private boolean completeOnly = false;
  private boolean inProgressOnly = false;


  /**
   *  Sets the linkModuleId attribute of the ActionLists object
   *
   * @param  linkModuleId  The new linkModuleId value
   */
  public void setLinkModuleId(int linkModuleId) {
    this.linkModuleId = linkModuleId;
  }


  /**
   *  Sets the owner attribute of the ActionLists object
   *
   * @param  owner  The new owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the pagedListInfo attribute of the ActionLists object
   *
   * @param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Sets the buildDetails attribute of the ActionLists object
   *
   * @param  buildDetails  The new buildDetails value
   */
  public void setBuildDetails(boolean buildDetails) {
    this.buildDetails = buildDetails;
  }


  /**
   *  Sets the completeOnly attribute of the ActionLists object
   *
   * @param  completeOnly  The new completeOnly value
   */
  public void setCompleteOnly(boolean completeOnly) {
    this.completeOnly = completeOnly;
  }


  /**
   *  Sets the inProgressOnly attribute of the ActionLists object
   *
   * @param  inProgressOnly  The new inProgressOnly value
   */
  public void setInProgressOnly(boolean inProgressOnly) {
    this.inProgressOnly = inProgressOnly;
  }


  /**
   *  Gets the completeOnly attribute of the ActionLists object
   *
   * @return    The completeOnly value
   */
  public boolean getCompleteOnly() {
    return completeOnly;
  }


  /**
   *  Gets the inProgressOnly attribute of the ActionLists object
   *
   * @return    The inProgressOnly value
   */
  public boolean getInProgressOnly() {
    return inProgressOnly;
  }


  /**
   *  Gets the buildDetails attribute of the ActionLists object
   *
   * @return    The buildDetails value
   */
  public boolean getBuildDetails() {
    return buildDetails;
  }


  /**
   *  Gets the pagedListInfo attribute of the ActionLists object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the linkModuleId attribute of the ActionLists object
   *
   * @return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Gets the owner attribute of the ActionLists object
   *
   * @return    The owner value
   */
  public int getOwner() {
    return owner;
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
        "SELECT COUNT(*) AS recordcount " +
        "FROM action_list al " +
        "WHERE al.action_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo == null) {
      pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(0);
    }

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
          "AND lower(al.description) < ? ");
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
    pagedListInfo.setDefaultSort("al.modified", "DESC");
    pagedListInfo.appendSqlTail(db, sqlOrder);

    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "* " +
        "FROM action_list al " +
        "WHERE al.action_id > -1 ");

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
      ActionList thisList = new ActionList(rs);
      this.add(thisList);
    }
    rs.close();
    pst.close();
    if (buildDetails) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        ActionList thisList = (ActionList) i.next();
        thisList.buildTotal(db);
        thisList.buildTotalComplete(db);
      }
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
      sqlFilter.append("AND (al.link_module_id = ?) ");
    }
    if (owner > -1) {
      sqlFilter.append("AND (al.owner = ?) ");
    }
    if (completeOnly) {
      sqlFilter.append("AND (al.completedate IS NOT NULL) ");
    } else if (inProgressOnly) {
      sqlFilter.append("AND (al.completedate IS NULL) ");
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
    if (linkModuleId > -1) {
      pst.setInt(++i, linkModuleId);
    }
    if (owner > -1) {
      pst.setInt(++i, owner);
    }
    return i;
  }
}


