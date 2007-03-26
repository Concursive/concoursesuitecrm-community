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
package org.aspcfs.modules.admin.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id$
 * @created January 13, 2003
 */
public class PermissionList extends Vector implements SyncableList{

  private static final long serialVersionUID = -3101139259287703124L;

  public final static String tableName = "permission";
  public final static String uniqueField = "permission_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;

  private String currentCategory = "!new";
  private boolean viewpointsOnly = false;
  private int enabled = Constants.TRUE;

  public int getEnabled(int tmp) {
    return enabled;
  }

  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }

  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }

  /**
   * Constructor for the PermissionList object
   */
  public PermissionList() {
  }

  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static Permission getObject(ResultSet rs) throws SQLException {
    Permission permission = new Permission(rs);
    return permission;
  }

  /**
   * Constructor for the PermissionList object
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public PermissionList(Connection db) throws SQLException {
    buildList(db);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getTableName()
   */
  public String getTableName() {
    return tableName;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getUniqueField()
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.sql.Timestamp)
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.lang.String)
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = java.sql.Timestamp.valueOf(lastAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.sql.Timestamp)
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.lang.String)
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = java.sql.Timestamp.valueOf(nextAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(int)
   */
  public void setSyncType(int syncType) {
    this.syncType = syncType;
  }

  
  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }
  
  /**
   * Sets the viewpointsOnly attribute of the PermissionList object
   *
   * @param viewpointsOnly The new viewpointsOnly value
   */
  public void setViewpointsOnly(boolean viewpointsOnly) {
    this.viewpointsOnly = viewpointsOnly;
  }


  /**
   * Gets the viewpointsOnly attribute of the PermissionList object
   *
   * @return The viewpointsOnly value
   */
  public boolean getViewpointsOnly() {
    return viewpointsOnly;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    ResultSet rs = this.queryList(db, null);
    while (rs.next()) {
      this.addElement(PermissionList.getObject(rs));
    }
    rs.close();
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
    ResultSet rs = null;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT p.*, c.category " +
            "FROM permission p, permission_category c " +
            "WHERE p.category_id = c.category_id ");
    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY c." + DatabaseUtils.addQuotes(db, "level") + ", c.category, p." + DatabaseUtils.addQuotes(db, "level") + " ");

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    return rs;
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

    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND p.enabled = ? ");
      sqlFilter.append("AND c.enabled = ? ");
    }

    if (viewpointsOnly) {
      sqlFilter.append("AND p.viewpoints = ? ");
      sqlFilter.append("AND c.viewpoints = ? ");
    }

    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND p.entered > ? ");
      }
      sqlFilter.append("AND p.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND p.modified > ? ");
      sqlFilter.append("AND p.entered < ? ");
      sqlFilter.append("AND p.modified < ? ");
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
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, (enabled == Constants.TRUE));
      pst.setBoolean(++i, (enabled == Constants.TRUE));
    }
    if (viewpointsOnly) {
      pst.setBoolean(++i, true);
      pst.setBoolean(++i, true);
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
   * Gets the newCategory attribute of the PermissionList object
   *
   * @param thisCategory Description of the Parameter
   * @return The newCategory value
   */
  public boolean isNewCategory(String thisCategory) {
    if (thisCategory.equals(currentCategory)) {
      return false;
    } else {
      currentCategory = thisCategory;
      return true;
    }
  }

  public static int retrieveMaxLevel(Connection db, int tmpCategoryId) throws SQLException {
    int maxLevel = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT MAX(" + DatabaseUtils.addQuotes(db, "level") + ") AS max_level " +
        "FROM permission " +
				"WHERE category_id = ? ");
    pst.setInt(1, tmpCategoryId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      maxLevel = rs.getInt("max_level");
    }
    rs.close();
    pst.close();
    return maxLevel;
  }
}
