/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

/**
 *  Description of the Class
 *
 * @author     matt rajkowski
 * @created    April 10, 2003
 * @version    $Id: FileFolderList.java,v 1.1.2.1 2004/03/19 21:00:50 rvasista
 *      Exp $
 */
public class FileFolderList extends ArrayList {

  private int parentId = -1;
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private PagedListInfo pagedListInfo = null;
  private boolean topLevelOnly = false;
  private boolean buildItemCount = false;


  /**
   *  Constructor for the FileFolderList object
   */
  public FileFolderList() { }


  /**
   *  Sets the parentId attribute of the FileFolderList object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the FileFolderList object
   *
   * @param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkItemId attribute of the FileFolderList object
   *
   * @param  tmp  The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the pagedListInfo attribute of the FileFolderList object
   *
   * @param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Sets the topLevelOnly attribute of the FileFolderList object
   *
   * @param  tmp  The new topLevelOnly value
   */
  public void setTopLevelOnly(boolean tmp) {
    this.topLevelOnly = tmp;
  }


  /**
   *  Sets the buildItemCount attribute of the FileFolderList object
   *
   * @param  tmp  The new buildItemCount value
   */
  public void setBuildItemCount(boolean tmp) {
    this.buildItemCount = tmp;
  }


  /**
   *  Gets the parentId attribute of the FileFolderList object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the pagedListInfo attribute of the FileFolderList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
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
        "FROM project_folders f " +
        "WHERE f.link_module_id > -1 ");
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
      pagedListInfo.setDefaultSort("subject", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY subject ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "* " +
        "FROM project_folders f " +
        "WHERE f.link_module_id > -1 ");
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
      FileFolder thisFolder = new FileFolder(rs);
      this.add(thisFolder);
    }
    rs.close();
    pst.close();
    //Build any extra data
    if (buildItemCount) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        FileFolder thisFolder = (FileFolder) i.next();
        thisFolder.buildItemCount(db);
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
      sqlFilter.append("AND link_module_id = ? ");
    }
    if (linkItemId > -1) {
      sqlFilter.append("AND link_item_id = ? ");
    }
    if (parentId > -1) {
      sqlFilter.append("AND parent_id = ? ");
    }
    if (topLevelOnly) {
      sqlFilter.append("AND parent_id IS NULL ");
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
    if (linkItemId > -1) {
      pst.setInt(++i, linkItemId);
    }
    if (parentId > -1) {
      pst.setInt(++i, parentId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  linkModuleId      Description of the Parameter
   * @param  linkItemId        Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int linkModuleId, int linkItemId) throws SQLException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT COUNT(*) as filecount " +
        "FROM project_folders pf " +
        "WHERE pf.link_module_id = ? and pf.link_item_id = ? ");
    pst.setInt(1, linkModuleId);
    pst.setInt(2, linkItemId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("filecount");
    }
    rs.close();
    pst.close();
    return count;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator folders = this.iterator();
    while (folders.hasNext()) {
      FileFolder thisFolder = (FileFolder) folders.next();
      thisFolder.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public FileFolderList buildCompleteHierarchy() throws SQLException {
    for (int i = 0; i < this.size(); i++) {
      FileFolder thisFolder = (FileFolder) this.get(i);
      if (thisFolder.getSubFolders().size() > 0) {
        this.addAll(i + 1, thisFolder.getSubFolders());
      }
    }
    return this;
  }


  /**
   *  Description of the Method
   *
   * @param  folderId          Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean hasFolder(int folderId) throws SQLException {
    for (int i=0; i < this.size();i++) {
      FileFolder thisFolder = (FileFolder) this.get(i);
      if (folderId == thisFolder.getId()) {
        return true;
      }
    }
    return false;
  }
}

