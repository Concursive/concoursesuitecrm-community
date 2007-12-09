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
package com.zeroio.webdav.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

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
 * @created November 3, 2004
 */
public class WebdavModuleList extends HashMap {

  public final static String tableName = "webdav";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  // Permission Category is enabled by default
  // filters
  private int enabled = Constants.TRUE;
  private int categoryId = -1;
  private String displayName = null;
  // resources
  private boolean buildContext = false;
  private String fileLibraryPath = null;


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
   * Sets the buildContext attribute of the WebdavModuleList object
   *
   * @param tmp The new buildContext value
   */
  public void setBuildContext(boolean tmp) {
    this.buildContext = tmp;
  }


  /**
   * Sets the buildContext attribute of the WebdavModuleList object
   *
   * @param tmp The new buildContext value
   */
  public void setBuildContext(String tmp) {
    this.buildContext = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildContext attribute of the WebdavModuleList object
   *
   * @return The buildContext value
   */
  public boolean getBuildContext() {
    return buildContext;
  }


  /**
   * Sets the fileLibraryPath attribute of the WebdavModuleList object
   *
   * @param tmp The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   * Gets the fileLibraryPath attribute of the WebdavModuleList object
   *
   * @return The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }


  /**
   * Sets the enabled attribute of the WebdavModuleList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the WebdavModuleList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryId attribute of the WebdavModuleList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the WebdavModuleList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the displayName attribute of the WebdavModuleList object
   *
   * @param tmp The new displayName value
   */
  public void setDisplayName(String tmp) {
    this.displayName = tmp;
  }


  /**
   * Gets the enabled attribute of the WebdavModuleList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Gets the categoryId attribute of the WebdavModuleList object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Gets the displayName attribute of the WebdavModuleList object
   *
   * @return The displayName value
   */
  public String getDisplayName() {
    return displayName;
  }


  /**
   * Constructor for the WebdavModuleList object
   */
  public WebdavModuleList() {
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    sqlSelect.append(
        "SELECT w.*, pc.category, pc.webdav " +
            "FROM webdav w " +
            "LEFT JOIN permission_category pc ON (w.category_id = pc.category_id) " +
            "WHERE w.id > 0 ");
    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY id ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      WebdavModule thisModule = new WebdavModule(rs);
      // set the fileLibraryPath for documents
      thisModule.setFileLibraryPath(fileLibraryPath);
      this.put(thisModule.getDisplayName(), thisModule);
      System.out.println(
          "WebdavModuleList-> Adding " + thisModule.getDisplayName() + " to List");
    }
    rs.close();
    pst.close();
    if (buildContext) {
      Iterator i = this.keySet().iterator();
      while (i.hasNext()) {
        String moduleName = (String) i.next();
        WebdavModule thisModule = (WebdavModule) this.get(moduleName);
        thisModule.buildContext();
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
    sqlFilter.append("AND pc.webdav = ? ");
    if (categoryId > -1) {
      sqlFilter.append("AND w.category_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND pc.enabled = ? ");
    }
    if (displayName != null) {
      sqlFilter.append("AND pc.category = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    pst.setBoolean(++i, true);
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, enabled == Constants.TRUE);
    }
    if (displayName != null) {
      pst.setString(++i, displayName);
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
}

