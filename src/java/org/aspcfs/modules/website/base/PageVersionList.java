/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author     kailash
 *@created    February 10, 2006 $Id: Exp $
 *@version    $Id: Exp $
 */
public class PageVersionList extends ArrayList {

  private PagedListInfo pagedListInfo = null;

  private int id = -1;
  private int versionNumber = -1;
  private int parentPageVersionId = -1;
  private int pageId = -1;


  /**
   *  Constructor for the PageVersionList object
   */
  public PageVersionList() { }


  /**
   *  Sets the pagedListInfo attribute of the PageVersionList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the PageVersionList object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PageVersionList object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the versionNumber attribute of the PageVersionList object
   *
   *@param  tmp  The new versionNumber value
   */
  public void setVersionNumber(int tmp) {
    this.versionNumber = tmp;
  }


  /**
   *  Sets the versionNumber attribute of the PageVersionList object
   *
   *@param  tmp  The new versionNumber value
   */
  public void setVersionNumber(String tmp) {
    this.versionNumber = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentPageVersionId attribute of the PageVersionList object
   *
   *@param  tmp  The new parentPageVersionId value
   */
  public void setParentPageVersionId(int tmp) {
    this.parentPageVersionId = tmp;
  }


  /**
   *  Sets the parentPageVersionId attribute of the PageVersionList object
   *
   *@param  tmp  The new parentPageVersionId value
   */
  public void setParentPageVersionId(String tmp) {
    this.parentPageVersionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pageId attribute of the PageVersionList object
   *
   *@param  tmp  The new pageId value
   */
  public void setPageId(int tmp) {
    this.pageId = tmp;
  }


  /**
   *  Sets the pageId attribute of the PageVersionList object
   *
   *@param  tmp  The new pageId value
   */
  public void setPageId(String tmp) {
    this.pageId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the PageVersionList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the PageVersionList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the versionNumber attribute of the PageVersionList object
   *
   *@return    The versionNumber value
   */
  public int getVersionNumber() {
    return versionNumber;
  }


  /**
   *  Gets the parentPageVersionId attribute of the PageVersionList object
   *
   *@return    The parentPageVersionId value
   */
  public int getParentPageVersionId() {
    return parentPageVersionId;
  }


  /**
   *  Gets the pageId attribute of the PageVersionList object
   *
   *@return    The pageId value
   */
  public int getPageId() {
    return pageId;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      PageVersion thisPageVersion = this.getObject(rs);
      this.add(thisPageVersion);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@param  pst            Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {

    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM page_version " +
        "WHERE page_version_id > -1 ");

    createFilter(sqlFilter, db);

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
      pagedListInfo.setDefaultSort("version_number", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY version_number ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " * " +
        "FROM page_version " +
        "WHERE page_version_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }

    return rs;
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter      Description of the Parameter
   *@param  db             Description of the Parameter
   *@throws  SQLException  Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
    if (id != -1) {
      sqlFilter.append("AND page_version_id = ? ");
    }
    if (versionNumber != -1) {
      sqlFilter.append("AND version_number = ? ");
    }
    if (parentPageVersionId != -1) {
      sqlFilter.append("AND parent_page_version_id = ? ");
    }
    if (pageId != -1) {
      sqlFilter.append("AND page_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst            Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id != -1) {
      pst.setInt(++i, id);
    }
    if (versionNumber != -1) {
      pst.setInt(++i, versionNumber);
    }
    if (parentPageVersionId != -1) {
      pst.setInt(++i, parentPageVersionId);
    }
    if (pageId != -1) {
      pst.setInt(++i, pageId);
    }
    return i;
  }


  /**
   *  Gets the object attribute of the PageVersionList object
   *
   *@param  rs                Description of the Parameter
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public PageVersion getObject(ResultSet rs) throws SQLException {
    return new PageVersion(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator pageVersionIterator = this.iterator();
    while (pageVersionIterator.hasNext()) {
      PageVersion thisPageVersion = (PageVersion) pageVersionIterator.next();
      thisPageVersion.delete(db);
    }
  }
  
  public void removeParentId(Connection db) throws SQLException {
    Iterator pageVersionIterator = this.iterator();
    while (pageVersionIterator.hasNext()) {
      PageVersion thisPageVersion = (PageVersion) pageVersionIterator.next();
      thisPageVersion.setParentPageVersionId(-1);
      thisPageVersion.update(db);
    }
  }

}

