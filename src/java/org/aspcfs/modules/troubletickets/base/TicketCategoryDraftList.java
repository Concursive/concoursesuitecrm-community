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
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * NOTE: This class is not dependent on tickets anymore and is used for working
 * on drafts for Category objects.
 *
 * @author akhi_m
 * @version $Id$
 * @created May 23, 2003
 */
public class TicketCategoryDraftList extends ArrayList {
  public final static String tableName = "ticket_category_draft";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  HtmlSelect catListSelect = new HtmlSelect();
  private PagedListInfo pagedListInfo = null;
  private int parentCode = -1;
  private int catLevel = -1;
  private String htmlJsEvent = "";
  private int enabledState = -1;
  private boolean topLevelOnly = false;
  private boolean buildHierarchy = false;
  private String noneLabel = null;
  private int siteId = -1;
  private boolean exclusiveToSite = false;


  /**
   * Constructor for the TicketCategoryDraftList object
   */
  public TicketCategoryDraftList() {
  }

  /**
   * Sets the lastAnchor attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Gets the tableName attribute of the TicketCategoryDraftList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the TicketCategoryDraftList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /**
   * Sets the PagedListInfo attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new PagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the HtmlJsEvent attribute of the TicketCategoryDraftList object
   *
   * @param htmlJsEvent The new htmlJsEvent value
   */
  public void setHtmlJsEvent(String htmlJsEvent) {
    this.htmlJsEvent = htmlJsEvent;
  }


  /**
   * Sets the CatListSelect attribute of the TicketCategoryDraftList object
   *
   * @param catListSelect The new CatListSelect value
   */
  public void setCatListSelect(HtmlSelect catListSelect) {
    this.catListSelect = catListSelect;
  }


  /**
   * Sets the ParentCode attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new ParentCode value
   */
  public void setParentCode(int tmp) {
    this.parentCode = tmp;
  }


  /**
   * Sets the ParentCode attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new ParentCode value
   */
  public void setParentCode(String tmp) {
    this.parentCode = Integer.parseInt(tmp);
  }


  /**
   * Sets the CatLevel attribute of the TicketCategoryDraftList object
   *
   * @param catLevel The new CatLevel value
   */
  public void setCatLevel(int catLevel) {
    this.catLevel = catLevel;
  }


  /**
   * Sets the CatLevel attribute of the TicketCategoryDraftList object
   *
   * @param catLevel The new CatLevel value
   */
  public void setCatLevel(String catLevel) {
    this.catLevel = Integer.parseInt(catLevel);
  }


  /**
   * Sets the enabledState attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new enabledState value
   */
  public void setEnabledState(int tmp) {
    this.enabledState = tmp;
  }


  /**
   * Sets the buildHierarchy attribute of the TicketCategoryDraftList object
   *
   * @param buildHierarchy The new buildHierarchy value
   */
  public void setBuildHierarchy(boolean buildHierarchy) {
    this.buildHierarchy = buildHierarchy;
  }


  /**
   * Sets the topLevelOnly attribute of the TicketCategoryDraftList object
   *
   * @param topLevelOnly The new topLevelOnly value
   */
  public void setTopLevelOnly(boolean topLevelOnly) {
    this.topLevelOnly = topLevelOnly;
  }


  /**
   * Gets the topLevelOnly attribute of the TicketCategoryDraftList object
   *
   * @return The topLevelOnly value
   */
  public boolean getTopLevelOnly() {
    return topLevelOnly;
  }


  /**
   * Gets the buildHierarchy attribute of the TicketCategoryDraftList object
   *
   * @return The buildHierarchy value
   */
  public boolean getBuildHierarchy() {
    return buildHierarchy;
  }


  /**
   * Gets the CatListSelect attribute of the TicketCategoryDraftList object
   *
   * @return The CatListSelect value
   */
  public HtmlSelect getCatListSelect() {
    return catListSelect;
  }


  /**
   * Gets the HtmlJsEvent attribute of the TicketCategoryDraftList object
   *
   * @return The HtmlJsEvent value
   */
  public String getHtmlJsEvent() {
    return htmlJsEvent;
  }


  /**
   * Gets the HtmlSelect attribute of the TicketCategoryDraftList object
   *
   * @param selectName Description of Parameter
   * @return The HtmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   * Gets the CatLevel attribute of the TicketCategoryDraftList object
   *
   * @return The CatLevel value
   */
  public int getCatLevel() {
    return catLevel;
  }


  /**
   * Gets the PagedListInfo attribute of the TicketCategoryDraftList object
   *
   * @return The PagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the ParentCode attribute of the TicketCategoryDraftList object
   *
   * @return The ParentCode value
   */
  public int getParentCode() {
    return parentCode;
  }


  /**
   * Gets the enabledState attribute of the TicketCategoryDraftList object
   *
   * @return The enabledState value
   */
  public int getEnabledState() {
    return enabledState;
  }


  /**
   * Gets the noneLabel attribute of the TicketCategoryDraftList object
   *
   * @return The noneLabel value
   */
  public String getNoneLabel() {
    return noneLabel;
  }


  /**
   * Sets the noneLabel attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new noneLabel value
   */
  public void setNoneLabel(String tmp) {
    this.noneLabel = tmp;
  }


  /**
   * Gets the siteId attribute of the TicketCategoryDraftList object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   * Sets the siteId attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   * Sets the siteId attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the exclusiveToSite attribute of the TicketCategoryDraftList object
   *
   * @return The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   * Sets the exclusiveToSite attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   * Sets the exclusiveToSite attribute of the TicketCategoryDraftList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the HtmlSelect attribute of the TicketCategoryDraftList object
   *
   * @param selectName Description of Parameter
   * @param defaultKey Description of Parameter
   * @return The HtmlSelect value
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
      if (noneLabel != null && !"".equals(noneLabel.trim())) {
        catListSelect.addItem(-1, noneLabel);
      } else {
        catListSelect.addItem(-1, "---------None---------");
      }
    }

    catListSelect.setJsEvent(this.getHtmlJsEvent());
    catListSelect.setBuilt(false);
    return catListSelect.getHtml(selectName, defaultKey);
  }


  /**
   * Description of the Method
   *
   * @param db        Description of Parameter
   * @param tableName Description of the Parameter
   * @throws SQLException Description of Exception
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
            "FROM " + DatabaseUtils.getTableName(db, tableName + "_draft") + " tc " +
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
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "tc.* " +
            "FROM " + DatabaseUtils.getTableName(db, tableName + "_draft") + " tc " +
            "WHERE tc.id > -1 ");
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
      TicketCategoryDraft thisCat = new TicketCategoryDraft(rs);
      thisCat.setBaseTableName(tableName);
      this.add(thisCat);
    }
    rs.close();
    pst.close();
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
    if (enabledState > -1) {
      sqlFilter.append("AND tc.enabled = ? ");
    }
    if (parentCode != -1) {
      sqlFilter.append("AND tc.parent_cat_code = ? ");
    }
    if (catLevel != -1) {
      sqlFilter.append("AND tc.cat_level = ? ");
    }
    if (siteId > -1) {
      sqlFilter.append("AND (tc.site_id = ? ");
      if (!exclusiveToSite) {
        sqlFilter.append("OR tc.site_id IS NULL ");
      }
      sqlFilter.append(") ");
    } else {
      sqlFilter.append("AND tc.site_id IS NULL ");
    }
    if (topLevelOnly) {
      sqlFilter.append("AND tc.parent_cat_code = 0 ");
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
    if (enabledState > -1) {
      pst.setBoolean(++i, enabledState == Constants.TRUE);
    }
    if (parentCode != -1) {
      pst.setInt(++i, parentCode);
    }
    if (catLevel != -1) {
      pst.setInt(++i, catLevel);
    }
    if (siteId > -1) {
      pst.setInt(++i, this.getSiteId());
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
   * Delete the draft entries.
   *
   * @param db            Description of the Parameter
   * @param baseTableName Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean deleteDraft(Connection db, String baseTableName, int siteId) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = null;
      if (baseTableName.equals("ticket_category")) {
        pst = db.prepareStatement(
            "DELETE FROM ticket_category_draft_plan_map " +
                "WHERE category_id IN (SELECT id FROM ticket_category_draft WHERE " + (siteId == -1 ? "site_id IS NULL" : "site_id = ?") + ") ");
        if (siteId > -1) {
          pst.setInt(1, siteId);
        }
        pst.execute();
        pst.close();
        // delete from draft assignment table contents
        pst = db.prepareStatement(
            "DELETE FROM " + DatabaseUtils.getTableName(db, "ticket_category_draft_assignment") + " " +
                "WHERE category_id IN (SELECT id FROM ticket_category_draft WHERE " + (siteId == -1 ? "site_id IS NULL" : "site_id = ?") + ") ");
        if (siteId > -1) {
          pst.setInt(1, siteId);
        }
        pst.execute();
        pst.close();
      }

      pst = db.prepareStatement(
          "DELETE from " + DatabaseUtils.getTableName(db, baseTableName + "_draft") + " " +
              "WHERE " + (siteId == -1 ? "site_id IS NULL" : "site_id = ?"));
      if (siteId > -1) {
        pst.setInt(1, siteId);
      }
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }
}

