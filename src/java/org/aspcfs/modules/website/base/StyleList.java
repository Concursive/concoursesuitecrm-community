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
 *@author     kailash
 *@created    March 1, 2006
 *@version    $Id: Exp $
 */
public class StyleList extends ArrayList {

  private PagedListInfo pagedListInfo = null;

  private int id = -1;
  private int constant = -1;
  private int custom = Constants.UNDEFINED;
  private int layoutId = -1;


  /**
   *  Constructor for the StyleList object
   */
  public StyleList() { }


  /**
   *  Sets the pagedListInfo attribute of the StyleList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the StyleList object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the StyleList object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the constant attribute of the StyleList object
   *
   *@param  tmp  The new constant value
   */
  public void setConstant(int tmp) {
    this.constant = tmp;
  }


  /**
   *  Sets the constant attribute of the StyleList object
   *
   *@param  tmp  The new constant value
   */
  public void setConstant(String tmp) {
    this.constant = Integer.parseInt(tmp);
  }


  /**
   *  Sets the custom attribute of the StyleList object
   *
   *@param  tmp  The new custom value
   */
  public void setCustom(int tmp) {
    this.custom = tmp;
  }


  /**
   *  Sets the custom attribute of the StyleList object
   *
   *@param  tmp  The new custom value
   */
  public void setCustom(String tmp) {
    this.custom = Integer.parseInt(tmp);
  }


  /**
   *  Sets the layoutId attribute of the StyleList object
   *
   *@param  tmp  The new layoutId value
   */
  public void setLayoutId(int tmp) {
    this.layoutId = tmp;
  }


  /**
   *  Sets the layoutId attribute of the StyleList object
   *
   *@param  tmp  The new layoutId value
   */
  public void setLayoutId(String tmp) {
    this.layoutId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the StyleList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the StyleList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the layoutId attribute of the StyleList object
   *
   *@return    The layoutId value
   */
  public int getLayoutId() {
    return layoutId;
  }


  /**
   *  Gets the constant attribute of the StyleList object
   *
   *@return    The constant value
   */
  public int getConstant() {
    return constant;
  }


  /**
   *  Gets the custom attribute of the StyleList object
   *
   *@return    The custom value
   */
  public int getCustom() {
    return custom;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      Style thisStyle = this.getObject(rs);
      this.add(thisStyle);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@param  pst            Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
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
        "FROM web_style " +
        "WHERE style_id > -1 ");

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
      pagedListInfo.setDefaultSort("style_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY style_id ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "ws.* " +
        "FROM web_style ws " +
        "WHERE style_id > -1 ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    return pst;
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter      Description of the Parameter
   *@param  db             Description of the Parameter
   *@throws  SQLException  Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
    if (id != -1) {
      sqlFilter.append("AND style_id = ? ");
    }
    if (layoutId != -1) {
      sqlFilter.append("AND layout_id = ? ");
    }
    if (constant != -1) {
      sqlFilter.append("AND style_constant = ? ");
    }
    if (custom != Constants.UNDEFINED) {
      sqlFilter.append("AND custom = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst            Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id != -1) {
      pst.setInt(++i, id);
    }
    if (layoutId != -1) {
      pst.setInt(++i, layoutId);
    }
    if (constant != -1) {
      pst.setInt(++i, constant);
    }
    if (custom != Constants.UNDEFINED) {
      pst.setBoolean(++i, (custom == Constants.TRUE));
    }
    return i;
  }


  /**
   *  Gets the object attribute of the StyleList object
   *
   *@param  rs             Description of the Parameter
   *@return                The object value
   *@throws  SQLException  Description of the Exception
   */
  public Style getObject(ResultSet rs) throws SQLException {
    Style thisStyle = new Style(rs);
    return thisStyle;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator styleIterator = this.iterator();
    while (styleIterator.hasNext()) {
      Style thisStyle = (Style) styleIterator.next();
      thisStyle.delete(db);
    }
  }
}

