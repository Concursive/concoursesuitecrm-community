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
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author     kailash
 *@created    March 1, 2006 $Id: Exp $
 *@version    $Id: Exp $
 */
public class Layout extends GenericBean {

  private int id = -1;
  private int constant = -1;
  private String name = null;
  private String jsp = null;
  private String thumbnail = null;
  private boolean custom = false;

  private boolean buildStyleList = false;
  private StyleList styleList = null;


  /**
   *  Constructor for the Layout object
   */
  public Layout() { }


  /**
   *  Constructor for the Layout object
   *
   *@param  db                Description of the Parameter
   *@param  tmpLayoutId       Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Layout(Connection db, int tmpLayoutId) throws SQLException {
    queryRecord(db, tmpLayoutId);
  }


  /**
   *  Constructor for the Layout object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Layout(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the Layout object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Layout object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the constant attribute of the Layout object
   *
   *@param  tmp  The new constant value
   */
  public void setConstant(int tmp) {
    this.constant = tmp;
  }


  /**
   *  Sets the constant attribute of the Layout object
   *
   *@param  tmp  The new constant value
   */
  public void setConstant(String tmp) {
    this.constant = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the Layout object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the jsp attribute of the Layout object
   *
   *@param  tmp  The new jsp value
   */
  public void setJsp(String tmp) {
    this.jsp = tmp;
  }


  /**
   *  Sets the thumbnail attribute of the Layout object
   *
   *@param  tmp  The new thumbnail value
   */
  public void setThumbnail(String tmp) {
    this.thumbnail = tmp;
  }


  /**
   *  Sets the custom attribute of the Layout object
   *
   *@param  tmp  The new custom value
   */
  public void setCustom(boolean tmp) {
    this.custom = tmp;
  }


  /**
   *  Sets the custom attribute of the Layout object
   *
   *@param  tmp  The new custom value
   */
  public void setCustom(String tmp) {
    this.custom = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the buildStyleList attribute of the Layout object
   *
   *@param  tmp  The new buildStyleList value
   */
  public void setBuildStyleList(boolean tmp) {
    this.buildStyleList = tmp;
  }


  /**
   *  Sets the buildStyleList attribute of the Layout object
   *
   *@param  tmp  The new buildStyleList value
   */
  public void setBuildStyleList(String tmp) {
    this.buildStyleList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the styleList attribute of the Layout object
   *
   *@param  tmp  The new styleList value
   */
  public void setStyleList(StyleList tmp) {
    this.styleList = tmp;
  }


  /**
   *  Gets the id attribute of the Layout object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the constant attribute of the Layout object
   *
   *@return    The constant value
   */
  public int getConstant() {
    return constant;
  }


  /**
   *  Gets the name attribute of the Layout object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the jsp attribute of the Layout object
   *
   *@return    The jsp value
   */
  public String getJsp() {
    return jsp;
  }


  /**
   *  Gets the thumbnail attribute of the Layout object
   *
   *@return    The thumbnail value
   */
  public String getThumbnail() {
    return thumbnail;
  }


  /**
   *  Gets the custom attribute of the Layout object
   *
   *@return    The custom value
   */
  public boolean getCustom() {
    return custom;
  }


  /**
   *  Gets the buildStyleList attribute of the Layout object
   *
   *@return    The buildStyleList value
   */
  public boolean getBuildStyleList() {
    return buildStyleList;
  }


  /**
   *  Gets the styleList attribute of the Layout object
   *
   *@return    The styleList value
   */
  public StyleList getStyleList() {
    return styleList;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@param  tmpSiteId      Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpLayoutId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM web_layout " +
        " WHERE layout_id = ? ");
    pst.setInt(1, tmpLayoutId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Layout record not found");
    }
    if (buildStyleList) {
      buildStyleList(db);
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

    id = DatabaseUtils.getNextSeq(db, "web_layout_layout_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO web_layout " +
        "(" + (id > -1 ? "layout_id, " : "") +
        "layout_constant , " +
        "layout_name , " +
        "jsp , " +
        "thumbnail , " +
        "custom ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    DatabaseUtils.setInt(pst, ++i, constant);
    pst.setString(++i, name);
    pst.setString(++i, jsp);
    pst.setString(++i, thumbnail);
    pst.setBoolean(++i, custom);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "web_layout_layout_id_seq", id);
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
        "UPDATE web_layout " +
        "SET " +
        "layout_constant = ? , " +
        "layout_name = ? , " +
        "jsp = ? , " +
        "thumbnail = ? , " +
        "custom = ? ");

    sql.append("WHERE layout_id = ? ");

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, constant);
    pst.setString(++i, name);
    pst.setString(++i, jsp);
    pst.setString(++i, thumbnail);
    pst.setBoolean(++i, custom);
    pst.setInt(++i, id);

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

      StyleList tmpStyleList = new StyleList();
      tmpStyleList.setLayoutId(this.getId());
      tmpStyleList.buildList(db);
      tmpStyleList.delete(db);
      tmpStyleList = null;

      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM web_layout " +
          "WHERE layout_id = ? ");

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
   *@param  rs             Description of the Parameter
   *@throws  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("layout_id");
    constant = DatabaseUtils.getInt(rs, "layout_constant");
    name = rs.getString("layout_name");
    jsp = rs.getString("jsp");
    thumbnail = rs.getString("thumbnail");
    custom = rs.getBoolean("custom");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildStyleList(Connection db) throws SQLException {
    styleList = new StyleList();
    styleList.buildList(db);
  }
}

