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
package com.zeroio.webdav.base;

import com.zeroio.webdav.context.ModuleContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id$
 * @created November 3, 2004
 */
public class WebdavModule {
  private int id = -1;
  private int categoryId = -1;
  private String className = null;
  private String displayName = null;
  private ModuleContext context = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  // resources
  private boolean buildContext = false;
  private String fileLibraryPath = null;


  /**
   * Sets the buildContext attribute of the WebdavModule object
   *
   * @param tmp The new buildContext value
   */
  public void setBuildContext(boolean tmp) {
    this.buildContext = tmp;
  }


  /**
   * Sets the buildContext attribute of the WebdavModule object
   *
   * @param tmp The new buildContext value
   */
  public void setBuildContext(String tmp) {
    this.buildContext = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildContext attribute of the WebdavModule object
   *
   * @return The buildContext value
   */
  public boolean getBuildContext() {
    return buildContext;
  }


  /**
   * Sets the fileLibraryPath attribute of the WebdavModule object
   *
   * @param tmp The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   * Gets the fileLibraryPath attribute of the WebdavModule object
   *
   * @return The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }


  /**
   * Sets the id attribute of the WebdavModule object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the WebdavModule object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryId attribute of the WebdavModule object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the WebdavModule object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the className attribute of the WebdavModule object
   *
   * @param tmp The new className value
   */
  public void setClassName(String tmp) {
    this.className = tmp;
  }


  /**
   * Sets the displayName attribute of the WebdavModule object
   *
   * @param tmp The new displayName value
   */
  public void setDisplayName(String tmp) {
    this.displayName = tmp;
  }


  /**
   * Sets the context attribute of the WebdavModule object
   *
   * @param tmp The new context value
   */
  public void setContext(ModuleContext tmp) {
    this.context = tmp;
  }


  /**
   * Sets the entered attribute of the WebdavModule object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the WebdavModule object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the WebdavModule object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the WebdavModule object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the WebdavModule object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the WebdavModule object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the WebdavModule object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the WebdavModule object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the WebdavModule object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the categoryId attribute of the WebdavModule object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Gets the className attribute of the WebdavModule object
   *
   * @return The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   * Gets the displayName attribute of the WebdavModule object
   *
   * @return The displayName value
   */
  public String getDisplayName() {
    return displayName;
  }


  /**
   * Gets the context attribute of the WebdavModule object
   *
   * @return The context value
   */
  public ModuleContext getContext() {
    return context;
  }


  /**
   * Gets the entered attribute of the WebdavModule object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the WebdavModule object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the WebdavModule object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the WebdavModule object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Constructor for the WebdavModule object
   */
  public WebdavModule() {
  }


  /**
   * Constructor for the WebdavModule object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public WebdavModule(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the WebdavModule object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public WebdavModule(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Module ID specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT w.*, pc.category " +
        "FROM webdav w " +
        "LEFT JOIN permission_category pc ON (w.category_id = pc.category_id) " +
        "WHERE id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Webdav Module record not found");
    }
    if (buildContext) {
      buildContext();
    }
  }


  /**
   * Description of the Method
   */
  protected void buildContext() {
    if (className != null) {
      try {
        context = (ModuleContext) Class.forName(className).newInstance();
        context.setContextName(displayName);
      } catch (ClassNotFoundException e) {
        e.printStackTrace(System.out);
      } catch (InstantiationException e) {
        e.printStackTrace(System.out);
      } catch (IllegalAccessException e) {
        e.printStackTrace(System.out);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param userId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResources(SystemStatus thisSystem, Connection db, int userId) throws SQLException {
    if (context != null) {
      context.buildResources(thisSystem, db, userId, fileLibraryPath);
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //webdav table
    id = rs.getInt("id");
    categoryId = rs.getInt("category_id");
    className = rs.getString("class_name");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    //permission_category table
    displayName = rs.getString("category");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "webdav_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO webdav " +
        "(" + (id > -1 ? "id, " : "") + "category_id, class_name, enteredby, modifiedby) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, categoryId);
    pst.setString(++i, className);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "webdav_id_seq", id);
    return true;
  }


  /**
   * Gets the permission attribute of the WebdavModule object
   *
   * @return The permission value
   */
  public String getPermission() {
    if (context != null) {
      return getContext().getPermission();
    } else {
      return "";
    }
  }

}

