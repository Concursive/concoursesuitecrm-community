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
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author kailash
 * @version $Id: Exp $
 * @created February 10, 2006 $Id: Exp $
 */
public class Site extends GenericBean {

  public static int EDIT_MODE = 602171327;
  public static int PORTAL_MODE = 602171328;

  private int id = -1;
  private String name = null;
  private String internalDescription = null;
  private int hitCount = 0;
  private String notes = null;
  private boolean enabled = false;
  private int layoutId = -1;
  private int styleId = -1;
	private int logoImageId = -1;
  private String template = null;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;

  private boolean buildTabList = false;
  private TabList tabList = null;
  private boolean override = true;
  private Tab tabToDisplay = null;


  /**
   * Constructor for the Site object
   */
  public Site() {
  }


  /**
   * Constructor for the Site object
   *
   * @param db        Description of the Parameter
   * @param tmpSiteId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Site(Connection db, int tmpSiteId) throws SQLException {
    queryRecord(db, tmpSiteId);
  }


  /**
   * Constructor for the Site object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Site(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the Site object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Site object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the siteName attribute of the Site object
   *
   * @param tmp The new siteName value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the internalDescription attribute of the Site object
   *
   * @param tmp The new internalDescription value
   */
  public void setInternalDescription(String tmp) {
    this.internalDescription = tmp;
  }


  /**
   * Sets the hitCount attribute of the Site object
   *
   * @param tmp The new hitCount value
   */
  public void setHitCount(int tmp) {
    this.hitCount = tmp;
  }


  /**
   * Sets the hitCount attribute of the Site object
   *
   * @param tmp The new hitCount value
   */
  public void setHitCount(String tmp) {
    this.hitCount = Integer.parseInt(tmp);
  }


  /**
   * Sets the notes attribute of the Site object
   *
   * @param tmp The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   * Sets the enabled attribute of the Site object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the Site object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the layoutId attribute of the Site object
   *
   * @param tmp The new layoutId value
   */
  public void setLayoutId(int tmp) {
    this.layoutId = tmp;
  }


  /**
   * Sets the layoutId attribute of the Site object
   *
   * @param tmp The new layoutId value
   */
  public void setLayoutId(String tmp) {
    this.layoutId = Integer.parseInt(tmp);
  }


  /**
   * Sets the styleId attribute of the Site object
   *
   * @param tmp The new styleId value
   */
  public void setStyleId(int tmp) {
    this.styleId = tmp;
  }


  /**
   * Sets the styleId attribute of the Site object
   *
   * @param tmp The new styleId value
   */
  public void setStyleId(String tmp) {
    this.styleId = Integer.parseInt(tmp);
  }


  /**
   * Sets the logoImageId attribute of the Site object
   *
   * @param tmp The new logoImageId value
   */
	public void setLogoImageId(int tmp) {
		this.logoImageId = tmp;
	}


  /**
   * Sets the logoImageId attribute of the Site object
   *
   * @param tmp The new logoImageId value
   */
	public void setLogoImageId(String tmp) {
		this.logoImageId = Integer.parseInt(tmp);
	}

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  /**
   * Sets the enteredBy attribute of the Site object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Site object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the Site object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the Site object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the Site object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the Site object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the Site object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the Site object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the buildTabList attribute of the Site object
   *
   * @param tmp The new buildTabList value
   */
  public void setBuildTabList(boolean tmp) {
    this.buildTabList = tmp;
  }


  /**
   * Sets the buildTabList attribute of the Site object
   *
   * @param tmp The new buildTabList value
   */
  public void setBuildTabList(String tmp) {
    this.buildTabList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the tabList attribute of the Site object
   *
   * @param tmp The new tabList value
   */
  public void setTabList(TabList tmp) {
    this.tabList = tmp;
  }


  /**
   * Sets the override attribute of the Site object
   *
   * @param tmp The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   * Sets the override attribute of the Site object
   *
   * @param tmp The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the id attribute of the Site object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the siteName attribute of the Site object
   *
   * @return The siteName value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the internalDescription attribute of the Site object
   *
   * @return The internalDescription value
   */
  public String getInternalDescription() {
    return internalDescription;
  }


  /**
   * Gets the hitCount attribute of the Site object
   *
   * @return The hitCount value
   */
  public int getHitCount() {
    return hitCount;
  }


  /**
   * Gets the notes attribute of the Site object
   *
   * @return The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   * Gets the enabled attribute of the Site object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the layoutId attribute of the Site object
   *
   * @return The layoutId value
   */
  public int getLayoutId() {
    return layoutId;
  }


  /**
   * Gets the styleId attribute of the Site object
   *
   * @return The styleId value
   */
  public int getStyleId() {
    return styleId;
  }


  /**
   * Gets the logoImageId attribute of the Site object
   *
   * @return The logoImageId value
   */
	public int getLogoImageId() {
		return logoImageId;
	}

  /**
   * Gets the enteredBy attribute of the Site object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the entered attribute of the Site object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the modifiedBy attribute of the Site object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the modified attribute of the Site object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the buildTabList attribute of the Site object
   *
   * @return The buildTabList value
   */
  public boolean getBuildTabList() {
    return buildTabList;
  }


  /**
   * Gets the tabList attribute of the Site object
   *
   * @return The tabList value
   */
  public TabList getTabList() {
    if (tabList != null) {
      return tabList;
    }
    return new TabList();
  }


  /**
   * Gets the override attribute of the Site object
   *
   * @return The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   * Gets the tabToDisplay attribute of the Site object
   *
   * @return The tabToDisplay value
   */
  public Tab getTabToDisplay() {
    return tabToDisplay;
  }


  /**
   * Sets the tabToDisplay attribute of the Site object
   *
   * @param tmp The new tabToDisplay value
   */
  public void setTabToDisplay(Tab tmp) {
    this.tabToDisplay = tmp;
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param tmpSiteId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpSiteId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
      " SELECT * " +
        " FROM web_site " +
        " WHERE site_id = ? ");
    pst.setInt(1, tmpSiteId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Site record not found");
    }
    if (buildTabList) {
      buildTabList(db);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean autoCommit = db.getAutoCommit();
    try {
      if (autoCommit) {
        db.setAutoCommit(false);
      }
      if (enabled) {
        SiteList.disableOtherSites(db);
      }
      id = DatabaseUtils.getNextSeq(db, "web_site_site_id_seq");
      PreparedStatement pst = db.prepareStatement(
        "INSERT INTO web_site " +
          "(" + (id > -1 ? "site_id, " : "") +
          "site_name , " +
          "internal_description , " +
          "hit_count , " +
          "notes , " +
          "enabled , " +
          "layout_id , " +
          "style_id , " +
					"logo_image_id, " +
          "enteredby , " +
          "modifiedby ) " +
          "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?,?,?,?,?)");
      int i = 0;
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setString(++i, name);
      pst.setString(++i, internalDescription);
      DatabaseUtils.setInt(pst, ++i, (hitCount > 0 ? hitCount : -1));
      pst.setString(++i, notes);
      pst.setBoolean(++i, enabled);
      DatabaseUtils.setInt(pst, ++i, layoutId);
      DatabaseUtils.setInt(pst, ++i, styleId);
      DatabaseUtils.setInt(pst, ++i, logoImageId);
      pst.setInt(++i, modifiedBy);
      pst.setInt(++i, modifiedBy);
      pst.execute();
      id = DatabaseUtils.getCurrVal(db, "web_site_site_id_seq", id);
      pst.close();
      if (autoCommit) {
        db.commit();
      }
    } catch (Exception e) {
      if (autoCommit) {
        db.rollback();
      }
      throw new SQLException("Site-> Error: " + e.getMessage());
    } finally {
      if (autoCommit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (this.getEnabled()) {
      SiteList.disableOtherSites(db);
    }
    int resultCount = -1;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
      "UPDATE web_site " +
        "SET " +
        "site_name = ?, " +
        "internal_description = ?, " +
        (hitCount > 0 ? "hit_count = ?, " : "") +
        "notes = ?, " +
        (layoutId > -1 ? "layout_id = ?, " : "") +
        (styleId > -1 ? "style_id = ?, " : "") +
				(logoImageId > -1 ? "logo_image_id = ?, " : "") +
        "enabled = ? "
    );

    if (!override) {
      sql.append(
        " , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE site_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setString(++i, name);
    pst.setString(++i, internalDescription);
    if (hitCount > 0) {
      DatabaseUtils.setInt(pst, ++i, (hitCount > 0 ? hitCount : -1));
    }
    pst.setString(++i, notes);
    if (layoutId > -1) {
      DatabaseUtils.setInt(pst, ++i, layoutId);
    }
    if (styleId > -1) {
      DatabaseUtils.setInt(pst, ++i, styleId);
    }
    if (logoImageId > -1) {
      DatabaseUtils.setInt(pst, ++i, logoImageId);
    }
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
   * Description of the Method
   *
   * @param db   Description of the Parameter
   * @param flag Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db, boolean flag) throws SQLException {
    this.setOverride(flag);
    return update(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {

    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }

      SiteLogList tmpSiteLogList = new SiteLogList();
      tmpSiteLogList.setSiteId(this.getId());
      tmpSiteLogList.buildList(db);
      tmpSiteLogList.delete(db);
      tmpSiteLogList = null;

      TabList tmpTabList = new TabList();
      tmpTabList.setSiteId(this.getId());
      tmpTabList.buildList(db);
      tmpTabList.delete(db);
      tmpTabList = null;

      PreparedStatement pst = db.prepareStatement(
        "DELETE FROM web_site " +
          "WHERE site_id = ? ");

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
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("site_id");
    name = rs.getString("site_name");
    internalDescription = rs.getString("internal_description");
    hitCount = DatabaseUtils.getInt(rs, "hit_count", 0);
    notes = rs.getString("notes");
    enabled = rs.getBoolean("enabled");
    layoutId = DatabaseUtils.getInt(rs, "layout_id");
    styleId = DatabaseUtils.getInt(rs, "style_id");
    logoImageId = DatabaseUtils.getInt(rs, "logo_image_id");

    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildTabList(Connection db) throws SQLException {
    buildTabList(db, Constants.UNDEFINED);
  }

  public void buildTabList(Connection db, int enabled) throws SQLException {
    tabList = new TabList();
    tabList.setSiteId(this.getId());
    tabList.setEnabled(enabled);
    tabList.buildList(db);
  }


  /**
   * fetches all the objects required to display a page
   *
   * @param db     Description of the Parameter
   * @param tabId  Description of the Parameter
   * @param mode   Description of the Parameter
   * @param pageId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResources(Connection db, int tabId, int pageId, int mode) throws SQLException {
    if (tabList == null) {
      buildTabList(db);
    }
    // get tab to view
    Iterator tabIterator = tabList.iterator();
    while (tabIterator.hasNext()) {
      Tab thisTab = (Tab) tabIterator.next();
      if (thisTab.getId() == tabId) {
        thisTab.setMode(mode);

        // All the contents of this page will be built
        thisTab.setPageToBuild(pageId);

        //For the menus
        thisTab.setBuildPages(true);
        thisTab.buildPageGroupList(db);

        //For building the tab banner associated with the tab
        thisTab.buildTabBanner(db);
        this.setTabToDisplay(thisTab);
      }
    }
  }
  
  public void buildRowsColumns(ArrayList rowColumnList) {
    Page page = this.getTabToDisplay().getThisPageToBuild();
    PageRowList rowList = page.getPageVersionToView().getPageRowList();
    rowList.buildRowsColumns(rowColumnList, 0);
  }

  public void updateLogoImageId(Connection db, int newLogoImageId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "UPDATE web_site " +
        "SET logo_image_id = ? " +
        "WHERE site_id = ?"
    );
    DatabaseUtils.setInt(pst, 1, newLogoImageId);
    pst.setInt(2, id);
    pst.executeUpdate();
    logoImageId = newLogoImageId;
  }

  public void updateLayoutId(Connection db, int newLayoutId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "UPDATE web_site " +
        "SET layout_id = ? " +
        "WHERE site_id = ?"
    );
    DatabaseUtils.setInt(pst, 1, newLayoutId);
    pst.setInt(2, id);
    pst.executeUpdate();
    layoutId = newLayoutId;
  }

  public void updateStyleId(Connection db, int newStyleId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "UPDATE web_site " +
        "SET style_id = ? " +
        "WHERE site_id = ?"
    );
    DatabaseUtils.setInt(pst, 1, newStyleId);
    pst.setInt(2, id);
    pst.executeUpdate();
    styleId = newStyleId;
  }
}

