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
public class TabBanner extends GenericBean {

  private int id = -1;
  private int tabId = -1;
  private int imageId = -1;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;

  private boolean override = false;


  /**
   *  Constructor for the TabBanner object
   */
  public TabBanner() { }


  /**
   *  Constructor for the TabBanner object
   *
   *@param  db                Description of the Parameter
   *@param  tmpTabBannerId    Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@throws  SQLException     Description of the Exception
   */
  public TabBanner(Connection db, int tmpTabBannerId) throws SQLException {
    queryRecord(db, tmpTabBannerId);
  }


  /**
   *  Constructor for the TabBanner object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@throws  SQLException     Description of the Exception
   */
  public TabBanner(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the TabBanner object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TabBanner object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the tabId attribute of the TabBanner object
   *
   *@param  tmp  The new tabId value
   */
  public void setTabId(int tmp) {
    this.tabId = tmp;
  }


  /**
   *  Sets the tabId attribute of the TabBanner object
   *
   *@param  tmp  The new tabId value
   */
  public void setTabId(String tmp) {
    this.tabId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the imageId attribute of the TabBanner object
   *
   *@param  tmp  The new imageId value
   */
  public void setImageId(int tmp) {
    this.imageId = tmp;
  }


  /**
   *  Sets the imageId attribute of the TabBanner object
   *
   *@param  tmp  The new imageId value
   */
  public void setImageId(String tmp) {
    this.imageId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the TabBanner object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the TabBanner object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the TabBanner object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the TabBanner object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the TabBanner object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the TabBanner object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the TabBanner object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the TabBanner object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the override attribute of the TabBanner object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   *  Sets the override attribute of the TabBanner object
   *
   *@param  tmp  The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the TabBanner object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the tabId attribute of the TabBanner object
   *
   *@return    The tabId value
   */
  public int getTabId() {
    return tabId;
  }


  /**
   *  Gets the imageId attribute of the TabBanner object
   *
   *@return    The imageId value
   */
  public int getImageId() {
    return imageId;
  }


  /**
   *  Gets the enteredBy attribute of the TabBanner object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the TabBanner object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the TabBanner object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the TabBanner object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the override attribute of the TabBanner object
   *
   *@return    The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   *  Description of the Method
   *
   *@param  db              Description of the Parameter
   *@param  tmpTabBannerId  Description of the Parameter
   *@return                 Description of the Return Value
   *@throws  SQLException   Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpTabBannerId) throws SQLException {
    // TODO: Does something go here?
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

    id = DatabaseUtils.getNextSeq(db, "web_tab_banner_tab_banner_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO web_tab_banner " +
        "(" + (id > -1 ? "tab_banner_id, " : "") +
        "tab_id , " +
        "image_id , " +
        "enteredby , " +
        "modifiedby ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    DatabaseUtils.setInt(pst, ++i, tabId);
    DatabaseUtils.setInt(pst, ++i, imageId);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "web_tab_banner_tab_banner_id_seq", id);
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
        "UPDATE web_tab_banner " +
        "SET " +
        "tab_id = ? , " +
        "image_id = ? " );

    if (!override) {
      sql.append(
          " , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
    }
    sql.append("WHERE tab_banner_id = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, tabId);
    DatabaseUtils.setInt(pst, ++i, imageId);
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
      PageList tmpPageList = new PageList();
      tmpPageList.setTabBannerId(this.getId());
      tmpPageList.buildList(db);
      tmpPageList.delete(db);
      tmpPageList = null;

      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM web_tab_banner " +
          "WHERE tab_banner_id =  ? ");

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
    id = rs.getInt("tab_banner_id");
    tabId = DatabaseUtils.getInt(rs, "tab_id");
    imageId = DatabaseUtils.getInt(rs, "image_id");

    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }
}

