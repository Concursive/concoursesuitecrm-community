//Copyright 2002-2003 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  Description of the Class
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
  private String HtmlJsEvent = "";
  private int enabledState = Constants.TRUE;
  private boolean topLevelOnly = false;
  private boolean buildHierarchy = false;


  /**
   *  Constructor for the TicketCategoryDraftList object
   *
   *@since
   */
  public TicketCategoryDraftList() { }


  /**
   *  Sets the PagedListInfo attribute of the TicketCategoryDraftList object
   *
   *@param  tmp  The new PagedListInfo value
   *@since
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the HtmlJsEvent attribute of the TicketCategoryDraftList object
   *
   *@param  HtmlJsEvent  The new HtmlJsEvent value
   *@since
   */
  public void setHtmlJsEvent(String HtmlJsEvent) {
    this.HtmlJsEvent = HtmlJsEvent;
  }


  /**
   *  Sets the CatListSelect attribute of the TicketCategoryDraftList object
   *
   *@param  catListSelect  The new CatListSelect value
   *@since
   */
  public void setCatListSelect(HtmlSelect catListSelect) {
    this.catListSelect = catListSelect;
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategoryDraftList object
   *
   *@param  tmp  The new ParentCode value
   *@since
   */
  public void setParentCode(int tmp) {
    this.parentCode = tmp;
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategoryDraftList object
   *
   *@param  tmp  The new ParentCode value
   *@since
   */
  public void setParentCode(String tmp) {
    this.parentCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the CatLevel attribute of the TicketCategoryDraftList object
   *
   *@param  catLevel  The new CatLevel value
   *@since
   */
  public void setCatLevel(int catLevel) {
    this.catLevel = catLevel;
  }


  /**
   *  Sets the CatLevel attribute of the TicketCategoryDraftList object
   *
   *@param  catLevel  The new CatLevel value
   *@since
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
   *@since
   */
  public HtmlSelect getCatListSelect() {
    return catListSelect;
  }


  /**
   *  Gets the HtmlJsEvent attribute of the TicketCategoryDraftList object
   *
   *@return    The HtmlJsEvent value
   *@since
   */
  public String getHtmlJsEvent() {
    return HtmlJsEvent;
  }


  /**
   *  Gets the HtmlSelect attribute of the TicketCategoryDraftList object
   *
   *@param  selectName  Description of Parameter
   *@return             The HtmlSelect value
   *@since
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  Gets the CatLevel attribute of the TicketCategoryDraftList object
   *
   *@return    The CatLevel value
   *@since
   */
  public int getCatLevel() {
    return catLevel;
  }


  /**
   *  Gets the PagedListInfo attribute of the TicketCategoryDraftList object
   *
   *@return    The PagedListInfo value
   *@since
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the ParentCode attribute of the TicketCategoryDraftList object
   *
   *@return    The ParentCode value
   *@since
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
   *@since
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    Iterator i = this.iterator();
    catListSelect.addItem(0, "Undetermined");
    while (i.hasNext()) {
      TicketCategory thisCat = (TicketCategory) i.next();
      catListSelect.addItem(
          thisCat.getId(),
          thisCat.getDescription());
    }

    if (!(this.getHtmlJsEvent().equals(""))) {
      catListSelect.setJsEvent(this.getHtmlJsEvent());
    }

    return catListSelect.getHtml(selectName, defaultKey);
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
        "SELECT COUNT(*) AS recordcount " +
        "FROM ticket_category_draft tc " +
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
      pagedListInfo.setDefaultSort("tc.cat_level, tc.level", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY tc.cat_level, tc.level");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "tc.* " +
        "FROM ticket_category_draft tc " +
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
   *@since
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

    if (topLevelOnly) {
      sqlFilter.append("AND tc.parent_code = -1 ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
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

}

