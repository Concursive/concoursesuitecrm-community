/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     kailash
 * @version $Id: Exp $
 * @created    February 10, 2006
 */
public class PageGroupList extends ArrayList {

  private PagedListInfo pagedListInfo = null;

  private int id = -1;
  private int tabId = -1;
  private String groupName = null;

  private boolean buildPages = false;
  private int pageToBuild = -1;
  private int mode = -1;
  private Page thisPageToBuild = null;
  private int afterPosition = -1;
  private int beforePosition = -1;
  private int position = -1;


  /**
   *  Constructor for the PageGroupList object
   */
  public PageGroupList() { }


  /**
   *  Sets the id attribute of the PageGroupList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PageGroupList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the tabId attribute of the PageGroupList object
   *
   * @param  tmp  The new tabId value
   */
  public void setTabId(int tmp) {
    this.tabId = tmp;
  }


  /**
   *  Sets the tabId attribute of the PageGroupList object
   *
   * @param  tmp  The new tabId value
   */
  public void setTabId(String tmp) {
    this.tabId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildPages attribute of the PageGroupList object
   *
   * @param  tmp  The new buildPages value
   */
  public void setBuildPages(boolean tmp) {
    this.buildPages = tmp;
  }


  /**
   *  Sets the buildPages attribute of the PageGroupList object
   *
   * @param  tmp  The new buildPages value
   */
  public void setBuildPages(String tmp) {
    this.buildPages = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the pageToBuild attribute of the PageGroupList object
   *
   * @param  tmp  The new pageToBuild value
   */
  public void setPageToBuild(int tmp) {
    this.pageToBuild = tmp;
  }


  /**
   *  Sets the pageToBuild attribute of the PageGroupList object
   *
   * @param  tmp  The new pageToBuild value
   */
  public void setPageToBuild(String tmp) {
    this.pageToBuild = Integer.parseInt(tmp);
  }


  /**
   *  Sets the mode attribute of the PageGroupList object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(int tmp) {
    this.mode = tmp;
  }


  /**
   *  Sets the mode attribute of the PageGroupList object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(String tmp) {
    this.mode = Integer.parseInt(tmp);
  }


  /**
   *  Gets the id attribute of the PageGroupList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the tabId attribute of the PageGroupList object
   *
   * @return    The tabId value
   */
  public int getTabId() {
    return tabId;
  }


  /**
   *  Gets the buildPages attribute of the PageGroupList object
   *
   * @return    The buildPages value
   */
  public boolean getBuildPages() {
    return buildPages;
  }


  /**
   *  Gets the pageToBuild attribute of the PageGroupList object
   *
   * @return    The pageToBuild value
   */
  public int getPageToBuild() {
    return pageToBuild;
  }


  /**
   *  Gets the mode attribute of the PageGroupList object
   *
   * @return    The mode value
   */
  public int getMode() {
    return mode;
  }


  /**
   *  Gets the thisPageToBuild attribute of the PageGroupList object
   *
   * @return    The thisPageToBuild value
   */
  public Page getThisPageToBuild() {
    return thisPageToBuild;
  }


  /**
   *  Sets the thisPageToBuild attribute of the PageGroupList object
   *
   * @param  tmp  The new thisPageToBuild value
   */
  public void setThisPageToBuild(Page tmp) {
    this.thisPageToBuild = tmp;
  }


  /**
   *  Gets the afterPosition attribute of the PageGroupList object
   *
   * @return    The afterPosition value
   */
  public int getAfterPosition() {
    return afterPosition;
  }


  /**
   *  Sets the afterPosition attribute of the PageGroupList object
   *
   * @param  tmp  The new afterPosition value
   */
  public void setAfterPosition(int tmp) {
    this.afterPosition = tmp;
  }


  /**
   *  Sets the afterPosition attribute of the PageGroupList object
   *
   * @param  tmp  The new afterPosition value
   */
  public void setAfterPosition(String tmp) {
    this.afterPosition = Integer.parseInt(tmp);
  }


  /**
   *  Gets the beforePosition attribute of the PageGroupList object
   *
   * @return    The beforePosition value
   */
  public int getBeforePosition() {
    return beforePosition;
  }


  /**
   *  Sets the beforePosition attribute of the PageGroupList object
   *
   * @param  tmp  The new beforePosition value
   */
  public void setBeforePosition(int tmp) {
    this.beforePosition = tmp;
  }


  /**
   *  Sets the beforePosition attribute of the PageGroupList object
   *
   * @param  tmp  The new beforePosition value
   */
  public void setBeforePosition(String tmp) {
    this.beforePosition = Integer.parseInt(tmp);
  }


  /**
   *  Gets the position attribute of the PageGroupList object
   *
   * @return    The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   *  Sets the position attribute of the PageGroupList object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   *  Sets the position attribute of the PageGroupList object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      PageGroup thisPageGroup = this.getObject(rs);
      this.add(thisPageGroup);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    if (buildPages) {
      Iterator pageGroupIterator = this.iterator();
      while (pageGroupIterator.hasNext()) {
        PageGroup thisPageGroup = (PageGroup) pageGroupIterator.next();
        thisPageGroup.setMode(this.getMode());
        thisPageGroup.setPageToBuild(pageToBuild);
        thisPageGroup.buildPageList(db);
        if (pageToBuild != -1 && thisPageGroup.getThisPageToBuild() != null) {
          this.setThisPageToBuild(thisPageGroup.getThisPageToBuild());
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {

    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
      "SELECT COUNT(*) AS recordcount " +
        "FROM web_page_group " +
        "WHERE page_group_id > -1 ");

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

      //Determine column to sort by
      pagedListInfo.setDefaultSort("group_position", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY group_position ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
      "wpg.* " +
        "FROM web_page_group wpg " +
        "WHERE page_group_id > -1 ");
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

    return rs;
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter      Description of the Parameter
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
    if (id != -1) {
      sqlFilter.append("AND page_group_id = ? ");
    }
    if (tabId != -1) {
      sqlFilter.append("AND tab_id = ? ");
    }
    if (groupName != null) {
      sqlFilter.append("AND group_name = ? ");
    }
    if (position > -1) {
      sqlFilter.append("AND group_position = ? ");
    }
    if (afterPosition > -1) {
      sqlFilter.append("AND group_position > ? ");
    }
    if (beforePosition > -1) {
      sqlFilter.append("AND group_position < ? ");
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
    if (id != -1) {
      pst.setInt(++i, id);
    }
    if (tabId != -1) {
      pst.setInt(++i, tabId);
    }
    if (groupName != null) {
    	pst.setString(++i, groupName);
    }
    if (position > 0) {
      pst.setInt(++i, position);
    }
    if (afterPosition > -1) {
      pst.setInt(++i, afterPosition);
    }
    if (beforePosition > -1) {
      pst.setInt(++i, beforePosition);
    }
    return i;
  }


  /**
   *  Gets the object attribute of the PageGroupList object
   *
   * @param  rs             Description of the Parameter
   * @return                The object value
   * @throws  SQLException  Description of the Exception
   */
  public PageGroup getObject(ResultSet rs) throws SQLException {
    return new PageGroup(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator pageGroupIterator = this.iterator();
    while (pageGroupIterator.hasNext()) {
      PageGroup thisPageGroup = (PageGroup) pageGroupIterator.next();
      thisPageGroup.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                      Description of the Parameter
   * @param  currentId               Description of the Parameter
   * @param  adjacentId              Description of the Parameter
   * @param  changeAdjacentPosition  Description of the Parameter
   * @param  addition                Description of the Parameter
   * @param  tabId                   Description of the Parameter
   * @throws  SQLException           Description of the Exception
   */
  public static void updateRelatedPageGroups(Connection db, int currentId, int adjacentId, int tabId, boolean changeAdjacentPosition, boolean addition) throws SQLException {
    if (addition) {
      PreparedStatement pst = db.prepareStatement(
        "UPDATE web_page_group " +
          "SET group_position = group_position + 1 " +
          "WHERE page_group_id <> ? " +
          "AND tab_id = ? " +
          "AND " + (changeAdjacentPosition ? "group_position >= " : "group_position > ") +
          "(SELECT group_position FROM web_page_group WHERE page_group_id = ?) ");
      pst.setInt(1, currentId);
      pst.setInt(2, tabId);
      pst.setInt(3, adjacentId);
      pst.executeUpdate();
      pst.close();
    } else {
      PreparedStatement pst = db.prepareStatement(
        "UPDATE web_page_group " +
          "SET group_position = group_position - 1 " +
          "WHERE tab_id = ? " +
          "AND group_position > " +
          "(SELECT group_position FROM web_page_group WHERE page_group_id = ?) ");
      pst.setInt(1, tabId);
      pst.setInt(2, currentId);
      pst.executeUpdate();
      pst.close();
    }
  }

  /**
   *  The pageGroupList is a secondary menu that is always shown in edit mode,
   *  but ownly shown in portal mode under certain conditions
   *
   * @return    true if the secondary menu should be shown in portal mode
   */
  public boolean canDisplay() {
    // The seconday menu can be shown if there is more than 1 pageGroup,
    // or the pageGroup has more than 1 page to display
    if (this.size() > 1) {
      return true;
    }
    if (this.size() == 0) {
      return false;
    }
    PageGroup thisPageGroup = (PageGroup) this.get(0);
    if (thisPageGroup.canDisplay()) {
      return true;
    }
    return false;
  }


  /**
   *  Gets the canDisplay attribute of the PageGroupList object
   *
   * @return    The canDisplay value
   */
  public boolean getCanDisplay() {
    return canDisplay();
  }


  /**
   *  Gets the count attribute of the PageGroupList object
   *
   * @return    The count value
   */
  public int getCount() {
    return this.size();
  }


  /**
   *  Gets the PageGroup by the groupId from the list of page groups
   *
   * @param  groupId  Description of the Parameter
   * @return          The groupById value
   */
  public PageGroup getGroupById(int groupId) {
    PageGroup result = null;
    Iterator pageGroupIterator = this.iterator();
    while (pageGroupIterator.hasNext()) {
      PageGroup thisPageGroup = (PageGroup) pageGroupIterator.next();
      if (thisPageGroup.getId() == groupId) {
        result = thisPageGroup;
        break;
      }
    }
    return result;
  }


	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}


	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}

