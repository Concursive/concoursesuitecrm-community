//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;

/**
 *  Description of the Class
 *
 *@author     matt
 *@created    December 28, 2001
 *@version    $Id$
 */
public class CustomFieldCategoryList extends Vector {
  
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

  public void setLinkModuleId(int tmp) { this.linkModuleId = tmp; }
  public void setIncludeEnabled(int tmp) { this.includeEnabled = tmp; }
  public void setIncludeScheduled(int tmp) { this.includeScheduled = tmp; }


  /**
   *  Sets the BuildResources attribute of the CustomFieldCategoryList object
   *
   *@param  tmp  The new BuildResources value
   *@since
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }

  public void setJsEvent(String tmp) { this.jsEvent = tmp; }

  /**
   *  Gets the PagedListInfo attribute of the CustomFieldCategoryList object
   *
   *@return    The PagedListInfo value
   *@since
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }

  public int getLinkModuleId() { return linkModuleId; }
  public int getIncludeEnabled() { return includeEnabled; }
  public int getIncludeScheduled() { return includeScheduled; }
  
	public boolean getAllSelectOption() {
		return allSelectOption;
	}
	public void setAllSelectOption(boolean allSelectOption) {
		this.allSelectOption = allSelectOption;
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

  public String getJsEvent() { return jsEvent; }
  
  public int getDefaultCategoryId() {
    int tmpDefault = -1;
    int count = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ++count;
      CustomFieldCategory thisCategory = (CustomFieldCategory)i.next();
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
  
  public CustomFieldCategory getDefaultCategory() {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomFieldCategory thisCategory = (CustomFieldCategory)i.next();
      if (thisCategory.getDefaultItem()) {
        return thisCategory;
      }
    }
    return new CustomFieldCategory();
  }
  
  public CustomFieldCategory getCategory(int tmp) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      CustomFieldCategory thisCategory = (CustomFieldCategory)i.next();
      if (thisCategory.getId() == tmp) {
        return thisCategory;
      }
    }
    return new CustomFieldCategory();
  }
  
  
  public String getHtmlSelect(String selectName, String defaultKey) {
    return this.getHtmlSelect(selectName, Integer.parseInt(defaultKey));
  }

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

    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT * " +
        "FROM custom_field_category cfc " +
    //"LEFT JOIN custom_field_group cfg ON (cfc.category_id = cfg.category_id) " +
    //"LEFT JOIN custom_field_info cfi ON (cfg.group_id = cfi.group_id) " +
        "WHERE cfc.module_id = " + linkModuleId + " ");

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) as recordcount " +
        "FROM custom_field_category cfc " +
        "WHERE cfc.module_id = " + linkModuleId + " ");

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
      if (pagedListInfo.getColumnToSortBy() == null || pagedListInfo.getColumnToSortBy().equals("")) {
        pagedListInfo.setColumnToSortBy("level, category_name, category_id");
        //pagedListInfo.setSortOrder("desc");
      }
      sqlOrder.append("ORDER BY " + pagedListInfo.getColumnToSortBy() + " ");
      if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals("")) {
        sqlOrder.append(pagedListInfo.getSortOrder() + " ");
      }

      //Determine items per page
      if (pagedListInfo.getItemsPerPage() > 0) {
        sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
      }

      sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
    } else {
      sqlOrder.append("ORDER BY level, category_name, category_id ");
    }

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      CustomFieldCategory thisCategory = new CustomFieldCategory(rs);
      this.addElement(thisCategory);
    }
    rs.close();
    pst.close();

    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        CustomFieldCategory thisCategory = (CustomFieldCategory)i.next();
        thisCategory.setLinkModuleId(linkModuleId);
        thisCategory.setIncludeEnabled(this.includeEnabled);
        thisCategory.setIncludeScheduled(this.includeScheduled);
        thisCategory.setBuildResources(this.buildResources);
        thisCategory.buildResources(db);
      }
    }
  }


  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (includeScheduled == Constants.TRUE) {
      sqlFilter.append("AND CURRENT_TIMESTAMP > cfc.start_date AND (CURRENT_TIMESTAMP < cfc.end_date OR cfc.end_date IS NULL) ");
    } else if (includeScheduled == Constants.FALSE) {
      sqlFilter.append("AND (CURRENT_TIMESTAMP < cfc.start_date OR (CURRENT_TIMESTAMP > cfc.end_date AND cfc.end_date IS NOT NULL)) ");
    }

    if (includeEnabled == Constants.TRUE) {
      sqlFilter.append("AND cfc.enabled = true ");
    } else if (includeEnabled == Constants.FALSE) {
      sqlFilter.append("AND cfc.enabled = false ");
    }
  }


  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    return i;
  }
}

