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
package org.aspcfs.modules.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Description of the Class
 *
 * @author matt
 * @version $Id: CustomFieldCategoryList.java,v 1.4 2002/03/13 15:36:32 chris
 *          Exp $
 * @created December 28, 2001
 */
public class CustomFieldCategoryList extends ArrayList implements SyncableList {

  public final static String tableName = "custom_field_category";
  public final static String uniqueField = "category_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private PagedListInfo pagedListInfo = null;

  //Properties for building the list
  private int linkModuleId = -1;
  private int includeEnabled = -1;
  private int includeScheduled = -1;
  private boolean buildResources = false;
  private boolean allSelectOption = false;

  private String jsEvent = null;

  private int linkItemId = -1;
  private boolean buildTotalNumOfRecords = false;

  private String htmlJsEvent = "";

  /**
   * Constructor for the CustomFieldCategoryList object
   */
  public CustomFieldCategoryList() {
  }

  /**
   * Description of the Method
   *
   * @param rs resultset
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static CustomFieldCategory getObject(ResultSet rs) throws SQLException {
    CustomFieldCategory customFieldCategory = new CustomFieldCategory(rs);
    return customFieldCategory;
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
   * Sets the linkItemId attribute of the CustomFieldCategoryList object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Sets the linkItemId attribute of the CustomFieldCategoryList object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkItemId attribute of the CustomFieldCategoryList object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Sets the buildTotalNumOfRecords attribute of the CustomFieldCategoryList
   * object
   *
   * @param tmp The new buildTotalNumOfRecords value
   */
  public void setBuildTotalNumOfRecords(boolean tmp) {
    this.buildTotalNumOfRecords = tmp;
  }


  /**
   * Sets the buildTotalNumOfRecords attribute of the CustomFieldCategoryList
   * object
   *
   * @param tmp The new buildTotalNumOfRecords value
   */
  public void setBuildTotalNumOfRecords(String tmp) {
    this.buildTotalNumOfRecords = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the buildTotalNumOfRecords attribute of the CustomFieldCategoryList
   * object
   *
   * @return The buildTotalNumOfRecords value
   */
  public boolean getBuildTotalNumOfRecords() {
    return buildTotalNumOfRecords;
  }

  /**
   * Sets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   * @param tmp The new PagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the CustomFieldCategoryList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the CustomFieldCategoryList object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   * Sets the includeEnabled attribute of the CustomFieldCategoryList object
   *
   * @param tmp The new includeEnabled value
   */
  public void setIncludeEnabled(int tmp) {
    this.includeEnabled = tmp;
  }


  /**
   * Sets the includeScheduled attribute of the CustomFieldCategoryList object
   *
   * @param tmp The new includeScheduled value
   */
  public void setIncludeScheduled(int tmp) {
    this.includeScheduled = tmp;
  }


  /**
   * Sets the BuildResources attribute of the CustomFieldCategoryList object
   *
   * @param tmp The new BuildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   * Sets the jsEvent attribute of the CustomFieldCategoryList object
   *
   * @param tmp The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   * Sets the allSelectOption attribute of the CustomFieldCategoryList object
   *
   * @param allSelectOption The new allSelectOption value
   */
  public void setAllSelectOption(boolean allSelectOption) {
    this.allSelectOption = allSelectOption;
  }


  /**
   * Gets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   * @return The PagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the linkModuleId attribute of the CustomFieldCategoryList object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Gets the includeEnabled attribute of the CustomFieldCategoryList object
   *
   * @return The includeEnabled value
   */
  public int getIncludeEnabled() {
    return includeEnabled;
  }


  /**
   * Gets the includeScheduled attribute of the CustomFieldCategoryList object
   *
   * @return The includeScheduled value
   */
  public int getIncludeScheduled() {
    return includeScheduled;
  }


  /**
   * Gets the allSelectOption attribute of the CustomFieldCategoryList object
   *
   * @return The allSelectOption value
   */
  public boolean getAllSelectOption() {
    return allSelectOption;
  }


  /**
   * Gets the BuildResources attribute of the CustomFieldCategoryList object
   *
   * @return The BuildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   * Gets the jsEvent attribute of the CustomFieldCategoryList object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }
   /**
   *  Sets the htmlJsEvent attribute of the CustomFieldCategoryList object
   *
   * @param  htmlJsEvent  The new htmlJsEvent value
   */
  public void setHtmlJsEvent(String htmlJsEvent) {
	  this.htmlJsEvent = htmlJsEvent;
  }

   /**
   *  Gets the HtmlJsEvent attribute of the CustomFieldCategoryList object
   *
   * @return    The HtmlJsEvent value
   */
  public String getHtmlJsEvent() {
  	return htmlJsEvent;
  }

  /**
   * Gets the defaultCategoryId attribute of the CustomFieldCategoryList object
   *
   * @return The defaultCategoryId value
   */
  public int getDefaultCategoryId() {
    int tmpDefault = -1;
    int count = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ++count;
      CustomFieldCategory thisCategory = (CustomFieldCategory) i.next();
      if (count == 1) {
        //If there is no default, use the first entry
        tmpDefault = thisCategory.getId();
      }
      if (thisCategory.getDefaultItem()) {
        return thisCategory.getId();
      }
    }
    return tmpDefault;
  }


  /**
   * Gets the defaultCategory attribute of the CustomFieldCategoryList object
   *
   * @return The defaultCategory value
   */
  public CustomFieldCategory getDefaultCategory() {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomFieldCategory thisCategory = (CustomFieldCategory) i.next();
      if (thisCategory.getDefaultItem()) {
        return thisCategory;
      }
    }
    return new CustomFieldCategory();
  }


  /**
   * Gets the category attribute of the CustomFieldCategoryList object
   *
   * @param tmp Description of Parameter
   * @return The category value
   */
  public CustomFieldCategory getCategory(int tmp) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomFieldCategory thisCategory = (CustomFieldCategory) i.next();
      if (thisCategory.getId() == tmp) {
        return thisCategory;
      }
    }
    return new CustomFieldCategory();
  }


  /**
   * Gets the htmlSelect attribute of the CustomFieldCategoryList object
   *
   * @param selectName Description of Parameter
   * @param defaultKey Description of Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, String defaultKey) {
    return this.getHtmlSelect(selectName, Integer.parseInt(defaultKey));
  }


  /**
   * Gets the htmlSelect attribute of the CustomFieldCategoryList object
   *
   * @param selectName Description of Parameter
   * @param defaultKey Description of Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    return getHtmlSelect(selectName,defaultKey,false);
  }

 public String getHtmlSelect(String selectName, int defaultKey,boolean noneSelect) {

    HtmlSelect thisSelect = new HtmlSelect();

    if (allSelectOption) {
      thisSelect.addItem(0, "All Folders");
    }

    if (noneSelect){
      thisSelect.addItem(-1, "--None--");
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomFieldCategory thisCategory = (CustomFieldCategory) i.next();
      thisSelect.addItem(thisCategory.getId(), thisCategory.getName());
    }

    if (jsEvent != null) {
      thisSelect.setJsEvent(this.jsEvent);
    }

      if (!(this.getHtmlJsEvent().equals(""))) {
    	thisSelect.setJsEvent(this.getHtmlJsEvent());
    }
    return thisSelect.getHtml(selectName, defaultKey);
  }

  /**
   * Description of the Method
   *
   * @param db
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    return prepareList(db, "", "");
  }

  /**
   * Description of the Method
   *
   * @param db
   * @param sqlFilter
   * @param sqlOrder
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " cfc.module_id as module_id, cfc.category_id as category_id, cfc.category_name as category_name, cfc." + DatabaseUtils.addQuotes(db, "level") + " as " + DatabaseUtils.addQuotes(db, "level") + ",  " +
            " cfc.description as description, cfc.start_date as start_date, cfc.end_date as end_date, " +
            " cfc.default_item as default_item, cfc.entered as entered, cfc.enabled as enabled, " +
            " cfc.multiple_records as multiple_records, cfc.read_only as read_only, cfc.modified " +
            " FROM " + tableName + " cfc, module_field_categorylink mfc " +
            " WHERE cfc.module_id = mfc.category_id ");
    if(sqlFilter == null || sqlFilter.length() == 0){
      StringBuffer buff = new StringBuffer();
      createFilter(buff);
      sqlFilter = buff.toString();
    }
    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() + sqlFilter + sqlOrder);
    prepareFilter(pst);
    return pst;
  }

  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) as recordcount " +
            "FROM custom_field_category cfc, module_field_categorylink mfc " +
            "WHERE cfc.module_id = mfc.category_id ");

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
      rs.close();
      pst.close();

      //Determine column to sort by
      pagedListInfo.setDefaultSort(
          "cfc." + DatabaseUtils.addQuotes(db, "level") + ", cfc.category_name, cfc.category_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append(
          "ORDER BY cfc." + DatabaseUtils.addQuotes(db, "level") + ", cfc.category_name, cfc.category_id ");
    }

    pst = prepareList(db, sqlFilter.toString(), sqlOrder.toString());
    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      CustomFieldCategory thisCategory = new CustomFieldCategory(rs);
      this.add(thisCategory);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }

    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomFieldCategory thisCategory = (CustomFieldCategory) i.next();
        thisCategory.setLinkModuleId(linkModuleId);
        thisCategory.setIncludeEnabled(this.includeEnabled);
        thisCategory.setIncludeScheduled(this.includeScheduled);
        thisCategory.setBuildResources(this.buildResources);
        thisCategory.buildResources(db);
      }
    }

    if (buildTotalNumOfRecords && linkItemId != -1) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomFieldCategory thisCategory = (CustomFieldCategory) i.next();
        thisCategory.determineNumberOfRecords(db, linkModuleId, linkItemId);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (linkModuleId > -1) {
     sqlFilter.append("AND cfc.module_id = ? ");
    }

    if (includeScheduled == Constants.TRUE) {
      sqlFilter.append(
          "AND CURRENT_TIMESTAMP > cfc.start_date AND (CURRENT_TIMESTAMP < cfc.end_date OR cfc.end_date IS NULL) ");
    } else if (includeScheduled == Constants.FALSE) {
      sqlFilter.append(
          "AND (CURRENT_TIMESTAMP < cfc.start_date OR (CURRENT_TIMESTAMP > cfc.end_date AND cfc.end_date IS NOT NULL)) ");
    }
    if (includeEnabled == Constants.TRUE || includeEnabled == Constants.FALSE) {
      sqlFilter.append("AND cfc.enabled = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND cfc.entered > ? ");
      }
      sqlFilter.append("AND cfc.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND cfc.modified > ? ");
      sqlFilter.append("AND cfc.entered < ? ");
      sqlFilter.append("AND cfc.modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (linkModuleId > -1) {
      pst.setInt(++i, linkModuleId);
    }

    if (includeEnabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (includeEnabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
}

