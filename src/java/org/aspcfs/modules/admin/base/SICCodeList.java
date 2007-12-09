/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.admin.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author holub
 * @version $Id: Exp $
 * @created Sep 15, 2006
 */
public class SICCodeList extends ArrayList {
  private static final long serialVersionUID = 322620745210514895L;
  public String tableName = null;
  protected HashMap selectedItems = null;

  private String constantId = null;
  private boolean enabled = true;

  public SICCodeList() {
    tableName = "lookup_sic_codes";
  }

  public SICCodeList(Connection db) throws SQLException {
    tableName = "lookup_sic_codes";
    buildList(db);
  }

  /**
   * Adds a feature to the Item attribute of the LookupList object
   *
   * @param tmp1 The feature to be added to the Item attribute
   * @param tmp2 The feature to be added to the Item attribute
   */
  public void addItem(int tmp1, String tmp2) {
    if (!exists(tmp1)) {
      SICCode thisElement = new SICCode();
      thisElement.setCode(tmp1);
      thisElement.setDescription(tmp2);
      if (this.size() > 0) {
        this.add(0, thisElement);
      } else {
        this.add(thisElement);
      }
    }
  }


  /**
   * Checks to see if the entry is already in the list
   *
   * @param tmp1 Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean exists(int tmp1) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      SICCode thisElement = (SICCode) i.next();
      if (thisElement.getCode() == tmp1) {
        return true;
      }
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sqlOrder = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlSelect = new StringBuffer();

    sqlSelect.append(
        "SELECT sict.* " +
            "FROM " + DatabaseUtils.getTableName(db, tableName) + " sict " +
            "WHERE code > -1 ");

    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ",description ");

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      SICCode thisType = new SICCode(rs);
      this.add(thisType);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }

  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (constantId != null) {
      sqlFilter.append("AND constant_id = ? ");
    }
  }

  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (constantId != null) {
      pst.setString(++i, constantId);
    }
    return i;
  }

  /**
   * Gets the idFromLevel attribute of the SICCodeList object
   *
   * @param level Description of the Parameter
   * @return The idFromLevel value
   */
  public int getIdFromConstantId(String constantId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      SICCode thisElement = (SICCode) i.next();
      if (thisElement.getConstantId().equals(constantId)) {
        return thisElement.getId();
      }
    }
    return -1;
  }

  /**
   * Gets the htmlSelect attribute of the LookupList object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    return getHtmlSelect(selectName, defaultKey, false);
  }


  /**
   * Gets the HtmlSelect attribute of the SICCodeList object
   *
   * @param selectName Description of Parameter
   * @param defaultKey Description of Parameter
   * @param disabled   Description of the Parameter
   * @return The HtmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey, boolean disabled) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setDisabled(disabled);
    Iterator i = this.iterator();
    boolean keyFound = false;
    int lookupDefault = defaultKey;
    while (i.hasNext()) {
      SICCode thisElement = (SICCode) i.next();
      // Add the item to the list
      if (thisElement.getEnabled() == true) {
        thisSelect.addItem(
            thisElement.getCode(), thisElement.getDescription());
        if (thisElement.getDefaultItem()) {
          lookupDefault = thisElement.getCode();
        }
      } else if (thisElement.getCode() == defaultKey) {
        thisSelect.addItem(
            thisElement.getCode(), thisElement.getDescription());
      }
      if (thisElement.getCode() == defaultKey) {
        keyFound = true;
      }
    }
    if (keyFound) {
      return thisSelect.getHtml(selectName, defaultKey);
    } else {
      return thisSelect.getHtml(selectName, lookupDefault);
    }
  }

  public String getDescriptionByCode(int code) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      SICCode thisElement = (SICCode) i.next();
      if (thisElement.getCode() == code) {
        return thisElement.getDescription();
      }
    }
    return null;
  }
}