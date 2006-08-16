/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.admin.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Builds a list of user Roles
 *
 * @author matt rajkowski
 * @version $Id$
 * @created January 14, 2002
 */
public class RoleList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private String emptyHtmlSelectRecord = null;
  private int enabledState = Constants.TRUE;
  private boolean buildUsers = false;
  private boolean buildUserCount = false;
  private int roleType = -1;
  private int excludeRoleType = -1;
  private String jsEvent = null;

  /**
   * Constructor for the RoleList object
   */
  public RoleList() {
  }


  /**
   * Sets the pagedListInfo attribute of the RoleList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the emptyHtmlSelectRecord attribute of the RoleList object
   *
   * @param tmp The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }


  /**
   * Sets the enabledState attribute of the RoleList object
   *
   * @param booleanInt The new enabledState value
   */
  public void setEnabledState(int booleanInt) {
    enabledState = booleanInt;
  }


  /**
   * Sets the buildUsers attribute of the RoleList object
   *
   * @param tmp The new buildUsers value
   */
  public void setBuildUsers(boolean tmp) {
    this.buildUsers = tmp;
  }


  /**
   * Sets the buildUserCount attribute of the RoleList object
   *
   * @param tmp The new buildUserCount value
   */
  public void setBuildUserCount(boolean tmp) {
    this.buildUserCount = tmp;
  }


  /**
   * Sets the roleType attribute of the RoleList object
   *
   * @param tmp The new roleType value
   */
  public void setRoleType(int tmp) {
    this.roleType = tmp;
  }


  /**
   * Sets the roleType attribute of the RoleList object
   *
   * @param tmp The new roleType value
   */
  public void setRoleType(String tmp) {
    this.roleType = Integer.parseInt(tmp);
  }


  /**
   * Sets the excludeRoleType attribute of the RoleList object
   *
   * @param tmp The new excludeRoleType value
   */
  public void setExcludeRoleType(int tmp) {
    this.excludeRoleType = tmp;
  }

  public void setJsEvent(String jsEvent) {
    this.jsEvent = jsEvent;
  }

  public String getJsEvent() {
    return jsEvent;
  }

  /**
   * Gets the htmlSelect attribute of the RoleList object
   *
   * @param selectName Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   * Gets the htmlSelect attribute of the RoleList object Selects the regular
   * roles from the role list
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect roleListSelect = new HtmlSelect();
    if (emptyHtmlSelectRecord != null) {
      roleListSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    if (jsEvent != null) {
      roleListSelect.setJsEvent(jsEvent);
    }
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Role thisRole = (Role) i.next();
      roleListSelect.addItem(
          thisRole.getId(),
          thisRole.getRole());
    }
    return roleListSelect.getHtml(selectName, defaultKey);
  }


  /**
   * Gets the lookupList attribute of the RoleList object
   *
   * @return The lookupList value
   */
  public LookupList getLookupList() {
    LookupList thisList = new LookupList();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Role thisRole = (Role) i.next();
      thisList.addItem(
          thisRole.getId(),
          thisRole.getRole());
    }
    return thisList;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM " + DatabaseUtils.addQuotes(db, "role") + " r " +
            "WHERE r.role_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(
          sqlCount.toString() +
              sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
                sqlFilter.toString() +
                "AND " + DatabaseUtils.toLowerCase(db) + "(" + DatabaseUtils.addQuotes(db, "role") + ") < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      //Determine column to sort by
      pagedListInfo.setDefaultSort("role", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY " + DatabaseUtils.addQuotes(db, "role") + " ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "r.* " +
            "FROM " + DatabaseUtils.addQuotes(db, "role") + " r " +
            "WHERE r.role_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      Role thisRole = new Role(rs);
      this.add(thisRole);
    }
    rs.close();
    pst.close();
    //Build resources
    if (buildUsers || buildUserCount) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        Role thisRole = (Role) i.next();
        if (buildUsers) {
          thisRole.buildUserList(db);
        }
        if (buildUserCount) {
          thisRole.buildUserCount(db, true);
        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (roleType != -1) {
      sqlFilter.append("AND role_type = ? ");
    }
    if (excludeRoleType != -1) {
      sqlFilter.append("AND role_type <> ? ");
    }
    if (enabledState > -1) {
      sqlFilter.append("AND enabled = ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (roleType != -1) {
      pst.setInt(++i, roleType);
    }
    if (excludeRoleType != -1) {
      pst.setInt(++i, excludeRoleType);
    }
    if (enabledState > -1) {
      pst.setBoolean(++i, enabledState == Constants.TRUE);
    }
    return i;
  }


  /**
   * Gets the roleNameFromId attribute of the RoleList object
   *
   * @param id Description of the Parameter
   * @return The roleNameFromId value
   */
  public String getRoleNameFromId(int id) {
    String result = null;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Role thisRole = (Role) i.next();
      if (id == thisRole.getId()) {
        result = thisRole.getRole();
        break;
      }
    }
    return result;
  }

}

