//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package org.aspcfs.modules.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  Description of the Class
 *
 *@author     matt
 *@created    December 28, 2001
 *@version    $Id: CustomFieldCategoryList.java,v 1.4 2002/03/13 15:36:32 chris
 *      Exp $
 */
public class CustomFieldCategoryList extends ArrayList {

  private PagedListInfo pagedListInfo = null;

  //Properties for building the list
  private int linkModuleId = -1;
  private int includeEnabled = -1;
  private int includeScheduled = -1;
  private boolean buildResources = false;
  private boolean allSelectOption = false;

  private String jsEvent = null;


  /**
   *  Constructor for the CustomFieldCategoryList object
   *
   *@since
   */
  public CustomFieldCategoryList() { }


  /**
   *  Sets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the CustomFieldCategoryList object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the includeEnabled attribute of the CustomFieldCategoryList object
   *
   *@param  tmp  The new includeEnabled value
   */
  public void setIncludeEnabled(int tmp) {
    this.includeEnabled = tmp;
  }


  /**
   *  Sets the includeScheduled attribute of the CustomFieldCategoryList object
   *
   *@param  tmp  The new includeScheduled value
   */
  public void setIncludeScheduled(int tmp) {
    this.includeScheduled = tmp;
  }


  /**
   *  Sets the BuildResources attribute of the CustomFieldCategoryList object
   *
   *@param  tmp  The new BuildResources value
   *@since
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the jsEvent attribute of the CustomFieldCategoryList object
   *
   *@param  tmp  The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   *  Sets the allSelectOption attribute of the CustomFieldCategoryList object
   *
   *@param  allSelectOption  The new allSelectOption value
   */
  public void setAllSelectOption(boolean allSelectOption) {
    this.allSelectOption = allSelectOption;
  }


  /**
   *  Gets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   *@return    The PagedListInfo value
   *@since
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the linkModuleId attribute of the CustomFieldCategoryList object
   *
   *@return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Gets the includeEnabled attribute of the CustomFieldCategoryList object
   *
   *@return    The includeEnabled value
   */
  public int getIncludeEnabled() {
    return includeEnabled;
  }


  /**
   *  Gets the includeScheduled attribute of the CustomFieldCategoryList object
   *
   *@return    The includeScheduled value
   */
  public int getIncludeScheduled() {
    return includeScheduled;
  }


  /**
   *  Gets the allSelectOption attribute of the CustomFieldCategoryList object
   *
   *@return    The allSelectOption value
   */
  public boolean getAllSelectOption() {
    return allSelectOption;
  }


  /**
   *  Gets the BuildResources attribute of the CustomFieldCategoryList object
   *
   *@return    The BuildResources value
   *@since
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Gets the jsEvent attribute of the CustomFieldCategoryList object
   *
   *@return    The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   *  Gets the defaultCategoryId attribute of the CustomFieldCategoryList object
   *
   *@return    The defaultCategoryId value
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
   *  Gets the defaultCategory attribute of the CustomFieldCategoryList object
   *
   *@return    The defaultCategory value
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
   *  Gets the category attribute of the CustomFieldCategoryList object
   *
   *@param  tmp  Description of Parameter
   *@return      The category value
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
   *  Gets the htmlSelect attribute of the CustomFieldCategoryList object
   *
   *@param  selectName  Description of Parameter
   *@param  defaultKey  Description of Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, String defaultKey) {
    return this.getHtmlSelect(selectName, Integer.parseInt(defaultKey));
  }


  /**
   *  Gets the htmlSelect attribute of the CustomFieldCategoryList object
   *
   *@param  selectName  Description of Parameter
   *@param  defaultKey  Description of Parameter
   *@return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect thisSelect = new HtmlSelect();

    if (allSelectOption) {
      thisSelect.addItem(0, "All Folders");
    }

    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomFieldCategory thisCategory = (CustomFieldCategory) i.next();
      thisSelect.addItem(thisCategory.getId(), thisCategory.getName());
    }

    if (jsEvent != null) {
      thisSelect.setJsEvent(this.jsEvent);
    }

    return thisSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) as recordcount " +
        "FROM custom_field_category cfc, module_field_categorylink mfc " +
        "WHERE cfc.module_id = mfc.category_id AND mfc.module_id = ? ");

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
      pst.close();
      rs.close();

      //Determine column to sort by
      pagedListInfo.setDefaultSort("cfc.level, cfc.category_name, cfc.category_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY cfc.level, cfc.category_name, cfc.category_id ");
    }
    
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
      "* " +
      "FROM custom_field_category cfc, module_field_categorylink mfc " +
      "WHERE cfc.module_id = mfc.category_id AND cfc.module_id = ? ");

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      CustomFieldCategory thisCategory = new CustomFieldCategory(rs);
      this.add(thisCategory);
    }
    rs.close();
    pst.close();

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
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (includeScheduled == Constants.TRUE) {
      sqlFilter.append("AND CURRENT_TIMESTAMP > cfc.start_date AND (CURRENT_TIMESTAMP < cfc.end_date OR cfc.end_date IS NULL) ");
    } else if (includeScheduled == Constants.FALSE) {
      sqlFilter.append("AND (CURRENT_TIMESTAMP < cfc.start_date OR (CURRENT_TIMESTAMP > cfc.end_date AND cfc.end_date IS NOT NULL)) ");
    }

    if (includeEnabled == Constants.TRUE || includeEnabled == Constants.FALSE) {
      sqlFilter.append("AND cfc.enabled = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    pst.setInt(++i, linkModuleId);
    
    if (includeEnabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (includeEnabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }

    return i;
  }
}

