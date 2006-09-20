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

import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 *  Contains Category items for displaying to the user
 *
 * @author     matt rajkowski
 * @created    February 3, 2004
 * @version    $Id: CategoryList.java,v 1.1.2.1 2004/02/04 05:47:22 mrajkowski
 *      Exp $
 */
public class CategoryList extends Vector {
  HtmlSelect catListSelect = new HtmlSelect();
  private PagedListInfo pagedListInfo = null;
  private int parentCode = -1;
  private int catLevel = -1;
  private String HtmlJsEvent = "";
  private int enabledState = -1;
  private boolean includeDisabled = false;
  private String tableName = null;
  private int siteId = -1;
  private boolean exclusiveToSite = false;


  /**
   *  Constructor for the CategoryList object
   */
  public CategoryList() { }


  /**
   *  Constructor for the CategoryList object
   *
   * @param  tableName  Description of the Parameter
   */
  public CategoryList(String tableName) {
    this.tableName = tableName;
  }


  /**
   *  Sets the pagedListInfo attribute of the CategoryList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the htmlJsEvent attribute of the CategoryList object
   *
   * @param  HtmlJsEvent  The new htmlJsEvent value
   */
  public void setHtmlJsEvent(String HtmlJsEvent) {
    this.HtmlJsEvent = HtmlJsEvent;
  }


  /**
   *  Sets the catListSelect attribute of the CategoryList object
   *
   * @param  catListSelect  The new catListSelect value
   */
  public void setCatListSelect(HtmlSelect catListSelect) {
    this.catListSelect = catListSelect;
  }


  /**
   *  Sets the parentCode attribute of the CategoryList object
   *
   * @param  tmp  The new parentCode value
   */
  public void setParentCode(int tmp) {
    this.parentCode = tmp;
  }


  /**
   *  Sets the parentCode attribute of the CategoryList object
   *
   * @param  tmp  The new parentCode value
   */
  public void setParentCode(String tmp) {
    this.parentCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the catLevel attribute of the CategoryList object
   *
   * @param  catLevel  The new catLevel value
   */
  public void setCatLevel(int catLevel) {
    this.catLevel = catLevel;
  }


  /**
   *  Sets the catLevel attribute of the CategoryList object
   *
   * @param  catLevel  The new catLevel value
   */
  public void setCatLevel(String catLevel) {
    this.catLevel = Integer.parseInt(catLevel);
  }


  /**
   *  Sets the enabledState attribute of the CategoryList object
   *
   * @param  tmp  The new enabledState value
   */
  public void setEnabledState(int tmp) {
    this.enabledState = tmp;
  }


  /**
   *  Sets the includeDisabled attribute of the CategoryList object
   *
   * @param  includeDisabled  The new includeDisabled value
   */
  public void setIncludeDisabled(boolean includeDisabled) {
    this.includeDisabled = includeDisabled;
  }


  /**
   *  Gets the includeDisabled attribute of the CategoryList object
   *
   * @return    The includeDisabled value
   */
  public boolean getIncludeDisabled() {
    return includeDisabled;
  }


  /**
   *  Gets the catListSelect attribute of the CategoryList object
   *
   * @return    The catListSelect value
   */
  public HtmlSelect getCatListSelect() {
    return catListSelect;
  }


  /**
   *  Gets the htmlJsEvent attribute of the CategoryList object
   *
   * @return    The htmlJsEvent value
   */
  public String getHtmlJsEvent() {
    return HtmlJsEvent;
  }


  /**
   *  Gets the htmlSelect attribute of the CategoryList object
   *
   * @param  selectName  Description of the Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the catLevel attribute of the CategoryList object
   *
   * @return    The catLevel value
   */
  public int getCatLevel() {
    return catLevel;
  }


  /**
   *  Gets the pagedListInfo attribute of the CategoryList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the parentCode attribute of the CategoryList object
   *
   * @return    The parentCode value
   */
  public int getParentCode() {
    return parentCode;
  }


  /**
   *  Gets the enabledState attribute of the CategoryList object
   *
   * @return    The enabledState value
   */
  public int getEnabledState() {
    return enabledState;
  }


  /**
   *  Sets the tableName attribute of the CategoryList object
   *
   * @param  tmp  The new tableName value
   */
  public void setTableName(String tmp) {
    this.tableName = tmp;
  }


  /**
   *  Gets the tableName attribute of the CategoryList object
   *
   * @return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the siteId attribute of the CategoryList object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the siteId attribute of the CategoryList object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the CategoryList object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the exclusiveToSite attribute of the CategoryList object
   *
   * @return    The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   *  Sets the exclusiveToSite attribute of the CategoryList object
   *
   * @param  tmp  The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   *  Sets the exclusiveToSite attribute of the CategoryList object
   *
   * @param  tmp  The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the htmlSelect attribute of the CategoryList object
   *
   * @param  selectName  Description of the Parameter
   * @param  defaultKey  Description of the Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    Iterator i = this.iterator();
    catListSelect.addAttribute("id", selectName);
    while (i.hasNext()) {
      Category thisCat = (Category) i.next();
      String elementText = thisCat.getDescription();
      if (thisCat.getEnabled()) {
        catListSelect.addItem(
            thisCat.getId(),
            elementText);
      } else if ((!(thisCat.getEnabled()) && thisCat.getId() == defaultKey) || includeDisabled) {
        if (catListSelect.getSelectSize() > 1) {
          HashMap colorAttribute = new HashMap();
          colorAttribute.put("style", "color: red");
          catListSelect.addItem(
              thisCat.getId(),
              elementText, colorAttribute);
        } else {
          elementText += "*";
          catListSelect.addItem(
              thisCat.getId(),
              elementText);
        }
      }
    }
    if (!(this.getHtmlJsEvent().equals(""))) {
      catListSelect.setJsEvent(this.getHtmlJsEvent());
    }
    return catListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    if (tableName == null) {
      throw new SQLException("Not configured");
    }
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM " + tableName + " c " +
        "WHERE c.id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(
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
            "AND c.id < ? ");
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
      pagedListInfo.setDefaultSort("c.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY c.description");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "c.* " +
        "FROM " + tableName + " c " +
        "WHERE c.id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      Category thisCat = new Category(rs);
      this.add(thisCat);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (enabledState != -1) {
      sqlFilter.append("AND c.enabled = ? ");
    }
    if (parentCode != -1) {
      sqlFilter.append("AND c.parent_cat_code = ? ");
    }
    if (catLevel != -1) {
      sqlFilter.append("AND c.cat_level = ? ");
    }
    if (siteId > -1) {
      sqlFilter.append("AND (c.site_id = ? ");
      if (!exclusiveToSite) {
        sqlFilter.append("OR site_id IS NULL ");
      }
      sqlFilter.append(") ");
    } else {
      sqlFilter.append("AND site_id IS NULL ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (enabledState != -1) {
      pst.setBoolean(++i, enabledState == Constants.TRUE);
    }
    if (parentCode != -1) {
      pst.setInt(++i, parentCode);
    }
    if (catLevel != -1) {
      pst.setInt(++i, catLevel);
    }
    if (siteId > -1) {
      pst.setInt(++i, siteId);
    }
    return i;
  }


  /**
   *  Gets the htmlSelect attribute of the CategoryList object
   *
   * @param  defaultKey  Description of the Parameter
   * @return             The htmlSelect value
   */
  public HtmlSelect getHtmlSelect(int defaultKey) {
    HtmlSelect catListSelect = new HtmlSelect();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Category thisCat = (Category) i.next();
      String elementText = thisCat.getDescription();
      if (thisCat.getEnabled()) {
        catListSelect.addItem(thisCat.getId(), elementText);
      } else if ((!thisCat.getEnabled() && thisCat.getId() == defaultKey) || includeDisabled) {
        if (catListSelect.getSelectSize() > 1) {
          HashMap colorAttribute = new HashMap();
          colorAttribute.put("style", "color: red");
          catListSelect.addItem(
              thisCat.getId(), elementText, colorAttribute, false);
        } else {
          elementText += "*";
          catListSelect.addItem(thisCat.getId(), elementText);
        }
      }
    }
    return catListSelect;
  }


  /**
   *  Gets the selectedValue attribute of the CategoryList object
   *
   * @param  id  Description of the Parameter
   * @return     The selectedValue value
   */
  public String getSelectedValue(int id) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Category thisCategory = (Category) i.next();
      if (id == thisCategory.getId()) {
        return thisCategory.getDescription();
      }
    }
    return null;
  }
}

