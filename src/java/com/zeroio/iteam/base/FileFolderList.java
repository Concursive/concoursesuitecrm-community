/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: FileFolderList.java,v 1.1.2.1 2004/03/19 21:00:50 rvasista
 *          Exp $
 * @created April 10, 2003
 */
public class FileFolderList extends ArrayList implements SyncableList {
  public final static String tableName = "project_folders";
  public final static String uniqueField = "folder_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  private int parentId = -1;
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private PagedListInfo pagedListInfo = null;
  private boolean topLevelOnly = false;
  private boolean buildItemCount = false;


  /**
   * Constructor for the FileFolderList object
   */
  public FileFolderList() {
  }

  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static FileFolder getObject(ResultSet rs) throws SQLException {
    FileFolder fileFolder = new FileFolder(rs);
    return fileFolder;
  }

  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionItemList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }
  
  /**
   * Gets the tableName attribute of the ActionItemList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionItemList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Sets the parentId attribute of the FileFolderList object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the FileFolderList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkItemId attribute of the FileFolderList object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Sets the pagedListInfo attribute of the FileFolderList object
   *
   * @param pagedListInfo The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   * Sets the topLevelOnly attribute of the FileFolderList object
   *
   * @param tmp The new topLevelOnly value
   */
  public void setTopLevelOnly(boolean tmp) {
    this.topLevelOnly = tmp;
  }


  /**
   * Sets the buildItemCount attribute of the FileFolderList object
   *
   * @param tmp The new buildItemCount value
   */
  public void setBuildItemCount(boolean tmp) {
    this.buildItemCount = tmp;
  }


  /**
   * Gets the parentId attribute of the FileFolderList object
   *
   * @return The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   * Gets the pagedListInfo attribute of the FileFolderList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }

  /**
   * Description of the Method
   *
   * @param db
   * @param pst
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    return queryList(db, pst, "", "");
  }
  
  /**
   * Description of the Method
   *
   * @param db
   * @param pst
   * @param sqlFilter
   * @param sqlOrder
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public ResultSet queryList(Connection db, PreparedStatement pst, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "f.* " +
            "FROM " + tableName + " f " +
            "WHERE f.link_module_id > -1 ");
    if(sqlFilter == null || sqlFilter.length() == 0){
      StringBuffer buff = new StringBuffer();
      createFilter(buff);
      sqlFilter = buff.toString();
    }
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter + sqlOrder);
    prepareFilter(pst);

    return DatabaseUtils.executeQuery(db, pst, pagedListInfo);
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

    rs = queryList(db, pst, sqlFilter.toString(), sqlOrder.toString());
    while (rs.next()) {
      FileFolder thisFolder = new FileFolder(rs);
      this.add(thisFolder);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
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
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
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
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND f.entered > ? ");
      }
      sqlFilter.append("AND f.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND f.modified > ? ");
      sqlFilter.append("AND f.entered < ? ");
      sqlFilter.append("AND f.modified < ? ");
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
    if (linkModuleId > -1) {
      pst.setInt(++i, linkModuleId);
    }
    if (linkItemId > -1) {
      pst.setInt(++i, linkItemId);
    }
    if (parentId > -1) {
      pst.setInt(++i, parentId);
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param linkModuleId Description of the Parameter
   * @param linkItemId   Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator folders = this.iterator();
    while (folders.hasNext()) {
      FileFolder thisFolder = (FileFolder) folders.next();
      thisFolder.delete(db);
    }
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
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
   * Description of the Method
   *
   * @param folderId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean hasFolder(int folderId) throws SQLException {
    for (int i = 0; i < this.size(); i++) {
      FileFolder thisFolder = (FileFolder) this.get(i);
      if (folderId == thisFolder.getId()) {
        return true;
      }
    }
    return false;
  }
}

