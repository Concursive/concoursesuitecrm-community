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
public class Page extends GenericBean {

  public final static int INITIAL_POSITION = 0;
  private int id = -1;
  private String name = null;
  private int position = -1;
  private int activePageVersionId = -1;
  private int constructionPageVersionId = -1;
  private int pageGroupId = -1;
  private int tabBannerId = -1;
  private String notes = null;
  private boolean enabled = false;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;

  private boolean buildPageVersionList = false;
  private PageVersionList pageVersionList = null;
  private boolean override = false;

  private boolean buildTabBanner = false;
  private TabBanner tabBanner = null;
  private int mode = -1;
  private boolean buildPageVersionToView = false;
  private PageVersion pageVersionToView = null;

  private int previousPageId = -1;
  private int nextPageId = -1;


  /**
   *  Constructor for the Page object
   */
  public Page() { }


  /**
   *  Constructor for the Page object
   *
   * @param  db                Description of the Parameter
   * @param  tmpPageId         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public Page(Connection db, int tmpPageId) throws SQLException {
    queryRecord(db, tmpPageId);
  }


  /**
   *  Constructor for the Page object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public Page(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the Page object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Page object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the Page object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the position attribute of the Page object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   *  Sets the position attribute of the Page object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
  }


  /**
   *  Sets the activePageVersionId attribute of the Page object
   *
   * @param  tmp  The new activePageVersionId value
   */
  public void setActivePageVersionId(int tmp) {
    this.activePageVersionId = tmp;
  }


  /**
   *  Sets the activePageVersionId attribute of the Page object
   *
   * @param  tmp  The new activePageVersionId value
   */
  public void setActivePageVersionId(String tmp) {
    this.activePageVersionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the constructionPageVersionId attribute of the Page object
   *
   * @param  tmp  The new constructionPageVersionId value
   */
  public void setConstructionPageVersionId(int tmp) {
    this.constructionPageVersionId = tmp;
  }


  /**
   *  Sets the constructionPageVersionId attribute of the Page object
   *
   * @param  tmp  The new constructionPageVersionId value
   */
  public void setConstructionPageVersionId(String tmp) {
    this.constructionPageVersionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pageGroupId attribute of the Page object
   *
   * @param  tmp  The new pageGroupId value
   */
  public void setPageGroupId(int tmp) {
    this.pageGroupId = tmp;
  }


  /**
   *  Sets the pageGroupId attribute of the Page object
   *
   * @param  tmp  The new pageGroupId value
   */
  public void setPageGroupId(String tmp) {
    this.pageGroupId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the tabBannerId attribute of the Page object
   *
   * @param  tmp  The new tabBannerId value
   */
  public void setTabBannerId(int tmp) {
    this.tabBannerId = tmp;
  }


  /**
   *  Sets the tabBannerId attribute of the Page object
   *
   * @param  tmp  The new tabBannerId value
   */
  public void setTabBannerId(String tmp) {
    this.tabBannerId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the notes attribute of the Page object
   *
   * @param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the enabled attribute of the Page object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the Page object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Page object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Page object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the Page object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Page object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Page object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Page object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Page object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Page object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the buildPageVersionList attribute of the Page object
   *
   * @param  tmp  The new buildPageVersionList value
   */
  public void setBuildPageVersionList(boolean tmp) {
    this.buildPageVersionList = tmp;
  }


  /**
   *  Sets the buildPageVersionList attribute of the Page object
   *
   * @param  tmp  The new buildPageVersionList value
   */
  public void setBuildPageVersionList(String tmp) {
    this.buildPageVersionList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the pageVersionList attribute of the Page object
   *
   * @param  tmp  The new pageVersionList value
   */
  public void setPageVersionList(PageVersionList tmp) {
    this.pageVersionList = tmp;
  }


  /**
   *  Sets the override attribute of the Page object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the Page object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the buildTabBanner attribute of the Page object
   *
   * @param  tmp  The new buildTabBanner value
   */
  public void setBuildTabBanner(boolean tmp) {
    this.buildTabBanner = tmp;
  }


  /**
   *  Sets the buildTabBanner attribute of the Page object
   *
   * @param  tmp  The new buildTabBanner value
   */
  public void setBuildTabBanner(String tmp) {
    this.buildTabBanner = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the tabBanner attribute of the Page object
   *
   * @param  tmp  The new tabBanner value
   */
  public void setTabBanner(TabBanner tmp) {
    this.tabBanner = tmp;
  }


  /**
   *  Sets the mode attribute of the Page object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(int tmp) {
    this.mode = tmp;
  }


  /**
   *  Sets the mode attribute of the Page object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(String tmp) {
    this.mode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildPageVersionToView attribute of the Page object
   *
   * @param  tmp  The new buildPageVersionToView value
   */
  public void setBuildPageVersionToView(boolean tmp) {
    this.buildPageVersionToView = tmp;
  }


  /**
   *  Sets the buildPageVersionToView attribute of the Page object
   *
   * @param  tmp  The new buildPageVersionToView value
   */
  public void setBuildPageVersionToView(String tmp) {
    this.buildPageVersionToView = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the pageVersionToView attribute of the Page object
   *
   * @param  tmp  The new pageVersionToView value
   */
  public void setPageVersionToView(PageVersion tmp) {
    this.pageVersionToView = tmp;
  }


  /**
   *  Gets the id attribute of the Page object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the name attribute of the Page object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the position attribute of the Page object
   *
   * @return    The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   *  Gets the activePageVersionId attribute of the Page object
   *
   * @return    The activePageVersionId value
   */
  public int getActivePageVersionId() {
    return activePageVersionId;
  }


  /**
   *  Gets the constructionPageVersionId attribute of the Page object
   *
   * @return    The constructionPageVersionId value
   */
  public int getConstructionPageVersionId() {
    return constructionPageVersionId;
  }


  /**
   *  Gets the pageGroupId attribute of the Page object
   *
   * @return    The pageGroupId value
   */
  public int getPageGroupId() {
    return pageGroupId;
  }


  /**
   *  Gets the tabBannerId attribute of the Page object
   *
   * @return    The tabBannerId value
   */
  public int getTabBannerId() {
    return tabBannerId;
  }


  /**
   *  Gets the notes attribute of the Page object
   *
   * @return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the enabled attribute of the Page object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the enteredBy attribute of the Page object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the Page object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the Page object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the Page object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the buildPageVersionList attribute of the Page object
   *
   * @return    The buildPageVersionList value
   */
  public boolean getBuildPageVersionList() {
    return buildPageVersionList;
  }


  /**
   *  Gets the pageVersionList attribute of the Page object
   *
   * @return    The pageVersionList value
   */
  public PageVersionList getPageVersionList() {
    return pageVersionList;
  }


  /**
   *  Gets the override attribute of the Page object
   *
   * @return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Gets the buildTabBanner attribute of the Page object
   *
   * @return    The buildTabBanner value
   */
  public boolean getBuildTabBanner() {
    return buildTabBanner;
  }


  /**
   *  Gets the tabBanner attribute of the Page object
   *
   * @return    The tabBanner value
   */
  public TabBanner getTabBanner() {
    return tabBanner;
  }


  /**
   *  Gets the mode attribute of the Page object
   *
   * @return    The mode value
   */
  public int getMode() {
    return mode;
  }


  /**
   *  Gets the buildPageVersionToView attribute of the Page object
   *
   * @return    The buildPageVersionToView value
   */
  public boolean getBuildPageVersionToView() {
    return buildPageVersionToView;
  }


  /**
   *  Gets the pageVersionToView attribute of the Page object
   *
   * @return    The pageVersionToView value
   */
  public PageVersion getPageVersionToView() {
    return pageVersionToView;
  }


  /**
   *  Gets the previousPageId attribute of the Page object
   *
   * @return    The previousPageId value
   */
  public int getPreviousPageId() {
    return previousPageId;
  }


  /**
   *  Sets the previousPageId attribute of the Page object
   *
   * @param  tmp  The new previousPageId value
   */
  public void setPreviousPageId(int tmp) {
    this.previousPageId = tmp;
  }


  /**
   *  Sets the previousPageId attribute of the Page object
   *
   * @param  tmp  The new previousPageId value
   */
  public void setPreviousPageId(String tmp) {
    this.previousPageId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the nextPageId attribute of the Page object
   *
   * @return    The nextPageId value
   */
  public int getNextPageId() {
    return nextPageId;
  }


  /**
   *  Sets the nextPageId attribute of the Page object
   *
   * @param  tmp  The new nextPageId value
   */
  public void setNextPageId(int tmp) {
    this.nextPageId = tmp;
  }


  /**
   *  Sets the nextPageId attribute of the Page object
   *
   * @param  tmp  The new nextPageId value
   */
  public void setNextPageId(String tmp) {
    this.nextPageId = Integer.parseInt(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  tmpPageId      Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpPageId) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM page " +
        " WHERE page_id = ? ");
    pst.setInt(1, tmpPageId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Page record not found");
    }

    if (buildTabBanner) {
      this.buildTabBanner(db);
    }
    if (buildPageVersionList) {
      this.buildPageVersionList(db);
    }
    if (buildPageVersionToView) {
      this.buildPageVersionToView(db);
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

    id = DatabaseUtils.getNextSeq(db, "page_page_id_seq");

    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO page " +
        "(" + (id > -1 ? "page_id, " : "") +
        "page_name , " +
        "page_position , " +
        "active_page_version_id , " +
        "construction_page_version_id , " +
        "page_group_id , " +
        "tab_banner_id , " +
        "notes , " +
        "enabled , " +
        "enteredby , " +
        "modifiedby ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, name);
    pst.setInt(++i, position);
    DatabaseUtils.setInt(pst, ++i, activePageVersionId);
    DatabaseUtils.setInt(pst, ++i, constructionPageVersionId);
    DatabaseUtils.setInt(pst, ++i, pageGroupId);
    DatabaseUtils.setInt(pst, ++i, tabBannerId);
    pst.setString(++i, notes);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "page_page_id_seq", id);
    pst.close();

    updateRelatedPages(db, true);

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
        "UPDATE page " +
        "SET " +
        "page_name = ? , " +
        "page_position = ? , " +
        "active_page_version_id = ? , " +
        "construction_page_version_id = ? , " +
        "page_group_id = ? , " +
        "tab_banner_id = ? , " +
        "notes = ? , " +
        "enabled = ?  ");

    if (!override) {
      sql.append(
          " , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE page_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, name);
    pst.setInt(++i, position);
    DatabaseUtils.setInt(pst, ++i, activePageVersionId);
    DatabaseUtils.setInt(pst, ++i, constructionPageVersionId);
    DatabaseUtils.setInt(pst, ++i, pageGroupId);
    DatabaseUtils.setInt(pst, ++i, tabBannerId);
    pst.setString(++i, notes);
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
      PreparedStatement pst = db.prepareStatement(
          "UPDATE page SET " +
          "active_page_version_id = ?,  " +
          "construction_page_version_id = ? " +
          "WHERE page_id =  ? ");

      DatabaseUtils.setInt(pst, 1, -1);
      DatabaseUtils.setInt(pst, 2, -1);
      DatabaseUtils.setInt(pst, 3, this.getId());
      pst.execute();
      pst.close();

      pageVersionList = new PageVersionList();
      pageVersionList.setPageId(this.getId());
      pageVersionList.buildList(db);
      pageVersionList.delete(db);
      pageVersionList = null;

      updateRelatedPages(db, false);

      pst = db.prepareStatement(
          "DELETE FROM page " +
          "WHERE page_id =  ? ");

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
    id = rs.getInt("page_id");
    name = rs.getString("page_name");
    position = rs.getInt("page_position");
    activePageVersionId = DatabaseUtils.getInt(rs, "active_page_version_id");
    constructionPageVersionId = DatabaseUtils.getInt(rs, "construction_page_version_id");
    pageGroupId = DatabaseUtils.getInt(rs, "page_group_id");
    tabBannerId = DatabaseUtils.getInt(rs, "tab_banner_id");
    notes = rs.getString("notes");
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
  public void buildTabBanner(Connection db) throws SQLException {
    tabBanner = new TabBanner(db, this.getTabBannerId());
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildPageVersionList(Connection db) throws SQLException {
    pageVersionList = new PageVersionList();
    pageVersionList.setPageId(this.getId());
    pageVersionList.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildPageVersionToView(Connection db) throws SQLException {
    pageVersionToView = new PageVersion();
    pageVersionToView.setMode(mode);
    pageVersionToView.setBuildPageRowList(true);
    if (mode == Site.EDIT_MODE) {
      //Get the constructionPageVersionId if it exists.
      //Get the active page version id if it exists and the other is -1;
      if (constructionPageVersionId != -1) {
        pageVersionToView.queryRecord(db, constructionPageVersionId);
      } else if (activePageVersionId != -1) {
        pageVersionToView.queryRecord(db, activePageVersionId);
      }
    } else {
      pageVersionToView.queryRecord(db, activePageVersionId);
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
      throw new SQLException("Row ID not specified");
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
  public int computePagePosition(Connection db) throws SQLException {
    int result = Page.INITIAL_POSITION;
    Page otherPage = new Page();
    if (previousPageId != -1) {
      otherPage.queryRecord(db, previousPageId);
      result = otherPage.getPosition() + 1;
    } else if (nextPageId != -1) {
      otherPage.queryRecord(db, nextPageId);
      result = otherPage.getPosition();
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
  public void updateRelatedPages(Connection db, boolean add) throws SQLException {
    if (previousPageId > -1) {
      PageList.updateRelatedPages(db, this.getId(), previousPageId, this.getPageGroupId(), false, add);
    } else if (nextPageId > -1) {
      PageList.updateRelatedPages(db, this.getId(), nextPageId, this.getPageGroupId(), true, add);
    } else if (!add) {
      PageList.updateRelatedPages(db, this.getId(), -1, this.getPageGroupId(), false, add);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  moveUp            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void move(Connection db, boolean moveUp) throws SQLException {
    Page switchPage = null;
    PageList pages = new PageList();
    pages.setPageGroupId(this.getPageGroupId());
    pages.setPosition((moveUp ? this.getPosition() - 1 : this.getPosition() + 1));
    pages.buildList(db);
    if (pages.size() > 0) {
      switchPage = (Page) pages.get(0);
      int switchPagePosition = this.getPosition();
      this.setPosition(switchPage.getPosition());
      this.update(db);
      switchPage.setPosition(switchPagePosition);
      switchPage.update(db);
    }
  }
}

