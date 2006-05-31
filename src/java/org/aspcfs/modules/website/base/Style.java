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
public class Style extends GenericBean {

  private int id = -1;
  private int constant = -1;
  private String name = null;
  private String css = null;
  private String thumbnail = null;
  private boolean custom = false;
  private int layoutId = -1;


  /**
   *  Constructor for the Style object
   */
  public Style() { }


  /**
   *  Constructor for the Style object
   *
   *@param  db                Description of the Parameter
   *@param  tmpStyleId        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Style(Connection db, int tmpStyleId) throws SQLException {
    queryRecord(db, tmpStyleId);
  }


  /**
   *  Constructor for the Style object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Style(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the Style object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Style object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the constant attribute of the Style object
   *
   *@param  tmp  The new constant value
   */
  public void setConstant(int tmp) {
    this.constant = tmp;
  }


  /**
   *  Sets the constant attribute of the Style object
   *
   *@param  tmp  The new constant value
   */
  public void setConstant(String tmp) {
    this.constant = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the Style object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the css attribute of the Style object
   *
   *@param  tmp  The new css value
   */
  public void setCss(String tmp) {
    this.css = tmp;
  }


  /**
   *  Sets the thumbnail attribute of the Style object
   *
   *@param  tmp  The new thumbnail value
   */
  public void setThumbnail(String tmp) {
    this.thumbnail = tmp;
  }


  /**
   *  Sets the custom attribute of the Style object
   *
   *@param  tmp  The new custom value
   */
  public void setCustom(boolean tmp) {
    this.custom = tmp;
  }


  /**
   *  Sets the custom attribute of the Style object
   *
   *@param  tmp  The new custom value
   */
  public void setCustom(String tmp) {
    this.custom = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the layoutId attribute of the Style object
   *
   *@param  tmp  The new layoutId value
   */
  public void setLayoutId(int tmp) {
    this.layoutId = tmp;
  }


  /**
   *  Sets the layoutId attribute of the Style object
   *
   *@param  tmp  The new layoutId value
   */
  public void setLayoutId(String tmp) {
    this.layoutId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the id attribute of the Style object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the constant attribute of the Style object
   *
   *@return    The constant value
   */
  public int getConstant() {
    return constant;
  }


  /**
   *  Gets the name attribute of the Style object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the css attribute of the Style object
   *
   *@return    The css value
   */
  public String getCss() {
    return css;
  }


  /**
   *  Gets the thumbnail attribute of the Style object
   *
   *@return    The thumbnail value
   */
  public String getThumbnail() {
    return thumbnail;
  }


  /**
   *  Gets the custom attribute of the Style object
   *
   *@return    The custom value
   */
  public boolean getCustom() {
    return custom;
  }


  /**
   *  Gets the layoutId attribute of the Style object
   *
   *@return    The layoutId value
   */
  public int getLayoutId() {
    return layoutId;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@param  tmpSiteId      Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpStyleId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM style " +
        " WHERE style_id = ? ");
    pst.setInt(1, tmpStyleId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Style record not found");
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

    id = DatabaseUtils.getNextSeq(db, "style_style_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO style " +
        "(" + (id > -1 ? "style_id, " : "") +
        "style_constant , " +
        "style_name , " +
        "css , " +
        "thumbnail , " +
        "custom , " +
        "layout_id ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    DatabaseUtils.setInt(pst, ++i, constant);
    pst.setString(++i, name);
    pst.setString(++i, css);
    pst.setString(++i, thumbnail);
    pst.setBoolean(++i, custom);
    DatabaseUtils.setInt(pst, ++i, layoutId);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "style_style_id_seq", id);
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
        "UPDATE style " +
        "SET " +
        "style_constant = ? , " +
        "style_name = ? , " +
        "css = ? , " +
        "thumbnail = ? , " +
        "custom = ? , " +
        "layout_id = ?  ");

    sql.append("WHERE layout_id = ? ");

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, constant);
    pst.setString(++i, name);
    pst.setString(++i, css);
    pst.setString(++i, thumbnail);
    pst.setBoolean(++i, custom);
    DatabaseUtils.setInt(pst, ++i, layoutId);
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

      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM style " +
          "WHERE style_id = ? ");

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
    id = rs.getInt("style_id");
    constant = DatabaseUtils.getInt(rs, "style_constant");
    name = rs.getString("style_name");
    css = rs.getString("css");
    thumbnail = rs.getString("thumbnail");
    custom = rs.getBoolean("custom");
    layoutId = DatabaseUtils.getInt(rs, "layout_id");
  }
}

