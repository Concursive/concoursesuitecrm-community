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
public class PageGroup extends GenericBean {

  public final static int INITIAL_POSITION = 0;
  private int id = -1;
  private String name = null;
  private String internalDescription = null;
  private int position = -1;
  private int tabId = -1;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;

  private PageList pageList = null;
  private int pageToBuild = -1;
  private boolean override = true;
  private int mode = -1;
  private Page thisPageToBuild = null;

  private int previousPageGroupId = -1;
  private int nextPageGroupId = -1;


  /**
   *  Constructor for the PageGroup object
   */
  public PageGroup() { }


  /**
   *  Constructor for the PageGroup object
   *
   * @param  db                Description of the Parameter
   * @param  tmpPageGroupId    Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public PageGroup(Connection db, int tmpPageGroupId) throws SQLException {
    queryRecord(db, tmpPageGroupId);
  }


  /**
   *  Constructor for the PageGroup object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public PageGroup(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the PageGroup object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PageGroup object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the PageGroup object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the internalDescription attribute of the PageGroup object
   *
   * @param  tmp  The new internalDescription value
   */
  public void setInternalDescription(String tmp) {
    this.internalDescription = tmp;
  }


  /**
   *  Sets the position attribute of the PageGroup object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   *  Sets the position attribute of the PageGroup object
   *
   * @param  tmp  The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
  }


  /**
   *  Sets the tabId attribute of the PageGroup object
   *
   * @param  tmp  The new tabId value
   */
  public void setTabId(int tmp) {
    this.tabId = tmp;
  }


  /**
   *  Sets the tabId attribute of the PageGroup object
   *
   * @param  tmp  The new tabId value
   */
  public void setTabId(String tmp) {
    this.tabId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the PageGroup object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the PageGroup object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the PageGroup object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the PageGroup object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the PageGroup object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the PageGroup object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the PageGroup object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the PageGroup object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the pageList attribute of the PageGroup object
   *
   * @param  tmp  The new pageList value
   */
  public void setPageList(PageList tmp) {
    this.pageList = tmp;
  }


  /**
   *  Sets the pageToBuild attribute of the PageGroup object
   *
   * @param  tmp  The new pageToBuild value
   */
  public void setPageToBuild(int tmp) {
    this.pageToBuild = tmp;
  }


  /**
   *  Sets the pageToBuild attribute of the PageGroup object
   *
   * @param  tmp  The new pageToBuild value
   */
  public void setPageToBuild(String tmp) {
    this.pageToBuild = Integer.parseInt(tmp);
  }


  /**
   *  Sets the override attribute of the PageGroup object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the PageGroup object
   *
   * @param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the mode attribute of the PageGroup object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(int tmp) {
    this.mode = tmp;
  }


  /**
   *  Sets the mode attribute of the PageGroup object
   *
   * @param  tmp  The new mode value
   */
  public void setMode(String tmp) {
    this.mode = Integer.parseInt(tmp);
  }


  /**
   *  Gets the id attribute of the PageGroup object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the name attribute of the PageGroup object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the internalDescription attribute of the PageGroup object
   *
   * @return    The internalDescription value
   */
  public String getInternalDescription() {
    return internalDescription;
  }


  /**
   *  Gets the position attribute of the PageGroup object
   *
   * @return    The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   *  Gets the tabId attribute of the PageGroup object
   *
   * @return    The tabId value
   */
  public int getTabId() {
    return tabId;
  }


  /**
   *  Gets the enteredBy attribute of the PageGroup object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the PageGroup object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the PageGroup object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the PageGroup object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the pageToBuild attribute of the PageGroup object
   *
   * @return    The pageToBuild value
   */
  public int getPageToBuild() {
    return pageToBuild;
  }


  /**
   *  Gets the override attribute of the PageGroup object
   *
   * @return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Gets the mode attribute of the PageGroup object
   *
   * @return    The mode value
   */
  public int getMode() {
    return mode;
  }


  /**
   *  Gets the pageList attribute of the PageGroup object
   *
   * @return    The pageList value
   */
  public PageList getPageList() {
    return pageList;
  }


  /**
   *  Gets the thisPageToBuild attribute of the PageGroup object
   *
   * @return    The thisPageToBuild value
   */
  public Page getThisPageToBuild() {
    return thisPageToBuild;
  }


  /**
   *  Sets the thisPageToBuild attribute of the PageGroup object
   *
   * @param  tmp  The new thisPageToBuild value
   */
  public void setThisPageToBuild(Page tmp) {
    this.thisPageToBuild = tmp;
  }


  /**
   *  Gets the previousPageGroupId attribute of the PageGroup object
   *
   * @return    The previousPageGroupId value
   */
  public int getPreviousPageGroupId() {
    return previousPageGroupId;
  }


  /**
   *  Sets the previousPageGroupId attribute of the PageGroup object
   *
   * @param  tmp  The new previousPageGroupId value
   */
  public void setPreviousPageGroupId(int tmp) {
    this.previousPageGroupId = tmp;
  }


  /**
   *  Sets the previousPageGroupId attribute of the PageGroup object
   *
   * @param  tmp  The new previousPageGroupId value
   */
  public void setPreviousPageGroupId(String tmp) {
    this.previousPageGroupId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the nextPageGroupId attribute of the PageGroup object
   *
   * @return    The nextPageGroupId value
   */
  public int getNextPageGroupId() {
    return nextPageGroupId;
  }


  /**
   *  Sets the nextPageGroupId attribute of the PageGroup object
   *
   * @param  tmp  The new nextPageGroupId value
   */
  public void setNextPageGroupId(int tmp) {
    this.nextPageGroupId = tmp;
  }


  /**
   *  Sets the nextPageGroupId attribute of the PageGroup object
   *
   * @param  tmp  The new nextPageGroupId value
   */
  public void setNextPageGroupId(String tmp) {
    this.nextPageGroupId = Integer.parseInt(tmp);
  }


  /**
   *  Description of the Method
   *
   * @param  db              Description of the Parameter
   * @param  tmpPageGroupId  Description of the Parameter
   * @return                 Description of the Return Value
   * @throws  SQLException   Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpPageGroupId) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM web_page_group " +
        " WHERE page_group_id = ? ");
    pst.setInt(1, tmpPageGroupId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Page Group record not found");
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

    id = DatabaseUtils.getNextSeq(db, "web_page_group_page_group_id_seq");

    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO web_page_group " +
        "(" + (id > -1 ? "page_group_id, " : "") +
        "group_name , " +
        "internal_description , " +
        "group_position , " +
        "tab_id , " +
        "enteredby , " +
        "modifiedby ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, name);
    pst.setString(++i, internalDescription);
    pst.setInt(++i, position);
    pst.setInt(++i, tabId);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "web_page_group_page_group_id_seq", id);
    pst.close();

    updateRelatedPageGroups(db, true);

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
        "UPDATE web_page_group " +
        "SET " +
        "group_name = ? , " +
        "internal_description = ? , " +
        "group_position = ? , " +
        "tab_id = ?  ");

    if (!override) {
      sql.append(
          " , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE page_group_id = ? ");
    if (!override) {
      sql.append("AND modified = ? ");
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, name);
    pst.setString(++i, internalDescription);
    pst.setInt(++i, position);
    pst.setInt(++i, tabId);
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

      PageList tmpPageList = new PageList();
      tmpPageList.setPageGroupId(this.getId());
      tmpPageList.buildList(db);
      tmpPageList.delete(db);
      tmpPageList = null;

      updateRelatedPageGroups(db, false);

      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM web_page_group " +
          "WHERE page_group_id =  ? ");

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
    id = rs.getInt("page_group_id");
    name = rs.getString("group_name");
    internalDescription = rs.getString("internal_description");
    position = rs.getInt("group_position");
    tabId = rs.getInt("tab_id");

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
  public void buildPageList(Connection db) throws SQLException {
    pageList = new PageList();
    pageList.setPageGroupId(this.getId());
    pageList.setMode(mode);
    pageList.setPageToBuild(pageToBuild);
    pageList.buildList(db);
    if (pageToBuild != -1 && pageList.getThisPageToBuild() != null) {
      this.setThisPageToBuild(pageList.getThisPageToBuild());
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
  public int computePageGroupPosition(Connection db) throws SQLException {
    int result = PageGroup.INITIAL_POSITION;
    PageGroup otherPageGroup = new PageGroup();
    if (previousPageGroupId != -1) {
      otherPageGroup.queryRecord(db, previousPageGroupId);
      result = otherPageGroup.getPosition() + 1;
    } else if (nextPageGroupId != -1) {
      otherPageGroup.queryRecord(db, nextPageGroupId);
      result = otherPageGroup.getPosition();
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
  public void updateRelatedPageGroups(Connection db, boolean add) throws SQLException {
    if (previousPageGroupId > -1) {
      PageGroupList.updateRelatedPageGroups(db, this.getId(), previousPageGroupId, this.getTabId(), false, add);
    } else if (nextPageGroupId > -1) {
      PageGroupList.updateRelatedPageGroups(db, this.getId(), nextPageGroupId, this.getTabId(), true, add);
    } else if (!add) {
      PageGroupList.updateRelatedPageGroups(db, this.getId(), -1, this.getTabId(), false, add);
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
    PageGroup switchPageGroup = null;
    PageGroupList groups = new PageGroupList();
    groups.setTabId(this.getTabId());
    groups.setPosition((moveUp ? this.getPosition() - 1 : this.getPosition() + 1));
    groups.buildList(db);
    if (groups.size() > 0) {
      switchPageGroup = (PageGroup) groups.get(0);
      int switchPageGroupPosition = this.getPosition();
      this.setPosition(switchPageGroup.getPosition());
      this.update(db);
      switchPageGroup.setPosition(switchPageGroupPosition);
      switchPageGroup.update(db);
    }
  }

  /**
     * The pageGroup is a secondary menu that is always shown in edit mode,
     * but ownly shown in portal mode under certain conditions
     *
     * @return true if the secondary menu should be shown in portal mode
     */
    public boolean canDisplay() {
    if (pageList != null && pageList.size() > 1) {
      return true;
    }
    return false;
  }
}

