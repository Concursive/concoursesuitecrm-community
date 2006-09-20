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

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A list of files stored in the filesystem and referenced in a database
 *
 * @author matt rajkowski
 * @version $Id: FileItemList.java,v 1.2.66.1 2004/03/19 21:00:50 rvasista Exp
 *          $
 * @created February 8, 2002
 */
public class FileItemList extends ArrayList {
  //filters
  private PagedListInfo pagedListInfo = null;
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int folderId = -1;
  private int owner = -1;
  private String ownerIdRange = null;
  private String fileLibraryPath = null;
  private boolean topLevelOnly = false;
  private boolean webImageFormatOnly = false;
  private int defaultFile = Constants.UNDEFINED;
  private int enabled = Constants.UNDEFINED;
  //calendar
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;
  //custom for projects, otherwise need to extend class
  private int forProjectUser = -1;
  //html select
  private String htmlJsEvent = "";
  private int buildPortalRecords = Constants.UNDEFINED;


  /**
   * Gets the enabled attribute of the FileItemList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Sets the enabled attribute of the FileItemList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the FileItemList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Gets the htmlJsEvent attribute of the FileItemList object
   *
   * @return The htmlJsEvent value
   */
  public String getHtmlJsEvent() {
    return htmlJsEvent;
  }


  /**
   * Sets the htmlJsEvent attribute of the FileItemList object
   *
   * @param tmp The new htmlJsEvent value
   */
  public void setHtmlJsEvent(String tmp) {
    this.htmlJsEvent = tmp;
  }


  /**
   * Constructor for the FileItemList object
   */
  public FileItemList() {
  }


  /**
   * Gets the defaultFile attribute of the FileItemList object
   *
   * @return The defaultFile value
   */
  public int getDefaultFile() {
    return defaultFile;
  }


  /**
   * Sets the defaultFile attribute of the FileItemList object
   *
   * @param tmp The new defaultFile value
   */
  public void setDefaultFile(int tmp) {
    this.defaultFile = tmp;
  }


  /**
   * Sets the defaultFile attribute of the FileItemList object
   *
   * @param tmp The new defaultFile value
   */
  public void setDefaultFile(String tmp) {
    this.defaultFile = Integer.parseInt(tmp);
  }


  /**
   * Sets the linkModuleId attribute of the FileItemList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Gets the linkModuleId attribute of the FileItemList object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Sets the linkItemId attribute of the FileItemList object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Gets the linkItemId attribute of the FileItemList object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Sets the pagedListInfo attribute of the FileItemList object
   *
   * @param pagedListInfo The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   * Sets the owner attribute of the FileItemList object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the ownerIdRange attribute of the FileItemList object
   *
   * @param tmp The new ownerIdRange value
   */
  public void setOwnerIdRange(String tmp) {
    this.ownerIdRange = tmp;
  }


  /**
   * Sets the folderId attribute of the FileItemList object
   *
   * @param tmp The new folderId value
   */
  public void setFolderId(int tmp) {
    this.folderId = tmp;
  }


  /**
   * Sets the fileLibraryPath attribute of the FileItemList object
   *
   * @param tmp The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   * Sets the topLevelOnly attribute of the FileItemList object
   *
   * @param tmp The new topLevelOnly value
   */
  public void setTopLevelOnly(boolean tmp) {
    this.topLevelOnly = tmp;
  }


  /**
   * Gets the webImageFormatOnly attribute of the FileItemList object
   *
   * @return The webImageFormatOnly value
   */
  public boolean getWebImageFormatOnly() {
    return webImageFormatOnly;
  }


  /**
   * Sets the webImageFormatOnly attribute of the FileItemList object
   *
   * @param tmp The new webImageFormatOnly value
   */
  public void setWebImageFormatOnly(boolean tmp) {
    this.webImageFormatOnly = tmp;
  }


  /**
   * Sets the webImageFormatOnly attribute of the FileItemList object
   *
   * @param tmp The new webImageFormatOnly value
   */
  public void setWebImageFormatOnly(String tmp) {
    this.webImageFormatOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the alertRangeStart attribute of the FileItemList object
   *
   * @param tmp The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp tmp) {
    this.alertRangeStart = tmp;
  }


  /**
   * Sets the alertRangeStart attribute of the FileItemList object
   *
   * @param tmp The new alertRangeStart value
   */
  public void setAlertRangeStart(String tmp) {
    this.alertRangeStart = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the alertRangeEnd attribute of the FileItemList object
   *
   * @param tmp The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp tmp) {
    this.alertRangeEnd = tmp;
  }


  /**
   * Sets the alertRangeEnd attribute of the FileItemList object
   *
   * @param tmp The new alertRangeEnd value
   */
  public void setAlertRangeEnd(String tmp) {
    this.alertRangeEnd = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the forProjectUser attribute of the FileItemList object
   *
   * @param tmp The new forProjectUser value
   */
  public void setForProjectUser(int tmp) {
    this.forProjectUser = tmp;
  }


  /**
   * Sets the forProjectUser attribute of the FileItemList object
   *
   * @param tmp The new forProjectUser value
   */
  public void setForProjectUser(String tmp) {
    this.forProjectUser = Integer.parseInt(tmp);
  }


  /**
   * Gets the pagedListInfo attribute of the FileItemList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the owner attribute of the FileItemList object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Gets the ownerIdRange attribute of the FileItemList object
   *
   * @return The ownerIdRange value
   */
  public String getOwnerIdRange() {
    return ownerIdRange;
  }


  /**
   * Gets the folderId attribute of the FileItemList object
   *
   * @return The folderId value
   */
  public int getFolderId() {
    return folderId;
  }


  /**
   * Gets the fileLibraryPath attribute of the FileItemList object
   *
   * @return The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }


  /**
   * Gets the fileSize of all of the FileItem objects within this collection
   *
   * @return The fileSize value
   */
  public long getFileSize() {
    long fileSize = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      fileSize += ((FileItem) i.next()).getSize();
    }
    return fileSize;
  }


	/**
   * Gets the alertRangeStart attribute of the FileItemList object
   *
   * @return The alertRangeStart value
   */
  public java.sql.Timestamp getAlertRangeStart() {
    return alertRangeStart;
  }


  /**
   * Gets the alertRangeEnd attribute of the FileItemList object
   *
   * @return The alertRangeEnd value
   */
  public java.sql.Timestamp getAlertRangeEnd() {
    return alertRangeEnd;
  }


  /**
   * Gets the forProjectUser attribute of the FileItemList object
   *
   * @return The forProjectUser value
   */
  public int getForProjectUser() {
    return forProjectUser;
  }


  /**
	 * @return Returns the buildPortalRecords.
	 */
	public int getBuildPortalRecords() {
		return buildPortalRecords;
	}


	/**
	 * @param buildPortalRecords The buildPortalRecords to set.
	 */
	public void setBuildPortalRecords(int buildPortalRecords) {
		this.buildPortalRecords = buildPortalRecords;
	}


	/**
   * Generates a list of matching FileItems
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
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
        "FROM project_files f " +
        "WHERE f.item_id > -1 ");
    createFilter(db, sqlFilter);
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
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
            sqlFilter.toString() +
            "AND " + DatabaseUtils.toLowerCase(db) + "(f.subject) < ? ");
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
      pagedListInfo.setDefaultSort("f.subject, f.client_filename", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY f.subject ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "f.*, t.filename AS thumbnail " +
        "FROM project_files f " +
        "LEFT JOIN project_files_thumbnail t ON (f.item_id = t.item_id AND f." + DatabaseUtils.addQuotes(db, "version")+ " = t." + DatabaseUtils.addQuotes(db, "version")+ ") " +
        "WHERE f.item_id > -1 ");
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
      FileItem thisItem = new FileItem(rs);
      thisItem.setDirectory(fileLibraryPath);
      this.add(thisItem);
    }
    rs.close();
    pst.close();
  }


  /**
   * Deletes all files in this list
   *
   * @param db           Description of Parameter
   * @param baseFilePath Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void delete(Connection db, String baseFilePath) throws SQLException {
    Iterator documents = this.iterator();
    while (documents.hasNext()) {
      FileItem thisFile = (FileItem) documents.next();
      thisFile.delete(db, baseFilePath);
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of Parameter
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (linkModuleId > -1) {
      sqlFilter.append("AND f.link_module_id = ? ");
    }
    if (linkItemId > -1) {
      sqlFilter.append("AND f.link_item_id = ? ");
    }
    if (folderId > -1) {
      sqlFilter.append("AND f.folder_id = ? ");
    }
    if (owner != -1) {
      sqlFilter.append("AND f.enteredby = ? ");
    }
    if (ownerIdRange != null) {
      sqlFilter.append("AND f.enteredby IN (" + ownerIdRange + ") ");
    }
    if (topLevelOnly) {
      sqlFilter.append("AND f.folder_id IS NULL ");
    }
    if (buildPortalRecords != Constants.UNDEFINED) {
      sqlFilter.append("AND f.allow_portal_access = ? ");
    }
    if (webImageFormatOnly) {
      sqlFilter.append(
          "AND (" + DatabaseUtils.toLowerCase(db) + "(f.client_filename) LIKE '%.gif' OR " + DatabaseUtils.toLowerCase(
              db) + "(f.client_filename) LIKE '%.jpg' OR " + DatabaseUtils.toLowerCase(
                  db) + "(f.client_filename) LIKE '%.png') ");
    }
    if (alertRangeStart != null) {
      sqlFilter.append("AND f.modified >= ? ");
    }
    if (alertRangeEnd != null) {
      sqlFilter.append("AND f.modified < ? ");
    }
    if (forProjectUser > -1) {
      sqlFilter.append(
          "AND (f.link_item_id in (SELECT DISTINCT project_id FROM project_team WHERE user_id = ? " +
          "AND status IS NULL) OR f.link_item_id IN (SELECT project_id FROM projects WHERE allow_guests = ? AND approvaldate IS NOT NULL)) ");
    }
    if (defaultFile != Constants.UNDEFINED) {
      sqlFilter.append("AND f.default_file = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND f.enabled = ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (linkModuleId > -1) {
      pst.setInt(++i, linkModuleId);
    }
    if (linkItemId > -1) {
      pst.setInt(++i, linkItemId);
    }
    if (folderId > -1) {
      pst.setInt(++i, folderId);
    }
    if (owner != -1) {
      pst.setInt(++i, owner);
    }
    if (alertRangeStart != null) {
      pst.setTimestamp(++i, alertRangeStart);
    }
    if (alertRangeEnd != null) {
      pst.setTimestamp(++i, alertRangeEnd);
    }
    if (forProjectUser > -1) {
      pst.setInt(++i, forProjectUser);
      pst.setBoolean(++i, true);
    }
    if (buildPortalRecords != Constants.UNDEFINED) {
      pst.setBoolean(++i, buildPortalRecords == Constants.TRUE);
    }
    if (defaultFile != Constants.UNDEFINED) {
      pst.setBoolean(++i, defaultFile == Constants.TRUE);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, enabled == Constants.TRUE);
    }
    return i;
  }


  /**
   * Returns the number of fileItems that match the module and itemid
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
        "FROM project_files pf " +
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
   * Checks to see if any of the files has the specified extension
   *
   * @param extension Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasFileType(String extension) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      FileItem thisItem = (FileItem) i.next();
      if (extension.equalsIgnoreCase(thisItem.getExtension())) {
        return true;
      }
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public long queryFileSize(Connection db) throws SQLException {
    long recordSize = 0;
    StringBuffer sqlFilter = new StringBuffer();
    String sqlCount =
        "SELECT SUM(" + DatabaseUtils.addQuotes(db, "size")+ ") AS recordsize " +
        "FROM project_files f " +
        "WHERE f.item_id > -1 ";
    createFilter(db, sqlFilter);
    PreparedStatement pst = db.prepareStatement(
        sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordSize = DatabaseUtils.getLong(rs, "recordsize", 0);
    }
    rs.close();
    pst.close();
    return recordSize;
  }


  /**
   * Gets the htmlSelectDefaultNone attribute of the FileItemList object
   *
   * @param selectName Description of the Parameter
   * @param currentKey Description of the Parameter
   * @return The htmlSelectDefaultNone value
   */
  public String getHtmlSelectDefaultNone(SystemStatus thisSystem, String selectName, int currentKey, boolean useDefault) {
    HtmlSelect fileSelect = new HtmlSelect();
    fileSelect.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
    Iterator i = this.iterator();
    int defaultKey = -1;
    while (i.hasNext()) {
      FileItem thisItem = (FileItem) i.next();
      if (thisItem.getEnabled()) {
        // Only add the item if enabled
        if (useDefault && thisItem.getDefaultFile()) {
          defaultKey = thisItem.getId();
        }
        fileSelect.addItem(
            thisItem.getId(), StringUtils.toHtml(thisItem.getSubject()));
      } else {
        // Allow disabled items that match the current key
        if (thisItem.getId() == currentKey) {
          fileSelect.addItem(
              thisItem.getId(), StringUtils.toHtml(thisItem.getSubject()) + " (X)");
        }
      }
    }
    if (!(this.getHtmlJsEvent().equals(""))) {
      fileSelect.setJsEvent(this.getHtmlJsEvent());
    }
    if (currentKey > 0) {
      return fileSelect.getHtml(selectName, currentKey);
    } else {
      return fileSelect.getHtml(selectName, defaultKey);
    }
  }
}

