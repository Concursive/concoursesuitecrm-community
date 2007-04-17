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

import org.aspcfs.modules.base.Constants;
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
 * @created    February 10, 2006 $Id: Exp $
 * @version    $Id: Exp $
 */
public class PageList extends ArrayList {

  private PagedListInfo pagedListInfo = null;

  private int id = -1;
  private int position = -1;
  private int activePageVersionId = -1;
  private int constructionPageVersionId = -1;
  private int pageGroupId = -1;
  private int tabBannerId = -1;
  private int enabled = Constants.UNDEFINED;
  private int linkModuleId = -1;
  private int linkContainerId = -1;
  private boolean dashboard = false;
  private boolean customTab = false;
  private boolean website = false;

  private int pageToBuild = -1;
  private int mode = -1;
  private Page thisPageToBuild = null;

  private int afterPosition = -1;
  private int beforePosition = -1;
  private int roleId = -1;


  /**
   *  Constructor for the PageList object
   */
  public PageList() { }


  /**
   *  Sets the pagedListInfo attribute of the PageList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the PageList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PageList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the position attribute of the PageList object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   *  Sets the position attribute of the PageList object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
  }


  /**
   *  Sets the activePageVersionId attribute of the PageList object
   *
   * @param  tmp  The new activePageVersionId value
   */
  public void setActivePageVersionId(int tmp) {
    this.activePageVersionId = tmp;
  }


  /**
   *  Sets the activePageVersionId attribute of the PageList object
   *
   * @param  tmp  The new activePageVersionId value
   */
  public void setActivePageVersionId(String tmp) {
    this.activePageVersionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the constructionPageVersionId attribute of the PageList object
   *
   * @param  tmp  The new constructionPageVersionId value
   */
  public void setConstructionPageVersionId(int tmp) {
    this.constructionPageVersionId = tmp;
  }


  /**
   *  Sets the constructionPageVersionId attribute of the PageList object
   *
   * @param  tmp  The new constructionPageVersionId value
   */
  public void setConstructionPageVersionId(String tmp) {
    this.constructionPageVersionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pageGroupId attribute of the PageList object
   *
   * @param  tmp  The new pageGroupId value
   */
  public void setPageGroupId(int tmp) {
    this.pageGroupId = tmp;
  }


  /**
   *  Sets the pageGroupId attribute of the PageList object
   *
   * @param  tmp  The new pageGroupId value
   */
  public void setPageGroupId(String tmp) {
    this.pageGroupId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the tabBannerId attribute of the PageList object
   *
   * @param  tmp  The new tabBannerId value
   */
  public void setTabBannerId(int tmp) {
    this.tabBannerId = tmp;
  }


  /**
   *  Sets the tabBannerId attribute of the PageList object
   *
   * @param  tmp  The new tabBannerId value
   */
  public void setTabBannerId(String tmp) {
    this.tabBannerId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the PageList object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the PageList object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pageToBuild attribute of the PageList object
   *
   * @param  tmp  The new pageToBuild value
   */
  public void setPageToBuild(int tmp) {
    this.pageToBuild = tmp;
  }


  /**
   *  Sets the pageToBuild attribute of the PageList object
   *
   * @param  tmp  The new pageToBuild value
   */
  public void setPageToBuild(String tmp) {
    this.pageToBuild = Integer.parseInt(tmp);
  }


  /**
   *  Sets the mode attribute of the PageList object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(int tmp) {
    this.mode = tmp;
    if (this.mode == Site.PORTAL_MODE) {
      this.setEnabled(Constants.TRUE);
    }
  }


  /**
   *  Sets the mode attribute of the PageList object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(String tmp) {
    this.mode = Integer.parseInt(tmp);
    if (this.mode == Site.PORTAL_MODE) {
      this.setEnabled(Constants.TRUE);
    }
  }


  /**
   *  Gets the pagedListInfo attribute of the PageList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the PageList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the position attribute of the PageList object
   *
   * @return    The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   *  Gets the activePageVersionId attribute of the PageList object
   *
   * @return    The activePageVersionId value
   */
  public int getActivePageVersionId() {
    return activePageVersionId;
  }


  /**
   *  Gets the constructionPageVersionId attribute of the PageList object
   *
   * @return    The constructionPageVersionId value
   */
  public int getConstructionPageVersionId() {
    return constructionPageVersionId;
  }


  /**
   *  Gets the pageGroupId attribute of the PageList object
   *
   * @return    The pageGroupId value
   */
  public int getPageGroupId() {
    return pageGroupId;
  }


  /**
   *  Gets the tabBannerId attribute of the PageList object
   *
   * @return    The tabBannerId value
   */
  public int getTabBannerId() {
    return tabBannerId;
  }


  /**
   *  Gets the enabled attribute of the PageList object
   *
   * @return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the pageToBuild attribute of the PageList object
   *
   * @return    The pageToBuild value
   */
  public int getPageToBuild() {
    return pageToBuild;
  }


  /**
   *  Gets the mode attribute of the PageList object
   *
   * @return    The mode value
   */
  public int getMode() {
    return mode;
  }


  /**
   *  Gets the thisPageToBuild attribute of the PageList object
   *
   * @return    The thisPageToBuild value
   */
  public Page getThisPageToBuild() {
    return thisPageToBuild;
  }


  /**
   *  Sets the thisPageToBuild attribute of the PageList object
   *
   * @param  tmp  The new thisPageToBuild value
   */
  public void setThisPageToBuild(Page tmp) {
    this.thisPageToBuild = tmp;
  }


  /**
   *  Gets the afterPosition attribute of the PageList object
   *
   * @return    The afterPosition value
   */
  public int getAfterPosition() {
    return afterPosition;
  }


  /**
   *  Sets the afterPosition attribute of the PageList object
   *
   * @param  tmp  The new afterPosition value
   */
  public void setAfterPosition(int tmp) {
    this.afterPosition = tmp;
  }


  /**
   *  Sets the afterPosition attribute of the PageList object
   *
   * @param  tmp  The new afterPosition value
   */
  public void setAfterPosition(String tmp) {
    this.afterPosition = Integer.parseInt(tmp);
  }


  /**
   *  Gets the beforePosition attribute of the PageList object
   *
   * @return    The beforePosition value
   */
  public int getBeforePosition() {
    return beforePosition;
  }


  /**
   *  Sets the beforePosition attribute of the PageList object
   *
   * @param  tmp  The new beforePosition value
   */
  public void setBeforePosition(int tmp) {
    this.beforePosition = tmp;
  }


  /**
   *  Sets the beforePosition attribute of the PageList object
   *
   * @param  tmp  The new beforePosition value
   */
  public void setBeforePosition(String tmp) {
    this.beforePosition = Integer.parseInt(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      Page thisPage = this.getObject(rs);
      this.add(thisPage);
      if (pageToBuild != -1 && thisPage.getId() == pageToBuild) {
        thisPageToBuild = thisPage;
      }
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    if (thisPageToBuild != null) {
      thisPageToBuild.setMode(mode);
      thisPageToBuild.buildPageVersionToView(db);
      if (thisPageToBuild.getTabBannerId() > -1) {
        thisPageToBuild.buildTabBanner(db);
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
  public PreparedStatement prepareList(Connection db) throws SQLException {

    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM web_page " +
        "WHERE page_id > -1 ");

    createFilter(sqlFilter, db);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      PreparedStatement pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine column to sort by
      pagedListInfo.setDefaultSort("page_position", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY page_position ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "wp.* " +
        "FROM web_page wp " +
        "WHERE page_id > -1 ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    return pst;
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
      sqlFilter.append("AND page_id = ? ");
    }
    if (position > -1) {
      sqlFilter.append("AND page_position = ? ");
    }
    if (activePageVersionId != -1) {
      sqlFilter.append("AND active_page_version_id = ? ");
    }
    if (constructionPageVersionId != -1) {
      sqlFilter.append("AND construction_page_version_id = ? ");
    }
    if (pageGroupId != -1) {
      sqlFilter.append("AND page_group_id = ? ");
    }
    if (tabBannerId != -1) {
      sqlFilter.append("AND tab_banner_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
    }
    if (afterPosition > -1) {
      sqlFilter.append("AND page_position > ? ");
    }
    if (beforePosition > -1) {
      sqlFilter.append("AND page_position < ? ");
    }
    if (linkModuleId > -1){
      sqlFilter.append("AND link_module_id = ? ");
      
    }
    if (linkContainerId > -1){
      sqlFilter.append("AND link_container_id = ? ");      
    }
    if(dashboard){
      sqlFilter.append("AND link_module_id is not null ");
    }else if(customTab){
      sqlFilter.append("AND link_container_id is not null ");
    }else if (website){
      sqlFilter.append("AND link_container_id is null AND link_module_id is null ");
    }
    if (roleId > -1) {
    	sqlFilter.append("AND page_id IN (SELECT web_page_id FROM web_page_role_map WHERE role_id = ?) ");
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
    if (position > -1) {
      pst.setInt(++i, position);
    }
    if (activePageVersionId != -1) {
      pst.setInt(++i, activePageVersionId);
    }
    if (constructionPageVersionId != -1) {
      pst.setInt(++i, constructionPageVersionId);
    }
    if (pageGroupId != -1) {
      pst.setInt(++i, pageGroupId);
    }
    if (tabBannerId != -1) {
      pst.setInt(++i, tabBannerId);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, (enabled == Constants.TRUE));
    }
    if (afterPosition > -1) {
      pst.setInt(++i, afterPosition);
    }
    if (beforePosition > -1) {
      pst.setInt(++i, beforePosition);
    }
    if (linkModuleId > -1){
      pst.setInt(++i, linkModuleId);
      
    }
    if (linkContainerId > -1){
      pst.setInt(++i,linkContainerId);      
    }

    if (roleId > -1){
      pst.setInt(++i,roleId);      
    }

    
    return i;
  }


  /**
   *  Gets the object attribute of the PageList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public Page getObject(ResultSet rs) throws SQLException {
    return new Page(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator pageIterator = this.iterator();
    while (pageIterator.hasNext()) {
      Page thisPage = (Page) pageIterator.next();
      thisPage.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  tmpTabId          Description of the Parameter
   * @param  mode              Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static int queryDefault(Connection db, int tmpTabId, int mode) throws SQLException {
    PageGroup defaultPageGroup = new PageGroup();
    Page defaultPage = new Page();

    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM web_page_group " +
        " WHERE tab_id = ? " +
        " AND group_position = ? " +
        " ORDER BY group_position ");
    int i = 0;
    pst.setInt(++i, tmpTabId);
    pst.setInt(++i, PageGroup.INITIAL_POSITION);
    rs = pst.executeQuery();
    if (rs.next()) {
      defaultPageGroup = new PageGroup(rs);
    }
    rs.close();
    pst.close();

    if (defaultPageGroup.getId() != -1) {
      pst = db.prepareStatement(
          " SELECT * " +
          " FROM web_page " +
          " WHERE page_group_id = ? " +
          " AND page_position = ? " +
          ((mode == Site.PORTAL_MODE) ? "AND enabled = ? " : "") +
          " ORDER BY page_position ");
      i = 0;
      pst.setInt(++i, defaultPageGroup.getId());
      pst.setInt(++i, Page.INITIAL_POSITION);
      if (mode == Site.PORTAL_MODE) {
        pst.setBoolean(++i, true);
      }
      rs = pst.executeQuery();
      if (rs.next()) {
        defaultPage = new Page(rs);
      }
      rs.close();
      pst.close();
    }
    return defaultPage.getId();
  }


  /**
   *  Description of the Method
   *
   * @param  db                      Description of the Parameter
   * @param  currentId               Description of the Parameter
   * @param  adjacentId              Description of the Parameter
   * @param  changeAdjacentPosition  Description of the Parameter
   * @param  addition                Description of the Parameter
   * @param  pageGroupId             Description of the Parameter
   * @exception  SQLException        Description of the Exception
   */
  public static void updateRelatedPages(Connection db, int currentId, int adjacentId, int pageGroupId, boolean changeAdjacentPosition, boolean addition) throws SQLException {
    if (addition) {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE web_page " +
          "SET page_position = page_position + 1 " +
          "WHERE page_id <> ? " +
          "AND page_group_id = ? " +
          "AND " + (changeAdjacentPosition ? "page_position >= " : "page_position > ") +
          "(SELECT page_position FROM web_page WHERE page_id = ?) ");
      pst.setInt(1, currentId);
      pst.setInt(2, pageGroupId);
      pst.setInt(3, adjacentId);
      pst.executeUpdate();
      pst.close();
    } else {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE web_page " +
          "SET page_position = page_position - 1 " +
          "WHERE page_group_id = ? " +
          "AND page_position > " +
          "(SELECT page_position FROM web_page WHERE page_id = ?) ");
      pst.setInt(1, pageGroupId);
      pst.setInt(2, currentId);
      pst.executeUpdate();
      pst.close();
    }
  }


  /**
   *  Gets the count attribute of the PageList object
   *
   * @return    The count value
   */
  public int getCount() {
    return this.size();
  }


  /**
   *  Gets the defaultPageId attribute of the PageList object
   *
   * @return    The defaultPageId value
   */
  public int getDefaultPageId() {
    int result = -1;
    Iterator pageIterator = this.iterator();
    while (pageIterator.hasNext()) {
      Page thisPage = (Page) pageIterator.next();
      if (thisPage.getPosition() == 0) {
        result = thisPage.getId();
        break;
      }
    }
    return result;
  }


  /**
   * @return the linkContainerId
   */
  public int getLinkContainerId() {
    return linkContainerId;
  }


  /**
   * @param linkContainerId the linkContainerId to set
   */
  public void setLinkContainerId(int linkContainerId) {
    this.linkContainerId = linkContainerId;
  }


  /**
   * @return the linkModuleId
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * @param linkModuleId the linkModuleId to set
   */
  public void setLinkModuleId(int linkModuleId) {
    this.linkModuleId = linkModuleId;
  }


  /**
   * @return the customeTab
   */
  public boolean isCustomTab() {
    return customTab;
  }


  /**
   * @param customTab the customeTab to set
   */
  public void setCustomTab(boolean customTab) {
    this.customTab = customTab;
  }


  /**
   * @return the dasboard
   */
  public boolean isDashboard() {
    return dashboard;
  }


  /**
   * @param dashboard the dasboard to set
   */
  public void setDashboard(boolean dashboard) {
    this.dashboard = dashboard;
  }


	/**
	 * @return the website
	 */
	public boolean isWebsite() {
		return website;
	}


	/**
	 * @param website the website to set
	 */
	public void setWebsite(boolean website) {
		this.website = website;
	}


	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}


	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


}


