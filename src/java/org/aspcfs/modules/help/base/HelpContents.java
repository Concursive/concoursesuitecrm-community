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

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id$
 */
public class HelpContents extends ArrayList {
  private boolean buildFeatures = false;
  private String module = null;
  private String section = null;
  private String subSection = null;
  private PagedListInfo pagedListInfo = null;


  /**
   *  Constructor for the HelpContents object
   */
  public HelpContents() { }


  /**
   *  Sets the module attribute of the HelpContents object
   *
   *@param  module  The new module value
   */
  public void setModule(String module) {
    this.module = module;
  }


  /**
   *  Sets the section attribute of the HelpContents object
   *
   *@param  section  The new section value
   */
  public void setSection(String section) {
    this.section = section;
  }


  /**
   *  Sets the pagedListInfo attribute of the HelpContents object
   *
   *@param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Sets the subSection attribute of the HelpContents object
   *
   *@param  subSection  The new subSection value
   */
  public void setSubSection(String subSection) {
    this.subSection = subSection;
  }


  /**
   *  Gets the subSection attribute of the HelpContents object
   *
   *@return    The subSection value
   */
  public String getSubSection() {
    return subSection;
  }


  /**
   *  Gets the pagedListInfo attribute of the HelpContents object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the module attribute of the HelpContents object
   *
   *@return    The module value
   */
  public String getModule() {
    return module;
  }


  /**
   *  Gets the section attribute of the HelpContents object
   *
   *@return    The section value
   */
  public String getSection() {
    return section;
  }


  /**
   *  Sets the buildFeatures attribute of the HelpContents object
   *
   *@param  buildFeatures  The new buildFeatures value
   */
  public void setBuildFeatures(boolean buildFeatures) {
    this.buildFeatures = buildFeatures;
  }


  /**
   *  Gets the buildFeatures attribute of the HelpContents object
   *
   *@return    The buildFeatures value
   */
  public boolean getBuildFeatures() {
    return buildFeatures;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void build(Connection db) throws SQLException {
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
        "FROM help_contents hc " +
        "WHERE hc.help_id > -1 ");
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
    rs.close();
    pst.close();

    //Determine the offset, based on the filter, for the first record to show
    if (!pagedListInfo.getCurrentLetter().equals("")) {
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString() +
          "AND hc.description < ? ");
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
    pagedListInfo.setDefaultSort("hc.module", "");
    pagedListInfo.appendSqlTail(db, sqlOrder);

    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "* " +
        "FROM help_contents hc " +
        "WHERE hc.help_id > -1 ");

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
      HelpItem thisItem = new HelpItem(rs);
      this.add(thisItem);
    }
    rs.close();
    pst.close();
    //build details
    Iterator i = this.iterator();
    while (i.hasNext()) {
      HelpItem thisItem = (HelpItem) i.next();
      if (buildFeatures) {
        thisItem.buildFeatures(db);
      }
    }
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

    if (module != null) {
      sqlFilter.append("AND hc.module = ? ");
    }

    if (section != null) {
      sqlFilter.append("AND hc.section = ? ");
    }

    if (subSection != null) {
      sqlFilter.append("AND hc.subSection = ? ");
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

    if (module != null) {
      pst.setString(++i, module);
    }

    if (section != null) {
      pst.setString(++i, section);
    }

    if (subSection != null) {
      pst.setString(++i, subSection);
    }
    return i;
  }

}

