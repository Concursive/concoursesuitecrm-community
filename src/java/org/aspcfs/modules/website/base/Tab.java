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

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.DependencyList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 * @author     kailash
 * @created    February 10, 2006 $Id: Exp $
 * @version    $Id: Exp $
 */
public class Tab extends GenericBean {

  public final static int INITIAL_POSITION = 0;
  private int id = -1;
  private String displayText = null;
  private String internalDescription = null;
  private int siteId = -1;
  private int position = -1;
  private boolean enabled = false;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;

  private int mode = -1;
  private boolean buildPageGroupList = false;
  private boolean buildPages = false;
  private PageGroupList pageGroupList = null;
  private boolean buildTabBanner = false;
  private TabBanner tabBanner = null;
  private int pageToBuild = -1;

  private boolean override = false;
  private Page thisPageToBuild = null;
  private int previousTabId = -1;
  private int nextTabId = -1;


  /**
   *  Constructor for the Tab object
   */
  public Tab() { }


  /**
   *  Constructor for the Tab object
   *
   * @param  db                Description of the Parameter
   * @param  tmpTabId          Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public Tab(Connection db, int tmpTabId) throws SQLException {
    queryRecord(db, tmpTabId);
  }


  /**
   *  Constructor for the Tab object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public Tab(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the Tab object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Tab object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the displayText attribute of the Tab object
   *
   * @param  tmp  The new displayText value
   */
  public void setDisplayText(String tmp) {
    this.displayText = tmp;
  }


  /**
   *  Sets the internalDescription attribute of the Tab object
   *
   * @param  tmp  The new internalDescription value
   */
  public void setInternalDescription(String tmp) {
    this.internalDescription = tmp;
  }


  /**
   *  Sets the siteId attribute of the Tab object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(int tmp) {
    this.siteId = tmp;
  }


  /**
   *  Sets the siteId attribute of the Tab object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the position attribute of the Tab object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   *  Sets the position attribute of the Tab object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the Tab object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the Tab object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Tab object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Tab object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the Tab object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Tab object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Tab object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Tab object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Tab object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Tab object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the mode attribute of the Tab object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(int tmp) {
    this.mode = tmp;
  }


  /**
   *  Sets the mode attribute of the Tab object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(String tmp) {
    this.mode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildPageGroupList attribute of the Tab object
   *
   * @param  tmp  The new buildPageGroupList value
   */
  public void setBuildPageGroupList(boolean tmp) {
    this.buildPageGroupList = tmp;
  }


  /**
   *  Sets the buildPageGroupList attribute of the Tab object
   *
   * @param  tmp  The new buildPageGroupList value
   */
  public void setBuildPageGroupList(String tmp) {
    this.buildPageGroupList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the buildPages attribute of the Tab object
   *
   * @param  tmp  The new buildPages value
   */
  public void setBuildPages(boolean tmp) {
    this.buildPages = tmp;
  }


  /**
   *  Sets the buildPages attribute of the Tab object
   *
   * @param  tmp  The new buildPages value
   */
  public void setBuildPages(String tmp) {
    this.buildPages = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the pageGroupList attribute of the Tab object
   *
   * @param  tmp  The new pageGroupList value
   */
  public void setPageGroupList(PageGroupList tmp) {
    this.pageGroupList = tmp;
  }


  /**
   *  Sets the buildTabBanner attribute of the Tab object
   *
   * @param  tmp  The new buildTabBanner value
   */
  public void setBuildTabBanner(boolean tmp) {
    this.buildTabBanner = tmp;
  }


  /**
   *  Sets the buildTabBanner attribute of the Tab object
   *
   * @param  tmp  The new buildTabBanner value
   */
  public void setBuildTabBanner(String tmp) {
    this.buildTabBanner = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the tabBanner attribute of the Tab object
   *
   * @param  tmp  The new tabBanner value
   */
  public void setTabBanner(TabBanner tmp) {
    this.tabBanner = tmp;
  }


  /**
   *  Sets the pageToBuild attribute of the Tab object
   *
   * @param  tmp  The new pageToBuild value
   */
  public void setPageToBuild(int tmp) {
    this.pageToBuild = tmp;
  }


  /**
   *  Sets the pageToBuild attribute of the Tab object
   *
   * @param  tmp  The new pageToBuild value
   */
  public void setPageToBuild(String tmp) {
    this.pageToBuild = Integer.parseInt(tmp);
  }


  /**
   *  Sets the override attribute of the Tab object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the Tab object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the Tab object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the displayText attribute of the Tab object
   *
   * @return    The displayText value
   */
  public String getDisplayText() {
    return displayText;
  }


  /**
   *  Gets the internalDescription attribute of the Tab object
   *
   * @return    The internalDescription value
   */
  public String getInternalDescription() {
    return internalDescription;
  }


  /**
   *  Gets the siteId attribute of the Tab object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Gets the position attribute of the Tab object
   *
   * @return    The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   *  Gets the enabled attribute of the Tab object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the enteredBy attribute of the Tab object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the Tab object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the Tab object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the Tab object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the mode attribute of the Tab object
   *
   * @return    The mode value
   */
  public int getMode() {
    return mode;
  }


  /**
   *  Gets the buildPageGroupList attribute of the Tab object
   *
   * @return    The buildPageGroupList value
   */
  public boolean getBuildPageGroupList() {
    return buildPageGroupList;
  }


  /**
   *  Gets the buildPages attribute of the Tab object
   *
   * @return    The buildPages value
   */
  public boolean getBuildPages() {
    return buildPages;
  }


  /**
   *  Gets the pageGroupList attribute of the Tab object
   *
   * @return    The pageGroupList value
   */
  public PageGroupList getPageGroupList() {
    return pageGroupList;
  }


  /**
   *  Gets the buildTabBanner attribute of the Tab object
   *
   * @return    The buildTabBanner value
   */
  public boolean getBuildTabBanner() {
    return buildTabBanner;
  }


  /**
   *  Gets the tabBanner attribute of the Tab object
   *
   * @return    The tabBanner value
   */
  public TabBanner getTabBanner() {
    return tabBanner;
  }


  /**
   *  Gets the pageToBuild attribute of the Tab object
   *
   * @return    The pageToBuild value
   */
  public int getPageToBuild() {
    return pageToBuild;
  }


  /**
   *  Gets the override attribute of the Tab object
   *
   * @return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Gets the thisPageToBuild attribute of the Tab object
   *
   * @return    The thisPageToBuild value
   */
  public Page getThisPageToBuild() {
    return thisPageToBuild;
  }


  /**
   *  Sets the thisPageToBuild attribute of the Tab object
   *
   * @param  tmp  The new thisPageToBuild value
   */
  public void setThisPageToBuild(Page tmp) {
    this.thisPageToBuild = tmp;
  }


  /**
   *  Gets the previousTabId attribute of the Tab object
   *
   * @return    The previousTabId value
   */
  public int getPreviousTabId() {
    return previousTabId;
  }


  /**
   *  Sets the previousTabId attribute of the Tab object
   *
   * @param  tmp  The new previousTabId value
   */
  public void setPreviousTabId(int tmp) {
    this.previousTabId = tmp;
  }


  /**
   *  Sets the previousTabId attribute of the Tab object
   *
   * @param  tmp  The new previousTabId value
   */
  public void setPreviousTabId(String tmp) {
    this.previousTabId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the nextTabId attribute of the Tab object
   *
   * @return    The nextTabId value
   */
  public int getNextTabId() {
    return nextTabId;
  }


  /**
   *  Sets the nextTabId attribute of the Tab object
   *
   * @param  tmp  The new nextTabId value
   */
  public void setNextTabId(int tmp) {
    this.nextTabId = tmp;
  }


  /**
   *  Sets the nextTabId attribute of the Tab object
   *
   * @param  tmp  The new nextTabId value
   */
  public void setNextTabId(String tmp) {
    this.nextTabId = Integer.parseInt(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  tmpTabId       Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpTabId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM web_tab " +
        " WHERE tab_id = ? ");
    pst.setInt(1, tmpTabId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Tab record not found");
    }
    if (buildPageGroupList) {
      buildPageGroupList(db);
    }
    if (buildTabBanner) {
      buildTabBanner(db);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {

    id = DatabaseUtils.getNextSeq(db, "web_tab_tab_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO web_tab " +
        "(" + (id > -1 ? "tab_id, " : "") +
        "display_text , " +
        "internal_description , " +
        "site_id , " +
        "tab_position , " +
        "enabled , " +
        "enteredby , " +
        "modifiedby ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, displayText);
    pst.setString(++i, internalDescription);
    DatabaseUtils.setInt(pst, ++i, siteId);
    pst.setInt(++i, position);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "web_tab_tab_id_seq", id);
    pst.close();

    updateRelatedTabs(db, true);
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {

    int resultCount = -1;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE web_tab " +
        "SET " +
        "display_text = ? , " +
        "internal_description = ? , " +
        "site_id = ? , " +
        "tab_position = ? , " +
        "enabled = ?  ");

    if (!override) {
      sql.append(
          " , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE tab_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, displayText);
    pst.setString(++i, internalDescription);
    DatabaseUtils.setInt(pst, ++i, siteId);
    pst.setInt(++i, position);
    pst.setBoolean(++i, enabled);
    if (!override) {
      pst.setInt(++i, modifiedBy);
    }
    pst.setInt(++i, id);
    if (!override) {
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {

    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }

      PageGroupList tmpPageGroupList = new PageGroupList();
      tmpPageGroupList.setTabId(this.getId());
      tmpPageGroupList.buildList(db);
      tmpPageGroupList.delete(db);
      tmpPageGroupList = null;

      TabBannerList tmpTabBannerList = new TabBannerList();
      tmpTabBannerList.setTabId(this.getId());
      tmpTabBannerList.buildList(db);
      tmpTabBannerList.delete(db);
      tmpTabBannerList = null;

      updateRelatedTabs(db, false);

      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM web_tab " +
          "WHERE tab_id =  ? ");

      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
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


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("tab_id");
    displayText = rs.getString("display_text");
    internalDescription = rs.getString("internal_description");
    siteId = DatabaseUtils.getInt(rs, "site_id");
    position = rs.getInt("tab_position");
    enabled = rs.getBoolean("enabled");

    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildPageGroupList(Connection db) throws SQLException {
    pageGroupList = new PageGroupList();
    pageGroupList.setTabId(this.getId());
    pageGroupList.setMode(mode);
    pageGroupList.setBuildPages(buildPages);

    // All the contents of this page will be built
    pageGroupList.setPageToBuild(pageToBuild);

    pageGroupList.buildList(db);

    if (pageToBuild != -1 && pageGroupList.getThisPageToBuild() != null) {
      this.setThisPageToBuild(pageGroupList.getThisPageToBuild());
      this.getThisPageToBuild().setPageGroup(pageGroupList.getGroupById(this.getThisPageToBuild().getPageGroupId()));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildTabBanner(Connection db) throws SQLException {
    TabBannerList tabBannerList = new TabBannerList();
    tabBannerList.setTabId(this.getId());
    tabBannerList.buildList(db);
    if (tabBannerList.size() > 0) {
      tabBanner = (TabBanner) tabBannerList.get(0);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Site ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    /*
     *  / Check for this site's dependencies
     *  try {
     *  i = 0;
     *  pst = db.prepareStatement(
     *  "SELECT count(*) as parentcount " +
     *  "FROM quote_entry " +
     *  "WHERE parent_id = ? ");
     *  pst.setInt(++i, this.getId());
     *  rs = pst.executeQuery();
     *  if (rs.next()) {
     *  int parentCount = rs.getInt("parentcount");
     *  if (parentCount != 0) {
     *  Dependency thisDependency = new Dependency();
     *  thisDependency.setName("numberOfParentsOfThisQuote");
     *  thisDependency.setCount(parentCount);
     *  thisDependency.setCanDelete(true);
     *  dependencyList.add(thisDependency);
     *  }
     *  }
     *  rs.close();
     *  pst.close();
     *  } catch (SQLException e) {
     *  throw new SQLException(e.getMessage());
     *  }
     */
    return dependencyList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int computeTabPosition(Connection db) throws SQLException {
    int result = Tab.INITIAL_POSITION;
    Tab otherTab = new Tab();
    if (previousTabId != -1) {
      otherTab.queryRecord(db, previousTabId);
      result = otherTab.getPosition() + 1;
    } else if (nextTabId != -1) {
      otherTab.queryRecord(db, nextTabId);
      result = otherTab.getPosition();
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  add               Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void updateRelatedTabs(Connection db, boolean add) throws SQLException {
    if (previousTabId > -1) {
      TabList.updateRelatedTabs(db, this.getId(), previousTabId, this.getSiteId(), false, add);
    } else if (nextTabId > -1) {
      TabList.updateRelatedTabs(db, this.getId(), nextTabId, this.getSiteId(), true, add);
    } else if (!add) {
      TabList.updateRelatedTabs(db, this.getId(), -1, this.getSiteId(), false, add);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  moveLeft          Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void move(Connection db, boolean moveLeft) throws SQLException {
    Tab switchTab = null;
    TabList tabs = new TabList();
    tabs.setSiteId(this.getSiteId());
    tabs.setPosition((moveLeft ? this.getPosition() - 1 : this.getPosition() + 1));
    tabs.buildList(db);
    if (tabs.size() > 0) {
      switchTab = (Tab) tabs.get(0);
      int switchTabPosition = this.getPosition();
      this.setPosition(switchTab.getPosition());
      this.update(db);
      switchTab.setPosition(switchTabPosition);
      switchTab.update(db);
    }
  }
}

