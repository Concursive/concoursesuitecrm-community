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
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 *@author     kailash
 *@created    February 10, 2006 $Id: Exp $
 *@version    $Id: Exp $
 */
public class PageVersion extends GenericBean {

  public static final int INITIAL_VERSION = 1;
  private int id = -1;
  private int versionNumber = -1;
  private String internalDescription = null;
  private String notes = null;
  private int parentPageVersionId = -1;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private int pageId = -1;

  private boolean override = false;

  private boolean buildPageRowList = false;
  private PageRowList pageRowList = null;
  private int mode = -1;

  private boolean isActive = false;
  private boolean isConstruction = false;


  /**
   *  Constructor for the PageVersion object
   */
  public PageVersion() { }


  /**
   *  Constructor for the PageVersion object
   *
   *@param  db                Description of the Parameter
   *@param  tmpPageVersionId  Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@throws  SQLException     Description of the Exception
   */
  public PageVersion(Connection db, int tmpPageVersionId) throws SQLException {
    queryRecord(db, tmpPageVersionId);
  }


  /**
   *  Constructor for the PageVersion object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@throws  SQLException     Description of the Exception
   */
  public PageVersion(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the PageVersion object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the PageVersion object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the versionNumber attribute of the PageVersion object
   *
   *@param  tmp  The new versionNumber value
   */
  public void setVersionNumber(int tmp) {
    this.versionNumber = tmp;
  }


  /**
   *  Sets the versionNumber attribute of the PageVersion object
   *
   *@param  tmp  The new versionNumber value
   */
  public void setVersionNumber(String tmp) {
    this.versionNumber = Integer.parseInt(tmp);
  }


  /**
   *  Sets the internalDescription attribute of the PageVersion object
   *
   *@param  tmp  The new internalDescription value
   */
  public void setInternalDescription(String tmp) {
    this.internalDescription = tmp;
  }


  /**
   *  Sets the notes attribute of the PageVersion object
   *
   *@param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the parentPageVersionId attribute of the PageVersion object
   *
   *@param  tmp  The new parentPageVersionId value
   */
  public void setParentPageVersionId(int tmp) {
    this.parentPageVersionId = tmp;
  }


  /**
   *  Sets the parentPageVersionId attribute of the PageVersion object
   *
   *@param  tmp  The new parentPageVersionId value
   */
  public void setParentPageVersionId(String tmp) {
    this.parentPageVersionId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the PageVersion object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the PageVersion object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the PageVersion object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the PageVersion object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the PageVersion object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the PageVersion object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the PageVersion object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the PageVersion object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the pageId attribute of the PageVersion object
   *
   *@param  tmp  The new pageId value
   */
  public void setPageId(int tmp) {
    this.pageId = tmp;
  }


  /**
   *  Sets the pageId attribute of the PageVersion object
   *
   *@param  tmp  The new pageId value
   */
  public void setPageId(String tmp) {
    this.pageId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the override attribute of the PageVersion object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the PageVersion object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the buildPageRowList attribute of the PageVersion object
   *
   *@param  tmp  The new buildPageRowList value
   */
  public void setBuildPageRowList(boolean tmp) {
    this.buildPageRowList = tmp;
  }


  /**
   *  Sets the buildPageRowList attribute of the PageVersion object
   *
   *@param  tmp  The new buildPageRowList value
   */
  public void setBuildPageRowList(String tmp) {
    this.buildPageRowList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the pageRowList attribute of the PageVersion object
   *
   *@param  tmp  The new pageRowList value
   */
  public void setPageRowList(PageRowList tmp) {
    this.pageRowList = tmp;
  }


  /**
   *  Sets the mode attribute of the PageVersion object
   *
   *@param  tmp  The new mode value
   */
  public void setMode(int tmp) {
    this.mode = tmp;
  }


  /**
   *  Sets the mode attribute of the PageVersion object
   *
   *@param  tmp  The new mode value
   */
  public void setMode(String tmp) {
    this.mode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the isActive attribute of the PageVersion object
   *
   *@param  tmp  The new isActive value
   */
  public void setIsActive(boolean tmp) {
    this.isActive = tmp;
  }


  /**
   *  Sets the isActive attribute of the PageVersion object
   *
   *@param  tmp  The new isActive value
   */
  public void setIsActive(String tmp) {
    this.isActive = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the isConstruction attribute of the PageVersion object
   *
   *@param  tmp  The new isConstruction value
   */
  public void setIsConstruction(boolean tmp) {
    this.isConstruction = tmp;
  }


  /**
   *  Sets the isConstruction attribute of the PageVersion object
   *
   *@param  tmp  The new isConstruction value
   */
  public void setIsConstruction(String tmp) {
    this.isConstruction = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the PageVersion object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the versionNumber attribute of the PageVersion object
   *
   *@return    The versionNumber value
   */
  public int getVersionNumber() {
    return versionNumber;
  }


  /**
   *  Gets the internalDescription attribute of the PageVersion object
   *
   *@return    The internalDescription value
   */
  public String getInternalDescription() {
    return internalDescription;
  }


  /**
   *  Gets the notes attribute of the PageVersion object
   *
   *@return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the parentPageVersionId attribute of the PageVersion object
   *
   *@return    The parentPageVersionId value
   */
  public int getParentPageVersionId() {
    return parentPageVersionId;
  }


  /**
   *  Gets the enteredBy attribute of the PageVersion object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the PageVersion object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the PageVersion object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the PageVersion object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the pageId attribute of the PageVersion object
   *
   *@return    The pageId value
   */
  public int getPageId() {
    return pageId;
  }


  /**
   *  Gets the override attribute of the PageVersion object
   *
   *@return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Gets the buildPageRowList attribute of the PageVersion object
   *
   *@return    The buildPageRowList value
   */
  public boolean getBuildPageRowList() {
    return buildPageRowList;
  }


  /**
   *  Gets the pageRowList attribute of the PageVersion object
   *
   *@return    The pageRowList value
   */
  public PageRowList getPageRowList() {
    return pageRowList;
  }


  /**
   *  Gets the mode attribute of the PageVersion object
   *
   *@return    The mode value
   */
  public int getMode() {
    return mode;
  }


  /**
   *  Gets the isActive attribute of the PageVersion object
   *
   *@return    The isActive value
   */
  public boolean getIsActive() {
    return isActive;
  }


  /**
   *  Gets the isConstruction attribute of the PageVersion object
   *
   *@return    The isConstruction value
   */
  public boolean getIsConstruction() {
    return isConstruction;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  tmpPageVersionId  Description of the Parameter
   *@return                   Description of the Return Value
   *@throws  SQLException     Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpPageVersionId) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM web_page_version " +
        " WHERE page_version_id = ? ");
    pst.setInt(1, tmpPageVersionId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Page Version record not found");
    }
    if (buildPageRowList) {
      this.buildPageRowList(db);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {

    id = DatabaseUtils.getNextSeq(db, "web_page_version_page_version_id_seq");

    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO web_page_version " +
        "(" + (id > -1 ? "page_version_id, " : "") +
        "version_number , " +
        "internal_description , " +
        "notes , " +
        "parent_page_version_id , " +
        "enteredby , " +
        "modifiedby, " +
        "page_id ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    DatabaseUtils.setInt(pst, ++i, versionNumber);
    pst.setString(++i, internalDescription);
    pst.setString(++i, notes);
    DatabaseUtils.setInt(pst, ++i, parentPageVersionId);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, modifiedBy);
    DatabaseUtils.setInt(pst, ++i, pageId);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "web_page_version_page_version_id_seq", id);
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {

    int resultCount = -1;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE web_page_version " +
        "SET " +
        "version_number = ? , " +
        "internal_description = ? , " +
        "notes = ? , " +
        "parent_page_version_id = ?  , " +
        "page_id = ? ");

    if (!override) {
      sql.append(
          " , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE page_version_id = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, versionNumber);
    pst.setString(++i, internalDescription);
    pst.setString(++i, notes);
    DatabaseUtils.setInt(pst, ++i, parentPageVersionId);
    DatabaseUtils.setInt(pst, ++i, pageId);
    if (!override) {
      pst.setInt(++i, modifiedBy);
    }
    pst.setInt(++i, id);
    if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, modified);
    }
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {

    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }

//Update the immediate children's parent id to null
      PageVersionList tmpParentPageVersionList = new PageVersionList();
      tmpParentPageVersionList.setParentPageVersionId(this.getId());
      tmpParentPageVersionList.buildList(db);
      tmpParentPageVersionList.removeParentId(db);
      tmpParentPageVersionList = null;

//Build the page and update construction version id and active version id if it matches the current version id
      Page tmpPage = new Page(db, this.getPageId());
      if (tmpPage.getConstructionPageVersionId() == this.getId() || tmpPage.getActivePageVersionId() == this.getId()) {
        if (tmpPage.getConstructionPageVersionId() == this.getId()) {
          tmpPage.setConstructionPageVersionId(-1);
        }
        if (tmpPage.getActivePageVersionId() == this.getId()) {
          tmpPage.setActivePageVersionId(-1);
        }
        tmpPage.update(db);
      }
      PageRowList tmpPageRowList = new PageRowList();
      tmpPageRowList.setPageVersionId(this.getId());
      tmpPageRowList.buildList(db);
      tmpPageRowList.delete(db);
      tmpPageRowList = null;

      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM web_page_version " +
          "WHERE page_version_id =  ? ");

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
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("page_version_id");
    versionNumber = DatabaseUtils.getInt(rs, "version_number");
    internalDescription = rs.getString("internal_description");
    notes = rs.getString("notes");
    parentPageVersionId = DatabaseUtils.getInt(rs, "parent_page_version_id");

    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    pageId = DatabaseUtils.getInt(rs, "page_id");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildPageRowList(Connection db) throws SQLException {
    pageRowList = new PageRowList();
    pageRowList.setPageVersionId(this.getId());
    pageRowList.setMode(mode);
    pageRowList.setBuildColumns(true);
    pageRowList.setBuildIcelet(true);
    pageRowList.setBuildIceletPropertyMap(true);
    pageRowList.setBuildSubRows(true);
    pageRowList.buildList(db);
  }

  public int getTabIdByPageVersionId(Connection db) throws SQLException {
    int tabId = -1;
    PreparedStatement pst = db.prepareStatement(
      "SELECT tab_id FROM web_page_group "+
      "WHERE page_group_id IN (SELECT page_group_id FROM web_page " +
      " WHERE page_id IN (SELECT page_id FROM web_page_version " +
      "  WHERE page_version_id = ? )) ");
    pst.setInt(1, this.getId());
//System.out.println(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      tabId = rs.getInt("tab_id");
    }
    rs.close();
    pst.close();
    return tabId;
  }
}

