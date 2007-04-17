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
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

/**
 * Contains a list of PermissionCategory objects, the list can be generated
 * based on specified filters
 *
 * @author Mathur
 * @version $Id: PermissionCategoryList.java,v 1.7.24.1 2003/09/15 20:58:21
 *          mrajkowski Exp $
 * @created January 13, 2003
 */
public class PermissionCategoryList extends Vector implements SyncableList {

  private static final long serialVersionUID = 1457263321295437190L;

  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;

  public final static String tableName = "permission_category";
  public final static String uniqueField = "category_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private boolean customizableModulesOnly = false;
  private boolean modulesWithReportsOnly = false;
  private int enabledState = -1;
  private int activeState = -1;


  /**
   * Constructor for the PermissionCategoryList object
   */
  public PermissionCategoryList() {  }

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
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static PermissionCategory getObject(ResultSet rs) throws SQLException {
    PermissionCategory pc = new PermissionCategory(rs);
    return pc;
  }

  /**
   * Sets the pagedListInfo attribute of the PermissionCategoryList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the emptyHtmlSelectRecord attribute of the PermissionCategoryList
   * object
   *
   * @param tmp The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   * Gets the pagedListInfo attribute of the PermissionCategoryList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the emptyHtmlSelectRecord attribute of the PermissionCategoryList
   * object
   *
   * @return The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }


  /**
   * Gets the lastAnchor attribute of the PermissionCategoryList object
   *
   * @return The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   * Gets the nextAnchor attribute of the PermissionCategoryList object
   *
   * @return The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   * Gets the syncType attribute of the PermissionCategoryList object
   *
   * @return The syncType value
   */
  public int getSyncType() {
    return syncType;
  }

  /**
   * Sets the enabledState attribute of the PermissionCategoryList object
   *
   * @param tmp The new enabledState value
   */
  public void setEnabledState(int tmp) {
    this.enabledState = tmp;
  }


  /**
   * Sets the activeState attribute of the PermissionCategoryList object
   *
   * @param tmp The new activeState value
   */
  public void setActiveState(int tmp) {
    this.activeState = tmp;
  }


  /**
   * Gets the enabledState attribute of the PermissionCategoryList object
   *
   * @return The enabledState value
   */
  public int getEnabledState() {
    return enabledState;
  }


  /**
   * Gets the activeState attribute of the PermissionCategoryList object
   *
   * @return The activeState value
   */
  public int getActiveState() {
    return activeState;
  }


  /**
   * Gets the customizableModulesOnly attribute of the PermissionCategoryList
   * object
   *
   * @return The customizableModulesOnly value
   */
  public boolean getCustomizableModulesOnly() {
    return customizableModulesOnly;
  }


  /**
   * Sets the customizableModulesOnly attribute of the PermissionCategoryList
   * object
   *
   * @param customizableModulesOnly The new customizableModulesOnly value
   */
  public void setCustomizableModulesOnly(boolean customizableModulesOnly) {
    this.customizableModulesOnly = customizableModulesOnly;
  }


  /**
   * Gets the modulesWithReportsOnly attribute of the PermissionCategoryList
   * object
   *
   * @return The modulesWithReportsOnly value
   */
  public boolean getModulesWithReportsOnly() {
    return modulesWithReportsOnly;
  }


  /**
   * Sets the modulesWithReportsOnly attribute of the PermissionCategoryList
   * object
   *
   * @param tmp The new modulesWithReportsOnly value
   */
  public void setModulesWithReportsOnly(boolean tmp) {
    this.modulesWithReportsOnly = tmp;
  }


  /**
   * Sets the modulesWithReportsOnly attribute of the PermissionCategoryList
   * object
   *
   * @param tmp The new modulesWithReportsOnly value
   */
  public void setModulesWithReportsOnly(String tmp) {
    this.modulesWithReportsOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = this.prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      this.add(PermissionCategoryList.getObject(rs));
    }
    rs.close();
    if(pst != null){
      pst.close();
    }
  }

  /**
   * Description of the Method
   *
   * @param db
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM permission_category pc " +
        "WHERE pc.category_id > 0 ");
    createFilter(db, sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      PreparedStatement pst = db.prepareStatement(
          sqlCount.toString() +
          sqlFilter.toString());
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
            "AND " + DatabaseUtils.toLowerCase(db) + "(pc.category) < ? ");
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
      pagedListInfo.setDefaultSort("pc.category", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY pc.category ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "pc.* " +
        "FROM permission_category pc " +
        "WHERE pc.category_id > 0 ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    return pst;
  }
  
  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
    if (enabledState != -1) {
      sqlFilter.append("AND enabled = ? ");
    }
    if (activeState != -1) {
      sqlFilter.append("AND " + DatabaseUtils.addQuotes(db, "active")+ " = ? ");
    }
    if (customizableModulesOnly) {
      sqlFilter.append(
          "AND (lookups = ? OR folders = ? " +
          "OR scheduled_events = ? OR object_events = ? " +
          "OR categories = ?) ");
    }
    if (modulesWithReportsOnly) {
      sqlFilter.append("AND reports = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND entered > ? ");
      }
      sqlFilter.append("AND entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND modified > ? ");
      sqlFilter.append("AND entered < ? ");
      sqlFilter.append("AND modified < ? ");
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
    if (enabledState != -1) {
      pst.setBoolean(++i, enabledState == Constants.TRUE);
    }
    if (activeState != -1) {
      pst.setBoolean(++i, activeState == Constants.TRUE);
    }
    if (customizableModulesOnly) {
      pst.setBoolean(++i, true);
      pst.setBoolean(++i, true);
      pst.setBoolean(++i, true);
      pst.setBoolean(++i, true);
      pst.setBoolean(++i, true);
    }
    if (modulesWithReportsOnly) {
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
   * Gets the htmlSelect attribute of the PermissionCategoryList object
   *
   * @param selectName Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   * Gets the htmlSelect attribute of the PermissionCategoryList object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect categoryListSelect = new HtmlSelect();
    if (emptyHtmlSelectRecord != null) {
      categoryListSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      PermissionCategory thisCategory = (PermissionCategory) i.next();
      categoryListSelect.addItem(
          thisCategory.getId(),
          thisCategory.getCategory());
    }
    return categoryListSelect.getHtml(selectName, defaultKey);
  }
}
