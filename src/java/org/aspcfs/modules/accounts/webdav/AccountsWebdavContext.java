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
package org.aspcfs.modules.accounts.webdav;

import com.zeroio.webdav.context.BaseWebdavContext;
import com.zeroio.webdav.context.ItemContext;
import com.zeroio.webdav.context.ModuleContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;

import java.sql.*;

/**
 *  AccountsWebdavContext represents a Module Context which has name-object
 *  bindings. A bound object represents an Organization.
 *
 *@author     ananth
 *@created    November 3, 2004
 *@version    $Id$
 */
public class AccountsWebdavContext
     extends BaseWebdavContext implements ModuleContext {

  private final static String ACCOUNTS = "accounts";
  private int linkModuleId = Constants.DOCUMENTS_ACCOUNTS;
  private String contextName = null;
  private String fileLibraryPath = null;
  private String permission = "accounts-view";
  //Permissions for this context
  private int userId = -1;


  /**
   *  Sets the userId attribute of the AccountsWebdavContext object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   *  Sets the userId attribute of the AccountsWebdavContext object
   *
   *@param  tmp  The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the userId attribute of the AccountsWebdavContext object
   *
   *@return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Sets the linkModuleId attribute of the AccountsWebdavContext object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the AccountsWebdavContext object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contextName attribute of the AccountsWebdavContext object
   *
   *@param  tmp  The new contextName value
   */
  public void setContextName(String tmp) {
    this.contextName = tmp;
  }


  /**
   *  Sets the fileLibraryPath attribute of the AccountsWebdavContext object
   *
   *@param  tmp  The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   *  Sets the permission attribute of the AccountsWebdavContext object
   *
   *@param  tmp  The new permission value
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }


  /**
   *  Gets the linkModuleId attribute of the AccountsWebdavContext object
   *
   *@return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Gets the contextName attribute of the AccountsWebdavContext object
   *
   *@return    The contextName value
   */
  public String getContextName() {
    return contextName;
  }


  /**
   *  Gets the fileLibraryPath attribute of the AccountsWebdavContext object
   *
   *@return    The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }


  /**
   *  Gets the permission attribute of the AccountsWebdavContext object
   *
   *@return    The permission value
   */
  public String getPermission() {
    return permission;
  }


  /**
   *  Constructor for the AccountsWebdavContext object
   */
  public AccountsWebdavContext() { }


  /**
   *  Constructor for the AccountsWebdavContext object
   *
   *@param  name          Description of the Parameter
   *@param  linkModuleId  Description of the Parameter
   */
  public AccountsWebdavContext(String name, int linkModuleId) {
    this.contextName = name;
    this.linkModuleId = linkModuleId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  userId            Description of the Parameter
   *@param  fileLibraryPath   Description of the Parameter
   *@param  thisSystem        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildResources(SystemStatus thisSystem, Connection db, int userId, String fileLibraryPath) throws SQLException {
    this.fileLibraryPath = fileLibraryPath;
    this.userId = userId;
    bindings.clear();
    if (hasPermission(thisSystem, userId, "accounts-view")) {
      populateBindings(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void populateBindings(Connection db) throws SQLException {
    if (linkModuleId == -1) {
      throw new SQLException("Module ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
      "SELECT org_id, name, entered, modified " +
      "FROM organization " +
      "WHERE org_id > 0");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ItemContext item = new ItemContext();
      item.setContextName(rs.getString("name"));
      item.setLinkModuleId(linkModuleId);
      item.setLinkItemId(rs.getInt("org_id"));
      item.setPath(fileLibraryPath + ACCOUNTS + fs);
      item.setUserId(userId);
      item.setPermission("accounts-accounts-documents-view");
      bindings.put(item.getContextName(), item);
      Timestamp entered = rs.getTimestamp("entered");
      Timestamp modified = rs.getTimestamp("modified");
      buildProperties(item.getContextName(), entered, modified, new Integer(0));
    }
  }
}

