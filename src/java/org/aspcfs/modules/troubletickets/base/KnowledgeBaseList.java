/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
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
 * Description of the Class
 *
 * @author partha
 * @version $Id: KnowledgeBaseList.java,v 1.1.4.1 2005/10/14 21:12:41
 *          mrajkowski Exp $
 * @created October 6, 2005
 */
public class KnowledgeBaseList extends ArrayList {
  //fields
  private PagedListInfo pagedListInfo = null;
  protected int id = -1;
  protected int categoryId = -1;
  protected String title = null;
  protected String description = null;
  protected int itemId = -1;
  protected int siteId = -1;
  protected boolean exclusiveToSite = false;

  public final static String tableName = "knowledge_base";
  public final static String uniqueField = "kb_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;


  //related records
  protected boolean buildResources = false;

  //other records
  private int catCode = 0;
  private int subCat1 = 0;
  private int subCat2 = 0;
  private int subCat3 = 0;


  /**
   * Constructor for the KnowledgeBaseList object
   */
  public KnowledgeBaseList() {
  }

  /**
   * Sets the lastAnchor attribute of the KnowledgeBaseList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the KnowledgeBaseList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the KnowledgeBaseList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the KnowledgeBaseList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the KnowledgeBaseList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Gets the tableName attribute of the KnowledgeBaseList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the KnowledgeBaseList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
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
    //Build a base SQL statement for counting records
    sqlCount.append(
        " SELECT COUNT(*) AS recordcount " +
            " FROM knowledge_base kb " +
            " LEFT JOIN ticket_category tc ON (kb.category_id = tc.id) " +
            " WHERE kb.kb_id > -1 ");
    createFilter(sqlFilter, db);
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

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
                sqlFilter.toString() +
                "AND " + DatabaseUtils.toLowerCase(db) + "(kb.title) < ?  ");
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
      pagedListInfo.setDefaultSort("kb.title", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY kb.entered ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " kb.* " +
            " FROM knowledge_base kb " +
            " LEFT JOIN ticket_category tc ON (kb.category_id = tc.id) " +
            " WHERE kb.kb_id > -1 ");

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
      KnowledgeBase thisKb = new KnowledgeBase(rs);
      this.add(thisKb);
    }
    rs.close();
    pst.close();

    if (buildResources) {
      Iterator iter = (Iterator) this.iterator();
      while (iter.hasNext()) {
        KnowledgeBase kb = (KnowledgeBase) iter.next();
        kb.setBuildResources(true);
        kb.buildResources(db);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter, Connection db) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (subCat3 > 0) {
      categoryId = subCat3;
    } else if (subCat2 > 0) {
      categoryId = subCat2;
    } else if (subCat1 > 0) {
      categoryId = subCat1;
    } else if (catCode > 0) {
      categoryId = catCode;
    }
    if (id > -1) {
      sqlFilter.append("AND kb.kb_id = ? ");
    }
    if (categoryId > -1) {
      sqlFilter.append("AND kb.category_id = ? ");
    }
    if (title != null) {
      sqlFilter.append("AND kb.title LIKE ? ");
    }
    if (description != null) {
      sqlFilter.append("AND kb.desctiption LIKE ? ");
    }
    if (itemId != -1) {
      sqlFilter.append("AND kb.item_id = ? ");
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
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    if (title != null) {
      pst.setString(++i, title);
    }
    if (description != null) {
      pst.setString(++i, description);
    }
    if (itemId != -1) {
      pst.setInt(++i, itemId);
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
   * Gets the hashMap attribute of the KnowledgeBaseList object
   *
   * @return The hashMap value
   */
  public HashMap getHashMap() {
    HashMap map = new HashMap();
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      KnowledgeBase kb = (KnowledgeBase) iter.next();
      map.put(String.valueOf(kb.getId()), new Integer(kb.getCategoryId()));
    }
    return map;
  }


  /**
   * Gets the htmlSelectObj attribute of the KnowledgeBaseList object
   *
   * @param selectedKey Description of the Parameter
   * @return The htmlSelectObj value
   */
  public HtmlSelect getHtmlSelectObj(int selectedKey) {
    HtmlSelect kbListSelect = new HtmlSelect();
    Iterator iter = this.iterator();
    while (iter.hasNext()) {
      KnowledgeBase thisKb = (KnowledgeBase) iter.next();
      kbListSelect.addItem(thisKb.getId(), thisKb.getTitle());
    }
    return kbListSelect;
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param baseFilePath Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    Iterator iter = this.iterator();
    while (iter.hasNext()) {
      KnowledgeBase thisKb = (KnowledgeBase) iter.next();
      thisKb.delete(db, baseFilePath);
      iter.remove();
    }
    return true;
  }


  /**
   * Gets the pagedListInfo attribute of the KnowledgeBaseList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Sets the pagedListInfo attribute of the KnowledgeBaseList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the id attribute of the KnowledgeBaseList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the KnowledgeBaseList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the KnowledgeBaseList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the categoryId attribute of the KnowledgeBaseList object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the categoryId attribute of the KnowledgeBaseList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the KnowledgeBaseList object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Gets the title attribute of the KnowledgeBaseList object
   *
   * @return The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   * Sets the title attribute of the KnowledgeBaseList object
   *
   * @param tmp The new title value
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   * Gets the description attribute of the KnowledgeBaseList object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Sets the description attribute of the KnowledgeBaseList object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Gets the itemId attribute of the KnowledgeBaseList object
   *
   * @return The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   * Sets the itemId attribute of the KnowledgeBaseList object
   *
   * @param tmp The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   * Sets the itemId attribute of the KnowledgeBaseList object
   *
   * @param tmp The new itemId value
   */
  public void setItemId(String tmp) {
    this.itemId = Integer.parseInt(tmp);
  }


  /**
   * Gets the buildResources attribute of the KnowledgeBaseList object
   *
   * @return The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   * Sets the buildResources attribute of the KnowledgeBaseList object
   *
   * @param tmp The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   * Sets the buildResources attribute of the KnowledgeBaseList object
   *
   * @param tmp The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the catCode attribute of the KnowledgeBaseList object
   *
   * @return The catCode value
   */
  public int getCatCode() {
    return catCode;
  }


  /**
   * Sets the catCode attribute of the KnowledgeBaseList object
   *
   * @param tmp The new catCode value
   */
  public void setCatCode(int tmp) {
    this.catCode = tmp;
  }


  /**
   * Sets the catCode attribute of the KnowledgeBaseList object
   *
   * @param tmp The new catCode value
   */
  public void setCatCode(String tmp) {
    this.catCode = Integer.parseInt(tmp);
  }


  /**
   * Gets the subCat1 attribute of the KnowledgeBaseList object
   *
   * @return The subCat1 value
   */
  public int getSubCat1() {
    return subCat1;
  }


  /**
   * Sets the subCat1 attribute of the KnowledgeBaseList object
   *
   * @param tmp The new subCat1 value
   */
  public void setSubCat1(int tmp) {
    this.subCat1 = tmp;
  }


  /**
   * Sets the subCat1 attribute of the KnowledgeBaseList object
   *
   * @param tmp The new subCat1 value
   */
  public void setSubCat1(String tmp) {
    this.subCat1 = Integer.parseInt(tmp);
  }


  /**
   * Gets the subCat2 attribute of the KnowledgeBaseList object
   *
   * @return The subCat2 value
   */
  public int getSubCat2() {
    return subCat2;
  }


  /**
   * Sets the subCat2 attribute of the KnowledgeBaseList object
   *
   * @param tmp The new subCat2 value
   */
  public void setSubCat2(int tmp) {
    this.subCat2 = tmp;
  }


  /**
   * Sets the subCat2 attribute of the KnowledgeBaseList object
   *
   * @param tmp The new subCat2 value
   */
  public void setSubCat2(String tmp) {
    this.subCat2 = Integer.parseInt(tmp);
  }


  /**
   * Gets the subCat3 attribute of the KnowledgeBaseList object
   *
   * @return The subCat3 value
   */
  public int getSubCat3() {
    return subCat3;
  }


  /**
   * Sets the subCat3 attribute of the KnowledgeBaseList object
   *
   * @param tmp The new subCat3 value
   */
  public void setSubCat3(int tmp) {
    this.subCat3 = tmp;
  }


  /**
   * Sets the subCat3 attribute of the KnowledgeBaseList object
   *
   * @param tmp The new subCat3 value
   */
  public void setSubCat3(String tmp) {
    this.subCat3 = Integer.parseInt(tmp);
  }


  /**
   * Gets the siteId attribute of the KnowledgeBaseList object
   *
   * @return The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   * Sets the siteId attribute of the KnowledgeBaseList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   * Sets the siteId attribute of the KnowledgeBaseList object
   *
   * @param tmp The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   * Gets the exclusiveToSite attribute of the KnowledgeBaseList object
   *
   * @return The exclusiveToSite value
   */
  public boolean getExclusiveToSite() {
    return exclusiveToSite;
  }


  /**
   * Sets the exclusiveToSite attribute of the KnowledgeBaseList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(boolean tmp) {
    this.exclusiveToSite = tmp;
  }


  /**
   * Sets the exclusiveToSite attribute of the KnowledgeBaseList object
   *
   * @param tmp The new exclusiveToSite value
   */
  public void setExclusiveToSite(String tmp) {
    this.exclusiveToSite = DatabaseUtils.parseBoolean(tmp);
  }
}

