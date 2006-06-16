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
package org.aspcfs.modules.help.base;

import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Represent a list of Business Rules on a page
 *
 * @author akhi_m
 * @version $id:exp$
 * @created July 9, 2003
 */
public class HelpBusinessRuleList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int linkHelpId = -1;
  private boolean completeOnly = false;
  private int enteredBy = -1;


  /**
   * Sets the pagedListInfo attribute of the HelpBusinessRuleList object
   *
   * @param pagedListInfo The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   * Sets the linkHelpId attribute of the HelpBusinessRuleList object
   *
   * @param linkHelpId The new linkHelpId value
   */
  public void setLinkHelpId(int linkHelpId) {
    this.linkHelpId = linkHelpId;
  }


  /**
   * Sets the completeOnly attribute of the HelpBusinessRuleList object
   *
   * @param completeOnly The new completeOnly value
   */
  public void setCompleteOnly(boolean completeOnly) {
    this.completeOnly = completeOnly;
  }


  /**
   * Sets the enteredBy attribute of the HelpBusinessRuleList object
   *
   * @param enteredBy The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   * Gets the enteredBy attribute of the HelpBusinessRuleList object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the pagedListInfo attribute of the HelpBusinessRuleList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the linkHelpId attribute of the HelpBusinessRuleList object
   *
   * @return The linkHelpId value
   */
  public int getLinkHelpId() {
    return linkHelpId;
  }


  /**
   * Gets the completeOnly attribute of the HelpBusinessRuleList object
   *
   * @return The completeOnly value
   */
  public boolean getCompleteOnly() {
    return completeOnly;
  }


  /**
   * Builds the list
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
        "SELECT COUNT(*) AS recordcount " +
        "FROM help_business_rules hf " +
        "WHERE hf.rule_id > -1 ");
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
      pst = db.prepareStatement(
          sqlCount.toString() +
          sqlFilter.toString() +
          "AND hf.description < ? ");
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
    pagedListInfo.setDefaultSort("hf.modified", "DESC");
    pagedListInfo.appendSqlTail(db, sqlOrder);

    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "hf.* " +
        "FROM help_business_rules hf " +
        "WHERE hf.rule_id > -1 ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();

    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      HelpBusinessRule thisFeature = new HelpBusinessRule(rs);
      this.add(thisFeature);
    }
    rs.close();
    pst.close();
  }


  /**
   * Create filters for the list
   *
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (enteredBy != -1) {
      sqlFilter.append("AND hf.enteredby = ? ");
    }

    if (linkHelpId != -1) {
      sqlFilter.append("AND hf.link_help_id = ? ");
    }

    if (completeOnly) {
      sqlFilter.append("AND t.complete = ? ");
    }
  }


  /**
   * Sets the filters
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (enteredBy != -1) {
      pst.setInt(++i, enteredBy);
    }

    if (linkHelpId != -1) {
      pst.setInt(++i, linkHelpId);
    }
    if (completeOnly) {
      pst.setBoolean(++i, true);
    }
    return i;
  }
}


