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

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  NOTE: This class is not dependent on tickets anymore and is used for working
 *  on drafts for Category objects.
 *
 *@author     akhi_m
 *@created    May 23, 2003
 *@version    $id: exp$
 */
public class TicketCategoryDraftList extends ArrayList {
  HtmlSelect catListSelect = new HtmlSelect();
  private PagedListInfo pagedListInfo = null;
  private int parentCode = -1;
  private int catLevel = -1;
  private String htmlJsEvent = "";
  private int enabledState = -1;
  private boolean topLevelOnly = false;
  private boolean buildHierarchy = false;


  /**
   *  Constructor for the TicketCategoryDraftList object
   */
  public TicketCategoryDraftList() { }


  /**
   *  Sets the PagedListInfo attribute of the TicketCategoryDraftList object
   *
   *@param  tmp  The new PagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the HtmlJsEvent attribute of the TicketCategoryDraftList object
   *
   *@param  htmlJsEvent  The new htmlJsEvent value
   */
  public void setHtmlJsEvent(String htmlJsEvent) {
    this.htmlJsEvent = htmlJsEvent;
  }


  /**
   *  Sets the CatListSelect attribute of the TicketCategoryDraftList object
   *
   *@param  catListSelect  The new CatListSelect value
   */
  public void setCatListSelect(HtmlSelect catListSelect) {
    this.catListSelect = catListSelect;
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategoryDraftList object
   *
   *@param  tmp  The new ParentCode value
   */
  public void setParentCode(int tmp) {
    this.parentCode = tmp;
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategoryDraftList object
   *
   *@param  tmp  The new ParentCode value
   */
  public void setParentCode(String tmp) {
    this.parentCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the CatLevel attribute of the TicketCategoryDraftList object
   *
   *@param  catLevel  The new CatLevel value
   */
  public void setCatLevel(int catLevel) {
    this.catLevel = catLevel;
  }


  /**
   *  Sets the CatLevel attribute of the TicketCategoryDraftList object
   *
   *@param  catLevel  The new CatLevel value
   */
  public void setCatLevel(String catLevel) {
    this.catLevel = Integer.parseInt(catLevel);
  }


  /**
   *  Sets the enabledState attribute of the TicketCategoryDraftList object
   *
   *@param  tmp  The new enabledState value
   */
  public void setEnabledState(int tmp) {
    this.enabledState = tmp;
  }


  /**
   *  Sets the buildHierarchy attribute of the TicketCategoryDraftList object
   *
   *@param  buildHierarchy  The new buildHierarchy value
   */
  public void setBuildHierarchy(boolean buildHierarchy) {
    this.buildHierarchy = buildHierarchy;
  }


  /**
   *  Sets the topLevelOnly attribute of the TicketCategoryDraftList object
   *
   *@param  topLevelOnly  The new topLevelOnly value
   */
  public void setTopLevelOnly(boolean topLevelOnly) {
    this.topLevelOnly = topLevelOnly;
  }


  /**
   *  Gets the topLevelOnly attribute of the TicketCategoryDraftList object
   *
   *@return    The topLevelOnly value
   */
  public boolean getTopLevelOnly() {
    return topLevelOnly;
  }


  /**
   *  Gets the buildHierarchy attribute of the TicketCategoryDraftList object
   *
   *@return    The buildHierarchy value
   */
  public boolean getBuildHierarchy() {
    return buildHierarchy;
  }


  /**
   *  Gets the CatListSelect attribute of the TicketCategoryDraftList object
   *
   *@return    The CatListSelect value
   */
  public HtmlSelect getCatListSelect() {
    return catListSelect;
  }


  /**
   *  Gets the HtmlJsEvent attribute of the TicketCategoryDraftList object
   *
   *@return    The HtmlJsEvent value
   */
  public String getHtmlJsEvent() {
    return htmlJsEvent;
  }


  /**
   *  Gets the HtmlSelect attribute of the TicketCategoryDraftList object
   *
   *@param  selectName  Description of Parameter
   *@return             The HtmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the CatLevel attribute of the TicketCategoryDraftList object
   *
   *@return    The CatLevel value
   */
  public int getCatLevel() {
    return catLevel;
  }


  /**
   *  Gets the PagedListInfo attribute of the TicketCategoryDraftList object
   *
   *@return    The PagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the ParentCode attribute of the TicketCategoryDraftList object
   *
   *@return    The ParentCode value
   */
  public int getParentCode() {
    return parentCode;
  }


  /**
   *  Gets the enabledState attribute of the TicketCategoryDraftList object
   *
   *@return    The enabledState value
   */
  public int getEnabledState() {
    return enabledState;
  }


  /**
   *  Gets the HtmlSelect attribute of the TicketCategoryDraftList object
   *
   *@param  selectName  Description of Parameter
   *@param  defaultKey  Description of Parameter
   *@return             The HtmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    Iterator i = this.iterator();
    catListSelect.clear();
    catListSelect.addAttribute("id", selectName);

    if (i.hasNext()) {
      while (i.hasNext()) {
        TicketCategoryDraft thisCat = (TicketCategoryDraft) i.next();
        String elementText = thisCat.getDescription();
        HashMap colorAttribute = new HashMap();
        if (!(thisCat.getEnabled())) {
          colorAttribute.put("style", "color: red");
        } else if (thisCat.getActualCatId() == -1) {
          colorAttribute.put("style", "color: blue");
        }
        catListSelect.addItem(
            thisCat.getId(),
            elementText, colorAttribute);
      }
    } else {
      catListSelect.addItem(-1, "---------None---------");
    }

    catListSelect.setJsEvent(this.getHtmlJsEvent());
    catListSelect.setBuilt(false);
    return catListSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  tableName         Description of the Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildList(Connection db, String tableName) throws SQLException {
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
        "FROM " + tableName + "_draft tc " +
        "WHERE tc.id > -1 ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() +
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
        pst = db.prepareStatement(sqlCount.toString() +
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
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "tc.* " +
        "FROM " + tableName + "_draft tc " +
        "WHERE tc.id > -1 ");
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
      TicketCategoryDraft thisCat = new TicketCategoryDraft(rs);
      this.add(thisCat);
    }
    rs.close();
    pst.close();
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
    if (enabledState > -1) {
      sqlFilter.append("AND tc.enabled = ? ");
    }
    if (parentCode != -1) {
      sqlFilter.append("AND tc.parent_cat_code = ? ");
    }
    if (catLevel != -1) {
      sqlFilter.append("AND tc.cat_level = ? ");
    }

    if (topLevelOnly) {
      sqlFilter.append("AND tc.parent_cat_code = 0 ");
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
    if (enabledState > -1) {
      pst.setBoolean(++i, enabledState == Constants.TRUE);
    }
    if (parentCode != -1) {
      pst.setInt(++i, parentCode);
    }
    if (catLevel != -1) {
      pst.setInt(++i, catLevel);
    }
    return i;
  }


  /**
   *  Delete the draft entries.
   *
   *@param  db                Description of the Parameter
   *@param  baseTableName     Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static boolean deleteDraft(Connection db, String baseTableName) throws SQLException {
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "DELETE from " + baseTableName + "_draft ");
      pst.execute();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }
}

