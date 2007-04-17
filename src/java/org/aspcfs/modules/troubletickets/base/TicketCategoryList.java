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
package org.aspcfs.modules.troubletickets.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 *  Contains TicketCategory items for displaying to the user
 *
 * @author     chris
 * @created    December 11, 2001
 * @version    $Id: TicketCategoryList.java,v 1.2 2002/03/25 19:12:22 mrajkowski
 *      Exp $
 */
public class TicketCategoryList extends Vector implements SyncableList {

  public final static String tableName = "ticket_category";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  HtmlSelect catListSelect = new HtmlSelect();
  private PagedListInfo pagedListInfo = null;
  private int parentCode = -1;
  private int catLevel = -1;
  private String HtmlJsEvent = "";
  private int enabledState = -1;
  private boolean includeDisabled = false;
  private int siteId = -1;
  private boolean exclusiveToSite = false;
  private boolean includeAllSites = false;


  /**
   *  Constructor for the TicketCategoryList object
   */
  public TicketCategoryList() { }

  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static TicketCategory getObject(ResultSet rs) throws SQLException {
    TicketCategory ticketCategory = new TicketCategory(rs);
    return ticketCategory;
  }

  /**
   *  Sets the PagedListInfo attribute of the TicketCategoryList object
   *
   * @param  tmp  The new PagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
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
   *  Sets the HtmlJsEvent attribute of the TicketCategoryList object
   *
   * @param  HtmlJsEvent  The new HtmlJsEvent value
   */
  public void setHtmlJsEvent(String HtmlJsEvent) {
    this.HtmlJsEvent = HtmlJsEvent;
  }


  /**
   *  Sets the CatListSelect attribute of the TicketCategoryList object
   *
   * @param  catListSelect  The new CatListSelect value
   */
  public void setCatListSelect(HtmlSelect catListSelect) {
    this.catListSelect = catListSelect;
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategoryList object
   *
   * @param  tmp  The new ParentCode value
   */
  public void setParentCode(int tmp) {
    this.parentCode = tmp;
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategoryList object
   *
   * @param  tmp  The new ParentCode value
   */
  public void setParentCode(String tmp) {
    this.parentCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the CatLevel attribute of the TicketCategoryList object
   *
   * @param  catLevel  The new CatLevel value
   */
  public void setCatLevel(int catLevel) {
    this.catLevel = catLevel;
  }


  /**
   *  Sets the CatLevel attribute of the TicketCategoryList object
   *
   * @param  catLevel  The new CatLevel value
   */
  public void setCatLevel(String catLevel) {
    this.catLevel = Integer.parseInt(catLevel);
  }


  /**
   *  Sets the enabledState attribute of the TicketCategoryList object
   *
   * @param  tmp  The new enabledState value
   */
  public void setEnabledState(int tmp) {
    this.enabledState = tmp;
  }


  /**
   *  Sets the includeDisabled attribute of the TicketCategoryList object
   *
   * @param  includeDisabled  The new includeDisabled value
   */
  public void setIncludeDisabled(boolean includeDisabled) {
    this.includeDisabled = includeDisabled;
  }


  /**
   *  Gets the includeDisabled attribute of the TicketCategoryList object
   *
   * @return    The includeDisabled value
   */
  public boolean getIncludeDisabled() {
    return includeDisabled;
  }


  /**
   *  Gets the CatListSelect attribute of the TicketCategoryList object
   *
   * @return    The CatListSelect value
   */
  public HtmlSelect getCatListSelect() {
    return catListSelect;
  }


  /**
   *  Gets the HtmlJsEvent attribute of the TicketCategoryList object
   *
   * @return    The HtmlJsEvent value
   */
  public String getHtmlJsEvent() {
    return HtmlJsEvent;
  }


  /**
   *  Gets the HtmlSelect attribute of the TicketCategoryList object
   *
   * @param  selectName  Description of Parameter
   * @return             The HtmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the CatLevel attribute of the TicketCategoryList object
   *
   * @return    The CatLevel value
   */
  public int getCatLevel() {
    return catLevel;
  }


  /**
   *  Gets the PagedListInfo attribute of the TicketCategoryList object
   *
   * @return    The PagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the ParentCode attribute of the TicketCategoryList object
   *
   * @return    The ParentCode value
   */
  public int getParentCode() {
    return parentCode;
  }


  /**
   *  Gets the enabledState attribute of the TicketCategoryList object
   *
   * @return    The enabledState value
   */
  public int getEnabledState() {
    return enabledState;
  }


  /**
   *  Gets the siteId attribute of the TicketCategoryList object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the siteId attribute of the TicketCategoryList object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the TicketCategoryList object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the exclusiveToSite attribute of the TicketCategoryList object
   *
   * @return    The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   *  Sets the exclusiveToSite attribute of the TicketCategoryList object
   *
   * @param  tmp  The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   *  Sets the exclusiveToSite attribute of the TicketCategoryList object
   *
   * @param  tmp  The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the includeAllSites attribute of the TicketCategoryList object
   *
   * @return    The includeAllSites value
   */
  public boolean getIncludeAllSites() {
    return includeAllSites;
  }


  /**
   *  Sets the includeAllSites attribute of the TicketCategoryList object
   *
   * @param  tmp  The new includeAllSites value
   */
  public void setIncludeAllSites(boolean tmp) {
    this.includeAllSites = tmp;
  }


  /**
   *  Sets the includeAllSites attribute of the TicketCategoryList object
   *
   * @param  tmp  The new includeAllSites value
   */
  public void setIncludeAllSites(String tmp) {
    this.includeAllSites = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the htmlSelect attribute of the TicketCategoryList object
   *
   * @param  selectName  Description of the Parameter
   * @param  defaultKey  Description of the Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, String defaultKey) {
    if (defaultKey != null && !"".equals(defaultKey.trim())) {
      return getHtmlSelect(selectName, Integer.parseInt(defaultKey));
    }
    return getHtmlSelect(selectName);
  }


  /**
   *  Gets the HtmlSelect attribute of the TicketCategoryList object
   *
   * @param  selectName  Description of Parameter
   * @param  defaultKey  Description of Parameter
   * @return             The HtmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    Iterator i = this.iterator();
    catListSelect.addAttribute("id", selectName);
    while (i.hasNext()) {
      TicketCategory thisCat = (TicketCategory) i.next();
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
   * Description of the Method
   *
   * @param db
   * @param pst
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
   * @param pst
   * @param sqlFilter
   * @param sqlOrder
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();

    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "tc.* " +
        "FROM ticket_category tc " +
        "WHERE tc.id > -1 ");
    if(sqlFilter == null || sqlFilter.length() == 0){
      StringBuffer buff = new StringBuffer();
      createFilter(buff);
      sqlFilter = buff.toString();
    }
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter + sqlOrder);
    prepareFilter(pst);
    return pst;
  }
  
  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
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
        "SELECT COUNT(*) AS recordcount " +
        "FROM ticket_category tc " +
        "WHERE tc.id > -1 ");

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
            "AND tc.id < ? ");
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
      pagedListInfo.setDefaultSort("tc.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY tc.description");
    }

    //Need to build a base SQL statement for returning records
    pst = prepareList(db, sqlFilter.toString(), sqlOrder.toString());
    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      TicketCategory thisCat = new TicketCategory(rs);
      this.addElement(thisCat);
    }
    rs.close();
    if(pst != null){
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (enabledState != -1) {
      sqlFilter.append("AND tc.enabled = ? ");
    }
    if (parentCode != -1) {
      sqlFilter.append("AND tc.parent_cat_code = ? ");
    }
    if (catLevel != -1) {
      sqlFilter.append("AND tc.cat_level = ? ");
    }
    if (!includeAllSites) {
      if (siteId > -1) {
        sqlFilter.append("AND (tc.site_id = ? ");
        if (!exclusiveToSite) {
          sqlFilter.append("OR tc.site_id IS NULL ");
        }
        sqlFilter.append(")");
      } else {
        sqlFilter.append("AND tc.site_id IS NULL ");
      }
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND tc.entered > ? ");
      }
      sqlFilter.append("AND tc.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND tc.modified > ? ");
      sqlFilter.append("AND tc.entered < ? ");
      sqlFilter.append("AND tc.modified < ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst            Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
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
   *  Returns just an HtmlSelect object without generating the Html output
   *
   * @param  defaultKey  Description of the Parameter
   * @return             The htmlSelect value
   */
  public HtmlSelect getHtmlSelect(int defaultKey) {
    HtmlSelect catListSelect = new HtmlSelect();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      TicketCategory thisCat = (TicketCategory) i.next();
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
   *  Gets the idFromValue attribute of the TicketCategoryList object
   *
   * @param  value  Description of the Parameter
   * @return        The idFromValue value
   */
  public int getIdFromValue(String value) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      TicketCategory thisCategory = (TicketCategory) i.next();
      if (value.equals(thisCategory.getDescription())) {
        return thisCategory.getId();
      }
    }
    return -1;
  }


  /**
   *  Gets the valueFromId attribute of the TicketCategoryList object
   *
   * @param  id  Description of the Parameter
   * @return     The valueFromId value
   */
  public String getValueFromId(int id) {
    String result = null;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      TicketCategory thisCategory = (TicketCategory) i.next();
      if (id == thisCategory.getId()) {
        result = thisCategory.getDescription() + (!thisCategory.getEnabled() ? "*" : "");
        break;
      }
    }
    return result;
  }


  /**
   *  Gets the enabled attribute of the TicketCategoryList object
   *
   * @param  id  Description of the Parameter
   * @return     The enabled value
   */
  public boolean isEnabled(int id) {
    boolean result = false;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      TicketCategory thisCategory = (TicketCategory) i.next();
      if (id == thisCategory.getId()) {
        result = thisCategory.getEnabled();
        break;
      }
    }
    return result;
  }
}

