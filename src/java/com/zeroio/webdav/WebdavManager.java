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
package com.zeroio.webdav;

import com.zeroio.webdav.base.WebdavModule;
import com.zeroio.webdav.base.WebdavModuleList;
import com.zeroio.webdav.context.*;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.PasswordHash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id$
 * @created November 2, 2004
 */
public class WebdavManager {

  private String fileLibraryPath = null;
  private boolean modulesBuilt = false;
  //User List cache
  private HashMap users = new HashMap();
  //Webdav Module cache
  private WebdavModuleList moduleList = new WebdavModuleList();


  /**
   * Sets the fileLibraryPath attribute of the WebdavManager object
   *
   * @param tmp The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   * Sets the modulesBuilt attribute of the WebdavManager object
   *
   * @param tmp The new modulesBuilt value
   */
  public void setModulesBuilt(boolean tmp) {
    this.modulesBuilt = tmp;
  }


  /**
   * Sets the modulesBuilt attribute of the WebdavManager object
   *
   * @param tmp The new modulesBuilt value
   */
  public void setModulesBuilt(String tmp) {
    this.modulesBuilt = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the users attribute of the WebdavManager object
   *
   * @param tmp The new users value
   */
  public void setUsers(HashMap tmp) {
    this.users = tmp;
  }


  /**
   * Sets the moduleList attribute of the WebdavManager object
   *
   * @param tmp The new moduleList value
   */
  public void setModuleList(WebdavModuleList tmp) {
    this.moduleList = tmp;
  }


  /**
   * Gets the fileLibraryPath attribute of the WebdavManager object
   *
   * @return The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }


  /**
   * Gets the modulesBuilt attribute of the WebdavManager object
   *
   * @return The modulesBuilt value
   */
  public boolean getModulesBuilt() {
    return modulesBuilt;
  }


  /**
   * Gets the users attribute of the WebdavManager object
   *
   * @return The users value
   */
  public HashMap getUsers() {
    return users;
  }


  /**
   * Gets the moduleList attribute of the WebdavManager object
   *
   * @return The moduleList value
   */
  public WebdavModuleList getModuleList() {
    return moduleList;
  }


  /**
   * Constructor for the WebdavManager object
   */
  public WebdavManager() {
  }


  /**
   * Constructor for the WebdavManager object
   *
   * @param fileLibraryPath Description of the Parameter
   */
  public WebdavManager(String fileLibraryPath) {
    this.fileLibraryPath = fileLibraryPath;
  }


  /**
   * Description of the Method
   *
   * @param db              Description of the Parameter
   * @param fileLibraryPath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildModules(Connection db, String fileLibraryPath) throws SQLException {
    this.fileLibraryPath = fileLibraryPath;
    // build the modules
    moduleList.clear();
    moduleList.setFileLibraryPath(fileLibraryPath);
    moduleList.setBuildContext(true);
    moduleList.buildList(db);
    modulesBuilt = true;
  }


  /**
   * used by basic authentication scheme
   *
   * @param db       Description of the Parameter
   * @param username Description of the Parameter
   * @param password Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean allowUser(Connection db, String username, String password) throws SQLException {
    boolean status = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT a.password, a.expires, a.alias, a.user_id, a.role_id, r.\"role\" " +
        "FROM access a, \"role\" r " +
        "WHERE a.role_id = r.role_id " +
        "AND " + DatabaseUtils.toLowerCase(db) + "(a.username) = ? " +
        "AND a.enabled = ? ");
    pst.setString(1, username.toLowerCase());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      //TODO: determine if the user account has not expired
      String pw = rs.getString("password");
      if (pw.equals(PasswordHash.encrypt(password))) {
        int userId = rs.getInt("user_id");
        int roleId = rs.getInt("role_id");
        WebdavUser user = new WebdavUser();
        user.setUserId(userId);
        user.setRoleId(roleId);
        users.put(username.toLowerCase(), user);
        status = true;
      }
    }
    rs.close();
    pst.close();
    return status;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param username Description of the Parameter
   * @param nonce    The feature to be added to the User attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean addUser(Connection db, String username, String nonce) throws SQLException {
    boolean status = false;
    PreparedStatement pst = db.prepareStatement(
        "SELECT a.expires, a.alias, a.user_id, a.role_id, a.webdav_password, r.\"role\" " +
        "FROM access a, \"role\" r " +
        "WHERE a.role_id = r.role_id " +
        "AND " + DatabaseUtils.toLowerCase(db) + "(a.username) = ? " +
        "AND a.enabled = ? ");
    pst.setString(1, username.toLowerCase());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      //TODO: determine if the user account has not expired
      int userId = rs.getInt("user_id");
      int roleId = rs.getInt("role_id");
      String digest = rs.getString("webdav_password");
      WebdavUser user = new WebdavUser();
      user.setUserId(userId);
      user.setRoleId(roleId);
      user.setDigest(digest);
      user.setNonce(nonce);
      users.put(username.toLowerCase(), user);
      status = true;
    }
    rs.close();
    pst.close();
    return status;
  }


  /**
   * Gets the webdavPassword attribute of the WebdavManager object
   *
   * @param db       Description of the Parameter
   * @param username Description of the Parameter
   * @return The webdavPassword value
   * @throws SQLException Description of the Exception
   */
  public String getWebdavPassword(Connection db, String username) throws SQLException {
    String password = "";
    PreparedStatement pst = db.prepareStatement(
        "SELECT webdav_password " +
        "FROM access " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(username) = ? " +
        "AND enabled = ? ");
    pst.setString(1, username.toLowerCase());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      password = rs.getString("webdav_password");
    }
    rs.close();
    pst.close();
    return password;
  }


  /**
   * Description of the Method
   *
   * @param username Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasUser(String username) {
    return (users.containsKey(username.toLowerCase()));
  }


  /**
   * Gets the user attribute of the WebdavManager object
   *
   * @param username Description of the Parameter
   * @return The user value
   */
  public WebdavUser getUser(String username) {
    return ((WebdavUser) users.get(username.toLowerCase()));
  }


  /**
   * Description of the Method
   *
   * @param username Description of the Parameter
   */
  public void removeUser(String username) {
    if (hasUser(username)) {
      users.remove(username.toLowerCase());
    }
  }


  /**
   * Iterates through the cached top level webdav modules for this system and
   * determines if the user has permission to view this module. If so the
   * modules list of bindings are populated
   *
   * @param db         Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @param username   Description of the Parameter
   * @return The resources value
   * @throws SQLException Description of the Exception
   */
  public ModuleContext getResources(Connection db, SystemStatus thisSystem, String username) throws SQLException {
    WebdavUser user = this.getUser(username);
    BaseWebdavContext context = new BaseWebdavContext(
        user.getUserId(), fileLibraryPath);
    Iterator i = moduleList.keySet().iterator();
    while (i.hasNext()) {
      String moduleName = (String) i.next();
      WebdavModule module = (WebdavModule) moduleList.get(moduleName);
      String permission = module.getContext().getPermission();
      if (hasPermission(thisSystem, user.getUserId(), permission)) {
        context.getBindings().put(moduleName, module.getContext());
        context.buildProperties(
            moduleName, module.getEntered(), module.getModified(), new Integer(
                0));
      }
    }
    BaseWebdavContext synchronization = new BaseWebdavContext(
        "Synchronization");
    CalendarContext calendar = new CalendarContext("Calendars");
    ContactContext contacts = new ContactContext("Contacts");

    synchronization.getBindings().put("Calendars", calendar);
    synchronization.getBindings().put("Contacts", contacts);

    synchronization.buildProperties(
        "Calendars", new java.sql.Timestamp(new java.util.Date().getTime()), new java.sql.Timestamp(
            new java.util.Date().getTime()), new Integer(0));
    synchronization.buildProperties(
        "Contacts", new java.sql.Timestamp(new java.util.Date().getTime()), new java.sql.Timestamp(
            new java.util.Date().getTime()), new Integer(0));

    context.getBindings().put("Synchronization", synchronization);
    context.buildProperties(
        "Synchronization", new java.sql.Timestamp(
            new java.util.Date().getTime()), new java.sql.Timestamp(
                new java.util.Date().getTime()), new Integer(0));
    return context;
  }


  /**
   * Description of the Method
   *
   * @param permission Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @param userId     Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasPermission(SystemStatus thisSystem, int userId, String permission) {
    return thisSystem.hasPermission(userId, permission);
  }


  /**
   * Gets the topLevelModule attribute of the WebdavManager object
   *
   * @param object Description of the Parameter
   * @return The topLevelModule value
   */
  public boolean isTopLevelModule(Object object) {
    if (object instanceof ItemContext) {
      return true;
    }

    Iterator i = moduleList.keySet().iterator();
    while (i.hasNext()) {
      String moduleName = (String) i.next();
      WebdavModule module = (WebdavModule) moduleList.get(moduleName);
      String className = module.getClassName();
      if (className.equals(object.getClass().getName())) {
        return true;
      }
    }
    return false;
  }
}

