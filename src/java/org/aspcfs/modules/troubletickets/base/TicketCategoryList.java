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
 * Contains TicketCategory items for displaying to the user
 *
 * @author chris
 * @version $Id: TicketCategoryList.java,v 1.2 2002/03/25 19:12:22 mrajkowski
 *          Exp $
 * @created December 11, 2001
 */
public class TicketCategoryList extends Vector {
  HtmlSelect catListSelect = new HtmlSelect();
  private PagedListInfo pagedListInfo = null;
  private int parentCode = -1;
  private int catLevel = -1;
  private String HtmlJsEvent = "";
  private int enabledState = -1;
  private boolean includeDisabled = false;


  /**
   * Constructor for the TicketCategoryList object
   */
  public TicketCategoryList() {
  }


  /**
   * Sets the PagedListInfo attribute of the TicketCategoryList object
   *
   * @param tmp The new PagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the HtmlJsEvent attribute of the TicketCategoryList object
   *
   * @param HtmlJsEvent The new HtmlJsEvent value
   */
  public void setHtmlJsEvent(String HtmlJsEvent) {
    this.HtmlJsEvent = HtmlJsEvent;
  }


  /**
   * Sets the CatListSelect attribute of the TicketCategoryList object
   *
   * @param catListSelect The new CatListSelect value
   */
  public void setCatListSelect(HtmlSelect catListSelect) {
    this.catListSelect = catListSelect;
  }


  /**
   * Sets the ParentCode attribute of the TicketCategoryList object
   *
   * @param tmp The new ParentCode value
   */
  public void setParentCode(int tmp) {
    this.parentCode = tmp;
  }


  /**
   * Sets the ParentCode attribute of the TicketCategoryList object
   *
   * @param tmp The new ParentCode value
   */
  public void setParentCode(String tmp) {
    this.parentCode = Integer.parseInt(tmp);
  }


  /**
   * Sets the CatLevel attribute of the TicketCategoryList object
   *
   * @param catLevel The new CatLevel value
   */
  public void setCatLevel(int catLevel) {
    this.catLevel = catLevel;
  }


  /**
   * Sets the CatLevel attribute of the TicketCategoryList object
   *
   * @param catLevel The new CatLevel value
   */
  public void setCatLevel(String catLevel) {
    this.catLevel = Integer.parseInt(catLevel);
  }


  /**
   * Sets the enabledState attribute of the TicketCategoryList object
   *
   * @param tmp The new enabledState value
   */
  public void setEnabledState(int tmp) {
    this.enabledState = tmp;
  }


  /**
   * Sets the includeDisabled attribute of the TicketCategoryList object
   *
   * @param includeDisabled The new includeDisabled value
   */
  public void setIncludeDisabled(boolean includeDisabled) {
    this.includeDisabled = includeDisabled;
  }


  /**
   * Gets the includeDisabled attribute of the TicketCategoryList object
   *
   * @return The includeDisabled value
   */
  public boolean getIncludeDisabled() {
    return includeDisabled;
  }


  /**
   * Gets the CatListSelect attribute of the TicketCategoryList object
   *
   * @return The CatListSelect value
   */
  public HtmlSelect getCatListSelect() {
    return catListSelect;
  }


  /**
   * Gets the HtmlJsEvent attribute of the TicketCategoryList object
   *
   * @return The HtmlJsEvent value
   */
  public String getHtmlJsEvent() {
    return HtmlJsEvent;
  }


  /**
   * Gets the HtmlSelect attribute of the TicketCategoryList object
   *
   * @param selectName Description of Parameter
   * @return The HtmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   * Gets the CatLevel attribute of the TicketCategoryList object
   *
   * @return The CatLevel value
   */
  public int getCatLevel() {
    return catLevel;
  }


  /**
   * Gets the PagedListInfo attribute of the TicketCategoryList object
   *
   * @return The PagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the ParentCode attribute of the TicketCategoryList object
   *
   * @return The ParentCode value
   */
  public int getParentCode() {
    return parentCode;
  }


  /**
   * Gets the enabledState attribute of the TicketCategoryList object
   *
   * @return The enabledState value
   */
  public int getEnabledState() {
    return enabledState;
  }


  /**
   * Gets the HtmlSelect attribute of the TicketCategoryList object
   *
   * @param selectName Description of Parameter
   * @param defaultKey Description of Parameter
   * @return The HtmlSelect value
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
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
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
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "tc.* " +
        "FROM ticket_category tc " +
        "WHERE tc.id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();

    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      TicketCategory thisCat = new TicketCategory(rs);
      this.addElement(thisCat);
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
    if (enabledState != -1) {
      sqlFilter.append("AND tc.enabled = ? ");
    }
    if (parentCode != -1) {
      sqlFilter.append("AND tc.parent_cat_code = ? ");
    }
    if (catLevel != -1) {
      sqlFilter.append("AND tc.cat_level = ? ");
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
    return i;
  }


  /**
   * Returns just an HtmlSelect object without generating the Html output
   *
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
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
   * Gets the idFromValue attribute of the TicketCategoryList object
   *
   * @param value Description of the Parameter
   * @return The idFromValue value
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
}

