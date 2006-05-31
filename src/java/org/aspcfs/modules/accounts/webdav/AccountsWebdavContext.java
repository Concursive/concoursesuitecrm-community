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
import org.aspcfs.modules.contacts.base.Contact;

import javax.naming.NamingException;
import java.io.FileNotFoundException;
import java.sql.*;

/**
 * AccountsWebdavContext represents a Module Context which has name-object
 * bindings. A bound object represents an Organization.
 *
 * @author ananth
 * @version $Id: AccountsWebdavContext.java,v 1.2.6.1 2005/05/10 18:01:53
 *          ananth Exp $
 * @created November 3, 2004
 */
public class AccountsWebdavContext
    extends BaseWebdavContext implements ModuleContext {

  private final static String ACCOUNTS = "accounts";
  private int linkModuleId = Constants.DOCUMENTS_ACCOUNTS;
  private String fileLibraryPath = null;
  private String permission = "accounts-view";
  //Permissions for this context
  private int userId = -1;


  /**
   * Sets the userId attribute of the AccountsWebdavContext object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the AccountsWebdavContext object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   * Gets the userId attribute of the AccountsWebdavContext object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Sets the linkModuleId attribute of the AccountsWebdavContext object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the AccountsWebdavContext object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   * Sets the fileLibraryPath attribute of the AccountsWebdavContext object
   *
   * @param tmp The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   * Sets the permission attribute of the AccountsWebdavContext object
   *
   * @param tmp The new permission value
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }


  /**
   * Gets the linkModuleId attribute of the AccountsWebdavContext object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Gets the fileLibraryPath attribute of the AccountsWebdavContext object
   *
   * @return The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }


  /**
   * Gets the permission attribute of the AccountsWebdavContext object
   *
   * @return The permission value
   */
  public String getPermission() {
    return permission;
  }


  /**
   * Constructor for the AccountsWebdavContext object
   */
  public AccountsWebdavContext() {
  }


  /**
   * Constructor for the AccountsWebdavContext object
   *
   * @param name         Description of the Parameter
   * @param linkModuleId Description of the Parameter
   */
  public AccountsWebdavContext(String name, int linkModuleId) {
    this.contextName = name;
    this.linkModuleId = linkModuleId;
  }


  /**
   * Description of the Method
   *
   * @param db              Description of the Parameter
   * @param userId          Description of the Parameter
   * @param fileLibraryPath Description of the Parameter
   * @param thisSystem      Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResources(SystemStatus thisSystem, Connection db, int userId, String fileLibraryPath) throws SQLException {
    this.fileLibraryPath = fileLibraryPath;
    this.userId = userId;
    bindings.clear();
    if (hasPermission(thisSystem, userId, "accounts-accounts-view")) {
      populateBindings(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void populateBindings(Connection db) throws SQLException {
    if (linkModuleId == -1) {
      throw new SQLException("Module ID not specified");
    }
    int siteId = getUserSiteId(db, userId);
    
    PreparedStatement pst = db.prepareStatement(
        "SELECT org_id, name, namelast, namefirst, entered, modified " +
        "FROM organization " +
        "WHERE org_id > 0 " +
        (siteId != -1 ? "AND site_id = ? " : "") +
        "AND (status_id IS NULL OR status_id = 7) " + 
        "AND enabled = ? " +
        "AND trashed_date IS NULL ");
    int i = 0;
    if (siteId != -1) {
      pst.setInt(++i, siteId);
    }
    pst.setBoolean(++i, true);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ItemContext item = new ItemContext();
      if (rs.getString("namelast") != null && rs.getString("namefirst") != null) {
        item.setContextName(Contact.getNameFirstLast(
          rs.getString("namefirst"), 
          rs.getString("namelast")));
      } else {
        item.setContextName(rs.getString("name"));
      }
      item.setLinkModuleId(linkModuleId);
      item.setLinkItemId(rs.getInt("org_id"));
      item.setPath(fileLibraryPath + ACCOUNTS + fs);
      item.setUserId(userId);
      item.setPermission("accounts-accounts-documents");
      bindings.put(item.getContextName(), item);
      Timestamp entered = rs.getTimestamp("entered");
      Timestamp modified = rs.getTimestamp("modified");
      buildProperties(
          item.getContextName(), entered, modified, new Integer(0));
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param folderName Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException          Description of the Exception
   * @throws NamingException       Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   */
  public boolean createSubcontext(SystemStatus thisSystem, Connection db, String folderName) throws SQLException,
      FileNotFoundException, NamingException {
    //Not allowed
    return false;
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
    //binding an object at root context not allowed
    System.out.println("binding an object with Accounts context not allowed");
    throw new NamingException(
        "Binding an object with Accounts context not allowed..");
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
    //Not allowed
    System.out.println(
        "Unbinding folder at accounts context not allowed....returning false");
    throw new NamingException("Unbinding at accounts context not allowed...");
  }
}

