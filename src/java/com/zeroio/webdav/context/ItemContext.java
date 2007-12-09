/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.webdav.context;

import com.zeroio.iteam.base.FileFolder;
import com.zeroio.iteam.base.FileFolderList;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import org.apache.naming.resources.Resource;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DateUtils;

import javax.naming.NamingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * An Item Context represents a Module Context which has name-object bindings
 * An Item Context could be any CFS Item such as an account, ticket or any item
 * that could have folders and files associated with it. The object that is
 * bound could either be a Folder context or a File resource
 *
 * @author ananth
 * @version $Id$
 * @created November 3, 2004
 */
public class ItemContext
    extends BaseWebdavContext implements ModuleContext {

  private int linkModuleId = -1;
  private int linkItemId = -1;
  private String path = null;
  private String permission = null;
  private int userId = -1;


  /**
   * Sets the userId attribute of the ItemContext object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the ItemContext object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   * Gets the userId attribute of the ItemContext object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Sets the linkModuleId attribute of the ItemContext object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the ItemContext object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   * Sets the linkItemId attribute of the ItemContext object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Sets the linkItemId attribute of the ItemContext object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   * Sets the path attribute of the ItemContext object
   *
   * @param tmp The new path value
   */
  public void setPath(String tmp) {
    this.path = tmp;
  }


  /**
   * Sets the permission attribute of the ItemContext object
   *
   * @param tmp The new permission value
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }


  /**
   * Gets the linkModuleId attribute of the ItemContext object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Gets the linkItemId attribute of the ItemContext object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Gets the path attribute of the ItemContext object
   *
   * @return The path value
   */
  public String getPath() {
    return path;
  }


  /**
   * Gets the permission attribute of the ItemContext object
   *
   * @return The permission value
   */
  public String getPermission() {
    return permission;
  }


  /**
   * Constructor for the ItemContext object
   */
  public ItemContext() {
  }


  /**
   * Constructor for the ItemContext object
   *
   * @param name     Description of the Parameter
   * @param moduleId Description of the Parameter
   * @param itemId   Description of the Parameter
   */
  public ItemContext(String name, int moduleId, int itemId) {
    this.contextName = name;
    this.linkModuleId = moduleId;
    this.linkItemId = itemId;
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @throws SQLException          Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   */
  public void buildResources(SystemStatus thisSystem, Connection db) throws SQLException, FileNotFoundException {
    bindings.clear();
    if (hasPermission(
        thisSystem, db, this.linkItemId,
        this.userId, this.getPermission() + "-view")) {
      populateBindings(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException          Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   */
  public void populateBindings(Connection db) throws SQLException, FileNotFoundException {
    if (linkModuleId == -1) {
      throw new SQLException("Module ID not specified");
    }
    if (linkItemId == -1) {
      throw new SQLException("Item ID not specified");
    }

    populateFolderBindings(db);
    populateFileBindings(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void populateFolderBindings(Connection db) throws SQLException {
    FileFolderList folders = new FileFolderList();
    folders.setLinkModuleId(linkModuleId);
    folders.setLinkItemId(linkItemId);
    folders.setTopLevelOnly(true);
    folders.buildList(db);
    Iterator i = folders.iterator();
    while (i.hasNext()) {
      FileFolder thisFolder = (FileFolder) i.next();
      thisFolder.buildItemCount(db);
      //TODO: determine if the user has permission to view this Folder
      //      If he has then include it in the binding list
      FolderContext context = new FolderContext();
      context.setContextName(thisFolder.getSubject());
      context.setLinkModuleId(linkModuleId);
      context.setLinkItemId(linkItemId);
      context.setFolderId(thisFolder.getId());
      context.setPath(path);
      context.setUserId(userId);
      context.setPermission(this.permission);
      bindings.put(thisFolder.getSubject(), context);
      // build properties
      buildProperties(
          thisFolder.getSubject(), thisFolder.getEntered(),
          thisFolder.getModified(), new Integer(thisFolder.getItemCount()));
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException          Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   */
  private void populateFileBindings(Connection db) throws SQLException, FileNotFoundException {
    FileItemList files = new FileItemList();
    files.setLinkModuleId(linkModuleId);
    files.setLinkItemId(linkItemId);
    files.setTopLevelOnly(true);
    files.setFileLibraryPath(path);
    files.buildList(db);
    Iterator j = files.iterator();
    while (j.hasNext()) {
      FileItem thisFile = (FileItem) j.next();
      //TODO: determine if the user has permission to view this File Item
      //      If he has then include it in the binding list
      File file = new File(thisFile.getFullFilePath());

      Resource resource = new Resource(new FileInputStream(file));
      resource.setLinkModuleId(linkModuleId);
      resource.setLinkItemId(linkItemId);
      resource.setFileItemId(thisFile.getId());
      resource.setSubject(thisFile.getSubject());
      resource.setName(thisFile.getClientFilename());

      bindings.put(thisFile.getClientFilename(), resource);
      //build properties
      buildProperties(
          thisFile.getClientFilename(), thisFile.getEntered(),
          thisFile.getModified(), new Integer(thisFile.getSize()));
    }
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param folderName Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public boolean createSubcontext(SystemStatus thisSystem, Connection db, String folderName) throws SQLException,
      FileNotFoundException, NamingException {
    if (!hasPermission(
        thisSystem, db, this.linkItemId,
        this.userId, this.getPermission() + "-view")) {
      return false;
    }

    if (!hasPermission(
        thisSystem, db, this.linkItemId,
        this.userId, this.getPermission() + "-add")) {
      return false;
    }
    //check to see if the folderName is safe
    //TODO: create a list of valid names and test for it
    if ("".equals(folderName.trim()) || folderName.indexOf("/") > -1) {
      return false;
    }
    //Create a new Folder
    FileFolder folder = new FileFolder();
    folder.setLinkModuleId(linkModuleId);
    folder.setLinkItemId(linkItemId);
    folder.setSubject(folderName);
    folder.setEnteredBy(userId);
    folder.setModifiedBy(userId);
    folder.insert(db);

    //Add this context to the list of bindings
    FolderContext context = new FolderContext();
    context.setContextName(folderName);
    context.setLinkModuleId(linkModuleId);
    context.setLinkItemId(linkItemId);
    context.setFolderId(folder.getId());
    context.setPath(path);
    context.setUserId(userId);
    context.setPermission(this.permission);

    bindings.put(folderName, context);

    //Query folder record to build its properties
    folder.queryRecord(db, folder.getId());
    //TODO: determine the folders size attribute
    buildProperties(
        folderName, folder.getEntered(), folder.getModified(), new Integer(0));

    return true;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param object Description of the Parameter
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public boolean bind(SystemStatus thisSystem, Connection db, Object object) throws SQLException, NamingException {
    if (!hasPermission(
        thisSystem, db, this.linkItemId,
        this.userId, this.getPermission() + "-view")) {
      return false;
    }
    if (!hasPermission(
        thisSystem, db, this.linkItemId,
        this.userId, this.getPermission() + "-edit")) {
      return false;
    }

    if (object instanceof FolderContext) {
      FolderContext context = (FolderContext) object;
      FileFolder thisFolder = null;

      if (context.getLinkModuleId() != this.getLinkModuleId() ||
          context.getLinkItemId() != this.getLinkItemId()) {
        return false;
      }

      try {
        db.setAutoCommit(false);
        thisFolder = new FileFolder(db, context.getFolderId());
        thisFolder.setLinkModuleId(linkModuleId);
        thisFolder.setLinkItemId(linkItemId);
        thisFolder.setSubject(context.getContextName());
        thisFolder.update(db);
        //set the parent to null so that this folder appears in root of the the folder tree
        thisFolder.updateParentId(db, 0);
        db.commit();
      } catch (SQLException e) {
        db.rollback();
        throw new SQLException(e.getMessage());
      } finally {
        db.setAutoCommit(true);
      }

      //Add it to the current list of bindings
      context.setLinkModuleId(linkModuleId);
      context.setLinkItemId(linkItemId);
      bindings.put(context.getContextName(), context);
      //Add the context properties
      buildProperties(
          context.getContextName(), thisFolder.getEntered(),
          thisFolder.getModified(), new Integer(thisFolder.getItemCount()));
    } else if (object instanceof Resource) {
      Resource resource = (Resource) object;
      FileItem thisFile = null;

      if (resource.getLinkModuleId() != this.getLinkModuleId() ||
          resource.getLinkItemId() != this.getLinkItemId()) {
        return false;
      }

      try {
        db.setAutoCommit(false);
        thisFile = new FileItem(db, resource.getFileItemId());
        thisFile.setLinkModuleId(linkModuleId);
        thisFile.setLinkItemId(linkItemId);
        thisFile.setClientFilename(resource.getSubject());
        thisFile.update(db);
        //set the folder id to null, so that this file appears in the top most level
        thisFile.updateFolderId(db, 0);
        db.commit();
      } catch (SQLException e) {
        db.rollback();
        throw new SQLException(e.getMessage());
      } finally {
        db.setAutoCommit(true);
      }

      //Add it to the current list of bindings
      resource.setLinkModuleId(linkModuleId);
      resource.setLinkItemId(linkItemId);
      bindings.put(thisFile.getSubject(), resource);
      //Add the resource properties
      buildProperties(
          thisFile.getSubject(), thisFile.getEntered(),
          thisFile.getModified(), new Integer(thisFile.getSize()));

    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param object Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public Object copyResource(SystemStatus thisSystem, Connection db, Object object) throws SQLException,
      IOException, NamingException {

    if (!hasPermission(
        thisSystem, db, this.linkItemId,
        this.userId, this.getPermission() + "-view")) {
      return null;
    }
    if (!hasPermission(
        thisSystem, db, this.linkItemId,
        this.userId, this.getPermission() + "-add")) {
      return null;
    }

    if (object instanceof Resource) {
      Resource resource = (Resource) object;
      FileItem thisFile = null;

      if (resource.getFileItemId() != -1) {
        //File already exists
        thisFile = new FileItem(db, resource.getFileItemId());
        thisFile.setDirectory(this.getPath());
        resource.saveAsFile(thisFile.getFullFilePath());

        thisFile.setModifiedBy(userId);
        thisFile.setSubject(resource.getName());
        thisFile.setClientFilename(resource.getName());
        thisFile.setSize(resource.getSize());
        thisFile.update(db);
      } else {
        //save the resource file stream to the fileLibrary
        String filePath = this.getPath() + DateUtils.getDatePath(
            new java.util.Date());
        File f = new File(filePath);
        if (!f.exists()) {
          f.mkdirs();
        }

        String fileName = DateUtils.getFilename();
        //System.out.println("PATH: " + filePath);
        //System.out.println("FILE NAME: " + fileName);

        resource.saveAsFile(filePath + fileName);

        thisFile = new FileItem();
        thisFile.setLinkModuleId(linkModuleId);
        thisFile.setLinkItemId(linkItemId);
        thisFile.setEnteredBy(userId);
        thisFile.setModifiedBy(userId);
        thisFile.setFolderId(0);
        thisFile.setSubject(resource.getName());
        thisFile.setClientFilename(resource.getName());
        thisFile.setFilename(fileName);
        thisFile.setVersion(1.0);
        thisFile.setSize(resource.getSize());
        thisFile.insert(db);
        //query the record
        thisFile = new FileItem(db, thisFile.getId());
      }
      
      //Add it to the current list of bindings
      resource.setLinkModuleId(linkModuleId);
      resource.setLinkItemId(linkItemId);
      bindings.put(resource.getName(), resource);
      //Add the resource properties
      buildProperties(
          thisFile.getSubject(), thisFile.getEntered(),
          thisFile.getModified(), new Integer(thisFile.getSize()));

      return thisFile;
    }
    return null;
  }


  /**
   * Description of the Method
   *
   * @param db          Description of the Parameter
   * @param contextName Description of the Parameter
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public void unbind(SystemStatus thisSystem, Connection db, String contextName) throws SQLException, NamingException {
    if (!hasPermission(
        thisSystem, db, this.linkItemId,
        this.userId, this.getPermission() + "-delete")) {
      return;
    }

    Object object = lookup(contextName);
    if (object == null) {
      throw new NamingException(contextName + " NOT FOUND..");
    }

    if (object instanceof FolderContext) {
      FolderContext context = (FolderContext) object;
      FileFolder thisFolder = new FileFolder();
      thisFolder.setId(context.getFolderId());
      thisFolder.delete(db);
    } else if (object instanceof Resource) {
      Resource resource = (Resource) object;
      FileItem thisFile = new FileItem();
      thisFile.setId(resource.getFileItemId());
      thisFile.setLinkModuleId(resource.getLinkModuleId());
      thisFile.setLinkItemId(resource.getLinkItemId());
      thisFile.delete(db, this.getPath());
    }

    //remove it from the current list of bindings
    bindings.remove(contextName);
    attributes.remove(contextName);
  }
}

