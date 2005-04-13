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

import java.sql.*;
import java.util.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    July 6, 2004
 *@version    $Id: FileFolderHierarchy.java,v 1.3 2004/08/05 20:37:42 mrajkowski
 *      Exp $
 */
public class FileFolderHierarchy {

  private int linkModuleId = -1;
  private int linkItemId = -1;
  private FileFolderList hierarchy = null;


  /**
   *  Sets the linkModuleId attribute of the FileFolderHierarchy object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkItemId attribute of the FileFolderHierarchy object
   *
   *@param  tmp  The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the hierarchy attribute of the FileFolderHierarchy object
   *
   *@param  tmp  The new hierarchy value
   */
  public void setHierarchy(FileFolderList tmp) {
    this.hierarchy = tmp;
  }


  /**
   *  Gets the linkModuleId attribute of the FileFolderHierarchy object
   *
   *@return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Gets the linkItemId attribute of the FileFolderHierarchy object
   *
   *@return    The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   *  Gets the hierarchy attribute of the FileFolderHierarchy object
   *
   *@return    The hierarchy value
   */
  public FileFolderList getHierarchy() {
    return hierarchy;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void build(Connection db) throws SQLException {
    if (linkModuleId == -1 || linkItemId == -1) {
      throw new SQLException("ID not specified");
    }
    hierarchy = new FileFolderList();
    hierarchy.setLinkModuleId(linkModuleId);
    hierarchy.setLinkItemId(linkItemId);
    hierarchy.setTopLevelOnly(true);
    hierarchy.buildList(db);
    buildItems(db, hierarchy, 1);
    FileFolderList buffer = hierarchy.buildCompleteHierarchy();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  parentId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void build(Connection db, int parentId) throws SQLException {
    if (linkModuleId == -1 || linkItemId == -1) {
      throw new SQLException("ID not specified");
    }
    hierarchy = new FileFolderList();
    hierarchy.setLinkModuleId(linkModuleId);
    hierarchy.setLinkItemId(linkItemId);
    hierarchy.setParentId(parentId);
    hierarchy.buildList(db);
    buildItems(db, hierarchy, 1);
    FileFolderList buffer = hierarchy.buildCompleteHierarchy();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  baseFilePath      Description of the Parameter
   *@param  parentId          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean deleteFolderHierarchy(Connection db, String baseFilePath, int parentId) throws SQLException {

    //Deleting files in the folder
    ResultSet rs = null;
    PreparedStatement pst = db.prepareStatement(
        "SELECT * FROM project_files " +
        "WHERE folder_id = ? ");
    int i = 0;
    pst.setInt(++i, parentId);
    rs = pst.executeQuery();

    FileItemList fileItemList = new FileItemList(); 
    while (rs.next()) {
      FileItem tmpFileItem = new FileItem();
      tmpFileItem.setId(rs.getInt("item_id"));
      tmpFileItem.setLinkModuleId(rs.getInt("link_module_id"));
      tmpFileItem.setLinkItemId(rs.getInt("link_item_id"));
      fileItemList.add(tmpFileItem);
    }
    rs.close();
    pst.close();
    
    Iterator itr = fileItemList.iterator();
    while (itr.hasNext()){
      FileItem tmpFileItem = (FileItem)itr.next();
      tmpFileItem.delete(db, baseFilePath);
    }

    rs = null;
    //fetching sub folders of a folder
    pst = db.prepareStatement(
        "SELECT * " +
        "FROM project_folders pf " +
        "WHERE pf.link_module_id = ? " +
        "AND pf.link_item_id = ? " +
        "AND pf.parent_id = ? ");
    i = 0;
    pst.setInt(++i, linkModuleId);
    pst.setInt(++i, linkItemId);
    pst.setInt(++i, parentId);
    rs = pst.executeQuery();

    ArrayList list = new ArrayList();
    while (rs.next()) {
      int folderId = rs.getInt("folder_id");
      list.add(new Integer(folderId));
    }
    rs.close();
    pst.close();
    
    itr = list.iterator();
    while (itr.hasNext()){
      int folderId = ((Integer)itr.next()).intValue();
      this.deleteFolderHierarchy(db, baseFilePath, folderId);
      FileFolder fileFolder = new FileFolder();
      fileFolder.setId(parentId);
      fileFolder.deleteBlankFolder(db);
    }
    
    FileFolder fileFolder = new FileFolder();
    fileFolder.setId(parentId);
    fileFolder.deleteBlankFolder(db);


    //db.commit();
    //db.setAutoCommit(true);
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  folderList        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildItems(Connection db, FileFolderList folderList) throws SQLException {
    Iterator i = folderList.iterator();
    while (i.hasNext()) {
      FileFolder folder = (FileFolder) i.next();
      FileFolderList folders = new FileFolderList();
      folders.setParentId(folder.getId());
      folders.buildList(db);
      folder.setSubFolders(folders);
      buildItems(db, folders);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  folderList        Description of the Parameter
   *@param  level             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildItems(Connection db, FileFolderList folderList, int level) throws SQLException {
    Iterator i = (Iterator) folderList.iterator();
    while (i.hasNext()) {
      FileFolder folder = (FileFolder) i.next();
      folder.setLevel(level);
      FileFolderList folders = new FileFolderList();
      folders.setParentId(folder.getId());
      folders.buildList(db);
      folder.setSubFolders(folders);
      buildItems(db, folders, level + 1);
    }
  }
}

