/*
 *  Copyright 2000-2003 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;

/**
 *  A list of files stored in the filesystem and referenced in a database
 *
 *@author     matt rajkowski
 *@created    February 8, 2002
 *@version    $Id$
 */
public class FileItemList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int folderId = -1;
  private int owner = -1;
  private String ownerIdRange = null;
  private String fileLibraryPath = null;


  /**
   *  Constructor for the FileItemList object
   */
  public FileItemList() { }


  /**
   *  Sets the linkModuleId attribute of the FileItemList object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkItemId attribute of the FileItemList object
   *
   *@param  tmp  The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the pagedListInfo attribute of the FileItemList object
   *
   *@param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Sets the owner attribute of the FileItemList object
   *
   *@param  tmp  The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   *  Sets the ownerIdRange attribute of the FileItemList object
   *
   *@param  tmp  The new ownerIdRange value
   */
  public void setOwnerIdRange(String tmp) {
    this.ownerIdRange = tmp;
  }


  /**
   *  Sets the folderId attribute of the FileItemList object
   *
   *@param  tmp  The new folderId value
   */
  public void setFolderId(int tmp) {
    this.folderId = tmp;
  }


  /**
   *  Sets the fileLibraryPath attribute of the FileItemList object
   *
   *@param  tmp  The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   *  Gets the pagedListInfo attribute of the FileItemList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the owner attribute of the FileItemList object
   *
   *@return    The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Gets the ownerIdRange attribute of the FileItemList object
   *
   *@return    The ownerIdRange value
   */
  public String getOwnerIdRange() {
    return ownerIdRange;
  }


  /**
   *  Gets the folderId attribute of the FileItemList object
   *
   *@return    The folderId value
   */
  public int getFolderId() {
    return folderId;
  }


  /**
   *  Gets the fileLibraryPath attribute of the FileItemList object
   *
   *@return    The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }


  /**
   *  Gets the fileSize of all of the FileItem objects within this collection
   *
   *@return    The fileSize value
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
   *  Generates a list of matching FileItems
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
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
            "AND subject < ? ");
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
      pagedListInfo.setDefaultSort("subject, client_filename", null);
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
        "f.* " +
        "FROM project_files f " +
        "WHERE f.item_id > -1 ");
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
      FileItem thisItem = new FileItem(rs);
      thisItem.setDirectory(fileLibraryPath);
      this.add(thisItem);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Deletes all files in this list
   *
   *@param  db                Description of Parameter
   *@param  baseFilePath      Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void delete(Connection db, String baseFilePath) throws SQLException {
    Iterator documents = this.iterator();
    while (documents.hasNext()) {
      FileItem thisFile = (FileItem) documents.next();
      thisFile.delete(db, baseFilePath);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of Parameter
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

    if (folderId > -1) {
      sqlFilter.append("AND folder_id = ? ");
    }

    if (owner != -1) {
      sqlFilter.append("AND enteredby = ? ");
    }

    if (ownerIdRange != null) {
      sqlFilter.append("AND enteredby IN (" + ownerIdRange + ") ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
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

    return i;
  }


  /**
   *  Returns the number of fileItems that match the module and itemid
   *
   *@param  db                Description of the Parameter
   *@param  linkModuleId      Description of the Parameter
   *@param  linkItemId        Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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
   *  Checks to see if any of the files has the specified extension
   *
   *@param  extension  Description of the Parameter
   *@return            Description of the Return Value
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int queryFileSize(Connection db) throws SQLException {
    int recordSize = 0;
    StringBuffer sqlFilter = new StringBuffer();
    String sqlCount =
        "SELECT SUM(size) AS recordsize " +
        "FROM project_files f " +
        "WHERE f.item_id > -1 ";
    createFilter(sqlFilter);
    PreparedStatement pst = db.prepareStatement(sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordSize = DatabaseUtils.getInt(rs, "recordsize", 0);
    }
    pst.close();
    rs.close();
    return recordSize;
  }
}

