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
package org.aspcfs.modules.actionplans.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id: ActionPlanCategoryList.java,v 1.1.2.2 2005/10/14 21:12:39
 *          mrajkowski Exp $
 * @created September 6, 2005
 */
public class ActionPlanCategoryList extends Vector {
  HtmlSelect catListSelect = new HtmlSelect();
  private PagedListInfo pagedListInfo = null;
  private int parentCode = -1;
  private int catLevel = -1;
  private String HtmlJsEvent = "";
  private int enabledState = -1;
  private boolean includeDisabled = false;
  private int siteId = -1;
  private boolean exclusiveToSite = false;

  public final static String tableName = "action_plan_category";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  /**
   * Constructor for the ActionPlanCategoryList object
   */
  public ActionPlanCategoryList() {
  }

  /**
   * Sets the lastAnchor attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Gets the tableName attribute of the ActionPlanCategoryList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionPlanCategoryList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the htmlSelect attribute of the ActionPlanCategoryList object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    Iterator i = this.iterator();
    catListSelect.addAttribute("id", selectName);
    while (i.hasNext()) {
      ActionPlanCategory thisCat = (ActionPlanCategory) i.next();
      String elementText = thisCat.getDescription();
      if (thisCat.getEnabled()) {
        catListSelect.addItem(
            thisCat.getId(),
            elementText);
      } else
      if ((!(thisCat.getEnabled()) && thisCat.getId() == defaultKey) || includeDisabled) {
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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
        "SELECT COUNT(*) AS recordcount " +
            "FROM action_plan_category apc " +
            "WHERE apc.id > -1 ");

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
                "AND apc.id < ? ");
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
      pagedListInfo.setDefaultSort("apc.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY apc.description");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "apc.* " +
            "FROM action_plan_category apc " +
            "WHERE apc.id > -1 ");
    pst = db.prepareStatement(new String(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString()));
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      ActionPlanCategory thisCat = new ActionPlanCategory(rs);
      this.addElement(thisCat);
    }
    rs.close();
    pst.close();
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
    if (enabledState != -1) {
      sqlFilter.append("AND apc.enabled = ? ");
    }
    if (parentCode != -1) {
      sqlFilter.append("AND apc.parent_cat_code = ? ");
    }
    if (catLevel != -1) {
      sqlFilter.append("AND apc.cat_level = ? ");
    }
    if (siteId > -1) {
      sqlFilter.append("AND (apc.site_id = ? ");
      if (!exclusiveToSite) {
        sqlFilter.append("OR apc.site_id IS NULL ");
      }
      sqlFilter.append(") ");
    } else {
      sqlFilter.append("AND apc.site_id IS NULL ");
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
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
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
   * Gets the htmlSelect attribute of the ActionPlanCategoryList object
   *
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public HtmlSelect getHtmlSelect(int defaultKey) {
    HtmlSelect catListSelect = new HtmlSelect();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlanCategory thisCat = (ActionPlanCategory) i.next();
      String elementText = thisCat.getDescription();
      if (thisCat.getEnabled()) {
        catListSelect.addItem(thisCat.getId(), elementText);
      } else
      if ((!thisCat.getEnabled() && thisCat.getId() == defaultKey) || includeDisabled) {
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
   * Gets the idFromValue attribute of the ActionPlanCategoryList object
   *
   * @param value Description of the Parameter
   * @return The idFromValue value
   */
  public int getIdFromValue(String value) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ActionPlanCategory thisCategory = (ActionPlanCategory) i.next();
      if (value.equals(thisCategory.getDescription())) {
        return thisCategory.getId();
      }
    }
    return -1;
  }


  /*
   *  Get and Set methods
   */
  /**
   * Gets the catListSelect attribute of the ActionPlanCategoryList object
   *
   * @return The catListSelect value
   */
  public HtmlSelect getCatListSelect() {
    return catListSelect;
  }


  /**
   * Sets the catListSelect attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new catListSelect value
   */
  public void setCatListSelect(HtmlSelect tmp) {
    this.catListSelect = tmp;
  }


  /**
   * Gets the pagedListInfo attribute of the ActionPlanCategoryList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the parentCode attribute of the ActionPlanCategoryList object
   *
   * @return The parentCode value
   */
  public int getParentCode() {
    return parentCode;
  }


  /**
   * Sets the parentCode attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new parentCode value
   */
  public void setParentCode(int tmp) {
    this.parentCode = tmp;
  }


  /**
   * Sets the parentCode attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new parentCode value
   */
  public void setParentCode(String tmp) {
    this.parentCode = Integer.parseInt(tmp);
  }


  /**
   * Gets the catLevel attribute of the ActionPlanCategoryList object
   *
   * @return The catLevel value
   */
  public int getCatLevel() {
    return catLevel;
  }


  /**
   * Sets the catLevel attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new catLevel value
   */
  public void setCatLevel(int tmp) {
    this.catLevel = tmp;
  }


  /**
   * Sets the catLevel attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new catLevel value
   */
  public void setCatLevel(String tmp) {
    this.catLevel = Integer.parseInt(tmp);
  }


  /**
   * Gets the htmlJsEvent attribute of the ActionPlanCategoryList object
   *
   * @return The htmlJsEvent value
   */
  public String getHtmlJsEvent() {
    return HtmlJsEvent;
  }


  /**
   * Sets the htmlJsEvent attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new htmlJsEvent value
   */
  public void setHtmlJsEvent(String tmp) {
    this.HtmlJsEvent = tmp;
  }


  /**
   * Gets the enabledState attribute of the ActionPlanCategoryList object
   *
   * @return The enabledState value
   */
  public int getEnabledState() {
    return enabledState;
  }


  /**
   * Sets the enabledState attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new enabledState value
   */
  public void setEnabledState(int tmp) {
    this.enabledState = tmp;
  }


  /**
   * Sets the enabledState attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new enabledState value
   */
  public void setEnabledState(String tmp) {
    this.enabledState = Integer.parseInt(tmp);
  }


  /**
   * Gets the includeDisabled attribute of the ActionPlanCategoryList object
   *
   * @return The includeDisabled value
   */
  public boolean getIncludeDisabled() {
    return includeDisabled;
  }


  /**
   * Sets the includeDisabled attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new includeDisabled value
   */
  public void setIncludeDisabled(boolean tmp) {
    this.includeDisabled = tmp;
  }


  /**
   * Sets the includeDisabled attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new includeDisabled value
   */
  public void setIncludeDisabled(String tmp) {
    this.includeDisabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the siteId attribute of the ActionPlanCategoryList object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   * Sets the siteId attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   * Sets the siteId attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the exclusiveToSite attribute of the ActionPlanCategoryList object
   *
   * @return The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   * Sets the exclusiveToSite attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   * Sets the exclusiveToSite attribute of the ActionPlanCategoryList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }

}

